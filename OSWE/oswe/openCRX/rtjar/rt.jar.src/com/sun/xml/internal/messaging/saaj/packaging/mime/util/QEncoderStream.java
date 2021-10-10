/*    */ package com.sun.xml.internal.messaging.saaj.packaging.mime.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QEncoderStream
/*    */   extends QPEncoderStream
/*    */ {
/*    */   private String specials;
/* 47 */   private static String WORD_SPECIALS = "=_?\"#$%&'(),.:;<>@[\\]^`{|}~";
/* 48 */   private static String TEXT_SPECIALS = "=_?";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public QEncoderStream(OutputStream out, boolean encodingWord) {
/* 57 */     super(out, 2147483647);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 64 */     this.specials = encodingWord ? WORD_SPECIALS : TEXT_SPECIALS;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(int c) throws IOException {
/* 73 */     c &= 0xFF;
/* 74 */     if (c == 32) {
/* 75 */       output(95, false);
/* 76 */     } else if (c < 32 || c >= 127 || this.specials.indexOf(c) >= 0) {
/*    */       
/* 78 */       output(c, true);
/*    */     } else {
/* 80 */       output(c, false);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int encodedLength(byte[] b, boolean encodingWord) {
/* 87 */     int len = 0;
/* 88 */     String specials = encodingWord ? WORD_SPECIALS : TEXT_SPECIALS;
/* 89 */     for (int i = 0; i < b.length; i++) {
/* 90 */       int c = b[i] & 0xFF;
/* 91 */       if (c < 32 || c >= 127 || specials.indexOf(c) >= 0) {
/*    */         
/* 93 */         len += 3;
/*    */       } else {
/* 95 */         len++;
/*    */       } 
/* 97 */     }  return len;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/packaging/mime/util/QEncoderStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */