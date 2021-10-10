/*     */ package com.sun.security.auth.module;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.AuthProvider;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.Destroyable;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.ConfirmationCallback;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.TextOutputCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.security.auth.login.FailedLoginException;
/*     */ import javax.security.auth.login.LoginException;
/*     */ import javax.security.auth.spi.LoginModule;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import javax.security.auth.x500.X500PrivateCredential;
/*     */ import jdk.Exported;
/*     */ import sun.security.util.Password;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public class KeyStoreLoginModule
/*     */   implements LoginModule
/*     */ {
/* 116 */   private static final ResourceBundle rb = AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>()
/*     */       {
/*     */         public ResourceBundle run() {
/* 119 */           return ResourceBundle.getBundle("sun.security.util.AuthResources");
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   private static final int UNINITIALIZED = 0;
/*     */   
/*     */   private static final int INITIALIZED = 1;
/*     */   
/*     */   private static final int AUTHENTICATED = 2;
/*     */   
/*     */   private static final int LOGGED_IN = 3;
/*     */   
/*     */   private static final int PROTECTED_PATH = 0;
/*     */   
/*     */   private static final int TOKEN = 1;
/*     */   
/*     */   private static final int NORMAL = 2;
/*     */   private static final String NONE = "NONE";
/*     */   private static final String P11KEYSTORE = "PKCS11";
/* 139 */   private static final TextOutputCallback bannerCallback = new TextOutputCallback(0, rb
/*     */ 
/*     */       
/* 142 */       .getString("Please.enter.keystore.information"));
/* 143 */   private final ConfirmationCallback confirmationCallback = new ConfirmationCallback(0, 2, 3);
/*     */   
/*     */   private Subject subject;
/*     */   
/*     */   private CallbackHandler callbackHandler;
/*     */   
/*     */   private Map<String, Object> sharedState;
/*     */   
/*     */   private Map<String, ?> options;
/*     */   
/*     */   private char[] keyStorePassword;
/*     */   
/*     */   private char[] privateKeyPassword;
/*     */   
/*     */   private KeyStore keyStore;
/*     */   private String keyStoreURL;
/*     */   private String keyStoreType;
/*     */   private String keyStoreProvider;
/*     */   private String keyStoreAlias;
/*     */   private String keyStorePasswordURL;
/*     */   private String privateKeyPasswordURL;
/*     */   private boolean debug;
/*     */   private X500Principal principal;
/*     */   private Certificate[] fromKeyStore;
/* 167 */   private CertPath certP = null;
/*     */   private X500PrivateCredential privateCredential;
/* 169 */   private int status = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean nullStream = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean token = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean protectedPath = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(Subject paramSubject, CallbackHandler paramCallbackHandler, Map<String, ?> paramMap1, Map<String, ?> paramMap2) {
/* 202 */     this.subject = paramSubject;
/* 203 */     this.callbackHandler = paramCallbackHandler;
/* 204 */     this.sharedState = (Map)paramMap1;
/* 205 */     this.options = paramMap2;
/*     */     
/* 207 */     processOptions();
/* 208 */     this.status = 1;
/*     */   }
/*     */   
/*     */   private void processOptions() {
/* 212 */     this.keyStoreURL = (String)this.options.get("keyStoreURL");
/* 213 */     if (this.keyStoreURL == null) {
/* 214 */       this
/*     */         
/* 216 */         .keyStoreURL = "file:" + System.getProperty("user.home").replace(File.separatorChar, '/') + '/' + ".keystore";
/*     */     
/*     */     }
/* 219 */     else if ("NONE".equals(this.keyStoreURL)) {
/* 220 */       this.nullStream = true;
/*     */     } 
/* 222 */     this.keyStoreType = (String)this.options.get("keyStoreType");
/* 223 */     if (this.keyStoreType == null) {
/* 224 */       this.keyStoreType = KeyStore.getDefaultType();
/*     */     }
/* 226 */     if ("PKCS11".equalsIgnoreCase(this.keyStoreType)) {
/* 227 */       this.token = true;
/*     */     }
/*     */     
/* 230 */     this.keyStoreProvider = (String)this.options.get("keyStoreProvider");
/*     */     
/* 232 */     this.keyStoreAlias = (String)this.options.get("keyStoreAlias");
/*     */     
/* 234 */     this.keyStorePasswordURL = (String)this.options.get("keyStorePasswordURL");
/*     */     
/* 236 */     this.privateKeyPasswordURL = (String)this.options.get("privateKeyPasswordURL");
/*     */     
/* 238 */     this.protectedPath = "true".equalsIgnoreCase((String)this.options
/* 239 */         .get("protected"));
/*     */     
/* 241 */     this.debug = "true".equalsIgnoreCase((String)this.options.get("debug"));
/* 242 */     if (this.debug) {
/* 243 */       debugPrint(null);
/* 244 */       debugPrint("keyStoreURL=" + this.keyStoreURL);
/* 245 */       debugPrint("keyStoreType=" + this.keyStoreType);
/* 246 */       debugPrint("keyStoreProvider=" + this.keyStoreProvider);
/* 247 */       debugPrint("keyStoreAlias=" + this.keyStoreAlias);
/* 248 */       debugPrint("keyStorePasswordURL=" + this.keyStorePasswordURL);
/* 249 */       debugPrint("privateKeyPasswordURL=" + this.privateKeyPasswordURL);
/* 250 */       debugPrint("protectedPath=" + this.protectedPath);
/* 251 */       debugPrint(null);
/*     */     } 
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
/*     */   public boolean login() throws LoginException {
/* 270 */     switch (this.status)
/*     */     
/*     */     { default:
/* 273 */         throw new LoginException("The login module is not initialized");
/*     */       
/*     */       case 1:
/*     */       case 2:
/* 277 */         if (this.token && !this.nullStream) {
/* 278 */           throw new LoginException("if keyStoreType is PKCS11 then keyStoreURL must be NONE");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 283 */         if (this.token && this.privateKeyPasswordURL != null) {
/* 284 */           throw new LoginException("if keyStoreType is PKCS11 then privateKeyPasswordURL must not be specified");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 289 */         if (this.protectedPath && (this.keyStorePasswordURL != null || this.privateKeyPasswordURL != null))
/*     */         {
/*     */           
/* 292 */           throw new LoginException("if protected is true then keyStorePasswordURL and privateKeyPasswordURL must not be specified");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 299 */         if (this.protectedPath) {
/* 300 */           getAliasAndPasswords(0);
/* 301 */         } else if (this.token) {
/* 302 */           getAliasAndPasswords(1);
/*     */         } else {
/* 304 */           getAliasAndPasswords(2);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 311 */           getKeyStoreInfo();
/*     */         } finally {
/* 313 */           if (this.privateKeyPassword != null && this.privateKeyPassword != this.keyStorePassword) {
/*     */             
/* 315 */             Arrays.fill(this.privateKeyPassword, false);
/* 316 */             this.privateKeyPassword = null;
/*     */           } 
/* 318 */           if (this.keyStorePassword != null) {
/* 319 */             Arrays.fill(this.keyStorePassword, false);
/* 320 */             this.keyStorePassword = null;
/*     */           } 
/*     */         } 
/* 323 */         this.status = 2;
/* 324 */         return true;
/*     */       case 3:
/* 326 */         break; }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getAliasAndPasswords(int paramInt) throws LoginException {
/* 333 */     if (this.callbackHandler == null) {
/*     */ 
/*     */ 
/*     */       
/* 337 */       switch (paramInt) {
/*     */         case 0:
/* 339 */           checkAlias();
/*     */           break;
/*     */         case 1:
/* 342 */           checkAlias();
/* 343 */           checkStorePass();
/*     */           break;
/*     */         case 2:
/* 346 */           checkAlias();
/* 347 */           checkStorePass();
/* 348 */           checkKeyPass();
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } else {
/*     */       NameCallback nameCallback;
/* 357 */       if (this.keyStoreAlias == null || this.keyStoreAlias.length() == 0) {
/*     */         
/* 359 */         nameCallback = new NameCallback(rb.getString("Keystore.alias."));
/*     */       } else {
/*     */         
/* 362 */         nameCallback = new NameCallback(rb.getString("Keystore.alias."), this.keyStoreAlias);
/*     */       } 
/*     */ 
/*     */       
/* 366 */       PasswordCallback passwordCallback1 = null;
/* 367 */       PasswordCallback passwordCallback2 = null;
/*     */       
/* 369 */       switch (paramInt) {
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 374 */           passwordCallback2 = new PasswordCallback(rb.getString("Private.key.password.optional."), false);
/*     */ 
/*     */         
/*     */         case 1:
/* 378 */           passwordCallback1 = new PasswordCallback(rb.getString("Keystore.password."), false);
/*     */           break;
/*     */       } 
/* 381 */       prompt(nameCallback, passwordCallback1, passwordCallback2);
/*     */     } 
/*     */     
/* 384 */     if (this.debug) {
/* 385 */       debugPrint("alias=" + this.keyStoreAlias);
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkAlias() throws LoginException {
/* 390 */     if (this.keyStoreAlias == null) {
/* 391 */       throw new LoginException("Need to specify an alias option to use KeyStoreLoginModule non-interactively.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkStorePass() throws LoginException {
/* 398 */     if (this.keyStorePasswordURL == null) {
/* 399 */       throw new LoginException("Need to specify keyStorePasswordURL option to use KeyStoreLoginModule non-interactively.");
/*     */     }
/*     */ 
/*     */     
/* 403 */     InputStream inputStream = null;
/*     */     try {
/* 405 */       inputStream = (new URL(this.keyStorePasswordURL)).openStream();
/* 406 */       this.keyStorePassword = Password.readPassword(inputStream);
/* 407 */     } catch (IOException iOException) {
/* 408 */       LoginException loginException = new LoginException("Problem accessing keystore password \"" + this.keyStorePasswordURL + "\"");
/*     */ 
/*     */       
/* 411 */       loginException.initCause(iOException);
/* 412 */       throw loginException;
/*     */     } finally {
/* 414 */       if (inputStream != null) {
/*     */         try {
/* 416 */           inputStream.close();
/* 417 */         } catch (IOException iOException) {
/* 418 */           LoginException loginException = new LoginException("Problem closing the keystore password stream");
/*     */           
/* 420 */           loginException.initCause(iOException);
/* 421 */           throw loginException;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkKeyPass() throws LoginException {
/* 428 */     if (this.privateKeyPasswordURL == null) {
/* 429 */       this.privateKeyPassword = this.keyStorePassword;
/*     */     } else {
/* 431 */       InputStream inputStream = null;
/*     */       try {
/* 433 */         inputStream = (new URL(this.privateKeyPasswordURL)).openStream();
/* 434 */         this.privateKeyPassword = Password.readPassword(inputStream);
/* 435 */       } catch (IOException iOException) {
/* 436 */         LoginException loginException = new LoginException("Problem accessing private key password \"" + this.privateKeyPasswordURL + "\"");
/*     */ 
/*     */         
/* 439 */         loginException.initCause(iOException);
/* 440 */         throw loginException;
/*     */       } finally {
/* 442 */         if (inputStream != null) {
/*     */           try {
/* 444 */             inputStream.close();
/* 445 */           } catch (IOException iOException) {
/* 446 */             LoginException loginException = new LoginException("Problem closing the private key password stream");
/*     */             
/* 448 */             loginException.initCause(iOException);
/* 449 */             throw loginException;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void prompt(NameCallback paramNameCallback, PasswordCallback paramPasswordCallback1, PasswordCallback paramPasswordCallback2) throws LoginException {
/* 461 */     if (paramPasswordCallback1 == null) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 466 */         this.callbackHandler.handle(new Callback[] { bannerCallback, paramNameCallback, this.confirmationCallback });
/*     */ 
/*     */       
/*     */       }
/* 470 */       catch (IOException iOException) {
/* 471 */         LoginException loginException = new LoginException("Problem retrieving keystore alias");
/*     */         
/* 473 */         loginException.initCause(iOException);
/* 474 */         throw loginException;
/* 475 */       } catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 476 */         throw new LoginException("Error: " + unsupportedCallbackException
/* 477 */             .getCallback().toString() + " is not available to retrieve authentication  information from the user");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 482 */       int i = this.confirmationCallback.getSelectedIndex();
/*     */       
/* 484 */       if (i == 2) {
/* 485 */         throw new LoginException("Login cancelled");
/*     */       }
/*     */       
/* 488 */       saveAlias(paramNameCallback);
/*     */     }
/* 490 */     else if (paramPasswordCallback2 == null) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 495 */         this.callbackHandler.handle(new Callback[] { bannerCallback, paramNameCallback, paramPasswordCallback1, this.confirmationCallback });
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 500 */       catch (IOException iOException) {
/* 501 */         LoginException loginException = new LoginException("Problem retrieving keystore alias and password");
/*     */         
/* 503 */         loginException.initCause(iOException);
/* 504 */         throw loginException;
/* 505 */       } catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 506 */         throw new LoginException("Error: " + unsupportedCallbackException
/* 507 */             .getCallback().toString() + " is not available to retrieve authentication  information from the user");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 512 */       int i = this.confirmationCallback.getSelectedIndex();
/*     */       
/* 514 */       if (i == 2) {
/* 515 */         throw new LoginException("Login cancelled");
/*     */       }
/*     */       
/* 518 */       saveAlias(paramNameCallback);
/* 519 */       saveStorePass(paramPasswordCallback1);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 526 */         this.callbackHandler.handle(new Callback[] { bannerCallback, paramNameCallback, paramPasswordCallback1, paramPasswordCallback2, this.confirmationCallback });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 532 */       catch (IOException iOException) {
/* 533 */         LoginException loginException = new LoginException("Problem retrieving keystore alias and passwords");
/*     */         
/* 535 */         loginException.initCause(iOException);
/* 536 */         throw loginException;
/* 537 */       } catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 538 */         throw new LoginException("Error: " + unsupportedCallbackException
/* 539 */             .getCallback().toString() + " is not available to retrieve authentication  information from the user");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 544 */       int i = this.confirmationCallback.getSelectedIndex();
/*     */       
/* 546 */       if (i == 2) {
/* 547 */         throw new LoginException("Login cancelled");
/*     */       }
/*     */       
/* 550 */       saveAlias(paramNameCallback);
/* 551 */       saveStorePass(paramPasswordCallback1);
/* 552 */       saveKeyPass(paramPasswordCallback2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveAlias(NameCallback paramNameCallback) {
/* 557 */     this.keyStoreAlias = paramNameCallback.getName();
/*     */   }
/*     */   
/*     */   private void saveStorePass(PasswordCallback paramPasswordCallback) {
/* 561 */     this.keyStorePassword = paramPasswordCallback.getPassword();
/* 562 */     if (this.keyStorePassword == null)
/*     */     {
/* 564 */       this.keyStorePassword = new char[0];
/*     */     }
/* 566 */     paramPasswordCallback.clearPassword();
/*     */   }
/*     */   
/*     */   private void saveKeyPass(PasswordCallback paramPasswordCallback) {
/* 570 */     this.privateKeyPassword = paramPasswordCallback.getPassword();
/* 571 */     if (this.privateKeyPassword == null || this.privateKeyPassword.length == 0)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 576 */       this.privateKeyPassword = this.keyStorePassword;
/*     */     }
/* 578 */     paramPasswordCallback.clearPassword();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getKeyStoreInfo() throws LoginException {
/*     */     try {
/* 586 */       if (this.keyStoreProvider == null) {
/* 587 */         this.keyStore = KeyStore.getInstance(this.keyStoreType);
/*     */       } else {
/* 589 */         this
/* 590 */           .keyStore = KeyStore.getInstance(this.keyStoreType, this.keyStoreProvider);
/*     */       } 
/* 592 */     } catch (KeyStoreException keyStoreException) {
/* 593 */       LoginException loginException = new LoginException("The specified keystore type was not available");
/*     */       
/* 595 */       loginException.initCause(keyStoreException);
/* 596 */       throw loginException;
/* 597 */     } catch (NoSuchProviderException noSuchProviderException) {
/* 598 */       LoginException loginException = new LoginException("The specified keystore provider was not available");
/*     */       
/* 600 */       loginException.initCause(noSuchProviderException);
/* 601 */       throw loginException;
/*     */     } 
/*     */ 
/*     */     
/* 605 */     InputStream inputStream = null;
/*     */     try {
/* 607 */       if (this.nullStream) {
/*     */         
/* 609 */         this.keyStore.load(null, this.keyStorePassword);
/*     */       } else {
/* 611 */         inputStream = (new URL(this.keyStoreURL)).openStream();
/* 612 */         this.keyStore.load(inputStream, this.keyStorePassword);
/*     */       } 
/* 614 */     } catch (MalformedURLException malformedURLException) {
/* 615 */       LoginException loginException = new LoginException("Incorrect keyStoreURL option");
/*     */       
/* 617 */       loginException.initCause(malformedURLException);
/* 618 */       throw loginException;
/* 619 */     } catch (GeneralSecurityException generalSecurityException) {
/* 620 */       LoginException loginException = new LoginException("Error initializing keystore");
/*     */       
/* 622 */       loginException.initCause(generalSecurityException);
/* 623 */       throw loginException;
/* 624 */     } catch (IOException iOException) {
/* 625 */       LoginException loginException = new LoginException("Error initializing keystore");
/*     */       
/* 627 */       loginException.initCause(iOException);
/* 628 */       throw loginException;
/*     */     } finally {
/* 630 */       if (inputStream != null) {
/*     */         try {
/* 632 */           inputStream.close();
/* 633 */         } catch (IOException iOException) {
/* 634 */           LoginException loginException = new LoginException("Error initializing keystore");
/*     */           
/* 636 */           loginException.initCause(iOException);
/* 637 */           throw loginException;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 644 */       this
/* 645 */         .fromKeyStore = this.keyStore.getCertificateChain(this.keyStoreAlias);
/* 646 */       if (this.fromKeyStore == null || this.fromKeyStore.length == 0 || !(this.fromKeyStore[0] instanceof X509Certificate))
/*     */       {
/*     */ 
/*     */         
/* 650 */         throw new FailedLoginException("Unable to find X.509 certificate chain in keystore");
/*     */       }
/*     */       
/* 653 */       LinkedList<Certificate> linkedList = new LinkedList();
/* 654 */       for (byte b = 0; b < this.fromKeyStore.length; b++) {
/* 655 */         linkedList.add(this.fromKeyStore[b]);
/*     */       }
/*     */       
/* 658 */       CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
/* 659 */       this
/* 660 */         .certP = certificateFactory.generateCertPath(linkedList);
/*     */     }
/* 662 */     catch (KeyStoreException keyStoreException) {
/* 663 */       LoginException loginException = new LoginException("Error using keystore");
/* 664 */       loginException.initCause(keyStoreException);
/* 665 */       throw loginException;
/* 666 */     } catch (CertificateException certificateException) {
/* 667 */       LoginException loginException = new LoginException("Error: X.509 Certificate type unavailable");
/*     */       
/* 669 */       loginException.initCause(certificateException);
/* 670 */       throw loginException;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 675 */       X509Certificate x509Certificate = (X509Certificate)this.fromKeyStore[0];
/* 676 */       this
/* 677 */         .principal = new X500Principal(x509Certificate.getSubjectDN().getName());
/*     */ 
/*     */       
/* 680 */       Key key = this.keyStore.getKey(this.keyStoreAlias, this.privateKeyPassword);
/* 681 */       if (key == null || !(key instanceof PrivateKey))
/*     */       {
/*     */         
/* 684 */         throw new FailedLoginException("Unable to recover key from keystore");
/*     */       }
/*     */ 
/*     */       
/* 688 */       this.privateCredential = new X500PrivateCredential(x509Certificate, (PrivateKey)key, this.keyStoreAlias);
/*     */     }
/* 690 */     catch (KeyStoreException keyStoreException) {
/* 691 */       LoginException loginException = new LoginException("Error using keystore");
/* 692 */       loginException.initCause(keyStoreException);
/* 693 */       throw loginException;
/* 694 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 695 */       LoginException loginException = new LoginException("Error using keystore");
/* 696 */       loginException.initCause(noSuchAlgorithmException);
/* 697 */       throw loginException;
/* 698 */     } catch (UnrecoverableKeyException unrecoverableKeyException) {
/* 699 */       FailedLoginException failedLoginException = new FailedLoginException("Unable to recover key from keystore");
/*     */       
/* 701 */       failedLoginException.initCause(unrecoverableKeyException);
/* 702 */       throw failedLoginException;
/*     */     } 
/* 704 */     if (this.debug) {
/* 705 */       debugPrint("principal=" + this.principal + "\n certificate=" + this.privateCredential
/*     */           
/* 707 */           .getCertificate() + "\n alias =" + this.privateCredential
/* 708 */           .getAlias());
/*     */     }
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
/*     */   public boolean commit() throws LoginException {
/* 742 */     switch (this.status) {
/*     */       
/*     */       default:
/* 745 */         throw new LoginException("The login module is not initialized");
/*     */       case 1:
/* 747 */         logoutInternal();
/* 748 */         throw new LoginException("Authentication failed");
/*     */       case 2:
/* 750 */         if (commitInternal()) {
/* 751 */           return true;
/*     */         }
/* 753 */         logoutInternal();
/* 754 */         throw new LoginException("Unable to retrieve certificates");
/*     */       case 3:
/*     */         break;
/* 757 */     }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean commitInternal() throws LoginException {
/* 765 */     if (this.subject.isReadOnly()) {
/* 766 */       throw new LoginException("Subject is set readonly");
/*     */     }
/* 768 */     this.subject.getPrincipals().add(this.principal);
/* 769 */     this.subject.getPublicCredentials().add(this.certP);
/* 770 */     this.subject.getPrivateCredentials().add(this.privateCredential);
/* 771 */     this.status = 3;
/* 772 */     return true;
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
/*     */   public boolean abort() throws LoginException {
/* 800 */     switch (this.status)
/*     */     
/*     */     { default:
/* 803 */         return false;
/*     */       case 1:
/* 805 */         return false;
/*     */       case 2:
/* 807 */         logoutInternal();
/* 808 */         return true;
/*     */       case 3:
/* 810 */         break; }  logoutInternal();
/* 811 */     return true;
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
/*     */   public boolean logout() throws LoginException {
/* 833 */     if (this.debug)
/* 834 */       debugPrint("Entering logout " + this.status); 
/* 835 */     switch (this.status)
/*     */     { case 0:
/* 837 */         throw new LoginException("The login module is not initialized");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 845 */         return false;
/*     */       case 3:
/* 847 */         break; }  logoutInternal();
/* 848 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void logoutInternal() throws LoginException {
/* 853 */     if (this.debug) {
/* 854 */       debugPrint("Entering logoutInternal");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 859 */     LoginException loginException = null;
/* 860 */     Provider provider = this.keyStore.getProvider();
/* 861 */     if (provider instanceof AuthProvider) {
/* 862 */       AuthProvider authProvider = (AuthProvider)provider;
/*     */       try {
/* 864 */         authProvider.logout();
/* 865 */         if (this.debug) {
/* 866 */           debugPrint("logged out of KeyStore AuthProvider");
/*     */         }
/* 868 */       } catch (LoginException loginException1) {
/*     */         
/* 870 */         loginException = loginException1;
/*     */       } 
/*     */     } 
/*     */     
/* 874 */     if (this.subject.isReadOnly()) {
/*     */ 
/*     */       
/* 877 */       this.principal = null;
/* 878 */       this.certP = null;
/* 879 */       this.status = 1;
/*     */       
/* 881 */       Iterator<Object> iterator = this.subject.getPrivateCredentials().iterator();
/* 882 */       while (iterator.hasNext()) {
/* 883 */         Destroyable destroyable = (Destroyable)iterator.next();
/* 884 */         if (this.privateCredential.equals(destroyable)) {
/* 885 */           this.privateCredential = null;
/*     */           try {
/* 887 */             ((Destroyable)destroyable).destroy();
/* 888 */             if (this.debug) {
/* 889 */               debugPrint("Destroyed private credential, " + destroyable
/* 890 */                   .getClass().getName());
/*     */             }
/* 892 */           } catch (DestroyFailedException destroyFailedException) {
/*     */ 
/*     */             
/* 895 */             LoginException loginException1 = new LoginException("Unable to destroy private credential, " + destroyable.getClass().getName());
/* 896 */             loginException1.initCause(destroyFailedException);
/* 897 */             throw loginException1;
/*     */           } 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 905 */       throw new LoginException("Unable to remove Principal (X500Principal ) and public credential (certificatepath) from read-only Subject");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 911 */     if (this.principal != null) {
/* 912 */       this.subject.getPrincipals().remove(this.principal);
/* 913 */       this.principal = null;
/*     */     } 
/* 915 */     if (this.certP != null) {
/* 916 */       this.subject.getPublicCredentials().remove(this.certP);
/* 917 */       this.certP = null;
/*     */     } 
/* 919 */     if (this.privateCredential != null) {
/* 920 */       this.subject.getPrivateCredentials().remove(this.privateCredential);
/* 921 */       this.privateCredential = null;
/*     */     } 
/*     */ 
/*     */     
/* 925 */     if (loginException != null) {
/* 926 */       throw loginException;
/*     */     }
/* 928 */     this.status = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void debugPrint(String paramString) {
/* 933 */     if (paramString == null) {
/* 934 */       System.err.println();
/*     */     } else {
/* 936 */       System.err.println("Debug KeyStoreLoginModule: " + paramString);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/module/KeyStoreLoginModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */