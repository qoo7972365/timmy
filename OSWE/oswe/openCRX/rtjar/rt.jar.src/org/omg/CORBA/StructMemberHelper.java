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
/*     */ 
/*     */ public abstract class StructMemberHelper
/*     */ {
/*  40 */   private static String _id = "IDL:omg.org/CORBA/StructMember:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, StructMember paramStructMember) {
/*  44 */     OutputStream outputStream = paramAny.create_output_stream();
/*  45 */     paramAny.type(type());
/*  46 */     write(outputStream, paramStructMember);
/*  47 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static StructMember extract(Any paramAny) {
/*  52 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  55 */   private static TypeCode __typeCode = null;
/*     */   private static boolean __active = false;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  59 */     if (__typeCode == null)
/*     */     {
/*  61 */       synchronized (TypeCode.class) {
/*     */         
/*  63 */         if (__typeCode == null) {
/*     */           
/*  65 */           if (__active)
/*     */           {
/*  67 */             return ORB.init().create_recursive_tc(_id);
/*     */           }
/*  69 */           __active = true;
/*  70 */           StructMember[] arrayOfStructMember = new StructMember[3];
/*  71 */           TypeCode typeCode = null;
/*  72 */           typeCode = ORB.init().create_string_tc(0);
/*  73 */           typeCode = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", typeCode);
/*  74 */           arrayOfStructMember[0] = new StructMember("name", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  78 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
/*  79 */           arrayOfStructMember[1] = new StructMember("type", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  83 */           typeCode = IDLTypeHelper.type();
/*  84 */           arrayOfStructMember[2] = new StructMember("type_def", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  88 */           __typeCode = ORB.init().create_struct_tc(id(), "StructMember", arrayOfStructMember);
/*  89 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/*  93 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/*  98 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static StructMember read(InputStream paramInputStream) {
/* 103 */     StructMember structMember = new StructMember();
/* 104 */     structMember.name = paramInputStream.read_string();
/* 105 */     structMember.type = paramInputStream.read_TypeCode();
/* 106 */     structMember.type_def = IDLTypeHelper.read(paramInputStream);
/* 107 */     return structMember;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, StructMember paramStructMember) {
/* 112 */     paramOutputStream.write_string(paramStructMember.name);
/* 113 */     paramOutputStream.write_TypeCode(paramStructMember.type);
/* 114 */     IDLTypeHelper.write(paramOutputStream, paramStructMember.type_def);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/StructMemberHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */