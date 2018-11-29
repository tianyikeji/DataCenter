package com.tianyi.datacenter.server.service.storage.impl;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.service.storage.DataStorageDMLService;
import com.tianyi.datacenter.server.service.storage.util.DBUtil;
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

    @Autowired
    private DBUtil database;

    @Autowired
    private DMLUtil dmlUtil;

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
                if (CollectionUtils.isEmpty(dmlVo.getCondition()) || CollectionUtils.isEmpty(dmlVo.getAttributes())) {
                    //更新操作，更新信息不能为空
                    //TODO
                    throw new DataCenterException("");
                }
            }else if("C".equals(dmlType)){
                if(CollectionUtils.isEmpty(dmlVo.getAttributes())){
                    //新增操作，字段信息不能为空
                    //TODO
                    throw new DataCenterException("");
                }
            } else if ("D".equals(dmlType)) {
                if (CollectionUtils.isEmpty(dmlVo.getCondition())) {
                    //TODO
                    throw new DataCenterException("");
                }
            }
        }

        //拼接sql语句
        String sql = dmlUtil.generateDML(dmlVo, requestVo.getPageInfo());

        Map<String,Object> rtnInfo = new HashMap<>();
        if("R".equals(dmlType)){
            //执行sql语句
            List datas = database.executeQuery(sql);

            if (datas.size() < 1) {
                return ResponseVo.fail("没有查询到数据");
            } else {
                rtnInfo.put("rtnData", datas);
                return ResponseVo.success(rtnInfo);
            }
        }else{
            int i = database.executeDML(dmlType, sql);
            if(i<1){
                //TODO
            }else{
                return ResponseVo.success();
            }
        }
        return null;
    }

}


