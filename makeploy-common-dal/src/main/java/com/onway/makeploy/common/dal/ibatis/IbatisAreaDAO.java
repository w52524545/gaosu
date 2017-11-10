/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.makeploy.common.dal.daointerface.AreaDAO;

import com.onway.makeploy.common.dal.dataobject.AreaDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.makeploy.common.dal.daointerface.AreaDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_area.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisAreaDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisAreaDAO extends SqlMapClientDaoSupport implements AreaDAO {
	/**
	 *  Query DB table <tt>makeploy_area</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_area</tt>
	 *
	 *	@return List<AreaDO>
	 *	@throws DataAccessException
	 */	 
    public List<AreaDO> selectAllFromArea() throws DataAccessException {


        return getSqlMapClientTemplate().queryForList("MS-AREA-SELECT-ALL-FROM-AREA", null);

    }

}