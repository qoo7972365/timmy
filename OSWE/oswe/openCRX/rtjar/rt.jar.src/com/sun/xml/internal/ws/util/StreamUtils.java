/*    */ package com.sun.xml.internal.ws.util;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StreamUtils
/*    */ {
/*    */   public static InputStream hasSomeData(InputStream in) {
/* 44 */     if (in != null) {
/*    */       try {
/* 46 */         if (in.available() < 1) {
/* 47 */           if (!in.markSupported()) {
/* 48 */             in = new BufferedInputStream(in);
/*    */           }
/* 50 */           in.mark(1);
/* 51 */           if (in.read() != -1) {
/* 52 */             in.reset();
/*    */           } else {
/* 54 */             in = null;
/*    */           } 
/*    */         } 
/* 57 */       } catch (IOException ioe) {
/* 58 */         in = null;
/*    */       } 
/*    */     }
/* 61 */     return in;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/StreamUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */