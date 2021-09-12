/*    */ package org.apache.jsp.jsp;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.el.ExpressionFactory;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspFactory;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*    */ import org.apache.jasper.runtime.JspSourceDependent;
/*    */ 
/*    */ public final class MibBrowserLeftPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*    */ {
/* 18 */   private static final JspFactory _jspxFactory = ;
/*    */   
/*    */   private static Map<String, Long> _jspx_dependants;
/*    */   
/*    */   private ExpressionFactory _el_expressionfactory;
/*    */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*    */   
/*    */   public Map<String, Long> getDependants()
/*    */   {
/* 27 */     return _jspx_dependants;
/*    */   }
/*    */   
/*    */   public void _jspInit() {
/* 31 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 32 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */ 
/*    */   public void _jspDestroy() {}
/*    */   
/*    */ 
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, ServletException
/*    */   {
/* 42 */     javax.servlet.http.HttpSession session = null;
/*    */     
/*    */ 
/* 45 */     JspWriter out = null;
/* 46 */     Object page = this;
/* 47 */     JspWriter _jspx_out = null;
/* 48 */     PageContext _jspx_page_context = null;
/*    */     
/*    */     try
/*    */     {
/* 52 */       response.setContentType("text/html");
/* 53 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 55 */       _jspx_page_context = pageContext;
/* 56 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 57 */       ServletConfig config = pageContext.getServletConfig();
/* 58 */       session = pageContext.getSession();
/* 59 */       out = pageContext.getOut();
/* 60 */       _jspx_out = out;
/*    */       
/* 62 */       out.write("\n\n\n\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr> \n          <td height=\"21\" colspan=\"2\" class=\"bodytextbold11\">Quick links</td>\n        </tr>\n\n     <tr>   \n    <td width=\"10\" height=\"21\" align=\"center\"><img src=\"../images/icon_left_arrow.gif\" width=\"4\" height=\"6\"></td>\n    <td height=\"21\" >\n    <a href=\"/adminAction.do?method=reloadSendTrapActionForm\" class=\"links\">Create Send Trap Action</a>\n      </td>\n       </tr>\n        <tr> \n          <td colspan=\"2\" align=\"left\" class=\"bodytextbold11\">&nbsp;</td>\n        </tr>\n        <tr> \n\t          <td colspan=\"2\" align=\"left\" class=\"bodytextbold11\">Quick Note : </td>\n        </tr>\n        <tr valign=\"middle\"> \n          <td height=\"64\" colspan=\"2\" class=\"quicknote\">\n\t\tThis MIB Browser can be used to find the Object IDs used for SNMP variables in Custom Monitor Screens\n\t\tand in creating Send Trap actions\n          </td>\n        </tr>\n      </table>");
/*    */     } catch (Throwable t) {
/* 64 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 65 */         out = _jspx_out;
/* 66 */         if ((out != null) && (out.getBufferSize() != 0))
/* 67 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 68 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 71 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MibBrowserLeftPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */