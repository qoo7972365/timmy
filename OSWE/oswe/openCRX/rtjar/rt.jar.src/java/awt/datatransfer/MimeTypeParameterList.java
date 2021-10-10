/*     */ package java.awt.datatransfer;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MimeTypeParameterList
/*     */   implements Cloneable
/*     */ {
/*     */   private Hashtable<String, String> parameters;
/*     */   private static final String TSPECIALS = "()<>@,;:\\\"/[]?=";
/*     */   
/*     */   public MimeTypeParameterList() {
/*  47 */     this.parameters = new Hashtable<>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MimeTypeParameterList(String paramString) throws MimeTypeParseException {
/*  53 */     this.parameters = new Hashtable<>();
/*     */ 
/*     */     
/*  56 */     parse(paramString);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  60 */     int i = 47721858;
/*  61 */     String str = null;
/*  62 */     Enumeration<String> enumeration = getNames();
/*     */     
/*  64 */     while (enumeration.hasMoreElements()) {
/*  65 */       str = enumeration.nextElement();
/*  66 */       i += str.hashCode();
/*  67 */       i += get(str).hashCode();
/*     */     } 
/*     */     
/*  70 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  80 */     if (!(paramObject instanceof MimeTypeParameterList)) {
/*  81 */       return false;
/*     */     }
/*  83 */     MimeTypeParameterList mimeTypeParameterList = (MimeTypeParameterList)paramObject;
/*  84 */     if (size() != mimeTypeParameterList.size()) {
/*  85 */       return false;
/*     */     }
/*  87 */     String str1 = null;
/*  88 */     String str2 = null;
/*  89 */     String str3 = null;
/*  90 */     Set<Map.Entry<String, String>> set = this.parameters.entrySet();
/*  91 */     Iterator<Map.Entry<String, String>> iterator = set.iterator();
/*  92 */     Map.Entry entry = null;
/*  93 */     while (iterator.hasNext()) {
/*  94 */       entry = iterator.next();
/*  95 */       str1 = (String)entry.getKey();
/*  96 */       str2 = (String)entry.getValue();
/*  97 */       str3 = mimeTypeParameterList.parameters.get(str1);
/*  98 */       if (str2 == null || str3 == null) {
/*     */         
/* 100 */         if (str2 != str3)
/* 101 */           return false;  continue;
/*     */       } 
/* 103 */       if (!str2.equals(str3)) {
/* 104 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void parse(String paramString) throws MimeTypeParseException {
/* 115 */     int i = paramString.length();
/* 116 */     if (i > 0) {
/* 117 */       int j = skipWhiteSpace(paramString, 0);
/* 118 */       int k = 0;
/*     */       
/* 120 */       if (j < i) {
/* 121 */         char c = paramString.charAt(j);
/* 122 */         while (j < i && c == ';') {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 128 */           j++;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 133 */           j = skipWhiteSpace(paramString, j);
/*     */           
/* 135 */           if (j < i) {
/*     */             
/* 137 */             k = j;
/* 138 */             c = paramString.charAt(j);
/* 139 */             while (j < i && isTokenChar(c)) {
/* 140 */               j++;
/* 141 */               c = paramString.charAt(j);
/*     */             } 
/* 143 */             String str = paramString.substring(k, j).toLowerCase();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 148 */             j = skipWhiteSpace(paramString, j);
/*     */             
/* 150 */             if (j < i && paramString.charAt(j) == '=') {
/*     */               
/* 152 */               j++;
/*     */ 
/*     */               
/* 155 */               j = skipWhiteSpace(paramString, j);
/*     */               
/* 157 */               if (j < i) {
/*     */                 String str1;
/* 159 */                 c = paramString.charAt(j);
/* 160 */                 if (c == '"') {
/*     */ 
/*     */                   
/* 163 */                   k = ++j;
/*     */                   
/* 165 */                   if (j < i) {
/*     */                     
/* 167 */                     boolean bool = false;
/* 168 */                     while (j < i && !bool) {
/* 169 */                       c = paramString.charAt(j);
/* 170 */                       if (c == '\\') {
/*     */                         
/* 172 */                         j += 2; continue;
/* 173 */                       }  if (c == '"') {
/*     */                         
/* 175 */                         bool = true; continue;
/*     */                       } 
/* 177 */                       j++;
/*     */                     } 
/*     */                     
/* 180 */                     if (c == '"') {
/* 181 */                       str1 = unquote(paramString.substring(k, j));
/*     */                       
/* 183 */                       j++;
/*     */                     } else {
/* 185 */                       throw new MimeTypeParseException("Encountered unterminated quoted parameter value.");
/*     */                     } 
/*     */                   } else {
/* 188 */                     throw new MimeTypeParseException("Encountered unterminated quoted parameter value.");
/*     */                   } 
/* 190 */                 } else if (isTokenChar(c)) {
/*     */                   
/* 192 */                   k = j;
/* 193 */                   boolean bool = false;
/* 194 */                   while (j < i && !bool) {
/* 195 */                     c = paramString.charAt(j);
/*     */                     
/* 197 */                     if (isTokenChar(c)) {
/* 198 */                       j++; continue;
/*     */                     } 
/* 200 */                     bool = true;
/*     */                   } 
/*     */                   
/* 203 */                   str1 = paramString.substring(k, j);
/*     */                 } else {
/*     */                   
/* 206 */                   throw new MimeTypeParseException("Unexpected character encountered at index " + j);
/*     */                 } 
/*     */ 
/*     */                 
/* 210 */                 this.parameters.put(str, str1);
/*     */               } else {
/* 212 */                 throw new MimeTypeParseException("Couldn't find a value for parameter named " + str);
/*     */               } 
/*     */             } else {
/* 215 */               throw new MimeTypeParseException("Couldn't find the '=' that separates a parameter name from its value.");
/*     */             } 
/*     */           } else {
/* 218 */             throw new MimeTypeParseException("Couldn't find parameter name");
/*     */           } 
/*     */ 
/*     */           
/* 222 */           j = skipWhiteSpace(paramString, j);
/* 223 */           if (j < i) {
/* 224 */             c = paramString.charAt(j);
/*     */           }
/*     */         } 
/* 227 */         if (j < i) {
/* 228 */           throw new MimeTypeParseException("More characters encountered in input than expected.");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 238 */     return this.parameters.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 245 */     return this.parameters.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String get(String paramString) {
/* 253 */     return this.parameters.get(paramString.trim().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString1, String paramString2) {
/* 261 */     this.parameters.put(paramString1.trim().toLowerCase(), paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(String paramString) {
/* 268 */     this.parameters.remove(paramString.trim().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getNames() {
/* 275 */     return this.parameters.keys();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 280 */     StringBuilder stringBuilder = new StringBuilder(this.parameters.size() * 16);
/*     */     
/* 282 */     Enumeration<String> enumeration = this.parameters.keys();
/* 283 */     while (enumeration.hasMoreElements()) {
/*     */       
/* 285 */       stringBuilder.append("; ");
/*     */       
/* 287 */       String str = enumeration.nextElement();
/* 288 */       stringBuilder.append(str);
/* 289 */       stringBuilder.append('=');
/* 290 */       stringBuilder.append(quote(this.parameters.get(str)));
/*     */     } 
/*     */     
/* 293 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 301 */     MimeTypeParameterList mimeTypeParameterList = null;
/*     */     try {
/* 303 */       mimeTypeParameterList = (MimeTypeParameterList)super.clone();
/* 304 */     } catch (CloneNotSupportedException cloneNotSupportedException) {}
/*     */     
/* 306 */     mimeTypeParameterList.parameters = (Hashtable<String, String>)this.parameters.clone();
/* 307 */     return mimeTypeParameterList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isTokenChar(char paramChar) {
/* 318 */     return (paramChar > ' ' && paramChar < '' && "()<>@,;:\\\"/[]?=".indexOf(paramChar) < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int skipWhiteSpace(String paramString, int paramInt) {
/* 326 */     int i = paramString.length();
/* 327 */     if (paramInt < i) {
/* 328 */       char c = paramString.charAt(paramInt);
/* 329 */       while (paramInt < i && Character.isWhitespace(c)) {
/* 330 */         paramInt++;
/* 331 */         c = paramString.charAt(paramInt);
/*     */       } 
/*     */     } 
/*     */     
/* 335 */     return paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String quote(String paramString) {
/* 342 */     boolean bool = false;
/*     */ 
/*     */     
/* 345 */     int i = paramString.length();
/* 346 */     for (byte b = 0; b < i && !bool; b++) {
/* 347 */       bool = !isTokenChar(paramString.charAt(b)) ? true : false;
/*     */     }
/*     */     
/* 350 */     if (bool) {
/* 351 */       StringBuilder stringBuilder = new StringBuilder((int)(i * 1.5D));
/*     */ 
/*     */       
/* 354 */       stringBuilder.append('"');
/*     */ 
/*     */       
/* 357 */       for (byte b1 = 0; b1 < i; b1++) {
/* 358 */         char c = paramString.charAt(b1);
/* 359 */         if (c == '\\' || c == '"') {
/* 360 */           stringBuilder.append('\\');
/*     */         }
/* 362 */         stringBuilder.append(c);
/*     */       } 
/*     */ 
/*     */       
/* 366 */       stringBuilder.append('"');
/*     */       
/* 368 */       return stringBuilder.toString();
/*     */     } 
/*     */ 
/*     */     
/* 372 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String unquote(String paramString) {
/* 380 */     int i = paramString.length();
/* 381 */     StringBuilder stringBuilder = new StringBuilder(i);
/*     */     
/* 383 */     boolean bool = false;
/* 384 */     for (byte b = 0; b < i; b++) {
/* 385 */       char c = paramString.charAt(b);
/* 386 */       if (!bool && c != '\\') {
/* 387 */         stringBuilder.append(c);
/* 388 */       } else if (bool) {
/* 389 */         stringBuilder.append(c);
/* 390 */         bool = false;
/*     */       } else {
/* 392 */         bool = true;
/*     */       } 
/*     */     } 
/*     */     
/* 396 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/datatransfer/MimeTypeParameterList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */