/*    */ package com.sun.org.apache.xml.internal.security.transforms;
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
/*    */ public class TransformationException
/*    */   extends XMLSecurityException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public TransformationException() {}
/*    */   
/*    */   public TransformationException(String paramString) {
/* 51 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TransformationException(String paramString, Object[] paramArrayOfObject) {
/* 61 */     super(paramString, paramArrayOfObject);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TransformationException(String paramString, Exception paramException) {
/* 71 */     super(paramString, paramException);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TransformationException(String paramString, Object[] paramArrayOfObject, Exception paramException) {
/* 82 */     super(paramString, paramArrayOfObject, paramException);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/TransformationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */