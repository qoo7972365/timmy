/*    */ package org.apache.jsp.jsp;
/*    */ 
/*    */ import com.adventnet.appmanager.util.FormatUtil;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import javax.el.ExpressionFactory;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.jsp.JspFactory;
/*    */ import javax.servlet.jsp.JspWriter;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.jasper.runtime.HttpJspBase;
/*    */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*    */ import org.apache.jasper.runtime.JspSourceDependent;
/*    */ 
/*    */ public final class businessServiceGraph_jsp extends HttpJspBase implements JspSourceDependent
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
/* 33 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*    */   }
/*    */   
/*    */ 
/*    */   public void _jspDestroy() {}
/*    */   
/*    */ 
/*    */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*    */     throws IOException, javax.servlet.ServletException
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
/* 53 */       response.setContentType("application/json; charset=UTF-8");
/* 54 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*    */       
/* 56 */       _jspx_page_context = pageContext;
/* 57 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 58 */       ServletConfig config = pageContext.getServletConfig();
/* 59 */       session = pageContext.getSession();
/* 60 */       out = pageContext.getOut();
/* 61 */       _jspx_out = out;
/*    */       
/* 63 */       out.write("<!--$Id$-->\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n   <html>\n      <head>\n         <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n         <script type='text/javascript' src='../template/graphUtil.js'></script>\n         <script type='text/javascript' src='../template/jitSpaceTree.js'></script>\n         <link href=\"/images/colpick.css\" rel=\"stylesheet\" type=\"text/css\"/>\n         <script type='text/javascript'>\n\t\t\tvar nodeIdentifier = \"node_\"; //No I18N\n            function createMonitorGroup(){\n            \t$(\"#progressSpanForCBV\").show();\t\n            \tvar tree =encodeURIComponent( JSON.stringify(getJSON()));\n\t\tvar isAddm = $('#useADDM').is(':checked');// No I18N\n            \t$(\"button[name=createMonitorGroup]\").attr(\"disabled\", true); //No I18N\n\t\t\tvar dataString = \"&method=createGroupFromGraph&graph=\"+tree+\"&useADDM=\"+isAddm; //No I18N\n   \t\t\t$.ajax({\n            \t\turl: \"GraphicalView.do?\",\t\t// No I18N\n");
/* 64 */       out.write("            \t\ttype: 'POST',\t\t// No I18N\n\t\t\t\t    data:dataString,\n\t\t\t\t\tdataType: \"json\",//No I18N\n            \t\tsuccess: function (res) {\n\t\t\t\t\t//alert(\"Monitor group is Created ... wait for few seconds, we will create a business view for the group\"); // No I18N\n            \t\t processResultOfcreateMG(res);\n            \t\t}\n            \t});\n            }\n\t\t function processResultOfcreateMG(result){\n\t\t\tvar message=result['message'];\n\t\t\tif(message === 'success'){\n\t\t\t\tvar haid=result['haid'];\n\t\t\t\tvar nodeIdVsResourceId = JSON.stringify(result[\"nodeIdVsResourceId\"]);\n\t\t\t\tvar viewProps = {};\n\t\t\t\tviewProps.displayProps = getAllDisplayProps();\n\t\t\t\tviewProps.coordinates = getJITNodes();\n\t\t\t\tvar dataString = \"&method=saveBusinessViewPropsForADDM&viewProps=\"+JSON.stringify(viewProps)+\"&haid=\"+haid+\"&nodeIdVsResourceId=\"+nodeIdVsResourceId; //No I18N\n\t   \t\t\t$.ajax({\n\t\t       \t\turl: \"GraphicalView.do?\",\t\t// No I18N\n\t\t       \t\ttype: 'POST',\t\t// No I18N\n\t\t\t\t\t    data:dataString,\n\t\t\t\t\t\tdataType: \"json\",//No I18N\n\t\t       \t\tsuccess: function (res) {\n");
/* 65 */       out.write("\t\t\t\t\t\tmessage=res['message'];\n\t\t\t\t\t\tdocument.location=\"/showapplication.do?haid=\"+haid+\"&method=showApplication&selectM=flashview&viewid=\"+res['viewId'];\n\t\t       \t\t}\n\t\t       \t});\n\t\t\t}else{\n\t\t\t}\n\t\t  }\n\t\t\n\t\t           \n\t\t  $('#saveDisplayProps').click(function(){\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t// No I18N\n\t\t  });\n\t\t\t\n         </script>\n      </head>\n      <body onload=\"\" >\n     ");
/* 66 */       JspRuntimeLibrary.include(request, response, "/jsp/businessServiceGraphTemplate.jsp" + ("/jsp/businessServiceGraphTemplate.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("BVName", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.flashview.displayname")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("displayAvailableViews", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("haid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("0", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selectedViewId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("0", request.getCharacterEncoding()), out, true);
/* 67 */       out.write("   \n        \n\n    <div class=\"btnBlock btnCreateBs\">\n                  <button name=\"createMonitorGroup\" type=\"button\" class=\"buttons btn btn_small\" onclick=\"\n            javascript:createMonitorGroup();\">");
/* 68 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.create"));
/* 69 */       out.write("</button>\n                  \n               </div>\n\t\t<script>\n\t\t\t$(document).ready(function(){\t\t\t\t\n\t\t\t\t$('#reset_Design').remove();\n\t\t\t\t$('#publish_view').remove();\n\t\t\t\tsetOtherColourProperties();\n\t\t\t});\n\n         </script>\n    </body>\n</html>\n");
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
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\businessServiceGraph_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */