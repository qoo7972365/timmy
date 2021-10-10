/*    */ package com.sun.corba.se.impl.corba;
/*    */ 
/*    */ import java.util.Vector;
/*    */ import org.omg.CORBA.Bounds;
/*    */ import org.omg.CORBA.ExceptionList;
/*    */ import org.omg.CORBA.TypeCode;
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
/*    */ public class ExceptionListImpl
/*    */   extends ExceptionList
/*    */ {
/* 44 */   private final int INITIAL_CAPACITY = 2;
/* 45 */   private final int CAPACITY_INCREMENT = 2;
/*    */   
/*    */   private Vector _exceptions;
/*    */   
/*    */   public ExceptionListImpl() {
/* 50 */     this._exceptions = new Vector(2, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public int count() {
/* 55 */     return this._exceptions.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(TypeCode paramTypeCode) {
/* 60 */     this._exceptions.addElement(paramTypeCode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeCode item(int paramInt) throws Bounds {
/*    */     try {
/* 67 */       return this._exceptions.elementAt(paramInt);
/* 68 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 69 */       throw new Bounds();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove(int paramInt) throws Bounds {
/*    */     try {
/* 77 */       this._exceptions.removeElementAt(paramInt);
/* 78 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 79 */       throw new Bounds();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/ExceptionListImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */