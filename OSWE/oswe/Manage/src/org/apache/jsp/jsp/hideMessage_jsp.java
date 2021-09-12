/*    */ package org.apache.jsp.jsp;
/*    */ 
/*    */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*    */ import java.io.IOException;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Map;
/*    */ import javax.el.ExpressionFactory;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspFactory;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.jasper.runtime.JspSourceDependent;
/*    */ 
/*    */ public final class hideMessage_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*    */ {
/* 19 */   private static final JspFactory _jspxFactory = ;
/*    */   
/*    */   private static Map<String, Long> _jspx_dependants;
/*    */   
/*    */   private ExpressionFactory _el_expressionfactory;
/*    */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*    */   
/*    */   public Map<String, Long> getDependants()
/*    */   {
/* 28 */     return _jspx_dependants;
/*    */   }
/*    */   
/*    */   public void _jspInit() {
/* 32 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 33 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */ 
/*    */   public void _jspDestroy() {}
/*    */   
/*    */ 
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, javax.servlet.ServletException
/*    */   {
/* 43 */     javax.servlet.http.HttpSession session = null;
/*    */     
/*    */ 
/* 46 */     JspWriter out = null;
/* 47 */     Object page = this;
/* 48 */     JspWriter _jspx_out = null;
/* 49 */     PageContext _jspx_page_context = null;
/*    */     
/*    */     try
/*    */     {
/* 53 */       response.setContentType("text/html");
/* 54 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 56 */       _jspx_page_context = pageContext;
/* 57 */       ServletContext application = pageContext.getServletContext();
/* 58 */       ServletConfig config = pageContext.getServletConfig();
/* 59 */       session = pageContext.getSession();
/* 60 */       out = pageContext.getOut();
/* 61 */       _jspx_out = out;
/*    */       
/* 63 */       out.write("<!--$Id$-->\n\n");
/* 64 */       ManagedApplication mo = null;
/* 65 */       mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 66 */       if (mo == null) {
/* 67 */         mo = new ManagedApplication();
/* 68 */         _jspx_page_context.setAttribute("mo", mo, 1);
/*    */       }
/* 70 */       out.write(10);
/*    */       
/* 72 */       if ("loginFirst".equalsIgnoreCase(request.getParameter("hide"))) {
/* 73 */         mo.executeUpdateStmt("update AM_GLOBALCONFIG set VALUE='false' where NAME='intropage'");
/* 74 */         ((Hashtable)application.getAttribute("globalconfig")).put("intropage", "false");
/* 75 */       } else if ("newfeatures".equalsIgnoreCase(request.getParameter("hide"))) {
/* 76 */         mo.executeUpdateStmt("update AM_GLOBALCONFIG set VALUE='false' where NAME='newfeatures'");
/* 77 */         ((Hashtable)application.getAttribute("globalconfig")).put("newfeatures", "false");
/* 78 */       } else if ("show.apminsighttab.alert".equalsIgnoreCase(request.getParameter("hide")))
/*    */       {
/*    */         try {
/* 81 */           com.adventnet.appmanager.util.DBUtil.insertIntoGlobalConfig("show.apminsighttab.alert", "false");
/*    */         }
/*    */         catch (Exception e)
/*    */         {
/* 85 */           e.printStackTrace();
/*    */         }
/*    */       }
/*    */       
/* 89 */       out.write(10);
/*    */     } catch (Throwable t) {
/* 91 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 92 */         out = _jspx_out;
/* 93 */         if ((out != null) && (out.getBufferSize() != 0))
/* 94 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 95 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 98 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\hideMessage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */