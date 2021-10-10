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
/*     */ public final class OperationDescriptionHelper
/*     */ {
/*  37 */   private static String _id = "IDL:omg.org/CORBA/OperationDescription:1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, OperationDescription paramOperationDescription) {
/*  45 */     OutputStream outputStream = paramAny.create_output_stream();
/*  46 */     paramAny.type(type());
/*  47 */     write(outputStream, paramOperationDescription);
/*  48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static OperationDescription extract(Any paramAny) {
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
/*  71 */           StructMember[] arrayOfStructMember = new StructMember[9];
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
/*  98 */           arrayOfStructMember[4] = new StructMember("result", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 102 */           typeCode = OperationModeHelper.type();
/* 103 */           arrayOfStructMember[5] = new StructMember("mode", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 107 */           typeCode = ORB.init().create_string_tc(0);
/* 108 */           typeCode = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", typeCode);
/* 109 */           typeCode = ORB.init().create_alias_tc(ContextIdentifierHelper.id(), "ContextIdentifier", typeCode);
/* 110 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 111 */           typeCode = ORB.init().create_alias_tc(ContextIdSeqHelper.id(), "ContextIdSeq", typeCode);
/* 112 */           arrayOfStructMember[6] = new StructMember("contexts", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 116 */           typeCode = ParameterDescriptionHelper.type();
/* 117 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 118 */           typeCode = ORB.init().create_alias_tc(ParDescriptionSeqHelper.id(), "ParDescriptionSeq", typeCode);
/* 119 */           arrayOfStructMember[7] = new StructMember("parameters", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 123 */           typeCode = ExceptionDescriptionHelper.type();
/* 124 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 125 */           typeCode = ORB.init().create_alias_tc(ExcDescriptionSeqHelper.id(), "ExcDescriptionSeq", typeCode);
/* 126 */           arrayOfStructMember[8] = new StructMember("exceptions", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 130 */           __typeCode = ORB.init().create_struct_tc(id(), "OperationDescription", arrayOfStructMember);
/* 131 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/* 135 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 140 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static OperationDescription read(InputStream paramInputStream) {
/* 145 */     OperationDescription operationDescription = new OperationDescription();
/* 146 */     operationDescription.name = paramInputStream.read_string();
/* 147 */     operationDescription.id = paramInputStream.read_string();
/* 148 */     operationDescription.defined_in = paramInputStream.read_string();
/* 149 */     operationDescription.version = paramInputStream.read_string();
/* 150 */     operationDescription.result = paramInputStream.read_TypeCode();
/* 151 */     operationDescription.mode = OperationModeHelper.read(paramInputStream);
/* 152 */     operationDescription.contexts = ContextIdSeqHelper.read(paramInputStream);
/* 153 */     operationDescription.parameters = ParDescriptionSeqHelper.read(paramInputStream);
/* 154 */     operationDescription.exceptions = ExcDescriptionSeqHelper.read(paramInputStream);
/* 155 */     return operationDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, OperationDescription paramOperationDescription) {
/* 160 */     paramOutputStream.write_string(paramOperationDescription.name);
/* 161 */     paramOutputStream.write_string(paramOperationDescription.id);
/* 162 */     paramOutputStream.write_string(paramOperationDescription.defined_in);
/* 163 */     paramOutputStream.write_string(paramOperationDescription.version);
/* 164 */     paramOutputStream.write_TypeCode(paramOperationDescription.result);
/* 165 */     OperationModeHelper.write(paramOutputStream, paramOperationDescription.mode);
/* 166 */     ContextIdSeqHelper.write(paramOutputStream, paramOperationDescription.contexts);
/* 167 */     ParDescriptionSeqHelper.write(paramOutputStream, paramOperationDescription.parameters);
/* 168 */     ExcDescriptionSeqHelper.write(paramOutputStream, paramOperationDescription.exceptions);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/OperationDescriptionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */