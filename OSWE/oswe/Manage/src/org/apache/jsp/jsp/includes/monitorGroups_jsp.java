/*     */ package org.apache.jsp.jsp.includes;
/*     */ 
/*     */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*     */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class monitorGroups_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*     */   public ArrayList getMGroupsCreatedInAdminServer(ArrayList aListOfAllMonitorGroups)
/*     */   {
/*  27 */     ArrayList aListAdminMonitorGrps = new ArrayList();
/*     */     try {
/*  29 */       for (int i = 0; i < aListOfAllMonitorGroups.size(); i++) {
/*  30 */         ArrayList innerList = (ArrayList)aListOfAllMonitorGroups.get(i);
/*  31 */         if ((innerList != null) && (innerList.size() >= 2))
/*     */         {
/*     */           try
/*     */           {
/*  35 */             String strMgId = (String)innerList.get(1);
/*  36 */             int mgId = Integer.parseInt(strMgId);
/*  37 */             if (mgId < 10000000) {
/*  38 */               aListAdminMonitorGrps.add(innerList);
/*     */             }
/*  40 */             String grpCreatedMasName = CommDBUtil.getManagedServerNameWithPort(strMgId);
/*  41 */             innerList.add(grpCreatedMasName);
/*     */           }
/*     */           catch (Exception ex1) {}
/*     */         }
/*     */       }
/*     */     } catch (Exception ex2) {
/*  47 */       ex2.printStackTrace();
/*     */     }
/*  49 */     return aListAdminMonitorGrps;
/*     */   }
/*     */   
/*  52 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  61 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  65 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  66 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  76 */     HttpSession session = null;
/*     */     
/*     */ 
/*  79 */     JspWriter out = null;
/*  80 */     Object page = this;
/*  81 */     JspWriter _jspx_out = null;
/*  82 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  86 */       response.setContentType("text/html");
/*  87 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  89 */       _jspx_page_context = pageContext;
/*  90 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  91 */       ServletConfig config = pageContext.getServletConfig();
/*  92 */       session = pageContext.getSession();
/*  93 */       out = pageContext.getOut();
/*  94 */       _jspx_out = out;
/*     */       
/*  96 */       out.write("<!--$Id$-->\n\n\n\n");
/*     */       
/*     */       try
/*     */       {
/* 100 */         boolean isprivilege = false;
/* 101 */         if (com.adventnet.appmanager.struts.beans.ClientDBUtil.isPrivilegedUser(request)) {
/* 102 */           isprivilege = true;
/*     */         }
/* 104 */         request.setAttribute("checkForMonitorGroup", Boolean.valueOf(isprivilege));
/*     */         
/*     */ 
/* 107 */         ArrayList dynamicSites = AlarmUtil.getSiteMonitorGroups();
/* 108 */         if (dynamicSites != null)
/*     */         {
/* 110 */           request.setAttribute("dynamicSites", dynamicSites);
/*     */         }
/*     */         
/* 113 */         ArrayList mgList = new ArrayList();
/* 114 */         if (EnterpriseUtil.isIt360MSPEdition())
/*     */         {
/* 116 */           AMActionForm form = (AMActionForm)request.getAttribute("AMActionForm");
/* 117 */           ArrayList orgs = AlarmUtil.getCustomerNames();
/*     */           
/* 119 */           if (orgs != null)
/*     */           {
/* 121 */             request.setAttribute("customers", orgs);
/*     */           }
/*     */           
/*     */ 
/* 125 */           if (form != null)
/*     */           {
/* 127 */             String customerName = form.getOrganization();
/* 128 */             if (customerName != null)
/*     */             {
/* 130 */               mgList = AlarmUtil.getSiteMonitorGroups(customerName);
/*     */             }
/*     */             
/*     */           }
/*     */           
/*     */ 
/*     */         }
/* 137 */         else if (isprivilege)
/*     */         {
/* 139 */           mgList = AlarmUtil.getConfiguredGroups(request, false, false, true);
/*     */         }
/*     */         else
/*     */         {
/* 143 */           mgList = AlarmUtil.getConfiguredGroups(null, false, false, true);
/*     */         }
/*     */         
/* 146 */         if (mgList != null)
/*     */         {
/* 148 */           request.setAttribute("applications", mgList);
/* 149 */           if (EnterpriseUtil.isAdminServer()) {
/* 150 */             ArrayList adminMGroups = getMGroupsCreatedInAdminServer(mgList);
/* 151 */             request.setAttribute("AllMonitorGroupsInAdminServer", mgList);
/* 152 */             request.setAttribute("MonitorGroupsCreatedInAdminServer", adminMGroups);
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 158 */         e.printStackTrace();
/*     */       }
/*     */       
/* 161 */       out.write(10);
/* 162 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 164 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 165 */         out = _jspx_out;
/* 166 */         if ((out != null) && (out.getBufferSize() != 0))
/* 167 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 168 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 171 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\monitorGroups_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */