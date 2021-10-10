/*    */ package com.sun.corba.se.impl.dynamicany;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import java.io.Serializable;
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.TCKind;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.DynamicAny.DynAny;
/*    */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*    */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
/*    */ import org.omg.DynamicAny.DynStruct;
/*    */ import org.omg.DynamicAny.NameDynAnyPair;
/*    */ import org.omg.DynamicAny.NameValuePair;
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
/*    */ public class DynStructImpl
/*    */   extends DynAnyComplexImpl
/*    */   implements DynStruct
/*    */ {
/*    */   private DynStructImpl() {
/* 49 */     this((ORB)null, (Any)null, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected DynStructImpl(ORB paramORB, Any paramAny, boolean paramBoolean) {
/* 54 */     super(paramORB, paramAny, paramBoolean);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected DynStructImpl(ORB paramORB, TypeCode paramTypeCode) {
/* 61 */     super(paramORB, paramTypeCode);
/*    */ 
/*    */ 
/*    */     
/* 65 */     this.index = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NameValuePair[] get_members() {
/* 73 */     if (this.status == 2) {
/* 74 */       throw this.wrapper.dynAnyDestroyed();
/*    */     }
/* 76 */     checkInitComponents();
/* 77 */     return this.nameValuePairs;
/*    */   }
/*    */   
/*    */   public NameDynAnyPair[] get_members_as_dyn_any() {
/* 81 */     if (this.status == 2) {
/* 82 */       throw this.wrapper.dynAnyDestroyed();
/*    */     }
/* 84 */     checkInitComponents();
/* 85 */     return this.nameDynAnyPairs;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/dynamicany/DynStructImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */