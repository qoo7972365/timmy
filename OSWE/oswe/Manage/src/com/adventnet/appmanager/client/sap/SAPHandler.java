/*     */ package com.adventnet.appmanager.client.sap;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.json.JSONUtil;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import java.net.URLEncoder;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ public class SAPHandler
/*     */ {
/*  19 */   private static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a");
/*     */   
/*  21 */   public static String getTRFCData(String resid) { ResultSet set = null;
/*  22 */     HashMap finalresult = new HashMap();
/*     */     try {
/*  24 */       set = AMConnectionPool.executeQueryStmt("select TID,CALLER,FNAME,DESTINATION,TIME,STATUS,MESSAGE,TCODE from AM_SAP_TRFCCallInfo where RESOURCEID=" + resid + " ORDER BY TIME DESC");
/*  25 */       List<HashMap> result = new ArrayList();
/*  26 */       while (set.next()) {
/*  27 */         HashMap row = new HashMap();
/*  28 */         row.put("TID", set.getString("TID"));
/*  29 */         row.put("CALLER", set.getString("CALLER"));
/*  30 */         row.put("FNAME", set.getString("FNAME"));
/*  31 */         row.put("DESTINATION", set.getString("DESTINATION"));
/*  32 */         Date cd = new Date(set.getLong("TIME"));
/*  33 */         row.put("TIME", sdf.format(cd));
/*  34 */         row.put("STATUS", set.getString("STATUS"));
/*  35 */         row.put("MESSAGE", set.getString("MESSAGE"));
/*  36 */         row.put("TCODE", set.getString("TCODE"));
/*  37 */         result.add(row);
/*     */       }
/*  39 */       finalresult.put("data", result);
/*     */     } catch (Exception e) {
/*  41 */       e.printStackTrace();
/*     */     } finally {
/*  43 */       if (set != null) {
/*  44 */         AMConnectionPool.closeResultSet(set);
/*     */       }
/*     */     }
/*  47 */     String jsonString = JSONUtil.getString(finalresult);
/*  48 */     return jsonString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getQoutSchedular(String resid)
/*     */   {
/*  56 */     ResultSet set = null;
/*  57 */     HashMap finalresult = new HashMap();
/*  58 */     Properties healthdetails = getHealthStatus(resid, "SAP_QOUT_SCHEDULAR");
/*  59 */     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resid + "&datatype=10");
/*     */     try {
/*  61 */       String query = "select mo.RESOURCEID,q.CLIENT,q.STATE,q.GROUPINFO,q.HOST,q.TIME from AM_ManagedObject mo left outer join AM_PARENTCHILDMAPPER pcm on mo.RESOURCEID=pcm.CHILDID left outer join AM_SAP_QSchedularInfo q on q.RESOURCEID=mo.RESOURCEID and q.COLLECTIONTIME=(select max(collectiontime) from AM_ManagedObjectData where resid=" + resid + ") where pcm.PARENTID=" + resid + " and mo.TYPE='SAP_QOUT_SCHEDULAR'";
/*  62 */       set = AMConnectionPool.executeQueryStmt(query);
/*  63 */       List<HashMap> result = new ArrayList();
/*  64 */       while (set.next()) {
/*  65 */         HashMap row = new HashMap();
/*  66 */         row.put("CLIENT", set.getString("CLIENT"));
/*  67 */         row.put("STATE", set.getString("STATE"));
/*  68 */         Date cd = new Date(set.getLong("TIME"));
/*  69 */         row.put("TIME", sdf.format(cd));
/*  70 */         if (set.getString("GROUPINFO") == null) {
/*  71 */           row.put("GROUPINFO", "-");
/*  72 */         } else if (set.getString("GROUPINFO").isEmpty()) {
/*  73 */           row.put("GROUPINFO", "-");
/*     */         } else {
/*  75 */           row.put("GROUPINFO", set.getString("GROUPINFO"));
/*     */         }
/*  77 */         if (set.getString("HOST") == null) {
/*  78 */           row.put("HOST", "-");
/*  79 */         } else if (set.getString("HOST").isEmpty()) {
/*  80 */           row.put("HOST", "-");
/*     */         } else {
/*  82 */           row.put("HOST", set.getString("HOST"));
/*     */         }
/*  84 */         row.put("HEALTH", "<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + set.getString("RESOURCEID") + "&attributeid=3770')\">" + getSeverityImageForHealth(healthdetails.getProperty(new StringBuilder().append(set.getString("RESOURCEID")).append("#").append("3770").toString())) + "</a>");
/*  85 */         row.put("CONFIGUREALARM", "<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=" + set.getString("RESOURCEID") + "&attributeIDs=3770,3771&attributeToSelect=3770&redirectto=" + encodeurl + "\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" border=\"0\"></a>");
/*  86 */         result.add(row);
/*     */       }
/*  88 */       finalresult.put("data", result);
/*     */     } catch (Exception e) {
/*  90 */       e.printStackTrace();
/*     */     } finally {
/*  92 */       if (set != null) {
/*  93 */         AMConnectionPool.closeResultSet(set);
/*     */       }
/*     */     }
/*  96 */     String jsonString = JSONUtil.getString(finalresult);
/*  97 */     return jsonString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getQinSchedular(String resid)
/*     */   {
/* 105 */     ResultSet set = null;
/* 106 */     HashMap finalresult = new HashMap();
/* 107 */     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resid + "&datatype=10");
/* 108 */     Properties healthdetails = getHealthStatus(resid, "SAP_QIN_SCHEDULAR");
/*     */     try {
/* 110 */       String query = "select mo.RESOURCEID,q.CLIENT,q.STATE,q.GROUPINFO,q.HOST,q.TIME from AM_ManagedObject mo left outer join AM_PARENTCHILDMAPPER pcm on mo.RESOURCEID=pcm.CHILDID left outer join AM_SAP_QSchedularInfo q on q.RESOURCEID=mo.RESOURCEID and q.COLLECTIONTIME=(select max(collectiontime) from AM_ManagedObjectData where resid=" + resid + ") where pcm.PARENTID=" + resid + " and mo.TYPE='SAP_QIN_SCHEDULAR'";
/* 111 */       set = AMConnectionPool.executeQueryStmt(query);
/* 112 */       List<HashMap> result = new ArrayList();
/* 113 */       while (set.next()) {
/* 114 */         HashMap row = new HashMap();
/* 115 */         row.put("CLIENT", set.getString("CLIENT"));
/* 116 */         row.put("STATE", set.getString("STATE"));
/* 117 */         Date cd = new Date(set.getLong("TIME"));
/* 118 */         row.put("TIME", sdf.format(cd));
/* 119 */         if (set.getString("GROUPINFO") == null) {
/* 120 */           row.put("GROUPINFO", "-");
/* 121 */         } else if (set.getString("GROUPINFO").isEmpty()) {
/* 122 */           row.put("GROUPINFO", "-");
/*     */         } else {
/* 124 */           row.put("GROUPINFO", set.getString("GROUPINFO"));
/*     */         }
/* 126 */         if (set.getString("HOST") == null) {
/* 127 */           row.put("HOST", "-");
/* 128 */         } else if (set.getString("HOST").isEmpty()) {
/* 129 */           row.put("HOST", "-");
/*     */         } else {
/* 131 */           row.put("HOST", set.getString("HOST"));
/*     */         }
/* 133 */         row.put("HEALTH", "<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + set.getString("RESOURCEID") + "&attributeid=3775')\">" + getSeverityImageForHealth(healthdetails.getProperty(new StringBuilder().append(set.getString("RESOURCEID")).append("#").append("3775").toString())) + "</a>");
/* 134 */         row.put("CONFIGUREALARM", "<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=" + set.getString("RESOURCEID") + "&attributeIDs=3775,3776&attributeToSelect=3775&redirectto=" + encodeurl + "\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" border=\"0\"></a>");
/* 135 */         result.add(row);
/*     */       }
/* 137 */       finalresult.put("data", result);
/*     */     } catch (Exception e) {
/* 139 */       e.printStackTrace();
/*     */     } finally {
/* 141 */       if (set != null) {
/* 142 */         AMConnectionPool.closeResultSet(set);
/*     */       }
/*     */     }
/* 145 */     String jsonString = JSONUtil.getString(finalresult);
/* 146 */     return jsonString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getQoutData(String resid)
/*     */   {
/* 154 */     ResultSet set = null;
/* 155 */     HashMap finalresult = new HashMap();
/* 156 */     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resid + "&datatype=10");
/* 157 */     Properties healthdetails = getHealthStatus(resid, "SAP_QOUT_DATA");
/*     */     try {
/* 159 */       String query = "select mo.RESOURCEID,q.CLIENT,q.QNAME,q.DESTINATION,q.STATE,q.MSG,q.DATE from AM_ManagedObject mo left outer join AM_PARENTCHILDMAPPER pcm on mo.RESOURCEID=pcm.CHILDID left outer join AM_SAP_QoutData q on q.RESOURCEID=mo.RESOURCEID and q.COLLECTIONTIME=(select max(collectiontime) from AM_ManagedObjectData where resid=" + resid + ") where pcm.PARENTID=" + resid + " and mo.TYPE='SAP_QOUT_DATA'";
/* 160 */       set = AMConnectionPool.executeQueryStmt(query);
/* 161 */       List<HashMap> result = new ArrayList();
/* 162 */       while (set.next()) {
/* 163 */         HashMap row = new HashMap();
/* 164 */         row.put("CLIENT", set.getString("CLIENT"));
/* 165 */         row.put("QNAME", set.getString("QNAME"));
/* 166 */         row.put("DESTINATION", set.getString("DESTINATION"));
/* 167 */         row.put("STATE", set.getString("STATE"));
/* 168 */         if (set.getString("MSG") == null) {
/* 169 */           row.put("MSG", "-");
/* 170 */         } else if (set.getString("MSG").isEmpty()) {
/* 171 */           row.put("MSG", "-");
/*     */         } else {
/* 173 */           row.put("MSG", set.getString("MSG"));
/*     */         }
/* 175 */         Date cd = new Date(set.getLong("DATE"));
/* 176 */         row.put("DATE", sdf.format(cd));
/* 177 */         row.put("HEALTH", "<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + set.getString("RESOURCEID") + "&attributeid=3780')\">" + getSeverityImageForHealth(healthdetails.getProperty(new StringBuilder().append(set.getString("RESOURCEID")).append("#").append("3780").toString())) + "</a>");
/* 178 */         row.put("CONFIGUREALARM", "<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=" + set.getString("RESOURCEID") + "&attributeIDs=3780,3781,3782&attributeToSelect=3780&redirectto=" + encodeurl + "\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" border=\"0\"></a>");
/* 179 */         result.add(row);
/*     */       }
/* 181 */       finalresult.put("data", result);
/*     */     } catch (Exception e) {
/* 183 */       e.printStackTrace();
/*     */     } finally {
/* 185 */       if (set != null) {
/* 186 */         AMConnectionPool.closeResultSet(set);
/*     */       }
/*     */     }
/* 189 */     String jsonString = JSONUtil.getString(finalresult);
/* 190 */     return jsonString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getQinData(String resid)
/*     */   {
/* 198 */     ResultSet set = null;
/* 199 */     HashMap finalresult = new HashMap();
/* 200 */     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resid + "&datatype=10");
/* 201 */     Properties healthdetails = getHealthStatus(resid, "SAP_QIN_DATA");
/*     */     try {
/* 203 */       String query = "select mo.RESOURCEID,q.CLIENT,q.QNAME,q.STATE,q.MSG,q.DATE from AM_ManagedObject mo left outer join AM_PARENTCHILDMAPPER pcm on mo.RESOURCEID=pcm.CHILDID left outer join AM_SAP_QinData q on q.RESOURCEID=mo.RESOURCEID and q.COLLECTIONTIME=(select max(collectiontime) from AM_ManagedObjectData where resid=" + resid + ") where pcm.PARENTID=" + resid + " and mo.TYPE='SAP_QIN_DATA'";
/* 204 */       set = AMConnectionPool.executeQueryStmt(query);
/* 205 */       List<HashMap> result = new ArrayList();
/* 206 */       while (set.next()) {
/* 207 */         HashMap row = new HashMap();
/* 208 */         row.put("CLIENT", set.getString("CLIENT"));
/* 209 */         row.put("QNAME", set.getString("QNAME"));
/* 210 */         row.put("STATE", set.getString("STATE"));
/* 211 */         if (set.getString("MSG") == null) {
/* 212 */           row.put("MSG", "-");
/* 213 */         } else if (set.getString("MSG").isEmpty()) {
/* 214 */           row.put("MSG", "-");
/*     */         } else {
/* 216 */           row.put("MSG", set.getString("MSG"));
/*     */         }
/* 218 */         Date cd = new Date(set.getLong("DATE"));
/* 219 */         row.put("DATE", sdf.format(cd));
/* 220 */         row.put("HEALTH", "<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + set.getString("RESOURCEID") + "&attributeid=3785')\">" + getSeverityImageForHealth(healthdetails.getProperty(new StringBuilder().append(set.getString("RESOURCEID")).append("#").append("3785").toString())) + "</a>");
/* 221 */         row.put("CONFIGUREALARM", "<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=" + set.getString("RESOURCEID") + "&attributeIDs=3785,3786,3787&attributeToSelect=3785&redirectto=" + encodeurl + "\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" border=\"0\"></a>");
/* 222 */         result.add(row);
/*     */       }
/* 224 */       finalresult.put("data", result);
/*     */     } catch (Exception e) {
/* 226 */       e.printStackTrace();
/*     */     } finally {
/* 228 */       if (set != null) {
/* 229 */         AMConnectionPool.closeResultSet(set);
/*     */       }
/*     */     }
/* 232 */     String jsonString = JSONUtil.getString(finalresult);
/* 233 */     return jsonString;
/*     */   }
/*     */   
/*     */   public static Properties getHealthStatus(String resid, String resourceType) {
/* 237 */     Properties status = new Properties();
/*     */     try {
/* 239 */       ArrayList listOfResourceIDs = DBUtil.getRowsForSingleColumn("select RESOURCEID from AM_ManagedObject where RESOURCEID in (select CHILDID from AM_PARENTCHILDMAPPER where PARENTID=" + resid + ") and TYPE='" + resourceType + "'");
/* 240 */       ArrayList listOfAttributeIDs = DBUtil.getRowsForSingleColumn("select ATTRIBUTEID from AM_ATTRIBUTES where RESOURCETYPE='" + resourceType + "'");
/* 241 */       status = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*     */     } catch (Exception ex) {
/* 243 */       ex.printStackTrace();
/*     */     }
/* 245 */     return status;
/*     */   }
/*     */   
/*     */   public static String getSeverityImageForHealth(String severity)
/*     */   {
/* 250 */     long j = 0L;
/*     */     
/* 252 */     if (severity == null)
/*     */     {
/* 254 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" >";
/*     */     }
/* 256 */     if (severity.equals("5"))
/*     */     {
/* 258 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  >";
/*     */     }
/* 260 */     if (severity.equals("4"))
/*     */     {
/* 262 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" >";
/*     */     }
/* 264 */     if (severity.equals("1"))
/*     */     {
/* 266 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" >";
/*     */     }
/*     */     
/*     */ 
/* 270 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" >";
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\sap\SAPHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */