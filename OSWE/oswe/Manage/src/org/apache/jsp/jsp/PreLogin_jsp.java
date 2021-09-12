/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.manageengine.appmanager.plugin.PluginUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.SetLocaleTag;
/*     */ 
/*     */ public final class PreLogin_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  32 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  38 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  39 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  56 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  60 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  61 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  62 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  63 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  64 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  65 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  66 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  67 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  68 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  69 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  70 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  74 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.release();
/*  75 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  76 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  77 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  78 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*  79 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  80 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  81 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  82 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  89 */     HttpSession session = null;
/*     */     
/*     */ 
/*  92 */     JspWriter out = null;
/*  93 */     Object page = this;
/*  94 */     JspWriter _jspx_out = null;
/*  95 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  99 */       response.setContentType("text/html;charset=UTF-8");
/* 100 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/* 102 */       _jspx_page_context = pageContext;
/* 103 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 104 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 105 */       session = pageContext.getSession();
/* 106 */       out = pageContext.getOut();
/* 107 */       _jspx_out = out;
/*     */       
/* 109 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/* 111 */       String spversion = com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getServicePackVersion() != null ? com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getServicePackVersion() : "None";
/* 112 */       String whatIsNewlink = OEMUtil.getOEMString("company.whatisnew.link");
/* 113 */       String productLogo = "/images/new_login-logo.png";
/* 114 */       String productLink = FormatUtil.getString("company.loginpage.link");
/* 115 */       String tShootlink = "http://apm.manageengine.com/index.html";
/* 116 */       String faqlink = "http://www.manageengine.com/products/applications_manager/applications-management-genfaq.html" + (System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 117 */       pageContext.setAttribute("ISOEM", "false");
/*     */       
/* 119 */       if (OEMUtil.isOEM())
/*     */       {
/* 121 */         pageContext.setAttribute("ISOEM", "true");
/* 122 */         tShootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/* 123 */         productLogo = OEMUtil.getOEMString("am.loginpage.logo");
/* 124 */         productLink = OEMUtil.getOEMString("company.loginpage.link");
/* 125 */         faqlink = OEMUtil.getOEMString("company.faq.link");
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 130 */         if ((com.adventnet.appmanager.util.Constants.isMsSQLDbConnectionRestart()) && (com.adventnet.appmanager.db.DBQueryUtil.getDBType().equals("mssql")))
/*     */         {
/* 132 */           com.adventnet.appmanager.client.resourcemanagement.ManagedApplication.refreshDBConection();
/* 133 */           com.adventnet.appmanager.util.Constants.setmsSQLDbConnectionRestart(false);
/* 134 */           System.out.println("login.jsp:setting to false after refreshing: Constants.isMsSQLDbConnectionRestart()" + com.adventnet.appmanager.util.Constants.isMsSQLDbConnectionRestart());
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 139 */         ex.printStackTrace();
/*     */       }
/*     */       
/* 142 */       String locale = System.getProperty("locale");
/* 143 */       String dbtype = System.getProperty("am.dbserver.type");
/* 144 */       request.setAttribute("locale", locale);
/* 145 */       if (locale.equals("vi_VN"))
/*     */       {
/* 147 */         javax.servlet.jsp.jstl.core.Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/* 148 */         request.setCharacterEncoding("UTF-8");
/*     */       }
/* 150 */       else if (locale.equals("fr_FR"))
/*     */       {
/* 152 */         javax.servlet.jsp.jstl.core.Config.set(request.getSession().getServletContext(), "javax.servlet.jsp.jstl.fmt.locale", locale);
/* 153 */         request.setCharacterEncoding("UTF-8");
/*     */       }
/*     */       else
/*     */       {
/* 157 */         out.write(10);
/* 158 */         out.write(9);
/* 159 */         out.write(9);
/* 160 */         if (_jspx_meth_fmt_005fsetLocale_005f0(_jspx_page_context))
/*     */           return;
/* 162 */         out.write(10);
/* 163 */         out.write(9);
/*     */       }
/*     */       
/* 166 */       if ((OEMUtil.isOEM()) && (OEMUtil.Address == null))
/*     */       {
/* 168 */         Enumeration e = request.getHeaderNames();
/* 169 */         while (e.hasMoreElements())
/*     */         {
/* 171 */           String headers = (String)e.nextElement();
/* 172 */           if ((headers != null) && (headers.equalsIgnoreCase("host")))
/*     */           {
/* 174 */             String[] h1 = request.getHeader(headers).toString().split(":");
/* 175 */             OEMUtil.Address = h1[0];
/* 176 */             OEMUtil.initializeProperties();
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 182 */       String username = "";
/* 183 */       String password = "";
/* 184 */       String licenseType = "null";
/* 185 */       String prodVersion = "null";
/* 186 */       int usersPermitted = -1;
/* 187 */       int monPermitted = -5;
/* 188 */       String monPermittedStr = "";
/* 189 */       String addonsEnabled = "";
/*     */       
/*     */       try
/*     */       {
/* 193 */         FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/* 194 */         licenseType = fd.getCategory();
/* 195 */         prodVersion = OEMUtil.getOEMString("product.build.number");
/* 196 */         usersPermitted = fd.getNumberOfUsersPermitted();
/* 197 */         monPermitted = fd.getNumberOfMonitorsPermitted();
/* 198 */         addonsEnabled = fd.getAddonsEnabledAsString().replaceAll(",", "_") + "&expiresOn=" + fd.getDateOfLicenseExpiry();
/* 199 */         if (monPermitted == -1)
/*     */         {
/* 201 */           monPermittedStr = "unlimited";
/*     */         }
/*     */         else
/*     */         {
/* 205 */           monPermittedStr = String.valueOf(monPermitted);
/*     */         }
/*     */       }
/*     */       catch (Exception exc)
/*     */       {
/* 210 */         exc.printStackTrace();
/* 211 */         licenseType = "null";
/* 212 */         prodVersion = "null";
/* 213 */         usersPermitted = -1;
/* 214 */         monPermittedStr = "-5";
/*     */       }
/*     */       
/*     */ 
/* 218 */       FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/* 219 */       request.setAttribute("showShockieMessage", Boolean.valueOf(fd.isShockieLicense()));
/* 220 */       String user = fd.getUserType();
/* 221 */       String expirydate = fd.getDateOfLicenseExpiry();
/* 222 */       long evaluationdays = fd.getEvaluationDays();
/* 223 */       String server = "local";
/* 224 */       String url = "";
/*     */       
/*     */       try
/*     */       {
/* 228 */         server = java.net.InetAddress.getLocalHost().getHostAddress();
/* 229 */         server = com.adventnet.appmanager.util.SupportZipUtil.getAddrLong(server) + "";
/* 230 */         int length = server.length();
/* 231 */         server = server.substring(length - 4, length);
/*     */       }
/*     */       catch (Exception ee)
/*     */       {
/* 235 */         ee.printStackTrace();
/*     */       }
/*     */       
/* 238 */       if (user.equals("R"))
/*     */       {
/* 240 */         user = "a";
/* 241 */         url = "si=" + server + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*     */       }
/* 243 */       else if (user.equals("T"))
/*     */       {
/* 245 */         user = "b";
/* 246 */         url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*     */       }
/* 248 */       else if (user.equals("F"))
/*     */       {
/* 250 */         user = "c";
/* 251 */         url = "si=" + server + "&ry=" + user + "&fi=18&pi=1&lang=" + System.getProperty("locale");
/*     */       }
/*     */       
/* 254 */       if (dbtype != null)
/*     */       {
/* 256 */         url = url + "&db=" + dbtype.toLowerCase();
/*     */       }
/*     */       
/* 259 */       String DATE_FORMAT = "EEE, d MMMM yyyy HH:mm:ss Z";
/* 260 */       java.text.DateFormat df = new java.text.SimpleDateFormat(DATE_FORMAT);
/* 261 */       String generatedTime = df.format(new java.util.Date());
/*     */       
/* 263 */       String usertype = fd.getUserType();
/* 264 */       long daysremaining = fd.getExpiryDate();
/* 265 */       pageContext.setAttribute("usertype", usertype);
/* 266 */       String loginmessage = "";
/* 267 */       String licenseMessage = "";
/* 268 */       String reloginMessage = FormatUtil.getString("am.webclient.plugin.reloginmessage.text");
/* 269 */       pageContext.setAttribute("showLoginMsg", "false");
/* 270 */       pageContext.setAttribute("showNewFeatures", "false");
/* 271 */       pageContext.setAttribute("showLicenseMsg", "false");
/* 272 */       if (!OEMUtil.isOEM())
/*     */       {
/* 274 */         if ((spversion.equalsIgnoreCase("None")) || (PluginUtil.isPlugin()))
/*     */         {
/* 276 */           pageContext.setAttribute("showLoginMsg", "true");
/* 277 */           if (PluginUtil.isPlugin()) {
/* 278 */             loginmessage = FormatUtil.getString("am.webclient.plugin.defaultusernamemessage.text");
/*     */           } else {
/* 280 */             loginmessage = FormatUtil.getString("am.webclient.newlogin.defaultusernamemessage.text");
/* 281 */             reloginMessage = loginmessage;
/*     */           }
/* 283 */           pageContext.setAttribute("isPlugin", "" + PluginUtil.isPlugin());
/*     */         }
/*     */         else
/*     */         {
/* 287 */           pageContext.setAttribute("showNewFeatures", "true");
/*     */         }
/*     */         
/* 290 */         if (user.equals("a"))
/*     */         {
/* 292 */           if (!expirydate.equals("never"))
/*     */           {
/* 294 */             if ((daysremaining < 16L) && (daysremaining >= 0L))
/*     */             {
/* 296 */               pageContext.setAttribute("showLicenseMsg", "true");
/* 297 */               licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense2.text", new String[] { String.valueOf(daysremaining), licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/* 298 */               if (daysremaining == 0L)
/*     */               {
/* 300 */                 licenseMessage = FormatUtil.getString("am.webclient.newlogin.registerlicense1.text", new String[] { licenseType, prodVersion, String.valueOf(usersPermitted), monPermittedStr, addonsEnabled });
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 305 */         else if (user.equals("b"))
/*     */         {
/* 307 */           if ((daysremaining < 16L) && (daysremaining >= 0L))
/*     */           {
/* 309 */             pageContext.setAttribute("showLicenseMsg", "true");
/* 310 */             licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense2.text", new String[] { String.valueOf(daysremaining) });
/* 311 */             if (daysremaining == 0L)
/*     */             {
/* 313 */               licenseMessage = FormatUtil.getString("am.webclient.newlogin.triallicense1.text");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 319 */       String buildNo = OEMUtil.getOEMString("product.build.number");
/*     */       
/*     */ 
/* 322 */       String buildType = "&nbsp;&nbsp;";
/* 323 */       String buildInfo = FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildno.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()), OEMUtil.getOEMString("product.name") });
/*     */       
/* 325 */       if ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()))
/*     */       {
/* 327 */         buildType = EnterpriseUtil.getServerTypeDisplayName() + buildType;
/*     */         
/* 329 */         buildInfo = ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (EnterpriseUtil.isCloudEdition()) ? buildType : "") + FormatUtil.getString("am.webclient.login.7sp1.footermessagewithbuildnonoprodname.text", new String[] { OEMUtil.getOEMString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()) });
/*     */       }
/*     */       
/*     */ 
/* 333 */       String footerMsg = "";
/*     */       
/* 335 */       String copyright = FormatUtil.getString("am.webclient.newlogin.copyrightyear.text", new String[] { OEMUtil.getOEMString("company.name") });
/* 336 */       if (OEMUtil.isOEM())
/*     */       {
/* 338 */         copyright = OEMUtil.getOEMString("company.startyear") + "-" + OEMUtil.getOEMString("company.currentyear") + " " + OEMUtil.getOEMString("company.name");
/*     */       }
/*     */       else
/*     */       {
/* 342 */         footerMsg = footerMsg + FormatUtil.getString("am.webclient.newlogin.prodinfo.text") + " ";
/*     */       }
/*     */       
/* 345 */       java.util.ArrayList domainList = com.manageengine.appmanager.util.ADAuthenticationUtil.getDomainList();
/* 346 */       if ((domainList != null) && (domainList.size() > 0)) {
/* 347 */         pageContext.setAttribute("domainList", domainList);
/*     */       }
/*     */       
/* 350 */       String errormessage = (String)request.getAttribute("errormessage");
/* 351 */       pageContext.setAttribute("showErrMsg", Boolean.toString(errormessage != null));
/*     */       
/* 353 */       out.write("\n<html>\n\t<head>\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n\t\t<title>");
/* 354 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/* 356 */       out.write("</title>\n\t    <link href=\"/images/prelogin.css\" rel=\"stylesheet\" type=\"text/css\">\n\t    ");
/* 357 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 358 */       out.write("\n\t\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/login.js\"></SCRIPT>\n\t</head>\n\t<body onLoad=\"javascript:getImage();\">\n\t\t");
/* 359 */       if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*     */         return;
/* 361 */       out.write("\n\t\t<div class=\"header\" style=\"display:block\" align=\"left\" width=\"98%\">\n\t\t\t<div class=\"logo\" width=\"20%\">\n\t\t\t\t<img src=\"/images/am_logo.png\"/>\n\t\t\t\t<!-- <img src=\"/images/APM-logo.png\"/> -->\n\t\t\t</div>\n\t\t\t<div class=\"preloginDiv\">\n\t\t\t\t<form autocomplete=\"off\" method=\"POST\" name=\"loginForm\" action=\"/j_security_check\" onSubmit='return validateUser();' >\n\t\t\t\t\t<input type=\"hidden\" name=\"clienttype\" value=\"html\">\n\t\t\t    \t<input type=\"hidden\" name=\"webstart\">\n\t\t\t    \t<input type=\"hidden\" name=\"j_username\">\n\t\t\t    \t<input type=\"hidden\" name=\"ScreenWidth\" value=\"1280\">\n\t\t\t    \t<input type=\"hidden\" name=\"ScreenHeight\" value=\"709\">\n\t\t\t\t\t<p class=\"inline username\">\n\t\t\t\t\t\t<input width=\"80%\" type=\"text\" class=\"icon-user\" name=\"username\" placeholder=\"");
/* 362 */       out.print(FormatUtil.getString("am.webclient.newlogin.username.text"));
/* 363 */       out.write("\" autocomplete=\"off\" autocapitalize=\"off\">\n\t\t\t\t\t\t<span style=\"display:none\"><em id=\"firsttime-user\"><a onclick=\"showHideHint('show')\">");
/* 364 */       out.print(FormatUtil.getString("am.webclient.newlogin.firsttime.text"));
/* 365 */       out.write("</a></em></span>\n\t\t\t\t\t</p>\n\t\t\t\t\t<p class=\"inline password\">\n\t\t\t\t\t\t<input width=\"80%\" type=\"password\" class=\"icon-lock\" name=\"j_password\" placeholder=\"");
/* 366 */       out.print(FormatUtil.getString("am.webclient.newlogin.password.text"));
/* 367 */       out.write("\" autocomplete=\"off\">\n\t\t\t\t\t\t<span style=\"display:block;padding-right: 10px;\" align=\"right\">\n\t\t\t\t\t\t\t<input type=\"checkbox\" id=\"RememberMe\" name=\"remember\" onClick=\"javascript: fnRemember()\">\n\t\t\t\t\t\t\t<!-- <label for=\"RememberMe\" class=\"checkboxtext\">Remember password</label> -->\n\t\t\t\t\t\t\t<em id=\"firsttime-user\">");
/* 368 */       out.print(FormatUtil.getString("am.webclient.newlogin.rememberme.text"));
/* 369 */       out.write("</em>\n\t\t\t\t\t\t</span>\n\t\t\t\t\t</p>\n\t\t\t\t\t");
/*     */       
/* 371 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 372 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 373 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/* 375 */       _jspx_th_c_005fif_005f0.setTest("${domainList != null }");
/* 376 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 377 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/* 379 */           out.write("\n\t\t\t\t\t<p class=\"inline domain\">\n\t\t\t\t\t\t<select name=\"domain\" class=\"icon-domain\">\n\t\t\t\t\t\t\t<option value='");
/* 380 */           if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */             return;
/* 382 */           out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 383 */           out.print(FormatUtil.getString("am.webclient.loginpage.local.auth.text"));
/* 384 */           out.write("</option>\n\t\t\t\t\t\t\t");
/* 385 */           if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */             return;
/* 387 */           out.write("\n\t\t\t\t\t\t</select>\n\t\t\t\t\t</p>\n\t\t\t\t    ");
/* 388 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 389 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 393 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 394 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 397 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 398 */         out.write("\n\t\t\t\t\t<p style=\"display:inline-block;vertical-align: top;margin-top: 10px;\">\n\t\t\t\t\t\t<input type=\"submit\" class=\"login_btn\" name=\"submit\" value=\"");
/* 399 */         out.print(FormatUtil.getString("am.webclient.login.login.text"));
/* 400 */         out.write("\">\n\t\t\t\t\t</p>\n\t\t\t\t</form>\n\t\t\t</div>\n\t\t</div>\n\t\t<div id=\"container\" align=\"center\">\n\t\t    <div id=\"mainnav\" align=\"center\" valign=\"middle\">\n\t\t    \t<a href=\"");
/* 401 */         if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*     */           return;
/* 403 */         out.write("\"><img src=\"/images/home.png\" class=\"home-icon\" title=\"");
/* 404 */         out.print(FormatUtil.getString("webclient.login.logoutpage.backtologin"));
/* 405 */         out.write("\"/></a> ");
/* 406 */         out.print(FormatUtil.getString("am.webclient.newlogin.logout.msg.text"));
/* 407 */         out.write("\n\t\t\t</div>\n\t\t\t<div id=\"apmOnlineContent\">\n\t\t\t\t<div id=\"onlinecontent\">\n\t\t\t\t\t<iframe name=\"onlinecontent\" src=\"");
/* 408 */         out.print(FormatUtil.getString("am.webclient.apmlogout.link"));
/* 409 */         out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 410 */         out.write("\" width=\"100%\" height=\"470px\" frameborder=\"0\" style=\"margin-top: -5px;\"></iframe>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div id=\"footer\" width=\"100%\" align=\"center\">\n\t\t\t<p class=\"footertext\" align=\"center\">\n\t\t\t\t<img name=\"copy\" src=\"\" style=\"display:none\"/>\n\t\t\t\t&copy 2015 <a href=\"https://www.zohocorp.com");
/* 411 */         out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 412 */         out.write("\" target=\"_blank\">");
/* 413 */         out.print(FormatUtil.getString("company.name"));
/* 414 */         out.write("</a> ");
/* 415 */         out.print(FormatUtil.getString("am.webclient.newlogin.allrights.text"));
/* 416 */         out.write("\n\t\t\t</p>\n\t\t</div>\n\t\t<script type=\"text/javascript\">\n\t\t\t$(document).ready(function(){\n\t\t\t\t// if(!navigator.onLine){\n\t\t\t\t// \tlocation.href='/index.do';\n\t\t\t\t// } \n\n\t\t\t\tsetTimeout(resetIframeSize, 10);\n\t\t\t});\n\n\t\t\t$(window).resize(function(){\n\t\t\t\tsetTimeout(resetIframeSize, 10);\n\t\t\t});\n\n\t\t\tfunction resetIframeSize(){\n\t\t\t\texpectedIFrameHeight = $(document).height() - $('.header ').height() - 90;\n\t\t\t\tcurrentIframeheight = $('#onlinecontent').height();\n\t\t\t\tif(expectedIFrameHeight > currentIframeheight){\n\t\t\t\t\tconsole.log(expectedIFrameHeight);\n\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height',expectedIFrameHeight+'px');\t//NO I18N \n\t\t\t\t\t$('#footer').css({'margin-top':'inherit','bottom':'0px','position':'absolute'});\t//NO I18N \n\t\t\t\t}\n\t\t\t\telse{\n\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height','470px');\t//NO I18N \n\t\t\t\t\t$('#footer').css({'margin-top':'45px','bottom':'inherit','position':'relative'});\t//No I18N \n\t\t\t\t\t//below code in this else loop will avoid the flickering issue while decreasing the iframe size.\n\t\t\t\t\texpectedIFrameHeight = $(document).height() - $('.header ').height() - 90;\n");
/* 417 */         out.write("\t\t\t\t\tcurrentIframeheight = $('#onlinecontent').height();\n\t\t\t\t\tif(expectedIFrameHeight > currentIframeheight){\n\t\t\t\t\t\t$('iframe[name=\"onlinecontent\"]').attr('height',expectedIFrameHeight+'px');\t//NO I18N \n\t\t\t\t\t\t$('#footer').css({'margin-top':'inherit','bottom':'0px','position':'absolute'});\t//NO I18N \n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\n\t\t\tDelete_Cookie('selectedtab');//No I18N\t\n\n\t\t\tfunction validateUser()\n\t\t\t{\n\t\t\t\tif(trimAll(document.loginForm.username.value)== '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/* 418 */         out.print(FormatUtil.getString("am.webclient.login.jsalertforusername.text"));
/* 419 */         out.write("\");\n\t\t\t\t\tdocument.loginForm.username.focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\telse if(trimAll(document.loginForm.j_password.value)== '')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/* 420 */         out.print(FormatUtil.getString("am.webclient.login.jsalertforpassowrd.text"));
/* 421 */         out.write("\");\n\t\t\t\t\tdocument.loginForm.j_password.focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\tvar usr = document.loginForm.username.value;\n\t\t\t\tdocument.loginForm.j_username.value=usr;\n\n\t\t\t\tif(document.loginForm.domain){\n\t\t\t\t\tvar domainCombo = document.loginForm.domain;\n\t\t\t\t\tvar domainSel = domainCombo.options[domainCombo.selectedIndex].value;\n\t\t\t\t\tdocument.loginForm.j_username.value = domainSel +\"\\\\\"+ usr;//No i18n\n\t\t\t\t}\n\t\t\t}\n\n\t\t\t");
/* 422 */         if (PluginUtil.isPlugin()) {
/* 423 */           out.write("\n\t\t\t$(window).one('load',function(){\t//NO I18N \n\t\t\t\tusername=$.getUrlParam('opm_user');\t//NO I18N \n\t\t\t\tif(username && window!=top){\n\t\t\t\t\tdocument.loginForm.username.value=username;\n\t\t\t\t\tdocument.loginForm.j_username.value=username;\n\t\t\t\t\tdocument.loginForm.j_password.value=username+'@opm';\t//No i18n \n\t\t\t\t\t$(\"input[name='submit']\").click();\t//NO I18N\t\n\t\t\t\t}\n\t\t\t});\n\t\t\t");
/*     */         }
/* 425 */         out.write("\n\n\t\t\tfunction getImage()\n\t\t\t{\n\t\t\t\tDelete_Cookie('am_monitorsview');//No I18N\n\t\t\t\tDelete_Cookie('am_applicationsview');//No I18N\n\t\t\t\tDelete_Cookie('am_mgview');//No I18N\n\t\t\t\tGet_Cookie('selectedtab');//NO I18N\n\n\t\t\t\tif(navigator.onLine){\n\t\t\t\t\timg=new Image();\n\t\t\t\t\timg.src=\"http://www.manageengine.com/images/copyright.gif?");
/* 426 */         out.print(url);
/* 427 */         out.print(System.getProperty("did") != null ? "&" + System.getProperty("did") : "");
/* 428 */         out.write("\"; //No I18N\t\n\t\t\t\t}\n\t\t\t\t// we are hiding this link as we dont have support for forget password yet\n\t\t\t\t// document.getElementById(\"loginForgot\").style.display=\"none\";\n\t\t\t\t");
/* 429 */         if ((!"R".equals(fd.getUserType())) && (request.getParameter("product") == null))
/*     */         {
/* 431 */           out.write("\n\t\t\t\t\tif(navigator.onLine && img.height==16){\n\t\t\t\t\t\tdocument['copy'].src=img.src;\n\t\t\t\t\t}\n\t\t\t\t");
/*     */         }
/* 433 */         if (request.getAttribute("clearcookie") != null)
/*     */         {
/* 435 */           username = "";
/* 436 */           password = "";
/*     */         }
/* 438 */         out.write("\n\t\t\t\tfnSetDefault();\n\t\t\t}\n\t\t\t\n\t\t\t");
/* 439 */         if (_jspx_meth_c_005fchoose_005f1(_jspx_page_context))
/*     */           return;
/* 441 */         out.write("\n\n\t\t\t$(document).ready(function() {\n\t\t\t  \t//to fix the pixel differences b/w input and dropdown boxes.\n\t\t\t  \t$('.icon-domain').width($('.icon-lock').width() + 22);\n\t\t\t  \t$(document).resize(function() {\n\t\t\t    \t$('.icon-domain').width($('.icon-lock').width() + 20);\n\t\t\t  \t});\n\t\t\t  \tif(window!=top){\n\t\t\t\t\t$('.loginhint').html('");
/* 442 */         out.print(reloginMessage);
/* 443 */         out.write("'); \t//NO I18N\n\t\t\t\t}\n\t\t\t});\n\t\n\t\t</script>\n\t</body>\n</html>\n");
/*     */       }
/* 445 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 446 */         out = _jspx_out;
/* 447 */         if ((out != null) && (out.getBufferSize() != 0))
/* 448 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 449 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 452 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fsetLocale_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 458 */     PageContext pageContext = _jspx_page_context;
/* 459 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 461 */     SetLocaleTag _jspx_th_fmt_005fsetLocale_005f0 = (SetLocaleTag)this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.get(SetLocaleTag.class);
/* 462 */     _jspx_th_fmt_005fsetLocale_005f0.setPageContext(_jspx_page_context);
/* 463 */     _jspx_th_fmt_005fsetLocale_005f0.setParent(null);
/*     */     
/* 465 */     _jspx_th_fmt_005fsetLocale_005f0.setValue("${locale}");
/*     */     
/* 467 */     _jspx_th_fmt_005fsetLocale_005f0.setScope("application");
/* 468 */     int _jspx_eval_fmt_005fsetLocale_005f0 = _jspx_th_fmt_005fsetLocale_005f0.doStartTag();
/* 469 */     if (_jspx_th_fmt_005fsetLocale_005f0.doEndTag() == 5) {
/* 470 */       this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f0);
/* 471 */       return true;
/*     */     }
/* 473 */     this._005fjspx_005ftagPool_005ffmt_005fsetLocale_0026_005fvalue_005fscope_005fnobody.reuse(_jspx_th_fmt_005fsetLocale_005f0);
/* 474 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 479 */     PageContext pageContext = _jspx_page_context;
/* 480 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 482 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 483 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 484 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 486 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.newlogout.text");
/* 487 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 488 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 489 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 490 */       return true;
/*     */     }
/* 492 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 493 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 498 */     PageContext pageContext = _jspx_page_context;
/* 499 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 501 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 502 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 503 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 504 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 505 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */       for (;;) {
/* 507 */         out.write("\n\t\t\t");
/* 508 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 509 */           return true;
/* 510 */         out.write("\n\t\t    ");
/* 511 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 512 */           return true;
/* 513 */         out.write(10);
/* 514 */         out.write(9);
/* 515 */         out.write(9);
/* 516 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 517 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 521 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 522 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 523 */       return true;
/*     */     }
/* 525 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 526 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 531 */     PageContext pageContext = _jspx_page_context;
/* 532 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 534 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 535 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 536 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*     */     
/* 538 */     _jspx_th_c_005fwhen_005f0.setTest("${applicationScope.globalconfig.showgettingstarted=='true'}");
/* 539 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 540 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */       for (;;) {
/* 542 */         out.write("\n\t\t\t\t");
/* 543 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 544 */           return true;
/* 545 */         out.write("\t    \t\n\t\t    ");
/* 546 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 547 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 551 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 552 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 553 */       return true;
/*     */     }
/* 555 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 556 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 561 */     PageContext pageContext = _jspx_page_context;
/* 562 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 564 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 565 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 566 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*     */     
/* 568 */     _jspx_th_c_005fset_005f0.setVar("redirectUrl");
/*     */     
/* 570 */     _jspx_th_c_005fset_005f0.setValue("/index.do");
/* 571 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 572 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 573 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 574 */       return true;
/*     */     }
/* 576 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 577 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 582 */     PageContext pageContext = _jspx_page_context;
/* 583 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 585 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 586 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 587 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 588 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 589 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */       for (;;) {
/* 591 */         out.write("\n\t\t    \t");
/* 592 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 593 */           return true;
/* 594 */         out.write("\n\t\t    ");
/* 595 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 596 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 600 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 601 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 602 */       return true;
/*     */     }
/* 604 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 605 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 610 */     PageContext pageContext = _jspx_page_context;
/* 611 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 613 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 614 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 615 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 617 */     _jspx_th_c_005fset_005f1.setVar("redirectUrl");
/*     */     
/* 619 */     _jspx_th_c_005fset_005f1.setValue("/MyPage.do?method=viewDashBoard&toredirect=true");
/* 620 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 621 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 622 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 623 */       return true;
/*     */     }
/* 625 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 626 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 631 */     PageContext pageContext = _jspx_page_context;
/* 632 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 634 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 635 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 636 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 638 */     _jspx_th_c_005fout_005f0.setValue("local");
/* 639 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 640 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 641 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 642 */       return true;
/*     */     }
/* 644 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 645 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 650 */     PageContext pageContext = _jspx_page_context;
/* 651 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 653 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 654 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 655 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 657 */     _jspx_th_c_005fforEach_005f0.setVar("eachDomain");
/*     */     
/* 659 */     _jspx_th_c_005fforEach_005f0.setItems("${domainList}");
/*     */     
/* 661 */     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 662 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */     try {
/* 664 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 665 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */         for (;;) {
/* 667 */           out.write("\t\t\t\t\t\t\t\t\t  \n\t\t\t\t\t\t\t\t<option value='");
/* 668 */           boolean bool; if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 669 */             return true;
/* 670 */           out.write("'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 671 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 672 */             return true;
/* 673 */           out.write("</option>\n\t\t\t\t\t\t\t");
/* 674 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 675 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 679 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 680 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 683 */         int tmp239_238 = 0; int[] tmp239_236 = _jspx_push_body_count_c_005fforEach_005f0; int tmp241_240 = tmp239_236[tmp239_238];tmp239_236[tmp239_238] = (tmp241_240 - 1); if (tmp241_240 <= 0) break;
/* 684 */         out = _jspx_page_context.popBody(); }
/* 685 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 687 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 688 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */     }
/* 690 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 695 */     PageContext pageContext = _jspx_page_context;
/* 696 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 698 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 699 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 700 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 702 */     _jspx_th_c_005fout_005f1.setValue("${eachDomain.value}");
/* 703 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 704 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 705 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 706 */       return true;
/*     */     }
/* 708 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 709 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 714 */     PageContext pageContext = _jspx_page_context;
/* 715 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 717 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 718 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 719 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 721 */     _jspx_th_c_005fout_005f2.setValue("${eachDomain.label}");
/* 722 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 723 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 724 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 725 */       return true;
/*     */     }
/* 727 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 728 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 733 */     PageContext pageContext = _jspx_page_context;
/* 734 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 736 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 737 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 738 */     _jspx_th_c_005fout_005f3.setParent(null);
/*     */     
/* 740 */     _jspx_th_c_005fout_005f3.setValue("${redirectUrl}");
/* 741 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 742 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 743 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 744 */       return true;
/*     */     }
/* 746 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 747 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 752 */     PageContext pageContext = _jspx_page_context;
/* 753 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 755 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 756 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 757 */     _jspx_th_c_005fchoose_005f1.setParent(null);
/* 758 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 759 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*     */       for (;;) {
/* 761 */         out.write("\n\t\t\t\t");
/* 762 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 763 */           return true;
/* 764 */         out.write("\n\t\t\t\t");
/* 765 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 766 */           return true;
/* 767 */         out.write("\n\t\t\t");
/* 768 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 769 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 773 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 774 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 775 */       return true;
/*     */     }
/* 777 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 778 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 783 */     PageContext pageContext = _jspx_page_context;
/* 784 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 786 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 787 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 788 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*     */     
/* 790 */     _jspx_th_c_005fwhen_005f1.setTest("${showErrMsg == 'true'}");
/* 791 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 792 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */       for (;;) {
/* 794 */         out.write("\n\t\t\t\t\tcheckAndRedirectToCookiesPage();\n\t\t\t\t\tshowLoginErr();\n\t\t\t\t");
/* 795 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 796 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 800 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 801 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 802 */       return true;
/*     */     }
/* 804 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 805 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 810 */     PageContext pageContext = _jspx_page_context;
/* 811 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 813 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 814 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 815 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 816 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 817 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*     */       for (;;) {
/* 819 */         out.write("\n\t\t\t\t\t$('div.loginerr').hide();\n\t\t\t\t");
/* 820 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 821 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 825 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 826 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 827 */       return true;
/*     */     }
/* 829 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 830 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\PreLogin_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */