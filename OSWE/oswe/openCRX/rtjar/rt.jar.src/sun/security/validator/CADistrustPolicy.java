/*     */ package sun.security.validator;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Security;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.EnumSet;
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
/*     */ enum CADistrustPolicy
/*     */ {
/*  48 */   SYMANTEC_TLS
/*     */   {
/*     */     void checkDistrust(String param1String, X509Certificate[] param1ArrayOfX509Certificate) throws ValidatorException {
/*  51 */       if (!param1String.equals("tls server")) {
/*     */         return;
/*     */       }
/*  54 */       SymantecTLSPolicy.checkDistrust(param1ArrayOfX509Certificate);
/*     */     }
/*     */   };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final EnumSet<CADistrustPolicy> POLICIES;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  71 */     POLICIES = parseProperty();
/*     */   } private static EnumSet<CADistrustPolicy> parseProperty() {
/*  73 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run()
/*     */           {
/*  77 */             return Security.getProperty("jdk.security.caDistrustPolicies");
/*     */           }
/*     */         });
/*     */     
/*  81 */     EnumSet<CADistrustPolicy> enumSet = EnumSet.noneOf(CADistrustPolicy.class);
/*     */     
/*  83 */     if (str == null || str.isEmpty()) {
/*  84 */       return enumSet;
/*     */     }
/*  86 */     String[] arrayOfString = str.split(",");
/*  87 */     for (String str1 : arrayOfString) {
/*  88 */       str1 = str1.trim();
/*     */       
/*     */       try {
/*  91 */         CADistrustPolicy cADistrustPolicy = Enum.<CADistrustPolicy>valueOf(CADistrustPolicy.class, str1);
/*  92 */         enumSet.add(cADistrustPolicy);
/*  93 */       } catch (IllegalArgumentException illegalArgumentException) {
/*     */         
/*  95 */         Debug debug = Debug.getInstance("certpath");
/*  96 */         if (debug != null) {
/*  97 */           debug.println("Unknown value for the jdk.security.caDistrustPolicies property: " + str1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 103 */     return enumSet;
/*     */   }
/*     */   
/*     */   abstract void checkDistrust(String paramString, X509Certificate[] paramArrayOfX509Certificate) throws ValidatorException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/validator/CADistrustPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */