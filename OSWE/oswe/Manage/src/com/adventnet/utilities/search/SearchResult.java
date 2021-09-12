/*     */ package com.adventnet.utilities.search;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchResult
/*     */ {
/*     */   private String tableName;
/*     */   private int tableID;
/*  20 */   private Map pkValues = new HashMap();
/*  21 */   private ArrayList pks = new ArrayList();
/*     */   
/*     */ 
/*  24 */   private Set keywords = new HashSet();
/*     */   
/*     */ 
/*     */ 
/*  28 */   private Set columnNames = new HashSet();
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
/*     */ 
/*     */   public String getTableName()
/*     */   {
/*  47 */     return this.tableName;
/*     */   }
/*     */   
/*     */   public void setTableName(String str) {
/*  51 */     this.tableName = str;
/*     */   }
/*     */   
/*     */   public int getTableID() {
/*  55 */     return this.tableID;
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
/*     */   public ArrayList getPrimaryKey()
/*     */   {
/*  68 */     return this.pks;
/*     */   }
/*     */   
/*     */   public ArrayList setPrimaryKey(ArrayList al) {
/*  72 */     return this.pks = al;
/*     */   }
/*     */   
/*     */   public Map getPrimaryKeyValues()
/*     */   {
/*  77 */     return this.pkValues;
/*     */   }
/*     */   
/*     */   public Map setPrimaryKeyValues(Map map) {
/*  81 */     return this.pkValues = map;
/*     */   }
/*     */   
/*     */   public boolean hasAnyPKValues() {
/*  85 */     return !this.pkValues.isEmpty();
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
/*     */ 
/*     */   public String toString()
/*     */   {
/* 118 */     return ">> TableName " + this.tableName + ", PrimaryKeys " + this.pkValues + " ColumnNames " + this.columnNames + " : keywords " + this.keywords + " PKs " + this.pks + " <<";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set getKeywords()
/*     */   {
/* 127 */     return this.keywords;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setKeywords(Set keywords)
/*     */   {
/* 135 */     this.keywords = keywords;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set getColumnNames()
/*     */   {
/* 143 */     return this.columnNames;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setColumnNames(Set columnNames)
/*     */   {
/* 151 */     this.columnNames = columnNames;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\utilities\search\SearchResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */