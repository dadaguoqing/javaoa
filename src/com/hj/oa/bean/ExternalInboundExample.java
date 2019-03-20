package com.hj.oa.bean;

import java.util.ArrayList;
import java.util.List;

public class ExternalInboundExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExternalInboundExample() {
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

        public Criteria andInboundNumIsNull() {
            addCriterion("inbound_num is null");
            return (Criteria) this;
        }

        public Criteria andInboundNumIsNotNull() {
            addCriterion("inbound_num is not null");
            return (Criteria) this;
        }

        public Criteria andInboundNumEqualTo(Integer value) {
            addCriterion("inbound_num =", value, "inboundNum");
            return (Criteria) this;
        }

        public Criteria andInboundNumNotEqualTo(Integer value) {
            addCriterion("inbound_num <>", value, "inboundNum");
            return (Criteria) this;
        }

        public Criteria andInboundNumGreaterThan(Integer value) {
            addCriterion("inbound_num >", value, "inboundNum");
            return (Criteria) this;
        }

        public Criteria andInboundNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("inbound_num >=", value, "inboundNum");
            return (Criteria) this;
        }

        public Criteria andInboundNumLessThan(Integer value) {
            addCriterion("inbound_num <", value, "inboundNum");
            return (Criteria) this;
        }

        public Criteria andInboundNumLessThanOrEqualTo(Integer value) {
            addCriterion("inbound_num <=", value, "inboundNum");
            return (Criteria) this;
        }

        public Criteria andInboundNumIn(List<Integer> values) {
            addCriterion("inbound_num in", values, "inboundNum");
            return (Criteria) this;
        }

        public Criteria andInboundNumNotIn(List<Integer> values) {
            addCriterion("inbound_num not in", values, "inboundNum");
            return (Criteria) this;
        }

        public Criteria andInboundNumBetween(Integer value1, Integer value2) {
            addCriterion("inbound_num between", value1, value2, "inboundNum");
            return (Criteria) this;
        }

        public Criteria andInboundNumNotBetween(Integer value1, Integer value2) {
            addCriterion("inbound_num not between", value1, value2, "inboundNum");
            return (Criteria) this;
        }

        public Criteria andInboundDateIsNull() {
            addCriterion("inbound_date is null");
            return (Criteria) this;
        }

        public Criteria andInboundDateIsNotNull() {
            addCriterion("inbound_date is not null");
            return (Criteria) this;
        }

        public Criteria andInboundDateEqualTo(String value) {
            addCriterion("inbound_date =", value, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateNotEqualTo(String value) {
            addCriterion("inbound_date <>", value, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateGreaterThan(String value) {
            addCriterion("inbound_date >", value, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateGreaterThanOrEqualTo(String value) {
            addCriterion("inbound_date >=", value, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateLessThan(String value) {
            addCriterion("inbound_date <", value, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateLessThanOrEqualTo(String value) {
            addCriterion("inbound_date <=", value, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateLike(String value) {
            addCriterion("inbound_date like", value, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateNotLike(String value) {
            addCriterion("inbound_date not like", value, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateIn(List<String> values) {
            addCriterion("inbound_date in", values, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateNotIn(List<String> values) {
            addCriterion("inbound_date not in", values, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateBetween(String value1, String value2) {
            addCriterion("inbound_date between", value1, value2, "inboundDate");
            return (Criteria) this;
        }

        public Criteria andInboundDateNotBetween(String value1, String value2) {
            addCriterion("inbound_date not between", value1, value2, "inboundDate");
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

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
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