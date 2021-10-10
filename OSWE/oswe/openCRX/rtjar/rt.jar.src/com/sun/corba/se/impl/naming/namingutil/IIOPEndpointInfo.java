/*    */ package com.sun.corba.se.impl.naming.namingutil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IIOPEndpointInfo
/*    */ {
/* 48 */   private int major = 1;
/* 49 */   private int minor = 0;
/*    */   
/* 51 */   private String host = "localhost";
/*    */   
/* 53 */   private int port = 2089;
/*    */ 
/*    */   
/*    */   public void setHost(String paramString) {
/* 57 */     this.host = paramString;
/*    */   }
/*    */   
/*    */   public String getHost() {
/* 61 */     return this.host;
/*    */   }
/*    */   
/*    */   public void setPort(int paramInt) {
/* 65 */     this.port = paramInt;
/*    */   }
/*    */   
/*    */   public int getPort() {
/* 69 */     return this.port;
/*    */   }
/*    */   
/*    */   public void setVersion(int paramInt1, int paramInt2) {
/* 73 */     this.major = paramInt1;
/* 74 */     this.minor = paramInt2;
/*    */   }
/*    */   
/*    */   public int getMajor() {
/* 78 */     return this.major;
/*    */   }
/*    */   
/*    */   public int getMinor() {
/* 82 */     return this.minor;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void dump() {
/* 88 */     System.out.println(" Major -> " + this.major + " Minor -> " + this.minor);
/* 89 */     System.out.println("host -> " + this.host);
/* 90 */     System.out.println("port -> " + this.port);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/namingutil/IIOPEndpointInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */