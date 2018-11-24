package com.tianyi.datacenter.server.vo;

public class DataObjectVo {

    private String dataObjectId;
    private String dataObjectName;
    private String dataObjectType;
    private String dbTableName;
    private String dataSetSql;
    private boolean isDict;

    public String getDataObjectId() {
        return dataObjectId;
    }

    public void setDataObjectId(String dataObjectId) {
        this.dataObjectId = dataObjectId;
    }

    public String getDataObjectName() {
        return dataObjectName;
    }

    public void setDataObjectName(String dataObjectName) {
        this.dataObjectName = dataObjectName;
    }

    public String getDataObjectType() {
        return dataObjectType;
    }

    public void setDataObjectType(String dataObjectType) {
        this.dataObjectType = dataObjectType;
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }

    public String getDataSetSql() {
        return dataSetSql;
    }

    public void setDataSetSql(String dataSetSql) {
        this.dataSetSql = dataSetSql;
    }

    public boolean isDict() {
        return isDict;
    }

    public void setDict(boolean dict) {
        isDict = dict;
    }
}
