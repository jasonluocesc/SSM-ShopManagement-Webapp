package com.wenbin.o2o.service.impl;

import com.wenbin.o2o.BaseTest;
import com.wenbin.o2o.dto.ShopExecution;
import com.wenbin.o2o.entity.Area;
import com.wenbin.o2o.entity.PersonInfo;
import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.entity.ShopCategory;
import com.wenbin.o2o.enums.ShopStateEnum;
import com.wenbin.o2o.exceptions.ShopOperationException;
import com.wenbin.o2o.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition,6,2);
        System.out.println("店铺列表数目为："+se.getShopList().size());
        System.out.println("店铺为"+se.getCount());
    }

    @Test
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺4");
        shop.setShopDesc("test4");
        shop.setShopAddr("test4");
        shop.setPhone("test4");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg=new File("G:\\Wenbin.jpg");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution se = shopService.addShop(shop,is,shopImg.getName());
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
    }

    @Test
    public void testModifyShop() throws ShopOperationException,FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(2L);
        shop.setShopName("CoCo after test");
        File shopImg = new File("G:/Wenbin.jpg");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution shopExecution = shopService.modifyShop(shop,is,shopImg.getName());
        System.out.println("新图片地址为:"+shopExecution.getShop().getShopImg());
    }
}