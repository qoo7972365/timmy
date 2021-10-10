/*     */ package com.sun.corba.se.impl.dynamicany;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.io.Serializable;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.DynamicAny.DynAny;
/*     */ import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
/*     */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*     */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
/*     */ import org.omg.DynamicAny.DynValueBox;
/*     */ import org.omg.DynamicAny.NameDynAnyPair;
/*     */ import org.omg.DynamicAny.NameValuePair;
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
/*     */ public class DynValueBoxImpl
/*     */   extends DynValueCommonImpl
/*     */   implements DynValueBox
/*     */ {
/*     */   private DynValueBoxImpl() {
/*  49 */     this((ORB)null, (Any)null, false);
/*     */   }
/*     */   
/*     */   protected DynValueBoxImpl(ORB paramORB, Any paramAny, boolean paramBoolean) {
/*  53 */     super(paramORB, paramAny, paramBoolean);
/*     */   }
/*     */   
/*     */   protected DynValueBoxImpl(ORB paramORB, TypeCode paramTypeCode) {
/*  57 */     super(paramORB, paramTypeCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Any get_boxed_value() throws InvalidValue {
/*  67 */     if (this.isNull) {
/*  68 */       throw new InvalidValue();
/*     */     }
/*  70 */     checkInitAny();
/*  71 */     return this.any;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set_boxed_value(Any paramAny) throws TypeMismatch {
/*  77 */     if (!this.isNull && !paramAny.type().equal(type())) {
/*  78 */       throw new TypeMismatch();
/*     */     }
/*  80 */     clearData();
/*  81 */     this.any = paramAny;
/*  82 */     this.representations = 2;
/*  83 */     this.index = 0;
/*  84 */     this.isNull = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DynAny get_boxed_value_as_dyn_any() throws InvalidValue {
/*  90 */     if (this.isNull) {
/*  91 */       throw new InvalidValue();
/*     */     }
/*  93 */     checkInitComponents();
/*  94 */     return this.components[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set_boxed_value_as_dyn_any(DynAny paramDynAny) throws TypeMismatch {
/* 100 */     if (!this.isNull && !paramDynAny.type().equal(type())) {
/* 101 */       throw new TypeMismatch();
/*     */     }
/* 103 */     clearData();
/* 104 */     this.components = new DynAny[] { paramDynAny };
/* 105 */     this.representations = 4;
/* 106 */     this.index = 0;
/* 107 */     this.isNull = false;
/*     */   }
/*     */   
/*     */   protected boolean initializeComponentsFromAny() {
/*     */     try {
/* 112 */       this.components = new DynAny[] { DynAnyUtil.createMostDerivedDynAny(this.any, this.orb, false) };
/* 113 */     } catch (InconsistentTypeCode inconsistentTypeCode) {
/* 114 */       return false;
/*     */     } 
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean initializeComponentsFromTypeCode() {
/*     */     try {
/* 121 */       this.any = DynAnyUtil.createDefaultAnyOfType(this.any.type(), this.orb);
/* 122 */       this.components = new DynAny[] { DynAnyUtil.createMostDerivedDynAny(this.any, this.orb, false) };
/* 123 */     } catch (InconsistentTypeCode inconsistentTypeCode) {
/* 124 */       return false;
/*     */     } 
/* 126 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean initializeAnyFromComponents() {
/* 130 */     this.any = getAny(this.components[0]);
/* 131 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/dynamicany/DynValueBoxImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */