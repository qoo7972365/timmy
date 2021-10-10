/*     */ package sun.security.jgss;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TokenTracker
/*     */ {
/*     */   static final int MAX_INTERVALS = 5;
/*     */   private int initNumber;
/*     */   private int windowStart;
/*     */   private int expectedNumber;
/*  70 */   private int windowStartIndex = 0;
/*     */   
/*  72 */   private LinkedList<Entry> list = new LinkedList<>();
/*     */ 
/*     */   
/*     */   public TokenTracker(int paramInt) {
/*  76 */     this.initNumber = paramInt;
/*  77 */     this.windowStart = paramInt;
/*  78 */     this.expectedNumber = paramInt;
/*     */ 
/*     */     
/*  81 */     Entry entry = new Entry(paramInt - 1);
/*     */     
/*  83 */     this.list.add(entry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getIntervalIndex(int paramInt) {
/*  93 */     Entry entry = null;
/*     */     
/*     */     int i;
/*  96 */     for (i = this.list.size() - 1; i >= 0; i--) {
/*  97 */       entry = this.list.get(i);
/*  98 */       if (entry.compareTo(paramInt) <= 0)
/*     */         break; 
/*     */     } 
/* 101 */     return i;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void getProps(int paramInt, MessageProp paramMessageProp) {
/* 152 */     boolean bool1 = false;
/* 153 */     boolean bool2 = false;
/* 154 */     boolean bool3 = false;
/* 155 */     boolean bool4 = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     int i = getIntervalIndex(paramInt);
/* 162 */     Entry entry = null;
/* 163 */     if (i != -1) {
/* 164 */       entry = this.list.get(i);
/*     */     }
/*     */ 
/*     */     
/* 168 */     if (paramInt == this.expectedNumber) {
/* 169 */       this.expectedNumber++;
/*     */ 
/*     */     
/*     */     }
/* 173 */     else if (entry != null && entry.contains(paramInt)) {
/* 174 */       bool4 = true;
/*     */     
/*     */     }
/* 177 */     else if (this.expectedNumber >= this.initNumber) {
/*     */ 
/*     */ 
/*     */       
/* 181 */       if (paramInt > this.expectedNumber) {
/* 182 */         bool1 = true;
/* 183 */       } else if (paramInt >= this.windowStart) {
/* 184 */         bool3 = true;
/* 185 */       } else if (paramInt >= this.initNumber) {
/* 186 */         bool2 = true;
/*     */       } else {
/* 188 */         bool1 = true;
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 194 */     else if (paramInt > this.expectedNumber) {
/* 195 */       if (paramInt < this.initNumber)
/* 196 */       { bool1 = true; }
/* 197 */       else if (this.windowStart >= this.initNumber)
/* 198 */       { if (paramInt >= this.windowStart) {
/* 199 */           bool3 = true;
/*     */         } else {
/* 201 */           bool2 = true;
/*     */         }  }
/* 203 */       else { bool2 = true; }
/*     */     
/* 205 */     } else if (this.windowStart > this.expectedNumber) {
/* 206 */       bool3 = true;
/* 207 */     } else if (paramInt < this.windowStart) {
/* 208 */       bool2 = true;
/*     */     } else {
/* 210 */       bool3 = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 215 */     if (!bool4 && !bool2) {
/* 216 */       add(paramInt, i);
/*     */     }
/* 218 */     if (bool1) {
/* 219 */       this.expectedNumber = paramInt + 1;
/*     */     }
/* 221 */     paramMessageProp.setSupplementaryStates(bool4, bool2, bool3, bool1, 0, null);
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
/*     */   private void add(int paramInt1, int paramInt2) {
/* 237 */     Entry entry1, entry2 = null;
/* 238 */     Entry entry3 = null;
/*     */     
/* 240 */     boolean bool1 = false;
/* 241 */     boolean bool2 = false;
/*     */     
/* 243 */     if (paramInt2 != -1) {
/* 244 */       entry2 = this.list.get(paramInt2);
/*     */ 
/*     */       
/* 247 */       if (paramInt1 == entry2.getEnd() + 1) {
/* 248 */         entry2.setEnd(paramInt1);
/* 249 */         bool1 = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 255 */     int i = paramInt2 + 1;
/* 256 */     if (i < this.list.size()) {
/* 257 */       entry3 = this.list.get(i);
/*     */ 
/*     */       
/* 260 */       if (paramInt1 == entry3.getStart() - 1) {
/* 261 */         if (!bool1) {
/* 262 */           entry3.setStart(paramInt1);
/*     */         } else {
/*     */           
/* 265 */           entry3.setStart(entry2.getStart());
/* 266 */           this.list.remove(paramInt2);
/*     */           
/* 268 */           if (this.windowStartIndex > paramInt2)
/* 269 */             this.windowStartIndex--; 
/*     */         } 
/* 271 */         bool2 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 275 */     if (bool2 || bool1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     if (this.list.size() < 5) {
/* 285 */       entry1 = new Entry(paramInt1);
/* 286 */       if (paramInt2 < this.windowStartIndex) {
/* 287 */         this.windowStartIndex++;
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 295 */       int j = this.windowStartIndex;
/* 296 */       if (this.windowStartIndex == this.list.size() - 1) {
/* 297 */         this.windowStartIndex = 0;
/*     */       }
/* 299 */       entry1 = this.list.remove(j);
/* 300 */       this.windowStart = ((Entry)this.list.get(this.windowStartIndex)).getStart();
/* 301 */       entry1.setStart(paramInt1);
/* 302 */       entry1.setEnd(paramInt1);
/*     */       
/* 304 */       if (paramInt2 >= j) {
/* 305 */         paramInt2--;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 320 */       else if (j != this.windowStartIndex) {
/*     */         
/* 322 */         if (paramInt2 == -1)
/*     */         {
/* 324 */           this.windowStart = paramInt1;
/*     */         }
/*     */       } else {
/* 327 */         this.windowStartIndex++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 335 */     this.list.add(paramInt2 + 1, entry1);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 339 */     StringBuffer stringBuffer = new StringBuffer("TokenTracker: ");
/* 340 */     stringBuffer.append(" initNumber=").append(this.initNumber);
/* 341 */     stringBuffer.append(" windowStart=").append(this.windowStart);
/* 342 */     stringBuffer.append(" expectedNumber=").append(this.expectedNumber);
/* 343 */     stringBuffer.append(" windowStartIndex=").append(this.windowStartIndex);
/* 344 */     stringBuffer.append("\n\tIntervals are: {");
/* 345 */     for (byte b = 0; b < this.list.size(); b++) {
/* 346 */       if (b != 0)
/* 347 */         stringBuffer.append(", "); 
/* 348 */       stringBuffer.append(((Entry)this.list.get(b)).toString());
/*     */     } 
/* 350 */     stringBuffer.append('}');
/* 351 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class Entry
/*     */   {
/*     */     private int start;
/*     */ 
/*     */     
/*     */     private int end;
/*     */ 
/*     */     
/*     */     Entry(int param1Int) {
/* 365 */       this.start = param1Int;
/* 366 */       this.end = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final int compareTo(int param1Int) {
/* 375 */       if (this.start > param1Int)
/* 376 */         return 1; 
/* 377 */       if (this.end < param1Int) {
/* 378 */         return -1;
/*     */       }
/* 380 */       return 0;
/*     */     }
/*     */     
/*     */     final boolean contains(int param1Int) {
/* 384 */       return (param1Int >= this.start && param1Int <= this.end);
/*     */     }
/*     */ 
/*     */     
/*     */     final void append(int param1Int) {
/* 389 */       if (param1Int == this.end + 1)
/* 390 */         this.end = param1Int; 
/*     */     }
/*     */     
/*     */     final void setInterval(int param1Int1, int param1Int2) {
/* 394 */       this.start = param1Int1;
/* 395 */       this.end = param1Int2;
/*     */     }
/*     */     
/*     */     final void setEnd(int param1Int) {
/* 399 */       this.end = param1Int;
/*     */     }
/*     */     
/*     */     final void setStart(int param1Int) {
/* 403 */       this.start = param1Int;
/*     */     }
/*     */     
/*     */     final int getStart() {
/* 407 */       return this.start;
/*     */     }
/*     */     
/*     */     final int getEnd() {
/* 411 */       return this.end;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 415 */       return "[" + this.start + ", " + this.end + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/TokenTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */