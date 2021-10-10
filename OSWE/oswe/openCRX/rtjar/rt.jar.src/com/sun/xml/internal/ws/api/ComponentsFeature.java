/*    */ package com.sun.xml.internal.ws.api;
/*    */ 
/*    */ import java.util.List;
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
/*    */ public class ComponentsFeature
/*    */   extends WebServiceFeature
/*    */   implements ServiceSharedFeatureMarker
/*    */ {
/*    */   private final List<ComponentFeature> componentFeatures;
/*    */   
/*    */   public ComponentsFeature(List<ComponentFeature> componentFeatures) {
/* 58 */     this.enabled = true;
/* 59 */     this.componentFeatures = componentFeatures;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getID() {
/* 64 */     return ComponentsFeature.class.getName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ComponentFeature> getComponentFeatures() {
/* 72 */     return this.componentFeatures;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/ComponentsFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */