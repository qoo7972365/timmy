/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.org.apache.xml.internal.security.utils.UnsyncBufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.Provider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMCryptoContext;
/*     */ import javax.xml.crypto.dsig.CanonicalizationMethod;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.SignatureMethod;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
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
/*     */ public final class DOMSignedInfo
/*     */   extends DOMStructure
/*     */   implements SignedInfo
/*     */ {
/*  58 */   private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
/*     */ 
/*     */   
/*     */   private List<Reference> references;
/*     */ 
/*     */   
/*     */   private CanonicalizationMethod canonicalizationMethod;
/*     */ 
/*     */   
/*     */   private SignatureMethod signatureMethod;
/*     */ 
/*     */   
/*     */   private String id;
/*     */ 
/*     */   
/*     */   private Document ownerDoc;
/*     */ 
/*     */   
/*     */   private Element localSiElem;
/*     */ 
/*     */   
/*     */   private InputStream canonData;
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMSignedInfo(CanonicalizationMethod paramCanonicalizationMethod, SignatureMethod paramSignatureMethod, List<? extends Reference> paramList) {
/*  84 */     if (paramCanonicalizationMethod == null || paramSignatureMethod == null || paramList == null) {
/*  85 */       throw new NullPointerException();
/*     */     }
/*  87 */     this.canonicalizationMethod = paramCanonicalizationMethod;
/*  88 */     this.signatureMethod = paramSignatureMethod;
/*  89 */     this.references = Collections.unmodifiableList(new ArrayList<>(paramList));
/*     */     
/*  91 */     if (this.references.isEmpty())
/*  92 */       throw new IllegalArgumentException("list of references must contain at least one entry"); 
/*     */     byte b;
/*     */     int i;
/*  95 */     for (b = 0, i = this.references.size(); b < i; b++) {
/*  96 */       Object object = this.references.get(b);
/*  97 */       if (!(object instanceof Reference)) {
/*  98 */         throw new ClassCastException("list of references contains an illegal type");
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMSignedInfo(CanonicalizationMethod paramCanonicalizationMethod, SignatureMethod paramSignatureMethod, List<? extends Reference> paramList, String paramString) {
/* 121 */     this(paramCanonicalizationMethod, paramSignatureMethod, paramList);
/* 122 */     this.id = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMSignedInfo(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
/* 132 */     this.localSiElem = paramElement;
/* 133 */     this.ownerDoc = paramElement.getOwnerDocument();
/*     */ 
/*     */     
/* 136 */     this.id = DOMUtils.getAttributeValue(paramElement, "Id");
/*     */ 
/*     */     
/* 139 */     Element element1 = DOMUtils.getFirstChildElement(paramElement, "CanonicalizationMethod");
/*     */     
/* 141 */     this.canonicalizationMethod = new DOMCanonicalizationMethod(element1, paramXMLCryptoContext, paramProvider);
/*     */ 
/*     */ 
/*     */     
/* 145 */     Element element2 = DOMUtils.getNextSiblingElement(element1, "SignatureMethod");
/*     */     
/* 147 */     this.signatureMethod = DOMSignatureMethod.unmarshal(element2);
/*     */     
/* 149 */     boolean bool = Utils.secureValidation(paramXMLCryptoContext);
/*     */     
/* 151 */     String str = this.signatureMethod.getAlgorithm();
/* 152 */     if (bool && Policy.restrictAlg(str)) {
/* 153 */       throw new MarshalException("It is forbidden to use algorithm " + str + " when secure validation is enabled");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     ArrayList<DOMReference> arrayList = new ArrayList(5);
/* 161 */     Element element3 = DOMUtils.getNextSiblingElement(element2, "Reference");
/* 162 */     arrayList.add(new DOMReference(element3, paramXMLCryptoContext, paramProvider));
/*     */     
/* 164 */     element3 = DOMUtils.getNextSiblingElement(element3);
/* 165 */     while (element3 != null) {
/* 166 */       String str1 = element3.getLocalName();
/* 167 */       if (!str1.equals("Reference")) {
/* 168 */         throw new MarshalException("Invalid element name: " + str1 + ", expected Reference");
/*     */       }
/*     */       
/* 171 */       arrayList.add(new DOMReference(element3, paramXMLCryptoContext, paramProvider));
/*     */       
/* 173 */       if (bool && Policy.restrictNumReferences(arrayList.size())) {
/* 174 */         String str2 = "A maximum of " + Policy.maxReferences() + " references per Manifest are allowed when secure validation is enabled";
/*     */ 
/*     */         
/* 177 */         throw new MarshalException(str2);
/*     */       } 
/* 179 */       element3 = DOMUtils.getNextSiblingElement(element3);
/*     */     } 
/* 181 */     this.references = Collections.unmodifiableList((List)arrayList);
/*     */   }
/*     */   
/*     */   public CanonicalizationMethod getCanonicalizationMethod() {
/* 185 */     return this.canonicalizationMethod;
/*     */   }
/*     */   
/*     */   public SignatureMethod getSignatureMethod() {
/* 189 */     return this.signatureMethod;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 193 */     return this.id;
/*     */   }
/*     */   
/*     */   public List getReferences() {
/* 197 */     return this.references;
/*     */   }
/*     */   
/*     */   public InputStream getCanonicalizedData() {
/* 201 */     return this.canonData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void canonicalize(XMLCryptoContext paramXMLCryptoContext, ByteArrayOutputStream paramByteArrayOutputStream) throws XMLSignatureException {
/* 206 */     if (paramXMLCryptoContext == null) {
/* 207 */       throw new NullPointerException("context cannot be null");
/*     */     }
/*     */     
/* 210 */     UnsyncBufferedOutputStream unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(paramByteArrayOutputStream);
/*     */     
/* 212 */     DOMSubTreeData dOMSubTreeData = new DOMSubTreeData(this.localSiElem, true);
/*     */     try {
/* 214 */       ((DOMCanonicalizationMethod)this.canonicalizationMethod)
/* 215 */         .canonicalize(dOMSubTreeData, paramXMLCryptoContext, unsyncBufferedOutputStream);
/* 216 */     } catch (TransformException transformException) {
/* 217 */       throw new XMLSignatureException(transformException);
/*     */     } 
/*     */     
/*     */     try {
/* 221 */       unsyncBufferedOutputStream.flush();
/* 222 */     } catch (IOException iOException) {
/* 223 */       if (log.isLoggable(Level.FINE)) {
/* 224 */         log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 229 */     byte[] arrayOfByte = paramByteArrayOutputStream.toByteArray();
/*     */ 
/*     */     
/* 232 */     if (log.isLoggable(Level.FINE)) {
/* 233 */       log.log(Level.FINE, "Canonicalized SignedInfo:");
/* 234 */       StringBuilder stringBuilder = new StringBuilder(arrayOfByte.length);
/* 235 */       for (byte b = 0; b < arrayOfByte.length; b++) {
/* 236 */         stringBuilder.append((char)arrayOfByte[b]);
/*     */       }
/* 238 */       log.log(Level.FINE, stringBuilder.toString());
/* 239 */       log.log(Level.FINE, "Data to be signed/verified:" + Base64.encode(arrayOfByte));
/*     */     } 
/*     */     
/* 242 */     this.canonData = new ByteArrayInputStream(arrayOfByte);
/*     */     
/*     */     try {
/* 245 */       unsyncBufferedOutputStream.close();
/* 246 */     } catch (IOException iOException) {
/* 247 */       if (log.isLoggable(Level.FINE)) {
/* 248 */         log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
/* 257 */     this.ownerDoc = DOMUtils.getOwnerDocument(paramNode);
/* 258 */     Element element = DOMUtils.createElement(this.ownerDoc, "SignedInfo", "http://www.w3.org/2000/09/xmldsig#", paramString);
/*     */ 
/*     */ 
/*     */     
/* 262 */     DOMCanonicalizationMethod dOMCanonicalizationMethod = (DOMCanonicalizationMethod)this.canonicalizationMethod;
/*     */     
/* 264 */     dOMCanonicalizationMethod.marshal(element, paramString, paramDOMCryptoContext);
/*     */ 
/*     */     
/* 267 */     ((DOMStructure)this.signatureMethod).marshal(element, paramString, paramDOMCryptoContext);
/*     */ 
/*     */     
/* 270 */     for (Reference reference : this.references) {
/* 271 */       ((DOMReference)reference).marshal(element, paramString, paramDOMCryptoContext);
/*     */     }
/*     */ 
/*     */     
/* 275 */     DOMUtils.setAttributeID(element, "Id", this.id);
/*     */     
/* 277 */     paramNode.appendChild(element);
/* 278 */     this.localSiElem = element;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 283 */     if (this == paramObject) {
/* 284 */       return true;
/*     */     }
/*     */     
/* 287 */     if (!(paramObject instanceof SignedInfo)) {
/* 288 */       return false;
/*     */     }
/* 290 */     SignedInfo signedInfo = (SignedInfo)paramObject;
/*     */ 
/*     */     
/* 293 */     boolean bool = (this.id == null) ? ((signedInfo.getId() == null) ? true : false) : this.id.equals(signedInfo.getId());
/*     */     
/* 295 */     return (this.canonicalizationMethod.equals(signedInfo.getCanonicalizationMethod()) && this.signatureMethod
/* 296 */       .equals(signedInfo.getSignatureMethod()) && this.references
/* 297 */       .equals(signedInfo.getReferences()) && bool);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 302 */     int i = 17;
/* 303 */     if (this.id != null) {
/* 304 */       i = 31 * i + this.id.hashCode();
/*     */     }
/* 306 */     i = 31 * i + this.canonicalizationMethod.hashCode();
/* 307 */     i = 31 * i + this.signatureMethod.hashCode();
/* 308 */     i = 31 * i + this.references.hashCode();
/*     */     
/* 310 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/DOMSignedInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */