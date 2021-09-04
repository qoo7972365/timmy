/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ 
/*     */ public final class shadowBoxLayout_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  18 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  27 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  31 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  32 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  42 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  45 */     JspWriter out = null;
/*  46 */     Object page = this;
/*  47 */     JspWriter _jspx_out = null;
/*  48 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  52 */       response.setContentType("text/html");
/*  53 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  55 */       _jspx_page_context = pageContext;
/*  56 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  57 */       ServletConfig config = pageContext.getServletConfig();
/*  58 */       session = pageContext.getSession();
/*  59 */       out = pageContext.getOut();
/*  60 */       _jspx_out = out;
/*     */       
/*  62 */       out.write("<!--$Id$-->\n\n<style type=\"text/css\">\n\tA.bcactive:HOVER,span.bcactive:HOVER\n\t{\n\t\ttext-decoration: underline;\n\t}\n\t.bcactive\n\t{\n\t\tfont-size: 13px;\n\t}\n\t.loadInPage\n\t{\n\t\tcursor: pointer;\n\t}\n</style>\n");
/*     */       
/*  64 */       String contentId = "snapShotContent";
/*  65 */       String tableHeader = "Table Header";
/*  66 */       String showSegmentDrop = "";
/*  67 */       if (request.getParameter("contentAreaId") != null)
/*     */       {
/*  69 */         contentId = request.getParameter("contentAreaId");
/*     */       }
/*  71 */       if (request.getParameter("headerTitle") != null)
/*     */       {
/*  73 */         tableHeader = request.getParameter("headerTitle");
/*     */       }
/*  75 */       if (request.getAttribute("headerTitle") != null)
/*     */       {
/*  77 */         tableHeader = (String)request.getAttribute("headerTitle");
/*     */       }
/*     */       
/*  80 */       if (request.getParameter("segmentDrop") != null)
/*     */       {
/*  82 */         showSegmentDrop = request.getParameter("segmentDrop");
/*     */       }
/*     */       
/*  85 */       out.write("\n<div class=\"TableRighLeftBottomBorder\" style=\"padding-top: 30px; padding-bottom: 50px;\">\n\n\t<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding-bottom: 5px;\">\n\t\t<tbody>\n\t\t\t<tr>\n\t\t\t\t<td nowrap=\"nowrap\">\n\t\t\t\t\t<span class=\"conf-mon-txt\" style=\"padding-left:15px;\">");
/*  86 */       out.print(tableHeader);
/*  87 */       out.write("</span>\n\t\t\t\t\t");
/*  88 */       if (contentId.equals(showSegmentDrop)) {
/*  89 */         out.write("\n\t\t\t\t\t<span class=\"conf-mon-txt\" style=\"float: right;\"> Segment by Destiantion ");
/*  90 */         out.write("\n\t\t\t\t\t\t<select id=\"EUMMonitorContent\" name=\"EUMMonitor\" class=\"formtext\">\n\t\t\t\t\t\t\t<option value=\"All\">-----Select A Monitor-----</option> ");
/*  91 */         out.write("\n\t\t\t\t\t\t</select>\n\t\t\t\t\t</span>\n\t\t\t\t\t");
/*     */       }
/*  93 */       out.write("\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</tbody>\n\t</table>\n\t<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tbody>\n\t\t\t<tr>\n\t\t\t\t<td width=\"14\" class=\"topleftcurveImg\"></td>\n\t\t\t\t<td class=\"topbgImg\"></td>\n\t\t\t\t<td width=\"16\" class=\"toprightcurveImg\"></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"leftbgImg\"></td>\n\t\t\t\t<td id=\"");
/*  94 */       out.print(contentId);
/*  95 */       out.write("\">\n\t\t\t\t\n\t\t\t\t</td>\n\t\t\t<td valign=\"top\" class=\"rightbgImg\"></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td width=\"14\" class=\"bottomleftcurveImg\"></td>\n\t\t\t\t<td class=\"bottombgImg\"></td>\n\t\t\t\t<td class=\"bottomrightcurveImg\"></td>\n\t\t\t</tr>\n\t\t</tbody>\n\t</table>\n</div>\n");
/*     */     } catch (Throwable t) {
/*  97 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  98 */         out = _jspx_out;
/*  99 */         if ((out != null) && (out.getBufferSize() != 0))
/* 100 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 101 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 104 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\shadowBoxLayout_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */