package com.tianyi.datacenter.server.service.storage.impl;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.storage.DataStorageDMLService;
import com.tianyi.datacenter.server.service.storage.util.DBUtil;
import com.tianyi.datacenter.server.service.storage.util.DMLUtil;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DataStorageDMLServiceUnitTest {
    @InjectMocks
    private DataStorageDMLService dataStorageDMLService = new DataStorageDMLServiceImpl();
    @Mock
    private DBUtil dbUtil;
    @Mock
    private DMLUtil dmlUtil;

    @Before
    public void setUp() throws DataCenterException {
        MockitoAnnotations.initMocks(this);


        when(dmlUtil.generateDML(any(DataStorageDMLVo.class), any())).thenReturn("");
        when(dbUtil.executeDML(anyString(), anyString())).thenReturn(1);
    }
    @Test
    public void selectDMLService() {

        DataStorageDMLVo vo = new DataStorageDMLVo();
        //操作 查询
        vo.setDmlType("R");
        //表信息
        DataObject object = new DataObject();
        object.setDefined("test");
        vo.setDataObject(object);

        RequestVo<DataStorageDMLVo> req = new RequestVo(vo);

        ResponseVo resp = null;
        try {
            resp = dataStorageDMLService.doServer(req);
        } catch (DataCenterException e) {
            e.printStackTrace();
        }

        assertTrue(resp.isSuccess());
    }

    @Test
    public void insertDMLService() throws DataCenterException {
        DataStorageDMLVo dmlVo = new DataStorageDMLVo();
        //操作类型
        dmlVo.setDmlType("C");
        //新增字段
        List<DataObjectAttribute> attributes = new ArrayList<>();
        DataObjectAttribute attribute = new DataObjectAttribute();
        attribute.setColumnName("field1");
        attribute.setType("text");
        attributes.add(attribute);
        //表信息
        DataObject dataObject = new DataObject();
        dataObject.setName("test1");
        //组装请求信息
        dmlVo.setDataObject(dataObject);
        dmlVo.setAttributes(attributes);
        RequestVo<DataStorageDMLVo> requestVo = new RequestVo<>(dmlVo);
        //调用dml服务
        ResponseVo responseVo = dataStorageDMLService.doServer(requestVo);
        assertTrue(responseVo.isSuccess());
    }
}