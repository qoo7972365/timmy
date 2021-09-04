/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.appmanager.util.SupportZipUtil;
/*     */ import com.manageengine.appmanager.plugin.PluginUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.jstl.core.Config;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.SetLocaleTag;
/*     */ 
/*     */ public final class AMLogin_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  33 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  39 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  40 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody;
/*     */   
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  49 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  53 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  55 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  59 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  66 */     HttpSession session = null;
/*     */     
/*     */ 
/*  69 */     JspWriter out = null;
/*  70 */     Object page = this;
/*  71 */     JspWriter _jspx_out = null;
/*  72 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  76 */       response.setContentType("text/html");
/*  77 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  79 */       _jspx_page_context = pageContext;
/*  80 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  81 */       ServletConfig config = pageContext.getServletConfig();
/*  82 */       session = pageContext.getSession();
/*  83 */       out = pageContext.getOut();
/*  84 */       _jspx_out = out;
/*     */       
/*  86 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*  88 */       String spversion = AMAutomaticPortChanger.getServicePackVersion() != null ? AMAutomaticPortChanger.getServicePackVersion() : "None";
/*  89 */       String whatIsNewlink = OEMUtil.getOEMString("company.whatisnew.link");
/*  90 */       String productLogo = "/images/APM-logo.png";
/*     */       
/*  92 */       String forumsLink = FormatUtil.getString("company.forum.link");
/*  93 */       String blogsLink = FormatUtil.getString("company.blogs.link");
/*  94 */       String faqlink = FormatUtil.getString("company.faq.link");
/*  95 */       String tShootlink = FormatUtil.getString("company.troubleshoot.link");
/*  96 */       String supportLink = FormatUtil.getString("company.support.link");
/*  97 */       String productLink = FormatUtil.getString("company.loginpage.link");
/*     */       
/*  99 */       String getInTouchLink = FormatUtil.getString("am.webclient.newlogin.getintouch.link");
/* 100 */       String featuresLink = FormatUtil.getString("am.webclient.newlogin.features.link");
/* 101 */       String techresourcesLink = FormatUtil.getString("am.webclient.newlogin.techresources.link");
/* 102 */       String newfeaturesLink = FormatUtil.getString("am.webclient.newlogin.newfeatures.link");
/* 103 */       String quotesLink = FormatUtil.getString("am.webclient.newlogin.quotes.link");
/* 104 */       boolean ssoEnabled = AMAutomaticPortChanger.isSsoEnabled();
/*     */       
/*     */ 
/* 107 */       pageContext.setAttribute("ssoEnabled", String.valueOf(ssoEnabled));
/* 108 */       String serviceurl = request.getParameter("casredirecturl");
/* 109 */       pageContext.setAttribute("serviceurl", serviceurl);
/* 110 */       String ticket = request.getParameter("ticket");
/*     */       
/*     */ 
/* 113 */       if ((ssoEnabled) && (ticket != null))
/*     */       {
/* 115 */         serviceurl = java.net.URLDecoder.decode(serviceurl);
/* 116 */         response.sendRedirect(serviceurl);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 121 */         pageContext.setAttribute("ISOEM", "false");
/*     */         
/* 123 */         if (OEMUtil.isOEM())
/*     */         {
/* 125 */           pageContext.setAttribute("ISOEM", "true");
/* 126 */           tShootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/* 127 */           productLogo = OEMUtil.getOEMString("am.loginpage.logo");
/* 128 */           productLink = OEMUtil.getOEMString("company.loginpage.link");
/* 129 */           faqlink = OEMUtil.getOEMString("company.faq.link");
/*     */         }
/*     */         
/*     */         try
/*     */         {
/* 134 */           if ((Constants.isMsSQLDbConnectionRestart()) && (com.adventnet.appmanager.db.DBQueryUtil.getDBType().equals("mssql")))
/*     */           {
/* 136 */             ManagedApplication.refreshDBConection();
/* 137 */             Constants.setmsSQLDbConnectionRestart(false);
/* 138 */             System.out.println("login.jsp:setting to false after refreshing: Constants.isMsSQLDbConnectionRestart()" + Constants.isMsSQLDbConnectionRestart());
/*     */           }
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 143 */           ex.printStackTrace();
/*     */         }
/*     */         
/* 146 */         String locale = System.getProperty("locale");
/* 147 */         String dbtype = System.getProperty("am.dbserver.type");
/* 148 */         request.setAttribute("locale", locale);
/* 149 */         if (locale.equals("vi_VN"))
/*     */         {
/* 151 */           Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/* 152 */           request.setCharacterEncoding("UTF-8");
/*     */         }
/* 154 */         else if (locale.equals("fr_FR"))
/*     */         {
/* 156 */           Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/* 157 */           request.setCharacterEncoding("UTF-8");
/*     */         }
/*     */         else
/*     */         {
/* 161 */           out.write(10);
/* 162 */           out.write(9);
/* 163 */           out.write(9);
/* 164 */           if (_jspx_meth_fmt_005fsetLocale_005f0(_jspx_page_context))
/*     */             return;
/* 166 */           out.write(10);
/* 167 */           out.write(9);
/*     */         }
/* 169 */         out.write(10);
/* 170 */         out.write(10);
/* 171 */         out.write(9);
/*     */         
/* 173 */         if ((OEMUtil.isOEM()) && (OEMUtil.Address == null))
/*     */         {
/* 175 */           Enumeration e = request.getHeaderNames();
/* 176 */           while (e.hasMoreElements())
/*     */           {
/* 178 */             String headers = (String)e.nextElement();
/* 179 */             if ((headers != null) && (headers.equalsIgnoreCase("host")))
/*     */             {
/* 181 */               String[] h1 = request.getHeader(headers).toString().split(":");
/* 182 */               OEMUtil.Address = h1[0];
/* 183 */               OEMUtil.initializeProperties();
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 189 */         String username = "";
/* 190 */         String password = "";
/* 191 */         String licenseType = "null";
/* 192 */         String prodVersion = "null";
/* 193 */         int usersPermitted = -1;
/* 194 */         int monPermitted = -5;
/* 195 */         String monPermittedStr = "";
/* 196 */         String addonsEnabled = "";
/*     */         
/*     */         try
/*     */         {
/* 200 */           FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/* 201 */           licenseType = fd.getCategory();
/* 202 */           prodVersion = OEMUtil.getOEMString("product.build.number");
/* 203 */           usersPermitted = fd.getNumberOfUsersPermitted();
/* 204 */           monPermitted = fd.getNumberOfMonitorsPermitted();
/* 205 */           addonsEnabled = fd.getAddonsEnabledAsString().replaceAll(",", "_") + "&expiresOn=" + fd.getDateOfLicenseExpiry();
/* 206 */           if (monPermitted == -1)
/*     */           {
/* 208 */             monPermittedStr = "unlimited";
/*     */           }
/*     */           else
/*     */           {
/* 212 */             monPermittedStr = String.valueOf(monPermitted);
/*     */           }
/*     */         }
/*     */         catch (Exception exc)
/*     */         {
/* 217 */           exc.printStackTrace();
/* 218 */           licenseType = "null";
/* 219 */           prodVersion = "null";
/* 220 */           usersPermitted = -1;
/* 221 */           monPermittedStr = "-5";
/*     */         }
/*     */         
/*     */ 
/* 225 */         FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/* 226 */         request.setAttribute("showShockieMessage", Boolean.valueOf(fd.isShockieLicense()));
/* 227 */         String user = fd.getUserType();
/* 228 */         String expirydate = fd.getDateOfLicenseExpiry();
/* 229 */         long evaluationdays = fd.getEvaluationDays();
/* 230 */         String server = "local";
/* 231 */         String url = "";
/*     */         
/*     */         try
/*     */         {
/* 235 */           server = InetAddress.getLocalHost().getHostAddress();
/* 236 */           server = SupportZipUtil.getAddrLong(server) + "";
/* 237 */           int length = server.length();
/* 238 */           server = server.substring(length - 4, length);
/*     */         }
/*     */         catch (Exception ee)
/*     */         {
/* 242 */           ee.printStackTrace();
/*     */         }
/*     */         
/* 245 */         if (user.equals("R"))
/*     */         {
/* 247 */           user = "a";
/* 248 */           url = "si=" + server + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*     */         }
/* 250 */         else if (user.equals("T"))
/*     */         {
/* 252 */           user = "b";
/* 253 */           url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*     */         }
/* 255 */         else if (user.equals("F"))
/*     */         {
/* 257 */           user = "c";
/* 258 */           url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*     */         }
/*     */         
/* 261 */         if (dbtype != null)
/*     */         {
/* 263 */           url = url + "&db=" + dbtype.toLowerCase();
/*     */         }
/*     */         
/* 266 */         String DATE_FORMAT = "EEE, d MMMM yyyy HH:mm:ss Z";
/* 267 */         DateFormat df = new java.text.SimpleDateFormat(DATE_FORMAT);
/* 268 */         String generatedTime = df.format(new java.util.Date());
/*     */         
/* 270 */         String usertype = fd.getUserType();
/* 271 */         long daysremaining = fd.getExpiryDate();
/* 272 */         pageContext.setAttribute("usertype", usertype);
/* 273 */         String loginmessage = "";
/* 274 */         String licenseMessage = "";
/* 275 */         String reloginMessage = FormatUtil.getString("am.webclient.plugin.reloginmessage.text");
/* 276 */         pageContext.setAttribute("showLoginMsg", "false");
/* 277 */         pageContext.setAttribute("showNewFeatures", "false");
/* 278 */         pageContext.setAttribute("showLicenseMsg", "false");
/* 279 */         if (!OEMUtil.isOEM())
/*     */         {
/* 281 */           if ((spversion.equalsIgnoreCase("None")) || (PluginUtil.isPlugin()))
/*     */           {
/* 283 */             pageContext.setAttribute("showLoginMsg", "true");
/* 284 */             if (PluginUtil.isPlugin()) {
/* 285 */               loginmessage = FormatUtil.getString("am.webclient.plugin.defaultusernamemessage.text");
/*     */             } else {
/* 287 */               loginmessage = FormatUtil.getString("am.webclient.newlogin.defaultusernamemessage.text");
/* 288 */               reloginMessage = loginmessage;
/*     */             }
/* 290 */             pageContext.setAttribute("isPlugin", "" + PluginUtil.isPlugin());
/*     */           }
/*     */           
/* 293 */           if (user.equals("a"))
/*     */           {
/* 295 */             if (!expirydate.equals("never"))
/*     */             {
/* 297 */               if ((daysremaining < 16L) && (daysremaining >= 0L))
/*     */               {
/* 299 */                 pageContext.setAttribute("showLicenseMsg", "true");
/* 300 */                 licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense2.text", new String[] { String.valueOf(daysremaining), licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/* 301 */                 if (daysremaining == 0L)
/*     */                 {
/* 303 */                   licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense1.text", new String[] { licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 308 */           else if (user.equals("b"))
/*     */           {
/* 310 */             if ((daysremaining < 16L) && (daysremaining >= 0L))
/*     */             {
/* 312 */               pageContext.setAttribute("showLicenseMsg", "true");
/* 313 */               licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense2.text", new String[] { String.valueOf(daysremaining) });
/* 314 */               if (daysremaining == 0L)
/*     */               {
/* 316 */                 licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense1.text");
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 322 */         String buildNo = OEMUtil.getOEMString("product.build.number");
/*     */         
/*     */ 
/* 325 */         String buildType = "&nbsp;&nbsp;";
/* 326 */         String buildInfo = FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildno.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()), OEMUtil.getOEMString("product.name") });
/*     */         
/* 328 */         if ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()))
/*     */         {
/* 330 */           buildType = EnterpriseUtil.getServerTypeDisplayName() + buildType;
/*     */           
/* 332 */           buildInfo = ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()) ? buildType : "") + FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildnonoprodname.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()) });
/*     */         }
/*     */         
/*     */ 
/* 336 */         String footerMsg = "";
/*     */         
/* 338 */         String copyright = FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") });
/* 339 */         if (OEMUtil.isOEM())
/*     */         {
/* 341 */           copyright = OEMUtil.getOEMString("company.startyear") + "-" + OEMUtil.getOEMString("company.currentyear") + " " + OEMUtil.getOEMString("company.name");
/*     */         }
/*     */         else
/*     */         {
/* 345 */           footerMsg = "&copy " + OEMUtil.getOEMString("company.currentyear") + " " + FormatUtil.getString("am.webclient.newlogin.prodinfo.text");
/*     */         }
/*     */         
/* 348 */         ArrayList domainList = com.manageengine.appmanager.util.ADAuthenticationUtil.getDomainList();
/* 349 */         if ((domainList != null) && (domainList.size() > 0)) {
/* 350 */           pageContext.setAttribute("domainList", domainList);
/*     */         }
/*     */         
/* 353 */         String errormessage = (String)request.getAttribute("errormessage");
/* 354 */         String casloginerror = request.getParameter("caserror");
/* 355 */         if (casloginerror != null) {
/* 356 */           request.setAttribute("errormessage", "webclient.login.loginfailed.message");
/* 357 */           pageContext.setAttribute("showErrMsg", "true");
/* 358 */           errormessage = (String)request.getAttribute("errormessage");
/*     */         } else {
/* 360 */           pageContext.setAttribute("showErrMsg", Boolean.toString(errormessage != null));
/*     */         }
/*     */         
/*     */ 
/* 364 */         out.write("\n<html>\n<head>\n\t<meta charset=\"utf-8\">\n\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n\t<title>");
/* 365 */         out.print(FormatUtil.getString("am.webclient.login.title.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 366 */         out.write("</title>\n\t<!--[if lt IE 9]>\n\t    <style type='text/css'>\n\t\t\t.loginDiv{background:#ddd;}\n\t\t\tselect {color:#333;}\n\t\t\tinput[type=text],input[type=password]{line-height:25px}\n\t    </style>\n\t<![endif]-->\n\t<!--[if lt IE 10]>\n\t    <style type='text/css'>\n\t\t\tinput[type=text],input[type=password]{line-height:25px}\n\t\t\tselect {padding-left:4px !important}\n\t    </style>\n\t<![endif]-->\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/new_login.css\">\n\t");
/* 367 */         out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 368 */         out.write("\n\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/login.js\"></SCRIPT>\n\t<script src=\"/template/jquery.slides.min.js\"></script>\n\t<script type=\"text/javascript\">\n\t\tvar slidesContainer = $('div.slidesContainer');\n\t\t$(function(){\n\t\t\t$('#slides').slidesjs({\n\t\t\t\twidth: slidesContainer.width(),\n\t\t\t\theight: slidesContainer.height()*.9,\n\t\t        effect: {\n\t\t\t\t\tslide: {\n\t\t\t\t\t\tspeed: 1800\n\t\t\t\t\t}\n\t\t        },\n\t\t        pagination: {\n\t\t\t    \tactive: true,\n\t\t\t    \teffect: \"slide\"\t//NO I18N \n\t\t\t    },\n\t\t        navigation: false,\n\t\t        // start: 3,\n\t\t        play: {\n\t\t          auto: true,\n\t\t          pauseOnHover: true,\n\t\t          restartDelay: 2500\n\t\t        }\n\t\t\t});\n\t\t\t\n\t\t\t$('.slidesjs-container').height($('#slides').height());\n\t\t\t$('.slidesjs-container img').each(function() {\n\t\t\t\t$(this).height($('#slides').height()-30).width($('#slides').width());\n\t\t\t});\n\t\t\t\n\t\t\t$('div#slidesContainer').resize(function() {\n\t\t    \t$('.slidesjs-container img').each(function() {\n\t\t\t    \t$(this).height($('#slides').height()-30).width($('#slides').width());\n");
/* 369 */         out.write("\t\t\t\t});\n\t\t\t});\n\t\t});\n\t</script>\n</head>\n<body align=\"center\" onLoad=\"javascript:getImage();\" style=\"position:absolute\">\n\t<div class=\"loginerr maxwidth\" width=\"90%\">\n\t\t");
/* 370 */         out.print(FormatUtil.getString(errormessage));
/* 371 */         out.write("\n\t</div>\n\t<div class=\"loginhint maxwidth\" width=\"90%\">\n\t\t");
/* 372 */         out.print(loginmessage);
/* 373 */         out.write("\n\t</div>\n\t<div class=\"header maxwidth\" align=\"right\">\n\t<!--<div class=\"about\" align=\"right\">\n\t\t\t<img src=\"/images/question-mark.png\" width=\"28\" height=\"28\"/>\n\t\t</div>-->\n\t\t<div class=\"menu-tab-container\" id=\"loginTopLinks\">\n\t\t\t<ul id=\"menu-tab\">\n\t\t\t\t<li><a href=\"");
/* 374 */         out.print(forumsLink);
/* 375 */         out.write("\" target=\"_blank\">");
/* 376 */         out.print(FormatUtil.getString("am.webclient.newlogin.forum.text"));
/* 377 */         out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 378 */         out.print(blogsLink);
/* 379 */         out.write("\" target=\"_blank\">");
/* 380 */         out.print(FormatUtil.getString("am.webclient.support.blogs"));
/* 381 */         out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 382 */         out.print(faqlink);
/* 383 */         out.write("\" target=\"_blank\">");
/* 384 */         out.print(FormatUtil.getString("am.webclient.gettingstarted.productinfo.link5.txt"));
/* 385 */         out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 386 */         out.print(tShootlink);
/* 387 */         out.write("\" target=\"_blank\">");
/* 388 */         out.print(FormatUtil.getString("am.webclient.newlogin.troubleshooting.text"));
/* 389 */         out.write("</a></li>\n\t\t\t\t<li><a href=\"");
/* 390 */         out.print(supportLink);
/* 391 */         out.write("\" target=\"_blank\">");
/* 392 */         out.print(FormatUtil.getString("am.webclient.newlogin.support.text"));
/* 393 */         out.write("</a></li>\n\t\t\t\t<li><a href=\"/help/index.html\" target=\"_blank\">");
/* 394 */         out.print(FormatUtil.getString("am.webclient.newlogin.userguide.text"));
/* 395 */         out.write("</a></li>\n\t\t\t</ul>\n\t\t</div>\n\t</div>\n\t<c:if test='${showShockieMessage == true}'>\n\t\t<div style=\"background-color:#DC1717; border-radius: 5px 5px 5px 5px;box-shadow: 2px 2px 2px 2px #CCCCCC;color: #FFFFFF;font-family: Arial, Helvetica, sans-serif;font-size: 14px;padding: 10px; display: inline; display: block;\" id=\"shockiemessage\"><b>");
/* 396 */         out.print(FormatUtil.getString("am.webclient.newlogin.genuinelicense.message.text"));
/* 397 */         out.write("</b></div>\n\t</c:if>\n\t<div id=\"container\" class=\"maxwidth\">\n\t  \t<div class=\"slidesContainer\">\n\t  \t\t<div id=\"slides\">\n\t\t\t\t<a href=\"");
/* 398 */         out.print(productLink);
/* 399 */         out.write("\" target=\"_blank\"><img src=\"/images/slides/img0.png\" alt=\"Best tool for monitoring applications in physical, virtual and cloud environments\"></a>\n\t\t\t\t<a href=\"");
/* 400 */         out.print(FormatUtil.getString("am.webclient.newlogin.img1.link"));
/* 401 */         out.write("\" target=\"_blank\"><img src=\"/images/slides/img1.png\" alt=\"sample text\"></a>\n\t\t\t\t<a href=\"");
/* 402 */         out.print(FormatUtil.getString("am.webclient.newlogin.img2.link"));
/* 403 */         out.write("\" target=\"_blank\"><img src=\"/images/slides/img2.png\" alt=\"Android Native App Support\"></a>\n\t\t    </div>\n\t\t\t<div class=\"loginLogo\" style=\"display: none;\">\n\t\t\t\t<p>\n\t\t\t\t\t<b>");
/* 404 */         out.print(OEMUtil.getOEMString("product.name"));
/* 405 */         out.write("</b>\n\t\t\t\t\t");
/* 406 */         out.print(FormatUtil.getString("am.webclient.newlogin.slider.text"));
/* 407 */         out.write("\n\t\t\t\t</p>\n\t\t\t</div>\n\t  \t</div>\n\t\t<div class=\"loginContainer\" align=\"right\">\n\t\t\t<div class=\"loginDiv\" align=\"center\">\n\t\t\t\t<a href=\"");
/* 408 */         out.print(productLink);
/* 409 */         out.write("\"> <img src=\"");
/* 410 */         out.print(productLogo);
/* 411 */         out.write("\" class=\"logo\" align=\"center\" border=\"0\"></a>\n\t\t\t\t<div class=\"loginForm\" align=\"right\">\n\t\t\t");
/* 412 */         if ((AMAutomaticPortChanger.isSsoEnabled()) && ("true".equalsIgnoreCase(System.getProperty("adminConnected")))) {
/* 413 */           String actionurl = "https://" + AMAutomaticPortChanger.getSSOHost() + ":" + AMAutomaticPortChanger.getSSOPort() + "/cas/login";
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 419 */           out.write("\n        <form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"");
/* 420 */           out.print(actionurl);
/* 421 */           out.write("\" onSubmit='return validateUser();'>\n");
/*     */         } else {
/* 423 */           out.write("\n\t\t\t\t\t<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"/j_security_check\" onSubmit='return validateUser();' >\n\n");
/*     */         }
/* 425 */         out.write("\n\n\t\t\t\t\t\t<input type=\"hidden\" name=\"clienttype\" value=\"html\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"webstart\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"j_username\">\n\t\t\t\t\t<c:if test=\"${ssoEnabled}\">\n                                                        <input type=\"hidden\" name=\"auto\" value=\"true\"/>\n                                                        <input type=\"hidden\" name=\"service\" value=\"<c:out value='${serviceurl}'/>\" />\n                                                        <input type=\"hidden\" name=\"password\" id=\"password\" value=\"admin123\"/>\n                                         </c:if>\n\n\t\t\t\t    \t<input type=\"hidden\" name=\"ScreenWidth\" value=\"1280\">\n\t\t\t\t    \t<input type=\"hidden\" name=\"ScreenHeight\" value=\"709\">\n\t\t\t\t\t\t<p class=\"float username\">\n\t\t\t\t\t\t\t<c:if test=\"${showLoginMsg=='true' && applicationScope.globalconfig.showgettingstarted=='true'}\">\n\t\t\t\t\t\t\t<em id=\"firsttime-user\"><a onclick=\"showHideHint('show')\">");
/* 426 */         out.print(FormatUtil.getString("am.webclient.newlogin.firsttime.text"));
/* 427 */         out.write("</a></em>\n\t\t\t\t\t\t\t</c:if>\n\t\t\t\t\t\t\t<input width=\"80%\" type=\"text\" class=\"icon-user\" name=\"username\" placeholder=\"");
/* 428 */         out.print(FormatUtil.getString("am.webclient.newlogin.username.text"));
/* 429 */         out.write("\" autocomplete=\"off\" autocapitalize=\"off\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float password\">\n\t\t\t\t\t\t\t<input width=\"80%\" type=\"password\" class=\"icon-lock\" name=\"j_password\" placeholder=\"");
/* 430 */         out.print(FormatUtil.getString("am.webclient.newlogin.password.text"));
/* 431 */         out.write("\" autocomplete=\"off\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\">\n\t\t\t\t\t\t\t<c:if test=\"${domainList != null }\">\n\t\t\t\t\t\t\t\t<select name=\"domain\" class=\"icon-domain\">\n\t\t\t\t\t\t\t\t\t<option value='<c:out value=\"local\"/>'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 432 */         out.print(FormatUtil.getString("am.webclient.loginpage.local.auth.text"));
/* 433 */         out.write("</option>\n\t\t\t\t\t\t\t\t\t<c:forEach var=\"eachDomain\" items=\"${domainList}\" varStatus=\"status\" >\t\t\t\t\t\t\t\t\t  \n\t\t\t\t\t\t\t\t\t\t<option value='<c:out value=\"${eachDomain.value}\"/>'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value=\"${eachDomain.label}\"/></option>\n\t\t\t\t\t\t\t\t\t</c:forEach>\n\t\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t    </c:if>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\">\n\t\t\t\t\t\t\t<input type=\"checkbox\" id=\"RememberMe\" name=\"remember\" onClick=\"javascript: fnRemember()\">\n\t\t\t\t\t\t\t<label for=\"RememberMe\" class=\"checkboxtext\">");
/* 434 */         out.print(FormatUtil.getString("am.webclient.newlogin.rememberme.text"));
/* 435 */         out.write("</label>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t\t<p class=\"float\"> \n\t\t\t\t\t\t\t<input type=\"submit\" class=\"login_btn\" name=\"submit\" value=\"");
/* 436 */         out.print(FormatUtil.getString("am.webclient.login.login.text"));
/* 437 */         out.write("\">\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</form>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\t\n\t</div>\n\t<div class=\"quicklinks\" align=\"center\">\n\t<div class=\"loginFeatures\">\n\t\t<div class=\"inline\" > \n\t\t\t<a href=\"");
/* 438 */         out.print(getInTouchLink);
/* 439 */         out.write("\" target=\"_blank\"><img src=\"/images/mail-us.png\" title=\"");
/* 440 */         out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.text"));
/* 441 */         out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 442 */         out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.text"));
/* 443 */         out.write("</h3>\n\t\t\t<p>");
/* 444 */         out.print(FormatUtil.getString("am.webclient.newlogin.getintouch.msg.text"));
/* 445 */         out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 446 */         out.print(featuresLink);
/* 447 */         out.write("\" target=\"_blank\"><img src=\"/images/features.png\" title=\"");
/* 448 */         out.print(FormatUtil.getString("am.webclient.newlogin.features.text"));
/* 449 */         out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 450 */         out.print(FormatUtil.getString("am.webclient.newlogin.features.text"));
/* 451 */         out.write("</h3>\n\t\t\t<p>");
/* 452 */         out.print(FormatUtil.getString("am.webclient.newlogin.features.msg.text"));
/* 453 */         out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 454 */         out.print(techresourcesLink);
/* 455 */         out.write("\" target=\"_blank\"><img src=\"/images/technical-resource.png\" title=\"");
/* 456 */         out.print(FormatUtil.getString("am.webclient.newlogin.techresources.text"));
/* 457 */         out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 458 */         out.print(FormatUtil.getString("am.webclient.newlogin.techresources.text"));
/* 459 */         out.write("</h3>\n\t\t\t<p>");
/* 460 */         out.print(FormatUtil.getString("am.webclient.newlogin.techresources.msg.text"));
/* 461 */         out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 462 */         out.print(newfeaturesLink);
/* 463 */         out.write("?build=");
/* 464 */         out.print(prodVersion);
/* 465 */         out.write("\" target=\"_blank\"><img src=\"/images/whats-new.png\" title=\"");
/* 466 */         out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.text"));
/* 467 */         out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 468 */         out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.text"));
/* 469 */         out.write("</h3>\n\t\t\t<p>");
/* 470 */         out.print(FormatUtil.getString("am.webclient.newlogin.newfeatures.msg.text"));
/* 471 */         out.write("</p>\n\t    </div>\n\t    <div class=\"inline\" > \n\t\t\t<a href=\"");
/* 472 */         out.print(quotesLink);
/* 473 */         out.write("\" target=\"_blank\"><img src=\"/images/feedback.png\" title=\"");
/* 474 */         out.print(FormatUtil.getString("am.webclient.talkback.feedback.text"));
/* 475 */         out.write("\" width=\"60\" height=\"60\" border=\"0\"></a>\n      \t\t<h3 class=\"quick-hea\">");
/* 476 */         out.print(FormatUtil.getString("am.webclient.newlogin.quotes.text"));
/* 477 */         out.write("</h3>\n\t\t\t<p>");
/* 478 */         out.print(FormatUtil.getString("am.webclient.newlogin.quotes.msg.text"));
/* 479 */         out.write("</p>\n\t    </div>\n\t</div>\n\t</div>\n    <div style=\"clear:both\"></div>\n\t<div class=\"loginFooter\">\n\t\t<p class=\"footertext\" align=\"center\">\n\t\t\t<img name=\"copy\" src=\"\" style=\"display:none\"/>\n\t\t\t");
/* 480 */         out.write("\n\t\t\t");
/* 481 */         if ((!DBUtil.hasGlobalConfigValue("loginFeatures")) || (DBUtil.getGlobalConfigValueasBoolean("loginFeatures"))) {
/* 482 */           out.write("\n\t\t\t<span style=\"display:inline-block;padding:5px;color:#666\">");
/* 483 */           out.print(buildInfo);
/* 484 */           out.write("</span><br/>\n\t\t\t");
/*     */         }
/* 486 */         out.write("\n\t\t\t");
/* 487 */         out.print(footerMsg);
/* 488 */         out.write("\n\t\t</p>\n\t</div>\n</body>\n<script>\n\tDelete_Cookie('selectedtab');//No I18N\t\n\n\tfunction validateUser()\n\t{\t\t\n\t\tif(trimAll(document.loginForm.username.value)== '')\n\t\t{\n\t\t\talert(\"");
/* 489 */         out.print(FormatUtil.getString("am.webclient.login.jsalertforusername.text"));
/* 490 */         out.write("\");\n\t\t\tdocument.loginForm.username.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse if(trimAll(document.loginForm.j_password.value)== '')\n\t\t{\n\t\t\talert(\"");
/* 491 */         out.print(FormatUtil.getString("am.webclient.login.jsalertforpassowrd.text"));
/* 492 */         out.write("\");\n\t\t\tdocument.loginForm.j_password.focus();\n\t\t\treturn false;\n\t\t}\n\t\tvar usr = document.loginForm.username.value;\n\t\tdocument.loginForm.j_username.value=usr;\n\t\t<c:if test=\"${ssoEnabled}\">\n                        document.loginForm.password.value=document.loginForm.j_password.value;\n                                \n                        </c:if> \n\t\tif(document.loginForm.domain){\n\t\t\tvar domainCombo = document.loginForm.domain;\n\t\t\tvar domainSel = domainCombo.options[domainCombo.selectedIndex].value;\n\t\t\tdocument.loginForm.j_username.value = domainSel +\"\\\\\"+ usr;//No i18n\n\t\t\t<c:if test=\"${ssoEnabled}\">     \n                        document.loginForm.username.value=domainSel +\"\\\\\"+ usr;\n                        </c:if>\n\t\t}\n\t}\n\n\t");
/* 493 */         if (PluginUtil.isPlugin()) {
/* 494 */           out.write("\n\t$(window).one('load',function(){\t//NO I18N \n\t\tusername=$.getUrlParam('opm_user');\t//NO I18N \n\t\tif(username && window!=top){\n\t\t\tdocument.loginForm.username.value=username;\n\t\t\tdocument.loginForm.j_username.value=username;\n\t\t\tdocument.loginForm.j_password.value=username+'@opm';\t//No i18n \n\t\t\t$(\"input[name='submit']\").click();\t//NO I18N\n\t\t}\n\t});\n\t");
/*     */         }
/* 496 */         out.write("\n\n\tfunction getImage()\n\t{\n\t\tDelete_Cookie('am_monitorsview');//No I18N\n\t\tDelete_Cookie('am_applicationsview');//No I18N\n\t\tDelete_Cookie('am_mgview');//No I18N\n\t\tGet_Cookie('selectedtab');//NO I18N\n\n\t\tif(navigator.onLine){\n\t\t\timg=new Image();\t//NO I18N \n\t\t\timg.src=\"https://www.manageengine.com/images/copyright.gif?");
/* 497 */         out.print(url);
/* 498 */         out.print(System.getProperty("did") != null ? "&" + System.getProperty("did") : "");
/* 499 */         out.write("\"; //No I18N\t\n\t\t}\n\t\t// we are hiding this link as we dont have support for forget password yet\n\t\t// document.getElementById(\"loginForgot\").style.display=\"none\";\n\t\t");
/* 500 */         if ((!"R".equals(fd.getUserType())) && (request.getParameter("product") == null))
/*     */         {
/* 502 */           out.write("\n\t\t\tif(navigator.onLine && img && img.height==16){\n\t\t\t\tdocument['copy'].src=img.src;\n\t\t\t}\n\t\t");
/*     */         }
/* 504 */         if (request.getAttribute("clearcookie") != null)
/*     */         {
/* 506 */           username = "";
/* 507 */           password = "";
/*     */         }
/* 509 */         out.write("\n\t\tfnSetDefault();\n\t}\n\t\n\t<c:choose>\n\t\t<c:when test=\"${showErrMsg == 'true'}\">\n\t\t\tshowLoginErr();\t//NO I18N \n\t\t</c:when>\n\t\t<c:otherwise>\n\t\t\tcheckAndRedirectToCookiesPage();\t//NO I18N \n\t\t\t$('div.loginerr').hide();\t\t\t//NO I18N \n\t\t</c:otherwise>\n\t</c:choose>\n\n\tfunction checkAndRedirectToCookiesPage() {\t\t//NO I18N \n\t  if (document.all) //IE4+ specific code\n\t  {\n\t    document.cookie = \"testcookie\"\t\t//NO I18N \n\t    cookieEnabled = (document.cookie.indexOf(\"testcookie\") != -1) ? true : false;\t//NO I18N \n\t  }\n\t  else {\n\t    Set_Cookie('testcookie', '', expires); \t\t//No I18N \n\t    var tempCookie = Get_Cookie('testcookie'); \t//No I18N \n\t    if (tempCookie != null) {\n\t      cookieEnabled = true;\t\t//NO I18N \n\t    }\n\t    else {\n\t      cookieEnabled = false;\t//NO I18N \n\t    }\n\t  }\n\t  if (!cookieEnabled) {\n\t    location.href = \"/html/EnableCookies.html\";\t\t//No I18N \n\t  }\n\t}\n\n    function hideSlide() {\n\t\t$('#slides').hide();\n\t\t$('.loginLogo').show();\n    }\n    \n    function hideFeatures() {\n\t\t$('.loginFeatures').hide();\n    }\n");
/* 510 */         out.write("    \n    function hideLoginTopLinks() {\n\t\t$('#loginTopLinks').hide();\n    }\n\n\t$(document).ready(function() {\t\t\t\t//NO I18N \n\t  \t//to fix the pixel differences b/w input and dropdown boxes.\n\t  \t$('.icon-domain').width($('.icon-lock').width() + 22);\t\t//NO I18N \n\t  \t$(document).resize(function() {\t\t\t\t\t\t\t\t//NO I18N \n\t    \t$('.icon-domain').width($('.icon-lock').width() + 20);\t//No I18N \n\t  \t});\n\t  \tif(window!=top){\n\t\t\t$('.loginhint').html('");
/* 511 */         out.print(reloginMessage);
/* 512 */         out.write("'); \t\t\t//NO I18N\n\t\t}\n\n\t\t");
/* 513 */         if ((DBUtil.hasGlobalConfigValue("loginSlider")) && (!DBUtil.getGlobalConfigValueasBoolean("loginSlider"))) {
/* 514 */           out.write("\n  \t\t\thideSlide();\n  \t\t");
/*     */         }
/* 516 */         out.write(10);
/* 517 */         out.write(9);
/* 518 */         out.write(9);
/* 519 */         if ((DBUtil.hasGlobalConfigValue("loginFeatures")) && (!DBUtil.getGlobalConfigValueasBoolean("loginFeatures"))) {
/* 520 */           out.write("\n  \t\t\thideFeatures();\n  \t\t");
/*     */         }
/* 522 */         out.write(10);
/* 523 */         out.write(9);
/* 524 */         out.write(9);
/* 525 */         if ((DBUtil.hasGlobalConfigValue("loginTopLinks")) && (!DBUtil.getGlobalConfigValueasBoolean("loginTopLinks"))) {
/* 526 */           out.write("\n  \t\t\thideLoginTopLinks();\n  \t\t");
/*     */         }
/* 528 */         out.write("\n\t});\n\t\t\n</script>\n</html>\n");
/*     */       }
/* 530 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 531 */         out = _jspx_out;
/* 532 */         if ((out != null) && (out.getBufferSize() != 0))
/* 533 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 534 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 537 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fsetLocale_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 543 */     PageContext pageContext = _jspx_page_context;
/* 544 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 546 */     SetLocaleTag _jspx_th_fmt_005fsetLocale_005f0 = (SetLocaleTag)this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.get(SetLocaleTag.class);
/* 547 */     _jspx_th_fmt_005fsetLocale_005f0.setPageContext(_jspx_page_context);
/* 548 */     _jspx_th_fmt_005fsetLocale_005f0.setParent(null);
/*     */     
/* 550 */     _jspx_th_fmt_005fsetLocale_005f0.setValue("${locale}");
/*     */     
/* 552 */     _jspx_th_fmt_005fsetLocale_005f0.setScope("application");
/* 553 */     int _jspx_eval_fmt_005fsetLocale_005f0 = _jspx_th_fmt_005fsetLocale_005f0.doStartTag();
/* 554 */     if (_jspx_th_fmt_005fsetLocale_005f0.doEndTag() == 5) {
/* 555 */       this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f0);
/* 556 */       return true;
/*     */     }
/* 558 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f0);
/* 559 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AMLogin_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */