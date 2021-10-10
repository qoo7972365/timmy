/*    */ package com.sun.xml.internal.messaging.saaj.packaging.mime.internet;
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
/*    */ 
/*    */ 
/*    */ class UniqueValue
/*    */ {
/* 53 */   private static int part = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getUniqueBoundaryValue() {
/* 63 */     StringBuffer s = new StringBuffer();
/*    */ 
/*    */     
/* 66 */     s.append("----=_Part_").append(part++).append("_")
/* 67 */       .append(s.hashCode()).append('.')
/* 68 */       .append(System.currentTimeMillis());
/* 69 */     return s.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/packaging/mime/internet/UniqueValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */