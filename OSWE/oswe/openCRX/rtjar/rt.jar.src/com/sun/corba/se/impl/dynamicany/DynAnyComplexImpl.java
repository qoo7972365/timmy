/*     */ package com.sun.corba.se.impl.dynamicany;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.TypeCodePackage.BadKind;
/*     */ import org.omg.CORBA.TypeCodePackage.Bounds;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.DynamicAny.DynAny;
/*     */ import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
/*     */ import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
/*     */ import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;
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
/*     */ 
/*     */ 
/*     */ abstract class DynAnyComplexImpl
/*     */   extends DynAnyConstructedImpl
/*     */ {
/*  49 */   String[] names = null;
/*     */ 
/*     */   
/*  52 */   NameValuePair[] nameValuePairs = null;
/*  53 */   NameDynAnyPair[] nameDynAnyPairs = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DynAnyComplexImpl() {
/*  60 */     this((ORB)null, (Any)null, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DynAnyComplexImpl(ORB paramORB, Any paramAny, boolean paramBoolean) {
/*  65 */     super(paramORB, paramAny, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DynAnyComplexImpl(ORB paramORB, TypeCode paramTypeCode) {
/*  72 */     super(paramORB, paramTypeCode);
/*     */ 
/*     */ 
/*     */     
/*  76 */     this.index = 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String current_member_name() throws TypeMismatch, InvalidValue {
/* 109 */     if (this.status == 2) {
/* 110 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 112 */     if (!checkInitComponents() || this.index < 0 || this.index >= this.names.length) {
/* 113 */       throw new InvalidValue();
/*     */     }
/* 115 */     return this.names[this.index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TCKind current_member_kind() throws TypeMismatch, InvalidValue {
/* 122 */     if (this.status == 2) {
/* 123 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 125 */     if (!checkInitComponents() || this.index < 0 || this.index >= this.components.length) {
/* 126 */       throw new InvalidValue();
/*     */     }
/* 128 */     return this.components[this.index].type().kind();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set_members(NameValuePair[] paramArrayOfNameValuePair) throws TypeMismatch, InvalidValue {
/* 136 */     if (this.status == 2) {
/* 137 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 139 */     if (paramArrayOfNameValuePair == null || paramArrayOfNameValuePair.length == 0) {
/* 140 */       clearData();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 145 */     DynAny dynAny = null;
/*     */ 
/*     */     
/* 148 */     TypeCode typeCode = this.any.type();
/*     */     
/* 150 */     int i = 0;
/*     */     try {
/* 152 */       i = typeCode.member_count();
/* 153 */     } catch (BadKind badKind) {}
/*     */     
/* 155 */     if (i != paramArrayOfNameValuePair.length) {
/* 156 */       clearData();
/* 157 */       throw new InvalidValue();
/*     */     } 
/*     */     
/* 160 */     allocComponents(paramArrayOfNameValuePair);
/*     */     
/* 162 */     for (byte b = 0; b < paramArrayOfNameValuePair.length; b++) {
/* 163 */       if (paramArrayOfNameValuePair[b] != null) {
/* 164 */         String str1 = (paramArrayOfNameValuePair[b]).id;
/* 165 */         String str2 = null;
/*     */         
/* 167 */         try { str2 = typeCode.member_name(b); }
/* 168 */         catch (BadKind badKind) {  }
/* 169 */         catch (Bounds bounds) {}
/*     */         
/* 171 */         if (!str2.equals(str1) && !str1.equals("")) {
/* 172 */           clearData();
/*     */           
/* 174 */           throw new TypeMismatch();
/*     */         } 
/* 176 */         Any any = (paramArrayOfNameValuePair[b]).value;
/* 177 */         TypeCode typeCode1 = null;
/*     */         
/* 179 */         try { typeCode1 = typeCode.member_type(b); }
/* 180 */         catch (BadKind badKind) {  }
/* 181 */         catch (Bounds bounds) {}
/*     */         
/* 183 */         if (!typeCode1.equal(any.type())) {
/* 184 */           clearData();
/*     */           
/* 186 */           throw new TypeMismatch();
/*     */         } 
/*     */         
/*     */         try {
/* 190 */           dynAny = DynAnyUtil.createMostDerivedDynAny(any, this.orb, false);
/* 191 */         } catch (InconsistentTypeCode inconsistentTypeCode) {
/* 192 */           throw new InvalidValue();
/*     */         } 
/* 194 */         addComponent(b, str1, any, dynAny);
/*     */       } else {
/* 196 */         clearData();
/*     */         
/* 198 */         throw new InvalidValue();
/*     */       } 
/*     */     } 
/* 201 */     this.index = (paramArrayOfNameValuePair.length == 0) ? -1 : 0;
/* 202 */     this.representations = 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set_members_as_dyn_any(NameDynAnyPair[] paramArrayOfNameDynAnyPair) throws TypeMismatch, InvalidValue {
/* 210 */     if (this.status == 2) {
/* 211 */       throw this.wrapper.dynAnyDestroyed();
/*     */     }
/* 213 */     if (paramArrayOfNameDynAnyPair == null || paramArrayOfNameDynAnyPair.length == 0) {
/* 214 */       clearData();
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 222 */     TypeCode typeCode = this.any.type();
/*     */     
/* 224 */     int i = 0;
/*     */     try {
/* 226 */       i = typeCode.member_count();
/* 227 */     } catch (BadKind badKind) {}
/*     */     
/* 229 */     if (i != paramArrayOfNameDynAnyPair.length) {
/* 230 */       clearData();
/* 231 */       throw new InvalidValue();
/*     */     } 
/*     */     
/* 234 */     allocComponents(paramArrayOfNameDynAnyPair);
/*     */     
/* 236 */     for (byte b = 0; b < paramArrayOfNameDynAnyPair.length; b++) {
/* 237 */       if (paramArrayOfNameDynAnyPair[b] != null) {
/* 238 */         String str1 = (paramArrayOfNameDynAnyPair[b]).id;
/* 239 */         String str2 = null;
/*     */         
/* 241 */         try { str2 = typeCode.member_name(b); }
/* 242 */         catch (BadKind badKind) {  }
/* 243 */         catch (Bounds bounds) {}
/*     */         
/* 245 */         if (!str2.equals(str1) && !str1.equals("")) {
/* 246 */           clearData();
/*     */           
/* 248 */           throw new TypeMismatch();
/*     */         } 
/* 250 */         DynAny dynAny = (paramArrayOfNameDynAnyPair[b]).value;
/* 251 */         Any any = getAny(dynAny);
/* 252 */         TypeCode typeCode1 = null;
/*     */         
/* 254 */         try { typeCode1 = typeCode.member_type(b); }
/* 255 */         catch (BadKind badKind) {  }
/* 256 */         catch (Bounds bounds) {}
/*     */         
/* 258 */         if (!typeCode1.equal(any.type())) {
/* 259 */           clearData();
/*     */           
/* 261 */           throw new TypeMismatch();
/*     */         } 
/*     */         
/* 264 */         addComponent(b, str1, any, dynAny);
/*     */       } else {
/* 266 */         clearData();
/*     */         
/* 268 */         throw new InvalidValue();
/*     */       } 
/*     */     } 
/* 271 */     this.index = (paramArrayOfNameDynAnyPair.length == 0) ? -1 : 0;
/* 272 */     this.representations = 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void allocComponents(int paramInt) {
/* 280 */     this.components = new DynAny[paramInt];
/* 281 */     this.names = new String[paramInt];
/* 282 */     this.nameValuePairs = new NameValuePair[paramInt];
/* 283 */     this.nameDynAnyPairs = new NameDynAnyPair[paramInt];
/* 284 */     for (byte b = 0; b < paramInt; b++) {
/* 285 */       this.nameValuePairs[b] = new NameValuePair();
/* 286 */       this.nameDynAnyPairs[b] = new NameDynAnyPair();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void allocComponents(NameValuePair[] paramArrayOfNameValuePair) {
/* 291 */     this.components = new DynAny[paramArrayOfNameValuePair.length];
/* 292 */     this.names = new String[paramArrayOfNameValuePair.length];
/* 293 */     this.nameValuePairs = paramArrayOfNameValuePair;
/* 294 */     this.nameDynAnyPairs = new NameDynAnyPair[paramArrayOfNameValuePair.length];
/* 295 */     for (byte b = 0; b < paramArrayOfNameValuePair.length; b++) {
/* 296 */       this.nameDynAnyPairs[b] = new NameDynAnyPair();
/*     */     }
/*     */   }
/*     */   
/*     */   private void allocComponents(NameDynAnyPair[] paramArrayOfNameDynAnyPair) {
/* 301 */     this.components = new DynAny[paramArrayOfNameDynAnyPair.length];
/* 302 */     this.names = new String[paramArrayOfNameDynAnyPair.length];
/* 303 */     this.nameValuePairs = new NameValuePair[paramArrayOfNameDynAnyPair.length];
/* 304 */     for (byte b = 0; b < paramArrayOfNameDynAnyPair.length; b++) {
/* 305 */       this.nameValuePairs[b] = new NameValuePair();
/*     */     }
/* 307 */     this.nameDynAnyPairs = paramArrayOfNameDynAnyPair;
/*     */   }
/*     */   
/*     */   private void addComponent(int paramInt, String paramString, Any paramAny, DynAny paramDynAny) {
/* 311 */     this.components[paramInt] = paramDynAny;
/* 312 */     this.names[paramInt] = (paramString != null) ? paramString : "";
/* 313 */     (this.nameValuePairs[paramInt]).id = paramString;
/* 314 */     (this.nameValuePairs[paramInt]).value = paramAny;
/* 315 */     (this.nameDynAnyPairs[paramInt]).id = paramString;
/* 316 */     (this.nameDynAnyPairs[paramInt]).value = paramDynAny;
/* 317 */     if (paramDynAny instanceof DynAnyImpl) {
/* 318 */       ((DynAnyImpl)paramDynAny).setStatus((byte)1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean initializeComponentsFromAny() {
/* 325 */     TypeCode typeCode1 = this.any.type();
/* 326 */     TypeCode typeCode2 = null;
/*     */     
/* 328 */     DynAny dynAny = null;
/* 329 */     String str = null;
/* 330 */     int i = 0;
/*     */     
/*     */     try {
/* 333 */       i = typeCode1.member_count();
/* 334 */     } catch (BadKind badKind) {}
/*     */ 
/*     */     
/* 337 */     InputStream inputStream = this.any.create_input_stream();
/*     */     
/* 339 */     allocComponents(i);
/*     */     
/* 341 */     for (byte b = 0; b < i; b++) {
/*     */       
/* 343 */       try { str = typeCode1.member_name(b);
/* 344 */         typeCode2 = typeCode1.member_type(b); }
/* 345 */       catch (BadKind badKind) {  }
/* 346 */       catch (Bounds bounds) {}
/*     */       
/* 348 */       Any any = DynAnyUtil.extractAnyFromStream(typeCode2, inputStream, this.orb);
/*     */       
/*     */       try {
/* 351 */         dynAny = DynAnyUtil.createMostDerivedDynAny(any, this.orb, false);
/*     */ 
/*     */       
/*     */       }
/* 355 */       catch (InconsistentTypeCode inconsistentTypeCode) {}
/*     */       
/* 357 */       addComponent(b, str, any, dynAny);
/*     */     } 
/* 359 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean initializeComponentsFromTypeCode() {
/* 368 */     TypeCode typeCode1 = this.any.type();
/* 369 */     TypeCode typeCode2 = null;
/*     */     
/* 371 */     DynAny dynAny = null;
/*     */     
/* 373 */     int i = 0;
/*     */     
/*     */     try {
/* 376 */       i = typeCode1.member_count();
/* 377 */     } catch (BadKind badKind) {}
/*     */ 
/*     */     
/* 380 */     allocComponents(i);
/*     */     
/* 382 */     for (byte b = 0; b < i; b++) {
/* 383 */       String str = null;
/*     */       
/* 385 */       try { str = typeCode1.member_name(b);
/* 386 */         typeCode2 = typeCode1.member_type(b); }
/* 387 */       catch (BadKind badKind) {  }
/* 388 */       catch (Bounds bounds) {}
/*     */       
/*     */       try {
/* 391 */         dynAny = DynAnyUtil.createMostDerivedDynAny(typeCode2, this.orb);
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
/*     */       }
/* 403 */       catch (InconsistentTypeCode inconsistentTypeCode) {}
/*     */ 
/*     */       
/* 406 */       Any any = getAny(dynAny);
/* 407 */       addComponent(b, str, any, dynAny);
/*     */     } 
/* 409 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearData() {
/* 416 */     super.clearData();
/* 417 */     this.names = null;
/* 418 */     this.nameValuePairs = null;
/* 419 */     this.nameDynAnyPairs = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/dynamicany/DynAnyComplexImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */