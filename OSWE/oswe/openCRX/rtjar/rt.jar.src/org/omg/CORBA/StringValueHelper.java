/*     */ package org.omg.CORBA;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.omg.CORBA.portable.BoxedValueHelper;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringValueHelper
/*     */   implements BoxedValueHelper
/*     */ {
/*  58 */   private static String _id = "IDL:omg.org/CORBA/StringValue:1.0";
/*     */   
/*  60 */   private static StringValueHelper _instance = new StringValueHelper();
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, String paramString) {
/*  64 */     OutputStream outputStream = paramAny.create_output_stream();
/*  65 */     paramAny.type(type());
/*  66 */     write(outputStream, paramString);
/*  67 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String extract(Any paramAny) {
/*  72 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  75 */   private static TypeCode __typeCode = null;
/*     */   private static boolean __active = false;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  79 */     if (__typeCode == null)
/*     */     {
/*  81 */       synchronized (TypeCode.class) {
/*     */         
/*  83 */         if (__typeCode == null) {
/*     */           
/*  85 */           if (__active)
/*     */           {
/*  87 */             return ORB.init().create_recursive_tc(_id);
/*     */           }
/*  89 */           __active = true;
/*  90 */           __typeCode = ORB.init().create_string_tc(0);
/*  91 */           __typeCode = ORB.init().create_value_box_tc(_id, "StringValue", __typeCode);
/*  92 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/*  96 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 101 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String read(InputStream paramInputStream) {
/* 106 */     if (!(paramInputStream instanceof InputStream))
/* 107 */       throw new BAD_PARAM(); 
/* 108 */     return (String)((InputStream)paramInputStream).read_value(_instance);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializable read_value(InputStream paramInputStream) {
/* 114 */     return paramInputStream.read_string();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, String paramString) {
/* 120 */     if (!(paramOutputStream instanceof OutputStream))
/* 121 */       throw new BAD_PARAM(); 
/* 122 */     ((OutputStream)paramOutputStream).write_value(paramString, _instance);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write_value(OutputStream paramOutputStream, Serializable paramSerializable) {
/* 127 */     if (!(paramSerializable instanceof String))
/* 128 */       throw new MARSHAL(); 
/* 129 */     String str = (String)paramSerializable;
/* 130 */     paramOutputStream.write_string(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public String get_id() {
/* 135 */     return _id;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/StringValueHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */