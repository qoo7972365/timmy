/*    */ package com.adventnet.utilities.search.renderer;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class RowEntry
/*    */ {
/* 18 */   private List columnValueLinks = new ArrayList();
/*    */   
/*    */ 
/* 21 */   private List onClickLinks = new ArrayList();
/*    */   
/*    */ 
/* 24 */   private List columnValues = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List getColumnValueLinks()
/*    */   {
/* 35 */     return this.columnValueLinks;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setColumnValueLinks(List columnValueLinks)
/*    */   {
/* 43 */     this.columnValueLinks = columnValueLinks;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public List getColumnValues()
/*    */   {
/* 51 */     return this.columnValues;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setColumnValues(List columnValues)
/*    */   {
/* 59 */     this.columnValues = columnValues;
/*    */   }
/*    */   
/*    */   public List getOnClickLinks()
/*    */   {
/* 64 */     return this.onClickLinks;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setOnClickLinks(List onClickLinks)
/*    */   {
/* 72 */     this.onClickLinks = onClickLinks;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 77 */     return "toString com.adventnet.utilities.search.renderer.RowEntry ColumnValues " + this.columnValues + " : " + " columnValueLinks " + this.columnValueLinks + " onClickLinks " + this.onClickLinks;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\utilities\search\renderer\RowEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */