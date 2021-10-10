/*     */ package sun.net.www;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringJoiner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageHeader
/*     */ {
/*     */   private String[] keys;
/*     */   private String[] values;
/*     */   private int nkeys;
/*     */   
/*     */   public MessageHeader() {
/*  50 */     grow();
/*     */   }
/*     */   
/*     */   public MessageHeader(InputStream paramInputStream) throws IOException {
/*  54 */     parseHeader(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getHeaderNamesInList() {
/*  61 */     StringJoiner stringJoiner = new StringJoiner(",");
/*  62 */     for (byte b = 0; b < this.nkeys; b++) {
/*  63 */       stringJoiner.add(this.keys[b]);
/*     */     }
/*  65 */     return stringJoiner.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/*  72 */     this.keys = null;
/*  73 */     this.values = null;
/*  74 */     this.nkeys = 0;
/*  75 */     grow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String findValue(String paramString) {
/*  85 */     if (paramString == null)
/*  86 */     { for (int i = this.nkeys; --i >= 0;) {
/*  87 */         if (this.keys[i] == null)
/*  88 */           return this.values[i]; 
/*     */       }  }
/*  90 */     else { for (int i = this.nkeys; --i >= 0;) {
/*  91 */         if (paramString.equalsIgnoreCase(this.keys[i]))
/*  92 */           return this.values[i]; 
/*     */       }  }
/*  94 */      return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int getKey(String paramString) {
/*  99 */     for (int i = this.nkeys; --i >= 0;) {
/* 100 */       if (this.keys[i] == paramString || (paramString != null && paramString
/* 101 */         .equalsIgnoreCase(this.keys[i])))
/* 102 */         return i; 
/* 103 */     }  return -1;
/*     */   }
/*     */   
/*     */   public synchronized String getKey(int paramInt) {
/* 107 */     if (paramInt < 0 || paramInt >= this.nkeys) return null; 
/* 108 */     return this.keys[paramInt];
/*     */   }
/*     */   
/*     */   public synchronized String getValue(int paramInt) {
/* 112 */     if (paramInt < 0 || paramInt >= this.nkeys) return null; 
/* 113 */     return this.values[paramInt];
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
/*     */   public synchronized String findNextValue(String paramString1, String paramString2) {
/* 128 */     boolean bool = false;
/* 129 */     if (paramString1 == null)
/* 130 */     { for (int i = this.nkeys; --i >= 0;) {
/* 131 */         if (this.keys[i] == null)
/* 132 */         { if (bool)
/* 133 */             return this.values[i]; 
/* 134 */           if (this.values[i] == paramString2)
/* 135 */             bool = true;  } 
/*     */       }  }
/* 137 */     else { for (int i = this.nkeys; --i >= 0;)
/* 138 */       { if (paramString1.equalsIgnoreCase(this.keys[i]))
/* 139 */         { if (bool)
/* 140 */             return this.values[i]; 
/* 141 */           if (this.values[i] == paramString2)
/* 142 */             bool = true;  }  }  }
/* 143 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean filterNTLMResponses(String paramString) {
/* 152 */     boolean bool = false; byte b;
/* 153 */     for (b = 0; b < this.nkeys; b++) {
/* 154 */       if (paramString.equalsIgnoreCase(this.keys[b]) && this.values[b] != null && this.values[b]
/* 155 */         .length() > 5 && this.values[b]
/* 156 */         .substring(0, 5).equalsIgnoreCase("NTLM ")) {
/* 157 */         bool = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 161 */     if (bool) {
/* 162 */       b = 0;
/* 163 */       for (byte b1 = 0; b1 < this.nkeys; b1++) {
/* 164 */         if (!paramString.equalsIgnoreCase(this.keys[b1]) || (
/* 165 */           !"Negotiate".equalsIgnoreCase(this.values[b1]) && 
/* 166 */           !"Kerberos".equalsIgnoreCase(this.values[b1]))) {
/*     */ 
/*     */           
/* 169 */           if (b1 != b) {
/* 170 */             this.keys[b] = this.keys[b1];
/* 171 */             this.values[b] = this.values[b1];
/*     */           } 
/* 173 */           b++;
/*     */         } 
/* 175 */       }  if (b != this.nkeys) {
/* 176 */         this.nkeys = b;
/* 177 */         return true;
/*     */       } 
/*     */     } 
/* 180 */     return false;
/*     */   }
/*     */   
/*     */   class HeaderIterator implements Iterator<String> {
/* 184 */     int index = 0;
/* 185 */     int next = -1;
/*     */     String key;
/*     */     boolean haveNext = false;
/*     */     Object lock;
/*     */     
/*     */     public HeaderIterator(String param1String, Object param1Object) {
/* 191 */       this.key = param1String;
/* 192 */       this.lock = param1Object;
/*     */     }
/*     */     public boolean hasNext() {
/* 195 */       synchronized (this.lock) {
/* 196 */         if (this.haveNext) {
/* 197 */           return true;
/*     */         }
/* 199 */         while (this.index < MessageHeader.this.nkeys) {
/* 200 */           if (this.key.equalsIgnoreCase(MessageHeader.this.keys[this.index])) {
/* 201 */             this.haveNext = true;
/* 202 */             this.next = this.index++;
/* 203 */             return true;
/*     */           } 
/* 205 */           this.index++;
/*     */         } 
/* 207 */         return false;
/*     */       } 
/*     */     }
/*     */     public String next() {
/* 211 */       synchronized (this.lock) {
/* 212 */         if (this.haveNext) {
/* 213 */           this.haveNext = false;
/* 214 */           return MessageHeader.this.values[this.next];
/*     */         } 
/* 216 */         if (hasNext()) {
/* 217 */           return next();
/*     */         }
/* 219 */         throw new NoSuchElementException("No more elements");
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 224 */       throw new UnsupportedOperationException("remove not allowed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<String> multiValueIterator(String paramString) {
/* 233 */     return new HeaderIterator(paramString, this);
/*     */   }
/*     */   
/*     */   public synchronized Map<String, List<String>> getHeaders() {
/* 237 */     return getHeaders(null);
/*     */   }
/*     */   
/*     */   public synchronized Map<String, List<String>> getHeaders(String[] paramArrayOfString) {
/* 241 */     return filterAndAddHeaders(paramArrayOfString, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Map<String, List<String>> filterAndAddHeaders(String[] paramArrayOfString, Map<String, List<String>> paramMap) {
/* 246 */     boolean bool = false;
/* 247 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 248 */     for (int i = this.nkeys; --i >= 0; ) {
/* 249 */       if (paramArrayOfString != null)
/*     */       {
/*     */         
/* 252 */         for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 253 */           if (paramArrayOfString[b] != null && paramArrayOfString[b]
/* 254 */             .equalsIgnoreCase(this.keys[i])) {
/* 255 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 260 */       if (!bool) {
/* 261 */         List<String> list = (List)hashMap.get(this.keys[i]);
/* 262 */         if (list == null) {
/* 263 */           list = new ArrayList();
/* 264 */           hashMap.put(this.keys[i], list);
/*     */         } 
/* 266 */         list.add(this.values[i]);
/*     */         continue;
/*     */       } 
/* 269 */       bool = false;
/*     */     } 
/*     */ 
/*     */     
/* 273 */     if (paramMap != null) {
/* 274 */       for (Map.Entry<String, List<String>> entry : paramMap.entrySet()) {
/* 275 */         List list = (List)hashMap.get(entry.getKey());
/* 276 */         if (list == null) {
/* 277 */           list = new ArrayList();
/* 278 */           hashMap.put(entry.getKey(), list);
/*     */         } 
/* 280 */         list.addAll((Collection)entry.getValue());
/*     */       } 
/*     */     }
/*     */     
/* 284 */     for (String str : hashMap.keySet()) {
/* 285 */       hashMap.put(str, Collections.unmodifiableList((List)hashMap.get(str)));
/*     */     }
/*     */     
/* 288 */     return (Map)Collections.unmodifiableMap(hashMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRequestline(String paramString) {
/* 298 */     String str = paramString.trim();
/* 299 */     int i = str.lastIndexOf(' ');
/* 300 */     if (i <= 0) return false; 
/* 301 */     int j = str.length();
/* 302 */     if (j - i < 9) return false;
/*     */     
/* 304 */     char c1 = str.charAt(j - 3);
/* 305 */     char c2 = str.charAt(j - 2);
/* 306 */     char c3 = str.charAt(j - 1);
/* 307 */     if (c1 < '1' || c1 > '9') return false; 
/* 308 */     if (c2 != '.') return false; 
/* 309 */     if (c3 < '0' || c3 > '9') return false;
/*     */     
/* 311 */     return str.substring(i + 1, j - 3).equalsIgnoreCase("HTTP/");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void print(PrintStream paramPrintStream) {
/* 320 */     for (byte b = 0; b < this.nkeys; b++) {
/* 321 */       if (this.keys[b] != null) {
/* 322 */         StringBuilder stringBuilder = new StringBuilder(this.keys[b]);
/* 323 */         if (this.values[b] != null) {
/* 324 */           stringBuilder.append(": " + this.values[b]);
/* 325 */         } else if (b != 0 || !isRequestline(this.keys[b])) {
/* 326 */           stringBuilder.append(":");
/*     */         } 
/* 328 */         paramPrintStream.print(stringBuilder.append("\r\n"));
/*     */       } 
/* 330 */     }  paramPrintStream.print("\r\n");
/* 331 */     paramPrintStream.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void add(String paramString1, String paramString2) {
/* 337 */     grow();
/* 338 */     this.keys[this.nkeys] = paramString1;
/* 339 */     this.values[this.nkeys] = paramString2;
/* 340 */     this.nkeys++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void prepend(String paramString1, String paramString2) {
/* 346 */     grow();
/* 347 */     for (int i = this.nkeys; i > 0; i--) {
/* 348 */       this.keys[i] = this.keys[i - 1];
/* 349 */       this.values[i] = this.values[i - 1];
/*     */     } 
/* 351 */     this.keys[0] = paramString1;
/* 352 */     this.values[0] = paramString2;
/* 353 */     this.nkeys++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void set(int paramInt, String paramString1, String paramString2) {
/* 362 */     grow();
/* 363 */     if (paramInt < 0)
/*     */       return; 
/* 365 */     if (paramInt >= this.nkeys) {
/* 366 */       add(paramString1, paramString2);
/*     */     } else {
/* 368 */       this.keys[paramInt] = paramString1;
/* 369 */       this.values[paramInt] = paramString2;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void grow() {
/* 377 */     if (this.keys == null || this.nkeys >= this.keys.length) {
/* 378 */       String[] arrayOfString1 = new String[this.nkeys + 4];
/* 379 */       String[] arrayOfString2 = new String[this.nkeys + 4];
/* 380 */       if (this.keys != null)
/* 381 */         System.arraycopy(this.keys, 0, arrayOfString1, 0, this.nkeys); 
/* 382 */       if (this.values != null)
/* 383 */         System.arraycopy(this.values, 0, arrayOfString2, 0, this.nkeys); 
/* 384 */       this.keys = arrayOfString1;
/* 385 */       this.values = arrayOfString2;
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
/*     */   public synchronized void remove(String paramString) {
/* 397 */     if (paramString == null) {
/* 398 */       for (byte b = 0; b < this.nkeys; b++) {
/* 399 */         while (this.keys[b] == null && b < this.nkeys) {
/* 400 */           for (byte b1 = b; b1 < this.nkeys - 1; b1++) {
/* 401 */             this.keys[b1] = this.keys[b1 + 1];
/* 402 */             this.values[b1] = this.values[b1 + 1];
/*     */           } 
/* 404 */           this.nkeys--;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 408 */       for (byte b = 0; b < this.nkeys; b++) {
/* 409 */         while (paramString.equalsIgnoreCase(this.keys[b]) && b < this.nkeys) {
/* 410 */           for (byte b1 = b; b1 < this.nkeys - 1; b1++) {
/* 411 */             this.keys[b1] = this.keys[b1 + 1];
/* 412 */             this.values[b1] = this.values[b1 + 1];
/*     */           } 
/* 414 */           this.nkeys--;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void set(String paramString1, String paramString2) {
/* 425 */     for (int i = this.nkeys; --i >= 0;) {
/* 426 */       if (paramString1.equalsIgnoreCase(this.keys[i])) {
/* 427 */         this.values[i] = paramString2; return;
/*     */       } 
/*     */     } 
/* 430 */     add(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setIfNotSet(String paramString1, String paramString2) {
/* 438 */     if (findValue(paramString1) == null) {
/* 439 */       add(paramString1, paramString2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String canonicalID(String paramString) {
/* 446 */     if (paramString == null)
/* 447 */       return ""; 
/* 448 */     byte b = 0;
/* 449 */     int i = paramString.length();
/* 450 */     boolean bool = false;
/*     */     char c;
/* 452 */     while (b < i && ((c = paramString.charAt(b)) == '<' || c <= ' ')) {
/*     */       
/* 454 */       b++;
/* 455 */       bool = true;
/*     */     } 
/* 457 */     while (b < i && ((c = paramString.charAt(i - 1)) == '>' || c <= ' ')) {
/*     */       
/* 459 */       i--;
/* 460 */       bool = true;
/*     */     } 
/* 462 */     return bool ? paramString.substring(b, i) : paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public void parseHeader(InputStream paramInputStream) throws IOException {
/* 467 */     synchronized (this) {
/* 468 */       this.nkeys = 0;
/*     */     } 
/* 470 */     mergeHeader(paramInputStream);
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
/*     */   public void mergeHeader(InputStream paramInputStream) throws IOException {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: ifnonnull -> 5
/*     */     //   4: return
/*     */     //   5: bipush #10
/*     */     //   7: newarray char
/*     */     //   9: astore_2
/*     */     //   10: aload_1
/*     */     //   11: invokevirtual read : ()I
/*     */     //   14: istore_3
/*     */     //   15: iload_3
/*     */     //   16: bipush #10
/*     */     //   18: if_icmpeq -> 381
/*     */     //   21: iload_3
/*     */     //   22: bipush #13
/*     */     //   24: if_icmpeq -> 381
/*     */     //   27: iload_3
/*     */     //   28: iflt -> 381
/*     */     //   31: iconst_0
/*     */     //   32: istore #4
/*     */     //   34: iconst_m1
/*     */     //   35: istore #5
/*     */     //   37: iload_3
/*     */     //   38: bipush #32
/*     */     //   40: if_icmple -> 47
/*     */     //   43: iconst_1
/*     */     //   44: goto -> 48
/*     */     //   47: iconst_0
/*     */     //   48: istore #7
/*     */     //   50: aload_2
/*     */     //   51: iload #4
/*     */     //   53: iinc #4, 1
/*     */     //   56: iload_3
/*     */     //   57: i2c
/*     */     //   58: castore
/*     */     //   59: aload_1
/*     */     //   60: invokevirtual read : ()I
/*     */     //   63: dup
/*     */     //   64: istore #6
/*     */     //   66: iflt -> 250
/*     */     //   69: iload #6
/*     */     //   71: lookupswitch default -> 209, 9 -> 140, 10 -> 150, 13 -> 150, 32 -> 144, 58 -> 120
/*     */     //   120: iload #7
/*     */     //   122: ifeq -> 134
/*     */     //   125: iload #4
/*     */     //   127: ifle -> 134
/*     */     //   130: iload #4
/*     */     //   132: istore #5
/*     */     //   134: iconst_0
/*     */     //   135: istore #7
/*     */     //   137: goto -> 209
/*     */     //   140: bipush #32
/*     */     //   142: istore #6
/*     */     //   144: iconst_0
/*     */     //   145: istore #7
/*     */     //   147: goto -> 209
/*     */     //   150: aload_1
/*     */     //   151: invokevirtual read : ()I
/*     */     //   154: istore_3
/*     */     //   155: iload #6
/*     */     //   157: bipush #13
/*     */     //   159: if_icmpne -> 184
/*     */     //   162: iload_3
/*     */     //   163: bipush #10
/*     */     //   165: if_icmpne -> 184
/*     */     //   168: aload_1
/*     */     //   169: invokevirtual read : ()I
/*     */     //   172: istore_3
/*     */     //   173: iload_3
/*     */     //   174: bipush #13
/*     */     //   176: if_icmpne -> 184
/*     */     //   179: aload_1
/*     */     //   180: invokevirtual read : ()I
/*     */     //   183: istore_3
/*     */     //   184: iload_3
/*     */     //   185: bipush #10
/*     */     //   187: if_icmpeq -> 252
/*     */     //   190: iload_3
/*     */     //   191: bipush #13
/*     */     //   193: if_icmpeq -> 252
/*     */     //   196: iload_3
/*     */     //   197: bipush #32
/*     */     //   199: if_icmple -> 205
/*     */     //   202: goto -> 252
/*     */     //   205: bipush #32
/*     */     //   207: istore #6
/*     */     //   209: iload #4
/*     */     //   211: aload_2
/*     */     //   212: arraylength
/*     */     //   213: if_icmplt -> 237
/*     */     //   216: aload_2
/*     */     //   217: arraylength
/*     */     //   218: iconst_2
/*     */     //   219: imul
/*     */     //   220: newarray char
/*     */     //   222: astore #8
/*     */     //   224: aload_2
/*     */     //   225: iconst_0
/*     */     //   226: aload #8
/*     */     //   228: iconst_0
/*     */     //   229: iload #4
/*     */     //   231: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
/*     */     //   234: aload #8
/*     */     //   236: astore_2
/*     */     //   237: aload_2
/*     */     //   238: iload #4
/*     */     //   240: iinc #4, 1
/*     */     //   243: iload #6
/*     */     //   245: i2c
/*     */     //   246: castore
/*     */     //   247: goto -> 59
/*     */     //   250: iconst_m1
/*     */     //   251: istore_3
/*     */     //   252: iload #4
/*     */     //   254: ifle -> 274
/*     */     //   257: aload_2
/*     */     //   258: iload #4
/*     */     //   260: iconst_1
/*     */     //   261: isub
/*     */     //   262: caload
/*     */     //   263: bipush #32
/*     */     //   265: if_icmpgt -> 274
/*     */     //   268: iinc #4, -1
/*     */     //   271: goto -> 252
/*     */     //   274: iload #5
/*     */     //   276: ifgt -> 288
/*     */     //   279: aconst_null
/*     */     //   280: astore #8
/*     */     //   282: iconst_0
/*     */     //   283: istore #5
/*     */     //   285: goto -> 338
/*     */     //   288: aload_2
/*     */     //   289: iconst_0
/*     */     //   290: iload #5
/*     */     //   292: invokestatic copyValueOf : ([CII)Ljava/lang/String;
/*     */     //   295: astore #8
/*     */     //   297: iload #5
/*     */     //   299: iload #4
/*     */     //   301: if_icmpge -> 316
/*     */     //   304: aload_2
/*     */     //   305: iload #5
/*     */     //   307: caload
/*     */     //   308: bipush #58
/*     */     //   310: if_icmpne -> 316
/*     */     //   313: iinc #5, 1
/*     */     //   316: iload #5
/*     */     //   318: iload #4
/*     */     //   320: if_icmpge -> 338
/*     */     //   323: aload_2
/*     */     //   324: iload #5
/*     */     //   326: caload
/*     */     //   327: bipush #32
/*     */     //   329: if_icmpgt -> 338
/*     */     //   332: iinc #5, 1
/*     */     //   335: goto -> 316
/*     */     //   338: iload #5
/*     */     //   340: iload #4
/*     */     //   342: if_icmplt -> 357
/*     */     //   345: new java/lang/String
/*     */     //   348: dup
/*     */     //   349: invokespecial <init> : ()V
/*     */     //   352: astore #9
/*     */     //   354: goto -> 370
/*     */     //   357: aload_2
/*     */     //   358: iload #5
/*     */     //   360: iload #4
/*     */     //   362: iload #5
/*     */     //   364: isub
/*     */     //   365: invokestatic copyValueOf : ([CII)Ljava/lang/String;
/*     */     //   368: astore #9
/*     */     //   370: aload_0
/*     */     //   371: aload #8
/*     */     //   373: aload #9
/*     */     //   375: invokevirtual add : (Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   378: goto -> 15
/*     */     //   381: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #476	-> 0
/*     */     //   #477	-> 4
/*     */     //   #478	-> 5
/*     */     //   #479	-> 10
/*     */     //   #480	-> 15
/*     */     //   #481	-> 31
/*     */     //   #482	-> 34
/*     */     //   #484	-> 37
/*     */     //   #485	-> 50
/*     */     //   #487	-> 59
/*     */     //   #488	-> 69
/*     */     //   #490	-> 120
/*     */     //   #491	-> 130
/*     */     //   #492	-> 134
/*     */     //   #493	-> 137
/*     */     //   #495	-> 140
/*     */     //   #498	-> 144
/*     */     //   #499	-> 147
/*     */     //   #502	-> 150
/*     */     //   #503	-> 155
/*     */     //   #504	-> 168
/*     */     //   #505	-> 173
/*     */     //   #506	-> 179
/*     */     //   #508	-> 184
/*     */     //   #509	-> 202
/*     */     //   #511	-> 205
/*     */     //   #514	-> 209
/*     */     //   #515	-> 216
/*     */     //   #516	-> 224
/*     */     //   #517	-> 234
/*     */     //   #519	-> 237
/*     */     //   #521	-> 250
/*     */     //   #523	-> 252
/*     */     //   #524	-> 268
/*     */     //   #526	-> 274
/*     */     //   #527	-> 279
/*     */     //   #528	-> 282
/*     */     //   #530	-> 288
/*     */     //   #531	-> 297
/*     */     //   #532	-> 313
/*     */     //   #533	-> 316
/*     */     //   #534	-> 332
/*     */     //   #537	-> 338
/*     */     //   #538	-> 345
/*     */     //   #540	-> 357
/*     */     //   #541	-> 370
/*     */     //   #542	-> 378
/*     */     //   #543	-> 381
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
/*     */   public synchronized String toString() {
/* 546 */     String str = super.toString() + this.nkeys + " pairs: ";
/* 547 */     for (byte b = 0; b < this.keys.length && b < this.nkeys; b++) {
/* 548 */       str = str + "{" + this.keys[b] + ": " + this.values[b] + "}";
/*     */     }
/* 550 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/MessageHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */