/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.daointerface;

import com.onway.makeploy.common.dal.dataobject.OptionDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>makeploy_option</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_option.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: OptionDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface OptionDAO {
	/**
	 *  Query DB table <tt>makeploy_option</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_option</tt>
	 *
	 *	@param userId
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<OptionDO>
	 *	@throws DataAccessException
	 */	 
    public List<OptionDO> selectOptionInfoByUserId(String userId, Integer startRow, Integer pageSize) throws DataAccessException;

	/**
	 *  Insert one <tt>OptionDO</tt> object to DB table <tt>makeploy_option</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into makeploy_option(USER_ID,TITLE,MESSAGE) values (?, ?, ?)</tt>
	 *
	 *	@param option
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insertOption(OptionDO option) throws DataAccessException;

	/**
	 *  Insert one <tt>OptionDO</tt> object to DB table <tt>makeploy_option</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into makeploy_option(USER_ID,MESSAGE,CREATER,GMT_CREATE,MODIFIER,GMT_MODIFIED,MEMO) values (?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)</tt>
	 *
	 *	@param option
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int addOption(OptionDO option) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_option</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_option</tt>
	 *
	 *	@param userId
	 *	@param offset
	 *	@param limit
	 *	@return List<OptionDO>
	 *	@throws DataAccessException
	 */	 
    public List<OptionDO> selectOptionByUserId(String userId, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_option</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from makeploy_option</tt>
	 *
	 *	@param userId
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long selectOptionCountByUserId(String userId) throws DataAccessException;

}