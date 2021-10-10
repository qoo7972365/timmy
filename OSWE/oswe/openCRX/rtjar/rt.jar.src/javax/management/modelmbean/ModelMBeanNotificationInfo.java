/*     */ package javax.management.modelmbean;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.security.AccessController;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.DescriptorAccess;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.RuntimeOperationsException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelMBeanNotificationInfo
/*     */   extends MBeanNotificationInfo
/*     */   implements DescriptorAccess
/*     */ {
/*     */   private static final long oldSerialVersionUID = -5211564525059047097L;
/*     */   private static final long newSerialVersionUID = -7445681389570207141L;
/* 113 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("notificationDescriptor", Descriptor.class), new ObjectStreamField("currClass", String.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("notificationDescriptor", Descriptor.class) };
/*     */   
/*     */   private static final long serialVersionUID;
/*     */   
/*     */   private static final ObjectStreamField[] serialPersistentFields;
/*     */   
/*     */   private static boolean compat = false;
/*     */   
/*     */   private Descriptor notificationDescriptor;
/*     */   
/*     */   private static final String currClass = "ModelMBeanNotificationInfo";
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 135 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/* 136 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/* 137 */       compat = (str != null && str.equals("1.0"));
/* 138 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 141 */     if (compat) {
/* 142 */       serialPersistentFields = oldSerialPersistentFields;
/* 143 */       serialVersionUID = -5211564525059047097L;
/*     */     } else {
/* 145 */       serialPersistentFields = newSerialPersistentFields;
/* 146 */       serialVersionUID = -7445681389570207141L;
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
/*     */   public ModelMBeanNotificationInfo(String[] paramArrayOfString, String paramString1, String paramString2) {
/* 173 */     this(paramArrayOfString, paramString1, paramString2, (Descriptor)null);
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
/*     */   public ModelMBeanNotificationInfo(String[] paramArrayOfString, String paramString1, String paramString2, Descriptor paramDescriptor) {
/* 201 */     super(paramArrayOfString, paramString1, paramString2);
/* 202 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 203 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanNotificationInfo.class
/* 204 */           .getName(), "ModelMBeanNotificationInfo", "Entry");
/*     */     }
/*     */     
/* 207 */     this.notificationDescriptor = validDescriptor(paramDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelMBeanNotificationInfo(ModelMBeanNotificationInfo paramModelMBeanNotificationInfo) {
/* 218 */     this(paramModelMBeanNotificationInfo.getNotifTypes(), paramModelMBeanNotificationInfo
/* 219 */         .getName(), paramModelMBeanNotificationInfo
/* 220 */         .getDescription(), paramModelMBeanNotificationInfo.getDescriptor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 228 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 229 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanNotificationInfo.class
/* 230 */           .getName(), "clone()", "Entry");
/*     */     }
/*     */     
/* 233 */     return new ModelMBeanNotificationInfo(this);
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
/*     */   public Descriptor getDescriptor() {
/* 246 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 247 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanNotificationInfo.class
/* 248 */           .getName(), "getDescriptor()", "Entry");
/*     */     }
/*     */ 
/*     */     
/* 252 */     if (this.notificationDescriptor == null) {
/*     */       
/* 254 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 255 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanNotificationInfo.class
/* 256 */             .getName(), "getDescriptor()", "Descriptor value is null, setting descriptor to default values");
/*     */       }
/*     */ 
/*     */       
/* 260 */       this.notificationDescriptor = validDescriptor((Descriptor)null);
/*     */     } 
/*     */     
/* 263 */     return (Descriptor)this.notificationDescriptor.clone();
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
/*     */   public void setDescriptor(Descriptor paramDescriptor) {
/* 284 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 285 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanNotificationInfo.class
/* 286 */           .getName(), "setDescriptor(Descriptor)", "Entry");
/*     */     }
/*     */     
/* 289 */     this.notificationDescriptor = validDescriptor(paramDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 299 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 300 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanNotificationInfo.class
/* 301 */           .getName(), "toString()", "Entry");
/*     */     }
/*     */ 
/*     */     
/* 305 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 307 */     stringBuilder.append("ModelMBeanNotificationInfo: ")
/* 308 */       .append(getName());
/*     */     
/* 310 */     stringBuilder.append(" ; Description: ")
/* 311 */       .append(getDescription());
/*     */     
/* 313 */     stringBuilder.append(" ; Descriptor: ")
/* 314 */       .append(getDescriptor());
/*     */     
/* 316 */     stringBuilder.append(" ; Types: ");
/* 317 */     String[] arrayOfString = getNotifTypes();
/* 318 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 319 */       if (b > 0) stringBuilder.append(", "); 
/* 320 */       stringBuilder.append(arrayOfString[b]);
/*     */     } 
/* 322 */     return stringBuilder.toString();
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
/*     */   private Descriptor validDescriptor(Descriptor paramDescriptor) throws RuntimeOperationsException {
/*     */     Descriptor descriptor;
/* 342 */     boolean bool = (paramDescriptor == null) ? true : false;
/* 343 */     if (bool) {
/* 344 */       descriptor = new DescriptorSupport();
/* 345 */       JmxProperties.MODELMBEAN_LOGGER.finer("Null Descriptor, creating new.");
/*     */     } else {
/* 347 */       descriptor = (Descriptor)paramDescriptor.clone();
/*     */     } 
/*     */ 
/*     */     
/* 351 */     if (bool && descriptor.getFieldValue("name") == null) {
/* 352 */       descriptor.setField("name", getName());
/* 353 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor name to " + getName());
/*     */     } 
/* 355 */     if (bool && descriptor.getFieldValue("descriptorType") == null) {
/* 356 */       descriptor.setField("descriptorType", "notification");
/* 357 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting descriptorType to \"notification\"");
/*     */     } 
/* 359 */     if (descriptor.getFieldValue("displayName") == null) {
/* 360 */       descriptor.setField("displayName", getName());
/* 361 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor displayName to " + getName());
/*     */     } 
/* 363 */     if (descriptor.getFieldValue("severity") == null) {
/* 364 */       descriptor.setField("severity", "6");
/* 365 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor severity field to 6");
/*     */     } 
/*     */ 
/*     */     
/* 369 */     if (!descriptor.isValid()) {
/* 370 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The isValid() method of the Descriptor object itself returned false,one or more required fields are invalid. Descriptor:" + descriptor
/*     */           
/* 372 */           .toString());
/*     */     }
/* 374 */     if (!getName().equalsIgnoreCase((String)descriptor.getFieldValue("name"))) {
/* 375 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"name\" field does not match the object described.  Expected: " + 
/*     */           
/* 377 */           getName() + " , was: " + descriptor.getFieldValue("name"));
/*     */     }
/* 379 */     if (!"notification".equalsIgnoreCase((String)descriptor.getFieldValue("descriptorType"))) {
/* 380 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"descriptorType\" field does not match the object described.  Expected: \"notification\" , was: " + descriptor
/*     */           
/* 382 */           .getFieldValue("descriptorType"));
/*     */     }
/*     */     
/* 385 */     return descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 396 */     paramObjectInputStream.defaultReadObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 406 */     if (compat) {
/*     */ 
/*     */       
/* 409 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 410 */       putField.put("notificationDescriptor", this.notificationDescriptor);
/* 411 */       putField.put("currClass", "ModelMBeanNotificationInfo");
/* 412 */       paramObjectOutputStream.writeFields();
/*     */     }
/*     */     else {
/*     */       
/* 416 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/modelmbean/ModelMBeanNotificationInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */