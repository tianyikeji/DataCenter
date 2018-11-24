package com.tianyi.datacenter.server.service.failure;

import com.tianyi.datacenter.server.service.HelloService;
import org.springframework.stereotype.Component;

/**
 * 负载均衡错误返回
 *
 * @author wenxinyan
 * @version 0.1
 */
@Component
public class HelloServiceFailure implements HelloService {

    @Override
    public String sayHello() {
        return "hello world service is not available !";
    }

}
