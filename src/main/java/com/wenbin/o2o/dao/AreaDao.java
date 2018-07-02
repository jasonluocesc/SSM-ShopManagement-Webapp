package com.wenbin.o2o.dao;

import com.wenbin.o2o.entity.Area;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface AreaDao {

    // list all the existing areas
    List<Area> queryArea();
}
