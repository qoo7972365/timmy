/*     */ package java.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringTokenizer
/*     */   implements Enumeration<Object>
/*     */ {
/*     */   private int currentPosition;
/*     */   private int newPosition;
/*     */   private int maxPosition;
/*     */   private String str;
/*     */   private String delimiters;
/*     */   private boolean retDelims;
/*     */   private boolean delimsChanged;
/*     */   private int maxDelimCodePoint;
/*     */   private boolean hasSurrogates = false;
/*     */   private int[] delimiterCodePoints;
/*     */   
/*     */   private void setMaxDelimCodePoint() {
/* 143 */     if (this.delimiters == null) {
/* 144 */       this.maxDelimCodePoint = 0;
/*     */       
/*     */       return;
/*     */     } 
/* 148 */     int i = 0;
/*     */     
/* 150 */     byte b = 0; int j;
/* 151 */     for (j = 0; j < this.delimiters.length(); j += Character.charCount(k)) {
/* 152 */       int k = this.delimiters.charAt(j);
/* 153 */       if (k >= 55296 && k <= 57343) {
/* 154 */         k = this.delimiters.codePointAt(j);
/* 155 */         this.hasSurrogates = true;
/*     */       } 
/* 157 */       if (i < k)
/* 158 */         i = k; 
/* 159 */       b++;
/*     */     } 
/* 161 */     this.maxDelimCodePoint = i;
/*     */     
/* 163 */     if (this.hasSurrogates) {
/* 164 */       this.delimiterCodePoints = new int[b]; int k;
/* 165 */       for (j = 0, k = 0; j < b; j++, k += Character.charCount(m)) {
/* 166 */         int m = this.delimiters.codePointAt(k);
/* 167 */         this.delimiterCodePoints[j] = m;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringTokenizer(String paramString1, String paramString2, boolean paramBoolean) {
/* 195 */     this.currentPosition = 0;
/* 196 */     this.newPosition = -1;
/* 197 */     this.delimsChanged = false;
/* 198 */     this.str = paramString1;
/* 199 */     this.maxPosition = paramString1.length();
/* 200 */     this.delimiters = paramString2;
/* 201 */     this.retDelims = paramBoolean;
/* 202 */     setMaxDelimCodePoint();
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
/*     */   public StringTokenizer(String paramString1, String paramString2) {
/* 221 */     this(paramString1, paramString2, false);
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
/*     */   public StringTokenizer(String paramString) {
/* 236 */     this(paramString, " \t\n\r\f", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int skipDelimiters(int paramInt) {
/* 245 */     if (this.delimiters == null) {
/* 246 */       throw new NullPointerException();
/*     */     }
/* 248 */     int i = paramInt;
/* 249 */     while (!this.retDelims && i < this.maxPosition) {
/* 250 */       if (!this.hasSurrogates) {
/* 251 */         char c = this.str.charAt(i);
/* 252 */         if (c > this.maxDelimCodePoint || this.delimiters.indexOf(c) < 0)
/*     */           break; 
/* 254 */         i++; continue;
/*     */       } 
/* 256 */       int j = this.str.codePointAt(i);
/* 257 */       if (j > this.maxDelimCodePoint || !isDelimiter(j)) {
/*     */         break;
/*     */       }
/* 260 */       i += Character.charCount(j);
/*     */     } 
/*     */     
/* 263 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int scanToken(int paramInt) {
/* 271 */     int i = paramInt;
/* 272 */     while (i < this.maxPosition) {
/* 273 */       if (!this.hasSurrogates) {
/* 274 */         char c = this.str.charAt(i);
/* 275 */         if (c <= this.maxDelimCodePoint && this.delimiters.indexOf(c) >= 0)
/*     */           break; 
/* 277 */         i++; continue;
/*     */       } 
/* 279 */       int j = this.str.codePointAt(i);
/* 280 */       if (j <= this.maxDelimCodePoint && isDelimiter(j))
/*     */         break; 
/* 282 */       i += Character.charCount(j);
/*     */     } 
/*     */     
/* 285 */     if (this.retDelims && paramInt == i)
/* 286 */       if (!this.hasSurrogates) {
/* 287 */         char c = this.str.charAt(i);
/* 288 */         if (c <= this.maxDelimCodePoint && this.delimiters.indexOf(c) >= 0)
/* 289 */           i++; 
/*     */       } else {
/* 291 */         int j = this.str.codePointAt(i);
/* 292 */         if (j <= this.maxDelimCodePoint && isDelimiter(j)) {
/* 293 */           i += Character.charCount(j);
/*     */         }
/*     */       }  
/* 296 */     return i;
/*     */   }
/*     */   
/*     */   private boolean isDelimiter(int paramInt) {
/* 300 */     for (byte b = 0; b < this.delimiterCodePoints.length; b++) {
/* 301 */       if (this.delimiterCodePoints[b] == paramInt) {
/* 302 */         return true;
/*     */       }
/*     */     } 
/* 305 */     return false;
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
/*     */   public boolean hasMoreTokens() {
/* 323 */     this.newPosition = skipDelimiters(this.currentPosition);
/* 324 */     return (this.newPosition < this.maxPosition);
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
/*     */   public String nextToken() {
/* 341 */     this
/* 342 */       .currentPosition = (this.newPosition >= 0 && !this.delimsChanged) ? this.newPosition : skipDelimiters(this.currentPosition);
/*     */ 
/*     */     
/* 345 */     this.delimsChanged = false;
/* 346 */     this.newPosition = -1;
/*     */     
/* 348 */     if (this.currentPosition >= this.maxPosition)
/* 349 */       throw new NoSuchElementException(); 
/* 350 */     int i = this.currentPosition;
/* 351 */     this.currentPosition = scanToken(this.currentPosition);
/* 352 */     return this.str.substring(i, this.currentPosition);
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
/*     */   public String nextToken(String paramString) {
/* 371 */     this.delimiters = paramString;
/*     */ 
/*     */     
/* 374 */     this.delimsChanged = true;
/*     */     
/* 376 */     setMaxDelimCodePoint();
/* 377 */     return nextToken();
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
/*     */   public boolean hasMoreElements() {
/* 391 */     return hasMoreTokens();
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
/*     */   public Object nextElement() {
/* 407 */     return nextToken();
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
/*     */   public int countTokens() {
/* 420 */     byte b = 0;
/* 421 */     int i = this.currentPosition;
/* 422 */     while (i < this.maxPosition) {
/* 423 */       i = skipDelimiters(i);
/* 424 */       if (i >= this.maxPosition)
/*     */         break; 
/* 426 */       i = scanToken(i);
/* 427 */       b++;
/*     */     } 
/* 429 */     return b;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/StringTokenizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */