/**   
* @Title: ProductController.java 
* @Package com.hj.oa.controller 
* @Description: 产品发货申请类 
* @author mlsong   
* @date 2018年6月2日 下午2:55:48 
* @version V1.0   
*/
package com.hj.oa.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hj.commons.ApiResult;
import com.hj.commons.ApprovalType;
import com.hj.commons.Const;
import com.hj.commons.DbStatus;
import com.hj.commons.OutstockStatus;
import com.hj.commons.ProductOutstockDetailStatus;
import com.hj.commons.ProductOutstockReason;
import com.hj.commons.ProductOutstockRecordStatus;
import com.hj.commons.ProductStatus;
import com.hj.commons.ResultCode;
import com.hj.oa.Consts;
import com.hj.oa.bean.ApprovalList;
import com.hj.oa.bean.ApprovalRecord;
import com.hj.oa.bean.Company;
import com.hj.oa.bean.Logistics;
import com.hj.oa.bean.OutstockDetail;
import com.hj.oa.bean.OutstockRecord;
import com.hj.oa.bean.Product;
import com.hj.oa.bean.ProductLogistics;
import com.hj.oa.bean.ProductOutstockDetail;
import com.hj.oa.bean.ProductOutstockRecord;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.service.ApprovalService;
import com.hj.oa.service.UserService;
import com.hj.oa.service.impl.ProductServiceImpl;
import com.hj.oa.service.inter.ProductService;
import com.hj.util.FileUtil;
import com.hj.util.UploadUtilV2;

/**
 * @ClassName: ProductController
 * @Description: 产品控制器
 * @author mlsong
 * @date 2018年6月2日 下午2:55:48
 */
@Controller
@RequestMapping("oa")
public class ProductController extends BaseController {
	// 上传文件跟路径
	private final static String UPLOAD_PATH = "D:/upload/product";
	// 上传合同相对路径
	private final static String CONTRACT_DIR = "/contract";
	// 上传订单图片相路径
	private final static String OUTSTOCK_ORDER_DIR = "/order";
	// 上传发货物流单图片相对路径
	private final static String SHIPMENT_SEND_DIR = "/shipment/send";
	// 上传回执物流单图片相对路径
	private final static String SHIPMENT_RECEIVE_DIR = "/shipment/receive";

	public final static Integer PAGE_SIZE = 10;

	/**
	 * 产品业务处理器
	 */
	@Autowired
	@Qualifier("productService")
	private ProductService productService;

	/**
	 * 审批业务处理器
	 */
	@Autowired
	@Qualifier("approvalService")
	private ApprovalService approvalService;

	/**
	 * 用户业务处理器
	 */
	@Autowired
	private UserService userService;

	@RequestMapping("product")
	public String productPage(HttpSession session) {
		List<Role> roles = getLoginUserRole(session);
		for (Role role : roles) {
			if ("产品发货人员".equals(role.getName())) {
				return "redirect:/web/oa/product/apply/add";
			} else if ("生产运营经理".equals(role.getName())) {

				return "redirect:/web/oa/product/list";
			} else if ("仓库管理员".equals(role.getName())) {

				return "redirect:/web/oa/product/outstock/list";
			}
		}
		return null;
	}

	@Autowired
	ProductServiceImpl plService;

	@RequestMapping("product/updateLog")
	public String updateLog(Integer id, ProductLogistics pl, String submitCode, HttpSession session, Model model) {
		try {
			if (pl.getCompany() != null) {
				String sc = (String) session.getAttribute(Consts.submitCode);
				if (!sc.equals(submitCode)) {// 重复提交
					throw new Exception("请勿重复提交");
				}
				session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
				this.plService.update(pl);
				return "redirect:/web/oa/product/logistics?msg=1";
			}
			ProductLogistics pl1 = this.plService.selectLogById(id);
			model.addAttribute("seal", pl1);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/product/updateLog";
		}
		return "oa/product/updateLog";
	}

	@RequestMapping("product/logistics")
	public String productPage2(Model model) {
		try {
			List<ProductLogistics> list = this.plService.findAll();
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
		}
		return "oa/product/logistics";
	}
	
	@RequestMapping("product/deleteLog")
	public String deleteLog(Model model,Integer id) {
		try {
			ProductLogistics pl = new ProductLogistics();
			pl.setId(id);
			pl.setStatus(DbStatus.FREEZE.getType());
			this.plService.update(pl);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "oa/product/logistics";
		}
		return "redirect:/web/oa/product/logistics?msg=1";
	}

	@RequestMapping("product/addCompany")
	public String productPage3(ProductLogistics pl, String submitCode, HttpSession session, Model model) {
		if (pl.getCompany() != null) {
			String sc = (String) session.getAttribute(Consts.submitCode);
			try {
				if (!sc.equals(submitCode)) {// 重复提交
					throw new Exception("请勿重复提交");
				}
				session.setAttribute(Consts.submitCode, UUID.randomUUID().toString());
				this.plService.insert(pl);
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", e.getMessage());
				return "oa/product/logistics";
			}
			return "redirect:/web/oa/product/logistics?msg=1";
		}
		return "oa/product/addCompany";
	}

	/**
	 * @Description: 产品发货申请页面
	 * @return String
	 * @author mlsong
	 * @date 2018年6月2日 下午3:22:27
	 */
	@RequestMapping(value = "product/apply/add", method = RequestMethod.GET)
	public String outstockPage(Model model, HttpSession session) {
		try {
			// 添加所有发货原因
			model.addAttribute("reasons", ProductOutstockReason.convertToList());
			if (!StringUtils.isEmpty((String) session.getAttribute("msg"))) {
				model.addAttribute("msg", session.getAttribute("msg"));
				session.removeAttribute("msg");
			}
			// 添加所有产品信息（JSON格式数据）
			JSONObject obj = new JSONObject(productService.getProducts(ProductStatus.USABLE));
			model.addAttribute("products", obj);
			// 查询最新订单号
			model.addAttribute("oddNumber", productService.buildOrder("FHSQ"));

			// 收货单位信息
			model.addAttribute("companys", productService.getCompanys(1));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统异常:" + e.getMessage());
		}

		return "oa/product/apply/add";
	}

	/**
	 * @Description: 提交发货申请
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 上午8:17:18
	 */
	@RequestMapping(value = "product/apply/add", method = RequestMethod.POST)
	public String addOutstockApply(HttpServletRequest request, User user, String oddNumber, String createTime,
			String cancelTime, String reason, String receiverCompany, String receiver, String addr, String tel,
			int[] ids, String[] remarks, int[] nums, String[] names, String[] units, String[] models,
			HttpSession session) {
		try {
			// 设置产品发货申请记录信息
			ProductOutstockRecord record = new ProductOutstockRecord();
			record.setReceiverCompany(receiverCompany);
			record.setReceiver(receiver);
			record.setAddr(addr);
			record.setTel(tel);
			record.setOddNumber(oddNumber);
			record.setProposer(user.getName());
			record.setProposerId(user.getId());
			record.setDeptId(user.getDeptId());
			record.setApprovalType(ApprovalType.PRODUCT_OUTSTOCK.getType());
			record.setReason(reason);
			record.setState(ProductOutstockRecordStatus.READY_FOR_APPROVAL.getCode());

			// 设置申请单创建时间和超时未审批取消时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			try {
				record.setCreateTime(sdf.parse(createTime));
				// record.setCancelTime(sdf.parse(cancelTime));
			} catch (ParseException e1) {
				logger.error(e1.getMessage(), e1);
				session.setAttribute("msg", "解析时间异常");
				return "redirect:/web/oa/product/apply/add";
			}

			// 设置审批角色id
			ApprovalList al = approvalService.getApprovalListByType(ApprovalType.PRODUCT_OUTSTOCK);
			if (al == null) {
				logger.error("数据库中未设置对应的审批流程!");
				session.setAttribute("msg", "数据库中未设置对应的审批流程,请先联系管理员设置！");
				return "redirect:/web/oa/product/apply/add";
			}
			record.setApprovalId(al.getStartRoleId());

			// 设置订单详细发货产品
			List<ProductOutstockDetail> products = new ArrayList<ProductOutstockDetail>();
			for (int i = 0; i < ids.length; i++) {
				ProductOutstockDetail prod = new ProductOutstockDetail();
				prod.setCount(nums[i]);
				prod.setProductId(ids[i]);
				prod.setProductModel(models[i]);
				prod.setProductName(names[i]);
				prod.setUnit(units[i]);
				// 待审批状态
				prod.setState(ProductOutstockDetailStatus.READY_FOR_APPROVAL.getCode());
				prod.setRemark(remarks[i]);
				products.add(prod);
			}

			// 保存申请单信息
			productService.saveOutstockRecordAndDetails(record, products);
			session.setAttribute("msg", "操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.setAttribute("msg", "系统异常:" + e.getMessage());
		}
		return "redirect:/web/oa/product/apply/add";

	}

	/**
	 * @Description: 文件预览
	 * @param response
	 * @param path
	 *            预览文件的本地地址
	 * @return
	 * @throws Exception
	 *             String
	 * @author mlsong
	 * @date 2018年6月12日 上午11:01:25
	 */
	@RequestMapping("preview")
	public String previewResult(HttpServletResponse response, HttpServletRequest req) throws Exception {
		String path = req.getParameter("path");
		path = path.replace("+", "%2B");
		path = URLDecoder.decode(path, "UTF-8");
		File file = new File(path);
		// 读出文件到response
		// 这里是先需要把要把文件内容先读到缓冲区
		// 再把缓冲区的内容写到response的输出流供用户下载
		FileInputStream fileInputStream = new FileInputStream(file);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		byte[] b = new byte[bufferedInputStream.available()];
		bufferedInputStream.read(b);
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(b);
		// 人走带门
		bufferedInputStream.close();
		outputStream.flush();
		outputStream.close();
		return null;
	}

	/**
	 * @Description: 待审批列表页
	 * @param model
	 * @param session
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 上午11:47:51
	 */
	@RequestMapping(value = "product/outstock/approval/list", method = RequestMethod.GET)
	public String approvalListPage(Model model, HttpSession session) {
		try {
			if (!StringUtils.isEmpty((String) session.getAttribute("msg"))) {
				model.addAttribute("msg", session.getAttribute("msg"));
				session.removeAttribute("msg");
			}
			// 获取当前用户角色
			List<Role> roles = getLoginUserRole(session);
			// 查询待审批记录
			List<ProductOutstockRecord> records = productService.getWillApprovalRecordsByRoles(roles);
			model.addAttribute("list", records);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统错误：" + e.getMessage());
		}
		return "oa/product/approval/approvalList";
	}

	/**
	 * @Description: 产品发货申请审批详细页
	 * @param recordId
	 *            产品发货申请id
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 上午11:02:28
	 */
	@RequestMapping(value = "product/outstock/approval", method = RequestMethod.GET)
	public String approvalPage(int recordId, Model model) {
		try {
			// 根据申请单id查询申请单
			ProductOutstockRecord record = productService.getProductOutstockRecordById(recordId);
			model.addAttribute("record", record);

			// 根据申请单查询申请单详细发货产品
			List<ProductOutstockDetail> details = productService.getProductOutstockDetailsById(recordId);
			model.addAttribute("details", details);

			String jsonArrStr = JSONArray.toJSONString(details);
			model.addAttribute("jsonDetails", jsonArrStr);
			// 查询该订单已审批的记录
			List<ApprovalRecord> approvals = approvalService.getApprovalRecordsByApplyId(recordId,
					ApprovalType.PRODUCT_OUTSTOCK);
			model.addAttribute("approvals", approvals);

			// TODO 已发货记录
			List<OutstockRecord> outRecords = productService.getOutstockRecordsByRecordId(recordId);
			if (outRecords != null && !outRecords.isEmpty()) {
				model.addAttribute("outRecords", outRecords);
			}

			// 申请人部门信息
			model.addAttribute("dept", userService.findDeptById(record.getDeptId()).getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统错误：" + e.getMessage());
		}

		return "oa/product/approval/approval";
	}

	/**
	 * @Description: 发货申请审批流程处理
	 * @param recordId
	 *            申请id
	 * @param comment
	 *            审批意见
	 * @param state
	 *            审批结果，1：通过；-1：未通过
	 * @param createTime
	 *            创建时间
	 * @param session
	 * @param bt
	 *            发货时间（生产运营经理审批）
	 * @param count
	 *            发货数量（生产运营经理审批）
	 * @param jsonArr
	 *            分批发货详细数据（生产运营经理审批）
	 * @param shipments
	 *            发货方式，0：一次性发货；1：分批发货
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 上午11:24:53
	 */
	@RequestMapping(value = "product/outstock/approval", method = RequestMethod.POST)
	public String approvalOutstockRecord(int recordId, String comment, Integer state, String createTime,
			HttpSession session, Integer[] productIds, Integer[] counts, String[] units, String beginDate,
			String[] wxbhs, @RequestParam MultipartFile[] bqzs, HttpServletRequest req, String[] fhDates) {
		try {
			User user = getLoginUser(session);
			// 发货申请记录
			ProductOutstockRecord record = productService.getProductOutstockRecordById(recordId);
			ApprovalRecord approRecord = null;
			// 设置审批信息
			if (state != null) {
				approRecord = new ApprovalRecord();
				approRecord.setApplyId(recordId);
				approRecord.setComment(comment);
				approRecord.setEmpId(user.getId());
				approRecord.setRoleId(record.getApprovalId());
				approRecord.setState(state);
				approRecord.setType(ApprovalType.PRODUCT_OUTSTOCK.getType());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
				try {
					approRecord.setUpdateTime(sdf.parse(createTime));
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
					session.setAttribute("msg", "日期格式解析错误");
				}
			}
			// 审批流程处理
			List<String> urls = new ArrayList<String>();
			for (int i = 1; i < bqzs.length; i++) {
				String url = UploadUtilV2.uploadFile2(bqzs[i], req, 2, UploadUtilV2.IMGFILE);
				urls.add(url);
			}
			if(state != null && state == -1) {
				urls = null;
				productService.handle(approRecord, record, productIds, counts, units, "1", wxbhs, urls, fhDates);
			} else {
				productService.handle(approRecord, record, productIds, counts, units, fhDates[0], wxbhs, urls, fhDates);
			}
			session.setAttribute("msg", "操作成功，请继续审批。或进行其他操作");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			session.setAttribute("msg", "系统错误：" + e.getMessage());
		}
		return "redirect:/web/oa/product/outstock/approval/list";
	}

	/**
	 * @Description: 待出库订单列表页
	 * @param model
	 * @param session
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 下午2:54:05
	 */
	@RequestMapping("product/outstock/list")
	public String outstockListPage(Model model, HttpSession session) {
		try {
			// 页面提示
			if (!StringUtils.isEmpty((String) session.getAttribute("msg"))) {
				model.addAttribute("msg", session.getAttribute("msg"));
				session.removeAttribute("msg");
			}
			// 查询所有待出库订单
			/*
			 * List<ProductOutstockRecord> records =
			 * productService.getProductOutstockRecords(
			 * ProductOutstockRecordStatus.READY_FOR_OUTSTOCK,
			 * ProductOutstockRecordStatus.ALREADY_OUTSTOCKED);
			 * model.addAttribute("list", records);
			 */

			// TODO 查询OutstockRecord
			List<OutstockRecord> outRecords = productService.getOutstockRecordsByStatus(
					OutstockStatus.READY_FOR_OUTSTOCK, OutstockStatus.ALREADY_OUTSTOCKED, OutstockStatus.WAIT_HUIDAN);
			model.addAttribute("list", outRecords);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统错误：" + e.getMessage());
		}
		return "oa/product/outstock/outstockList";
	}

	/**
	 * @Description: 详细出库订单页
	 * @param recordId
	 *            产品发货申请ID
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 下午3:00:55
	 */
	@RequestMapping("product/outstock/{recordId}")
	public String outstockDetailPage(@PathVariable int recordId, Model model, HttpSession session) {
		try {
			OutstockRecord outRecord = productService.getOutstockRecordById(recordId);
			model.addAttribute("outRecord", outRecord);
			// 申请单记录
			ProductOutstockRecord record = productService.getProductOutstockRecordById(outRecord.getRecordId());
			model.addAttribute("record", record);

			// 申请发货的所有产品
			List<ProductOutstockDetail> details = productService.getProductOutstockDetailsById(outRecord.getRecordId());
			model.addAttribute("details", details);

			// 申请单的所有已审批记录
			List<ApprovalRecord> approvals = approvalService.getApprovalRecordsByApplyId(outRecord.getRecordId(),
					ApprovalType.PRODUCT_OUTSTOCK);
			model.addAttribute("approvals", approvals);

			// 申请人部门信息
			model.addAttribute("dept", userService.findDeptById(record.getDeptId()).getName());

			// 批次发货记录
			List<OutstockRecord> outRecords = productService.getOutstockRecordsByRecordId(outRecord.getRecordId());
			model.addAttribute("outRecords", outRecords);

			// 物流收货信息
			String shTime = this.productService.selectShTime(outRecord.getId());
			model.addAttribute("shTime", shTime);
			
			List<Logistics> logistics = this.productService.findAllLogistics();
			model.addAttribute("logistics", logistics);
			return "oa/product/outstock/outstockManyDetail";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.setAttribute("msg", "系统错误：" + e.getMessage());
		}
		// 出现错误，重定向到列表页
		return "redirect:/web/oa/product/outstock/list";
	}

	/**
	 * @Description: 确认发货
	 * @param request
	 * @param oddNumber
	 *            申请单号
	 * @param id
	 *            申请单id
	 * @param recordId
	 *            批次发货记录id
	 * @param type
	 *            发货形式：0 一次性发货，1 批次发货
	 * @param shipmentOrderNum
	 *            物流单号
	 * @param outstockOrderNum
	 *            出库单号
	 * @param shipmentCompany
	 *            物流公司
	 * @param beginDate
	 *            发货日期
	 * @param beginHour
	 *            发货时间：小时
	 * @param beginMin
	 *            发货时间：分钟
	 * @param model
	 * @param session
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 下午3:16:40
	 */
	@RequestMapping("product/confirm/outstock")
	public String confirmOutstock(HttpServletRequest request, String oddNumber, int id, int recordId, String type,
			String shipmentOrderNum, String outstockOrderNum, String shipmentCompany, String beginDate,
			String beginHour, String beginMin, Model model, HttpSession session) {
		try {
			// 发货记录
			OutstockRecord record = new OutstockRecord();
			record.setId(recordId);
			record.setRecordId(id);
			record.setShipmentCompany(shipmentCompany);
			record.setShipmentOrderNum(shipmentOrderNum);
			record.setOutstockOrderNum(outstockOrderNum);
			record.setState(OutstockStatus.ALREADY_OUTSTOCKED.getCode());
			if ("0".equals(type)) {
				record.setOutstockType(false);
			} else {
				record.setOutstockType(true);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

			try {
				record.setOutstockTime(sdf.parse(beginDate + " " + beginHour + beginMin));
			} catch (ParseException e1) {
				logger.error(e1.getMessage(), e1);
				session.setAttribute("msg", "发货失败，日期格式转化错误：" + e1.getMessage());
				return "redirect:/web/oa/product/outstock/list";
			}

			// 接收上传的出库单照片和发货单照片
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				CommonsMultipartFile mutiFile1 = (CommonsMultipartFile) multipartRequest.getFile("shipmentOrderPic");
				CommonsMultipartFile mutiFile2 = (CommonsMultipartFile) multipartRequest.getFile("outstockOrderPic");
				FileItem fileItem1 = mutiFile1.getFileItem();
				FileItem fileItem2 = mutiFile2.getFileItem();

				String fileName1 = fileItem1.getName();
				String extName1 = FileUtil.getFileExtName(fileName1);

				String fileName2 = fileItem2.getName();
				String extName2 = FileUtil.getFileExtName(fileName2);
				if (fileName1.isEmpty()) {
					if (!".jpg".equalsIgnoreCase(extName2)) {
						session.setAttribute("msg", "操作失败，您上传的文件格式不符合要求。");
						return "redirect:/web/oa/product/outstock/list";
					}
				} else {
					if (!".jpg".equalsIgnoreCase(extName1) || !".jpg".equalsIgnoreCase(extName2)) {
						session.setAttribute("msg", "操作失败，您上传的文件格式不符合要求。");
						return "redirect:/web/oa/product/outstock/list";
					}
				}

				if (!fileName1.isEmpty()) {
					String filePath1 = UPLOAD_PATH + "/" + recordId + SHIPMENT_SEND_DIR;
					File path1 = new File(filePath1);
					if (!path1.exists()) {
						path1.mkdirs();
					}
					File file1 = new File(filePath1 + "/" + fileName1);
					fileItem1.write(file1);
					record.setShipmentOrderPic(filePath1 + "/" + fileName1);
				}
				String filePath2 = UPLOAD_PATH + "/" + recordId + OUTSTOCK_ORDER_DIR;
				File path2 = new File(filePath2);
				if (!path2.exists()) {
					path2.mkdirs();
				}
				File file2 = new File(filePath2 + "/" + fileName2);
				fileItem2.write(file2);
				record.setOutstockOrderPic(filePath2 + "/" + fileName2);
			} catch (Exception e) {
				session.setAttribute("msg", "上传合同文件失败");
				return "redirect:/web/oa/product/outstock/list";
			}

			session.setAttribute("msg", "操作成功");
			// 更新发货记录
			productService.handleOutstock(record);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.setAttribute("msg", "系统错误：" + e.getMessage());
		}

		return "redirect:/web/oa/product/outstock/list";
	}

	/**
	 * @Description: 确认物流回执
	 * @param request
	 * @param id
	 *            申请id
	 * @param recordId
	 *            发货单id
	 * @param type
	 *            发货形式
	 * @param shipmentReceiveNum
	 *            回执单号
	 * @param confirmTime
	 *            确认时间
	 * @param comment
	 *            批注
	 * @param model
	 * @param session
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 下午3:31:16
	 */
	@RequestMapping("product/confirm/receive")
	public String confirmReceive(HttpServletRequest request, int id, int recordId, String type,
			String shipmentReceiveNum, String confirmTime, String comment, Model model, HttpSession session) {
		try {
			OutstockRecord record = new OutstockRecord();
			record.setId(recordId);
			record.setRecordId(id);
			record.setComment(comment);
			record.setShipmentReceiveNum(shipmentReceiveNum);
			record.setState(OutstockStatus.ALREADY_RECIVE_RECEIPT.getCode());
			if ("0".equals(type)) {
				record.setOutstockType(false);
			} else {
				record.setOutstockType(true);
			}

			// 设置确认回执时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			try {
				record.setReceiveTime(sdf.parse(confirmTime));
			} catch (ParseException e1) {
				logger.error(e1.getMessage(), e1);
				session.setAttribute("msg", "确认物流回单失败，日期格式转换错误：" + e1.getMessage());
				return "redirect:/web/oa/product/outstock/list";
			}

			// 接收回执单
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				CommonsMultipartFile mutiFile = (CommonsMultipartFile) multipartRequest.getFile("shipmentReceivePic");
				FileItem fileItem = mutiFile.getFileItem();

				String fileName = fileItem.getName();
				String extName = FileUtil.getFileExtName(fileName);

				if (!".jpg".equalsIgnoreCase(extName)) {
					logger.error("文件格式错误！");
					session.setAttribute("msg", "操作失败，您上传的文件格式不符合要求。");
					return "redirect:/web/oa/product/outstock/list";
				}

				String filePath = UPLOAD_PATH + "/" + recordId + SHIPMENT_RECEIVE_DIR;
				File path = new File(filePath);
				if (!path.exists()) {
					path.mkdirs();
				}
				File file = new File(filePath + "/" + fileName);
				fileItem.write(file);
				record.setShipmentReceivePic(filePath + "/" + fileName);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				session.setAttribute("msg", "上传合同文件失败,错误原因：" + e.getMessage());
				return "redirect:/web/oa/product/outstock/list";
			}

			session.setAttribute("msg", "操作成功");
			// 处理物流回执
			productService.handleReceive(record);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.setAttribute("msg", "系统错误：" + e.getMessage());
		}

		return "redirect:/web/oa/product/outstock/list";
	}

	@RequestMapping("product/gaizhuangtai")
	public String gaizhuangtai(Integer recordId, String beginDate, HttpSession session,String beginHour,String beginMin) {
		String date = beginDate + " " + beginHour + beginMin;
		this.productService.updateProductRecordByid(recordId, date);
		session.setAttribute("msg", "操作成功");
		return "redirect:/web/oa/product/outstock/list";
	}

	/**
	 * @Description: 申请/审批/发货记录页
	 * @param recordId
	 *            申请单id
	 * @param type
	 *            记录类型：apply，申请记录；approval，审批记录；outstock，出库记录
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 下午3:44:01
	 */
	@RequestMapping("product/{type}/records/{recordId}")
	public String approvalRecord(@PathVariable int recordId, @PathVariable String type, Model model) {
		try {
			ProductOutstockRecord record = null;
			if (type.equals("outstock")) {
				OutstockRecord outRec = productService.getOutstockRecordById(recordId);
				model.addAttribute("outRec", outRec);
				record = productService.getProductOutstockRecordById(outRec.getRecordId());
				recordId = record.getId();
			} else {
				record = productService.getProductOutstockRecordById(recordId);
			}

			// 申请记录
			model.addAttribute("record", record);

			// 申请发货详细产品
			List<ProductOutstockDetail> details = productService.getProductOutstockDetailsById(recordId);
			model.addAttribute("details", details);

			// 审批记录
			List<ApprovalRecord> approvals = approvalService.getApprovalRecordsByApplyId(recordId,
					ApprovalType.PRODUCT_OUTSTOCK);
			model.addAttribute("approvals", approvals);

			// 申请人部门信息
			model.addAttribute("dept", userService.findDeptById(record.getDeptId()).getName());

			// TODO 已发货记录
			List<OutstockRecord> outRecords = productService.getOutstockRecordsByRecordId(recordId);
			if (outRecords != null && !outRecords.isEmpty()) {
				model.addAttribute("outRecords", outRecords);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统错误：" + e.getMessage());
		}

		// 根据type类型判断，转发到不同的记录页面
		if ("approval".equals(type)) {
			return "oa/product/approval/approvalRecordDetail";
		} else if ("apply".equals(type)) {
			return "oa/product/apply/applyRecordDetail";
		} else if ("outstock".equals(type)) {
			return "oa/product/outstock/outstockRecordDetail";
		} else {
			throw new RuntimeException("无此类型错误！");
		}
	}

	/**
	 * @Description: 产品发货申请记录列表页
	 * @param model
	 * @param session
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 下午3:52:41
	 */
	@RequestMapping("product/outstock/apply/records")
	public String applyRecords(Model model, HttpSession session) {
		try {
			// 查询当前登录用户的所有发货申请记录
			List<ProductOutstockRecord> records = productService
					.getProductOutstockRecordByUserId(getLoginUser(session).getId());
			model.addAttribute("records", records);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统错误：" + e.getMessage());
		}
		return "oa/product/apply/applyList";
	}

	/**
	 * @Description: 产品发货申请审批记录列表页
	 * @param model
	 * @param session
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 下午3:58:18
	 */
	@RequestMapping("product/outstock/approval/records")
	public String approvalRecords(Model model, HttpSession session) {
		try {
			// 查询当前用户的审批记录
			List<ApprovalRecord> records = approvalService.getApprovalRecordsByUserId(getLoginUser(session).getId(),
					ApprovalType.PRODUCT_OUTSTOCK);
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			// 返回需要的字段在列表页显示
			for (ApprovalRecord rec : records) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 查询申请人，申请订单信息
				ProductOutstockRecord outRecord = productService.getProductOutstockRecordById(rec.getApplyId());

				User proposer = userService.findById(outRecord.getProposerId());
				map.put("oddNumber", outRecord.getOddNumber());
				map.put("proposer", proposer.getName());
				map.put("createTime", outRecord.getCreateTime());
				map.put("approvalTime", rec.getUpdateTime());
				map.put("state", outRecord.getState());
				map.put("id", outRecord.getId());
				result.add(map);
			}
			model.addAttribute("records", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统错误：" + e.getMessage());
		}

		return "oa/product/approval/approvalRecordList";
	}

	/**
	 * @Description: 产品出库记录列表页
	 * @param model
	 * @param session
	 * @return String
	 * @author mlsong
	 * @date 2018年6月12日 下午4:10:02
	 */
	@RequestMapping("product/outstock/records")
	public String outstockRecords(Model model, HttpSession session) {
		try {
			// 查询所有已完成申请订单
			List<OutstockRecord> records = productService
					.getOutstockRecordsByStatus(OutstockStatus.ALREADY_RECIVE_RECEIPT);
			model.addAttribute("records", records);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统错误：" + e.getMessage());
		}
		return "oa/product/outstock/outstockRecordList";
	}

	/**
	 * @Description: 收货单位列表页
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月22日 下午2:36:12
	 */
	@RequestMapping("product/receiver/list")
	public String receiverListPage(Model model, HttpSession session) {
		try {
			String msg = (String) session.getAttribute("msg");
			if (!StringUtils.isEmpty(msg)) {
				model.addAttribute("msg", msg);
				session.removeAttribute("msg");
			}
			// 查询所有产品信息
			List<Company> companyList = productService.getCompanys(1);
			model.addAttribute("companys", companyList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统异常：" + e.getMessage());
		}
		return "oa/product/receiver/list";
	}

	/**
	 * @Description: 收货单位修改页
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月22日 下午2:36:12
	 */
	@RequestMapping("product/receiver/update/{id}")
	public String receiverUpdatePage(Model model, @PathVariable Integer id) {
		try {
			Company company = productService.getCompanyById(id);
			model.addAttribute("company", company);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统异常：" + e.getMessage());
		}
		return "oa/product/receiver/update";
	}

	/**
	 * @Description: 收货单位修改
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月22日 下午2:36:12
	 */
	@RequestMapping("product/receiver/update")
	public String receiverUpdate(HttpSession session, Company company) {
		try {
			boolean ret = productService.updateCompany(company);
			if (ret == true) {
				session.setAttribute("msg", "修改成功");
			} else {
				session.setAttribute("msg", "修改失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.setAttribute("msg", "系统异常：" + e.getMessage());
		}
		return "redirect:/web/oa/product/receiver/list";
	}

	/**
	 * @Description: 收货单位新增页
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月22日 下午2:36:12
	 */
	@RequestMapping(value = "product/receiver/add", method = RequestMethod.GET)
	public String receiverAddPage() {
		return "oa/product/receiver/add";
	}

	/**
	 * @Description: 产品新增
	 * @return String
	 * @author mlsong
	 * @date 2018年6月22日 下午5:26:43
	 */
	@RequestMapping(value = "product/receiver/add", method = RequestMethod.POST)
	public String receiverAdd(Company company, HttpSession session) {
		try {
			boolean ret = productService.addCompany(company);
			if (ret == true) {
				session.setAttribute("msg", "操作成功");
			} else {
				session.setAttribute("msg", "操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.setAttribute("msg", "系统异常：" + e.getMessage());
		}
		return "redirect:/web/oa/product/receiver/list";
	}

	/**
	 * @Description: 产品列表页
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月22日 下午2:36:12
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("product/list")
	public String productListPage(Model model, HttpSession session, Product product) {
		try {
			String msg = (String) session.getAttribute("msg");
			if (!StringUtils.isEmpty(msg)) {
				model.addAttribute("msg", msg);
			}
			List<Product> products = new ArrayList<Product>();

			// 设置默认页面大小
			if (product.getPageSize() == null || product.getPageSize() < 1) {
				product.setPageSize(PAGE_SIZE);
			}
			// 当前页
			if (product.getCurPage() == null || product.getCurPage() < 1) {
				product.setCurPage(1);
			}
			// 查询所有产品信息
			Map<String, Object> productList = productService.getProducts(product);
			for (Map.Entry<String, Object> entry : productList.entrySet()) {
				products.addAll((List<Product>) entry.getValue());
			}

			int sum = productService.getProductCount(product);

			Integer totalPage = 1;
			totalPage = (sum / product.getPageSize()) + (sum % product.getPageSize() == 0 ? 0 : 1);

			product.setTotalPage(totalPage);
			model.addAttribute("products", products);
			model.addAttribute("prod", product);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统异常：" + e.getMessage());
		}
		return "oa/product/productList";
	}

	/**
	 * @Description: 修改产品状态接口
	 * @param id
	 *            产品id
	 * @return ApiResult
	 * @author mlsong
	 * @date 2018年6月22日 下午3:44:06
	 */
	@ResponseBody
	@RequestMapping("product/changeState/{id}")
	public ApiResult changeProductState(@PathVariable Integer id) {
		ApiResult result = new ApiResult();
		try {
			// 修改产品状态
			boolean isSuccess = productService.changeProductState(id);
			if (!isSuccess) {
				result.setResultCode(ResultCode.FAILED);
				result.setData("修改状态失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResultCode(ResultCode.SYSTEM_ERROR);
			result.setData(e.getMessage());
		}
		return result;
	}

	/**
	 * @Description: 产品修改页
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月22日 下午2:36:12
	 */
	@RequestMapping("product/update/{id}")
	public String productUpdatePage(Model model, @PathVariable Integer id) {
		try {
			Product product = productService.getProductById(id);
			model.addAttribute("product", product);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", "系统异常：" + e.getMessage());
		}
		return "oa/product/productUpdate";
	}

	/**
	 * @Description: 产品修改
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月22日 下午2:36:12
	 */
	@RequestMapping("product/update")
	public String productUpdate(HttpSession session, Product product) {
		try {
			boolean ret = productService.updateProduct(product);
			if (ret == true) {
				session.setAttribute("msg", "修改成功");
			} else {
				session.setAttribute("msg", "修改失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.setAttribute("msg", "系统异常：" + e.getMessage());
		}
		return "redirect:/web/oa/product/list";
	}

	/**
	 * @Description: 产品新增页
	 * @param model
	 * @return String
	 * @author mlsong
	 * @date 2018年6月22日 下午2:36:12
	 */
	@RequestMapping(value = "product/add", method = RequestMethod.GET)
	public String productAddPage() {
		return "oa/product/productAdd";
	}

	/**
	 * @Description: 产品新增
	 * @return String
	 * @author mlsong
	 * @date 2018年6月22日 下午5:26:43
	 */
	@RequestMapping(value = "product/add", method = RequestMethod.POST)
	public String productAdd(Product product, HttpSession session) {
		try {
			boolean ret = productService.addProduct(product);
			if (ret == true) {
				session.setAttribute("msg", "操作成功");
			} else {
				session.setAttribute("msg", "操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			session.setAttribute("msg", "系统异常：" + e.getMessage());
		}
		return "redirect:/web/oa/product/list";
	}
}