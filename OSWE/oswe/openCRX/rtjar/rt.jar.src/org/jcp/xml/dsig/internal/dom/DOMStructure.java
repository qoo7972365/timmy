/*    */ package org.jcp.xml.dsig.internal.dom;
/*    */ 
/*    */ import javax.xml.crypto.MarshalException;
/*    */ import javax.xml.crypto.XMLStructure;
/*    */ import javax.xml.crypto.dom.DOMCryptoContext;
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DOMStructure
/*    */   implements XMLStructure
/*    */ {
/*    */   public final boolean isFeatureSupported(String paramString) {
/* 44 */     if (paramString == null) {
/* 45 */       throw new NullPointerException();
/*    */     }
/* 47 */     return false;
/*    */   }
/*    */   
/*    */   public abstract void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMStructure.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */