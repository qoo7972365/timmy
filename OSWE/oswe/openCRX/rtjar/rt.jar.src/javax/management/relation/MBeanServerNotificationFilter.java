/*     */ package javax.management.relation;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.security.AccessController;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.MBeanServerNotification;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationFilterSupport;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanServerNotificationFilter
/*     */   extends NotificationFilterSupport
/*     */ {
/*     */   private static final long oldSerialVersionUID = 6001782699077323605L;
/*     */   private static final long newSerialVersionUID = 2605900539589789736L;
/*  78 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("mySelectObjNameList", Vector.class), new ObjectStreamField("myDeselectObjNameList", Vector.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("selectedNames", List.class), new ObjectStreamField("deselectedNames", List.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final ObjectStreamField[] serialPersistentFields;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean compat = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 111 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/* 112 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/* 113 */       compat = (str != null && str.equals("1.0"));
/* 114 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 117 */     if (compat) {
/* 118 */       serialPersistentFields = oldSerialPersistentFields;
/* 119 */       serialVersionUID = 6001782699077323605L;
/*     */     } else {
/* 121 */       serialPersistentFields = newSerialPersistentFields;
/* 122 */       serialVersionUID = 2605900539589789736L;
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
/* 140 */   private List<ObjectName> selectedNames = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   private List<ObjectName> deselectedNames = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanServerNotificationFilter() {
/* 163 */     JmxProperties.RELATION_LOGGER.entering(MBeanServerNotificationFilter.class.getName(), "MBeanServerNotificationFilter");
/*     */ 
/*     */     
/* 166 */     enableType("JMX.mbean.registered");
/* 167 */     enableType("JMX.mbean.unregistered");
/*     */     
/* 169 */     JmxProperties.RELATION_LOGGER.exiting(MBeanServerNotificationFilter.class.getName(), "MBeanServerNotificationFilter");
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
/*     */   public synchronized void disableAllObjectNames() {
/* 184 */     JmxProperties.RELATION_LOGGER.entering(MBeanServerNotificationFilter.class.getName(), "disableAllObjectNames");
/*     */ 
/*     */     
/* 187 */     this.selectedNames = new Vector<>();
/* 188 */     this.deselectedNames = null;
/*     */     
/* 190 */     JmxProperties.RELATION_LOGGER.exiting(MBeanServerNotificationFilter.class.getName(), "disableAllObjectNames");
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
/*     */   public synchronized void disableObjectName(ObjectName paramObjectName) throws IllegalArgumentException {
/* 205 */     if (paramObjectName == null) {
/* 206 */       String str = "Invalid parameter.";
/* 207 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 210 */     JmxProperties.RELATION_LOGGER.entering(MBeanServerNotificationFilter.class.getName(), "disableObjectName", paramObjectName);
/*     */ 
/*     */ 
/*     */     
/* 214 */     if (this.selectedNames != null && 
/* 215 */       this.selectedNames.size() != 0) {
/* 216 */       this.selectedNames.remove(paramObjectName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 221 */     if (this.deselectedNames != null)
/*     */     {
/* 223 */       if (!this.deselectedNames.contains(paramObjectName))
/*     */       {
/* 225 */         this.deselectedNames.add(paramObjectName);
/*     */       }
/*     */     }
/*     */     
/* 229 */     JmxProperties.RELATION_LOGGER.exiting(MBeanServerNotificationFilter.class.getName(), "disableObjectName");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void enableAllObjectNames() {
/* 239 */     JmxProperties.RELATION_LOGGER.entering(MBeanServerNotificationFilter.class.getName(), "enableAllObjectNames");
/*     */ 
/*     */     
/* 242 */     this.selectedNames = null;
/* 243 */     this.deselectedNames = new Vector<>();
/*     */     
/* 245 */     JmxProperties.RELATION_LOGGER.exiting(MBeanServerNotificationFilter.class.getName(), "enableAllObjectNames");
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
/*     */   public synchronized void enableObjectName(ObjectName paramObjectName) throws IllegalArgumentException {
/* 260 */     if (paramObjectName == null) {
/* 261 */       String str = "Invalid parameter.";
/* 262 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */     
/* 265 */     JmxProperties.RELATION_LOGGER.entering(MBeanServerNotificationFilter.class.getName(), "enableObjectName", paramObjectName);
/*     */ 
/*     */ 
/*     */     
/* 269 */     if (this.deselectedNames != null && 
/* 270 */       this.deselectedNames.size() != 0) {
/* 271 */       this.deselectedNames.remove(paramObjectName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 276 */     if (this.selectedNames != null)
/*     */     {
/* 278 */       if (!this.selectedNames.contains(paramObjectName))
/*     */       {
/* 280 */         this.selectedNames.add(paramObjectName);
/*     */       }
/*     */     }
/*     */     
/* 284 */     JmxProperties.RELATION_LOGGER.exiting(MBeanServerNotificationFilter.class.getName(), "enableObjectName");
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
/*     */   public synchronized Vector<ObjectName> getEnabledObjectNames() {
/* 299 */     if (this.selectedNames != null) {
/* 300 */       return new Vector<>(this.selectedNames);
/*     */     }
/* 302 */     return null;
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
/*     */   public synchronized Vector<ObjectName> getDisabledObjectNames() {
/* 316 */     if (this.deselectedNames != null) {
/* 317 */       return new Vector<>(this.deselectedNames);
/*     */     }
/* 319 */     return null;
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
/*     */   public synchronized boolean isNotificationEnabled(Notification paramNotification) throws IllegalArgumentException {
/* 347 */     if (paramNotification == null) {
/* 348 */       String str1 = "Invalid parameter.";
/* 349 */       throw new IllegalArgumentException(str1);
/*     */     } 
/*     */     
/* 352 */     JmxProperties.RELATION_LOGGER.entering(MBeanServerNotificationFilter.class.getName(), "isNotificationEnabled", paramNotification);
/*     */ 
/*     */ 
/*     */     
/* 356 */     String str = paramNotification.getType();
/* 357 */     Vector<String> vector = getEnabledTypes();
/* 358 */     if (!vector.contains(str)) {
/* 359 */       JmxProperties.RELATION_LOGGER.logp(Level.FINER, MBeanServerNotificationFilter.class
/* 360 */           .getName(), "isNotificationEnabled", "Type not selected, exiting");
/*     */ 
/*     */       
/* 363 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 367 */     MBeanServerNotification mBeanServerNotification = (MBeanServerNotification)paramNotification;
/*     */ 
/*     */     
/* 370 */     ObjectName objectName = mBeanServerNotification.getMBeanName();
/*     */     
/* 372 */     boolean bool = false;
/* 373 */     if (this.selectedNames != null) {
/*     */ 
/*     */       
/* 376 */       if (this.selectedNames.size() == 0) {
/*     */         
/* 378 */         JmxProperties.RELATION_LOGGER.logp(Level.FINER, MBeanServerNotificationFilter.class
/* 379 */             .getName(), "isNotificationEnabled", "No ObjectNames selected, exiting");
/*     */ 
/*     */         
/* 382 */         return false;
/*     */       } 
/*     */       
/* 385 */       bool = this.selectedNames.contains(objectName);
/* 386 */       if (!bool) {
/*     */         
/* 388 */         JmxProperties.RELATION_LOGGER.logp(Level.FINER, MBeanServerNotificationFilter.class
/* 389 */             .getName(), "isNotificationEnabled", "ObjectName not in selected list, exiting");
/*     */ 
/*     */         
/* 392 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 396 */     if (!bool) {
/*     */ 
/*     */       
/* 399 */       if (this.deselectedNames == null) {
/*     */ 
/*     */         
/* 402 */         JmxProperties.RELATION_LOGGER.logp(Level.FINER, MBeanServerNotificationFilter.class
/* 403 */             .getName(), "isNotificationEnabled", "ObjectName not selected, and all names deselected, exiting");
/*     */ 
/*     */ 
/*     */         
/* 407 */         return false;
/*     */       } 
/* 409 */       if (this.deselectedNames.contains(objectName)) {
/*     */         
/* 411 */         JmxProperties.RELATION_LOGGER.logp(Level.FINER, MBeanServerNotificationFilter.class
/* 412 */             .getName(), "isNotificationEnabled", "ObjectName explicitly not selected, exiting");
/*     */ 
/*     */         
/* 415 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 419 */     JmxProperties.RELATION_LOGGER.logp(Level.FINER, MBeanServerNotificationFilter.class
/* 420 */         .getName(), "isNotificationEnabled", "ObjectName selected, exiting");
/*     */ 
/*     */     
/* 423 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 432 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 436 */       ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 437 */       this.selectedNames = Util.<List<ObjectName>>cast(getField.get("mySelectObjNameList", (Object)null));
/* 438 */       if (getField.defaulted("mySelectObjNameList"))
/*     */       {
/* 440 */         throw new NullPointerException("mySelectObjNameList");
/*     */       }
/* 442 */       this.deselectedNames = Util.<List<ObjectName>>cast(getField.get("myDeselectObjNameList", (Object)null));
/* 443 */       if (getField.defaulted("myDeselectObjNameList"))
/*     */       {
/* 445 */         throw new NullPointerException("myDeselectObjNameList");
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 452 */       paramObjectInputStream.defaultReadObject();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 462 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 466 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 467 */       putField.put("mySelectObjNameList", this.selectedNames);
/* 468 */       putField.put("myDeselectObjNameList", this.deselectedNames);
/* 469 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 475 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/MBeanServerNotificationFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */