package com.tianyi.datacenter.server.controller.object;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.object.DataObjectAttributeService;
import com.tianyi.datacenter.server.service.object.DataObjectService;
import com.tianyi.datacenter.server.service.storage.DataStorageDDLService;
import com.tianyi.datacenter.server.vo.PageListVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenxinyan on 2018/11/22.
 */
@RestController
@RequestMapping("data")
public class DataObjectAttributeController {

    Logger logger = LoggerFactory.getLogger(DataObjectAttributeController.class);
    @Autowired
    private DataObjectAttributeService dataObjectAttributeService;
    @Autowired
    private DataObjectService dataObjectService;
    @Autowired
    private DataStorageDDLService dataStorageDDLService;

    @RequestMapping("/object/attributes")
    @Transactional
    public ResponseVo add(@RequestBody JSONObject jsonObject){
        List<Map<String, String>> requestList = (List<Map<String, String>>) jsonObject.get("list");
        int resId = (int) jsonObject.get("objectId");
        List<DataObjectAttribute> attributeList = new ArrayList<>();
        for(Map<String, String> map : requestList){
            DataObjectAttribute attribute = new DataObjectAttribute();
            if(StringUtils.isEmpty(map.get("id"))){
                attribute = this.setAttribute(resId, map.get("columnName"), map.get("jdbcType"),
                        Integer.parseInt(map.get("length")), map.get("name"), map.get("description"), map.get("type"),
                        map.get("dicRes"), map.get("rule"), map.get("isKey"), map.get("isNull"), map.get("isIncrement"), map.get("indexType"));
            } else {
                attribute = dataObjectAttributeService.getById(Integer.parseInt(map.get("id")));
                attribute.setResId(resId);
            }

            attributeList.add(attribute);
        }

        DataObject dataObject = dataObjectService.getById(resId);
        Map<String, Object> map = new HashMap<>();
        map.put("resId", resId);
        String ddlType = "U";

        List<DataObjectAttribute> attributes = dataObjectAttributeService.listNoPage(map);
        if(attributes.size() == 0 || attributes == null){
            ddlType = "C";
        } else {
            List<String> columnList = this.getColumnList(attributes);
            List<String> nameList = this.getNameList(attributes);
            for(DataObjectAttribute doa : attributeList){
                if(columnList.contains(doa.getColumnName())){
                    return ResponseVo.fail("columnName重复");
                }
                if(nameList.contains(doa.getName())){
                    return ResponseVo.fail("name重复");
                }
            }
        }

        List<Integer> ids = new ArrayList<>();
        for(DataObjectAttribute doa : attributeList){
            dataObjectAttributeService.insert(doa);
            ids.add(doa.getId());
        }

        if("对象".equals(dataObject.getType())){
            try {
                ResponseVo  responseVo = dataStorageDDLService.doServer(dataStorageDDLService.getRequestVo(ddlType, dataObject, attributeList));
                logger.debug("调用DDL是否成功:" + responseVo.isSuccess());
            } catch (DataCenterException e) {
                logger.error(e.toString());
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("id", ids);

        return ResponseVo.success(result);
    }

    @RequestMapping("/object/attribute/delete")
    public ResponseVo delete(@RequestBody JSONObject jsonParam){
        Map<String, Object> map = new HashMap<>();
        int id = (int) jsonParam.get("id");
        map.put("id", id);

        int resId = dataObjectAttributeService.getById(id).getResId();

        dataObjectAttributeService.delete(map);

        DataObject dataObject = dataObjectService.getById(resId);
        if("对象".equals(dataObject.getType())){
            Map<String, Object> param = new HashMap<>();
            param.put("resId", resId);
            List<DataObjectAttribute> attributeList = dataObjectAttributeService.listNoPage(param);
            try {
                dataStorageDDLService.doServer(dataStorageDDLService.getRequestVo("U", dataObject, attributeList));
            } catch (DataCenterException e) {
                logger.error(e.toString());
            }
        }

        return ResponseVo.success();
    }

    @RequestMapping("/object/attribute/update")
    public ResponseVo update(@RequestBody DataObjectAttribute dataObjectAttribute){
        dataObjectAttributeService.update(dataObjectAttribute);

        DataObject dataObject = dataObjectService.getById(dataObjectAttribute.getResId());
        if("对象".equals(dataObject.getType())){
            Map<String, Object> map = new HashMap<>();
            map.put("resId", dataObjectAttribute.getResId());
            List<DataObjectAttribute> attributeList = dataObjectAttributeService.listNoPage(map);
            try {
                dataStorageDDLService.doServer(dataStorageDDLService.getRequestVo("U", dataObject, attributeList));
            } catch (DataCenterException e) {
                logger.error(e.toString());
            }
        }

        return ResponseVo.success();
    }

    @RequestMapping("/object/attribute/list")
    public ResponseVo list(@RequestBody JSONObject jsonParam){
        Map pageInfoTmp = (Map) jsonParam.get("pageInfo");
        PageListVo pageInfo = new PageListVo(0,0);
        if(pageInfoTmp!=null){
            pageInfo = new PageListVo((int) pageInfoTmp.get("page"), (int) pageInfoTmp.get("pageSize"));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("resId", jsonParam.get("resId"));

        RequestVo<Map> requestVo = new RequestVo<>(map);

        requestVo.setPageInfo(pageInfo);

        ResponseVo responseVo = ResponseVo.fail("查询对象属性失败！");

        if(pageInfo.getPage() == 0 && pageInfo.getPageSize() == 0){
            Map<String, Object> result = new HashMap<>();
            result.put("list", dataObjectAttributeService.listNoPage(map));

            responseVo = ResponseVo.success(result);
        } else {
            try {
                responseVo = dataObjectAttributeService.list(requestVo);
            } catch (DataCenterException e) {
                logger.error(e.toString());
            }
        }

        return responseVo;
    }

    private List<String> getColumnList(List<DataObjectAttribute> attributeList){
        List<String> columnList = new ArrayList<>();

        for(DataObjectAttribute doa : attributeList){
            columnList.add(doa.getColumnName());
        }

        return columnList;
    }

    private List<String> getNameList(List<DataObjectAttribute> attributeList){
        List<String> nameList = new ArrayList<>();

        for(DataObjectAttribute doa : attributeList){
            nameList.add(doa.getName());
        }

        return nameList;
    }

    private DataObjectAttribute setAttribute(int resId, String columnName, String jdbcType, int length, String name,
                                             String description, String type, String dicRes, String rule, String isKey,
                                             String isNull, String isIncrement, String indexType){
        DataObjectAttribute dataObjectAttribute = new DataObjectAttribute();
        dataObjectAttribute.setResId(resId);
        dataObjectAttribute.setColumnName(columnName);
        dataObjectAttribute.setJdbcType(jdbcType);
        dataObjectAttribute.setLength(length);
        dataObjectAttribute.setName(name);
        dataObjectAttribute.setDescription(description);
        dataObjectAttribute.setType(type);
        dataObjectAttribute.setDicRes(dicRes);
        dataObjectAttribute.setRule(rule);
        dataObjectAttribute.setIsKey(isKey);
        dataObjectAttribute.setIsNull(isNull);
        dataObjectAttribute.setIsIncrement(isIncrement);
        dataObjectAttribute.setIndexType(indexType);

        return dataObjectAttribute;
    }
}
