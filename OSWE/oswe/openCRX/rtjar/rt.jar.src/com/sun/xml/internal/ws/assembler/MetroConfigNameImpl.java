/*    */ package com.sun.xml.internal.ws.assembler;
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
/*    */ public class MetroConfigNameImpl
/*    */   implements MetroConfigName
/*    */ {
/*    */   private final String defaultFileName;
/*    */   private final String appFileName;
/*    */   
/*    */   public MetroConfigNameImpl(String defaultFileName, String appFileName) {
/* 39 */     this.defaultFileName = defaultFileName;
/* 40 */     this.appFileName = appFileName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDefaultFileName() {
/* 45 */     return this.defaultFileName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAppFileName() {
/* 50 */     return this.appFileName;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/MetroConfigNameImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */