package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hj.oa.bean.Materia;
import com.hj.oa.bean.MateriaApply;
import com.hj.oa.bean.MateriaDetail;
import com.hj.oa.bean.MateriaMail;
import com.hj.oa.bean.MateriaPurchase;
import com.hj.oa.bean.MateriaPurchaseDetail;
import com.hj.oa.bean.MateriaRecord;
import com.hj.oa.bean.Mould;
import com.hj.oa.bean.Warehouse;

public interface MateriaMapper {
	void insertMatertia(Materia materia);

	Integer isExistCode(String code);

	List<Materia> findAll();

	List<Materia> findAll2();
	

	void materiaRecord(MateriaRecord mr);

	void materiaRecordDetail(MateriaRecord mr);

	void insertMaStock(String materiaCode);

	void insertStockDetail(Materia ma);

	Warehouse findWarehouse(String code);
	
	Warehouse findWarehouse2(String code);
	
	List<String> findWarehouse3(Integer id);

	void updateStock(Warehouse wh);
	
	void updateStockCache(Warehouse wh);

	void insertMaApply(MateriaApply ma);

	void insertApplyDetail(MateriaDetail md);

	List<MateriaApply> findApproveById(int id);

	List<MateriaApply> findOutwareList(int id);

	List<MateriaApply> findMyAllotList(int id);

	List<MateriaApply> findMyAllotList2(String str);

	List<MateriaApply> findMyAllotList3(String str);

	List<MateriaApply> findMyApplyRecord(int id);

	List<MateriaApply> findMyInwareList(int id);

	List<MateriaApply> findInwareRecordList(int id);

	List<MateriaApply> findInwareRecordList2(int id);

	List<MateriaApply> myOutwareList2(int id);

	List<MateriaApply> findMySpRecord(int id);
	
	List<MateriaApply> findMySpRecord2(MateriaApply ma);
	
	List<Integer> findMySpIds(int id);

	List<MateriaApply> findMyRejectList(int id);

	List<MateriaApply> findMyRejectList2(int id);

	List<MateriaDetail> findApplyByCode(String code);

	MateriaApply findApplyById(int id);
	
	List<MateriaMail> findApplyAllById(int id);
	
	Integer findAdminIdByWarehouse(String warehouse);
	
	Integer findIdByWarehouse(String warehouse);

	Materia findCodeById(int id);

	Double findTotalStock(String materiaCode);

	Materia findMateriaByCode(String materiaCode);

	List<Mould> selectAllMould();

	void updateRealNum(Materia ma);

	void deleteRecordByCode(@Param("materiaCode") String materiaCode, @Param("temp") String temp);

	List<String> findTempByCode(@Param("materiaCode") String materiaCode, @Param("warehouse") String warehouse);

	void insertMould(Mould mo);

	void updatePurchase(Integer id);

	void updateIsDeal(int id);

	List<Materia> findMtByCode(String code);

	void updateApply(MateriaApply ma);

	void savePurchase(MateriaPurchase mp);

	void updatePurchaseStatus(MateriaPurchase mp);

	void savePurchase3(MateriaPurchase mp);

	void savePurchase2(MateriaPurchaseDetail mpd);

	void savePurchase4(MateriaPurchaseDetail mpd);

	List<MateriaPurchase> findPurchase(int id);

	List<MateriaPurchase> findPurchase2(int id);
	
	List<MateriaMail> findPurchaseAllById(int id);
	
	List<MateriaMail> findPurchaseAllById2(int id);

	MateriaPurchase findPurchaseById(int id);

	List<Materia> findMateriaByMateriaCode(@Param("materiaCode") String materiaCode);

	List<Materia> findPurchaseByMateriaCode(@Param("materiaCode") String materiaCode);

	List<MateriaPurchaseDetail> findPurDetail(String number);

	List<MateriaPurchase> findPurchaseRecord(int id);

	List<MateriaPurchase> findPurchaseRecord5(int id);

	List<MateriaPurchase> findInwareList(int id);

	List<MateriaPurchase> findInwareList2(int id);

	List<MateriaApply> findInwareList3(@Param("warehouse") String warehouse);

	List<MateriaPurchase> findInwareRecord(int id);

	List<MateriaPurchase> findPurchaseRecord3(int id);

	List<MateriaPurchase> findPurchaseRecord4(int id);
	
	List<MateriaPurchase> findPurchaseRecordByArray();
	
	List<MateriaPurchase> findPurchaseRecordByArray2(@Param("deptIds") List<Integer> deptIds);
	
	List<MateriaPurchase> findPurchaseRecordByArray3();

	List<MateriaPurchase> findPurchaseRecord2(int id);

	List<MateriaPurchase> findMyApproveRecord(int id);

	List<MateriaPurchase> findMyApproveRecord2(int id);

	void insertStatus(MateriaPurchase mp);

	void updateStatus2(MateriaPurchase mp);

	void saveReject(MateriaApply ma);

	void saveRejectDetail(MateriaDetail md);

	List<MateriaApply> findRejectById(int id);

	List<MateriaApply> findMyOutwareList(int id);

	List<MateriaApply> findAllotList(int id);

	List<MateriaApply> findAllotList2(int id);

	void updateStatus(MateriaApply ma);

	void saveAllot(MateriaApply ma);

	void updateMateria(Materia ma);

	String findMouldByType(String type);

	List<Integer> findStockByCode(String code);

	void updateMateriaPhoto(Materia ma);

	void updateMtDetail(Materia ma);

	List<MateriaRecord> findAllInware(MateriaRecord ma);

	List<MateriaRecord> findAllCost(MateriaRecord ma);

	List<Materia> findAllStock(Materia ma);

	List<Materia> findMaByWarehouse(Materia ma);

	Materia findAllById(Integer id);

	Materia findById(Integer id);
	
	Materia findStockDetail(MateriaRecord str);

	Integer findFirstStock(MateriaRecord mr);

	List<Integer> findOutware(MateriaRecord mr);

	List<Integer> findIdByCode(String code);

	void updateMateriaById(@Param("code") String code, @Param("id") int id);

	void updateStockById(@Param("code") String code, @Param("id") int id);

	// 删除物料编码以及记录
	void deleteCode(@Param("materiaCode") String materiaCode);

	void deleteApplyRecord(@Param("materiaCode") String materiaCode);

	void deletePurchaseRecord(@Param("materiaCode") String materiaCode);

	void deleteRecord(@Param("materiaCode") String materiaCode);

	void deleteStockRecord(@Param("materiaCode") String materiaCode);

	void deleteStockDetail(@Param("materiaCode") String materiaCode);
	
	List<String> findAllWarehouse();
	
	void updateOutwareRealNum(@Param("code") String code,@Param("materiaCode") String materiaCode,
			@Param("realNum") double realNum);

}
