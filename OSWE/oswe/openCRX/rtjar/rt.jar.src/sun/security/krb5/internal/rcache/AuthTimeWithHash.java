/*     */ package sun.security.krb5.internal.rcache;
/*     */ 
/*     */ import java.util.Objects;
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
/*     */ public class AuthTimeWithHash
/*     */   extends AuthTime
/*     */   implements Comparable<AuthTimeWithHash>
/*     */ {
/*     */   final String hash;
/*     */   
/*     */   public AuthTimeWithHash(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3) {
/*  44 */     super(paramString1, paramString2, paramInt1, paramInt2);
/*  45 */     this.hash = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  55 */     if (this == paramObject) return true; 
/*  56 */     if (!(paramObject instanceof AuthTimeWithHash)) return false; 
/*  57 */     AuthTimeWithHash authTimeWithHash = (AuthTimeWithHash)paramObject;
/*  58 */     return (Objects.equals(this.hash, authTimeWithHash.hash) && 
/*  59 */       Objects.equals(this.client, authTimeWithHash.client) && 
/*  60 */       Objects.equals(this.server, authTimeWithHash.server) && this.ctime == authTimeWithHash.ctime && this.cusec == authTimeWithHash.cusec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  70 */     return Objects.hash(new Object[] { this.hash });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  75 */     return String.format("%d/%06d/%s/%s", new Object[] { Integer.valueOf(this.ctime), Integer.valueOf(this.cusec), this.hash, this.client });
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(AuthTimeWithHash paramAuthTimeWithHash) {
/*  80 */     int i = 0;
/*  81 */     if (this.ctime != paramAuthTimeWithHash.ctime) {
/*  82 */       i = Integer.compare(this.ctime, paramAuthTimeWithHash.ctime);
/*  83 */     } else if (this.cusec != paramAuthTimeWithHash.cusec) {
/*  84 */       i = Integer.compare(this.cusec, paramAuthTimeWithHash.cusec);
/*     */     } else {
/*  86 */       i = this.hash.compareTo(paramAuthTimeWithHash.hash);
/*     */     } 
/*  88 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSameIgnoresHash(AuthTime paramAuthTime) {
/*  97 */     return (this.client.equals(paramAuthTime.client) && this.server
/*  98 */       .equals(paramAuthTime.server) && this.ctime == paramAuthTime.ctime && this.cusec == paramAuthTime.cusec);
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
/*     */   public byte[] encode(boolean paramBoolean) {
/*     */     String str1;
/*     */     String str2;
/* 113 */     if (paramBoolean) {
/* 114 */       str1 = "";
/* 115 */       str2 = String.format("HASH:%s %d:%s %d:%s", new Object[] { this.hash, 
/* 116 */             Integer.valueOf(this.client.length()), this.client, 
/* 117 */             Integer.valueOf(this.server.length()), this.server });
/*     */     } else {
/* 119 */       str1 = this.client;
/* 120 */       str2 = this.server;
/*     */     } 
/* 122 */     return encode0(str1, str2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/rcache/AuthTimeWithHash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */