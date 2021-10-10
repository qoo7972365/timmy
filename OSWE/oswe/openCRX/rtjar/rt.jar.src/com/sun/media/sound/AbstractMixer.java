/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import javax.sound.sampled.Control;
/*     */ import javax.sound.sampled.Line;
/*     */ import javax.sound.sampled.LineUnavailableException;
/*     */ import javax.sound.sampled.Mixer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractMixer
/*     */   extends AbstractLine
/*     */   implements Mixer
/*     */ {
/*     */   protected static final int PCM = 0;
/*     */   protected static final int ULAW = 1;
/*     */   protected static final int ALAW = 2;
/*     */   private final Mixer.Info mixerInfo;
/*     */   protected Line.Info[] sourceLineInfo;
/*     */   protected Line.Info[] targetLineInfo;
/*     */   private boolean started = false;
/*     */   private boolean manuallyOpened = false;
/*  93 */   private final Vector sourceLines = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private final Vector targetLines = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractMixer(Mixer.Info paramInfo, Control[] paramArrayOfControl, Line.Info[] paramArrayOfInfo1, Line.Info[] paramArrayOfInfo2) {
/* 113 */     super(new Line.Info(Mixer.class), null, paramArrayOfControl);
/*     */ 
/*     */     
/* 116 */     this.mixer = this;
/* 117 */     if (paramArrayOfControl == null) {
/* 118 */       paramArrayOfControl = new Control[0];
/*     */     }
/*     */ 
/*     */     
/* 122 */     this.mixerInfo = paramInfo;
/* 123 */     this.sourceLineInfo = paramArrayOfInfo1;
/* 124 */     this.targetLineInfo = paramArrayOfInfo2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Mixer.Info getMixerInfo() {
/* 132 */     return this.mixerInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Line.Info[] getSourceLineInfo() {
/* 137 */     Line.Info[] arrayOfInfo = new Line.Info[this.sourceLineInfo.length];
/* 138 */     System.arraycopy(this.sourceLineInfo, 0, arrayOfInfo, 0, this.sourceLineInfo.length);
/* 139 */     return arrayOfInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Line.Info[] getTargetLineInfo() {
/* 145 */     Line.Info[] arrayOfInfo = new Line.Info[this.targetLineInfo.length];
/* 146 */     System.arraycopy(this.targetLineInfo, 0, arrayOfInfo, 0, this.targetLineInfo.length);
/* 147 */     return arrayOfInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Line.Info[] getSourceLineInfo(Line.Info paramInfo) {
/* 154 */     Vector<Line.Info> vector = new Vector();
/*     */     byte b;
/* 156 */     for (b = 0; b < this.sourceLineInfo.length; b++) {
/*     */       
/* 158 */       if (paramInfo.matches(this.sourceLineInfo[b])) {
/* 159 */         vector.addElement(this.sourceLineInfo[b]);
/*     */       }
/*     */     } 
/*     */     
/* 163 */     Line.Info[] arrayOfInfo = new Line.Info[vector.size()];
/* 164 */     for (b = 0; b < arrayOfInfo.length; b++) {
/* 165 */       arrayOfInfo[b] = vector.elementAt(b);
/*     */     }
/*     */     
/* 168 */     return arrayOfInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Line.Info[] getTargetLineInfo(Line.Info paramInfo) {
/* 175 */     Vector<Line.Info> vector = new Vector();
/*     */     byte b;
/* 177 */     for (b = 0; b < this.targetLineInfo.length; b++) {
/*     */       
/* 179 */       if (paramInfo.matches(this.targetLineInfo[b])) {
/* 180 */         vector.addElement(this.targetLineInfo[b]);
/*     */       }
/*     */     } 
/*     */     
/* 184 */     Line.Info[] arrayOfInfo = new Line.Info[vector.size()];
/* 185 */     for (b = 0; b < arrayOfInfo.length; b++) {
/* 186 */       arrayOfInfo[b] = vector.elementAt(b);
/*     */     }
/*     */     
/* 189 */     return arrayOfInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isLineSupported(Line.Info paramInfo) {
/*     */     byte b;
/* 197 */     for (b = 0; b < this.sourceLineInfo.length; b++) {
/*     */       
/* 199 */       if (paramInfo.matches(this.sourceLineInfo[b])) {
/* 200 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 204 */     for (b = 0; b < this.targetLineInfo.length; b++) {
/*     */       
/* 206 */       if (paramInfo.matches(this.targetLineInfo[b])) {
/* 207 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Line getLine(Line.Info paramInfo) throws LineUnavailableException;
/*     */   
/*     */   public abstract int getMaxLines(Line.Info paramInfo);
/*     */   
/*     */   protected abstract void implOpen() throws LineUnavailableException;
/*     */   
/*     */   protected abstract void implStart();
/*     */   
/*     */   protected abstract void implStop();
/*     */   
/*     */   protected abstract void implClose();
/*     */   
/*     */   public final Line[] getSourceLines() {
/*     */     Line[] arrayOfLine;
/* 229 */     synchronized (this.sourceLines) {
/*     */       
/* 231 */       arrayOfLine = new Line[this.sourceLines.size()];
/*     */       
/* 233 */       for (byte b = 0; b < arrayOfLine.length; b++) {
/* 234 */         arrayOfLine[b] = this.sourceLines.elementAt(b);
/*     */       }
/*     */     } 
/*     */     
/* 238 */     return arrayOfLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Line[] getTargetLines() {
/*     */     Line[] arrayOfLine;
/* 246 */     synchronized (this.targetLines) {
/*     */       
/* 248 */       arrayOfLine = new Line[this.targetLines.size()];
/*     */       
/* 250 */       for (byte b = 0; b < arrayOfLine.length; b++) {
/* 251 */         arrayOfLine[b] = this.targetLines.elementAt(b);
/*     */       }
/*     */     } 
/*     */     
/* 255 */     return arrayOfLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void synchronize(Line[] paramArrayOfLine, boolean paramBoolean) {
/* 263 */     throw new IllegalArgumentException("Synchronization not supported by this mixer.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void unsynchronize(Line[] paramArrayOfLine) {
/* 271 */     throw new IllegalArgumentException("Synchronization not supported by this mixer.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isSynchronizationSupported(Line[] paramArrayOfLine, boolean paramBoolean) {
/* 280 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void open() throws LineUnavailableException {
/* 290 */     open(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final synchronized void open(boolean paramBoolean) throws LineUnavailableException {
/* 298 */     if (!isOpen()) {
/* 299 */       implOpen();
/*     */       
/* 301 */       setOpen(true);
/* 302 */       if (paramBoolean) {
/* 303 */         this.manuallyOpened = true;
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
/*     */   final synchronized void open(Line paramLine) throws LineUnavailableException {
/* 326 */     if (equals(paramLine)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 332 */     if (isSourceLine(paramLine.getLineInfo())) {
/* 333 */       if (!this.sourceLines.contains(paramLine))
/*     */       {
/*     */         
/* 336 */         open(false);
/*     */ 
/*     */         
/* 339 */         this.sourceLines.addElement(paramLine);
/*     */       }
/*     */     
/*     */     }
/* 343 */     else if (isTargetLine(paramLine.getLineInfo()) && 
/* 344 */       !this.targetLines.contains(paramLine)) {
/*     */ 
/*     */       
/* 347 */       open(false);
/*     */ 
/*     */       
/* 350 */       this.targetLines.addElement(paramLine);
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
/*     */   final synchronized void close(Line paramLine) {
/* 371 */     if (equals(paramLine)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 376 */     this.sourceLines.removeElement(paramLine);
/* 377 */     this.targetLines.removeElement(paramLine);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     if (this.sourceLines.isEmpty() && this.targetLines.isEmpty() && !this.manuallyOpened)
/*     */     {
/* 385 */       close();
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
/*     */   public final synchronized void close() {
/* 397 */     if (isOpen()) {
/*     */       
/* 399 */       Line[] arrayOfLine = getSourceLines(); byte b;
/* 400 */       for (b = 0; b < arrayOfLine.length; b++) {
/* 401 */         arrayOfLine[b].close();
/*     */       }
/*     */ 
/*     */       
/* 405 */       arrayOfLine = getTargetLines();
/* 406 */       for (b = 0; b < arrayOfLine.length; b++) {
/* 407 */         arrayOfLine[b].close();
/*     */       }
/*     */       
/* 410 */       implClose();
/*     */ 
/*     */       
/* 413 */       setOpen(false);
/*     */     } 
/* 415 */     this.manuallyOpened = false;
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
/*     */   final synchronized void start(Line paramLine) {
/* 427 */     if (equals(paramLine)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 433 */     if (!this.started) {
/*     */       
/* 435 */       implStart();
/* 436 */       this.started = true;
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
/*     */   final synchronized void stop(Line paramLine) {
/* 451 */     if (equals(paramLine)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 456 */     Vector<AbstractDataLine> vector1 = (Vector)this.sourceLines.clone();
/* 457 */     for (byte b1 = 0; b1 < vector1.size(); b1++) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 462 */       if (vector1.elementAt(b1) instanceof AbstractDataLine) {
/* 463 */         AbstractDataLine abstractDataLine = vector1.elementAt(b1);
/* 464 */         if (abstractDataLine.isStartedRunning() && !abstractDataLine.equals(paramLine)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 471 */     Vector<AbstractDataLine> vector2 = (Vector)this.targetLines.clone();
/* 472 */     for (byte b2 = 0; b2 < vector2.size(); b2++) {
/*     */ 
/*     */ 
/*     */       
/* 476 */       if (vector2.elementAt(b2) instanceof AbstractDataLine) {
/* 477 */         AbstractDataLine abstractDataLine = vector2.elementAt(b2);
/* 478 */         if (abstractDataLine.isStartedRunning() && !abstractDataLine.equals(paramLine)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 487 */     this.started = false;
/* 488 */     implStop();
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
/*     */   final boolean isSourceLine(Line.Info paramInfo) {
/* 502 */     for (byte b = 0; b < this.sourceLineInfo.length; b++) {
/* 503 */       if (paramInfo.matches(this.sourceLineInfo[b])) {
/* 504 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 508 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean isTargetLine(Line.Info paramInfo) {
/* 519 */     for (byte b = 0; b < this.targetLineInfo.length; b++) {
/* 520 */       if (paramInfo.matches(this.targetLineInfo[b])) {
/* 521 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 525 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Line.Info getLineInfo(Line.Info paramInfo) {
/* 535 */     if (paramInfo == null) {
/* 536 */       return null;
/*     */     }
/*     */     
/*     */     byte b;
/*     */     
/* 541 */     for (b = 0; b < this.sourceLineInfo.length; b++) {
/* 542 */       if (paramInfo.matches(this.sourceLineInfo[b])) {
/* 543 */         return this.sourceLineInfo[b];
/*     */       }
/*     */     } 
/*     */     
/* 547 */     for (b = 0; b < this.targetLineInfo.length; b++) {
/* 548 */       if (paramInfo.matches(this.targetLineInfo[b])) {
/* 549 */         return this.targetLineInfo[b];
/*     */       }
/*     */     } 
/*     */     
/* 553 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AbstractMixer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */