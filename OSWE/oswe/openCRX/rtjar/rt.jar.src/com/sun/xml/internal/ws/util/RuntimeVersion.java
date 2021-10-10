/*    */ package com.sun.xml.internal.ws.util;
/*    */ 
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
/*    */ public final class RuntimeVersion
/*    */ {
/*    */   public static final Version VERSION;
/*    */   
/*    */   static {
/* 42 */     Version version = null;
/* 43 */     InputStream in = RuntimeVersion.class.getResourceAsStream("version.properties");
/*    */     try {
/* 45 */       version = Version.create(in);
/*    */     } finally {
/* 47 */       if (in != null) {
/*    */         try {
/* 49 */           in.close();
/* 50 */         } catch (IOException iOException) {}
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 55 */     VERSION = (version == null) ? Version.create(null) : version;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 62 */     return VERSION.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/RuntimeVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */