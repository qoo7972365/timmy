/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*     */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*     */ import com.adventnet.appmanager.server.dao.AMManagedObjectDao;
/*     */ import com.adventnet.appmanager.server.framework.AMServerFramework;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.management.scheduler.Scheduler;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
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
/*     */ public class PingAction
/*     */   extends DispatchAction
/*     */ {
/*  50 */   private ManagedApplication mo = new ManagedApplication();
/*  51 */   protected Scheduler schedular = null;
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
/*     */   public ActionForward createMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 157 */     return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=Ping Monitor");
/*     */   }
/*     */   
/*     */   public int createMO(String hostIp)
/*     */   {
/* 162 */     int resourceid = -1;
/*     */     try {
/* 164 */       AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/* 165 */       AMManagedObject ammo = new AMManagedObject();
/* 166 */       ammo.setRESOURCENAME(hostIp);
/* 167 */       ammo.setType("Ping Monitor");
/* 168 */       ammo.setDISPLAYNAME(hostIp);
/* 169 */       ammo.setDESCRIPTION("Ping Monitor");
/* 170 */       dao.create(ammo);
/* 171 */       resourceid = ammo.getRESOURCEID();
/* 172 */       AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 173 */       adder.addDependentAttributes(4, resourceid);
/*     */     }
/*     */     catch (Exception e) {
/* 176 */       e.printStackTrace();
/*     */     }
/* 178 */     return resourceid;
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
/*     */   public ActionForward updatePing(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 248 */     return new ActionForward("", true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showPingMonitorDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 259 */     String path = null;
/* 260 */     ActionMessages messages = new ActionMessages();
/* 261 */     ActionErrors errors = new ActionErrors();
/*     */     
/* 263 */     long collectiontime = 0L;
/*     */     try
/*     */     {
/* 266 */       String haid = request.getParameter("haid");
/* 267 */       String applicationName = request.getParameter("name");
/* 268 */       String resourceid = request.getParameter("resourceid");
/* 269 */       String resourcename = request.getParameter("resourcename");
/*     */       
/* 271 */       String resourcetype = request.getParameter("type");
/* 272 */       String moname = request.getParameter("moname");
/* 273 */       ManagedApplication mo = new ManagedApplication();
/* 274 */       String pollinterval = (String)request.getAttribute("reloadperiod");
/*     */       
/* 276 */       if ((pollinterval == null) || (pollinterval.equals("")))
/*     */       {
/* 278 */         int polling = 300;
/* 279 */         pollinterval = String.valueOf(polling);
/*     */       }
/* 281 */       request.setAttribute("reloadperiod", pollinterval);
/* 282 */       HashMap map = new HashMap();
/* 283 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 288 */       ResultSet rsedit = null;
/* 289 */       String sltqry = "select DISPLAYNAME,TIMEOUT,POLLINTERVAL,ERRORMSG from AM_PING,AM_ManagedObject where AM_PING.RESOURCEID=" + resourceid + " and AM_ManagedObject.RESOURCEID=AM_PING.RESOURCEID";
/* 290 */       int poll = 0;
/* 291 */       String error = null;
/*     */       try {
/* 293 */         rsedit = AMConnectionPool.executeQueryStmt(sltqry);
/* 294 */         if (rsedit.next()) {
/* 295 */           String res = rsedit.getString("DISPLAYNAME");
/*     */           
/* 297 */           int time = rsedit.getInt("TIMEOUT");
/* 298 */           poll = rsedit.getInt("POLLINTERVAL");
/* 299 */           error = rsedit.getString("ERRORMSG");
/* 300 */           ((AMActionForm)form).setHostname(rsedit.getString("DISPLAYNAME"));
/* 301 */           ((AMActionForm)form).setPollInterval(rsedit.getInt("POLLINTERVAL") / 60);
/*     */           
/* 303 */           ((AMActionForm)form).setPtimeout(rsedit.getInt("TIMEOUT"));
/*     */         }
/*     */         
/*     */ 
/* 307 */         rsedit.close();
/*     */       }
/*     */       catch (Exception ex) {
/* 310 */         ex.printStackTrace();
/*     */       }
/*     */       
/* 313 */       String pmsg = "";
/* 314 */       if (error.equals("Exception while parsing the Ping Output")) {
/* 315 */         pmsg = FormatUtil.getString("am.webclient.parsing.error") + "." + FormatUtil.getString("am.webclient.fsm.support", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/* 316 */         errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/* 317 */         saveErrors(request, errors);
/* 318 */       } else if (error.indexOf("Packet Statistics") != -1)
/*     */       {
/*     */ 
/* 321 */         pmsg = FormatUtil.getString("am.webclient.ping.error");
/* 322 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(pmsg));
/* 323 */         saveMessages(request, messages);
/*     */ 
/*     */ 
/*     */       }
/* 327 */       else if (error.indexOf("Ping output") != -1)
/*     */       {
/*     */ 
/* 330 */         pmsg = FormatUtil.getString("am.webclient.ping.error1");
/* 331 */         errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/* 332 */         saveErrors(request, errors);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 338 */       String dcqry = "select max(COLLECTIONTIME) from AM_PINGDATA where AM_PINGDATA.RESOURCEID=" + resourceid;
/* 339 */       ResultSet rsdc = null;
/* 340 */       long dctime = 0L;
/*     */       try {
/* 342 */         rsdc = AMConnectionPool.executeQueryStmt(dcqry);
/* 343 */         if (rsdc.next()) {
/* 344 */           dctime = rsdc.getLong(1);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 349 */         ex.printStackTrace();
/*     */       }
/*     */       
/* 352 */       if (dctime != 0L)
/*     */       {
/* 354 */         if (dctime > AMServerFramework.serverStartupTime)
/*     */         {
/* 356 */           map.put("LASTDC", Long.valueOf(dctime));
/* 357 */           map.put("NEXTDC", Long.valueOf(dctime + poll * 1000));
/*     */         }
/*     */         else
/*     */         {
/* 361 */           map.put("LASTDC", Long.valueOf(dctime));
/* 362 */           map.put("NEXTDC", Long.valueOf(AMServerFramework.serverStartupTime + poll * 1000));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 368 */       request.setAttribute("systeminfo", map);
/*     */       
/* 370 */       ResultSet rs = null;
/* 371 */       ResultSet col_time = null;
/* 372 */       String coll_query = "select max(collectiontime) from AM_ManagedObjectData where RESID=" + resourceid;
/*     */       try
/*     */       {
/* 375 */         rs = AMConnectionPool.executeQueryStmt(coll_query);
/* 376 */         if (rs.next())
/*     */         {
/* 378 */           collectiontime = rs.getLong(1);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 385 */         e.printStackTrace();
/*     */       }
/* 387 */       rs.close();
/*     */       
/*     */ 
/* 390 */       Hashtable pingData = new Hashtable();
/* 391 */       String dataqry = "select PACK_SENT,PACK_RCVD,PACKLOSS_PERCENT,RTT from AM_PINGDATA where COLLECTIONTIME=" + collectiontime + " and RESOURCEID=" + resourceid;
/* 392 */       ResultSet rs1 = null;
/*     */       try
/*     */       {
/* 395 */         rs1 = AMConnectionPool.executeQueryStmt(dataqry);
/* 396 */         if (rs1.next())
/*     */         {
/* 398 */           pingData.put("packsent", rs1.getString("PACK_SENT"));
/* 399 */           pingData.put("packrecvd", rs1.getString("PACK_RCVD"));
/* 400 */           pingData.put("packloss", rs1.getString("PACKLOSS_PERCENT"));
/* 401 */           pingData.put("rtt", rs1.getString("RTT"));
/*     */         }
/*     */       }
/*     */       catch (Exception ee) {
/* 405 */         ee.printStackTrace();
/*     */       }
/*     */       
/* 408 */       request.setAttribute("pingData", pingData);
/* 409 */       request.setAttribute("moname", moname);
/*     */     }
/*     */     catch (Exception ex) {
/* 412 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 415 */     request.setAttribute("reconfigure", "false");
/* 416 */     return new ActionForward("/jsp/pingDetails.jsp");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\PingAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */