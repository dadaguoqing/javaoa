package com.hj.oa.bean;

import java.io.Serializable;

/**
 * 芯片申请审批表
 * @author wqfang
 *
 */
public class ApplySiliconApprove implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;//芯片申请
	private int applyId;//芯片申请ID
	/**
	 * <p>芯片申请审批状态</p>
	 * <p>-1:失败</p>
	 * <p>0:一级审批</p>
	 * <p>1:二级审批</p>
	 * <p>2:三级审批</p>
	 * <p>3:四级审批</p>
	 * <p>4:五级审批</p>
	 * <p>5:成功</p>
	 * */
	private int status;
	private Integer approveId01;//一级审批人empId
	private Integer approveId02;
	private Integer approveId03;
	private Integer approveId04;
	private Integer approveId05;
	private String approveReason01;//一级审批人意见
	private String approveReason02;
	private String approveReason03;
	private String approveReason04;
	private String approveReason05;
	private int currentId;//当前审批ID
	private String applyName;
	private int empId;//申请人ID
	private String brand;//芯片型号
	private int num;//芯片申请数量
	private String reason;//申请原因
	private String dayStr;//申请日期
	private String useDayStr;//需求日期
	private int deptId;
	private String empName;
	private String packageType;
	private String unit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getApproveId01() {
		return approveId01;
	}
	public void setApproveId01(Integer approveId01) {
		this.approveId01 = approveId01;
	}
	public Integer getApproveId02() {
		return approveId02;
	}
	public void setApproveId02(Integer approveId02) {
		this.approveId02 = approveId02;
	}
	public Integer getApproveId03() {
		return approveId03;
	}
	public void setApproveId03(Integer approveId03) {
		this.approveId03 = approveId03;
	}
	public Integer getApproveId04() {
		return approveId04;
	}
	public void setApproveId04(Integer approveId04) {
		this.approveId04 = approveId04;
	}
	public Integer getApproveId05() {
		return approveId05;
	}
	public void setApproveId05(Integer approveId05) {
		this.approveId05 = approveId05;
	}
	public String getApproveReason01() {
		return approveReason01;
	}
	public void setApproveReason01(String approveReason01) {
		this.approveReason01 = approveReason01;
	}
	public String getApproveReason02() {
		return approveReason02;
	}
	public void setApproveReason02(String approveReason02) {
		this.approveReason02 = approveReason02;
	}
	public String getApproveReason03() {
		return approveReason03;
	}
	public void setApproveReason03(String approveReason03) {
		this.approveReason03 = approveReason03;
	}
	public String getApproveReason04() {
		return approveReason04;
	}
	public void setApproveReason04(String approveReason04) {
		this.approveReason04 = approveReason04;
	}
	public String getApproveReason05() {
		return approveReason05;
	}
	public void setApproveReason05(String approveReason05) {
		this.approveReason05 = approveReason05;
	}
	public int getCurrentId() {
		return currentId;
	}
	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getUseDayStr() {
		return useDayStr;
	}
	public void setUseDayStr(String useDayStr) {
		this.useDayStr = useDayStr;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
