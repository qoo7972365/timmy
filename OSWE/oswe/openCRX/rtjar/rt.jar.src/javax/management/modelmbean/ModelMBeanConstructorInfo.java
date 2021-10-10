/*     */ package javax.management.modelmbean;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.AccessController;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.DescriptorAccess;
/*     */ import javax.management.MBeanConstructorInfo;
/*     */ import javax.management.MBeanParameterInfo;
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
/*     */ public class ModelMBeanConstructorInfo
/*     */   extends MBeanConstructorInfo
/*     */   implements DescriptorAccess
/*     */ {
/*     */   private static final long oldSerialVersionUID = -4440125391095574518L;
/*     */   private static final long newSerialVersionUID = 3862947819818064362L;
/* 106 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("consDescriptor", Descriptor.class), new ObjectStreamField("currClass", String.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("consDescriptor", Descriptor.class) };
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
/*     */   static {
/*     */     try {
/* 127 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/* 128 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/* 129 */       compat = (str != null && str.equals("1.0"));
/* 130 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 133 */     if (compat) {
/* 134 */       serialPersistentFields = oldSerialPersistentFields;
/* 135 */       serialVersionUID = -4440125391095574518L;
/*     */     } else {
/* 137 */       serialPersistentFields = newSerialPersistentFields;
/* 138 */       serialVersionUID = 3862947819818064362L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   private Descriptor consDescriptor = validDescriptor((Descriptor)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String currClass = "ModelMBeanConstructorInfo";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelMBeanConstructorInfo(String paramString, Constructor<?> paramConstructor) {
/* 166 */     super(paramString, paramConstructor);
/* 167 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 168 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanConstructorInfo.class
/* 169 */           .getName(), "ModelMBeanConstructorInfo(String,Constructor)", "Entry");
/*     */     }
/*     */ 
/*     */     
/* 173 */     this.consDescriptor = validDescriptor((Descriptor)null);
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
/*     */   public ModelMBeanConstructorInfo(String paramString, Constructor<?> paramConstructor, Descriptor paramDescriptor) {
/* 210 */     super(paramString, paramConstructor);
/*     */     
/* 212 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 213 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanConstructorInfo.class
/* 214 */           .getName(), "ModelMBeanConstructorInfo(String,Constructor,Descriptor)", "Entry");
/*     */     }
/*     */ 
/*     */     
/* 218 */     this.consDescriptor = validDescriptor(paramDescriptor);
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
/*     */   public ModelMBeanConstructorInfo(String paramString1, String paramString2, MBeanParameterInfo[] paramArrayOfMBeanParameterInfo) {
/* 233 */     super(paramString1, paramString2, paramArrayOfMBeanParameterInfo);
/*     */     
/* 235 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 236 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanConstructorInfo.class
/* 237 */           .getName(), "ModelMBeanConstructorInfo(String,String,MBeanParameterInfo[])", "Entry");
/*     */     }
/*     */ 
/*     */     
/* 241 */     this.consDescriptor = validDescriptor((Descriptor)null);
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
/*     */   public ModelMBeanConstructorInfo(String paramString1, String paramString2, MBeanParameterInfo[] paramArrayOfMBeanParameterInfo, Descriptor paramDescriptor) {
/* 267 */     super(paramString1, paramString2, paramArrayOfMBeanParameterInfo);
/* 268 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 269 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanConstructorInfo.class
/* 270 */           .getName(), "ModelMBeanConstructorInfo(String,String,MBeanParameterInfo[],Descriptor)", "Entry");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 275 */     this.consDescriptor = validDescriptor(paramDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ModelMBeanConstructorInfo(ModelMBeanConstructorInfo paramModelMBeanConstructorInfo) {
/* 286 */     super(paramModelMBeanConstructorInfo.getName(), paramModelMBeanConstructorInfo.getDescription(), paramModelMBeanConstructorInfo.getSignature());
/* 287 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 288 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanConstructorInfo.class
/* 289 */           .getName(), "ModelMBeanConstructorInfo(ModelMBeanConstructorInfo)", "Entry");
/*     */     }
/*     */ 
/*     */     
/* 293 */     this.consDescriptor = validDescriptor(this.consDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 303 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 304 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanConstructorInfo.class
/* 305 */           .getName(), "clone()", "Entry");
/*     */     }
/*     */     
/* 308 */     return new ModelMBeanConstructorInfo(this);
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
/*     */   public Descriptor getDescriptor() {
/* 324 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 325 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanConstructorInfo.class
/* 326 */           .getName(), "getDescriptor()", "Entry");
/*     */     }
/*     */     
/* 329 */     if (this.consDescriptor == null) {
/* 330 */       this.consDescriptor = validDescriptor((Descriptor)null);
/*     */     }
/* 332 */     return (Descriptor)this.consDescriptor.clone();
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
/*     */   public void setDescriptor(Descriptor paramDescriptor) {
/* 359 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 360 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanConstructorInfo.class
/* 361 */           .getName(), "setDescriptor()", "Entry");
/*     */     }
/*     */     
/* 364 */     this.consDescriptor = validDescriptor(paramDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 373 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 374 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanConstructorInfo.class
/* 375 */           .getName(), "toString()", "Entry");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 381 */     String str = "ModelMBeanConstructorInfo: " + getName() + " ; Description: " + getDescription() + " ; Descriptor: " + getDescriptor() + " ; Signature: ";
/*     */     
/* 383 */     MBeanParameterInfo[] arrayOfMBeanParameterInfo = getSignature();
/* 384 */     for (byte b = 0; b < arrayOfMBeanParameterInfo.length; b++)
/*     */     {
/* 386 */       str = str.concat(arrayOfMBeanParameterInfo[b].getType() + ", ");
/*     */     }
/* 388 */     return str;
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
/* 408 */     boolean bool = (paramDescriptor == null) ? true : false;
/* 409 */     if (bool) {
/* 410 */       descriptor = new DescriptorSupport();
/* 411 */       JmxProperties.MODELMBEAN_LOGGER.finer("Null Descriptor, creating new.");
/*     */     } else {
/* 413 */       descriptor = (Descriptor)paramDescriptor.clone();
/*     */     } 
/*     */ 
/*     */     
/* 417 */     if (bool && descriptor.getFieldValue("name") == null) {
/* 418 */       descriptor.setField("name", getName());
/* 419 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor name to " + getName());
/*     */     } 
/* 421 */     if (bool && descriptor.getFieldValue("descriptorType") == null) {
/* 422 */       descriptor.setField("descriptorType", "operation");
/* 423 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting descriptorType to \"operation\"");
/*     */     } 
/* 425 */     if (descriptor.getFieldValue("displayName") == null) {
/* 426 */       descriptor.setField("displayName", getName());
/* 427 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor displayName to " + getName());
/*     */     } 
/* 429 */     if (descriptor.getFieldValue("role") == null) {
/* 430 */       descriptor.setField("role", "constructor");
/* 431 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor role field to \"constructor\"");
/*     */     } 
/*     */ 
/*     */     
/* 435 */     if (!descriptor.isValid()) {
/* 436 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The isValid() method of the Descriptor object itself returned false,one or more required fields are invalid. Descriptor:" + descriptor
/*     */           
/* 438 */           .toString());
/*     */     }
/* 440 */     if (!getName().equalsIgnoreCase((String)descriptor.getFieldValue("name"))) {
/* 441 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"name\" field does not match the object described.  Expected: " + 
/*     */           
/* 443 */           getName() + " , was: " + descriptor.getFieldValue("name"));
/*     */     }
/* 445 */     if (!"operation".equalsIgnoreCase((String)descriptor.getFieldValue("descriptorType"))) {
/* 446 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"descriptorType\" field does not match the object described.  Expected: \"operation\" , was: " + descriptor
/*     */           
/* 448 */           .getFieldValue("descriptorType"));
/*     */     }
/* 450 */     if (!((String)descriptor.getFieldValue("role")).equalsIgnoreCase("constructor")) {
/* 451 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"role\" field does not match the object described.  Expected: \"constructor\" , was: " + descriptor
/*     */           
/* 453 */           .getFieldValue("role"));
/*     */     }
/*     */     
/* 456 */     return descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 465 */     paramObjectInputStream.defaultReadObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 474 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 478 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 479 */       putField.put("consDescriptor", this.consDescriptor);
/* 480 */       putField.put("currClass", "ModelMBeanConstructorInfo");
/* 481 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 487 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/modelmbean/ModelMBeanConstructorInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */