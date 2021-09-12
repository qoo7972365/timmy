/*     */ package com.adventnet.appmanager.ingres.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*     */ import com.adventnet.appmanager.json.JSONUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.querymonitor.QueryUtil;
/*     */ import com.adventnet.appmanager.servlets.APIServlet;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URLEncoder;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public class IngresAction
/*     */   extends DispatchAction
/*     */ {
/*     */   private ManagedApplication mo;
/*     */   
/*  57 */   public IngresAction() { this.mo = new ManagedApplication(); }
/*     */   
/*     */   public ActionForward triggerIngersProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  60 */     response.setContentType("text/html; charset=UTF-8");
/*  61 */     Connection connection = null;
/*  62 */     String resourceid = request.getParameter("resourceid");
/*  63 */     Statement stmt = null;
/*  64 */     ResultSet rs = null;
/*  65 */     long collectionTime = 0L;
/*  66 */     String query = null;
/*  67 */     String dspname = null;
/*  68 */     SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yy, HH:mm:ss z");
/*  69 */     String apiKey = request.getParameter("apikey");
/*  70 */     String args = request.getParameter("args");
/*  71 */     boolean fromappman = true;
/*  72 */     PrintWriter out = response.getWriter();
/*  73 */     StringBuilder temp = new StringBuilder();
/*  74 */     HashMap resNames = new HashMap();
/*  75 */     String resname = null;
/*  76 */     String host = null;
/*  77 */     String referenceID = null;
/*  78 */     String apiKeyargs = null;
/*  79 */     Hashtable mysqlParams; if (args != null) {
/*  80 */       fromappman = false;
/*  81 */       mysqlParams = (Hashtable)JSONUtil.getObject(args);
/*  82 */       host = (String)mysqlParams.get("ip");
/*  83 */       apiKeyargs = (String)mysqlParams.get("apikey");
/*  84 */       referenceID = (String)mysqlParams.get("uid");
/*     */     }
/*  86 */     if ((request.getRemoteUser() != null) || (validateAPIKey(apiKey)) || (validateAPIKey(apiKeyargs))) {
/*     */       try {
/*  88 */         if ((resourceid != null) && (!resourceid.equals("null"))) {
/*  89 */           query = "select RESOURCEID,RESOURCENAME from AM_ManagedObject where TYPE='Ingres' and RESOURCEID=" + resourceid;
/*  90 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  91 */           if (rs.next()) {
/*  92 */             resNames.put(rs.getString("RESOURCEID"), rs.getString("RESOURCENAME"));
/*     */           }
/*     */         }
/*     */         
/*  96 */         if (host != null) {
/*  97 */           query = "select RESOURCEID,RESOURCENAME from AM_ManagedObject where resourcename='" + host + "' and TYPE='Ingres'";
/*  98 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  99 */           if (rs.next()) {
/* 100 */             resNames.put(rs.getString("RESOURCEID"), rs.getString("RESOURCENAME"));
/*     */           }
/*     */         }
/* 103 */         if (resNames.size() == 0) {
/* 104 */           temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 105 */           temp.append("<tr><td><span class='bodytext'> " + FormatUtil.getString("am.webclient.ingres.processlist.nosource") + " </span></td></tr></table>");
/* 106 */           out.write(temp.toString());
/* 107 */           out.flush();
/* 108 */           return null;
/*     */         }
/*     */         
/* 111 */         Properties dbDetails = getIngeresDbdetailsforIp(host, "Ingres");
/* 112 */         status = QueryUtil.getConnection(dbDetails);
/*     */         
/* 114 */         if (status.getProperty("authentication").equals("passed")) {
/* 115 */           connection = (Connection)status.get("connection");
/*     */         } else {
/* 117 */           AMLog.debug("Error while connecting Ingres--->" + status.getProperty("error"));
/* 118 */           temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 119 */           temp.append("<tr><td><span class='bodytext'> " + FormatUtil.getString(new StringBuilder().append("Problem in collecting process list : ").append(status.getProperty("error")).toString()) + " </span></td></tr></table>");
/* 120 */           out.write(temp.toString());
/* 121 */           out.flush();
/* 122 */           return null;
/*     */         }
/*     */         
/* 125 */         if (connection != null) {
/* 126 */           Set set = resNames.entrySet();
/* 127 */           Iterator i = set.iterator();
/* 128 */           while (i.hasNext()) {
/* 129 */             Map.Entry me = (Map.Entry)i.next();
/* 130 */             resourceid = (String)me.getKey();
/* 131 */             resname = (String)me.getValue();
/* 132 */             ArrayList list = new ArrayList();
/* 133 */             query = "callproc modified_show_sessions";
/* 134 */             CallableStatement cs = connection.prepareCall(query);
/* 135 */             stmt = connection.createStatement();
/* 136 */             collectionTime = System.currentTimeMillis();
/* 137 */             Date logDate = new Date(collectionTime);
/*     */             try {
/* 139 */               rs = cs.executeQuery();
/* 140 */               ResultSetMetaData rsmd = rs.getMetaData();
/* 141 */               while (rs.next()) {
/* 142 */                 ArrayList values = new ArrayList();
/* 143 */                 int ccount = rsmd.getColumnCount();
/* 144 */                 for (int j = 1; j <= ccount; j++) {
/* 145 */                   values.add(rs.getString(j));
/*     */                 }
/* 147 */                 list.add(values);
/*     */               }
/*     */             } catch (SQLException e) {
/* 150 */               e.printStackTrace();
/*     */             }
/* 152 */             Collections.sort(list, new timeComparatorForIngres(null));
/* 153 */             dspname = FormatUtil.getString("am.webclient.mysql.processlist.title", new String[] { resname }) + " , " + sdf.format(logDate);
/* 154 */             request.setAttribute("displayname", dspname);
/* 155 */             request.setAttribute("processList", list);
/* 156 */             genIngresProcessList(resourceid, collectionTime, list, resname, referenceID, fromappman);
/*     */           }
/*     */         }
/*     */       } catch (Exception e) { Properties status;
/* 160 */         e.printStackTrace();
/* 161 */         AMLog.debug("Ingres Action : Problem in collecting process list :" + e.getMessage());
/* 162 */         if (!fromappman) {
/* 163 */           temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 164 */           temp.append("<tr><td><span class='bodytext'> " + FormatUtil.getString(new StringBuilder().append("Problem in collecting process list : ").append(e.getMessage()).toString()) + " </span></td></tr></table>");
/* 165 */           out.write(temp.toString());
/* 166 */           out.flush();
/* 167 */           return null;
/*     */         }
/* 169 */         request.setAttribute("error", "Problem in collecting process list : " + e.getMessage());
/* 170 */         return mapping.findForward("IngresProcessList");
/*     */       } finally {
/* 172 */         if (rs != null) {
/*     */           try {
/* 174 */             rs.close();
/*     */           } catch (Exception ce) {
/* 176 */             ce.printStackTrace();
/*     */           }
/*     */         }
/* 179 */         if (stmt != null) {
/*     */           try {
/* 181 */             stmt.close();
/*     */           } catch (Exception ce) {
/* 183 */             ce.printStackTrace();
/*     */           }
/*     */         }
/* 186 */         if (connection != null) {
/*     */           try {
/* 188 */             connection.close();
/*     */           } catch (Exception ce) {
/* 190 */             ce.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 195 */     AMLog.debug("Ingres Action : Wrong API Key to trigger process list");
/* 196 */     if (!fromappman) {
/* 197 */       temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 198 */       temp.append("<tr><td><span class='bodytext'> " + FormatUtil.getString("am.javaruntime.wrongapikey") + " </span></td></tr></table>");
/* 199 */       out.write(temp.toString());
/* 200 */       out.flush();
/* 201 */       return null;
/*     */     }
/* 203 */     request.setAttribute("error", FormatUtil.getString("am.javaruntime.wrongapikey"));
/* 204 */     return mapping.findForward("IngresProcessList");
/*     */     
/* 206 */     if (!fromappman) {
/* 207 */       temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 208 */       temp.append("<span class='bodytext'>");
/* 209 */       if (request.getParameter("synUrls") != null) {
/* 210 */         temp.append("Message from Managed Server ").append(EnterpriseUtil.getManagedServerIndex()).append(" : ");
/*     */       }
/* 212 */       temp.append(FormatUtil.getString("am.webclient.mysql.processlist.success")).append(" </span></td></tr></table>");
/* 213 */       out.write(temp.toString());
/* 214 */       out.flush();
/* 215 */       return null;
/*     */     }
/* 217 */     return mapping.findForward("IngresProcessList");
/*     */   }
/*     */   
/*     */   private static Properties getIngeresDbdetailsforIp(String host, String type) throws Exception {
/* 221 */     Properties props = new Properties();
/* 222 */     ResultSet rs = null;
/* 223 */     String query = null;
/* 224 */     String typeId = null;
/*     */     try {
/* 226 */       query = "select TYPEID from AM_MONITOR_TYPES where TYPENAME='" + type + "'";
/* 227 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 228 */       if (rs.next()) {
/* 229 */         typeId = rs.getString("TYPEID");
/*     */       }
/*     */     } finally {
/* 232 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     try {
/* 235 */       query = "select HostName,Port,UserName," + DBQueryUtil.decode("Password") + " as password ,DBname,arg.RESOURCEID from AM_ARGS_" + typeId + " arg,AM_ManagedObject mo where mo.RESOURCEID=arg.RESOURCEID and HostName='" + host + "'";
/* 236 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 237 */       while (rs.next()) {
/* 238 */         props.setProperty("Host Name", rs.getString("HostName"));
/* 239 */         props.setProperty("Port", rs.getString("Port"));
/* 240 */         props.setProperty("Username", rs.getString("UserName"));
/* 241 */         props.setProperty("Password", rs.getString("password"));
/* 242 */         props.setProperty("Database Name", rs.getString("DBname"));
/* 243 */         props.setProperty("resourceId", rs.getString("arg.RESOURCEID"));
/* 244 */         props.setProperty("Database", "Ingres");
/*     */       }
/*     */     } finally {
/* 247 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 249 */     return props;
/*     */   }
/*     */   
/*     */   public ActionForward displayIngresProcessList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 253 */     response.setContentType("text/html; charset=UTF-8");
/*     */     try {
/* 255 */       SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yy, HH:mm:ss z");
/* 256 */       SimpleDateFormat sdfw = new SimpleDateFormat("d-MMM-yyyy-HH-mm-ss-SS", Locale.getDefault());
/* 257 */       AMConnectionPool pool = new AMConnectionPool();
/* 258 */       StringBuilder temp = new StringBuilder();
/* 259 */       String ipaddress = request.getParameter("IP");
/* 260 */       String uid = request.getParameter("UID");
/* 261 */       ArrayList urllist = new ArrayList();
/* 262 */       StringBuilder resourceids = new StringBuilder();
/* 263 */       PrintWriter out = response.getWriter();
/* 264 */       ResultSet rs = null;
/* 265 */       String query = null;
/*     */       try {
/* 267 */         if (request.getParameter("synUrls") != null)
/*     */           try {
/* 269 */             temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 270 */             temp.append("<tr><td>");
/* 271 */             temp.append("<span class='bodytext'> <a class=\"resourcename\" href='https://" + AMCacheHandler.getAMServerPropertiesValue("am.ntlm.hostname") + ":" + AMCacheHandler.getAMServerPropertiesValue("am.ssl.port") + "/DebugInfo.do?method=showProcessList&type=ingres&IP=" + ipaddress + "&UID=" + uid + "&apikey=" + request.getParameter("apikey") + "'target=_blank><font color=black>Process List from Managed Server " + EnterpriseUtil.getManagedServerIndex() + "</font> </a><br> </span>");
/* 272 */             temp.append("</td></tr>");
/* 273 */             temp.append("</table>");
/* 274 */             out.write(temp.toString());
/* 275 */             out.flush();
/* 276 */             return null;
/*     */           } catch (Exception e) {
/* 278 */             e.printStackTrace();
/*     */           }
/*     */         Properties list;
/* 281 */         if ((request.getRemoteUser() != null) || (APIServlet.validateAPIKey(request))) {
/* 282 */           if (ipaddress != null) {
/* 283 */             query = "select RESOURCEID from AM_ManagedObject where resourcename='" + ipaddress + "' and TYPE='Ingres'";
/* 284 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 285 */             while (rs.next()) {
/* 286 */               resourceids.append(rs.getString("RESOURCEID")).append(",");
/*     */             }
/* 288 */             AMConnectionPool.closeStatement(rs);
/*     */           }
/* 290 */           if ((resourceids.length() != 0) && (ipaddress != null) && (uid != null)) {
/* 291 */             query = "select URL,COLLECTIONTIME,RESOURCEID from AM_MONITOR_DEBUG_INFO where TYPE='Ingres Process list' and RESOURCEID IN(" + resourceids.substring(0, resourceids.length() - 1) + ") and REFERENCEID='" + uid + "' order by COLLECTIONTIME desc";
/* 292 */           } else if ((resourceids.length() != 0) && (ipaddress != null)) {
/* 293 */             query = "select URL,COLLECTIONTIME,RESOURCEID from AM_MONITOR_DEBUG_INFO where TYPE='Ingres Process list' and RESOURCEID IN(" + resourceids.substring(0, resourceids.length() - 1) + ") order by COLLECTIONTIME desc";
/* 294 */           } else if (uid != null) {
/* 295 */             query = "select URL,COLLECTIONTIME,RESOURCEID  from AM_MONITOR_DEBUG_INFO   where  TYPE='Ingres Process list' and REFERENCEID='" + uid + "' order by  COLLECTIONTIME desc";
/*     */           }
/* 297 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 298 */           while (rs.next()) {
/* 299 */             list = new Properties();
/* 300 */             list.setProperty("URL", rs.getString("URL"));
/* 301 */             Date logDate = new Date(rs.getLong("COLLECTIONTIME"));
/* 302 */             list.setProperty("DSPNAME", sdfw.format(logDate));
/* 303 */             list.setProperty("DSPNAME_EXT", sdf.format(logDate));
/* 304 */             urllist.add(list);
/*     */           }
/* 306 */           AMConnectionPool.closeStatement(rs);
/* 307 */           request.setAttribute("urllist", urllist);
/*     */         } else {
/* 309 */           temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 310 */           temp.append("<tr><td><span class='bodytext'> " + FormatUtil.getString("am.javaruntime.wrongapikey") + " </span></td></tr></table>");
/* 311 */           out.write(temp.toString());
/* 312 */           out.flush();
/* 313 */           return null;
/*     */         }
/*     */       } catch (Exception e) {
/* 316 */         e.printStackTrace();
/* 317 */         AMLog.debug("Ingres Action : Problem in collecting process list" + e.getMessage());
/*     */       } finally {
/* 319 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     } catch (Exception e) {
/* 322 */       e.printStackTrace();
/* 323 */       AMLog.debug("Ingres Action : Problem in collecting process list" + e.getMessage());
/*     */     }
/* 325 */     return mapping.findForward("IngresShowProcessList");
/*     */   }
/*     */   
/*     */   public static void genIngresProcessList(String resid, long colTime, ArrayList processList, String resname, String referenceID, boolean appman) {
/* 329 */     AMConnectionPool pool = new AMConnectionPool();
/*     */     try {
/* 331 */       String processListURL = genIngresProcessURL(resid, Long.valueOf(colTime), processList, resname, appman);
/* 332 */       AMConnectionPool.executeUpdateStmt("insert into AM_MONITOR_DEBUG_INFO values (" + resid + ",'" + processListURL + "','" + referenceID + "','Ingres Process list'," + colTime + ")");
/*     */     } catch (Exception e) {
/* 334 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static String genIngresProcessURL(String resourceid, Long collectionTime, ArrayList processList, String resourceName, boolean appman) {
/* 339 */     String url = null;
/* 340 */     StringBuilder str = new StringBuilder();
/*     */     try {
/* 342 */       SimpleDateFormat sdfw = new SimpleDateFormat("EEE, dd MMM yy, HH:mm:ss z");
/* 343 */       SimpleDateFormat sdf = new SimpleDateFormat("d-MM-yyyy-HH-mm-ss-SS", Locale.getDefault());
/* 344 */       String fileName = null;
/* 345 */       Date logDate = new Date(collectionTime.longValue());
/* 346 */       String dir = "." + File.separator + "html" + File.separator + "Data" + File.separator + "IngresMonitor" + File.separator + "IngresProcess" + File.separator + resourceid;
/* 347 */       File parentDir = new File(dir);
/* 348 */       if (!parentDir.exists()) {
/* 349 */         parentDir.mkdirs();
/*     */       }
/* 351 */       String port = System.getProperty("webserver.port");
/* 352 */       String hostName = Constants.getAppHostName();
/* 353 */       File contentFile = new File(dir + File.separator + resourceName + "_" + sdf.format(logDate) + ".html");
/* 354 */       PrintStream out = new PrintStream(new FileOutputStream(contentFile));
/*     */       
/* 356 */       str.append("<html><title>Show session List for " + resourceName + "</title><head><meta http-equiv='Content-Type' content='text/html;charset=utf-8' /><title>Ingres Process List</title></head> <body>");
/* 357 */       str.append("<script>function show(sv){document.getElementById(sv).style.display='block';}function hide(hv){document.getElementById(hv).style.display='none';}</script>  <style type='text/css'>.staticlinks2 {font-family: Verdana, Arial, Helvetica, sans-serif;font-weight: bold;font-size: 14px; color: #000000;text-decoration: underline;}.red-table-head{background-color:#ff6e6e; color:#fff; font-family:verdana, arial; font-size:12px; font-weight:bold; border:0px solid #ff0000; line-height:22px; } .red-table-td-white{background-color:#fff;  font-weight:normal; color:#000; font-size:11px; font-family:verdana, arial; padding:3px;} .red-table-td-pink{background-color:#fff2f2; font-weight:normal; color:#000; font-size:11px; font-family:verdana, arial; padding:3px; } .bodytext{font-family: Verdana, Arial, Helvetica, sans-serif;font-weight: bold;font-size: 11px;padding-bottom:4px;}.critical{border-bottom-width:1px;border-bottom-style: solid;border-bottom-color: #DEDEDE;height: 20px;padding-left: 3px;font-family: Verdana,Arial, Helvetica, sans-serif;font-size: 11px;background:red;}.whitegrayborder {border-bottom-width: 1px;border-bottom-style: solid;border-bottom-color: #DEDEDE;height: 20px;padding-left: 3px;font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 11px;color: #000000;}.yellowgrayborder {border-bottom-width: 1px;border-bottom-style: solid;border-bottom-color: #DEDEDE;height: 20px;padding-left: 3px;font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 11px;color: #000000;background: #E3F2FF;}.lrtbdarkborder {border: 1px solid #4E83AF;}</style></meta>");
/*     */       
/* 359 */       str.append("<div id='dthreadtable_ext_" + sdf.format(logDate) + "' >");
/* 360 */       str.append("<table width='100%'><tr><td align='right' style='padding-bottom:5px'><a class='staticlinks2' href=\"javascript:show('dthreadtable_ext_" + sdf.format(logDate) + "_C');hide('dthreadtable_ext_" + sdf.format(logDate) + "');\"></a></td></tr></table>");
/* 361 */       str.append("<table id='threadtable_ext' border='0' class='lrtbdarkborder' width='100%' cellpadding='2' cellspacing='1'>");
/* 362 */       str.append("<tr height='24' style='font-weight:bold;color:white;background:#4E83AF;font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;'>");
/* 363 */       str.append("<td class='tableheading' width='10%'>").append("Session ID").append("</td>");
/* 364 */       str.append("<td class='tableheading' width='10%'>").append("Database").append("</td>");
/* 365 */       str.append("<td class='tableheading' width='10%'>").append("User").append("</td>");
/* 366 */       str.append("<td class='tableheading' width='10%'>").append("Terminal").append("</td>");
/* 367 */       str.append("<td class='tableheading' width='10%'>").append("Elapsed Time(s)").append("</td>");
/* 368 */       str.append("<td class='tableheading' width='35%'>").append("session query").append("</td>");
/*     */       
/* 370 */       boolean nodata = true;
/* 371 */       int size = processList.size();
/* 372 */       if (size < 0) {
/* 373 */         nodata = false;
/*     */       }
/* 375 */       for (int i = 0; i < processList.size(); i++) {
/* 376 */         ArrayList process = (ArrayList)processList.get(i);
/* 377 */         nodata = false;
/* 378 */         String sclass = "yellowgrayborder";
/* 379 */         if (i % 2 == 0) {
/* 380 */           sclass = "whitegrayborder";
/*     */         }
/* 382 */         str.append("<tr class='").append(sclass).append("'>");
/* 383 */         for (int j = 0; j < process.size(); j++) {
/* 384 */           if (j != 5) {
/* 385 */             str.append("<td>").append((String)process.get(j)).append("</td>");
/*     */           } else {
/* 387 */             str.append("<td><div style='WORD-BREAK:BREAK-ALL; word-wrap: break-word; width:350px; white-space:-moz-pre-wrap; white-space: normal;'>").append((String)process.get(j)).append("</div></td></tr>");
/*     */           }
/*     */         }
/*     */       }
/* 391 */       if (nodata) {
/* 392 */         str.append("<tr class='whitegrayborder'>");
/* 393 */         str.append("<td colspan='8' align='center'>").append("No Process available").append("</td></tr>");
/*     */       }
/* 395 */       if (appman) {
/* 396 */         str.append("<b><span class='bodytext'>" + FormatUtil.getString("am.webclient.mysql.processlist.title", new String[] { resourceName }) + " , " + sdfw.format(logDate) + "</span></b><br><br>");
/*     */       }
/* 398 */       str.append("</table></div>");
/* 399 */       str.append("</body></html>");
/* 400 */       out.println(str);
/* 401 */       url = "/html/Data/IngresMonitor/IngresProcess/" + resourceid + "/" + URLEncoder.encode(resourceName) + "_" + sdf.format(logDate) + ".html";
/*     */       
/* 403 */       out.close();
/*     */     } catch (Exception e) {
/* 405 */       e.printStackTrace();
/*     */     }
/* 407 */     return url;
/*     */   }
/*     */   
/*     */   private static class timeComparatorForIngres
/*     */     implements Comparator
/*     */   {
/*     */     public int compare(Object o1, Object o2)
/*     */     {
/* 415 */       ArrayList list1 = (ArrayList)o1;
/* 416 */       ArrayList list2 = (ArrayList)o2;
/* 417 */       int val1 = Integer.parseInt((String)list1.get(4));
/* 418 */       int val2 = Integer.parseInt((String)list2.get(4));
/* 419 */       if (val1 == val2) {
/* 420 */         return 0;
/*     */       }
/* 422 */       if (val1 > val2) {
/* 423 */         return -1;
/*     */       }
/* 425 */       return 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public static ArrayList convertStringtoArray(String commaseparated_commands)
/*     */   {
/* 431 */     ArrayList colValue = new ArrayList();
/* 432 */     if (commaseparated_commands != null) {
/* 433 */       StringTokenizer commandTokens = new StringTokenizer(commaseparated_commands, ",");
/* 434 */       while (commandTokens.hasMoreTokens()) {
/* 435 */         colValue.add(commandTokens.nextToken().trim());
/*     */       }
/*     */     }
/* 438 */     return colValue;
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean validateAPIKey(String apiKey)
/*     */   {
/* 444 */     String APIKey = "";
/* 445 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 446 */     ResultSet rs = null;
/* 447 */     boolean validateKey = false;
/*     */     try {
/* 449 */       String checkquery = "select * from AM_UserPasswordTable where APIKEY='" + apiKey + "'";
/* 450 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 451 */       while (rs.next()) {
/* 452 */         APIKey = rs.getString("APIKEY");
/* 453 */         if (APIKey.equals(apiKey)) {
/* 454 */           validateKey = true;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 459 */       ex.printStackTrace();
/*     */     }
/*     */     finally {
/* 462 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 464 */     return validateKey;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\ingres\struts\IngresAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */