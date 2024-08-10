package com.gravel.shortcut.utils;

import java.util.regex.Pattern;

/**
 * @ClassName Validator
 * @Description: 验证工具类
 * @Author gravel
 * @Date 2020/2/10
 * @Version V1.0
 **/
public class Validator {

    private final static String URL_REGEX = "(ftp|http|https):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?";
    private final static String YUN_BENZ_REGEX = "^https://([a-z.\\-]*)(yunbenz\\.com|hibenz\\.cn|hnlongyun\\.com|hnbenz\\.cn|hnlybd\\.com|xinghuiyunkeji\\.com|bjhub\\.cn)([\\w#!:.?+=&%@!\\-\\/\\u4e00-\\u9fa5]*)";

    /**
     * 验证URL地址
     *
     * @param url
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkUrl(String url) {
        return Pattern.matches(YUN_BENZ_REGEX, url);
    }

    public static void main(String[] args) {
        String t = "https://www.hnlongyun.com/mp/jump?page=pages/index/index?from=100";
        System.out.println(checkUrl(t));
    }

}
