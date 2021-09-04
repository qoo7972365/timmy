/*     */ package com.adventnet.appmanager.oracleas.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*     */ import com.adventnet.appmanager.server.oracleas.datacollection.ScheduleOracleASDataCollection;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ public class OracleASAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward showdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  30 */     ArrayList resIDs = new ArrayList();
/*  31 */     String resourceid = request.getParameter("resourceid");
/*  32 */     resIDs.add(resourceid);
/*  33 */     String oldquery = null;
/*  34 */     String query = "select DISPLAYNAME from AM_ManagedObject where resourceid=" + resourceid;
/*  35 */     ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  36 */     if (rs.next()) {
/*  37 */       ((AMActionForm)form).setDisplayname(rs.getString("DISPLAYNAME"));
/*     */     }
/*  39 */     rs.close();
/*     */     
/*  41 */     query = "select pollinterval from CollectData,AM_ManagedObject where CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=" + resourceid;
/*  42 */     rs = AMConnectionPool.executeQueryStmt(query);
/*  43 */     if (rs.next()) {
/*  44 */       ((AMActionForm)form).setPollInterval(rs.getInt("pollinterval") / 60);
/*     */     }
/*  46 */     rs.close();
/*     */     
/*  48 */     query = "select resourceid from AM_ManagedObject where type='OAS-OPMN-Process'";
/*  49 */     rs = AMConnectionPool.executeQueryStmt(query);
/*  50 */     while (rs.next())
/*     */     {
/*  52 */       resIDs.add(rs.getString("resourceid"));
/*     */     }
/*  54 */     rs.close();
/*     */     
/*  56 */     String poll = (String)request.getAttribute("reloadperiod");
/*  57 */     String webappid = request.getParameter("webappid");
/*     */     
/*  59 */     if (webappid == null)
/*     */     {
/*  61 */       HashMap map = null;
/*  62 */       if (poll == null) poll = "300";
/*  63 */       map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(poll));
/*     */       
/*  65 */       if (map != null)
/*     */       {
/*  67 */         request.setAttribute("systeminfo", map);
/*     */       }
/*  69 */       long maxcoltime = 0L;
/*     */       try {
/*  71 */         query = "select max(collectiontime) as maxtime from AM_OAS_HTTPServerData  where AM_OAS_HTTPServerData.RESOURCEID=" + resourceid;
/*  72 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  73 */         if (rs.next())
/*     */         {
/*  75 */           maxcoltime = rs.getLong("maxtime");
/*     */         }
/*     */       } catch (Exception e) {
/*  78 */         e.printStackTrace();
/*     */       } finally {
/*  80 */         closeResultSet(rs);
/*     */       }
/*     */       
/*     */ 
/*  84 */       query = "select * from AM_OAS_HTTPServerData where AM_OAS_HTTPServerData.RESOURCEID=" + resourceid + " and AM_OAS_HTTPServerData.COLLECTIONTIME=" + maxcoltime;
/*     */       
/*     */ 
/*  87 */       rs = null;
/*     */       try {
/*  89 */         long start = System.currentTimeMillis();
/*  90 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  91 */         if (rs.next()) {
/*  92 */           HashMap hsdata = new HashMap();
/*  93 */           hsdata.put("ACTIVECONNECTION", rs.getString("ACTIVECONNECTION"));
/*  94 */           hsdata.put("AVGCONNECTIONTIME", rs.getString("AVGCONNECTIONTIME"));
/*  95 */           hsdata.put("ACTIVEREQUEST", rs.getString("ACTIVEREQUEST"));
/*  96 */           hsdata.put("AVGREQUESTTIME", rs.getString("AVGREQUESTTIME"));
/*  97 */           hsdata.put("REQUESTTHROUGHPUT", rs.getString("REQUESTTHROUGHPUT"));
/*  98 */           hsdata.put("DATATHROUGHPUT", rs.getString("DATATHROUGHPUT"));
/*  99 */           hsdata.put("DATAPROCESSED", rs.getString("DATAPROCESSED"));
/*     */           
/* 101 */           request.setAttribute("hsdata", hsdata);
/*     */         }
/* 103 */         end = System.currentTimeMillis();
/*     */       }
/*     */       catch (Exception e) {}finally
/*     */       {
/*     */         long end;
/*     */         
/* 109 */         closeResultSet(rs);
/*     */       }
/* 111 */       long start = System.currentTimeMillis();
/* 112 */       query = "select max(collectiontime) as maxtime from AM_OAS_JVMData jd,AM_PARENTCHILDMAPPER pcm where jd.resourceid=pcm.childid and pcm.parentid=" + resourceid;
/* 113 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 114 */       maxcoltime = 0L;
/* 115 */       if (rs.next())
/*     */       {
/* 117 */         maxcoltime = rs.getLong("maxtime");
/*     */       }
/* 119 */       rs.close();
/*     */       
/* 121 */       query = "select mo.displayname,jd.* from AM_OAS_JVMData jd,AM_ManagedObject mo,AM_PARENTCHILDMAPPER pcm where mo.resourceid=jd.resourceid and jd.resourceid=pcm.childid and pcm.parentid=" + resourceid + " and jd.collectiontime=" + maxcoltime;
/* 122 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 123 */       ArrayList jvmdata = new ArrayList();
/* 124 */       while (rs.next())
/*     */       {
/* 126 */         Properties prop = new Properties();
/* 127 */         prop.setProperty("name", rs.getString("displayname"));
/* 128 */         prop.setProperty("resid", rs.getString("RESOURCEID"));
/* 129 */         prop.setProperty("activethreadgroups", rs.getString("ACTIVETHREADGROUPS"));
/* 130 */         prop.setProperty("activethreads", rs.getString("ACTIVETHREADS"));
/* 131 */         prop.setProperty("heapusage", rs.getString("HEAPUSAGE"));
/* 132 */         String open = rs.getString("OPENTRANSACTIONS");
/* 133 */         open = (open != null) && (Integer.parseInt(open) != -1) ? open : "-";
/* 134 */         prop.setProperty("open", open);
/* 135 */         String committed = rs.getString("COMMITTED");
/* 136 */         committed = (committed != null) && (Integer.parseInt(committed) != -1) ? committed : "-";
/* 137 */         prop.setProperty("committed", committed);
/* 138 */         String aborted = rs.getString("ABORTED");
/* 139 */         aborted = (aborted != null) && (Integer.parseInt(aborted) != -1) ? aborted : "-";
/* 140 */         prop.setProperty("aborted", aborted);
/* 141 */         String activeCon = rs.getString("ACTIVECONNECTION");
/* 142 */         activeCon = Integer.parseInt(activeCon) != -1 ? activeCon : "-";
/* 143 */         prop.setProperty("activeConnection", activeCon);
/* 144 */         String avgConTime = rs.getString("AVGCONNECTIONTIME");
/* 145 */         avgConTime = Integer.parseInt(avgConTime) != -1 ? avgConTime : "-";
/* 146 */         prop.setProperty("avgConnTime", avgConTime);
/* 147 */         jvmdata.add(prop);
/*     */       }
/* 149 */       rs.close();
/* 150 */       request.setAttribute("jvmdata", jvmdata);
/* 151 */       long end = System.currentTimeMillis();
/*     */       
/*     */ 
/* 154 */       query = "select max(COLLECTIONTIME) as maxtime from AM_OAS_WebAppData wd,AM_PARENTCHILDMAPPER pcm,AM_EAR ear,AM_WAR war,AM_ManagedObject pmo where wd.resourceid=war.warid and war.parentid=ear.earid and ear.parentid=pmo.resourceid and pmo.resourceid=pcm.childid and pcm.parentid=" + resourceid;
/* 155 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 156 */       maxcoltime = 0L;
/* 157 */       if (rs.next())
/*     */       {
/* 159 */         maxcoltime = rs.getLong("maxtime");
/*     */       }
/* 161 */       rs.close();
/*     */       
/* 163 */       oldquery = "select m2.DISPLAYNAME,EARNAME,m1.DISPLAYNAME as process,count(AM_Servlet.ID) as servletcount,AM_OAS_WebAppData.* from AM_WAR,AM_EAR,AM_ManagedObject m1,AM_ManagedObject m2,AM_PARENTCHILDMAPPER,AM_OAS_WebAppData left outer join AM_Servlet on AM_Servlet.parentid=warid  where AM_EAR.earid=AM_WAR.parentid and AM_EAR.parentid=m1.resourceid and m1.type='OAS-OPMN-Process' and m2.resourceid=warid and m1.resourceid=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and m2.resourceid=AM_OAS_WebAppData.resourceid and AM_OAS_WebAppData.collectiontime=" + maxcoltime + " group by m2.resourceid,EARNAME";
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 171 */       FormatUtil.printQueryChange("OracleASAction.java", oldquery, query);
/*     */       
/* 173 */       rs = DBQueryUtil.executeQueryStmt("am.Oracleas.action.servletcount", new String[] { "'OAS-OPMN-Process'", resourceid, maxcoltime + "" });
/* 174 */       ArrayList webappdata = new ArrayList();
/* 175 */       while (rs.next())
/*     */       {
/* 177 */         Properties data = new Properties();
/* 178 */         data.setProperty("name", rs.getString("DISPLAYNAME"));
/* 179 */         data.setProperty("earname", rs.getString("EARNAME"));
/* 180 */         data.setProperty("process", rs.getString("process"));
/* 181 */         data.setProperty("servletcount", rs.getString("servletcount"));
/* 182 */         String warid = rs.getString("RESOURCEID");
/* 183 */         resIDs.add(warid);
/* 184 */         data.setProperty("resid", warid);
/* 185 */         data.setProperty("throughput", rs.getString("REQUESTTHROUGHPUT"));
/* 186 */         data.setProperty("avgprocesstime", rs.getString("AVGPROCESSTIME"));
/* 187 */         data.setProperty("active", rs.getString("ACTIVEREQUEST"));
/* 188 */         data.setProperty("activesession", rs.getString("ACTIVESESSION"));
/* 189 */         long sesstime = rs.getLong("AVGSESSIONACTIVETIME") / 60000L;
/* 190 */         data.setProperty("avgsessionactivetime", String.valueOf(sesstime));
/*     */         
/* 192 */         webappdata.add(data);
/*     */       }
/* 194 */       rs.close();
/* 195 */       request.setAttribute("webappdata", webappdata);
/* 196 */       start = System.currentTimeMillis();
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
/* 241 */       query = "select max(collectiontime) as maxtime from AM_OAS_JMSData,AM_PARENTCHILDMAPPER p1,AM_PARENTCHILDMAPPER p2,AM_ManagedObject where p2.childid=AM_OAS_JMSData.resourceid and p2.parentid=p1.childid and p1.parentid=AM_ManagedObject.resourceid and AM_ManagedObject.resourceid=" + resourceid;
/* 242 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 243 */       maxcoltime = 0L;
/* 244 */       if (rs.next())
/*     */       {
/* 246 */         maxcoltime = rs.getLong("maxtime");
/*     */       }
/* 248 */       rs.close();
/*     */       
/* 250 */       query = "select AM_OAS_JMS.location,AM_OAS_JMS.type,AM_OAS_JMSData.*,AM_ManagedObject.displayname from AM_OAS_JMS,AM_OAS_JMSData,AM_ManagedObject,AM_PARENTCHILDMAPPER p1, AM_PARENTCHILDMAPPER p2 where AM_OAS_JMS.RESOURCEID=AM_OAS_JMSData.RESOURCEID and AM_OAS_JMSData.collectiontime=" + maxcoltime + " and p1.CHILDID=AM_OAS_JMSData.RESOURCEID and p1.PARENTID=AM_ManagedObject.RESOURCEID and p1.parentid=p2.childid and p2.parentid=" + resourceid;
/* 251 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 252 */       ArrayList jmsdata = new ArrayList();
/* 253 */       while (rs.next()) {
/* 254 */         String enqavg = rs.getString("ENQAVG");
/* 255 */         String deqavg = rs.getString("DEQAVG");
/* 256 */         Properties prop = new Properties();
/* 257 */         prop.setProperty("process", rs.getString("displayname"));
/* 258 */         prop.setProperty("location", rs.getString("location"));
/* 259 */         prop.setProperty("type", rs.getString("type"));
/* 260 */         String jmsid = rs.getString("RESOURCEID");
/* 261 */         resIDs.add(jmsid);
/* 262 */         prop.setProperty("resid", jmsid);
/* 263 */         prop.setProperty("messageCount", rs.getString("MESSAGECOUNT"));
/* 264 */         prop.setProperty("enq", rs.getString("ENQ"));
/* 265 */         prop.setProperty("deq", rs.getString("DEQ"));
/* 266 */         prop.setProperty("pendingMessageCount", rs.getString("PENDINGMESSAGECOUNT"));
/* 267 */         if (enqavg.contains("-1"))
/*     */         {
/* 269 */           prop.setProperty("enqAvg", "-");
/*     */         }
/*     */         else
/*     */         {
/* 273 */           prop.setProperty("enqAvg", rs.getString("ENQAVG"));
/*     */         }
/* 275 */         if (deqavg.contains("-1"))
/*     */         {
/* 277 */           prop.setProperty("deqAvg", "-");
/*     */         }
/*     */         else {
/* 280 */           prop.setProperty("deqAvg", rs.getString("DEQAVG"));
/*     */         }
/* 282 */         String storesize = rs.getString("STORESIZE");
/* 283 */         if (storesize.contains("-"))
/*     */         {
/* 285 */           prop.setProperty("storeSize", "-");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 290 */           prop.setProperty("storeSize", rs.getString("STORESIZE"));
/*     */         }
/*     */         
/* 293 */         jmsdata.add(prop);
/*     */       }
/* 295 */       rs.close();
/* 296 */       request.setAttribute("jmsdata", jmsdata);
/* 297 */       start = System.currentTimeMillis();
/*     */     }
/*     */     
/*     */ 
/* 301 */     if (webappid != null)
/*     */     {
/* 303 */       HashMap map = null;
/* 304 */       if (poll == null) poll = "300";
/* 305 */       map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(poll));
/*     */       
/* 307 */       if (map != null)
/*     */       {
/* 309 */         request.setAttribute("systeminfo", map);
/*     */       }
/*     */       
/* 312 */       resIDs.add(webappid);
/* 313 */       query = "select max(COLLECTIONTIME) as maxtime from AM_OAS_ServletData,AM_Servlet where AM_OAS_ServletData.RESOURCEID=AM_Servlet.ID and AM_Servlet.PARENTID=" + webappid;
/* 314 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 315 */       long maxcoltime = 0L;
/* 316 */       if (rs.next())
/*     */       {
/* 318 */         maxcoltime = rs.getLong("maxtime");
/*     */       }
/* 320 */       rs.close();
/*     */       
/* 322 */       query = "select AM_ManagedObject.DISPLAYNAME,AM_OAS_ServletData.* from AM_OAS_ServletData,AM_Servlet,AM_ManagedObject where AM_Servlet.ID=AM_ManagedObject.RESOURCEID and AM_OAS_ServletData.RESOURCEID=AM_Servlet.ID and AM_Servlet.PARENTID=" + webappid + " and AM_OAS_ServletData.COLLECTIONTIME=" + maxcoltime;
/* 323 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 324 */       ArrayList servletdata = new ArrayList();
/* 325 */       while (rs.next())
/*     */       {
/* 327 */         Properties prop = new Properties();
/* 328 */         String servletid = rs.getString("RESOURCEID");
/* 329 */         resIDs.add(servletid);
/* 330 */         prop.setProperty("resid", servletid);
/* 331 */         prop.setProperty("name", rs.getString("DISPLAYNAME"));
/* 332 */         prop.setProperty("throughput", rs.getString("REQUESTTHROUGHPUT"));
/* 333 */         prop.setProperty("avgprocesstime", rs.getString("AVGPROCESSTIME"));
/* 334 */         prop.setProperty("active", rs.getString("ACTIVEREQUEST"));
/* 335 */         prop.setProperty("maxactive", rs.getString("MAXACTIVEREQUEST"));
/* 336 */         prop.setProperty("activeinstance", rs.getString("ACTIVEINSTANCE"));
/* 337 */         prop.setProperty("availinstance", rs.getString("AVAILABLEINSTANCE"));
/* 338 */         servletdata.add(prop);
/*     */       }
/* 340 */       request.setAttribute("servletdata", servletdata);
/* 341 */       rs.close();
/*     */     }
/*     */     
/* 344 */     request.setAttribute("resIDs", resIDs);
/* 345 */     ActionErrors errors = new ActionErrors();
/* 346 */     String errormsg = "";
/*     */     try {
/* 348 */       rs = AMConnectionPool.executeQueryStmt("select ERRORMSG from AM_RESOURCECONFIG where RESOURCEID=" + resourceid);
/* 349 */       if (rs.next()) {
/* 350 */         errormsg = rs.getString("ERRORMSG");
/*     */       }
/* 352 */       if ((errormsg != null) && ((errormsg.indexOf("ResponseCode") != -1) || (errormsg.indexOf("Unable to invoke AggreSpy servlet") != -1))) {
/* 353 */         errors.add("org.apache.struts.action.ERROR", new ActionMessage(errormsg));
/*     */       }
/* 355 */       if (!errors.isEmpty()) {
/* 356 */         saveErrors(request, errors);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 365 */       return mapping.findForward("details");
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 359 */       ex.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 362 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */   }
/*     */   
/*     */   public ActionForward changeOracleAsStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 370 */     String changeStateTo = request.getParameter("state");
/* 371 */     String resourceid = request.getParameter("resourceid");
/* 372 */     String opmnid = request.getParameter("opmnid");
/* 373 */     String query = "update AM_ManagedObject set DCSTARTED=2 where RESOURCEID=" + opmnid;
/* 374 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */     try
/*     */     {
/*     */       try {
/* 378 */         if (changeStateTo.equals("enable")) {
/* 379 */           query = "update AM_ManagedObject set DCSTARTED=2 where RESOURCEID=" + opmnid;
/* 380 */           AMConnectionPool.executeUpdateStmt("insert into AM_RCAMAPPER values (" + resourceid + ",3402," + opmnid + ",3403)"); } else { int health;
/* 381 */           if (changeStateTo.equals("disable")) {
/* 382 */             query = "update AM_ManagedObject set DCSTARTED=7 where RESOURCEID=" + opmnid;
/* 383 */             AMConnectionPool.executeUpdateStmt("delete from AM_RCAMAPPER where PARENTRESOURCEID=" + resourceid + " and CHILDRESOURCEID=" + opmnid);
/* 384 */             health = new AMRCAnalyser().applyRCA(Integer.parseInt(resourceid), 3402, System.currentTimeMillis());
/* 385 */           } else if (changeStateTo.equals("delete")) {
/*     */             try {
/* 387 */               ScheduleOracleASDataCollection.deleteOpmnDataCollection(opmnid);
/* 388 */               EnterpriseUtil.handleDeletionInAAM(new String[] { opmnid });
/* 389 */             } catch (Exception ex2) { ex2.printStackTrace();
/*     */             }
/*     */           }
/* 392 */         } } catch (Exception e) { e.printStackTrace();
/*     */       }
/* 394 */       AMConnectionPool.executeUpdateStmt(query);
/*     */     } catch (Exception ex) {
/* 396 */       ex.printStackTrace();
/*     */     }
/* 398 */     return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID", true);
/*     */   }
/*     */   
/*     */   private void closeResultSet(ResultSet rs)
/*     */   {
/* 403 */     if (rs != null) {
/*     */       try
/*     */       {
/* 406 */         rs.close();
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\oracleas\struts\OracleASAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */