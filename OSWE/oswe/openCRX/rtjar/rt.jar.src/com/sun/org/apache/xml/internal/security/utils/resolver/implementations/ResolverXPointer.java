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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolverXPointer
/*     */   extends ResourceResolverSpi
/*     */ {
/*  53 */   private static Logger log = Logger.getLogger(ResolverXPointer.class.getName());
/*     */   
/*     */   private static final String XP = "#xpointer(id(";
/*  56 */   private static final int XP_LENGTH = "#xpointer(id(".length();
/*     */ 
/*     */   
/*     */   public boolean engineIsThreadSafe() {
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInput engineResolveURI(ResourceResolverContext paramResourceResolverContext) throws ResourceResolverException {
/*     */     Element element;
/*  70 */     Document document1 = null;
/*  71 */     Document document2 = paramResourceResolverContext.attr.getOwnerElement().getOwnerDocument();
/*     */     
/*  73 */     if (isXPointerSlash(paramResourceResolverContext.uriToResolve)) {
/*  74 */       document1 = document2;
/*  75 */     } else if (isXPointerId(paramResourceResolverContext.uriToResolve)) {
/*  76 */       String str = getXPointerId(paramResourceResolverContext.uriToResolve);
/*  77 */       element = document2.getElementById(str);
/*     */       
/*  79 */       if (paramResourceResolverContext.secureValidation) {
/*  80 */         Element element1 = paramResourceResolverContext.attr.getOwnerDocument().getDocumentElement();
/*  81 */         if (!XMLUtils.protectAgainstWrappingAttack(element1, str)) {
/*  82 */           Object[] arrayOfObject = { str };
/*  83 */           throw new ResourceResolverException("signature.Verification.MultipleIDs", arrayOfObject, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  89 */       if (element == null) {
/*  90 */         Object[] arrayOfObject = { str };
/*     */         
/*  92 */         throw new ResourceResolverException("signature.Verification.MissingID", arrayOfObject, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  98 */     XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(element);
/*     */     
/* 100 */     xMLSignatureInput.setMIMEType("text/xml");
/* 101 */     if (paramResourceResolverContext.baseUri != null && paramResourceResolverContext.baseUri.length() > 0) {
/* 102 */       xMLSignatureInput.setSourceURI(paramResourceResolverContext.baseUri.concat(paramResourceResolverContext.uriToResolve));
/*     */     } else {
/* 104 */       xMLSignatureInput.setSourceURI(paramResourceResolverContext.uriToResolve);
/*     */     } 
/*     */     
/* 107 */     return xMLSignatureInput;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean engineCanResolveURI(ResourceResolverContext paramResourceResolverContext) {
/* 114 */     if (paramResourceResolverContext.uriToResolve == null) {
/* 115 */       return false;
/*     */     }
/* 117 */     if (isXPointerSlash(paramResourceResolverContext.uriToResolve) || isXPointerId(paramResourceResolverContext.uriToResolve)) {
/* 118 */       return true;
/*     */     }
/*     */     
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isXPointerSlash(String paramString) {
/* 131 */     if (paramString.equals("#xpointer(/)")) {
/* 132 */       return true;
/*     */     }
/*     */     
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isXPointerId(String paramString) {
/* 145 */     if (paramString.startsWith("#xpointer(id(") && paramString.endsWith("))")) {
/* 146 */       String str = paramString.substring(XP_LENGTH, paramString.length() - 2);
/*     */       
/* 148 */       int i = str.length() - 1;
/* 149 */       if ((str.charAt(0) == '"' && str.charAt(i) == '"') || (str
/* 150 */         .charAt(0) == '\'' && str.charAt(i) == '\'')) {
/* 151 */         if (log.isLoggable(Level.FINE)) {
/* 152 */           log.log(Level.FINE, "Id = " + str.substring(1, i));
/*     */         }
/* 154 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getXPointerId(String paramString) {
/* 168 */     if (paramString.startsWith("#xpointer(id(") && paramString.endsWith("))")) {
/* 169 */       String str = paramString.substring(XP_LENGTH, paramString.length() - 2);
/*     */       
/* 171 */       int i = str.length() - 1;
/* 172 */       if ((str.charAt(0) == '"' && str.charAt(i) == '"') || (str
/* 173 */         .charAt(0) == '\'' && str.charAt(i) == '\'')) {
/* 174 */         return str.substring(1, i);
/*     */       }
/*     */     } 
/*     */     
/* 178 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/resolver/implementations/ResolverXPointer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */