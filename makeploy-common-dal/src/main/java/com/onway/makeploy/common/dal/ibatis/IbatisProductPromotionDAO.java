/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.makeploy.common.dal.daointerface.ProductPromotionDAO;

import com.onway.makeploy.common.dal.dataobject.ProductPromotionDO;
import org.springframework.dao.DataAccessException;
import com.onway.common.lang.Money;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.makeploy.common.dal.daointerface.ProductPromotionDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_product_promotion.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisProductPromotionDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisProductPromotionDAO extends SqlMapClientDaoSupport implements ProductPromotionDAO {
	/**
	 *  Query DB table <tt>makeploy_product_promotion</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_product_promotion</tt>
	 *
	 *	@param shopId
	 *	@param productNo
	 *	@return ProductPromotionDO
	 *	@throws DataAccessException
	 */	 
    public ProductPromotionDO selectProPromotionByShopIdAndProductNo(String shopId, String productNo) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("shopId", shopId);
        param.put("productNo", productNo);

        return (ProductPromotionDO) getSqlMapClientTemplate().queryForObject("MS-PRODUCT-PROMOTION-SELECT-PRO-PROMOTION-BY-SHOP-ID-AND-PRODUCT-NO", param);

    }

	/**
	 *  Insert one <tt>ProductPromotionDO</tt> object to DB table <tt>makeploy_product_promotion</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into makeploy_product_promotion(ID,USER_ID,USER_NAME,CELL,SHOP_ID,SHOP_NAME,PRODUCT_NO,PRODUCT_NAME,INDUSTRY,STATUS,TYPE,NEED_PEOPLE,PRICE,OLD_PRICE,SOLE_COUNT,POST_WAY,MESSAGES,PRODUCT_URL,CREATER,GMT_CREATE,MODIFIER,GMT_MODIFIED,MEMO) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)</tt>
	 *
	 *	@param productPromotion
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int addProPromotion(ProductPromotionDO productPromotion) throws DataAccessException {
    	if (productPromotion == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-PRODUCT-PROMOTION-ADD-PRO-PROMOTION", productPromotion);

        return productPromotion.getId();
    }

	/**
	 *  Update DB table <tt>makeploy_product_promotion</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_product_promotion set STATUS=?, TYPE=?, PRICE=?, OLD_PRICE=?, NEED_PEOPLE=?, USER_NAME=?, CELL=?, INDUSTRY=?, POST_WAY=?, MESSAGES=?, GMT_MODIFIED=CURRENT_TIMESTAMP where ((PRODUCT_NO = ?) AND (SHOP_ID = ?))</tt>
	 *
	 *	@param status
	 *	@param type
	 *	@param price
	 *	@param oldPrice
	 *	@param needPeople
	 *	@param userName
	 *	@param cell
	 *	@param industry
	 *	@param postWay
	 *	@param messages
	 *	@param productNo
	 *	@param shopId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int changeProPromotion(String status, String type, Money price, Money oldPrice, int needPeople, String userName, String cell, String industry, String postWay, String messages, String productNo, String shopId) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("status", status);
        param.put("type", type);
        param.put("price", price);
        param.put("oldPrice", oldPrice);
        param.put("needPeople", new Integer(needPeople));
        param.put("userName", userName);
        param.put("cell", cell);
        param.put("industry", industry);
        param.put("postWay", postWay);
        param.put("messages", messages);
        param.put("productNo", productNo);
        param.put("shopId", shopId);

        return getSqlMapClientTemplate().update("MS-PRODUCT-PROMOTION-CHANGE-PRO-PROMOTION", param);
    }

	/**
	 *  Query DB table <tt>makeploy_product_promotion</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_product_promotion</tt>
	 *
	 *	@param shopId
	 *	@param productNo
	 *	@param offset
	 *	@param limit
	 *	@return List<ProductPromotionDO>
	 *	@throws DataAccessException
	 */	 
    public List<ProductPromotionDO> selectAllProPromotion(String shopId, String productNo, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("shopId", shopId);
        param.put("productNo", productNo);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-PRODUCT-PROMOTION-SELECT-ALL-PRO-PROMOTION", param);

    }

	/**
	 *  Query DB table <tt>makeploy_product_promotion</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from makeploy_product_promotion</tt>
	 *
	 *	@param shopId
	 *	@param productNo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectAllProPromotionCount(String shopId, String productNo) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("shopId", shopId);
        param.put("productNo", productNo);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-PRODUCT-PROMOTION-SELECT-ALL-PRO-PROMOTION-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Update DB table <tt>makeploy_product_promotion</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_product_promotion set STATUS=?, GMT_MODIFIED=CURRENT_TIMESTAMP where ((PRODUCT_NO = ?) AND (SHOP_ID = ?))</tt>
	 *
	 *	@param status
	 *	@param productNo
	 *	@param shopId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int passOrReturnProPromotion(String status, String productNo, String shopId) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("status", status);
        param.put("productNo", productNo);
        param.put("shopId", shopId);

        return getSqlMapClientTemplate().update("MS-PRODUCT-PROMOTION-PASS-OR-RETURN-PRO-PROMOTION", param);
    }

}