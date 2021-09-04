/*      */ package com.adventnet.appmanager.client.jdk15;
/*      */ 
/*      */ import com.adventnet.adaptors.clients.Client;
/*      */ import com.adventnet.adaptors.clients.jboss.JBossConnectException;
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.cam.CAMJMXUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.jmx.JDK15Util;
/*      */ import com.adventnet.appmanager.json.JSONUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.ResourceConfig;
/*      */ import com.adventnet.appmanager.servlets.APIServlet;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.management.scheduler.Scheduler;
/*      */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import java.io.File;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.management.remote.JMXConnector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
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
/*      */ public class JDK15Action
/*      */   extends DispatchAction
/*      */ {
/*      */   private ManagedApplication mo;
/*      */   
/*   89 */   public JDK15Action() { this.mo = new ManagedApplication(); }
/*      */   
/*      */   public ActionForward showdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*   92 */     response.setContentType("text/html; charset=UTF-8");
/*   93 */     String datatypestr = request.getParameter("datatype");
/*   94 */     int datatype = 1;
/*   95 */     String resid = request.getParameter("resourceid");
/*   96 */     Integer resourceid = Integer.valueOf(Integer.parseInt(resid));
/*      */     
/*   98 */     if (datatypestr != null) {
/*   99 */       datatype = Integer.parseInt(datatypestr);
/*      */     }
/*  101 */     if (datatype == 1) {
/*  102 */       overviewDetails(mapping, form, request, response);
/*      */     }
/*  104 */     else if (datatype == 2) {
/*  105 */       garbageDetails(mapping, form, request, response);
/*      */     }
/*  107 */     else if (datatype == 3) {
/*  108 */       threadsDetails(mapping, form, request, response);
/*      */     }
/*  110 */     else if (datatype == 4) {
/*  111 */       configurationDetails(mapping, form, request, response);
/*      */     }
/*  113 */     else if (datatype == 5) {
/*  114 */       actionDetails(mapping, form, request, response);
/*      */     }
/*  116 */     return mapping.findForward("details");
/*      */   }
/*      */   
/*      */   public ActionForward garbageDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  121 */     response.setContentType("text/html; charset=UTF-8");
/*  122 */     long coltime = 0L;
/*  123 */     float gccoltime = 0.0F;
/*  124 */     long vmstarttime = 0L;
/*  125 */     long gcstarttime = 0L;
/*  126 */     long gcendtime = 0L;
/*  127 */     ResultSet rs = null;
/*  128 */     String resid = request.getParameter("resourceid");
/*  129 */     String query = null;
/*      */     try
/*      */     {
/*  132 */       query = "select max(collectiontime) as coltime from AM_JDK15_GCINFO where resourceid=" + resid;
/*  133 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  134 */       if (rs.next()) {
/*  135 */         coltime = rs.getLong("coltime");
/*      */       }
/*  137 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  139 */       String vendor = "Sun";
/*  140 */       String jvmtype = "Java HotSpot";
/*  141 */       query = "select ATTRIBUTEID,CONFVALUE from AM_CONFIGURATION_INFO where ATTRIBUTEID in (5088,5089,5090) and LATEST=1 and resourceid=" + resid;
/*  142 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  143 */       while (rs.next())
/*      */       {
/*  145 */         if (rs.getString("ATTRIBUTEID").equals("5090")) {
/*  146 */           vendor = rs.getString("CONFVALUE");
/*  147 */         } else if (rs.getString("ATTRIBUTEID").equals("5089")) {
/*  148 */           jvmtype = rs.getString("CONFVALUE");
/*      */         } else {
/*  150 */           vmstarttime = Long.parseLong(rs.getString("CONFVALUE"));
/*      */         }
/*      */       }
/*  153 */       AMConnectionPool.closeStatement(rs);
/*  154 */       vendor = (jvmtype != null) && (jvmtype.indexOf("Java HotSpot") != -1) && (vendor != null) && (vendor.contains("Oracle")) ? "Sun Microsystems Inc." : vendor;
/*  155 */       request.setAttribute("vendor", vendor);
/*      */       
/*  157 */       boolean isMetaspace = JDK15Util.GetIsMetaspaceAsBoolean(vendor, jvmtype);
/*  158 */       request.setAttribute("isMetaspace", isMetaspace + "");
/*      */       
/*  160 */       HashMap gcmemory = new HashMap();
/*  161 */       HashMap memcomp = new HashMap();
/*  162 */       if (vendor.contains("Sun")) {
/*  163 */         SimpleDateFormat sdf = new SimpleDateFormat("MMM dd hh:mm:ss:SS");
/*  164 */         query = "select mo.DISPLAYNAME,bgc.*,ebgc.*,agc.*,eagc.*  from AM_JDK15_HEAPMEMORYINFO_BGC bgc, AM_JDK15_NONHEAPMEMORYINFO_BGC ebgc,AM_JDK15_HEAPMEMORYINFO_AGC agc, AM_JDK15_NONHEAPMEMORYINFO_AGC eagc ,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where bgc.resourceid=mo.resourceid and mo.resourceid=ebgc.resourceid and mo.resourceid=eagc.resourceid and mo.resourceid=eagc.resourceid and  bgc.resourceid=pcm.childid  and ebgc.resourceid=pcm.childid and  agc.resourceid=pcm.childid  and eagc.resourceid=pcm.childid  and pcm.parentid=" + resid + " and bgc.collectiontime=" + coltime + " and  ebgc.collectiontime=" + coltime + " and  agc.collectiontime=" + coltime + " and  eagc.collectiontime=" + coltime;
/*  165 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  166 */         ArrayList buffdata = new ArrayList();
/*  167 */         while (rs.next()) {
/*  168 */           memcomp = new HashMap();
/*  169 */           buffdata.add(rs.getString("RESOURCEID"));
/*  170 */           memcomp.put("gcresid", rs.getString("RESOURCEID"));
/*  171 */           gccoltime = (float)rs.getLong("COLTIMEMIN");
/*  172 */           memcomp.put("collectiontime", Float.valueOf(gccoltime));
/*      */           
/*  174 */           if (rs.getString("COLCOUNTMIN").equals("-1")) {
/*  175 */             memcomp.put("collection_count", "-");
/*      */           } else {
/*  177 */             memcomp.put("collection_count", rs.getString("COLCOUNTMIN"));
/*      */           }
/*  179 */           if (rs.getString("THREADCOUNT").equals("-1")) {
/*  180 */             memcomp.put("thread_count", "-");
/*      */           } else {
/*  182 */             memcomp.put("thread_count", rs.getString("THREADCOUNT"));
/*      */           }
/*      */           
/*  185 */           gcstarttime = vmstarttime + rs.getLong("STARTTIME");
/*  186 */           Date gcstart = new Date(gcstarttime);
/*  187 */           memcomp.put("starttime", sdf.format(gcstart));
/*  188 */           gcendtime = vmstarttime + rs.getLong("ENDTIME");
/*  189 */           Date gcend = new Date(gcendtime);
/*  190 */           memcomp.put("endtime", sdf.format(gcend));
/*      */           
/*  192 */           memcomp.put("heap_used_agc", rs.getString("AUSEDHEAP"));
/*  193 */           memcomp.put("heap_per_agc", rs.getString("AUSEDHEAPPER"));
/*  194 */           memcomp.put("heap_dif_agc", rs.getString("AUSEDHEAPDIFF"));
/*  195 */           memcomp.put("Eden Space_used_agc", rs.getString("AUSEDEDEN"));
/*  196 */           memcomp.put("Eden Space_per_agc", rs.getString("AEDENPER"));
/*  197 */           memcomp.put("Eden Space_dif_agc", rs.getString("AUSEDEDENDIFF"));
/*  198 */           memcomp.put("Survivor Space_used_agc", rs.getString("AUSEDSURVIVOR"));
/*  199 */           memcomp.put("Survivor Space_per_agc", rs.getString("ASURVIVORPER"));
/*  200 */           memcomp.put("Survivor Space_dif_agc", rs.getString("AUSEDSURVIVORDIFF"));
/*  201 */           memcomp.put("Tenured Gen_used_agc", rs.getString("AUSEDTENGEN"));
/*  202 */           memcomp.put("Tenured Gen_per_agc", rs.getString("ATENGENPER"));
/*  203 */           memcomp.put("Tenured Gen_dif_agc", rs.getString("AUSEDTENGENDIFF"));
/*      */           
/*      */ 
/*  206 */           memcomp.put("nonheap_used_agc", rs.getString("AUSEDNONHEAP"));
/*  207 */           memcomp.put("nonheap_per_agc", rs.getString("AUSEDNONHEAPPER"));
/*  208 */           memcomp.put("nonheap_dif_agc", rs.getString("AUSEDNONHEAPDIFF"));
/*  209 */           memcomp.put("Perm Gen_used_agc", rs.getString("AUSEDPERMGEN"));
/*  210 */           memcomp.put("Perm Gen_per_agc", rs.getString("APERMGENPER"));
/*  211 */           memcomp.put("Perm Gen_dif_agc", rs.getString("AUSEDPERMGENDIFF"));
/*  212 */           memcomp.put("Perm Gen [shared-ro]_used_agc", rs.getString("AUSEDPERMGENRO"));
/*  213 */           memcomp.put("Perm Gen [shared-ro]_per_agc", rs.getString("APERMGENROPER"));
/*  214 */           memcomp.put("Perm Gen [shared-ro]_dif_agc", rs.getString("AUSEDPERMGENRODIFF"));
/*  215 */           memcomp.put("Perm Gen [shared-rw]_used_agc", rs.getString("AUSEDPERMGENRW"));
/*  216 */           memcomp.put("Perm Gen [shared-rw]_per_agc", rs.getString("APERMGENRWPER"));
/*  217 */           memcomp.put("Perm Gen [shared-rw]_dif_agc", rs.getString("AUSEDPERMGENRWDIFF"));
/*  218 */           memcomp.put("Code Cache_used_agc", rs.getString("AUSEDCODECACHE"));
/*  219 */           memcomp.put("Code Cache_per_agc", rs.getString("ACODECACHEPER"));
/*  220 */           memcomp.put("Code Cache_dif_agc", rs.getString("AUSEDCODECACHEDIFF"));
/*      */           
/*  222 */           memcomp.put("Metaspace_used_agc", rs.getString("AUSEDMSPACE"));
/*  223 */           memcomp.put("Metaspace_per_agc", rs.getString("AMSPACEPER"));
/*  224 */           memcomp.put("Metaspace_dif_agc", rs.getString("AUSEDMSPACEDIFF"));
/*      */           
/*  226 */           memcomp.put("Compressed Class Space_used_agc", rs.getString("AUSEDCSPACE"));
/*  227 */           memcomp.put("Compressed Class Space_per_agc", rs.getString("ACSPACEPER"));
/*  228 */           memcomp.put("Compressed Class Space_dif_agc", rs.getString("AUSEDCSPACEDIFF"));
/*      */           
/*      */ 
/*  231 */           memcomp.put("heap_used_bgc", rs.getString("USEDHEAP"));
/*  232 */           memcomp.put("heap_per_bgc", rs.getString("USEDHEAPPER"));
/*  233 */           memcomp.put("Eden Space_used_bgc", rs.getString("USEDEDEN"));
/*  234 */           memcomp.put("Eden Space_per_bgc", rs.getString("EDENPER"));
/*  235 */           memcomp.put("Survivor Space_used_bgc", rs.getString("USEDSURVIVOR"));
/*  236 */           memcomp.put("Survivor Space_per_bgc", rs.getString("SURVIVORPER"));
/*  237 */           memcomp.put("Tenured Gen_used_bgc", rs.getString("USEDTENGEN"));
/*  238 */           memcomp.put("Tenured Gen_per_bgc", rs.getString("TENGENPER"));
/*      */           
/*      */ 
/*  241 */           memcomp.put("nonheap_used_bgc", rs.getString("USEDNONHEAP"));
/*  242 */           memcomp.put("nonheap_per_bgc", rs.getString("USEDNONHEAPPER"));
/*  243 */           memcomp.put("Code Cache_used_bgc", rs.getString("USEDCODECACHE"));
/*  244 */           memcomp.put("Code Cache_per_bgc", rs.getString("CODECACHEPER"));
/*  245 */           memcomp.put("Perm Gen_used_bgc", rs.getString("USEDPERMGEN"));
/*  246 */           memcomp.put("Perm Gen_per_bgc", rs.getString("PERMGENPER"));
/*  247 */           memcomp.put("Perm Gen [shared-ro]_used_bgc", rs.getString("USEDPERMGENRO"));
/*  248 */           memcomp.put("Perm Gen [shared-ro]_per_bgc", rs.getString("PERMGENROPER"));
/*  249 */           memcomp.put("Perm Gen [shared-rw]_used_bgc", rs.getString("USEDPERMGENRW"));
/*  250 */           memcomp.put("Perm Gen [shared-rw]_per_bgc", rs.getString("PERMGENRWPER"));
/*      */           
/*  252 */           memcomp.put("Metaspace_used_bgc", rs.getString("USEDMSPACE"));
/*  253 */           memcomp.put("Metaspace_per_bgc", rs.getString("MSPACEPER"));
/*      */           
/*  255 */           memcomp.put("Compressed Class Space_used_bgc", rs.getString("USEDCSPACE"));
/*  256 */           memcomp.put("Compressed Class Space_per_bgc", rs.getString("CSPACEPER"));
/*      */           
/*  258 */           gcmemory.put(rs.getString("DISPLAYNAME"), memcomp);
/*      */         }
/*      */         
/*  261 */         AMConnectionPool.closeStatement(rs);
/*  262 */         request.setAttribute("buffdata", buffdata);
/*  263 */         request.setAttribute("gcmemoryProp", gcmemory);
/*      */       }
/*      */       
/*      */ 
/*  267 */       float totgccoltime = 0.0F;
/*  268 */       query = "select * from AM_JDK15_GCINFO where RESOURCEID=" + resid + " and COLLECTIONTIME=" + coltime;
/*  269 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  270 */       memcomp = new HashMap();
/*  271 */       memcomp.put("totcoltime", "-");
/*  272 */       memcomp.put("totcolcount", "-");
/*  273 */       memcomp.put("totthreadcount", "-");
/*  274 */       memcomp.put("gctimeper", "-");
/*  275 */       if (rs.next()) {
/*  276 */         totgccoltime = (float)rs.getLong("TOTCOLTIMEDIF");
/*  277 */         memcomp.put("totcoltime", Float.valueOf(totgccoltime));
/*  278 */         memcomp.put("totcolcount", Long.valueOf(rs.getLong("TOTCOLCOUNTDIF")));
/*  279 */         memcomp.put("totthreadcount", Long.valueOf(rs.getLong("TOTTHREADCOUNT")));
/*      */       }
/*  281 */       String poll = (String)request.getAttribute("reloadperiod");
/*  282 */       HashMap map = null;
/*  283 */       if (poll == null) {
/*  284 */         poll = "300";
/*      */       }
/*  286 */       query = "select GCTIMEPER from AM_JDK15_VMINFO where resourceid=" + resid + " and COLLECTIONTIME=" + coltime;
/*  287 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  288 */       if (rs.next())
/*      */       {
/*  290 */         memcomp.put("gctimeper", Long.valueOf(rs.getLong("GCTIMEPER")));
/*      */       }
/*  292 */       AMConnectionPool.closeStatement(rs);
/*  293 */       request.setAttribute("totmemcomp", memcomp);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  297 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  300 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  302 */     return mapping.findForward("garbage");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward threadsDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  310 */     SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy-HH-mm-ss", Locale.getDefault());
/*  311 */     response.setContentType("text/html; charset=UTF-8");
/*  312 */     long coltime = 0L;
/*  313 */     ResultSet rs = null;
/*  314 */     String resid = request.getParameter("resourceid");
/*  315 */     String query = null;
/*      */     try
/*      */     {
/*  318 */       query = "select LIVE,DAEMON,RUNNABLE,BLOCKED,WAITING,TIMED_WAITING,PEAKTHREADS,TOTALSTARTED,DEADLOCKED from AM_JDK15_THREADINFO where resourceid=" + resid + " order by Collectiontime desc";
/*  319 */       query = DBQueryUtil.getTopNValues(query, "1");
/*  320 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  321 */       String live = "-";String daemon = "-";String runnable = "-";
/*  322 */       String blocked = "-";String waiting = "-";String deadlocked = "-";
/*  323 */       String timed_waiting = "-";String peakthreads = "-";String totalstarted = "-";
/*  324 */       HashMap threadProps = new HashMap();
/*  325 */       if (rs.next()) {
/*  326 */         live = rs.getString("LIVE");
/*  327 */         daemon = rs.getString("DAEMON");
/*  328 */         runnable = rs.getString("RUNNABLE");
/*  329 */         blocked = rs.getString("BLOCKED");
/*  330 */         waiting = rs.getString("WAITING");
/*  331 */         timed_waiting = rs.getString("TIMED_WAITING");
/*  332 */         peakthreads = rs.getString("PEAKTHREADS");
/*  333 */         totalstarted = rs.getString("TOTALSTARTED");
/*  334 */         deadlocked = rs.getString("DEADLOCKED");
/*      */       }
/*  336 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*      */ 
/*  339 */       threadProps.put("live", live);
/*  340 */       threadProps.put("daemon", daemon);
/*  341 */       threadProps.put("runnable", runnable);
/*  342 */       threadProps.put("blocked", blocked);
/*  343 */       threadProps.put("waiting", waiting);
/*  344 */       threadProps.put("timed_waiting", timed_waiting);
/*  345 */       threadProps.put("peakthreads", peakthreads);
/*  346 */       threadProps.put("totalstarted", totalstarted);
/*  347 */       threadProps.put("deadlocked", deadlocked);
/*      */       
/*  349 */       List Threaddump = new ArrayList();
/*  350 */       query = "select URL,COLLECTIONTIME from AM_MONITOR_DEBUG_INFO  where RESOURCEID=" + resid + " and TYPE='Thread Dump' order by Collectiontime desc";
/*  351 */       String modifiedquery = DBQueryUtil.getTopNValues(query, "5");
/*  352 */       rs = AMConnectionPool.executeQueryStmt(modifiedquery);
/*  353 */       String url = null;
/*  354 */       while (rs.next()) {
/*  355 */         Properties threaddumpurl = new Properties();
/*  356 */         threaddumpurl.setProperty("ABSURL", rs.getString("URL"));
/*  357 */         url = rs.getString("URL") + "?resourceid=" + resid;
/*  358 */         threaddumpurl.setProperty("URL", url);
/*  359 */         Date logDate = new Date(rs.getLong("COLLECTIONTIME"));
/*  360 */         threaddumpurl.setProperty("DSPNAME", sdf.format(logDate));
/*  361 */         Threaddump.add(threaddumpurl);
/*      */       }
/*  363 */       AMConnectionPool.closeStatement(rs);
/*  364 */       threadProps.put("threadurls", Threaddump);
/*  365 */       request.setAttribute("threadProps", threadProps);
/*      */       
/*      */ 
/*  368 */       query = "select count(*) as ROW_COUNT from AM_MONITOR_DEBUG_INFO  where RESOURCEID=" + resid + " and TYPE='Thread Dump'";
/*  369 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  370 */       int rowCount = 0;
/*  371 */       while (rs.next()) {
/*  372 */         rowCount = rs.getInt("ROW_COUNT");
/*      */       }
/*  374 */       AMConnectionPool.closeStatement(rs);
/*  375 */       request.setAttribute("ROW_COUNT", Integer.valueOf(rowCount));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  379 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  382 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  384 */     return mapping.findForward("threads");
/*      */   }
/*      */   
/*      */   public ActionForward deletethreadURL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  390 */     SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy-HH-mm-ss", Locale.getDefault());
/*  391 */     response.setContentType("text/html; charset=UTF-8");
/*  392 */     boolean delurl = false;
/*  393 */     if (request.getParameter("url") != null)
/*      */     {
/*  395 */       String temp = new File(".").getAbsolutePath();
/*  396 */       String nmshome = temp.substring(0, temp.length() - 2);
/*  397 */       File contentFile = new File(nmshome + request.getParameter("url"));
/*  398 */       if (contentFile.exists()) {
/*  399 */         delurl = contentFile.delete();
/*      */       }
/*  401 */       if ((delurl) || (!contentFile.exists())) {
/*      */         try {
/*  403 */           AMConnectionPool.executeUpdateStmt("delete from AM_MONITOR_DEBUG_INFO where URL ='" + request.getParameter("url") + "'");
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  407 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  412 */     if ((request.getParameter("tab") != null) && (request.getParameter("tab").equals("5")))
/*      */     {
/*  414 */       return actionDetails(mapping, form, request, response);
/*      */     }
/*  416 */     return threadsDetails(mapping, form, request, response);
/*      */   }
/*      */   
/*      */   public ActionForward deleteheapURL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  421 */     boolean delurl = false;
/*  422 */     if (request.getParameter("url") != null) {
/*      */       try
/*      */       {
/*  425 */         AMConnectionPool.executeUpdateStmt("delete from AM_MONITOR_DEBUG_INFO where URL ='" + request.getParameter("url") + "'");
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  429 */         e.printStackTrace();
/*      */       }
/*      */     }
/*  432 */     return actionDetails(mapping, form, request, response);
/*      */   }
/*      */   
/*      */   public ActionForward actionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  437 */     SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy-HH-mm-ss", Locale.getDefault());
/*  438 */     String resid = request.getParameter("resourceid");
/*  439 */     HashMap threadProps = new HashMap();
/*  440 */     List Threaddump = new ArrayList();
/*  441 */     ResultSet rs = null;
/*      */     try {
/*  443 */       String query = "select URL,COLLECTIONTIME from AM_MONITOR_DEBUG_INFO  where RESOURCEID=" + resid + " and TYPE='Thread Dump' order by Collectiontime desc";
/*  444 */       String modifiedquery = DBQueryUtil.getTopNValues(query, "5");
/*  445 */       rs = AMConnectionPool.executeQueryStmt(modifiedquery);
/*  446 */       String url = null;
/*  447 */       while (rs.next()) {
/*  448 */         Properties threaddumpurl = new Properties();
/*  449 */         threaddumpurl.setProperty("ABSURL", rs.getString("URL"));
/*  450 */         url = rs.getString("URL") + "?resourceid=" + resid;
/*  451 */         threaddumpurl.setProperty("URL", url);
/*  452 */         Date logDate = new Date(rs.getLong("COLLECTIONTIME"));
/*  453 */         threaddumpurl.setProperty("DSPNAME", sdf.format(logDate));
/*  454 */         Threaddump.add(threaddumpurl);
/*      */       }
/*  456 */       AMConnectionPool.closeStatement(rs);
/*  457 */       threadProps.put("threadurls", Threaddump);
/*  458 */       request.setAttribute("threadProps", threadProps);
/*      */       
/*      */ 
/*  461 */       query = "select count(*) as ROW_COUNT from AM_MONITOR_DEBUG_INFO  where RESOURCEID=" + resid + " and TYPE='Thread Dump'";
/*  462 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  463 */       int rowCount = 0;
/*  464 */       while (rs.next()) {
/*  465 */         rowCount = rs.getInt("ROW_COUNT");
/*      */       }
/*  467 */       AMConnectionPool.closeStatement(rs);
/*  468 */       request.setAttribute("ROW_COUNT", Integer.valueOf(rowCount));
/*      */       
/*      */ 
/*  471 */       List Heapdump = new ArrayList();
/*  472 */       query = "select URL,COLLECTIONTIME from AM_MONITOR_DEBUG_INFO  where RESOURCEID=" + resid + " and TYPE='Heap Dump' order by Collectiontime desc";
/*  473 */       modifiedquery = DBQueryUtil.getTopNValues(query, "5");
/*  474 */       rs = AMConnectionPool.executeQueryStmt(modifiedquery);
/*  475 */       while (rs.next()) {
/*  476 */         Heapdump.add(rs.getString("URL"));
/*      */       }
/*  478 */       AMConnectionPool.closeStatement(rs);
/*  479 */       threadProps.put("Heapdump", Heapdump);
/*      */       
/*  481 */       query = "select count(*) as ROW_COUNT from AM_MONITOR_DEBUG_INFO  where RESOURCEID=" + resid + " and TYPE='Heap Dump'";
/*  482 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  483 */       int hrowCount = 0;
/*  484 */       while (rs.next()) {
/*  485 */         hrowCount = rs.getInt("ROW_COUNT");
/*      */       }
/*  487 */       AMConnectionPool.closeStatement(rs);
/*  488 */       request.setAttribute("HROW_COUNT", Integer.valueOf(hrowCount));
/*      */       
/*  490 */       query = "select CONFVALUE from AM_CONFIGURATION_INFO where  RESOURCEID=" + resid + " and ATTRIBUTEID=5104 and LATEST=1";
/*  491 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  492 */       String javaHome = null;
/*  493 */       if (rs.next()) {
/*  494 */         javaHome = rs.getString("CONFVALUE");
/*      */       }
/*      */       
/*  497 */       request.setAttribute("javaHome", javaHome);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  502 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  505 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  507 */     return mapping.findForward("actions");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward configurationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  514 */     String resid = request.getParameter("resourceid");
/*  515 */     SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
/*      */     
/*  517 */     long coltime = 0L;
/*  518 */     ResultSet rs = null;
/*  519 */     String query = null;
/*  520 */     String vendor = "Sun";
/*  521 */     String jvmtype = "Java HotSpot";
/*      */     
/*      */     try
/*      */     {
/*  525 */       query = "select A.ATTRIBUTEID,B.ATTRIBUTE,A.CONFVALUE from  AM_CONFIGURATION_INFO A,AM_ATTRIBUTES B where A.ATTRIBUTEID=B.ATTRIBUTEID and A.LATEST=1 and  A.ATTRIBUTEID IN (5088,5089,5090,5091,5092,5093,5094,5095,5096,5097,5098,5103) and A.RESOURCEID=" + resid;
/*  526 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  527 */       HashMap osProp = new HashMap();
/*  528 */       while (rs.next()) {
/*  529 */         if ("5089".equals(rs.getString("ATTRIBUTEID"))) {
/*  530 */           jvmtype = rs.getString("CONFVALUE");
/*      */         }
/*  532 */         if ("5090".equals(rs.getString("ATTRIBUTEID"))) {
/*  533 */           vendor = rs.getString("CONFVALUE");
/*      */         }
/*  535 */         osProp.put(rs.getString("ATTRIBUTE"), rs.getString("CONFVALUE"));
/*      */       }
/*  537 */       AMConnectionPool.closeStatement(rs);
/*  538 */       request.setAttribute("osProp", osProp);
/*      */       
/*  540 */       vendor = (jvmtype != null) && (jvmtype.indexOf("Java HotSpot") != -1) && (vendor != null) && (vendor.contains("Oracle")) ? "Sun Microsystems Inc." : vendor;
/*  541 */       request.setAttribute("vendor", vendor);
/*      */       
/*  543 */       boolean isMetaspace = JDK15Util.GetIsMetaspaceAsBoolean(vendor, jvmtype);
/*  544 */       request.setAttribute("isMetaspace", isMetaspace + "");
/*      */       
/*  546 */       query = "select * from AM_JDK15_VMINFO where RESOURCEID=" + resid + " order by collectiontime desc";
/*  547 */       query = DBQueryUtil.getTopNValues(query, "1");
/*  548 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  549 */       StringBuilder uptimehm = new StringBuilder();
/*  550 */       if (rs.next()) {
/*  551 */         System.out.println("Uptime for JDK15 ==========>" + rs.getLong("UPTIME"));
/*  552 */         long uptime = rs.getLong("UPTIME") / 1000L;
/*  553 */         long seconds = uptime % 60L;
/*  554 */         uptime /= 60L;
/*  555 */         long minutes = uptime % 60L;
/*  556 */         uptime /= 60L;
/*  557 */         long hours = uptime % 24L;
/*  558 */         uptime /= 24L;
/*  559 */         long days = uptime;
/*  560 */         uptimehm.append(days).append(" : Days ").append(hours).append(" : Hours ").append(minutes).append(" : Minutes");
/*      */       }
/*  562 */       AMConnectionPool.closeStatement(rs);
/*  563 */       request.setAttribute("uptime", uptimehm);
/*      */       
/*  565 */       String confkey = null;
/*  566 */       query = "select A.ATTRIBUTEID,B.ATTRIBUTE,A.CONFVALUE from  AM_CONFIGURATION_INFO A,AM_ATTRIBUTES B where A.ATTRIBUTEID=B.ATTRIBUTEID and A.LATEST=1 and  A.ATTRIBUTEID NOT IN(5088,5089,5090,5091,5092,5093,5094,5095,5096,5097,5098,5101,5102,5103,5104) and A.RESOURCEID=" + resid;
/*  567 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  568 */       HashMap vmargsProp = new HashMap();
/*  569 */       Properties osProplnk = new Properties();
/*  570 */       while (rs.next()) {
/*  571 */         if ("5105".equals(rs.getString("ATTRIBUTEID"))) {
/*  572 */           if (!"-1".equals(rs.getString("CONFVALUE"))) {
/*  573 */             vmargsProp.put(rs.getString("ATTRIBUTE"), rs.getString("CONFVALUE"));
/*  574 */             osProplnk.setProperty(rs.getString("ATTRIBUTE"), rs.getString("ATTRIBUTEID"));
/*      */           }
/*      */         }
/*      */         else {
/*  578 */           vmargsProp.put(rs.getString("ATTRIBUTE"), rs.getString("CONFVALUE"));
/*  579 */           osProplnk.setProperty(rs.getString("ATTRIBUTE"), rs.getString("ATTRIBUTEID"));
/*      */         }
/*      */       }
/*  582 */       AMConnectionPool.closeStatement(rs);
/*  583 */       request.setAttribute("vmargsProp", vmargsProp);
/*  584 */       request.setAttribute("osProplnk", osProplnk);
/*      */       
/*  586 */       HashMap memProp = new HashMap();
/*  587 */       memProp.put("totalmemory", "-");
/*  588 */       memProp.put("freememory", "-");
/*  589 */       memProp.put("totalswapspace", "-");
/*  590 */       memProp.put("freeswapspace", "-");
/*  591 */       memProp.put("commitedvirmem", "-");
/*  592 */       memProp.put("objectpending", "-");
/*      */       
/*      */ 
/*  595 */       query = "select TOTALMEMORY,FREEMEMORY,TOTALSWAPSPACE,FREESWAPSPACE,COMMITEDVIRMEM,OBJECTPENDING from AM_JDK15_MEMORYINFO where RESOURCEID=" + resid + " order by collectiontime desc";
/*  596 */       query = DBQueryUtil.getTopNValues(query, "1");
/*  597 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  598 */       long totalmemory = -1L;
/*  599 */       long freememory = -1L;
/*  600 */       if (rs.next()) {
/*  601 */         totalmemory = rs.getLong("TOTALMEMORY");
/*  602 */         freememory = rs.getLong("FREEMEMORY");
/*  603 */         memProp.put("totalmemory", Long.valueOf(totalmemory / 1048576L));
/*  604 */         memProp.put("freememory", Long.valueOf(freememory / 1048576L));
/*  605 */         memProp.put("totalswapspace", rs.getString("TOTALSWAPSPACE"));
/*  606 */         memProp.put("freeswapspace", rs.getString("FREESWAPSPACE"));
/*  607 */         memProp.put("commitedvirmem", rs.getString("COMMITEDVIRMEM"));
/*  608 */         memProp.put("objectpending", rs.getString("OBJECTPENDING"));
/*      */       }
/*  610 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  612 */       query = "select MAXHEAP,COLLECTIONTIME from AM_JDK15_HEAPMEMORYINFO where RESOURCEID=" + resid + " order by collectiontime desc";
/*  613 */       query = DBQueryUtil.getTopNValues(query, "1");
/*  614 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  615 */       long maxheap = -1L;
/*  616 */       if (rs.next()) {
/*  617 */         maxheap = rs.getLong("maxheap");
/*  618 */         coltime = rs.getLong("COLLECTIONTIME");
/*      */       }
/*  620 */       AMConnectionPool.closeStatement(rs);
/*  621 */       memProp.put("maxheap", Long.valueOf(maxheap / 1048576L));
/*      */       
/*  623 */       query = "select MAXNONHEAP from AM_JDK15_NONHEAPMEMORYINFO where RESOURCEID=" + resid + " order by collectiontime desc";
/*  624 */       query = DBQueryUtil.getTopNValues(query, "1");
/*  625 */       long maxnonheap = -1L;
/*      */       try {
/*  627 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  628 */         if (rs.next()) {
/*  629 */           maxnonheap = rs.getLong(1);
/*      */         }
/*      */       } catch (Exception e) {
/*  632 */         e.printStackTrace();
/*      */       }
/*      */       finally {}
/*      */       
/*  636 */       memProp.put("maxnonheap", maxnonheap / 1048576L + "");
/*      */       
/*      */ 
/*  639 */       request.setAttribute("memProp", memProp);
/*      */       
/*      */ 
/*      */ 
/*  643 */       float gccoltime = 0.0F;
/*      */       
/*      */ 
/*  646 */       Object gcinfoProp = new ArrayList();
/*  647 */       query = "select mo.DISPLAYNAME,bi.COLTIME,bi.COLCOUNT,bi.RESOURCEID from AM_JDK15_HEAPMEMORYINFO_BGC bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.resourceid=bi.resourceid and bi.resourceid=pcm.childid and pcm.parentid=" + resid + " and collectiontime=" + coltime;
/*  648 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  649 */       while (rs.next()) {
/*  650 */         Map gcinfo = new HashMap();
/*  651 */         gcinfo.put("gcname", rs.getString("DISPLAYNAME"));
/*  652 */         gccoltime = (float)(rs.getLong("COLTIME") / 1000L);
/*  653 */         gcinfo.put("gccoltime", Float.valueOf(gccoltime));
/*  654 */         gcinfo.put("gccolcount", rs.getString("COLCOUNT"));
/*  655 */         gcinfo.put("gcresid", rs.getString("RESOURCEID"));
/*  656 */         ((List)gcinfoProp).add(gcinfo);
/*      */       }
/*  658 */       AMConnectionPool.closeStatement(rs);
/*  659 */       request.setAttribute("gcinfoProp", gcinfoProp);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  663 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  666 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  668 */     return mapping.findForward("configuration");
/*      */   }
/*      */   
/*      */   public ActionForward overviewDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  673 */     long coltime = 0L;
/*  674 */     ResultSet rs = null;
/*  675 */     String resid = request.getParameter("resourceid");
/*  676 */     String query = null;
/*      */     try
/*      */     {
/*  679 */       query = "select displayname from AM_ManagedObject where resourceid=" + resid;
/*  680 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  681 */       if (rs.next()) {
/*  682 */         ((AMActionForm)form).setDisplayname(rs.getString("displayname"));
/*  683 */         request.setAttribute("monitorname", rs.getString("displayname"));
/*      */       }
/*  685 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*      */ 
/*  688 */       rs = AMConnectionPool.executeQueryStmt("select ATTRIBUTEID,CONFVALUE from AM_CONFIGURATION_INFO where RESOURCEID=" + resid + " and ATTRIBUTEID in (5089,5090) and LATEST=1 order by ATTRIBUTEID");
/*  689 */       String vendor = "Sun";
/*  690 */       String jvmtype = "Java HotSpot";
/*  691 */       while (rs.next()) {
/*  692 */         if (rs.getString("ATTRIBUTEID").equals("5090")) {
/*  693 */           vendor = rs.getString("CONFVALUE");
/*      */         } else {
/*  695 */           jvmtype = rs.getString("CONFVALUE");
/*      */         }
/*      */       }
/*  698 */       AMConnectionPool.closeStatement(rs);
/*  699 */       vendor = (jvmtype != null) && (jvmtype.indexOf("Java HotSpot") != -1) && (vendor != null) && (vendor.contains("Oracle")) ? "Sun Microsystems Inc." : vendor;
/*  700 */       request.setAttribute("vendor", vendor);
/*      */       
/*  702 */       boolean isMetaspace = JDK15Util.GetIsMetaspaceAsBoolean(vendor, jvmtype);
/*  703 */       request.setAttribute("isMetaspace", isMetaspace + "");
/*      */       
/*  705 */       query = "select databasename as jndiurl,username," + DBQueryUtil.decode("AM_RESOURCECONFIG.PASSWORD") + " as password from AM_RESOURCECONFIG where resourceid=" + resid;
/*  706 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  707 */       if (rs.next()) {
/*  708 */         ((AMActionForm)form).setJndiurl(rs.getString("JNDIURL"));
/*  709 */         ((AMActionForm)form).setUsername(rs.getString("username"));
/*  710 */         ((AMActionForm)form).setPassword(rs.getString("password"));
/*      */       }
/*  712 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  714 */       query = "select pollinterval,APPLNDISCPORT from CollectData cd,AM_ManagedObject mo where mo.resourceid=" + resid + " and mo.resourcename=cd.resourcename";
/*  715 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  716 */       if (rs.next()) {
/*  717 */         int poll = rs.getInt("pollinterval") / 60;
/*  718 */         ((AMActionForm)form).setPollInterval(poll);
/*  719 */         ((AMActionForm)form).setPort(rs.getString("APPLNDISCPORT"));
/*      */       }
/*  721 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  723 */       query = "select A.ATTRIBUTEID,B.ATTRIBUTE,A.CONFVALUE from AM_CONFIGURATION_INFO A,AM_ATTRIBUTES B where A.ATTRIBUTEID=B.ATTRIBUTEID and A.LATEST=1 and  A.ATTRIBUTEID IN (5088,5089,5090,5091,5092,5093,5094,5095,5096,5097,5098) and A.RESOURCEID=" + resid;
/*  724 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  725 */       HashMap osProp = new HashMap();
/*  726 */       while (rs.next()) {
/*  727 */         osProp.put(rs.getString("ATTRIBUTE"), rs.getString("CONFVALUE"));
/*      */       }
/*  729 */       AMConnectionPool.closeStatement(rs);
/*  730 */       request.setAttribute("osProp", osProp);
/*      */       
/*      */ 
/*  733 */       HashMap memoryProps = new HashMap();
/*  734 */       memoryProps.put("totalswapspace", "-");
/*  735 */       memoryProps.put("freeswapspace", "-");
/*  736 */       memoryProps.put("commitedvirmem", "-");
/*      */       
/*  738 */       query = "select TOTALMEMORY,FREEMEMORY,CPUTIMEPER,PROCESSMEMORY,PROCESSMEMORYPER,TOTALSWAPSPACE,FREESWAPSPACE,COMMITEDVIRMEM,COLLECTIONTIME,VMCPULOAD from AM_JDK15_MEMORYINFO where resourceid=" + resid + " order by collectiontime desc";
/*  739 */       query = DBQueryUtil.getTopNValues(query, "1");
/*  740 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  741 */       long totalmemory = 0L;
/*  742 */       long freememory = 0L;
/*  743 */       long cpuper = 0L;
/*  744 */       long cpuload = 0L;
/*  745 */       String cpuperstr = "0";
/*  746 */       long processmemory = 0L;
/*  747 */       long processmemoryper = 0L;
/*  748 */       if (rs.next()) {
/*  749 */         totalmemory = rs.getLong("TOTALMEMORY");
/*  750 */         freememory = rs.getLong("FREEMEMORY");
/*  751 */         cpuper = rs.getLong("CPUTIMEPER");
/*  752 */         coltime = rs.getLong("COLLECTIONTIME");
/*      */         
/*  754 */         if (rs.getLong("VMCPULOAD") != -1L) {
/*  755 */           cpuload = rs.getLong("VMCPULOAD");
/*      */         }
/*      */         
/*  758 */         if (cpuper != -1L)
/*      */         {
/*  760 */           cpuperstr = String.valueOf(cpuper);
/*      */         }
/*      */         else
/*      */         {
/*  764 */           cpuperstr = "-";
/*      */         }
/*  766 */         processmemory = rs.getLong("PROCESSMEMORY");
/*  767 */         processmemoryper = rs.getLong("PROCESSMEMORYPER");
/*      */         
/*  769 */         memoryProps.put("totalswapspace", rs.getString("TOTALSWAPSPACE"));
/*  770 */         memoryProps.put("freeswapspace", rs.getString("FREESWAPSPACE"));
/*  771 */         memoryProps.put("commitedvirmem", rs.getString("COMMITEDVIRMEM"));
/*      */       }
/*  773 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  775 */       memoryProps.put("cpuload", Long.valueOf(cpuload));
/*  776 */       memoryProps.put("commitheap", "-");
/*  777 */       memoryProps.put("initialheap", "-");
/*  778 */       memoryProps.put("commitden", "-");
/*  779 */       memoryProps.put("initden", "-");
/*  780 */       memoryProps.put("commitsurvivor", "-");
/*  781 */       memoryProps.put("initsurvivor", "-");
/*  782 */       memoryProps.put("committengen", "-");
/*  783 */       memoryProps.put("inittengen", "-");
/*      */       
/*  785 */       memoryProps.put("initcodecache", "-");
/*  786 */       memoryProps.put("commitcodecache", "-");
/*  787 */       memoryProps.put("initpermgrnrw", "-");
/*  788 */       memoryProps.put("commitpermgrnrw", "-");
/*  789 */       memoryProps.put("initpermgrnro", "-");
/*  790 */       memoryProps.put("commitpermgrnro", "-");
/*  791 */       memoryProps.put("initpermgen", "-");
/*  792 */       memoryProps.put("commitpermgen", "-");
/*  793 */       memoryProps.put("initialnonheap", "-");
/*  794 */       memoryProps.put("commitnonheap", "-");
/*      */       
/*  796 */       long maxheap = -1L;long heap = -1L;long heapper = -1L;
/*  797 */       long maxnonheap = -1L;long nonheap = -1L;long nonheapper = -1L;
/*      */       
/*      */ 
/*  800 */       long maxeden = -1L;long eden = -1L;long edenper = -1L;
/*  801 */       long maxsurvivor = -1L;long survivor = -1L;long survivorper = -1L;
/*  802 */       long maxtengen = -1L;long tengen = -1L;long tengenper = -1L;
/*      */       
/*  804 */       long maxpermgen = -1L;long permgen = -1L;long permgenper = -1L;
/*  805 */       long maxpermgenro = -1L;long permgenro = -1L;long permgenroper = -1L;
/*  806 */       long maxpermgenrw = -1L;long permgenrw = -1L;long permgenrwper = -1L;
/*  807 */       long maxcodecache = -1L;long codecache = -1L;long codecacheper = -1L;
/*      */       
/*      */ 
/*      */ 
/*  811 */       memoryProps.put("MAXJAVAHEAP", "-");
/*  812 */       memoryProps.put("JAVAHEAP", "-");
/*  813 */       memoryProps.put("COMMITJAVAHEAP", "-");
/*  814 */       memoryProps.put("INITJAVAHEAP", "-");
/*  815 */       memoryProps.put("PERJAVAHEAP", new Long(-1L));
/*      */       
/*  817 */       memoryProps.put("MAXJITCCACHE", "-");
/*  818 */       memoryProps.put("JITCCACHE", "-");
/*  819 */       memoryProps.put("INITJITCCACHE", "-");
/*  820 */       memoryProps.put("COMMITJITCCACHE", "-");
/*  821 */       memoryProps.put("PERJITCCACHE", new Long(-1L));
/*  822 */       memoryProps.put("MAXJITDCACHE", "-");
/*  823 */       memoryProps.put("JITDCACHE", "-");
/*  824 */       memoryProps.put("INITJITDCACHE", "-");
/*  825 */       memoryProps.put("COMMITJITDCACHE", "-");
/*  826 */       memoryProps.put("PERJITDCACHE", new Long(-1L));
/*  827 */       memoryProps.put("MAXCLASSSTOR", "-");
/*  828 */       memoryProps.put("CLASSSTOR", "-");
/*  829 */       memoryProps.put("COMMITCLASSSTOR", "-");
/*  830 */       memoryProps.put("INITCLASSSTOR", "-");
/*  831 */       memoryProps.put("PERCLASSSTOR", new Long(-1L));
/*  832 */       memoryProps.put("MAXNONHPSTOR", "-");
/*  833 */       memoryProps.put("NONHPSTOR", "-");
/*  834 */       memoryProps.put("COMMITNONHPSTOR", "-");
/*  835 */       memoryProps.put("INITNONHPSTOR", "-");
/*  836 */       memoryProps.put("PERNONHPSTOR", new Long(-1L));
/*      */       
/*  838 */       if (vendor.contains("IBM"))
/*      */       {
/*      */ 
/*  841 */         query = "select maxheap,usedheap,usedheapper,COMMITHEAP,INITHEAP,MAXJAVAHEAP,JAVAHEAP,COMMITJAVAHEAP,INITJAVAHEAP,PERJAVAHEAP from AM_JDK15_HEAPMEMORYINFO,AM_JDK15_HEAPMEMORYINFO_EXT where AM_JDK15_HEAPMEMORYINFO.resourceid=" + resid + " and AM_JDK15_HEAPMEMORYINFO.RESOURCEID=AM_JDK15_HEAPMEMORYINFO_EXT.RESOURCEID and AM_JDK15_HEAPMEMORYINFO.COLLECTIONTIME=AM_JDK15_HEAPMEMORYINFO_EXT.COLLECTIONTIME and AM_JDK15_HEAPMEMORYINFO.collectiontime=" + coltime;
/*  842 */         rs = AMConnectionPool.executeQueryStmt(query);
/*      */         
/*  844 */         if (rs.next()) {
/*  845 */           maxheap = rs.getLong("maxheap");
/*  846 */           heap = rs.getLong("usedheap");
/*  847 */           heapper = rs.getLong("usedheapper");
/*      */           
/*  849 */           if (rs.getLong("COMMITHEAP") == -1L) {
/*  850 */             memoryProps.put("commitheap", "-");
/*      */           } else {
/*  852 */             memoryProps.put("commitheap", Long.valueOf(rs.getLong("COMMITHEAP")));
/*      */           }
/*  854 */           if (rs.getLong("INITHEAP") == -1L) {
/*  855 */             memoryProps.put("initialheap", "-");
/*      */           } else {
/*  857 */             memoryProps.put("initialheap", Long.valueOf(rs.getLong("INITHEAP")));
/*      */           }
/*      */           
/*  860 */           if (rs.getLong("MAXJAVAHEAP") != -1L) {
/*  861 */             memoryProps.put("MAXJAVAHEAP", Long.valueOf(rs.getLong("MAXJAVAHEAP") / 1048576L));
/*      */           }
/*  863 */           if (rs.getLong("JAVAHEAP") != -1L) {
/*  864 */             memoryProps.put("JAVAHEAP", Long.valueOf(rs.getLong("JAVAHEAP") / 1048576L));
/*      */           }
/*  866 */           if (rs.getLong("COMMITJAVAHEAP") != -1L) {
/*  867 */             memoryProps.put("COMMITJAVAHEAP", Long.valueOf(rs.getLong("COMMITJAVAHEAP")));
/*      */           }
/*  869 */           if (rs.getLong("INITJAVAHEAP") != -1L) {
/*  870 */             memoryProps.put("INITJAVAHEAP", Long.valueOf(rs.getLong("INITJAVAHEAP")));
/*      */           }
/*  872 */           if (rs.getLong("PERJAVAHEAP") != -1L) {
/*  873 */             memoryProps.put("PERJAVAHEAP", Long.valueOf(rs.getLong("PERJAVAHEAP")));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  879 */         query = "select maxnonheap,usednonheap,usednonheapper,COMMITNONHEAP,INITNONHEAP,MAXJITCCACHE,JITCCACHE,INITJITCCACHE,COMMITJITCCACHE,PERJITCCACHE,MAXJITDCACHE,JITDCACHE,INITJITDCACHE,COMMITJITDCACHE,PERJITDCACHE,MAXCLASSSTOR,CLASSSTOR,COMMITCLASSSTOR,INITCLASSSTOR,PERCLASSSTOR,MAXNONHPSTOR,NONHPSTOR,COMMITNONHPSTOR,INITNONHPSTOR,PERNONHPSTOR  from  AM_JDK15_NONHEAPMEMORYINFO,AM_JDK15_NONHEAPMEMORYINFO_EXT  where AM_JDK15_NONHEAPMEMORYINFO.resourceid=" + resid + " and AM_JDK15_NONHEAPMEMORYINFO_EXT.RESOURCEID=AM_JDK15_NONHEAPMEMORYINFO.RESOURCEID and AM_JDK15_NONHEAPMEMORYINFO_EXT.COLLECTIONTIME=AM_JDK15_NONHEAPMEMORYINFO.COLLECTIONTIME and AM_JDK15_NONHEAPMEMORYINFO.collectiontime=" + coltime;
/*  880 */         rs = AMConnectionPool.executeQueryStmt(query);
/*      */         
/*  882 */         if (rs.next())
/*      */         {
/*  884 */           maxnonheap = rs.getLong("maxnonheap");
/*  885 */           nonheap = rs.getLong("usednonheap");
/*  886 */           nonheapper = rs.getLong("usednonheapper");
/*      */           
/*      */ 
/*  889 */           if (rs.getLong("COMMITNONHEAP") == -1L) {
/*  890 */             memoryProps.put("commitnonheap", "-");
/*      */           } else {
/*  892 */             memoryProps.put("commitnonheap", Long.valueOf(rs.getLong("COMMITNONHEAP")));
/*      */           }
/*  894 */           if (rs.getLong("INITNONHEAP") == -1L) {
/*  895 */             memoryProps.put("initialnonheap", "-");
/*      */           } else {
/*  897 */             memoryProps.put("initialnonheap", Long.valueOf(rs.getLong("INITNONHEAP")));
/*      */           }
/*      */           
/*  900 */           if (rs.getLong("MAXJITCCACHE") != -1L) {
/*  901 */             memoryProps.put("MAXJITCCACHE", Long.valueOf(rs.getLong("MAXJITCCACHE") / 1048576L));
/*      */           }
/*      */           
/*      */ 
/*  905 */           if (rs.getLong("JITCCACHE") != -1L) {
/*  906 */             memoryProps.put("JITCCACHE", Long.valueOf(rs.getLong("JITCCACHE") / 1048576L));
/*      */           }
/*  908 */           if (rs.getLong("INITJITCCACHE") != -1L) {
/*  909 */             memoryProps.put("INITJITCCACHE", Long.valueOf(rs.getLong("INITJITCCACHE")));
/*      */           }
/*  911 */           if (rs.getLong("COMMITJITCCACHE") != -1L) {
/*  912 */             memoryProps.put("COMMITJITCCACHE", Long.valueOf(rs.getLong("COMMITJITCCACHE")));
/*      */           }
/*      */           
/*  915 */           memoryProps.put("PERJITCCACHE", Long.valueOf(rs.getLong("PERJITCCACHE")));
/*      */           
/*  917 */           if (rs.getLong("MAXJITDCACHE") != -1L) {
/*  918 */             memoryProps.put("MAXJITDCACHE", Long.valueOf(rs.getLong("MAXJITDCACHE") / 1048576L));
/*      */           }
/*  920 */           if (rs.getLong("JITDCACHE") != -1L) {
/*  921 */             memoryProps.put("JITDCACHE", Long.valueOf(rs.getLong("JITDCACHE") / 1048576L));
/*      */           }
/*  923 */           if (rs.getLong("INITJITDCACHE") != -1L) {
/*  924 */             memoryProps.put("INITJITDCACHE", Long.valueOf(rs.getLong("INITJITDCACHE")));
/*      */           }
/*      */           
/*  927 */           if (rs.getLong("COMMITJITDCACHE") != -1L) {
/*  928 */             memoryProps.put("COMMITJITDCACHE", Long.valueOf(rs.getLong("COMMITJITDCACHE")));
/*      */           }
/*      */           
/*  931 */           memoryProps.put("PERJITDCACHE", Long.valueOf(rs.getLong("PERJITDCACHE")));
/*      */           
/*  933 */           if (rs.getLong("MAXCLASSSTOR") != -1L) {
/*  934 */             memoryProps.put("MAXCLASSSTOR", Long.valueOf(rs.getLong("MAXCLASSSTOR") / 1048576L));
/*      */           }
/*  936 */           if (rs.getLong("CLASSSTOR") != -1L) {
/*  937 */             memoryProps.put("CLASSSTOR", Long.valueOf(rs.getLong("CLASSSTOR") / 1048576L));
/*      */           }
/*  939 */           if (rs.getLong("COMMITCLASSSTOR") != -1L) {
/*  940 */             memoryProps.put("COMMITCLASSSTOR", Long.valueOf(rs.getLong("COMMITCLASSSTOR")));
/*      */           }
/*  942 */           if (rs.getLong("INITCLASSSTOR") != -1L) {
/*  943 */             memoryProps.put("INITCLASSSTOR", Long.valueOf(rs.getLong("INITCLASSSTOR")));
/*      */           }
/*      */           
/*  946 */           memoryProps.put("PERCLASSSTOR", Long.valueOf(rs.getLong("PERCLASSSTOR")));
/*      */           
/*  948 */           if (rs.getLong("MAXNONHPSTOR") != -1L) {
/*  949 */             memoryProps.put("MAXNONHPSTOR", Long.valueOf(rs.getLong("MAXNONHPSTOR") / 1048576L));
/*      */           }
/*  951 */           if (rs.getLong("NONHPSTOR") != -1L) {
/*  952 */             memoryProps.put("NONHPSTOR", Long.valueOf(rs.getLong("NONHPSTOR") / 1048576L));
/*      */           }
/*  954 */           if (rs.getLong("COMMITNONHPSTOR") != -1L) {
/*  955 */             memoryProps.put("COMMITNONHPSTOR", Long.valueOf(rs.getLong("COMMITNONHPSTOR")));
/*      */           }
/*  957 */           if (rs.getLong("INITNONHPSTOR") != -1L) {
/*  958 */             memoryProps.put("INITNONHPSTOR", Long.valueOf(rs.getLong("INITNONHPSTOR")));
/*      */           }
/*      */           
/*  961 */           memoryProps.put("PERNONHPSTOR", Long.valueOf(rs.getLong("PERNONHPSTOR")));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  970 */       memoryProps.put("MAXJJAVAHEAP", "-");
/*  971 */       memoryProps.put("JJAVAHEAP", "-");
/*  972 */       memoryProps.put("COMMITJJAVAHEAP", "-");
/*  973 */       memoryProps.put("INITJJAVAHEAP", "-");
/*  974 */       memoryProps.put("PERJJAVAHEAP", new Long(-1L));
/*  975 */       memoryProps.put("MAXNURSERY", "-");
/*  976 */       memoryProps.put("NURSERY", "-");
/*  977 */       memoryProps.put("COMMITNURSERY", "-");
/*  978 */       memoryProps.put("INITNURSERY", "-");
/*  979 */       memoryProps.put("PERNURSERY", new Long(-1L));
/*      */       
/*      */ 
/*      */ 
/*  983 */       if ((vendor.contains("BEA")) || (vendor.contains("Oracle")))
/*      */       {
/*  985 */         memoryProps.put("MAXOLDSPACE", "-");
/*  986 */         memoryProps.put("OLDSPACE", "-");
/*  987 */         memoryProps.put("COMMITOLDSPACE", "-");
/*  988 */         memoryProps.put("INITOLDSPACE", "-");
/*  989 */         memoryProps.put("PEROLDSPACE", new Long(-1L));
/*  990 */         memoryProps.put("MAXCLASSMEM", "-");
/*  991 */         memoryProps.put("CLASSMEM", "-");
/*  992 */         memoryProps.put("COMMITCLASSMEM", "-");
/*  993 */         memoryProps.put("INITCLASSMEM", "-");
/*  994 */         memoryProps.put("PERCLASSMEM", new Long(-1L));
/*  995 */         memoryProps.put("MAXCLASSBLOCK", "-");
/*  996 */         memoryProps.put("CLASSBLOCK", "-");
/*  997 */         memoryProps.put("COMMITCLASSBLOCK", "-");
/*  998 */         memoryProps.put("INITCLASSBLOCK", "-");
/*  999 */         memoryProps.put("PERCLASSBLOCK", new Long(-1L));
/*      */         
/* 1001 */         query = "select maxheap,usedheap,usedheapper,COMMITHEAP,INITHEAP,MAXJJAVAHEAP,JJAVAHEAP,COMMITJJAVAHEAP,INITJJAVAHEAP,PERJJAVAHEAP,MAXNURSERY,NURSERY,COMMITNURSERY,INITNURSERY,PERNURSERY,MAXOLDSPACE,OLDSPACE,COMMITOLDSPACE,INITOLDSPACE,PEROLDSPACE from AM_JDK15_HEAPMEMORYINFO,AM_JDK15_HEAPMEMORYINFO_EXT where AM_JDK15_HEAPMEMORYINFO.resourceid=" + resid + " and AM_JDK15_HEAPMEMORYINFO.RESOURCEID=AM_JDK15_HEAPMEMORYINFO_EXT.RESOURCEID and AM_JDK15_HEAPMEMORYINFO.COLLECTIONTIME=AM_JDK15_HEAPMEMORYINFO_EXT.COLLECTIONTIME and AM_JDK15_HEAPMEMORYINFO.collectiontime=" + coltime;
/* 1002 */         rs = AMConnectionPool.executeQueryStmt(query);
/*      */         
/* 1004 */         if (rs.next()) {
/* 1005 */           maxheap = rs.getLong("maxheap");
/* 1006 */           heap = rs.getLong("usedheap");
/* 1007 */           heapper = rs.getLong("usedheapper");
/*      */           
/* 1009 */           if (rs.getLong("COMMITHEAP") == -1L) {
/* 1010 */             memoryProps.put("commitheap", "-");
/*      */           } else {
/* 1012 */             memoryProps.put("commitheap", Long.valueOf(rs.getLong("COMMITHEAP")));
/*      */           }
/* 1014 */           if (rs.getLong("INITHEAP") == -1L) {
/* 1015 */             memoryProps.put("initialheap", "-");
/*      */           } else {
/* 1017 */             memoryProps.put("initialheap", Long.valueOf(rs.getLong("INITHEAP")));
/*      */           }
/*      */           
/* 1020 */           if (rs.getLong("MAXJJAVAHEAP") != -1L) {
/* 1021 */             memoryProps.put("MAXJJAVAHEAP", Long.valueOf(rs.getLong("MAXJJAVAHEAP") / 1048576L));
/*      */           }
/* 1023 */           if (rs.getLong("JJAVAHEAP") != -1L) {
/* 1024 */             memoryProps.put("JJAVAHEAP", Long.valueOf(rs.getLong("JJAVAHEAP") / 1048576L));
/*      */           }
/* 1026 */           if (rs.getLong("COMMITJJAVAHEAP") != -1L) {
/* 1027 */             memoryProps.put("COMMITJJAVAHEAP", Long.valueOf(rs.getLong("COMMITJJAVAHEAP")));
/*      */           }
/* 1029 */           if (rs.getLong("INITJJAVAHEAP") != -1L) {
/* 1030 */             memoryProps.put("INITJJAVAHEAP", Long.valueOf(rs.getLong("INITJJAVAHEAP")));
/*      */           }
/*      */           
/* 1033 */           memoryProps.put("PERJJAVAHEAP", Long.valueOf(rs.getLong("PERJJAVAHEAP")));
/*      */           
/* 1035 */           if (rs.getLong("MAXNURSERY") != -1L) {
/* 1036 */             memoryProps.put("MAXNURSERY", Long.valueOf(rs.getLong("MAXNURSERY") / 1048576L));
/*      */           }
/*      */           
/* 1039 */           if (rs.getLong("NURSERY") != -1L) {
/* 1040 */             memoryProps.put("NURSERY", Long.valueOf(rs.getLong("NURSERY") / 1048576L));
/*      */           }
/*      */           
/* 1043 */           if (rs.getLong("COMMITNURSERY") != -1L) {
/* 1044 */             memoryProps.put("COMMITNURSERY", Long.valueOf(rs.getLong("COMMITNURSERY")));
/*      */           }
/* 1046 */           if (rs.getLong("INITNURSERY") != -1L) {
/* 1047 */             memoryProps.put("INITNURSERY", Long.valueOf(rs.getLong("INITNURSERY")));
/*      */           }
/*      */           
/* 1050 */           memoryProps.put("PERNURSERY", Long.valueOf(rs.getLong("PERNURSERY")));
/*      */           
/* 1052 */           if (rs.getLong("MAXOLDSPACE") != -1L) {
/* 1053 */             memoryProps.put("MAXOLDSPACE", Long.valueOf(rs.getLong("MAXOLDSPACE") / 1048576L));
/*      */           }
/* 1055 */           if (rs.getLong("OLDSPACE") != -1L) {
/* 1056 */             memoryProps.put("OLDSPACE", Long.valueOf(rs.getLong("OLDSPACE") / 1048576L));
/*      */           }
/* 1058 */           if (rs.getLong("COMMITOLDSPACE") != -1L) {
/* 1059 */             memoryProps.put("COMMITOLDSPACE", Long.valueOf(rs.getLong("COMMITOLDSPACE")));
/*      */           }
/* 1061 */           if (rs.getLong("INITOLDSPACE") != -1L) {
/* 1062 */             memoryProps.put("INITOLDSPACE", Long.valueOf(rs.getLong("INITOLDSPACE")));
/*      */           }
/* 1064 */           memoryProps.put("PEROLDSPACE", Long.valueOf(rs.getLong("PEROLDSPACE")));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1070 */         query = "select maxnonheap,usednonheap,usednonheapper,COMMITNONHEAP,INITNONHEAP,MAXCLASSMEM,CLASSMEM,COMMITCLASSMEM,INITCLASSMEM,PERCLASSMEM,MAXCLASSBLOCK,CLASSBLOCK,COMMITCLASSBLOCK,INITCLASSBLOCK,PERCLASSBLOCK from AM_JDK15_NONHEAPMEMORYINFO,AM_JDK15_NONHEAPMEMORYINFO_EXT where AM_JDK15_NONHEAPMEMORYINFO.resourceid=" + resid + " and AM_JDK15_NONHEAPMEMORYINFO.RESOURCEID=AM_JDK15_NONHEAPMEMORYINFO_EXT.RESOURCEID and AM_JDK15_NONHEAPMEMORYINFO.COLLECTIONTIME=AM_JDK15_NONHEAPMEMORYINFO_EXT.COLLECTIONTIME and AM_JDK15_NONHEAPMEMORYINFO.collectiontime=" + coltime;
/* 1071 */         rs = AMConnectionPool.executeQueryStmt(query);
/*      */         
/* 1073 */         if (rs.next())
/*      */         {
/* 1075 */           maxnonheap = rs.getLong("maxnonheap");
/* 1076 */           nonheap = rs.getLong("usednonheap");
/* 1077 */           nonheapper = rs.getLong("usednonheapper");
/*      */           
/*      */ 
/* 1080 */           if (rs.getLong("COMMITNONHEAP") == -1L) {
/* 1081 */             memoryProps.put("commitnonheap", "-");
/*      */           } else {
/* 1083 */             memoryProps.put("commitnonheap", Long.valueOf(rs.getLong("COMMITNONHEAP")));
/*      */           }
/* 1085 */           if (rs.getLong("INITNONHEAP") == -1L) {
/* 1086 */             memoryProps.put("initialnonheap", "-");
/*      */           } else {
/* 1088 */             memoryProps.put("initialnonheap", Long.valueOf(rs.getLong("INITNONHEAP")));
/*      */           }
/*      */           
/* 1091 */           if (rs.getLong("MAXCLASSMEM") != -1L) {
/* 1092 */             memoryProps.put("MAXCLASSMEM", Long.valueOf(rs.getLong("MAXCLASSMEM") / 1048576L));
/*      */           }
/* 1094 */           if (rs.getLong("CLASSMEM") != -1L) {
/* 1095 */             memoryProps.put("CLASSMEM", Long.valueOf(rs.getLong("CLASSMEM") / 1048576L));
/*      */           }
/* 1097 */           if (rs.getLong("COMMITCLASSMEM") != -1L) {
/* 1098 */             memoryProps.put("COMMITCLASSMEM", Long.valueOf(rs.getLong("COMMITCLASSMEM")));
/*      */           }
/* 1100 */           if (rs.getLong("INITCLASSMEM") != -1L) {
/* 1101 */             memoryProps.put("INITCLASSMEM", Long.valueOf(rs.getLong("INITCLASSMEM")));
/*      */           }
/* 1103 */           memoryProps.put("PERCLASSMEM", Long.valueOf(rs.getLong("PERCLASSMEM")));
/*      */           
/* 1105 */           if (rs.getLong("MAXCLASSBLOCK") != -1L) {
/* 1106 */             memoryProps.put("MAXCLASSBLOCK", Long.valueOf(rs.getLong("MAXCLASSBLOCK") / 1048576L));
/*      */           }
/* 1108 */           if (rs.getLong("CLASSBLOCK") != -1L) {
/* 1109 */             memoryProps.put("CLASSBLOCK", Long.valueOf(rs.getLong("CLASSBLOCK") / 1048576L));
/*      */           }
/* 1111 */           if (rs.getLong("COMMITCLASSBLOCK") != -1L) {
/* 1112 */             memoryProps.put("COMMITCLASSBLOCK", Long.valueOf(rs.getLong("COMMITCLASSBLOCK")));
/*      */           }
/* 1114 */           if (rs.getLong("INITCLASSBLOCK") != -1L) {
/* 1115 */             memoryProps.put("INITCLASSBLOCK", Long.valueOf(rs.getLong("INITCLASSBLOCK")));
/*      */           }
/* 1117 */           memoryProps.put("PERCLASSBLOCK", Long.valueOf(rs.getLong("PERCLASSBLOCK")));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1126 */       if (vendor.contains("Sun"))
/*      */       {
/* 1128 */         query = "select maxheap,usedheap,usedheapper,maxeden,eden,edenper,maxsurvivor,survivor,survivorper,maxtengen,tengen,tengenper,COMMITHEAP,INITHEAP,COMMITEDEN,INITEDEN,COMMITSURVIVOR,INITSURVIVOR,COMMITTENGEN,INITTENGEN from AM_JDK15_HEAPMEMORYINFO where resourceid=" + resid + " and collectiontime=" + coltime;
/* 1129 */         rs = AMConnectionPool.executeQueryStmt(query);
/*      */         
/*      */ 
/* 1132 */         if (rs.next()) {
/* 1133 */           maxheap = rs.getLong("maxheap");
/* 1134 */           heap = rs.getLong("usedheap");
/* 1135 */           heapper = rs.getLong("usedheapper");
/* 1136 */           maxeden = rs.getLong("maxeden");
/* 1137 */           eden = rs.getLong("eden");
/* 1138 */           edenper = rs.getLong("edenper");
/* 1139 */           maxsurvivor = rs.getLong("maxsurvivor");
/* 1140 */           survivor = rs.getLong("survivor");
/* 1141 */           survivorper = rs.getLong("survivorper");
/* 1142 */           maxtengen = rs.getLong("maxtengen");
/* 1143 */           tengen = rs.getLong("tengen");
/* 1144 */           tengenper = rs.getLong("tengenper");
/*      */           
/* 1146 */           memoryProps.put("commitheap", Long.valueOf(rs.getLong("COMMITHEAP")));
/* 1147 */           memoryProps.put("initialheap", Long.valueOf(rs.getLong("INITHEAP")));
/* 1148 */           memoryProps.put("commitden", Long.valueOf(rs.getLong("COMMITEDEN")));
/* 1149 */           memoryProps.put("initden", Long.valueOf(rs.getLong("INITEDEN")));
/* 1150 */           memoryProps.put("commitsurvivor", Long.valueOf(rs.getLong("COMMITSURVIVOR")));
/* 1151 */           memoryProps.put("initsurvivor", Long.valueOf(rs.getLong("INITSURVIVOR")));
/* 1152 */           memoryProps.put("committengen", Long.valueOf(rs.getLong("COMMITTENGEN")));
/* 1153 */           memoryProps.put("inittengen", Long.valueOf(rs.getLong("INITTENGEN")));
/*      */         }
/* 1155 */         AMConnectionPool.closeStatement(rs);
/*      */         
/* 1157 */         query = "select maxnonheap,usednonheap,usednonheapper,maxpermgen,permgen,permgenper,maxpermgenro,permgenro,permgenroper,maxpermgenrw,permgenrw,permgenrwper,maxcodecache,codecache,codecacheper,COMMITNONHEAP,INITNONHEAP,COMMITPERMGEN,INITPERMGEN,COMMITPERMGENRO,INITPERMGENRO,COMMITPERMGENRW,INITPERMGENRW,COMMITCODECACHE,INITCODECACHE,MAXMSPACE,INITMSPACE,COMMITMSPACE,MSPACE,MSPACEPER,MAXCCSPACE,INITCCSPACE,COMMITCCSPACE,CCSPACE,CCSPACEPER from AM_JDK15_NONHEAPMEMORYINFO where resourceid=" + resid + " and collectiontime=" + coltime;
/* 1158 */         rs = AMConnectionPool.executeQueryStmt(query);
/*      */         
/* 1160 */         if (rs.next()) {
/* 1161 */           maxnonheap = rs.getLong("maxnonheap");
/* 1162 */           nonheap = rs.getLong("usednonheap");
/* 1163 */           nonheapper = rs.getLong("usednonheapper");
/* 1164 */           maxpermgen = rs.getLong("maxpermgen");
/* 1165 */           permgen = rs.getLong("permgen");
/* 1166 */           permgenper = rs.getLong("permgenper");
/* 1167 */           maxpermgenro = rs.getLong("maxpermgenro");
/* 1168 */           permgenro = rs.getLong("permgenro");
/* 1169 */           permgenroper = rs.getLong("permgenroper");
/* 1170 */           maxpermgenrw = rs.getLong("maxpermgenrw");
/* 1171 */           permgenrw = rs.getLong("permgenrw");
/* 1172 */           permgenrwper = rs.getLong("permgenrwper");
/* 1173 */           maxcodecache = rs.getLong("maxcodecache");
/* 1174 */           codecache = rs.getLong("codecache");
/* 1175 */           codecacheper = rs.getLong("codecacheper");
/*      */           
/* 1177 */           if (rs.getLong("COMMITNONHEAP") == -1L) {
/* 1178 */             memoryProps.put("commitnonheap", "-");
/*      */           } else {
/* 1180 */             memoryProps.put("commitnonheap", Long.valueOf(rs.getLong("COMMITNONHEAP")));
/*      */           }
/* 1182 */           if (rs.getLong("INITNONHEAP") == -1L) {
/* 1183 */             memoryProps.put("initialnonheap", "-");
/*      */           } else {
/* 1185 */             memoryProps.put("initialnonheap", Long.valueOf(rs.getLong("INITNONHEAP")));
/*      */           }
/* 1187 */           if (rs.getLong("COMMITPERMGEN") == -1L) {
/* 1188 */             memoryProps.put("commitpermgen", "-");
/*      */           } else {
/* 1190 */             memoryProps.put("commitpermgen", Long.valueOf(rs.getLong("COMMITPERMGEN")));
/*      */           }
/* 1192 */           if (rs.getLong("COMMITPERMGEN") == -1L) {
/* 1193 */             memoryProps.put("initpermgen", "-");
/*      */           } else {
/* 1195 */             memoryProps.put("initpermgen", Long.valueOf(rs.getLong("INITPERMGEN")));
/*      */           }
/* 1197 */           if (rs.getLong("COMMITPERMGENRO") == -1L) {
/* 1198 */             memoryProps.put("commitpermgrnro", "-");
/*      */           } else {
/* 1200 */             memoryProps.put("commitpermgrnro", Long.valueOf(rs.getLong("COMMITPERMGENRO")));
/*      */           }
/* 1202 */           if (rs.getLong("INITPERMGENRO") == -1L) {
/* 1203 */             memoryProps.put("initpermgrnro", "-");
/*      */           } else {
/* 1205 */             memoryProps.put("initpermgrnro", Long.valueOf(rs.getLong("INITPERMGENRO")));
/*      */           }
/* 1207 */           if (rs.getLong("COMMITPERMGENRW") == -1L) {
/* 1208 */             memoryProps.put("commitpermgrnrw", "-");
/*      */           } else {
/* 1210 */             memoryProps.put("commitpermgrnrw", Long.valueOf(rs.getLong("INITPERMGENRO")));
/*      */           }
/* 1212 */           if (rs.getLong("INITPERMGENRW") == -1L) {
/* 1213 */             memoryProps.put("initpermgrnrw", "-");
/*      */           } else {
/* 1215 */             memoryProps.put("initpermgrnrw", Long.valueOf(rs.getLong("INITPERMGENRW")));
/*      */           }
/* 1217 */           if (rs.getLong("COMMITCODECACHE") == -1L) {
/* 1218 */             memoryProps.put("commitcodecache", "-");
/*      */           } else {
/* 1220 */             memoryProps.put("commitcodecache", Long.valueOf(rs.getLong("COMMITCODECACHE")));
/*      */           }
/* 1222 */           if (rs.getLong("INITCODECACHE") == -1L) {
/* 1223 */             memoryProps.put("initcodecache", "-");
/*      */           } else {
/* 1225 */             memoryProps.put("initcodecache", Long.valueOf(rs.getLong("INITCODECACHE")));
/*      */           }
/*      */           
/*      */ 
/* 1229 */           if (rs.getLong("MAXMSPACE") == -1L) {
/* 1230 */             memoryProps.put("MAXMSPACE", "-1");
/*      */           } else {
/* 1232 */             memoryProps.put("MAXMSPACE", Long.valueOf(rs.getLong("MAXMSPACE") / 1048576L));
/*      */           }
/* 1234 */           if (rs.getLong("INITMSPACE") == -1L) {
/* 1235 */             memoryProps.put("INITMSPACE", "-1");
/*      */           } else {
/* 1237 */             memoryProps.put("INITMSPACE", Long.valueOf(rs.getLong("INITMSPACE")));
/*      */           }
/* 1239 */           if (rs.getLong("COMMITMSPACE") == -1L) {
/* 1240 */             memoryProps.put("COMMITMSPACE", "-1");
/*      */           } else {
/* 1242 */             memoryProps.put("COMMITMSPACE", Long.valueOf(rs.getLong("COMMITMSPACE")));
/*      */           }
/* 1244 */           if (rs.getLong("MSPACE") == -1L) {
/* 1245 */             memoryProps.put("MSPACE", "-1");
/*      */           } else {
/* 1247 */             memoryProps.put("MSPACE", Long.valueOf(rs.getLong("MSPACE") / 1048576L));
/*      */           }
/* 1249 */           if (rs.getLong("MSPACEPER") == -1L) {
/* 1250 */             memoryProps.put("MSPACEPER", "-1");
/*      */           } else {
/* 1252 */             memoryProps.put("MSPACEPER", Long.valueOf(rs.getLong("MSPACEPER")));
/*      */           }
/*      */           
/* 1255 */           if (rs.getLong("MAXCCSPACE") == -1L) {
/* 1256 */             memoryProps.put("MAXCCSPACE", "-1");
/*      */           } else {
/* 1258 */             memoryProps.put("MAXCCSPACE", Long.valueOf(rs.getLong("MAXCCSPACE") / 1048576L));
/*      */           }
/* 1260 */           if (rs.getLong("INITCCSPACE") == -1L) {
/* 1261 */             memoryProps.put("INITCCSPACE", "-1");
/*      */           } else {
/* 1263 */             memoryProps.put("INITCCSPACE", Long.valueOf(rs.getLong("INITCCSPACE")));
/*      */           }
/* 1265 */           if (rs.getLong("COMMITCCSPACE") == -1L) {
/* 1266 */             memoryProps.put("COMMITCCSPACE", "-1");
/*      */           } else {
/* 1268 */             memoryProps.put("COMMITCCSPACE", Long.valueOf(rs.getLong("COMMITCCSPACE")));
/*      */           }
/* 1270 */           if (rs.getLong("CCSPACE") == -1L) {
/* 1271 */             memoryProps.put("CCSPACE", "-1");
/*      */           } else {
/* 1273 */             memoryProps.put("CCSPACE", Long.valueOf(rs.getLong("CCSPACE") / 1048576L));
/*      */           }
/* 1275 */           if (rs.getLong("CCSPACEPER") == -1L) {
/* 1276 */             memoryProps.put("CCSPACEPER", "-1");
/*      */           } else {
/* 1278 */             memoryProps.put("CCSPACEPER", Long.valueOf(rs.getLong("CCSPACEPER")));
/*      */           }
/*      */         }
/*      */         
/* 1282 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1289 */       memoryProps.put("totalMemory", Long.valueOf(totalmemory / 1048576L));
/* 1290 */       memoryProps.put("freeMemory", Long.valueOf(freememory / 1048576L));
/* 1291 */       memoryProps.put("usedMemory", Long.valueOf((totalmemory - freememory) / 1048576L));
/* 1292 */       memoryProps.put("processmemory", Long.valueOf(processmemory / 1048576L));
/* 1293 */       memoryProps.put("processmemoryper", Long.valueOf(processmemoryper));
/* 1294 */       memoryProps.put("cpuper", cpuperstr);
/*      */       
/* 1296 */       memoryProps.put("heap", Long.valueOf(heap / 1048576L));
/* 1297 */       memoryProps.put("nonheap", Long.valueOf(nonheap / 1048576L));
/*      */       
/* 1299 */       if (heapper == -1L) {
/* 1300 */         memoryProps.put("heapper", new Long(-1L));
/*      */       } else {
/* 1302 */         memoryProps.put("heapper", Long.valueOf(heapper));
/*      */       }
/*      */       
/* 1305 */       if (nonheapper == -1L) {
/* 1306 */         memoryProps.put("nonheapper", "-1");
/*      */       } else {
/* 1308 */         memoryProps.put("nonheapper", Long.valueOf(nonheapper));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1316 */       memoryProps.put("maxheap", Long.valueOf(maxheap / 1048576L));
/* 1317 */       memoryProps.put("eden", Long.valueOf(eden / 1048576L));
/* 1318 */       if (edenper == -1L) {
/* 1319 */         memoryProps.put("edenper", "-1");
/*      */       }
/*      */       else {
/* 1322 */         memoryProps.put("edenper", Long.valueOf(edenper));
/*      */       }
/* 1324 */       memoryProps.put("maxeden", Long.valueOf(maxeden / 1048576L));
/* 1325 */       memoryProps.put("survivor", Long.valueOf(survivor / 1048576L));
/* 1326 */       if (survivorper == -1L) {
/* 1327 */         memoryProps.put("survivorper", "-1");
/*      */       }
/*      */       else {
/* 1330 */         memoryProps.put("survivorper", Long.valueOf(survivorper));
/*      */       }
/* 1332 */       memoryProps.put("maxsurvivor", Long.valueOf(maxsurvivor / 1048576L));
/* 1333 */       memoryProps.put("tengen", Long.valueOf(tengen / 1048576L));
/* 1334 */       if (tengenper == -1L) {
/* 1335 */         memoryProps.put("tengenper", "-1");
/*      */       }
/*      */       else {
/* 1338 */         memoryProps.put("tengenper", Long.valueOf(tengenper));
/*      */       }
/* 1340 */       memoryProps.put("maxtengen", Long.valueOf(maxtengen / 1048576L));
/*      */       
/*      */ 
/*      */ 
/* 1344 */       if (maxnonheap == -1L) {
/* 1345 */         memoryProps.put("maxnonheap", "-1");
/*      */       }
/*      */       else {
/* 1348 */         memoryProps.put("maxnonheap", Long.valueOf(maxnonheap / 1048576L));
/*      */       }
/*      */       
/* 1351 */       memoryProps.put("permgen", Long.valueOf(permgen / 1048576L));
/* 1352 */       if (permgenper == -1L) {
/* 1353 */         memoryProps.put("permgenper", "-1");
/*      */       }
/*      */       else {
/* 1356 */         memoryProps.put("permgenper", Long.valueOf(permgenper));
/*      */       }
/* 1358 */       memoryProps.put("maxpermgen", Long.valueOf(maxpermgen / 1048576L));
/* 1359 */       memoryProps.put("permgenro", Long.valueOf(permgenro / 1048576L));
/* 1360 */       if (permgenroper == -1L) {
/* 1361 */         memoryProps.put("permgenroper", "-1");
/*      */       }
/*      */       else {
/* 1364 */         memoryProps.put("permgenroper", Long.valueOf(permgenroper));
/*      */       }
/* 1366 */       memoryProps.put("maxpermgenro", Long.valueOf(maxpermgenro / 1048576L));
/* 1367 */       memoryProps.put("permgenrw", Long.valueOf(permgenrw / 1048576L));
/* 1368 */       if (permgenrwper == -1L) {
/* 1369 */         memoryProps.put("permgenrwper", "-1");
/*      */       }
/*      */       else {
/* 1372 */         memoryProps.put("permgenrwper", Long.valueOf(permgenrwper));
/*      */       }
/* 1374 */       memoryProps.put("maxpermgenrw", Long.valueOf(maxpermgenrw / 1048576L));
/* 1375 */       memoryProps.put("codecache", Long.valueOf(codecache / 1048576L));
/* 1376 */       if (codecacheper == -1L) {
/* 1377 */         memoryProps.put("codecacheper", "-1");
/*      */       }
/*      */       else {
/* 1380 */         memoryProps.put("codecacheper", Long.valueOf(codecacheper));
/*      */       }
/* 1382 */       memoryProps.put("maxcodecache", Long.valueOf(maxcodecache / 1048576L));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1388 */       request.setAttribute("memoryProps", memoryProps);
/*      */       
/*      */ 
/* 1391 */       query = "select live,daemon,runnable,blocked,waiting,timed_waiting,deadlocked from AM_JDK15_THREADINFO where resourceid=" + resid + " and collectiontime=" + coltime;
/* 1392 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1393 */       String live = "-";String daemon = "-";String runnable = "-";
/* 1394 */       String blocked = "-";String waiting = "-";String deadlocked = "-";
/* 1395 */       String timed_waiting = "-";
/* 1396 */       if (rs.next()) {
/* 1397 */         live = rs.getString("live");
/* 1398 */         daemon = rs.getString("daemon");
/* 1399 */         runnable = rs.getString("runnable");
/* 1400 */         blocked = rs.getString("blocked");
/* 1401 */         waiting = rs.getString("waiting");
/* 1402 */         timed_waiting = rs.getString("timed_waiting");
/* 1403 */         deadlocked = rs.getString("deadlocked");
/*      */       }
/* 1405 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1407 */       HashMap threadProps = new HashMap();
/* 1408 */       threadProps.put("live", live);
/* 1409 */       threadProps.put("daemon", daemon);
/* 1410 */       threadProps.put("runnable", runnable);
/* 1411 */       threadProps.put("blocked", blocked);
/* 1412 */       threadProps.put("waiting", waiting);
/* 1413 */       threadProps.put("timed_waiting", timed_waiting);
/* 1414 */       threadProps.put("deadlocked", deadlocked);
/*      */       
/* 1416 */       request.setAttribute("threadProps", threadProps);
/*      */       
/* 1418 */       String poll = (String)request.getAttribute("reloadperiod");
/* 1419 */       HashMap map = null;
/* 1420 */       if (poll == null)
/* 1421 */         poll = "300";
/* 1422 */       map = ClientDBUtil.getSystemHealthPollInfoForService(resid, Long.parseLong(poll));
/*      */       
/*      */ 
/*      */ 
/* 1426 */       query = "select pollinterval from CollectData cd,AM_ManagedObject mo where mo.resourceid=" + resid + " and mo.resourcename=cd.resourcename";
/* 1427 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1428 */       int pollint = 0;
/* 1429 */       if (rs.next()) {
/* 1430 */         pollint = rs.getInt("pollinterval");
/* 1431 */         int poll1 = pollint / 60;
/* 1432 */         ((AMActionForm)form).setPollInterval(poll1);
/*      */       }
/* 1434 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*      */ 
/* 1437 */       query = "select max(collectiontime) as coltime from AM_ManagedObjectData where resid=" + resid;
/* 1438 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1439 */       if (rs.next()) {
/* 1440 */         long l = rs.getLong("coltime");
/* 1441 */         long np = l + pollint * 1000L;
/*      */         
/* 1443 */         map.put("LASTDC", Long.valueOf(l));
/* 1444 */         map.put("NEXTDC", Long.valueOf(np));
/*      */       }
/* 1446 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*      */ 
/* 1449 */       if (map != null) {
/* 1450 */         request.setAttribute("systeminfo", map);
/*      */       }
/*      */       
/*      */ 
/* 1454 */       query = "select responsetime,classesloaded,classesunloaded,GCTIMEPER,COMPILETIMEPER,maxfiledescriptor,openfiledescriptor from AM_JDK15_VMINFO where resourceid=" + resid + " and collectiontime=" + coltime;
/* 1455 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1456 */       long loaded = -1L;long unloaded = -1L;double gctime = -1.0D;
/* 1457 */       double comptime = -1.0D;long maxfiled = -1L;long openfiled = -1L;
/* 1458 */       long responsetime = -1L;long gctimeper = -1L;long compileper = -1L;
/* 1459 */       if (rs.next())
/*      */       {
/* 1461 */         responsetime = rs.getLong("responsetime");
/* 1462 */         loaded = rs.getLong("classesloaded");
/* 1463 */         unloaded = rs.getLong("classesunloaded");
/* 1464 */         maxfiled = rs.getLong("maxfiledescriptor");
/* 1465 */         openfiled = rs.getLong("openfiledescriptor");
/* 1466 */         gctimeper = rs.getLong("GCTIMEPER");
/* 1467 */         compileper = rs.getLong("COMPILETIMEPER");
/*      */       }
/* 1469 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1471 */       HashMap vmProps = new HashMap();
/* 1472 */       if (responsetime >= 0L) {
/* 1473 */         vmProps.put("responsetime", Long.valueOf(responsetime));
/*      */       }
/*      */       else {
/* 1476 */         vmProps.put("responsetime", "-");
/*      */       }
/* 1478 */       if (loaded >= 0L) {
/* 1479 */         vmProps.put("classesloaded", Long.valueOf(loaded));
/*      */       }
/*      */       else {
/* 1482 */         vmProps.put("classesloaded", "-");
/*      */       }
/* 1484 */       if (unloaded >= 0L) {
/* 1485 */         vmProps.put("classesunloaded", Long.valueOf(unloaded));
/*      */       }
/*      */       else {
/* 1488 */         vmProps.put("classesunloaded", "-");
/*      */       }
/* 1490 */       vmProps.put("maxfiledescriptor", Long.valueOf(maxfiled));
/* 1491 */       vmProps.put("openfiledescriptor", Long.valueOf(openfiled));
/* 1492 */       if ((gctimeper == -1L) || (gctimeper < 0L)) {
/* 1493 */         vmProps.put("gctimeper", "-");
/*      */       } else {
/* 1495 */         vmProps.put("gctimeper", Long.valueOf(gctimeper));
/*      */       }
/* 1497 */       if ((compileper == -1L) || (compileper < 0L)) {
/* 1498 */         vmProps.put("compiletimeper", "-");
/*      */       }
/*      */       else {
/* 1501 */         vmProps.put("compiletimeper", Long.valueOf(compileper));
/*      */       }
/*      */       
/* 1504 */       request.setAttribute("vmProps", vmProps);
/*      */       ResultSet rstype;
/*      */       try
/*      */       {
/* 1508 */         String query_msg = "select ERRORMSG from AM_RESOURCECONFIG where RESOURCEID=" + resid;
/* 1509 */         AMConnectionPool.getInstance();rstype = AMConnectionPool.executeQueryStmt(query_msg);
/* 1510 */         if (rstype.next())
/*      */         {
/* 1512 */           String message = rstype.getString("ERRORMSG");
/* 1513 */           if ((message != null) && (!message.equalsIgnoreCase("NULL")) && (!message.equals("")) && (message.indexOf("successful") == -1))
/*      */           {
/*      */ 
/* 1516 */             ActionMessages messages = new ActionMessages();
/* 1517 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(message));
/* 1518 */             saveMessages(request, messages);
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1528 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1531 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 1534 */     return mapping.findForward("overview");
/*      */   }
/*      */   
/*      */   public ActionForward getThreadInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1539 */     AMConnectionPool pool = new AMConnectionPool();
/* 1540 */     long colTime = 0L;
/* 1541 */     String resid = request.getParameter("resourceid");
/* 1542 */     String ipaddress = request.getParameter("IP");
/* 1543 */     String hostname = request.getParameter("hostname");
/* 1544 */     String pid = request.getParameter("PID");
/* 1545 */     String threshold = request.getParameter("threshold");
/*      */     
/* 1547 */     String query = null;
/* 1548 */     ResultSet rs = null;
/* 1549 */     String dispname = "";
/* 1550 */     String type = null;
/* 1551 */     String resname = null;
/* 1552 */     HashMap resNames = new HashMap();
/* 1553 */     boolean appman = true;
/* 1554 */     int nCpus = 1;
/* 1555 */     String apiKey = request.getParameter("apikey");
/* 1556 */     int referenceID = 0;
/* 1557 */     response.setContentType("text/html; charset=UTF-8");
/* 1558 */     PrintWriter out = response.getWriter();
/* 1559 */     StringBuffer temp = new StringBuffer();
/* 1560 */     int MAX_THREAD = 500;
/* 1561 */     int delay = 30000;
/* 1562 */     float limit = 30.0F;
/*      */     
/*      */ 
/* 1565 */     boolean first = true;
/* 1566 */     if (request.getParameter("first") != null)
/*      */     {
/* 1568 */       first = false;
/*      */     }
/* 1570 */     JMXConnector jc = null;
/* 1571 */     Client client = null;
/*      */     try
/*      */     {
/* 1574 */       if ((request.getParameter("delay") != null) && (!request.getParameter("delay").equals("")) && (!request.getParameter("delay").equals("null")))
/*      */       {
/* 1576 */         delay = Integer.parseInt(request.getParameter("delay")) * 1000;
/*      */       }
/*      */       
/*      */ 
/* 1580 */       if ((request.getParameter("threshold") != null) && (!request.getParameter("threshold").equals("")) && (!request.getParameter("threshold").equals("null")))
/*      */       {
/* 1582 */         limit = Float.valueOf(request.getParameter("threshold")).floatValue();
/*      */       }
/*      */       
/*      */ 
/* 1586 */       if ((request.getParameter("UID") != null) && (!request.getParameter("UID").equals("")) && (!request.getParameter("UID").equals("null")))
/*      */       {
/* 1588 */         referenceID = Integer.parseInt(request.getParameter("UID"));
/*      */       }
/*      */       
/* 1591 */       if ((request.getRemoteUser() != null) || (APIServlet.validateAPIKey(request)))
/*      */       {
/* 1593 */         if ((resid != null) && (!resid.equals("null")))
/*      */         {
/* 1595 */           query = "select DISPLAYNAME,TYPE,RESOURCENAME from AM_ManagedObject where TYPE IN " + Constants.jmxTypes + " and RESOURCEID=" + resid;
/* 1596 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1597 */           if (rs.next()) {
/* 1598 */             dispname = rs.getString("DISPLAYNAME");
/* 1599 */             type = rs.getString("TYPE");
/* 1600 */             resNames.put(resid, rs.getString("RESOURCENAME"));
/*      */           }
/*      */           else
/*      */           {
/* 1604 */             query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME from CollectData, AM_ManagedObject  where TARGETADDRESS=(select CollectData.TARGETADDRESS from AM_ManagedObject,CollectData where AM_ManagedObject.resourcename=CollectData.resourcename and AM_ManagedObject.resourceid=" + resid + ") and COMPONENTNAME='JDK1.5' and AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME";
/* 1605 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1606 */             while (rs.next()) {
/* 1607 */               resNames.put(rs.getString("RESOURCEID"), rs.getString("RESOURCENAME"));
/*      */             }
/* 1609 */             rs.close();
/* 1610 */             appman = false;
/*      */           }
/* 1612 */           AMConnectionPool.closeStatement(rs);
/* 1613 */           request.setAttribute("displayname", dispname);
/*      */         }
/*      */         
/* 1616 */         if ((ipaddress != null) && (!ipaddress.equals("null")))
/*      */         {
/* 1618 */           query = "select RESOURCEID,AM_ManagedObject.RESOURCENAME from AM_ManagedObject, CollectData where TARGETADDRESS='" + ipaddress + "' and COMPONENTNAME='JDK1.5' and AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME";
/* 1619 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1620 */           while (rs.next()) {
/* 1621 */             resNames.put(rs.getString("RESOURCEID"), rs.getString("RESOURCENAME"));
/*      */           }
/* 1623 */           AMConnectionPool.closeStatement(rs);
/* 1624 */           appman = false;
/*      */         }
/*      */         
/* 1627 */         if ((hostname != null) && (!hostname.equals("null")))
/*      */         {
/* 1629 */           query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME from CollectData, AM_ManagedObject  where TARGETADDRESS=(select CollectData.TARGETADDRESS from CollectData where CollectData.resourcename ='" + hostname + "') and COMPONENTNAME='JDK1.5' and AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME";
/* 1630 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1631 */           while (rs.next()) {
/* 1632 */             resNames.put(rs.getString("RESOURCEID"), rs.getString("RESOURCENAME"));
/*      */           }
/* 1634 */           AMConnectionPool.closeStatement(rs);
/* 1635 */           appman = false;
/*      */         }
/*      */         
/* 1638 */         if ((pid != null) && (!pid.equals("null")))
/*      */         {
/* 1640 */           query = "select RESOURCENAME,AM_ManagedObject.RESOURCEID from AM_CONFIGURATION_INFO,AM_ManagedObject where AM_CONFIGURATION_INFO.ATTRIBUTEID=5103 and AM_CONFIGURATION_INFO.LATEST=1  and AM_CONFIGURATION_INFO.CONFVALUE IN(" + pid + ") and AM_CONFIGURATION_INFO.RESOURCEID=AM_ManagedObject.RESOURCEID";
/* 1641 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1642 */           while (rs.next()) {
/* 1643 */             resNames.put(rs.getString("RESOURCEID"), rs.getString("RESOURCENAME"));
/*      */           }
/* 1645 */           AMConnectionPool.closeStatement(rs);
/* 1646 */           appman = false;
/*      */         }
/*      */         
/* 1649 */         query = "select CONFVALUE from AM_CONFIGURATION_INFO where ATTRIBUTEID=5098 and LATEST=1 and RESOURCEID=" + resid;
/* 1650 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1651 */         if (rs.next()) {
/* 1652 */           nCpus = Integer.parseInt(rs.getString("CONFVALUE"));
/*      */         }
/* 1654 */         AMConnectionPool.closeStatement(rs);
/*      */         
/*      */ 
/* 1657 */         if (resNames.size() == 0)
/*      */         {
/* 1659 */           temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 1660 */           temp.append("<tr><td>");
/* 1661 */           temp.append("<span class='bodytext'> " + FormatUtil.getString("am.javaruntime.nomonitor") + " </span>");
/* 1662 */           temp.append("</td></tr>");
/* 1663 */           temp.append("</table>");
/* 1664 */           out.write(temp.toString());
/* 1665 */           out.flush();
/* 1666 */           return null;
/*      */         }
/*      */         
/* 1669 */         if (appman)
/*      */         {
/* 1671 */           HashMap threadinfo1 = new HashMap();
/* 1672 */           HashMap threadinfo2 = new HashMap();
/* 1673 */           ArrayList threadinf = new ArrayList();
/* 1674 */           resname = (String)resNames.get(resid);
/* 1675 */           String host = null;
/* 1676 */           int port = -1;
/*      */           try {
/* 1678 */             ResourceConfig rc = null;
/* 1679 */             Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(resid));
/* 1680 */             if (resname != null)
/*      */             {
/* 1682 */               if ("JBOSS-server".equalsIgnoreCase(type)) {
/* 1683 */                 client = CAMJMXUtil.getClient(agentInfo);
/*      */               } else {
/* 1685 */                 ApplnDataCollectionAPI dcapi = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/* 1686 */                 rc = (ResourceConfig)dcapi.getCollectData(resname, "JDK1.5");
/* 1687 */                 if (rc != null)
/*      */                 {
/* 1689 */                   host = rc.getHostName();
/* 1690 */                   port = rc.getApplnDiscPort();
/*      */                 }
/*      */                 try {
/* 1693 */                   jc = JDK15Util.getJMXConnector(host, port, Integer.parseInt(resid));
/* 1694 */                 } catch (Exception e) { e.printStackTrace();
/*      */                 }
/*      */               } }
/* 1697 */             if ((jc != null) || (client != null))
/*      */             {
/* 1699 */               colTime = System.currentTimeMillis();
/* 1700 */               boolean fflag = true;
/* 1701 */               if (first)
/*      */               {
/* 1703 */                 if ("JBOSS-server".equalsIgnoreCase(type)) {
/* 1704 */                   threadinfo1 = JDK15Util.getThreadInfo(client, resid);
/*      */                 } else {
/* 1706 */                   threadinfo1 = JDK15Util.getThreadInfo(jc, resid);
/*      */                 }
/* 1708 */                 JDK15Util.threadDumpInfo.put(resid + colTime, threadinfo1);
/* 1709 */                 ArrayList threadinfo = changeThreadDumpFormat(threadinfo1, resid);
/* 1710 */                 request.setAttribute("threadinfo", threadinfo);
/* 1711 */                 request.setAttribute("key", Long.valueOf(colTime));
/* 1712 */                 request.setAttribute("first", Boolean.valueOf(fflag));
/*      */                 
/* 1714 */                 return mapping.findForward("threadinfo");
/*      */               }
/*      */               
/*      */ 
/* 1718 */               String key = "0";
/* 1719 */               if (request.getParameter("key") != null)
/*      */               {
/* 1721 */                 key = request.getParameter("key");
/*      */               }
/*      */               
/* 1724 */               fflag = false;
/* 1725 */               Thread.sleep(5000L);
/*      */               try {
/* 1727 */                 if (jc != null) {
/* 1728 */                   jc.close();
/* 1729 */                   jc = null;
/*      */                 }
/* 1731 */                 else if (client != null) {
/* 1732 */                   client.disconnect();
/* 1733 */                   client = null;
/*      */                 }
/*      */               } catch (Exception ex) {}
/*      */               try {
/* 1737 */                 if ("JBOSS-server".equalsIgnoreCase(type)) {
/* 1738 */                   client = CAMJMXUtil.getClient(agentInfo);
/* 1739 */                   threadinfo2 = JDK15Util.getThreadInfo(client, resid);
/*      */                 } else {
/* 1741 */                   jc = JDK15Util.getJMXConnector(host, port, Integer.parseInt(resid));
/* 1742 */                   threadinfo2 = JDK15Util.getThreadInfo(jc, resid);
/*      */                 }
/* 1744 */               } catch (Exception e) { ((Exception)e).printStackTrace();
/*      */               }
/*      */               
/* 1747 */               threadinf = JDK15Util.getThreadDumpDiff((HashMap)JDK15Util.threadDumpInfo.get(resid + key), threadinfo2, resid, nCpus, limit);
/*      */               
/* 1749 */               JDK15Util.genThreadDumpForHTML(resid, colTime, threadinf, resname, Integer.toString(referenceID), limit, false);
/*      */               
/* 1751 */               request.setAttribute("first", Boolean.valueOf(fflag));
/* 1752 */               request.setAttribute("threadinfo", threadinf);
/*      */               
/* 1754 */               JDK15Util.threadDumpInfo.remove(resid + key);
/* 1755 */               return mapping.findForward("threadinfo");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 1760 */             temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 1761 */             temp.append("<tr><td>");
/* 1762 */             temp.append("<span class='bodytext'> " + FormatUtil.getString("am.javaruntime.threaddump.error") + " </span>");
/* 1763 */             temp.append("</td></tr>");
/* 1764 */             temp.append("</table>");
/*      */ 
/*      */           }
/*      */           catch (JBossConnectException e)
/*      */           {
/*      */ 
/* 1770 */             e.printStackTrace();
/* 1771 */             temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 1772 */             temp.append("<tr><td>");
/* 1773 */             temp.append("<span class='bodytext'> " + FormatUtil.getString("am.javaruntime.threaddump.error") + e.getMessage() + " </span>");
/* 1774 */             temp.append("</td></tr>");
/* 1775 */             temp.append("</table>");
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1779 */             e.printStackTrace();
/* 1780 */             temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 1781 */             temp.append("<tr><td>");
/* 1782 */             if ("JBOSS-server".equalsIgnoreCase(type)) {
/* 1783 */               temp.append("<span class='bodytext'> " + FormatUtil.getString("am.javaruntime.threaddump.error") + " " + FormatUtil.getString("am.javaruntime.threaddump.jbossversion4xerror") + " </span>");
/*      */             }
/*      */             else {
/* 1786 */               temp.append("<span class='bodytext'> " + FormatUtil.getString("am.javaruntime.threaddump.error") + e.getMessage() + " </span>");
/*      */             }
/* 1788 */             temp.append("</td></tr>");
/* 1789 */             temp.append("</table>");
/*      */           }
/*      */           
/* 1792 */           out.write(temp.toString());
/* 1793 */           out.flush();
/* 1794 */           return null;
/*      */         }
/* 1796 */         Set set = resNames.entrySet();
/* 1797 */         Iterator i = set.iterator();
/* 1798 */         Map.Entry me; while (i.hasNext()) {
/* 1799 */           me = (Map.Entry)i.next();
/* 1800 */           resid = (String)me.getKey();
/* 1801 */           resname = (String)me.getValue();
/*      */           
/*      */ 
/* 1804 */           if (MAX_THREAD > 0) {
/* 1805 */             triggerThreadDumpThread triggerThread = new triggerThreadDumpThread(resid, resname, colTime, referenceID, nCpus, delay, limit);
/* 1806 */             triggerThread.setName("triggerThreadDumpThread-Resourceid-" + resid + "-Time-" + System.currentTimeMillis());
/* 1807 */             triggerThread.start();
/*      */           }
/*      */           else
/*      */           {
/* 1811 */             temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 1812 */             temp.append("<tr><td>");
/* 1813 */             temp.append("<span class='bodytext'>" + FormatUtil.getString("am.javaruntime.threaddump.exceeds", new String[] { String.valueOf(MAX_THREAD) }) + "</span>");
/* 1814 */             temp.append("</td></tr>");
/* 1815 */             temp.append("</table>");
/*      */           }
/* 1817 */           MAX_THREAD--;
/*      */         }
/*      */         
/* 1820 */         if (!appman)
/*      */         {
/*      */ 
/* 1823 */           temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 1824 */           temp.append("<tr><td>");
/* 1825 */           temp.append("<span class='bodytext'>");
/* 1826 */           if (request.getParameter("synUrls") != null) {
/* 1827 */             temp.append("Message from Managed Server " + EnterpriseUtil.getManagedServerIndex() + " : ");
/*      */           }
/* 1829 */           temp.append(FormatUtil.getString("am.javaruntime.threaddump.success") + " </span>");
/* 1830 */           temp.append("</td></tr>");
/* 1831 */           temp.append("</table>");
/* 1832 */           out.print(temp);
/* 1833 */           out.flush();
/* 1834 */           return null;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1839 */         temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 1840 */         temp.append("<tr><td>");
/* 1841 */         temp.append("<span class='bodytext'> " + FormatUtil.getString("am.javaruntime.wrongapikey") + " </span>");
/* 1842 */         temp.append("</td></tr>");
/* 1843 */         temp.append("</table>");
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
/* 1856 */       AMConnectionPool.closeStatement(rs);
/*      */       try {
/* 1858 */         if (jc != null) {
/* 1859 */           jc.close();
/* 1860 */           jc = null;
/*      */         }
/* 1862 */         else if (client != null) {
/* 1863 */           client.disconnect();
/* 1864 */           client = null;
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/*      */       
/* 1869 */       out.write(temp.toString());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1848 */       e.printStackTrace();
/* 1849 */       temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 1850 */       temp.append("<tr><td>");
/* 1851 */       temp.append("<span class='bodytext'> " + FormatUtil.getString("am.javaruntime.threaddump.error") + "" + e.getMessage() + " </span>");
/* 1852 */       temp.append("</td></tr>");
/* 1853 */       temp.append("</table>");
/*      */     }
/*      */     finally {
/* 1856 */       AMConnectionPool.closeStatement(rs);
/*      */       try {
/* 1858 */         if (jc != null) {
/* 1859 */           jc.close();
/* 1860 */           jc = null;
/*      */         }
/* 1862 */         else if (client != null) {
/* 1863 */           client.disconnect();
/* 1864 */           client = null;
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */     
/* 1870 */     out.flush();
/* 1871 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   class triggerThreadDumpThread
/*      */     extends Thread
/*      */   {
/*      */     String resname;
/*      */     
/*      */     String resid;
/*      */     long colTime;
/*      */     int referenceID;
/* 1883 */     long threadinfotime = 0L;
/* 1884 */     long threadinfogenhtml = 0L;
/* 1885 */     AMConnectionPool pool = new AMConnectionPool();
/* 1886 */     int nCpus = 0;
/* 1887 */     int delay = 30000;
/* 1888 */     float limit = 30.0F;
/* 1889 */     ArrayList threadinf = new ArrayList();
/*      */     
/*      */     triggerThreadDumpThread(String resid, String resname, long colTime, int referenceID, int nCpus, int delay, float limit) {
/* 1892 */       this.resid = resid;
/* 1893 */       this.resname = resname;
/* 1894 */       this.colTime = colTime;
/* 1895 */       this.referenceID = referenceID;
/* 1896 */       this.nCpus = nCpus;
/* 1897 */       this.delay = delay;
/* 1898 */       this.limit = limit; }
/*      */     
/* 1900 */     HashMap threadinfo1 = new HashMap();
/* 1901 */     HashMap threadinfo2 = new HashMap();
/*      */     
/*      */     public void run()
/*      */     {
/* 1905 */       JMXConnector jc = null;
/* 1906 */       Client client = null;
/*      */       try
/*      */       {
/* 1909 */         AMLog.debug("JavaRuntime Monitor : Thread Dump Process Started for the Resource : " + this.resname);
/* 1910 */         long t1 = System.currentTimeMillis();
/* 1911 */         String host = null;
/* 1912 */         int port = -1;
/* 1913 */         Map agentInfo = CAMDBUtil.getAMMOInfo(Integer.parseInt(this.resid));
/* 1914 */         String type = DBUtil.getTypeName(Integer.parseInt(this.resid));
/* 1915 */         if (this.resname != null)
/*      */         {
/* 1917 */           if ("JBOSS-server".equalsIgnoreCase(type)) {
/* 1918 */             client = CAMJMXUtil.getClient(agentInfo);
/*      */           } else {
/* 1920 */             ApplnDataCollectionAPI dcapi = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/* 1921 */             ResourceConfig rc = (ResourceConfig)dcapi.getCollectData(this.resname, "JDK1.5");
/* 1922 */             if (rc != null)
/*      */             {
/* 1924 */               host = rc.getHostName();
/* 1925 */               port = rc.getApplnDiscPort();
/*      */             }
/*      */             try {
/* 1928 */               jc = JDK15Util.getJMXConnector(host, port, Integer.parseInt(this.resid));
/* 1929 */             } catch (Exception e) { e.printStackTrace();
/*      */             }
/*      */           } }
/* 1932 */         if ((jc != null) || (client != null))
/*      */         {
/* 1934 */           if (((HashMap)this.threadinfo1.get(this.resid) != null) && (((HashMap)this.threadinfo1.get(this.resid)).size() > 0))
/*      */           {
/* 1936 */             if ("JBOSS-server".equalsIgnoreCase(type)) {
/* 1937 */               this.threadinfo2 = JDK15Util.getThreadInfo(client, this.resid);
/*      */             } else {
/* 1939 */               this.threadinfo2 = JDK15Util.getThreadInfo(jc, this.resid);
/*      */             }
/* 1941 */             this.colTime = System.currentTimeMillis();
/* 1942 */             this.threadinfotime = (this.colTime - t1);
/* 1943 */             AMLog.debug("JavaRuntime Monitor : Generate Thread Dump After Process took : " + this.threadinfotime + " ms & Thread Name " + Thread.currentThread().getName());
/*      */             
/* 1945 */             this.threadinf = JDK15Util.getThreadDumpDiff(this.threadinfo1, this.threadinfo2, this.resid, this.nCpus, this.limit);
/*      */             
/* 1947 */             JDK15Util.genThreadDumpForHTML(this.resid, this.colTime, this.threadinf, this.resname, Integer.toString(this.referenceID), this.limit, true);
/*      */           }
/*      */           else
/*      */           {
/* 1951 */             if ("JBOSS-server".equalsIgnoreCase(type)) {
/* 1952 */               this.threadinfo1 = JDK15Util.getThreadInfo(client, this.resid);
/*      */             } else {
/* 1954 */               this.threadinfo1 = JDK15Util.getThreadInfo(jc, this.resid);
/*      */             }
/* 1956 */             this.colTime = System.currentTimeMillis();
/* 1957 */             this.threadinfotime = (this.colTime - t1);
/* 1958 */             AMLog.debug("JavaRuntime Monitor : Generate Thread Dump Before Process took : " + this.threadinfotime + " ms & Thread Name " + Thread.currentThread().getName());
/*      */             
/* 1960 */             Scheduler schedular = Scheduler.getScheduler("JavaRuntimeThreadDump");
/* 1961 */             if (schedular == null)
/*      */             {
/* 1963 */               schedular = Scheduler.createScheduler("JavaRuntimeThreadDump", 10);
/* 1964 */               schedular.start();
/*      */             }
/* 1966 */             schedular.scheduleTask(this, System.currentTimeMillis() + this.delay);
/*      */           }
/*      */         }
/*      */         return;
/*      */       }
/*      */       catch (Exception e) {
/* 1972 */         e.printStackTrace();
/*      */       } finally {
/*      */         try {
/* 1975 */           if (jc != null) {
/* 1976 */             jc.close();
/* 1977 */             jc = null;
/*      */           }
/* 1979 */           else if (client != null) {
/* 1980 */             client.disconnect();
/* 1981 */             client = null;
/*      */           }
/*      */         }
/*      */         catch (Exception e) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList changeThreadDumpFormat(HashMap threadinfo1, String resid)
/*      */   {
/* 1994 */     ArrayList threadinfo = new ArrayList();
/*      */     try
/*      */     {
/* 1997 */       Set set = ((HashMap)threadinfo1.get(resid)).entrySet();
/* 1998 */       Iterator i = set.iterator();
/* 1999 */       while (i.hasNext()) {
/* 2000 */         Map.Entry me = (Map.Entry)i.next();
/* 2001 */         String ThreadName = (String)me.getKey();
/* 2002 */         HashMap map = new HashMap();
/*      */         
/* 2004 */         if (!ThreadName.equals("overallinfo"))
/*      */         {
/* 2006 */           HashMap memcomp = (HashMap)me.getValue();
/*      */           
/* 2008 */           map.put("name", ThreadName);
/* 2009 */           map.put("threadid", (Long)memcomp.get("threadid"));
/* 2010 */           map.put("blocked", (Long)memcomp.get("blocked"));
/* 2011 */           map.put("waited", (Long)memcomp.get("waited"));
/* 2012 */           map.put("state", (String)memcomp.get("state"));
/* 2013 */           map.put("lockname", (String)memcomp.get("lockname"));
/* 2014 */           map.put("trace", (ArrayList)memcomp.get("trace"));
/* 2015 */           map.put("deadlocked", (String)memcomp.get("deadlocked"));
/* 2016 */           map.put("usertime", Long.valueOf(((Long)memcomp.get("usertime")).longValue() / 1000000L));
/* 2017 */           map.put("cputime", Long.valueOf(((Long)memcomp.get("cputime")).longValue() / 1000000L));
/* 2018 */           DecimalFormat df = new DecimalFormat("#.###");
/*      */           
/* 2020 */           threadinfo.add(map);
/* 2021 */           Collections.sort(threadinfo, new CPUTimeComparator(null));
/*      */         }
/*      */       }
/* 2024 */       Collections.sort(threadinfo, new CPUTimeComparator(null));
/* 2025 */       HashMap overallinfo = new HashMap();
/* 2026 */       overallinfo.put("totalthreads", (Long)((HashMap)((HashMap)threadinfo1.get(resid)).get("overallinfo")).get("totalthreads"));
/* 2027 */       overallinfo.put("running", (Long)((HashMap)((HashMap)threadinfo1.get(resid)).get("overallinfo")).get("running"));
/* 2028 */       overallinfo.put("blocked", (Long)((HashMap)((HashMap)threadinfo1.get(resid)).get("overallinfo")).get("blocked"));
/* 2029 */       overallinfo.put("waiting", (Long)((HashMap)((HashMap)threadinfo1.get(resid)).get("overallinfo")).get("waiting"));
/* 2030 */       overallinfo.put("timedwaiting", (Long)((HashMap)((HashMap)threadinfo1.get(resid)).get("overallinfo")).get("timedwaiting"));
/* 2031 */       overallinfo.put("cputime", (Long)((HashMap)((HashMap)threadinfo1.get(resid)).get("overallinfo")).get("cputime"));
/* 2032 */       overallinfo.put("usertime", (Long)((HashMap)((HashMap)threadinfo1.get(resid)).get("overallinfo")).get("usertime"));
/* 2033 */       threadinfo.add(overallinfo);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 2037 */       exc.printStackTrace();
/*      */     }
/* 2039 */     return threadinfo;
/*      */   }
/*      */   
/*      */   private static class CPUTimeComparator implements Comparator
/*      */   {
/*      */     public int compare(Object o1, Object o2)
/*      */     {
/* 2046 */       HashMap ohm1 = (HashMap)o1;
/* 2047 */       HashMap ohm2 = (HashMap)o2;
/* 2048 */       long cpu1 = ((Long)ohm1.get("cputime")).longValue();
/* 2049 */       long cpu2 = ((Long)ohm2.get("cputime")).longValue();
/* 2050 */       if (cpu1 == cpu2)
/* 2051 */         return 0;
/* 2052 */       if (cpu1 > cpu2) {
/* 2053 */         return -1;
/*      */       }
/* 2055 */       return 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getThreadDump(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2065 */     AMConnectionPool pool = new AMConnectionPool();
/* 2066 */     String resid = request.getParameter("resourceid");
/* 2067 */     String tabval = request.getParameter("tabval");
/* 2068 */     response.setContentType("text/html; charset=UTF-8");
/* 2069 */     PrintWriter out = response.getWriter();
/* 2070 */     StringBuffer temp = null;
/* 2071 */     SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy-HH-mm-ss", Locale.getDefault());
/* 2072 */     temp = new StringBuffer();
/* 2073 */     ResultSet rs = null;
/*      */     try {
/* 2075 */       temp.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'>");
/* 2076 */       String query = "select URL,COLLECTIONTIME from AM_MONITOR_DEBUG_INFO  where RESOURCEID=" + resid + " and TYPE='Thread Dump' order by Collectiontime desc";
/* 2077 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2078 */       String url = null;
/* 2079 */       int rowCount = 0;
/* 2080 */       while (rs.next()) {
/* 2081 */         if (rowCount <= 4)
/*      */         {
/* 2083 */           rowCount++;
/*      */         }
/*      */         else {
/* 2086 */           url = rs.getString("URL") + "?resourceid=" + resid;
/* 2087 */           Date logDate = new Date(rs.getLong("COLLECTIONTIME"));
/*      */           
/* 2089 */           temp.append("<tr>");
/* 2090 */           temp.append("<td width='80%' style='padding-left:25px' class='whitegrayborderbr' title='" + url + "'> <a class='staticlinks-blue' href='javascript:void(0);' onclick=\"javascript:MM_openBrWindow('" + url + "','ThreadInfo','scrollbars=yes,resizable=yes')\">" + sdf.format(logDate) + "</a></td>");
/* 2091 */           if ((request.isUserInRole("ADMIN")) && (!EnterpriseUtil.isAdminServer()))
/*      */           {
/* 2093 */             temp.append("<td width='20%' class='whitegrayborderbr'> <a title='" + FormatUtil.getString("am.javaruntime.threaddump.delete") + "' class='staticlinks' href='javascript:void(0);' onclick=\"javascript:deleteThreadDump('" + rs.getString("URL") + "','" + resid + "','" + tabval + "');return false;\"><img hspace='5' border='0' src='/images/deleteWidget.gif'/> </a></td>");
/*      */           }
/* 2095 */           temp.append("</tr>");
/*      */         }
/*      */       }
/* 2098 */       AMConnectionPool.closeStatement(rs);
/* 2099 */       temp.append("</table>");
/*      */     } catch (Exception e) {
/* 2101 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2104 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2107 */     out.println(temp);
/* 2108 */     out.flush();
/* 2109 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getHeapdumpdata(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2116 */     AMConnectionPool pool = new AMConnectionPool();
/* 2117 */     String resid = request.getParameter("resourceid");
/* 2118 */     response.setContentType("text/html; charset=UTF-8");
/* 2119 */     PrintWriter out = response.getWriter();
/* 2120 */     StringBuffer temp = null;
/* 2121 */     temp = new StringBuffer();
/* 2122 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2125 */       String query = "select CONFVALUE from AM_CONFIGURATION_INFO where  RESOURCEID=" + resid + " and ATTRIBUTEID=5104 and LATEST=1";
/* 2126 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2127 */       String javaHome = null;
/* 2128 */       if (rs.next()) {
/* 2129 */         javaHome = rs.getString("CONFVALUE");
/*      */       }
/* 2131 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2133 */       temp.append("<table border='0' cellpadding='0' cellspacing='0' width='100%'>");
/* 2134 */       query = "select URL,COLLECTIONTIME from AM_MONITOR_DEBUG_INFO  where RESOURCEID=" + resid + " and TYPE='Heap Dump' order by Collectiontime desc";
/* 2135 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2136 */       String url = null;
/* 2137 */       int rowCount = 0;
/* 2138 */       while (rs.next()) {
/* 2139 */         if (rowCount <= 4)
/*      */         {
/* 2141 */           rowCount++;
/*      */         }
/*      */         else {
/* 2144 */           temp.append("<tr>");
/* 2145 */           temp.append("<td width='80%' style='padding-left:25px' class='whitegrayborderbr' title=" + javaHome + ">" + rs.getString("URL") + "</td>");
/* 2146 */           if ((request.isUserInRole("ADMIN")) && (!EnterpriseUtil.isAdminServer()))
/*      */           {
/* 2148 */             temp.append("<td width='20%' class='whitegrayborderbr'> <a title='" + FormatUtil.getString("am.javaruntime.heapdump.delete") + "' class='staticlinks' href='javascript:void(0);' onclick=\"javascript:deleteHeapDump('" + rs.getString("URL") + "','" + resid + "');return false;\"><img hspace='5' border='0' src='/images/deleteWidget.gif'/> </a></td>");
/*      */           }
/* 2150 */           temp.append("</tr>");
/*      */         }
/*      */       }
/* 2153 */       AMConnectionPool.closeStatement(rs);
/* 2154 */       temp.append("</table>");
/*      */     } catch (Exception e) {
/* 2156 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2159 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2162 */     out.println(temp);
/* 2163 */     out.flush();
/* 2164 */     return null;
/*      */   }
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
/*      */   public ActionForward getHeapDump(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2179 */     AMConnectionPool pool = new AMConnectionPool();
/* 2180 */     String resid = request.getParameter("resourceid");
/* 2181 */     response.setContentType("text/html; charset=UTF-8");
/* 2182 */     PrintWriter out = response.getWriter();
/* 2183 */     StringBuffer temp = null;
/* 2184 */     ResultSet rs = null;
/* 2185 */     String host = null;
/* 2186 */     int port = -1;
/* 2187 */     JMXConnector jc = null;
/*      */     try {
/* 2189 */       String query = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where resourceid=" + resid;
/* 2190 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2191 */       String resname = null;
/* 2192 */       String dspname = null;
/* 2193 */       if (rs.next()) {
/* 2194 */         resname = rs.getString("RESOURCENAME");
/* 2195 */         dspname = rs.getString("DISPLAYNAME");
/*      */       }
/* 2197 */       AMConnectionPool.closeStatement(rs);
/* 2198 */       ApplnDataCollectionAPI dcapi = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/* 2199 */       ResourceConfig rc = (ResourceConfig)dcapi.getCollectData(resname, "JDK1.5");
/* 2200 */       if (rc != null)
/*      */       {
/* 2202 */         host = rc.getHostName();
/* 2203 */         port = rc.getApplnDiscPort();
/*      */       }
/*      */       try {
/* 2206 */         jc = JDK15Util.getJMXConnector(host, port, Integer.parseInt(resid));
/*      */       } catch (Exception e) {
/* 2208 */         e.printStackTrace();
/* 2209 */         temp = new StringBuffer();
/* 2210 */         temp.append("<table cellpadding='0' cellspacing='0' width='100%' border='0'>");
/* 2211 */         temp.append("<tr><td  class='msg-status-tp-left-corn'></td><td class='msg-status-top-mid-bg'></td><td  class='msg-status-tp-right-corn'></td></tr><tr>");
/* 2212 */         temp.append("<td class='msg-status-left-bg'>&nbsp;</td><td  width='98%' class='msg-table-width'><table cellpadding='0' cellspacing='0' width='98%' border='0'><tr>");
/* 2213 */         temp.append("<td width='3%' class='msg-table-width-bg'><img src='../images/icon_message_failure.gif' alt='icon' height='20' width='20'></td>");
/* 2214 */         temp.append("<td width='98%' class='msg-table-width'>&nbsp;" + FormatUtil.getString("am.webclient.jdk15.connecterror.text") + "</td></tr></table>");
/* 2215 */         temp.append("</td><td class='msg-status-right-bg'></td></tr>");
/* 2216 */         temp.append("<tr><td class='msg-status-btm-left-corn'>&nbsp;</td><td class='msg-status-btm-mid-bg'>&nbsp;</td><td class='msg-status-btm-right-corn'>&nbsp;</td></tr></table>");
/* 2217 */         out.println(temp);
/* 2218 */         out.flush();
/* 2219 */         return null;
/*      */       }
/*      */       
/* 2222 */       boolean result = JDK15Util.triggerHeapDump(jc, System.currentTimeMillis(), resid, dspname, "0");
/* 2223 */       temp = new StringBuffer();
/* 2224 */       if (result) {
/* 2225 */         temp.append("<table cellpadding='0' cellspacing='0' width='100%' border='0'>");
/* 2226 */         temp.append("<tr><td  class='msg-status-tp-left-corn'></td><td class='msg-status-top-mid-bg'></td><td  class='msg-status-tp-right-corn'></td></tr><tr>");
/* 2227 */         temp.append("<td class='msg-status-left-bg'>&nbsp;</td><td  width='98%' class='msg-table-width'><table cellpadding='0' cellspacing='0' width='98%' border='0'><tr>");
/* 2228 */         temp.append("<td width='3%' class='msg-table-width-bg'><img src='../images/icon_message_success.gif' alt='icon' height='20' width='20'></td>");
/* 2229 */         temp.append("<td width='98%' class='msg-table-width'>&nbsp;" + FormatUtil.getString("am.javaruntime.heapdump.success") + "</td></tr></table>");
/* 2230 */         temp.append("</td><td class='msg-status-right-bg'></td></tr>");
/* 2231 */         temp.append("<tr><td class='msg-status-btm-left-corn'>&nbsp;</td><td class='msg-status-btm-mid-bg'>&nbsp;</td><td class='msg-status-btm-right-corn'>&nbsp;</td></tr></table>");
/*      */       } else {
/* 2233 */         temp.append("<table cellpadding='0' cellspacing='0' width='100%' border='0'>");
/* 2234 */         temp.append("<tr><td  class='msg-status-tp-left-corn'></td><td class='msg-status-top-mid-bg'></td><td  class='msg-status-tp-right-corn'></td></tr><tr>");
/* 2235 */         temp.append("<td class='msg-status-left-bg'>&nbsp;</td><td  width='98%' class='msg-table-width'><table cellpadding='0' cellspacing='0' width='98%' border='0'><tr>");
/* 2236 */         temp.append("<td width='3%' class='msg-table-width-bg'><img src='../images/icon_message_failure.gif' alt='icon' height='20' width='20'></td>");
/* 2237 */         temp.append("<td width='98%' class='msg-table-width'>&nbsp;" + FormatUtil.getString("am.javaruntime.heapdump.error") + "</td></tr></table>");
/* 2238 */         temp.append("</td><td class='msg-status-right-bg'></td></tr>");
/* 2239 */         temp.append("<tr><td class='msg-status-btm-left-corn'>&nbsp;</td><td class='msg-status-btm-mid-bg'>&nbsp;</td><td class='msg-status-btm-right-corn'>&nbsp;</td></tr></table>");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2247 */       AMConnectionPool.closeStatement(rs);
/*      */       try {
/* 2249 */         if (jc != null) {
/* 2250 */           jc.close();
/* 2251 */           jc = null;
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/* 2255 */       out.println(temp);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2244 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2247 */       AMConnectionPool.closeStatement(rs);
/*      */       try {
/* 2249 */         if (jc != null) {
/* 2250 */           jc.close();
/* 2251 */           jc = null;
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/* 2256 */     out.flush();
/* 2257 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward triggerGC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2266 */     AMConnectionPool pool = new AMConnectionPool();
/* 2267 */     String resid = request.getParameter("resourceid");
/* 2268 */     response.setContentType("text/html; charset=UTF-8");
/* 2269 */     PrintWriter out = response.getWriter();
/* 2270 */     StringBuffer temp = null;
/* 2271 */     ResultSet rs = null;
/* 2272 */     String host = null;
/* 2273 */     int port = -1;
/* 2274 */     JMXConnector jc = null;
/*      */     try {
/* 2276 */       String query = "select RESOURCENAME from AM_ManagedObject where resourceid=" + resid;
/* 2277 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2278 */       String resname = null;
/* 2279 */       String dspname = null;
/* 2280 */       if (rs.next()) {
/* 2281 */         resname = rs.getString("RESOURCENAME");
/*      */       }
/* 2283 */       AMConnectionPool.closeStatement(rs);
/* 2284 */       ApplnDataCollectionAPI dcapi = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/* 2285 */       ResourceConfig rc = (ResourceConfig)dcapi.getCollectData(resname, "JDK1.5");
/* 2286 */       if (rc != null)
/*      */       {
/* 2288 */         host = rc.getHostName();
/* 2289 */         port = rc.getApplnDiscPort();
/*      */       }
/*      */       try {
/* 2292 */         jc = JDK15Util.getJMXConnector(host, port, Integer.parseInt(resid));
/*      */       } catch (Exception e) {
/* 2294 */         e.printStackTrace();
/*      */       }
/* 2296 */       boolean result = JDK15Util.triggerGC(jc);
/* 2297 */       temp = new StringBuffer();
/* 2298 */       if (result) {
/* 2299 */         temp.append("<table cellpadding='0' cellspacing='0' width='100%' border='0'>");
/* 2300 */         temp.append("<tr><td  class='msg-status-tp-left-corn'></td><td class='msg-status-top-mid-bg'></td><td  class='msg-status-tp-right-corn'></td></tr><tr>");
/* 2301 */         temp.append("<td class='msg-status-left-bg'>&nbsp;</td><td  width='98%' class='msg-table-width'><table cellpadding='0' cellspacing='0' width='98%' border='0'><tr>");
/* 2302 */         temp.append("<td width='3%' class='msg-table-width-bg'><img src='../images/icon_message_success.gif' alt='icon' height='20' width='20'></td>");
/* 2303 */         temp.append("<td width='98%' class='msg-table-width'>&nbsp;" + FormatUtil.getString("am.javaruntime.action.performgc.success") + "</td></tr></table>");
/* 2304 */         temp.append("</td><td class='msg-status-right-bg'></td></tr>");
/* 2305 */         temp.append("<tr><td class='msg-status-btm-left-corn'>&nbsp;</td><td class='msg-status-btm-mid-bg'>&nbsp;</td><td class='msg-status-btm-right-corn'>&nbsp;</td></tr></table>");
/*      */       } else {
/* 2307 */         temp.append("<table cellpadding='0' cellspacing='0' width='100%' border='0'>");
/* 2308 */         temp.append("<tr><td  class='msg-status-tp-left-corn'></td><td class='msg-status-top-mid-bg'></td><td  class='msg-status-tp-right-corn'></td></tr><tr>");
/* 2309 */         temp.append("<td class='msg-status-left-bg'>&nbsp;</td><td  width='98%' class='msg-table-width'><table cellpadding='0' cellspacing='0' width='98%' border='0'><tr>");
/* 2310 */         temp.append("<td width='3%' class='msg-table-width-bg'><img src='../images/icon_message_failure.gif' alt='icon' height='20' width='20'></td>");
/* 2311 */         temp.append("<td width='98%' class='msg-table-width'>&nbsp;" + FormatUtil.getString("am.javaruntime.action.performgc.error") + "</td></tr></table>");
/* 2312 */         temp.append("</td><td class='msg-status-right-bg'></td></tr>");
/* 2313 */         temp.append("<tr><td class='msg-status-btm-left-corn'>&nbsp;</td><td class='msg-status-btm-mid-bg'>&nbsp;</td><td class='msg-status-btm-right-corn'>&nbsp;</td></tr></table>");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2321 */       AMConnectionPool.closeStatement(rs);
/*      */       try {
/* 2323 */         if (jc != null) {
/* 2324 */           jc.close();
/* 2325 */           jc = null;
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/*      */       
/* 2330 */       out.println(temp);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2318 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2321 */       AMConnectionPool.closeStatement(rs);
/*      */       try {
/* 2323 */         if (jc != null) {
/* 2324 */           jc.close();
/* 2325 */           jc = null;
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */     
/* 2331 */     out.flush();
/* 2332 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward showThreadDump(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2340 */     SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yy, HH:mm:ss z");
/* 2341 */     SimpleDateFormat sdfw = new SimpleDateFormat("d-MM-yyyy-HH-mm-ss-SS", Locale.getDefault());
/* 2342 */     AMConnectionPool pool = new AMConnectionPool();
/* 2343 */     long colTime = 0L;
/* 2344 */     String resid = request.getParameter("resourceid");
/* 2345 */     String ipaddress = request.getParameter("IP");
/* 2346 */     String uid = request.getParameter("UID");
/* 2347 */     StringBuilder resourceids = new StringBuilder();
/* 2348 */     String query = null;
/* 2349 */     ResultSet rs = null;
/* 2350 */     ArrayList urllist = new ArrayList();
/* 2351 */     Hashtable jreDspNames = new Hashtable();
/*      */     try {
/* 2353 */       if (request.getParameter("synUrls") != null) {
/*      */         try {
/* 2355 */           PrintWriter out = response.getWriter();
/* 2356 */           StringBuffer temp = new StringBuffer();
/* 2357 */           temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 2358 */           temp.append("<tr><td>");
/* 2359 */           temp.append("<span class='bodytext'> <a class=\"resourcename\" href='https://" + AMCacheHandler.getAMServerPropertiesValue("am.external.hostname") + ":" + AMCacheHandler.getAMServerPropertiesValue("am.ssl.port") + "/DebugInfo.do?method=showThreadDump&IP=" + ipaddress + "&UID=" + uid + "&apikey=" + request.getParameter("apikey") + "'target=_blank><font color=black>Thread Dump details from Managed Server " + EnterpriseUtil.getManagedServerIndex() + "</font> </a><br> </span>");
/* 2360 */           temp.append("</td></tr>");
/* 2361 */           temp.append("</table>");
/* 2362 */           out.write(temp.toString());
/* 2363 */           out.flush();
/* 2364 */           return null;
/*      */         } catch (Exception e) {
/* 2366 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/* 2370 */       if (request.getParameter("dspname") != null) {
/* 2371 */         String dspName = request.getParameter("dspname");
/* 2372 */         jreDspNames = (Hashtable)JSONUtil.getObject(dspName);
/*      */       }
/*      */       
/* 2375 */       if ((request.getRemoteUser() != null) || (APIServlet.validateAPIKey(request)))
/*      */       {
/*      */ 
/*      */ 
/* 2379 */         if ((ipaddress != null) && (uid != null))
/*      */         {
/* 2381 */           query = "select RESOURCEID,AM_ManagedObject.RESOURCENAME from AM_ManagedObject, CollectData where TARGETADDRESS='" + ipaddress + "' and COMPONENTNAME='JDK1.5' and AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME";
/* 2382 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 2383 */           while (rs.next()) {
/* 2384 */             resourceids.append(rs.getString("RESOURCEID")).append(",");
/*      */           }
/* 2386 */           AMConnectionPool.closeStatement(rs);
/* 2387 */           if (resourceids.length() != 0)
/*      */           {
/* 2389 */             query = "select DI.URL,DI.COLLECTIONTIME,DI.RESOURCEID,CI.CONFVALUE as PROCESSID,CII.CONFVALUE as PORT from AM_MONITOR_DEBUG_INFO DI left join AM_CONFIGURATION_INFO CI on CI.RESOURCEID=DI.RESOURCEID and CI.ATTRIBUTEID=5103  and CI.LATEST=1  left join  AM_CONFIGURATION_INFO CII on CII.RESOURCEID=DI.RESOURCEID and CII.ATTRIBUTEID=5081 where DI.TYPE='Thread Dump' and DI.RESOURCEID IN(" + resourceids.substring(0, resourceids.length() - 1) + ") and DI.REFERENCEID='" + uid + "'  order by  DI.COLLECTIONTIME desc";
/*      */           }
/*      */         }
/* 2392 */         else if (ipaddress != null)
/*      */         {
/* 2394 */           query = "select RESOURCEID,AM_ManagedObject.RESOURCENAME from AM_ManagedObject, CollectData where TARGETADDRESS='" + ipaddress + "' and COMPONENTNAME='JDK1.5' and AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME";
/* 2395 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 2396 */           while (rs.next()) {
/* 2397 */             resourceids.append(rs.getString("RESOURCEID")).append(",");
/*      */           }
/* 2399 */           AMConnectionPool.closeStatement(rs);
/*      */           
/* 2401 */           if (resourceids.length() != 0)
/*      */           {
/* 2403 */             query = "select DI.URL,DI.COLLECTIONTIME,DI.RESOURCEID,CI.CONFVALUE as PROCESSID,CII.CONFVALUE as PORT from AM_MONITOR_DEBUG_INFO DI left join AM_CONFIGURATION_INFO CI on CI.RESOURCEID=DI.RESOURCEID and CI.ATTRIBUTEID=5103  and CI.LATEST=1  left join  AM_CONFIGURATION_INFO CII on CII.RESOURCEID=DI.RESOURCEID and CII.ATTRIBUTEID=5081 where DI.TYPE='Thread Dump' and DI.RESOURCEID IN(" + resourceids.substring(0, resourceids.length() - 1) + ") order by  DI.COLLECTIONTIME desc";
/*      */           }
/*      */         }
/* 2406 */         else if (uid != null)
/*      */         {
/* 2408 */           query = "select DI.URL,DI.COLLECTIONTIME,DI.RESOURCEID,CI.CONFVALUE as PROCESSID,CII.CONFVALUE as PORT from AM_MONITOR_DEBUG_INFO DI left join AM_CONFIGURATION_INFO CI on CI.RESOURCEID=DI.RESOURCEID and CI.ATTRIBUTEID=5103  and CI.LATEST=1  left join  AM_CONFIGURATION_INFO CII on CII.RESOURCEID=DI.RESOURCEID and CII.ATTRIBUTEID=5081 where DI.TYPE='Thread Dump' and DI.REFERENCEID='" + uid + "'  order by  DI.COLLECTIONTIME desc";
/*      */         }
/*      */         
/* 2411 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2412 */         String portNum = null;
/* 2413 */         while (rs.next()) {
/* 2414 */           Properties list = new Properties();
/* 2415 */           list.setProperty("URL", rs.getString("URL"));
/* 2416 */           Date logDate = new Date(rs.getLong("COLLECTIONTIME"));
/* 2417 */           list.setProperty("DSPNAME", sdfw.format(logDate));
/* 2418 */           portNum = rs.getString("PORT");
/* 2419 */           if (jreDspNames.containsKey(portNum)) {
/* 2420 */             portNum = (String)jreDspNames.get(portNum);
/*      */           }
/* 2422 */           list.setProperty("DSPNAME_EXT", "<B>PID:</B>" + rs.getString("PROCESSID") + "  (" + portNum + ")");
/* 2423 */           urllist.add(list);
/*      */         }
/* 2425 */         AMConnectionPool.closeStatement(rs);
/* 2426 */         request.setAttribute("urllist", urllist);
/*      */       }
/*      */       else
/*      */       {
/* 2430 */         request.setAttribute("urllist", urllist);
/* 2431 */         request.setAttribute("error", FormatUtil.getString("am.javaruntime.wrongapikey"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2436 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2439 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2441 */     return mapping.findForward("showthreaddump");
/*      */   }
/*      */   
/*      */   public ActionForward editMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2446 */     String resourceid = request.getParameter("resourceid");
/* 2447 */     String moname = request.getParameter("moname");
/* 2448 */     String pollinterval = request.getParameter("pollInterval");
/* 2449 */     int poll = Integer.parseInt(pollinterval);
/* 2450 */     poll *= 60;
/* 2451 */     pollinterval = poll + "";
/* 2452 */     String port = request.getParameter("port");
/* 2453 */     String displayname = ((AMActionForm)form).getDisplayname();
/* 2454 */     String query = "update AM_ManagedObject set displayname='" + displayname + "' where resourceid=" + resourceid;
/* 2455 */     EnterpriseUtil.addUpdateQueryToFile(query);
/* 2456 */     AMConnectionPool.executeUpdateStmt(query);
/* 2457 */     query = "update CollectData set pollinterval=" + pollinterval + " ,APPLNDISCPORT =" + port + " where resourcename like '" + moname + "'";
/* 2458 */     AMConnectionPool.executeUpdateStmt(query);
/* 2459 */     query = "update InetService set PORTNO=" + port + " where NAME='" + moname + "'";
/* 2460 */     AMConnectionPool.executeUpdateStmt(query);
/* 2461 */     String jndiurl = request.getParameter("jndiurl");
/* 2462 */     String username = ((AMActionForm)form).getUsername();
/* 2463 */     String password = ((AMActionForm)form).getPassword();
/* 2464 */     String jmxurl = request.getParameter("jmxurl");
/* 2465 */     query = "update AM_RESOURCECONFIG set DATABASENAME='" + jndiurl + "',USERNAME='" + username + "',PASSWORD=" + DBQueryUtil.encode(password) + "  where resourceid=" + resourceid;
/* 2466 */     AMConnectionPool.executeUpdateStmt(query);
/* 2467 */     if ((jmxurl != null) && (!"".equalsIgnoreCase(jmxurl))) {
/* 2468 */       AMConnectionPool.executeUpdateStmt("uupdate AM_MX4J_EXT_INFO set JMXURL='" + jmxurl + "' where RESOURCEID=" + resourceid);
/*      */     }
/*      */     
/*      */ 
/* 2472 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward gcViewImpact(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2479 */     String resid = request.getParameter("resourceid");
/* 2480 */     String gcresid = request.getParameter("gcresourceid");
/*      */     
/* 2482 */     long coltime = 0L;
/* 2483 */     float gccoltime = 0.0F;
/* 2484 */     ResultSet rs = null;
/*      */     try {
/* 2486 */       String query = "select max(collectiontime) as coltime from AM_JDK15_HEAPMEMORYINFO where resourceid=" + resid;
/* 2487 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2488 */       if (rs.next()) {
/* 2489 */         coltime = rs.getLong("coltime");
/*      */       }
/* 2491 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2493 */       String vendor = "Sun";
/* 2494 */       String jvmtype = "Java HotSpot";
/*      */       
/* 2496 */       vendor = DBUtil.getConfigurationDetails(String.valueOf(resid), "5090");
/* 2497 */       jvmtype = DBUtil.getConfigurationDetails(String.valueOf(resid), "5089");
/*      */       
/* 2499 */       vendor = (jvmtype != null) && (jvmtype.indexOf("Java HotSpot") != -1) && (vendor != null) && (vendor.contains("Oracle")) ? "Sun Microsystems Inc." : vendor;
/* 2500 */       request.setAttribute("vendor", vendor);
/*      */       
/* 2502 */       boolean isMetaspace = JDK15Util.GetIsMetaspaceAsBoolean(vendor, jvmtype);
/* 2503 */       request.setAttribute("isMetaspace", isMetaspace + "");
/*      */       
/* 2505 */       HashMap memcomp = new HashMap();
/* 2506 */       query = "select mo.DISPLAYNAME,bgc.*,ebgc.*,agc.*,eagc.*  from AM_JDK15_HEAPMEMORYINFO_BGC bgc, AM_JDK15_NONHEAPMEMORYINFO_BGC ebgc,AM_JDK15_HEAPMEMORYINFO_AGC agc, AM_JDK15_NONHEAPMEMORYINFO_AGC eagc ,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where bgc.resourceid=mo.resourceid and mo.resourceid=ebgc.resourceid and mo.resourceid=eagc.resourceid and mo.resourceid=eagc.resourceid and  bgc.resourceid=pcm.childid  and ebgc.resourceid=pcm.childid and  agc.resourceid=pcm.childid  and eagc.resourceid=pcm.childid  and pcm.parentid=" + resid + " and bgc.collectiontime=" + coltime + " and  ebgc.collectiontime=" + coltime + " and  agc.collectiontime=" + coltime + " and  eagc.collectiontime=" + coltime + " and bgc.resourceid=" + gcresid + " and  ebgc.resourceid=" + gcresid + " and  agc.resourceid=" + gcresid + " and  eagc.resourceid=" + gcresid;
/* 2507 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2508 */       ArrayList buffdata = new ArrayList();
/* 2509 */       if (rs.next()) {
/* 2510 */         memcomp = new HashMap();
/* 2511 */         buffdata.add(rs.getString("RESOURCEID"));
/* 2512 */         memcomp.put("gcresid", rs.getString("RESOURCEID"));
/* 2513 */         gccoltime = (float)(rs.getLong("COLTIME") / 1000L);
/* 2514 */         memcomp.put("collectiontime", Float.valueOf(gccoltime));
/*      */         
/* 2516 */         if (rs.getString("COLCOUNT").equals("-1")) {
/* 2517 */           memcomp.put("collection_count", "-");
/*      */         } else {
/* 2519 */           memcomp.put("collection_count", rs.getString("COLCOUNT"));
/*      */         }
/* 2521 */         if (rs.getString("THREADCOUNT").equals("-1")) {
/* 2522 */           memcomp.put("thread_count", "-");
/*      */         } else {
/* 2524 */           memcomp.put("thread_count", rs.getString("THREADCOUNT"));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2529 */         memcomp.put("dcname", rs.getString("DISPLAYNAME"));
/* 2530 */         if (rs.getString("AUSEDHEAP").equals("-1")) {
/* 2531 */           memcomp.put("heap_used_agc", "-");
/*      */         } else {
/* 2533 */           memcomp.put("heap_used_agc", rs.getString("AUSEDHEAP"));
/*      */         }
/* 2535 */         if (rs.getString("AUSEDHEAPPER").equals("-1")) {
/* 2536 */           memcomp.put("heap_per_agc", "-");
/*      */         } else {
/* 2538 */           memcomp.put("heap_per_agc", rs.getString("AUSEDHEAPPER"));
/*      */         }
/* 2540 */         if (rs.getString("AUSEDHEAPDIFF").equals("-1")) {
/* 2541 */           memcomp.put("heap_dif_agc", "-");
/*      */         } else {
/* 2543 */           memcomp.put("heap_dif_agc", rs.getString("AUSEDHEAPDIFF"));
/*      */         }
/* 2545 */         if (rs.getString("AUSEDEDEN").equals("-1")) {
/* 2546 */           memcomp.put("Eden Space_used_agc", "-");
/*      */         } else {
/* 2548 */           memcomp.put("Eden Space_used_agc", rs.getString("AUSEDEDEN"));
/*      */         }
/* 2550 */         if (rs.getString("AEDENPER").equals("-1")) {
/* 2551 */           memcomp.put("Eden Space_per_agc", "-");
/*      */         } else {
/* 2553 */           memcomp.put("Eden Space_per_agc", rs.getString("AEDENPER"));
/*      */         }
/* 2555 */         if (rs.getString("AUSEDEDENDIFF").equals("-1")) {
/* 2556 */           memcomp.put("Eden Space_dif_agc", "-");
/*      */         } else {
/* 2558 */           memcomp.put("Eden Space_dif_agc", rs.getString("AUSEDEDENDIFF"));
/*      */         }
/* 2560 */         if (rs.getString("AUSEDSURVIVOR").equals("-1")) {
/* 2561 */           memcomp.put("Survivor Space_used_agc", "-");
/*      */         } else {
/* 2563 */           memcomp.put("Survivor Space_used_agc", rs.getString("AUSEDSURVIVOR"));
/*      */         }
/* 2565 */         if (rs.getString("ASURVIVORPER").equals("-1")) {
/* 2566 */           memcomp.put("Survivor Space_per_agc", "-");
/*      */         } else {
/* 2568 */           memcomp.put("Survivor Space_per_agc", rs.getString("ASURVIVORPER"));
/*      */         }
/* 2570 */         if (rs.getString("AUSEDSURVIVORDIFF").equals("-1")) {
/* 2571 */           memcomp.put("Survivor Space_dif_agc", "-");
/*      */         } else {
/* 2573 */           memcomp.put("Survivor Space_dif_agc", rs.getString("AUSEDSURVIVORDIFF"));
/*      */         }
/* 2575 */         if (rs.getString("AUSEDTENGEN").equals("-1")) {
/* 2576 */           memcomp.put("Tenured Gen_used_agc", "-");
/*      */         } else {
/* 2578 */           memcomp.put("Tenured Gen_used_agc", rs.getString("AUSEDTENGEN"));
/*      */         }
/* 2580 */         if (rs.getString("ATENGENPER").equals("-1")) {
/* 2581 */           memcomp.put("Tenured Gen_per_agc", "-");
/*      */         } else {
/* 2583 */           memcomp.put("Tenured Gen_per_agc", rs.getString("ATENGENPER"));
/*      */         }
/* 2585 */         if (rs.getString("AUSEDTENGENDIFF").equals("-1")) {
/* 2586 */           memcomp.put("Tenured Gen_dif_agc", "-");
/*      */         } else {
/* 2588 */           memcomp.put("Tenured Gen_dif_agc", rs.getString("AUSEDTENGENDIFF"));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2593 */         if (rs.getString("AUSEDNONHEAP").equals("-1")) {
/* 2594 */           memcomp.put("nonheap_used_agc", "-");
/*      */         } else {
/* 2596 */           memcomp.put("nonheap_used_agc", rs.getString("AUSEDNONHEAP"));
/*      */         }
/* 2598 */         if (rs.getString("AUSEDNONHEAPPER").equals("-1")) {
/* 2599 */           memcomp.put("nonheap_per_agc", "-");
/*      */         } else {
/* 2601 */           memcomp.put("nonheap_per_agc", rs.getString("AUSEDNONHEAPPER"));
/*      */         }
/* 2603 */         if (rs.getString("AUSEDNONHEAPDIFF").equals("-1")) {
/* 2604 */           memcomp.put("nonheap_dif_agc", "-");
/*      */         } else {
/* 2606 */           memcomp.put("nonheap_dif_agc", rs.getString("AUSEDNONHEAPDIFF"));
/*      */         }
/* 2608 */         if (!isMetaspace) {
/* 2609 */           if (rs.getString("AUSEDPERMGEN").equals("-1")) {
/* 2610 */             memcomp.put("Perm Gen_used_agc", "-");
/*      */           } else {
/* 2612 */             memcomp.put("Perm Gen_used_agc", rs.getString("AUSEDPERMGEN"));
/*      */           }
/* 2614 */           if (rs.getString("APERMGENPER").equals("-1")) {
/* 2615 */             memcomp.put("Perm Gen_per_agc", "-");
/*      */           } else {
/* 2617 */             memcomp.put("Perm Gen_per_agc", rs.getString("APERMGENPER"));
/*      */           }
/* 2619 */           if (rs.getString("AUSEDPERMGENDIFF").equals("-1")) {
/* 2620 */             memcomp.put("Perm Gen_dif_agc", "-");
/*      */           } else {
/* 2622 */             memcomp.put("Perm Gen_dif_agc", rs.getString("AUSEDPERMGENDIFF"));
/*      */           }
/*      */           
/*      */ 
/* 2626 */           if (rs.getString("AUSEDPERMGENRO").equals("-1")) {
/* 2627 */             memcomp.put("Perm Gen [shared-ro]_used_agc", "-");
/*      */           } else {
/* 2629 */             memcomp.put("Perm Gen [shared-ro]_used_agc", rs.getString("AUSEDPERMGENRO"));
/*      */           }
/* 2631 */           if (rs.getString("APERMGENROPER").equals("-1")) {
/* 2632 */             memcomp.put("Perm Gen [shared-ro]_per_agc", "-");
/*      */           } else {
/* 2634 */             memcomp.put("Perm Gen [shared-ro]_per_agc", rs.getString("APERMGENROPER"));
/*      */           }
/* 2636 */           if (rs.getString("AUSEDPERMGENRODIFF").equals("-1")) {
/* 2637 */             memcomp.put("Perm Gen [shared-ro]_dif_agc", "-");
/*      */           } else {
/* 2639 */             memcomp.put("Perm Gen [shared-ro]_dif_agc", rs.getString("AUSEDPERMGENRODIFF"));
/*      */           }
/* 2641 */           if (rs.getString("AUSEDPERMGENRW").equals("-1")) {
/* 2642 */             memcomp.put("Perm Gen [shared-rw]_used_agc", "-");
/*      */           } else {
/* 2644 */             memcomp.put("Perm Gen [shared-rw]_used_agc", rs.getString("AUSEDPERMGENRW"));
/*      */           }
/* 2646 */           if (rs.getString("APERMGENRWPER").equals("-1")) {
/* 2647 */             memcomp.put("Perm Gen [shared-rw]_per_agc", "-");
/*      */           } else {
/* 2649 */             memcomp.put("Perm Gen [shared-rw]_per_agc", rs.getString("APERMGENRWPER"));
/*      */           }
/* 2651 */           if (rs.getString("AUSEDPERMGENRWDIFF").equals("-1")) {
/* 2652 */             memcomp.put("Perm Gen [shared-rw]_dif_agc", "-");
/*      */           } else {
/* 2654 */             memcomp.put("Perm Gen [shared-rw]_dif_agc", rs.getString("AUSEDPERMGENRWDIFF"));
/*      */           }
/*      */         }
/* 2657 */         if (rs.getString("AUSEDCODECACHE").equals("-1")) {
/* 2658 */           memcomp.put("Code Cache_used_agc", "-");
/*      */         } else {
/* 2660 */           memcomp.put("Code Cache_used_agc", rs.getString("AUSEDCODECACHE"));
/*      */         }
/* 2662 */         if (rs.getString("ACODECACHEPER").equals("-1")) {
/* 2663 */           memcomp.put("Code Cache_per_agc", "-");
/*      */         } else {
/* 2665 */           memcomp.put("Code Cache_per_agc", rs.getString("ACODECACHEPER"));
/*      */         }
/*      */         
/* 2668 */         if (rs.getString("AUSEDCODECACHEDIFF").equals("-1")) {
/* 2669 */           memcomp.put("Code Cache_dif_agc", "-");
/*      */         } else {
/* 2671 */           memcomp.put("Code Cache_dif_agc", rs.getString("AUSEDCODECACHEDIFF"));
/*      */         }
/*      */         
/* 2674 */         if (isMetaspace) {
/* 2675 */           if (rs.getString("AUSEDMSPACE").equals("-1")) {
/* 2676 */             memcomp.put("Metaspace_used_agc", "-");
/*      */           } else {
/* 2678 */             memcomp.put("Metaspace_used_agc", rs.getString("AUSEDMSPACE"));
/*      */           }
/* 2680 */           if (rs.getString("AMSPACEPER").equals("-1")) {
/* 2681 */             memcomp.put("Metaspace_per_agc", "-");
/*      */           } else {
/* 2683 */             memcomp.put("Metaspace_per_agc", rs.getString("AMSPACEPER"));
/*      */           }
/* 2685 */           if (rs.getString("AUSEDMSPACEDIFF").equals("-1")) {
/* 2686 */             memcomp.put("Metaspace_dif_agc", "-");
/*      */           } else {
/* 2688 */             memcomp.put("Metaspace_dif_agc", rs.getString("AUSEDMSPACEDIFF"));
/*      */           }
/* 2690 */           if (rs.getString("AUSEDCSPACE").equals("-1")) {
/* 2691 */             memcomp.put("Compressed Class Space_used_agc", "-");
/*      */           } else {
/* 2693 */             memcomp.put("Compressed Class Space_used_agc", rs.getString("AUSEDCSPACE"));
/*      */           }
/* 2695 */           if (rs.getString("ACSPACEPER").equals("-1")) {
/* 2696 */             memcomp.put("Compressed Class Space_per_agc", "-");
/*      */           } else {
/* 2698 */             memcomp.put("Compressed Class Space_per_agc", rs.getString("ACSPACEPER"));
/*      */           }
/* 2700 */           if (rs.getString("AUSEDCSPACEDIFF").equals("-1")) {
/* 2701 */             memcomp.put("Compressed Class Space_dif_agc", "-");
/*      */           } else {
/* 2703 */             memcomp.put("Compressed Class Space_dif_agc", rs.getString("AUSEDCSPACEDIFF"));
/*      */           }
/*      */         }
/*      */         
/* 2707 */         if (rs.getString("USEDHEAP").equals("-1")) {
/* 2708 */           memcomp.put("heap_used_bgc", "-");
/*      */         } else {
/* 2710 */           memcomp.put("heap_used_bgc", rs.getString("USEDHEAP"));
/*      */         }
/* 2712 */         if (rs.getString("USEDHEAPPER").equals("-1")) {
/* 2713 */           memcomp.put("heap_per_bgc", "-");
/*      */         } else {
/* 2715 */           memcomp.put("heap_per_bgc", rs.getString("USEDHEAPPER"));
/*      */         }
/* 2717 */         if (rs.getString("USEDEDEN").equals("-1")) {
/* 2718 */           memcomp.put("Eden Space_used_bgc", "-");
/*      */         } else {
/* 2720 */           memcomp.put("Eden Space_used_bgc", rs.getString("USEDEDEN"));
/*      */         }
/* 2722 */         if (rs.getString("EDENPER").equals("-1")) {
/* 2723 */           memcomp.put("Eden Space_per_bgc", "-");
/*      */         } else {
/* 2725 */           memcomp.put("Eden Space_per_bgc", rs.getString("EDENPER"));
/*      */         }
/* 2727 */         if (rs.getString("USEDSURVIVOR").equals("-1")) {
/* 2728 */           memcomp.put("Survivor Space_used_bgc", "-");
/*      */         } else {
/* 2730 */           memcomp.put("Survivor Space_used_bgc", rs.getString("USEDSURVIVOR"));
/*      */         }
/* 2732 */         if (rs.getString("SURVIVORPER").equals("-1")) {
/* 2733 */           memcomp.put("Survivor Space_per_bgc", "-");
/*      */         } else {
/* 2735 */           memcomp.put("Survivor Space_per_bgc", rs.getString("SURVIVORPER"));
/*      */         }
/* 2737 */         if (rs.getString("USEDTENGEN").equals("-1")) {
/* 2738 */           memcomp.put("Tenured Gen_used_bgc", "-");
/*      */         } else {
/* 2740 */           memcomp.put("Tenured Gen_used_bgc", rs.getString("USEDTENGEN"));
/*      */         }
/* 2742 */         if (rs.getString("TENGENPER").equals("-1")) {
/* 2743 */           memcomp.put("Tenured Gen_per_bgc", "-");
/*      */         } else {
/* 2745 */           memcomp.put("Tenured Gen_per_bgc", rs.getString("TENGENPER"));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2750 */         if (rs.getString("USEDNONHEAP").equals("-1")) {
/* 2751 */           memcomp.put("nonheap_used_bgc", "-");
/*      */         } else {
/* 2753 */           memcomp.put("nonheap_used_bgc", rs.getString("USEDNONHEAP"));
/*      */         }
/* 2755 */         if (rs.getString("USEDNONHEAPPER").equals("-1")) {
/* 2756 */           memcomp.put("nonheap_per_bgc", "-");
/*      */         } else {
/* 2758 */           memcomp.put("nonheap_per_bgc", rs.getString("USEDNONHEAPPER"));
/*      */         }
/* 2760 */         if (rs.getString("USEDCODECACHE").equals("-1")) {
/* 2761 */           memcomp.put("Code Cache_used_bgc", "-");
/*      */         } else {
/* 2763 */           memcomp.put("Code Cache_used_bgc", rs.getString("USEDCODECACHE"));
/*      */         }
/* 2765 */         if (rs.getString("CODECACHEPER").equals("-1")) {
/* 2766 */           memcomp.put("Code Cache_per_bgc", "-");
/*      */         } else {
/* 2768 */           memcomp.put("Code Cache_per_bgc", rs.getString("CODECACHEPER"));
/*      */         }
/* 2770 */         if (!isMetaspace) {
/* 2771 */           if (rs.getString("USEDPERMGEN").equals("-1")) {
/* 2772 */             memcomp.put("Perm Gen_used_bgc", "-");
/*      */           } else {
/* 2774 */             memcomp.put("Perm Gen_used_bgc", rs.getString("USEDPERMGEN"));
/*      */           }
/* 2776 */           if (rs.getString("PERMGENPER").equals("-1")) {
/* 2777 */             memcomp.put("Perm Gen_per_bgc", "-");
/*      */           } else {
/* 2779 */             memcomp.put("Perm Gen_per_bgc", rs.getString("PERMGENPER"));
/*      */           }
/*      */           
/*      */ 
/* 2783 */           if (rs.getString("USEDPERMGENRO").equals("-1")) {
/* 2784 */             memcomp.put("Perm Gen [shared-ro]_used_bgc", "-");
/*      */           } else {
/* 2786 */             memcomp.put("Perm Gen [shared-ro]_used_bgc", rs.getString("USEDPERMGENRO"));
/*      */           }
/* 2788 */           if (rs.getString("PERMGENROPER").equals("-1")) {
/* 2789 */             memcomp.put("Perm Gen [shared-ro]_per_bgc", "-");
/*      */           } else {
/* 2791 */             memcomp.put("Perm Gen [shared-ro]_per_bgc", rs.getString("PERMGENROPER"));
/*      */           }
/* 2793 */           if (rs.getString("USEDPERMGENRW").equals("-1")) {
/* 2794 */             memcomp.put("Perm Gen [shared-rw]_used_bgc", "-");
/*      */           } else {
/* 2796 */             memcomp.put("Perm Gen [shared-rw]_used_bgc", rs.getString("USEDPERMGENRW"));
/*      */           }
/* 2798 */           if (rs.getString("PERMGENRWPER").equals("-1")) {
/* 2799 */             memcomp.put("Perm Gen [shared-rw]_per_bgc", "-");
/*      */           } else {
/* 2801 */             memcomp.put("Perm Gen [shared-rw]_per_bgc", rs.getString("PERMGENRWPER"));
/*      */           }
/*      */         }
/* 2804 */         if (isMetaspace) {
/* 2805 */           if (rs.getString("USEDMSPACE").equals("-1")) {
/* 2806 */             memcomp.put("Metaspace_used_bgc", "-");
/*      */           } else {
/* 2808 */             memcomp.put("Metaspace_used_bgc", rs.getString("USEDMSPACE"));
/*      */           }
/* 2810 */           if (rs.getString("MSPACEPER").equals("-1")) {
/* 2811 */             memcomp.put("Metaspace_per_bgc", "-");
/*      */           } else {
/* 2813 */             memcomp.put("Metaspace_per_bgc", rs.getString("MSPACEPER"));
/*      */           }
/*      */           
/* 2816 */           if (rs.getString("USEDCSPACE").equals("-1")) {
/* 2817 */             memcomp.put("Compressed Class Space_used_bgc", "-");
/*      */           } else {
/* 2819 */             memcomp.put("Compressed Class Space_used_bgc", rs.getString("USEDCSPACE"));
/*      */           }
/* 2821 */           if (rs.getString("CSPACEPER").equals("-1")) {
/* 2822 */             memcomp.put("Compressed Class Space_per_bgc", "-");
/*      */           } else {
/* 2824 */             memcomp.put("Compressed Class Space_per_bgc", rs.getString("CSPACEPER"));
/*      */           }
/*      */         }
/*      */       }
/* 2828 */       AMConnectionPool.closeStatement(rs);
/* 2829 */       request.setAttribute("buffdata", buffdata);
/* 2830 */       request.setAttribute("gcmemoryProp", memcomp);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2834 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2837 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2839 */     return mapping.findForward("viewimpact");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward sendActionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2847 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2848 */     AMActionForm am = (AMActionForm)form;
/* 2849 */     ActionMessages messages = new ActionMessages();
/* 2850 */     ActionErrors errors = new ActionErrors();
/* 2851 */     response.setContentType("text/html; charset=UTF-8");
/* 2852 */     ResultSet rs = null;
/* 2853 */     String temp = null;
/* 2854 */     String sname = null;
/* 2855 */     String emailid = null;
/* 2856 */     String host = InetAddress.getLocalHost().getHostName();
/* 2857 */     String message = "Java";
/* 2858 */     String port = System.getProperty("webserver.port");
/* 2859 */     ResultSet rst = null;
/*      */     try
/*      */     {
/* 2862 */       if (request.getParameter("message") != null) {
/* 2863 */         message = request.getParameter("message");
/*      */       }
/* 2865 */       emailid = request.getParameter("emailid");
/* 2866 */       int i = 0;
/* 2867 */       String actionquery = null;
/* 2868 */       if (emailid != null)
/*      */       {
/* 2870 */         String q1 = "select HOST from AM_MAILSETTINGS";
/* 2871 */         rst = AMConnectionPool.executeQueryStmt(q1);
/* 2872 */         String returnVal = null;
/* 2873 */         String sentactid = null;
/* 2874 */         if (rst.next())
/*      */         {
/* 2876 */           String portFilled = FormatUtil.getString("am.javaruntime.javaaction.email.message", new String[] { message });
/*      */           
/*      */ 
/* 2879 */           if (returnVal == null)
/*      */           {
/* 2881 */             sname = request.getParameter("emailname");
/* 2882 */             sname = sname + "_Action";
/* 2883 */             String query = "SELECT COUNT(*) FROM AM_ACTIONPROFILE WHERE NAME like '" + sname + "%'";
/* 2884 */             rst = AMConnectionPool.executeQueryStmt(query);
/* 2885 */             if (rst.next())
/*      */             {
/* 2887 */               int cnt = rst.getInt(1);
/* 2888 */               if (cnt > 0)
/*      */               {
/* 2890 */                 sname = sname + cnt;
/*      */               }
/*      */             }
/* 2893 */             String actid = DBQueryUtil.getIncrementedID("ID", "AM_ACTIONPROFILE") + "";
/* 2894 */             actionquery = "INSERT INTO AM_ACTIONPROFILE (ID,NAME,TYPE) VALUES(" + actid + ",'" + sname + "','1')";
/* 2895 */             AMConnectionPool.executeUpdateStmt(actionquery);
/* 2896 */             String subject = FormatUtil.getString("am.webclient.managermail.bsm.alertfrommessage.text", new String[] { OEMUtil.getOEMString("product.name") });
/* 2897 */             String act2 = "insert into AM_EMAILACTION (ID, FROMADDRESS, TOADDRESS, SUBJECT, MESSAGE,SMTPPORT) values (" + actid + ",'" + emailid + "','" + emailid + "','" + subject + "','" + FormatUtil.getString("am.webclient.mail.default.message.text", new String[] { OEMUtil.getOEMString("product.name") }) + "',25)";
/* 2898 */             AMConnectionPool.executeUpdateStmt(act2);
/* 2899 */             Properties pro = new Properties();
/* 2900 */             ArrayList rows = new ArrayList();
/* 2901 */             pro.setProperty("label", sname);
/* 2902 */             pro.setProperty("value", actid);
/* 2903 */             rows.add(pro);
/*      */             
/* 2905 */             am.setSendmail(actid);
/* 2906 */             sentactid = sname + "," + actid;
/* 2907 */             request.setAttribute("tabtoselect", "3");
/* 2908 */             PrintWriter pw = response.getWriter();
/* 2909 */             pw.print(sentactid);
/*      */           }
/*      */           else
/*      */           {
/* 2913 */             request.setAttribute("tabtoselect", "3");
/*      */             
/* 2915 */             returnVal = FormatUtil.getString("am.webclient.schedulereport.showwschedule.mailmessage.text");
/* 2916 */             sentactid = returnVal + ",0";
/* 2917 */             PrintWriter pw = response.getWriter();
/* 2918 */             pw.print(sentactid);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 2923 */           returnVal = FormatUtil.getString("am.webclient.schedulereport.showwschedule.smtpmailmessage.text");
/*      */           
/* 2925 */           sentactid = returnVal + ",0";
/* 2926 */           PrintWriter pw = response.getWriter();
/* 2927 */           pw.print(sentactid);
/*      */         }
/* 2929 */         AMConnectionPool.closeStatement(rst);
/*      */       }
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/* 2934 */       es.printStackTrace();
/*      */     }
/*      */     finally {
/* 2937 */       AMConnectionPool.closeStatement(rst);
/* 2938 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2940 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward showThreadDumpAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2947 */     if (request.getRemoteUser() != null) {
/* 2948 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2949 */       ResultSet set = null;
/* 2950 */       String monitorType = request.getParameter("monitorType");
/*      */       try
/*      */       {
/* 2953 */         AMActionForm amform = (AMActionForm)form;
/* 2954 */         ActionMessages messages = new ActionMessages();
/* 2955 */         ArrayList rows = new ArrayList();
/* 2956 */         String actionID = request.getParameter("actionID");
/*      */         
/* 2958 */         if ((request.getParameter("save") == null) || (request.getParameter("save").equals("null"))) {
/* 2959 */           ((AMActionForm)form).setLogConfig("0");
/* 2960 */           amform.setMethod("createThreadDumpAction");
/*      */           
/* 2962 */           if (monitorType.equalsIgnoreCase("amazon"))
/*      */           {
/* 2964 */             ((AMActionForm)form).setJtaskMethod("Start");
/*      */           }
/* 2966 */           if ((request.getParameter("type") != null) && (request.getParameter("type").equals("7"))) {
/* 2967 */             ((AMActionForm)form).setJtaskMethod("ThreadDump");
/* 2968 */           } else if ((request.getParameter("type") != null) && (request.getParameter("type").equals("8"))) {
/* 2969 */             ((AMActionForm)form).setJtaskMethod("HeapDump");
/* 2970 */           } else if ((request.getParameter("type") != null) && (request.getParameter("type").equals("9"))) {
/* 2971 */             ((AMActionForm)form).setJtaskMethod("PerformGC");
/* 2972 */           } else if ((request.getParameter("type") != null) && (request.getParameter("type").equals("14"))) {
/* 2973 */             ((AMActionForm)form).setJtaskMethod("Start");
/* 2974 */           } else if ((request.getParameter("type") != null) && (request.getParameter("type").equals("15"))) {
/* 2975 */             ((AMActionForm)form).setJtaskMethod("Stop");
/* 2976 */           } else if ((request.getParameter("type") != null) && (request.getParameter("type").equals("16"))) {
/* 2977 */             ((AMActionForm)form).setJtaskMethod("Restart");
/*      */           }
/*      */         }
/* 2980 */         String query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1";
/* 2981 */         if (EnterpriseUtil.isAdminServer()) {
/* 2982 */           query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1 and AM_ACTIONPROFILE.NAME !='ADMINEMAIL'";
/*      */         }
/*      */         try
/*      */         {
/* 2986 */           set = AMConnectionPool.executeQueryStmt(query);
/* 2987 */           AMLog.debug("Java Thread Dump Action : " + query);
/* 2988 */           while (set.next())
/*      */           {
/* 2990 */             String labelvalue = set.getString(2) + ":(" + set.getString(3) + ")";
/* 2991 */             Properties dataProps = new Properties();
/* 2992 */             dataProps.setProperty("label", labelvalue);
/* 2993 */             dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 2994 */             rows.add(dataProps);
/*      */           }
/* 2996 */           set.close();
/*      */           
/* 2998 */           ((AMActionForm)form).setMaillist(rows);
/* 2999 */           ((AMActionForm)form).setTdcount(2);
/* 3000 */           ((AMActionForm)form).setTddelay(30);
/*      */ 
/*      */         }
/*      */         catch (Exception exp)
/*      */         {
/* 3005 */           AMLog.fatal("Java Thread Dump Action :  Exception ", exp);
/* 3006 */           exp.printStackTrace();
/* 3007 */           throw new Exception(exp);
/*      */         }
/*      */         
/*      */ 
/* 3011 */         ArrayList applications = new ArrayList();
/* 3012 */         boolean isPrivilegedUser = false;
/* 3013 */         if (ClientDBUtil.isPrivilegedUser(request)) {
/* 3014 */           isPrivilegedUser = true;
/*      */         }
/* 3016 */         if ((isPrivilegedUser) && (!EnterpriseUtil.isIt360MSPEdition())) {
/* 3017 */           applications = AlarmUtil.getApplicationsForOwner(request.getRemoteUser(), request);
/*      */         }
/* 3019 */         else if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 3021 */           applications = AlarmUtil.getConfiguredGroups(request);
/*      */         }
/*      */         else
/*      */         {
/* 3025 */           applications = AlarmUtil.getConfiguredGroups();
/*      */         }
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
/* 3038 */         if (applications != null)
/*      */         {
/* 3040 */           request.setAttribute("applications", applications);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 3045 */         String privilegeCondition = "";
/* 3046 */         boolean isUserResourceEnabled = false;
/* 3047 */         String loginUserid = null;
/* 3048 */         if (isPrivilegedUser) {
/* 3049 */           if (Constants.isUserResourceEnabled()) {
/* 3050 */             isUserResourceEnabled = true;
/* 3051 */             loginUserid = Constants.getLoginUserid(request);
/*      */           } else {
/* 3053 */             Vector resourceids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 3054 */             privilegeCondition = " and " + DependantMOUtil.getCondition("RESOURCEID", resourceids);
/*      */           }
/*      */         }
/*      */         
/* 3058 */         if (monitorType.equalsIgnoreCase("jre")) {
/* 3059 */           if (isUserResourceEnabled) {
/* 3060 */             query = "select AM_ManagedObject.RESOURCEID,RESOURCENAME,DISPLAYNAME,TYPE from AM_ManagedObject,AM_USERRESOURCESTABLE where AM_ManagedObject.RESORUCEID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and TYPE IN " + Constants.jmxTypes + " ORDER BY RESOURCENAME";
/*      */           } else {
/* 3062 */             query = "select RESOURCEID,RESOURCENAME,DISPLAYNAME,TYPE from AM_ManagedObject where TYPE IN " + Constants.jmxTypes + " " + privilegeCondition + " ORDER BY RESOURCENAME";
/*      */           }
/*      */           
/* 3065 */           set = AMConnectionPool.executeQueryStmt(query);
/* 3066 */           rows = new ArrayList();
/* 3067 */           while (set.next())
/*      */           {
/* 3069 */             Properties dataProps = new Properties();
/* 3070 */             if (Constants.noSqlList.contains(set.getString(4)))
/*      */             {
/* 3072 */               dataProps.setProperty("label", set.getString(3));
/*      */             }
/*      */             else
/*      */             {
/* 3076 */               dataProps.setProperty("label", set.getString(2));
/*      */             }
/* 3078 */             dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 3079 */             rows.add(dataProps);
/*      */           }
/* 3081 */           AMConnectionPool.closeStatement(set);
/* 3082 */           ((AMActionForm)form).setJrelist(rows);
/* 3083 */           AMLog.debug("jrelist = " + rows);
/* 3084 */           request.setAttribute("jrelist", rows);
/*      */           
/*      */ 
/* 3087 */           if (isUserResourceEnabled) {
/* 3088 */             query = "select AM_ManagedObject.RESOURCEID,RESOURCENAME from AM_ManagedObject,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID= " + loginUserid + " and TYPE IN " + Constants.serverTypes + "  ORDER BY RESOURCENAME";
/*      */           } else {
/* 3090 */             query = "select RESOURCEID,RESOURCENAME from AM_ManagedObject where TYPE IN " + Constants.serverTypes + " " + privilegeCondition + " ORDER BY RESOURCENAME";
/*      */           }
/*      */           
/* 3093 */           set = AMConnectionPool.executeQueryStmt(query);
/* 3094 */           rows = new ArrayList();
/* 3095 */           while (set.next())
/*      */           {
/* 3097 */             Properties dataProps = new Properties();
/* 3098 */             dataProps.setProperty("label", set.getString(2));
/* 3099 */             dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 3100 */             rows.add(dataProps);
/*      */           }
/* 3102 */           AMConnectionPool.closeStatement(set);
/* 3103 */           if (isUserResourceEnabled) {
/* 3104 */             query = "select AM_ManagedObject.RESOURCEID,RESOURCENAME from AM_ManagedObject,AM_USERRESOURCESTABLE where AM_ManagedObject.RESOURCEID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and TYPE IN " + Constants.nosqlJmxTypes + " " + privilegeCondition + " ORDER BY RESOURCENAME";
/*      */           } else {
/* 3106 */             query = "select RESOURCEID,RESOURCENAME from AM_ManagedObject where TYPE IN " + Constants.nosqlJmxTypes + " " + privilegeCondition + " ORDER BY RESOURCENAME";
/*      */           }
/*      */           
/* 3109 */           set = AMConnectionPool.executeQueryStmt(query);
/* 3110 */           while (set.next())
/*      */           {
/* 3112 */             Properties dataProps = new Properties();
/* 3113 */             dataProps.setProperty("label", set.getString(2));
/* 3114 */             dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 3115 */             rows.add(dataProps);
/*      */           }
/* 3117 */           AMConnectionPool.closeStatement(set);
/* 3118 */           ((AMActionForm)form).setHostlist(rows);
/* 3119 */           request.setAttribute("hostlist", rows);
/*      */         }
/*      */         else
/*      */         {
/* 3123 */           String query1 = null;
/* 3124 */           if (isUserResourceEnabled) {
/* 3125 */             query1 = "SELECT AM_ManagedObject.RESOURCEID,RESOURCENAME from AM_ManagedObject,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and type='EC2Instance' ORDER BY RESOURCENAME";
/*      */           } else {
/* 3127 */             query1 = "SELECT RESOURCEID,RESOURCENAME from AM_ManagedObject where type='EC2Instance' " + privilegeCondition + " ORDER BY RESOURCENAME";
/*      */           }
/*      */           
/* 3130 */           rows = new ArrayList();
/* 3131 */           String typeId = "";
/* 3132 */           ResultSet rst = null;
/* 3133 */           ResultSet rsForTypeId = null;
/*      */           try {
/* 3135 */             String typeIdQuery = null;
/* 3136 */             typeIdQuery = "select TYPEID from AM_MONITOR_TYPES where TYPENAME ='EC2Instance' " + privilegeCondition + "";
/* 3137 */             rsForTypeId = AMConnectionPool.executeQueryStmt(typeIdQuery);
/* 3138 */             if (rsForTypeId.next()) {
/* 3139 */               typeId = rsForTypeId.getString("TYPEID");
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/* 3143 */             e.printStackTrace();
/*      */           }
/*      */           finally {}
/*      */           
/*      */ 
/*      */           try
/*      */           {
/* 3150 */             set = AMConnectionPool.executeQueryStmt(query1);
/* 3151 */             while (set.next())
/*      */             {
/* 3153 */               String queryName = "select CONFVALUE from AM_CONFIGURATION_INFO where ATTRIBUTEID=9852 and LATEST=1 and RESOURCEID=" + set.getInt(1);
/* 3154 */               rst = AMConnectionPool.executeQueryStmt(queryName);
/* 3155 */               String label = null;
/* 3156 */               Properties dataProps = new Properties();
/* 3157 */               if (rst != null) {
/* 3158 */                 while (rst.next()) {
/* 3159 */                   label = rst.getString(1) + "(" + set.getString(2) + ")";
/* 3160 */                   dataProps.setProperty("label", label);
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 3165 */               label = set.getString(2) + "(" + set.getString(2) + ")";
/* 3166 */               dataProps.setProperty("label", set.getString(2));
/*      */               
/*      */ 
/* 3169 */               dataProps.setProperty("value", set.getString(1));
/* 3170 */               rows.add(dataProps);
/*      */             }
/*      */             
/* 3173 */             ((AMActionForm)form).setEc2Instance(rows);
/* 3174 */             request.setAttribute("ec2Instance", rows);
/*      */           }
/*      */           catch (Exception exp) {
/* 3177 */             AMLog.debug("Amzon instance resources : " + exp);
/* 3178 */             exp.printStackTrace();
/*      */           } finally {
/* 3180 */             AMConnectionPool.closeStatement(set);
/*      */           }
/*      */         }
/*      */         
/* 3184 */         if (actionID != null)
/*      */         {
/* 3186 */           query = "select AM_ACTIONPROFILE.NAME,AM_JREACTIONS.DELAY,AM_JREACTIONS.COUNT,AM_JREACTIONS.EMAIL_ACTION_ID,AM_JREACTIONS.TYPE,AM_JREACTIONS.TARGET_RESID,AM_ACTIONPROFILE.TYPE from AM_JREACTIONS,AM_ACTIONPROFILE where AM_JREACTIONS.ID=AM_ACTIONPROFILE.ID  and AM_JREACTIONS.ID=" + actionID;
/* 3187 */           rows = this.mo.getRows(query);
/* 3188 */           if (rows != null)
/*      */           {
/* 3190 */             ArrayList row = (ArrayList)rows.get(0);
/* 3191 */             ((AMActionForm)form).setTdcount(Integer.parseInt((String)row.get(2)));
/* 3192 */             ((AMActionForm)form).setTddelay(Integer.parseInt((String)row.get(1)));
/* 3193 */             ((AMActionForm)form).setDisplayname((String)row.get(0));
/* 3194 */             ((AMActionForm)form).setLogConfig((String)row.get(4));
/* 3195 */             ((AMActionForm)form).setSendmail((String)row.get(3));
/* 3196 */             ((AMActionForm)form).setSelectedMG((String)row.get(5));
/* 3197 */             ((AMActionForm)form).setSelectedhost((String)row.get(5));
/* 3198 */             ((AMActionForm)form).setSelectedjre((String)row.get(5));
/* 3199 */             ((AMActionForm)form).setId(Integer.parseInt(actionID));
/* 3200 */             amform.setMethod("editThreadDumpAction");
/* 3201 */             String type = (String)row.get(6);
/* 3202 */             if ((type != null) && (type.equals("8")))
/*      */             {
/* 3204 */               type = "HeapDump";
/* 3205 */             } else if ((type != null) && (type.equals("9")))
/*      */             {
/* 3207 */               type = "PerformGC";
/* 3208 */             } else if ((type != null) && (type.equals("14")))
/*      */             {
/* 3210 */               type = "Start";
/* 3211 */             } else if ((type != null) && (type.equals("15")))
/*      */             {
/* 3213 */               type = "Stop";
/* 3214 */             } else if ((type != null) && (type.equals("16")))
/*      */             {
/* 3216 */               type = "Restart";
/*      */             }
/*      */             else
/*      */             {
/* 3220 */               type = "ThreadDump";
/*      */             }
/* 3222 */             ((AMActionForm)form).setJtaskMethod(type);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 3228 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/* 3231 */         AMConnectionPool.closeStatement(set);
/*      */       }
/* 3233 */       String ext = request.getParameter("ext");
/* 3234 */       if (ext != null)
/*      */       {
/* 3236 */         return new ActionForward("/jsp/ThreadDumpActionForm.jsp?haid=" + request.getParameter("haid") + "&global=" + request.getParameter("global") + "&monitorType=" + monitorType);
/*      */       }
/* 3238 */       if (monitorType.equalsIgnoreCase("jre")) {
/* 3239 */         return new ActionForward("/showTile.do?TileName=.ThreadDumpActions&monitorType=" + monitorType);
/*      */       }
/*      */       
/* 3242 */       return new ActionForward("/showTile.do?TileName=.AmazonInstanceAction&monitorType=" + monitorType);
/*      */     }
/*      */     
/* 3245 */     return new ActionForward("/webclient/common/jsp/home.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward createThreadDumpAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3253 */     if (!isTokenValid(request)) {
/* 3254 */       return new ActionForward("/adminAction.do?method=showActionProfiles");
/*      */     }
/* 3256 */     resetToken(request);
/*      */     
/* 3258 */     String returnpath = request.getParameter("returnpath");
/* 3259 */     String popupParam = request.getParameter("popup");
/* 3260 */     boolean popup = (popupParam != null) && (popupParam.equals("true"));
/* 3261 */     ActionMessages messages = new ActionMessages();
/* 3262 */     AMActionForm am = (AMActionForm)form;
/* 3263 */     String displayname = am.getDisplayname();
/* 3264 */     int targetResid = 0;
/* 3265 */     String actionName = FormatUtil.getString("am.javaruntime.action.performgc");
/* 3266 */     request.setAttribute("selected", "am.javaruntime.javaaction.performgc");
/*      */     try {
/* 3268 */       ((AMActionForm)form).setLogConfig(am.getLogConfig());
/* 3269 */       if (am.getLogConfig().equals("2")) {
/* 3270 */         targetResid = Integer.parseInt(am.getSelectedMG());
/* 3271 */       } else if (am.getLogConfig().equals("3")) {
/* 3272 */         targetResid = Integer.parseInt(am.getSelectedhost());
/* 3273 */       } else if (am.getLogConfig().equals("4")) {
/* 3274 */         targetResid = Integer.parseInt(am.getSelectedjre());
/* 3275 */       } else if (am.getLogConfig().equals("6")) {
/* 3276 */         targetResid = Integer.parseInt(am.getSelectedMG());
/* 3277 */       } else if (am.getLogConfig().equals("7")) {
/* 3278 */         targetResid = Integer.parseInt(am.getSelectedjre());
/*      */       }
/*      */       
/* 3281 */       String timetogenerate = am.getJtaskMethod();
/* 3282 */       int type = 9;
/* 3283 */       if ((timetogenerate != null) && (timetogenerate.equalsIgnoreCase("ThreadDump")))
/*      */       {
/* 3285 */         type = 7;
/* 3286 */         actionName = FormatUtil.getString("am.javaruntime.action.threaddump");
/* 3287 */         request.setAttribute("selected", "am.javaruntime.javaaction.threaddump");
/* 3288 */       } else if ((timetogenerate != null) && (timetogenerate.equalsIgnoreCase("HeapDump"))) {
/* 3289 */         type = 8;
/* 3290 */         actionName = FormatUtil.getString("am.javaruntime.action.heapdump");
/* 3291 */         request.setAttribute("selected", "am.javaruntime.javaaction.heapdump");
/* 3292 */       } else if ((timetogenerate != null) && (timetogenerate.equalsIgnoreCase("Start")))
/*      */       {
/* 3294 */         actionName = "Start";
/* 3295 */         type = 14;
/*      */       }
/* 3297 */       else if ((timetogenerate != null) && (timetogenerate.equalsIgnoreCase("Stop")))
/*      */       {
/* 3299 */         actionName = "Stop";
/* 3300 */         type = 15;
/*      */       }
/* 3302 */       else if ((timetogenerate != null) && (timetogenerate.equalsIgnoreCase("Restart")))
/*      */       {
/* 3304 */         type = 16;
/* 3305 */         actionName = "Restart";
/*      */       }
/*      */       
/* 3308 */       if (DBQueryUtil.getDBType().equals("mssql")) {
/* 3309 */         displayname = displayname.replaceAll("'", "''");
/*      */       } else {
/* 3311 */         displayname = displayname.replaceAll("'", "\\\\'");
/*      */       }
/*      */       
/* 3314 */       String checkquery = "select * from AM_ACTIONPROFILE where NAME='" + displayname + "'";
/* 3315 */       ArrayList list = this.mo.getRows(checkquery);
/* 3316 */       if (list.size() == 0)
/*      */       {
/* 3318 */         String insertquery = "insert into AM_ACTIONPROFILE (ID,NAME,TYPE) values(" + DBQueryUtil.getIncrementedID("ID", "AM_ACTIONPROFILE") + ",'" + displayname + "'," + type + ") ";
/* 3319 */         this.mo.executeUpdateStmt(insertquery);
/* 3320 */         ArrayList result = this.mo.getRows("select max(ID) from AM_ACTIONPROFILE");
/* 3321 */         if (result.size() > 0)
/*      */         {
/* 3323 */           String id = (String)((ArrayList)result.get(0)).get(0);
/* 3324 */           if (id == null) {
/* 3325 */             id = "1";
/*      */           }
/* 3327 */           PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("insert into  AM_JREACTIONS (ID,DELAY,COUNT,EMAIL_ACTION_ID,TYPE,TARGET_RESID) values (?,?,?,?,?,?)");
/*      */           try {
/* 3329 */             ps.setInt(1, Integer.valueOf(id).intValue());
/* 3330 */             ps.setInt(2, am.getTddelay());
/* 3331 */             ps.setInt(3, am.getTdcount());
/* 3332 */             ps.setInt(4, Integer.parseInt(am.getSendmail()));
/* 3333 */             ps.setInt(5, Integer.parseInt(am.getLogConfig()));
/* 3334 */             ps.setInt(6, targetResid);
/* 3335 */             ps.executeUpdate();
/* 3336 */             DelegatedUserRoleUtil.addEntryToConfigUserTable(request, Integer.parseInt(id), 2);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/* 3344 */               if (ps != null) {
/* 3345 */                 ps.close();
/*      */               }
/*      */             }
/*      */             catch (Exception ex) {
/* 3349 */               ex.printStackTrace();
/*      */             }
/*      */             
/* 3352 */             if (type == 7) {
/*      */               break label861;
/*      */             }
/*      */           }
/*      */           catch (Exception exp)
/*      */           {
/* 3340 */             exp.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 3344 */               if (ps != null) {
/* 3345 */                 ps.close();
/*      */               }
/*      */             }
/*      */             catch (Exception ex) {
/* 3349 */               ex.printStackTrace();
/*      */             }
/*      */           }
/* 3352 */           if ((type == 8) || (type == 9)) {
/* 3353 */             label861: messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.javaruntime.action.create.success", actionName));
/*      */           } else {
/* 3355 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.amazon.ec2Instanceactions.createaction.text", displayname));
/*      */           }
/* 3357 */           saveMessages(request, messages);
/* 3358 */           if (popup)
/*      */           {
/* 3360 */             associateActions(request);
/* 3361 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("emailaction.createandassociate.success"));
/* 3362 */             return new ActionForward("/jsp/ThresholdCreationForwarder.jsp" + getWizString(request));
/*      */           }
/* 3364 */           if (returnpath != null)
/*      */           {
/* 3366 */             return new ActionForward(returnpath);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 3373 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.javaruntime.action.alreadyexists", request.getParameter("displayname")));
/* 3374 */         saveMessages(request, messages);
/* 3375 */         if (popup) {
/* 3376 */           return new ActionForward("/jsp/ThreadDumpActionForm.jsp");
/*      */         }
/* 3378 */         return new ActionForward("/JavaRuntime.do?method=showThreadDumpAction");
/*      */       }
/*      */     } catch (Exception e) {
/* 3381 */       e.printStackTrace();
/*      */     }
/* 3383 */     if (returnpath != null) {
/* 3384 */       return new ActionForward(returnpath);
/*      */     }
/* 3386 */     return new ActionForward("/adminAction.do?method=showActionProfiles");
/*      */   }
/*      */   
/*      */   public ActionForward deleteThreadDumpAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 3392 */     String[] applications = request.getParameterValues("threaddumpcheckbox");
/* 3393 */     if (applications != null)
/*      */     {
/* 3395 */       for (int i = 0; i < applications.length; i++)
/*      */       {
/* 3397 */         FaultUtil.deleteAction(applications[i]);
/* 3398 */         DelegatedUserRoleUtil.deleteEntryFromConfigUserTable(Integer.parseInt(applications[i]), 2);
/*      */       }
/*      */     }
/* 3401 */     ActionMessages messages = new ActionMessages();
/* 3402 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.javaruntime.action.deletetd.success"));
/* 3403 */     saveMessages(request, messages);
/* 3404 */     request.setAttribute("selected", "am.javaruntime.javaaction.threaddump");
/* 3405 */     return new ActionForward("/adminAction.do?method=showActionProfiles");
/*      */   }
/*      */   
/*      */   public ActionForward deleteHeapDumpAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 3411 */     String[] applications = request.getParameterValues("heapdumpcheckbox");
/* 3412 */     if (applications != null)
/*      */     {
/* 3414 */       for (int i = 0; i < applications.length; i++)
/*      */       {
/* 3416 */         FaultUtil.deleteAction(applications[i]);
/* 3417 */         DelegatedUserRoleUtil.deleteEntryFromConfigUserTable(Integer.parseInt(applications[i]), 2);
/*      */       }
/*      */     }
/* 3420 */     ActionMessages messages = new ActionMessages();
/* 3421 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.javaruntime.action.deletehd.success"));
/* 3422 */     saveMessages(request, messages);
/* 3423 */     request.setAttribute("selected", "am.javaruntime.javaaction.heapdump");
/* 3424 */     return new ActionForward("/adminAction.do?method=showActionProfiles");
/*      */   }
/*      */   
/*      */   public ActionForward deletePerformGCAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 3430 */     String[] applications = request.getParameterValues("performgccheckbox");
/* 3431 */     if (applications != null)
/*      */     {
/* 3433 */       for (int i = 0; i < applications.length; i++) {
/* 3434 */         FaultUtil.deleteAction(applications[i]);
/* 3435 */         DelegatedUserRoleUtil.deleteEntryFromConfigUserTable(Integer.parseInt(applications[i]), 2);
/*      */       }
/*      */     }
/* 3438 */     ActionMessages messages = new ActionMessages();
/* 3439 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.javaruntime.action.deletegc.success"));
/* 3440 */     saveMessages(request, messages);
/* 3441 */     request.setAttribute("selected", "am.javaruntime.javaaction.performgc");
/* 3442 */     return new ActionForward("/adminAction.do?method=showActionProfiles");
/*      */   }
/*      */   
/*      */   public ActionForward editThreadDumpAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 3448 */     ActionMessages messages = new ActionMessages();
/* 3449 */     AMActionForm am = (AMActionForm)form;
/* 3450 */     String displayname = am.getDisplayname();
/* 3451 */     String update = request.getParameter("update");
/* 3452 */     String monitorType = "jre";
/* 3453 */     int targetResid = 0;
/* 3454 */     String actionName = FormatUtil.getString("am.javaruntime.action.performgc");
/* 3455 */     request.setAttribute("selected", "am.javaruntime.javaaction.performgc");
/* 3456 */     if (DBQueryUtil.getDBType().equals("mssql")) {
/* 3457 */       displayname = displayname.replaceAll("'", "''");
/*      */     }
/*      */     else {
/* 3460 */       displayname = displayname.replaceAll("'", "\\\\'");
/*      */     }
/*      */     
/* 3463 */     ((AMActionForm)form).setLogConfig(am.getLogConfig());
/* 3464 */     if (am.getLogConfig().equals("2")) {
/* 3465 */       targetResid = Integer.parseInt(am.getSelectedMG());
/* 3466 */     } else if (am.getLogConfig().equals("3")) {
/* 3467 */       targetResid = Integer.parseInt(am.getSelectedhost());
/* 3468 */     } else if (am.getLogConfig().equals("4")) {
/* 3469 */       targetResid = Integer.parseInt(am.getSelectedjre());
/* 3470 */     } else if (am.getLogConfig().equals("6")) {
/* 3471 */       targetResid = Integer.parseInt(am.getSelectedMG());
/* 3472 */     } else if (am.getLogConfig().equals("7")) {
/* 3473 */       targetResid = Integer.parseInt(am.getSelectedjre());
/*      */     }
/* 3475 */     String timetogenerate = am.getJtaskMethod();
/* 3476 */     int type = 9;
/* 3477 */     if ((timetogenerate != null) && (timetogenerate.equalsIgnoreCase("ThreadDump"))) {
/* 3478 */       type = 7;
/* 3479 */       actionName = FormatUtil.getString("am.javaruntime.action.threaddump");
/* 3480 */       request.setAttribute("selected", "am.javaruntime.javaaction.threaddump");
/* 3481 */     } else if ((timetogenerate != null) && (timetogenerate.equalsIgnoreCase("HeapDump"))) {
/* 3482 */       type = 8;
/* 3483 */       actionName = FormatUtil.getString("am.javaruntime.action.heapdump");
/* 3484 */       request.setAttribute("selected", "am.javaruntime.javaaction.heapdump");
/* 3485 */     } else if ((timetogenerate != null) && (timetogenerate.equalsIgnoreCase("Start"))) {
/* 3486 */       type = 14;
/* 3487 */       actionName = "Start";
/* 3488 */       monitorType = "amazon";
/* 3489 */     } else if ((timetogenerate != null) && (timetogenerate.equalsIgnoreCase("Stop"))) {
/* 3490 */       type = 15;
/* 3491 */       actionName = "Stop";
/* 3492 */       monitorType = "amazon";
/* 3493 */     } else if ((timetogenerate != null) && (timetogenerate.equalsIgnoreCase("Restart")))
/*      */     {
/* 3495 */       type = 16;
/* 3496 */       actionName = "Restart";
/* 3497 */       monitorType = "amazon";
/*      */     }
/*      */     
/* 3500 */     String checkquery = "select * from AM_ACTIONPROFILE where NAME='" + displayname + "' and ID!=" + am.getId();
/* 3501 */     ArrayList list = this.mo.getRows(checkquery);
/* 3502 */     if (list.size() == 0) {
/* 3503 */       String updateactionquery = "update AM_ACTIONPROFILE set NAME='" + displayname + "',TYPE=" + type + " where ID = " + am.getId();
/* 3504 */       this.mo.executeUpdateStmt(updateactionquery);
/*      */       
/* 3506 */       PreparedStatement ps = null;
/*      */       try {
/* 3508 */         ps = AMConnectionPool.getConnection().prepareStatement("update AM_JREACTIONS set DELAY=?,COUNT=?,EMAIL_ACTION_ID =?,TYPE=?,TARGET_RESID=? where ID =?");
/*      */         try {
/* 3510 */           ps.setInt(1, am.getTddelay());
/* 3511 */           ps.setInt(2, am.getTdcount());
/* 3512 */           ps.setInt(3, Integer.parseInt(am.getSendmail()));
/* 3513 */           ps.setInt(4, Integer.parseInt(am.getLogConfig()));
/* 3514 */           ps.setInt(5, targetResid);
/* 3515 */           ps.setInt(6, am.getId());
/* 3516 */           ps.executeUpdate();
/*      */         }
/*      */         catch (Exception exp) {
/* 3519 */           exp.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 3527 */           if (ps != null) {
/* 3528 */             ps.close();
/*      */           }
/*      */         }
/*      */         catch (Exception ex) {
/* 3532 */           ex.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/* 3536 */         if (!monitorType.equalsIgnoreCase("jre")) {
/*      */           break label787;
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 3523 */         exp.printStackTrace();
/*      */       }
/*      */       finally {
/*      */         try {
/* 3527 */           if (ps != null) {
/* 3528 */             ps.close();
/*      */           }
/*      */         }
/*      */         catch (Exception ex) {
/* 3532 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3537 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.javaruntime.action.update.success", actionName));
/*      */       break label807;
/* 3539 */       label787: messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.amazon.ec2Instanceactions.updateaction.text", displayname));
/*      */       label807:
/* 3541 */       saveMessages(request, messages);
/*      */     }
/*      */     else {
/* 3544 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.javaruntime.action.alreadyexists", request.getParameter("displayname")));
/* 3545 */       saveMessages(request, messages);
/* 3546 */       if (update == null) {
/* 3547 */         return new ActionForward("/JavaRuntime.do?method=showThreadDumpAction&save='true'");
/*      */       }
/* 3549 */       return new ActionForward("/showActionProfiles.do?method=getActionDetails&monitorType=" + monitorType + "&actionid=" + am.getId());
/*      */     }
/*      */     
/*      */ 
/* 3553 */     if (update == null) {
/* 3554 */       return new ActionForward("/adminAction.do?method=showActionProfiles");
/*      */     }
/* 3556 */     return new ActionForward("/showActionProfiles.do?method=getActionDetails&monitorType=" + monitorType + "&actionid=" + am.getId());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getWizString(HttpServletRequest request)
/*      */   {
/* 3564 */     String wiz = request.getParameter("wiz");
/* 3565 */     if (wiz != null)
/*      */     {
/* 3567 */       wiz = "?wiz=true";
/*      */     }
/*      */     else
/*      */     {
/* 3571 */       wiz = "";
/*      */     }
/* 3573 */     return wiz;
/*      */   }
/*      */   
/*      */   private void associateActions(HttpServletRequest request) throws Exception
/*      */   {
/* 3578 */     String resourceid = request.getParameter("resourceid");
/* 3579 */     String attributeid = request.getParameter("attributeid");
/* 3580 */     String severity = request.getParameter("severity");
/* 3581 */     String actionID = "";
/* 3582 */     ResultSet rs = null;
/*      */     try {
/* 3584 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3585 */       rs = AMConnectionPool.executeQueryStmt("select MAX(ID) from AM_ACTIONPROFILE");
/* 3586 */       if (rs.next())
/*      */       {
/* 3588 */         actionID = "" + rs.getInt(1);
/*      */       }
/* 3590 */       AMConnectionPool.closeStatement(rs);
/* 3591 */       if (!actionID.equals(""))
/*      */       {
/* 3593 */         String query = "insert into AM_ATTRIBUTEACTIONMAPPER (ID, ATTRIBUTE, SEVERITY, ACTIONID) values (" + resourceid + ",'" + attributeid + "'," + severity + "," + actionID + ")";
/* 3594 */         AMConnectionPool.executeUpdateStmt(query);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 3598 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 3601 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward deleteEC2instanceAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 3606 */     String[] applications = request.getParameterValues("ec2instancecheckbox");
/* 3607 */     if (applications != null)
/*      */     {
/* 3609 */       for (int i = 0; i < applications.length; i++)
/*      */       {
/* 3611 */         FaultUtil.deleteAction(applications[i]);
/*      */       }
/*      */     }
/* 3614 */     ActionMessages messages = new ActionMessages();
/* 3615 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.amazon.ec2Instanceactions.deleteaction.text"));
/* 3616 */     saveMessages(request, messages);
/* 3617 */     return new ActionForward("/adminAction.do?method=showActionProfiles");
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\jdk15\JDK15Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */