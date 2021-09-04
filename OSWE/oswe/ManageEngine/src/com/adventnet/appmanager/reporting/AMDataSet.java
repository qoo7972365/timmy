/*    */ package com.adventnet.appmanager.reporting;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AMDataSet
/*    */ {
/* 12 */   ArrayList rows = null;
/*    */   
/*    */   public AMDataSet()
/*    */   {
/* 16 */     this.rows = new ArrayList();
/*    */   }
/*    */   
/*    */   public void set(int rowIndex, int columnIndex, Object value) throws Exception
/*    */   {
/* 21 */     Hashtable column = null;
/*    */     try
/*    */     {
/* 24 */       column = (Hashtable)this.rows.get(rowIndex);
/*    */     }
/*    */     catch (IndexOutOfBoundsException exp) {
/* 27 */       column = new Hashtable();
/* 28 */       this.rows.add(rowIndex, column);
/*    */     }
/* 30 */     column.put(String.valueOf(columnIndex), value);
/*    */   }
/*    */   
/*    */   public int getRows()
/*    */   {
/* 35 */     return this.rows.size();
/*    */   }
/*    */   
/*    */   public int getColumnSize(int rowIndex) throws Exception
/*    */   {
/* 40 */     Hashtable column = (Hashtable)this.rows.get(rowIndex);
/* 41 */     if (column == null)
/* 42 */       throw new Exception("No column found");
/* 43 */     return column.size();
/*    */   }
/*    */   
/*    */   public Object get(int rowIndex, int columnIndex) throws Exception
/*    */   {
/* 48 */     Hashtable column = (Hashtable)this.rows.get(rowIndex);
/* 49 */     if (column == null)
/* 50 */       throw new Exception("No column found ");
/* 51 */     return column.get(String.valueOf(columnIndex));
/*    */   }
/*    */   
/*    */   public void print() {}
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\AMDataSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */