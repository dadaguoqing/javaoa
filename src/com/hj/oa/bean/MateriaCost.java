package com.hj.oa.bean;

import java.io.Serializable;

/**
 * 物料申领花费
 * @author wqfang
 *
 * 2018年6月7日
 */
public class MateriaCost implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int mt_code;
	private String cost_code;
	private int mt_warehouse;
	private int materiaId;
	private int warehouseId;
	private Double cost_num;
	private Double cost_price;
	private double mt_cache;
	private double mt_stock;
	private double mt_price;
	private String mt_date;
	
	
	public Double getCost_num() {
		return cost_num;
	}
	public void setCost_num(Double cost_num) {
		this.cost_num = cost_num;
	}
	public Double getCost_price() {
		return cost_price;
	}
	public void setCost_price(Double cost_price) {
		this.cost_price = cost_price;
	}
	public String getCost_code() {
		return cost_code;
	}
	public void setCost_code(String cost_code) {
		this.cost_code = cost_code;
	}
	public int getMateriaId() {
		return materiaId;
	}
	public void setMateriaId(int materiaId) {
		this.materiaId = materiaId;
	}
	public int getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMt_code() {
		return mt_code;
	}
	public void setMt_code(int mt_code) {
		this.mt_code = mt_code;
	}
	public int getMt_warehouse() {
		return mt_warehouse;
	}
	public void setMt_warehouse(int mt_warehouse) {
		this.mt_warehouse = mt_warehouse;
	}
	public double getMt_cache() {
		return mt_cache;
	}
	public void setMt_cache(double mt_cache) {
		this.mt_cache = mt_cache;
	}
	public double getMt_stock() {
		return mt_stock;
	}
	public void setMt_stock(double mt_stock) {
		this.mt_stock = mt_stock;
	}
	public double getMt_price() {
		return mt_price;
	}
	public void setMt_price(double mt_price) {
		this.mt_price = mt_price;
	}
	public String getMt_date() {
		return mt_date;
	}
	public void setMt_date(String mt_date) {
		this.mt_date = mt_date;
	}
}
