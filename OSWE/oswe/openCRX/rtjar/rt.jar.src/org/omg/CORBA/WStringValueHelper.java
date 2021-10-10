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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WStringValueHelper
/*     */   implements BoxedValueHelper
/*     */ {
/*  62 */   private static String _id = "IDL:omg.org/CORBA/WStringValue:1.0";
/*     */   
/*  64 */   private static WStringValueHelper _instance = new WStringValueHelper();
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, String paramString) {
/*  68 */     OutputStream outputStream = paramAny.create_output_stream();
/*  69 */     paramAny.type(type());
/*  70 */     write(outputStream, paramString);
/*  71 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String extract(Any paramAny) {
/*  76 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  79 */   private static TypeCode __typeCode = null;
/*     */   private static boolean __active = false;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  83 */     if (__typeCode == null)
/*     */     {
/*  85 */       synchronized (TypeCode.class) {
/*     */         
/*  87 */         if (__typeCode == null) {
/*     */           
/*  89 */           if (__active)
/*     */           {
/*  91 */             return ORB.init().create_recursive_tc(_id);
/*     */           }
/*  93 */           __active = true;
/*  94 */           __typeCode = ORB.init().create_wstring_tc(0);
/*  95 */           __typeCode = ORB.init().create_value_box_tc(_id, "WStringValue", __typeCode);
/*  96 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/* 100 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 105 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String read(InputStream paramInputStream) {
/* 110 */     if (!(paramInputStream instanceof InputStream))
/* 111 */       throw new BAD_PARAM(); 
/* 112 */     return (String)((InputStream)paramInputStream).read_value(_instance);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializable read_value(InputStream paramInputStream) {
/* 118 */     return paramInputStream.read_wstring();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, String paramString) {
/* 124 */     if (!(paramOutputStream instanceof OutputStream))
/* 125 */       throw new BAD_PARAM(); 
/* 126 */     ((OutputStream)paramOutputStream).write_value(paramString, _instance);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write_value(OutputStream paramOutputStream, Serializable paramSerializable) {
/* 131 */     if (!(paramSerializable instanceof String))
/* 132 */       throw new MARSHAL(); 
/* 133 */     String str = (String)paramSerializable;
/* 134 */     paramOutputStream.write_wstring(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public String get_id() {
/* 139 */     return _id;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/WStringValueHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */