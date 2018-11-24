package com.tianyi.datacenter.common.util;

import freemarker.ext.beans.HashAdapter;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashMap;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class FreeMarkerUtilTest {

    @Test
    public void process() {


        FreeMarkerUtil.process(new HashMap(), "Retrieve");
    }
}