package com.hj.oa;

import java.math.BigDecimal;

public class ExternalApplyDetail {
    private Integer id;

    private String applyCode;

    private String pcbDescription;

    private Integer pcbNum;

    private String impedanceDescript;

    private String pcbGerber;

    private Integer weldNum;

    private String weldBom;

    private String weldGerber;

    private String weldCoordinate;

    private String weldSilkScreen;

    private String weldDescript;

    private Integer steelNum;

    private BigDecimal steelSize1;

    private BigDecimal steelSize2;

    private BigDecimal steelThinkness;

    private String steelMaterial;

    private String steelUse;

    private String steelPolishing;

    private String glueDescript;

    private String paintDescript;

    private Integer acrylicNum;

    private String acrylicCad;

    private Integer chassisNum;

    private String chassisCad;

    private String chassisList;

    private Integer pencilNum;

    private String pencilCad;

    private String pencilList;

    private BigDecimal pcbCost;

    private BigDecimal componentCost;

    private BigDecimal weldCost;

    private BigDecimal steelCost;

    private BigDecimal glueCost;

    private BigDecimal paintCost;

    private BigDecimal acrylicCost;

    private BigDecimal chassisCost;

    private BigDecimal pencilCost;

    private BigDecimal totalCost;

    private String isUrgent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
    }

    public String getPcbDescription() {
        return pcbDescription;
    }

    public void setPcbDescription(String pcbDescription) {
        this.pcbDescription = pcbDescription == null ? null : pcbDescription.trim();
    }

    public Integer getPcbNum() {
        return pcbNum;
    }

    public void setPcbNum(Integer pcbNum) {
        this.pcbNum = pcbNum;
    }

    public String getImpedanceDescript() {
        return impedanceDescript;
    }

    public void setImpedanceDescript(String impedanceDescript) {
        this.impedanceDescript = impedanceDescript == null ? null : impedanceDescript.trim();
    }

    public String getPcbGerber() {
        return pcbGerber;
    }

    public void setPcbGerber(String pcbGerber) {
        this.pcbGerber = pcbGerber == null ? null : pcbGerber.trim();
    }

    public Integer getWeldNum() {
        return weldNum;
    }

    public void setWeldNum(Integer weldNum) {
        this.weldNum = weldNum;
    }

    public String getWeldBom() {
        return weldBom;
    }

    public void setWeldBom(String weldBom) {
        this.weldBom = weldBom == null ? null : weldBom.trim();
    }

    public String getWeldGerber() {
        return weldGerber;
    }

    public void setWeldGerber(String weldGerber) {
        this.weldGerber = weldGerber == null ? null : weldGerber.trim();
    }

    public String getWeldCoordinate() {
        return weldCoordinate;
    }

    public void setWeldCoordinate(String weldCoordinate) {
        this.weldCoordinate = weldCoordinate == null ? null : weldCoordinate.trim();
    }

    public String getWeldSilkScreen() {
        return weldSilkScreen;
    }

    public void setWeldSilkScreen(String weldSilkScreen) {
        this.weldSilkScreen = weldSilkScreen == null ? null : weldSilkScreen.trim();
    }

    public String getWeldDescript() {
        return weldDescript;
    }

    public void setWeldDescript(String weldDescript) {
        this.weldDescript = weldDescript == null ? null : weldDescript.trim();
    }

    public Integer getSteelNum() {
        return steelNum;
    }

    public void setSteelNum(Integer steelNum) {
        this.steelNum = steelNum;
    }

    public BigDecimal getSteelSize1() {
        return steelSize1;
    }

    public void setSteelSize1(BigDecimal steelSize1) {
        this.steelSize1 = steelSize1;
    }

    public BigDecimal getSteelSize2() {
        return steelSize2;
    }

    public void setSteelSize2(BigDecimal steelSize2) {
        this.steelSize2 = steelSize2;
    }

    public BigDecimal getSteelThinkness() {
        return steelThinkness;
    }

    public void setSteelThinkness(BigDecimal steelThinkness) {
        this.steelThinkness = steelThinkness;
    }

    public String getSteelMaterial() {
        return steelMaterial;
    }

    public void setSteelMaterial(String steelMaterial) {
        this.steelMaterial = steelMaterial == null ? null : steelMaterial.trim();
    }

    public String getSteelUse() {
        return steelUse;
    }

    public void setSteelUse(String steelUse) {
        this.steelUse = steelUse == null ? null : steelUse.trim();
    }

    public String getSteelPolishing() {
        return steelPolishing;
    }

    public void setSteelPolishing(String steelPolishing) {
        this.steelPolishing = steelPolishing == null ? null : steelPolishing.trim();
    }

    public String getGlueDescript() {
        return glueDescript;
    }

    public void setGlueDescript(String glueDescript) {
        this.glueDescript = glueDescript == null ? null : glueDescript.trim();
    }

    public String getPaintDescript() {
        return paintDescript;
    }

    public void setPaintDescript(String paintDescript) {
        this.paintDescript = paintDescript == null ? null : paintDescript.trim();
    }

    public Integer getAcrylicNum() {
        return acrylicNum;
    }

    public void setAcrylicNum(Integer acrylicNum) {
        this.acrylicNum = acrylicNum;
    }

    public String getAcrylicCad() {
        return acrylicCad;
    }

    public void setAcrylicCad(String acrylicCad) {
        this.acrylicCad = acrylicCad == null ? null : acrylicCad.trim();
    }

    public Integer getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(Integer chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getChassisCad() {
        return chassisCad;
    }

    public void setChassisCad(String chassisCad) {
        this.chassisCad = chassisCad == null ? null : chassisCad.trim();
    }

    public String getChassisList() {
        return chassisList;
    }

    public void setChassisList(String chassisList) {
        this.chassisList = chassisList == null ? null : chassisList.trim();
    }

    public Integer getPencilNum() {
        return pencilNum;
    }

    public void setPencilNum(Integer pencilNum) {
        this.pencilNum = pencilNum;
    }

    public String getPencilCad() {
        return pencilCad;
    }

    public void setPencilCad(String pencilCad) {
        this.pencilCad = pencilCad == null ? null : pencilCad.trim();
    }

    public String getPencilList() {
        return pencilList;
    }

    public void setPencilList(String pencilList) {
        this.pencilList = pencilList == null ? null : pencilList.trim();
    }

    public BigDecimal getPcbCost() {
        return pcbCost;
    }

    public void setPcbCost(BigDecimal pcbCost) {
        this.pcbCost = pcbCost;
    }

    public BigDecimal getComponentCost() {
        return componentCost;
    }

    public void setComponentCost(BigDecimal componentCost) {
        this.componentCost = componentCost;
    }

    public BigDecimal getWeldCost() {
        return weldCost;
    }

    public void setWeldCost(BigDecimal weldCost) {
        this.weldCost = weldCost;
    }

    public BigDecimal getSteelCost() {
        return steelCost;
    }

    public void setSteelCost(BigDecimal steelCost) {
        this.steelCost = steelCost;
    }

    public BigDecimal getGlueCost() {
        return glueCost;
    }

    public void setGlueCost(BigDecimal glueCost) {
        this.glueCost = glueCost;
    }

    public BigDecimal getPaintCost() {
        return paintCost;
    }

    public void setPaintCost(BigDecimal paintCost) {
        this.paintCost = paintCost;
    }

    public BigDecimal getAcrylicCost() {
        return acrylicCost;
    }

    public void setAcrylicCost(BigDecimal acrylicCost) {
        this.acrylicCost = acrylicCost;
    }

    public BigDecimal getChassisCost() {
        return chassisCost;
    }

    public void setChassisCost(BigDecimal chassisCost) {
        this.chassisCost = chassisCost;
    }

    public BigDecimal getPencilCost() {
        return pencilCost;
    }

    public void setPencilCost(BigDecimal pencilCost) {
        this.pencilCost = pencilCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(String isUrgent) {
        this.isUrgent = isUrgent == null ? null : isUrgent.trim();
    }
}