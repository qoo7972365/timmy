/*     */ package javax.swing.undo;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.swing.event.UndoableEditEvent;
/*     */ import javax.swing.event.UndoableEditListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UndoableEditSupport
/*     */ {
/*     */   protected int updateLevel;
/*     */   protected CompoundEdit compoundEdit;
/*     */   protected Vector<UndoableEditListener> listeners;
/*     */   protected Object realSource;
/*     */   
/*     */   public UndoableEditSupport() {
/*  46 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UndoableEditSupport(Object paramObject) {
/*  55 */     this.realSource = (paramObject == null) ? this : paramObject;
/*  56 */     this.updateLevel = 0;
/*  57 */     this.compoundEdit = null;
/*  58 */     this.listeners = new Vector<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addUndoableEditListener(UndoableEditListener paramUndoableEditListener) {
/*  69 */     this.listeners.addElement(paramUndoableEditListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeUndoableEditListener(UndoableEditListener paramUndoableEditListener) {
/*  80 */     this.listeners.removeElement(paramUndoableEditListener);
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
/*     */   public synchronized UndoableEditListener[] getUndoableEditListeners() {
/*  92 */     return this.listeners.<UndoableEditListener>toArray(new UndoableEditListener[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _postEdit(UndoableEdit paramUndoableEdit) {
/* 101 */     UndoableEditEvent undoableEditEvent = new UndoableEditEvent(this.realSource, paramUndoableEdit);
/* 102 */     Enumeration<UndoableEditListener> enumeration = ((Vector)this.listeners.clone()).elements();
/* 103 */     while (enumeration.hasMoreElements()) {
/* 104 */       ((UndoableEditListener)enumeration.nextElement())
/* 105 */         .undoableEditHappened(undoableEditEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void postEdit(UndoableEdit paramUndoableEdit) {
/* 115 */     if (this.updateLevel == 0) {
/* 116 */       _postEdit(paramUndoableEdit);
/*     */     } else {
/*     */       
/* 119 */       this.compoundEdit.addEdit(paramUndoableEdit);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpdateLevel() {
/* 129 */     return this.updateLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void beginUpdate() {
/* 136 */     if (this.updateLevel == 0) {
/* 137 */       this.compoundEdit = createCompoundEdit();
/*     */     }
/* 139 */     this.updateLevel++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CompoundEdit createCompoundEdit() {
/* 147 */     return new CompoundEdit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void endUpdate() {
/* 156 */     this.updateLevel--;
/* 157 */     if (this.updateLevel == 0) {
/* 158 */       this.compoundEdit.end();
/* 159 */       _postEdit(this.compoundEdit);
/* 160 */       this.compoundEdit = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 171 */     return super.toString() + " updateLevel: " + this.updateLevel + " listeners: " + this.listeners + " compoundEdit: " + this.compoundEdit;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/undo/UndoableEditSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */