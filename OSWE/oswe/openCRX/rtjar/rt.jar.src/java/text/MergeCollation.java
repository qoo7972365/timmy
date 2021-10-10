/*     */ package java.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MergeCollation
/*     */ {
/*     */   public MergeCollation(String paramString) throws ParseException {
/*  71 */     for (byte b = 0; b < this.statusArray.length; b++)
/*  72 */       this.statusArray[b] = 0; 
/*  73 */     setPattern(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPattern() {
/*  80 */     return getPattern(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPattern(boolean paramBoolean) {
/*  89 */     StringBuffer stringBuffer = new StringBuffer();
/*  90 */     PatternEntry patternEntry = null;
/*  91 */     ArrayList<PatternEntry> arrayList = null;
/*     */     byte b;
/*  93 */     for (b = 0; b < this.patterns.size(); b++) {
/*  94 */       PatternEntry patternEntry1 = this.patterns.get(b);
/*  95 */       if (patternEntry1.extension.length() != 0) {
/*  96 */         if (arrayList == null)
/*  97 */           arrayList = new ArrayList(); 
/*  98 */         arrayList.add(patternEntry1);
/*     */       } else {
/* 100 */         if (arrayList != null) {
/* 101 */           PatternEntry patternEntry2 = findLastWithNoExtension(b - 1);
/* 102 */           for (int i = arrayList.size() - 1; i >= 0; i--) {
/* 103 */             patternEntry = arrayList.get(i);
/* 104 */             patternEntry.addToBuffer(stringBuffer, false, paramBoolean, patternEntry2);
/*     */           } 
/* 106 */           arrayList = null;
/*     */         } 
/* 108 */         patternEntry1.addToBuffer(stringBuffer, false, paramBoolean, null);
/*     */       } 
/*     */     } 
/* 111 */     if (arrayList != null) {
/* 112 */       PatternEntry patternEntry1 = findLastWithNoExtension(b - 1);
/* 113 */       for (int i = arrayList.size() - 1; i >= 0; i--) {
/* 114 */         patternEntry = arrayList.get(i);
/* 115 */         patternEntry.addToBuffer(stringBuffer, false, paramBoolean, patternEntry1);
/*     */       } 
/* 117 */       arrayList = null;
/*     */     } 
/* 119 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private final PatternEntry findLastWithNoExtension(int paramInt) {
/* 123 */     for (; --paramInt >= 0; paramInt--) {
/* 124 */       PatternEntry patternEntry = this.patterns.get(paramInt);
/* 125 */       if (patternEntry.extension.length() == 0) {
/* 126 */         return patternEntry;
/*     */       }
/*     */     } 
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String emitPattern() {
/* 138 */     return emitPattern(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String emitPattern(boolean paramBoolean) {
/* 149 */     StringBuffer stringBuffer = new StringBuffer();
/* 150 */     for (byte b = 0; b < this.patterns.size(); b++) {
/*     */       
/* 152 */       PatternEntry patternEntry = this.patterns.get(b);
/* 153 */       if (patternEntry != null) {
/* 154 */         patternEntry.addToBuffer(stringBuffer, true, paramBoolean, null);
/*     */       }
/*     */     } 
/* 157 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPattern(String paramString) throws ParseException {
/* 165 */     this.patterns.clear();
/* 166 */     addPattern(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPattern(String paramString) throws ParseException {
/* 175 */     if (paramString == null) {
/*     */       return;
/*     */     }
/* 178 */     PatternEntry.Parser parser = new PatternEntry.Parser(paramString);
/*     */     
/* 180 */     PatternEntry patternEntry = parser.next();
/* 181 */     while (patternEntry != null) {
/* 182 */       fixEntry(patternEntry);
/* 183 */       patternEntry = parser.next();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 192 */     return this.patterns.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PatternEntry getItemAt(int paramInt) {
/* 201 */     return this.patterns.get(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   ArrayList<PatternEntry> patterns = new ArrayList<>();
/*     */   
/* 209 */   private transient PatternEntry saveEntry = null;
/* 210 */   private transient PatternEntry lastEntry = null;
/*     */ 
/*     */ 
/*     */   
/* 214 */   private transient StringBuffer excess = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 224 */   private transient byte[] statusArray = new byte[8192];
/* 225 */   private final byte BITARRAYMASK = 1;
/* 226 */   private final int BYTEPOWER = 3;
/* 227 */   private final int BYTEMASK = 7;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void fixEntry(PatternEntry paramPatternEntry) throws ParseException {
/* 242 */     if (this.lastEntry != null && paramPatternEntry.chars.equals(this.lastEntry.chars) && paramPatternEntry.extension
/* 243 */       .equals(this.lastEntry.extension)) {
/* 244 */       if (paramPatternEntry.strength != 3 && paramPatternEntry.strength != -2)
/*     */       {
/* 246 */         throw new ParseException("The entries " + this.lastEntry + " and " + paramPatternEntry + " are adjacent in the rules, but have conflicting strengths: A character can't be unequal to itself.", -1);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 255 */     boolean bool = true;
/* 256 */     if (paramPatternEntry.strength != -2) {
/* 257 */       int i = -1;
/*     */       
/* 259 */       if (paramPatternEntry.chars.length() == 1) {
/*     */         
/* 261 */         char c = paramPatternEntry.chars.charAt(0);
/* 262 */         int k = c >> 3;
/* 263 */         byte b1 = this.statusArray[k];
/* 264 */         byte b2 = (byte)(1 << (c & 0x7));
/*     */         
/* 266 */         if (b1 != 0 && (b1 & b2) != 0) {
/* 267 */           i = this.patterns.lastIndexOf(paramPatternEntry);
/*     */         }
/*     */         else {
/*     */           
/* 271 */           this.statusArray[k] = (byte)(b1 | b2);
/*     */         } 
/*     */       } else {
/* 274 */         i = this.patterns.lastIndexOf(paramPatternEntry);
/*     */       } 
/* 276 */       if (i != -1) {
/* 277 */         this.patterns.remove(i);
/*     */       }
/*     */       
/* 280 */       this.excess.setLength(0);
/* 281 */       int j = findLastEntry(this.lastEntry, this.excess);
/*     */       
/* 283 */       if (this.excess.length() != 0) {
/* 284 */         paramPatternEntry.extension = this.excess + paramPatternEntry.extension;
/* 285 */         if (j != this.patterns.size()) {
/* 286 */           this.lastEntry = this.saveEntry;
/* 287 */           bool = false;
/*     */         } 
/*     */       } 
/* 290 */       if (j == this.patterns.size()) {
/* 291 */         this.patterns.add(paramPatternEntry);
/* 292 */         this.saveEntry = paramPatternEntry;
/*     */       } else {
/* 294 */         this.patterns.add(j, paramPatternEntry);
/*     */       } 
/*     */     } 
/* 297 */     if (bool) {
/* 298 */       this.lastEntry = paramPatternEntry;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final int findLastEntry(PatternEntry paramPatternEntry, StringBuffer paramStringBuffer) throws ParseException {
/* 305 */     if (paramPatternEntry == null) {
/* 306 */       return 0;
/*     */     }
/* 308 */     if (paramPatternEntry.strength != -2) {
/*     */ 
/*     */ 
/*     */       
/* 312 */       int j = -1;
/* 313 */       if (paramPatternEntry.chars.length() == 1) {
/* 314 */         int k = paramPatternEntry.chars.charAt(0) >> 3;
/* 315 */         if ((this.statusArray[k] & 1 << (paramPatternEntry.chars
/* 316 */           .charAt(0) & 0x7)) != 0) {
/* 317 */           j = this.patterns.lastIndexOf(paramPatternEntry);
/*     */         }
/*     */       } else {
/* 320 */         j = this.patterns.lastIndexOf(paramPatternEntry);
/*     */       } 
/* 322 */       if (j == -1) {
/* 323 */         throw new ParseException("couldn't find last entry: " + paramPatternEntry, j);
/*     */       }
/* 325 */       return j + 1;
/*     */     } 
/*     */     int i;
/* 328 */     for (i = this.patterns.size() - 1; i >= 0; i--) {
/* 329 */       PatternEntry patternEntry = this.patterns.get(i);
/* 330 */       if (patternEntry.chars.regionMatches(0, paramPatternEntry.chars, 0, patternEntry.chars
/* 331 */           .length())) {
/* 332 */         paramStringBuffer.append(paramPatternEntry.chars.substring(patternEntry.chars.length(), paramPatternEntry.chars
/* 333 */               .length()));
/*     */         break;
/*     */       } 
/*     */     } 
/* 337 */     if (i == -1)
/* 338 */       throw new ParseException("couldn't find: " + paramPatternEntry, i); 
/* 339 */     return i + 1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/MergeCollation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */