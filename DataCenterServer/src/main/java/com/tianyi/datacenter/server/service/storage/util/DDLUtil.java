package com.tianyi.datacenter.server.service.storage.util;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.common.util.FreeMarkerUtil;
import com.tianyi.datacenter.server.entity.object.DataObject;
import com.tianyi.datacenter.server.entity.object.DataObjectAttribute;
import com.tianyi.datacenter.server.vo.DataStorageDDLVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tianyi.datacenter.common.exception.DataCenterException.DC_DO_8901;
import static com.tianyi.datacenter.common.exception.DataCenterException.DC_DO_8901_MSG;

@Component
public class DDLUtil {

    private Logger logger = LoggerFactory.getLogger(DBUtil.class);

    /**
     * 根据DDL对象生成DDL语句
     *
     * @param ddlVo ddl信息
     * @return 查询sql语句
     * @author zhouwei
     * 2018/11/19 15:26
     */
    public String generateDDL(DataStorageDDLVo ddlVo) throws DataCenterException {
        String ddlSql;

        switch (ddlVo.getDdlType()) {
            case "C":
                //创建表操作
                String ftlName = "Create";
                ddlSql = generateCreateTableSql(ddlVo.getDataObject(), ddlVo.getAttributes(), ftlName);
                if (ddlSql == null) {
                    //TODO
                    throw new DataCenterException("", "");
                }
                break;
            case "U":
                ftlName = "Alter";
                //TODO 完成修改表的逻辑
                ddlSql = generateAlterTableSql(ddlVo.getDataObject(), ddlVo.getAddColumns(), ddlVo.getAlterColumns(),
                        ddlVo.getDropColumns(), ftlName, ddlVo.getPkInfo());
                break;
            default:
                throw new DataCenterException(DC_DO_8901, DC_DO_8901_MSG);
        }
        //删除回车换行符
        ddlSql = ddlSql.replaceAll("[\r\n]", "").replaceAll("\\s{3,}?", " ");
        logger.debug("生成create table语句:" + ddlSql);
        return ddlSql;
    }

    /**
     * 生成修改表的ddl语句
     * @author zhouwei
     * 2018/11/28 19:10
     * @param dataObject 表信息
     * @param addColumns 修改字段信息
     * @param alterColumns 修改字段信息
     * @param dropColumns 删除字段信息
     * @param ftlName 模板名称
     * @param pkInfo 主键信息
     * @return ddl修改表信息sql
    */
    private String generateAlterTableSql(DataObject dataObject, List<DataObjectAttribute> addColumns,
                                         List<DataObjectAttribute> alterColumns,
                                         List<DataObjectAttribute> dropColumns, String ftlName,
                                         Map<String, Object> pkInfo) {
        Map<String, Object> ftlRoot = new HashMap<>();
        ftlRoot.put("addColumns", addColumns);
        ftlRoot.put("alterColumns", alterColumns);
        ftlRoot.put("dropColumns", dropColumns);
        ftlRoot.put("dataObject", dataObject);
        ftlRoot.put("pkInfo", pkInfo);

        return FreeMarkerUtil.process(ftlRoot, ftlName);
    }

    /**
     * 生成创建表的ddl语句
     * 不支持联合主键，联合索引，触发器
     *
     * @param dataObject 数据对象
     * @param attributes 数据对象属性列表
     * @param ftlName 模板名称
     * @return String 查询sql
     * @author zhouwei
     * 2018/11/19 16:39
     */
    private String generateCreateTableSql(DataObject dataObject,
                                                 List<DataObjectAttribute> attributes, String ftlName) {
        Map<String, Object> ftlRoot = new HashMap<>();
        ftlRoot.put("columns", attributes);
        ftlRoot.put("dataObject", dataObject);

        return FreeMarkerUtil.process(ftlRoot, ftlName);
    }
}
