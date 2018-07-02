package com.wenbin.o2o.dto;

import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.enums.ShopStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Data
public class ShopExcution {

    private int state;

    private String stateInfo;

    private int count;

    private Shop shop;

    private List<Shop> shopList;

    public ShopExcution(){

    }
    public ShopExcution(ShopStateEnum stateEnum){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }
    public ShopExcution(ShopStateEnum stateEnum,Shop shop) {
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.shop = shop;
    }
    public ShopExcution(ShopStateEnum stateEnum,List<Shop> shopList) {
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.shopList = shopList;
    }

}
