/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.internal.EncTGSRepPart;
/*     */ import sun.security.krb5.internal.KRBError;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.TGSRep;
/*     */ import sun.security.krb5.internal.TGSReq;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.ccache.Credentials;
/*     */ import sun.security.util.DerValue;
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
/*     */ public class KrbTgsRep
/*     */   extends KrbKdcRep
/*     */ {
/*     */   private TGSRep rep;
/*     */   private Credentials creds;
/*     */   private Ticket secondTicket;
/*  47 */   private static final boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */   
/*     */   KrbTgsRep(byte[] paramArrayOfbyte, KrbTgsReq paramKrbTgsReq) throws KrbException, IOException {
/*  51 */     DerValue derValue = new DerValue(paramArrayOfbyte);
/*  52 */     TGSReq tGSReq = paramKrbTgsReq.getMessage();
/*  53 */     TGSRep tGSRep = null;
/*     */     try {
/*  55 */       tGSRep = new TGSRep(derValue);
/*  56 */     } catch (Asn1Exception asn1Exception) {
/*  57 */       KrbException krbException; tGSRep = null;
/*  58 */       KRBError kRBError = new KRBError(derValue);
/*  59 */       String str1 = kRBError.getErrorString();
/*  60 */       String str2 = null;
/*  61 */       if (str1 != null && str1.length() > 0) {
/*  62 */         if (str1.charAt(str1.length() - 1) == '\000') {
/*  63 */           str2 = str1.substring(0, str1.length() - 1);
/*     */         } else {
/*  65 */           str2 = str1;
/*     */         } 
/*     */       }
/*  68 */       if (str2 == null) {
/*     */         
/*  70 */         krbException = new KrbException(kRBError.getErrorCode());
/*     */       } else {
/*     */         
/*  73 */         krbException = new KrbException(kRBError.getErrorCode(), str2);
/*     */       } 
/*  75 */       krbException.initCause(asn1Exception);
/*  76 */       throw krbException;
/*     */     } 
/*  78 */     byte[] arrayOfByte1 = tGSRep.encPart.decrypt(paramKrbTgsReq.tgsReqKey, 
/*  79 */         paramKrbTgsReq.usedSubkey() ? 9 : 8);
/*     */ 
/*     */     
/*  82 */     byte[] arrayOfByte2 = tGSRep.encPart.reset(arrayOfByte1);
/*  83 */     derValue = new DerValue(arrayOfByte2);
/*  84 */     EncTGSRepPart encTGSRepPart = new EncTGSRepPart(derValue);
/*  85 */     tGSRep.encKDCRepPart = encTGSRepPart;
/*     */     
/*  87 */     check(false, tGSReq, tGSRep, paramKrbTgsReq.tgsReqKey);
/*     */     
/*  89 */     PrincipalName principalName = paramKrbTgsReq.getServerAlias();
/*  90 */     if (principalName != null) {
/*  91 */       PrincipalName principalName1 = encTGSRepPart.sname;
/*  92 */       if (principalName.equals(principalName1) || 
/*  93 */         isReferralSname(principalName1)) {
/*  94 */         principalName = null;
/*     */       }
/*     */     } 
/*     */     
/*  98 */     this
/*     */       
/* 100 */       .creds = new Credentials(tGSRep.ticket, tGSRep.cname, paramKrbTgsReq.getClientAlias(), encTGSRepPart.sname, principalName, encTGSRepPart.key, encTGSRepPart.flags, encTGSRepPart.authtime, encTGSRepPart.starttime, encTGSRepPart.endtime, encTGSRepPart.renewTill, encTGSRepPart.caddr);
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
/* 111 */     this.rep = tGSRep;
/* 112 */     this.secondTicket = paramKrbTgsReq.getSecondTicket();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials getCreds() {
/* 119 */     return this.creds;
/*     */   }
/*     */   
/*     */   Credentials setCredentials() {
/* 123 */     return new Credentials(this.rep, this.secondTicket);
/*     */   }
/*     */   
/*     */   private static boolean isReferralSname(PrincipalName paramPrincipalName) {
/* 127 */     if (paramPrincipalName != null) {
/* 128 */       String[] arrayOfString = paramPrincipalName.getNameStrings();
/* 129 */       if (arrayOfString.length == 2 && arrayOfString[0]
/* 130 */         .equals("krbtgt"))
/*     */       {
/* 132 */         return true;
/*     */       }
/*     */     } 
/* 135 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbTgsRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */