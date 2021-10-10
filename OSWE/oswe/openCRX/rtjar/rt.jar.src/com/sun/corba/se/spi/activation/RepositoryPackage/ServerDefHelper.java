/*     */ package com.sun.corba.se.spi.activation.RepositoryPackage;
/*     */ 
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.StructMember;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ public abstract class ServerDefHelper
/*     */ {
/*  13 */   private static String _id = "IDL:activation/Repository/ServerDef:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, ServerDef paramServerDef) {
/*  17 */     OutputStream outputStream = paramAny.create_output_stream();
/*  18 */     paramAny.type(type());
/*  19 */     write(outputStream, paramServerDef);
/*  20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ServerDef extract(Any paramAny) {
/*  25 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  28 */   private static TypeCode __typeCode = null;
/*     */   private static boolean __active = false;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  32 */     if (__typeCode == null)
/*     */     {
/*  34 */       synchronized (TypeCode.class) {
/*     */         
/*  36 */         if (__typeCode == null) {
/*     */           
/*  38 */           if (__active)
/*     */           {
/*  40 */             return ORB.init().create_recursive_tc(_id);
/*     */           }
/*  42 */           __active = true;
/*  43 */           StructMember[] arrayOfStructMember = new StructMember[5];
/*  44 */           TypeCode typeCode = null;
/*  45 */           typeCode = ORB.init().create_string_tc(0);
/*  46 */           arrayOfStructMember[0] = new StructMember("applicationName", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  50 */           typeCode = ORB.init().create_string_tc(0);
/*  51 */           arrayOfStructMember[1] = new StructMember("serverName", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  55 */           typeCode = ORB.init().create_string_tc(0);
/*  56 */           arrayOfStructMember[2] = new StructMember("serverClassPath", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  60 */           typeCode = ORB.init().create_string_tc(0);
/*  61 */           arrayOfStructMember[3] = new StructMember("serverArgs", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  65 */           typeCode = ORB.init().create_string_tc(0);
/*  66 */           arrayOfStructMember[4] = new StructMember("serverVmArgs", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  70 */           __typeCode = ORB.init().create_struct_tc(id(), "ServerDef", arrayOfStructMember);
/*  71 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/*  75 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/*  80 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ServerDef read(InputStream paramInputStream) {
/*  85 */     ServerDef serverDef = new ServerDef();
/*  86 */     serverDef.applicationName = paramInputStream.read_string();
/*  87 */     serverDef.serverName = paramInputStream.read_string();
/*  88 */     serverDef.serverClassPath = paramInputStream.read_string();
/*  89 */     serverDef.serverArgs = paramInputStream.read_string();
/*  90 */     serverDef.serverVmArgs = paramInputStream.read_string();
/*  91 */     return serverDef;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, ServerDef paramServerDef) {
/*  96 */     paramOutputStream.write_string(paramServerDef.applicationName);
/*  97 */     paramOutputStream.write_string(paramServerDef.serverName);
/*  98 */     paramOutputStream.write_string(paramServerDef.serverClassPath);
/*  99 */     paramOutputStream.write_string(paramServerDef.serverArgs);
/* 100 */     paramOutputStream.write_string(paramServerDef.serverVmArgs);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/RepositoryPackage/ServerDefHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */