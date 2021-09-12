/*     */ package com.adventnet.appmanager.wta.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.ResourceConfig;
/*     */ import com.adventnet.appmanager.server.wta.datacollection.WTADataCollector;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
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
/*     */ public class WTAAction
/*     */   extends DispatchAction
/*     */ {
/*  47 */   private Log log1 = LogFactory.getLog("WTAAction");
/*     */   
/*     */   int urlDetails;
/*     */   
/*     */   public WTAAction()
/*     */   {
/*  53 */     String urlquery = "select AM_ManagedObject.RESOURCEID,RESOURCENAME as URL,max(AM_WTA_Transaction.TRANSACTIONID) as tid,max(AM_WTA_Transaction.INVOCATIONTIME) as INVOCATIONTIME,REMOTEHOST,TOTALEXECUTIONTIME,WEBEXECUTIONTIME,EJBEXECUTIONTIME,JAVAEXECUTIONTIME,JDBCEXECUTIONTIME,min(AM_WTA_MethodInstance.ID),STATUS from AM_ManagedObject,AM_WTA_Transaction,AM_PARENTCHILDMAPPER,AM_WTA_TransactionStats,AM_WTA_MethodInstance where AM_ManagedObject.RESOURCEID=AM_WTA_Transaction.URLID and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=? and AM_WTA_TransactionStats.TRANSACTIONID=AM_WTA_Transaction.TRANSACTIONID and AM_WTA_Transaction.TRANSACTIONID=AM_WTA_MethodInstance.TRANSACTIONID group by URLID";
/*  54 */     this.urlDetails = AMConnectionPool.getPreparedStatementID(urlquery);
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward showdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  61 */     request.removeAttribute("reloadperiod");
/*  62 */     String resourceid = request.getParameter("resourceid");
/*  63 */     String view = request.getParameter("view");
/*  64 */     if ((view != null) && (view.equals("current")))
/*     */     {
/*     */ 
/*  67 */       WTADataCollector wdc = new WTADataCollector();
/*  68 */       ResourceConfig rc = new ResourceConfig();
/*  69 */       rc.setresourceID(Integer.parseInt(resourceid));
/*  70 */       wdc.getCollectedData(rc);
/*     */     }
/*     */     
/*  73 */     String query = "select max(COLLECTIONTIME) as time from AM_ManagedObjectData where RESID=" + resourceid;
/*  74 */     ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  75 */     if (rs.next())
/*     */     {
/*  77 */       long maxcoltime = rs.getLong("time");
/*  78 */       rs.close();
/*  79 */       query = "select MESSAGE from AM_ManagedObjectDataCollectionInfo where RESOURCEID=" + resourceid + " and STARTTIME=" + maxcoltime;
/*  80 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  81 */       if (rs.next()) {
/*  82 */         request.setAttribute("error_message", rs.getString("MESSAGE"));
/*     */       }
/*  84 */       rs.close();
/*     */     }
/*     */     
/*  87 */     query = "select CHILDID,max(AM_WTA_Transaction.TRANSACTIONID) as tid from AM_PARENTCHILDMAPPER,AM_WTA_Transaction where PARENTID=" + resourceid + " and CHILDID=AM_WTA_Transaction.URLID group by CHILDID";
/*     */     
/*  89 */     PreparedStatement ps = AMConnectionPool.getPreparedStatement(query);
/*  90 */     rs = ps.executeQuery();
/*  91 */     ArrayList al = new ArrayList();
/*  92 */     while (rs.next())
/*     */     {
/*  94 */       al.add(rs.getString("tid"));
/*     */     }
/*  96 */     rs.close();
/*  97 */     ps.close();
/*     */     
/*  99 */     HashMap data = new HashMap();
/* 100 */     Properties systemprops = new Properties();
/* 101 */     int count = 0;
/* 102 */     int error = 0;
/*     */     
/*     */ 
/* 105 */     if (FreeEditionDetails.getFreeEditionDetails().isWTAAddonPresent()) {
/* 106 */       ActionMessages messages = new ActionMessages();
/* 107 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.wta.upgrade.message")));
/* 108 */       saveMessages(request, messages);
/*     */     }
/*     */     
/* 111 */     if (al.size() > 0) {
/* 112 */       StringBuffer buf = new StringBuffer("select AM_ManagedObject.RESOURCEID,AM_WTA_Transaction.TRANSACTIONID as tid,RESOURCENAME as URL,AM_WTA_Transaction.INVOCATIONTIME,REMOTEHOST,TOTALEXECUTIONTIME,WEBEXECUTIONTIME,EJBEXECUTIONTIME,JAVAEXECUTIONTIME,JDBCEXECUTIONTIME,STATUS  from AM_ManagedObject,AM_WTA_Transaction,AM_WTA_TransactionStats where AM_ManagedObject.RESOURCEID=AM_WTA_Transaction.URLID and AM_WTA_TransactionStats.TRANSACTIONID=AM_WTA_Transaction.TRANSACTIONID and (");
/* 113 */       int i = 0; for (int size = al.size(); i < size; i++)
/*     */       {
/* 115 */         buf.append("AM_WTA_Transaction.TRANSACTIONID=" + al.get(i));
/* 116 */         if (i != size - 1) {
/* 117 */           buf.append(" or ");
/*     */         }
/*     */       }
/*     */       
/* 121 */       String temp = null;
/* 122 */       if (!FreeEditionDetails.getFreeEditionDetails().isWTAAddonPresent())
/*     */       {
/* 124 */         buf.append(") order by TOTALEXECUTIONTIME ");
/* 125 */         temp = buf.toString();
/*     */         
/* 127 */         temp = DBQueryUtil.getTopNValues(temp, 5);
/*     */         
/* 129 */         ActionMessages messages = new ActionMessages();
/* 130 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.wta.noaddon.5urls")));
/* 131 */         saveMessages(request, messages);
/*     */       }
/*     */       else
/*     */       {
/* 135 */         buf.append(")");
/* 136 */         temp = buf.toString();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 141 */       ps = AMConnectionPool.getPreparedStatement(temp);
/*     */       
/* 143 */       rs = ps.executeQuery();
/*     */       
/* 145 */       while (rs.next())
/*     */       {
/* 147 */         count++;
/* 148 */         String url = rs.getString("URL");
/* 149 */         HashMap map = new HashMap();
/* 150 */         map.put("urlid", rs.getString("RESOURCEID"));
/* 151 */         map.put("total", new Long(rs.getLong("TOTALEXECUTIONTIME")));
/* 152 */         map.put("web", new Long(rs.getLong("WEBEXECUTIONTIME")));
/* 153 */         map.put("ejb", new Long(rs.getLong("EJBEXECUTIONTIME")));
/* 154 */         map.put("java", new Long(rs.getLong("JAVAEXECUTIONTIME")));
/* 155 */         map.put("jdbc", new Long(rs.getLong("JDBCEXECUTIONTIME")));
/* 156 */         map.put("remoteHost", rs.getString("REMOTEHOST"));
/* 157 */         map.put("invocationTime", rs.getString("INVOCATIONTIME"));
/* 158 */         int status = rs.getInt("STATUS");
/* 159 */         if (status == 3) {
/* 160 */           error++;
/*     */         }
/* 162 */         map.put("status", String.valueOf(status));
/* 163 */         map.put("tid", rs.getString("tid"));
/*     */         
/* 165 */         data.put(url, map);
/*     */       }
/* 167 */       rs.close();
/* 168 */       ps.close();
/*     */     }
/*     */     
/* 171 */     request.setAttribute("TransactionData", data);
/*     */     
/* 173 */     systemprops.setProperty("count", String.valueOf(count));
/* 174 */     systemprops.setProperty("errorcount", String.valueOf(error));
/*     */     
/* 176 */     query = "select TARGETNAME,PORTNO from InetService,AM_ManagedObject where AM_ManagedObject.RESOURCEID=? and AM_ManagedObject.RESOURCENAME=InetService.NAME";
/* 177 */     PreparedStatement ps1 = AMConnectionPool.getPreparedStatement(query);
/* 178 */     ps1.setString(1, resourceid);
/* 179 */     ResultSet rs1 = ps1.executeQuery();
/* 180 */     while (rs1.next())
/*     */     {
/* 182 */       systemprops.setProperty("serverName", rs1.getString("TARGETNAME"));
/* 183 */       systemprops.setProperty("port", rs1.getString("PORTNO"));
/*     */     }
/* 185 */     rs1.close();
/* 186 */     ps1.close();
/*     */     
/* 188 */     request.setAttribute("systemprops", systemprops);
/*     */     
/*     */ 
/* 191 */     query = "select * from AM_WTA_Config where RESOURCEID=" + resourceid;
/* 192 */     ResultSet rs2 = AMConnectionPool.executeQueryStmt(query);
/* 193 */     while (rs2.next())
/*     */     {
/* 195 */       boolean enabled = rs2.getInt("ENABLED") == 1;
/* 196 */       ((AMActionForm)form).setInstrumentationEnabled(enabled);
/* 197 */       ((AMActionForm)form).setSamplingFactor(rs2.getInt("SAMPLINGFACTOR"));
/* 198 */       ((AMActionForm)form).setMaxURL(rs2.getInt("MAXURL"));
/* 199 */       ((AMActionForm)form).setMaxChildren(rs2.getInt("MAXCHILDREN"));
/* 200 */       ((AMActionForm)form).setMaxDepth(rs2.getInt("MAXDEPTH"));
/* 201 */       ((AMActionForm)form).setFullTraceCount(rs2.getInt("FULLTRACECOUNT"));
/* 202 */       boolean traceenabled = rs2.getInt("TRACEENABLED") == 1;
/* 203 */       ((AMActionForm)form).setTracingEnabled(traceenabled);
/* 204 */       boolean packageinclude = rs2.getInt("PACKAGEINCLUDE") == 1;
/* 205 */       ((AMActionForm)form).setPackageInclude(packageinclude);
/*     */     }
/* 207 */     rs2.close();
/* 208 */     query = "select NAME from AM_WTA_Config_Package_Map,AM_WTA_Config_Package where RESOURCEID=" + resourceid + " and AM_WTA_Config_Package_Map.PACKAGEID=AM_WTA_Config_Package.ID";
/* 209 */     rs2 = AMConnectionPool.executeQueryStmt(query);
/* 210 */     StringBuffer sb = new StringBuffer();
/* 211 */     while (rs2.next())
/*     */     {
/* 213 */       sb.append(rs2.getString("NAME"));
/* 214 */       if (!rs2.isLast())
/*     */       {
/* 216 */         sb.append(",");
/*     */       }
/*     */     }
/* 219 */     ((AMActionForm)form).setPackageList(sb.toString());
/* 220 */     rs2.close();
/*     */     
/* 222 */     query = "select POLLINTERVAL from CollectData,AM_ManagedObject where CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=" + resourceid;
/* 223 */     rs2 = AMConnectionPool.executeQueryStmt(query);
/* 224 */     while (rs2.next())
/*     */     {
/* 226 */       ((AMActionForm)form).setPollInterval(rs2.getInt("POLLINTERVAL") / 60);
/*     */     }
/* 228 */     rs2.close();
/*     */     
/* 230 */     query = "select ERRORMSG from AM_RESOURCECONFIG where RESOURCEID=" + resourceid;
/* 231 */     rs2 = AMConnectionPool.executeQueryStmt(query);
/* 232 */     while (rs2.next())
/*     */     {
/* 234 */       String errormsg = rs2.getString("ERRORMSG");
/* 235 */       if (errormsg.equals("Version incompatible"))
/*     */       {
/* 237 */         ActionErrors errors = new ActionErrors();
/* 238 */         errors.add("org.apache.struts.action.ERROR", new ActionMessage("webtransaction.discovery.error.incompatible", errormsg));
/* 239 */         saveErrors(request, errors);
/*     */       }
/*     */     }
/* 242 */     return mapping.findForward("mainscreen");
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward showtrace(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 249 */     String tid = request.getParameter("tid");
/* 250 */     String resourceid = request.getParameter("resourceid");
/* 251 */     String view = request.getParameter("view");
/*     */     
/* 253 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, 300L);
/* 254 */     if (map != null)
/*     */     {
/* 256 */       request.setAttribute("wtasysteminfo", map);
/*     */     }
/*     */     
/* 259 */     populateComponentCount(tid, request, view);
/*     */     
/* 261 */     String query = "SELECT A1.ID,PACKAGE.NAME AS PACKAGE,CLASS.NAME AS CLASS,METHOD.NAME AS METHOD,A3.ARG_INDEX,ARGPK.NAME AS ARGPNAME,ARGCK.NAME AS ARGCNAME,AM_WTA_ArgumentValue.VALUE,AM_WTA_ClassType.TYPE,INCLUSIVETIME,EXCLUSIVETIME,THREADID,STATUS,EXCEPTIONMESSAGE,REMOTEHOST FROM AM_WTA_MethodInstance A1 JOIN AM_WTA_MethodIdentifier A2 ON A1.METHODIDENTIFIERID=A2.ID JOIN  AM_WTA_KeyWord PACKAGE ON A2.PACKAGEID=PACKAGE.ID JOIN AM_WTA_KeyWord CLASS ON A2.CLASSID=CLASS.ID join AM_WTA_KeyWord METHOD ON A2.METHODID=METHOD.ID join AM_WTA_Argument A3 on A2.ARGID=A3.ARGID join AM_WTA_KeyWord ARGPK on A3.PACKAGEID=ARGPK.ID join AM_WTA_KeyWord ARGCK on  A3.CLASSID=ARGCK.ID join AM_WTA_ClassToClassType A4 on A2.CLASSID=A4.CLASSID join  AM_WTA_KeyWord CLASSPK on A4.PACKAGEID=CLASSPK.ID join AM_WTA_KeyWord CLASSCK on A4.CLASSID=CLASSCK.ID join AM_WTA_ClassType on A4.TYPE=AM_WTA_ClassType.ID join AM_WTA_Transaction on A1.TRANSACTIONID=AM_WTA_Transaction.TRANSACTIONID LEFT OUTER JOIN AM_WTA_ArgumentValue ON (A1.ID=AM_WTA_ArgumentValue.METHODINSTANCEID AND A3.ARG_INDEX=AM_WTA_ArgumentValue.ARG_INDEX) WHERE A1.TRANSACTIONID=" + tid + " ORDER BY A1.ID";
/*     */     
/*     */ 
/*     */ 
/* 265 */     PreparedStatement ps = AMConnectionPool.getPreparedStatement(query);
/* 266 */     ResultSet rs = ps.executeQuery();
/* 267 */     HashMap methins = new HashMap();
/* 268 */     HashMap commonProp = new HashMap();
/* 269 */     boolean first = true;
/* 270 */     while (rs.next())
/*     */     {
/* 272 */       String id = rs.getString("ID");
/* 273 */       if (first) {
/* 274 */         if (commonProp.get("remoteHost") == null) {
/* 275 */           commonProp.put("remoteHost", rs.getString("REMOTEHOST"));
/*     */         }
/* 277 */         if (commonProp.get("threadName") == null) {
/* 278 */           commonProp.put("threadName", rs.getString("THREADID"));
/*     */         }
/* 280 */         request.setAttribute("firstMethodInstance", id);
/* 281 */         first = false;
/*     */       }
/*     */       
/* 284 */       HashMap props = null;
/* 285 */       if (methins.get(id) != null) {
/* 286 */         props = (HashMap)methins.get(id);
/*     */       }
/*     */       else {
/* 289 */         props = new HashMap();
/* 290 */         props.put("package", rs.getString("PACKAGE"));
/* 291 */         props.put("class", rs.getString("CLASS"));
/* 292 */         props.put("method", rs.getString("METHOD"));
/* 293 */         props.put("type", rs.getString("TYPE"));
/* 294 */         props.put("inclusiveTime", rs.getString("INCLUSIVETIME"));
/* 295 */         props.put("exclusiveTime", rs.getString("EXCLUSIVETIME"));
/* 296 */         props.put("status", rs.getString("STATUS"));
/* 297 */         props.put("exception", rs.getString("EXCEPTIONMESSAGE"));
/* 298 */         props.put("children", new ArrayList());
/* 299 */         methins.put(id, props);
/*     */       }
/*     */       
/* 302 */       int arg_idx = Integer.parseInt(rs.getString("ARG_INDEX"));
/* 303 */       if (arg_idx > 0) {
/* 304 */         HashMap argProp = new HashMap();
/* 305 */         argProp.put("argpname", rs.getString("ARGPNAME"));
/* 306 */         argProp.put("argcname", rs.getString("ARGCNAME"));
/* 307 */         String value = rs.getString("VALUE");
/* 308 */         if (value != null) {
/* 309 */           argProp.put("value", value);
/*     */         }
/* 311 */         ArrayList args = new ArrayList();
/* 312 */         if (props.get("args") != null) {
/* 313 */           args = (ArrayList)props.get("args");
/*     */         }
/* 315 */         args.add(argProp);
/* 316 */         props.put("args", args);
/*     */       }
/*     */     }
/*     */     
/* 320 */     rs.close();
/* 321 */     ps.close();
/*     */     
/*     */ 
/* 324 */     String qstr = "select PARENTID,CHILDID from AM_WTA_MethodTree,AM_WTA_MethodInstance a1,AM_WTA_MethodInstance a2 where a1.TRANSACTIONID=" + tid + " and a2.TRANSACTIONID=" + tid + " and a1.ID=AM_WTA_MethodTree.PARENTID and a2.ID=AM_WTA_MethodTree.CHILDID";
/* 325 */     ps = AMConnectionPool.getPreparedStatement(qstr);
/* 326 */     rs = ps.executeQuery();
/* 327 */     while (rs.next())
/*     */     {
/* 329 */       String parent = rs.getString("PARENTID");
/* 330 */       String child = rs.getString("CHILDID");
/* 331 */       HashMap prop = (HashMap)methins.get(parent);
/* 332 */       ArrayList children = (ArrayList)prop.get("children");
/* 333 */       children.add(child);
/*     */     }
/* 335 */     rs.close();
/* 336 */     ps.close();
/*     */     
/* 338 */     if ((view == null) || (view.equals(""))) {
/* 339 */       removeChildWithTimeZero(methins, (HashMap)methins.get((String)request.getAttribute("firstMethodInstance")));
/*     */     }
/*     */     
/* 342 */     request.setAttribute("traceinfo", methins);
/*     */     
/* 344 */     String qry = "select AM_WTA_Transaction.INVOCATIONTIME,RESOURCENAME as URL from AM_ManagedObject,AM_WTA_Transaction where AM_ManagedObject.RESOURCEID=AM_WTA_Transaction.URLID and AM_WTA_Transaction.TRANSACTIONID=" + tid;
/* 345 */     ps = AMConnectionPool.getPreparedStatement(qry);
/* 346 */     rs = ps.executeQuery();
/* 347 */     while (rs.next()) {
/* 348 */       commonProp.put("url", rs.getString("URL"));
/* 349 */       commonProp.put("invocationtime", rs.getString("INVOCATIONTIME"));
/*     */     }
/* 351 */     rs.close();
/* 352 */     ps.close();
/* 353 */     request.setAttribute("commonProp", commonProp);
/*     */     
/* 355 */     return mapping.findForward("trace");
/*     */   }
/*     */   
/*     */   private void removeChildWithTimeZero(HashMap allmethins, HashMap methins) {
/* 359 */     ArrayList children = (ArrayList)methins.get("children");
/* 360 */     ArrayList temp = new ArrayList();
/* 361 */     int i = 0; for (int size = children.size(); i < size; i++) {
/* 362 */       String id = (String)children.get(i);
/* 363 */       HashMap prop = (HashMap)allmethins.get(id);
/*     */       
/* 365 */       removeChildWithTimeZero(allmethins, prop);
/*     */       
/* 367 */       long extime = Long.parseLong((String)prop.get("exclusiveTime"));
/* 368 */       if (extime > 0L) {
/* 369 */         temp.add(id);
/*     */       }
/*     */       else {
/* 372 */         temp.addAll((ArrayList)prop.get("children"));
/*     */       }
/*     */     }
/* 375 */     methins.put("children", temp);
/*     */   }
/*     */   
/*     */   private void populateComponentCount(String tid, HttpServletRequest request, String view) throws Exception
/*     */   {
/* 380 */     ResultSet rs = null;
/*     */     try {
/* 382 */       HashMap map = new HashMap();
/* 383 */       String query = "";
/* 384 */       String toappend = "";
/*     */       
/* 386 */       if ((view == null) || (view.equals("")))
/*     */       {
/* 388 */         toappend = " and EXCLUSIVETIME>0";
/*     */       }
/* 390 */       query = "select count(*) as count from AM_WTA_MethodInstance,AM_WTA_MethodIdentifier,AM_WTA_ClassToClassType,AM_WTA_ClassType where transactionid=" + tid + " and AM_WTA_MethodInstance.METHODIDENTIFIERID=AM_WTA_MethodIdentifier.ID and AM_WTA_MethodIdentifier.PACKAGEID=AM_WTA_ClassToClassType.PACKAGEID and AM_WTA_MethodIdentifier.CLASSID=AM_WTA_ClassToClassType.CLASSID and AM_WTA_ClassToClassType.TYPE=AM_WTA_ClassType.ID and (AM_WTA_ClassType.TYPE='Servlet Filter' or AM_WTA_ClassType.TYPE='Servlet' or AM_WTA_ClassType.TYPE='JSP')" + toappend;
/* 391 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 392 */       if (rs.next()) {
/* 393 */         map.put("Web", String.valueOf(rs.getInt("count")));
/*     */       }
/*     */       
/* 396 */       query = "select count(*) as count from AM_WTA_MethodInstance,AM_WTA_MethodIdentifier,AM_WTA_ClassToClassType,AM_WTA_ClassType where transactionid=" + tid + " and AM_WTA_MethodInstance.METHODIDENTIFIERID=AM_WTA_MethodIdentifier.ID and AM_WTA_MethodIdentifier.PACKAGEID=AM_WTA_ClassToClassType.PACKAGEID and AM_WTA_MethodIdentifier.CLASSID=AM_WTA_ClassToClassType.CLASSID and AM_WTA_ClassToClassType.TYPE=AM_WTA_ClassType.ID and (AM_WTA_ClassType.TYPE='EJB')" + toappend;
/* 397 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 398 */       if (rs.next()) {
/* 399 */         map.put("EJB", String.valueOf(rs.getInt("count")));
/*     */       }
/*     */       
/* 402 */       query = "select count(*) as count from AM_WTA_MethodInstance,AM_WTA_MethodIdentifier,AM_WTA_ClassToClassType,AM_WTA_ClassType where transactionid=" + tid + " and AM_WTA_MethodInstance.METHODIDENTIFIERID=AM_WTA_MethodIdentifier.ID and AM_WTA_MethodIdentifier.PACKAGEID=AM_WTA_ClassToClassType.PACKAGEID and AM_WTA_MethodIdentifier.CLASSID=AM_WTA_ClassToClassType.CLASSID and AM_WTA_ClassToClassType.TYPE=AM_WTA_ClassType.ID and (AM_WTA_ClassType.TYPE='Java'or AM_WTA_ClassType.TYPE='Struts')" + toappend;
/* 403 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 404 */       if (rs.next()) {
/* 405 */         map.put("Java", String.valueOf(rs.getInt("count")));
/*     */       }
/*     */       
/* 408 */       query = "select count(*) as count from AM_WTA_MethodInstance,AM_WTA_MethodIdentifier,AM_WTA_ClassToClassType,AM_WTA_ClassType where transactionid=" + tid + " and AM_WTA_MethodInstance.METHODIDENTIFIERID=AM_WTA_MethodIdentifier.ID and AM_WTA_MethodIdentifier.PACKAGEID=AM_WTA_ClassToClassType.PACKAGEID and AM_WTA_MethodIdentifier.CLASSID=AM_WTA_ClassToClassType.CLASSID and AM_WTA_ClassToClassType.TYPE=AM_WTA_ClassType.ID and (AM_WTA_ClassType.TYPE='JDBC')" + toappend;
/* 409 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 410 */       if (rs.next()) {
/* 411 */         map.put("JDBC", String.valueOf(rs.getInt("count")));
/*     */       }
/*     */       
/* 414 */       query = "select count(*) as count from AM_WTA_MethodInstance where transactionid=" + tid + toappend;
/* 415 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 416 */       if (rs.next()) {
/* 417 */         map.put("Total", String.valueOf(rs.getInt("count")));
/*     */       }
/*     */       
/* 420 */       rs.close();
/* 421 */       request.setAttribute("compCount", map);
/*     */     } finally {
/* 423 */       if (rs != null) {
/* 424 */         rs.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\wta\struts\WTAAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */