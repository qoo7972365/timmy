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
/*    */ public final class ReloadHostResource_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
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
/* 62 */       out.write("<!--$Id$-->\n<html>\n<body>\n<script>\n window.close();\n window.opener.location.reload(true);\n /*");
/*    */       
/* 64 */       String resid = request.getParameter("resourceid");
/*    */       
/* 66 */       out.write("\n window.opener.location.href=\"/showresource.do?resourceid=");
/* 67 */       out.print(resid);
/* 68 */       out.write("&method=showResourceForResourceID\"; */\n</script>\n</body>\n</html>\n");
/*    */     } catch (Throwable t) {
/* 70 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 71 */         out = _jspx_out;
/* 72 */         if ((out != null) && (out.getBufferSize() != 0))
/* 73 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 74 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 77 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ReloadHostResource_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */