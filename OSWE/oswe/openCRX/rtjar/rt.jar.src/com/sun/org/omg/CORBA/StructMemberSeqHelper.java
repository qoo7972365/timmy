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
/*     */ 
/*     */ 
/*     */ public final class StructMemberSeqHelper
/*     */ {
/*  39 */   private static String _id = "IDL:omg.org/CORBA/StructMemberSeq:1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, StructMember[] paramArrayOfStructMember) {
/*  49 */     OutputStream outputStream = paramAny.create_output_stream();
/*  50 */     paramAny.type(type());
/*  51 */     write(outputStream, paramArrayOfStructMember);
/*  52 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructMember[] extract(Any paramAny) {
/*  59 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  62 */   private static TypeCode __typeCode = null;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  65 */     if (__typeCode == null) {
/*     */       
/*  67 */       __typeCode = StructMemberHelper.type();
/*  68 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/*  69 */       __typeCode = ORB.init().create_alias_tc(id(), "StructMemberSeq", __typeCode);
/*     */     } 
/*  71 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/*  76 */     return _id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructMember[] read(InputStream paramInputStream) {
/*  85 */     StructMember[] arrayOfStructMember = null;
/*  86 */     int i = paramInputStream.read_long();
/*     */ 
/*     */     
/*  89 */     arrayOfStructMember = new StructMember[i];
/*  90 */     for (byte b = 0; b < arrayOfStructMember.length; b++)
/*  91 */       arrayOfStructMember[b] = StructMemberHelper.read(paramInputStream); 
/*  92 */     return arrayOfStructMember;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, StructMember[] paramArrayOfStructMember) {
/*  99 */     paramOutputStream.write_long(paramArrayOfStructMember.length);
/* 100 */     for (byte b = 0; b < paramArrayOfStructMember.length; b++)
/* 101 */       StructMemberHelper.write(paramOutputStream, paramArrayOfStructMember[b]); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/StructMemberSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */