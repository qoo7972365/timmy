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
/*    */ 
/*    */ public final class DOMBase64Transform
/*    */   extends ApacheTransform
/*    */ {
/*    */   public void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException {
/* 45 */     if (paramTransformParameterSpec != null)
/* 46 */       throw new InvalidAlgorithmParameterException("params must be null"); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMBase64Transform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */