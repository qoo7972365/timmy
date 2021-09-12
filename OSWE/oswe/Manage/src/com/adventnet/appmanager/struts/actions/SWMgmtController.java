/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.SWMgmtUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SWMgmtController
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward initiateDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  34 */     ArrayList list = new ArrayList();
/*  35 */     String[] selectedManagedServers = request.getParameterValues("checkbox");
/*  36 */     int len = selectedManagedServers.length;
/*  37 */     for (int j = 0; j < len; j++)
/*     */     {
/*  39 */       list.add(selectedManagedServers[j]);
/*     */     }
/*     */     
/*  42 */     String masIds = list.toString();
/*  43 */     masIds = masIds.substring(1, masIds.length() - 1);
/*     */     
/*     */ 
/*     */ 
/*  47 */     AMConnectionPool.executeUpdateStmt("update SWDetails set DOWNLOADSTATUS = 'STARTED' where RUNNINGVERSION < NEWVERSION and DOWNLOADSTATUS != 'IN_PROGRESS' and DOWNLOADSTATUS != 'COMPLETED' and SERVERID in (" + masIds + ")");
/*     */     
/*     */     try
/*     */     {
/*  51 */       String secIds = getSecondaryMasIds(masIds);
/*  52 */       if ((secIds != null) && (secIds.length() > 0)) {
/*  53 */         AMConnectionPool.executeUpdateStmt("update SWDetails set DOWNLOADSTATUS = 'STARTED' where RUNNINGVERSION < NEWVERSION and DOWNLOADSTATUS != 'IN_PROGRESS' and DOWNLOADSTATUS != 'COMPLETED' and SERVERID in (" + secIds + ")");
/*     */       }
/*     */     } catch (Exception ex) {
/*  56 */       ex.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/*  60 */     if (Constants.isIt360)
/*     */     {
/*  62 */       return new ActionForward("/showIT360Tile.do?TileName=IT360.ProbeSettings&reqForAdminLayout=true&ifsrc=AppManager&rebrandId=-1");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  67 */     response.sendRedirect("/adminAction.do?method=showManagedServers");
/*  68 */     return new ActionForward("/adminAction.do?method=showManagedServers");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward initiateUpgrade(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  78 */     ArrayList list = new ArrayList();
/*  79 */     String[] selectedManagedServers = request.getParameterValues("checkbox");
/*  80 */     boolean backupRequired = Boolean.parseBoolean((String)request.getAttribute("backupRequired"));
/*  81 */     int len = selectedManagedServers.length;
/*  82 */     for (int j = 0; j < len; j++)
/*     */     {
/*  84 */       list.add(selectedManagedServers[j]);
/*     */     }
/*  86 */     String masIds = list.toString();
/*  87 */     masIds = masIds.substring(1, masIds.length() - 1);
/*     */     
/*  89 */     AMConnectionPool.executeUpdateStmt("update SWDetails set UPGRADESTATUS = 'STARTED', BACKUPREQUIRED='" + backupRequired + "' where RUNNINGVERSION < NEWVERSION and DOWNLOADSTATUS = 'COMPLETED' and SERVERID in (" + masIds + ")");
/*     */     
/*     */     try
/*     */     {
/*  93 */       String secIds = getSecondaryMasIds(masIds);
/*  94 */       if ((secIds != null) && (secIds.length() > 0)) {
/*  95 */         AMConnectionPool.executeUpdateStmt("update SWDetails set UPGRADESTATUS = 'STARTED', BACKUPREQUIRED='" + backupRequired + "' where RUNNINGVERSION < NEWVERSION and DOWNLOADSTATUS = 'COMPLETED' and SERVERID in (" + secIds + ")");
/*     */       }
/*     */     } catch (Exception ex) {
/*  98 */       ex.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/* 102 */     if (Constants.isIt360)
/*     */     {
/* 104 */       return new ActionForward("/showIT360Tile.do?TileName=IT360.ProbeSettings&reqForAdminLayout=true&ifsrc=AppManager&rebrandId=-1");
/*     */     }
/*     */     
/*     */ 
/* 108 */     response.sendRedirect("/adminAction.do?method=showManagedServers");
/* 109 */     return new ActionForward("/adminAction.do?method=showManagedServers");
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward initiateCentralDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 116 */     String versionInWebSite = SWMgmtUtil.getCompatibleLatestVersionFromWebSite();
/* 117 */     String downloadStatus = "STARTED";
/* 118 */     String newVersion = versionInWebSite;
/* 119 */     String chkSum = SWMgmtUtil.getchkSumFromWebSite();
/* 120 */     String newPkgname = SWMgmtUtil.getLatestPkgNameFromWebSite();
/* 121 */     String webSiteLocation = SWMgmtUtil.getPkgLocationWebSite();
/* 122 */     String synchStatus = "DISABLED";
/* 123 */     String serverID = SWMgmtUtil.getServerID() + "";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 131 */     if (DBUtil.getGlobalConfigValueasBoolean("easyUpgrade"))
/*     */     {
/* 133 */       SWMgmtUtil.doSWDownloadCheck(downloadStatus, newVersion, chkSum, newPkgname, webSiteLocation, synchStatus, serverID);
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
/* 144 */     if (Constants.isIt360)
/*     */     {
/* 146 */       return new ActionForward("/showIT360Tile.do?TileName=IT360.ProbeSettings&reqForAdminLayout=true&ifsrc=AppManager&rebrandId=-1");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 151 */     response.sendRedirect("/jsp/About.jsp");
/* 152 */     return new ActionForward("/jsp/About.jsp");
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward initiateCentralUpgrade(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 159 */     String versionInWebSite = SWMgmtUtil.getCompatibleLatestVersionFromWebSite();
/* 160 */     String newVersion = versionInWebSite;
/* 161 */     String chkSum = SWMgmtUtil.getchkSumFromWebSite();
/* 162 */     String newPkgname = SWMgmtUtil.getLatestPkgNameFromWebSite();
/* 163 */     String webSiteLocation = SWMgmtUtil.getPkgLocationWebSite();
/* 164 */     String synchStatus = "DISABLED";
/* 165 */     String serverID = SWMgmtUtil.getServerID() + "";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 171 */     String upgradeStatus = "STARTED";
/* 172 */     boolean backupRequired = Boolean.parseBoolean(request.getParameter("backupRequired"));
/*     */     
/* 174 */     AMConnectionPool.executeUpdateStmt("update SWDetails set UPGRADESTATUS = 'STARTED', BACKUPREQUIRED='" + backupRequired + "' where SERVERID=" + serverID);
/*     */     
/* 176 */     if (DBQueryUtil.isMssql())
/*     */     {
/* 178 */       SWMgmtUtil.doSWUpgradeCheck(upgradeStatus, newVersion, chkSum, newPkgname, synchStatus, serverID, false);
/*     */     }
/*     */     else
/*     */     {
/* 182 */       SWMgmtUtil.doSWUpgradeCheck(upgradeStatus, newVersion, chkSum, newPkgname, synchStatus, serverID, backupRequired);
/*     */     }
/*     */     
/* 185 */     if (Constants.isIt360)
/*     */     {
/* 187 */       return new ActionForward("/showIT360Tile.do?TileName=IT360.ProbeSettings&reqForAdminLayout=true&ifsrc=AppManager&rebrandId=-1");
/*     */     }
/*     */     
/*     */ 
/* 191 */     response.sendRedirect("/jsp/About.jsp");
/* 192 */     return new ActionForward("/jsp/About.jsp");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String getSecondaryMasIds(String primaryMasIds)
/*     */   {
/*     */     try
/*     */     {
/* 206 */       StringBuilder secIds = new StringBuilder();
/* 207 */       StringTokenizer tokenObj = new StringTokenizer(primaryMasIds, ",");
/* 208 */       while (tokenObj.hasMoreTokens()) {
/* 209 */         String tok = tokenObj.nextToken();
/* 210 */         secIds.append(Integer.parseInt(tok) + 10000).append(",");
/*     */       }
/* 212 */       secIds = secIds.deleteCharAt(secIds.length() - 1);
/* 213 */       return secIds.toString();
/*     */     } catch (Exception ex) {
/* 215 */       ex.printStackTrace();
/*     */     }
/* 217 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\SWMgmtController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */