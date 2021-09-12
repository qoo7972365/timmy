/*    */ package org.apache.jsp.jsp;
/*    */ 
/*    */ import com.adventnet.appmanager.util.FormatUtil;
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
/*    */ public final class v2TrapActionFields_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*    */ {
/* 19 */   private static final JspFactory _jspxFactory = ;
/*    */   
/*    */   private static Map<String, Long> _jspx_dependants;
/*    */   
/*    */   private ExpressionFactory _el_expressionfactory;
/*    */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*    */   
/*    */   public Map<String, Long> getDependants()
/*    */   {
/* 28 */     return _jspx_dependants;
/*    */   }
/*    */   
/*    */   public void _jspInit() {
/* 32 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 33 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */ 
/*    */   public void _jspDestroy() {}
/*    */   
/*    */ 
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, ServletException
/*    */   {
/* 43 */     javax.servlet.http.HttpSession session = null;
/*    */     
/*    */ 
/* 46 */     JspWriter out = null;
/* 47 */     Object page = this;
/* 48 */     JspWriter _jspx_out = null;
/* 49 */     PageContext _jspx_page_context = null;
/*    */     
/*    */     try
/*    */     {
/* 53 */       response.setContentType("text/html");
/* 54 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 56 */       _jspx_page_context = pageContext;
/* 57 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 58 */       ServletConfig config = pageContext.getServletConfig();
/* 59 */       session = pageContext.getSession();
/* 60 */       out = pageContext.getOut();
/* 61 */       _jspx_out = out;
/*    */       
/* 63 */       out.write("\n\n <tr>\n    <td class=\"bodytext label-align\">");
/* 64 */       out.print(FormatUtil.getString("am.webclient.newaction.trapcommunity"));
/* 65 */       out.write("</td>\n    <td class=\"bodytext\"><html:text property=\"trapCommunity\" size=\"40\" styleClass=\"formtext default\"  maxlength=\"50\"/></td>\n    <td class=\"bodytext\">&nbsp;</td>\n  </tr>\n<tr> \n     <td class=\"bodytext label-align\">");
/* 66 */       out.print(FormatUtil.getString("am.webclient.newaction.snmptrapoid"));
/* 67 */       out.write("</td>\n      <td colspan=\"2\" class=\"bodytext\"><html:text property=\"v2SNMPTrapOID\" size=\"40\" styleClass=\"formtext default\"  maxlength=\"255\"/></td>\n</tr>\n");
/*    */     } catch (Throwable t) {
/* 69 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 70 */         out = _jspx_out;
/* 71 */         if ((out != null) && (out.getBufferSize() != 0))
/* 72 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 73 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 76 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\v2TrapActionFields_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */