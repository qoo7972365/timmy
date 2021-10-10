/*    */ package org.jcp.xml.dsig.internal.dom;
/*    */ 
/*    */ import java.security.InvalidAlgorithmParameterException;
/*    */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DOMEnvelopedTransform
/*    */   extends ApacheTransform
/*    */ {
/*    */   public void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException {
/* 44 */     if (paramTransformParameterSpec != null)
/* 45 */       throw new InvalidAlgorithmParameterException("params must be null"); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMEnvelopedTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */