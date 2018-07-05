package com.wenbin.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class Shop {

    private Long shopId;

    private String shopName;

    private String shopDesc;

    private String shopAddr;

    private String phone;
    //image url
    private String shopImg;

    private Integer priority;

    private Date createTime;

    private Date lastEditTime;
    // -1.不可用 0.审核中 1.可用
    private Integer enableStatus;
    // 超级管理员给店家的提醒，包括为什么审核不通过等
    private String advice;

    private Area area;

    private PersonInfo owner;

    private ShopCategory shopCategory;
}
