/*    */ package com.oracle.webservices.internal.api.databinding;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.ServiceSharedFeatureMarker;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.xml.ws.WebServiceFeature;
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
/*    */ public class DatabindingModeFeature
/*    */   extends WebServiceFeature
/*    */   implements ServiceSharedFeatureMarker
/*    */ {
/*    */   public static final String ID = "http://jax-ws.java.net/features/databinding";
/*    */   public static final String GLASSFISH_JAXB = "glassfish.jaxb";
/*    */   private String mode;
/*    */   private Map<String, Object> properties;
/*    */   
/*    */   public DatabindingModeFeature(String mode) {
/* 52 */     this.mode = mode;
/* 53 */     this.properties = new HashMap<>();
/*    */   }
/*    */   
/*    */   public String getMode() {
/* 57 */     return this.mode;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 61 */     return "http://jax-ws.java.net/features/databinding";
/*    */   }
/*    */   
/*    */   public Map<String, Object> getProperties() {
/* 65 */     return this.properties;
/*    */   }
/*    */   public static Builder builder() {
/* 68 */     return new Builder(new DatabindingModeFeature(null));
/*    */   }
/*    */   
/*    */   public static final class Builder {
/* 72 */     Builder(DatabindingModeFeature x) { this.o = x; } private final DatabindingModeFeature o; public DatabindingModeFeature build() {
/* 73 */       return this.o;
/*    */     } public Builder value(String x) {
/* 75 */       this.o.mode = x; return this;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/api/databinding/DatabindingModeFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */