/*     */ package javax.swing.undo;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StateEdit
/*     */   extends AbstractUndoableEdit
/*     */ {
/*     */   protected static final String RCSID = "$Id: StateEdit.java,v 1.6 1997/10/01 20:05:51 sandipc Exp $";
/*     */   protected StateEditable object;
/*     */   protected Hashtable<Object, Object> preState;
/*     */   protected Hashtable<Object, Object> postState;
/*     */   protected String undoRedoName;
/*     */   
/*     */   public StateEdit(StateEditable paramStateEditable) {
/* 101 */     init(paramStateEditable, null);
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
/*     */   public StateEdit(StateEditable paramStateEditable, String paramString) {
/* 114 */     init(paramStateEditable, paramString);
/*     */   }
/*     */   
/*     */   protected void init(StateEditable paramStateEditable, String paramString) {
/* 118 */     this.object = paramStateEditable;
/* 119 */     this.preState = new Hashtable<>(11);
/* 120 */     this.object.storeState(this.preState);
/* 121 */     this.postState = null;
/* 122 */     this.undoRedoName = paramString;
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
/*     */   public void end() {
/* 136 */     this.postState = new Hashtable<>(11);
/* 137 */     this.object.storeState(this.postState);
/* 138 */     removeRedundantState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void undo() {
/* 145 */     super.undo();
/* 146 */     this.object.restoreState(this.preState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void redo() {
/* 153 */     super.redo();
/* 154 */     this.object.restoreState(this.postState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPresentationName() {
/* 161 */     return this.undoRedoName;
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
/*     */   protected void removeRedundantState() {
/* 173 */     Vector<Object> vector = new Vector();
/* 174 */     Enumeration<Object> enumeration = this.preState.keys();
/*     */ 
/*     */     
/* 177 */     while (enumeration.hasMoreElements()) {
/* 178 */       Object object = enumeration.nextElement();
/* 179 */       if (this.postState.containsKey(object) && this.postState
/* 180 */         .get(object).equals(this.preState.get(object))) {
/* 181 */         vector.addElement(object);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 186 */     for (int i = vector.size() - 1; i >= 0; i--) {
/* 187 */       Object object = vector.elementAt(i);
/* 188 */       this.preState.remove(object);
/* 189 */       this.postState.remove(object);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/undo/StateEdit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */