/*     */ package com.manageengine.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.manageengine.appmanager.APIRequestHandler;
/*     */ import com.manageengine.appmanager.utils.MobileUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MobileActions
/*     */   extends DispatchAction
/*     */ {
/*  32 */   List<Properties> viewList = null;
/*     */   private static final int IPHONE_VIEW_LENGTH = 10;
/*     */   private static final int IPHONE_HEADER_HEIGHT = 50;
/*     */   private static final int IPHONE_SUBHEADER_HEIGHT = 26;
/*     */   private static final int IPHONE_FOOTER_HEIGHT = 44;
/*     */   private static final String IPHONE_CONTENT_HEIGHT = "263px";
/*     */   private static final int IPAD_VIEW_LENGTH = 25;
/*     */   private static final String IPAD_CONTENT_HEIGHT = "590px";
/*  40 */   Vector<String> headerVec = new Vector();
/*  41 */   int total = 0; int toalPages = 0; int viewLen = 9; int pageNum = 1; int startInd = 1; int endInd = 9;
/*  42 */   String userName = null; String viewName = null; String pageNumber = null; String action = null; String fromIndexStr = null; String toIndexStr = null; String userAgent = null; String contentHeight = "263px";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward setEnvironment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  51 */     String resp = null;
/*  52 */     StringBuffer result = null;
/*     */     
/*     */     try
/*     */     {
/*  56 */       PrintWriter out = response.getWriter();
/*  57 */       result = new StringBuffer();
/*  58 */       boolean allowClearAlarms = true;
/*  59 */       if (request.isUserInRole("OPERATOR")) {
/*  60 */         allowClearAlarms = DBUtil.getGlobalConfigValueasBoolean("allowClearAlarms");
/*     */       }
/*  62 */       request.getSession().setAttribute("allowClearAlarms", Boolean.toString(allowClearAlarms));
/*  63 */       String mobile = (String)request.getSession().getAttribute("mobile");
/*  64 */       if (!mobile.equals("true"))
/*     */       {
/*  66 */         request.getSession().setAttribute("mobile", "true");
/*     */       }
/*  68 */       String rowsPerPage = (String)request.getSession().getAttribute("MobileRowsCount");
/*  69 */       String screenHeight = request.getParameter("pageScreenHeight");
/*  70 */       if (screenHeight != null)
/*     */       {
/*     */ 
/*  73 */         int pageHeight = Integer.parseInt(screenHeight) - 50 - 26 - 44;
/*     */         
/*  75 */         rowsPerPage = pageHeight / 32 + "";
/*  76 */         request.getSession().setAttribute("MobileRowsCount", rowsPerPage);
/*  77 */         AMLog.info("MOBILE: Successfully Initialized the Rows..");
/*     */       }
/*  79 */       resp = "success";
/*  80 */       result.append(resp);
/*  81 */       out.println(result);
/*  82 */       out.flush();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  86 */       e.printStackTrace();
/*     */     }
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward showDashboards(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  93 */     request.setCharacterEncoding("UTF-8");
/*  94 */     String serverType = "NotEnterpriseAdmin";
/*  95 */     if (EnterpriseUtil.isAdminServer())
/*     */     {
/*  97 */       serverType = "EnterpriseAdmin";
/*     */     }
/*  99 */     request.setAttribute("serverType", serverType);
/* 100 */     response.setContentType("text/html;charset=UTF-8");
/* 101 */     MobileUtils.getDashboardDetails(request);
/* 102 */     return mapping.findForward("dashboardView");
/*     */   }
/*     */   
/*     */   public ActionForward showHomePage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 107 */     request.setCharacterEncoding("UTF-8");
/* 108 */     response.setContentType("text/html;charset=UTF-8");
/* 109 */     getHomePageDetails(request);
/* 110 */     return mapping.findForward("mobileHomePageView");
/*     */   }
/*     */   
/*     */   public ActionForward infrastructureView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 115 */     initializeParams(request, response);
/* 116 */     request.setAttribute("viewId", "infrastructureView");
/* 117 */     this.viewList = MobileUtils.getInfraDetails(request);
/* 118 */     this.headerVec.add(FormatUtil.getString("am.mobile.category.txt"));
/* 119 */     this.headerVec.add(FormatUtil.getString("Health"));
/* 120 */     this.headerVec.add(FormatUtil.getString("am.mobile.outages.txt"));
/* 121 */     request.setAttribute("column", FormatUtil.getString("am.mobile.category.txt"));
/* 122 */     request.setAttribute("title", FormatUtil.getString("am.mobile.infraview.txt"));
/* 123 */     setRequestParams(request);
/* 124 */     return mapping.findForward("infraView");
/*     */   }
/*     */   
/*     */   public ActionForward ListMonitorsForType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 129 */     initializeParams(request, response);
/*     */     
/* 131 */     String type = request.getParameter("type");
/* 132 */     type = type != null ? type : "all";
/* 133 */     String typeimage = MobileUtils.getImagePathForType(type);
/* 134 */     String typeName = request.getParameter("typeName");
/* 135 */     typeName = typeName != null ? typeName : type;
/*     */     
/* 137 */     request.setAttribute("type", type);
/* 138 */     request.setAttribute("typeName", typeName);
/* 139 */     request.setAttribute("typeimage", typeimage);
/* 140 */     request.setAttribute("viewId", "ListMonitorsForType");
/* 141 */     request.setAttribute("uri", "/AppManager/json/ListMonitor?apikey=api_key&type=" + type);
/*     */     
/* 143 */     this.viewList = MobileUtils.getMonitorsForType(request);
/*     */     
/* 145 */     request.setAttribute("column", FormatUtil.getString("am.mobile.category.txt"));
/* 146 */     request.setAttribute("title", FormatUtil.getString("Monitor Type: "));
/* 147 */     this.headerVec.add(FormatUtil.getString("am.mobile.monitor.name.txt"));
/* 148 */     this.headerVec.add(FormatUtil.getString("Availability"));
/* 149 */     this.headerVec.add(FormatUtil.getString("Health"));
/* 150 */     setRequestParams(request, true);
/* 151 */     return mapping.findForward("categoryView");
/*     */   }
/*     */   
/*     */   public ActionForward mobileSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 156 */     initializeParams(request, response);
/* 157 */     if ((this.viewName == null) || (this.viewName.equals("")))
/*     */     {
/* 159 */       this.viewName = FormatUtil.getString("Server");
/*     */     }
/* 161 */     if (request.getParameter("searchTerm") == null)
/*     */     {
/* 163 */       setRequestParams(request);
/* 164 */       return mapping.findForward("SearchPageView");
/*     */     }
/* 166 */     request.setAttribute("viewId", "mobileSearch");
/* 167 */     request.setAttribute("term", request.getParameter("searchTerm"));
/* 168 */     this.headerVec.add(FormatUtil.getString("Name"));
/* 169 */     this.headerVec.add(FormatUtil.getString("Type"));
/* 170 */     this.headerVec.add(FormatUtil.getString("Availability"));
/* 171 */     this.headerVec.add(FormatUtil.getString("Health"));
/*     */     
/* 173 */     this.viewList = MobileUtils.getSearchResults(request);
/* 174 */     request.setAttribute("title", FormatUtil.getString("Applications Manager"));
/* 175 */     setRequestParams(request, true);
/* 176 */     return mapping.findForward("MOList");
/*     */   }
/*     */   
/*     */   public ActionForward monitorGroupView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 181 */     initializeParams(request, response);
/* 182 */     if ((this.viewName == null) || (this.viewName.equals("")))
/*     */     {
/* 184 */       this.viewName = FormatUtil.getString("Server");
/*     */     }
/* 186 */     String title = FormatUtil.getString("am.mobile.monitorgroups.txt");
/* 187 */     this.viewList = MobileUtils.getMonitorGrpsList(request);
/* 188 */     this.headerVec.add(FormatUtil.getString("am.mobile.monitorgroup.txt"));
/* 189 */     this.headerVec.add(FormatUtil.getString("am.mobile.todaysuptime.txt"));
/* 190 */     this.headerVec.add(FormatUtil.getString("am.mobile.outages.txt"));
/* 191 */     request.setAttribute("title", title);
/* 192 */     request.setAttribute("viewId", "monitorGroupView");
/* 193 */     setRequestParams(request, true);
/* 194 */     return mapping.findForward("listView");
/*     */   }
/*     */   
/*     */   public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 199 */     initializeParams(request, response);
/*     */     
/* 201 */     String resp = null;
/* 202 */     StringBuffer result = null;
/*     */     
/*     */     try
/*     */     {
/* 206 */       out = response.getWriter();
/* 207 */       result = new StringBuffer();
/*     */       
/* 209 */       String action = request.getParameter("action");
/* 210 */       String uri = "/AppManager/xml/" + action + "?apikey=api_key&resourceid=" + request.getParameter("resourceid");
/* 211 */       if ((action.equals("PickupAlarm")) || (action.equals("UnpickupAlarm")) || (action.equals("ClearAlarm")))
/*     */       {
/* 213 */         uri = "/AppManager/xml/AlarmAction?apikey=api_key&action=" + action + "&entity=" + request.getParameter("entity");
/*     */       }
/* 215 */       APIRequestHandler apiReqHandler = APIRequestHandler.getInstance();
/* 216 */       request.setAttribute("uri", uri);
/* 217 */       resp = apiReqHandler.getRequestData(request);
/* 218 */       result.append(resp);
/* 219 */       out.println(result);
/* 220 */       out.flush();
/*     */     }
/*     */     catch (Exception e) {
/*     */       PrintWriter out;
/* 224 */       e.printStackTrace();
/* 225 */       if ((resp != null) && (!resp.contains("4000")))
/*     */       {
/*     */         try
/*     */         {
/* 229 */           out = response.getWriter();
/* 230 */           result = new StringBuffer();
/* 231 */           out.println("Failed to " + this.action.toLowerCase() + " the monitor.");
/* 232 */           out.flush();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 236 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 240 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward showMonitorDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 245 */     initializeParams(request, response);
/* 246 */     setRequestParams(request);
/* 247 */     MobileUtils.getMonitorDetails(request);
/* 248 */     return mapping.findForward("MonitorDetailsViewPage");
/*     */   }
/*     */   
/*     */   public ActionForward showMGDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 255 */       initializeParams(request, response);
/* 256 */       if ((this.viewName == null) || (this.viewName.equals("")))
/*     */       {
/* 258 */         this.viewName = FormatUtil.getString("Server");
/*     */       }
/* 260 */       String title = request.getParameter("title");
/* 261 */       MobileUtils.getMGDetails(request);
/* 262 */       this.headerVec.add(FormatUtil.getString("am.mobile.monitorgroup.txt"));
/* 263 */       this.headerVec.add(FormatUtil.getString("Todays Availability"));
/* 264 */       this.headerVec.add(FormatUtil.getString("am.mobile.outages.txt"));
/* 265 */       request.setAttribute("title", title);
/* 266 */       request.setAttribute("viewId", "showMGDetails");
/* 267 */       setRequestParams(request);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 271 */       e.printStackTrace();
/*     */     }
/* 273 */     return mapping.findForward("MGDetailsViewPage");
/*     */   }
/*     */   
/*     */   public ActionForward showHistoryDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 278 */     initializeParams(request, response);
/* 279 */     MobileUtils.getHistoryDetails(request);
/* 280 */     return mapping.findForward("HistoryDetailsViewPage");
/*     */   }
/*     */   
/*     */   public ActionForward showAlarmDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 285 */     initializeParams(request, response);
/* 286 */     if ((this.viewName == null) || (this.viewName.equals("")))
/*     */     {
/* 288 */       this.viewName = FormatUtil.getString("Server");
/*     */     }
/* 290 */     this.viewList = MobileUtils.getAlarmsDetails(request);
/* 291 */     this.headerVec.add(FormatUtil.getString("Name"));
/* 292 */     this.headerVec.add(FormatUtil.getString("Health"));
/* 293 */     this.headerVec.add(FormatUtil.getString("Message"));
/* 294 */     request.setAttribute("viewId", "alarmDetails");
/* 295 */     request.setAttribute("userName", request.getRemoteUser());
/* 296 */     setRequestParams(request);
/* 297 */     return mapping.findForward("AlarmDetails");
/*     */   }
/*     */   
/*     */   public ActionForward listAlarms(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 303 */     initializeParams(request, response);
/* 304 */     if ((this.viewName == null) || (this.viewName.equals("")))
/*     */     {
/* 306 */       this.viewName = "Alarms";
/*     */     }
/* 308 */     this.viewList = MobileUtils.getAlarmsList(request);
/* 309 */     this.headerVec = new Vector();
/* 310 */     this.headerVec.add(FormatUtil.getString("Name"));
/* 311 */     this.headerVec.add(FormatUtil.getString(""));
/* 312 */     this.headerVec.add(FormatUtil.getString("Message"));
/* 313 */     request.setAttribute("viewId", "listAlarms");
/* 314 */     setRequestParams(request, true);
/* 315 */     return mapping.findForward("AlarmViewPage");
/*     */   }
/*     */   
/*     */   public ActionForward listActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 320 */     initializeParams(request, response);
/* 321 */     MobileUtils.getActions(request);
/* 322 */     setRequestParams(request);
/* 323 */     return mapping.findForward("ActionsViewPage");
/*     */   }
/*     */   
/*     */   public ActionForward downDevicesViewAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 328 */     initializeParams(request, response);
/* 329 */     if (request.getParameter("viewName") == null)
/*     */     {
/* 331 */       this.viewName = "DownDevices";
/*     */     }
/* 333 */     request.setAttribute("viewId", "downDevicesViewAction");
/*     */     
/* 335 */     String resids = request.getParameter("resourceids");
/* 336 */     if (resids == null)
/*     */     {
/* 338 */       resids = "-1";
/* 339 */       request.setAttribute("type", "critical");
/* 340 */       this.viewList = MobileUtils.getAlarmsList(request);
/* 341 */       ArrayList<String> downMontrsList = getDownMontrsList(this.viewList);
/* 342 */       for (int i = 0; i < downMontrsList.size(); i++)
/*     */       {
/* 344 */         resids = resids + "," + (String)downMontrsList.get(i);
/*     */       }
/*     */     }
/* 347 */     request.setAttribute("uri", "/AppManager/json/ListMonitor?apikey=api_key&resourceid=" + resids + "&SortOrder=desc&SortBy=LASTALARMTIME,DISPLAYNAME,RESOURCEID");
/*     */     
/* 349 */     this.viewList = MobileUtils.getMonitorsForType(request);
/* 350 */     request.setAttribute("title", FormatUtil.getString("am.mobile.downdevices.txt"));
/* 351 */     this.headerVec.add(FormatUtil.getString("am.mobile.monitor.name.txt"));
/* 352 */     this.headerVec.add(FormatUtil.getString(""));
/* 353 */     this.headerVec.add(FormatUtil.getString("Downtime"));
/* 354 */     setRequestParams(request, true);
/* 355 */     return mapping.findForward("categoryView");
/*     */   }
/*     */   
/*     */   private void initializeParams(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 360 */     this.headerVec = new Vector();
/* 361 */     String userAgent = request.getHeader("User-Agent").toLowerCase();
/* 362 */     String isMobile = request.getSession().getAttribute("mobile") != null ? (String)request.getSession().getAttribute("mobile") : "false";
/* 363 */     int rowsPerPage = 0;
/*     */     
/*     */     try
/*     */     {
/* 367 */       if (request.getSession().getAttribute("MobileRowsCount") != null)
/*     */       {
/* 369 */         rowsPerPage = Integer.parseInt((String)request.getSession().getAttribute("MobileRowsCount"));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 374 */       e.printStackTrace();
/*     */     }
/*     */     
/* 377 */     AMLog.debug("Got useraget==>" + userAgent);
/* 378 */     if ((userAgent != null) && (userAgent.contains("ipad")))
/*     */     {
/* 380 */       this.viewLen = 25;
/* 381 */       this.viewLen = (rowsPerPage > 25 ? rowsPerPage : 25);
/* 382 */       this.contentHeight = "590px";
/*     */     }
/* 384 */     else if ((userAgent != null) && ((userAgent.contains("iphone")) || (isMobile.equals("true"))))
/*     */     {
/* 386 */       this.viewLen = (rowsPerPage > 10 ? rowsPerPage : 10);
/* 387 */       this.contentHeight = "263px";
/*     */     }
/*     */     else
/*     */     {
/* 391 */       this.viewLen = 10;
/* 392 */       this.contentHeight = "263px";
/*     */     }
/*     */     
/* 395 */     this.viewName = request.getParameter("viewName");
/* 396 */     this.pageNumber = request.getParameter("PAGE_NUMBER");
/* 397 */     this.action = request.getParameter("ACTION");
/* 398 */     if ((this.pageNumber != null) && (!this.pageNumber.equals("")))
/*     */     {
/* 400 */       this.pageNum = Integer.parseInt(this.pageNumber);
/* 401 */       if ((this.action != null) && (this.action.equals("NEXT")))
/*     */       {
/* 403 */         this.startInd = (this.pageNum * this.viewLen + 1);
/* 404 */         this.pageNum += 1;
/*     */       }
/* 406 */       else if ((this.action != null) && (this.action.equals("PREVIOUS")))
/*     */       {
/* 408 */         this.pageNum -= 1;
/* 409 */         this.startInd = (this.pageNum * this.viewLen + 1 - this.viewLen);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 414 */       this.pageNum = 1;
/* 415 */       this.startInd = 1;
/*     */     }
/*     */     try
/*     */     {
/* 419 */       request.setCharacterEncoding("UTF-8");
/* 420 */       response.setContentType("text/html;charset=UTF-8");
/* 421 */       response.setContentType("Content-Encoding=charset=UTF-8");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 425 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void setRequestParams(HttpServletRequest request)
/*     */   {
/* 431 */     setRequestParams(request, false);
/*     */   }
/*     */   
/*     */   private void setRequestParams(HttpServletRequest request, boolean isPagingRequired)
/*     */   {
/*     */     try
/*     */     {
/* 438 */       if (this.viewList != null)
/*     */       {
/* 440 */         if (isPagingRequired)
/*     */         {
/* 442 */           this.total = (this.viewList != null ? this.viewList.size() : 0);
/* 443 */           this.toalPages = (this.total % this.viewLen > 0 ? this.total / this.viewLen + 1 : this.total / this.viewLen);
/* 444 */           this.endInd = (this.pageNum == this.toalPages ? this.total : this.startInd + (this.viewLen - 1));
/* 445 */           if (!this.viewList.isEmpty())
/*     */           {
/* 447 */             request.setAttribute("viewList", this.viewList.subList(this.startInd - 1, this.endInd));
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 452 */           request.setAttribute("viewList", this.viewList);
/*     */         }
/*     */       }
/* 455 */       String serverType = "NotEnterpriseAdmin";
/* 456 */       if (EnterpriseUtil.isAdminServer())
/*     */       {
/* 458 */         serverType = "EnterpriseAdmin";
/*     */       }
/* 460 */       request.setAttribute("serverType", serverType);
/*     */       
/* 462 */       request.setAttribute("contentHeight", this.contentHeight);
/* 463 */       request.setAttribute("headerList", this.headerVec);
/* 464 */       request.setAttribute("TOTAL_PAGES", new Integer(this.toalPages));
/* 465 */       request.setAttribute("PAGE_NUMBER", new Integer(this.pageNum));
/* 466 */       request.setAttribute("FROM_INDEX", new Integer(this.startInd));
/* 467 */       request.setAttribute("TO_INDEX", new Integer(this.endInd));
/* 468 */       request.setAttribute("TOTAL_RECORDS", new Integer(this.total));
/* 469 */       request.setAttribute("viewName", this.viewName);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 473 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void getHomePageDetails(HttpServletRequest request)
/*     */   {
/*     */     try
/*     */     {
/* 481 */       String serverType = "NotEnterpriseAdmin";
/* 482 */       if (EnterpriseUtil.isAdminServer())
/*     */       {
/* 484 */         serverType = "EnterpriseAdmin";
/*     */       }
/* 486 */       request.setAttribute("serverType", serverType);
/* 487 */       request.setAttribute("type", "critical");
/* 488 */       String resids = "-1";
/* 489 */       this.viewList = MobileUtils.getAlarmsList(request);
/* 490 */       request.setAttribute("alarms", Integer.valueOf(this.viewList.size()));
/* 491 */       ArrayList<String> downMontrsList = getDownMontrsList(this.viewList);
/* 492 */       for (int i = 0; i < downMontrsList.size(); i++)
/*     */       {
/* 494 */         resids = resids + "," + (String)downMontrsList.get(i);
/*     */       }
/* 496 */       request.setAttribute("resourceIds", resids);
/* 497 */       request.setAttribute("DownDevices", Integer.valueOf(downMontrsList.size()));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 501 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private ArrayList<String> getDownMontrsList(List<Properties> list) {
/* 506 */     ArrayList<String> toReturn = new ArrayList();
/*     */     try
/*     */     {
/* 509 */       for (int i = 0; i < list.size(); i++)
/*     */       {
/* 511 */         Properties props = (Properties)list.get(i);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 516 */         if ((!props.getProperty("type").equals("HAI")) && (props.getProperty("availseverity").equals("1")))
/*     */         {
/* 518 */           toReturn.add(props.getProperty("resourceid"));
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 523 */       e.printStackTrace();
/*     */     }
/* 525 */     return toReturn;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\appmanager\struts\actions\MobileActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */