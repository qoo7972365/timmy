/*     */ package java.security;
/*     */ 
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import sun.security.jca.GetInstance;
/*     */ import sun.security.jca.JCAUtil;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class KeyPairGenerator
/*     */   extends KeyPairGeneratorSpi
/*     */ {
/* 131 */   private static final Debug pdebug = Debug.getInstance("provider", "Provider");
/* 132 */   private static final boolean skipDebug = (
/* 133 */     Debug.isOn("engine=") && !Debug.isOn("keypairgenerator"));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String algorithm;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Provider provider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected KeyPairGenerator(String paramString) {
/* 150 */     this.algorithm = paramString;
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
/*     */   public String getAlgorithm() {
/* 163 */     return this.algorithm;
/*     */   }
/*     */ 
/*     */   
/*     */   private static KeyPairGenerator getInstance(GetInstance.Instance paramInstance, String paramString) {
/*     */     KeyPairGenerator keyPairGenerator;
/* 169 */     if (paramInstance.impl instanceof KeyPairGenerator) {
/* 170 */       keyPairGenerator = (KeyPairGenerator)paramInstance.impl;
/*     */     } else {
/* 172 */       KeyPairGeneratorSpi keyPairGeneratorSpi = (KeyPairGeneratorSpi)paramInstance.impl;
/* 173 */       keyPairGenerator = new Delegate(keyPairGeneratorSpi, paramString);
/*     */     } 
/* 175 */     keyPairGenerator.provider = paramInstance.provider;
/*     */     
/* 177 */     if (!skipDebug && pdebug != null) {
/* 178 */       pdebug.println("KeyPairGenerator." + paramString + " algorithm from: " + keyPairGenerator.provider
/* 179 */           .getName());
/*     */     }
/*     */     
/* 182 */     return keyPairGenerator;
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
/*     */   public static KeyPairGenerator getInstance(String paramString) throws NoSuchAlgorithmException {
/* 215 */     List<Provider.Service> list = GetInstance.getServices("KeyPairGenerator", paramString);
/* 216 */     Iterator<Provider.Service> iterator = list.iterator();
/* 217 */     if (!iterator.hasNext()) {
/* 218 */       throw new NoSuchAlgorithmException(paramString + " KeyPairGenerator not available");
/*     */     }
/*     */ 
/*     */     
/* 222 */     NoSuchAlgorithmException noSuchAlgorithmException = null;
/*     */     while (true) {
/* 224 */       Provider.Service service = iterator.next();
/*     */       
/*     */       try {
/* 227 */         GetInstance.Instance instance = GetInstance.getInstance(service, KeyPairGeneratorSpi.class);
/* 228 */         if (instance.impl instanceof KeyPairGenerator) {
/* 229 */           return getInstance(instance, paramString);
/*     */         }
/* 231 */         return new Delegate(instance, iterator, paramString);
/*     */       }
/* 233 */       catch (NoSuchAlgorithmException noSuchAlgorithmException1) {
/* 234 */         if (noSuchAlgorithmException == null) {
/* 235 */           noSuchAlgorithmException = noSuchAlgorithmException1;
/*     */         }
/*     */         
/* 238 */         if (!iterator.hasNext()) {
/* 239 */           throw noSuchAlgorithmException;
/*     */         }
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyPairGenerator getInstance(String paramString1, String paramString2) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 279 */     GetInstance.Instance instance = GetInstance.getInstance("KeyPairGenerator", KeyPairGeneratorSpi.class, paramString1, paramString2);
/*     */     
/* 281 */     return getInstance(instance, paramString1);
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
/*     */   
/*     */   public static KeyPairGenerator getInstance(String paramString, Provider paramProvider) throws NoSuchAlgorithmException {
/* 315 */     GetInstance.Instance instance = GetInstance.getInstance("KeyPairGenerator", KeyPairGeneratorSpi.class, paramString, paramProvider);
/*     */     
/* 317 */     return getInstance(instance, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 326 */     disableFailover();
/* 327 */     return this.provider;
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
/*     */   void disableFailover() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(int paramInt) {
/* 351 */     initialize(paramInt, JCAUtil.getSecureRandom());
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
/*     */   public void initialize(int paramInt, SecureRandom paramSecureRandom) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 411 */     initialize(paramAlgorithmParameterSpec, JCAUtil.getSecureRandom());
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
/*     */   public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final KeyPair genKeyPair() {
/* 470 */     return generateKeyPair();
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
/*     */   public KeyPair generateKeyPair() {
/* 501 */     return null;
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
/*     */   private static final class Delegate
/*     */     extends KeyPairGenerator
/*     */   {
/*     */     private volatile KeyPairGeneratorSpi spi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 546 */     private final Object lock = new Object();
/*     */     
/*     */     private Iterator<Provider.Service> serviceIterator;
/*     */     
/*     */     private static final int I_NONE = 1;
/*     */     
/*     */     private static final int I_SIZE = 2;
/*     */     
/*     */     private static final int I_PARAMS = 3;
/*     */     private int initType;
/*     */     private int initKeySize;
/*     */     private AlgorithmParameterSpec initParams;
/*     */     private SecureRandom initRandom;
/*     */     
/*     */     Delegate(KeyPairGeneratorSpi param1KeyPairGeneratorSpi, String param1String) {
/* 561 */       super(param1String);
/* 562 */       this.spi = param1KeyPairGeneratorSpi;
/*     */     }
/*     */ 
/*     */     
/*     */     Delegate(GetInstance.Instance param1Instance, Iterator<Provider.Service> param1Iterator, String param1String) {
/* 567 */       super(param1String);
/* 568 */       this.spi = (KeyPairGeneratorSpi)param1Instance.impl;
/* 569 */       this.provider = param1Instance.provider;
/* 570 */       this.serviceIterator = param1Iterator;
/* 571 */       this.initType = 1;
/*     */       
/* 573 */       if (!KeyPairGenerator.skipDebug && KeyPairGenerator.pdebug != null) {
/* 574 */         KeyPairGenerator.pdebug.println("KeyPairGenerator." + param1String + " algorithm from: " + this.provider
/* 575 */             .getName());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private KeyPairGeneratorSpi nextSpi(KeyPairGeneratorSpi param1KeyPairGeneratorSpi, boolean param1Boolean) {
/* 587 */       synchronized (this.lock) {
/*     */ 
/*     */         
/* 590 */         if (param1KeyPairGeneratorSpi != null && param1KeyPairGeneratorSpi != this.spi) {
/* 591 */           return this.spi;
/*     */         }
/* 593 */         if (this.serviceIterator == null) {
/* 594 */           return null;
/*     */         }
/* 596 */         while (this.serviceIterator.hasNext()) {
/* 597 */           Provider.Service service = this.serviceIterator.next();
/*     */           try {
/* 599 */             Object object = service.newInstance(null);
/*     */             
/* 601 */             if (!(object instanceof KeyPairGeneratorSpi)) {
/*     */               continue;
/*     */             }
/* 604 */             if (object instanceof KeyPairGenerator) {
/*     */               continue;
/*     */             }
/* 607 */             KeyPairGeneratorSpi keyPairGeneratorSpi = (KeyPairGeneratorSpi)object;
/* 608 */             if (param1Boolean) {
/* 609 */               if (this.initType == 2) {
/* 610 */                 keyPairGeneratorSpi.initialize(this.initKeySize, this.initRandom);
/* 611 */               } else if (this.initType == 3) {
/* 612 */                 keyPairGeneratorSpi.initialize(this.initParams, this.initRandom);
/* 613 */               } else if (this.initType != 1) {
/* 614 */                 throw new AssertionError("KeyPairGenerator initType: " + this.initType);
/*     */               } 
/*     */             }
/*     */             
/* 618 */             this.provider = service.getProvider();
/* 619 */             this.spi = keyPairGeneratorSpi;
/* 620 */             return keyPairGeneratorSpi;
/* 621 */           } catch (Exception exception) {}
/*     */         } 
/*     */ 
/*     */         
/* 625 */         disableFailover();
/* 626 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     void disableFailover() {
/* 631 */       this.serviceIterator = null;
/* 632 */       this.initType = 0;
/* 633 */       this.initParams = null;
/* 634 */       this.initRandom = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void initialize(int param1Int, SecureRandom param1SecureRandom) {
/* 639 */       if (this.serviceIterator == null) {
/* 640 */         this.spi.initialize(param1Int, param1SecureRandom);
/*     */         return;
/*     */       } 
/* 643 */       RuntimeException runtimeException = null;
/* 644 */       KeyPairGeneratorSpi keyPairGeneratorSpi = this.spi;
/*     */       while (true) {
/*     */         try {
/* 647 */           keyPairGeneratorSpi.initialize(param1Int, param1SecureRandom);
/* 648 */           this.initType = 2;
/* 649 */           this.initKeySize = param1Int;
/* 650 */           this.initParams = null;
/* 651 */           this.initRandom = param1SecureRandom;
/*     */           return;
/* 653 */         } catch (RuntimeException runtimeException1) {
/* 654 */           if (runtimeException == null) {
/* 655 */             runtimeException = runtimeException1;
/*     */           }
/* 657 */           keyPairGeneratorSpi = nextSpi(keyPairGeneratorSpi, false);
/*     */           
/* 659 */           if (keyPairGeneratorSpi == null)
/* 660 */             throw runtimeException; 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void initialize(AlgorithmParameterSpec param1AlgorithmParameterSpec, SecureRandom param1SecureRandom) throws InvalidAlgorithmParameterException {
/* 666 */       if (this.serviceIterator == null) {
/* 667 */         this.spi.initialize(param1AlgorithmParameterSpec, param1SecureRandom);
/*     */         return;
/*     */       } 
/* 670 */       Exception exception = null;
/* 671 */       KeyPairGeneratorSpi keyPairGeneratorSpi = this.spi;
/*     */       while (true) {
/*     */         try {
/* 674 */           keyPairGeneratorSpi.initialize(param1AlgorithmParameterSpec, param1SecureRandom);
/* 675 */           this.initType = 3;
/* 676 */           this.initKeySize = 0;
/* 677 */           this.initParams = param1AlgorithmParameterSpec;
/* 678 */           this.initRandom = param1SecureRandom;
/*     */           return;
/* 680 */         } catch (Exception exception1) {
/* 681 */           if (exception == null) {
/* 682 */             exception = exception1;
/*     */           }
/* 684 */           keyPairGeneratorSpi = nextSpi(keyPairGeneratorSpi, false);
/*     */           
/* 686 */           if (keyPairGeneratorSpi == null) {
/* 687 */             if (exception instanceof RuntimeException) {
/* 688 */               throw (RuntimeException)exception;
/*     */             }
/*     */             
/* 691 */             throw (InvalidAlgorithmParameterException)exception;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } public KeyPair generateKeyPair() {
/* 696 */       if (this.serviceIterator == null) {
/* 697 */         return this.spi.generateKeyPair();
/*     */       }
/* 699 */       RuntimeException runtimeException = null;
/* 700 */       KeyPairGeneratorSpi keyPairGeneratorSpi = this.spi;
/*     */       while (true) {
/*     */         try {
/* 703 */           return keyPairGeneratorSpi.generateKeyPair();
/* 704 */         } catch (RuntimeException runtimeException1) {
/* 705 */           if (runtimeException == null) {
/* 706 */             runtimeException = runtimeException1;
/*     */           }
/* 708 */           keyPairGeneratorSpi = nextSpi(keyPairGeneratorSpi, true);
/*     */           
/* 710 */           if (keyPairGeneratorSpi == null)
/* 711 */             throw runtimeException; 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/KeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */