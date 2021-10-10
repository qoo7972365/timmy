/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TextAction
/*     */   extends AbstractAction
/*     */ {
/*     */   public TextAction(String paramString) {
/*  69 */     super(paramString);
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
/*     */   protected final JTextComponent getTextComponent(ActionEvent paramActionEvent) {
/*  82 */     if (paramActionEvent != null) {
/*  83 */       Object object = paramActionEvent.getSource();
/*  84 */       if (object instanceof JTextComponent) {
/*  85 */         return (JTextComponent)object;
/*     */       }
/*     */     } 
/*  88 */     return getFocusedComponent();
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
/*     */   public static final Action[] augmentList(Action[] paramArrayOfAction1, Action[] paramArrayOfAction2) {
/* 106 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 107 */     for (Action action : paramArrayOfAction1) {
/* 108 */       String str = (String)action.getValue("Name");
/* 109 */       hashtable.put((str != null) ? str : "", action);
/*     */     } 
/* 111 */     for (Action action : paramArrayOfAction2) {
/* 112 */       String str = (String)action.getValue("Name");
/* 113 */       hashtable.put((str != null) ? str : "", action);
/*     */     } 
/* 115 */     Action[] arrayOfAction = new Action[hashtable.size()];
/* 116 */     byte b = 0;
/* 117 */     for (Enumeration<Action> enumeration = hashtable.elements(); enumeration.hasMoreElements();) {
/* 118 */       arrayOfAction[b++] = enumeration.nextElement();
/*     */     }
/* 120 */     return arrayOfAction;
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
/*     */   protected final JTextComponent getFocusedComponent() {
/* 133 */     return JTextComponent.getFocusedComponent();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/TextAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */