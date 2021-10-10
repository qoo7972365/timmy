/*     */ package sun.net.www.protocol.http;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthCacheImpl
/*     */   implements AuthCache
/*     */ {
/*  40 */   HashMap<String, LinkedList<AuthCacheValue>> hashtable = new HashMap<>();
/*     */ 
/*     */   
/*     */   public void setMap(HashMap<String, LinkedList<AuthCacheValue>> paramHashMap) {
/*  44 */     this.hashtable = paramHashMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void put(String paramString, AuthCacheValue paramAuthCacheValue) {
/*  51 */     LinkedList<AuthCacheValue> linkedList = this.hashtable.get(paramString);
/*  52 */     String str = paramAuthCacheValue.getPath();
/*  53 */     if (linkedList == null) {
/*  54 */       linkedList = new LinkedList();
/*  55 */       this.hashtable.put(paramString, linkedList);
/*     */     } 
/*     */     
/*  58 */     ListIterator<AuthCacheValue> listIterator = linkedList.listIterator();
/*  59 */     while (listIterator.hasNext()) {
/*  60 */       AuthenticationInfo authenticationInfo = (AuthenticationInfo)listIterator.next();
/*  61 */       if (authenticationInfo.path == null || authenticationInfo.path.startsWith(str)) {
/*  62 */         listIterator.remove();
/*     */       }
/*     */     } 
/*  65 */     listIterator.add(paramAuthCacheValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized AuthCacheValue get(String paramString1, String paramString2) {
/*  72 */     Object object = null;
/*  73 */     LinkedList<AuthenticationInfo> linkedList = (LinkedList)this.hashtable.get(paramString1);
/*  74 */     if (linkedList == null || linkedList.size() == 0) {
/*  75 */       return null;
/*     */     }
/*  77 */     if (paramString2 == null)
/*     */     {
/*  79 */       return linkedList.get(0);
/*     */     }
/*  81 */     ListIterator<AuthenticationInfo> listIterator = linkedList.listIterator();
/*  82 */     while (listIterator.hasNext()) {
/*  83 */       AuthenticationInfo authenticationInfo = listIterator.next();
/*  84 */       if (paramString2.startsWith(authenticationInfo.path)) {
/*  85 */         return authenticationInfo;
/*     */       }
/*     */     } 
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   public synchronized void remove(String paramString, AuthCacheValue paramAuthCacheValue) {
/*  92 */     LinkedList linkedList = this.hashtable.get(paramString);
/*  93 */     if (linkedList == null) {
/*     */       return;
/*     */     }
/*  96 */     if (paramAuthCacheValue == null) {
/*  97 */       linkedList.clear();
/*     */       return;
/*     */     } 
/* 100 */     ListIterator<AuthenticationInfo> listIterator = linkedList.listIterator();
/* 101 */     while (listIterator.hasNext()) {
/* 102 */       AuthenticationInfo authenticationInfo = listIterator.next();
/* 103 */       if (paramAuthCacheValue.equals(authenticationInfo))
/* 104 */         listIterator.remove(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/AuthCacheImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */