package com.hj.oa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.hj.oa.dao.OfficeSupplyMapper;

@Service
public class OfficeSupplyService {

	@Autowired
	private OfficeSupplyMapper osMapper;
	
	public void addYp(OsYaopin yp){
		this.osMapper.addYp(yp);
	}
	
	public void updateYp(OsYaopin yp){
		this.osMapper.updateYp(yp);
	}
	
	public void addWp(OsWupin wp){
		this.osMapper.addWp(wp);
	}
	public void addManagement(Management ma){
		this.osMapper.addManagement(ma);
	}
	
	//采购相关
	
	//我的采购记录, 1-办公用品采购，2-药品采购
	public List<OsCg> findMyCgRecord( int empId,  int type){
		return osMapper.findMyCgRecord(empId, type);
	}
	
	public List<OsCg> findMyCgSp(int empId, int type){
		//我的采购审批列表
		return osMapper.findMyCgSp(empId, type);
	}
	
	public List<ApplyApprove> findSpeApprove(int deptId,int empId){
		//我的特别申请审批列表
		return osMapper.findSpeApprove(deptId,empId);
	}
	
	public List<Management> findModifyApproveList(){
		//我的特别申请审批列表
		return osMapper.findModifyApproveList();
	}
	
	public Management findManagementByMan(Management ma){
		//我的特别申请审批列表
		return osMapper.findManagementByMan(ma);
	}
	public List<Management> findManagementListByMan(Management ma){
		return osMapper.findManagementListByMan(ma);
	}
	public Management findManagementById(int id){
		//我的特别申请审批列表
		return osMapper.findManagementById(id);
	}
	
	public void updateManagement(Management ma){
		osMapper.updateManagement(ma);
	}
	
	public void updateArticleById(Management ma){
		osMapper.updateArticleById(ma);
	}
	
	public void deleteManagementById(int staus){
		osMapper.deleteManagementById(staus);
	}

	public List<ApplyApprove> findSpeApplyList(int applyStatus){
		//特别申请列表
		return osMapper.findSpeApplyList(applyStatus);
	}
	public void updateSpeApplyStatus(int applyStatus){
		//特别申请列表
		 osMapper.updateSpeApplyStatus(applyStatus);
	}
	
	public List<OsCg> findCgRkList(int type){
		return osMapper.findCgRkList( type);
	}
	
	public List<OsCg> findCgRecords(int type){
		return osMapper.findCgRecords(type);
	}
	
	public OsCg findCgById(int id){
		//查询某一条采购
		return osMapper.findCgById(id);
	}
	
	public void addCg(OsCg cg, List<OsCgWupinVo> list){
		osMapper.addCg(cg);
		for(OsCgWupinVo vo : list){
			osMapper.addCgWupin(cg.getId(), vo.getId(), vo.getMoney(), vo.getNum(),vo.getBz(),vo.getTotalPrice(),vo.getSubTotal());
		}
	}
	
	public void updateCg(OsCg cg){
		osMapper.updateCg(cg);
	}
	public void updateApply(ApplyApprove app){
		osMapper.updateApply(app);
	}
	public void updateSpeApply(SpecialApply sa){
		osMapper.updateSpeApply(sa);
	}
	
	//2
	public void addCgWupin(int cgId, int wpId,double money, int num,String bz,double totalPrice,double subTotal){
		osMapper.addCgWupin(cgId, wpId, money, num, bz,totalPrice,subTotal);
	}
	
	public List<OsCgWupinVo> findWupinByCgId(int cgId){
		return osMapper.findWupinByCgId(cgId);
	}
	
	public List<OsCgWupinVo> findYaopinByCgId(int cgId){
		return osMapper.findYaopinByCgId(cgId);
	}
	
	public void updateCgWupin( int id,  int cgStatus, String bz){
		osMapper.updateCgWupin(id, cgStatus, bz);
	}
	
	public void updateCgWupinPl( List<OsCgWupinVo> list ){
		
		for(OsCgWupinVo cgWp : list){
			
			OsWupin wp = this.findWupinById(cgWp.getWpId());
			int stock = wp.getStock();
			wp.setStock(stock + cgWp.getNum());
			this.updateWpStock(wp.getId(), wp.getStock());
			
			osMapper.updateCgWupin(cgWp.getId(), 1, null);
		}
		
	}
	
	public void updateCgYaopinPl( List<OsCgWupinVo> list ){
		
		for(OsCgWupinVo cgWp : list){
			
			OsYaopin yp = this.findYaopinById(cgWp.getWpId());
			int stock = yp.getStock();
			yp.setStock(stock + cgWp.getNum());
			this.updateYpStock(yp.getId(), yp.getStock());
			
			osMapper.updateCgWupin(cgWp.getId(), 1, null);
		}
		
	}
	
	//库存
	public void updateWpStock(int id, int stock){
		osMapper.updateWpStock(id, stock);
	}
	
	//库存
	public void updateWpStock(int type, int id, int num){
		//osMapper.updateWpStock(id, stock);
		OsWupin wp = this.osMapper.findWupinById(id);
		int stock = wp.getStock();
		if(type == 1){
			stock += num;
		}else if(type == -1){
			stock -= num;
		}
		osMapper.updateWpStock(id, stock);
	}
	
	public void updateYpStock(int id, int stock){
		osMapper.updateYpStock(id,stock);
	}
	
	public void updateYpStock(int type, int id, int num){
		//osMapper.updateWpStock(id, stock);
		OsYaopin yp = this.osMapper.findYaopinById(id);
		int stock = yp.getStock();
		if(type == 1){
			stock += num;
		}else if(type == -1){
			stock -= num;
		}
		osMapper.updateYpStock(id, stock);
	}
	
	//损益
	public List<OsSy> findMySyRecord(int empId, int type){
		//我的损益记录, 1-办公用品损益，2-药品损益
		return osMapper.findMySyRecord(empId, type);
	}
	
	public List<OsSy> findMySySp(int empId, int type){
		//我的损益审批列表
		return osMapper.findMySySp(empId, type);
	}
	
	public OsSy findSyById(int id){
		//查询某一条采购
		return osMapper.findSyById(id);
	}
	
	public void addSy(OsSy sy, List<OsCgWupinVo> list){
		osMapper.addSy(sy);
		for(OsCgWupinVo vo : list){
			osMapper.addSyWupin(sy.getId(), vo.getId(), null, sy.getSyType(), vo.getNum());
		}
	}
	
	public void updateSy(OsSy sy){
		osMapper.updateSy(sy);
	}
	
	public void updateSy(OsSy sy, List<OsCgWupinVo> list){
		osMapper.updateSy(sy);
		for(OsCgWupinVo vo : list){
			if(sy.getType() == 1){
				this.updateWpStock(sy.getSyType(), vo.getWpId(), vo.getNum());
			}else if(sy.getType() ==2){//药品
				this.updateYpStock(sy.getSyType(), vo.getWpId(), vo.getNum());
			}
		}
	}
	
	public List<OsCgWupinVo> findWupinBySyId(int syId){
		return osMapper.findWupinBySyId(syId);
	}
	
	public List<OsCgWupinVo> findYaopinBySyId(int syId){
		return osMapper.findYaopinBySyId(syId);
	}
	// end 采购相关
	
	public List<OsWupin> findWupinByType(Integer companyOnly){
		return osMapper.findWupinByType(companyOnly);
	}
	
	public List<OsWupin> findWupinByTypeName(String type){
		return osMapper.findWupinByTypeName(type);
	}
	
	public List<String> findWupinClassify(){
		return osMapper.findWupinClassify();
	}
	
	public List<OsYaopin> findAllYaopin(){
		return osMapper.findAllYaopin();
	}
	
	public void insertSilicon(ApplySilicon as){
		osMapper.insertSilicon(as);
	}
	
	public void insertSiliconApprove(ApplySiliconApprove asa){
		osMapper.insertSiliconApprove(asa);
	}
	
	public List<ApplySiliconApprove> findSiliconApprove(int  useId){
		return osMapper.findSiliconApprove(useId);
	}
	
	public List<ApplySupport> findSupportApprove(int  useId){
		return osMapper.findSupportApprove(useId);
	}
	
	public ApplySupportApprove findSupportApplyById(int  id){
		return osMapper.findSupportApplyById(id);
	}
	
	public List<ApplySupportApprove> findSupportApproveList(int  id){
		return osMapper.findSupportApproveList(id);
	}
	
	public ApplySupportApprove findSupportRecordById(int  id){
		return osMapper.findSupportRecordById(id);
	}
	
	public void updateSupportRecord(ApplySupportApprove  asa){
		 osMapper.updateSupportRecord(asa);
	}
	public void setSupportStatus(ApplySupportApprove asa){
		osMapper.setSupportStatus(asa);
	}
	
	public void insertSupportResult(ApplySupportApprove  asa){
		osMapper.insertSupportResult(asa);
	}
	
	public List<SupportRecord> findSupportById(int  id){
		return osMapper.findSupportById(id);
	}
	
	public Integer findFAEId(int  id){
		return osMapper.findFAEId(id);
	}
	
	public void saveApproveResult(ApplySupportApprove asa){
		 osMapper.saveApproveResult(asa);
	}
	public void saveApproveResult2(ApplySupportApprove asa){
		osMapper.saveApproveResult2(asa);
	}
	
	public List<ApplySilicon> findSiliconByApplyId(int  applyId){
		return osMapper.findSiliconByApplyId(applyId);
	}
	
	public List<ApplySiliconApprove> findSiliconApproveRecord(int  useId){
		return osMapper.findSiliconApproveRecord(useId);
	}
	
	public ApplySiliconApprove findSiliconApproveById(int  id){
		return osMapper.findSiliconApproveById(id);
	}
	
	public List<ApplySiliconApprove> findSiliconApply(int  useId){
		return osMapper.findSiliconApply(useId);
	}
	
	public List<ApplySilicon> findSiliconById(int  id){
		return osMapper.findSiliconById(id);
	}
	
	public List<SiliconMail> findSiliconInfo(int  id){
		return osMapper.findSiliconInfo(id);
	}
	
	public ApplySiliconApprove findSiliconUser(int  id){
		return osMapper.findSiliconUser(id);
	}
	
	public void insertSupportApply(ApplySupport  as){
		 osMapper.insertSupportApply(as);
	}
	
	public void insertSupportApprove(ApplySupportApprove  asa){
		osMapper.insertSupportApprove(asa);
	}
	
	public List<ApplySupport> findSupportApplyRecord(int  id){
		return osMapper.findSupportApplyRecord(id);
	}
	
	public void saveSiliconApprove(ApplySiliconApprove  app){
		 osMapper.saveSiliconApprove(app);
	}
	
	public OsWupin findWupinById(int id){
		return osMapper.findWupinById(id);
	}
	
	
	public Management findManagementByArtId(int id){
		return osMapper.findManagementByArtId(id);
	}
	
	public OsYaopin findYaopinById(int id){
		return osMapper.findYaopinById(id);
	}
	
	public List<OsSq> findSqByStatus(int status){
		return osMapper.findSqByStatus(status);
	}
	
	public List<OsYaopinSq> findMedSqByStatus(int status){
		return osMapper.findMedSqByStatus(status);
	}
	
	public List<OsSq> findSqByEmpId(int empId){
		return osMapper.findSqByEmpId(empId);
	}
	public List<ApplyApprove> findSpeApplyByEmpId(int empId){
		return osMapper.findSpeApplyByEmpId(empId);
	}
	
	public List<OsYaopinSq> findMedSqByEmpId(int empId){
		return osMapper.findMedSqByEmpId(empId);
	}
	
	public List<OsSq> findCompSq(){
		return osMapper.findCompSq();
	}
	
	public List<OsYaopinSq> findCompMedSq(){
		return osMapper.findCompMedSq();
	}
	
	public OsSq findSqById(int id){
		return osMapper.findSqById(id);
	}
	public ApplyApprove findSpeApplyById(int id){
		return osMapper.findSpeApplyById(id);
	}
	public ApplyApprove findSpeApproveById(int id){
		return osMapper.findSpeApproveById(id);
	}
	public Integer getDeptDirectorIdByName(String name){
		return osMapper.getDeptDirectorIdByName(name);
	}
	
	public Integer getDeptDirectorIdById(Integer id){
		return osMapper.getDeptDirectorIdById(id);
	}
	
	public Integer getDeptPIdByName(String name){
		return osMapper.getDeptPIdByName(name);
	}
	
	public OsYaopinSq findYaopinSqById(int id){
		return osMapper.findYaopinSqById(id);
	}
	
	public List<OsWupinVo> findSqWpBySqId(int spId){
		return osMapper.findSqWpBySqId(spId);
	}
	public List<SpecialApply> findSpeApplyBySqId(int spId){
		return osMapper.findSpeApplyBySqId(spId);
	}
	
	public List<OsYaopinVo> findSqYpBySqId(int spId){
		return osMapper.findSqYpBySqId(spId);
	}
	
	public List<OsWupinVo> findSqWpByWpId(String begin, String end, int wpId){
		return osMapper.findSqWpByWpId(begin, end ,wpId);
	}
	
	public List<OsYaopinVo> findSqYpByYpId(String begin, String end, int wpId){
		return osMapper.findSqYpByYpId(begin, end ,wpId);
	}
	
	public List<OsWupinVo> findSqWpSum1(String begin, String end, Integer empId, Integer showType, Integer type){
		return osMapper.findSqWpSum(begin, end, empId, showType, type);
	}
	
	public List<PurchaseRecord> findPurchaseAll(ParamBean pb){
		return osMapper.findPurchaseAll(pb);
	}
	
	public List<OsWupinVo> findSqWpAll(ParamBean pb){
		return osMapper.findSqWpAll(pb);
	}
	
	public List<OsWupin> findStockAll(){
		return osMapper.findStockAll();
	}
	
	public List<OsYaopinVo> findSqYpSum(String begin, String end, Integer empId, Integer showType, Integer type){
		return osMapper.findSqYpSum(begin, end, empId, showType, type);
	}
	
	public void addSq(OsSq sq, List<OsWupinVo> wps){
		osMapper.addSq(sq);
		for(OsWupinVo wp : wps){
			wp.setSqId(sq.getId());
			osMapper.addSqWp(wp);
		}
	}
	//添加特殊申请
	public void addSpecialApply(ApplyApprove sq, List<SpecialApply> wps){
		osMapper.addApply(sq);
		for(SpecialApply wp : wps){
			wp.setApplyId(sq.getId());
			osMapper.addArticle(wp);
		}
	}
	
	public void addMedSq(OsYaopinSq sq, List<OsYaopinVo> yps){
		osMapper.addYaopinSq(sq);
		for(OsYaopinVo yp : yps){
			yp.setSqId(sq.getId());
			osMapper.addSqYp(yp);
		}
	}
	
	public void updateSq(OsSq sq){
		osMapper.updateSq(sq);
		osMapper.updateSqWp(sq.getStatus(), sq.getId());
	}
	
	public void updateYpSq(OsYaopinSq sq){
		osMapper.updateYaopinSq(sq);
		osMapper.updateSqYp(sq.getStatus(), sq.getId());
	}
	
	//比较申请物品和库存物品数量
	public boolean compareArticle(List<OsWupinVo> list){
		if(list == null){
			return true;
		}
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(OsWupinVo li:list){
			OsWupin stockArticle = osMapper.findWupinById(li.getId());
			int stock = stockArticle.getStock();
			
			if(map.containsKey(li.getName())){
				int rest = map.get(li.getName()) - li.getNum();
				map.put(li.getName(), rest>=0?rest:-1);
			}else{
				int rest = stock-li.getNum();
				map.put(li.getName()+li.getType(), rest>=0?rest:-1);
			}
			
		}
		if(map.containsValue(-1)){
			return false;
		}
		return true;
	}
}
