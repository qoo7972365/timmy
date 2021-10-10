/*     */ package java.beans;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.util.EventListener;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertyChangeSupport
/*     */   implements Serializable
/*     */ {
/*  82 */   private PropertyChangeListenerMap map = new PropertyChangeListenerMap();
/*     */ 
/*     */   
/*     */   private Object source;
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeSupport(Object paramObject) {
/*  90 */     if (paramObject == null) {
/*  91 */       throw new NullPointerException();
/*     */     }
/*  93 */     this.source = paramObject;
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
/*     */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 107 */     if (paramPropertyChangeListener == null) {
/*     */       return;
/*     */     }
/* 110 */     if (paramPropertyChangeListener instanceof PropertyChangeListenerProxy) {
/* 111 */       PropertyChangeListenerProxy propertyChangeListenerProxy = (PropertyChangeListenerProxy)paramPropertyChangeListener;
/*     */ 
/*     */       
/* 114 */       addPropertyChangeListener(propertyChangeListenerProxy.getPropertyName(), propertyChangeListenerProxy
/* 115 */           .getListener());
/*     */     } else {
/* 117 */       this.map.add(null, paramPropertyChangeListener);
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
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 133 */     if (paramPropertyChangeListener == null) {
/*     */       return;
/*     */     }
/* 136 */     if (paramPropertyChangeListener instanceof PropertyChangeListenerProxy) {
/* 137 */       PropertyChangeListenerProxy propertyChangeListenerProxy = (PropertyChangeListenerProxy)paramPropertyChangeListener;
/*     */ 
/*     */       
/* 140 */       removePropertyChangeListener(propertyChangeListenerProxy.getPropertyName(), propertyChangeListenerProxy
/* 141 */           .getListener());
/*     */     } else {
/* 143 */       this.map.remove(null, paramPropertyChangeListener);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyChangeListener[] getPropertyChangeListeners() {
/* 179 */     return this.map.getListeners();
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
/*     */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 198 */     if (paramPropertyChangeListener == null || paramString == null) {
/*     */       return;
/*     */     }
/* 201 */     paramPropertyChangeListener = this.map.extract(paramPropertyChangeListener);
/* 202 */     if (paramPropertyChangeListener != null) {
/* 203 */       this.map.add(paramString, paramPropertyChangeListener);
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
/*     */   public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 223 */     if (paramPropertyChangeListener == null || paramString == null) {
/*     */       return;
/*     */     }
/* 226 */     paramPropertyChangeListener = this.map.extract(paramPropertyChangeListener);
/* 227 */     if (paramPropertyChangeListener != null) {
/* 228 */       this.map.remove(paramString, paramPropertyChangeListener);
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
/*     */   public PropertyChangeListener[] getPropertyChangeListeners(String paramString) {
/* 244 */     return this.map.getListeners(paramString);
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
/*     */   public void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 262 */     if (paramObject1 == null || paramObject2 == null || !paramObject1.equals(paramObject2)) {
/* 263 */       firePropertyChange(new PropertyChangeEvent(this.source, paramString, paramObject1, paramObject2));
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
/*     */   public void firePropertyChange(String paramString, int paramInt1, int paramInt2) {
/* 282 */     if (paramInt1 != paramInt2) {
/* 283 */       firePropertyChange(paramString, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
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
/*     */   public void firePropertyChange(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 302 */     if (paramBoolean1 != paramBoolean2) {
/* 303 */       firePropertyChange(paramString, Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2));
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
/*     */   public void firePropertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 317 */     Object object1 = paramPropertyChangeEvent.getOldValue();
/* 318 */     Object object2 = paramPropertyChangeEvent.getNewValue();
/* 319 */     if (object1 == null || object2 == null || !object1.equals(object2)) {
/* 320 */       String str = paramPropertyChangeEvent.getPropertyName();
/*     */       
/* 322 */       PropertyChangeListener[] arrayOfPropertyChangeListener1 = this.map.get(null);
/*     */       
/* 324 */       PropertyChangeListener[] arrayOfPropertyChangeListener2 = (str != null) ? this.map.get(str) : null;
/*     */ 
/*     */       
/* 327 */       fire(arrayOfPropertyChangeListener1, paramPropertyChangeEvent);
/* 328 */       fire(arrayOfPropertyChangeListener2, paramPropertyChangeEvent);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void fire(PropertyChangeListener[] paramArrayOfPropertyChangeListener, PropertyChangeEvent paramPropertyChangeEvent) {
/* 333 */     if (paramArrayOfPropertyChangeListener != null) {
/* 334 */       for (PropertyChangeListener propertyChangeListener : paramArrayOfPropertyChangeListener) {
/* 335 */         propertyChangeListener.propertyChange(paramPropertyChangeEvent);
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
/*     */   public void fireIndexedPropertyChange(String paramString, int paramInt, Object paramObject1, Object paramObject2) {
/* 357 */     if (paramObject1 == null || paramObject2 == null || !paramObject1.equals(paramObject2)) {
/* 358 */       firePropertyChange(new IndexedPropertyChangeEvent(this.source, paramString, paramObject1, paramObject2, paramInt));
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
/*     */   public void fireIndexedPropertyChange(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 379 */     if (paramInt2 != paramInt3) {
/* 380 */       fireIndexedPropertyChange(paramString, paramInt1, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3));
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
/*     */   public void fireIndexedPropertyChange(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 401 */     if (paramBoolean1 != paramBoolean2) {
/* 402 */       fireIndexedPropertyChange(paramString, paramInt, Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2));
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
/*     */   public boolean hasListeners(String paramString) {
/* 415 */     return this.map.hasListeners(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 425 */     Hashtable<Object, Object> hashtable = null;
/* 426 */     PropertyChangeListener[] arrayOfPropertyChangeListener = null;
/* 427 */     synchronized (this.map) {
/* 428 */       for (Map.Entry<String, PropertyChangeListener[]> entry : this.map.getEntries()) {
/* 429 */         String str = (String)entry.getKey();
/* 430 */         if (str == null) {
/* 431 */           arrayOfPropertyChangeListener = (PropertyChangeListener[])entry.getValue(); continue;
/*     */         } 
/* 433 */         if (hashtable == null) {
/* 434 */           hashtable = new Hashtable<>();
/*     */         }
/* 436 */         PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this.source);
/* 437 */         propertyChangeSupport.map.set(null, (PropertyChangeListener[])entry.getValue());
/* 438 */         hashtable.put(str, propertyChangeSupport);
/*     */       } 
/*     */     } 
/*     */     
/* 442 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 443 */     putField.put("children", hashtable);
/* 444 */     putField.put("source", this.source);
/* 445 */     putField.put("propertyChangeSupportSerializedDataVersion", 2);
/* 446 */     paramObjectOutputStream.writeFields();
/*     */     
/* 448 */     if (arrayOfPropertyChangeListener != null) {
/* 449 */       for (PropertyChangeListener propertyChangeListener : arrayOfPropertyChangeListener) {
/* 450 */         if (propertyChangeListener instanceof Serializable) {
/* 451 */           paramObjectOutputStream.writeObject(propertyChangeListener);
/*     */         }
/*     */       } 
/*     */     }
/* 455 */     paramObjectOutputStream.writeObject(null);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 459 */     this.map = new PropertyChangeListenerMap();
/*     */     
/* 461 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */ 
/*     */     
/* 464 */     Hashtable hashtable = (Hashtable)getField.get("children", (Object)null);
/* 465 */     this.source = getField.get("source", (Object)null);
/* 466 */     getField.get("propertyChangeSupportSerializedDataVersion", 2);
/*     */     
/*     */     Object object;
/* 469 */     while (null != (object = paramObjectInputStream.readObject())) {
/* 470 */       this.map.add(null, (PropertyChangeListener)object);
/*     */     }
/* 472 */     if (hashtable != null) {
/* 473 */       for (Map.Entry entry : hashtable.entrySet()) {
/* 474 */         for (PropertyChangeListener propertyChangeListener : ((PropertyChangeSupport)entry.getValue()).getPropertyChangeListeners()) {
/* 475 */           this.map.add((String)entry.getKey(), propertyChangeListener);
/*     */         }
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
/* 491 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("children", Hashtable.class), new ObjectStreamField("source", Object.class), new ObjectStreamField("propertyChangeSupportSerializedDataVersion", int.class) };
/*     */ 
/*     */ 
/*     */   
/*     */   static final long serialVersionUID = 6401253773779951803L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class PropertyChangeListenerMap
/*     */     extends ChangeListenerMap<PropertyChangeListener>
/*     */   {
/*     */     private PropertyChangeListenerMap() {}
/*     */ 
/*     */ 
/*     */     
/* 507 */     private static final PropertyChangeListener[] EMPTY = new PropertyChangeListener[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected PropertyChangeListener[] newArray(int param1Int) {
/* 519 */       return (0 < param1Int) ? new PropertyChangeListener[param1Int] : EMPTY;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected PropertyChangeListener newProxy(String param1String, PropertyChangeListener param1PropertyChangeListener) {
/* 534 */       return new PropertyChangeListenerProxy(param1String, param1PropertyChangeListener);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final PropertyChangeListener extract(PropertyChangeListener param1PropertyChangeListener) {
/* 541 */       while (param1PropertyChangeListener instanceof PropertyChangeListenerProxy) {
/* 542 */         param1PropertyChangeListener = ((PropertyChangeListenerProxy)param1PropertyChangeListener).getListener();
/*     */       }
/* 544 */       return param1PropertyChangeListener;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/PropertyChangeSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */