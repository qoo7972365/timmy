/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.event.AncestorEvent;
/*     */ import javax.swing.event.AncestorListener;
/*     */ import javax.swing.event.EventListenerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AncestorNotifier
/*     */   implements ComponentListener, PropertyChangeListener, Serializable
/*     */ {
/*     */   transient Component firstInvisibleAncestor;
/*  49 */   EventListenerList listenerList = new EventListenerList();
/*     */   JComponent root;
/*     */   
/*     */   AncestorNotifier(JComponent paramJComponent) {
/*  53 */     this.root = paramJComponent;
/*  54 */     addListeners(paramJComponent, true);
/*     */   }
/*     */   
/*     */   void addAncestorListener(AncestorListener paramAncestorListener) {
/*  58 */     this.listenerList.add(AncestorListener.class, paramAncestorListener);
/*     */   }
/*     */   
/*     */   void removeAncestorListener(AncestorListener paramAncestorListener) {
/*  62 */     this.listenerList.remove(AncestorListener.class, paramAncestorListener);
/*     */   }
/*     */   
/*     */   AncestorListener[] getAncestorListeners() {
/*  66 */     return this.listenerList.<AncestorListener>getListeners(AncestorListener.class);
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
/*     */   protected void fireAncestorAdded(JComponent paramJComponent, int paramInt, Container paramContainer1, Container paramContainer2) {
/*  78 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/*  81 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  82 */       if (arrayOfObject[i] == AncestorListener.class) {
/*     */         
/*  84 */         AncestorEvent ancestorEvent = new AncestorEvent(paramJComponent, paramInt, paramContainer1, paramContainer2);
/*     */         
/*  86 */         ((AncestorListener)arrayOfObject[i + 1]).ancestorAdded(ancestorEvent);
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
/*     */   protected void fireAncestorRemoved(JComponent paramJComponent, int paramInt, Container paramContainer1, Container paramContainer2) {
/* 100 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/* 103 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 104 */       if (arrayOfObject[i] == AncestorListener.class) {
/*     */         
/* 106 */         AncestorEvent ancestorEvent = new AncestorEvent(paramJComponent, paramInt, paramContainer1, paramContainer2);
/*     */         
/* 108 */         ((AncestorListener)arrayOfObject[i + 1]).ancestorRemoved(ancestorEvent);
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
/*     */   protected void fireAncestorMoved(JComponent paramJComponent, int paramInt, Container paramContainer1, Container paramContainer2) {
/* 121 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/* 124 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 125 */       if (arrayOfObject[i] == AncestorListener.class) {
/*     */         
/* 127 */         AncestorEvent ancestorEvent = new AncestorEvent(paramJComponent, paramInt, paramContainer1, paramContainer2);
/*     */         
/* 129 */         ((AncestorListener)arrayOfObject[i + 1]).ancestorMoved(ancestorEvent);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void removeAllListeners() {
/* 135 */     removeListeners(this.root);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void addListeners(Component paramComponent, boolean paramBoolean) {
/* 141 */     this.firstInvisibleAncestor = null;
/* 142 */     Component component = paramComponent;
/* 143 */     for (; this.firstInvisibleAncestor == null; 
/* 144 */       component = component.getParent()) {
/* 145 */       if (paramBoolean || component != paramComponent) {
/* 146 */         component.addComponentListener(this);
/*     */         
/* 148 */         if (component instanceof JComponent) {
/* 149 */           JComponent jComponent = (JComponent)component;
/*     */           
/* 151 */           jComponent.addPropertyChangeListener(this);
/*     */         } 
/*     */       } 
/* 154 */       if (!component.isVisible() || component.getParent() == null || component instanceof java.awt.Window) {
/* 155 */         this.firstInvisibleAncestor = component;
/*     */       }
/*     */     } 
/* 158 */     if (this.firstInvisibleAncestor instanceof java.awt.Window && this.firstInvisibleAncestor
/* 159 */       .isVisible()) {
/* 160 */       this.firstInvisibleAncestor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void removeListeners(Component paramComponent) {
/* 166 */     for (Component component = paramComponent; component != null; component = component.getParent()) {
/* 167 */       component.removeComponentListener(this);
/* 168 */       if (component instanceof JComponent) {
/* 169 */         JComponent jComponent = (JComponent)component;
/* 170 */         jComponent.removePropertyChangeListener(this);
/*     */       } 
/* 172 */       if (component == this.firstInvisibleAncestor || component instanceof java.awt.Window) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void componentResized(ComponentEvent paramComponentEvent) {}
/*     */   
/*     */   public void componentMoved(ComponentEvent paramComponentEvent) {
/* 181 */     Component component = paramComponentEvent.getComponent();
/*     */     
/* 183 */     fireAncestorMoved(this.root, 3, (Container)component, component
/* 184 */         .getParent());
/*     */   }
/*     */   
/*     */   public void componentShown(ComponentEvent paramComponentEvent) {
/* 188 */     Component component = paramComponentEvent.getComponent();
/*     */     
/* 190 */     if (component == this.firstInvisibleAncestor) {
/* 191 */       addListeners(component, false);
/* 192 */       if (this.firstInvisibleAncestor == null) {
/* 193 */         fireAncestorAdded(this.root, 1, (Container)component, component
/* 194 */             .getParent());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void componentHidden(ComponentEvent paramComponentEvent) {
/* 200 */     Component component = paramComponentEvent.getComponent();
/* 201 */     boolean bool = (this.firstInvisibleAncestor == null) ? true : false;
/*     */     
/* 203 */     if (!(component instanceof java.awt.Window)) {
/* 204 */       removeListeners(component.getParent());
/*     */     }
/* 206 */     this.firstInvisibleAncestor = component;
/* 207 */     if (bool) {
/* 208 */       fireAncestorRemoved(this.root, 2, (Container)component, component
/* 209 */           .getParent());
/*     */     }
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 214 */     String str = paramPropertyChangeEvent.getPropertyName();
/*     */     
/* 216 */     if (str != null && (str.equals("parent") || str.equals("ancestor"))) {
/* 217 */       JComponent jComponent = (JComponent)paramPropertyChangeEvent.getSource();
/*     */       
/* 219 */       if (paramPropertyChangeEvent.getNewValue() != null) {
/* 220 */         if (jComponent == this.firstInvisibleAncestor) {
/* 221 */           addListeners(jComponent, false);
/* 222 */           if (this.firstInvisibleAncestor == null) {
/* 223 */             fireAncestorAdded(this.root, 1, jComponent, jComponent
/* 224 */                 .getParent());
/*     */           }
/*     */         } 
/*     */       } else {
/* 228 */         boolean bool = (this.firstInvisibleAncestor == null) ? true : false;
/* 229 */         Container container = (Container)paramPropertyChangeEvent.getOldValue();
/*     */         
/* 231 */         removeListeners(container);
/* 232 */         this.firstInvisibleAncestor = jComponent;
/* 233 */         if (bool)
/* 234 */           fireAncestorRemoved(this.root, 2, jComponent, container); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/AncestorNotifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */