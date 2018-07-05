package com.wenbin.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class ProductCategory {

    private Long productCategoryId;

    private Long shopId;

    private String productCategoryName;

    private Integer priority;

    private Date createTime;
}
