/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CoderResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Surrogate
/*     */ {
/*     */   public static final char MIN_HIGH = '?';
/*     */   public static final char MAX_HIGH = '?';
/*     */   public static final char MIN_LOW = '?';
/*     */   public static final char MAX_LOW = '?';
/*     */   public static final char MIN = '?';
/*     */   public static final char MAX = '?';
/*     */   public static final int UCS4_MIN = 65536;
/*     */   public static final int UCS4_MAX = 1114111;
/*     */   
/*     */   public static boolean isHigh(int paramInt) {
/*  59 */     return (55296 <= paramInt && paramInt <= 56319);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLow(int paramInt) {
/*  67 */     return (56320 <= paramInt && paramInt <= 57343);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean is(int paramInt) {
/*  75 */     return (55296 <= paramInt && paramInt <= 57343);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean neededFor(int paramInt) {
/*  84 */     return Character.isSupplementaryCodePoint(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char high(int paramInt) {
/*  92 */     assert Character.isSupplementaryCodePoint(paramInt);
/*  93 */     return Character.highSurrogate(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char low(int paramInt) {
/* 101 */     assert Character.isSupplementaryCodePoint(paramInt);
/* 102 */     return Character.lowSurrogate(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int toUCS4(char paramChar1, char paramChar2) {
/* 110 */     assert Character.isHighSurrogate(paramChar1) && Character.isLowSurrogate(paramChar2);
/* 111 */     return Character.toCodePoint(paramChar1, paramChar2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Parser
/*     */   {
/*     */     private int character;
/*     */ 
/*     */ 
/*     */     
/* 123 */     private CoderResult error = CoderResult.UNDERFLOW;
/*     */ 
/*     */     
/*     */     private boolean isPair;
/*     */ 
/*     */     
/*     */     public int character() {
/* 130 */       assert this.error == null;
/* 131 */       return this.character;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isPair() {
/* 139 */       assert this.error == null;
/* 140 */       return this.isPair;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int increment() {
/* 148 */       assert this.error == null;
/* 149 */       return this.isPair ? 2 : 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CoderResult error() {
/* 157 */       assert this.error != null;
/* 158 */       return this.error;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CoderResult unmappableResult() {
/* 166 */       assert this.error == null;
/* 167 */       return CoderResult.unmappableForLength(this.isPair ? 2 : 1);
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
/*     */     public int parse(char param1Char, CharBuffer param1CharBuffer) {
/* 184 */       if (Character.isHighSurrogate(param1Char)) {
/* 185 */         if (!param1CharBuffer.hasRemaining()) {
/* 186 */           this.error = CoderResult.UNDERFLOW;
/* 187 */           return -1;
/*     */         } 
/* 189 */         char c = param1CharBuffer.get();
/* 190 */         if (Character.isLowSurrogate(c)) {
/* 191 */           this.character = Character.toCodePoint(param1Char, c);
/* 192 */           this.isPair = true;
/* 193 */           this.error = null;
/* 194 */           return this.character;
/*     */         } 
/* 196 */         this.error = CoderResult.malformedForLength(1);
/* 197 */         return -1;
/*     */       } 
/* 199 */       if (Character.isLowSurrogate(param1Char)) {
/* 200 */         this.error = CoderResult.malformedForLength(1);
/* 201 */         return -1;
/*     */       } 
/* 203 */       this.character = param1Char;
/* 204 */       this.isPair = false;
/* 205 */       this.error = null;
/* 206 */       return this.character;
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
/*     */     public int parse(char param1Char, char[] param1ArrayOfchar, int param1Int1, int param1Int2) {
/* 225 */       assert param1ArrayOfchar[param1Int1] == param1Char;
/* 226 */       if (Character.isHighSurrogate(param1Char)) {
/* 227 */         if (param1Int2 - param1Int1 < 2) {
/* 228 */           this.error = CoderResult.UNDERFLOW;
/* 229 */           return -1;
/*     */         } 
/* 231 */         char c = param1ArrayOfchar[param1Int1 + 1];
/* 232 */         if (Character.isLowSurrogate(c)) {
/* 233 */           this.character = Character.toCodePoint(param1Char, c);
/* 234 */           this.isPair = true;
/* 235 */           this.error = null;
/* 236 */           return this.character;
/*     */         } 
/* 238 */         this.error = CoderResult.malformedForLength(1);
/* 239 */         return -1;
/*     */       } 
/* 241 */       if (Character.isLowSurrogate(param1Char)) {
/* 242 */         this.error = CoderResult.malformedForLength(1);
/* 243 */         return -1;
/*     */       } 
/* 245 */       this.character = param1Char;
/* 246 */       this.isPair = false;
/* 247 */       this.error = null;
/* 248 */       return this.character;
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
/*     */   public static class Generator
/*     */   {
/* 262 */     private CoderResult error = CoderResult.OVERFLOW;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CoderResult error() {
/* 269 */       assert this.error != null;
/* 270 */       return this.error;
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
/*     */     public int generate(int param1Int1, int param1Int2, CharBuffer param1CharBuffer) {
/* 288 */       if (Character.isBmpCodePoint(param1Int1)) {
/* 289 */         char c = (char)param1Int1;
/* 290 */         if (Character.isSurrogate(c)) {
/* 291 */           this.error = CoderResult.malformedForLength(param1Int2);
/* 292 */           return -1;
/*     */         } 
/* 294 */         if (param1CharBuffer.remaining() < 1) {
/* 295 */           this.error = CoderResult.OVERFLOW;
/* 296 */           return -1;
/*     */         } 
/* 298 */         param1CharBuffer.put(c);
/* 299 */         this.error = null;
/* 300 */         return 1;
/* 301 */       }  if (Character.isValidCodePoint(param1Int1)) {
/* 302 */         if (param1CharBuffer.remaining() < 2) {
/* 303 */           this.error = CoderResult.OVERFLOW;
/* 304 */           return -1;
/*     */         } 
/* 306 */         param1CharBuffer.put(Character.highSurrogate(param1Int1));
/* 307 */         param1CharBuffer.put(Character.lowSurrogate(param1Int1));
/* 308 */         this.error = null;
/* 309 */         return 2;
/*     */       } 
/* 311 */       this.error = CoderResult.unmappableForLength(param1Int2);
/* 312 */       return -1;
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
/*     */     
/*     */     public int generate(int param1Int1, int param1Int2, char[] param1ArrayOfchar, int param1Int3, int param1Int4) {
/* 333 */       if (Character.isBmpCodePoint(param1Int1)) {
/* 334 */         char c = (char)param1Int1;
/* 335 */         if (Character.isSurrogate(c)) {
/* 336 */           this.error = CoderResult.malformedForLength(param1Int2);
/* 337 */           return -1;
/*     */         } 
/* 339 */         if (param1Int4 - param1Int3 < 1) {
/* 340 */           this.error = CoderResult.OVERFLOW;
/* 341 */           return -1;
/*     */         } 
/* 343 */         param1ArrayOfchar[param1Int3] = c;
/* 344 */         this.error = null;
/* 345 */         return 1;
/* 346 */       }  if (Character.isValidCodePoint(param1Int1)) {
/* 347 */         if (param1Int4 - param1Int3 < 2) {
/* 348 */           this.error = CoderResult.OVERFLOW;
/* 349 */           return -1;
/*     */         } 
/* 351 */         param1ArrayOfchar[param1Int3] = Character.highSurrogate(param1Int1);
/* 352 */         param1ArrayOfchar[param1Int3 + 1] = Character.lowSurrogate(param1Int1);
/* 353 */         this.error = null;
/* 354 */         return 2;
/*     */       } 
/* 356 */       this.error = CoderResult.unmappableForLength(param1Int2);
/* 357 */       return -1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/Surrogate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */