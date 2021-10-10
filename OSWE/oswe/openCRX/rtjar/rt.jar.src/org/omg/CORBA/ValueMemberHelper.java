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
/*     */ public abstract class ValueMemberHelper
/*     */ {
/*  40 */   private static String _id = "IDL:omg.org/CORBA/ValueMember:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, ValueMember paramValueMember) {
/*  44 */     OutputStream outputStream = paramAny.create_output_stream();
/*  45 */     paramAny.type(type());
/*  46 */     write(outputStream, paramValueMember);
/*  47 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ValueMember extract(Any paramAny) {
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
/*  70 */           StructMember[] arrayOfStructMember = new StructMember[7];
/*  71 */           TypeCode typeCode = null;
/*  72 */           typeCode = ORB.init().create_string_tc(0);
/*  73 */           typeCode = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", typeCode);
/*  74 */           arrayOfStructMember[0] = new StructMember("name", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  78 */           typeCode = ORB.init().create_string_tc(0);
/*  79 */           typeCode = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", typeCode);
/*  80 */           arrayOfStructMember[1] = new StructMember("id", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  84 */           typeCode = ORB.init().create_string_tc(0);
/*  85 */           typeCode = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", typeCode);
/*  86 */           arrayOfStructMember[2] = new StructMember("defined_in", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  90 */           typeCode = ORB.init().create_string_tc(0);
/*  91 */           typeCode = ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", typeCode);
/*  92 */           arrayOfStructMember[3] = new StructMember("version", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  96 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
/*  97 */           arrayOfStructMember[4] = new StructMember("type", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 101 */           typeCode = IDLTypeHelper.type();
/* 102 */           arrayOfStructMember[5] = new StructMember("type_def", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 106 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_short);
/* 107 */           typeCode = ORB.init().create_alias_tc(VisibilityHelper.id(), "Visibility", typeCode);
/* 108 */           arrayOfStructMember[6] = new StructMember("access", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 112 */           __typeCode = ORB.init().create_struct_tc(id(), "ValueMember", arrayOfStructMember);
/* 113 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/* 117 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 122 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ValueMember read(InputStream paramInputStream) {
/* 127 */     ValueMember valueMember = new ValueMember();
/* 128 */     valueMember.name = paramInputStream.read_string();
/* 129 */     valueMember.id = paramInputStream.read_string();
/* 130 */     valueMember.defined_in = paramInputStream.read_string();
/* 131 */     valueMember.version = paramInputStream.read_string();
/* 132 */     valueMember.type = paramInputStream.read_TypeCode();
/* 133 */     valueMember.type_def = IDLTypeHelper.read(paramInputStream);
/* 134 */     valueMember.access = paramInputStream.read_short();
/* 135 */     return valueMember;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, ValueMember paramValueMember) {
/* 140 */     paramOutputStream.write_string(paramValueMember.name);
/* 141 */     paramOutputStream.write_string(paramValueMember.id);
/* 142 */     paramOutputStream.write_string(paramValueMember.defined_in);
/* 143 */     paramOutputStream.write_string(paramValueMember.version);
/* 144 */     paramOutputStream.write_TypeCode(paramValueMember.type);
/* 145 */     IDLTypeHelper.write(paramOutputStream, paramValueMember.type_def);
/* 146 */     paramOutputStream.write_short(paramValueMember.access);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ValueMemberHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */