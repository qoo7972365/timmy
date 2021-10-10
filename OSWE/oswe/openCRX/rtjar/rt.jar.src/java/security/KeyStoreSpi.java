/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class KeyStoreSpi
/*     */ {
/*     */   public abstract Key engineGetKey(String paramString, char[] paramArrayOfchar) throws NoSuchAlgorithmException, UnrecoverableKeyException;
/*     */   
/*     */   public abstract Certificate[] engineGetCertificateChain(String paramString);
/*     */   
/*     */   public abstract Certificate engineGetCertificate(String paramString);
/*     */   
/*     */   public abstract Date engineGetCreationDate(String paramString);
/*     */   
/*     */   public abstract void engineSetKeyEntry(String paramString, Key paramKey, char[] paramArrayOfchar, Certificate[] paramArrayOfCertificate) throws KeyStoreException;
/*     */   
/*     */   public abstract void engineSetKeyEntry(String paramString, byte[] paramArrayOfbyte, Certificate[] paramArrayOfCertificate) throws KeyStoreException;
/*     */   
/*     */   public abstract void engineSetCertificateEntry(String paramString, Certificate paramCertificate) throws KeyStoreException;
/*     */   
/*     */   public abstract void engineDeleteEntry(String paramString) throws KeyStoreException;
/*     */   
/*     */   public abstract Enumeration<String> engineAliases();
/*     */   
/*     */   public abstract boolean engineContainsAlias(String paramString);
/*     */   
/*     */   public abstract int engineSize();
/*     */   
/*     */   public abstract boolean engineIsKeyEntry(String paramString);
/*     */   
/*     */   public abstract boolean engineIsCertificateEntry(String paramString);
/*     */   
/*     */   public abstract String engineGetCertificateAlias(Certificate paramCertificate);
/*     */   
/*     */   public abstract void engineStore(OutputStream paramOutputStream, char[] paramArrayOfchar) throws IOException, NoSuchAlgorithmException, CertificateException;
/*     */   
/*     */   public void engineStore(KeyStore.LoadStoreParameter paramLoadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
/* 320 */     throw new UnsupportedOperationException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void engineLoad(InputStream paramInputStream, char[] paramArrayOfchar) throws IOException, NoSuchAlgorithmException, CertificateException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void engineLoad(KeyStore.LoadStoreParameter paramLoadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
/* 383 */     if (paramLoadStoreParameter == null) {
/* 384 */       engineLoad((InputStream)null, (char[])null);
/*     */       
/*     */       return;
/*     */     } 
/* 388 */     if (paramLoadStoreParameter instanceof KeyStore.SimpleLoadStoreParameter) {
/* 389 */       char[] arrayOfChar; KeyStore.ProtectionParameter protectionParameter = paramLoadStoreParameter.getProtectionParameter();
/*     */       
/* 391 */       if (protectionParameter instanceof KeyStore.PasswordProtection) {
/* 392 */         arrayOfChar = ((KeyStore.PasswordProtection)protectionParameter).getPassword();
/* 393 */       } else if (protectionParameter instanceof KeyStore.CallbackHandlerProtection) {
/*     */         
/* 395 */         CallbackHandler callbackHandler = ((KeyStore.CallbackHandlerProtection)protectionParameter).getCallbackHandler();
/* 396 */         PasswordCallback passwordCallback = new PasswordCallback("Password: ", false);
/*     */         
/*     */         try {
/* 399 */           callbackHandler.handle(new Callback[] { passwordCallback });
/* 400 */         } catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 401 */           throw new NoSuchAlgorithmException("Could not obtain password", unsupportedCallbackException);
/*     */         } 
/*     */         
/* 404 */         arrayOfChar = passwordCallback.getPassword();
/* 405 */         passwordCallback.clearPassword();
/* 406 */         if (arrayOfChar == null) {
/* 407 */           throw new NoSuchAlgorithmException("No password provided");
/*     */         }
/*     */       } else {
/*     */         
/* 411 */         throw new NoSuchAlgorithmException("ProtectionParameter must be PasswordProtection or CallbackHandlerProtection");
/*     */       } 
/*     */       
/* 414 */       engineLoad(null, arrayOfChar);
/*     */       
/*     */       return;
/*     */     } 
/* 418 */     throw new UnsupportedOperationException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyStore.Entry engineGetEntry(String paramString, KeyStore.ProtectionParameter paramProtectionParameter) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException {
/* 450 */     if (!engineContainsAlias(paramString)) {
/* 451 */       return null;
/*     */     }
/*     */     
/* 454 */     if (paramProtectionParameter == null) {
/* 455 */       if (engineIsCertificateEntry(paramString)) {
/* 456 */         return new KeyStore.TrustedCertificateEntry(
/* 457 */             engineGetCertificate(paramString));
/*     */       }
/* 459 */       throw new UnrecoverableKeyException("requested entry requires a password");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 464 */     if (paramProtectionParameter instanceof KeyStore.PasswordProtection) {
/* 465 */       if (engineIsCertificateEntry(paramString)) {
/* 466 */         throw new UnsupportedOperationException("trusted certificate entries are not password-protected");
/*     */       }
/* 468 */       if (engineIsKeyEntry(paramString)) {
/* 469 */         KeyStore.PasswordProtection passwordProtection = (KeyStore.PasswordProtection)paramProtectionParameter;
/*     */         
/* 471 */         char[] arrayOfChar = passwordProtection.getPassword();
/*     */         
/* 473 */         Key key = engineGetKey(paramString, arrayOfChar);
/* 474 */         if (key instanceof PrivateKey) {
/* 475 */           Certificate[] arrayOfCertificate = engineGetCertificateChain(paramString);
/* 476 */           return new KeyStore.PrivateKeyEntry((PrivateKey)key, arrayOfCertificate);
/* 477 */         }  if (key instanceof SecretKey) {
/* 478 */           return new KeyStore.SecretKeyEntry((SecretKey)key);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 483 */     throw new UnsupportedOperationException();
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
/*     */   public void engineSetEntry(String paramString, KeyStore.Entry paramEntry, KeyStore.ProtectionParameter paramProtectionParameter) throws KeyStoreException {
/* 509 */     if (paramProtectionParameter != null && !(paramProtectionParameter instanceof KeyStore.PasswordProtection))
/*     */     {
/* 511 */       throw new KeyStoreException("unsupported protection parameter");
/*     */     }
/* 513 */     KeyStore.PasswordProtection passwordProtection = null;
/* 514 */     if (paramProtectionParameter != null) {
/* 515 */       passwordProtection = (KeyStore.PasswordProtection)paramProtectionParameter;
/*     */     }
/*     */ 
/*     */     
/* 519 */     if (paramEntry instanceof KeyStore.TrustedCertificateEntry) {
/* 520 */       if (paramProtectionParameter != null && passwordProtection.getPassword() != null)
/*     */       {
/* 522 */         throw new KeyStoreException("trusted certificate entries are not password-protected");
/*     */       }
/*     */       
/* 525 */       KeyStore.TrustedCertificateEntry trustedCertificateEntry = (KeyStore.TrustedCertificateEntry)paramEntry;
/*     */       
/* 527 */       engineSetCertificateEntry(paramString, trustedCertificateEntry.getTrustedCertificate());
/*     */       return;
/*     */     } 
/* 530 */     if (paramEntry instanceof KeyStore.PrivateKeyEntry) {
/* 531 */       if (passwordProtection == null || passwordProtection.getPassword() == null)
/*     */       {
/* 533 */         throw new KeyStoreException("non-null password required to create PrivateKeyEntry");
/*     */       }
/*     */ 
/*     */       
/* 537 */       engineSetKeyEntry(paramString, ((KeyStore.PrivateKeyEntry)paramEntry)
/* 538 */           .getPrivateKey(), passwordProtection
/* 539 */           .getPassword(), ((KeyStore.PrivateKeyEntry)paramEntry)
/* 540 */           .getCertificateChain());
/*     */       return;
/*     */     } 
/* 543 */     if (paramEntry instanceof KeyStore.SecretKeyEntry) {
/* 544 */       if (passwordProtection == null || passwordProtection.getPassword() == null)
/*     */       {
/* 546 */         throw new KeyStoreException("non-null password required to create SecretKeyEntry");
/*     */       }
/*     */ 
/*     */       
/* 550 */       engineSetKeyEntry(paramString, ((KeyStore.SecretKeyEntry)paramEntry)
/* 551 */           .getSecretKey(), passwordProtection
/* 552 */           .getPassword(), (Certificate[])null);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 558 */     throw new KeyStoreException("unsupported entry type: " + paramEntry
/* 559 */         .getClass().getName());
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
/*     */   public boolean engineEntryInstanceOf(String paramString, Class<? extends KeyStore.Entry> paramClass) {
/* 580 */     if (paramClass == KeyStore.TrustedCertificateEntry.class) {
/* 581 */       return engineIsCertificateEntry(paramString);
/*     */     }
/* 583 */     if (paramClass == KeyStore.PrivateKeyEntry.class) {
/* 584 */       return (engineIsKeyEntry(paramString) && 
/* 585 */         engineGetCertificate(paramString) != null);
/*     */     }
/* 587 */     if (paramClass == KeyStore.SecretKeyEntry.class) {
/* 588 */       return (engineIsKeyEntry(paramString) && 
/* 589 */         engineGetCertificate(paramString) == null);
/*     */     }
/* 591 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/KeyStoreSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */