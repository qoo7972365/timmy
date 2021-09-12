/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*     */ import com.adventnet.appmanager.cam.CAMSNMPUtil;
/*     */ import com.adventnet.appmanager.cam.CAMServerUtil;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.SNMPUtil;
/*     */ import com.adventnet.snmp.mibs.MibModule;
/*     */ import com.adventnet.snmp.mibs.MibOperations;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
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
/*     */ public class CAMSNMPAction
/*     */   extends DispatchAction
/*     */ {
/*  42 */   private AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward addSNMPObjects(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  51 */     ActionMessages messages = new ActionMessages();
/*  52 */     ActionErrors errors = new ActionErrors();
/*  53 */     String hostName = null;
/*  54 */     String portNumber = null;
/*  55 */     String camID = "";
/*     */     try
/*     */     {
/*  58 */       camID = request.getParameter("camid");
/*     */       
/*     */ 
/*  61 */       String resourceID = request.getParameter("resourceid");
/*  62 */       String screenID = request.getParameter("screenid");
/*     */       
/*  64 */       request.setAttribute("camid", camID);
/*  65 */       List list = CAMDBUtil.getCAMDetails(camID);
/*  66 */       request.setAttribute("camname", list.get(0));
/*  67 */       request.setAttribute("camdesc", list.get(2));
/*     */       
/*  69 */       request.setAttribute("haid", request.getParameter("haid"));
/*  70 */       request.setAttribute("screenid", screenID);
/*  71 */       String mibName = (String)((DynaActionForm)form).get("mibname");
/*     */       try {
/*  73 */         String path = System.getProperty("webnms.rootdir") + File.separator + File.separator + "mibs" + File.separator + mibName;
/*  74 */         MibOperations mibop = SNMPUtil.getMibOperation();
/*  75 */         MibModule mod = mibop.loadMibModule(path);
/*  76 */         mibName = mod.getName();
/*  77 */         AMLog.debug("CAMSNMPAction: MIB File Name from its defination: " + mibName);
/*     */       } catch (Exception en) {
/*  79 */         AMLog.debug("------------- Error while getting the MIB DEFINITION NAME--- Mib file: " + mibName + " ------------------");
/*     */       }
/*  81 */       String snmpOID = (String)((DynaActionForm)form).get("snmpoid");
/*     */       
/*  83 */       String oidDisplayName = (String)((DynaActionForm)form).get("oiddisplayname");
/*     */       
/*  85 */       String isFromResourcePage = request.getParameter("isfromresourcepage");
/*  86 */       request.setAttribute("isfromresourcepage", isFromResourcePage);
/*     */       
/*  88 */       Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resourceID));
/*     */       
/*     */ 
/*     */ 
/*  92 */       String resourceType = request.getParameter("resourcetype");
/*     */       
/*     */ 
/*  95 */       hostName = (String)agentInfo.get("TARGETNAME");
/*  96 */       portNumber = (String)agentInfo.get("PORTNO");
/*  97 */       int port = Integer.parseInt(portNumber);
/*     */       
/*  99 */       agentInfo.put("RESOURCEID", resourceID);
/* 100 */       agentInfo.put("camID", camID);
/* 101 */       agentInfo.put("screenID", screenID);
/*     */       
/* 103 */       agentInfo.put("hostName", hostName);
/* 104 */       agentInfo.put("port", String.valueOf(port));
/* 105 */       agentInfo.put("attributesAdded", new ArrayList());
/*     */       
/* 107 */       String[] name = oidDisplayName.split(",");
/* 108 */       String[] oid = snmpOID.split(",");
/* 109 */       Map map; for (int i = 0; i < name.length; i++) {
/* 110 */         agentInfo.put("ORIG_snmpOID", oid[i]);
/* 111 */         map = CAMSNMPUtil.handleOIDAction(agentInfo, mibName, oid[i], name[i], false);
/*     */       }
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
/*     */ 
/* 136 */       CAMServerUtil.checkAndScheduleCAMJob(Integer.parseInt(resourceID));
/* 137 */       request.setAttribute("hostname", hostName);
/* 138 */       request.setAttribute("portnumber", portNumber);
/* 139 */       request.setAttribute("resourcetype", resourceType);
/* 140 */       request.setAttribute("resourceid", resourceID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 160 */       ee.printStackTrace();
/* 161 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Error ocurred while adding the specified MIB Objects. Please check if you have specified the correct SNMP Object Identifer and that the MIB is valid."));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 167 */     if (!errors.isEmpty()) {
/* 168 */       saveErrors(request, errors);
/* 169 */       return mapping.findForward("snmp");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 174 */     String url = "/ShowCAM.do?method=configureScreen&screenid=" + request.getParameter("screenid") + "&camid=" + camID + "&haid=" + request.getParameter("haid");
/* 175 */     if ((request.getParameter("isfromresourcepage") != null) && (request.getParameter("isfromresourcepage").equalsIgnoreCase("true")))
/*     */     {
/* 177 */       url = url + "&isfromresourcepage=true";
/*     */     }
/* 179 */     return new ActionForward(url, true);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\CAMSNMPAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */