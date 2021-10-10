/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CookieManager
/*     */   extends CookieHandler
/*     */ {
/*     */   private CookiePolicy policyCallback;
/* 124 */   private CookieStore cookieJar = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CookieManager() {
/* 137 */     this(null, null);
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
/*     */   public CookieManager(CookieStore paramCookieStore, CookiePolicy paramCookiePolicy) {
/* 156 */     this.policyCallback = (paramCookiePolicy == null) ? CookiePolicy.ACCEPT_ORIGINAL_SERVER : paramCookiePolicy;
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (paramCookieStore == null) {
/* 161 */       this.cookieJar = new InMemoryCookieStore();
/*     */     } else {
/* 163 */       this.cookieJar = paramCookieStore;
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
/*     */   public void setCookiePolicy(CookiePolicy paramCookiePolicy) {
/* 181 */     if (paramCookiePolicy != null) this.policyCallback = paramCookiePolicy;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CookieStore getCookieStore() {
/* 191 */     return this.cookieJar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, List<String>> get(URI paramURI, Map<String, List<String>> paramMap) throws IOException {
/* 200 */     if (paramURI == null || paramMap == null) {
/* 201 */       throw new IllegalArgumentException("Argument is null");
/*     */     }
/*     */     
/* 204 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */ 
/*     */     
/* 207 */     if (this.cookieJar == null) {
/* 208 */       return (Map)Collections.unmodifiableMap(hashMap);
/*     */     }
/* 210 */     boolean bool = "https".equalsIgnoreCase(paramURI.getScheme());
/* 211 */     ArrayList<HttpCookie> arrayList = new ArrayList();
/* 212 */     String str = paramURI.getPath();
/* 213 */     if (str == null || str.isEmpty()) {
/* 214 */       str = "/";
/*     */     }
/* 216 */     for (HttpCookie httpCookie : this.cookieJar.get(paramURI)) {
/*     */ 
/*     */ 
/*     */       
/* 220 */       if (pathMatches(str, httpCookie.getPath()) && (bool || 
/* 221 */         !httpCookie.getSecure())) {
/*     */         
/* 223 */         if (httpCookie.isHttpOnly()) {
/* 224 */           String str2 = paramURI.getScheme();
/* 225 */           if (!"http".equalsIgnoreCase(str2) && !"https".equalsIgnoreCase(str2)) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 230 */         String str1 = httpCookie.getPortlist();
/* 231 */         if (str1 != null && !str1.isEmpty()) {
/* 232 */           int i = paramURI.getPort();
/* 233 */           if (i == -1) {
/* 234 */             i = "https".equals(paramURI.getScheme()) ? 443 : 80;
/*     */           }
/* 236 */           if (isInPortList(str1, i))
/* 237 */             arrayList.add(httpCookie); 
/*     */           continue;
/*     */         } 
/* 240 */         arrayList.add(httpCookie);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 246 */     List<String> list = sortByPath(arrayList);
/*     */     
/* 248 */     hashMap.put("Cookie", list);
/* 249 */     return (Map)Collections.unmodifiableMap(hashMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(URI paramURI, Map<String, List<String>> paramMap) throws IOException {
/* 257 */     if (paramURI == null || paramMap == null) {
/* 258 */       throw new IllegalArgumentException("Argument is null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 263 */     if (this.cookieJar == null) {
/*     */       return;
/*     */     }
/* 266 */     PlatformLogger platformLogger = PlatformLogger.getLogger("java.net.CookieManager");
/* 267 */     for (String str : paramMap.keySet()) {
/*     */ 
/*     */       
/* 270 */       if (str == null || (
/* 271 */         !str.equalsIgnoreCase("Set-Cookie2") && 
/* 272 */         !str.equalsIgnoreCase("Set-Cookie"))) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 279 */       for (String str1 : paramMap.get(str)) {
/*     */         try {
/*     */           List<?> list;
/*     */           try {
/* 283 */             list = HttpCookie.parse(str1);
/* 284 */           } catch (IllegalArgumentException illegalArgumentException) {
/*     */             
/* 286 */             list = Collections.emptyList();
/* 287 */             if (platformLogger.isLoggable(PlatformLogger.Level.SEVERE)) {
/* 288 */               platformLogger.severe("Invalid cookie for " + paramURI + ": " + str1);
/*     */             }
/*     */           } 
/* 291 */           for (HttpCookie httpCookie : list) {
/* 292 */             if (httpCookie.getPath() == null) {
/*     */ 
/*     */               
/* 295 */               String str3 = paramURI.getPath();
/* 296 */               if (!str3.endsWith("/")) {
/* 297 */                 int i = str3.lastIndexOf("/");
/* 298 */                 if (i > 0) {
/* 299 */                   str3 = str3.substring(0, i + 1);
/*     */                 } else {
/* 301 */                   str3 = "/";
/*     */                 } 
/*     */               } 
/* 304 */               httpCookie.setPath(str3);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 311 */             if (httpCookie.getDomain() == null) {
/* 312 */               String str3 = paramURI.getHost();
/* 313 */               if (str3 != null && !str3.contains("."))
/* 314 */                 str3 = str3 + ".local"; 
/* 315 */               httpCookie.setDomain(str3);
/*     */             } 
/* 317 */             String str2 = httpCookie.getPortlist();
/* 318 */             if (str2 != null) {
/* 319 */               int i = paramURI.getPort();
/* 320 */               if (i == -1) {
/* 321 */                 i = "https".equals(paramURI.getScheme()) ? 443 : 80;
/*     */               }
/* 323 */               if (str2.isEmpty()) {
/*     */ 
/*     */                 
/* 326 */                 httpCookie.setPortlist("" + i);
/* 327 */                 if (shouldAcceptInternal(paramURI, httpCookie)) {
/* 328 */                   this.cookieJar.add(paramURI, httpCookie);
/*     */                 }
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */               
/* 334 */               if (isInPortList(str2, i) && 
/* 335 */                 shouldAcceptInternal(paramURI, httpCookie)) {
/* 336 */                 this.cookieJar.add(paramURI, httpCookie);
/*     */               }
/*     */               continue;
/*     */             } 
/* 340 */             if (shouldAcceptInternal(paramURI, httpCookie)) {
/* 341 */               this.cookieJar.add(paramURI, httpCookie);
/*     */             }
/*     */           }
/*     */         
/* 345 */         } catch (IllegalArgumentException illegalArgumentException) {}
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
/*     */   private boolean shouldAcceptInternal(URI paramURI, HttpCookie paramHttpCookie) {
/*     */     try {
/* 359 */       return this.policyCallback.shouldAccept(paramURI, paramHttpCookie);
/* 360 */     } catch (Exception exception) {
/* 361 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isInPortList(String paramString, int paramInt) {
/* 367 */     int i = paramString.indexOf(",");
/* 368 */     int j = -1;
/* 369 */     while (i > 0) {
/*     */       try {
/* 371 */         j = Integer.parseInt(paramString.substring(0, i));
/* 372 */         if (j == paramInt) {
/* 373 */           return true;
/*     */         }
/* 375 */       } catch (NumberFormatException numberFormatException) {}
/*     */       
/* 377 */       paramString = paramString.substring(i + 1);
/* 378 */       i = paramString.indexOf(",");
/*     */     } 
/* 380 */     if (!paramString.isEmpty()) {
/*     */       try {
/* 382 */         j = Integer.parseInt(paramString);
/* 383 */         if (j == paramInt) {
/* 384 */           return true;
/*     */         }
/* 386 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */     
/* 389 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean pathMatches(String paramString1, String paramString2) {
/* 396 */     if (paramString1 == paramString2)
/* 397 */       return true; 
/* 398 */     if (paramString1 == null || paramString2 == null)
/* 399 */       return false; 
/* 400 */     if (paramString1.startsWith(paramString2)) {
/* 401 */       return true;
/*     */     }
/* 403 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> sortByPath(List<HttpCookie> paramList) {
/* 412 */     Collections.sort(paramList, new CookiePathComparator());
/*     */     
/* 414 */     ArrayList<String> arrayList = new ArrayList();
/* 415 */     for (HttpCookie httpCookie : paramList) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 420 */       if (paramList.indexOf(httpCookie) == 0 && httpCookie.getVersion() > 0) {
/* 421 */         arrayList.add("$Version=\"1\"");
/*     */       }
/*     */       
/* 424 */       arrayList.add(httpCookie.toString());
/*     */     } 
/* 426 */     return arrayList;
/*     */   }
/*     */   
/*     */   static class CookiePathComparator
/*     */     implements Comparator<HttpCookie> {
/*     */     public int compare(HttpCookie param1HttpCookie1, HttpCookie param1HttpCookie2) {
/* 432 */       if (param1HttpCookie1 == param1HttpCookie2) return 0; 
/* 433 */       if (param1HttpCookie1 == null) return -1; 
/* 434 */       if (param1HttpCookie2 == null) return 1;
/*     */ 
/*     */       
/* 437 */       if (!param1HttpCookie1.getName().equals(param1HttpCookie2.getName())) return 0;
/*     */ 
/*     */       
/* 440 */       if (param1HttpCookie1.getPath().startsWith(param1HttpCookie2.getPath()))
/* 441 */         return -1; 
/* 442 */       if (param1HttpCookie2.getPath().startsWith(param1HttpCookie1.getPath())) {
/* 443 */         return 1;
/*     */       }
/* 445 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/CookieManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */