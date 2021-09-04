/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
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
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class MyField_005fUserDetails_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  21 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
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
/*  36 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  37 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  38 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  42 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  49 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  52 */     JspWriter out = null;
/*  53 */     Object page = this;
/*  54 */     JspWriter _jspx_out = null;
/*  55 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  59 */       response.setContentType("text/html");
/*  60 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  62 */       _jspx_page_context = pageContext;
/*  63 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  64 */       ServletConfig config = pageContext.getServletConfig();
/*  65 */       session = pageContext.getSession();
/*  66 */       out = pageContext.getOut();
/*  67 */       _jspx_out = out;
/*     */       
/*  69 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<script type=\"text/javascript\" language=\"JavaScript1.2\" src=\"/template/jquery-1.3.1.min.js\"></script>\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  70 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  72 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n<title>User Details</title>\t");
/*  73 */       out.write("\n\n</head>\n<style>\n.userDetialsStyle {\n    background-color: #EBF3F8;\n    color: #000000;\n    font-family: Arial,Helvetica,sans-serif;\n    font-size: 11px;\n    height: 20px;\n    border-bottom: 5px solid black;\n    padding-left: 5px;\n    \n}\n</style>\n");
/*     */       
/*  75 */       HashMap userData = (HashMap)request.getAttribute("userData");
/*     */       
/*  77 */       out.write("\n<body>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<table class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n<tr>\n<td class=\"tableheadingtrans\" colspan=\"3\">\nUser Details\t");
/*  78 */       out.write("\n</td>\n</tr>\n<tr>\n<td width=\"40%\" align=\"center\">\n\n<img src='");
/*  79 */       out.print(userData.get("imagepath"));
/*  80 */       out.write("' align=\"center\" border='0' width=\"100\" height=\"100\">\n\n</td>\n<td width=\"60%\">\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"10\">\n<tr class=\"monitorinfoodd\">\n<td width=\"50%\">\nUserName\t");
/*  81 */       out.write("\n</td>\n<td  width=\"50%\">\n");
/*  82 */       out.print(userData.get("username"));
/*  83 */       out.write("\n</td>\n</tr>\n<tr class=\"monitorinfoodd\">\n<td >\nDescription\t");
/*  84 */       out.write("\n</td>\n<td >\n");
/*  85 */       out.print(userData.get("description"));
/*  86 */       out.write("\n</td>\n</tr>\n<tr class=\"monitorinfoodd\">\n<td>\nEmail\t");
/*  87 */       out.write("\n</td>\n<td>\n");
/*  88 */       out.print(userData.get("email"));
/*  89 */       out.write("\n</td>\n</tr>\n<tr class=\"monitorinfoodd\">\n<td >\nRole\t");
/*  90 */       out.write("\n</td>\n<td >\n");
/*  91 */       out.print(userData.get("groupname"));
/*  92 */       out.write("\n</td>\n</tr>\n</table>\n</td>\n</tr>\n\n</table>\n\n</body>\n</html>");
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
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 107 */     PageContext pageContext = _jspx_page_context;
/* 108 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 110 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 111 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 112 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 114 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 116 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 117 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 118 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 119 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 120 */       return true;
/*     */     }
/* 122 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 123 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MyField_005fUserDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */