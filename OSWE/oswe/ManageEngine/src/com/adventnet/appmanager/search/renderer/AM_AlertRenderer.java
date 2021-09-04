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
/*     */ public class AM_AlertRenderer
/*     */   implements RendererMeta
/*     */ {
/*     */   public TableStructure render(SearchResult sr)
/*     */     throws Exception
/*     */   {
/*  31 */     TableStructure ts = new TableStructure();
/*  32 */     ts.setTitle("Alerts");
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
/*     */ 
/*  46 */     List columnNames = ts.getColumnNames();
/*  47 */     columnNames.add("Monitor");
/*     */     
/*  49 */     columnNames.add("Message");
/*  50 */     columnNames.add("Severity");
/*  51 */     columnNames.add("Last Updated");
/*     */     
/*  53 */     AMConnectionPool cp = SearchDBUtil.getConnectionPool();
/*  54 */     ResultSet rs = null;
/*  55 */     Map pkVals = sr.getPrimaryKeyValues();
/*  56 */     List pkCT = (List)pkVals.get("ENTITY");
/*     */     
/*  58 */     String pkCTStr = SearchDBUtil.convertToString(pkCT.toArray());
/*  59 */     String sql = "Select SOURCE, ENTITY, AM_ManagedObject.DISPLAYNAME, AM_ATTRIBUTES.DISPLAYNAME 'ATTRIBUTE', CATEGORY,SEVERITY,MODTIME, MMESSAGE  from Alert, AM_ManagedObject,  AM_ATTRIBUTES where Alert.GROUPNAME='AppManager' and AM_ManagedObject.RESOURCEID = Alert.SOURCE AND AM_ATTRIBUTES.ATTRIBUTEID = Alert.CATEGORY  AND ENTITY IN ( " + pkCTStr + ")";
/*     */     
/*     */ 
/*  62 */     rs = AMConnectionPool.executeQueryStmt(sql);
/*     */     
/*  64 */     while (rs.next()) {
/*  65 */       RowEntry re = new RowEntry();
/*  66 */       re.getColumnValues().add(FormatUtil.getTrimmedText((String)rs.getObject("DISPLAYNAME"), 40));
/*  67 */       re.getColumnValueLinks().add("");
/*  68 */       re.getOnClickLinks().add("");
/*     */       
/*     */ 
/*     */ 
/*  72 */       re.getColumnValues().add(FormatUtil.getTruncatedAlertMessage(rs.getObject("MMESSAGE").toString()));
/*  73 */       String link = "/fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=" + rs.getObject("ENTITY") + "&source=" + rs.getObject("DISPLAYNAME") + "&category=" + rs.getObject("CATEGORY");
/*     */       
/*  75 */       re.getColumnValueLinks().add(link);
/*  76 */       re.getOnClickLinks().add("");
/*     */       
/*     */ 
/*     */ 
/*  80 */       String health = rs.getObject("SEVERITY").toString();
/*  81 */       if (health.equals("1")) {
/*  82 */         re.getColumnValues().add("Critical");
/*  83 */       } else if (health.equals("4")) {
/*  84 */         re.getColumnValues().add("Warning");
/*  85 */       } else if (health.equals("5")) {
/*  86 */         re.getColumnValues().add("Clear");
/*  87 */       } else if (health.equals("-1")) {
/*  88 */         re.getColumnValues().add("Unknown");
/*     */       } else {
/*  90 */         re.getColumnValues().add(health);
/*     */       }
/*  92 */       re.getColumnValueLinks().add("");
/*  93 */       re.getOnClickLinks().add("");
/*     */       
/*     */ 
/*  96 */       String date = rs.getObject("MODTIME").toString();
/*  97 */       re.getColumnValues().add(FormatUtil.formatDT(String.valueOf(Long.parseLong(date))));
/*  98 */       re.getColumnValueLinks().add("");
/*  99 */       re.getOnClickLinks().add("");
/* 100 */       ts.getRowEntry().add(re);
/*     */     }
/*     */     
/*     */ 
/* 104 */     rs.close();
/*     */     
/*     */ 
/* 107 */     return ts;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\search\renderer\AM_AlertRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */