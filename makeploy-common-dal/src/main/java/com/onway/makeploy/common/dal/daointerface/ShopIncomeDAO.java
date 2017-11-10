/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.daointerface;

import com.onway.makeploy.common.dal.dataobject.ShopIncomeDO;
import org.springframework.dao.DataAccessException;
import com.onway.common.lang.Money;
import java.util.Date;
import java.util.List;
import java.util.Date;
import com.onway.common.lang.Money;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>makeploy_shop_income</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_shop_income.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: ShopIncomeDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface ShopIncomeDAO {
	/**
	 *  Query DB table <tt>makeploy_shop_income</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_shop_income</tt>
	 *
	 *	@param userId
	 *	@param shopId
	 *	@return ShopIncomeDO
	 *	@throws DataAccessException
	 */	 
    public ShopIncomeDO selectShopIncomeInfoByUserIdAndShopId(String userId, String shopId) throws DataAccessException;

	/**
	 *  Insert one <tt>ShopIncomeDO</tt> object to DB table <tt>makeploy_shop_income</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into makeploy_shop_income(ID,USER_ID,SHOP_ID,SELL_ACCOUT,INCOME_ACCOUT,TIME,CREATER,GMT_CREATE,MODIFIER,GMT_MODIFIED,MEMO) values (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)</tt>
	 *
	 *	@param shopIncome
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int addShopIncome(ShopIncomeDO shopIncome) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_shop_income</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_shop_income set INCOME_ACCOUT=?, GMT_MODIFIED=CURRENT_TIMESTAMP where ((USER_ID = ?) AND (SHOP_ID = ?))</tt>
	 *
	 *	@param incomeAccout
	 *	@param userId
	 *	@param shopId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int changeShopIncome(Money incomeAccout, String userId, String shopId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_shop_income</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_shop_income</tt>
	 *
	 *	@param shopId
	 *	@param startTime
	 *	@param endTime
	 *	@param offset
	 *	@param limit
	 *	@return List<ShopIncomeDO>
	 *	@throws DataAccessException
	 */	 
    public List<ShopIncomeDO> selectIncomeInfo(String shopId, Date startTime, Date endTime, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_shop_income</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from makeploy_shop_income</tt>
	 *
	 *	@param shopId
	 *	@param startTime
	 *	@param endTime
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long selectIncomeInfoCount(String shopId, Date startTime, Date endTime) throws DataAccessException;

}