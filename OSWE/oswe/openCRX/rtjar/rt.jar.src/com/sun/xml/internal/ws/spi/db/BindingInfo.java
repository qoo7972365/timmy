/*    */ package com.sun.xml.internal.ws.spi.db;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class BindingInfo
/*    */ {
/*    */   private String databindingMode;
/*    */   private String defaultNamespace;
/* 44 */   private Collection<Class> contentClasses = (Collection)new ArrayList<>();
/* 45 */   private Collection<TypeInfo> typeInfos = new ArrayList<>();
/* 46 */   private Map<Class, Class> subclassReplacements = (Map)new HashMap<>();
/* 47 */   private Map<String, Object> properties = new HashMap<>();
/*    */   
/*    */   protected ClassLoader classLoader;
/*    */   private SEIModel seiModel;
/*    */   private URL wsdlURL;
/*    */   
/*    */   public String getDatabindingMode() {
/* 54 */     return this.databindingMode;
/*    */   }
/*    */   public void setDatabindingMode(String databindingMode) {
/* 57 */     this.databindingMode = databindingMode;
/*    */   }
/*    */   
/*    */   public String getDefaultNamespace() {
/* 61 */     return this.defaultNamespace;
/*    */   }
/*    */   public void setDefaultNamespace(String defaultNamespace) {
/* 64 */     this.defaultNamespace = defaultNamespace;
/*    */   }
/*    */   
/*    */   public Collection<Class> contentClasses() {
/* 68 */     return this.contentClasses;
/*    */   }
/*    */   public Collection<TypeInfo> typeInfos() {
/* 71 */     return this.typeInfos;
/*    */   }
/*    */   public Map<Class, Class> subclassReplacements() {
/* 74 */     return this.subclassReplacements;
/*    */   }
/*    */   public Map<String, Object> properties() {
/* 77 */     return this.properties;
/*    */   }
/*    */   
/*    */   public SEIModel getSEIModel() {
/* 81 */     return this.seiModel;
/*    */   }
/*    */   public void setSEIModel(SEIModel seiModel) {
/* 84 */     this.seiModel = seiModel;
/*    */   }
/*    */   public ClassLoader getClassLoader() {
/* 87 */     return this.classLoader;
/*    */   }
/*    */   public void setClassLoader(ClassLoader classLoader) {
/* 90 */     this.classLoader = classLoader;
/*    */   }
/*    */   public URL getWsdlURL() {
/* 93 */     return this.wsdlURL;
/*    */   }
/*    */   public void setWsdlURL(URL wsdlURL) {
/* 96 */     this.wsdlURL = wsdlURL;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/BindingInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */