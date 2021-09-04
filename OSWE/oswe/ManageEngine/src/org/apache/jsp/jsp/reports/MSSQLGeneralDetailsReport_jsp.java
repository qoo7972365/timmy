/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.bean.SummaryBean;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.SkipPageException;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class MSSQLGeneralDetailsReport_jsp
/*     */   extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  29 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  38 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  42 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  43 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  53 */     HttpSession session = null;
/*     */     
/*     */ 
/*  56 */     JspWriter out = null;
/*  57 */     Object page = this;
/*  58 */     JspWriter _jspx_out = null;
/*  59 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  63 */       response.setContentType("text/html");
/*  64 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  66 */       _jspx_page_context = pageContext;
/*  67 */       ServletContext application = pageContext.getServletContext();
/*  68 */       ServletConfig config = pageContext.getServletConfig();
/*  69 */       session = pageContext.getSession();
/*  70 */       out = pageContext.getOut();
/*  71 */       _jspx_out = out;
/*     */       
/*  73 */       out.write("<!--$Id$-->\n\n\n\n\n\n \n  \n \n \n \n\n\n");
/*  74 */       SummaryBean sumgraph = null;
/*  75 */       sumgraph = (SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/*  76 */       if (sumgraph == null) {
/*  77 */         sumgraph = new SummaryBean();
/*  78 */         _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*     */       }
/*  80 */       out.write("\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n\n");
/*     */       
/*  82 */       if ("pdf".equals(request.getAttribute("sp-report-type"))) {
/*  83 */         pageContext.forward("/servlet/PDFReport");
/*     */       }
/*  85 */       else if ("excel".equals(request.getAttribute("sp-report-type"))) {
/*  86 */         pageContext.forward("/servlet/PDFReport");
/*     */       }
/*     */       
/*  89 */       out.write("\n\n<script type=\"text/javascript\" src=\"../../../template/jquery.multiselect.min.js\"></script>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../images/jquery.multiselect.css\" />\n<br>\n<form name=\"form1\" action=\"/showReports.do\">\t\t\n<table width=\"100%\" class=\"lrtbdarkborder\" style=\"overflow-y: scroll;\" cellspacing=\"0\" cellpadding=\"1\" border=\"0\">\n\t<tr width=\"100%\"> \n\t");
/*  90 */       String[] selectedColumns = (String[])request.getAttribute("selectedColumns");
/*  91 */       HashMap map = (HashMap)request.getAttribute("columnHeadings");
/*  92 */       String selectedCol = "";
/*  93 */       for (int i = 0; i < selectedColumns.length; i++) {
/*  94 */         selectedCol = selectedCol + selectedColumns[i] + ",";
/*  95 */         out.write("\t\t\t\t\n\t\t\t<td class=\"tableheadingbborder\" style=\"padding-left:5px; padding-right:5px;\">");
/*  96 */         out.print((String)map.get(selectedColumns[i]));
/*  97 */         out.write("</td>\t\t\n\t   ");
/*     */       }
/*     */       
/* 100 */       out.write("\n\t</tr>\t\t\t\n\t\n\t");
/* 101 */       ArrayList data = (ArrayList)request.getAttribute("data");
/* 102 */       for (int i = 0; i < data.size(); i++) {
/* 103 */         Properties p = (Properties)data.get(i);
/* 104 */         out.write("\n\t\t<tr width=\"100%\"> \t\n\t\t");
/* 105 */         for (int j = 0; j < selectedColumns.length; j++) {
/* 106 */           out.write("\t\t\t\t\t\t\t\t\n\t\t\t<td class=\"whitegrayrightalign\" style=\"padding-left:5px; padding-right:5px;\">");
/* 107 */           out.print(p.getProperty(selectedColumns[j]));
/* 108 */           out.write("</td>\t\t\n\t\t");
/*     */         }
/* 110 */         out.write("\n\t\t</tr>\n\t");
/*     */       }
/*     */       
/* 113 */       out.write("\n\t<input type=\"hidden\" name=\"selectedColumns\" value='");
/* 114 */       out.print(selectedCol);
/* 115 */       out.write("'>\n\t<input type=\"hidden\" name=\"columnHeadings\" value='");
/* 116 */       out.print(request.getAttribute("columnHeadings"));
/* 117 */       out.write("'>\n</table>\n</form>");
/*     */     } catch (Throwable t) {
/* 119 */       if (!(t instanceof SkipPageException)) {
/* 120 */         out = _jspx_out;
/* 121 */         if ((out != null) && (out.getBufferSize() != 0))
/* 122 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 123 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 126 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\MSSQLGeneralDetailsReport_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */