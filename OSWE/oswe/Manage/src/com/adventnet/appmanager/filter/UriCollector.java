/*     */ package com.adventnet.appmanager.filter;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.AMServerFramework;
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.nms.util.PureUtils;
/*     */ import com.manageengine.apminsight.apm.util.ApmInsightLicenseUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ 
/*     */ public class UriCollector
/*     */   implements Filter
/*     */ {
/*  41 */   private FilterConfig config = null;
/*  42 */   private ServletContext moServletContext = null;
/*  43 */   private static boolean access = true;
/*  44 */   private static ArrayList rows = null;
/*     */   
/*     */   public void init(FilterConfig config) throws ServletException {
/*  47 */     this.config = config;
/*  48 */     this.moServletContext = config.getServletContext();
/*     */   }
/*     */   
/*     */   public void destroy() {
/*  52 */     this.config = null;
/*     */   }
/*     */   
/*     */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/*  56 */     HttpSession session = ((HttpServletRequest)request).getSession();
/*  57 */     if ((OEMUtil.isOEM()) && (OEMUtil.getOEMString("am.personalize.remove").equalsIgnoreCase("true"))) {
/*  58 */       session.setAttribute("selectedskin", OEMUtil.getOEMString("skin.color"));
/*     */     }
/*     */     
/*  61 */     if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.getUriCollectorAccess())) {
/*  62 */       access = true;
/*     */     }
/*     */     
/*  65 */     if ((request instanceof HttpServletRequest)) {
/*  66 */       if ((session != null) && (session.getAttribute("isDelegatedAdmin") == null) && (((HttpServletRequest)request).getRemoteUser() != null)) {
/*  67 */         String loggedinUser = ((HttpServletRequest)request).getRemoteUser();
/*  68 */         session.setAttribute("isDelegatedAdmin", Boolean.valueOf(DBUtil.isDelegatedAdmin(loggedinUser)));
/*     */       }
/*  70 */       request.setAttribute("category", Constants.getCategorytype());
/*     */       
/*  72 */       String userName = (String)session.getAttribute("userName");
/*  73 */       if (userName == null) {
/*  74 */         session.setAttribute("userName", "root");
/*  75 */         AMLog.debug("~~~~~~~~~~~~~~~ Browser Information for a New User ~~~~~~~~~~~~~~~~");
/*  76 */         for (Enumeration e = ((HttpServletRequest)request).getHeaderNames(); e.hasMoreElements();) {
/*  77 */           String headername = (String)e.nextElement();
/*  78 */           if (!headername.equalsIgnoreCase("cookie")) {
/*  79 */             AMLog.debug(headername + "                : " + ((HttpServletRequest)request).getHeader(headername));
/*     */           }
/*     */         }
/*  82 */         AMLog.debug("-------------------------------------------------------------------");
/*     */       }
/*  84 */       if (((session.getAttribute("selectedskin") == null) || (session.getAttribute("customreloadperiod") == null)) && (((HttpServletRequest)request).getRemoteUser() != null)) {
/*  85 */         readStateFile((HttpServletRequest)request);
/*     */       }
/*  87 */       boolean admin = ((HttpServletRequest)request).isUserInRole("ADMIN");
/*  88 */       if (admin) {
/*  89 */         request.setAttribute("ADMIN", "true");
/*     */       }
/*  91 */       boolean demo = ((HttpServletRequest)request).isUserInRole("DEMO");
/*  92 */       if (demo) {
/*  93 */         request.setAttribute("DEMO", "true");
/*     */       }
/*     */       
/*  96 */       boolean operator = ((HttpServletRequest)request).isUserInRole("OPERATOR");
/*  97 */       if (operator) {
/*  98 */         request.setAttribute("OPERATOR", "true");
/*     */       }
/*     */       
/*     */ 
/* 102 */       String isMobile = session.getAttribute("mobile") != null ? (String)session.getAttribute("mobile") : "false";
/* 103 */       HttpServletRequest req = (HttpServletRequest)request;
/*     */       
/* 105 */       HttpServletResponse hresp = (HttpServletResponse)response;
/* 106 */       if ((((HttpServletRequest)request).isUserInRole("ENTERPRISEADMIN")) && ((req.getRequestURI().startsWith("/showresource.do")) || (req.getRequestURI().startsWith("/showCustom.do")))) {
/* 107 */         String uri = req.getRequestURI();
/* 108 */         String queryString = req.getQueryString();
/*     */         
/*     */ 
/* 111 */         String scrWidth = request.getParameter("screenWidth");
/* 112 */         Cookie objCookie = new Cookie("ScreenWidth", scrWidth);
/* 113 */         objCookie.setMaxAge(31536000);
/* 114 */         hresp.addCookie(objCookie);
/*     */       }
/* 116 */       if (isMobile.equalsIgnoreCase("true")) {
/* 117 */         String uri = req.getRequestURI();
/* 118 */         String queryString = req.getQueryString();
/* 119 */         AMLog.info("uri==>" + uri);
/* 120 */         AMLog.info("queryString==>" + queryString);
/* 121 */         session.setAttribute("selectedskin", "Blue");
/* 122 */         if ((uri.startsWith("/showapplication.do")) && (queryString.contains("method=showApplication"))) {
/* 123 */           hresp.sendRedirect(hresp.encodeRedirectURL("/mobile/DetailsView.do?method=showMGDetails&groupId=" + req.getParameter("haid")));
/* 124 */         } else if (uri.startsWith("/showresource.do")) {
/* 125 */           if (queryString.contains("method=showResourceTypes")) {
/* 126 */             hresp.sendRedirect(hresp.encodeRedirectURL("/mobile/overview.do?method=ListMonitorsForType&type=" + req.getParameter("network")));
/* 127 */           } else if (queryString.contains("method=showResourceForResourceID")) {
/* 128 */             ManagedApplication mo = new ManagedApplication();
/* 129 */             String qryToChkHAI = "Select * from AM_ManagedObject where TYPE='HAI' and RESOURCEID=" + req.getParameter("resourceid");
/* 130 */             ArrayList list = mo.getRows(qryToChkHAI);
/* 131 */             if (list.size() > 0) {
/* 132 */               hresp.sendRedirect(hresp.encodeRedirectURL("/mobile/DetailsView.do?method=showMGDetails&groupId=" + req.getParameter("resourceid")));
/*     */             } else {
/* 134 */               hresp.sendRedirect(hresp.encodeRedirectURL("/mobile/DetailsView.do?method=showMonitorDetails&resourceid=" + req.getParameter("resourceid")));
/*     */             }
/*     */           }
/* 137 */         } else if (uri.startsWith("/fault/AlarmDetails.do")) {
/* 138 */           String entity = req.getParameter("entity");
/* 139 */           String resId = entity.substring(0, entity.indexOf("_"));
/* 140 */           hresp.sendRedirect(hresp.encodeRedirectURL("/mobile/AlarmViewAction.do?method=showAlarmDetails&resourceid=" + resId));
/* 141 */         } else if ((req.getParameter("targetClient") != null) && (req.getParameter("targetClient").equalsIgnoreCase("classic"))) {
/* 142 */           session.setAttribute("mobile", "false");
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */       }
/* 150 */       else if ((req.getParameter("targetClient") != null) && (req.getParameter("targetClient").equalsIgnoreCase("mobile"))) {
/* 151 */         session.setAttribute("mobile", "true");
/* 152 */         hresp.sendRedirect(hresp.encodeRedirectURL("/mobile/HomeDetails.do?method=showHomePage"));
/*     */       }
/*     */       
/*     */ 
/* 156 */       if (operator) {
/* 157 */         String value = null;
/* 158 */         String direct = null;
/* 159 */         value = ((HttpServletRequest)request).getRequestURI();
/* 160 */         HttpServletResponse resp = (HttpServletResponse)response;
/* 161 */         if ((value != null) && (value.equals("/jsp/ThresholdActionConfiguration.jsp"))) {
/* 162 */           direct = ((HttpServletRequest)request).getParameter("redirectto");
/* 163 */           if (direct.indexOf("?") != -1) {
/* 164 */             resp.sendRedirect(direct + "&message=false");
/*     */           } else {
/* 166 */             resp.sendRedirect(direct + "?message=false");
/*     */           }
/*     */         }
/*     */         
/* 170 */         if ((value.startsWith("/showresource.do")) && 
/* 171 */           (((HttpServletRequest)request).getParameter("resourceid") != null)) {
/* 172 */           int resourceid = Integer.parseInt(((HttpServletRequest)request).getParameter("resourceid"));
/* 173 */           checkForUser(resourceid, (HttpServletRequest)request, (HttpServletResponse)response);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 178 */       String uri = (String)((HttpServletRequest)request).getAttribute("uri");
/* 179 */       if (uri == null) {
/* 180 */         String requestURL = ((HttpServletRequest)request).getRequestURI();
/* 181 */         String requestQueryString = ((HttpServletRequest)request).getQueryString();
/*     */         
/* 183 */         ((HttpServletRequest)request).setAttribute("uri", requestURL);
/* 184 */         ((HttpServletRequest)request).setAttribute("qs", requestQueryString);
/*     */       }
/*     */       
/*     */ 
/* 188 */       if ((System.getProperty("DEMOUSER") != null) && (System.getProperty("DEMOUSER").equals("true"))) {
/*     */         try {
/* 190 */           Cookie[] cook = ((HttpServletRequest)request).getCookies();
/* 191 */           String na = "";
/* 192 */           String va = "";
/* 193 */           for (int i = 0; i < cook.length; i++) {
/* 194 */             Cookie cookie = cook[i];
/* 195 */             na = cookie.getName();
/*     */             
/* 197 */             if ("am_demolanguage".equals(na)) {
/* 198 */               va = cookie.getValue();
/*     */             }
/*     */           }
/*     */           
/* 202 */           String name = Thread.currentThread().getName();
/*     */           try {
/* 204 */             String realThreadName = name.substring(0, name.indexOf("#"));
/* 205 */             Thread.currentThread().setName(realThreadName);
/*     */           }
/*     */           catch (Exception eq) {}
/*     */           
/* 209 */           if (name.startsWith("http")) {
/* 210 */             Thread.currentThread().setName(Thread.currentThread().getName() + "#" + va);
/*     */           }
/*     */         } catch (Exception e) {
/* 213 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 220 */       System.out.println(((HttpServletRequest)request).getRequestURI() + "\nAccess " + isAccess() + " To Xtra Monitors " + AMServerFramework.toXtraMonitorsPage + " Check monitor " + AMServerFramework.checkMonitor);
/* 221 */       boolean isAllowed = (FreeEditionDetails.getFreeEditionDetails().getCategory().equals("CLOUD")) && (FreeEditionDetails.getFreeEditionDetails().getUserType().equals("R"));
/* 222 */       if ((isAllowed) && (
/* 223 */         (isAccess()) || (AMServerFramework.toXtraMonitorsPage) || (AMServerFramework.checkMonitor))) {
/* 224 */         System.out.println("IS SAP ALLOWED : " + FreeEditionDetails.getFreeEditionDetails().isSAPAllowed());
/*     */         
/* 226 */         AMConnectionPool cp = AMConnectionPool.getInstance();
/* 227 */         ManagedApplication mo = new ManagedApplication();
/* 228 */         FreeEditionDetails free = FreeEditionDetails.getFreeEditionDetails();
/* 229 */         String usrtype = free.getUserType();
/* 230 */         ArrayList native_ids = Constants.getNative_ids();
/*     */         
/* 232 */         HttpServletRequest req = (HttpServletRequest)request;
/*     */         
/* 234 */         ArrayList unsupported = new ArrayList();
/* 235 */         ArrayList unsupportedEumAgents = new ArrayList();
/* 236 */         ArrayList extra = new ArrayList();
/* 237 */         if (req.getRequestURI().equals("/deleteMO.do")) {
/* 238 */           request.setAttribute("extramonitors", "set");
/*     */         }
/* 240 */         String unsupport_str = Constants.getNotSupported().trim();
/* 241 */         String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + Constants.getEUMChildString() + ")";
/* 242 */         String query2 = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type in " + unsupport_str + " and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and " + eumChildListCond + " order by creationtime desc,AM_ManagedResourceType.RESOURCETYPE";
/*     */         
/* 244 */         if ((unsupport_str != null) && (!unsupport_str.equals(""))) {
/* 245 */           unsupported = mo.getRows(query2);
/*     */         }
/* 247 */         if ((usrtype != null) && (usrtype.equals("F")))
/*     */         {
/*     */ 
/* 250 */           unsupportedEumAgents = mo.getRows("select AGENTNAME,AGENTID from AM_RBMAGENTDATA where AGENTID NOT IN(" + EnterpriseUtil.getDistributedStartResourceId() + ")");
/*     */         } else {
/* 252 */           if (!FreeEditionDetails.getFreeEditionDetails().isSAPAllowed()) {
/* 253 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type ='SAP' and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 254 */             ArrayList unsupported1 = mo.getRows(query);
/* 255 */             unsupported.addAll(unsupported1);
/* 256 */             System.out.println("SAP IS NOT ALLOWED......." + unsupported);
/*     */           }
/* 258 */           if (!FreeEditionDetails.getFreeEditionDetails().isSAPCCMSAllowed()) {
/* 259 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type ='SAP-CCMS' and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 260 */             ArrayList unsupported1 = mo.getRows(query);
/* 261 */             unsupported.addAll(unsupported1);
/* 262 */             System.out.println("SAP-CCMS IS NOT ALLOWED......." + unsupported);
/*     */           }
/* 264 */           if (!FreeEditionDetails.getFreeEditionDetails().isAS400Allowed()) {
/* 265 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type ='AS400/iSeries' and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 266 */             ArrayList unsupported1 = mo.getRows(query);
/* 267 */             unsupported.addAll(unsupported1);
/* 268 */             System.out.println("AS400 IS NOT ALLOWED......." + unsupported);
/*     */           }
/*     */           
/* 271 */           if (!FreeEditionDetails.getFreeEditionDetails().isEUMAllowed()) {
/* 272 */             eumChildListCond = "AM_ManagedObject.resourceid IN (" + Constants.getEUMChildWOLocalString() + ")";
/* 273 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where " + eumChildListCond + " and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by creationtime desc,AM_ManagedResourceType.RESOURCETYPE";
/*     */             
/* 275 */             ArrayList unsupported1 = mo.getRows(query);
/* 276 */             unsupported.addAll(unsupported1);
/* 277 */             query = "select * from AM_RBMAGENTDATA";
/* 278 */             unsupportedEumAgents = mo.getRows("select distinct(AGENTNAME), AGENTID from AM_RBMAGENTDATA where AGENTID NOT IN(" + EnterpriseUtil.getDistributedStartResourceId() + ")");
/* 279 */             AMLog.debug("EUM IS NOT ALLOWED......." + unsupportedEumAgents);
/*     */           }
/* 281 */           else if (FreeEditionDetails.getFreeEditionDetails().getUserType().equals("T"))
/*     */           {
/* 283 */             System.out.println("Trail Version Support unlimited RBM Agents");
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 290 */           if (!FreeEditionDetails.getFreeEditionDetails().isMqSeries()) {
/* 291 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type in ('WebsphereMQ','WebsphereMB') and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 292 */             ArrayList unsupported1 = mo.getRows(query);
/* 293 */             unsupported.addAll(unsupported1);
/* 294 */             System.out.println("WebSphereMQ is not allowed......" + unsupported);
/*     */           }
/* 296 */           if (!FreeEditionDetails.getFreeEditionDetails().isSharePoint()) {
/* 297 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type ='OfficeSharePointServer' and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 298 */             ArrayList unsupported1 = mo.getRows(query);
/* 299 */             unsupported.addAll(unsupported1);
/* 300 */             System.out.println("Office Share Point IS NOT ALLOWED......." + unsupported);
/*     */           }
/* 302 */           if (!FreeEditionDetails.getFreeEditionDetails().isWebTransaction()) {
/* 303 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type ='WTA' and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 304 */             ArrayList unsupported1 = mo.getRows(query);
/* 305 */             unsupported.addAll(unsupported1);
/* 306 */             System.out.println("WTA IS NOT ALLOWED......." + unsupported);
/*     */           }
/*     */           
/*     */ 
/* 310 */           checkApmInsightLicense(unsupported);
/*     */           
/* 312 */           if (!FreeEditionDetails.getFreeEditionDetails().isOracleEBSAllowed()) {
/* 313 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type ='OracleEBS' and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 314 */             ArrayList unsupported1 = mo.getRows(query);
/* 315 */             unsupported.addAll(unsupported1);
/* 316 */             System.out.println("OracleEBS IS NOT ALLOWED......." + unsupported);
/*     */           }
/* 318 */           if (!FreeEditionDetails.getFreeEditionDetails().isVMWare()) {
/* 319 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type ='VMWare ESX/ESXi' and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 320 */             ArrayList unsupported1 = mo.getRows(query);
/* 321 */             unsupported.addAll(unsupported1);
/* 322 */             System.out.println("VMWARE IS NOT ALLOWED......." + unsupported);
/*     */           }
/* 324 */           if (!FreeEditionDetails.getFreeEditionDetails().isHyperV()) {
/* 325 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type ='Hyper-V-Server' and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 326 */             ArrayList unsupported1 = mo.getRows(query);
/* 327 */             unsupported.addAll(unsupported1);
/* 328 */             AMLog.debug("Hyper-V Server IS NOT ALLOWED......." + unsupported);
/*     */           }
/* 330 */           if (!FreeEditionDetails.getFreeEditionDetails().isAmazon()) {
/* 331 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type ='Amazon' and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 332 */             ArrayList unsupported1 = mo.getRows(query);
/* 333 */             unsupported.addAll(unsupported1);
/*     */           }
/*     */           
/* 336 */           if (!FreeEditionDetails.getFreeEditionDetails().isSiebelAllowed()) {
/* 337 */             String query = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.type ='SiebelEnterpriseServer' and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE order by AM_ManagedResourceType.RESOURCETYPE";
/* 338 */             ArrayList unsupported1 = mo.getRows(query);
/* 339 */             unsupported.addAll(unsupported1);
/* 340 */             AMLog.debug("Siebel CRM IS NOT ALLOWED......." + unsupported);
/*     */           }
/* 342 */           if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!free.isOpmanagerConnectorPresent())))) {
/* 343 */             System.out.println("Opmanager Connector Present : " + free.isOpmanagerConnectorPresent());
/* 344 */             String opmanidquery = "select ID from AM_INTEGRATEDPRODUCTS  where PRODUCT_NAME='OpManager'";
/* 345 */             ArrayList idlist = mo.getRows(opmanidquery);
/* 346 */             if (idlist.size() > 0) {
/* 347 */               String opmanid = (String)((ArrayList)idlist.get(0)).get(0);
/* 348 */               String deleteExtDevices = "delete from ExternalDeviceDetails where PRODUCTID =" + opmanid;
/* 349 */               AMConnectionPool.executeUpdateStmt(deleteExtDevices);
/* 350 */               String extquery = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH  from AM_AssociatedExtDevices inner join AM_ManagedObject on AM_AssociatedExtDevices.RESID =AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=" + opmanid + "  left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID left outer join AM_ManagedResourceType on  AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE  where  AM_UnManagedNodes.resid is null ";
/* 351 */               ArrayList unsupported2 = mo.getRows(extquery);
/* 352 */               unsupported.addAll(unsupported2);
/* 353 */               System.out.println("Opmanager unsupported2: " + unsupported2);
/*     */             }
/*     */           }
/* 356 */           if ((usrtype != null) && ((usrtype.equals("F")) || ((usrtype.equals("R")) && (!free.isOpstorConnectorPresent())))) {
/* 357 */             System.out.println("OpStor Connector Present : " + free.isOpstorConnectorPresent());
/* 358 */             String opstoridquery = "select ID from AM_INTEGRATEDPRODUCTS  where PRODUCT_NAME='OpStor'";
/* 359 */             ArrayList idlist = mo.getRows(opstoridquery);
/* 360 */             if (idlist.size() > 0) {
/* 361 */               String opstorid = (String)((ArrayList)idlist.get(0)).get(0);
/* 362 */               String deleteExtDevices = "delete from ExternalDeviceDetails where PRODUCTID =" + opstorid;
/* 363 */               AMConnectionPool.executeUpdateStmt(deleteExtDevices);
/* 364 */               String extquery = "select AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH  from AM_AssociatedExtDevices inner join AM_ManagedObject on AM_AssociatedExtDevices.RESID =AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=" + opstorid + "  left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID left outer join AM_ManagedResourceType on  AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE  where  AM_UnManagedNodes.resid is null  ";
/* 365 */               ArrayList unsupported3 = mo.getRows(extquery);
/* 366 */               unsupported.addAll(unsupported3);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 371 */         if ((unsupported != null) && (unsupported.size() <= 0) && (unsupportedEumAgents != null) && (unsupportedEumAgents.size() <= 0)) {
/* 372 */           int totalnoofmonitors = Constants.getNoofMonitors_withoutnatives();
/* 373 */           if (EnterpriseUtil.isAdminServer()) {
/* 374 */             totalnoofmonitors = CommDBUtil.getTotalMonitorCountInAdminServer();
/*     */           }
/* 376 */           if ((FreeEditionDetails.getFreeEditionDetails().getNumberOfMonitorsPermitted() != -1) && (totalnoofmonitors > FreeEditionDetails.getFreeEditionDetails().getNumberOfMonitorsPermitted())) {
/* 377 */             if (!EnterpriseUtil.isAdminServer()) {
/* 378 */               eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + Constants.getEUMChildString() + ")";
/* 379 */               String query1 = "select  AM_ManagedObject.resourceid,COALESCE(AM_ManagedObject.displayname,AM_ManagedObject.RESOURCENAME),AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.RESOURCETYPE from AM_ManagedObject left outer join AM_ManagedResourceType on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.resourceid where type in " + Constants.resourceTypes + Constants.excludeIntf + " and " + eumChildListCond + "  and AM_UnManagedNodes.resid is null order by creationtime desc,AM_ManagedResourceType.RESOURCETYPE,AM_ManagedObject.resourceid";
/*     */               
/* 381 */               extra = mo.getRows(query1);
/* 382 */               if ((extra != null) && (native_ids != null) && (native_ids.size() > 0)) {
/* 383 */                 for (int i = 0; i < extra.size(); i++) {
/* 384 */                   ArrayList row = (ArrayList)extra.get(i);
/* 385 */                   if (native_ids.contains(row.get(0))) {
/* 386 */                     extra.remove(i);
/* 387 */                     i--;
/*     */                   }
/*     */                   
/*     */                 }
/*     */                 
/*     */               }
/*     */               
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 398 */             setAccess(false);
/* 399 */             AMServerFramework.toXtraMonitorsPage = false;
/* 400 */             EnterpriseUtil.setUriCollectorAccess(false);
/*     */           }
/*     */         }
/*     */         
/* 404 */         if ((!req.getRequestURI().equals("/jsp/DeleteExtraMonitors.jsp")) && (!req.getRequestURI().equals("/Logout.do")) && (!req.getRequestURI().equals("/webclient/common/jsp/home.jsp")) && (!req.getRequestURI().equals("/jsp/formpages/Login.jsp")) && (!req.getRequestURI().equals("/jsp/PreLogin.jsp")) && (!req.getRequestURI().equals("/deleteMO.do")) && (!req.getRequestURI().equals("/jsp/formpages/Error.jsp")) && (!req.getRequestURI().equals("/webclient/common/jsp/registerLicence.jsp")) && (!req.getRequestURI().equals("/register.do")) && (!req.getRequestURI().equals("/jsp/EnterpriseDeleteExtraMonitors.jsp"))) {
/* 405 */           if (EnterpriseUtil.isAdminServer()) {
/* 406 */             if (FreeEditionDetails.getFreeEditionDetails().getUserType().equals("R")) {
/* 407 */               int exact_monitors = CommDBUtil.getTotalMonitorCountInAdminServer();
/* 408 */               if ((FreeEditionDetails.getFreeEditionDetails().getNumberOfMonitorsPermitted() != -1) && (exact_monitors > FreeEditionDetails.getFreeEditionDetails().getNumberOfMonitorsPermitted())) {
/* 409 */                 request.setAttribute("count", "" + exact_monitors);
/* 410 */                 HttpServletResponse rsp = (HttpServletResponse)response;
/* 411 */                 rsp.sendRedirect("/jsp/EnterpriseDeleteExtraMonitors.jsp");
/* 412 */                 return;
/*     */               }
/*     */             }
/*     */           } else {
/* 416 */             AMLog.debug("UNSUPPORTED " + unsupported.size() + " EXTRA " + extra.size() + " Unsupported EUM Agents : " + unsupportedEumAgents.size());
/* 417 */             if (((unsupported != null) && (unsupported.size() > 0)) || ((unsupportedEumAgents != null) && (unsupportedEumAgents.size() > 0))) {
/* 418 */               if ((unsupported != null) && (unsupported.size() > 0)) {
/* 419 */                 request.setAttribute("unsupportedmonitors", unsupported);
/*     */               }
/* 421 */               if ((unsupportedEumAgents != null) && (unsupportedEumAgents.size() > 0)) {
/* 422 */                 request.setAttribute("unsupportedEumAgents", unsupportedEumAgents);
/*     */               }
/*     */               
/*     */ 
/* 426 */               request.getRequestDispatcher("/jsp/DeleteExtraMonitors.jsp").forward(request, response);
/* 427 */               return; }
/* 428 */             if (extra.size() > 0) {
/* 429 */               request.setAttribute("extramonitors", extra);
/*     */               
/*     */ 
/* 432 */               request.getRequestDispatcher("/jsp/DeleteExtraMonitors.jsp").forward(request, response);
/* 433 */               return;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception exc) {
/* 440 */       exc.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/* 444 */     chain.doFilter(request, response);
/*     */   }
/*     */   
/*     */   public static void checkForUser(int resID, HttpServletRequest hrequest, HttpServletResponse hresponse)
/*     */   {
/* 449 */     boolean isPresent = false;
/*     */     try {
/* 451 */       Vector resourceids = new Vector();
/* 452 */       if (Constants.isUserResourceEnabled()) {
/* 453 */         resourceids = ClientDBUtil.getUserResourceID(hrequest);
/*     */       } else {
/* 455 */         resourceids = ClientDBUtil.getResourceIdentity(hrequest.getRemoteUser());
/*     */       }
/*     */       
/* 458 */       if (resourceids.contains(String.valueOf(resID))) {
/* 459 */         isPresent = true;
/*     */       }
/*     */       
/* 462 */       if (!isPresent) {
/* 463 */         hresponse.sendRedirect(hresponse.encodeRedirectURL("/showresource.do?group=All&method=showResourceTypes&fromwhere=operator"));
/*     */       }
/*     */     } catch (Exception ex) {
/* 466 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public FilterConfig getFilterConfig()
/*     */   {
/* 472 */     return this.config;
/*     */   }
/*     */   
/*     */   public void setFilterConfig(FilterConfig cfg)
/*     */   {
/*     */     try {
/* 478 */       init(cfg);
/*     */     }
/*     */     catch (ServletException ee) {}
/*     */   }
/*     */   
/*     */ 
/*     */   private void log(Object o) {}
/*     */   
/*     */ 
/*     */   protected void readStateFile(HttpServletRequest request)
/*     */   {
/* 489 */     HttpSession session = request.getSession();
/* 490 */     ServletContext context = session.getServletContext();
/* 491 */     String stateFile = context.getInitParameter("webClientStateFile");
/* 492 */     String path = PureUtils.rootDir + "users/" + request.getRemoteUser() + "/" + stateFile;
/* 493 */     log("read state file" + path);
/* 494 */     File f = new File(path);
/* 495 */     if (f.exists()) {
/* 496 */       log("File exists");
/* 497 */       Properties prop = new Properties();
/*     */       try {
/* 499 */         FileInputStream inStream = new FileInputStream(f);
/* 500 */         prop.load(inStream);
/* 501 */         inStream.close();
/* 502 */         log("prpps" + prop);
/*     */       } catch (IOException ex) {
/* 504 */         log("Exception" + ex);
/*     */       } catch (Exception exe) {
/* 506 */         log("Exception" + exe);
/*     */       }
/*     */       
/* 509 */       session.setAttribute("selectedskin", prop.getProperty("selectedskin"));
/* 510 */       if (prop.getProperty("selectedskin").equals("Gray")) {
/* 511 */         if (OEMUtil.isOEM())
/*     */         {
/* 513 */           session.setAttribute("selectedskin", OEMUtil.getOEMString("skin.color"));
/*     */         } else {
/* 515 */           session.setAttribute("selectedskin", "Grey");
/*     */         }
/*     */       }
/* 518 */       if (prop.getProperty("customreloadperiod") != null) {
/* 519 */         session.setAttribute("customreloadperiod", prop.getProperty("customreloadperiod"));
/*     */       } else {
/* 521 */         session.setAttribute("customreloadperiod", "300");
/*     */       }
/*     */       
/* 524 */       if (prop.getProperty("selectedscheme") != null) {
/* 525 */         session.setAttribute("selectedscheme", prop.getProperty("selectedscheme"));
/*     */       } else {
/* 527 */         session.setAttribute("selectedscheme", "default");
/*     */       }
/* 529 */       if (prop.getProperty("alarm_viewlength") != null) {
/* 530 */         session.setAttribute("alarm_viewlength", prop.getProperty("alarm_viewlength"));
/*     */       }
/*     */       
/* 533 */       if (prop.getProperty("sql_viewlength") != null) {
/* 534 */         session.setAttribute("sql_viewlength", prop.getProperty("sql_viewlength"));
/*     */       }
/*     */     }
/*     */     else {
/* 538 */       log("File does not exist");
/* 539 */       if (OEMUtil.isOEM()) {
/* 540 */         session.setAttribute("selectedskin", OEMUtil.getOEMString("skin.color"));
/*     */       } else {
/* 542 */         String value = context.getInitParameter("defaultSkin");
/* 543 */         session.setAttribute("selectedskin", value);
/*     */       }
/* 545 */       session.setAttribute("selectedscheme", "default");
/* 546 */       if (session.getAttribute("customreloadperiod") == null) {
/* 547 */         session.setAttribute("customreloadperiod", "300");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isAccess() {
/* 553 */     return access;
/*     */   }
/*     */   
/*     */   public static void setAccess(boolean aAccess) {
/* 557 */     access = aAccess;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void checkApmInsightLicense(ArrayList unSuppportedList)
/*     */   {
/* 564 */     unSuppportedList.addAll(ApmInsightLicenseUtil.getInstance().getUnsupportedMonitors("APM-Insight-Instance"));
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\filter\UriCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */