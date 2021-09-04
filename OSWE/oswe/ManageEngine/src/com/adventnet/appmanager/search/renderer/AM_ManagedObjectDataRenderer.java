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
/*     */ 
/*     */ public class AM_ManagedObjectDataRenderer
/*     */   implements RendererMeta
/*     */ {
/*     */   public TableStructure render(SearchResult sr)
/*     */     throws Exception
/*     */   {
/*  31 */     TableStructure ts = new TableStructure();
/*  32 */     ts.setTitle("Availability and Health");
/*  33 */     ts.setDescription("");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  40 */     ts.setResultsPresent(sr.hasAnyPKValues());
/*  41 */     if (!sr.hasAnyPKValues()) {
/*  42 */       return ts;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  47 */     List columnNames = ts.getColumnNames();
/*     */     
/*  49 */     columnNames.add("Display Name");
/*     */     
/*  51 */     columnNames.add("Availability");
/*  52 */     columnNames.add("Health");
/*  53 */     columnNames.add("Last Updated");
/*     */     
/*  55 */     AMConnectionPool cp = SearchDBUtil.getConnectionPool();
/*  56 */     ResultSet rs = null;
/*  57 */     Map pkVals = sr.getPrimaryKeyValues();
/*  58 */     List pkCT = (List)pkVals.get("COLLECTIONTIME");
/*  59 */     List pkRESID = (List)pkVals.get("RESID");
/*     */     
/*     */ 
/*  62 */     int size = pkRESID.size();
/*  63 */     for (int i = 0; i < size; i++)
/*     */     {
/*  65 */       String sql = "Select AM_ManagedObject.DISPLAYNAME 'Display Name',  RESID, COLLECTIONTIME, AVAILABILITY, HEALTH, AM_ManagedObject.TYPE 'TYPE',AM_ManagedObject.RESOURCENAME from AM_ManagedObjectData, AM_ManagedObject where  AM_ManagedObject.RESOURCEID=AM_ManagedObjectData.RESID and  RESID =" + pkRESID.get(i).toString() + " and COLLECTIONTIME=" + pkCT.get(i);
/*     */       
/*  67 */       rs = AMConnectionPool.executeQueryStmt(sql);
/*  68 */       RowEntry re = new RowEntry();
/*  69 */       String link = "";
/*  70 */       String resourceName = "";
/*  71 */       while (rs.next()) {
/*  72 */         String type = rs.getString("TYPE");
/*  73 */         String resID = rs.getString("RESID");
/*  74 */         resourceName = rs.getString("RESOURCENAME");
/*     */         
/*  76 */         re.getColumnValues().add(FormatUtil.getTrimmedText(rs.getString("Display Name"), 60));
/*     */         
/*  78 */         if (type.equalsIgnoreCase("HAI")) {
/*  79 */           link = "showapplication.do?haid=" + resID + "&method=showApplication&name=" + resourceName;
/*  80 */         } else if ((!type.equalsIgnoreCase("Disk")) && (!type.equalsIgnoreCase("snmp-node")) && (!type.equalsIgnoreCase("EJB")) && (!type.equalsIgnoreCase("JDBC")))
/*     */         {
/*     */ 
/*  83 */           link = "/showresource.do?resourceid=" + resID + "&type=" + type + "&resourcename=" + resourceName + "&method=showdetails&moname=" + resourceName;
/*     */         }
/*     */         else {
/*  86 */           link = "";
/*     */         }
/*     */         
/*     */ 
/*  90 */         re.getColumnValueLinks().add(link);
/*  91 */         re.getOnClickLinks().add("");
/*     */         
/*  93 */         long value = rs.getLong("AVAILABILITY");
/*  94 */         if (value == 1L) {
/*  95 */           re.getColumnValues().add("Up");
/*  96 */         } else if (value == 0L) {
/*  97 */           re.getColumnValues().add("Down");
/*     */         } else {
/*  99 */           re.getColumnValues().add("Unknown");
/*     */         }
/* 101 */         re.getColumnValueLinks().add("");
/* 102 */         re.getOnClickLinks().add("");
/*     */         
/* 104 */         String health = rs.getObject("HEALTH").toString();
/* 105 */         if (health.equals("1")) {
/* 106 */           re.getColumnValues().add("Critical");
/* 107 */         } else if (health.equals("4")) {
/* 108 */           re.getColumnValues().add("Warning");
/* 109 */         } else if (health.equals("5")) {
/* 110 */           re.getColumnValues().add("Clear");
/* 111 */         } else if (health.equals("-1")) {
/* 112 */           re.getColumnValues().add("Unknown");
/*     */         } else {
/* 114 */           re.getColumnValues().add(health);
/*     */         }
/* 116 */         re.getColumnValueLinks().add("");
/* 117 */         re.getOnClickLinks().add("");
/*     */         
/* 119 */         String date = rs.getObject("COLLECTIONTIME").toString();
/*     */         
/* 121 */         re.getColumnValues().add(FormatUtil.formatDT(String.valueOf(Long.parseLong(date))));
/* 122 */         re.getColumnValueLinks().add("");
/* 123 */         re.getOnClickLinks().add("");
/*     */       }
/*     */       
/* 126 */       if (re.getColumnValues().size() > 0) {
/* 127 */         ts.getRowEntry().add(re);
/*     */       }
/* 129 */       rs.close();
/*     */     }
/*     */     
/*     */ 
/* 133 */     return ts;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\search\renderer\AM_ManagedObjectDataRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */