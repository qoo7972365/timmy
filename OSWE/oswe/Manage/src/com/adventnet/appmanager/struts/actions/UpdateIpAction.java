/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.appmanager.util.StartUtil;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.PerformDataCollection;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.model.CollectData;
/*     */ import com.adventnet.nms.applnfw.discovery.server.model.InetService;
/*     */ import com.adventnet.nms.topodb.ManagedObject;
/*     */ import com.adventnet.nms.topodb.TopoAPI;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UpdateIpAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward updateIPAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  44 */     Hashtable idAndOldIpht = new Hashtable();
/*  45 */     ActionMessages messages = new ActionMessages();
/*  46 */     String resids = request.getParameter("resids");
/*     */     
/*  48 */     String refreshHost = request.getParameter("refreshHost");
/*  49 */     String replaceHost = request.getParameter("replaceHost");
/*  50 */     StartUtil.printStr("UpdateIpAction::::::::::refreshHost:::" + refreshHost);
/*  51 */     StartUtil.printStr("UpdateIpAction::::::::::replaceHost:::" + replaceHost);
/*  52 */     ResultSet rs = null;
/*  53 */     ArrayList resourceids = new ArrayList();
/*  54 */     idAndOldIpht = getResIdsAndOldIps(resids);
/*  55 */     StringTokenizer nameAfterComma = new StringTokenizer(resids, ",");
/*  56 */     if (resids == null)
/*     */     {
/*  58 */       saveMessages(request, messages);
/*  59 */       request.setAttribute("SUCCESS", "false");
/*  60 */       return mapping.findForward("failed");
/*     */     }
/*     */     
/*     */ 
/*  64 */     int count = 0;
/*  65 */     while (nameAfterComma.hasMoreTokens())
/*     */     {
/*  67 */       String resid = nameAfterComma.nextToken();
/*  68 */       resourceids.add(resid);
/*     */     }
/*     */     try
/*     */     {
/*  72 */       ArrayList<String> updateQueriesToAdmin = new ArrayList();
/*  73 */       for (Enumeration e = idAndOldIpht.keys(); e.hasMoreElements();)
/*     */       {
/*  75 */         String key = (String)e.nextElement();
/*  76 */         String value = (String)idAndOldIpht.get(key);
/*  77 */         String newIp = request.getParameter(key);
/*  78 */         String oldIp = value;
/*  79 */         System.out.println("UpdateIpAction::::::::::OLDIP=" + oldIp + ":::::::NEWIP=" + newIp);
/*  80 */         if (newIp != null)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  86 */           String query = null;
/*  87 */           String oldHostName = null;
/*  88 */           query = "select RESOURCENAME  from CollectData  where TARGETADDRESS='" + oldIp + "' and COMPONENTNAME='HOST'";
/*  89 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  90 */           if (rs.next()) {
/*  91 */             oldHostName = rs.getString("RESOURCENAME");
/*     */           }
/*     */           
/*  94 */           updateTables(key, newIp, oldIp);
/*     */           
/*  96 */           if (Constants.sqlManager) {
/*  97 */             AMConnectionPool.executeUpdateStmt("update TopoObject set IPADDRESS='" + newIp + "' where IPADDRESS='" + oldIp + "'");
/*  98 */             AMConnectionPool.executeUpdateStmt("update InetService set TARGETADDRESS='" + newIp + "' where TARGETADDRESS='" + oldIp + "'");
/*  99 */             AMConnectionPool.executeUpdateStmt("update CollectData set TARGETADDRESS='" + newIp + "' where TARGETADDRESS='" + oldIp + "'");
/* 100 */           } else if ((!oldIp.equals(newIp)) && (EnterpriseUtil.isManagedServer)) {
/* 101 */             StringBuffer buff = new StringBuffer();
/* 102 */             buff.append("update TopoObject set IPADDRESS='").append(newIp).append("'").append(" where IPADDRESS='").append(oldIp).append("'").append(";\n");
/* 103 */             buff.append("update InetService set TARGETADDRESS='").append(newIp).append("'").append(" where TARGETADDRESS='").append(oldIp).append("'").append(";\n");
/* 104 */             buff.append("update CollectData set TARGETADDRESS='").append(newIp).append("'").append(" where TARGETADDRESS='").append(oldIp).append("'");
/* 105 */             updateQueriesToAdmin.add(buff.toString());
/*     */           }
/*     */           
/*     */           try
/*     */           {
/* 110 */             if ((refreshHost.equals("true")) && (oldHostName != null)) {
/* 111 */               getUpdateHostName(InetAddress.getByName(newIp).getCanonicalHostName(), oldHostName);
/*     */             }
/* 113 */             if ((replaceHost.equals("true")) && (oldHostName != null)) {
/* 114 */               getUpdateHostName(newIp, oldHostName);
/*     */             }
/*     */           } catch (Exception eip) {
/* 117 */             eip.printStackTrace();
/*     */           }
/* 119 */           count++;
/*     */         }
/*     */       }
/* 122 */       int size = updateQueriesToAdmin.size();
/* 123 */       if (size > 0) {
/* 124 */         StartUtil.printStr("updateQueriesToAdmin:::: " + updateQueriesToAdmin);
/* 125 */         StringBuffer buff = new StringBuffer();
/* 126 */         for (int i = 0; i < size; i++) {
/* 127 */           buff.append((String)updateQueriesToAdmin.get(i));
/* 128 */           if (i != size - 1) {
/* 129 */             buff.append(";\n");
/*     */           }
/*     */         }
/* 132 */         EnterpriseUtil.addUpdateQueryToFile(buff.toString());
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 142 */         if (rs != null) {
/* 143 */           rs.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 146 */         e.printStackTrace();
/*     */       }
/*     */       
/* 149 */       if (count == 0) {
/*     */         break label987;
/*     */       }
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/* 137 */       System.out.println("\n\nUnable to Update IP: check for some exceptions");
/* 138 */       exception.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       try {
/* 142 */         if (rs != null) {
/* 143 */           rs.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 146 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 151 */     String Count = "";
/* 152 */     Count = Count + count;
/* 153 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.updateip.edited", new String[] { Count, OEMUtil.getOEMString("product.name") })));
/* 154 */     saveMessages(request, messages);
/* 155 */     request.setAttribute("SUCCESS", "true");
/* 156 */     return mapping.findForward("success");
/*     */     
/*     */     label987:
/*     */     
/* 160 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.updateip.unable")));
/* 161 */     saveMessages(request, messages);
/* 162 */     request.setAttribute("SUCCESS", "false");
/* 163 */     return mapping.findForward("failed");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Hashtable getResIdsAndOldIps(String resids)
/*     */   {
/* 171 */     ridsAndOldIpsht = new Hashtable();
/* 172 */     Statement statement = null;
/* 173 */     Connection connection = null;
/* 174 */     ResultSet results = null;
/*     */     try {
/* 176 */       connection = AMConnectionPool.getConnection();
/* 177 */       statement = connection.createStatement();
/* 178 */       String resids_forquery = "(";
/* 179 */       StringTokenizer nameAfterComma = new StringTokenizer(resids, ",");
/* 180 */       while (nameAfterComma.hasMoreTokens()) {
/* 181 */         String temp = nameAfterComma.nextToken();
/* 182 */         resids_forquery = resids_forquery + "" + temp + ", ";
/*     */       }
/* 184 */       if (!resids_forquery.equals("(")) {
/* 185 */         resids_forquery = resids_forquery.substring(0, resids_forquery.length() - 2);
/* 186 */         resids_forquery = resids_forquery + ")";
/*     */       }
/*     */       
/* 189 */       if (Constants.sqlManager)
/*     */       {
/*     */ 
/* 192 */         String ridsAndOldIpQuery = "select AM_ManagedObject.RESOURCEID  from AM_ManagedObject, (select TARGETADDRESS from collectdata, AM_ManagedObject where RESOURCEID in " + resids_forquery + " and AM_ManagedObject.RESOURCENAME=collectdata.RESOURCENAME) c, collectdata c1 where c1.TARGETADDRESS=c.TARGETADDRESS and c1.COMPONENTNAME='HOST' and AM_ManagedObject.RESOURCENAME=c1.RESOURCENAME";
/* 193 */         results = AMConnectionPool.executeQueryStmt(ridsAndOldIpQuery);
/* 194 */         String condition = " ";
/* 195 */         while (results.next()) {
/* 196 */           condition = condition + results.getString("RESOURCEID") + ",";
/*     */         }
/* 198 */         resids_forquery = "(";
/* 199 */         nameAfterComma = new StringTokenizer(condition, ",");
/* 200 */         while (nameAfterComma.hasMoreTokens()) {
/* 201 */           String temp = nameAfterComma.nextToken();
/* 202 */           resids_forquery = resids_forquery + "" + temp + ", ";
/*     */         }
/* 204 */         if (!resids_forquery.equals("(")) {
/* 205 */           resids_forquery = resids_forquery.substring(0, resids_forquery.length() - 2);
/* 206 */           resids_forquery = resids_forquery + ")";
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 214 */       String ridsAndOldIpQuery = "select RESOURCEID, IPADDRESS from AM_ManagedObject join TopoObject on AM_ManagedObject.RESOURCENAME=TopoObject.NAME where RESOURCEID in" + resids_forquery + "";
/* 215 */       results = AMConnectionPool.executeQueryStmt(ridsAndOldIpQuery);
/*     */       
/*     */ 
/* 218 */       while (results.next()) {
/* 219 */         String rid = results.getString("RESOURCEID");
/* 220 */         String oldIp = results.getString("IPADDRESS");
/* 221 */         System.out.println("UpdateIpAction:::::::::::::rid=" + rid + "::::::oldIp=" + oldIp);
/* 222 */         ridsAndOldIpsht.put(rid, oldIp);
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
/* 246 */       return ridsAndOldIpsht;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 225 */       System.out.println("Exception in getResIdsAndOldIps.");
/* 226 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 232 */         if (statement != null)
/*     */         {
/* 234 */           statement.close();
/*     */         }
/* 236 */         if (results != null)
/*     */         {
/* 238 */           results.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 242 */         System.out.println("EXCEPTION OCCURED IN UPDATEIPACTION-STATEMENT.CLOSE");
/* 243 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void updateTables(String resourceid, String newIp, String oldIp)
/*     */   {
/* 252 */     ResultSet set = null;
/* 253 */     ResultSet set1 = null;
/* 254 */     ResultSet set2 = null;
/* 255 */     Properties props = null;
/*     */     
/*     */     try
/*     */     {
/* 259 */       String query = "select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + resourceid;
/* 260 */       set = AMConnectionPool.executeQueryStmt(query);
/* 261 */       String resourceName = null;
/* 262 */       if (set.next())
/*     */       {
/* 264 */         resourceName = set.getString(1);
/*     */       }
/* 266 */       TopoAPI tapi = (TopoAPI)NmsUtil.getAPI("TopoAPI");
/* 267 */       ManagedObject mo = tapi.getByName(resourceName);
/* 268 */       if ((mo != null) && (tapi != null))
/*     */       {
/* 270 */         props = mo.getProperties();
/* 271 */         props.setProperty("ipAddress", newIp);
/* 272 */         mo.setProperties(props);
/* 273 */         tapi.updateObject(mo, true, false);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 278 */       String query2 = "select NAME from InetService where TARGETADDRESS='" + oldIp + "'";
/* 279 */       set2 = AMConnectionPool.executeQueryStmt(query2);
/* 280 */       String resourceName2 = null;
/* 281 */       while (set2.next())
/*     */       {
/* 283 */         resourceName2 = set2.getString("NAME");
/* 284 */         InetService ins = (InetService)tapi.getByName(resourceName2);
/* 285 */         if ((ins != null) && (tapi != null))
/*     */         {
/* 287 */           ins.setTargetAddress(newIp);
/* 288 */           tapi.updateObject(ins, true, false);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 294 */       ApplnDataCollectionAPI aapi = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/* 295 */       String query1 = "select RESOURCENAME,COMPONENTNAME from CollectData where TARGETADDRESS='" + oldIp + "'";
/* 296 */       String resourceName1 = null;
/* 297 */       String componentName = null;
/* 298 */       set1 = AMConnectionPool.executeQueryStmt(query1);
/* 299 */       while (set1.next())
/*     */       {
/* 301 */         resourceName1 = set1.getString("RESOURCENAME");
/* 302 */         componentName = set1.getString("COMPONENTNAME");
/* 303 */         CollectData cdata = aapi.getCollectData(resourceName1, componentName);
/* 304 */         if ((cdata != null) && (aapi != null))
/*     */         {
/* 306 */           cdata.setTargetAddress(newIp);
/* 307 */           aapi.updateCollectData(cdata);
/*     */         }
/*     */       }
/*     */       return;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 314 */       System.out.println("EXCEPTION OCCURED IN UPDATEIPACTION::" + ex);
/* 315 */       ex.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 321 */         if (set != null)
/*     */         {
/* 323 */           set.close();
/*     */         }
/* 325 */         if (set1 != null)
/*     */         {
/* 327 */           set1.close();
/*     */         }
/* 329 */         if (set2 != null)
/*     */         {
/* 331 */           set2.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 336 */         System.out.println("EXCEPTION OCCURED IN UPDATEIPACTION-SET.CLOSE");
/* 337 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void getUpdateHostName(String newHost, String host)
/*     */   {
/* 345 */     Statement s = null;
/*     */     try {
/* 347 */       s = AMConnectionPool.getConnection().createStatement();
/*     */       
/* 349 */       String str1 = "update TopoObject set NAME='" + newHost + "' where NAME='" + host + "'";
/* 350 */       String str2 = "update Node set NAME='" + newHost + "' where NAME='" + host + "'";
/* 351 */       String str3 = "update InetService set TARGETNAME='" + newHost + "' where TARGETNAME='" + host + "'";
/* 352 */       String str4 = "update CollectData set RESOURCENAME='" + newHost + "' where RESOURCENAME='" + host + "'";
/* 353 */       String str5 = "update IpAddress set PARENTNODE='" + newHost + "' where PARENTNODE='" + host + "'";
/* 354 */       String str6 = "update AM_ManagedObject set RESOURCENAME=REPLACE(RESOURCENAME,'" + host + "','" + newHost + "'), DISPLAYNAME=REPLACE(DISPLAYNAME,'" + host + "','" + newHost + "') where RESOURCENAME like '" + host + "%%%'";
/* 355 */       String str7 = "update ManagedObject set NAME='" + newHost + "' where NAME='" + host + "'";
/* 356 */       String str8 = "update HostDetails set RESOURCENAME='" + newHost + "' where RESOURCENAME='" + host + "'";
/* 357 */       s.addBatch(str1);
/* 358 */       s.addBatch(str2);
/* 359 */       s.addBatch(str3);
/* 360 */       s.addBatch(str4);
/* 361 */       s.addBatch(str5);
/* 362 */       s.addBatch(str6);
/* 363 */       s.addBatch(str7);
/* 364 */       s.addBatch(str8);
/* 365 */       s.executeBatch();
/* 366 */       AMLog.debug("HostName has been changed from " + host + " to " + newHost);
/*     */       
/*     */ 
/* 369 */       updateScheduleTaskHashTable(newHost, host);
/*     */       
/* 371 */       if ((!host.equalsIgnoreCase(newHost)) && (EnterpriseUtil.isManagedServer)) {
/* 372 */         StringBuffer queries = new StringBuffer();
/* 373 */         queries.append(str1).append(";").append("\n");
/* 374 */         queries.append(str2).append(";").append("\n");
/* 375 */         queries.append(str3).append(";").append("\n");
/* 376 */         queries.append(str4).append(";").append("\n");
/* 377 */         queries.append(str5).append(";").append("\n");
/* 378 */         queries.append(str6).append(";").append("\n");
/* 379 */         queries.append(str7).append(";").append("\n");
/* 380 */         queries.append(str8);
/* 381 */         EnterpriseUtil.addUpdateQueryToFile(queries.toString());
/*     */       }
/*     */       return;
/*     */     } catch (Exception e) {
/* 385 */       AMLog.debug("Operation is failed to update hostname");
/* 386 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       try {
/* 390 */         if (s != null) {
/* 391 */           s.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 394 */         System.out.println("EXCEPTION OCCURED IN UPDATEIPACTION-SET.CLOSE");
/* 395 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateScheduleTaskHashTable(String newHost, String oldHost)
/*     */   {
/* 409 */     AMLog.debug("HOST UPDATE IP: [PerfomnDataCollection object update] oldhost: " + oldHost + " \tnewhost: " + newHost);
/* 410 */     if (oldHost.equals(newHost)) {
/* 411 */       return;
/*     */     }
/* 413 */     int pollInterval = 0;
/* 414 */     String resourceName = "";
/* 415 */     String componentName = "";
/* 416 */     ResultSet rs = null;
/*     */     try {
/* 418 */       String qry = "SELECT RESOURCENAME,COMPONENTNAME,POLLINTERVAL FROM CollectData WHERE RESOURCENAME='" + newHost + "'";
/* 419 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 420 */       if (rs.next()) {
/* 421 */         resourceName = rs.getString(1);
/* 422 */         componentName = rs.getString(2);
/* 423 */         pollInterval = rs.getInt(3);
/*     */       }
/* 425 */       Hashtable tasks = PerformDataCollection.getScheduledTasks();
/* 426 */       if ((resourceName.length() > 0) && (componentName.length() > 0) && (pollInterval > 0)) {
/* 427 */         PerformDataCollection pdc = (PerformDataCollection)tasks.get(oldHost + "\t" + componentName);
/* 428 */         pdc.stopMonitoring = true;
/* 429 */         new PerformDataCollection(resourceName, componentName, pollInterval, false);
/*     */       }
/* 431 */       PerformDataCollection.getScheduledTasks();
/*     */     } catch (Exception ex) {
/* 433 */       ex.printStackTrace();
/*     */     } finally {
/* 435 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\UpdateIpAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */