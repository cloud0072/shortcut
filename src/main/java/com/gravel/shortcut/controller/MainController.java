package com.gravel.shortcut.controller;

import com.google.zxing.WriterException;
import com.gravel.shortcut.domain.Result;
import com.gravel.shortcut.domain.ResultGenerator;
import com.gravel.shortcut.service.UrlConvertService;
import com.gravel.shortcut.utils.QRcodeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * @ClassName MainController
 * @Description: 主控制器
 * @Author gravel
 * @Date 2020/1/29
 * @Version V1.0
 **/
@RestController
public class MainController {

    @Resource
    private UrlConvertService urlConvertService;

    @Value("${common.domain}")
    private String domain;

    @GetMapping(value = "/qrcode", produces = MediaType.IMAGE_JPEG_VALUE)
    public BufferedImage getImage(@RequestParam String url) throws IOException, WriterException {
        return QRcodeUtils.QREncode(url);
    }

    /**
     * 传入url 返回 转换成功的url
     *
     * @param data
     * @return
     */
    @PostMapping("/convert")
    public Result<String> convertUrl(@RequestBody Map<String, String> data) {
        String url = data.get("url");
        return ResultGenerator.genSuccessResult(urlConvertService.convertUrl(url));
    }

    @PostMapping("/revert")
    public Result<String> revertUrl(@RequestBody Map<String, String> data) {
        String url = data.get("url");
        return ResultGenerator.genSuccessResult(urlConvertService.revertUrl(url));
    }

    /**
     * 完整参数
     * @param request
     * @return
     */
    @PostMapping("/encode")
    public Result<String> encode(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        StringBuilder param = new StringBuilder();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            if (param.indexOf("=") > 0) {
                param.append("&");
            }
            param.append(key).append("=").append(request.getParameter(key));
        }
        return ResultGenerator.genSuccessResult(urlConvertService.encode(param.toString()));
    }

    /**
     * 短参数
     * @param request
     * @return
     */
    @PostMapping("/decode")
    public Result<String> decode(HttpServletRequest request) {
        String key = request.getParameter("key");
        return ResultGenerator.genSuccessResult(urlConvertService.decode(key));
    }

}
