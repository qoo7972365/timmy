/*      */ package com.manageengine.appmanager.vservers.vmware.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*      */ import com.adventnet.appmanager.fault.AMThresholdApplier;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.credentialManager.CredentialManagerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.AMDCInf;
/*      */ import com.adventnet.appmanager.server.template.AMProcessTemplateManager;
/*      */ import com.adventnet.appmanager.struts.actions.AdminTools;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.vserver.VMUtil;
/*      */ import com.adventnet.appmanager.vserver.VMWareUtil;
/*      */ import com.manageengine.appmanager.xen.xenserver.operation.XenServerVMOperations;
/*      */ import com.manageengine.appmanager.xen.xenserver.util.XenServerDBUtil;
/*      */ import com.manageengine.eum.xen.xenserver.client.XenConnection;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.RequestDispatcher;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.htmlparser.util.Translate;
/*      */ 
/*      */ 
/*      */ public final class VMManageActions
/*      */   extends DispatchAction
/*      */ {
/*      */   private ManagedApplication mo;
/*      */   
/*   56 */   public VMManageActions() { this.mo = new ManagedApplication(); }
/*      */   
/*   58 */   private static final String APM_HOME = System.getProperty("webnms.rootdir", ".");
/*      */   
/*      */ 
/*      */   public ActionForward manageInstances(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*   64 */     System.out.println("(*) manageInstances(..)");
/*   65 */     ActionMessages messages = new ActionMessages();
/*   66 */     if ((request.getParameter("resIds") != null) && (request.getParameter("action") != null) && (request.getParameter("resIds").trim() != "") && (request.getParameter("action").trim() != ""))
/*      */     {
/*      */ 
/*   69 */       String action = request.getParameter("action");
/*   70 */       String resIds = request.getParameter("resIds");
/*   71 */       String setMessage = null;
/*   72 */       Integer vmPowerActnType = VMWareUtil.START;
/*   73 */       long vmAvailability = 1L;
/*      */       
/*   75 */       String[] resourceIds = resIds.split(",");
/*   76 */       System.out.println("(*) action=" + action);
/*   77 */       if (action.equalsIgnoreCase("Start"))
/*      */       {
/*   79 */         System.out.println("(*) action.equalsIgnoreCase( Start");
/*   80 */         setMessage = FormatUtil.getString("am.webclient.amazon.ec2instances.startinstances.text", new String[] { String.valueOf(resourceIds.length) });
/*   81 */         vmPowerActnType = VMWareUtil.START;
/*      */       }
/*   83 */       else if (action.equalsIgnoreCase("Stop"))
/*      */       {
/*   85 */         System.out.println("(*) action.equalsIgnoreCase( Stop");
/*   86 */         setMessage = FormatUtil.getString("am.webclient.amazon.ec2instances.stopinstances.text", new String[] { String.valueOf(resourceIds.length) });
/*   87 */         vmPowerActnType = VMWareUtil.STOP;
/*   88 */         vmAvailability = 0L;
/*      */       }
/*   90 */       else if (action.equalsIgnoreCase("Reboot"))
/*      */       {
/*   92 */         System.out.println("(*) action.equalsIgnoreCase( Reboot");
/*   93 */         setMessage = FormatUtil.getString("am.webclient.amazon.ec2instances.rebootinstances.text", new String[] { String.valueOf(resourceIds.length) });
/*   94 */         vmPowerActnType = VMWareUtil.RESTART;
/*      */       }
/*      */       
/*   97 */       HashMap<String, String[]> vmsResults = new HashMap();
/*   98 */       AMThresholdApplier thresholdApplier = new AMThresholdApplier();
/*   99 */       String resourceId; HashMap<String, Object[]> esxHostDetails; HashMap<String, String[]> vmResult; for (resourceId : resourceIds)
/*      */       {
/*  101 */         System.out.println("(*) resourceId=" + resourceId);
/*      */         
/*  103 */         esxHostDetails = VMUtil.collectESXs(resourceId, 4);
/*  104 */         vmResult = null;
/*  105 */         for (String esxResourceId : esxHostDetails.keySet())
/*      */         {
/*  107 */           vmResult = new HashMap();
/*  108 */           vmResult = VMWareUtil.takeAction(esxHostDetails, vmPowerActnType, -1L);
/*  109 */           System.out.println("(*) vmResult=" + vmResult);
/*      */           
/*  111 */           String[] values = null;
/*  112 */           for (String vmNme : vmResult.keySet())
/*      */           {
/*  114 */             values = (String[])vmResult.get(vmNme);
/*      */           }
/*  116 */           vmsResults.putAll(vmResult);
/*  117 */           System.out.println("(*) vmsResults=" + vmsResults);
/*      */           
/*  119 */           if (values[0].equals(VMWareUtil.VM_CHANGEACTION_SUCCESS))
/*      */           {
/*  121 */             long currSysTime = System.currentTimeMillis();
/*      */             
/*  123 */             thresholdApplier.applyThreshold(new Integer(resourceId).intValue(), 7600, vmAvailability, currSysTime);
/*  124 */             System.out.println("(*) The availability(7600) of resId(" + resourceId + ") successfully changed to " + vmAvailability);
/*      */             
/*  126 */             new AMRCAnalyser().applyRCA(new Integer(resourceId).intValue(), 7601, currSysTime);
/*  127 */             System.out.println("(*) The health(7601) of resId(" + resourceId + ") successfully changed in sysTime " + currSysTime);
/*      */             
/*      */ 
/*  130 */             String vmCurrentPowerState = vmPowerActnType == VMWareUtil.STOP ? FormatUtil.getString("am.vm.poweredoff") : FormatUtil.getString("am.vm.poweredon");
/*  131 */             VMUtil.updateVmPowerInDb(resourceId, vmCurrentPowerState);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  136 */       request.setAttribute("action", action);
/*  137 */       System.out.println("(*) vmsResults=" + vmsResults);
/*  138 */       request.setAttribute("vmsResults", vmsResults);
/*  139 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(setMessage));
/*  140 */       saveMessages(request, messages);
/*  141 */       request.setAttribute("SUCCESS", "true");
/*  142 */       return mapping.findForward("success");
/*      */     }
/*      */     
/*      */ 
/*  146 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.amazon.ec2instances.startstopreboot.default.errtext")));
/*  147 */     saveMessages(request, messages);
/*      */     
/*  149 */     request.setAttribute("SUCCESS", "false");
/*  150 */     return mapping.findForward("failure");
/*      */   }
/*      */   
/*      */   public ActionForward collectVMs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  156 */     System.out.println("(*) collectVMs(..)");
/*      */     
/*  158 */     String actionIdStr = request.getParameter("actionID");
/*  159 */     if (actionIdStr != null)
/*      */     {
/*  161 */       int actionId = new Integer(actionIdStr).intValue();
/*      */       
/*  163 */       String[] actionDetails = VMUtil.getActionDetails(actionId);
/*  164 */       String vmWarePowerType = actionDetails[3];
/*  165 */       System.out.println("vmWarePowerType===" + vmWarePowerType);
/*  166 */       ArrayList<String> vms = VMUtil.collectAssociatedVms(actionId, vmWarePowerType);
/*  167 */       System.out.println("vms===" + vms);
/*  168 */       if (!vms.isEmpty())
/*      */       {
/*  170 */         request.setAttribute("vmList", vms);
/*      */       }
/*  172 */       request.setAttribute("actionId", Integer.valueOf(actionId));
/*  173 */       String resourceType = "VMWare ESX/ESXi";
/*  174 */       int typeInt = Integer.parseInt(vmWarePowerType);
/*  175 */       if (typeInt > 800)
/*      */       {
/*  177 */         resourceType = "XenServerHost";
/*      */       }
/*  179 */       else if (typeInt > 200)
/*      */       {
/*  181 */         resourceType = "Hyper-V-Server";
/*      */       }
/*  183 */       request.setAttribute("resourceType", resourceType);
/*      */       
/*  185 */       String actionAndMail = "";
/*  186 */       String belowInstances = "";
/*      */       
/*      */ 
/*  189 */       System.out.println("vmWarePowerType===" + vmWarePowerType);
/*  190 */       if ((vmWarePowerType.equals(VMWareUtil.START.toString())) || (vmWarePowerType.equals("201")) || (vmWarePowerType.equals(VMUtil.XENSTART.toString())))
/*      */       {
/*  192 */         actionAndMail = FormatUtil.getString("am.vm.test.actionandmail", new String[] { FormatUtil.getString("am.vm.action.startvm") });
/*  193 */         belowInstances = FormatUtil.getString("am.vm.test.action.belowinstance", new String[] { FormatUtil.getString("am.vm.action.started") });
/*      */       }
/*  195 */       else if ((vmWarePowerType.equals(VMWareUtil.STOP.toString())) || (vmWarePowerType.equals("202")) || (vmWarePowerType.equals(VMUtil.XENSTOP.toString())))
/*      */       {
/*  197 */         actionAndMail = FormatUtil.getString("am.vm.test.actionandmail", new String[] { FormatUtil.getString("am.vm.action.stopvm") });
/*  198 */         belowInstances = FormatUtil.getString("am.vm.test.action.belowinstance", new String[] { FormatUtil.getString("am.vm.action.stopped") });
/*      */       }
/*  200 */       else if ((vmWarePowerType.equals(VMWareUtil.RESTART.toString())) || (vmWarePowerType.equals("203")) || (vmWarePowerType.equals(VMUtil.XENRESTART.toString())))
/*      */       {
/*  202 */         actionAndMail = FormatUtil.getString("am.vm.test.actionandmail", new String[] { FormatUtil.getString("am.vm.action.restartvm") });
/*  203 */         belowInstances = FormatUtil.getString("am.vm.test.action.belowinstance", new String[] { FormatUtil.getString("am.vm.action.restarted") });
/*      */       }
/*  205 */       System.out.println("(*) actionAndMail=" + actionAndMail);
/*  206 */       request.setAttribute("actionAndMail", actionAndMail);
/*  207 */       System.out.println("(*) belowInstances=" + belowInstances);
/*  208 */       request.setAttribute("belowInstances", belowInstances);
/*      */     }
/*  210 */     return mapping.findForward("success");
/*      */   }
/*      */   
/*      */   public ActionForward testVMAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  215 */     ActionMessages messages = new ActionMessages();
/*  216 */     ActionErrors errors = new ActionErrors();
/*      */     
/*  218 */     String actionId = request.getParameter("actionID");
/*  219 */     String executeVMActions = request.getParameter("executeVMActions");
/*  220 */     System.out.println("(*) testVMAction fn ( actionId=" + actionId + ", executeVMActions=" + executeVMActions + ").");
/*      */     
/*  222 */     Hashtable<String, String> result = getVMActionResp(actionId, executeVMActions);
/*  223 */     boolean success = Boolean.valueOf((String)result.get("success")).booleanValue();
/*  224 */     String message = (String)result.get("message");
/*  225 */     request.setAttribute("message", message);
/*  226 */     if (success)
/*      */     {
/*      */ 
/*  229 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("am.vm.testaction.success")));
/*  230 */       saveMessages(request, messages);
/*      */     }
/*      */     else
/*      */     {
/*  234 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", message));
/*  235 */       saveErrors(request, errors);
/*      */     }
/*      */     
/*  238 */     if (request.getParameter("remote") != null)
/*      */     {
/*  240 */       AdminTools.sendResponse(actionId, success, message, response);
/*  241 */       return null;
/*      */     }
/*      */     
/*  244 */     return mapping.findForward("success");
/*      */   }
/*      */   
/*      */   public static Hashtable<String, String> getVMActionResp(String actionId)
/*      */   {
/*  249 */     return getVMActionResp(actionId, "false");
/*      */   }
/*      */   
/*      */   public static Hashtable<String, String> getVMActionResp(String actionId, String executeVMActions)
/*      */   {
/*  254 */     Hashtable<String, String> vmActionResultTable = new Hashtable();
/*  255 */     boolean success = false;
/*  256 */     String message = FormatUtil.getString("am.vm.testaction.failure");
/*  257 */     String queryForAction = "Select AM_ACTIONPROFILE.NAME from AM_ACTIONPROFILE ,AM_JREACTIONS where AM_JREACTIONS.EMAIL_ACTION_ID=AM_ACTIONPROFILE.ID and AM_JREACTIONS.ID=" + actionId;
/*  258 */     ArrayList<ArrayList> list = DBUtil.getRows(queryForAction);
/*      */     
/*  260 */     if (!list.isEmpty())
/*      */     {
/*  262 */       VMManageActions vmManageActions = new VMManageActions(); VMManageActions 
/*  263 */         tmp68_66 = vmManageActions;tmp68_66.getClass();VmActionThread vmActionThread = new VmActionThread(tmp68_66);
/*  264 */       vmActionThread.execVMAction = executeVMActions;
/*  265 */       vmActionThread.actionId = actionId;
/*  266 */       vmActionThread.start();
/*  267 */       success = true;
/*  268 */       message = FormatUtil.getString("am.vm.testaction.success");
/*  269 */       vmActionResultTable.put("Action_Name", ((ArrayList)list.get(0)).get(0).toString());
/*      */     }
/*  271 */     vmActionResultTable.put("success", success + "");
/*  272 */     vmActionResultTable.put("message", message);
/*      */     
/*  274 */     return vmActionResultTable;
/*      */   }
/*      */   
/*      */   public ActionForward manageXenInstances(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
/*      */   {
/*  279 */     ActionMessages messages = new ActionMessages();
/*  280 */     String resIds = request.getParameter("resIds");
/*  281 */     String action = request.getParameter("action");
/*  282 */     AMLog.debug("[VMManageActions] calling manageXenInstances do perform action : " + action + " for Resource ID's : " + resIds);
/*      */     
/*  284 */     String message = "";
/*  285 */     int vmAvailability = 1;
/*  286 */     AMThresholdApplier thresholdApplier = new AMThresholdApplier();
/*  287 */     if ((resIds != null) && (action != null) && (!resIds.trim().equals("")) && (!action.trim().equals("")))
/*      */     {
/*  289 */       String[] resourceIDArray = resIds.split(",");
/*  290 */       XenServerVMOperations vmActionType = null;
/*  291 */       if (action.equalsIgnoreCase("start"))
/*      */       {
/*  293 */         vmActionType = XenServerVMOperations.START_VM;
/*  294 */         message = FormatUtil.getString("am.webclient.amazon.ec2instances.startinstances.text", new String[] { String.valueOf(resourceIDArray.length) });
/*      */       }
/*  296 */       else if (action.equalsIgnoreCase("stop"))
/*      */       {
/*  298 */         vmActionType = XenServerVMOperations.STOP_VM;
/*  299 */         message = FormatUtil.getString("am.webclient.amazon.ec2instances.stopinstances.text", new String[] { String.valueOf(resourceIDArray.length) });
/*  300 */         vmAvailability = 0;
/*      */       }
/*  302 */       else if (action.equalsIgnoreCase("reboot"))
/*      */       {
/*  304 */         vmActionType = XenServerVMOperations.RESTART_VM;
/*  305 */         message = FormatUtil.getString("am.webclient.amazon.ec2instances.rebootinstances.text", new String[] { String.valueOf(resourceIDArray.length) });
/*      */       }
/*      */       
/*  308 */       HashMap<String, HashMap<String, String>> vmResultMap = new HashMap();
/*  309 */       HashMap<String, HashMap<String, String>> xenServerHostDetails = XenServerDBUtil.getXenServerHostDetailsForVMResourceID(resIds);
/*  310 */       AMLog.debug("[VMManageActions] xenServerHostDetails === " + xenServerHostDetails);
/*  311 */       for (HashMap<String, String> xenServerMap : xenServerHostDetails.values())
/*      */       {
/*  313 */         AMLog.debug("[VMManageActions] xenServerMap :" + xenServerMap);
/*  314 */         XenConnection connection = null;
/*  315 */         String hostName = (String)xenServerMap.remove("HOSTNAME");
/*  316 */         int port = Integer.parseInt((String)xenServerMap.remove("PORT"));
/*  317 */         String userName = (String)xenServerMap.remove("USERNAME");
/*  318 */         String password = (String)xenServerMap.remove("PASSWORD");
/*  319 */         String vmHostName = (String)xenServerMap.remove("VMHOSTNAME");
/*      */         try
/*      */         {
/*  322 */           connection = new XenConnection(hostName, port, userName, password);
/*  323 */           for (String vmResourceID : xenServerMap.keySet())
/*      */           {
/*  325 */             String vmUuid = (String)xenServerMap.get(vmResourceID);
/*  326 */             HashMap<String, String> vmResult = XenServerVMOperations.performAction(vmActionType, connection, vmUuid);
/*  327 */             AMLog.debug("[VMManageActions] VM Action Result :" + vmResult.toString() + " for uuid :" + vmUuid);
/*  328 */             vmResultMap.put(vmResourceID, vmResult);
/*  329 */             if (((String)vmResult.get("Status")).equalsIgnoreCase("true"))
/*      */             {
/*  331 */               long currSysTime = System.currentTimeMillis();
/*      */               
/*  333 */               AMLog.debug("[VMManageActions] Calling applyThreshold for resourceID :" + vmResourceID + " for availability status change. The Availability state change value is:" + vmAvailability);
/*  334 */               thresholdApplier.applyThreshold(new Integer(vmResourceID).intValue(), 15500, vmAvailability, currSysTime);
/*      */               
/*      */ 
/*  337 */               AMLog.debug("[VMManageActions] Calling AMRCAAnalyser for resourceID :" + vmResourceID + " for RCA escalation for the availability state change.");
/*  338 */               new AMRCAnalyser().applyRCA(new Integer(vmResourceID).intValue(), 15501, currSysTime);
/*      */ 
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */ 
/*  349 */           e.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*  353 */           if (connection != null)
/*      */           {
/*  355 */             connection.close();
/*      */           }
/*      */         }
/*      */       }
/*  359 */       AMLog.debug("[VMManageActions] VM RESULT STATUS INFO :" + vmResultMap.toString());
/*  360 */       request.setAttribute("action", action);
/*  361 */       request.setAttribute("vmsResultMap", vmResultMap);
/*  362 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(message));
/*  363 */       saveMessages(request, messages);
/*  364 */       request.setAttribute("SUCCESS", "true");
/*  365 */       return mapping.findForward("success");
/*      */     }
/*      */     
/*      */ 
/*  369 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.amazon.ec2instances.startstopreboot.default.errtext")));
/*  370 */     saveMessages(request, messages);
/*  371 */     request.setAttribute("SUCCESS", "false");
/*  372 */     return mapping.findForward("failure");
/*      */   }
/*      */   
/*      */   class VmActionThread extends Thread {
/*      */     VmActionThread() {}
/*      */     
/*  378 */     String execVMAction = "false";
/*  379 */     String actionId = "";
/*      */     
/*      */     public void run() {
/*  382 */       if (Boolean.valueOf(this.execVMAction).booleanValue())
/*      */       {
/*  384 */         String htmlResult = VMUtil.triggerVMAction(Integer.parseInt(this.actionId), "");
/*  385 */         VMUtil.sendEMail(Integer.parseInt(this.actionId), htmlResult);
/*      */       }
/*      */       else
/*      */       {
/*  389 */         String[] actionDetails = VMUtil.getActionDetails(Integer.parseInt(this.actionId));
/*  390 */         VMUtil.sendTestEmail(actionDetails[4]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward configureOS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
/*      */   {
/*  397 */     ActionErrors errors = new ActionErrors();
/*  398 */     ActionMessages msgs = new ActionMessages();
/*  399 */     String resourceID = request.getParameter("resourceid");
/*  400 */     request.setAttribute("resourceID", resourceID);
/*  401 */     String apmHostType = System.getProperty("os.name");
/*  402 */     String mode = null;
/*  403 */     int timeOut = 40;
/*  404 */     Hashtable vmGuestDetails = getVMGuestDetails(resourceID);
/*  405 */     String vmGuestOS = (String)vmGuestDetails.get("OS");
/*  406 */     if ((apmHostType.indexOf("Windows") != -1) && (vmGuestOS != null) && (vmGuestOS.indexOf("Windows") != -1))
/*      */     {
/*  408 */       request.setAttribute("isWindowsApmHost", Boolean.valueOf(true));
/*      */     }
/*      */     else
/*      */     {
/*  412 */       request.setAttribute("isWindowsApmHost", Boolean.valueOf(false));
/*      */     }
/*      */     
/*  415 */     if ((vmGuestDetails.get("HOSTNAME") != null) || (vmGuestDetails.get("IPADDRESS") != null))
/*      */     {
/*  417 */       request.setAttribute("isSaveAllowed", Boolean.valueOf(true));
/*  418 */       request.setAttribute("hostName", (String)vmGuestDetails.get("HOSTNAME"));
/*  419 */       request.setAttribute("targetIP", (String)vmGuestDetails.get("IPADDRESS"));
/*      */     }
/*      */     else
/*      */     {
/*  423 */       request.setAttribute("isSaveAllowed", Boolean.valueOf(false));
/*  424 */       request.setAttribute("hostName", (String)vmGuestDetails.get("MO_DISPLAYNAME"));
/*  425 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.vm.confing.os.metric.vmtool.error")));
/*  426 */       saveErrors(request, errors);
/*  427 */       return mapping.findForward("configure");
/*      */     }
/*      */     
/*  430 */     if (vmGuestOS == null)
/*      */     {
/*  432 */       request.setAttribute("isSaveAllowed", Boolean.valueOf(false));
/*  433 */       request.setAttribute("hostName", (String)vmGuestDetails.get("MO_DISPLAYNAME"));
/*  434 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.vm.confing.os.metric.vmtool.error")));
/*  435 */       saveErrors(request, errors);
/*  436 */       return mapping.findForward("configure");
/*      */     }
/*      */     
/*  439 */     if ((apmHostType.indexOf("Windows") == -1) && (vmGuestOS.indexOf("Windows") != -1))
/*      */     {
/*  441 */       request.setAttribute("isSaveAllowed", Boolean.valueOf(false));
/*  442 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.vm.confing.os.metric.linuxbuild.error", new String[] { OEMUtil.getOEMString("product.name") })));
/*  443 */       saveErrors(request, errors);
/*  444 */       return mapping.findForward("configure");
/*      */     }
/*      */     
/*  447 */     String resNameQuery = "select hd.RESOURCENAME,hi.MODE,hi.TELNETPORT,hd.USERNAME," + DBQueryUtil.decode("hd.PASSWORD") + " as PASSWORD,hd.PROMPT,hi.SNMPCOMMUNITY,hi.TIMEOUT from HostDetails as hd ,  AM_HOSTINFO as hi , AM_ManagedObject as ammo where ammo.RESOURCENAME=hd.RESOURCENAME and hi.RESOURCEID= ammo.RESOURCEID and  ammo.RESOURCEID=" + resourceID;
/*      */     
/*  449 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  450 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  453 */       rs = AMConnectionPool.executeQueryStmt(resNameQuery);
/*  454 */       if (rs.next())
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  462 */         if (rs.getInt("TIMEOUT") > 0)
/*      */         {
/*  464 */           timeOut = rs.getInt("TIMEOUT");
/*      */         }
/*  466 */         mode = rs.getString("MODE").toLowerCase();
/*  467 */         ((DynaActionForm)form).set("mode", mode);
/*  468 */         ((DynaActionForm)form).set("port", Integer.valueOf(rs.getInt("TELNETPORT")));
/*  469 */         ((DynaActionForm)form).set("username", rs.getString("USERNAME"));
/*  470 */         ((DynaActionForm)form).set("prompt", rs.getString("PROMPT"));
/*  471 */         boolean issshPKAuth = false;
/*  472 */         if (("ssh".equalsIgnoreCase(mode)) && ("keyBasedAuth".equals(rs.getString("SNMPCOMMUNITY"))))
/*      */         {
/*  474 */           issshPKAuth = true;
/*      */         }
/*  476 */         ((DynaActionForm)form).set("sshPKAuth", Boolean.valueOf(issshPKAuth));
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  481 */         ((DynaActionForm)form).set("prompt", "$");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  487 */       ee.printStackTrace();
/*      */     }
/*      */     finally {
/*  490 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  492 */     ((DynaActionForm)form).set("timeOut", Integer.valueOf(timeOut));
/*  493 */     boolean isEventLogEnabled = false;
/*  494 */     String eventLogQuery = "select STATUS from AM_HOSTEVENTLOGSTATUS where RESOURCEID=" + resourceID;
/*  495 */     ResultSet rs2 = null;
/*  496 */     if ((mode != null) && ("wmi".equalsIgnoreCase(mode)))
/*      */     {
/*      */ 
/*      */       try
/*      */       {
/*  501 */         rs2 = AMConnectionPool.executeQueryStmt(eventLogQuery);
/*  502 */         if ((rs2.next()) && (rs2.getInt(1) == 1))
/*      */         {
/*  504 */           isEventLogEnabled = true;
/*      */         }
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/*  509 */         ee.printStackTrace();
/*      */       }
/*      */       finally {
/*  512 */         AMConnectionPool.closeStatement(rs2);
/*      */       }
/*  514 */       ((DynaActionForm)form).set("isEventLogEnabled", Boolean.valueOf(isEventLogEnabled));
/*      */     }
/*      */     
/*      */ 
/*  518 */     String osTypeForArgs = "Windows";
/*  519 */     if (((String)vmGuestDetails.get("OS")).indexOf("Windows") == -1)
/*      */     {
/*  521 */       osTypeForArgs = "Linux";
/*      */     }
/*  523 */     ArrayList args = NewMonitorUtil.getArgsforConfMon(osTypeForArgs);
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  528 */       Hashtable credentialHash = new Hashtable();
/*  529 */       if (((HashMap)args.get(4)).get("cmTelnetValue") != null)
/*      */       {
/*  531 */         credentialHash.put("cmTelnet", ((HashMap)args.get(4)).get("cmTelnetValue"));
/*      */       }
/*  533 */       if (((HashMap)args.get(4)).get("cmSSHValue") != null)
/*      */       {
/*  535 */         credentialHash.put("cmSSH", ((HashMap)args.get(4)).get("cmSSHValue"));
/*      */       }
/*  537 */       if (((HashMap)args.get(4)).get("cmWMIValue") != null)
/*      */       {
/*  539 */         credentialHash.put("cmWMI", ((HashMap)args.get(4)).get("cmWMIValue"));
/*      */       }
/*      */       
/*  542 */       request.setAttribute("credentialHash", credentialHash);
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  546 */       ee.printStackTrace();
/*      */     }
/*      */     
/*  549 */     CredentialManagerUtil credUtil = new CredentialManagerUtil();
/*  550 */     long getCredIDIfPresent = credUtil.checkResIDInMappingTable(Integer.parseInt(resourceID));
/*  551 */     if (getCredIDIfPresent == -1L)
/*      */     {
/*  553 */       ((DynaActionForm)form).set("isCredentialManager", "false");
/*      */     }
/*      */     else
/*      */     {
/*  557 */       ((DynaActionForm)form).set("isCredentialManager", "true");
/*  558 */       ((DynaActionForm)form).set("credentialID", String.valueOf(getCredIDIfPresent));
/*  559 */       request.setAttribute("credentialIDSel", Long.valueOf(getCredIDIfPresent));
/*      */     }
/*  561 */     return mapping.findForward("configure");
/*      */   }
/*      */   
/*      */   public ActionForward updateConfigureOS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
/*  565 */     ActionErrors errors = new ActionErrors();
/*  566 */     ActionMessages msgs = new ActionMessages();
/*  567 */     boolean isUpdate = false;
/*  568 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  569 */     ResultSet rs = null;
/*  570 */     CredentialManagerUtil credUtil = new CredentialManagerUtil();
/*  571 */     boolean isCredentialManager = false;
/*  572 */     long credentialID = -1L;
/*  573 */     Properties credentialSelectedProperties = new Properties();
/*      */     
/*      */ 
/*  576 */     String resourceID = (String)((DynaActionForm)form).get("resourceID");
/*  577 */     Hashtable vmGuestDetails = getVMGuestDetails(resourceID);
/*  578 */     String category = "VirtualMachine";
/*  579 */     String tmpCategory = (String)vmGuestDetails.get("MO_TYPE");
/*  580 */     String vmBaseID = Constants.getTypeId(tmpCategory);
/*  581 */     if (tmpCategory != null)
/*      */     {
/*  583 */       if (!tmpCategory.equals("XenServerVM"))
/*      */       {
/*  585 */         category = tmpCategory;
/*      */       }
/*      */     }
/*  588 */     String mode = ((String)((DynaActionForm)form).get("mode")).toUpperCase();
/*  589 */     String port = "23";
/*  590 */     if (((DynaActionForm)form).get("port") != null)
/*      */     {
/*  592 */       port = ((DynaActionForm)form).get("port").toString();
/*      */     }
/*  594 */     int telnetPort = Integer.parseInt(port);
/*  595 */     String snmpCommunity = "public";
/*  596 */     String username = (String)((DynaActionForm)form).get("username");
/*  597 */     String privateKey = null;
/*  598 */     String password = "";
/*  599 */     String passPhrase = "";
/*  600 */     boolean issshPKAuth = false;
/*  601 */     int timeOut = ((Integer)((DynaActionForm)form).get("timeOut")).intValue();
/*      */     
/*  603 */     if ((((DynaActionForm)form).get("isCredentialManager") != null) && ("true".equals((String)((DynaActionForm)form).get("isCredentialManager"))))
/*      */     {
/*  605 */       isCredentialManager = true;
/*  606 */       credentialID = new Long((String)((DynaActionForm)form).get("credentialID")).longValue();
/*  607 */       credentialSelectedProperties = credUtil.rowNameVsValue(credentialID);
/*      */     }
/*      */     
/*      */ 
/*  611 */     if (("ssh".equalsIgnoreCase(mode)) && (((DynaActionForm)form).get("sshPKAuth") != null))
/*      */     {
/*  613 */       issshPKAuth = ((Boolean)((DynaActionForm)form).get("sshPKAuth")).booleanValue();
/*      */     }
/*  615 */     if ((issshPKAuth) && (((DynaActionForm)form).get("privateKey") != null))
/*      */     {
/*  617 */       privateKey = (String)((DynaActionForm)form).get("privateKey");
/*  618 */       snmpCommunity = "keyBasedAuth";
/*  619 */       passPhrase = (String)((DynaActionForm)form).get("passPhrase");
/*      */     }
/*  621 */     else if (((DynaActionForm)form).get("password") != null)
/*      */     {
/*  623 */       password = (String)((DynaActionForm)form).get("password");
/*      */     }
/*  625 */     boolean isEventLogEnabled = false;
/*  626 */     if (("wmi".equalsIgnoreCase(mode)) && (((DynaActionForm)form).get("isEventLogEnabled") != null))
/*      */     {
/*  628 */       isEventLogEnabled = ((Boolean)((DynaActionForm)form).get("isEventLogEnabled")).booleanValue();
/*      */     }
/*  630 */     String prompt = "$";
/*  631 */     if (((DynaActionForm)form).get("prompt") != null)
/*      */     {
/*  633 */       prompt = Translate.decode((String)((DynaActionForm)form).get("prompt"));
/*      */     }
/*      */     
/*      */ 
/*  637 */     if (isCredentialManager)
/*      */     {
/*  639 */       username = credentialSelectedProperties.getProperty("username");
/*  640 */       password = credentialSelectedProperties.getProperty("password");
/*  641 */       if (credentialSelectedProperties.getProperty("prompt") != null)
/*      */       {
/*  643 */         prompt = credentialSelectedProperties.getProperty("prompt");
/*      */       }
/*  645 */       if (("ssh".equalsIgnoreCase(mode)) && (credentialSelectedProperties.getProperty("sshPKAuth", "false").equalsIgnoreCase("true")))
/*      */       {
/*  647 */         issshPKAuth = true;
/*  648 */         snmpCommunity = "keyBasedAuth";
/*  649 */         passPhrase = credentialSelectedProperties.getProperty("passPhrase");
/*  650 */         password = credentialSelectedProperties.getProperty("passPhrase");
/*      */       }
/*      */       
/*      */ 
/*  654 */       ((DynaActionForm)form).set("isCredentialManager", "true");
/*  655 */       ((DynaActionForm)form).set("credentialID", String.valueOf(credentialID));
/*  656 */       request.setAttribute("credentialIDSel", Long.valueOf(credentialID));
/*      */     } else {
/*  658 */       ((DynaActionForm)form).set("isCredentialManager", "false");
/*      */     }
/*      */     
/*      */ 
/*  662 */     String isConfigQuery = "SELECT GUESTCONFIG FROM AM_ARGS_" + vmBaseID + " WHERE RESOURCEID=" + resourceID;
/*  663 */     if (DBQueryUtil.isPgsql())
/*      */     {
/*  665 */       isConfigQuery = "SELECT \"GuestConfig\" FROM AM_ARGS_" + vmBaseID + " WHERE RESOURCEID=" + resourceID;
/*      */     }
/*      */     try {
/*  668 */       rs = AMConnectionPool.executeQueryStmt(isConfigQuery);
/*  669 */       if (rs.next())
/*      */       {
/*  671 */         String guestConfig = rs.getString(1);
/*  672 */         if ((guestConfig != null) && (Integer.parseInt(guestConfig) == 1))
/*      */         {
/*  674 */           isUpdate = true;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ee) {
/*  679 */       ee.printStackTrace();
/*      */     }
/*      */     finally {
/*  682 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/*  685 */     String resourceName = "";
/*  686 */     if (vmGuestDetails.get("RESOURCENAME") != null)
/*      */     {
/*  688 */       resourceName = (String)vmGuestDetails.get("RESOURCENAME");
/*      */     }
/*  690 */     String displayName = null;
/*  691 */     String targetAddress = null;
/*  692 */     if (vmGuestDetails.get("IPADDRESS") != null)
/*      */     {
/*      */       try
/*      */       {
/*  696 */         InetAddress iAddr = InetAddress.getByName((String)vmGuestDetails.get("IPADDRESS"));
/*  697 */         displayName = iAddr.getCanonicalHostName();
/*  698 */         targetAddress = iAddr.getHostAddress();
/*      */ 
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/*      */ 
/*  704 */         ee.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  711 */     if ((displayName == null) || (targetAddress == null) || ((displayName != null) && (targetAddress != null) && (displayName.equals(targetAddress)) && (vmGuestDetails.get("HOSTNAME") != null)))
/*      */     {
/*      */       try
/*      */       {
/*  715 */         InetAddress iAddr = InetAddress.getByName((String)vmGuestDetails.get("HOSTNAME"));
/*  716 */         displayName = iAddr.getCanonicalHostName();
/*  717 */         targetAddress = iAddr.getHostAddress();
/*      */ 
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/*      */ 
/*  723 */         ee.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  732 */     String resourceType = "";
/*  733 */     if (vmGuestDetails.get("OS") != null)
/*      */     {
/*  735 */       resourceType = (String)vmGuestDetails.get("OS");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  740 */     request.setAttribute("isSaveAllowed", Boolean.valueOf(true));
/*  741 */     request.setAttribute("hostName", displayName);
/*  742 */     request.setAttribute("targetIP", targetAddress);
/*  743 */     request.setAttribute("resourceID", resourceID);
/*  744 */     ((DynaActionForm)form).set("mode", mode.toLowerCase());
/*  745 */     ((DynaActionForm)form).set("port", Integer.valueOf(Integer.parseInt(port)));
/*  746 */     ((DynaActionForm)form).set("username", username);
/*  747 */     ((DynaActionForm)form).set("prompt", prompt);
/*  748 */     ((DynaActionForm)form).set("isEventLogEnabled", Boolean.valueOf(isEventLogEnabled));
/*  749 */     ((DynaActionForm)form).set("sshPKAuth", Boolean.valueOf(issshPKAuth));
/*  750 */     if (privateKey != null)
/*      */     {
/*  752 */       ((DynaActionForm)form).set("privateKey", privateKey);
/*      */     }
/*  754 */     String ostype = System.getProperty("os.name");
/*  755 */     if ((ostype.indexOf("Windows") != -1) && (resourceType.indexOf("Windows") != -1))
/*      */     {
/*  757 */       request.setAttribute("isWindowsApmHost", Boolean.valueOf(true));
/*      */     }
/*      */     else
/*      */     {
/*  761 */       request.setAttribute("isWindowsApmHost", Boolean.valueOf(false));
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  767 */       String osTypeForArgs = "Windows";
/*  768 */       if (((String)vmGuestDetails.get("OS")).indexOf("Windows") == -1)
/*      */       {
/*  770 */         osTypeForArgs = "Linux";
/*      */       }
/*  772 */       ArrayList args = NewMonitorUtil.getArgsforConfMon(osTypeForArgs);
/*  773 */       Hashtable credentialHash = new Hashtable();
/*  774 */       if (((HashMap)args.get(4)).get("cmTelnetValue") != null)
/*      */       {
/*  776 */         credentialHash.put("cmTelnet", ((HashMap)args.get(4)).get("cmTelnetValue"));
/*      */       }
/*  778 */       if (((HashMap)args.get(4)).get("cmSSHValue") != null)
/*      */       {
/*  780 */         credentialHash.put("cmSSH", ((HashMap)args.get(4)).get("cmSSHValue"));
/*      */       }
/*  782 */       if (((HashMap)args.get(4)).get("cmWMIValue") != null)
/*      */       {
/*  784 */         credentialHash.put("cmWMI", ((HashMap)args.get(4)).get("cmWMIValue"));
/*      */       }
/*      */       
/*  787 */       request.setAttribute("credentialHash", credentialHash);
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  791 */       ee.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*  795 */     Properties hostProperties = new Properties();
/*  796 */     hostProperties.setProperty("HOST", targetAddress);
/*      */     
/*  798 */     hostProperties.setProperty("givenhost", displayName);
/*      */     
/*  800 */     hostProperties.setProperty("PORT", port);
/*  801 */     hostProperties.setProperty("username", username);
/*  802 */     if (password == null)
/*      */     {
/*  804 */       password = "";
/*      */     }
/*  806 */     hostProperties.setProperty("password", password);
/*  807 */     hostProperties.setProperty("TIMEOUT", String.valueOf(timeOut));
/*  808 */     if ((isCredentialManager) && (issshPKAuth))
/*      */     {
/*  810 */       String credName = credUtil.getDetailsForACredential(String.valueOf(credentialID)).getProperty("credentialName", "");
/*  811 */       String srcFile = APM_HOME + File.separator + "credentialManager_privateKey_" + credName + ".txt";
/*  812 */       String fileFormatAsPerCanonicalName = credUtil.getNameAsPerSSHFormat(displayName);
/*  813 */       String destFile = APM_HOME + File.separator + fileFormatAsPerCanonicalName;
/*  814 */       credUtil.copyFile(srcFile, destFile);
/*  815 */       hostProperties.setProperty("sshPKAuthChecked", "checked");
/*  816 */       hostProperties.setProperty("sshPKFileName", srcFile);
/*  817 */       String passwordPassPhrase = credentialSelectedProperties.getProperty("passphrase", "");
/*  818 */       credentialSelectedProperties.setProperty("password", passwordPassPhrase);
/*  819 */       hostProperties.setProperty("password", passwordPassPhrase);
/*      */     }
/*  821 */     else if ((issshPKAuth) && (!isCredentialManager))
/*      */     {
/*  823 */       hostProperties.setProperty("sshPKAuthChecked", "checked");
/*  824 */       hostProperties.setProperty("privateKey", privateKey);
/*  825 */       hostProperties.setProperty("password", passPhrase);
/*      */     }
/*      */     else
/*      */     {
/*  829 */       hostProperties.setProperty("password", password);
/*      */     }
/*      */     
/*  832 */     hostProperties.setProperty("os", resourceType);
/*  833 */     hostProperties.setProperty("MODE", mode);
/*  834 */     hostProperties.setProperty("TELNETPORT", String.valueOf(telnetPort));
/*  835 */     hostProperties.setProperty("prompt", prompt);
/*      */     
/*  837 */     Properties authCheckPorps = new Properties();
/*      */     
/*  839 */     AMDCInf amdc = null;
/*      */     try
/*      */     {
/*  842 */       amdc = (AMDCInf)Class.forName("com.adventnet.appmanager.server.hostresources.datacollection.ScheduleHostDataCollection").newInstance();
/*  843 */       authCheckPorps = amdc.CheckAuthentication(hostProperties);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  847 */       e.printStackTrace();
/*      */     }
/*  849 */     if ((authCheckPorps.getProperty("authentication") != null) && ("failed".equalsIgnoreCase(authCheckPorps.getProperty("authentication"))))
/*      */     {
/*  851 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", authCheckPorps.getProperty("error")));
/*  852 */       saveErrors(request, errors);
/*  853 */       return mapping.findForward("configure");
/*      */     }
/*  855 */     if (authCheckPorps.getProperty("authentication") == null)
/*      */     {
/*  857 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.vm.confing.os.metric.update.error")));
/*  858 */       saveErrors(request, errors);
/*  859 */       return mapping.findForward("configure");
/*      */     }
/*      */     
/*  862 */     long getCredIDIfPresent = credUtil.checkResIDInMappingTable(Integer.parseInt(resourceID));
/*  863 */     if (isCredentialManager)
/*      */     {
/*  865 */       if (getCredIDIfPresent == -1L)
/*      */       {
/*  867 */         credUtil.addToCredentialToResourceMap(credentialID, Integer.parseInt(resourceID));
/*      */       }
/*  869 */       else if (credentialID != getCredIDIfPresent)
/*      */       {
/*  871 */         credUtil.updateCredentialToResourceMap(credentialID, Integer.parseInt(resourceID));
/*      */       }
/*  873 */       ((DynaActionForm)form).set("isCredentialManager", "true");
/*  874 */       ((DynaActionForm)form).set("credentialID", String.valueOf(credentialID));
/*  875 */       request.setAttribute("credentialIDSel", Long.valueOf(getCredIDIfPresent));
/*      */     }
/*      */     else
/*      */     {
/*  879 */       ((DynaActionForm)form).set("isCredentialManager", "false");
/*  880 */       if (getCredIDIfPresent != -1L)
/*      */       {
/*  882 */         credUtil.deleteCredential(getCredIDIfPresent);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  887 */     boolean isInsertEventLog = false;
/*      */     try
/*      */     {
/*  890 */       String query = "SELECT STATUS FROM AM_HOSTEVENTLOGSTATUS WHERE RESOURCEID='" + resourceID + "'";
/*  891 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  892 */       int status = -1;
/*  893 */       int formStatus = isEventLogEnabled ? 1 : 0;
/*  894 */       if (rs.next())
/*      */       {
/*  896 */         status = rs.getInt(1);
/*  897 */         isInsertEventLog = true;
/*      */       }
/*  899 */       if (!isInsertEventLog)
/*      */       {
/*  901 */         query = "INSERT INTO AM_HOSTEVENTLOGSTATUS VALUES (" + resourceID + ", " + (isEventLogEnabled ? 1 : 0) + ")";
/*  902 */         AMLog.debug("Insert query for AM_HOSTEVENTLOGSTATUS: " + query);
/*  903 */         AMConnectionPool.executeUpdateStmt(query);
/*      */       }
/*  905 */       else if ((isInsertEventLog) && (status != formStatus))
/*      */       {
/*  907 */         query = "UPDATE AM_HOSTEVENTLOGSTATUS SET STATUS=" + (isEventLogEnabled ? 1 : 0) + " WHERE RESOURCEID=" + resourceID;
/*  908 */         AMLog.debug("update query for AM_HOSTEVENTLOGSTATUS: " + query);
/*  909 */         AMConnectionPool.executeUpdateStmt(query);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  914 */       e.printStackTrace();
/*  915 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.vm.confing.os.metric.insert.error")));
/*  916 */       saveErrors(request, errors);
/*      */     }
/*      */     finally
/*      */     {
/*  920 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/*  923 */     if (!isUpdate)
/*      */     {
/*  925 */       PreparedStatement ps = null;
/*      */       
/*      */       try
/*      */       {
/*  929 */         String componentName = "HOST";
/*  930 */         String configClass = "com.adventnet.nms.appln.hostresource.datacollection.server.model.HostDetails";
/*  931 */         String dcClass = "com.adventnet.appmanager.server.hostresources.datacollection.HostResourceMonitor";
/*  932 */         String dcXmlName = "conf/application/HostResource.xml";
/*  933 */         String dcDtdName = "conf/application/HostResource.dtd";
/*  934 */         String active = "false";
/*  935 */         String storeData = "false";
/*  936 */         String configured = "false";
/*  937 */         int pollInterval = 300;
/*  938 */         int applnDiscPort = -1;
/*  939 */         String webNMS = "";
/*      */         try
/*      */         {
/*  942 */           ps = AMConnectionPool.getConnection().prepareStatement("insert into CollectData values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*  943 */           ps.setString(1, resourceName);
/*  944 */           ps.setString(2, componentName);
/*  945 */           ps.setString(3, displayName);
/*  946 */           ps.setString(4, targetAddress);
/*  947 */           ps.setString(5, resourceType);
/*  948 */           ps.setString(6, configClass);
/*  949 */           ps.setString(7, dcClass);
/*  950 */           ps.setString(8, dcXmlName);
/*  951 */           ps.setString(9, dcDtdName);
/*  952 */           ps.setString(10, active);
/*  953 */           ps.setString(11, storeData);
/*  954 */           ps.setString(12, configured);
/*  955 */           ps.setInt(13, pollInterval);
/*  956 */           ps.setInt(14, applnDiscPort);
/*  957 */           ps.setString(15, webNMS);
/*  958 */           ps.executeUpdate();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  962 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  968 */         String errorMsg = "";
/*  969 */         String loginPrompt = ":";
/*  970 */         String passwordPrompt = ":";
/*      */         
/*      */         try
/*      */         {
/*  974 */           AMConnectionPool.executeUpdateStmt("insert into HostDetails values ('" + resourceName + "','" + componentName + "','" + username + "'," + DBQueryUtil.encode(password) + ",'" + prompt + "','" + category + "','" + errorMsg + "','" + loginPrompt + "','" + passwordPrompt + "')");
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  978 */           e.printStackTrace();
/*      */         }
/*  980 */         int resID = Integer.parseInt(resourceID);
/*      */         
/*      */ 
/*  983 */         int snmpPort = 161;
/*      */         
/*  985 */         String snmpVersion = "V1V2";
/*      */         
/*  987 */         String am_userName = "";
/*  988 */         String contextName = "";
/*  989 */         String securityLevel = "";
/*  990 */         String authProtocol = "";
/*  991 */         String authPassword = "";
/*  992 */         String privProtocal = "";
/*  993 */         String privPassword = "";
/*      */         
/*  995 */         ps = AMConnectionPool.getConnection().prepareStatement("insert into AM_HOSTINFO values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*  996 */         ps.setInt(1, resID);
/*  997 */         ps.setString(2, mode);
/*  998 */         ps.setInt(3, telnetPort);
/*  999 */         ps.setInt(4, snmpPort);
/* 1000 */         ps.setString(5, snmpCommunity);
/* 1001 */         ps.setString(6, snmpVersion);
/* 1002 */         ps.setString(7, am_userName);
/* 1003 */         ps.setString(8, contextName);
/* 1004 */         ps.setString(9, securityLevel);
/* 1005 */         ps.setString(10, authProtocol);
/* 1006 */         ps.setBytes(11, authPassword.getBytes());
/* 1007 */         ps.setString(12, privProtocal);
/* 1008 */         ps.setBytes(13, privPassword.getBytes());
/* 1009 */         ps.setInt(14, timeOut);
/* 1010 */         ps.executeUpdate();
/*      */         
/* 1012 */         String parentKey = "NULL";
/* 1013 */         String managed = "true";
/* 1014 */         int status = 5;
/* 1015 */         int failureThreshold = 1;
/* 1016 */         int failureCount = 0;
/* 1017 */         String statusChangeTime = "0";
/* 1018 */         String statusUpdateTime = "0";
/* 1019 */         String tester = "usertest";
/* 1020 */         String uClass = "com.adventnet.appmanager.server.framework.statuspoll.ApplicationStatusPoller";
/* 1021 */         String className = "Node";
/* 1022 */         String webNMS2 = "NULL";
/* 1023 */         String ownerName = "NULL";
/* 1024 */         String statusPollEnabled = "flase";
/* 1025 */         String isContainer = "flase";
/* 1026 */         String isGroup = "flase";
/*      */         try
/*      */         {
/* 1029 */           ps = AMConnectionPool.getConnection().prepareStatement("insert into ManagedObject values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/* 1030 */           ps.setString(1, resourceName);
/* 1031 */           ps.setString(2, displayName);
/* 1032 */           ps.setString(3, parentKey);
/* 1033 */           ps.setString(4, resourceType);
/* 1034 */           ps.setString(5, managed);
/* 1035 */           ps.setInt(6, status);
/* 1036 */           ps.setInt(7, failureThreshold);
/* 1037 */           ps.setInt(8, failureCount);
/* 1038 */           ps.setInt(9, pollInterval);
/* 1039 */           ps.setString(10, statusChangeTime);
/* 1040 */           ps.setString(11, statusUpdateTime);
/* 1041 */           ps.setString(12, tester);
/* 1042 */           ps.setString(13, uClass);
/* 1043 */           ps.setString(14, className);
/* 1044 */           ps.setString(15, webNMS2);
/* 1045 */           ps.setString(16, ownerName);
/* 1046 */           ps.setString(17, statusPollEnabled);
/* 1047 */           ps.setString(18, isContainer);
/* 1048 */           ps.setString(19, isGroup);
/* 1049 */           ps.executeUpdate();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1053 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/* 1057 */         String insertQuery = "INSERT INTO AM_ARGS_" + vmBaseID + " (RESOURCEID, GUESTCONFIG) VALUES (?,?)";
/* 1058 */         if (DBQueryUtil.isPgsql())
/*      */         {
/* 1060 */           insertQuery = "INSERT INTO AM_ARGS_" + vmBaseID + " (RESOURCEID, \"GuestConfig\") VALUES (?,?)";
/*      */         }
/* 1062 */         String moType = (String)vmGuestDetails.get("MO_TYPE");
/*      */         
/* 1064 */         ps = AMConnectionPool.getConnection().prepareStatement(insertQuery);
/* 1065 */         ps.setInt(1, resID);
/* 1066 */         ps.setString(2, "1");
/* 1067 */         ps.executeUpdate();
/*      */         
/*      */ 
/*      */ 
/* 1071 */         msgs.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("am.webclient.vm.confing.os.metric.insert.success")));
/* 1072 */         saveMessages(request, msgs);
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/* 1076 */         ee.printStackTrace();
/*      */         
/*      */ 
/* 1079 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.vm.confing.os.metric.insert.error")));
/* 1080 */         saveErrors(request, errors);
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/* 1086 */           ps.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1096 */     PreparedStatement ps2 = null;
/*      */     try
/*      */     {
/* 1099 */       ps2 = AMConnectionPool.getConnection().prepareStatement("update CollectData set DISPLAYNAME=? , TARGETADDRESS=? , RESOURCETYPE=? where RESOURCENAME=?");
/* 1100 */       ps2.setString(1, displayName);
/* 1101 */       ps2.setString(2, targetAddress);
/* 1102 */       ps2.setString(3, resourceType);
/* 1103 */       ps2.setString(4, resourceName);
/* 1104 */       ps2.executeUpdate();
/* 1105 */       ps2 = AMConnectionPool.getConnection().prepareStatement("update AM_HOSTINFO set MODE=? , TELNETPORT=? , SNMPCOMMUNITY=? , TIMEOUT=? where RESOURCEID=?");
/* 1106 */       ps2.setString(1, mode);
/* 1107 */       ps2.setInt(2, telnetPort);
/* 1108 */       ps2.setString(3, snmpCommunity);
/* 1109 */       ps2.setInt(4, timeOut);
/* 1110 */       ps2.setInt(5, Integer.parseInt(resourceID));
/* 1111 */       ps2.executeUpdate();
/* 1112 */       EnterpriseUtil.addUpdateQueryToFile("update AM_HOSTINFO set MODE='" + mode + "',TELNETPORT=" + telnetPort + ",SNMPCOMMUNITY='" + snmpCommunity + "',TIMEOUT=" + timeOut + " where RESOURCEID=" + resourceID);
/* 1113 */       AMConnectionPool.executeUpdateStmt("update HostDetails set USERNAME='" + username + "',PASSWORD=" + DBQueryUtil.encode(password) + ",PROMPT='" + prompt + "' where  RESOURCENAME='" + resourceName + "'");
/*      */       
/*      */ 
/*      */ 
/* 1117 */       msgs.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("am.webclient.vm.confing.os.metric.update.success")));
/* 1118 */       saveMessages(request, msgs);
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
/*      */       try
/*      */       {
/* 1133 */         ps2.close();
/*      */       }
/*      */       catch (Exception exc) {}
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1144 */       AMProcessTemplateManager.associateNewServerToTemplate(resourceType, Integer.parseInt(resourceID), mode);
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 1123 */       ee.printStackTrace();
/*      */       
/*      */ 
/* 1126 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.vm.confing.os.metric.update.error")));
/* 1127 */       saveErrors(request, errors);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1133 */         ps2.close();
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1145 */     request.setAttribute("isReload", Boolean.valueOf(true));
/* 1146 */     return mapping.findForward("configure");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getVMTabs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/* 1153 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1154 */     ResultSet rs = null;
/*      */     try {
/* 1156 */       String resId = request.getParameter("resourceid");
/* 1157 */       int resourceID = Integer.parseInt(resId);
/* 1158 */       int startIndex = 0;
/* 1159 */       int selectedPage = 1;
/* 1160 */       int noOfRows = 25;
/* 1161 */       int tabId = 1;
/* 1162 */       if (request.getParameter("Id") != null) {
/* 1163 */         tabId = Integer.parseInt(request.getParameter("Id"));
/*      */       }
/* 1165 */       response.setContentType("text/html");
/*      */       
/* 1167 */       String actionPath = (String)request.getAttribute("actionPath");
/* 1168 */       request.setAttribute("actionPath", actionPath);
/* 1169 */       if (request.getParameter("selectedPage") != null)
/*      */       {
/* 1171 */         selectedPage = Integer.parseInt(request.getParameter("selectedPage"));
/*      */       }
/* 1173 */       if (request.getParameter("noOfRows") != null)
/*      */       {
/* 1175 */         noOfRows = Integer.parseInt(request.getParameter("noOfRows"));
/*      */       }
/* 1177 */       startIndex = (selectedPage - 1) * noOfRows;
/*      */       
/*      */ 
/* 1180 */       request.setAttribute("osType", getVMOSDetails(resId));
/*      */       try
/*      */       {
/* 1183 */         String modeQuery = "select MODE from AM_HOSTINFO where RESOURCEID=" + resId;
/* 1184 */         rs = AMConnectionPool.executeQueryStmt(modeQuery);
/* 1185 */         if (rs.next())
/*      */         {
/* 1187 */           request.setAttribute("mode", rs.getString(1));
/* 1188 */           request.setAttribute("isGuestConfigured", Boolean.valueOf(true));
/*      */         }
/*      */         else
/*      */         {
/* 1192 */           request.setAttribute("isGuestConfigured", Boolean.valueOf(false));
/*      */         }
/* 1194 */       } catch (Exception ee) { ee.printStackTrace(); }
/* 1195 */       rs = null;
/*      */       
/* 1197 */       if (tabId == 1)
/*      */       {
/* 1199 */         String hostResourceType = null;
/*      */         try
/*      */         {
/* 1202 */           String typeQuery = "select cd.RESOURCETYPE from CollectData as cd ,AM_ManagedObject as mo  where mo.RESOURCENAME=cd.RESOURCENAME and RESOURCEID=" + resId;
/* 1203 */           rs = AMConnectionPool.executeQueryStmt(typeQuery);
/* 1204 */           if (rs.next())
/*      */           {
/* 1206 */             hostResourceType = rs.getString(1); }
/*      */         } catch (Exception ee) {
/* 1208 */           ee.printStackTrace(); }
/* 1209 */         rs = null;
/*      */         
/* 1211 */         boolean isWindowsResType = (hostResourceType != null) && (hostResourceType.toLowerCase().indexOf("windows") != -1);
/* 1212 */         if (isWindowsResType)
/*      */         {
/* 1214 */           String serviceQuery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resId + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE='Service'";
/* 1215 */           ArrayList configuredservices = this.mo.getRows(serviceQuery);
/* 1216 */           if ((configuredservices != null) && (configuredservices.size() > 0))
/*      */           {
/* 1218 */             request.setAttribute("windowsServices", configuredservices);
/*      */           }
/*      */         }
/*      */         
/* 1222 */         RequestDispatcher rd = request.getRequestDispatcher("/jsp/ServicesInVM.jsp");
/* 1223 */         rd.include(request, response);
/*      */       }
/* 1225 */       else if (tabId == 2)
/*      */       {
/*      */ 
/*      */ 
/* 1229 */         String dctime = "0";
/*      */         try
/*      */         {
/* 1232 */           String timeStampQuery = "select max(ins.DCTIME) as DATACOLLECTIONTIME from AM_HOST_PROCESS_INSTANCE as ins,AM_HOST_PROCESS_INFO as info where ins. RESOURCEID= info.RESOURCEID and info.PARENTID=" + resId;
/* 1233 */           rs = AMConnectionPool.executeQueryStmt(timeStampQuery);
/* 1234 */           if ((rs.next()) && (rs.getString(1) != null))
/*      */           {
/* 1236 */             dctime = rs.getString(1);
/*      */           }
/*      */         } catch (Exception ee) {
/* 1239 */           ee.printStackTrace(); }
/* 1240 */         String dQuery = "select AM_HOST_PROCESS_INFO.RESOURCEID,DISPLAYNAME,PROCESSNAME,COMMAND,COALESCE(INSTANCE,'-1') INSTANCE,COALESCE(PCPU,'-1') PCPU,COALESCE(PMEM,'-1') PMEM from AM_HOST_PROCESS_INFO left outer join AM_HOST_PROCESS_INSTANCE on AM_HOST_PROCESS_INFO.RESOURCEID=AM_HOST_PROCESS_INSTANCE.RESOURCEID and AM_HOST_PROCESS_INSTANCE.DCTIME=" + dctime + " left outer join AM_HOST_PROCESS_CPUMEM on AM_HOST_PROCESS_INFO.RESOURCEID=AM_HOST_PROCESS_CPUMEM.RESOURCEID and AM_HOST_PROCESS_CPUMEM.DCTIME=" + dctime + " where AM_HOST_PROCESS_INFO.PARENTID=" + resId;
/*      */         
/* 1242 */         ArrayList list1 = this.mo.getRows(dQuery);
/*      */         
/* 1244 */         if (list1.size() != 0)
/*      */         {
/* 1246 */           request.setAttribute("processList", list1);
/*      */         }
/*      */         
/*      */ 
/* 1250 */         RequestDispatcher rd = request.getRequestDispatcher("/jsp/ProcessesInVM.jsp");
/* 1251 */         rd.include(request, response);
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/* 1257 */           String hostEventStatusQuery = "select STATUS  from AM_HOSTEVENTLOGSTATUS where RESOURCEID=" + resId;
/* 1258 */           rs = AMConnectionPool.executeQueryStmt(hostEventStatusQuery);
/* 1259 */           if (rs.next())
/*      */           {
/* 1261 */             request.setAttribute("hosteventlogstatus", rs.getString(1));
/*      */           }
/*      */           else
/*      */           {
/* 1265 */             request.setAttribute("hosteventlogstatus", "0"); }
/*      */         } catch (Exception ee) {
/* 1267 */           ee.printStackTrace(); }
/* 1268 */         rs = null;
/* 1269 */         String query = "select AM_EVENTLOGMONITORDATA.RULETYPE,AM_EVENTLOGMONITORDATA.SOURCE,AM_EVENTLOGMONITORDATA.EVENTID,AM_EVENTTYPE.DESCRIPTION,AM_EVENTLOGMONITORDATA.USERNAME,AM_EVENTLOGMONITORDATA.DESCRIPTIONSTRING,AM_EVENTLOGMONITORDATA.EVENTGENERATEDTIME,AM_GLOBALEVENTLOGRULES.RULENAME from AM_EVENTLOGMONITORDATA,AM_EVENTTYPE,AM_GLOBALEVENTLOGRULES where (AM_EVENTLOGMONITORDATA.RESOURCEID=" + resId + ") and (AM_EVENTLOGMONITORDATA.CLUSTERRESOURCEID=-1) and (AM_EVENTLOGMONITORDATA.EVENTTYPE=AM_EVENTTYPE.EVENTTYPE) and (AM_GLOBALEVENTLOGRULES.RULEID=AM_EVENTLOGMONITORDATA.RULEID) order by AM_EVENTLOGMONITORDATA.COLLECTIONTIME desc,AM_EVENTLOGMONITORDATA.EVENTGENERATEDTIME desc";
/* 1270 */         query = DBQueryUtil.getTopNValues(query, "5");
/*      */         
/* 1272 */         ArrayList latestevents = this.mo.getRows(query);
/* 1273 */         request.setAttribute("latestevents", latestevents);
/* 1274 */         RequestDispatcher rd = request.getRequestDispatcher("/jsp/EventLogsInVM.jsp");
/* 1275 */         rd.include(request, response);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1282 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1285 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1287 */     return null;
/*      */   }
/*      */   
/*      */   public static String getVMOSDetails(String resourceID)
/*      */   {
/* 1292 */     String osName = null;
/* 1293 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1294 */     ResultSet rs = null;
/* 1295 */     String osTypeQuery = "select GUESTOS from AM_VM_GUESTOSMAPPING where RESID =" + resourceID;
/*      */     try
/*      */     {
/* 1298 */       rs = AMConnectionPool.executeQueryStmt(osTypeQuery);
/* 1299 */       if (rs.next())
/*      */       {
/* 1301 */         String fullOSName = rs.getString(1);
/* 1302 */         if (fullOSName.contains("Windows XP"))
/*      */         {
/* 1304 */           osName = "Windows XP";
/*      */         }
/* 1306 */         else if (fullOSName.contains("Windows Server 2003"))
/*      */         {
/* 1308 */           osName = "Windows 2003";
/*      */         }
/* 1310 */         else if (fullOSName.contains("Windows Server 2008"))
/*      */         {
/* 1312 */           osName = "Windows 2008";
/*      */         }
/* 1314 */         else if (fullOSName.contains("Windows Vista"))
/*      */         {
/* 1316 */           osName = "Windows Vista";
/*      */         }
/* 1318 */         else if (fullOSName.contains("Windows 7"))
/*      */         {
/* 1320 */           osName = "Windows 7";
/*      */         }
/* 1322 */         else if (fullOSName.contains("Windows 8"))
/*      */         {
/* 1324 */           osName = "Windows 8";
/*      */         }
/* 1326 */         else if (fullOSName.contains("Windows 10"))
/*      */         {
/* 1328 */           osName = "Windows 10";
/*      */         }
/* 1330 */         else if (fullOSName.contains("Windows Server 2012"))
/*      */         {
/* 1332 */           osName = "Windows 2012";
/*      */         }
/* 1334 */         else if ((fullOSName.contains("Linux")) || (fullOSName.contains("CentOS")))
/*      */         {
/* 1336 */           osName = "Linux";
/*      */         }
/*      */         else
/*      */         {
/* 1340 */           osName = "Node";
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 1346 */       ee.printStackTrace();
/*      */     }
/*      */     finally {
/* 1349 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1351 */     return osName;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Hashtable getVMGuestDetails(String resourceID)
/*      */   {
/* 1357 */     Hashtable retHash = new Hashtable();
/* 1358 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1359 */     ResultSet rs = null;
/* 1360 */     String resourceType = null;
/* 1361 */     String attributeIdStr = "-1";
/* 1362 */     String resNameQuery = "SELECT RESOURCENAME,DISPLAYNAME,TYPE FROM AM_ManagedObject WHERE RESOURCEID =" + resourceID;
/*      */     
/*      */     try
/*      */     {
/* 1366 */       rs = AMConnectionPool.executeQueryStmt(resNameQuery);
/* 1367 */       if (rs.next())
/*      */       {
/* 1369 */         retHash.put("RESOURCENAME", rs.getString(1));
/* 1370 */         retHash.put("MO_DISPLAYNAME", rs.getString(2));
/* 1371 */         resourceType = rs.getString(3);
/* 1372 */         retHash.put("MO_TYPE", resourceType);
/*      */       }
/*      */       
/* 1375 */       if ("VirtualMachine".equals(resourceType))
/*      */       {
/* 1377 */         attributeIdStr = "7615,7616";
/*      */       }
/* 1379 */       else if ("XenServerVM".equals(resourceType))
/*      */       {
/* 1381 */         attributeIdStr = "15526";
/*      */       }
/*      */       
/* 1384 */       String ipAddressQuery = "SELECT CONFVALUE,ATTRIBUTEID  FROM  AM_CONFIGURATION_INFO WHERE LATEST=1 AND RESOURCEID=" + resourceID + " AND ATTRIBUTEID in (" + attributeIdStr + ")";
/* 1385 */       rs = AMConnectionPool.executeQueryStmt(ipAddressQuery);
/* 1386 */       while (rs.next())
/*      */       {
/* 1388 */         int attributeID = rs.getInt(2);
/* 1389 */         if ("VirtualMachine".equals(resourceType))
/*      */         {
/* 1391 */           if (attributeID == 7615)
/*      */           {
/* 1393 */             retHash.put("IPADDRESS", rs.getString(1));
/*      */           }
/*      */           else
/*      */           {
/* 1397 */             retHash.put("HOSTNAME", rs.getString(1));
/*      */           }
/*      */         }
/* 1400 */         else if ("XenServerVM".equals(resourceType))
/*      */         {
/* 1402 */           if (attributeID == 15526)
/*      */           {
/* 1404 */             retHash.put("HOSTNAME", rs.getString(1));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1410 */       String os = getVMOSDetails(resourceID);
/* 1411 */       if (os != null)
/*      */       {
/* 1413 */         retHash.put("OS", getVMOSDetails(resourceID));
/*      */       }
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 1418 */       ee.printStackTrace();
/*      */     }
/*      */     finally {
/* 1421 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1423 */     return retHash;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\appmanager\vservers\vmware\actions\VMManageActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */