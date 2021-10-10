/*    */ package com.sun.org.apache.xml.internal.security.c14n;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CanonicalizationException
/*    */   extends XMLSecurityException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public CanonicalizationException() {}
/*    */   
/*    */   public CanonicalizationException(String paramString) {
/* 53 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CanonicalizationException(String paramString, Object[] paramArrayOfObject) {
/* 63 */     super(paramString, paramArrayOfObject);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CanonicalizationException(String paramString, Exception paramException) {
/* 73 */     super(paramString, paramException);
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
/*    */   public CanonicalizationException(String paramString, Object[] paramArrayOfObject, Exception paramException) {
/* 86 */     super(paramString, paramArrayOfObject, paramException);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/CanonicalizationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */