/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.util.ChildMOHandler;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ public class DashboardAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward generateAvailabilityHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/*  41 */       String type = request.getParameter("type");
/*  42 */       int period = Integer.parseInt(request.getParameter("period"));
/*  43 */       String userName = request.getRemoteUser();
/*  44 */       long starttime = ReportUtilities.getStartTime(period);
/*  45 */       long endtime = ReportUtilities.getEndTime(period);
/*  46 */       request.setAttribute("startdate", new Date(starttime));
/*  47 */       request.setAttribute("endtime", new Date(endtime));
/*  48 */       Hashtable h = null;
/*  49 */       String owner = request.getRemoteUser();
/*  50 */       String role = "";
/*     */       
/*  52 */       HttpSession session = request.getSession();
/*  53 */       ServletContext ctx = session.getServletContext();
/*  54 */       String resourceId = request.getParameter("resourceId") != null ? request.getParameter("resourceId") : null;
/*     */       
/*     */ 
/*     */ 
/*  58 */       int selectedPage = 1;
/*  59 */       int startIndex = 0;
/*     */       
/*  61 */       if (request.getParameter("selectedPage") != null)
/*     */       {
/*  63 */         selectedPage = Integer.parseInt(request.getParameter("selectedPage"));
/*     */       }
/*  65 */       else if (request.getSession().getAttribute("selectedPage") != null)
/*     */       {
/*  67 */         selectedPage = ((Integer)request.getSession().getAttribute("selectedPage")).intValue();
/*     */       }
/*     */       
/*  70 */       int noOfRows = 25;
/*     */       
/*  72 */       if (request.getParameter("noOfRows") != null)
/*     */       {
/*  74 */         noOfRows = Integer.parseInt(request.getParameter("noOfRows"));
/*     */       }
/*  76 */       else if (request.getSession().getAttribute("noOfRows") != null)
/*     */       {
/*  78 */         noOfRows = ((Integer)request.getSession().getAttribute("noOfRows")).intValue();
/*     */       }
/*     */       
/*  81 */       startIndex = (selectedPage - 1) * noOfRows;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  87 */       ArrayList aListCategoryLink = new ArrayList(Arrays.asList(Constants.categoryLink));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  92 */       HashMap widgetProps = new HashMap();
/*  93 */       Vector resIds_vector = new Vector();
/*  94 */       if (EnterpriseUtil.isIt360MSPEdition())
/*     */       {
/*  96 */         if (request.getAttribute("widgetProps") != null)
/*     */         {
/*  98 */           widgetProps = (HashMap)request.getAttribute("widgetProps");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 103 */           widgetProps = CustomerManagementAPI.addCustInfoInWidgetProps(request, widgetProps);
/*     */         }
/* 105 */         resIds_vector = CustomerManagementAPI.filterResourceIds(widgetProps);
/* 106 */         AMLog.debug("resIds_vector in generateAvailabilityHistory()" + resIds_vector);
/*     */       }
/*     */       
/*     */ 
/* 110 */       if (ClientDBUtil.isPrivilegedUser(request))
/*     */       {
/* 112 */         role = "operator";
/*     */       }
/*     */       else
/*     */       {
/* 116 */         role = "user";
/*     */       }
/*     */       
/* 119 */       String group = "Others";
/* 120 */       if (type != null) {
/* 121 */         if (type.equals("HAI")) {
/* 122 */           group = "HAI";
/* 123 */           if ((role.equals("operator")) && (ctx.getAttribute("mgdrilldown") != null) && (ctx.getAttribute("mgdrilldown").equals("true")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*     */           {
/* 125 */             h = ReportUtilities.getMGHistoryForOperator(owner, period, true, 1, request);
/*     */           }
/* 127 */           else if ((role.equals("operator")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*     */           {
/* 129 */             h = ReportUtilities.getMGHistoryForOperator(owner, period, false, 1, request);
/*     */ 
/*     */ 
/*     */           }
/* 133 */           else if (EnterpriseUtil.isIt360MSPEdition())
/*     */           {
/* 135 */             h = ReportUtilities.getAvailabilityHistoryForMGs(type, period, owner, role, request);
/*     */           }
/*     */           else
/*     */           {
/* 139 */             h = ReportUtilities.getAvailabilityHistoryForMGs(type, period, owner, role, request);
/*     */           }
/*     */           
/*     */         }
/* 143 */         else if ((resourceId != null) && (request.getParameter("isConfMonitor") != null)) {
/* 144 */           String query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo where resourceid=" + resourceId;
/* 145 */           h = ReportUtilities.getAvailabilityHistoryById(query, period, true);
/*     */ 
/*     */         }
/* 148 */         else if (aListCategoryLink.contains(type))
/*     */         {
/*     */ 
/* 151 */           if (EnterpriseUtil.isIt360MSPEdition())
/*     */           {
/* 153 */             h = ReportUtilities.getAvailabilityHistoryByCategoryType(type, period, owner, role, resIds_vector, startIndex, noOfRows, request);
/*     */           }
/*     */           else
/*     */           {
/* 157 */             h = ReportUtilities.getAvailabilityHistoryByCategoryType(type, period, owner, role, null, startIndex, noOfRows, request);
/*     */           }
/*     */           
/* 160 */           request.setAttribute("CategoryType", type);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 165 */           String selectedTab = EnterpriseUtil.getSelectedTab(request);
/* 166 */           if (selectedTab != null)
/*     */           {
/* 168 */             if (selectedTab.equals("Server"))
/*     */             {
/* 170 */               type = "servers";
/*     */             }
/* 172 */             else if (selectedTab.equals("Application"))
/*     */             {
/* 174 */               type = "applications";
/*     */             }
/* 176 */             else if (selectedTab.equals("Networks"))
/*     */             {
/* 178 */               type = "$ComplexType_NetworkDevices";
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 183 */           if (EnterpriseUtil.isIt360MSPEdition())
/*     */           {
/* 185 */             h = ReportUtilities.getAvailabilityHistoryByType(type, period, owner, role, resIds_vector, startIndex, noOfRows, request);
/*     */           }
/*     */           else
/*     */           {
/* 189 */             h = ReportUtilities.getAvailabilityHistoryByType(type, period, owner, role, null, startIndex, noOfRows, request);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 195 */         int haid = Integer.parseInt(request.getParameter("haid"));
/* 196 */         boolean isowner = true;
/* 197 */         if ((role != null) && (role.equals("operator"))) {
/* 198 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 199 */           Hashtable haidparents = DBUtil.getParentMGsforChildMGs("('" + haid + "')");
/* 200 */           String haidstoCheck = "(";
/* 201 */           ArrayList temp1 = (ArrayList)haidparents.get(Integer.valueOf(haid));
/* 202 */           if (temp1 != null) {
/* 203 */             for (int i = 0; i < temp1.size(); i++) {
/* 204 */               haidstoCheck = haidstoCheck + "'" + temp1.get(i) + "',";
/*     */             }
/*     */           }
/* 207 */           haidstoCheck = haidstoCheck + "'" + haid + "')";
/*     */           try
/*     */           {
/* 210 */             String q1 = "select * from AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where USERNAME='" + userName + "' and USERID=OWNERID AND HAID IN " + haidstoCheck;
/* 211 */             if (Constants.isUserResourceEnabled()) {
/* 212 */               String loginUserid = Constants.getLoginUserid(request);
/* 213 */               q1 = "select * from AM_USERRESOURCESTABLE where USERID=" + loginUserid + " AND RESOURCEID IN " + haidstoCheck;
/*     */             }
/* 215 */             ResultSet rs1 = AMConnectionPool.executeQueryStmt(q1);
/* 216 */             if (rs1.next())
/*     */             {
/* 218 */               isowner = true;
/*     */             }
/*     */             else {
/* 221 */               isowner = false;
/*     */             }
/* 223 */             rs1.close();
/*     */           }
/*     */           catch (Exception exc) {}
/*     */         }
/*     */         
/*     */ 
/* 229 */         if (isowner) {
/* 230 */           h = ReportUtilities.getAvailabilityHistoryById(haid, period);
/*     */         }
/*     */         else {
/* 233 */           h = ReportUtilities.getAvailabilityHistoryById(haid, period, false, userName, request);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 238 */       System.out.println("DashboardAction : Availability Data " + h);
/* 239 */       request.setAttribute("downtime_report", h);
/* 240 */       request.setAttribute("resourceMap", getChildMonitorInfo(h));
/* 241 */       if ((period == 1) || (period == 4)) {
/* 242 */         request.setAttribute("type", "TIME");
/*     */       }
/*     */       else {
/* 245 */         request.setAttribute("type", "DATE");
/*     */       }
/* 247 */       long timeperiod = endtime - starttime;
/*     */       
/* 249 */       request.setAttribute("AVAILABILITY", calculateAvailability(h, timeperiod));
/*     */       
/* 251 */       long unit = timeperiod / 10L;
/* 252 */       ArrayList units = new ArrayList(5);
/* 253 */       if (period == 4) {
/* 254 */         for (int i = 1; i <= 5; i++)
/*     */         {
/* 256 */           units.add(new Date(starttime + i * 3600000L));
/*     */         }
/*     */         
/* 259 */       } else if (period == 1) {
/* 260 */         for (int i = 1; i <= 11; i++)
/*     */         {
/* 262 */           units.add(new Date(starttime + i * 7200000L));
/*     */         }
/*     */         
/* 265 */       } else if (period == 2) {
/* 266 */         for (int i = 1; i <= 9; i++)
/*     */         {
/* 268 */           units.add(new Date(starttime + i * 259200000L));
/*     */         }
/*     */       }
/* 271 */       request.setAttribute("units", units);
/*     */       
/* 273 */       request.setAttribute("isConfMonitor", request.getParameter("isConfMonitor") != null ? "true" : "false");
/* 274 */       return new ActionForward("/jsp/downtimechart.jsp?group=" + group);
/*     */     } catch (Exception e) {
/* 276 */       e.printStackTrace();
/*     */     }
/* 278 */     return null;
/*     */   }
/*     */   
/*     */   private HashMap<String, String[]> getChildMonitorInfo(Hashtable h)
/*     */   {
/* 283 */     HashMap resDispMap = new HashMap();
/* 284 */     Vector childVec = new Vector();
/* 285 */     if (h != null)
/*     */     {
/* 287 */       Iterator itr = h.keySet().iterator();
/* 288 */       while (itr.hasNext())
/*     */       {
/* 290 */         String key = (String)itr.next();
/* 291 */         int typeIndex = key.indexOf("$");
/* 292 */         int resIndex = key.indexOf("#");
/* 293 */         if ((typeIndex != -1) && (resIndex != -1))
/*     */         {
/* 295 */           String typeStr = key.substring(0, typeIndex);
/* 296 */           boolean isTypeSupported = ChildMOHandler.isChildMonitorTypeSupportedForMG(typeStr);
/* 297 */           if (isTypeSupported)
/*     */           {
/* 299 */             String resId = key.substring(typeIndex + 1, resIndex);
/* 300 */             childVec.add(resId);
/* 301 */             resDispMap.put(resId, "");
/*     */           }
/*     */         }
/*     */       }
/* 305 */       HashMap<String, HashMap<String, String>> childMonitorInfo = ChildMOHandler.getChildMonitorWithParentInfo(childVec);
/* 306 */       if (childMonitorInfo != null)
/*     */       {
/* 308 */         itr = childMonitorInfo.keySet().iterator();
/* 309 */         while (itr.hasNext())
/*     */         {
/* 311 */           String resId = (String)itr.next();
/* 312 */           HashMap<String, String> monitorMap = (HashMap)childMonitorInfo.get(resId);
/* 313 */           if (monitorMap != null)
/*     */           {
/* 315 */             String[] arr = new String[2];
/* 316 */             arr[0] = ((String)monitorMap.get("displayname"));
/* 317 */             arr[1] = ((String)monitorMap.get("trimmeddisplayname"));
/* 318 */             resDispMap.put(resId, arr);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 323 */     return resDispMap;
/*     */   }
/*     */   
/*     */   private Hashtable calculateAvailability(Hashtable h, long timeperiod) {
/* 327 */     Hashtable toret = new Hashtable();
/* 328 */     Enumeration e = h.keys();
/* 329 */     while (e.hasMoreElements()) {
/* 330 */       String key = (String)e.nextElement();
/* 331 */       ArrayList l = (ArrayList)h.get(key);
/* 332 */       long total = 0L;
/* 333 */       long period = timeperiod;
/* 334 */       int i = 0; for (int size = l.size(); i < size; i++) {
/* 335 */         Hashtable temp = (Hashtable)l.get(i);
/* 336 */         String status = (String)temp.get("STATUS");
/* 337 */         if (status.equals("NO_DC")) {
/* 338 */           long start = ((Date)temp.get("STARTTIME")).getTime();
/* 339 */           long end = ((Date)temp.get("ENDTIME")).getTime();
/* 340 */           period -= end - start;
/*     */         }
/* 342 */         else if (status.equals("AVAILABALE")) {
/* 343 */           long start = ((Date)temp.get("STARTTIME")).getTime();
/* 344 */           long end = ((Date)temp.get("ENDTIME")).getTime();
/* 345 */           total += end - start;
/*     */         }
/*     */       }
/* 348 */       double per = total * 100L / period;
/*     */       
/* 350 */       if (per > 100.0D)
/*     */       {
/* 352 */         per = 100.0D;
/*     */       }
/* 354 */       toret.put(key, Double.valueOf(per));
/*     */     }
/* 356 */     return toret;
/*     */   }
/*     */   
/*     */   public ActionForward generateVirtualMachineTable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 362 */     String group = request.getParameter("group");
/* 363 */     String network = request.getParameter("network");
/* 364 */     return new ActionForward("/jsp/displayvirtualmachines.jsp?group=" + group + "&network=" + network);
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward generateEC2Table(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 371 */     String group = request.getParameter("group");
/* 372 */     String network = request.getParameter("network");
/* 373 */     return new ActionForward("/jsp/displayEC2.jsp?group=" + group + "&network=" + network);
/*     */   }
/*     */   
/*     */   public ActionForward generateHealthHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 381 */       String userName = request.getRemoteUser();
/* 382 */       String owner = request.getRemoteUser();
/* 383 */       String role = "";
/* 384 */       HttpSession session = request.getSession();
/* 385 */       ServletContext ctx = session.getServletContext();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 390 */       int selectedPage = 1;
/* 391 */       int startIndex = 0;
/*     */       
/* 393 */       if (request.getParameter("selectedPage") != null)
/*     */       {
/* 395 */         selectedPage = Integer.parseInt(request.getParameter("selectedPage"));
/*     */       }
/* 397 */       else if (request.getSession().getAttribute("selectedPage") != null)
/*     */       {
/* 399 */         selectedPage = ((Integer)request.getSession().getAttribute("selectedPage")).intValue();
/*     */       }
/*     */       
/* 402 */       int noOfRows = 25;
/*     */       
/* 404 */       if (request.getParameter("noOfRows") != null)
/*     */       {
/* 406 */         noOfRows = Integer.parseInt(request.getParameter("noOfRows"));
/*     */       }
/* 408 */       else if (request.getSession().getAttribute("noOfRows") != null)
/*     */       {
/* 410 */         noOfRows = ((Integer)request.getSession().getAttribute("noOfRows")).intValue();
/*     */       }
/*     */       
/* 413 */       startIndex = (selectedPage - 1) * noOfRows;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 420 */       HashMap widgetProps = new HashMap();
/* 421 */       Vector resIds_vector = new Vector();
/* 422 */       String resourceId = request.getParameter("resourceId") != null ? request.getParameter("resourceId") : null;
/* 423 */       if (EnterpriseUtil.isIt360MSPEdition())
/*     */       {
/* 425 */         if (request.getAttribute("widgetProps") != null)
/*     */         {
/* 427 */           widgetProps = (HashMap)request.getAttribute("widgetProps");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 432 */           widgetProps = CustomerManagementAPI.addCustInfoInWidgetProps(request, widgetProps);
/*     */         }
/* 434 */         resIds_vector = CustomerManagementAPI.filterResourceIds(widgetProps);
/* 435 */         AMLog.debug("resIds_vector in generateHealthHistory()" + resIds_vector);
/*     */       }
/*     */       
/*     */ 
/* 439 */       if (ClientDBUtil.isPrivilegedUser(request))
/*     */       {
/* 441 */         role = "operator";
/*     */       }
/*     */       else
/*     */       {
/* 445 */         role = "user";
/*     */       }
/* 447 */       String type = request.getParameter("type");
/* 448 */       int period = Integer.parseInt(request.getParameter("period"));
/*     */       
/* 450 */       long endtime = ReportUtilities.getRoundedEndTime(period);
/* 451 */       request.setAttribute("endtime", new Date(endtime));
/*     */       
/* 453 */       Hashtable h = null;
/* 454 */       String group = "Others";
/* 455 */       ArrayList aListCategoryLink = new ArrayList(Arrays.asList(Constants.categoryLink));
/* 456 */       if (type != null) {
/* 457 */         if (type.equals("HAI")) {
/* 458 */           group = "HAI";
/* 459 */           if ((role.equals("operator")) && (ctx.getAttribute("mgdrilldown") != null) && (ctx.getAttribute("mgdrilldown").equals("true")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*     */           {
/* 461 */             h = ReportUtilities.getMGHistoryForOperator(owner, period, true, 2, request);
/*     */           }
/* 463 */           else if ((role.equals("operator")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*     */           {
/* 465 */             h = ReportUtilities.getMGHistoryForOperator(owner, period, false, 2, request);
/*     */ 
/*     */ 
/*     */           }
/* 469 */           else if (EnterpriseUtil.isIt360MSPEdition())
/*     */           {
/* 471 */             h = ReportUtilities.getHealthHistoryForMGs(type, period, owner, role, request);
/*     */           }
/*     */           else
/*     */           {
/* 475 */             h = ReportUtilities.getHealthHistoryForMGs(type, period, owner, role, request);
/*     */           }
/*     */           
/*     */         }
/* 479 */         else if ((resourceId != null) && (request.getParameter("isConfMonitor") != null)) {
/* 480 */           String query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo where resourceid=" + resourceId;
/* 481 */           ArrayList eumChildList = new ArrayList();
/* 482 */           if (!Constants.sqlManager) {
/* 483 */             eumChildList = Constants.getEUMChildList();
/*     */           }
/* 485 */           if (eumChildList.contains(resourceId)) {
/* 486 */             h = ReportUtilities.getHealthHistoryById(query, period, true, true);
/*     */           }
/*     */           else {
/* 489 */             h = ReportUtilities.getHealthHistoryById(query, period, true, false);
/*     */           }
/*     */           
/*     */         }
/* 493 */         else if (aListCategoryLink.contains(type))
/*     */         {
/* 495 */           if (EnterpriseUtil.isIt360MSPEdition())
/*     */           {
/* 497 */             h = ReportUtilities.getHealthHistoryByCategoryType(type, period, owner, role, resIds_vector, startIndex, noOfRows, request);
/*     */           }
/*     */           else
/*     */           {
/* 501 */             h = ReportUtilities.getHealthHistoryByCategoryType(type, period, owner, role, null, startIndex, noOfRows, request);
/*     */           }
/* 503 */           request.setAttribute("CategoryType", type);
/*     */ 
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 510 */           String selectedTab = EnterpriseUtil.getSelectedTab(request);
/* 511 */           if (selectedTab != null)
/*     */           {
/* 513 */             if (selectedTab.equals("Server"))
/*     */             {
/* 515 */               type = "servers";
/*     */             }
/* 517 */             else if (selectedTab.equals("Application"))
/*     */             {
/* 519 */               type = "applications";
/*     */             }
/* 521 */             else if (selectedTab.equals("Networks"))
/*     */             {
/* 523 */               type = "$ComplexType_NetworkDevices";
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 528 */           if (EnterpriseUtil.isIt360MSPEdition())
/*     */           {
/* 530 */             h = ReportUtilities.getHealthHistoryByType(type, period, owner, role, resIds_vector, startIndex, noOfRows, request);
/*     */           }
/*     */           else
/*     */           {
/* 534 */             h = ReportUtilities.getHealthHistoryByType(type, period, owner, role, null, startIndex, noOfRows, request);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 540 */         int haid = Integer.parseInt(request.getParameter("haid"));
/* 541 */         boolean isowner = true;
/* 542 */         if ((role != null) && (role.equals("operator"))) {
/* 543 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 544 */           Hashtable haidparents = DBUtil.getParentMGsforChildMGs("('" + haid + "')");
/* 545 */           String haidstoCheck = "(";
/* 546 */           ArrayList temp1 = (ArrayList)haidparents.get(Integer.valueOf(haid));
/* 547 */           if (temp1 != null) {
/* 548 */             for (int i = 0; i < temp1.size(); i++) {
/* 549 */               haidstoCheck = haidstoCheck + "'" + temp1.get(i) + "',";
/*     */             }
/*     */           }
/* 552 */           haidstoCheck = haidstoCheck + "'" + haid + "')";
/*     */           try {
/* 554 */             String q1 = "select * from AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where USERNAME='" + userName + "' and USERID=OWNERID AND HAID IN " + haidstoCheck;
/* 555 */             if (Constants.isUserResourceEnabled()) {
/* 556 */               String loginUserid = Constants.getLoginUserid(request);
/* 557 */               q1 = "select * from AM_USERRESOURCESTABLE where USERID=" + loginUserid + " AND RESOURCEID IN " + haidstoCheck;
/*     */             }
/* 559 */             ResultSet rs1 = AMConnectionPool.executeQueryStmt(q1);
/* 560 */             if (rs1.next())
/*     */             {
/* 562 */               isowner = true;
/*     */             }
/*     */             else {
/* 565 */               isowner = false;
/*     */             }
/* 567 */             rs1.close();
/*     */           }
/*     */           catch (Exception exc) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 574 */         if (isowner) {
/* 575 */           h = ReportUtilities.getHealthHistoryById(haid, period);
/*     */         }
/*     */         else {
/* 578 */           h = ReportUtilities.getHealthHistoryById(haid, period, false, userName, request);
/*     */         }
/*     */       }
/*     */       
/* 582 */       request.setAttribute("resourceMap", getChildMonitorInfo(h));
/* 583 */       request.setAttribute("health_report", h);
/* 584 */       request.setAttribute("isConfMonitor", request.getParameter("isConfMonitor") != null ? "true" : "false");
/* 585 */       return new ActionForward("/jsp/healthcharttop.jsp?group=" + group);
/*     */     } catch (Exception e) {
/* 587 */       e.printStackTrace();
/*     */     }
/* 589 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward generateHyperVDashboard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 593 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 594 */     String pageID = null;
/* 595 */     ResultSet rs = null;
/* 596 */     if ((request.getParameter("type") != null) && ("Hyper-V-Server".equals(request.getParameter("type")))) {
/*     */       try
/*     */       {
/* 599 */         String query = "select VALUE from AM_GLOBALCONFIG where NAME='" + FormatUtil.getString("am.webclient.dashboard.tophyperv.name") + "'";
/* 600 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 601 */         if (rs.next())
/*     */         {
/* 603 */           pageID = rs.getString("VALUE");
/*     */         }
/*     */         
/* 606 */         return new ActionForward("/MyPage.do?method=popOut&pageid=" + pageID);
/*     */       } catch (Exception e) {
/* 608 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 611 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     
/* 615 */     return null;
/*     */   }
/*     */   
/* 618 */   public ActionForward generateVIDashboard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { AMConnectionPool cp = AMConnectionPool.getInstance();
/* 619 */     String pageID = null;
/* 620 */     ResultSet rs = null;
/* 621 */     ActionForward localActionForward; if ((request.getParameter("type") != null) && ("VirtualMachine".equals(request.getParameter("type")))) {
/*     */       try
/*     */       {
/* 624 */         String query = "select VALUE from AM_GLOBALCONFIG where NAME='" + FormatUtil.getString("am.webclient.dashboard.topvm.name") + "'";
/* 625 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 626 */         if (rs.next())
/*     */         {
/* 628 */           pageID = rs.getString("VALUE");
/*     */         }
/*     */         
/* 631 */         return new ActionForward("/MyPage.do?method=popOut&pageid=" + pageID);
/*     */       } catch (Exception e) {
/* 633 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 636 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */       
/* 639 */     } else if ((request.getParameter("type") != null) && ("XenServerHost".equals(request.getParameter("type"))))
/*     */     {
/*     */       try
/*     */       {
/* 643 */         String query = "select VALUE from AM_GLOBALCONFIG where NAME='" + FormatUtil.getString("am.webclient.dashboard.topxenserver.name") + "'";
/* 644 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 645 */         if (rs.next())
/*     */         {
/* 647 */           pageID = rs.getString("VALUE");
/*     */         }
/* 649 */         return new ActionForward("/MyPage.do?method=popOut&pageid=" + pageID);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 653 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */     } else {
/*     */       try
/*     */       {
/* 659 */         String query = "select VALUE from AM_GLOBALCONFIG where NAME='" + FormatUtil.getString("am.webclient.dashboard.topesx.name") + "'";
/* 660 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 661 */         if (rs.next())
/*     */         {
/* 663 */           pageID = rs.getString("VALUE");
/*     */         }
/*     */         
/* 666 */         return new ActionForward("/MyPage.do?method=popOut&pageid=" + pageID);
/*     */       } catch (Exception e) {
/* 668 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 671 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/* 674 */     return null;
/*     */   }
/*     */   
/* 677 */   public ActionForward generateContainerDashboard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { AMConnectionPool cp = AMConnectionPool.getInstance();
/* 678 */     String pageID = DBUtil.getGlobalConfigValue("am.webclient.dashboard.topDockerContainer.name");
/* 679 */     return new ActionForward("/MyPage.do?method=popOut&pageid=" + pageID);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\DashboardAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */