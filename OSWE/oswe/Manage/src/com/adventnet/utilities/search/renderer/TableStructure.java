/*     */ package com.adventnet.utilities.search.renderer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class TableStructure
/*     */ {
/*  18 */   private String title = "";
/*     */   
/*     */ 
/*  21 */   private String description = "";
/*     */   
/*     */ 
/*  24 */   private List columnNames = new ArrayList();
/*     */   
/*     */ 
/*  27 */   private List rowEntry = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean resultsPresent;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTitle()
/*     */   {
/*  41 */     return this.title;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/*  49 */     this.title = title;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  57 */     return this.description;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/*  65 */     this.description = description;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List getColumnNames()
/*     */   {
/*  73 */     return this.columnNames;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setColumnNames(List columnNames)
/*     */   {
/*  81 */     this.columnNames = columnNames;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List getRowEntry()
/*     */   {
/*  89 */     return this.rowEntry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRowEntry(List rowEntry)
/*     */   {
/*  97 */     this.rowEntry = rowEntry;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isResultsPresent()
/*     */   {
/* 105 */     return this.resultsPresent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setResultsPresent(boolean resultsPresent)
/*     */   {
/* 113 */     this.resultsPresent = resultsPresent;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\utilities\search\renderer\TableStructure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */