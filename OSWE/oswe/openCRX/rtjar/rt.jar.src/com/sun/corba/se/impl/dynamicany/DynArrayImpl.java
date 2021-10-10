/*     */ package com.sun.corba.se.impl.dynamicany;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.io.Serializable;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.DynamicAny.DynAny;
/*     */ import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
/*     */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*     */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
/*     */ import org.omg.DynamicAny.DynArray;
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
/*     */ public class DynArrayImpl
/*     */   extends DynAnyCollectionImpl
/*     */   implements DynArray
/*     */ {
/*     */   private DynArrayImpl() {
/*  50 */     this((ORB)null, (Any)null, false);
/*     */   }
/*     */   
/*     */   protected DynArrayImpl(ORB paramORB, Any paramAny, boolean paramBoolean) {
/*  54 */     super(paramORB, paramAny, paramBoolean);
/*     */   }
/*     */   
/*     */   protected DynArrayImpl(ORB paramORB, TypeCode paramTypeCode) {
/*  58 */     super(paramORB, paramTypeCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean initializeComponentsFromAny() {
/*     */     InputStream inputStream;
/*  65 */     TypeCode typeCode1 = this.any.type();
/*  66 */     int i = getBound();
/*  67 */     TypeCode typeCode2 = getContentType();
/*     */ 
/*     */     
/*     */     try {
/*  71 */       inputStream = this.any.create_input_stream();
/*  72 */     } catch (BAD_OPERATION bAD_OPERATION) {
/*  73 */       return false;
/*     */     } 
/*     */     
/*  76 */     this.components = new DynAny[i];
/*  77 */     this.anys = new Any[i];
/*     */     
/*  79 */     for (byte b = 0; b < i; b++) {
/*     */ 
/*     */       
/*  82 */       this.anys[b] = DynAnyUtil.extractAnyFromStream(typeCode2, inputStream, this.orb);
/*     */       
/*     */       try {
/*  85 */         this.components[b] = DynAnyUtil.createMostDerivedDynAny(this.anys[b], this.orb, false);
/*  86 */       } catch (InconsistentTypeCode inconsistentTypeCode) {}
/*     */     } 
/*     */     
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean initializeComponentsFromTypeCode() {
/*  98 */     TypeCode typeCode1 = this.any.type();
/*  99 */     int i = getBound();
/* 100 */     TypeCode typeCode2 = getContentType();
/*     */     
/* 102 */     this.components = new DynAny[i];
/* 103 */     this.anys = new Any[i];
/*     */     
/* 105 */     for (byte b = 0; b < i; b++) {
/* 106 */       createDefaultComponentAt(b, typeCode2);
/*     */     }
/* 108 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkValue(Object[] paramArrayOfObject) throws InvalidValue {
/* 134 */     if (paramArrayOfObject == null || paramArrayOfObject.length != getBound())
/* 135 */       throw new InvalidValue(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/dynamicany/DynArrayImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */