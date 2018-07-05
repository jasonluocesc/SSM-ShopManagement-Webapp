package com.wenbin.o2o.service.impl;

import com.wenbin.o2o.dao.ShopDao;
import com.wenbin.o2o.dto.ShopExecution;
import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.enums.ShopStateEnum;
import com.wenbin.o2o.exceptions.ShopOperationException;
import com.wenbin.o2o.service.ShopService;
import com.wenbin.o2o.util.ImageUtil;
import com.wenbin.o2o.util.PageCalculator;
import com.wenbin.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String filename) throws ShopOperationException {

        //judge if modify image
        if(shop==null||shop.getShopId()==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else{
            try{
                if(shopImgInputStream!=null && filename!=null && !"".equals(filename)){
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if(tempShop.getShopImg()!=null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop,shopImgInputStream,filename);
                }
                shop.setLastEditTime(new Date());
                int effectNum = shopDao.updateShop(shop);
                if(effectNum<=0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);

                }else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }
            }catch (Exception e){
                throw new ShopOperationException("modifyShop error"+e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {

        //null value judge
        if(shop==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
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


        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex=PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count=shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shopList!=null){
            se.setShopList(shopList);
            se.setCount(count);
        }else{
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return se;
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
        // get shop image relative path
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName,dest);
        shop.setShopImg(shopImgAddr);
    }
}
