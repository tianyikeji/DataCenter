package com.tianyi.datacenter.server.service;

import com.tianyi.datacenter.server.service.failure.HelloServiceFailure;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 负载均衡服务
 *
 * @author wenxinyan
 * @version 0.1
 */
@FeignClient(value = "SERVICE-DATACENTER", fallback = HelloServiceFailure.class)
@Service
public interface HelloService {

    @RequestMapping("hello")
    String sayHello();

}
