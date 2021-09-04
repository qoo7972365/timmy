/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
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
/*     */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class header_jsp extends HttpJspBase implements JspSourceDependent
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
/*  58 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  59 */       ServletConfig config = pageContext.getServletConfig();
/*  60 */       session = pageContext.getSession();
/*  61 */       out = pageContext.getOut();
/*  62 */       _jspx_out = out;
/*     */       
/*  64 */       out.write("<!DOCTYPE html>\n");
/*  65 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  66 */       out.write(10);
/*  67 */       out.write(10);
/*  68 */       out.write(10);
/*  69 */       out.write(10);
/*     */       
/*  71 */       if (Constants.sqlManager)
/*     */       {
/*  73 */         out.write("\n    \t");
/*  74 */         JspRuntimeLibrary.include(request, response, "/jsp/mssql/sqlheader_app.jsp", out, false);
/*  75 */         out.write("\t\n    ");
/*     */       }
/*  77 */       else if ((OEMUtil.getOEMString("remove.header") != null) && (OEMUtil.getOEMString("remove.header").equals("true")))
/*     */       {
/*     */ 
/*  80 */         out.write("\n    \t");
/*  81 */         JspRuntimeLibrary.include(request, response, "/jsp/test.jsp", out, false);
/*  82 */         out.write("\n    ");
/*     */ 
/*     */       }
/*  85 */       else if (Constants.isIt360)
/*     */       {
/*  87 */         out.write("\n\t\t\t");
/*  88 */         JspRuntimeLibrary.include(request, response, "/it360/jsp/header_IT360.jsp", out, false);
/*  89 */         out.write(10);
/*  90 */         out.write(9);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*  95 */         out.write(10);
/*  96 */         out.write(9);
/*  97 */         out.write(9);
/*  98 */         JspRuntimeLibrary.include(request, response, "/jsp/header_app.jsp", out, false);
/*  99 */         out.write(10);
/* 100 */         out.write(9);
/*     */       }
/*     */     }
/*     */     catch (Throwable t) {
/* 104 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 105 */         out = _jspx_out;
/* 106 */         if ((out != null) && (out.getBufferSize() != 0))
/* 107 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 108 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 111 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\header_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */