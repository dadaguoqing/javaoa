package com.hj.oa.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.Consts;
import com.hj.oa.bean.ApproveBean;
import com.hj.oa.bean.ApproveBeanExample;
import com.hj.oa.bean.ManageConfig;
import com.hj.oa.bean.MateriaMould;
import com.hj.oa.bean.MateriaMouldExample;
import com.hj.oa.bean.MateriaSeq;
import com.hj.oa.dao.ApproveBeanMapper;
import com.hj.oa.dao.ManageConfigMapper;
import com.hj.oa.dao.MateriaMapper_v2;
import com.hj.oa.dao.MateriaMouldMapper;
import com.hj.util.DateUtil;
/**
 * @ClassName:  CommonService   
 * @Description: 常用工具
 * @author: wqfang
 * @date:   2018年11月7日 上午11:56:56
 *
 */

@Service
public class CommonService {
	
	@Autowired
	private MateriaMapper_v2 maMapper;
	@Autowired
	private MateriaMouldMapper maMouldMapper;
	@Autowired
	private ApproveBeanMapper approveMapper;
	@Autowired
	private ManageConfigMapper manageConfigMapper;

	/**
	 * @Title: getApplyCode   
	 * @Description: 获取申请编号
	 * @return: String      
	 * @throws
	 */
	public String getApplyCode(String str, Integer type) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date2 = sdf.format(date);
		String code = str + date2;
		MateriaSeq ms = this.maMapper.findMateriaSeqById(type);
		if (ms != null) {
			if (date2.equals(ms.getDate())) {
				code += String.format("%03d", ms.getCurrentId() + 1);
			} else {
				code += String.format("%03d", 1);
			}
		}
		return code;
	}
	
	/**
	 * @Title: updateSeq   
	 * @Description: 更新申请编号
	 * @return: void      
	 * @throws
	 */
	public void updateSeq(String number, Integer id, Integer subLength) {
		MateriaSeq ms = new MateriaSeq();
		ms.setId(id);
		ms.setCurrentId(Integer.parseInt(number.substring(8 + subLength, 11 + subLength)));
		ms.setDate(number.substring(subLength, subLength + 8));
		this.maMapper.updateMateriaSeq(ms);
	}
	
	
	public MateriaMould selectByPrimaryKey(String type) {
		return this.maMouldMapper.selectByPrimaryKey(type);
	}
	
	public void updateMateriaMould(MateriaMould  mm) {
		this.maMouldMapper.updateByPrimaryKeySelective(mm);
	}
	
	public List<MateriaMould> selectAllMould() {
		MateriaMouldExample ex = new MateriaMouldExample();
		ex.setOrderByClause("type desc");
		ex.createCriteria();
		return this.maMouldMapper.selectByExample(ex);
	}
	
	public List<ApproveBean> getApproveByApplyCode(String applyCode) {
		ApproveBeanExample ex = new ApproveBeanExample();
		ex.createCriteria().andCodeEqualTo(applyCode);
		return this.approveMapper.selectByExample(ex);
	}
	
	public void saveApprove(String opinion, String sp, Integer status, String applyCode, Integer approveId) {
		ApproveBean ab = new ApproveBean();
		ab.setApproveDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		ab.setApproveOpinion(opinion);
		ab.setApproveStatus(status);
		ab.setCode(applyCode);
		ab.setApproveResult(sp);
		ab.setApproveId(approveId);
		this.approveMapper.insertSelective(ab);
	}
	
	public ManageConfig selectConfigById(Integer id) {
		return this.manageConfigMapper.selectByPrimaryKey(id);
	}
	
	public int insertManageConfig(ManageConfig mc) {
		return this.manageConfigMapper.insertSelective(mc);
	}
	
	public int  updateManageConfig(ManageConfig mc) {
		return this.manageConfigMapper.updateByPrimaryKeySelective(mc);
	}
	
}
