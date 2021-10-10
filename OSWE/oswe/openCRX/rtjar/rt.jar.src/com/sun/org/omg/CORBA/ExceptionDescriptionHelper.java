/*     */ package com.sun.org.omg.CORBA;
/*     */ 
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.StructMember;
/*     */ import org.omg.CORBA.TCKind;
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
/*     */ public final class ExceptionDescriptionHelper
/*     */ {
/*  37 */   private static String _id = "IDL:omg.org/CORBA/ExceptionDescription:1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, ExceptionDescription paramExceptionDescription) {
/*  45 */     OutputStream outputStream = paramAny.create_output_stream();
/*  46 */     paramAny.type(type());
/*  47 */     write(outputStream, paramExceptionDescription);
/*  48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ExceptionDescription extract(Any paramAny) {
/*  53 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  56 */   private static TypeCode __typeCode = null;
/*     */   private static boolean __active = false;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  60 */     if (__typeCode == null)
/*     */     {
/*  62 */       synchronized (TypeCode.class) {
/*     */         
/*  64 */         if (__typeCode == null) {
/*     */           
/*  66 */           if (__active)
/*     */           {
/*  68 */             return ORB.init().create_recursive_tc(_id);
/*     */           }
/*  70 */           __active = true;
/*  71 */           StructMember[] arrayOfStructMember = new StructMember[5];
/*  72 */           TypeCode typeCode = null;
/*  73 */           typeCode = ORB.init().create_string_tc(0);
/*  74 */           typeCode = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", typeCode);
/*  75 */           arrayOfStructMember[0] = new StructMember("name", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  79 */           typeCode = ORB.init().create_string_tc(0);
/*  80 */           typeCode = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", typeCode);
/*  81 */           arrayOfStructMember[1] = new StructMember("id", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  85 */           typeCode = ORB.init().create_string_tc(0);
/*  86 */           typeCode = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", typeCode);
/*  87 */           arrayOfStructMember[2] = new StructMember("defined_in", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  91 */           typeCode = ORB.init().create_string_tc(0);
/*  92 */           typeCode = ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", typeCode);
/*  93 */           arrayOfStructMember[3] = new StructMember("version", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  97 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
/*  98 */           arrayOfStructMember[4] = new StructMember("type", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 102 */           __typeCode = ORB.init().create_struct_tc(id(), "ExceptionDescription", arrayOfStructMember);
/* 103 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/* 107 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 112 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ExceptionDescription read(InputStream paramInputStream) {
/* 117 */     ExceptionDescription exceptionDescription = new ExceptionDescription();
/* 118 */     exceptionDescription.name = paramInputStream.read_string();
/* 119 */     exceptionDescription.id = paramInputStream.read_string();
/* 120 */     exceptionDescription.defined_in = paramInputStream.read_string();
/* 121 */     exceptionDescription.version = paramInputStream.read_string();
/* 122 */     exceptionDescription.type = paramInputStream.read_TypeCode();
/* 123 */     return exceptionDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, ExceptionDescription paramExceptionDescription) {
/* 128 */     paramOutputStream.write_string(paramExceptionDescription.name);
/* 129 */     paramOutputStream.write_string(paramExceptionDescription.id);
/* 130 */     paramOutputStream.write_string(paramExceptionDescription.defined_in);
/* 131 */     paramOutputStream.write_string(paramExceptionDescription.version);
/* 132 */     paramOutputStream.write_TypeCode(paramExceptionDescription.type);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/ExceptionDescriptionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */