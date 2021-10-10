/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Fixups
/*     */   extends AbstractCollection<Fixups.Fixup>
/*     */ {
/*     */   byte[] bytes;
/*     */   int head;
/*     */   int tail;
/*     */   int size;
/*     */   ConstantPool.Entry[] entries;
/*     */   int[] bigDescs;
/*     */   private static final int MINBIGSIZE = 1;
/*     */   
/*     */   Fixups(byte[] paramArrayOfbyte) {
/*  62 */     this.bytes = paramArrayOfbyte;
/*  63 */     this.entries = new ConstantPool.Entry[3];
/*  64 */     this.bigDescs = noBigDescs;
/*     */   }
/*     */   
/*     */   Fixups() {
/*  68 */     this((byte[])null);
/*     */   }
/*     */   Fixups(byte[] paramArrayOfbyte, Collection<Fixup> paramCollection) {
/*  71 */     this(paramArrayOfbyte);
/*  72 */     addAll(paramCollection);
/*     */   }
/*     */   Fixups(Collection<Fixup> paramCollection) {
/*  75 */     this((byte[])null);
/*  76 */     addAll(paramCollection);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final int[] noBigDescs = new int[] { 1 }; private static final int LOC_SHIFT = 1; private static final int FMT_MASK = 1; private static final byte UNUSED_BYTE = 0;
/*     */   private static final byte OVERFLOW_BYTE = -1;
/*     */   
/*     */   public int size() {
/*  85 */     return this.size;
/*     */   }
/*     */   private static final int BIGSIZE = 0; private static final int U2_FORMAT = 0; private static final int U1_FORMAT = 1; private static final int SPECIAL_LOC = 0; private static final int SPECIAL_FMT = 0;
/*     */   public void trimToSize() {
/*  89 */     if (this.size != this.entries.length) {
/*  90 */       ConstantPool.Entry[] arrayOfEntry = this.entries;
/*  91 */       this.entries = new ConstantPool.Entry[this.size];
/*  92 */       System.arraycopy(arrayOfEntry, 0, this.entries, 0, this.size);
/*     */     } 
/*  94 */     int i = this.bigDescs[0];
/*  95 */     if (i == 1) {
/*  96 */       this.bigDescs = noBigDescs;
/*  97 */     } else if (i != this.bigDescs.length) {
/*  98 */       int[] arrayOfInt = this.bigDescs;
/*  99 */       this.bigDescs = new int[i];
/* 100 */       System.arraycopy(arrayOfInt, 0, this.bigDescs, 0, i);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitRefs(Collection<ConstantPool.Entry> paramCollection) {
/* 105 */     for (byte b = 0; b < this.size; b++) {
/* 106 */       paramCollection.add(this.entries[b]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 112 */     if (this.bytes != null)
/*     */     {
/* 114 */       for (Fixup fixup : this)
/*     */       {
/* 116 */         storeIndex(fixup.location(), fixup.format(), 0);
/*     */       }
/*     */     }
/* 119 */     this.size = 0;
/* 120 */     if (this.bigDescs != noBigDescs) {
/* 121 */       this.bigDescs[0] = 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public byte[] getBytes() {
/* 126 */     return this.bytes;
/*     */   }
/*     */   
/*     */   public void setBytes(byte[] paramArrayOfbyte) {
/* 130 */     if (this.bytes == paramArrayOfbyte)
/* 131 */       return;  ArrayList arrayList = null;
/* 132 */     assert (arrayList = new ArrayList(this)) != null;
/* 133 */     if (this.bytes == null || paramArrayOfbyte == null) {
/*     */ 
/*     */       
/* 136 */       ArrayList<? extends Fixup> arrayList1 = new ArrayList(this);
/* 137 */       clear();
/* 138 */       this.bytes = paramArrayOfbyte;
/* 139 */       addAll(arrayList1);
/*     */     } else {
/*     */       
/* 142 */       this.bytes = paramArrayOfbyte;
/*     */     } 
/* 144 */     assert arrayList.equals(new ArrayList(this));
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
/*     */   static int fmtLen(int paramInt) {
/* 162 */     return 1 + (paramInt - 1) / -1;
/* 163 */   } static int descLoc(int paramInt) { return paramInt >>> 1; }
/* 164 */   static int descFmt(int paramInt) { return paramInt & 0x1; } static int descEnd(int paramInt) {
/* 165 */     return descLoc(paramInt) + fmtLen(descFmt(paramInt));
/*     */   } static int makeDesc(int paramInt1, int paramInt2) {
/* 167 */     int i = paramInt1 << 1 | paramInt2;
/* 168 */     assert descLoc(i) == paramInt1;
/* 169 */     assert descFmt(i) == paramInt2;
/* 170 */     return i;
/*     */   } int fetchDesc(int paramInt1, int paramInt2) {
/*     */     int i;
/* 173 */     byte b = this.bytes[paramInt1];
/* 174 */     assert b != -1;
/*     */     
/* 176 */     if (paramInt2 == 0) {
/* 177 */       byte b1 = this.bytes[paramInt1 + 1];
/* 178 */       i = ((b & 0xFF) << 8) + (b1 & 0xFF);
/*     */     } else {
/* 180 */       i = b & 0xFF;
/*     */     } 
/*     */     
/* 183 */     return i + (paramInt1 << 1);
/*     */   } boolean storeDesc(int paramInt1, int paramInt2, int paramInt3) {
/*     */     byte b1, b2;
/* 186 */     if (this.bytes == null)
/* 187 */       return false; 
/* 188 */     int i = paramInt3 - (paramInt1 << 1);
/*     */     
/* 190 */     switch (paramInt2) {
/*     */       case 0:
/* 192 */         assert this.bytes[paramInt1 + 0] == 0;
/* 193 */         assert this.bytes[paramInt1 + 1] == 0;
/* 194 */         b1 = (byte)(i >> 8);
/* 195 */         b2 = (byte)(i >> 0);
/* 196 */         if (i == (i & 0xFFFF) && b1 != -1) {
/* 197 */           this.bytes[paramInt1 + 0] = b1;
/* 198 */           this.bytes[paramInt1 + 1] = b2;
/* 199 */           assert fetchDesc(paramInt1, paramInt2) == paramInt3;
/* 200 */           return true;
/*     */         } 
/*     */         break;
/*     */       case 1:
/* 204 */         assert this.bytes[paramInt1] == 0;
/* 205 */         b1 = (byte)i;
/* 206 */         if (i == (i & 0xFF) && b1 != -1) {
/* 207 */           this.bytes[paramInt1] = b1;
/* 208 */           assert fetchDesc(paramInt1, paramInt2) == paramInt3;
/* 209 */           return true;
/*     */         }  break;
/*     */       default:
/*     */         assert false;
/*     */         break;
/*     */     } 
/* 215 */     this.bytes[paramInt1] = -1;
/* 216 */     this.bytes[paramInt1 + 1] = (byte)this.bigDescs[0]; assert paramInt2 == 1 || (byte)this.bigDescs[0] != 999;
/* 217 */     return false;
/*     */   }
/*     */   void storeIndex(int paramInt1, int paramInt2, int paramInt3) {
/* 220 */     storeIndex(this.bytes, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   static void storeIndex(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) {
/* 224 */     switch (paramInt2) {
/*     */       case 0:
/* 226 */         assert paramInt3 == (paramInt3 & 0xFFFF) : paramInt3;
/* 227 */         paramArrayOfbyte[paramInt1 + 0] = (byte)(paramInt3 >> 8);
/* 228 */         paramArrayOfbyte[paramInt1 + 1] = (byte)(paramInt3 >> 0);
/*     */         return;
/*     */       case 1:
/* 231 */         assert paramInt3 == (paramInt3 & 0xFF) : paramInt3;
/* 232 */         paramArrayOfbyte[paramInt1] = (byte)paramInt3;
/*     */         return;
/*     */     } 
/*     */     assert false;
/*     */   }
/*     */   
/*     */   void addU1(int paramInt, ConstantPool.Entry paramEntry) {
/* 239 */     add(paramInt, 1, paramEntry);
/*     */   }
/*     */   
/*     */   void addU2(int paramInt, ConstantPool.Entry paramEntry) {
/* 243 */     add(paramInt, 0, paramEntry);
/*     */   }
/*     */   
/*     */   public static class Fixup
/*     */     implements Comparable<Fixup> {
/*     */     int desc;
/*     */     ConstantPool.Entry entry;
/*     */     
/*     */     Fixup(int param1Int, ConstantPool.Entry param1Entry) {
/* 252 */       this.desc = param1Int;
/* 253 */       this.entry = param1Entry;
/*     */     }
/*     */     public Fixup(int param1Int1, int param1Int2, ConstantPool.Entry param1Entry) {
/* 256 */       this.desc = Fixups.makeDesc(param1Int1, param1Int2);
/* 257 */       this.entry = param1Entry;
/*     */     }
/* 259 */     public int location() { return Fixups.descLoc(this.desc); }
/* 260 */     public int format() { return Fixups.descFmt(this.desc); } public ConstantPool.Entry entry() {
/* 261 */       return this.entry;
/*     */     }
/*     */     
/*     */     public int compareTo(Fixup param1Fixup) {
/* 265 */       return location() - param1Fixup.location();
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 269 */       if (!(param1Object instanceof Fixup)) return false; 
/* 270 */       Fixup fixup = (Fixup)param1Object;
/* 271 */       return (this.desc == fixup.desc && this.entry == fixup.entry);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 275 */       int i = 7;
/* 276 */       i = 59 * i + this.desc;
/* 277 */       i = 59 * i + Objects.hashCode(this.entry);
/* 278 */       return i;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 282 */       return "@" + location() + ((format() == 1) ? ".1" : "") + "=" + this.entry;
/*     */     }
/*     */   }
/*     */   
/*     */   private class Itr
/*     */     implements Iterator<Fixup> {
/* 288 */     int index = 0;
/* 289 */     int bigIndex = 1;
/* 290 */     int next = Fixups.this.head;
/*     */     public boolean hasNext() {
/* 292 */       return (this.index < Fixups.this.size);
/*     */     } public void remove() {
/* 294 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public Fixups.Fixup next() {
/* 297 */       int i = this.index;
/* 298 */       return new Fixups.Fixup(nextDesc(), Fixups.this.entries[i]);
/*     */     }
/*     */     int nextDesc() {
/* 301 */       this.index++;
/* 302 */       int i = this.next;
/* 303 */       if (this.index < Fixups.this.size) {
/*     */         
/* 305 */         int j = Fixups.descLoc(i);
/* 306 */         int k = Fixups.descFmt(i);
/* 307 */         if (Fixups.this.bytes != null && Fixups.this.bytes[j] != -1) {
/* 308 */           this.next = Fixups.this.fetchDesc(j, k);
/*     */         }
/*     */         else {
/*     */           
/* 312 */           assert k == 1 || Fixups.this.bytes == null || Fixups.this.bytes[j + 1] == (byte)this.bigIndex;
/* 313 */           this.next = Fixups.this.bigDescs[this.bigIndex++];
/*     */         } 
/*     */       } 
/* 316 */       return i;
/*     */     }
/*     */     
/*     */     private Itr() {} }
/*     */   
/*     */   public Iterator<Fixup> iterator() {
/* 322 */     return new Itr();
/*     */   }
/*     */   public void add(int paramInt1, int paramInt2, ConstantPool.Entry paramEntry) {
/* 325 */     addDesc(makeDesc(paramInt1, paramInt2), paramEntry);
/*     */   }
/*     */   
/*     */   public boolean add(Fixup paramFixup) {
/* 329 */     addDesc(paramFixup.desc, paramFixup.entry);
/* 330 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Fixup> paramCollection) {
/* 335 */     if (paramCollection instanceof Fixups) {
/*     */       
/* 337 */       Fixups fixups = (Fixups)paramCollection;
/* 338 */       if (fixups.size == 0) return false; 
/* 339 */       if (this.size == 0 && this.entries.length < fixups.size)
/* 340 */         growEntries(fixups.size); 
/* 341 */       ConstantPool.Entry[] arrayOfEntry = fixups.entries;
/* 342 */       fixups.getClass(); for (Itr itr = new Itr(); itr.hasNext(); ) {
/* 343 */         int i = itr.index;
/* 344 */         addDesc(itr.nextDesc(), arrayOfEntry[i]);
/*     */       } 
/* 346 */       return true;
/*     */     } 
/* 348 */     return super.addAll(paramCollection);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addDesc(int paramInt, ConstantPool.Entry paramEntry) {
/* 353 */     if (this.entries.length == this.size)
/* 354 */       growEntries(this.size * 2); 
/* 355 */     this.entries[this.size] = paramEntry;
/* 356 */     if (this.size == 0) {
/* 357 */       this.head = this.tail = paramInt;
/*     */     } else {
/* 359 */       int i = this.tail;
/*     */       
/* 361 */       int j = descLoc(i);
/* 362 */       int k = descFmt(i);
/* 363 */       int m = fmtLen(k);
/* 364 */       int n = descLoc(paramInt);
/*     */       
/* 366 */       if (n < j + m)
/* 367 */         badOverlap(n); 
/* 368 */       this.tail = paramInt;
/* 369 */       if (!storeDesc(j, k, paramInt)) {
/*     */         
/* 371 */         int i1 = this.bigDescs[0];
/* 372 */         if (this.bigDescs.length == i1) {
/* 373 */           growBigDescs();
/*     */         }
/* 375 */         this.bigDescs[i1++] = paramInt;
/* 376 */         this.bigDescs[0] = i1;
/*     */       } 
/*     */     } 
/* 379 */     this.size++;
/*     */   }
/*     */   private void badOverlap(int paramInt) {
/* 382 */     throw new IllegalArgumentException("locs must be ascending and must not overlap:  " + paramInt + " >> " + this);
/*     */   }
/*     */   
/*     */   private void growEntries(int paramInt) {
/* 386 */     ConstantPool.Entry[] arrayOfEntry = this.entries;
/* 387 */     this.entries = new ConstantPool.Entry[Math.max(3, paramInt)];
/* 388 */     System.arraycopy(arrayOfEntry, 0, this.entries, 0, arrayOfEntry.length);
/*     */   }
/*     */   private void growBigDescs() {
/* 391 */     int[] arrayOfInt = this.bigDescs;
/* 392 */     this.bigDescs = new int[arrayOfInt.length * 2];
/* 393 */     System.arraycopy(arrayOfInt, 0, this.bigDescs, 0, arrayOfInt.length);
/*     */   }
/*     */ 
/*     */   
/*     */   static Object addRefWithBytes(Object paramObject, byte[] paramArrayOfbyte, ConstantPool.Entry paramEntry) {
/* 398 */     return add(paramObject, paramArrayOfbyte, 0, 0, paramEntry);
/*     */   }
/*     */   static Object addRefWithLoc(Object paramObject, int paramInt, ConstantPool.Entry paramEntry) {
/* 401 */     return add(paramObject, null, paramInt, 0, paramEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object add(Object paramObject, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ConstantPool.Entry paramEntry) {
/*     */     Fixups fixups;
/* 408 */     if (paramObject == null) {
/* 409 */       if (paramInt1 == 0 && paramInt2 == 0)
/*     */       {
/*     */ 
/*     */         
/* 413 */         return paramEntry;
/*     */       }
/* 415 */       fixups = new Fixups(paramArrayOfbyte);
/* 416 */     } else if (!(paramObject instanceof Fixups)) {
/*     */       
/* 418 */       ConstantPool.Entry entry = (ConstantPool.Entry)paramObject;
/* 419 */       fixups = new Fixups(paramArrayOfbyte);
/* 420 */       fixups.add(0, 0, entry);
/*     */     } else {
/* 422 */       fixups = (Fixups)paramObject;
/* 423 */       assert fixups.bytes == paramArrayOfbyte;
/*     */     } 
/* 425 */     fixups.add(paramInt1, paramInt2, paramEntry);
/* 426 */     return fixups;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setBytes(Object paramObject, byte[] paramArrayOfbyte) {
/* 431 */     if (paramObject instanceof Fixups) {
/* 432 */       Fixups fixups = (Fixups)paramObject;
/* 433 */       fixups.setBytes(paramArrayOfbyte);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object trimToSize(Object paramObject) {
/* 439 */     if (paramObject instanceof Fixups) {
/* 440 */       Fixups fixups = (Fixups)paramObject;
/* 441 */       fixups.trimToSize();
/* 442 */       if (fixups.size() == 0)
/* 443 */         paramObject = null; 
/*     */     } 
/* 445 */     return paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void visitRefs(Object paramObject, Collection<ConstantPool.Entry> paramCollection) {
/* 451 */     if (paramObject != null) {
/* 452 */       if (!(paramObject instanceof Fixups)) {
/*     */         
/* 454 */         paramCollection.add((ConstantPool.Entry)paramObject);
/*     */       } else {
/* 456 */         Fixups fixups = (Fixups)paramObject;
/* 457 */         fixups.visitRefs(paramCollection);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void finishRefs(Object paramObject, byte[] paramArrayOfbyte, ConstantPool.Index paramIndex) {
/* 465 */     if (paramObject == null)
/*     */       return; 
/* 467 */     if (!(paramObject instanceof Fixups)) {
/*     */       
/* 469 */       int i = paramIndex.indexOf((ConstantPool.Entry)paramObject);
/* 470 */       storeIndex(paramArrayOfbyte, 0, 0, i);
/*     */       return;
/*     */     } 
/* 473 */     Fixups fixups = (Fixups)paramObject;
/* 474 */     assert fixups.bytes == paramArrayOfbyte;
/* 475 */     fixups.finishRefs(paramIndex);
/*     */   }
/*     */   
/*     */   void finishRefs(ConstantPool.Index paramIndex) {
/* 479 */     if (isEmpty())
/*     */       return; 
/* 481 */     for (Fixup fixup : this) {
/* 482 */       int i = paramIndex.indexOf(fixup.entry);
/*     */ 
/*     */ 
/*     */       
/* 486 */       storeIndex(fixup.location(), fixup.format(), i);
/*     */     } 
/*     */     
/* 489 */     this.bytes = null;
/* 490 */     clear();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/Fixups.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */