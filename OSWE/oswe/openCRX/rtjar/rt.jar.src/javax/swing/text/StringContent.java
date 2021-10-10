/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
/*     */ import javax.swing.undo.AbstractUndoableEdit;
/*     */ import javax.swing.undo.CannotRedoException;
/*     */ import javax.swing.undo.CannotUndoException;
/*     */ import javax.swing.undo.UndoableEdit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringContent
/*     */   implements AbstractDocument.Content, Serializable
/*     */ {
/*     */   public StringContent() {
/*  59 */     this(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringContent(int paramInt) {
/*  69 */     if (paramInt < 1) {
/*  70 */       paramInt = 1;
/*     */     }
/*  72 */     this.data = new char[paramInt];
/*  73 */     this.data[0] = '\n';
/*  74 */     this.count = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/*  84 */     return this.count;
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
/*     */   public UndoableEdit insertString(int paramInt, String paramString) throws BadLocationException {
/*  97 */     if (paramInt >= this.count || paramInt < 0) {
/*  98 */       throw new BadLocationException("Invalid location", this.count);
/*     */     }
/* 100 */     char[] arrayOfChar = paramString.toCharArray();
/* 101 */     replace(paramInt, 0, arrayOfChar, 0, arrayOfChar.length);
/* 102 */     if (this.marks != null) {
/* 103 */       updateMarksForInsert(paramInt, paramString.length());
/*     */     }
/* 105 */     return new InsertUndo(paramInt, paramString.length());
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
/*     */   public UndoableEdit remove(int paramInt1, int paramInt2) throws BadLocationException {
/* 118 */     if (paramInt1 + paramInt2 >= this.count) {
/* 119 */       throw new BadLocationException("Invalid range", this.count);
/*     */     }
/* 121 */     String str = getString(paramInt1, paramInt2);
/* 122 */     RemoveUndo removeUndo = new RemoveUndo(paramInt1, str);
/* 123 */     replace(paramInt1, paramInt2, empty, 0, 0);
/* 124 */     if (this.marks != null) {
/* 125 */       updateMarksForRemove(paramInt1, paramInt2);
/*     */     }
/* 127 */     return removeUndo;
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
/*     */   public String getString(int paramInt1, int paramInt2) throws BadLocationException {
/* 141 */     if (paramInt1 + paramInt2 > this.count) {
/* 142 */       throw new BadLocationException("Invalid range", this.count);
/*     */     }
/* 144 */     return new String(this.data, paramInt1, paramInt2);
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
/*     */   public void getChars(int paramInt1, int paramInt2, Segment paramSegment) throws BadLocationException {
/* 157 */     if (paramInt1 + paramInt2 > this.count) {
/* 158 */       throw new BadLocationException("Invalid location", this.count);
/*     */     }
/* 160 */     paramSegment.array = this.data;
/* 161 */     paramSegment.offset = paramInt1;
/* 162 */     paramSegment.count = paramInt2;
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
/*     */   public Position createPosition(int paramInt) throws BadLocationException {
/* 176 */     if (this.marks == null) {
/* 177 */       this.marks = new Vector<>();
/*     */     }
/* 179 */     return new StickyPosition(paramInt);
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
/*     */   void replace(int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4) {
/* 195 */     int i = paramInt4 - paramInt2;
/* 196 */     int j = paramInt1 + paramInt2;
/* 197 */     int k = this.count - j;
/* 198 */     int m = j + i;
/* 199 */     if (this.count + i >= this.data.length) {
/*     */       
/* 201 */       int n = Math.max(2 * this.data.length, this.count + i);
/* 202 */       char[] arrayOfChar = new char[n];
/* 203 */       System.arraycopy(this.data, 0, arrayOfChar, 0, paramInt1);
/* 204 */       System.arraycopy(paramArrayOfchar, paramInt3, arrayOfChar, paramInt1, paramInt4);
/* 205 */       System.arraycopy(this.data, j, arrayOfChar, m, k);
/* 206 */       this.data = arrayOfChar;
/*     */     } else {
/*     */       
/* 209 */       System.arraycopy(this.data, j, this.data, m, k);
/* 210 */       System.arraycopy(paramArrayOfchar, paramInt3, this.data, paramInt1, paramInt4);
/*     */     } 
/* 212 */     this.count += i;
/*     */   }
/*     */   
/*     */   void resize(int paramInt) {
/* 216 */     char[] arrayOfChar = new char[paramInt];
/* 217 */     System.arraycopy(this.data, 0, arrayOfChar, 0, Math.min(paramInt, this.count));
/* 218 */     this.data = arrayOfChar;
/*     */   }
/*     */   
/*     */   synchronized void updateMarksForInsert(int paramInt1, int paramInt2) {
/* 222 */     if (paramInt1 == 0)
/*     */     {
/*     */       
/* 225 */       paramInt1 = 1;
/*     */     }
/* 227 */     int i = this.marks.size();
/* 228 */     for (byte b = 0; b < i; b++) {
/* 229 */       PosRec posRec = this.marks.elementAt(b);
/* 230 */       if (posRec.unused) {
/*     */         
/* 232 */         this.marks.removeElementAt(b);
/* 233 */         b--;
/* 234 */         i--;
/* 235 */       } else if (posRec.offset >= paramInt1) {
/* 236 */         posRec.offset += paramInt2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void updateMarksForRemove(int paramInt1, int paramInt2) {
/* 242 */     int i = this.marks.size();
/* 243 */     for (byte b = 0; b < i; b++) {
/* 244 */       PosRec posRec = this.marks.elementAt(b);
/* 245 */       if (posRec.unused) {
/*     */         
/* 247 */         this.marks.removeElementAt(b);
/* 248 */         b--;
/* 249 */         i--;
/* 250 */       } else if (posRec.offset >= paramInt1 + paramInt2) {
/* 251 */         posRec.offset -= paramInt2;
/* 252 */       } else if (posRec.offset >= paramInt1) {
/* 253 */         posRec.offset = paramInt1;
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
/*     */   protected Vector getPositionsInRange(Vector paramVector, int paramInt1, int paramInt2) {
/* 275 */     int i = this.marks.size();
/* 276 */     int j = paramInt1 + paramInt2;
/* 277 */     Vector<UndoPosRef> vector = (paramVector == null) ? new Vector() : paramVector;
/* 278 */     for (byte b = 0; b < i; b++) {
/* 279 */       PosRec posRec = this.marks.elementAt(b);
/* 280 */       if (posRec.unused) {
/*     */         
/* 282 */         this.marks.removeElementAt(b);
/* 283 */         b--;
/* 284 */         i--;
/* 285 */       } else if (posRec.offset >= paramInt1 && posRec.offset <= j) {
/* 286 */         vector.addElement(new UndoPosRef(posRec));
/*     */       } 
/* 288 */     }  return vector;
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
/*     */   protected void updateUndoPositions(Vector<UndoPosRef> paramVector) {
/* 301 */     for (int i = paramVector.size() - 1; i >= 0; i--) {
/* 302 */       UndoPosRef undoPosRef = paramVector.elementAt(i);
/*     */       
/* 304 */       if (undoPosRef.rec.unused) {
/* 305 */         paramVector.removeElementAt(i);
/*     */       } else {
/*     */         
/* 308 */         undoPosRef.resetLocation();
/*     */       } 
/*     */     } 
/*     */   }
/* 312 */   private static final char[] empty = new char[0];
/*     */   
/*     */   private char[] data;
/*     */   
/*     */   private int count;
/*     */   
/*     */   transient Vector<PosRec> marks;
/*     */ 
/*     */   
/*     */   final class PosRec
/*     */   {
/*     */     int offset;
/*     */     boolean unused;
/*     */     
/*     */     PosRec(int param1Int) {
/* 327 */       this.offset = param1Int;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final class StickyPosition
/*     */     implements Position
/*     */   {
/*     */     StringContent.PosRec rec;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     StickyPosition(int param1Int) {
/* 343 */       this.rec = new StringContent.PosRec(param1Int);
/* 344 */       StringContent.this.marks.addElement(this.rec);
/*     */     }
/*     */     
/*     */     public int getOffset() {
/* 348 */       return this.rec.offset;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void finalize() throws Throwable {
/* 354 */       this.rec.unused = true;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 358 */       return Integer.toString(getOffset());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   final class UndoPosRef
/*     */   {
/*     */     protected int undoLocation;
/*     */     
/*     */     protected StringContent.PosRec rec;
/*     */     
/*     */     UndoPosRef(StringContent.PosRec param1PosRec) {
/* 370 */       this.rec = param1PosRec;
/* 371 */       this.undoLocation = param1PosRec.offset;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void resetLocation() {
/* 379 */       this.rec.offset = this.undoLocation;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class InsertUndo
/*     */     extends AbstractUndoableEdit
/*     */   {
/*     */     protected int offset;
/*     */     
/*     */     protected int length;
/*     */     protected String string;
/*     */     protected Vector posRefs;
/*     */     
/*     */     protected InsertUndo(int param1Int1, int param1Int2) {
/* 394 */       this.offset = param1Int1;
/* 395 */       this.length = param1Int2;
/*     */     }
/*     */     
/*     */     public void undo() throws CannotUndoException {
/* 399 */       super.undo();
/*     */       try {
/* 401 */         synchronized (StringContent.this) {
/*     */           
/* 403 */           if (StringContent.this.marks != null)
/* 404 */             this.posRefs = StringContent.this.getPositionsInRange(null, this.offset, this.length); 
/* 405 */           this.string = StringContent.this.getString(this.offset, this.length);
/* 406 */           StringContent.this.remove(this.offset, this.length);
/*     */         } 
/* 408 */       } catch (BadLocationException badLocationException) {
/* 409 */         throw new CannotUndoException();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void redo() throws CannotRedoException {
/* 414 */       super.redo();
/*     */       try {
/* 416 */         synchronized (StringContent.this) {
/* 417 */           StringContent.this.insertString(this.offset, this.string);
/* 418 */           this.string = null;
/*     */           
/* 420 */           if (this.posRefs != null) {
/* 421 */             StringContent.this.updateUndoPositions(this.posRefs);
/* 422 */             this.posRefs = null;
/*     */           } 
/*     */         } 
/* 425 */       } catch (BadLocationException badLocationException) {
/* 426 */         throw new CannotRedoException();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class RemoveUndo
/*     */     extends AbstractUndoableEdit
/*     */   {
/*     */     protected int offset;
/*     */ 
/*     */     
/*     */     protected int length;
/*     */ 
/*     */     
/*     */     protected String string;
/*     */ 
/*     */     
/*     */     protected Vector posRefs;
/*     */ 
/*     */     
/*     */     protected RemoveUndo(int param1Int, String param1String) {
/* 449 */       this.offset = param1Int;
/* 450 */       this.string = param1String;
/* 451 */       this.length = param1String.length();
/* 452 */       if (StringContent.this.marks != null)
/* 453 */         this.posRefs = StringContent.this.getPositionsInRange(null, param1Int, this.length); 
/*     */     }
/*     */     
/*     */     public void undo() throws CannotUndoException {
/* 457 */       super.undo();
/*     */       try {
/* 459 */         synchronized (StringContent.this) {
/* 460 */           StringContent.this.insertString(this.offset, this.string);
/*     */           
/* 462 */           if (this.posRefs != null) {
/* 463 */             StringContent.this.updateUndoPositions(this.posRefs);
/* 464 */             this.posRefs = null;
/*     */           } 
/* 466 */           this.string = null;
/*     */         } 
/* 468 */       } catch (BadLocationException badLocationException) {
/* 469 */         throw new CannotUndoException();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void redo() throws CannotRedoException {
/* 474 */       super.redo();
/*     */       try {
/* 476 */         synchronized (StringContent.this) {
/* 477 */           this.string = StringContent.this.getString(this.offset, this.length);
/*     */           
/* 479 */           if (StringContent.this.marks != null)
/* 480 */             this.posRefs = StringContent.this.getPositionsInRange(null, this.offset, this.length); 
/* 481 */           StringContent.this.remove(this.offset, this.length);
/*     */         } 
/* 483 */       } catch (BadLocationException badLocationException) {
/* 484 */         throw new CannotRedoException();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/StringContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */