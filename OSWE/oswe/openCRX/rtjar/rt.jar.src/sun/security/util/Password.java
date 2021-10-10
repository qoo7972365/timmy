/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.Console;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.util.Arrays;
/*     */ import sun.misc.SharedSecrets;
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
/*     */ public class Password
/*     */ {
/*     */   private static volatile CharsetEncoder enc;
/*     */   
/*     */   public static char[] readPassword(InputStream paramInputStream) throws IOException {
/*  40 */     return readPassword(paramInputStream, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char[] readPassword(InputStream paramInputStream, boolean paramBoolean) throws IOException {
/*  50 */     char[] arrayOfChar = null;
/*  51 */     byte[] arrayOfByte = null;
/*     */ 
/*     */     
/*     */     try {
/*  55 */       Console console = null;
/*  56 */       if (!paramBoolean && paramInputStream == System.in && (console = System.console()) != null) {
/*  57 */         arrayOfChar = console.readPassword();
/*     */ 
/*     */         
/*  60 */         if (arrayOfChar != null && arrayOfChar.length == 0) {
/*  61 */           return null;
/*     */         }
/*  63 */         arrayOfByte = convertToBytes(arrayOfChar);
/*  64 */         paramInputStream = new ByteArrayInputStream(arrayOfByte);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       char[] arrayOfChar1 = new char[128], arrayOfChar2 = arrayOfChar1;
/*     */       
/*  76 */       int i = arrayOfChar2.length;
/*  77 */       byte b = 0;
/*     */ 
/*     */       
/*  80 */       boolean bool = false;
/*  81 */       while (!bool) {
/*  82 */         int k, j; switch (j = paramInputStream.read()) {
/*     */           case -1:
/*     */           case 10:
/*  85 */             bool = true;
/*     */             continue;
/*     */           
/*     */           case 13:
/*  89 */             k = paramInputStream.read();
/*  90 */             if (k != 10 && k != -1) {
/*  91 */               if (!(paramInputStream instanceof PushbackInputStream)) {
/*  92 */                 paramInputStream = new PushbackInputStream(paramInputStream);
/*     */               }
/*  94 */               ((PushbackInputStream)paramInputStream).unread(k); break;
/*     */             } 
/*  96 */             bool = true;
/*     */             continue;
/*     */         } 
/*     */ 
/*     */         
/* 101 */         if (--i < 0) {
/* 102 */           arrayOfChar2 = new char[b + 128];
/* 103 */           i = arrayOfChar2.length - b - 1;
/* 104 */           System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, b);
/* 105 */           Arrays.fill(arrayOfChar1, ' ');
/* 106 */           arrayOfChar1 = arrayOfChar2;
/*     */         } 
/* 108 */         arrayOfChar2[b++] = (char)j;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 113 */       if (b == 0) {
/* 114 */         return null;
/*     */       }
/*     */       
/* 117 */       char[] arrayOfChar3 = new char[b];
/* 118 */       System.arraycopy(arrayOfChar2, 0, arrayOfChar3, 0, b);
/* 119 */       Arrays.fill(arrayOfChar2, ' ');
/*     */       
/* 121 */       return arrayOfChar3;
/*     */     } finally {
/* 123 */       if (arrayOfChar != null) {
/* 124 */         Arrays.fill(arrayOfChar, ' ');
/*     */       }
/* 126 */       if (arrayOfByte != null) {
/* 127 */         Arrays.fill(arrayOfByte, (byte)0);
/*     */       }
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
/*     */   private static byte[] convertToBytes(char[] paramArrayOfchar) {
/* 140 */     if (enc == null) {
/* 141 */       synchronized (Password.class) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 146 */         enc = SharedSecrets.getJavaIOAccess().charset().newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/*     */       } 
/*     */     }
/* 149 */     byte[] arrayOfByte = new byte[(int)(enc.maxBytesPerChar() * paramArrayOfchar.length)];
/* 150 */     ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
/* 151 */     synchronized (enc) {
/* 152 */       enc.reset().encode(CharBuffer.wrap(paramArrayOfchar), byteBuffer, true);
/*     */     } 
/* 154 */     if (byteBuffer.position() < arrayOfByte.length) {
/* 155 */       arrayOfByte[byteBuffer.position()] = 10;
/*     */     }
/* 157 */     return arrayOfByte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/Password.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */