/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import sun.misc.HexDumpEncoder;
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
/*     */ public abstract class Ber
/*     */ {
/*     */   protected byte[] buf;
/*     */   protected int offset;
/*     */   protected int bufsize;
/*     */   public static final int ASN_BOOLEAN = 1;
/*     */   public static final int ASN_INTEGER = 2;
/*     */   public static final int ASN_BIT_STRING = 3;
/*     */   public static final int ASN_SIMPLE_STRING = 4;
/*     */   public static final int ASN_OCTET_STR = 4;
/*     */   public static final int ASN_NULL = 5;
/*     */   public static final int ASN_OBJECT_ID = 6;
/*     */   public static final int ASN_SEQUENCE = 16;
/*     */   public static final int ASN_SET = 17;
/*     */   public static final int ASN_PRIMITIVE = 0;
/*     */   public static final int ASN_UNIVERSAL = 0;
/*     */   public static final int ASN_CONSTRUCTOR = 32;
/*     */   public static final int ASN_APPLICATION = 64;
/*     */   public static final int ASN_CONTEXT = 128;
/*     */   public static final int ASN_PRIVATE = 192;
/*     */   public static final int ASN_ENUMERATED = 10;
/*     */   
/*     */   public static void dumpBER(OutputStream paramOutputStream, String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*     */     try {
/*  52 */       paramOutputStream.write(10);
/*  53 */       paramOutputStream.write(paramString.getBytes("UTF8"));
/*     */       
/*  55 */       (new HexDumpEncoder()).encodeBuffer(new ByteArrayInputStream(paramArrayOfbyte, paramInt1, paramInt2), paramOutputStream);
/*     */ 
/*     */ 
/*     */       
/*  59 */       paramOutputStream.write(10);
/*  60 */     } catch (IOException iOException) {
/*     */       try {
/*  62 */         paramOutputStream.write("Ber.dumpBER(): error encountered\n"
/*  63 */             .getBytes("UTF8"));
/*  64 */       } catch (IOException iOException1) {}
/*     */     } 
/*     */   }
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
/*     */   static final class EncodeException
/*     */     extends IOException
/*     */   {
/*     */     private static final long serialVersionUID = -5247359637775781768L;
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
/*     */     EncodeException(String param1String) {
/*  99 */       super(param1String);
/*     */     } }
/*     */   
/*     */   static final class DecodeException extends IOException {
/*     */     private static final long serialVersionUID = 8735036969244425583L;
/*     */     
/*     */     DecodeException(String param1String) {
/* 106 */       super(param1String);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/Ber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */