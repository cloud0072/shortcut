package com.gravel.shortcut.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.gravel.shortcut.configuration.AsyncJob;
import com.gravel.shortcut.configuration.bloom.BloomFilter;
import com.gravel.shortcut.service.GenNumService;
import com.gravel.shortcut.service.UrlConvertService;
import com.gravel.shortcut.utils.NumericConvertUtils;
import com.gravel.shortcut.utils.SnowFlake;
import com.gravel.shortcut.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @ClassName UrlConvertServiceImpl
 * @Description: 短地址处理service
 * @Author gravel
 * @Date 2020/1/29
 * @Version V1.0
 **/
@Slf4j
@Service
public class UrlConvertServiceImpl implements UrlConvertService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private BloomFilter bloomFilter;

    @Resource
    private AsyncJob asyncJob;

    @Qualifier("SnowFlakeGen")
    @Resource
    private GenNumService genNumService;

    /**
     * 得到短地址URL
     *
     * @param url
     * @return
     */
    @Override
    public String convertUrl(String url) {
        Preconditions.checkArgument(Validator.checkUrl(url), "[url]格式不合法！url={}", url);
        log.info("转换开始----->[url]={}", url);
        String shortCut;
        // 如果布隆过滤器能命中，则直接返回 对应的value
        if (bloomFilter.includeByBloomFilter(url)) {
            if (!Strings.isNullOrEmpty(shortCut = redisTemplate.opsForValue().get(url))) {
                log.info("布隆过滤器命中----->[shortCut]={}", shortCut);
                return shortCut;
            }
        }
        // 生成一个Id
        shortCut = genShortCut();
        log.info("转换成功----->[shortCut]={}", shortCut);
        // 将短网址和短域名异步添加到布隆过滤器中，提升响应速度
        asyncJob.add2RedisAndBloomFilter(shortCut, url);
        // 存在的话直接返回

        return shortCut;
    }

    private String genShortCut() {
        String shortCut;
        // 直接生成一个新的短地址，并存入缓存
        long nextId = genNumService.genNum();
        // 转换为62进制
        shortCut = NumericConvertUtils.convertTo(nextId, 62);
        String url = redisTemplate.opsForValue().get(shortCut);
        if (StringUtils.isEmpty(url)) {
            return shortCut;
        } else {
            return genShortCut();
        }
    }

    /**
     * 将短地址URL 转换为正常的地址
     *
     * @param shortUrl
     * @return
     */
    @Override
    public String revertUrl(String shortUrl) {
        log.info("还原开始----->[shortUrl]={}", shortUrl);
        String shortcut = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        String url = redisTemplate.opsForValue().get(shortcut);
        log.info("还原成功----->[真实Url]={}", url);
        return url;
    }

    /**
     * 参数转换
     * @param param
     * @return
     */
    @Override
    public String encode(String param) {
//        Preconditions.checkArgument(Validator.checkUrl(url), "[url]格式不合法！url={}", url);
        log.info("转换开始----->[param]={}", param);
        String key;
        // 如果布隆过滤器能命中，则直接返回 对应的value
        if (bloomFilter.includeByBloomFilter(param)) {
            key = redisTemplate.opsForValue().get(param);
            if (!Strings.isNullOrEmpty(key)) {
                log.info("布隆过滤器命中----->[key]={}", key);
                return key;
            }
        }
        // 生成一个Id
        key = genShortCut();
        log.info("转换成功----->[key]={}", key);
        // 将短网址和短域名异步添加到布隆过滤器中，提升响应速度
        asyncJob.add2RedisAndBloomFilter(key, param);
        // 存在的话直接返回

        return key;
    }

    @Override
    public String decode(String key) {
        log.info("还原开始----->[key]={}", key);
        String param = redisTemplate.opsForValue().get(key);
        log.info("还原成功----->[param]={}", param);
        return param;
    }

}
