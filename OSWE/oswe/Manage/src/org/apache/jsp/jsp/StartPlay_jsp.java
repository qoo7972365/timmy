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
/*    */ public final class StartPlay_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
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
/* 62 */       out.write("<!-- $Id$ -->\n<html>\n<head>\n<title> Application Manager : Real Browser Monitor - Start Play </title>\n</head>\n");
/*    */       
/* 64 */       String script = request.getParameter("scriptname");
/* 65 */       String resourceId = request.getParameter("resourceid");
/*    */       
/* 67 */       out.write("\n<script>\nvar http=\"\";//No I18N\nfunction changeState(val)\n{\n}\nfunction getHTTPObject() \n{\n\tvar xmlhttp;\n \t/*@cc_on\n  \t@if (@_jscript_version >= 5)\n  \t  try \n  \t  {\n  \t    xmlhttp = new ActiveXObject(\"Msxml2.XMLHTTP\");//No I18N\n  \t  } catch (e) \n  \t  {\n  \t    try \n  \t    {\n  \t      xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");//No I18N\n  \t    } catch (E) \n  \t    {\n  \t      xmlhttp = false;\n  \t    }\n  \t  }\n  \t@else\n  \txmlhttp = false;\n\t  @end @*/\n\tif (!xmlhttp && typeof XMLHttpRequest != 'undefined') \n\t{\n\t  try \n          {\n      \t    xmlhttp = new XMLHttpRequest();//No I18N\n    \t  } catch (e) \n    \t  {\n      \t    xmlhttp = false;//No I18N\n    \t  }\n  \t}\n\treturn xmlhttp;\n}\n\n\nfunction startPlayFromUI()\n{\n\ttry//No I18N\n\t{\n\t\tscript = \"");
/* 68 */       out.print(script);
/* 69 */       out.write("\";//No I18N\n\t\tvar resId = \"");
/* 70 */       out.print(resourceId);
/* 71 */       out.write("\"//No I18N\n\t\tsuitelevel = false;//No I18N\n\t\thttp = getHTTPObject();\n\t\tvar url=\"/jsp/HandleRecordPlayFromUI.jsp?ttype=wft&todotype=play&todomode=start&level=\"+suitelevel+\"&rbmmode=true&scriptname=\"+script+\"&resourceid=\"+resId;//No I18N\n/*\t\tif(filter != null && filter != \"none\")\n\t\t{\n\t\t\turl = url+\"&filtername=\"+filter;//No I18N\n\t\t}\n\t\telse if(sequence != null && sequence != \"none\")\n\t\t{\n\t\t\turl = url+\"&sequencename=\"+sequence;//No I18N\n\t\t}*/\n\t\thttp.open(\"GET\", url, true);\n\t\thttp.onreadystatechange = handleRecordPlayActionResponse; \n\t\thttp.send(null);\n\t}\n\tcatch(e)\n\t{\n\t}\n}\n\nfunction stopRecordFromUI()\n{\n\ttry\n\t{\n\t\thttp = getHTTPObject();\n\t\tvar url=\"/jsp/HandleRecordPlayFromUI.jsp?ttype=wft&todotype=record&todomode=stop\";//No I18N\n\t\thttp.open(\"GET\", url, true);\n\t\thttp.onreadystatechange = handleRecordPlayActionResponse; \n\t\thttp.send(null);\n\t}\n\tcatch(e)//No I18N\n\t{\n\t}\n}\n\nfunction handleRecordPlayActionResponse()\n{\n\tif (http.readyState == 4) \n\t{\n\t\t//refreshTree();\n\t}\n}\n\n</script>\n<body onload=\"startPlayFromUI()\">\n<table>\n");
/* 72 */       out.write("<tr>\n<td></td>\n</tr>\n<tr>\n<td></td>\n</tr>\n</table>\n<br>\n<div id=\"scriptlinesdiv\" STYLE=\"display:block;scroll:auto;\" width=\"100%\" align=\"center\">\n\t</div>\n</body>\n</html>\n");
/*    */     } catch (Throwable t) {
/* 74 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 75 */         out = _jspx_out;
/* 76 */         if ((out != null) && (out.getBufferSize() != 0))
/* 77 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 78 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*    */       }
/*    */     } finally {
/* 81 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\StartPlay_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */