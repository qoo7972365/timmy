/*     */ package com.adventnet.appmanager.tomcat.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDCInf;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDataCollectionHandler;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.ResourceConfig;
/*     */ import com.adventnet.appmanager.server.tomcat.datacollection.TomcatDataCollector;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.model.CollectData;
/*     */ import com.adventnet.nms.applnfw.discovery.server.model.InetService;
/*     */ import com.adventnet.nms.topodb.TopoAPI;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.Action;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.action.DynaActionForm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TomcatAction
/*     */   extends Action
/*     */ {
/*  46 */   private ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*  47 */   private ManagedApplication mo = new ManagedApplication();
/*  48 */   static Hashtable versioninfo = null;
/*     */   
/*     */   static {
/*  51 */     versioninfo = new Hashtable();
/*  52 */     Vector v3 = new Vector();
/*  53 */     v3.add("Availability");
/*  54 */     v3.add("Memory Usage");
/*  55 */     versioninfo.put("3", v3);
/*  56 */     Vector v4 = new Vector();
/*  57 */     v4.add("Availability");
/*  58 */     v4.add("Server Performance");
/*  59 */     v4.add("Memory Usage");
/*  60 */     v4.add("Response Summary");
/*  61 */     v4.add("Application Summary");
/*  62 */     versioninfo.put("4", v4);
/*  63 */     Vector v5 = new Vector();
/*  64 */     v5.add("Availability");
/*  65 */     v5.add("Server Performance");
/*  66 */     v5.add("Memory Usage");
/*  67 */     v5.add("Threads Availability");
/*  68 */     v5.add("Response Summary");
/*  69 */     v5.add("Application Summary");
/*  70 */     versioninfo.put("5", v5);
/*     */     
/*  72 */     Vector v6 = new Vector();
/*  73 */     v6.add("Availability");
/*  74 */     v6.add("Server Performance");
/*  75 */     v6.add("Memory Usage");
/*  76 */     v6.add("Threads Availability");
/*  77 */     v6.add("Response Summary");
/*  78 */     v6.add("Application Summary");
/*  79 */     versioninfo.put("6", v6);
/*     */     
/*  81 */     Vector v7 = new Vector();
/*  82 */     v7.add("Availability");
/*  83 */     v7.add("Server Performance");
/*  84 */     v7.add("Memory Usage");
/*  85 */     v7.add("Threads Availability");
/*  86 */     v7.add("Response Summary");
/*  87 */     v7.add("Application Summary");
/*  88 */     versioninfo.put("7", v7);
/*     */   }
/*     */   
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*     */     try
/*     */     {
/*  95 */       String selectedscheme = "default";
/*  96 */       if (request.getParameter("selectedscheme") != null)
/*     */       {
/*  98 */         selectedscheme = request.getParameter("selectedscheme");
/*  99 */         request.setAttribute("selectedscheme", selectedscheme);
/*     */       }
/* 101 */       String selectedSkin = "Grey";
/* 102 */       if (request.getParameter("selectedSkin") != null)
/*     */       {
/* 104 */         selectedSkin = request.getParameter("selectedSkin");
/* 105 */         request.setAttribute("selectedskin", selectedSkin);
/*     */       }
/*     */       
/* 108 */       if (request.getParameter("showconfigdiv") == null)
/*     */       {
/* 110 */         this.mo.getReloadPeriod(request);
/*     */       }
/* 112 */       if ((request.getParameter("showconfigdiv") != null) && (request.getParameter("showconfigdiv").equals("true")))
/*     */       {
/* 114 */         request.removeAttribute("reloadperiod");
/*     */       }
/* 116 */       ActionMessages messages = new ActionMessages();
/* 117 */       ActionErrors errors = new ActionErrors();
/* 118 */       String name = null;
/* 119 */       String resourceid = request.getParameter("resourceid");
/*     */       
/* 121 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 122 */       String displayname = null;
/* 123 */       int configured = 1;
/* 124 */       String sslenabled = "false";
/* 125 */       String tomcatmanagerurl = "/manager";
/* 126 */       ResultSet modetails = null;
/*     */       try
/*     */       {
/* 129 */         modetails = AMConnectionPool.executeQueryStmt("select AM_ManagedObject.* , AM_TOMCATINFO.SSLENABLED, AM_TOMCATINFO.URL from AM_ManagedObject left outer join AM_TOMCATINFO on  AM_TOMCATINFO.RESOURCEID=AM_ManagedObject.RESOURCEID  where AM_ManagedObject.RESOURCEID=" + resourceid + "");
/* 130 */         if (modetails.next())
/*     */         {
/* 132 */           name = modetails.getString("RESOURCENAME");
/* 133 */           displayname = modetails.getString("DISPLAYNAME");
/* 134 */           configured = modetails.getInt("DCSTARTED");
/* 135 */           sslenabled = modetails.getString("SSLENABLED");
/* 136 */           tomcatmanagerurl = modetails.getString("URL");
/* 137 */           if (sslenabled == null)
/*     */           {
/* 139 */             sslenabled = "false";
/* 140 */             AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt("insert into AM_TOMCATINFO (RESOURCEID,SSLENABLED) values(" + resourceid + ",'" + sslenabled + "')");
/*     */ 
/*     */           }
/* 143 */           else if (((sslenabled != null) && (sslenabled.equals("null"))) || (sslenabled.equals("NULL")))
/*     */           {
/* 145 */             sslenabled = "false";
/*     */             
/* 147 */             AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt("update AM_TOMCATINFO set SSLENABLED='" + sslenabled + "' where RESOURCEID = " + resourceid);
/*     */           }
/* 149 */           if ((tomcatmanagerurl != null) && (!tomcatmanagerurl.equals(""))) {
/* 150 */             AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt("update AM_TOMCATINFO set URL='" + tomcatmanagerurl + "' where RESOURCEID = " + resourceid);
/* 151 */           } else if (("5".equals(request.getParameter("version"))) || ("6".equals(request.getParameter("version"))) || ("7".equals(request.getParameter("version")))) {
/* 152 */             AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt("update AM_TOMCATINFO set URL='/manager' where RESOURCEID = " + resourceid);
/*     */           }
/* 154 */           request.setAttribute("showdata", configured + "");
/*     */         }
/*     */         else
/*     */         {
/* 158 */           return new ActionForward("/jsp/NoData.jsp?message=No Data Available.");
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 163 */         e.printStackTrace();
/*     */       } finally {
/* 165 */         if (modetails != null) {
/* 166 */           AMConnectionPool.closeStatement(modetails);
/*     */         }
/*     */       }
/* 169 */       ResourceConfig tomcat = (ResourceConfig)this.api.getCollectData(name, "TOMCAT");
/* 170 */       while (tomcat == null)
/*     */       {
/*     */         try
/*     */         {
/* 174 */           Thread.sleep(300L);
/*     */         }
/*     */         catch (Exception e) {}
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 181 */       HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, tomcat.getPollInterval());
/* 182 */       if (map != null)
/*     */       {
/* 184 */         request.setAttribute("systeminfo", map);
/*     */       }
/* 186 */       AMLog.debug("Tomcat : Name inside the Data Class" + name);
/* 187 */       String haid = request.getParameter("haid");
/* 188 */       String appName = request.getParameter("appName");
/* 189 */       request.setAttribute("haid", haid);
/* 190 */       request.setAttribute("name", name);
/* 191 */       request.setAttribute("appName", appName);
/* 192 */       request.setAttribute("displayname", displayname);
/* 193 */       TopoAPI tapi = (TopoAPI)NmsUtil.getAPI("TopoAPI");
/* 194 */       InetService tomcatobject = null;
/*     */       try {
/* 196 */         tomcatobject = (InetService)tapi.getByName(tomcat.getResourceName());
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 200 */         e.printStackTrace();
/*     */       }
/* 202 */       String version = null;
/* 203 */       if (tomcatobject != null)
/*     */       {
/* 205 */         version = tomcatobject.getProtocolVersion();
/* 206 */         request.setAttribute("machine", tomcatobject.getTargetName());
/*     */       }
/* 208 */       request.setAttribute("version", version);
/*     */       
/* 210 */       Hashtable contextdetails = new Hashtable();
/* 211 */       if (!version.equals("3")) {
/* 212 */         contextdetails = getContexts(tomcat.getresourceID());
/*     */       }
/* 214 */       request.setAttribute("contextdetails", contextdetails);
/* 215 */       request.setAttribute("categorylist", (Vector)versioninfo.get(version));
/* 216 */       if ((request.getParameter("configure") != null) && (request.getParameter("configure").equals("true"))) {
/* 217 */         int poll = 300;
/* 218 */         String username = null;
/* 219 */         String password = null;
/* 220 */         sslenabled = (String)((DynaActionForm)form).get("sslenabled");
/* 221 */         if (sslenabled == null)
/*     */         {
/* 223 */           sslenabled = "false";
/*     */         }
/* 225 */         else if (sslenabled.equals("on"))
/*     */         {
/* 227 */           sslenabled = "true";
/*     */         }
/* 229 */         else if (sslenabled.equals(""))
/*     */         {
/* 231 */           sslenabled = "false";
/*     */         }
/* 233 */         request.setAttribute("sslenabled", sslenabled);
/* 234 */         version = request.getParameter("version");
/* 235 */         username = (String)((DynaActionForm)form).get("username");
/* 236 */         password = (String)((DynaActionForm)form).get("password");
/* 237 */         tomcatmanagerurl = (String)((DynaActionForm)form).get("tomcatmanagerurl");
/* 238 */         Integer pollInt = (Integer)((DynaActionForm)form).get("pollinterval");
/* 239 */         if ((version.equals("5")) || (version.equals("6")) || (version.equals("7"))) {
/* 240 */           if ((username != null) && (username.length() == 0))
/*     */           {
/* 242 */             username = null;
/* 243 */             errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("hostresource.username"));
/*     */           }
/* 245 */           if ((password != null) && (password.length() == 0))
/*     */           {
/* 247 */             password = null;
/* 248 */             errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("hostresource.password"));
/*     */           }
/*     */         }
/* 251 */         if (pollInt == null)
/*     */         {
/* 253 */           poll = 300;
/* 254 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("hostresource.pollinterval"));
/*     */         }
/*     */         else {
/* 257 */           poll = pollInt.intValue() * 60;
/*     */         }
/* 259 */         if (!errors.isEmpty()) {
/* 260 */           saveErrors(request, errors);
/* 261 */           request.setAttribute("configured", "false");
/* 262 */           if (request.getParameter("configured") != null) {
/* 263 */             request.setAttribute("configured", "true");
/*     */           }
/* 265 */           return mapping.getInputForward();
/*     */         }
/*     */         
/*     */ 
/* 269 */         Properties TestProps = new Properties();
/* 270 */         TestProps.setProperty("HOST", tomcat.getTargetAddress());
/* 271 */         TestProps.setProperty("PORT", String.valueOf(tomcat.getApplnDiscPort()));
/* 272 */         TestProps.setProperty("username", username);
/* 273 */         TestProps.setProperty("password", password);
/* 274 */         TestProps.setProperty("version", version);
/* 275 */         TestProps.setProperty("sslenabled", sslenabled);
/* 276 */         TestProps.setProperty("tomcatmanagerurl", tomcatmanagerurl);
/* 277 */         Properties authresult = null;
/*     */         try
/*     */         {
/* 280 */           AMDCInf amdc = (AMDCInf)Class.forName("com.adventnet.appmanager.server.tomcat.datacollection.ScheduleTomcatDataCollection").newInstance();
/* 281 */           authresult = amdc.CheckAuthentication(TestProps);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 285 */           e.printStackTrace();
/*     */         }
/* 287 */         String result = authresult.getProperty("authentication");
/* 288 */         if (result.equals("failed"))
/*     */         {
/* 290 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", authresult.getProperty("error")));
/* 291 */           saveMessages(request, messages);
/* 292 */           request.setAttribute("configured", "false");
/* 293 */           if (request.getParameter("configured") != null)
/*     */           {
/*     */ 
/*     */ 
/* 297 */             request.setAttribute("configured", "true");
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 304 */           tomcatobject.setProtocolVersion(version);
/*     */           try
/*     */           {
/* 307 */             tapi.updateObject(tomcatobject);
/* 308 */             Thread.sleep(500L);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 312 */             System.out.println("Unable to update the Tomcat Version");
/* 313 */             e.printStackTrace();
/*     */           }
/* 315 */           AMDataCollectionHandler.getInstance().updateCollectData(name, "TOMCAT", poll, true);
/* 316 */           AMConnectionPool update = AMConnectionPool.getInstance();
/* 317 */           String updatequery = null;
/* 318 */           if ((version.equals("5")) || (version.equals("6")) || (version.equals("7"))) {
/* 319 */             updatequery = "update AM_RESOURCECONFIG set USERNAME='" + username + "' , PASSWORD=" + DBQueryUtil.encode(password) + " , ERRORMSG='" + "' where RESOURCEID='" + tomcat.getresourceID() + "'";
/* 320 */             AMConnectionPool.executeUpdateStmt(updatequery);
/* 321 */             updatequery = "update AM_TOMCATINFO set URL='" + tomcatmanagerurl + "' where RESOURCEID = " + resourceid;
/* 322 */             AMConnectionPool.executeUpdateStmt(updatequery);
/*     */           }
/* 324 */           request.setAttribute("categorylist", (Vector)versioninfo.get(version));
/* 325 */           request.setAttribute("version", version);
/* 326 */           String newdisplayname = request.getParameter("displayname");
/* 327 */           updatequery = "update AM_ManagedObject  set  DISPLAYNAME='" + newdisplayname + "' , DCSTARTED=2 where RESOURCEID='" + resourceid + "'";
/* 328 */           AMConnectionPool.executeUpdateStmt(updatequery);
/* 329 */           EnterpriseUtil.addUpdateQueryToFile(updatequery);
/* 330 */           updatequery = "update AM_RESOURCECONFIG set ERRORMSG='Data Collection reconfigured. Kindly wait till the next polling interval' where RESOURCEID='" + resourceid + "'";
/* 331 */           AMConnectionPool.executeUpdateStmt(updatequery);
/* 332 */           request.setAttribute("displayname", newdisplayname);
/* 333 */           updatequery = "update AM_TOMCATINFO set SSLENABLED='" + sslenabled + "' where RESOURCEID = " + resourceid;
/* 334 */           AMConnectionPool.executeUpdateStmt(updatequery);
/* 335 */           CollectData col = this.api.getCollectData(name, "TOMCAT");
/* 336 */           TomcatDataCollector.contexttable.put(col.getResourceName(), String.valueOf(System.currentTimeMillis()));
/* 337 */           AMDataCollectionHandler.getInstance();AMDataCollectionHandler.scheduleDataCollection(col, true);
/*     */         }
/* 339 */         String path = "/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID";
/* 340 */         if (request.getParameter("haid") != null)
/*     */         {
/* 342 */           path = path + "&haid=" + request.getParameter("haid");
/*     */         }
/* 344 */         if (result.equals("passed"))
/*     */         {
/* 346 */           return new ActionForward(path, true);
/*     */         }
/*     */         
/*     */ 
/* 350 */         path = path + "&showconfigdiv=true&configure=false";
/* 351 */         return new ActionForward(path);
/*     */       }
/*     */       
/* 354 */       tomcat = null;
/* 355 */       tomcat = (ResourceConfig)this.api.getCollectData(name, "TOMCAT");
/* 356 */       request.setAttribute("configured", "true");
/* 357 */       if (tomcat.getErrorMsg().equalsIgnoreCase("Data Collection reconfigured. Kindly wait till the next polling interval"))
/*     */       {
/* 359 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString(tomcat.getErrorMsg())));
/* 360 */         saveMessages(request, messages);
/*     */       }
/* 362 */       else if ((tomcat.getErrorMsg().indexOf("Data Collection Sucessful") == -1) && (tomcat.getErrorMsg().indexOf("Monitoring Not Started") == -1))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 370 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(tomcat.getErrorMsg()));
/* 371 */         saveErrors(request, errors);
/*     */       }
/*     */       
/* 374 */       request.setAttribute("resourceid", String.valueOf(tomcat.getresourceID()));
/* 375 */       if (request.getParameter("showconfigdiv") == null)
/*     */       {
/*     */ 
/* 378 */         if ((version.equals("5")) || (version.equals("6")) || (version.equals("7"))) {
/* 379 */           ((DynaActionForm)form).set("username", tomcat.getUserName());
/* 380 */           ((DynaActionForm)form).set("password", tomcat.getPassword());
/* 381 */           ((DynaActionForm)form).set("version", version);
/* 382 */           ((DynaActionForm)form).set("tomcatmanagerurl", tomcatmanagerurl);
/*     */         }
/* 384 */         ((DynaActionForm)form).set("version", version);
/* 385 */         ((DynaActionForm)form).set("pollinterval", new Integer(tomcat.getPollInterval() / 60));
/* 386 */         ((DynaActionForm)form).set("displayname", displayname);
/* 387 */         ((DynaActionForm)form).set("sslenabled", sslenabled);
/*     */ 
/*     */       }
/* 390 */       else if ((request.getParameter("showconfigdiv") == null) || (!request.getParameter("showconfigdiv").equals("true"))) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 397 */       if ((request.getParameter("context") == null) || (request.getParameter("context").equals("null"))) {
/* 398 */         if ((version.equals("5")) || (version.equals("6")) || (version.equals("7"))) {
/* 399 */           Properties tomcatdetails = getTomcatDetails(name, tomcat.getresourceID(), version);
/* 400 */           request.setAttribute("tomcatdetails", tomcatdetails);
/* 401 */           int i = 0;
/* 402 */           Hashtable tomcatsessiondet = new Hashtable();
/* 403 */           for (Enumeration e = contextdetails.keys(); e.hasMoreElements();) {
/* 404 */             String app = (String)e.nextElement();
/*     */             try {
/* 406 */               tomcatsessiondet = getTomcatSession(contextdetails);
/* 407 */               i++;
/*     */             } catch (Exception ex) {
/* 409 */               ex.printStackTrace();
/*     */             }
/*     */           }
/* 412 */           request.setAttribute("tomcatsessiondet", tomcatsessiondet);
/*     */         }
/*     */         
/* 415 */         if (version.equals("4")) {
/* 416 */           Properties tomcatdetails = getTomcatDetails(name, tomcat.getresourceID(), version);
/* 417 */           request.setAttribute("tomcatdetails", tomcatdetails);
/*     */         }
/*     */         
/*     */ 
/* 421 */         if (version.equals("3")) {
/* 422 */           Properties tomcatdetails = getTomcatDetails(name, tomcat.getresourceID(), version);
/* 423 */           request.setAttribute("tomcatdetails", tomcatdetails);
/*     */         }
/*     */         
/*     */ 
/* 427 */         return mapping.findForward("server");
/*     */       }
/*     */       
/* 430 */       if (!version.equals("3")) {
/* 431 */         request.setAttribute("context", request.getParameter("context"));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 436 */       return mapping.findForward("context");
/*     */     }
/*     */     catch (Exception e) {
/* 439 */       e.printStackTrace(); }
/* 440 */     return null;
/*     */   }
/*     */   
/*     */   public Properties getTomcatDetails(String name, int id, String version)
/*     */   {
/* 445 */     Properties p = new Properties();
/* 446 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 447 */     String tomcatdetailsquery = "select * from AM_TOMCATDETAILS where RESOURCEID=" + id + "";
/*     */     try {
/* 449 */       ResultSet rs = AMConnectionPool.executeQueryStmt(tomcatdetailsquery);
/* 450 */       if (rs.next()) {
/* 451 */         p.setProperty("TOMCATVERSION", rs.getString("TOMCATVERSION"));
/* 452 */         p.setProperty("JAVAVERSION", rs.getString("JAVAVERSION"));
/* 453 */         p.setProperty("JVMVENDOR", rs.getString("JVMVENDOR"));
/* 454 */         p.setProperty("OSNAME", rs.getString("OSNAME"));
/*     */       }
/* 456 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     catch (Exception e) {
/* 459 */       AMLog.fatal("Tomcat : Exception in querying the data for TomcatDetails", e);
/*     */     }
/*     */     
/* 462 */     return p;
/*     */   }
/*     */   
/*     */ 
/*     */   public Hashtable getTomcatSession(Hashtable contextdetails)
/*     */   {
/* 468 */     Properties ses = new Properties();
/* 469 */     long time = 0L;
/* 470 */     StringBuffer idstr = new StringBuffer();
/* 471 */     String app = null;
/* 472 */     ArrayList ids = new ArrayList();
/* 473 */     Properties rid = new Properties();
/* 474 */     Hashtable session = new Hashtable();
/* 475 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 476 */     for (Enumeration e = contextdetails.keys(); e.hasMoreElements();) {
/* 477 */       app = (String)e.nextElement();
/* 478 */       rid = (Properties)contextdetails.get(app);
/* 479 */       ids.add(rid.getProperty("ID"));
/* 480 */       idstr.append(rid.getProperty("ID") + ",");
/*     */     }
/* 482 */     String id = idstr.toString();
/* 483 */     id = id.substring(0, id.length() - 1);
/* 484 */     String coltime = "select MAX(COLLECTIONTIME) from AM_TOMCATSESSIONDETAILS where RESOURCEID in (" + id + ")";
/*     */     try {
/* 486 */       ResultSet rs = AMConnectionPool.executeQueryStmt(coltime);
/* 487 */       if (rs.next()) {
/* 488 */         time = rs.getLong(1);
/*     */       }
/* 490 */       AMConnectionPool.closeStatement(rs);
/*     */     } catch (Exception e) {
/* 492 */       e.printStackTrace();
/*     */     }
/* 494 */     if (time > 0L) {
/* 495 */       for (int i = 0; i < ids.size(); i++)
/*     */       {
/* 497 */         String tomcatsessiondetails = "select RESOURCEID,SEESION from AM_TOMCATSESSIONDETAILS where RESOURCEID=" + ids.get(i) + " and COLLECTIONTIME =" + time + "";
/*     */         try {
/* 499 */           ResultSet rs = AMConnectionPool.executeQueryStmt(tomcatsessiondetails);
/* 500 */           if (rs.next())
/*     */           {
/* 502 */             ses.setProperty(String.valueOf(rs.getInt("RESOURCEID")), rs.getString("SEESION"));
/*     */           }
/*     */           
/* 505 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */         catch (Exception e) {
/* 508 */           AMLog.fatal("Tomcat : Exception in querying the data for TomcatSessionDetails", e);
/* 509 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 515 */       for (Enumeration e = contextdetails.keys(); e.hasMoreElements();) {
/*     */         try {
/* 517 */           app = (String)e.nextElement();
/* 518 */           rid = (Properties)contextdetails.get(app);
/* 519 */           String svalue = ses.getProperty(rid.getProperty("ID"));
/* 520 */           rid.setProperty("session", svalue);
/*     */         }
/*     */         catch (Exception ex) {
/* 523 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/* 527 */       session.put("sessdet", contextdetails);
/*     */     }
/*     */     
/* 530 */     return session;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Properties getAlerts(int id)
/*     */   {
/* 537 */     String alertquery = "select CATEGORY , SEVERITY from Alert where SOURCE=" + id + "";
/* 538 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 539 */     Properties alert = new Properties();
/*     */     try {
/* 541 */       ResultSet rs = AMConnectionPool.executeQueryStmt(alertquery);
/* 542 */       while (rs.next()) {
/* 543 */         alert.setProperty(rs.getString(1), "" + rs.getInt(2));
/*     */       }
/*     */       try
/*     */       {
/* 547 */         AMConnectionPool.closeStatement(rs);
/*     */ 
/*     */       }
/*     */       catch (Exception exc) {}
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 554 */       AMLog.fatal("Tomcat : Exception in querying the data for Alert for Tomcat", e);
/*     */     }
/*     */     
/* 557 */     return alert;
/*     */   }
/*     */   
/*     */   public Hashtable getContexts(int resourceID)
/*     */   {
/* 562 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 563 */     Hashtable h = new Hashtable();
/*     */     try {
/* 565 */       String detailsquery = "select WARID,AM_TC_WAR.WARNAME from AM_TC_WAR where AM_TC_WAR.PARENTID=" + resourceID;
/* 566 */       ResultSet rs = AMConnectionPool.executeQueryStmt(detailsquery);
/* 567 */       while (rs.next()) {
/* 568 */         Properties p = new Properties();
/* 569 */         p.setProperty("ID", String.valueOf(rs.getInt(1)));
/* 570 */         h.put(rs.getString(2), p);
/*     */       }
/*     */       
/* 573 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     catch (Exception e) {
/* 576 */       e.printStackTrace();
/*     */     }
/* 578 */     return h;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\tomcat\struts\TomcatAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */