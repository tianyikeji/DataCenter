package com.tianyi.datacenter.server.service.storage.impl;

import com.tianyi.datacenter.common.exception.DataCenterException;
import com.tianyi.datacenter.server.service.storage.DataStorageDDLService;
import com.tianyi.datacenter.server.service.storage.util.DBUtil;
import com.tianyi.datacenter.server.service.storage.util.DDLUtil;
import com.tianyi.datacenter.server.vo.DataStorageDDLVo;
import com.tianyi.datacenter.server.vo.RequestVo;
import com.tianyi.datacenter.server.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 执行DDL语句服务实现
 *
 * @author zhouwei
 * 2018/11/22 08:30
 * @version 0.1
 **/
@Service
public class DataStorageDDLServiceImpl implements DataStorageDDLService {

    @Autowired
    private DBUtil database;

    private Logger logger = LoggerFactory.getLogger(DataStorageDDLService.class);

    @Override
    public ResponseVo doServer(RequestVo<DataStorageDDLVo> requestVo) throws DataCenterException {

        //校验服务接口
        DataStorageDDLVo ddlVo = requestVo.getRequest();
        String ddlType = ddlVo.getDdlType();
        if (ddlVo.getDataObject() == null || ddlVo.getAttributes() == null) {
            //TODO
            throw new DataCenterException("");
        }

        //创建ddl语句
        String ddlSql = DDLUtil.generateDDL(ddlVo);

        //执行ddl语句
        int rtnInt = database.executeDDL(ddlType, ddlSql);
        logger.debug("ddl语句执行结果："+rtnInt);

        //todo 增加返回码
        return ResponseVo.success();
    }
}
