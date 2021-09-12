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
/*    */ public final class adminFooter_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
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
/* 62 */       out.write("\n<script defer type='text/javascript'>\n\t\t(function()\n\t\t {\n\t\tvar iframe = parent.document.getElementById('adminIFrame');\n\t\tif (iframe != null) {\n\t\t\tvar iframeName = iframe.name;\n\t\t\tif (iframeName != null && iframeName == 'adminIFrame') {\n\t\t\t\tadminResize();\n\t\t\t\tsetInterval(adminResize, 3000);\n\t\t\t}\n\t\t}\n\n\t})();\n\tfunction adminResize() {\n\t\t//var newHeight=parent.document.body.clientHeight+jQuery(document).height();\n\t\tvar parentBody = parent.document.body;\n\t\t//alert(\"new height : \"+newHeight);\t\n\n\t\tvar frameDiv = jQuery(\"#adminIFrame\", parentBody);//No I18N\n\t\tvar body = document.body, html = document.documentElement;\n\t\tvar height = Math.max(body.scrollHeight, body.offsetHeight,\n\t\t\t\thtml.clientHeight, html.scrollHeight, html.offsetHeight);\n\t\tjQuery('#adminIFrame', parentBody).width(\"100%\");\n\t\tif (jQuery('#adminIFrame', parentBody).height() != height) {\n\n\t\t\tjQuery('#adminIFrame', parentBody).height(height + 10 + \"px\");//No I18N \n\t\t\t//plugin changes\n\t\t\tif (typeof parent.resizeParentWindow == 'function') {//No I18N \n\t\t\t\tparent.resizeParentWindow();//No I18N \n");
/* 63 */       out.write("\t\t\t}\n\n\t\t}\n\t\n\n\t}\n</script>\n");
/*    */     } catch (Throwable t) {
/* 65 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 66 */         out = _jspx_out;
/* 67 */         if ((out != null) && (out.getBufferSize() != 0))
/* 68 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 69 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 72 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\adminFooter_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */