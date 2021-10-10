/*     */ package javax.management.modelmbean;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.DescriptorAccess;
/*     */ import javax.management.IntrospectionException;
/*     */ import javax.management.MBeanAttributeInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelMBeanAttributeInfo
/*     */   extends MBeanAttributeInfo
/*     */   implements DescriptorAccess
/*     */ {
/*     */   private static final long oldSerialVersionUID = 7098036920755973145L;
/*     */   private static final long newSerialVersionUID = 6181543027787327345L;
/* 132 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("attrDescriptor", Descriptor.class), new ObjectStreamField("currClass", String.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("attrDescriptor", Descriptor.class) };
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID;
/*     */ 
/*     */   
/*     */   private static final ObjectStreamField[] serialPersistentFields;
/*     */ 
/*     */   
/*     */   private static boolean compat = false;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 154 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/* 155 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/* 156 */       compat = (str != null && str.equals("1.0"));
/* 157 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 160 */     if (compat) {
/* 161 */       serialPersistentFields = oldSerialPersistentFields;
/* 162 */       serialVersionUID = 7098036920755973145L;
/*     */     } else {
/* 164 */       serialPersistentFields = newSerialPersistentFields;
/* 165 */       serialVersionUID = 6181543027787327345L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   private Descriptor attrDescriptor = validDescriptor((Descriptor)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String currClass = "ModelMBeanAttributeInfo";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelMBeanAttributeInfo(String paramString1, String paramString2, Method paramMethod1, Method paramMethod2) throws IntrospectionException {
/* 202 */     super(paramString1, paramString2, paramMethod1, paramMethod2);
/*     */     
/* 204 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 205 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanAttributeInfo.class
/* 206 */           .getName(), "ModelMBeanAttributeInfo(String,String,Method,Method)", "Entry", paramString1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     this.attrDescriptor = validDescriptor((Descriptor)null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelMBeanAttributeInfo(String paramString1, String paramString2, Method paramMethod1, Method paramMethod2, Descriptor paramDescriptor) throws IntrospectionException {
/* 252 */     super(paramString1, paramString2, paramMethod1, paramMethod2);
/*     */     
/* 254 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 255 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanAttributeInfo.class
/* 256 */           .getName(), "ModelMBeanAttributeInfo(String,String,Method,Method,Descriptor)", "Entry", paramString1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 261 */     this.attrDescriptor = validDescriptor(paramDescriptor);
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
/*     */   public ModelMBeanAttributeInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 283 */     super(paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2, paramBoolean3);
/*     */     
/* 285 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 286 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanAttributeInfo.class
/* 287 */           .getName(), "ModelMBeanAttributeInfo(String,String,String,boolean,boolean,boolean)", "Entry", paramString1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 292 */     this.attrDescriptor = validDescriptor((Descriptor)null);
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
/*     */   public ModelMBeanAttributeInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Descriptor paramDescriptor) {
/* 323 */     super(paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2, paramBoolean3);
/* 324 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 325 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanAttributeInfo.class
/* 326 */           .getName(), "ModelMBeanAttributeInfo(String,String,String,boolean,boolean,boolean,Descriptor)", "Entry", paramString1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 331 */     this.attrDescriptor = validDescriptor(paramDescriptor);
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
/*     */   public ModelMBeanAttributeInfo(ModelMBeanAttributeInfo paramModelMBeanAttributeInfo) {
/* 344 */     super(paramModelMBeanAttributeInfo.getName(), paramModelMBeanAttributeInfo
/* 345 */         .getType(), paramModelMBeanAttributeInfo
/* 346 */         .getDescription(), paramModelMBeanAttributeInfo
/* 347 */         .isReadable(), paramModelMBeanAttributeInfo
/* 348 */         .isWritable(), paramModelMBeanAttributeInfo
/* 349 */         .isIs());
/* 350 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 351 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanAttributeInfo.class
/* 352 */           .getName(), "ModelMBeanAttributeInfo(ModelMBeanAttributeInfo)", "Entry");
/*     */     }
/*     */ 
/*     */     
/* 356 */     Descriptor descriptor = paramModelMBeanAttributeInfo.getDescriptor();
/* 357 */     this.attrDescriptor = validDescriptor(descriptor);
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
/*     */   public Descriptor getDescriptor() {
/* 371 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 372 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanAttributeInfo.class
/* 373 */           .getName(), "getDescriptor()", "Entry");
/*     */     }
/*     */     
/* 376 */     if (this.attrDescriptor == null) {
/* 377 */       this.attrDescriptor = validDescriptor((Descriptor)null);
/*     */     }
/* 379 */     return (Descriptor)this.attrDescriptor.clone();
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
/* 400 */     this.attrDescriptor = validDescriptor(paramDescriptor);
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
/*     */   public Object clone() {
/* 414 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 415 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanAttributeInfo.class
/* 416 */           .getName(), "clone()", "Entry");
/*     */     }
/*     */     
/* 419 */     return new ModelMBeanAttributeInfo(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 429 */     return "ModelMBeanAttributeInfo: " + 
/* 430 */       getName() + " ; Description: " + 
/* 431 */       getDescription() + " ; Types: " + 
/* 432 */       getType() + " ; isReadable: " + 
/* 433 */       isReadable() + " ; isWritable: " + 
/* 434 */       isWritable() + " ; Descriptor: " + 
/* 435 */       getDescriptor();
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
/*     */   private Descriptor validDescriptor(Descriptor paramDescriptor) throws RuntimeOperationsException {
/*     */     Descriptor descriptor;
/* 454 */     boolean bool = (paramDescriptor == null) ? true : false;
/* 455 */     if (bool) {
/* 456 */       descriptor = new DescriptorSupport();
/* 457 */       JmxProperties.MODELMBEAN_LOGGER.finer("Null Descriptor, creating new.");
/*     */     } else {
/* 459 */       descriptor = (Descriptor)paramDescriptor.clone();
/*     */     } 
/*     */ 
/*     */     
/* 463 */     if (bool && descriptor.getFieldValue("name") == null) {
/* 464 */       descriptor.setField("name", getName());
/* 465 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor name to " + getName());
/*     */     } 
/* 467 */     if (bool && descriptor.getFieldValue("descriptorType") == null) {
/* 468 */       descriptor.setField("descriptorType", "attribute");
/* 469 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting descriptorType to \"attribute\"");
/*     */     } 
/* 471 */     if (descriptor.getFieldValue("displayName") == null) {
/* 472 */       descriptor.setField("displayName", getName());
/* 473 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor displayName to " + getName());
/*     */     } 
/*     */ 
/*     */     
/* 477 */     if (!descriptor.isValid()) {
/* 478 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The isValid() method of the Descriptor object itself returned false,one or more required fields are invalid. Descriptor:" + descriptor
/*     */           
/* 480 */           .toString());
/*     */     }
/* 482 */     if (!getName().equalsIgnoreCase((String)descriptor.getFieldValue("name"))) {
/* 483 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"name\" field does not match the object described.  Expected: " + 
/*     */           
/* 485 */           getName() + " , was: " + descriptor.getFieldValue("name"));
/*     */     }
/*     */     
/* 488 */     if (!"attribute".equalsIgnoreCase((String)descriptor.getFieldValue("descriptorType"))) {
/* 489 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"descriptorType\" field does not match the object described.  Expected: \"attribute\" , was: " + descriptor
/*     */           
/* 491 */           .getFieldValue("descriptorType"));
/*     */     }
/*     */     
/* 494 */     return descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 504 */     paramObjectInputStream.defaultReadObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 513 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 517 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 518 */       putField.put("attrDescriptor", this.attrDescriptor);
/* 519 */       putField.put("currClass", "ModelMBeanAttributeInfo");
/* 520 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 526 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/modelmbean/ModelMBeanAttributeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */