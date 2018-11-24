package com.tianyi.datacenter.server.controller.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 环境搭建测试Controller
 *
 * @author wenxinyan
 * @version 0.1
 */
@RestController
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${server.port}") String port;
    @RequestMapping("/hello")
    public String hello() {
        logger.info("hello world from port : {}", port);
        return "hello world from port : " + port;
    }

}
