/*    */ package com.sun.xml.internal.ws.api;
/*    */ 
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
/*    */ public class ComponentFeature
/*    */   extends WebServiceFeature
/*    */   implements ServiceSharedFeatureMarker
/*    */ {
/*    */   private final Component component;
/*    */   private final Target target;
/*    */   
/*    */   public enum Target
/*    */   {
/* 53 */     CONTAINER, ENDPOINT, SERVICE, STUB;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ComponentFeature(Component component) {
/* 64 */     this(component, Target.CONTAINER);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ComponentFeature(Component component, Target target) {
/* 73 */     this.enabled = true;
/* 74 */     this.component = component;
/* 75 */     this.target = target;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getID() {
/* 80 */     return ComponentFeature.class.getName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Component getComponent() {
/* 88 */     return this.component;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Target getTarget() {
/* 96 */     return this.target;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/ComponentFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */