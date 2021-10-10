/*    */ package com.sun.org.glassfish.external.probe.provider;
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
/*    */ public enum PluginPoint
/*    */ {
/* 36 */   SERVER("server", "server"),
/* 37 */   APPLICATIONS("applications", "server/applications");
/*    */   
/*    */   String name;
/*    */   String path;
/*    */   
/*    */   PluginPoint(String lname, String lpath) {
/* 43 */     this.name = lname;
/* 44 */     this.path = lpath;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 48 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 52 */     return this.path;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/external/probe/provider/PluginPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */