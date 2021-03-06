/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.onway.makeploy.common.dal.dataobject.RoleDO;

/**
 * A dao interface provides methods to access database table <tt>makeploy_role</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_role.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: RoleDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface RoleDAO {
	/**
	 *  Query DB table <tt>makeploy_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_role</tt>
	 *
	 *	@param userId
	 *	@return RoleDO
	 *	@throws DataAccessException
	 */	 
    public RoleDO selectRoleByUserId(String userId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_role</tt>
	 *
	 *	@param userId
	 *	@return RoleDO
	 *	@throws DataAccessException
	 */	 
    public RoleDO selectShopRoleByUserId(String userId) throws DataAccessException;

	/**
	 *  Insert one <tt>RoleDO</tt> object to DB table <tt>makeploy_role</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into makeploy_role(ID,USER_ID,ROLE,CREATER,GMT_CREATE,MODIFIER,GMT_MODIFIED,MEMO) values (?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)</tt>
	 *
	 *	@param role
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int addRole(RoleDO role) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_role</tt>
	 *
	 *	@param userId
	 *	@return List<RoleDO>
	 *	@throws DataAccessException
	 */	 
    public List<RoleDO> selectUserRole(String userId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_role</tt>
	 *
	 *	@param userId
	 *	@return RoleDO
	 *	@throws DataAccessException
	 */	 
    public RoleDO checkOperativeByUserId(String userId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_role</tt>
	 *
	 *	@param memo
	 *	@return RoleDO
	 *	@throws DataAccessException
	 */	 
    public RoleDO checkCommerceResult(String memo) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_role</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_role set USER_ID=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (MEMO = ?)</tt>
	 *
	 *	@param userId
	 *	@param memo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int changeComerceRole(String userId, String memo) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_role</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_role</tt>
	 *
	 *	@param userId
	 *	@return RoleDO
	 *	@throws DataAccessException
	 */	 
    public RoleDO searchCommerceId(String userId) throws DataAccessException;

}