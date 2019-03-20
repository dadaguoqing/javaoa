package com.hj.oa.bean;

import java.util.ArrayList;
import java.util.List;

public class MerchandiseApplyDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MerchandiseApplyDetailExample() {
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

        public Criteria andProductTypeIsNull() {
            addCriterion("product_type is null");
            return (Criteria) this;
        }

        public Criteria andProductTypeIsNotNull() {
            addCriterion("product_type is not null");
            return (Criteria) this;
        }

        public Criteria andProductTypeEqualTo(Integer value) {
            addCriterion("product_type =", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotEqualTo(Integer value) {
            addCriterion("product_type <>", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeGreaterThan(Integer value) {
            addCriterion("product_type >", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("product_type >=", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLessThan(Integer value) {
            addCriterion("product_type <", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLessThanOrEqualTo(Integer value) {
            addCriterion("product_type <=", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeIn(List<Integer> values) {
            addCriterion("product_type in", values, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotIn(List<Integer> values) {
            addCriterion("product_type not in", values, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeBetween(Integer value1, Integer value2) {
            addCriterion("product_type between", value1, value2, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("product_type not between", value1, value2, "productType");
            return (Criteria) this;
        }

        public Criteria andProductModelIsNull() {
            addCriterion("product_model is null");
            return (Criteria) this;
        }

        public Criteria andProductModelIsNotNull() {
            addCriterion("product_model is not null");
            return (Criteria) this;
        }

        public Criteria andProductModelEqualTo(String value) {
            addCriterion("product_model =", value, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelNotEqualTo(String value) {
            addCriterion("product_model <>", value, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelGreaterThan(String value) {
            addCriterion("product_model >", value, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelGreaterThanOrEqualTo(String value) {
            addCriterion("product_model >=", value, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelLessThan(String value) {
            addCriterion("product_model <", value, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelLessThanOrEqualTo(String value) {
            addCriterion("product_model <=", value, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelLike(String value) {
            addCriterion("product_model like", value, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelNotLike(String value) {
            addCriterion("product_model not like", value, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelIn(List<String> values) {
            addCriterion("product_model in", values, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelNotIn(List<String> values) {
            addCriterion("product_model not in", values, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelBetween(String value1, String value2) {
            addCriterion("product_model between", value1, value2, "productModel");
            return (Criteria) this;
        }

        public Criteria andProductModelNotBetween(String value1, String value2) {
            addCriterion("product_model not between", value1, value2, "productModel");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNull() {
            addCriterion("product_code is null");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNotNull() {
            addCriterion("product_code is not null");
            return (Criteria) this;
        }

        public Criteria andProductCodeEqualTo(String value) {
            addCriterion("product_code =", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotEqualTo(String value) {
            addCriterion("product_code <>", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThan(String value) {
            addCriterion("product_code >", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThanOrEqualTo(String value) {
            addCriterion("product_code >=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThan(String value) {
            addCriterion("product_code <", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThanOrEqualTo(String value) {
            addCriterion("product_code <=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLike(String value) {
            addCriterion("product_code like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotLike(String value) {
            addCriterion("product_code not like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeIn(List<String> values) {
            addCriterion("product_code in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotIn(List<String> values) {
            addCriterion("product_code not in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeBetween(String value1, String value2) {
            addCriterion("product_code between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotBetween(String value1, String value2) {
            addCriterion("product_code not between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeIsNull() {
            addCriterion("week_code is null");
            return (Criteria) this;
        }

        public Criteria andWeekCodeIsNotNull() {
            addCriterion("week_code is not null");
            return (Criteria) this;
        }

        public Criteria andWeekCodeEqualTo(String value) {
            addCriterion("week_code =", value, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeNotEqualTo(String value) {
            addCriterion("week_code <>", value, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeGreaterThan(String value) {
            addCriterion("week_code >", value, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeGreaterThanOrEqualTo(String value) {
            addCriterion("week_code >=", value, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeLessThan(String value) {
            addCriterion("week_code <", value, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeLessThanOrEqualTo(String value) {
            addCriterion("week_code <=", value, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeLike(String value) {
            addCriterion("week_code like", value, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeNotLike(String value) {
            addCriterion("week_code not like", value, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeIn(List<String> values) {
            addCriterion("week_code in", values, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeNotIn(List<String> values) {
            addCriterion("week_code not in", values, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeBetween(String value1, String value2) {
            addCriterion("week_code between", value1, value2, "weekCode");
            return (Criteria) this;
        }

        public Criteria andWeekCodeNotBetween(String value1, String value2) {
            addCriterion("week_code not between", value1, value2, "weekCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeIsNull() {
            addCriterion("package_code is null");
            return (Criteria) this;
        }

        public Criteria andPackageCodeIsNotNull() {
            addCriterion("package_code is not null");
            return (Criteria) this;
        }

        public Criteria andPackageCodeEqualTo(String value) {
            addCriterion("package_code =", value, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeNotEqualTo(String value) {
            addCriterion("package_code <>", value, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeGreaterThan(String value) {
            addCriterion("package_code >", value, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeGreaterThanOrEqualTo(String value) {
            addCriterion("package_code >=", value, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeLessThan(String value) {
            addCriterion("package_code <", value, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeLessThanOrEqualTo(String value) {
            addCriterion("package_code <=", value, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeLike(String value) {
            addCriterion("package_code like", value, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeNotLike(String value) {
            addCriterion("package_code not like", value, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeIn(List<String> values) {
            addCriterion("package_code in", values, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeNotIn(List<String> values) {
            addCriterion("package_code not in", values, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeBetween(String value1, String value2) {
            addCriterion("package_code between", value1, value2, "packageCode");
            return (Criteria) this;
        }

        public Criteria andPackageCodeNotBetween(String value1, String value2) {
            addCriterion("package_code not between", value1, value2, "packageCode");
            return (Criteria) this;
        }

        public Criteria andInboundReasonIsNull() {
            addCriterion("inbound_reason is null");
            return (Criteria) this;
        }

        public Criteria andInboundReasonIsNotNull() {
            addCriterion("inbound_reason is not null");
            return (Criteria) this;
        }

        public Criteria andInboundReasonEqualTo(String value) {
            addCriterion("inbound_reason =", value, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonNotEqualTo(String value) {
            addCriterion("inbound_reason <>", value, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonGreaterThan(String value) {
            addCriterion("inbound_reason >", value, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonGreaterThanOrEqualTo(String value) {
            addCriterion("inbound_reason >=", value, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonLessThan(String value) {
            addCriterion("inbound_reason <", value, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonLessThanOrEqualTo(String value) {
            addCriterion("inbound_reason <=", value, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonLike(String value) {
            addCriterion("inbound_reason like", value, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonNotLike(String value) {
            addCriterion("inbound_reason not like", value, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonIn(List<String> values) {
            addCriterion("inbound_reason in", values, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonNotIn(List<String> values) {
            addCriterion("inbound_reason not in", values, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonBetween(String value1, String value2) {
            addCriterion("inbound_reason between", value1, value2, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andInboundReasonNotBetween(String value1, String value2) {
            addCriterion("inbound_reason not between", value1, value2, "inboundReason");
            return (Criteria) this;
        }

        public Criteria andSupplierIsNull() {
            addCriterion("supplier is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIsNotNull() {
            addCriterion("supplier is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierEqualTo(String value) {
            addCriterion("supplier =", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualTo(String value) {
            addCriterion("supplier <>", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThan(String value) {
            addCriterion("supplier >", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualTo(String value) {
            addCriterion("supplier >=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThan(String value) {
            addCriterion("supplier <", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualTo(String value) {
            addCriterion("supplier <=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLike(String value) {
            addCriterion("supplier like", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotLike(String value) {
            addCriterion("supplier not like", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierIn(List<String> values) {
            addCriterion("supplier in", values, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotIn(List<String> values) {
            addCriterion("supplier not in", values, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierBetween(String value1, String value2) {
            addCriterion("supplier between", value1, value2, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotBetween(String value1, String value2) {
            addCriterion("supplier not between", value1, value2, "supplier");
            return (Criteria) this;
        }

        public Criteria andInboundSourceIsNull() {
            addCriterion("inbound_source is null");
            return (Criteria) this;
        }

        public Criteria andInboundSourceIsNotNull() {
            addCriterion("inbound_source is not null");
            return (Criteria) this;
        }

        public Criteria andInboundSourceEqualTo(String value) {
            addCriterion("inbound_source =", value, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceNotEqualTo(String value) {
            addCriterion("inbound_source <>", value, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceGreaterThan(String value) {
            addCriterion("inbound_source >", value, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceGreaterThanOrEqualTo(String value) {
            addCriterion("inbound_source >=", value, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceLessThan(String value) {
            addCriterion("inbound_source <", value, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceLessThanOrEqualTo(String value) {
            addCriterion("inbound_source <=", value, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceLike(String value) {
            addCriterion("inbound_source like", value, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceNotLike(String value) {
            addCriterion("inbound_source not like", value, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceIn(List<String> values) {
            addCriterion("inbound_source in", values, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceNotIn(List<String> values) {
            addCriterion("inbound_source not in", values, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceBetween(String value1, String value2) {
            addCriterion("inbound_source between", value1, value2, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andInboundSourceNotBetween(String value1, String value2) {
            addCriterion("inbound_source not between", value1, value2, "inboundSource");
            return (Criteria) this;
        }

        public Criteria andLocationIsNull() {
            addCriterion("location is null");
            return (Criteria) this;
        }

        public Criteria andLocationIsNotNull() {
            addCriterion("location is not null");
            return (Criteria) this;
        }

        public Criteria andLocationEqualTo(String value) {
            addCriterion("location =", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotEqualTo(String value) {
            addCriterion("location <>", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThan(String value) {
            addCriterion("location >", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThanOrEqualTo(String value) {
            addCriterion("location >=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThan(String value) {
            addCriterion("location <", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThanOrEqualTo(String value) {
            addCriterion("location <=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLike(String value) {
            addCriterion("location like", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotLike(String value) {
            addCriterion("location not like", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationIn(List<String> values) {
            addCriterion("location in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotIn(List<String> values) {
            addCriterion("location not in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationBetween(String value1, String value2) {
            addCriterion("location between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotBetween(String value1, String value2) {
            addCriterion("location not between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityIsNull() {
            addCriterion("suplus_quantity is null");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityIsNotNull() {
            addCriterion("suplus_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityEqualTo(Integer value) {
            addCriterion("suplus_quantity =", value, "suplusQuantity");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityNotEqualTo(Integer value) {
            addCriterion("suplus_quantity <>", value, "suplusQuantity");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityGreaterThan(Integer value) {
            addCriterion("suplus_quantity >", value, "suplusQuantity");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("suplus_quantity >=", value, "suplusQuantity");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityLessThan(Integer value) {
            addCriterion("suplus_quantity <", value, "suplusQuantity");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("suplus_quantity <=", value, "suplusQuantity");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityIn(List<Integer> values) {
            addCriterion("suplus_quantity in", values, "suplusQuantity");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityNotIn(List<Integer> values) {
            addCriterion("suplus_quantity not in", values, "suplusQuantity");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityBetween(Integer value1, Integer value2) {
            addCriterion("suplus_quantity between", value1, value2, "suplusQuantity");
            return (Criteria) this;
        }

        public Criteria andSuplusQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("suplus_quantity not between", value1, value2, "suplusQuantity");
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