/*    */ package jdk.internal.util.xml.impl;
/*    */ 
/*    */ import java.io.Reader;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Input
/*    */ {
/*    */   public String pubid;
/*    */   public String sysid;
/*    */   public String xmlenc;
/*    */   public char xmlver;
/*    */   public Reader src;
/*    */   public char[] chars;
/*    */   public int chLen;
/*    */   public int chIdx;
/*    */   public Input next;
/*    */   
/*    */   public Input(int paramInt) {
/* 64 */     this.chars = new char[paramInt];
/* 65 */     this.chLen = this.chars.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Input(char[] paramArrayOfchar) {
/* 74 */     this.chars = paramArrayOfchar;
/* 75 */     this.chLen = this.chars.length;
/*    */   }
/*    */   
/*    */   public Input() {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/impl/Input.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */