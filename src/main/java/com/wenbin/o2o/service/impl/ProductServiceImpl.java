package com.wenbin.o2o.service.impl;

import com.wenbin.o2o.dao.ProductDao;
import com.wenbin.o2o.dao.ProductImgDao;
import com.wenbin.o2o.dto.ImageHolder;
import com.wenbin.o2o.dto.ProductExecution;
import com.wenbin.o2o.entity.Product;
import com.wenbin.o2o.entity.ProductImg;
import com.wenbin.o2o.enums.ProductStateEnum;
import com.wenbin.o2o.exceptions.ProductOperationException;
import com.wenbin.o2o.service.ProductService;
import com.wenbin.o2o.util.ImageUtil;
import com.wenbin.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if(thumbnail!=null){
                addThumbnail(product,thumbnail);

            }
            try {
                int effectNum = productDao.insertProduct(product);
                if(effectNum<=0){
                    throw new ProductOperationException("创建商品失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品失败:"+e.getMessage());
            }
            if(productImgList!=null&&productImgList.size()>0){
                addProductImgList(product,productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if(product.getProductId()!=null&&product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            product.setLastEditTime(new Date());
            if(thumbnail!=null){
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if(tempProduct.getImgAddr()!=null){
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(tempProduct,thumbnail);
            }
            if(productImgList!=null&&productImgList.size()>0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgList);
            }
            try{
                int effectNum=productDao.updateProduct(product);
                if(effectNum<0){
                    throw new ProductOperationException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);
            }catch (Exception e){
                throw new ProductOperationException("更新商品信息失败："+e.toString());
            }
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    private void deleteProductImgList(Long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for(ProductImg productImg:productImgList){
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }


    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for(ImageHolder productImgHolder:productImgHolderList){
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if(productImgList!=null&&productImgList.size()>0){
            try {
                int effectNum = productImgDao.batchInsertProductImg(productImgList);
                if(effectNum<=0){
                    throw new ProductOperationException("创建商品图片详情失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品详情图片失败"+e.getMessage());
            }
        }
    }

    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);

    }
}
