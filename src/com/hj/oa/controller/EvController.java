package com.hj.oa.controller;

import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Evaluation;
import com.hj.oa.bean.Role;
import com.hj.oa.bean.User;
import com.hj.oa.service.EvaluationService;
import com.hj.oa.service.UserService;
import com.hj.util.ExcelUtil;
import com.hj.util.RoleUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EvController
  extends BaseController
{
  @Autowired
  private EvaluationService evService;
  @Autowired
  private UserService userService;
  
  @RequestMapping({"oa/ev/mine"})
  public String mine(HttpSession session, Model model)
  {
    User loginUser = getLoginUser(session);
    
    List<Evaluation> list = this.evService.getEmpEvaluation(Integer.valueOf(loginUser.getId()), null, Integer.valueOf(2));
    Evaluation ev = this.evService.getEmpEvaluationSum(Integer.valueOf(loginUser.getId()), null, Integer.valueOf(2));
    
    model.addAttribute("list", list);
    model.addAttribute("ev", ev);
    
    return "oa/ev/mine";
  }
  
  @RequestMapping({"oa/ev/huizong"})
  @ResponseBody
  public HashMap<String, String> huizong(HttpSession session, Model model)
  {
    List<User> users = this.userService.findAllUsers();
    for (User loginUser : users) {
      if (loginUser.getId() != 1)
      {
        List<Evaluation> list = this.evService.getEmpEvaluation(Integer.valueOf(loginUser.getId()), null, Integer.valueOf(2));
        Evaluation ev = this.evService.getEmpEvaluationSum(Integer.valueOf(loginUser.getId()), null, Integer.valueOf(2));
        
        ExcelUtil.generateForEv(ev, list, loginUser.getName());
      }
    }
    HashMap<String, String> map = new HashMap();
    map.put("result", "ok");
    return map;
  }
  
  @RequestMapping({"oa/ev/empDetail/{empId}"})
  public String empDetail(@PathVariable int empId, HttpSession session, Model model)
  {
    User emp = this.userService.findById(empId);
    
    List<Evaluation> list = this.evService.getEmpEvaluation(Integer.valueOf(emp.getId()), null, Integer.valueOf(2));
    Evaluation ev = this.evService.getEmpEvaluationSum(Integer.valueOf(emp.getId()), null, Integer.valueOf(2));
    
    model.addAttribute("list", list);
    model.addAttribute("ev", ev);
    model.addAttribute("emp", emp);
    
    return "oa/ev/empDetail";
  }
  
  @RequestMapping({"oa/ev/empDetailZg/{empId}"})
  public String empDetailZg(@PathVariable Integer empId, Integer zgId, HttpSession session, Model model)
  {
    User emp = this.userService.findById(empId.intValue());
    User loginUser = getLoginUser(session);
    
    zgId = Integer.valueOf(zgId == null ? loginUser.getId() : zgId.intValue());
    List<Evaluation> list = this.evService.getEmpEvaluation(Integer.valueOf(emp.getId()), zgId, Integer.valueOf(2));
    Evaluation ev = this.evService.getEmpEvaluationSum(Integer.valueOf(emp.getId()), zgId, Integer.valueOf(2));
    
    model.addAttribute("list", list);
    model.addAttribute("ev", ev);
    model.addAttribute("emp", emp);
    
    return "oa/ev/empDetailZg";
  }
  
  @RequestMapping({"oa/ev/subs"})
  public String subs(HttpSession session, Integer deptId, Model model)
  {
    User loginUser = getLoginUser(session);
    List<Role> roles = getLoginUserRole(session);
    List<Dept> depts = new ArrayList();
    if ((RoleUtil.hasRole(roles, "总经理")) || (RoleUtil.hasRole(roles, "行政主管")))
    {
      depts = this.userService.findAllSubDept();
      Dept d = new Dept();
      d.setId(0);
      d.setName("全体员工");
      depts.add(0, d);
    }
    else if (RoleUtil.hasRole(roles, "技术总监"))
    {
      List<Dept> dps = this.userService.findAllSubDept();
      for (Dept d : dps) {
        if (d.getPid().intValue() == 2) {
          depts.add(d);
        }
      }
    }
    else if (RoleUtil.hasRole(roles, "部门主管"))
    {
      List<Dept> mdp = this.userService.findDeptsByMgr(loginUser.getId());
      depts.addAll(mdp);
      if (61 == loginUser.getId())
      {
        Dept dept = new Dept();
        dept.setId(14);
        dept.setName("技术部");
        depts.add(dept);
        Dept dept1 = new Dept();
        dept1.setId(4);
        dept1.setName("行政部");
        depts.add(dept1);
      }
    }
    if (41 == loginUser.getId())
    {
      Dept dept = new Dept();
      dept.setId(8);
      dept.setName("系统设计部");
      depts.add(dept);
    }
    else if (35 == loginUser.getId())
    {
      Dept dept1 = new Dept();
      dept1.setId(4);
      dept1.setName("行政部");
      depts.add(dept1);
      Dept dept = new Dept();
      dept.setId(17);
      dept.setName("生产运营部");
      depts.add(dept);
      if ((deptId == null) || (deptId.intValue() == 0)) {
        deptId = Integer.valueOf(17);
      }
    }
    else if (96 == loginUser.getId())
    {
      Dept dept = new Dept();
      dept.setId(5);
      dept.setName("财务部");
      depts.add(dept);
      deptId = Integer.valueOf(5);
    }
    model.addAttribute("depts", depts);
    if ((deptId == null) || (deptId.intValue() == 0))
    {
      if ((RoleUtil.hasRole(roles, "总经理")) || (RoleUtil.hasRole(roles, "行政主管")))
      {
        List<Evaluation> list = this.evService.findAllEmpSumEavluation(null, Integer.valueOf(2));
        model.addAttribute("list", list);
      }
      else if (RoleUtil.hasRole(roles, "部门主管"))
      {
        Dept dept = (Dept)depts.get(0);
        List<Evaluation> list = this.evService.findEmpSumEavluationByDept(Integer.valueOf(dept.getId()), null, Integer.valueOf(2));
        model.addAttribute("list", list);
        model.addAttribute("dept", dept);
      }
      return "oa/ev/subs";
    }
    Dept dept = this.userService.findDeptById(deptId);
    List<Evaluation> list = this.evService.findEmpSumEavluationByDept(Integer.valueOf(dept.getId()), null, Integer.valueOf(2));
    model.addAttribute("list", list);
    model.addAttribute("dept", dept);
    
    return "oa/ev/subs";
  }
  
  @RequestMapping({"oa/ev/subsZg"})
  public String subsZg(HttpSession session, Integer deptId, Integer zgId, Model model)
  {
    User loginUser = getLoginUser(session);
    List<Role> roles = getLoginUserRole(session);
    List<Dept> depts = new ArrayList();
    zgId = Integer.valueOf(zgId == null ? loginUser.getId() : zgId.intValue());
    if ((RoleUtil.hasRole(roles, "总经理")) || (RoleUtil.hasRole(roles, "行政主管")))
    {
      depts = this.userService.findAllSubDept();
      Dept d = new Dept();
      d.setId(0);
      d.setName("全体员工");
      depts.add(0, d);
    }
    else if (RoleUtil.hasRole(roles, "技术总监"))
    {
      List<Dept> dps = this.userService.findAllSubDept();
      for (Dept d : dps) {
        if (d.getPid().intValue() == 2) {
          depts.add(d);
        }
      }
    }
    else if (RoleUtil.hasRole(roles, "部门主管"))
    {
      List<Dept> mdp = this.userService.findDeptsByMgr(loginUser.getId());
      depts.addAll(mdp);
      if (61 == loginUser.getId())
      {
        Dept dept = new Dept();
        dept.setId(14);
        dept.setName("技术部");
        depts.add(dept);
        Dept dept1 = new Dept();
        dept1.setId(4);
        dept1.setName("行政部");
        depts.add(dept1);
      }
    }
    if (41 == loginUser.getId())
    {
      Dept dept = new Dept();
      dept.setId(8);
      dept.setName("系统设计部");
      depts.add(dept);
    }
    else if (35 == loginUser.getId())
    {
      Dept dept1 = new Dept();
      dept1.setId(4);
      dept1.setName("行政部");
      depts.add(dept1);
      Dept dept = new Dept();
      dept.setId(17);
      dept.setName("生产运营部");
      depts.add(dept);
      if ((deptId == null) || (deptId.intValue() == 0)) {
        deptId = Integer.valueOf(17);
      }
    }
    else if (96 == loginUser.getId())
    {
      Dept dept = new Dept();
      dept.setId(5);
      dept.setName("财务部");
      depts.add(dept);
      deptId = Integer.valueOf(5);
    }
    model.addAttribute("depts", depts);
    if ((deptId == null) || (deptId.intValue() == 0))
    {
      if ((RoleUtil.hasRole(roles, "总经理")) || (RoleUtil.hasRole(roles, "行政主管")))
      {
        List<Evaluation> list = this.evService.findAllEmpSumEavluation(zgId, Integer.valueOf(2));
        model.addAttribute("list", list);
      }
      else if (RoleUtil.hasRole(roles, "部门主管"))
      {
        Dept dept = (Dept)depts.get(0);
        List<Evaluation> list = this.evService.findEmpSumEavluationByDept(Integer.valueOf(dept.getId()), Integer.valueOf(loginUser.getId()), Integer.valueOf(2));
        model.addAttribute("list", list);
        model.addAttribute("dept", dept);
      }
      return "oa/ev/subsZg";
    }
    Dept dept = this.userService.findDeptById(deptId);
    List<Evaluation> list = this.evService.findEmpSumEavluationByDept(Integer.valueOf(dept.getId()), Integer.valueOf(loginUser.getId()), Integer.valueOf(2));
    model.addAttribute("list", list);
    model.addAttribute("dept", dept);
    
    return "oa/ev/subsZg";
  }
  
  @RequestMapping({"oa/ev/modify"})
  public String modify(Integer empId, String empName, Integer[] evaIds, Integer tabId, Integer[] types, String[] points, String[] anses, HttpSession session, Model model)
  {
    User loginUser = (User)session.getAttribute("loginUser");
    Evaluation evv = this.evService.getEmpEvaluationSum(empId, Integer.valueOf(loginUser.getId()), Integer.valueOf(2));
    if (evv.getPoint() != null)
    {
      List<Evaluation> list = this.evService.getEmpEvaluation(empId, Integer.valueOf(loginUser.getId()), Integer.valueOf(2));
      evv = this.evService.getEmpEvaluationSum(empId, null, Integer.valueOf(2));
      
      model.addAttribute("list", list);
      model.addAttribute("ev", evv);
      
      return "redirect:/web/oa/ev/empDetailZg/" + empId;
    }
    String msg = "操作成功";
    try
    {
      Evaluation evSum = new Evaluation();
      
      evSum.setEmpId(empId);
      evSum.setEmpName(empName);
      evSum.setZgId(Integer.valueOf(loginUser.getId()));
      evSum.setTabId(tabId.intValue());
      evSum.setZgName(loginUser.getName());
      
      int countp = 0;
      double score = 0.0D;
      double type0 = 0.0D;double type1 = 0.0D;double type2 = 0.0D;double type3 = 0.0D;
      
      List<Evaluation> list = new ArrayList();
      int size = evaIds.length;
      if (size <= 0)
      {
        model.addAttribute("msg", "提交数据为空");
        
        return "redirect:/web/oa/ev/empDetailZg/" + empId;
      }
      for (int i = 0; i < size; i++)
      {
        Evaluation ev = new Evaluation();
        ev.setEmpId(empId);
        ev.setEmpName(empName);
        ev.setZgId(Integer.valueOf(loginUser.getId()));
        

        int evId = evaIds[i].intValue();
        String p = points[i];
        int po = Integer.parseInt(p);
        if (types[i].intValue() == 0) {
          type0 += po;
        } else if (types[i].intValue() == 1) {
          type1 += po;
        } else if (types[i].intValue() == 2) {
          type2 += po;
        } else if (types[i].intValue() == 3) {
          type3 += po;
        }
        countp += po;
        ev.setPoint(Integer.valueOf(po));
        ev.setAns(anses[i]);
        ev.setEvaId(Integer.valueOf(evId));
        list.add(ev);
      }
      score = type0 / 100.0D * 100.0D * 0.55D + type1 / 160.0D * 100.0D * 0.15D + type2 / 110.0D * 100.0D * 0.15D + type3 / 170.0D * 100.0D * 0.15D;
      evSum.setScore(Double.valueOf(score));
      evSum.setPoint(Integer.valueOf(countp));
      this.evService.saveEvaluation(list, evSum);
    }
    catch (Exception e)
    {
      msg = "提交失败。";
      e.printStackTrace();
    }
    model.addAttribute("msg", msg);
    
    List<Evaluation> list = this.evService.getEmpEvaluation(empId, Integer.valueOf(loginUser.getId()), Integer.valueOf(2));
    Evaluation ev = this.evService.getEmpEvaluationSum(empId, null, Integer.valueOf(2));
    
    model.addAttribute("list", list);
    model.addAttribute("ev", ev);
    

    return "redirect:/web/oa/ev/empDetailZg/" + empId;
  }
  
  @RequestMapping({"oa/ev/myModify"})
  public String myModify(Integer[] evaIds, Integer[] types, String[] points, String[] anses, Integer tabId, HttpSession session, Model model)
  {
    User loginUser = (User)session.getAttribute("loginUser");
    Evaluation evv = this.evService.getEmpEvaluationSum(Integer.valueOf(loginUser.getId()), null, Integer.valueOf(2));
    if (evv.getPoint() != null)
    {
      List<Evaluation> list = this.evService.getEmpEvaluation(Integer.valueOf(loginUser.getId()), null, Integer.valueOf(2));
      evv = this.evService.getEmpEvaluationSum(Integer.valueOf(loginUser.getId()), null, Integer.valueOf(2));
      
      model.addAttribute("list", list);
      model.addAttribute("ev", evv);
      
      return "redirect:/web/oa/ev/mine";
    }
    String msg = "操作成功";
    try
    {
      Evaluation evSum = new Evaluation();
      
      evSum.setEmpId(Integer.valueOf(loginUser.getId()));
      evSum.setEmpName(loginUser.getName());
      
      int countp = 0;
      double score = 0.0D;
      double type0 = 0.0D;double type1 = 0.0D;double type2 = 0.0D;double type3 = 0.0D;
      
      List<Evaluation> list = new ArrayList();
      int size = evaIds.length;
      if (size <= 0)
      {
        model.addAttribute("msg", "提交数据为空");
        
        return "redirect:/web/oa/ev/mine";
      }
      for (int i = 0; i < size; i++)
      {
        Evaluation ev = new Evaluation();
        ev.setEmpId(Integer.valueOf(loginUser.getId()));
        ev.setEmpName(loginUser.getName());
        

        int evId = evaIds[i].intValue();
        String p = points[i];
        int po = Integer.parseInt(p);
        if (types[i].intValue() == 0) {
          type0 += po;
        } else if (types[i].intValue() == 1) {
          type1 += po;
        } else if (types[i].intValue() == 2) {
          type2 += po;
        } else if (types[i].intValue() == 3) {
          type3 += po;
        }
        countp += po;
        ev.setPoint(Integer.valueOf(po));
        ev.setAns(anses[i]);
        ev.setEvaId(Integer.valueOf(evId));
        list.add(ev);
      }
      score = type0 / 100.0D * 100.0D * 0.55D + type1 / 160.0D * 100.0D * 0.15D + type2 / 110.0D * 100.0D * 0.15D + type3 / 170.0D * 100.0D * 0.15D;
      evSum.setTabId(tabId.intValue());
      evSum.setScore(Double.valueOf(score));
      evSum.setPoint(Integer.valueOf(countp));
      this.evService.saveEvaluation(list, evSum);
    }
    catch (Exception e)
    {
      msg = "提交失败。";
      e.printStackTrace();
    }
    model.addAttribute("msg", msg);
    
    List<Evaluation> list = this.evService.getEmpEvaluation(Integer.valueOf(loginUser.getId()), null, Integer.valueOf(2));
    Evaluation ev = this.evService.getEmpEvaluationSum(Integer.valueOf(loginUser.getId()), null, Integer.valueOf(2));
    
    model.addAttribute("list", list);
    model.addAttribute("ev", ev);
    

    return "redirect:/web/oa/ev/mine";
  }
}
