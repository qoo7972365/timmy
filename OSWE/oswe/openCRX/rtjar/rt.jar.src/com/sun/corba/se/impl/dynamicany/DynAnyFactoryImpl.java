/*    */ package com.sun.corba.se.impl.dynamicany;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.LocalObject;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.DynamicAny.DynAny;
/*    */ import org.omg.DynamicAny.DynAnyFactory;
/*    */ import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
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
/*    */ public class DynAnyFactoryImpl
/*    */   extends LocalObject
/*    */   implements DynAnyFactory
/*    */ {
/*    */   private ORB orb;
/*    */   
/*    */   private DynAnyFactoryImpl() {
/* 55 */     this.orb = null;
/*    */   }
/*    */   
/*    */   public DynAnyFactoryImpl(ORB paramORB) {
/* 59 */     this.orb = paramORB;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DynAny create_dyn_any(Any paramAny) throws InconsistentTypeCode {
/* 70 */     return DynAnyUtil.createMostDerivedDynAny(paramAny, this.orb, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DynAny create_dyn_any_from_type_code(TypeCode paramTypeCode) throws InconsistentTypeCode {
/* 77 */     return DynAnyUtil.createMostDerivedDynAny(paramTypeCode, this.orb);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 82 */   private String[] __ids = new String[] { "IDL:omg.org/DynamicAny/DynAnyFactory:1.0" };
/*    */   
/*    */   public String[] _ids() {
/* 85 */     return (String[])this.__ids.clone();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/dynamicany/DynAnyFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */