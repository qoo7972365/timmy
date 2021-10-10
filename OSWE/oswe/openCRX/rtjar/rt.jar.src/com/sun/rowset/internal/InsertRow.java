/*     */ package com.sun.rowset.internal;
/*     */ 
/*     */ import com.sun.rowset.JdbcRowSetResourceBundle;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.BitSet;
/*     */ import javax.sql.RowSetMetaData;
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
/*     */ public class InsertRow
/*     */   extends BaseRow
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private BitSet colsInserted;
/*     */   private int cols;
/*     */   private JdbcRowSetResourceBundle resBundle;
/*     */   static final long serialVersionUID = 1066099658102869344L;
/*     */   
/*     */   public InsertRow(int paramInt) {
/*  68 */     this.origVals = new Object[paramInt];
/*  69 */     this.colsInserted = new BitSet(paramInt);
/*  70 */     this.cols = paramInt;
/*     */     try {
/*  72 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/*  73 */     } catch (IOException iOException) {
/*  74 */       throw new RuntimeException(iOException);
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
/*     */   protected void markColInserted(int paramInt) {
/*  88 */     this.colsInserted.set(paramInt);
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
/*     */   public boolean isCompleteRow(RowSetMetaData paramRowSetMetaData) throws SQLException {
/* 102 */     for (byte b = 0; b < this.cols; b++) {
/* 103 */       if (!this.colsInserted.get(b) && paramRowSetMetaData
/* 104 */         .isNullable(b + 1) == 0)
/*     */       {
/* 106 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initInsertRow() {
/* 119 */     for (byte b = 0; b < this.cols; b++) {
/* 120 */       this.colsInserted.clear(b);
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
/*     */   public Object getColumnObject(int paramInt) throws SQLException {
/* 136 */     if (!this.colsInserted.get(paramInt - 1)) {
/* 137 */       throw new SQLException(this.resBundle.handleGetObject("insertrow.novalue").toString());
/*     */     }
/* 139 */     return this.origVals[paramInt - 1];
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
/*     */   public void setColumnObject(int paramInt, Object paramObject) {
/* 157 */     this.origVals[paramInt - 1] = paramObject;
/* 158 */     markColInserted(paramInt - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 168 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     try {
/* 171 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 172 */     } catch (IOException iOException) {
/* 173 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/internal/InsertRow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */