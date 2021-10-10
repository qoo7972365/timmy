/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class XMLDSigRI
/*     */   extends Provider
/*     */ {
/*     */   static final long serialVersionUID = -5049765099299494554L;
/*     */   private static final String INFO = "XMLDSig (DOM XMLSignatureFactory; DOM KeyInfoFactory; C14N 1.0, C14N 1.1, Exclusive C14N, Base64, Enveloped, XPath, XPath2, XSLT TransformServices)";
/*     */   
/*     */   public XMLDSigRI() {
/*  64 */     super("XMLDSig", 1.8D, "XMLDSig (DOM XMLSignatureFactory; DOM KeyInfoFactory; C14N 1.0, C14N 1.1, Exclusive C14N, Base64, Enveloped, XPath, XPath2, XSLT TransformServices)");
/*     */     
/*  66 */     final HashMap<Object, Object> map = new HashMap<>();
/*  67 */     hashMap.put("XMLSignatureFactory.DOM", "org.jcp.xml.dsig.internal.dom.DOMXMLSignatureFactory");
/*     */     
/*  69 */     hashMap.put("KeyInfoFactory.DOM", "org.jcp.xml.dsig.internal.dom.DOMKeyInfoFactory");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     hashMap.put("TransformService.http://www.w3.org/TR/2001/REC-xml-c14n-20010315", "org.jcp.xml.dsig.internal.dom.DOMCanonicalXMLC14NMethod");
/*     */     
/*  76 */     hashMap.put("Alg.Alias.TransformService.INCLUSIVE", "http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
/*     */     
/*  78 */     hashMap.put("TransformService.http://www.w3.org/TR/2001/REC-xml-c14n-20010315 MechanismType", "DOM");
/*     */ 
/*     */ 
/*     */     
/*  82 */     hashMap.put("TransformService.http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments", "org.jcp.xml.dsig.internal.dom.DOMCanonicalXMLC14NMethod");
/*     */ 
/*     */     
/*  85 */     hashMap.put("Alg.Alias.TransformService.INCLUSIVE_WITH_COMMENTS", "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
/*     */     
/*  87 */     hashMap.put("TransformService.http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments MechanismType", "DOM");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     hashMap.put("TransformService.http://www.w3.org/2006/12/xml-c14n11", "org.jcp.xml.dsig.internal.dom.DOMCanonicalXMLC14N11Method");
/*     */     
/*  94 */     hashMap.put("TransformService.http://www.w3.org/2006/12/xml-c14n11 MechanismType", "DOM");
/*     */ 
/*     */ 
/*     */     
/*  98 */     hashMap.put("TransformService.http://www.w3.org/2006/12/xml-c14n11#WithComments", "org.jcp.xml.dsig.internal.dom.DOMCanonicalXMLC14N11Method");
/*     */     
/* 100 */     hashMap.put("TransformService.http://www.w3.org/2006/12/xml-c14n11#WithComments MechanismType", "DOM");
/*     */ 
/*     */ 
/*     */     
/* 104 */     hashMap.put("TransformService.http://www.w3.org/2001/10/xml-exc-c14n#", "org.jcp.xml.dsig.internal.dom.DOMExcC14NMethod");
/*     */     
/* 106 */     hashMap.put("Alg.Alias.TransformService.EXCLUSIVE", "http://www.w3.org/2001/10/xml-exc-c14n#");
/*     */     
/* 108 */     hashMap.put("TransformService.http://www.w3.org/2001/10/xml-exc-c14n# MechanismType", "DOM");
/*     */ 
/*     */ 
/*     */     
/* 112 */     hashMap.put("TransformService.http://www.w3.org/2001/10/xml-exc-c14n#WithComments", "org.jcp.xml.dsig.internal.dom.DOMExcC14NMethod");
/*     */ 
/*     */     
/* 115 */     hashMap.put("Alg.Alias.TransformService.EXCLUSIVE_WITH_COMMENTS", "http://www.w3.org/2001/10/xml-exc-c14n#WithComments");
/*     */     
/* 117 */     hashMap.put("TransformService.http://www.w3.org/2001/10/xml-exc-c14n#WithComments MechanismType", "DOM");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     hashMap.put("TransformService.http://www.w3.org/2000/09/xmldsig#base64", "org.jcp.xml.dsig.internal.dom.DOMBase64Transform");
/*     */     
/* 124 */     hashMap.put("Alg.Alias.TransformService.BASE64", "http://www.w3.org/2000/09/xmldsig#base64");
/* 125 */     hashMap.put("TransformService.http://www.w3.org/2000/09/xmldsig#base64 MechanismType", "DOM");
/*     */ 
/*     */ 
/*     */     
/* 129 */     hashMap.put("TransformService.http://www.w3.org/2000/09/xmldsig#enveloped-signature", "org.jcp.xml.dsig.internal.dom.DOMEnvelopedTransform");
/*     */     
/* 131 */     hashMap.put("Alg.Alias.TransformService.ENVELOPED", "http://www.w3.org/2000/09/xmldsig#enveloped-signature");
/* 132 */     hashMap.put("TransformService.http://www.w3.org/2000/09/xmldsig#enveloped-signature MechanismType", "DOM");
/*     */ 
/*     */ 
/*     */     
/* 136 */     hashMap.put("TransformService.http://www.w3.org/2002/06/xmldsig-filter2", "org.jcp.xml.dsig.internal.dom.DOMXPathFilter2Transform");
/*     */     
/* 138 */     hashMap.put("Alg.Alias.TransformService.XPATH2", "http://www.w3.org/2002/06/xmldsig-filter2");
/* 139 */     hashMap.put("TransformService.http://www.w3.org/2002/06/xmldsig-filter2 MechanismType", "DOM");
/*     */ 
/*     */ 
/*     */     
/* 143 */     hashMap.put("TransformService.http://www.w3.org/TR/1999/REC-xpath-19991116", "org.jcp.xml.dsig.internal.dom.DOMXPathTransform");
/*     */     
/* 145 */     hashMap.put("Alg.Alias.TransformService.XPATH", "http://www.w3.org/TR/1999/REC-xpath-19991116");
/* 146 */     hashMap.put("TransformService.http://www.w3.org/TR/1999/REC-xpath-19991116 MechanismType", "DOM");
/*     */ 
/*     */ 
/*     */     
/* 150 */     hashMap.put("TransformService.http://www.w3.org/TR/1999/REC-xslt-19991116", "org.jcp.xml.dsig.internal.dom.DOMXSLTTransform");
/*     */     
/* 152 */     hashMap.put("Alg.Alias.TransformService.XSLT", "http://www.w3.org/TR/1999/REC-xslt-19991116");
/* 153 */     hashMap.put("TransformService.http://www.w3.org/TR/1999/REC-xslt-19991116 MechanismType", "DOM");
/*     */     
/* 155 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/* 157 */             XMLDSigRI.this.putAll(map);
/* 158 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/XMLDSigRI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */