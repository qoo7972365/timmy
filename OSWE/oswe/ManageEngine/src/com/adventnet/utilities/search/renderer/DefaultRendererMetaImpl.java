/*     */ package com.adventnet.utilities.search.renderer;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.utilities.search.SearchDBUtil;
/*     */ import com.adventnet.utilities.search.SearchResult;
/*     */ import java.sql.DatabaseMetaData;
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
/*     */ public class DefaultRendererMetaImpl
/*     */   implements RendererMeta
/*     */ {
/*     */   public TableStructure render(SearchResult sr)
/*     */     throws Exception
/*     */   {
/*  28 */     TableStructure ts = new TableStructure();
/*  29 */     ts.setTitle("Managed Resources");
/*  30 */     ts.setDescription("This search shows the managed resources that correspond to your search query.");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  37 */     ts.setResultsPresent(sr.hasAnyPKValues());
/*  38 */     if (!sr.hasAnyPKValues()) {
/*  39 */       return ts;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  44 */     AMConnectionPool cp = SearchDBUtil.getConnectionPool();
/*  45 */     DatabaseMetaData dbmd = AMConnectionPool.getMetaData();
/*  46 */     ResultSet rs = dbmd.getColumns(null, null, sr.getTableName(), null);
/*  47 */     List columnNames = ts.getColumnNames();
/*     */     
/*     */ 
/*     */ 
/*  51 */     while (rs.next()) {
/*  52 */       columnNames.add(rs.getString("COLUMN_NAME"));
/*     */     }
/*     */     
/*  55 */     rs.close();
/*  56 */     Map pkVals = sr.getPrimaryKeyValues();
/*  57 */     List pk = sr.getPrimaryKey();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  64 */     String sql = "Select * from " + sr.getTableName() + " where ";
/*     */     
/*  66 */     String tmpSQL = "";
/*  67 */     int size = pk.size();
/*  68 */     for (int i = 0; i < size; i++)
/*     */     {
/*  70 */       List specificPKValues = (List)pkVals.get(pk.get(i));
/*     */       
/*  72 */       int size1 = specificPKValues.size();
/*     */       
/*  74 */       if (size1 > 0) {
/*  75 */         tmpSQL = (String)pk.get(i) + " IN ( ";
/*     */       }
/*  77 */       for (int j = 0; j < size1; j++) {
/*  78 */         tmpSQL = tmpSQL + " '" + specificPKValues.get(j) + "'";
/*  79 */         if (j < size1 - 1) {
/*  80 */           tmpSQL = tmpSQL + ",";
/*     */         }
/*     */       }
/*     */       
/*  84 */       tmpSQL = tmpSQL + ")";
/*  85 */       if (i < size - 1) {
/*  86 */         tmpSQL = tmpSQL + " OR ";
/*     */       }
/*     */     }
/*     */     
/*  90 */     String outputSQL = sql + tmpSQL;
/*  91 */     rs = AMConnectionPool.executeQueryStmt(outputSQL);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  97 */     List retLS = ts.getRowEntry();
/*  98 */     while (rs.next()) {
/*  99 */       RowEntry re = new RowEntry();
/*     */       
/* 101 */       size = columnNames.size();
/* 102 */       for (int i = 0; i < size; i++) {
/* 103 */         re.getColumnValues().add(rs.getObject((String)columnNames.get(i)));
/* 104 */         re.getColumnValueLinks().add("");
/* 105 */         re.getOnClickLinks().add("");
/*     */       }
/*     */       
/*     */ 
/* 109 */       retLS.add(re);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 114 */     return ts;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\utilities\search\renderer\DefaultRendererMetaImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */