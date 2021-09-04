/*    */ package org.apache.jsp.jsp;
/*    */ 
/*    */ import com.adventnet.qengine.serverfwk.jsp.core.EventProcessor;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.el.ExpressionFactory;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.http.Cookie;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspFactory;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.jasper.runtime.HttpJspBase;
/*    */ import org.apache.jasper.runtime.JspSourceDependent;
/*    */ import org.apache.tomcat.InstanceManager;
/*    */ 
/*    */ public final class DetectToolBarIE_jsp extends HttpJspBase implements JspSourceDependent
/*    */ {
/* 20 */   private static final JspFactory _jspxFactory = ;
/*    */   
/*    */   private static Map<String, Long> _jspx_dependants;
/*    */   
/*    */   private ExpressionFactory _el_expressionfactory;
/*    */   private InstanceManager _jsp_instancemanager;
/*    */   
/*    */   public Map<String, Long> getDependants()
/*    */   {
/* 29 */     return _jspx_dependants;
/*    */   }
/*    */   
/*    */   public void _jspInit() {
/* 33 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 34 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */ 
/*    */   public void _jspDestroy() {}
/*    */   
/*    */ 
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, javax.servlet.ServletException
/*    */   {
/* 44 */     javax.servlet.http.HttpSession session = null;
/*    */     
/*    */ 
/* 47 */     JspWriter out = null;
/* 48 */     Object page = this;
/* 49 */     JspWriter _jspx_out = null;
/* 50 */     PageContext _jspx_page_context = null;
/*    */     
/*    */     try
/*    */     {
/* 54 */       response.setContentType("text/html; charset=utf-8");
/* 55 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 57 */       _jspx_page_context = pageContext;
/* 58 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 59 */       ServletConfig config = pageContext.getServletConfig();
/* 60 */       session = pageContext.getSession();
/* 61 */       out = pageContext.getOut();
/* 62 */       _jspx_out = out;
/*    */       
/* 64 */       out.write("<!-- $Id$-->\n\n\n\n");
/*    */       
/* 66 */       String currenttheme = EventProcessor.defaultTheme;
/*    */       
/* 68 */       String userName = "rbm";
/*    */       
/*    */ 
/*    */ 
/*    */ 
/* 73 */       String userCookieValue = EventProcessor.getRandomSessionString(userName);
/*    */       
/* 75 */       Cookie clientNameCookie = new Cookie("clientname", userCookieValue);
/* 76 */       clientNameCookie.setPath("/");
/* 77 */       response.addCookie(clientNameCookie);
/*    */       
/*    */ 
/* 80 */       out.write("\n<html>\n<head>\n<TITLE>AdventNet QEngine Web Test Studio : QEngine Toolbar Download & Install</TITLE> \n<link href=\"/framework/styles/");
/* 81 */       out.print(currenttheme);
/* 82 */       out.write("/qengine.css\" rel=\"stylesheet\" type=\"text/css\">\n<Script language=\"javascript\" src=\"/webfunctional/js/editor.js\">\n</Script>\n<Script language=\"javascript\" src=\"/framework/js/common.js\">\n</Script>\n<script>\nfunction checkTBInstall()\n{\n     if (typeof(tb)==\"undefined\")\n     {\n\t\t//alert(\"Toolbar not found. Install Toolbar\");//No I18N\n\t\tparent.showToolbarErrorMessage();\n//\t\twindow.parent.document.getElementById(\"loading\").style.display = \"none\";\n\t\t//showProductDefaultPage();\n     }\n     else\n\t\t \n     {\n\t\t try\n\t\t {\n//\t\twindow.parent.document.getElementById(\"loading\").style.display = \"none\";\n\t\tlocation.href=\"QEConnectToolBarIEFrame.jsp\";//No I18N\n//\t\tparent.detectToolbarframe.src = \"QEConnectToolBarIEFrame.jsp\";//No I18N\n\t\t }\n\t\t catch(e)\n\t\t {\n\t\t\t alert(e);\n\t\t }\n     }\n}\n\nfunction loadpage(url)\n{\n\t\tdocument.getElementById(\"innerrighttipsframe\").src=url;\n}\n\nfunction innerwindowDetails()\n{\n\t_client_width = document.body.clientWidth;\n\t_client_height = parent.document.body.clientHeight;\n\t_right_frame_height = _client_height-150;//No I18N\n");
/* 83 */       out.write("\tdocument.getElementById(\"innerrighttipsframe\").height=_right_frame_height;\n}\n\n\nvar _prev_elem = null;\nfunction callElemTrMouseOver()\n{\n\tcallMouseOver(this);\n}\n\nfunction callMouseOver(elem)\n{\n\tif(_prev_elem!=elem)\n\t{\n\t\telem.className=\"mouseroverstyle cursorhandstyle\";//No I18N\n\t}\n}\nfunction callElemTrMouseOut()\n{\n\tcallMouseOut(this);\n}\n\nfunction callMouseOut(elem)\n{\n\tif(_prev_elem!=elem)\n\t{\n\t\telem.className=\"mouseoutstyle\";//No I18N\n\t}\n}\nfunction callElemTrMouseClick()\n{\n\t\n\tcallMouseClick(this);\n}\n\nfunction callMouseClick(elem)\n{\n\t//forwardToSelectedElem(elem.id);\n\tif(_prev_elem!=elem)\n\t{\n\t\tif(_prev_elem)\n\t\t{\n\t\t\t_prev_elem.className=\"\";//No I18N\n\t\t}\n\t\telem.className=\"mouseclickstyle\";//No I18N\n\t\t_prev_elem =elem;\n\t}\n}\n\nfunction callHighlight(elem)\n{\n\tif(_prev_elem!=elem)\n\t{\n\t\tif(_prev_elem)\n\t\t{\n\t\t\t_prev_elem.className=\"\";//No I18N\n\t\t}\n\t\telem.className=\"mouseclickstyle\";//No I18N\n\t\t_prev_elem =elem;\n\t}\n}\n\n</script>\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"innerwindowDetails;checkTBInstall();\">\n");
/* 84 */       out.write("<object classid=\"clsid:E0B1B541-149F-483A-8CCA-026BA4D7CA45\" id=\"tb\">\n    <input type=\"hidden\">\n</object>          \n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"ind-tabletcwhitebg\">\n\t<tr> \n    \t<td align=\"left\" valign=\"top\">\n        \t<br> \n\t\t\t<iframe src=\"\" frameborder=\"0\" id=\"innerrighttipsframe\" width=\"0%\" scrolling=\"auto\" marginheight=\"0\" marginwidth=\"0\"></iframe>\n\t\t</td>\n\t</tr>\n</table>\n</body>\n</html>\n");
/*    */     } catch (Throwable t) {
/* 86 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 87 */         out = _jspx_out;
/* 88 */         if ((out != null) && (out.getBufferSize() != 0))
/* 89 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 90 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 93 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DetectToolBarIE_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */