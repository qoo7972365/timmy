/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class ManagerHeader_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  24 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  25 */   static { _jspx_dependants.put("/jsp/includes/commonIncludes.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
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
/*     */ 
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  47 */     HttpSession session = null;
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
/*  67 */       out.write("<!--$Id$-->\n\n");
/*  68 */       String isconsole = (String)session.getAttribute("isconsole");
/*     */       
/*  70 */       if ((isconsole != null) && (isconsole.equals("true")))
/*     */       {
/*  72 */         session.setAttribute("console", isconsole);
/*  73 */         out.write(10);
/*  74 */         out.write(9);
/*  75 */         JspRuntimeLibrary.include(request, response, "/jsp/ManagerHeader_console.jsp", out, false);
/*  76 */         out.write(10);
/*     */       }
/*     */       else
/*     */       {
/*  80 */         isconsole = "false";
/*  81 */         session.setAttribute("console", isconsole);
/*  82 */         out.write(10);
/*  83 */         out.write(9);
/*  84 */         out.write("<!-- $Id$ -->\n\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\"/>\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n\n<!-- JS include via file -->\n");
/*  85 */         out.write("\n<!-- Scripts getting included in a common place. TODO: Need to merge the script files. This is not applicable for SQL Manager-->\n<script type=\"text/javascript\" src=\"/template/appmanager.js\"></script>\n<script type=\"text/javascript\" src=\"/template/validation.js\"></script>\n<script type=\"text/javascript\" src=\"/template/dnd.js\"></script>\n<script type=\"text/javascript\" src=\"/template/TabDrag.js\"></script>\n<script type=\"text/javascript\" src=\"/template/dropdown.js\"></script>\n<script type=\"text/javascript\" src=\"/template/Dialog.js\"></script>\n<script type=\"text/javascript\" src=\"/template/Utils.js\"></script>\n<script type=\"text/javascript\" src=\"/template/jquery-1.11.0.min.js\"></script>\n<script type=\"text/javascript\" src=\"/template/jquery-migrate-1.2.1.min.js\"></script>\n<script type=\"text/javascript\" src=\"/template/jquery-ui.min.js\" ></script>\n");
/*  86 */         out.write(10);
/*  87 */         out.write(9);
/*  88 */         JspRuntimeLibrary.include(request, response, "/jsp/header_app.jsp", out, false);
/*  89 */         out.write(10);
/*     */       }
/*  91 */       out.write(10);
/*  92 */       out.write(10);
/*     */     } catch (Throwable t) {
/*  94 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  95 */         out = _jspx_out;
/*  96 */         if ((out != null) && (out.getBufferSize() != 0))
/*  97 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  98 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 101 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {}
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ManagerHeader_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */