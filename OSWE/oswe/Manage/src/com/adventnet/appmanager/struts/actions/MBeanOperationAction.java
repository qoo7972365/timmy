/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*     */ import com.adventnet.appmanager.cam.CAMJMXUtil;
/*     */ import com.adventnet.appmanager.cam.jmx12.CAMJMX12Util;
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.management.ObjectName;
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
/*     */ import org.htmlparser.util.Translate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanOperationAction
/*     */   extends DispatchAction
/*     */ {
/*  43 */   private AMConnectionPool cp = AMConnectionPool.getInstance();
/*  44 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */ 
/*     */   public ActionForward showInitialScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  50 */     List agentsList = CAMDBUtil.getDataCollectableAgentDetails(false, request);
/*  51 */     request.setAttribute("datacollectableagents", agentsList);
/*  52 */     String popupParam = request.getParameter("popup");
/*  53 */     boolean popup = (popupParam != null) && (popupParam.equals("true"));
/*  54 */     request.setAttribute("HelpKey", "MBean Operation");
/*  55 */     ArrayList rows = CAMDBUtil.getListofMails();
/*  56 */     ((DynaActionForm)form).set("maillist", rows);
/*  57 */     if (popup) {
/*  58 */       return new ActionForward("/jsp/mopaction_initialscreen.jsp");
/*     */     }
/*  60 */     return mapping.findForward("mopAction");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showDomains(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  72 */     ActionMessages messages = new ActionMessages();
/*  73 */     ActionErrors errors = new ActionErrors();
/*     */     
/*  75 */     String hostName = null;
/*  76 */     String portNumber = null;
/*  77 */     String resourceType = null;
/*  78 */     String popupParam = request.getParameter("popup");
/*  79 */     boolean popup = (popupParam != null) && (popupParam.equals("true"));
/*  80 */     String checkquery = "select * from AM_ACTIONPROFILE where NAME='" + request.getParameter("actionname").replaceAll("'", "\\\\'") + "'";
/*  81 */     ResultSet set = AMConnectionPool.executeQueryStmt(checkquery);
/*  82 */     if (set.first())
/*     */     {
/*  84 */       set.close();
/*  85 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("mopaction.create.alreadyexists", request.getParameter("displayname")));
/*  86 */       saveMessages(request, messages);
/*  87 */       return showInitialScreen(mapping, form, request, response);
/*     */     }
/*  89 */     set.close();
/*     */     try
/*     */     {
/*  92 */       String resourceID = request.getParameter("actionresourceid");
/*  93 */       if (resourceID == null)
/*     */       {
/*  95 */         resourceID = (String)request.getAttribute("actionresourceid");
/*     */       }
/*  97 */       Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID));
/*  98 */       hostName = (String)agentInfo.get("TARGETNAME");
/*  99 */       portNumber = (String)agentInfo.get("PORTNO");
/* 100 */       resourceType = (String)agentInfo.get("TYPE");
/* 101 */       request.setAttribute("actionname", request.getParameter("actionname"));
/* 102 */       request.setAttribute("sendmail", request.getParameter("sendmail"));
/* 103 */       request.setAttribute("actionresourceid", resourceID);
/* 104 */       request.setAttribute("hostname", hostName);
/* 105 */       request.setAttribute("resourcetype", resourceType);
/* 106 */       request.setAttribute("portnumber", portNumber);
/*     */       
/*     */ 
/*     */ 
/* 110 */       Collection domains = null;
/* 111 */       if (resourceType.equals("JMX1.2-MX4J-RMI"))
/*     */       {
/* 113 */         domains = CAMJMX12Util.getDomains(agentInfo);
/*     */       }
/*     */       else
/*     */       {
/* 117 */         domains = CAMJMXUtil.getDomains(agentInfo);
/*     */       }
/* 119 */       request.setAttribute("domains", domains);
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 123 */       AMLog.warning("Fault :  Action : ", ee);
/* 124 */       if ("WebSphere-server".equals(resourceType)) {
/* 125 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(ee.getMessage()));
/*     */       }
/* 127 */       else if ((ee.getMessage() != null) && (ee.getMessage().indexOf("Unsupported protocol: remoting-jmx") != -1)) {
/* 128 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("mopaction.mbeanoperations.protocol.erroroccured.text")));
/*     */       } else {
/* 130 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("mopaction.mbeanoperations.erroroccured.text", new String[] { hostName, portNumber })));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 136 */     if (!errors.isEmpty()) {
/* 137 */       saveErrors(request, errors);
/* 138 */       return showInitialScreen(mapping, form, request, response);
/*     */     }
/* 140 */     if (popup)
/*     */     {
/* 142 */       return new ActionForward("/jsp/mopaction_showdomains.jsp");
/*     */     }
/*     */     
/*     */ 
/* 146 */     return mapping.findForward("mopAction2");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showMBeans(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 156 */     ActionErrors errors = new ActionErrors();
/* 157 */     String popupParam = request.getParameter("popup");
/* 158 */     boolean popup = (popupParam != null) && (popupParam.equals("true"));
/*     */     try
/*     */     {
/* 161 */       String hostName = request.getParameter("hostname");
/* 162 */       String portNumber = request.getParameter("portnumber");
/* 163 */       String resourceType = request.getParameter("resourcetype");
/* 164 */       String domain = request.getParameter("domain");
/* 165 */       String domainFilter = request.getParameter("domainfilter");
/* 166 */       String resourceID = request.getParameter("actionresourceid");
/*     */       
/* 168 */       ObjectName obName = null;
/* 169 */       String obNameStr = null;
/*     */       
/*     */ 
/* 172 */       if ((domain != null) && (!domain.equals("adventnet_meam_cam_domainfilter"))) {
/* 173 */         obNameStr = domain + ":*";
/* 174 */         obName = new ObjectName(obNameStr);
/*     */       } else {
/* 176 */         obNameStr = domainFilter;
/* 177 */         obName = new ObjectName(obNameStr);
/*     */       }
/* 179 */       request.setAttribute("actionname", request.getParameter("actionname"));
/* 180 */       request.setAttribute("sendmail", request.getParameter("sendmail"));
/* 181 */       request.setAttribute("actionresourceid", resourceID);
/* 182 */       request.setAttribute("hostname", hostName);
/* 183 */       request.setAttribute("resourcetype", resourceType);
/* 184 */       request.setAttribute("portnumber", portNumber);
/* 185 */       request.setAttribute("domain", domain);
/*     */       
/* 187 */       Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID));
/* 188 */       Map mbeanInfo = null;
/* 189 */       if (resourceType.equals("JMX1.2-MX4J-RMI"))
/*     */       {
/* 191 */         mbeanInfo = CAMJMX12Util.showMBeansOfDomain(agentInfo, obNameStr);
/*     */       }
/*     */       else
/*     */       {
/* 195 */         mbeanInfo = CAMJMXUtil.showMBeansOfDomain(agentInfo, obName);
/*     */       }
/* 197 */       request.setAttribute("mbeaninfo", mbeanInfo);
/* 198 */       if (mbeanInfo.size() == 0)
/*     */       {
/* 200 */         ActionMessages messages = new ActionMessages();
/* 201 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("mopaction.mbeansnotpresent"));
/* 202 */         saveMessages(request, messages);
/* 203 */         if (popup)
/*     */         {
/* 205 */           return new ActionForward("/jsp/mopaction_nombeans.jsp");
/*     */         }
/*     */         
/*     */ 
/* 209 */         return mapping.findForward("nomopAction");
/*     */       }
/*     */     }
/*     */     catch (Throwable ee)
/*     */     {
/* 214 */       ee.printStackTrace();
/* 215 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Error ocurred while listing the MBeans.Please try again."));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 220 */     if (!errors.isEmpty()) {
/* 221 */       saveErrors(request, errors);
/* 222 */       return showDomains(mapping, form, request, response);
/*     */     }
/* 224 */     if (popup)
/*     */     {
/* 226 */       return new ActionForward("/jsp/mopaction_showmbeans.jsp");
/*     */     }
/*     */     
/*     */ 
/* 230 */     return mapping.findForward("mopAction3");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showUserInputOperationValuesGetterScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 241 */     ActionErrors errors = new ActionErrors();
/* 242 */     String popupParam = request.getParameter("popup");
/* 243 */     boolean popup = (popupParam != null) && (popupParam.equals("true"));
/* 244 */     String obName = Translate.decode(request.getParameter("mbeanname"));
/*     */     try
/*     */     {
/* 247 */       String hostName = request.getParameter("hostname");
/* 248 */       String portNumber = request.getParameter("portnumber");
/* 249 */       String resourceType = request.getParameter("resourcetype");
/* 250 */       String domain = request.getParameter("domain");
/* 251 */       String domainFilter = request.getParameter("domainfilter");
/* 252 */       String resourceID = request.getParameter("actionresourceid");
/*     */       
/* 254 */       request.setAttribute("actionname", request.getParameter("actionname"));
/* 255 */       request.setAttribute("sendmail", request.getParameter("sendmail"));
/* 256 */       request.setAttribute("actionresourceid", resourceID);
/* 257 */       request.setAttribute("hostname", hostName);
/* 258 */       request.setAttribute("resourcetype", resourceType);
/* 259 */       request.setAttribute("portnumber", portNumber);
/* 260 */       request.setAttribute("domain", domain);
/* 261 */       request.setAttribute("mbeanname", obName);
/* 262 */       Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID));
/* 263 */       Map mbeanInfo = null;
/* 264 */       if (resourceType.equals("JMX1.2-MX4J-RMI"))
/*     */       {
/* 266 */         mbeanInfo = CAMJMX12Util.showMBeansOperations(agentInfo, obName);
/*     */       }
/*     */       else
/*     */       {
/* 270 */         mbeanInfo = CAMJMXUtil.showMBeansOperations(agentInfo, new ObjectName(obName));
/*     */       }
/*     */       
/* 273 */       request.setAttribute("mbeaninfo", mbeanInfo);
/* 274 */       if (mbeanInfo.size() == 0)
/*     */       {
/* 276 */         ActionMessages messages = new ActionMessages();
/* 277 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("mopaction.mbeanoperations.notpresent", obName));
/* 278 */         saveMessages(request, messages);
/* 279 */         if (popup)
/*     */         {
/* 281 */           return new ActionForward("/jsp/mopaction_nombeans.jsp");
/*     */         }
/*     */         
/*     */ 
/* 285 */         return new ActionForward("/showTile.do?TileName=.NoMBeanOperations");
/*     */       }
/*     */     }
/*     */     catch (Throwable ee)
/*     */     {
/* 290 */       ee.printStackTrace();
/* 291 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Error ocurred while listing the MBeans.Please try again."));
/*     */     }
/* 293 */     if (!errors.isEmpty()) {
/* 294 */       saveErrors(request, errors);
/* 295 */       return showMBeans(mapping, form, request, response);
/*     */     }
/* 297 */     if (popup)
/*     */     {
/* 299 */       return new ActionForward("/jsp/mopaction_opvaluesscreen.jsp");
/*     */     }
/*     */     
/*     */ 
/* 303 */     return mapping.findForward("mopAction4");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward executeMBeanOperationActionWithUserIntervention(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 317 */       String actionID = request.getParameter("actionID");
/* 318 */       String query = "select AM_ACTIONPROFILE.NAME,AM_CAM_DC_GROUPS.RESOURCEID,AM_CAM_DC_GROUPS.GROUPNAME,AM_MBEANOPERATIONACTION.OPERATIONNAME,AM_MBEANOPERATIONACTION.ARGSCOUNT,AM_ManagedObject.TYPE from AM_ACTIONPROFILE,AM_CAM_DC_GROUPS,AM_ManagedObject,AM_MBEANOPERATIONACTION where AM_ACTIONPROFILE.ID =" + actionID + " AND AM_ACTIONPROFILE.ID=AM_MBEANOPERATIONACTION.ID and AM_MBEANOPERATIONACTION.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_CAM_DC_GROUPS.RESOURCEID=AM_ManagedObject.RESOURCEID";
/* 319 */       ResultSet res4 = AMConnectionPool.executeQueryStmt(query);
/* 320 */       if (res4.first())
/*     */       {
/* 322 */         String actionName = res4.getString(1);
/* 323 */         int resourceID = res4.getInt(2);
/* 324 */         Map agentInfo = CAMDBUtil.getAMMOInfo(resourceID);
/* 325 */         request.setAttribute("actionID", actionID);
/* 326 */         request.setAttribute("actionname", actionName);
/* 327 */         request.setAttribute("sendmail", request.getParameter("sendmail"));
/* 328 */         request.setAttribute("actionresourceid", "" + resourceID);
/* 329 */         request.setAttribute("hostname", (String)agentInfo.get("TARGETNAME"));
/* 330 */         request.setAttribute("portnumber", (String)agentInfo.get("PORTNO"));
/* 331 */         String mBeanName = res4.getString(3);
/* 332 */         String operationName = res4.getString(4);
/* 333 */         String resType = res4.getString(6);
/* 334 */         request.setAttribute("operationname", operationName);
/* 335 */         request.setAttribute("mbeanname", mBeanName);
/* 336 */         int argsCount = res4.getInt(5);
/* 337 */         request.setAttribute("argsCount", "" + argsCount);
/* 338 */         request.setAttribute("resourcetype", resType);
/* 339 */         if (argsCount > 0)
/*     */         {
/* 341 */           String operationDetailsQuery = "select ARGINDEX,ARGTYPE,POSITIONINPREDEFINEDVALUES,VALUE from AM_MBEANOPERATIONACTION,AM_MBEANOPERATIONACTION_ARGDETAILS,AM_MBEANOPERATIONACTION_ARGVALUES where AM_MBEANOPERATIONACTION.ID=" + actionID + " and AM_MBEANOPERATIONACTION.ID=AM_MBEANOPERATIONACTION_ARGDETAILS.AM_MBEANOPERATIONACTIONID and AM_MBEANOPERATIONACTION_ARGDETAILS.ARG_ID=AM_MBEANOPERATIONACTION_ARGVALUES.ARG_ID";
/* 342 */           ResultSet res5 = AMConnectionPool.executeQueryStmt(operationDetailsQuery);
/* 343 */           Map forJsp = new HashMap();
/* 344 */           while (res5.next())
/*     */           {
/* 346 */             int argIndex = res5.getInt(1);
/* 347 */             String argType = res5.getString(2);
/* 348 */             Object value = res5.getString(4);
/* 349 */             ArrayList argValues = null;
/* 350 */             if (forJsp.get("" + argIndex) == null)
/*     */             {
/* 352 */               argValues = new ArrayList();
/* 353 */               argValues.add(argType);
/* 354 */               forJsp.put("" + argIndex, argValues);
/*     */             }
/*     */             else
/*     */             {
/* 358 */               argValues = (ArrayList)forJsp.get("" + argIndex);
/*     */             }
/* 360 */             argValues.add(value);
/*     */           }
/* 362 */           res5.close();
/* 363 */           request.setAttribute("argdetails", forJsp);
/*     */         }
/* 365 */         res4.close();
/*     */       }
/*     */     }
/*     */     catch (Throwable ee) {
/* 369 */       ee.printStackTrace();
/*     */     }
/* 371 */     return new ActionForward("/jsp/mopaction_chooseactionvalues.jsp");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showDomainsForJMXNotifications(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 383 */     ActionMessages messages = new ActionMessages();
/* 384 */     ActionErrors errors = new ActionErrors();
/* 385 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 386 */     String hostName = null;
/* 387 */     String portNumber = null;
/* 388 */     String popupParam = request.getParameter("popup");
/* 389 */     boolean popup = (popupParam != null) && (popupParam.equals("true"));
/* 390 */     String resourceID = request.getParameter("actionresourceid");
/*     */     try
/*     */     {
/* 393 */       if (resourceID == null)
/*     */       {
/* 395 */         resourceID = (String)request.getAttribute("actionresourceid");
/*     */       }
/* 397 */       Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID));
/* 398 */       hostName = (String)agentInfo.get("TARGETNAME");
/* 399 */       portNumber = (String)agentInfo.get("PORTNO");
/* 400 */       String resourceType = (String)agentInfo.get("TYPE");
/* 401 */       request.setAttribute("actionresourceid", resourceID);
/* 402 */       request.setAttribute("hostname", hostName);
/* 403 */       request.setAttribute("resourcetype", resourceType);
/* 404 */       request.setAttribute("portnumber", portNumber);
/* 405 */       Collection domains = null;
/* 406 */       if (resourceType.equals("JMX1.2-MX4J-RMI"))
/*     */       {
/* 408 */         domains = CAMJMX12Util.getDomains(agentInfo);
/*     */       }
/* 410 */       request.setAttribute("domains", domains);
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 414 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("mopaction.mbeanoperations.erroroccured.text", new String[] { hostName, portNumber })));
/*     */     }
/*     */     
/* 417 */     if (!errors.isEmpty()) {
/* 418 */       saveErrors(request, errors);
/* 419 */       return new ActionForward("/showresource.do?resourceid=" + resourceID + "&method=showResourceForResourceID");
/*     */     }
/*     */     
/* 422 */     if (popup)
/*     */     {
/* 424 */       return new ActionForward("/jsp/jmxnotification_showdomains.jsp");
/*     */     }
/*     */     
/*     */ 
/* 428 */     return mapping.findForward("jmxNotification2");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward showMBeansForJMXNotifcation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 436 */     ActionErrors errors = new ActionErrors();
/* 437 */     String popupParam = request.getParameter("popup");
/* 438 */     boolean popup = (popupParam != null) && (popupParam.equals("true"));
/*     */     try
/*     */     {
/* 441 */       String hostName = request.getParameter("hostname");
/* 442 */       String portNumber = request.getParameter("portnumber");
/* 443 */       String resourceType = request.getParameter("resourcetype");
/* 444 */       String domain = request.getParameter("domain");
/* 445 */       String domainFilter = request.getParameter("domainfilter");
/* 446 */       String resourceID = request.getParameter("actionresourceid");
/*     */       
/* 448 */       ObjectName obName = null;
/* 449 */       String obNameStr = null;
/*     */       
/*     */ 
/* 452 */       if ((domain != null) && (!domain.equals("adventnet_meam_cam_domainfilter"))) {
/* 453 */         obNameStr = domain + ":*";
/* 454 */         obName = new ObjectName(obNameStr);
/*     */       } else {
/* 456 */         obNameStr = domainFilter;
/* 457 */         obName = new ObjectName(obNameStr);
/*     */       }
/* 459 */       request.setAttribute("actionresourceid", resourceID);
/* 460 */       request.setAttribute("hostname", hostName);
/* 461 */       request.setAttribute("resourcetype", resourceType);
/* 462 */       request.setAttribute("portnumber", portNumber);
/* 463 */       request.setAttribute("domain", domain);
/*     */       
/* 465 */       Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID));
/* 466 */       List mbeanInfo = null;
/* 467 */       if (resourceType.equals("JMX1.2-MX4J-RMI"))
/*     */       {
/* 469 */         mbeanInfo = CAMJMX12Util.showMBeansWithListenerCompatibility(agentInfo, obNameStr);
/*     */       }
/*     */       
/* 472 */       request.setAttribute("mbeaninfo", mbeanInfo);
/* 473 */       if (mbeanInfo.size() == 0)
/*     */       {
/* 475 */         ActionMessages messages = new ActionMessages();
/* 476 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jmxnotification.mbeansnotpresent"));
/* 477 */         saveMessages(request, messages);
/* 478 */         return new ActionForward("/showTile.do?TileName=.NoJMXNotifications");
/*     */       }
/*     */     }
/*     */     catch (Throwable ee) {
/* 482 */       ee.printStackTrace();
/* 483 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Error ocurred while listing the MBeans.Please try again."));
/*     */     }
/*     */     
/* 486 */     if (!errors.isEmpty()) {
/* 487 */       saveErrors(request, errors);
/* 488 */       return showDomainsForJMXNotifications(mapping, form, request, response);
/*     */     }
/* 490 */     if (popup)
/*     */     {
/* 492 */       return new ActionForward("/jsp/jmxnotification.jsp");
/*     */     }
/*     */     
/*     */ 
/* 496 */     return mapping.findForward("jmxNotification3");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showJMXNotificationProfileCreateScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 507 */     ActionErrors errors = new ActionErrors();
/* 508 */     String popupParam = request.getParameter("popup");
/* 509 */     boolean popup = (popupParam != null) && (popupParam.equals("true"));
/* 510 */     String obName = request.getParameter("mbeanname");
/*     */     try
/*     */     {
/* 513 */       String hostName = request.getParameter("hostname");
/* 514 */       String portNumber = request.getParameter("portnumber");
/* 515 */       String resourceType = request.getParameter("resourcetype");
/* 516 */       String domain = request.getParameter("domain");
/* 517 */       String domainFilter = request.getParameter("domainfilter");
/* 518 */       String resourceID = request.getParameter("actionresourceid");
/* 519 */       request.setAttribute("actionresourceid", resourceID);
/* 520 */       request.setAttribute("hostname", hostName);
/* 521 */       request.setAttribute("resourcetype", resourceType);
/* 522 */       request.setAttribute("portnumber", portNumber);
/* 523 */       request.setAttribute("domain", domain);
/* 524 */       request.setAttribute("mbeanname", obName);
/* 525 */       String all = "select AM_ACTIONPROFILE.ID ,AM_ACTIONPROFILE.NAME from AM_ACTIONPROFILE where AM_ACTIONPROFILE.NAME not in ('Marker','Restart The Service')";
/* 526 */       ResultSet rs = AMConnectionPool.executeQueryStmt(all);
/* 527 */       ArrayList rows = new ArrayList();
/* 528 */       while (rs.next())
/*     */       {
/* 530 */         Properties p = new Properties();
/* 531 */         p.setProperty("label", rs.getString("NAME"));
/* 532 */         p.setProperty("value", rs.getString("ID"));
/* 533 */         rows.add(p);
/*     */       }
/* 535 */       rs.close();
/* 536 */       AMActionForm form1 = new AMActionForm();
/* 537 */       form1.setToAdd(rows);
/* 538 */       request.setAttribute("AMActionForm", form1);
/*     */     }
/*     */     catch (Throwable ee) {
/* 541 */       ee.printStackTrace();
/* 542 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Error ocurred while listing the MBeans.Please try again."));
/*     */     }
/* 544 */     if (!errors.isEmpty()) {
/* 545 */       saveErrors(request, errors);
/* 546 */       return showMBeansForJMXNotifcation(mapping, form, request, response);
/*     */     }
/*     */     
/* 549 */     if (popup)
/*     */     {
/* 551 */       return new ActionForward("/jsp/createjmxnotification.jsp");
/*     */     }
/*     */     
/*     */ 
/* 555 */     return mapping.findForward("jmxNotification4");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\MBeanOperationAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */