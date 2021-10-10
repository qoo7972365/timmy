/*     */ package com.sun.corba.se.impl.dynamicany;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.io.Serializable;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.DynamicAny.DynAny;
/*     */ import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
/*     */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*     */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
/*     */ import org.omg.DynamicAny.DynSequence;
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
/*     */ public class DynSequenceImpl
/*     */   extends DynAnyCollectionImpl
/*     */   implements DynSequence
/*     */ {
/*     */   private DynSequenceImpl() {
/*  54 */     this((ORB)null, (Any)null, false);
/*     */   }
/*     */   
/*     */   protected DynSequenceImpl(ORB paramORB, Any paramAny, boolean paramBoolean) {
/*  58 */     super(paramORB, paramAny, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DynSequenceImpl(ORB paramORB, TypeCode paramTypeCode) {
/*  63 */     super(paramORB, paramTypeCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean initializeComponentsFromAny() {
/*     */     InputStream inputStream;
/*  70 */     TypeCode typeCode1 = this.any.type();
/*     */     
/*  72 */     TypeCode typeCode2 = getContentType();
/*     */ 
/*     */     
/*     */     try {
/*  76 */       inputStream = this.any.create_input_stream();
/*  77 */     } catch (BAD_OPERATION bAD_OPERATION) {
/*  78 */       return false;
/*     */     } 
/*     */     
/*  81 */     int i = inputStream.read_long();
/*  82 */     this.components = new DynAny[i];
/*  83 */     this.anys = new Any[i];
/*     */     
/*  85 */     for (byte b = 0; b < i; b++) {
/*     */ 
/*     */       
/*  88 */       this.anys[b] = DynAnyUtil.extractAnyFromStream(typeCode2, inputStream, this.orb);
/*     */       
/*     */       try {
/*  91 */         this.components[b] = DynAnyUtil.createMostDerivedDynAny(this.anys[b], this.orb, false);
/*  92 */       } catch (InconsistentTypeCode inconsistentTypeCode) {}
/*     */     } 
/*     */     
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean initializeComponentsFromTypeCode() {
/* 101 */     this.components = new DynAny[0];
/* 102 */     this.anys = new Any[0];
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean initializeAnyFromComponents() {
/* 108 */     OutputStream outputStream = this.any.create_output_stream();
/*     */     
/* 110 */     outputStream.write_long(this.components.length);
/* 111 */     for (byte b = 0; b < this.components.length; b++) {
/* 112 */       if (this.components[b] instanceof DynAnyImpl) {
/* 113 */         ((DynAnyImpl)this.components[b]).writeAny(outputStream);
/*     */       } else {
/*     */         
/* 116 */         this.components[b].to_any().write_value(outputStream);
/*     */       } 
/*     */     } 
/* 119 */     this.any.read_value(outputStream.create_input_stream(), this.any.type());
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get_length() {
/* 130 */     if (this.status == 2) {
/* 131 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 133 */     return checkInitComponents() ? this.components.length : 0;
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
/*     */ 
/*     */   
/*     */   public void set_length(int paramInt) throws InvalidValue {
/* 161 */     if (this.status == 2) {
/* 162 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 164 */     int i = getBound();
/* 165 */     if (i > 0 && paramInt > i) {
/* 166 */       throw new InvalidValue();
/*     */     }
/*     */     
/* 169 */     checkInitComponents();
/*     */     
/* 171 */     int j = this.components.length;
/* 172 */     if (paramInt > j) {
/*     */       
/* 174 */       DynAny[] arrayOfDynAny = new DynAny[paramInt];
/* 175 */       Any[] arrayOfAny = new Any[paramInt];
/* 176 */       System.arraycopy(this.components, 0, arrayOfDynAny, 0, j);
/* 177 */       System.arraycopy(this.anys, 0, arrayOfAny, 0, j);
/* 178 */       this.components = arrayOfDynAny;
/* 179 */       this.anys = arrayOfAny;
/*     */ 
/*     */       
/* 182 */       TypeCode typeCode = getContentType();
/* 183 */       for (int k = j; k < paramInt; k++) {
/* 184 */         createDefaultComponentAt(k, typeCode);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 189 */       if (this.index == -1)
/* 190 */         this.index = j; 
/* 191 */     } else if (paramInt < j) {
/*     */       
/* 193 */       DynAny[] arrayOfDynAny = new DynAny[paramInt];
/* 194 */       Any[] arrayOfAny = new Any[paramInt];
/* 195 */       System.arraycopy(this.components, 0, arrayOfDynAny, 0, paramInt);
/* 196 */       System.arraycopy(this.anys, 0, arrayOfAny, 0, paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 203 */       this.components = arrayOfDynAny;
/* 204 */       this.anys = arrayOfAny;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       if (paramInt == 0 || this.index >= paramInt) {
/* 213 */         this.index = -1;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 218 */     else if (this.index == -1 && paramInt > 0) {
/* 219 */       this.index = 0;
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
/* 245 */     if (paramArrayOfObject == null || paramArrayOfObject.length == 0) {
/* 246 */       clearData();
/* 247 */       this.index = -1;
/*     */       return;
/*     */     } 
/* 250 */     this.index = 0;
/*     */     
/* 252 */     int i = getBound();
/* 253 */     if (i > 0 && paramArrayOfObject.length > i)
/* 254 */       throw new InvalidValue(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/dynamicany/DynSequenceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */