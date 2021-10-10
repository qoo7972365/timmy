/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.x509.GeneralName;
/*     */ import sun.security.x509.GeneralNameInterface;
/*     */ import sun.security.x509.GeneralNames;
/*     */ import sun.security.x509.SubjectAlternativeNameExtension;
/*     */ import sun.security.x509.X500Name;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ForwardState
/*     */   implements State
/*     */ {
/*  56 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   X500Principal issuerDN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   X509CertImpl cert;
/*     */ 
/*     */ 
/*     */   
/*     */   HashSet<GeneralNameInterface> subjectNamesTraversed;
/*     */ 
/*     */ 
/*     */   
/*     */   int traversedCACerts;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean init = true;
/*     */ 
/*     */ 
/*     */   
/*     */   UntrustedChecker untrustedChecker;
/*     */ 
/*     */ 
/*     */   
/*     */   ArrayList<PKIXCertPathChecker> forwardCheckers;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean keyParamsNeededFlag = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitial() {
/*  96 */     return this.init;
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
/*     */   public boolean keyParamsNeeded() {
/* 108 */     return this.keyParamsNeededFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     StringBuilder stringBuilder = new StringBuilder();
/* 117 */     stringBuilder.append("State [");
/* 118 */     stringBuilder.append("\n  issuerDN of last cert: ").append(this.issuerDN);
/* 119 */     stringBuilder.append("\n  traversedCACerts: ").append(this.traversedCACerts);
/* 120 */     stringBuilder.append("\n  init: ").append(String.valueOf(this.init));
/* 121 */     stringBuilder.append("\n  keyParamsNeeded: ")
/* 122 */       .append(String.valueOf(this.keyParamsNeededFlag));
/* 123 */     stringBuilder.append("\n  subjectNamesTraversed: \n")
/* 124 */       .append(this.subjectNamesTraversed);
/* 125 */     stringBuilder.append("]\n");
/* 126 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initState(List<PKIXCertPathChecker> paramList) throws CertPathValidatorException {
/* 137 */     this.subjectNamesTraversed = new HashSet<>();
/* 138 */     this.traversedCACerts = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     this.forwardCheckers = new ArrayList<>();
/* 145 */     for (PKIXCertPathChecker pKIXCertPathChecker : paramList) {
/* 146 */       if (pKIXCertPathChecker.isForwardCheckingSupported()) {
/* 147 */         pKIXCertPathChecker.init(true);
/* 148 */         this.forwardCheckers.add(pKIXCertPathChecker);
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     this.init = true;
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
/*     */   public void updateState(X509Certificate paramX509Certificate) throws CertificateException, IOException, CertPathValidatorException {
/* 164 */     if (paramX509Certificate == null) {
/*     */       return;
/*     */     }
/* 167 */     X509CertImpl x509CertImpl = X509CertImpl.toImpl(paramX509Certificate);
/*     */ 
/*     */     
/* 170 */     if (PKIX.isDSAPublicKeyWithoutParams(x509CertImpl.getPublicKey())) {
/* 171 */       this.keyParamsNeededFlag = true;
/*     */     }
/*     */ 
/*     */     
/* 175 */     this.cert = x509CertImpl;
/*     */ 
/*     */     
/* 178 */     this.issuerDN = paramX509Certificate.getIssuerX500Principal();
/*     */     
/* 180 */     if (!X509CertImpl.isSelfIssued(paramX509Certificate))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       if (!this.init && paramX509Certificate.getBasicConstraints() != -1) {
/* 187 */         this.traversedCACerts++;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 193 */     if (this.init || !X509CertImpl.isSelfIssued(paramX509Certificate)) {
/* 194 */       X500Principal x500Principal = paramX509Certificate.getSubjectX500Principal();
/* 195 */       this.subjectNamesTraversed.add(X500Name.asX500Name(x500Principal));
/*     */ 
/*     */       
/*     */       try {
/* 199 */         SubjectAlternativeNameExtension subjectAlternativeNameExtension = x509CertImpl.getSubjectAlternativeNameExtension();
/* 200 */         if (subjectAlternativeNameExtension != null) {
/* 201 */           GeneralNames generalNames = subjectAlternativeNameExtension.get("subject_name");
/*     */           
/* 203 */           for (GeneralName generalName : generalNames.names()) {
/* 204 */             this.subjectNamesTraversed.add(generalName.getName());
/*     */           }
/*     */         } 
/* 207 */       } catch (IOException iOException) {
/* 208 */         if (debug != null) {
/* 209 */           debug.println("ForwardState.updateState() unexpected exception");
/*     */           
/* 211 */           iOException.printStackTrace();
/*     */         } 
/* 213 */         throw new CertPathValidatorException(iOException);
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     this.init = false;
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
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 233 */       ForwardState forwardState = (ForwardState)super.clone();
/*     */ 
/*     */       
/* 236 */       forwardState
/* 237 */         .forwardCheckers = (ArrayList<PKIXCertPathChecker>)this.forwardCheckers.clone();
/*     */       
/* 239 */       ListIterator<PKIXCertPathChecker> listIterator = forwardState.forwardCheckers.listIterator();
/* 240 */       while (listIterator.hasNext()) {
/* 241 */         PKIXCertPathChecker pKIXCertPathChecker = listIterator.next();
/* 242 */         if (pKIXCertPathChecker instanceof Cloneable) {
/* 243 */           listIterator.set((PKIXCertPathChecker)pKIXCertPathChecker.clone());
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 252 */       forwardState
/* 253 */         .subjectNamesTraversed = (HashSet<GeneralNameInterface>)this.subjectNamesTraversed.clone();
/* 254 */       return forwardState;
/* 255 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 256 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/ForwardState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */