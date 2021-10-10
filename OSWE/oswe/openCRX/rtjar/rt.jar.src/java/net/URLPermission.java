/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.security.Permission;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class URLPermission
/*     */   extends Permission
/*     */ {
/*     */   private static final long serialVersionUID = -2702463814894478682L;
/*     */   private transient String scheme;
/*     */   private transient String ssp;
/*     */   private transient String path;
/*     */   private transient List<String> methods;
/*     */   private transient List<String> requestHeaders;
/*     */   private transient Authority authority;
/*     */   private String actions;
/*     */   
/*     */   public URLPermission(String paramString1, String paramString2) {
/* 165 */     super(paramString1);
/* 166 */     init(paramString2);
/*     */   }
/*     */   private void init(String paramString) {
/*     */     String str1, str2;
/* 170 */     parseURI(getName());
/* 171 */     int i = paramString.indexOf(':');
/* 172 */     if (paramString.lastIndexOf(':') != i) {
/* 173 */       throw new IllegalArgumentException("Invalid actions string: \"" + paramString + "\"");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 178 */     if (i == -1) {
/* 179 */       str1 = paramString;
/* 180 */       str2 = "";
/*     */     } else {
/* 182 */       str1 = paramString.substring(0, i);
/* 183 */       str2 = paramString.substring(i + 1);
/*     */     } 
/*     */     
/* 186 */     List<String> list = normalizeMethods(str1);
/* 187 */     Collections.sort(list);
/* 188 */     this.methods = Collections.unmodifiableList(list);
/*     */     
/* 190 */     list = normalizeHeaders(str2);
/* 191 */     Collections.sort(list);
/* 192 */     this.requestHeaders = Collections.unmodifiableList(list);
/*     */     
/* 194 */     this.actions = actions();
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
/*     */   public URLPermission(String paramString) {
/* 207 */     this(paramString, "*:*");
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
/*     */   public String getActions() {
/* 223 */     return this.actions;
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
/*     */   public boolean implies(Permission paramPermission) {
/* 262 */     if (!(paramPermission instanceof URLPermission)) {
/* 263 */       return false;
/*     */     }
/*     */     
/* 266 */     URLPermission uRLPermission = (URLPermission)paramPermission;
/*     */     
/* 268 */     if (!((String)this.methods.get(0)).equals("*") && 
/* 269 */       Collections.indexOfSubList(this.methods, uRLPermission.methods) == -1) {
/* 270 */       return false;
/*     */     }
/*     */     
/* 273 */     if (this.requestHeaders.isEmpty() && !uRLPermission.requestHeaders.isEmpty()) {
/* 274 */       return false;
/*     */     }
/*     */     
/* 277 */     if (!this.requestHeaders.isEmpty() && 
/* 278 */       !((String)this.requestHeaders.get(0)).equals("*") && 
/* 279 */       Collections.indexOfSubList(this.requestHeaders, uRLPermission.requestHeaders) == -1)
/*     */     {
/* 281 */       return false;
/*     */     }
/*     */     
/* 284 */     if (!this.scheme.equals(uRLPermission.scheme)) {
/* 285 */       return false;
/*     */     }
/*     */     
/* 288 */     if (this.ssp.equals("*")) {
/* 289 */       return true;
/*     */     }
/*     */     
/* 292 */     if (!this.authority.implies(uRLPermission.authority)) {
/* 293 */       return false;
/*     */     }
/*     */     
/* 296 */     if (this.path == null) {
/* 297 */       return (uRLPermission.path == null);
/*     */     }
/* 299 */     if (uRLPermission.path == null) {
/* 300 */       return false;
/*     */     }
/*     */     
/* 303 */     if (this.path.endsWith("/-")) {
/* 304 */       String str = this.path.substring(0, this.path.length() - 1);
/* 305 */       return uRLPermission.path.startsWith(str);
/*     */     } 
/*     */     
/* 308 */     if (this.path.endsWith("/*")) {
/* 309 */       String str1 = this.path.substring(0, this.path.length() - 1);
/* 310 */       if (!uRLPermission.path.startsWith(str1)) {
/* 311 */         return false;
/*     */       }
/* 313 */       String str2 = uRLPermission.path.substring(str1.length());
/*     */       
/* 315 */       if (str2.indexOf('/') != -1) {
/* 316 */         return false;
/*     */       }
/* 318 */       if (str2.equals("-")) {
/* 319 */         return false;
/*     */       }
/* 321 */       return true;
/*     */     } 
/* 323 */     return this.path.equals(uRLPermission.path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 332 */     if (!(paramObject instanceof URLPermission)) {
/* 333 */       return false;
/*     */     }
/* 335 */     URLPermission uRLPermission = (URLPermission)paramObject;
/* 336 */     if (!this.scheme.equals(uRLPermission.scheme)) {
/* 337 */       return false;
/*     */     }
/* 339 */     if (!getActions().equals(uRLPermission.getActions())) {
/* 340 */       return false;
/*     */     }
/* 342 */     if (!this.authority.equals(uRLPermission.authority)) {
/* 343 */       return false;
/*     */     }
/* 345 */     if (this.path != null) {
/* 346 */       return this.path.equals(uRLPermission.path);
/*     */     }
/* 348 */     return (uRLPermission.path == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 357 */     return getActions().hashCode() + this.scheme
/* 358 */       .hashCode() + this.authority
/* 359 */       .hashCode() + ((this.path == null) ? 0 : this.path
/* 360 */       .hashCode());
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> normalizeMethods(String paramString) {
/* 365 */     ArrayList<String> arrayList = new ArrayList();
/* 366 */     StringBuilder stringBuilder = new StringBuilder();
/* 367 */     for (byte b = 0; b < paramString.length(); b++) {
/* 368 */       char c = paramString.charAt(b);
/* 369 */       if (c == ',')
/* 370 */       { String str1 = stringBuilder.toString();
/* 371 */         if (str1.length() > 0)
/* 372 */           arrayList.add(str1); 
/* 373 */         stringBuilder = new StringBuilder(); }
/* 374 */       else { if (c == ' ' || c == '\t') {
/* 375 */           throw new IllegalArgumentException("White space not allowed in methods: \"" + paramString + "\"");
/*     */         }
/*     */         
/* 378 */         if (c >= 'a' && c <= 'z') {
/* 379 */           c = (char)(c - 32);
/*     */         }
/* 381 */         stringBuilder.append(c); }
/*     */     
/*     */     } 
/* 384 */     String str = stringBuilder.toString();
/* 385 */     if (str.length() > 0)
/* 386 */       arrayList.add(str); 
/* 387 */     return arrayList;
/*     */   }
/*     */   
/*     */   private List<String> normalizeHeaders(String paramString) {
/* 391 */     ArrayList<String> arrayList = new ArrayList();
/* 392 */     StringBuilder stringBuilder = new StringBuilder();
/* 393 */     boolean bool = true;
/* 394 */     for (byte b = 0; b < paramString.length(); b++) {
/* 395 */       char c = paramString.charAt(b);
/* 396 */       if (c >= 'a' && c <= 'z')
/* 397 */       { if (bool) {
/* 398 */           c = (char)(c - 32);
/* 399 */           bool = false;
/*     */         } 
/* 401 */         stringBuilder.append(c); }
/* 402 */       else { if (c == ' ' || c == '\t') {
/* 403 */           throw new IllegalArgumentException("White space not allowed in headers: \"" + paramString + "\"");
/*     */         }
/* 405 */         if (c == '-') {
/* 406 */           bool = true;
/* 407 */           stringBuilder.append(c);
/* 408 */         } else if (c == ',') {
/* 409 */           String str1 = stringBuilder.toString();
/* 410 */           if (str1.length() > 0)
/* 411 */             arrayList.add(str1); 
/* 412 */           stringBuilder = new StringBuilder();
/* 413 */           bool = true;
/*     */         } else {
/* 415 */           bool = false;
/* 416 */           stringBuilder.append(c);
/*     */         }  }
/*     */     
/* 419 */     }  String str = stringBuilder.toString();
/* 420 */     if (str.length() > 0)
/* 421 */       arrayList.add(str); 
/* 422 */     return arrayList;
/*     */   }
/*     */   private void parseURI(String paramString) {
/*     */     String str2;
/* 426 */     int i = paramString.length();
/* 427 */     int j = paramString.indexOf(':');
/* 428 */     if (j == -1 || j + 1 == i) {
/* 429 */       throw new IllegalArgumentException("Invalid URL string: \"" + paramString + "\"");
/*     */     }
/*     */     
/* 432 */     this.scheme = paramString.substring(0, j).toLowerCase();
/* 433 */     this.ssp = paramString.substring(j + 1);
/*     */     
/* 435 */     if (!this.ssp.startsWith("//")) {
/* 436 */       if (!this.ssp.equals("*")) {
/* 437 */         throw new IllegalArgumentException("Invalid URL string: \"" + paramString + "\"");
/*     */       }
/*     */       
/* 440 */       this.authority = new Authority(this.scheme, "*");
/*     */       return;
/*     */     } 
/* 443 */     String str1 = this.ssp.substring(2);
/*     */     
/* 445 */     j = str1.indexOf('/');
/*     */     
/* 447 */     if (j == -1) {
/* 448 */       this.path = "";
/* 449 */       str2 = str1;
/*     */     } else {
/* 451 */       str2 = str1.substring(0, j);
/* 452 */       this.path = str1.substring(j);
/*     */     } 
/* 454 */     this.authority = new Authority(this.scheme, str2.toLowerCase());
/*     */   }
/*     */   
/*     */   private String actions() {
/* 458 */     StringBuilder stringBuilder = new StringBuilder();
/* 459 */     for (String str : this.methods) {
/* 460 */       stringBuilder.append(str);
/*     */     }
/* 462 */     stringBuilder.append(":");
/* 463 */     for (String str : this.requestHeaders) {
/* 464 */       stringBuilder.append(str);
/*     */     }
/* 466 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 474 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 475 */     String str = (String)getField.get("actions", (Object)null);
/*     */     
/* 477 */     init(str);
/*     */   }
/*     */   
/*     */   static class Authority {
/*     */     HostPortrange p;
/*     */     
/*     */     Authority(String param1String1, String param1String2) {
/* 484 */       int i = param1String2.indexOf('@');
/* 485 */       if (i == -1) {
/* 486 */         this.p = new HostPortrange(param1String1, param1String2);
/*     */       } else {
/* 488 */         this.p = new HostPortrange(param1String1, param1String2.substring(i + 1));
/*     */       } 
/*     */     }
/*     */     
/*     */     boolean implies(Authority param1Authority) {
/* 493 */       return (impliesHostrange(param1Authority) && impliesPortrange(param1Authority));
/*     */     }
/*     */     
/*     */     private boolean impliesHostrange(Authority param1Authority) {
/* 497 */       String str1 = this.p.hostname();
/* 498 */       String str2 = param1Authority.p.hostname();
/*     */       
/* 500 */       if (this.p.wildcard() && str1.equals(""))
/*     */       {
/* 502 */         return true;
/*     */       }
/* 504 */       if (param1Authority.p.wildcard() && str2.equals(""))
/*     */       {
/* 506 */         return false;
/*     */       }
/* 508 */       if (str1.equals(str2))
/*     */       {
/*     */         
/* 511 */         return true;
/*     */       }
/* 513 */       if (this.p.wildcard())
/*     */       {
/* 515 */         return str2.endsWith(str1);
/*     */       }
/* 517 */       return false;
/*     */     }
/*     */     
/*     */     private boolean impliesPortrange(Authority param1Authority) {
/* 521 */       int[] arrayOfInt1 = this.p.portrange();
/* 522 */       int[] arrayOfInt2 = param1Authority.p.portrange();
/* 523 */       if (arrayOfInt1[0] == -1)
/*     */       {
/* 525 */         return true;
/*     */       }
/* 527 */       return (arrayOfInt1[0] <= arrayOfInt2[0] && arrayOfInt1[1] >= arrayOfInt2[1]);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean equals(Authority param1Authority) {
/* 532 */       return this.p.equals(param1Authority.p);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 536 */       return this.p.hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/URLPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */