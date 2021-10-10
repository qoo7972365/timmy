/*    */ package com.sun.xml.internal.bind.marshaller;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.CharsetEncoder;
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
/*    */ public class NioEscapeHandler
/*    */   implements CharacterEscapeHandler
/*    */ {
/*    */   private final CharsetEncoder encoder;
/*    */   
/*    */   public NioEscapeHandler(String charsetName) {
/* 60 */     this.encoder = Charset.forName(charsetName).newEncoder();
/*    */   }
/*    */   
/*    */   public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
/* 64 */     int limit = start + length;
/* 65 */     for (int i = start; i < limit; i++) {
/* 66 */       switch (ch[i]) {
/*    */         case '&':
/* 68 */           out.write("&amp;");
/*    */           break;
/*    */         case '<':
/* 71 */           out.write("&lt;");
/*    */           break;
/*    */         case '>':
/* 74 */           out.write("&gt;");
/*    */           break;
/*    */         case '"':
/* 77 */           if (isAttVal) {
/* 78 */             out.write("&quot;"); break;
/*    */           } 
/* 80 */           out.write(34);
/*    */           break;
/*    */         
/*    */         default:
/* 84 */           if (this.encoder.canEncode(ch[i])) {
/* 85 */             out.write(ch[i]); break;
/*    */           } 
/* 87 */           out.write("&#");
/* 88 */           out.write(Integer.toString(ch[i]));
/* 89 */           out.write(59);
/*    */           break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/marshaller/NioEscapeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */