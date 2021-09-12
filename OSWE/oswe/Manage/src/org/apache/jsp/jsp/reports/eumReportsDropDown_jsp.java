/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class eumReportsDropDown_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  32 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  36 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  37 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  47 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  50 */     JspWriter out = null;
/*  51 */     Object page = this;
/*  52 */     JspWriter _jspx_out = null;
/*  53 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  57 */       response.setContentType("text/html");
/*  58 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  60 */       _jspx_page_context = pageContext;
/*  61 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  62 */       ServletConfig config = pageContext.getServletConfig();
/*  63 */       session = pageContext.getSession();
/*  64 */       out = pageContext.getOut();
/*  65 */       _jspx_out = out;
/*     */       
/*  67 */       out.write("\n\n\n\n\n\n\n");
/*     */       
/*     */ 
/*  70 */       String monType = "";
/*  71 */       String ownerCondition = "";
/*  72 */       boolean isUserResourceEnabled = false;
/*  73 */       String loginUserid = null;
/*  74 */       if ((request.getParameter("monType") != null) && (!request.getParameter("monType").trim().equals("")) && (!request.getParameter("monType").trim().equals("None")))
/*     */       {
/*  76 */         if (Constants.isPrivilegedUser(request))
/*     */         {
/*  78 */           if (Constants.isUserResourceEnabled()) {
/*  79 */             isUserResourceEnabled = true;
/*  80 */             loginUserid = Constants.getLoginUserid(request);
/*     */           } else {
/*  82 */             Vector<String> allowedResid = ReportUtilities.getResourceIdentity(request.getRemoteUser());
/*  83 */             ownerCondition = " and " + ReportUtilities.getCondition("RESOURCEID", allowedResid);
/*     */           }
/*     */         }
/*     */         
/*  87 */         monType = request.getParameter("monType");
/*  88 */         String resQuery = null;
/*  89 */         if (isUserResourceEnabled) {
/*  90 */           resQuery = "select am.RESOURCEID,am.DISPLAYNAME from AM_USERRESOURCESTABLE, AM_ManagedObject am,AM_ManagedResourceType bm where AM_USERRESOURCESTABLE.RESOURCEID=am.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and bm.RESOURCETYPE=am.TYPE and bm.SUBGROUP in ('" + monType + "') and " + ReportUtilities.getCondition("am.RESOURCEID", Constants.getEUMMonitorIds(false)) + " order by am.TYPE asc";
/*     */         } else {
/*  92 */           resQuery = "select am.RESOURCEID,am.DISPLAYNAME from AM_ManagedObject am,AM_ManagedResourceType bm where bm.RESOURCETYPE=am.TYPE and bm.SUBGROUP in ('" + monType + "') and " + ReportUtilities.getCondition("am.RESOURCEID", Constants.getEUMMonitorIds(false)) + ownerCondition + " order by am.TYPE asc";
/*     */         }
/*     */         
/*  95 */         ResultSet resRs = null;
/*     */         try
/*     */         {
/*  98 */           resRs = AMConnectionPool.executeQueryStmt(resQuery);
/*     */           
/* 100 */           while (resRs.next())
/*     */           {
/* 102 */             out.write("\n\t\t\t\t<option value=\"");
/* 103 */             out.print(resRs.getString("RESOURCEID"));
/* 104 */             out.write(34);
/* 105 */             out.write(62);
/* 106 */             out.print(resRs.getString("DISPLAYNAME"));
/* 107 */             out.write("</option>\n\t\t\t");
/*     */           }
/*     */         }
/*     */         catch (Exception childEx)
/*     */         {
/* 112 */           childEx.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/* 116 */           AMConnectionPool.closeStatement(resRs);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 121 */         out.write("\n\t\t<option value=\"All\">-----Select A Monitor-----</option> ");
/* 122 */         out.write(10);
/* 123 */         out.write(9);
/*     */       }
/*     */       
/*     */ 
/* 127 */       out.write(10);
/* 128 */       out.write(10);
/*     */     } catch (Throwable t) {
/* 130 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 131 */         out = _jspx_out;
/* 132 */         if ((out != null) && (out.getBufferSize() != 0))
/* 133 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 134 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 137 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\eumReportsDropDown_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */