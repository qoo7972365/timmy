/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringJoiner;
/*     */ import sun.security.util.DerEncoder;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RDN
/*     */ {
/*     */   final AVA[] assertion;
/*     */   private volatile List<AVA> avaList;
/*     */   private volatile String canonicalString;
/*     */   
/*     */   public RDN(String paramString) throws IOException {
/*  88 */     this(paramString, Collections.emptyMap());
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
/*     */   public RDN(String paramString, Map<String, String> paramMap) throws IOException {
/* 104 */     int i = 0;
/* 105 */     int j = 0;
/* 106 */     int k = 0;
/* 107 */     ArrayList<AVA> arrayList = new ArrayList(3);
/* 108 */     int m = paramString.indexOf('+');
/* 109 */     while (m >= 0) {
/* 110 */       i += X500Name.countQuotes(paramString, j, m);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       if (m > 0 && paramString.charAt(m - 1) != '\\' && i != 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 123 */         String str1 = paramString.substring(k, m);
/* 124 */         if (str1.length() == 0) {
/* 125 */           throw new IOException("empty AVA in RDN \"" + paramString + "\"");
/*     */         }
/*     */ 
/*     */         
/* 129 */         AVA aVA1 = new AVA(new StringReader(str1), paramMap);
/* 130 */         arrayList.add(aVA1);
/*     */ 
/*     */         
/* 133 */         k = m + 1;
/*     */ 
/*     */         
/* 136 */         i = 0;
/*     */       } 
/* 138 */       j = m + 1;
/* 139 */       m = paramString.indexOf('+', j);
/*     */     } 
/*     */ 
/*     */     
/* 143 */     String str = paramString.substring(k);
/* 144 */     if (str.length() == 0) {
/* 145 */       throw new IOException("empty AVA in RDN \"" + paramString + "\"");
/*     */     }
/* 147 */     AVA aVA = new AVA(new StringReader(str), paramMap);
/* 148 */     arrayList.add(aVA);
/*     */     
/* 150 */     this.assertion = arrayList.<AVA>toArray(new AVA[arrayList.size()]);
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
/*     */   RDN(String paramString1, String paramString2) throws IOException {
/* 165 */     this(paramString1, paramString2, Collections.emptyMap());
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
/*     */   RDN(String paramString1, String paramString2, Map<String, String> paramMap) throws IOException {
/* 182 */     if (!paramString2.equalsIgnoreCase("RFC2253")) {
/* 183 */       throw new IOException("Unsupported format " + paramString2);
/*     */     }
/* 185 */     int i = 0;
/* 186 */     int j = 0;
/* 187 */     ArrayList<AVA> arrayList = new ArrayList(3);
/* 188 */     int k = paramString1.indexOf('+');
/* 189 */     while (k >= 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 197 */       if (k > 0 && paramString1.charAt(k - 1) != '\\') {
/*     */ 
/*     */ 
/*     */         
/* 201 */         String str1 = paramString1.substring(j, k);
/* 202 */         if (str1.length() == 0) {
/* 203 */           throw new IOException("empty AVA in RDN \"" + paramString1 + "\"");
/*     */         }
/*     */ 
/*     */         
/* 207 */         AVA aVA1 = new AVA(new StringReader(str1), 3, paramMap);
/*     */         
/* 209 */         arrayList.add(aVA1);
/*     */ 
/*     */         
/* 212 */         j = k + 1;
/*     */       } 
/* 214 */       i = k + 1;
/* 215 */       k = paramString1.indexOf('+', i);
/*     */     } 
/*     */ 
/*     */     
/* 219 */     String str = paramString1.substring(j);
/* 220 */     if (str.length() == 0) {
/* 221 */       throw new IOException("empty AVA in RDN \"" + paramString1 + "\"");
/*     */     }
/* 223 */     AVA aVA = new AVA(new StringReader(str), 3, paramMap);
/* 224 */     arrayList.add(aVA);
/*     */     
/* 226 */     this.assertion = arrayList.<AVA>toArray(new AVA[arrayList.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RDN(DerValue paramDerValue) throws IOException {
/* 237 */     if (paramDerValue.tag != 49) {
/* 238 */       throw new IOException("X500 RDN");
/*     */     }
/* 240 */     DerInputStream derInputStream = new DerInputStream(paramDerValue.toByteArray());
/* 241 */     DerValue[] arrayOfDerValue = derInputStream.getSet(5);
/*     */     
/* 243 */     this.assertion = new AVA[arrayOfDerValue.length];
/* 244 */     for (byte b = 0; b < arrayOfDerValue.length; b++) {
/* 245 */       this.assertion[b] = new AVA(arrayOfDerValue[b]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RDN(int paramInt) {
/* 255 */     this.assertion = new AVA[paramInt];
/*     */   }
/*     */   public RDN(AVA paramAVA) {
/* 258 */     if (paramAVA == null) {
/* 259 */       throw new NullPointerException();
/*     */     }
/* 261 */     this.assertion = new AVA[] { paramAVA };
/*     */   }
/*     */   
/*     */   public RDN(AVA[] paramArrayOfAVA) {
/* 265 */     this.assertion = (AVA[])paramArrayOfAVA.clone();
/* 266 */     for (byte b = 0; b < this.assertion.length; b++) {
/* 267 */       if (this.assertion[b] == null) {
/* 268 */         throw new NullPointerException();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AVA> avas() {
/* 277 */     List<AVA> list = this.avaList;
/* 278 */     if (list == null) {
/* 279 */       list = Collections.unmodifiableList(Arrays.asList(this.assertion));
/* 280 */       this.avaList = list;
/*     */     } 
/* 282 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 289 */     return this.assertion.length;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 293 */     if (this == paramObject) {
/* 294 */       return true;
/*     */     }
/* 296 */     if (!(paramObject instanceof RDN)) {
/* 297 */       return false;
/*     */     }
/* 299 */     RDN rDN = (RDN)paramObject;
/* 300 */     if (this.assertion.length != rDN.assertion.length) {
/* 301 */       return false;
/*     */     }
/* 303 */     String str1 = toRFC2253String(true);
/* 304 */     String str2 = rDN.toRFC2253String(true);
/* 305 */     return str1.equals(str2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 315 */     return toRFC2253String(true).hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DerValue findAttribute(ObjectIdentifier paramObjectIdentifier) {
/* 325 */     for (byte b = 0; b < this.assertion.length; b++) {
/* 326 */       if ((this.assertion[b]).oid.equals(paramObjectIdentifier)) {
/* 327 */         return (this.assertion[b]).value;
/*     */       }
/*     */     } 
/* 330 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 340 */     paramDerOutputStream.putOrderedSetOf((byte)49, (DerEncoder[])this.assertion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 349 */     if (this.assertion.length == 1) {
/* 350 */       return this.assertion[0].toString();
/*     */     }
/*     */     
/* 353 */     StringBuilder stringBuilder = new StringBuilder();
/* 354 */     for (byte b = 0; b < this.assertion.length; b++) {
/* 355 */       if (b != 0) {
/* 356 */         stringBuilder.append(" + ");
/*     */       }
/* 358 */       stringBuilder.append(this.assertion[b].toString());
/*     */     } 
/* 360 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toRFC1779String() {
/* 368 */     return toRFC1779String(Collections.emptyMap());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toRFC1779String(Map<String, String> paramMap) {
/* 377 */     if (this.assertion.length == 1) {
/* 378 */       return this.assertion[0].toRFC1779String(paramMap);
/*     */     }
/*     */     
/* 381 */     StringBuilder stringBuilder = new StringBuilder();
/* 382 */     for (byte b = 0; b < this.assertion.length; b++) {
/* 383 */       if (b != 0) {
/* 384 */         stringBuilder.append(" + ");
/*     */       }
/* 386 */       stringBuilder.append(this.assertion[b].toRFC1779String(paramMap));
/*     */     } 
/* 388 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toRFC2253String() {
/* 396 */     return 
/* 397 */       toRFC2253StringInternal(false, Collections.emptyMap());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toRFC2253String(Map<String, String> paramMap) {
/* 406 */     return toRFC2253StringInternal(false, paramMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toRFC2253String(boolean paramBoolean) {
/* 416 */     if (!paramBoolean) {
/* 417 */       return 
/* 418 */         toRFC2253StringInternal(false, Collections.emptyMap());
/*     */     }
/* 420 */     String str = this.canonicalString;
/* 421 */     if (str == null) {
/*     */       
/* 423 */       str = toRFC2253StringInternal(true, Collections.emptyMap());
/* 424 */       this.canonicalString = str;
/*     */     } 
/* 426 */     return str;
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
/*     */   private String toRFC2253StringInternal(boolean paramBoolean, Map<String, String> paramMap) {
/* 442 */     if (this.assertion.length == 1) {
/* 443 */       return paramBoolean ? this.assertion[0].toRFC2253CanonicalString() : this.assertion[0]
/* 444 */         .toRFC2253String(paramMap);
/*     */     }
/*     */     
/* 447 */     AVA[] arrayOfAVA = this.assertion;
/* 448 */     if (paramBoolean) {
/*     */ 
/*     */       
/* 451 */       arrayOfAVA = (AVA[])this.assertion.clone();
/* 452 */       Arrays.sort(arrayOfAVA, AVAComparator.getInstance());
/*     */     } 
/* 454 */     StringJoiner stringJoiner = new StringJoiner("+");
/* 455 */     for (AVA aVA : arrayOfAVA) {
/* 456 */       stringJoiner.add(paramBoolean ? aVA.toRFC2253CanonicalString() : aVA
/* 457 */           .toRFC2253String(paramMap));
/*     */     }
/* 459 */     return stringJoiner.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/RDN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */