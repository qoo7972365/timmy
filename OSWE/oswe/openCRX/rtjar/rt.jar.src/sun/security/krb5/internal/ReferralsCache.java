/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ReferralsCache
/*     */ {
/*  49 */   private static Map<ReferralCacheKey, Map<String, ReferralCacheEntry>> referralsMap = new HashMap<>();
/*     */   
/*     */   private static final class ReferralCacheKey {
/*     */     private PrincipalName cname;
/*     */     
/*     */     ReferralCacheKey(PrincipalName param1PrincipalName1, PrincipalName param1PrincipalName2) {
/*  55 */       this.cname = param1PrincipalName1;
/*  56 */       this.sname = param1PrincipalName2;
/*     */     } private PrincipalName sname;
/*     */     public boolean equals(Object param1Object) {
/*  59 */       if (!(param1Object instanceof ReferralCacheKey))
/*  60 */         return false; 
/*  61 */       ReferralCacheKey referralCacheKey = (ReferralCacheKey)param1Object;
/*  62 */       return (this.cname.equals(referralCacheKey.cname) && this.sname
/*  63 */         .equals(referralCacheKey.sname));
/*     */     }
/*     */     public int hashCode() {
/*  66 */       return this.cname.hashCode() + this.sname.hashCode();
/*     */     } }
/*     */   
/*     */   static final class ReferralCacheEntry {
/*     */     private final Credentials creds;
/*     */     private final String toRealm;
/*     */     
/*     */     ReferralCacheEntry(Credentials param1Credentials, String param1String) {
/*  74 */       this.creds = param1Credentials;
/*  75 */       this.toRealm = param1String;
/*     */     }
/*     */     Credentials getCreds() {
/*  78 */       return this.creds;
/*     */     }
/*     */     String getToRealm() {
/*  81 */       return this.toRealm;
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
/*     */   static synchronized void put(PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, String paramString1, String paramString2, Credentials paramCredentials) {
/*  98 */     ReferralCacheKey referralCacheKey = new ReferralCacheKey(paramPrincipalName1, paramPrincipalName2);
/*  99 */     pruneExpired(referralCacheKey);
/* 100 */     if (paramCredentials.getEndTime().before(new Date())) {
/*     */       return;
/*     */     }
/* 103 */     Map<Object, Object> map = (Map)referralsMap.get(referralCacheKey);
/* 104 */     if (map == null) {
/* 105 */       map = new HashMap<>();
/* 106 */       referralsMap.put(referralCacheKey, map);
/*     */     } 
/* 108 */     map.remove(paramString1);
/* 109 */     ReferralCacheEntry referralCacheEntry1 = new ReferralCacheEntry(paramCredentials, paramString2);
/* 110 */     map.put(paramString1, referralCacheEntry1);
/*     */ 
/*     */     
/* 113 */     ReferralCacheEntry referralCacheEntry2 = referralCacheEntry1;
/* 114 */     LinkedList<ReferralCacheEntry> linkedList = new LinkedList();
/* 115 */     while (referralCacheEntry2 != null) {
/* 116 */       if (linkedList.contains(referralCacheEntry2)) {
/*     */         
/* 118 */         map.remove(referralCacheEntry1.getToRealm());
/*     */         break;
/*     */       } 
/* 121 */       linkedList.add(referralCacheEntry2);
/* 122 */       referralCacheEntry2 = (ReferralCacheEntry)map.get(referralCacheEntry2.getToRealm());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized ReferralCacheEntry get(PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, String paramString) {
/* 132 */     ReferralCacheKey referralCacheKey = new ReferralCacheKey(paramPrincipalName1, paramPrincipalName2);
/* 133 */     pruneExpired(referralCacheKey);
/* 134 */     Map map = referralsMap.get(referralCacheKey);
/* 135 */     if (map != null) {
/* 136 */       ReferralCacheEntry referralCacheEntry = (ReferralCacheEntry)map.get(paramString);
/* 137 */       if (referralCacheEntry != null) {
/* 138 */         return referralCacheEntry;
/*     */       }
/*     */     } 
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void pruneExpired(ReferralCacheKey paramReferralCacheKey) {
/* 148 */     Date date = new Date();
/* 149 */     Map map = referralsMap.get(paramReferralCacheKey);
/* 150 */     if (map != null)
/*     */     {
/* 152 */       for (Map.Entry entry : map.entrySet()) {
/* 153 */         if (((ReferralCacheEntry)entry.getValue()).getCreds().getEndTime().before(date))
/* 154 */           map.remove(entry.getKey()); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/ReferralsCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */