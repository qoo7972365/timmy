/*      */ package com.sun.rowset;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Date;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Hashtable;
/*      */ import javax.sql.rowset.FilteredRowSet;
/*      */ import javax.sql.rowset.Predicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FilteredRowSetImpl
/*      */   extends WebRowSetImpl
/*      */   implements Serializable, Cloneable, FilteredRowSet
/*      */ {
/*      */   private Predicate p;
/*      */   private boolean onInsertRow = false;
/*      */   static final long serialVersionUID = 6178454588413509360L;
/*      */   
/*      */   public FilteredRowSetImpl() throws SQLException {}
/*      */   
/*      */   public FilteredRowSetImpl(Hashtable paramHashtable) throws SQLException {
/*   71 */     super(paramHashtable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFilter(Predicate paramPredicate) throws SQLException {
/*   80 */     this.p = paramPredicate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Predicate getFilter() {
/*   89 */     return this.p;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean internalNext() throws SQLException {
/*  126 */     boolean bool = false;
/*      */     
/*  128 */     for (int i = getRow(); i <= size(); i++) {
/*  129 */       bool = super.internalNext();
/*      */       
/*  131 */       if (!bool || this.p == null) {
/*  132 */         return bool;
/*      */       }
/*  134 */       if (this.p.evaluate(this)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  140 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean internalPrevious() throws SQLException {
/*  155 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */     
/*  159 */     for (int i = getRow(); i > 0; i--) {
/*      */       
/*  161 */       bool = super.internalPrevious();
/*      */       
/*  163 */       if (this.p == null) {
/*  164 */         return bool;
/*      */       }
/*      */       
/*  167 */       if (this.p.evaluate(this)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  173 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean internalFirst() throws SQLException {
/*  192 */     boolean bool = super.internalFirst();
/*      */     
/*  194 */     if (this.p == null) {
/*  195 */       return bool;
/*      */     }
/*      */     
/*  198 */     while (bool) {
/*      */       
/*  200 */       if (this.p.evaluate(this)) {
/*      */         break;
/*      */       }
/*  203 */       bool = super.internalNext();
/*      */     } 
/*  205 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean internalLast() throws SQLException {
/*  223 */     boolean bool = super.internalLast();
/*      */     
/*  225 */     if (this.p == null) {
/*  226 */       return bool;
/*      */     }
/*      */     
/*  229 */     while (bool) {
/*      */       
/*  231 */       if (this.p.evaluate(this)) {
/*      */         break;
/*      */       }
/*      */       
/*  235 */       bool = super.internalPrevious();
/*      */     } 
/*      */     
/*  238 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean relative(int paramInt) throws SQLException {
/*  297 */     boolean bool1, bool2 = false;
/*  298 */     boolean bool3 = false;
/*      */     
/*  300 */     if (getType() == 1003) {
/*  301 */       throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.relative").toString());
/*      */     }
/*      */     
/*  304 */     if (paramInt > 0) {
/*      */       
/*  306 */       byte b = 0;
/*  307 */       while (b < paramInt) {
/*      */         
/*  309 */         if (isAfterLast()) {
/*  310 */           return false;
/*      */         }
/*  312 */         bool2 = internalNext();
/*  313 */         b++;
/*      */       } 
/*      */       
/*  316 */       bool1 = bool2;
/*      */     } else {
/*  318 */       int i = paramInt;
/*  319 */       while (i < 0) {
/*      */         
/*  321 */         if (isBeforeFirst()) {
/*  322 */           return false;
/*      */         }
/*  324 */         bool3 = internalPrevious();
/*  325 */         i++;
/*      */       } 
/*  327 */       bool1 = bool3;
/*      */     } 
/*  329 */     if (paramInt != 0)
/*  330 */       notifyCursorMoved(); 
/*  331 */     return bool1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean absolute(int paramInt) throws SQLException {
/*  384 */     boolean bool1, bool2 = false;
/*      */     
/*  386 */     if (paramInt == 0 || getType() == 1003) {
/*  387 */       throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.absolute").toString());
/*      */     }
/*      */     
/*  390 */     if (paramInt > 0) {
/*  391 */       bool2 = internalFirst();
/*      */       
/*  393 */       byte b = 0;
/*  394 */       while (b < paramInt - 1) {
/*  395 */         if (isAfterLast()) {
/*  396 */           return false;
/*      */         }
/*  398 */         bool2 = internalNext();
/*  399 */         b++;
/*      */       } 
/*  401 */       bool1 = bool2;
/*      */     } else {
/*  403 */       bool2 = internalLast();
/*      */       
/*  405 */       int i = paramInt;
/*  406 */       while (i + 1 < 0) {
/*  407 */         if (isBeforeFirst()) {
/*  408 */           return false;
/*      */         }
/*  410 */         bool2 = internalPrevious();
/*  411 */         i++;
/*      */       } 
/*  413 */       bool1 = bool2;
/*      */     } 
/*  415 */     notifyCursorMoved();
/*  416 */     return bool1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveToInsertRow() throws SQLException {
/*  447 */     this.onInsertRow = true;
/*  448 */     super.moveToInsertRow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateInt(int paramInt1, int paramInt2) throws SQLException {
/*  501 */     if (this.onInsertRow && 
/*  502 */       this.p != null) {
/*  503 */       boolean bool = this.p.evaluate(Integer.valueOf(paramInt2), paramInt1);
/*      */       
/*  505 */       if (!bool) {
/*  506 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  511 */     super.updateInt(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateInt(String paramString, int paramInt) throws SQLException {
/*  538 */     updateInt(findColumn(paramString), paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBoolean(int paramInt, boolean paramBoolean) throws SQLException {
/*  568 */     if (this.onInsertRow && 
/*  569 */       this.p != null) {
/*  570 */       boolean bool = this.p.evaluate(Boolean.valueOf(paramBoolean), paramInt);
/*      */       
/*  572 */       if (!bool) {
/*  573 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  578 */     super.updateBoolean(paramInt, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBoolean(String paramString, boolean paramBoolean) throws SQLException {
/*  605 */     updateBoolean(findColumn(paramString), paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateByte(int paramInt, byte paramByte) throws SQLException {
/*  636 */     if (this.onInsertRow && 
/*  637 */       this.p != null) {
/*  638 */       boolean bool = this.p.evaluate(Byte.valueOf(paramByte), paramInt);
/*      */       
/*  640 */       if (!bool) {
/*  641 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  646 */     super.updateByte(paramInt, paramByte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateByte(String paramString, byte paramByte) throws SQLException {
/*  674 */     updateByte(findColumn(paramString), paramByte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateShort(int paramInt, short paramShort) throws SQLException {
/*  705 */     if (this.onInsertRow && 
/*  706 */       this.p != null) {
/*  707 */       boolean bool = this.p.evaluate(Short.valueOf(paramShort), paramInt);
/*      */       
/*  709 */       if (!bool) {
/*  710 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  715 */     super.updateShort(paramInt, paramShort);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateShort(String paramString, short paramShort) throws SQLException {
/*  742 */     updateShort(findColumn(paramString), paramShort);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateLong(int paramInt, long paramLong) throws SQLException {
/*  773 */     if (this.onInsertRow && 
/*  774 */       this.p != null) {
/*  775 */       boolean bool = this.p.evaluate(Long.valueOf(paramLong), paramInt);
/*      */       
/*  777 */       if (!bool) {
/*  778 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  783 */     super.updateLong(paramInt, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateLong(String paramString, long paramLong) throws SQLException {
/*  810 */     updateLong(findColumn(paramString), paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateFloat(int paramInt, float paramFloat) throws SQLException {
/*  840 */     if (this.onInsertRow && 
/*  841 */       this.p != null) {
/*  842 */       boolean bool = this.p.evaluate(Float.valueOf(paramFloat), paramInt);
/*      */       
/*  844 */       if (!bool) {
/*  845 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  850 */     super.updateFloat(paramInt, paramFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateFloat(String paramString, float paramFloat) throws SQLException {
/*  877 */     updateFloat(findColumn(paramString), paramFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateDouble(int paramInt, double paramDouble) throws SQLException {
/*  907 */     if (this.onInsertRow && 
/*  908 */       this.p != null) {
/*  909 */       boolean bool = this.p.evaluate(Double.valueOf(paramDouble), paramInt);
/*      */       
/*  911 */       if (!bool) {
/*  912 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  917 */     super.updateDouble(paramInt, paramDouble);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateDouble(String paramString, double paramDouble) throws SQLException {
/*  944 */     updateDouble(findColumn(paramString), paramDouble);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
/*  974 */     if (this.onInsertRow && 
/*  975 */       this.p != null) {
/*  976 */       boolean bool = this.p.evaluate(paramBigDecimal, paramInt);
/*      */       
/*  978 */       if (!bool) {
/*  979 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  984 */     super.updateBigDecimal(paramInt, paramBigDecimal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
/* 1011 */     updateBigDecimal(findColumn(paramString), paramBigDecimal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateString(int paramInt, String paramString) throws SQLException {
/* 1044 */     if (this.onInsertRow && 
/* 1045 */       this.p != null) {
/* 1046 */       boolean bool = this.p.evaluate(paramString, paramInt);
/*      */       
/* 1048 */       if (!bool) {
/* 1049 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1054 */     super.updateString(paramInt, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateString(String paramString1, String paramString2) throws SQLException {
/* 1081 */     updateString(findColumn(paramString1), paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
/* 1110 */     String str = "";
/*      */     
/* 1112 */     Byte[] arrayOfByte = new Byte[paramArrayOfbyte.length];
/*      */     
/* 1114 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 1115 */       arrayOfByte[b] = Byte.valueOf(paramArrayOfbyte[b]);
/* 1116 */       str = str.concat(arrayOfByte[b].toString());
/*      */     } 
/*      */ 
/*      */     
/* 1120 */     if (this.onInsertRow && 
/* 1121 */       this.p != null) {
/* 1122 */       boolean bool = this.p.evaluate(str, paramInt);
/*      */       
/* 1124 */       if (!bool) {
/* 1125 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1130 */     super.updateBytes(paramInt, paramArrayOfbyte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
/* 1157 */     updateBytes(findColumn(paramString), paramArrayOfbyte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateDate(int paramInt, Date paramDate) throws SQLException {
/* 1188 */     if (this.onInsertRow && 
/* 1189 */       this.p != null) {
/* 1190 */       boolean bool = this.p.evaluate(paramDate, paramInt);
/*      */       
/* 1192 */       if (!bool) {
/* 1193 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1198 */     super.updateDate(paramInt, paramDate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateDate(String paramString, Date paramDate) throws SQLException {
/* 1227 */     updateDate(findColumn(paramString), paramDate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTime(int paramInt, Time paramTime) throws SQLException {
/* 1258 */     if (this.onInsertRow && 
/* 1259 */       this.p != null) {
/* 1260 */       boolean bool = this.p.evaluate(paramTime, paramInt);
/*      */       
/* 1262 */       if (!bool) {
/* 1263 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1268 */     super.updateTime(paramInt, paramTime);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTime(String paramString, Time paramTime) throws SQLException {
/* 1297 */     updateTime(findColumn(paramString), paramTime);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
/* 1329 */     if (this.onInsertRow && 
/* 1330 */       this.p != null) {
/* 1331 */       boolean bool = this.p.evaluate(paramTimestamp, paramInt);
/*      */       
/* 1333 */       if (!bool) {
/* 1334 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1339 */     super.updateTimestamp(paramInt, paramTimestamp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
/* 1371 */     updateTimestamp(findColumn(paramString), paramTimestamp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
/* 1399 */     if (this.onInsertRow && 
/* 1400 */       this.p != null) {
/* 1401 */       boolean bool = this.p.evaluate(paramInputStream, paramInt1);
/*      */       
/* 1403 */       if (!bool) {
/* 1404 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1409 */     super.updateAsciiStream(paramInt1, paramInputStream, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/* 1433 */     updateAsciiStream(findColumn(paramString), paramInputStream, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
/* 1468 */     if (this.onInsertRow && 
/* 1469 */       this.p != null) {
/* 1470 */       boolean bool = this.p.evaluate(paramReader, paramInt1);
/*      */       
/* 1472 */       if (!bool) {
/* 1473 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1478 */     super.updateCharacterStream(paramInt1, paramReader, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
/* 1509 */     updateCharacterStream(findColumn(paramString), paramReader, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
/* 1542 */     if (this.onInsertRow && 
/* 1543 */       this.p != null) {
/* 1544 */       boolean bool = this.p.evaluate(paramInputStream, paramInt1);
/*      */       
/* 1546 */       if (!bool) {
/* 1547 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1552 */     super.updateBinaryStream(paramInt1, paramInputStream, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
/* 1583 */     updateBinaryStream(findColumn(paramString), paramInputStream, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateObject(int paramInt, Object paramObject) throws SQLException {
/* 1613 */     if (this.onInsertRow && 
/* 1614 */       this.p != null) {
/* 1615 */       boolean bool = this.p.evaluate(paramObject, paramInt);
/*      */       
/* 1617 */       if (!bool) {
/* 1618 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1623 */     super.updateObject(paramInt, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateObject(String paramString, Object paramObject) throws SQLException {
/* 1650 */     updateObject(findColumn(paramString), paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
/* 1685 */     if (this.onInsertRow && 
/* 1686 */       this.p != null) {
/* 1687 */       boolean bool = this.p.evaluate(paramObject, paramInt1);
/*      */       
/* 1689 */       if (!bool) {
/* 1690 */         throw new SQLException(this.resBundle.handleGetObject("filteredrowsetimpl.notallowed").toString());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1695 */     super.updateObject(paramInt1, paramObject, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateObject(String paramString, Object paramObject, int paramInt) throws SQLException {
/* 1727 */     updateObject(findColumn(paramString), paramObject, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insertRow() throws SQLException {
/* 1747 */     this.onInsertRow = false;
/* 1748 */     super.insertRow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1758 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */     try {
/* 1761 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 1762 */     } catch (IOException iOException) {
/* 1763 */       throw new RuntimeException(iOException);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/FilteredRowSetImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */