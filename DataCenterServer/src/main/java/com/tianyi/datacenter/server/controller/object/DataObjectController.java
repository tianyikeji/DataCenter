package com.tianyi.datacenter.server.controller.object;

import com.alibaba.fastjson.JSON;
import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.object.DataObjectAttributeService;
import com.tianyi.datacenter.server.service.object.DataObjectService;
import com.tianyi.datacenter.server.vo.PageListVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/object/add")
    public ResponseVo add(DataObject dataObject, String dataObjectAttribute){
        dataObjectService.insert(dataObject);

        Map<String, List<DataObjectAttribute>> attributeMap = (Map<String, List<DataObjectAttribute>>) JSON.parse(dataObjectAttribute);
        List<DataObjectAttribute> attributeList = attributeMap.get("list");
        for(DataObjectAttribute doa : attributeList){
            doa.setResId(dataObject.getId());
            dataObjectAttributeService.insert(doa);
        }

        //TODO 调用DDL

        return ResponseVo.success();
    }

    @RequestMapping("/object/delete")
    public ResponseVo delete(int id){
        dataObjectService.delete(id);

        Map<String, Object> map = new HashMap<>();
        map.put("resId", id);
        dataObjectAttributeService.delete(map);

        return ResponseVo.success();
    }

    @RequestMapping("object")
    @Transactional
    public ResponseVo update(DataObject dataObject){
        dataObjectService.update(dataObject);

        //TODO 调用DDL

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
    public ResponseVo list(Integer page, String type, String isDic, String name){
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("isDic", isDic);
        map.put("name", name);

        RequestVo<Map> requestVo = new RequestVo<>(map);

        PageListVo pageListVo = new PageListVo(page);
        requestVo.setPageInfo(pageListVo);

        ResponseVo responseVo = ResponseVo.fail("查询对象失败！");

        try {
            responseVo = dataObjectService.list(requestVo);
        } catch (DataCenterException e) {
            logger.error(e.toString());
        }

        return responseVo;
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
}
