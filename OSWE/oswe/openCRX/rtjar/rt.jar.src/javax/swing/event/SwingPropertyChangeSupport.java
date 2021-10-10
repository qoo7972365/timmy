/*     */ package javax.swing.event;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SwingPropertyChangeSupport
/*     */   extends PropertyChangeSupport
/*     */ {
/*     */   static final long serialVersionUID = 7162625831330845068L;
/*     */   private final boolean notifyOnEDT;
/*     */   
/*     */   public SwingPropertyChangeSupport(Object paramObject) {
/*  53 */     this(paramObject, false);
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
/*     */   public SwingPropertyChangeSupport(Object paramObject, boolean paramBoolean) {
/*  68 */     super(paramObject);
/*  69 */     this.notifyOnEDT = paramBoolean;
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
/*     */   public void firePropertyChange(final PropertyChangeEvent evt) {
/*  87 */     if (evt == null) {
/*  88 */       throw new NullPointerException();
/*     */     }
/*  90 */     if (!isNotifyOnEDT() || 
/*  91 */       SwingUtilities.isEventDispatchThread()) {
/*  92 */       super.firePropertyChange(evt);
/*     */     } else {
/*  94 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/*  97 */               SwingPropertyChangeSupport.this.firePropertyChange(evt);
/*     */             }
/*     */           });
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
/*     */   public final boolean isNotifyOnEDT() {
/* 111 */     return this.notifyOnEDT;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/SwingPropertyChangeSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */