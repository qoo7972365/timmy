/*    */ package com.sun.xml.internal.ws.server;
/*    */ 
/*    */ import com.sun.istack.internal.localization.Localizable;
/*    */ import com.sun.xml.internal.ws.util.exception.JAXWSExceptionBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerRtException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public ServerRtException(String key, Object... args) {
/* 36 */     super(key, args);
/*    */   }
/*    */   
/*    */   public ServerRtException(Throwable throwable) {
/* 40 */     super(throwable);
/*    */   }
/*    */   
/*    */   public ServerRtException(Localizable arg) {
/* 44 */     super("server.rt.err", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 48 */     return "com.sun.xml.internal.ws.resources.server";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/ServerRtException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */