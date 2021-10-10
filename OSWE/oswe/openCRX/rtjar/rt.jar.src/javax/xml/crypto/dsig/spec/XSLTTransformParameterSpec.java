/*    */ package javax.xml.crypto.dsig.spec;
/*    */ 
/*    */ import javax.xml.crypto.XMLStructure;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class XSLTTransformParameterSpec
/*    */   implements TransformParameterSpec
/*    */ {
/*    */   private XMLStructure stylesheet;
/*    */   
/*    */   public XSLTTransformParameterSpec(XMLStructure paramXMLStructure) {
/* 64 */     if (paramXMLStructure == null) {
/* 65 */       throw new NullPointerException();
/*    */     }
/* 67 */     this.stylesheet = paramXMLStructure;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLStructure getStylesheet() {
/* 76 */     return this.stylesheet;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/spec/XSLTTransformParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */