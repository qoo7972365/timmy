/*     */ package com.sun.xml.internal.stream;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.PropertyManager;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*     */ import com.sun.org.apache.xerces.internal.util.URI;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLResourceIdentifierImpl;
/*     */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import java.io.File;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
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
/*     */ public class XMLEntityStorage
/*     */ {
/*     */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*     */   protected static final String WARN_ON_DUPLICATE_ENTITYDEF = "http://apache.org/xml/features/warn-on-duplicate-entitydef";
/*     */   protected boolean fWarnDuplicateEntityDef;
/*  66 */   protected Map<String, Entity> fEntities = new HashMap<>();
/*     */   
/*     */   protected Entity.ScannedEntity fCurrentEntity;
/*     */   
/*     */   private XMLEntityManager fEntityManager;
/*     */   
/*     */   protected XMLErrorReporter fErrorReporter;
/*     */   
/*     */   protected PropertyManager fPropertyManager;
/*     */   
/*     */   protected boolean fInExternalSubset = false;
/*     */   
/*     */   private static String gUserDir;
/*     */   
/*     */   private static String gEscapedUserDir;
/*     */   
/*     */   public XMLEntityStorage(PropertyManager propertyManager) {
/*  83 */     this.fPropertyManager = propertyManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEntityStorage(XMLEntityManager entityManager) {
/*  90 */     this.fEntityManager = entityManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset(PropertyManager propertyManager) {
/*  95 */     this.fErrorReporter = (XMLErrorReporter)propertyManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/*  96 */     this.fEntities.clear();
/*  97 */     this.fCurrentEntity = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 102 */     this.fEntities.clear();
/* 103 */     this.fCurrentEntity = null;
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
/*     */   
/*     */   public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
/* 125 */     this.fWarnDuplicateEntityDef = componentManager.getFeature("http://apache.org/xml/features/warn-on-duplicate-entitydef", false);
/*     */     
/* 127 */     this.fErrorReporter = (XMLErrorReporter)componentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/*     */     
/* 129 */     this.fEntities.clear();
/* 130 */     this.fCurrentEntity = null;
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
/*     */   public Entity getEntity(String name) {
/* 142 */     return this.fEntities.get(name);
/*     */   }
/*     */   
/*     */   public boolean hasEntities() {
/* 146 */     return (this.fEntities != null);
/*     */   }
/*     */   
/*     */   public int getEntitySize() {
/* 150 */     return this.fEntities.size();
/*     */   }
/*     */   
/*     */   public Enumeration getEntityKeys() {
/* 154 */     return Collections.enumeration(this.fEntities.keySet());
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
/*     */   public void addInternalEntity(String name, String text) {
/* 171 */     if (!this.fEntities.containsKey(name)) {
/* 172 */       Entity entity = new Entity.InternalEntity(name, text, this.fInExternalSubset);
/* 173 */       this.fEntities.put(name, entity);
/*     */     
/*     */     }
/* 176 */     else if (this.fWarnDuplicateEntityDef) {
/* 177 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[] { name }, (short)0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExternalEntity(String name, String publicId, String literalSystemId, String baseSystemId) {
/* 210 */     if (!this.fEntities.containsKey(name)) {
/* 211 */       if (baseSystemId == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 222 */         if (this.fCurrentEntity != null && this.fCurrentEntity.entityLocation != null) {
/* 223 */           baseSystemId = this.fCurrentEntity.entityLocation.getExpandedSystemId();
/*     */         }
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 237 */       this.fCurrentEntity = this.fEntityManager.getCurrentEntity();
/*     */ 
/*     */       
/* 240 */       Entity entity = new Entity.ExternalEntity(name, new XMLResourceIdentifierImpl(publicId, literalSystemId, baseSystemId, expandSystemId(literalSystemId, baseSystemId)), null, this.fInExternalSubset);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       this.fEntities.put(name, entity);
/*     */     
/*     */     }
/* 248 */     else if (this.fWarnDuplicateEntityDef) {
/* 249 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[] { name }, (short)0);
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
/*     */   public boolean isExternalEntity(String entityName) {
/* 267 */     Entity entity = this.fEntities.get(entityName);
/* 268 */     if (entity == null) {
/* 269 */       return false;
/*     */     }
/* 271 */     return entity.isExternal();
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
/*     */   public boolean isEntityDeclInExternalSubset(String entityName) {
/* 284 */     Entity entity = this.fEntities.get(entityName);
/* 285 */     if (entity == null) {
/* 286 */       return false;
/*     */     }
/* 288 */     return entity.isEntityDeclInExternalSubset();
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
/*     */ 
/*     */   
/*     */   public void addUnparsedEntity(String name, String publicId, String systemId, String baseSystemId, String notation) {
/* 311 */     this.fCurrentEntity = this.fEntityManager.getCurrentEntity();
/* 312 */     if (!this.fEntities.containsKey(name)) {
/* 313 */       Entity entity = new Entity.ExternalEntity(name, new XMLResourceIdentifierImpl(publicId, systemId, baseSystemId, null), notation, this.fInExternalSubset);
/*     */ 
/*     */       
/* 316 */       this.fEntities.put(name, entity);
/*     */     
/*     */     }
/* 319 */     else if (this.fWarnDuplicateEntityDef) {
/* 320 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[] { name }, (short)0);
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
/*     */   public boolean isUnparsedEntity(String entityName) {
/* 337 */     Entity entity = this.fEntities.get(entityName);
/* 338 */     if (entity == null) {
/* 339 */       return false;
/*     */     }
/* 341 */     return entity.isUnparsed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeclaredEntity(String entityName) {
/* 352 */     Entity entity = this.fEntities.get(entityName);
/* 353 */     return (entity != null);
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
/*     */   public static String expandSystemId(String systemId) {
/* 369 */     return expandSystemId(systemId, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 377 */   private static boolean[] gNeedEscaping = new boolean[128];
/*     */   
/* 379 */   private static char[] gAfterEscaping1 = new char[128];
/*     */   
/* 381 */   private static char[] gAfterEscaping2 = new char[128];
/* 382 */   private static char[] gHexChs = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/*     */   
/*     */   static {
/* 386 */     for (int i = 0; i <= 31; i++) {
/* 387 */       gNeedEscaping[i] = true;
/* 388 */       gAfterEscaping1[i] = gHexChs[i >> 4];
/* 389 */       gAfterEscaping2[i] = gHexChs[i & 0xF];
/*     */     } 
/* 391 */     gNeedEscaping[127] = true;
/* 392 */     gAfterEscaping1[127] = '7';
/* 393 */     gAfterEscaping2[127] = 'F';
/* 394 */     char[] escChs = { ' ', '<', '>', '#', '%', '"', '{', '}', '|', '\\', '^', '~', '[', ']', '`' };
/*     */     
/* 396 */     int len = escChs.length;
/*     */     
/* 398 */     for (int j = 0; j < len; j++) {
/* 399 */       char ch = escChs[j];
/* 400 */       gNeedEscaping[ch] = true;
/* 401 */       gAfterEscaping1[ch] = gHexChs[ch >> 4];
/* 402 */       gAfterEscaping2[ch] = gHexChs[ch & 0xF];
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
/*     */   private static synchronized String getUserDir() {
/* 417 */     String userDir = "";
/*     */     try {
/* 419 */       userDir = SecuritySupport.getSystemProperty("user.dir");
/*     */     }
/* 421 */     catch (SecurityException securityException) {}
/*     */ 
/*     */ 
/*     */     
/* 425 */     if (userDir.length() == 0) {
/* 426 */       return "";
/*     */     }
/*     */ 
/*     */     
/* 430 */     if (userDir.equals(gUserDir)) {
/* 431 */       return gEscapedUserDir;
/*     */     }
/*     */ 
/*     */     
/* 435 */     gUserDir = userDir;
/*     */     
/* 437 */     char separator = File.separatorChar;
/* 438 */     userDir = userDir.replace(separator, '/');
/*     */     
/* 440 */     int len = userDir.length();
/* 441 */     StringBuffer buffer = new StringBuffer(len * 3);
/*     */     
/* 443 */     if (len >= 2 && userDir.charAt(1) == ':') {
/* 444 */       int ch = Character.toUpperCase(userDir.charAt(0));
/* 445 */       if (ch >= 65 && ch <= 90) {
/* 446 */         buffer.append('/');
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 451 */     int i = 0;
/* 452 */     for (; i < len; i++) {
/* 453 */       int ch = userDir.charAt(i);
/*     */       
/* 455 */       if (ch >= 128)
/*     */         break; 
/* 457 */       if (gNeedEscaping[ch]) {
/* 458 */         buffer.append('%');
/* 459 */         buffer.append(gAfterEscaping1[ch]);
/* 460 */         buffer.append(gAfterEscaping2[ch]);
/*     */       }
/*     */       else {
/*     */         
/* 464 */         buffer.append((char)ch);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 469 */     if (i < len) {
/*     */       
/* 471 */       byte[] bytes = null;
/*     */       
/*     */       try {
/* 474 */         bytes = userDir.substring(i).getBytes("UTF-8");
/* 475 */       } catch (UnsupportedEncodingException e) {
/*     */         
/* 477 */         return userDir;
/*     */       } 
/* 479 */       len = bytes.length;
/*     */ 
/*     */       
/* 482 */       for (i = 0; i < len; i++) {
/* 483 */         byte b = bytes[i];
/*     */         
/* 485 */         if (b < 0) {
/* 486 */           int ch = b + 256;
/* 487 */           buffer.append('%');
/* 488 */           buffer.append(gHexChs[ch >> 4]);
/* 489 */           buffer.append(gHexChs[ch & 0xF]);
/*     */         }
/* 491 */         else if (gNeedEscaping[b]) {
/* 492 */           buffer.append('%');
/* 493 */           buffer.append(gAfterEscaping1[b]);
/* 494 */           buffer.append(gAfterEscaping2[b]);
/*     */         } else {
/*     */           
/* 497 */           buffer.append((char)b);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 503 */     if (!userDir.endsWith("/")) {
/* 504 */       buffer.append('/');
/*     */     }
/* 506 */     gEscapedUserDir = buffer.toString();
/*     */     
/* 508 */     return gEscapedUserDir;
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
/*     */   public static String expandSystemId(String systemId, String baseSystemId) {
/* 527 */     if (systemId == null || systemId.length() == 0) {
/* 528 */       return systemId;
/*     */     }
/*     */     
/*     */     try {
/* 532 */       new URI(systemId);
/* 533 */       return systemId;
/* 534 */     } catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException malformedURIException) {
/*     */ 
/*     */ 
/*     */       
/* 538 */       String id = fixURI(systemId);
/*     */ 
/*     */       
/* 541 */       URI base = null;
/* 542 */       URI uri = null;
/*     */       try {
/* 544 */         if (baseSystemId == null || baseSystemId.length() == 0 || baseSystemId
/* 545 */           .equals(systemId)) {
/* 546 */           String dir = getUserDir();
/* 547 */           base = new URI("file", "", dir, null, null);
/*     */         } else {
/*     */           
/*     */           try {
/* 551 */             base = new URI(fixURI(baseSystemId));
/*     */           }
/* 553 */           catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e) {
/* 554 */             if (baseSystemId.indexOf(':') != -1) {
/*     */ 
/*     */               
/* 557 */               base = new URI("file", "", fixURI(baseSystemId), null, null);
/*     */             } else {
/*     */               
/* 560 */               String dir = getUserDir();
/* 561 */               dir = dir + fixURI(baseSystemId);
/* 562 */               base = new URI("file", "", dir, null, null);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 567 */         uri = new URI(base, id);
/*     */       }
/* 569 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 574 */       if (uri == null) {
/* 575 */         return systemId;
/*     */       }
/* 577 */       return uri.toString();
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
/*     */   protected static String fixURI(String str) {
/* 594 */     str = str.replace(File.separatorChar, '/');
/*     */ 
/*     */     
/* 597 */     if (str.length() >= 2) {
/* 598 */       char ch1 = str.charAt(1);
/*     */       
/* 600 */       if (ch1 == ':') {
/* 601 */         char ch0 = Character.toUpperCase(str.charAt(0));
/* 602 */         if (ch0 >= 'A' && ch0 <= 'Z') {
/* 603 */           str = "/" + str;
/*     */         
/*     */         }
/*     */       }
/* 607 */       else if (ch1 == '/' && str.charAt(0) == '/') {
/* 608 */         str = "file:" + str;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 613 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExternalSubset() {
/* 619 */     this.fInExternalSubset = true;
/*     */   }
/*     */   
/*     */   public void endExternalSubset() {
/* 623 */     this.fInExternalSubset = false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/XMLEntityStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */