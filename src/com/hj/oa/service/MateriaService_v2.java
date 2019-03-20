package com.hj.oa.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.commons.Constants;
import com.hj.commons.DbStatus;
import com.hj.commons.MateriaConstants;
import com.hj.commons.MateriaInsertType;
import com.hj.commons.ResultBean;
import com.hj.oa.Consts;
import com.hj.oa.bean.ApproveStaff;
import com.hj.oa.bean.Materia;
import com.hj.oa.bean.MateriaApply;
import com.hj.oa.bean.MateriaApprove;
import com.hj.oa.bean.MateriaCache;
import com.hj.oa.bean.MateriaCacheExample;
import com.hj.oa.bean.MateriaCost;
import com.hj.oa.bean.MateriaDept;
import com.hj.oa.bean.MateriaDetail;
import com.hj.oa.bean.MateriaExcel;
import com.hj.oa.bean.MateriaMould;
import com.hj.oa.bean.MateriaPurchase;
import com.hj.oa.bean.MateriaPurchaseDetail;
import com.hj.oa.bean.MateriaPurchaseDetail2;
import com.hj.oa.bean.MateriaPurchaseExample;
import com.hj.oa.bean.MateriaRecord;
import com.hj.oa.bean.MateriaSeq;
import com.hj.oa.bean.MateriaWarehouse;
import com.hj.oa.bean.MateriaWarehouseExample;
import com.hj.oa.bean.Mould;
import com.hj.oa.bean.User;
import com.hj.oa.dao.MateriaCacheMapper;
import com.hj.oa.dao.MateriaMapper_v2;
import com.hj.oa.dao.MateriaMouldMapper;
import com.hj.oa.dao.MateriaPurchaseMapper;
import com.hj.oa.dao.MateriaWarehouseMapper;
import com.hj.oa.dao.OfficeSupplyMapper;
import com.hj.oa.dao.UserMapper;
import com.hj.util.DateUtil;
import com.hj.util.FileUtil;
import com.hj.util.MailTableUtil;
import com.hj.util.MailUtil;
import com.hj.util.ReadExcelUtils;

/**
 * 物料service
 * 
 * @author wqfang
 *
 */
@Service
public class MateriaService_v2 {
	@Autowired
	MateriaMapper_v2 maMapper;
	@Autowired
	OfficeSupplyMapper osMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private MateriaCacheMapper maCacheMapper;
	@Autowired
	private MateriaPurchaseMapper maPurchaseMapper;
	@Autowired
	private MateriaMouldMapper maMouldMapper;
	@Autowired
	private MateriaWarehouseMapper maWarehouseMapper;
	@Autowired
	private MailTableUtil mailTableUtil;

	/**
	 * 根据仓库管理员ID查找对应仓库
	 * 
	 * @param adminId
	 * @return
	 */
	public List<MateriaDept> findWarehousesByAdminId(Integer adminId) {
		return this.maMapper.findWarehousesByAdminId(adminId);
	}

	/**
	 * 查询模板下载地址
	 * 
	 * @param type
	 * @return
	 */
	public String findMouldByType(String type) {
		return this.maMapper.findMouldByType(type);
	}

	/**
	 * 查询所有模板名称
	 * 
	 * @return
	 */
	public List<Mould> selectAllMould() {
		return this.maMapper.selectAllMould();
	}

	/**
	 * 上传模板
	 * 
	 * @param mo
	 */
	public void insertMould(Mould mo) {
		this.maMapper.insertMould(mo);
	}

	/**
	 * 上传模板
	 * 
	 * @param type
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public void uploadMould(String type, String newFileName) throws Exception {
		if (StringUtils.isBlank(newFileName)) {
			throw new RuntimeException("文件名为空");
		} else {
			Mould mo = new Mould();
			mo.setType(type);
			mo.setUrl(newFileName);
			this.maMapper.insertMould(mo);
		}
	}

	/**
	 * 物料录入
	 * 
	 * @param map
	 * @throws Exception
	 */
	public ResultBean<String> addMateria(Map<String, String> map, Integer receivedType, Integer warehouseId, User user,
			String timeCode) throws Exception {
		ResultBean<String> result = new ResultBean<String>();
		String msg = map.get("msg");
		String filePath = map.get("path") + "/" + map.get("url");
		if (Consts.SUCCESS.equals(msg)) {
			Integer i = 0;
			List<List<Map<String, String>>> lists = new ArrayList<List<Map<String, String>>>();
			try {
				lists = new ReadExcelUtils().readExcel2(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 插入入库记录
			String temp = System.currentTimeMillis() + "";
			// 添加到入库记录表
			MateriaDetail mr = new MateriaDetail();
			mr.setCreatedate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
			mr.setApplyId(user.getId());
			mr.setType(1);
			mr.setWarehouseId(warehouseId);
			// 入库类型
			mr.setCode(temp);
			this.maMapper.insertMateriaDetail(mr);
			for (List<Map<String, String>> map2 : lists) {
				String materiaCode = map2.get(0).get(Consts.MATERIA_CODE).trim();
				if (StringUtils.isEmpty(materiaCode)) {
					continue;
				}
				Integer length = materiaCode.length();
				if (length != 12 && length != 14) {
					result.setCode(ResultBean.FAIL);
					result.setMsg("序号为" + i + "所在行" + Consts.ERROR_EXCEL4);
					throw new RuntimeException("物料编码长度有误");
				} else {
					i++;
					Materia ma = new Materia();
					ma.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
					ma.setMateriaCode(materiaCode);
					// 判断数字是否存在
					String numStr = "";
					if (map2.get(11).containsKey(Consts.MATERIA_NUM)) {
						numStr = map2.get(11).get(Consts.MATERIA_NUM);
					} else {
						numStr = "0";
					}
					if (StringUtils.isEmpty(numStr)) {
						numStr = "0";
					}
					ma.setNum(Double.parseDouble(numStr));
					int code = result.getCode();
					if (code == ResultBean.SUCCESS) {
						// 判断物料是否存在
						Integer codeId = this.maMapper.findIdBymateriaCode(materiaCode);
						String codeStr = codeId == null ? "" : codeId.toString();
						// 不存在则录入
						String priceStr = map2.get(6).get(Consts.MATERIA_PRICE);
						if (!StringUtils.isEmpty(priceStr)) {
							ma.setPrice(Double.parseDouble(priceStr));
						}
						if (codeStr.isEmpty()) {
							String classfiyStr = map2.get(1).get(Consts.MATERIA_CLASSIFY);
							String brandStr = map2.get(2).get(Consts.MATERIA_BRAND);
							String specStr = map2.get(3).get(Consts.MATERIA_SPEC);
							String packageStr = map2.get(4).get(Consts.MATERIA_PACKAGE);
							String unitStr = map2.get(5).get(Consts.MATERIA_UNIT);
							String supplierStr = map2.get(7).get(Consts.MATERIA_SUPPLIER);
							String chineseShortStr = map2.get(8).get(Consts.MATERIA_CHINESESHORT);
							String englishShortStr = map2.get(9).get(Consts.MATERIA_ENGLISHSHORT);
							String functionDiffStr = map2.get(10).get(Consts.MATERIA_FUNCTIONDIFF);
							ma.setClassfiy(classfiyStr);
							ma.setBrand(brandStr);
							ma.setPackage1(packageStr);
							ma.setUnit(unitStr);
							ma.setSupplier(supplierStr);
							ma.setFunctionChina(chineseShortStr);
							ma.setFunctionEnglish(englishShortStr);
							ma.setDiff(functionDiffStr);
							ma.setSpec(specStr);
							// 插入录入信息
							this.maMapper.insertMatertia(ma);
						}
						codeId = this.maMapper.findIdBymateriaCode(materiaCode);
						// 插入库存数
						this.insertStock(codeId + "", warehouseId, ma.getNum(), ma.getPrice());
						// 插入库存变动记录详情
						addMateriaDetailNum(codeId.toString(), timeCode, ma.getNum());
					}
				}
			}
			if (i == 0) {
				result.setCode(ResultBean.FAIL);
				result.setMsg(Consts.ERROR_EXCEL3);
			}
		}
		// 删除文件
		FileUtil.deleteFile(filePath);
		return result;
	}

	/**
	 * 入库数据
	 */
	public void insertStock(String codeId, Integer warehouseId, Double num, Double price) {
		MateriaDetail md = new MateriaDetail();
		md.setMateriaCode(codeId);
		md.setWarehouseId(warehouseId);
		md.setNum(num);
		md.setCreatedate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		md.setPrice(price);
		this.maMapper.insertFirstStock(md);
	}

	/**
	 * 库存变化记录
	 */
	public void addMateriaDetail(Integer id, Integer warehouseId, String timeCode, Integer type) {
		MateriaDetail md = new MateriaDetail();
		md.setApplyId(id);
		md.setCreatedate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		md.setType(type);// 1是录入
		md.setWarehouseId(warehouseId);
		md.setCode(timeCode);
		this.maMapper.insertMateriaDetail(md);
	}

	/**
	 * 库存变化物料数量详情
	 */
	public void addMateriaDetailNum(String materiaCode, String timeCode, double num) {
		MateriaDetail md1 = new MateriaDetail();
		md1.setMateriaCode(materiaCode);
		md1.setCode(timeCode);
		md1.setNum(num);
		// 插入变动记录详情
		this.maMapper.insertMateriaDetailNum(md1);
	}

	/**
	 * 查询物料库存
	 * 
	 * @param warehouse
	 * @param materiaCode
	 * @return
	 */
	public Double findStock(Integer warehouseId, String materiaCode) {
		return this.maMapper.findStock(warehouseId, materiaCode);
	}

	/**
	 * 更新库存
	 */
	public void updateStock(Integer warehouseId, String materiaCode, double num) {
		this.maMapper.updateStock(warehouseId, materiaCode, num);
	}

	/**
	 * 根据仓库来添加物料库存
	 * 
	 * @param user
	 * @param materiaCode
	 */
	public void addStock(Integer warehouseId, String materiaCode, double num) {
		double stock = this.findStock(warehouseId, materiaCode);
		this.maMapper.updateStock(warehouseId, materiaCode, stock + num);
	}

	/**
	 * 插入库存变动记录
	 * 
	 * @param md
	 */
	public void insertMateriaDetail(MateriaDetail md) {
		this.maMapper.insertMateriaDetail(md);
	}

	/**
	 * 查询所有仓库
	 * 
	 * @param md
	 */
	public List<MateriaDept> findAllWarehouse() {
		return this.maMapper.findAllWarehouse();
	}

	/**
	 * 校验excel文件
	 * 
	 * @throws Exception
	 */
	public List<MateriaExcel> readExcel(Map<String, String> map, Integer warehouseId, Integer times, Integer type)
			throws Exception {
		List<MateriaExcel> list = new ArrayList<MateriaExcel>();
		String url = map.get("url");
		String path = map.get("path");
		String filePath = path + "/" + url;
		List<List<Map<String, String>>> lists = null;
		lists = new ReadExcelUtils().readExcel3(filePath, Consts.THREE);
		for (List<Map<String, String>> maps : lists) {
			MateriaExcel me = new MateriaExcel();
			me.setFlag(true);
			String materiaCode = "";
			if (maps.get(1).containsKey(Consts.MATERIA_CODE)) {
				materiaCode = maps.get(1).get(Consts.MATERIA_CODE);
			}
			if (StringUtils.isEmpty(materiaCode)) {
				break;
			}
			String numStr = "";
			int numRow = 0;
			if (type == 1) {
				numRow = 2;
			} else if (type == 2) {
				numRow = 6;
			}
			if (maps.get(numRow).containsKey(Consts.MATERIA_NUM)) {
				numStr = maps.get(numRow).get(Consts.MATERIA_NUM);
			} else {
				throw new RuntimeException("数量不能为空");
			}
			// 物料编号存在。
			me.setId(Integer.parseInt(maps.get(0).get(Consts.MATERIA_ID)));
			me.setMateriaCode(materiaCode);
			if (type == 1) {
				me.setItemNum(maps.get(3).get(Consts.MATERIA_ITEM));
				me.setSpec(maps.get(4).get(Consts.MATERIA_SPEC));
				me.setPackage1(maps.get(5).get(Consts.MATERIA_PACKAGE));
				me.setType(maps.get(6).get(Consts.MATERIA_TYPE));
				me.setClassify(maps.get(7).get(Consts.MATERIA_CLASSIFY));
				me.setBrand(maps.get(8).get(Consts.MATERIA_BRAND));
				me.setContent(maps.get(9).get(Consts.MATERIA_REMARK));
			} else if (type == 2) {
				me.setClassify(maps.get(2).get(Consts.MATERIA_CLASSIFY2));
				me.setSpec(maps.get(3).get(Consts.MATERIA_SPEC));
				me.setPackage1(maps.get(4).get(Consts.MATERIA_PACKAGE));
				me.setUnit(maps.get(5).get(Consts.MATERIA_UNIT));
				me.setContent(maps.get(7).get(Consts.MATERIA_REMARK));
			}
			me.setNum(Double.parseDouble(numStr));
			Integer codeId = this.maMapper.findIdBymateriaCode(materiaCode);
			String codeStr = codeId == null ? "" : codeId.toString();
			// 判断仓库是否存在该物料
			Integer n = this.maMapper.isExistCode(codeStr, warehouseId);
			double demandNum = me.getNum() * times;
			me.setDemandNum(demandNum);
			if (n > 0) {// 存在
				double stock = this.maMapper.findStock(warehouseId, codeStr);
				if (stock < demandNum) {
					me.setLackNum(demandNum - stock);
					String remark = Consts.MATERIA_LACK;
					List<MateriaDept> warehouses = this.maMapper.findAllWarehouse();
					for (MateriaDept wh : warehouses) {
						Double stock1 = this.maMapper.findStock(wh.getId(), codeStr);
						if (stock1 < demandNum) {
							remark += "," + wh.getWarehouse() + "库存不足";
						} else {
							remark += "," + wh.getWarehouse() + "库存充足";
						}
					}
					me.setRemark(remark);
					me.setFlag(false);
				} else {
					me.setLackNum(0.0);
				}
			} else if (n == 0) {
				me.setRemark(Consts.MATERIA_NOTEXIT);
				me.setFlag(false);
			}
			list.add(me);
		}
		return list;
	}

	/**
	 * 插入物料详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public void insertApplyDetail(HttpServletRequest request, String code, Integer warehouse) {
		MateriaDetail md = new MateriaDetail();
		md.setCode(code);
		String[] codes = request.getParameterValues("maCode");
		if (codes != null) {
			String[] nums = request.getParameterValues("num");
			for(int i = 0; i < codes.length; i++) {
				Integer codeId = this.maMapper.findIdBymateriaCode(codes[i]);
				String codeStr = codeId == null ? "" : codeId.toString();
				// 插入到申请表
				md.setNum(Double.parseDouble(nums[i]));
				md.setMateriaCode(codeStr);
				this.maMapper.insertApplyDetail(md);
				MateriaCache maCache = new MateriaCache();
				maCache.setApplyCode(code);
				maCache.setMateriaId(codeId);
				maCache.setNum(Double.parseDouble(nums[i]));
				maCache.setWarehouseId(warehouse);
				this.maCacheMapper.insertSelective(maCache);
			}
		}
	}
	
	/**
	 * @throws Exception 
	 * @Title: checkApplyStock   
	 * @Description: 检查库存是否充足
	 * @param: @param request
	 * @param: @param warehouse      
	 * @return: void      
	 * @throws
	 */
	public void checkApplyStock(HttpServletRequest request, Integer whId) throws Exception {
		String[] codes = request.getParameterValues("maCode");
		Map<Integer,Double> map = new HashMap<Integer, Double>();
		if (codes != null) {
			String[] nums = request.getParameterValues("num");
			for (int i = 0; i < codes.length; i++) {
				Integer codeId = this.maMapper.findIdBymateriaCode(codes[i]);
				Double num = Double.parseDouble(nums[i]);
				// 如果已经存在，则取之前剩下的库存
				if(map.containsKey(codeId)) {
					Double ns = map.get(codeId);
					map.put(codeId, ns - num);
				} else {
					Double stock = this.findCacheMateriaById(codeId, whId);
					map.put(codeId, (stock - num));
				}
			}
		
			// 检查是否库存不足
			for(Map.Entry<Integer, Double> entry : map.entrySet()) {
				if(entry.getValue() < 0) {
					Materia ma = this.maMapper.findMateriaInfoById2(entry.getKey());
					throw new RuntimeException("物料编码为： "+ ma.getMateriaCode() + " 的物料库存不足！");
				}
			}
		} else {
			throw new RuntimeException("数据异常");
		}
	}
	

	/**
	 * 插入物料详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<MateriaPurchaseDetail> insertApplyDetail2(HttpServletRequest request, String code, Integer whId) throws Exception {
		String[] codes = request.getParameterValues("maCode");
		List<MateriaPurchaseDetail> mpds = new ArrayList<MateriaPurchaseDetail>();
		if (codes != null) {
			String[] nums = request.getParameterValues("num");
			String[] specs = request.getParameterValues("spec");
			String[] classifys = request.getParameterValues("classify");
			String[] packages = request.getParameterValues("package1");
			for (int i = 0; i < codes.length; i++) {
				Double num = Double.parseDouble(nums[i]);
				MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
				mpd.setCode(code);
				mpd.setSpec(specs[i]);
				mpd.setClassify(classifys[i]);
				mpd.setPackage1(packages[i]);
				mpd.setMaCode(codes[i]);
				mpd.setNeedNum(num);
				this.maMapper.insertPurchaseDetail(mpd);
				mpds.add(mpd);
			}
		} else {
			throw new RuntimeException("数据异常");
		}
		return mpds;
	}

	// 插入申请人信息
	public void insertApplyMateria2(MateriaApply ma, User loginUser, String url2) {
		ma.setEmpId(loginUser.getId());
		ma.setDayStr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		String deptName = loginUser.getDeptName();
		Integer dId = this.osMapper.getDeptDirectorIdByName(deptName);
		Integer pId = this.osMapper.getDeptPIdByName(deptName);
		Integer mId = 1;
		ma.setdId(dId);
		ma.setpId(pId);
		if (dId == pId || dId == mId) {
			if (pId == mId) {
				ma.setStatus(3);
				ma.setCurrentId(pId);
			}
			ma.setStatus(2);
			ma.setCurrentId(pId);
		} else {
			ma.setStatus(1);
			ma.setCurrentId(dId);
		}
		ma.setmId(1);
		ma.setType(1);
		// 申领物料份数
		ma.setFaId(1);
		MateriaDept md = this.maMapper.findWarehouseById(ma.getWarehouseId());
		ma.setWarehouse(md.getWarehouse());
		ma.setaId(md.getAdminId());
		ma.setUrl2(url2);
		this.insertMaApply(ma);
		List<Materia> list = this.maMapper.findMtByCode(ma.getCode());
		this.updateSeq(ma.getCode(), MateriaInsertType.MATERIA_APPLY.getType());
		User user = this.userMapper.findById(ma.getCurrentId());
		String table = MailTableUtil.createApplyTable(ma, list);
		this.sendMailTable(user, loginUser, Consts.MAIL_APPLY, table);
	}

	// 插入申请人信息
	public void insertApplyMateria(MateriaApply ma, User loginUser, String url, String url2, Integer times,
			Integer type) {
		ma.setEmpId(loginUser.getId());
		ma.setDayStr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		String deptName = loginUser.getDeptName();
		Integer dId = this.osMapper.getDeptDirectorIdByName(deptName);
		Integer pId = this.osMapper.getDeptPIdByName(deptName);
		Integer mId = 1;
		ma.setdId(dId);
		ma.setpId(pId);
		if (dId == pId || dId == mId) {
			if (pId == mId) {
				ma.setStatus(3);
				ma.setCurrentId(pId);
			}
			ma.setStatus(2);
			ma.setCurrentId(pId);
		} else {
			ma.setStatus(1);
			ma.setCurrentId(dId);
		}
		ma.setmId(1);
		ma.setType(1);
		// 申领类型
		ma.setfId(type);
		// 申领物料份数
		ma.setFaId(times);
		MateriaDept md = this.maMapper.findWarehouseById(ma.getWarehouseId());
		ma.setWarehouse(md.getWarehouse());
		ma.setaId(md.getAdminId());
		ma.setUrl(url);
		this.insertMaApply(ma);
		User user = this.userMapper.findById(ma.getCurrentId());

		List<Materia> list = this.maMapper.findMtByCode(ma.getCode());
		String table = MailTableUtil.createApplyTable(ma, list);
		this.sendMailTable(user, loginUser, Consts.MAIL_APPLY, table);
	}

	/**
	 * 查询物料申请审批列表
	 * 
	 * @param id
	 * @return
	 */
	public List<MateriaApply> findApproveById(int id) {
		return this.maMapper.findApproveById(id);
	}

	/**
	 * 查询物料申请审批详情
	 * 
	 * @param id
	 * @return
	 */
	public MateriaApply findApplyById(int id) {
		return this.maMapper.findApplyById(id);
	}

	/**
	 * 插入申请记录
	 * 
	 * @return
	 */
	public void insertMaApply(MateriaApply ma) {
		this.maMapper.insertMaApply(ma);
	}

	/**
	 * 查询审批详情
	 * 
	 * @param id
	 * @return
	 */
	public List<Materia> findMtByCode(String code) {
		return this.maMapper.findMtByCode(code);
	}

	/**
	 * 物料申领审批
	 */
	public void maApprove(Integer id, String opinion, String sp) {
		// 判断状态
		MateriaApply ma = this.maMapper.findApplyById(id);
		Integer warehouseId = Integer.parseInt(ma.getWarehouse());
		MateriaDept md = this.maMapper.findWarehouseById(warehouseId);
		ma.setWarehouse(md.getWarehouse());
		List<Materia> materias = this.maMapper.findMtByCode(ma.getCode());
		String table = MailTableUtil.createApplyTable(ma, materias);
		User applyUser = this.userMapper.findById(ma.getEmpId());
		int status = ma.getStatus();
		MateriaApply mta = new MateriaApply();
		mta.setId(ma.getId());
		String now = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt);
		if (status == 1) {
			mta.setdDayStr(now);
			mta.setdOpinion(opinion);
			mta.setdResult(sp);
			if ("审批通过".equals(sp)) {
				if (ma.getpId() == ma.getmId()) {
					mta.setStatus(3);
					mta.setCurrentId(ma.getmId());
				} else {
					mta.setStatus(2);
					mta.setCurrentId(ma.getpId());
				}
			} else {
				mta.setStatus(-1);
			}
		} else if (status == 2) {
			mta.setpDayStr(now);
			mta.setpOpinion(opinion);
			mta.setpResult(sp);
			if ("审批通过".equals(sp)) {
				mta.setStatus(3);
				mta.setCurrentId(ma.getmId());
			} else {
				mta.setStatus(-1);
			}
		} else if (status == 3) {
			mta.setmDayStr(now);
			mta.setmOpinion(opinion);
			mta.setmResult(sp);
			if ("审批通过".equals(sp)) {
				mta.setStatus(4);
				mta.setCurrentId(ma.getaId());
			} else {
				mta.setStatus(-1);
			}
		} else if (status == 4) {
			mta.setaDayStr(now);
			mta.setaOpinion(opinion);
			mta.setaResult(sp);
			if ("审批通过".equals(sp)) {
				mta.setStatus(5);
				List<MateriaDetail> lists = this.maMapper.findApplyByCode(ma.getCode());
				// 添加消耗记录
				String timeCode = System.currentTimeMillis() + "";
				this.addMateriaDetail(applyUser.getId(), warehouseId, timeCode, 2);
				
				for (MateriaDetail list : lists) {
					String materiaCode = list.getMateriaCode();
					double num = list.getNum();
					// 消耗记录详情
					this.addMateriaDetailNum(materiaCode, timeCode, num);
					// 减去库存和插入消耗记录
					this.updateStockRecord(Integer.parseInt(materiaCode), num, warehouseId, ma.getCode());
				}
			} else {
				mta.setStatus(-1);
			}
		}
		int statu = mta.getStatus();
		if (statu > 0 && statu < 5) {
			User user = this.userMapper.findById(mta.getCurrentId());
			this.sendMailTable(user, applyUser, Consts.MAIL_APPLY, table);
		} else {
			// 流程完成去除缓存库存
			this.clearMateriaCache(ma.getCode());
			this.sendMaileResult2(applyUser, mta.getStatus(), Consts.MAIL_APPLY, table);
		}
		this.maMapper.updateApply(mta);
	}

	/**
	 * 减去库存和插入消耗记录
	 */
	public Double updateStockRecord(Integer maId, Double num, Integer whId, String code) {
		// 遍历物料编码和数量
		Double cost = 0.0;
		// 查询所有库存不为零的物料信息
		List<MateriaCost> mcs = this.maMapper.findPioneerStock(maId, whId);
		int i = 0;
		while (num > 0) {
			double currentStock = mcs.get(i).getMt_stock();
			double currentPrice = mcs.get(i).getMt_price();
			Integer currentId = mcs.get(i).getId();
			if (num < currentStock) {
				cost += num * currentPrice;
				// 减去库存，并插入记录
				this.maMapper.updateMaStock(currentId, num);
				this.insertMaCost(code, maId, whId, num, currentPrice);
			} else {
				cost += currentStock * currentPrice;
				this.maMapper.updateMaStock(currentId, currentStock);
				this.insertMaCost(code, maId, whId, currentStock, currentPrice);
			}
			num -= currentStock;
			i++;
		}
		return cost;
	}

	/**
	 * 插入申领花费详情
	 */
	public void insertMaCost(String code, Integer maId, Integer whId, Double num, Double price) {
		MateriaCost mc = new MateriaCost();
		mc.setCost_code(code);
		mc.setMateriaId(maId);
		mc.setWarehouseId(whId);
		mc.setCost_num(num);
		mc.setCost_price(price);
		this.maMapper.insertMaCost(mc);
	}

	/**
	 * 查询物料审批记录
	 * 
	 * @param id
	 * @return
	 */
	public List<MateriaApply> findMySpRecord(int id) {
		return this.maMapper.findMySpRecord(id);
	}

	public List<Integer> findMySpIds(int id) {
		return this.maMapper.findMySpIds(id);
	}

	/**
	 * 查询审批详情
	 * 
	 * @param id
	 * @return
	 */
	public List<MateriaApply> findMyApplyRecord(int id) {
		return this.maMapper.findMyApplyRecord(id);
	}

	/**
	 * 物料申请信息详情
	 * 
	 * @param user收件人
	 * @param user2
	 *            发件人
	 * @param type
	 *            邮件类型
	 * @param table
	 */
	private void sendMailTable(User user, User user2, String type, String table) {
		String toName = user.getName();
		String fromName = user2.getName();
		String to = user.getEmail();
		String deptName = user2.getDeptName();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append(deptName).append(fromName).append("向您提交了一个").append(type)
				.append("，请及时登录系统审批。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批通知", text);
	}

	private void sendMaileResult2(User user, int status, String type, String table) {
		String toName = user.getName();
		String to = user.getEmail();
		String result = status == -1 ? "<a style='color:green;'>未通过审批</a>" : "<a style='color:red;'>已通过审批</a>";
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append("您的一个").append(type).append(result);
		sb.append("，请登录系统查看。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批结果提醒", text);
	}

	@SuppressWarnings("rawtypes")
	public List<MateriaRecord> findAllInware(Map map) {
		return this.maMapper.findAllInware(map);
	}

	public Integer findtotalInware(MateriaRecord mr) {
		return this.maMapper.findtotalInware(mr);
	}

	@SuppressWarnings("rawtypes")
	public List<MateriaRecord> findAllCost(Map map) {
		return this.maMapper.findAllCost(map);
	}

	public Integer findtTotalCost(MateriaRecord ma) {
		return this.maMapper.findtTotalCost(ma);
	}

	public List<Materia> findAllStock(Materia ma) {
		return this.maMapper.findAllStock(ma);
	}

	public Integer findStockLength(Materia mm) {
		return this.maMapper.findStockLength(mm);
	}

	public List<Materia> findByCurPage(Integer beginIndex, Integer pageSize, String maCode, String spec, String all) {
		return this.maMapper.findByCurPage(beginIndex, pageSize, maCode, spec, all);
	}

	public MateriaDept findWarehouseById(Integer id) {
		return this.maMapper.findWarehouseById(id);
	}

	public Materia findMateriaInfoById(Integer id, String whId) {
		return this.maMapper.findMateriaInfoById(id, whId);
	}
	
	public Double findMateriaPrice(Integer id) {
		return this.maMapper.findMateriaPrice(id);
	}

	public Materia findMateriaInfoById2(Integer id) {
		return this.maMapper.findMateriaInfoById2(id);
	}

	public Integer findIdBymateriaCode2(String materiaCode) {
		return this.maMapper.findIdBymateriaCode2(materiaCode);
	}

	public void updateMateriaInfo(Materia ma) {
		this.maMapper.updateMateriaInfo(ma);
	}

	public void doInware(Integer id, String[] maCodes, String[] prices, String[] costs, String[] classifys,
			String[] brands, String[] packages, String[] buynums, String[] specs, String[] units) throws Exception {
		MateriaPurchase mp = this.maMapper.selectMateriaPurchaseById(id);
		double w = mp.getCountMoney();
		Integer warehouseId = (int) w;
		String temp = System.currentTimeMillis() + "";
		// 添加到入库记录表
		MateriaRecord mr = new MateriaRecord();
		mr.setDayStr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		mr.setPersonId(mp.getCurrentId());
		mr.setType(1);
		mr.setWarehouseId(warehouseId);
		// 入库类型
		mr.setReceivedType(Integer.parseInt(mp.getReason()));
		mr.setTemp(mp.getRequisitionCode());
		this.addMateriaDetail(mp.getCurrentId(), warehouseId, temp, 1);
		for (int i = 0; i < maCodes.length; i++) {
			Integer codeId = this.maMapper.findIdBymateriaCode(maCodes[i]);
			String codeStr = codeId == null ? "" : codeId.toString();
			Materia ma = new Materia();
			Double price = Double.parseDouble(prices[i]) + Double.parseDouble(costs[i]);
			Double num = Double.parseDouble(buynums[i]);
			ma.setPrice(price);
			ma.setMateriaCode(maCodes[i]);
			ma.setNum(num);
			if (codeStr.isEmpty()) {
				ma.setClassfiy(classifys[i]);
				ma.setBrand(brands[i]);
				ma.setSpec(specs[i]);
				ma.setPackage1(packages[i]);
				ma.setUnit(units[i]);
				ma.setNum(num);
				ma.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
				this.maMapper.insertMatertia(ma);
				codeId = this.maMapper.findIdBymateriaCode(maCodes[i]);
			}
			this.insertStock(codeId + "", warehouseId, num, price);
			// 插入库存数
			// 插入库存变动记录详情
			addMateriaDetailNum(codeId.toString(), temp, ma.getNum());
		}
	}

	/**
	 * 检查excel文件
	 * 
	 * @throws Exception
	 */
	public List<MateriaPurchaseDetail> checkExcel(List<List<Map<String, String>>> lists,
			List<MateriaPurchaseDetail> mpds, String code) throws Exception {
		// 校验文件格式
		List<Map<String, String>> test = lists.get(0);
		Boolean flag = test.get(0).containsKey(Consts.MATERIA_CODE) && test.get(1).containsKey(Consts.MATERIA_CLASSIFY)
				&& test.get(2).containsKey(Consts.MATERIA_BRAND) && test.get(3).containsKey(Consts.MATERIA_SPEC)
				&& test.get(4).containsKey(Consts.MATERIA_PACKAGE) && test.get(5).containsKey(Consts.MATERIA_UNIT)
				&& test.get(6).containsKey(Consts.MATERIA_PRICE) && test.get(7).containsKey("供应商")
				&& test.get(8).containsKey("功能简称（中）") && test.get(9).containsKey("功能简称（英）")
				&& test.get(10).containsKey("功能区分") && test.get(11).containsKey(Consts.MATERIA_NUM)
				&& test.get(12).containsKey(Consts.MATERIA_OTHERCOST);
		if (!flag) {
			throw new RuntimeException(MateriaConstants.ERROR_MOUDLE);
		}
		for (List<Map<String, String>> list : lists) {
			// 过滤空list
			String maCode = list.get(0).get(Consts.MATERIA_CODE);
			if (StringUtils.isBlank(maCode)) {
				break;
			}
			MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
			mpd.setMaCode(maCode);
			mpd.setClassify(list.get(1).get(Consts.MATERIA_CLASSIFY));
			mpd.setBrand(list.get(2).get(Consts.MATERIA_BRAND));
			mpd.setSpec(list.get(3).get(Consts.MATERIA_SPEC));
			mpd.setPackage1(list.get(4).get(Consts.MATERIA_PACKAGE));
			mpd.setUnit(list.get(5).get(Consts.MATERIA_UNIT));
			String price = list.get(6).get(Consts.MATERIA_PRICE);
			mpd.setPrice(Double.parseDouble(StringUtils.isBlank(price) ? "0" : price));
			String num = list.get(11).get(Consts.MATERIA_NUM);
			Double num2 = Double.parseDouble(StringUtils.isBlank(num) ? "0" : num);
			if (num2 <= 0) {
				throw new RuntimeException(MateriaConstants.ERROR_NUM);
			}
			mpd.setBuynum(num2);
			String other = list.get(12).get(Consts.MATERIA_OTHERCOST);
			mpd.setCost(Double.parseDouble(StringUtils.isBlank(other) ? "0" : other));
			mpd.setCode(code);
			mpds.add(mpd);
		}
		return mpds;
	}

	public List<MateriaPurchaseDetail> addInwareInfo(HttpServletRequest req, List<MateriaPurchaseDetail> mpds,
			String[] codes, String code) {
		String[] classifys = req.getParameterValues("classfiy");
		String[] brands = req.getParameterValues("brand");
		String[] specs = req.getParameterValues("spec");
		String[] packages = req.getParameterValues("package1");
		String[] units = req.getParameterValues("unit");
		String[] suppliers = req.getParameterValues("supplier");
		String[] prices = req.getParameterValues("price");
		String[] costs = req.getParameterValues("cost");
		String[] nums = req.getParameterValues("num");
		for (int i = 0; i < codes.length; i++) {
			MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
			mpd.setCode(code);
			mpd.setMaCode(codes[i]);
			mpd.setClassify(classifys[i]);
			mpd.setBrand(brands[i]);
			mpd.setSpec(specs[i]);
			mpd.setPackage1(packages[i]);
			mpd.setUnit(units[i]);
			mpd.setSupplier(suppliers[i]);
			mpd.setPrice(Double.parseDouble(prices[i]));
			mpd.setCost(Double.parseDouble(costs[i]));
			mpd.setBuynum(Double.parseDouble(nums[i]));
			mpds.add(mpd);
		}
		return mpds;
	}

	/**
	 * 其他入库申请
	 * 
	 * @throws Exception
	 */
	public void inwareElse(User loginUser, String code, String bz, String url, Integer type, ApproveStaff as,
			String url2, Integer jyr, String wl, List<MateriaPurchaseDetail> mpds) throws Exception {
		// 入库编码requisitionCode、仓库URL2、入库类型reason
		List<User> users = this.userMapper.findUserByRole(Consts.STOREWARE_MANAGER);
		if (users.isEmpty()) {
			throw new RuntimeException("仓库管理员未设置");
		}
		MateriaPurchase mp = new MateriaPurchase();
		mp.setEmpId(loginUser.getId());
		mp.setDaystr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		mp.setRequisitionCode(code);
		mp.setType(MateriaInsertType.MATERIA_INWARE.getType());
		mp.setContent(bz);
		mp.setUrl(url);
		mp.setUrl2(url2);
		mp.setReason(type + "");
		mp.setCurrentId(jyr);
		mp.setStatus(1);
		if("研发物料".equals(wl)) {
			List<User> user = this.userMapper.findUserByRole(Constants.MATERIA_MANAGE);
			mp.setCurrentId(user.get(0).getId());
			mp.setStatus(3);
		}
		mp.setProjectCode(jyr.toString());
		mp.setPurchaseCode(wl);
		this.maMapper.insertPurchase(mp);
		this.updateSeq(code, MateriaInsertType.MATERIA_INWARE.getType());
		User user = this.userMapper.findById(mp.getCurrentId());
		String table = MailTableUtil.createInwareTable(mp, mpds, 1, user, false);
		this.sendMailTable(user, loginUser, Consts.INWARE_APPLY, table);
	}

	/**
	 * 其他入库申请详情
	 * 
	 * @throws Exception
	 */
	public void inwareElseDetail(List<MateriaPurchaseDetail> mpds) throws Exception {
		if (!mpds.isEmpty()) {
			for (MateriaPurchaseDetail mpd : mpds) {
				this.maMapper.insertPurchaseDetail(mpd);
			}
		} else {
			throw new RuntimeException("物料列表为空");
		}
	}

	public List<MateriaApply> findMyInwareList(int id) {
		return this.maMapper.findMyInwareList(id);
	}

	public List<MateriaApply> findInwareRecordList2(int id) {
		return this.maMapper.findInwareRecordList2(id);
	}

	/**
	 * 入库审批
	 * 
	 * @throws Exception
	 */
	public void inwareApprove(MateriaPurchase mps, String opinion, Integer id, HttpServletRequest req, String sp,
			ApproveStaff as,String url2, String wllx) throws Exception {
		Integer status = mps.getStatus();
		// 插入审批详情9O7
		MateriaApprove ma = new MateriaApprove();
		ma.setCode(mps.getRequisitionCode());
		ma.setApproveId(mps.getCurrentId());
		ma.setApproveStatus(status);
		ma.setApproveResult(sp);
		ma.setApproveDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		ma.setApproveOpinion(opinion);
		this.maMapper.insertMateriaApproveSelective(ma);
		// 仓库管理员
		List<User> users = this.userMapper.findUserByRole(MateriaConstants.MATERIA_MANAGE);
		MateriaPurchase mp = new MateriaPurchase();
		// 仓库id放在CountMoney
				switch (status) {
					case 1:
						if ("通过".equals(sp)) {
							mp.setUrl2(url2);
							mp.setPurchaseCode(wllx);
							String[] ids = req.getParameterValues("ids");
							String[] links = req.getParameterValues("link");
							mp.setCurrentId(DbStatus.FREEZE.getType());
							mp.setStatus(DbStatus.FREEZE.getType());
							boolean flag = false;
							for(int i = 0; i < ids.length; i++) {
								if("合格".equals(links[i]) || "挑选使用".equals(links[i]) || "特采".equals(links[i])) {
									flag = true;
								}
								MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
								mpd.setId(Integer.parseInt(ids[i]));
								mpd.setLink(links[i]);
								this.maMapper.updateMateriaPurchaseDetailBySelective(mpd);
							}
							if(flag) {
								mp.setCurrentId(users.get(0).getId());
								mp.setStatus(status + 2);
							}
							break;
						} else {
							mp.setCurrentId(0);
							mp.setStatus(-1);
							break;
						}
					case 3:
						String[] maCodes = req.getParameterValues("maCode");
						if (maCodes != null) {
							String[] ids = req.getParameterValues("ids");
							String[] realNum = req.getParameterValues("realNum");
							String[] whIds = req.getParameterValues("warehouseId");
							String[] prices = req.getParameterValues("price");
							
							// 将入库的物料标记为已处理
							for(int i = 0; i < maCodes.length; i++) {
								// 更新物料库存
								Integer codeId = this.maMapper.findIdBymateriaCode(maCodes[i]);
								if(codeId == null) {
									throw new RuntimeException("物料编码为： " + maCodes[i] +  " 的物料不存在，请核对后入库！");
								}
								this.insertStock(codeId + "", Integer.parseInt(whIds[i]), Double.parseDouble(realNum[i]), Double.parseDouble(prices[i]));
								// 增加入库记录
								
								MateriaDetail md = new MateriaDetail();
								md.setCreatedate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
								md.setApplyId(users.get(0).getId());
								md.setType(1);
								md.setWarehouseId(Integer.parseInt(whIds[i]));
								// 入库类型
								String temp = System.currentTimeMillis() + "";
								md.setCode(temp);
								this.maMapper.insertMateriaDetail(md);
								addMateriaDetailNum(codeId + "", temp, Double.parseDouble(realNum[i]));
							}
							
							for(int i = 0; i < ids.length; i++) {
								MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
								mpd.setId(Integer.parseInt(ids[i]));
								mpd.setNeedNum(Double.parseDouble(realNum[i]));
								mpd.setNeedDate(maCodes[i]);
								mpd.setUseDate(whIds[i]);
								this.maMapper.updateMateriaPurchaseDetailBySelective(mpd);
							}
						} else {
							throw new RuntimeException("物料列表为空");
						}
						mp.setCurrentId(0);
						mp.setStatus(status + 1);
						break;
					default:
						throw new RuntimeException("系统出错");
				}
		List<MateriaPurchaseDetail> mpds = this.selectMateriaPurchaseDetailByCode(mps.getRequisitionCode());
		User zj = this.userMapper.findById(mps.getCurrentId());
		User user = this.userMapper.findById(mps.getEmpId());
		int statu = mp.getStatus();
		String table = MailTableUtil.createInwareTable(mps, mpds, statu, zj, false);
		if (statu > 0 && statu < 4) {
			User user2 = this.userMapper.findById(mp.getCurrentId());
			this.sendMailTable(user2, user, Consts.INWARE_APPLY, table);
		} else if (statu == 4 || statu == -1) {
			this.sendMaileResult2(user, statu, Consts.INWARE_APPLY, table);
		}
		if(statu == 4) {
			for(MateriaPurchaseDetail mpd : mpds) {
				Integer typpe = Integer.parseInt(mpd.getUseDate());
				MateriaWarehouse mh = this.maWarehouseMapper.selectByPrimaryKey(typpe);
				mpd.setUseDate(mh.getWarehouse());
			}
			String table2 = MailTableUtil.createInwareTable(mps, mpds, statu, zj, true);
			List<User> users2 = this.userMapper.findUserByRole(Constants.EXTERNAL_TREASURER2);
			this.mailTableUtil.sendInboundNotice(users2.get(0), user, Consts.INWARE_APPLY, table2);
		}
		mp.setId(mps.getId());
		this.maMapper.updateMateriaPurchaseByPrimaryKeySelective(mp);
	}

	public void updatePurchaseDetail(Integer[] ids, String[] maCodes, String[] nums) {
		for (int i = 0; i < ids.length; i++) {
			MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
			mpd.setId(ids[i]);
			mpd.setMaCode(maCodes[i]);
			mpd.setNeedNum(Double.parseDouble(nums[i]));
			this.maMapper.updateMateriaPurchaseDetailBySelective(mpd);
		}
	}

	public void updateIsDeal(int id) {
		this.maMapper.updateIsDeal(id);
	}

	/**
	 * 计算申领花费，按照不同入库价格计算
	 * 
	 * @param list
	 * @return
	 */
	public double countCost(List<Materia> list, Integer whId) {
		// 遍历物料编码和数量
		Double cost = 0.0;
		if (!list.isEmpty()) {
			for (Materia ma : list) {
				Double needNum = ma.getNum();
				Integer maId = this.maMapper.findIdBymateriaCode(ma.getMateriaCode());
				// 查询所有库存不为零的物料信息
				List<MateriaCost> mcs = this.maMapper.findPioneerStock(maId, whId);
				int i = 0;
				while (needNum > 0) {
					double currentStock = mcs.get(i).getMt_stock();
					double currentPrice = mcs.get(i).getMt_price();
					cost += needNum < currentStock ? needNum * currentPrice : currentStock * currentPrice;
					needNum -= currentStock;
					i++;
				}
			}
		}
		return cost;
	}

	/**
	 * 获取采购编码
	 * 
	 * @param str
	 */
	public String getPurchaseCode(String str, Integer type) {
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

	public void insertPurchase(MateriaPurchase record) {
		this.maMapper.insertPurchase(record);
	}

	public void insertPurchaseDetail(MateriaPurchaseDetail record) {
		this.maMapper.insertPurchaseDetail(record);
	}

	public void updateMateriaSeq(MateriaSeq ms) {
		this.maMapper.updateMateriaSeq(ms);
	}

	public void savePurchase(MateriaPurchaseDetail2 mpds, String number, String bz, String reason, String countMoney,
			Integer id, ApproveStaff as, String requisitionCode, String url) throws RuntimeException{
		MateriaPurchase mp = new MateriaPurchase();
		mp.setDaystr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		mp.setEmpId(id);
		mp.setPurchaseCode(number);
		mp.setContent(bz);
		mp.setCountMoney(Double.parseDouble(countMoney));
		mp.setReason(reason);
		mp.setUrl(url);
		// 判断初始状态
		int initStatus = 1;
		int initId = as.getDeptDirector();
		if (as.getDeptDirector() == as.getDeptSupremo()) {
			initStatus = 2;
			initId = as.getDeptSupremo();
		} else if (as.getDeptDirector() == as.getManager()) {
			initStatus = 2;
			initId = as.getDeptSupremo();
		}
		mp.setCurrentId(initId);
		mp.setStatus(initStatus);
		mp.setType(1);
		mp.setRequisitionCode(requisitionCode);
		this.maMapper.insertPurchase(mp);
		for (int i = 0; i < mpds.getMaCode().length; i++) {
			MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
			mpd.setCode(number);
			mpd.setMaCode(mpds.getMaCode()[i]);
			mpd.setBrand(mpds.getBrand()[i]);
			mpd.setClassify(mpds.getClassify()[i]);
			mpd.setSpec(mpds.getSpec()[i]);
			mpd.setPackage1(mpds.getPackage1()[i]);
			mpd.setUnit(mpds.getUnit()[i]);
			mpd.setNeedNum(mpds.getNeedNum()[i]);
			mpd.setNeedDate(mpds.getNeedDate()[i]);
			mpd.setUseDate(mpds.getUseDate()[i]);
			mpd.setBuynum(mpds.getBuynum()[i]);
			mpd.setPrice(mpds.getPrice()[i]);
			mpd.setCost(mpds.getCost()[i]);
			mpd.setSupplier(mpds.getSupplier()[i]);
			mpd.setLink(mpds.getLink()[i]);
			this.maMapper.insertPurchaseDetail(mpd);
		}
		this.updateSeq(number, MateriaInsertType.MATERIA_PURCHASE.getType());
		String table = MailTableUtil.createPurchaseTable(mp, mp.getCountMoney());
		User user = this.userMapper.findById(mp.getCurrentId());
		User user2 = this.userMapper.findById(id);
		this.sendMailTable(user, user2, Consts.MAIL_PURCHASE2, table);
	}

	public void updateSeq(String number, Integer id) {
		// 更新seq
		MateriaSeq ms = new MateriaSeq();
		ms.setId(id);
		ms.setCurrentId(Integer.parseInt(number.substring(10, 13)));
		ms.setDate(number.substring(2, 10));
		this.maMapper.updateMateriaSeq(ms);
	}

	public List<MateriaPurchase> findPurchaseApprove(Integer userId, Integer type) {
		return this.maMapper.findPurchaseApprove(userId, type);
	}
	
	public List<MateriaPurchase> findInboundRecord(Integer userId, Integer type) {
		return this.maMapper.findInboundRecord(userId, type);
	}
	
	public List<MateriaPurchase> findPurchaseApproveRecord() {
		return this.maMapper.findPurchaseApproveRecord();
	}
	
	public List<MateriaPurchase> findReventDealRecord() {
		return this.maMapper.findReventDealRecord();
	}

	public List<MateriaPurchase> findMyPurchaseApprove(Integer userId, Integer type) {
		return this.maMapper.findMyPurchaseApprove(userId, type);
	}

	public List<MateriaPurchase> findRkRecord() {
		return this.maMapper.findRkRecord();
	}

	public List<MateriaPurchase> findMyPurchaseRecord(Integer userId, Integer type) {
		return this.maMapper.findMyPurchaseRecord(userId, type);
	}
	
	public List<MateriaPurchase> findAllPurchaseRecordByType(Integer type) {
		return this.maMapper.findAllPurchaseRecordByType(type);
	}
	
	public List<MateriaPurchase> findRejectList(Integer userId, Integer type, Integer beginIndex, Integer endIndex) {
		return this.maMapper.findRejectList(userId, type, beginIndex, endIndex);
	}

	public List<MateriaPurchase> findMyPurchaseRecord2(Integer userId, Integer type) {
		return this.maMapper.findMyPurchaseRecord2(userId, type);
	}

	public MateriaPurchase selectMateriaPurchaseById(Integer userId) {
		return this.maMapper.selectMateriaPurchaseById(userId);
	}

	public List<MateriaPurchase> selectMateriaPurchaseByStatus(Integer status) {
		return this.maMapper.selectMateriaPurchaseByStatus(status);
	}

	public List<MateriaPurchaseDetail> selectMateriaPurchaseDetailByCode(String code) {
		return this.maMapper.selectMateriaPurchaseDetailByCode(code);
	}

	public MateriaPurchaseDetail selectMateriaPurchaseDetailById(Integer id) {
		return this.maMapper.selectMateriaPurchaseDetailById(id);
	}

	public void updateMateriaPurchaseById(Integer id) {
		this.maMapper.updateMateriaPurchaseById(id);
	}

	public List<MateriaApprove> selectMateriaApproveByPrimaryKey(String code) {
		return this.maMapper.selectMateriaApproveByPrimaryKey(code);
	}

	public void purApprove(MateriaPurchase mps, String sp, String opinion, ApproveStaff as) throws Exception {
		Integer status = mps.getStatus();
		// 插入审批详情
		MateriaApprove ma = new MateriaApprove();
		ma.setCode(mps.getPurchaseCode());
		ma.setApproveId(mps.getCurrentId());
		ma.setApproveStatus(status);
		ma.setApproveResult(sp);
		ma.setApproveDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		ma.setApproveOpinion(opinion);
		this.maMapper.insertMateriaApproveSelective(ma);
		MateriaPurchase mp = new MateriaPurchase();
		if ("通过".equals(sp)) {
			switch (status) {
			case 1:
				if (as.getDeptSupremo() == as.getManager()) {
					mp.setCurrentId(as.getTreasurer());
					mp.setStatus(status + 2);
				} else {
					mp.setCurrentId(as.getDeptSupremo());
					mp.setStatus(status + 1);
				}
				break;
			case 2:
				mp.setCurrentId(as.getTreasurer());
				mp.setStatus(status + 1);
				break;
			case 3:
				mp.setCurrentId(as.getManager());
				mp.setStatus(status + 1);
				break;
			case 4:
				mp.setCurrentId(0);
				mp.setStatus(status + 1);
				break;
			default:
				throw new RuntimeException("系统出错");
			}
		} else {
			mp.setCurrentId(0);
			mp.setStatus(-1);
		}
		List<MateriaPurchaseDetail> mpds = this.maMapper.selectMateriaPurchaseDetailByCode(mps.getPurchaseCode());
		double zj = 0.0;
		for (MateriaPurchaseDetail mpd : mpds) {
			zj += mpd.getNeedNum() * mpd.getPrice();
		}
		String table = MailTableUtil.createPurchaseTable(mps, zj);
		User user = this.userMapper.findById(mps.getEmpId());
		User user2 = this.userMapper.findById(mp.getCurrentId());
		int statu = mp.getStatus();
		if (statu > 0 && statu < 5) {
			this.sendMailTable(user2, user, Consts.MAIL_PURCHASE2, table);
		} else {
			this.sendMaileResult2(user, statu, Consts.MAIL_PURCHASE2, table);
		}
		mp.setId(mps.getId());
		this.maMapper.updateMateriaPurchaseByPrimaryKeySelective(mp);
	}

	/**
	 * 插入请购详情
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void saveRequestion(ApproveStaff as, MateriaPurchase mp, List<List<Map<String, String>>> lists,
			String number, List<MateriaPurchaseDetail> mpds) throws Exception {
		// 校验物料请购文件
		if (!lists.isEmpty() || lists == null) {
			List<Map<String, String>> list = lists.get(0);
			boolean flag = list.get(0).containsKey(Consts.MATERIA_ID) && list.get(1).containsKey(Consts.MATERIA_CODE)
					&& list.get(2).containsKey(Consts.MATERIA_CLASSIFY) && list.get(3).containsKey(Consts.MATERIA_BRAND)
					&& list.get(4).containsKey("型号规格") && list.get(5).containsKey(Consts.MATERIA_PACKAGE)
					&& list.get(6).containsKey(Consts.MATERIA_UNIT) && list.get(7).containsKey("其他参数要求描述")
					&& list.get(8).containsKey(Consts.MATERIA_DEMANDNUM)
					&& list.get(9).containsKey(Consts.MATERIA_ARRIVALDATE) && list.get(10).containsKey("预计单价");
			if (!flag) {
				throw new RuntimeException(MateriaConstants.ERROR_MOUDLE);
			}
		}

		// 插入上传文件的物料信息
		if (!lists.isEmpty()) {
			for (List<Map<String, String>> list : lists) {
				String materiaCode = list.get(1).get("物料编码");
				if (StringUtils.isBlank(materiaCode)) {
					break;
				}
				String classify = list.get(2).get("品名分类");
				String brand = list.get(3).get("品牌");
				String spec = list.get(4).get("型号规格");
				String package1 = list.get(5).get("封装");
				String unit = list.get(6).get("单位");
				String other = list.get(7).get("其他参数要求描述");
				String needNum = list.get(8).get("需求数量");
				String needDate = list.get(9).get("需求日期");
				String price = list.get(10).get("预计单价");
				MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
				mpd.setMaCode(materiaCode);
				mpd.setCode(number);
				mpd.setClassify(classify);
				mpd.setBrand(brand);
				mpd.setSpec(spec);
				mpd.setPackage1(package1);
				mpd.setUnit(unit);
				mpd.setNeedNum(Double.parseDouble(StringUtils.isBlank(needNum) ? "0" : needNum));
				if (StringUtils.isBlank(needDate)) {
					throw new RuntimeException("请购文件日期不能为空");
				}
				try {
					Date date = DateUtil.stringToDate(needDate, "yyyy.MM.dd");
				} catch (Exception e) {
					throw new RuntimeException("请购文件日期格式错误");
				}
				mpd.setNeedDate(needDate);
				mpd.setPrice(Double.parseDouble(StringUtils.isBlank(price) ? "0" : price));
				mpd.setLink(other);
				mpds.add(mpd);
			}
		}
		if (mpds.isEmpty()) {
			throw new RuntimeException("请购文件为空");
		}
		double totalprice = 0.0;
		for (MateriaPurchaseDetail mpd : mpds) {
			totalprice += mpd.getNeedNum() * mpd.getPrice();
			this.maMapper.insertPurchaseDetail(mpd);
		}
		// 判断初始状态
		int initStatus = 1;
		int initId = as.getDeptDirector();
		if (as.getDeptDirector() == as.getDeptSupremo()) {
			initStatus = 2;
			initId = as.getDeptSupremo();
			if (as.getDeptSupremo() == as.getManager()) {
				initStatus = 3;
				initId = as.getManager();
			}
		} else if (as.getDeptDirector() == as.getManager()) {
			initStatus = 2;
			initId = as.getDeptSupremo();
		}
		mp.setCurrentId(initId);
		mp.setStatus(initStatus);
		this.maMapper.insertPurchase(mp);
		this.updateSeq(number, MateriaInsertType.MATERIA_REQUISITION.getType());
		String table = MailTableUtil.createPurchaseTable2(mp, totalprice);
		User user = this.userMapper.findById(mp.getEmpId());
		User user2 = this.userMapper.findById(mp.getCurrentId());
		this.sendMailTable(user2, user, Consts.MAIL_PURCHASE, table);
	}

	public void purApprove2(MateriaPurchase mps, String sp, String opinion, ApproveStaff as) throws Exception {
		Integer status = mps.getStatus();
		// 插入审批详情
		MateriaApprove ma = new MateriaApprove();
		ma.setCode(mps.getRequisitionCode());
		ma.setApproveId(mps.getCurrentId());
		ma.setApproveStatus(status);
		ma.setApproveResult(sp);
		ma.setApproveDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		ma.setApproveOpinion(opinion);
		this.maMapper.insertMateriaApproveSelective(ma);
		MateriaPurchase mp = new MateriaPurchase();
		if ("通过".equals(sp)) {
			switch (status) {
			case 1:
				if (as.getDeptSupremo() == as.getManager()) {
					mp.setCurrentId(as.getManager());
					mp.setStatus(status + 2);
				} else {
					mp.setCurrentId(as.getDeptSupremo());
					mp.setStatus(status + 1);
				}
				break;
			case 2:
				mp.setCurrentId(as.getManager());
				mp.setStatus(status + 1);
				break;
			case 3:
				mp.setCurrentId(0);
				mp.setStatus(status + 1);
				break;
			default:
				throw new RuntimeException("系统出错");
			}
		} else {
			mp.setCurrentId(0);
			mp.setStatus(-1);
		}
		List<MateriaPurchaseDetail> mpds = this.maMapper.selectMateriaPurchaseDetailByCode(mps.getRequisitionCode());
		double zj = 0.0;
		for (MateriaPurchaseDetail mpd : mpds) {
			zj += mpd.getNeedNum() * mpd.getPrice();
		}
		String table = MailTableUtil.createPurchaseTable2(mps, zj);
		User user = this.userMapper.findById(mps.getEmpId());
		User user2 = this.userMapper.findById(mp.getCurrentId());
		int statu = mp.getStatus();
		if (statu > 0 && statu < 4) {
			this.sendMailTable(user2, user, Consts.MAIL_PURCHASE, table);
		} else {
			this.sendMaileResult2(user, statu, Consts.MAIL_PURCHASE, table);
		}
		// 审批通过之后发邮件给ljchen
		if (statu == 4) {
			List<User> users = this.userMapper.findUserByRole("物料采购员");
			this.sendMaileResult2(users.get(0), statu, Consts.MAIL_PURCHASE, table);
		}
		mp.setId(mps.getId());
		this.maMapper.updateMateriaPurchaseByPrimaryKeySelective(mp);
	}

	public Double findTotalStock(String materiaCode) {
		Integer id = this.maMapper.findIdBymateriaCode(materiaCode);
		Double stock = 0.0;
		if (id != null) {
			stock = this.maMapper.findTotalStock(id + "");
		}
		return stock;
	}

	public void saveReventApply(HttpServletRequest req, String number, User user, String content) throws Exception {
		List<User> users = this.userMapper.findUserByRole(Consts.STOREWARE_MANAGER);
		if (users.isEmpty()) {
			throw new RuntimeException("仓库管理员未设置");
		}
		MateriaPurchase mp = new MateriaPurchase();
		mp.setEmpId(user.getId());
		mp.setDaystr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		mp.setRequisitionCode(number);
		mp.setContent(content);
		mp.setStatus(1);
		mp.setCurrentId(users.get(0).getId());
		mp.setType(MateriaInsertType.MATERIA_REVENT.getType());
		this.maMapper.insertPurchase(mp);
		String[] maCodes = req.getParameterValues("maCode");
		if (maCodes != null) {
			String[] nums = req.getParameterValues("num");
			for (int i = 0; i < maCodes.length; i++) {
				MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
				Integer codeId = this.maMapper.findIdBymateriaCode(maCodes[i]);
				if (codeId != null) {
					Materia ma = this.maMapper.findMateriaInfoById2(codeId);
					mpd.setCode(number);
					mpd.setMaCode(ma.getMateriaCode());
					mpd.setSpec(ma.getSpec());
					mpd.setClassify(ma.getClassfiy());
					mpd.setBrand(ma.getBrand());
					mpd.setPackage1(ma.getPackage1());
					mpd.setNeedNum(Double.parseDouble(nums[i]));
					this.maMapper.insertPurchaseDetail(mpd);
				} else {
					throw new RuntimeException(maCodes[i] + "不存在,请重新申请");
				}
			}
			this.updateSeq(number, MateriaInsertType.MATERIA_REVENT.getType());
			String table = MailTableUtil.createReventTable(mp);
			User user2 = this.userMapper.findById(mp.getCurrentId());
			this.sendMailTable(user2, user, MateriaConstants.REVENT_APPLY, table);
		}
	}

	public void dealRevent(Integer status, String code, Integer id, Integer[] maIds, Integer warehouseId, User user)
			throws Exception {
		if (status == 2) {
			// 入库记录
			MateriaDetail md = new MateriaDetail();
			md.setCreatedate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
			md.setApplyId(user.getId());
			md.setType(1);
			md.setWarehouseId(warehouseId);
			// 入库类型
			String temp = System.currentTimeMillis() + "";
			md.setCode(temp);
			this.maMapper.insertMateriaDetail(md);
			for (Integer maId : maIds) {
				MateriaPurchaseDetail mpd = this.maMapper.selectMateriaPurchaseDetailById(maId);
				Integer codeId = this.maMapper.findIdBymateriaCode(mpd.getMaCode());
				this.insertStock(codeId + "", warehouseId, mpd.getNeedNum(), 0.0);
				addMateriaDetailNum(codeId + "", temp, mpd.getNeedNum());
				// 更新处理状态为已处理
				this.maMapper.updateMateriaPurchaseById(maId);
			}
		}
		boolean flag = true;
		MateriaPurchase mps = this.maMapper.selectMateriaPurchaseById(id);
		List<MateriaPurchaseDetail> list = this.maMapper.selectMateriaPurchaseDetailByCode(mps.getRequisitionCode());
		for (MateriaPurchaseDetail li : list) {
			if ("未处理".equals(li.getIsDeal())) {
				flag = false;
				break;
			}
		}
		if (flag || (status == -1)) {
			MateriaPurchase mp = new MateriaPurchase();
			mp.setStatus(status);
			mp.setId(id);
			mp.setCurrentId(0);
			this.maMapper.updateMateriaPurchaseByPrimaryKeySelective(mp);
			// 发邮件
			MateriaPurchase mp2 = this.maMapper.selectMateriaPurchaseById(id);
			String table = MailTableUtil.createReventTable(mp2);
			User user2 = this.userMapper.findById(mp2.getEmpId());
			this.sendMaileResult2(user2, status, MateriaConstants.REVENT_APPLY, table);
		}
	}

	public void checkApply(List<List<Map<String, String>>> lists, List<Materia> materias) throws Exception {
		for (List<Map<String, String>> list : lists) {
			if (list.isEmpty() || list == null) {
				break;
			}
			if (!list.get(1).containsKey("物料编码") || !list.get(2).containsKey("数量")) {
				throw new RuntimeException("文件格式错误，请下载最新模板后重试");
			}
			Materia materia = new Materia();
			String materiaCode = list.get(1).get("物料编码");
			if (StringUtils.isBlank(materiaCode)) {
				break;
			}
			String numStr = list.get(2).get("数量");
			materia.setMateriaCode(materiaCode);
			if (StringUtils.isBlank(numStr)) {
				throw new RuntimeException("数量不能为空");
			}
			Double num = 0.0;
			try {
				num = Double.parseDouble(numStr);
			} catch (Exception e) {
				throw new RuntimeException("数量格式错误");
			}
			if (num <= 0) {
				throw new RuntimeException("数量必须大于0");
			}
			materia.setNum(num);
			materias.add(materia);
		}
	}
	
	public void checkInboundApply(List<List<Map<String, String>>> lists, List<Materia> materias) throws Exception {
		for (List<Map<String, String>> list : lists) {
			if (list.isEmpty() || list == null) {
				break;
			}
			if (!list.get(0).containsKey("序号") || !list.get(1).containsKey("物料编码") ||
					!list.get(2).containsKey("品名分类") || !list.get(3).containsKey("品牌") ||
					!list.get(4).containsKey("规格型号") || !list.get(5).containsKey("封装") ||
					!list.get(6).containsKey("单位") || !list.get(7).containsKey("供应商") ||
					!list.get(8).containsKey("单价（元）") || !list.get(9).containsKey("其他费用（元）") ||
					!list.get(10).containsKey("采购数量")
					) {
				throw new RuntimeException("文件格式错误，请下载最新模板后重试！");
			}
			Materia materia = new Materia();
			String index = list.get(0).get("序号");
			String materiaCode = list.get(1).get("物料编码");
			String numStr = list.get(10).get("采购数量");
			String spec = list.get(4).get("规格型号");
			String priceStr = list.get(8).get("单价（元）");
			if (StringUtils.isBlank(materiaCode) && StringUtils.isBlank(numStr) 
					&& StringUtils.isBlank(spec) && StringUtils.isBlank(priceStr)) {
					break;
			} 
			materia.setMateriaCode(StringUtils.isBlank(materiaCode) ? "-" : materiaCode);
			String classify = list.get(2).get("品名分类");
			String brand = list.get(3).get("品牌");
			String package1 = list.get(5).get("封装");
			String unit = list.get(6).get("单位");
			String supplier = list.get(7).get("供应商");
			String otherStr = list.get(9).get("其他费用（元）");
			materia.setClassfiy(classify);
			materia.setBrand(brand);
			materia.setSpec(spec);
			materia.setPackage1(package1);
			materia.setUnit(unit);
			materia.setSupplier(supplier);
			
			if (StringUtils.isBlank(priceStr)) {
				throw new RuntimeException("序号为" + index + "的<b>单价</b>不能为空,请修改后重新上传文件！");
			}
			double price = 0.0;
			try {
				price = Double.parseDouble(priceStr);
			} catch (Exception e) {
				throw new RuntimeException("序号为" + index + "的<b>单价</b>格式错误,请修改后重新上传文件！");
			}
			if (price < 0) {
				throw new RuntimeException("序号为" + index + "的<b>单价</b>不能小于0,请修改后重新上传文件！");
			}
			
			if (StringUtils.isBlank(otherStr)) {
				throw new RuntimeException("序号为" + index + "的<b>其他费用</b>不能为空,请修改后重新上传文件！");
			}
			double other = 0.0;
			try {
				other = Double.parseDouble(otherStr);
			} catch (Exception e) {
				throw new RuntimeException("序号为" + index + "的<b>其他费用</b>格式错误,请修改后重新上传文件！");
			}
			if (other < 0) {
				throw new RuntimeException("序号为" + index + "的<b>其他费用</b>不能小于0,请修改后重新上传文件！");
			}
			
			if (StringUtils.isBlank(numStr)) {
				throw new RuntimeException("序号为" + index + "的<b>采购数量</b>不能为空,请修改后重新上传文件！");
			}
			Double num = 0.0;
			try {
				num = Double.parseDouble(numStr);
			} catch (Exception e) {
				throw new RuntimeException("序号为" + index + "的<b>采购数量</b>格式错误,请修改后重新上传文件！");
			}
			if (num <= 0) {
				throw new RuntimeException("序号为" + index + "的<b>采购数量</b>必须大于0,请修改后重新上传文件！");
			}
			materia.setPrice(price);
			materia.setCost(other);
			materia.setNum(num);
			materias.add(materia);
		}
	}

	public Materia findMtBySpec(String spec) {
		return this.maMapper.findMtBySpec(spec);
	}

	public List<String> findListMtBySpec(String spec) {
		return this.maMapper.findListMtBySpec(spec);
	}

	public List<String> findListMtByMaCode(String maCode) {
		return this.maMapper.findListMtByMaCode(maCode);
	}

	public void saveSy(String number, String warehouse, HttpServletRequest req, ApproveStaff as, User user, String bz)
			throws Exception {
		MateriaPurchase mp = new MateriaPurchase();
		mp.setEmpId(user.getId());
		mp.setRequisitionCode(number);
		mp.setDaystr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		mp.setContent(bz);
		// 判断审批人
		int initStatus = 1;
		int initId = as.getDeptDirector();
		if (as.getDeptDirector() == as.getDeptSupremo()) {
			initStatus = 2;
			initId = as.getDeptSupremo();
		} else if (as.getDeptDirector() == as.getManager()) {
			initStatus = 2;
			initId = as.getDeptSupremo();
		}
		mp.setCurrentId(initId);
		mp.setStatus(initStatus);
		mp.setType(MateriaInsertType.MATERIA_SY.getType());

		String[] codes = req.getParameterValues("code");
		List<Materia> mas = new ArrayList<Materia>();
		if (codes != null) {
			String[] nums = req.getParameterValues("num");
			MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
			mpd.setCode(number);
			mpd.setUnit(warehouse);
			for (int i = 0; i < codes.length; i++) {
				// 判断物料编码是否都存在
				Integer maId = this.maMapper.findIdBymateriaCode2(codes[i]);
				if (maId == null) {
					throw new RuntimeException("存在未录入物料");
				}
				mpd.setMaCode(codes[i]);
				mpd.setBuynum(Double.parseDouble(nums[i]));
				this.maMapper.insertPurchaseDetail(mpd);
				Materia ma = this.maMapper.findMateriaInfoById2(maId);
				ma.setNum(Double.parseDouble(nums[i]));
				mas.add(ma);
			}
		} else {
			throw new RuntimeException("数据读取出错");
		}
		MateriaDept md = this.maMapper.findWarehouseById(Integer.parseInt(warehouse));
		this.maMapper.insertPurchase(mp);
		updateSeq(number, MateriaInsertType.MATERIA_SY.getType());
		String table = MailTableUtil.createSyTable(mp, md.getWarehouse(), mas);
		User user2 = this.userMapper.findById(mp.getCurrentId());
		this.sendMailTable(user2, user, MateriaConstants.SY_APPLY, table);

	}

	public void saveSyApprove(MateriaPurchase mps, String sp, String opinion, ApproveStaff as) throws Exception {
		Integer status = mps.getStatus();
		// 插入审批详情
		MateriaApprove ma = new MateriaApprove();
		ma.setCode(mps.getRequisitionCode());
		ma.setApproveId(mps.getCurrentId());
		ma.setApproveStatus(status);
		ma.setApproveResult(sp);
		ma.setApproveDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		ma.setApproveOpinion(opinion);
		this.maMapper.insertMateriaApproveSelective(ma);
		MateriaPurchase mp = new MateriaPurchase();
		if ("通过".equals(sp)) {
			switch (status) {
			case 1:
				if (as.getDeptSupremo() == as.getManager()) {
					mp.setCurrentId(as.getManager());
					mp.setStatus(status + 2);
				} else {
					mp.setCurrentId(as.getDeptSupremo());
					mp.setStatus(status + 1);
				}
				break;
			case 2:
				mp.setCurrentId(as.getManager());
				mp.setStatus(status + 1);
				break;
			case 3:
				mp.setCurrentId(0);
				mp.setStatus(status + 1);
				break;
			default:
				throw new RuntimeException("系统出错");
			}
		} else {
			mp.setCurrentId(0);
			mp.setStatus(-1);
		}

		List<MateriaPurchaseDetail> mpd = this.maMapper.selectMateriaPurchaseDetailByCode(mps.getRequisitionCode());
		List<Materia> mas = new ArrayList<Materia>();
		for (MateriaPurchaseDetail mpp : mpd) {
			Integer code = this.findIdBymateriaCode2(mpp.getMaCode());
			Materia m = this.maMapper.findMateriaInfoById2(code);
			m.setNum(mpp.getBuynum());
			mas.add(m);
		}

		MateriaDept md = this.maMapper.findWarehouseById(Integer.parseInt(mpd.get(0).getUnit()));
		String table = MailTableUtil.createSyTable(mps, md.getWarehouse(), mas);
		User user = this.userMapper.findById(mps.getEmpId());
		User user2 = this.userMapper.findById(mp.getCurrentId());
		int statu = mp.getStatus();
		if (statu > 0 && statu < 4) {
			this.sendMailTable(user2, user, MateriaConstants.SY_APPLY, table);
		} else {
			this.sendMaileResult2(user, statu, MateriaConstants.SY_APPLY, table);
		}
		mp.setId(mps.getId());
		this.maMapper.updateMateriaPurchaseByPrimaryKeySelective(mp);
		// 审批通过
		if (statu == 4) {
			List<MateriaPurchaseDetail> mpds = this.maMapper
					.selectMateriaPurchaseDetailByCode(mps.getRequisitionCode());
			for (MateriaPurchaseDetail mpd1 : mpds) {
				Integer warehouse = Integer.parseInt(mpd1.getUnit());
				Integer maId = this.findIdBymateriaCode2(mpd1.getMaCode());
				Double num = mpd1.getBuynum();
				if (maId == null || warehouse == null) {
					throw new RuntimeException("数据读取错误");
				} else {
					this.addStock(warehouse, maId + "", num);
				}
			}
		}
	}

	public MateriaPurchase selectMateriaPurchaseByCode(String code) {
		return this.maMapper.selectMateriaPurchaseByCode(code);
	}

	public void updateMateriaPurchaseByPrimaryKeySelective(MateriaPurchase mp) {
		this.maMapper.updateMateriaPurchaseByPrimaryKeySelective(mp);
	}

	public List<MateriaPurchase> findMateriaByType(Integer type, Integer status) {
		return this.maMapper.findMateriaByType(type, status);
	}
	
	
	/****************************************************带缓存查询物料********************************************************/
	
	/**
	 * @Title: findCacheMateriaById   
	 * @Description: 查询单个物料库存
	 * @param: @param codeId
	 * @param: @param warehouseId
	 * @param: @return      
	 * @return: Double      
	 * @throws
	 */
	public Double findCacheMateriaById(Integer codeId, Integer warehouseId) {
		Double trueStock = this.maMapper.findTrueStock(codeId, warehouseId);
		Double cacheStock = this.maMapper.findCacheStock(codeId, warehouseId);
		return trueStock - cacheStock;
	}
	
	public void clearMateriaCache(String maCode) {
		MateriaCacheExample ex = new MateriaCacheExample();
		ex.createCriteria().andApplyCodeEqualTo(maCode);
		MateriaCache maCache = new MateriaCache();
		maCache.setStatus(DbStatus.FREEZE.getType());
		this.maCacheMapper.updateByExampleSelective(maCache, ex);
	}
	
	
	/**
	 * 插入物料详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public void insertRejectDetail(HttpServletRequest request, String code) throws Exception {
		MateriaDetail md = new MateriaDetail();
		md.setCode(code);
		String[] codes = request.getParameterValues("maCode");
		if (codes != null) {
			String[] nums = request.getParameterValues("num");
			for (int i = 0; i < codes.length; i++) {
				Double num = Double.parseDouble(nums[i]);
				Integer codeId = this.maMapper.findIdBymateriaCode(codes[i]);
				md.setNum(num);
				md.setMateriaCode(codeId + "");
				this.maMapper.insertApplyDetail(md);
			}
		} else {
			throw new RuntimeException("数据异常");
		}
	}
	
	
	// 插入申请人信息
		public void insertApplyReject(MateriaPurchase mp, User loginUser, 
				ApproveStaff as, Integer whId, List<MateriaPurchaseDetail> mpds) {
			mp.setEmpId(loginUser.getId());
			mp.setDaystr(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
			mp.setType(MateriaInsertType.MATERIA_REJECT.getType());
			mp.setProjectCode(whId + "");
			Map<String, Integer> map = this.getCurrentStatus(as);
			mp.setStatus(map.get("status"));
			mp.setCurrentId(map.get("currentId"));
			this.insertPurchase(mp);
			this.updateSeq(mp.getRequisitionCode(), MateriaInsertType.MATERIA_REJECT.getType());
			User user = this.userMapper.findById(mp.getCurrentId());
			MateriaDept md = this.maMapper.findWarehouseById(whId);
			String table = MailTableUtil.createBFTable(mp, md.getWarehouse(), mpds);
			this.sendMailTable(user, loginUser, Constants.MATERIA_REJECT, table);
		}
		
		
		public Map<String, Integer> getCurrentStatus(ApproveStaff as) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			Integer director = as.getDeptDirector();
			Integer supremo = as.getDeptSupremo();
			Integer treasurer = as.getTreasurer();
			Integer manage = as.getManager();
			Integer currentId = director;
			Integer status = 1;
			
			if(director == supremo) {
				status = 2;
				currentId = supremo;
			}
			if(director == supremo && director == treasurer) {
				status = 3;
				currentId = treasurer;
			}
			if(director == supremo && director == treasurer && director == manage) {
				status = 4;
				currentId = manage;
			}
			map.put("currentId", currentId);
			map.put("status", status);
			return map;
		}

		public Integer findRecordTotalSize(Integer userId, Integer type) {
			return this.maMapper.findRecordTotalSize(userId, type);
		}
		
		/**
		 * 物料报废审批状态
		 */
		@SuppressWarnings("unused")
		private final static Integer REJECT_APPROVE_PASS = 5;
		@SuppressWarnings("unused")
		private final static Integer REJECT_APPROVE_FAIL = -1;
		
		public long countRejectApprove(Integer currentId, Integer type, boolean flag) {
			MateriaPurchaseExample ex = new MateriaPurchaseExample();
			if(flag) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(1);
				ex.createCriteria().andTypeEqualTo(type).andStatusNotIn(list);
			} else {
				ex.createCriteria().andCurrentIdEqualTo(currentId).andTypeEqualTo(type);
			}
			return this.maPurchaseMapper.countByExample(ex);
		}
		
		public List<MateriaPurchase> findRejectApprove(Integer currentId, Integer type,
				Integer beginIndex, Integer pageSize, boolean flag) {
			MateriaPurchaseExample ex = new MateriaPurchaseExample();
			ex.setBeginIndex(beginIndex);
			ex.setPageSize(pageSize);
			if(flag) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(1);
				ex.createCriteria().andTypeEqualTo(type).andStatusNotIn(list);
			} else {
				ex.createCriteria().andCurrentIdEqualTo(currentId).andTypeEqualTo(type);
			}
			return this.maPurchaseMapper.selectByExample(ex);
		}
		
		public void insertApprove(MateriaPurchase mp, String sp, String opinion) {
			MateriaApprove ma = new MateriaApprove();
			ma.setCode(mp.getRequisitionCode());
			ma.setApproveStatus(mp.getStatus());
			ma.setApproveDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
			ma.setApproveId(mp.getCurrentId());
			ma.setApproveResult(sp);
			ma.setApproveOpinion(opinion);
			this.maMapper.insertMateriaApproveSelective(ma);
		}
		
		public void saveRejectApprove(MateriaPurchase mp, String sp, ApproveStaff as) {
			List<MateriaPurchaseDetail> mpds = this.maMapper.selectMateriaPurchaseDetailByCode(mp.getRequisitionCode());
			int currentId = mp.getCurrentId();
			int status = mp.getStatus();
			Integer whId = Integer.parseInt(mp.getProjectCode());
			if("通过".equals(sp)) {
				switch (status) {
					case 1 : 
						status++;
						currentId = as.getDeptSupremo();
						if(as.getDeptSupremo() == as.getTreasurer() && as.getDeptSupremo() != as.getManager()) {
							status++;
							currentId = as.getTreasurer();
						}
						if(as.getDeptSupremo() != as.getTreasurer() && as.getDeptSupremo() == as.getManager()) {
							status++;
							currentId = as.getTreasurer();
						}
						if(as.getDeptSupremo() == as.getTreasurer() && as.getDeptSupremo() == as.getManager()) {
							status++;
							status++;
							currentId = as.getManager();
						}
						break;
					case 2 :
						status++;
						currentId = as.getTreasurer();
						if(as.getTreasurer() == as.getManager()) {
							status++;
							currentId = as.getManager();
						}
						break;
					case 3 :
						status++;
						currentId = as.getManager();
						break;
					case 4 :
						status++;
						currentId = DbStatus.FREEZE.getType();
						// 减去库存
						for(MateriaPurchaseDetail mpd : mpds) {
							Integer codeId = this.maMapper.findIdBymateriaCode(mpd.getMaCode());
							this.insertStock(codeId+"" , whId, -mpd.getNeedNum(), 0.0);
						}
						break;
				}
			} else {
				status = DbStatus.FREEZE.getType();
				currentId = DbStatus.FREEZE.getType();
			}
			mp.setStatus(status);
			mp.setCurrentId(currentId);
			this.maPurchaseMapper.updateByPrimaryKeySelective(mp);
			
			
			MateriaDept md = this.findWarehouseById(whId);
			String table = MailTableUtil.createBFTable(mp, md.getWarehouse(), mpds);
			User user = this.userMapper.findById(mp.getCurrentId());
			User user2 = this.userMapper.findById(mp.getEmpId());
			if(status < 5 && status > 1) {
				this.sendMailTable(user, user2, Constants.MATERIA_REJECT, table);
			} else {
				this.sendMaileResult2(user2, status, Constants.MATERIA_REJECT, table);
			}
		}
		
		
		
		public void checkPurchaseApply(List<List<Map<String, String>>> lists, List<MateriaPurchaseDetail> materias) throws Exception {
			int i = 3;
			
			for (List<Map<String, String>> list : lists) {
				i++;
				if (list.isEmpty() || list == null) {
					break;
				}
				if (!list.get(0).containsKey("物料编码") || !list.get(1).containsKey("品名分类") ||
						!list.get(2).containsKey("品牌") || !list.get(3).containsKey("规格型号") ||
						!list.get(4).containsKey("封装") || !list.get(5).containsKey("单位") ||
						!list.get(6).containsKey("需求数量") || !list.get(7).containsKey("需求日期") ||
						!list.get(8).containsKey("预计单价") || !list.get(9).containsKey("计划日期") ||
						!list.get(10).containsKey("其他费用") || !list.get(11).containsKey("供应商") ||
						!list.get(12).containsKey("链接") || !list.get(13).containsKey("采购数量")
						) {
					throw new RuntimeException("文件格式错误，请下载最新模板后重试");
				}
				String materiaCode = list.get(0).get("物料编码");
				String classify = list.get(1).get("品名分类");
				String brand = list.get(2).get("品牌");
				String spec = list.get(3).get("规格型号");
				String package1 = list.get(4).get("封装");
				String unit = list.get(5).get("单位");
				String numStr = list.get(6).get("需求数量");
				String needDate = list.get(7).get("需求日期");
				String priceStr = list.get(8).get("预计单价");
				String planDate = list.get(9).get("计划日期");
				String otherCostStr = list.get(10).get("其他费用");
				String supplier = list.get(11).get("供应商");
				String link = list.get(12).get("链接");
				String buyNumStr = list.get(13).get("采购数量");
				MateriaPurchaseDetail mpd = new MateriaPurchaseDetail();
				if(StringUtils.isBlank(materiaCode) && StringUtils.isBlank(spec) 
						&& StringUtils.isBlank(numStr) && StringUtils.isBlank(priceStr)) {
					break;
				}
				mpd.setMaCode(StringUtils.isBlank(materiaCode) ? "-" : materiaCode);
				mpd.setClassify(classify);
				mpd.setBrand(brand);
				mpd.setSpec(spec);
				mpd.setPackage1(package1);
				mpd.setUnit(unit);
				
				Double needNum = 0.0;
				try {
					needNum = Double.parseDouble(StringUtils.isBlank(numStr) ? "0.0" : numStr);
				} catch (Exception e) {
					throw new RuntimeException("上传文件<span style='color:red'>第"+ i +"行'需求数量'</span>格式错误,请修改后重新上传文件！");
				}
				if(needNum <= 0) {
					throw new RuntimeException("上传文件<span style='color:red'>第"+ i +"行'需求数量'</span>必须大于0,请修改后重新上传文件！");
				}
				mpd.setNeedNum(needNum);
				
				mpd.setNeedDate(needDate);
				
				Double price = 0.0;
				try {
					price = Double.parseDouble(StringUtils.isBlank(priceStr) ? "0.0" : priceStr);
				} catch (Exception e) {
					throw new RuntimeException("上传文件<span style='color:red'>第"+ i +"行'预计单价'</span>格式错误,请修改后重新上传文件！");
				}
				if(price < 0) {
					throw new RuntimeException("上传文件<span style='color:red'>第"+ i +"行'预计单价'</span>不能小于0,请修改后重新上传文件！");
				}
				mpd.setPrice(price);
				
				mpd.setUseDate(planDate);
				
				Double otherCost = 0.0;
				try {
					otherCost = Double.parseDouble(StringUtils.isBlank(otherCostStr) ? "0.0" : otherCostStr);
				} catch (Exception e) {
					throw new RuntimeException("上传文件<span style='color:red'>第"+ i +"行'其他费用'</span>格式错误,请修改后重新上传文件！");
				}
				if(otherCost < 0) {
					throw new RuntimeException("上传文件<span style='color:red'>第"+ i +"行'其他费用'</span>不能小于0,请修改后重新上传文件！");
				}
				mpd.setCost(otherCost);
				mpd.setSupplier(supplier);
				mpd.setLink(link);
				
				Double buyNum = 0.0;
				try {
					buyNum = Double.parseDouble(StringUtils.isBlank(buyNumStr) ? "0.0" : buyNumStr);
				} catch (Exception e) {
					throw new RuntimeException("上传文件<span style='color:red'>第"+ i +"行'采购数量'</span>格式错误,请修改后重新上传文件！");
				}
				if(buyNum < 0) {
					throw new RuntimeException("上传文件<span style='color:red'>第"+ i +"行'采购数量'</span>不能小于0,请修改后重新上传文件！");
				}
				mpd.setBuynum(buyNum);
				materias.add(mpd);
			}
		}
		
		
		public MateriaMould selectByPrimaryKey(String type) {
			return this.maMouldMapper.selectByPrimaryKey(type);
		}
		
		public void updateMateriaMold(MateriaMould  mm) {
			this.maMouldMapper.updateByPrimaryKeySelective(mm);
		}
		
		public List<MateriaWarehouse> selectByExample(MateriaWarehouseExample example) {
			return this.maWarehouseMapper.selectByExample(example);
		}
		
}