/*    */ package com.sun.rowset.internal;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Arrays;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseRow
/*    */   implements Serializable, Cloneable
/*    */ {
/*    */   private static final long serialVersionUID = 4152013523511412238L;
/*    */   protected Object[] origVals;
/*    */   
/*    */   public Object[] getOrigRow() {
/* 74 */     Object[] arrayOfObject = this.origVals;
/* 75 */     return (arrayOfObject == null) ? null : Arrays.<Object>copyOf(arrayOfObject, arrayOfObject.length);
/*    */   }
/*    */   
/*    */   public abstract Object getColumnObject(int paramInt) throws SQLException;
/*    */   
/*    */   public abstract void setColumnObject(int paramInt, Object paramObject) throws SQLException;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/internal/BaseRow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */