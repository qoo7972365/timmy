/*     */ package com.sun.rowset.internal;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import java.util.BitSet;
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
/*     */ public class Row
/*     */   extends BaseRow
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 5047859032611314762L;
/*     */   private Object[] currentVals;
/*     */   private BitSet colsChanged;
/*     */   private boolean deleted;
/*     */   private boolean updated;
/*     */   private boolean inserted;
/*     */   private int numCols;
/*     */   
/*     */   public Row(int paramInt) {
/* 103 */     this.origVals = new Object[paramInt];
/* 104 */     this.currentVals = new Object[paramInt];
/* 105 */     this.colsChanged = new BitSet(paramInt);
/* 106 */     this.numCols = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Row(int paramInt, Object[] paramArrayOfObject) {
/* 117 */     this.origVals = new Object[paramInt];
/* 118 */     System.arraycopy(paramArrayOfObject, 0, this.origVals, 0, paramInt);
/* 119 */     this.currentVals = new Object[paramInt];
/* 120 */     this.colsChanged = new BitSet(paramInt);
/* 121 */     this.numCols = paramInt;
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
/*     */   public void initColumnObject(int paramInt, Object paramObject) {
/* 135 */     this.origVals[paramInt - 1] = paramObject;
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
/*     */   public void setColumnObject(int paramInt, Object paramObject) {
/* 150 */     this.currentVals[paramInt - 1] = paramObject;
/* 151 */     setColUpdated(paramInt - 1);
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
/*     */   public Object getColumnObject(int paramInt) throws SQLException {
/* 165 */     if (getColUpdated(paramInt - 1)) {
/* 166 */       return this.currentVals[paramInt - 1];
/*     */     }
/* 168 */     return this.origVals[paramInt - 1];
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
/*     */   public boolean getColUpdated(int paramInt) {
/* 184 */     return this.colsChanged.get(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeleted() {
/* 194 */     this.deleted = true;
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
/*     */   public boolean getDeleted() {
/* 208 */     return this.deleted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearDeleted() {
/* 216 */     this.deleted = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInserted() {
/* 227 */     this.inserted = true;
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
/*     */   public boolean getInserted() {
/* 240 */     return this.inserted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearInserted() {
/* 249 */     this.inserted = false;
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
/*     */   public boolean getUpdated() {
/* 261 */     return this.updated;
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
/*     */   public void setUpdated() {
/* 273 */     for (byte b = 0; b < this.numCols; b++) {
/* 274 */       if (getColUpdated(b) == true) {
/* 275 */         this.updated = true;
/*     */         return;
/*     */       } 
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
/*     */   private void setColUpdated(int paramInt) {
/* 292 */     this.colsChanged.set(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearUpdated() {
/* 303 */     this.updated = false;
/* 304 */     for (byte b = 0; b < this.numCols; b++) {
/* 305 */       this.currentVals[b] = null;
/* 306 */       this.colsChanged.clear(b);
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
/*     */   public void moveCurrentToOrig() {
/* 322 */     for (byte b = 0; b < this.numCols; b++) {
/* 323 */       if (getColUpdated(b) == true) {
/* 324 */         this.origVals[b] = this.currentVals[b];
/* 325 */         this.currentVals[b] = null;
/* 326 */         this.colsChanged.clear(b);
/*     */       } 
/*     */     } 
/* 329 */     this.updated = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseRow getCurrentRow() {
/* 339 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/internal/Row.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */