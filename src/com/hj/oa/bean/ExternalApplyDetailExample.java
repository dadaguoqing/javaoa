package com.hj.oa.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExternalApplyDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExternalApplyDetailExample() {
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

        public Criteria andPcbDescriptionIsNull() {
            addCriterion("pcb_description is null");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionIsNotNull() {
            addCriterion("pcb_description is not null");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionEqualTo(String value) {
            addCriterion("pcb_description =", value, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionNotEqualTo(String value) {
            addCriterion("pcb_description <>", value, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionGreaterThan(String value) {
            addCriterion("pcb_description >", value, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("pcb_description >=", value, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionLessThan(String value) {
            addCriterion("pcb_description <", value, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionLessThanOrEqualTo(String value) {
            addCriterion("pcb_description <=", value, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionLike(String value) {
            addCriterion("pcb_description like", value, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionNotLike(String value) {
            addCriterion("pcb_description not like", value, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionIn(List<String> values) {
            addCriterion("pcb_description in", values, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionNotIn(List<String> values) {
            addCriterion("pcb_description not in", values, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionBetween(String value1, String value2) {
            addCriterion("pcb_description between", value1, value2, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbDescriptionNotBetween(String value1, String value2) {
            addCriterion("pcb_description not between", value1, value2, "pcbDescription");
            return (Criteria) this;
        }

        public Criteria andPcbNumIsNull() {
            addCriterion("pcb_num is null");
            return (Criteria) this;
        }

        public Criteria andPcbNumIsNotNull() {
            addCriterion("pcb_num is not null");
            return (Criteria) this;
        }

        public Criteria andPcbNumEqualTo(Integer value) {
            addCriterion("pcb_num =", value, "pcbNum");
            return (Criteria) this;
        }

        public Criteria andPcbNumNotEqualTo(Integer value) {
            addCriterion("pcb_num <>", value, "pcbNum");
            return (Criteria) this;
        }

        public Criteria andPcbNumGreaterThan(Integer value) {
            addCriterion("pcb_num >", value, "pcbNum");
            return (Criteria) this;
        }

        public Criteria andPcbNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("pcb_num >=", value, "pcbNum");
            return (Criteria) this;
        }

        public Criteria andPcbNumLessThan(Integer value) {
            addCriterion("pcb_num <", value, "pcbNum");
            return (Criteria) this;
        }

        public Criteria andPcbNumLessThanOrEqualTo(Integer value) {
            addCriterion("pcb_num <=", value, "pcbNum");
            return (Criteria) this;
        }

        public Criteria andPcbNumIn(List<Integer> values) {
            addCriterion("pcb_num in", values, "pcbNum");
            return (Criteria) this;
        }

        public Criteria andPcbNumNotIn(List<Integer> values) {
            addCriterion("pcb_num not in", values, "pcbNum");
            return (Criteria) this;
        }

        public Criteria andPcbNumBetween(Integer value1, Integer value2) {
            addCriterion("pcb_num between", value1, value2, "pcbNum");
            return (Criteria) this;
        }

        public Criteria andPcbNumNotBetween(Integer value1, Integer value2) {
            addCriterion("pcb_num not between", value1, value2, "pcbNum");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptIsNull() {
            addCriterion("impedance_descript is null");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptIsNotNull() {
            addCriterion("impedance_descript is not null");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptEqualTo(String value) {
            addCriterion("impedance_descript =", value, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptNotEqualTo(String value) {
            addCriterion("impedance_descript <>", value, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptGreaterThan(String value) {
            addCriterion("impedance_descript >", value, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptGreaterThanOrEqualTo(String value) {
            addCriterion("impedance_descript >=", value, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptLessThan(String value) {
            addCriterion("impedance_descript <", value, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptLessThanOrEqualTo(String value) {
            addCriterion("impedance_descript <=", value, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptLike(String value) {
            addCriterion("impedance_descript like", value, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptNotLike(String value) {
            addCriterion("impedance_descript not like", value, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptIn(List<String> values) {
            addCriterion("impedance_descript in", values, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptNotIn(List<String> values) {
            addCriterion("impedance_descript not in", values, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptBetween(String value1, String value2) {
            addCriterion("impedance_descript between", value1, value2, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andImpedanceDescriptNotBetween(String value1, String value2) {
            addCriterion("impedance_descript not between", value1, value2, "impedanceDescript");
            return (Criteria) this;
        }

        public Criteria andPcbGerberIsNull() {
            addCriterion("pcb_gerber is null");
            return (Criteria) this;
        }

        public Criteria andPcbGerberIsNotNull() {
            addCriterion("pcb_gerber is not null");
            return (Criteria) this;
        }

        public Criteria andPcbGerberEqualTo(String value) {
            addCriterion("pcb_gerber =", value, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberNotEqualTo(String value) {
            addCriterion("pcb_gerber <>", value, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberGreaterThan(String value) {
            addCriterion("pcb_gerber >", value, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberGreaterThanOrEqualTo(String value) {
            addCriterion("pcb_gerber >=", value, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberLessThan(String value) {
            addCriterion("pcb_gerber <", value, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberLessThanOrEqualTo(String value) {
            addCriterion("pcb_gerber <=", value, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberLike(String value) {
            addCriterion("pcb_gerber like", value, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberNotLike(String value) {
            addCriterion("pcb_gerber not like", value, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberIn(List<String> values) {
            addCriterion("pcb_gerber in", values, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberNotIn(List<String> values) {
            addCriterion("pcb_gerber not in", values, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberBetween(String value1, String value2) {
            addCriterion("pcb_gerber between", value1, value2, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andPcbGerberNotBetween(String value1, String value2) {
            addCriterion("pcb_gerber not between", value1, value2, "pcbGerber");
            return (Criteria) this;
        }

        public Criteria andWeldNumIsNull() {
            addCriterion("weld_num is null");
            return (Criteria) this;
        }

        public Criteria andWeldNumIsNotNull() {
            addCriterion("weld_num is not null");
            return (Criteria) this;
        }

        public Criteria andWeldNumEqualTo(Integer value) {
            addCriterion("weld_num =", value, "weldNum");
            return (Criteria) this;
        }

        public Criteria andWeldNumNotEqualTo(Integer value) {
            addCriterion("weld_num <>", value, "weldNum");
            return (Criteria) this;
        }

        public Criteria andWeldNumGreaterThan(Integer value) {
            addCriterion("weld_num >", value, "weldNum");
            return (Criteria) this;
        }

        public Criteria andWeldNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("weld_num >=", value, "weldNum");
            return (Criteria) this;
        }

        public Criteria andWeldNumLessThan(Integer value) {
            addCriterion("weld_num <", value, "weldNum");
            return (Criteria) this;
        }

        public Criteria andWeldNumLessThanOrEqualTo(Integer value) {
            addCriterion("weld_num <=", value, "weldNum");
            return (Criteria) this;
        }

        public Criteria andWeldNumIn(List<Integer> values) {
            addCriterion("weld_num in", values, "weldNum");
            return (Criteria) this;
        }

        public Criteria andWeldNumNotIn(List<Integer> values) {
            addCriterion("weld_num not in", values, "weldNum");
            return (Criteria) this;
        }

        public Criteria andWeldNumBetween(Integer value1, Integer value2) {
            addCriterion("weld_num between", value1, value2, "weldNum");
            return (Criteria) this;
        }

        public Criteria andWeldNumNotBetween(Integer value1, Integer value2) {
            addCriterion("weld_num not between", value1, value2, "weldNum");
            return (Criteria) this;
        }

        public Criteria andWeldBomIsNull() {
            addCriterion("weld_bom is null");
            return (Criteria) this;
        }

        public Criteria andWeldBomIsNotNull() {
            addCriterion("weld_bom is not null");
            return (Criteria) this;
        }

        public Criteria andWeldBomEqualTo(String value) {
            addCriterion("weld_bom =", value, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomNotEqualTo(String value) {
            addCriterion("weld_bom <>", value, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomGreaterThan(String value) {
            addCriterion("weld_bom >", value, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomGreaterThanOrEqualTo(String value) {
            addCriterion("weld_bom >=", value, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomLessThan(String value) {
            addCriterion("weld_bom <", value, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomLessThanOrEqualTo(String value) {
            addCriterion("weld_bom <=", value, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomLike(String value) {
            addCriterion("weld_bom like", value, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomNotLike(String value) {
            addCriterion("weld_bom not like", value, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomIn(List<String> values) {
            addCriterion("weld_bom in", values, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomNotIn(List<String> values) {
            addCriterion("weld_bom not in", values, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomBetween(String value1, String value2) {
            addCriterion("weld_bom between", value1, value2, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldBomNotBetween(String value1, String value2) {
            addCriterion("weld_bom not between", value1, value2, "weldBom");
            return (Criteria) this;
        }

        public Criteria andWeldGerberIsNull() {
            addCriterion("weld_gerber is null");
            return (Criteria) this;
        }

        public Criteria andWeldGerberIsNotNull() {
            addCriterion("weld_gerber is not null");
            return (Criteria) this;
        }

        public Criteria andWeldGerberEqualTo(String value) {
            addCriterion("weld_gerber =", value, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberNotEqualTo(String value) {
            addCriterion("weld_gerber <>", value, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberGreaterThan(String value) {
            addCriterion("weld_gerber >", value, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberGreaterThanOrEqualTo(String value) {
            addCriterion("weld_gerber >=", value, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberLessThan(String value) {
            addCriterion("weld_gerber <", value, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberLessThanOrEqualTo(String value) {
            addCriterion("weld_gerber <=", value, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberLike(String value) {
            addCriterion("weld_gerber like", value, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberNotLike(String value) {
            addCriterion("weld_gerber not like", value, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberIn(List<String> values) {
            addCriterion("weld_gerber in", values, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberNotIn(List<String> values) {
            addCriterion("weld_gerber not in", values, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberBetween(String value1, String value2) {
            addCriterion("weld_gerber between", value1, value2, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldGerberNotBetween(String value1, String value2) {
            addCriterion("weld_gerber not between", value1, value2, "weldGerber");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateIsNull() {
            addCriterion("weld_coordinate is null");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateIsNotNull() {
            addCriterion("weld_coordinate is not null");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateEqualTo(String value) {
            addCriterion("weld_coordinate =", value, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateNotEqualTo(String value) {
            addCriterion("weld_coordinate <>", value, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateGreaterThan(String value) {
            addCriterion("weld_coordinate >", value, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateGreaterThanOrEqualTo(String value) {
            addCriterion("weld_coordinate >=", value, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateLessThan(String value) {
            addCriterion("weld_coordinate <", value, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateLessThanOrEqualTo(String value) {
            addCriterion("weld_coordinate <=", value, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateLike(String value) {
            addCriterion("weld_coordinate like", value, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateNotLike(String value) {
            addCriterion("weld_coordinate not like", value, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateIn(List<String> values) {
            addCriterion("weld_coordinate in", values, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateNotIn(List<String> values) {
            addCriterion("weld_coordinate not in", values, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateBetween(String value1, String value2) {
            addCriterion("weld_coordinate between", value1, value2, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldCoordinateNotBetween(String value1, String value2) {
            addCriterion("weld_coordinate not between", value1, value2, "weldCoordinate");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenIsNull() {
            addCriterion("weld_silk_screen is null");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenIsNotNull() {
            addCriterion("weld_silk_screen is not null");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenEqualTo(String value) {
            addCriterion("weld_silk_screen =", value, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenNotEqualTo(String value) {
            addCriterion("weld_silk_screen <>", value, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenGreaterThan(String value) {
            addCriterion("weld_silk_screen >", value, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenGreaterThanOrEqualTo(String value) {
            addCriterion("weld_silk_screen >=", value, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenLessThan(String value) {
            addCriterion("weld_silk_screen <", value, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenLessThanOrEqualTo(String value) {
            addCriterion("weld_silk_screen <=", value, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenLike(String value) {
            addCriterion("weld_silk_screen like", value, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenNotLike(String value) {
            addCriterion("weld_silk_screen not like", value, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenIn(List<String> values) {
            addCriterion("weld_silk_screen in", values, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenNotIn(List<String> values) {
            addCriterion("weld_silk_screen not in", values, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenBetween(String value1, String value2) {
            addCriterion("weld_silk_screen between", value1, value2, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldSilkScreenNotBetween(String value1, String value2) {
            addCriterion("weld_silk_screen not between", value1, value2, "weldSilkScreen");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptIsNull() {
            addCriterion("weld_descript is null");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptIsNotNull() {
            addCriterion("weld_descript is not null");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptEqualTo(String value) {
            addCriterion("weld_descript =", value, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptNotEqualTo(String value) {
            addCriterion("weld_descript <>", value, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptGreaterThan(String value) {
            addCriterion("weld_descript >", value, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptGreaterThanOrEqualTo(String value) {
            addCriterion("weld_descript >=", value, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptLessThan(String value) {
            addCriterion("weld_descript <", value, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptLessThanOrEqualTo(String value) {
            addCriterion("weld_descript <=", value, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptLike(String value) {
            addCriterion("weld_descript like", value, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptNotLike(String value) {
            addCriterion("weld_descript not like", value, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptIn(List<String> values) {
            addCriterion("weld_descript in", values, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptNotIn(List<String> values) {
            addCriterion("weld_descript not in", values, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptBetween(String value1, String value2) {
            addCriterion("weld_descript between", value1, value2, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andWeldDescriptNotBetween(String value1, String value2) {
            addCriterion("weld_descript not between", value1, value2, "weldDescript");
            return (Criteria) this;
        }

        public Criteria andSteelNumIsNull() {
            addCriterion("steel_num is null");
            return (Criteria) this;
        }

        public Criteria andSteelNumIsNotNull() {
            addCriterion("steel_num is not null");
            return (Criteria) this;
        }

        public Criteria andSteelNumEqualTo(Integer value) {
            addCriterion("steel_num =", value, "steelNum");
            return (Criteria) this;
        }

        public Criteria andSteelNumNotEqualTo(Integer value) {
            addCriterion("steel_num <>", value, "steelNum");
            return (Criteria) this;
        }

        public Criteria andSteelNumGreaterThan(Integer value) {
            addCriterion("steel_num >", value, "steelNum");
            return (Criteria) this;
        }

        public Criteria andSteelNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("steel_num >=", value, "steelNum");
            return (Criteria) this;
        }

        public Criteria andSteelNumLessThan(Integer value) {
            addCriterion("steel_num <", value, "steelNum");
            return (Criteria) this;
        }

        public Criteria andSteelNumLessThanOrEqualTo(Integer value) {
            addCriterion("steel_num <=", value, "steelNum");
            return (Criteria) this;
        }

        public Criteria andSteelNumIn(List<Integer> values) {
            addCriterion("steel_num in", values, "steelNum");
            return (Criteria) this;
        }

        public Criteria andSteelNumNotIn(List<Integer> values) {
            addCriterion("steel_num not in", values, "steelNum");
            return (Criteria) this;
        }

        public Criteria andSteelNumBetween(Integer value1, Integer value2) {
            addCriterion("steel_num between", value1, value2, "steelNum");
            return (Criteria) this;
        }

        public Criteria andSteelNumNotBetween(Integer value1, Integer value2) {
            addCriterion("steel_num not between", value1, value2, "steelNum");
            return (Criteria) this;
        }

        public Criteria andSteelSize1IsNull() {
            addCriterion("steel_size1 is null");
            return (Criteria) this;
        }

        public Criteria andSteelSize1IsNotNull() {
            addCriterion("steel_size1 is not null");
            return (Criteria) this;
        }

        public Criteria andSteelSize1EqualTo(BigDecimal value) {
            addCriterion("steel_size1 =", value, "steelSize1");
            return (Criteria) this;
        }

        public Criteria andSteelSize1NotEqualTo(BigDecimal value) {
            addCriterion("steel_size1 <>", value, "steelSize1");
            return (Criteria) this;
        }

        public Criteria andSteelSize1GreaterThan(BigDecimal value) {
            addCriterion("steel_size1 >", value, "steelSize1");
            return (Criteria) this;
        }

        public Criteria andSteelSize1GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("steel_size1 >=", value, "steelSize1");
            return (Criteria) this;
        }

        public Criteria andSteelSize1LessThan(BigDecimal value) {
            addCriterion("steel_size1 <", value, "steelSize1");
            return (Criteria) this;
        }

        public Criteria andSteelSize1LessThanOrEqualTo(BigDecimal value) {
            addCriterion("steel_size1 <=", value, "steelSize1");
            return (Criteria) this;
        }

        public Criteria andSteelSize1In(List<BigDecimal> values) {
            addCriterion("steel_size1 in", values, "steelSize1");
            return (Criteria) this;
        }

        public Criteria andSteelSize1NotIn(List<BigDecimal> values) {
            addCriterion("steel_size1 not in", values, "steelSize1");
            return (Criteria) this;
        }

        public Criteria andSteelSize1Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("steel_size1 between", value1, value2, "steelSize1");
            return (Criteria) this;
        }

        public Criteria andSteelSize1NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("steel_size1 not between", value1, value2, "steelSize1");
            return (Criteria) this;
        }

        public Criteria andSteelSize2IsNull() {
            addCriterion("steel_size2 is null");
            return (Criteria) this;
        }

        public Criteria andSteelSize2IsNotNull() {
            addCriterion("steel_size2 is not null");
            return (Criteria) this;
        }

        public Criteria andSteelSize2EqualTo(BigDecimal value) {
            addCriterion("steel_size2 =", value, "steelSize2");
            return (Criteria) this;
        }

        public Criteria andSteelSize2NotEqualTo(BigDecimal value) {
            addCriterion("steel_size2 <>", value, "steelSize2");
            return (Criteria) this;
        }

        public Criteria andSteelSize2GreaterThan(BigDecimal value) {
            addCriterion("steel_size2 >", value, "steelSize2");
            return (Criteria) this;
        }

        public Criteria andSteelSize2GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("steel_size2 >=", value, "steelSize2");
            return (Criteria) this;
        }

        public Criteria andSteelSize2LessThan(BigDecimal value) {
            addCriterion("steel_size2 <", value, "steelSize2");
            return (Criteria) this;
        }

        public Criteria andSteelSize2LessThanOrEqualTo(BigDecimal value) {
            addCriterion("steel_size2 <=", value, "steelSize2");
            return (Criteria) this;
        }

        public Criteria andSteelSize2In(List<BigDecimal> values) {
            addCriterion("steel_size2 in", values, "steelSize2");
            return (Criteria) this;
        }

        public Criteria andSteelSize2NotIn(List<BigDecimal> values) {
            addCriterion("steel_size2 not in", values, "steelSize2");
            return (Criteria) this;
        }

        public Criteria andSteelSize2Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("steel_size2 between", value1, value2, "steelSize2");
            return (Criteria) this;
        }

        public Criteria andSteelSize2NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("steel_size2 not between", value1, value2, "steelSize2");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessIsNull() {
            addCriterion("steel_thinkness is null");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessIsNotNull() {
            addCriterion("steel_thinkness is not null");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessEqualTo(BigDecimal value) {
            addCriterion("steel_thinkness =", value, "steelThinkness");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessNotEqualTo(BigDecimal value) {
            addCriterion("steel_thinkness <>", value, "steelThinkness");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessGreaterThan(BigDecimal value) {
            addCriterion("steel_thinkness >", value, "steelThinkness");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("steel_thinkness >=", value, "steelThinkness");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessLessThan(BigDecimal value) {
            addCriterion("steel_thinkness <", value, "steelThinkness");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessLessThanOrEqualTo(BigDecimal value) {
            addCriterion("steel_thinkness <=", value, "steelThinkness");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessIn(List<BigDecimal> values) {
            addCriterion("steel_thinkness in", values, "steelThinkness");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessNotIn(List<BigDecimal> values) {
            addCriterion("steel_thinkness not in", values, "steelThinkness");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("steel_thinkness between", value1, value2, "steelThinkness");
            return (Criteria) this;
        }

        public Criteria andSteelThinknessNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("steel_thinkness not between", value1, value2, "steelThinkness");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialIsNull() {
            addCriterion("steel_material is null");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialIsNotNull() {
            addCriterion("steel_material is not null");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialEqualTo(String value) {
            addCriterion("steel_material =", value, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialNotEqualTo(String value) {
            addCriterion("steel_material <>", value, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialGreaterThan(String value) {
            addCriterion("steel_material >", value, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialGreaterThanOrEqualTo(String value) {
            addCriterion("steel_material >=", value, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialLessThan(String value) {
            addCriterion("steel_material <", value, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialLessThanOrEqualTo(String value) {
            addCriterion("steel_material <=", value, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialLike(String value) {
            addCriterion("steel_material like", value, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialNotLike(String value) {
            addCriterion("steel_material not like", value, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialIn(List<String> values) {
            addCriterion("steel_material in", values, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialNotIn(List<String> values) {
            addCriterion("steel_material not in", values, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialBetween(String value1, String value2) {
            addCriterion("steel_material between", value1, value2, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelMaterialNotBetween(String value1, String value2) {
            addCriterion("steel_material not between", value1, value2, "steelMaterial");
            return (Criteria) this;
        }

        public Criteria andSteelUseIsNull() {
            addCriterion("steel_use is null");
            return (Criteria) this;
        }

        public Criteria andSteelUseIsNotNull() {
            addCriterion("steel_use is not null");
            return (Criteria) this;
        }

        public Criteria andSteelUseEqualTo(String value) {
            addCriterion("steel_use =", value, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseNotEqualTo(String value) {
            addCriterion("steel_use <>", value, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseGreaterThan(String value) {
            addCriterion("steel_use >", value, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseGreaterThanOrEqualTo(String value) {
            addCriterion("steel_use >=", value, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseLessThan(String value) {
            addCriterion("steel_use <", value, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseLessThanOrEqualTo(String value) {
            addCriterion("steel_use <=", value, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseLike(String value) {
            addCriterion("steel_use like", value, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseNotLike(String value) {
            addCriterion("steel_use not like", value, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseIn(List<String> values) {
            addCriterion("steel_use in", values, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseNotIn(List<String> values) {
            addCriterion("steel_use not in", values, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseBetween(String value1, String value2) {
            addCriterion("steel_use between", value1, value2, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelUseNotBetween(String value1, String value2) {
            addCriterion("steel_use not between", value1, value2, "steelUse");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingIsNull() {
            addCriterion("steel_polishing is null");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingIsNotNull() {
            addCriterion("steel_polishing is not null");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingEqualTo(String value) {
            addCriterion("steel_polishing =", value, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingNotEqualTo(String value) {
            addCriterion("steel_polishing <>", value, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingGreaterThan(String value) {
            addCriterion("steel_polishing >", value, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingGreaterThanOrEqualTo(String value) {
            addCriterion("steel_polishing >=", value, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingLessThan(String value) {
            addCriterion("steel_polishing <", value, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingLessThanOrEqualTo(String value) {
            addCriterion("steel_polishing <=", value, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingLike(String value) {
            addCriterion("steel_polishing like", value, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingNotLike(String value) {
            addCriterion("steel_polishing not like", value, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingIn(List<String> values) {
            addCriterion("steel_polishing in", values, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingNotIn(List<String> values) {
            addCriterion("steel_polishing not in", values, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingBetween(String value1, String value2) {
            addCriterion("steel_polishing between", value1, value2, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andSteelPolishingNotBetween(String value1, String value2) {
            addCriterion("steel_polishing not between", value1, value2, "steelPolishing");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptIsNull() {
            addCriterion("glue_descript is null");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptIsNotNull() {
            addCriterion("glue_descript is not null");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptEqualTo(String value) {
            addCriterion("glue_descript =", value, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptNotEqualTo(String value) {
            addCriterion("glue_descript <>", value, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptGreaterThan(String value) {
            addCriterion("glue_descript >", value, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptGreaterThanOrEqualTo(String value) {
            addCriterion("glue_descript >=", value, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptLessThan(String value) {
            addCriterion("glue_descript <", value, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptLessThanOrEqualTo(String value) {
            addCriterion("glue_descript <=", value, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptLike(String value) {
            addCriterion("glue_descript like", value, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptNotLike(String value) {
            addCriterion("glue_descript not like", value, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptIn(List<String> values) {
            addCriterion("glue_descript in", values, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptNotIn(List<String> values) {
            addCriterion("glue_descript not in", values, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptBetween(String value1, String value2) {
            addCriterion("glue_descript between", value1, value2, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andGlueDescriptNotBetween(String value1, String value2) {
            addCriterion("glue_descript not between", value1, value2, "glueDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptIsNull() {
            addCriterion("paint_descript is null");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptIsNotNull() {
            addCriterion("paint_descript is not null");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptEqualTo(String value) {
            addCriterion("paint_descript =", value, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptNotEqualTo(String value) {
            addCriterion("paint_descript <>", value, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptGreaterThan(String value) {
            addCriterion("paint_descript >", value, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptGreaterThanOrEqualTo(String value) {
            addCriterion("paint_descript >=", value, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptLessThan(String value) {
            addCriterion("paint_descript <", value, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptLessThanOrEqualTo(String value) {
            addCriterion("paint_descript <=", value, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptLike(String value) {
            addCriterion("paint_descript like", value, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptNotLike(String value) {
            addCriterion("paint_descript not like", value, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptIn(List<String> values) {
            addCriterion("paint_descript in", values, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptNotIn(List<String> values) {
            addCriterion("paint_descript not in", values, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptBetween(String value1, String value2) {
            addCriterion("paint_descript between", value1, value2, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andPaintDescriptNotBetween(String value1, String value2) {
            addCriterion("paint_descript not between", value1, value2, "paintDescript");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumIsNull() {
            addCriterion("acrylic_num is null");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumIsNotNull() {
            addCriterion("acrylic_num is not null");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumEqualTo(Integer value) {
            addCriterion("acrylic_num =", value, "acrylicNum");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumNotEqualTo(Integer value) {
            addCriterion("acrylic_num <>", value, "acrylicNum");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumGreaterThan(Integer value) {
            addCriterion("acrylic_num >", value, "acrylicNum");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("acrylic_num >=", value, "acrylicNum");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumLessThan(Integer value) {
            addCriterion("acrylic_num <", value, "acrylicNum");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumLessThanOrEqualTo(Integer value) {
            addCriterion("acrylic_num <=", value, "acrylicNum");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumIn(List<Integer> values) {
            addCriterion("acrylic_num in", values, "acrylicNum");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumNotIn(List<Integer> values) {
            addCriterion("acrylic_num not in", values, "acrylicNum");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumBetween(Integer value1, Integer value2) {
            addCriterion("acrylic_num between", value1, value2, "acrylicNum");
            return (Criteria) this;
        }

        public Criteria andAcrylicNumNotBetween(Integer value1, Integer value2) {
            addCriterion("acrylic_num not between", value1, value2, "acrylicNum");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadIsNull() {
            addCriterion("acrylic_cad is null");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadIsNotNull() {
            addCriterion("acrylic_cad is not null");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadEqualTo(String value) {
            addCriterion("acrylic_cad =", value, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadNotEqualTo(String value) {
            addCriterion("acrylic_cad <>", value, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadGreaterThan(String value) {
            addCriterion("acrylic_cad >", value, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadGreaterThanOrEqualTo(String value) {
            addCriterion("acrylic_cad >=", value, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadLessThan(String value) {
            addCriterion("acrylic_cad <", value, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadLessThanOrEqualTo(String value) {
            addCriterion("acrylic_cad <=", value, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadLike(String value) {
            addCriterion("acrylic_cad like", value, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadNotLike(String value) {
            addCriterion("acrylic_cad not like", value, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadIn(List<String> values) {
            addCriterion("acrylic_cad in", values, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadNotIn(List<String> values) {
            addCriterion("acrylic_cad not in", values, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadBetween(String value1, String value2) {
            addCriterion("acrylic_cad between", value1, value2, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andAcrylicCadNotBetween(String value1, String value2) {
            addCriterion("acrylic_cad not between", value1, value2, "acrylicCad");
            return (Criteria) this;
        }

        public Criteria andChassisNumIsNull() {
            addCriterion("chassis_num is null");
            return (Criteria) this;
        }

        public Criteria andChassisNumIsNotNull() {
            addCriterion("chassis_num is not null");
            return (Criteria) this;
        }

        public Criteria andChassisNumEqualTo(Integer value) {
            addCriterion("chassis_num =", value, "chassisNum");
            return (Criteria) this;
        }

        public Criteria andChassisNumNotEqualTo(Integer value) {
            addCriterion("chassis_num <>", value, "chassisNum");
            return (Criteria) this;
        }

        public Criteria andChassisNumGreaterThan(Integer value) {
            addCriterion("chassis_num >", value, "chassisNum");
            return (Criteria) this;
        }

        public Criteria andChassisNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("chassis_num >=", value, "chassisNum");
            return (Criteria) this;
        }

        public Criteria andChassisNumLessThan(Integer value) {
            addCriterion("chassis_num <", value, "chassisNum");
            return (Criteria) this;
        }

        public Criteria andChassisNumLessThanOrEqualTo(Integer value) {
            addCriterion("chassis_num <=", value, "chassisNum");
            return (Criteria) this;
        }

        public Criteria andChassisNumIn(List<Integer> values) {
            addCriterion("chassis_num in", values, "chassisNum");
            return (Criteria) this;
        }

        public Criteria andChassisNumNotIn(List<Integer> values) {
            addCriterion("chassis_num not in", values, "chassisNum");
            return (Criteria) this;
        }

        public Criteria andChassisNumBetween(Integer value1, Integer value2) {
            addCriterion("chassis_num between", value1, value2, "chassisNum");
            return (Criteria) this;
        }

        public Criteria andChassisNumNotBetween(Integer value1, Integer value2) {
            addCriterion("chassis_num not between", value1, value2, "chassisNum");
            return (Criteria) this;
        }

        public Criteria andChassisCadIsNull() {
            addCriterion("chassis_cad is null");
            return (Criteria) this;
        }

        public Criteria andChassisCadIsNotNull() {
            addCriterion("chassis_cad is not null");
            return (Criteria) this;
        }

        public Criteria andChassisCadEqualTo(String value) {
            addCriterion("chassis_cad =", value, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadNotEqualTo(String value) {
            addCriterion("chassis_cad <>", value, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadGreaterThan(String value) {
            addCriterion("chassis_cad >", value, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadGreaterThanOrEqualTo(String value) {
            addCriterion("chassis_cad >=", value, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadLessThan(String value) {
            addCriterion("chassis_cad <", value, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadLessThanOrEqualTo(String value) {
            addCriterion("chassis_cad <=", value, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadLike(String value) {
            addCriterion("chassis_cad like", value, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadNotLike(String value) {
            addCriterion("chassis_cad not like", value, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadIn(List<String> values) {
            addCriterion("chassis_cad in", values, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadNotIn(List<String> values) {
            addCriterion("chassis_cad not in", values, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadBetween(String value1, String value2) {
            addCriterion("chassis_cad between", value1, value2, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisCadNotBetween(String value1, String value2) {
            addCriterion("chassis_cad not between", value1, value2, "chassisCad");
            return (Criteria) this;
        }

        public Criteria andChassisListIsNull() {
            addCriterion("chassis_list is null");
            return (Criteria) this;
        }

        public Criteria andChassisListIsNotNull() {
            addCriterion("chassis_list is not null");
            return (Criteria) this;
        }

        public Criteria andChassisListEqualTo(String value) {
            addCriterion("chassis_list =", value, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListNotEqualTo(String value) {
            addCriterion("chassis_list <>", value, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListGreaterThan(String value) {
            addCriterion("chassis_list >", value, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListGreaterThanOrEqualTo(String value) {
            addCriterion("chassis_list >=", value, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListLessThan(String value) {
            addCriterion("chassis_list <", value, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListLessThanOrEqualTo(String value) {
            addCriterion("chassis_list <=", value, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListLike(String value) {
            addCriterion("chassis_list like", value, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListNotLike(String value) {
            addCriterion("chassis_list not like", value, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListIn(List<String> values) {
            addCriterion("chassis_list in", values, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListNotIn(List<String> values) {
            addCriterion("chassis_list not in", values, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListBetween(String value1, String value2) {
            addCriterion("chassis_list between", value1, value2, "chassisList");
            return (Criteria) this;
        }

        public Criteria andChassisListNotBetween(String value1, String value2) {
            addCriterion("chassis_list not between", value1, value2, "chassisList");
            return (Criteria) this;
        }

        public Criteria andPencilNumIsNull() {
            addCriterion("pencil_num is null");
            return (Criteria) this;
        }

        public Criteria andPencilNumIsNotNull() {
            addCriterion("pencil_num is not null");
            return (Criteria) this;
        }

        public Criteria andPencilNumEqualTo(Integer value) {
            addCriterion("pencil_num =", value, "pencilNum");
            return (Criteria) this;
        }

        public Criteria andPencilNumNotEqualTo(Integer value) {
            addCriterion("pencil_num <>", value, "pencilNum");
            return (Criteria) this;
        }

        public Criteria andPencilNumGreaterThan(Integer value) {
            addCriterion("pencil_num >", value, "pencilNum");
            return (Criteria) this;
        }

        public Criteria andPencilNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("pencil_num >=", value, "pencilNum");
            return (Criteria) this;
        }

        public Criteria andPencilNumLessThan(Integer value) {
            addCriterion("pencil_num <", value, "pencilNum");
            return (Criteria) this;
        }

        public Criteria andPencilNumLessThanOrEqualTo(Integer value) {
            addCriterion("pencil_num <=", value, "pencilNum");
            return (Criteria) this;
        }

        public Criteria andPencilNumIn(List<Integer> values) {
            addCriterion("pencil_num in", values, "pencilNum");
            return (Criteria) this;
        }

        public Criteria andPencilNumNotIn(List<Integer> values) {
            addCriterion("pencil_num not in", values, "pencilNum");
            return (Criteria) this;
        }

        public Criteria andPencilNumBetween(Integer value1, Integer value2) {
            addCriterion("pencil_num between", value1, value2, "pencilNum");
            return (Criteria) this;
        }

        public Criteria andPencilNumNotBetween(Integer value1, Integer value2) {
            addCriterion("pencil_num not between", value1, value2, "pencilNum");
            return (Criteria) this;
        }

        public Criteria andPencilCadIsNull() {
            addCriterion("pencil_cad is null");
            return (Criteria) this;
        }

        public Criteria andPencilCadIsNotNull() {
            addCriterion("pencil_cad is not null");
            return (Criteria) this;
        }

        public Criteria andPencilCadEqualTo(String value) {
            addCriterion("pencil_cad =", value, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadNotEqualTo(String value) {
            addCriterion("pencil_cad <>", value, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadGreaterThan(String value) {
            addCriterion("pencil_cad >", value, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadGreaterThanOrEqualTo(String value) {
            addCriterion("pencil_cad >=", value, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadLessThan(String value) {
            addCriterion("pencil_cad <", value, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadLessThanOrEqualTo(String value) {
            addCriterion("pencil_cad <=", value, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadLike(String value) {
            addCriterion("pencil_cad like", value, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadNotLike(String value) {
            addCriterion("pencil_cad not like", value, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadIn(List<String> values) {
            addCriterion("pencil_cad in", values, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadNotIn(List<String> values) {
            addCriterion("pencil_cad not in", values, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadBetween(String value1, String value2) {
            addCriterion("pencil_cad between", value1, value2, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilCadNotBetween(String value1, String value2) {
            addCriterion("pencil_cad not between", value1, value2, "pencilCad");
            return (Criteria) this;
        }

        public Criteria andPencilListIsNull() {
            addCriterion("pencil_list is null");
            return (Criteria) this;
        }

        public Criteria andPencilListIsNotNull() {
            addCriterion("pencil_list is not null");
            return (Criteria) this;
        }

        public Criteria andPencilListEqualTo(String value) {
            addCriterion("pencil_list =", value, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListNotEqualTo(String value) {
            addCriterion("pencil_list <>", value, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListGreaterThan(String value) {
            addCriterion("pencil_list >", value, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListGreaterThanOrEqualTo(String value) {
            addCriterion("pencil_list >=", value, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListLessThan(String value) {
            addCriterion("pencil_list <", value, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListLessThanOrEqualTo(String value) {
            addCriterion("pencil_list <=", value, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListLike(String value) {
            addCriterion("pencil_list like", value, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListNotLike(String value) {
            addCriterion("pencil_list not like", value, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListIn(List<String> values) {
            addCriterion("pencil_list in", values, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListNotIn(List<String> values) {
            addCriterion("pencil_list not in", values, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListBetween(String value1, String value2) {
            addCriterion("pencil_list between", value1, value2, "pencilList");
            return (Criteria) this;
        }

        public Criteria andPencilListNotBetween(String value1, String value2) {
            addCriterion("pencil_list not between", value1, value2, "pencilList");
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

        public Criteria andComponentCostIsNull() {
            addCriterion("component_cost is null");
            return (Criteria) this;
        }

        public Criteria andComponentCostIsNotNull() {
            addCriterion("component_cost is not null");
            return (Criteria) this;
        }

        public Criteria andComponentCostEqualTo(BigDecimal value) {
            addCriterion("component_cost =", value, "componentCost");
            return (Criteria) this;
        }

        public Criteria andComponentCostNotEqualTo(BigDecimal value) {
            addCriterion("component_cost <>", value, "componentCost");
            return (Criteria) this;
        }

        public Criteria andComponentCostGreaterThan(BigDecimal value) {
            addCriterion("component_cost >", value, "componentCost");
            return (Criteria) this;
        }

        public Criteria andComponentCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("component_cost >=", value, "componentCost");
            return (Criteria) this;
        }

        public Criteria andComponentCostLessThan(BigDecimal value) {
            addCriterion("component_cost <", value, "componentCost");
            return (Criteria) this;
        }

        public Criteria andComponentCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("component_cost <=", value, "componentCost");
            return (Criteria) this;
        }

        public Criteria andComponentCostIn(List<BigDecimal> values) {
            addCriterion("component_cost in", values, "componentCost");
            return (Criteria) this;
        }

        public Criteria andComponentCostNotIn(List<BigDecimal> values) {
            addCriterion("component_cost not in", values, "componentCost");
            return (Criteria) this;
        }

        public Criteria andComponentCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("component_cost between", value1, value2, "componentCost");
            return (Criteria) this;
        }

        public Criteria andComponentCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("component_cost not between", value1, value2, "componentCost");
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

        public Criteria andTotalMoneyIsNull() {
            addCriterion("total_money is null");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyIsNotNull() {
            addCriterion("total_money is not null");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyEqualTo(BigDecimal value) {
            addCriterion("total_money =", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyNotEqualTo(BigDecimal value) {
            addCriterion("total_money <>", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyGreaterThan(BigDecimal value) {
            addCriterion("total_money >", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_money >=", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyLessThan(BigDecimal value) {
            addCriterion("total_money <", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_money <=", value, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyIn(List<BigDecimal> values) {
            addCriterion("total_money in", values, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyNotIn(List<BigDecimal> values) {
            addCriterion("total_money not in", values, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_money between", value1, value2, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andTotalMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_money not between", value1, value2, "totalMoney");
            return (Criteria) this;
        }

        public Criteria andIsUrgentIsNull() {
            addCriterion("is_urgent is null");
            return (Criteria) this;
        }

        public Criteria andIsUrgentIsNotNull() {
            addCriterion("is_urgent is not null");
            return (Criteria) this;
        }

        public Criteria andIsUrgentEqualTo(String value) {
            addCriterion("is_urgent =", value, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentNotEqualTo(String value) {
            addCriterion("is_urgent <>", value, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentGreaterThan(String value) {
            addCriterion("is_urgent >", value, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentGreaterThanOrEqualTo(String value) {
            addCriterion("is_urgent >=", value, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentLessThan(String value) {
            addCriterion("is_urgent <", value, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentLessThanOrEqualTo(String value) {
            addCriterion("is_urgent <=", value, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentLike(String value) {
            addCriterion("is_urgent like", value, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentNotLike(String value) {
            addCriterion("is_urgent not like", value, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentIn(List<String> values) {
            addCriterion("is_urgent in", values, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentNotIn(List<String> values) {
            addCriterion("is_urgent not in", values, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentBetween(String value1, String value2) {
            addCriterion("is_urgent between", value1, value2, "isUrgent");
            return (Criteria) this;
        }

        public Criteria andIsUrgentNotBetween(String value1, String value2) {
            addCriterion("is_urgent not between", value1, value2, "isUrgent");
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