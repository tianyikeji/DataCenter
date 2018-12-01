package com.tianyi.datacenter.server.service.storage.impl;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.storage.DataStorageDDLService;
import com.tianyi.datacenter.server.service.storage.util.DBUtil;
import com.tianyi.datacenter.server.service.storage.util.DDLUtil;
import com.tianyi.datacenter.server.vo.DataStorageDDLVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
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
    public ResponseVo doServer(RequestVo<DataStorageDDLVo> requestVo) {

        //校验服务接口
        DataStorageDDLVo ddlVo = requestVo.getRequest();
        String ddlType = ddlVo.getDdlType();
        if(StringUtils.isEmpty(ddlType)){
            return ResponseVo.fail("参数校验失败");
        }
        if (ddlVo.getDataObject() == null || ddlVo.getAttributes() == null) {
            return ResponseVo.fail("参数校验失败");
        }

        if ("U".equals(ddlType)){
            try {
                //如果是alter操作，需要处理新增字段，修改字段，删除字段的信息
                handlerAlterColumns(ddlVo);
                //获取主键信息
                ddlVo.setPkInfo(database.getPrimaryKey(ddlVo.getDataObject().getDefined()));
            } catch (SQLException e) {
                e.printStackTrace();
                return ResponseVo.fail("生成Alter语句错误");
            } catch (DataCenterException e) {
                e.printStackTrace();
                return ResponseVo.fail(e.getMessage());
            }
        }
        //创建ddl语句
        String ddlSql = null;
        try {
            ddlSql = ddlUtil.generateDDL(ddlVo);
        } catch (DataCenterException e) {
            e.printStackTrace();
            return ResponseVo.fail(e.getMessage());
        }

        //执行ddl语句
        try {
            database.executeDDL(ddlType, ddlSql);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVo.fail("执行语句异常:"+e.getCause());
        }
        return ResponseVo.success();
    }

    /**
     * 组装alter表需要的属性信息
     * @author zhouwei
     * 2018/11/29 10:30
     * @param ddlVo 修改请求接口数据
     * @exception SQLException 获取字段信息异常
    */
    private void handlerAlterColumns(DataStorageDDLVo ddlVo) throws SQLException, DataCenterException {
        List<DataObjectAttribute> attributes = ddlVo.getAttributes();
        Map<String, Map<String, Object>> schemaColumnInfo = database.getColumnsInfo(ddlVo.getDataObject().getDefined());
        //数据库有多少个字段
        int dbTableHaveColumns = schemaColumnInfo.size();

        List<DataObjectAttribute> addColumns = new ArrayList<>();
        List<DataObjectAttribute> alterColumns = new ArrayList<>();
        List<DataObjectAttribute> dropColumns = new ArrayList<>();
       /*
       以请求参数中的字段为基准，比较schema的字段信息，比较后删除schema信息，剩余的是需要删除的字段
       如果没有，说明需要新增
       如果有，需要判断是否需要修改
        */

        for (DataObjectAttribute attribute : attributes) {
            Map<String ,Object> schemaColumn = schemaColumnInfo.get(attribute.getColumnName().toUpperCase()) ;
            if (schemaColumn == null) {
                //没有这个字段，需要新增
                addColumns.add(attribute);
            } else {
                //有同名文件，判断是否需要修改
                if(needAlter(attribute, schemaColumn)) {
                    alterColumns.add(attribute);
                }
            }
            //删除处理过的字段，剩余的字段需要删除
            schemaColumnInfo.remove(attribute.getColumnName().toUpperCase());
        }
        //组装需要删除的字段
        for (Object o : schemaColumnInfo.keySet()) {
            DataObjectAttribute tmp = new DataObjectAttribute();
            tmp.setColumnName((String) o);
            dropColumns.add(tmp);
        }
        ddlVo.setAddColumns(addColumns);
        ddlVo.setAlterColumns(alterColumns);
        if(dbTableHaveColumns>0 && dbTableHaveColumns==dropColumns.size()) {
            throw new DataCenterException("应该调用删除table服务");
        }
        ddlVo.setDropColumns(dropColumns);
    }

    /**
     * 比较字段信息是否一致（字段名一定一致）
     * @author zhouwei
     * 2018/11/29 15:58
     * @param attribute 传入的字段信息
     * @param schemaColumn 数据库字段信息
     * @return 比较结果 true，一致；false，不一致
    */
    private boolean needAlter(DataObjectAttribute attribute, Map<String, Object> schemaColumn) {
        //判断是否为空
       if(("true".equals(attribute.getIsNull()))!=("YES".equals(schemaColumn.get("IS_NULLABLE")))){
           return true;
       }
       //判断类型是否一致
        if(!attribute.getJdbcType().equalsIgnoreCase(((String) schemaColumn.get("TYPE_NAME")).split(" ")[0])){
            return  true;
        }
        //判断字段说明是否一致
        if(!attribute.getDescription().equalsIgnoreCase((String) schemaColumn.get("REMARKS"))){
            return true;
        }
        //判断长度是否一致，字符和日期判断长度，数字是精度，不做校验
        if ("text".equals(attribute.getType()) || "date".equals(attribute.getJdbcType())) {
            if(attribute.getLength()!=(int)schemaColumn.get("COLUMN_SIZE")){
                return true;
            }
        }
        return false;
    }
}
