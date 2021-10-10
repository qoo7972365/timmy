/*     */ package sun.security.jca;
/*     */ 
/*     */ import java.security.Provider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Providers
/*     */ {
/*  39 */   private static final ThreadLocal<ProviderList> threadLists = new InheritableThreadLocal<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static volatile int threadListsUsed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static volatile ProviderList providerList = ProviderList.EMPTY; static {
/*  54 */     providerList = ProviderList.fromSecurityProperties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     jarVerificationProviders = new String[] { "sun.security.provider.Sun", "sun.security.rsa.SunRsaSign", "sun.security.ec.SunEC", "sun.security.provider.VerificationProvider" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String BACKUP_PROVIDER_CLASSNAME = "sun.security.provider.VerificationProvider";
/*     */ 
/*     */   
/*     */   private static final String[] jarVerificationProviders;
/*     */ 
/*     */ 
/*     */   
/*     */   public static Provider getSunProvider() {
/*     */     try {
/*  97 */       Class<?> clazz = Class.forName(jarVerificationProviders[0]);
/*  98 */       return (Provider)clazz.newInstance();
/*  99 */     } catch (Exception exception) {
/*     */       try {
/* 101 */         Class<?> clazz = Class.forName("sun.security.provider.VerificationProvider");
/* 102 */         return (Provider)clazz.newInstance();
/* 103 */       } catch (Exception exception1) {
/* 104 */         throw new RuntimeException("Sun provider not found", exception);
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
/*     */   public static Object startJarVerification() {
/* 116 */     ProviderList providerList1 = getProviderList();
/* 117 */     ProviderList providerList2 = providerList1.getJarList(jarVerificationProviders);
/*     */     
/* 119 */     return beginThreadProviderList(providerList2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void stopJarVerification(Object paramObject) {
/* 127 */     endThreadProviderList((ProviderList)paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ProviderList getProviderList() {
/* 135 */     ProviderList providerList = getThreadProviderList();
/* 136 */     if (providerList == null) {
/* 137 */       providerList = getSystemProviderList();
/*     */     }
/* 139 */     return providerList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setProviderList(ProviderList paramProviderList) {
/* 147 */     if (getThreadProviderList() == null) {
/* 148 */       setSystemProviderList(paramProviderList);
/*     */     } else {
/* 150 */       changeThreadProviderList(paramProviderList);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ProviderList getFullProviderList() {
/* 161 */     synchronized (Providers.class) {
/* 162 */       ProviderList providerList = getThreadProviderList();
/* 163 */       if (providerList != null) {
/* 164 */         ProviderList providerList3 = providerList.removeInvalid();
/* 165 */         if (providerList3 != providerList) {
/* 166 */           changeThreadProviderList(providerList3);
/* 167 */           providerList = providerList3;
/*     */         } 
/* 169 */         return providerList;
/*     */       } 
/*     */     } 
/* 172 */     ProviderList providerList1 = getSystemProviderList();
/* 173 */     ProviderList providerList2 = providerList1.removeInvalid();
/* 174 */     if (providerList2 != providerList1) {
/* 175 */       setSystemProviderList(providerList2);
/* 176 */       providerList1 = providerList2;
/*     */     } 
/* 178 */     return providerList1;
/*     */   }
/*     */   
/*     */   private static ProviderList getSystemProviderList() {
/* 182 */     return providerList;
/*     */   }
/*     */   
/*     */   private static void setSystemProviderList(ProviderList paramProviderList) {
/* 186 */     providerList = paramProviderList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ProviderList getThreadProviderList() {
/* 192 */     if (threadListsUsed == 0) {
/* 193 */       return null;
/*     */     }
/* 195 */     return threadLists.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void changeThreadProviderList(ProviderList paramProviderList) {
/* 202 */     threadLists.set(paramProviderList);
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
/*     */   public static synchronized ProviderList beginThreadProviderList(ProviderList paramProviderList) {
/* 222 */     if (ProviderList.debug != null) {
/* 223 */       ProviderList.debug.println("ThreadLocal providers: " + paramProviderList);
/*     */     }
/* 225 */     ProviderList providerList = threadLists.get();
/* 226 */     threadListsUsed++;
/* 227 */     threadLists.set(paramProviderList);
/* 228 */     return providerList;
/*     */   }
/*     */   
/*     */   public static synchronized void endThreadProviderList(ProviderList paramProviderList) {
/* 232 */     if (paramProviderList == null) {
/* 233 */       if (ProviderList.debug != null) {
/* 234 */         ProviderList.debug.println("Disabling ThreadLocal providers");
/*     */       }
/* 236 */       threadLists.remove();
/*     */     } else {
/* 238 */       if (ProviderList.debug != null) {
/* 239 */         ProviderList.debug
/* 240 */           .println("Restoring previous ThreadLocal providers: " + paramProviderList);
/*     */       }
/* 242 */       threadLists.set(paramProviderList);
/*     */     } 
/* 244 */     threadListsUsed--;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jca/Providers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */