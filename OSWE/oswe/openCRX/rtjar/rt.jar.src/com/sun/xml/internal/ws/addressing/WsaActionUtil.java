/*    */ package com.sun.xml.internal.ws.addressing;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.model.CheckedException;
/*    */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WsaActionUtil
/*    */ {
/*    */   public static final String getDefaultFaultAction(JavaMethod method, CheckedException ce) {
/* 42 */     String tns = method.getOwner().getTargetNamespace();
/* 43 */     String delim = getDelimiter(tns);
/* 44 */     if (tns.endsWith(delim)) {
/* 45 */       tns = tns.substring(0, tns.length() - 1);
/*    */     }
/*    */     
/* 48 */     return tns + delim + method
/* 49 */       .getOwner().getPortTypeName().getLocalPart() + delim + method
/* 50 */       .getOperationName() + delim + "Fault" + delim + ce.getExceptionClass().getSimpleName();
/*    */   }
/*    */   
/*    */   private static String getDelimiter(String tns) {
/* 54 */     String delim = "/";
/*    */     
/*    */     try {
/* 57 */       URI uri = new URI(tns);
/* 58 */       if (uri.getScheme() != null && uri.getScheme().equalsIgnoreCase("urn")) {
/* 59 */         delim = ":";
/*    */       }
/* 61 */     } catch (URISyntaxException e) {
/* 62 */       LOGGER.warning("TargetNamespace of WebService is not a valid URI");
/*    */     } 
/* 64 */     return delim;
/*    */   }
/*    */   
/* 67 */   private static final Logger LOGGER = Logger.getLogger(WsaActionUtil.class.getName());
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/WsaActionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */