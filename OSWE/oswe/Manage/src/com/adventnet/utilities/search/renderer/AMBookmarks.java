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
/*    */ public class AMBookmarks
/*    */   implements RendererMeta
/*    */ {
/*    */   public TableStructure render(SearchResult sr)
/*    */     throws Exception
/*    */   {
/* 29 */     TableStructure ts = new TableStructure();
/* 30 */     ts.setTitle("Bookmarks");
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
/* 55 */     String sql = "SELECT AM_SearchDocLinksKeywordsMapper.DISPLAYNAME 'LINK_DISPLAYNAME', AM_SearchDocLinks.LINK 'LINK', AM_SearchDocLinks.DESCRIPTION 'DESCRIPTION'  FROM AM_SearchKeywords,  AM_SearchDocLinks, AM_SearchDocLinksKeywordsMapper  WHERE AM_SearchKeywords.id  IN ( " + pkCTStr + " ) AND AM_SearchDocLinks.LINKID = AM_SearchDocLinksKeywordsMapper.LINKID  AND AM_SearchKeywords.id = AM_SearchDocLinksKeywordsMapper.KEYWORDID";
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 60 */     rs = AMConnectionPool.executeQueryStmt(sql);
/*    */     
/* 62 */     while (rs.next()) {
/* 63 */       RowEntry re = new RowEntry();
/* 64 */       re.getColumnValues().add(rs.getObject("LINK_DISPLAYNAME"));
/* 65 */       String link = rs.getString("LINK");
/* 66 */       re.getColumnValueLinks().add(link);
/* 67 */       re.getOnClickLinks().add("");
/*    */       
/*    */ 
/* 70 */       re.getColumnValues().add(rs.getString("DESCRIPTION"));
/* 71 */       re.getColumnValueLinks().add("");
/* 72 */       re.getOnClickLinks().add("");
/*    */       
/* 74 */       ts.getRowEntry().add(re);
/*    */     }
/*    */     
/*    */ 
/* 78 */     rs.close();
/*    */     
/*    */ 
/* 81 */     return ts;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\utilities\search\renderer\AMBookmarks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */