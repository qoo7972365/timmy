/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.SupportZipUtil;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import com.manageengine.appmanager.util.ADAuthenticationUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLDecoder;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import javax.el.ExpressionFactory;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.SkipPageException;
/*      */ import javax.servlet.jsp.jstl.core.Config;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.HttpJspBase;
/*      */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*      */ import org.apache.jasper.runtime.JspSourceDependent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.SetLocaleTag;
/*      */ import org.apache.tomcat.InstanceManager;
/*      */ 
/*      */ public final class Login_jsp extends HttpJspBase implements JspSourceDependent
/*      */ {
/*   55 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   61 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/*   62 */   static { _jspx_dependants.put("/jsp/AMLogin.jsp", Long.valueOf(1473429417000L));
/*   63 */     _jspx_dependants.put("/webclient/mobile/jsp/MobileLogin.jsp", Long.valueOf(1473429417000L));
/*   64 */     _jspx_dependants.put("/jsp/PreLogin.jsp", Long.valueOf(1473429417000L));
/*   65 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private ExpressionFactory _el_expressionfactory;
/*      */   private InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*   82 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   86 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   96 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  100 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  101 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  102 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  103 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  104 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  105 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  106 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  107 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.release();
/*  108 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  115 */     HttpSession session = null;
/*      */     
/*      */ 
/*  118 */     JspWriter out = null;
/*  119 */     Object page = this;
/*  120 */     JspWriter _jspx_out = null;
/*  121 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  125 */       response.setContentType("text/html;charset=UTF-8");
/*  126 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  128 */       _jspx_page_context = pageContext;
/*  129 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  130 */       ServletConfig config = pageContext.getServletConfig();
/*  131 */       session = pageContext.getSession();
/*  132 */       out = pageContext.getOut();
/*  133 */       _jspx_out = out;
/*      */       
/*  135 */       out.write(10);
/*  136 */       out.write(10);
/*      */       
/*  138 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  139 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  140 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/*  141 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  142 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */         for (;;) {
/*  144 */           out.write("  \n\t");
/*      */           
/*  146 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  147 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  148 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  150 */           _jspx_th_c_005fwhen_005f0.setTest("${!empty mobile && mobile=='true'}");
/*  151 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  152 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */             for (;;) {
/*  154 */               out.write(10);
/*  155 */               out.write(9);
/*  156 */               out.write(9);
/*  157 */               out.write("<!DOCTYPE html>\n<!--  Do not move down the above DOCTYPE definition(let that be first line) -->\n");
/*  158 */               out.write("\n\n<!-- Applications Manager Login Screen -->\n");
/*  159 */               out.write("\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\" bgcolor=\"ccc\">\n\n\n\n\n\n\n\n<head>\n<title>");
/*  160 */               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  162 */               out.write("</title>\n<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0\">\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />\n<link href=\"/images/mobile/mobile.css\" rel=\"stylesheet\" type=\"text/css\" />\n<link rel=\"apple-touch-icon-precomposed\" href=\"/images/mobile/me_apm_icon.png\"/>\n<link rel=\"apple-touch-startup-image\" href=\"/images/mobile/apm_startup.png\"/>\n</head>\n\n");
/*      */               
/*  164 */               String mobile = request.getSession().getAttribute("mobile") == null ? "true" : (String)request.getSession().getAttribute("mobile");
/*  165 */               if (mobile.equals("true"))
/*      */               {
/*  167 */                 request.getSession().setAttribute("mobile", mobile);
/*      */               }
/*  169 */               ArrayList domainList = ADAuthenticationUtil.getDomainList();
/*  170 */               if ((domainList != null) && (domainList.size() > 0)) {
/*  171 */                 pageContext.setAttribute("domainList", domainList);
/*      */               }
/*      */               
/*  174 */               boolean ssoEnabled = AMAutomaticPortChanger.isSsoEnabled();
/*  175 */               pageContext.setAttribute("ssoEnabled", String.valueOf(ssoEnabled));
/*  176 */               String actionUrl = "/j_security_check";
/*  177 */               if (ssoEnabled) {
/*  178 */                 if ("true".equalsIgnoreCase(System.getProperty("adminConnected"))) {
/*  179 */                   actionUrl = "/cas/login";
/*  180 */                   actionUrl = EnterpriseUtil.isManagedServer() ? "https://" + EnterpriseUtil.getAdminServerHost() + ":" + EnterpriseUtil.getAdminServerPort() + actionUrl : actionUrl;
/*      */                 }
/*  182 */                 if (request.getParameter("ticket") != null) {
/*  183 */                   String serviceurl = request.getParameter("casredirecturl");
/*  184 */                   pageContext.setAttribute("serviceurl", serviceurl);
/*  185 */                   response.sendRedirect(URLDecoder.decode(serviceurl)); return;
/*      */                 }
/*      */                 
/*  188 */                 if (request.getParameter("caserror") != null) {
/*  189 */                   request.setAttribute("errormessage", "webclient.login.loginfailed.message");
/*  190 */                   String errormessage = (String)request.getAttribute("errormessage");
/*  191 */                   pageContext.setAttribute("errormessage", errormessage);
/*      */                 }
/*      */               }
/*  194 */               pageContext.setAttribute("actionUrl", actionUrl);
/*      */               
/*  196 */               out.write("\n<body onLoad=\"javascript:fnSetDefault();\" style=\"background:#264a7a url(/images/mobile/bg.png) repeat-x scroll left bottom;\">\n<div id=\"LoginDiv\" class=\"indexBG\">\n<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"");
/*  197 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  199 */               out.write("\" onSubmit='return validateUser(\"");
/*  200 */               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  202 */               out.write(34);
/*  203 */               out.write(44);
/*  204 */               out.write(34);
/*  205 */               if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  207 */               out.write("\");'>\n\t<input type=\"hidden\" name=\"j_username\" value=\"admin2\"/>\n\t");
/*  208 */               if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  210 */               out.write("\n\n<div id=\"indexLogo\" align=\"center\"><img src=\"/images/mobile/spacer.gif\" width=\"205\" height=\"69\" class=\"logoBGlarge\"/></div>\n");
/*  211 */               out.write("\n\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" class=\"loginTab\" style=\"height: 285px;\">\n  ");
/*      */               
/*  213 */               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  214 */               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  215 */               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*  216 */               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  217 */               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                 for (;;) {
/*  219 */                   out.write(10);
/*  220 */                   out.write(9);
/*      */                   
/*  222 */                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  223 */                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  224 */                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                   
/*  226 */                   _jspx_th_c_005fwhen_005f1.setTest("${errormessage!=null}");
/*  227 */                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  228 */                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                     for (;;) {
/*  230 */                       out.write("\n\t\t<tr>\n\t\t\t<td height=\"20\"><span align=\"left\" valign=\"top\"\tstyle=\"position: relative; background-color: #FFCC00; color: #000000; border-radius: 5px;\"><font color=\"#000000;\"> ");
/*  231 */                       out.print(FormatUtil.getString((String)request.getAttribute("errormessage")));
/*  232 */                       out.write("</font></span></td>\n\t\t</tr>\n\t");
/*  233 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  234 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  238 */                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  239 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                   }
/*      */                   
/*  242 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  243 */                   out.write(10);
/*  244 */                   out.write(9);
/*  245 */                   if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/*      */                     return;
/*  247 */                   out.write(10);
/*  248 */                   out.write(32);
/*  249 */                   out.write(32);
/*  250 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  251 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  255 */               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  256 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */               }
/*      */               
/*  259 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  260 */               out.write("\n  <tr>\n    <td height=\"20\" align=\"left\" valign=\"middle\" style=\"padding-left: 2px;\">");
/*  261 */               if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  263 */               out.write("</td>\n  </tr>\n  <tr>\n    <td height=\"35\" align=\"left\" valign=\"top\"><input name=\"username\" type=\"text\" class=\"formTxtField\"/></td>\n  </tr>\n  <tr>\n    <td height=\"20\" align=\"left\" valign=\"middle\" style=\"padding-left: 2px;\">");
/*  264 */               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  266 */               out.write("</td>\n  </tr>\n  <tr>\n    <td height=\"35\" align=\"left\" valign=\"top\"><input name=\"j_password\" type=\"password\" class=\"formTxtField\"/></td>\n  </tr>  \n  <tr>\n\t<td>\n\t\t");
/*  267 */               if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  269 */               out.write("\n\t</td>\n  </tr>\n  <tr>\n    <td height=\"15\" align=\"left\"><table><tr><td><input type=\"checkbox\" name=\"remember\" value=\"true\" class=\"formChkBox\" onchange=\"javascript: fnRemember()\"/></td><td>");
/*  270 */               if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  272 */               out.write("</td></tr></table> </td>\n  </tr>\n  <tr>\n    <td height=\"70\" align=\"center\" valign=\"top\"> <input class=\"loginBtn\" name=\"button\" type=\"submit\" value=\"");
/*  273 */               if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  275 */               out.write("\" ></td>\n  </tr>\n  <tr>\n\t  <td align=\"center\" valign=\"bottom\" style=\"color:#99B1D0;\">");
/*  276 */               out.print(FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") }));
/*  277 */               out.write("</td>\n  </tr>\n</table>\n\n</form>\n</div>\n</body>\n\n");
/*  278 */               out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  279 */               out.write("\n<script src=\"/template/mobile.js\"></script>\n<script language=\"javascript\" SRC=\"/webclient/common/js/validation.js\" type=\"text/javascript\"></script>\n<script type=\"text/javascript\">\n\tif ('standalone' in navigator && !navigator.standalone && (/iphone|ipod|ipad/gi).test(navigator.platform) && (/Safari/i).test(navigator.appVersion)) {\n\t\tvar addToHomeConfig = {\n\t\t\t\tanimationIn:'drop',\t\t// Animation In\t\t//NO I18N\n\t\t\t\tanimationOut:'drop',\t// Animation Out\t//NO I18N\n\t\t\t\tlifespan:15000,\t\t\t// The popup lives 15 seconds\n\t\t\t\texpire:5,\t\t\t\t// The popup is shown only once every 5 minutes\n\t\t\t\ttouchIcon:true,\n\t\t\t\t//message:'This is a custom message. Your device is an <strong>%device</strong>. The action icon is `%icon`.'\n\t\t\t};\n\t\tdocument.write('<link rel=\"stylesheet\" href=\"/images/mobile/style/add2home.css\">');\n\t\tdocument.write('<script type=\"application/javascript\" src=\"/images/mobile/script/add2home.js\"><\\/script>');\n\t}\n\t$(document).ready(function() {\n\t\twindow.addEventListener(\"load\",function() {\n\t\t  setTimeout(function(){\n\t\t    // Hide the address bar!\n");
/*  280 */               out.write("\t\t    window.scrollTo(0, 1);\n\t\t  }, 10);\n\t\t});\n\t});\n\n\t");
/*      */               
/*      */ 
/*  283 */               if (request.getAttribute("errormessage") == null)
/*      */               {
/*      */ 
/*  286 */                 out.write("\t\n\t\tif(true){\n\t\t\tif (document.all) //IE4+ specific code\n\t\t\t{\n\t\t\t\tdocument.cookie=\"testcookie\"\n\t\t\t\tcookieEnabled=(document.cookie.indexOf(\"testcookie\")!=-1)? true : false\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tSet_Cookie('testcookie','',expires);//NO I18N\n\t\t\t\tvar tempCookie = Get_Cookie('testcookie');//NO I18N\n\t\t\t\tif(tempCookie !=null)\n\t\t\t\t{\n\t\t\t\t\tcookieEnabled=true;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\tcookieEnabled=false;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t\tif (!cookieEnabled)\n\t\t{\n\t\t\tlocation.href=\"/html/EnableCookies.html\";\n\t\t}\n\t");
/*      */               }
/*  288 */               out.write("\n</script>\n</html>\n");
/*  289 */               out.write(9);
/*  290 */               out.write(10);
/*  291 */               out.write(9);
/*  292 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  293 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  297 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  298 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */           }
/*      */           
/*  301 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  302 */           out.write(10);
/*  303 */           out.write(9);
/*      */           
/*  305 */           WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  306 */           _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  307 */           _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  309 */           _jspx_th_c_005fwhen_005f2.setTest("${!empty param.showPreLogin && param.showPreLogin=='true'}");
/*  310 */           int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  311 */           if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */             for (;;) {
/*  313 */               out.write(10);
/*  314 */               out.write(9);
/*  315 */               out.write(9);
/*  316 */               out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/*  318 */               String spversion = AMAutomaticPortChanger.getServicePackVersion() != null ? AMAutomaticPortChanger.getServicePackVersion() : "None";
/*  319 */               String whatIsNewlink = OEMUtil.getOEMString("company.whatisnew.link");
/*  320 */               String productLogo = "/images/new_login-logo.png";
/*  321 */               String productLink = FormatUtil.getString("company.loginpage.link");
/*  322 */               String tShootlink = "http://apm.manageengine.com/index.html";
/*  323 */               String faqlink = "http://www.manageengine.com/products/applications_manager/applications-management-genfaq.html" + (System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/*  324 */               pageContext.setAttribute("ISOEM", "false");
/*      */               
/*  326 */               if (OEMUtil.isOEM())
/*      */               {
/*  328 */                 pageContext.setAttribute("ISOEM", "true");
/*  329 */                 tShootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  330 */                 productLogo = OEMUtil.getOEMString("am.loginpage.logo");
/*  331 */                 productLink = OEMUtil.getOEMString("company.loginpage.link");
/*  332 */                 faqlink = OEMUtil.getOEMString("company.faq.link");
/*      */               }
/*      */               
/*      */               try
/*      */               {
/*  337 */                 if ((Constants.isMsSQLDbConnectionRestart()) && (DBQueryUtil.getDBType().equals("mssql")))
/*      */                 {
/*  339 */                   ManagedApplication.refreshDBConection();
/*  340 */                   Constants.setmsSQLDbConnectionRestart(false);
/*  341 */                   System.out.println("login.jsp:setting to false after refreshing: Constants.isMsSQLDbConnectionRestart()" + Constants.isMsSQLDbConnectionRestart());
/*      */                 }
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/*  346 */                 ex.printStackTrace();
/*      */               }
/*      */               
/*  349 */               String locale = System.getProperty("locale");
/*  350 */               String dbtype = System.getProperty("am.dbserver.type");
/*  351 */               request.setAttribute("locale", locale);
/*  352 */               if (locale.equals("vi_VN"))
/*      */               {
/*  354 */                 Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  355 */                 request.setCharacterEncoding("UTF-8");
/*      */               }
/*  357 */               else if (locale.equals("fr_FR"))
/*      */               {
/*  359 */                 Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  360 */                 request.setCharacterEncoding("UTF-8");
/*      */               }
/*      */               else
/*      */               {
/*  364 */                 out.write(10);
/*  365 */                 out.write(9);
/*  366 */                 out.write(9);
/*  367 */                 if (_jspx_meth_fmt_005fsetLocale_005f0(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  369 */                 out.write(10);
/*  370 */                 out.write(9);
/*      */               }
/*      */               
/*  373 */               if ((OEMUtil.isOEM()) && (OEMUtil.Address == null))
/*      */               {
/*  375 */                 Enumeration e = request.getHeaderNames();
/*  376 */                 while (e.hasMoreElements())
/*      */                 {
/*  378 */                   String headers = (String)e.nextElement();
/*  379 */                   if ((headers != null) && (headers.equalsIgnoreCase("host")))
/*      */                   {
/*  381 */                     String[] h1 = request.getHeader(headers).toString().split(":");
/*  382 */                     OEMUtil.Address = h1[0];
/*  383 */                     OEMUtil.initializeProperties();
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*  389 */               String username = "";
/*  390 */               String password = "";
/*  391 */               String licenseType = "null";
/*  392 */               String prodVersion = "null";
/*  393 */               int usersPermitted = -1;
/*  394 */               int monPermitted = -5;
/*  395 */               String monPermittedStr = "";
/*  396 */               String addonsEnabled = "";
/*      */               
/*      */               try
/*      */               {
/*  400 */                 FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  401 */                 licenseType = fd.getCategory();
/*  402 */                 prodVersion = OEMUtil.getOEMString("product.build.number");
/*  403 */                 usersPermitted = fd.getNumberOfUsersPermitted();
/*  404 */                 monPermitted = fd.getNumberOfMonitorsPermitted();
/*  405 */                 addonsEnabled = fd.getAddonsEnabledAsString().replaceAll(",", "_") + "&expiresOn=" + fd.getDateOfLicenseExpiry();
/*  406 */                 if (monPermitted == -1)
/*      */                 {
/*  408 */                   monPermittedStr = "unlimited";
/*      */                 }
/*      */                 else
/*      */                 {
/*  412 */                   monPermittedStr = String.valueOf(monPermitted);
/*      */                 }
/*      */               }
/*      */               catch (Exception exc)
/*      */               {
/*  417 */                 exc.printStackTrace();
/*  418 */                 licenseType = "null";
/*  419 */                 prodVersion = "null";
/*  420 */                 usersPermitted = -1;
/*  421 */                 monPermittedStr = "-5";
/*      */               }
/*      */               
/*      */ 
/*  425 */               FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  426 */               request.setAttribute("showShockieMessage", Boolean.valueOf(fd.isShockieLicense()));
/*  427 */               String user = fd.getUserType();
/*  428 */               String expirydate = fd.getDateOfLicenseExpiry();
/*  429 */               long evaluationdays = fd.getEvaluationDays();
/*  430 */               String server = "local";
/*  431 */               String url = "";
/*      */               
/*      */               try
/*      */               {
/*  435 */                 server = InetAddress.getLocalHost().getHostAddress();
/*  436 */                 server = SupportZipUtil.getAddrLong(server) + "";
/*  437 */                 int length = server.length();
/*  438 */                 server = server.substring(length - 4, length);
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/*  442 */                 ee.printStackTrace();
/*      */               }
/*      */               
/*  445 */               if (user.equals("R"))
/*      */               {
/*  447 */                 user = "a";
/*  448 */                 url = "si=" + server + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*  450 */               else if (user.equals("T"))
/*      */               {
/*  452 */                 user = "b";
/*  453 */                 url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*  455 */               else if (user.equals("F"))
/*      */               {
/*  457 */                 user = "c";
/*  458 */                 url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*      */               
/*  461 */               if (dbtype != null)
/*      */               {
/*  463 */                 url = url + "&db=" + dbtype.toLowerCase();
/*      */               }
/*      */               
/*  466 */               String DATE_FORMAT = "EEE, d MMMM yyyy HH:mm:ss Z";
/*  467 */               DateFormat df = new SimpleDateFormat(DATE_FORMAT);
/*  468 */               String generatedTime = df.format(new Date());
/*      */               
/*  470 */               String usertype = fd.getUserType();
/*  471 */               long daysremaining = fd.getExpiryDate();
/*  472 */               pageContext.setAttribute("usertype", usertype);
/*  473 */               String loginmessage = "";
/*  474 */               String licenseMessage = "";
/*  475 */               String reloginMessage = FormatUtil.getString("am.webclient.plugin.reloginmessage.text");
/*  476 */               pageContext.setAttribute("showLoginMsg", "false");
/*  477 */               pageContext.setAttribute("showNewFeatures", "false");
/*  478 */               pageContext.setAttribute("showLicenseMsg", "false");
/*  479 */               if (!OEMUtil.isOEM())
/*      */               {
/*  481 */                 if ((spversion.equalsIgnoreCase("None")) || (PluginUtil.isPlugin()))
/*      */                 {
/*  483 */                   pageContext.setAttribute("showLoginMsg", "true");
/*  484 */                   if (PluginUtil.isPlugin()) {
/*  485 */                     loginmessage = FormatUtil.getString("am.webclient.plugin.defaultusernamemessage.text");
/*      */                   } else {
/*  487 */                     loginmessage = FormatUtil.getString("am.webclient.newlogin.defaultusernamemessage.text");
/*  488 */                     reloginMessage = loginmessage;
/*      */                   }
/*  490 */                   pageContext.setAttribute("isPlugin", "" + PluginUtil.isPlugin());
/*      */                 }
/*      */                 else
/*      */                 {
/*  494 */                   pageContext.setAttribute("showNewFeatures", "true");
/*      */                 }
/*      */                 
/*  497 */                 if (user.equals("a"))
/*      */                 {
/*  499 */                   if (!expirydate.equals("never"))
/*      */                   {
/*  501 */                     if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                     {
/*  503 */                       pageContext.setAttribute("showLicenseMsg", "true");
/*  504 */                       licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense2.text", new String[] { String.valueOf(daysremaining), licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*  505 */                       if (daysremaining == 0L)
/*      */                       {
/*  507 */                         licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense1.text", new String[] { licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*  512 */                 else if (user.equals("b"))
/*      */                 {
/*  514 */                   if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                   {
/*  516 */                     pageContext.setAttribute("showLicenseMsg", "true");
/*  517 */                     licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense2.text", new String[] { String.valueOf(daysremaining) });
/*  518 */                     if (daysremaining == 0L)
/*      */                     {
/*  520 */                       licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense1.text");
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/*  526 */               String buildNo = OEMUtil.getOEMString("product.build.number");
/*      */               
/*      */ 
/*  529 */               String buildType = "&nbsp;&nbsp;";
/*  530 */               String buildInfo = FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildno.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()), OEMUtil.getOEMString("product.name") });
/*      */               
/*  532 */               if ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()))
/*      */               {
/*  534 */                 buildType = EnterpriseUtil.getServerTypeDisplayName() + buildType;
/*      */                 
/*  536 */                 buildInfo = ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()) ? buildType : "") + FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildnonoprodname.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()) });
/*      */               }
/*      */               
/*      */ 
/*  540 */               String footerMsg = "";
/*      */               
/*  542 */               String copyright = FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") });
/*  543 */               if (OEMUtil.isOEM())
/*      */               {
/*  545 */                 copyright = OEMUtil.getOEMString("company.startyear") + "-" + OEMUtil.getOEMString("company.currentyear") + " " + OEMUtil.getOEMString("company.name");
/*      */               }
/*      */               else
/*      */               {
/*  549 */                 footerMsg = footerMsg + FormatUtil.getString("am.webclient.newlogin.prodinfo.text") + " ";
/*      */               }
/*      */               
/*  552 */               ArrayList domainList = ADAuthenticationUtil.getDomainList();
/*  553 */               if ((domainList != null) && (domainList.size() > 0)) {
/*  554 */                 pageContext.setAttribute("domainList", domainList);
/*      */               }
/*      */               
/*  557 */               String errormessage = (String)request.getAttribute("errormessage");
/*  558 */               pageContext.setAttribute("showErrMsg", Boolean.toString(errormessage != null));
/*      */               
/*  560 */               out.write("\n<html>\n\t<head>\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n\t\t<title>");
/*  561 */               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                 return;
/*  563 */               out.write("</title>\n\t    <link href=\"/images/prelogin.css\" rel=\"stylesheet\" type=\"text/css\">\n\t    ");
/*  564 */               out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  565 */               out.write("\n\t\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/login.js\"></SCRIPT>\n\t</head>\n\t<body onLoad=\"javascript:getImage();\">\n\t\t");
/*  566 */               if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                 return;
/*  568 */               out.write("\n\t\t<div class=\"header\" style=\"display:block\" align=\"left\" width=\"98%\">\n\t\t\t<div class=\"logo\" width=\"20%\">\n\t\t\t\t<img src=\"/images/am_logo.png\"/>\n\t\t\t\t<!-- <img src=\"/images/APM-logo.png\"/> -->\n\t\t\t</div>\n\t\t\t<div class=\"preloginDiv\">\n\t\t\t\t<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"/j_security_check\" onSubmit='return validateUser();' >\n\t\t\t\t\t<input type=\"hidden\" name=\"clienttype\" value=\"html\">\n\t\t\t    \t<input type=\"hidden\" name=\"webstart\">\n\t\t\t    \t<input type=\"hidden\" name=\"j_username\">\n\t\t\t    \t<input type=\"hidden\" name=\"ScreenWidth\" value=\"1280\">\n\t\t\t    \t<input type=\"hidden\" name=\"ScreenHeight\" value=\"709\">\n\t\t\t\t\t<p class=\"inline username\">\n\t\t\t\t\t\t<input width=\"80%\" type=\"text\" class=\"icon-user\" name=\"username\" placeholder=\"");
/*  569 */               out.print(FormatUtil.getString("am.webclient.newlogin.username.text"));
/*  570 */               out.write("\" autocomplete=\"off\" autocapitalize=\"off\">\n\t\t\t\t\t\t<span style=\"display:none\"><em id=\"firsttime-user\"><a onclick=\"showHideHint('show')\">");
/*  571 */               out.print(FormatUtil.getString("am.webclient.newlogin.firsttime.text"));
/*  572 */               out.write("</a></em></span>\n\t\t\t\t\t</p>\n\t\t\t\t\t<p class=\"inline password\">\n\t\t\t\t\t\t<input width=\"80%\" type=\"password\" class=\"icon-lock\" name=\"j_password\" placeholder=\"");
/*  573 */               out.print(FormatUtil.getString("am.webclient.newlogin.password.text"));
/*  574 */               out.write("\" autocomplete=\"off\">\n\t\t\t\t\t\t<span style=\"display:block;padding-right: 10px;\" align=\"right\">\n\t\t\t\t\t\t\t<input type=\"checkbox\" id=\"RememberMe\" name=\"remember\" onClick=\"javascript: fnRemember()\">\n\t\t\t\t\t\t\t<!-- <label for=\"RememberMe\" class=\"checkboxtext\">Remember password</label> -->\n\t\t\t\t\t\t\t<em id=\"firsttime-user\">");
/*  575 */               out.print(FormatUtil.getString("am.webclient.newlogin.rememberme.text"));
/*  576 */               out.write("</em>\n\t\t\t\t\t\t</span>\n\t\t\t\t\t</p>\n\t\t\t\t\t");
/*      */               
/*  578 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  579 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  580 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f2);
/*      */               
/*  582 */               _jspx_th_c_005fif_005f2.setTest("${domainList != null }");
/*  583 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  584 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/*  586 */                   out.write("\n\t\t\t\t\t<p class=\"inline domain\">\n\t\t\t\t\t\t<select name=\"domain\" class=\"icon-domain\">\n\t\t\t\t\t\t\t<option value='");
/*  587 */                   if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                     return;
/*  589 */                   out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/*  590 */                   out.print(FormatUtil.getString("am.webclient.loginpage.local.auth.text"));
/*  591 */                   out.write("</option>\n\t\t\t\t\t\t\t");
/*  592 */                   if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                     return;
/*  594 */                   out.write("\n\t\t\t\t\t\t</select>\n\t\t\t\t\t</p>\n\t\t\t\t    ");
/*  595 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  596 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  600 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  601 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/*  604 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  605 */               out.write("\n\t\t\t\t\t<p style=\"display:inline-block;vertical-align: top;margin-top: 10px;\">\n\t\t\t\t\t\t<input type=\"submit\" class=\"login_btn\" name=\"submit\" value=\"");
/*  606 */               out.print(FormatUtil.getString("am.webclient.login.login.text"));
/*  607 */               out.write("\">\n\t\t\t\t\t</p>\n\t\t\t\t</form>\n\t\t\t</div>\n\t\t</div>\n\t\t<div id=\"container\" align=\"center\">\n\t\t    <div id=\"mainnav\" align=\"center\" valign=\"middle\">\n\t\t    \t<a href=\"");
/*  608 */               if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                 return;
/*  610 */               out.write("\"><img src=\"/images/home.png\" class=\"home-icon\" title=\"");
/*  611 */               out.print(FormatUtil.getString("webclient.login.logoutpage.backtologin"));
/*  612 */               out.write("\"/></a> ");
/*  613 */               out.print(FormatUtil.getString("am.webclient.newlogin.logout.msg.text"));
/*  614 */               out.write("\n\t\t\t</div>\n\t\t\t<div id=\"apmOnlineContent\">\n\t\t\t\t<div id=\"onlinecontent\">\n\t\t\t\t\t<iframe name=\"onlinecontent\" src=\"");
/*  615 */               out.print(FormatUtil.getString("am.webclient.apmlogout.link"));
/*  616 */               out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/*  617 */               out.write("\" width=\"100%\" height=\"470px\" frameborder=\"0\" style=\"margin-top: -5px;\"></iframe>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div id=\"footer\" width=\"100%\" align=\"center\">\n\t\t\t<p class=\"footertext\" align=\"center\">\n\t\t\t\t<img name=\"copy\" src=\"\" style=\"display:none\"/>\n\t\t\t\t&copy 2015 <a href=\"https://www.zohocorp.com");
/*  618 */               out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/*  619 */               out.write("\" target=\"_blank\">");
/*  620 */               out.print(FormatUtil.getString("company.name"));
/*  621 */               out.write("</a> ");
/*  622 */               out.print(FormatUtil.getString("am.webclient.newlogin.allrights.text"));
/*  623 */               out.write("\n\t\t\t</p>\n\t\t</div>\n\t\t<script type=\"text/javascript\">\n\t\t\t$(document).ready(function(){\n\t\t\t\t// if(!navigator.onLine){\n\t\t\t\t// \tlocation.href='/index.do';\n\t\t\t\t// } \n\n\t\t\t\tsetTimeout(resetIframeSize, 10);\n\t\t\t});\n\n\t\t\t$(window).resize(function(){\n\t\t\t\tsetTimeout(resetIframeSize, 10);\n\t\t\t});\n\n\t\t\tfunction resetIframeSize(){\n\t\t\t\texpectedIFrameHeight = $(document).height() - $('.header ').height() - 90;\n\t\t\t\tcurrentIframeheight = $('#onlinecontent').height();\n\t\t\t\tif(expectedIFrameHeight > currentIframeheight){\n\t\t\t\t\tconsole.log(expectedIFrameHeight);\n\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height',expectedIFrameHeight+'px');\t//NO I18N \n\t\t\t\t\t$('#footer').css({'margin-top':'inherit','bottom':'0px','position':'absolute'});\t//NO I18N \n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height','470px');\t//NO I18N \n\t\t\t\t\t$('#footer').css({'margin-top':'45px','bottom':'inherit','position':'relative'});\t//No I18N \n\t\t\t\t\t//below code in this else loop will avoid the flickering issue while decreasing the iframe size.\n\t\t\t\t\texpectedIFrameHeight = $(document).height() - $('.header ').height() - 90;\n");
/*  624 */               out.write("\t\t\t\t\tcurrentIframeheight = $('#onlinecontent').height();\n\t\t\t\t\tif(expectedIFrameHeight > currentIframeheight){\n\t\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height',expectedIFrameHeight+'px');\t//NO I18N \n\t\t\t\t\t\t$('#footer').css({'margin-top':'inherit','bottom':'0px','position':'absolute'});\t//NO I18N \n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\n\t\t\tDelete_Cookie('selectedtab');//No I18N\t\n\n\t\t\tfunction validateUser()\n\t\t\t{\n\t\t\t\tif(trimAll(document.loginForm.username.value)== '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  625 */               out.print(FormatUtil.getString("am.webclient.login.jsalertforusername.text"));
/*  626 */               out.write("\");\n\t\t\t\t\tdocument.loginForm.username.focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if(trimAll(document.loginForm.j_password.value)== '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  627 */               out.print(FormatUtil.getString("am.webclient.login.jsalertforpassowrd.text"));
/*  628 */               out.write("\");\n\t\t\t\t\tdocument.loginForm.j_password.focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\tvar usr = document.loginForm.username.value;\n\t\t\t\tdocument.loginForm.j_username.value=usr;\n\n\t\t\t\tif(document.loginForm.domain){\n\t\t\t\t\tvar domainCombo = document.loginForm.domain;\n\t\t\t\t\tvar domainSel = domainCombo.options[domainCombo.selectedIndex].value;\n\t\t\t\t\tdocument.loginForm.j_username.value = domainSel +\"\\\\\"+ usr;//No i18n\n\t\t\t\t}\n\t\t\t}\n\n\t\t\t");
/*  629 */               if (PluginUtil.isPlugin()) {
/*  630 */                 out.write("\n\t\t\t$(window).one('load',function(){\t//NO I18N \n\t\t\t\tusername=$.getUrlParam('opm_user');\t//NO I18N \n\t\t\t\tif(username && window!=top){\n\t\t\t\t\tdocument.loginForm.username.value=username;\n\t\t\t\t\tdocument.loginForm.j_username.value=username;\n\t\t\t\t\tdocument.loginForm.j_password.value=username+'@opm';\t//No i18n \n\t\t\t\t\t$(\"input[name='submit']\").click();\t//NO I18N\t\n\t\t\t\t}\n\t\t\t});\n\t\t\t");
/*      */               }
/*  632 */               out.write("\n\n\t\t\tfunction getImage()\n\t\t\t{\n\t\t\t\tDelete_Cookie('am_monitorsview');//No I18N\n\t\t\t\tDelete_Cookie('am_applicationsview');//No I18N\n\t\t\t\tDelete_Cookie('am_mgview');//No I18N\n\t\t\t\tGet_Cookie('selectedtab');//NO I18N\n\n\t\t\t\tif(navigator.onLine){\n\t\t\t\t\timg=new Image();\n\t\t\t\t\timg.src=\"http://www.manageengine.com/images/copyright.gif?");
/*  633 */               out.print(url);
/*  634 */               out.print(System.getProperty("did") != null ? "&" + System.getProperty("did") : "");
/*  635 */               out.write("\"; //No I18N\t\n\t\t\t\t}\n\t\t\t\t// we are hiding this link as we dont have support for forget password yet\n\t\t\t\t// document.getElementById(\"loginForgot\").style.display=\"none\";\n\t\t\t\t");
/*  636 */               if ((!"R".equals(fd.getUserType())) && (request.getParameter("product") == null))
/*      */               {
/*  638 */                 out.write("\n\t\t\t\t\tif(navigator.onLine && img.height==16){\n\t\t\t\t\t\tdocument['copy'].src=img.src;\n\t\t\t\t\t}\n\t\t\t\t");
/*      */               }
/*  640 */               if (request.getAttribute("clearcookie") != null)
/*      */               {
/*  642 */                 username = "";
/*  643 */                 password = "";
/*      */               }
/*  645 */               out.write("\n\t\t\t\tfnSetDefault();\n\t\t\t}\n\t\t\t\n\t\t\t");
/*  646 */               if (_jspx_meth_c_005fchoose_005f3(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                 return;
/*  648 */               out.write("\n\n\t\t\t$(document).ready(function() {\n\t\t\t  \t//to fix the pixel differences b/w input and dropdown boxes.\n\t\t\t  \t$('.icon-domain').width($('.icon-lock').width() + 22);\n\t\t\t  \t$(document).resize(function() {\n\t\t\t    \t$('.icon-domain').width($('.icon-lock').width() + 20);\n\t\t\t  \t});\n\t\t\t  \tif(window!=top){\n\t\t\t\t\t$('.loginhint').html('");
/*  649 */               out.print(reloginMessage);
/*  650 */               out.write("'); \t//NO I18N\n\t\t\t\t}\n\t\t\t});\n\t\n\t\t</script>\n\t</body>\n</html>\n");
/*  651 */               out.write(9);
/*  652 */               out.write(10);
/*  653 */               out.write(9);
/*  654 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  655 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  659 */           if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  660 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */           }
/*      */           
/*  663 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  664 */           out.write(10);
/*  665 */           out.write(9);
/*      */           
/*  667 */           OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  668 */           _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  669 */           _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f0);
/*  670 */           int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  671 */           if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */             for (;;) {
/*  673 */               out.write(10);
/*  674 */               out.write(9);
/*  675 */               out.write(9);
/*  676 */               out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/*  678 */               String spversion = AMAutomaticPortChanger.getServicePackVersion() != null ? AMAutomaticPortChanger.getServicePackVersion() : "None";
/*  679 */               String whatIsNewlink = OEMUtil.getOEMString("company.whatisnew.link");
/*  680 */               String productLogo = "/images/APM-logo.png";
/*      */               
/*  682 */               String forumsLink = FormatUtil.getString("company.forum.link");
/*  683 */               String blogsLink = FormatUtil.getString("company.blogs.link");
/*  684 */               String faqlink = FormatUtil.getString("company.faq.link");
/*  685 */               String tShootlink = FormatUtil.getString("company.troubleshoot.link");
/*  686 */               String supportLink = FormatUtil.getString("company.support.link");
/*  687 */               String productLink = FormatUtil.getString("company.loginpage.link");
/*      */               
/*  689 */               String getInTouchLink = FormatUtil.getString("am.webclient.newlogin.getintouch.link");
/*  690 */               String featuresLink = FormatUtil.getString("am.webclient.newlogin.features.link");
/*  691 */               String techresourcesLink = FormatUtil.getString("am.webclient.newlogin.techresources.link");
/*  692 */               String newfeaturesLink = FormatUtil.getString("am.webclient.newlogin.newfeatures.link");
/*  693 */               String quotesLink = FormatUtil.getString("am.webclient.newlogin.quotes.link");
/*  694 */               boolean ssoEnabled = AMAutomaticPortChanger.isSsoEnabled();
/*      */               
/*      */ 
/*  697 */               pageContext.setAttribute("ssoEnabled", String.valueOf(ssoEnabled));
/*  698 */               String serviceurl = request.getParameter("casredirecturl");
/*  699 */               pageContext.setAttribute("serviceurl", serviceurl);
/*  700 */               String ticket = request.getParameter("ticket");
/*      */               
/*      */ 
/*  703 */               if ((ssoEnabled) && (ticket != null))
/*      */               {
/*  705 */                 serviceurl = URLDecoder.decode(serviceurl);
/*  706 */                 response.sendRedirect(serviceurl); return;
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*  711 */               pageContext.setAttribute("ISOEM", "false");
/*      */               
/*  713 */               if (OEMUtil.isOEM())
/*      */               {
/*  715 */                 pageContext.setAttribute("ISOEM", "true");
/*  716 */                 tShootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  717 */                 productLogo = OEMUtil.getOEMString("am.loginpage.logo");
/*  718 */                 productLink = OEMUtil.getOEMString("company.loginpage.link");
/*  719 */                 faqlink = OEMUtil.getOEMString("company.faq.link");
/*      */               }
/*      */               
/*      */               try
/*      */               {
/*  724 */                 if ((Constants.isMsSQLDbConnectionRestart()) && (DBQueryUtil.getDBType().equals("mssql")))
/*      */                 {
/*  726 */                   ManagedApplication.refreshDBConection();
/*  727 */                   Constants.setmsSQLDbConnectionRestart(false);
/*  728 */                   System.out.println("login.jsp:setting to false after refreshing: Constants.isMsSQLDbConnectionRestart()" + Constants.isMsSQLDbConnectionRestart());
/*      */                 }
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/*  733 */                 ex.printStackTrace();
/*      */               }
/*      */               
/*  736 */               String locale = System.getProperty("locale");
/*  737 */               String dbtype = System.getProperty("am.dbserver.type");
/*  738 */               request.setAttribute("locale", locale);
/*  739 */               if (locale.equals("vi_VN"))
/*      */               {
/*  741 */                 Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  742 */                 request.setCharacterEncoding("UTF-8");
/*      */               }
/*  744 */               else if (locale.equals("fr_FR"))
/*      */               {
/*  746 */                 Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  747 */                 request.setCharacterEncoding("UTF-8");
/*      */               }
/*      */               else
/*      */               {
/*  751 */                 out.write(10);
/*  752 */                 out.write(9);
/*  753 */                 out.write(9);
/*  754 */                 if (_jspx_meth_fmt_005fsetLocale_005f1(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                   return;
/*  756 */                 out.write(10);
/*  757 */                 out.write(9);
/*      */               }
/*  759 */               out.write(10);
/*  760 */               out.write(10);
/*  761 */               out.write(9);
/*      */               
/*  763 */               if ((OEMUtil.isOEM()) && (OEMUtil.Address == null))
/*      */               {
/*  765 */                 Enumeration e = request.getHeaderNames();
/*  766 */                 while (e.hasMoreElements())
/*      */                 {
/*  768 */                   String headers = (String)e.nextElement();
/*  769 */                   if ((headers != null) && (headers.equalsIgnoreCase("host")))
/*      */                   {
/*  771 */                     String[] h1 = request.getHeader(headers).toString().split(":");
/*  772 */                     OEMUtil.Address = h1[0];
/*  773 */                     OEMUtil.initializeProperties();
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*  779 */               String username = "";
/*  780 */               String password = "";
/*  781 */               String licenseType = "null";
/*  782 */               String prodVersion = "null";
/*  783 */               int usersPermitted = -1;
/*  784 */               int monPermitted = -5;
/*  785 */               String monPermittedStr = "";
/*  786 */               String addonsEnabled = "";
/*      */               
/*      */               try
/*      */               {
/*  790 */                 FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  791 */                 licenseType = fd.getCategory();
/*  792 */                 prodVersion = OEMUtil.getOEMString("product.build.number");
/*  793 */                 usersPermitted = fd.getNumberOfUsersPermitted();
/*  794 */                 monPermitted = fd.getNumberOfMonitorsPermitted();
/*  795 */                 addonsEnabled = fd.getAddonsEnabledAsString().replaceAll(",", "_") + "&expiresOn=" + fd.getDateOfLicenseExpiry();
/*  796 */                 if (monPermitted == -1)
/*      */                 {
/*  798 */                   monPermittedStr = "unlimited";
/*      */                 }
/*      */                 else
/*      */                 {
/*  802 */                   monPermittedStr = String.valueOf(monPermitted);
/*      */                 }
/*      */               }
/*      */               catch (Exception exc)
/*      */               {
/*  807 */                 exc.printStackTrace();
/*  808 */                 licenseType = "null";
/*  809 */                 prodVersion = "null";
/*  810 */                 usersPermitted = -1;
/*  811 */                 monPermittedStr = "-5";
/*      */               }
/*      */               
/*      */ 
/*  815 */               FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  816 */               request.setAttribute("showShockieMessage", Boolean.valueOf(fd.isShockieLicense()));
/*  817 */               String user = fd.getUserType();
/*  818 */               String expirydate = fd.getDateOfLicenseExpiry();
/*  819 */               long evaluationdays = fd.getEvaluationDays();
/*  820 */               String server = "local";
/*  821 */               String url = "";
/*      */               
/*      */               try
/*      */               {
/*  825 */                 server = InetAddress.getLocalHost().getHostAddress();
/*  826 */                 server = SupportZipUtil.getAddrLong(server) + "";
/*  827 */                 int length = server.length();
/*  828 */                 server = server.substring(length - 4, length);
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/*  832 */                 ee.printStackTrace();
/*      */               }
/*      */               
/*  835 */               if (user.equals("R"))
/*      */               {
/*  837 */                 user = "a";
/*  838 */                 url = "si=" + server + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*  840 */               else if (user.equals("T"))
/*      */               {
/*  842 */                 user = "b";
/*  843 */                 url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*  845 */               else if (user.equals("F"))
/*      */               {
/*  847 */                 user = "c";
/*  848 */                 url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*      */               
/*  851 */               if (dbtype != null)
/*      */               {
/*  853 */                 url = url + "&db=" + dbtype.toLowerCase();
/*      */               }
/*      */               
/*  856 */               String DATE_FORMAT = "EEE, d MMMM yyyy HH:mm:ss Z";
/*  857 */               DateFormat df = new SimpleDateFormat(DATE_FORMAT);
/*  858 */               String generatedTime = df.format(new Date());
/*      */               
/*  860 */               String usertype = fd.getUserType();
/*  861 */               long daysremaining = fd.getExpiryDate();
/*  862 */               pageContext.setAttribute("usertype", usertype);
/*  863 */               String loginmessage = "";
/*  864 */               String licenseMessage = "";
/*  865 */               String reloginMessage = FormatUtil.getString("am.webclient.plugin.reloginmessage.text");
/*  866 */               pageContext.setAttribute("showLoginMsg", "false");
/*  867 */               pageContext.setAttribute("showNewFeatures", "false");
/*  868 */               pageContext.setAttribute("showLicenseMsg", "false");
/*  869 */               if (!OEMUtil.isOEM())
/*      */               {
/*  871 */                 if ((spversion.equalsIgnoreCase("None")) || (PluginUtil.isPlugin()))
/*      */                 {
/*  873 */                   pageContext.setAttribute("showLoginMsg", "true");
/*  874 */                   if (PluginUtil.isPlugin()) {
/*  875 */                     loginmessage = FormatUtil.getString("am.webclient.plugin.defaultusernamemessage.text");
/*      */                   } else {
/*  877 */                     loginmessage = FormatUtil.getString("am.webclient.newlogin.defaultusernamemessage.text");
/*  878 */                     reloginMessage = loginmessage;
/*      */                   }
/*  880 */                   pageContext.setAttribute("isPlugin", "" + PluginUtil.isPlugin());
/*      */                 }
/*      */                 
/*  883 */                 if (user.equals("a"))
/*      */                 {
/*  885 */                   if (!expirydate.equals("never"))
/*      */                   {
/*  887 */                     if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                     {
/*  889 */                       pageContext.setAttribute("showLicenseMsg", "true");
/*  890 */                       licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense2.text", new String[] { String.valueOf(daysremaining), licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*  891 */                       if (daysremaining == 0L)
/*      */                       {
/*  893 */                         licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense1.text", new String[] { licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*  898 */                 else if (user.equals("b"))
/*      */                 {
/*  900 */                   if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                   {
/*  902 */                     pageContext.setAttribute("showLicenseMsg", "true");
/*  903 */                     licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense2.text", new String[] { String.valueOf(daysremaining) });
/*  904 */                     if (daysremaining == 0L)
/*      */                     {
/*  906 */                       licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense1.text");
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/*  912 */               String buildNo = OEMUtil.getOEMString("product.build.number");
/*      */               
/*      */ 
/*  915 */               String buildType = "&nbsp;&nbsp;";
/*  916 */               String buildInfo = FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildno.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()), OEMUtil.getOEMString("product.name") });
/*      */               
/*  918 */               if ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()))
/*      */               {
/*  920 */                 buildType = EnterpriseUtil.getServerTypeDisplayName() + buildType;
/*      */                 
/*  922 */                 buildInfo = ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()) ? buildType : "") + FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildnonoprodname.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()) });
/*      */               }
/*      */               
/*      */ 
/*  926 */               String footerMsg = "";
/*      */               
/*  928 */               String copyright = FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") });
/*  929 */               if (OEMUtil.isOEM())
/*      */               {
/*  931 */                 copyright = OEMUtil.getOEMString("company.startyear") + "-" + OEMUtil.getOEMString("company.currentyear") + " " + OEMUtil.getOEMString("company.name");
/*      */               }
/*      */               else
/*      */               {
/*  935 */                 footerMsg = "&copy " + OEMUtil.getOEMString("company.currentyear") + " " + FormatUtil.getString("am.webclient.newlogin.prodinfo.text");
/*      */               }
/*      */               
/*  938 */               ArrayList domainList = ADAuthenticationUtil.getDomainList();
/*  939 */               if ((domainList != null) && (domainList.size() > 0)) {
/*  940 */                 pageContext.setAttribute("domainList", domainList);
/*      */               }
/*      */               
/*  943 */               String errormessage = (String)request.getAttribute("errormessage");
/*  944 */               String casloginerror = request.getParameter("caserror");
/*  945 */               if (casloginerror != null) {
/*  946 */                 request.setAttribute("errormessage", "webclient.login.loginfailed.message");
/*  947 */                 pageContext.setAttribute("showErrMsg", "true");
/*  948 */                 errormessage = (String)request.getAttribute("errormessage");
/*      */               } else {
/*  950 */                 pageContext.setAttribute("showErrMsg", Boolean.toString(errormessage != null));
/*      */               }
/*      */               
/*      */ 
/*  954 */               out.write("\n<html>\n<head>\n\t<meta charset=\"utf-8\">\n\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n\t<title>");
/*  955 */               out.print(FormatUtil.getString("am.webclient.login.title.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*  956 */               out.write("</title>\n\t<!--[if lt IE 9]>\n\t    <style type='text/css'>\n\t\t\t.loginDiv{background:#ddd;}\n\t\t\tselect {color:#333;}\n\t\t\tinput[type=text],input[type=password]{line-height:25px}\n\t    </style>\n\t<![endif]-->\n\t<!--[if lt IE 10]>\n\t    <style type='text/css'>\n\t\t\tinput[type=text],input[type=password]{line-height:25px}\n\t\t\tselect {padding-left:4px !important}\n\t    </style>\n\t<![endif]-->\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/new_login.css\">\n\t");
/*  957 */               out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  958 */               out.write("\n\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/login.js\"></SCRIPT>\n\t<script src=\"/template/jquery.slides.min.js\"></script>\n\t<script type=\"text/javascript\">\n\t\tvar slidesContainer = $('div.slidesContainer');\n\t\t$(function(){\n\t\t\t$('#slides').slidesjs({\n\t\t\t\twidth: slidesContainer.width(),\n\t\t\t\theight: slidesContainer.height()*.9,\n\t\t        effect: {\n\t\t\t\t\tslide: {\n\t\t\t\t\t\tspeed: 1800\n\t\t\t\t\t}\n\t\t        },\n\t\t        pagination: {\n\t\t\t    \tactive: true,\n\t\t\t    \teffect: \"slide\"\t//NO I18N \n\t\t\t    },\n\t\t        navigation: false,\n\t\t        // start: 3,\n\t\t        play: {\n\t\t          auto: true,\n\t\t          pauseOnHover: true,\n\t\t          restartDelay: 2500\n\t\t        }\n\t\t\t});\n\t\t\t\n\t\t\t$('.slidesjs-container').height($('#slides').height());\n\t\t\t$('.slidesjs-container img').each(function() {\n\t\t\t\t$(this).height($('#slides').height()-30).width($('#slides').width());\n\t\t\t});\n\t\t\t\n\t\t\t$('div#slidesContainer').resize(function() {\n\t\t    \t$('.slidesjs-container img').each(function() {\n\t\t\t    \t$(this).height($('#slides').height()-30).width($('#slides').width());\n");
/*  959 */               out.write("\t\t\t\t});\n\t\t\t});\n\t\t});\n\t</script>\n</head>\n<body align=\"center\" onLoad=\"javascript:getImage();\" style=\"position:absolute\">\n\t<div class=\"loginerr maxwidth\" width=\"90%\">\n\t\t");
/*  960 */               out.print(FormatUtil.getString(errormessage));
/*  961 */               out.write("\n\t</div>\n\t<div class=\"loginhint maxwidth\" width=\"90%\">\n\t\t");
/*  962 */               out.print(loginmessage);
/*  963 */               out.write("\n\t</div>\n\t<div class=\"header maxwidth\" align=\"right\">\n\t<!--<div class=\"about\" align=\"right\">\n\t\t\t<img src=\"/images/question-mark.png\" width=\"28\" height=\"28\"/>\n\t\t</div>-->\n\t\t<div class=\"menu-tab-container\" id=\"loginTopLinks\">\n\t\t\t<ul id=\"menu-tab\">\n\t\t\t\t<li><a href=\"");
/*  964 */               out.print(forumsLink);
/*  965 */               out.write("\" target=\"_blank\">");
/*  966 */               out.print(FormatUtil.getString("am.webclient.newlogin.forum.text"));
/*  967 */               out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/*  968 */               out.print(blogsLink);
/*  969 */               out.write("\" target=\"_blank\">");
/*  970 */               out.print(FormatUtil.getString("am.webclient.support.blogs"));
/*  971 */               out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/*  972 */               out.print(faqlink);
/*  973 */               out.write("\" target=\"_blank\">");
/*  974 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.productinfo.link5.txt"));
/*  975 */               out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/*  976 */               out.print(tShootlink);
/*  977 */               out.write("\" target=\"_blank\">");
/*  978 */               out.print(FormatUtil.getString("am.webclient.newlogin.troubleshooting.text"));
/*  979 */               out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/*  980 */               out.print(supportLink);
/*  981 */               out.write("\" target=\"_blank\">");
/*  982 */               out.print(FormatUtil.getString("am.webclient.newlogin.support.text"));
/*  983 */               out.write("</a></li>\n\t\t\t\t<li><a href=\"/help/index.html\" target=\"_blank\">");
/*  984 */               out.print(FormatUtil.getString("am.webclient.newlogin.userguide.text"));
/*  985 */               out.write("</a></li>\n\t\t\t</ul>\n\t\t</div>\n\t</div>\n\t");
/*      */               
/*  987 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  988 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  989 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */               
/*  991 */               _jspx_th_c_005fif_005f3.setTest("${showShockieMessage == true}");
/*  992 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  993 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/*  995 */                   out.write("\n\t\t<div style=\"background-color:#DC1717; border-radius: 5px 5px 5px 5px;box-shadow: 2px 2px 2px 2px #CCCCCC;color: #FFFFFF;font-family: Arial, Helvetica, sans-serif;font-size: 14px;padding: 10px; display: inline; display: block;\" id=\"shockiemessage\"><b>");
/*  996 */                   out.print(FormatUtil.getString("am.webclient.newlogin.genuinelicense.message.text"));
/*  997 */                   out.write("</b></div>\n\t");
/*  998 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  999 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1003 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1004 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 1007 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1008 */               out.write("\n\t<div id=\"container\" class=\"maxwidth\">\n\t  \t<div class=\"slidesContainer\">\n\t  \t\t<div id=\"slides\">\n\t\t\t\t<a href=\"");
/* 1009 */               out.print(productLink);
/* 1010 */               out.write("\" target=\"_blank\"><img src=\"/images/slides/img0.png\" alt=\"Best tool for monitoring applications in physical, virtual and cloud environments\"></a>\n\t\t\t\t<a href=\"");
/* 1011 */               out.print(FormatUtil.getString("am.webclient.newlogin.img1.link"));
/* 1012 */               out.write("\" target=\"_blank\"><img src=\"/images/slides/img1.png\" alt=\"sample text\"></a>\n\t\t\t\t<a href=\"");
/* 1013 */               out.print(FormatUtil.getString("am.webclient.newlogin.img2.link"));
/* 1014 */               out.write("\" target=\"_blank\"><img src=\"/images/slides/img2.png\" alt=\"Android Native App Support\"></a>\n\t\t    </div>\n\t\t\t<div class=\"loginLogo\" style=\"display: none;\">\n\t\t\t\t<p>\n\t\t\t\t\t<b>");
/* 1015 */               out.print(OEMUtil.getOEMString("product.name"));
/* 1016 */               out.write("</b>\n\t\t\t\t\t");
/* 1017 */               out.print(FormatUtil.getString("am.webclient.newlogin.slider.text"));
/* 1018 */               out.write("\n\t\t\t\t</p>\n\t\t\t</div>\n\t  \t</div>\n\t\t<div class=\"loginContainer\" align=\"right\">\n\t\t\t<div class=\"loginDiv\" align=\"center\">\n\t\t\t\t<a href=\"");
/* 1019 */               out.print(productLink);
/* 1020 */               out.write("\"> <img src=\"");
/* 1021 */               out.print(productLogo);
/* 1022 */               out.write("\" class=\"logo\" align=\"center\" border=\"0\"></a>\n\t\t\t\t<div class=\"loginForm\" align=\"right\">\n\t\t\t");
/* 1023 */               if ((AMAutomaticPortChanger.isSsoEnabled()) && ("true".equalsIgnoreCase(System.getProperty("adminConnected")))) {
/* 1024 */                 String actionurl = "https://" + AMAutomaticPortChanger.getSSOHost() + ":" + AMAutomaticPortChanger.getSSOPort() + "/cas/login";
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1030 */                 out.write("\n        <form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"");
/* 1031 */                 out.print(actionurl);
/* 1032 */                 out.write("\" onSubmit='return validateUser();'>\n");
/*      */               } else {
/* 1034 */                 out.write("\n\t\t\t\t\t<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"/j_security_check\" onSubmit='return validateUser();' >\n\n");
/*      */               }
/* 1036 */               out.write("\n\n\t\t\t\t\t\t<input type=\"hidden\" name=\"clienttype\" value=\"html\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"webstart\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"j_username\">\n\t\t\t\t\t");
/* 1037 */               if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                 return;
/* 1039 */               out.write("\n\n\t\t\t\t    \t<input type=\"hidden\" name=\"ScreenWidth\" value=\"1280\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"ScreenHeight\" value=\"709\">\n\t\t\t\t\t\t<p class=\"float username\">\n\t\t\t\t\t\t\t");
/*      */               
/* 1041 */               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1042 */               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1043 */               _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */               
/* 1045 */               _jspx_th_c_005fif_005f5.setTest("${showLoginMsg=='true' && applicationScope.globalconfig.showgettingstarted=='true'}");
/* 1046 */               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1047 */               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                 for (;;) {
/* 1049 */                   out.write("\n\t\t\t\t\t\t\t<em id=\"firsttime-user\"><a onclick=\"showHideHint('show')\">");
/* 1050 */                   out.print(FormatUtil.getString("am.webclient.newlogin.firsttime.text"));
/* 1051 */                   out.write("</a></em>\n\t\t\t\t\t\t\t");
/* 1052 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1053 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1057 */               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1058 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */               }
/*      */               
/* 1061 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1062 */               out.write("\n\t\t\t\t\t\t\t<input width=\"80%\" type=\"text\" class=\"icon-user\" name=\"username\" placeholder=\"");
/* 1063 */               out.print(FormatUtil.getString("am.webclient.newlogin.username.text"));
/* 1064 */               out.write("\" autocomplete=\"off\" autocapitalize=\"off\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float password\">\n\t\t\t\t\t\t\t<input width=\"80%\" type=\"password\" class=\"icon-lock\" name=\"j_password\" placeholder=\"");
/* 1065 */               out.print(FormatUtil.getString("am.webclient.newlogin.password.text"));
/* 1066 */               out.write("\" autocomplete=\"off\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\">\n\t\t\t\t\t\t\t");
/*      */               
/* 1068 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1069 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1070 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */               
/* 1072 */               _jspx_th_c_005fif_005f6.setTest("${domainList != null }");
/* 1073 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1074 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/* 1076 */                   out.write("\n\t\t\t\t\t\t\t\t<select name=\"domain\" class=\"icon-domain\">\n\t\t\t\t\t\t\t\t\t<option value='");
/* 1077 */                   if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1079 */                   out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 1080 */                   out.print(FormatUtil.getString("am.webclient.loginpage.local.auth.text"));
/* 1081 */                   out.write("</option>\n\t\t\t\t\t\t\t\t\t");
/* 1082 */                   if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1084 */                   out.write("\n\t\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t    ");
/* 1085 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1086 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1090 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1091 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 1094 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1095 */               out.write("\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\">\n\t\t\t\t\t\t\t<input type=\"checkbox\" id=\"RememberMe\" name=\"remember\" onClick=\"javascript: fnRemember()\">\n\t\t\t\t\t\t\t<label for=\"RememberMe\" class=\"checkboxtext\">");
/* 1096 */               out.print(FormatUtil.getString("am.webclient.newlogin.rememberme.text"));
/* 1097 */               out.write("</label>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\"> \n\t\t\t\t\t\t\t<input type=\"submit\" class=\"login_btn\" name=\"submit\" value=\"");
/* 1098 */               out.print(FormatUtil.getString("am.webclient.login.login.text"));
/* 1099 */               out.write("\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</form>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\t\n\t</div>\n\t<div class=\"quicklinks\" align=\"center\">\n\t<div class=\"loginFeatures\">\n\t\t<div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1100 */               out.print(getInTouchLink);
/* 1101 */               out.write("\" target=\"_blank\"><img src=\"/images/mail-us.png\" title=\"");
/* 1102 */               out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.text"));
/* 1103 */               out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1104 */               out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.text"));
/* 1105 */               out.write("</h3>\n\t\t\t<p>");
/* 1106 */               out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.msg.text"));
/* 1107 */               out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1108 */               out.print(featuresLink);
/* 1109 */               out.write("\" target=\"_blank\"><img src=\"/images/features.png\" title=\"");
/* 1110 */               out.print(FormatUtil.getString("am.webclient.newlogin.features.text"));
/* 1111 */               out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1112 */               out.print(FormatUtil.getString("am.webclient.newlogin.features.text"));
/* 1113 */               out.write("</h3>\n\t\t\t<p>");
/* 1114 */               out.print(FormatUtil.getString("am.webclient.newlogin.features.msg.text"));
/* 1115 */               out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1116 */               out.print(techresourcesLink);
/* 1117 */               out.write("\" target=\"_blank\"><img src=\"/images/technical-resource.png\" title=\"");
/* 1118 */               out.print(FormatUtil.getString("am.webclient.newlogin.techresources.text"));
/* 1119 */               out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1120 */               out.print(FormatUtil.getString("am.webclient.newlogin.techresources.text"));
/* 1121 */               out.write("</h3>\n\t\t\t<p>");
/* 1122 */               out.print(FormatUtil.getString("am.webclient.newlogin.techresources.msg.text"));
/* 1123 */               out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1124 */               out.print(newfeaturesLink);
/* 1125 */               out.write("?build=");
/* 1126 */               out.print(prodVersion);
/* 1127 */               out.write("\" target=\"_blank\"><img src=\"/images/whats-new.png\" title=\"");
/* 1128 */               out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.text"));
/* 1129 */               out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1130 */               out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.text"));
/* 1131 */               out.write("</h3>\n\t\t\t<p>");
/* 1132 */               out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.msg.text"));
/* 1133 */               out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1134 */               out.print(quotesLink);
/* 1135 */               out.write("\" target=\"_blank\"><img src=\"/images/feedback.png\" title=\"");
/* 1136 */               out.print(FormatUtil.getString("am.webclient.talkback.feedback.text"));
/* 1137 */               out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1138 */               out.print(FormatUtil.getString("am.webclient.newlogin.quotes.text"));
/* 1139 */               out.write("</h3>\n\t\t\t<p>");
/* 1140 */               out.print(FormatUtil.getString("am.webclient.newlogin.quotes.msg.text"));
/* 1141 */               out.write("</p>\n\t    </div>\n\t</div>\n\t</div>\n    <div style=\"clear:both\"></div>\n\t<div class=\"loginFooter\">\n\t\t<p class=\"footertext\" align=\"center\">\n\t\t\t<img name=\"copy\" src=\"\" style=\"display:none\"/>\n\t\t\t");
/* 1142 */               out.write("\n\t\t\t");
/* 1143 */               if ((!DBUtil.hasGlobalConfigValue("loginFeatures")) || (DBUtil.getGlobalConfigValueasBoolean("loginFeatures"))) {
/* 1144 */                 out.write("\n\t\t\t<span style=\"display:inline-block;padding:5px;color:#666\">");
/* 1145 */                 out.print(buildInfo);
/* 1146 */                 out.write("</span><br/>\n\t\t\t");
/*      */               }
/* 1148 */               out.write("\n\t\t\t");
/* 1149 */               out.print(footerMsg);
/* 1150 */               out.write("\n\t\t</p>\n\t</div>\n</body>\n<script>\n\tDelete_Cookie('selectedtab');//No I18N\t\n\n\tfunction validateUser()\n\t{\t\t\n\t\tif(trimAll(document.loginForm.username.value)== '')\n\t\t{\n\t\t\talert(\"");
/* 1151 */               out.print(FormatUtil.getString("am.webclient.login.jsalertforusername.text"));
/* 1152 */               out.write("\");\n\t\t\tdocument.loginForm.username.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse if(trimAll(document.loginForm.j_password.value)== '')\n\t\t{\n\t\t\talert(\"");
/* 1153 */               out.print(FormatUtil.getString("am.webclient.login.jsalertforpassowrd.text"));
/* 1154 */               out.write("\");\n\t\t\tdocument.loginForm.j_password.focus();\n\t\t\treturn false;\n\t\t}\n\t\tvar usr = document.loginForm.username.value;\n\t\tdocument.loginForm.j_username.value=usr;\n\t\t");
/* 1155 */               if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                 return;
/* 1157 */               out.write(" \n\t\tif(document.loginForm.domain){\n\t\t\tvar domainCombo = document.loginForm.domain;\n\t\t\tvar domainSel = domainCombo.options[domainCombo.selectedIndex].value;\n\t\t\tdocument.loginForm.j_username.value = domainSel +\"\\\\\"+ usr;//No i18n\n\t\t\t");
/* 1158 */               if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                 return;
/* 1160 */               out.write("\n\t\t}\n\t}\n\n\t");
/* 1161 */               if (PluginUtil.isPlugin()) {
/* 1162 */                 out.write("\n\t$(window).one('load',function(){\t//NO I18N \n\t\tusername=$.getUrlParam('opm_user');\t//NO I18N \n\t\tif(username && window!=top){\n\t\t\tdocument.loginForm.username.value=username;\n\t\t\tdocument.loginForm.j_username.value=username;\n\t\t\tdocument.loginForm.j_password.value=username+'@opm';\t//No i18n \n\t\t\t$(\"input[name='submit']\").click();\t//NO I18N\n\t\t}\n\t});\n\t");
/*      */               }
/* 1164 */               out.write("\n\n\tfunction getImage()\n\t{\n\t\tDelete_Cookie('am_monitorsview');//No I18N\n\t\tDelete_Cookie('am_applicationsview');//No I18N\n\t\tDelete_Cookie('am_mgview');//No I18N\n\t\tGet_Cookie('selectedtab');//NO I18N\n\n\t\tif(navigator.onLine){\n\t\t\timg=new Image();\t//NO I18N \n\t\t\timg.src=\"https://www.manageengine.com/images/copyright.gif?");
/* 1165 */               out.print(url);
/* 1166 */               out.print(System.getProperty("did") != null ? "&" + System.getProperty("did") : "");
/* 1167 */               out.write("\"; //No I18N\t\n\t\t}\n\t\t// we are hiding this link as we dont have support for forget password yet\n\t\t// document.getElementById(\"loginForgot\").style.display=\"none\";\n\t\t");
/* 1168 */               if ((!"R".equals(fd.getUserType())) && (request.getParameter("product") == null))
/*      */               {
/* 1170 */                 out.write("\n\t\t\tif(navigator.onLine && img && img.height==16){\n\t\t\t\tdocument['copy'].src=img.src;\n\t\t\t}\n\t\t");
/*      */               }
/* 1172 */               if (request.getAttribute("clearcookie") != null)
/*      */               {
/* 1174 */                 username = "";
/* 1175 */                 password = "";
/*      */               }
/* 1177 */               out.write("\n\t\tfnSetDefault();\n\t}\n\t\n\t");
/* 1178 */               if (_jspx_meth_c_005fchoose_005f4(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                 return;
/* 1180 */               out.write("\n\n\tfunction checkAndRedirectToCookiesPage() {\t\t//NO I18N \n\t  if (document.all) //IE4+ specific code\n\t  {\n\t    document.cookie = \"testcookie\"\t\t//NO I18N \n\t    cookieEnabled = (document.cookie.indexOf(\"testcookie\") != -1) ? true : false;\t//NO I18N \n\t  }\n\t  else {\n\t    Set_Cookie('testcookie', '', expires); \t\t//No I18N \n\t    var tempCookie = Get_Cookie('testcookie'); \t//No I18N \n\t    if (tempCookie != null) {\n\t      cookieEnabled = true;\t\t//NO I18N \n\t    }\n\t    else {\n\t      cookieEnabled = false;\t//NO I18N \n\t    }\n\t  }\n\t  if (!cookieEnabled) {\n\t    location.href = \"/html/EnableCookies.html\";\t\t//No I18N \n\t  }\n\t}\n\n    function hideSlide() {\n\t\t$('#slides').hide();\n\t\t$('.loginLogo').show();\n    }\n    \n    function hideFeatures() {\n\t\t$('.loginFeatures').hide();\n    }\n    \n    function hideLoginTopLinks() {\n\t\t$('#loginTopLinks').hide();\n    }\n\n\t$(document).ready(function() {\t\t\t\t//NO I18N \n\t  \t//to fix the pixel differences b/w input and dropdown boxes.\n\t  \t$('.icon-domain').width($('.icon-lock').width() + 22);\t\t//NO I18N \n");
/* 1181 */               out.write("\t  \t$(document).resize(function() {\t\t\t\t\t\t\t\t//NO I18N \n\t    \t$('.icon-domain').width($('.icon-lock').width() + 20);\t//No I18N \n\t  \t});\n\t  \tif(window!=top){\n\t\t\t$('.loginhint').html('");
/* 1182 */               out.print(reloginMessage);
/* 1183 */               out.write("'); \t\t\t//NO I18N\n\t\t}\n\n\t\t");
/* 1184 */               if ((DBUtil.hasGlobalConfigValue("loginSlider")) && (!DBUtil.getGlobalConfigValueasBoolean("loginSlider"))) {
/* 1185 */                 out.write("\n  \t\t\thideSlide();\n  \t\t");
/*      */               }
/* 1187 */               out.write(10);
/* 1188 */               out.write(9);
/* 1189 */               out.write(9);
/* 1190 */               if ((DBUtil.hasGlobalConfigValue("loginFeatures")) && (!DBUtil.getGlobalConfigValueasBoolean("loginFeatures"))) {
/* 1191 */                 out.write("\n  \t\t\thideFeatures();\n  \t\t");
/*      */               }
/* 1193 */               out.write(10);
/* 1194 */               out.write(9);
/* 1195 */               out.write(9);
/* 1196 */               if ((DBUtil.hasGlobalConfigValue("loginTopLinks")) && (!DBUtil.getGlobalConfigValueasBoolean("loginTopLinks"))) {
/* 1197 */                 out.write("\n  \t\t\thideLoginTopLinks();\n  \t\t");
/*      */               }
/* 1199 */               out.write("\n\t});\n\t\t\n</script>\n</html>\n");
/* 1200 */               out.write(9);
/* 1201 */               out.write(10);
/* 1202 */               out.write(9);
/* 1203 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1204 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1208 */           if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1209 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */           }
/*      */           
/* 1212 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1213 */           out.write(10);
/* 1214 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1215 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1219 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1220 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */       }
/*      */       else
/* 1223 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */     } catch (Throwable t) {
/* 1225 */       if (!(t instanceof SkipPageException)) {
/* 1226 */         out = _jspx_out;
/* 1227 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1228 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1229 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1232 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1238 */     PageContext pageContext = _jspx_page_context;
/* 1239 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1241 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1242 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 1243 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1245 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.mobile.login.title.text");
/* 1246 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 1247 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 1248 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1249 */       return true;
/*      */     }
/* 1251 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1252 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1257 */     PageContext pageContext = _jspx_page_context;
/* 1258 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1260 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1261 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1262 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1264 */     _jspx_th_c_005fout_005f0.setValue("${actionUrl}");
/* 1265 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1266 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1267 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1268 */       return true;
/*      */     }
/* 1270 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1271 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1276 */     PageContext pageContext = _jspx_page_context;
/* 1277 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1279 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1280 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 1281 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1283 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.login.jsalertforusername.text");
/* 1284 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 1285 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 1286 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 1287 */       return true;
/*      */     }
/* 1289 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 1290 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1295 */     PageContext pageContext = _jspx_page_context;
/* 1296 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1298 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1299 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 1300 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1302 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.login.jsalertforpassowrd.text");
/* 1303 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 1304 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 1305 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 1306 */       return true;
/*      */     }
/* 1308 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 1309 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1314 */     PageContext pageContext = _jspx_page_context;
/* 1315 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1317 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1318 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1319 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1321 */     _jspx_th_c_005fif_005f0.setTest("${ssoEnabled}");
/* 1322 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1323 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1325 */         out.write("\n\t    <input type=\"hidden\" name=\"auto\" value=\"true\"/>\n\t    <input type=\"hidden\" name=\"service\" value=\"");
/* 1326 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 1327 */           return true;
/* 1328 */         out.write("\" />\n\t    <input type=\"hidden\" name=\"password\" id=\"password\" value=\"admin1\"/>\n\t");
/* 1329 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1330 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1334 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1335 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1336 */       return true;
/*      */     }
/* 1338 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1344 */     PageContext pageContext = _jspx_page_context;
/* 1345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1347 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1348 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1349 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 1351 */     _jspx_th_c_005fout_005f1.setValue("${serviceurl}");
/* 1352 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1353 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1354 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1355 */       return true;
/*      */     }
/* 1357 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1363 */     PageContext pageContext = _jspx_page_context;
/* 1364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1366 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1367 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1368 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 1369 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1370 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1372 */         out.write("\n\t\t<tr height=\"20\">\n\t\t\t<td>&nbsp;</td>\n\t\t</tr>\n\t");
/* 1373 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1374 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1378 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1379 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1380 */       return true;
/*      */     }
/* 1382 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1388 */     PageContext pageContext = _jspx_page_context;
/* 1389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1391 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1392 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 1393 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1395 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.mobile.username.txt");
/* 1396 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 1397 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 1398 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 1399 */       return true;
/*      */     }
/* 1401 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 1402 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1407 */     PageContext pageContext = _jspx_page_context;
/* 1408 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1410 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1411 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 1412 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1414 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.mobile.password.txt");
/* 1415 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 1416 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 1417 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 1418 */       return true;
/*      */     }
/* 1420 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 1421 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1426 */     PageContext pageContext = _jspx_page_context;
/* 1427 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1429 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1430 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1431 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1433 */     _jspx_th_c_005fif_005f1.setTest("${domainList != null }");
/* 1434 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1435 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 1437 */         out.write("\n\t\t<select name=\"domain\" style=\"width:210px\" >\n\t\t\t<option value='");
/* 1438 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1439 */           return true;
/* 1440 */         out.write(39);
/* 1441 */         out.write(62);
/* 1442 */         if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1443 */           return true;
/* 1444 */         out.write(" </option>\n\t\t\t<option value='");
/* 1445 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1446 */           return true;
/* 1447 */         out.write(39);
/* 1448 */         out.write(62);
/* 1449 */         if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1450 */           return true;
/* 1451 */         out.write(" </option>\n\t\t\t");
/* 1452 */         if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1453 */           return true;
/* 1454 */         out.write("\n\t\t</select>\n\t\t");
/* 1455 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1456 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1460 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1461 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1462 */       return true;
/*      */     }
/* 1464 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1465 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1470 */     PageContext pageContext = _jspx_page_context;
/* 1471 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1473 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1474 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1475 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1477 */     _jspx_th_c_005fout_005f2.setValue("select");
/* 1478 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1479 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1480 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1481 */       return true;
/*      */     }
/* 1483 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1484 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1489 */     PageContext pageContext = _jspx_page_context;
/* 1490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1492 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1493 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 1494 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1496 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.admintab.adduser.importad.select.domain");
/* 1497 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 1498 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 1499 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 1500 */       return true;
/*      */     }
/* 1502 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 1503 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1508 */     PageContext pageContext = _jspx_page_context;
/* 1509 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1511 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1512 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1513 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1515 */     _jspx_th_c_005fout_005f3.setValue("local");
/* 1516 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1517 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1518 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1519 */       return true;
/*      */     }
/* 1521 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1522 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1527 */     PageContext pageContext = _jspx_page_context;
/* 1528 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1530 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1531 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 1532 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1534 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.loginpage.local.auth.text");
/* 1535 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 1536 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 1537 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 1538 */       return true;
/*      */     }
/* 1540 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 1541 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1546 */     PageContext pageContext = _jspx_page_context;
/* 1547 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1549 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1550 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1551 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1553 */     _jspx_th_c_005fforEach_005f0.setVar("eachDomain");
/*      */     
/* 1555 */     _jspx_th_c_005fforEach_005f0.setItems("${domainList}");
/*      */     
/* 1557 */     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 1558 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1560 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1561 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1563 */           out.write("\t\t\t\t\t\t\t\t\t  \n\t\t\t\t<option value='");
/* 1564 */           boolean bool; if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1565 */             return true;
/* 1566 */           out.write(39);
/* 1567 */           out.write(62);
/* 1568 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1569 */             return true;
/* 1570 */           out.write("</option>\n\t\t\t");
/* 1571 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1572 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1576 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1577 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1580 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f0; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 1581 */         out = _jspx_page_context.popBody(); }
/* 1582 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1584 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1585 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1587 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1592 */     PageContext pageContext = _jspx_page_context;
/* 1593 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1595 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1596 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1597 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1599 */     _jspx_th_c_005fout_005f4.setValue("${eachDomain.value}");
/* 1600 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1601 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1602 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1603 */       return true;
/*      */     }
/* 1605 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1606 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1611 */     PageContext pageContext = _jspx_page_context;
/* 1612 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1614 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1615 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1616 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1618 */     _jspx_th_c_005fout_005f5.setValue("${eachDomain.label}");
/* 1619 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1620 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1621 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1622 */       return true;
/*      */     }
/* 1624 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1625 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1630 */     PageContext pageContext = _jspx_page_context;
/* 1631 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1633 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1634 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 1635 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1637 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.mobile.rememberme.txt");
/* 1638 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 1639 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 1640 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 1641 */       return true;
/*      */     }
/* 1643 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 1644 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1649 */     PageContext pageContext = _jspx_page_context;
/* 1650 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1652 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1653 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 1654 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1656 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.webclient.login.login.text");
/* 1657 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 1658 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 1659 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 1660 */       return true;
/*      */     }
/* 1662 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 1663 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fsetLocale_005f0(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1668 */     PageContext pageContext = _jspx_page_context;
/* 1669 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1671 */     SetLocaleTag _jspx_th_fmt_005fsetLocale_005f0 = (SetLocaleTag)this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.get(SetLocaleTag.class);
/* 1672 */     _jspx_th_fmt_005fsetLocale_005f0.setPageContext(_jspx_page_context);
/* 1673 */     _jspx_th_fmt_005fsetLocale_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1675 */     _jspx_th_fmt_005fsetLocale_005f0.setValue("${locale}");
/*      */     
/* 1677 */     _jspx_th_fmt_005fsetLocale_005f0.setScope("application");
/* 1678 */     int _jspx_eval_fmt_005fsetLocale_005f0 = _jspx_th_fmt_005fsetLocale_005f0.doStartTag();
/* 1679 */     if (_jspx_th_fmt_005fsetLocale_005f0.doEndTag() == 5) {
/* 1680 */       this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f0);
/* 1681 */       return true;
/*      */     }
/* 1683 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f0);
/* 1684 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1689 */     PageContext pageContext = _jspx_page_context;
/* 1690 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1692 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1693 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 1694 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1696 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.newlogout.text");
/* 1697 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 1698 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 1699 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 1700 */       return true;
/*      */     }
/* 1702 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 1703 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1708 */     PageContext pageContext = _jspx_page_context;
/* 1709 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1711 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1712 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1713 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 1714 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1715 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1717 */         out.write("\n\t\t\t");
/* 1718 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 1719 */           return true;
/* 1720 */         out.write("\n\t\t    ");
/* 1721 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 1722 */           return true;
/* 1723 */         out.write(10);
/* 1724 */         out.write(9);
/* 1725 */         out.write(9);
/* 1726 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1727 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1731 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1732 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1733 */       return true;
/*      */     }
/* 1735 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1736 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1741 */     PageContext pageContext = _jspx_page_context;
/* 1742 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1744 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1745 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1746 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1748 */     _jspx_th_c_005fwhen_005f3.setTest("${applicationScope.globalconfig.showgettingstarted=='true'}");
/* 1749 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1750 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 1752 */         out.write("\n\t\t\t\t");
/* 1753 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 1754 */           return true;
/* 1755 */         out.write("\t    \t\n\t\t    ");
/* 1756 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1757 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1761 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1762 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1763 */       return true;
/*      */     }
/* 1765 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1771 */     PageContext pageContext = _jspx_page_context;
/* 1772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1774 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 1775 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 1776 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 1778 */     _jspx_th_c_005fset_005f0.setVar("redirectUrl");
/*      */     
/* 1780 */     _jspx_th_c_005fset_005f0.setValue("/index.do");
/* 1781 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 1782 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 1783 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 1784 */       return true;
/*      */     }
/* 1786 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 1787 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1792 */     PageContext pageContext = _jspx_page_context;
/* 1793 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1795 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1796 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1797 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 1798 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1799 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1801 */         out.write("\n\t\t    \t");
/* 1802 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 1803 */           return true;
/* 1804 */         out.write("\n\t\t    ");
/* 1805 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1806 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1810 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1811 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1812 */       return true;
/*      */     }
/* 1814 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1815 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1820 */     PageContext pageContext = _jspx_page_context;
/* 1821 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1823 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 1824 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 1825 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1827 */     _jspx_th_c_005fset_005f1.setVar("redirectUrl");
/*      */     
/* 1829 */     _jspx_th_c_005fset_005f1.setValue("/MyPage.do?method=viewDashBoard&toredirect=true");
/* 1830 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 1831 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 1832 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 1833 */       return true;
/*      */     }
/* 1835 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 1836 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1841 */     PageContext pageContext = _jspx_page_context;
/* 1842 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1844 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1845 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1846 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1848 */     _jspx_th_c_005fout_005f6.setValue("local");
/* 1849 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1850 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1851 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1852 */       return true;
/*      */     }
/* 1854 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1855 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1860 */     PageContext pageContext = _jspx_page_context;
/* 1861 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1863 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1864 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1865 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1867 */     _jspx_th_c_005fforEach_005f1.setVar("eachDomain");
/*      */     
/* 1869 */     _jspx_th_c_005fforEach_005f1.setItems("${domainList}");
/*      */     
/* 1871 */     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 1872 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1874 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1875 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1877 */           out.write("\t\t\t\t\t\t\t\t\t  \n\t\t\t\t\t\t\t\t<option value='");
/* 1878 */           boolean bool; if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1879 */             return true;
/* 1880 */           out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 1881 */           if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1882 */             return true;
/* 1883 */           out.write("</option>\n\t\t\t\t\t\t\t");
/* 1884 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1885 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1889 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1890 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1893 */         int tmp241_240 = 0; int[] tmp241_238 = _jspx_push_body_count_c_005fforEach_005f1; int tmp243_242 = tmp241_238[tmp241_240];tmp241_238[tmp241_240] = (tmp243_242 - 1); if (tmp243_242 <= 0) break;
/* 1894 */         out = _jspx_page_context.popBody(); }
/* 1895 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1897 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1898 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1900 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1905 */     PageContext pageContext = _jspx_page_context;
/* 1906 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1908 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1909 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1910 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1912 */     _jspx_th_c_005fout_005f7.setValue("${eachDomain.value}");
/* 1913 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1914 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1915 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1916 */       return true;
/*      */     }
/* 1918 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1919 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1924 */     PageContext pageContext = _jspx_page_context;
/* 1925 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1927 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1928 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1929 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1931 */     _jspx_th_c_005fout_005f8.setValue("${eachDomain.label}");
/* 1932 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1933 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1934 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1935 */       return true;
/*      */     }
/* 1937 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1943 */     PageContext pageContext = _jspx_page_context;
/* 1944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1946 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1947 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1948 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1950 */     _jspx_th_c_005fout_005f9.setValue("${redirectUrl}");
/* 1951 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1952 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1953 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1954 */       return true;
/*      */     }
/* 1956 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1957 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1962 */     PageContext pageContext = _jspx_page_context;
/* 1963 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1965 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1966 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1967 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 1968 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1969 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 1971 */         out.write("\n\t\t\t\t");
/* 1972 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 1973 */           return true;
/* 1974 */         out.write("\n\t\t\t\t");
/* 1975 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 1976 */           return true;
/* 1977 */         out.write("\n\t\t\t");
/* 1978 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1979 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1983 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1984 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1985 */       return true;
/*      */     }
/* 1987 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1988 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1993 */     PageContext pageContext = _jspx_page_context;
/* 1994 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1996 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1997 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1998 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 2000 */     _jspx_th_c_005fwhen_005f4.setTest("${showErrMsg == 'true'}");
/* 2001 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2002 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 2004 */         out.write("\n\t\t\t\t\tcheckAndRedirectToCookiesPage();\n\t\t\t\t\tshowLoginErr();\n\t\t\t\t");
/* 2005 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2006 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2010 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2011 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2012 */       return true;
/*      */     }
/* 2014 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2020 */     PageContext pageContext = _jspx_page_context;
/* 2021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2023 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2024 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2025 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 2026 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2027 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 2029 */         out.write("\n\t\t\t\t\t$('div.loginerr').hide();\n\t\t\t\t");
/* 2030 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2031 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2035 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2036 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2037 */       return true;
/*      */     }
/* 2039 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fsetLocale_005f1(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2045 */     PageContext pageContext = _jspx_page_context;
/* 2046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2048 */     SetLocaleTag _jspx_th_fmt_005fsetLocale_005f1 = (SetLocaleTag)this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.get(SetLocaleTag.class);
/* 2049 */     _jspx_th_fmt_005fsetLocale_005f1.setPageContext(_jspx_page_context);
/* 2050 */     _jspx_th_fmt_005fsetLocale_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2052 */     _jspx_th_fmt_005fsetLocale_005f1.setValue("${locale}");
/*      */     
/* 2054 */     _jspx_th_fmt_005fsetLocale_005f1.setScope("application");
/* 2055 */     int _jspx_eval_fmt_005fsetLocale_005f1 = _jspx_th_fmt_005fsetLocale_005f1.doStartTag();
/* 2056 */     if (_jspx_th_fmt_005fsetLocale_005f1.doEndTag() == 5) {
/* 2057 */       this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f1);
/* 2058 */       return true;
/*      */     }
/* 2060 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f1);
/* 2061 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2066 */     PageContext pageContext = _jspx_page_context;
/* 2067 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2069 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2070 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2071 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2073 */     _jspx_th_c_005fif_005f4.setTest("${ssoEnabled}");
/* 2074 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2075 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 2077 */         out.write("\n                                                        <input type=\"hidden\" name=\"auto\" value=\"true\"/>\n                                                        <input type=\"hidden\" name=\"service\" value=\"");
/* 2078 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 2079 */           return true;
/* 2080 */         out.write("\" />\n                                                        <input type=\"hidden\" name=\"password\" id=\"password\" value=\"admin123\"/>\n                                         ");
/* 2081 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2082 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2086 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2087 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2088 */       return true;
/*      */     }
/* 2090 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2091 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2096 */     PageContext pageContext = _jspx_page_context;
/* 2097 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2099 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2100 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 2101 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 2103 */     _jspx_th_c_005fout_005f10.setValue("${serviceurl}");
/* 2104 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 2105 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 2106 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2107 */       return true;
/*      */     }
/* 2109 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2110 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2115 */     PageContext pageContext = _jspx_page_context;
/* 2116 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2118 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2119 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 2120 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2122 */     _jspx_th_c_005fout_005f11.setValue("local");
/* 2123 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 2124 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 2125 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2126 */       return true;
/*      */     }
/* 2128 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2129 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2134 */     PageContext pageContext = _jspx_page_context;
/* 2135 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2137 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2138 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 2139 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2141 */     _jspx_th_c_005fforEach_005f2.setVar("eachDomain");
/*      */     
/* 2143 */     _jspx_th_c_005fforEach_005f2.setItems("${domainList}");
/*      */     
/* 2145 */     _jspx_th_c_005fforEach_005f2.setVarStatus("status");
/* 2146 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 2148 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 2149 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 2151 */           out.write("\t\t\t\t\t\t\t\t\t  \n\t\t\t\t\t\t\t\t\t\t<option value='");
/* 2152 */           boolean bool; if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 2153 */             return true;
/* 2154 */           out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 2155 */           if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 2156 */             return true;
/* 2157 */           out.write("</option>\n\t\t\t\t\t\t\t\t\t");
/* 2158 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 2159 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2163 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 2164 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2167 */         int tmp241_240 = 0; int[] tmp241_238 = _jspx_push_body_count_c_005fforEach_005f2; int tmp243_242 = tmp241_238[tmp241_240];tmp241_238[tmp241_240] = (tmp243_242 - 1); if (tmp243_242 <= 0) break;
/* 2168 */         out = _jspx_page_context.popBody(); }
/* 2169 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 2171 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 2172 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 2174 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 2179 */     PageContext pageContext = _jspx_page_context;
/* 2180 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2182 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2183 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2184 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 2186 */     _jspx_th_c_005fout_005f12.setValue("${eachDomain.value}");
/* 2187 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2188 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2189 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2190 */       return true;
/*      */     }
/* 2192 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2193 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 2198 */     PageContext pageContext = _jspx_page_context;
/* 2199 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2201 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2202 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2203 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 2205 */     _jspx_th_c_005fout_005f13.setValue("${eachDomain.label}");
/* 2206 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2207 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2208 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2209 */       return true;
/*      */     }
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2212 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2217 */     PageContext pageContext = _jspx_page_context;
/* 2218 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2220 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2221 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2222 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2224 */     _jspx_th_c_005fif_005f7.setTest("${ssoEnabled}");
/* 2225 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2226 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 2228 */         out.write("\n                        document.loginForm.password.value=document.loginForm.j_password.value;\n                                \n                        ");
/* 2229 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2230 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2234 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2235 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2236 */       return true;
/*      */     }
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2239 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2244 */     PageContext pageContext = _jspx_page_context;
/* 2245 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2247 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2248 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2249 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2251 */     _jspx_th_c_005fif_005f8.setTest("${ssoEnabled}");
/* 2252 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2253 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 2255 */         out.write("     \n                        document.loginForm.username.value=domainSel +\"\\\\\"+ usr;\n                        ");
/* 2256 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2257 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2261 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2262 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2263 */       return true;
/*      */     }
/* 2265 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2266 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2271 */     PageContext pageContext = _jspx_page_context;
/* 2272 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2274 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2275 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2276 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/* 2277 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2278 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 2280 */         out.write(10);
/* 2281 */         out.write(9);
/* 2282 */         out.write(9);
/* 2283 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2284 */           return true;
/* 2285 */         out.write(10);
/* 2286 */         out.write(9);
/* 2287 */         out.write(9);
/* 2288 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2289 */           return true;
/* 2290 */         out.write(10);
/* 2291 */         out.write(9);
/* 2292 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2293 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2297 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2298 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2299 */       return true;
/*      */     }
/* 2301 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2307 */     PageContext pageContext = _jspx_page_context;
/* 2308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2310 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2311 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2312 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 2314 */     _jspx_th_c_005fwhen_005f5.setTest("${showErrMsg == 'true'}");
/* 2315 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2316 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 2318 */         out.write("\n\t\t\tshowLoginErr();\t//NO I18N \n\t\t");
/* 2319 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2320 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2324 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2325 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2326 */       return true;
/*      */     }
/* 2328 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2334 */     PageContext pageContext = _jspx_page_context;
/* 2335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2337 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2338 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2339 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 2340 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2341 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 2343 */         out.write("\n\t\t\tcheckAndRedirectToCookiesPage();\t//NO I18N \n\t\t\t$('div.loginerr').hide();\t\t\t//NO I18N \n\t\t");
/* 2344 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2345 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2349 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2350 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2351 */       return true;
/*      */     }
/* 2353 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2354 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Login_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */