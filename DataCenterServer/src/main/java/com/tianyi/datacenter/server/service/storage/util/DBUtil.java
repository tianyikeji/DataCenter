package com.tianyi.datacenter.server.service.storage.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * //TODO 说明
 *
 * @author zhouwei
 * 2018/11/22 10:10
 * @version 0.1
 **/
@Component
public class DBUtil {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * TODO 说明
     * @author zhouwei
     * 2018/11/22 10:11
     * @param
     * @return
    */
    public int executeDDL(String operType, String sql) {
        int rst = 0;
        if("C".equals(operType)){
            //创建表语句
            rst = createTable(sql);
        }
        return rst;
    }

    private int createTable(String sql) {
        return jdbcTemplate.update(sql);
    }
}
