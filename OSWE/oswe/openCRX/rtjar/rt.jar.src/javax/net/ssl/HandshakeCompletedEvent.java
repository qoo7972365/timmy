/*     */ package javax.net.ssl;
/*     */ 
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.EventObject;
/*     */ import javax.security.cert.X509Certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HandshakeCompletedEvent
/*     */   extends EventObject
/*     */ {
/*     */   private static final long serialVersionUID = 7914963744257769778L;
/*     */   private transient SSLSession session;
/*     */   
/*     */   public HandshakeCompletedEvent(SSLSocket paramSSLSocket, SSLSession paramSSLSession) {
/*  64 */     super(paramSSLSocket);
/*  65 */     this.session = paramSSLSession;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SSLSession getSession() {
/*  76 */     return this.session;
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
/*     */   public String getCipherSuite() {
/*  89 */     return this.session.getCipherSuite();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Certificate[] getLocalCertificates() {
/* 113 */     return this.session.getLocalCertificates();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
/* 133 */     return this.session.getPeerCertificates();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
/* 159 */     return this.session.getPeerCertificateChain();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
/*     */     Principal principal;
/*     */     try {
/* 183 */       principal = this.session.getPeerPrincipal();
/* 184 */     } catch (AbstractMethodError abstractMethodError) {
/*     */ 
/*     */       
/* 187 */       Certificate[] arrayOfCertificate = getPeerCertificates();
/* 188 */       principal = ((X509Certificate)arrayOfCertificate[0]).getSubjectX500Principal();
/*     */     } 
/* 190 */     return principal;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Principal getLocalPrincipal() {
/*     */     Principal principal;
/*     */     try {
/* 210 */       principal = this.session.getLocalPrincipal();
/* 211 */     } catch (AbstractMethodError abstractMethodError) {
/* 212 */       principal = null;
/*     */ 
/*     */       
/* 215 */       Certificate[] arrayOfCertificate = getLocalCertificates();
/* 216 */       if (arrayOfCertificate != null)
/*     */       {
/* 218 */         principal = ((X509Certificate)arrayOfCertificate[0]).getSubjectX500Principal();
/*     */       }
/*     */     } 
/* 221 */     return principal;
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
/*     */   public SSLSocket getSocket() {
/* 233 */     return (SSLSocket)getSource();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/HandshakeCompletedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */