package com.hj.oa.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExternalDealExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExternalDealExample() {
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

        public Criteria andApplyCodeIsNull() {
            addCriterion("apply_code is null");
            return (Criteria) this;
        }

        public Criteria andApplyCodeIsNotNull() {
            addCriterion("apply_code is not null");
            return (Criteria) this;
        }

        public Criteria andApplyCodeEqualTo(String value) {
            addCriterion("apply_code =", value, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeNotEqualTo(String value) {
            addCriterion("apply_code <>", value, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeGreaterThan(String value) {
            addCriterion("apply_code >", value, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("apply_code >=", value, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeLessThan(String value) {
            addCriterion("apply_code <", value, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeLessThanOrEqualTo(String value) {
            addCriterion("apply_code <=", value, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeLike(String value) {
            addCriterion("apply_code like", value, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeNotLike(String value) {
            addCriterion("apply_code not like", value, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeIn(List<String> values) {
            addCriterion("apply_code in", values, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeNotIn(List<String> values) {
            addCriterion("apply_code not in", values, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeBetween(String value1, String value2) {
            addCriterion("apply_code between", value1, value2, "applyCode");
            return (Criteria) this;
        }

        public Criteria andApplyCodeNotBetween(String value1, String value2) {
            addCriterion("apply_code not between", value1, value2, "applyCode");
            return (Criteria) this;
        }

        public Criteria andPcbCostIsNull() {
            addCriterion("pcb_cost is null");
            return (Criteria) this;
        }

        public Criteria andPcbCostIsNotNull() {
            addCriterion("pcb_cost is not null");
            return (Criteria) this;
        }

        public Criteria andPcbCostEqualTo(BigDecimal value) {
            addCriterion("pcb_cost =", value, "pcbCost");
            return (Criteria) this;
        }

        public Criteria andPcbCostNotEqualTo(BigDecimal value) {
            addCriterion("pcb_cost <>", value, "pcbCost");
            return (Criteria) this;
        }

        public Criteria andPcbCostGreaterThan(BigDecimal value) {
            addCriterion("pcb_cost >", value, "pcbCost");
            return (Criteria) this;
        }

        public Criteria andPcbCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pcb_cost >=", value, "pcbCost");
            return (Criteria) this;
        }

        public Criteria andPcbCostLessThan(BigDecimal value) {
            addCriterion("pcb_cost <", value, "pcbCost");
            return (Criteria) this;
        }

        public Criteria andPcbCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pcb_cost <=", value, "pcbCost");
            return (Criteria) this;
        }

        public Criteria andPcbCostIn(List<BigDecimal> values) {
            addCriterion("pcb_cost in", values, "pcbCost");
            return (Criteria) this;
        }

        public Criteria andPcbCostNotIn(List<BigDecimal> values) {
            addCriterion("pcb_cost not in", values, "pcbCost");
            return (Criteria) this;
        }

        public Criteria andPcbCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pcb_cost between", value1, value2, "pcbCost");
            return (Criteria) this;
        }

        public Criteria andPcbCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pcb_cost not between", value1, value2, "pcbCost");
            return (Criteria) this;
        }

        public Criteria andPcbDateIsNull() {
            addCriterion("pcb_date is null");
            return (Criteria) this;
        }

        public Criteria andPcbDateIsNotNull() {
            addCriterion("pcb_date is not null");
            return (Criteria) this;
        }

        public Criteria andPcbDateEqualTo(String value) {
            addCriterion("pcb_date =", value, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateNotEqualTo(String value) {
            addCriterion("pcb_date <>", value, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateGreaterThan(String value) {
            addCriterion("pcb_date >", value, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateGreaterThanOrEqualTo(String value) {
            addCriterion("pcb_date >=", value, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateLessThan(String value) {
            addCriterion("pcb_date <", value, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateLessThanOrEqualTo(String value) {
            addCriterion("pcb_date <=", value, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateLike(String value) {
            addCriterion("pcb_date like", value, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateNotLike(String value) {
            addCriterion("pcb_date not like", value, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateIn(List<String> values) {
            addCriterion("pcb_date in", values, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateNotIn(List<String> values) {
            addCriterion("pcb_date not in", values, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateBetween(String value1, String value2) {
            addCriterion("pcb_date between", value1, value2, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbDateNotBetween(String value1, String value2) {
            addCriterion("pcb_date not between", value1, value2, "pcbDate");
            return (Criteria) this;
        }

        public Criteria andPcbPdfIsNull() {
            addCriterion("pcb_pdf is null");
            return (Criteria) this;
        }

        public Criteria andPcbPdfIsNotNull() {
            addCriterion("pcb_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andPcbPdfEqualTo(String value) {
            addCriterion("pcb_pdf =", value, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfNotEqualTo(String value) {
            addCriterion("pcb_pdf <>", value, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfGreaterThan(String value) {
            addCriterion("pcb_pdf >", value, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfGreaterThanOrEqualTo(String value) {
            addCriterion("pcb_pdf >=", value, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfLessThan(String value) {
            addCriterion("pcb_pdf <", value, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfLessThanOrEqualTo(String value) {
            addCriterion("pcb_pdf <=", value, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfLike(String value) {
            addCriterion("pcb_pdf like", value, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfNotLike(String value) {
            addCriterion("pcb_pdf not like", value, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfIn(List<String> values) {
            addCriterion("pcb_pdf in", values, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfNotIn(List<String> values) {
            addCriterion("pcb_pdf not in", values, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfBetween(String value1, String value2) {
            addCriterion("pcb_pdf between", value1, value2, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andPcbPdfNotBetween(String value1, String value2) {
            addCriterion("pcb_pdf not between", value1, value2, "pcbPdf");
            return (Criteria) this;
        }

        public Criteria andBomCostIsNull() {
            addCriterion("bom_cost is null");
            return (Criteria) this;
        }

        public Criteria andBomCostIsNotNull() {
            addCriterion("bom_cost is not null");
            return (Criteria) this;
        }

        public Criteria andBomCostEqualTo(BigDecimal value) {
            addCriterion("bom_cost =", value, "bomCost");
            return (Criteria) this;
        }

        public Criteria andBomCostNotEqualTo(BigDecimal value) {
            addCriterion("bom_cost <>", value, "bomCost");
            return (Criteria) this;
        }

        public Criteria andBomCostGreaterThan(BigDecimal value) {
            addCriterion("bom_cost >", value, "bomCost");
            return (Criteria) this;
        }

        public Criteria andBomCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bom_cost >=", value, "bomCost");
            return (Criteria) this;
        }

        public Criteria andBomCostLessThan(BigDecimal value) {
            addCriterion("bom_cost <", value, "bomCost");
            return (Criteria) this;
        }

        public Criteria andBomCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bom_cost <=", value, "bomCost");
            return (Criteria) this;
        }

        public Criteria andBomCostIn(List<BigDecimal> values) {
            addCriterion("bom_cost in", values, "bomCost");
            return (Criteria) this;
        }

        public Criteria andBomCostNotIn(List<BigDecimal> values) {
            addCriterion("bom_cost not in", values, "bomCost");
            return (Criteria) this;
        }

        public Criteria andBomCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bom_cost between", value1, value2, "bomCost");
            return (Criteria) this;
        }

        public Criteria andBomCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bom_cost not between", value1, value2, "bomCost");
            return (Criteria) this;
        }

        public Criteria andBomDateIsNull() {
            addCriterion("bom_date is null");
            return (Criteria) this;
        }

        public Criteria andBomDateIsNotNull() {
            addCriterion("bom_date is not null");
            return (Criteria) this;
        }

        public Criteria andBomDateEqualTo(String value) {
            addCriterion("bom_date =", value, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateNotEqualTo(String value) {
            addCriterion("bom_date <>", value, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateGreaterThan(String value) {
            addCriterion("bom_date >", value, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateGreaterThanOrEqualTo(String value) {
            addCriterion("bom_date >=", value, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateLessThan(String value) {
            addCriterion("bom_date <", value, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateLessThanOrEqualTo(String value) {
            addCriterion("bom_date <=", value, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateLike(String value) {
            addCriterion("bom_date like", value, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateNotLike(String value) {
            addCriterion("bom_date not like", value, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateIn(List<String> values) {
            addCriterion("bom_date in", values, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateNotIn(List<String> values) {
            addCriterion("bom_date not in", values, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateBetween(String value1, String value2) {
            addCriterion("bom_date between", value1, value2, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomDateNotBetween(String value1, String value2) {
            addCriterion("bom_date not between", value1, value2, "bomDate");
            return (Criteria) this;
        }

        public Criteria andBomPdfIsNull() {
            addCriterion("bom_pdf is null");
            return (Criteria) this;
        }

        public Criteria andBomPdfIsNotNull() {
            addCriterion("bom_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andBomPdfEqualTo(String value) {
            addCriterion("bom_pdf =", value, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfNotEqualTo(String value) {
            addCriterion("bom_pdf <>", value, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfGreaterThan(String value) {
            addCriterion("bom_pdf >", value, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfGreaterThanOrEqualTo(String value) {
            addCriterion("bom_pdf >=", value, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfLessThan(String value) {
            addCriterion("bom_pdf <", value, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfLessThanOrEqualTo(String value) {
            addCriterion("bom_pdf <=", value, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfLike(String value) {
            addCriterion("bom_pdf like", value, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfNotLike(String value) {
            addCriterion("bom_pdf not like", value, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfIn(List<String> values) {
            addCriterion("bom_pdf in", values, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfNotIn(List<String> values) {
            addCriterion("bom_pdf not in", values, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfBetween(String value1, String value2) {
            addCriterion("bom_pdf between", value1, value2, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andBomPdfNotBetween(String value1, String value2) {
            addCriterion("bom_pdf not between", value1, value2, "bomPdf");
            return (Criteria) this;
        }

        public Criteria andWeldCostIsNull() {
            addCriterion("weld_cost is null");
            return (Criteria) this;
        }

        public Criteria andWeldCostIsNotNull() {
            addCriterion("weld_cost is not null");
            return (Criteria) this;
        }

        public Criteria andWeldCostEqualTo(BigDecimal value) {
            addCriterion("weld_cost =", value, "weldCost");
            return (Criteria) this;
        }

        public Criteria andWeldCostNotEqualTo(BigDecimal value) {
            addCriterion("weld_cost <>", value, "weldCost");
            return (Criteria) this;
        }

        public Criteria andWeldCostGreaterThan(BigDecimal value) {
            addCriterion("weld_cost >", value, "weldCost");
            return (Criteria) this;
        }

        public Criteria andWeldCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("weld_cost >=", value, "weldCost");
            return (Criteria) this;
        }

        public Criteria andWeldCostLessThan(BigDecimal value) {
            addCriterion("weld_cost <", value, "weldCost");
            return (Criteria) this;
        }

        public Criteria andWeldCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("weld_cost <=", value, "weldCost");
            return (Criteria) this;
        }

        public Criteria andWeldCostIn(List<BigDecimal> values) {
            addCriterion("weld_cost in", values, "weldCost");
            return (Criteria) this;
        }

        public Criteria andWeldCostNotIn(List<BigDecimal> values) {
            addCriterion("weld_cost not in", values, "weldCost");
            return (Criteria) this;
        }

        public Criteria andWeldCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weld_cost between", value1, value2, "weldCost");
            return (Criteria) this;
        }

        public Criteria andWeldCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("weld_cost not between", value1, value2, "weldCost");
            return (Criteria) this;
        }

        public Criteria andWeldDateIsNull() {
            addCriterion("weld_date is null");
            return (Criteria) this;
        }

        public Criteria andWeldDateIsNotNull() {
            addCriterion("weld_date is not null");
            return (Criteria) this;
        }

        public Criteria andWeldDateEqualTo(String value) {
            addCriterion("weld_date =", value, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateNotEqualTo(String value) {
            addCriterion("weld_date <>", value, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateGreaterThan(String value) {
            addCriterion("weld_date >", value, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateGreaterThanOrEqualTo(String value) {
            addCriterion("weld_date >=", value, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateLessThan(String value) {
            addCriterion("weld_date <", value, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateLessThanOrEqualTo(String value) {
            addCriterion("weld_date <=", value, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateLike(String value) {
            addCriterion("weld_date like", value, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateNotLike(String value) {
            addCriterion("weld_date not like", value, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateIn(List<String> values) {
            addCriterion("weld_date in", values, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateNotIn(List<String> values) {
            addCriterion("weld_date not in", values, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateBetween(String value1, String value2) {
            addCriterion("weld_date between", value1, value2, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldDateNotBetween(String value1, String value2) {
            addCriterion("weld_date not between", value1, value2, "weldDate");
            return (Criteria) this;
        }

        public Criteria andWeldPdfIsNull() {
            addCriterion("weld_pdf is null");
            return (Criteria) this;
        }

        public Criteria andWeldPdfIsNotNull() {
            addCriterion("weld_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andWeldPdfEqualTo(String value) {
            addCriterion("weld_pdf =", value, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfNotEqualTo(String value) {
            addCriterion("weld_pdf <>", value, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfGreaterThan(String value) {
            addCriterion("weld_pdf >", value, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfGreaterThanOrEqualTo(String value) {
            addCriterion("weld_pdf >=", value, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfLessThan(String value) {
            addCriterion("weld_pdf <", value, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfLessThanOrEqualTo(String value) {
            addCriterion("weld_pdf <=", value, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfLike(String value) {
            addCriterion("weld_pdf like", value, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfNotLike(String value) {
            addCriterion("weld_pdf not like", value, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfIn(List<String> values) {
            addCriterion("weld_pdf in", values, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfNotIn(List<String> values) {
            addCriterion("weld_pdf not in", values, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfBetween(String value1, String value2) {
            addCriterion("weld_pdf between", value1, value2, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andWeldPdfNotBetween(String value1, String value2) {
            addCriterion("weld_pdf not between", value1, value2, "weldPdf");
            return (Criteria) this;
        }

        public Criteria andSteelCostIsNull() {
            addCriterion("steel_cost is null");
            return (Criteria) this;
        }

        public Criteria andSteelCostIsNotNull() {
            addCriterion("steel_cost is not null");
            return (Criteria) this;
        }

        public Criteria andSteelCostEqualTo(BigDecimal value) {
            addCriterion("steel_cost =", value, "steelCost");
            return (Criteria) this;
        }

        public Criteria andSteelCostNotEqualTo(BigDecimal value) {
            addCriterion("steel_cost <>", value, "steelCost");
            return (Criteria) this;
        }

        public Criteria andSteelCostGreaterThan(BigDecimal value) {
            addCriterion("steel_cost >", value, "steelCost");
            return (Criteria) this;
        }

        public Criteria andSteelCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("steel_cost >=", value, "steelCost");
            return (Criteria) this;
        }

        public Criteria andSteelCostLessThan(BigDecimal value) {
            addCriterion("steel_cost <", value, "steelCost");
            return (Criteria) this;
        }

        public Criteria andSteelCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("steel_cost <=", value, "steelCost");
            return (Criteria) this;
        }

        public Criteria andSteelCostIn(List<BigDecimal> values) {
            addCriterion("steel_cost in", values, "steelCost");
            return (Criteria) this;
        }

        public Criteria andSteelCostNotIn(List<BigDecimal> values) {
            addCriterion("steel_cost not in", values, "steelCost");
            return (Criteria) this;
        }

        public Criteria andSteelCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("steel_cost between", value1, value2, "steelCost");
            return (Criteria) this;
        }

        public Criteria andSteelCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("steel_cost not between", value1, value2, "steelCost");
            return (Criteria) this;
        }

        public Criteria andSteelDateIsNull() {
            addCriterion("steel_date is null");
            return (Criteria) this;
        }

        public Criteria andSteelDateIsNotNull() {
            addCriterion("steel_date is not null");
            return (Criteria) this;
        }

        public Criteria andSteelDateEqualTo(String value) {
            addCriterion("steel_date =", value, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateNotEqualTo(String value) {
            addCriterion("steel_date <>", value, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateGreaterThan(String value) {
            addCriterion("steel_date >", value, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateGreaterThanOrEqualTo(String value) {
            addCriterion("steel_date >=", value, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateLessThan(String value) {
            addCriterion("steel_date <", value, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateLessThanOrEqualTo(String value) {
            addCriterion("steel_date <=", value, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateLike(String value) {
            addCriterion("steel_date like", value, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateNotLike(String value) {
            addCriterion("steel_date not like", value, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateIn(List<String> values) {
            addCriterion("steel_date in", values, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateNotIn(List<String> values) {
            addCriterion("steel_date not in", values, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateBetween(String value1, String value2) {
            addCriterion("steel_date between", value1, value2, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelDateNotBetween(String value1, String value2) {
            addCriterion("steel_date not between", value1, value2, "steelDate");
            return (Criteria) this;
        }

        public Criteria andSteelPdfIsNull() {
            addCriterion("steel_pdf is null");
            return (Criteria) this;
        }

        public Criteria andSteelPdfIsNotNull() {
            addCriterion("steel_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andSteelPdfEqualTo(String value) {
            addCriterion("steel_pdf =", value, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfNotEqualTo(String value) {
            addCriterion("steel_pdf <>", value, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfGreaterThan(String value) {
            addCriterion("steel_pdf >", value, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfGreaterThanOrEqualTo(String value) {
            addCriterion("steel_pdf >=", value, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfLessThan(String value) {
            addCriterion("steel_pdf <", value, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfLessThanOrEqualTo(String value) {
            addCriterion("steel_pdf <=", value, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfLike(String value) {
            addCriterion("steel_pdf like", value, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfNotLike(String value) {
            addCriterion("steel_pdf not like", value, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfIn(List<String> values) {
            addCriterion("steel_pdf in", values, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfNotIn(List<String> values) {
            addCriterion("steel_pdf not in", values, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfBetween(String value1, String value2) {
            addCriterion("steel_pdf between", value1, value2, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andSteelPdfNotBetween(String value1, String value2) {
            addCriterion("steel_pdf not between", value1, value2, "steelPdf");
            return (Criteria) this;
        }

        public Criteria andGlueCostIsNull() {
            addCriterion("glue_cost is null");
            return (Criteria) this;
        }

        public Criteria andGlueCostIsNotNull() {
            addCriterion("glue_cost is not null");
            return (Criteria) this;
        }

        public Criteria andGlueCostEqualTo(BigDecimal value) {
            addCriterion("glue_cost =", value, "glueCost");
            return (Criteria) this;
        }

        public Criteria andGlueCostNotEqualTo(BigDecimal value) {
            addCriterion("glue_cost <>", value, "glueCost");
            return (Criteria) this;
        }

        public Criteria andGlueCostGreaterThan(BigDecimal value) {
            addCriterion("glue_cost >", value, "glueCost");
            return (Criteria) this;
        }

        public Criteria andGlueCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("glue_cost >=", value, "glueCost");
            return (Criteria) this;
        }

        public Criteria andGlueCostLessThan(BigDecimal value) {
            addCriterion("glue_cost <", value, "glueCost");
            return (Criteria) this;
        }

        public Criteria andGlueCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("glue_cost <=", value, "glueCost");
            return (Criteria) this;
        }

        public Criteria andGlueCostIn(List<BigDecimal> values) {
            addCriterion("glue_cost in", values, "glueCost");
            return (Criteria) this;
        }

        public Criteria andGlueCostNotIn(List<BigDecimal> values) {
            addCriterion("glue_cost not in", values, "glueCost");
            return (Criteria) this;
        }

        public Criteria andGlueCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("glue_cost between", value1, value2, "glueCost");
            return (Criteria) this;
        }

        public Criteria andGlueCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("glue_cost not between", value1, value2, "glueCost");
            return (Criteria) this;
        }

        public Criteria andGlueDateIsNull() {
            addCriterion("glue_date is null");
            return (Criteria) this;
        }

        public Criteria andGlueDateIsNotNull() {
            addCriterion("glue_date is not null");
            return (Criteria) this;
        }

        public Criteria andGlueDateEqualTo(String value) {
            addCriterion("glue_date =", value, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateNotEqualTo(String value) {
            addCriterion("glue_date <>", value, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateGreaterThan(String value) {
            addCriterion("glue_date >", value, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateGreaterThanOrEqualTo(String value) {
            addCriterion("glue_date >=", value, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateLessThan(String value) {
            addCriterion("glue_date <", value, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateLessThanOrEqualTo(String value) {
            addCriterion("glue_date <=", value, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateLike(String value) {
            addCriterion("glue_date like", value, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateNotLike(String value) {
            addCriterion("glue_date not like", value, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateIn(List<String> values) {
            addCriterion("glue_date in", values, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateNotIn(List<String> values) {
            addCriterion("glue_date not in", values, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateBetween(String value1, String value2) {
            addCriterion("glue_date between", value1, value2, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGlueDateNotBetween(String value1, String value2) {
            addCriterion("glue_date not between", value1, value2, "glueDate");
            return (Criteria) this;
        }

        public Criteria andGluePdfIsNull() {
            addCriterion("glue_pdf is null");
            return (Criteria) this;
        }

        public Criteria andGluePdfIsNotNull() {
            addCriterion("glue_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andGluePdfEqualTo(String value) {
            addCriterion("glue_pdf =", value, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfNotEqualTo(String value) {
            addCriterion("glue_pdf <>", value, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfGreaterThan(String value) {
            addCriterion("glue_pdf >", value, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfGreaterThanOrEqualTo(String value) {
            addCriterion("glue_pdf >=", value, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfLessThan(String value) {
            addCriterion("glue_pdf <", value, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfLessThanOrEqualTo(String value) {
            addCriterion("glue_pdf <=", value, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfLike(String value) {
            addCriterion("glue_pdf like", value, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfNotLike(String value) {
            addCriterion("glue_pdf not like", value, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfIn(List<String> values) {
            addCriterion("glue_pdf in", values, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfNotIn(List<String> values) {
            addCriterion("glue_pdf not in", values, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfBetween(String value1, String value2) {
            addCriterion("glue_pdf between", value1, value2, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andGluePdfNotBetween(String value1, String value2) {
            addCriterion("glue_pdf not between", value1, value2, "gluePdf");
            return (Criteria) this;
        }

        public Criteria andPaintCostIsNull() {
            addCriterion("paint_cost is null");
            return (Criteria) this;
        }

        public Criteria andPaintCostIsNotNull() {
            addCriterion("paint_cost is not null");
            return (Criteria) this;
        }

        public Criteria andPaintCostEqualTo(BigDecimal value) {
            addCriterion("paint_cost =", value, "paintCost");
            return (Criteria) this;
        }

        public Criteria andPaintCostNotEqualTo(BigDecimal value) {
            addCriterion("paint_cost <>", value, "paintCost");
            return (Criteria) this;
        }

        public Criteria andPaintCostGreaterThan(BigDecimal value) {
            addCriterion("paint_cost >", value, "paintCost");
            return (Criteria) this;
        }

        public Criteria andPaintCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("paint_cost >=", value, "paintCost");
            return (Criteria) this;
        }

        public Criteria andPaintCostLessThan(BigDecimal value) {
            addCriterion("paint_cost <", value, "paintCost");
            return (Criteria) this;
        }

        public Criteria andPaintCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("paint_cost <=", value, "paintCost");
            return (Criteria) this;
        }

        public Criteria andPaintCostIn(List<BigDecimal> values) {
            addCriterion("paint_cost in", values, "paintCost");
            return (Criteria) this;
        }

        public Criteria andPaintCostNotIn(List<BigDecimal> values) {
            addCriterion("paint_cost not in", values, "paintCost");
            return (Criteria) this;
        }

        public Criteria andPaintCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("paint_cost between", value1, value2, "paintCost");
            return (Criteria) this;
        }

        public Criteria andPaintCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("paint_cost not between", value1, value2, "paintCost");
            return (Criteria) this;
        }

        public Criteria andPaintDateIsNull() {
            addCriterion("paint_date is null");
            return (Criteria) this;
        }

        public Criteria andPaintDateIsNotNull() {
            addCriterion("paint_date is not null");
            return (Criteria) this;
        }

        public Criteria andPaintDateEqualTo(String value) {
            addCriterion("paint_date =", value, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateNotEqualTo(String value) {
            addCriterion("paint_date <>", value, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateGreaterThan(String value) {
            addCriterion("paint_date >", value, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateGreaterThanOrEqualTo(String value) {
            addCriterion("paint_date >=", value, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateLessThan(String value) {
            addCriterion("paint_date <", value, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateLessThanOrEqualTo(String value) {
            addCriterion("paint_date <=", value, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateLike(String value) {
            addCriterion("paint_date like", value, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateNotLike(String value) {
            addCriterion("paint_date not like", value, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateIn(List<String> values) {
            addCriterion("paint_date in", values, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateNotIn(List<String> values) {
            addCriterion("paint_date not in", values, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateBetween(String value1, String value2) {
            addCriterion("paint_date between", value1, value2, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintDateNotBetween(String value1, String value2) {
            addCriterion("paint_date not between", value1, value2, "paintDate");
            return (Criteria) this;
        }

        public Criteria andPaintPdfIsNull() {
            addCriterion("paint_pdf is null");
            return (Criteria) this;
        }

        public Criteria andPaintPdfIsNotNull() {
            addCriterion("paint_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andPaintPdfEqualTo(String value) {
            addCriterion("paint_pdf =", value, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfNotEqualTo(String value) {
            addCriterion("paint_pdf <>", value, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfGreaterThan(String value) {
            addCriterion("paint_pdf >", value, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfGreaterThanOrEqualTo(String value) {
            addCriterion("paint_pdf >=", value, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfLessThan(String value) {
            addCriterion("paint_pdf <", value, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfLessThanOrEqualTo(String value) {
            addCriterion("paint_pdf <=", value, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfLike(String value) {
            addCriterion("paint_pdf like", value, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfNotLike(String value) {
            addCriterion("paint_pdf not like", value, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfIn(List<String> values) {
            addCriterion("paint_pdf in", values, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfNotIn(List<String> values) {
            addCriterion("paint_pdf not in", values, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfBetween(String value1, String value2) {
            addCriterion("paint_pdf between", value1, value2, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andPaintPdfNotBetween(String value1, String value2) {
            addCriterion("paint_pdf not between", value1, value2, "paintPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostIsNull() {
            addCriterion("acrylic_cost is null");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostIsNotNull() {
            addCriterion("acrylic_cost is not null");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostEqualTo(BigDecimal value) {
            addCriterion("acrylic_cost =", value, "acrylicCost");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostNotEqualTo(BigDecimal value) {
            addCriterion("acrylic_cost <>", value, "acrylicCost");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostGreaterThan(BigDecimal value) {
            addCriterion("acrylic_cost >", value, "acrylicCost");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("acrylic_cost >=", value, "acrylicCost");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostLessThan(BigDecimal value) {
            addCriterion("acrylic_cost <", value, "acrylicCost");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("acrylic_cost <=", value, "acrylicCost");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostIn(List<BigDecimal> values) {
            addCriterion("acrylic_cost in", values, "acrylicCost");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostNotIn(List<BigDecimal> values) {
            addCriterion("acrylic_cost not in", values, "acrylicCost");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("acrylic_cost between", value1, value2, "acrylicCost");
            return (Criteria) this;
        }

        public Criteria andAcrylicCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("acrylic_cost not between", value1, value2, "acrylicCost");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateIsNull() {
            addCriterion("acrylic_date is null");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateIsNotNull() {
            addCriterion("acrylic_date is not null");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateEqualTo(String value) {
            addCriterion("acrylic_date =", value, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateNotEqualTo(String value) {
            addCriterion("acrylic_date <>", value, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateGreaterThan(String value) {
            addCriterion("acrylic_date >", value, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateGreaterThanOrEqualTo(String value) {
            addCriterion("acrylic_date >=", value, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateLessThan(String value) {
            addCriterion("acrylic_date <", value, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateLessThanOrEqualTo(String value) {
            addCriterion("acrylic_date <=", value, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateLike(String value) {
            addCriterion("acrylic_date like", value, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateNotLike(String value) {
            addCriterion("acrylic_date not like", value, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateIn(List<String> values) {
            addCriterion("acrylic_date in", values, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateNotIn(List<String> values) {
            addCriterion("acrylic_date not in", values, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateBetween(String value1, String value2) {
            addCriterion("acrylic_date between", value1, value2, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicDateNotBetween(String value1, String value2) {
            addCriterion("acrylic_date not between", value1, value2, "acrylicDate");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfIsNull() {
            addCriterion("acrylic_pdf is null");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfIsNotNull() {
            addCriterion("acrylic_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfEqualTo(String value) {
            addCriterion("acrylic_pdf =", value, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfNotEqualTo(String value) {
            addCriterion("acrylic_pdf <>", value, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfGreaterThan(String value) {
            addCriterion("acrylic_pdf >", value, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfGreaterThanOrEqualTo(String value) {
            addCriterion("acrylic_pdf >=", value, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfLessThan(String value) {
            addCriterion("acrylic_pdf <", value, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfLessThanOrEqualTo(String value) {
            addCriterion("acrylic_pdf <=", value, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfLike(String value) {
            addCriterion("acrylic_pdf like", value, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfNotLike(String value) {
            addCriterion("acrylic_pdf not like", value, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfIn(List<String> values) {
            addCriterion("acrylic_pdf in", values, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfNotIn(List<String> values) {
            addCriterion("acrylic_pdf not in", values, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfBetween(String value1, String value2) {
            addCriterion("acrylic_pdf between", value1, value2, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andAcrylicPdfNotBetween(String value1, String value2) {
            addCriterion("acrylic_pdf not between", value1, value2, "acrylicPdf");
            return (Criteria) this;
        }

        public Criteria andChassisCostIsNull() {
            addCriterion("chassis_cost is null");
            return (Criteria) this;
        }

        public Criteria andChassisCostIsNotNull() {
            addCriterion("chassis_cost is not null");
            return (Criteria) this;
        }

        public Criteria andChassisCostEqualTo(BigDecimal value) {
            addCriterion("chassis_cost =", value, "chassisCost");
            return (Criteria) this;
        }

        public Criteria andChassisCostNotEqualTo(BigDecimal value) {
            addCriterion("chassis_cost <>", value, "chassisCost");
            return (Criteria) this;
        }

        public Criteria andChassisCostGreaterThan(BigDecimal value) {
            addCriterion("chassis_cost >", value, "chassisCost");
            return (Criteria) this;
        }

        public Criteria andChassisCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("chassis_cost >=", value, "chassisCost");
            return (Criteria) this;
        }

        public Criteria andChassisCostLessThan(BigDecimal value) {
            addCriterion("chassis_cost <", value, "chassisCost");
            return (Criteria) this;
        }

        public Criteria andChassisCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("chassis_cost <=", value, "chassisCost");
            return (Criteria) this;
        }

        public Criteria andChassisCostIn(List<BigDecimal> values) {
            addCriterion("chassis_cost in", values, "chassisCost");
            return (Criteria) this;
        }

        public Criteria andChassisCostNotIn(List<BigDecimal> values) {
            addCriterion("chassis_cost not in", values, "chassisCost");
            return (Criteria) this;
        }

        public Criteria andChassisCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("chassis_cost between", value1, value2, "chassisCost");
            return (Criteria) this;
        }

        public Criteria andChassisCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("chassis_cost not between", value1, value2, "chassisCost");
            return (Criteria) this;
        }

        public Criteria andChassisDateIsNull() {
            addCriterion("chassis_date is null");
            return (Criteria) this;
        }

        public Criteria andChassisDateIsNotNull() {
            addCriterion("chassis_date is not null");
            return (Criteria) this;
        }

        public Criteria andChassisDateEqualTo(String value) {
            addCriterion("chassis_date =", value, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateNotEqualTo(String value) {
            addCriterion("chassis_date <>", value, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateGreaterThan(String value) {
            addCriterion("chassis_date >", value, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateGreaterThanOrEqualTo(String value) {
            addCriterion("chassis_date >=", value, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateLessThan(String value) {
            addCriterion("chassis_date <", value, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateLessThanOrEqualTo(String value) {
            addCriterion("chassis_date <=", value, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateLike(String value) {
            addCriterion("chassis_date like", value, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateNotLike(String value) {
            addCriterion("chassis_date not like", value, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateIn(List<String> values) {
            addCriterion("chassis_date in", values, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateNotIn(List<String> values) {
            addCriterion("chassis_date not in", values, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateBetween(String value1, String value2) {
            addCriterion("chassis_date between", value1, value2, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisDateNotBetween(String value1, String value2) {
            addCriterion("chassis_date not between", value1, value2, "chassisDate");
            return (Criteria) this;
        }

        public Criteria andChassisPdfIsNull() {
            addCriterion("chassis_pdf is null");
            return (Criteria) this;
        }

        public Criteria andChassisPdfIsNotNull() {
            addCriterion("chassis_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andChassisPdfEqualTo(String value) {
            addCriterion("chassis_pdf =", value, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfNotEqualTo(String value) {
            addCriterion("chassis_pdf <>", value, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfGreaterThan(String value) {
            addCriterion("chassis_pdf >", value, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfGreaterThanOrEqualTo(String value) {
            addCriterion("chassis_pdf >=", value, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfLessThan(String value) {
            addCriterion("chassis_pdf <", value, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfLessThanOrEqualTo(String value) {
            addCriterion("chassis_pdf <=", value, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfLike(String value) {
            addCriterion("chassis_pdf like", value, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfNotLike(String value) {
            addCriterion("chassis_pdf not like", value, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfIn(List<String> values) {
            addCriterion("chassis_pdf in", values, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfNotIn(List<String> values) {
            addCriterion("chassis_pdf not in", values, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfBetween(String value1, String value2) {
            addCriterion("chassis_pdf between", value1, value2, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andChassisPdfNotBetween(String value1, String value2) {
            addCriterion("chassis_pdf not between", value1, value2, "chassisPdf");
            return (Criteria) this;
        }

        public Criteria andPencilCostIsNull() {
            addCriterion("pencil_cost is null");
            return (Criteria) this;
        }

        public Criteria andPencilCostIsNotNull() {
            addCriterion("pencil_cost is not null");
            return (Criteria) this;
        }

        public Criteria andPencilCostEqualTo(BigDecimal value) {
            addCriterion("pencil_cost =", value, "pencilCost");
            return (Criteria) this;
        }

        public Criteria andPencilCostNotEqualTo(BigDecimal value) {
            addCriterion("pencil_cost <>", value, "pencilCost");
            return (Criteria) this;
        }

        public Criteria andPencilCostGreaterThan(BigDecimal value) {
            addCriterion("pencil_cost >", value, "pencilCost");
            return (Criteria) this;
        }

        public Criteria andPencilCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pencil_cost >=", value, "pencilCost");
            return (Criteria) this;
        }

        public Criteria andPencilCostLessThan(BigDecimal value) {
            addCriterion("pencil_cost <", value, "pencilCost");
            return (Criteria) this;
        }

        public Criteria andPencilCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pencil_cost <=", value, "pencilCost");
            return (Criteria) this;
        }

        public Criteria andPencilCostIn(List<BigDecimal> values) {
            addCriterion("pencil_cost in", values, "pencilCost");
            return (Criteria) this;
        }

        public Criteria andPencilCostNotIn(List<BigDecimal> values) {
            addCriterion("pencil_cost not in", values, "pencilCost");
            return (Criteria) this;
        }

        public Criteria andPencilCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pencil_cost between", value1, value2, "pencilCost");
            return (Criteria) this;
        }

        public Criteria andPencilCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pencil_cost not between", value1, value2, "pencilCost");
            return (Criteria) this;
        }

        public Criteria andPencilDateIsNull() {
            addCriterion("pencil_date is null");
            return (Criteria) this;
        }

        public Criteria andPencilDateIsNotNull() {
            addCriterion("pencil_date is not null");
            return (Criteria) this;
        }

        public Criteria andPencilDateEqualTo(String value) {
            addCriterion("pencil_date =", value, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateNotEqualTo(String value) {
            addCriterion("pencil_date <>", value, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateGreaterThan(String value) {
            addCriterion("pencil_date >", value, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateGreaterThanOrEqualTo(String value) {
            addCriterion("pencil_date >=", value, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateLessThan(String value) {
            addCriterion("pencil_date <", value, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateLessThanOrEqualTo(String value) {
            addCriterion("pencil_date <=", value, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateLike(String value) {
            addCriterion("pencil_date like", value, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateNotLike(String value) {
            addCriterion("pencil_date not like", value, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateIn(List<String> values) {
            addCriterion("pencil_date in", values, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateNotIn(List<String> values) {
            addCriterion("pencil_date not in", values, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateBetween(String value1, String value2) {
            addCriterion("pencil_date between", value1, value2, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilDateNotBetween(String value1, String value2) {
            addCriterion("pencil_date not between", value1, value2, "pencilDate");
            return (Criteria) this;
        }

        public Criteria andPencilPdfIsNull() {
            addCriterion("pencil_pdf is null");
            return (Criteria) this;
        }

        public Criteria andPencilPdfIsNotNull() {
            addCriterion("pencil_pdf is not null");
            return (Criteria) this;
        }

        public Criteria andPencilPdfEqualTo(String value) {
            addCriterion("pencil_pdf =", value, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfNotEqualTo(String value) {
            addCriterion("pencil_pdf <>", value, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfGreaterThan(String value) {
            addCriterion("pencil_pdf >", value, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfGreaterThanOrEqualTo(String value) {
            addCriterion("pencil_pdf >=", value, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfLessThan(String value) {
            addCriterion("pencil_pdf <", value, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfLessThanOrEqualTo(String value) {
            addCriterion("pencil_pdf <=", value, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfLike(String value) {
            addCriterion("pencil_pdf like", value, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfNotLike(String value) {
            addCriterion("pencil_pdf not like", value, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfIn(List<String> values) {
            addCriterion("pencil_pdf in", values, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfNotIn(List<String> values) {
            addCriterion("pencil_pdf not in", values, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfBetween(String value1, String value2) {
            addCriterion("pencil_pdf between", value1, value2, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andPencilPdfNotBetween(String value1, String value2) {
            addCriterion("pencil_pdf not between", value1, value2, "pencilPdf");
            return (Criteria) this;
        }

        public Criteria andTotalCostIsNull() {
            addCriterion("total_cost is null");
            return (Criteria) this;
        }

        public Criteria andTotalCostIsNotNull() {
            addCriterion("total_cost is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCostEqualTo(BigDecimal value) {
            addCriterion("total_cost =", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostNotEqualTo(BigDecimal value) {
            addCriterion("total_cost <>", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostGreaterThan(BigDecimal value) {
            addCriterion("total_cost >", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_cost >=", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostLessThan(BigDecimal value) {
            addCriterion("total_cost <", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_cost <=", value, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostIn(List<BigDecimal> values) {
            addCriterion("total_cost in", values, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostNotIn(List<BigDecimal> values) {
            addCriterion("total_cost not in", values, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_cost between", value1, value2, "totalCost");
            return (Criteria) this;
        }

        public Criteria andTotalCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_cost not between", value1, value2, "totalCost");
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