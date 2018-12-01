package com.tianyi.datacenter.server.controller.object;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.object.DataObjectAttributeService;
import com.tianyi.datacenter.server.service.object.DataObjectService;
import com.tianyi.datacenter.server.service.storage.DataStorageDDLService;
import com.tianyi.datacenter.server.vo.DataStorageDDLVo;
import com.tianyi.datacenter.server.vo.PageListVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据对象定义
 *
 * @author wenxinyan
 * @version 0.1
 */
@RestController
@RequestMapping("data")
public class DataObjectController {

    Logger logger = LoggerFactory.getLogger(DataObjectController.class);
    @Autowired
    private DataObjectService dataObjectService;
    @Autowired
    private DataObjectAttributeService dataObjectAttributeService;
    @Autowired
    private DataStorageDDLService dataStorageDDLService;

    @RequestMapping("/object/add")
    public ResponseVo add(@RequestBody DataObject dataObject){
        Map<String, Object> check = new HashMap<>();
        check.put("checkname", dataObject.getName());
        if(dataObjectService.listNoPage(check).size() > 0){
            return ResponseVo.fail("该名称已存在");
        }

        check = new HashMap<>();
        check.put("defined", dataObject.getDefined());
        if(dataObjectService.listNoPage(check).size() > 0){
            return ResponseVo.fail("该数据定义已存在");
        }

        dataObjectService.insert(dataObject);
        Map<String, Object> result = new HashMap<>();
        result.put("id", dataObject.getId());

//        if("true".equals(dataObject.getIsDic())){
//            DataObjectAttribute pid = this.setAttribute(dataObject.getId(), "pid", "int", 11,
//                    "字典主键", "字典主键", "数字", "规则", "yes", "not null",
//                    "no", "index");
//            dataObjectAttributeService.insert(pid);
//            DataObjectAttribute key = this.setAttribute(dataObject.getId(), "key", "varchar",
//                    50, "键", "字典数据的键", "文本", "规则", "no", "not null",
//                    "no", "index");
//            dataObjectAttributeService.insert(key);
//            DataObjectAttribute value = this.setAttribute(dataObject.getId(), "value", "varchar",
//                    50, "值", "字典数据的值", "文本", "规则", "no", "not null",
//                    "no", "index");
//            dataObjectAttributeService.insert(value);
//
//            List<DataObjectAttribute> attributeList = new ArrayList<>();
//            attributeList.add(pid);
//            attributeList.add(key);
//            attributeList.add(value);
//
//            try {
//                dataStorageDDLService.doServer(dataStorageDDLService.getRequestVo("C", dataObject, attributeList));
//            } catch (DataCenterException e) {
//                logger.error(e.toString());
//            }
//        }

        return ResponseVo.success(result);
    }

    @RequestMapping("/object/delete")
    public ResponseVo delete(@RequestBody JSONObject jsonParam){
        if(jsonParam.get("id") == null){
            return ResponseVo.fail("缺少参数");
        }
        int id = (int) jsonParam.get("id");
        dataObjectService.delete(id);

        Map<String, Object> map = new HashMap<>();
        map.put("resId", id);
        dataObjectAttributeService.delete(map);

        return ResponseVo.success();
    }

    @RequestMapping("/object")
    @Transactional
    public ResponseVo update(@RequestBody DataObject dataObject){
        dataObjectService.update(dataObject);

        if("对象".equals(dataObject.getType())){
            try {
                dataStorageDDLService.doServer(dataStorageDDLService.getRequestVo("U", dataObject, null));
            } catch (DataCenterException e) {
                logger.error(e.toString());
            }
        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("resId", dataObject.getId());
//        dataObjectAttributeService.delete(map);
//
//        Map<String, List<DataObjectAttribute>> attributeMap = (Map<String, List<DataObjectAttribute>>) JSON.parse(dataObjectAttribute);
//        List<DataObjectAttribute> attributeList = attributeMap.get("list");
//        for(DataObjectAttribute doa : attributeList){
//            doa.setResId(dataObject.getId());
//            dataObjectAttributeService.insert(doa);
//        }

        return ResponseVo.success();
    }

    @RequestMapping("/object/list")
    public ResponseVo list(@RequestBody JSONObject jsonParam, String type, String isDic, String name){
        if(jsonParam.get("type") == null || jsonParam.get("isDic") == null || jsonParam.get("name") == null || jsonParam.get("pageInfo") == null){
            return ResponseVo.fail("缺少传参");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", jsonParam.get("type"));
        map.put("isDic", jsonParam.get("isDic"));
        map.put("name", jsonParam.get("name"));
        if(jsonParam.get("tableName") != null){
            map.put("checkname", jsonParam.get("tableName"));
        }
        Map pageInfoTmp = (Map) jsonParam.get("pageInfo");
        PageListVo pageInfo = new PageListVo((int)pageInfoTmp.get("page"),(int)pageInfoTmp.get("pageSize"));

        RequestVo<Map> requestVo = new RequestVo<>(map);

        requestVo.setPageInfo(pageInfo);

        ResponseVo responseVo = ResponseVo.fail("查询对象失败！");

        if(pageInfo.getPage() == 0 && pageInfo.getPageSize() == 0){
            Map<String, Object> result = new HashMap<>();
            result.put("list", dataObjectService.listNoPage(map));

            responseVo = ResponseVo.success(result);
        } else {
            try {
                responseVo = dataObjectService.list(requestVo);
            } catch (DataCenterException e) {
                logger.error(e.toString());
            }
        }

        return responseVo;
    }

    @RequestMapping("/object/binding")
    public ResponseVo binding(@RequestBody JSONObject jsonObject){
        if(jsonObject.get("dataObjectId") == null || jsonObject.get("keyName") == null || jsonObject.get("valueName") == null){
            return ResponseVo.fail("缺少传参");
        }
        DataObject dataObject = dataObjectService.getById((int) jsonObject.get("dataObjectId"));
        if(jsonObject.get("pid") != null){
            dataObject.setPid((int) jsonObject.get("pid"));
        }
        dataObject.setDicKey((String) jsonObject.get("keyName"));
        dataObject.setDicValue((String) jsonObject.get("valueName"));

        dataObjectService.update(dataObject);

        return ResponseVo.success();
    }

//    @RequestMapping("listattr")
//    public ResponseVo listAttr(Integer page, int resId){
//        Map<String, Object> map = new HashMap<>();
//        map.put("resId", resId);
//
//        RequestVo<Map> requestVo = new RequestVo<>(map);
//
//        PageListVo pageListVo = new PageListVo(page);
//        requestVo.setPageInfo(pageListVo);
//
//        ResponseVo responseVo = ResponseVo.fail("查询对象属性失败！");
//
//        try {
//            responseVo = dataObjectAttributeService.list(requestVo);
//        } catch (DataCenterException e) {
//            logger.error(e.toString());
//        }
//
//        return responseVo;
//    }

    private DataObjectAttribute setAttribute(int resId, String columnName, String jdbcType, int length, String name,
                                             String description, String Type, String Rule, String isKey, String isNull,
                                             String isIncrement, String indexType){
        DataObjectAttribute dataObjectAttribute = new DataObjectAttribute();
        dataObjectAttribute.setResId(resId);
        dataObjectAttribute.setColumnName(columnName);
        dataObjectAttribute.setJdbcType(jdbcType);
        dataObjectAttribute.setLength(length);
        dataObjectAttribute.setName(name);
        dataObjectAttribute.setDescription(description);
        dataObjectAttribute.setType(Type);
        dataObjectAttribute.setRule(Rule);
        dataObjectAttribute.setIsKey(isKey);
        dataObjectAttribute.setIsNull(isNull);
        dataObjectAttribute.setIsIncrement(isIncrement);
        dataObjectAttribute.setIndexType(indexType);

        return dataObjectAttribute;
    }

}
