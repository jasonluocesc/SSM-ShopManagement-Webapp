package com.wenbin.o2o.dto;

import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.enums.ShopStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class ShopExecution {

    private int state;

    private String stateInfo;

    private int count;

    private Shop shop;

    private List<Shop> shopList;

    public ShopExecution(){

    }
    // 失败的构造器
    public ShopExecution(ShopStateEnum stateEnum){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }
    // 成功的构造器
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.shop = shop;
    }
    // 成功的构造器
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.shopList = shopList;
    }

}
