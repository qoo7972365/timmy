/*     */ package com.sun.corba.se.impl.corba;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.CDRInputStream;
/*     */ import com.sun.corba.se.impl.encoding.CDROutputStream;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Principal;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.TypeCodePackage.BadKind;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.Streamable;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TCUtility
/*     */ {
/*     */   static void marshalIn(OutputStream paramOutputStream, TypeCode paramTypeCode, long paramLong, Object paramObject) {
/*  65 */     switch (paramTypeCode.kind().value()) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 31:
/*     */         return;
/*     */ 
/*     */       
/*     */       case 2:
/*  73 */         paramOutputStream.write_short((short)(int)(paramLong & 0xFFFFL));
/*     */ 
/*     */       
/*     */       case 4:
/*  77 */         paramOutputStream.write_ushort((short)(int)(paramLong & 0xFFFFL));
/*     */ 
/*     */       
/*     */       case 3:
/*     */       case 17:
/*  82 */         paramOutputStream.write_long((int)(paramLong & 0xFFFFFFFFL));
/*     */ 
/*     */       
/*     */       case 5:
/*  86 */         paramOutputStream.write_ulong((int)(paramLong & 0xFFFFFFFFL));
/*     */ 
/*     */       
/*     */       case 6:
/*  90 */         paramOutputStream.write_float(Float.intBitsToFloat((int)(paramLong & 0xFFFFFFFFL)));
/*     */ 
/*     */       
/*     */       case 7:
/*  94 */         paramOutputStream.write_double(Double.longBitsToDouble(paramLong));
/*     */ 
/*     */       
/*     */       case 8:
/*  98 */         if (paramLong == 0L) {
/*  99 */           paramOutputStream.write_boolean(false);
/*     */         } else {
/* 101 */           paramOutputStream.write_boolean(true);
/*     */         } 
/*     */       
/*     */       case 9:
/* 105 */         paramOutputStream.write_char((char)(int)(paramLong & 0xFFFFL));
/*     */ 
/*     */       
/*     */       case 10:
/* 109 */         paramOutputStream.write_octet((byte)(int)(paramLong & 0xFFL));
/*     */ 
/*     */       
/*     */       case 11:
/* 113 */         paramOutputStream.write_any((Any)paramObject);
/*     */ 
/*     */       
/*     */       case 12:
/* 117 */         paramOutputStream.write_TypeCode((TypeCode)paramObject);
/*     */ 
/*     */       
/*     */       case 13:
/* 121 */         paramOutputStream.write_Principal((Principal)paramObject);
/*     */ 
/*     */       
/*     */       case 14:
/* 125 */         paramOutputStream.write_Object((Object)paramObject);
/*     */ 
/*     */       
/*     */       case 23:
/* 129 */         paramOutputStream.write_longlong(paramLong);
/*     */ 
/*     */       
/*     */       case 24:
/* 133 */         paramOutputStream.write_ulonglong(paramLong);
/*     */ 
/*     */       
/*     */       case 26:
/* 137 */         paramOutputStream.write_wchar((char)(int)(paramLong & 0xFFFFL));
/*     */ 
/*     */       
/*     */       case 18:
/* 141 */         paramOutputStream.write_string((String)paramObject);
/*     */ 
/*     */       
/*     */       case 27:
/* 145 */         paramOutputStream.write_wstring((String)paramObject);
/*     */ 
/*     */       
/*     */       case 29:
/*     */       case 30:
/* 150 */         ((OutputStream)paramOutputStream).write_value((Serializable)paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 28:
/* 156 */         if (paramOutputStream instanceof CDROutputStream) {
/*     */           try {
/* 158 */             ((CDROutputStream)paramOutputStream).write_fixed((BigDecimal)paramObject, paramTypeCode
/* 159 */                 .fixed_digits(), paramTypeCode
/* 160 */                 .fixed_scale());
/* 161 */           } catch (BadKind badKind) {}
/*     */         } else {
/*     */           
/* 164 */           paramOutputStream.write_fixed((BigDecimal)paramObject);
/*     */         } 
/*     */ 
/*     */       
/*     */       case 15:
/*     */       case 16:
/*     */       case 19:
/*     */       case 20:
/*     */       case 21:
/*     */       case 22:
/* 174 */         ((Streamable)paramObject)._write(paramOutputStream);
/*     */ 
/*     */       
/*     */       case 32:
/* 178 */         ((OutputStream)paramOutputStream).write_abstract_interface(paramObject);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get((ORB)paramOutputStream
/* 185 */         .orb(), "rpc.presentation");
/*     */     
/* 187 */     throw oRBUtilSystemException.typecodeNotSupported();
/*     */   }
/*     */ 
/*     */   
/*     */   static void unmarshalIn(InputStream paramInputStream, TypeCode paramTypeCode, long[] paramArrayOflong, Object[] paramArrayOfObject) {
/*     */     ORBUtilSystemException oRBUtilSystemException;
/* 193 */     int i = paramTypeCode.kind().value();
/* 194 */     long l = 0L;
/* 195 */     Object object = paramArrayOfObject[0];
/*     */     
/* 197 */     switch (i) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 31:
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 205 */         l = paramInputStream.read_short() & 0xFFFFL;
/*     */         break;
/*     */       
/*     */       case 4:
/* 209 */         l = paramInputStream.read_ushort() & 0xFFFFL;
/*     */         break;
/*     */       
/*     */       case 3:
/*     */       case 17:
/* 214 */         l = paramInputStream.read_long() & 0xFFFFFFFFL;
/*     */         break;
/*     */       
/*     */       case 5:
/* 218 */         l = paramInputStream.read_ulong() & 0xFFFFFFFFL;
/*     */         break;
/*     */       
/*     */       case 6:
/* 222 */         l = Float.floatToIntBits(paramInputStream.read_float()) & 0xFFFFFFFFL;
/*     */         break;
/*     */       
/*     */       case 7:
/* 226 */         l = Double.doubleToLongBits(paramInputStream.read_double());
/*     */         break;
/*     */       
/*     */       case 9:
/* 230 */         l = paramInputStream.read_char() & 0xFFFFL;
/*     */         break;
/*     */       
/*     */       case 10:
/* 234 */         l = paramInputStream.read_octet() & 0xFFL;
/*     */         break;
/*     */       
/*     */       case 8:
/* 238 */         if (paramInputStream.read_boolean()) {
/* 239 */           l = 1L; break;
/*     */         } 
/* 241 */         l = 0L;
/*     */         break;
/*     */       
/*     */       case 11:
/* 245 */         object = paramInputStream.read_any();
/*     */         break;
/*     */       
/*     */       case 12:
/* 249 */         object = paramInputStream.read_TypeCode();
/*     */         break;
/*     */       
/*     */       case 13:
/* 253 */         object = paramInputStream.read_Principal();
/*     */         break;
/*     */       
/*     */       case 14:
/* 257 */         if (object instanceof Streamable) {
/* 258 */           ((Streamable)object)._read(paramInputStream); break;
/*     */         } 
/* 260 */         object = paramInputStream.read_Object();
/*     */         break;
/*     */       
/*     */       case 23:
/* 264 */         l = paramInputStream.read_longlong();
/*     */         break;
/*     */       
/*     */       case 24:
/* 268 */         l = paramInputStream.read_ulonglong();
/*     */         break;
/*     */       
/*     */       case 26:
/* 272 */         l = paramInputStream.read_wchar() & 0xFFFFL;
/*     */         break;
/*     */       
/*     */       case 18:
/* 276 */         object = paramInputStream.read_string();
/*     */         break;
/*     */       
/*     */       case 27:
/* 280 */         object = paramInputStream.read_wstring();
/*     */         break;
/*     */       
/*     */       case 29:
/*     */       case 30:
/* 285 */         object = ((InputStream)paramInputStream).read_value();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 28:
/*     */         try {
/* 292 */           if (paramInputStream instanceof CDRInputStream) {
/* 293 */             object = ((CDRInputStream)paramInputStream).read_fixed(paramTypeCode.fixed_digits(), paramTypeCode
/* 294 */                 .fixed_scale()); break;
/*     */           } 
/* 296 */           BigDecimal bigDecimal = paramInputStream.read_fixed();
/* 297 */           object = bigDecimal.movePointLeft(paramTypeCode.fixed_scale());
/*     */         }
/* 299 */         catch (BadKind badKind) {}
/*     */         break;
/*     */ 
/*     */       
/*     */       case 15:
/*     */       case 16:
/*     */       case 19:
/*     */       case 20:
/*     */       case 21:
/*     */       case 22:
/* 309 */         ((Streamable)object)._read(paramInputStream);
/*     */         break;
/*     */       
/*     */       case 32:
/* 313 */         object = ((InputStream)paramInputStream).read_abstract_interface();
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 319 */         oRBUtilSystemException = ORBUtilSystemException.get((ORB)paramInputStream
/* 320 */             .orb(), "rpc.presentation");
/*     */         
/* 322 */         throw oRBUtilSystemException.typecodeNotSupported();
/*     */     } 
/*     */     
/* 325 */     paramArrayOfObject[0] = object;
/* 326 */     paramArrayOflong[0] = l;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/TCUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */