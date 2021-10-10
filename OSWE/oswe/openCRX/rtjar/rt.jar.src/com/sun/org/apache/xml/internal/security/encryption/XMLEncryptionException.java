/*    */ package com.sun.org.apache.xml.internal.security.encryption;
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
/*    */ 
/*    */ public class XMLEncryptionException
/*    */   extends XMLSecurityException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public XMLEncryptionException() {}
/*    */   
/*    */   public XMLEncryptionException(String paramString) {
/* 49 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLEncryptionException(String paramString, Object[] paramArrayOfObject) {
/* 58 */     super(paramString, paramArrayOfObject);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLEncryptionException(String paramString, Exception paramException) {
/* 67 */     super(paramString, paramException);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLEncryptionException(String paramString, Object[] paramArrayOfObject, Exception paramException) {
/* 78 */     super(paramString, paramArrayOfObject, paramException);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/encryption/XMLEncryptionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */