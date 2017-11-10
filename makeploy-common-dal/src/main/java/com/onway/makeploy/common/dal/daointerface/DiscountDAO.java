/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.daointerface;

import com.onway.makeploy.common.dal.dataobject.DiscountDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import com.onway.common.lang.Money;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>makeploy_discount</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_discount.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: DiscountDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface DiscountDAO {
	/**
	 *  Query DB table <tt>makeploy_discount</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_discount</tt>
	 *
	 *	@param userId
	 *	@param status
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<DiscountDO>
	 *	@throws DataAccessException
	 */	 
    public List<DiscountDO> selectDiscountInfoByUserIdAndStatus(String userId, String status, Integer startRow, Integer pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_discount</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_discount</tt>
	 *
	 *	@param userId
	 *	@param status
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectDiscountCountByStatusAndUserId(String userId, String status) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_discount</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_discount</tt>
	 *
	 *	@param userId
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<DiscountDO>
	 *	@throws DataAccessException
	 */	 
    public List<DiscountDO> selectAllDiscountByUserId(String userId, Integer startRow, Integer pageSize) throws DataAccessException;

}