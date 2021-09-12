/*      */ package org.apache.jsp.jsp.formpages;
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
/*      */ import javax.servlet.http.Cookie;
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
/*      */ import org.apache.struts.taglib.logic.PresentTag;
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
/*   57 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   63 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/*   64 */   static { _jspx_dependants.put("/jsp/Login.jsp", Long.valueOf(1473429417000L));
/*   65 */     _jspx_dependants.put("/jsp/AMLogin.jsp", Long.valueOf(1473429417000L));
/*   66 */     _jspx_dependants.put("/webclient/mobile/jsp/MobileLogin.jsp", Long.valueOf(1473429417000L));
/*   67 */     _jspx_dependants.put("/jsp/PreLogin.jsp", Long.valueOf(1473429417000L));
/*   68 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
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
/*   86 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   90 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  100 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  101 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  105 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  106 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  107 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  108 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  109 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  110 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  111 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  112 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  113 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.release();
/*  114 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  121 */     HttpSession session = null;
/*      */     
/*      */ 
/*  124 */     JspWriter out = null;
/*  125 */     Object page = this;
/*  126 */     JspWriter _jspx_out = null;
/*  127 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  131 */       response.setContentType("text/html;charset=UTF-8");
/*  132 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  134 */       _jspx_page_context = pageContext;
/*  135 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  136 */       ServletConfig config = pageContext.getServletConfig();
/*  137 */       session = pageContext.getSession();
/*  138 */       out = pageContext.getOut();
/*  139 */       _jspx_out = out;
/*      */       
/*  141 */       out.write("<!DOCTYPE html>\n");
/*  142 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  143 */       out.write("\n\n\n\n\n\n\n");
/*      */       
/*  145 */       response.setHeader("Cache-Control", "no-store");
/*  146 */       response.setHeader("Pragma", "no-cache");
/*  147 */       response.setDateHeader("Expires", 0L);
/*  148 */       Cookie[] cookies = request.getCookies();
/*      */       
/*      */ 
/*      */ 
/*  152 */       String hostname = request.getServerName();
/*      */       
/*  154 */       String appManagerUserAgent = AMAutomaticPortChanger.userAgent;
/*  155 */       String userAgent = request.getHeader("User-Agent");
/*      */       
/*      */ 
/*  158 */       boolean ignoreCookieCheck = false;
/*      */       
/*  160 */       if ((userAgent != null) && (userAgent.indexOf(appManagerUserAgent) != -1)) {
/*  161 */         ignoreCookieCheck = true;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  166 */       String isMobile = "false";
/*  167 */       userAgent = userAgent.toLowerCase();
/*  168 */       if ((userAgent.matches(".*(android.+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino).*")) || (userAgent.substring(0, 4).matches("1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|e\\-|e\\/|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\\-|2|g)|yas\\-|your|zeto|zte\\-")))
/*      */       {
/*  170 */         isMobile = "true";
/*  171 */         ignoreCookieCheck = true;
/*      */       }
/*  173 */       pageContext.setAttribute("mobileHomePage", isMobile);
/*  174 */       pageContext.setAttribute("mobile", isMobile);
/*      */       
/*  176 */       if ((hostname != null) && (hostname.contains("_")) && (userAgent.toLowerCase().contains("msie")))
/*      */       {
/*  178 */         response.sendRedirect("/jsp/formpages/AccessRestricted.jsp?EnableCookiesInIE=true");
/*      */       }
/*      */       
/*  181 */       if (session.isNew())
/*      */       {
/*  183 */         if ((ignoreCookieCheck) || (cookies != null)) {
/*  184 */           String referer = request.getHeader("Referer");
/*  185 */           if (referer == null)
/*      */           {
/*  187 */             if ("true".equals(pageContext.getAttribute("mobileHomePage")))
/*      */             {
/*  189 */               response.sendRedirect("/mobile/HomeDetails.do?method=showHomePage");
/*      */             }
/*      */             else
/*      */             {
/*  193 */               System.out.println("Redirecting to applications.do");
/*  194 */               response.sendRedirect("/applications.do");
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  199 */             System.out.println("Redirecting to " + referer);
/*  200 */             response.sendRedirect(referer);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*  205 */       else if (!AMAutomaticPortChanger.isSsoEnabled())
/*      */       {
/*      */ 
/*  208 */         out.write(" \n\t\t");
/*      */         
/*  210 */         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  211 */         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  212 */         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */         
/*  214 */         _jspx_th_logic_005fpresent_005f0.setRole("USERS");
/*  215 */         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  216 */         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */           for (;;) {
/*  218 */             out.write("\n\t\t\t");
/*      */             
/*  220 */             if ("true".equals(pageContext.getAttribute("mobileHomePage")))
/*      */             {
/*  222 */               response.sendRedirect("/mobile/HomeDetails.do?method=showHomePage");
/*      */             }
/*      */             else
/*      */             {
/*  226 */               System.out.println("Redirecting to applications.do");
/*  227 */               response.sendRedirect("/applications.do");
/*      */             }
/*      */             
/*  230 */             out.write(10);
/*  231 */             out.write(9);
/*  232 */             out.write(9);
/*  233 */             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  234 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  238 */         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  239 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */         }
/*      */         
/*  242 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  243 */         out.write(10);
/*  244 */         out.write(9);
/*      */       }
/*      */       
/*      */ 
/*  248 */       out.write(10);
/*  249 */       out.write(10);
/*  250 */       out.write(10);
/*  251 */       out.write(10);
/*      */       
/*  253 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  254 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  255 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/*  256 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  257 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */         for (;;) {
/*  259 */           out.write("  \n\t");
/*      */           
/*  261 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  262 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  263 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  265 */           _jspx_th_c_005fwhen_005f0.setTest("${!empty mobile && mobile=='true'}");
/*  266 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  267 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */             for (;;) {
/*  269 */               out.write(10);
/*  270 */               out.write(9);
/*  271 */               out.write(9);
/*  272 */               out.write("<!DOCTYPE html>\n<!--  Do not move down the above DOCTYPE definition(let that be first line) -->\n");
/*  273 */               out.write("\n\n<!-- Applications Manager Login Screen -->\n");
/*  274 */               out.write("\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\" bgcolor=\"ccc\">\n\n\n\n\n\n\n\n<head>\n<title>");
/*  275 */               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  277 */               out.write("</title>\n<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0\">\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />\n<link href=\"/images/mobile/mobile.css\" rel=\"stylesheet\" type=\"text/css\" />\n<link rel=\"apple-touch-icon-precomposed\" href=\"/images/mobile/me_apm_icon.png\"/>\n<link rel=\"apple-touch-startup-image\" href=\"/images/mobile/apm_startup.png\"/>\n</head>\n\n");
/*      */               
/*  279 */               String mobile = request.getSession().getAttribute("mobile") == null ? "true" : (String)request.getSession().getAttribute("mobile");
/*  280 */               if (mobile.equals("true"))
/*      */               {
/*  282 */                 request.getSession().setAttribute("mobile", mobile);
/*      */               }
/*  284 */               ArrayList domainList = ADAuthenticationUtil.getDomainList();
/*  285 */               if ((domainList != null) && (domainList.size() > 0)) {
/*  286 */                 pageContext.setAttribute("domainList", domainList);
/*      */               }
/*      */               
/*  289 */               boolean ssoEnabled = AMAutomaticPortChanger.isSsoEnabled();
/*  290 */               pageContext.setAttribute("ssoEnabled", String.valueOf(ssoEnabled));
/*  291 */               String actionUrl = "/j_security_check";
/*  292 */               if (ssoEnabled) {
/*  293 */                 if ("true".equalsIgnoreCase(System.getProperty("adminConnected"))) {
/*  294 */                   actionUrl = "/cas/login";
/*  295 */                   actionUrl = EnterpriseUtil.isManagedServer() ? "https://" + EnterpriseUtil.getAdminServerHost() + ":" + EnterpriseUtil.getAdminServerPort() + actionUrl : actionUrl;
/*      */                 }
/*  297 */                 if (request.getParameter("ticket") != null) {
/*  298 */                   String serviceurl = request.getParameter("casredirecturl");
/*  299 */                   pageContext.setAttribute("serviceurl", serviceurl);
/*  300 */                   response.sendRedirect(URLDecoder.decode(serviceurl)); return;
/*      */                 }
/*      */                 
/*  303 */                 if (request.getParameter("caserror") != null) {
/*  304 */                   request.setAttribute("errormessage", "webclient.login.loginfailed.message");
/*  305 */                   String errormessage = (String)request.getAttribute("errormessage");
/*  306 */                   pageContext.setAttribute("errormessage", errormessage);
/*      */                 }
/*      */               }
/*  309 */               pageContext.setAttribute("actionUrl", actionUrl);
/*      */               
/*  311 */               out.write("\n<body onLoad=\"javascript:fnSetDefault();\" style=\"background:#264a7a url(/images/mobile/bg.png) repeat-x scroll left bottom;\">\n<div id=\"LoginDiv\" class=\"indexBG\">\n<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"");
/*  312 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  314 */               out.write("\" onSubmit='return validateUser(\"");
/*  315 */               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  317 */               out.write(34);
/*  318 */               out.write(44);
/*  319 */               out.write(34);
/*  320 */               if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  322 */               out.write("\");'>\n\t<input type=\"hidden\" name=\"j_username\" value=\"admin2\"/>\n\t");
/*  323 */               if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  325 */               out.write("\n\n<div id=\"indexLogo\" align=\"center\"><img src=\"/images/mobile/spacer.gif\" width=\"205\" height=\"69\" class=\"logoBGlarge\"/></div>\n");
/*  326 */               out.write("\n\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" class=\"loginTab\" style=\"height: 285px;\">\n  ");
/*      */               
/*  328 */               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  329 */               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  330 */               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*  331 */               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  332 */               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                 for (;;) {
/*  334 */                   out.write(10);
/*  335 */                   out.write(9);
/*      */                   
/*  337 */                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  338 */                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  339 */                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                   
/*  341 */                   _jspx_th_c_005fwhen_005f1.setTest("${errormessage!=null}");
/*  342 */                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  343 */                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                     for (;;) {
/*  345 */                       out.write("\n\t\t<tr>\n\t\t\t<td height=\"20\"><span align=\"left\" valign=\"top\"\tstyle=\"position: relative; background-color: #FFCC00; color: #000000; border-radius: 5px;\"><font color=\"#000000;\"> ");
/*  346 */                       out.print(FormatUtil.getString((String)request.getAttribute("errormessage")));
/*  347 */                       out.write("</font></span></td>\n\t\t</tr>\n\t");
/*  348 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  349 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  353 */                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  354 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                   }
/*      */                   
/*  357 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  358 */                   out.write(10);
/*  359 */                   out.write(9);
/*  360 */                   if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/*      */                     return;
/*  362 */                   out.write(10);
/*  363 */                   out.write(32);
/*  364 */                   out.write(32);
/*  365 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  366 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  370 */               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  371 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */               }
/*      */               
/*  374 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  375 */               out.write("\n  <tr>\n    <td height=\"20\" align=\"left\" valign=\"middle\" style=\"padding-left: 2px;\">");
/*  376 */               if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  378 */               out.write("</td>\n  </tr>\n  <tr>\n    <td height=\"35\" align=\"left\" valign=\"top\"><input name=\"username\" type=\"text\" class=\"formTxtField\"/></td>\n  </tr>\n  <tr>\n    <td height=\"20\" align=\"left\" valign=\"middle\" style=\"padding-left: 2px;\">");
/*  379 */               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  381 */               out.write("</td>\n  </tr>\n  <tr>\n    <td height=\"35\" align=\"left\" valign=\"top\"><input name=\"j_password\" type=\"password\" class=\"formTxtField\"/></td>\n  </tr>  \n  <tr>\n\t<td>\n\t\t");
/*  382 */               if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  384 */               out.write("\n\t</td>\n  </tr>\n  <tr>\n    <td height=\"15\" align=\"left\"><table><tr><td><input type=\"checkbox\" name=\"remember\" value=\"true\" class=\"formChkBox\" onchange=\"javascript: fnRemember()\"/></td><td>");
/*  385 */               if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  387 */               out.write("</td></tr></table> </td>\n  </tr>\n  <tr>\n    <td height=\"70\" align=\"center\" valign=\"top\"> <input class=\"loginBtn\" name=\"button\" type=\"submit\" value=\"");
/*  388 */               if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  390 */               out.write("\" ></td>\n  </tr>\n  <tr>\n\t  <td align=\"center\" valign=\"bottom\" style=\"color:#99B1D0;\">");
/*  391 */               out.print(FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") }));
/*  392 */               out.write("</td>\n  </tr>\n</table>\n\n</form>\n</div>\n</body>\n\n");
/*  393 */               out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  394 */               out.write("\n<script src=\"/template/mobile.js\"></script>\n<script language=\"javascript\" SRC=\"/webclient/common/js/validation.js\" type=\"text/javascript\"></script>\n<script type=\"text/javascript\">\n\tif ('standalone' in navigator && !navigator.standalone && (/iphone|ipod|ipad/gi).test(navigator.platform) && (/Safari/i).test(navigator.appVersion)) {\n\t\tvar addToHomeConfig = {\n\t\t\t\tanimationIn:'drop',\t\t// Animation In\t\t//NO I18N\n\t\t\t\tanimationOut:'drop',\t// Animation Out\t//NO I18N\n\t\t\t\tlifespan:15000,\t\t\t// The popup lives 15 seconds\n\t\t\t\texpire:5,\t\t\t\t// The popup is shown only once every 5 minutes\n\t\t\t\ttouchIcon:true,\n\t\t\t\t//message:'This is a custom message. Your device is an <strong>%device</strong>. The action icon is `%icon`.'\n\t\t\t};\n\t\tdocument.write('<link rel=\"stylesheet\" href=\"/images/mobile/style/add2home.css\">');\n\t\tdocument.write('<script type=\"application/javascript\" src=\"/images/mobile/script/add2home.js\"><\\/script>');\n\t}\n\t$(document).ready(function() {\n\t\twindow.addEventListener(\"load\",function() {\n\t\t  setTimeout(function(){\n\t\t    // Hide the address bar!\n");
/*  395 */               out.write("\t\t    window.scrollTo(0, 1);\n\t\t  }, 10);\n\t\t});\n\t});\n\n\t");
/*      */               
/*      */ 
/*  398 */               if (request.getAttribute("errormessage") == null)
/*      */               {
/*      */ 
/*  401 */                 out.write("\t\n\t\tif(true){\n\t\t\tif (document.all) //IE4+ specific code\n\t\t\t{\n\t\t\t\tdocument.cookie=\"testcookie\"\n\t\t\t\tcookieEnabled=(document.cookie.indexOf(\"testcookie\")!=-1)? true : false\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tSet_Cookie('testcookie','',expires);//NO I18N\n\t\t\t\tvar tempCookie = Get_Cookie('testcookie');//NO I18N\n\t\t\t\tif(tempCookie !=null)\n\t\t\t\t{\n\t\t\t\t\tcookieEnabled=true;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\tcookieEnabled=false;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t\tif (!cookieEnabled)\n\t\t{\n\t\t\tlocation.href=\"/html/EnableCookies.html\";\n\t\t}\n\t");
/*      */               }
/*  403 */               out.write("\n</script>\n</html>\n");
/*  404 */               out.write(9);
/*  405 */               out.write(10);
/*  406 */               out.write(9);
/*  407 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  408 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  412 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  413 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */           }
/*      */           
/*  416 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  417 */           out.write(10);
/*  418 */           out.write(9);
/*      */           
/*  420 */           WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  421 */           _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  422 */           _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  424 */           _jspx_th_c_005fwhen_005f2.setTest("${!empty param.showPreLogin && param.showPreLogin=='true'}");
/*  425 */           int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  426 */           if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */             for (;;) {
/*  428 */               out.write(10);
/*  429 */               out.write(9);
/*  430 */               out.write(9);
/*  431 */               out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/*  433 */               String spversion = AMAutomaticPortChanger.getServicePackVersion() != null ? AMAutomaticPortChanger.getServicePackVersion() : "None";
/*  434 */               String whatIsNewlink = OEMUtil.getOEMString("company.whatisnew.link");
/*  435 */               String productLogo = "/images/new_login-logo.png";
/*  436 */               String productLink = FormatUtil.getString("company.loginpage.link");
/*  437 */               String tShootlink = "http://apm.manageengine.com/index.html";
/*  438 */               String faqlink = "http://www.manageengine.com/products/applications_manager/applications-management-genfaq.html" + (System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/*  439 */               pageContext.setAttribute("ISOEM", "false");
/*      */               
/*  441 */               if (OEMUtil.isOEM())
/*      */               {
/*  443 */                 pageContext.setAttribute("ISOEM", "true");
/*  444 */                 tShootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  445 */                 productLogo = OEMUtil.getOEMString("am.loginpage.logo");
/*  446 */                 productLink = OEMUtil.getOEMString("company.loginpage.link");
/*  447 */                 faqlink = OEMUtil.getOEMString("company.faq.link");
/*      */               }
/*      */               
/*      */               try
/*      */               {
/*  452 */                 if ((Constants.isMsSQLDbConnectionRestart()) && (DBQueryUtil.getDBType().equals("mssql")))
/*      */                 {
/*  454 */                   ManagedApplication.refreshDBConection();
/*  455 */                   Constants.setmsSQLDbConnectionRestart(false);
/*  456 */                   System.out.println("login.jsp:setting to false after refreshing: Constants.isMsSQLDbConnectionRestart()" + Constants.isMsSQLDbConnectionRestart());
/*      */                 }
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/*  461 */                 ex.printStackTrace();
/*      */               }
/*      */               
/*  464 */               String locale = System.getProperty("locale");
/*  465 */               String dbtype = System.getProperty("am.dbserver.type");
/*  466 */               request.setAttribute("locale", locale);
/*  467 */               if (locale.equals("vi_VN"))
/*      */               {
/*  469 */                 Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  470 */                 request.setCharacterEncoding("UTF-8");
/*      */               }
/*  472 */               else if (locale.equals("fr_FR"))
/*      */               {
/*  474 */                 Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  475 */                 request.setCharacterEncoding("UTF-8");
/*      */               }
/*      */               else
/*      */               {
/*  479 */                 out.write(10);
/*  480 */                 out.write(9);
/*  481 */                 out.write(9);
/*  482 */                 if (_jspx_meth_fmt_005fsetLocale_005f0(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                   return;
/*  484 */                 out.write(10);
/*  485 */                 out.write(9);
/*      */               }
/*      */               
/*  488 */               if ((OEMUtil.isOEM()) && (OEMUtil.Address == null))
/*      */               {
/*  490 */                 Enumeration e = request.getHeaderNames();
/*  491 */                 while (e.hasMoreElements())
/*      */                 {
/*  493 */                   String headers = (String)e.nextElement();
/*  494 */                   if ((headers != null) && (headers.equalsIgnoreCase("host")))
/*      */                   {
/*  496 */                     String[] h1 = request.getHeader(headers).toString().split(":");
/*  497 */                     OEMUtil.Address = h1[0];
/*  498 */                     OEMUtil.initializeProperties();
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*  504 */               String username = "";
/*  505 */               String password = "";
/*  506 */               String licenseType = "null";
/*  507 */               String prodVersion = "null";
/*  508 */               int usersPermitted = -1;
/*  509 */               int monPermitted = -5;
/*  510 */               String monPermittedStr = "";
/*  511 */               String addonsEnabled = "";
/*      */               
/*      */               try
/*      */               {
/*  515 */                 FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  516 */                 licenseType = fd.getCategory();
/*  517 */                 prodVersion = OEMUtil.getOEMString("product.build.number");
/*  518 */                 usersPermitted = fd.getNumberOfUsersPermitted();
/*  519 */                 monPermitted = fd.getNumberOfMonitorsPermitted();
/*  520 */                 addonsEnabled = fd.getAddonsEnabledAsString().replaceAll(",", "_") + "&expiresOn=" + fd.getDateOfLicenseExpiry();
/*  521 */                 if (monPermitted == -1)
/*      */                 {
/*  523 */                   monPermittedStr = "unlimited";
/*      */                 }
/*      */                 else
/*      */                 {
/*  527 */                   monPermittedStr = String.valueOf(monPermitted);
/*      */                 }
/*      */               }
/*      */               catch (Exception exc)
/*      */               {
/*  532 */                 exc.printStackTrace();
/*  533 */                 licenseType = "null";
/*  534 */                 prodVersion = "null";
/*  535 */                 usersPermitted = -1;
/*  536 */                 monPermittedStr = "-5";
/*      */               }
/*      */               
/*      */ 
/*  540 */               FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  541 */               request.setAttribute("showShockieMessage", Boolean.valueOf(fd.isShockieLicense()));
/*  542 */               String user = fd.getUserType();
/*  543 */               String expirydate = fd.getDateOfLicenseExpiry();
/*  544 */               long evaluationdays = fd.getEvaluationDays();
/*  545 */               String server = "local";
/*  546 */               String url = "";
/*      */               
/*      */               try
/*      */               {
/*  550 */                 server = InetAddress.getLocalHost().getHostAddress();
/*  551 */                 server = SupportZipUtil.getAddrLong(server) + "";
/*  552 */                 int length = server.length();
/*  553 */                 server = server.substring(length - 4, length);
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/*  557 */                 ee.printStackTrace();
/*      */               }
/*      */               
/*  560 */               if (user.equals("R"))
/*      */               {
/*  562 */                 user = "a";
/*  563 */                 url = "si=" + server + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*  565 */               else if (user.equals("T"))
/*      */               {
/*  567 */                 user = "b";
/*  568 */                 url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*  570 */               else if (user.equals("F"))
/*      */               {
/*  572 */                 user = "c";
/*  573 */                 url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*      */               
/*  576 */               if (dbtype != null)
/*      */               {
/*  578 */                 url = url + "&db=" + dbtype.toLowerCase();
/*      */               }
/*      */               
/*  581 */               String DATE_FORMAT = "EEE, d MMMM yyyy HH:mm:ss Z";
/*  582 */               DateFormat df = new SimpleDateFormat(DATE_FORMAT);
/*  583 */               String generatedTime = df.format(new Date());
/*      */               
/*  585 */               String usertype = fd.getUserType();
/*  586 */               long daysremaining = fd.getExpiryDate();
/*  587 */               pageContext.setAttribute("usertype", usertype);
/*  588 */               String loginmessage = "";
/*  589 */               String licenseMessage = "";
/*  590 */               String reloginMessage = FormatUtil.getString("am.webclient.plugin.reloginmessage.text");
/*  591 */               pageContext.setAttribute("showLoginMsg", "false");
/*  592 */               pageContext.setAttribute("showNewFeatures", "false");
/*  593 */               pageContext.setAttribute("showLicenseMsg", "false");
/*  594 */               if (!OEMUtil.isOEM())
/*      */               {
/*  596 */                 if ((spversion.equalsIgnoreCase("None")) || (PluginUtil.isPlugin()))
/*      */                 {
/*  598 */                   pageContext.setAttribute("showLoginMsg", "true");
/*  599 */                   if (PluginUtil.isPlugin()) {
/*  600 */                     loginmessage = FormatUtil.getString("am.webclient.plugin.defaultusernamemessage.text");
/*      */                   } else {
/*  602 */                     loginmessage = FormatUtil.getString("am.webclient.newlogin.defaultusernamemessage.text");
/*  603 */                     reloginMessage = loginmessage;
/*      */                   }
/*  605 */                   pageContext.setAttribute("isPlugin", "" + PluginUtil.isPlugin());
/*      */                 }
/*      */                 else
/*      */                 {
/*  609 */                   pageContext.setAttribute("showNewFeatures", "true");
/*      */                 }
/*      */                 
/*  612 */                 if (user.equals("a"))
/*      */                 {
/*  614 */                   if (!expirydate.equals("never"))
/*      */                   {
/*  616 */                     if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                     {
/*  618 */                       pageContext.setAttribute("showLicenseMsg", "true");
/*  619 */                       licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense2.text", new String[] { String.valueOf(daysremaining), licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*  620 */                       if (daysremaining == 0L)
/*      */                       {
/*  622 */                         licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense1.text", new String[] { licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*  627 */                 else if (user.equals("b"))
/*      */                 {
/*  629 */                   if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                   {
/*  631 */                     pageContext.setAttribute("showLicenseMsg", "true");
/*  632 */                     licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense2.text", new String[] { String.valueOf(daysremaining) });
/*  633 */                     if (daysremaining == 0L)
/*      */                     {
/*  635 */                       licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense1.text");
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/*  641 */               String buildNo = OEMUtil.getOEMString("product.build.number");
/*      */               
/*      */ 
/*  644 */               String buildType = "&nbsp;&nbsp;";
/*  645 */               String buildInfo = FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildno.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()), OEMUtil.getOEMString("product.name") });
/*      */               
/*  647 */               if ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()))
/*      */               {
/*  649 */                 buildType = EnterpriseUtil.getServerTypeDisplayName() + buildType;
/*      */                 
/*  651 */                 buildInfo = ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()) ? buildType : "") + FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildnonoprodname.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()) });
/*      */               }
/*      */               
/*      */ 
/*  655 */               String footerMsg = "";
/*      */               
/*  657 */               String copyright = FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") });
/*  658 */               if (OEMUtil.isOEM())
/*      */               {
/*  660 */                 copyright = OEMUtil.getOEMString("company.startyear") + "-" + OEMUtil.getOEMString("company.currentyear") + " " + OEMUtil.getOEMString("company.name");
/*      */               }
/*      */               else
/*      */               {
/*  664 */                 footerMsg = footerMsg + FormatUtil.getString("am.webclient.newlogin.prodinfo.text") + " ";
/*      */               }
/*      */               
/*  667 */               ArrayList domainList = ADAuthenticationUtil.getDomainList();
/*  668 */               if ((domainList != null) && (domainList.size() > 0)) {
/*  669 */                 pageContext.setAttribute("domainList", domainList);
/*      */               }
/*      */               
/*  672 */               String errormessage = (String)request.getAttribute("errormessage");
/*  673 */               pageContext.setAttribute("showErrMsg", Boolean.toString(errormessage != null));
/*      */               
/*  675 */               out.write("\n<html>\n\t<head>\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n\t\t<title>");
/*  676 */               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                 return;
/*  678 */               out.write("</title>\n\t    <link href=\"/images/prelogin.css\" rel=\"stylesheet\" type=\"text/css\">\n\t    ");
/*  679 */               out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  680 */               out.write("\n\t\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/login.js\"></SCRIPT>\n\t</head>\n\t<body onLoad=\"javascript:getImage();\">\n\t\t");
/*  681 */               if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                 return;
/*  683 */               out.write("\n\t\t<div class=\"header\" style=\"display:block\" align=\"left\" width=\"98%\">\n\t\t\t<div class=\"logo\" width=\"20%\">\n\t\t\t\t<img src=\"/images/am_logo.png\"/>\n\t\t\t\t<!-- <img src=\"/images/APM-logo.png\"/> -->\n\t\t\t</div>\n\t\t\t<div class=\"preloginDiv\">\n\t\t\t\t<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"/j_security_check\" onSubmit='return validateUser();' >\n\t\t\t\t\t<input type=\"hidden\" name=\"clienttype\" value=\"html\">\n\t\t\t    \t<input type=\"hidden\" name=\"webstart\">\n\t\t\t    \t<input type=\"hidden\" name=\"j_username\">\n\t\t\t    \t<input type=\"hidden\" name=\"ScreenWidth\" value=\"1280\">\n\t\t\t    \t<input type=\"hidden\" name=\"ScreenHeight\" value=\"709\">\n\t\t\t\t\t<p class=\"inline username\">\n\t\t\t\t\t\t<input width=\"80%\" type=\"text\" class=\"icon-user\" name=\"username\" placeholder=\"");
/*  684 */               out.print(FormatUtil.getString("am.webclient.newlogin.username.text"));
/*  685 */               out.write("\" autocomplete=\"off\" autocapitalize=\"off\">\n\t\t\t\t\t\t<span style=\"display:none\"><em id=\"firsttime-user\"><a onclick=\"showHideHint('show')\">");
/*  686 */               out.print(FormatUtil.getString("am.webclient.newlogin.firsttime.text"));
/*  687 */               out.write("</a></em></span>\n\t\t\t\t\t</p>\n\t\t\t\t\t<p class=\"inline password\">\n\t\t\t\t\t\t<input width=\"80%\" type=\"password\" class=\"icon-lock\" name=\"j_password\" placeholder=\"");
/*  688 */               out.print(FormatUtil.getString("am.webclient.newlogin.password.text"));
/*  689 */               out.write("\" autocomplete=\"off\">\n\t\t\t\t\t\t<span style=\"display:block;padding-right: 10px;\" align=\"right\">\n\t\t\t\t\t\t\t<input type=\"checkbox\" id=\"RememberMe\" name=\"remember\" onClick=\"javascript: fnRemember()\">\n\t\t\t\t\t\t\t<!-- <label for=\"RememberMe\" class=\"checkboxtext\">Remember password</label> -->\n\t\t\t\t\t\t\t<em id=\"firsttime-user\">");
/*  690 */               out.print(FormatUtil.getString("am.webclient.newlogin.rememberme.text"));
/*  691 */               out.write("</em>\n\t\t\t\t\t\t</span>\n\t\t\t\t\t</p>\n\t\t\t\t\t");
/*      */               
/*  693 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  694 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  695 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f2);
/*      */               
/*  697 */               _jspx_th_c_005fif_005f2.setTest("${domainList != null }");
/*  698 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  699 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/*  701 */                   out.write("\n\t\t\t\t\t<p class=\"inline domain\">\n\t\t\t\t\t\t<select name=\"domain\" class=\"icon-domain\">\n\t\t\t\t\t\t\t<option value='");
/*  702 */                   if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                     return;
/*  704 */                   out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/*  705 */                   out.print(FormatUtil.getString("am.webclient.loginpage.local.auth.text"));
/*  706 */                   out.write("</option>\n\t\t\t\t\t\t\t");
/*  707 */                   if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                     return;
/*  709 */                   out.write("\n\t\t\t\t\t\t</select>\n\t\t\t\t\t</p>\n\t\t\t\t    ");
/*  710 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  711 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  715 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  716 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/*  719 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  720 */               out.write("\n\t\t\t\t\t<p style=\"display:inline-block;vertical-align: top;margin-top: 10px;\">\n\t\t\t\t\t\t<input type=\"submit\" class=\"login_btn\" name=\"submit\" value=\"");
/*  721 */               out.print(FormatUtil.getString("am.webclient.login.login.text"));
/*  722 */               out.write("\">\n\t\t\t\t\t</p>\n\t\t\t\t</form>\n\t\t\t</div>\n\t\t</div>\n\t\t<div id=\"container\" align=\"center\">\n\t\t    <div id=\"mainnav\" align=\"center\" valign=\"middle\">\n\t\t    \t<a href=\"");
/*  723 */               if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                 return;
/*  725 */               out.write("\"><img src=\"/images/home.png\" class=\"home-icon\" title=\"");
/*  726 */               out.print(FormatUtil.getString("webclient.login.logoutpage.backtologin"));
/*  727 */               out.write("\"/></a> ");
/*  728 */               out.print(FormatUtil.getString("am.webclient.newlogin.logout.msg.text"));
/*  729 */               out.write("\n\t\t\t</div>\n\t\t\t<div id=\"apmOnlineContent\">\n\t\t\t\t<div id=\"onlinecontent\">\n\t\t\t\t\t<iframe name=\"onlinecontent\" src=\"");
/*  730 */               out.print(FormatUtil.getString("am.webclient.apmlogout.link"));
/*  731 */               out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/*  732 */               out.write("\" width=\"100%\" height=\"470px\" frameborder=\"0\" style=\"margin-top: -5px;\"></iframe>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div id=\"footer\" width=\"100%\" align=\"center\">\n\t\t\t<p class=\"footertext\" align=\"center\">\n\t\t\t\t<img name=\"copy\" src=\"\" style=\"display:none\"/>\n\t\t\t\t&copy 2015 <a href=\"https://www.zohocorp.com");
/*  733 */               out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/*  734 */               out.write("\" target=\"_blank\">");
/*  735 */               out.print(FormatUtil.getString("company.name"));
/*  736 */               out.write("</a> ");
/*  737 */               out.print(FormatUtil.getString("am.webclient.newlogin.allrights.text"));
/*  738 */               out.write("\n\t\t\t</p>\n\t\t</div>\n\t\t<script type=\"text/javascript\">\n\t\t\t$(document).ready(function(){\n\t\t\t\t// if(!navigator.onLine){\n\t\t\t\t// \tlocation.href='/index.do';\n\t\t\t\t// } \n\n\t\t\t\tsetTimeout(resetIframeSize, 10);\n\t\t\t});\n\n\t\t\t$(window).resize(function(){\n\t\t\t\tsetTimeout(resetIframeSize, 10);\n\t\t\t});\n\n\t\t\tfunction resetIframeSize(){\n\t\t\t\texpectedIFrameHeight = $(document).height() - $('.header ').height() - 90;\n\t\t\t\tcurrentIframeheight = $('#onlinecontent').height();\n\t\t\t\tif(expectedIFrameHeight > currentIframeheight){\n\t\t\t\t\tconsole.log(expectedIFrameHeight);\n\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height',expectedIFrameHeight+'px');\t//NO I18N \n\t\t\t\t\t$('#footer').css({'margin-top':'inherit','bottom':'0px','position':'absolute'});\t//NO I18N \n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height','470px');\t//NO I18N \n\t\t\t\t\t$('#footer').css({'margin-top':'45px','bottom':'inherit','position':'relative'});\t//No I18N \n\t\t\t\t\t//below code in this else loop will avoid the flickering issue while decreasing the iframe size.\n\t\t\t\t\texpectedIFrameHeight = $(document).height() - $('.header ').height() - 90;\n");
/*  739 */               out.write("\t\t\t\t\tcurrentIframeheight = $('#onlinecontent').height();\n\t\t\t\t\tif(expectedIFrameHeight > currentIframeheight){\n\t\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height',expectedIFrameHeight+'px');\t//NO I18N \n\t\t\t\t\t\t$('#footer').css({'margin-top':'inherit','bottom':'0px','position':'absolute'});\t//NO I18N \n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\n\t\t\tDelete_Cookie('selectedtab');//No I18N\t\n\n\t\t\tfunction validateUser()\n\t\t\t{\n\t\t\t\tif(trimAll(document.loginForm.username.value)== '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  740 */               out.print(FormatUtil.getString("am.webclient.login.jsalertforusername.text"));
/*  741 */               out.write("\");\n\t\t\t\t\tdocument.loginForm.username.focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if(trimAll(document.loginForm.j_password.value)== '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  742 */               out.print(FormatUtil.getString("am.webclient.login.jsalertforpassowrd.text"));
/*  743 */               out.write("\");\n\t\t\t\t\tdocument.loginForm.j_password.focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\tvar usr = document.loginForm.username.value;\n\t\t\t\tdocument.loginForm.j_username.value=usr;\n\n\t\t\t\tif(document.loginForm.domain){\n\t\t\t\t\tvar domainCombo = document.loginForm.domain;\n\t\t\t\t\tvar domainSel = domainCombo.options[domainCombo.selectedIndex].value;\n\t\t\t\t\tdocument.loginForm.j_username.value = domainSel +\"\\\\\"+ usr;//No i18n\n\t\t\t\t}\n\t\t\t}\n\n\t\t\t");
/*  744 */               if (PluginUtil.isPlugin()) {
/*  745 */                 out.write("\n\t\t\t$(window).one('load',function(){\t//NO I18N \n\t\t\t\tusername=$.getUrlParam('opm_user');\t//NO I18N \n\t\t\t\tif(username && window!=top){\n\t\t\t\t\tdocument.loginForm.username.value=username;\n\t\t\t\t\tdocument.loginForm.j_username.value=username;\n\t\t\t\t\tdocument.loginForm.j_password.value=username+'@opm';\t//No i18n \n\t\t\t\t\t$(\"input[name='submit']\").click();\t//NO I18N\t\n\t\t\t\t}\n\t\t\t});\n\t\t\t");
/*      */               }
/*  747 */               out.write("\n\n\t\t\tfunction getImage()\n\t\t\t{\n\t\t\t\tDelete_Cookie('am_monitorsview');//No I18N\n\t\t\t\tDelete_Cookie('am_applicationsview');//No I18N\n\t\t\t\tDelete_Cookie('am_mgview');//No I18N\n\t\t\t\tGet_Cookie('selectedtab');//NO I18N\n\n\t\t\t\tif(navigator.onLine){\n\t\t\t\t\timg=new Image();\n\t\t\t\t\timg.src=\"http://www.manageengine.com/images/copyright.gif?");
/*  748 */               out.print(url);
/*  749 */               out.print(System.getProperty("did") != null ? "&" + System.getProperty("did") : "");
/*  750 */               out.write("\"; //No I18N\t\n\t\t\t\t}\n\t\t\t\t// we are hiding this link as we dont have support for forget password yet\n\t\t\t\t// document.getElementById(\"loginForgot\").style.display=\"none\";\n\t\t\t\t");
/*  751 */               if ((!"R".equals(fd.getUserType())) && (request.getParameter("product") == null))
/*      */               {
/*  753 */                 out.write("\n\t\t\t\t\tif(navigator.onLine && img.height==16){\n\t\t\t\t\t\tdocument['copy'].src=img.src;\n\t\t\t\t\t}\n\t\t\t\t");
/*      */               }
/*  755 */               if (request.getAttribute("clearcookie") != null)
/*      */               {
/*  757 */                 username = "";
/*  758 */                 password = "";
/*      */               }
/*  760 */               out.write("\n\t\t\t\tfnSetDefault();\n\t\t\t}\n\t\t\t\n\t\t\t");
/*  761 */               if (_jspx_meth_c_005fchoose_005f3(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*      */                 return;
/*  763 */               out.write("\n\n\t\t\t$(document).ready(function() {\n\t\t\t  \t//to fix the pixel differences b/w input and dropdown boxes.\n\t\t\t  \t$('.icon-domain').width($('.icon-lock').width() + 22);\n\t\t\t  \t$(document).resize(function() {\n\t\t\t    \t$('.icon-domain').width($('.icon-lock').width() + 20);\n\t\t\t  \t});\n\t\t\t  \tif(window!=top){\n\t\t\t\t\t$('.loginhint').html('");
/*  764 */               out.print(reloginMessage);
/*  765 */               out.write("'); \t//NO I18N\n\t\t\t\t}\n\t\t\t});\n\t\n\t\t</script>\n\t</body>\n</html>\n");
/*  766 */               out.write(9);
/*  767 */               out.write(10);
/*  768 */               out.write(9);
/*  769 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  770 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  774 */           if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  775 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */           }
/*      */           
/*  778 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  779 */           out.write(10);
/*  780 */           out.write(9);
/*      */           
/*  782 */           OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  783 */           _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  784 */           _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f0);
/*  785 */           int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  786 */           if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */             for (;;) {
/*  788 */               out.write(10);
/*  789 */               out.write(9);
/*  790 */               out.write(9);
/*  791 */               out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/*  793 */               String spversion = AMAutomaticPortChanger.getServicePackVersion() != null ? AMAutomaticPortChanger.getServicePackVersion() : "None";
/*  794 */               String whatIsNewlink = OEMUtil.getOEMString("company.whatisnew.link");
/*  795 */               String productLogo = "/images/APM-logo.png";
/*      */               
/*  797 */               String forumsLink = FormatUtil.getString("company.forum.link");
/*  798 */               String blogsLink = FormatUtil.getString("company.blogs.link");
/*  799 */               String faqlink = FormatUtil.getString("company.faq.link");
/*  800 */               String tShootlink = FormatUtil.getString("company.troubleshoot.link");
/*  801 */               String supportLink = FormatUtil.getString("company.support.link");
/*  802 */               String productLink = FormatUtil.getString("company.loginpage.link");
/*      */               
/*  804 */               String getInTouchLink = FormatUtil.getString("am.webclient.newlogin.getintouch.link");
/*  805 */               String featuresLink = FormatUtil.getString("am.webclient.newlogin.features.link");
/*  806 */               String techresourcesLink = FormatUtil.getString("am.webclient.newlogin.techresources.link");
/*  807 */               String newfeaturesLink = FormatUtil.getString("am.webclient.newlogin.newfeatures.link");
/*  808 */               String quotesLink = FormatUtil.getString("am.webclient.newlogin.quotes.link");
/*  809 */               boolean ssoEnabled = AMAutomaticPortChanger.isSsoEnabled();
/*      */               
/*      */ 
/*  812 */               pageContext.setAttribute("ssoEnabled", String.valueOf(ssoEnabled));
/*  813 */               String serviceurl = request.getParameter("casredirecturl");
/*  814 */               pageContext.setAttribute("serviceurl", serviceurl);
/*  815 */               String ticket = request.getParameter("ticket");
/*      */               
/*      */ 
/*  818 */               if ((ssoEnabled) && (ticket != null))
/*      */               {
/*  820 */                 serviceurl = URLDecoder.decode(serviceurl);
/*  821 */                 response.sendRedirect(serviceurl); return;
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*  826 */               pageContext.setAttribute("ISOEM", "false");
/*      */               
/*  828 */               if (OEMUtil.isOEM())
/*      */               {
/*  830 */                 pageContext.setAttribute("ISOEM", "true");
/*  831 */                 tShootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  832 */                 productLogo = OEMUtil.getOEMString("am.loginpage.logo");
/*  833 */                 productLink = OEMUtil.getOEMString("company.loginpage.link");
/*  834 */                 faqlink = OEMUtil.getOEMString("company.faq.link");
/*      */               }
/*      */               
/*      */               try
/*      */               {
/*  839 */                 if ((Constants.isMsSQLDbConnectionRestart()) && (DBQueryUtil.getDBType().equals("mssql")))
/*      */                 {
/*  841 */                   ManagedApplication.refreshDBConection();
/*  842 */                   Constants.setmsSQLDbConnectionRestart(false);
/*  843 */                   System.out.println("login.jsp:setting to false after refreshing: Constants.isMsSQLDbConnectionRestart()" + Constants.isMsSQLDbConnectionRestart());
/*      */                 }
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/*  848 */                 ex.printStackTrace();
/*      */               }
/*      */               
/*  851 */               String locale = System.getProperty("locale");
/*  852 */               String dbtype = System.getProperty("am.dbserver.type");
/*  853 */               request.setAttribute("locale", locale);
/*  854 */               if (locale.equals("vi_VN"))
/*      */               {
/*  856 */                 Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  857 */                 request.setCharacterEncoding("UTF-8");
/*      */               }
/*  859 */               else if (locale.equals("fr_FR"))
/*      */               {
/*  861 */                 Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/*  862 */                 request.setCharacterEncoding("UTF-8");
/*      */               }
/*      */               else
/*      */               {
/*  866 */                 out.write(10);
/*  867 */                 out.write(9);
/*  868 */                 out.write(9);
/*  869 */                 if (_jspx_meth_fmt_005fsetLocale_005f1(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                   return;
/*  871 */                 out.write(10);
/*  872 */                 out.write(9);
/*      */               }
/*  874 */               out.write(10);
/*  875 */               out.write(10);
/*  876 */               out.write(9);
/*      */               
/*  878 */               if ((OEMUtil.isOEM()) && (OEMUtil.Address == null))
/*      */               {
/*  880 */                 Enumeration e = request.getHeaderNames();
/*  881 */                 while (e.hasMoreElements())
/*      */                 {
/*  883 */                   String headers = (String)e.nextElement();
/*  884 */                   if ((headers != null) && (headers.equalsIgnoreCase("host")))
/*      */                   {
/*  886 */                     String[] h1 = request.getHeader(headers).toString().split(":");
/*  887 */                     OEMUtil.Address = h1[0];
/*  888 */                     OEMUtil.initializeProperties();
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*  894 */               String username = "";
/*  895 */               String password = "";
/*  896 */               String licenseType = "null";
/*  897 */               String prodVersion = "null";
/*  898 */               int usersPermitted = -1;
/*  899 */               int monPermitted = -5;
/*  900 */               String monPermittedStr = "";
/*  901 */               String addonsEnabled = "";
/*      */               
/*      */               try
/*      */               {
/*  905 */                 FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  906 */                 licenseType = fd.getCategory();
/*  907 */                 prodVersion = OEMUtil.getOEMString("product.build.number");
/*  908 */                 usersPermitted = fd.getNumberOfUsersPermitted();
/*  909 */                 monPermitted = fd.getNumberOfMonitorsPermitted();
/*  910 */                 addonsEnabled = fd.getAddonsEnabledAsString().replaceAll(",", "_") + "&expiresOn=" + fd.getDateOfLicenseExpiry();
/*  911 */                 if (monPermitted == -1)
/*      */                 {
/*  913 */                   monPermittedStr = "unlimited";
/*      */                 }
/*      */                 else
/*      */                 {
/*  917 */                   monPermittedStr = String.valueOf(monPermitted);
/*      */                 }
/*      */               }
/*      */               catch (Exception exc)
/*      */               {
/*  922 */                 exc.printStackTrace();
/*  923 */                 licenseType = "null";
/*  924 */                 prodVersion = "null";
/*  925 */                 usersPermitted = -1;
/*  926 */                 monPermittedStr = "-5";
/*      */               }
/*      */               
/*      */ 
/*  930 */               FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  931 */               request.setAttribute("showShockieMessage", Boolean.valueOf(fd.isShockieLicense()));
/*  932 */               String user = fd.getUserType();
/*  933 */               String expirydate = fd.getDateOfLicenseExpiry();
/*  934 */               long evaluationdays = fd.getEvaluationDays();
/*  935 */               String server = "local";
/*  936 */               String url = "";
/*      */               
/*      */               try
/*      */               {
/*  940 */                 server = InetAddress.getLocalHost().getHostAddress();
/*  941 */                 server = SupportZipUtil.getAddrLong(server) + "";
/*  942 */                 int length = server.length();
/*  943 */                 server = server.substring(length - 4, length);
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/*  947 */                 ee.printStackTrace();
/*      */               }
/*      */               
/*  950 */               if (user.equals("R"))
/*      */               {
/*  952 */                 user = "a";
/*  953 */                 url = "si=" + server + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*  955 */               else if (user.equals("T"))
/*      */               {
/*  957 */                 user = "b";
/*  958 */                 url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*  960 */               else if (user.equals("F"))
/*      */               {
/*  962 */                 user = "c";
/*  963 */                 url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*      */               }
/*      */               
/*  966 */               if (dbtype != null)
/*      */               {
/*  968 */                 url = url + "&db=" + dbtype.toLowerCase();
/*      */               }
/*      */               
/*  971 */               String DATE_FORMAT = "EEE, d MMMM yyyy HH:mm:ss Z";
/*  972 */               DateFormat df = new SimpleDateFormat(DATE_FORMAT);
/*  973 */               String generatedTime = df.format(new Date());
/*      */               
/*  975 */               String usertype = fd.getUserType();
/*  976 */               long daysremaining = fd.getExpiryDate();
/*  977 */               pageContext.setAttribute("usertype", usertype);
/*  978 */               String loginmessage = "";
/*  979 */               String licenseMessage = "";
/*  980 */               String reloginMessage = FormatUtil.getString("am.webclient.plugin.reloginmessage.text");
/*  981 */               pageContext.setAttribute("showLoginMsg", "false");
/*  982 */               pageContext.setAttribute("showNewFeatures", "false");
/*  983 */               pageContext.setAttribute("showLicenseMsg", "false");
/*  984 */               if (!OEMUtil.isOEM())
/*      */               {
/*  986 */                 if ((spversion.equalsIgnoreCase("None")) || (PluginUtil.isPlugin()))
/*      */                 {
/*  988 */                   pageContext.setAttribute("showLoginMsg", "true");
/*  989 */                   if (PluginUtil.isPlugin()) {
/*  990 */                     loginmessage = FormatUtil.getString("am.webclient.plugin.defaultusernamemessage.text");
/*      */                   } else {
/*  992 */                     loginmessage = FormatUtil.getString("am.webclient.newlogin.defaultusernamemessage.text");
/*  993 */                     reloginMessage = loginmessage;
/*      */                   }
/*  995 */                   pageContext.setAttribute("isPlugin", "" + PluginUtil.isPlugin());
/*      */                 }
/*      */                 
/*  998 */                 if (user.equals("a"))
/*      */                 {
/* 1000 */                   if (!expirydate.equals("never"))
/*      */                   {
/* 1002 */                     if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                     {
/* 1004 */                       pageContext.setAttribute("showLicenseMsg", "true");
/* 1005 */                       licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense2.text", new String[] { String.valueOf(daysremaining), licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/* 1006 */                       if (daysremaining == 0L)
/*      */                       {
/* 1008 */                         licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense1.text", new String[] { licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/* 1013 */                 else if (user.equals("b"))
/*      */                 {
/* 1015 */                   if ((daysremaining < 16L) && (daysremaining >= 0L))
/*      */                   {
/* 1017 */                     pageContext.setAttribute("showLicenseMsg", "true");
/* 1018 */                     licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense2.text", new String[] { String.valueOf(daysremaining) });
/* 1019 */                     if (daysremaining == 0L)
/*      */                     {
/* 1021 */                       licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense1.text");
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/* 1027 */               String buildNo = OEMUtil.getOEMString("product.build.number");
/*      */               
/*      */ 
/* 1030 */               String buildType = "&nbsp;&nbsp;";
/* 1031 */               String buildInfo = FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildno.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()), OEMUtil.getOEMString("product.name") });
/*      */               
/* 1033 */               if ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()))
/*      */               {
/* 1035 */                 buildType = EnterpriseUtil.getServerTypeDisplayName() + buildType;
/*      */                 
/* 1037 */                 buildInfo = ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()) ? buildType : "") + FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildnonoprodname.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()) });
/*      */               }
/*      */               
/*      */ 
/* 1041 */               String footerMsg = "";
/*      */               
/* 1043 */               String copyright = FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") });
/* 1044 */               if (OEMUtil.isOEM())
/*      */               {
/* 1046 */                 copyright = OEMUtil.getOEMString("company.startyear") + "-" + OEMUtil.getOEMString("company.currentyear") + " " + OEMUtil.getOEMString("company.name");
/*      */               }
/*      */               else
/*      */               {
/* 1050 */                 footerMsg = "&copy " + OEMUtil.getOEMString("company.currentyear") + " " + FormatUtil.getString("am.webclient.newlogin.prodinfo.text");
/*      */               }
/*      */               
/* 1053 */               ArrayList domainList = ADAuthenticationUtil.getDomainList();
/* 1054 */               if ((domainList != null) && (domainList.size() > 0)) {
/* 1055 */                 pageContext.setAttribute("domainList", domainList);
/*      */               }
/*      */               
/* 1058 */               String errormessage = (String)request.getAttribute("errormessage");
/* 1059 */               String casloginerror = request.getParameter("caserror");
/* 1060 */               if (casloginerror != null) {
/* 1061 */                 request.setAttribute("errormessage", "webclient.login.loginfailed.message");
/* 1062 */                 pageContext.setAttribute("showErrMsg", "true");
/* 1063 */                 errormessage = (String)request.getAttribute("errormessage");
/*      */               } else {
/* 1065 */                 pageContext.setAttribute("showErrMsg", Boolean.toString(errormessage != null));
/*      */               }
/*      */               
/*      */ 
/* 1069 */               out.write("\n<html>\n<head>\n\t<meta charset=\"utf-8\">\n\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n\t<title>");
/* 1070 */               out.print(FormatUtil.getString("am.webclient.login.title.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 1071 */               out.write("</title>\n\t<!--[if lt IE 9]>\n\t    <style type='text/css'>\n\t\t\t.loginDiv{background:#ddd;}\n\t\t\tselect {color:#333;}\n\t\t\tinput[type=text],input[type=password]{line-height:25px}\n\t    </style>\n\t<![endif]-->\n\t<!--[if lt IE 10]>\n\t    <style type='text/css'>\n\t\t\tinput[type=text],input[type=password]{line-height:25px}\n\t\t\tselect {padding-left:4px !important}\n\t    </style>\n\t<![endif]-->\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/new_login.css\">\n\t");
/* 1072 */               out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 1073 */               out.write("\n\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/login.js\"></SCRIPT>\n\t<script src=\"/template/jquery.slides.min.js\"></script>\n\t<script type=\"text/javascript\">\n\t\tvar slidesContainer = $('div.slidesContainer');\n\t\t$(function(){\n\t\t\t$('#slides').slidesjs({\n\t\t\t\twidth: slidesContainer.width(),\n\t\t\t\theight: slidesContainer.height()*.9,\n\t\t        effect: {\n\t\t\t\t\tslide: {\n\t\t\t\t\t\tspeed: 1800\n\t\t\t\t\t}\n\t\t        },\n\t\t        pagination: {\n\t\t\t    \tactive: true,\n\t\t\t    \teffect: \"slide\"\t//NO I18N \n\t\t\t    },\n\t\t        navigation: false,\n\t\t        // start: 3,\n\t\t        play: {\n\t\t          auto: true,\n\t\t          pauseOnHover: true,\n\t\t          restartDelay: 2500\n\t\t        }\n\t\t\t});\n\t\t\t\n\t\t\t$('.slidesjs-container').height($('#slides').height());\n\t\t\t$('.slidesjs-container img').each(function() {\n\t\t\t\t$(this).height($('#slides').height()-30).width($('#slides').width());\n\t\t\t});\n\t\t\t\n\t\t\t$('div#slidesContainer').resize(function() {\n\t\t    \t$('.slidesjs-container img').each(function() {\n\t\t\t    \t$(this).height($('#slides').height()-30).width($('#slides').width());\n");
/* 1074 */               out.write("\t\t\t\t});\n\t\t\t});\n\t\t});\n\t</script>\n</head>\n<body align=\"center\" onLoad=\"javascript:getImage();\" style=\"position:absolute\">\n\t<div class=\"loginerr maxwidth\" width=\"90%\">\n\t\t");
/* 1075 */               out.print(FormatUtil.getString(errormessage));
/* 1076 */               out.write("\n\t</div>\n\t<div class=\"loginhint maxwidth\" width=\"90%\">\n\t\t");
/* 1077 */               out.print(loginmessage);
/* 1078 */               out.write("\n\t</div>\n\t<div class=\"header maxwidth\" align=\"right\">\n\t<!--<div class=\"about\" align=\"right\">\n\t\t\t<img src=\"/images/question-mark.png\" width=\"28\" height=\"28\"/>\n\t\t</div>-->\n\t\t<div class=\"menu-tab-container\" id=\"loginTopLinks\">\n\t\t\t<ul id=\"menu-tab\">\n\t\t\t\t<li><a href=\"");
/* 1079 */               out.print(forumsLink);
/* 1080 */               out.write("\" target=\"_blank\">");
/* 1081 */               out.print(FormatUtil.getString("am.webclient.newlogin.forum.text"));
/* 1082 */               out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 1083 */               out.print(blogsLink);
/* 1084 */               out.write("\" target=\"_blank\">");
/* 1085 */               out.print(FormatUtil.getString("am.webclient.support.blogs"));
/* 1086 */               out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 1087 */               out.print(faqlink);
/* 1088 */               out.write("\" target=\"_blank\">");
/* 1089 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.productinfo.link5.txt"));
/* 1090 */               out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 1091 */               out.print(tShootlink);
/* 1092 */               out.write("\" target=\"_blank\">");
/* 1093 */               out.print(FormatUtil.getString("am.webclient.newlogin.troubleshooting.text"));
/* 1094 */               out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 1095 */               out.print(supportLink);
/* 1096 */               out.write("\" target=\"_blank\">");
/* 1097 */               out.print(FormatUtil.getString("am.webclient.newlogin.support.text"));
/* 1098 */               out.write("</a></li>\n\t\t\t\t<li><a href=\"/help/index.html\" target=\"_blank\">");
/* 1099 */               out.print(FormatUtil.getString("am.webclient.newlogin.userguide.text"));
/* 1100 */               out.write("</a></li>\n\t\t\t</ul>\n\t\t</div>\n\t</div>\n\t");
/*      */               
/* 1102 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1103 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1104 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */               
/* 1106 */               _jspx_th_c_005fif_005f3.setTest("${showShockieMessage == true}");
/* 1107 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1108 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 1110 */                   out.write("\n\t\t<div style=\"background-color:#DC1717; border-radius: 5px 5px 5px 5px;box-shadow: 2px 2px 2px 2px #CCCCCC;color: #FFFFFF;font-family: Arial, Helvetica, sans-serif;font-size: 14px;padding: 10px; display: inline; display: block;\" id=\"shockiemessage\"><b>");
/* 1111 */                   out.print(FormatUtil.getString("am.webclient.newlogin.genuinelicense.message.text"));
/* 1112 */                   out.write("</b></div>\n\t");
/* 1113 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1114 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1118 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1119 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 1122 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1123 */               out.write("\n\t<div id=\"container\" class=\"maxwidth\">\n\t  \t<div class=\"slidesContainer\">\n\t  \t\t<div id=\"slides\">\n\t\t\t\t<a href=\"");
/* 1124 */               out.print(productLink);
/* 1125 */               out.write("\" target=\"_blank\"><img src=\"/images/slides/img0.png\" alt=\"Best tool for monitoring applications in physical, virtual and cloud environments\"></a>\n\t\t\t\t<a href=\"");
/* 1126 */               out.print(FormatUtil.getString("am.webclient.newlogin.img1.link"));
/* 1127 */               out.write("\" target=\"_blank\"><img src=\"/images/slides/img1.png\" alt=\"sample text\"></a>\n\t\t\t\t<a href=\"");
/* 1128 */               out.print(FormatUtil.getString("am.webclient.newlogin.img2.link"));
/* 1129 */               out.write("\" target=\"_blank\"><img src=\"/images/slides/img2.png\" alt=\"Android Native App Support\"></a>\n\t\t    </div>\n\t\t\t<div class=\"loginLogo\" style=\"display: none;\">\n\t\t\t\t<p>\n\t\t\t\t\t<b>");
/* 1130 */               out.print(OEMUtil.getOEMString("product.name"));
/* 1131 */               out.write("</b>\n\t\t\t\t\t");
/* 1132 */               out.print(FormatUtil.getString("am.webclient.newlogin.slider.text"));
/* 1133 */               out.write("\n\t\t\t\t</p>\n\t\t\t</div>\n\t  \t</div>\n\t\t<div class=\"loginContainer\" align=\"right\">\n\t\t\t<div class=\"loginDiv\" align=\"center\">\n\t\t\t\t<a href=\"");
/* 1134 */               out.print(productLink);
/* 1135 */               out.write("\"> <img src=\"");
/* 1136 */               out.print(productLogo);
/* 1137 */               out.write("\" class=\"logo\" align=\"center\" border=\"0\"></a>\n\t\t\t\t<div class=\"loginForm\" align=\"right\">\n\t\t\t");
/* 1138 */               if ((AMAutomaticPortChanger.isSsoEnabled()) && ("true".equalsIgnoreCase(System.getProperty("adminConnected")))) {
/* 1139 */                 String actionurl = "https://" + AMAutomaticPortChanger.getSSOHost() + ":" + AMAutomaticPortChanger.getSSOPort() + "/cas/login";
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1145 */                 out.write("\n        <form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"");
/* 1146 */                 out.print(actionurl);
/* 1147 */                 out.write("\" onSubmit='return validateUser();'>\n");
/*      */               } else {
/* 1149 */                 out.write("\n\t\t\t\t\t<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"/j_security_check\" onSubmit='return validateUser();' >\n\n");
/*      */               }
/* 1151 */               out.write("\n\n\t\t\t\t\t\t<input type=\"hidden\" name=\"clienttype\" value=\"html\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"webstart\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"j_username\">\n\t\t\t\t\t");
/* 1152 */               if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                 return;
/* 1154 */               out.write("\n\n\t\t\t\t    \t<input type=\"hidden\" name=\"ScreenWidth\" value=\"1280\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"ScreenHeight\" value=\"709\">\n\t\t\t\t\t\t<p class=\"float username\">\n\t\t\t\t\t\t\t");
/*      */               
/* 1156 */               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1157 */               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1158 */               _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */               
/* 1160 */               _jspx_th_c_005fif_005f5.setTest("${showLoginMsg=='true' && applicationScope.globalconfig.showgettingstarted=='true'}");
/* 1161 */               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1162 */               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                 for (;;) {
/* 1164 */                   out.write("\n\t\t\t\t\t\t\t<em id=\"firsttime-user\"><a onclick=\"showHideHint('show')\">");
/* 1165 */                   out.print(FormatUtil.getString("am.webclient.newlogin.firsttime.text"));
/* 1166 */                   out.write("</a></em>\n\t\t\t\t\t\t\t");
/* 1167 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1168 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1172 */               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1173 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */               }
/*      */               
/* 1176 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1177 */               out.write("\n\t\t\t\t\t\t\t<input width=\"80%\" type=\"text\" class=\"icon-user\" name=\"username\" placeholder=\"");
/* 1178 */               out.print(FormatUtil.getString("am.webclient.newlogin.username.text"));
/* 1179 */               out.write("\" autocomplete=\"off\" autocapitalize=\"off\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float password\">\n\t\t\t\t\t\t\t<input width=\"80%\" type=\"password\" class=\"icon-lock\" name=\"j_password\" placeholder=\"");
/* 1180 */               out.print(FormatUtil.getString("am.webclient.newlogin.password.text"));
/* 1181 */               out.write("\" autocomplete=\"off\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\">\n\t\t\t\t\t\t\t");
/*      */               
/* 1183 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1184 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1185 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */               
/* 1187 */               _jspx_th_c_005fif_005f6.setTest("${domainList != null }");
/* 1188 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1189 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/* 1191 */                   out.write("\n\t\t\t\t\t\t\t\t<select name=\"domain\" class=\"icon-domain\">\n\t\t\t\t\t\t\t\t\t<option value='");
/* 1192 */                   if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1194 */                   out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 1195 */                   out.print(FormatUtil.getString("am.webclient.loginpage.local.auth.text"));
/* 1196 */                   out.write("</option>\n\t\t\t\t\t\t\t\t\t");
/* 1197 */                   if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1199 */                   out.write("\n\t\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t    ");
/* 1200 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1201 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1205 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1206 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 1209 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1210 */               out.write("\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\">\n\t\t\t\t\t\t\t<input type=\"checkbox\" id=\"RememberMe\" name=\"remember\" onClick=\"javascript: fnRemember()\">\n\t\t\t\t\t\t\t<label for=\"RememberMe\" class=\"checkboxtext\">");
/* 1211 */               out.print(FormatUtil.getString("am.webclient.newlogin.rememberme.text"));
/* 1212 */               out.write("</label>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\"> \n\t\t\t\t\t\t\t<input type=\"submit\" class=\"login_btn\" name=\"submit\" value=\"");
/* 1213 */               out.print(FormatUtil.getString("am.webclient.login.login.text"));
/* 1214 */               out.write("\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</form>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\t\n\t</div>\n\t<div class=\"quicklinks\" align=\"center\">\n\t<div class=\"loginFeatures\">\n\t\t<div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1215 */               out.print(getInTouchLink);
/* 1216 */               out.write("\" target=\"_blank\"><img src=\"/images/mail-us.png\" title=\"");
/* 1217 */               out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.text"));
/* 1218 */               out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1219 */               out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.text"));
/* 1220 */               out.write("</h3>\n\t\t\t<p>");
/* 1221 */               out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.msg.text"));
/* 1222 */               out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1223 */               out.print(featuresLink);
/* 1224 */               out.write("\" target=\"_blank\"><img src=\"/images/features.png\" title=\"");
/* 1225 */               out.print(FormatUtil.getString("am.webclient.newlogin.features.text"));
/* 1226 */               out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1227 */               out.print(FormatUtil.getString("am.webclient.newlogin.features.text"));
/* 1228 */               out.write("</h3>\n\t\t\t<p>");
/* 1229 */               out.print(FormatUtil.getString("am.webclient.newlogin.features.msg.text"));
/* 1230 */               out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1231 */               out.print(techresourcesLink);
/* 1232 */               out.write("\" target=\"_blank\"><img src=\"/images/technical-resource.png\" title=\"");
/* 1233 */               out.print(FormatUtil.getString("am.webclient.newlogin.techresources.text"));
/* 1234 */               out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1235 */               out.print(FormatUtil.getString("am.webclient.newlogin.techresources.text"));
/* 1236 */               out.write("</h3>\n\t\t\t<p>");
/* 1237 */               out.print(FormatUtil.getString("am.webclient.newlogin.techresources.msg.text"));
/* 1238 */               out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1239 */               out.print(newfeaturesLink);
/* 1240 */               out.write("?build=");
/* 1241 */               out.print(prodVersion);
/* 1242 */               out.write("\" target=\"_blank\"><img src=\"/images/whats-new.png\" title=\"");
/* 1243 */               out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.text"));
/* 1244 */               out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1245 */               out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.text"));
/* 1246 */               out.write("</h3>\n\t\t\t<p>");
/* 1247 */               out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.msg.text"));
/* 1248 */               out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 1249 */               out.print(quotesLink);
/* 1250 */               out.write("\" target=\"_blank\"><img src=\"/images/feedback.png\" title=\"");
/* 1251 */               out.print(FormatUtil.getString("am.webclient.talkback.feedback.text"));
/* 1252 */               out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 1253 */               out.print(FormatUtil.getString("am.webclient.newlogin.quotes.text"));
/* 1254 */               out.write("</h3>\n\t\t\t<p>");
/* 1255 */               out.print(FormatUtil.getString("am.webclient.newlogin.quotes.msg.text"));
/* 1256 */               out.write("</p>\n\t    </div>\n\t</div>\n\t</div>\n    <div style=\"clear:both\"></div>\n\t<div class=\"loginFooter\">\n\t\t<p class=\"footertext\" align=\"center\">\n\t\t\t<img name=\"copy\" src=\"\" style=\"display:none\"/>\n\t\t\t");
/* 1257 */               out.write("\n\t\t\t");
/* 1258 */               if ((!DBUtil.hasGlobalConfigValue("loginFeatures")) || (DBUtil.getGlobalConfigValueasBoolean("loginFeatures"))) {
/* 1259 */                 out.write("\n\t\t\t<span style=\"display:inline-block;padding:5px;color:#666\">");
/* 1260 */                 out.print(buildInfo);
/* 1261 */                 out.write("</span><br/>\n\t\t\t");
/*      */               }
/* 1263 */               out.write("\n\t\t\t");
/* 1264 */               out.print(footerMsg);
/* 1265 */               out.write("\n\t\t</p>\n\t</div>\n</body>\n<script>\n\tDelete_Cookie('selectedtab');//No I18N\t\n\n\tfunction validateUser()\n\t{\t\t\n\t\tif(trimAll(document.loginForm.username.value)== '')\n\t\t{\n\t\t\talert(\"");
/* 1266 */               out.print(FormatUtil.getString("am.webclient.login.jsalertforusername.text"));
/* 1267 */               out.write("\");\n\t\t\tdocument.loginForm.username.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse if(trimAll(document.loginForm.j_password.value)== '')\n\t\t{\n\t\t\talert(\"");
/* 1268 */               out.print(FormatUtil.getString("am.webclient.login.jsalertforpassowrd.text"));
/* 1269 */               out.write("\");\n\t\t\tdocument.loginForm.j_password.focus();\n\t\t\treturn false;\n\t\t}\n\t\tvar usr = document.loginForm.username.value;\n\t\tdocument.loginForm.j_username.value=usr;\n\t\t");
/* 1270 */               if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                 return;
/* 1272 */               out.write(" \n\t\tif(document.loginForm.domain){\n\t\t\tvar domainCombo = document.loginForm.domain;\n\t\t\tvar domainSel = domainCombo.options[domainCombo.selectedIndex].value;\n\t\t\tdocument.loginForm.j_username.value = domainSel +\"\\\\\"+ usr;//No i18n\n\t\t\t");
/* 1273 */               if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                 return;
/* 1275 */               out.write("\n\t\t}\n\t}\n\n\t");
/* 1276 */               if (PluginUtil.isPlugin()) {
/* 1277 */                 out.write("\n\t$(window).one('load',function(){\t//NO I18N \n\t\tusername=$.getUrlParam('opm_user');\t//NO I18N \n\t\tif(username && window!=top){\n\t\t\tdocument.loginForm.username.value=username;\n\t\t\tdocument.loginForm.j_username.value=username;\n\t\t\tdocument.loginForm.j_password.value=username+'@opm';\t//No i18n \n\t\t\t$(\"input[name='submit']\").click();\t//NO I18N\n\t\t}\n\t});\n\t");
/*      */               }
/* 1279 */               out.write("\n\n\tfunction getImage()\n\t{\n\t\tDelete_Cookie('am_monitorsview');//No I18N\n\t\tDelete_Cookie('am_applicationsview');//No I18N\n\t\tDelete_Cookie('am_mgview');//No I18N\n\t\tGet_Cookie('selectedtab');//NO I18N\n\n\t\tif(navigator.onLine){\n\t\t\timg=new Image();\t//NO I18N \n\t\t\timg.src=\"https://www.manageengine.com/images/copyright.gif?");
/* 1280 */               out.print(url);
/* 1281 */               out.print(System.getProperty("did") != null ? "&" + System.getProperty("did") : "");
/* 1282 */               out.write("\"; //No I18N\t\n\t\t}\n\t\t// we are hiding this link as we dont have support for forget password yet\n\t\t// document.getElementById(\"loginForgot\").style.display=\"none\";\n\t\t");
/* 1283 */               if ((!"R".equals(fd.getUserType())) && (request.getParameter("product") == null))
/*      */               {
/* 1285 */                 out.write("\n\t\t\tif(navigator.onLine && img && img.height==16){\n\t\t\t\tdocument['copy'].src=img.src;\n\t\t\t}\n\t\t");
/*      */               }
/* 1287 */               if (request.getAttribute("clearcookie") != null)
/*      */               {
/* 1289 */                 username = "";
/* 1290 */                 password = "";
/*      */               }
/* 1292 */               out.write("\n\t\tfnSetDefault();\n\t}\n\t\n\t");
/* 1293 */               if (_jspx_meth_c_005fchoose_005f4(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/*      */                 return;
/* 1295 */               out.write("\n\n\tfunction checkAndRedirectToCookiesPage() {\t\t//NO I18N \n\t  if (document.all) //IE4+ specific code\n\t  {\n\t    document.cookie = \"testcookie\"\t\t//NO I18N \n\t    cookieEnabled = (document.cookie.indexOf(\"testcookie\") != -1) ? true : false;\t//NO I18N \n\t  }\n\t  else {\n\t    Set_Cookie('testcookie', '', expires); \t\t//No I18N \n\t    var tempCookie = Get_Cookie('testcookie'); \t//No I18N \n\t    if (tempCookie != null) {\n\t      cookieEnabled = true;\t\t//NO I18N \n\t    }\n\t    else {\n\t      cookieEnabled = false;\t//NO I18N \n\t    }\n\t  }\n\t  if (!cookieEnabled) {\n\t    location.href = \"/html/EnableCookies.html\";\t\t//No I18N \n\t  }\n\t}\n\n    function hideSlide() {\n\t\t$('#slides').hide();\n\t\t$('.loginLogo').show();\n    }\n    \n    function hideFeatures() {\n\t\t$('.loginFeatures').hide();\n    }\n    \n    function hideLoginTopLinks() {\n\t\t$('#loginTopLinks').hide();\n    }\n\n\t$(document).ready(function() {\t\t\t\t//NO I18N \n\t  \t//to fix the pixel differences b/w input and dropdown boxes.\n\t  \t$('.icon-domain').width($('.icon-lock').width() + 22);\t\t//NO I18N \n");
/* 1296 */               out.write("\t  \t$(document).resize(function() {\t\t\t\t\t\t\t\t//NO I18N \n\t    \t$('.icon-domain').width($('.icon-lock').width() + 20);\t//No I18N \n\t  \t});\n\t  \tif(window!=top){\n\t\t\t$('.loginhint').html('");
/* 1297 */               out.print(reloginMessage);
/* 1298 */               out.write("'); \t\t\t//NO I18N\n\t\t}\n\n\t\t");
/* 1299 */               if ((DBUtil.hasGlobalConfigValue("loginSlider")) && (!DBUtil.getGlobalConfigValueasBoolean("loginSlider"))) {
/* 1300 */                 out.write("\n  \t\t\thideSlide();\n  \t\t");
/*      */               }
/* 1302 */               out.write(10);
/* 1303 */               out.write(9);
/* 1304 */               out.write(9);
/* 1305 */               if ((DBUtil.hasGlobalConfigValue("loginFeatures")) && (!DBUtil.getGlobalConfigValueasBoolean("loginFeatures"))) {
/* 1306 */                 out.write("\n  \t\t\thideFeatures();\n  \t\t");
/*      */               }
/* 1308 */               out.write(10);
/* 1309 */               out.write(9);
/* 1310 */               out.write(9);
/* 1311 */               if ((DBUtil.hasGlobalConfigValue("loginTopLinks")) && (!DBUtil.getGlobalConfigValueasBoolean("loginTopLinks"))) {
/* 1312 */                 out.write("\n  \t\t\thideLoginTopLinks();\n  \t\t");
/*      */               }
/* 1314 */               out.write("\n\t});\n\t\t\n</script>\n</html>\n");
/* 1315 */               out.write(9);
/* 1316 */               out.write(10);
/* 1317 */               out.write(9);
/* 1318 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1319 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1323 */           if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1324 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */           }
/*      */           
/* 1327 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1328 */           out.write(10);
/* 1329 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1330 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1334 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1335 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */       }
/*      */       else {
/* 1338 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1339 */         out.write(32);
/* 1340 */         out.write(10);
/*      */       }
/* 1342 */     } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
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


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\formpages\Login_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */