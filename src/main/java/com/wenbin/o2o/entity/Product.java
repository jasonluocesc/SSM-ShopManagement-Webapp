package com.wenbin.o2o.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class Product {

    private Long productId;

    private String productName;

    private String productDesc;

    private String imgAddr;

    private String normalPrice;
    // 推广价格
    private String promotionPrice;

    private Integer priority;

    private Date createTime;

    private Date lastEditTime;
    // 0.下架 1.在前端展示系统展示
    private Integer enableStatus;

    // 图片详情图列表，跟商品是多对一的关系
    private List<ProductImg> productImgList;
    // 商品类别，一件商品仅属于一个商品类别
    private ProductCategory productCategory;
    // 店铺实体类，标明商品属于哪个店铺
    private Shop shop;
}
