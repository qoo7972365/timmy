/*    */ package com.sun.xml.internal.messaging.saaj.util;
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
/*    */ public class ParseUtil
/*    */ {
/*    */   private static char unescape(String s, int i) {
/* 36 */     return (char)Integer.parseInt(s.substring(i + 1, i + 3), 16);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String decode(String s) {
/* 45 */     StringBuffer sb = new StringBuffer();
/*    */     
/* 47 */     int i = 0;
/* 48 */     while (i < s.length()) {
/* 49 */       char c = s.charAt(i);
/*    */ 
/*    */       
/* 52 */       if (c != '%') {
/* 53 */         i++;
/*    */       } else {
/*    */         try {
/* 56 */           c = unescape(s, i);
/* 57 */           i += 3;
/*    */           
/* 59 */           if ((c & 0x80) != 0) {
/* 60 */             char c2; char c3; switch (c >> 4) { case 12:
/*    */               case 13:
/* 62 */                 c2 = unescape(s, i);
/* 63 */                 i += 3;
/* 64 */                 c = (char)((c & 0x1F) << 6 | c2 & 0x3F);
/*    */                 break;
/*    */               
/*    */               case 14:
/* 68 */                 c2 = unescape(s, i);
/* 69 */                 i += 3;
/* 70 */                 c3 = unescape(s, i);
/* 71 */                 i += 3;
/* 72 */                 c = (char)((c & 0xF) << 12 | (c2 & 0x3F) << 6 | c3 & 0x3F);
/*    */                 break;
/*    */ 
/*    */ 
/*    */               
/*    */               default:
/* 78 */                 throw new IllegalArgumentException(); }
/*    */           
/*    */           } 
/* 81 */         } catch (NumberFormatException e) {
/* 82 */           throw new IllegalArgumentException();
/*    */         } 
/*    */       } 
/*    */       
/* 86 */       sb.append(c);
/*    */     } 
/*    */     
/* 89 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/util/ParseUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */