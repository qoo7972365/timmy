/*     */ package java.rmi.dgc;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.rmi.server.UID;
/*     */ import java.security.SecureRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class VMID
/*     */   implements Serializable
/*     */ {
/*     */   private static final byte[] randomBytes;
/*     */   
/*     */   static {
/*  58 */     SecureRandom secureRandom = new SecureRandom();
/*  59 */     byte[] arrayOfByte = new byte[8];
/*  60 */     secureRandom.nextBytes(arrayOfByte);
/*  61 */     randomBytes = arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private byte[] addr = randomBytes;
/*  74 */   private UID uid = new UID();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -538642295484486218L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean isUnique() {
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  92 */     return this.uid.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 100 */     if (paramObject instanceof VMID) {
/* 101 */       VMID vMID = (VMID)paramObject;
/* 102 */       if (!this.uid.equals(vMID.uid))
/* 103 */         return false; 
/* 104 */       if ((((this.addr == null) ? 1 : 0) ^ ((vMID.addr == null) ? 1 : 0)) != 0)
/* 105 */         return false; 
/* 106 */       if (this.addr != null) {
/* 107 */         if (this.addr.length != vMID.addr.length)
/* 108 */           return false; 
/* 109 */         for (byte b = 0; b < this.addr.length; b++) {
/* 110 */           if (this.addr[b] != vMID.addr[b])
/* 111 */             return false; 
/*     */         } 
/* 113 */       }  return true;
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     StringBuffer stringBuffer = new StringBuffer();
/* 124 */     if (this.addr != null)
/* 125 */       for (byte b = 0; b < this.addr.length; b++) {
/* 126 */         int i = this.addr[b] & 0xFF;
/* 127 */         stringBuffer.append(((i < 16) ? "0" : "") + 
/* 128 */             Integer.toString(i, 16));
/*     */       }  
/* 130 */     stringBuffer.append(':');
/* 131 */     stringBuffer.append(this.uid.toString());
/* 132 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/dgc/VMID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */