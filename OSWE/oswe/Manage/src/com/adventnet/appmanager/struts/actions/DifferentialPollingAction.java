/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.DifferentialPollingUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.MASSyncUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class DifferentialPollingAction extends org.apache.struts.actions.DispatchAction
/*     */ {
/*     */   public ActionForward getlistOfDCComponentsForType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  24 */     String monitorType = request.getParameter("monitorType");
/*  25 */     ArrayList dcComponentsList = new ArrayList();
/*  26 */     java.util.LinkedHashMap performanceDataCollectionMap = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getPerformanceDataCollectionMap(monitorType);
/*  27 */     response.setContentType("text/html; charset=UTF-8");
/*  28 */     if (performanceDataCollectionMap != null)
/*     */     {
/*  30 */       Iterator dcComponents = performanceDataCollectionMap.keySet().iterator();
/*  31 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(monitorType);
/*  32 */       Properties dcComponentProps = null;
/*  33 */       while (dcComponents.hasNext()) {
/*     */         try {
/*  35 */           String dcComponentName = (String)dcComponents.next();
/*  36 */           dcComponentProps = (Properties)performanceDataCollectionMap.get(dcComponentName);
/*  37 */           dcComponentsList.add(dcComponentProps);
/*  38 */           boolean isEnabled = DifferentialPollingUtil.isDCComponentEnabledForType(monitorType, dcComponentName);
/*  39 */           String showPollingOption = dcComponentProps.getProperty("Show-Polling-Options");
/*  40 */           String displayName = FormatUtil.getString(dcComponentProps.getProperty("Display"));
/*  41 */           int pollingInterval = -3;
/*  42 */           if ((showPollingOption != null) && (showPollingOption.equalsIgnoreCase("YES"))) {
/*  43 */             pollingInterval = DifferentialPollingUtil.getPollingIntervalForId(typeId, dcComponentName);
/*     */           }
/*  45 */           dcComponentProps.put("Name", dcComponentName);
/*  46 */           dcComponentProps.put("Display", displayName);
/*  47 */           dcComponentProps.put("isEnabled", Boolean.valueOf(isEnabled));
/*  48 */           if (pollingInterval == -1) {
/*  49 */             pollingInterval = dcComponentProps.getProperty("Default-Polling-Interval") != null ? Integer.parseInt(dcComponentProps.getProperty("Default-Polling-Interval")) : 0;
/*     */           }
/*  51 */           dcComponentProps.put("PollingInterval", Integer.valueOf(pollingInterval));
/*     */         }
/*     */         catch (Exception e) {
/*  54 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  59 */     if (("XenServerHost".equals(monitorType)) || ("WebsphereMQ".equals(monitorType)) || ("VMWare ESX/ESXi".equals(monitorType)) || ("file".equals(monitorType)) || ("Script Monitor".equals(monitorType)) || ("QueryMonitor".equals(monitorType)) || ("FTPMonitor".equals(monitorType)) || ("JMX1.2-MX4J-RMI".equals(monitorType)) || ("JDK1.5".equals(monitorType))) {
/*  60 */       Properties customdcComponentProps = new Properties();
/*  61 */       customdcComponentProps.setProperty("Name", "PERFDATA");
/*  62 */       customdcComponentProps.setProperty("Display", "Performance");
/*  63 */       dcComponentsList.add(customdcComponentProps);
/*     */     }
/*     */     
/*  66 */     int totalNumberOfMonitors = DBUtil.getCount("select count(RESOURCEID) from AM_ManagedObject where type='" + monitorType + "'");
/*  67 */     request.setAttribute("DCComponentsList", dcComponentsList);
/*  68 */     request.setAttribute("totalNumberOfMonitors", Integer.valueOf(totalNumberOfMonitors));
/*  69 */     request.setAttribute("monitorType", monitorType);
/*  70 */     request.setAttribute("selecteddcComponentName", request.getParameter("selecteddcComponentName"));
/*  71 */     javax.servlet.RequestDispatcher rd = request.getRequestDispatcher("/jsp/DCComponentsList.jsp");
/*  72 */     rd.include(request, response);
/*  73 */     return null;
/*     */   }
/*     */   
/*  76 */   public ActionForward updatedcComponentStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { String monitorType = request.getParameter("Monitortype");
/*  77 */     String dcComponentName = request.getParameter("dcComponentsSelect");
/*  78 */     HashMap<String, String> params = new HashMap();
/*  79 */     params.put("configureBy", "Monitor Type");
/*  80 */     params.put("Monitortype", monitorType);
/*  81 */     params.put("dcComponentsSelect", dcComponentName);
/*  82 */     Properties dcComponentVsStatus = new Properties();
/*  83 */     Properties dcComponentPropsVsIds = new Properties();
/*  84 */     String baseId = com.adventnet.appmanager.util.Constants.getTypeId(monitorType);
/*  85 */     Vector resIdList = DBUtil.getResIdsOfGivenResTypes(monitorType);
/*  86 */     resIdList.add(baseId + "");
/*  87 */     com.adventnet.appmanager.logging.AMLog.debug(" updatedcComponentStatus :: monitorType :" + monitorType + "\t baseId :" + baseId + "\n resIdList :" + resIdList);
/*     */     try
/*     */     {
/*  90 */       boolean status = true;
/*  91 */       String pollingstatus = request.getParameter("pollingStatus");
/*  92 */       if ((pollingstatus != null) && (pollingstatus.equals("-1"))) {
/*  93 */         status = false;
/*     */       }
/*     */       
/*     */ 
/*  97 */       String customPollingValue = request.getParameter("customeFrequency");
/*  98 */       String timeUnit = request.getParameter("selectedTimeUnit");
/*  99 */       params.put("status", String.valueOf(status));
/* 100 */       int customValue = customPollingValue.trim().equals("") ? 0 : Integer.parseInt(customPollingValue);
/* 101 */       if ((timeUnit.equalsIgnoreCase("hours")) && (customValue != 0)) {
/* 102 */         customValue *= 60;
/*     */       }
/* 104 */       int pollingInterval = pollingstatus.equals("0") ? 0 : !status ? -1 : customValue;
/* 105 */       params.put("pollingInterval", String.valueOf(pollingInterval));
/* 106 */       dcComponentVsStatus.put(dcComponentName, String.valueOf(status));
/*     */       
/* 108 */       Properties resIdProps = new Properties();
/* 109 */       dcComponentPropsVsIds.put(dcComponentName, resIdProps);
/* 110 */       Iterator resIds = resIdList.iterator();
/* 111 */       while (resIds.hasNext()) {
/* 112 */         String resId = (String)resIds.next();
/* 113 */         Properties dcComponentPropsVsresId = new Properties();
/* 114 */         dcComponentPropsVsresId.put("Status", String.valueOf(status));
/* 115 */         dcComponentPropsVsresId.put("PollingInterval", Integer.valueOf(pollingInterval));
/* 116 */         resIdProps.put(resId, dcComponentPropsVsresId);
/*     */       }
/*     */       
/* 119 */       if ((!dcComponentVsStatus.isEmpty()) || (!dcComponentPropsVsIds.isEmpty())) {
/* 120 */         DifferentialPollingUtil.updateDCComponentForType(monitorType, dcComponentVsStatus, dcComponentPropsVsIds);
/*     */       }
/*     */       
/* 123 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (!params.isEmpty())) {
/* 124 */         Vector serverids = MASSyncUtil.getallServerIDS();
/* 125 */         for (int i = 0; i < serverids.size(); i++) {
/* 126 */           MASSyncUtil.addTasktoSync(params, "/AppManager/xml/dcComponentsConfigs/update", serverids.get(i).toString(), "POST", 12, 2);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 132 */       e.printStackTrace();
/*     */     }
/*     */     
/* 135 */     request.setAttribute("showconfigureDC", "true");
/* 136 */     request.setAttribute("MonitorType", monitorType);
/* 137 */     request.setAttribute("dcComponentName", dcComponentName);
/* 138 */     request.setAttribute("showMsg", "true");
/* 139 */     return new ActionForward("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showconfigureDC=true&MonitorType=" + monitorType);
/*     */   }
/*     */   
/*     */   public ActionForward updatePollingIntervalForId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 143 */     try { HashMap<String, String> params = new HashMap();
/* 144 */       params.put("configureBy", "Monitors");
/* 145 */       String dcComponentName = request.getParameter("dcComponentName");
/* 146 */       int pollingInterval = Integer.parseInt(request.getParameter("pollingInterval"));
/* 147 */       String monitorType = request.getParameter("monitorType");
/* 148 */       String resourceIds = request.getParameter("resourceIds");
/* 149 */       params.put("dcComponentsSelect", dcComponentName);
/* 150 */       params.put("pollingInterval", String.valueOf(pollingInterval));
/* 151 */       params.put("Monitortype", monitorType);
/* 152 */       params.put("resourceIds", resourceIds);
/* 153 */       DifferentialPollingUtil.bulkUpdatePollingStatus(dcComponentName, resourceIds, pollingInterval, monitorType);
/* 154 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (!params.isEmpty())) {
/* 155 */         Vector serverids = MASSyncUtil.getallServerIDS();
/* 156 */         for (int i = 0; i < serverids.size(); i++) {
/* 157 */           MASSyncUtil.addTasktoSync(params, "/AppManager/xml/dcComponentsConfigs/update", serverids.get(i).toString(), "POST", 12, 2);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 162 */       e.printStackTrace();
/*     */     }
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward getCommonPerfProps(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 169 */     JSONObject jsonObj = new JSONObject();
/* 170 */     String monitorType = request.getParameter("monitorType");
/* 171 */     jsonObj = getListprops(monitorType);
/* 172 */     response.setCharacterEncoding("UTF-8");
/* 173 */     java.io.PrintWriter out = response.getWriter();
/* 174 */     response.setContentType("application/json");
/* 175 */     response.setHeader("Cache-Control", "no-cache");
/* 176 */     out.println(jsonObj);
/* 177 */     out.flush();
/* 178 */     return null;
/*     */   }
/*     */   
/*     */   public static JSONObject getListprops(String monitorType)
/*     */   {
/* 183 */     JSONObject obj = new JSONObject();
/*     */     try {
/* 185 */       Map data = new HashMap();
/*     */       
/* 187 */       Map details = new HashMap();
/* 188 */       if ("XenServerHost".equals(monitorType)) {
/* 189 */         details.put("value", DBUtil.getServerConfigValue("am.xen.resourcepool.discovery.enable"));
/* 190 */         details.put("name", FormatUtil.getString("am.xen.resourcepool.discovery.enable"));
/* 191 */         data.put("am.xen.resourcepool.discovery.enable", details);
/* 192 */         obj.put("0", data);
/*     */       }
/* 194 */       else if ("VMWare ESX/ESXi".equals(monitorType))
/*     */       {
/* 196 */         details.put("value", DBUtil.getServerConfigValue("am.vsphere.read.timeout"));
/* 197 */         details.put("name", FormatUtil.getString("am.vsphere.read.timeout"));
/* 198 */         data.put("am.vsphere.read.timeout", details);
/* 199 */         details = new HashMap();
/* 200 */         details.put("value", DBUtil.getServerConfigValue("am.vsphere.connection.timeout"));
/* 201 */         details.put("name", FormatUtil.getString("am.vsphere.connection.timeout"));
/* 202 */         data.put("am.vsphere.connection.timeout", details);
/* 203 */         obj.put("1", data);
/*     */       }
/* 205 */       else if ("file".equals(monitorType))
/*     */       {
/*     */ 
/* 208 */         details.put("value", DBUtil.getServerConfigValue("am.dirsize.unit"));
/* 209 */         details.put("name", FormatUtil.getString("am.dirsize.unit"));
/* 210 */         data.put("am.dirsize.unit", details);
/*     */         
/* 212 */         details = new HashMap();
/* 213 */         details.put("value", DBUtil.getServerConfigValue("am.filesize.unit"));
/* 214 */         details.put("name", FormatUtil.getString("am.filesize.unit"));
/* 215 */         data.put("am.filesize.unit", details);
/*     */         
/* 217 */         details = new HashMap();
/* 218 */         details.put("value", DBUtil.getServerConfigValue("am.file.contentcheckcount"));
/* 219 */         details.put("name", FormatUtil.getString("am.file.contentcheckcount"));
/* 220 */         data.put("am.file.contentcheckcount", details);
/* 221 */         obj.put("1", data);
/* 222 */       } else if ("Script Monitor".equals(monitorType))
/*     */       {
/* 224 */         details.put("value", DBUtil.getServerConfigValue("am.script.deleterow"));
/* 225 */         details.put("name", FormatUtil.getString("am.script.deleterow"));
/* 226 */         data.put("am.script.deleterow", details);
/* 227 */         obj.put("0", data);
/*     */       }
/* 229 */       else if ("QueryMonitor".equals(monitorType))
/*     */       {
/* 231 */         details.put("value", DBUtil.getServerConfigValue("am.querymonitor.rowcount"));
/* 232 */         details.put("name", FormatUtil.getString("am.querymonitor.rowcount"));
/* 233 */         data.put("am.querymonitor.rowcount", details);
/*     */         
/* 235 */         details = new HashMap();
/* 236 */         details.put("value", DBUtil.getServerConfigValue("am.query.timeout"));
/* 237 */         details.put("name", FormatUtil.getString("am.query.timeout"));
/* 238 */         data.put("am.query.timeout", details);
/* 239 */         obj.put("1", data);
/*     */       }
/* 241 */       else if ("FTPMonitor".equals(monitorType))
/*     */       {
/* 243 */         details.put("value", DBUtil.getServerConfigValue("am.ftp.filesize"));
/* 244 */         details.put("name", FormatUtil.getString("am.ftp.filesize"));
/* 245 */         data.put("am.ftp.filesize", details);
/* 246 */         obj.put("1", data);
/*     */       }
/* 248 */       else if ("JMX1.2-MX4J-RMI".equals(monitorType)) {
/* 249 */         details.put("value", DBUtil.getServerConfigValue("am.cam.mbeanslistsize"));
/* 250 */         details.put("name", FormatUtil.getString("am.cam.mbeanslistsize"));
/* 251 */         data.put("am.cam.mbeanslistsize", details);
/* 252 */         obj.put("1", data);
/*     */       }
/* 254 */       else if ("WebsphereMQ".equals(monitorType))
/*     */       {
/* 256 */         details.put("value", DBUtil.getServerConfigValue("am.mqdelrow.enabled"));
/* 257 */         details.put("name", FormatUtil.getString("am.mqdelrow.enabled"));
/* 258 */         data.put("am.mqdelrow.enabled", details);
/* 259 */         obj.put("0", data);
/* 260 */         data = new HashMap();
/* 261 */         details = new HashMap();
/* 262 */         details.put("value", DBUtil.getServerConfigValue("am.mqseries.ccsid"));
/* 263 */         details.put("name", FormatUtil.getString("am.mqseries.ccsid"));
/* 264 */         data.put("am.mqseries.ccsid", details);
/* 265 */         obj.put("1", data);
/*     */       }
/* 267 */       else if ("JDK1.5".equals(monitorType))
/*     */       {
/* 269 */         details.put("value", DBUtil.getServerConfigValue("am.htmldata.jre.daystoretain"));
/* 270 */         details.put("name", FormatUtil.getString("am.htmldata.jre.daystoretain"));
/* 271 */         data.put("am.htmldata.jre.daystoretain", details);
/* 272 */         obj.put("1", data);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 276 */       e.printStackTrace();
/*     */     }
/* 278 */     return obj;
/*     */   }
/*     */   
/*     */   public ActionForward updatePerfComponentStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 283 */     String monitorType = request.getParameter("Monitortype");
/*     */     try {
/* 285 */       HashMap params = new HashMap();
/*     */       
/* 287 */       if ("XenServerHost".equals(monitorType))
/*     */       {
/* 289 */         String xendiscovery = request.getParameter("am.xen.resourcepool.discovery.enableval");
/*     */         
/* 291 */         if (!xendiscovery.equalsIgnoreCase(DBUtil.getServerConfigValue("am.xen.resourcepool.discovery.enable")))
/*     */         {
/* 293 */           DBUtil.updateServerConfigValue("am.xen.resourcepool.discovery.enable", xendiscovery);
/* 294 */           params.put("am.xen.resourcepool.discovery.enable", xendiscovery);
/*     */         }
/*     */       }
/* 297 */       else if ("VMWare ESX/ESXi".equals(monitorType))
/*     */       {
/* 299 */         String vsphereRead = request.getParameter("am.vsphere.read.timeout");
/* 300 */         String vsphereConntimeout = request.getParameter("am.vsphere.connection.timeout");
/*     */         
/*     */ 
/* 303 */         if (("".equals(vsphereRead)) || ("0".equals(vsphereRead))) {
/* 304 */           vsphereRead = "180";
/*     */         }
/* 306 */         if (!vsphereRead.equalsIgnoreCase(DBUtil.getServerConfigValue("am.vsphere.read.timeout")))
/*     */         {
/* 308 */           DBUtil.updateServerConfigValue("am.vsphere.read.timeout", vsphereRead);
/* 309 */           params.put("am.vsphere.read.timeout", vsphereRead);
/*     */         }
/*     */         
/* 312 */         if (("".equals(vsphereConntimeout)) || ("0".equals(vsphereConntimeout))) {
/* 313 */           vsphereConntimeout = "180";
/*     */         }
/*     */         
/* 316 */         if (!vsphereConntimeout.equalsIgnoreCase(DBUtil.getServerConfigValue("am.vsphere.connection.timeout")))
/*     */         {
/* 318 */           DBUtil.updateServerConfigValue("am.vsphere.connection.timeout", vsphereConntimeout);
/* 319 */           params.put("am.vsphere.connection.timeout", vsphereConntimeout);
/*     */         }
/*     */       }
/* 322 */       else if ("file".equals(monitorType))
/*     */       {
/* 324 */         String dirsize = request.getParameter("am.dirsize.unit");
/* 325 */         String filesize = request.getParameter("am.filesize.unit");
/* 326 */         String filecontcount = request.getParameter("am.file.contentcheckcount");
/*     */         
/* 328 */         if ((!"".equals(dirsize)) && 
/* 329 */           (!dirsize.equalsIgnoreCase(DBUtil.getServerConfigValue("am.dirsize.unit"))))
/*     */         {
/* 331 */           DBUtil.updateServerConfigValue("am.dirsize.unit", dirsize);
/* 332 */           params.put("am.dirsize.unit", dirsize);
/*     */         }
/*     */         
/*     */ 
/* 336 */         if ((!"".equals(filesize)) && 
/* 337 */           (!filesize.equalsIgnoreCase(DBUtil.getServerConfigValue("am.filesize.unit"))))
/*     */         {
/* 339 */           DBUtil.updateServerConfigValue("am.filesize.unit", filesize);
/* 340 */           params.put("am.filesize.unit", filesize);
/*     */         }
/*     */         
/*     */ 
/* 344 */         if ((!"".equals(filecontcount)) && 
/* 345 */           (!filecontcount.equalsIgnoreCase(DBUtil.getServerConfigValue("am.file.contentcheckcount"))))
/*     */         {
/* 347 */           DBUtil.updateServerConfigValue("am.file.contentcheckcount", filecontcount);
/* 348 */           params.put("am.file.contentcheckcount", filecontcount);
/*     */         }
/*     */       }
/* 351 */       else if ("Script Monitor".equals(monitorType))
/*     */       {
/* 353 */         String scriptDelRow = request.getParameter("am.script.deleterowval");
/*     */         
/* 355 */         if (!scriptDelRow.equalsIgnoreCase(DBUtil.getServerConfigValue("am.script.deleterow")))
/*     */         {
/* 357 */           DBUtil.updateServerConfigValue("am.script.deleterow", scriptDelRow);
/* 358 */           params.put("am.script.deleterow", scriptDelRow);
/*     */         }
/* 360 */       } else if ("QueryMonitor".equals(monitorType))
/*     */       {
/* 362 */         String qryRowCnt = request.getParameter("am.querymonitor.rowcount");
/* 363 */         String qrytimeout = request.getParameter("am.query.timeout");
/*     */         
/* 365 */         if (("".equals(qryRowCnt)) || (Integer.parseInt(qryRowCnt) == 0)) {
/* 366 */           qryRowCnt = "50";
/*     */         }
/* 368 */         if (!qryRowCnt.equalsIgnoreCase(DBUtil.getServerConfigValue("am.querymonitor.rowcount")))
/*     */         {
/* 370 */           DBUtil.updateServerConfigValue("am.querymonitor.rowcount", qryRowCnt);
/* 371 */           params.put("am.querymonitor.rowcount", qryRowCnt);
/*     */         }
/*     */         
/* 374 */         if ((!"".equals(qrytimeout)) && 
/* 375 */           (!qrytimeout.equalsIgnoreCase(DBUtil.getServerConfigValue("am.query.timeout"))))
/*     */         {
/* 377 */           DBUtil.updateServerConfigValue("am.query.timeout", qrytimeout);
/* 378 */           params.put("am.query.timeout", qrytimeout);
/*     */         }
/*     */         
/*     */       }
/* 382 */       else if ("FTPMonitor".equals(monitorType))
/*     */       {
/* 384 */         String ftpfilesize = request.getParameter("am.ftp.filesize");
/*     */         
/* 386 */         if (("".equals(ftpfilesize)) || (Integer.parseInt(ftpfilesize) == 0)) {
/* 387 */           ftpfilesize = "10";
/*     */         }
/* 389 */         if (!ftpfilesize.equalsIgnoreCase(DBUtil.getServerConfigValue("am.ftp.filesize")))
/*     */         {
/* 391 */           DBUtil.updateServerConfigValue("am.ftp.filesize", ftpfilesize);
/* 392 */           params.put("am.ftp.filesize", ftpfilesize);
/*     */         }
/* 394 */       } else if ("JMX1.2-MX4J-RMI".equals(monitorType))
/*     */       {
/* 396 */         String mbeanslistsize = request.getParameter("am.cam.mbeanslistsize");
/*     */         
/* 398 */         if (("".equals(mbeanslistsize)) || (Integer.parseInt(mbeanslistsize) == 0)) {
/* 399 */           mbeanslistsize = "250";
/*     */         }
/* 401 */         if (!mbeanslistsize.equalsIgnoreCase(DBUtil.getServerConfigValue("am.cam.mbeanslistsize")))
/*     */         {
/* 403 */           DBUtil.updateServerConfigValue("am.cam.mbeanslistsize", mbeanslistsize);
/* 404 */           params.put("am.cam.mbeanslistsize", mbeanslistsize);
/*     */         }
/*     */         
/*     */       }
/* 408 */       else if ("WebsphereMQ".equals(monitorType))
/*     */       {
/* 410 */         String mqdelrow = request.getParameter("am.mqdelrow.enabledval");
/* 411 */         String mqseries = request.getParameter("am.mqseries.ccsid");
/*     */         
/* 413 */         if (!mqdelrow.equalsIgnoreCase(DBUtil.getServerConfigValue("am.mqdelrow.enabled")))
/*     */         {
/* 415 */           DBUtil.updateServerConfigValue("am.mqdelrow.enabled", mqdelrow);
/* 416 */           params.put("am.mqdelrow.enabled", mqdelrow);
/*     */         }
/*     */         
/* 419 */         if ((!"".equals(mqseries)) && 
/* 420 */           (!mqseries.equalsIgnoreCase(DBUtil.getServerConfigValue("am.mqseries.ccsid"))))
/*     */         {
/* 422 */           DBUtil.updateServerConfigValue("am.mqseries.ccsid", mqseries);
/* 423 */           params.put("am.mqseries.ccsid", mqseries);
/*     */         }
/*     */       }
/* 426 */       else if ("JDK1.5".equals(monitorType)) {
/* 427 */         String jredaystoretain = request.getParameter("am.htmldata.jre.daystoretain");
/* 428 */         if (("".equals(jredaystoretain)) || (Integer.parseInt(jredaystoretain) == 0)) {
/* 429 */           jredaystoretain = "30";
/*     */         }
/* 431 */         if (!jredaystoretain.equalsIgnoreCase(DBUtil.getServerConfigValue("am.htmldata.jre.daystoretain")))
/*     */         {
/* 433 */           DBUtil.updateServerConfigValue("am.htmldata.jre.daystoretain", jredaystoretain);
/* 434 */           params.put("am.htmldata.jre.daystoretain", jredaystoretain);
/*     */         }
/*     */       }
/* 437 */       if (!params.isEmpty()) {
/* 438 */         com.adventnet.appmanager.dbcache.AMCacheHandler.updateGlobalVariables(params);
/*     */       }
/* 440 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (!params.isEmpty())) {
/* 441 */         Vector serverids = MASSyncUtil.getallServerIDS();
/* 442 */         for (int id = 0; id < serverids.size(); id++) {
/* 443 */           MASSyncUtil.addTasktoSync(params, "/AppManager/xml/serverConfigs/update", serverids.get(id).toString(), "POST", 9, 2);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 448 */       e.printStackTrace();
/*     */     }
/* 450 */     return new ActionForward("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showconfigureDC=true&MonitorType=" + monitorType);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\DifferentialPollingAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */