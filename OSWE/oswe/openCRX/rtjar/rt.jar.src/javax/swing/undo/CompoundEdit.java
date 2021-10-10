/*     */ package javax.swing.undo;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompoundEdit
/*     */   extends AbstractUndoableEdit
/*     */ {
/*     */   boolean inProgress = true;
/*  50 */   protected Vector<UndoableEdit> edits = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void undo() throws CannotUndoException {
/*  59 */     super.undo();
/*  60 */     int i = this.edits.size();
/*  61 */     while (i-- > 0) {
/*  62 */       UndoableEdit undoableEdit = this.edits.elementAt(i);
/*  63 */       undoableEdit.undo();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void redo() throws CannotRedoException {
/*  73 */     super.redo();
/*  74 */     Enumeration<UndoableEdit> enumeration = this.edits.elements();
/*  75 */     while (enumeration.hasMoreElements()) {
/*  76 */       ((UndoableEdit)enumeration.nextElement()).redo();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UndoableEdit lastEdit() {
/*  86 */     int i = this.edits.size();
/*  87 */     if (i > 0) {
/*  88 */       return this.edits.elementAt(i - 1);
/*     */     }
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void die() {
/*  98 */     int i = this.edits.size();
/*  99 */     for (int j = i - 1; j >= 0; j--) {
/*     */       
/* 101 */       UndoableEdit undoableEdit = this.edits.elementAt(j);
/*     */ 
/*     */       
/* 104 */       undoableEdit.die();
/*     */     } 
/* 106 */     super.die();
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
/*     */   public boolean addEdit(UndoableEdit paramUndoableEdit) {
/* 125 */     if (!this.inProgress) {
/* 126 */       return false;
/*     */     }
/* 128 */     UndoableEdit undoableEdit = lastEdit();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (undoableEdit == null) {
/* 136 */       this.edits.addElement(paramUndoableEdit);
/*     */     }
/* 138 */     else if (!undoableEdit.addEdit(paramUndoableEdit)) {
/* 139 */       if (paramUndoableEdit.replaceEdit(undoableEdit)) {
/* 140 */         this.edits.removeElementAt(this.edits.size() - 1);
/*     */       }
/* 142 */       this.edits.addElement(paramUndoableEdit);
/*     */     } 
/*     */     
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void end() {
/* 156 */     this.inProgress = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUndo() {
/* 166 */     return (!isInProgress() && super.canUndo());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRedo() {
/* 176 */     return (!isInProgress() && super.canRedo());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInProgress() {
/* 187 */     return this.inProgress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSignificant() {
/* 196 */     Enumeration<UndoableEdit> enumeration = this.edits.elements();
/* 197 */     while (enumeration.hasMoreElements()) {
/* 198 */       if (((UndoableEdit)enumeration.nextElement()).isSignificant()) {
/* 199 */         return true;
/*     */       }
/*     */     } 
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPresentationName() {
/* 212 */     UndoableEdit undoableEdit = lastEdit();
/* 213 */     if (undoableEdit != null) {
/* 214 */       return undoableEdit.getPresentationName();
/*     */     }
/* 216 */     return super.getPresentationName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUndoPresentationName() {
/* 227 */     UndoableEdit undoableEdit = lastEdit();
/* 228 */     if (undoableEdit != null) {
/* 229 */       return undoableEdit.getUndoPresentationName();
/*     */     }
/* 231 */     return super.getUndoPresentationName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRedoPresentationName() {
/* 242 */     UndoableEdit undoableEdit = lastEdit();
/* 243 */     if (undoableEdit != null) {
/* 244 */       return undoableEdit.getRedoPresentationName();
/*     */     }
/* 246 */     return super.getRedoPresentationName();
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
/*     */   public String toString() {
/* 258 */     return super.toString() + " inProgress: " + this.inProgress + " edits: " + this.edits;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/undo/CompoundEdit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */