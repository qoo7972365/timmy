/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyName;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ public final class DOMKeyName
/*     */   extends DOMStructure
/*     */   implements KeyName
/*     */ {
/*     */   private final String name;
/*     */   
/*     */   public DOMKeyName(String paramString) {
/*  56 */     if (paramString == null) {
/*  57 */       throw new NullPointerException("name cannot be null");
/*     */     }
/*  59 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMKeyName(Element paramElement) {
/*  68 */     this.name = paramElement.getFirstChild().getNodeValue();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  72 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/*  78 */     Document document = DOMUtils.getOwnerDocument(paramNode);
/*     */     
/*  80 */     Element element = DOMUtils.createElement(document, "KeyName", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */     
/*  82 */     element.appendChild(document.createTextNode(this.name));
/*  83 */     paramNode.appendChild(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  88 */     if (this == paramObject) {
/*  89 */       return true;
/*     */     }
/*  91 */     if (!(paramObject instanceof KeyName)) {
/*  92 */       return false;
/*     */     }
/*  94 */     KeyName keyName = (KeyName)paramObject;
/*  95 */     return this.name.equals(keyName.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 100 */     int i = 17;
/* 101 */     i = 31 * i + this.name.hashCode();
/*     */     
/* 103 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMKeyName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */