package com.tianyi.datacenter.server.service.storage.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Qualifier("businessJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    /**
     * TODO 说明
     * @author zhouwei
     * 2018/11/22 10:11
     * @param operType 操作类型
     * @param sql 执行sql
     * @return int
    */
    public void executeDDL(String operType, String sql) throws Exception {
        if ("C".equals(operType)) {
            //创建表语句
            createTable(sql);
        } else if ("U".equals(operType)) {
            //修改表语句
            alterTable(sql);
        }
    }

    /**
     * 执行dml语句，查询除外
     * @author zhouwei
     * 2018/11/28 10:05
     * @param operType 操作类型，C-新增；U-更新；D-删除
     * @return 执行影响条数int
    */
    public int executeDML(String operType, String sql){
        int rst = 0;
        switch (operType) {
            case "C":
                rst = update(sql);
                break;
            case "U":
                rst = update(sql);
                break;
            case "D":
                rst = update(sql);
                break;
        }
        return rst;
    }

    /**
     * 执行查询sql
     * @author zhouwei
     * 2018/11/28 10:07
     * @param sql 查询sql语句
     * @return 查询结果List
    */
    public List<Map<String, Object>> executeQuery(String sql){
        return queryForList(sql);
    }

    private void createTable(String sql) throws Exception {
        jdbcTemplate.update(sql);
    }

    private void alterTable(String sql) throws Exception{
        jdbcTemplate.update(sql);
    }

    private int update(String sql) {
        return jdbcTemplate.update(sql);
    }

    private List<Map<String, Object>> queryForList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 获取指定表的字段信息
     * @author zhouwei
     * 2018/11/28 19:47
     * @param tableName 指定表名
     * @return 表的所有字段信息
    */
    public Map<String, Map<String, Object>> getColumnsInfo(String tableName) throws SQLException {
        //获取schema信息（字段信息）
        DatabaseMetaData metaData = null;
        try {
            metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = metaData.getColumns(null, null, tableName, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //组装字段属性并返回
        Map<String, Map<String, Object>> columnsInfo = new HashMap<>();
        while(rs.next()){
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("COLUMN_NAME", rs.getObject("COLUMN_NAME"));
            tmp.put("DATA_TYPE", rs.getObject("DATA_TYPE"));
            tmp.put("TYPE_NAME", rs.getObject("TYPE_NAME"));
            tmp.put("COLUMN_SIZE", rs.getObject("COLUMN_SIZE"));
            tmp.put("DECIMAL_DIGITS", rs.getObject("DECIMAL_DIGITS"));
            tmp.put("REMARKS", rs.getObject("REMARKS"));
            tmp.put("IS_AUTOINCREMENT", rs.getObject("IS_AUTOINCREMENT"));
            tmp.put("IS_NULLABLE", rs.getObject("IS_NULLABLE"));
            columnsInfo.put(rs.getString("COLUMN_NAME").toUpperCase(), tmp);
        }
        return columnsInfo;

    }
}
