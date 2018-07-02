package com.wenbin.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class ProductCategory {

    private Long productCategoryId;
    // 该类别是属于哪个店铺的
    private Long shopId;
    // 类别名
    private String productCategoryName;
    // 权重，越大越排前显示
    private Integer priority;
    // 创建时间
    private Date createTime;
}
