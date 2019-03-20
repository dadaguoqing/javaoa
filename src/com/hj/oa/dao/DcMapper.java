package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.DcCaidan;
import com.hj.oa.bean.DcCaidanVo;
import com.hj.oa.bean.DcEmpBalance;
import com.hj.oa.bean.DcEmpBalanceRecord;
import com.hj.oa.bean.DcEmpCaidan;
import com.hj.oa.bean.DcEmpDay;
import com.hj.oa.bean.DcProvider;
import com.hj.oa.bean.DingCan;
import com.hj.oa.bean.TodayOrder;

public interface DcMapper {

	//zeroth cat
	public int findEmpDcIdByDcEmpDay(DcEmpDay tempDay);
	
	//zeroth cat
	public void endAuto(int id);
	
	//zeroth cat
	public void updateAuto(DcEmpBalance curBal);
	
	public void insertAuto(DcEmpBalance dcb);
	
	public void insertAutoInfo(TodayOrder to);
	
	//供应商管理
	public void addProvider(DcProvider provider);
	
	public void deleteProvider(int id);
	
	public void updateProvider(DcProvider provider);
	
	public List<DcProvider> findAllProvider();
	
	public DcProvider findProviderById(int id);
	
	public void addMenu(DcCaidan caidan);
	
	public List<DcCaidan> findMenuByProvider(int providerId);
	
	public List<DcCaidan> findAllMenu();
	
	public void deleteMenu(int id);
	
	
	
	//订餐管理
	public void addDc(DingCan dc);
	
	public void deleteDc(int id);
	public void deleteDcCd(int dcId);
	public void deleteDcEmpDay(int dcId);
	public void deleteDcEmpCd(int dcId);
	
	public void addDcCaidan(DcCaidan cd);
	
	public void addEmpDcDay(DcEmpDay ded);
	
	public List<DcCaidan> findCaidanByDcId(int dcId);
	
	public int findDesOIdByDes(String dcId);
	
	public DcCaidan findCaidanById(int id);
	
	public DcEmpDay findEmpDcByDay(@Param("empId") int empId, @Param("dayStr") String dayStr);
	
	public DcEmpDay findEmpDcById(int id);
	
	public int findEmpIdByName(String empName);
	
	public Integer isAutoDc(int id);
	
	public List<DcEmpDay> findEmpDcByDcId(int dcId);
	
	public List<DcEmpDay> findEmpDCListByDcEmpDay(DcEmpDay dcId);
	
	public DcEmpDay findEmpDCByDcEmpDay(DcEmpDay dcId);
	
	public List<DcEmpDay> findEmpDcByDcIdUnDc(int dcId);
	
	public void updateDingcan(DingCan dc);
	
	public DingCan findDcByDay(String dayStr);
	public DingCan findDcById(int id);
	public void updateStatus(DingCan dc);
	
	public void addEmpDcCaidan(DcEmpCaidan empCaidan);
	
	public void removeEmpDcCaidan(@Param("cdId") int cdId , @Param("empDcId") int empDcId);
	
	public List<DcEmpCaidan> findEmpDcCaidanByDcId(@Param("dcId") int dcId , @Param("empDcId") int empDcId);
	
	public List<TodayOrder> findAutoOrderByEmpId(int id);
	
	public List<TodayOrder> findAutoOrderAll();
	
	public DcEmpCaidan findDescriptById(@Param("id") int id );
	
	public void updateEmpDcDay(DcEmpDay empDay);
	
	public List<DcCaidanVo> findHuizongByDcId(int id);
	
	public List<DcCaidanVo> findTotalByDcId(@Param("dcId")int dcId,@Param("id")int id);
	
	public List<DingCan> findOrderStatus(DingCan dc);
	
	public List<DingCan> findAutoOrder(int status);
	
	public String findEmpNameById(int empId);
	
	public void deleteAutoOrder(int id);
	
	public List<DcCaidanVo> findDcDetail(@Param("dcId") int dcId);
	
	public List<DcCaidanVo> findDcDetailByEmp(@Param("empId") int empId, @Param("begin") String begin, @Param("end") String end);
	
	
	//用户余额管理
	public void addEmpBalance(DcEmpBalance balance);
	public DcEmpBalance findBalanceByEmpId(int empId);
	public List<DcCaidan> findCaidanByDate(String today);
	public List<DcEmpBalance> findAllBalance();
	public List<DcEmpBalance> findTempBalance();
	public void updateEmpBalance(DcEmpBalance blance);
	
	public void addBalanceRecord(DcEmpBalanceRecord record);//添加用户账户修改记录
	public List<DcEmpBalanceRecord> findBalanceRecordByEmpId(@Param("empId") int empId, @Param("begin") String begin, @Param("end") String end);
	public List<DcEmpBalanceRecord> findBalanceRecord(@Param("begin") String begin, @Param("end") String end);
}
