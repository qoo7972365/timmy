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
/*     */ public abstract class UnionMemberHelper
/*     */ {
/*  40 */   private static String _id = "IDL:omg.org/CORBA/UnionMember:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, UnionMember paramUnionMember) {
/*  44 */     OutputStream outputStream = paramAny.create_output_stream();
/*  45 */     paramAny.type(type());
/*  46 */     write(outputStream, paramUnionMember);
/*  47 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static UnionMember extract(Any paramAny) {
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
/*  70 */           StructMember[] arrayOfStructMember = new StructMember[4];
/*  71 */           TypeCode typeCode = null;
/*  72 */           typeCode = ORB.init().create_string_tc(0);
/*  73 */           typeCode = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", typeCode);
/*  74 */           arrayOfStructMember[0] = new StructMember("name", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  78 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_any);
/*  79 */           arrayOfStructMember[1] = new StructMember("label", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  83 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
/*  84 */           arrayOfStructMember[2] = new StructMember("type", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  88 */           typeCode = IDLTypeHelper.type();
/*  89 */           arrayOfStructMember[3] = new StructMember("type_def", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  93 */           __typeCode = ORB.init().create_struct_tc(id(), "UnionMember", arrayOfStructMember);
/*  94 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/*  98 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 103 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static UnionMember read(InputStream paramInputStream) {
/* 108 */     UnionMember unionMember = new UnionMember();
/* 109 */     unionMember.name = paramInputStream.read_string();
/* 110 */     unionMember.label = paramInputStream.read_any();
/* 111 */     unionMember.type = paramInputStream.read_TypeCode();
/* 112 */     unionMember.type_def = IDLTypeHelper.read(paramInputStream);
/* 113 */     return unionMember;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, UnionMember paramUnionMember) {
/* 118 */     paramOutputStream.write_string(paramUnionMember.name);
/* 119 */     paramOutputStream.write_any(paramUnionMember.label);
/* 120 */     paramOutputStream.write_TypeCode(paramUnionMember.type);
/* 121 */     IDLTypeHelper.write(paramOutputStream, paramUnionMember.type_def);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/UnionMemberHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */