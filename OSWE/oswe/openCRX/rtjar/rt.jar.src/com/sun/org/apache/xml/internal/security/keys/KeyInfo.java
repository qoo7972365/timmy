/*      */ package com.sun.org.apache.xml.internal.security.keys;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*      */ import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
/*      */ import com.sun.org.apache.xml.internal.security.encryption.XMLEncryptionException;
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.DEREncodedKeyValue;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.KeyInfoReference;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.KeyName;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.MgmtData;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.PGPData;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.RetrievalMethod;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.SPKIData;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.DSAKeyValue;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
/*      */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver;
/*      */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
/*      */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
/*      */ import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
/*      */ import com.sun.org.apache.xml.internal.security.transforms.Transforms;
/*      */ import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
/*      */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PublicKey;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.crypto.SecretKey;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class KeyInfo
/*      */   extends SignatureElementProxy
/*      */ {
/*   99 */   private static Logger log = Logger.getLogger(KeyInfo.class.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  105 */   private List<X509Data> x509Datas = null;
/*  106 */   private List<EncryptedKey> encryptedKeys = null;
/*      */   private static final List<StorageResolver> nullList;
/*      */   
/*      */   static {
/*  110 */     ArrayList<? extends StorageResolver> arrayList = new ArrayList(1);
/*  111 */     arrayList.add(null);
/*  112 */     nullList = Collections.unmodifiableList(arrayList);
/*      */   }
/*      */ 
/*      */   
/*  116 */   private List<StorageResolver> storageResolvers = nullList;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  121 */   private List<KeyResolverSpi> internalKeyResolvers = new ArrayList<>();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean secureValidation;
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyInfo(Document paramDocument) {
/*  130 */     super(paramDocument);
/*      */     
/*  132 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyInfo(Element paramElement, String paramString) throws XMLSecurityException {
/*  143 */     super(paramElement, paramString);
/*      */     
/*  145 */     Attr attr = paramElement.getAttributeNodeNS((String)null, "Id");
/*  146 */     if (attr != null) {
/*  147 */       paramElement.setIdAttributeNode(attr, true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSecureValidation(boolean paramBoolean) {
/*  155 */     this.secureValidation = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setId(String paramString) {
/*  164 */     if (paramString != null) {
/*  165 */       this.constructionElement.setAttributeNS((String)null, "Id", paramString);
/*  166 */       this.constructionElement.setIdAttributeNS((String)null, "Id", true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getId() {
/*  176 */     return this.constructionElement.getAttributeNS((String)null, "Id");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addKeyName(String paramString) {
/*  185 */     add(new KeyName(this.doc, paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(KeyName paramKeyName) {
/*  194 */     this.constructionElement.appendChild(paramKeyName.getElement());
/*  195 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addKeyValue(PublicKey paramPublicKey) {
/*  204 */     add(new KeyValue(this.doc, paramPublicKey));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addKeyValue(Element paramElement) {
/*  213 */     add(new KeyValue(this.doc, paramElement));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(DSAKeyValue paramDSAKeyValue) {
/*  222 */     add(new KeyValue(this.doc, paramDSAKeyValue));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(RSAKeyValue paramRSAKeyValue) {
/*  231 */     add(new KeyValue(this.doc, paramRSAKeyValue));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(PublicKey paramPublicKey) {
/*  240 */     add(new KeyValue(this.doc, paramPublicKey));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(KeyValue paramKeyValue) {
/*  249 */     this.constructionElement.appendChild(paramKeyValue.getElement());
/*  250 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMgmtData(String paramString) {
/*  259 */     add(new MgmtData(this.doc, paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(MgmtData paramMgmtData) {
/*  268 */     this.constructionElement.appendChild(paramMgmtData.getElement());
/*  269 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(PGPData paramPGPData) {
/*  278 */     this.constructionElement.appendChild(paramPGPData.getElement());
/*  279 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRetrievalMethod(String paramString1, Transforms paramTransforms, String paramString2) {
/*  290 */     add(new RetrievalMethod(this.doc, paramString1, paramTransforms, paramString2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(RetrievalMethod paramRetrievalMethod) {
/*  299 */     this.constructionElement.appendChild(paramRetrievalMethod.getElement());
/*  300 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(SPKIData paramSPKIData) {
/*  309 */     this.constructionElement.appendChild(paramSPKIData.getElement());
/*  310 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(X509Data paramX509Data) {
/*  319 */     if (this.x509Datas == null) {
/*  320 */       this.x509Datas = new ArrayList<>();
/*      */     }
/*  322 */     this.x509Datas.add(paramX509Data);
/*  323 */     this.constructionElement.appendChild(paramX509Data.getElement());
/*  324 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(EncryptedKey paramEncryptedKey) throws XMLEncryptionException {
/*  335 */     if (this.encryptedKeys == null) {
/*  336 */       this.encryptedKeys = new ArrayList<>();
/*      */     }
/*  338 */     this.encryptedKeys.add(paramEncryptedKey);
/*  339 */     XMLCipher xMLCipher = XMLCipher.getInstance();
/*  340 */     this.constructionElement.appendChild(xMLCipher.martial(paramEncryptedKey));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDEREncodedKeyValue(PublicKey paramPublicKey) throws XMLSecurityException {
/*  350 */     add(new DEREncodedKeyValue(this.doc, paramPublicKey));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(DEREncodedKeyValue paramDEREncodedKeyValue) {
/*  359 */     this.constructionElement.appendChild(paramDEREncodedKeyValue.getElement());
/*  360 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addKeyInfoReference(String paramString) throws XMLSecurityException {
/*  370 */     add(new KeyInfoReference(this.doc, paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(KeyInfoReference paramKeyInfoReference) {
/*  379 */     this.constructionElement.appendChild(paramKeyInfoReference.getElement());
/*  380 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addUnknownElement(Element paramElement) {
/*  389 */     this.constructionElement.appendChild(paramElement);
/*  390 */     XMLUtils.addReturnToElement(this.constructionElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthKeyName() {
/*  399 */     return length("http://www.w3.org/2000/09/xmldsig#", "KeyName");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthKeyValue() {
/*  408 */     return length("http://www.w3.org/2000/09/xmldsig#", "KeyValue");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthMgmtData() {
/*  417 */     return length("http://www.w3.org/2000/09/xmldsig#", "MgmtData");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthPGPData() {
/*  426 */     return length("http://www.w3.org/2000/09/xmldsig#", "PGPData");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthRetrievalMethod() {
/*  435 */     return length("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthSPKIData() {
/*  444 */     return length("http://www.w3.org/2000/09/xmldsig#", "SPKIData");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthX509Data() {
/*  453 */     if (this.x509Datas != null) {
/*  454 */       return this.x509Datas.size();
/*      */     }
/*  456 */     return length("http://www.w3.org/2000/09/xmldsig#", "X509Data");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthDEREncodedKeyValue() {
/*  465 */     return length("http://www.w3.org/2009/xmldsig11#", "DEREncodedKeyValue");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthKeyInfoReference() {
/*  474 */     return length("http://www.w3.org/2009/xmldsig11#", "KeyInfoReference");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthUnknownElement() {
/*  483 */     byte b1 = 0;
/*  484 */     NodeList nodeList = this.constructionElement.getChildNodes();
/*      */     
/*  486 */     for (byte b2 = 0; b2 < nodeList.getLength(); b2++) {
/*  487 */       Node node = nodeList.item(b2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  493 */       if (node.getNodeType() == 1 && node
/*  494 */         .getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#")) {
/*  495 */         b1++;
/*      */       }
/*      */     } 
/*      */     
/*  499 */     return b1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyName itemKeyName(int paramInt) throws XMLSecurityException {
/*  511 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/*  512 */         .getFirstChild(), "KeyName", paramInt);
/*      */     
/*  514 */     if (element != null) {
/*  515 */       return new KeyName(element, this.baseURI);
/*      */     }
/*  517 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyValue itemKeyValue(int paramInt) throws XMLSecurityException {
/*  529 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/*  530 */         .getFirstChild(), "KeyValue", paramInt);
/*      */     
/*  532 */     if (element != null) {
/*  533 */       return new KeyValue(element, this.baseURI);
/*      */     }
/*  535 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MgmtData itemMgmtData(int paramInt) throws XMLSecurityException {
/*  547 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/*  548 */         .getFirstChild(), "MgmtData", paramInt);
/*      */     
/*  550 */     if (element != null) {
/*  551 */       return new MgmtData(element, this.baseURI);
/*      */     }
/*  553 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PGPData itemPGPData(int paramInt) throws XMLSecurityException {
/*  565 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/*  566 */         .getFirstChild(), "PGPData", paramInt);
/*      */     
/*  568 */     if (element != null) {
/*  569 */       return new PGPData(element, this.baseURI);
/*      */     }
/*  571 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RetrievalMethod itemRetrievalMethod(int paramInt) throws XMLSecurityException {
/*  583 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/*  584 */         .getFirstChild(), "RetrievalMethod", paramInt);
/*      */     
/*  586 */     if (element != null) {
/*  587 */       return new RetrievalMethod(element, this.baseURI);
/*      */     }
/*  589 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SPKIData itemSPKIData(int paramInt) throws XMLSecurityException {
/*  601 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/*  602 */         .getFirstChild(), "SPKIData", paramInt);
/*      */     
/*  604 */     if (element != null) {
/*  605 */       return new SPKIData(element, this.baseURI);
/*      */     }
/*  607 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Data itemX509Data(int paramInt) throws XMLSecurityException {
/*  618 */     if (this.x509Datas != null) {
/*  619 */       return this.x509Datas.get(paramInt);
/*      */     }
/*      */     
/*  622 */     Element element = XMLUtils.selectDsNode(this.constructionElement
/*  623 */         .getFirstChild(), "X509Data", paramInt);
/*      */     
/*  625 */     if (element != null) {
/*  626 */       return new X509Data(element, this.baseURI);
/*      */     }
/*  628 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EncryptedKey itemEncryptedKey(int paramInt) throws XMLSecurityException {
/*  639 */     if (this.encryptedKeys != null) {
/*  640 */       return this.encryptedKeys.get(paramInt);
/*      */     }
/*      */     
/*  643 */     Element element = XMLUtils.selectXencNode(this.constructionElement
/*  644 */         .getFirstChild(), "EncryptedKey", paramInt);
/*      */     
/*  646 */     if (element != null) {
/*  647 */       XMLCipher xMLCipher = XMLCipher.getInstance();
/*  648 */       xMLCipher.init(4, null);
/*  649 */       return xMLCipher.loadEncryptedKey(element);
/*      */     } 
/*  651 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DEREncodedKeyValue itemDEREncodedKeyValue(int paramInt) throws XMLSecurityException {
/*  663 */     Element element = XMLUtils.selectDs11Node(this.constructionElement
/*  664 */         .getFirstChild(), "DEREncodedKeyValue", paramInt);
/*      */     
/*  666 */     if (element != null) {
/*  667 */       return new DEREncodedKeyValue(element, this.baseURI);
/*      */     }
/*  669 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyInfoReference itemKeyInfoReference(int paramInt) throws XMLSecurityException {
/*  681 */     Element element = XMLUtils.selectDs11Node(this.constructionElement
/*  682 */         .getFirstChild(), "KeyInfoReference", paramInt);
/*      */     
/*  684 */     if (element != null) {
/*  685 */       return new KeyInfoReference(element, this.baseURI);
/*      */     }
/*  687 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element itemUnknownElement(int paramInt) {
/*  697 */     NodeList nodeList = this.constructionElement.getChildNodes();
/*  698 */     byte b1 = 0;
/*      */     
/*  700 */     for (byte b2 = 0; b2 < nodeList.getLength(); b2++) {
/*  701 */       Node node = nodeList.item(b2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  709 */       b1++;
/*      */       
/*  711 */       if (node.getNodeType() == 1 && node.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") && b1 == paramInt) {
/*  712 */         return (Element)node;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  717 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  726 */     return (this.constructionElement.getFirstChild() == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKeyName() {
/*  735 */     return (lengthKeyName() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKeyValue() {
/*  744 */     return (lengthKeyValue() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsMgmtData() {
/*  753 */     return (lengthMgmtData() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsPGPData() {
/*  762 */     return (lengthPGPData() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsRetrievalMethod() {
/*  771 */     return (lengthRetrievalMethod() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsSPKIData() {
/*  780 */     return (lengthSPKIData() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsUnknownElement() {
/*  789 */     return (lengthUnknownElement() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsX509Data() {
/*  798 */     return (lengthX509Data() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsDEREncodedKeyValue() {
/*  807 */     return (lengthDEREncodedKeyValue() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKeyInfoReference() {
/*  816 */     return (lengthKeyInfoReference() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey() throws KeyResolverException {
/*  826 */     PublicKey publicKey = getPublicKeyFromInternalResolvers();
/*      */     
/*  828 */     if (publicKey != null) {
/*  829 */       if (log.isLoggable(Level.FINE)) {
/*  830 */         log.log(Level.FINE, "I could find a key using the per-KeyInfo key resolvers");
/*      */       }
/*      */       
/*  833 */       return publicKey;
/*      */     } 
/*  835 */     if (log.isLoggable(Level.FINE)) {
/*  836 */       log.log(Level.FINE, "I couldn't find a key using the per-KeyInfo key resolvers");
/*      */     }
/*      */     
/*  839 */     publicKey = getPublicKeyFromStaticResolvers();
/*      */     
/*  841 */     if (publicKey != null) {
/*  842 */       if (log.isLoggable(Level.FINE)) {
/*  843 */         log.log(Level.FINE, "I could find a key using the system-wide key resolvers");
/*      */       }
/*      */       
/*  846 */       return publicKey;
/*      */     } 
/*  848 */     if (log.isLoggable(Level.FINE)) {
/*  849 */       log.log(Level.FINE, "I couldn't find a key using the system-wide key resolvers");
/*      */     }
/*      */     
/*  852 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PublicKey getPublicKeyFromStaticResolvers() throws KeyResolverException {
/*  862 */     Iterator<KeyResolverSpi> iterator = KeyResolver.iterator();
/*  863 */     while (iterator.hasNext()) {
/*  864 */       KeyResolverSpi keyResolverSpi = iterator.next();
/*  865 */       keyResolverSpi.setSecureValidation(this.secureValidation);
/*  866 */       Node node = this.constructionElement.getFirstChild();
/*  867 */       String str = getBaseURI();
/*  868 */       while (node != null) {
/*  869 */         if (node.getNodeType() == 1) {
/*  870 */           for (StorageResolver storageResolver : this.storageResolvers) {
/*      */             
/*  872 */             PublicKey publicKey = keyResolverSpi.engineLookupAndResolvePublicKey((Element)node, str, storageResolver);
/*      */ 
/*      */ 
/*      */             
/*  876 */             if (publicKey != null) {
/*  877 */               return publicKey;
/*      */             }
/*      */           } 
/*      */         }
/*  881 */         node = node.getNextSibling();
/*      */       } 
/*      */     } 
/*  884 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PublicKey getPublicKeyFromInternalResolvers() throws KeyResolverException {
/*  894 */     for (KeyResolverSpi keyResolverSpi : this.internalKeyResolvers) {
/*  895 */       if (log.isLoggable(Level.FINE)) {
/*  896 */         log.log(Level.FINE, "Try " + keyResolverSpi.getClass().getName());
/*      */       }
/*  898 */       keyResolverSpi.setSecureValidation(this.secureValidation);
/*  899 */       Node node = this.constructionElement.getFirstChild();
/*  900 */       String str = getBaseURI();
/*  901 */       while (node != null) {
/*  902 */         if (node.getNodeType() == 1) {
/*  903 */           for (StorageResolver storageResolver : this.storageResolvers) {
/*      */             
/*  905 */             PublicKey publicKey = keyResolverSpi.engineLookupAndResolvePublicKey((Element)node, str, storageResolver);
/*      */ 
/*      */ 
/*      */             
/*  909 */             if (publicKey != null) {
/*  910 */               return publicKey;
/*      */             }
/*      */           } 
/*      */         }
/*  914 */         node = node.getNextSibling();
/*      */       } 
/*      */     } 
/*      */     
/*  918 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getX509Certificate() throws KeyResolverException {
/*  929 */     X509Certificate x509Certificate = getX509CertificateFromInternalResolvers();
/*      */     
/*  931 */     if (x509Certificate != null) {
/*  932 */       if (log.isLoggable(Level.FINE)) {
/*  933 */         log.log(Level.FINE, "I could find a X509Certificate using the per-KeyInfo key resolvers");
/*      */       }
/*      */       
/*  936 */       return x509Certificate;
/*      */     } 
/*  938 */     if (log.isLoggable(Level.FINE)) {
/*  939 */       log.log(Level.FINE, "I couldn't find a X509Certificate using the per-KeyInfo key resolvers");
/*      */     }
/*      */ 
/*      */     
/*  943 */     x509Certificate = getX509CertificateFromStaticResolvers();
/*      */     
/*  945 */     if (x509Certificate != null) {
/*  946 */       if (log.isLoggable(Level.FINE)) {
/*  947 */         log.log(Level.FINE, "I could find a X509Certificate using the system-wide key resolvers");
/*      */       }
/*      */       
/*  950 */       return x509Certificate;
/*      */     } 
/*  952 */     if (log.isLoggable(Level.FINE)) {
/*  953 */       log.log(Level.FINE, "I couldn't find a X509Certificate using the system-wide key resolvers");
/*      */     }
/*      */     
/*  956 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   X509Certificate getX509CertificateFromStaticResolvers() throws KeyResolverException {
/*  969 */     if (log.isLoggable(Level.FINE)) {
/*  970 */       log.log(Level.FINE, "Start getX509CertificateFromStaticResolvers() with " + 
/*  971 */           KeyResolver.length() + " resolvers");
/*      */     }
/*      */ 
/*      */     
/*  975 */     String str = getBaseURI();
/*  976 */     Iterator<KeyResolverSpi> iterator = KeyResolver.iterator();
/*  977 */     while (iterator.hasNext()) {
/*  978 */       KeyResolverSpi keyResolverSpi = iterator.next();
/*  979 */       keyResolverSpi.setSecureValidation(this.secureValidation);
/*  980 */       X509Certificate x509Certificate = applyCurrentResolver(str, keyResolverSpi);
/*  981 */       if (x509Certificate != null) {
/*  982 */         return x509Certificate;
/*      */       }
/*      */     } 
/*  985 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate applyCurrentResolver(String paramString, KeyResolverSpi paramKeyResolverSpi) throws KeyResolverException {
/*  991 */     Node node = this.constructionElement.getFirstChild();
/*  992 */     while (node != null) {
/*  993 */       if (node.getNodeType() == 1) {
/*  994 */         for (StorageResolver storageResolver : this.storageResolvers) {
/*      */           
/*  996 */           X509Certificate x509Certificate = paramKeyResolverSpi.engineLookupResolveX509Certificate((Element)node, paramString, storageResolver);
/*      */ 
/*      */ 
/*      */           
/* 1000 */           if (x509Certificate != null) {
/* 1001 */             return x509Certificate;
/*      */           }
/*      */         } 
/*      */       }
/* 1005 */       node = node.getNextSibling();
/*      */     } 
/* 1007 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   X509Certificate getX509CertificateFromInternalResolvers() throws KeyResolverException {
/* 1018 */     if (log.isLoggable(Level.FINE)) {
/* 1019 */       log.log(Level.FINE, "Start getX509CertificateFromInternalResolvers() with " + 
/*      */           
/* 1021 */           lengthInternalKeyResolver() + " resolvers");
/*      */     }
/*      */     
/* 1024 */     String str = getBaseURI();
/* 1025 */     for (KeyResolverSpi keyResolverSpi : this.internalKeyResolvers) {
/* 1026 */       if (log.isLoggable(Level.FINE)) {
/* 1027 */         log.log(Level.FINE, "Try " + keyResolverSpi.getClass().getName());
/*      */       }
/* 1029 */       keyResolverSpi.setSecureValidation(this.secureValidation);
/* 1030 */       X509Certificate x509Certificate = applyCurrentResolver(str, keyResolverSpi);
/* 1031 */       if (x509Certificate != null) {
/* 1032 */         return x509Certificate;
/*      */       }
/*      */     } 
/*      */     
/* 1036 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SecretKey getSecretKey() throws KeyResolverException {
/* 1045 */     SecretKey secretKey = getSecretKeyFromInternalResolvers();
/*      */     
/* 1047 */     if (secretKey != null) {
/* 1048 */       if (log.isLoggable(Level.FINE)) {
/* 1049 */         log.log(Level.FINE, "I could find a secret key using the per-KeyInfo key resolvers");
/*      */       }
/*      */       
/* 1052 */       return secretKey;
/*      */     } 
/* 1054 */     if (log.isLoggable(Level.FINE)) {
/* 1055 */       log.log(Level.FINE, "I couldn't find a secret key using the per-KeyInfo key resolvers");
/*      */     }
/*      */     
/* 1058 */     secretKey = getSecretKeyFromStaticResolvers();
/*      */     
/* 1060 */     if (secretKey != null) {
/* 1061 */       if (log.isLoggable(Level.FINE)) {
/* 1062 */         log.log(Level.FINE, "I could find a secret key using the system-wide key resolvers");
/*      */       }
/*      */       
/* 1065 */       return secretKey;
/*      */     } 
/* 1067 */     if (log.isLoggable(Level.FINE)) {
/* 1068 */       log.log(Level.FINE, "I couldn't find a secret key using the system-wide key resolvers");
/*      */     }
/*      */     
/* 1071 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SecretKey getSecretKeyFromStaticResolvers() throws KeyResolverException {
/* 1081 */     Iterator<KeyResolverSpi> iterator = KeyResolver.iterator();
/* 1082 */     while (iterator.hasNext()) {
/* 1083 */       KeyResolverSpi keyResolverSpi = iterator.next();
/* 1084 */       keyResolverSpi.setSecureValidation(this.secureValidation);
/*      */       
/* 1086 */       Node node = this.constructionElement.getFirstChild();
/* 1087 */       String str = getBaseURI();
/* 1088 */       while (node != null) {
/* 1089 */         if (node.getNodeType() == 1) {
/* 1090 */           for (StorageResolver storageResolver : this.storageResolvers) {
/*      */             
/* 1092 */             SecretKey secretKey = keyResolverSpi.engineLookupAndResolveSecretKey((Element)node, str, storageResolver);
/*      */ 
/*      */ 
/*      */             
/* 1096 */             if (secretKey != null) {
/* 1097 */               return secretKey;
/*      */             }
/*      */           } 
/*      */         }
/* 1101 */         node = node.getNextSibling();
/*      */       } 
/*      */     } 
/* 1104 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SecretKey getSecretKeyFromInternalResolvers() throws KeyResolverException {
/* 1115 */     for (KeyResolverSpi keyResolverSpi : this.internalKeyResolvers) {
/* 1116 */       if (log.isLoggable(Level.FINE)) {
/* 1117 */         log.log(Level.FINE, "Try " + keyResolverSpi.getClass().getName());
/*      */       }
/* 1119 */       keyResolverSpi.setSecureValidation(this.secureValidation);
/* 1120 */       Node node = this.constructionElement.getFirstChild();
/* 1121 */       String str = getBaseURI();
/* 1122 */       while (node != null) {
/* 1123 */         if (node.getNodeType() == 1) {
/* 1124 */           for (StorageResolver storageResolver : this.storageResolvers) {
/*      */             
/* 1126 */             SecretKey secretKey = keyResolverSpi.engineLookupAndResolveSecretKey((Element)node, str, storageResolver);
/*      */ 
/*      */ 
/*      */             
/* 1130 */             if (secretKey != null) {
/* 1131 */               return secretKey;
/*      */             }
/*      */           } 
/*      */         }
/* 1135 */         node = node.getNextSibling();
/*      */       } 
/*      */     } 
/*      */     
/* 1139 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey() throws KeyResolverException {
/* 1148 */     PrivateKey privateKey = getPrivateKeyFromInternalResolvers();
/*      */     
/* 1150 */     if (privateKey != null) {
/* 1151 */       if (log.isLoggable(Level.FINE)) {
/* 1152 */         log.log(Level.FINE, "I could find a private key using the per-KeyInfo key resolvers");
/*      */       }
/* 1154 */       return privateKey;
/*      */     } 
/* 1156 */     if (log.isLoggable(Level.FINE)) {
/* 1157 */       log.log(Level.FINE, "I couldn't find a secret key using the per-KeyInfo key resolvers");
/*      */     }
/*      */     
/* 1160 */     privateKey = getPrivateKeyFromStaticResolvers();
/* 1161 */     if (privateKey != null) {
/* 1162 */       if (log.isLoggable(Level.FINE)) {
/* 1163 */         log.log(Level.FINE, "I could find a private key using the system-wide key resolvers");
/*      */       }
/* 1165 */       return privateKey;
/*      */     } 
/* 1167 */     if (log.isLoggable(Level.FINE)) {
/* 1168 */       log.log(Level.FINE, "I couldn't find a private key using the system-wide key resolvers");
/*      */     }
/*      */     
/* 1171 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PrivateKey getPrivateKeyFromStaticResolvers() throws KeyResolverException {
/* 1181 */     Iterator<KeyResolverSpi> iterator = KeyResolver.iterator();
/* 1182 */     while (iterator.hasNext()) {
/* 1183 */       KeyResolverSpi keyResolverSpi = iterator.next();
/* 1184 */       keyResolverSpi.setSecureValidation(this.secureValidation);
/*      */       
/* 1186 */       Node node = this.constructionElement.getFirstChild();
/* 1187 */       String str = getBaseURI();
/* 1188 */       while (node != null) {
/* 1189 */         if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */           
/* 1193 */           PrivateKey privateKey = keyResolverSpi.engineLookupAndResolvePrivateKey((Element)node, str, null);
/*      */ 
/*      */ 
/*      */           
/* 1197 */           if (privateKey != null) {
/* 1198 */             return privateKey;
/*      */           }
/*      */         } 
/* 1201 */         node = node.getNextSibling();
/*      */       } 
/*      */     } 
/* 1204 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PrivateKey getPrivateKeyFromInternalResolvers() throws KeyResolverException {
/* 1214 */     for (KeyResolverSpi keyResolverSpi : this.internalKeyResolvers) {
/* 1215 */       if (log.isLoggable(Level.FINE)) {
/* 1216 */         log.log(Level.FINE, "Try " + keyResolverSpi.getClass().getName());
/*      */       }
/* 1218 */       keyResolverSpi.setSecureValidation(this.secureValidation);
/* 1219 */       Node node = this.constructionElement.getFirstChild();
/* 1220 */       String str = getBaseURI();
/* 1221 */       while (node != null) {
/* 1222 */         if (node.getNodeType() == 1) {
/*      */ 
/*      */ 
/*      */           
/* 1226 */           PrivateKey privateKey = keyResolverSpi.engineLookupAndResolvePrivateKey((Element)node, str, null);
/*      */ 
/*      */ 
/*      */           
/* 1230 */           if (privateKey != null) {
/* 1231 */             return privateKey;
/*      */           }
/*      */         } 
/* 1234 */         node = node.getNextSibling();
/*      */       } 
/*      */     } 
/*      */     
/* 1238 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerInternalKeyResolver(KeyResolverSpi paramKeyResolverSpi) {
/* 1248 */     this.internalKeyResolvers.add(paramKeyResolverSpi);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int lengthInternalKeyResolver() {
/* 1256 */     return this.internalKeyResolvers.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   KeyResolverSpi itemInternalKeyResolver(int paramInt) {
/* 1266 */     return this.internalKeyResolvers.get(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStorageResolver(StorageResolver paramStorageResolver) {
/* 1275 */     if (this.storageResolvers == nullList)
/*      */     {
/* 1277 */       this.storageResolvers = new ArrayList<>();
/*      */     }
/* 1279 */     this.storageResolvers.add(paramStorageResolver);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getBaseLocalName() {
/* 1285 */     return "KeyInfo";
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/keys/KeyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */