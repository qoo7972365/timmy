/*     */ package com.sun.org.apache.xml.internal.security.c14n;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_OmitComments;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_WithComments;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315ExclOmitComments;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315ExclWithComments;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315OmitComments;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315WithComments;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.implementations.CanonicalizerPhysical;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.AlgorithmAlreadyRegisteredException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler;
/*     */ import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Canonicalizer
/*     */ {
/*     */   public static final String ENCODING = "UTF8";
/*     */   public static final String XPATH_C14N_WITH_COMMENTS_SINGLE_NODE = "(.//. | .//@* | .//namespace::*)";
/*     */   public static final String ALGO_ID_C14N_OMIT_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
/*     */   public static final String ALGO_ID_C14N_WITH_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
/*     */   public static final String ALGO_ID_C14N_EXCL_OMIT_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#";
/*     */   public static final String ALGO_ID_C14N_EXCL_WITH_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
/*     */   public static final String ALGO_ID_C14N11_OMIT_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11";
/*     */   public static final String ALGO_ID_C14N11_WITH_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11#WithComments";
/*     */   public static final String ALGO_ID_C14N_PHYSICAL = "http://santuario.apache.org/c14n/physical";
/* 102 */   private static Map<String, Class<? extends CanonicalizerSpi>> canonicalizerHash = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final CanonicalizerSpi canonicalizerSpi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Canonicalizer(String paramString) throws InvalidCanonicalizerException {
/*     */     try {
/* 116 */       Class<CanonicalizerSpi> clazz = (Class)canonicalizerHash.get(paramString);
/*     */       
/* 118 */       this.canonicalizerSpi = clazz.newInstance();
/* 119 */       this.canonicalizerSpi.reset = true;
/* 120 */     } catch (Exception exception) {
/* 121 */       Object[] arrayOfObject = { paramString };
/* 122 */       throw new InvalidCanonicalizerException("signature.Canonicalizer.UnknownCanonicalizer", arrayOfObject, exception);
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
/*     */   public static final Canonicalizer getInstance(String paramString) throws InvalidCanonicalizerException {
/* 137 */     return new Canonicalizer(paramString);
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
/*     */   public static void register(String paramString1, String paramString2) throws AlgorithmAlreadyRegisteredException, ClassNotFoundException {
/* 152 */     JavaUtils.checkRegisterPermission();
/*     */ 
/*     */     
/* 155 */     Class clazz = canonicalizerHash.get(paramString1);
/*     */     
/* 157 */     if (clazz != null) {
/* 158 */       Object[] arrayOfObject = { paramString1, clazz };
/* 159 */       throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", arrayOfObject);
/*     */     } 
/*     */     
/* 162 */     canonicalizerHash.put(paramString1, 
/* 163 */         Class.forName(paramString2));
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
/*     */   public static void register(String paramString, Class<? extends CanonicalizerSpi> paramClass) throws AlgorithmAlreadyRegisteredException, ClassNotFoundException {
/* 178 */     JavaUtils.checkRegisterPermission();
/*     */     
/* 180 */     Class clazz = canonicalizerHash.get(paramString);
/*     */     
/* 182 */     if (clazz != null) {
/* 183 */       Object[] arrayOfObject = { paramString, clazz };
/* 184 */       throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", arrayOfObject);
/*     */     } 
/*     */     
/* 187 */     canonicalizerHash.put(paramString, paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerDefaultAlgorithms() {
/* 194 */     canonicalizerHash.put("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", Canonicalizer20010315OmitComments.class);
/*     */ 
/*     */ 
/*     */     
/* 198 */     canonicalizerHash.put("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments", Canonicalizer20010315WithComments.class);
/*     */ 
/*     */ 
/*     */     
/* 202 */     canonicalizerHash.put("http://www.w3.org/2001/10/xml-exc-c14n#", Canonicalizer20010315ExclOmitComments.class);
/*     */ 
/*     */ 
/*     */     
/* 206 */     canonicalizerHash.put("http://www.w3.org/2001/10/xml-exc-c14n#WithComments", Canonicalizer20010315ExclWithComments.class);
/*     */ 
/*     */ 
/*     */     
/* 210 */     canonicalizerHash.put("http://www.w3.org/2006/12/xml-c14n11", Canonicalizer11_OmitComments.class);
/*     */ 
/*     */ 
/*     */     
/* 214 */     canonicalizerHash.put("http://www.w3.org/2006/12/xml-c14n11#WithComments", Canonicalizer11_WithComments.class);
/*     */ 
/*     */ 
/*     */     
/* 218 */     canonicalizerHash.put("http://santuario.apache.org/c14n/physical", CanonicalizerPhysical.class);
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
/*     */   public final String getURI() {
/* 230 */     return this.canonicalizerSpi.engineGetURI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIncludeComments() {
/* 239 */     return this.canonicalizerSpi.engineGetIncludeComments();
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
/*     */   public byte[] canonicalize(byte[] paramArrayOfbyte) throws ParserConfigurationException, IOException, SAXException, CanonicalizationException {
/* 257 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/* 258 */     InputSource inputSource = new InputSource(byteArrayInputStream);
/* 259 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 260 */     documentBuilderFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/*     */     
/* 262 */     documentBuilderFactory.setNamespaceAware(true);
/*     */ 
/*     */     
/* 265 */     documentBuilderFactory.setValidating(true);
/*     */     
/* 267 */     DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     documentBuilder.setErrorHandler(new IgnoreAllErrorHandler());
/*     */     
/* 292 */     Document document = documentBuilder.parse(inputSource);
/* 293 */     return canonicalizeSubtree(document);
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
/*     */   public byte[] canonicalizeSubtree(Node paramNode) throws CanonicalizationException {
/* 305 */     return this.canonicalizerSpi.engineCanonicalizeSubTree(paramNode);
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
/*     */   public byte[] canonicalizeSubtree(Node paramNode, String paramString) throws CanonicalizationException {
/* 318 */     return this.canonicalizerSpi.engineCanonicalizeSubTree(paramNode, paramString);
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
/*     */   public byte[] canonicalizeXPathNodeSet(NodeList paramNodeList) throws CanonicalizationException {
/* 331 */     return this.canonicalizerSpi.engineCanonicalizeXPathNodeSet(paramNodeList);
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
/*     */   public byte[] canonicalizeXPathNodeSet(NodeList paramNodeList, String paramString) throws CanonicalizationException {
/* 346 */     return this.canonicalizerSpi
/* 347 */       .engineCanonicalizeXPathNodeSet(paramNodeList, paramString);
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
/*     */   public byte[] canonicalizeXPathNodeSet(Set<Node> paramSet) throws CanonicalizationException {
/* 359 */     return this.canonicalizerSpi.engineCanonicalizeXPathNodeSet(paramSet);
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
/*     */   public byte[] canonicalizeXPathNodeSet(Set<Node> paramSet, String paramString) throws CanonicalizationException {
/* 373 */     return this.canonicalizerSpi
/* 374 */       .engineCanonicalizeXPathNodeSet(paramSet, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWriter(OutputStream paramOutputStream) {
/* 383 */     this.canonicalizerSpi.setWriter(paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getImplementingCanonicalizerClass() {
/* 392 */     return this.canonicalizerSpi.getClass().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notReset() {
/* 399 */     this.canonicalizerSpi.reset = false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/Canonicalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */