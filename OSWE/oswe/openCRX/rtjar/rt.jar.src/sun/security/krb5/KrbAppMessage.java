/*     */ package sun.security.krb5;
/*     */ 
/*     */ import sun.security.krb5.internal.HostAddress;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.SeqNumber;
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
/*     */ abstract class KrbAppMessage
/*     */ {
/*  37 */   private static boolean DEBUG = Krb5.DEBUG;
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
/*     */   void check(KerberosTime paramKerberosTime, Integer paramInteger1, Integer paramInteger2, HostAddress paramHostAddress1, HostAddress paramHostAddress2, SeqNumber paramSeqNumber, HostAddress paramHostAddress3, HostAddress paramHostAddress4, boolean paramBoolean1, boolean paramBoolean2, PrincipalName paramPrincipalName) throws KrbApErrException {
/*  54 */     if (paramHostAddress3 != null && (
/*  55 */       paramHostAddress1 == null || paramHostAddress3 == null || 
/*  56 */       !paramHostAddress1.equals(paramHostAddress3))) {
/*  57 */       if (DEBUG && paramHostAddress1 == null) {
/*  58 */         System.out.println("packetSAddress is null");
/*     */       }
/*  60 */       if (DEBUG && paramHostAddress3 == null) {
/*  61 */         System.out.println("sAddress is null");
/*     */       }
/*  63 */       throw new KrbApErrException(38);
/*     */     } 
/*     */ 
/*     */     
/*  67 */     if (paramHostAddress4 != null && (
/*  68 */       paramHostAddress2 == null || paramHostAddress4 == null || 
/*  69 */       !paramHostAddress2.equals(paramHostAddress4))) {
/*  70 */       throw new KrbApErrException(38);
/*     */     }
/*     */     
/*  73 */     if (paramKerberosTime != null) {
/*  74 */       if (paramInteger1 != null)
/*     */       {
/*  76 */         paramKerberosTime = paramKerberosTime.withMicroSeconds(paramInteger1.intValue());
/*     */       }
/*  78 */       if (!paramKerberosTime.inClockSkew()) {
/*  79 */         throw new KrbApErrException(37);
/*     */       }
/*     */     }
/*  82 */     else if (paramBoolean1) {
/*  83 */       throw new KrbApErrException(37);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     if (paramSeqNumber == null && paramBoolean2 == true) {
/*  93 */       throw new KrbApErrException(400);
/*     */     }
/*  95 */     if (paramInteger2 != null && paramSeqNumber != null) {
/*  96 */       if (paramInteger2.intValue() != paramSeqNumber.current()) {
/*  97 */         throw new KrbApErrException(42);
/*     */       }
/*  99 */       paramSeqNumber.step();
/*     */     }
/* 101 */     else if (paramBoolean2) {
/* 102 */       throw new KrbApErrException(42);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (paramKerberosTime == null && paramInteger2 == null)
/* 108 */       throw new KrbApErrException(41); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbAppMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */