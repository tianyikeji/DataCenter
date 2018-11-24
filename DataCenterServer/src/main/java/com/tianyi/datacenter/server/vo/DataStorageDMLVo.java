package com.tianyi.datacenter.server.vo;

import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;

import java.util.List;
import java.util.Map;

/**
 * //TODO 说明
 *
 * @author zhouwei
 * 2018/11/20 14:47
 * @version 0.1
 **/
public class DataStorageDMLVo {
    private String dmlSql;

    private String dmlType;
    private DataObject dataObject;
    private Map condition;
    private List<DataObjectAttribute> attributes;
    private Map<String, Object> updateInfo;

    public String getDmlSql() {
        return dmlSql;
    }

    public void setDmlSql(String dmlSql) {
        this.dmlSql = dmlSql;
    }

    public String getDmlType() {
        return dmlType;
    }

    public void setDmlType(String dmlType) {
        this.dmlType = dmlType;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map condition) {
        this.condition = condition;
    }

    public List<DataObjectAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<DataObjectAttribute> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(Map<String, Object> updateINfo) {
        this.updateInfo = updateINfo;
    }
}
