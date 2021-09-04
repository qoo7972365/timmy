/*    */ package org.apache.jsp.jsp;
/*    */ 
/*    */ import com.adventnet.appmanager.util.FormatUtil;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.el.ExpressionFactory;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspFactory;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.jasper.runtime.HttpJspBase;
/*    */ import org.apache.jasper.runtime.JspSourceDependent;
/*    */ 
/*    */ public final class PersonalizedDemoForm_jsp extends HttpJspBase implements JspSourceDependent
/*    */ {
/* 19 */   private static final JspFactory _jspxFactory = ;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 25 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 26 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*    */   
/*    */ 
/*    */   private ExpressionFactory _el_expressionfactory;
/*    */   
/*    */   public Map<String, Long> getDependants()
/*    */   {
/* 33 */     return _jspx_dependants;
/*    */   }
/*    */   
/*    */   public void _jspInit() {
/* 37 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 38 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*    */   
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, javax.servlet.ServletException
/*    */   {
/* 48 */     javax.servlet.http.HttpSession session = null;
/*    */     
/*    */ 
/* 51 */     JspWriter out = null;
/* 52 */     Object page = this;
/* 53 */     JspWriter _jspx_out = null;
/* 54 */     PageContext _jspx_page_context = null;
/*    */     
/*    */     try
/*    */     {
/* 58 */       response.setContentType("text/html");
/* 59 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 61 */       _jspx_page_context = pageContext;
/* 62 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 63 */       ServletConfig config = pageContext.getServletConfig();
/* 64 */       session = pageContext.getSession();
/* 65 */       out = pageContext.getOut();
/* 66 */       _jspx_out = out;
/*    */       
/* 68 */       out.write("<!DOCTYPE html>\n");
/* 69 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n\n");
/* 70 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 71 */       out.write("\n<script type=\"text/javascript\" src=\"/template/validation.js\"></script>\n<html>\n<head></head>\n<style>\n\t\t.popupForm {\n\t\t\tposition: fixed;\n\t\t\topacity:0;\n\t\t\ttop: -1400px;\n\t\t\tleft:50%;\n\t\t\tz-index: 999999999999999;\n\t\t\twidth:320px;\n\t\t\tmargin-left:-160px;\n\t\t\tbackground: #fff;\n\t\t\tpadding:20px 20px;\n\t\t\ttransition:all .5s;\n\t\t}\n\t\t.popupForm.showPopup {\n\t\t\topacity:1;\n\t\t\ttop: 100px;\n\t\t}\n\t\tspan.close {\n\t\t\tposition: absolute;\n\t\t\tright: 10px;\n\t\t\ttop:21px;\n\t\t\twidth: 20px;\n\t\t\theight: 20px;\n\t\t\tfont:14px arial;\n\t\t\tfont-weight: 100;\n\t\t\tcolor: #999;\n\t\t\tcursor: pointer;\n\t\t\tz-index: 9999999999999999;\n\t\t}\n\t\t.requestSupport {\n\t\t\tmargin:0 auto;\n\t\t}\n\t\t.requestSupport input,.form-sec select {\n\t\t\tborder: 1px solid #bbb;\n\t\t\tcolor: #777;\n\t\t\tfont-size: 14px;\n\t\t\tmargin-top: 16px;\n\t\t\toutline: medium none;\n\t\t\tpadding:10px 10px;\n\t\t\ttransition: all 0.2s ease-in-out 0s;\n\t\t\twidth:280px;\n\t\t}\n\t\t.requestSupport .supportBut {\n\t\t\tbackground-color:#e26a6a !important;\n\t\t\twidth:auto !important;\n\t\t\tcolor:#fff !important;\n\t\t\tcursor:pointer;\n\t\t}\n\t\t.tac {\n\t\t\ttext-align:center;\n");
/* 72 */       out.write("\t\t}\n\t\t.tar {\n\t\t\ttext-align:right;\n\t\t}\n\t\th4.formHeader {\n\t\t\tfont-size:14px;\n\t\t\tfont-family:arial;\n\t\t\tfont-weight:100 !important;\n\t\t\ttext-transform:uppercase;\n\t\t\tcolor:#8e9daa;\n\t\t\tpadding:0;\n\t\t\tmargin:0 0 10px;\n\t\t\ttext-align: center;\n\t\t}\n\t\tiframe {\n\t\tborder:0px;\n\t\t}\n</style>\n\n\t<script>\n\t\t\t\n\t\tfunction tl1validate() {\n\t\tvar parentwin = window.parent.document.getElementsByClassName('popupForm');\t\t//NO I18N\n\t\tvar overlay = window.parent.document.getElementsByClassName('overlay');\t\t//NO I18N\n\t\t\t$('#nexturl').val(window.location.href);\n\t\t\tif($('#emailID').val()=='' || !isEmailId($('#emailID').val())){\t//NO I18N\n\t\t\t\talert('");
/* 73 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.email"));
/* 74 */       out.write("');\n\t\t\t}else{\n\t\t\t\tvar _gaq = _gaq || [];\t\t\t\t\t\t\t//NO I18N\n\t\t\t\t_gaq.push(['_trackEvent', 'lead', 'appmgr', 'appmgr_website']);\t");
/* 75 */       out.write("\n\t\t\t\tsetTimeout('document.RequestSupport.submit()', 500);\n\t\t\t\t$(parentwin).removeClass('showPopup');\n\t\t\t\t$(overlay).hide();\n\t\t\t}\n\t\t\treturn false;\n\t\t}\n\t\t\n\t\t\n\t</script>\n\n<body>\t\t\n<div class=\"requestSupport\">\n\t<form onsubmit=\"return tl1validate();\" name=\"RequestSupport\" id=\"RequestSupport\" method=\"post\" action=\"http://www.manageengine.com/cgi-bin/mailcopy?applications_manager\">\n\t\t\t\t<div class=\"tac\">\n\t\t\t\t\t<input type=\"text\" name=\"userName\" placeholder=\"");
/* 76 */       out.print(FormatUtil.getString("am.webclient.inlinefeedback.name"));
/* 77 */       out.write("\" id=\"userName\">\n\t\t\t\t</div>\n\t\t\t\t<div class=\"tac\">\n\t\t\t\t\t<input type=\"text\" name=\"emailID\" placeholder=\"");
/* 78 */       out.print(FormatUtil.getString("am.webclient.businessemail.text"));
/* 79 */       out.write(" *\" id=\"emailID\">\n\t\t\t\t</div>\n\t\t\t\t<div class=\"tac\">\n\t\t\t\t\t<input type=\"text\" placeholder=\"");
/* 80 */       out.print(FormatUtil.getString("it360.search.table.trackit.library.column.phone"));
/* 81 */       out.write("\" name=\"tel\" id=\"tel\">\n\t\t\t\t</div>\n\t\t\t\t<div class=\"tar\" >\n\t\t\t\t\t<input type=\"submit\" value=\"Submit\" name=\"submit-form\" class=\"supportBut\">\n\t\t\t\t\t<input name=\"subject\" value=\"Request for Personalized Demo\" type=\"hidden\">\n\t\t\t\t\t<input type=\"hidden\" name=\"next-url\" id=\"nexturl\" value=\"\" />\n\t\t\t\t</div>\n\t\t</form>\n</div>\n</body>\n</html>");
/*    */     } catch (Throwable t) {
/* 83 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 84 */         out = _jspx_out;
/* 85 */         if ((out != null) && (out.getBufferSize() != 0))
/* 86 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 87 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 90 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */   
/*    */   public void _jspDestroy() {}
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\PersonalizedDemoForm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */