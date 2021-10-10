/*     */ package org.omg.PortableServer;
/*     */ 
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CurrentHelper
/*     */ {
/*  57 */   private static String _id = "IDL:omg.org/PortableServer/Current:2.3";
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, Current paramCurrent) {
/*  62 */     OutputStream outputStream = paramAny.create_output_stream();
/*  63 */     paramAny.type(type());
/*  64 */     write(outputStream, paramCurrent);
/*  65 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static Current extract(Any paramAny) {
/*  70 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  73 */   private static TypeCode __typeCode = null;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  76 */     if (__typeCode == null)
/*     */     {
/*  78 */       __typeCode = ORB.init().create_interface_tc(
/*  79 */           id(), "Current");
/*     */     }
/*  81 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/*  86 */     return _id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Current read(InputStream paramInputStream) {
/*  92 */     throw new MARSHAL();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, Current paramCurrent) {
/*  98 */     throw new MARSHAL();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Current narrow(Object paramObject) {
/* 104 */     if (paramObject == null)
/* 105 */       return null; 
/* 106 */     if (paramObject instanceof Current)
/* 107 */       return (Current)paramObject; 
/* 108 */     if (!paramObject._is_a(id()))
/* 109 */       throw new BAD_PARAM(); 
/* 110 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/CurrentHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */