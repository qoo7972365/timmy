/*      */ package com.manageengine.appmanager.amazon.client.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.struts.actions.AdminTools;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.AppManagerUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.amazonaws.AmazonClientException;
/*      */ import com.amazonaws.AmazonServiceException;
/*      */ import com.amazonaws.auth.AWSCredentials;
/*      */ import com.amazonaws.auth.BasicAWSCredentials;
/*      */ import com.amazonaws.services.ec2.AmazonEC2;
/*      */ import com.amazonaws.services.ec2.model.GetConsoleOutputResult;
/*      */ import com.amazonaws.services.ec2.model.InstanceMonitoring;
/*      */ import com.amazonaws.services.ec2.model.InstanceState;
/*      */ import com.amazonaws.services.ec2.model.InstanceStateChange;
/*      */ import com.amazonaws.services.ec2.model.Monitoring;
/*      */ import com.manageengine.appmanager.amazon.comm.EC2Requests;
/*      */ import com.manageengine.appmanager.amazon.comm.ServicesRequests;
/*      */ import com.manageengine.appmanager.amazon.util.EC2InstanceAction;
/*      */ import com.manageengine.appmanager.amazon.util.EC2RequestsConstants;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.commons.codec.binary.Base64;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ 
/*      */ public final class EC2InstanceManageActions
/*      */   extends DispatchAction
/*      */ {
/*      */   public ActionForward manageInstances(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*   59 */     ActionMessages messages = new ActionMessages();
/*   60 */     ArrayList<String> ec2ResourceIdsList = new ArrayList();
/*   61 */     ArrayList<String> instanceDisplayName = new ArrayList();
/*   62 */     ResultSet rsForQuery = null;
/*   63 */     String toPerform = "";
/*   64 */     String setMessage = null;
/*   65 */     String ec2InstanceTypeId = "";
/*   66 */     if ((request.getParameter("resIds") != null) && (request.getParameter("action") != null) && (request.getParameter("resIds").trim() != "") && (request.getParameter("action").trim() != ""))
/*      */     {
/*      */ 
/*   69 */       String ec2ResourceIds = request.getParameter("resIds");
/*   70 */       StringTokenizer nameAfterComma = new StringTokenizer(ec2ResourceIds, ",");
/*   71 */       int count = nameAfterComma.countTokens();
/*   72 */       if (count >= 1)
/*      */       {
/*   74 */         while (nameAfterComma.hasMoreTokens())
/*      */         {
/*   76 */           ec2ResourceIdsList.add(nameAfterComma.nextToken());
/*      */         }
/*      */       }
/*   79 */       List<InstanceStateChange> instanceStateChangeList = null;
/*   80 */       List<InstanceMonitoring> instanceMonitoringList = null;
/*   81 */       ArrayList<String> tables = ConfMonitorUtil.getInstance().getCurrentTable("EC2Instance", null);
/*   82 */       ArrayList instanseStatusList = new ArrayList();
/*      */       
/*   84 */       int regionNum = 0; for (int i = 0; regionNum < EC2RequestsConstants.regions.length; i++)
/*      */       {
/*   86 */         String amazonResourceId = getParentId(ec2ResourceIds);
/*   87 */         AWSCredentials credentials = getCredentialsForResourceId(amazonResourceId);
/*   88 */         toPerform = request.getParameter("action");
/*   89 */         request.setAttribute("action", toPerform);
/*   90 */         String region = EC2RequestsConstants.regions[regionNum];
/*   91 */         AmazonEC2 service = ServicesRequests.getEc2Service(credentials);
/*   92 */         ArrayList<String> instancesInRegion = getInstancesInRegion(ec2ResourceIds, region);
/*      */         
/*   94 */         ec2InstanceTypeId = DBUtil.getBaseId("EC2Instance");
/*   95 */         String instanceName = "-";
/*   96 */         if (ec2ResourceIdsList.size() > i) {
/*   97 */           String id = (String)ec2ResourceIdsList.get(i);
/*   98 */           System.out.println("resid for ec2int : " + id);
/*   99 */           String nameQuery = "select CONFVALUE from AM_CONFIGURATION_INFO  where RESOURCEID='" + id + "'  and  ATTRIBUTEID = 9852 and LATEST=1";
/*      */           try {
/*  101 */             rsForQuery = AMConnectionPool.executeQueryStmt(nameQuery);
/*  102 */             if (rsForQuery.next())
/*      */             {
/*  104 */               instanceName = rsForQuery.getString("CONFVALUE");
/*      */             }
/*      */             
/*  107 */             instanceDisplayName.add(instanceName);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/*  116 */               rsForQuery.close();
/*      */             } catch (Exception exx) {
/*  118 */               exx.printStackTrace();
/*      */             }
/*      */             
/*      */ 
/*      */             try
/*      */             {
/*  124 */               if ((toPerform.equalsIgnoreCase("Start")) && (instancesInRegion.size() > 0))
/*      */               {
/*      */ 
/*  127 */                 instanceStateChangeList = EC2Requests.startInstances(service, region, instancesInRegion);
/*  128 */                 setMessage = FormatUtil.getString("am.webclient.amazon.ec2instances.startinstances.text", new String[] { String.valueOf(ec2ResourceIdsList.size()) });
/*  129 */                 setStateForInstancesInRegion(instanceStateChangeList);
/*      */               }
/*  131 */               else if ((toPerform.equalsIgnoreCase("Stop")) && (instancesInRegion.size() > 0))
/*      */               {
/*      */ 
/*  134 */                 instanceStateChangeList = EC2Requests.stopInstances(service, region, instancesInRegion);
/*  135 */                 setMessage = FormatUtil.getString("am.webclient.amazon.ec2instances.stopinstances.text", new String[] { String.valueOf(ec2ResourceIdsList.size()) });
/*  136 */                 setStateForInstancesInRegion(instanceStateChangeList);
/*      */               }
/*  138 */               else if ((toPerform.equalsIgnoreCase("Reboot")) && (instancesInRegion.size() > 0))
/*      */               {
/*      */ 
/*  141 */                 EC2Requests.rebootInstances(service, region, instancesInRegion);
/*  142 */                 setMessage = FormatUtil.getString("am.webclient.amazon.ec2instances.rebootinstances.text", new String[] { String.valueOf(ec2ResourceIdsList.size()) });
/*  143 */                 setStateForInstancesInRegion(instancesInRegion, toPerform, instanseStatusList);
/*  144 */                 if ((instanseStatusList != null) && (instanseStatusList.size() > 0))
/*      */                 {
/*  146 */                   request.setAttribute("ec2InstanceChangeList", instanseStatusList);
/*      */                 }
/*      */               }
/*  149 */               else if ((toPerform.equalsIgnoreCase("Enable")) && (instancesInRegion.size() > 0))
/*      */               {
/*      */ 
/*  152 */                 instanceMonitoringList = EC2Requests.enableMonitoring(service, region, instancesInRegion);
/*  153 */                 setMessage = FormatUtil.getString("am.webclient.amazon.cloudwatch.enabled.text", new String[] { String.valueOf(ec2ResourceIdsList.size()) });
/*  154 */                 setMonitoringStateForInstancesInRegion(instanceMonitoringList);
/*      */               }
/*  156 */               else if ((toPerform.equalsIgnoreCase("Disable")) && (instancesInRegion.size() > 0))
/*      */               {
/*      */ 
/*  159 */                 instanceMonitoringList = EC2Requests.disableMonitoring(service, region, instancesInRegion);
/*  160 */                 setMessage = FormatUtil.getString("am.webclient.amazon.cloudwatch.disabled.text", new String[] { String.valueOf(ec2ResourceIdsList.size()) });
/*  161 */                 setMonitoringStateForInstancesInRegion(instanceMonitoringList);
/*      */               }
/*      */             }
/*      */             catch (AmazonServiceException e)
/*      */             {
/*  166 */               e.printStackTrace();
/*  167 */               setMessage = e.getMessage();
/*      */             }
/*      */             catch (IllegalArgumentException e)
/*      */             {
/*  171 */               e.printStackTrace();
/*      */             }
/*      */             catch (AmazonClientException e)
/*      */             {
/*  175 */               e.printStackTrace();
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/*  179 */               e.printStackTrace();
/*      */             }
/*      */             catch (Throwable e)
/*      */             {
/*  183 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception err)
/*      */           {
/*  111 */             System.out.println("Unable to retrieve name for instance");
/*  112 */             err.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/*  116 */               rsForQuery.close();
/*      */             } catch (Exception exx) {
/*  118 */               exx.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*   84 */         regionNum++;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  186 */       if (ec2ResourceIdsList.size() > 0)
/*      */       {
/*  188 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(setMessage));
/*  189 */         saveMessages(request, messages);
/*  190 */         request.setAttribute("SUCCESS", "true");
/*  191 */         if (instanceStateChangeList != null) {
/*  192 */           Properties instanceProps = null;
/*  193 */           int lenghtforinst = 0;
/*  194 */           for (InstanceStateChange instance : instanceStateChangeList) {
/*      */             try {
/*  196 */               instanceProps = new Properties();
/*  197 */               instanceProps.setProperty("InstanceId", instance.getInstanceId());
/*  198 */               instanceProps.setProperty("InstanceName", (String)instanceDisplayName.get(lenghtforinst++));
/*  199 */               if (instance.getPreviousState().getName().equalsIgnoreCase(instance.getCurrentState().getName())) {
/*  200 */                 instanceProps.setProperty("Status", FormatUtil.getString("am.amazon.ec2instanceaction.alreadymessage.text") + " " + instance.getCurrentState().getName());
/*      */               }
/*      */               else {
/*  203 */                 instanceProps.setProperty("Status", instance.getCurrentState().getName());
/*      */               }
/*  205 */               instanseStatusList.add(instanceProps);
/*      */             }
/*      */             catch (Exception e) {
/*  208 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           
/*  212 */           request.setAttribute("ec2InstanceChangeList", instanseStatusList);
/*      */         }
/*      */         else {
/*  215 */           request.setAttribute("toPerform", toPerform);
/*      */         }
/*  217 */         request.setAttribute("displayNameforInstance", instanceDisplayName);
/*  218 */         return mapping.findForward("success");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  223 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.amazon.ec2instances.startstopreboot.default.errtext")));
/*  224 */       saveMessages(request, messages);
/*  225 */       request.setAttribute("SUCCESS", "false");
/*  226 */       return mapping.findForward("failure");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  232 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.amazon.ec2instances.startstopreboot.default.errtext")));
/*  233 */     saveMessages(request, messages);
/*  234 */     request.setAttribute("SUCCESS", "false");
/*  235 */     return mapping.findForward("failure");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward filterStartStopInstances(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  245 */     ActionMessages messages = new ActionMessages();
/*  246 */     ArrayList<String> resourceIdsListToManage = new ArrayList();
/*  247 */     ArrayList<String> instanceDisplayName = new ArrayList();
/*  248 */     String setMessage = "";
/*  249 */     String unableToPerform = "";
/*  250 */     String nonEBSInstances = "";
/*      */     
/*  252 */     if ((request.getParameter("rowIds") != null) && (request.getParameter("action") != null) && (request.getParameter("rowIds").trim() != "") && (request.getParameter("action").trim() != ""))
/*      */     {
/*      */ 
/*      */ 
/*  256 */       String ec2ResourceIds = request.getParameter("rowIds");
/*  257 */       String action = "";
/*  258 */       String actionStateToDisplay = "";
/*  259 */       if (request.getParameter("action").equalsIgnoreCase("StartInstances"))
/*      */       {
/*  261 */         action = "Start";
/*  262 */         actionStateToDisplay = FormatUtil.getString("am.vm.action.starting");
/*      */       }
/*  264 */       else if (request.getParameter("action").equalsIgnoreCase("StopInstances"))
/*      */       {
/*  266 */         action = "Stop";
/*  267 */         actionStateToDisplay = FormatUtil.getString("am.vm.action.stopping");
/*      */       }
/*  269 */       else if (request.getParameter("action").equalsIgnoreCase("RebootInstances"))
/*      */       {
/*      */ 
/*  272 */         action = "Reboot";
/*  273 */         actionStateToDisplay = FormatUtil.getString("am.vm.action.restarting");
/*      */       }
/*      */       
/*  276 */       String query = "select RESOURCEID, DISPLAYNAME, TYPE from AM_ManagedObject where RESOURCEID in (" + ec2ResourceIds + ")";
/*      */       
/*  278 */       Hashtable<String, String> name_ids = new Hashtable();
/*  279 */       ResultSet results = null;
/*  280 */       ResultSet rsForTypeId = null;
/*  281 */       ResultSet rsForQuery = null;
/*  282 */       String toManage = "";
/*  283 */       String ec2InstanceTypeId = "";
/*  284 */       String resourceType = "";
/*      */       
/*      */       try
/*      */       {
/*  288 */         results = AMConnectionPool.executeQueryStmt(query);
/*  289 */         ec2InstanceTypeId = DBUtil.getBaseId("EC2Instance");
/*  290 */         while (results.next())
/*      */         {
/*  292 */           String dispname = results.getString("DISPLAYNAME");
/*  293 */           String id = results.getString("RESOURCEID");
/*  294 */           resourceType = results.getString("TYPE");
/*  295 */           String state = "";
/*  296 */           if ((resourceType.equalsIgnoreCase("VirtualMachine")) || (resourceType.equalsIgnoreCase("XenServerVM")) || (resourceType.equalsIgnoreCase("Docker Container")))
/*      */           {
/*  298 */             if (toManage.length() > 0)
/*      */             {
/*  300 */               toManage = toManage + "," + id;
/*      */             }
/*      */             else
/*      */             {
/*  304 */               toManage = toManage + id;
/*      */             }
/*  306 */             resourceIdsListToManage.add(id);
/*  307 */             name_ids.put(id, dispname);
/*      */           }
/*  309 */           else if (resourceType.equalsIgnoreCase("EC2Instance"))
/*      */           {
/*  311 */             String instanceName = "-";
/*  312 */             if (toManage.length() > 0)
/*      */             {
/*  314 */               toManage = toManage + "," + id;
/*      */             }
/*      */             else {
/*  317 */               toManage = toManage + id;
/*      */             }
/*  319 */             String nameQuery = "select CONFVALUE from AM_CONFIGURATION_INFO  where RESOURCEID='" + id + "'  and  ATTRIBUTEID = 9852 and LATEST=1";
/*  320 */             rsForQuery = AMConnectionPool.executeQueryStmt(nameQuery);
/*  321 */             if (rsForQuery.next())
/*      */             {
/*  323 */               instanceName = rsForQuery.getString("CONFVALUE");
/*      */             }
/*  325 */             instanceDisplayName.add(instanceName);
/*  326 */             resourceIdsListToManage.add(id);
/*  327 */             name_ids.put(id, dispname);
/*      */ 
/*      */ 
/*      */           }
/*  331 */           else if (nonEBSInstances.trim().length() > 0)
/*      */           {
/*  333 */             nonEBSInstances = nonEBSInstances + ", <b>" + dispname + "</b>";
/*      */           }
/*      */           else
/*      */           {
/*  337 */             nonEBSInstances = "&nbsp;&nbsp;<b>" + dispname + "</b>";
/*      */           }
/*      */         }
/*      */         
/*  341 */         request.setAttribute("instanceDisplayName", instanceDisplayName);
/*  342 */         request.setAttribute("resourceIdsToManage", toManage);
/*  343 */         request.setAttribute("nameids", name_ids);
/*  344 */         action = (action.equals("Reboot")) && (resourceType.equalsIgnoreCase("Docker Container")) ? "Restart" : action;
/*  345 */         request.setAttribute("action", action);
/*      */         
/*  347 */         if ((resourceType.equalsIgnoreCase("VirtualMachine")) || (resourceType.equalsIgnoreCase("XenServerVM")) || (resourceType.equalsIgnoreCase("Docker Container")))
/*      */         {
/*  349 */           request.setAttribute("resourceType", resourceType);
/*      */         }
/*  351 */         if (resourceType.equalsIgnoreCase("Docker Container")) {
/*  352 */           request.setAttribute("title", FormatUtil.getString("am.docker.container.action.title", new String[] { FormatUtil.getString(action), FormatUtil.getString(resourceType) }));
/*  353 */           request.setAttribute("selectedText", FormatUtil.getString("am.docker.container.action.selectedText", new String[] { FormatUtil.getString(action), FormatUtil.getString(resourceType) }));
/*  354 */           request.setAttribute("confirmText", FormatUtil.getString("am.docker.container.action.confirmText", new String[] { FormatUtil.getString(action) }));
/*  355 */           request.setAttribute("parentId", request.getParameter("parentId") != null ? request.getParameter("parentId") : "");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/*  366 */           if (results != null)
/*      */           {
/*  368 */             results.close();
/*      */           }
/*  370 */           if (rsForTypeId != null)
/*      */           {
/*  372 */             rsForTypeId.close();
/*      */           }
/*  374 */           rsForQuery.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*  378 */         if (nonEBSInstances.trim().length() <= 0) {
/*      */           break label1127;
/*      */         }
/*      */       }
/*      */       catch (Exception exception)
/*      */       {
/*  360 */         exception.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/*  366 */           if (results != null)
/*      */           {
/*  368 */             results.close();
/*      */           }
/*  370 */           if (rsForTypeId != null)
/*      */           {
/*  372 */             rsForTypeId.close();
/*      */           }
/*  374 */           rsForQuery.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */       }
/*      */       
/*      */ 
/*  380 */       nonEBSInstances = FormatUtil.getString("am.amazon.ec2instances.nonebsvolumes.text", new String[] { String.valueOf(nonEBSInstances) });
/*  381 */       setMessage = FormatUtil.getString("am.amazon.ec2instances.unableto" + action.toLowerCase() + "reason.text", new String[] { String.valueOf(nonEBSInstances) });
/*  382 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(setMessage));
/*  383 */       saveMessages(request, messages);
/*      */       label1127:
/*  385 */       if (resourceIdsListToManage.size() > 0)
/*      */       {
/*  387 */         request.setAttribute("SUCCESS", "true");
/*  388 */         request.setAttribute("actionStateToDisplay", actionStateToDisplay);
/*  389 */         String returnPath = "/jsp/amazon/ManageEC2Instances.jsp?resIds=" + toManage;
/*  390 */         return new ActionForward(returnPath);
/*      */       }
/*      */       
/*      */ 
/*  394 */       setMessage = "<br>Unable to perform " + action + " Instances Operation";
/*  395 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(setMessage));
/*  396 */       saveMessages(request, messages);
/*      */       
/*  398 */       request.setAttribute("SUCCESS", "false");
/*  399 */       return mapping.findForward("failure");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  406 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.amazon.ec2instances.startstopreboot.default.errtext")));
/*  407 */     saveMessages(request, messages);
/*  408 */     request.setAttribute("SUCCESS", "false");
/*  409 */     return mapping.findForward("failure");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward filterInstancesForCloudwatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  419 */     ActionMessages messages = new ActionMessages();
/*  420 */     ArrayList<String> resourceIdsListToManage = new ArrayList();
/*  421 */     String setMessage = "";
/*  422 */     String unableToPerform = "";
/*  423 */     if ((request.getParameter("rowIds") != null) && (request.getParameter("action") != null) && (request.getParameter("rowIds").trim() != "") && (request.getParameter("action").trim() != ""))
/*      */     {
/*      */ 
/*  426 */       String ec2ResourceIds = request.getParameter("rowIds");
/*  427 */       String action = "";
/*  428 */       String actionState = "";
/*  429 */       if (request.getParameter("action").equalsIgnoreCase("EnableCloudWatch"))
/*      */       {
/*  431 */         action = "Enable";
/*  432 */         actionState = "disabled";
/*      */       }
/*  434 */       else if (request.getParameter("action").equalsIgnoreCase("DisableCloudWatch"))
/*      */       {
/*  436 */         action = "Disable";
/*  437 */         actionState = "enabled";
/*      */       }
/*  439 */       String query = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject where RESOURCEID in (" + ec2ResourceIds + ")";
/*      */       
/*  441 */       Hashtable<String, String> name_ids = new Hashtable();
/*  442 */       ResultSet results = null;
/*  443 */       ResultSet rsForTypeId = null;
/*  444 */       ResultSet rsForQuery = null;
/*  445 */       String toManage = "";
/*  446 */       String ec2InstanceTypeId = "";
/*      */       try
/*      */       {
/*  449 */         results = AMConnectionPool.executeQueryStmt(query);
/*  450 */         ec2InstanceTypeId = DBUtil.getBaseId("EC2Instance");
/*  451 */         while (results.next())
/*      */         {
/*  453 */           String dispname = results.getString("DISPLAYNAME");
/*  454 */           String id = results.getString("RESOURCEID");
/*      */           
/*      */ 
/*  457 */           String monitoringState = "";
/*  458 */           String stateQuery = "select CONFVALUE from AM_CONFIGURATION_INFO  where RESOURCEID='" + id + "'  and  ATTRIBUTEID = 9829 and LATEST=1";
/*  459 */           rsForQuery = AMConnectionPool.executeQueryStmt(stateQuery);
/*  460 */           if (rsForQuery.next())
/*      */           {
/*  462 */             monitoringState = rsForQuery.getString("CONFVALUE");
/*      */           }
/*      */           
/*  465 */           if (((monitoringState.equalsIgnoreCase("enabled")) && (action.equalsIgnoreCase("Disable"))) || ((monitoringState.equalsIgnoreCase("disabled")) && (action.equalsIgnoreCase("Enable"))))
/*      */           {
/*      */ 
/*  468 */             if (toManage.length() > 0)
/*      */             {
/*  470 */               toManage = toManage + "," + id;
/*      */             }
/*      */             else
/*      */             {
/*  474 */               toManage = toManage + id;
/*      */             }
/*  476 */             resourceIdsListToManage.add(id);
/*  477 */             name_ids.put(id, dispname);
/*      */ 
/*      */ 
/*      */           }
/*  481 */           else if (unableToPerform.trim().length() > 0)
/*      */           {
/*  483 */             unableToPerform = unableToPerform + ", <b>" + dispname + "</b>";
/*      */           }
/*      */           else
/*      */           {
/*  487 */             unableToPerform = "&nbsp;&nbsp;<b>" + dispname + "</b>";
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  494 */         request.setAttribute("resourceIdsToManage", toManage);
/*  495 */         request.setAttribute("nameids", name_ids);
/*  496 */         request.setAttribute("action", action);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/*  506 */           if (results != null)
/*      */           {
/*  508 */             results.close();
/*      */           }
/*  510 */           if (rsForTypeId != null)
/*      */           {
/*  512 */             rsForTypeId.close();
/*      */           }
/*  514 */           rsForQuery.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*  519 */         if (unableToPerform.trim().length() > 0) {
/*      */           break label679;
/*      */         }
/*      */       }
/*      */       catch (Exception exception)
/*      */       {
/*  500 */         exception.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/*  506 */           if (results != null)
/*      */           {
/*  508 */             results.close();
/*      */           }
/*  510 */           if (rsForTypeId != null)
/*      */           {
/*  512 */             rsForTypeId.close();
/*      */           }
/*  514 */           rsForQuery.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */       }
/*      */       
/*  519 */       if (resourceIdsListToManage.size() <= 0) {
/*      */         label679:
/*  521 */         if (unableToPerform.trim().length() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  525 */           setMessage = FormatUtil.getString("am.amazon.ec2instances." + action.toLowerCase() + "cloudwatch.unable.text", new String[] { String.valueOf(unableToPerform) });
/*      */         }
/*  527 */         if (resourceIdsListToManage.size() <= 0)
/*      */         {
/*  529 */           setMessage = FormatUtil.getString(new StringBuilder().append("am.amazon.ec2instances.").append(action.toLowerCase()).append("cloudwatch.selectone.text").toString()) + setMessage;
/*      */         }
/*  531 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(setMessage));
/*  532 */         saveMessages(request, messages);
/*      */       }
/*      */       
/*  535 */       if (resourceIdsListToManage.size() > 0)
/*      */       {
/*  537 */         request.setAttribute("SUCCESS", "true");
/*      */         
/*  539 */         String returnPath = "/jsp/amazon/ManageEC2Instances.jsp?resIds=" + toManage;
/*      */         
/*  541 */         return new ActionForward(returnPath);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  546 */       request.setAttribute("SUCCESS", "false");
/*  547 */       return mapping.findForward("failure");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  554 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.amazon.ec2instances.cloudwatch.default.errtext")));
/*  555 */     saveMessages(request, messages);
/*  556 */     request.setAttribute("SUCCESS", "false");
/*  557 */     return mapping.findForward("failure");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getSystemLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  566 */     ActionMessages messages = new ActionMessages();
/*  567 */     ArrayList<String> ec2ResourceIdsList = new ArrayList();
/*  568 */     if ((request.getParameter("rowIds") != null) && (request.getParameter("rowIds").trim() != ""))
/*      */     {
/*  570 */       String ec2ResourceIds = request.getParameter("rowIds");
/*  571 */       StringTokenizer nameAfterComma = new StringTokenizer(ec2ResourceIds, ",");
/*  572 */       int count = nameAfterComma.countTokens();
/*  573 */       if (count >= 1)
/*      */       {
/*  575 */         while (nameAfterComma.hasMoreTokens())
/*      */         {
/*  577 */           ec2ResourceIdsList.add(nameAfterComma.nextToken());
/*      */         }
/*      */       }
/*  580 */       String instanceResourceID = (String)ec2ResourceIdsList.get(0);
/*  581 */       String regionAndInstanceID = getRegionAndInstanceId(instanceResourceID);
/*  582 */       String[] regionAndInstanceIDValues = regionAndInstanceID.split("\\|");
/*  583 */       String regionOfInstance = regionAndInstanceIDValues[0].trim();
/*  584 */       String instanceId = regionAndInstanceIDValues[1].trim();
/*  585 */       String amazonResourceId = getParentId(ec2ResourceIds);
/*  586 */       AWSCredentials credentials = getCredentialsForResourceId(amazonResourceId);
/*  587 */       request.setAttribute("action", "System Log");
/*  588 */       AmazonEC2 service = ServicesRequests.getEc2Service(credentials);
/*      */       try
/*      */       {
/*  591 */         GetConsoleOutputResult getConsoleOutputResult = EC2Requests.getConsoleOutput(service, regionOfInstance, instanceId);
/*  592 */         Hashtable<String, String> systemLog = new Hashtable();
/*  593 */         systemLog.put("Instance ID", getConsoleOutputResult.getInstanceId());
/*  594 */         systemLog.put("Time Stamp", getConsoleOutputResult.getTimestamp().toString());
/*  595 */         systemLog.put("Output", new String(Base64.decodeBase64(getConsoleOutputResult.getOutput().getBytes())));
/*      */         
/*  597 */         request.setAttribute("systemLog", systemLog);
/*      */       }
/*      */       catch (AmazonServiceException e)
/*      */       {
/*  601 */         e.printStackTrace();
/*      */       }
/*      */       catch (IllegalArgumentException e)
/*      */       {
/*  605 */         e.printStackTrace();
/*      */       }
/*      */       catch (AmazonClientException e)
/*      */       {
/*  609 */         e.printStackTrace();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  613 */         e.printStackTrace();
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*  617 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*  621 */       if (ec2ResourceIdsList.size() > 0)
/*      */       {
/*  623 */         request.setAttribute("SUCCESS", "true");
/*  624 */         return mapping.findForward("success");
/*      */       }
/*      */       
/*      */ 
/*  628 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("<br>Unable to get System Log Information."));
/*  629 */       saveMessages(request, messages);
/*  630 */       request.setAttribute("SUCCESS", "false");
/*  631 */       return mapping.findForward("failure");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  636 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("<br>Unable to get System Log Information."));
/*  637 */     saveMessages(request, messages);
/*  638 */     request.setAttribute("SUCCESS", "false");
/*  639 */     return mapping.findForward("failure");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getParentId(String instanceResourceIDs)
/*      */   {
/*  648 */     ResultSet rs = null;
/*  649 */     String amazonResourceIdQuery = "select min(AM_PARENTCHILDMAPPER.PARENTID) as ParentId from AM_PARENTCHILDMAPPER left join AM_ManagedObject on AM_PARENTCHILDMAPPER.PARENTID=AM_ManagedObject.RESOURCEID where AM_PARENTCHILDMAPPER.CHILDID in (" + instanceResourceIDs + ") and AM_ManagedObject.Type in ('Amazon','EC2Instance','RDSInstance')";
/*      */     
/*  651 */     String amazonResourceId = "";
/*      */     try
/*      */     {
/*  654 */       rs = AMConnectionPool.executeQueryStmt(amazonResourceIdQuery);
/*  655 */       if (rs.next())
/*      */       {
/*  657 */         amazonResourceId = rs.getString("PARENTID");
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  662 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  666 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  668 */     return amazonResourceId;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private AWSCredentials getCredentialsForResourceId(String amazonResourceId)
/*      */   {
/*  676 */     ResultSet rs = null;
/*  677 */     String amazonTypeId = DBUtil.getBaseId("Amazon");
/*  678 */     String keysQuery = "select *," + DBQueryUtil.decode("PASSWORD") + " as secretKey from AM_ARGS_" + amazonTypeId + " where resourceId = '" + amazonResourceId + "'";
/*  679 */     if (DBQueryUtil.isPgsql()) {
/*  680 */       keysQuery = "select *," + DBQueryUtil.decode("\"Password\"") + " as secretKey from AM_ARGS_" + amazonTypeId + " where resourceId = '" + amazonResourceId + "'";
/*      */     }
/*  682 */     String accessKey = "";
/*  683 */     String secretKey = "";
/*      */     try
/*      */     {
/*  686 */       rs = AMConnectionPool.executeQueryStmt(keysQuery);
/*  687 */       if (rs.next())
/*      */       {
/*  689 */         accessKey = rs.getString("accessKey");
/*  690 */         secretKey = rs.getString("secretKey");
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  695 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  699 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  701 */     return new BasicAWSCredentials(accessKey, secretKey);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private ArrayList<String> getInstancesInRegion(String ids, String region)
/*      */   {
/*  709 */     ArrayList<String> instancesInRegion = new ArrayList();
/*  710 */     String ec2InstanceTypeId = DBUtil.getBaseId("EC2Instance");
/*  711 */     String argsTable = "AM_ARGS_" + ec2InstanceTypeId;
/*  712 */     String query = "select " + argsTable + ".RESOURCEID," + argsTable + ".INSTANCEID from " + argsTable + " left join AM_ManagedObject on  " + argsTable + ".RESOURCEID=AM_ManagedObject.RESOURCEID where REGION='" + region + "' and " + argsTable + ".RESOURCEID in (" + ids + ")";
/*  713 */     if (DBQueryUtil.isPgsql())
/*      */     {
/*  715 */       query = "select " + argsTable + ".RESOURCEID," + argsTable + ".\"INSTANCEID\" from " + argsTable + " left join AM_ManagedObject on  " + argsTable + ".RESOURCEID=AM_ManagedObject.RESOURCEID where \"REGION\"='" + region + "' and " + argsTable + ".RESOURCEID in (" + ids + ")";
/*      */     }
/*      */     
/*  718 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  721 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  722 */       while (rs.next())
/*      */       {
/*  724 */         instancesInRegion.add(rs.getString("INSTANCEID"));
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  729 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  733 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  735 */     return instancesInRegion;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setStateForInstancesInRegion(List<InstanceStateChange> instanceStateChangeList)
/*      */   {
/*  743 */     String ec2InstanceTypeId = DBUtil.getBaseId("EC2Instance");
/*  744 */     ResultSet rsForCollectionTime = null;
/*      */     try
/*      */     {
/*  747 */       tables = ConfMonitorUtil.getInstance().getCurrentTable("EC2Instance", null);
/*  748 */       String colName = ConfMonitorConfiguration.getInstance().getAttributeName("EC2Instance", "9814");
/*  749 */       for (InstanceStateChange instanceStateChange : instanceStateChangeList)
/*      */       {
/*  751 */         String collectionTimeQuery = "select max(COLLECTIONTIME) as COLLECTIONTIME,AM_ManagedObject.ResourceID as RESOURCEID from " + (String)tables.get(0) + " left join AM_ManagedObject on AM_ManagedObject.RESOURCEID=" + (String)tables.get(0) + ".RESOURCEID where '" + AppManagerUtil.getResIdConditionForEC2Instance(ec2InstanceTypeId, instanceStateChange.getInstanceId()) + "' group by AM_ManagedObject.RESOURCEID";
/*  752 */         rsForCollectionTime = AMConnectionPool.executeQueryStmt(collectionTimeQuery);
/*  753 */         String collectionTime = "";
/*  754 */         String resourceId = "";
/*  755 */         while (rsForCollectionTime.next())
/*      */         {
/*  757 */           collectionTime = rsForCollectionTime.getString("COLLECTIONTIME");
/*  758 */           resourceId = rsForCollectionTime.getString("RESOURCEID");
/*      */         }
/*  760 */         updateConfigurationInfo(resourceId, "9814", instanceStateChange.getCurrentState().getName(), collectionTime);
/*      */       }
/*      */     }
/*      */     catch (SQLException e) {
/*      */       ArrayList<String> tables;
/*  765 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  769 */       AMConnectionPool.closeStatement(rsForCollectionTime);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setStateForInstancesInRegion(List<String> instanceList, String state, ArrayList instanseStatusList)
/*      */   {
/*  779 */     String ec2InstanceTypeId = DBUtil.getBaseId("EC2Instance");
/*  780 */     ResultSet rsForCollectionTime = null;
/*      */     try
/*      */     {
/*  783 */       tables = ConfMonitorUtil.getInstance().getCurrentTable("EC2Instance", null);
/*  784 */       for (String instance : instanceList)
/*      */       {
/*  786 */         String collectionTimeQuery = "select max(COLLECTIONTIME) as COLLECTIONTIME,AM_ManagedObject.RESOURCEID as RESOURCEID from " + (String)tables.get(0) + " left join AM_ManagedObject on AM_ManagedObject.RESOURCEID=" + (String)tables.get(0) + ".RESOURCEID where " + AppManagerUtil.getResIdConditionForEC2Instance(ec2InstanceTypeId, instance) + " group by AM_ManagedObject.RESOURCEID";
/*  787 */         AMLog.debug("collectionTimeQuery in overloaded setStateForInstancesInRegion is : " + collectionTimeQuery);
/*  788 */         rsForCollectionTime = AMConnectionPool.executeQueryStmt(collectionTimeQuery);
/*  789 */         String collectionTime = "";
/*  790 */         String resourceId = "";
/*  791 */         Properties instanceProps = null;
/*  792 */         while (rsForCollectionTime.next())
/*      */         {
/*  794 */           collectionTime = rsForCollectionTime.getString("COLLECTIONTIME");
/*  795 */           resourceId = rsForCollectionTime.getString("RESOURCEID");
/*  796 */           instanceProps = new Properties();
/*  797 */           instanceProps.setProperty("InstanceId", rsForCollectionTime.getString("RESOURCENAME"));
/*  798 */           instanceProps.setProperty("InstanceName", rsForCollectionTime.getString("DISPLAYNAME"));
/*  799 */           instanceProps.setProperty("Status", "Reboot Triggered");
/*  800 */           instanseStatusList.add(instanceProps);
/*      */         }
/*  802 */         updateConfigurationInfo(resourceId, "9814", state, collectionTime);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       ArrayList<String> tables;
/*  808 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  812 */       AMConnectionPool.closeStatement(rsForCollectionTime);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setMonitoringStateForInstancesInRegion(List<InstanceMonitoring> instanceMonitoringList)
/*      */   {
/*  822 */     String ec2InstanceTypeId = DBUtil.getBaseId("EC2Instance");
/*  823 */     ResultSet rsForCollectionTime = null;
/*      */     try
/*      */     {
/*  826 */       tables = ConfMonitorUtil.getInstance().getCurrentTable("EC2Instance", null);
/*  827 */       for (InstanceMonitoring instanceMonitoring : instanceMonitoringList)
/*      */       {
/*  829 */         String collectionTimeQuery = "select max(COLLECTIONTIME) as COLLECTIONTIME,AM_ManagedObject.ResourceID as RESOURCEID from " + (String)tables.get(0) + " left join AM_ManagedObject on AM_ManagedObject.RESOURCEID=" + (String)tables.get(0) + ".RESOURCEID where " + AppManagerUtil.getResIdConditionForEC2Instance(ec2InstanceTypeId, instanceMonitoring.getInstanceId()) + " group by AM_ManagedObject.ResourceID";
/*  830 */         AMLog.debug("EC2InstanceManageActions.setMonitoringStateForInstancesInRegion() collectionTimeQuery is " + collectionTimeQuery);
/*  831 */         rsForCollectionTime = AMConnectionPool.executeQueryStmt(collectionTimeQuery);
/*  832 */         String collectionTime = "";
/*  833 */         String resourceId = "";
/*  834 */         while (rsForCollectionTime.next())
/*      */         {
/*  836 */           collectionTime = rsForCollectionTime.getString("COLLECTIONTIME");
/*  837 */           resourceId = rsForCollectionTime.getString("RESOURCEID");
/*      */         }
/*  839 */         updateConfigurationInfo(resourceId, "9829", instanceMonitoring.getMonitoring().getState(), collectionTime);
/*      */       }
/*      */     }
/*      */     catch (SQLException e) {
/*      */       ArrayList<String> tables;
/*  844 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  848 */       AMConnectionPool.closeStatement(rsForCollectionTime);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getRegionAndInstanceId(String instanceResourceID)
/*      */   {
/*  857 */     HashMap<String, String> instanceRegionMap = new HashMap();
/*  858 */     AppManagerUtil.getInstanceRegionMap(instanceResourceID, DBUtil.getBaseId("EC2Instance"), instanceRegionMap);
/*  859 */     return (String)instanceRegionMap.get("REGION") + "|" + (String)instanceRegionMap.get("INSTANCEID");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getMaxCollectionTimeForAttributeId(String attributeID, String resourceID)
/*      */   {
/*  867 */     String collectionTime = "";
/*  868 */     ResultSet rsForQuery = null;
/*  869 */     String ec2InstanceTypeId = DBUtil.getBaseId("EC2Instance");
/*  870 */     ArrayList<String> tables = ConfMonitorUtil.getInstance().getCurrentTable("EC2Instance", null);
/*  871 */     String collectionTimeQuery = "select max(COLLECTIONTIME) as COLLECTIONTIME from " + (String)tables.get(0) + " where RESOURCEID = " + resourceID;
/*      */     try
/*      */     {
/*  874 */       rsForQuery = AMConnectionPool.executeQueryStmt(collectionTimeQuery);
/*  875 */       if (rsForQuery.next())
/*      */       {
/*  877 */         collectionTime = rsForQuery.getString("COLLECTIONTIME");
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  882 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  886 */       AMConnectionPool.closeStatement(rsForQuery);
/*      */     }
/*  888 */     return collectionTime;
/*      */   }
/*      */   
/*  891 */   public ActionForward testamazonactions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) { String actionID = request.getParameter("actionID");
/*  892 */     String targetID = null;
/*  893 */     ArrayList ec2InstList = null;
/*  894 */     String type = null;
/*  895 */     ResultSet res = null;
/*  896 */     String query = "select AM_ACTIONPROFILE.NAME,AM_JREACTIONS.EMAIL_ACTION_ID,AM_JREACTIONS.TYPE,AM_JREACTIONS.TARGET_RESID,AM_ACTIONPROFILE.TYPE from AM_JREACTIONS,AM_ACTIONPROFILE where AM_JREACTIONS.ID=AM_ACTIONPROFILE.ID  and AM_JREACTIONS.ID=" + actionID;
/*      */     try {
/*  898 */       res = AMConnectionPool.executeQueryStmt(query);
/*  899 */       while (res.next()) {
/*  900 */         targetID = res.getString(4);
/*  901 */         ec2InstList = EC2InstanceAction.getInstanceResourceId(targetID);
/*  902 */         type = res.getString(5);
/*      */       }
/*      */     } catch (Exception err) {
/*  905 */       err.printStackTrace();
/*      */     } finally {
/*  907 */       AMConnectionPool.closeStatement(res);
/*      */     }
/*  909 */     String ec2ResourceTypeId = EC2InstanceAction.getTypeIDForResource("EC2Instance");
/*  910 */     ResultSet rst = null;
/*  911 */     ArrayList<Properties> ec2InstanceList = new ArrayList();
/*  912 */     ArrayList<String> tables = ConfMonitorUtil.getInstance().getCurrentTable("EC2Instance", null);
/*  913 */     if (!ec2InstList.isEmpty()) {
/*  914 */       Iterator<String> it = ec2InstList.iterator();
/*  915 */       while (it.hasNext()) {
/*  916 */         Properties listForEC2Inst = new Properties();
/*  917 */         int resID = Integer.parseInt((String)it.next());
/*      */         
/*  919 */         String queryForEC2Inst = "Select  RESOURCENAME,CONFVALUE from AM_ManagedObject,AM_CONFIGURATION_INFO where  AM_ManagedObject.RESOURCEID=AM_CONFIGURATION_INFO.RESOURCEID and AM_ManagedObject.RESOURCEID=" + resID + " and LATEST=1 and  ATTRIBUTEID = 9852";
/*      */         try {
/*  921 */           rst = AMConnectionPool.executeQueryStmt(queryForEC2Inst);
/*  922 */           while (rst.next()) {
/*  923 */             AMLog.debug("value for amazonaction : " + rst.getString(1));
/*  924 */             AMLog.debug("value for amazonaction : " + rst.getString(2));
/*  925 */             String label = rst.getString(1) + "(" + rst.getString(2) + ")";
/*  926 */             listForEC2Inst.setProperty("label", label);
/*  927 */             listForEC2Inst.setProperty("value", String.valueOf(resID));
/*  928 */             ec2InstanceList.add(listForEC2Inst);
/*      */           }
/*      */         } catch (Exception epp) {
/*  931 */           epp.printStackTrace();
/*      */         } finally {
/*  933 */           AMConnectionPool.closeStatement(rst);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  938 */     ((AMActionForm)form).setEc2Instance(ec2InstanceList);
/*  939 */     request.setAttribute("ec2InstList", ec2InstanceList);
/*  940 */     request.setAttribute("type", type);
/*  941 */     return new ActionForward("/jsp/TestAmazonAction.jsp?haid=" + request.getParameter("haid") + "&actionID=" + actionID);
/*      */   }
/*      */   
/*      */   public ActionForward triggerAmazonAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  946 */     ActionMessages messages = new ActionMessages();
/*  947 */     String actionID = request.getParameter("actionID");
/*  948 */     String update = request.getParameter("update");
/*      */     
/*  950 */     Hashtable<String, String> amazonMailInfo = getAmazonActionResp(actionID, update);
/*  951 */     String message = (String)amazonMailInfo.remove("actionMsg");
/*  952 */     String actionName = (String)amazonMailInfo.remove("actionName");
/*  953 */     boolean success = message != null;
/*  954 */     if (success)
/*      */     {
/*  956 */       messages.add("success", new ActionMessage(message, actionName));
/*  957 */       saveMessages(request, messages);
/*      */     }
/*      */     else
/*      */     {
/*  961 */       messages.add("failure", new ActionMessage(message, actionName));
/*  962 */       saveMessages(request, messages);
/*      */     }
/*  964 */     if (request.getParameter("remote") != null)
/*      */     {
/*  966 */       AdminTools.sendResponse(actionID, success, message, response);
/*  967 */       return null;
/*      */     }
/*  969 */     return new ActionForward("/manageEC2Instances.do?method=testamazonactions&haid=" + request.getParameter("haid") + "&actionID=" + actionID);
/*      */   }
/*      */   
/*      */   public static Hashtable<String, String> getAmazonActionResp(String actionID)
/*      */   {
/*  974 */     return getAmazonActionResp(actionID, "false");
/*      */   }
/*      */   
/*      */   public static Hashtable<String, String> getAmazonActionResp(String actionID, String update)
/*      */   {
/*  979 */     String filePath = "";
/*  980 */     String instAction = null;
/*  981 */     String targetId = null;
/*  982 */     String actionName = null;
/*  983 */     String actionType = null;
/*  984 */     String sux = null;
/*  985 */     String subject = "";
/*  986 */     Hashtable<String, String> amazonMailInfo = new Hashtable();
/*      */     try
/*      */     {
/*  989 */       filePath = FormatUtil.getContentsAsString("./conf/amazonaction.html");
/*      */     }
/*      */     catch (IOException io)
/*      */     {
/*  993 */       io.printStackTrace();
/*      */     }
/*  995 */     String topheading = FormatUtil.getString("am.webclient.managermail.testing.heading.text");
/*  996 */     String query = "select AM_ACTIONPROFILE.NAME,AM_JREACTIONS.EMAIL_ACTION_ID,AM_JREACTIONS.TYPE,AM_JREACTIONS.TARGET_RESID,AM_ACTIONPROFILE.TYPE from AM_JREACTIONS,AM_ACTIONPROFILE where AM_JREACTIONS.ID=AM_ACTIONPROFILE.ID  and AM_JREACTIONS.ID=" + actionID;
/*  997 */     ResultSet res = null;
/*      */     try
/*      */     {
/* 1000 */       res = AMConnectionPool.executeQueryStmt(query);
/* 1001 */       if (res.next())
/*      */       {
/* 1003 */         instAction = res.getString(1);
/* 1004 */         String type = res.getString(5);
/* 1005 */         targetId = res.getString(4);
/* 1006 */         if ((type != null) && (type.equals("14")))
/*      */         {
/* 1008 */           actionName = "Start";
/* 1009 */           actionType = FormatUtil.getString("am.ec2instanceaction.startaction.text");
/*      */         }
/* 1011 */         else if ((type != null) && (type.equals("15")))
/*      */         {
/* 1013 */           actionName = "Stop";
/* 1014 */           actionType = FormatUtil.getString("am.ec2instanceaction.stopaction.text");
/*      */         }
/*      */         else
/*      */         {
/* 1018 */           actionName = "Restart";
/* 1019 */           actionType = FormatUtil.getString("am.ec2instanceaction.restartaction.text");
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1026 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1030 */       AMConnectionPool.closeStatement(res);
/*      */     }
/*      */     try
/*      */     {
/* 1034 */       sux = "<table width='40%' border='0' cellpadding='5' cellspacing='0'><tr bgcolor='#e0e0e0'><td width='40%'>" + FormatUtil.getString("am.executeec2actiondetails.text") + "</td></tr></table>";
/* 1035 */       sux = sux + "<table width='40%' border='0' cellpadding='5' cellspacing='0'><tr><td width='20%' bgcolor='#AAAAAA' align='right'>" + FormatUtil.getString("am.ec2instanceaction.actionname.text") + "</td><td width='20%' bgcolor='#f9f9f5'>" + instAction + "</td></tr>";
/* 1036 */       sux = sux + "<tr><td width='20%' bgcolor='#AAAAAA' align='right'>" + FormatUtil.getString("am.ec2instanceaction.actiontype.text") + "</td><td width='20%' bgcolor='#f9f9f5'>" + actionType + "</td></tr></table><br>";
/* 1037 */       String sux1 = null;
/* 1038 */       if (update == null)
/*      */       {
/* 1040 */         sux1 = EC2InstanceAction.initiateAmazonInstAction(targetId, actionName);
/*      */       }
/* 1042 */       if (sux1 != null)
/*      */       {
/* 1044 */         sux = sux + sux1;
/* 1045 */         amazonMailInfo.put("success", "true");
/* 1046 */         amazonMailInfo.put("message", FormatUtil.getString("am.ec2instanceaction.successmessage.text") + actionName);
/*      */       }
/*      */       else
/*      */       {
/* 1050 */         amazonMailInfo.put("success", "true");
/* 1051 */         amazonMailInfo.put("message", FormatUtil.getString("Action failed") + actionName);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception est)
/*      */     {
/* 1057 */       amazonMailInfo.put("success", "false");
/* 1058 */       amazonMailInfo.put("message", FormatUtil.getString("am.ec2instanceaction.failuremessage.text") + actionName + " - " + est.getMessage());
/*      */     }
/* 1060 */     subject = "Alarm for action - " + instAction;
/* 1061 */     String message = FormatUtil.findReplace(filePath, "~topheading~", topheading);
/* 1062 */     amazonMailInfo.put("tableheading", message);
/* 1063 */     String mailMssgInst = FormatUtil.findReplace(message, "~userinfo~", sux);
/* 1064 */     String repotMailBy = FormatUtil.findReplace(mailMssgInst, "~reportby~", FormatUtil.getString("am.webclient.managermail.reportby.text"));
/* 1065 */     String newMailMssg = FormatUtil.findReplace(repotMailBy, "~product name~", OEMUtil.getOEMString("product.name"));
/* 1066 */     amazonMailInfo.put("mailMssg", newMailMssg);
/* 1067 */     amazonMailInfo.put("subject", subject);
/* 1068 */     String returnMssg = EC2InstanceAction.triggeredManualMssgAction(Integer.parseInt(actionID), amazonMailInfo);
/*      */     
/* 1070 */     String actionMsg = FormatUtil.getString("am.ec2instanceaction.failuremessage.text");
/* 1071 */     if (returnMssg != null)
/*      */     {
/* 1073 */       actionMsg = FormatUtil.getString("am.ec2instanceaction.successmessage.text");
/*      */     }
/* 1075 */     amazonMailInfo.put("actionName", actionName);
/* 1076 */     amazonMailInfo.put("actionMsg", actionMsg);
/* 1077 */     return amazonMailInfo;
/*      */   }
/*      */   
/*      */   private static void updateConfigurationInfo(String instanceResorceID, String attID, String setValue, String collectionTime)
/*      */   {
/* 1082 */     HashMap configMap = new HashMap();
/* 1083 */     configMap.put(attID, setValue);
/* 1084 */     NewMonitorUtil.insertConfigurationDetails(configMap, Integer.parseInt(instanceResorceID), Long.valueOf(collectionTime).longValue(), false);
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\appmanager\amazon\client\actions\EC2InstanceManageActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */