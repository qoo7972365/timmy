/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import java.math.BigInteger;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DOMCryptoBinary
/*     */   extends DOMStructure
/*     */ {
/*     */   private final BigInteger bigNum;
/*     */   private final String value;
/*     */   
/*     */   public DOMCryptoBinary(BigInteger paramBigInteger) {
/*  66 */     if (paramBigInteger == null) {
/*  67 */       throw new NullPointerException("bigNum is null");
/*     */     }
/*  69 */     this.bigNum = paramBigInteger;
/*     */     
/*  71 */     this.value = Base64.encode(paramBigInteger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMCryptoBinary(Node paramNode) throws MarshalException {
/*  81 */     this.value = paramNode.getNodeValue();
/*     */     try {
/*  83 */       this.bigNum = Base64.decodeBigIntegerFromText((Text)paramNode);
/*  84 */     } catch (Exception exception) {
/*  85 */       throw new MarshalException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getBigNum() {
/*  95 */     return this.bigNum;
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 100 */     paramNode
/* 101 */       .appendChild(DOMUtils.getOwnerDocument(paramNode).createTextNode(this.value));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMCryptoBinary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */