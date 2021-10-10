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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class OutputUtil
/*    */ {
/* 50 */   private static byte[] newline = new byte[] { 13, 10 };
/*    */   
/*    */   public static void writeln(String s, OutputStream out) throws IOException {
/* 53 */     writeAsAscii(s, out);
/* 54 */     writeln(out);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void writeAsAscii(String s, OutputStream out) throws IOException {
/* 61 */     int len = s.length();
/* 62 */     for (int i = 0; i < len; i++)
/* 63 */       out.write((byte)s.charAt(i)); 
/*    */   }
/*    */   
/*    */   public static void writeln(OutputStream out) throws IOException {
/* 67 */     out.write(newline);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/packaging/mime/util/OutputUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */