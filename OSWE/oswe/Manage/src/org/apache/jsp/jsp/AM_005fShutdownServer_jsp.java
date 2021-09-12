/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class AM_005fShutdownServer_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  30 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/*  31 */   static { _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/*  32 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  39 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  54 */     HttpSession session = null;
/*     */     
/*     */ 
/*  57 */     JspWriter out = null;
/*  58 */     Object page = this;
/*  59 */     JspWriter _jspx_out = null;
/*  60 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  64 */       response.setContentType("text/html");
/*  65 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  67 */       _jspx_page_context = pageContext;
/*  68 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  69 */       ServletConfig config = pageContext.getServletConfig();
/*  70 */       session = pageContext.getSession();
/*  71 */       out = pageContext.getOut();
/*  72 */       _jspx_out = out;
/*     */       
/*  74 */       out.write(" <!--$Id$-->\n\n\n\n\n\n\n\n\n\n<html>\n\n<body>\n<div id=\"datemessage\" style=\"display:none;\">\n<table class=\"messagebox\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\" width=\"99%\">\n              <tbody><tr> \n                <td align=\"center\" width=\"5%\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" height=\"25\" width=\"25\"></td>\n                <td class=\"message\" width=\"95%\">  \n                ");
/*  75 */       out.print((String)request.getAttribute("datemessage"));
/*  76 */       out.write("\n                   </td>\n\n              </tr>\n            </tbody></table>\n            <br></div>\n              <div id=\"successmessage\"  style=\"display:none;\">\n   <table class=\"messagebox\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\" width=\"40%\" align=\"center\">\n                 <tr> \n                   </tr>\n                   <tr valign=\"center\">\n                   <td align=\"center\" valign=middle width=\"5%\">\n                   \n\t\t <img src=\"../images/icon_message_success.gif\"  alt=\"Icon\" >\n\t\t   \n                   </td>\n                   <td class=\"message\" width=\"95%\">  \n                   ");
/*  77 */       out.print(FormatUtil.getString("am.webclient.shutdown.success.text"));
/*  78 */       out.write("\n                  </td>\n                  </tr>\n                   <tr></tr>\n               </table>\n            <br></div>\n            <div id=\"restartmessage\"  style=\"display:none;\">\n   <table class=\"messagebox\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\" width=\"40%\" align=\"center\">\n                 <tr> \n                   </tr>\n                   <tr valign=\"center\">\n                   <td align=\"center\" valign=middle width=\"5%\">\n                   \n\t\t <img src=\"../images/icon_message_success.gif\"  alt=\"Icon\" >\n\t\t   \n                   </td>\n                   <td class=\"message\" width=\"95%\">  \n                   ");
/*  79 */       out.print(FormatUtil.getString("am.webclient.systemsettings.datesettings.serverstartup.text"));
/*  80 */       out.write("\n                  </td>\n                  </tr>\n                   <tr></tr>\n               </table>\n            <br>\n            <table align=\"center\">\n     <tr>\n     <td>\n     <img src='../images/progressbar.gif'>\n     </td>\n     </tr>\n     <tr>\n     <td class=\"bodytext\" align=\"center\">");
/*  81 */       out.print(FormatUtil.getString("webclient.topo.objectdetails.discoverystatus.inprogress"));
/*  82 */       out.write("</td>\n     </tr>\n     </table></div>\n  \n   \n   \n   <div id=\"progress\" style=\"display:none;\" >\n   <br><br>\n     <table align=\"center\">\n     <tr>\n     <td>\n     <img src='../images/progressbar.gif'>\n     </td>\n     </tr>\n     <tr>\n     <td class=\"bodytext\" align=\"center\">");
/*  83 */       out.print(FormatUtil.getString("am.webclient.shutdown.shutdownprogress.text"));
/*  84 */       out.write("</td>\n     </tr>\n     </table>\n     <br>\n  </div>\n\n  \n  \n  \n  \n  \n  <div id=\"confirmbox\">\n \n  <table width=60% align=\"center\" valign=\"center\">\n  <tr>\n\t  \n\t  <td width=\"100%\" height=\"170\">");
/*  85 */       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/*  86 */       out.write(" \n\t  <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" height=\"100\">  \n\t\t  \n\t\t  <tr> \n\t\t      <td  width=\"15%\" align=\"center\" valign=\"top\"><img src=\"../images/icon_cross.gif\" alt=\"Icon\" vspace=\"5\"></td>\n\t\t      <td  width=\"85%\">\n\t\t      <table width=100% align=\"center\" valign=\"center\">\n\t\t      <tr>\n\t\t      <td class=\"bodytextbold\">\n\t\t  ");
/*  87 */       out.print(FormatUtil.getString("am.webclient.shutdown.confirm.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*  88 */       out.write("\n\t\t      </td>\n\t\t      </tr>\n\t\t      <tr>\n\t\t      <td BACKGROUND=\"../images/spacer.gif\">&nbsp;\n\t\t     \n\t\t      </td>\n\t\t      </tr>\n\t\t      <tr>\n\t\t      <td align=\"center\">\n\t\t        &nbsp;&nbsp;\n                        <input  type=\"button\" class=\"buttons\"\n                 value='");
/*  89 */       out.print(FormatUtil.getString("am.webclient.shutdown.ok.text"));
/*  90 */       out.write("' onclick=\"javascript:shutdown()\" >\n                        <input type=\"button\" value=\"");
/*  91 */       out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/*  92 */       out.write("\" class='buttons' onClick=\"history.back();\" />\n\t\t      </td>\n\t\t      </tr>\n\t\t      </table>\n\t\t      </td> \n\t\t  </tr>\n\t\t  \n\t  </td>\n  </tr>\n  </table>");
/*  93 */       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/*  94 */       out.write("</td></tr></table>\n  </div>\n  <br><br><br><br>\n  \n<script language=\"JavaScript\" type=\"text/JavaScript\">\n");
/*     */       
/*  96 */       if (request.getAttribute("datemessage") != null)
/*     */       {
/*     */ 
/*     */ 
/* 100 */         out.write("\n\njavascript:showDiv('datemessage');\n\nrestart();\n");
/*     */       }
/* 102 */       out.write("\n\n\nfunction checkforshutdown1()\n{\n     if(http.readyState == 4)\n     {\n       try{\n\n\t        if(http.status == 200)\n\t\t{\n\t\t  continuoscheckforRestart();\n                }\n\t\telse\n\t\t{\n\t\t\n                    checking();\n                     var t=setTimeout(\"waitforrestart()\",30000)\n\t\t\n\t\t}\n\t   }\n\t\tcatch(e)\n\t      {\n\t        var s=setTimeout(\"waitforrestart()\",30000)\n                checking();\n              \n      \t       // alert(\"Exceppion\"+e);\n\t      }\n\t\n\t }\n\n\n}\n\n\n\n\n\nfunction checkforshutdown()\n{\n     if(http.readyState == 4)\n     {\n       try{\n\n\t        if(http.status == 200)\n\t\t{\n\t\t  continuoscheck();\n                }\n\t\telse\n\t\t{\n                    var t=setTimeout(\"waitforshutdown()\",30000)\n\t\t\n\t\t}\n\t   }\n\t\tcatch(e)\n\t      {\n                    \n                    var s=setTimeout(\"waitforshutdown()\", 30000);\n      \t       // alert(\"Exceppion\"+e);\n\t      }\n\t\n\t }\n\n\n}\n\n\nfunction checkingOver()\n{\n  \nif(http.readyState == 4)\n     {\n       try{\n                   \n                if(http.status == 200)\n\t\t{\n\t\t location.href=\"/applications.do\";\n");
/* 103 */       out.write("\t\t  \n                }\n                else\n                {\n                    checking();\n                \n                }\n                \n            \n            }\n            catch(e)\n\t      {\n                checking();\n\t      }\n         \n    }\n\n}\n\nfunction checking()\n{\n       \n        http = getHTTPObject();\n        http.open(\"GET\",\"/GlobalActions.do?method=restartCheck\",true);\n        http.onreadystatechange = checkingOver;\n        http.send(null);\n\n}\nfunction waitforshutdown()\n{\njavascript:showDiv('successmessage');  \njavascript:hideDiv('progress');\n}\n\nfunction waitforrestart()\n{\njavascript:showDiv('restartmessage');  \njavascript:hideDiv('datemessage');  \njavascript:hideDiv('progress');\n}\n\nfunction shutdown()\n{\n       \n        javascript:showDiv('progress');\n        javascript:hideDiv('confirmbox');\n        http = getHTTPObject();\n        http.open(\"GET\",\"/GlobalActions.do?method=shutdownServer\",true);\n        http.onreadystatechange = checkforshutdown;\n        http.send(null);\n}\n\nfunction restart()\n");
/* 104 */       out.write("{\n        \n        javascript:showDiv('progress');\n        javascript:hideDiv('confirmbox');\n        \n        http = getHTTPObject();\n        http.open(\"GET\",\"/GlobalActions.do?method=shutdownServer\",true);\n        http.onreadystatechange = checkforshutdown1;\n        http.send(null);\n\n}\n\nfunction continuoscheck()\n{\n\n        http = getHTTPObject();\n        http.open(\"GET\",\"/GlobalActions.do?method=servercheck\",true);\n        http.onreadystatechange = checkforshutdown;\n        http.send(null);\n\n}\nfunction continuoscheckforRestart()\n{\n\n        http = getHTTPObject();\n        http.open(\"GET\",\"/GlobalActions.do?method=servercheck\",true);\n        http.onreadystatechange = checkforshutdown1;\n        http.send(null);\n\n}\n</script>\n\n\n\n \n  \n      </body>\n      </html>\n");
/*     */     } catch (Throwable t) {
/* 106 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 107 */         out = _jspx_out;
/* 108 */         if ((out != null) && (out.getBufferSize() != 0))
/* 109 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 110 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 113 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {}
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AM_005fShutdownServer_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */