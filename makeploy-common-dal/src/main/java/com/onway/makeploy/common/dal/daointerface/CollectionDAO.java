/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.daointerface;

import com.onway.makeploy.common.dal.dataobject.CollectionDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>makeploy_collection</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_collection.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: CollectionDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface CollectionDAO {
	/**
	 *  Query DB table <tt>makeploy_collection</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_collection</tt>
	 *
	 *	@param userId
	 *	@param type
	 *	@param status
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<CollectionDO>
	 *	@throws DataAccessException
	 */	 
    public List<CollectionDO> selectCollectionInfoByUserIdAndTypeAndStatus(String userId, String type, String status, Integer startRow, Integer pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_collection</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_collection</tt>
	 *
	 *	@param userId
	 *	@param productNo
	 *	@param shopId
	 *	@return CollectionDO
	 *	@throws DataAccessException
	 */	 
    public CollectionDO selectCollectionInfoByUserIdAndProductNoAndShopId(String userId, String productNo, String shopId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_collection</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_collection</tt>
	 *
	 *	@param userId
	 *	@param type
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectCollectionCountByUserIdAndType(String userId, String type) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_collection</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_collection</tt>
	 *
	 *	@param userId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectCollectionCount(String userId) throws DataAccessException;

	/**
	 *  Insert one <tt>CollectionDO</tt> object to DB table <tt>makeploy_collection</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into makeploy_collection(ID,USER_ID,PRODUCT_NO,SHOP_ID,TYPE,STATUS,CREATER,GMT_CREATE,MODIFIER,GMT_MODIFIED,MEMO) values (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)</tt>
	 *
	 *	@param collection
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int addCollection(CollectionDO collection) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_collection</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_collection set STATUS=1, GMT_MODIFIED=CURRENT_TIMESTAMP where ((USER_ID = ?) AND (PRODUCT_NO = ?) AND (SHOP_ID = ?) AND (TYPE = 0))</tt>
	 *
	 *	@param collection
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int changeGoodsCollectionStatus(CollectionDO collection) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_collection</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_collection set STATUS=1, GMT_MODIFIED=CURRENT_TIMESTAMP where ((USER_ID = ?) AND (SHOP_ID = ?) AND (TYPE = 1))</tt>
	 *
	 *	@param collection
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int changeShopCollectionStatus(CollectionDO collection) throws DataAccessException;

}