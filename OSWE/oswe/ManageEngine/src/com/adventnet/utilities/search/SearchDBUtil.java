/*     */ package com.adventnet.utilities.search;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class SearchDBUtil
/*     */ {
/*     */   public static final int TYPE_1_KEYWORD = 1;
/*     */   public static final int TYPE_3_KEYWORD = 3;
/*     */   
/*     */   public static Map getResultHandlers() throws Exception
/*     */   {
/*  18 */     AMConnectionPool cp = getConnectionPool();
/*  19 */     String sql = "select AM_SearchTables.tablename 'tablename',AM_SearchResultsHandler.classname from AM_SearchResultsHandler, AM_SearchTables where  AM_SearchTables.tableid=AM_SearchResultsHandler.tableid";
/*     */     
/*  21 */     ResultSet rs = AMConnectionPool.executeQueryStmt(sql);
/*  22 */     Map map = new HashMap();
/*  23 */     while (rs.next()) {
/*  24 */       map.put(rs.getString("tablename"), rs.getString("classname"));
/*     */     }
/*  26 */     rs.close();
/*  27 */     return map;
/*     */   }
/*     */   
/*     */   public static List getKeywordsType1()
/*     */     throws Exception
/*     */   {
/*  33 */     AMConnectionPool cp = getConnectionPool();
/*  34 */     List retList = new java.util.ArrayList();
/*  35 */     String sql = "select DISTINCT  AM_SearchKeywordsColumnsMapper.keywordid,  AM_SearchKeywords.keyword,  AM_SearchKeywords.type from  AM_SearchKeywords, AM_SearchKeywordsColumnsMapper where AM_SearchKeywordsColumnsMapper.keywordid = AM_SearchKeywords.id and  AM_SearchKeywords.type =1";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  40 */     ResultSet rs = AMConnectionPool.executeQueryStmt(sql);
/*  41 */     Map map = new HashMap();
/*  42 */     while (rs.next()) {
/*  43 */       retList.add(rs.getString("keyword"));
/*     */     }
/*  45 */     rs.close();
/*  46 */     return retList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static List getKeywordsType3()
/*     */     throws Exception
/*     */   {
/*  58 */     AMConnectionPool cp = getConnectionPool();
/*  59 */     List retList = new java.util.ArrayList();
/*     */     
/*  61 */     String sql = "select DISTINCT  AM_SearchKeywordsColumnsMapper.keywordid,  AM_SearchKeywords.type,  keyword from  AM_SearchKeywords, AM_SearchKeywordsColumnsMapper where AM_SearchKeywordsColumnsMapper.keywordid = AM_SearchKeywords.id and AM_SearchKeywords.type=3";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  67 */     ResultSet rs = AMConnectionPool.executeQueryStmt(sql);
/*  68 */     Map map = new HashMap();
/*  69 */     while (rs.next()) {
/*  70 */       retList.add(rs.getString("keyword"));
/*     */     }
/*  72 */     rs.close();
/*  73 */     return retList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AMConnectionPool getConnectionPool()
/*     */   {
/*  85 */     return AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Map getKeywordAliasMap(Set type3Keywords, String tname)
/*     */     throws Exception
/*     */   {
/*  96 */     AMConnectionPool cp = getConnectionPool();
/*  97 */     Map keyAlias = new HashMap();
/*  98 */     String kywrds = convertToString(type3Keywords.toArray());
/*     */     
/* 100 */     String sql = "select AM_SearchKeywordActualValue.value 'alias', AM_SearchKeywordActualValue.id,AM_SearchKeywords.id,AM_SearchKeywords.keyword 'keyword' from AM_SearchKeywords,AM_SearchKeywordActualValue where AM_SearchKeywords.keyword  in (" + kywrds + " ) and AM_SearchKeywordActualValue.keywordid = AM_SearchKeywords.id";
/*     */     
/*     */ 
/*     */ 
/* 104 */     ResultSet rs = AMConnectionPool.executeQueryStmt(sql);
/* 105 */     String keyword = "";
/* 106 */     Set aliasSet = null;
/* 107 */     while (rs.next()) {
/* 108 */       keyword = rs.getString("keyword");
/* 109 */       aliasSet = (Set)keyAlias.get(keyword);
/* 110 */       if (aliasSet == null) {
/* 111 */         aliasSet = new java.util.HashSet();
/* 112 */         keyAlias.put(keyword, aliasSet);
/*     */       }
/* 114 */       aliasSet.add(rs.getObject("alias"));
/*     */     }
/*     */     
/* 117 */     rs.close();
/* 118 */     return keyAlias;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String convertToString(Object[] sq)
/*     */   {
/* 128 */     String inClauseExtension = "";
/* 129 */     for (int i = 0; i < sq.length; i++)
/*     */     {
/* 131 */       inClauseExtension = inClauseExtension + "'" + sq[i] + "'";
/* 132 */       if (i < sq.length - 1) {
/* 133 */         inClauseExtension = inClauseExtension + ",";
/*     */       }
/*     */     }
/* 136 */     return inClauseExtension;
/*     */   }
/*     */   
/*     */   public static List getPKs(String tname) throws Exception {
/* 140 */     return updatePKs(tname, new java.util.ArrayList());
/*     */   }
/*     */   
/*     */ 
/*     */   public static List updatePKs(String tname, List retLS)
/*     */     throws Exception
/*     */   {
/* 147 */     AMConnectionPool cp = getConnectionPool();
/* 148 */     java.sql.DatabaseMetaData dbmd = AMConnectionPool.getMetaData();
/* 149 */     ResultSet rs = dbmd.getPrimaryKeys(null, null, tname);
/* 150 */     while (rs.next()) {
/* 151 */       retLS.add(rs.getString("COLUMN_NAME"));
/*     */     }
/* 153 */     rs.close();
/* 154 */     return retLS;
/*     */   }
/*     */   
/*     */   public static String commaSeparatedPKsForSQLQuery(Collection collect) throws Exception {
/* 158 */     String pkCols = "";
/*     */     
/* 160 */     Object[] arr = collect.toArray();
/* 161 */     int size = arr.length;
/* 162 */     for (int i = 0; i < size; i++)
/*     */     {
/* 164 */       if (arr[i].toString().equalsIgnoreCase("COLLECTIONTIME")) {
/* 165 */         pkCols = pkCols + " max(" + arr[i] + ") 'COLLECTIONTIME'";
/*     */       } else {
/* 167 */         pkCols = pkCols + arr[i];
/*     */       }
/*     */       
/* 170 */       if (i < size - 1) {
/* 171 */         pkCols = pkCols + ",";
/*     */       }
/*     */     }
/* 174 */     return pkCols;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Collection cloneCollection(Collection col)
/*     */     throws Exception
/*     */   {
/* 185 */     Collection retSet = (Collection)col.getClass().newInstance();
/* 186 */     java.util.Iterator it = col.iterator();
/* 187 */     while (it.hasNext()) {
/* 188 */       retSet.add(it.next());
/*     */     }
/* 190 */     return retSet;
/*     */   }
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
/*     */   public static String findReplace(String str, String find, String replace)
/*     */   {
/* 207 */     if ((str == null) || (find == null) || (replace == null)) { return null;
/*     */     }
/* 209 */     String des = new String();
/* 210 */     while (str.indexOf(find) != -1) {
/* 211 */       des = des + str.substring(0, str.indexOf(find));
/* 212 */       des = des + replace;
/* 213 */       str = str.substring(str.indexOf(find) + find.length());
/*     */     }
/* 215 */     des = des + str;
/* 216 */     return des;
/*     */   }
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
/*     */   public static String getWhereLikeClause(String colName, String[] arr)
/*     */   {
/* 230 */     if (arr.length == 0) {
/* 231 */       return "";
/*     */     }
/*     */     
/* 234 */     String retStr = " (";
/* 235 */     int size = arr.length;
/* 236 */     for (int i = 0; i < size; i++) {
/* 237 */       retStr = retStr + colName + " LIKE '%" + arr[i] + "%' ";
/* 238 */       if (i < arr.length - 1) {
/* 239 */         retStr = retStr + " OR ";
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 244 */     retStr = retStr + ")";
/*     */     
/* 246 */     return retStr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void main(String[] arg)
/*     */     throws Exception
/*     */   {
/* 254 */     System.out.println(getWhereLikeClause("KEYWORD", new String[] { "A", "B", "RRR" }));
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\utilities\search\SearchDBUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */