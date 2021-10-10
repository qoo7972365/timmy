/*     */ package javax.security.sasl;
/*     */ 
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Sasl
/*     */ {
/*     */   public static final String QOP = "javax.security.sasl.qop";
/*     */   public static final String STRENGTH = "javax.security.sasl.strength";
/*     */   public static final String SERVER_AUTH = "javax.security.sasl.server.authentication";
/*     */   public static final String BOUND_SERVER_NAME = "javax.security.sasl.bound.server.name";
/*     */   public static final String MAX_BUFFER = "javax.security.sasl.maxbuffer";
/*     */   public static final String RAW_SEND_SIZE = "javax.security.sasl.rawsendsize";
/*     */   public static final String REUSE = "javax.security.sasl.reuse";
/*     */   public static final String POLICY_NOPLAINTEXT = "javax.security.sasl.policy.noplaintext";
/*     */   public static final String POLICY_NOACTIVE = "javax.security.sasl.policy.noactive";
/*     */   public static final String POLICY_NODICTIONARY = "javax.security.sasl.policy.nodictionary";
/*     */   public static final String POLICY_NOANONYMOUS = "javax.security.sasl.policy.noanonymous";
/*     */   public static final String POLICY_FORWARD_SECRECY = "javax.security.sasl.policy.forward";
/*     */   public static final String POLICY_PASS_CREDENTIALS = "javax.security.sasl.policy.credentials";
/*     */   public static final String CREDENTIALS = "javax.security.sasl.credentials";
/*     */   
/*     */   public static SaslClient createSaslClient(String[] paramArrayOfString, String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/* 361 */     SaslClient saslClient = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 367 */       String str; if ((str = paramArrayOfString[b]) == null) {
/* 368 */         throw new NullPointerException("Mechanism name cannot be null");
/*     */       }
/* 370 */       if (str.length() != 0) {
/*     */ 
/*     */         
/* 373 */         String str1 = "SaslClientFactory." + str;
/* 374 */         Provider[] arrayOfProvider = Security.getProviders(str1);
/* 375 */         for (byte b1 = 0; arrayOfProvider != null && b1 < arrayOfProvider.length; b1++) {
/* 376 */           String str2 = arrayOfProvider[b1].getProperty(str1);
/* 377 */           if (str2 != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 382 */             SaslClientFactory saslClientFactory = (SaslClientFactory)loadFactory(arrayOfProvider[b1], str2);
/* 383 */             if (saslClientFactory != null) {
/* 384 */               saslClient = saslClientFactory.createSaslClient(new String[] { paramArrayOfString[b] }, paramString1, paramString2, paramString3, paramMap, paramCallbackHandler);
/*     */ 
/*     */               
/* 387 */               if (saslClient != null)
/* 388 */                 return saslClient; 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 394 */     return null;
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
/*     */   private static Object loadFactory(Provider paramProvider, String paramString) throws SaslException {
/*     */     try {
/* 409 */       ClassLoader classLoader = paramProvider.getClass().getClassLoader();
/*     */       
/* 411 */       Class<?> clazz = Class.forName(paramString, true, classLoader);
/* 412 */       return clazz.newInstance();
/* 413 */     } catch (ClassNotFoundException classNotFoundException) {
/* 414 */       throw new SaslException("Cannot load class " + paramString, classNotFoundException);
/* 415 */     } catch (InstantiationException instantiationException) {
/* 416 */       throw new SaslException("Cannot instantiate class " + paramString, instantiationException);
/* 417 */     } catch (IllegalAccessException illegalAccessException) {
/* 418 */       throw new SaslException("Cannot access class " + paramString, illegalAccessException);
/* 419 */     } catch (SecurityException securityException) {
/* 420 */       throw new SaslException("Cannot access class " + paramString, securityException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SaslServer createSaslServer(String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/* 504 */     SaslServer saslServer = null;
/*     */ 
/*     */ 
/*     */     
/* 508 */     if (paramString1 == null)
/* 509 */       throw new NullPointerException("Mechanism name cannot be null"); 
/* 510 */     if (paramString1.length() == 0) {
/* 511 */       return null;
/*     */     }
/*     */     
/* 514 */     String str = "SaslServerFactory." + paramString1;
/* 515 */     Provider[] arrayOfProvider = Security.getProviders(str);
/* 516 */     for (byte b = 0; arrayOfProvider != null && b < arrayOfProvider.length; b++) {
/* 517 */       String str1 = arrayOfProvider[b].getProperty(str);
/* 518 */       if (str1 == null) {
/* 519 */         throw new SaslException("Provider does not support " + str);
/*     */       }
/*     */       
/* 522 */       SaslServerFactory saslServerFactory = (SaslServerFactory)loadFactory(arrayOfProvider[b], str1);
/* 523 */       if (saslServerFactory != null) {
/* 524 */         saslServer = saslServerFactory.createSaslServer(paramString1, paramString2, paramString3, paramMap, paramCallbackHandler);
/*     */         
/* 526 */         if (saslServer != null) {
/* 527 */           return saslServer;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 532 */     return null;
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
/*     */   public static Enumeration<SaslClientFactory> getSaslClientFactories() {
/* 544 */     Set<Object> set = getFactories("SaslClientFactory");
/* 545 */     final Iterator iter = set.iterator();
/* 546 */     return new Enumeration<SaslClientFactory>() {
/*     */         public boolean hasMoreElements() {
/* 548 */           return iter.hasNext();
/*     */         }
/*     */         public SaslClientFactory nextElement() {
/* 551 */           return iter.next();
/*     */         }
/*     */       };
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
/*     */   public static Enumeration<SaslServerFactory> getSaslServerFactories() {
/* 565 */     Set<Object> set = getFactories("SaslServerFactory");
/* 566 */     final Iterator iter = set.iterator();
/* 567 */     return new Enumeration<SaslServerFactory>() {
/*     */         public boolean hasMoreElements() {
/* 569 */           return iter.hasNext();
/*     */         }
/*     */         public SaslServerFactory nextElement() {
/* 572 */           return iter.next();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static Set<Object> getFactories(String paramString) {
/* 578 */     HashSet<Object> hashSet = new HashSet();
/*     */     
/* 580 */     if (paramString == null || paramString.length() == 0 || paramString
/* 581 */       .endsWith(".")) {
/* 582 */       return hashSet;
/*     */     }
/*     */ 
/*     */     
/* 586 */     Provider[] arrayOfProvider = Security.getProviders();
/* 587 */     HashSet<String> hashSet1 = new HashSet();
/*     */ 
/*     */     
/* 590 */     for (byte b = 0; b < arrayOfProvider.length; b++) {
/* 591 */       hashSet1.clear();
/*     */ 
/*     */       
/* 594 */       for (Enumeration<Object> enumeration = arrayOfProvider[b].keys(); enumeration.hasMoreElements(); ) {
/* 595 */         String str = (String)enumeration.nextElement();
/* 596 */         if (str.startsWith(paramString))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 603 */           if (str.indexOf(" ") < 0) {
/* 604 */             String str1 = arrayOfProvider[b].getProperty(str);
/* 605 */             if (!hashSet1.contains(str1)) {
/* 606 */               hashSet1.add(str1);
/*     */               try {
/* 608 */                 Object object = loadFactory(arrayOfProvider[b], str1);
/* 609 */                 if (object != null) {
/* 610 */                   hashSet.add(object);
/*     */                 }
/* 612 */               } catch (Exception exception) {}
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 619 */     return Collections.unmodifiableSet(hashSet);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/sasl/Sasl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */