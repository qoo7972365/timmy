/*     */ package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverContext;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.Document;
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
/*     */ public class ResolverFragment
/*     */   extends ResourceResolverSpi
/*     */ {
/*  46 */   private static Logger log = Logger.getLogger(ResolverFragment.class.getName());
/*     */ 
/*     */   
/*     */   public boolean engineIsThreadSafe() {
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInput engineResolveURI(ResourceResolverContext paramResourceResolverContext) throws ResourceResolverException {
/*     */     Element element;
/*  63 */     Document document1 = paramResourceResolverContext.attr.getOwnerElement().getOwnerDocument();
/*     */     
/*  65 */     Document document2 = null;
/*  66 */     if (paramResourceResolverContext.uriToResolve.equals("")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  71 */       if (log.isLoggable(Level.FINE)) {
/*  72 */         log.log(Level.FINE, "ResolverFragment with empty URI (means complete document)");
/*     */       }
/*  74 */       document2 = document1;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  84 */       String str = paramResourceResolverContext.uriToResolve.substring(1);
/*     */       
/*  86 */       element = document1.getElementById(str);
/*  87 */       if (element == null) {
/*  88 */         Object[] arrayOfObject = { str };
/*  89 */         throw new ResourceResolverException("signature.Verification.MissingID", arrayOfObject, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/*     */       } 
/*     */ 
/*     */       
/*  93 */       if (paramResourceResolverContext.secureValidation) {
/*  94 */         Element element1 = paramResourceResolverContext.attr.getOwnerDocument().getDocumentElement();
/*  95 */         if (!XMLUtils.protectAgainstWrappingAttack(element1, str)) {
/*  96 */           Object[] arrayOfObject = { str };
/*  97 */           throw new ResourceResolverException("signature.Verification.MultipleIDs", arrayOfObject, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 102 */       if (log.isLoggable(Level.FINE)) {
/* 103 */         log.log(Level.FINE, "Try to catch an Element with ID " + str + " and Element was " + element);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 109 */     XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(element);
/* 110 */     xMLSignatureInput.setExcludeComments(true);
/*     */     
/* 112 */     xMLSignatureInput.setMIMEType("text/xml");
/* 113 */     if (paramResourceResolverContext.baseUri != null && paramResourceResolverContext.baseUri.length() > 0) {
/* 114 */       xMLSignatureInput.setSourceURI(paramResourceResolverContext.baseUri.concat(paramResourceResolverContext.uriToResolve));
/*     */     } else {
/* 116 */       xMLSignatureInput.setSourceURI(paramResourceResolverContext.uriToResolve);
/*     */     } 
/* 118 */     return xMLSignatureInput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean engineCanResolveURI(ResourceResolverContext paramResourceResolverContext) {
/* 128 */     if (paramResourceResolverContext.uriToResolve == null) {
/* 129 */       if (log.isLoggable(Level.FINE)) {
/* 130 */         log.log(Level.FINE, "Quick fail for null uri");
/*     */       }
/* 132 */       return false;
/*     */     } 
/*     */     
/* 135 */     if (paramResourceResolverContext.uriToResolve.equals("") || (paramResourceResolverContext.uriToResolve
/* 136 */       .charAt(0) == '#' && !paramResourceResolverContext.uriToResolve.startsWith("#xpointer("))) {
/*     */       
/* 138 */       if (log.isLoggable(Level.FINE)) {
/* 139 */         log.log(Level.FINE, "State I can resolve reference: \"" + paramResourceResolverContext.uriToResolve + "\"");
/*     */       }
/* 141 */       return true;
/*     */     } 
/* 143 */     if (log.isLoggable(Level.FINE)) {
/* 144 */       log.log(Level.FINE, "Do not seem to be able to resolve reference: \"" + paramResourceResolverContext.uriToResolve + "\"");
/*     */     }
/* 146 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/resolver/implementations/ResolverFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */