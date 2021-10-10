/*     */ package com.sun.corba.se.impl.dynamicany;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.LocalObject;
/*     */ import org.omg.CORBA.ORBPackage.InvalidName;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.DynamicAny.DynAny;
/*     */ import org.omg.DynamicAny.DynAnyFactory;
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
/*     */ 
/*     */ 
/*     */ abstract class DynAnyImpl
/*     */   extends LocalObject
/*     */   implements DynAny
/*     */ {
/*     */   protected static final int NO_INDEX = -1;
/*     */   protected static final byte STATUS_DESTROYABLE = 0;
/*     */   protected static final byte STATUS_UNDESTROYABLE = 1;
/*     */   protected static final byte STATUS_DESTROYED = 2;
/*  59 */   protected ORB orb = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected ORBUtilSystemException wrapper;
/*     */ 
/*     */   
/*  66 */   protected Any any = null;
/*     */   
/*  68 */   protected byte status = 0;
/*  69 */   protected int index = -1;
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
/*     */   private String[] __ids;
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
/*     */   protected DynAnyFactory factory() {
/*     */     try {
/* 101 */       return (DynAnyFactory)this.orb.resolve_initial_references("DynAnyFactory");
/*     */     }
/* 103 */     catch (InvalidName invalidName) {
/* 104 */       throw new RuntimeException("Unable to find DynAnyFactory");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Any getAny() {
/* 109 */     return this.any;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Any getAny(DynAny paramDynAny) {
/* 115 */     if (paramDynAny instanceof DynAnyImpl) {
/* 116 */       return ((DynAnyImpl)paramDynAny).getAny();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     return paramDynAny.to_any();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeAny(OutputStream paramOutputStream) {
/* 127 */     this.any.write_value(paramOutputStream);
/*     */   }
/*     */   
/*     */   protected void setStatus(byte paramByte) {
/* 131 */     this.status = paramByte;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clearData() {
/* 136 */     this.any.type(this.any.type());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeCode type() {
/* 144 */     if (this.status == 2) {
/* 145 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 147 */     return this.any.type();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void assign(DynAny paramDynAny) throws TypeMismatch {
/* 154 */     if (this.status == 2) {
/* 155 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 157 */     if (this.any != null && !this.any.type().equal(paramDynAny.type())) {
/* 158 */       throw new TypeMismatch();
/*     */     }
/* 160 */     this.any = paramDynAny.to_any();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void from_any(Any paramAny) throws TypeMismatch, InvalidValue {
/* 168 */     if (this.status == 2) {
/* 169 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 171 */     if (this.any != null && !this.any.type().equal(paramAny.type())) {
/* 172 */       throw new TypeMismatch();
/*     */     }
/*     */ 
/*     */     
/* 176 */     Any any = null;
/*     */     try {
/* 178 */       any = DynAnyUtil.copy(paramAny, this.orb);
/* 179 */     } catch (Exception exception) {
/* 180 */       throw new InvalidValue();
/*     */     } 
/* 182 */     if (!DynAnyUtil.isInitialized(any)) {
/* 183 */       throw new InvalidValue();
/*     */     }
/* 185 */     this.any = any;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DynAnyImpl() {
/* 195 */     this.__ids = new String[] { "IDL:omg.org/DynamicAny/DynAny:1.0" }; this.wrapper = ORBUtilSystemException.get("rpc.presentation"); } protected DynAnyImpl(ORB paramORB, Any paramAny, boolean paramBoolean) { this.__ids = new String[] { "IDL:omg.org/DynamicAny/DynAny:1.0" }; this.orb = paramORB; this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.presentation"); if (paramBoolean) { this.any = DynAnyUtil.copy(paramAny, paramORB); } else { this.any = paramAny; }  this.index = -1; } protected DynAnyImpl(ORB paramORB, TypeCode paramTypeCode) { this.__ids = new String[] { "IDL:omg.org/DynamicAny/DynAny:1.0" };
/*     */     this.orb = paramORB;
/*     */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.presentation");
/* 198 */     this.any = DynAnyUtil.createDefaultAnyOfType(paramTypeCode, paramORB); } public String[] _ids() { return (String[])this.__ids.clone(); }
/*     */ 
/*     */   
/*     */   public abstract Any to_any();
/*     */   
/*     */   public abstract boolean equal(DynAny paramDynAny);
/*     */   
/*     */   public abstract void destroy();
/*     */   
/*     */   public abstract DynAny copy();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/dynamicany/DynAnyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */