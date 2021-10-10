/*     */ package com.sun.org.apache.xml.internal.security;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
/*     */ import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver;
/*     */ import com.sun.org.apache.xml.internal.security.transforms.Transform;
/*     */ import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
/*     */ import com.sun.org.apache.xml.internal.security.utils.I18n;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Attr;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Init
/*     */ {
/*     */   public static final String CONF_NS = "http://www.xmlsecurity.org/NS/#configuration";
/*  66 */   private static Logger log = Logger.getLogger(Init.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean alreadyInitialized = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final synchronized boolean isInitialized() {
/*  76 */     return alreadyInitialized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void init() {
/*  84 */     if (alreadyInitialized) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  89 */     InputStream inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>()
/*     */         {
/*     */           public InputStream run()
/*     */           {
/*  93 */             String str = System.getProperty("com.sun.org.apache.xml.internal.security.resource.config");
/*  94 */             if (str == null) {
/*  95 */               return null;
/*     */             }
/*  97 */             return getClass().getResourceAsStream(str);
/*     */           }
/*     */         });
/* 100 */     if (inputStream == null) {
/* 101 */       dynamicInit();
/*     */     } else {
/* 103 */       fileInit(inputStream);
/*     */     } 
/*     */     
/* 106 */     alreadyInitialized = true;
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
/*     */   private static void dynamicInit() {
/* 118 */     I18n.init("en", "US");
/*     */     
/* 120 */     if (log.isLoggable(Level.FINE)) {
/* 121 */       log.log(Level.FINE, "Registering default algorithms");
/*     */     }
/*     */     try {
/* 124 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             
/*     */             public Void run() throws XMLSecurityException
/*     */             {
/* 129 */               ElementProxy.registerDefaultPrefixes();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 134 */               Transform.registerDefaultAlgorithms();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 139 */               SignatureAlgorithm.registerDefaultAlgorithms();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 144 */               JCEMapper.registerDefaultAlgorithms();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 149 */               Canonicalizer.registerDefaultAlgorithms();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 154 */               ResourceResolver.registerDefaultResolvers();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 159 */               KeyResolver.registerDefaultResolvers();
/*     */               
/* 161 */               return null;
/*     */             }
/*     */           });
/* 164 */     } catch (PrivilegedActionException privilegedActionException) {
/* 165 */       XMLSecurityException xMLSecurityException = (XMLSecurityException)privilegedActionException.getException();
/* 166 */       log.log(Level.SEVERE, xMLSecurityException.getMessage(), xMLSecurityException);
/* 167 */       xMLSecurityException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fileInit(InputStream paramInputStream) {
/*     */     try {
/* 177 */       DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 178 */       documentBuilderFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE.booleanValue());
/*     */       
/* 180 */       documentBuilderFactory.setNamespaceAware(true);
/* 181 */       documentBuilderFactory.setValidating(false);
/*     */       
/* 183 */       DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
/* 184 */       Document document = documentBuilder.parse(paramInputStream);
/* 185 */       Node node1 = document.getFirstChild();
/* 186 */       for (; node1 != null && 
/* 187 */         !"Configuration".equals(node1.getLocalName()); node1 = node1.getNextSibling());
/*     */ 
/*     */ 
/*     */       
/* 191 */       if (node1 == null) {
/* 192 */         log.log(Level.SEVERE, "Error in reading configuration file - Configuration element not found");
/*     */         return;
/*     */       } 
/* 195 */       for (Node node2 = node1.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
/* 196 */         if (1 == node2.getNodeType()) {
/*     */ 
/*     */           
/* 199 */           String str = node2.getLocalName();
/* 200 */           if (str.equals("ResourceBundles")) {
/* 201 */             Element element = (Element)node2;
/*     */             
/* 203 */             Attr attr1 = element.getAttributeNode("defaultLanguageCode");
/* 204 */             Attr attr2 = element.getAttributeNode("defaultCountryCode");
/*     */             
/* 206 */             String str1 = (attr1 == null) ? null : attr1.getNodeValue();
/*     */             
/* 208 */             String str2 = (attr2 == null) ? null : attr2.getNodeValue();
/* 209 */             I18n.init(str1, str2);
/*     */           } 
/*     */           
/* 212 */           if (str.equals("CanonicalizationMethods")) {
/*     */             
/* 214 */             Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "CanonicalizationMethod");
/*     */             
/* 216 */             for (byte b = 0; b < arrayOfElement.length; b++) {
/* 217 */               String str1 = arrayOfElement[b].getAttributeNS(null, "URI");
/*     */               
/* 219 */               String str2 = arrayOfElement[b].getAttributeNS(null, "JAVACLASS");
/*     */               try {
/* 221 */                 Canonicalizer.register(str1, str2);
/* 222 */                 if (log.isLoggable(Level.FINE)) {
/* 223 */                   log.log(Level.FINE, "Canonicalizer.register(" + str1 + ", " + str2 + ")");
/*     */                 }
/* 225 */               } catch (ClassNotFoundException classNotFoundException) {
/* 226 */                 Object[] arrayOfObject = { str1, str2 };
/* 227 */                 log.log(Level.SEVERE, I18n.translate("algorithm.classDoesNotExist", arrayOfObject));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 232 */           if (str.equals("TransformAlgorithms")) {
/*     */             
/* 234 */             Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "TransformAlgorithm");
/*     */             
/* 236 */             for (byte b = 0; b < arrayOfElement.length; b++) {
/* 237 */               String str1 = arrayOfElement[b].getAttributeNS(null, "URI");
/*     */               
/* 239 */               String str2 = arrayOfElement[b].getAttributeNS(null, "JAVACLASS");
/*     */               try {
/* 241 */                 Transform.register(str1, str2);
/* 242 */                 if (log.isLoggable(Level.FINE)) {
/* 243 */                   log.log(Level.FINE, "Transform.register(" + str1 + ", " + str2 + ")");
/*     */                 }
/* 245 */               } catch (ClassNotFoundException classNotFoundException) {
/* 246 */                 Object[] arrayOfObject = { str1, str2 };
/*     */                 
/* 248 */                 log.log(Level.SEVERE, I18n.translate("algorithm.classDoesNotExist", arrayOfObject));
/* 249 */               } catch (NoClassDefFoundError noClassDefFoundError) {
/* 250 */                 log.log(Level.WARNING, "Not able to found dependencies for algorithm, I'll keep working.");
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 255 */           if ("JCEAlgorithmMappings".equals(str)) {
/* 256 */             Node node = ((Element)node2).getElementsByTagName("Algorithms").item(0);
/* 257 */             if (node != null) {
/*     */               
/* 259 */               Element[] arrayOfElement = XMLUtils.selectNodes(node.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "Algorithm");
/* 260 */               for (byte b = 0; b < arrayOfElement.length; b++) {
/* 261 */                 Element element = arrayOfElement[b];
/* 262 */                 String str1 = element.getAttribute("URI");
/* 263 */                 JCEMapper.register(str1, new JCEMapper.Algorithm(element));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 268 */           if (str.equals("SignatureAlgorithms")) {
/*     */             
/* 270 */             Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "SignatureAlgorithm");
/*     */             
/* 272 */             for (byte b = 0; b < arrayOfElement.length; b++) {
/* 273 */               String str1 = arrayOfElement[b].getAttributeNS(null, "URI");
/*     */               
/* 275 */               String str2 = arrayOfElement[b].getAttributeNS(null, "JAVACLASS");
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 280 */                 SignatureAlgorithm.register(str1, str2);
/* 281 */                 if (log.isLoggable(Level.FINE)) {
/* 282 */                   log.log(Level.FINE, "SignatureAlgorithm.register(" + str1 + ", " + str2 + ")");
/*     */                 }
/*     */               }
/* 285 */               catch (ClassNotFoundException classNotFoundException) {
/* 286 */                 Object[] arrayOfObject = { str1, str2 };
/*     */                 
/* 288 */                 log.log(Level.SEVERE, I18n.translate("algorithm.classDoesNotExist", arrayOfObject));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 293 */           if (str.equals("ResourceResolvers")) {
/*     */             
/* 295 */             Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "Resolver");
/*     */             
/* 297 */             for (byte b = 0; b < arrayOfElement.length; b++) {
/*     */               
/* 299 */               String str1 = arrayOfElement[b].getAttributeNS(null, "JAVACLASS");
/*     */               
/* 301 */               String str2 = arrayOfElement[b].getAttributeNS(null, "DESCRIPTION");
/*     */               
/* 303 */               if (str2 != null && str2.length() > 0) {
/* 304 */                 if (log.isLoggable(Level.FINE)) {
/* 305 */                   log.log(Level.FINE, "Register Resolver: " + str1 + ": " + str2);
/*     */                 
/*     */                 }
/*     */               }
/* 309 */               else if (log.isLoggable(Level.FINE)) {
/* 310 */                 log.log(Level.FINE, "Register Resolver: " + str1 + ": For unknown purposes");
/*     */               } 
/*     */ 
/*     */               
/*     */               try {
/* 315 */                 ResourceResolver.register(str1);
/* 316 */               } catch (Throwable throwable) {
/* 317 */                 log.log(Level.WARNING, "Cannot register:" + str1 + " perhaps some needed jars are not installed", throwable);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 326 */           if (str.equals("KeyResolver")) {
/*     */             
/* 328 */             Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "Resolver");
/* 329 */             ArrayList<String> arrayList = new ArrayList(arrayOfElement.length);
/* 330 */             for (byte b = 0; b < arrayOfElement.length; b++) {
/*     */               
/* 332 */               String str1 = arrayOfElement[b].getAttributeNS(null, "JAVACLASS");
/*     */               
/* 334 */               String str2 = arrayOfElement[b].getAttributeNS(null, "DESCRIPTION");
/*     */               
/* 336 */               if (str2 != null && str2.length() > 0) {
/* 337 */                 if (log.isLoggable(Level.FINE)) {
/* 338 */                   log.log(Level.FINE, "Register Resolver: " + str1 + ": " + str2);
/*     */                 
/*     */                 }
/*     */               }
/* 342 */               else if (log.isLoggable(Level.FINE)) {
/* 343 */                 log.log(Level.FINE, "Register Resolver: " + str1 + ": For unknown purposes");
/*     */               } 
/*     */ 
/*     */               
/* 347 */               arrayList.add(str1);
/*     */             } 
/* 349 */             KeyResolver.registerClassNames(arrayList);
/*     */           } 
/*     */ 
/*     */           
/* 353 */           if (str.equals("PrefixMappings")) {
/* 354 */             if (log.isLoggable(Level.FINE)) {
/* 355 */               log.log(Level.FINE, "Now I try to bind prefixes:");
/*     */             }
/*     */ 
/*     */             
/* 359 */             Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "PrefixMapping");
/*     */             
/* 361 */             for (byte b = 0; b < arrayOfElement.length; b++)
/* 362 */             { String str1 = arrayOfElement[b].getAttributeNS(null, "namespace");
/* 363 */               String str2 = arrayOfElement[b].getAttributeNS(null, "prefix");
/* 364 */               if (log.isLoggable(Level.FINE)) {
/* 365 */                 log.log(Level.FINE, "Now I try to bind " + str2 + " to " + str1);
/*     */               }
/* 367 */               ElementProxy.setDefaultPrefix(str1, str2); } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 371 */     } catch (Exception exception) {
/* 372 */       log.log(Level.SEVERE, "Bad: ", exception);
/* 373 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/Init.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */