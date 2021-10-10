/*    */ package com.sun.jndi.ldap;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.util.Arrays;
/*    */ import javax.naming.ldap.Control;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SimpleClientId
/*    */   extends ClientId
/*    */ {
/*    */   private final String username;
/*    */   private final Object passwd;
/*    */   private final int myHash;
/*    */   
/*    */   SimpleClientId(int paramInt1, String paramString1, int paramInt2, String paramString2, Control[] paramArrayOfControl, OutputStream paramOutputStream, String paramString3, String paramString4, Object paramObject) {
/* 48 */     super(paramInt1, paramString1, paramInt2, paramString2, paramArrayOfControl, paramOutputStream, paramString3);
/*    */ 
/*    */     
/* 51 */     this.username = paramString4;
/* 52 */     int i = 0;
/* 53 */     if (paramObject == null) {
/* 54 */       this.passwd = null;
/* 55 */     } else if (paramObject instanceof byte[]) {
/* 56 */       this.passwd = ((byte[])paramObject).clone();
/* 57 */       i = Arrays.hashCode((byte[])paramObject);
/* 58 */     } else if (paramObject instanceof char[]) {
/* 59 */       this.passwd = ((char[])paramObject).clone();
/* 60 */       i = Arrays.hashCode((char[])paramObject);
/*    */     } else {
/* 62 */       this.passwd = paramObject;
/* 63 */       i = paramObject.hashCode();
/*    */     } 
/*    */     
/* 66 */     this
/* 67 */       .myHash = super.hashCode() ^ ((paramString4 != null) ? paramString4.hashCode() : 0) ^ i;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 72 */     if (paramObject == null || !(paramObject instanceof SimpleClientId)) {
/* 73 */       return false;
/*    */     }
/*    */     
/* 76 */     SimpleClientId simpleClientId = (SimpleClientId)paramObject;
/*    */     
/* 78 */     return (super.equals(paramObject) && (this.username == simpleClientId.username || (this.username != null && this.username
/*    */       
/* 80 */       .equals(simpleClientId.username))) && (this.passwd == simpleClientId.passwd || (this.passwd != null && simpleClientId.passwd != null && ((this.passwd instanceof String && this.passwd
/*    */ 
/*    */       
/* 83 */       .equals(simpleClientId.passwd)) || (this.passwd instanceof byte[] && simpleClientId.passwd instanceof byte[] && 
/*    */ 
/*    */       
/* 86 */       Arrays.equals((byte[])this.passwd, (byte[])simpleClientId.passwd)) || (this.passwd instanceof char[] && simpleClientId.passwd instanceof char[] && 
/*    */ 
/*    */       
/* 89 */       Arrays.equals((char[])this.passwd, (char[])simpleClientId.passwd))))));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 94 */     return this.myHash;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 98 */     return super.toString() + ":" + this.username;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/SimpleClientId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */