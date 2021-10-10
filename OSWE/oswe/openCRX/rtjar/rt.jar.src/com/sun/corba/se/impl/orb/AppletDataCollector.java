/*    */ package com.sun.corba.se.impl.orb;
/*    */ 
/*    */ import java.applet.Applet;
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
/*    */ public class AppletDataCollector
/*    */   extends DataCollectorBase
/*    */ {
/*    */   private Applet applet;
/*    */   
/*    */   AppletDataCollector(Applet paramApplet, Properties paramProperties, String paramString1, String paramString2) {
/* 37 */     super(paramProperties, paramString1, paramString2);
/* 38 */     this.applet = paramApplet;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isApplet() {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void collect() {
/* 48 */     checkPropertyDefaults();
/*    */     
/* 50 */     findPropertiesFromFile();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 55 */     findPropertiesFromProperties();
/* 56 */     findPropertiesFromApplet(this.applet);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/AppletDataCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */