package com.hj.oa.bean;

import java.util.ArrayList;
import java.util.List;

public class MateriaPurchaseExample {
    protected String orderByClause;
    
    private Integer beginIndex;
    
    private Integer pageSize;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;
    
    
    public Integer getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(Integer beginIndex) {
		this.beginIndex = beginIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public MateriaPurchaseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andEmpIdIsNull() {
            addCriterion("emp_id is null");
            return (Criteria) this;
        }

        public Criteria andEmpIdIsNotNull() {
            addCriterion("emp_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmpIdEqualTo(Integer value) {
            addCriterion("emp_id =", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotEqualTo(Integer value) {
            addCriterion("emp_id <>", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdGreaterThan(Integer value) {
            addCriterion("emp_id >", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("emp_id >=", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdLessThan(Integer value) {
            addCriterion("emp_id <", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdLessThanOrEqualTo(Integer value) {
            addCriterion("emp_id <=", value, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdIn(List<Integer> values) {
            addCriterion("emp_id in", values, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotIn(List<Integer> values) {
            addCriterion("emp_id not in", values, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdBetween(Integer value1, Integer value2) {
            addCriterion("emp_id between", value1, value2, "empId");
            return (Criteria) this;
        }

        public Criteria andEmpIdNotBetween(Integer value1, Integer value2) {
            addCriterion("emp_id not between", value1, value2, "empId");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeIsNull() {
            addCriterion("requisition_code is null");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeIsNotNull() {
            addCriterion("requisition_code is not null");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeEqualTo(String value) {
            addCriterion("requisition_code =", value, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeNotEqualTo(String value) {
            addCriterion("requisition_code <>", value, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeGreaterThan(String value) {
            addCriterion("requisition_code >", value, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeGreaterThanOrEqualTo(String value) {
            addCriterion("requisition_code >=", value, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeLessThan(String value) {
            addCriterion("requisition_code <", value, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeLessThanOrEqualTo(String value) {
            addCriterion("requisition_code <=", value, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeLike(String value) {
            addCriterion("requisition_code like", value, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeNotLike(String value) {
            addCriterion("requisition_code not like", value, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeIn(List<String> values) {
            addCriterion("requisition_code in", values, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeNotIn(List<String> values) {
            addCriterion("requisition_code not in", values, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeBetween(String value1, String value2) {
            addCriterion("requisition_code between", value1, value2, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andRequisitionCodeNotBetween(String value1, String value2) {
            addCriterion("requisition_code not between", value1, value2, "requisitionCode");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(String value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(String value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(String value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(String value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(String value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLike(String value) {
            addCriterion("reason like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotLike(String value) {
            addCriterion("reason not like", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<String> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<String> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(String value1, String value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(String value1, String value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andDaystrIsNull() {
            addCriterion("dayStr is null");
            return (Criteria) this;
        }

        public Criteria andDaystrIsNotNull() {
            addCriterion("dayStr is not null");
            return (Criteria) this;
        }

        public Criteria andDaystrEqualTo(String value) {
            addCriterion("dayStr =", value, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrNotEqualTo(String value) {
            addCriterion("dayStr <>", value, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrGreaterThan(String value) {
            addCriterion("dayStr >", value, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrGreaterThanOrEqualTo(String value) {
            addCriterion("dayStr >=", value, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrLessThan(String value) {
            addCriterion("dayStr <", value, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrLessThanOrEqualTo(String value) {
            addCriterion("dayStr <=", value, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrLike(String value) {
            addCriterion("dayStr like", value, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrNotLike(String value) {
            addCriterion("dayStr not like", value, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrIn(List<String> values) {
            addCriterion("dayStr in", values, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrNotIn(List<String> values) {
            addCriterion("dayStr not in", values, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrBetween(String value1, String value2) {
            addCriterion("dayStr between", value1, value2, "daystr");
            return (Criteria) this;
        }

        public Criteria andDaystrNotBetween(String value1, String value2) {
            addCriterion("dayStr not between", value1, value2, "daystr");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeIsNull() {
            addCriterion("purchase_code is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeIsNotNull() {
            addCriterion("purchase_code is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeEqualTo(String value) {
            addCriterion("purchase_code =", value, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeNotEqualTo(String value) {
            addCriterion("purchase_code <>", value, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeGreaterThan(String value) {
            addCriterion("purchase_code >", value, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_code >=", value, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeLessThan(String value) {
            addCriterion("purchase_code <", value, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeLessThanOrEqualTo(String value) {
            addCriterion("purchase_code <=", value, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeLike(String value) {
            addCriterion("purchase_code like", value, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeNotLike(String value) {
            addCriterion("purchase_code not like", value, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeIn(List<String> values) {
            addCriterion("purchase_code in", values, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeNotIn(List<String> values) {
            addCriterion("purchase_code not in", values, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeBetween(String value1, String value2) {
            addCriterion("purchase_code between", value1, value2, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andPurchaseCodeNotBetween(String value1, String value2) {
            addCriterion("purchase_code not between", value1, value2, "purchaseCode");
            return (Criteria) this;
        }

        public Criteria andCountMoneyIsNull() {
            addCriterion("count_money is null");
            return (Criteria) this;
        }

        public Criteria andCountMoneyIsNotNull() {
            addCriterion("count_money is not null");
            return (Criteria) this;
        }

        public Criteria andCountMoneyEqualTo(Double value) {
            addCriterion("count_money =", value, "countMoney");
            return (Criteria) this;
        }

        public Criteria andCountMoneyNotEqualTo(Double value) {
            addCriterion("count_money <>", value, "countMoney");
            return (Criteria) this;
        }

        public Criteria andCountMoneyGreaterThan(Double value) {
            addCriterion("count_money >", value, "countMoney");
            return (Criteria) this;
        }

        public Criteria andCountMoneyGreaterThanOrEqualTo(Double value) {
            addCriterion("count_money >=", value, "countMoney");
            return (Criteria) this;
        }

        public Criteria andCountMoneyLessThan(Double value) {
            addCriterion("count_money <", value, "countMoney");
            return (Criteria) this;
        }

        public Criteria andCountMoneyLessThanOrEqualTo(Double value) {
            addCriterion("count_money <=", value, "countMoney");
            return (Criteria) this;
        }

        public Criteria andCountMoneyIn(List<Double> values) {
            addCriterion("count_money in", values, "countMoney");
            return (Criteria) this;
        }

        public Criteria andCountMoneyNotIn(List<Double> values) {
            addCriterion("count_money not in", values, "countMoney");
            return (Criteria) this;
        }

        public Criteria andCountMoneyBetween(Double value1, Double value2) {
            addCriterion("count_money between", value1, value2, "countMoney");
            return (Criteria) this;
        }

        public Criteria andCountMoneyNotBetween(Double value1, Double value2) {
            addCriterion("count_money not between", value1, value2, "countMoney");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andCurrentIdIsNull() {
            addCriterion("current_id is null");
            return (Criteria) this;
        }

        public Criteria andCurrentIdIsNotNull() {
            addCriterion("current_id is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentIdEqualTo(Integer value) {
            addCriterion("current_id =", value, "currentId");
            return (Criteria) this;
        }

        public Criteria andCurrentIdNotEqualTo(Integer value) {
            addCriterion("current_id <>", value, "currentId");
            return (Criteria) this;
        }

        public Criteria andCurrentIdGreaterThan(Integer value) {
            addCriterion("current_id >", value, "currentId");
            return (Criteria) this;
        }

        public Criteria andCurrentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_id >=", value, "currentId");
            return (Criteria) this;
        }

        public Criteria andCurrentIdLessThan(Integer value) {
            addCriterion("current_id <", value, "currentId");
            return (Criteria) this;
        }

        public Criteria andCurrentIdLessThanOrEqualTo(Integer value) {
            addCriterion("current_id <=", value, "currentId");
            return (Criteria) this;
        }

        public Criteria andCurrentIdIn(List<Integer> values) {
            addCriterion("current_id in", values, "currentId");
            return (Criteria) this;
        }

        public Criteria andCurrentIdNotIn(List<Integer> values) {
            addCriterion("current_id not in", values, "currentId");
            return (Criteria) this;
        }

        public Criteria andCurrentIdBetween(Integer value1, Integer value2) {
            addCriterion("current_id between", value1, value2, "currentId");
            return (Criteria) this;
        }

        public Criteria andCurrentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("current_id not between", value1, value2, "currentId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrl2IsNull() {
            addCriterion("url2 is null");
            return (Criteria) this;
        }

        public Criteria andUrl2IsNotNull() {
            addCriterion("url2 is not null");
            return (Criteria) this;
        }

        public Criteria andUrl2EqualTo(String value) {
            addCriterion("url2 =", value, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2NotEqualTo(String value) {
            addCriterion("url2 <>", value, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2GreaterThan(String value) {
            addCriterion("url2 >", value, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2GreaterThanOrEqualTo(String value) {
            addCriterion("url2 >=", value, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2LessThan(String value) {
            addCriterion("url2 <", value, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2LessThanOrEqualTo(String value) {
            addCriterion("url2 <=", value, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2Like(String value) {
            addCriterion("url2 like", value, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2NotLike(String value) {
            addCriterion("url2 not like", value, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2In(List<String> values) {
            addCriterion("url2 in", values, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2NotIn(List<String> values) {
            addCriterion("url2 not in", values, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2Between(String value1, String value2) {
            addCriterion("url2 between", value1, value2, "url2");
            return (Criteria) this;
        }

        public Criteria andUrl2NotBetween(String value1, String value2) {
            addCriterion("url2 not between", value1, value2, "url2");
            return (Criteria) this;
        }

        public Criteria andProjectcodeIsNull() {
            addCriterion("projectCode is null");
            return (Criteria) this;
        }

        public Criteria andProjectcodeIsNotNull() {
            addCriterion("projectCode is not null");
            return (Criteria) this;
        }

        public Criteria andProjectcodeEqualTo(String value) {
            addCriterion("projectCode =", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotEqualTo(String value) {
            addCriterion("projectCode <>", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeGreaterThan(String value) {
            addCriterion("projectCode >", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeGreaterThanOrEqualTo(String value) {
            addCriterion("projectCode >=", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeLessThan(String value) {
            addCriterion("projectCode <", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeLessThanOrEqualTo(String value) {
            addCriterion("projectCode <=", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeLike(String value) {
            addCriterion("projectCode like", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotLike(String value) {
            addCriterion("projectCode not like", value, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeIn(List<String> values) {
            addCriterion("projectCode in", values, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotIn(List<String> values) {
            addCriterion("projectCode not in", values, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeBetween(String value1, String value2) {
            addCriterion("projectCode between", value1, value2, "projectcode");
            return (Criteria) this;
        }

        public Criteria andProjectcodeNotBetween(String value1, String value2) {
            addCriterion("projectCode not between", value1, value2, "projectcode");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}