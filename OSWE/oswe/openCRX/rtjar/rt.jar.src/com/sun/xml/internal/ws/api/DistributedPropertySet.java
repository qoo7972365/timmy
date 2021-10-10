/*    */ package com.sun.xml.internal.ws.api;
/*    */ 
/*    */ import com.oracle.webservices.internal.api.message.BaseDistributedPropertySet;
/*    */ import com.oracle.webservices.internal.api.message.PropertySet;
/*    */ import com.sun.istack.internal.NotNull;
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
/*    */ public abstract class DistributedPropertySet
/*    */   extends BaseDistributedPropertySet
/*    */ {
/*    */   public void addSatellite(@NotNull PropertySet satellite) {
/* 42 */     addSatellite((PropertySet)satellite);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSatellite(@NotNull Class keyClass, @NotNull PropertySet satellite) {
/* 49 */     addSatellite(keyClass, (PropertySet)satellite);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void copySatelliteInto(@NotNull DistributedPropertySet r) {
/* 56 */     copySatelliteInto((com.oracle.webservices.internal.api.message.DistributedPropertySet)r);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeSatellite(PropertySet satellite) {
/* 63 */     removeSatellite((PropertySet)satellite);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/DistributedPropertySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */