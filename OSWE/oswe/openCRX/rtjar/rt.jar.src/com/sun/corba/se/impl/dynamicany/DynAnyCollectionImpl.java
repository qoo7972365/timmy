/*     */ package com.sun.corba.se.impl.dynamicany;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.TypeCodePackage.BadKind;
/*     */ import org.omg.DynamicAny.DynAny;
/*     */ import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
/*     */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*     */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
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
/*     */ abstract class DynAnyCollectionImpl
/*     */   extends DynAnyConstructedImpl
/*     */ {
/*  49 */   Any[] anys = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DynAnyCollectionImpl() {
/*  56 */     this((ORB)null, (Any)null, false);
/*     */   }
/*     */   
/*     */   protected DynAnyCollectionImpl(ORB paramORB, Any paramAny, boolean paramBoolean) {
/*  60 */     super(paramORB, paramAny, paramBoolean);
/*     */   }
/*     */   
/*     */   protected DynAnyCollectionImpl(ORB paramORB, TypeCode paramTypeCode) {
/*  64 */     super(paramORB, paramTypeCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createDefaultComponentAt(int paramInt, TypeCode paramTypeCode) {
/*     */     try {
/*  73 */       this.components[paramInt] = DynAnyUtil.createMostDerivedDynAny(paramTypeCode, this.orb);
/*  74 */     } catch (InconsistentTypeCode inconsistentTypeCode) {}
/*     */ 
/*     */     
/*  77 */     this.anys[paramInt] = getAny(this.components[paramInt]);
/*     */   }
/*     */   
/*     */   protected TypeCode getContentType() {
/*     */     try {
/*  82 */       return this.any.type().content_type();
/*  83 */     } catch (BadKind badKind) {
/*  84 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getBound() {
/*     */     try {
/*  94 */       return this.any.type().length();
/*  95 */     } catch (BadKind badKind) {
/*  96 */       return 0;
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
/*     */   public Any[] get_elements() {
/* 111 */     if (this.status == 2) {
/* 112 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 114 */     return checkInitComponents() ? this.anys : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void checkValue(Object[] paramArrayOfObject) throws InvalidValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set_elements(Any[] paramArrayOfAny) throws TypeMismatch, InvalidValue {
/* 130 */     if (this.status == 2) {
/* 131 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 133 */     checkValue((Object[])paramArrayOfAny);
/*     */     
/* 135 */     this.components = new DynAny[paramArrayOfAny.length];
/* 136 */     this.anys = paramArrayOfAny;
/*     */ 
/*     */     
/* 139 */     TypeCode typeCode = getContentType();
/* 140 */     for (byte b = 0; b < paramArrayOfAny.length; b++) {
/* 141 */       if (paramArrayOfAny[b] != null) {
/* 142 */         if (!paramArrayOfAny[b].type().equal(typeCode)) {
/* 143 */           clearData();
/*     */           
/* 145 */           throw new TypeMismatch();
/*     */         } 
/*     */         
/*     */         try {
/* 149 */           this.components[b] = DynAnyUtil.createMostDerivedDynAny(paramArrayOfAny[b], this.orb, false);
/*     */         }
/* 151 */         catch (InconsistentTypeCode inconsistentTypeCode) {
/* 152 */           throw new InvalidValue();
/*     */         } 
/*     */       } else {
/* 155 */         clearData();
/*     */         
/* 157 */         throw new InvalidValue();
/*     */       } 
/*     */     } 
/* 160 */     this.index = (paramArrayOfAny.length == 0) ? -1 : 0;
/*     */     
/* 162 */     this.representations = 4;
/*     */   }
/*     */   
/*     */   public DynAny[] get_elements_as_dyn_any() {
/* 166 */     if (this.status == 2) {
/* 167 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 169 */     return checkInitComponents() ? this.components : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set_elements_as_dyn_any(DynAny[] paramArrayOfDynAny) throws TypeMismatch, InvalidValue {
/* 177 */     if (this.status == 2) {
/* 178 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 180 */     checkValue((Object[])paramArrayOfDynAny);
/*     */     
/* 182 */     this.components = (paramArrayOfDynAny == null) ? emptyComponents : paramArrayOfDynAny;
/* 183 */     this.anys = new Any[paramArrayOfDynAny.length];
/*     */ 
/*     */     
/* 186 */     TypeCode typeCode = getContentType();
/* 187 */     for (byte b = 0; b < paramArrayOfDynAny.length; b++) {
/* 188 */       if (paramArrayOfDynAny[b] != null) {
/* 189 */         if (!paramArrayOfDynAny[b].type().equal(typeCode)) {
/* 190 */           clearData();
/*     */           
/* 192 */           throw new TypeMismatch();
/*     */         } 
/* 194 */         this.anys[b] = getAny(paramArrayOfDynAny[b]);
/*     */       } else {
/* 196 */         clearData();
/*     */         
/* 198 */         throw new InvalidValue();
/*     */       } 
/*     */     } 
/* 201 */     this.index = (paramArrayOfDynAny.length == 0) ? -1 : 0;
/*     */     
/* 203 */     this.representations = 4;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/dynamicany/DynAnyCollectionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */