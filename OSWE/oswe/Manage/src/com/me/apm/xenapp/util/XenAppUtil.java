/*     */ package com.me.apm.xenapp.util;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.me.apm.server.datatable.DataTableColumn;
/*     */ import com.me.apm.server.datatable.DataTableDefinition;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XenAppUtil
/*     */ {
/*  28 */   public static String[] eventLogColumnNames = { "RULENAME", "RULETYPE", "SOURCE", "EVENTID", "DESCRIPTION", "USERNAME", "DESCRIPTIONSTRING", "EVENTGENERATEDTIME" };
/*     */   
/*  30 */   private static String eventLogTableName = "XenAppEventLog";
/*     */   
/*  32 */   private static DataTableDefinition eventLogTableDef = null;
/*     */   
/*  34 */   private static String eventLogCountQuery = "select count(*) from AM_EVENTLOGMONITORDATA where RESOURCEID in (?)";
/*     */   
/*  36 */   private static SimpleDateFormat eventLogDateFormat = new SimpleDateFormat("MMM d, yyyy hh:mm:ss aaa");
/*     */   
/*  38 */   private static String resourceType = "XenApp";
/*     */   
/*  40 */   private static String ajaxEventLogDataSourceURL = "/dataTableAction.do?method=getEventLogForXenApp&resourceid=";
/*     */   
/*     */ 
/*     */   public static JsonObject getEventLogTableInitParams(String resourceid)
/*     */   {
/*  45 */     JsonObject initParams = new JsonObject();
/*     */     
/*  47 */     if (eventLogTableDef != null)
/*     */     {
/*  49 */       initParams = eventLogTableDef.getInitParams();
/*     */     }
/*     */     else
/*     */     {
/*  53 */       LinkedHashMap<String, DataTableColumn> map = new LinkedHashMap();
/*     */       
/*     */ 
/*  56 */       map.put("0", new DataTableColumn("RULENAME", FormatUtil.getString("am.webclient.eventlogrules.RuleName"), 0));
/*  57 */       map.put("1", new DataTableColumn("RULETYPE", FormatUtil.getString("am.webclient.eventlogrules.LogFileType"), 1));
/*  58 */       map.put("2", new DataTableColumn("SOURCE", FormatUtil.getString("am.webclient.alerts.trap.source"), 2));
/*  59 */       map.put("3", new DataTableColumn("EVENTID", FormatUtil.getString("am.webclient.eventlogrules.EventId"), 3));
/*  60 */       map.put("4", new DataTableColumn("DESCRIPTION", FormatUtil.getString("webclient.topo.type"), 4));
/*  61 */       map.put("5", new DataTableColumn("USERNAME", FormatUtil.getString("webclient.topo.userName"), 5));
/*  62 */       map.put("6", new DataTableColumn("DESCRIPTIONSTRING", FormatUtil.getString("Description"), 6, true, false));
/*  63 */       map.put("7", new DataTableColumn("EVENTGENERATEDTIME", FormatUtil.getString("am.webclient.eventlogrules.generatedtime"), 7, false, true));
/*     */       
/*  65 */       DataTableDefinition xenEventLog = new DataTableDefinition(eventLogTableName, map, ajaxEventLogDataSourceURL);
/*     */       
/*     */ 
/*  68 */       eventLogTableDef = xenEventLog;
/*  69 */       initParams = eventLogTableDef.getInitParams();
/*     */       
/*  71 */       JsonArray defArray = new JsonArray();
/*  72 */       defArray.add(new JsonPrimitive("7"));
/*  73 */       defArray.add(new JsonPrimitive("desc"));
/*  74 */       JsonArray sortingArray = new JsonArray();
/*  75 */       sortingArray.add(defArray);
/*  76 */       xenEventLog.setOrder(sortingArray);
/*     */     }
/*     */     
/*  79 */     String dataSrcURL = getEventLogDataSourceURL();
/*  80 */     dataSrcURL = dataSrcURL + resourceid;
/*     */     
/*  82 */     initParams.addProperty("ajax", dataSrcURL);
/*  83 */     AMLog.debug("XenAPP init params " + initParams);
/*  84 */     return initParams;
/*     */   }
/*     */   
/*     */ 
/*     */   public static DataTableDefinition getEventLogTableDefinition()
/*     */   {
/*  90 */     if (eventLogTableDef == null)
/*     */     {
/*  92 */       eventLogTableDef.getInitParams();
/*     */     }
/*  94 */     return eventLogTableDef;
/*     */   }
/*     */   
/*     */   public static String getEventLogDataSourceURL()
/*     */   {
/*  99 */     String dataSrc = String.valueOf(ajaxEventLogDataSourceURL);
/* 100 */     return dataSrc;
/*     */   }
/*     */   
/*     */ 
/*     */   public static PreparedStatement getEventLogCountPS(String resourceId)
/*     */   {
/*     */     try
/*     */     {
/* 108 */       PreparedStatement ps = AMConnectionPool.getPreparedStatement(eventLogCountQuery);
/* 109 */       long resid = Long.parseLong(resourceId);
/* 110 */       ps.setLong(1, resid);
/* 111 */       AMLog.debug("XenApp eventlog count " + resourceId + " " + ps);
/* 112 */       return ps;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 116 */       AMLog.debug("Exception when getting XenApp eventlog count preparedstatement");
/*     */     }
/* 118 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public static String getEventLogQuery(String resId, String globalSearchStr, String columnSearch, long lastDCTime)
/*     */   {
/* 124 */     String finalQuery = "";
/*     */     try
/*     */     {
/* 127 */       String filterCondition = "";
/*     */       
/* 129 */       finalQuery = "select gelr.RULENAME,elm.RULETYPE,amo.RESOURCENAME,elm.SOURCE,elm.EVENTID,et.DESCRIPTION,elm.USERNAME,elm.DESCRIPTIONSTRING,elm.EVENTGENERATEDTIME from AM_EVENTLOGMONITORDATA elm inner join AM_ManagedObject amo on elm.RESOURCEID=amo.RESOURCEID inner join AM_EVENTTYPE as et on elm.EVENTTYPE=et.EVENTTYPE inner join AM_GLOBALEVENTLOGRULES as gelr on elm.RULEID=gelr.RULEID where elm.RESOURCEID in (" + resId + ") and elm.CLUSTERRESOURCEID=-1";
/*     */       
/* 131 */       String globalSearch = "";
/*     */       
/* 133 */       if ((globalSearchStr != null) && (!"".equals(globalSearchStr)))
/*     */       {
/* 135 */         globalSearch = " and (gelr.RULENAME like '%" + globalSearchStr + "%'" + " or elm.RULETYPE like '%" + globalSearchStr + "%'" + " or amo.RESOURCENAME like '%" + globalSearchStr + "%'" + " or elm.SOURCE like '%" + globalSearchStr + "%'" + " or elm.EVENTID like '%" + globalSearchStr + "%'" + " or et.DESCRIPTION like '%" + globalSearchStr + "%'" + " or elm.USERNAME like '%" + globalSearchStr + "%'" + " or elm.DESCRIPTIONSTRING like '%" + globalSearchStr + "%')";
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 145 */         filterCondition = globalSearch;
/*     */       }
/*     */       
/* 148 */       if ((globalSearchStr != "") && (columnSearch.length() > 0))
/*     */       {
/* 150 */         filterCondition = globalSearch + " and " + columnSearch.toString();
/*     */       }
/* 152 */       else if (columnSearch.length() > 0)
/*     */       {
/* 154 */         filterCondition = " and " + columnSearch.toString();
/*     */       }
/* 156 */       else if ((globalSearchStr != null) && (globalSearchStr != ""))
/*     */       {
/* 158 */         filterCondition = globalSearch;
/*     */       }
/*     */       
/* 161 */       if (lastDCTime > -1L)
/*     */       {
/* 163 */         filterCondition = filterCondition + " and elm.COLLECTIONTIME <= " + String.valueOf(lastDCTime);
/*     */       }
/*     */       
/* 166 */       finalQuery = finalQuery + filterCondition;
/*     */     }
/*     */     catch (Exception ex) {}
/*     */     
/*     */ 
/*     */ 
/* 172 */     return finalQuery;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static JsonArray getEventLogData(String query, int startRow, int noOfRows, String sortDir, String orderColumnName)
/*     */   {
/* 179 */     JsonArray eventLogData = new JsonArray();
/* 180 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 183 */       if ((startRow >= 0) && (noOfRows > 0))
/*     */       {
/* 185 */         String finalQuery = query;
/*     */         
/* 187 */         finalQuery = finalQuery + " order by " + orderColumnName + " " + sortDir;
/*     */         
/* 189 */         finalQuery = DBQueryUtil.addLimit(finalQuery, startRow, noOfRows, orderColumnName, sortDir.equals("asc"));
/*     */         
/* 191 */         AMLog.debug("XenApp Final Query " + finalQuery);
/* 192 */         rs = AMConnectionPool.executeQueryStmt(finalQuery);
/*     */         
/* 194 */         while (rs.next())
/*     */         {
/* 196 */           JsonArray eventLogRow = new JsonArray();
/*     */           
/* 198 */           eventLogRow.add(new JsonPrimitive(rs.getString("RULENAME")));
/* 199 */           eventLogRow.add(new JsonPrimitive(rs.getString("RULETYPE")));
/*     */           
/* 201 */           eventLogRow.add(new JsonPrimitive(rs.getString("SOURCE")));
/* 202 */           eventLogRow.add(new JsonPrimitive(rs.getString("EVENTID")));
/* 203 */           eventLogRow.add(new JsonPrimitive(rs.getString("DESCRIPTION")));
/* 204 */           eventLogRow.add(new JsonPrimitive(rs.getString("USERNAME")));
/*     */           
/* 206 */           String descStr = rs.getString("DESCRIPTIONSTRING");
/* 207 */           String descStrTrimmed = "";
/*     */           try
/*     */           {
/* 210 */             descStrTrimmed = descStr.substring(0, 8);
/* 211 */             descStrTrimmed = descStrTrimmed + "....";
/*     */           }
/*     */           catch (IndexOutOfBoundsException ex)
/*     */           {
/* 215 */             descStrTrimmed = descStr;
/*     */           }
/* 217 */           eventLogRow.add(new JsonPrimitive(descStrTrimmed));
/* 218 */           String dateTime = rs.getString("EVENTGENERATEDTIME");
/*     */           try
/*     */           {
/* 221 */             Date dateObj = new Date(Long.valueOf(dateTime).longValue());
/* 222 */             String formatted = eventLogDateFormat.format(dateObj);
/* 223 */             eventLogRow.add(new JsonPrimitive(formatted));
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 227 */             eventLogRow.add(new JsonPrimitive("-1"));
/*     */           }
/* 229 */           eventLogRow.add(new JsonPrimitive(""));
/*     */           
/* 231 */           eventLogData.add(eventLogRow);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 238 */       AMLog.debug("Exception in getting Eventlog data ", ex);
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 243 */       AMConnectionPool.closeResultSet(rs);
/*     */     }
/* 245 */     AMLog.info("XenApp Eventlog data array " + eventLogData);
/*     */     
/* 247 */     return eventLogData;
/*     */   }
/*     */   
/*     */   public static String getResourceTypeName()
/*     */   {
/* 252 */     return resourceType;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\me\apm\xenapp\util\XenAppUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */