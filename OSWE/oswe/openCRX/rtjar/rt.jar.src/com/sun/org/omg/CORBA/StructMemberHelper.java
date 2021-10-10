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
/*     */ 
/*     */ 
/*     */ public final class StructMemberHelper
/*     */ {
/*  39 */   private static String _id = "IDL:omg.org/CORBA/StructMember:1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, StructMember paramStructMember) {
/*  49 */     OutputStream outputStream = paramAny.create_output_stream();
/*  50 */     paramAny.type(type());
/*  51 */     write(outputStream, paramStructMember);
/*  52 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructMember extract(Any paramAny) {
/*  59 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  62 */   private static TypeCode __typeCode = null;
/*     */   private static boolean __active = false;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  66 */     if (__typeCode == null)
/*     */     {
/*  68 */       synchronized (TypeCode.class) {
/*     */         
/*  70 */         if (__typeCode == null) {
/*     */           
/*  72 */           if (__active)
/*     */           {
/*  74 */             return ORB.init().create_recursive_tc(_id);
/*     */           }
/*  76 */           __active = true;
/*  77 */           StructMember[] arrayOfStructMember = new StructMember[3];
/*  78 */           TypeCode typeCode = null;
/*  79 */           typeCode = ORB.init().create_string_tc(0);
/*  80 */           typeCode = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", typeCode);
/*  81 */           arrayOfStructMember[0] = new StructMember("name", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  85 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
/*  86 */           arrayOfStructMember[1] = new StructMember("type", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  90 */           typeCode = IDLTypeHelper.type();
/*  91 */           arrayOfStructMember[2] = new StructMember("type_def", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  95 */           __typeCode = ORB.init().create_struct_tc(id(), "StructMember", arrayOfStructMember);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructMember read(InputStream paramInputStream) {
/* 114 */     StructMember structMember = new StructMember();
/* 115 */     structMember.name = paramInputStream.read_string();
/* 116 */     structMember.type = paramInputStream.read_TypeCode();
/* 117 */     structMember.type_def = IDLTypeHelper.read(paramInputStream);
/* 118 */     return structMember;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, StructMember paramStructMember) {
/* 125 */     paramOutputStream.write_string(paramStructMember.name);
/* 126 */     paramOutputStream.write_TypeCode(paramStructMember.type);
/* 127 */     IDLTypeHelper.write(paramOutputStream, paramStructMember.type_def);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/StructMemberHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */