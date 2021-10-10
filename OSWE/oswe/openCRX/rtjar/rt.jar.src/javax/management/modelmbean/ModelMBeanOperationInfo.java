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
/*     */ import javax.management.MBeanOperationInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelMBeanOperationInfo
/*     */   extends MBeanOperationInfo
/*     */   implements DescriptorAccess
/*     */ {
/*     */   private static final long oldSerialVersionUID = 9087646304346171239L;
/*     */   private static final long newSerialVersionUID = 6532732096650090465L;
/* 125 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("operationDescriptor", Descriptor.class), new ObjectStreamField("currClass", String.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("operationDescriptor", Descriptor.class) };
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
/* 147 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/* 148 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/* 149 */       compat = (str != null && str.equals("1.0"));
/* 150 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 153 */     if (compat) {
/* 154 */       serialPersistentFields = oldSerialPersistentFields;
/* 155 */       serialVersionUID = 9087646304346171239L;
/*     */     } else {
/* 157 */       serialPersistentFields = newSerialPersistentFields;
/* 158 */       serialVersionUID = 6532732096650090465L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   private Descriptor operationDescriptor = validDescriptor((Descriptor)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String currClass = "ModelMBeanOperationInfo";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelMBeanOperationInfo(String paramString, Method paramMethod) {
/* 186 */     super(paramString, paramMethod);
/*     */     
/* 188 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 189 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanOperationInfo.class
/* 190 */           .getName(), "ModelMBeanOperationInfo(String,Method)", "Entry");
/*     */     }
/*     */ 
/*     */     
/* 194 */     this.operationDescriptor = validDescriptor((Descriptor)null);
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
/*     */   public ModelMBeanOperationInfo(String paramString, Method paramMethod, Descriptor paramDescriptor) {
/* 230 */     super(paramString, paramMethod);
/* 231 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 232 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanOperationInfo.class
/* 233 */           .getName(), "ModelMBeanOperationInfo(String,Method,Descriptor)", "Entry");
/*     */     }
/*     */ 
/*     */     
/* 237 */     this.operationDescriptor = validDescriptor(paramDescriptor);
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
/*     */   public ModelMBeanOperationInfo(String paramString1, String paramString2, MBeanParameterInfo[] paramArrayOfMBeanParameterInfo, String paramString3, int paramInt) {
/* 259 */     super(paramString1, paramString2, paramArrayOfMBeanParameterInfo, paramString3, paramInt);
/*     */     
/* 261 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 262 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanOperationInfo.class
/* 263 */           .getName(), "ModelMBeanOperationInfo(String,String,MBeanParameterInfo[],String,int)", "Entry");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 268 */     this.operationDescriptor = validDescriptor((Descriptor)null);
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
/*     */   public ModelMBeanOperationInfo(String paramString1, String paramString2, MBeanParameterInfo[] paramArrayOfMBeanParameterInfo, String paramString3, int paramInt, Descriptor paramDescriptor) {
/* 304 */     super(paramString1, paramString2, paramArrayOfMBeanParameterInfo, paramString3, paramInt);
/* 305 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 306 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanOperationInfo.class
/* 307 */           .getName(), "ModelMBeanOperationInfo(String,String,MBeanParameterInfo[],String,int,Descriptor)", "Entry");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 312 */     this.operationDescriptor = validDescriptor(paramDescriptor);
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
/*     */   public ModelMBeanOperationInfo(ModelMBeanOperationInfo paramModelMBeanOperationInfo) {
/* 325 */     super(paramModelMBeanOperationInfo.getName(), paramModelMBeanOperationInfo
/* 326 */         .getDescription(), paramModelMBeanOperationInfo
/* 327 */         .getSignature(), paramModelMBeanOperationInfo
/* 328 */         .getReturnType(), paramModelMBeanOperationInfo
/* 329 */         .getImpact());
/* 330 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 331 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanOperationInfo.class
/* 332 */           .getName(), "ModelMBeanOperationInfo(ModelMBeanOperationInfo)", "Entry");
/*     */     }
/*     */ 
/*     */     
/* 336 */     Descriptor descriptor = paramModelMBeanOperationInfo.getDescriptor();
/* 337 */     this.operationDescriptor = validDescriptor(descriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 348 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 349 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanOperationInfo.class
/* 350 */           .getName(), "clone()", "Entry");
/*     */     }
/*     */     
/* 353 */     return new ModelMBeanOperationInfo(this);
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
/*     */   public Descriptor getDescriptor() {
/* 368 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 369 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanOperationInfo.class
/* 370 */           .getName(), "getDescriptor()", "Entry");
/*     */     }
/*     */     
/* 373 */     if (this.operationDescriptor == null) {
/* 374 */       this.operationDescriptor = validDescriptor((Descriptor)null);
/*     */     }
/*     */     
/* 377 */     return (Descriptor)this.operationDescriptor.clone();
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
/*     */   public void setDescriptor(Descriptor paramDescriptor) {
/* 399 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 400 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanOperationInfo.class
/* 401 */           .getName(), "setDescriptor(Descriptor)", "Entry");
/*     */     }
/*     */     
/* 404 */     this.operationDescriptor = validDescriptor(paramDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 413 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 414 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanOperationInfo.class
/* 415 */           .getName(), "toString()", "Entry");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 422 */     String str = "ModelMBeanOperationInfo: " + getName() + " ; Description: " + getDescription() + " ; Descriptor: " + getDescriptor() + " ; ReturnType: " + getReturnType() + " ; Signature: ";
/*     */     
/* 424 */     MBeanParameterInfo[] arrayOfMBeanParameterInfo = getSignature();
/* 425 */     for (byte b = 0; b < arrayOfMBeanParameterInfo.length; b++)
/*     */     {
/* 427 */       str = str.concat(arrayOfMBeanParameterInfo[b].getType() + ", ");
/*     */     }
/* 429 */     return str;
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
/* 449 */     boolean bool = (paramDescriptor == null) ? true : false;
/* 450 */     if (bool) {
/* 451 */       descriptor = new DescriptorSupport();
/* 452 */       JmxProperties.MODELMBEAN_LOGGER.finer("Null Descriptor, creating new.");
/*     */     } else {
/* 454 */       descriptor = (Descriptor)paramDescriptor.clone();
/*     */     } 
/*     */ 
/*     */     
/* 458 */     if (bool && descriptor.getFieldValue("name") == null) {
/* 459 */       descriptor.setField("name", getName());
/* 460 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor name to " + getName());
/*     */     } 
/* 462 */     if (bool && descriptor.getFieldValue("descriptorType") == null) {
/* 463 */       descriptor.setField("descriptorType", "operation");
/* 464 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting descriptorType to \"operation\"");
/*     */     } 
/* 466 */     if (descriptor.getFieldValue("displayName") == null) {
/* 467 */       descriptor.setField("displayName", getName());
/* 468 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor displayName to " + getName());
/*     */     } 
/* 470 */     if (descriptor.getFieldValue("role") == null) {
/* 471 */       descriptor.setField("role", "operation");
/* 472 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor role field to \"operation\"");
/*     */     } 
/*     */ 
/*     */     
/* 476 */     if (!descriptor.isValid()) {
/* 477 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The isValid() method of the Descriptor object itself returned false,one or more required fields are invalid. Descriptor:" + descriptor
/*     */           
/* 479 */           .toString());
/*     */     }
/* 481 */     if (!getName().equalsIgnoreCase((String)descriptor.getFieldValue("name"))) {
/* 482 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"name\" field does not match the object described.  Expected: " + 
/*     */           
/* 484 */           getName() + " , was: " + descriptor.getFieldValue("name"));
/*     */     }
/* 486 */     if (!"operation".equalsIgnoreCase((String)descriptor.getFieldValue("descriptorType"))) {
/* 487 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"descriptorType\" field does not match the object described.  Expected: \"operation\" , was: " + descriptor
/*     */           
/* 489 */           .getFieldValue("descriptorType"));
/*     */     }
/* 491 */     String str = (String)descriptor.getFieldValue("role");
/* 492 */     if (!str.equalsIgnoreCase("operation") && 
/* 493 */       !str.equalsIgnoreCase("setter") && 
/* 494 */       !str.equalsIgnoreCase("getter")) {
/* 495 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"role\" field does not match the object described.  Expected: \"operation\", \"setter\", or \"getter\" , was: " + descriptor
/*     */           
/* 497 */           .getFieldValue("role"));
/*     */     }
/* 499 */     Object object = descriptor.getFieldValue("targetType");
/* 500 */     if (object != null && 
/* 501 */       !(object instanceof String)) {
/* 502 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor field \"targetValue\" is invalid class.  Expected: java.lang.String,  was: " + object
/*     */           
/* 504 */           .getClass().getName());
/*     */     }
/*     */     
/* 507 */     return descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 516 */     paramObjectInputStream.defaultReadObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 525 */     if (compat) {
/*     */ 
/*     */ 
/*     */       
/* 529 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 530 */       putField.put("operationDescriptor", this.operationDescriptor);
/* 531 */       putField.put("currClass", "ModelMBeanOperationInfo");
/* 532 */       paramObjectOutputStream.writeFields();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 538 */       paramObjectOutputStream.defaultWriteObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/modelmbean/ModelMBeanOperationInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */