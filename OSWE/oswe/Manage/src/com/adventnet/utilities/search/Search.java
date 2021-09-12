/*     */ package com.adventnet.utilities.search;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Search
/*     */ {
/*     */   private String[] origSQ;
/*  23 */   private static AMConnectionPool cp = null;
/*     */   
/*     */   public Search(String[] sq)
/*     */   {
/*  27 */     if (cp == null) {
/*  28 */       cp = SearchDBUtil.getConnectionPool();
/*     */     }
/*     */     
/*  31 */     this.origSQ = sq;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List start()
/*     */     throws Exception
/*     */   {
/*  40 */     List al = search();
/*  41 */     return al;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private List search()
/*     */     throws Exception
/*     */   {
/*  50 */     long start = System.currentTimeMillis();
/*  51 */     String[] filteredSQ = filter(this.origSQ);
/*  52 */     List results1 = type1Search(filteredSQ);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  57 */     if (results1.size() == 0) {
/*  58 */       results1 = type2Search(filteredSQ);
/*     */     }
/*     */     
/*     */ 
/*  62 */     List t3Results = type3Search(filteredSQ);
/*  63 */     int size = t3Results.size();
/*  64 */     for (int i = 0; i < size; i++) {
/*  65 */       results1.add(t3Results.get(i));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  70 */     List t4Results = type4Search(filteredSQ);
/*  71 */     size = t4Results.size();
/*  72 */     for (int i = 0; i < size; i++) {
/*  73 */       results1.add(t4Results.get(i));
/*     */     }
/*  75 */     List t5Results = type5Search(filteredSQ);
/*  76 */     size = t5Results.size();
/*  77 */     for (int i = 0; i < size; i++) {
/*  78 */       results1.add(t5Results.get(i));
/*     */     }
/*     */     
/*  81 */     List t6Results = type6Search(filteredSQ);
/*  82 */     size = t6Results.size();
/*  83 */     for (int i = 0; i < size; i++) {
/*  84 */       results1.add(t6Results.get(i));
/*     */     }
/*     */     
/*     */ 
/*  88 */     AMLog.debug("Search : Total Time for search : " + (System.currentTimeMillis() - start) + " ms.");
/*     */     
/*     */ 
/*  91 */     return results1;
/*     */   }
/*     */   
/*     */   private String[] filter(String[] sq)
/*     */     throws Exception
/*     */   {
/*  97 */     removeSpecialCharacters(sq);
/*  98 */     return sq;
/*     */   }
/*     */   
/*     */   private String[] removeSpecialCharacters(String[] sq) throws Exception {
/* 102 */     int size = sq.length;
/* 103 */     for (int i = 0; i < size; i++) {
/* 104 */       sq[i] = SearchDBUtil.findReplace(sq[i], "'", "");
/* 105 */       sq[i] = SearchDBUtil.findReplace(sq[i], "\\", "");
/*     */     }
/*     */     
/*     */ 
/* 109 */     return sq;
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
/*     */   private List type1Search(String[] sq)
/*     */     throws Exception
/*     */   {
/* 124 */     if (sq.length == 0) {
/* 125 */       return new ArrayList();
/*     */     }
/*     */     
/* 128 */     String inClauseExtension = "";
/* 129 */     for (int i = 0; i < sq.length; i++)
/*     */     {
/* 131 */       inClauseExtension = inClauseExtension + "'" + sq[i] + "'";
/* 132 */       if (i < sq.length - 1) {
/* 133 */         inClauseExtension = inClauseExtension + ",";
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 143 */     String sql = "select AM_SearchKeywords.keyword 'keyword', AM_SearchKeywords.type, AM_SearchKeywords.id 'keywordid', AM_SearchTables.tablename, AM_SearchColumns.columnname 'columnname', AM_SearchColumns.columnid, AM_SearchTables.tableid, AM_SearchKeywordsColumnsMapper.columnid from AM_SearchKeywords, AM_SearchColumns, AM_SearchTables, AM_SearchKeywordsColumnsMapper where AM_SearchKeywords.keyword in (" + inClauseExtension + ")  and AM_SearchKeywords.type =" + 1 + " and AM_SearchKeywords.id =  AM_SearchKeywordsColumnsMapper.keywordid and " + "AM_SearchKeywordsColumnsMapper.columnid = AM_SearchColumns.columnid and " + "AM_SearchColumns.tableid = AM_SearchTables.tableid";
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
/* 154 */     ResultSet rs = AMConnectionPool.executeQueryStmt(sql);
/*     */     
/* 156 */     ArrayList results1 = new ArrayList();
/* 157 */     Set keywords = new HashSet();
/* 158 */     ArrayList tables = new ArrayList();
/* 159 */     SearchResult sr = null;
/* 160 */     String tmpStr = null;
/* 161 */     while (rs.next()) {
/* 162 */       String tname = String.valueOf(rs.getString("tablename"));
/*     */       
/* 164 */       sr = null;
/* 165 */       SearchResult tmpSR = null;
/* 166 */       for (int i = 0; i < results1.size(); i++)
/*     */       {
/*     */ 
/* 169 */         tmpSR = (SearchResult)results1.get(i);
/* 170 */         if ((tname != null) && (tname.equals(tmpSR.getTableName()))) {
/* 171 */           sr = tmpSR;
/* 172 */           break;
/*     */         }
/*     */       }
/* 175 */       if (sr == null) {
/* 176 */         sr = new SearchResult();
/* 177 */         sr.setTableName(tname);
/* 178 */         tables.add(tname);
/* 179 */         results1.add(sr);
/*     */       }
/*     */       
/*     */ 
/* 183 */       sr.getKeywords().add(rs.getString("keyword"));
/* 184 */       keywords.add(rs.getString("keyword"));
/* 185 */       sr.getColumnNames().add(rs.getString("columnname"));
/*     */     }
/*     */     
/* 188 */     rs.close();
/*     */     
/*     */ 
/* 191 */     String[] sqHereAfter = stripKeywordsFromSearchQuery(sq, keywords);
/* 192 */     if (sqHereAfter.length == 0)
/*     */     {
/* 194 */       results1.clear();
/*     */       
/* 196 */       return results1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 201 */     String tmpTName = null;
/*     */     
/* 203 */     String tmpSQL3 = "";
/* 204 */     sr = null;
/*     */     
/* 206 */     for (int i = 0; i < results1.size(); i++) {
/* 207 */       sr = (SearchResult)results1.get(i);
/* 208 */       Object[] cols = sr.getColumnNames().toArray();
/* 209 */       if (cols.length != 0)
/*     */       {
/*     */ 
/* 212 */         String colStr = "";
/* 213 */         for (int j = 0; j < cols.length; j++) {
/* 214 */           if (j == 0) {
/* 215 */             tmpSQL3 = " where ";
/*     */           }
/* 217 */           colStr = (String)cols[j];
/*     */           
/*     */ 
/*     */ 
/* 221 */           for (int k = 0; k < sqHereAfter.length; k++) {
/* 222 */             tmpSQL3 = tmpSQL3 + colStr + " LIKE '%" + sqHereAfter[k] + "%'";
/* 223 */             if (k < sqHereAfter.length - 1) {
/* 224 */               tmpSQL3 = tmpSQL3 + " or ";
/*     */             }
/*     */           }
/*     */           
/* 228 */           if (j < cols.length - 1) {
/* 229 */             tmpSQL3 = tmpSQL3 + " or ";
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 240 */         String execSQL = "select " + commaSeparatedPKs(sr) + " from " + sr.getTableName() + tmpSQL3;
/* 241 */         updatePKValuesT1(execSQL, sr);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 247 */     return results1;
/*     */   }
/*     */   
/*     */   private String commaSeparatedPKs(SearchResult sr) throws Exception
/*     */   {
/* 252 */     String pkCols = "";
/* 253 */     List ls = updatePKs(sr);
/* 254 */     int size = ls.size();
/* 255 */     for (int i = 0; i < size; i++) {
/* 256 */       pkCols = pkCols + ls.get(i);
/*     */       
/* 258 */       if (i < size - 1) {
/* 259 */         pkCols = pkCols + ",";
/*     */       }
/*     */     }
/*     */     
/* 263 */     return pkCols;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private List updatePKs(SearchResult sr)
/*     */     throws Exception
/*     */   {
/* 272 */     List retLS = sr.getPrimaryKey();
/* 273 */     retLS.clear();
/* 274 */     DatabaseMetaData dbmd = AMConnectionPool.getMetaData();
/* 275 */     ResultSet rs = dbmd.getPrimaryKeys(null, null, sr.getTableName());
/* 276 */     while (rs.next()) {
/* 277 */       retLS.add(rs.getString("COLUMN_NAME"));
/*     */     }
/* 279 */     rs.close();
/* 280 */     return retLS;
/*     */   }
/*     */   
/*     */   private void updatePKValuesT1(String sql, SearchResult sr) throws Exception {
/* 284 */     ResultSet rs = AMConnectionPool.executeQueryStmt(sql);
/* 285 */     Map map = sr.getPrimaryKeyValues();
/* 286 */     List pks = sr.getPrimaryKey();
/* 287 */     List[] arrPK = new ArrayList[pks.size()];
/* 288 */     int size = pks.size();
/* 289 */     for (int i = 0; i < size; i++) {
/* 290 */       arrPK[i] = new ArrayList();
/*     */     }
/*     */     
/*     */ 
/* 294 */     while (rs.next()) {
/* 295 */       size = pks.size();
/* 296 */       for (int i = 0; i < size; i++) {
/* 297 */         arrPK[i].add(rs.getObject((String)pks.get(i)));
/* 298 */         map.put(pks.get(i), arrPK[i]);
/*     */       }
/*     */     }
/* 301 */     rs.close();
/*     */   }
/*     */   
/* 304 */   public String[] stripKeywordsFromSearchQuery(String[] sq, Set keywords) { ArrayList al = new ArrayList(sq.length);
/* 305 */     for (int i = 0; i < sq.length; i++) {
/* 306 */       if (!keywords.contains(sq[i])) {
/* 307 */         al.add(sq[i]);
/*     */       }
/*     */     }
/* 310 */     return (String[])al.toArray(new String[0]);
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
/*     */   private List type2Search(String[] sq)
/*     */     throws Exception
/*     */   {
/* 326 */     List keywords = SearchDBUtil.getKeywordsType1();
/*     */     
/* 328 */     if (keywords.size() == 0) {
/* 329 */       return new ArrayList();
/*     */     }
/*     */     
/*     */ 
/* 333 */     int size = sq.length;
/*     */     
/* 335 */     for (int i = 0; i < size; i++) {
/* 336 */       keywords.add(sq[i]);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 341 */     size = keywords.size();
/* 342 */     String[] newGS = new String[size];
/* 343 */     for (int i = 0; i < size; i++) {
/* 344 */       newGS[i] = ((String)keywords.get(i));
/*     */     }
/*     */     
/* 347 */     return type1Search(newGS);
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
/*     */   private List type3Search(String[] sq)
/*     */     throws Exception
/*     */   {
/* 361 */     if (sq.length == 0) {
/* 362 */       return new ArrayList();
/*     */     }
/* 364 */     String inClauseExtension = SearchDBUtil.convertToString(sq);
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
/* 378 */     String sql = "select AM_SearchKeywords.keyword 'keyword', AM_SearchKeywords.type, AM_SearchKeywords.id 'keywordid', AM_SearchTables.tablename, AM_SearchColumns.columnname 'columnname', AM_SearchColumns.columnid, AM_SearchTables.tableid, AM_SearchKeywordsColumnsMapper.columnid from AM_SearchKeywords, AM_SearchColumns, AM_SearchTables, AM_SearchKeywordsColumnsMapper where AM_SearchKeywords.keyword in (" + inClauseExtension + ") and AM_SearchKeywords.type = " + 3 + " and AM_SearchKeywords.id =  AM_SearchKeywordsColumnsMapper.keywordid and " + "AM_SearchKeywordsColumnsMapper.columnid = AM_SearchColumns.columnid and " + "AM_SearchColumns.tableid = AM_SearchTables.tableid";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 387 */     ResultSet rs = AMConnectionPool.executeQueryStmt(sql);
/* 388 */     List resultsT3 = new ArrayList();
/* 389 */     Set type3Keywords = new HashSet();
/* 390 */     List tables = new ArrayList();
/* 391 */     SearchResult sr = null;
/* 392 */     String tmpStr = null;
/* 393 */     while (rs.next()) {
/* 394 */       String tname = String.valueOf(rs.getString("tablename"));
/*     */       
/* 396 */       sr = null;
/* 397 */       SearchResult tmpSR = null;
/* 398 */       for (int i = 0; i < resultsT3.size(); i++)
/*     */       {
/*     */ 
/* 401 */         tmpSR = (SearchResult)resultsT3.get(i);
/* 402 */         if ((tname != null) && (tname.equals(tmpSR.getTableName()))) {
/* 403 */           sr = tmpSR;
/* 404 */           break;
/*     */         }
/*     */       }
/* 407 */       if (sr == null) {
/* 408 */         sr = new SearchResult();
/* 409 */         sr.setTableName(tname);
/* 410 */         tables.add(tname);
/* 411 */         resultsT3.add(sr);
/*     */       }
/*     */       
/*     */ 
/* 415 */       sr.getKeywords().add(rs.getString("keyword"));
/* 416 */       type3Keywords.add(rs.getString("keyword"));
/* 417 */       sr.getColumnNames().add(rs.getString("columnname"));
/*     */       
/* 419 */       SearchDBUtil.updatePKs(sr.getTableName(), sr.getPrimaryKey());
/*     */     }
/* 421 */     rs.close();
/* 422 */     if (resultsT3.size() == 0) {
/* 423 */       return resultsT3;
/*     */     }
/*     */     
/* 426 */     updatePKValuesT3(resultsT3, type3Keywords);
/* 427 */     return resultsT3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void updatePKValuesT3(List srList, Set type3Keywords)
/*     */     throws Exception
/*     */   {
/* 440 */     int size = srList.size();
/* 441 */     SearchResult sr = null;
/* 442 */     for (int i = 0; i < size; i++) {
/* 443 */       sr = (SearchResult)srList.get(i);
/* 444 */       Map map = sr.getPrimaryKeyValues();
/* 445 */       List pks = sr.getPrimaryKey();
/* 446 */       List[] arrPK = new ArrayList[pks.size()];
/* 447 */       for (int pkCtr = 0; pkCtr < pks.size(); pkCtr++) {
/* 448 */         arrPK[pkCtr] = new ArrayList();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 454 */       Map keywordAliasMap = SearchDBUtil.getKeywordAliasMap(type3Keywords, sr.getTableName());
/* 455 */       sr.getPrimaryKey().clear();
/* 456 */       SearchDBUtil.updatePKs(sr.getTableName(), sr.getPrimaryKey());
/*     */       
/* 458 */       String colsStr = "";
/*     */       
/* 460 */       Object[] arrCols = sr.getColumnNames().toArray();
/* 461 */       int sizej = arrCols.length;
/* 462 */       for (int j = 0; j < sizej; j++) {
/* 463 */         String colName = (String)arrCols[j];
/* 464 */         Object[] objKeywords = sr.getKeywords().toArray();
/* 465 */         String commaSepValuesOfTheAlias = "";
/* 466 */         int sizek = objKeywords.length;
/* 467 */         for (int k = 0; k < sizek; k++) {
/* 468 */           Set keyAliasSet = (Set)keywordAliasMap.get(objKeywords[k]);
/* 469 */           if (keyAliasSet != null) {
/* 470 */             commaSepValuesOfTheAlias = commaSepValuesOfTheAlias + SearchDBUtil.convertToString(keyAliasSet.toArray());
/*     */             
/* 472 */             if (k < sizek - 1) {
/* 473 */               commaSepValuesOfTheAlias = commaSepValuesOfTheAlias + " , ";
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 485 */         colsStr = colsStr + colName + " IN ( " + commaSepValuesOfTheAlias + " ) ";
/*     */         
/* 487 */         if (j < sizej - 1) {
/* 488 */           colsStr = colsStr + " and ";
/*     */         }
/*     */       }
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
/* 502 */       String pkCols = SearchDBUtil.commaSeparatedPKsForSQLQuery(sr.getPrimaryKey());
/* 503 */       String sql = "";
/*     */       
/* 505 */       String groupBy = "";
/* 506 */       if (pkCols.toUpperCase().indexOf("COLLECTIONTIME") != -1)
/*     */       {
/* 508 */         groupBy = " Group By ";
/* 509 */         List tmpKyrds = (List)SearchDBUtil.cloneCollection(sr.getPrimaryKey());
/* 510 */         tmpKyrds.remove("COLLECTIONTIME");
/* 511 */         tmpKyrds.remove("collectiontime");
/* 512 */         groupBy = groupBy + " " + SearchDBUtil.commaSeparatedPKsForSQLQuery(tmpKyrds);
/* 513 */         sql = "select " + pkCols + " from " + sr.getTableName() + " " + groupBy;
/*     */       } else {
/* 515 */         sql = "select " + pkCols + " from " + sr.getTableName() + " where " + colsStr;
/*     */       }
/*     */       
/* 518 */       ResultSet rsPKValuesToVerify = AMConnectionPool.executeQueryStmt(sql);
/* 519 */       while (rsPKValuesToVerify.next()) {
/* 520 */         String anyPKCol = (String)pks.get(0);
/* 521 */         String preColStr = " ";
/* 522 */         for (int a = 0; a < pks.size(); a++)
/*     */         {
/* 524 */           preColStr = preColStr + (String)pks.get(a) + "='" + rsPKValuesToVerify.getObject((String)pks.get(a)) + "' ";
/* 525 */           if (a < pks.size() - 1) {
/* 526 */             preColStr = preColStr + " and ";
/*     */           }
/*     */         }
/*     */         
/* 530 */         String sqlToVeirfy = "select " + anyPKCol + " from " + sr.getTableName() + " where " + preColStr + " and " + colsStr;
/*     */         
/* 532 */         Statement stmt = AMConnectionPool.query(sqlToVeirfy, true);
/* 533 */         ResultSet rsPKValuesBeingVerified = stmt.getResultSet();
/* 534 */         if (rsPKValuesBeingVerified.next())
/*     */         {
/* 536 */           int sizek = pks.size();
/* 537 */           for (int k = 0; k < sizek; k++) {
/* 538 */             arrPK[k].add(rsPKValuesToVerify.getObject((String)pks.get(k)));
/* 539 */             map.put(pks.get(k), arrPK[k]);
/*     */           }
/*     */         }
/* 542 */         stmt.close();
/*     */       }
/* 544 */       rsPKValuesToVerify.close();
/* 545 */       rsPKValuesToVerify = null;
/*     */     }
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
/*     */   private List type4Search(String[] sq)
/*     */     throws Exception
/*     */   {
/* 563 */     if (sq.length == 0) {
/* 564 */       return new ArrayList();
/*     */     }
/*     */     
/*     */ 
/* 568 */     String whereLikeClause = SearchDBUtil.getWhereLikeClause("KEYWORD", sq);
/*     */     
/* 570 */     String qry = "SELECT AM_SearchKeywords.ID FROM AM_SearchKeywords where " + whereLikeClause + " AND TYPE =4";
/*     */     
/*     */ 
/*     */ 
/* 574 */     ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 575 */     List resultsT4 = new ArrayList();
/* 576 */     SearchResult sr = new SearchResult();
/* 577 */     sr.setTableName("AM_SearchKeywords");
/* 578 */     Map pkVals = sr.getPrimaryKeyValues();
/* 579 */     List pkey = sr.getPrimaryKey();
/* 580 */     pkey.add("ID");
/*     */     
/* 582 */     List pkList = new ArrayList();
/* 583 */     pkVals.put("ID", pkList);
/* 584 */     String tmpStr = null;
/* 585 */     boolean anyResult = false;
/* 586 */     while (rs.next()) {
/* 587 */       pkList.add(rs.getString("ID"));
/* 588 */       anyResult = true;
/*     */     }
/*     */     
/* 591 */     List retList = new ArrayList();
/* 592 */     if (anyResult) {
/* 593 */       retList.add(sr);
/*     */     }
/*     */     
/* 596 */     return retList;
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
/*     */   private List type5Search(String[] sq)
/*     */     throws Exception
/*     */   {
/* 610 */     if (sq.length == 0) {
/* 611 */       return new ArrayList();
/*     */     }
/*     */     
/* 614 */     String whereLikeClause = SearchDBUtil.getWhereLikeClause("KEYWORD", sq);
/*     */     
/* 616 */     String qry = "SELECT AM_SearchKeywords.ID FROM AM_SearchKeywords where " + whereLikeClause + "  AND TYPE = 5";
/*     */     
/*     */ 
/*     */ 
/* 620 */     ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 621 */     List resultsT4 = new ArrayList();
/* 622 */     SearchResult sr = new SearchResult();
/* 623 */     sr.setTableName("AM_SearchKeywords_TYPE5");
/*     */     
/*     */ 
/* 626 */     Map pkVals = sr.getPrimaryKeyValues();
/* 627 */     List pkey = sr.getPrimaryKey();
/* 628 */     pkey.add("ID");
/*     */     
/* 630 */     List pkList = new ArrayList();
/* 631 */     pkVals.put("ID", pkList);
/* 632 */     String tmpStr = null;
/* 633 */     boolean anyResult = false;
/* 634 */     while (rs.next()) {
/* 635 */       pkList.add(rs.getString("ID"));
/* 636 */       anyResult = true;
/*     */     }
/*     */     
/* 639 */     List retList = new ArrayList();
/* 640 */     if (anyResult) {
/* 641 */       retList.add(sr);
/*     */     }
/*     */     
/* 644 */     return retList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private List type6Search(String[] sq)
/*     */     throws Exception
/*     */   {
/* 656 */     if (sq.length == 0) {
/* 657 */       return new ArrayList();
/*     */     }
/*     */     
/* 660 */     String whereLikeClause = SearchDBUtil.getWhereLikeClause("KEYWORD", sq);
/*     */     
/* 662 */     String qry = "SELECT AM_SearchKeywords.ID FROM AM_SearchKeywords where " + whereLikeClause + " AND TYPE = 6";
/*     */     
/*     */ 
/* 665 */     ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 666 */     List resultsT4 = new ArrayList();
/* 667 */     SearchResult sr = new SearchResult();
/* 668 */     sr.setTableName("Search_Results_Context_String");
/*     */     
/* 670 */     Map pkVals = sr.getPrimaryKeyValues();
/* 671 */     List pkey = sr.getPrimaryKey();
/* 672 */     pkey.add("ID");
/*     */     
/* 674 */     List pkList = new ArrayList();
/* 675 */     pkVals.put("ID", pkList);
/* 676 */     String tmpStr = null;
/* 677 */     boolean anyResult = false;
/* 678 */     while (rs.next()) {
/* 679 */       pkList.add(rs.getString("ID"));
/* 680 */       anyResult = true;
/*     */     }
/*     */     
/* 683 */     List retList = new ArrayList();
/* 684 */     if (anyResult) {
/* 685 */       retList.add(sr);
/*     */     }
/*     */     
/* 688 */     return retList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] sq)
/*     */     throws Exception
/*     */   {
/* 697 */     if (sq.length == 0)
/*     */     {
/* 699 */       System.out.println("Please enter the search query : ");
/* 700 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 706 */       cp = AMConnectionPool.getNewConnectionPool("jdbc:mysql://app-linux4/GKMRMEDB", "root", "", "org.gjt.mm.mysql.Driver");
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 711 */       System.err.println("Could not connect to the database. Message : " + ex.getMessage() + ". Exiting...");
/* 712 */       System.exit(1);
/*     */     }
/* 714 */     Search sh = new Search(sq);
/* 715 */     List al = sh.start();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\utilities\search\Search.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */