/*     */ package org.omg.CORBA;
/*     */ 
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
/*     */ public abstract class NameValuePairHelper
/*     */ {
/*  39 */   private static String _id = "IDL:omg.org/CORBA/NameValuePair:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, NameValuePair paramNameValuePair) {
/*  43 */     OutputStream outputStream = paramAny.create_output_stream();
/*  44 */     paramAny.type(type());
/*  45 */     write(outputStream, paramNameValuePair);
/*  46 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static NameValuePair extract(Any paramAny) {
/*  51 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  54 */   private static TypeCode __typeCode = null;
/*     */   private static boolean __active = false;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  58 */     if (__typeCode == null)
/*     */     {
/*  60 */       synchronized (TypeCode.class) {
/*     */         
/*  62 */         if (__typeCode == null) {
/*     */           
/*  64 */           if (__active)
/*     */           {
/*  66 */             return ORB.init().create_recursive_tc(_id);
/*     */           }
/*  68 */           __active = true;
/*  69 */           StructMember[] arrayOfStructMember = new StructMember[2];
/*  70 */           TypeCode typeCode = null;
/*  71 */           typeCode = ORB.init().create_string_tc(0);
/*  72 */           typeCode = ORB.init().create_alias_tc(FieldNameHelper.id(), "FieldName", typeCode);
/*  73 */           arrayOfStructMember[0] = new StructMember("id", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  77 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_any);
/*  78 */           arrayOfStructMember[1] = new StructMember("value", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  82 */           __typeCode = ORB.init().create_struct_tc(id(), "NameValuePair", arrayOfStructMember);
/*  83 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/*  87 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/*  92 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static NameValuePair read(InputStream paramInputStream) {
/*  97 */     NameValuePair nameValuePair = new NameValuePair();
/*  98 */     nameValuePair.id = paramInputStream.read_string();
/*  99 */     nameValuePair.value = paramInputStream.read_any();
/* 100 */     return nameValuePair;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, NameValuePair paramNameValuePair) {
/* 105 */     paramOutputStream.write_string(paramNameValuePair.id);
/* 106 */     paramOutputStream.write_any(paramNameValuePair.value);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/NameValuePairHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */