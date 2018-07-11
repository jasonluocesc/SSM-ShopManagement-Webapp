package com.wenbin.o2o.service;

import com.wenbin.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
public interface HeadLineService {

    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
