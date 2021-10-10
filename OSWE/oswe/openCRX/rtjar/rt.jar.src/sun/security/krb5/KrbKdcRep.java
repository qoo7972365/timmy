/*     */ package sun.security.krb5;
/*     */ 
/*     */ import sun.security.krb5.internal.KDCRep;
/*     */ import sun.security.krb5.internal.KDCReq;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.PAData;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ abstract class KrbKdcRep
/*     */ {
/*     */   static void check(boolean paramBoolean, KDCReq paramKDCReq, KDCRep paramKDCRep, EncryptionKey paramEncryptionKey) throws KrbApErrException {
/*  49 */     if (paramBoolean && !paramKDCReq.reqBody.cname.equals(paramKDCRep.cname) && (
/*  50 */       !paramKDCReq.reqBody.kdcOptions.get(15) || 
/*  51 */       !paramKDCRep.encKDCRepPart.flags.get(15))) {
/*  52 */       paramKDCRep.encKDCRepPart.key.destroy();
/*  53 */       throw new KrbApErrException(41);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (!paramKDCReq.reqBody.sname.equals(paramKDCRep.encKDCRepPart.sname)) {
/*  60 */       String[] arrayOfString = paramKDCRep.encKDCRepPart.sname.getNameStrings();
/*  61 */       if (paramBoolean || !paramKDCReq.reqBody.kdcOptions.get(15) || arrayOfString == null || arrayOfString.length != 2 || 
/*     */         
/*  63 */         !arrayOfString[0].equals("krbtgt") || 
/*  64 */         !paramKDCRep.encKDCRepPart.sname.getRealmString().equals(paramKDCReq.reqBody.sname
/*  65 */           .getRealmString())) {
/*  66 */         paramKDCRep.encKDCRepPart.key.destroy();
/*  67 */         throw new KrbApErrException(41);
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     if (paramKDCReq.reqBody.getNonce() != paramKDCRep.encKDCRepPart.nonce) {
/*  72 */       paramKDCRep.encKDCRepPart.key.destroy();
/*  73 */       throw new KrbApErrException(41);
/*     */     } 
/*     */     
/*  76 */     if (paramKDCReq.reqBody.addresses != null && paramKDCRep.encKDCRepPart.caddr != null && 
/*     */       
/*  78 */       !paramKDCReq.reqBody.addresses.equals(paramKDCRep.encKDCRepPart.caddr)) {
/*  79 */       paramKDCRep.encKDCRepPart.key.destroy();
/*  80 */       throw new KrbApErrException(41);
/*     */     } 
/*     */     
/*     */     byte b;
/*  84 */     for (b = 2; b < 6; b++) {
/*  85 */       if (paramKDCReq.reqBody.kdcOptions.get(b) != paramKDCRep.encKDCRepPart.flags
/*  86 */         .get(b)) {
/*  87 */         if (Krb5.DEBUG) {
/*  88 */           System.out.println("> KrbKdcRep.check: at #" + b + ". request for " + paramKDCReq.reqBody.kdcOptions
/*  89 */               .get(b) + ", received " + paramKDCRep.encKDCRepPart.flags
/*  90 */               .get(b));
/*     */         }
/*  92 */         throw new KrbApErrException(41);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     if (paramKDCReq.reqBody.kdcOptions.get(8) && 
/* 100 */       !paramKDCRep.encKDCRepPart.flags.get(8)) {
/* 101 */       throw new KrbApErrException(41);
/*     */     }
/*     */     
/* 104 */     if (paramKDCReq.reqBody.from == null || paramKDCReq.reqBody.from.isZero())
/*     */     {
/* 106 */       if (paramKDCRep.encKDCRepPart.starttime != null && 
/* 107 */         !paramKDCRep.encKDCRepPart.starttime.inClockSkew()) {
/* 108 */         paramKDCRep.encKDCRepPart.key.destroy();
/* 109 */         throw new KrbApErrException(37);
/*     */       } 
/*     */     }
/*     */     
/* 113 */     if (paramKDCReq.reqBody.from != null && !paramKDCReq.reqBody.from.isZero())
/*     */     {
/* 115 */       if (paramKDCRep.encKDCRepPart.starttime != null && 
/* 116 */         !paramKDCReq.reqBody.from.equals(paramKDCRep.encKDCRepPart.starttime)) {
/* 117 */         paramKDCRep.encKDCRepPart.key.destroy();
/* 118 */         throw new KrbApErrException(41);
/*     */       } 
/*     */     }
/*     */     
/* 122 */     if (!paramKDCReq.reqBody.till.isZero() && paramKDCRep.encKDCRepPart.endtime
/* 123 */       .greaterThan(paramKDCReq.reqBody.till)) {
/* 124 */       paramKDCRep.encKDCRepPart.key.destroy();
/* 125 */       throw new KrbApErrException(41);
/*     */     } 
/*     */ 
/*     */     
/* 129 */     if (paramKDCRep.encKDCRepPart.flags.get(15) && paramKDCReq.reqBody.kdcOptions
/* 130 */       .get(15)) {
/* 131 */       b = 0;
/* 132 */       boolean bool = false;
/*     */ 
/*     */       
/* 135 */       for (PAData pAData : paramKDCReq.pAData) {
/* 136 */         if (pAData.getType() == 149) {
/* 137 */           b = 1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 142 */       if (paramKDCRep.encKDCRepPart.pAData != null) {
/* 143 */         for (PAData pAData : paramKDCRep.encKDCRepPart.pAData) {
/* 144 */           if (pAData.getType() == 149) {
/*     */ 
/*     */             
/*     */             try {
/* 148 */               Checksum checksum = new Checksum((new DerInputStream(pAData.getValue())).getDerValue());
/*     */ 
/*     */ 
/*     */               
/* 152 */               bool = checksum.verifyAnyChecksum(paramKDCReq
/* 153 */                   .asn1Encode(), paramEncryptionKey, 56);
/*     */             }
/* 155 */             catch (Exception exception) {
/* 156 */               if (Krb5.DEBUG) {
/* 157 */                 exception.printStackTrace();
/*     */               }
/*     */             } 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 165 */       if (b != 0 && !bool) {
/* 166 */         throw new KrbApErrException(41);
/*     */       }
/*     */     } 
/*     */     
/* 170 */     if (paramKDCReq.reqBody.kdcOptions.get(8) && 
/* 171 */       paramKDCReq.reqBody.rtime != null && !paramKDCReq.reqBody.rtime.isZero())
/*     */     {
/* 173 */       if (paramKDCRep.encKDCRepPart.renewTill == null || paramKDCRep.encKDCRepPart.renewTill
/* 174 */         .greaterThan(paramKDCReq.reqBody.rtime)) {
/*     */         
/* 176 */         paramKDCRep.encKDCRepPart.key.destroy();
/* 177 */         throw new KrbApErrException(41);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbKdcRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */