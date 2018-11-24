package com.tianyi.datacenter.server.vo;

import java.util.List;
import java.util.Map;


/**
 * 数据存储模块执行DML接口Vo
 *
 * @author zhouwei
 * @version 0.1
 */
public class DataObjectDMLVo {
    private String dataObjectId;
    private String operaType;
    private Map condition;
    private Map updateInfo;
    private String effectQnt;
    private String rtnData;

    private DataObjectVo dataObject;
    private List<DataObjectAttributeVo> attributes;
    private List<DataObjectAttributeVo> order;

    public String getDataObjectId() {
        return dataObjectId;
    }

    public void setDataObjectId(String dataObjectId) {
        this.dataObjectId = dataObjectId;
    }

    public String getOperaType() {
        return operaType;
    }

    public void setOperaType(String operaType) {
        this.operaType = operaType;
    }

    public Map getCondition() {
        return condition;
    }

    public void setCondition(Map condition) {
        this.condition = condition;
    }

    public Map getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(Map updateInfo) {
        this.updateInfo = updateInfo;
    }

    public String getEffectQnt() {
        return effectQnt;
    }

    public void setEffectQnt(String effectQnt) {
        this.effectQnt = effectQnt;
    }

    public String getRtnData() {
        return rtnData;
    }

    public void setRtnData(String rtnData) {
        this.rtnData = rtnData;
    }

    public DataObjectVo getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObjectVo dataObject) {
        this.dataObject = dataObject;
    }

    public List<DataObjectAttributeVo> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<DataObjectAttributeVo> attributes) {
        this.attributes = attributes;
    }

    public List<DataObjectAttributeVo> getOrder() {
        return order;
    }

    public void setOrder(List<DataObjectAttributeVo> order) {
        this.order = order;
    }
}
