/*    */ package com.sun.xml.internal.ws.api.ha;
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
/*    */ public class HaInfo
/*    */ {
/*    */   private final String replicaInstance;
/*    */   private final String key;
/*    */   private final boolean failOver;
/*    */   
/*    */   public HaInfo(String key, String replicaInstance, boolean failOver) {
/* 68 */     this.key = key;
/* 69 */     this.replicaInstance = replicaInstance;
/* 70 */     this.failOver = failOver;
/*    */   }
/*    */   
/*    */   public String getReplicaInstance() {
/* 74 */     return this.replicaInstance;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 78 */     return this.key;
/*    */   }
/*    */   
/*    */   public boolean isFailOver() {
/* 82 */     return this.failOver;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/ha/HaInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */