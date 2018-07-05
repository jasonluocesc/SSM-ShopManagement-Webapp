package com.wenbin.o2o.util;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex, int pageSize){
        return(pageIndex>0)?(pageIndex-1)*pageSize:0;
    }
}
