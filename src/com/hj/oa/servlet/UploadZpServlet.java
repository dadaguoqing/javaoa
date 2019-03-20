package com.hj.oa.servlet;

import com.hj.oa.bean.Evaluation;
import com.hj.oa.bean.User;
import com.hj.oa.service.EvaluationService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.WebApplicationContext;

public class UploadZpServlet
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  public static SimpleDateFormat dFormat = new SimpleDateFormat("yyyy年MM月dd日");
  public static HashMap<Integer, Evaluation> evMap = new HashMap();
  private EvaluationService evService;
  private String[] types = { ".PDF", ".DOC", ".DOCX", ".XLS", ".XLSX" };
  
  private boolean isValidType(String type)
  {
    for (String str : this.types) {
      if (str.equals(type)) {
        return true;
      }
    }
    return false;
  }
  
  public void init()
  {
    ServletContext ctx = getServletContext();
    WebApplicationContext appCtx = (WebApplicationContext)ctx.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    this.evService = ((EvaluationService)appCtx.getBean(EvaluationService.class));
    
    List<Evaluation> list = this.evService.getAllEvaluation();
    for (Evaluation ev : list) {
      evMap.put(Integer.valueOf(ev.getId()), ev);
    }
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = request.getSession();
    
    User loginUser = (User)session.getAttribute("loginUser");
    











    String msg = "操作成功";
    
    int uploadSize = 20;
    try
    {
      Evaluation evSum = new Evaluation();
      
      evSum.setEmpId(Integer.valueOf(loginUser.getId()));
      evSum.setEmpName(loginUser.getName());
      

      DiskFileItemFactory factory = new DiskFileItemFactory();
      
      ServletFileUpload upload = new ServletFileUpload(factory);
      


      upload.setSizeMax(uploadSize * 3 * 1024 * 1024);
      upload.setFileSizeMax(uploadSize * 1024 * 1024);
      

      List<FileItem> items = upload.parseRequest(request);
      
      Iterator<FileItem> iter = items.iterator();
      
      List<Integer> eIds = new ArrayList();
      List<String> ps = new ArrayList();
      List<String> anses = new ArrayList();
      int countp = 0;
      

      List<Evaluation> list = new ArrayList();
      int size = eIds.size();
      for (int i = 0; i < size; i++)
      {
        Evaluation ev = new Evaluation();
        ev.setEmpId(Integer.valueOf(loginUser.getId()));
        ev.setEmpName(loginUser.getName());
        
        int evId = ((Integer)eIds.get(i)).intValue();
        Evaluation e = (Evaluation)evMap.get(Integer.valueOf(evId));
        String p = (String)ps.get(i);
        if (e.getPoint() == null)
        {
          ev.setAns(p);
        }
        else
        {
          int po = Integer.parseInt(p);
          countp += po;
          ev.setPoint(Integer.valueOf(po));
          ev.setAns((String)anses.get(i));
        }
        ev.setId(evId);
        
        list.add(ev);
      }
      evSum.setPoint(Integer.valueOf(countp));
      
      this.evService.saveEvaluation(list, evSum);
    }
//    catch (FileUploadBase.SizeLimitExceededException e)
//    {
//      msg = "您上传的文档超过规定大小（" + uploadSize + "MB）。";
//      e.printStackTrace();
//    }
//    catch (FileUploadBase.InvalidContentTypeException e)
//    {
//      msg = "您上传的文档格式不符合规定。（服务器支持的格式：pdf, doc, docx, xls, xlsx）";
//      e.printStackTrace();
//    }
    catch (FileUploadException e)
    {
      msg = "上传失败，请求异常";
      e.printStackTrace();
    }
    catch (Exception e)
    {
      msg = "提交失败。";
      e.printStackTrace();
    }
    request.setAttribute("msg", msg);
    







    request.getRequestDispatcher("/WEB-INF/views/oa/ev/mine.jsp").forward(request, response);
  }
}
