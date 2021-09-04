/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.adaptors.clients.Client;
/*     */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*     */ import com.adventnet.appmanager.cam.CAMJMXUtil;
/*     */ import com.adventnet.appmanager.cam.jmx12.CAMJMX12Util;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.File;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class CAMJMXAction
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
/*     */   public ActionForward chooseDomains(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  74 */     ActionMessages messages = new ActionMessages();
/*  75 */     ActionErrors errors = new ActionErrors();
/*  76 */     String hostName = null;
/*  77 */     String portNumber = null;
/*  78 */     String resourceType = null;
/*     */     try
/*     */     {
/*  81 */       String camID = request.getParameter("camid");
/*  82 */       String resourceID = request.getParameter("resourceid");
/*  83 */       if (resourceID == null) {
/*  84 */         resourceID = (String)request.getAttribute("resourceid");
/*     */       }
/*     */       
/*  87 */       String isFromResourcePage = request.getParameter("isfromresourcepage");
/*     */       
/*     */ 
/*  90 */       request.setAttribute("camid", camID);
/*  91 */       List list = CAMDBUtil.getCAMDetails(camID);
/*  92 */       request.setAttribute("camname", list.get(0));
/*  93 */       request.setAttribute("camdesc", list.get(2));
/*     */       
/*  95 */       request.setAttribute("haid", request.getParameter("haid"));
/*  96 */       request.setAttribute("screenid", request.getParameter("screenid"));
/*     */       
/*     */ 
/*  99 */       Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID));
/*     */       
/*     */ 
/* 102 */       hostName = (String)agentInfo.get("TARGETNAME");
/* 103 */       portNumber = (String)agentInfo.get("PORTNO");
/* 104 */       resourceType = (String)agentInfo.get("TYPE");
/*     */       
/* 106 */       request.setAttribute("hostname", hostName);
/* 107 */       request.setAttribute("portnumber", portNumber);
/* 108 */       request.setAttribute("resourcetype", resourceType);
/* 109 */       request.setAttribute("resourceid", resourceID);
/* 110 */       request.setAttribute("isfromresourcepage", isFromResourcePage);
/* 111 */       int port = Integer.parseInt(portNumber);
/*     */       
/* 113 */       if ((resourceType.equals("SNMP")) || (resourceType.equals("SNMPV1")))
/*     */       {
/*     */ 
/*     */ 
/* 117 */         return mapping.findForward("snmp");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 122 */       Collection domains = null;
/* 123 */       if (resourceType.equals("JMX1.2-MX4J-RMI"))
/*     */       {
/*     */ 
/* 126 */         domains = CAMJMX12Util.getDomains(agentInfo);
/* 127 */         if ((domains.size() == 1) && (domains.contains("*")))
/*     */         {
/* 129 */           request.setAttribute("domains", domains);
/* 130 */           return new ActionForward("/CAMShowMBeansOfDomain.do?method=showMBeansOfDomain&resourceid=" + resourceID + "&hostname=" + hostName + "&portnumber=" + portNumber + "&resourcetype=" + resourceType + "&domain=*&domainfilter=:*&camid=" + request.getParameter("camid") + "&screenid=" + request.getParameter("screenid") + "&haid=" + request.getParameter("haid"));
/*     */         }
/*     */       }
/* 133 */       else if (!resourceType.equals("WebSphere-server"))
/*     */       {
/* 135 */         domains = CAMJMXUtil.getDomains(agentInfo);
/*     */       }
/*     */       else
/*     */       {
/* 139 */         Client client = CAMJMXUtil.getClient(agentInfo);
/* 140 */         if (client != null)
/*     */         {
/* 142 */           client.disconnect();
/*     */         }
/* 144 */         domains = new HashSet();
/*     */       }
/*     */       
/* 147 */       request.setAttribute("domains", domains);
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*     */ 
/* 153 */       AMLog.warning("CAM Action : ", ee);
/* 154 */       if ("WebSphere-server".equals(resourceType)) {
/* 155 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(ee.getMessage()));
/*     */       }
/* 157 */       else if ((ee.getMessage() != null) && (ee.getMessage().indexOf("Unsupported protocol: remoting-jmx") != -1)) {
/* 158 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("mopaction.mbeanoperations.protocol.erroroccured.text")));
/*     */       } else {
/* 160 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("mopaction.mbeanoperations.erroroccuredathost.text"), new String[] { hostName, portNumber }));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 166 */     if (!errors.isEmpty()) {
/* 167 */       saveErrors(request, errors);
/* 168 */       return mapping.getInputForward();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 173 */     return mapping.findForward("showdomains");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showMBeansOfDomain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 184 */     ActionErrors errors = new ActionErrors();
/*     */     try {
/* 186 */       String camID = request.getParameter("camid");
/* 187 */       request.setAttribute("camid", camID);
/* 188 */       List list = CAMDBUtil.getCAMDetails(camID);
/* 189 */       request.setAttribute("camname", list.get(0));
/* 190 */       request.setAttribute("camdesc", list.get(2));
/*     */       
/* 192 */       request.setAttribute("haid", request.getParameter("haid"));
/* 193 */       request.setAttribute("screenid", request.getParameter("screenid"));
/*     */       
/* 195 */       String hostName = request.getParameter("hostname");
/* 196 */       String portNumber = request.getParameter("portnumber");
/* 197 */       String resourceType = request.getParameter("resourcetype");
/* 198 */       String domain = request.getParameter("domain");
/* 199 */       String domainFilter = request.getParameter("domainfilter");
/* 200 */       String resourceID = request.getParameter("resourceid");
/* 201 */       String isFromResourcePage = request.getParameter("isfromresourcepage");
/* 202 */       ObjectName obName = null;
/* 203 */       String obNameStr = null;
/*     */       
/*     */ 
/* 206 */       if ((domain != null) && (!domain.equals("adventnet_meam_cam_domainfilter"))) {
/* 207 */         obNameStr = domain + ":*";
/* 208 */         obName = new ObjectName(obNameStr);
/*     */       } else {
/* 210 */         obNameStr = domainFilter;
/* 211 */         obName = new ObjectName(obNameStr);
/*     */       }
/*     */       
/* 214 */       request.setAttribute("hostname", hostName);
/* 215 */       request.setAttribute("portnumber", portNumber);
/* 216 */       request.setAttribute("resourcetype", resourceType);
/* 217 */       request.setAttribute("resourceid", resourceID);
/* 218 */       request.setAttribute("isfromresourcepage", isFromResourcePage);
/*     */       
/* 220 */       int port = Integer.parseInt(portNumber);
/*     */       
/*     */ 
/* 223 */       Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID));
/* 224 */       Map mbeanInfo = null;
/* 225 */       if (resourceType.equals("JMX1.2-MX4J-RMI"))
/*     */       {
/* 227 */         mbeanInfo = CAMJMX12Util.showMBeansOfDomain(agentInfo, obNameStr);
/*     */       }
/*     */       else {
/* 230 */         mbeanInfo = CAMJMXUtil.showMBeansOfDomain(agentInfo, obName);
/*     */       }
/*     */       
/* 233 */       request.setAttribute("mbeaninfo", mbeanInfo);
/* 234 */       Map globalInfoMap = (Map)request.getAttribute("mbeaninfo");
/* 235 */       Map mbeanInfoMap = (Map)globalInfoMap.get("mbeaninfo");
/*     */       
/* 237 */       if ((mbeanInfoMap == null) || (mbeanInfoMap.size() == 0))
/*     */       {
/* 239 */         ActionMessages messages = new ActionMessages();
/* 240 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.mbeansnotpresent"));
/* 241 */         saveMessages(request, messages);
/* 242 */         return new ActionForward("/CAMChooseDomains.do?method=chooseDomains");
/*     */       }
/*     */       
/* 245 */       String query = "select DISPLAYNAME, RESOURCEID from AM_ManagedObject where TYPE='JMX1.2-MX4J-RMI' and RESOURCEID!=" + resourceID;
/* 246 */       ResultSet rs = null;
/* 247 */       Map similarMons = new HashMap();
/*     */       try
/*     */       {
/* 250 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 251 */         while (rs.next()) {
/* 252 */           String dispName = rs.getString("DISPLAYNAME");
/* 253 */           String resrcID = rs.getString("RESOURCEID");
/* 254 */           similarMons.put(resrcID, dispName);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 259 */         ex.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 263 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/* 265 */       request.setAttribute("similarMons", similarMons);
/*     */     }
/*     */     catch (Throwable ee)
/*     */     {
/* 269 */       ee.printStackTrace();
/* 270 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("mopaction.mbeanoperations.listmbeans.error"));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 276 */     if (!errors.isEmpty()) {
/* 277 */       saveErrors(request, errors);
/* 278 */       return mapping.findForward("configurescreen");
/*     */     }
/*     */     
/* 281 */     return mapping.findForward("showmbeansandattributes");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward updateScreenAttributes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 291 */     request.setAttribute("camid", request.getParameter("camid"));
/* 292 */     request.setAttribute("haid", request.getParameter("haid"));
/* 293 */     request.setAttribute("screenid", request.getParameter("screenid"));
/* 294 */     String attributeid = request.getParameter("attributeid");
/* 295 */     String[] mbeanAttribsArr = request.getParameterValues("attributes");
/* 296 */     String displayname = "default";
/*     */     try {
/* 298 */       displayname = (String)((DynaActionForm)form).get("displayname");
/*     */     } catch (Exception e) {}
/* 300 */     String camID = (String)((DynaActionForm)form).get("camid");
/* 301 */     String screenID = (String)((DynaActionForm)form).get("screenid");
/* 302 */     String haid = (String)((DynaActionForm)form).get("haid");
/* 303 */     int type = 0;
/*     */     try {
/* 305 */       type = ((Integer)((DynaActionForm)form).get("type")).intValue();
/*     */     } catch (Exception ee) {}
/* 307 */     String hostName = request.getParameter("hostname");
/* 308 */     String portNumber = request.getParameter("portnumber");
/* 309 */     if (portNumber.equals("null")) {
/* 310 */       portNumber = "0";
/*     */     }
/* 312 */     String resourceType = request.getParameter("resourcetype");
/* 313 */     String resourceID = request.getParameter("resourceid");
/*     */     
/*     */ 
/*     */ 
/* 317 */     String isFromResourcePage = request.getParameter("isfromresourcepage");
/*     */     
/* 319 */     String isfromeditpage = request.getParameter("isfromeditpage");
/* 320 */     request.setAttribute("hostname", hostName);
/* 321 */     request.setAttribute("portnumber", portNumber);
/* 322 */     request.setAttribute("resourcetype", resourceType);
/* 323 */     request.setAttribute("resourceid", resourceID);
/*     */     
/*     */     boolean status;
/* 326 */     if (mbeanAttribsArr != null) {
/* 327 */       checkAndChange(type, attributeid);
/* 328 */       int port = Integer.parseInt(portNumber);
/*     */       
/*     */ 
/* 331 */       String[] similarmonitorsselected = request.getParameterValues("similarmonitorsselected");
/*     */       
/* 333 */       if (("applyselected".equalsIgnoreCase(request.getParameter("multimonitors"))) && (similarmonitorsselected != null)) {
/* 334 */         String resIDs = resourceID;
/* 335 */         for (int i = 0; i < similarmonitorsselected.length; i++) {
/* 336 */           resIDs = resIDs + ", " + similarmonitorsselected[i];
/*     */         }
/* 338 */         ResultSet rs = null;
/* 339 */         String query = "select CAMID, SCREENID from AM_CAM_SCREEN where CAMID in (" + resIDs + ")";
/* 340 */         AMLog.debug("CAM Query to selected monitors :: " + query);
/*     */         try
/*     */         {
/* 343 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 344 */           boolean status; while (rs.next())
/*     */           {
/* 346 */             String resourceID1 = rs.getString("CAMID");
/* 347 */             String screenID1 = rs.getString("SCREENID");
/* 348 */             Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID1));
/* 349 */             status = CAMDBUtil.updateScreenAttributes(Integer.parseInt(camID), Integer.parseInt(screenID1), Integer.parseInt(resourceID1), type, mbeanAttribsArr, hostName, port, resourceType, (String)agentInfo.get("RESOURCENAME"), displayname);
/*     */           }
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 354 */           ex.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/* 358 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       } else {
/* 361 */         Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID));
/* 362 */         status = CAMDBUtil.updateScreenAttributes(Integer.parseInt(camID), Integer.parseInt(screenID), Integer.parseInt(resourceID), type, mbeanAttribsArr, hostName, port, resourceType, (String)agentInfo.get("RESOURCENAME"), displayname);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 367 */     List camDetails = CAMDBUtil.getCAMDetails(camID);
/*     */     
/* 369 */     request.setAttribute("camname", camDetails.get(0));
/* 370 */     request.setAttribute("camdesc", camDetails.get(2));
/*     */     
/* 372 */     if ("true".equals(isFromResourcePage)) {
/* 373 */       return new ActionForward("/showresource.do?resourceid=" + resourceID + "&method=showResourceForResourceID", true);
/*     */     }
/*     */     
/* 376 */     if ("true".equals(isfromeditpage))
/*     */     {
/* 378 */       ActionMessages messages = new ActionMessages();
/* 379 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.cam.editattribute.message.text")));
/* 380 */       saveMessages(request, messages);
/*     */       
/*     */ 
/* 383 */       return new ActionForward("/jsp/attribute_edit.jsp?dispType=" + type);
/*     */     }
/*     */     
/*     */ 
/* 387 */     String url = "/ShowCAM.do?method=configureScreen&screenid=" + request.getParameter("screenid") + "&camid=" + camID + "&haid=" + haid;
/* 388 */     return new ActionForward(url, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean checkAndChange(int type, String attributeid)
/*     */   {
/* 396 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 397 */     String qry = "select type from AM_ATTRIBUTES WHERE ATTRIBUTEID=" + attributeid;
/*     */     
/*     */     try
/*     */     {
/* 401 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 402 */       if (rs.next())
/*     */       {
/* 404 */         int type1 = rs.getInt(1);
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
/* 415 */         if (((type1 == 0) || (type1 == 1)) && (type == 3))
/*     */         {
/*     */ 
/* 418 */           String qry1 = "update AM_CAM_DC_ATTRIBUTES SET TYPE=1 WHERE ATTRIBUTEID=" + attributeid;
/* 419 */           String qry2 = "UPDATE AM_ATTRIBUTES SET TYPE=3 WHERE ATTRIBUTEID=" + attributeid;
/* 420 */           AMConnectionPool.executeUpdateStmt(qry1);
/* 421 */           AMConnectionPool.executeUpdateStmt(qry2);
/* 422 */           DBUtil.removeAttributeFromAttributeDetailsMap(attributeid);
/*     */         }
/* 424 */         else if (type1 != type)
/*     */         {
/*     */ 
/* 427 */           String qry1 = "update AM_CAM_DC_ATTRIBUTES SET TYPE=0 WHERE ATTRIBUTEID=" + attributeid;
/* 428 */           AMConnectionPool.executeUpdateStmt(qry1);
/* 429 */           String qry2 = "UPDATE AM_ATTRIBUTES SET TYPE=0 WHERE ATTRIBUTEID=" + attributeid;
/* 430 */           AMConnectionPool.executeUpdateStmt(qry2);
/* 431 */           DBUtil.removeAttributeFromAttributeDetailsMap(attributeid);
/*     */         }
/*     */       }
/* 434 */       rs.close();
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 438 */       exc.printStackTrace();
/*     */     }
/* 440 */     return true;
/*     */   }
/*     */   
/*     */   public ActionForward addMBeanAttributesDirectly(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 446 */     ActionErrors errors = new ActionErrors();
/*     */     try {
/* 448 */       String camID = request.getParameter("camid");
/*     */       
/* 450 */       List list = CAMDBUtil.getCAMDetails(camID);
/* 451 */       request.setAttribute("camname", list.get(0));
/* 452 */       request.setAttribute("camdesc", list.get(2));
/* 453 */       request.setAttribute("haid", request.getParameter("haid"));
/* 454 */       request.setAttribute("screenid", request.getParameter("screenid"));
/* 455 */       request.setAttribute("camid", camID);
/*     */       
/* 457 */       String hostName = request.getParameter("hostname");
/* 458 */       String portNumber = request.getParameter("portnumber");
/* 459 */       String resourceType = request.getParameter("resourcetype");
/* 460 */       String resourceID = request.getParameter("resourceid");
/* 461 */       String isFromResourcePage = request.getParameter("isfromresourcepage");
/*     */       
/* 463 */       request.setAttribute("hostname", hostName);
/* 464 */       request.setAttribute("portnumber", portNumber);
/* 465 */       request.setAttribute("resourcetype", resourceType);
/* 466 */       request.setAttribute("resourceid", resourceID);
/* 467 */       request.setAttribute("isfromresourcepage", isFromResourcePage);
/* 468 */       String attributeNames = request.getParameter("attributenames");
/* 469 */       String groupName = request.getParameter("groupName");
/* 470 */       String attributeType = request.getParameter("attributeType");
/* 471 */       ArrayList listofattribs = new ArrayList();
/* 472 */       StringTokenizer tokenizer = new StringTokenizer(attributeNames, ",");
/* 473 */       while (tokenizer.hasMoreTokens())
/*     */       {
/* 475 */         String token = tokenizer.nextToken();
/* 476 */         String entry = groupName + "|" + token + "|" + attributeType;
/* 477 */         listofattribs.add(entry);
/*     */       }
/* 479 */       String[] mbeanAttribsArr = new String[listofattribs.size()];
/* 480 */       for (int i = 0; i < mbeanAttribsArr.length; i++)
/*     */       {
/* 482 */         mbeanAttribsArr[i] = ((String)listofattribs.get(i));
/*     */       }
/* 484 */       Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID));
/* 485 */       String resourceName = (String)agentInfo.get("RESOURCENAME");
/* 486 */       boolean updated = CAMDBUtil.updateScreenAttributes(Integer.parseInt(camID), Integer.parseInt(request.getParameter("screenid")), Integer.parseInt(resourceID), 1, mbeanAttribsArr, hostName, Integer.parseInt(portNumber), resourceType, resourceName, "DEFAULT");
/* 487 */       if (updated)
/*     */       {
/* 489 */         ActionMessages messages = new ActionMessages();
/* 490 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.mbeans.added.manually"));
/* 491 */         saveMessages(request, messages);
/*     */       }
/*     */     }
/*     */     catch (Throwable ee) {
/* 495 */       ee.printStackTrace();
/* 496 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("mopaction.mbeanoperations.addingattributes.error"));
/*     */     }
/*     */     
/*     */ 
/* 500 */     if (!errors.isEmpty()) {
/* 501 */       saveErrors(request, errors);
/*     */     }
/* 503 */     return mapping.findForward("configurescreen");
/*     */   }
/*     */   
/*     */   public ActionForward deletethreadURL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 508 */     SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy-HH-mm-ss", Locale.getDefault());
/* 509 */     response.setContentType("text/html; charset=UTF-8");
/* 510 */     boolean delurl = false;
/* 511 */     if (request.getParameter("url") != null)
/*     */     {
/* 513 */       String temp = new File(".").getAbsolutePath();
/* 514 */       String nmshome = temp.substring(0, temp.length() - 2);
/* 515 */       File contentFile = new File(nmshome + request.getParameter("url"));
/* 516 */       if (contentFile.exists()) {
/* 517 */         delurl = contentFile.delete();
/*     */       }
/* 519 */       if ((delurl) || (!contentFile.exists())) {
/*     */         try {
/* 521 */           AMConnectionPool.executeUpdateStmt("delete from AM_MONITOR_DEBUG_INFO where URL ='" + request.getParameter("url") + "'");
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 525 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 529 */     return mapping.findForward("showmbeansandattributes");
/*     */   }
/*     */   
/*     */   public ActionForward getThreadDump(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 533 */     AMConnectionPool pool = new AMConnectionPool();
/* 534 */     String resid = request.getParameter("resourceid");
/* 535 */     response.setContentType("text/html; charset=UTF-8");
/* 536 */     PrintWriter out = response.getWriter();
/* 537 */     StringBuffer temp = null;
/* 538 */     SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy-HH-mm-ss", Locale.getDefault());
/* 539 */     temp = new StringBuffer();
/* 540 */     ResultSet rs = null;
/*     */     try {
/* 542 */       temp.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'>");
/* 543 */       String query = "select URL,COLLECTIONTIME from AM_MONITOR_DEBUG_INFO  where RESOURCEID=" + resid + " and TYPE='Thread Dump' order by Collectiontime desc";
/* 544 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 545 */       String url = null;
/* 546 */       int rowCount = 0;
/* 547 */       while (rs.next()) {
/* 548 */         if (rowCount <= 4)
/*     */         {
/* 550 */           rowCount++;
/*     */         }
/*     */         else {
/* 553 */           url = rs.getString("URL") + "?resourceid=" + resid;
/* 554 */           Date logDate = new Date(rs.getLong("COLLECTIONTIME"));
/* 555 */           temp.append("<tr>");
/* 556 */           temp.append("<td width='80%' style='padding-left:25px' class='whitegrayborderbr' title='" + url + "'> <a class='staticlinks-blue' href='javascript:void(0);' onclick=\"javascript:MM_openBrWindow('" + url + "','ThreadInfo','scrollbars=yes,resizable=yes')\">" + sdf.format(logDate) + "</a></td>");
/* 557 */           if ((request.isUserInRole("ADMIN")) && (!EnterpriseUtil.isAdminServer()))
/*     */           {
/* 559 */             temp.append("<td width='20%' class='whitegrayborderbr'> <a title='" + FormatUtil.getString("am.javaruntime.threaddump.delete") + "' class='staticlinks' href='javascript:void(0);' onclick=\"javascript:deleteThreadDump('" + rs.getString("URL") + "','" + resid + "');return false;\"><img hspace='5' border='0' src='/images/deleteWidget.gif'/> </a></td>");
/*     */           }
/* 561 */           temp.append("</tr>");
/*     */         }
/*     */       }
/* 564 */       AMConnectionPool.closeStatement(rs);
/* 565 */       temp.append("</table>");
/*     */     } catch (Exception e) {
/* 567 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 570 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 572 */     out.println(temp);
/* 573 */     out.flush();
/* 574 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\CAMJMXAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */