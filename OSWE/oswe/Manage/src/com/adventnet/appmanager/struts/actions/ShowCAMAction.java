/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.ReportCustomAttributeUtil;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.action.DynaActionForm;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShowCAMAction
/*     */   extends DispatchAction
/*     */ {
/*  50 */   private AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showCAMApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  74 */     ActionMessages messages = new ActionMessages();
/*  75 */     ActionErrors errors = new ActionErrors();
/*  76 */     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/*  77 */     request.setAttribute("hideFieldsForIT360", hideFieldsForIT360);
/*     */     
/*     */     try
/*     */     {
/*  81 */       String camID = request.getParameter("camid");
/*  82 */       String haID = request.getParameter("haid");
/*  83 */       List screenList = CAMDBUtil.getScreens(Integer.parseInt(camID));
/*     */       
/*  85 */       if (screenList.size() > 0)
/*     */       {
/*  87 */         request.setAttribute("screenid", ((List)screenList.get(0)).get(0));
/*  88 */         return showScreen(mapping, form, request, response);
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*  95 */       ee.printStackTrace();
/*  96 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.check", ee.toString()));
/*  97 */       throw ee;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 111 */     return mapping.findForward("showcamapplication");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 137 */     if (request.getAttribute("camid") == null) {
/* 138 */       request.setAttribute("camid", request.getParameter("camid"));
/*     */     }
/*     */     
/*     */ 
/* 142 */     if ((request.getAttribute("haid") == null) && (request.getParameter("haid") != null)) {
/* 143 */       request.setAttribute("haid", request.getParameter("haid"));
/*     */     }
/*     */     
/* 146 */     if ((request.getAttribute("screenid") == null) && (request.getParameter("screenid") != null)) {
/* 147 */       request.setAttribute("screenid", request.getParameter("screenid"));
/*     */     }
/*     */     
/* 150 */     if (request.getParameter("attributeid") != null)
/*     */     {
/* 152 */       request.setAttribute("attributeid", request.getParameter("attributeid"));
/*     */     }
/*     */     
/* 155 */     String screenID = (String)request.getAttribute("screenid");
/* 156 */     List screenInfo = CAMDBUtil.getScreenInfo(Long.parseLong(screenID));
/* 157 */     request.setAttribute("screeninfo", screenInfo);
/*     */     
/*     */ 
/*     */ 
/* 161 */     int posOfType = 3;
/* 162 */     if (screenInfo.get(posOfType).equals("1")) {
/* 163 */       List attributesList = CAMDBUtil.getAttributesForScreen(Long.parseLong(screenID));
/*     */       
/* 165 */       request.setAttribute("attributeslist", attributesList);
/* 166 */       return mapping.findForward("showdashboardscreen");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 172 */     return mapping.findForward("showscreen");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showSingleGraphScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 188 */     ActionErrors errors = new ActionErrors();
/*     */     
/*     */     try
/*     */     {
/* 192 */       String selectedscheme = "";
/* 193 */       if (request.getParameter("selectedscheme") != null)
/*     */       {
/* 195 */         selectedscheme = request.getParameter("selectedscheme");
/* 196 */         request.setAttribute("selectedscheme", selectedscheme);
/*     */       }
/* 198 */       String selectedSkin = "Grey";
/* 199 */       if (request.getParameter("selectedSkin") != null)
/*     */       {
/* 201 */         selectedSkin = request.getParameter("selectedSkin");
/* 202 */         request.setAttribute("selectedskin", selectedSkin);
/*     */       }
/*     */       
/* 205 */       if (request.getParameter("attributeid") != null)
/*     */       {
/* 207 */         request.setAttribute("attributeid", request.getParameter("attributeid"));
/* 208 */         Map attributeInfo = CAMDBUtil.getAttributeInfo(Integer.parseInt(request.getParameter("attributeid")));
/* 209 */         request.setAttribute("attributeinfo", attributeInfo);
/*     */       } else {
/* 211 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Cannot not generate the graph. There was no attribute mentioned for which graph has to be generated."));
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 218 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Could not generate the grapgh. Either an attribute was not specified or a non-numeric attribute was selected for graphing."));
/*     */     }
/*     */     
/*     */ 
/* 222 */     if (!errors.isEmpty()) {
/* 223 */       saveErrors(request, errors);
/* 224 */       return mapping.getInputForward();
/*     */     }
/*     */     
/* 227 */     return mapping.findForward("showsingleattributegraph");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward addScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 246 */     if (request.getAttribute("camid") == null) {
/* 247 */       request.setAttribute("camid", request.getParameter("camid"));
/*     */     }
/*     */     
/*     */ 
/* 251 */     if ((request.getAttribute("haid") == null) && (request.getParameter("haid") != null)) {
/* 252 */       request.setAttribute("haid", request.getParameter("haid"));
/*     */     }
/* 254 */     String camID = request.getParameter("camid");
/* 255 */     List list = CAMDBUtil.getCAMDetails(camID);
/* 256 */     String camName = (String)list.get(0);
/* 257 */     String camDescription = (String)list.get(2);
/* 258 */     request.setAttribute("camname", camName);
/* 259 */     request.setAttribute("camdesc", camDescription);
/*     */     
/* 261 */     List camScreenTypesList = CAMDBUtil.getScreenTypes();
/* 262 */     request.setAttribute("camscreentypeslist", camScreenTypesList);
/*     */     
/* 264 */     return mapping.findForward("addscreen");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward configureScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 279 */     if ((request.getAttribute("screenid") == null) && (request.getParameter("screenid") != null)) {
/* 280 */       request.setAttribute("screenid", request.getParameter("screenid"));
/*     */     }
/*     */     
/*     */ 
/* 284 */     String screenID = (String)request.getAttribute("screenid");
/*     */     
/*     */ 
/*     */ 
/* 288 */     if ((request.getAttribute("haid") == null) && (request.getParameter("haid") != null)) {
/* 289 */       request.setAttribute("haid", request.getParameter("haid"));
/*     */     }
/*     */     
/*     */ 
/* 293 */     String camID = request.getParameter("camid");
/* 294 */     String isFromResourcePage = request.getParameter("isfromresourcepage");
/*     */     
/* 296 */     request.setAttribute("camid", camID);
/* 297 */     List list = CAMDBUtil.getCAMDetails(camID);
/*     */     
/* 299 */     request.setAttribute("camname", list.get(0));
/* 300 */     request.setAttribute("camdesc", list.get(2));
/* 301 */     String type = (String)list.get(1);
/* 302 */     if ("true".equals(isFromResourcePage)) {
/* 303 */       request.setAttribute("resourceid", camID);
/* 304 */       request.setAttribute("isfromresourcepage", isFromResourcePage);
/*     */       
/* 306 */       if (!CAMDBUtil.anyAttributeForScreen(screenID))
/*     */       {
/*     */ 
/* 309 */         if (type.equals("SAP-CCMS"))
/*     */         {
/* 311 */           return new ActionForward("/sap.do?method=showCCMSMonitors&resourceid=" + camID);
/*     */         }
/* 313 */         return new ActionForward("/CAMChooseDomains.do?method=chooseDomains");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 319 */     List agentsList = CAMDBUtil.getDataCollectableAgentDetails(true, request);
/* 320 */     request.setAttribute("datacollectableagents", agentsList);
/*     */     
/* 322 */     return mapping.findForward("configurescreen");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward deleteAttributeConfigureForScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 333 */     ActionMessages messages = new ActionMessages();
/* 334 */     ActionErrors errors = new ActionErrors();
/* 335 */     if ((request.getAttribute("screenid") == null) && (request.getParameter("screenid") != null)) {
/* 336 */       request.setAttribute("screenid", request.getParameter("screenid"));
/*     */     }
/*     */     
/*     */ 
/* 340 */     if ((request.getAttribute("haid") == null) && (request.getParameter("haid") != null)) {
/* 341 */       request.setAttribute("haid", request.getParameter("haid"));
/*     */     }
/*     */     
/*     */ 
/* 345 */     String camID = request.getParameter("camid");
/* 346 */     String type = "Custom-Application";
/* 347 */     request.setAttribute("camid", camID);
/* 348 */     List list = CAMDBUtil.getCAMDetails(camID);
/*     */     
/*     */ 
/* 351 */     request.setAttribute("camname", list.get(0));
/* 352 */     request.setAttribute("camdesc", list.get(2));
/* 353 */     type = (String)list.get(1);
/*     */     
/* 355 */     String isFromResourcePage = request.getParameter("isfromresourcepage");
/*     */     
/* 357 */     if ("true".equals(isFromResourcePage)) {
/* 358 */       request.setAttribute("resourceid", camID);
/* 359 */       request.setAttribute("isfromresourcepage", isFromResourcePage);
/*     */     }
/*     */     
/* 362 */     String[] selectedAttribs = (String[])((DynaActionForm)form).get("choosenattributeid");
/* 363 */     String commaSepTableIDs = request.getParameter("tableids");
/*     */     
/* 365 */     int size = selectedAttribs.length;
/*     */     
/* 367 */     if (size > 0)
/*     */     {
/* 369 */       String commaSepStr = "";
/* 370 */       for (int i = 0; i < size; i++) {
/* 371 */         commaSepStr = commaSepStr + selectedAttribs[i];
/* 372 */         if (i < size - 1) {
/* 373 */           commaSepStr = commaSepStr + ",";
/*     */         }
/*     */       }
/*     */       
/* 377 */       ArrayList<String> camDcGroupids = CAMDBUtil.getCamDcGroupsId(commaSepStr);
/* 378 */       int screenID = Integer.parseInt((String)request.getAttribute("screenid"));
/* 379 */       CAMDBUtil.deleteAttributes(commaSepStr, screenID);
/* 380 */       if (!commaSepTableIDs.equals(""))
/*     */       {
/* 382 */         String fordelete = "";
/* 383 */         StringTokenizer st = new StringTokenizer(commaSepTableIDs.substring(0, commaSepTableIDs.length() - 1), ",");
/* 384 */         while (st.hasMoreTokens())
/*     */         {
/* 386 */           String tableid = st.nextToken();
/* 387 */           if (!CAMDBUtil.ifExists("AM_CAM_TABLE_COLUMN_MAPPER", "TABLEATTRIBUTEID", tableid))
/*     */           {
/* 389 */             fordelete = fordelete + tableid + ",";
/*     */           }
/*     */         }
/* 392 */         if (!fordelete.equals(""))
/*     */         {
/* 394 */           CAMDBUtil.deleteAttributes(fordelete.substring(0, fordelete.length() - 1), screenID);
/*     */         }
/*     */       }
/* 397 */       CAMDBUtil.deleteOrphanedDcGroups(camID, camDcGroupids);
/*     */       
/* 399 */       boolean anyAttribDeleted = true;
/* 400 */       if (anyAttribDeleted) {
/* 401 */         Hashtable healthkeys = (Hashtable)Constants.getGlobalObject("healthkeys");
/* 402 */         new AMRCAnalyser().applyRCA(Integer.parseInt(camID), Integer.parseInt((String)healthkeys.get(type)), System.currentTimeMillis());
/*     */       }
/*     */       
/* 405 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.screens.attributes.deleted.success"));
/* 406 */       saveMessages(request, messages);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 412 */     return mapping.findForward("configurescreen");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward deleteScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 423 */     ActionMessages messages = new ActionMessages();
/* 424 */     ActionErrors errors = new ActionErrors();
/* 425 */     String screenID = request.getParameter("screenid");
/*     */     
/* 427 */     if ((request.getAttribute("screenid") == null) && (screenID != null)) {
/* 428 */       request.setAttribute("screenid", request.getParameter("screenid"));
/*     */     }
/*     */     
/*     */ 
/* 432 */     if ((request.getAttribute("haid") == null) && (request.getParameter("haid") != null)) {
/* 433 */       request.setAttribute("haid", request.getParameter("haid"));
/*     */     }
/*     */     
/*     */ 
/* 437 */     String camID = request.getParameter("camid");
/* 438 */     request.setAttribute("camid", camID);
/* 439 */     List list = CAMDBUtil.getCAMDetails(camID);
/*     */     
/* 441 */     request.setAttribute("camname", list.get(0));
/* 442 */     request.setAttribute("camdesc", list.get(2));
/*     */     
/* 444 */     boolean status = CAMDBUtil.deleteScreen(Integer.parseInt(screenID));
/*     */     
/* 446 */     if (status) {
/* 447 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.screens.deleted.success"));
/* 448 */       saveMessages(request, messages);
/*     */     } else {
/* 450 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("cam.screens.deleted.failed"));
/* 451 */       saveErrors(request, errors);
/*     */     }
/*     */     
/*     */ 
/* 455 */     return mapping.findForward("success");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward enableReportsForScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 467 */     ActionMessages messages = new ActionMessages();
/* 468 */     ActionErrors errors = new ActionErrors();
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 473 */       if ((request.getAttribute("screenid") == null) && (request.getParameter("screenid") != null)) {
/* 474 */         request.setAttribute("screenid", request.getParameter("screenid"));
/*     */       }
/*     */       
/*     */ 
/* 478 */       if ((request.getAttribute("haid") == null) && (request.getParameter("haid") != null)) {
/* 479 */         request.setAttribute("haid", request.getParameter("haid"));
/*     */       }
/*     */       
/* 482 */       String camID = request.getParameter("camid");
/* 483 */       request.setAttribute("camid", camID);
/* 484 */       List list = CAMDBUtil.getCAMDetails(camID);
/*     */       
/* 486 */       request.setAttribute("camname", list.get(0));
/* 487 */       request.setAttribute("camdesc", list.get(2));
/*     */       
/*     */ 
/* 490 */       String isFromResourcePage = request.getParameter("isfromresourcepage");
/*     */       
/* 492 */       if ("true".equals(isFromResourcePage)) {
/* 493 */         request.setAttribute("resourceid", camID);
/* 494 */         request.setAttribute("isfromresourcepage", isFromResourcePage);
/*     */       }
/*     */       
/*     */ 
/* 498 */       boolean anyAttribIsNonNumeric = false;
/* 499 */       String[] selectedAttribs = (String[])((DynaActionForm)form).get("choosenattributeid");
/* 500 */       int size = selectedAttribs.length;
/* 501 */       if (size > 0)
/*     */       {
/* 503 */         List listOfAllowedAttribs = CAMDBUtil.getReportsAllowedAttributes(Long.parseLong(request.getParameter("screenid")));
/* 504 */         for (int i = 0; i < size; i++)
/*     */         {
/* 506 */           String attributeid = selectedAttribs[i];
/* 507 */           if (listOfAllowedAttribs.indexOf("" + attributeid) != -1)
/*     */           {
/* 509 */             CAMDBUtil.addToArchiverConfig(attributeid);
/* 510 */             ReportCustomAttributeUtil.enableReportsInAttributesExt(attributeid);
/*     */           } else {
/* 512 */             anyAttribIsNonNumeric = true;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 518 */       if (anyAttribIsNonNumeric) {
/* 519 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.reports.someenabled"));
/*     */       } else {
/* 521 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.reports.enabled.success"));
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 525 */       e.printStackTrace();
/* 526 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("cam.reports.enabled.failed"));
/*     */     }
/*     */     
/* 529 */     if (!errors.isEmpty()) {
/* 530 */       saveErrors(request, errors);
/*     */     }
/*     */     
/* 533 */     if (!messages.isEmpty()) {
/* 534 */       saveMessages(request, messages);
/*     */     }
/*     */     
/* 537 */     ReportUtil.loadAllAttributeDetails();
/* 538 */     return mapping.findForward("configurescreen");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward disableReportsForScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 546 */     ActionMessages messages = new ActionMessages();
/* 547 */     ActionErrors errors = new ActionErrors();
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 552 */       if ((request.getAttribute("screenid") == null) && (request.getParameter("screenid") != null)) {
/* 553 */         request.setAttribute("screenid", request.getParameter("screenid"));
/*     */       }
/*     */       
/*     */ 
/* 557 */       if ((request.getAttribute("haid") == null) && (request.getParameter("haid") != null)) {
/* 558 */         request.setAttribute("haid", request.getParameter("haid"));
/*     */       }
/*     */       
/* 561 */       String camID = request.getParameter("camid");
/* 562 */       request.setAttribute("camid", camID);
/* 563 */       List list = CAMDBUtil.getCAMDetails(camID);
/*     */       
/* 565 */       request.setAttribute("camname", list.get(0));
/* 566 */       request.setAttribute("camdesc", list.get(2));
/*     */       
/*     */ 
/* 569 */       String isFromResourcePage = request.getParameter("isfromresourcepage");
/*     */       
/* 571 */       if ("true".equals(isFromResourcePage)) {
/* 572 */         request.setAttribute("resourceid", camID);
/* 573 */         request.setAttribute("isfromresourcepage", isFromResourcePage);
/*     */       }
/*     */       
/*     */ 
/* 577 */       String[] selectedAttribs = (String[])((DynaActionForm)form).get("choosenattributeid");
/* 578 */       int size = selectedAttribs.length;
/* 579 */       if (size > 0)
/*     */       {
/* 581 */         for (int i = 0; i < size; i++)
/*     */         {
/* 583 */           String attributeid = selectedAttribs[i];
/* 584 */           CAMDBUtil.removeFromArchiverConfig(attributeid);
/* 585 */           ReportCustomAttributeUtil.disableReportsInAttributesExt(attributeid);
/*     */         }
/*     */       }
/*     */       
/* 589 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.reports.disabled.success"));
/*     */     }
/*     */     catch (Exception e) {
/* 592 */       e.printStackTrace();
/* 593 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("cam.reports.disabled.failed"));
/*     */     }
/*     */     
/*     */ 
/* 597 */     if (!errors.isEmpty()) {
/* 598 */       saveErrors(request, errors);
/*     */     }
/*     */     
/* 601 */     if (!messages.isEmpty()) {
/* 602 */       saveMessages(request, messages);
/*     */     }
/* 604 */     ReportUtil.loadAllAttributeDetails();
/* 605 */     return mapping.findForward("configurescreen");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward disableReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 614 */     ActionMessages messages = new ActionMessages();
/* 615 */     ActionErrors errors = new ActionErrors();
/*     */     try
/*     */     {
/* 618 */       String[] selectedAttribs = (String[])((DynaActionForm)form).get("choosenattributeid");
/* 619 */       int size = selectedAttribs.length;
/* 620 */       if (size > 0)
/*     */       {
/* 622 */         for (int i = 0; i < size; i++)
/*     */         {
/* 624 */           String attributeid = selectedAttribs[i];
/* 625 */           CAMDBUtil.removeFromArchiverConfig(attributeid);
/* 626 */           ReportCustomAttributeUtil.disableReportsInAttributesExt(attributeid);
/*     */         }
/*     */         
/* 629 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.reports.globalconfig.disable.success"));
/* 630 */         saveMessages(request, messages);
/*     */       }
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 635 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("cam.reports.globalconfig.disable.failed"));
/*     */     }
/*     */     
/* 638 */     if (!errors.isEmpty()) {
/* 639 */       saveErrors(request, errors);
/* 640 */       return mapping.getInputForward();
/*     */     }
/* 642 */     ReportUtil.loadAllAttributeDetails();
/* 643 */     return new ActionForward("/showReports.do?actionMethod=getReportIndex");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward enableReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 652 */     ActionMessages messages = new ActionMessages();
/* 653 */     ActionErrors errors = new ActionErrors();
/*     */     
/*     */     try
/*     */     {
/* 657 */       String[] selectedAttribs = (String[])((DynaActionForm)form).get("choosenattributeid");
/* 658 */       int size = selectedAttribs.length;
/* 659 */       if (size > 0)
/*     */       {
/* 661 */         for (int i = 0; i < size; i++)
/*     */         {
/* 663 */           String attributeid = selectedAttribs[i];
/* 664 */           CAMDBUtil.addToArchiverConfig(attributeid);
/* 665 */           ReportCustomAttributeUtil.enableReportsInAttributesExt(attributeid);
/*     */         }
/* 667 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.reports.globalconfig.enable.success"));
/* 668 */         saveMessages(request, messages);
/*     */       }
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 673 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("cam.reports.globalconfig.enable.failed"));
/*     */     }
/*     */     
/* 676 */     if (!errors.isEmpty()) {
/* 677 */       saveErrors(request, errors);
/* 678 */       return mapping.getInputForward();
/*     */     }
/* 680 */     ReportUtil.loadAllAttributeDetails();
/* 681 */     return new ActionForward("/showReports.do?actionMethod=getReportIndex");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward bulkEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 691 */     ActionMessages messages = new ActionMessages();
/* 692 */     ActionErrors errors = new ActionErrors();
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 697 */       if ((request.getAttribute("screenid") == null) && (request.getParameter("screenid") != null)) {
/* 698 */         request.setAttribute("screenid", request.getParameter("screenid"));
/*     */       }
/* 700 */       int type = 0;
/*     */       try {
/* 702 */         type = ((Integer)((DynaActionForm)form).get("type")).intValue();
/*     */       }
/*     */       catch (Exception ee) {}
/* 705 */       boolean anyAttribIsNonNumeric = false;
/* 706 */       ArrayList attribs = new ArrayList();
/* 707 */       String selectedAttribs = request.getParameter("selectedids");
/*     */       try {
/* 709 */         StringTokenizer st = new StringTokenizer(selectedAttribs, ",");
/* 710 */         while (st.hasMoreTokens())
/*     */         {
/* 712 */           attribs.add(st.nextToken());
/*     */         }
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/* 717 */       int size = attribs.size();
/*     */       
/* 719 */       if (size > 0)
/*     */       {
/* 721 */         List listOfAllowedAttribs = CAMDBUtil.getReportsAllowedAttributes(Long.parseLong(request.getParameter("screenid")));
/* 722 */         for (int i = 0; i < size; i++)
/*     */         {
/* 724 */           String attributeid = (String)attribs.get(i);
/* 725 */           int j = listOfAllowedAttribs.indexOf("" + attributeid);
/* 726 */           if (listOfAllowedAttribs.indexOf(attributeid) != -1)
/*     */           {
/* 728 */             CAMDBUtil.updateAttributes(attributeid, type);
/*     */           } else {
/* 730 */             anyAttribIsNonNumeric = true;
/*     */           }
/*     */         }
/*     */       }
/* 734 */       if (anyAttribIsNonNumeric) {
/* 735 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.attributes.someenabled"));
/*     */       } else
/* 737 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("Updated Successfully.Wait till the next polling Interval"));
/*     */     } catch (Exception e) {
/* 739 */       e.printStackTrace();
/*     */     }
/* 741 */     saveMessages(request, messages);
/*     */     
/*     */ 
/* 744 */     return new ActionForward("/jsp/bulk_edit.jsp");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ShowCAMAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */