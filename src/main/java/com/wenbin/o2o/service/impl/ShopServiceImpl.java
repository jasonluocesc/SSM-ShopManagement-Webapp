package com.wenbin.o2o.service.impl;

import com.wenbin.o2o.dao.ShopDao;
import com.wenbin.o2o.dto.ShopExcution;
import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.enums.ShopStateEnum;
import com.wenbin.o2o.exceptions.ShopOperationException;
import com.wenbin.o2o.service.ShopService;
import com.wenbin.o2o.util.ImageUtil;
import com.wenbin.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExcution addShop(Shop shop, InputStream shopImgInputStream,String fileName) {

        //null value judge
        if(shop==null){
            return new ShopExcution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //set initial value
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum<=0){
                throw new ShopOperationException("店铺创建失败");
            }else {
                if(shopImgInputStream !=null){
                    try {
                        addShopImg(shop, shopImgInputStream,fileName);
                    }catch (Exception e){
                        throw new ShopOperationException("addShopImg error: "+e.getMessage());
                    }
                    effectedNum=shopDao.updateShop(shop);
                    if(effectedNum<=0){
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }

        }catch (Exception e){
            throw new ShopOperationException("addShop error"+e.getMessage());
        }


        return new ShopExcution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
        // get shop image relative path
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName,dest);
        shop.setShopImg(shopImgAddr);
    }
}
