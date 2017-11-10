/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.daointerface;

import com.onway.makeploy.common.dal.dataobject.ProductCommentDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>makeploy_product_comment</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_product_comment.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: ProductCommentDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface ProductCommentDAO {
	/**
	 *  Query DB table <tt>makeploy_product_comment</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_product_comment</tt>
	 *
	 *	@param shopId
	 *	@param productNo
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<ProductCommentDO>
	 *	@throws DataAccessException
	 */	 
    public List<ProductCommentDO> selectProductCommentByShopIdAndProductNo(String shopId, String productNo, Integer startRow, Integer pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_product_comment</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_product_comment</tt>
	 *
	 *	@param shopId
	 *	@param productNo
	 *	@return ProductCommentDO
	 *	@throws DataAccessException
	 */	 
    public ProductCommentDO selectNewProductComment(String shopId, String productNo) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_product_comment</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_product_comment</tt>
	 *
	 *	@param productNo
	 *	@param shopId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectCommentCountByShopIdAndProductNo(String productNo, String shopId) throws DataAccessException;

	/**
	 *  Insert one <tt>ProductCommentDO</tt> object to DB table <tt>makeploy_product_comment</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into makeploy_product_comment(SHOP_ID,USER_ID,PRODUCT_NO,ORDER_ID,STAR_COUNT_ONE,STAR_COUNT_TWO,STAR_COUNT_THREE,COMMENTS,CREATER,GMT_CREATE,MODIFIER,GMT_MODIFIED,MEMO) values (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)</tt>
	 *
	 *	@param productComment
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insertProductComments(ProductCommentDO productComment) throws DataAccessException;

}