/*     */ package com.sun.org.omg.CORBA.ValueDefPackage;
/*     */ 
/*     */ import com.sun.org.omg.CORBA.AttrDescriptionSeqHelper;
/*     */ import com.sun.org.omg.CORBA.AttributeDescriptionHelper;
/*     */ import com.sun.org.omg.CORBA.IdentifierHelper;
/*     */ import com.sun.org.omg.CORBA.InitializerHelper;
/*     */ import com.sun.org.omg.CORBA.InitializerSeqHelper;
/*     */ import com.sun.org.omg.CORBA.OpDescriptionSeqHelper;
/*     */ import com.sun.org.omg.CORBA.OperationDescriptionHelper;
/*     */ import com.sun.org.omg.CORBA.RepositoryIdHelper;
/*     */ import com.sun.org.omg.CORBA.RepositoryIdSeqHelper;
/*     */ import com.sun.org.omg.CORBA.ValueMemberHelper;
/*     */ import com.sun.org.omg.CORBA.ValueMemberSeqHelper;
/*     */ import com.sun.org.omg.CORBA.VersionSpecHelper;
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
/*     */ public final class FullValueDescriptionHelper
/*     */ {
/*  37 */   private static String _id = "IDL:omg.org/CORBA/ValueDef/FullValueDescription:1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, FullValueDescription paramFullValueDescription) {
/*  45 */     OutputStream outputStream = paramAny.create_output_stream();
/*  46 */     paramAny.type(type());
/*  47 */     write(outputStream, paramFullValueDescription);
/*  48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static FullValueDescription extract(Any paramAny) {
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
/*  71 */           StructMember[] arrayOfStructMember = new StructMember[15];
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
/*  85 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_boolean);
/*  86 */           arrayOfStructMember[2] = new StructMember("is_abstract", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  90 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_boolean);
/*  91 */           arrayOfStructMember[3] = new StructMember("is_custom", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  95 */           typeCode = ORB.init().create_string_tc(0);
/*  96 */           typeCode = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", typeCode);
/*  97 */           arrayOfStructMember[4] = new StructMember("defined_in", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 101 */           typeCode = ORB.init().create_string_tc(0);
/* 102 */           typeCode = ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", typeCode);
/* 103 */           arrayOfStructMember[5] = new StructMember("version", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 107 */           typeCode = OperationDescriptionHelper.type();
/* 108 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 109 */           typeCode = ORB.init().create_alias_tc(OpDescriptionSeqHelper.id(), "OpDescriptionSeq", typeCode);
/* 110 */           arrayOfStructMember[6] = new StructMember("operations", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 114 */           typeCode = AttributeDescriptionHelper.type();
/* 115 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 116 */           typeCode = ORB.init().create_alias_tc(AttrDescriptionSeqHelper.id(), "AttrDescriptionSeq", typeCode);
/* 117 */           arrayOfStructMember[7] = new StructMember("attributes", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 121 */           typeCode = ValueMemberHelper.type();
/* 122 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 123 */           typeCode = ORB.init().create_alias_tc(ValueMemberSeqHelper.id(), "ValueMemberSeq", typeCode);
/* 124 */           arrayOfStructMember[8] = new StructMember("members", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 128 */           typeCode = InitializerHelper.type();
/* 129 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 130 */           typeCode = ORB.init().create_alias_tc(InitializerSeqHelper.id(), "InitializerSeq", typeCode);
/* 131 */           arrayOfStructMember[9] = new StructMember("initializers", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 135 */           typeCode = ORB.init().create_string_tc(0);
/* 136 */           typeCode = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", typeCode);
/* 137 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 138 */           typeCode = ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", typeCode);
/* 139 */           arrayOfStructMember[10] = new StructMember("supported_interfaces", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 143 */           typeCode = ORB.init().create_string_tc(0);
/* 144 */           typeCode = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", typeCode);
/* 145 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 146 */           typeCode = ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", typeCode);
/* 147 */           arrayOfStructMember[11] = new StructMember("abstract_base_values", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 151 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_boolean);
/* 152 */           arrayOfStructMember[12] = new StructMember("is_truncatable", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 156 */           typeCode = ORB.init().create_string_tc(0);
/* 157 */           typeCode = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", typeCode);
/* 158 */           arrayOfStructMember[13] = new StructMember("base_value", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 162 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
/* 163 */           arrayOfStructMember[14] = new StructMember("type", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 167 */           __typeCode = ORB.init().create_struct_tc(id(), "FullValueDescription", arrayOfStructMember);
/* 168 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/* 172 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 177 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FullValueDescription read(InputStream paramInputStream) {
/* 182 */     FullValueDescription fullValueDescription = new FullValueDescription();
/* 183 */     fullValueDescription.name = paramInputStream.read_string();
/* 184 */     fullValueDescription.id = paramInputStream.read_string();
/* 185 */     fullValueDescription.is_abstract = paramInputStream.read_boolean();
/* 186 */     fullValueDescription.is_custom = paramInputStream.read_boolean();
/* 187 */     fullValueDescription.defined_in = paramInputStream.read_string();
/* 188 */     fullValueDescription.version = paramInputStream.read_string();
/* 189 */     fullValueDescription.operations = OpDescriptionSeqHelper.read(paramInputStream);
/* 190 */     fullValueDescription.attributes = AttrDescriptionSeqHelper.read(paramInputStream);
/* 191 */     fullValueDescription.members = ValueMemberSeqHelper.read(paramInputStream);
/* 192 */     fullValueDescription.initializers = InitializerSeqHelper.read(paramInputStream);
/* 193 */     fullValueDescription.supported_interfaces = RepositoryIdSeqHelper.read(paramInputStream);
/* 194 */     fullValueDescription.abstract_base_values = RepositoryIdSeqHelper.read(paramInputStream);
/* 195 */     fullValueDescription.is_truncatable = paramInputStream.read_boolean();
/* 196 */     fullValueDescription.base_value = paramInputStream.read_string();
/* 197 */     fullValueDescription.type = paramInputStream.read_TypeCode();
/* 198 */     return fullValueDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, FullValueDescription paramFullValueDescription) {
/* 203 */     paramOutputStream.write_string(paramFullValueDescription.name);
/* 204 */     paramOutputStream.write_string(paramFullValueDescription.id);
/* 205 */     paramOutputStream.write_boolean(paramFullValueDescription.is_abstract);
/* 206 */     paramOutputStream.write_boolean(paramFullValueDescription.is_custom);
/* 207 */     paramOutputStream.write_string(paramFullValueDescription.defined_in);
/* 208 */     paramOutputStream.write_string(paramFullValueDescription.version);
/* 209 */     OpDescriptionSeqHelper.write(paramOutputStream, paramFullValueDescription.operations);
/* 210 */     AttrDescriptionSeqHelper.write(paramOutputStream, paramFullValueDescription.attributes);
/* 211 */     ValueMemberSeqHelper.write(paramOutputStream, paramFullValueDescription.members);
/* 212 */     InitializerSeqHelper.write(paramOutputStream, paramFullValueDescription.initializers);
/* 213 */     RepositoryIdSeqHelper.write(paramOutputStream, paramFullValueDescription.supported_interfaces);
/* 214 */     RepositoryIdSeqHelper.write(paramOutputStream, paramFullValueDescription.abstract_base_values);
/* 215 */     paramOutputStream.write_boolean(paramFullValueDescription.is_truncatable);
/* 216 */     paramOutputStream.write_string(paramFullValueDescription.base_value);
/* 217 */     paramOutputStream.write_TypeCode(paramFullValueDescription.type);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/ValueDefPackage/FullValueDescriptionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */