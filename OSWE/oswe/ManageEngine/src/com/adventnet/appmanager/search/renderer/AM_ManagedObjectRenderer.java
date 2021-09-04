/*     */ package com.adventnet.appmanager.search.renderer;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.utilities.search.SearchDBUtil;
/*     */ import com.adventnet.utilities.search.SearchResult;
/*     */ import com.adventnet.utilities.search.renderer.RendererMeta;
/*     */ import com.adventnet.utilities.search.renderer.RowEntry;
/*     */ import com.adventnet.utilities.search.renderer.TableStructure;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class AM_ManagedObjectRenderer
/*     */   implements RendererMeta
/*     */ {
/*     */   public TableStructure render(SearchResult sr)
/*     */     throws Exception
/*     */   {
/*  31 */     TableStructure ts = new TableStructure();
/*  32 */     ts.setTitle("Monitors");
/*  33 */     ts.setDescription("");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  39 */     ts.setResultsPresent(sr.hasAnyPKValues());
/*  40 */     if (!sr.hasAnyPKValues()) {
/*  41 */       return ts;
/*     */     }
/*     */     
/*     */ 
/*  45 */     List columnNames = ts.getColumnNames();
/*     */     
/*  47 */     columnNames.add("Display Name");
/*  48 */     columnNames.add("Type");
/*  49 */     columnNames.add("Alerts");
/*     */     
/*     */ 
/*  52 */     AMConnectionPool cp = SearchDBUtil.getConnectionPool();
/*  53 */     ResultSet rs = null;
/*  54 */     Map pkVals = sr.getPrimaryKeyValues();
/*     */     
/*  56 */     List pkRESID = (List)pkVals.get("RESOURCEID");
/*  57 */     String inClause = SearchDBUtil.convertToString(pkRESID.toArray());
/*     */     
/*  59 */     Map mapOfAlerts = new HashMap();
/*  60 */     String alertsSQL = "select AM_ManagedObject.resourceid, count(Alert.SOURCE) NUMBEROFALERTS from AM_ManagedObject, Alert where GROUPNAME='AppManager' and SOURCE=resourceid  and AM_ManagedObject.RESOURCEID IN ( " + inClause + " ) group by resourceid";
/*     */     
/*     */ 
/*  63 */     rs = AMConnectionPool.executeQueryStmt(alertsSQL);
/*  64 */     while (rs.next()) {
/*  65 */       mapOfAlerts.put(rs.getString("resourceid"), FormatUtil.formatNumber(rs.getString("NUMBEROFALERTS")));
/*     */     }
/*     */     
/*  68 */     rs.close();
/*     */     
/*     */ 
/*     */ 
/*  72 */     String sql = "Select AM_ManagedObject.DISPLAYNAME 'Display Name',  RESOURCENAME, RESOURCEID,RESOURCENAME,type,AM_ManagedResourceType.DISPLAYNAME as RESOURCETYPE from  AM_ManagedObject,AM_ManagedResourceType where RESOURCETYPE=TYPE and  AM_ManagedObject.RESOURCEID IN (" + inClause + " )";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  77 */     rs = AMConnectionPool.executeQueryStmt(sql);
/*  78 */     String link = "";
/*  79 */     String onClick = "";
/*  80 */     String alertC = "";
/*  81 */     while (rs.next())
/*     */     {
/*  83 */       onClick = "";
/*  84 */       RowEntry re = new RowEntry();
/*  85 */       String type = rs.getString("TYPE");
/*  86 */       String resID = rs.getString("RESOURCEID");
/*  87 */       String resourceName = rs.getString("RESOURCENAME");
/*     */       
/*  89 */       re.getColumnValues().add(rs.getString("Display Name"));
/*     */       
/*     */ 
/*     */ 
/*  93 */       if (type.equalsIgnoreCase("HAI")) {
/*  94 */         link = "showapplication.do?haid=" + resID + "&method=showApplication&name=" + resourceName;
/*  95 */       } else if ((!type.equalsIgnoreCase("Disk")) && (!type.equalsIgnoreCase("snmp-node")) && (!type.equalsIgnoreCase("EJB")) && (!type.equalsIgnoreCase("JDBC")))
/*     */       {
/*  97 */         if (type.equalsIgnoreCase("Network")) {
/*  98 */           link = "/showresource.do?resourceid=" + resID + "&type=" + type + "&resourcename=" + resourceName + "&method=showResourceTypes&moname=" + resourceName;
/*     */         } else {
/* 100 */           link = "/showresource.do?resourceid=" + resID + "&type=" + type + "&resourcename=" + resourceName + "&method=showdetails&moname=" + resourceName;
/*     */         }
/*     */       } else {
/* 103 */         link = "";
/*     */       }
/*     */       
/* 106 */       re.getColumnValueLinks().add(link);
/* 107 */       re.getOnClickLinks().add("");
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
/* 123 */       re.getColumnValues().add(rs.getString("RESOURCETYPE"));
/* 124 */       re.getColumnValueLinks().add("");
/* 125 */       re.getOnClickLinks().add("");
/*     */       
/* 127 */       alertC = (String)mapOfAlerts.get(resID);
/* 128 */       if (alertC == null)
/*     */       {
/* 130 */         re.getColumnValues().add("0");
/* 131 */         re.getColumnValueLinks().add("");
/* 132 */         re.getOnClickLinks().add("");
/*     */       } else {
/* 134 */         re.getColumnValues().add(alertC);
/* 135 */         re.getColumnValueLinks().add("");
/* 136 */         re.getOnClickLinks().add("showAlarms('" + resID + "')");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 141 */       ts.getRowEntry().add(re);
/*     */     }
/*     */     
/* 144 */     rs.close();
/* 145 */     return ts;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\search\renderer\AM_ManagedObjectRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */