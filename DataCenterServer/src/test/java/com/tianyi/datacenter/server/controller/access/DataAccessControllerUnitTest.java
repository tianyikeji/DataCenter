package com.tianyi.datacenter.server.controller.access;

import com.alibaba.fastjson.JSONObject;
import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.access.DataAccessService;
import com.tianyi.datacenter.server.service.object.DataObjectAttributeService;
import com.tianyi.datacenter.server.service.object.DataObjectService;
import com.tianyi.datacenter.server.service.storage.DataStorageDMLService;
import com.tianyi.datacenter.server.vo.DataStorageDMLVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.when;


public class DataAccessControllerUnitTest {

    @InjectMocks
    DataAccessController dataAccessController = new DataAccessController();

    @Mock
    private DataAccessService dataAccessService;

    @Mock
    private DataStorageDMLService dataStorageDMLService;

    @Mock
    private DataObjectService dataObjectService;

    @Mock
    private DataObjectAttributeService dataObjectAttributeService;

    @Before
    public void setUp() throws DataCenterException {
        List<DataObjectAttribute> insertAttributes = new ArrayList<>();
        RequestVo<DataStorageDMLVo> dmlVoRequestVo = new RequestVo<>(new DataStorageDMLVo());
        MockitoAnnotations.initMocks(this);
        when(dataAccessService.integrateData(null, "C", 0, 0
                , null, insertAttributes)).thenReturn(dmlVoRequestVo);
        when(dataObjectService.getById(1)).thenReturn(null);
        when(dataObjectAttributeService.listNoPage(anyMap())).thenReturn(insertAttributes);
        when(dataStorageDMLService.doServer(dmlVoRequestVo)).thenReturn(ResponseVo.success());
    }

    @Test
    public void addBusinessData() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataObjectId", 1);
        jsonObject.put("data", new HashMap<>());
        ResponseVo responseVo = dataAccessController.add(jsonObject);
        assertTrue(responseVo.isSuccess());
    }
}