/*     */ package java.net;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InMemoryCookieStore
/*     */   implements CookieStore
/*     */ {
/*  48 */   private List<HttpCookie> cookieJar = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private Map<String, List<HttpCookie>> domainIndex = null;
/*  55 */   private Map<URI, List<HttpCookie>> uriIndex = null;
/*     */ 
/*     */   
/*  58 */   private ReentrantLock lock = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InMemoryCookieStore() {
/*  65 */     this.cookieJar = new ArrayList<>();
/*  66 */     this.domainIndex = new HashMap<>();
/*  67 */     this.uriIndex = new HashMap<>();
/*     */     
/*  69 */     this.lock = new ReentrantLock(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(URI paramURI, HttpCookie paramHttpCookie) {
/*  77 */     if (paramHttpCookie == null) {
/*  78 */       throw new NullPointerException("cookie is null");
/*     */     }
/*     */ 
/*     */     
/*  82 */     this.lock.lock();
/*     */     
/*     */     try {
/*  85 */       this.cookieJar.remove(paramHttpCookie);
/*     */ 
/*     */       
/*  88 */       if (paramHttpCookie.getMaxAge() != 0L) {
/*  89 */         this.cookieJar.add(paramHttpCookie);
/*     */         
/*  91 */         if (paramHttpCookie.getDomain() != null) {
/*  92 */           addIndex(this.domainIndex, paramHttpCookie.getDomain(), paramHttpCookie);
/*     */         }
/*  94 */         if (paramURI != null)
/*     */         {
/*  96 */           addIndex(this.uriIndex, getEffectiveURI(paramURI), paramHttpCookie);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 100 */       this.lock.unlock();
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
/*     */   public List<HttpCookie> get(URI paramURI) {
/* 114 */     if (paramURI == null) {
/* 115 */       throw new NullPointerException("uri is null");
/*     */     }
/*     */     
/* 118 */     ArrayList<HttpCookie> arrayList = new ArrayList();
/* 119 */     boolean bool = "https".equalsIgnoreCase(paramURI.getScheme());
/* 120 */     this.lock.lock();
/*     */     
/*     */     try {
/* 123 */       getInternal1(arrayList, this.domainIndex, paramURI.getHost(), bool);
/*     */       
/* 125 */       getInternal2(arrayList, this.uriIndex, getEffectiveURI(paramURI), bool);
/*     */     } finally {
/* 127 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 130 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<HttpCookie> getCookies() {
/*     */     List<HttpCookie> list;
/* 139 */     this.lock.lock();
/*     */     try {
/* 141 */       Iterator<HttpCookie> iterator = this.cookieJar.iterator();
/* 142 */       while (iterator.hasNext()) {
/* 143 */         if (((HttpCookie)iterator.next()).hasExpired()) {
/* 144 */           iterator.remove();
/*     */         }
/*     */       } 
/*     */     } finally {
/* 148 */       list = Collections.unmodifiableList(this.cookieJar);
/* 149 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 152 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<URI> getURIs() {
/* 160 */     ArrayList<URI> arrayList = new ArrayList();
/*     */     
/* 162 */     this.lock.lock();
/*     */     try {
/* 164 */       Iterator<URI> iterator = this.uriIndex.keySet().iterator();
/* 165 */       while (iterator.hasNext()) {
/* 166 */         URI uRI = iterator.next();
/* 167 */         List list = this.uriIndex.get(uRI);
/* 168 */         if (list == null || list.size() == 0)
/*     */         {
/*     */           
/* 171 */           iterator.remove();
/*     */         }
/*     */       } 
/*     */     } finally {
/* 175 */       arrayList.addAll(this.uriIndex.keySet());
/* 176 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 179 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(URI paramURI, HttpCookie paramHttpCookie) {
/* 188 */     if (paramHttpCookie == null) {
/* 189 */       throw new NullPointerException("cookie is null");
/*     */     }
/*     */     
/* 192 */     boolean bool = false;
/* 193 */     this.lock.lock();
/*     */     try {
/* 195 */       bool = this.cookieJar.remove(paramHttpCookie);
/*     */     } finally {
/* 197 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 200 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAll() {
/* 208 */     this.lock.lock();
/*     */     try {
/* 210 */       if (this.cookieJar.isEmpty()) {
/* 211 */         return false;
/*     */       }
/* 213 */       this.cookieJar.clear();
/* 214 */       this.domainIndex.clear();
/* 215 */       this.uriIndex.clear();
/*     */     } finally {
/* 217 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 220 */     return true;
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
/*     */   private boolean netscapeDomainMatches(String paramString1, String paramString2) {
/* 240 */     if (paramString1 == null || paramString2 == null) {
/* 241 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 245 */     boolean bool = ".local".equalsIgnoreCase(paramString1);
/* 246 */     int i = paramString1.indexOf('.');
/* 247 */     if (i == 0) {
/* 248 */       i = paramString1.indexOf('.', 1);
/*     */     }
/* 250 */     if (!bool && (i == -1 || i == paramString1.length() - 1)) {
/* 251 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 255 */     int j = paramString2.indexOf('.');
/* 256 */     if (j == -1 && bool) {
/* 257 */       return true;
/*     */     }
/*     */     
/* 260 */     int k = paramString1.length();
/* 261 */     int m = paramString2.length() - k;
/* 262 */     if (m == 0)
/*     */     {
/* 264 */       return paramString2.equalsIgnoreCase(paramString1); } 
/* 265 */     if (m > 0) {
/*     */       
/* 267 */       String str1 = paramString2.substring(0, m);
/* 268 */       String str2 = paramString2.substring(m);
/*     */       
/* 270 */       return str2.equalsIgnoreCase(paramString1);
/* 271 */     }  if (m == -1)
/*     */     {
/* 273 */       return (paramString1.charAt(0) == '.' && paramString2
/* 274 */         .equalsIgnoreCase(paramString1.substring(1)));
/*     */     }
/*     */     
/* 277 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getInternal1(List<HttpCookie> paramList, Map<String, List<HttpCookie>> paramMap, String paramString, boolean paramBoolean) {
/* 284 */     ArrayList<HttpCookie> arrayList = new ArrayList();
/* 285 */     for (Map.Entry<String, List<HttpCookie>> entry : paramMap.entrySet()) {
/* 286 */       String str = (String)entry.getKey();
/* 287 */       List list = (List)entry.getValue();
/* 288 */       for (HttpCookie httpCookie : list) {
/* 289 */         if ((httpCookie.getVersion() == 0 && netscapeDomainMatches(str, paramString)) || (httpCookie
/* 290 */           .getVersion() == 1 && HttpCookie.domainMatches(str, paramString))) {
/* 291 */           if (this.cookieJar.indexOf(httpCookie) != -1) {
/*     */             
/* 293 */             if (!httpCookie.hasExpired()) {
/*     */ 
/*     */               
/* 296 */               if ((paramBoolean || !httpCookie.getSecure()) && 
/* 297 */                 !paramList.contains(httpCookie))
/* 298 */                 paramList.add(httpCookie); 
/*     */               continue;
/*     */             } 
/* 301 */             arrayList.add(httpCookie);
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 306 */           arrayList.add(httpCookie);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 311 */       for (HttpCookie httpCookie : arrayList) {
/* 312 */         list.remove(httpCookie);
/* 313 */         this.cookieJar.remove(httpCookie);
/*     */       } 
/*     */       
/* 316 */       arrayList.clear();
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
/*     */   private <T> void getInternal2(List<HttpCookie> paramList, Map<T, List<HttpCookie>> paramMap, Comparable<T> paramComparable, boolean paramBoolean) {
/*     */     // Byte code:
/*     */     //   0: aload_2
/*     */     //   1: invokeinterface keySet : ()Ljava/util/Set;
/*     */     //   6: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   11: astore #5
/*     */     //   13: aload #5
/*     */     //   15: invokeinterface hasNext : ()Z
/*     */     //   20: ifeq -> 186
/*     */     //   23: aload #5
/*     */     //   25: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   30: astore #6
/*     */     //   32: aload_3
/*     */     //   33: aload #6
/*     */     //   35: invokeinterface compareTo : (Ljava/lang/Object;)I
/*     */     //   40: ifne -> 183
/*     */     //   43: aload_2
/*     */     //   44: aload #6
/*     */     //   46: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   51: checkcast java/util/List
/*     */     //   54: astore #7
/*     */     //   56: aload #7
/*     */     //   58: ifnull -> 183
/*     */     //   61: aload #7
/*     */     //   63: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   68: astore #8
/*     */     //   70: aload #8
/*     */     //   72: invokeinterface hasNext : ()Z
/*     */     //   77: ifeq -> 183
/*     */     //   80: aload #8
/*     */     //   82: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   87: checkcast java/net/HttpCookie
/*     */     //   90: astore #9
/*     */     //   92: aload_0
/*     */     //   93: getfield cookieJar : Ljava/util/List;
/*     */     //   96: aload #9
/*     */     //   98: invokeinterface indexOf : (Ljava/lang/Object;)I
/*     */     //   103: iconst_m1
/*     */     //   104: if_icmpeq -> 173
/*     */     //   107: aload #9
/*     */     //   109: invokevirtual hasExpired : ()Z
/*     */     //   112: ifne -> 151
/*     */     //   115: iload #4
/*     */     //   117: ifne -> 128
/*     */     //   120: aload #9
/*     */     //   122: invokevirtual getSecure : ()Z
/*     */     //   125: ifne -> 180
/*     */     //   128: aload_1
/*     */     //   129: aload #9
/*     */     //   131: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   136: ifne -> 180
/*     */     //   139: aload_1
/*     */     //   140: aload #9
/*     */     //   142: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   147: pop
/*     */     //   148: goto -> 180
/*     */     //   151: aload #8
/*     */     //   153: invokeinterface remove : ()V
/*     */     //   158: aload_0
/*     */     //   159: getfield cookieJar : Ljava/util/List;
/*     */     //   162: aload #9
/*     */     //   164: invokeinterface remove : (Ljava/lang/Object;)Z
/*     */     //   169: pop
/*     */     //   170: goto -> 180
/*     */     //   173: aload #8
/*     */     //   175: invokeinterface remove : ()V
/*     */     //   180: goto -> 70
/*     */     //   183: goto -> 13
/*     */     //   186: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #328	-> 0
/*     */     //   #329	-> 32
/*     */     //   #330	-> 43
/*     */     //   #332	-> 56
/*     */     //   #333	-> 61
/*     */     //   #334	-> 70
/*     */     //   #335	-> 80
/*     */     //   #336	-> 92
/*     */     //   #338	-> 107
/*     */     //   #340	-> 115
/*     */     //   #341	-> 131
/*     */     //   #342	-> 139
/*     */     //   #344	-> 151
/*     */     //   #345	-> 158
/*     */     //   #350	-> 173
/*     */     //   #352	-> 180
/*     */     //   #355	-> 183
/*     */     //   #356	-> 186
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
/*     */   private <T> void addIndex(Map<T, List<HttpCookie>> paramMap, T paramT, HttpCookie paramHttpCookie) {
/* 363 */     if (paramT != null) {
/* 364 */       List<HttpCookie> list = paramMap.get(paramT);
/* 365 */       if (list != null) {
/*     */         
/* 367 */         list.remove(paramHttpCookie);
/*     */         
/* 369 */         list.add(paramHttpCookie);
/*     */       } else {
/* 371 */         list = new ArrayList<>();
/* 372 */         list.add(paramHttpCookie);
/* 373 */         paramMap.put(paramT, list);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URI getEffectiveURI(URI paramURI) {
/* 384 */     URI uRI = null;
/*     */     
/*     */     try {
/* 387 */       uRI = new URI("http", paramURI.getHost(), null, null, null);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 392 */     catch (URISyntaxException uRISyntaxException) {
/* 393 */       uRI = paramURI;
/*     */     } 
/*     */     
/* 396 */     return uRI;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/InMemoryCookieStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */