/*    */ package com.sun.corba.se.impl.orbutil;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.StringWriter;
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
/*    */ public class HexOutputStream
/*    */   extends OutputStream
/*    */ {
/* 41 */   private static final char[] hex = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private StringWriter writer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HexOutputStream(StringWriter paramStringWriter) {
/* 54 */     this.writer = paramStringWriter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void write(int paramInt) throws IOException {
/* 65 */     this.writer.write(hex[paramInt >> 4 & 0xF]);
/* 66 */     this.writer.write(hex[paramInt >> 0 & 0xF]);
/*    */   }
/*    */   
/*    */   public synchronized void write(byte[] paramArrayOfbyte) throws IOException {
/* 70 */     write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 76 */     for (byte b = 0; b < paramInt2; b++)
/* 77 */       write(paramArrayOfbyte[paramInt1 + b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/HexOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */