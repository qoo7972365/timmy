/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.Init;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.NodeSetData;
/*     */ import javax.xml.crypto.OctetStreamData;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.TransformService;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
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
/*     */ public abstract class ApacheCanonicalizer
/*     */   extends TransformService
/*     */ {
/*     */   static {
/*  54 */     Init.init();
/*     */   }
/*     */ 
/*     */   
/*  58 */   private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
/*     */   
/*     */   protected Canonicalizer apacheCanonicalizer;
/*     */   private Transform apacheTransform;
/*     */   protected String inclusiveNamespaces;
/*     */   protected C14NMethodParameterSpec params;
/*     */   protected Document ownerDoc;
/*     */   protected Element transformElem;
/*     */   
/*     */   public final AlgorithmParameterSpec getParameterSpec() {
/*  68 */     return this.params;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws InvalidAlgorithmParameterException {
/*  74 */     if (paramXMLCryptoContext != null && !(paramXMLCryptoContext instanceof javax.xml.crypto.dom.DOMCryptoContext)) {
/*  75 */       throw new ClassCastException("context must be of type DOMCryptoContext");
/*     */     }
/*     */     
/*  78 */     if (paramXMLStructure == null) {
/*  79 */       throw new NullPointerException();
/*     */     }
/*  81 */     if (!(paramXMLStructure instanceof DOMStructure)) {
/*  82 */       throw new ClassCastException("parent must be of type DOMStructure");
/*     */     }
/*  84 */     this
/*  85 */       .transformElem = (Element)((DOMStructure)paramXMLStructure).getNode();
/*  86 */     this.ownerDoc = DOMUtils.getOwnerDocument(this.transformElem);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshalParams(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
/*  92 */     if (paramXMLCryptoContext != null && !(paramXMLCryptoContext instanceof javax.xml.crypto.dom.DOMCryptoContext)) {
/*  93 */       throw new ClassCastException("context must be of type DOMCryptoContext");
/*     */     }
/*     */     
/*  96 */     if (paramXMLStructure == null) {
/*  97 */       throw new NullPointerException();
/*     */     }
/*  99 */     if (!(paramXMLStructure instanceof DOMStructure)) {
/* 100 */       throw new ClassCastException("parent must be of type DOMStructure");
/*     */     }
/* 102 */     this
/* 103 */       .transformElem = (Element)((DOMStructure)paramXMLStructure).getNode();
/* 104 */     this.ownerDoc = DOMUtils.getOwnerDocument(this.transformElem);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Data canonicalize(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws TransformException {
/* 110 */     return canonicalize(paramData, paramXMLCryptoContext, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Data canonicalize(Data paramData, XMLCryptoContext paramXMLCryptoContext, OutputStream paramOutputStream) throws TransformException {
/* 116 */     if (this.apacheCanonicalizer == null) {
/*     */       try {
/* 118 */         this.apacheCanonicalizer = Canonicalizer.getInstance(getAlgorithm());
/* 119 */         if (log.isLoggable(Level.FINE)) {
/* 120 */           log.log(Level.FINE, "Created canonicalizer for algorithm: " + getAlgorithm());
/*     */         }
/* 122 */       } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
/* 123 */         throw new TransformException("Couldn't find Canonicalizer for: " + 
/* 124 */             getAlgorithm() + ": " + invalidCanonicalizerException
/* 125 */             .getMessage(), invalidCanonicalizerException);
/*     */       } 
/*     */     }
/*     */     
/* 129 */     if (paramOutputStream != null) {
/* 130 */       this.apacheCanonicalizer.setWriter(paramOutputStream);
/*     */     } else {
/* 132 */       this.apacheCanonicalizer.setWriter(new ByteArrayOutputStream());
/*     */     } 
/*     */     
/*     */     try {
/* 136 */       Set<Node> set = null;
/* 137 */       if (paramData instanceof ApacheData)
/*     */       
/* 139 */       { XMLSignatureInput xMLSignatureInput = ((ApacheData)paramData).getXMLSignatureInput();
/* 140 */         if (xMLSignatureInput.isElement()) {
/* 141 */           if (this.inclusiveNamespaces != null) {
/* 142 */             return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer
/*     */                   
/* 144 */                   .canonicalizeSubtree(xMLSignatureInput.getSubNode(), this.inclusiveNamespaces)));
/*     */           }
/* 146 */           return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer
/*     */                 
/* 148 */                 .canonicalizeSubtree(xMLSignatureInput.getSubNode())));
/*     */         } 
/* 150 */         if (xMLSignatureInput.isNodeSet()) {
/* 151 */           set = xMLSignatureInput.getNodeSet();
/*     */         } else {
/* 153 */           return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer
/* 154 */                 .canonicalize(
/* 155 */                   Utils.readBytesFromStream(xMLSignatureInput.getOctetStream()))));
/*     */         }  }
/* 157 */       else { if (paramData instanceof DOMSubTreeData) {
/* 158 */           DOMSubTreeData dOMSubTreeData = (DOMSubTreeData)paramData;
/* 159 */           if (this.inclusiveNamespaces != null) {
/* 160 */             return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer
/*     */                   
/* 162 */                   .canonicalizeSubtree(dOMSubTreeData.getRoot(), this.inclusiveNamespaces)));
/*     */           }
/* 164 */           return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer
/*     */                 
/* 166 */                 .canonicalizeSubtree(dOMSubTreeData.getRoot())));
/*     */         } 
/* 168 */         if (paramData instanceof NodeSetData) {
/* 169 */           NodeSetData<Node> nodeSetData = (NodeSetData)paramData;
/*     */ 
/*     */           
/* 172 */           Set<Node> set1 = Utils.toNodeSet(nodeSetData.iterator());
/* 173 */           set = set1;
/* 174 */           if (log.isLoggable(Level.FINE)) {
/* 175 */             log.log(Level.FINE, "Canonicalizing " + set.size() + " nodes");
/*     */           }
/*     */         } else {
/* 178 */           return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer
/* 179 */                 .canonicalize(
/* 180 */                   Utils.readBytesFromStream(((OctetStreamData)paramData)
/* 181 */                     .getOctetStream()))));
/*     */         }  }
/* 183 */        if (this.inclusiveNamespaces != null) {
/* 184 */         return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer
/*     */               
/* 186 */               .canonicalizeXPathNodeSet(set, this.inclusiveNamespaces)));
/*     */       }
/* 188 */       return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer
/* 189 */             .canonicalizeXPathNodeSet(set)));
/*     */     }
/* 191 */     catch (Exception exception) {
/* 192 */       throw new TransformException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext, OutputStream paramOutputStream) throws TransformException {
/*     */     XMLSignatureInput xMLSignatureInput;
/* 199 */     if (paramData == null) {
/* 200 */       throw new NullPointerException("data must not be null");
/*     */     }
/* 202 */     if (paramOutputStream == null) {
/* 203 */       throw new NullPointerException("output stream must not be null");
/*     */     }
/*     */     
/* 206 */     if (this.ownerDoc == null) {
/* 207 */       throw new TransformException("transform must be marshalled");
/*     */     }
/*     */     
/* 210 */     if (this.apacheTransform == null) {
/*     */       try {
/* 212 */         this
/* 213 */           .apacheTransform = new Transform(this.ownerDoc, getAlgorithm(), this.transformElem.getChildNodes());
/* 214 */         this.apacheTransform.setElement(this.transformElem, paramXMLCryptoContext.getBaseURI());
/* 215 */         if (log.isLoggable(Level.FINE)) {
/* 216 */           log.log(Level.FINE, "Created transform for algorithm: " + getAlgorithm());
/*     */         }
/* 218 */       } catch (Exception exception) {
/* 219 */         throw new TransformException("Couldn't find Transform for: " + 
/* 220 */             getAlgorithm(), exception);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 225 */     if (paramData instanceof ApacheData) {
/* 226 */       if (log.isLoggable(Level.FINE)) {
/* 227 */         log.log(Level.FINE, "ApacheData = true");
/*     */       }
/* 229 */       xMLSignatureInput = ((ApacheData)paramData).getXMLSignatureInput();
/* 230 */     } else if (paramData instanceof NodeSetData) {
/* 231 */       if (log.isLoggable(Level.FINE)) {
/* 232 */         log.log(Level.FINE, "isNodeSet() = true");
/*     */       }
/* 234 */       if (paramData instanceof DOMSubTreeData) {
/* 235 */         DOMSubTreeData dOMSubTreeData = (DOMSubTreeData)paramData;
/* 236 */         xMLSignatureInput = new XMLSignatureInput(dOMSubTreeData.getRoot());
/* 237 */         xMLSignatureInput.setExcludeComments(dOMSubTreeData.excludeComments());
/*     */       }
/*     */       else {
/*     */         
/* 241 */         Set<Node> set = Utils.toNodeSet(((NodeSetData<Node>)paramData).iterator());
/* 242 */         xMLSignatureInput = new XMLSignatureInput(set);
/*     */       } 
/*     */     } else {
/* 245 */       if (log.isLoggable(Level.FINE)) {
/* 246 */         log.log(Level.FINE, "isNodeSet() = false");
/*     */       }
/*     */       
/*     */       try {
/* 250 */         xMLSignatureInput = new XMLSignatureInput(((OctetStreamData)paramData).getOctetStream());
/* 251 */       } catch (Exception exception) {
/* 252 */         throw new TransformException(exception);
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 257 */       xMLSignatureInput = this.apacheTransform.performTransform(xMLSignatureInput, paramOutputStream);
/* 258 */       if (!xMLSignatureInput.isNodeSet() && !xMLSignatureInput.isElement()) {
/* 259 */         return null;
/*     */       }
/* 261 */       if (xMLSignatureInput.isOctetStream()) {
/* 262 */         return new ApacheOctetStreamData(xMLSignatureInput);
/*     */       }
/* 264 */       return new ApacheNodeSetData(xMLSignatureInput);
/*     */     }
/* 266 */     catch (Exception exception) {
/* 267 */       throw new TransformException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final boolean isFeatureSupported(String paramString) {
/* 272 */     if (paramString == null) {
/* 273 */       throw new NullPointerException();
/*     */     }
/* 275 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/ApacheCanonicalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */