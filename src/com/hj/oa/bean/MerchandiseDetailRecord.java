package com.hj.oa.bean;

/**
 * @ClassName:  MerchandiseDetailRecord   
 * @Description: 出入库记录详情BEAN
 * @author: wqfang
 * @date:   2018年10月15日 上午9:27:22   
 *
 */
public class MerchandiseDetailRecord extends MerchandiseApplyDetail {
	
    private Integer sendStatus;


    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }
}