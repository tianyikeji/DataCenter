package com.tianyi.datacenter.server;

//import com.netflix.discovery.converters.Auto;
import com.tianyi.datacenter.common.aop.ControllerAspect;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

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
    @Autowired
    private BusinessDBConfig businessDBConfig;

    public static void main(String[] args) {
        SpringApplication.run(DataCenterServerApplication.class, args);
    }

    @Bean(name="businessDataSource")
    public DataSource businessDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(businessDBConfig.getDriver());
        dataSource.setPassword(businessDBConfig.getPassword());
        dataSource.setUsername(businessDBConfig.getUsername());
        dataSource.setJdbcUrl(businessDBConfig.getUrl());
        return dataSource;
    }

    @Bean(name = "businessJdbcTemplate")
    public JdbcTemplate businessJdbcTemplate(@Qualifier("businessDataSource")DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
