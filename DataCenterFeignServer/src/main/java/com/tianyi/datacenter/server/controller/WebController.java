package com.tianyi.datacenter.server.controller;

import com.tianyi.datacenter.server.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 负载均衡
 *
 * @author wenxinyan
 * @version 0.1
 */
@RestController
public class WebController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/sayhello")
    public String sayHello() {
        return helloService.sayHello();
    }

}
