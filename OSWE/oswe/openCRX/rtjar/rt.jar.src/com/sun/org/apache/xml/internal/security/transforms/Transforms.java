/*     */ package com.sun.org.apache.xml.internal.security.transforms;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class Transforms
/*     */   extends SignatureElementProxy
/*     */ {
/*     */   public static final String TRANSFORM_C14N_OMIT_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
/*     */   public static final String TRANSFORM_C14N_WITH_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
/*     */   public static final String TRANSFORM_C14N11_OMIT_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11";
/*     */   public static final String TRANSFORM_C14N11_WITH_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11#WithComments";
/*     */   public static final String TRANSFORM_C14N_EXCL_OMIT_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#";
/*     */   public static final String TRANSFORM_C14N_EXCL_WITH_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
/*     */   public static final String TRANSFORM_XSLT = "http://www.w3.org/TR/1999/REC-xslt-19991116";
/*     */   public static final String TRANSFORM_BASE64_DECODE = "http://www.w3.org/2000/09/xmldsig#base64";
/*     */   public static final String TRANSFORM_XPATH = "http://www.w3.org/TR/1999/REC-xpath-19991116";
/*     */   public static final String TRANSFORM_ENVELOPED_SIGNATURE = "http://www.w3.org/2000/09/xmldsig#enveloped-signature";
/*     */   public static final String TRANSFORM_XPOINTER = "http://www.w3.org/TR/2001/WD-xptr-20010108";
/*     */   public static final String TRANSFORM_XPATH2FILTER = "http://www.w3.org/2002/06/xmldsig-filter2";
/* 106 */   private static Logger log = Logger.getLogger(Transforms.class.getName());
/*     */ 
/*     */   
/*     */   private Element[] transforms;
/*     */ 
/*     */   
/*     */   private boolean secureValidation;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Transforms() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Transforms(Document paramDocument) {
/* 121 */     super(paramDocument);
/* 122 */     XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transforms(Element paramElement, String paramString) throws DOMException, XMLSignatureException, InvalidTransformException, TransformationException, XMLSecurityException {
/* 140 */     super(paramElement, paramString);
/*     */     
/* 142 */     int i = getLength();
/*     */     
/* 144 */     if (i == 0) {
/*     */       
/* 146 */       Object[] arrayOfObject = { "Transform", "Transforms" };
/*     */       
/* 148 */       throw new TransformationException("xml.WrongContent", arrayOfObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecureValidation(boolean paramBoolean) {
/* 156 */     this.secureValidation = paramBoolean;
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
/*     */   public void addTransform(String paramString) throws TransformationException {
/*     */     try {
/* 169 */       if (log.isLoggable(Level.FINE)) {
/* 170 */         log.log(Level.FINE, "Transforms.addTransform(" + paramString + ")");
/*     */       }
/*     */       
/* 173 */       Transform transform = new Transform(this.doc, paramString);
/*     */       
/* 175 */       addTransform(transform);
/* 176 */     } catch (InvalidTransformException invalidTransformException) {
/* 177 */       throw new TransformationException("empty", invalidTransformException);
/*     */     } 
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
/*     */ 
/*     */   
/*     */   public void addTransform(String paramString, Element paramElement) throws TransformationException {
/*     */     try {
/* 193 */       if (log.isLoggable(Level.FINE)) {
/* 194 */         log.log(Level.FINE, "Transforms.addTransform(" + paramString + ")");
/*     */       }
/*     */       
/* 197 */       Transform transform = new Transform(this.doc, paramString, paramElement);
/*     */       
/* 199 */       addTransform(transform);
/* 200 */     } catch (InvalidTransformException invalidTransformException) {
/* 201 */       throw new TransformationException("empty", invalidTransformException);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTransform(String paramString, NodeList paramNodeList) throws TransformationException {
/*     */     try {
/* 218 */       Transform transform = new Transform(this.doc, paramString, paramNodeList);
/* 219 */       addTransform(transform);
/* 220 */     } catch (InvalidTransformException invalidTransformException) {
/* 221 */       throw new TransformationException("empty", invalidTransformException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTransform(Transform paramTransform) {
/* 231 */     if (log.isLoggable(Level.FINE)) {
/* 232 */       log.log(Level.FINE, "Transforms.addTransform(" + paramTransform.getURI() + ")");
/*     */     }
/*     */     
/* 235 */     Element element = paramTransform.getElement();
/*     */     
/* 237 */     this.constructionElement.appendChild(element);
/* 238 */     XMLUtils.addReturnToElement(this.constructionElement);
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
/*     */ 
/*     */   
/*     */   public XMLSignatureInput performTransforms(XMLSignatureInput paramXMLSignatureInput) throws TransformationException {
/* 252 */     return performTransforms(paramXMLSignatureInput, (OutputStream)null);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInput performTransforms(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream) throws TransformationException {
/*     */     try {
/* 268 */       int i = getLength() - 1;
/* 269 */       for (byte b = 0; b < i; b++) {
/* 270 */         Transform transform = item(b);
/* 271 */         String str = transform.getURI();
/* 272 */         if (log.isLoggable(Level.FINE)) {
/* 273 */           log.log(Level.FINE, "Perform the (" + b + ")th " + str + " transform");
/*     */         }
/* 275 */         checkSecureValidation(transform);
/* 276 */         paramXMLSignatureInput = transform.performTransform(paramXMLSignatureInput);
/*     */       } 
/* 278 */       if (i >= 0) {
/* 279 */         Transform transform = item(i);
/* 280 */         checkSecureValidation(transform);
/* 281 */         paramXMLSignatureInput = transform.performTransform(paramXMLSignatureInput, paramOutputStream);
/*     */       } 
/*     */       
/* 284 */       return paramXMLSignatureInput;
/* 285 */     } catch (IOException iOException) {
/* 286 */       throw new TransformationException("empty", iOException);
/* 287 */     } catch (CanonicalizationException canonicalizationException) {
/* 288 */       throw new TransformationException("empty", canonicalizationException);
/* 289 */     } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/* 290 */       throw new TransformationException("empty", invalidCanonicalizerException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkSecureValidation(Transform paramTransform) throws TransformationException {
/* 295 */     String str = paramTransform.getURI();
/* 296 */     if (this.secureValidation && "http://www.w3.org/TR/1999/REC-xslt-19991116".equals(str)) {
/* 297 */       Object[] arrayOfObject = { str };
/*     */       
/* 299 */       throw new TransformationException("signature.Transform.ForbiddenTransform", arrayOfObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 311 */     if (this.transforms == null) {
/* 312 */       this
/* 313 */         .transforms = XMLUtils.selectDsNodes(this.constructionElement.getFirstChild(), "Transform");
/*     */     }
/* 315 */     return this.transforms.length;
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
/*     */   public Transform item(int paramInt) throws TransformationException {
/*     */     try {
/* 328 */       if (this.transforms == null) {
/* 329 */         this
/* 330 */           .transforms = XMLUtils.selectDsNodes(this.constructionElement.getFirstChild(), "Transform");
/*     */       }
/* 332 */       return new Transform(this.transforms[paramInt], this.baseURI);
/* 333 */     } catch (XMLSecurityException xMLSecurityException) {
/* 334 */       throw new TransformationException("empty", xMLSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseLocalName() {
/* 340 */     return "Transforms";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/Transforms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */