/*     */ package jdk.xml.internal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JdkXmlFeatures
/*     */ {
/*     */   public static final String ORACLE_JAXP_PROPERTY_PREFIX = "http://www.oracle.com/xml/jaxp/properties/";
/*     */   public static final String XML_FEATURE_MANAGER = "http://www.oracle.com/xml/jaxp/properties/XmlFeatureManager";
/*     */   public static final String ORACLE_FEATURE_SERVICE_MECHANISM = "http://www.oracle.com/feature/use-service-mechanism";
/*     */   public static final String ORACLE_ENABLE_EXTENSION_FUNCTION = "http://www.oracle.com/xml/jaxp/properties/enableExtensionFunctions";
/*     */   public static final String SP_ENABLE_EXTENSION_FUNCTION = "javax.xml.enableExtensionFunctions";
/*     */   public static final String SP_ENABLE_EXTENSION_FUNCTION_SPEC = "jdk.xml.enableExtensionFunctions";
/*     */   private final boolean[] featureValues;
/*     */   private final State[] states;
/*     */   boolean secureProcessing;
/*     */   
/*     */   public enum XmlFeature
/*     */   {
/*  62 */     ENABLE_EXTENSION_FUNCTION("http://www.oracle.com/xml/jaxp/properties/enableExtensionFunctions", "jdk.xml.enableExtensionFunctions", "http://www.oracle.com/xml/jaxp/properties/enableExtensionFunctions", "javax.xml.enableExtensionFunctions", true, false, true, true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     JDK_OVERRIDE_PARSER("jdk.xml.overrideDefaultParser", "jdk.xml.overrideDefaultParser", "http://www.oracle.com/feature/use-service-mechanism", "http://www.oracle.com/feature/use-service-mechanism", false, false, true, false);
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     private final String nameSP;
/*     */ 
/*     */     
/*     */     private final String nameOld;
/*     */ 
/*     */     
/*     */     private final String nameOldSP;
/*     */ 
/*     */     
/*     */     private final boolean valueDefault;
/*     */ 
/*     */     
/*     */     private final boolean valueEnforced;
/*     */ 
/*     */     
/*     */     private final boolean hasSystem;
/*     */     
/*     */     private final boolean enforced;
/*     */ 
/*     */     
/*     */     XmlFeature(String name, String nameSP, String nameOld, String nameOldSP, boolean value, boolean valueEnforced, boolean hasSystem, boolean enforced) {
/*  97 */       this.name = name;
/*  98 */       this.nameSP = nameSP;
/*  99 */       this.nameOld = nameOld;
/* 100 */       this.nameOldSP = nameOldSP;
/* 101 */       this.valueDefault = value;
/* 102 */       this.valueEnforced = valueEnforced;
/* 103 */       this.hasSystem = hasSystem;
/* 104 */       this.enforced = enforced;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean equalsPropertyName(String propertyName) {
/* 114 */       return (this.name.equals(propertyName) || (this.nameOld != null && this.nameOld
/* 115 */         .equals(propertyName)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String apiProperty() {
/* 124 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String systemProperty() {
/* 133 */       return this.nameSP;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String systemPropertyOld() {
/* 142 */       return this.nameOldSP;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean defaultValue() {
/* 150 */       return this.valueDefault;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean enforcedValue() {
/* 158 */       return this.valueEnforced;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean hasSystemProperty() {
/* 166 */       return this.hasSystem;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean enforced() {
/* 174 */       return this.enforced;
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
/*     */   public enum State
/*     */   {
/* 187 */     DEFAULT("default"), FSP("FEATURE_SECURE_PROCESSING"),
/* 188 */     JAXPDOTPROPERTIES("jaxp.properties"), SYSTEMPROPERTY("system property"),
/* 189 */     APIPROPERTY("property");
/*     */     final String literal;
/*     */     
/*     */     State(String literal) {
/* 193 */       this.literal = literal;
/*     */     }
/*     */     
/*     */     String literal() {
/* 197 */       return this.literal;
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
/*     */   public JdkXmlFeatures(boolean secureProcessing) {
/* 221 */     this.featureValues = new boolean[(XmlFeature.values()).length];
/* 222 */     this.states = new State[(XmlFeature.values()).length];
/* 223 */     this.secureProcessing = secureProcessing;
/* 224 */     for (XmlFeature f : XmlFeature.values()) {
/* 225 */       if (secureProcessing && f.enforced()) {
/* 226 */         this.featureValues[f.ordinal()] = f.enforcedValue();
/* 227 */         this.states[f.ordinal()] = State.FSP;
/*     */       } else {
/* 229 */         this.featureValues[f.ordinal()] = f.defaultValue();
/* 230 */         this.states[f.ordinal()] = State.DEFAULT;
/*     */       } 
/*     */     } 
/*     */     
/* 234 */     readSystemProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 243 */     readSystemProperties();
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
/*     */   public boolean setFeature(String propertyName, State state, Object value) {
/* 255 */     int index = getIndex(propertyName);
/* 256 */     if (index > -1) {
/* 257 */       setFeature(index, state, value);
/* 258 */       return true;
/*     */     } 
/* 260 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(XmlFeature feature, State state, boolean value) {
/* 271 */     setFeature(feature.ordinal(), state, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFeature(XmlFeature feature) {
/* 281 */     return this.featureValues[feature.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFeature(int index) {
/* 290 */     return this.featureValues[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(int index, State state, Object value) {
/*     */     boolean temp;
/* 302 */     if (Boolean.class.isAssignableFrom(value.getClass())) {
/* 303 */       temp = ((Boolean)value).booleanValue();
/*     */     } else {
/* 305 */       temp = Boolean.parseBoolean((String)value);
/*     */     } 
/* 307 */     setFeature(index, state, temp);
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
/*     */   public void setFeature(int index, State state, boolean value) {
/* 319 */     if (state.compareTo(this.states[index]) >= 0) {
/* 320 */       this.featureValues[index] = value;
/* 321 */       this.states[index] = state;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(String propertyName) {
/* 332 */     for (XmlFeature feature : XmlFeature.values()) {
/* 333 */       if (feature.equalsPropertyName(propertyName))
/*     */       {
/* 335 */         return feature.ordinal();
/*     */       }
/*     */     } 
/* 338 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readSystemProperties() {
/* 345 */     for (XmlFeature feature : XmlFeature.values()) {
/* 346 */       if (!getSystemProperty(feature, feature.systemProperty())) {
/*     */         
/* 348 */         String oldName = feature.systemPropertyOld();
/* 349 */         if (oldName != null) {
/* 350 */           getSystemProperty(feature, oldName);
/*     */         }
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
/*     */   private boolean getSystemProperty(XmlFeature feature, String sysPropertyName) {
/*     */     try {
/* 365 */       String value = SecuritySupport.getSystemProperty(sysPropertyName);
/* 366 */       if (value != null && !value.equals("")) {
/* 367 */         setFeature(feature, State.SYSTEMPROPERTY, Boolean.parseBoolean(value));
/* 368 */         return true;
/*     */       } 
/*     */       
/* 371 */       value = SecuritySupport.readJAXPProperty(sysPropertyName);
/* 372 */       if (value != null && !value.equals("")) {
/* 373 */         setFeature(feature, State.JAXPDOTPROPERTIES, Boolean.parseBoolean(value));
/* 374 */         return true;
/*     */       } 
/* 376 */     } catch (NumberFormatException e) {
/*     */       
/* 378 */       throw new NumberFormatException("Invalid setting for system property: " + feature.systemProperty());
/*     */     } 
/* 380 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/xml/internal/JdkXmlFeatures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */