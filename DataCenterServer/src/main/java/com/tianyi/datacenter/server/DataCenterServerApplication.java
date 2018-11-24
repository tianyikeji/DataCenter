package com.tianyi.datacenter.server;

import com.tianyi.datacenter.common.aop.ControllerAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 数据中心服务启动类
 *
 * @author wenxinyan
 * @version 0.1
 */
@SpringBootApplication
@EnableAspectJAutoProxy
//@EnableEurekaClient
@Import(ControllerAspect.class)
@MapperScan("com.tianyi.datacenter.server.dao")
public class DataCenterServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataCenterServerApplication.class, args);
    }

}
