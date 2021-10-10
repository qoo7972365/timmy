/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.Init;
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
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
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
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
/*     */ public abstract class ApacheTransform
/*     */   extends TransformService
/*     */ {
/*     */   static {
/*  57 */     Init.init();
/*     */   }
/*     */ 
/*     */   
/*  61 */   private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
/*     */   private Transform apacheTransform;
/*     */   protected Document ownerDoc;
/*     */   protected Element transformElem;
/*     */   protected TransformParameterSpec params;
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
/*     */   public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws TransformException {
/* 110 */     if (paramData == null) {
/* 111 */       throw new NullPointerException("data must not be null");
/*     */     }
/* 113 */     return transformIt(paramData, paramXMLCryptoContext, (OutputStream)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext, OutputStream paramOutputStream) throws TransformException {
/* 119 */     if (paramData == null) {
/* 120 */       throw new NullPointerException("data must not be null");
/*     */     }
/* 122 */     if (paramOutputStream == null) {
/* 123 */       throw new NullPointerException("output stream must not be null");
/*     */     }
/* 125 */     return transformIt(paramData, paramXMLCryptoContext, paramOutputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   private Data transformIt(Data paramData, XMLCryptoContext paramXMLCryptoContext, OutputStream paramOutputStream) throws TransformException {
/*     */     XMLSignatureInput xMLSignatureInput;
/* 131 */     if (this.ownerDoc == null) {
/* 132 */       throw new TransformException("transform must be marshalled");
/*     */     }
/*     */     
/* 135 */     if (this.apacheTransform == null) {
/*     */       try {
/* 137 */         this
/* 138 */           .apacheTransform = new Transform(this.ownerDoc, getAlgorithm(), this.transformElem.getChildNodes());
/* 139 */         this.apacheTransform.setElement(this.transformElem, paramXMLCryptoContext.getBaseURI());
/* 140 */         if (log.isLoggable(Level.FINE)) {
/* 141 */           log.log(Level.FINE, "Created transform for algorithm: " + 
/* 142 */               getAlgorithm());
/*     */         }
/* 144 */       } catch (Exception exception) {
/* 145 */         throw new TransformException("Couldn't find Transform for: " + 
/* 146 */             getAlgorithm(), exception);
/*     */       } 
/*     */     }
/*     */     
/* 150 */     if (Utils.secureValidation(paramXMLCryptoContext)) {
/* 151 */       String str = getAlgorithm();
/* 152 */       if (Policy.restrictAlg(str)) {
/* 153 */         throw new TransformException("Transform " + str + " is forbidden when secure validation is enabled");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (paramData instanceof ApacheData) {
/* 161 */       if (log.isLoggable(Level.FINE)) {
/* 162 */         log.log(Level.FINE, "ApacheData = true");
/*     */       }
/* 164 */       xMLSignatureInput = ((ApacheData)paramData).getXMLSignatureInput();
/* 165 */     } else if (paramData instanceof NodeSetData) {
/* 166 */       if (log.isLoggable(Level.FINE)) {
/* 167 */         log.log(Level.FINE, "isNodeSet() = true");
/*     */       }
/* 169 */       if (paramData instanceof DOMSubTreeData) {
/* 170 */         if (log.isLoggable(Level.FINE)) {
/* 171 */           log.log(Level.FINE, "DOMSubTreeData = true");
/*     */         }
/* 173 */         DOMSubTreeData dOMSubTreeData = (DOMSubTreeData)paramData;
/* 174 */         xMLSignatureInput = new XMLSignatureInput(dOMSubTreeData.getRoot());
/* 175 */         xMLSignatureInput.setExcludeComments(dOMSubTreeData.excludeComments());
/*     */       }
/*     */       else {
/*     */         
/* 179 */         Set<Node> set = Utils.toNodeSet(((NodeSetData<Node>)paramData).iterator());
/* 180 */         xMLSignatureInput = new XMLSignatureInput(set);
/*     */       } 
/*     */     } else {
/* 183 */       if (log.isLoggable(Level.FINE)) {
/* 184 */         log.log(Level.FINE, "isNodeSet() = false");
/*     */       }
/*     */       
/*     */       try {
/* 188 */         xMLSignatureInput = new XMLSignatureInput(((OctetStreamData)paramData).getOctetStream());
/* 189 */       } catch (Exception exception) {
/* 190 */         throw new TransformException(exception);
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 195 */       if (paramOutputStream != null) {
/* 196 */         xMLSignatureInput = this.apacheTransform.performTransform(xMLSignatureInput, paramOutputStream);
/* 197 */         if (!xMLSignatureInput.isNodeSet() && !xMLSignatureInput.isElement()) {
/* 198 */           return null;
/*     */         }
/*     */       } else {
/* 201 */         xMLSignatureInput = this.apacheTransform.performTransform(xMLSignatureInput);
/*     */       } 
/* 203 */       if (xMLSignatureInput.isOctetStream()) {
/* 204 */         return new ApacheOctetStreamData(xMLSignatureInput);
/*     */       }
/* 206 */       return new ApacheNodeSetData(xMLSignatureInput);
/*     */     }
/* 208 */     catch (Exception exception) {
/* 209 */       throw new TransformException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final boolean isFeatureSupported(String paramString) {
/* 214 */     if (paramString == null) {
/* 215 */       throw new NullPointerException();
/*     */     }
/* 217 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/ApacheTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */