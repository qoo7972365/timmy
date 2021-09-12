/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.server.template.AMProcessTemplateManager;
/*     */ import com.adventnet.appmanager.server.template.AMTemplateConfiguration;
/*     */ import com.adventnet.appmanager.server.template.MonitorGroupUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMProcessTemplateForm;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.utilities.stringutils.StrUtil;
/*     */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ProcessTemplateAction extends org.apache.struts.actions.DispatchAction
/*     */ {
/*  32 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */   public ActionForward showAllTemplates(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*     */     try {
/*  36 */       String templateType = request.getParameter("templatetype");
/*  37 */       Map templatemap = new AMProcessTemplateManager().fetchAllTemplates(Integer.parseInt(templateType), request);
/*  38 */       request.setAttribute("TemplateDetails", templatemap);
/*  39 */       request.setAttribute("templatetype", templateType);
/*  40 */       request.setAttribute("templatetypestr", getTemplateTypeStr(templateType));
/*     */     } catch (Exception e) {
/*  42 */       e.printStackTrace();
/*     */     }
/*  44 */     return mapping.findForward("showalltemplates");
/*     */   }
/*     */   
/*     */   public ActionForward associateProcessTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  49 */     Logger logger = AMProcessTemplateManager.getLogger();
/*  50 */     java.util.Enumeration allparams = request.getParameterNames();
/*  51 */     while (allparams.hasMoreElements()) {
/*  52 */       String ParameterNames = (String)allparams.nextElement();
/*  53 */       logger.log(Level.INFO, ParameterNames + " " + request.getParameter(ParameterNames));
/*     */     }
/*  55 */     if (!isTokenValid(request)) {
/*  56 */       return showAllTemplates(mapping, form, request, response);
/*     */     }
/*     */     try
/*     */     {
/*  60 */       resetToken(request);
/*  61 */       String templateType = request.getParameter("templatetype") != null ? request.getParameter("templatetype") : "0";
/*  62 */       AMTemplateConfiguration templateconfig = com.adventnet.appmanager.server.template.AMTemplateFactory.getAMTemplateConfiguration(Integer.parseInt(templateType));
/*  63 */       AMProcessTemplateManager templatemanager = new AMProcessTemplateManager();
/*  64 */       request.setAttribute("templatetype", templateType);
/*  65 */       request.setAttribute("templatetypestr", getTemplateTypeStr(templateType));
/*  66 */       String templatename = request.getParameter("templateName");
/*  67 */       String templateDescription = request.getParameter("templateDescription");
/*  68 */       String matchinterval = "0";
/*  69 */       if (request.getParameter("matchinterval") != null) {
/*  70 */         matchinterval = request.getParameter("matchinterval");
/*     */       }
/*  72 */       templateconfig.setTemplateName(templatename);
/*  73 */       templateconfig.setTemplateDescription(templateDescription);
/*  74 */       templateconfig.setTemplateType(Integer.parseInt(templateType));
/*  75 */       templateconfig.setMatchintevel(Integer.parseInt(matchinterval));
/*  76 */       int loginuserID = DelegatedUserRoleUtil.getLoginUserid(request);
/*  77 */       templateconfig.setLoginUserID(loginuserID);
/*     */       
/*  79 */       String[] selectedprocess = request.getParameterValues("processcheckbox");
/*  80 */       List<String> processretainedAfterEdit = new ArrayList();
/*  81 */       Map<String, String[]> processdefnModAfterEdit = new HashMap();
/*  82 */       if ((selectedprocess != null) && (selectedprocess.length > 0)) {
/*  83 */         HashMap processidmap = new HashMap();
/*  84 */         for (int i = 0; i < selectedprocess.length; i++) {
/*  85 */           logger.log(Level.INFO, " SELECTED PROCESS " + selectedprocess[i]);
/*  86 */           String[] processnamearg = new String[5];
/*  87 */           String selectprocessid = selectedprocess[i];
/*  88 */           processnamearg[0] = request.getParameter("pname_" + selectprocessid);
/*  89 */           processnamearg[1] = request.getParameter("pcmd_" + selectprocessid);
/*  90 */           processnamearg[2] = request.getParameter("pnameregex_" + selectprocessid);
/*  91 */           processnamearg[3] = request.getParameter("pcmdregex_" + selectprocessid);
/*  92 */           processnamearg[4] = request.getParameter("pdispname_" + selectprocessid);
/*  93 */           if (processnamearg[0] != null) {
/*  94 */             logger.log(Level.INFO, "ADDED PROCESSID " + selectprocessid + " " + processnamearg[4] + " " + processnamearg[0] + " " + processnamearg[1]);
/*  95 */             processnamearg[0] = processnamearg[0].trim();
/*  96 */             if (processnamearg[1] != null) {
/*  97 */               processnamearg[1] = processnamearg[1].trim();
/*     */             }
/*  99 */             if ((processnamearg[4] == null) || (processnamearg[4].trim().length() == 0))
/*     */             {
/* 101 */               processnamearg[4] = processnamearg[0];
/*     */             }
/* 103 */             processnamearg[4] = processnamearg[4].trim();
/* 104 */             processidmap.put(selectprocessid, processnamearg);
/* 105 */           } else if ((selectprocessid != null) && (selectprocessid.trim().length() > 0)) {
/* 106 */             logger.log(Level.INFO, "EXISTING PROCESS " + selectprocessid);
/* 107 */             processretainedAfterEdit.add(selectprocessid);
/* 108 */             String[] modifiedprocessdefn = new String[5];
/* 109 */             modifiedprocessdefn[0] = request.getParameter("pnameedit_" + selectprocessid);
/* 110 */             modifiedprocessdefn[1] = request.getParameter("pcmdedit_" + selectprocessid);
/* 111 */             modifiedprocessdefn[2] = request.getParameter("pnameregexedit_" + selectprocessid);
/* 112 */             modifiedprocessdefn[3] = request.getParameter("pcmdregexedit_" + selectprocessid);
/* 113 */             modifiedprocessdefn[4] = request.getParameter("pdispnameedit_" + selectprocessid);
/* 114 */             if (modifiedprocessdefn[0] != null) {
/* 115 */               logger.log(Level.INFO, "PROCESS DEFN MODIFIED AFTER EDIT " + selectprocessid + "  DISPNAME " + modifiedprocessdefn[4] + " MODNAME " + modifiedprocessdefn[0] + " MODIFIEDARG " + modifiedprocessdefn[1]);
/* 116 */               if (modifiedprocessdefn[1] != null) {
/* 117 */                 modifiedprocessdefn[1] = modifiedprocessdefn[1].trim();
/* 118 */                 modifiedprocessdefn[1] = org.htmlparser.util.Translate.decode(modifiedprocessdefn[1]);
/*     */               }
/* 120 */               if ((modifiedprocessdefn[4] == null) || (modifiedprocessdefn[4].trim().length() == 0))
/*     */               {
/* 122 */                 modifiedprocessdefn[4] = modifiedprocessdefn[0];
/*     */               }
/* 124 */               modifiedprocessdefn[4] = modifiedprocessdefn[4].trim();
/* 125 */               processdefnModAfterEdit.put(selectprocessid, modifiedprocessdefn);
/*     */             }
/*     */           }
/*     */         }
/* 129 */         templateconfig.setPSRetainedAfterEdit(processretainedAfterEdit);
/* 130 */         templateconfig.setPSAddedAfterEdit(processidmap);
/* 131 */         templateconfig.setPSDefinition(processidmap);
/* 132 */         templateconfig.setPSDefnModAfterEdit(processdefnModAfterEdit);
/* 133 */         request.setAttribute("cacheprocessid", processidmap);
/*     */       }
/*     */       
/*     */ 
/* 137 */       Map allattributedetails = templateconfig.getAttributeDetails();
/* 138 */       HashMap templateattributethresholdmap = new HashMap();
/* 139 */       Iterator keyitr = allattributedetails.keySet().iterator();
/* 140 */       while (keyitr.hasNext()) {
/* 141 */         String attributeid = (String)keyitr.next();
/* 142 */         HashMap singleattributeinfo = (HashMap)allattributedetails.get(attributeid);
/* 143 */         HashMap singleattributethresholdmap = new HashMap();
/* 144 */         if (attributeid != null) {
/* 145 */           String thresholdid = request.getParameter("threshold_" + attributeid);
/* 146 */           if ((thresholdid != null) && (thresholdid.length() > 0)) {
/* 147 */             singleattributethresholdmap.put("thresholdid", thresholdid);
/*     */           }
/* 149 */           String thresholdname = request.getParameter("thresholdname_" + attributeid);
/* 150 */           if ((thresholdname != null) && (thresholdname.length() > 0)) {
/* 151 */             String thresholdidquery = "select ID from AM_THRESHOLDCONFIG where NAME='" + thresholdname + "'";
/* 152 */             ArrayList thresholdidresult = this.mo.getRows(thresholdidquery);
/* 153 */             if ((thresholdidresult != null) && (thresholdidresult.size() > 0)) {
/* 154 */               List thresholdrow = (List)thresholdidresult.get(0);
/* 155 */               thresholdid = (String)thresholdrow.get(0);
/* 156 */               if (thresholdid != null) {
/* 157 */                 singleattributethresholdmap.put("thresholdid", thresholdid);
/*     */               }
/*     */             }
/*     */           }
/* 161 */           String criticalactions = request.getParameter("criticalaction_" + attributeid);
/* 162 */           if ((criticalactions != null) && (criticalactions.length() > 0)) {
/* 163 */             String[] criticalactionsArr = criticalactions.split(",");
/* 164 */             List<String> criticalactionlist = new ArrayList();
/* 165 */             for (String criticalaction : criticalactionsArr) {
/* 166 */               criticalactionlist.add(criticalaction);
/*     */             }
/* 168 */             singleattributethresholdmap.put("criticalactions", criticalactionlist);
/*     */           }
/* 170 */           String warningactions = request.getParameter("warningaction_" + attributeid);
/* 171 */           if ((warningactions != null) && (warningactions.length() > 0)) {
/* 172 */             String[] warningactionsArr = warningactions.split(",");
/* 173 */             List<String> warningactionlist = new ArrayList();
/* 174 */             for (String warningaction : warningactionsArr) {
/* 175 */               warningactionlist.add(warningaction);
/*     */             }
/* 177 */             singleattributethresholdmap.put("warningactions", warningactionlist);
/*     */           }
/* 179 */           String clearactions = request.getParameter("clearaction_" + attributeid);
/* 180 */           if ((clearactions != null) && (clearactions.length() > 0)) {
/* 181 */             String[] clearactionsArr = clearactions.split(",");
/* 182 */             List<String> clearactionlist = new ArrayList();
/* 183 */             for (String clearaction : clearactionsArr) {
/* 184 */               clearactionlist.add(clearaction);
/*     */             }
/* 186 */             singleattributethresholdmap.put("clearactions", clearactionlist);
/*     */           }
/* 188 */           templateattributethresholdmap.put(attributeid, singleattributethresholdmap);
/* 189 */           logger.log(Level.INFO, "TEMPLATE Attribute " + attributeid + " THRESH " + thresholdid + " CRITICAL " + criticalactions + " WARN " + warningactions + " CLEAR " + clearactions);
/*     */         }
/*     */       }
/*     */       
/* 193 */       if (templateattributethresholdmap.size() > 0) {
/* 194 */         templateconfig.setAttributeThresholdMap(templateattributethresholdmap);
/*     */       }
/*     */       
/* 197 */       String monitorchooser = request.getParameter("monitorchooser");
/*     */       
/* 199 */       logger.log(Level.INFO, "MONITORCATEGORY " + monitorchooser);
/* 200 */       if (monitorchooser.equalsIgnoreCase("monitorlist"))
/*     */       {
/* 202 */         String[] servertypeselectedId = request.getParameterValues("servertypelist");
/* 203 */         String[] serverlistarr = request.getParameterValues(servertypeselectedId[0] + "_selected");
/* 204 */         String servertypeselected = request.getParameter("selectedservertype");
/* 205 */         if (serverlistarr != null) {
/* 206 */           List<String> serverlist = java.util.Arrays.asList(serverlistarr);
/* 207 */           templateconfig.setServerList(serverlist);
/* 208 */           logger.log(Level.INFO, "SERVERLIST SELECTED  " + servertypeselected + " " + serverlist);
/*     */         }
/*     */         
/* 211 */         templateconfig.setMonitorChooser(1);
/* 212 */         templateconfig.setSelectedServerType(servertypeselected);
/* 213 */       } else if (monitorchooser.equalsIgnoreCase("monitortype"))
/*     */       {
/* 215 */         String[] multiservertype = request.getParameterValues("servertypecheckbox");
/* 216 */         if (multiservertype != null) {
/* 217 */           templateconfig.setServerList(java.util.Arrays.asList(multiservertype));
/* 218 */           logger.log(Level.INFO, "SERVERTYPE SELECTED " + templateconfig.getServerList());
/*     */         }
/* 220 */         templateconfig.setMonitorChooser(0);
/* 221 */       } else if (monitorchooser.equalsIgnoreCase("monitorgroup"))
/*     */       {
/* 223 */         templateconfig.setMonitorChooser(2);
/* 224 */         String[] mgselected = request.getParameterValues("select");
/* 225 */         templateconfig.setServerList(java.util.Arrays.asList(mgselected));
/* 226 */         logger.log(Level.INFO, "MG SELECTED " + templateconfig.getServerList());
/*     */       }
/*     */       
/* 229 */       String isEdit = request.getParameter("edit");
/* 230 */       logger.log(Level.INFO, "IS EDIT " + isEdit);
/* 231 */       templatemanager.setTemplateConfiguration(templateconfig);
/* 232 */       if ((isEdit != null) && (isEdit.equalsIgnoreCase("true"))) {
/* 233 */         templateconfig.setTemplateId(Integer.parseInt(request.getParameter("templateid")));
/* 234 */         templatemanager.editTemplate();
/*     */       } else {
/* 236 */         templatemanager.associateTemplate();
/*     */       }
/*     */     } catch (Exception exc) {
/* 239 */       exc.printStackTrace();
/*     */     }
/* 241 */     return showAllTemplates(mapping, form, request, response);
/*     */   }
/*     */   
/*     */   public ActionForward createProcessTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 246 */     Logger logger = AMProcessTemplateManager.getLogger();
/* 247 */     String templateType = request.getParameter("templatetype") != null ? request.getParameter("templatetype") : "0";
/* 248 */     String isEdit = request.getParameter("edit");
/*     */     try
/*     */     {
/* 251 */       AMProcessTemplateManager templatemanager = new AMProcessTemplateManager();
/* 252 */       request.setAttribute("templatetype", templateType);
/* 253 */       request.setAttribute("templatetypestr", getTemplateTypeStr(templateType));
/* 254 */       if ((isEdit != null) && (isEdit.equalsIgnoreCase("true"))) {
/* 255 */         String templateid = request.getParameter("templateid");
/*     */         
/* 257 */         Integer.parseInt(templateid);
/* 258 */         AMTemplateConfiguration templateconfig = templatemanager.fetchTemplate(templateid, request);
/* 259 */         AMProcessTemplateForm templateform = (AMProcessTemplateForm)form;
/* 260 */         templateform.setTemplateName(templateconfig.getTemplateName());
/* 261 */         templateform.setTemplateDescription(templateconfig.getTemplateDescription());
/* 262 */         request.setAttribute("cacheprocessid", templateconfig.getPSDefinition());
/* 263 */         request.setAttribute("attributedetails", templateconfig.getAttributeThresholdMap());
/* 264 */         request.setAttribute("servertypelist", templateconfig.getServerTypeMapping());
/* 265 */         request.setAttribute("serverdetaillist", templateconfig.getServerList());
/* 266 */         request.setAttribute("matchinterval", String.valueOf(templateconfig.getMatchintevel()));
/* 267 */         if (templateconfig.getMonitorChooser() == 1) {
/* 268 */           request.setAttribute("selectedservertype", templateconfig.getSelectedServerType());
/*     */         }
/* 270 */         request.setAttribute("monitorchoosertype", Integer.valueOf(templateconfig.getMonitorChooser()));
/* 271 */         if (EnterpriseUtil.isAdminServer()) {
/* 272 */           request.setAttribute("groupdetaillist", MonitorGroupUtil.getMGDetails(request, true));
/*     */         } else {
/* 274 */           request.setAttribute("groupdetaillist", MonitorGroupUtil.getMGDetails(request));
/*     */         }
/* 276 */         request.setAttribute("selectedMonitor", templateconfig.getSelectedGroups());
/*     */       }
/*     */       else
/*     */       {
/* 280 */         AMTemplateConfiguration templateconfig = com.adventnet.appmanager.server.template.AMTemplateFactory.getAMTemplateConfiguration(Integer.parseInt(templateType));
/* 281 */         Map allattributedetails = templateconfig.getAttributeDetails();
/* 282 */         request.setAttribute("attributedetails", allattributedetails);
/* 283 */         Map servertypelist = templateconfig.getAllServerTypeList();
/* 284 */         request.setAttribute("servertypelist", servertypelist);
/* 285 */         List allserverlist = templateconfig.getAllServerList(null, request);
/* 286 */         logger.log(Level.INFO, "ALL SERVER LIST " + allserverlist);
/* 287 */         request.setAttribute("serverdetaillist", allserverlist);
/* 288 */         if (EnterpriseUtil.isAdminServer()) {
/* 289 */           Map groupdetail = MonitorGroupUtil.getMGDetails(request, true);
/* 290 */           request.setAttribute("groupdetaillist", groupdetail);
/*     */         } else {
/* 292 */           Map groupdetail = MonitorGroupUtil.getMGDetails(request);
/* 293 */           request.setAttribute("groupdetaillist", groupdetail);
/*     */         }
/* 295 */         request.setAttribute("matchinterval", "0");
/* 296 */         if (DBUtil.isDelegatedAdmin(request.getRemoteUser()))
/*     */         {
/* 298 */           request.setAttribute("monitorchoosertype", Integer.valueOf(2));
/*     */         }
/*     */       }
/* 301 */       request.setAttribute("servertypei18nkey", com.adventnet.appmanager.util.Constants.getServerTypei18nKeys());
/*     */     } catch (Exception exc) {
/* 303 */       exc.printStackTrace();
/*     */     }
/* 305 */     return mapping.findForward("createtemplates");
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward deleteTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 311 */     Logger logger = AMProcessTemplateManager.getLogger();
/*     */     try {
/* 313 */       String templateType = request.getParameter("templatetype") != null ? request.getParameter("templatetype") : "0";
/* 314 */       AMProcessTemplateManager templatemanager = new AMProcessTemplateManager();
/* 315 */       request.setAttribute("templatetype", templateType);
/* 316 */       request.setAttribute("templatetypestr", getTemplateTypeStr(templateType));
/* 317 */       String[] deleteids = request.getParameterValues("checkbox");
/* 318 */       templatemanager.deleteTemplate(deleteids);
/*     */     }
/*     */     catch (Exception exc) {
/* 321 */       exc.printStackTrace();
/*     */     }
/* 323 */     return showAllTemplates(mapping, form, request, response);
/*     */   }
/*     */   
/*     */   public ActionForward getThresholdActionList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 328 */     Logger logger = AMProcessTemplateManager.getLogger();
/* 329 */     String templateid = request.getParameter("templateid");
/* 330 */     String attributeid = request.getParameter("attributeid");
/* 331 */     Map thresholdlist = new HashMap();
/* 332 */     String userId = DBUtil.getUserID(request.getRemoteUser());
/* 333 */     Vector<String> resIds = DelegatedUserRoleUtil.getMonitorsForUser(userId);
/* 334 */     if (DBUtil.isDelegatedAdmin(request.getRemoteUser())) {
/* 335 */       Vector<String> thresholdIdList = new Vector();
/* 336 */       if (DBUtil.getGlobalConfigValueasBoolean("allowDAdminViewAllThresholds")) {
/* 337 */         thresholdIdList = DelegatedUserRoleUtil.getConfigIDsWithViewPerm(DelegatedUserRoleUtil.getLoginUserid(request), 1);
/*     */       }
/*     */       else {
/* 340 */         thresholdIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(DelegatedUserRoleUtil.getLoginUserid(request), 1);
/*     */       }
/* 342 */       String query = "SELECT THRESHOLDCONFIGURATIONID FROM AM_ATTRIBUTETHRESHOLDMAPPER WHERE " + DBUtil.getCondition("AM_ATTRIBUTETHRESHOLDMAPPER.ID", resIds) + " and AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID not in ";
/* 343 */       thresholdIdList = DelegatedUserRoleUtil.getCompleteConfigIds(query, thresholdIdList);
/* 344 */       ResultSet thresholdset = null;
/*     */       try {
/* 346 */         String resultquery = "select ID,NAME FROM AM_THRESHOLDCONFIG WHERE " + DBUtil.getCondition("ID", thresholdIdList);
/* 347 */         thresholdset = AMConnectionPool.executeQueryStmt(resultquery);
/* 348 */         while (thresholdset.next()) {
/* 349 */           int thresholdid = thresholdset.getInt(1);
/* 350 */           String thresholdname = thresholdset.getString(2);
/* 351 */           thresholdlist.put(Integer.valueOf(thresholdid), thresholdname);
/*     */         }
/*     */       } catch (Exception exc) {
/* 354 */         exc.printStackTrace();
/*     */       } finally {
/* 356 */         AMConnectionPool.closeStatement(thresholdset);
/*     */       }
/*     */     }
/*     */     else {
/* 360 */       thresholdlist = new DBUtil().getNumericThreshold();
/*     */     }
/*     */     
/* 363 */     request.setAttribute("thresholddetail", thresholdlist);
/* 364 */     String[] attributeinfo = DBUtil.getAttributeDetails(attributeid);
/* 365 */     request.setAttribute("attributetype", attributeinfo[0]);
/* 366 */     String templatetype = request.getParameter("templatetype");
/* 367 */     String criticalactiontext = "";
/* 368 */     String warningactiontext = "";
/* 369 */     String clearactiontext = "";
/* 370 */     String attributetype = attributeinfo[0];
/* 371 */     if ((attributetype != null) && (attributetype.equals("1")))
/*     */     {
/* 373 */       criticalactiontext = FormatUtil.getString("am.webclient.configurealert.availabilitycritical");
/* 374 */       clearactiontext = FormatUtil.getString("am.webclient.configurealert.availabilityclear");
/* 375 */     } else if ((attributetype != null) && (attributetype.equals("2")))
/*     */     {
/* 377 */       criticalactiontext = FormatUtil.getString("am.webclient.configurealert.healthcritical");
/* 378 */       warningactiontext = FormatUtil.getString("am.webclient.configurealert.healthwarning");
/* 379 */       clearactiontext = FormatUtil.getString("am.webclient.configurealert.healthclear");
/*     */     } else {
/* 381 */       criticalactiontext = FormatUtil.getString("am.webclient.configurealert.thresholdcritical");
/* 382 */       warningactiontext = FormatUtil.getString("am.webclient.configurealert.thresholdwarning");
/* 383 */       clearactiontext = FormatUtil.getString("am.webclient.configurealert.thresholdclear");
/*     */     }
/* 385 */     request.setAttribute("criticalactiontext", criticalactiontext);
/* 386 */     request.setAttribute("warningactiontext", warningactiontext);
/* 387 */     request.setAttribute("clearactiontext", clearactiontext);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 393 */     String criticalselected = request.getParameter("critical");
/* 394 */     String warningselected = request.getParameter("warning");
/* 395 */     String clearselected = request.getParameter("clear");
/* 396 */     boolean isActionSelected = (criticalselected != null) || (warningselected != null) || (clearselected != null);
/* 397 */     String allactionsqry = "select * from AM_ACTIONPROFILE ";
/* 398 */     if ((templatetype != null) && ((templatetype.equals("0")) || (templatetype.equals("1")))) {
/* 399 */       allactionsqry = allactionsqry + " where NAME NOT IN ('Marker','Restart The Service','SDPCloseAction')";
/*     */     } else {
/* 401 */       allactionsqry = allactionsqry + " where NAME NOT IN ('Marker')";
/*     */     }
/* 403 */     if (DBUtil.isDelegatedAdmin(request.getRemoteUser())) {
/* 404 */       Vector<String> actionIdList = new Vector();
/* 405 */       if (DBUtil.getGlobalConfigValueasBoolean("allowDAdminViewAllActions")) {
/* 406 */         actionIdList = DelegatedUserRoleUtil.getConfigIDsWithViewPerm(DelegatedUserRoleUtil.getLoginUserid(request), 2);
/*     */       }
/*     */       else {
/* 409 */         actionIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(DelegatedUserRoleUtil.getLoginUserid(request), 2);
/*     */       }
/* 411 */       String qry = "select ACTIONID from AM_ATTRIBUTEACTIONMAPPER where " + DBUtil.getCondition("AM_ATTRIBUTEACTIONMAPPER.ID", resIds) + " and AM_ATTRIBUTEACTIONMAPPER.ACTIONID not in ";
/* 412 */       actionIdList = DelegatedUserRoleUtil.getCompleteConfigIds(qry, actionIdList);
/* 413 */       allactionsqry = allactionsqry + " and " + DBUtil.getCondition("ID", actionIdList);
/*     */     }
/*     */     
/* 416 */     ResultSet actionset = null;
/* 417 */     Map actiondetail = new HashMap();
/*     */     try {
/* 419 */       actionset = AMConnectionPool.executeQueryStmt(allactionsqry);
/* 420 */       List criticalselectedlist = new ArrayList();
/* 421 */       List warningselectedlist = new ArrayList();
/* 422 */       List clearselectedlist = new ArrayList();
/* 423 */       if (criticalselected != null) {
/* 424 */         criticalselectedlist = convertCommaStringtoList(criticalselected);
/*     */       }
/* 426 */       if (warningselected != null) {
/* 427 */         warningselectedlist = convertCommaStringtoList(warningselected);
/*     */       }
/* 429 */       if (clearselected != null) {
/* 430 */         clearselectedlist = convertCommaStringtoList(clearselected);
/*     */       }
/*     */       
/* 433 */       HashMap criticalselectedmap = new HashMap();
/* 434 */       HashMap criticalavailablemap = new HashMap();
/* 435 */       HashMap warningselectedmap = new HashMap();
/* 436 */       HashMap warningavailablemap = new HashMap();
/* 437 */       HashMap clearselectedmap = new HashMap();
/* 438 */       HashMap clearavailablemap = new HashMap();
/*     */       
/* 440 */       while (actionset.next())
/*     */       {
/*     */ 
/* 443 */         String actionid = actionset.getString(1);
/* 444 */         String actionname = actionset.getString(2);
/* 445 */         if (isActionSelected) {
/* 446 */           if (criticalselectedlist.contains(actionid)) {
/* 447 */             criticalselectedmap.put(actionid, actionname);
/*     */           } else {
/* 449 */             criticalavailablemap.put(actionid, actionname);
/*     */           }
/* 451 */           if (warningselectedlist.contains(actionid)) {
/* 452 */             warningselectedmap.put(actionid, actionname);
/*     */           } else {
/* 454 */             warningavailablemap.put(actionid, actionname);
/*     */           }
/* 456 */           if (clearselectedlist.contains(actionid)) {
/* 457 */             clearselectedmap.put(actionid, actionname);
/*     */           } else {
/* 459 */             clearavailablemap.put(actionid, actionname);
/*     */           }
/*     */         } else {
/* 462 */           actiondetail.put(actionid, actionname);
/*     */         }
/*     */       }
/*     */       
/* 466 */       if (isActionSelected) {
/* 467 */         request.setAttribute("criticalavailableaction", criticalavailablemap);
/* 468 */         request.setAttribute("warningavailableaction", warningavailablemap);
/* 469 */         request.setAttribute("clearavailableaction", clearavailablemap);
/* 470 */         request.setAttribute("criticalselectedaction", criticalselectedmap);
/* 471 */         request.setAttribute("warningselectedaction", warningselectedmap);
/* 472 */         request.setAttribute("clearselectedaction", clearselectedmap);
/* 473 */         logger.log(Level.INFO, "CLEARSELMAP " + clearselectedmap);
/* 474 */         logger.log(Level.INFO, "CRITISELMAP " + criticalselectedmap);
/*     */       } else {
/* 476 */         request.setAttribute("criticalavailableaction", actiondetail);
/* 477 */         request.setAttribute("warningavailableaction", actiondetail);
/* 478 */         request.setAttribute("clearavailableaction", actiondetail);
/*     */       }
/*     */     } catch (Exception exc) {
/* 481 */       exc.printStackTrace();
/*     */     } finally {
/* 483 */       AMConnectionPool.closeStatement(actionset);
/*     */     }
/* 485 */     return mapping.findForward("templatethresholdactionconfig");
/*     */   }
/*     */   
/*     */   public ActionForward editProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 490 */     String templatetype = request.getParameter("templatetype");
/* 491 */     String templatetypestr = getTemplateTypeStr(templatetype);
/* 492 */     String processid = request.getParameter("processid");
/* 493 */     String processname = request.getParameter("processname");
/* 494 */     String processarg = request.getParameter("processarg");
/* 495 */     String pnameregex = request.getParameter("ispnameregex");
/* 496 */     String pcmdregex = request.getParameter("ispcmdregex");
/* 497 */     String processdispname = request.getParameter("processdispname");
/* 498 */     Logger logger = AMProcessTemplateManager.getLogger();
/*     */     try {
/* 500 */       logger.log(Level.INFO, "Edit process initiated for id " + processid + " processdispname " + processdispname + "  processname " + processname + " arg " + processarg);
/* 501 */       request.setAttribute("processid", processid);
/* 502 */       if ((processname != null) && (processname.trim().length() > 0)) {
/* 503 */         if (processname != null) {
/* 504 */           processname = StrUtil.findReplace(processname, "\\", "\\\\");
/*     */         }
/* 506 */         if (processarg != null) {
/* 507 */           processarg = StrUtil.findReplace(processarg, "\\", "\\\\");
/* 508 */           processarg = org.htmlparser.util.Translate.decode(processarg);
/*     */         }
/* 510 */         request.setAttribute("processdispname", processdispname);
/* 511 */         request.setAttribute("processname", processname);
/* 512 */         request.setAttribute("processarg", processarg);
/* 513 */         request.setAttribute("ispnameregex", pnameregex);
/* 514 */         request.setAttribute("ispcmdregex", pcmdregex);
/*     */       }
/*     */       else {
/* 517 */         ArrayList<ArrayList> processlist = DBUtil.getRows("select PROCESSNAME,PROCESSCMD,ISPROCESSREGEX,ISCMDREGEX,DISPLAYNAME from AM_TEMPLATE_PROCESS_DEFINITION where TEMPLATEPROCESSID=" + processid);
/* 518 */         if (processlist.size() > 0) {
/* 519 */           ArrayList<String> singleprocessinfo = (ArrayList)processlist.get(0);
/* 520 */           processname = (String)singleprocessinfo.get(0);
/* 521 */           processarg = (String)singleprocessinfo.get(1);
/* 522 */           pnameregex = (String)singleprocessinfo.get(2);
/* 523 */           pcmdregex = (String)singleprocessinfo.get(3);
/* 524 */           processdispname = (String)singleprocessinfo.get(4);
/*     */           
/* 526 */           if (processname != null) {
/* 527 */             processname = StrUtil.findReplace(processname, "\\", "\\\\");
/*     */           }
/*     */           
/* 530 */           if (processarg != null) {
/* 531 */             processarg = StrUtil.findReplace(processarg, "\\", "\\\\");
/*     */           }
/* 533 */           request.setAttribute("processname", processname);
/* 534 */           request.setAttribute("processarg", processarg);
/* 535 */           request.setAttribute("ispnameregex", pnameregex);
/* 536 */           request.setAttribute("ispcmdregex", pcmdregex);
/* 537 */           request.setAttribute("processdispname", processdispname);
/*     */         } else {
/* 539 */           logger.log(Level.INFO, "Unable to find DB entry " + templatetypestr + " template  for " + templatetypestr + " id " + processid);
/*     */         }
/*     */       }
/*     */     } catch (Exception exc) {
/* 543 */       exc.printStackTrace();
/* 544 */       logger.log(Level.INFO, "Unable to edit the " + templatetypestr + " template  for " + templatetypestr + " id " + processid);
/*     */     }
/* 546 */     return mapping.findForward("editprocess");
/*     */   }
/*     */   
/*     */   public ActionForward createThreshold(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*     */     try {
/* 551 */       org.apache.struts.action.ActionMessages messages = new org.apache.struts.action.ActionMessages();
/* 552 */       Logger logger = AMProcessTemplateManager.getLogger();
/* 553 */       String displayname = request.getParameter("thresholdname");
/* 554 */       String checkquery = "select * from AM_THRESHOLDCONFIG where NAME='" + displayname + "'";
/* 555 */       ArrayList list = this.mo.getRows(checkquery);
/* 556 */       if (list.size() == 0) {
/* 557 */         AdminActions adminAction = new AdminActions();
/* 558 */         adminAction.insertThreshold(request, false, true);
/* 559 */         com.adventnet.appmanager.dbcache.AMCacheHandler.setThresholdProfileinCache();
/* 560 */         ResultSet rs = null;
/* 561 */         String configID = "";
/* 562 */         String query = "SELECT ID FROM AM_THRESHOLDCONFIG WHERE ID=(SELECT MAX(ID) FROM AM_THRESHOLDCONFIG)";
/*     */         try {
/* 564 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 565 */           while (rs.next()) {
/* 566 */             configID = rs.getString("ID");
/*     */           }
/*     */         } catch (Exception e) {
/* 569 */           e.printStackTrace();
/*     */         } finally {
/* 571 */           AMConnectionPool.closeResultSet(rs);
/*     */         }
/* 573 */         DelegatedUserRoleUtil.addEntryToConfigUserTable(request, Integer.parseInt(configID), 1);
/*     */       } else {
/* 575 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new org.apache.struts.action.ActionMessage("thresholdprofile.create.alreadyexists", request.getParameter("displayname")));
/* 576 */         saveMessages(request, messages);
/*     */       }
/*     */     } catch (Exception exc) {
/* 579 */       exc.printStackTrace();
/*     */     }
/* 581 */     return null;
/*     */   }
/*     */   
/*     */   public static List convertCommaStringtoList(String commaString)
/*     */   {
/* 586 */     Logger logger = AMProcessTemplateManager.getLogger();
/* 587 */     List commatolist = new ArrayList();
/* 588 */     StringTokenizer commatokenizer = new StringTokenizer(commaString, ",");
/* 589 */     while (commatokenizer.hasMoreTokens()) {
/* 590 */       String token = commatokenizer.nextToken();
/* 591 */       logger.log(Level.INFO, "COMMA TOKEN " + token);
/* 592 */       commatolist.add(token);
/*     */     }
/* 594 */     logger.log(Level.INFO, " RETURN LIST " + commatolist);
/* 595 */     return commatolist;
/*     */   }
/*     */   
/*     */   private String getTemplateTypeStr(String templatetype)
/*     */   {
/* 600 */     Logger logger = AMProcessTemplateManager.getLogger();
/*     */     
/* 602 */     if (Integer.parseInt(templatetype) == 0)
/* 603 */       return FormatUtil.getString("am.webclient.templatetype.process");
/* 604 */     if (Integer.parseInt(templatetype) == 1) {
/* 605 */       return FormatUtil.getString("am.webclient.templatetype.service");
/*     */     }
/* 607 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward getAllServerList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*     */     try
/*     */     {
/* 619 */       String templatetype = request.getParameter("templatetype");
/* 620 */       AMTemplateConfiguration templateconfig = com.adventnet.appmanager.server.template.AMTemplateFactory.getAMTemplateConfiguration(Integer.parseInt(templatetype));
/* 621 */       List serverlist = templateconfig.getAllServerList(null, request);
/* 622 */       if ((serverlist != null) && (serverlist.size() > 0)) {
/* 623 */         Map<String, Map> serverdetaillist = (Map)serverlist.get(0);
/* 624 */         if ((serverdetaillist != null) && (serverdetaillist.size() > 0) && (EnterpriseUtil.isAdminServer()))
/*     */         {
/* 626 */           Map<String, Map> allServersSplittedByMas = new HashMap();
/*     */           
/* 628 */           Map<String, String> allMasDispNames = EnterpriseUtil.getAllManagedServerDisplayName();
/*     */           
/*     */ 
/*     */ 
/* 632 */           Iterator itr1 = serverdetaillist.keySet().iterator();
/* 633 */           while (itr1.hasNext()) {
/* 634 */             String resourceType = (String)itr1.next();
/* 635 */             Map serversListForType = (Map)serverdetaillist.get(resourceType);
/* 636 */             Map<String, Map> inner1 = new HashMap();
/* 637 */             allServersSplittedByMas.put(resourceType, inner1);
/*     */             
/*     */ 
/* 640 */             Iterator itr2 = serversListForType.keySet().iterator();
/* 641 */             while (itr2.hasNext()) {
/* 642 */               String resourceId = (String)itr2.next();
/* 643 */               String serverDispName = (String)serversListForType.get(resourceId);
/* 644 */               String allotedRange = EnterpriseUtil.getAllotedRange(resourceId);
/* 645 */               String masDispName = (String)allMasDispNames.get(allotedRange);
/* 646 */               Map<String, String> inner2 = (Map)inner1.get(masDispName);
/* 647 */               if (inner2 == null) {
/* 648 */                 inner2 = new HashMap();
/* 649 */                 inner1.put(masDispName, inner2);
/*     */               }
/* 651 */               inner2.put(resourceId, serverDispName);
/*     */             }
/*     */           }
/* 654 */           serverdetaillist = allServersSplittedByMas;
/*     */         }
/* 656 */         com.adventnet.appmanager.logging.AMLog.debug("All avialble servers for template configuration\t" + serverdetaillist + " and tempalte type  " + templatetype);
/* 657 */         request.setAttribute("isAdminServer", Boolean.valueOf(EnterpriseUtil.isAdminServer()));
/* 658 */         request.setAttribute("serverdetaillist", serverdetaillist);
/* 659 */         request.setAttribute("servertypei18nkey", com.adventnet.appmanager.util.Constants.getServerTypei18nKeys());
/*     */       }
/*     */     } catch (Exception exc) {
/* 662 */       exc.printStackTrace();
/*     */     }
/* 664 */     return new ActionForward("/jsp/AssociateTemplateAndProcess.jsp?addtotemplate=true");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ProcessTemplateAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */