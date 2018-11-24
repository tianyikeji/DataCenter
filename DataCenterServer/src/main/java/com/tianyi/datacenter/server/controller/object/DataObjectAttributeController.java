package com.tianyi.datacenter.server.controller.object;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.service.object.DataObjectAttributeService;
import com.tianyi.datacenter.server.vo.PageListVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    @RequestMapping("/object/attributes")
    public ResponseVo add(DataObjectAttribute dataObjectAttribute){
        dataObjectAttributeService.insert(dataObjectAttribute);

        //TODO 调用DDL

        return ResponseVo.success();
    }

    @RequestMapping("/object/attribute/delete")
    public ResponseVo delete(int id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        dataObjectAttributeService.delete(map);

        //TODO 调用DDL

        return ResponseVo.success();
    }

    @RequestMapping("/object/attribute/update")
    public ResponseVo update(DataObjectAttribute dataObjectAttribute){
        dataObjectAttributeService.update(dataObjectAttribute);

        //TODO 调用DDL

        return ResponseVo.success();
    }

    @RequestMapping("/object/attribute/list")
    public ResponseVo list(Integer page, int resId){
        Map<String, Object> map = new HashMap<>();
        map.put("resId", resId);

        RequestVo<Map> requestVo = new RequestVo<>(map);

        PageListVo pageListVo = new PageListVo(page);
        requestVo.setPageInfo(pageListVo);

        ResponseVo responseVo = ResponseVo.fail("查询对象属性失败！");

        try {
            responseVo = dataObjectAttributeService.list(requestVo);
        } catch (DataCenterException e) {
            logger.error(e.toString());
        }

        return responseVo;
    }
}
