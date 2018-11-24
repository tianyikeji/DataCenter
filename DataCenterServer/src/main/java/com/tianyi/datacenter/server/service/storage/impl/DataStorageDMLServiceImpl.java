package com.tianyi.datacenter.server.service.storage.impl;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.service.storage.DataStorageDMLService;
import com.tianyi.datacenter.server.service.storage.util.DMLUtil;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据存储模块执行DML语句服务实现
 * 2018/11/15 19:03
 *
 * @author zhouwei
 * @version 0.1
 **/
@Service
public class DataStorageDMLServiceImpl implements DataStorageDMLService {

    //todo 修改成调用统一的数据库操作bean
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 生成DML执行语句服务
     *
     * @param requestVo 请求对象
     * @return sql语句
     * @author zhouwei
     * 2018/11/15 19:03
     **/
    public ResponseVo doServer(RequestVo<DataStorageDMLVo> requestVo) throws DataCenterException {
        DataStorageDMLVo dmlVo = requestVo.getRequest();

        //校验请求参数
        String dmlType = dmlVo.getDmlType();
        if (StringUtils.isEmpty(dmlType) || ObjectUtils.isEmpty(dmlVo.getDataObject())) {
            //操作类型参数不能为空
            //TODO
            throw new DataCenterException("");
        } else {
            if ("U".equals(dmlType)) {
                if (CollectionUtils.isEmpty(dmlVo.getCondition()) || CollectionUtils.isEmpty(dmlVo.getUpdateInfo())) {
                    //更新操作，更新信息不能为空
                    //TODO
                    throw new DataCenterException("");
                }
            }if("C".equals(dmlType)){
                if(CollectionUtils.isEmpty(dmlVo.getUpdateInfo())){
                    //新增操作，字段信息不能为空
                    //TODO
                    throw new DataCenterException("");
                }
            }
        }

        //拼接sql语句
        String sql = DMLUtil.generateDML(dmlVo, requestVo.getPageInfo());

        Map rtnInfo = new HashMap();
        if("R".equals(dmlType)){
            //执行sql语句
            List datas = executeDML(dmlType, sql);

            if (datas.size() < 1) {
                //TODO
            } else {
                rtnInfo.put("rtnData", datas);
                return ResponseVo.success(rtnInfo);
            }
        }else{
            int i = executeDML(dmlType, sql);
            if(i<1){
                //TODO
            }else{
                return ResponseVo.success();
            }
        }
        return null;
    }

    /**
     * 执行dml语句方法
     * @author zhouwei
     * 2018/11/21 17:06
     * @param  operType 操作类型
     * @param sql dml语句
     * @return 返回指定类型的数据，查询返回list，其余返回int
    */
    private <T> T executeDML(String operType, String sql) {
        if ("R".equals(operType)) {
            //查询操作
            List datas = query(sql);
            return (T)datas;
        }else if("C".equals(operType)){
            //新增操作
            int rtnInt = insert(sql);
        }else if("U".equals(operType)){

        }
        return null;
    }

    /**
     * 执行插入语句
     * @author zhouwei
     * 2018/11/22 17:36
     * @param sql insert语句
     * @return 1代表成功，其余代表失败
    */
    private int insert(String sql) {
        return 0;
    }


    /**
     * 执行查询sql语句
     * @author zhouwei
     * 2018/11/21 17:08
     * @param sql 查询语句
     * @return  查询结果
    */
    private List query(String sql) {
        //查询语句
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

        return result;
    }



}


