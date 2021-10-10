/*     */ package com.sun.org.omg.CORBA;
/*     */ 
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.ORB;
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
/*     */ 
/*     */ 
/*     */ public final class ValueMemberSeqHelper
/*     */ {
/*  39 */   private static String _id = "IDL:omg.org/CORBA/ValueMemberSeq:1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, ValueMember[] paramArrayOfValueMember) {
/*  49 */     OutputStream outputStream = paramAny.create_output_stream();
/*  50 */     paramAny.type(type());
/*  51 */     write(outputStream, paramArrayOfValueMember);
/*  52 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ValueMember[] extract(Any paramAny) {
/*  59 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  62 */   private static TypeCode __typeCode = null;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  65 */     if (__typeCode == null) {
/*     */       
/*  67 */       __typeCode = ValueMemberHelper.type();
/*  68 */       __typeCode = ORB.init().create_sequence_tc(0, __typeCode);
/*  69 */       __typeCode = ORB.init().create_alias_tc(id(), "ValueMemberSeq", __typeCode);
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
/*     */   public static ValueMember[] read(InputStream paramInputStream) {
/*  85 */     ValueMember[] arrayOfValueMember = null;
/*  86 */     int i = paramInputStream.read_long();
/*     */ 
/*     */     
/*  89 */     arrayOfValueMember = new ValueMember[i];
/*  90 */     for (byte b = 0; b < arrayOfValueMember.length; b++)
/*  91 */       arrayOfValueMember[b] = ValueMemberHelper.read(paramInputStream); 
/*  92 */     return arrayOfValueMember;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, ValueMember[] paramArrayOfValueMember) {
/*  99 */     paramOutputStream.write_long(paramArrayOfValueMember.length);
/* 100 */     for (byte b = 0; b < paramArrayOfValueMember.length; b++)
/* 101 */       ValueMemberHelper.write(paramOutputStream, paramArrayOfValueMember[b]); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/ValueMemberSeqHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */