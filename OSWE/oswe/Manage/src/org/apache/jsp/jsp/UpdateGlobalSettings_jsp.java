/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class UpdateGlobalSettings_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  29 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  33 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  34 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  44 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  47 */     JspWriter out = null;
/*  48 */     Object page = this;
/*  49 */     JspWriter _jspx_out = null;
/*  50 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  54 */       response.setContentType("text/html");
/*  55 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  57 */       _jspx_page_context = pageContext;
/*  58 */       ServletContext application = pageContext.getServletContext();
/*  59 */       ServletConfig config = pageContext.getServletConfig();
/*  60 */       session = pageContext.getSession();
/*  61 */       out = pageContext.getOut();
/*  62 */       _jspx_out = out;
/*     */       
/*  64 */       out.write("<!--$Id$-->\n\n");
/*  65 */       ManagedApplication mo = null;
/*  66 */       mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/*  67 */       if (mo == null) {
/*  68 */         mo = new ManagedApplication();
/*  69 */         _jspx_page_context.setAttribute("mo", mo, 1);
/*     */       }
/*  71 */       out.write(10);
/*  72 */       out.write(10);
/*  73 */       out.write(10);
/*     */       
/*  75 */       String check = request.getParameter("page");
/*  76 */       String pass = request.getParameter("redirect");
/*  77 */       String key = request.getParameter("key");
/*  78 */       String value = request.getParameter("value");
/*     */       
/*  80 */       ((Hashtable)application.getAttribute("globalconfig")).put(key, value);
/*  81 */       mo.executeUpdateStmt("update AM_GLOBALCONFIG set VALUE='" + value + "' where NAME='" + key + "'");
/*  82 */       if ((check != null) && (check.equalsIgnoreCase("login")))
/*     */       {
/*  84 */         response.sendRedirect("/MyPage.do?method=viewDashBoard");
/*     */ 
/*     */ 
/*     */       }
/*  88 */       else if ((pass != null) && (pass.equalsIgnoreCase("support")))
/*     */       {
/*  90 */         response.sendRedirect("/common/serverinfo.do");
/*     */       }
/*     */       else
/*     */       {
/*  94 */         String url = "/MyPage.do?method=viewDashBoard&toredirect=true";
/*     */         
/*     */ 
/*  97 */         out.write(10);
/*  98 */         out.write(10);
/*     */         
/* 100 */         _jspx_page_context.forward(url);
/*     */       }
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 105 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 106 */         out = _jspx_out;
/* 107 */         if ((out != null) && (out.getBufferSize() != 0))
/* 108 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 109 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 112 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\UpdateGlobalSettings_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */