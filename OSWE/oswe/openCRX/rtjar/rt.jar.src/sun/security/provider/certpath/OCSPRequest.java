/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.cert.Extension;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.x509.PKIXExtensions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class OCSPRequest
/*     */ {
/*  80 */   private static final Debug debug = Debug.getInstance("certpath");
/*  81 */   private static final boolean dump = (debug != null && Debug.isOn("ocsp"));
/*     */ 
/*     */   
/*     */   private final List<CertId> certIds;
/*     */ 
/*     */   
/*     */   private final List<Extension> extensions;
/*     */   
/*     */   private byte[] nonce;
/*     */ 
/*     */   
/*     */   OCSPRequest(CertId paramCertId) {
/*  93 */     this(Collections.singletonList(paramCertId));
/*     */   }
/*     */   
/*     */   OCSPRequest(List<CertId> paramList) {
/*  97 */     this.certIds = paramList;
/*  98 */     this.extensions = Collections.emptyList();
/*     */   }
/*     */   
/*     */   OCSPRequest(List<CertId> paramList, List<Extension> paramList1) {
/* 102 */     this.certIds = paramList;
/* 103 */     this.extensions = paramList1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] encodeBytes() throws IOException {
/* 109 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 110 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 111 */     for (CertId certId : this.certIds) {
/* 112 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 113 */       certId.encode(derOutputStream);
/* 114 */       derOutputStream2.write((byte)48, derOutputStream);
/*     */     } 
/*     */     
/* 117 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 118 */     if (!this.extensions.isEmpty()) {
/* 119 */       DerOutputStream derOutputStream5 = new DerOutputStream();
/* 120 */       for (Extension extension : this.extensions) {
/* 121 */         extension.encode(derOutputStream5);
/* 122 */         if (extension.getId().equals(PKIXExtensions.OCSPNonce_Id
/* 123 */             .toString())) {
/* 124 */           this.nonce = extension.getValue();
/*     */         }
/*     */       } 
/* 127 */       DerOutputStream derOutputStream6 = new DerOutputStream();
/* 128 */       derOutputStream6.write((byte)48, derOutputStream5);
/* 129 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), derOutputStream6);
/*     */     } 
/*     */ 
/*     */     
/* 133 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 134 */     derOutputStream3.write((byte)48, derOutputStream1);
/*     */ 
/*     */     
/* 137 */     DerOutputStream derOutputStream4 = new DerOutputStream();
/* 138 */     derOutputStream4.write((byte)48, derOutputStream3);
/*     */     
/* 140 */     byte[] arrayOfByte = derOutputStream4.toByteArray();
/*     */     
/* 142 */     if (dump) {
/* 143 */       HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 144 */       debug.println("OCSPRequest bytes...\n\n" + hexDumpEncoder
/* 145 */           .encode(arrayOfByte) + "\n");
/*     */     } 
/*     */     
/* 148 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   List<CertId> getCertIds() {
/* 152 */     return this.certIds;
/*     */   }
/*     */   
/*     */   byte[] getNonce() {
/* 156 */     return this.nonce;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/OCSPRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */