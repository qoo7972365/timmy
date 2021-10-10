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
/*     */ public class VetoableChangeSupport
/*     */   implements Serializable
/*     */ {
/*  82 */   private VetoableChangeListenerMap map = new VetoableChangeListenerMap();
/*     */ 
/*     */   
/*     */   private Object source;
/*     */ 
/*     */ 
/*     */   
/*     */   public VetoableChangeSupport(Object paramObject) {
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
/*     */   public void addVetoableChangeListener(VetoableChangeListener paramVetoableChangeListener) {
/* 107 */     if (paramVetoableChangeListener == null) {
/*     */       return;
/*     */     }
/* 110 */     if (paramVetoableChangeListener instanceof VetoableChangeListenerProxy) {
/* 111 */       VetoableChangeListenerProxy vetoableChangeListenerProxy = (VetoableChangeListenerProxy)paramVetoableChangeListener;
/*     */ 
/*     */       
/* 114 */       addVetoableChangeListener(vetoableChangeListenerProxy.getPropertyName(), vetoableChangeListenerProxy
/* 115 */           .getListener());
/*     */     } else {
/* 117 */       this.map.add(null, paramVetoableChangeListener);
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
/*     */   public void removeVetoableChangeListener(VetoableChangeListener paramVetoableChangeListener) {
/* 133 */     if (paramVetoableChangeListener == null) {
/*     */       return;
/*     */     }
/* 136 */     if (paramVetoableChangeListener instanceof VetoableChangeListenerProxy) {
/* 137 */       VetoableChangeListenerProxy vetoableChangeListenerProxy = (VetoableChangeListenerProxy)paramVetoableChangeListener;
/*     */ 
/*     */       
/* 140 */       removeVetoableChangeListener(vetoableChangeListenerProxy.getPropertyName(), vetoableChangeListenerProxy
/* 141 */           .getListener());
/*     */     } else {
/* 143 */       this.map.remove(null, paramVetoableChangeListener);
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
/*     */   public VetoableChangeListener[] getVetoableChangeListeners() {
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
/*     */   public void addVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener) {
/* 198 */     if (paramVetoableChangeListener == null || paramString == null) {
/*     */       return;
/*     */     }
/* 201 */     paramVetoableChangeListener = this.map.extract(paramVetoableChangeListener);
/* 202 */     if (paramVetoableChangeListener != null) {
/* 203 */       this.map.add(paramString, paramVetoableChangeListener);
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
/*     */   public void removeVetoableChangeListener(String paramString, VetoableChangeListener paramVetoableChangeListener) {
/* 223 */     if (paramVetoableChangeListener == null || paramString == null) {
/*     */       return;
/*     */     }
/* 226 */     paramVetoableChangeListener = this.map.extract(paramVetoableChangeListener);
/* 227 */     if (paramVetoableChangeListener != null) {
/* 228 */       this.map.remove(paramString, paramVetoableChangeListener);
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
/*     */   public VetoableChangeListener[] getVetoableChangeListeners(String paramString) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireVetoableChange(String paramString, Object paramObject1, Object paramObject2) throws PropertyVetoException {
/* 270 */     if (paramObject1 == null || paramObject2 == null || !paramObject1.equals(paramObject2)) {
/* 271 */       fireVetoableChange(new PropertyChangeEvent(this.source, paramString, paramObject1, paramObject2));
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
/*     */   public void fireVetoableChange(String paramString, int paramInt1, int paramInt2) throws PropertyVetoException {
/* 298 */     if (paramInt1 != paramInt2) {
/* 299 */       fireVetoableChange(paramString, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
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
/*     */   public void fireVetoableChange(String paramString, boolean paramBoolean1, boolean paramBoolean2) throws PropertyVetoException {
/* 326 */     if (paramBoolean1 != paramBoolean2) {
/* 327 */       fireVetoableChange(paramString, Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2));
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
/*     */   public void fireVetoableChange(PropertyChangeEvent paramPropertyChangeEvent) throws PropertyVetoException {
/* 349 */     Object object1 = paramPropertyChangeEvent.getOldValue();
/* 350 */     Object object2 = paramPropertyChangeEvent.getNewValue();
/* 351 */     if (object1 == null || object2 == null || !object1.equals(object2)) {
/* 352 */       VetoableChangeListener[] arrayOfVetoableChangeListener3; String str = paramPropertyChangeEvent.getPropertyName();
/*     */       
/* 354 */       VetoableChangeListener[] arrayOfVetoableChangeListener1 = this.map.get(null);
/*     */       
/* 356 */       VetoableChangeListener[] arrayOfVetoableChangeListener2 = (str != null) ? this.map.get(str) : null;
/*     */ 
/*     */ 
/*     */       
/* 360 */       if (arrayOfVetoableChangeListener1 == null) {
/* 361 */         arrayOfVetoableChangeListener3 = arrayOfVetoableChangeListener2;
/*     */       }
/* 363 */       else if (arrayOfVetoableChangeListener2 == null) {
/* 364 */         arrayOfVetoableChangeListener3 = arrayOfVetoableChangeListener1;
/*     */       } else {
/*     */         
/* 367 */         arrayOfVetoableChangeListener3 = new VetoableChangeListener[arrayOfVetoableChangeListener1.length + arrayOfVetoableChangeListener2.length];
/* 368 */         System.arraycopy(arrayOfVetoableChangeListener1, 0, arrayOfVetoableChangeListener3, 0, arrayOfVetoableChangeListener1.length);
/* 369 */         System.arraycopy(arrayOfVetoableChangeListener2, 0, arrayOfVetoableChangeListener3, arrayOfVetoableChangeListener1.length, arrayOfVetoableChangeListener2.length);
/*     */       } 
/* 371 */       if (arrayOfVetoableChangeListener3 != null) {
/* 372 */         byte b = 0;
/*     */         try {
/* 374 */           while (b < arrayOfVetoableChangeListener3.length) {
/* 375 */             arrayOfVetoableChangeListener3[b].vetoableChange(paramPropertyChangeEvent);
/* 376 */             b++;
/*     */           }
/*     */         
/* 379 */         } catch (PropertyVetoException propertyVetoException) {
/* 380 */           paramPropertyChangeEvent = new PropertyChangeEvent(this.source, str, object2, object1);
/* 381 */           for (byte b1 = 0; b1 < b; b1++) {
/*     */             try {
/* 383 */               arrayOfVetoableChangeListener3[b1].vetoableChange(paramPropertyChangeEvent);
/*     */             }
/* 385 */             catch (PropertyVetoException propertyVetoException1) {}
/*     */           } 
/*     */ 
/*     */           
/* 389 */           throw propertyVetoException;
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
/*     */   public boolean hasListeners(String paramString) {
/* 404 */     return this.map.hasListeners(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 414 */     Hashtable<Object, Object> hashtable = null;
/* 415 */     VetoableChangeListener[] arrayOfVetoableChangeListener = null;
/* 416 */     synchronized (this.map) {
/* 417 */       for (Map.Entry<String, VetoableChangeListener[]> entry : this.map.getEntries()) {
/* 418 */         String str = (String)entry.getKey();
/* 419 */         if (str == null) {
/* 420 */           arrayOfVetoableChangeListener = (VetoableChangeListener[])entry.getValue(); continue;
/*     */         } 
/* 422 */         if (hashtable == null) {
/* 423 */           hashtable = new Hashtable<>();
/*     */         }
/* 425 */         VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this.source);
/* 426 */         vetoableChangeSupport.map.set(null, (VetoableChangeListener[])entry.getValue());
/* 427 */         hashtable.put(str, vetoableChangeSupport);
/*     */       } 
/*     */     } 
/*     */     
/* 431 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 432 */     putField.put("children", hashtable);
/* 433 */     putField.put("source", this.source);
/* 434 */     putField.put("vetoableChangeSupportSerializedDataVersion", 2);
/* 435 */     paramObjectOutputStream.writeFields();
/*     */     
/* 437 */     if (arrayOfVetoableChangeListener != null) {
/* 438 */       for (VetoableChangeListener vetoableChangeListener : arrayOfVetoableChangeListener) {
/* 439 */         if (vetoableChangeListener instanceof Serializable) {
/* 440 */           paramObjectOutputStream.writeObject(vetoableChangeListener);
/*     */         }
/*     */       } 
/*     */     }
/* 444 */     paramObjectOutputStream.writeObject(null);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 448 */     this.map = new VetoableChangeListenerMap();
/*     */     
/* 450 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */ 
/*     */     
/* 453 */     Hashtable hashtable = (Hashtable)getField.get("children", (Object)null);
/* 454 */     this.source = getField.get("source", (Object)null);
/* 455 */     getField.get("vetoableChangeSupportSerializedDataVersion", 2);
/*     */     
/*     */     Object object;
/* 458 */     while (null != (object = paramObjectInputStream.readObject())) {
/* 459 */       this.map.add(null, (VetoableChangeListener)object);
/*     */     }
/* 461 */     if (hashtable != null) {
/* 462 */       for (Map.Entry entry : hashtable.entrySet()) {
/* 463 */         for (VetoableChangeListener vetoableChangeListener : ((VetoableChangeSupport)entry.getValue()).getVetoableChangeListeners()) {
/* 464 */           this.map.add((String)entry.getKey(), vetoableChangeListener);
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
/* 480 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("children", Hashtable.class), new ObjectStreamField("source", Object.class), new ObjectStreamField("vetoableChangeSupportSerializedDataVersion", int.class) };
/*     */ 
/*     */ 
/*     */   
/*     */   static final long serialVersionUID = -5090210921595982017L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class VetoableChangeListenerMap
/*     */     extends ChangeListenerMap<VetoableChangeListener>
/*     */   {
/*     */     private VetoableChangeListenerMap() {}
/*     */ 
/*     */ 
/*     */     
/* 496 */     private static final VetoableChangeListener[] EMPTY = new VetoableChangeListener[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected VetoableChangeListener[] newArray(int param1Int) {
/* 508 */       return (0 < param1Int) ? new VetoableChangeListener[param1Int] : EMPTY;
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
/*     */     protected VetoableChangeListener newProxy(String param1String, VetoableChangeListener param1VetoableChangeListener) {
/* 523 */       return new VetoableChangeListenerProxy(param1String, param1VetoableChangeListener);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final VetoableChangeListener extract(VetoableChangeListener param1VetoableChangeListener) {
/* 530 */       while (param1VetoableChangeListener instanceof VetoableChangeListenerProxy) {
/* 531 */         param1VetoableChangeListener = ((VetoableChangeListenerProxy)param1VetoableChangeListener).getListener();
/*     */       }
/* 533 */       return param1VetoableChangeListener;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/VetoableChangeSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */