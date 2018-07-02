package com.wenbin.o2o.service;

import com.wenbin.o2o.BaseTest;
import com.wenbin.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;
    @Test
    public void testGetAreaList() {
        List<Area> areaList = areaService.getAreaList();
        assertEquals("南门",areaList.get(0).getAreaName());
    }
}