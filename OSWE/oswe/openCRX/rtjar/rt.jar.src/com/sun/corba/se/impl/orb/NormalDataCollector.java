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
/*    */ public class NormalDataCollector
/*    */   extends DataCollectorBase
/*    */ {
/*    */   private String[] args;
/*    */   
/*    */   public NormalDataCollector(String[] paramArrayOfString, Properties paramProperties, String paramString1, String paramString2) {
/* 41 */     super(paramProperties, paramString1, paramString2);
/* 42 */     this.args = paramArrayOfString;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isApplet() {
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void collect() {
/* 52 */     checkPropertyDefaults();
/*    */     
/* 54 */     findPropertiesFromFile();
/* 55 */     findPropertiesFromSystem();
/* 56 */     findPropertiesFromProperties();
/* 57 */     findPropertiesFromArgs(this.args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/NormalDataCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */