/*      */ package org.apache.jsp.jsp.formpages;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.SupportZipUtil;
/*      */ import com.adventnet.appmanager.util.UserSessionHandler;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import com.manageengine.appmanager.util.ADAuthenticationUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLDecoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import javax.el.ExpressionFactory;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspApplicationContext;
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
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
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
/*      */ public final class Error_jsp extends HttpJspBase implements JspSourceDependent
/*      */ {
/*   63 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   69 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/*   70 */   static { _jspx_dependants.put("/jsp/Login.jsp", Long.valueOf(1473429417000L));
/*   71 */     _jspx_dependants.put("/jsp/AMLogin.jsp", Long.valueOf(1473429417000L));
/*   72 */     _jspx_dependants.put("/webclient/mobile/jsp/MobileLogin.jsp", Long.valueOf(1473429417000L));
/*   73 */     _jspx_dependants.put("/jsp/PreLogin.jsp", Long.valueOf(1473429417000L));
/*   74 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
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
/*   91 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   95 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  100 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  101 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  102 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  103 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  104 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  105 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  109 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  110 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  111 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  112 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  113 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  114 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  115 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  116 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.release();
/*  117 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  124 */     HttpSession session = null;
/*      */     
/*      */ 
/*  127 */     JspWriter out = null;
/*  128 */     Object page = this;
/*  129 */     JspWriter _jspx_out = null;
/*  130 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  134 */       response.setContentType("text/html;charset=UTF-8");
/*  135 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  137 */       _jspx_page_context = pageContext;
/*  138 */       ServletContext application = pageContext.getServletContext();
/*  139 */       ServletConfig config = pageContext.getServletConfig();
/*  140 */       session = pageContext.getSession();
/*  141 */       out = pageContext.getOut();
/*  142 */       _jspx_out = out;
/*      */       
/*  144 */       out.write("<!DOCTYPE html>\n");
/*  145 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  146 */       out.write(10);
/*  147 */       out.write(10);
/*  148 */       out.write(10);
/*      */       
/*      */ 
/*  151 */       if (request.getParameter("id") != null) {
/*  152 */         System.out.println("In IF ");
/*  153 */         int hostID = 0;
/*  154 */         String hostmode = null;
/*  155 */         int id = Integer.parseInt(request.getParameter("id"));
/*  156 */         String hostquery = "Select MODE  from AM_SCRIPTHOSTDETAILS where ID=" + id;
/*  157 */         ResultSet rs = null;
/*      */         try
/*      */         {
/*  160 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*  161 */           rs = AMConnectionPool.executeQueryStmt(hostquery);
/*  162 */           if (rs.next())
/*      */           {
/*  164 */             hostmode = rs.getString("MODE");
/*  165 */             if (hostmode.equals("WMI")) {
/*  166 */               hostID = id;
/*      */             }
/*      */           }
/*      */         } catch (Exception e) {
/*  170 */           e.printStackTrace();
/*      */         }
/*      */         finally {
/*  173 */           if (rs != null) {
/*  174 */             rs.close();
/*      */           }
/*      */         }
/*  177 */         out.println(hostID);
/*      */       }
/*      */       else
/*      */       {
/*  181 */         System.out.println("Login failed att");
/*  182 */         String errorMessage = FormatUtil.getString("am.webclient.login.err.invalidunamepwd.text");
/*  183 */         int noOfFailure = 0;
/*      */         
/*  185 */         UserSessionHandler ush1 = UserSessionHandler.getInstance();
/*  186 */         boolean state = ush1.getAccLockEnabledStatus();
/*  187 */         System.out.println("Error state" + state);
/*  188 */         if (state)
/*      */         {
/*  190 */           int count = 0;
/*  191 */           count = ush1.getAccLockCountValue();
/*  192 */           System.out.println("Error count" + count);
/*  193 */           errorMessage = errorMessage + FormatUtil.getString("am.webclient.login.err.accountdisabled.consecutive.entries.text", new String[] { String.valueOf(count) });
/*  194 */           System.out.println("Error Message" + errorMessage);
/*      */         }
/*  196 */         ActionError actErr = new ActionError("webclient.login.loginfailed.message");
/*      */         
/*      */ 
/*      */         try
/*      */         {
/*  201 */           String val = request.getParameter("j_username");
/*  202 */           String[] userNameArray = val.split("\\\\", 2);
/*      */           
/*  204 */           if (userNameArray.length > 1)
/*      */           {
/*  206 */             val = userNameArray[1];
/*      */           }
/*      */           else {
/*  209 */             val = userNameArray[0];
/*      */           }
/*  211 */           if ((val != null) && (state))
/*      */           {
/*  213 */             UserSessionHandler ush = UserSessionHandler.getInstance();
/*  214 */             ManagedApplication ma = new ManagedApplication();
/*  215 */             String uid = ma.getUserID(request);
/*      */             
/*  217 */             if (ush.getUnlockTimeArrived(uid)) noOfFailure = 0; else {
/*  218 */               noOfFailure = ush.updateLoginFailure(uid);
/*      */             }
/*      */             
/*  221 */             if (noOfFailure > ush.getMaxLoginFailCount())
/*      */             {
/*  223 */               int udcnt = ush.lockAccount(uid);
/*  224 */               System.out.println("UPDATE CNT " + udcnt);
/*  225 */               actErr = new ActionError("am.webclient.login.err.accountlockexceed.text");
/*  226 */               errorMessage = FormatUtil.getString("am.webclient.login.err.failcountexceed.accountlocked.text");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  234 */           System.out.println("Ex:  " + ex);
/*      */         }
/*      */         
/*      */ 
/*  238 */         out.write(10);
/*  239 */         out.write(10);
/*      */         
/*  241 */         ActionErrors errors = new ActionErrors();
/*  242 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", actErr);
/*  243 */         request.setAttribute("org.apache.struts.action.ERROR", errors);
/*  244 */         System.out.println("Setting the error Message" + request.getAttribute("org.apache.struts.action.ERROR"));
/*  245 */         request.setAttribute("clearcookie", "true");
/*  246 */         request.setAttribute("errormessage", errorMessage);
/*      */         
/*  248 */         out.write(10);
/*  249 */         out.write(10);
/*  250 */         out.write(10);
/*      */         
/*  252 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  253 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  254 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/*  255 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  256 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */           for (;;) {
/*  258 */             out.write("  \n\t");
/*      */             
/*  260 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  261 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  262 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */             
/*  264 */             _jspx_th_c_005fwhen_005f0.setTest("${!empty mobile && mobile=='true'}");
/*  265 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  266 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */               for (;;) {
/*  268 */                 out.write(10);
/*  269 */                 out.write(9);
/*  270 */                 out.write(9);
/*  271 */                 out.write("<!DOCTYPE html>\n<!--  Do not move down the above DOCTYPE definition(let that be first line) -->\n");
/*  272 */                 out.write("\n\n<!-- Applications Manager Login Screen -->\n");
/*  273 */                 out.write("\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\" bgcolor=\"ccc\">\n\n\n\n\n\n\n\n<head>\n<title>");
/*  274 */                 if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  276 */                 out.write("</title>\n<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0\">\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />\n<link href=\"/images/mobile/mobile.css\" rel=\"stylesheet\" type=\"text/css\" />\n<link rel=\"apple-touch-icon-precomposed\" href=\"/images/mobile/me_apm_icon.png\"/>\n<link rel=\"apple-touch-startup-image\" href=\"/images/mobile/apm_startup.png\"/>\n</head>\n\n");
/*      */                 
/*  278 */                 String mobile = request.getSession().getAttribute("mobile") == null ? "true" : (String)request.getSession().getAttribute("mobile");
/*  279 */                 if (mobile.equals("true"))
/*      */                 {
/*  281 */                   request.getSession().setAttribute("mobile", mobile);
/*      */                 }
/*  283 */                 ArrayList domainList = ADAuthenticationUtil.getDomainList();
/*  284 */                 if ((domainList != null) && (domainList.size() > 0)) {
/*  285 */                   pageContext.setAttribute("domainList", domainList);
/*      */                 }
/*      */                 
/*  288 */                 boolean ssoEnabled = AMAutomaticPortChanger.isSsoEnabled();
/*  289 */                 pageContext.setAttribute("ssoEnabled", String.valueOf(ssoEnabled));
/*  290 */                 String actionUrl = "/j_security_check";
/*  291 */                 if (ssoEnabled) {
/*  292 */                   if ("true".equalsIgnoreCase(System.getProperty("adminConnected"))) {
/*  293 */                     actionUrl = "/cas/login";
/*  294 */                     actionUrl = EnterpriseUtil.isManagedServer() ? "https://" + EnterpriseUtil.getAdminServerHost() + ":" + EnterpriseUtil.getAdminServerPort() + actionUrl : actionUrl;
/*      */                   }
/*  296 */                   if (request.getParameter("ticket") != null) {
/*  297 */                     String serviceurl = request.getParameter("casredirecturl");
/*  298 */                     pageContext.setAttribute("serviceurl", serviceurl);
/*  299 */                     response.sendRedirect(URLDecoder.decode(serviceurl)); return;
/*      */                   }
/*      */                   
/*  302 */                   if (request.getParameter("caserror") != null) {
/*  303 */                     request.setAttribute("errormessage", "webclient.login.loginfailed.message");
/*  304 */                     String errormessage = (String)request.getAttribute("errormessage");
/*  305 */                     pageContext.setAttribute("errormessage", errormessage);
/*      */                   }
/*      */                 }
/*  308 */                 pageContext.setAttribute("actionUrl", actionUrl);
/*      */                 
/*  310 */                 out.write("\n<body onLoad=\"javascript:fnSetDefault();\" style=\"background:#264a7a url(/images/mobile/bg.png) repeat-x scroll left bottom;\">\n<div id=\"LoginDiv\" class=\"indexBG\">\n<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"");
/*  311 */                 if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  313 */                 out.write("\" onSubmit='return validateUser(\"");
/*  314 */                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  316 */                 out.write(34);
/*  317 */                 out.write(44);
/*  318 */                 out.write(34);
/*  319 */                 if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  321 */                 out.write("\");'>\n\t<input type=\"hidden\" name=\"j_username\" value=\"admin2\"/>\n\t");
/*  322 */                 if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  324 */                 out.write("\n\n<div id=\"indexLogo\" align=\"center\"><img src=\"/images/mobile/spacer.gif\" width=\"205\" height=\"69\" class=\"logoBGlarge\"/></div>\n");
/*  325 */                 out.write("\n\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" class=\"loginTab\" style=\"height: 285px;\">\n  ");
/*      */                 
/*  327 */                 ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  328 */                 _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  329 */                 _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*  330 */                 int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  331 */                 if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                   for (;;) {
/*  333 */                     out.write(10);
/*  334 */                     out.write(9);
/*      */                     
/*  336 */                     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  337 */                     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  338 */                     _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                     
/*  340 */                     _jspx_th_c_005fwhen_005f1.setTest("${errormessage!=null}");
/*  341 */                     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  342 */                     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                       for (;;) {
/*  344 */                         out.write("\n\t\t<tr>\n\t\t\t<td height=\"20\"><span align=\"left\" valign=\"top\"\tstyle=\"position: relative; background-color: #FFCC00; color: #000000; border-radius: 5px;\"><font color=\"#000000;\"> ");
/*  345 */                         out.print(FormatUtil.getString((String)request.getAttribute("errormessage")));
/*  346 */                         out.write("</font></span></td>\n\t\t</tr>\n\t");
/*  347 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  348 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  352 */                     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  353 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                     }
/*      */                     
/*  356 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  357 */                     out.write(10);
/*  358 */                     out.write(9);
/*  359 */                     if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/*      */                       return;
/*  361 */                     out.write(10);
/*  362 */                     out.write(32);
/*  363 */                     out.write(32);
/*  364 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  365 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  369 */                 if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  370 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                 }
/*      */                 
/*  373 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  374 */                 out.write("\n  <tr>\n    <td height=\"20\" align=\"left\" valign=\"middle\" style=\"padding-left: 2px;\">");
/*  375 */                 if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  377 */                 out.write("</td>\n  </tr>\n  <tr>\n    <td height=\"35\" align=\"left\" valign=\"top\"><input name=\"username\" type=\"text\" class=\"formTxtField\"/></td>\n  </tr>\n  <tr>\n    <td height=\"20\" align=\"left\" valign=\"middle\" style=\"padding-left: 2px;\">");
/*  378 */                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  380 */                 out.write("</td>\n  </tr>\n  <tr>\n    <td height=\"35\" align=\"left\" valign=\"top\"><input name=\"j_password\" type=\"password\" class=\"formTxtField\"/></td>\n  </tr>  \n  <tr>\n\t<td>\n\t\t");
/*  381 */                 if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  383 */                 out.write("\n\t</td>\n  </tr>\n  <tr>\n    <td height=\"15\" align=\"left\"><table><tr><td><input type=\"checkbox\" name=\"remember\" value=\"true\" class=\"formChkBox\" onchange=\"javascript: fnRemember()\"/></td><td>");
/*  384 */                 if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  386 */                 out.write("</td></tr></table> </td>\n  </tr>\n  <tr>\n    <td height=\"70\" align=\"center\" valign=\"top\"> <input class=\"loginBtn\" name=\"button\" type=\"submit\" value=\"");
/*  387 */                 if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                   return;
/*  389 */                 out.write("\" ></td>\n  </tr>\n  <tr>\n\t  <td align=\"center\" valign=\"bottom\" style=\"color:#99B1D0;\">");
/*  390 */                 out.print(FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") }));
/*  391 */                 out.write("</td>\n  </tr>\n</table>\n\n</form>\n</div>\n</body>\n\n");
/*  392 */                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  393 */                 out.write("\n<script src=\"/template/mobile.js\"></script>\n<script language=\"javascript\" SRC=\"/webclient/common/js/validation.js\" type=\"text/javascript\"></script>\n<script type=\"text/javascript\">\n\tif ('standalone' in navigator && !navigator.standalone && (/iphone|ipod|ipad/gi).test(navigator.platform) && (/Safari/i).test(navigator.appVersion)) {\n\t\tvar addToHomeConfig = {\n\t\t\t\tanimationIn:'drop',\t\t// Animation In\t\t//NO I18N\n\t\t\t\tanimationOut:'drop',\t// Animation Out\t//NO I18N\n\t\t\t\tlifespan:15000,\t\t\t// The popup lives 15 seconds\n\t\t\t\texpire:5,\t\t\t\t// The popup is shown only once every 5 minutes\n\t\t\t\ttouchIcon:true,\n\t\t\t\t//message:'This is a custom message. Your device is an <strong>%device</strong>. The action icon is `%icon`.'\n\t\t\t};\n\t\tdocument.write('<link rel=\"stylesheet\" href=\"/images/mobile/style/add2home.css\">');\n\t\tdocument.write('<script type=\"application/javascript\" src=\"/images/mobile/script/add2home.js\"><\\/script>');\n\t}\n\t$(document).ready(function() {\n\t\twindow.addEventListener(\"load\",function() {\n\t\t  setTimeout(function(){\n\t\t    // Hide the address bar!\n");
/*  394 */                 out.write("\t\t    window.scrollTo(0, 1);\n\t\t  }, 10);\n\t\t});\n\t});\n\n\t");
/*      */                 
/*      */ 
/*  397 */                 if (request.getAttribute("errormessage") == null)
/*      */                 {
/*      */ 
/*  400 */                   out.write("\t\n\t\tif(true){\n\t\t\tif (document.all) //IE4+ specific code\n\t\t\t{\n\t\t\t\tdocument.cookie=\"testcookie\"\n\t\t\t\tcookieEnabled=(document.cookie.indexOf(\"testcookie\")!=-1)? true : false\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tSet_Cookie('testcookie','',expires);//NO I18N\n\t\t\t\tvar tempCookie = Get_Cookie('testcookie');//NO I18N\n\t\t\t\tif(tempCookie !=null)\n\t\t\t\t{\n\t\t\t\t\tcookieEnabled=true;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\tcookieEnabled=false;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t\tif (!cookieEnabled)\n\t\t{\n\t\t\tlocation.href=\"/html/EnableCookies.html\";\n\t\t}\n\t");
/*      */                 }
/*  402 */                 out.write("\n</script>\n</html>\n");
/*  403 */                 out.write(9);
/*  404 */                 out.write(10);
/*  405 */                 out.write(9);
/*  406 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  407 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  411 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  412 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */             }
/*      */             
/*  415 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  416 */             out.write(10);
/*  417 */             out.write(9);
/*      */             
/*  419 */             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  420 */             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  421 */             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*      */             
/*  423 */             _jspx_th_c_005fwhen_005f2.setTest("${!empty param.showPreLogin && param.showPreLogin=='true'}");
/*  424 */             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  425 */             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */               for (;;) {
/*  427 */                 out.write(10);
/*  428 */                 out.write(9);
/*  429 */                 out.write(9);
/*  430 */                 out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */                 
/*  432 */                 String spversion = AMAutomaticPortChanger.getServicePackVersion() != null ? AMAutomaticPortChanger.getServicePackVersion() : "None";
/*  433 */                 String whatIsNewlink = OEMUtil.getOEMString("company.whatisnew.link");
/*  434 */                 String productLogo = "/images/new_login-logo.png";
/*  435 */                 String productLink = FormatUtil.getString("company.loginpage.link");
/*  436 */                 String tShootlink = "http://apm.manageengine.com/index.html";
/*  437 */                 String faqlink = "http://www.manageengine.com/products/applications_manager/applications-management-genfaq.html" + (System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/*  438 */                 pageContext.setAttribute("ISOEM", "false");
/*      */                 
/*  440 */                 if (OEMUtil.isOEM())
/*      */                 {
/*  442 */                   pageContext.setAttribute("ISOEM", "true");
/*  443 */                   tShootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  444 */                   productLogo = OEMUtil.getOEMString("am.loginpage.logo");
/*  445 */                   productLink = OEMUtil.getOEMString("company.loginpage.link");
/*  446 */                   faqlink = OEMUtil.getOEMString("company.faq.link");
/*      */                 }
/*      */                 
/*      */                 try
/*      */                 {
/*  451 */                   if ((Constants.isMsSQLDbConnectionRestart()) && (DBQueryUtil.getDBType().equals("mssql")))
/*      */                   {
/*  453 */                     ManagedApplication.refreshDBConection();
/*  454 */                     Constants.setmsSQLDbConnectionRestart(false);
/*  455 */                     System.out.println("login.jsp:setting to false after refreshing: Constants.isMsSQLDbConnectionRestart()" + Constants.isMsSQLDbConnectionRestart());
/*      */                   }
/*      */                 }
/*      */                 catch (Exception ex)
/*      */                 {
/*  460 */                   ex.printStackTrace();
/*      */                 }
/*      */                 
/*  463 */                 String locale = System.getProperty("locale");
/*  464 */                 String dbtype = System.getProperty("am.dbserver.type");
/*  465 */                 request.setAttribute("locale", locale);
/*  466 */                 if (locale.equals("vi_VN"))
/*      */                 {
/*  468 */                   Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  469 */                   request.setCharacterEncoding("UTF-8");
/*      */                 }
/*  471 */                 else if (locale.equals("fr_FR"))
/*      */                 {
/*  473 */                   Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  474 */                   request.setCharacterEncoding("UTF-8");
/*      */                 }
/*      */                 else
/*      */                 {
/*  478 */                   out.write(10);
/*  479 */                   out.write(9);
/*  480 */                   out.write(9);
/*  481 */                   if (_jspx_meth_fmt_005fsetLocale_005f0(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                     return;
/*  483 */                   out.write(10);
/*  484 */                   out.write(9);
/*      */                 }
/*      */                 
/*  487 */                 if ((OEMUtil.isOEM()) && (OEMUtil.Address == null))
/*      */                 {
/*  489 */                   Enumeration e = request.getHeaderNames();
/*  490 */                   while (e.hasMoreElements())
/*      */                   {
/*  492 */                     String headers = (String)e.nextElement();
/*  493 */                     if ((headers != null) && (headers.equalsIgnoreCase("host")))
/*      */                     {
/*  495 */                       String[] h1 = request.getHeader(headers).toString().split(":");
/*  496 */                       OEMUtil.Address = h1[0];
/*  497 */                       OEMUtil.initializeProperties();
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*      */ 
/*  503 */                 String username = "";
/*  504 */                 String password = "";
/*  505 */                 String licenseType = "null";
/*  506 */                 String prodVersion = "null";
/*  507 */                 int usersPermitted = -1;
/*  508 */                 int monPermitted = -5;
/*  509 */                 String monPermittedStr = "";
/*  510 */                 String addonsEnabled = "";
/*      */                 
/*      */                 try
/*      */                 {
/*  514 */                   FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  515 */                   licenseType = fd.getCategory();
/*  516 */                   prodVersion = OEMUtil.getOEMString("product.build.number");
/*  517 */                   usersPermitted = fd.getNumberOfUsersPermitted();
/*  518 */                   monPermitted = fd.getNumberOfMonitorsPermitted();
/*  519 */                   addonsEnabled = fd.getAddonsEnabledAsString().replaceAll(",", "_") + "&expiresOn=" + fd.getDateOfLicenseExpiry();
/*  520 */                   if (monPermitted == -1)
/*      */                   {
/*  522 */                     monPermittedStr = "unlimited";
/*      */                   }
/*      */                   else
/*      */                   {
/*  526 */                     monPermittedStr = String.valueOf(monPermitted);
/*      */                   }
/*      */                 }
/*      */                 catch (Exception exc)
/*      */                 {
/*  531 */                   exc.printStackTrace();
/*  532 */                   licenseType = "null";
/*  533 */                   prodVersion = "null";
/*  534 */                   usersPermitted = -1;
/*  535 */                   monPermittedStr = "-5";
/*      */                 }
/*      */                 
/*      */ 
/*  539 */                 FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  540 */                 request.setAttribute("showShockieMessage", Boolean.valueOf(fd.isShockieLicense()));
/*  541 */                 String user = fd.getUserType();
/*  542 */                 String expirydate = fd.getDateOfLicenseExpiry();
/*  543 */                 long evaluationdays = fd.getEvaluationDays();
/*  544 */                 String server = "local";
/*  545 */                 String url = "";
/*      */                 
/*      */                 try
/*      */                 {
/*  549 */                   server = InetAddress.getLocalHost().getHostAddress();
/*  550 */                   server = SupportZipUtil.getAddrLong(server) + "";
/*  551 */                   int length = server.length();
/*  552 */                   server = server.substring(length - 4, length);
/*      */                 }
/*      */                 catch (Exception ee)
/*      */                 {
/*  556 */                   ee.printStackTrace();
/*      */                 }
/*      */                 
/*  559 */                 if (user.equals("R"))
/*      */                 {
/*  561 */                   user = "a";
/*  562 */                   url = "si=" + server + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */                 }
/*  564 */                 else if (user.equals("T"))
/*      */                 {
/*  566 */                   user = "b";
/*  567 */                   url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */                 }
/*  569 */                 else if (user.equals("F"))
/*      */                 {
/*  571 */                   user = "c";
/*  572 */                   url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */                 }
/*      */                 
/*  575 */                 if (dbtype != null)
/*      */                 {
/*  577 */                   url = url + "&db=" + dbtype.toLowerCase();
/*      */                 }
/*      */                 
/*  580 */                 String DATE_FORMAT = "EEE, d MMMM yyyy HH:mm:ss Z";
/*  581 */                 DateFormat df = new SimpleDateFormat(DATE_FORMAT);
/*  582 */                 String generatedTime = df.format(new Date());
/*      */                 
/*  584 */                 String usertype = fd.getUserType();
/*  585 */                 long daysremaining = fd.getExpiryDate();
/*  586 */                 pageContext.setAttribute("usertype", usertype);
/*  587 */                 String loginmessage = "";
/*  588 */                 String licenseMessage = "";
/*  589 */                 String reloginMessage = FormatUtil.getString("am.webclient.plugin.reloginmessage.text");
/*  590 */                 pageContext.setAttribute("showLoginMsg", "false");
/*  591 */                 pageContext.setAttribute("showNewFeatures", "false");
/*  592 */                 pageContext.setAttribute("showLicenseMsg", "false");
/*  593 */                 if (!OEMUtil.isOEM())
/*      */                 {
/*  595 */                   if ((spversion.equalsIgnoreCase("None")) || (PluginUtil.isPlugin()))
/*      */                   {
/*  597 */                     pageContext.setAttribute("showLoginMsg", "true");
/*  598 */                     if (PluginUtil.isPlugin()) {
/*  599 */                       loginmessage = FormatUtil.getString("am.webclient.plugin.defaultusernamemessage.text");
/*      */                     } else {
/*  601 */                       loginmessage = FormatUtil.getString("am.webclient.newlogin.defaultusernamemessage.text");
/*  602 */                       reloginMessage = loginmessage;
/*      */                     }
/*  604 */                     pageContext.setAttribute("isPlugin", "" + PluginUtil.isPlugin());
/*      */                   }
/*      */                   else
/*      */                   {
/*  608 */                     pageContext.setAttribute("showNewFeatures", "true");
/*      */                   }
/*      */                   
/*  611 */                   if (user.equals("a"))
/*      */                   {
/*  613 */                     if (!expirydate.equals("never"))
/*      */                     {
/*  615 */                       if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                       {
/*  617 */                         pageContext.setAttribute("showLicenseMsg", "true");
/*  618 */                         licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense2.text", new String[] { String.valueOf(daysremaining), licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*  619 */                         if (daysremaining == 0L)
/*      */                         {
/*  621 */                           licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense1.text", new String[] { licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                   }
/*  626 */                   else if (user.equals("b"))
/*      */                   {
/*  628 */                     if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                     {
/*  630 */                       pageContext.setAttribute("showLicenseMsg", "true");
/*  631 */                       licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense2.text", new String[] { String.valueOf(daysremaining) });
/*  632 */                       if (daysremaining == 0L)
/*      */                       {
/*  634 */                         licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense1.text");
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*  640 */                 String buildNo = OEMUtil.getOEMString("product.build.number");
/*      */                 
/*      */ 
/*  643 */                 String buildType = "&nbsp;&nbsp;";
/*  644 */                 String buildInfo = FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildno.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()), OEMUtil.getOEMString("product.name") });
/*      */                 
/*  646 */                 if ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()))
/*      */                 {
/*  648 */                   buildType = EnterpriseUtil.getServerTypeDisplayName() + buildType;
/*      */                   
/*  650 */                   buildInfo = ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()) ? buildType : "") + FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildnonoprodname.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()) });
/*      */                 }
/*      */                 
/*      */ 
/*  654 */                 String footerMsg = "";
/*      */                 
/*  656 */                 String copyright = FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") });
/*  657 */                 if (OEMUtil.isOEM())
/*      */                 {
/*  659 */                   copyright = OEMUtil.getOEMString("company.startyear") + "-" + OEMUtil.getOEMString("company.currentyear") + " " + OEMUtil.getOEMString("company.name");
/*      */                 }
/*      */                 else
/*      */                 {
/*  663 */                   footerMsg = footerMsg + FormatUtil.getString("am.webclient.newlogin.prodinfo.text") + " ";
/*      */                 }
/*      */                 
/*  666 */                 ArrayList domainList = ADAuthenticationUtil.getDomainList();
/*  667 */                 if ((domainList != null) && (domainList.size() > 0)) {
/*  668 */                   pageContext.setAttribute("domainList", domainList);
/*      */                 }
/*      */                 
/*  671 */                 String errormessage = (String)request.getAttribute("errormessage");
/*  672 */                 pageContext.setAttribute("showErrMsg", Boolean.toString(errormessage != null));
/*      */                 
/*  674 */                 out.write("\n<html>\n\t<head>\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n\t\t<title>");
/*  675 */                 if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  677 */                 out.write("</title>\n\t    <link href=\"/images/prelogin.css\" rel=\"stylesheet\" type=\"text/css\">\n\t    ");
/*  678 */                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  679 */                 out.write("\n\t\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/login.js\"></SCRIPT>\n\t</head>\n\t<body onLoad=\"javascript:getImage();\">\n\t\t");
/*  680 */                 if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  682 */                 out.write("\n\t\t<div class=\"header\" style=\"display:block\" align=\"left\" width=\"98%\">\n\t\t\t<div class=\"logo\" width=\"20%\">\n\t\t\t\t<img src=\"/images/am_logo.png\"/>\n\t\t\t\t<!-- <img src=\"/images/APM-logo.png\"/> -->\n\t\t\t</div>\n\t\t\t<div class=\"preloginDiv\">\n\t\t\t\t<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"/j_security_check\" onSubmit='return validateUser();' >\n\t\t\t\t\t<input type=\"hidden\" name=\"clienttype\" value=\"html\">\n\t\t\t    \t<input type=\"hidden\" name=\"webstart\">\n\t\t\t    \t<input type=\"hidden\" name=\"j_username\">\n\t\t\t    \t<input type=\"hidden\" name=\"ScreenWidth\" value=\"1280\">\n\t\t\t    \t<input type=\"hidden\" name=\"ScreenHeight\" value=\"709\">\n\t\t\t\t\t<p class=\"inline username\">\n\t\t\t\t\t\t<input width=\"80%\" type=\"text\" class=\"icon-user\" name=\"username\" placeholder=\"");
/*  683 */                 out.print(FormatUtil.getString("am.webclient.newlogin.username.text"));
/*  684 */                 out.write("\" autocomplete=\"off\" autocapitalize=\"off\">\n\t\t\t\t\t\t<span style=\"display:none\"><em id=\"firsttime-user\"><a onclick=\"showHideHint('show')\">");
/*  685 */                 out.print(FormatUtil.getString("am.webclient.newlogin.firsttime.text"));
/*  686 */                 out.write("</a></em></span>\n\t\t\t\t\t</p>\n\t\t\t\t\t<p class=\"inline password\">\n\t\t\t\t\t\t<input width=\"80%\" type=\"password\" class=\"icon-lock\" name=\"j_password\" placeholder=\"");
/*  687 */                 out.print(FormatUtil.getString("am.webclient.newlogin.password.text"));
/*  688 */                 out.write("\" autocomplete=\"off\">\n\t\t\t\t\t\t<span style=\"display:block;padding-right: 10px;\" align=\"right\">\n\t\t\t\t\t\t\t<input type=\"checkbox\" id=\"RememberMe\" name=\"remember\" onClick=\"javascript: fnRemember()\">\n\t\t\t\t\t\t\t<!-- <label for=\"RememberMe\" class=\"checkboxtext\">Remember password</label> -->\n\t\t\t\t\t\t\t<em id=\"firsttime-user\">");
/*  689 */                 out.print(FormatUtil.getString("am.webclient.newlogin.rememberme.text"));
/*  690 */                 out.write("</em>\n\t\t\t\t\t\t</span>\n\t\t\t\t\t</p>\n\t\t\t\t\t");
/*      */                 
/*  692 */                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  693 */                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  694 */                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                 
/*  696 */                 _jspx_th_c_005fif_005f2.setTest("${domainList != null }");
/*  697 */                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  698 */                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                   for (;;) {
/*  700 */                     out.write("\n\t\t\t\t\t<p class=\"inline domain\">\n\t\t\t\t\t\t<select name=\"domain\" class=\"icon-domain\">\n\t\t\t\t\t\t\t<option value='");
/*  701 */                     if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                       return;
/*  703 */                     out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/*  704 */                     out.print(FormatUtil.getString("am.webclient.loginpage.local.auth.text"));
/*  705 */                     out.write("</option>\n\t\t\t\t\t\t\t");
/*  706 */                     if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                       return;
/*  708 */                     out.write("\n\t\t\t\t\t\t</select>\n\t\t\t\t\t</p>\n\t\t\t\t    ");
/*  709 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  710 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  714 */                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  715 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                 }
/*      */                 
/*  718 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  719 */                 out.write("\n\t\t\t\t\t<p style=\"display:inline-block;vertical-align: top;margin-top: 10px;\">\n\t\t\t\t\t\t<input type=\"submit\" class=\"login_btn\" name=\"submit\" value=\"");
/*  720 */                 out.print(FormatUtil.getString("am.webclient.login.login.text"));
/*  721 */                 out.write("\">\n\t\t\t\t\t</p>\n\t\t\t\t</form>\n\t\t\t</div>\n\t\t</div>\n\t\t<div id=\"container\" align=\"center\">\n\t\t    <div id=\"mainnav\" align=\"center\" valign=\"middle\">\n\t\t    \t<a href=\"");
/*  722 */                 if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  724 */                 out.write("\"><img src=\"/images/home.png\" class=\"home-icon\" title=\"");
/*  725 */                 out.print(FormatUtil.getString("webclient.login.logoutpage.backtologin"));
/*  726 */                 out.write("\"/></a> ");
/*  727 */                 out.print(FormatUtil.getString("am.webclient.newlogin.logout.msg.text"));
/*  728 */                 out.write("\n\t\t\t</div>\n\t\t\t<div id=\"apmOnlineContent\">\n\t\t\t\t<div id=\"onlinecontent\">\n\t\t\t\t\t<iframe name=\"onlinecontent\" src=\"");
/*  729 */                 out.print(FormatUtil.getString("am.webclient.apmlogout.link"));
/*  730 */                 out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/*  731 */                 out.write("\" width=\"100%\" height=\"470px\" frameborder=\"0\" style=\"margin-top: -5px;\"></iframe>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div id=\"footer\" width=\"100%\" align=\"center\">\n\t\t\t<p class=\"footertext\" align=\"center\">\n\t\t\t\t<img name=\"copy\" src=\"\" style=\"display:none\"/>\n\t\t\t\t&copy 2015 <a href=\"https://www.zohocorp.com");
/*  732 */                 out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/*  733 */                 out.write("\" target=\"_blank\">");
/*  734 */                 out.print(FormatUtil.getString("company.name"));
/*  735 */                 out.write("</a> ");
/*  736 */                 out.print(FormatUtil.getString("am.webclient.newlogin.allrights.text"));
/*  737 */                 out.write("\n\t\t\t</p>\n\t\t</div>\n\t\t<script type=\"text/javascript\">\n\t\t\t$(document).ready(function(){\n\t\t\t\t// if(!navigator.onLine){\n\t\t\t\t// \tlocation.href='/index.do';\n\t\t\t\t// } \n\n\t\t\t\tsetTimeout(resetIframeSize, 10);\n\t\t\t});\n\n\t\t\t$(window).resize(function(){\n\t\t\t\tsetTimeout(resetIframeSize, 10);\n\t\t\t});\n\n\t\t\tfunction resetIframeSize(){\n\t\t\t\texpectedIFrameHeight = $(document).height() - $('.header ').height() - 90;\n\t\t\t\tcurrentIframeheight = $('#onlinecontent').height();\n\t\t\t\tif(expectedIFrameHeight > currentIframeheight){\n\t\t\t\t\tconsole.log(expectedIFrameHeight);\n\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height',expectedIFrameHeight+'px');\t//NO I18N \n\t\t\t\t\t$('#footer').css({'margin-top':'inherit','bottom':'0px','position':'absolute'});\t//NO I18N \n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height','470px');\t//NO I18N \n\t\t\t\t\t$('#footer').css({'margin-top':'45px','bottom':'inherit','position':'relative'});\t//No I18N \n\t\t\t\t\t//below code in this else loop will avoid the flickering issue while decreasing the iframe size.\n\t\t\t\t\texpectedIFrameHeight = $(document).height() - $('.header ').height() - 90;\n");
/*  738 */                 out.write("\t\t\t\t\tcurrentIframeheight = $('#onlinecontent').height();\n\t\t\t\t\tif(expectedIFrameHeight > currentIframeheight){\n\t\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height',expectedIFrameHeight+'px');\t//NO I18N \n\t\t\t\t\t\t$('#footer').css({'margin-top':'inherit','bottom':'0px','position':'absolute'});\t//NO I18N \n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\n\t\t\tDelete_Cookie('selectedtab');//No I18N\t\n\n\t\t\tfunction validateUser()\n\t\t\t{\n\t\t\t\tif(trimAll(document.loginForm.username.value)== '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  739 */                 out.print(FormatUtil.getString("am.webclient.login.jsalertforusername.text"));
/*  740 */                 out.write("\");\n\t\t\t\t\tdocument.loginForm.username.focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if(trimAll(document.loginForm.j_password.value)== '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  741 */                 out.print(FormatUtil.getString("am.webclient.login.jsalertforpassowrd.text"));
/*  742 */                 out.write("\");\n\t\t\t\t\tdocument.loginForm.j_password.focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\tvar usr = document.loginForm.username.value;\n\t\t\t\tdocument.loginForm.j_username.value=usr;\n\n\t\t\t\tif(document.loginForm.domain){\n\t\t\t\t\tvar domainCombo = document.loginForm.domain;\n\t\t\t\t\tvar domainSel = domainCombo.options[domainCombo.selectedIndex].value;\n\t\t\t\t\tdocument.loginForm.j_username.value = domainSel +\"\\\\\"+ usr;//No i18n\n\t\t\t\t}\n\t\t\t}\n\n\t\t\t");
/*  743 */                 if (PluginUtil.isPlugin()) {
/*  744 */                   out.write("\n\t\t\t$(window).one('load',function(){\t//NO I18N \n\t\t\t\tusername=$.getUrlParam('opm_user');\t//NO I18N \n\t\t\t\tif(username && window!=top){\n\t\t\t\t\tdocument.loginForm.username.value=username;\n\t\t\t\t\tdocument.loginForm.j_username.value=username;\n\t\t\t\t\tdocument.loginForm.j_password.value=username+'@opm';\t//No i18n \n\t\t\t\t\t$(\"input[name='submit']\").click();\t//NO I18N\t\n\t\t\t\t}\n\t\t\t});\n\t\t\t");
/*      */                 }
/*  746 */                 out.write("\n\n\t\t\tfunction getImage()\n\t\t\t{\n\t\t\t\tDelete_Cookie('am_monitorsview');//No I18N\n\t\t\t\tDelete_Cookie('am_applicationsview');//No I18N\n\t\t\t\tDelete_Cookie('am_mgview');//No I18N\n\t\t\t\tGet_Cookie('selectedtab');//NO I18N\n\n\t\t\t\tif(navigator.onLine){\n\t\t\t\t\timg=new Image();\n\t\t\t\t\timg.src=\"http://www.manageengine.com/images/copyright.gif?");
/*  747 */                 out.print(url);
/*  748 */                 out.print(System.getProperty("did") != null ? "&" + System.getProperty("did") : "");
/*  749 */                 out.write("\"; //No I18N\t\n\t\t\t\t}\n\t\t\t\t// we are hiding this link as we dont have support for forget password yet\n\t\t\t\t// document.getElementById(\"loginForgot\").style.display=\"none\";\n\t\t\t\t");
/*  750 */                 if ((!"R".equals(fd.getUserType())) && (request.getParameter("product") == null))
/*      */                 {
/*  752 */                   out.write("\n\t\t\t\t\tif(navigator.onLine && img.height==16){\n\t\t\t\t\t\tdocument['copy'].src=img.src;\n\t\t\t\t\t}\n\t\t\t\t");
/*      */                 }
/*  754 */                 if (request.getAttribute("clearcookie") != null)
/*      */                 {
/*  756 */                   username = "";
/*  757 */                   password = "";
/*      */                 }
/*  759 */                 out.write("\n\t\t\t\tfnSetDefault();\n\t\t\t}\n\t\t\t\n\t\t\t");
/*  760 */                 if (_jspx_meth_c_005fchoose_005f3(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  762 */                 out.write("\n\n\t\t\t$(document).ready(function() {\n\t\t\t  \t//to fix the pixel differences b/w input and dropdown boxes.\n\t\t\t  \t$('.icon-domain').width($('.icon-lock').width() + 22);\n\t\t\t  \t$(document).resize(function() {\n\t\t\t    \t$('.icon-domain').width($('.icon-lock').width() + 20);\n\t\t\t  \t});\n\t\t\t  \tif(window!=top){\n\t\t\t\t\t$('.loginhint').html('");
/*  763 */                 out.print(reloginMessage);
/*  764 */                 out.write("'); \t//NO I18N\n\t\t\t\t}\n\t\t\t});\n\t\n\t\t</script>\n\t</body>\n</html>\n");
/*  765 */                 out.write(9);
/*  766 */                 out.write(10);
/*  767 */                 out.write(9);
/*  768 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  769 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  773 */             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  774 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */             }
/*      */             
/*  777 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  778 */             out.write(10);
/*  779 */             out.write(9);
/*      */             
/*  781 */             OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  782 */             _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  783 */             _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f0);
/*  784 */             int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  785 */             if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */               for (;;) {
/*  787 */                 out.write(10);
/*  788 */                 out.write(9);
/*  789 */                 out.write(9);
/*  790 */                 out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */                 
/*  792 */                 String spversion = AMAutomaticPortChanger.getServicePackVersion() != null ? AMAutomaticPortChanger.getServicePackVersion() : "None";
/*  793 */                 String whatIsNewlink = OEMUtil.getOEMString("company.whatisnew.link");
/*  794 */                 String productLogo = "/images/APM-logo.png";
/*      */                 
/*  796 */                 String forumsLink = FormatUtil.getString("company.forum.link");
/*  797 */                 String blogsLink = FormatUtil.getString("company.blogs.link");
/*  798 */                 String faqlink = FormatUtil.getString("company.faq.link");
/*  799 */                 String tShootlink = FormatUtil.getString("company.troubleshoot.link");
/*  800 */                 String supportLink = FormatUtil.getString("company.support.link");
/*  801 */                 String productLink = FormatUtil.getString("company.loginpage.link");
/*      */                 
/*  803 */                 String getInTouchLink = FormatUtil.getString("am.webclient.newlogin.getintouch.link");
/*  804 */                 String featuresLink = FormatUtil.getString("am.webclient.newlogin.features.link");
/*  805 */                 String techresourcesLink = FormatUtil.getString("am.webclient.newlogin.techresources.link");
/*  806 */                 String newfeaturesLink = FormatUtil.getString("am.webclient.newlogin.newfeatures.link");
/*  807 */                 String quotesLink = FormatUtil.getString("am.webclient.newlogin.quotes.link");
/*  808 */                 boolean ssoEnabled = AMAutomaticPortChanger.isSsoEnabled();
/*      */                 
/*      */ 
/*  811 */                 pageContext.setAttribute("ssoEnabled", String.valueOf(ssoEnabled));
/*  812 */                 String serviceurl = request.getParameter("casredirecturl");
/*  813 */                 pageContext.setAttribute("serviceurl", serviceurl);
/*  814 */                 String ticket = request.getParameter("ticket");
/*      */                 
/*      */ 
/*  817 */                 if ((ssoEnabled) && (ticket != null))
/*      */                 {
/*  819 */                   serviceurl = URLDecoder.decode(serviceurl);
/*  820 */                   response.sendRedirect(serviceurl); return;
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*  825 */                 pageContext.setAttribute("ISOEM", "false");
/*      */                 
/*  827 */                 if (OEMUtil.isOEM())
/*      */                 {
/*  829 */                   pageContext.setAttribute("ISOEM", "true");
/*  830 */                   tShootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  831 */                   productLogo = OEMUtil.getOEMString("am.loginpage.logo");
/*  832 */                   productLink = OEMUtil.getOEMString("company.loginpage.link");
/*  833 */                   faqlink = OEMUtil.getOEMString("company.faq.link");
/*      */                 }
/*      */                 
/*      */                 try
/*      */                 {
/*  838 */                   if ((Constants.isMsSQLDbConnectionRestart()) && (DBQueryUtil.getDBType().equals("mssql")))
/*      */                   {
/*  840 */                     ManagedApplication.refreshDBConection();
/*  841 */                     Constants.setmsSQLDbConnectionRestart(false);
/*  842 */                     System.out.println("login.jsp:setting to false after refreshing: Constants.isMsSQLDbConnectionRestart()" + Constants.isMsSQLDbConnectionRestart());
/*      */                   }
/*      */                 }
/*      */                 catch (Exception ex)
/*      */                 {
/*  847 */                   ex.printStackTrace();
/*      */                 }
/*      */                 
/*  850 */                 String locale = System.getProperty("locale");
/*  851 */                 String dbtype = System.getProperty("am.dbserver.type");
/*  852 */                 request.setAttribute("locale", locale);
/*  853 */                 if (locale.equals("vi_VN"))
/*      */                 {
/*  855 */                   Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  856 */                   request.setCharacterEncoding("UTF-8");
/*      */                 }
/*  858 */                 else if (locale.equals("fr_FR"))
/*      */                 {
/*  860 */                   Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  861 */                   request.setCharacterEncoding("UTF-8");
/*      */                 }
/*      */                 else
/*      */                 {
/*  865 */                   out.write(10);
/*  866 */                   out.write(9);
/*  867 */                   out.write(9);
/*  868 */                   if (_jspx_meth_fmt_005fsetLocale_005f1(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                     return;
/*  870 */                   out.write(10);
/*  871 */                   out.write(9);
/*      */                 }
/*  873 */                 out.write(10);
/*  874 */                 out.write(10);
/*  875 */                 out.write(9);
/*      */                 
/*  877 */                 if ((OEMUtil.isOEM()) && (OEMUtil.Address == null))
/*      */                 {
/*  879 */                   Enumeration e = request.getHeaderNames();
/*  880 */                   while (e.hasMoreElements())
/*      */                   {
/*  882 */                     String headers = (String)e.nextElement();
/*  883 */                     if ((headers != null) && (headers.equalsIgnoreCase("host")))
/*      */                     {
/*  885 */                       String[] h1 = request.getHeader(headers).toString().split(":");
/*  886 */                       OEMUtil.Address = h1[0];
/*  887 */                       OEMUtil.initializeProperties();
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*      */ 
/*  893 */                 String username = "";
/*  894 */                 String password = "";
/*  895 */                 String licenseType = "null";
/*  896 */                 String prodVersion = "null";
/*  897 */                 int usersPermitted = -1;
/*  898 */                 int monPermitted = -5;
/*  899 */                 String monPermittedStr = "";
/*  900 */                 String addonsEnabled = "";
/*      */                 
/*      */                 try
/*      */                 {
/*  904 */                   FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  905 */                   licenseType = fd.getCategory();
/*  906 */                   prodVersion = OEMUtil.getOEMString("product.build.number");
/*  907 */                   usersPermitted = fd.getNumberOfUsersPermitted();
/*  908 */                   monPermitted = fd.getNumberOfMonitorsPermitted();
/*  909 */                   addonsEnabled = fd.getAddonsEnabledAsString().replaceAll(",", "_") + "&expiresOn=" + fd.getDateOfLicenseExpiry();
/*  910 */                   if (monPermitted == -1)
/*      */                   {
/*  912 */                     monPermittedStr = "unlimited";
/*      */                   }
/*      */                   else
/*      */                   {
/*  916 */                     monPermittedStr = String.valueOf(monPermitted);
/*      */                   }
/*      */                 }
/*      */                 catch (Exception exc)
/*      */                 {
/*  921 */                   exc.printStackTrace();
/*  922 */                   licenseType = "null";
/*  923 */                   prodVersion = "null";
/*  924 */                   usersPermitted = -1;
/*  925 */                   monPermittedStr = "-5";
/*      */                 }
/*      */                 
/*      */ 
/*  929 */                 FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  930 */                 request.setAttribute("showShockieMessage", Boolean.valueOf(fd.isShockieLicense()));
/*  931 */                 String user = fd.getUserType();
/*  932 */                 String expirydate = fd.getDateOfLicenseExpiry();
/*  933 */                 long evaluationdays = fd.getEvaluationDays();
/*  934 */                 String server = "local";
/*  935 */                 String url = "";
/*      */                 
/*      */                 try
/*      */                 {
/*  939 */                   server = InetAddress.getLocalHost().getHostAddress();
/*  940 */                   server = SupportZipUtil.getAddrLong(server) + "";
/*  941 */                   int length = server.length();
/*  942 */                   server = server.substring(length - 4, length);
/*      */                 }
/*      */                 catch (Exception ee)
/*      */                 {
/*  946 */                   ee.printStackTrace();
/*      */                 }
/*      */                 
/*  949 */                 if (user.equals("R"))
/*      */                 {
/*  951 */                   user = "a";
/*  952 */                   url = "si=" + server + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */                 }
/*  954 */                 else if (user.equals("T"))
/*      */                 {
/*  956 */                   user = "b";
/*  957 */                   url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */                 }
/*  959 */                 else if (user.equals("F"))
/*      */                 {
/*  961 */                   user = "c";
/*  962 */                   url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */                 }
/*      */                 
/*  965 */                 if (dbtype != null)
/*      */                 {
/*  967 */                   url = url + "&db=" + dbtype.toLowerCase();
/*      */                 }
/*      */                 
/*  970 */                 String DATE_FORMAT = "EEE, d MMMM yyyy HH:mm:ss Z";
/*  971 */                 DateFormat df = new SimpleDateFormat(DATE_FORMAT);
/*  972 */                 String generatedTime = df.format(new Date());
/*      */                 
/*  974 */                 String usertype = fd.getUserType();
/*  975 */                 long daysremaining = fd.getExpiryDate();
/*  976 */                 pageContext.setAttribute("usertype", usertype);
/*  977 */                 String loginmessage = "";
/*  978 */                 String licenseMessage = "";
/*  979 */                 String reloginMessage = FormatUtil.getString("am.webclient.plugin.reloginmessage.text");
/*  980 */                 pageContext.setAttribute("showLoginMsg", "false");
/*  981 */                 pageContext.setAttribute("showNewFeatures", "false");
/*  982 */                 pageContext.setAttribute("showLicenseMsg", "false");
/*  983 */                 if (!OEMUtil.isOEM())
/*      */                 {
/*  985 */                   if ((spversion.equalsIgnoreCase("None")) || (PluginUtil.isPlugin()))
/*      */                   {
/*  987 */                     pageContext.setAttribute("showLoginMsg", "true");
/*  988 */                     if (PluginUtil.isPlugin()) {
/*  989 */                       loginmessage = FormatUtil.getString("am.webclient.plugin.defaultusernamemessage.text");
/*      */                     } else {
/*  991 */                       loginmessage = FormatUtil.getString("am.webclient.newlogin.defaultusernamemessage.text");
/*  992 */                       reloginMessage = loginmessage;
/*      */                     }
/*  994 */                     pageContext.setAttribute("isPlugin", "" + PluginUtil.isPlugin());
/*      */                   }
/*      */                   
/*  997 */                   if (user.equals("a"))
/*      */                   {
/*  999 */                     if (!expirydate.equals("never"))
/*      */                     {
/* 1001 */                       if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                       {
/* 1003 */                         pageContext.setAttribute("showLicenseMsg", "true");
/* 1004 */                         licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense2.text", new String[] { String.valueOf(daysremaining), licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/* 1005 */                         if (daysremaining == 0L)
/*      */                         {
/* 1007 */                           licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense1.text", new String[] { licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                   }
/* 1012 */                   else if (user.equals("b"))
/*      */                   {
/* 1014 */                     if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                     {
/* 1016 */                       pageContext.setAttribute("showLicenseMsg", "true");
/* 1017 */                       licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense2.text", new String[] { String.valueOf(daysremaining) });
/* 1018 */                       if (daysremaining == 0L)
/*      */                       {
/* 1020 */                         licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense1.text");
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/* 1026 */                 String buildNo = OEMUtil.getOEMString("product.build.number");
/*      */                 
/*      */ 
/* 1029 */                 String buildType = "&nbsp;&nbsp;";
/* 1030 */                 String buildInfo = FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildno.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()), OEMUtil.getOEMString("product.name") });
/*      */                 
/* 1032 */                 if ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()))
/*      */                 {
/* 1034 */                   buildType = EnterpriseUtil.getServerTypeDisplayName() + buildType;
/*      */                   
/* 1036 */                   buildInfo = ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()) ? buildType : "") + FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildnonoprodname.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()) });
/*      */                 }
/*      */                 
/*      */ 
/* 1040 */                 String footerMsg = "";
/*      */                 
/* 1042 */                 String copyright = FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") });
/* 1043 */                 if (OEMUtil.isOEM())
/*      */                 {
/* 1045 */                   copyright = OEMUtil.getOEMString("company.startyear") + "-" + OEMUtil.getOEMString("company.currentyear") + " " + OEMUtil.getOEMString("company.name");
/*      */                 }
/*      */                 else
/*      */                 {
/* 1049 */                   footerMsg = "&copy " + OEMUtil.getOEMString("company.currentyear") + " " + FormatUtil.getString("am.webclient.newlogin.prodinfo.text");
/*      */                 }
/*      */                 
/* 1052 */                 ArrayList domainList = ADAuthenticationUtil.getDomainList();
/* 1053 */                 if ((domainList != null) && (domainList.size() > 0)) {
/* 1054 */                   pageContext.setAttribute("domainList", domainList);
/*      */                 }
/*      */                 
/* 1057 */                 String errormessage = (String)request.getAttribute("errormessage");
/* 1058 */                 String casloginerror = request.getParameter("caserror");
/* 1059 */                 if (casloginerror != null) {
/* 1060 */                   request.setAttribute("errormessage", "webclient.login.loginfailed.message");
/* 1061 */                   pageContext.setAttribute("showErrMsg", "true");
/* 1062 */                   errormessage = (String)request.getAttribute("errormessage");
/*      */                 } else {
/* 1064 */                   pageContext.setAttribute("showErrMsg", Boolean.toString(errormessage != null));
/*      */                 }
/*      */                 
/*      */ 
/* 1068 */                 out.write("\n<html>\n<head>\n\t<meta charset=\"utf-8\">\n\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n\t<title>");
/* 1069 */                 out.print(FormatUtil.getString("am.webclient.login.title.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 1070 */                 out.write("</title>\n\t<!--[if lt IE 9]>\n\t    <style type='text/css'>\n\t\t\t.loginDiv{background:#ddd;}\n\t\t\tselect {color:#333;}\n\t\t\tinput[type=text],input[type=password]{line-height:25px}\n\t    </style>\n\t<![endif]-->\n\t<!--[if lt IE 10]>\n\t    <style type='text/css'>\n\t\t\tinput[type=text],input[type=password]{line-height:25px}\n\t\t\tselect {padding-left:4px !important}\n\t    </style>\n\t<![endif]-->\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/new_login.css\">\n\t");
/* 1071 */                 out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 1072 */                 out.write("\n\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/login.js\"></SCRIPT>\n\t<script src=\"/template/jquery.slides.min.js\"></script>\n\t<script type=\"text/javascript\">\n\t\tvar slidesContainer = $('div.slidesContainer');\n\t\t$(function(){\n\t\t\t$('#slides').slidesjs({\n\t\t\t\twidth: slidesContainer.width(),\n\t\t\t\theight: slidesContainer.height()*.9,\n\t\t        effect: {\n\t\t\t\t\tslide: {\n\t\t\t\t\t\tspeed: 1800\n\t\t\t\t\t}\n\t\t        },\n\t\t        pagination: {\n\t\t\t    \tactive: true,\n\t\t\t    \teffect: \"slide\"\t//NO I18N \n\t\t\t    },\n\t\t        navigation: false,\n\t\t        // start: 3,\n\t\t        play: {\n\t\t          auto: true,\n\t\t          pauseOnHover: true,\n\t\t          restartDelay: 2500\n\t\t        }\n\t\t\t});\n\t\t\t\n\t\t\t$('.slidesjs-container').height($('#slides').height());\n\t\t\t$('.slidesjs-container img').each(function() {\n\t\t\t\t$(this).height($('#slides').height()-30).width($('#slides').width());\n\t\t\t});\n\t\t\t\n\t\t\t$('div#slidesContainer').resize(function() {\n\t\t    \t$('.slidesjs-container img').each(function() {\n\t\t\t    \t$(this).height($('#slides').height()-30).width($('#slides').width());\n");
/* 1073 */                 out.write("\t\t\t\t});\n\t\t\t});\n\t\t});\n\t</script>\n</head>\n<body align=\"center\" onLoad=\"javascript:getImage();\" style=\"position:absolute\">\n\t<div class=\"loginerr maxwidth\" width=\"90%\">\n\t\t");
/* 1074 */                 out.print(FormatUtil.getString(errormessage));
/* 1075 */                 out.write("\n\t</div>\n\t<div class=\"loginhint maxwidth\" width=\"90%\">\n\t\t");
/* 1076 */                 out.print(loginmessage);
/* 1077 */                 out.write("\n\t</div>\n\t<div class=\"header maxwidth\" align=\"right\">\n\t<!--<div class=\"about\" align=\"right\">\n\t\t\t<img src=\"/images/question-mark.png\" width=\"28\" height=\"28\"/>\n\t\t</div>-->\n\t\t<div class=\"menu-tab-container\" id=\"loginTopLinks\">\n\t\t\t<ul id=\"menu-tab\">\n\t\t\t\t<li><a href=\"");
/* 1078 */                 out.print(forumsLink);
/* 1079 */                 out.write("\" target=\"_blank\">");
/* 1080 */                 out.print(FormatUtil.getString("am.webclient.newlogin.forum.text"));
/* 1081 */                 out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 1082 */                 out.print(blogsLink);
/* 1083 */                 out.write("\" target=\"_blank\">");
/* 1084 */                 out.print(FormatUtil.getString("am.webclient.support.blogs"));
/* 1085 */                 out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 1086 */                 out.print(faqlink);
/* 1087 */                 out.write("\" target=\"_blank\">");
/* 1088 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.productinfo.link5.txt"));
/* 1089 */                 out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 1090 */                 out.print(tShootlink);
/* 1091 */                 out.write("\" target=\"_blank\">");
/* 1092 */                 out.print(FormatUtil.getString("am.webclient.newlogin.troubleshooting.text"));
/* 1093 */                 out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 1094 */                 out.print(supportLink);
/* 1095 */                 out.write("\" target=\"_blank\">");
/* 1096 */                 out.print(FormatUtil.getString("am.webclient.newlogin.support.text"));
/* 1097 */                 out.write("</a></li>\n\t\t\t\t<li><a href=\"/help/index.html\" target=\"_blank\">");
/* 1098 */                 out.print(FormatUtil.getString("am.webclient.newlogin.userguide.text"));
/* 1099 */                 out.write("</a></li>\n\t\t\t</ul>\n\t\t</div>\n\t</div>\n\t");
/*      */                 
/* 1101 */                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1102 */                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1103 */                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                 
/* 1105 */                 _jspx_th_c_005fif_005f3.setTest("${showShockieMessage == true}");
/* 1106 */                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1107 */                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                   for (;;) {
/* 1109 */                     out.write("\n\t\t<div style=\"background-color:#DC1717; border-radius: 5px 5px 5px 5px;box-shadow: 2px 2px 2px 2px #CCCCCC;color: #FFFFFF;font-family: Arial, Helvetica, sans-serif;font-size: 14px;padding: 10px; display: inline; display: block;\" id=\"shockiemessage\"><b>");
/* 1110 */                     out.print(FormatUtil.getString("am.webclient.newlogin.genuinelicense.message.text"));
/* 1111 */                     out.write("</b></div>\n\t");
/* 1112 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1113 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1117 */                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1118 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                 }
/*      */                 
/* 1121 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1122 */                 out.write("\n\t<div id=\"container\" class=\"maxwidth\">\n\t  \t<div class=\"slidesContainer\">\n\t  \t\t<div id=\"slides\">\n\t\t\t\t<a href=\"");
/* 1123 */                 out.print(productLink);
/* 1124 */                 out.write("\" target=\"_blank\"><img src=\"/images/slides/img0.png\" alt=\"Best tool for monitoring applications in physical, virtual and cloud environments\"></a>\n\t\t\t\t<a href=\"");
/* 1125 */                 out.print(FormatUtil.getString("am.webclient.newlogin.img1.link"));
/* 1126 */                 out.write("\" target=\"_blank\"><img src=\"/images/slides/img1.png\" alt=\"sample text\"></a>\n\t\t\t\t<a href=\"");
/* 1127 */                 out.print(FormatUtil.getString("am.webclient.newlogin.img2.link"));
/* 1128 */                 out.write("\" target=\"_blank\"><img src=\"/images/slides/img2.png\" alt=\"Android Native App Support\"></a>\n\t\t    </div>\n\t\t\t<div class=\"loginLogo\" style=\"display: none;\">\n\t\t\t\t<p>\n\t\t\t\t\t<b>");
/* 1129 */                 out.print(OEMUtil.getOEMString("product.name"));
/* 1130 */                 out.write("</b>\n\t\t\t\t\t");
/* 1131 */                 out.print(FormatUtil.getString("am.webclient.newlogin.slider.text"));
/* 1132 */                 out.write("\n\t\t\t\t</p>\n\t\t\t</div>\n\t  \t</div>\n\t\t<div class=\"loginContainer\" align=\"right\">\n\t\t\t<div class=\"loginDiv\" align=\"center\">\n\t\t\t\t<a href=\"");
/* 1133 */                 out.print(productLink);
/* 1134 */                 out.write("\"> <img src=\"");
/* 1135 */                 out.print(productLogo);
/* 1136 */                 out.write("\" class=\"logo\" align=\"center\" border=\"0\"></a>\n\t\t\t\t<div class=\"loginForm\" align=\"right\">\n\t\t\t");
/* 1137 */                 if ((AMAutomaticPortChanger.isSsoEnabled()) && ("true".equalsIgnoreCase(System.getProperty("adminConnected")))) {
/* 1138 */                   String actionurl = "https://" + AMAutomaticPortChanger.getSSOHost() + ":" + AMAutomaticPortChanger.getSSOPort() + "/cas/login";
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1144 */                   out.write("\n        <form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"");
/* 1145 */                   out.print(actionurl);
/* 1146 */                   out.write("\" onSubmit='return validateUser();'>\n");
/*      */                 } else {
/* 1148 */                   out.write("\n\t\t\t\t\t<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"/j_security_check\" onSubmit='return validateUser();' >\n\n");
/*      */                 }
/* 1150 */                 out.write("\n\n\t\t\t\t\t\t<input type=\"hidden\" name=\"clienttype\" value=\"html\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"webstart\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"j_username\">\n\t\t\t\t\t");
/* 1151 */                 if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                   return;
/* 1153 */                 out.write("\n\n\t\t\t\t    \t<input type=\"hidden\" name=\"ScreenWidth\" value=\"1280\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"ScreenHeight\" value=\"709\">\n\t\t\t\t\t\t<p class=\"float username\">\n\t\t\t\t\t\t\t");
/*      */                 
/* 1155 */                 IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1156 */                 _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1157 */                 _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                 
/* 1159 */                 _jspx_th_c_005fif_005f5.setTest("${showLoginMsg=='true' && applicationScope.globalconfig.showgettingstarted=='true'}");
/* 1160 */                 int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1161 */                 if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                   for (;;) {
/* 1163 */                     out.write("\n\t\t\t\t\t\t\t<em id=\"firsttime-user\"><a onclick=\"showHideHint('show')\">");
/* 1164 */                     out.print(FormatUtil.getString("am.webclient.newlogin.firsttime.text"));
/* 1165 */                     out.write("</a></em>\n\t\t\t\t\t\t\t");
/* 1166 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1167 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1171 */                 if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1172 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                 }
/*      */                 
/* 1175 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1176 */                 out.write("\n\t\t\t\t\t\t\t<input width=\"80%\" type=\"text\" class=\"icon-user\" name=\"username\" placeholder=\"");
/* 1177 */                 out.print(FormatUtil.getString("am.webclient.newlogin.username.text"));
/* 1178 */                 out.write("\" autocomplete=\"off\" autocapitalize=\"off\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float password\">\n\t\t\t\t\t\t\t<input width=\"80%\" type=\"password\" class=\"icon-lock\" name=\"j_password\" placeholder=\"");
/* 1179 */                 out.print(FormatUtil.getString("am.webclient.newlogin.password.text"));
/* 1180 */                 out.write("\" autocomplete=\"off\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\">\n\t\t\t\t\t\t\t");
/*      */                 
/* 1182 */                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1183 */                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1184 */                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                 
/* 1186 */                 _jspx_th_c_005fif_005f6.setTest("${domainList != null }");
/* 1187 */                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1188 */                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                   for (;;) {
/* 1190 */                     out.write("\n\t\t\t\t\t\t\t\t<select name=\"domain\" class=\"icon-domain\">\n\t\t\t\t\t\t\t\t\t<option value='");
/* 1191 */                     if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                       return;
/* 1193 */                     out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 1194 */                     out.print(FormatUtil.getString("am.webclient.loginpage.local.auth.text"));
/* 1195 */                     out.write("</option>\n\t\t\t\t\t\t\t\t\t");
/* 1196 */                     if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                       return;
/* 1198 */                     out.write("\n\t\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t    ");
/* 1199 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1200 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1204 */                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1205 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                 }
/*      */                 
/* 1208 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1209 */                 out.write("\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\">\n\t\t\t\t\t\t\t<input type=\"checkbox\" id=\"RememberMe\" name=\"remember\" onClick=\"javascript: fnRemember()\">\n\t\t\t\t\t\t\t<label for=\"RememberMe\" class=\"checkboxtext\">");
/* 1210 */                 out.print(FormatUtil.getString("am.webclient.newlogin.rememberme.text"));
/* 1211 */                 out.write("</label>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\"> \n\t\t\t\t\t\t\t<input type=\"submit\" class=\"login_btn\" name=\"submit\" value=\"");
/* 1212 */                 out.print(FormatUtil.getString("am.webclient.login.login.text"));
/* 1213 */                 out.write("\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</form>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\t\n\t</div>\n\t<div class=\"quicklinks\" align=\"center\">\n\t<div class=\"loginFeatures\">\n\t\t<div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1214 */                 out.print(getInTouchLink);
/* 1215 */                 out.write("\" target=\"_blank\"><img src=\"/images/mail-us.png\" title=\"");
/* 1216 */                 out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.text"));
/* 1217 */                 out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1218 */                 out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.text"));
/* 1219 */                 out.write("</h3>\n\t\t\t<p>");
/* 1220 */                 out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.msg.text"));
/* 1221 */                 out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1222 */                 out.print(featuresLink);
/* 1223 */                 out.write("\" target=\"_blank\"><img src=\"/images/features.png\" title=\"");
/* 1224 */                 out.print(FormatUtil.getString("am.webclient.newlogin.features.text"));
/* 1225 */                 out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1226 */                 out.print(FormatUtil.getString("am.webclient.newlogin.features.text"));
/* 1227 */                 out.write("</h3>\n\t\t\t<p>");
/* 1228 */                 out.print(FormatUtil.getString("am.webclient.newlogin.features.msg.text"));
/* 1229 */                 out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1230 */                 out.print(techresourcesLink);
/* 1231 */                 out.write("\" target=\"_blank\"><img src=\"/images/technical-resource.png\" title=\"");
/* 1232 */                 out.print(FormatUtil.getString("am.webclient.newlogin.techresources.text"));
/* 1233 */                 out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1234 */                 out.print(FormatUtil.getString("am.webclient.newlogin.techresources.text"));
/* 1235 */                 out.write("</h3>\n\t\t\t<p>");
/* 1236 */                 out.print(FormatUtil.getString("am.webclient.newlogin.techresources.msg.text"));
/* 1237 */                 out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1238 */                 out.print(newfeaturesLink);
/* 1239 */                 out.write("?build=");
/* 1240 */                 out.print(prodVersion);
/* 1241 */                 out.write("\" target=\"_blank\"><img src=\"/images/whats-new.png\" title=\"");
/* 1242 */                 out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.text"));
/* 1243 */                 out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1244 */                 out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.text"));
/* 1245 */                 out.write("</h3>\n\t\t\t<p>");
/* 1246 */                 out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.msg.text"));
/* 1247 */                 out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1248 */                 out.print(quotesLink);
/* 1249 */                 out.write("\" target=\"_blank\"><img src=\"/images/feedback.png\" title=\"");
/* 1250 */                 out.print(FormatUtil.getString("am.webclient.talkback.feedback.text"));
/* 1251 */                 out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1252 */                 out.print(FormatUtil.getString("am.webclient.newlogin.quotes.text"));
/* 1253 */                 out.write("</h3>\n\t\t\t<p>");
/* 1254 */                 out.print(FormatUtil.getString("am.webclient.newlogin.quotes.msg.text"));
/* 1255 */                 out.write("</p>\n\t    </div>\n\t</div>\n\t</div>\n    <div style=\"clear:both\"></div>\n\t<div class=\"loginFooter\">\n\t\t<p class=\"footertext\" align=\"center\">\n\t\t\t<img name=\"copy\" src=\"\" style=\"display:none\"/>\n\t\t\t");
/* 1256 */                 out.write("\n\t\t\t");
/* 1257 */                 if ((!DBUtil.hasGlobalConfigValue("loginFeatures")) || (DBUtil.getGlobalConfigValueasBoolean("loginFeatures"))) {
/* 1258 */                   out.write("\n\t\t\t<span style=\"display:inline-block;padding:5px;color:#666\">");
/* 1259 */                   out.print(buildInfo);
/* 1260 */                   out.write("</span><br/>\n\t\t\t");
/*      */                 }
/* 1262 */                 out.write("\n\t\t\t");
/* 1263 */                 out.print(footerMsg);
/* 1264 */                 out.write("\n\t\t</p>\n\t</div>\n</body>\n<script>\n\tDelete_Cookie('selectedtab');//No I18N\t\n\n\tfunction validateUser()\n\t{\t\t\n\t\tif(trimAll(document.loginForm.username.value)== '')\n\t\t{\n\t\t\talert(\"");
/* 1265 */                 out.print(FormatUtil.getString("am.webclient.login.jsalertforusername.text"));
/* 1266 */                 out.write("\");\n\t\t\tdocument.loginForm.username.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse if(trimAll(document.loginForm.j_password.value)== '')\n\t\t{\n\t\t\talert(\"");
/* 1267 */                 out.print(FormatUtil.getString("am.webclient.login.jsalertforpassowrd.text"));
/* 1268 */                 out.write("\");\n\t\t\tdocument.loginForm.j_password.focus();\n\t\t\treturn false;\n\t\t}\n\t\tvar usr = document.loginForm.username.value;\n\t\tdocument.loginForm.j_username.value=usr;\n\t\t");
/* 1269 */                 if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                   return;
/* 1271 */                 out.write(" \n\t\tif(document.loginForm.domain){\n\t\t\tvar domainCombo = document.loginForm.domain;\n\t\t\tvar domainSel = domainCombo.options[domainCombo.selectedIndex].value;\n\t\t\tdocument.loginForm.j_username.value = domainSel +\"\\\\\"+ usr;//No i18n\n\t\t\t");
/* 1272 */                 if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                   return;
/* 1274 */                 out.write("\n\t\t}\n\t}\n\n\t");
/* 1275 */                 if (PluginUtil.isPlugin()) {
/* 1276 */                   out.write("\n\t$(window).one('load',function(){\t//NO I18N \n\t\tusername=$.getUrlParam('opm_user');\t//NO I18N \n\t\tif(username && window!=top){\n\t\t\tdocument.loginForm.username.value=username;\n\t\t\tdocument.loginForm.j_username.value=username;\n\t\t\tdocument.loginForm.j_password.value=username+'@opm';\t//No i18n \n\t\t\t$(\"input[name='submit']\").click();\t//NO I18N\n\t\t}\n\t});\n\t");
/*      */                 }
/* 1278 */                 out.write("\n\n\tfunction getImage()\n\t{\n\t\tDelete_Cookie('am_monitorsview');//No I18N\n\t\tDelete_Cookie('am_applicationsview');//No I18N\n\t\tDelete_Cookie('am_mgview');//No I18N\n\t\tGet_Cookie('selectedtab');//NO I18N\n\n\t\tif(navigator.onLine){\n\t\t\timg=new Image();\t//NO I18N \n\t\t\timg.src=\"https://www.manageengine.com/images/copyright.gif?");
/* 1279 */                 out.print(url);
/* 1280 */                 out.print(System.getProperty("did") != null ? "&" + System.getProperty("did") : "");
/* 1281 */                 out.write("\"; //No I18N\t\n\t\t}\n\t\t// we are hiding this link as we dont have support for forget password yet\n\t\t// document.getElementById(\"loginForgot\").style.display=\"none\";\n\t\t");
/* 1282 */                 if ((!"R".equals(fd.getUserType())) && (request.getParameter("product") == null))
/*      */                 {
/* 1284 */                   out.write("\n\t\t\tif(navigator.onLine && img && img.height==16){\n\t\t\t\tdocument['copy'].src=img.src;\n\t\t\t}\n\t\t");
/*      */                 }
/* 1286 */                 if (request.getAttribute("clearcookie") != null)
/*      */                 {
/* 1288 */                   username = "";
/* 1289 */                   password = "";
/*      */                 }
/* 1291 */                 out.write("\n\t\tfnSetDefault();\n\t}\n\t\n\t");
/* 1292 */                 if (_jspx_meth_c_005fchoose_005f4(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                   return;
/* 1294 */                 out.write("\n\n\tfunction checkAndRedirectToCookiesPage() {\t\t//NO I18N \n\t  if (document.all) //IE4+ specific code\n\t  {\n\t    document.cookie = \"testcookie\"\t\t//NO I18N \n\t    cookieEnabled = (document.cookie.indexOf(\"testcookie\") != -1) ? true : false;\t//NO I18N \n\t  }\n\t  else {\n\t    Set_Cookie('testcookie', '', expires); \t\t//No I18N \n\t    var tempCookie = Get_Cookie('testcookie'); \t//No I18N \n\t    if (tempCookie != null) {\n\t      cookieEnabled = true;\t\t//NO I18N \n\t    }\n\t    else {\n\t      cookieEnabled = false;\t//NO I18N \n\t    }\n\t  }\n\t  if (!cookieEnabled) {\n\t    location.href = \"/html/EnableCookies.html\";\t\t//No I18N \n\t  }\n\t}\n\n    function hideSlide() {\n\t\t$('#slides').hide();\n\t\t$('.loginLogo').show();\n    }\n    \n    function hideFeatures() {\n\t\t$('.loginFeatures').hide();\n    }\n    \n    function hideLoginTopLinks() {\n\t\t$('#loginTopLinks').hide();\n    }\n\n\t$(document).ready(function() {\t\t\t\t//NO I18N \n\t  \t//to fix the pixel differences b/w input and dropdown boxes.\n\t  \t$('.icon-domain').width($('.icon-lock').width() + 22);\t\t//NO I18N \n");
/* 1295 */                 out.write("\t  \t$(document).resize(function() {\t\t\t\t\t\t\t\t//NO I18N \n\t    \t$('.icon-domain').width($('.icon-lock').width() + 20);\t//No I18N \n\t  \t});\n\t  \tif(window!=top){\n\t\t\t$('.loginhint').html('");
/* 1296 */                 out.print(reloginMessage);
/* 1297 */                 out.write("'); \t\t\t//NO I18N\n\t\t}\n\n\t\t");
/* 1298 */                 if ((DBUtil.hasGlobalConfigValue("loginSlider")) && (!DBUtil.getGlobalConfigValueasBoolean("loginSlider"))) {
/* 1299 */                   out.write("\n  \t\t\thideSlide();\n  \t\t");
/*      */                 }
/* 1301 */                 out.write(10);
/* 1302 */                 out.write(9);
/* 1303 */                 out.write(9);
/* 1304 */                 if ((DBUtil.hasGlobalConfigValue("loginFeatures")) && (!DBUtil.getGlobalConfigValueasBoolean("loginFeatures"))) {
/* 1305 */                   out.write("\n  \t\t\thideFeatures();\n  \t\t");
/*      */                 }
/* 1307 */                 out.write(10);
/* 1308 */                 out.write(9);
/* 1309 */                 out.write(9);
/* 1310 */                 if ((DBUtil.hasGlobalConfigValue("loginTopLinks")) && (!DBUtil.getGlobalConfigValueasBoolean("loginTopLinks"))) {
/* 1311 */                   out.write("\n  \t\t\thideLoginTopLinks();\n  \t\t");
/*      */                 }
/* 1313 */                 out.write("\n\t});\n\t\t\n</script>\n</html>\n");
/* 1314 */                 out.write(9);
/* 1315 */                 out.write(10);
/* 1316 */                 out.write(9);
/* 1317 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1318 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1322 */             if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1323 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */             }
/*      */             
/* 1326 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1327 */             out.write(10);
/* 1328 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1329 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1333 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1334 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */         }
/*      */         
/* 1337 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1338 */         out.write("                        \n");
/*      */       }
/* 1340 */       out.write(10);
/*      */     } catch (Throwable t) {
/* 1342 */       if (!(t instanceof SkipPageException)) {
/* 1343 */         out = _jspx_out;
/* 1344 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1345 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1346 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1349 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1355 */     PageContext pageContext = _jspx_page_context;
/* 1356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1358 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1359 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 1360 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1362 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.mobile.login.title.text");
/* 1363 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 1364 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 1365 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1366 */       return true;
/*      */     }
/* 1368 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1369 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1374 */     PageContext pageContext = _jspx_page_context;
/* 1375 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1377 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1378 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1379 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1381 */     _jspx_th_c_005fout_005f0.setValue("${actionUrl}");
/* 1382 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1383 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1384 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1385 */       return true;
/*      */     }
/* 1387 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1393 */     PageContext pageContext = _jspx_page_context;
/* 1394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1396 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1397 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 1398 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1400 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.login.jsalertforusername.text");
/* 1401 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 1402 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 1403 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 1404 */       return true;
/*      */     }
/* 1406 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 1407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1412 */     PageContext pageContext = _jspx_page_context;
/* 1413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1415 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1416 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 1417 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1419 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.login.jsalertforpassowrd.text");
/* 1420 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 1421 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 1422 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 1423 */       return true;
/*      */     }
/* 1425 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 1426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1431 */     PageContext pageContext = _jspx_page_context;
/* 1432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1434 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1435 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1436 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1438 */     _jspx_th_c_005fif_005f0.setTest("${ssoEnabled}");
/* 1439 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1440 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1442 */         out.write("\n\t    <input type=\"hidden\" name=\"auto\" value=\"true\"/>\n\t    <input type=\"hidden\" name=\"service\" value=\"");
/* 1443 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 1444 */           return true;
/* 1445 */         out.write("\" />\n\t    <input type=\"hidden\" name=\"password\" id=\"password\" value=\"admin1\"/>\n\t");
/* 1446 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1447 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1451 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1452 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1453 */       return true;
/*      */     }
/* 1455 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1461 */     PageContext pageContext = _jspx_page_context;
/* 1462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1464 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1465 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1466 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 1468 */     _jspx_th_c_005fout_005f1.setValue("${serviceurl}");
/* 1469 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1470 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1471 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1472 */       return true;
/*      */     }
/* 1474 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1475 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1480 */     PageContext pageContext = _jspx_page_context;
/* 1481 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1483 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1484 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1485 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 1486 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1487 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1489 */         out.write("\n\t\t<tr height=\"20\">\n\t\t\t<td>&nbsp;</td>\n\t\t</tr>\n\t");
/* 1490 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1491 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1495 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1496 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1497 */       return true;
/*      */     }
/* 1499 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1500 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1505 */     PageContext pageContext = _jspx_page_context;
/* 1506 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1508 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1509 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 1510 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1512 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.mobile.username.txt");
/* 1513 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 1514 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 1515 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 1516 */       return true;
/*      */     }
/* 1518 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 1519 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1524 */     PageContext pageContext = _jspx_page_context;
/* 1525 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1527 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1528 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 1529 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1531 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.mobile.password.txt");
/* 1532 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 1533 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 1534 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 1535 */       return true;
/*      */     }
/* 1537 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 1538 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1543 */     PageContext pageContext = _jspx_page_context;
/* 1544 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1546 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1547 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1548 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1550 */     _jspx_th_c_005fif_005f1.setTest("${domainList != null }");
/* 1551 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1552 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 1554 */         out.write("\n\t\t<select name=\"domain\" style=\"width:210px\" >\n\t\t\t<option value='");
/* 1555 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1556 */           return true;
/* 1557 */         out.write(39);
/* 1558 */         out.write(62);
/* 1559 */         if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1560 */           return true;
/* 1561 */         out.write(" </option>\n\t\t\t<option value='");
/* 1562 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1563 */           return true;
/* 1564 */         out.write(39);
/* 1565 */         out.write(62);
/* 1566 */         if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1567 */           return true;
/* 1568 */         out.write(" </option>\n\t\t\t");
/* 1569 */         if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 1570 */           return true;
/* 1571 */         out.write("\n\t\t</select>\n\t\t");
/* 1572 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1573 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1577 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1578 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1579 */       return true;
/*      */     }
/* 1581 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1582 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1587 */     PageContext pageContext = _jspx_page_context;
/* 1588 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1590 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1591 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1592 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1594 */     _jspx_th_c_005fout_005f2.setValue("select");
/* 1595 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1596 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1597 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1598 */       return true;
/*      */     }
/* 1600 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1601 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1606 */     PageContext pageContext = _jspx_page_context;
/* 1607 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1609 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1610 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 1611 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1613 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.admintab.adduser.importad.select.domain");
/* 1614 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 1615 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 1616 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 1617 */       return true;
/*      */     }
/* 1619 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 1620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1625 */     PageContext pageContext = _jspx_page_context;
/* 1626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1628 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1629 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1630 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1632 */     _jspx_th_c_005fout_005f3.setValue("local");
/* 1633 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1634 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1635 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1636 */       return true;
/*      */     }
/* 1638 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1644 */     PageContext pageContext = _jspx_page_context;
/* 1645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1647 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1648 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 1649 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1651 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.loginpage.local.auth.text");
/* 1652 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 1653 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 1654 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 1655 */       return true;
/*      */     }
/* 1657 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 1658 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1663 */     PageContext pageContext = _jspx_page_context;
/* 1664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1666 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1667 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1668 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1670 */     _jspx_th_c_005fforEach_005f0.setVar("eachDomain");
/*      */     
/* 1672 */     _jspx_th_c_005fforEach_005f0.setItems("${domainList}");
/*      */     
/* 1674 */     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 1675 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1677 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1678 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1680 */           out.write("\t\t\t\t\t\t\t\t\t  \n\t\t\t\t<option value='");
/* 1681 */           boolean bool; if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1682 */             return true;
/* 1683 */           out.write(39);
/* 1684 */           out.write(62);
/* 1685 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1686 */             return true;
/* 1687 */           out.write("</option>\n\t\t\t");
/* 1688 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1689 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1693 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1694 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1697 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f0; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 1698 */         out = _jspx_page_context.popBody(); }
/* 1699 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1701 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1702 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1704 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1709 */     PageContext pageContext = _jspx_page_context;
/* 1710 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1712 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1713 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1714 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1716 */     _jspx_th_c_005fout_005f4.setValue("${eachDomain.value}");
/* 1717 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1718 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1719 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1720 */       return true;
/*      */     }
/* 1722 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1728 */     PageContext pageContext = _jspx_page_context;
/* 1729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1731 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1732 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1733 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1735 */     _jspx_th_c_005fout_005f5.setValue("${eachDomain.label}");
/* 1736 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1737 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1738 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1739 */       return true;
/*      */     }
/* 1741 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1742 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1747 */     PageContext pageContext = _jspx_page_context;
/* 1748 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1750 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1751 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 1752 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1754 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.mobile.rememberme.txt");
/* 1755 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 1756 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 1757 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 1758 */       return true;
/*      */     }
/* 1760 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 1761 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1766 */     PageContext pageContext = _jspx_page_context;
/* 1767 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1769 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1770 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 1771 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1773 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.webclient.login.login.text");
/* 1774 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 1775 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 1776 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 1777 */       return true;
/*      */     }
/* 1779 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 1780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fsetLocale_005f0(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1785 */     PageContext pageContext = _jspx_page_context;
/* 1786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1788 */     SetLocaleTag _jspx_th_fmt_005fsetLocale_005f0 = (SetLocaleTag)this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.get(SetLocaleTag.class);
/* 1789 */     _jspx_th_fmt_005fsetLocale_005f0.setPageContext(_jspx_page_context);
/* 1790 */     _jspx_th_fmt_005fsetLocale_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1792 */     _jspx_th_fmt_005fsetLocale_005f0.setValue("${locale}");
/*      */     
/* 1794 */     _jspx_th_fmt_005fsetLocale_005f0.setScope("application");
/* 1795 */     int _jspx_eval_fmt_005fsetLocale_005f0 = _jspx_th_fmt_005fsetLocale_005f0.doStartTag();
/* 1796 */     if (_jspx_th_fmt_005fsetLocale_005f0.doEndTag() == 5) {
/* 1797 */       this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f0);
/* 1798 */       return true;
/*      */     }
/* 1800 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f0);
/* 1801 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1806 */     PageContext pageContext = _jspx_page_context;
/* 1807 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1809 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1810 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 1811 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1813 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.newlogout.text");
/* 1814 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 1815 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 1816 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 1817 */       return true;
/*      */     }
/* 1819 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 1820 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1825 */     PageContext pageContext = _jspx_page_context;
/* 1826 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1828 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1829 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 1830 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 1831 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 1832 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 1834 */         out.write("\n\t\t\t");
/* 1835 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 1836 */           return true;
/* 1837 */         out.write("\n\t\t    ");
/* 1838 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 1839 */           return true;
/* 1840 */         out.write(10);
/* 1841 */         out.write(9);
/* 1842 */         out.write(9);
/* 1843 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 1844 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1848 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 1849 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1850 */       return true;
/*      */     }
/* 1852 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 1853 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1858 */     PageContext pageContext = _jspx_page_context;
/* 1859 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1861 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1862 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1863 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 1865 */     _jspx_th_c_005fwhen_005f3.setTest("${applicationScope.globalconfig.showgettingstarted=='true'}");
/* 1866 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1867 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 1869 */         out.write("\n\t\t\t\t");
/* 1870 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 1871 */           return true;
/* 1872 */         out.write("\t    \t\n\t\t    ");
/* 1873 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1874 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1878 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1879 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1880 */       return true;
/*      */     }
/* 1882 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1883 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1888 */     PageContext pageContext = _jspx_page_context;
/* 1889 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1891 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 1892 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 1893 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 1895 */     _jspx_th_c_005fset_005f0.setVar("redirectUrl");
/*      */     
/* 1897 */     _jspx_th_c_005fset_005f0.setValue("/index.do");
/* 1898 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 1899 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 1900 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 1901 */       return true;
/*      */     }
/* 1903 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 1904 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1909 */     PageContext pageContext = _jspx_page_context;
/* 1910 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1912 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1913 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1914 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 1915 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1916 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1918 */         out.write("\n\t\t    \t");
/* 1919 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 1920 */           return true;
/* 1921 */         out.write("\n\t\t    ");
/* 1922 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1923 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1927 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1928 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1929 */       return true;
/*      */     }
/* 1931 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1932 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1937 */     PageContext pageContext = _jspx_page_context;
/* 1938 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1940 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 1941 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 1942 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1944 */     _jspx_th_c_005fset_005f1.setVar("redirectUrl");
/*      */     
/* 1946 */     _jspx_th_c_005fset_005f1.setValue("/MyPage.do?method=viewDashBoard&toredirect=true");
/* 1947 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 1948 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 1949 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 1950 */       return true;
/*      */     }
/* 1952 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 1953 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1958 */     PageContext pageContext = _jspx_page_context;
/* 1959 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1961 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1962 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1963 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1965 */     _jspx_th_c_005fout_005f6.setValue("local");
/* 1966 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1967 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1968 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1969 */       return true;
/*      */     }
/* 1971 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1972 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1977 */     PageContext pageContext = _jspx_page_context;
/* 1978 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1980 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1981 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1982 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1984 */     _jspx_th_c_005fforEach_005f1.setVar("eachDomain");
/*      */     
/* 1986 */     _jspx_th_c_005fforEach_005f1.setItems("${domainList}");
/*      */     
/* 1988 */     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 1989 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1991 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1992 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1994 */           out.write("\t\t\t\t\t\t\t\t\t  \n\t\t\t\t\t\t\t\t<option value='");
/* 1995 */           boolean bool; if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1996 */             return true;
/* 1997 */           out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 1998 */           if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1999 */             return true;
/* 2000 */           out.write("</option>\n\t\t\t\t\t\t\t");
/* 2001 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 2002 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2006 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 2007 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2010 */         int tmp241_240 = 0; int[] tmp241_238 = _jspx_push_body_count_c_005fforEach_005f1; int tmp243_242 = tmp241_238[tmp241_240];tmp241_238[tmp241_240] = (tmp243_242 - 1); if (tmp243_242 <= 0) break;
/* 2011 */         out = _jspx_page_context.popBody(); }
/* 2012 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 2014 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 2015 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 2017 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2022 */     PageContext pageContext = _jspx_page_context;
/* 2023 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2025 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2026 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2027 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 2029 */     _jspx_th_c_005fout_005f7.setValue("${eachDomain.value}");
/* 2030 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2031 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2032 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2033 */       return true;
/*      */     }
/* 2035 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2036 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2041 */     PageContext pageContext = _jspx_page_context;
/* 2042 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2044 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2045 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2046 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 2048 */     _jspx_th_c_005fout_005f8.setValue("${eachDomain.label}");
/* 2049 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2050 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2051 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2052 */       return true;
/*      */     }
/* 2054 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2055 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2060 */     PageContext pageContext = _jspx_page_context;
/* 2061 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2063 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2064 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 2065 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2067 */     _jspx_th_c_005fout_005f9.setValue("${redirectUrl}");
/* 2068 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 2069 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 2070 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2071 */       return true;
/*      */     }
/* 2073 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2079 */     PageContext pageContext = _jspx_page_context;
/* 2080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2082 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2083 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2084 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/* 2085 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2086 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 2088 */         out.write("\n\t\t\t\t");
/* 2089 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 2090 */           return true;
/* 2091 */         out.write("\n\t\t\t\t");
/* 2092 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 2093 */           return true;
/* 2094 */         out.write("\n\t\t\t");
/* 2095 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2096 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2100 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2101 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2102 */       return true;
/*      */     }
/* 2104 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2105 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2110 */     PageContext pageContext = _jspx_page_context;
/* 2111 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2113 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2114 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2115 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 2117 */     _jspx_th_c_005fwhen_005f4.setTest("${showErrMsg == 'true'}");
/* 2118 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2119 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 2121 */         out.write("\n\t\t\t\t\tcheckAndRedirectToCookiesPage();\n\t\t\t\t\tshowLoginErr();\n\t\t\t\t");
/* 2122 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2123 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2127 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2128 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2129 */       return true;
/*      */     }
/* 2131 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2132 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2137 */     PageContext pageContext = _jspx_page_context;
/* 2138 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2140 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2141 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2142 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 2143 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2144 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 2146 */         out.write("\n\t\t\t\t\t$('div.loginerr').hide();\n\t\t\t\t");
/* 2147 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2148 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2152 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2153 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2154 */       return true;
/*      */     }
/* 2156 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2157 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fsetLocale_005f1(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2162 */     PageContext pageContext = _jspx_page_context;
/* 2163 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2165 */     SetLocaleTag _jspx_th_fmt_005fsetLocale_005f1 = (SetLocaleTag)this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.get(SetLocaleTag.class);
/* 2166 */     _jspx_th_fmt_005fsetLocale_005f1.setPageContext(_jspx_page_context);
/* 2167 */     _jspx_th_fmt_005fsetLocale_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2169 */     _jspx_th_fmt_005fsetLocale_005f1.setValue("${locale}");
/*      */     
/* 2171 */     _jspx_th_fmt_005fsetLocale_005f1.setScope("application");
/* 2172 */     int _jspx_eval_fmt_005fsetLocale_005f1 = _jspx_th_fmt_005fsetLocale_005f1.doStartTag();
/* 2173 */     if (_jspx_th_fmt_005fsetLocale_005f1.doEndTag() == 5) {
/* 2174 */       this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f1);
/* 2175 */       return true;
/*      */     }
/* 2177 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f1);
/* 2178 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2183 */     PageContext pageContext = _jspx_page_context;
/* 2184 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2186 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2187 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2188 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2190 */     _jspx_th_c_005fif_005f4.setTest("${ssoEnabled}");
/* 2191 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2192 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 2194 */         out.write("\n                                                        <input type=\"hidden\" name=\"auto\" value=\"true\"/>\n                                                        <input type=\"hidden\" name=\"service\" value=\"");
/* 2195 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 2196 */           return true;
/* 2197 */         out.write("\" />\n                                                        <input type=\"hidden\" name=\"password\" id=\"password\" value=\"admin123\"/>\n                                         ");
/* 2198 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2199 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2203 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2204 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2205 */       return true;
/*      */     }
/* 2207 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2213 */     PageContext pageContext = _jspx_page_context;
/* 2214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2216 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2217 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 2218 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 2220 */     _jspx_th_c_005fout_005f10.setValue("${serviceurl}");
/* 2221 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 2222 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 2223 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2224 */       return true;
/*      */     }
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2227 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2232 */     PageContext pageContext = _jspx_page_context;
/* 2233 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2235 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2236 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 2237 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2239 */     _jspx_th_c_005fout_005f11.setValue("local");
/* 2240 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 2241 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 2242 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2243 */       return true;
/*      */     }
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2246 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2251 */     PageContext pageContext = _jspx_page_context;
/* 2252 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2254 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2255 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 2256 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2258 */     _jspx_th_c_005fforEach_005f2.setVar("eachDomain");
/*      */     
/* 2260 */     _jspx_th_c_005fforEach_005f2.setItems("${domainList}");
/*      */     
/* 2262 */     _jspx_th_c_005fforEach_005f2.setVarStatus("status");
/* 2263 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 2265 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 2266 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 2268 */           out.write("\t\t\t\t\t\t\t\t\t  \n\t\t\t\t\t\t\t\t\t\t<option value='");
/* 2269 */           boolean bool; if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 2270 */             return true;
/* 2271 */           out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 2272 */           if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 2273 */             return true;
/* 2274 */           out.write("</option>\n\t\t\t\t\t\t\t\t\t");
/* 2275 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 2276 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2280 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 2281 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2284 */         int tmp241_240 = 0; int[] tmp241_238 = _jspx_push_body_count_c_005fforEach_005f2; int tmp243_242 = tmp241_238[tmp241_240];tmp241_238[tmp241_240] = (tmp243_242 - 1); if (tmp243_242 <= 0) break;
/* 2285 */         out = _jspx_page_context.popBody(); }
/* 2286 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 2288 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 2289 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 2291 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 2296 */     PageContext pageContext = _jspx_page_context;
/* 2297 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2299 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2300 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2301 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 2303 */     _jspx_th_c_005fout_005f12.setValue("${eachDomain.value}");
/* 2304 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2305 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2306 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2307 */       return true;
/*      */     }
/* 2309 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2310 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 2315 */     PageContext pageContext = _jspx_page_context;
/* 2316 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2318 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2319 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2320 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 2322 */     _jspx_th_c_005fout_005f13.setValue("${eachDomain.label}");
/* 2323 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2324 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2325 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2326 */       return true;
/*      */     }
/* 2328 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2334 */     PageContext pageContext = _jspx_page_context;
/* 2335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2337 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2338 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2339 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2341 */     _jspx_th_c_005fif_005f7.setTest("${ssoEnabled}");
/* 2342 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2343 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 2345 */         out.write("\n                        document.loginForm.password.value=document.loginForm.j_password.value;\n                                \n                        ");
/* 2346 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2347 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2351 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2352 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2353 */       return true;
/*      */     }
/* 2355 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2356 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2361 */     PageContext pageContext = _jspx_page_context;
/* 2362 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2364 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2365 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2366 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 2368 */     _jspx_th_c_005fif_005f8.setTest("${ssoEnabled}");
/* 2369 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2370 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 2372 */         out.write("     \n                        document.loginForm.username.value=domainSel +\"\\\\\"+ usr;\n                        ");
/* 2373 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2374 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2378 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2379 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2380 */       return true;
/*      */     }
/* 2382 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2388 */     PageContext pageContext = _jspx_page_context;
/* 2389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2391 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2392 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2393 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/* 2394 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2395 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 2397 */         out.write(10);
/* 2398 */         out.write(9);
/* 2399 */         out.write(9);
/* 2400 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2401 */           return true;
/* 2402 */         out.write(10);
/* 2403 */         out.write(9);
/* 2404 */         out.write(9);
/* 2405 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 2406 */           return true;
/* 2407 */         out.write(10);
/* 2408 */         out.write(9);
/* 2409 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2410 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2414 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2415 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2416 */       return true;
/*      */     }
/* 2418 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2419 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2424 */     PageContext pageContext = _jspx_page_context;
/* 2425 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2427 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2428 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2429 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 2431 */     _jspx_th_c_005fwhen_005f5.setTest("${showErrMsg == 'true'}");
/* 2432 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2433 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 2435 */         out.write("\n\t\t\tshowLoginErr();\t//NO I18N \n\t\t");
/* 2436 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2437 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2441 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2442 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2443 */       return true;
/*      */     }
/* 2445 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2446 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2451 */     PageContext pageContext = _jspx_page_context;
/* 2452 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2454 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2455 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2456 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 2457 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2458 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 2460 */         out.write("\n\t\t\tcheckAndRedirectToCookiesPage();\t//NO I18N \n\t\t\t$('div.loginerr').hide();\t\t\t//NO I18N \n\t\t");
/* 2461 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2462 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2466 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2467 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2468 */       return true;
/*      */     }
/* 2470 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2471 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\formpages\Error_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */