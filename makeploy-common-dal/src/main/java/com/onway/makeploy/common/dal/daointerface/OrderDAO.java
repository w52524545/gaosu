/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.daointerface;

import com.onway.makeploy.common.dal.dataobject.OrderDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import com.onway.makeploy.common.dal.dataobject.ProductShow;
import java.util.Date;
import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.OrderInfo;
import java.util.Date;
import com.onway.common.lang.Money;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>makeploy_order</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_order.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: OrderDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface OrderDAO {
	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param userId
	 *	@param orderId
	 *	@param productNo
	 *	@param payStatus
	 *	@param sendGoods
	 *	@param delflag
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectOrdersByStatusAndUserId(String userId, String orderId, String productNo, String payStatus, String sendGoods, String delflag, Integer startRow, Integer pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param userId
	 *	@param orderId
	 *	@param productNo
	 *	@param payStatus
	 *	@param sendGoods
	 *	@param delflag
	 *	@param startRow
	 *	@param pageSize
	 *	@param shopId
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectOrdersByStatusAndUserIdAndShopId(String userId, String orderId, String productNo, String payStatus, String sendGoods, String delflag, Integer startRow, Integer pageSize, String shopId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param userId
	 *	@param orderId
	 *	@param productNo
	 *	@param payStatus
	 *	@param sendGoods
	 *	@param delflag
	 *	@param startRow
	 *	@param pageSize
	 *	@param shopId
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectTeamGoOrdersByStatusAndUserIdAndShopId(String userId, String orderId, String productNo, String payStatus, String sendGoods, String delflag, Integer startRow, Integer pageSize, String shopId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param userId
	 *	@param orderId
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectPartnerRankOrder(String userId, String orderId, Integer startRow, Integer pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param userId
	 *	@param orderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectPartnerRankOrderCount(String userId, String orderId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param userId
	 *	@param orderId
	 *	@param productNo
	 *	@param payStatus
	 *	@param sendGoods
	 *	@param delflag
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectOrdersCountByStatusAndUserId(String userId, String orderId, String productNo, String payStatus, String sendGoods, String delflag) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param userId
	 *	@param orderId
	 *	@param productNo
	 *	@param payStatus
	 *	@param sendGoods
	 *	@param delflag
	 *	@param shopId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectOrdersCountByStatusAndUserIdAndShopId(String userId, String orderId, String productNo, String payStatus, String sendGoods, String delflag, String shopId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param userId
	 *	@param orderId
	 *	@param productNo
	 *	@param payStatus
	 *	@param sendGoods
	 *	@param delflag
	 *	@param shopId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectTeamGoOrdersCountByStatusAndUserIdAndShopId(String userId, String orderId, String productNo, String payStatus, String sendGoods, String delflag, String shopId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param orderId
	 *	@param delflag
	 *	@return OrderDO
	 *	@throws DataAccessException
	 */	 
    public OrderDO selectOrdersByOrderId(String orderId, String delflag) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_order set USER_ID=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ORDER_ID = ?)</tt>
	 *
	 *	@param userId
	 *	@param orderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateOrderUserId(String userId, String orderId) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_order set ORDER_STATUS=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ORDER_ID = ?)</tt>
	 *
	 *	@param orderStatus
	 *	@param orderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateOrderStatusByOrderId(String orderStatus, String orderId) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_order set DELFLAG=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ORDER_ID = ?)</tt>
	 *
	 *	@param delflag
	 *	@param orderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateDelFlagByOrderId(String delflag, String orderId) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_order set ORDER_STATUS=?, SEND_GOODS=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ORDER_ID = ?)</tt>
	 *
	 *	@param orderStatus
	 *	@param sendGoods
	 *	@param orderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateOrderStatusAndOrderSendGoodsByOrderId(String orderStatus, String sendGoods, String orderId) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_order set TRANSPORT_NO=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ORDER_ID = ?)</tt>
	 *
	 *	@param transportNo
	 *	@param orderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int addTransportNoByOrderId(String transportNo, String orderId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param userId
	 *	@param delflag
	 *	@param product
	 *	@param sendGoods
	 *	@param payStatus
	 *	@param offset
	 *	@param limit
	 *	@return List<ProductShow>
	 *	@throws DataAccessException
	 */	 
    public List<ProductShow> selectOrderByProductNO(String userId, String delflag, String product, String sendGoods, String payStatus, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from makeploy_order</tt>
	 *
	 *	@param userId
	 *	@param delflag
	 *	@param product
	 *	@param sendGoods
	 *	@param payStatus
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long selectorderCountByProductNO(String userId, String delflag, String product, String sendGoods, String payStatus) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param orderId
	 *	@param delflag
	 *	@return ProductShow
	 *	@throws DataAccessException
	 */	 
    public ProductShow selectOrderAllByOrderId(String orderId, String delflag) throws DataAccessException;

	/**
	 *  Insert one <tt>OrderDO</tt> object to DB table <tt>makeploy_order</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into makeploy_order(ID,USER_ID,USER_NAME,ADDRESS_NO,ORDER_NO,ORDER_ID,ORDER_NUM,CELL,PAY_NO,PARFLAG,TRANSPORT_NO,PRODUCT_NO,EACHPRO_COUNT,PRODUCT_COUNT,EACHPRO_PRICE,POINT,SHOP_ID,ORDER_PRICE,PAY_STATUS,SEND_GOODS,PAY_TIME,PAY_WAY,ORDER_TYPE,ORDER_STATUS,LUGGAGE,DELFLAG,EXPECTED_RETURN,EXPECTED_INTEGRAL,ACTUAL_RETURN,ACTUAL_INTEGRAL,RETURN_FLG,PROVIENCE,CITY,DISTRICT,ADDR,CREATER,GMT_CREATE,MODIFIER,GMT_MODIFIED,MEMO) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 3, 4, CURRENT_TIMESTAMP, ?, 6, 0, ?, 0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)</tt>
	 *
	 *	@param order
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int creatPartnerOrder(OrderDO order) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order where SHOP_ID</tt>
	 *
	 *	@param shopId
	 *	@param delflag
	 *	@param sendGoods
	 *	@param payStatus
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectByShopID(String shopId, String delflag, String sendGoods, String payStatus) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from makeploy_order where SHOP_ID</tt>
	 *
	 *	@param shopId
	 *	@param delflag
	 *	@param sendGoods
	 *	@param payStatus
	 *	@param orderStatus
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectCountByShopID(String shopId, String delflag, String sendGoods, String payStatus, String orderStatus) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param shopId
	 *	@param orderId
	 *	@param delflag
	 *	@param sendGoods
	 *	@param payStatus
	 *	@param orderStatus
	 *	@param startDate
	 *	@param endDate
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<OrderInfo>
	 *	@throws DataAccessException
	 */	 
    public List<OrderInfo> selectByShopIDSearchBox(String shopId, String orderId, String delflag, String sendGoods, String payStatus, String orderStatus, Date startDate, Date endDate, Integer startRow, Integer pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from makeploy_order where SHOP_ID</tt>
	 *
	 *	@param shopId
	 *	@param orderId
	 *	@param delflag
	 *	@param sendGoods
	 *	@param payStatus
	 *	@param orderStatus
	 *	@param startDate
	 *	@param endDate
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectCountByShopIDSearchBox(String shopId, String orderId, String delflag, String sendGoods, String payStatus, String orderStatus, Date startDate, Date endDate) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param shopId
	 *	@param payWay
	 *	@param start
	 *	@param end
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectIncome(String shopId, String payWay, Date start, Date end) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order where ((PAY_STATUS = '3') AND (SEND_GOODS = '4') AND (ORDER_TYPE = '1') AND (ORDER_STATUS = '0') AND (RETURN_FLG = '0'))</tt>
	 *
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectSuccees(Integer startRow, Integer pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from makeploy_order where ((PAY_STATUS = '3') AND (SEND_GOODS = '4') AND (ORDER_TYPE = '1') AND (ORDER_STATUS = '0') AND (RETURN_FLG = '0'))</tt>
	 *
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int selectSucceesCount() throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_order set ACTUAL_RETURN=?, ACTUAL_INTEGRAL=?, RETURN_FLG=?, GMT_MODIFIED=CURRENT_TIMESTAMP where ((ORDER_ID = ?) AND (ORDER_NO = ?))</tt>
	 *
	 *	@param actualReturn
	 *	@param actualIntegral
	 *	@param returnFlg
	 *	@param orderId
	 *	@param orderNo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateReturn(Money actualReturn, long actualIntegral, String returnFlg, String orderId, String orderNo) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order where ((ORDER_TYPE = 4) AND (USER_ID = ?))</tt>
	 *
	 *	@param userId
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> partnerOrderByUserId(String userId) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_order set USER_NAME=?, CELL=?, PROVIENCE=?, CITY=?, DISTRICT=?, ADDR=?, ORDER_PRICE=?, LUGGAGE=?, GMT_MODIFIED=CURRENT_TIMESTAMP where ((ORDER_ID = ?) AND (DELFLAG = '0'))</tt>
	 *
	 *	@param userName
	 *	@param cell
	 *	@param provience
	 *	@param city
	 *	@param district
	 *	@param addr
	 *	@param orderPrice
	 *	@param luggage
	 *	@param orderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateInfoBySendsGoods(String userName, String cell, String provience, String city, String district, String addr, Money orderPrice, Money luggage, String orderId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param weekAgo
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectConfirm(String weekAgo) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param minuteAgo
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectCancle(String minuteAgo) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_order set ORDER_STATUS=? where (ORDER_ID = ?)</tt>
	 *
	 *	@param orderStatus
	 *	@param orderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int cancleOrder(String orderStatus, String orderId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param shopId
	 *	@param payWay
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectMounthOrder(String shopId, String payWay) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param shopId
	 *	@param payWay
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectWeekOrder(String shopId, String payWay) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param productNo
	 *	@param shopId
	 *	@param praflag
	 *	@param payWay
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectMounthProductOrder(String productNo, String shopId, String praflag, String payWay) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param productNo
	 *	@param shopId
	 *	@param praflag
	 *	@param payWay
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectWeekProductOrder(String productNo, String shopId, String praflag, String payWay) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param productNo
	 *	@param shopId
	 *	@param praflag
	 *	@param payWay
	 *	@param start
	 *	@param end
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectProductIncome(String productNo, String shopId, String praflag, String payWay, Date start, Date end) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@return OrderDO
	 *	@throws DataAccessException
	 */	 
    public OrderDO selectLastPartnerOrder() throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_order set CREATER=? where (ORDER_ID = ?)</tt>
	 *
	 *	@param creater
	 *	@param orderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int insertCreate(String creater, String orderId) throws DataAccessException;

	/**
	 *  Query DB table <tt>makeploy_order</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_order</tt>
	 *
	 *	@param orderNum
	 *	@return List<OrderDO>
	 *	@throws DataAccessException
	 */	 
    public List<OrderDO> selectBigThan(Integer orderNum) throws DataAccessException;

	/**
	 *  Update DB table <tt>makeploy_order</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update makeploy_order set ORDER_NUM=? where (ORDER_ID = ?)</tt>
	 *
	 *	@param orderNum
	 *	@param orderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int changeOrderNum(int orderNum, String orderId) throws DataAccessException;

}