package com.hj.oa.bean;

import java.util.List;

public class MerchandiseApplyDetailModel {
    
	private List<MerchandiseApplyDetail> msApplyDetails;

	public List<MerchandiseApplyDetail> getMsApplyDetail() {
		return msApplyDetails;
	}

	public void setMsApplyDetail(List<MerchandiseApplyDetail> msApplyDetails) {
		this.msApplyDetails = msApplyDetails;
	}

	public MerchandiseApplyDetailModel(List<MerchandiseApplyDetail> msApplyDetails) {
		super();
		this.msApplyDetails = msApplyDetails;
	}

	public MerchandiseApplyDetailModel() {
		super();
	}
	
}