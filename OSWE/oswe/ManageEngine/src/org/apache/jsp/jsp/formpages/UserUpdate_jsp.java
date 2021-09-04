/*     */ package org.apache.jsp.jsp.formpages;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import java.io.IOException;
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
/*     */ public final class UserUpdate_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  31 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  35 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  36 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  46 */     HttpSession session = null;
/*     */     
/*     */ 
/*  49 */     JspWriter out = null;
/*  50 */     Object page = this;
/*  51 */     JspWriter _jspx_out = null;
/*  52 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  56 */       response.setContentType("text/html;charset=UTF-8");
/*  57 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  59 */       _jspx_page_context = pageContext;
/*  60 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  61 */       ServletConfig config = pageContext.getServletConfig();
/*  62 */       session = pageContext.getSession();
/*  63 */       out = pageContext.getOut();
/*  64 */       _jspx_out = out;
/*     */       
/*  66 */       out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<meta http-equiv=\"Content-Type\" content=\"UTF-8\">\n<!--$Id$-->\n\n\n\n\n\n");
/*  67 */       out.write(10);
/*     */       
/*  69 */       String faviconHref = "/favicon.ico";
/*  70 */       String productName = OEMUtil.getOEMString("product.name");
/*     */       
/*  72 */       out.write("\n<html>\n<head>\n<title>");
/*  73 */       out.print(productName);
/*  74 */       out.write("</title>\n\n<link REL=\"SHORTCUT ICON\" HREF=\"");
/*  75 */       out.print(faviconHref);
/*  76 */       out.write("\">\n</head>\n\n<body style=\"background-color:#fff;\">\n\n<style type=\"text/css\">\n\t#container-error\n\t{\n\t\tborder:1px solid #c1c1c1;\n\t\tbackground: #fff; font:11px Arial, Helvetica, sans-serif; width:90%; margin:80px;\n\t}\n\n\t#header-error\n\t{\n\t\tbackground: #ededed; line-height:18px;\n\t\tpadding: 15px; color:#000; font-size:8px;\n\t}\n\n\t#header-error h1\n\t{\n\t\tmargin: 0;  color:#000;\n\t\tfont-size:14px;\n\t}\n\n\t#content-error\n\t{\n\t\tclear: left; background-color:#fff9f9;\n\t\tpadding: 20px; height:170px;\n\t}\n\t#content-error1\n\t{\n\t\tclear: left; background-color:#fff9f9;\n\t\tpadding-left: 20px;\t\t\n\t\tpadding-right: 2px;\n\t\tpadding-top: 2px;\n\t\t height:200px;\n\t}\n\t#content-error h2\n\t{\n\t\tcolor: #000;\n\t\tfont-size: 100%;\n\t\tmargin: 0 0 .5em;\n\t}\n\t#content-error p\n\t{\n\t\tcolor: #000;\n\t\tfont-size: 12px;\n\t\tmargin: 3em 0 .5em;\n\t}\n\t\t#content-error1 p\n\t{\n\t\tcolor: #000;\n\t\tfont-size: 12px;\n\t\t\n\t}\t#content-error1 li\n\t{\n\t\tcolor: #000;\n\t\tfont-size: 12px;\n\t\tpadding:2px;\n\n\t}\n\t#footer-error\n\t{\n\t\tbackground: #ededed;\n\t\ttext-align: right;\n\t\tpadding: 10px;\n\t\theight: 1%; font-size:10px;\n");
/*  77 */       out.write("\t}\n</style>\n\n<div id=\"container-error\">\n\t<div id=\"header-error\">\n\t\t<h1>\n\t\t\t");
/*  78 */       out.print(productName);
/*  79 */       out.write("\n\t\t</h1>\n\t</div>\n\t");
/*  80 */       if ((request.getParameter("EnableCookiesInIE") != null) && (request.getParameter("EnableCookiesInIE").equals("true"))) {
/*  81 */         out.write("\n\t<div id=\"content-error1\">\n\t\t<p>\n\t\t<img src=\"/images/icon_cross.gif\" style=\"position: relative; top: 18px; right: 9px;\">\t\t\n\t\t\t");
/*  82 */         out.print(FormatUtil.getString("am.webclient.userauthorization.nonstandard.hostname"));
/*  83 */         out.write("\t\n\t</div>\n\t");
/*     */       } else {
/*  85 */         out.write("\n\t<div id=\"content-error\" align=\"center\">\n\t\t<p>\n\t\t\t<img src=\"/images/icon_getmoredata.gif\" style=\"position: relative; top: 18px; right: 9px;\">\n\t\t \t");
/*  86 */         out.print(FormatUtil.getString("am.webclient.userauthorization.logout"));
/*  87 */         out.write("\n\t\t</p>\n\t</div>\n\t");
/*     */       }
/*  89 */       out.write("\n\t<div id=\"footer-error\">\n\t\t&copy; ");
/*  90 */       out.print(OEMUtil.getOEMString("company.currentyear"));
/*  91 */       out.write(32);
/*  92 */       out.print(OEMUtil.getOEMString("company.name"));
/*  93 */       out.write(9);
/*  94 */       out.write("\n\t</div>\n</div>\n\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/*  96 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  97 */         out = _jspx_out;
/*  98 */         if ((out != null) && (out.getBufferSize() != 0))
/*  99 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 100 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 103 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\formpages\UserUpdate_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */