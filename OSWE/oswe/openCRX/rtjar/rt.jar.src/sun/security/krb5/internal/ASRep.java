/*    */ package sun.security.krb5.internal;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import sun.security.krb5.Asn1Exception;
/*    */ import sun.security.krb5.EncryptedData;
/*    */ import sun.security.krb5.PrincipalName;
/*    */ import sun.security.krb5.RealmException;
/*    */ import sun.security.util.DerValue;
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
/*    */ public class ASRep
/*    */   extends KDCRep
/*    */ {
/*    */   public ASRep(PAData[] paramArrayOfPAData, PrincipalName paramPrincipalName, Ticket paramTicket, EncryptedData paramEncryptedData) throws IOException {
/* 48 */     super(paramArrayOfPAData, paramPrincipalName, paramTicket, paramEncryptedData, 11);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ASRep(byte[] paramArrayOfbyte) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 54 */     init(new DerValue(paramArrayOfbyte));
/*    */   }
/*    */ 
/*    */   
/*    */   public ASRep(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 59 */     init(paramDerValue);
/*    */   }
/*    */ 
/*    */   
/*    */   private void init(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 64 */     init(paramDerValue, 11);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ASRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */