/*     */ package sun.net;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Security;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InetAddressCachePolicy
/*     */ {
/*     */   private static final String cachePolicyProp = "networkaddress.cache.ttl";
/*     */   private static final String cachePolicyPropFallback = "sun.net.inetaddr.ttl";
/*     */   private static final String negativeCachePolicyProp = "networkaddress.cache.negative.ttl";
/*     */   private static final String negativeCachePolicyPropFallback = "sun.net.inetaddr.negative.ttl";
/*     */   public static final int FOREVER = -1;
/*     */   public static final int NEVER = 0;
/*     */   public static final int DEFAULT_POSITIVE = 30;
/*  59 */   private static int cachePolicy = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static int negativeCachePolicy = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean propertySet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean propertyNegativeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  88 */     Integer integer = AccessController.<Integer>doPrivileged(new PrivilegedAction<Integer>()
/*     */         {
/*     */           public Integer run() {
/*     */             try {
/*  92 */               String str = Security.getProperty("networkaddress.cache.ttl");
/*  93 */               if (str != null) {
/*  94 */                 return Integer.valueOf(str);
/*     */               }
/*  96 */             } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 101 */               String str = System.getProperty("sun.net.inetaddr.ttl");
/* 102 */               if (str != null) {
/* 103 */                 return Integer.decode(str);
/*     */               }
/* 105 */             } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */             
/* 108 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 112 */     if (integer != null) {
/* 113 */       cachePolicy = integer.intValue();
/* 114 */       if (cachePolicy < 0) {
/* 115 */         cachePolicy = -1;
/*     */       }
/* 117 */       propertySet = true;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 122 */     else if (System.getSecurityManager() == null) {
/* 123 */       cachePolicy = 30;
/*     */     } 
/*     */     
/* 126 */     integer = AccessController.<Integer>doPrivileged(new PrivilegedAction<Integer>()
/*     */         {
/*     */           public Integer run() {
/*     */             try {
/* 130 */               String str = Security.getProperty("networkaddress.cache.negative.ttl");
/* 131 */               if (str != null) {
/* 132 */                 return Integer.valueOf(str);
/*     */               }
/* 134 */             } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 139 */               String str = System.getProperty("sun.net.inetaddr.negative.ttl");
/* 140 */               if (str != null) {
/* 141 */                 return Integer.decode(str);
/*     */               }
/* 143 */             } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */             
/* 146 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 150 */     if (integer != null) {
/* 151 */       negativeCachePolicy = integer.intValue();
/* 152 */       if (negativeCachePolicy < 0) {
/* 153 */         negativeCachePolicy = -1;
/*     */       }
/* 155 */       propertyNegativeSet = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized int get() {
/* 160 */     return cachePolicy;
/*     */   }
/*     */   
/*     */   public static synchronized int getNegative() {
/* 164 */     return negativeCachePolicy;
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
/*     */   public static synchronized void setIfNotSet(int paramInt) {
/* 180 */     if (!propertySet) {
/* 181 */       checkValue(paramInt, cachePolicy);
/* 182 */       cachePolicy = paramInt;
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
/*     */   public static synchronized void setNegativeIfNotSet(int paramInt) {
/* 199 */     if (!propertyNegativeSet)
/*     */     {
/*     */ 
/*     */       
/* 203 */       negativeCachePolicy = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkValue(int paramInt1, int paramInt2) {
/* 213 */     if (paramInt1 == -1) {
/*     */       return;
/*     */     }
/* 216 */     if (paramInt2 == -1 || paramInt1 < paramInt2 || paramInt1 < -1)
/*     */     {
/*     */ 
/*     */       
/* 220 */       throw new SecurityException("can't make InetAddress cache more lax");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/InetAddressCachePolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */