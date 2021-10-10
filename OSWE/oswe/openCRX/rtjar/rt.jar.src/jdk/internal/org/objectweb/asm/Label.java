/*     */ package jdk.internal.org.objectweb.asm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Label
/*     */ {
/*     */   static final int DEBUG = 1;
/*     */   static final int RESOLVED = 2;
/*     */   static final int RESIZED = 4;
/*     */   static final int PUSHED = 8;
/*     */   static final int TARGET = 16;
/*     */   static final int STORE = 32;
/*     */   static final int REACHABLE = 64;
/*     */   static final int JSR = 128;
/*     */   static final int RET = 256;
/*     */   static final int SUBROUTINE = 512;
/*     */   static final int VISITED = 1024;
/*     */   static final int VISITED2 = 2048;
/*     */   public Object info;
/*     */   int status;
/*     */   int line;
/*     */   int position;
/*     */   private int referenceCount;
/*     */   private int[] srcAndRefPositions;
/*     */   int inputStackTop;
/*     */   int outputStackMax;
/*     */   Frame frame;
/*     */   Label successor;
/*     */   Edge successors;
/*     */   Label next;
/*     */   
/*     */   public int getOffset() {
/* 302 */     if ((this.status & 0x2) == 0) {
/* 303 */       throw new IllegalStateException("Label offset position has not been resolved yet");
/*     */     }
/*     */     
/* 306 */     return this.position;
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
/*     */   void put(MethodWriter paramMethodWriter, ByteVector paramByteVector, int paramInt, boolean paramBoolean) {
/* 330 */     if ((this.status & 0x2) == 0) {
/* 331 */       if (paramBoolean) {
/* 332 */         addReference(-1 - paramInt, paramByteVector.length);
/* 333 */         paramByteVector.putInt(-1);
/*     */       } else {
/* 335 */         addReference(paramInt, paramByteVector.length);
/* 336 */         paramByteVector.putShort(-1);
/*     */       }
/*     */     
/* 339 */     } else if (paramBoolean) {
/* 340 */       paramByteVector.putInt(this.position - paramInt);
/*     */     } else {
/* 342 */       paramByteVector.putShort(this.position - paramInt);
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
/*     */   private void addReference(int paramInt1, int paramInt2) {
/* 362 */     if (this.srcAndRefPositions == null) {
/* 363 */       this.srcAndRefPositions = new int[6];
/*     */     }
/* 365 */     if (this.referenceCount >= this.srcAndRefPositions.length) {
/* 366 */       int[] arrayOfInt = new int[this.srcAndRefPositions.length + 6];
/* 367 */       System.arraycopy(this.srcAndRefPositions, 0, arrayOfInt, 0, this.srcAndRefPositions.length);
/*     */       
/* 369 */       this.srcAndRefPositions = arrayOfInt;
/*     */     } 
/* 371 */     this.srcAndRefPositions[this.referenceCount++] = paramInt1;
/* 372 */     this.srcAndRefPositions[this.referenceCount++] = paramInt2;
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
/*     */   boolean resolve(MethodWriter paramMethodWriter, int paramInt, byte[] paramArrayOfbyte) {
/* 400 */     boolean bool = false;
/* 401 */     this.status |= 0x2;
/* 402 */     this.position = paramInt;
/* 403 */     byte b = 0;
/* 404 */     while (b < this.referenceCount) {
/* 405 */       int i = this.srcAndRefPositions[b++];
/* 406 */       int j = this.srcAndRefPositions[b++];
/*     */       
/* 408 */       if (i >= 0) {
/* 409 */         int m = paramInt - i;
/* 410 */         if (m < -32768 || m > 32767) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 420 */           int n = paramArrayOfbyte[j - 1] & 0xFF;
/* 421 */           if (n <= 168) {
/*     */             
/* 423 */             paramArrayOfbyte[j - 1] = (byte)(n + 49);
/*     */           } else {
/*     */             
/* 426 */             paramArrayOfbyte[j - 1] = (byte)(n + 20);
/*     */           } 
/* 428 */           bool = true;
/*     */         } 
/* 430 */         paramArrayOfbyte[j++] = (byte)(m >>> 8);
/* 431 */         paramArrayOfbyte[j] = (byte)m; continue;
/*     */       } 
/* 433 */       int k = paramInt + i + 1;
/* 434 */       paramArrayOfbyte[j++] = (byte)(k >>> 24);
/* 435 */       paramArrayOfbyte[j++] = (byte)(k >>> 16);
/* 436 */       paramArrayOfbyte[j++] = (byte)(k >>> 8);
/* 437 */       paramArrayOfbyte[j] = (byte)k;
/*     */     } 
/*     */     
/* 440 */     return bool;
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
/*     */   Label getFirst() {
/* 452 */     return (this.frame == null) ? this : this.frame.owner;
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
/*     */   boolean inSubroutine(long paramLong) {
/* 467 */     if ((this.status & 0x400) != 0) {
/* 468 */       return ((this.srcAndRefPositions[(int)(paramLong >>> 32L)] & (int)paramLong) != 0);
/*     */     }
/* 470 */     return false;
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
/*     */   boolean inSameSubroutine(Label paramLabel) {
/* 483 */     if ((this.status & 0x400) == 0 || (paramLabel.status & 0x400) == 0) {
/* 484 */       return false;
/*     */     }
/* 486 */     for (byte b = 0; b < this.srcAndRefPositions.length; b++) {
/* 487 */       if ((this.srcAndRefPositions[b] & paramLabel.srcAndRefPositions[b]) != 0) {
/* 488 */         return true;
/*     */       }
/*     */     } 
/* 491 */     return false;
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
/*     */   void addToSubroutine(long paramLong, int paramInt) {
/* 503 */     if ((this.status & 0x400) == 0) {
/* 504 */       this.status |= 0x400;
/* 505 */       this.srcAndRefPositions = new int[paramInt / 32 + 1];
/*     */     } 
/* 507 */     this.srcAndRefPositions[(int)(paramLong >>> 32L)] = this.srcAndRefPositions[(int)(paramLong >>> 32L)] | (int)paramLong;
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
/*     */   void visitSubroutine(Label paramLabel, long paramLong, int paramInt) {
/* 528 */     Label label = this;
/* 529 */     while (label != null) {
/*     */       
/* 531 */       Label label1 = label;
/* 532 */       label = label1.next;
/* 533 */       label1.next = null;
/*     */       
/* 535 */       if (paramLabel != null) {
/* 536 */         if ((label1.status & 0x800) != 0) {
/*     */           continue;
/*     */         }
/* 539 */         label1.status |= 0x800;
/*     */         
/* 541 */         if ((label1.status & 0x100) != 0 && 
/* 542 */           !label1.inSameSubroutine(paramLabel)) {
/* 543 */           Edge edge1 = new Edge();
/* 544 */           edge1.info = label1.inputStackTop;
/* 545 */           edge1.successor = paramLabel.successors.successor;
/* 546 */           edge1.next = label1.successors;
/* 547 */           label1.successors = edge1;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 552 */         if (label1.inSubroutine(paramLong)) {
/*     */           continue;
/*     */         }
/*     */         
/* 556 */         label1.addToSubroutine(paramLong, paramInt);
/*     */       } 
/*     */       
/* 559 */       Edge edge = label1.successors;
/* 560 */       while (edge != null) {
/*     */ 
/*     */ 
/*     */         
/* 564 */         if ((label1.status & 0x80) == 0 || edge != label1.successors.next)
/*     */         {
/* 566 */           if (edge.successor.next == null) {
/* 567 */             edge.successor.next = label;
/* 568 */             label = edge.successor;
/*     */           } 
/*     */         }
/* 571 */         edge = edge.next;
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
/*     */   public String toString() {
/* 587 */     return "L" + System.identityHashCode(this);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/Label.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */