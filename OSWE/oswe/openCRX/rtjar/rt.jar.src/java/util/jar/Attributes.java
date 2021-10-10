/*     */ package java.util.jar;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import sun.misc.ASCIICaseInsensitiveComparator;
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
/*     */ public class Attributes
/*     */   implements Map<Object, Object>, Cloneable
/*     */ {
/*     */   protected Map<Object, Object> map;
/*     */   
/*     */   public Attributes() {
/*  64 */     this(11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attributes(int paramInt) {
/*  74 */     this.map = new HashMap<>(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attributes(Attributes paramAttributes) {
/*  84 */     this.map = new HashMap<>(paramAttributes);
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
/*     */   public Object get(Object paramObject) {
/*  97 */     return this.map.get(paramObject);
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
/*     */   public String getValue(String paramString) {
/* 116 */     return (String)get(new Name(paramString));
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
/*     */   public String getValue(Name paramName) {
/* 133 */     return (String)get(paramName);
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
/*     */   public Object put(Object paramObject1, Object paramObject2) {
/* 148 */     return this.map.put(paramObject1, paramObject2);
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
/*     */   public String putValue(String paramString1, String paramString2) {
/* 168 */     return (String)put(new Name(paramString1), paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(Object paramObject) {
/* 179 */     return this.map.remove(paramObject);
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
/*     */   public boolean containsValue(Object paramObject) {
/* 191 */     return this.map.containsValue(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object paramObject) {
/* 201 */     return this.map.containsKey(paramObject);
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
/*     */   public void putAll(Map<?, ?> paramMap) {
/* 213 */     if (!Attributes.class.isInstance(paramMap))
/* 214 */       throw new ClassCastException(); 
/* 215 */     for (Map.Entry<?, ?> entry : paramMap.entrySet()) {
/* 216 */       put(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 223 */     this.map.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 230 */     return this.map.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 237 */     return this.map.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Object> keySet() {
/* 244 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Object> values() {
/* 251 */     return this.map.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<Object, Object>> entrySet() {
/* 259 */     return this.map.entrySet();
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
/*     */   public boolean equals(Object paramObject) {
/* 271 */     return this.map.equals(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 278 */     return this.map.hashCode();
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
/*     */   public Object clone() {
/* 291 */     return new Attributes(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutputStream paramDataOutputStream) throws IOException {
/* 299 */     Iterator<Map.Entry<Object, Object>> iterator = entrySet().iterator();
/* 300 */     while (iterator.hasNext()) {
/* 301 */       Map.Entry entry = iterator.next();
/*     */       
/* 303 */       StringBuffer stringBuffer = new StringBuffer(((Name)entry.getKey()).toString());
/* 304 */       stringBuffer.append(": ");
/*     */       
/* 306 */       String str = (String)entry.getValue();
/* 307 */       if (str != null) {
/* 308 */         byte[] arrayOfByte = str.getBytes("UTF8");
/* 309 */         str = new String(arrayOfByte, 0, 0, arrayOfByte.length);
/*     */       } 
/* 311 */       stringBuffer.append(str);
/*     */       
/* 313 */       stringBuffer.append("\r\n");
/* 314 */       Manifest.make72Safe(stringBuffer);
/* 315 */       paramDataOutputStream.writeBytes(stringBuffer.toString());
/*     */     } 
/* 317 */     paramDataOutputStream.writeBytes("\r\n");
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
/*     */   void writeMain(DataOutputStream paramDataOutputStream) throws IOException {
/* 330 */     String str1 = Name.MANIFEST_VERSION.toString();
/* 331 */     String str2 = getValue(str1);
/* 332 */     if (str2 == null) {
/* 333 */       str1 = Name.SIGNATURE_VERSION.toString();
/* 334 */       str2 = getValue(str1);
/*     */     } 
/*     */     
/* 337 */     if (str2 != null) {
/* 338 */       paramDataOutputStream.writeBytes(str1 + ": " + str2 + "\r\n");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 343 */     Iterator<Map.Entry<Object, Object>> iterator = entrySet().iterator();
/* 344 */     while (iterator.hasNext()) {
/* 345 */       Map.Entry entry = iterator.next();
/* 346 */       String str = ((Name)entry.getKey()).toString();
/* 347 */       if (str2 != null && !str.equalsIgnoreCase(str1)) {
/*     */         
/* 349 */         StringBuffer stringBuffer = new StringBuffer(str);
/* 350 */         stringBuffer.append(": ");
/*     */         
/* 352 */         String str3 = (String)entry.getValue();
/* 353 */         if (str3 != null) {
/* 354 */           byte[] arrayOfByte = str3.getBytes("UTF8");
/* 355 */           str3 = new String(arrayOfByte, 0, 0, arrayOfByte.length);
/*     */         } 
/* 357 */         stringBuffer.append(str3);
/*     */         
/* 359 */         stringBuffer.append("\r\n");
/* 360 */         Manifest.make72Safe(stringBuffer);
/* 361 */         paramDataOutputStream.writeBytes(stringBuffer.toString());
/*     */       } 
/*     */     } 
/* 364 */     paramDataOutputStream.writeBytes("\r\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void read(Manifest.FastInputStream paramFastInputStream, byte[] paramArrayOfbyte) throws IOException {
/* 372 */     String str1 = null, str2 = null;
/* 373 */     byte[] arrayOfByte = null;
/*     */     
/*     */     int i;
/* 376 */     while ((i = paramFastInputStream.readLine(paramArrayOfbyte)) != -1) {
/* 377 */       boolean bool = false;
/* 378 */       if (paramArrayOfbyte[--i] != 10) {
/* 379 */         throw new IOException("line too long");
/*     */       }
/* 381 */       if (i > 0 && paramArrayOfbyte[i - 1] == 13) {
/* 382 */         i--;
/*     */       }
/* 384 */       if (i == 0) {
/*     */         break;
/*     */       }
/* 387 */       byte b = 0;
/* 388 */       if (paramArrayOfbyte[0] == 32) {
/*     */         
/* 390 */         if (str1 == null) {
/* 391 */           throw new IOException("misplaced continuation line");
/*     */         }
/* 393 */         bool = true;
/* 394 */         byte[] arrayOfByte1 = new byte[arrayOfByte.length + i - 1];
/* 395 */         System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, arrayOfByte.length);
/* 396 */         System.arraycopy(paramArrayOfbyte, 1, arrayOfByte1, arrayOfByte.length, i - 1);
/* 397 */         if (paramFastInputStream.peek() == 32) {
/* 398 */           arrayOfByte = arrayOfByte1;
/*     */           continue;
/*     */         } 
/* 401 */         str2 = new String(arrayOfByte1, 0, arrayOfByte1.length, "UTF8");
/* 402 */         arrayOfByte = null;
/*     */       } else {
/* 404 */         while (paramArrayOfbyte[b++] != 58) {
/* 405 */           if (b >= i) {
/* 406 */             throw new IOException("invalid header field");
/*     */           }
/*     */         } 
/* 409 */         if (paramArrayOfbyte[b++] != 32) {
/* 410 */           throw new IOException("invalid header field");
/*     */         }
/* 412 */         str1 = new String(paramArrayOfbyte, 0, 0, b - 2);
/* 413 */         if (paramFastInputStream.peek() == 32) {
/* 414 */           arrayOfByte = new byte[i - b];
/* 415 */           System.arraycopy(paramArrayOfbyte, b, arrayOfByte, 0, i - b);
/*     */           continue;
/*     */         } 
/* 418 */         str2 = new String(paramArrayOfbyte, b, i - b, "UTF8");
/*     */       } 
/*     */       try {
/* 421 */         if (putValue(str1, str2) != null && !bool) {
/* 422 */           PlatformLogger.getLogger("java.util.jar").warning("Duplicate name in Manifest: " + str1 + ".\nEnsure that the manifest does not have duplicate entries, and\nthat blank lines separate individual sections in both your\nmanifest and in the META-INF/MANIFEST.MF entry in the jar file.");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 432 */       catch (IllegalArgumentException illegalArgumentException) {
/* 433 */         throw new IOException("invalid header field name: " + str1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Name
/*     */   {
/*     */     private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 449 */     private int hashCode = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Name(String param1String) {
/* 460 */       if (param1String == null) {
/* 461 */         throw new NullPointerException("name");
/*     */       }
/* 463 */       if (!isValid(param1String)) {
/* 464 */         throw new IllegalArgumentException(param1String);
/*     */       }
/* 466 */       this.name = param1String.intern();
/*     */     }
/*     */     
/*     */     private static boolean isValid(String param1String) {
/* 470 */       int i = param1String.length();
/* 471 */       if (i > 70 || i == 0) {
/* 472 */         return false;
/*     */       }
/* 474 */       for (byte b = 0; b < i; b++) {
/* 475 */         if (!isValid(param1String.charAt(b))) {
/* 476 */           return false;
/*     */         }
/*     */       } 
/* 479 */       return true;
/*     */     }
/*     */     
/*     */     private static boolean isValid(char param1Char) {
/* 483 */       return (isAlpha(param1Char) || isDigit(param1Char) || param1Char == '_' || param1Char == '-');
/*     */     }
/*     */     
/*     */     private static boolean isAlpha(char param1Char) {
/* 487 */       return ((param1Char >= 'a' && param1Char <= 'z') || (param1Char >= 'A' && param1Char <= 'Z'));
/*     */     }
/*     */     
/*     */     private static boolean isDigit(char param1Char) {
/* 491 */       return (param1Char >= '0' && param1Char <= '9');
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 501 */       if (param1Object instanceof Name) {
/* 502 */         Comparator<String> comparator = ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER;
/* 503 */         return (comparator.compare(this.name, ((Name)param1Object).name) == 0);
/*     */       } 
/* 505 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 513 */       if (this.hashCode == -1) {
/* 514 */         this.hashCode = ASCIICaseInsensitiveComparator.lowerCaseHashCode(this.name);
/*     */       }
/* 516 */       return this.hashCode;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 523 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 533 */     public static final Name MANIFEST_VERSION = new Name("Manifest-Version");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 541 */     public static final Name SIGNATURE_VERSION = new Name("Signature-Version");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 547 */     public static final Name CONTENT_TYPE = new Name("Content-Type");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 556 */     public static final Name CLASS_PATH = new Name("Class-Path");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 565 */     public static final Name MAIN_CLASS = new Name("Main-Class");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 573 */     public static final Name SEALED = new Name("Sealed");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 581 */     public static final Name EXTENSION_LIST = new Name("Extension-List");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 589 */     public static final Name EXTENSION_NAME = new Name("Extension-Name");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/* 600 */     public static final Name EXTENSION_INSTALLATION = new Name("Extension-Installation");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 608 */     public static final Name IMPLEMENTATION_TITLE = new Name("Implementation-Title");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 616 */     public static final Name IMPLEMENTATION_VERSION = new Name("Implementation-Version");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 624 */     public static final Name IMPLEMENTATION_VENDOR = new Name("Implementation-Vendor");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/* 635 */     public static final Name IMPLEMENTATION_VENDOR_ID = new Name("Implementation-Vendor-Id");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/* 646 */     public static final Name IMPLEMENTATION_URL = new Name("Implementation-URL");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 654 */     public static final Name SPECIFICATION_TITLE = new Name("Specification-Title");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 662 */     public static final Name SPECIFICATION_VERSION = new Name("Specification-Version");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 670 */     public static final Name SPECIFICATION_VENDOR = new Name("Specification-Vendor");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/jar/Attributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */