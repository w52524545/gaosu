/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.daointerface;

import com.onway.makeploy.common.dal.dataobject.BorrowMoneyDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import com.onway.common.lang.Money;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>makeploy_borrow_money</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_borrow_money.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: BorrowMoneyDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface BorrowMoneyDAO {
	/**
	 *  Query DB table <tt>makeploy_borrow_money</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_borrow_money</tt>
	 *
	 *	@param state
	 *	@param offset
	 *	@param limit
	 *	@return List<BorrowMoneyDO>
	 *	@throws DataAccessException
	 */	 
    public List<BorrowMoneyDO> selectInfoByState(String state, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_borrow_money</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from makeploy_borrow_money</tt>
	 *
	 *	@param state
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long selectInfoCount(String state) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_borrow_money</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_borrow_money where (BORROW_ID = ?)</tt>
	 *
	 *	@param borrowId
	 *	@return BorrowMoneyDO
	 *	@throws DataAccessException
	 */	 
    public BorrowMoneyDO selectByBorrowId(String borrowId) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_borrow_money</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_borrow_money set BORROW_STATE=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (BORROW_ID = ?)</tt>
	 *
	 *	@param borrowState
	 *	@param borrowId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int modifyState(String borrowState, String borrowId) throws DataAccessException;

}