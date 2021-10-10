/*     */ package com.sun.xml.internal.messaging.saaj.packaging.mime.internet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HeaderTokenizer
/*     */ {
/*     */   private String string;
/*     */   private boolean skipComments;
/*     */   private String delimiters;
/*     */   private int currentPos;
/*     */   private int maxPos;
/*     */   private int nextPos;
/*     */   private int peekPos;
/*     */   public static final String RFC822 = "()<>@,;:\\\"\t .[]";
/*     */   public static final String MIME = "()<>@,;:\\\"\t []/?=";
/*     */   
/*     */   public static class Token
/*     */   {
/*     */     private int type;
/*     */     private String value;
/*     */     public static final int ATOM = -1;
/*     */     public static final int QUOTEDSTRING = -2;
/*     */     public static final int COMMENT = -3;
/*     */     public static final int EOF = -4;
/*     */     
/*     */     public Token(int type, String value) {
/*  87 */       this.type = type;
/*  88 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getType() {
/* 108 */       return this.type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getValue() {
/* 120 */       return this.value;
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
/* 143 */   private static final Token EOFToken = new Token(-4, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderTokenizer(String header, String delimiters, boolean skipComments) {
/* 158 */     this.string = (header == null) ? "" : header;
/* 159 */     this.skipComments = skipComments;
/* 160 */     this.delimiters = delimiters;
/* 161 */     this.currentPos = this.nextPos = this.peekPos = 0;
/* 162 */     this.maxPos = this.string.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderTokenizer(String header, String delimiters) {
/* 172 */     this(header, delimiters, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderTokenizer(String header) {
/* 181 */     this(header, "()<>@,;:\\\"\t .[]");
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
/*     */   public Token next() throws ParseException {
/* 196 */     this.currentPos = this.nextPos;
/* 197 */     Token tk = getNext();
/* 198 */     this.nextPos = this.peekPos = this.currentPos;
/* 199 */     return tk;
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
/*     */   public Token peek() throws ParseException {
/* 214 */     this.currentPos = this.peekPos;
/* 215 */     Token tk = getNext();
/* 216 */     this.peekPos = this.currentPos;
/* 217 */     return tk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRemainder() {
/* 227 */     return this.string.substring(this.nextPos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Token getNext() throws ParseException {
/* 237 */     if (this.currentPos >= this.maxPos) {
/* 238 */       return EOFToken;
/*     */     }
/*     */     
/* 241 */     if (skipWhiteSpace() == -4) {
/* 242 */       return EOFToken;
/*     */     }
/*     */ 
/*     */     
/* 246 */     boolean filter = false;
/*     */     
/* 248 */     char c = this.string.charAt(this.currentPos);
/*     */ 
/*     */ 
/*     */     
/* 252 */     while (c == '(') {
/*     */ 
/*     */       
/* 255 */       int i = ++this.currentPos, nesting = 1;
/* 256 */       for (; nesting > 0 && this.currentPos < this.maxPos; 
/* 257 */         this.currentPos++) {
/* 258 */         c = this.string.charAt(this.currentPos);
/* 259 */         if (c == '\\') {
/* 260 */           this.currentPos++;
/* 261 */           filter = true;
/* 262 */         } else if (c == '\r') {
/* 263 */           filter = true;
/* 264 */         } else if (c == '(') {
/* 265 */           nesting++;
/* 266 */         } else if (c == ')') {
/* 267 */           nesting--;
/*     */         } 
/* 269 */       }  if (nesting != 0) {
/* 270 */         throw new ParseException("Unbalanced comments");
/*     */       }
/* 272 */       if (!this.skipComments) {
/*     */         String s;
/*     */ 
/*     */         
/* 276 */         if (filter) {
/* 277 */           s = filterToken(this.string, i, this.currentPos - 1);
/*     */         } else {
/* 279 */           s = this.string.substring(i, this.currentPos - 1);
/*     */         } 
/* 281 */         return new Token(-3, s);
/*     */       } 
/*     */ 
/*     */       
/* 285 */       if (skipWhiteSpace() == -4)
/* 286 */         return EOFToken; 
/* 287 */       c = this.string.charAt(this.currentPos);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 292 */     if (c == '"') {
/* 293 */       for (int i = ++this.currentPos; this.currentPos < this.maxPos; this.currentPos++) {
/* 294 */         c = this.string.charAt(this.currentPos);
/* 295 */         if (c == '\\') {
/* 296 */           this.currentPos++;
/* 297 */           filter = true;
/* 298 */         } else if (c == '\r') {
/* 299 */           filter = true;
/* 300 */         } else if (c == '"') {
/* 301 */           String s; this.currentPos++;
/*     */ 
/*     */           
/* 304 */           if (filter) {
/* 305 */             s = filterToken(this.string, i, this.currentPos - 1);
/*     */           } else {
/* 307 */             s = this.string.substring(i, this.currentPos - 1);
/*     */           } 
/* 309 */           return new Token(-2, s);
/*     */         } 
/*     */       } 
/* 312 */       throw new ParseException("Unbalanced quoted string");
/*     */     } 
/*     */ 
/*     */     
/* 316 */     if (c < ' ' || c >= '' || this.delimiters.indexOf(c) >= 0) {
/* 317 */       this.currentPos++;
/* 318 */       char[] ch = new char[1];
/* 319 */       ch[0] = c;
/* 320 */       return new Token(c, new String(ch));
/*     */     } 
/*     */     
/*     */     int start;
/* 324 */     for (start = this.currentPos; this.currentPos < this.maxPos; this.currentPos++) {
/* 325 */       c = this.string.charAt(this.currentPos);
/*     */ 
/*     */       
/* 328 */       if (c < ' ' || c >= '' || c == '(' || c == ' ' || c == '"' || this.delimiters
/* 329 */         .indexOf(c) >= 0)
/*     */         break; 
/*     */     } 
/* 332 */     return new Token(-1, this.string.substring(start, this.currentPos));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int skipWhiteSpace() {
/* 338 */     for (; this.currentPos < this.maxPos; this.currentPos++) {
/* 339 */       char c; if ((c = this.string.charAt(this.currentPos)) != ' ' && c != '\t' && c != '\r' && c != '\n')
/*     */       {
/* 341 */         return this.currentPos; } 
/* 342 */     }  return -4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String filterToken(String s, int start, int end) {
/* 349 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 351 */     boolean gotEscape = false;
/* 352 */     boolean gotCR = false;
/*     */     
/* 354 */     for (int i = start; i < end; i++) {
/* 355 */       char c = s.charAt(i);
/* 356 */       if (c == '\n' && gotCR) {
/*     */ 
/*     */         
/* 359 */         gotCR = false;
/*     */       }
/*     */       else {
/*     */         
/* 363 */         gotCR = false;
/* 364 */         if (!gotEscape) {
/*     */           
/* 366 */           if (c == '\\') {
/* 367 */             gotEscape = true;
/* 368 */           } else if (c == '\r') {
/* 369 */             gotCR = true;
/*     */           } else {
/* 371 */             sb.append(c);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 376 */           sb.append(c);
/* 377 */           gotEscape = false;
/*     */         } 
/*     */       } 
/* 380 */     }  return sb.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/packaging/mime/internet/HeaderTokenizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */