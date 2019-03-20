package com.hj.oa.bean;

import java.io.Serializable;

public class Evaluation
  implements Serializable
{
  private static final long serialVersionUID = 12042018L;
  private int id;
  private int tabId;
  private String title;
  private Integer point;
  private Integer evaId;
  private Integer type;
  private String bz;
  private Integer empId;
  private String empName;
  private String zgName;
  private Integer zgId;
  private Integer empPoint;
  private Double score;
  private String ans;
  private String content;
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
  public Integer getPoint()
  {
    return this.point;
  }
  
  public void setPoint(Integer point)
  {
    this.point = point;
  }
  
  public Integer getEvaId()
  {
    return this.evaId;
  }
  
  public void setEvaId(Integer evaId)
  {
    this.evaId = evaId;
  }
  
  public Integer getType()
  {
    return this.type;
  }
  
  public void setType(Integer type)
  {
    this.type = type;
  }
  
  public String getBz()
  {
    return this.bz;
  }
  
  public void setBz(String bz)
  {
    this.bz = bz;
  }
  
  public Integer getEmpId()
  {
    return this.empId;
  }
  
  public void setEmpId(Integer empId)
  {
    this.empId = empId;
  }
  
  public String getEmpName()
  {
    return this.empName;
  }
  
  public void setEmpName(String empName)
  {
    this.empName = empName;
  }
  
  public Integer getZgId()
  {
    return this.zgId;
  }
  
  public void setZgId(Integer zgId)
  {
    this.zgId = zgId;
  }
  
  public Integer getEmpPoint()
  {
    return this.empPoint;
  }
  
  public void setEmpPoint(Integer empPoint)
  {
    this.empPoint = empPoint;
  }
  
  public Double getScore()
  {
    return this.score;
  }
  
  public void setScore(Double score)
  {
    this.score = score;
  }
  
  public String getAns()
  {
    return this.ans;
  }
  
  public void setAns(String ans)
  {
    this.ans = ans;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
  
  public int getTabId()
  {
    return this.tabId;
  }
  
  public void setTabId(int tabId)
  {
    this.tabId = tabId;
  }
  
  public String getZgName()
  {
    return this.zgName;
  }
  
  public void setZgName(String zgName)
  {
    this.zgName = zgName;
  }
}
