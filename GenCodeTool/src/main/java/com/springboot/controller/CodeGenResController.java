package com.springboot.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.springboot.entity.ColumnClass;
import com.springboot.service.CodeGenService;
import com.springboot.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述：代码生成器
 * Created by xy on 2018/10/30.
 */
@Controller
@RequestMapping("/generate")
public class CodeGenResController {

    @Autowired
    private CodeGenService codeGenService;

    @RequestMapping("hello")
    public String index() {
        return "hello";
    }

    @ResponseBody
    @RequestMapping(value = "/code/res", method = RequestMethod.POST)
    @CrossOrigin
/*    public ResponseVo generator(@RequestParam String author, @RequestParam String tableName, @RequestParam String packageName,
                                @RequestParam String diskPath, @RequestParam String tableAnnotation, @RequestParam String modelName,
                                @RequestParam List<String> suffixs, @RequestParam String objectList, @RequestParam Integer resId,
                                @RequestParam String isDic) throws Exception {*/
    public ResponseVo generator(@RequestBody JSONObject jsonParam) throws Exception {
        String author = (String) jsonParam.get("author");
        String tableName = (String) jsonParam.get("tableName");
        String packageName = (String) jsonParam.get("packageName");
        String diskPath = (String) jsonParam.get("diskPath");
        String tableAnnotation = (String) jsonParam.get("tableAnnotation");
        String modelName = (String) jsonParam.get("modelName");
        List<String> suffixs = (List<String>) jsonParam.get("suffixs");
        String objectList = (String) jsonParam.get("objectList");
        Integer resId = (Integer) jsonParam.get("resId");
        String isDic = (String) jsonParam.get("isDic");

        String changeTableName = codeGenService.replaceUnderLineAndUpperCase(tableName);

        objectList = objectList.replace("\"","\'");
        JSONObject jsonObject = JSON.parseObject(objectList);
        String listString = jsonObject.getString("list");
        List<Map<String, String>> list = JSON.parseObject(listString, new TypeReference<List>() {
        });
        List<ColumnClass> columnClassList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            ColumnClass columnClass = new ColumnClass();
            columnClass.setColumnType(list.get(i).get("jdbcType"));
            columnClass.setColumnComment(list.get(i).get("name"));
            columnClass.setColumnName(list.get(i).get("columnName"));
            columnClass.setChangeColumnName(changeTableName);
            columnClass.setResId(resId);
            columnClass.setType(list.get(i).get("type"));
            columnClass.setDicRes(list.get(i).get("dicRes"));
            columnClassList.add(columnClass);
        }
        for(String suffix:suffixs){
            codeGenService.generate(tableName, diskPath, changeTableName, author, packageName, tableAnnotation, modelName, suffix, columnClassList);
        }
        return ResponseVo.success();
    }


}