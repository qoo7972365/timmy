/*     */ package sun.security.krb5.internal.rcache;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.KrbApErrException;
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
/*     */ 
/*     */ public class AuthList
/*     */ {
/*     */   private final LinkedList<AuthTimeWithHash> entries;
/*     */   private final int lifespan;
/*  59 */   private volatile int oldestTime = Integer.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthList(int paramInt) {
/*  65 */     this.lifespan = paramInt;
/*  66 */     this.entries = new LinkedList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void put(AuthTimeWithHash paramAuthTimeWithHash, KerberosTime paramKerberosTime) throws KrbApErrException {
/*  76 */     if (this.entries.isEmpty()) {
/*  77 */       this.entries.addFirst(paramAuthTimeWithHash);
/*  78 */       this.oldestTime = paramAuthTimeWithHash.ctime;
/*     */       return;
/*     */     } 
/*  81 */     AuthTimeWithHash authTimeWithHash = this.entries.getFirst();
/*  82 */     int i = authTimeWithHash.compareTo(paramAuthTimeWithHash);
/*  83 */     if (i < 0)
/*     */     
/*     */     { 
/*  86 */       this.entries.addFirst(paramAuthTimeWithHash); }
/*  87 */     else { if (i == 0) {
/*  88 */         throw new KrbApErrException(34);
/*     */       }
/*     */       
/*  91 */       ListIterator<AuthTimeWithHash> listIterator = this.entries.listIterator(1);
/*  92 */       boolean bool = false;
/*  93 */       while (listIterator.hasNext()) {
/*  94 */         authTimeWithHash = listIterator.next();
/*  95 */         i = authTimeWithHash.compareTo(paramAuthTimeWithHash);
/*  96 */         if (i < 0) {
/*     */           
/*  98 */           this.entries.add(this.entries.indexOf(authTimeWithHash), paramAuthTimeWithHash);
/*  99 */           bool = true; break;
/*     */         } 
/* 101 */         if (i == 0) {
/* 102 */           throw new KrbApErrException(34);
/*     */         }
/*     */       } 
/* 105 */       if (!bool)
/*     */       {
/* 107 */         this.entries.addLast(paramAuthTimeWithHash);
/*     */       } }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     long l = (paramKerberosTime.getSeconds() - this.lifespan);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (this.oldestTime > l - 5L) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 124 */     while (!this.entries.isEmpty()) {
/* 125 */       AuthTimeWithHash authTimeWithHash1 = this.entries.removeLast();
/* 126 */       if (authTimeWithHash1.ctime >= l) {
/* 127 */         this.entries.addLast(authTimeWithHash1);
/* 128 */         this.oldestTime = authTimeWithHash1.ctime;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 133 */     this.oldestTime = Integer.MIN_VALUE;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 137 */     return this.entries.isEmpty();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 141 */     StringBuilder stringBuilder = new StringBuilder();
/* 142 */     Iterator<AuthTimeWithHash> iterator = this.entries.descendingIterator();
/* 143 */     int i = this.entries.size();
/* 144 */     while (iterator.hasNext()) {
/* 145 */       AuthTimeWithHash authTimeWithHash = iterator.next();
/* 146 */       stringBuilder.append('#').append(i--).append(": ")
/* 147 */         .append(authTimeWithHash.toString()).append('\n');
/*     */     } 
/* 149 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/rcache/AuthList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */