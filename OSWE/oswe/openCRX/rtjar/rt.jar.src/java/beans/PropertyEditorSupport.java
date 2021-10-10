/*     */ package java.beans;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
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
/*     */ public class PropertyEditorSupport
/*     */   implements PropertyEditor
/*     */ {
/*     */   private Object value;
/*     */   private Object source;
/*     */   private Vector<PropertyChangeListener> listeners;
/*     */   
/*     */   public PropertyEditorSupport() {
/*  44 */     setSource(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyEditorSupport(Object paramObject) {
/*  54 */     if (paramObject == null) {
/*  55 */       throw new NullPointerException();
/*     */     }
/*  57 */     setSource(paramObject);
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
/*     */   public Object getSource() {
/*  70 */     return this.source;
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
/*     */   public void setSource(Object paramObject) {
/*  84 */     this.source = paramObject;
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
/*     */   public void setValue(Object paramObject) {
/*  96 */     this.value = paramObject;
/*  97 */     firePropertyChange();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 106 */     return this.value;
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
/*     */   public boolean isPaintable() {
/* 118 */     return false;
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
/*     */   public void paintValue(Graphics paramGraphics, Rectangle paramRectangle) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJavaInitializationString() {
/* 149 */     return "???";
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
/*     */   public String getAsText() {
/* 165 */     return (this.value != null) ? this.value
/* 166 */       .toString() : null;
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
/*     */   public void setAsText(String paramString) throws IllegalArgumentException {
/* 179 */     if (this.value instanceof String) {
/* 180 */       setValue(paramString);
/*     */       return;
/*     */     } 
/* 183 */     throw new IllegalArgumentException(paramString);
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
/*     */   public String[] getTags() {
/* 200 */     return null;
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
/*     */   public Component getCustomEditor() {
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsCustomEditor() {
/* 230 */     return false;
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
/*     */   public synchronized void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 253 */     if (this.listeners == null) {
/* 254 */       this.listeners = new Vector<>();
/*     */     }
/* 256 */     this.listeners.addElement(paramPropertyChangeListener);
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
/*     */   public synchronized void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 271 */     if (this.listeners == null) {
/*     */       return;
/*     */     }
/* 274 */     this.listeners.removeElement(paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void firePropertyChange() {
/*     */     Vector<PropertyChangeListener> vector;
/* 282 */     synchronized (this) {
/* 283 */       if (this.listeners == null) {
/*     */         return;
/*     */       }
/* 286 */       vector = unsafeClone(this.listeners);
/*     */     } 
/*     */     
/* 289 */     PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(this.source, null, null, null);
/*     */     
/* 291 */     for (byte b = 0; b < vector.size(); b++) {
/* 292 */       PropertyChangeListener propertyChangeListener = vector.elementAt(b);
/* 293 */       propertyChangeListener.propertyChange(propertyChangeEvent);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> Vector<T> unsafeClone(Vector<T> paramVector) {
/* 299 */     return (Vector<T>)paramVector.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/PropertyEditorSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */