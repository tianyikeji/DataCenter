package com.tianyi.datacenter.server.service.storage.impl;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.storage.DataStorageDDLService;
import com.tianyi.datacenter.server.service.storage.util.DBUtil;
import com.tianyi.datacenter.server.service.storage.util.DDLUtil;
import com.tianyi.datacenter.server.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 执行DDL语句服务实现
 *
 * @author zhouwei
 * 2018/11/22 08:30
 * @version 0.1
 **/
@Service
public class DataStorageDDLServiceImpl implements DataStorageDDLService {

    @Autowired
    private DBUtil database;
    private DDLUtil ddlUtil = new DDLUtil();

    private Logger logger = LoggerFactory.getLogger(DataStorageDDLService.class);

    @Override
    public RequestVo<DataStorageDDLVo> getRequestVo(String ddlType, DataObject dataObject, List<DataObjectAttribute> attributeList){
        DataStorageDDLVo dataStorageDDLVo = new DataStorageDDLVo();
        dataStorageDDLVo.setDdlType(ddlType);
        dataStorageDDLVo.setDataObject(dataObject);
        dataStorageDDLVo.setAttributes(attributeList);

        RequestVo<DataStorageDDLVo> requestVo = new RequestVo<>(dataStorageDDLVo);

        return requestVo;
    }

    @Override
    public ResponseVo doServer(RequestVo<DataStorageDDLVo> requestVo) throws DataCenterException {

        //校验服务接口
        DataStorageDDLVo ddlVo = requestVo.getRequest();
        String ddlType = ddlVo.getDdlType();
        if(StringUtils.isEmpty(ddlType)){
            //TODO
            throw new DataCenterException("参入校验失败");
        }
        if (ddlVo.getDataObject() == null || ddlVo.getAttributes() == null) {
            //TODO
            throw new DataCenterException("");
        }

        if ("U".equals(ddlType)){
            try {
                handlerAlterColumns(ddlVo);
            } catch (SQLException e) {
                //todo
                e.printStackTrace();
                return ResponseVo.fail("生成Alter语句错误");
            }
        }
        //创建ddl语句
        String ddlSql = ddlUtil.generateDDL(ddlVo);

        //执行ddl语句
        try {
            database.executeDDL(ddlType, ddlSql);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVo.fail("执行语句异常:"+e.getCause());
        }

        //todo 增加返回码
        return ResponseVo.success();
    }

    /**
     * 组装alter表需要的属性信息
     * @author zhouwei
     * 2018/11/29 10:30
     * @param ddlVo 修改请求接口数据
    */
    private void handlerAlterColumns(DataStorageDDLVo ddlVo) throws SQLException {
       List<DataObjectAttribute> attributes = ddlVo.getAttributes();
       Map columnsInfo = database.getColumnsInfo(ddlVo.getDataObject().getDefined());

        List<DataObjectAttribute> addColumns = new ArrayList<>();
       for (DataObjectAttribute attribute : attributes) {
            if(columnsInfo.get(attribute.getColumnName().toUpperCase())==null){
              //没有这个字段，需要新增
                addColumns.add(attribute);
            }
            //TODO 修改字段
        }
        ddlVo.setAddColumns(addColumns);
    }
}
