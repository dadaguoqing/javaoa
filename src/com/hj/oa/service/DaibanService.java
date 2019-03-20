package com.hj.oa.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hj.commons.Constants;
import com.hj.commons.MateriaInsertType;
import com.hj.commons.OutstockStatus;
import com.hj.oa.Consts;
import com.hj.oa.bean.ApplySiliconApprove;
import com.hj.oa.bean.ApplySupport;
import com.hj.oa.bean.AuthSP;
import com.hj.oa.bean.BKqSp;
import com.hj.oa.bean.DaiLiSP;
import com.hj.oa.bean.Daiban;
import com.hj.oa.bean.ExternalApplyExample;
import com.hj.oa.bean.JiaBan;
import com.hj.oa.bean.LabPcbSq;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.LeaveCancel;
import com.hj.oa.bean.MateriaApply;
import com.hj.oa.bean.MateriaPurchase;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.MerchandiseApply;
import com.hj.oa.bean.Notice;
import com.hj.oa.bean.OsCg;
import com.hj.oa.bean.OsSq;
import com.hj.oa.bean.OsSy;
import com.hj.oa.bean.OsYaopinSq;
import com.hj.oa.bean.OutstockRecord;
import com.hj.oa.bean.ProductOutstockRecord;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.SealApply;
import com.hj.oa.bean.User;
import com.hj.oa.bean.ZcPropCg;
import com.hj.oa.bean.ZcPropLy;
import com.hj.oa.bean.ZcPropWb;
import com.hj.oa.bean.ZcPropZy;
import com.hj.oa.bean.ZcProperty;
import com.hj.oa.dao.AuthSPMapper;
import com.hj.oa.dao.DaiLiSPMapper;
import com.hj.oa.dao.GiftMapper;
import com.hj.oa.dao.JiaBanMapper;
import com.hj.oa.dao.KqMapper;
import com.hj.oa.dao.LabMapper;
import com.hj.oa.dao.LeaveMapper;
import com.hj.oa.dao.MateriaMapper_v2;
import com.hj.oa.dao.MerchandiseApplyMapper;
import com.hj.oa.dao.NoticeMapper;
import com.hj.oa.dao.OfficeSupplyMapper;
import com.hj.oa.dao.PropertyMapper;
import com.hj.oa.dao.RoleMapper;
import com.hj.oa.dao.SealApplyMapper;
import com.hj.oa.dao.UserMapper;
import com.hj.oa.service.inter.ProductService;
import com.hj.util.OtherUtil;
import com.hj.util.RoleUtil;

@Service
public class DaibanService {

	@Autowired
	private LeaveMapper leaveMapper;
	@Autowired
	private JiaBanMapper jiabanMapper;
	@Autowired
	private DaiLiSPMapper dailiMapaper;
	@Autowired
	private AuthSPMapper authMapper;
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	GiftMapper giftMapper;

	@Autowired
	private KqMapper kqMapper;
	@Autowired
	private OfficeSupplyMapper osMapper;
	@Autowired
	private PropertyMapper propMapper;
	@Autowired
	private LabMapper labMapper;
	@Autowired
	private MateriaMapper_v2 maMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SealApplyMapper saMapper;
	@Autowired
	private MerchandiseService msService;
	@Autowired
	private MateriaService_v2 maService;
	@Autowired
	private ExternalService exService;

	@Autowired
	@Qualifier("productService")
	private ProductService productService;

	public HashMap<String, ArrayList<Daiban>> findDailiDaibanByEmpId(int empId) {

		HashMap<String, ArrayList<Daiban>> ml = new HashMap<String, ArrayList<Daiban>>();

		// 代理别人的
		List<User> dlUser = roleMapper.findWoDailiBieren(empId);
		if (dlUser == null || dlUser.isEmpty()) {
			return ml;
		}

		for (User dlu : dlUser) {

			ArrayList<Daiban> dbs = new ArrayList<Daiban>();
			empId = dlu.getId();
			String name = dlu.getName();

			List<Menu> menus = roleMapper.findDailiMenus(empId);
			List<Role> empRoles = roleMapper.findRolesByEmpId(empId);

			List<Leave> leaves = new ArrayList<Leave>();// leaveMapper.findMySpTypeAll(empId);//请假，出差
			List<JiaBan> jiabans = new ArrayList<JiaBan>();// jiabanMapper.findMySp(empId);
															// //加班审批
			List<Notice> notices = noticeMapper.findByUser(empId, -1);// 查询未读的消息
			List<DaiLiSP> dailiSps = new ArrayList<DaiLiSP>();
			List<AuthSP> authSps = new ArrayList<AuthSP>();

			List<BKqSp> bdks = new ArrayList<BKqSp>();// kqMapper.findMySp(empId);

			int qj = 0;
			int wc = 0;

			if (OtherUtil.containsMenu(Consts.qjsp, menus)) {
				leaves = leaveMapper.findMySpTypeAll(empId);// 请假
				if (!leaves.isEmpty()) {
					for (Leave l : leaves) {
						if (l.getWaichu() == 0) {
							qj++;
						}
					}
				}
			}

			if (OtherUtil.containsMenu(Consts.wcsp, menus)) {
				leaves = leaveMapper.findMySpTypeAll(empId);// 出差
				if (!leaves.isEmpty()) {
					for (Leave l : leaves) {
						if (l.getWaichu() == 1) {
							wc++;
						}
					}
				}
			}

			if (OtherUtil.containsMenu(Consts.jbsp, menus)) {
				jiabans = jiabanMapper.findMySp(empId); // 加班审批
			}

			// 目前系统中，可以不判断是否给予代理权限，以后要加上
			// if(OtherUtil.containsMenu(Consts.bkq, menus)){//打卡补签
			bdks = kqMapper.findMySp(empId);
			// }

			List<LeaveCancel> leaveCancels = leaveMapper.findMyLeaveCancelSpTypeAll(empId);// 销假申请

			if (OtherUtil.containsMenu(Consts.qxsp, menus) && RoleUtil.hasRole(empRoles, "总经理")) {
				authSps = authMapper.findMySP();
			}
			if (OtherUtil.containsMenu(Consts.qxdlsp, menus) && RoleUtil.hasRole(empRoles, "总经理")) {
				dailiSps = dailiMapaper.findMySP();
			}

			if (qj > 0) {
				Daiban db = new Daiban();
				db.setName("请假审批");
				db.setCount(qj);
				db.setUrl("web/oa/leave/mysp");
				db.setDialiId(empId);
				db.setDailiName(name);
				dbs.add(db);
			}
			if (wc > 0) {
				Daiban db = new Daiban();
				db.setName("外出（出差）审批");
				db.setCount(wc);
				db.setUrl("web/oa/waichu/mysp");
				db.setDialiId(empId);
				db.setDailiName(name);
				dbs.add(db);
			}

			if (!leaveCancels.isEmpty()) {// 目前仅开放开发模式
				int xqj = 0;
				int xwc = 0;
				for (LeaveCancel l : leaveCancels) {
					if (l.getWaichu() == 1) {
						xwc++;
					} else {
						xqj++;
					}
				}
				if (xqj > 0) {
					Daiban db = new Daiban();
					db.setName("销假审批");
					db.setCount(xqj);
					db.setUrl("web/oa/leave/myLeaveCancelSp");
					db.setDialiId(empId);
					db.setDailiName(name);
					dbs.add(db);
				}
				/*
				 * if(xwc>0){ Daiban db = new Daiban(); db.setName("外出（出差）审批");
				 * db.setCount(xwc);
				 * db.setUrl("web/oa/waichu/myWaichuCancelSp"); dbs.add(db); }
				 */
			}

			if (!bdks.isEmpty()) {
				int bkq = bdks.size();
				Daiban db = new Daiban();
				db.setName("打卡补签审批");
				db.setDialiId(empId);
				db.setCount(bkq);
				db.setUrl("web/oa/kq/mysp");
				dbs.add(db);
			}

			if (!jiabans.isEmpty()) {
				int jb = jiabans.size();
				Daiban db = new Daiban();
				db.setName("加班审批");
				db.setCount(jb);
				db.setUrl("web/oa/jiaban/mysp");
				db.setDialiId(empId);
				db.setDailiName(name);
				dbs.add(db);
			}

			if (!dailiSps.isEmpty()) {
				int dl = dailiSps.size();
				Daiban db = new Daiban();
				db.setName("权限代理审批");
				db.setCount(dl);
				db.setUrl("web/oa/asp/mydlsp");
				db.setDialiId(empId);
				db.setDailiName(name);
				dbs.add(db);
			}

			if (!authSps.isEmpty()) {
				int ap = authSps.size();
				Daiban db = new Daiban();
				db.setName("权限审批");
				db.setCount(ap);
				db.setUrl("web/oa/asp/mysp");
				db.setDialiId(empId);
				db.setDailiName(name);
				dbs.add(db);
			}

			if (!notices.isEmpty()) {
				int nt = notices.size();
				Daiban db = new Daiban();
				db.setName("未读消息");
				db.setCount(nt);
				db.setUrl("web/oa/notice/list");
				db.setDialiId(empId);
				db.setDailiName(name);
				dbs.add(db);
			}

			// 没有判断是否代理
			// 资产领用审批
			this.addZclySp(dbs, empId, empRoles);
			// 资产转移审批
			this.addZczySp(dbs, empId, empRoles);
			// 资产维修审批
			this.addZcwxSp(dbs, empId, empRoles);
			// 资产注销审批
			this.addZczxSp(dbs, empId, empRoles);
			// 资产发放
			this.addZclyFF(dbs, empId, empRoles);
			// 资产归还
			this.addZcgh(dbs, empId, empRoles);
			// 资产采购
			this.addZccgSp(dbs, empId, empRoles);

			ml.put(name, dbs);
		}
		return ml;

	}

	public List<Daiban> findDaibanByEmpId(int empId, List<Role> empRoles) {

		List<Daiban> dbs = new ArrayList<Daiban>();

		List<Leave> leaves = leaveMapper.findMySpTypeAll(empId);// 请假，出差
		List<LeaveCancel> leaveCancels = leaveMapper.findMyLeaveCancelSpTypeAll(empId);// 我的销假（出差）申请
		List<JiaBan> jiabans = jiabanMapper.findMySp(empId); // 加班审批
		List<DaiLiSP> dailiSps = new ArrayList<DaiLiSP>();
		List<AuthSP> authSps = new ArrayList<AuthSP>();
		List<Notice> notices = noticeMapper.findByUser(empId, -1);// 查询未读的消息
		List<BKqSp> bdks = kqMapper.findMySp(empId);
		List<OsSq> osSqs = new ArrayList<OsSq>();// this.osMapper.findSqByStatus(0);
		List<OsYaopinSq> osYpSqs = new ArrayList<OsYaopinSq>();
		List<ApplySiliconApprove> siliconAppplys = osMapper.findSiliconApprove(empId);// 芯片申请审批
		// 技术支持申请审批首页提醒
		List<ApplySupport> supportApplys = osMapper.findSupportApprove(empId);
		// 外协加工审批首页提醒
		List<LabPcbSq> labApprove = labMapper.findMyPcbSp2(empId);
		// 物料申领提醒
		List<MateriaApply> ma = maMapper.findApproveById(empId);
		// 入库审批
		List<MateriaPurchase> mps = this.maMapper.findPurchaseApprove(empId,
				MateriaInsertType.MATERIA_PURCHASE.getType());
		List<MateriaPurchase> mps2 = this.maMapper.findPurchaseApprove(empId,
				MateriaInsertType.MATERIA_REQUISITION.getType());
		
		List<MateriaPurchase> mps3 = this.maMapper.findPurchaseApprove(empId,
				MateriaInsertType.MATERIA_INWARE.getType());
		
		List<MateriaPurchase> mps4 = this.maMapper.findPurchaseApprove(empId,
				MateriaInsertType.MATERIA_REVENT.getType());
		List<MateriaPurchase> mps5 = this.maMapper.findPurchaseApprove(empId,
				MateriaInsertType.MATERIA_SY.getType());
		List<SealApply> sas = this.saMapper.selectByCurrentId(empId);
		
		long bfsp = this.maService.
				countRejectApprove(empId, MateriaInsertType.MATERIA_REJECT.getType(), false);
		long externalApproveList = this.exService.countApproveList(empId);
		ExternalApplyExample ex = new ExternalApplyExample();
		ex.createCriteria().andStatusEqualTo(5);
		long externalDealList = this.exService.countByExample(ex);
		// new 
		long dealApproveList = this.exService.countDealApproveList(empId);
		
		ExternalApplyExample ex2 = new ExternalApplyExample();
		ex2.createCriteria().andStatusEqualTo(10);
		long inboundList = this.exService.countByExample(ex2);
		// TODO
		
		
		// 成品入库审批
//		long cpTotal = this.msService.findAllRecord(empId, ApplyType.MERCHANDISE_INBOUND.getType(), 2);
//		
//		// 半成品入库审批
//		long bcpTotal = this.msService.findAllRecord(empId, ApplyType.HALF_MERCHANDISE_INBOUND.getType(), 2);
//		
//		// 不良品入库审批
//		long blpTotal = this.msService.findAllRecord(empId, ApplyType.REJECTS_INBOUND.getType(), 2);

		
		
		
		// 产品发货审批
		List<ProductOutstockRecord> por = productService.getWillApprovalRecordsByRoles(empRoles);

		// 产品发货审批
		if (!por.isEmpty()) {
			int size = por.size();
			Daiban db = new Daiban();
			db.setName("产品发货审批");
			db.setDialiId(empId);
			db.setCount(size);
			db.setUrl("web/oa/product/outstock/approval/list");
			dbs.add(db);
		}

		for (Role ro : empRoles) {
			if (ro.containsRole("仓库管理员")) {
				// 产品出库及回单确认
				// List<ProductOutstockRecord> por2 =
				// productService.getProductOutstockRecords(ProductOutstockRecordStatus.READY_FOR_OUTSTOCK,
				// ProductOutstockRecordStatus.ALREADY_OUTSTOCKED);
				List<OutstockRecord> por2 = productService.getOutstockRecordsByStatus(OutstockStatus.READY_FOR_OUTSTOCK,
						OutstockStatus.ALREADY_OUTSTOCKED);

				// 产品发货审批
				if (!por2.isEmpty()) {
					int size = por2.size();
					Daiban db = new Daiban();
					db.setName("产品发货/回单确认");
					db.setDialiId(empId);
					db.setCount(size);
					db.setUrl("web/oa/product/outstock/list");
					dbs.add(db);
				}
				break;
			}
		}

		if (RoleUtil.hasRole(empRoles, "总经理")) {
			dailiSps = dailiMapaper.findMySP();
			authSps = authMapper.findMySP();
		}

		if (RoleUtil.hasRole(empRoles, "考勤管理员")) {
			osSqs = this.osMapper.findSqByStatus(0);
			osYpSqs = this.osMapper.findMedSqByStatus(0);
		}

		if (!leaves.isEmpty()) {
			int qj = 0;
			int wc = 0;
			for (Leave l : leaves) {
				if (l.getWaichu() == 1) {
					wc++;
				} else {
					qj++;
				}
			}
			if (qj > 0) {
				Daiban db = new Daiban();
				db.setName("请假审批");
				db.setCount(qj);
				db.setUrl("web/oa/leave/mysp");
				dbs.add(db);
			}
			if (wc > 0) {
				Daiban db = new Daiban();
				db.setName("出差审批");
				db.setCount(wc);
				db.setUrl("web/oa/waichu/mysp");
				dbs.add(db);
			}
		}

		if (!leaveCancels.isEmpty()) {
			int xqj = 0;
			int xwc = 0;
			for (LeaveCancel l : leaveCancels) {
				if (l.getWaichu() == 1) {
					xwc++;
				} else {
					xqj++;
				}
			}
			if (xqj > 0) {
				Daiban db = new Daiban();
				db.setName("销假审批");
				db.setCount(xqj);
				db.setUrl("web/oa/leave/myLeaveCancelSp");
				dbs.add(db);
			}

			if (xwc > 0) {
				Daiban db = new Daiban();
				db.setName("出差注销审批");
				db.setCount(xwc);
				db.setUrl("web/oa/waichu/myWaichuCancelSp");
				dbs.add(db);
			}
		}

		// 办公用品申领
		if (!osSqs.isEmpty()) {
			int ossq = osSqs.size();
			Daiban db = new Daiban();
			db.setName("办公用品申领");
			db.setDialiId(empId);
			db.setCount(ossq);
			db.setUrl("web/oa/os/sqList");
			dbs.add(db);
		}
		
		// 应急药品申领
		if (!osYpSqs.isEmpty()) {
			int ypsq = osYpSqs.size();
			Daiban db = new Daiban();
			db.setName("应急药品申领");
			db.setDialiId(empId);
			db.setCount(ypsq);
			db.setUrl("web/oa/os/medSqList");
			dbs.add(db);
		}

		// 芯片申请审批
		if (!siliconAppplys.isEmpty()) {
			int xpsp = siliconAppplys.size();
			Daiban db = new Daiban();
			db.setName("芯片申请审批");
			db.setDialiId(empId);
			db.setCount(xpsp);
			db.setUrl("web/oa/os/siliconApprove");
			dbs.add(db);
		}

		// 技术支持申请审批
		if (!supportApplys.isEmpty()) {
			int xpsp = supportApplys.size();
			Daiban db = new Daiban();
			db.setName("技术支持申请审批");
			db.setDialiId(empId);
			db.setCount(xpsp);
			db.setUrl("web/oa/os/supportApproveList");
			dbs.add(db);
		}
		// 外协加工申请审批
		if (!labApprove.isEmpty()) {
			int xpsp = labApprove.size();
			Daiban db = new Daiban();
			db.setName("外协加工审批");
			db.setDialiId(empId);
			db.setCount(xpsp);
			db.setUrl("web/oa/lab/myPcbSp2");
			dbs.add(db);
		}

		// 物料申领审批
		if (!ma.isEmpty()) {
			int wlsp = ma.size();
			Daiban db = new Daiban();

			db.setDialiId(empId);
			db.setCount(wlsp);
			// 如果审批人是仓库管理员就跳到处理页面
			List<User> users = userMapper.findUserByRole("仓库管理员");
			User user = userMapper.findById(empId);
			if (users.contains(user)) {
				db.setName("物料申领处理");
				db.setUrl("web/oa/materia_v2/maApplyList2");
			} else {
				db.setName("物料申领审批");
				db.setUrl("web/oa/materia_v2/maApplyList");
			}
			dbs.add(db);
		}

		// 物料出库审批
		if (!mps3.isEmpty()) {
			int wlck = mps3.size();
			Daiban db = new Daiban();
			db.setDialiId(empId);
			db.setCount(wlck);
			List<User> users = userMapper.findUserByRole("新版仓库管理员");
			User user = userMapper.findById(empId);
			if (users.contains(user)) {
				db.setName("物料入库处理");
				db.setUrl("web/oa/materia_v2/toInware");
			} else {
				db.setName("物料入库检验");
				db.setUrl("web/oa/materia_v2/checkInbound");
			}
			dbs.add(db);
		}
		
		// 返料入库处理
		if (!mps4.isEmpty()) {
			int wlck = mps4.size();
			Daiban db = new Daiban();
			db.setDialiId(empId);
			db.setCount(wlck);
			db.setName("返料入库处理");
			db.setUrl("web/oa/materia_v2/reventApprove");
			dbs.add(db);
		}
		
		// 返料入库处理
		if (bfsp != 0) {
			Daiban db = new Daiban();
			db.setDialiId(empId);
			db.setCount((int)bfsp);
			db.setName("物料报废审批");
			db.setUrl("web/oa/materia_v2/rejectApprove");
			dbs.add(db);
		}
		
		// 外协加工审批 TODO
		if (externalApproveList != 0) {
			Daiban db = new Daiban();
			db.setDialiId(empId);
			db.setCount((int)externalApproveList);
			db.setName("外协加工审批");
			db.setUrl("web/oa/external/approveList");
			dbs.add(db);
		}
		
		// 外协加工处理
		List<User> users = this.userMapper.findUserByRole(Constants.EXTERNAL_MANAGE);
			if(!users.isEmpty()) {
				if (users.get(0).getId() == empId && externalDealList != 0) {
					Daiban db = new Daiban();
					db.setDialiId(empId);
					db.setCount((int)externalDealList);
					db.setName("外协加工处理");
					db.setUrl("web/oa/external/dealList");
					dbs.add(db);
				}
			}
		
		// 外协加工处理审批
		if (dealApproveList != 0) {
			Daiban db = new Daiban();
			db.setDialiId(empId);
			db.setCount((int)dealApproveList);
			db.setName("外协加工处理审批");
			db.setUrl("web/oa/external/dealApproveList");
			dbs.add(db);
		}
		
		if(!users.isEmpty()) {
			if (users.get(0).getId() == empId && inboundList != 0) {
				if (inboundList != 0) {
					Daiban db = new Daiban();
					db.setDialiId(empId);
					db.setCount((int)inboundList);
					db.setName("外协加工入库");
					db.setUrl("web/oa/external/inboundList");
					dbs.add(db);
				}
			}
		}
		
		// TODO  
		
//		// 成品入库审批
//		if (cpTotal != 0) {
//			Daiban db = new Daiban();
//			db.setDialiId(empId);
//			db.setCount((int)cpTotal);
//			db.setName("成品入库审批");
//			db.setUrl("web/oa/merchandise/apply/recordList/" +  ApplyType.MERCHANDISE_INBOUND.getType() + "/2");
//			dbs.add(db);
//		}
//		
//		// 半成品入库审批
//		if (bcpTotal != 0) {
//			Daiban db = new Daiban();
//			db.setDialiId(empId);
//			db.setCount((int)bcpTotal);
//			db.setName("半成品入库审批");
//			db.setUrl("web/oa/merchandise/apply/recordList/" +  ApplyType.HALF_MERCHANDISE_INBOUND.getType() + "/2");
//			dbs.add(db);
//		}
//		
//		// 不良品入库审批
//		if (blpTotal != 0) {
//			Daiban db = new Daiban();
//			db.setDialiId(empId);
//			db.setCount((int)blpTotal);
//			db.setName("不良品入库审批");
//			db.setUrl("web/oa/merchandise/apply/recordList/" +  ApplyType.REJECTS_INBOUND.getType() + "/2");
//			dbs.add(db);
//		}
		
		
		
		// 用章审批
		if (!sas.isEmpty()) {
			int wlck = sas.size();
			Daiban db = new Daiban();
			db.setDialiId(empId);
			db.setCount(wlck);
			db.setName("用章审批");
			db.setUrl("web/oa/seal/approveList");
			dbs.add(db);
		}
		
		// 返料入库处理
		if (!mps5.isEmpty()) {
			int wlck = mps5.size();
			Daiban db = new Daiban();
			db.setDialiId(empId);
			db.setCount(wlck);
			db.setName("物料损益审批");
			db.setUrl("web/oa/materia_v2/syApproveList");
			dbs.add(db);
		}

		if (!bdks.isEmpty()) {
			int bkq = bdks.size();
			Daiban db = new Daiban();
			db.setName("打卡补签审批");
			db.setDialiId(empId);
			db.setCount(bkq);
			db.setUrl("web/oa/kq/mysp");
			dbs.add(db);
		}

		if (!mps.isEmpty()) {
			int bkq = mps.size();
			Daiban db = new Daiban();
			db.setName("物料采购审批");
			db.setDialiId(empId);
			db.setCount(bkq);
			db.setUrl("web/oa/materia_v2/purchaseList");
			dbs.add(db);
		}

		if (!mps2.isEmpty()) {
			int bkq = mps2.size();
			Daiban db = new Daiban();
			db.setName("物料请购审批");
			db.setDialiId(empId);
			db.setCount(bkq);
			db.setUrl("web/oa/materia_v2/requisitionList");
			dbs.add(db);
		}

		// if (!rkcl.isEmpty()) {
		// int bkq = rkcl.size();
		// Daiban db = new Daiban();
		// db.setName("入库申请审批");
		// db.setDialiId(empId);
		// db.setCount(bkq);
		// List<User> users = userMapper.findUserByRole("部门主管");
		// User user = userMapper.findById(empId);
		// if (users.contains(user)) {
		// db.setUrl("web/oa/materia/inwareApproveList");
		// } else {
		// db.setUrl("web/oa/materia/inwareList2");
		// }
		// dbs.add(db);
		// }

		if (!jiabans.isEmpty()) {
			int jb = jiabans.size();
			Daiban db = new Daiban();
			db.setName("加班审批");
			db.setCount(jb);
			db.setUrl("web/oa/jiaban/mysp");
			dbs.add(db);
		}

		if (!dailiSps.isEmpty()) {
			int dl = dailiSps.size();
			Daiban db = new Daiban();
			db.setName("权限代理审批");
			db.setCount(dl);
			db.setUrl("web/oa/asp/mydlsp");
			dbs.add(db);
		}

		if (!authSps.isEmpty()) {
			int ap = authSps.size();
			Daiban db = new Daiban();
			db.setName("权限修改审批");
			db.setCount(ap);
			db.setUrl("web/oa/asp/mysp");
			dbs.add(db);
		}

		if (!notices.isEmpty()) {
			int nt = notices.size();
			Daiban db = new Daiban();
			db.setName("未读消息");
			db.setCount(nt);
			db.setUrl("web/oa/notice/list");
			dbs.add(db);
		}

		// 资产领用审批
		this.addZclySp(dbs, empId, empRoles);
		// 资产转移审批
		this.addZczySp(dbs, empId, empRoles);
		// 资产维修审批
		this.addZcwxSp(dbs, empId, empRoles);
		// 资产注销审批
		this.addZczxSp(dbs, empId, empRoles);
		// 资产发放
		this.addZclyFF(dbs, empId, empRoles);
		// 资产归还
		this.addZcgh(dbs, empId, empRoles);
		// 资产采购
		this.addZccgSp(dbs, empId, empRoles);
		// 办公用品采购
		this.addWpcgSp(dbs, empId, empRoles);
		// 办公用品损益
		this.addWpsySp(dbs, empId, empRoles);
		// 应急药品采购
		this.addYpcgSp(dbs, empId, empRoles);
		// 应急药品损益
		this.addYpsySp(dbs, empId, empRoles);
		// 添加PCB制板
		// this.addPcbSp(dbs, empId, empRoles);
		// 添加钢网加工
		this.addGwSp(dbs, empId, empRoles);
		// 添加节日福利待办
		this.addGiftForDw(dbs, empId, empRoles);
		return dbs;
	}

	// 添加节日福利待办

	private void addGiftForDw(List<Daiban> dbs, int empId, List<Role> empRoles) {
		// List<LabPcbSq> sqs = labMapper.findMyPcbSp(empId,
		// 1);//propMapper.findMyCgSp(empId);//new ArrayList<ZcPropLy>();
		// Gift gift = giftMapper.getByEmp(empId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时");
		String dayStr = sdf.format(new Date());
		Integer size = this.giftMapper.getEmpTodo(empId, dayStr);

		if (size != null && size > 0 && empId != Consts.managerId) {
			// int size = 1;
			Daiban db = new Daiban();
			db.setName("节日福利挑选");
			db.setCount(size);
			db.setUrl("web/oa/gift/list");
			dbs.add(db);
		}
	}

	// 添加钢网制板审批通知
	private void addGwSp(List<Daiban> dbs, int empId, List<Role> empRoles) {
		List<LabPcbSq> sqs = labMapper.findMyPcbSp(empId, 2);// propMapper.findMyCgSp(empId);//new
																// ArrayList<ZcPropLy>();

		if (!sqs.isEmpty()) {
			int size = sqs.size();
			Daiban db = new Daiban();
			db.setName("钢网加工审批");
			db.setCount(size);
			db.setUrl("web/oa/lab/myGwSp");
			dbs.add(db);
		}
	}

	// 添加应急药品损益审批通知
	private void addYpsySp(List<Daiban> dbs, int empId, List<Role> empRoles) {
		List<OsSy> sqs = osMapper.findMySySp(empId, 2);// propMapper.findMyCgSp(empId);//new
														// ArrayList<ZcPropLy>();

		if (!sqs.isEmpty()) {
			int size = sqs.size();
			Daiban db = new Daiban();
			db.setName("应急药品损益审批");
			db.setCount(size);
			db.setUrl("web/oa/os/myYpSySp");
			dbs.add(db);
		}
	}

	// 添加办公用品损益审批通知
	private void addWpsySp(List<Daiban> dbs, int empId, List<Role> empRoles) {
		List<OsSy> sqs = osMapper.findMySySp(empId, 1);// propMapper.findMyCgSp(empId);//new
														// ArrayList<ZcPropLy>();

		if (!sqs.isEmpty()) {
			int size = sqs.size();
			Daiban db = new Daiban();
			db.setName("办公用品损益审批");
			db.setCount(size);
			db.setUrl("web/oa/os/myWpSySp");
			dbs.add(db);
		}
	}

	// 添加应急药品采购审批通知
	private void addYpcgSp(List<Daiban> dbs, int empId, List<Role> empRoles) {
		List<OsCg> sqs = osMapper.findMyCgSp(empId, 2);// propMapper.findMyCgSp(empId);//new
														// ArrayList<ZcPropLy>();

		if (!sqs.isEmpty()) {
			int size = sqs.size();
			Daiban db = new Daiban();
			db.setName("应急药品采购审批");
			db.setCount(size);
			db.setUrl("web/oa/os/ypCgSp");
			dbs.add(db);
		}
	}

	// 添加办公用品采购审批通知
	private void addWpcgSp(List<Daiban> dbs, int empId, List<Role> empRoles) {
		List<OsCg> sqs = osMapper.findMyCgSp(empId, 1);// propMapper.findMyCgSp(empId);//new
														// ArrayList<ZcPropLy>();

		if (!sqs.isEmpty()) {
			int size = sqs.size();
			Daiban db = new Daiban();
			db.setName("办公用品采购审批");
			db.setCount(size);
			db.setUrl("web/oa/os/wpCgSp");
			dbs.add(db);
		}
	}

	// 添加资产采购审批通知
	private void addZccgSp(List<Daiban> dbs, int empId, List<Role> empRoles) {
		List<ZcPropCg> sqs = propMapper.findMyCgSp(empId);// new
															// ArrayList<ZcPropLy>();

		if (!sqs.isEmpty()) {
			int size = sqs.size();
			Daiban db = new Daiban();
			db.setName("资产采购审批");
			db.setCount(size);
			db.setUrl("web/oa/prop/myCgSp");
			dbs.add(db);
		}
	}

	// 添加资产领用审批通知
	private void addZclySp(List<Daiban> dbs, int empId, List<Role> empRoles) {
		List<ZcPropLy> sqs = propMapper.findMyLySp(empId);// new
															// ArrayList<ZcPropLy>();

		if (!sqs.isEmpty()) {
			int size = sqs.size();
			Daiban db = new Daiban();
			db.setName("资产申领审批");
			db.setCount(size);
			db.setUrl("web/oa/prop/myLySp");
			dbs.add(db);
		}
	}

	// 添加资产转移审批通知
	private void addZczySp(List<Daiban> dbs, int empId, List<Role> empRoles) {
		List<ZcPropZy> sqs = propMapper.findMyZySp(empId);// new
															// ArrayList<ZcPropLy>();

		if (!sqs.isEmpty()) {
			int size = sqs.size();
			Daiban db = new Daiban();
			db.setName("资产转移审批");
			db.setCount(size);
			db.setUrl("web/oa/prop/myZySp");
			dbs.add(db);
		}
	}

	// 添加资产维修审批通知
	private void addZcwxSp(List<Daiban> dbs, int empId, List<Role> empRoles) {
		List<ZcPropWb> sqs = propMapper.findMyWbSp(empId, 0);// new
																// ArrayList<ZcPropLy>();

		if (!sqs.isEmpty()) {
			int size = sqs.size();
			Daiban db = new Daiban();
			db.setName("资产维修审批");
			db.setCount(size);
			db.setUrl("web/oa/prop/myWxSp");
			dbs.add(db);
		}
	}

	// 添加资产注销审批通知
	private void addZczxSp(List<Daiban> dbs, int empId, List<Role> empRoles) {
		List<ZcPropWb> sqs = propMapper.findMyWbSp(empId, 1);// new
																// ArrayList<ZcPropLy>();

		if (!sqs.isEmpty()) {
			int size = sqs.size();
			Daiban db = new Daiban();
			db.setName("资产注销审批");
			db.setCount(size);
			db.setUrl("web/oa/prop/myZxSp");
			dbs.add(db);
		}
	}

	// 添加资产领用处理
	private void addZclyFF(List<Daiban> dbs, int empId, List<Role> empRoles) {

		if (RoleUtil.hasRole(empRoles, "固定资产管理员")) {
			List<ZcPropLy> sqs = propMapper.findUnhanderLySp();

			if (!sqs.isEmpty()) {
				int size = sqs.size();
				Daiban db = new Daiban();
				db.setName("固定资产发放");
				db.setCount(size);
				db.setUrl("web/oa/prop/ff");
				dbs.add(db);
			}
		}
	}

	// 添加资产归还处理
	private void addZcgh(List<Daiban> dbs, int empId, List<Role> empRoles) {

		if (RoleUtil.hasRole(empRoles, "固定资产管理员")) {
			List<ZcProperty> sqs = propMapper.findPropForGh();

			if (!sqs.isEmpty()) {
				int size = sqs.size();
				Daiban db = new Daiban();
				db.setName("固定资产归还");
				db.setCount(size);
				db.setUrl("web/oa/prop/gh");
				dbs.add(db);
			}
		}
	}

}
