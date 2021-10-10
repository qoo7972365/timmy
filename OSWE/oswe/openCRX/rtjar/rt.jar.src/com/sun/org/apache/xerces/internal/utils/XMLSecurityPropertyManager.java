/*     */ package com.sun.org.apache.xerces.internal.utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMLSecurityPropertyManager
/*     */ {
/*     */   private final String[] values;
/*     */   
/*     */   public enum State
/*     */   {
/*  44 */     DEFAULT, FSP, JAXPDOTPROPERTIES, SYSTEMPROPERTY, APIPROPERTY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Property
/*     */   {
/*  51 */     ACCESS_EXTERNAL_DTD("http://javax.xml.XMLConstants/property/accessExternalDTD", "all"),
/*     */     
/*  53 */     ACCESS_EXTERNAL_SCHEMA("http://javax.xml.XMLConstants/property/accessExternalSchema", "all");
/*     */     
/*     */     final String name;
/*     */     
/*     */     final String defaultValue;
/*     */     
/*     */     Property(String name, String value) {
/*  60 */       this.name = name;
/*  61 */       this.defaultValue = value;
/*     */     }
/*     */     
/*     */     public boolean equalsName(String propertyName) {
/*  65 */       return (propertyName == null) ? false : this.name.equals(propertyName);
/*     */     }
/*     */     
/*     */     String defaultValue() {
/*  69 */       return this.defaultValue;
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
/*  80 */   private State[] states = new State[] { State.DEFAULT, State.DEFAULT };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSecurityPropertyManager() {
/*  86 */     this.values = new String[(Property.values()).length];
/*  87 */     for (Property property : Property.values()) {
/*  88 */       this.values[property.ordinal()] = property.defaultValue();
/*     */     }
/*     */     
/*  91 */     readSystemProperties();
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
/*     */   public boolean setValue(String propertyName, State state, Object value) {
/* 104 */     int index = getIndex(propertyName);
/* 105 */     if (index > -1) {
/* 106 */       setValue(index, state, (String)value);
/* 107 */       return true;
/*     */     } 
/* 109 */     return false;
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
/*     */   public void setValue(Property property, State state, String value) {
/* 121 */     if (state.compareTo(this.states[property.ordinal()]) >= 0) {
/* 122 */       this.values[property.ordinal()] = value;
/* 123 */       this.states[property.ordinal()] = state;
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
/*     */   public void setValue(int index, State state, String value) {
/* 135 */     if (state.compareTo(this.states[index]) >= 0) {
/* 136 */       this.values[index] = value;
/* 137 */       this.states[index] = state;
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
/*     */   public String getValue(String propertyName) {
/* 149 */     int index = getIndex(propertyName);
/* 150 */     if (index > -1) {
/* 151 */       return getValueByIndex(index);
/*     */     }
/*     */     
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue(Property property) {
/* 164 */     return this.values[property.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueByIndex(int index) {
/* 173 */     return this.values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(String propertyName) {
/* 182 */     for (Property property : Property.values()) {
/* 183 */       if (property.equalsName(propertyName))
/*     */       {
/* 185 */         return property.ordinal();
/*     */       }
/*     */     } 
/* 188 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readSystemProperties() {
/* 195 */     getSystemProperty(Property.ACCESS_EXTERNAL_DTD, "javax.xml.accessExternalDTD");
/*     */     
/* 197 */     getSystemProperty(Property.ACCESS_EXTERNAL_SCHEMA, "javax.xml.accessExternalSchema");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getSystemProperty(Property property, String systemProperty) {
/*     */     try {
/* 209 */       String value = SecuritySupport.getSystemProperty(systemProperty);
/* 210 */       if (value != null) {
/* 211 */         this.values[property.ordinal()] = value;
/* 212 */         this.states[property.ordinal()] = State.SYSTEMPROPERTY;
/*     */         
/*     */         return;
/*     */       } 
/* 216 */       value = SecuritySupport.readJAXPProperty(systemProperty);
/* 217 */       if (value != null) {
/* 218 */         this.values[property.ordinal()] = value;
/* 219 */         this.states[property.ordinal()] = State.JAXPDOTPROPERTIES;
/*     */       } 
/* 221 */     } catch (NumberFormatException numberFormatException) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/utils/XMLSecurityPropertyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */