package com.gravel.shortcut.controller;

import com.gravel.shortcut.configuration.ServerInitConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IndexController
 * @Description: 页面控制器
 * @Author gravel
 * @Date 2020/1/29
 * @Version V1.0
 **/
@Controller
public class IndexController {

    @Resource
    private ServerInitConfiguration serverInitConfiguration;

    @Value("${common.domain}")
    private String domain;

    @GetMapping(value = "/")
    public RedirectView home(HttpServletRequest request) {
        return new RedirectView("https://www.yunbenz.com");
    }

    @GetMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("domain", StringUtils.isEmpty(domain)?serverInitConfiguration.getUrl():domain);
        return mv;
    }
}
