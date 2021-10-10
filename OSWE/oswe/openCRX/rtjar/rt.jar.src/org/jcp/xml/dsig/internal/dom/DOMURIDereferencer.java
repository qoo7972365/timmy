/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.Init;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReference;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMURIReference;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class DOMURIDereferencer
/*     */   implements URIDereferencer
/*     */ {
/*  50 */   static final URIDereferencer INSTANCE = new DOMURIDereferencer();
/*     */ 
/*     */ 
/*     */   
/*     */   private DOMURIDereferencer() {
/*  55 */     Init.init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Data dereference(URIReference paramURIReference, XMLCryptoContext paramXMLCryptoContext) throws URIReferenceException {
/*  61 */     if (paramURIReference == null) {
/*  62 */       throw new NullPointerException("uriRef cannot be null");
/*     */     }
/*  64 */     if (paramXMLCryptoContext == null) {
/*  65 */       throw new NullPointerException("context cannot be null");
/*     */     }
/*     */     
/*  68 */     DOMURIReference dOMURIReference = (DOMURIReference)paramURIReference;
/*  69 */     Attr attr = (Attr)dOMURIReference.getHere();
/*  70 */     String str1 = paramURIReference.getURI();
/*  71 */     DOMCryptoContext dOMCryptoContext = (DOMCryptoContext)paramXMLCryptoContext;
/*  72 */     String str2 = paramXMLCryptoContext.getBaseURI();
/*     */     
/*  74 */     boolean bool = Utils.secureValidation(paramXMLCryptoContext);
/*     */     
/*  76 */     if (bool && Policy.restrictReferenceUriScheme(str1)) {
/*  77 */       throw new URIReferenceException("Uri " + str1 + " is forbidden when secure validation is enabled");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  82 */     if (str1 != null && str1.length() != 0 && str1.charAt(0) == '#') {
/*  83 */       String str = str1.substring(1);
/*     */       
/*  85 */       if (str.startsWith("xpointer(id(")) {
/*  86 */         int i = str.indexOf('\'');
/*  87 */         int j = str.indexOf('\'', i + 1);
/*  88 */         str = str.substring(i + 1, j);
/*     */       } 
/*     */ 
/*     */       
/*  92 */       Element element = attr.getOwnerDocument().getElementById(str);
/*  93 */       if (element == null)
/*     */       {
/*  95 */         element = dOMCryptoContext.getElementById(str);
/*     */       }
/*  97 */       if (element != null) {
/*  98 */         if (bool && Policy.restrictDuplicateIds()) {
/*  99 */           Element element1 = element.getOwnerDocument().getDocumentElement();
/* 100 */           if (!XMLUtils.protectAgainstWrappingAttack(element1, element, str)) {
/* 101 */             String str3 = "Multiple Elements with the same ID " + str + " detected when secure validation is enabled";
/*     */ 
/*     */             
/* 104 */             throw new URIReferenceException(str3);
/*     */           } 
/*     */         } 
/*     */         
/* 108 */         XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(element);
/* 109 */         if (!str1.substring(1).startsWith("xpointer(id(")) {
/* 110 */           xMLSignatureInput.setExcludeComments(true);
/*     */         }
/*     */         
/* 113 */         xMLSignatureInput.setMIMEType("text/xml");
/* 114 */         if (str2 != null && str2.length() > 0) {
/* 115 */           xMLSignatureInput.setSourceURI(str2.concat(attr.getNodeValue()));
/*     */         } else {
/* 117 */           xMLSignatureInput.setSourceURI(attr.getNodeValue());
/*     */         } 
/* 119 */         return new ApacheNodeSetData(xMLSignatureInput);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 125 */       ResourceResolver resourceResolver = ResourceResolver.getInstance(attr, str2, false);
/* 126 */       XMLSignatureInput xMLSignatureInput = resourceResolver.resolve(attr, str2, false);
/*     */       
/* 128 */       if (xMLSignatureInput.isOctetStream()) {
/* 129 */         return new ApacheOctetStreamData(xMLSignatureInput);
/*     */       }
/* 131 */       return new ApacheNodeSetData(xMLSignatureInput);
/*     */     }
/* 133 */     catch (Exception exception) {
/* 134 */       throw new URIReferenceException(exception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMURIDereferencer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */