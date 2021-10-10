/*     */ package com.sun.corba.se.impl.dynamicany;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.AnyImpl;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.math.BigDecimal;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.DynamicAny.DynAny;
/*     */ import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
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
/*     */ public class DynAnyUtil
/*     */ {
/*     */   static boolean isConsistentType(TypeCode paramTypeCode) {
/*  49 */     int i = paramTypeCode.kind().value();
/*  50 */     return (i != 13 && i != 31 && i != 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isConstructedDynAny(DynAny paramDynAny) {
/*  58 */     int i = paramDynAny.type().kind().value();
/*  59 */     return (i == 19 || i == 15 || i == 20 || i == 16 || i == 17 || i == 28 || i == 29 || i == 30);
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
/*     */   static DynAny createMostDerivedDynAny(Any paramAny, ORB paramORB, boolean paramBoolean) throws InconsistentTypeCode {
/*  72 */     if (paramAny == null || !isConsistentType(paramAny.type())) {
/*  73 */       throw new InconsistentTypeCode();
/*     */     }
/*  75 */     switch (paramAny.type().kind().value()) {
/*     */       case 19:
/*  77 */         return new DynSequenceImpl(paramORB, paramAny, paramBoolean);
/*     */       case 15:
/*  79 */         return new DynStructImpl(paramORB, paramAny, paramBoolean);
/*     */       case 20:
/*  81 */         return new DynArrayImpl(paramORB, paramAny, paramBoolean);
/*     */       case 16:
/*  83 */         return new DynUnionImpl(paramORB, paramAny, paramBoolean);
/*     */       case 17:
/*  85 */         return new DynEnumImpl(paramORB, paramAny, paramBoolean);
/*     */       case 28:
/*  87 */         return new DynFixedImpl(paramORB, paramAny, paramBoolean);
/*     */       case 29:
/*  89 */         return new DynValueImpl(paramORB, paramAny, paramBoolean);
/*     */       case 30:
/*  91 */         return new DynValueBoxImpl(paramORB, paramAny, paramBoolean);
/*     */     } 
/*  93 */     return new DynAnyBasicImpl(paramORB, paramAny, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static DynAny createMostDerivedDynAny(TypeCode paramTypeCode, ORB paramORB) throws InconsistentTypeCode {
/* 100 */     if (paramTypeCode == null || !isConsistentType(paramTypeCode)) {
/* 101 */       throw new InconsistentTypeCode();
/*     */     }
/* 103 */     switch (paramTypeCode.kind().value()) {
/*     */       case 19:
/* 105 */         return new DynSequenceImpl(paramORB, paramTypeCode);
/*     */       case 15:
/* 107 */         return new DynStructImpl(paramORB, paramTypeCode);
/*     */       case 20:
/* 109 */         return new DynArrayImpl(paramORB, paramTypeCode);
/*     */       case 16:
/* 111 */         return new DynUnionImpl(paramORB, paramTypeCode);
/*     */       case 17:
/* 113 */         return new DynEnumImpl(paramORB, paramTypeCode);
/*     */       case 28:
/* 115 */         return new DynFixedImpl(paramORB, paramTypeCode);
/*     */       case 29:
/* 117 */         return new DynValueImpl(paramORB, paramTypeCode);
/*     */       case 30:
/* 119 */         return new DynValueBoxImpl(paramORB, paramTypeCode);
/*     */     } 
/* 121 */     return new DynAnyBasicImpl(paramORB, paramTypeCode);
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
/*     */   static Any extractAnyFromStream(TypeCode paramTypeCode, InputStream paramInputStream, ORB paramORB) {
/* 139 */     return AnyImpl.extractAnyFromStream(paramTypeCode, paramInputStream, paramORB);
/*     */   }
/*     */ 
/*     */   
/*     */   static Any createDefaultAnyOfType(TypeCode paramTypeCode, ORB paramORB) {
/* 144 */     ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get(paramORB, "rpc.presentation");
/*     */ 
/*     */     
/* 147 */     Any any = paramORB.create_any();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     switch (paramTypeCode.kind().value()) {
/*     */       
/*     */       case 8:
/* 160 */         any.insert_boolean(false);
/*     */ 
/*     */       
/*     */       case 2:
/* 164 */         any.insert_short((short)0);
/*     */ 
/*     */       
/*     */       case 4:
/* 168 */         any.insert_ushort((short)0);
/*     */ 
/*     */       
/*     */       case 3:
/* 172 */         any.insert_long(0);
/*     */ 
/*     */       
/*     */       case 5:
/* 176 */         any.insert_ulong(0);
/*     */ 
/*     */       
/*     */       case 23:
/* 180 */         any.insert_longlong(0L);
/*     */ 
/*     */       
/*     */       case 24:
/* 184 */         any.insert_ulonglong(0L);
/*     */ 
/*     */       
/*     */       case 6:
/* 188 */         any.insert_float(0.0F);
/*     */ 
/*     */       
/*     */       case 7:
/* 192 */         any.insert_double(0.0D);
/*     */ 
/*     */       
/*     */       case 10:
/* 196 */         any.insert_octet((byte)0);
/*     */ 
/*     */       
/*     */       case 9:
/* 200 */         any.insert_char(false);
/*     */ 
/*     */       
/*     */       case 26:
/* 204 */         any.insert_wchar(false);
/*     */ 
/*     */ 
/*     */       
/*     */       case 18:
/* 209 */         any.type(paramTypeCode);
/*     */         
/* 211 */         any.insert_string("");
/*     */ 
/*     */ 
/*     */       
/*     */       case 27:
/* 216 */         any.type(paramTypeCode);
/*     */         
/* 218 */         any.insert_wstring("");
/*     */ 
/*     */       
/*     */       case 14:
/* 222 */         any.insert_Object(null);
/*     */ 
/*     */ 
/*     */       
/*     */       case 12:
/* 227 */         any.insert_TypeCode(any.type());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 11:
/* 234 */         any.insert_any(paramORB.create_any());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 15:
/*     */       case 16:
/*     */       case 17:
/*     */       case 19:
/*     */       case 20:
/*     */       case 22:
/*     */       case 29:
/*     */       case 30:
/* 249 */         any.type(paramTypeCode);
/*     */       
/*     */       case 28:
/* 252 */         any.insert_fixed(new BigDecimal("0.0"), paramTypeCode);
/*     */       
/*     */       case 1:
/*     */       case 13:
/*     */       case 21:
/*     */       case 31:
/*     */       case 32:
/* 259 */         any.type(paramTypeCode);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/* 270 */         return any;
/*     */       case 25:
/*     */         throw oRBUtilSystemException.tkLongDoubleNotSupported();
/*     */     } 
/*     */     throw oRBUtilSystemException.typecodeNotSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Any copy(Any paramAny, ORB paramORB) {
/* 281 */     return (Any)new AnyImpl(paramORB, paramAny);
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
/*     */   static DynAny convertToNative(DynAny paramDynAny, ORB paramORB) {
/* 297 */     if (paramDynAny instanceof DynAnyImpl) {
/* 298 */       return paramDynAny;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 303 */       return createMostDerivedDynAny(paramDynAny.to_any(), paramORB, true);
/* 304 */     } catch (InconsistentTypeCode inconsistentTypeCode) {
/* 305 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isInitialized(Any paramAny) {
/* 314 */     boolean bool = ((AnyImpl)paramAny).isInitialized();
/* 315 */     switch (paramAny.type().kind().value()) {
/*     */       case 18:
/* 317 */         return (bool && paramAny.extract_string() != null);
/*     */       case 27:
/* 319 */         return (bool && paramAny.extract_wstring() != null);
/*     */     } 
/* 321 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean set_current_component(DynAny paramDynAny1, DynAny paramDynAny2) {
/* 327 */     if (paramDynAny2 != null) {
/*     */       try {
/* 329 */         paramDynAny1.rewind();
/*     */         do {
/* 331 */           if (paramDynAny1.current_component() == paramDynAny2)
/* 332 */             return true; 
/* 333 */         } while (paramDynAny1.next());
/* 334 */       } catch (TypeMismatch typeMismatch) {}
/*     */     }
/* 336 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/dynamicany/DynAnyUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */