/*     */ package com.me.apm.msdynamics.crm.util;
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
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MSDynamicsCRMUtil
/*     */ {
/*  28 */   public static String[] eventLogColumnNames = { "RULENAME", "RULETYPE", "SOURCE", "EVENTID", "DESCRIPTION", "USERNAME", "DESCRIPTIONSTRING", "EVENTGENERATEDTIME" };
/*     */   
/*  30 */   private static String eventLogTableName = "MSDynamicsCRMEventLog";
/*     */   
/*  32 */   private static DataTableDefinition eventLogTableDef = null;
/*     */   
/*  34 */   private static String eventLogCountQuery = "select count(*) from AM_EVENTLOGMONITORDATA where RESOURCEID in (?)";
/*     */   
/*  36 */   private static SimpleDateFormat eventLogDateFormat = new SimpleDateFormat("MMM d, yyyy hh:mm:ss aaa");
/*     */   
/*  38 */   private static String resourceType = "Microsoft Dynamics CRM";
/*     */   
/*  40 */   private static String ajaxEventLogDataSourceURL = "/dataTableAction.do?method=getEventLogForDynamicsCRM&resourceid=";
/*     */   
/*     */   public static HashMap<String, String[]> getCRMServiceName(String resourceIds)
/*     */   {
/*  44 */     ResultSet rs = null;
/*     */     
/*  46 */     HashMap<String, String[]> serviceMap = new HashMap();
/*     */     try
/*     */     {
/*  49 */       String query = "select * from AM_ManagedObject where RESOURCEID in (" + resourceIds + ")";
/*  50 */       rs = AMConnectionPool.executeQueryStmt(query);
/*     */       
/*  52 */       while (rs.next())
/*     */       {
/*  54 */         String resId = String.valueOf(rs.getInt(1));
/*  55 */         String resrcName = rs.getString("RESOURCENAME");
/*  56 */         int firstIndx = resrcName.indexOf("_");
/*  57 */         int lastIndx = resrcName.lastIndexOf("_");
/*  58 */         String serviceName = resrcName.substring(firstIndx, lastIndx);
/*     */         
/*  60 */         String[] details = new String[1];
/*  61 */         details[0] = serviceName;
/*     */         
/*  63 */         serviceMap.put(resId, details);
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  69 */       e.printStackTrace();
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*  74 */       if (rs != null)
/*     */       {
/*  76 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     
/*  80 */     return serviceMap;
/*     */   }
/*     */   
/*     */ 
/*     */   public static JsonObject getEventLogTableInitParams(String resourceid)
/*     */   {
/*  86 */     JsonObject initParams = new JsonObject();
/*     */     
/*  88 */     if (eventLogTableDef != null)
/*     */     {
/*  90 */       initParams = eventLogTableDef.getInitParams();
/*     */     }
/*     */     else
/*     */     {
/*  94 */       LinkedHashMap<String, DataTableColumn> map = new LinkedHashMap();
/*     */       
/*     */ 
/*  97 */       map.put("0", new DataTableColumn("RULENAME", FormatUtil.getString("am.webclient.eventlogrules.RuleName"), 0));
/*  98 */       map.put("1", new DataTableColumn("RULETYPE", FormatUtil.getString("am.webclient.eventlogrules.LogFileType"), 1));
/*  99 */       map.put("2", new DataTableColumn("SOURCE", FormatUtil.getString("am.webclient.alerts.trap.source"), 2));
/* 100 */       map.put("3", new DataTableColumn("EVENTID", FormatUtil.getString("am.webclient.eventlogrules.EventId"), 3));
/* 101 */       map.put("4", new DataTableColumn("DESCRIPTION", FormatUtil.getString("webclient.topo.type"), 4));
/* 102 */       map.put("5", new DataTableColumn("USERNAME", FormatUtil.getString("webclient.topo.userName"), 5));
/* 103 */       map.put("6", new DataTableColumn("DESCRIPTIONSTRING", FormatUtil.getString("Description"), 6, true, false));
/* 104 */       map.put("7", new DataTableColumn("EVENTGENERATEDTIME", FormatUtil.getString("am.webclient.eventlogrules.generatedtime"), 7, false, true));
/*     */       
/* 106 */       DataTableDefinition crmEventLog = new DataTableDefinition(eventLogTableName, map, ajaxEventLogDataSourceURL);
/*     */       
/*     */ 
/* 109 */       eventLogTableDef = crmEventLog;
/* 110 */       initParams = eventLogTableDef.getInitParams();
/*     */       
/* 112 */       JsonArray defArray = new JsonArray();
/* 113 */       defArray.add(new JsonPrimitive("7"));
/* 114 */       defArray.add(new JsonPrimitive("desc"));
/* 115 */       JsonArray sortingArray = new JsonArray();
/* 116 */       sortingArray.add(defArray);
/* 117 */       crmEventLog.setOrder(sortingArray);
/*     */     }
/*     */     
/* 120 */     String dataSrcURL = getEventLogDataSourceURL();
/* 121 */     dataSrcURL = dataSrcURL + resourceid;
/*     */     
/* 123 */     initParams.addProperty("ajax", dataSrcURL);
/* 124 */     AMLog.info("MSCRM eventlog init params " + initParams);
/* 125 */     return initParams;
/*     */   }
/*     */   
/*     */ 
/*     */   public static DataTableDefinition getEventLogTableDefinition()
/*     */   {
/* 131 */     if (eventLogTableDef == null)
/*     */     {
/* 133 */       eventLogTableDef.getInitParams();
/*     */     }
/* 135 */     return eventLogTableDef;
/*     */   }
/*     */   
/*     */   public static String getEventLogDataSourceURL()
/*     */   {
/* 140 */     String dataSrc = String.valueOf(ajaxEventLogDataSourceURL);
/* 141 */     return dataSrc;
/*     */   }
/*     */   
/*     */   public static PreparedStatement getEventLogCountPS(String resourceId)
/*     */   {
/*     */     try
/*     */     {
/* 148 */       PreparedStatement ps = AMConnectionPool.getPreparedStatement(eventLogCountQuery);
/* 149 */       long resid = Long.parseLong(resourceId);
/* 150 */       ps.setLong(1, resid);
/* 151 */       AMLog.info("MSCRM eventlog count " + resourceId + " " + ps);
/* 152 */       return ps;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 156 */       AMLog.info("Exception when getting DynamicsCRM eventlog count preparedstatement");
/*     */     }
/* 158 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public static String getEventLogQuery(String resId, String globalSearchStr, String columnSearch, long lastDCTime)
/*     */   {
/* 164 */     String finalQuery = "";
/*     */     try
/*     */     {
/* 167 */       String filterCondition = "";
/*     */       
/* 169 */       finalQuery = "select gelr.RULENAME,elm.RULETYPE,amo.RESOURCENAME,elm.SOURCE,elm.EVENTID,et.DESCRIPTION,elm.USERNAME,elm.DESCRIPTIONSTRING,elm.EVENTGENERATEDTIME from AM_EVENTLOGMONITORDATA elm inner join AM_MANAGEDOBJECT amo on elm.RESOURCEID=amo.RESOURCEID inner join AM_EVENTTYPE as et on elm.EVENTTYPE=et.EVENTTYPE inner join AM_GLOBALEVENTLOGRULES as gelr on elm.RULEID=gelr.RULEID where elm.RESOURCEID in (" + resId + ") and elm.CLUSTERRESOURCEID=-1";
/*     */       
/* 171 */       String globalSearch = "";
/*     */       
/* 173 */       if ((globalSearchStr != null) && (!"".equals(globalSearchStr)))
/*     */       {
/* 175 */         globalSearch = " and (gelr.RULENAME like '%" + globalSearchStr + "%'" + " or elm.RULETYPE like '%" + globalSearchStr + "%'" + " or amo.RESOURCENAME like '%" + globalSearchStr + "%'" + " or elm.SOURCE like '%" + globalSearchStr + "%'" + " or elm.EVENTID like '%" + globalSearchStr + "%'" + " or et.DESCRIPTION like '%" + globalSearchStr + "%'" + " or elm.USERNAME like '%" + globalSearchStr + "%'" + " or elm.DESCRIPTIONSTRING like '%" + globalSearchStr + "%')";
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 185 */         filterCondition = globalSearch;
/*     */       }
/*     */       
/* 188 */       if ((globalSearchStr != "") && (columnSearch.length() > 0))
/*     */       {
/* 190 */         filterCondition = globalSearch + " and " + columnSearch.toString();
/*     */       }
/* 192 */       else if (columnSearch.length() > 0)
/*     */       {
/* 194 */         filterCondition = " and " + columnSearch.toString();
/*     */       }
/* 196 */       else if ((globalSearchStr != null) && (globalSearchStr != ""))
/*     */       {
/* 198 */         filterCondition = globalSearch;
/*     */       }
/*     */       
/* 201 */       if (lastDCTime > -1L)
/*     */       {
/* 203 */         filterCondition = filterCondition + " and elm.COLLECTIONTIME <= " + String.valueOf(lastDCTime);
/*     */       }
/*     */       
/* 206 */       finalQuery = finalQuery + filterCondition;
/*     */     }
/*     */     catch (Exception ex) {}
/*     */     
/*     */ 
/*     */ 
/* 212 */     return finalQuery;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static JsonArray getEventLogData(String query, int startRow, int noOfRows, String sortDir, String orderColumnName)
/*     */   {
/* 219 */     JsonArray eventLogData = new JsonArray();
/* 220 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 223 */       if ((startRow >= 0) && (noOfRows > 0))
/*     */       {
/* 225 */         String finalQuery = query;
/*     */         
/* 227 */         finalQuery = finalQuery + " order by " + orderColumnName + " " + sortDir;
/*     */         
/* 229 */         finalQuery = DBQueryUtil.addLimit(finalQuery, startRow, noOfRows, orderColumnName, sortDir.equals("asc"));
/*     */         
/* 231 */         AMLog.info("MSCRM Final Query " + finalQuery);
/* 232 */         rs = AMConnectionPool.executeQueryStmt(finalQuery);
/*     */         
/* 234 */         while (rs.next())
/*     */         {
/* 236 */           JsonArray eventLogRow = new JsonArray();
/*     */           
/* 238 */           eventLogRow.add(new JsonPrimitive(rs.getString("RULENAME")));
/* 239 */           eventLogRow.add(new JsonPrimitive(rs.getString("RULETYPE")));
/*     */           
/* 241 */           eventLogRow.add(new JsonPrimitive(rs.getString("SOURCE")));
/* 242 */           eventLogRow.add(new JsonPrimitive(rs.getString("EVENTID")));
/* 243 */           eventLogRow.add(new JsonPrimitive(rs.getString("DESCRIPTION")));
/* 244 */           eventLogRow.add(new JsonPrimitive(rs.getString("USERNAME")));
/*     */           
/* 246 */           String descStr = rs.getString("DESCRIPTIONSTRING");
/* 247 */           String descStrTrimmed = "";
/*     */           try
/*     */           {
/* 250 */             descStrTrimmed = descStr.substring(0, 8);
/* 251 */             descStrTrimmed = descStrTrimmed + "....";
/*     */           }
/*     */           catch (IndexOutOfBoundsException ex)
/*     */           {
/* 255 */             descStrTrimmed = descStr;
/*     */           }
/* 257 */           eventLogRow.add(new JsonPrimitive(descStrTrimmed));
/* 258 */           String dateTime = rs.getString("EVENTGENERATEDTIME");
/*     */           try
/*     */           {
/* 261 */             Date dateObj = new Date(Long.valueOf(dateTime).longValue());
/* 262 */             String formatted = eventLogDateFormat.format(dateObj);
/* 263 */             eventLogRow.add(new JsonPrimitive(formatted));
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 267 */             eventLogRow.add(new JsonPrimitive("-1"));
/*     */           }
/* 269 */           eventLogRow.add(new JsonPrimitive(""));
/*     */           
/* 271 */           eventLogData.add(eventLogRow);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 278 */       AMLog.debug("Exception in getting Eventlog data ", ex);
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 283 */       AMConnectionPool.closeResultSet(rs);
/*     */     }
/* 285 */     AMLog.debug("MSCRM Eventlog data array " + eventLogData);
/*     */     
/* 287 */     return eventLogData;
/*     */   }
/*     */   
/*     */   public static String getResourceTypeName()
/*     */   {
/* 292 */     return resourceType;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\me\apm\msdynamics\crm\util\MSDynamicsCRMUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */