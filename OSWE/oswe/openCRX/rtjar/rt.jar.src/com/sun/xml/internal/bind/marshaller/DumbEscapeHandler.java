/*    */ package com.sun.xml.internal.bind.marshaller;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
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
/*    */ public class DumbEscapeHandler
/*    */   implements CharacterEscapeHandler
/*    */ {
/* 45 */   public static final CharacterEscapeHandler theInstance = new DumbEscapeHandler();
/*    */   
/*    */   public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
/* 48 */     int limit = start + length;
/* 49 */     for (int i = start; i < limit; i++) {
/* 50 */       switch (ch[i]) {
/*    */         case '&':
/* 52 */           out.write("&amp;");
/*    */           break;
/*    */         case '<':
/* 55 */           out.write("&lt;");
/*    */           break;
/*    */         case '>':
/* 58 */           out.write("&gt;");
/*    */           break;
/*    */         case '"':
/* 61 */           if (isAttVal) {
/* 62 */             out.write("&quot;"); break;
/*    */           } 
/* 64 */           out.write(34);
/*    */           break;
/*    */         
/*    */         default:
/* 68 */           if (ch[i] > '') {
/* 69 */             out.write("&#");
/* 70 */             out.write(Integer.toString(ch[i]));
/* 71 */             out.write(59); break;
/*    */           } 
/* 73 */           out.write(ch[i]);
/*    */           break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/marshaller/DumbEscapeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */