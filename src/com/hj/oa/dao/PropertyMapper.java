package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.ParamBean;
import com.hj.oa.bean.ZcPlace;
import com.hj.oa.bean.ZcPropCg;
import com.hj.oa.bean.ZcPropLog;
import com.hj.oa.bean.ZcPropLy;
import com.hj.oa.bean.ZcPropTmp;
import com.hj.oa.bean.ZcPropWb;
import com.hj.oa.bean.ZcPropWbVo;
import com.hj.oa.bean.ZcPropZy;
import com.hj.oa.bean.ZcProperty;
import com.hj.oa.bean.ZcType;

public interface PropertyMapper {
	
	public void addProperty(ZcProperty prop);
	
	public void updateStatus(int id);
	
	public void addLyProp(@Param("lyId") int lyId, @Param("name") String name, @Param("bz") String bz);
	
	public void addCgProp(@Param("cgId") int cgId, @Param("name") String name, @Param("bz") String bz,@Param("unit")char unit,@Param("price")double price,@Param("num")int num,@Param("money")double money, @Param("brand") String brand, @Param("type") int type, @Param("place") int place);
	
	public void addZyProp(@Param("zyId") int zyId, @Param("propId") int propId);
	
	public void addLy(ZcPropLy ly);
	
	public void addCg(ZcPropCg cg);
	
	public void addZy(ZcPropZy zy);
	
	public void addWb(ZcPropWb wb);
	
	public void addPlace(ZcPlace place);
	
	public void deletePlaceById(int id);
	
	public int findStockById(int id);
	
	public List<ZcProperty> findAddRecodeAll();
	
	public List<ZcPropLog> findPropAll(ParamBean pb);
	
	public List<ZcPropLy> findGrantAll();
	
	public List<ZcPropWbVo> findRepairAll();
	
	public void addType(ZcType type);
	
	public List<ZcType> findAllType();
	
	public List<ZcType> findFirstTypes();
	
	public List<ZcType> findSecondTypes();
	
	public List<ZcType> findSubTypes(int pId);
	
	public ZcType findTypeById(int id);
	
	public ZcProperty findPurchaseByCgId(int id);
	
	public String findPlaceById(int id);
	
	//public List<ZcType> findTypes(Integer pId, Integer id);
	
	public List<ZcProperty> findAll();
	
	public List<ZcProperty> findByCond(@Param("showType") int showType, @Param("deptId") int deptId, @Param("empId") int empId,
			@Param("placeId") int placeId, @Param("typeId") int typeId, @Param("code") String code);
	
	
	public List<ZcProperty> findByCond2();
	public List<ZcProperty> findPropByLy(int id);
	
	public List<ZcProperty> findPropByCg(int id);
	
	public ZcProperty findPropByCgAndId(@Param("id") int id);
	
	public List<ZcProperty> findPropByZy(int id);
	
	public ZcProperty findPropById(int id);
	
	public List<ZcProperty> findAllUnused();
	
	public List<ZcProperty> findUnusedProps(@Param("placeId") int placeId, @Param("typeId") int typeId);
	
	//查询待发放的资产
	//public List<ZcProperty> findPropForLy();
	
	//查询待归还的资产
	public List<ZcProperty> findPropForGh();
	
	public List<ZcProperty> findAutoStoreRecord(ParamBean pb);
	
	//查询用户的资产
	public List<ZcProperty> findPropByEmpId(int id);
	
	public List<ZcPlace> findAllPlace();
	
	public List<ZcPropLog> findPropLog(int propId);
	
	public List<ZcPropLog> findEmpPropLog(@Param("empId") int empId, @Param("desc") String desc);
	
	public List<ZcProperty> findUnusedPropByType(@Param("pId") Integer pId, @Param("typeId") Integer typeId);
	
	public List<ZcPropLy> findMyLySp(int empId);
	
	public List<ZcPropCg> findMyCgSp(int empId);
	
	public List<ZcPropCg> findMyCgRecords(int empId);
	
	public List<ZcPropCg> findCgByStatus(int status);
	
	public List<ZcPropCg> findCgByStatus2();
	
	public List<ZcPropCg> findAllCg();
	
	//查看我的申领记录
	public List<ZcPropLy> findLyByEmpId(int empId);
	
	public List<ZcPropLy> findUnhanderLySp();
	
	public List<ZcPropZy> findMyZySp(int empId);
	
	public List<ZcPropZy> findAllZy();
	
	public List<ZcPropWb> findMyWbSp(@Param("empId") int empId, @Param("wb")  int wb);
	
	public ZcPropLy findLyById(int id);
	
	public ZcPropCg findCgById(int id);
	
	public ZcPropZy findZyById(int id);
	
	public ZcPropWb findWbById(int id);
	
	public List<ZcPropWb> findAllWbByType(@Param("wb")  int wb);
	
	//public void addEmpProp(@Param("empId") Integer empId, @Param("propId") Integer propId, @Param("dayStr") String dayStr);
	
	public void addPropLog(ZcPropLog log);
	
	public void updateProp(ZcProperty prop);
	
	public void delProp(ZcProperty prop);
	
	//public void deleteEmpProp(int propId);
	
	public void updateLy(ZcPropLy ly);
	
	public void updateCg(ZcPropCg cg);
	
	public void updateCgStatusForAll(ZcPropCg cg);
	
	public List<ZcProperty> findAllProperty();
	
	public List<ZcPropCg> findPurchaseProperty(ParamBean pb);
	
	public void updateCgPropMoney(@Param("money") double money, @Param("id") Integer id);
	
	public void updateCgPropStatus(@Param("cgStatus") Integer cgStatus, @Param("id") Integer id, @Param("brk") String brk);
	
	public void updateLyStatus(ZcPropLy ly);
	
	public void updateCgStatus(ZcPropCg cg);
	
	public void updateZy(ZcPropZy zy);
	
	public void updateWb(ZcPropWb wb);
	
	public List<ZcPropTmp> findPropTmp();
	
	public void addPropCode(ZcProperty prop);
	
	public void updatePlace(ZcPlace zp);
	
	public void updatePropCode(ZcProperty prop);
	
	public ZcProperty findPropCodeByName(String name);
	
	public List<ZcProperty> findPropCode();
	
	//public void updateLyProp(@Param("lyId") Integer lyId, @Param("propId") Integer propId, @Param("status") Integer status);
}
