package com.hj.oa.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.commons.AjaxResult;
import com.hj.commons.ApprovalStatus;
import com.hj.commons.EmailType;
import com.hj.oa.Consts;
import com.hj.oa.bean.ApproveStaff;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.ParamBean;
import com.hj.oa.bean.Role;
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
import com.hj.oa.service.MenuService;
import com.hj.oa.service.PropertyService;
import com.hj.oa.service.RoleService;
import com.hj.oa.service.UserService;
import com.hj.util.DateUtil;
import com.hj.util.ExcelUtil;
import com.hj.util.FileUtil;
import com.hj.util.MailContentBuilder;
import com.hj.util.MailTableUtil;
import com.hj.util.MailUtil;
import com.hj.util.OtherUtil;
import com.hj.util.RoleUtil;
import com.hj.util.ServletUtil;
import com.hj.util.StringUtil;
import com.hj.util.StringtoByteUtil;
import com.hj.util.ZcUtil;

@Controller
public class PropertyController extends BaseController {

	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private MailTableUtil mailTableUtil;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PropertyService propService;
	@Autowired
	private MenuService menuService;

	private static Map<String, Integer> codeMap = null;// new HashMap<String,
														// Integer>();

	public static Integer getCode(String code, PropertyService ps) {
		initCodeMap(ps);

		Integer i = codeMap.get(code);
		if (null == i) {// 如果不存在，添加
			i = 1;
			ZcProperty prop = new ZcProperty();
			prop.setName(code);
			prop.setNum(i);
			ps.addPropCode(prop);
		} else { // 存在，跟新，数据库以及本地都同步
			i++;
			ZcProperty prop = new ZcProperty();
			prop.setName(code);
			prop.setNum(i);
			ps.updatePropCode(prop);
		}

		codeMap.put(code, i);
		return i;

	}

	private static void initCodeMap(PropertyService ps) {

		if (codeMap != null) {
			return;
		}

		List<ZcProperty> list = ps.findPropCode();
		codeMap = new HashMap<String, Integer>();
		for (ZcProperty prop : list) {
			codeMap.put(prop.getName(), prop.getNum());
		}
	}

	/*
	 * public static void showAllCodeMap(){ Set<String> keys = codeMap.keySet();
	 * for(String key : keys){ } }
	 */

	// 导出资产详情
	@RequestMapping("oa/prop/export")
	@ResponseBody
	public HashMap<String, String> exportProps(HttpSession session) {

		User loginUser = this.getLoginUser(session);
		if (loginUser.getId() != Consts.managerId) {
			throw new RuntimeException("您没有权限");
		}

		// List<ZcProperty> allProps = this.propService.findByCond(0, 0, 0, 0,
		// 0, null);
		List<ZcProperty> allProps = this.propService.findByCond2();

		// List<Dept> depts = this.userService.findAllDept();
		List<User> users = this.userService.findAllUsers();

		HashMap<Integer, String> deptMap = new HashMap<Integer, String>();
		for (User u : users) {
			String deptName = u.getDeptName();
			if (null == deptName) {
				deptName = "";
			}
			deptMap.put(u.getId(), deptName);
		}
		// 为每个人生成一个excel
		ExcelUtil.generateForProp(allProps, deptMap);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("result", "ok");
		return map;
	}

	// 初始化数据
	@ResponseBody
	@RequestMapping("oa/prop/initProps")
	public String initProps(HttpSession session, Model model) {
		try {
			User loginUser = (User) this.getLoginUser(session);

			if (loginUser.getId() != 1) {
				return "not promd";
			}

			List<User> allUsers = this.userService.findAllUsers();
			List<ZcType> types = this.propService.findSecondTypes();
			List<ZcPlace> places = this.propService.findAllPlace();

			Map<String, Integer> uMap = new HashMap<String, Integer>();
			Map<String, Integer> tMap = new HashMap<String, Integer>();
			Map<String, Integer> pMap = new HashMap<String, Integer>();

			for (User u : allUsers) {
				uMap.put(u.getName(), u.getId());
			}

			for (ZcType t : types) {
				tMap.put(t.getName(), t.getId());
			}

			for (ZcPlace p : places) {
				pMap.put(p.getName(), p.getId());
			}

			List<ZcPropTmp> propTmps = propService.findPropTmp();
			List<ZcProperty> props = new ArrayList<ZcProperty>(propTmps.size());

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat(Consts.chinaDateFmt);
			for (ZcPropTmp t : propTmps) {
				ZcProperty p = new ZcProperty();

				Double price = t.getPrice();
				String unit = t.getUnit().trim();
				String bz = t.getBz().trim();

				String code = t.getCode().trim(); //
				int codeSeq = PropertyController.getCode(code, propService);
				code = "MS-" + code + String.format("%06d", codeSeq);
				String name = t.getName().trim();
				String spec = t.getSpec().trim();// 规格型号

				Integer empId = null;
				String empName = t.getEmpName().trim();
				if (StringUtils.isNotEmpty(empName)) {
					empId = uMap.get(empName);
					if (empId == null) {
						throw new RuntimeException("用户名称找不到对应的系统用户！");
					}
				}

				String typeName = t.getType().trim();
				Integer typeId = tMap.get(typeName); // 不能为空
				if (typeId == null) {
					throw new RuntimeException("类型不能为空，类型错误");
				}
				String buyTime = t.getBuyTime().trim();
				Date d = sdf1.parse(buyTime);
				String buyDate = sdf2.format(d);

				String placeName = t.getPlace().trim();
				Integer placeId = pMap.get(placeName);
				if (placeId == null) {
					throw new RuntimeException("存放地点不能为null！");
				}

				p.setBuyDate(buyDate);
				p.setBz(bz);
				p.setCode(code);// 生成code代码
				p.setEmpId(empId);
				p.setName(name);
				p.setPlaceId(placeId);
				p.setTypeId(typeId);
				p.setUnit(unit);
				p.setSpec(spec);
				p.setPrice(price);

				props.add(p);
			}

			this.propService.addProps(props);
			// showAllCodeMap();
			return "ok";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping("oa/prop/chooseProp")
	public String chooseProp(Integer placeId, Integer typeId, HttpSession session, Model model) {

		List<ZcType> types = propService.findFirstTypes();
		List<ZcPlace> places = this.propService.findAllPlace();
		List<ZcType> subTypes = propService.findSecondTypes();

		model.addAttribute("types", types);
		model.addAttribute("places", places);
		model.addAttribute("subTypes", subTypes);

		if (null == placeId && null == typeId) {
			return "oa/prop/chooseProp";
		}

		if (null == placeId) {
			placeId = 0;
		}

		if (null == typeId) {
			typeId = 0;
		}
		// List<OsWupin> list = osService.findWupinByType(companyOnly);

		model.addAttribute("placeId", placeId);
		model.addAttribute("typeId", typeId);

		List<ZcProperty> props = propService.findUnusedProps(placeId, typeId);

		/*
		 * HashMap<Integer, ArrayList<ZcProperty>> map = new HashMap<Integer,
		 * ArrayList<ZcProperty>>(); for(ZcProperty p : props){ Integer tId =
		 * p.getTypeId(); ArrayList<ZcProperty> ps = map.get(tId); if(null ==
		 * ps){ ps = new ArrayList<ZcProperty>(); ZcType ty =
		 * this.propService.findTypeById(tId); subTypes.add(ty); map.put(tId,
		 * ps); } ps.add(p); }
		 */

		model.addAttribute("props", props);
		return "oa/prop/chooseProp";
	}

	@RequestMapping("oa/prop/chooseMyProp")
	public String chooseMyProp(HttpSession session, Model model) {

		User loginUser = (User) this.getLoginUser(session);
		List<ZcProperty> props = propService.findPropByEmpId(loginUser.getId());

		model.addAttribute("props", props);

		return "oa/prop/chooseMyProp";
	}

	@RequestMapping("oa/prop/choosePropByEmp")
	public String choosePropByEmp(int empId, HttpSession session, Model model) {

		List<ZcProperty> props = propService.findPropByEmpId(empId);

		model.addAttribute("props", props);

		return "oa/prop/chooseMyProp";
	}

	@RequestMapping("oa/prop/myHis")
	public String myHis(HttpSession session, Model model) {

		User loginUser = (User) this.getLoginUser(session);
		List<ZcPropLog> logs = propService.findEmpPropLog(loginUser.getId(), null);

		model.addAttribute("list", logs);

		return "oa/prop/myHis";
	}

	@RequestMapping("oa/prop/showDetail")
	public String showDetail(Integer id, HttpSession session, Model model) {

		ZcProperty prop = propService.findPropById(id);
		List<ZcPropLog> logs = this.propService.findPropLog(id);

		model.addAttribute("prop", prop);
		model.addAttribute("logs", logs);

		return "oa/prop/showDetail";
	}

	@RequestMapping("oa/prop/goEdit/{id}")
	public String goEdit(@PathVariable("id") int id, Integer uType, Integer showType, String uname, Integer empId,
			Integer placeId, Integer typeId, HttpSession session, Model model) {

		List<ZcPlace> places = this.propService.findAllPlace();
		List<ZcType> types = this.propService.findSecondTypes();
		model.addAttribute("places", places);
		model.addAttribute("types", types);

		ZcProperty prop = this.propService.findPropById(id);
		model.addAttribute("prop", prop);

		model.addAttribute("uType", uType);
		model.addAttribute("showType", showType);
		model.addAttribute("empId", empId);
		model.addAttribute("uname", uname);
		model.addAttribute("placeId", placeId);
		model.addAttribute("typeId", typeId);

		// model.addAttribute("msg","您成功添加固定资产，资产编号为："+prop.getCode()+"。请妥善处理");

		return "oa/prop/edit";
	}

	@RequestMapping("oa/prop/edit")
	public String edit(ZcProperty prop, Integer uType, Integer showType, String uname, Integer empId2, Integer placeId2,
			Integer typeId2, HttpSession session, Model model) {

		ZcProperty tProp = this.propService.findPropById(prop.getId());
		tProp.setBz(prop.getBz());
		tProp.setPlaceId(prop.getPlaceId());
		tProp.setSpec(prop.getSpec());

		this.propService.updateProperty(tProp);

		model.addAttribute("uType", uType);
		model.addAttribute("showType", showType);
		model.addAttribute("empId", empId2);
		model.addAttribute("uname", uname);
		model.addAttribute("placeId", placeId2);
		model.addAttribute("typeId", typeId2);

		model.addAttribute("msg", "资产信息编辑成功。");

		List<ZcPlace> places = this.propService.findAllPlace();
		List<ZcType> types = this.propService.findSecondTypes();
		model.addAttribute("places", places);
		model.addAttribute("types", types);

		model.addAttribute("prop", tProp);

		return "oa/prop/edit";
	}

	@RequestMapping("oa/prop/add")
	public String add(ZcProperty prop, HttpSession session, Model model) {

		List<ZcPlace> places = this.propService.findAllPlace();
		List<ZcType> types = this.propService.findSecondTypes();
		model.addAttribute("places", places);
		model.addAttribute("types", types);
		if (prop == null) {
			return "oa/prop/add";
		}
		User loginUser = this.getLoginUser(session);
		prop.setEmpId(loginUser.getId());
		// 1是手动入库
		prop.setAddType(1);
		prop.setStoreTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt).substring(0, 11));
		for (int i = 0; i < prop.getNum(); i++) {
			String code = StringtoByteUtil.getSixCode(prop.getName(), 6);
			int codeSeq = PropertyController.getCode(code, propService);
			code = "MS-" + code + String.format("%06d", codeSeq);
			prop.setCode(code);
			this.propService.addProperty(prop);
		}
		return "oa/prop/add";
	}

	@RequestMapping("oa/prop/compCg")
	public String compCg(int cgId) {
		ZcPropCg cg = this.propService.findCgById(cgId);

		cg.setCgStatus(1);
		this.propService.updateCgStatusForAll(cg);

		return "redirect:/web/oa/prop/cgrk?msg=1";
	}

	@RequestMapping("oa/prop/brk")
	public String brk(Integer ocgId, Integer oid, Integer cgStatus, String bz, HttpSession session, Model model) {

		ZcProperty oprop = this.propService.findPropByCgAndId(oid);
		oprop.setCgStatus(cgStatus);
		this.propService.updateCgPropStatus(oprop.getCgStatus(), oprop.getId(), bz);// 不入库

		ZcPropCg cg = this.propService.findCgById(ocgId);
		List<ZcProperty> props = this.propService.findPropByCg(ocgId);
		boolean flag = true;
		for (ZcProperty p : props) {
			if (p.getCgStatus() == 0) {
				flag = false;
				break;
			}
		}
		if (flag) {
			cg.setCgStatus(1);
			this.propService.updateCgStatusForAll(cg);
		}

		return "redirect:/web/oa/prop/editCgForAdd/" + ocgId + "?msg=2";
	}

	@RequestMapping("oa/prop/addCg")
	public String addCg(Integer ocgId, Integer oid, Integer cgStatus, String bz, HttpSession session, Model model) {
		ZcProperty oprop = this.propService.findPropByCgAndId(oid);
		User loginUser = this.getLoginUser(session);
		oprop.setEmpId(loginUser.getId());
		oprop.setBuyDate(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt).substring(0, 11));
		String type = oprop.getType();
		if (type != null && !"".equals(type)) {
			oprop.setTypeId(Integer.parseInt(oprop.getType()));
		}
		Integer placeId = oprop.getPlace();
		if (placeId != null && !"".equals(placeId)) {
			oprop.setPlaceId(oprop.getPlace());
		}
		oprop.setSpec(oprop.getBrand());
		oprop.setAddType(2);
		oprop.setStoreTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt).substring(0, 11));
		Integer num = oprop.getNum();
		if (num == null || "".equals(num) || num == 0) {
			oprop.setNum(1);
		}
		for (int i = 0; i < oprop.getNum(); i++) {
			String code = StringtoByteUtil.getSixCode(oprop.getName(), 6);
			int codeSeq = PropertyController.getCode(code, propService);
			code = "MS-" + code + String.format("%06d", codeSeq);
			oprop.setCode(code);
			this.propService.addProperty(oprop);
		}
		this.propService.updateStatus(oid);
		return "redirect:/web/oa/prop/editCgForAdd/" + ocgId + "?msg=1";
	}

	@RequestMapping("oa/prop/search")
	public String search(Integer uType, Integer showType, String uname, Integer deptId, Integer empId, Integer placeId,
			Integer typeId, String code, HttpServletRequest request, HttpSession session, Model model) {
		this.mgr(uType, showType, uname, deptId, empId, placeId, typeId, code, request, session, model);

		return "oa/prop/search";
	}

	@RequestMapping("oa/prop/exportAllRecord")
	public String exportAllRecord(HttpSession session, Model model, HttpServletResponse response) {
		List<ZcProperty> list = this.propService.findAllProperty();
		for (ZcProperty li : list) {
			if (li.getTypeId() == 0) {
				String type = "";
				li.setType(type);
			} else {
				String type = this.propService.findTypeById(li.getTypeId()).getName();
				li.setType(type);
			}
			if (li.getPlaceId() == 0) {
				String placeName = "";
				li.setPlaceName(placeName);
			} else {
				String placeName = this.propService.findPlaceById(li.getPlaceId());
				li.setPlaceName(placeName);
			}
			if (li.getEmpId() != null) {
				User user = this.userService.findById(li.getEmpId());
				if (user != null) {
					li.setEmpName(user.getName());
				}
			}
		}
		File file = ExcelUtil.exportAllProperty(list);
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "oa/prop/mgr";
	}

	@RequestMapping("oa/prop/mgr")
	public String mgr(Integer uType, Integer showType, String uname, Integer deptId, Integer empId, Integer placeId,
			Integer typeId, String code, HttpServletRequest request, HttpSession session, Model model) {

		if (uType == null) {
			uType = 0;
		}
		if (showType == null) {
			showType = 0;
		}
		if (deptId == null) {
			deptId = 0;
		}
		if (empId == null) {
			empId = 0;
		}
		if (placeId == null) {
			placeId = 0;
		}
		if (typeId == null) {
			typeId = 0;
		}
		if (StringUtils.isEmpty(code)) {
			code = null;
		}

		if (uType == 0) {
			empId = 0;
			uname = "";
		}

		List<ZcType> secondTypes = this.propService.findSecondTypes();
		List<ZcPlace> places = this.propService.findAllPlace();

		if (request.getMethod().equalsIgnoreCase("get")) {
			model.addAttribute("uType", uType);
			model.addAttribute("showType", showType);
			model.addAttribute("deptId", deptId);
			model.addAttribute("empId", empId);
			model.addAttribute("uname", uname);
			model.addAttribute("placeId", placeId);
			model.addAttribute("typeId", typeId);
			model.addAttribute("code", code);
			// model.addAttribute("list",props);
			model.addAttribute("fTypes", secondTypes);
			model.addAttribute("places", places);
			return "oa/prop/mgr";
		}

		List<ZcProperty> props = this.propService.findByCond(showType, deptId, empId, placeId, typeId, code);

		List<ZcType> firstTypes = this.propService.findFirstTypes();

		double total = 0;
		for (ZcProperty prop : props) {
			if (prop.getPrice() != null) {
				total += prop.getPrice();
			}
		}

		model.addAttribute("total", total);
		model.addAttribute("uType", uType);
		model.addAttribute("showType", showType);
		model.addAttribute("deptId", deptId);
		model.addAttribute("empId", empId);
		model.addAttribute("uname", uname);
		model.addAttribute("placeId", placeId);
		model.addAttribute("typeId", typeId);
		model.addAttribute("code", code);
		model.addAttribute("list", props);
		model.addAttribute("fTypes", secondTypes);
		model.addAttribute("places", places);

		return "oa/prop/mgr";
	}

	@RequestMapping("oa/prop/addPlace")
	public String addPlace(String name, HttpSession session, Model model) {
		List<ZcPlace> places = this.propService.findAllPlace();
		if (StringUtils.isEmpty(name)) {
			model.addAttribute("places", places);
			return "oa/prop/addPlace";
		}
		ZcPlace place = new ZcPlace();
		place.setName(name);
		this.propService.addPlace(place);
		return "redirect:/web/oa/prop/addPlace?msg=1";
	}

	@RequestMapping("oa/prop/updatePlace")
	public String updatePlace(String name, HttpSession session, Model model) {

		return "oa/prop/updatePlace";
	}

	@RequestMapping("oa/prop/getPlaces")
	@ResponseBody
	public AjaxResult<List<ZcPlace>> getPlaces() {
		AjaxResult<List<ZcPlace>> res = new AjaxResult<List<ZcPlace>>();
		List<ZcPlace> places = this.propService.findAllPlace();
		if (places.isEmpty() || places == null) {
			res.setSuccess(false);
			res.setMsg("无放置地点，请添加地点");
		} else {
			res.setData(places);
		}
		return res;
	}

	@RequestMapping("oa/prop/updatePlaceAjax")
	@ResponseBody
	public AjaxResult<String> updatePlaceAjax(@RequestParam String name, @RequestParam Integer id) {
		AjaxResult<String> res = new AjaxResult<String>();
		// 查询有没有这个地点
		List<ZcPlace> places = this.propService.findAllPlace();
		for (ZcPlace place : places) {
			if (name.equals(place.getName())) {
				res.setMsg("已存在的地点，请重新修改");
				res.setSuccess(false);
			}
		}
		if (res.isSuccess()) {
			ZcPlace zp = new ZcPlace();
			zp.setId(id);
			zp.setName(name);
			this.propService.updatePlace(zp);
			res.setMsg("修改成功");
		}
		return res;
	}

	@RequestMapping("oa/prop/deletePlace")
	public String deletePlace(int id, HttpSession session, Model model, String submitCode) {
		int a = this.propService.findStockById(id);
		if (a > 0) {
			model.addAttribute("msg", "该地点还有资产存放，无法删除！");
			return "oa/prop/addPlace";
		}
		this.propService.deletePlaceById(id);
		return "redirect:/web/oa/prop/addPlace?msg=1";
	}

	@RequestMapping("oa/prop/exportPurchaseRecord")
	public String exportPurchaseRecord(String begin, String end, HttpSession session, Model model,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(begin) || StringUtils.isEmpty(end)) {
			model.addAttribute("msg", "请选择时间！");
			return "oa/prop/cgRecords";
		}
		ParamBean pb = new ParamBean();
		pb.setBegin(begin);
		pb.setEnd(end);
		pb.setAddType(2);
		List<ZcProperty> list = this.propService.findAutoStoreRecord(pb);
		for (ZcProperty li : list) {
			ZcType type = this.propService.findTypeById(li.getTypeId());
			if (type != null) {
				li.setType(type.getName());
			}
			String placeName = this.propService.findPlaceById(li.getPlaceId());
			if (placeName != null && !placeName.isEmpty()) {
				li.setPlaceName(placeName);
			}
			if (li.getEmpId() != null) {
				User user = this.userService.findById(li.getEmpId());
				li.setEmpName(user.getName());
			}
		}
		File file = ExcelUtil.propAddRecord(list);
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "oa/prop/cgRecords";
	}

	@RequestMapping("oa/prop/exportAddRecord")
	public String exportAddhRecode(String begin, String end, HttpSession session, Model model,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(begin) || StringUtils.isEmpty(end)) {
			model.addAttribute("msg", "请选择时间！");
			return "oa/prop/add";
		}
		ParamBean pb = new ParamBean();
		pb.setBegin(begin);
		pb.setEnd(end);
		pb.setAddType(1);
		List<ZcProperty> list = this.propService.findAutoStoreRecord(pb);
		for (ZcProperty li : list) {
			ZcType type = this.propService.findTypeById(li.getTypeId());
			if (type != null) {
				li.setType(type.getName());
			}
			String placeName = this.propService.findPlaceById(li.getPlaceId());
			if (placeName != null && !placeName.isEmpty()) {
				li.setPlaceName(placeName);
			}
			if (li.getEmpId() != null) {
				User user = this.userService.findById(li.getEmpId());
				li.setEmpName(user.getName());
			}
		}
		File file = ExcelUtil.propAddRecord(list);
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "oa/prop/add";
	}

	@RequestMapping("oa/prop/exportRecord1")
	public String exportRecord1(String beginDate, String endDate, HttpSession session, Model model,
			HttpServletResponse response) {
		if (beginDate == null || beginDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			model.addAttribute("msg", "请选择时间！");
			return "oa/prop/ff";
		}
		ParamBean pb = new ParamBean();
		pb.setType(1);
		pb.setBegin(beginDate);
		pb.setEnd(endDate);
		List<ZcPropLog> list = this.propService.findPropAll(pb);
		for (ZcPropLog li : list) {
			li.setPlaceName(this.propService.findPlaceById(li.getPlaceId()));
		}
		File file = ExcelUtil.propRecord(list);
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "oa/prop/ff";
	}

	@RequestMapping("oa/prop/exportRecord2")
	public String exportRecord2(String beginDate, String endDate, HttpSession session, Model model,
			HttpServletResponse response) {
		if (beginDate == null || beginDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			model.addAttribute("msg", "请选择时间！");
			return "oa/prop/wxRecord";
		}
		ParamBean pb = new ParamBean();
		pb.setType(2);
		pb.setBegin(beginDate);
		pb.setEnd(endDate);
		List<ZcPropLog> list = this.propService.findPropAll(pb);
		for (ZcPropLog li : list) {
			li.setPlaceName(this.propService.findPlaceById(li.getPlaceId()));
		}
		File file = ExcelUtil.propRecord(list);
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "oa/prop/wxRecord";
	}

	@RequestMapping("oa/prop/exportRecord3")
	public String exportRevertRecode(String beginDate, String endDate, HttpSession session, Model model,
			HttpServletResponse response) {
		if (beginDate == null || beginDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			model.addAttribute("msg", "请选择时间！");
			return "oa/prop/gh";
		}
		ParamBean pb = new ParamBean();
		pb.setType(3);
		pb.setBegin(beginDate);
		pb.setEnd(endDate);
		List<ZcPropLog> list = this.propService.findPropAll(pb);
		for (ZcPropLog li : list) {
			li.setPlaceName(this.propService.findPlaceById(li.getPlaceId()));
		}
		File file = ExcelUtil.propRecord(list);
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "oa/prop/gh";
	}

	@RequestMapping("oa/prop/exportRepairRecord")
	public String exportRepairtRecord(HttpSession session, Model model, HttpServletResponse response) {

		List<ZcPropWbVo> list = this.propService.findRepairAll();
		for (ZcPropWbVo li : list) {
			if (li.getStatus() == 4) {
				li.setApproveStatus("审批通过");
			} else if (li.getStatus() == -1) {
				li.setApproveStatus("审批不通过");
			}
		}
		File file = ExcelUtil.propRepairRecord(list);
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "oa/prop/ff";
	}

	@RequestMapping("oa/prop/addType")
	public String addType(String name, Integer pId, String submitCode, HttpSession session, Model model) {

		List<ZcType> firstTypes = this.propService.findFirstTypes();

		if (StringUtils.isEmpty(name)) {
			model.addAttribute("firstTypes", firstTypes);
			return "oa/prop/addType";
		}

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/prop/addType";
		}
		session.removeAttribute(Consts.submitCode);

		ZcType type = new ZcType();
		type.setName(name);
		type.setpId(pId);

		this.propService.addType(type);
		model.addAttribute("firstTypes", firstTypes);
		model.addAttribute("cType", pId);
		model.addAttribute("msg", "操作成功。");

		return "oa/prop/addType";
	}

	@RequestMapping("oa/prop/ajaxTypes/{pid}")
	public String addType(@PathVariable("pid") Integer pId, HttpSession session, Model model) {

		List<ZcType> types = this.propService.findSubTypes(pId);
		model.addAttribute("types", types);

		return "oa/prop/ajaxTypes";
	}

	@RequestMapping("oa/prop/goGh")
	public String goGh(String[] ids, HttpSession session, Model model, HttpServletRequest request) {
		User loginUser = (User) this.getLoginUser(session);
		List<ZcProperty> sqProps = new ArrayList<ZcProperty>();
		for (int i = 0; i < ids.length; i++) {
			ZcProperty prop = this.propService.findPropById(Integer.parseInt(ids[i]));
			if (loginUser.getId() != prop.getEmpId()) {
				return "redirect:/web/oa/prop/mine";
			}
			prop.setGhStatus(1);// 正在归还
			this.propService.updateProperty(prop);
			sqProps.add(prop);
		}
		// 发送邮件
		String tableStr = createMailTable(sqProps);
		User pps = loginUser;// this.userService.findById(prop.getEmpId());
		String deptName = OtherUtil.getDeptName(loginUser, this.userService);

		// 通知资产管理员
		List<User> gly = this.userService.findUserByRole("固定资产管理员");
		for (User user : gly) {
			String to = user.getEmail();
			String copyTo = null;
			String fromName = pps.getName();
			this.sendMailForPropGh(user.getName(), fromName, to, copyTo, tableStr, deptName);
		}
		return "redirect:/web/oa/prop/mine";
	}

	@RequestMapping("oa/prop/goWx")
	public String goWx(Integer id, HttpSession session, Model model) {
		ZcProperty prop = this.propService.findPropById(id);
		model.addAttribute("prop", prop);
		return "oa/prop/wxSq";
	}

	@RequestMapping("oa/prop/goZx")
	public String goZx(Integer id, HttpSession session, Model model) {
		ZcProperty prop = this.propService.findPropById(id);
		model.addAttribute("prop", prop);
		return "oa/prop/zxSq";
	}

	@RequestMapping("oa/prop/zxSq")
	public String zxSq(int propId, String content, String reason, HttpSession session, Model model) {
		ZcProperty prop = this.propService.findPropById(propId);
		User loginUser = this.getLoginUser(session);
		// 获取登陆用户的所属部门（可能为空）
		Dept dept = this.getLoginUserDept(session);
		String deptName = OtherUtil.getDeptName(loginUser, dept);

		ZcPropWb sq = new ZcPropWb();

		sq.setContent(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		// String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		sq.setProposer(loginUser.getId());
		sq.setProposerName(loginUser.getName());

		sq.setReason(reason);
		sq.setPropId(propId);
		sq.setWb(1);// 0-注销

		sq.setStatus(1);
		sq.setMgrId(8);// 设置为崔莺莺。
		sq.setDirectId(Consts.managerId);
		sq.setBossId(Consts.managerId);
		sq.setCurrentId(8);
		// 添加申请
		this.propService.addPropWb(sq);

		List<ZcProperty> sqProps = new ArrayList<ZcProperty>();
		sqProps.add(prop);
		// 发送邮件
		String tableStr = createMailTable(sqProps);

		// 发送邮件给上级审批
		User pps = loginUser;// userService.findById(leave.getProposer());
		User handler = userService.findById(sq.getCurrentId());

		String copyTo = null;
		/*
		 * //别人代理我的 User bdlUser =
		 * roleService.findDailiByOwnerId(handler.getId()); String copyTo =
		 * null;
		 * 
		 * if(bdlUser!=null){ List<Menu> menus =
		 * menuService.findDlMenu(bdlUser.getId(), handler.getId());
		 * if(OtherUtil.containsMenu(Consts.gdzcsl, menus)){
		 * 
		 * copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
		 * if(Consts.devMode == 0){//应用环境 copyTo = bdlUser.getEmail(); } } }
		 */

		this.sendMailForZx(handler.getName(), pps.getName(), handler.getEmail(), copyTo, tableStr, reason, deptName);

		model.addAttribute("msg", "申请已经提交，请等待审批");
		model.addAttribute("prop", prop);
		return "oa/prop/zxSq";

	}

	@RequestMapping("oa/prop/wxSq")
	public String wxSq(int propId, String content, Integer money, HttpSession session, Model model) {
		ZcProperty prop = this.propService.findPropById(propId);
		User loginUser = this.getLoginUser(session);
		// 获取登陆用户的所属部门（可能为空）
		Dept dept = this.getLoginUserDept(session);
		String deptName = OtherUtil.getDeptName(loginUser, dept);
		List<Role> roles = this.getLoginUserRole(session);

		ZcPropWb sq = new ZcPropWb();

		sq.setContent(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		// String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		sq.setProposer(loginUser.getId());
		sq.setProposerName(loginUser.getName());

		sq.setMoney(money);
		sq.setPropId(propId);
		sq.setWb(0);// 0-维修
		sq.setStatus(1);
		sq.setMgrId(8);// 设置为崔莺莺。
		sq.setDirectId(Consts.managerId);
		sq.setBossId(Consts.managerId);
		sq.setCurrentId(8);
		// 添加申请
		this.propService.addPropWb(sq);

		List<ZcProperty> sqProps = new ArrayList<ZcProperty>();
		sqProps.add(prop);
		// 发送邮件
		String tableStr = createMailTable(sqProps);

		// 发送邮件给上级审批
		User pps = loginUser;// userService.findById(leave.getProposer());
		User handler = userService.findById(sq.getCurrentId());

		String copyTo = null;
		/*
		 * //别人代理我的 User bdlUser =
		 * roleService.findDailiByOwnerId(handler.getId()); String copyTo =
		 * null;
		 * 
		 * if(bdlUser!=null){ List<Menu> menus =
		 * menuService.findDlMenu(bdlUser.getId(), handler.getId());
		 * if(OtherUtil.containsMenu(Consts.gdzcsl, menus)){
		 * 
		 * copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
		 * if(Consts.devMode == 0){//应用环境 copyTo = bdlUser.getEmail(); } } }
		 */

		this.sendMailForWx(handler.getName(), pps.getName(), handler.getEmail(), copyTo, tableStr, money, deptName);

		model.addAttribute("msg", "申请已经提交，请等待审批");
		model.addAttribute("prop", prop);
		return "oa/prop/wxSq";

	}

	@RequestMapping("oa/prop/ff")
	public String ff(HttpSession session, Model model) {
		List<Role> roles = this.getLoginUserRole(session);
		if (!RoleUtil.hasRole(roles, "固定资产管理员")) {
			return "redirect:/web/login";
		}
		List<ZcPropLy> lys = propService.findUnhanderLySp();
		model.addAttribute("list", lys);
		return "oa/prop/ff";
	}

	@RequestMapping("oa/prop/gh")
	public String gh(HttpSession session, Model model) {
		List<ZcProperty> props = this.propService.findPropForGh();
		List<ZcPlace> places = this.propService.findAllPlace();
		model.addAttribute("list", props);
		model.addAttribute("places", places);
		return "oa/prop/gh";
	}

	@RequestMapping("oa/prop/sgh")
	public String sgh(Integer ffType, String[] ids, HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);
		String dayStr = DateUtil.getCurrentTime(Consts.chinaDateTimeFmt).substring(0, 11);
		int len = ids.length;

		int[] propIds = new int[len];
		int[] empIds = new int[len];

		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		for (int i = 0; i < len; i++) {
			String[] ss = ids[i].split(",");
			propIds[i] = Integer.parseInt(ss[0]);
			empIds[i] = Integer.parseInt(ss[1]);
			int empId = empIds[i];
			ArrayList<Integer> ary = map.get(empId);
			if (null == ary) {
				ary = new ArrayList<Integer>();
				map.put(empId, ary);
			}
			ary.add(propIds[i]);

		}

		HashMap<User, ArrayList<ZcProperty>> map2 = new HashMap<User, ArrayList<ZcProperty>>();
		Set<Integer> keys = map.keySet();

		for (Integer empId : keys) {
			User u = this.userService.findById(empId);
			ArrayList<Integer> pids = map.get(empId);
			ArrayList<ZcProperty> ps = new ArrayList<ZcProperty>();
			for (Integer pid : pids) {
				ps.add(this.propService.findPropById(pid));
			}
			map2.put(u, ps);
		}

		if (ffType == 1) {
			this.propService.updateGhProp(propIds, empIds, 1, loginUser.getId(), dayStr);
			// 发送邮件
			Set<User> ukeys = map2.keySet();
			for (User u : ukeys) {
				ArrayList<ZcProperty> ps = map2.get(u);
				// 发送给用户
				String table = this.createMailTable(ps);
				this.sendGhMailToUser(u.getName(), loginUser.getName(), u.getEmail(), loginUser.getEmail(), table);

				// 发送邮件通知上级
				User sUser = u;
				List<Role> uRoles = this.roleService.findRolesByEmpId(u.getId());
				List<User> sups = this.userService.findSuperiors(sUser, uRoles);

				this.sendGhMailToUserSups(sups, u.getName(), loginUser.getName(), table);
			}
		} else if (ffType == -1) {
			this.propService.updateGhProp(propIds, empIds, -1, loginUser.getId(), dayStr);
			// 发送邮件
			Set<User> ukeys = map2.keySet();
			for (User u : ukeys) {
				ArrayList<ZcProperty> ps = map2.get(u);
				// 发送给用户
				String table = this.createMailTable(ps);
				this.sendCGhailToUser(u.getName(), loginUser.getName(), u.getEmail(), loginUser.getEmail(), table);
			}
		}

		List<ZcProperty> props = this.propService.findPropForGh();
		List<ZcPlace> places = this.propService.findAllPlace();
		model.addAttribute("list", props);
		model.addAttribute("places", places);
		model.addAttribute("msg", "操作成功");
		return "oa/prop/gh";
	}

	@RequestMapping("oa/prop/mine")
	public String mine(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);

		List<ZcProperty> props = this.propService.findPropByEmpId(loginUser.getId());
		model.addAttribute("list", props);
		for (ZcProperty li : props) {
			if (li.getGhStatus() == 0) {
				model.addAttribute("ghStatus", li.getGhStatus());
			}
		}
		return "oa/prop/mine";
	}

	@RequestMapping("oa/prop/editLyff/{id}")
	public String editLyff(@PathVariable("id") int id, HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		ZcPropLy ly = this.propService.findLyById(id);
		User propEmp = this.userService.findById(ly.getProposer());
		List<Role> roles = this.getLoginUserRole(session);
		// Dept dept = this.userService.findDeptById(ly)

		if (!RoleUtil.hasRole(roles, "固定资产管理员") || ly.getStatus() != 4 || ly.getLyStatus() != 0) {
			return "redirect:/web/oa/prop/ff";
		}

		List<ZcProperty> props = this.propService.findPropByLy(ly.getId());

		model.addAttribute("sq", ly);
		model.addAttribute("list", props);
		model.addAttribute("propEmp", propEmp);

		return "oa/prop/editLyff";
	}

	@RequestMapping("oa/prop/sff")
	public String sff(Integer ffType, int lyId, int[] ids, HttpSession session, Model model) {

		User loginUser = this.getLoginUser(session);

		int len = ids.length;

		ArrayList<ZcProperty> ps = new ArrayList<ZcProperty>();
		for (int i = 0; i < len; i++) {
			int pid = ids[i];
			ps.add(this.propService.findPropById(pid));
		}

		ZcPropLy ly = this.propService.findLyById(lyId);
		User u = this.userService.findById(ly.getProposer());
		String table = MailTableUtil.createPropsend(ps);
		if (ffType == 1) {
			ly.setLyStatus(1);
			ly.setLyBz(loginUser.getName() + "，成功发放");
			this.propService.updateLyStatus(ly);
			// 修改物品的属性，添加日志
			this.propService.ffProps(ps, u);
			// TODO 给申请人发送邮件
			mailTableUtil.sendFFMailToUser(u.getName(), u.getEmail(), loginUser.getEmail(), table);
			// TODO 发送邮件通知上级
			User sUser = u;
			List<Role> uRoles = this.roleService.findRolesByEmpId(u.getId());
			List<User> sups = this.userService.findSuperiors(sUser, uRoles);
			mailTableUtil.sendFFMailToUserSups(sups, u.getName(), loginUser.getName(), table);
		} else if (ffType == -1) {
			ly.setLyStatus(-1);
			ly.setLyBz(loginUser.getName() + "，取消发放");
			this.propService.updateLyStatus(ly);
			// 发送邮件
			this.sendCFFMailToUser(u.getName(), loginUser.getName(), u.getEmail(), loginUser.getEmail(), table);
		}

		return "redirect:/web/oa/prop/ff?msg=1";
	}

	@RequestMapping("oa/prop/zyRecord")
	public String zyRecord(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		List<ZcPropZy> list = this.propService.findAllZy();// 所有维修记录
		// d
		model.addAttribute("list", list);
		return "oa/prop/zyRecord";
	}

	@RequestMapping("oa/prop/zyDetail/{id}")
	public String zyDetail(@PathVariable("id") int id, HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		ZcPropZy zy = this.propService.findZyById(id);
		User propEmp = this.userService.findById(zy.getProposer());
		User zyEmp = this.userService.findById(zy.getUid());
		// Dept dept = this.userService.findDeptById(ly)

		List<ZcProperty> props = this.propService.findPropByZy(zy.getId());

		model.addAttribute("sq", zy);
		model.addAttribute("list", props);
		model.addAttribute("propEmp", propEmp);
		model.addAttribute("zyEmp", zyEmp);

		return "oa/prop/zyDetail";
	}

	@RequestMapping("oa/prop/zy")
	public String zySq(Integer zcEmpId, Integer[] ids, Integer uid, String content, HttpSession session, Model model) {

		// User loginUser = this.getLoginUser(session);
		// 获取登陆用户的所属部门（可能为空）
		// Dept dept = this.getLoginUserDept(session);
		List<Role> roles = this.getLoginUserRole(session);

		if (!RoleUtil.hasRole(roles, "固定资产管理员")) {// 必须是资产管理员
			return "redirect:/web/login";
		}

		if (null == ids || ids.length == 0) {
			return "oa/prop/zy";
		}

		User zcUser = this.userService.findById(zcEmpId);
		Dept zcDept = this.userService.findDeptById(zcUser.getDeptId());
		String deptName = OtherUtil.getDeptName(zcUser, zcDept);

		User zyUser = this.userService.findById(uid);
		Dept zyDept = this.userService.findDeptById(zyUser.getDeptId());

		ZcPropZy sq = new ZcPropZy();
		sq.setContent(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		// String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		sq.setProposer(zcUser.getId());
		sq.setProposerName(zcUser.getName());
		sq.setUid(uid);
		sq.setuName(zyUser.getName());

		ZcUtil.zczy(sq, zcDept, zyDept, zyUser, roles);

		this.propService.addPropZy(sq, ids);

		model.addAttribute("msg", "申请成功，请等待审批");

		// 转移的资产
		List<ZcProperty> sqProps = new ArrayList<ZcProperty>();
		List<Integer> list = new ArrayList<Integer>();
		int len = ids.length;
		for (int i = 0; i < len; i++) {
			Integer id = ids[i];
			if (list.contains(id)) {
				continue;
			}
			ZcProperty p = this.propService.findPropById(id);
			sqProps.add(p);
			list.add(id);
		}

		String tableStr = createMailTable(sqProps);

		// 发送邮件给上级审批
		// User pps = zcUser;//userService.findById(leave.getProposer());
		User handler = userService.findById(sq.getCurrentId());

		// 别人代理我的
		User bdlUser = roleService.findDailiByOwnerId(handler.getId());
		String copyTo = null;

		if (bdlUser != null) {
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), handler.getId());
			if (OtherUtil.containsMenu(Consts.gdzcsl, menus)) {

				copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
				if (Consts.devMode == 0) {// 应用环境
					copyTo = bdlUser.getEmail();
				}
			}
		}

		this.sendMailForZy(handler.getName(), zcUser, zyUser, handler.getEmail(), copyTo, tableStr, deptName);
		return "oa/prop/zy";
	}

	// 采购申请
	@RequestMapping("oa/prop/cg")
	public String cgSq(String[] names, String brands[], int types[], int places[], String[] bzs, char[] units,
			double[] prices, int[] nums, double[] moneys, String content, HttpSession session, Model model) {

		if (null == names || names.length == 0) {
			List<ZcPlace> selectPlaces = this.propService.findAllPlace();
			List<ZcType> selectTypes = this.propService.findSecondTypes();
			model.addAttribute("places", selectPlaces);
			model.addAttribute("types", selectTypes);
			return "oa/prop/cg";
		}
		// 判断一下，如果是刘总。。。。

		User loginUser = this.getLoginUser(session);
		// 获取登陆用户的所属部门（可能为空）
		Dept dept = this.getLoginUserDept(session);
		String deptName = OtherUtil.getDeptName(loginUser, dept);
		List<Role> roles = this.getLoginUserRole(session);

		ZcPropCg sq = new ZcPropCg();
		sq.setContent(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		// String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		sq.setProposer(loginUser.getId());
		sq.setProposerName(loginUser.getName());
		// 类别、地点选择
		List<ZcPlace> selectPlaces = this.propService.findAllPlace();
		List<ZcType> selecTypes = this.propService.findSecondTypes();
		model.addAttribute("places", selectPlaces);
		model.addAttribute("types", selecTypes);
		ApproveStaff as= this.userService.findApproveIdByUserId(loginUser.getId());
		Integer sup = as.getDeptSupremo();
		Integer tre = as.getTreasurer();
		Integer man = as.getManager();
		ZcUtil.zccg(sq, dept, roles, tre);
		if(loginUser.getId() == sup || sup == tre || sup == man) {
			sq.setCurrentId(tre);
			sq.setStatus(3);
			sq.setDirectId(null);
		}
		this.propService.addPropCg(sq, names, bzs, units, prices, nums, moneys, brands, types, places);
		model.addAttribute("msg", "您的采购申请已经提交，请等待审批");

		// 申领的资产
		String tableStr = MailTableUtil.createMailTableForCg(sq, names, bzs, units, prices, nums, moneys, brands, types,
				places, selectPlaces, selecTypes, content);

		// 发送邮件给上级审批
		User pps = loginUser;// userService.findById(leave.getProposer());
		User handler = userService.findById(sq.getCurrentId());

		// 别人代理我的
		User bdlUser = roleService.findDailiByOwnerId(handler.getId());
		String copyTo = null;

		if (bdlUser != null) {
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), handler.getId());
			if (OtherUtil.containsMenu(Consts.gdzcsl, menus)) {
				copyTo = bdlUser.getEmail();
			}
		}
		this.sendMailForCg(handler.getName(), pps.getName(), handler.getEmail(), copyTo, tableStr, deptName);
		return "oa/prop/cg";
	}

	@RequestMapping("oa/prop/ly")
	public String sq(String[] names, String[] bzs, String content, HttpSession session, Model model) {

		if (null == names || names.length == 0) {
			return "oa/prop/ly";
		}
		// 判断一下，如果是刘总。。。。

		User loginUser = this.getLoginUser(session);
		// 获取登陆用户的所属部门（可能为空）
		Dept dept = this.getLoginUserDept(session);
		String deptName = OtherUtil.getDeptName(loginUser, dept);
		List<Role> roles = this.getLoginUserRole(session);

		ZcPropLy sq = new ZcPropLy();
		sq.setContent(content);
		sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
		// String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
		sq.setProposer(loginUser.getId());
		sq.setProposerName(loginUser.getName());

//		ZcUtil.zcly(sq, dept, roles);
		// 新审批流程，跳过重复审批
		Map<String, Integer> map = this.userService.getApproveStatus(loginUser.getId(), 0);
		int status = map.get("status");
		int currentId = map.get("currentId");
		sq.setCurrentId(currentId);
		sq.setStatus(status);
		ApproveStaff as = this.userService.findApproveIdByUserId(loginUser.getId());
		sq.setMgrId(as.getDeptDirector());
		sq.setDirectId(as.getDeptSupremo());
		sq.setBossId(as.getManager());
		// 判断跳过
		if(as.getDeptDirector() == as.getDeptSupremo() ||
				as.getDeptDirector() == as.getManager() ||
				loginUser.getId() == as.getDeptDirector()) {
			sq.setMgrId(null);
		}
		if(as.getDeptSupremo() == as.getManager()) {
			sq.setDirectId(null);
		}
		
		this.propService.addPropLy(sq, names, bzs);
		model.addAttribute("msg", "您的申领提交成功，请等待审批");

		// 申领的资产
		// String tableStr = createMailTableForLy(names, bzs);

		// 发送邮件给上级审批
		User pps = loginUser;// userService.findById(leave.getProposer());
		User handler = userService.findById(sq.getCurrentId());

		String table = MailTableUtil.buildApplyPropTable(names, bzs, content);
		if(status != 4) {
			mailTableUtil.sendMailTable(handler, pps, "固定资产申领", table);
		}
//		String text = MailContentBuilder.buildEmailContent(EmailType.APPROVAL_REMIND_NOTICE, handler.getName(),
//				pps.getName(), deptName, "固定资产申领", null, table);
//		this.sendMailForLy(handler.getEmail(), copyTo, text);

		return "oa/prop/ly";
	}
	// old....
	/*
	 * @RequestMapping("oa/prop/ly1") public String sq1(Integer[] ids, String
	 * content, HttpSession session, Model model){
	 * 
	 * if(null == ids || ids.length == 0){ return "oa/prop/ly"; }
	 * 
	 * User loginUser = this.getLoginUser(session); //获取登陆用户的所属部门（可能为空） Dept
	 * dept = this.getLoginUserDept(session); List<Role> roles =
	 * this.getLoginUserRole(session);
	 * 
	 * ZcPropLy sq = new ZcPropLy(); sq.setContent(content);
	 * sq.setCreateTime(DateUtil.getCurrentTime(Consts.chinaDateTimeFmt));
	 * //String dayStr = DateUtil.getCurrentTime(Consts.chinaDateFmt);
	 * sq.setProposer(loginUser.getId());
	 * sq.setProposerName(loginUser.getName());
	 * 
	 * ZcUtil.zcly(sq, dept, roles);
	 * 
	 * this.propService.addPropLy(sq, ids);
	 * 
	 * model.addAttribute("msg","申领成功，请等待审批");
	 * 
	 * //申领的资产 List<ZcProperty> sqProps = new ArrayList<ZcProperty>(); int len =
	 * ids.length; for(int i=0; i<len; i++){ Integer id = ids[i]; ZcProperty p =
	 * this.propService.findPropById(id); sqProps.add(p); }
	 * 
	 * String tableStr = createMailTable(sqProps);
	 * 
	 * //发送邮件给上级审批 User pps =
	 * loginUser;//userService.findById(leave.getProposer()); User handler =
	 * userService.findById(sq.getCurrentId());
	 * 
	 * //别人代理我的 User bdlUser = roleService.findDailiByOwnerId(handler.getId());
	 * String copyTo = null;
	 * 
	 * if(bdlUser!=null){ List<Menu> menus =
	 * menuService.findDlMenu(bdlUser.getId(), handler.getId());
	 * if(OtherUtil.containsMenu(Consts.gdzcsl, menus)){
	 * 
	 * copyTo = "long.lian@sohu.com";// bdlUser.getEmail(); if(Consts.devMode ==
	 * 0){//应用环境 copyTo = bdlUser.getEmail(); } } }
	 * 
	 * this.sendMailForLy(handler.getName(), pps.getName(), handler.getEmail(),
	 * copyTo, tableStr);
	 * 
	 * return "oa/prop/ly"; }
	 */

	@RequestMapping("oa/prop/myLy")
	public String myLy(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		List<ZcPropLy> lys = this.propService.findLyByEmpId(loginUser.getId());
		model.addAttribute("list", lys);
		return "oa/prop/myLySqList";
	}

	@RequestMapping("oa/prop/myLySp")
	public String myLySp(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		List<ZcPropLy> lys = this.propService.findMyLySp(loginUser.getId());
		model.addAttribute("list", lys);
		return "oa/prop/myLySp";
	}

	@RequestMapping("oa/prop/myCgSp")
	public String myCgSp(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		List<ZcPropCg> cgs = this.propService.findMyCgSp(loginUser.getId());
		model.addAttribute("list", cgs);
		return "oa/prop/myCgSp";
	}

	// 没有处理的采购入库
	@RequestMapping("oa/prop/cgrk")
	public String cgrkList(HttpSession session, Model model) {
		List<ZcPropCg> cgs = this.propService.findCgByStatus(5);// 完成的
		model.addAttribute("list", cgs);
		return "oa/prop/cgrk";
	}

	// 处理完成的采购入库
	@RequestMapping("oa/prop/cgRecords")
	public String cgRecords(HttpSession session, Model model) {
		List<ZcPropCg> cgs = this.propService.findCgByStatus2();// 完成的
		model.addAttribute("list", cgs);
		return "oa/prop/cgRecords";
	}

	// 处理完成的采购入库
	@RequestMapping("oa/prop/myCgSpRecords")
	public String myCgSpRecords(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);

		List<User> allUsers = this.userService.findAllUsers();
		Map<Integer, String> userMap = new HashMap<Integer, String>();

		for (User u : allUsers) {
			userMap.put(u.getId(), u.getName());
		}

		List<ZcPropCg> cgs = this.propService.findAllCg();
		model.addAttribute("list", cgs);
		model.addAttribute("userMap", userMap);
		return "oa/prop/myCgSpRecords";
	}

	// 我的采购记录
	@RequestMapping("oa/prop/myCgRecords")
	public String myCgRecords(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);

		List<User> allUsers = this.userService.findAllUsers();
		Map<Integer, String> userMap = new HashMap<Integer, String>();

		for (User u : allUsers) {
			userMap.put(u.getId(), u.getName());
		}

		List<ZcPropCg> cgs = this.propService.findMyCgRecords(loginUser.getId());
		model.addAttribute("list", cgs);
		model.addAttribute("userMap", userMap);
		return "oa/prop/myCgRecords";
	}

	@RequestMapping("oa/prop/myZySp")
	public String myZySp(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		List<ZcPropZy> zys = this.propService.findMyZySp(loginUser.getId());
		model.addAttribute("list", zys);
		return "oa/prop/myZySp";
	}

	@RequestMapping("oa/prop/myWxSp")
	public String myWxSp(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		List<ZcPropWb> wxs = this.propService.findMyWbSp(loginUser.getId(), 0);
		model.addAttribute("list", wxs);
		return "oa/prop/myWxSp";
	}

	@RequestMapping("oa/prop/wxRecord")
	public String wxRecord(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		List<ZcPropWb> wxs = this.propService.findAllWbByType(0);// 所有维修记录
		model.addAttribute("list", wxs);
		return "oa/prop/wxRecord";
	}

	@RequestMapping("oa/prop/myZxSp")
	public String myZxSp(HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		List<ZcPropWb> zxs = this.propService.findMyWbSp(loginUser.getId(), 1);
		model.addAttribute("list", zxs);
		return "oa/prop/myZxSp";
	}

	@RequestMapping("oa/prop/editLy/{id}")
	public String editLy(@PathVariable("id") int id, HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		ZcPropLy ly = this.propService.findLyById(id);
		User propEmp = this.userService.findById(ly.getProposer());
		// Dept dept = this.userService.findDeptById(ly)

		if (loginUser.getId() != ly.getCurrentId()) {
			return "redirect:/web/oa/prop/myLySp";
		}

		List<ZcProperty> props = this.propService.findPropByLy(ly.getId());

		model.addAttribute("sq", ly);
		model.addAttribute("list", props);
		model.addAttribute("propEmp", propEmp);

		return "oa/prop/editLy";
	}

	@RequestMapping("oa/prop/editCg/{id}")
	public String editCg(@PathVariable("id") int id, HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		ZcPropCg cg = this.propService.findCgById(id);
		User propEmp = this.userService.findById(cg.getProposer());
		// Dept dept = this.userService.findDeptById(ly)

		if (loginUser.getId() != cg.getCurrentId()) {
			return "redirect:/web/oa/prop/myCgSp";
		}

		List<ZcProperty> props = this.propService.findPropByCg(cg.getId());

		model.addAttribute("sq", cg);
		model.addAttribute("list", props);
		model.addAttribute("propEmp", propEmp);

		if (cg.getStatus() == 2) {
			return "oa/prop/editCgDj";
		} else {
			return "oa/prop/editCg";
		}
	}

	@RequestMapping("oa/prop/cgDetail/{id}")
	public String cgDetail(@PathVariable("id") int id, HttpSession session, Model model) {
		ZcPropCg cg = this.propService.findCgById(id);
		User propEmp = this.userService.findById(cg.getProposer());

		List<ZcProperty> props = this.propService.findPropByCg(cg.getId());

		model.addAttribute("sq", cg);
		model.addAttribute("list", props);
		model.addAttribute("propEmp", propEmp);

		return "oa/prop/cgDetail";
	}

	@RequestMapping("oa/prop/cgDetail2/{id}")
	public String cgDetail2(@PathVariable("id") int id, HttpSession session, Model model) {
		ZcPropCg cg = this.propService.findCgById(id);
		User propEmp = this.userService.findById(cg.getProposer());

		List<ZcProperty> props = this.propService.findPropByCg(cg.getId());

		model.addAttribute("sq", cg);
		model.addAttribute("list", props);
		model.addAttribute("propEmp", propEmp);

		return "oa/prop/cgDetailForProposer";
	}

	@RequestMapping("oa/prop/cgDetail3/{id}")
	public String cgDetail3(@PathVariable("id") int id, HttpSession session, Model model) {
		ZcPropCg cg = this.propService.findCgById(id);
		User propEmp = this.userService.findById(cg.getProposer());

		List<ZcProperty> props = this.propService.findPropByCg(cg.getId());

		model.addAttribute("sq", cg);
		model.addAttribute("list", props);
		model.addAttribute("propEmp", propEmp);

		return "oa/prop/cgDetailForSp";
	}

	@RequestMapping("oa/prop/editCgForAdd/{id}")
	public String editCgForAdd(@PathVariable("id") int id, HttpSession session, Model model) {
		ZcPropCg cg = this.propService.findCgById(id);
		User propEmp = this.userService.findById(cg.getProposer());

		List<ZcProperty> props = this.propService.findPropByCg(cg.getId());
		for (ZcProperty prop : props) {
			Integer num = prop.getNum();
			if (num == null || "".equals(num) || num == 0) {
				prop.setNum(1);
			}
		}
		model.addAttribute("sq", cg);
		model.addAttribute("list", props);
		model.addAttribute("propEmp", propEmp);

		return "oa/prop/editCgForAdd";
	}

	@RequestMapping("oa/prop/lyDetail/{id}")
	public String lyDetail(@PathVariable("id") int id, HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		ZcPropLy ly = this.propService.findLyById(id);
		User propEmp = loginUser;// this.userService.findById(ly.getProposer());
		// Dept dept = this.userService.findDeptById(ly)

		if (loginUser.getId() != ly.getProposer()) {
			return "redirect:/web/oa/prop/myLy";
		}

		List<ZcProperty> props = this.propService.findPropByLy(ly.getId());

		model.addAttribute("sq", ly);
		model.addAttribute("list", props);
		model.addAttribute("propEmp", propEmp);

		return "oa/prop/lyDetail";
	}

	@RequestMapping("oa/prop/editZy/{id}")
	public String editZy(@PathVariable("id") int id, HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		ZcPropZy zy = this.propService.findZyById(id);
		User propEmp = this.userService.findById(zy.getProposer());
		User zyEmp = this.userService.findById(zy.getUid());
		// Dept dept = this.userService.findDeptById(ly)

		if (loginUser.getId() != zy.getCurrentId()) {
			return "redirect:/web/oa/prop/myZySp";
		}

		List<ZcProperty> props = this.propService.findPropByZy(zy.getId());

		model.addAttribute("sq", zy);
		model.addAttribute("list", props);
		model.addAttribute("propEmp", propEmp);
		model.addAttribute("zyEmp", zyEmp);

		return "oa/prop/editZy";
	}

	@RequestMapping("oa/prop/editWx/{id}")
	public String editWx(@PathVariable("id") int id, HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		ZcPropWb wb = this.propService.findWbById(id);
		User propEmp = this.userService.findById(wb.getProposer());
		// Dept dept = this.userService.findDeptById(ly)

		if (loginUser.getId() != wb.getCurrentId()) {
			return "redirect:/web/oa/prop/myLySp";
		}

		ZcProperty prop = this.propService.findPropById(wb.getPropId());
		// List<ZcProperty> props = this.propService.findPropByLy(ly.getId());

		model.addAttribute("sq", wb);
		model.addAttribute("prop", prop);
		model.addAttribute("propEmp", propEmp);

		return "oa/prop/editWx";
	}

	@RequestMapping("oa/prop/wxDetail/{id}")
	public String wxDetail(@PathVariable("id") int id, HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		ZcPropWb wb = this.propService.findWbById(id);
		User propEmp = this.userService.findById(wb.getProposer());

		ZcProperty prop = this.propService.findPropById(wb.getPropId());

		model.addAttribute("sq", wb);
		model.addAttribute("prop", prop);
		model.addAttribute("propEmp", propEmp);

		return "oa/prop/wxDetail";
	}

	@RequestMapping("oa/prop/editZx/{id}")
	public String editZx(@PathVariable("id") int id, HttpSession session, Model model) {
		User loginUser = (User) this.getLoginUser(session);
		ZcPropWb wb = this.propService.findWbById(id);
		User propEmp = this.userService.findById(wb.getProposer());
		// Dept dept = this.userService.findDeptById(ly)

		if (loginUser.getId() != wb.getCurrentId()) {
			return "redirect:/web/oa/prop/myLySp";
		}

		ZcProperty prop = this.propService.findPropById(wb.getPropId());
		// List<ZcProperty> props = this.propService.findPropByLy(ly.getId());

		model.addAttribute("sq", wb);
		model.addAttribute("prop", prop);
		model.addAttribute("propEmp", propEmp);

		return "oa/prop/editZx";
	}

	@RequestMapping("oa/prop/wxSp")
	public String wxSp(int id, String yj, String sp, String submitCode, Model model, HttpSession session) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/prop/myWxSp";
		}
		session.removeAttribute(Consts.submitCode);

		ZcPropWb wb = this.propService.findWbById(id);// .findById(id);
		User loginUser = (User) session.getAttribute("loginUser");

		int currentSpId = wb.getCurrentId();

		if (wb == null || loginUser.getId() != currentSpId) { // 不归我审批
			return "redirect:/web/oa/prop/myWxSp";
		}
		int status = wb.getStatus();
		if (status == -1 || status == 4) { // 已经结束
			return "redirect:/web/oa/prop/myWxSp";
		}

		if (status == 1) {
			wb.setMgrCmt(yj);
			if ("审批通过".equals(sp)) {
				wb = spNext(wb, loginUser, status, yj);
			} else {
				wb.setStatus(-1);
			}
		} else if (status == 2) {
			wb.setDirectCmt(yj);

			if ("审批通过".equals(sp)) {
				wb = spNext(wb, loginUser, status, yj);
			} else {
				wb.setStatus(-1);
			}

		} else if (status == 3) {
			wb.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				wb.setStatus(4);
			} else {
				wb.setStatus(-1);
			}
		}

		/*
		 * boolean isDaili = this.isDailiLogin(session); Integer dailiId = null;
		 * if(isDaili){ User dlUser = this.getDailiUser(session); dailiId =
		 * dlUser.getId(); }
		 */

		List<ZcProperty> sqProps = new ArrayList<ZcProperty>();
		ZcProperty pro = this.propService.findPropById(wb.getPropId());
		sqProps.add(pro);

		String tableStr = createMailTable(sqProps);

		User pps = userService.findById(wb.getProposer());
		String deptName = OtherUtil.getDeptName(pps, this.userService);
		User handler = userService.findById(wb.getCurrentId());

		this.propService.updateWb(wb, pro);

		User bdlUser = roleService.findDailiByOwnerId(handler.getId());
		String copyTo = null;

		if (bdlUser != null) {
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), handler.getId());
			if (OtherUtil.containsMenu(Consts.gdzcwx, menus)) {

				copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
				if (Consts.devMode == 0) {// 应用环境
					copyTo = bdlUser.getEmail();
				}
			}
		}

		if (wb.getStatus() == 4) { // 结束
			String to = pps.getEmail();

			String subject = "固定资产维修审核结果通知";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
			sb.append(pps.getName());
			sb.append(
					"</p><p style='font-size:14px;padding:5px 0px;'>您的一个固定资产维修申请<span style='color:green;'>通过审核</span>。请登录系统查看详情。</p>");
			sb.append(
					"<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, sb.toString());

		} else if (wb.getStatus() == -1) {
			String to = pps.getEmail();

			String subject = "固定资产维修审核结果通知";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
			sb.append(pps.getName());
			sb.append(
					"</p><p style='font-size:14px;padding:5px 0px;'>您的一个固定资产维修申请<span style='color:red;'>没有通过审核</span>。请登录系统查看详情。</p>");
			sb.append(
					"<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, sb.toString());
		} else {// 通知有审批
				// new ArrayList<ZcProperty>();

			this.sendMailForWx(handler.getName(), pps.getName(), handler.getEmail(), copyTo, tableStr, wb.getMoney(),
					deptName);
		}

		return "redirect:/web/oa/prop/myWxSp?msg=1";
	}

	@RequestMapping("oa/prop/zxSp")
	public String zxSp(int id, String yj, String sp, String submitCode, Model model, HttpSession session) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/prop/myZxSp";
		}
		session.removeAttribute(Consts.submitCode);

		ZcPropWb wb = this.propService.findWbById(id);// .findById(id);
		User loginUser = (User) session.getAttribute("loginUser");

		int currentSpId = wb.getCurrentId();

		if (wb == null || loginUser.getId() != currentSpId) { // 不归我审批
			return "redirect:/web/oa/prop/myZxSp";
		}
		int status = wb.getStatus();
		if (status == -1 || status == 4) { // 已经结束
			return "redirect:/web/oa/prop/myZxSp";
		}

		if (status == 1) {
			wb.setMgrCmt(yj);
			if ("审批通过".equals(sp)) {
				wb = spNext(wb, loginUser, status, yj);
			} else {
				wb.setStatus(-1);
			}
		} else if (status == 2) {
			wb.setDirectCmt(yj);

			if ("审批通过".equals(sp)) {
				wb = spNext(wb, loginUser, status, yj);
			} else {
				wb.setStatus(-1);
			}

		} else if (status == 3) {
			wb.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				wb.setStatus(4);
			} else {
				wb.setStatus(-1);
			}
		}

		/*
		 * boolean isDaili = this.isDailiLogin(session); Integer dailiId = null;
		 * if(isDaili){ User dlUser = this.getDailiUser(session); dailiId =
		 * dlUser.getId(); }
		 */

		List<ZcProperty> sqProps = new ArrayList<ZcProperty>();
		ZcProperty pro = this.propService.findPropById(wb.getPropId());
		sqProps.add(pro);

		String tableStr = createMailTable(sqProps);

		User pps = userService.findById(wb.getProposer());
		User handler = userService.findById(wb.getCurrentId());

		this.propService.updateWb(wb, pro);

		User bdlUser = roleService.findDailiByOwnerId(handler.getId());
		String copyTo = null;

		if (bdlUser != null) {
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), handler.getId());
			if (OtherUtil.containsMenu(Consts.gdzczx, menus)) {

				copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
				if (Consts.devMode == 0) {// 应用环境
					copyTo = bdlUser.getEmail();
				}
			}
		}

		if (wb.getStatus() == 4) { // 结束
			String to = pps.getEmail();

			String subject = "固定资产注销审核结果通知";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
			sb.append(pps.getName());
			sb.append(
					"</p><p style='font-size:14px;padding:5px 0px;'>您的一个固定资产注销申请<span style='color:green;'>通过审核</span>。请登录系统查看详情。</p>");
			sb.append(
					"<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, sb.toString());

		} else if (wb.getStatus() == -1) {
			String to = pps.getEmail();

			String subject = "固定资产注销审核结果通知";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
			sb.append(pps.getName());
			sb.append(
					"</p><p style='font-size:14px;padding:5px 0px;'>您的一个固定资产注销申请<span style='color:red;'>没有通过审核</span>。请登录系统查看详情。</p>");
			sb.append(
					"<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, sb.toString());
		} else {// 通知有审批
				// new ArrayList<ZcProperty>();
			String deptName = OtherUtil.getDeptName(pps, this.userService);
			this.sendMailForZx(handler.getName(), pps.getName(), handler.getEmail(), copyTo, tableStr, wb.getReason(),
					deptName);
		}

		return "redirect:/web/oa/prop/myZxSp?msg=1";
	}

	@RequestMapping("oa/prop/cgSpDj")
	public String cgSpDj(int applyId, double[] moneys, int[] ids, String submitCode, Model model, HttpSession session) {
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/prop/myCgSp";
		}
		session.removeAttribute(Consts.submitCode);

		ZcPropCg cg = this.propService.findCgById(applyId);// .findById(id);
		User loginUser = (User) session.getAttribute("loginUser");

		int currentSpId = cg.getCurrentId();

		if (cg == null || loginUser.getId() != currentSpId) { // 不归我审批
			return "redirect:/web/oa/prop/myCgSp";
		}
		int status = cg.getStatus();
		if (status == -1 || status == 5) { // 已经结束
			return "redirect:/web/oa/prop/myCgSp";
		}

		cg.setStatus(3);
		cg.setCurrentId(cg.getCaiwuId());// 告诉财务要审批了
		this.propService.updateCgPropMoney(cg, moneys, ids);

		List<ZcProperty> sqProps = this.propService.findPropByCg(cg.getId());
		boolean withMoney = true;
		String tableStr = createMailTableForCg(sqProps, withMoney);

		User pps = userService.findById(cg.getProposer());
		String deptName = OtherUtil.getDeptName(pps, this.userService);
		User handler = userService.findById(cg.getCurrentId());

		this.sendMailForCg(handler.getName(), pps.getName(), handler.getEmail(), null, tableStr, deptName);

		return "redirect:/web/oa/prop/myCgSp?msg=1";
	}

	@RequestMapping("oa/prop/cgSp")
	public String cgSp(int id, String yj, String sp, String submitCode, Model model, HttpSession session) {
		// id是t_zc_cg 的id
		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/prop/myCgSp";
		}
		session.removeAttribute(Consts.submitCode);

		ZcPropCg cg = this.propService.findCgById(id);// .findById(id);
		User loginUser = (User) session.getAttribute("loginUser");

		int currentSpId = cg.getCurrentId();

		if (cg == null || loginUser.getId() != currentSpId) { // 不归我审批
			return "redirect:/web/oa/prop/myCgSp";
		}
		int status = cg.getStatus();

		if (status == -1 || status == 5) { // 已经结束
			return "redirect:/web/oa/prop/myCgSp";
		}

		if (status == 1) {// 总监
			cg.setDirectCmt(yj);

			if ("审批通过".equals(sp)) {
				cg.setStatus(3);
				cg.setCurrentId(cg.getCaiwuId());
			} else {
				cg.setStatus(-1);
			}
		} else if (status == 3) { // 财务主管
			cg.setCaiwuCmt(yj);

			if ("审批通过".equals(sp)) {
				cg.setStatus(4);
				cg.setCurrentId(cg.getBossId());
			} else {
				cg.setStatus(-1);
			}

		} else if (status == 4) { // 总经理
			cg.setBossCmt(yj);

			if ("审批通过".equals(sp)) {
				cg.setStatus(5);
			} else {
				cg.setStatus(-1);
			}
		}
		List<ZcProperty> sqProps = this.propService.findPropByCg(cg.getId());
		// 类别、地点选择
		List<ZcPlace> selectPlaces = this.propService.findAllPlace();
		List<ZcType> selecTypes = this.propService.findSecondTypes();
		String tableStr = MailTableUtil.createMailTableForCg(cg, sqProps, selectPlaces, selecTypes);

		User pps = userService.findById(cg.getProposer());
		User handler = userService.findById(cg.getCurrentId());

		int statu = cg.getStatus();
		if(statu > 1 && status < 5) {
			mailTableUtil.sendMailTable(handler, pps, "固定资产采购", tableStr);
		} else {
			mailTableUtil.sendMaileResult2(pps, cg.getStatus(), "固定资产采购", tableStr);
		}
		this.propService.updateCg(cg);
		return "redirect:/web/oa/prop/myCgSp?msg=1";
	}

	@RequestMapping("oa/prop/lySp")
	public String lySp(int id, String yj, String sp, String submitCode, Model model, HttpSession session) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/prop/myLySp";
		}
		session.removeAttribute(Consts.submitCode);

		ZcPropLy ly = this.propService.findLyById(id);// .findById(id);
		User loginUser = (User) session.getAttribute("loginUser");

		int currentSpId = ly.getCurrentId();

		if (ly == null || loginUser.getId() != currentSpId) { // 不归我审批
			return "redirect:/web/oa/prop/myLySp";
		}
		int status = ly.getStatus();
		if (status == -1 || status == 4) { // 已经结束
			return "redirect:/web/oa/prop/myLySp";
		}
		
		ApproveStaff as = this.userService.findApproveIdByUserId(ly.getProposer());
		int dir = as.getDeptDirector();
		int sup = as.getDeptSupremo();
		int man = as.getManager();
		
		if (status == 1) {
			ly.setMgrCmt(yj);
			if ("审批通过".equals(sp)) {
				if(sup == man) {
					ly.setCurrentId(man);
					ly.setStatus(3);
				} else {
					ly.setCurrentId(sup);
					ly.setStatus(2);
				}
			} else {
				ly.setStatus(-1);
			}
		} else if (status == 2) {
			ly.setDirectCmt(yj);
			if ("审批通过".equals(sp)) {
				ly = spNext(ly, loginUser, status, yj);
			} else {
				ly.setStatus(-1);
			}

		} else if (status == 3) {
			ly.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				ly.setStatus(4);
			} else {
				ly.setStatus(-1);
			}
		}

		/*
		 * boolean isDaili = this.isDailiLogin(session); Integer dailiId = null;
		 * if(isDaili){ User dlUser = this.getDailiUser(session); dailiId =
		 * dlUser.getId(); }
		 */
		List<ZcProperty> sqProps = this.propService.findPropByLy(ly.getId());
		String tableStr = createMailTableForLy(sqProps);

		User pps = userService.findById(ly.getProposer());
		String deptName = OtherUtil.getDeptName(pps, this.userService);
		User handler = userService.findById(ly.getCurrentId());

		String table = MailTableUtil.buildApplyPropTable(sqProps, ly.getContent());
		
		if (ly.getStatus() == 4) { // 审批通过。需要做一系列事情
			// 通知资产管理员
			List<User> gly = this.userService.findUserByRole("固定资产管理员");
			for (User user : gly) {
				mailTableUtil.sendPropResult(user, pps, "固定资产申领", table);
			}
		} 
		this.propService.updateLy(ly);

		int statu = ly.getStatus();
		if(statu < 4 && statu > 1) {
			mailTableUtil.sendMailTable(handler, pps, "固定资产申领", table);
		} else {
			mailTableUtil.sendMaileResult2(pps, status, "固定资产申领", table);
		}
		return "redirect:/web/oa/prop/myLySp?msg=1";
	}

	// 转移审批
	@RequestMapping("oa/prop/zySp")
	public String zySp(int id, String yj, String sp, String submitCode, Model model, HttpSession session) {

		String sc = (String) session.getAttribute(Consts.submitCode);
		if (!sc.equals(submitCode)) {// 重复提交
			return "redirect:/web/oa/prop/myZySp";
		}
		session.removeAttribute(Consts.submitCode);

		ZcPropZy zy = this.propService.findZyById(id);// .findById(id);

		User loginUser = (User) session.getAttribute("loginUser");

		int currentSpId = zy.getCurrentId();

		if (zy == null || loginUser.getId() != currentSpId) { // 不归我审批
			return "redirect:/web/oa/prop/myZySp";
		}
		int status = zy.getStatus();
		if (status == -1 || status == 5) { // 已经结束
			return "redirect:/web/oa/prop/myZySp";
		}

		if (status == 1) {
			zy.setMgrCmt(yj);
			if ("审批通过".equals(sp)) {
				zy = spNext(zy, loginUser, status, yj);
			} else {
				zy.setStatus(-1);
			}
		} else if (status == 2) {
			zy.setMgr2Cmt(yj);

			if ("审批通过".equals(sp)) {
				zy = spNext(zy, loginUser, status, yj);
			} else {
				zy.setStatus(-1);
			}

		} else if (status == 3) {
			zy.setDirectCmt(yj);

			if ("审批通过".equals(sp)) {
				zy = spNext(zy, loginUser, status, yj);
			} else {
				zy.setStatus(-1);
			}

		} else if (status == 4) {
			zy.setBossCmt(yj);
			if ("审批通过".equals(sp)) {
				zy.setStatus(5);
			} else {
				zy.setStatus(-1);
			}
		}

		/*
		 * boolean isDaili = this.isDailiLogin(session); Integer dailiId = null;
		 * if(isDaili){ User dlUser = this.getDailiUser(session); dailiId =
		 * dlUser.getId(); }
		 */
		List<ZcProperty> sqProps = this.propService.findPropByZy(zy.getId());
		String tableStr = createMailTable(sqProps);

		User pps = userService.findById(zy.getProposer());
		User zyUser = userService.findById(zy.getUid());
		User handler = userService.findById(zy.getCurrentId());

		this.propService.updateZy(zy, sqProps, pps, zyUser);
		/*
		 * if(zy.getStatus() == 5){ //审批通过。需要做一系列事情
		 * 
		 * 
		 * //通知双方资产转移 List<User> gly =
		 * this.userService.findUserByRole("固定资产管理员"); for(User user : gly){
		 * String to = user.getEmail(); String copyTo = null; String fromName =
		 * pps.getName(); this.sendMailForPropDiliver(user.getName(), fromName,
		 * to, copyTo, tableStr); }
		 * 
		 * }
		 */

		// 别人代理我的
		User bdlUser = roleService.findDailiByOwnerId(handler.getId());
		String copyTo = null;

		if (bdlUser != null) {
			List<Menu> menus = menuService.findDlMenu(bdlUser.getId(), handler.getId());
			if (OtherUtil.containsMenu(Consts.gdzcsl, menus)) {

				copyTo = "long.lian@sohu.com";// bdlUser.getEmail();
				if (Consts.devMode == 0) {// 应用环境
					copyTo = bdlUser.getEmail();
				}
			}
		}

		if (zy.getStatus() == 5) { // 结束
			String to = pps.getEmail();
			copyTo = zyUser.getEmail();
			// d
			String subject = "固定资产转移通知";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，各位");
			sb.append("</p><p style='font-size:14px;padding:5px 0px;'>").append(pps.getName())
					.append("的一个固定资产转移<span style='color:green;'>通过审核</span>。");
			sb.append(pps.getName()).append("向").append(zyUser.getName()).append("转移下列资产，请注意交接。");
			sb.append("</p>");
			sb.append(tableStr);
			sb.append(
					"<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, subject, sb.toString());

		} else if (zy.getStatus() == -1) {
			String to = pps.getEmail();

			String subject = "固定资产转移通知";
			StringBuilder sb = new StringBuilder("<html><body><p style='font-size:14px;padding:5px;'>你好，");
			sb.append(pps.getName());
			sb.append(
					"</p><p style='font-size:14px;padding:5px 0px;'>您的一个固定资产申领<span style='color:red;'>没有通过审核</span>。");
			sb.append("您的下列资产不需要转移。");
			sb.append("</p>");
			sb.append(tableStr);
			sb.append(
					"<p style='font-size:13px;font-weight:bold;padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
			mailUtil.sendEMail(to, null, Consts.defaultFrom, subject, sb.toString());
		} else {// 通知有审批
				// new ArrayList<ZcProperty>();
			String deptName = OtherUtil.getDeptName(pps, this.userService);
			this.sendMailForZy(handler.getName(), pps, zyUser, handler.getEmail(), copyTo, tableStr, deptName);
		}

		return "redirect:/web/oa/prop/myZySp?msg=1";
	}

	private ZcPropLy spNext(ZcPropLy ly, User user, int status, String yj) {
		status++;
		if (status == 2) {
			Integer did = ly.getDirectId();
			if (did == null) {// 审批结束
				ly.setStatus(4);
			} else {
				if (did == user.getId()) {// 同一个人
					ly.setDirectCmt(yj);
					return spNext(ly, user, status, yj);
				} else if(did == ly.getBossId()){
					ly.setCurrentId(ly.getBossId());
					ly.setStatus(3);
				} else {
					ly.setCurrentId(did);
					ly.setStatus(status);
				}
			}
		} else if (status == 3) {
			Integer bid = ly.getBossId();
			if (bid == null) {// 审批结束
				ly.setStatus(4);
			} else {
				if (bid == user.getId()) {// 同一个人
					ly.setBossCmt(yj);
					// return spNext(ly, user, status, yj);
					ly.setStatus(4);
				} else {
					ly.setCurrentId(bid);
					ly.setStatus(status);
				}
			}
		}

		return ly;
	}

	private ZcPropWb spNext(ZcPropWb ly, User user, int status, String yj) {
		status++;
		if (status == 2) {
			Integer did = ly.getDirectId();
			if (did == null) {// 审批结束
				ly.setStatus(4);
			} else {
				if (did == user.getId()) {// 同一个人
					ly.setDirectCmt(yj);
					return spNext(ly, user, status, yj);
				} else {
					ly.setCurrentId(did);
					ly.setStatus(status);
				}
			}
		} else if (status == 3) {
			Integer bid = ly.getBossId();
			if (bid == null) {// 审批结束
				ly.setStatus(4);
			} else {
				if (bid == user.getId()) {// 同一个人
					ly.setBossCmt(yj);
					// return spNext(ly, user, status, yj);
					ly.setStatus(4);
				} else {
					ly.setCurrentId(bid);
					ly.setStatus(status);
				}
			}
		}

		return ly;
	}

	private ZcPropZy spNext(ZcPropZy ly, User user, int status, String yj) {
		status++;
		if (status == 2) {
			Integer did = ly.getMgr2Id();
			if (did == null) {// 审批结束
				ly.setStatus(5);
			} else {
				if (did == user.getId()) {// 同一个人
					ly.setMgr2Cmt(yj);
					return spNext(ly, user, status, yj);
				} else {
					ly.setCurrentId(did);
					ly.setStatus(status);
				}
			}
		} else if (status == 3) {
			Integer did = ly.getDirectId();
			if (did == null) {// 审批结束
				ly.setStatus(5);
			} else {
				if (did == user.getId()) {// 同一个人
					ly.setDirectCmt(yj);
					return spNext(ly, user, status, yj);
				} else {
					ly.setCurrentId(did);
					ly.setStatus(status);
				}
			}
		} else if (status == 4) {
			Integer bid = ly.getBossId();
			if (bid == null) {// 审批结束
				ly.setStatus(5);
			} else {
				if (bid == user.getId()) {// 同一个人
					ly.setBossCmt(yj);
					// return spNext(ly, user, status, yj);
					ly.setStatus(5);
				} else {
					ly.setCurrentId(bid);
					ly.setStatus(status);
				}
			}
		}

		return ly;
	}

	private String createMailTable(List<ZcProperty> props) {
		StringBuilder sb = new StringBuilder();

		sb.append("<table style='margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' ><tr>"
				+ "<th style='width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'>资产编码</th>"
				+ "<th style='width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'>资产名称</th>"
				+ "<th style='width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'>规格型号</th></tr>");
		for (ZcProperty vo : props) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getCode());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getSpec());
			sb.append("</td></tr>");
		}
		sb.append("</table>");

		return sb.toString();
	}

	private String createMailTableForLy(List<ZcProperty> props) {
		StringBuilder sb = new StringBuilder();

		sb.append("<table style='margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
				+ "<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>资产名称</th>"
				+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>备注信息</th></tr>");
		for (ZcProperty vo : props) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			if (StringUtils.isEmpty(vo.getBz())) {
				sb.append("无");
			} else {
				sb.append(vo.getBz());
			}
			sb.append("</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	private String createMailTableForCg(List<ZcProperty> props, boolean withMoney) {
		StringBuilder sb = new StringBuilder();

		if (withMoney) {// 显示多少钱
			double zj = 0.0;
			sb.append("<table style='margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >" + "<tr>"
					+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>资产名称</th>"
					+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>备注信息</th>"
					+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>预算资金</th>"
					+ "</tr>");
			for (ZcProperty vo : props) {
				sb.append("<tr><td style='text-align:center;padding:3px 7px; border:1px solid #888;'>");
				sb.append(vo.getName());
				sb.append("</td><td style='text-align:left;padding:3px 7px; border:1px solid #888;'>");
				if (StringUtils.isEmpty(vo.getBz())) {
					sb.append("无");
				} else {
					sb.append(vo.getBz());
				}
				sb.append("</td><td style='text-align:left;padding:3px 7px; border:1px solid #888;'>");
				sb.append(vo.getMoney()).append("元");
				sb.append("</td></tr>");
				zj += vo.getMoney();
			}

			sb.append("<tr><td style='text-align:center;padding:3px 7px; border:1px solid #888; color:red;'>");
			sb.append("总计金额");
			sb.append("</td><td style='text-align:left;padding:3px 7px; border:1px solid #888;'>");
			sb.append("</td><td style='text-align:left;padding:3px 7px; border:1px solid #888; color:red;'>");
			sb.append(zj).append("元");
			sb.append("</td></tr>");

			sb.append("</table>");
		} else {
			sb.append(
					"<table style='margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' ><tr><th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>资产名称</th><th style='width:200px;text-align:left; padding:3px 7px; border:1px solid #888;'>备注信息</th></tr>");
			for (ZcProperty vo : props) {
				sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;'>");
				sb.append(vo.getName());
				sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
				if (StringUtils.isEmpty(vo.getBz())) {
					sb.append("无");
				} else {
					sb.append(vo.getBz());
				}
				sb.append("</td></tr>");
			}
			sb.append("</table>");
		}

		return sb.toString();
	}

	private String createMailTableForLy(String[] names, String[] bzs) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table style='margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' ><tr>"
				+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>资产名称</th>"
				+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>备注信息</th></tr>");
		for (int i = 0; i < names.length; i++) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(names[i]);
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(bzs[i]);
			sb.append("</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	private String createMailTableForCg() {
		StringBuilder sb = new StringBuilder();

		sb.append(
				"<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;'>"
						+ "<tr>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申请时间</b></td>"
						+ "<td style='width:800px;text-align:center; padding:3px 7px; border:1px solid #888;' colspan=\"8\"></td>"
						+ "</tr>" + "<tr>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>资产名称</b></td>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>类别</b></td>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>规格型号</b></td>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>放置地点</b></td>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>单位</b></td>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>单价</b></td>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>数量</b></td>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>总价（元）</b></td>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>备注</b></td>"
						+ "</tr>");
		sb.append(
				"<tr>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b><font color='red'>总计金额</font></b></td>"
						+ "<td style='width:800px;text-align:center; padding:3px 7px; border:1px solid #888;' colspan=\"8\"></td>"
						+ "</tr>" + "<tr>"
						+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>采购事由</b></td>"
						+ "<td style='width:800px;text-align:center; padding:3px 7px; border:1px solid #888;' colspan=\"8\"></td>"
						+ "</tr>");
		sb.append("</table>");

		return sb.toString();
	}

	private String createMailTableWithPrice(List<ZcProperty> props) {
		StringBuilder sb = new StringBuilder();

		sb.append(
				"<table style='margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' ><tr><th style='width:110px;text-align:left; padding:3px 7px; border:1px solid #888;'>资产编码</th><th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>资产名称</th><th style='width:130px;text-align:left; padding:3px 7px; border:1px solid #888;'>规格型号</th><th style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>单价（元）</th></tr>");
		for (ZcProperty vo : props) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getCode());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getSpec());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
			sb.append(vo.getPrice());
			sb.append("</td></tr>");
		}
		sb.append("</table>");

		return sb.toString();
	}

	private void sendMailForLy(String to, String copyTo, String text) {
		// TODO send
		/*
		 * StringBuilder sb = new StringBuilder();
		 * 
		 * sb.append("<html><head>"); sb.append(
		 * "</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，"
		 * ).append(toName).append("。</p>"); sb.append(
		 * "<p style='padding:5px 0 0px 0px;'>"
		 * ).append(deptName).append(fromName).append(
		 * "向您提交了一个固定资产申领，请及时审批。详情如下："); sb.append("</p>"); sb.append(table);
		 * sb.append(
		 * "<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>"
		 * );
		 * 
		 * String text = sb.toString();
		 */
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产申领审批", text);

	}

	private void sendMailForCgComp(String toName, String to, String zcName, String zcBz) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append("您申请采购的固定资产（名称：").append(zcName).append("，备注信息：")
				.append(zcBz).append("）已经采购入库。");
		sb.append("</p>");
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, "固定资产采购入库通知", text);

	}

	private void sendMailForCg(String toName, String fromName, String to, String copyTo, String table,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append("</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'>"
				+ "<p style='padding:5px; padding-bottom:0;'>您好，").append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName)
				.append("向您提交了一个固定资产采购申请，请及时登陆系统审批。详情如下：");
		sb.append("</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产采购审批", text);

	}

	private void sendMailForCgDingjia(String toName, String fromName, String to, String copyTo, String table,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName)
				.append("向您提交了一个固定资产资产采购申请，请及时登陆系统审批。详情如下：");
		sb.append("</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产采购审批", text);

	}

	private void sendMailForZy(String toName, User zcUser, User zyUser, String to, String copyTo, String table,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(zcUser.getName())
				.append("向您提交了一个固定资产转移申请，将下列资产转移给").append(zyUser.getName()).append("。请及时审批");
		sb.append("</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产转移审批", text);

	}

	private void sendMailForWx(String toName, String fromName, String to, String copyTo, String table, int money,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName)
				.append("向您提交了一个固定资产维修申请，请及时审批。报修资产基本信息如下，预计费用").append(money).append("（元）。");
		sb.append("</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产维修审批", text);

	}

	private void sendMailForZx(String toName, String fromName, String to, String copyTo, String table, String reason,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName)
				.append("向您提交了一个固定资产注销申请，请及时审批。注销资产基本信息如下，注销原因：").append(reason);
		sb.append("</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产注销审批", text);

	}


	// 成功发放，通知所有上级

	// 成功归还
	private void sendGhMailToUser(String toName, String fromName, String to, String copyTo, String table) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>您成功向资产管理员").append(fromName).append("归还了下列资产，请核实，如有疑问，请联系其本人。");
		sb.append("</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产归还通知", text);

	}

	// 成功归还发送给所有的上级
	private void sendGhMailToUserSups(List<User> sups, String ghName, String zcName, String table) {
		for (User u : sups) {

			StringBuilder sb = new StringBuilder();

			sb.append("<html><head>");
			sb.append(
					"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，")
					.append(u.getName()).append("。</p>");
			sb.append("<p style='padding:5px 0 0px 0px;'>").append(ghName).append("成功向资产管理员").append(zcName)
					.append("归还了下列资产。<span style='color:red'>（仅作为通知，不需要您处理）</span>");
			sb.append("</p>");
			sb.append(table);
			sb.append(
					"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

			String text = sb.toString();

			String to = u.getEmail();
			mailUtil.sendEMail(to, null, Consts.defaultFrom, "固定资产归还通知", text);
		}

	}

	// 取消发放
	private void sendCFFMailToUser(String toName, String fromName, String to, String copyTo, String table) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append("您申请的资产已取消，详情如下。");
		sb.append("</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产取消发放通知", text);
	}

	// 取消归还
	private void sendCGhailToUser(String toName, String fromName, String to, String copyTo, String table) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>资产管理员").append(fromName).append("取消了您的下列资产归还申请，如有疑问，请联系其本人。");
		sb.append("</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产归还取消通知", text);

	}

	private void sendMailForPropDiliver(String to, String copyTo, String text) {
		// TODO send
		/*
		 * StringBuilder sb = new StringBuilder();
		 * 
		 * sb.append("<html><head>"); sb.append(
		 * "</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，"
		 * ).append(toName).append("。</p>"); sb.append(
		 * "<p style='padding:5px 0 0px 0px;'>"
		 * ).append(deptName).append(fromName).append(
		 * "的一个固定资产申领通过了审核，请及时处理。详情如下："); sb.append("</p>"); sb.append(table);
		 * sb.append(
		 * "<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>"
		 * );
		 * 
		 * String text = sb.toString();
		 */
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产领用处理", text);

	}

	private void sendMailForPropRk(String toName, String fromName, String to, String copyTo, String table,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName)
				.append("的一个固定资产采购通过了审核，请及时处理。详情如下：");
		sb.append("</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产采购审批结果通知", text);

	}

	private void sendMailForPropGh(String toName, String fromName, String to, String copyTo, String table,
			String deptName) {
		StringBuilder sb = new StringBuilder();

		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:14px; font-family:\"微软雅黑\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("。</p>");
		sb.append("<p style='padding:5px 0 0px 0px;'>").append(deptName).append(fromName)
				.append("像您提交了一个固定资产归还申请，请及时处理。详情如下：");
		sb.append("</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:13px; font-weight:bold;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");

		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产归还处理", text);

	}

	private String buildApplyPropTable(String applyTime, String[] names, String[] bzs, String reason) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;'>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申请时间</b></td>"
						+ "<td style='width:600px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
						+ applyTime + "</td>" + "</tr>" + "<tr>"
						+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>资产名称</b></td>"
						+ "<td style='background-color:#CECEFF;width:600px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>备注信息（如规格型号、配置等，没有请写“无”）</b></td>"
						+ "</tr>");
		for (int i = 0; i < names.length; i++) {
			sb.append("<tr>" + "<td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ names[i] + "</td>"
					+ "<td style='width:600px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + bzs[i]
					+ "</td>" + "</tr>");
		}
		sb.append(
				"<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申领事由</b></td>"
						+ "<td style='width:600px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + reason
						+ "</td>" + "</tr>" + "</table>");
		return sb.toString();
	}

	private String buildApplyPropTable(String applyTime, List<ZcProperty> zcs, String reason) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;'>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申请时间</b></td>"
						+ "<td style='width:600px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
						+ applyTime + "</td>" + "</tr>" + "<tr>"
						+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>资产名称</b></td>"
						+ "<td style='background-color:#CECEFF;width:600px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>备注信息（如规格型号、配置等，没有请写“无”）</b></td>"
						+ "</tr>");
		for (ZcProperty zc : zcs) {
			sb.append("<tr>" + "<td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zc.getName() + "</td>"
					+ "<td style='width:600px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + zc.getBz()
					+ "</td>" + "</tr>");
		}
		sb.append(
				"<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申领事由</b></td>"
						+ "<td style='width:600px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + reason
						+ "</td>" + "</tr>" + "</table>");
		return sb.toString();
	}

	private String createMailTableForCg(ZcPropCg sq, String[] names, String[] bzs, char[] units, double[] prices,
			int[] nums, double[] moneys, String[] brands, int[] types, int[] places, List<ZcPlace> selectPlaces,
			List<ZcType> selecTypes) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;'>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申请时间</b></td>"
						+ "<td style='width:800px;text-align:center; padding:3px 7px; border:1px solid #888;' colspan=\"8\">"
						+ sq.getCreateTime() + "</td>" + "</tr>" + "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>资产名称</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>类别</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>规格型号</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>放置地点</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>单位</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>单价</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>数量</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>总价（元）</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>备注</b></td>"
						+ "</tr>");
		int sum = 0;
		for (int i = 0; i < names.length; i++) {
			sum += moneys[i];
			sb.append("<tr>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ names[i] + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>");
			for (ZcType zt : selecTypes) {
				if (zt.getId() == types[i]) {
					sb.append(zt.getName());
					break;
				}
			}
			sb.append("</td>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ brands[i] + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>");
			for (ZcPlace zp : selectPlaces) {
				if (zp.getId() == places[i]) {
					sb.append(zp.getName());
					break;
				}
			}
			sb.append("</td>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ units[i] + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + prices[i]
					+ "</td>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ nums[i] + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + moneys[i]
					+ "</td>" + "<td style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>"
					+ bzs[i] + "</td>" + "</tr>");
		}

		sb.append(
				"<tr>" + "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b><font color='red'>总计金额</font></b></td>"
						+ "<td style='width:800px;text-align:center; padding:3px 7px; border:1px solid #888;' colspan=\"8\">"
						+ sum + "元</td>" + "</tr>" + "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>采购事由</b></td>"
						+ "<td style='width:800px;text-align:left; padding:3px 7px; border:1px solid #888;' colspan=\"8\">"
						+ sq.getContent() + "</td>" + "</tr>");
		sb.append("</table>");

		return sb.toString();
	}

	private String createMailTableForCg(ZcPropCg cg, List<ZcProperty> sqProps, List<ZcPlace> selectPlaces,
			List<ZcType> selecTypes) {
		StringBuilder sb = new StringBuilder();

		sb.append(
				"<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;'>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申请时间</b></td>"
						+ "<td style='width:800px;text-align:center; padding:3px 7px; border:1px solid #888;' colspan=\"8\">"
						+ cg.getCreateTime() + "</td>" + "</tr>" + "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>资产名称</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>类别</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>规格型号</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>放置地点</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>单位</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>单价</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>数量</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>总价（元）</b></td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>备注</b></td>"
						+ "</tr>");
		int sum = 0;
		for (ZcProperty zp : sqProps) {
			sum += zp.getMoney();
			sb.append("<tr>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getName() + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>");
			for (ZcType zt : selecTypes) {
				if (zt.getId() == Integer.parseInt(zp.getType())) {
					sb.append(zt.getName());
					break;
				}
			}
			sb.append("</td>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getBrand() + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>");
			for (ZcPlace zpl : selectPlaces) {
				if (zpl.getId() == zp.getPlace()) {
					sb.append(zp.getName());
					break;
				}
			}
			sb.append("</td>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getUnit() + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getPrice() + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getNum() + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getMoney() + "</td>"
					+ "<td style='width:100px;text-left; padding:3px 7px; border:1px solid #888;'>" + zp.getBz()
					+ "</td>" + "</tr>");
		}

		sb.append(
				"<tr>" + "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b><font color='red'>总计金额</font></b></td>"
						+ "<td style='width:800px;text-align:center; padding:3px 7px; border:1px solid #888;' colspan=\"8\">"
						+ sum + "元</td>" + "</tr>" + "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>采购事由</b></td>"
						+ "<td style='width:800px;text-left; padding:3px 7px; border:1px solid #888;' colspan=\"8\">"
						+ cg.getContent() + "</td>" + "</tr>");
		sb.append("</table>");

		return sb.toString();
	}
}
