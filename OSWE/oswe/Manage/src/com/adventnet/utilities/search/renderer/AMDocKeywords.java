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
/*    */ public class AMDocKeywords
/*    */   implements RendererMeta
/*    */ {
/*    */   public TableStructure render(SearchResult sr)
/*    */     throws Exception
/*    */   {
/* 29 */     TableStructure ts = new TableStructure();
/* 30 */     ts.setTitle("Help Documents");
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
/* 45 */     columnNames.add("Keyword");
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
/* 59 */     rs = AMConnectionPool.executeQueryStmt(sql);
/*    */     
/* 61 */     while (rs.next()) {
/* 62 */       RowEntry re = new RowEntry();
/* 63 */       re.getColumnValues().add(rs.getObject("LINK_DISPLAYNAME"));
/*    */       
/*    */ 
/*    */ 
/* 67 */       String link = rs.getString("LINK");
/* 68 */       link = link + "\" target=\"newwindow";
/*    */       
/*    */ 
/*    */ 
/* 72 */       re.getColumnValueLinks().add(link);
/* 73 */       re.getOnClickLinks().add("");
/*    */       
/*    */ 
/* 76 */       re.getColumnValues().add(rs.getString("DESCRIPTION"));
/* 77 */       re.getColumnValueLinks().add("");
/* 78 */       re.getOnClickLinks().add("");
/*    */       
/* 80 */       ts.getRowEntry().add(re);
/*    */     }
/*    */     
/*    */ 
/* 84 */     rs.close();
/*    */     
/*    */ 
/* 87 */     return ts;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\utilities\search\renderer\AMDocKeywords.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */