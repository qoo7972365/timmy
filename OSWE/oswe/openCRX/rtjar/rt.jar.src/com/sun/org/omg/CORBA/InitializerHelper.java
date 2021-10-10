/*     */ package com.sun.org.omg.CORBA;
/*     */ 
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.StructMember;
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
/*     */ public final class InitializerHelper
/*     */ {
/*  37 */   private static String _id = "IDL:omg.org/CORBA/Initializer:1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, Initializer paramInitializer) {
/*  45 */     OutputStream outputStream = paramAny.create_output_stream();
/*  46 */     paramAny.type(type());
/*  47 */     write(outputStream, paramInitializer);
/*  48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static Initializer extract(Any paramAny) {
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
/*  71 */           StructMember[] arrayOfStructMember = new StructMember[2];
/*  72 */           TypeCode typeCode = null;
/*  73 */           typeCode = StructMemberHelper.type();
/*  74 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/*  75 */           typeCode = ORB.init().create_alias_tc(StructMemberSeqHelper.id(), "StructMemberSeq", typeCode);
/*  76 */           arrayOfStructMember[0] = new StructMember("members", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  80 */           typeCode = ORB.init().create_string_tc(0);
/*  81 */           typeCode = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", typeCode);
/*  82 */           arrayOfStructMember[1] = new StructMember("name", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  86 */           __typeCode = ORB.init().create_struct_tc(id(), "Initializer", arrayOfStructMember);
/*  87 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/*  91 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/*  96 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Initializer read(InputStream paramInputStream) {
/* 101 */     Initializer initializer = new Initializer();
/* 102 */     initializer.members = StructMemberSeqHelper.read(paramInputStream);
/* 103 */     initializer.name = paramInputStream.read_string();
/* 104 */     return initializer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, Initializer paramInitializer) {
/* 109 */     StructMemberSeqHelper.write(paramOutputStream, paramInitializer.members);
/* 110 */     paramOutputStream.write_string(paramInitializer.name);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/InitializerHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */