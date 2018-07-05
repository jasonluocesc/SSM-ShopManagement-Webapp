package com.wenbin.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class ProductImg {
    private Long productImgId;
    // 图片地址
    private String imgAddr;
    // 图片简介
    private String imgDesc;
    // 权重，越大越排前显示
    private Integer priority;

    private Date createTime;

    private Long productId;
}
