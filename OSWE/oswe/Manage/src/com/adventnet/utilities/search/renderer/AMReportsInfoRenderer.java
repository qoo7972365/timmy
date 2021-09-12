/*    */ package com.adventnet.utilities.search.renderer;
/*    */ 
/*    */ import com.adventnet.appmanager.db.AMConnectionPool;
/*    */ import com.adventnet.utilities.search.SearchDBUtil;
/*    */ import com.adventnet.utilities.search.SearchResult;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AMReportsInfoRenderer
/*    */   implements RendererMeta
/*    */ {
/*    */   public TableStructure render(SearchResult sr)
/*    */     throws Exception
/*    */   {
/* 29 */     TableStructure ts = new TableStructure();
/* 30 */     ts.setTitle("Reports");
/* 31 */     ts.setDescription("");
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 37 */     ts.setResultsPresent(sr.hasAnyPKValues());
/* 38 */     if (!sr.hasAnyPKValues()) {
/* 39 */       return ts;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 44 */     List columnNames = ts.getColumnNames();
/* 45 */     columnNames.add("Name");
/* 46 */     columnNames.add("Description");
/*    */     
/*    */ 
/* 49 */     AMConnectionPool cp = SearchDBUtil.getConnectionPool();
/* 50 */     ResultSet rs = null;
/* 51 */     Map pkVals = sr.getPrimaryKeyValues();
/* 52 */     List pkCT = (List)pkVals.get("ID");
/* 53 */     String pkCTStr = SearchDBUtil.convertToString(pkCT.toArray());
/*    */     
/*    */ 
/* 56 */     String sql = "SELECT AM_SearchDocLinksKeywordsMapper.DISPLAYNAME 'LINK_DISPLAYNAME', AM_SearchDocLinks.LINK 'LINK', AM_SearchDocLinks.DESCRIPTION 'DESCRIPTION'  FROM AM_SearchKeywords,  AM_SearchDocLinks, AM_SearchDocLinksKeywordsMapper  WHERE AM_SearchKeywords.id  IN ( " + pkCTStr + " ) AND AM_SearchDocLinks.LINKID = AM_SearchDocLinksKeywordsMapper.LINKID  AND AM_SearchKeywords.id = AM_SearchDocLinksKeywordsMapper.KEYWORDID";
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 63 */     rs = AMConnectionPool.executeQueryStmt(sql);
/*    */     
/* 65 */     while (rs.next()) {
/* 66 */       RowEntry re = new RowEntry();
/* 67 */       re.getColumnValues().add(rs.getObject("LINK_DISPLAYNAME"));
/* 68 */       String link = rs.getString("LINK");
/* 69 */       re.getColumnValueLinks().add(link);
/* 70 */       re.getOnClickLinks().add("");
/*    */       
/*    */ 
/* 73 */       re.getColumnValues().add(rs.getString("DESCRIPTION"));
/* 74 */       re.getColumnValueLinks().add("");
/* 75 */       re.getOnClickLinks().add("");
/*    */       
/* 77 */       ts.getRowEntry().add(re);
/*    */     }
/*    */     
/*    */ 
/* 81 */     rs.close();
/*    */     
/*    */ 
/* 84 */     return ts;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\utilities\search\renderer\AMReportsInfoRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */