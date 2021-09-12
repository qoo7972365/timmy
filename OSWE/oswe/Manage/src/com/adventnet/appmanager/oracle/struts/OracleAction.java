/*     */ package com.adventnet.appmanager.oracle.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDCInf;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.appln.oracle.datacollection.server.model.OracleCollectData;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import com.adventnet.utilities.stringutils.StrUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ public final class OracleAction
/*     */   extends Action
/*     */ {
/*  37 */   private ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*     */   
/*  39 */   private static boolean driverisinclasspath = false;
/*  40 */   private static boolean checked = false;
/*     */   
/*  42 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  46 */     this.mo.getReloadPeriod(request);
/*  47 */     ActionMessages messages = new ActionMessages();
/*  48 */     ActionErrors errors = new ActionErrors();
/*     */     
/*     */     try
/*     */     {
/*  52 */       String scripts_query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.type ='Script Monitor' and AM_PARENTCHILDMAPPER.PARENTID=" + request.getParameter("resourceid") + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*  53 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(scripts_query);
/*  54 */       ArrayList al = new ArrayList();
/*  55 */       while (rs.next())
/*     */       {
/*  57 */         al.add(rs.getString(1));
/*     */       }
/*  59 */       request.setAttribute("script_ids", al);
/*     */       try
/*     */       {
/*  62 */         closeResultSet(rs);
/*     */       }
/*     */       catch (Exception exc1)
/*     */       {
/*  66 */         exc1.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception exc1)
/*     */     {
/*  71 */       exc1.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/*  75 */     String queryMonitor = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.type ='QueryMonitor' and AM_PARENTCHILDMAPPER.PARENTID=" + request.getParameter("resourceid") + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*  76 */     System.out.println("the queryMonito========>:" + queryMonitor);
/*     */     
/*     */     try
/*     */     {
/*  80 */       AMConnectionPool.getInstance();ResultSet rsQry = AMConnectionPool.executeQueryStmt(queryMonitor);
/*  81 */       ArrayList ql = new ArrayList();
/*  82 */       while (rsQry.next())
/*     */       {
/*  84 */         ql.add(rsQry.getString(1));
/*     */       }
/*  86 */       request.setAttribute("query_ids", ql);
/*     */       try
/*     */       {
/*  89 */         closeResultSet(rsQry);
/*     */       }
/*     */       catch (Exception exc1)
/*     */       {
/*  93 */         exc1.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception exc1)
/*     */     {
/*  98 */       exc1.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 103 */     if (!checked) {
/*     */       try {
/* 105 */         Class.forName("oracle.jdbc.driver.OracleDriver");
/* 106 */         driverisinclasspath = true;
/*     */       }
/*     */       catch (Exception e) {
/* 109 */         driverisinclasspath = false;
/*     */       }
/* 111 */       checked = true;
/*     */     }
/* 113 */     String name = null;
/* 114 */     String resourceid = request.getParameter("resourceid");
/* 115 */     String pollint = (String)request.getAttribute("reloadperiod");
/*     */     try {
/* 117 */       Long.parseLong(pollint);
/*     */     }
/*     */     catch (Exception e) {
/* 120 */       pollint = "300";
/*     */     }
/* 122 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(pollint));
/* 123 */     if (map != null)
/*     */     {
/* 125 */       request.setAttribute("systeminfo", map);
/*     */     }
/*     */     
/* 128 */     String displayname = null;
/* 129 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 130 */     int configured = 1;
/*     */     try
/*     */     {
/* 133 */       ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/* 134 */       if (modetails.next())
/*     */       {
/* 136 */         name = modetails.getString("RESOURCENAME");
/* 137 */         displayname = modetails.getString("DISPLAYNAME");
/* 138 */         configured = modetails.getInt("DCSTARTED");
/* 139 */         request.setAttribute("showdata", configured + "");
/*     */       }
/*     */       else
/*     */       {
/* 143 */         return new ActionForward("/jsp/NoData.jsp?message=" + FormatUtil.getString("am.webclient.oracle.nodata.available"));
/*     */       }
/* 145 */       if (modetails != null) {
/* 146 */         closeResultSet(modetails);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 150 */       e.printStackTrace();
/*     */     }
/* 152 */     if (configured != 1) {
/* 153 */       if (!driverisinclasspath) {
/* 154 */         return new ActionForward("/jsp/NoData.jsp?message=" + FormatUtil.getString("am.webclient.oracle.classes12.zip.required"));
/*     */       }
/* 156 */     } else if ((configured != 2) && 
/* 157 */       (!driverisinclasspath)) {
/* 158 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.oracle.classes12.zip.required")));
/* 159 */       saveMessages(request, messages);
/*     */     }
/*     */     
/*     */ 
/* 163 */     if ((!driverisinclasspath) && (request.getParameter("configure") != null))
/*     */     {
/* 165 */       return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID", true);
/*     */     }
/* 167 */     OracleCollectData oracle = (OracleCollectData)this.api.getCollectData(name, "ORACLE");
/* 168 */     request.setAttribute("name", name);
/* 169 */     request.setAttribute("haid", request.getParameter("haid"));
/* 170 */     request.setAttribute("resourceid", request.getParameter("resourceid"));
/* 171 */     if (request.getParameter("details") == null) {
/* 172 */       request.setAttribute("details", "Availability");
/*     */     }
/*     */     else {
/* 175 */       request.setAttribute("details", request.getParameter("details"));
/*     */     }
/* 177 */     if (request.getParameter("configure") != null) {
/* 178 */       String username = (String)((DynaActionForm)form).get("username");
/* 179 */       String password = (String)((DynaActionForm)form).get("password");
/* 180 */       String instance = (String)((DynaActionForm)form).get("instance");
/* 181 */       Integer poll = (Integer)((DynaActionForm)form).get("pollinterval");
/* 182 */       Properties userProps = new Properties();
/* 183 */       userProps.setProperty("resourceName", name);
/* 184 */       userProps.setProperty("userName", username);
/* 185 */       userProps.setProperty("password", password);
/* 186 */       userProps.setProperty("instanceName", instance);
/* 187 */       userProps.setProperty("pollInterval", String.valueOf(poll.intValue() * 60));
/* 188 */       if ((username != null) && (username.length() == 0))
/*     */       {
/* 190 */         username = null;
/* 191 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("hostresource.username"));
/*     */       }
/* 193 */       if ((password != null) && (password.length() == 0))
/*     */       {
/* 195 */         password = "NULL";
/* 196 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("hostresource.password"));
/*     */       }
/* 198 */       if ((instance != null) && (instance.length() == 0))
/*     */       {
/* 200 */         instance = null;
/* 201 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("oracle.instance"));
/*     */       }
/* 203 */       if ((poll != null) && (poll.intValue() == 0))
/*     */       {
/* 205 */         poll = new Integer(300);
/* 206 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("hostresource.pollinterval"));
/*     */       }
/* 208 */       if (!errors.isEmpty()) {
/* 209 */         saveErrors(request, errors);
/* 210 */         request.setAttribute("configured", "false");
/* 211 */         if (request.getParameter("configured") != null) {
/* 212 */           request.setAttribute("configured", "true");
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 217 */       Properties TestProps = new Properties();
/* 218 */       ResultSet rs = AMConnectionPool.executeQueryStmt("select TARGETNAME from InetService where NAME ='" + oracle.getResourceName() + "'");
/* 219 */       rs.next();
/* 220 */       TestProps.setProperty("HOST", rs.getString("TARGETNAME"));
/* 221 */       TestProps.setProperty("PORT", String.valueOf(oracle.getApplnDiscPort()));
/* 222 */       TestProps.setProperty("username", username);
/* 223 */       TestProps.setProperty("password", password);
/* 224 */       TestProps.setProperty("instance", instance);
/* 225 */       rs.close();
/* 226 */       Properties authresult = null;
/* 227 */       AMDCInf amdc = null;
/* 228 */       String result = null;
/* 229 */       if ((username.equals(oracle.getUserName())) && (password.equals(oracle.getPassword())) && (instance.equals(oracle.getInstanceName())))
/*     */       {
/* 231 */         amdc = (AMDCInf)Class.forName("com.adventnet.appmanager.server.oracle.datacollection.ScheduleOracleDataCollection").newInstance();
/* 232 */         result = "passed";
/*     */       }
/*     */       else
/*     */       {
/*     */         try
/*     */         {
/* 238 */           amdc = (AMDCInf)Class.forName("com.adventnet.appmanager.server.oracle.datacollection.ScheduleOracleDataCollection").newInstance();
/* 239 */           authresult = amdc.CheckAuthentication(TestProps);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 243 */           e.printStackTrace();
/*     */         }
/* 245 */         result = authresult.getProperty("authentication");
/*     */       }
/* 247 */       if (result.equals("failed"))
/*     */       {
/* 249 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", FormatUtil.getString("am.oracleaction.editmonitorerror.text") + StrUtil.wrapWord(authresult.getProperty("exception"), 75, " ")));
/* 250 */         saveMessages(request, messages);
/* 251 */         request.setAttribute("configured", "false");
/*     */         
/* 253 */         if (request.getParameter("configured") != null) {
/* 254 */           request.setAttribute("configured", "true");
/*     */         }
/* 256 */         request.setAttribute("displayname", displayname);
/* 257 */         return mapping.findForward("DisplayOracleData");
/*     */       }
/*     */       
/* 260 */       String newdisplayname = request.getParameter("displayname");
/* 261 */       TestProps.setProperty("name", name);
/* 262 */       TestProps.setProperty("displayname", newdisplayname);
/* 263 */       TestProps.setProperty("pollinterval", String.valueOf(poll.intValue() * 60));
/* 264 */       TestProps.setProperty("id", resourceid);
/* 265 */       amdc.ScheduleDataCollection(TestProps);
/* 266 */       request.setAttribute("displayname", newdisplayname);
/*     */       
/* 268 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("am.webclient.oracle.reconfigured.message")));
/* 269 */       saveMessages(request, messages);
/* 270 */       return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID", true);
/*     */     }
/*     */     
/*     */ 
/* 274 */     request.setAttribute("configured", "true");
/* 275 */     request.setAttribute("details", "Availability");
/* 276 */     ((DynaActionForm)form).set("username", oracle.getUserName());
/* 277 */     ((DynaActionForm)form).set("password", oracle.getPassword());
/* 278 */     ((DynaActionForm)form).set("displayname", displayname);
/* 279 */     ((DynaActionForm)form).set("instance", oracle.getInstanceName());
/* 280 */     ((DynaActionForm)form).set("pollinterval", new Integer(oracle.getPollInterval() / 60));
/*     */     
/*     */ 
/*     */ 
/* 284 */     String error = oracle.getErrorMsg();
/* 285 */     System.out.println("error message in oracle is" + error);
/* 286 */     String buffergets = "select sql_text, buffer_gets, executions, buffer_gets/decode(executions, 0, 1, executions) bg_per_exe from v$sql where executions &gt; 0  and ROWNUM &lt;= 10 order by executions,bg_per_exe desc";
/* 287 */     String diskreads = "select sql_text, disk_reads, executions , disk_reads/decode(executions, 0, 1, executions) dr_per_exe from v$sql where executions &gt; 0 and ROWNUM &lt;= 10 order by executions,dr_per_exe desc";
/* 288 */     String lockwaiters = "select waiting_session,holding_session,lock_type,mode_held,mode_requested,lock_id1,lock_id2 from dba_waiters";
/* 289 */     String lockholders = "select sid,serial#,machine,program,lockwait from v$session where sid in (select holding_session from dba_blockers)";
/* 290 */     String lockdetails = "select b.object_name,a.session_id,c.serial#,decode(lmode,0,'None',1,'Null',2,'Row-S (SS)',3,'Row-X (SX)',4,'Share (S)',5,'S/Row-X (SSX)',6,'Exclusive (X)',lmode),decode(l.request,0,'Blocker','Waiting'),d.spid,to_char(c.logon_time,'dd mon yyyy hh24 mi ss'),c.last_call_et/60 from v$locked_object a, dba_objects b, v$session c, v$process d, v$lock l where a.object_id = b.object_id and a.session_id = c.sid and c.paddr = d.addr and l.sid = c.sid";
/* 291 */     if (oracle.getErrorMsg().equalsIgnoreCase(FormatUtil.getString("am.webclient.oracle.confmessage")))
/*     */     {
/* 293 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", oracle.getErrorMsg()));
/* 294 */       saveMessages(request, messages);
/*     */     }
/* 296 */     else if ((oracle.getErrorMsg().indexOf("Data Collection Successful") == -1) && (oracle.getErrorMsg().indexOf("Data collection yet to start") == -1))
/*     */     {
/*     */ 
/* 299 */       if ((error.indexOf("buffer_gets") != -1) || (error.indexOf("disk_reads") != -1) || (error.indexOf("waiting_session") != -1) || (error.indexOf("lockwait") != -1) || (error.indexOf("a.session_id") != -1))
/*     */       {
/* 301 */         request.setAttribute("errormessage", error);
/*     */       }
/* 303 */       else if (error.indexOf(lockholders) != -1)
/*     */       {
/* 305 */         request.setAttribute("errormessage", error);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 310 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", oracle.getErrorMsg()));
/* 311 */         saveMessages(request, messages);
/*     */       }
/*     */     }
/* 314 */     request.setAttribute("configured", "true");
/*     */     
/*     */ 
/* 317 */     return mapping.findForward("DisplayOracleData");
/*     */   }
/*     */   
/*     */   public Properties getAlerts(int resourceid) {
/* 321 */     String alertquery = "select CATEGORY , SEVERITY from Alert where SOURCE=" + resourceid + "";
/* 322 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 323 */     Properties alert = new Properties();
/*     */     try {
/* 325 */       ResultSet rs = AMConnectionPool.executeQueryStmt(alertquery);
/* 326 */       while (rs.next()) {
/* 327 */         alert.setProperty(rs.getString(1), String.valueOf(rs.getInt(2)));
/*     */       }
/* 329 */       closeResultSet(rs);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 333 */       e.printStackTrace();
/*     */     }
/* 335 */     return alert;
/*     */   }
/*     */   
/*     */   private void closeResultSet(ResultSet rs)
/*     */   {
/*     */     try
/*     */     {
/* 342 */       if (rs != null)
/*     */       {
/* 344 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 349 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\oracle\struts\OracleAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */