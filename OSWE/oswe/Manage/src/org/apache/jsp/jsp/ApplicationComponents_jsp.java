/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class ApplicationComponents_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  28 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  32 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  33 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  43 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  46 */     JspWriter out = null;
/*  47 */     Object page = this;
/*  48 */     JspWriter _jspx_out = null;
/*  49 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  53 */       response.setContentType("text/html");
/*  54 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  56 */       _jspx_page_context = pageContext;
/*  57 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  58 */       ServletConfig config = pageContext.getServletConfig();
/*  59 */       session = pageContext.getSession();
/*  60 */       out = pageContext.getOut();
/*  61 */       _jspx_out = out;
/*     */       
/*  63 */       out.write(10);
/*  64 */       GetDataFromDB dataHandler = null;
/*  65 */       dataHandler = (GetDataFromDB)_jspx_page_context.getAttribute("dataHandler", 1);
/*  66 */       if (dataHandler == null) {
/*  67 */         dataHandler = new GetDataFromDB();
/*  68 */         _jspx_page_context.setAttribute("dataHandler", dataHandler, 1);
/*     */       }
/*  70 */       out.write(10);
/*     */       
/*  72 */       String resourceName = request.getParameter("resourceName");
/*  73 */       String type = request.getParameter("type");
/*  74 */       String webappname = request.getParameter("webappname");
/*  75 */       if (type == null)
/*  76 */         type = "WEBAPP";
/*  77 */       ArrayList data = null;
/*  78 */       java.util.Date obj = null;
/*  79 */       if (type.equals("JDBC"))
/*     */       {
/*     */ 
/*  82 */         out.write(10);
/*  83 */         out.write(9);
/*     */         
/*  85 */         _jspx_page_context.forward("../jsp/ShowJDBCDetails.jsp");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*  93 */       else if (type.equals("EJB"))
/*     */       {
/*     */ 
/*  96 */         out.write(10);
/*  97 */         out.write(9);
/*     */         
/*  99 */         _jspx_page_context.forward("../jsp/ShowEJBDetails.jsp");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 107 */       else if (type.equals("WEBAPP"))
/*     */       {
/*     */ 
/* 110 */         out.write(10);
/* 111 */         out.write(9);
/*     */         
/* 113 */         _jspx_page_context.forward("../jsp/ShowKeyValues.jsp");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 121 */       else if ((data == null) || (data.size() == 0))
/*     */       {
/*     */ 
/* 124 */         out.write(10);
/* 125 */         out.write(9);
/*     */         
/* 127 */         _jspx_page_context.forward("../jsp/NoData.jsp" + ("../jsp/NoData.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceName", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceName), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("message", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("No Data ", request.getCharacterEncoding()));
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/*     */ 
/* 136 */         out.write(10);
/* 137 */         out.write(10);
/*     */       }
/* 139 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 140 */         out = _jspx_out;
/* 141 */         if ((out != null) && (out.getBufferSize() != 0))
/* 142 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 143 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 146 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ApplicationComponents_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */