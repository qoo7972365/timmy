/*    */ package com.sun.security.auth.module;
/*    */ 
/*    */ import jdk.Exported;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Exported
/*    */ public class UnixSystem
/*    */ {
/*    */   protected String username;
/*    */   protected long uid;
/*    */   protected long gid;
/*    */   protected long[] groups;
/*    */   
/*    */   private native void getUnixInfo();
/*    */   
/*    */   public UnixSystem() {
/* 48 */     System.loadLibrary("jaas_unix");
/* 49 */     getUnixInfo();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUsername() {
/* 60 */     return this.username;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getUid() {
/* 71 */     return this.uid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getGid() {
/* 82 */     return this.gid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long[] getGroups() {
/* 93 */     return (this.groups == null) ? null : (long[])this.groups.clone();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/module/UnixSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */