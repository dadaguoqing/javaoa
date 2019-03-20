package com.hj.oa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.Materia;
import com.hj.oa.bean.MateriaApply;
import com.hj.oa.bean.MateriaApprove;
import com.hj.oa.bean.MateriaCost;
import com.hj.oa.bean.MateriaDept;
import com.hj.oa.bean.MateriaDetail;
import com.hj.oa.bean.MateriaPurchase;
import com.hj.oa.bean.MateriaPurchaseDetail;
import com.hj.oa.bean.MateriaRecord;
import com.hj.oa.bean.MateriaSeq;
import com.hj.oa.bean.Mould;
import com.hj.oa.bean.Warehouse;

public interface MateriaMapper_v2 {
	
	List<MateriaDept> findWarehousesByAdminId(Integer adminId);
	
	MateriaDept findWarehouseById(Integer id);
	
	Materia findMateriaInfoById(@Param("id")Integer id,@Param("whId")String whId);
	
	Materia findMateriaInfoById2(@Param("id")Integer id);
	
	Double findMateriaPrice(@Param("id")Integer id);
	
	String findMouldByType(String type);
	
	List<Mould> selectAllMould();
	
	public void insertMould(Mould mo);
	
	public Integer isExistCode(@Param("materiaCode")String materiaCode,@Param("warehouse")Integer warehouseId);
	
	public Integer findIdBymateriaCode(@Param("materiaCode")String materiaCode);
	
	void insertMatertia(Materia materia);
	
	void insertMaStock(String materiaCode);
	
	void materiaRecordDetail(MateriaRecord mr);
	
	void insertStockDetail(Materia ma);
	
	Integer findIdByWarehouse(String warehouse);
	
	Warehouse findWarehouse2(String code);
	
	void updateStock(Warehouse wh);
	
	void insertFirstStock(MateriaDetail md);
	
	void insertMateriaDetail(MateriaDetail md);
	
	void insertMateriaDetailNum(MateriaDetail md);
	
	Double findStock(@Param(value = "warehouse") Integer warehouseId,@Param(value = "materiaCode") String materiaCode);
	
	public void updateStock(@Param(value = "warehouseId")Integer warehouseId,
			@Param(value = "code") String materiaCode,@Param(value = "num") double num);
	
	List<MateriaDept> findAllWarehouse();
	
	void insertApplyDetail(MateriaDetail md);
	
	void insertMaApply(MateriaApply ma);
	
	void updateApply(MateriaApply ma);
	
	Integer findAdminIdByWarehouseId(String warehouseId);
	
	List<MateriaApply> findApproveById(int id);
	
	MateriaApply findApplyById(int id);
	
	List<Materia> findMtByCode(String code);
	
	List<MateriaDetail> findApplyByCode(String code);
	
	List<MateriaApply> findMySpRecord(int id);
	
	List<Integer> findMySpIds(int id);
	
	List<MateriaApply> findMyApplyRecord(int id);
	
	List<MateriaRecord> findAllInware(Map map);
	
	Integer findtotalInware(MateriaRecord mr);
	
	List<MateriaRecord> findAllCost(Map map);
	
	Integer findtTotalCost(MateriaRecord ma);
	
	List<Materia> findAllStock(Materia ma);
	
	Integer findStockLength(Materia mm);
	
	List<Materia> findByCurPage(@Param("beginIndex") Integer beginIndex,
			@Param("pageSize") Integer pageSize,@Param("materiaCode") String materiaCode,
			@Param("spec") String spec, @Param("all") String all);
	
	Integer findIdBymateriaCode2(String materiaCode);
	
	void updateMateriaInfo(Materia ma);
	
	void saveAllot(MateriaApply ma);
	
	List<MateriaApply> findMyInwareList(int id);
	
	List<MateriaCost> findPioneerStock(@Param("maId")Integer maId,@Param("whId")Integer whId);
	
	List<MateriaApply> findInwareRecordList2(int id);
	
	void updateIsDeal(int id);
	
	void updateMaStock(@Param("id")Integer id,@Param("num")Double num);
	
	void insertMaCost(MateriaCost mc);
	
	MateriaSeq findMateriaSeqById(Integer id);
	
	void updateMateriaSeq(MateriaSeq ms);
	
	void insertPurchase(MateriaPurchase record);
	
	void insertPurchaseDetail(MateriaPurchaseDetail record);
	
	List<MateriaPurchase> findPurchaseApprove(@Param("userId")Integer userId,@Param("type")Integer type);
	
	List<MateriaPurchase> findInboundRecord(@Param("userId")Integer userId,@Param("type")Integer type);
	
	List<MateriaPurchase> findPurchaseApproveRecord();
	
	List<MateriaPurchase> findReventDealRecord();
	
	List<MateriaPurchase> findMyPurchaseApprove(@Param("userId")Integer userId,@Param("type")Integer type);
	
	List<MateriaPurchase> findRkRecord();
	
	List<MateriaPurchase> findMyPurchaseRecord(@Param("userId")Integer userId,@Param("type")Integer type);
	
	List<MateriaPurchase> findAllPurchaseRecordByType(@Param("type")Integer type);
	
	List<MateriaPurchase> findRejectList(Integer userId, Integer type, Integer beginIndex, Integer endIndexx);
	
	List<MateriaPurchase> findMyPurchaseRecord2(@Param("userId")Integer userId,@Param("type")Integer type);
	
	MateriaPurchase selectMateriaPurchaseById(Integer userId);
	
	List<MateriaPurchase> selectMateriaPurchaseByStatus(@Param("status")Integer status);
	
	List<MateriaPurchaseDetail> selectMateriaPurchaseDetailByCode(String code);
	
	MateriaPurchaseDetail selectMateriaPurchaseDetailById(Integer id);
	
	List<MateriaApprove> selectMateriaApproveByPrimaryKey(String code);
	
	void updateMateriaPurchaseByPrimaryKeySelective(MateriaPurchase mp);
	
	void insertMateriaApproveSelective(MateriaApprove mp);
	
	void updateMateriaPurchaseById(Integer id);
	
	Double findTotalStock(String id);
	
	Materia findMtBySpec(String spec);
	
	List<String> findListMtBySpec(String spec);
	
	List<String> findListMtByMaCode(String maCode);
	
	MateriaPurchase selectMateriaPurchaseByCode(String code);
	
	void updateMateriaPurchaseDetailBySelective(MateriaPurchaseDetail mpd);
	
	List<MateriaPurchase> findMateriaByType(@Param("type") Integer type ,@Param("status") Integer status);
	
	Double findTrueStock(Integer codeId, Integer warehouseId);
	
	Double findCacheStock(Integer codeId, Integer warehouseId);
	
	Integer findRecordTotalSize(Integer userId, Integer type);
}
