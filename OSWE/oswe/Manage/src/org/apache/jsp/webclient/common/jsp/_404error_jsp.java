/*    */ package org.apache.jsp.webclient.common.jsp;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.el.ExpressionFactory;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspFactory;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.jasper.runtime.JspSourceDependent;
/*    */ 
/*    */ public final class _404error_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*    */ {
/* 18 */   private static final JspFactory _jspxFactory = ;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 24 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 25 */   static { _jspx_dependants.put("/webclient/common/jspf/commonIncludes.jspf", Long.valueOf(1473429417000L)); }
/*    */   
/*    */ 
/*    */   private ExpressionFactory _el_expressionfactory;
/*    */   
/*    */   public Map<String, Long> getDependants()
/*    */   {
/* 32 */     return _jspx_dependants;
/*    */   }
/*    */   
/*    */   public void _jspInit() {
/* 36 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 37 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*    */   
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, ServletException
/*    */   {
/* 47 */     javax.servlet.http.HttpSession session = null;
/*    */     
/*    */ 
/* 50 */     JspWriter out = null;
/* 51 */     Object page = this;
/* 52 */     JspWriter _jspx_out = null;
/* 53 */     PageContext _jspx_page_context = null;
/*    */     
/*    */     try
/*    */     {
/* 57 */       response.setContentType("text/html");
/* 58 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 60 */       _jspx_page_context = pageContext;
/* 61 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 62 */       ServletConfig config = pageContext.getServletConfig();
/* 63 */       session = pageContext.getSession();
/* 64 */       out = pageContext.getOut();
/* 65 */       _jspx_out = out;
/*    */       
/* 67 */       out.write(10);
/* 68 */       out.write("\n\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<title>AdventNet Web NMS4 - Notice</title>\n");
/* 69 */       out.write("\n</head>\n\n<body class=\"bgNone\">\n<table width=\"500\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n  <tr> \n    <td height=\"30\" align=\"left\" class=\"header2Bg\"><span class=\"header2\"> &nbsp;Sorry, \n      the page you requested was not found. </span></td>\n  </tr>\n  <tr> \n    <td align=\"left\"> <table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\">\n        <tr>\n          <td> <p align=\"left\" class=\"text\"><br>\n              Please check the URL for proper spelling and capitalization. If \n              you're having trouble locating a destination, try from our <a href=\"/mainLayout.do\" target=\"_top\">home \n              page</a>.</p>\n            </td>\n        </tr>\n      </table>\n      \n    </td>\n  </tr>\n  <tr> \n    <td align=\"left\">&nbsp;</td>\n  </tr>\n  <tr>\n    <td align=\"left\" class=\"header2Bg\"><img src=\"../images/trans.gif\" width=\"1\" height=\"1\"></td>\n  </tr>\n</table>\n</body>\n</html>\n");
/*    */     } catch (Throwable t) {
/* 71 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 72 */         out = _jspx_out;
/* 73 */         if ((out != null) && (out.getBufferSize() != 0))
/* 74 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 75 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 78 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */   
/*    */   public void _jspDestroy() {}
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\common\jsp\_404error_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */