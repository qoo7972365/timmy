/*    */ package com.sun.org.apache.xalan.internal.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class XMLSecurityPropertyManager
/*    */   extends FeaturePropertyBase
/*    */ {
/*    */   public enum Property
/*    */   {
/* 42 */     ACCESS_EXTERNAL_DTD("http://javax.xml.XMLConstants/property/accessExternalDTD", "all"),
/*    */     
/* 44 */     ACCESS_EXTERNAL_STYLESHEET("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "all");
/*    */     
/*    */     final String name;
/*    */     
/*    */     final String defaultValue;
/*    */     
/*    */     Property(String name, String value) {
/* 51 */       this.name = name;
/* 52 */       this.defaultValue = value;
/*    */     }
/*    */     
/*    */     public boolean equalsName(String propertyName) {
/* 56 */       return (propertyName == null) ? false : this.name.equals(propertyName);
/*    */     }
/*    */     
/*    */     String defaultValue() {
/* 60 */       return this.defaultValue;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XMLSecurityPropertyManager() {
/* 69 */     this.values = new String[(Property.values()).length];
/* 70 */     for (Property property : Property.values()) {
/* 71 */       this.values[property.ordinal()] = property.defaultValue();
/*    */     }
/*    */     
/* 74 */     readSystemProperties();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIndex(String propertyName) {
/* 83 */     for (Property property : Property.values()) {
/* 84 */       if (property.equalsName(propertyName))
/*    */       {
/* 86 */         return property.ordinal();
/*    */       }
/*    */     } 
/* 89 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void readSystemProperties() {
/* 96 */     getSystemProperty(Property.ACCESS_EXTERNAL_DTD, "javax.xml.accessExternalDTD");
/*    */     
/* 98 */     getSystemProperty(Property.ACCESS_EXTERNAL_STYLESHEET, "javax.xml.accessExternalStylesheet");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/utils/XMLSecurityPropertyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */