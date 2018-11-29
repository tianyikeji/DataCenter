package com.tianyi.datacenter.server.service.storage.util;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.PageListVo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DMLUtilUnitTest {
    private DMLUtil dmlUtil = new DMLUtil();

    @Test
    public void generateDelete() {
        DataStorageDMLVo dmlVo = new DataStorageDMLVo();

        //删除
        dmlVo.setDmlType("D");
        //表信息
        DataObject object = new DataObject();
        object.setDefined("test");
        dmlVo.setDataObject(object);
        //条件
        dmlVo.setCondition(generateConditions());

        String sql = null;
        try {
            sql = dmlUtil.generateDML(dmlVo, null);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        assertEquals("DELETE FROM test WHERE field3 > 'value3'  and field4 =value4  and field4 like '%value4%' and " +
                "field4 < 'value4'  and field4 >=value4  and field4 <=value4 ", sql);

    }

    @Test
    public void generateInsert() {
        DataStorageDMLVo dmlVo = new DataStorageDMLVo();

        //插入
        dmlVo.setDmlType("C");
        //表信息
        DataObject dataObject = new DataObject();
        dataObject.setDefined("test");
        //字段
        List<DataObjectAttribute> attributes = new ArrayList<>();
        DataObjectAttribute attribute = new DataObjectAttribute();
        attribute.setColumnName("field1");
        attribute.setType("text");
        attribute.setValue("field1");
        attributes.add(attribute);

        attribute = new DataObjectAttribute();
        attribute.setColumnName("field2");
        attribute.setType("text");
        attribute.setValue("field2");
        attributes.add(attribute);

        dmlVo.setAttributes(attributes);
        dmlVo.setDataObject(dataObject);

        String sql = "";
        try {
            sql = dmlUtil.generateDML(dmlVo, null);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        assertEquals("INSERT INTO test ( field1, field2 ) VALUES (   'field1' ,   'field2'  )", sql);
    }
    @Test
    public void generateQueryWithOutCondition(){

        DataStorageDMLVo dmlVo = new DataStorageDMLVo();

        //查询类型
        dmlVo.setDmlType("R");

        //表名
        DataObject dataObject = new DataObject();
        dataObject.setDefined("test");
        dmlVo.setDataObject(dataObject);

        //查询字段
        List<DataObjectAttribute> attributes = new ArrayList<>();
        DataObjectAttribute attribute = new DataObjectAttribute();
        attribute.setColumnName("fieldA");
        attributes.add(attribute);
        attribute = new DataObjectAttribute();
        attribute.setColumnName("fieldB");
        attributes.add(attribute);
        attribute = new DataObjectAttribute();
        attribute.setColumnName("fieldC");
        attributes.add(attribute);
        dmlVo.setAttributes(attributes);

        //分页信息
        PageListVo pageInfo = new PageListVo(1, 50);

        //调用生成sql工具类
        String sql = null;
        try {
            sql = dmlUtil.generateDML(dmlVo, pageInfo);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        assertEquals("SELECT DISTINCT   fieldA,  fieldB,  fieldC FROM test LIMIT 0,50", sql);
    }
    @Test
    public void generateQuery() {

        DataStorageDMLVo dmlVo = new DataStorageDMLVo();

        //查询类型
        dmlVo.setDmlType("R");

        //条件字段
        List<DataObjectAttribute> conditions = new ArrayList<>();
        DataObjectAttribute tmp = new DataObjectAttribute();

        tmp.setColumnName("field1");
        tmp.setJdbcType("text");
        tmp.setValue("value1");
        conditions.add(tmp);

        tmp = new DataObjectAttribute();
        tmp.setColumnName("field2");
        tmp.setJdbcType("text");
        tmp.setValue("value2");
        conditions.add(tmp);

        dmlVo.setCondition(conditions);

        //表名
        DataObject dataObject = new DataObject();
        dataObject.setDefined("test");
        dmlVo.setDataObject(dataObject);

        //查询字段
        List<DataObjectAttribute> attributes = new ArrayList<>();
        DataObjectAttribute attribute = new DataObjectAttribute();
        attribute.setColumnName("fieldA");
        attributes.add(attribute);
        attribute = new DataObjectAttribute();
        attribute.setColumnName("fieldB");
        attributes.add(attribute);
        attribute = new DataObjectAttribute();
        attribute.setColumnName("fieldC");
        attributes.add(attribute);
        dmlVo.setAttributes(attributes);

        //分页信息
        PageListVo pageInfo = new PageListVo(2, 50);

        //调用生成sql工具类
        String sql = null;
        try {
            sql = dmlUtil.generateDML(dmlVo, pageInfo);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        assertEquals("SELECT DISTINCT   fieldA,  fieldB,  fieldC FROM test WHERE  field1 = 'value1'  and field2 = " +
                "'value2'  LIMIT 50,50", sql);
    }


    @Test
    public void generateUpdate() {

        DataStorageDMLVo dmlVo = new DataStorageDMLVo();

        //插入
        dmlVo.setDmlType("U");
        //表信息
        DataObject dataObject = new DataObject();
        dataObject.setDefined("test");
        dmlVo.setDataObject(dataObject);
        //字段
        List<DataObjectAttribute> updateAttributes = new ArrayList<>();
        DataObjectAttribute tmp = new DataObjectAttribute();

        tmp.setColumnName("field1");
        tmp.setType("text");
        tmp.setValue("value1");
        updateAttributes.add(tmp);

        tmp = new DataObjectAttribute();
        tmp.setColumnName("field2");
        tmp.setType("text");
        tmp.setValue("value2");
        updateAttributes.add(tmp);

        dmlVo.setAttributes(updateAttributes);

        dmlVo.setCondition(generateConditions());

        String sql = "";
        try {
            sql = dmlUtil.generateDML(dmlVo, null);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

//        System.out.println(sql);
        assertEquals("UPDATE test SET field1 = 'value1', field2 = 'value2' WHERE field3 > 'value3' and field4 " +
                "=value4 and field4 like '%value4%'  and field4 < 'value4' and field4 >=value4 and field4 <=value4", sql);
    }

    private List<DataObjectAttribute> generateConditions() {
        List<DataObjectAttribute> conditionAttributes = new ArrayList<>();
        //大于字符
        DataObjectAttribute tmp = new DataObjectAttribute();
        tmp.setColumnName("field3");
        tmp.setType("text");
        tmp.setValue("value3");
        tmp.setOper("greate than");
        conditionAttributes.add(tmp);

        //没有运算属性，默认=
        tmp = new DataObjectAttribute();
        tmp.setColumnName("field4");
        tmp.setType("int");
        tmp.setValue("value4");
        conditionAttributes.add(tmp);

        //like 字符
        tmp = new DataObjectAttribute();
        tmp.setColumnName("field4");
        tmp.setType("text");
        tmp.setValue("value4");
        tmp.setOper("like");
        conditionAttributes.add(tmp);

        //小于字符
        tmp = new DataObjectAttribute();
        tmp.setColumnName("field4");
        tmp.setValue("value4");
        tmp.setOper("less than");
        conditionAttributes.add(tmp);

        //大于等于
        tmp = new DataObjectAttribute();
        tmp.setColumnName("field4");
        tmp.setType("int");
        tmp.setValue("value4");
        tmp.setOper("greate than equals");
        conditionAttributes.add(tmp);

        //小于等于
        tmp = new DataObjectAttribute();
        tmp.setColumnName("field4");
        tmp.setType("int");
        tmp.setOper("less than equals");
        tmp.setValue("value4");
        conditionAttributes.add(tmp);

        return conditionAttributes;
    }
}