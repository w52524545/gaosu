/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.daointerface;

import com.onway.makeploy.common.dal.dataobject.MsgDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>makeploy_msg</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_msg.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: MsgDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface MsgDAO {
	/**
	 *  Query DB table <tt>makeploy_msg</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_msg</tt>
	 *
	 *	@param userId
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<MsgDO>
	 *	@throws DataAccessException
	 */	 
    public List<MsgDO> selectMsgInfoByUserId(String userId, Integer startRow, Integer pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_msg</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_msg</tt>
	 *
	 *	@param userId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectMsgCountByUserId(String userId) throws DataAccessException;

	/**
	 *  Insert one <tt>MsgDO</tt> object to DB table <tt>makeploy_msg</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into makeploy_msg(ID,USER_ID,S_USER_ID,R_USER_ID,STATUS,MSG,SEND_TIME,CREATER,GMT_CREATE,MODIFIER,GMT_MODIFIED,MEMO) values (?, ?, ?, ?, '1', ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)</tt>
	 *
	 *	@param msg
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int addSystemMsg(MsgDO msg) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_msg</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_msg where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return MsgDO
	 *	@throws DataAccessException
	 */	 
    public MsgDO selectInfoById(int id) throws DataAccessException;

}