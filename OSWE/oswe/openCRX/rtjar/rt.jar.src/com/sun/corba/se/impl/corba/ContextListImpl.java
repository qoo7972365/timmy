/*    */ package com.sun.corba.se.impl.corba;
/*    */ 
/*    */ import java.util.Vector;
/*    */ import org.omg.CORBA.Bounds;
/*    */ import org.omg.CORBA.ContextList;
/*    */ import org.omg.CORBA.ORB;
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
/*    */ public class ContextListImpl
/*    */   extends ContextList
/*    */ {
/* 41 */   private final int INITIAL_CAPACITY = 2;
/* 42 */   private final int CAPACITY_INCREMENT = 2;
/*    */   
/*    */   private ORB _orb;
/*    */   
/*    */   private Vector _contexts;
/*    */ 
/*    */   
/*    */   public ContextListImpl(ORB paramORB) {
/* 50 */     this._orb = paramORB;
/* 51 */     this._contexts = new Vector(2, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public int count() {
/* 56 */     return this._contexts.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(String paramString) {
/* 61 */     this._contexts.addElement(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String item(int paramInt) throws Bounds {
/*    */     try {
/* 68 */       return this._contexts.elementAt(paramInt);
/* 69 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 70 */       throw new Bounds();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove(int paramInt) throws Bounds {
/*    */     try {
/* 78 */       this._contexts.removeElementAt(paramInt);
/* 79 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 80 */       throw new Bounds();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/ContextListImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */