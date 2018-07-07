package com.wenbin.o2o.dao;

import com.wenbin.o2o.entity.Area;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface AreaDao {

    // 列出所有区域
    List<Area> queryArea();
}
