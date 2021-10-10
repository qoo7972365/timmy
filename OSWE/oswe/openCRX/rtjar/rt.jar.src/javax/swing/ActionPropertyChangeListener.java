/*     */ package javax.swing;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ActionPropertyChangeListener<T extends JComponent>
/*     */   implements PropertyChangeListener, Serializable
/*     */ {
/*     */   private static ReferenceQueue<JComponent> queue;
/*     */   private transient OwnedWeakReference<T> target;
/*     */   private Action action;
/*     */   
/*     */   private static ReferenceQueue<JComponent> getQueue() {
/*  64 */     synchronized (ActionPropertyChangeListener.class) {
/*  65 */       if (queue == null) {
/*  66 */         queue = new ReferenceQueue<>();
/*     */       }
/*     */     } 
/*  69 */     return queue;
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionPropertyChangeListener(T paramT, Action paramAction) {
/*  74 */     setTarget(paramT);
/*  75 */     this.action = paramAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/*  84 */     T t = getTarget();
/*  85 */     if (t == null) {
/*  86 */       getAction().removePropertyChangeListener(this);
/*     */     } else {
/*  88 */       actionPropertyChanged(t, getAction(), paramPropertyChangeEvent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void actionPropertyChanged(T paramT, Action paramAction, PropertyChangeEvent paramPropertyChangeEvent);
/*     */ 
/*     */ 
/*     */   
/*     */   private void setTarget(T paramT) {
/* 100 */     ReferenceQueue<JComponent> referenceQueue = getQueue();
/*     */ 
/*     */     
/*     */     OwnedWeakReference ownedWeakReference;
/*     */     
/* 105 */     while ((ownedWeakReference = (OwnedWeakReference)referenceQueue.poll()) != null) {
/* 106 */       ActionPropertyChangeListener<?> actionPropertyChangeListener = ownedWeakReference.getOwner();
/* 107 */       Action action = actionPropertyChangeListener.getAction();
/* 108 */       if (action != null) {
/* 109 */         action.removePropertyChangeListener(actionPropertyChangeListener);
/*     */       }
/*     */     } 
/* 112 */     this.target = new OwnedWeakReference<>(paramT, (ReferenceQueue)referenceQueue, this);
/*     */   }
/*     */   
/*     */   public T getTarget() {
/* 116 */     if (this.target == null)
/*     */     {
/* 118 */       return null;
/*     */     }
/* 120 */     return this.target.get();
/*     */   }
/*     */   
/*     */   public Action getAction() {
/* 124 */     return this.action;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 128 */     paramObjectOutputStream.defaultWriteObject();
/* 129 */     paramObjectOutputStream.writeObject(getTarget());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 135 */     paramObjectInputStream.defaultReadObject();
/* 136 */     JComponent jComponent = (JComponent)paramObjectInputStream.readObject();
/* 137 */     if (jComponent != null) {
/* 138 */       setTarget((T)jComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class OwnedWeakReference<U extends JComponent>
/*     */     extends WeakReference<U>
/*     */   {
/*     */     private ActionPropertyChangeListener<?> owner;
/*     */     
/*     */     OwnedWeakReference(U param1U, ReferenceQueue<? super U> param1ReferenceQueue, ActionPropertyChangeListener<?> param1ActionPropertyChangeListener) {
/* 149 */       super(param1U, param1ReferenceQueue);
/* 150 */       this.owner = param1ActionPropertyChangeListener;
/*     */     }
/*     */     
/*     */     public ActionPropertyChangeListener<?> getOwner() {
/* 154 */       return this.owner;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ActionPropertyChangeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */