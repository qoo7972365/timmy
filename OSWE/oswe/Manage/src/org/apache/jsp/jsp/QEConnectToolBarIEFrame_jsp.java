/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.qengine.serverfwk.jsp.core.MainServer;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class QEConnectToolBarIEFrame_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  29 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  33 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  34 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  44 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  47 */     JspWriter out = null;
/*  48 */     Object page = this;
/*  49 */     JspWriter _jspx_out = null;
/*  50 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  54 */       response.setContentType("text/html; charset=utf-8");
/*  55 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  57 */       _jspx_page_context = pageContext;
/*  58 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  59 */       ServletConfig config = pageContext.getServletConfig();
/*  60 */       session = pageContext.getSession();
/*  61 */       out = pageContext.getOut();
/*  62 */       _jspx_out = out;
/*     */       
/*  64 */       out.write("<!-- $Id$-->\n\n\n\n");
/*     */       
/*     */ 
/*  67 */       String userName = "rbm";
/*     */       
/*  69 */       String serverHost = "";
/*  70 */       String serverPort = "";
/*  71 */       String host = request.getHeader("host");
/*  72 */       System.out.println("the host" + host);
/*  73 */       if (host != null)
/*     */       {
/*  75 */         int ind = host.indexOf(":");
/*  76 */         if (ind > -1)
/*     */         {
/*  78 */           serverHost = host.substring(0, host.indexOf(":"));
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/*  84 */           serverHost = host;
/*     */         }
/*     */         
/*  87 */         serverPort = "" + MainServer.mainserver_port;
/*  88 */         System.out.println("The severHost & Port in QEIEToolbarFrame page" + serverHost + "%%%%" + serverPort);
/*     */       }
/*     */       
/*     */ 
/*  92 */       out.write("\n<html>\n<head>\n<TITLE>AdventNet QEngine Web Test Studio : QEngine Toolbar Download & Install</TITLE> \n<link href=\"/framework/styles/blue/qengine.css\" rel=\"stylesheet\" type=\"text/css\">\n<Script language=\"javascript\" src=\"/webfunctional/js/editor.js\">\n</Script>\n<Script language=\"javascript\" src=\"/framework/js/common.js\">\n</Script>\n<script>\nfunction helpindexlink()\t\n{\n\t\tvar helplink=window.document.URL;//No I18N\n\t\thelplink=helplink.substring(0,helplink.indexOf(\"framework\"));//No I18N\n\t\thelplink=helplink+\"help/getting_started/test_script_creation.html#connecting_to_the_server\";//No I18N\n\t\tvar ele=document.getElementById(\"helpindex\");//No I18N\n\t\tele.href=helplink;//No I18N\n\t\treturn helplink;\n}\n\nfunction showdiv()\n{\n\t//alert(\"test\");\n\tvar divelem = document.getElementById('loading');\t//No I18N\n\tvar divleft = Math.floor( (document.body.clientWidth - 400) / 2);\t//No I18N\n\tvar divtop = Math.floor( (document.body.clientHeight - 150) / 2);\t//No I18N\n\tdivelem.style.display='block';//No I18N\n\tdivelem.style.top=divtop;//No I18N\n");
/*  93 */       out.write("\tdivelem.style.left=divleft;//No I18N\n\tsetTimeout(\"hidediv()\",10*1000);//No I18N\n\n}\t\nfunction hidediv()\n{\n\tvar divelem = document.getElementById('loading');\t\n\tdivelem.style.display='none';//No I18N\n}\n  </Script>\n\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" onLoad=\"showdiv();\">\n<object classid=\"clsid:E0B1B541-149F-483A-8CCA-026BA4D7CA45\" id=\"conn\" width=\"1\" height=\"1\">\n\t\t<param name=\"serverhost\" value=\"");
/*  94 */       out.print(serverHost);
/*  95 */       out.write("\"/>\n  \t\t<param name=\"serverport\" value=\"");
/*  96 */       out.print(serverPort);
/*  97 */       out.write("\"/>\n  \t\t<param name=\"clientusername\" value=\"");
/*  98 */       out.print(userName);
/*  99 */       out.write("\"/>\n  \t\t<param name=\"producttype\" value=\"rbm\"/>\n\t\t<input type=\"hidden\">\n</object>          \n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                    <tr> \n                      <td><table width=\"600\" border=\"0\" class=\"tableborder\" bgcolor=\"#f7f7f7\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t  <tr> \n          <td>&nbsp;</td>\n          <td>&nbsp;</td>\n          <td colspan=\"2\" class=\"loginBigText\">Enable QEngine Toolbar in your browser </td>\n        </tr>\n        <tr>\n          <td>&nbsp;</td>\n          <td>&nbsp;</td>\n          <td colspan=\"2\" class=\"innercellbglight\"><br></td>\n        </tr>\n        <tr> \n          <td class=\"\" width=\"24\">&nbsp;</td>\n          <td class=\"\" width=\"24\" height=\"29\"><img src=\"/framework/images/hand.gif\" align=\"absmiddle\"></td>\n          <td height=\"29\"><h2>Connect to the QEngine Server via Toolbar:</h2></td>\n          <td>&nbsp;</td>\n        </tr>\n        <tr> \n          <td>&nbsp;</td>\n          <td>&nbsp;</td>\n          <td colspan=\"2\" class=\"innercellbglight\"><ul>\n");
/* 100 */       out.write("               <li> In the brower, you will find the QEngine toolbar at the top frame. Click the <strong>Connect button <img src=\"/framework/images/connect.gif\" align=\"absmiddle\"></strong>  in the QEngine toolbar to connect to the QEngine server. To know the detailed steps, refer to the <a id=\"helpindex\" class=\"underlinecls\" target=\"_blank\">  <script language=\"JavaScript\">helpindexlink();</script>  help documentation</a>.</li>\n          </ul></td>\n        </tr>\n        <tr> \n          <td>&nbsp;</td>\n          <td>&nbsp;</td>\n          <td colspan=\"2\" class=\"innercellbglight\"><ul>\n              <li><a href=\"/framework/jsp/IETips.jsp#tbnotvisible\" class=\"underlinecls\">I can't \n                find the toolbar to connect to the server, what should i do?</a></li>\n            </ul></td>\n        </tr>\n       </table></td></tr>\n</table>\n<div id=\"loading\" STYLE=\"display:none; position: absolute; width: 650px; height: 200px; left: 3px; top: 4px;\" align=\"center\" class=\"\"> \n  <div class=\"\" align=\"center\">  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/* 101 */       out.write("    <tr>\n\t\t\t<td align=\"left\" valign=\"top\"><img src=\"/framework/images/blue/tl.gif\" alt=\"\" width=\"4\" height=\"4\"></td>\n\t\t\t<td class=\"newscriptdivbg\"><img src=\"/framework/images/spacer.gif\" alt=\"\" width=\"4\" height=\"4\"></td>\n\t\t\t<td align=\"right\" valign=\"top\"><img src=\"/framework/images/blue/tr.gif\" alt=\"\" width=\"4\" height=\"4\"></td>\n\t  </tr>\n\t\t  <tr>\n\t\t\t<td class=\"newscriptdivbg\"  width=\"4\"><img src=\"/framework/images/spacer.gif\" alt=\"\" width=\"4\" height=\"4\"></td>\n\t\t\t<td class=\"newscriptdivbg\" align=\"center\" valign=\"middle\">\n\t\t\t\n<br><br><br><span style=\"color:#ffffff; font-size:18;\">Searching for server information. .<br>Please wait . . .</span><br><br>\n\n\t\t\t\t  \n\t\t\t</td>\n\t\t\t<td class=\"newscriptdivbg\" width=\"4\"><img src=\"/framework/images/spacer.gif\" alt=\"\" width=\"4\" height=\"4\"></td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t\t\n      <td align=\"left\" valign=\"bottom\"><img src=\"/framework/images/blue/bl.gif\" alt=\"\" width=\"4\" height=\"4\"></td>\n\t\t\t\n      <td height=\"4\" class=\"newscriptdivbg\"><img src=\"/framework/images/spacer.gif\" alt=\"\" width=\"4\" height=\"4\"></td>\n");
/* 102 */       out.write("\t\t\t<td  align=\"right\" valign=\"bottom\"><img src=\"/framework/images/blue/br.gif\" alt=\"\" width=\"4\" height=\"4\"></td>\n\t\t  </tr>\n\t\t</table>\n\n</div>\n</div>\t\n\t\t\t\t  \n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 104 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 105 */         out = _jspx_out;
/* 106 */         if ((out != null) && (out.getBufferSize() != 0))
/* 107 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 108 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 111 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\QEConnectToolBarIEFrame_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */