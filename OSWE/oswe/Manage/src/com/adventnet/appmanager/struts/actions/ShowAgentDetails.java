/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.server.framework.AgentUtil;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ShowAgentDetails
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward getAgentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  49 */     String condition = "";
/*     */     try {
/*  51 */       if (EnterpriseUtil.isAdminServer())
/*     */       {
/*  53 */         String idRangeStartsFrom = ((AMActionForm)form).getSelectedServer();
/*  54 */         idRangeStartsFrom = request.getParameter("selectedServer");
/*  55 */         if ((idRangeStartsFrom != null) && (!idRangeStartsFrom.equals("0")))
/*     */         {
/*  57 */           int serverId = Integer.parseInt(idRangeStartsFrom);
/*  58 */           if (serverId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE)
/*     */           {
/*  60 */             idRangeStartsFrom = serverId * com.adventnet.appmanager.server.framework.comm.Constants.RANGE + "";
/*     */           }
/*  62 */           int maxId = Integer.parseInt(idRangeStartsFrom) + com.adventnet.appmanager.server.framework.comm.Constants.RANGE;
/*  63 */           condition = " BETWEEN " + idRangeStartsFrom + " AND (" + (maxId - 1) + ")";
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  69 */       e.printStackTrace();
/*     */     }
/*  71 */     String getagent = "select AGENTID,DISPLAYNAME,AGENTIP,PORT,STATUS,POLLINTERVAL,AGENTVERSION,LASTUPDATEDTIME from AM_RBMAGENTDATA where AGENTID >" + EnterpriseUtil.getDistributedStartResourceId();
/*  72 */     if (EnterpriseUtil.isAdminServer())
/*     */     {
/*  74 */       getagent = "select AGENTID,DISPLAYNAME,AGENTIP,PORT,STATUS,POLLINTERVAL,AGENTVERSION,LASTUPDATEDTIME from AM_RBMAGENTDATA where AGENTID not in(select ALLOTED_GLOBAL_RANGE from AM_MAS_SERVER)";
/*  75 */       if (!condition.equals(""))
/*     */       {
/*  77 */         getagent = getagent + " and AGENTID " + condition;
/*     */       }
/*     */     }
/*  80 */     ResultSet rs = null;
/*  81 */     ArrayList<LinkedHashMap<String, String>> agentList = new ArrayList();
/*     */     try {
/*  83 */       rs = AMConnectionPool.executeQueryStmt(getagent);
/*  84 */       while (rs.next())
/*     */       {
/*  86 */         LinkedHashMap<String, String> agentDetails = new LinkedHashMap();
/*  87 */         agentDetails.put("AGENTID", rs.getString("AGENTID"));
/*  88 */         agentDetails.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*  89 */         agentDetails.put("AGENTIP", rs.getString("AGENTIP"));
/*  90 */         agentDetails.put("AGENTPORT", rs.getString("PORT"));
/*  91 */         agentDetails.put("STATUS", rs.getString("STATUS"));
/*     */         
/*  93 */         int pollinterval = 1;
/*     */         try
/*     */         {
/*  96 */           pollinterval = Integer.parseInt(rs.getString("POLLINTERVAL")) / 60000;
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 100 */           AMLog.debug("pollinterval value in getAgentDetails is :  " + e.getMessage());
/*     */         }
/*     */         
/* 103 */         agentDetails.put("POLLINTERVAL", pollinterval + FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 104 */         agentDetails.put("AGENTVERSION", rs.getString("AGENTVERSION"));
/* 105 */         String lastDC = "-";
/* 106 */         if ((rs.getString("LASTUPDATEDTIME") != null) && (!rs.getString("LASTUPDATEDTIME").equals("-")))
/*     */         {
/* 108 */           lastDC = FormatUtil.formatDT(rs.getString("LASTUPDATEDTIME"));
/*     */         }
/* 110 */         agentDetails.put("LASTUPDATEDTIME", lastDC);
/* 111 */         agentList.add(agentDetails);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 121 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 125 */         e.printStackTrace();
/*     */       }
/*     */       
/* 128 */       if (!EnterpriseUtil.isAdminServer()) {
/*     */         break label611;
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 115 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 121 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 125 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 130 */     ArrayList<Properties> managedServers = getMangagedServers();
/* 131 */     if ((managedServers != null) && (!managedServers.isEmpty()))
/*     */     {
/* 133 */       request.setAttribute("managedServers", managedServers);
/* 134 */       ((AMActionForm)form).setManagedServers(managedServers);
/*     */     }
/*     */     label611:
/* 137 */     String ajaxRequest = request.getParameter("ajaxRequest");
/* 138 */     if ((ajaxRequest != null) && (ajaxRequest.equals("true")) && (EnterpriseUtil.isAdminServer()))
/*     */     {
/* 140 */       response.setContentType("text/html; charset=UTF-8");
/* 141 */       PrintWriter pw = response.getWriter();
/* 142 */       pw.print("<table class=\"bodytext upgradeAmountBg1\" width=\"100%\">");
/* 143 */       if (agentList.isEmpty())
/*     */       {
/* 145 */         pw.print("<tr><td><span class=\"bodytext\">" + FormatUtil.getString("am.webclient.eumdashboard.noagents") + "</span></td></tr>");
/*     */       }
/*     */       else
/*     */       {
/* 149 */         int size = agentList.size();
/* 150 */         pw.print("<tr><td><table class=\"bodytext upgradeAmountBg1\" width=\"55%\"><tr height=\"28\"><span class=\"bodytext\"><input type=\"checkbox\" name=\"selectAllAgents\" onclick=\"toggleChecked(this.checked)\">" + FormatUtil.getString("am.webclient.eumagent.selectall") + "</span><br><br>");
/* 151 */         for (int i = 0; i < size; i++)
/*     */         {
/* 153 */           LinkedHashMap<String, String> agentDetails = (LinkedHashMap)agentList.get(i);
/* 154 */           String agentId = (String)agentDetails.get("AGENTID");
/* 155 */           String displayName = FormatUtil.getTrimmedText((String)agentDetails.get("DISPLAYNAME"), 23);
/* 156 */           String textclass = "color='black'";
/* 157 */           String status = (String)agentDetails.get("STATUS");
/* 158 */           if (!status.equals("0"))
/*     */           {
/* 160 */             textclass = "color='red'";
/* 161 */             status = FormatUtil.getString("am.webclient.eumdashboard.agentdown");
/*     */           }
/* 163 */           pw.print("<td nowrap=\"nowrap\" style=\"padding-left:18px;\">");
/* 164 */           pw.print("<input type=\"checkbox\" name=\"selectedAgents\" onclick=\"javascript:findSelectedIndex('" + i + "');\" value='" + agentId + "'/>");
/* 165 */           pw.print("<a style=\"cursor:pointer\" class=\"tooltip\" onMouseOver=\"ddrivetip(this,event,'" + status + "',false,true,'#000000',100,'lightyellow')\" onmouseout='" + (status.equals("") ? "" : "hideddrivetip()") + "'><font " + textclass + ">" + displayName + "</font></a></td>");
/* 166 */           if ((i + 1) % 4 == 0)
/*     */           {
/* 168 */             pw.print("</tr><tr height=\"5px\"><td colspan=\"3\"><img src=\"/images/spacer.gif\"></img></tr><tr>");
/*     */           }
/*     */         }
/* 171 */         pw.print("</table></td></tr>");
/*     */       }
/* 173 */       pw.print("</table>");
/* 174 */       pw.flush();
/* 175 */       return null;
/*     */     }
/* 177 */     request.setAttribute("agentList", agentList);
/* 178 */     String path = "/jsp/EumDetailsPage.jsp";
/* 179 */     return new ActionForward(path);
/*     */   }
/*     */   
/*     */   public ActionForward editAgentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 185 */     ActionMessages messages = new ActionMessages();
/* 186 */     String agentId = request.getParameter("agentId");
/* 187 */     String oldDisplayName = request.getParameter("oldName");
/* 188 */     String newName = request.getParameter("agentName");
/* 189 */     String newIp = request.getParameter("agentIp");
/* 190 */     PrintWriter pw = response.getWriter();
/* 191 */     String updateAgent = "update AM_RBMAGENTDATA set DISPLAYNAME='" + newName + "',AGENTIP='" + newIp + "' Where AGENTID=" + agentId;
/*     */     try {
/* 193 */       AMConnectionPool.executeUpdateStmt(updateAgent);
/* 194 */       EnterpriseUtil.addUpdateQueryToFile(updateAgent);
/* 195 */       if (!oldDisplayName.equals(newName)) {
/* 196 */         EditAssociatedResourceNames(newName, agentId);
/*     */       }
/* 198 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.eumdashboard.edit.msgsuccess")));
/* 199 */       saveMessages(request, messages);
/* 200 */       pw.print("Success");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 204 */       e.printStackTrace();
/* 205 */       messages.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.eumdashboard.edit.msgfailure")));
/* 206 */       saveMessages(request, messages);
/* 207 */       pw.print("Failure");
/*     */     }
/*     */     
/* 210 */     return null;
/*     */   }
/*     */   
/*     */   public void EditAssociatedResourceNames(String agentName, String agentId) throws Exception
/*     */   {
/* 215 */     NewMonitorUtil.updateEUMChildDisplayNames(agentId, agentName, "", "Agent");
/*     */   }
/*     */   
/*     */   private void setHealthPropsInReq(HttpServletRequest request, Hashtable healthkeys, Vector<String> eumMonIdsVector, String ownerCondition, String adminServerCondition)
/*     */   {
/* 220 */     ResultSet rs = null;
/* 221 */     ConfMonitorConfiguration confConfig = ConfMonitorConfiguration.getInstance();
/* 222 */     String eumImage = "";
/* 223 */     LinkedHashMap<String, Hashtable<Object, Object>> typeWiseAlertCountMap = null;
/* 224 */     ArrayList<String> allEntities = null;
/* 225 */     ArrayList<String> parentIdList = null;
/* 226 */     int totalMons = 0;
/* 227 */     LinkedHashMap<String, ArrayList<String>> groupedTypeMap = new LinkedHashMap();
/*     */     
/*     */     try
/*     */     {
/* 231 */       typeWiseAlertCountMap = new LinkedHashMap();
/* 232 */       allEntities = new ArrayList();
/* 233 */       parentIdList = new ArrayList();
/*     */       
/* 235 */       StringBuilder query = new StringBuilder("select RESOURCETYPE from AM_ManagedResourceType where RESOURCETYPE IN ").append(com.adventnet.appmanager.util.Constants.resourceTypesEUM);
/* 236 */       String notSupported = com.adventnet.appmanager.util.Constants.getNotSupported();
/* 237 */       if ((com.adventnet.appmanager.util.Constants.getCategorytype().equalsIgnoreCase("CLOUD")) && (notSupported != null) && (!notSupported.trim().equalsIgnoreCase("")))
/*     */       {
/*     */ 
/* 240 */         query = query.append(" and RESOURCETYPE NOT IN").append(notSupported);
/*     */       }
/*     */       try
/*     */       {
/* 244 */         rs = AMConnectionPool.executeQueryStmt(query.toString());
/* 245 */         while (rs.next())
/*     */         {
/* 247 */           groupedTypeMap.put(rs.getString("RESOURCETYPE"), new ArrayList());
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 252 */         e.printStackTrace();
/*     */       }
/*     */       finally {}
/*     */       
/*     */ 
/*     */ 
/* 258 */       query = new StringBuilder("select RESOURCEID,TYPE from AM_ManagedObject where ").append(ReportUtilities.getCondition("RESOURCEID", eumMonIdsVector)).append(" and TYPE IN ").append(com.adventnet.appmanager.util.Constants.resourceTypesEUM);
/*     */       
/* 260 */       if ((com.adventnet.appmanager.util.Constants.getCategorytype().equalsIgnoreCase("CLOUD")) && (notSupported.length() > 0))
/*     */       {
/* 262 */         query = query.append(" and TYPE NOT IN").append(com.adventnet.appmanager.util.Constants.getNotSupported());
/*     */       }
/* 264 */       query.append(ownerCondition).append(" and RESOURCEID " + adminServerCondition);
/* 265 */       rs = AMConnectionPool.executeQueryStmt(query.toString());
/*     */       
/* 267 */       while (rs.next())
/*     */       {
/* 269 */         String resourceType = rs.getString("TYPE");
/* 270 */         String resID = rs.getString("RESOURCEID");
/* 271 */         String entity = resID + "_" + healthkeys.get(resourceType);
/* 272 */         parentIdList = (ArrayList)groupedTypeMap.get(resourceType);
/* 273 */         parentIdList.add(resID);
/* 274 */         allEntities.add(entity);
/* 275 */         groupedTypeMap.put(resourceType, parentIdList);
/*     */       }
/*     */       
/* 278 */       Properties healthProps = FaultUtil.getStatus(allEntities, false);
/*     */       
/*     */ 
/*     */ 
/* 282 */       Object grpItr = groupedTypeMap.keySet().iterator();
/* 283 */       Hashtable<Object, Object> allht = new Hashtable();
/* 284 */       ArrayList<String> allClearList = new ArrayList();
/* 285 */       ArrayList<String> allCriticalList = new ArrayList();
/* 286 */       ArrayList<String> allWarningList = new ArrayList();
/* 287 */       while (((Iterator)grpItr).hasNext())
/*     */       {
/* 289 */         String parentResType = (String)((Iterator)grpItr).next();
/* 290 */         ArrayList<String> eumResIDList = (ArrayList)groupedTypeMap.get(parentResType);
/* 291 */         ArrayList<String> clearList = new ArrayList();
/* 292 */         ArrayList<String> criticalList = new ArrayList();
/* 293 */         ArrayList<String> warningList = new ArrayList();
/* 294 */         Hashtable<Object, Object> alertInfoOfIndResType = new Hashtable();
/*     */         
/* 296 */         int noOfMonsPerType = eumResIDList.size();
/* 297 */         totalMons += noOfMonsPerType;
/* 298 */         int clear = 0;
/* 299 */         int critical = 0;
/* 300 */         int warning = 0;
/* 301 */         int criticalPercentage = 0;
/* 302 */         int clearPercentage = 0;
/* 303 */         int warningPercentage = 0;
/*     */         
/* 305 */         for (int i = 0; i < noOfMonsPerType; i++)
/*     */         {
/* 307 */           String healthEntity = (String)eumResIDList.get(i) + "#" + healthkeys.get(parentResType);
/* 308 */           if (healthProps.getProperty(healthEntity) != null)
/*     */           {
/* 310 */             int status = Integer.parseInt(healthProps.getProperty(healthEntity));
/* 311 */             if (status == 5)
/*     */             {
/* 313 */               clear++;
/* 314 */               clearList.add(eumResIDList.get(i));
/*     */             }
/* 316 */             else if (status == 1)
/*     */             {
/* 318 */               critical++;
/* 319 */               criticalList.add(eumResIDList.get(i));
/*     */             }
/* 321 */             else if (status == 4)
/*     */             {
/* 323 */               warning++;
/* 324 */               warningList.add(eumResIDList.get(i));
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 329 */         alertInfoOfIndResType.put("Clear", Integer.valueOf(clear));
/* 330 */         alertInfoOfIndResType.put("Critical", Integer.valueOf(critical));
/* 331 */         alertInfoOfIndResType.put("Warning", Integer.valueOf(warning));
/* 332 */         alertInfoOfIndResType.put("Total", Integer.valueOf(noOfMonsPerType));
/*     */         
/*     */         try
/*     */         {
/* 336 */           clearPercentage = clear * 100 / noOfMonsPerType;
/* 337 */           criticalPercentage = critical * 100 / noOfMonsPerType;
/* 338 */           warningPercentage = warning * 100 / noOfMonsPerType;
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 342 */           AMLog.debug("Below exception is harmless. Happens if noOfMonsPerType is 0.");
/* 343 */           AMLog.debug(e.getMessage());
/*     */         }
/*     */         
/* 346 */         alertInfoOfIndResType.put("clearPercentage", Integer.valueOf(clearPercentage));
/* 347 */         alertInfoOfIndResType.put("criticalPercentage", Integer.valueOf(criticalPercentage));
/* 348 */         alertInfoOfIndResType.put("warningPercentage", Integer.valueOf(warningPercentage));
/*     */         
/* 350 */         alertInfoOfIndResType.put(Integer.valueOf(5), clearList);
/* 351 */         alertInfoOfIndResType.put(Integer.valueOf(1), criticalList);
/* 352 */         alertInfoOfIndResType.put(Integer.valueOf(4), warningList);
/*     */         
/* 354 */         if ((confConfig.getTypeDescription(parentResType) != null) && (!confConfig.getTypeDescription(parentResType).isEmpty()))
/*     */         {
/* 356 */           eumImage = "/images/eumDB_" + confConfig.getTypeDescription(parentResType).get("Image");
/*     */         }
/*     */         else
/*     */         {
/* 360 */           eumImage = "/images/eumDB_icon_monitors_rbm.gif";
/*     */         }
/* 362 */         alertInfoOfIndResType.put("imageName", eumImage);
/* 363 */         allClearList.addAll(clearList);
/* 364 */         allCriticalList.addAll(criticalList);
/* 365 */         allWarningList.addAll(warningList);
/* 366 */         typeWiseAlertCountMap.put(parentResType, alertInfoOfIndResType);
/*     */       }
/*     */       
/* 369 */       allht.put(Integer.valueOf(5), allClearList);
/* 370 */       allht.put(Integer.valueOf(1), allCriticalList);
/* 371 */       allht.put(Integer.valueOf(4), allWarningList);
/*     */       
/* 373 */       allht.put("Clear", Integer.valueOf(allClearList.size()));
/* 374 */       allht.put("Critical", Integer.valueOf(allCriticalList.size()));
/* 375 */       allht.put("Warning", Integer.valueOf(allWarningList.size()));
/* 376 */       allht.put("Total", Integer.valueOf(totalMons));
/*     */       
/* 378 */       int allClearper = 0;
/* 379 */       int allCriticalper = 0;
/* 380 */       int allWarningper = 0;
/*     */       try
/*     */       {
/* 383 */         allClearper = allClearList.size() * 100 / totalMons;
/* 384 */         allCriticalper = allCriticalList.size() * 100 / totalMons;
/* 385 */         allWarningper = allWarningList.size() * 100 / totalMons;
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 389 */         AMLog.debug("Below exception is harmless. Happens if totalMons is 0.");
/* 390 */         AMLog.debug(e.getMessage());
/*     */       }
/*     */       
/* 393 */       allht.put("clearPercentage", Integer.valueOf(allClearper));
/* 394 */       allht.put("criticalPercentage", Integer.valueOf(allCriticalper));
/* 395 */       allht.put("warningPercentage", Integer.valueOf(allWarningper));
/*     */       
/* 397 */       typeWiseAlertCountMap.put("zxy", allht);
/* 398 */       request.setAttribute("typeWiseAlertCountMap", typeWiseAlertCountMap);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 402 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 406 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward showEumSnapshot(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 414 */     LinkedHashMap<String, HashMap<String, String>> parentVSagentIdVsResId = new LinkedHashMap();
/* 415 */     LinkedHashMap<String, String> agentIdVsDispName = new LinkedHashMap();
/* 416 */     LinkedHashMap<String, String> agentIdVsStatus = new LinkedHashMap();
/* 417 */     LinkedHashMap<String, String> idVsDispName = new LinkedHashMap();
/* 418 */     HashMap<String, String> agentIdVsUrl = new HashMap();
/* 419 */     HashMap<String, String> resIdVsType = new HashMap();
/* 420 */     ArrayList<String> parentList = new ArrayList();
/* 421 */     ArrayList<String> healthEntityList = new ArrayList();
/* 422 */     HashMap<String, String> agentIdVsResId = null;
/* 423 */     Properties healthProps = new Properties();
/* 424 */     Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/* 425 */     String localId = EnterpriseUtil.getDistributedStartResourceId();
/* 426 */     AMActionForm myForm = (AMActionForm)form;
/* 427 */     String selectedType = myForm.getResTypeValue();
/* 428 */     String severity = myForm.getSeverity();
/* 429 */     String typeToGet = selectedType;
/* 430 */     Vector<String> eumMonIdsVector = com.adventnet.appmanager.util.Constants.getEUMMonitorIds(false);
/* 431 */     String severityCondition = "";
/* 432 */     String adminServerCondition = "";
/* 433 */     String rbmCondition = "";
/*     */     
/*     */     try
/*     */     {
/* 437 */       if (EnterpriseUtil.isAdminServer())
/*     */       {
/* 439 */         String idRangeStartsFrom = myForm.getSelectedServer();
/* 440 */         if ((idRangeStartsFrom != null) && (!idRangeStartsFrom.equals("0")))
/*     */         {
/* 442 */           int maxId = Integer.parseInt(idRangeStartsFrom) + com.adventnet.appmanager.server.framework.comm.Constants.RANGE;
/* 443 */           adminServerCondition = " BETWEEN " + idRangeStartsFrom + " AND (" + (maxId - 1) + ")";
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 449 */       e.printStackTrace();
/*     */     }
/* 451 */     String ownerCondition = "";
/* 452 */     if (ClientDBUtil.isPrivilegedUser(request))
/*     */     {
/* 454 */       if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 455 */         String loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 456 */         ownerCondition = " and RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ")";
/*     */       } else {
/* 458 */         Vector allowedResid = ReportUtilities.getResourceIdentity(request.getRemoteUser());
/* 459 */         ownerCondition = " and " + ReportUtilities.getCondition("RESOURCEID", allowedResid);
/*     */       }
/*     */     }
/*     */     
/* 463 */     setHealthPropsInReq(request, healthkeys, eumMonIdsVector, ownerCondition, adminServerCondition);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 468 */     if (selectedType.equals("zxy"))
/*     */     {
/* 470 */       selectedType = com.adventnet.appmanager.util.Constants.resourceTypesEUM;
/*     */     }
/*     */     else
/*     */     {
/* 474 */       if ((selectedType.equals("RBM")) || (selectedType.equals("Selenium")))
/*     */       {
/* 476 */         rbmCondition = adminServerCondition.equals("") ? " where PORT > 0 " : " and PORT > 0 ";
/*     */       }
/* 478 */       selectedType = "('" + selectedType + "')";
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 484 */       int sev = Integer.parseInt(severity);
/* 485 */       request.setAttribute("eumSeverity", "" + sev);
/* 486 */       ArrayList<String> filteredList = (ArrayList)((Hashtable)((LinkedHashMap)request.getAttribute("typeWiseAlertCountMap")).get(typeToGet)).get(Integer.valueOf(sev));
/* 487 */       severityCondition = severityCondition + " and " + ReportUtilities.getCondition("RESOURCEID", new Vector(filteredList));
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 496 */       StringBuilder parentQuery = new StringBuilder("select RESOURCEID,TYPE,DISPLAYNAME from AM_ManagedObject where ").append(ReportUtilities.getCondition("RESOURCEID", eumMonIdsVector)).append(" and TYPE IN ").append(selectedType).append(severityCondition);
/* 497 */       parentQuery.append(" and RESOURCEID " + adminServerCondition).append(ownerCondition).append(" order by TYPE");
/* 498 */       ResultSet parentRs = null;
/*     */       try
/*     */       {
/* 501 */         parentRs = AMConnectionPool.executeQueryStmt(parentQuery.toString());
/* 502 */         while (parentRs.next())
/*     */         {
/* 504 */           String parentId = parentRs.getString("RESOURCEID");
/* 505 */           parentList.add(parentId);
/* 506 */           resIdVsType.put(parentId, parentRs.getString("TYPE"));
/* 507 */           idVsDispName.put(parentId, parentRs.getString("DISPLAYNAME"));
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 512 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 516 */         AMConnectionPool.closeStatement(parentRs);
/*     */       }
/* 518 */       if (!parentList.isEmpty())
/*     */       {
/* 520 */         String parentCondition = " and " + ReportUtilities.getCondition("AM_EUM_PARENTCHILD_MAPPING.PARENTID", new Vector(parentList));
/* 521 */         StringBuilder childQuery = new StringBuilder("select AM_EUM_PARENTCHILD_MAPPING.PARENTID as PARENTID,AM_ManagedObject.resourceid as CHILDID,AM_ManagedObject.TYPE,AM_ManagedObject.Displayname as DISPLAYNAME,COALESCE(AM_RBMAGENTDATA.Displayname,'Local') as AGENTDISPNAME,COALESCE(AM_RBMAGENTDATA.AGENTID," + localId + ") as AGENTID,COALESCE(AM_RBMAGENTDATA.STATUS,0) as STATUS from AM_ManagedObject join AM_EUM_PARENTCHILD_MAPPING on AM_ManagedObject.resourceid=AM_EUM_PARENTCHILD_MAPPING.childid  ").append(parentCondition).append(" join AM_RESOURCE_AGENT_MAPPING on AM_EUM_PARENTCHILD_MAPPING.childid=AM_RESOURCE_AGENT_MAPPING.RESOURCEID left outer join AM_RBMAGENTDATA on AM_RBMAGENTDATA.AGENTID=AM_RESOURCE_AGENT_MAPPING.AGENTID order by AM_ManagedObject.RESOURCEID");
/* 522 */         ResultSet childRs = null;
/*     */         try
/*     */         {
/* 525 */           childRs = AMConnectionPool.executeQueryStmt(childQuery.toString());
/* 526 */           while (childRs.next())
/*     */           {
/* 528 */             String parentId = childRs.getString("PARENTID");
/* 529 */             String resType = childRs.getString("TYPE");
/* 530 */             String resDispName = childRs.getString("DISPLAYNAME");
/* 531 */             String childID = childRs.getString("CHILDID");
/* 532 */             String agentId = childRs.getString("AGENTID");
/*     */             
/* 534 */             if (!parentList.contains(parentId))
/*     */             {
/* 536 */               parentList.add(parentId);
/*     */             }
/* 538 */             if (!resIdVsType.containsKey(parentId))
/*     */             {
/* 540 */               resIdVsType.put(parentId, resType);
/*     */             }
/* 542 */             if (!resIdVsType.containsKey(childID))
/*     */             {
/* 544 */               resIdVsType.put(childID, resType);
/*     */             }
/* 546 */             if (!idVsDispName.containsKey(parentId))
/*     */             {
/* 548 */               idVsDispName.put(parentId, resDispName);
/*     */             }
/* 550 */             if (!agentIdVsDispName.containsKey(agentId))
/*     */             {
/* 552 */               agentIdVsDispName.put(agentId, childRs.getString("AGENTDISPNAME"));
/*     */             }
/* 554 */             if (!agentIdVsStatus.containsKey(agentId))
/*     */             {
/* 556 */               agentIdVsStatus.put(agentId, childRs.getString("STATUS"));
/*     */             }
/* 558 */             healthEntityList.add(childID + "_" + healthkeys.get(resType));
/* 559 */             if (parentVSagentIdVsResId.containsKey(parentId))
/*     */             {
/* 561 */               agentIdVsResId = (HashMap)parentVSagentIdVsResId.get(parentId);
/*     */             }
/*     */             else
/*     */             {
/* 565 */               agentIdVsResId = new HashMap();
/*     */             }
/* 567 */             agentIdVsResId.put(agentId, childID);
/* 568 */             parentVSagentIdVsResId.put(parentId, agentIdVsResId);
/*     */           }
/* 570 */           healthProps = FaultUtil.getStatus(healthEntityList, false);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 574 */           e.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/* 578 */           AMConnectionPool.closeStatement(childRs);
/*     */         }
/*     */       }
/* 581 */       StringBuilder agentQuery = new StringBuilder("select AGENTID,DISPLAYNAME,STATUS,AGENTNAME,PORT from AM_RBMAGENTDATA ").append(" where AGENTID " + adminServerCondition);
/* 582 */       agentQuery.append(rbmCondition);
/* 583 */       ResultSet agentRs = AMConnectionPool.executeQueryStmt(agentQuery.toString());
/*     */       try
/*     */       {
/* 586 */         while (agentRs.next())
/*     */         {
/* 588 */           String agentId = agentRs.getString("AGENTID");
/* 589 */           if (!agentIdVsDispName.containsKey(agentId))
/*     */           {
/* 591 */             agentIdVsDispName.put(agentId, agentRs.getString("DISPLAYNAME"));
/* 592 */             agentIdVsStatus.put(agentId, agentRs.getString("STATUS"));
/*     */           }
/* 594 */           String port = agentRs.getString("PORT");
/* 595 */           if ((!agentIdVsUrl.containsKey(agentId)) && (port != null) && (Integer.parseInt(port) > 0))
/*     */           {
/* 597 */             agentIdVsUrl.put(agentId, "http://" + agentRs.getString("AGENTNAME") + ":" + agentRs.getString("PORT"));
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception agentEx)
/*     */       {
/* 603 */         agentEx.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 607 */         AMConnectionPool.closeStatement(agentRs);
/*     */       }
/*     */       
/* 610 */       if (EnterpriseUtil.isAdminServer())
/*     */       {
/* 612 */         ArrayList<Properties> managedServers = getMangagedServers();
/* 613 */         if ((managedServers != null) && (!managedServers.isEmpty()))
/*     */         {
/* 615 */           request.setAttribute("managedServers", managedServers);
/* 616 */           myForm.setManagedServers(managedServers);
/*     */         }
/*     */       }
/*     */       
/* 620 */       request.setAttribute("agentIdVsDispName", agentIdVsDispName);
/* 621 */       request.setAttribute("agentIdVsStatus", agentIdVsStatus);
/* 622 */       request.setAttribute("parentList", parentList);
/* 623 */       request.setAttribute("idVsDispName", idVsDispName);
/* 624 */       request.setAttribute("agentIdVsUrl", agentIdVsUrl);
/* 625 */       request.setAttribute("parentVSagentIdVsResId", parentVSagentIdVsResId);
/* 626 */       request.setAttribute("resIdVsType", resIdVsType);
/* 627 */       request.setAttribute("healthProps", healthProps);
/* 628 */       request.setAttribute("resType", typeToGet);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 632 */       e.printStackTrace();
/*     */     }
/* 634 */     return new ActionForward("/jsp/eumDashboard.jsp");
/*     */   }
/*     */   
/*     */   public static ArrayList<Properties> getMangagedServers()
/*     */   {
/* 639 */     String getMangaged = "select ALLOTED_GLOBAL_RANGE,DISPLAYNAME,SERVERSTATUS from AM_MAS_SERVER";
/* 640 */     ArrayList<Properties> managedServers = new ArrayList();
/* 641 */     ResultSet manserverRs = null;
/*     */     try
/*     */     {
/* 644 */       manserverRs = AMConnectionPool.executeQueryStmt(getMangaged);
/* 645 */       while (manserverRs.next())
/*     */       {
/* 647 */         String idrange = manserverRs.getString("ALLOTED_GLOBAL_RANGE");
/* 648 */         String disName = manserverRs.getString("DISPLAYNAME");
/*     */         
/* 650 */         Properties data = new Properties();
/* 651 */         data.setProperty("label", disName);
/* 652 */         data.setProperty("value", idrange);
/* 653 */         managedServers.add(data);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 657 */       e.printStackTrace();
/*     */     }
/* 659 */     return managedServers;
/*     */   }
/*     */   
/* 662 */   public static String getEndingRange(String startingRange) { String endingRange = "";
/* 663 */     String query = "select ALLOTED_GLOBAL_RANGE  from AM_MAS_SERVER where ID > (select ID from AM_MAS_SERVER where ALLOTED_GLOBAL_RANGE =" + startingRange + ") order by ID";
/* 664 */     query = DBQueryUtil.getTopNValues(query, "1");
/* 665 */     ResultSet endingRangeRs = null;
/*     */     try {
/* 667 */       endingRangeRs = AMConnectionPool.executeQueryStmt(query);
/* 668 */       if (endingRangeRs.next()) {
/* 669 */         endingRange = endingRangeRs.getString("ALLOTED_GLOBAL_RANGE");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 673 */       e.printStackTrace();
/* 674 */       return endingRange;
/*     */     }
/* 676 */     return endingRange;
/*     */   }
/*     */   
/*     */   public ActionForward deleteAgent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 682 */     String agentId = request.getParameter("agentId");
/* 683 */     String agentName = request.getParameter("agentName");
/*     */     try
/*     */     {
/* 686 */       boolean isdeletesuccessful = AgentUtil.deleteAgent(agentId);
/* 687 */       if (isdeletesuccessful) {
/* 688 */         request.setAttribute("AgentStatus", "Success");
/* 689 */         request.setAttribute("AgentMsg", FormatUtil.getString("am.webclient.EUM.deleteAgentSuccess", new String[] { agentName }));
/*     */       }
/*     */       else
/*     */       {
/* 693 */         request.setAttribute("AgentStatus", "Failure");
/* 694 */         request.setAttribute("AgentMsg", FormatUtil.getString("am.webclient.EUM.deleteAgentFailed", new String[] { agentName }));
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 698 */       e.printStackTrace();
/* 699 */       request.setAttribute("AgentStatus", "Failure");
/* 700 */       request.setAttribute("AgentMsg", FormatUtil.getString("am.webclient.EUM.deleteAgentFailed", new String[] { agentName }));
/*     */     }
/* 702 */     return new ActionForward("/showAgent.do?method=getAgentDetails");
/*     */   }
/*     */   
/*     */   public ActionForward getAgentStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 708 */     ResultSet rs = null;
/* 709 */     PrintWriter out = null;
/* 710 */     String agentStatus = "";
/*     */     try
/*     */     {
/* 713 */       String agentId = request.getParameter("agentId");
/* 714 */       String agentName = request.getParameter("agentName");
/*     */       
/* 716 */       String qry = "select STATUS from AM_RBMAGENTDATA where AGENTID=" + agentId;
/* 717 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 718 */       while (rs.next())
/*     */       {
/* 720 */         agentStatus = rs.getString("STATUS");
/*     */       }
/*     */       
/* 723 */       if (agentStatus.equals("0"))
/*     */       {
/* 725 */         agentStatus = FormatUtil.getString("am.webclient.eum.alert.agentdelete", new String[] { agentName });
/*     */       }
/*     */       else
/*     */       {
/* 729 */         agentStatus = "";
/*     */       }
/* 731 */       response.setContentType("text/html;charset=UTF-8");
/* 732 */       out = response.getWriter();
/* 733 */       out.write(agentStatus);
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
/* 750 */       return null;
/*     */     }
/*     */     catch (Exception e) {}finally
/*     */     {
/* 740 */       AMConnectionPool.closeStatement(rs);
/* 741 */       if (out != null)
/*     */       {
/*     */         try
/*     */         {
/* 745 */           out.close();
/*     */         }
/*     */         catch (Exception e) {}
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ShowAgentDetails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */