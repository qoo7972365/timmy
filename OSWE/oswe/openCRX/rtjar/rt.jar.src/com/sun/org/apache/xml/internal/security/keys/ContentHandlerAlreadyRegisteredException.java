/*    */ package com.sun.org.apache.xml.internal.security.keys;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContentHandlerAlreadyRegisteredException
/*    */   extends XMLSecurityException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ContentHandlerAlreadyRegisteredException() {}
/*    */   
/*    */   public ContentHandlerAlreadyRegisteredException(String paramString) {
/* 48 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ContentHandlerAlreadyRegisteredException(String paramString, Object[] paramArrayOfObject) {
/* 58 */     super(paramString, paramArrayOfObject);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ContentHandlerAlreadyRegisteredException(String paramString, Exception paramException) {
/* 68 */     super(paramString, paramException);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ContentHandlerAlreadyRegisteredException(String paramString, Object[] paramArrayOfObject, Exception paramException) {
/* 81 */     super(paramString, paramArrayOfObject, paramException);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/ContentHandlerAlreadyRegisteredException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */