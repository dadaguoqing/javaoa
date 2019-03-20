package com.hj.oa.bean;

import java.io.Serializable;

public class LabAll implements Serializable{

	private static final long serialVersionUID = 1L;

//	PCB
	private int id;
	private int sqId;
	private String fileName;
	private String filePath;
	private String createTime;
	private String gyyq;
	private String ddbh;
	private Integer numSet;
	private Integer numUnit;
	private String cs;
	private Float ccChang;
	private Float ccKuang;
	private String cpbh;
	private String cl;
	private String tbNei;
	private String tbWai;
	private String zh;
	private String zhColor;
	private String zf;
	private String zfColor;
	private String cstd;
	private String cstdType;
	private String wxjgfs;
	private String jszyq;
	private String dcbg;
	private String zkcsbg;
	private String cpjcbg;
	private String fgzh;
	private String bmtc;
	private String bmtchd;
	private String kzms;
	private String bz;
//	焊接
	private Integer weldType;
	private Integer num;
	private Integer piles;
	private Integer paster;
	private Integer paster2;
	private Integer gyType;
	private Integer BGAType;
	private Integer hjgy;
	private Integer maxSize;
	private Float ju;
	private Float xzzj;
//	钢网
	private Integer numSet2;
	private Float ccChang2;
	private String size;
	private String cpbh2;
	private String cl2;
	private String bmtc2;
	private String bmtchd2;
	private String type;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSqId() {
		return sqId;
	}
	public void setSqId(int sqId) {
		this.sqId = sqId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getGyyq() {
		return gyyq;
	}
	public void setGyyq(String gyyq) {
		this.gyyq = gyyq;
	}
	public String getDdbh() {
		return ddbh;
	}
	public void setDdbh(String ddbh) {
		this.ddbh = ddbh;
	}
	public Integer getNumSet() {
		return numSet;
	}
	public void setNumSet(Integer numSet) {
		this.numSet = numSet;
	}
	public Integer getNumUnit() {
		return numUnit;
	}
	public void setNumUnit(Integer numUnit) {
		this.numUnit = numUnit;
	}
	public String getCs() {
		return cs;
	}
	public void setCs(String cs) {
		this.cs = cs;
	}
	public Float getCcChang() {
		return ccChang;
	}
	public void setCcChang(Float ccChang) {
		this.ccChang = ccChang;
	}
	public Float getCcKuang() {
		return ccKuang;
	}
	public void setCcKuang(Float ccKuang) {
		this.ccKuang = ccKuang;
	}
	public String getCpbh() {
		return cpbh;
	}
	public void setCpbh(String cpbh) {
		this.cpbh = cpbh;
	}
	public String getCl() {
		return cl;
	}
	public void setCl(String cl) {
		this.cl = cl;
	}
	public String getTbNei() {
		return tbNei;
	}
	public void setTbNei(String tbNei) {
		this.tbNei = tbNei;
	}
	public String getTbWai() {
		return tbWai;
	}
	public void setTbWai(String tbWai) {
		this.tbWai = tbWai;
	}
	public String getZh() {
		return zh;
	}
	public void setZh(String zh) {
		this.zh = zh;
	}
	public String getZhColor() {
		return zhColor;
	}
	public void setZhColor(String zhColor) {
		this.zhColor = zhColor;
	}
	public String getZf() {
		return zf;
	}
	public void setZf(String zf) {
		this.zf = zf;
	}
	public String getZfColor() {
		return zfColor;
	}
	public void setZfColor(String zfColor) {
		this.zfColor = zfColor;
	}
	public String getCstd() {
		return cstd;
	}
	public void setCstd(String cstd) {
		this.cstd = cstd;
	}
	public String getCstdType() {
		return cstdType;
	}
	public void setCstdType(String cstdType) {
		this.cstdType = cstdType;
	}
	public String getWxjgfs() {
		return wxjgfs;
	}
	public void setWxjgfs(String wxjgfs) {
		this.wxjgfs = wxjgfs;
	}
	public String getJszyq() {
		return jszyq;
	}
	public void setJszyq(String jszyq) {
		this.jszyq = jszyq;
	}
	public String getDcbg() {
		return dcbg;
	}
	public void setDcbg(String dcbg) {
		this.dcbg = dcbg;
	}
	public String getZkcsbg() {
		return zkcsbg;
	}
	public void setZkcsbg(String zkcsbg) {
		this.zkcsbg = zkcsbg;
	}
	public String getCpjcbg() {
		return cpjcbg;
	}
	public void setCpjcbg(String cpjcbg) {
		this.cpjcbg = cpjcbg;
	}
	public String getFgzh() {
		return fgzh;
	}
	public void setFgzh(String fgzh) {
		this.fgzh = fgzh;
	}
	public String getBmtc() {
		return bmtc;
	}
	public void setBmtc(String bmtc) {
		this.bmtc = bmtc;
	}
	public String getBmtchd() {
		return bmtchd;
	}
	public void setBmtchd(String bmtchd) {
		this.bmtchd = bmtchd;
	}
	public String getKzms() {
		return kzms;
	}
	public void setKzms(String kzms) {
		this.kzms = kzms;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public Integer getWeldType() {
		return weldType;
	}
	public void setWeldType(Integer weldType) {
		this.weldType = weldType;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getPiles() {
		return piles;
	}
	public void setPiles(Integer piles) {
		this.piles = piles;
	}
	public Integer getPaster() {
		return paster;
	}
	public void setPaster(Integer paster) {
		this.paster = paster;
	}
	public Integer getPaster2() {
		return paster2;
	}
	public void setPaster2(Integer paster2) {
		this.paster2 = paster2;
	}
	public Integer getGyType() {
		return gyType;
	}
	public void setGyType(Integer gyType) {
		this.gyType = gyType;
	}
	public Integer getBGAType() {
		return BGAType;
	}
	public void setBGAType(Integer bGAType) {
		BGAType = bGAType;
	}
	public Integer getHjgy() {
		return hjgy;
	}
	public void setHjgy(Integer hjgy) {
		this.hjgy = hjgy;
	}
	public Integer getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	public Float getJu() {
		return ju;
	}
	public void setJu(Float ju) {
		this.ju = ju;
	}
	public Float getXzzj() {
		return xzzj;
	}
	public void setXzzj(Float xzzj) {
		this.xzzj = xzzj;
	}
	public Integer getNumSet2() {
		return numSet2;
	}
	public void setNumSet2(Integer numSet2) {
		this.numSet2 = numSet2;
	}
	public Float getCcChang2() {
		return ccChang2;
	}
	public void setCcChang2(Float ccChang2) {
		this.ccChang2 = ccChang2;
	}
	
	public String getCpbh2() {
		return cpbh2;
	}
	public void setCpbh2(String cpbh2) {
		this.cpbh2 = cpbh2;
	}
	public String getCl2() {
		return cl2;
	}
	public void setCl2(String cl2) {
		this.cl2 = cl2;
	}
	public String getBmtc2() {
		return bmtc2;
	}
	public void setBmtc2(String bmtc2) {
		this.bmtc2 = bmtc2;
	}
	public String getBmtchd2() {
		return bmtchd2;
	}
	public void setBmtchd2(String bmtchd2) {
		this.bmtchd2 = bmtchd2;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
