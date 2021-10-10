/*     */ package javax.swing.event;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.EventListener;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventListenerList
/*     */   implements Serializable
/*     */ {
/* 103 */   private static final Object[] NULL_ARRAY = new Object[0];
/*     */   
/* 105 */   protected transient Object[] listenerList = NULL_ARRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getListenerList() {
/* 125 */     return this.listenerList;
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
/*     */   public <T extends EventListener> T[] getListeners(Class<T> paramClass) {
/* 137 */     Object[] arrayOfObject = this.listenerList;
/* 138 */     int i = getListenerCount(arrayOfObject, paramClass);
/* 139 */     EventListener[] arrayOfEventListener = (EventListener[])Array.newInstance(paramClass, i);
/* 140 */     byte b = 0;
/* 141 */     for (int j = arrayOfObject.length - 2; j >= 0; j -= 2) {
/* 142 */       if (arrayOfObject[j] == paramClass) {
/* 143 */         arrayOfEventListener[b++] = (EventListener)arrayOfObject[j + 1];
/*     */       }
/*     */     } 
/* 146 */     return (T[])arrayOfEventListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListenerCount() {
/* 153 */     return this.listenerList.length / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListenerCount(Class<?> paramClass) {
/* 161 */     Object[] arrayOfObject = this.listenerList;
/* 162 */     return getListenerCount(arrayOfObject, paramClass);
/*     */   }
/*     */   
/*     */   private int getListenerCount(Object[] paramArrayOfObject, Class paramClass) {
/* 166 */     byte b1 = 0;
/* 167 */     for (byte b2 = 0; b2 < paramArrayOfObject.length; b2 += 2) {
/* 168 */       if (paramClass == (Class)paramArrayOfObject[b2])
/* 169 */         b1++; 
/*     */     } 
/* 171 */     return b1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized <T extends EventListener> void add(Class<T> paramClass, T paramT) {
/* 180 */     if (paramT == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 186 */     if (!paramClass.isInstance(paramT)) {
/* 187 */       throw new IllegalArgumentException("Listener " + paramT + " is not of type " + paramClass);
/*     */     }
/*     */     
/* 190 */     if (this.listenerList == NULL_ARRAY) {
/*     */ 
/*     */       
/* 193 */       this.listenerList = new Object[] { paramClass, paramT };
/*     */     } else {
/*     */       
/* 196 */       int i = this.listenerList.length;
/* 197 */       Object[] arrayOfObject = new Object[i + 2];
/* 198 */       System.arraycopy(this.listenerList, 0, arrayOfObject, 0, i);
/*     */       
/* 200 */       arrayOfObject[i] = paramClass;
/* 201 */       arrayOfObject[i + 1] = paramT;
/*     */       
/* 203 */       this.listenerList = arrayOfObject;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized <T extends EventListener> void remove(Class<T> paramClass, T paramT) {
/* 213 */     if (paramT == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 219 */     if (!paramClass.isInstance(paramT)) {
/* 220 */       throw new IllegalArgumentException("Listener " + paramT + " is not of type " + paramClass);
/*     */     }
/*     */ 
/*     */     
/* 224 */     int i = -1;
/* 225 */     for (int j = this.listenerList.length - 2; j >= 0; j -= 2) {
/* 226 */       if (this.listenerList[j] == paramClass && this.listenerList[j + 1].equals(paramT) == true) {
/* 227 */         i = j;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 233 */     if (i != -1) {
/* 234 */       Object[] arrayOfObject = new Object[this.listenerList.length - 2];
/*     */       
/* 236 */       System.arraycopy(this.listenerList, 0, arrayOfObject, 0, i);
/*     */ 
/*     */ 
/*     */       
/* 240 */       if (i < arrayOfObject.length) {
/* 241 */         System.arraycopy(this.listenerList, i + 2, arrayOfObject, i, arrayOfObject.length - i);
/*     */       }
/*     */       
/* 244 */       this.listenerList = (arrayOfObject.length == 0) ? NULL_ARRAY : arrayOfObject;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 250 */     Object[] arrayOfObject = this.listenerList;
/* 251 */     paramObjectOutputStream.defaultWriteObject();
/*     */ 
/*     */     
/* 254 */     for (byte b = 0; b < arrayOfObject.length; b += 2) {
/* 255 */       Class clazz = (Class)arrayOfObject[b];
/* 256 */       EventListener eventListener = (EventListener)arrayOfObject[b + 1];
/* 257 */       if (eventListener != null && eventListener instanceof Serializable) {
/* 258 */         paramObjectOutputStream.writeObject(clazz.getName());
/* 259 */         paramObjectOutputStream.writeObject(eventListener);
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     paramObjectOutputStream.writeObject(null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 268 */     this.listenerList = NULL_ARRAY;
/* 269 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     Object object;
/* 272 */     while (null != (object = paramObjectInputStream.readObject())) {
/* 273 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 274 */       EventListener eventListener = (EventListener)paramObjectInputStream.readObject();
/* 275 */       String str = (String)object;
/* 276 */       ReflectUtil.checkPackageAccess(str);
/* 277 */       add(Class.forName(str, true, classLoader), eventListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 285 */     Object[] arrayOfObject = this.listenerList;
/* 286 */     String str = "EventListenerList: ";
/* 287 */     str = str + (arrayOfObject.length / 2) + " listeners: ";
/* 288 */     for (byte b = 0; b <= arrayOfObject.length - 2; b += 2) {
/* 289 */       str = str + " type " + ((Class)arrayOfObject[b]).getName();
/* 290 */       str = str + " listener " + arrayOfObject[b + 1];
/*     */     } 
/* 292 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/EventListenerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */