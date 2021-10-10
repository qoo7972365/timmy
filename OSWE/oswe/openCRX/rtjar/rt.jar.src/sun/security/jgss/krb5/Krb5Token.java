/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.jgss.GSSToken;
/*     */ import sun.security.util.ObjectIdentifier;
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
/*     */ abstract class Krb5Token
/*     */   extends GSSToken
/*     */ {
/*     */   public static final int AP_REQ_ID = 256;
/*     */   public static final int AP_REP_ID = 512;
/*     */   public static final int ERR_ID = 768;
/*     */   public static final int MIC_ID = 257;
/*     */   public static final int WRAP_ID = 513;
/*     */   public static final int MIC_ID_v2 = 1028;
/*     */   public static final int WRAP_ID_v2 = 1284;
/*     */   public static ObjectIdentifier OID;
/*     */   
/*     */   static {
/*     */     try {
/*  81 */       OID = new ObjectIdentifier(Krb5MechFactory.GSS_KRB5_MECH_OID.toString());
/*  82 */     } catch (IOException iOException) {}
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
/*     */   public static String getTokenName(int paramInt) {
/*  94 */     String str = null;
/*  95 */     switch (paramInt)
/*     */     { case 256:
/*     */       case 512:
/*  98 */         str = "Context Establishment Token";
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
/* 116 */         return str;case 257: str = "MIC Token"; return str;case 1028: str = "MIC Token (new format)"; return str;case 513: str = "Wrap Token"; return str;case 1284: str = "Wrap Token (new format)"; return str; }  str = "Kerberos GSS-API Mechanism Token"; return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/Krb5Token.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */