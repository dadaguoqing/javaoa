package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.ApplyApprove;
import com.hj.oa.bean.ApplySilicon;
import com.hj.oa.bean.ApplySiliconApprove;
import com.hj.oa.bean.ApplySupport;
import com.hj.oa.bean.ApplySupportApprove;
import com.hj.oa.bean.Management;
import com.hj.oa.bean.OsCg;
import com.hj.oa.bean.OsCgWupinVo;
import com.hj.oa.bean.OsSq;
import com.hj.oa.bean.OsSy;
import com.hj.oa.bean.OsWupin;
import com.hj.oa.bean.OsWupinVo;
import com.hj.oa.bean.OsYaopin;
import com.hj.oa.bean.OsYaopinSq;
import com.hj.oa.bean.OsYaopinVo;
import com.hj.oa.bean.ParamBean;
import com.hj.oa.bean.PurchaseRecord;
import com.hj.oa.bean.SiliconMail;
import com.hj.oa.bean.SpecialApply;
import com.hj.oa.bean.SupportRecord;

public interface OfficeSupplyMapper {
	
	public void addYp(OsYaopin yp);
	
	public void updateYp(OsYaopin yp);
	
	public void addWp(OsWupin wp);
	
	public void addManagement(Management ma);
	
	//采购相关
	public List<OsCg> findMyCgRecord(@Param("empId") int empId, @Param("type") int type);//我的采购记录, 1-办公用品采购，2-药品采购
	
	public List<OsCg> findMyCgSp(@Param("empId") int empId, @Param("type") int type);//我的采购审批列表
	
	public List<ApplyApprove> findSpeApprove(@Param("deptId") int deptId,@Param("empId") int empId);//我的特别申请审批列表
	
	public List<ApplyApprove> findSpeApplyList(int applyStatus);//特别申请列表
	
	public void updateSpeApplyStatus(int applyStatus);//特别申请列表
	
	public List<Management> findModifyApproveList();//特别申请列表
	
	public Management findManagementByMan(Management ma);//特别申请列表
	
	public List<Management> findManagementListByMan(Management ma);//特别申请列表
	
	public Management findManagementById(int id);//特别申请列表
	
	public void updateManagement(Management ma);//特别申请列表
	
	public void updateArticleById(Management ma);//特别申请列表
	
	public void deleteManagementById(int staus);//特别申请列表
	
	
	public List<OsCg> findCgRkList(@Param("type") int type);
	
	public List<OsCg> findCgRecords(@Param("type") int type);
	
	public OsCg findCgById(int id);//查询某一条采购
	
	public void addCg(OsCg cg);
	
	public void updateCg(OsCg cg);
	
	public void updateApply(ApplyApprove app);
	
	public void updateSpeApply(SpecialApply app);
	
	//2
	public void addCgWupin(@Param("cgId") int cgId,@Param("wpId") int wpId,
			
			@Param("money") double money,@Param("num") int num,@Param("bz") String bz,
			@Param("totalPrice") double totalPrice,@Param("subTotal") double subTotal);
	
	public List<OsCgWupinVo> findWupinByCgId(int cgId);
	
	public List<OsCgWupinVo> findYaopinByCgId(int cgId);
	
	public void updateCgWupin(@Param("id") int id, @Param("cgStatus") int cgStatus, @Param("bz") String bz);
	
	//库存
	public void updateWpStock(@Param("id") int id, @Param("stock") int stock);
	
	public void updateYpStock(@Param("id") int id, @Param("stock") int stock);
	
	//损益
	public List<OsSy> findMySyRecord(@Param("empId") int empId, @Param("type") int type);//我的损益记录, 1-办公用品损益，2-药品损益
	
	public List<OsSy> findMySySp(@Param("empId") int empId, @Param("type") int type);//我的损益审批列表
	
	public OsSy findSyById(int id);//查询某一条采购
	
	public void addSy(OsSy sy);
	
	public void updateSy(OsSy sy);
	
	public void addSyWupin(@Param("syId") int syId,@Param("wpId") int wpId, @Param("bz") String bz, @Param("syType") double syType,@Param("num") int num);
	
	public List<OsCgWupinVo> findWupinBySyId(int syId);
	
	public List<OsCgWupinVo> findYaopinBySyId(int syId);
	
	//采购结束
	
	public List<OsWupin> findWupinByType(@Param("companyOnly") Integer companyOnly);
	
	public List<OsWupin> findWupinByTypeName(@Param("type") String type);
	
	public List<OsYaopin> findAllYaopin();
	
	public void insertSilicon(ApplySilicon as);
	
	public void insertSiliconApprove(ApplySiliconApprove asa);
	
	public List<String> findWupinClassify();
	
	public List<ApplySiliconApprove> findSiliconApprove(@Param("useId") int useId);
	
	public List<ApplySupport> findSupportApprove(@Param("useId") int useId);
	
	public ApplySupportApprove findSupportApplyById(@Param("id") int id);
	
	public List<ApplySupportApprove> findSupportApproveList(@Param("id") int id);
	
	public ApplySupportApprove findSupportRecordById(@Param("id") int id);
	
	public void updateSupportRecord(ApplySupportApprove asa);
	
	public void setSupportStatus(ApplySupportApprove asa);
	
	public void insertSupportResult(ApplySupportApprove asa);
	
	
	public List<SupportRecord> findSupportById(@Param("id") int id);
	
	public Integer findFAEId(@Param("id") int id);
	
	public void saveApproveResult(ApplySupportApprove asa);
	
	public void saveApproveResult2(ApplySupportApprove asa);
	
	public List<ApplySilicon> findSiliconByApplyId(@Param("applyId") int applyId);
	
	public ApplySiliconApprove findSiliconApproveById(@Param("id") int id);
	
	public List<ApplySiliconApprove> findSiliconApproveRecord(@Param("useId") int useId);
	
	public List<ApplySiliconApprove> findSiliconApply(@Param("useId") int useId);
	
	public List<ApplySilicon> findSiliconById(@Param("id") int id);
	
	public List<SiliconMail> findSiliconInfo(@Param("id") int id);
	
	public ApplySiliconApprove findSiliconUser(@Param("id") int id);
	
	public void insertSupportApply(ApplySupport as);
	
	public void insertSupportApprove(ApplySupportApprove asa);
	
	public void saveSiliconApprove(ApplySiliconApprove app);
	
	public OsWupin findWupinById(int id);
	
	public List<ApplySupport> findSupportApplyRecord(int id);
	
	public Management findManagementByArtId(int id);
	
	public OsYaopin findYaopinById(int id);
	
	public List<OsSq> findSqByStatus(int status);
	
	public List<OsYaopinSq> findMedSqByStatus(int status);
	
	public List<OsSq> findSqByEmpId(int empId);
	
	public List<ApplyApprove> findSpeApplyByEmpId(int empId);
	
	public List<OsYaopinSq> findMedSqByEmpId(int empId);
	
	public List<OsSq> findCompSq();
	
	public List<OsYaopinSq> findCompMedSq();
	
	public OsSq findSqById(int id);
	
	public ApplyApprove findSpeApplyById(int id);
	
	public ApplyApprove findSpeApproveById(int id);
	
	public Integer getDeptDirectorIdByName(String name);
	
	public Integer getDeptDirectorIdById(Integer id);
	
	public Integer getDeptPIdByName(String name);
	
	
	public OsYaopinSq findYaopinSqById(int id);
	
	public List<OsWupinVo> findSqWpBySqId(int spId);
	
	public List<SpecialApply> findSpeApplyBySqId(int spId);
	
	public List<OsYaopinVo> findSqYpBySqId(int spId);
	
	public List<OsWupinVo> findSqWpByWpId(@Param("begin") String begin, @Param("end") String end, @Param("wpId") int wpId);
	
	public List<OsYaopinVo> findSqYpByYpId(@Param("begin") String begin, @Param("end") String end, @Param("ypId") int ypId);
	
	public List<OsWupinVo> findSqWpSum(@Param("begin") String begin, @Param("end") String end, @Param("empId") Integer empId, @Param("showType") Integer showType, @Param("type") Integer type);
	
	public List<OsYaopinVo> findSqYpSum(@Param("begin") String begin, @Param("end") String end, @Param("empId") Integer empId, @Param("showType") Integer showType, @Param("type") Integer type);
	
	public void addSqWp(OsWupinVo vo);
	//插入特殊申请记录
	public void addArticle(SpecialApply vo);
	
	public List<PurchaseRecord> findPurchaseAll(ParamBean pb);
	
	public List<OsWupinVo> findSqWpAll(ParamBean pb);
	
	public List<OsWupin> findStockAll();
	//对于每个申请的药品
	public void addSqYp(OsYaopinVo vo);
	
	public void addSq(OsSq sq);
	
	public void addApply(ApplyApprove sq);
	
	public void addYaopinSq(OsYaopinSq sq);
	
	public void updateSq(OsSq sq);
	
	public void updateYaopinSq(OsYaopinSq sq);
	
	public void updateSqWp(@Param("status") int status, @Param("sqId") int sqId);
	
	public void updateSqYp(@Param("status") int status, @Param("sqId") int sqId);
}
