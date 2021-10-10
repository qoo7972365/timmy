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
/*     */ public final class AttributeDescriptionHelper
/*     */ {
/*  37 */   private static String _id = "IDL:omg.org/CORBA/AttributeDescription:1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, AttributeDescription paramAttributeDescription) {
/*  45 */     OutputStream outputStream = paramAny.create_output_stream();
/*  46 */     paramAny.type(type());
/*  47 */     write(outputStream, paramAttributeDescription);
/*  48 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static AttributeDescription extract(Any paramAny) {
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
/*  71 */           StructMember[] arrayOfStructMember = new StructMember[6];
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
/* 102 */           typeCode = AttributeModeHelper.type();
/* 103 */           arrayOfStructMember[5] = new StructMember("mode", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/* 107 */           __typeCode = ORB.init().create_struct_tc(id(), "AttributeDescription", arrayOfStructMember);
/* 108 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/* 112 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 117 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static AttributeDescription read(InputStream paramInputStream) {
/* 122 */     AttributeDescription attributeDescription = new AttributeDescription();
/* 123 */     attributeDescription.name = paramInputStream.read_string();
/* 124 */     attributeDescription.id = paramInputStream.read_string();
/* 125 */     attributeDescription.defined_in = paramInputStream.read_string();
/* 126 */     attributeDescription.version = paramInputStream.read_string();
/* 127 */     attributeDescription.type = paramInputStream.read_TypeCode();
/* 128 */     attributeDescription.mode = AttributeModeHelper.read(paramInputStream);
/* 129 */     return attributeDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, AttributeDescription paramAttributeDescription) {
/* 134 */     paramOutputStream.write_string(paramAttributeDescription.name);
/* 135 */     paramOutputStream.write_string(paramAttributeDescription.id);
/* 136 */     paramOutputStream.write_string(paramAttributeDescription.defined_in);
/* 137 */     paramOutputStream.write_string(paramAttributeDescription.version);
/* 138 */     paramOutputStream.write_TypeCode(paramAttributeDescription.type);
/* 139 */     AttributeModeHelper.write(paramOutputStream, paramAttributeDescription.mode);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/AttributeDescriptionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */