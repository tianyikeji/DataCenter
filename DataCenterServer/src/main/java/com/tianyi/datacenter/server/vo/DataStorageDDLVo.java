package com.tianyi.datacenter.server.vo;

import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;

import java.util.List;

/**
 * 数据存储模块执行DDL接口Vo
 *
 * @author zhouwei
 * @version 0.1
 */
public class DataStorageDDLVo {
    private String ddlType;
    private DataObject dataObject;
    private List<DataObjectAttribute> attributes;

    public String getDdlType() {
        return ddlType;
    }

    public void setDdlType(String ddlType) {
        this.ddlType = ddlType;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public List<DataObjectAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<DataObjectAttribute> attributes) {
        this.attributes = attributes;
    }
}
