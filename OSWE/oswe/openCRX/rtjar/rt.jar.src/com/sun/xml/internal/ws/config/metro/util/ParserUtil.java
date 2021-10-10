/*    */ package com.sun.xml.internal.ws.config.metro.util;
/*    */ 
/*    */ import com.sun.istack.internal.logging.Logger;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParserUtil
/*    */ {
/* 38 */   private static final Logger LOGGER = Logger.getLogger(ParserUtil.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean parseBooleanValue(String value) throws WebServiceException {
/* 53 */     if ("true".equals(value) || "1".equals(value)) {
/* 54 */       return true;
/*    */     }
/* 56 */     if ("false".equals(value) || "0".equals(value)) {
/* 57 */       return false;
/*    */     }
/*    */     
/* 60 */     throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("invalid boolean value"));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/config/metro/util/ParserUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */