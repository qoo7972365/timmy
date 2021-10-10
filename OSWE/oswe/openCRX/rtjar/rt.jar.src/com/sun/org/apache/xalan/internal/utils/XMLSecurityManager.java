/*     */ package com.sun.org.apache.xalan.internal.utils;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMLSecurityManager
/*     */ {
/*     */   private final int[] values;
/*     */   private State[] states;
/*     */   private boolean[] isSet;
/*     */   
/*     */   public enum State
/*     */   {
/*  51 */     DEFAULT("default"), FSP("FEATURE_SECURE_PROCESSING"),
/*  52 */     JAXPDOTPROPERTIES("jaxp.properties"), SYSTEMPROPERTY("system property"),
/*  53 */     APIPROPERTY("property");
/*     */     final String literal;
/*     */     
/*     */     State(String literal) {
/*  57 */       this.literal = literal;
/*     */     }
/*     */     
/*     */     String literal() {
/*  61 */       return this.literal;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Limit
/*     */   {
/*  70 */     ENTITY_EXPANSION_LIMIT("EntityExpansionLimit", "http://www.oracle.com/xml/jaxp/properties/entityExpansionLimit", "jdk.xml.entityExpansionLimit", 0, 64000),
/*     */     
/*  72 */     MAX_OCCUR_NODE_LIMIT("MaxOccurLimit", "http://www.oracle.com/xml/jaxp/properties/maxOccurLimit", "jdk.xml.maxOccurLimit", 0, 5000),
/*     */     
/*  74 */     ELEMENT_ATTRIBUTE_LIMIT("ElementAttributeLimit", "http://www.oracle.com/xml/jaxp/properties/elementAttributeLimit", "jdk.xml.elementAttributeLimit", 0, 10000),
/*     */     
/*  76 */     TOTAL_ENTITY_SIZE_LIMIT("TotalEntitySizeLimit", "http://www.oracle.com/xml/jaxp/properties/totalEntitySizeLimit", "jdk.xml.totalEntitySizeLimit", 0, 50000000),
/*     */     
/*  78 */     GENERAL_ENTITY_SIZE_LIMIT("MaxEntitySizeLimit", "http://www.oracle.com/xml/jaxp/properties/maxGeneralEntitySizeLimit", "jdk.xml.maxGeneralEntitySizeLimit", 0, 0),
/*     */     
/*  80 */     PARAMETER_ENTITY_SIZE_LIMIT("MaxEntitySizeLimit", "http://www.oracle.com/xml/jaxp/properties/maxParameterEntitySizeLimit", "jdk.xml.maxParameterEntitySizeLimit", 0, 1000000),
/*     */     
/*  82 */     MAX_ELEMENT_DEPTH_LIMIT("MaxElementDepthLimit", "http://www.oracle.com/xml/jaxp/properties/maxElementDepth", "jdk.xml.maxElementDepth", 0, 0),
/*     */     
/*  84 */     MAX_NAME_LIMIT("MaxXMLNameLimit", "http://www.oracle.com/xml/jaxp/properties/maxXMLNameLimit", "jdk.xml.maxXMLNameLimit", 1000, 1000),
/*     */     
/*  86 */     ENTITY_REPLACEMENT_LIMIT("EntityReplacementLimit", "http://www.oracle.com/xml/jaxp/properties/entityReplacementLimit", "jdk.xml.entityReplacementLimit", 0, 3000000);
/*     */     
/*     */     final String key;
/*     */     
/*     */     final String apiProperty;
/*     */     final String systemProperty;
/*     */     final int defaultValue;
/*     */     final int secureValue;
/*     */     
/*     */     Limit(String key, String apiProperty, String systemProperty, int value, int secureValue) {
/*  96 */       this.key = key;
/*  97 */       this.apiProperty = apiProperty;
/*  98 */       this.systemProperty = systemProperty;
/*  99 */       this.defaultValue = value;
/* 100 */       this.secureValue = secureValue;
/*     */     }
/*     */     
/*     */     public boolean equalsAPIPropertyName(String propertyName) {
/* 104 */       return (propertyName == null) ? false : this.apiProperty.equals(propertyName);
/*     */     }
/*     */     
/*     */     public boolean equalsSystemPropertyName(String propertyName) {
/* 108 */       return (propertyName == null) ? false : this.systemProperty.equals(propertyName);
/*     */     }
/*     */     
/*     */     public String key() {
/* 112 */       return this.key;
/*     */     }
/*     */     
/*     */     public String apiProperty() {
/* 116 */       return this.apiProperty;
/*     */     }
/*     */     
/*     */     String systemProperty() {
/* 120 */       return this.systemProperty;
/*     */     }
/*     */     
/*     */     public int defaultValue() {
/* 124 */       return this.defaultValue;
/*     */     }
/*     */     
/*     */     int secureValue() {
/* 128 */       return this.secureValue;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum NameMap
/*     */   {
/* 137 */     ENTITY_EXPANSION_LIMIT("jdk.xml.entityExpansionLimit", "entityExpansionLimit"),
/*     */     
/* 139 */     MAX_OCCUR_NODE_LIMIT("jdk.xml.maxOccurLimit", "maxOccurLimit"),
/*     */     
/* 141 */     ELEMENT_ATTRIBUTE_LIMIT("jdk.xml.elementAttributeLimit", "elementAttributeLimit");
/*     */     
/*     */     final String newName;
/*     */     final String oldName;
/*     */     
/*     */     NameMap(String newName, String oldName) {
/* 147 */       this.newName = newName;
/* 148 */       this.oldName = oldName;
/*     */     }
/*     */     
/*     */     String getOldName(String newName) {
/* 152 */       if (newName.equals(this.newName)) {
/* 153 */         return this.oldName;
/*     */       }
/* 155 */       return null;
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
/* 175 */   private final int indexEntityCountInfo = 10000;
/* 176 */   private String printEntityCountInfo = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityManager() {
/* 183 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityManager(boolean secureProcessing) {
/* 192 */     this.values = new int[(Limit.values()).length];
/* 193 */     this.states = new State[(Limit.values()).length];
/* 194 */     this.isSet = new boolean[(Limit.values()).length];
/* 195 */     for (Limit limit : Limit.values()) {
/* 196 */       if (secureProcessing) {
/* 197 */         this.values[limit.ordinal()] = limit.secureValue();
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
/* 212 */     for (Limit limit : Limit.values()) {
/* 213 */       if (secure) {
/* 214 */         setLimit(limit.ordinal(), State.FSP, limit.secureValue());
/*     */       } else {
/* 216 */         setLimit(limit.ordinal(), State.FSP, limit.defaultValue());
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
/*     */   public boolean setLimit(String propertyName, State state, Object value) {
/* 230 */     int index = getIndex(propertyName);
/* 231 */     if (index > -1) {
/* 232 */       setLimit(index, state, value);
/* 233 */       return true;
/*     */     } 
/* 235 */     return false;
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
/* 246 */     setLimit(limit.ordinal(), state, value);
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
/* 257 */     if (index == 10000) {
/*     */       
/* 259 */       this.printEntityCountInfo = (String)value;
/*     */     } else {
/* 261 */       int temp = 0;
/*     */       try {
/* 263 */         temp = Integer.parseInt((String)value);
/* 264 */         if (temp < 0) {
/* 265 */           temp = 0;
/*     */         }
/* 267 */       } catch (NumberFormatException numberFormatException) {}
/* 268 */       setLimit(index, state, temp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLimit(int index, State state, int value) {
/* 279 */     if (index == 10000) {
/*     */       
/* 281 */       this.printEntityCountInfo = "yes";
/*     */     
/*     */     }
/* 284 */     else if (state.compareTo(this.states[index]) >= 0) {
/* 285 */       this.values[index] = value;
/* 286 */       this.states[index] = state;
/* 287 */       this.isSet[index] = true;
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
/*     */   public String getLimitAsString(String propertyName) {
/* 301 */     int index = getIndex(propertyName);
/* 302 */     if (index > -1) {
/* 303 */       return getLimitValueByIndex(index);
/*     */     }
/*     */     
/* 306 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLimitValueAsString(Limit limit) {
/* 316 */     return Integer.toString(this.values[limit.ordinal()]);
/*     */   }
/*     */ 
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
/*     */   public int getLimitByIndex(int index) {
/* 336 */     return this.values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLimitValueByIndex(int index) {
/* 345 */     if (index == 10000) {
/* 346 */       return this.printEntityCountInfo;
/*     */     }
/*     */     
/* 349 */     return Integer.toString(this.values[index]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State getState(Limit limit) {
/* 358 */     return this.states[limit.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStateLiteral(Limit limit) {
/* 368 */     return this.states[limit.ordinal()].literal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(String propertyName) {
/* 378 */     for (Limit limit : Limit.values()) {
/* 379 */       if (limit.equalsAPIPropertyName(propertyName))
/*     */       {
/* 381 */         return limit.ordinal();
/*     */       }
/*     */     } 
/*     */     
/* 385 */     if (propertyName.equals("http://www.oracle.com/xml/jaxp/properties/getEntityCountInfo")) {
/* 386 */       return 10000;
/*     */     }
/* 388 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSet(int index) {
/* 397 */     return this.isSet[index];
/*     */   }
/*     */   
/*     */   public boolean printEntityCountInfo() {
/* 401 */     return this.printEntityCountInfo.equals("yes");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readSystemProperties() {
/* 408 */     for (Limit limit : Limit.values()) {
/* 409 */       if (!getSystemProperty(limit, limit.systemProperty()))
/*     */       {
/* 411 */         for (NameMap nameMap : NameMap.values()) {
/* 412 */           String oldName = nameMap.getOldName(limit.systemProperty());
/* 413 */           if (oldName != null) {
/* 414 */             getSystemProperty(limit, oldName);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 423 */   private static final CopyOnWriteArrayList<String> printedWarnings = new CopyOnWriteArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void printWarning(String parserClassName, String propertyName, SAXException exception) {
/* 433 */     String key = parserClassName + ":" + propertyName;
/* 434 */     if (printedWarnings.addIfAbsent(key)) {
/* 435 */       System.err.println("Warning: " + parserClassName + ": " + exception.getMessage());
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
/* 447 */       String value = SecuritySupport.getSystemProperty(sysPropertyName);
/* 448 */       if (value != null && !value.equals("")) {
/* 449 */         this.values[limit.ordinal()] = Integer.parseInt(value);
/* 450 */         this.states[limit.ordinal()] = State.SYSTEMPROPERTY;
/* 451 */         return true;
/*     */       } 
/*     */       
/* 454 */       value = SecuritySupport.readJAXPProperty(sysPropertyName);
/* 455 */       if (value != null && !value.equals("")) {
/* 456 */         this.values[limit.ordinal()] = Integer.parseInt(value);
/* 457 */         this.states[limit.ordinal()] = State.JAXPDOTPROPERTIES;
/* 458 */         return true;
/*     */       } 
/* 460 */     } catch (NumberFormatException e) {
/*     */       
/* 462 */       throw new NumberFormatException("Invalid setting for system property: " + limit.systemProperty());
/*     */     } 
/* 464 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/utils/XMLSecurityManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */