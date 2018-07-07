package com.wenbin.o2o.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class WechatAuth {

    private Long wechatAuthId;

    private String openId;

    private Date createTime;

    private PersonInfo personInfo;
}
