package com.hj.oa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.Consts;
import com.hj.oa.bean.ParamBean;
import com.hj.oa.bean.User;
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
import com.hj.oa.dao.PropertyMapper;
import com.hj.oa.dao.UserMapper;
import com.hj.util.DateUtil;

@Service
public class PropertyService {

	@Autowired
	private PropertyMapper propMapper;
	@Autowired
	private UserMapper userMapper;
	
	public void addProperty(ZcProperty prop){
		propMapper.addProperty(prop);
	}
	
	public void updateStatus(int id){
		propMapper.updateStatus(id);
	}
	
	public void addProps(List<ZcProperty> props){
		for(ZcProperty prop : props){
			propMapper.addProperty(prop);
		}
	}
	
	public void addPropLy(ZcPropLy ly, String[] names, String[] bzs){
		propMapper.addLy(ly);
		
		for(int i=0; i<names.length; i++){
			String name = names[i];
			String bz = bzs[i];
			propMapper.addLyProp(ly.getId(), name, bz);
		}
	}
	
	public void addPropCg(ZcPropCg cg, String[] names, String[] bzs,char[] units,double[] prices,
						int[] nums,double[] moneys,String brands[],int types[],int places[]){
		propMapper.addCg(cg);
		
		for(int i=0; i<names.length; i++){
			String name = names[i];
			String bz = bzs[i];
			propMapper.addCgProp(cg.getId(), name, bz,units[i],prices[i],nums[i],moneys[i],brands[i],types[i],places[i]);
		}
	}
	
	public void addPropZy(ZcPropZy zy, Integer[] ids){
		propMapper.addZy(zy);
		List<Integer> list = new ArrayList<Integer>();
		
		for(int id : ids){
			if(list.contains(id)){
				continue;
			}
			propMapper.addZyProp(zy.getId(), id);
			list.add(id);
		}
	}
	
	public void addPropWb(ZcPropWb wb){
		propMapper.addWb(wb);
	}
	
	public void addZy(ZcPropZy zy){
		propMapper.addZy(zy);
	}
	
	public void addPlace(ZcPlace place){
		propMapper.addPlace(place);
	}
	public void deletePlaceById(int id){
		propMapper.deletePlaceById(id);
	}
	
	public int findStockById(int id){
		return propMapper.findStockById(id);
	}
	
	public List<ZcProperty> findAddRecodeAll(){
		return propMapper.findAddRecodeAll();
	}
	
	public List<ZcPropLog> findPropAll(ParamBean pb){
		return propMapper.findPropAll(pb);
	}
	public List<ZcPropLy> findGrantAll(){
		return propMapper.findGrantAll();
	}
	public List<ZcPropWbVo> findRepairAll(){
		return propMapper.findRepairAll();
	}
	
	public void addType(ZcType type){
		this.propMapper.addType(type);
	}
	
	public List<ZcType> findAllType(){
		return propMapper.findAllType();
	}
	
	public List<ZcType> findFirstTypes(){
		return propMapper.findFirstTypes();
	}
	
	public List<ZcType> findSecondTypes(){
		return propMapper.findSecondTypes();
	}
	
	public ZcProperty findPurchaseByCgId(int id){
		return propMapper.findPurchaseByCgId(id);
	}
	
	public List<ZcType> findSubTypes(int pId){
		return propMapper.findSubTypes(pId);
	}
	
	public ZcType findTypeById(int id){
		return propMapper.findTypeById(id);
	}
	
	public String findPlaceById(int id){
		return propMapper.findPlaceById(id);
	}
	
	public List<ZcProperty> findAll(){
		return propMapper.findAll();
	}
	
	public List<ZcProperty> findByCond(int showType, int deptId, int empId, int placeId, int typeId, String code){
		return propMapper.findByCond(showType, deptId, empId, placeId, typeId,code);
	}
	
	public List<ZcProperty> findByCond2(){
		return propMapper.findByCond2();
	}
	
	public ZcProperty findPropById(int id){
		return propMapper.findPropById(id);
	}
	
	public List<ZcProperty> findAllUnused(){
		return propMapper.findAllUnused();
	}
	
	public List<ZcProperty> findUnusedProps(int placeId, int typeId){
		return propMapper.findUnusedProps(placeId, typeId);
	}
	
	public List<ZcProperty> findPropByEmpId(int id){
		return propMapper.findPropByEmpId(id);
	}
	
	public List<ZcProperty> findUnusedPropByType(Integer pId, Integer typeId){
		return propMapper.findUnusedPropByType(pId, typeId);
	}
	
	public List<ZcPropLog> findPropLog(int propId){
		return propMapper.findPropLog(propId);
	}
	
	public List<ZcPropLog> findEmpPropLog(int empId, String desc){
		if(desc == null){
			desc = "desc";
		}
		return propMapper.findEmpPropLog(empId, desc);
	}
	
	
	public List<ZcProperty> findPropByLy(int id){
		return propMapper.findPropByLy(id);
	}
	
	public List<ZcProperty> findPropByCg(int id){
		return propMapper.findPropByCg(id);
	}
	
	public ZcProperty findPropByCgAndId( int id){
		return propMapper.findPropByCgAndId(id);
	}
	
	
	public List<ZcProperty> findPropByZy(int id){
		return propMapper.findPropByZy(id);
	}
	
	public List<ZcPropTmp> findPropTmp(){
		return propMapper.findPropTmp();
	}
	
	/*
	public List<ZcProperty> findPropForLy(){
		return propMapper.findPropForLy();
	}*/
	
	public List<ZcProperty> findPropForGh(){
		return propMapper.findPropForGh();
	}
	
	public List<ZcPlace> findAllPlace(){
		return propMapper.findAllPlace();
	}
	
	public List<ZcPropLy> findMyLySp(int empId){
		return propMapper.findMyLySp(empId);
	}
	
	public List<ZcPropCg> findMyCgSp(int empId){
		return propMapper.findMyCgSp(empId);
	}
	
	public List<ZcPropCg> findMyCgRecords(int empId){
		return propMapper.findMyCgRecords(empId);
	}
	
	public List<ZcPropCg> findCgByStatus(int status){
		return propMapper.findCgByStatus(status);
	}
	
	public List<ZcProperty> findAutoStoreRecord(ParamBean pb){
		return propMapper.findAutoStoreRecord(pb);
	}
	
	public List<ZcPropCg> findCgByStatus2(){
		return propMapper.findCgByStatus2();
	}
	
	public List<ZcPropCg> findAllCg(){
		return propMapper.findAllCg();
	}
	
	//查看我的申领记录
	public List<ZcPropLy> findLyByEmpId(int empId){
		return propMapper.findLyByEmpId(empId);
	}
	
	public List<ZcPropZy> findMyZySp(int empId){
		return propMapper.findMyZySp(empId);
	}
	
	public List<ZcPropZy> findAllZy(){
		return propMapper.findAllZy();
	}
	
	public List<ZcPropWb> findMyWbSp(int empId, int wb){
		return propMapper.findMyWbSp(empId, wb);
	}
	
	public ZcPropLy findLyById(int id){
		return propMapper.findLyById(id);
	}
	
	public ZcPropCg findCgById(int id){
		return propMapper.findCgById(id);
	}
	
	public List<ZcPropLy> findUnhanderLySp(){
		return propMapper.findUnhanderLySp();
	}
	
	public ZcPropZy findZyById(int id){
		return propMapper.findZyById(id);
	}
	
	public ZcPropWb findWbById(int id){
		return propMapper.findWbById(id);
	}
	
	public void updateLy(ZcPropLy ly){
		propMapper.updateLy(ly);
		
	}
	
	public void updateCg(ZcPropCg cg){
		propMapper.updateCg(cg);
		
	}
	
	public void updateCgStatusForAll(ZcPropCg cg){
		propMapper.updateCgStatusForAll(cg);
		
	}
	
	public List<ZcProperty> findAllProperty(){
		return propMapper.findAllProperty();
	}
	
	public List<ZcPropCg> findPurchaseProperty(ParamBean pb){
		return propMapper.findPurchaseProperty(pb);
	}
	
	public void updateCgPropMoney(ZcPropCg cg , double[] moneys, int[] ids){
		propMapper.updateCg(cg);
		for(int i=0; i<ids.length; i++){
			double money = moneys[i];
			int id = ids[i];
			this.propMapper.updateCgPropMoney(money, id);
		}
	}
	
	public void updateCgPropStatus(Integer cgStatus, Integer id, String brk){
		this.propMapper.updateCgPropStatus(cgStatus, id, brk);
	}
	
	public void updateLyStatus(ZcPropLy ly){
		propMapper.updateLyStatus(ly);
		
	}
	/*
	public void updateLy(ZcPropLy ly, List<ZcProperty> props){
		propMapper.updateLy(ly);
		
		if(ly.getStatus() == 4){
			for(ZcProperty prop:props){
//				propMapper.updateLyProp(ly.getId(), prop.getId(), 1);
			}
		}else if(ly.getStatus() == -1){
			for(ZcProperty prop:props){
//				propMapper.updateLyProp(ly.getId(), prop.getId(), -1);
			}
		}
	}*/
	
	public void updateZy(ZcPropZy zy, List<ZcProperty> props, User emp, User zyUser){
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		propMapper.updateZy(zy);
		if(zy.getStatus() == 5){ //转移成功
			for(ZcProperty prop:props){
				//propMapper.updateLyProp(ly.getId(), prop.getId(), 1);
				prop.setEmpId(zyUser.getId());
				prop.setDayStr(dayStr);
				propMapper.updateProp(prop);
				
				ZcPropLog log = new ZcPropLog();
				log.setDayStr(dayStr);
				log.setPropId(prop.getId());
				log.setInfo(emp.getName()+"转移给" + zyUser.getName()+"。");
				
				log.setType("转出");
				log.setEmpId(emp.getId());
				log.setEmpName(emp.getName());
				
				propMapper.addPropLog(log);
			}
		}
	}
	
	public void updateGhProp(int[] propIds, int[] empIds, int status,int userId,String dayStr){
		int len = propIds.length;
		for(int i=0; i<len; i++){
			//propMapper.updateLyProp(lyIds[i], propIds[i], status);
			if(status == 1){//归还。。。。
				//propMapper.addEmpProp(empIds[i], propIds[i], dayStr);
				ZcProperty prop = propMapper.findPropById(propIds[i]);
				prop.setHanderId(userId);
				prop.setEmpId(null);
				prop.setGhStatus(0);
				prop.setDayStr(dayStr);
				this.propMapper.updateProp(prop);
				
				User emp = userMapper.findById(empIds[i]);
				
				ZcPropLog log = new ZcPropLog();
				log.setDayStr(dayStr);
				log.setPropId(prop.getId());
				log.setInfo(emp.getName()+"归还。");
				log.setEmpId(emp.getId());
				log.setEmpName(emp.getName());
				log.setType("归还");
				propMapper.addPropLog(log);
			}else{
				ZcProperty prop = propMapper.findPropById(propIds[i]);
				prop.setGhStatus(0);
				this.propMapper.updateProp(prop);
			}
		}
	}
	
	public void ffProps(List<ZcProperty> props, User emp){
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		
		for(ZcProperty prop : props){
			prop.setEmpId(emp.getId());
			prop.setDayStr(dayStr);
			this.propMapper.updateProp(prop);
			
			
			ZcPropLog log = new ZcPropLog();
			log.setDayStr(dayStr);
			log.setPropId(prop.getId());
			log.setInfo(emp.getName()+"领用。");
			log.setType("领用");
			log.setEmpId(emp.getId());
			log.setEmpName(emp.getName());
			propMapper.addPropLog(log);
		}
	}
	
	public void updateLyProp(int[] lyIds, int[] propIds, int[] empIds, int status){
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		int len = lyIds.length;
		for(int i=0; i<len; i++){
//			propMapper.updateLyProp(lyIds[i], propIds[i], status);
			if(status == 2){//发放。。。。
				//propMapper.addEmpProp(empIds[i], propIds[i], dayStr);
				ZcProperty prop = propMapper.findPropById(propIds[i]);
				prop.setEmpId(empIds[i]);
				prop.setDayStr(dayStr);
				this.propMapper.updateProp(prop);
				
				User emp = userMapper.findById(empIds[i]);
				
				ZcPropLog log = new ZcPropLog();
				log.setDayStr(dayStr);
				log.setPropId(prop.getId());
				log.setInfo(emp.getName()+"领用。");
				propMapper.addPropLog(log);
			}
		}
	}
	
	public void updateWb(ZcPropWb wb, ZcProperty prop){
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		propMapper.updateWb(wb);
		if(wb.getStatus() == 4){ //审批通过，添加一条资产维修记录，或者删除一个资产。
			if(wb.getWb() == 1){ //通过注销
				
				propMapper.delProp(prop);
				
				ZcPropLog log = new ZcPropLog();
				log.setDayStr(dayStr);
				log.setPropId(prop.getId());
				log.setInfo("通过注销申请，注销成功。");
				if(prop.getEmpId() != null){
					log.setEmpId(prop.getEmpId());
				}
				log.setType("注销");
				propMapper.addPropLog(log);
				
			}else if(wb.getWb() == 0){ //通过维修, 添加一个维修记录
				
				ZcPropLog log = new ZcPropLog();
				log.setDayStr(dayStr);
				log.setPropId(prop.getId());
				log.setInfo("申请了一次维修。");
				if(prop.getEmpId() != null){
					log.setEmpId(prop.getEmpId());
				}
				log.setType("维修");
				propMapper.addPropLog(log);
			}
		}
	}
	
	public List<ZcPropWb> findAllWbByType(int wb){
		return this.propMapper.findAllWbByType(wb);
	}
	
	
	public void updateProperty(ZcProperty prop){
		propMapper.updateProp(prop);
	}
	
	public void addPropCode(ZcProperty prop){
		propMapper.addPropCode(prop);
	}
	
	public void updatePropCode(ZcProperty prop){
		propMapper.updatePropCode(prop);
	}
	
	public ZcProperty findPropCodeByName(String name){
		return propMapper.findPropCodeByName(name);
	}
	
	public List<ZcProperty> findPropCode(){
		return propMapper.findPropCode();
	}
	public void updatePlace(ZcPlace zp){
		propMapper.updatePlace(zp);
	}
}
