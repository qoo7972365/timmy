/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
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
/*     */ public final class ErrorPage_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  27 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  31 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  32 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  42 */     javax.servlet.http.HttpSession session = null;
/*  43 */     Throwable exception = org.apache.jasper.runtime.JspRuntimeLibrary.getThrowable(request);
/*  44 */     if (exception != null) {
/*  45 */       response.setStatus(500);
/*     */     }
/*     */     
/*     */ 
/*  49 */     JspWriter out = null;
/*  50 */     Object page = this;
/*  51 */     JspWriter _jspx_out = null;
/*  52 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  56 */       response.setContentType("text/html");
/*  57 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  59 */       _jspx_page_context = pageContext;
/*  60 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  61 */       ServletConfig config = pageContext.getServletConfig();
/*  62 */       session = pageContext.getSession();
/*  63 */       out = pageContext.getOut();
/*  64 */       _jspx_out = out;
/*     */       
/*  66 */       out.write(10);
/*  67 */       out.write(10);
/*  68 */       out.write(10);
/*     */       
/*  70 */       StringBuffer values = request.getRequestURL();
/*  71 */       String querystring = request.getQueryString();
/*  72 */       request.setAttribute("pathinfo", values);
/*  73 */       request.setAttribute("querystring", querystring);
/*     */       
/*  75 */       if (exception != null)
/*     */       {
/*  77 */         exception.printStackTrace();
/*  78 */         request.setAttribute("amexception", exception);
/*     */         
/*  80 */         out.write(10);
/*  81 */         out.write(9);
/*     */         
/*  83 */         _jspx_page_context.forward("/common/serverinfo.do");
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/*     */ 
/*  91 */         request.setAttribute("amexception", new Exception());
/*     */         
/*  93 */         out.write(10);
/*  94 */         out.write(9);
/*     */         
/*  96 */         _jspx_page_context.forward("/common/serverinfo.do");
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/*     */ 
/* 106 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 107 */         out = _jspx_out;
/* 108 */         if ((out != null) && (out.getBufferSize() != 0))
/* 109 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 110 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 113 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ErrorPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */