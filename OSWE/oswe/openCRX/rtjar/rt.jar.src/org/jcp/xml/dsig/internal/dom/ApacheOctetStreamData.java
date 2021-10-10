/*    */ package org.jcp.xml.dsig.internal.dom;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*    */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*    */ import java.io.IOException;
/*    */ import javax.xml.crypto.OctetStreamData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApacheOctetStreamData
/*    */   extends OctetStreamData
/*    */   implements ApacheData
/*    */ {
/*    */   private XMLSignatureInput xi;
/*    */   
/*    */   public ApacheOctetStreamData(XMLSignatureInput paramXMLSignatureInput) throws CanonicalizationException, IOException {
/* 44 */     super(paramXMLSignatureInput.getOctetStream(), paramXMLSignatureInput.getSourceURI(), paramXMLSignatureInput.getMIMEType());
/* 45 */     this.xi = paramXMLSignatureInput;
/*    */   }
/*    */   
/*    */   public XMLSignatureInput getXMLSignatureInput() {
/* 49 */     return this.xi;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/ApacheOctetStreamData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */