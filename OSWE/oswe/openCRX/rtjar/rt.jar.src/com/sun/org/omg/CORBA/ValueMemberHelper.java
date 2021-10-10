/*     */ package com.sun.org.omg.CORBA;
/*     */ 
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.StructMember;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.ValueMember;
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
/*     */ public final class ValueMemberHelper
/*     */ {
/*  39 */   private static String _id = "IDL:omg.org/CORBA/ValueMember:1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, ValueMember paramValueMember) {
/*  49 */     OutputStream outputStream = paramAny.create_output_stream();
/*  50 */     paramAny.type(type());
/*  51 */     write(outputStream, paramValueMember);
/*  52 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ValueMember extract(Any paramAny) {
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
/*  77 */           StructMember[] arrayOfStructMember = new StructMember[7];
/*  78 */           TypeCode typeCode = null;
/*  79 */           typeCode = ORB.init().create_string_tc(0);
/*  80 */           typeCode = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", typeCode);
/*  81 */           arrayOfStructMember[0] = new StructMember("name", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  85 */           typeCode = ORB.init().create_string_tc(0);
/*  86 */           typeCode = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", typeCode);
/*  87 */           arrayOfStructMember[1] = new StructMember("id", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  91 */           typeCode = ORB.init().create_string_tc(0);
/*  92 */           typeCode = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", typeCode);
/*  93 */           arrayOfStructMember[2] = new StructMember("defined_in", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  97 */           typeCode = ORB.init().create_string_tc(0);
/*  98 */           typeCode = ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", typeCode);
/*  99 */           arrayOfStructMember[3] = new StructMember("version", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 103 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
/* 104 */           arrayOfStructMember[4] = new StructMember("type", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 108 */           typeCode = IDLTypeHelper.type();
/* 109 */           arrayOfStructMember[5] = new StructMember("type_def", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 113 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_short);
/* 114 */           typeCode = ORB.init().create_alias_tc(VisibilityHelper.id(), "Visibility", typeCode);
/* 115 */           arrayOfStructMember[6] = new StructMember("access", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 119 */           __typeCode = ORB.init().create_struct_tc(id(), "ValueMember", arrayOfStructMember);
/* 120 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/* 124 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 129 */     return _id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ValueMember read(InputStream paramInputStream) {
/* 138 */     ValueMember valueMember = new ValueMember();
/* 139 */     valueMember.name = paramInputStream.read_string();
/* 140 */     valueMember.id = paramInputStream.read_string();
/* 141 */     valueMember.defined_in = paramInputStream.read_string();
/* 142 */     valueMember.version = paramInputStream.read_string();
/* 143 */     valueMember.type = paramInputStream.read_TypeCode();
/* 144 */     valueMember.type_def = IDLTypeHelper.read(paramInputStream);
/* 145 */     valueMember.access = paramInputStream.read_short();
/* 146 */     return valueMember;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, ValueMember paramValueMember) {
/* 153 */     paramOutputStream.write_string(paramValueMember.name);
/* 154 */     paramOutputStream.write_string(paramValueMember.id);
/* 155 */     paramOutputStream.write_string(paramValueMember.defined_in);
/* 156 */     paramOutputStream.write_string(paramValueMember.version);
/* 157 */     paramOutputStream.write_TypeCode(paramValueMember.type);
/* 158 */     IDLTypeHelper.write(paramOutputStream, paramValueMember.type_def);
/* 159 */     paramOutputStream.write_short(paramValueMember.access);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/ValueMemberHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */