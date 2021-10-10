/*     */ package com.sun.corba.se.impl.protocol.giopmsgheaders;
/*     */ 
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.UnionMember;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.IOP.TaggedProfile;
/*     */ import org.omg.IOP.TaggedProfileHelper;
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
/*     */ public abstract class TargetAddressHelper
/*     */ {
/*  38 */   private static String _id = "IDL:messages/TargetAddress:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, TargetAddress paramTargetAddress) {
/*  42 */     OutputStream outputStream = paramAny.create_output_stream();
/*  43 */     paramAny.type(type());
/*  44 */     write(outputStream, paramTargetAddress);
/*  45 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static TargetAddress extract(Any paramAny) {
/*  50 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  53 */   private static TypeCode __typeCode = null;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  56 */     if (__typeCode == null) {
/*     */ 
/*     */       
/*  59 */       TypeCode typeCode1 = ORB.init().get_primitive_tc(TCKind.tk_short);
/*  60 */       typeCode1 = ORB.init().create_alias_tc(AddressingDispositionHelper.id(), "AddressingDisposition", typeCode1);
/*  61 */       UnionMember[] arrayOfUnionMember = new UnionMember[3];
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  66 */       Any any = ORB.init().create_any();
/*  67 */       any.insert_short((short)0);
/*  68 */       TypeCode typeCode2 = ORB.init().get_primitive_tc(TCKind.tk_octet);
/*  69 */       typeCode2 = ORB.init().create_sequence_tc(0, typeCode2);
/*  70 */       arrayOfUnionMember[0] = new UnionMember("object_key", any, typeCode2, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  77 */       any = ORB.init().create_any();
/*  78 */       any.insert_short((short)1);
/*  79 */       typeCode2 = TaggedProfileHelper.type();
/*  80 */       arrayOfUnionMember[1] = new UnionMember("profile", any, typeCode2, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  87 */       any = ORB.init().create_any();
/*  88 */       any.insert_short((short)2);
/*  89 */       typeCode2 = IORAddressingInfoHelper.type();
/*  90 */       arrayOfUnionMember[2] = new UnionMember("ior", any, typeCode2, null);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  95 */       __typeCode = ORB.init().create_union_tc(id(), "TargetAddress", typeCode1, arrayOfUnionMember);
/*     */     } 
/*  97 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/* 102 */     return _id; } public static TargetAddress read(InputStream paramInputStream) {
/*     */     byte[] arrayOfByte;
/*     */     int i;
/*     */     TaggedProfile taggedProfile;
/*     */     IORAddressingInfo iORAddressingInfo;
/* 107 */     TargetAddress targetAddress = new TargetAddress();
/* 108 */     short s = 0;
/* 109 */     s = paramInputStream.read_short();
/* 110 */     switch (s) {
/*     */       
/*     */       case 0:
/* 113 */         arrayOfByte = null;
/* 114 */         i = paramInputStream.read_long();
/* 115 */         arrayOfByte = new byte[i];
/* 116 */         paramInputStream.read_octet_array(arrayOfByte, 0, i);
/* 117 */         targetAddress.object_key(arrayOfByte);
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
/* 132 */         return targetAddress;case 1: taggedProfile = null; taggedProfile = TaggedProfileHelper.read(paramInputStream); targetAddress.profile(taggedProfile); return targetAddress;case 2: iORAddressingInfo = null; iORAddressingInfo = IORAddressingInfoHelper.read(paramInputStream); targetAddress.ior(iORAddressingInfo); return targetAddress;
/*     */     } 
/*     */     throw new BAD_OPERATION();
/*     */   }
/*     */   public static void write(OutputStream paramOutputStream, TargetAddress paramTargetAddress) {
/* 137 */     paramOutputStream.write_short(paramTargetAddress.discriminator());
/* 138 */     switch (paramTargetAddress.discriminator()) {
/*     */       
/*     */       case 0:
/* 141 */         paramOutputStream.write_long((paramTargetAddress.object_key()).length);
/* 142 */         paramOutputStream.write_octet_array(paramTargetAddress.object_key(), 0, (paramTargetAddress.object_key()).length);
/*     */         return;
/*     */       case 1:
/* 145 */         TaggedProfileHelper.write(paramOutputStream, paramTargetAddress.profile());
/*     */         return;
/*     */       case 2:
/* 148 */         IORAddressingInfoHelper.write(paramOutputStream, paramTargetAddress.ior());
/*     */         return;
/*     */     } 
/* 151 */     throw new BAD_OPERATION();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/TargetAddressHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */