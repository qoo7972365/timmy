/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificatePolicySet
/*     */ {
/*     */   private final Vector<CertificatePolicyId> ids;
/*     */   
/*     */   public CertificatePolicySet(Vector<CertificatePolicyId> paramVector) {
/*  51 */     this.ids = paramVector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CertificatePolicySet(DerInputStream paramDerInputStream) throws IOException {
/*  61 */     this.ids = new Vector<>();
/*  62 */     DerValue[] arrayOfDerValue = paramDerInputStream.getSequence(5);
/*     */     
/*  64 */     for (byte b = 0; b < arrayOfDerValue.length; b++) {
/*  65 */       CertificatePolicyId certificatePolicyId = new CertificatePolicyId(arrayOfDerValue[b]);
/*  66 */       this.ids.addElement(certificatePolicyId);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  74 */     return "CertificatePolicySet:[\n" + this.ids
/*  75 */       .toString() + "]\n";
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
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/*  87 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     
/*  89 */     for (byte b = 0; b < this.ids.size(); b++) {
/*  90 */       ((CertificatePolicyId)this.ids.elementAt(b)).encode(derOutputStream);
/*     */     }
/*  92 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CertificatePolicyId> getCertPolicyIds() {
/* 102 */     return Collections.unmodifiableList(this.ids);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/CertificatePolicySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */