/*     */ package com.sun.org.apache.xerces.internal.utils;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.SecurityManager;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*     */ public final class XMLSecurityManager
/*     */ {
/*     */   private static final int NO_LIMIT = 0;
/*     */   private final int[] values;
/*     */   private State[] states;
/*     */   boolean secureProcessing;
/*     */   private boolean[] isSet;
/*     */   
/*     */   public enum State
/*     */   {
/*  47 */     DEFAULT("default"), FSP("FEATURE_SECURE_PROCESSING"),
/*  48 */     JAXPDOTPROPERTIES("jaxp.properties"), SYSTEMPROPERTY("system property"),
/*  49 */     APIPROPERTY("property");
/*     */     final String literal;
/*     */     
/*     */     State(String literal) {
/*  53 */       this.literal = literal;
/*     */     }
/*     */     
/*     */     String literal() {
/*  57 */       return this.literal;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Limit
/*     */   {
/*  66 */     ENTITY_EXPANSION_LIMIT("EntityExpansionLimit", "http://www.oracle.com/xml/jaxp/properties/entityExpansionLimit", "jdk.xml.entityExpansionLimit", 0, 64000),
/*     */     
/*  68 */     MAX_OCCUR_NODE_LIMIT("MaxOccurLimit", "http://www.oracle.com/xml/jaxp/properties/maxOccurLimit", "jdk.xml.maxOccurLimit", 0, 5000),
/*     */     
/*  70 */     ELEMENT_ATTRIBUTE_LIMIT("ElementAttributeLimit", "http://www.oracle.com/xml/jaxp/properties/elementAttributeLimit", "jdk.xml.elementAttributeLimit", 0, 10000),
/*     */     
/*  72 */     TOTAL_ENTITY_SIZE_LIMIT("TotalEntitySizeLimit", "http://www.oracle.com/xml/jaxp/properties/totalEntitySizeLimit", "jdk.xml.totalEntitySizeLimit", 0, 50000000),
/*     */     
/*  74 */     GENERAL_ENTITY_SIZE_LIMIT("MaxEntitySizeLimit", "http://www.oracle.com/xml/jaxp/properties/maxGeneralEntitySizeLimit", "jdk.xml.maxGeneralEntitySizeLimit", 0, 0),
/*     */     
/*  76 */     PARAMETER_ENTITY_SIZE_LIMIT("MaxEntitySizeLimit", "http://www.oracle.com/xml/jaxp/properties/maxParameterEntitySizeLimit", "jdk.xml.maxParameterEntitySizeLimit", 0, 1000000),
/*     */     
/*  78 */     MAX_ELEMENT_DEPTH_LIMIT("MaxElementDepthLimit", "http://www.oracle.com/xml/jaxp/properties/maxElementDepth", "jdk.xml.maxElementDepth", 0, 0),
/*     */     
/*  80 */     MAX_NAME_LIMIT("MaxXMLNameLimit", "http://www.oracle.com/xml/jaxp/properties/maxXMLNameLimit", "jdk.xml.maxXMLNameLimit", 1000, 1000),
/*     */     
/*  82 */     ENTITY_REPLACEMENT_LIMIT("EntityReplacementLimit", "http://www.oracle.com/xml/jaxp/properties/entityReplacementLimit", "jdk.xml.entityReplacementLimit", 0, 3000000);
/*     */     
/*     */     final String key;
/*     */     
/*     */     final String apiProperty;
/*     */     final String systemProperty;
/*     */     final int defaultValue;
/*     */     final int secureValue;
/*     */     
/*     */     Limit(String key, String apiProperty, String systemProperty, int value, int secureValue) {
/*  92 */       this.key = key;
/*  93 */       this.apiProperty = apiProperty;
/*  94 */       this.systemProperty = systemProperty;
/*  95 */       this.defaultValue = value;
/*  96 */       this.secureValue = secureValue;
/*     */     }
/*     */     
/*     */     public boolean equalsAPIPropertyName(String propertyName) {
/* 100 */       return (propertyName == null) ? false : this.apiProperty.equals(propertyName);
/*     */     }
/*     */     
/*     */     public boolean equalsSystemPropertyName(String propertyName) {
/* 104 */       return (propertyName == null) ? false : this.systemProperty.equals(propertyName);
/*     */     }
/*     */     
/*     */     public String key() {
/* 108 */       return this.key;
/*     */     }
/*     */     
/*     */     public String apiProperty() {
/* 112 */       return this.apiProperty;
/*     */     }
/*     */     
/*     */     String systemProperty() {
/* 116 */       return this.systemProperty;
/*     */     }
/*     */     
/*     */     public int defaultValue() {
/* 120 */       return this.defaultValue;
/*     */     }
/*     */     
/*     */     int secureValue() {
/* 124 */       return this.secureValue;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum NameMap
/*     */   {
/* 133 */     ENTITY_EXPANSION_LIMIT("jdk.xml.entityExpansionLimit", "entityExpansionLimit"),
/* 134 */     MAX_OCCUR_NODE_LIMIT("jdk.xml.maxOccurLimit", "maxOccurLimit"),
/* 135 */     ELEMENT_ATTRIBUTE_LIMIT("jdk.xml.elementAttributeLimit", "elementAttributeLimit");
/*     */     final String newName;
/*     */     final String oldName;
/*     */     
/*     */     NameMap(String newName, String oldName) {
/* 140 */       this.newName = newName;
/* 141 */       this.oldName = oldName;
/*     */     }
/*     */     
/*     */     String getOldName(String newName) {
/* 145 */       if (newName.equals(this.newName)) {
/* 146 */         return this.oldName;
/*     */       }
/* 148 */       return null;
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
/* 174 */   private final int indexEntityCountInfo = 10000;
/* 175 */   private String printEntityCountInfo = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityManager() {
/* 182 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityManager(boolean secureProcessing) {
/* 191 */     this.values = new int[(Limit.values()).length];
/* 192 */     this.states = new State[(Limit.values()).length];
/* 193 */     this.isSet = new boolean[(Limit.values()).length];
/* 194 */     this.secureProcessing = secureProcessing;
/* 195 */     for (Limit limit : Limit.values()) {
/* 196 */       if (secureProcessing) {
/* 197 */         this.values[limit.ordinal()] = limit.secureValue;
/* 198 */         this.states[limit.ordinal()] = State.FSP;
/*     */       } else {
/* 200 */         this.values[limit.ordinal()] = limit.defaultValue();
/* 201 */         this.states[limit.ordinal()] = State.DEFAULT;
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     readSystemProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecureProcessing(boolean secure) {
/* 212 */     this.secureProcessing = secure;
/* 213 */     for (Limit limit : Limit.values()) {
/* 214 */       if (secure) {
/* 215 */         setLimit(limit.ordinal(), State.FSP, limit.secureValue());
/*     */       } else {
/* 217 */         setLimit(limit.ordinal(), State.FSP, limit.defaultValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSecureProcessing() {
/* 227 */     return this.secureProcessing;
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
/*     */   public boolean setLimit(String propertyName, State state, Object value) {
/* 240 */     int index = getIndex(propertyName);
/* 241 */     if (index > -1) {
/* 242 */       setLimit(index, state, value);
/* 243 */       return true;
/*     */     } 
/* 245 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLimit(Limit limit, State state, int value) {
/* 256 */     setLimit(limit.ordinal(), state, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLimit(int index, State state, Object value) {
/* 267 */     if (index == 10000) {
/* 268 */       this.printEntityCountInfo = (String)value;
/*     */     } else {
/*     */       int temp;
/* 271 */       if (Integer.class.isAssignableFrom(value.getClass())) {
/* 272 */         temp = ((Integer)value).intValue();
/*     */       } else {
/* 274 */         temp = Integer.parseInt((String)value);
/* 275 */         if (temp < 0) {
/* 276 */           temp = 0;
/*     */         }
/*     */       } 
/* 279 */       setLimit(index, state, temp);
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
/*     */   public void setLimit(int index, State state, int value) {
/* 291 */     if (index == 10000) {
/*     */       
/* 293 */       this.printEntityCountInfo = "yes";
/*     */     
/*     */     }
/* 296 */     else if (state.compareTo(this.states[index]) >= 0) {
/* 297 */       this.values[index] = value;
/* 298 */       this.states[index] = state;
/* 299 */       this.isSet[index] = true;
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
/*     */   public String getLimitAsString(String propertyName) {
/* 312 */     int index = getIndex(propertyName);
/* 313 */     if (index > -1) {
/* 314 */       return getLimitValueByIndex(index);
/*     */     }
/*     */     
/* 317 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLimit(Limit limit) {
/* 326 */     return this.values[limit.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLimitValueAsString(Limit limit) {
/* 336 */     return Integer.toString(this.values[limit.ordinal()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLimitValueByIndex(int index) {
/* 346 */     if (index == 10000) {
/* 347 */       return this.printEntityCountInfo;
/*     */     }
/*     */     
/* 350 */     return Integer.toString(this.values[index]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State getState(Limit limit) {
/* 360 */     return this.states[limit.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStateLiteral(Limit limit) {
/* 370 */     return this.states[limit.ordinal()].literal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(String propertyName) {
/* 380 */     for (Limit limit : Limit.values()) {
/* 381 */       if (limit.equalsAPIPropertyName(propertyName))
/*     */       {
/* 383 */         return limit.ordinal();
/*     */       }
/*     */     } 
/*     */     
/* 387 */     if (propertyName.equals("http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo")) {
/* 388 */       return 10000;
/*     */     }
/* 390 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNoLimit(int limit) {
/* 399 */     return (limit == 0);
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
/*     */   public boolean isOverLimit(Limit limit, String entityName, int size, XMLLimitAnalyzer limitAnalyzer) {
/* 412 */     return isOverLimit(limit.ordinal(), entityName, size, limitAnalyzer);
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
/*     */   public boolean isOverLimit(int index, String entityName, int size, XMLLimitAnalyzer limitAnalyzer) {
/* 426 */     if (this.values[index] == 0) {
/* 427 */       return false;
/*     */     }
/* 429 */     if (size > this.values[index]) {
/* 430 */       limitAnalyzer.addValue(index, entityName, size);
/* 431 */       return true;
/*     */     } 
/* 433 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOverLimit(Limit limit, XMLLimitAnalyzer limitAnalyzer) {
/* 444 */     return isOverLimit(limit.ordinal(), limitAnalyzer);
/*     */   }
/*     */   
/*     */   public boolean isOverLimit(int index, XMLLimitAnalyzer limitAnalyzer) {
/* 448 */     if (this.values[index] == 0) {
/* 449 */       return false;
/*     */     }
/*     */     
/* 452 */     if (index == Limit.ELEMENT_ATTRIBUTE_LIMIT.ordinal() || index == Limit.ENTITY_EXPANSION_LIMIT
/* 453 */       .ordinal() || index == Limit.TOTAL_ENTITY_SIZE_LIMIT
/* 454 */       .ordinal() || index == Limit.ENTITY_REPLACEMENT_LIMIT
/* 455 */       .ordinal() || index == Limit.MAX_ELEMENT_DEPTH_LIMIT
/* 456 */       .ordinal() || index == Limit.MAX_NAME_LIMIT
/* 457 */       .ordinal())
/*     */     {
/* 459 */       return (limitAnalyzer.getTotalValue(index) > this.values[index]);
/*     */     }
/* 461 */     return (limitAnalyzer.getValue(index) > this.values[index]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void debugPrint(XMLLimitAnalyzer limitAnalyzer) {
/* 466 */     if (this.printEntityCountInfo.equals("yes")) {
/* 467 */       limitAnalyzer.debugPrint(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSet(int index) {
/* 478 */     return this.isSet[index];
/*     */   }
/*     */   
/*     */   public boolean printEntityCountInfo() {
/* 482 */     return this.printEntityCountInfo.equals("yes");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readSystemProperties() {
/* 490 */     for (Limit limit : Limit.values()) {
/* 491 */       if (!getSystemProperty(limit, limit.systemProperty()))
/*     */       {
/* 493 */         for (NameMap nameMap : NameMap.values()) {
/* 494 */           String oldName = nameMap.getOldName(limit.systemProperty());
/* 495 */           if (oldName != null) {
/* 496 */             getSystemProperty(limit, oldName);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 505 */   private static final CopyOnWriteArrayList<String> printedWarnings = new CopyOnWriteArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void printWarning(String parserClassName, String propertyName, SAXException exception) {
/* 515 */     String key = parserClassName + ":" + propertyName;
/* 516 */     if (printedWarnings.addIfAbsent(key)) {
/* 517 */       System.err.println("Warning: " + parserClassName + ": " + exception.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean getSystemProperty(Limit limit, String sysPropertyName) {
/*     */     try {
/* 529 */       String value = SecuritySupport.getSystemProperty(sysPropertyName);
/* 530 */       if (value != null && !value.equals("")) {
/* 531 */         this.values[limit.ordinal()] = Integer.parseInt(value);
/* 532 */         this.states[limit.ordinal()] = State.SYSTEMPROPERTY;
/* 533 */         return true;
/*     */       } 
/*     */       
/* 536 */       value = SecuritySupport.readJAXPProperty(sysPropertyName);
/* 537 */       if (value != null && !value.equals("")) {
/* 538 */         this.values[limit.ordinal()] = Integer.parseInt(value);
/* 539 */         this.states[limit.ordinal()] = State.JAXPDOTPROPERTIES;
/* 540 */         return true;
/*     */       } 
/* 542 */     } catch (NumberFormatException e) {
/*     */       
/* 544 */       throw new NumberFormatException("Invalid setting for system property: " + limit.systemProperty());
/*     */     } 
/* 546 */     return false;
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
/*     */   public static XMLSecurityManager convert(Object value, XMLSecurityManager securityManager) {
/* 560 */     if (value == null) {
/* 561 */       if (securityManager == null) {
/* 562 */         securityManager = new XMLSecurityManager(true);
/*     */       }
/* 564 */       return securityManager;
/*     */     } 
/* 566 */     if (XMLSecurityManager.class.isAssignableFrom(value.getClass())) {
/* 567 */       return (XMLSecurityManager)value;
/*     */     }
/* 569 */     if (securityManager == null) {
/* 570 */       securityManager = new XMLSecurityManager(true);
/*     */     }
/* 572 */     if (SecurityManager.class.isAssignableFrom(value.getClass())) {
/* 573 */       SecurityManager origSM = (SecurityManager)value;
/* 574 */       securityManager.setLimit(Limit.MAX_OCCUR_NODE_LIMIT, State.APIPROPERTY, origSM.getMaxOccurNodeLimit());
/* 575 */       securityManager.setLimit(Limit.ENTITY_EXPANSION_LIMIT, State.APIPROPERTY, origSM.getEntityExpansionLimit());
/* 576 */       securityManager.setLimit(Limit.ELEMENT_ATTRIBUTE_LIMIT, State.APIPROPERTY, origSM.getElementAttrLimit());
/*     */     } 
/* 578 */     return securityManager;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/utils/XMLSecurityManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */