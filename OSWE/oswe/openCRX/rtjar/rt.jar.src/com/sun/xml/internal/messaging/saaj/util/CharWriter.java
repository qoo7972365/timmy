/*    */ package com.sun.xml.internal.messaging.saaj.util;
/*    */ 
/*    */ import java.io.CharArrayWriter;
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
/*    */ public class CharWriter
/*    */   extends CharArrayWriter
/*    */ {
/*    */   public CharWriter() {}
/*    */   
/*    */   public CharWriter(int size) {
/* 38 */     super(size);
/*    */   }
/*    */   
/*    */   public char[] getChars() {
/* 42 */     return this.buf;
/*    */   }
/*    */   
/*    */   public int getCount() {
/* 46 */     return this.count;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/util/CharWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */