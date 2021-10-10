/*    */ package com.sun.corba.se.impl.orb;
/*    */ 
/*    */ import java.util.Properties;
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
/*    */ public class PropertyOnlyDataCollector
/*    */   extends DataCollectorBase
/*    */ {
/*    */   public PropertyOnlyDataCollector(Properties paramProperties, String paramString1, String paramString2) {
/* 41 */     super(paramProperties, paramString1, paramString2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isApplet() {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void collect() {
/* 51 */     checkPropertyDefaults();
/*    */     
/* 53 */     findPropertiesFromProperties();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/PropertyOnlyDataCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */