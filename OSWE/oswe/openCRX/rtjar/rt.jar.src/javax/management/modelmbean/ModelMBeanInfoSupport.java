/*      */ package javax.management.modelmbean;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.security.AccessController;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.Descriptor;
/*      */ import javax.management.MBeanAttributeInfo;
/*      */ import javax.management.MBeanConstructorInfo;
/*      */ import javax.management.MBeanException;
/*      */ import javax.management.MBeanInfo;
/*      */ import javax.management.MBeanNotificationInfo;
/*      */ import javax.management.MBeanOperationInfo;
/*      */ import javax.management.RuntimeOperationsException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ModelMBeanInfoSupport
/*      */   extends MBeanInfo
/*      */   implements ModelMBeanInfo
/*      */ {
/*      */   private static final long oldSerialVersionUID = -3944083498453227709L;
/*      */   private static final long newSerialVersionUID = -1935722590756516193L;
/*  100 */   private static final ObjectStreamField[] oldSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("modelMBeanDescriptor", Descriptor.class), new ObjectStreamField("mmbAttributes", MBeanAttributeInfo[].class), new ObjectStreamField("mmbConstructors", MBeanConstructorInfo[].class), new ObjectStreamField("mmbNotifications", MBeanNotificationInfo[].class), new ObjectStreamField("mmbOperations", MBeanOperationInfo[].class), new ObjectStreamField("currClass", String.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   private static final ObjectStreamField[] newSerialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("modelMBeanDescriptor", Descriptor.class), new ObjectStreamField("modelMBeanAttributes", MBeanAttributeInfo[].class), new ObjectStreamField("modelMBeanConstructors", MBeanConstructorInfo[].class), new ObjectStreamField("modelMBeanNotifications", MBeanNotificationInfo[].class), new ObjectStreamField("modelMBeanOperations", MBeanOperationInfo[].class) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final ObjectStreamField[] serialPersistentFields;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean compat = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/*  142 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/*  143 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  144 */       compat = (str != null && str.equals("1.0"));
/*  145 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  148 */     if (compat) {
/*  149 */       serialPersistentFields = oldSerialPersistentFields;
/*  150 */       serialVersionUID = -3944083498453227709L;
/*      */     } else {
/*  152 */       serialPersistentFields = newSerialPersistentFields;
/*  153 */       serialVersionUID = -1935722590756516193L;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  162 */   private Descriptor modelMBeanDescriptor = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MBeanAttributeInfo[] modelMBeanAttributes;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MBeanConstructorInfo[] modelMBeanConstructors;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MBeanNotificationInfo[] modelMBeanNotifications;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MBeanOperationInfo[] modelMBeanOperations;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String ATTR = "attribute";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String OPER = "operation";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String NOTF = "notification";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String CONS = "constructor";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String MMB = "mbean";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String ALL = "all";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String currClass = "ModelMBeanInfoSupport";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ModelMBeanInfoSupport(ModelMBeanInfo paramModelMBeanInfo) {
/*  217 */     super(paramModelMBeanInfo.getClassName(), paramModelMBeanInfo
/*  218 */         .getDescription(), paramModelMBeanInfo
/*  219 */         .getAttributes(), paramModelMBeanInfo
/*  220 */         .getConstructors(), paramModelMBeanInfo
/*  221 */         .getOperations(), paramModelMBeanInfo
/*  222 */         .getNotifications());
/*      */     
/*  224 */     this.modelMBeanAttributes = paramModelMBeanInfo.getAttributes();
/*  225 */     this.modelMBeanConstructors = paramModelMBeanInfo.getConstructors();
/*  226 */     this.modelMBeanOperations = paramModelMBeanInfo.getOperations();
/*  227 */     this.modelMBeanNotifications = paramModelMBeanInfo.getNotifications();
/*      */     
/*      */     try {
/*  230 */       Descriptor descriptor = paramModelMBeanInfo.getMBeanDescriptor();
/*  231 */       this.modelMBeanDescriptor = validDescriptor(descriptor);
/*  232 */     } catch (MBeanException mBeanException) {
/*  233 */       this.modelMBeanDescriptor = validDescriptor(null);
/*  234 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  235 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  236 */             .getName(), "ModelMBeanInfo(ModelMBeanInfo)", "Could not get a valid modelMBeanDescriptor, setting a default Descriptor");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  243 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  244 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  245 */           .getName(), "ModelMBeanInfo(ModelMBeanInfo)", "Exit");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ModelMBeanInfoSupport(String paramString1, String paramString2, ModelMBeanAttributeInfo[] paramArrayOfModelMBeanAttributeInfo, ModelMBeanConstructorInfo[] paramArrayOfModelMBeanConstructorInfo, ModelMBeanOperationInfo[] paramArrayOfModelMBeanOperationInfo, ModelMBeanNotificationInfo[] paramArrayOfModelMBeanNotificationInfo) {
/*  274 */     this(paramString1, paramString2, paramArrayOfModelMBeanAttributeInfo, paramArrayOfModelMBeanConstructorInfo, paramArrayOfModelMBeanOperationInfo, paramArrayOfModelMBeanNotificationInfo, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ModelMBeanInfoSupport(String paramString1, String paramString2, ModelMBeanAttributeInfo[] paramArrayOfModelMBeanAttributeInfo, ModelMBeanConstructorInfo[] paramArrayOfModelMBeanConstructorInfo, ModelMBeanOperationInfo[] paramArrayOfModelMBeanOperationInfo, ModelMBeanNotificationInfo[] paramArrayOfModelMBeanNotificationInfo, Descriptor paramDescriptor) {
/*  316 */     super(paramString1, paramString2, (paramArrayOfModelMBeanAttributeInfo != null) ? (MBeanAttributeInfo[])paramArrayOfModelMBeanAttributeInfo : (MBeanAttributeInfo[])NO_ATTRIBUTES, (paramArrayOfModelMBeanConstructorInfo != null) ? (MBeanConstructorInfo[])paramArrayOfModelMBeanConstructorInfo : (MBeanConstructorInfo[])NO_CONSTRUCTORS, (paramArrayOfModelMBeanOperationInfo != null) ? (MBeanOperationInfo[])paramArrayOfModelMBeanOperationInfo : (MBeanOperationInfo[])NO_OPERATIONS, (paramArrayOfModelMBeanNotificationInfo != null) ? (MBeanNotificationInfo[])paramArrayOfModelMBeanNotificationInfo : (MBeanNotificationInfo[])NO_NOTIFICATIONS);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  328 */     this.modelMBeanAttributes = (MBeanAttributeInfo[])paramArrayOfModelMBeanAttributeInfo;
/*  329 */     this.modelMBeanConstructors = (MBeanConstructorInfo[])paramArrayOfModelMBeanConstructorInfo;
/*  330 */     this.modelMBeanOperations = (MBeanOperationInfo[])paramArrayOfModelMBeanOperationInfo;
/*  331 */     this.modelMBeanNotifications = (MBeanNotificationInfo[])paramArrayOfModelMBeanNotificationInfo;
/*  332 */     this.modelMBeanDescriptor = validDescriptor(paramDescriptor);
/*  333 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  334 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  335 */           .getName(), "ModelMBeanInfoSupport(String,String,ModelMBeanAttributeInfo[],ModelMBeanConstructorInfo[],ModelMBeanOperationInfo[],ModelMBeanNotificationInfo[],Descriptor)", "Exit");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  343 */   private static final ModelMBeanAttributeInfo[] NO_ATTRIBUTES = new ModelMBeanAttributeInfo[0];
/*      */   
/*  345 */   private static final ModelMBeanConstructorInfo[] NO_CONSTRUCTORS = new ModelMBeanConstructorInfo[0];
/*      */   
/*  347 */   private static final ModelMBeanNotificationInfo[] NO_NOTIFICATIONS = new ModelMBeanNotificationInfo[0];
/*      */   
/*  349 */   private static final ModelMBeanOperationInfo[] NO_OPERATIONS = new ModelMBeanOperationInfo[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*  364 */     return new ModelMBeanInfoSupport(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public Descriptor[] getDescriptors(String paramString) throws MBeanException, RuntimeOperationsException {
/*      */     Descriptor[] arrayOfDescriptor;
/*  370 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  371 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  372 */           .getName(), "getDescriptors(String)", "Entry");
/*      */     }
/*      */ 
/*      */     
/*  376 */     if (paramString == null || paramString.equals("")) {
/*  377 */       paramString = "all";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  384 */     if (paramString.equalsIgnoreCase("mbean")) {
/*  385 */       arrayOfDescriptor = new Descriptor[] { this.modelMBeanDescriptor };
/*  386 */     } else if (paramString.equalsIgnoreCase("attribute")) {
/*  387 */       MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = this.modelMBeanAttributes;
/*  388 */       int i = 0;
/*  389 */       if (arrayOfMBeanAttributeInfo != null) i = arrayOfMBeanAttributeInfo.length;
/*      */       
/*  391 */       arrayOfDescriptor = new Descriptor[i];
/*  392 */       for (byte b = 0; b < i; b++) {
/*  393 */         arrayOfDescriptor[b] = ((ModelMBeanAttributeInfo)arrayOfMBeanAttributeInfo[b])
/*  394 */           .getDescriptor();
/*      */       }
/*  396 */     } else if (paramString.equalsIgnoreCase("operation")) {
/*  397 */       MBeanOperationInfo[] arrayOfMBeanOperationInfo = this.modelMBeanOperations;
/*  398 */       int i = 0;
/*  399 */       if (arrayOfMBeanOperationInfo != null) i = arrayOfMBeanOperationInfo.length;
/*      */       
/*  401 */       arrayOfDescriptor = new Descriptor[i];
/*  402 */       for (byte b = 0; b < i; b++) {
/*  403 */         arrayOfDescriptor[b] = ((ModelMBeanOperationInfo)arrayOfMBeanOperationInfo[b])
/*  404 */           .getDescriptor();
/*      */       }
/*  406 */     } else if (paramString.equalsIgnoreCase("constructor")) {
/*  407 */       MBeanConstructorInfo[] arrayOfMBeanConstructorInfo = this.modelMBeanConstructors;
/*  408 */       int i = 0;
/*  409 */       if (arrayOfMBeanConstructorInfo != null) i = arrayOfMBeanConstructorInfo.length;
/*      */       
/*  411 */       arrayOfDescriptor = new Descriptor[i];
/*  412 */       for (byte b = 0; b < i; b++) {
/*  413 */         arrayOfDescriptor[b] = ((ModelMBeanConstructorInfo)arrayOfMBeanConstructorInfo[b])
/*  414 */           .getDescriptor();
/*      */       }
/*  416 */     } else if (paramString.equalsIgnoreCase("notification")) {
/*  417 */       MBeanNotificationInfo[] arrayOfMBeanNotificationInfo = this.modelMBeanNotifications;
/*  418 */       int i = 0;
/*  419 */       if (arrayOfMBeanNotificationInfo != null) i = arrayOfMBeanNotificationInfo.length;
/*      */       
/*  421 */       arrayOfDescriptor = new Descriptor[i];
/*  422 */       for (byte b = 0; b < i; b++) {
/*  423 */         arrayOfDescriptor[b] = ((ModelMBeanNotificationInfo)arrayOfMBeanNotificationInfo[b])
/*  424 */           .getDescriptor();
/*      */       }
/*  426 */     } else if (paramString.equalsIgnoreCase("all")) {
/*      */       
/*  428 */       MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = this.modelMBeanAttributes;
/*  429 */       int i = 0;
/*  430 */       if (arrayOfMBeanAttributeInfo != null) i = arrayOfMBeanAttributeInfo.length;
/*      */       
/*  432 */       MBeanOperationInfo[] arrayOfMBeanOperationInfo = this.modelMBeanOperations;
/*  433 */       int j = 0;
/*  434 */       if (arrayOfMBeanOperationInfo != null) j = arrayOfMBeanOperationInfo.length;
/*      */       
/*  436 */       MBeanConstructorInfo[] arrayOfMBeanConstructorInfo = this.modelMBeanConstructors;
/*  437 */       int k = 0;
/*  438 */       if (arrayOfMBeanConstructorInfo != null) k = arrayOfMBeanConstructorInfo.length;
/*      */       
/*  440 */       MBeanNotificationInfo[] arrayOfMBeanNotificationInfo = this.modelMBeanNotifications;
/*  441 */       int m = 0;
/*  442 */       if (arrayOfMBeanNotificationInfo != null) m = arrayOfMBeanNotificationInfo.length;
/*      */       
/*  444 */       int n = i + k + j + m + 1;
/*  445 */       arrayOfDescriptor = new Descriptor[n];
/*      */       
/*  447 */       arrayOfDescriptor[n - 1] = this.modelMBeanDescriptor;
/*      */       
/*  449 */       byte b1 = 0; byte b2;
/*  450 */       for (b2 = 0; b2 < i; b2++) {
/*  451 */         arrayOfDescriptor[b1] = ((ModelMBeanAttributeInfo)arrayOfMBeanAttributeInfo[b2])
/*  452 */           .getDescriptor();
/*  453 */         b1++;
/*      */       } 
/*  455 */       for (b2 = 0; b2 < k; b2++) {
/*  456 */         arrayOfDescriptor[b1] = ((ModelMBeanConstructorInfo)arrayOfMBeanConstructorInfo[b2])
/*  457 */           .getDescriptor();
/*  458 */         b1++;
/*      */       } 
/*  460 */       for (b2 = 0; b2 < j; b2++) {
/*  461 */         arrayOfDescriptor[b1] = ((ModelMBeanOperationInfo)arrayOfMBeanOperationInfo[b2])
/*  462 */           .getDescriptor();
/*  463 */         b1++;
/*      */       } 
/*  465 */       for (b2 = 0; b2 < m; b2++) {
/*  466 */         arrayOfDescriptor[b1] = ((ModelMBeanNotificationInfo)arrayOfMBeanNotificationInfo[b2])
/*  467 */           .getDescriptor();
/*  468 */         b1++;
/*      */       } 
/*      */     } else {
/*  471 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Descriptor Type is invalid");
/*      */ 
/*      */ 
/*      */       
/*  475 */       throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred trying to find the descriptors of the MBean");
/*      */     } 
/*  477 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  478 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  479 */           .getName(), "getDescriptors(String)", "Exit");
/*      */     }
/*      */ 
/*      */     
/*  483 */     return arrayOfDescriptor;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDescriptors(Descriptor[] paramArrayOfDescriptor) throws MBeanException, RuntimeOperationsException {
/*  489 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  490 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  491 */           .getName(), "setDescriptors(Descriptor[])", "Entry");
/*      */     }
/*      */     
/*  494 */     if (paramArrayOfDescriptor == null)
/*      */     {
/*  496 */       throw new RuntimeOperationsException(new IllegalArgumentException("Descriptor list is invalid"), "Exception occurred trying to set the descriptors of the MBeanInfo");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  501 */     if (paramArrayOfDescriptor.length == 0) {
/*      */       return;
/*      */     }
/*  504 */     for (byte b = 0; b < paramArrayOfDescriptor.length; b++) {
/*  505 */       setDescriptor(paramArrayOfDescriptor[b], null);
/*      */     }
/*  507 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  508 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  509 */           .getName(), "setDescriptors(Descriptor[])", "Exit");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Descriptor getDescriptor(String paramString) throws MBeanException, RuntimeOperationsException {
/*  533 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  534 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  535 */           .getName(), "getDescriptor(String)", "Entry");
/*      */     }
/*      */     
/*  538 */     return getDescriptor(paramString, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Descriptor getDescriptor(String paramString1, String paramString2) throws MBeanException, RuntimeOperationsException {
/*  545 */     if (paramString1 == null)
/*      */     {
/*  547 */       throw new RuntimeOperationsException(new IllegalArgumentException("Descriptor is invalid"), "Exception occurred trying to set the descriptors of the MBeanInfo");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  553 */     if ("mbean".equalsIgnoreCase(paramString2)) {
/*  554 */       return (Descriptor)this.modelMBeanDescriptor.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  564 */     if ("attribute".equalsIgnoreCase(paramString2) || paramString2 == null) {
/*  565 */       ModelMBeanAttributeInfo modelMBeanAttributeInfo = getAttribute(paramString1);
/*  566 */       if (modelMBeanAttributeInfo != null)
/*  567 */         return modelMBeanAttributeInfo.getDescriptor(); 
/*  568 */       if (paramString2 != null)
/*  569 */         return null; 
/*      */     } 
/*  571 */     if ("operation".equalsIgnoreCase(paramString2) || paramString2 == null) {
/*  572 */       ModelMBeanOperationInfo modelMBeanOperationInfo = getOperation(paramString1);
/*  573 */       if (modelMBeanOperationInfo != null)
/*  574 */         return modelMBeanOperationInfo.getDescriptor(); 
/*  575 */       if (paramString2 != null)
/*  576 */         return null; 
/*      */     } 
/*  578 */     if ("constructor".equalsIgnoreCase(paramString2) || paramString2 == null) {
/*      */       
/*  580 */       ModelMBeanConstructorInfo modelMBeanConstructorInfo = getConstructor(paramString1);
/*  581 */       if (modelMBeanConstructorInfo != null)
/*  582 */         return modelMBeanConstructorInfo.getDescriptor(); 
/*  583 */       if (paramString2 != null)
/*  584 */         return null; 
/*      */     } 
/*  586 */     if ("notification".equalsIgnoreCase(paramString2) || paramString2 == null) {
/*      */       
/*  588 */       ModelMBeanNotificationInfo modelMBeanNotificationInfo = getNotification(paramString1);
/*  589 */       if (modelMBeanNotificationInfo != null)
/*  590 */         return modelMBeanNotificationInfo.getDescriptor(); 
/*  591 */       if (paramString2 != null)
/*  592 */         return null; 
/*      */     } 
/*  594 */     if (paramString2 == null)
/*  595 */       return null; 
/*  596 */     throw new RuntimeOperationsException(new IllegalArgumentException("Descriptor Type is invalid"), "Exception occurred trying to find the descriptors of the MBean");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDescriptor(Descriptor paramDescriptor, String paramString) throws MBeanException, RuntimeOperationsException {
/*  609 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  610 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  611 */           .getName(), "setDescriptor(Descriptor,String)", "Entry");
/*      */     }
/*      */ 
/*      */     
/*  615 */     if (paramDescriptor == null) {
/*  616 */       paramDescriptor = new DescriptorSupport();
/*      */     }
/*      */     
/*  619 */     if (paramString == null || paramString.equals("")) {
/*      */       
/*  621 */       paramString = (String)paramDescriptor.getFieldValue("descriptorType");
/*      */       
/*  623 */       if (paramString == null) {
/*  624 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  625 */             .getName(), "setDescriptor(Descriptor,String)", "descriptorType null in both String parameter and Descriptor, defaulting to mbean");
/*      */ 
/*      */         
/*  628 */         paramString = "mbean";
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  633 */     String str = (String)paramDescriptor.getFieldValue("name");
/*  634 */     if (str == null) {
/*  635 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  636 */           .getName(), "setDescriptor(Descriptor,String)", "descriptor name null, defaulting to " + 
/*      */           
/*  638 */           getClassName());
/*  639 */       str = getClassName();
/*      */     } 
/*  641 */     boolean bool = false;
/*  642 */     if (paramString.equalsIgnoreCase("mbean")) {
/*  643 */       setMBeanDescriptor(paramDescriptor);
/*  644 */       bool = true;
/*  645 */     } else if (paramString.equalsIgnoreCase("attribute")) {
/*  646 */       MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = this.modelMBeanAttributes;
/*  647 */       int i = 0;
/*  648 */       if (arrayOfMBeanAttributeInfo != null) i = arrayOfMBeanAttributeInfo.length;
/*      */       
/*  650 */       for (byte b = 0; b < i; b++) {
/*  651 */         if (str.equals(arrayOfMBeanAttributeInfo[b].getName())) {
/*  652 */           bool = true;
/*  653 */           ModelMBeanAttributeInfo modelMBeanAttributeInfo = (ModelMBeanAttributeInfo)arrayOfMBeanAttributeInfo[b];
/*      */           
/*  655 */           modelMBeanAttributeInfo.setDescriptor(paramDescriptor);
/*  656 */           if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  662 */             StringBuilder stringBuilder = (new StringBuilder()).append("Setting descriptor to ").append(paramDescriptor).append("\t\n local: AttributeInfo descriptor is ").append(modelMBeanAttributeInfo.getDescriptor()).append("\t\n modelMBeanInfo: AttributeInfo descriptor is ").append(getDescriptor(str, "attribute"));
/*  663 */             JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  664 */                 .getName(), "setDescriptor(Descriptor,String)", stringBuilder
/*      */                 
/*  666 */                 .toString());
/*      */           } 
/*      */         } 
/*      */       } 
/*  670 */     } else if (paramString.equalsIgnoreCase("operation")) {
/*  671 */       MBeanOperationInfo[] arrayOfMBeanOperationInfo = this.modelMBeanOperations;
/*  672 */       int i = 0;
/*  673 */       if (arrayOfMBeanOperationInfo != null) i = arrayOfMBeanOperationInfo.length;
/*      */       
/*  675 */       for (byte b = 0; b < i; b++) {
/*  676 */         if (str.equals(arrayOfMBeanOperationInfo[b].getName())) {
/*  677 */           bool = true;
/*  678 */           ModelMBeanOperationInfo modelMBeanOperationInfo = (ModelMBeanOperationInfo)arrayOfMBeanOperationInfo[b];
/*      */           
/*  680 */           modelMBeanOperationInfo.setDescriptor(paramDescriptor);
/*      */         } 
/*      */       } 
/*  683 */     } else if (paramString.equalsIgnoreCase("constructor")) {
/*  684 */       MBeanConstructorInfo[] arrayOfMBeanConstructorInfo = this.modelMBeanConstructors;
/*  685 */       int i = 0;
/*  686 */       if (arrayOfMBeanConstructorInfo != null) i = arrayOfMBeanConstructorInfo.length;
/*      */       
/*  688 */       for (byte b = 0; b < i; b++) {
/*  689 */         if (str.equals(arrayOfMBeanConstructorInfo[b].getName())) {
/*  690 */           bool = true;
/*  691 */           ModelMBeanConstructorInfo modelMBeanConstructorInfo = (ModelMBeanConstructorInfo)arrayOfMBeanConstructorInfo[b];
/*      */           
/*  693 */           modelMBeanConstructorInfo.setDescriptor(paramDescriptor);
/*      */         } 
/*      */       } 
/*  696 */     } else if (paramString.equalsIgnoreCase("notification")) {
/*  697 */       MBeanNotificationInfo[] arrayOfMBeanNotificationInfo = this.modelMBeanNotifications;
/*  698 */       int i = 0;
/*  699 */       if (arrayOfMBeanNotificationInfo != null) i = arrayOfMBeanNotificationInfo.length;
/*      */       
/*  701 */       for (byte b = 0; b < i; b++) {
/*  702 */         if (str.equals(arrayOfMBeanNotificationInfo[b].getName())) {
/*  703 */           bool = true;
/*  704 */           ModelMBeanNotificationInfo modelMBeanNotificationInfo = (ModelMBeanNotificationInfo)arrayOfMBeanNotificationInfo[b];
/*      */           
/*  706 */           modelMBeanNotificationInfo.setDescriptor(paramDescriptor);
/*      */         } 
/*      */       } 
/*      */     } else {
/*  710 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Invalid descriptor type: " + paramString);
/*      */ 
/*      */       
/*  713 */       throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred trying to set the descriptors of the MBean");
/*      */     } 
/*      */     
/*  716 */     if (!bool) {
/*  717 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Descriptor name is invalid: type=" + paramString + "; name=" + str);
/*      */ 
/*      */ 
/*      */       
/*  721 */       throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred trying to set the descriptors of the MBean");
/*      */     } 
/*  723 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  724 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  725 */           .getName(), "setDescriptor(Descriptor,String)", "Exit");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ModelMBeanAttributeInfo getAttribute(String paramString) throws MBeanException, RuntimeOperationsException {
/*  734 */     ModelMBeanAttributeInfo modelMBeanAttributeInfo = null;
/*  735 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  736 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  737 */           .getName(), "getAttribute(String)", "Entry");
/*      */     }
/*      */     
/*  740 */     if (paramString == null) {
/*  741 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute Name is null"), "Exception occurred trying to get the ModelMBeanAttributeInfo of the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  746 */     MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = this.modelMBeanAttributes;
/*  747 */     int i = 0;
/*  748 */     if (arrayOfMBeanAttributeInfo != null) i = arrayOfMBeanAttributeInfo.length;
/*      */     
/*  750 */     for (byte b = 0; b < i && modelMBeanAttributeInfo == null; b++) {
/*  751 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  758 */         StringBuilder stringBuilder = (new StringBuilder()).append("\t\n this.getAttributes() MBeanAttributeInfo Array ").append(b).append(":").append(((ModelMBeanAttributeInfo)arrayOfMBeanAttributeInfo[b]).getDescriptor()).append("\t\n this.modelMBeanAttributes MBeanAttributeInfo Array ").append(b).append(":").append(((ModelMBeanAttributeInfo)this.modelMBeanAttributes[b]).getDescriptor());
/*  759 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  760 */             .getName(), "getAttribute(String)", stringBuilder
/*  761 */             .toString());
/*      */       } 
/*  763 */       if (paramString.equals(arrayOfMBeanAttributeInfo[b].getName())) {
/*  764 */         modelMBeanAttributeInfo = (ModelMBeanAttributeInfo)arrayOfMBeanAttributeInfo[b].clone();
/*      */       }
/*      */     } 
/*  767 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  768 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  769 */           .getName(), "getAttribute(String)", "Exit");
/*      */     }
/*      */ 
/*      */     
/*  773 */     return modelMBeanAttributeInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ModelMBeanOperationInfo getOperation(String paramString) throws MBeanException, RuntimeOperationsException {
/*  780 */     ModelMBeanOperationInfo modelMBeanOperationInfo = null;
/*  781 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  782 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  783 */           .getName(), "getOperation(String)", "Entry");
/*      */     }
/*      */     
/*  786 */     if (paramString == null) {
/*  787 */       throw new RuntimeOperationsException(new IllegalArgumentException("inName is null"), "Exception occurred trying to get the ModelMBeanOperationInfo of the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  792 */     MBeanOperationInfo[] arrayOfMBeanOperationInfo = this.modelMBeanOperations;
/*  793 */     int i = 0;
/*  794 */     if (arrayOfMBeanOperationInfo != null) i = arrayOfMBeanOperationInfo.length;
/*      */     
/*  796 */     for (byte b = 0; b < i && modelMBeanOperationInfo == null; b++) {
/*  797 */       if (paramString.equals(arrayOfMBeanOperationInfo[b].getName())) {
/*  798 */         modelMBeanOperationInfo = (ModelMBeanOperationInfo)arrayOfMBeanOperationInfo[b].clone();
/*      */       }
/*      */     } 
/*  801 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  802 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  803 */           .getName(), "getOperation(String)", "Exit");
/*      */     }
/*      */ 
/*      */     
/*  807 */     return modelMBeanOperationInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ModelMBeanConstructorInfo getConstructor(String paramString) throws MBeanException, RuntimeOperationsException {
/*  826 */     ModelMBeanConstructorInfo modelMBeanConstructorInfo = null;
/*  827 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  828 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  829 */           .getName(), "getConstructor(String)", "Entry");
/*      */     }
/*      */     
/*  832 */     if (paramString == null) {
/*  833 */       throw new RuntimeOperationsException(new IllegalArgumentException("Constructor name is null"), "Exception occurred trying to get the ModelMBeanConstructorInfo of the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  838 */     MBeanConstructorInfo[] arrayOfMBeanConstructorInfo = this.modelMBeanConstructors;
/*  839 */     int i = 0;
/*  840 */     if (arrayOfMBeanConstructorInfo != null) i = arrayOfMBeanConstructorInfo.length;
/*      */     
/*  842 */     for (byte b = 0; b < i && modelMBeanConstructorInfo == null; b++) {
/*  843 */       if (paramString.equals(arrayOfMBeanConstructorInfo[b].getName())) {
/*  844 */         modelMBeanConstructorInfo = (ModelMBeanConstructorInfo)arrayOfMBeanConstructorInfo[b].clone();
/*      */       }
/*      */     } 
/*  847 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  848 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  849 */           .getName(), "getConstructor(String)", "Exit");
/*      */     }
/*      */ 
/*      */     
/*  853 */     return modelMBeanConstructorInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ModelMBeanNotificationInfo getNotification(String paramString) throws MBeanException, RuntimeOperationsException {
/*  859 */     ModelMBeanNotificationInfo modelMBeanNotificationInfo = null;
/*  860 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  861 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  862 */           .getName(), "getNotification(String)", "Entry");
/*      */     }
/*      */     
/*  865 */     if (paramString == null) {
/*  866 */       throw new RuntimeOperationsException(new IllegalArgumentException("Notification name is null"), "Exception occurred trying to get the ModelMBeanNotificationInfo of the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  871 */     MBeanNotificationInfo[] arrayOfMBeanNotificationInfo = this.modelMBeanNotifications;
/*  872 */     int i = 0;
/*  873 */     if (arrayOfMBeanNotificationInfo != null) i = arrayOfMBeanNotificationInfo.length;
/*      */     
/*  875 */     for (byte b = 0; b < i && modelMBeanNotificationInfo == null; b++) {
/*  876 */       if (paramString.equals(arrayOfMBeanNotificationInfo[b].getName())) {
/*  877 */         modelMBeanNotificationInfo = (ModelMBeanNotificationInfo)arrayOfMBeanNotificationInfo[b].clone();
/*      */       }
/*      */     } 
/*  880 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  881 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  882 */           .getName(), "getNotification(String)", "Exit");
/*      */     }
/*      */ 
/*      */     
/*  886 */     return modelMBeanNotificationInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Descriptor getDescriptor() {
/*  896 */     return getMBeanDescriptorNoException();
/*      */   }
/*      */   
/*      */   public Descriptor getMBeanDescriptor() throws MBeanException {
/*  900 */     return getMBeanDescriptorNoException();
/*      */   }
/*      */   
/*      */   private Descriptor getMBeanDescriptorNoException() {
/*  904 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  905 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  906 */           .getName(), "getMBeanDescriptorNoException()", "Entry");
/*      */     }
/*      */ 
/*      */     
/*  910 */     if (this.modelMBeanDescriptor == null) {
/*  911 */       this.modelMBeanDescriptor = validDescriptor(null);
/*      */     }
/*  913 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  914 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  915 */           .getName(), "getMBeanDescriptorNoException()", "Exit, returning: " + this.modelMBeanDescriptor);
/*      */     }
/*      */ 
/*      */     
/*  919 */     return (Descriptor)this.modelMBeanDescriptor.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMBeanDescriptor(Descriptor paramDescriptor) throws MBeanException, RuntimeOperationsException {
/*  924 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  925 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, ModelMBeanInfoSupport.class
/*  926 */           .getName(), "setMBeanDescriptor(Descriptor)", "Entry");
/*      */     }
/*      */     
/*  929 */     this.modelMBeanDescriptor = validDescriptor(paramDescriptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Descriptor validDescriptor(Descriptor paramDescriptor) throws RuntimeOperationsException {
/*      */     Descriptor descriptor;
/*  948 */     boolean bool = (paramDescriptor == null) ? true : false;
/*  949 */     if (bool) {
/*  950 */       descriptor = new DescriptorSupport();
/*  951 */       JmxProperties.MODELMBEAN_LOGGER.finer("Null Descriptor, creating new.");
/*      */     } else {
/*  953 */       descriptor = (Descriptor)paramDescriptor.clone();
/*      */     } 
/*      */ 
/*      */     
/*  957 */     if (bool && descriptor.getFieldValue("name") == null) {
/*  958 */       descriptor.setField("name", getClassName());
/*  959 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor name to " + getClassName());
/*      */     } 
/*  961 */     if (bool && descriptor.getFieldValue("descriptorType") == null) {
/*  962 */       descriptor.setField("descriptorType", "mbean");
/*  963 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting descriptorType to \"mbean\"");
/*      */     } 
/*  965 */     if (descriptor.getFieldValue("displayName") == null) {
/*  966 */       descriptor.setField("displayName", getClassName());
/*  967 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor displayName to " + getClassName());
/*      */     } 
/*  969 */     if (descriptor.getFieldValue("persistPolicy") == null) {
/*  970 */       descriptor.setField("persistPolicy", "never");
/*  971 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor persistPolicy to \"never\"");
/*      */     } 
/*  973 */     if (descriptor.getFieldValue("log") == null) {
/*  974 */       descriptor.setField("log", "F");
/*  975 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor \"log\" field to \"F\"");
/*      */     } 
/*  977 */     if (descriptor.getFieldValue("visibility") == null) {
/*  978 */       descriptor.setField("visibility", "1");
/*  979 */       JmxProperties.MODELMBEAN_LOGGER.finer("Defaulting Descriptor visibility to 1");
/*      */     } 
/*      */ 
/*      */     
/*  983 */     if (!descriptor.isValid()) {
/*  984 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The isValid() method of the Descriptor object itself returned false,one or more required fields are invalid. Descriptor:" + descriptor
/*      */           
/*  986 */           .toString());
/*      */     }
/*      */     
/*  989 */     if (!((String)descriptor.getFieldValue("descriptorType")).equalsIgnoreCase("mbean")) {
/*  990 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid Descriptor argument"), "The Descriptor \"descriptorType\" field does not match the object described.  Expected: mbean , was: " + descriptor
/*      */           
/*  992 */           .getFieldValue("descriptorType"));
/*      */     }
/*      */     
/*  995 */     return descriptor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1006 */     if (compat) {
/*      */ 
/*      */       
/* 1009 */       ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 1010 */       this
/* 1011 */         .modelMBeanDescriptor = (Descriptor)getField.get("modelMBeanDescriptor", (Object)null);
/* 1012 */       if (getField.defaulted("modelMBeanDescriptor")) {
/* 1013 */         throw new NullPointerException("modelMBeanDescriptor");
/*      */       }
/* 1015 */       this
/* 1016 */         .modelMBeanAttributes = (MBeanAttributeInfo[])getField.get("mmbAttributes", (Object)null);
/* 1017 */       if (getField.defaulted("mmbAttributes")) {
/* 1018 */         throw new NullPointerException("mmbAttributes");
/*      */       }
/* 1020 */       this
/* 1021 */         .modelMBeanConstructors = (MBeanConstructorInfo[])getField.get("mmbConstructors", (Object)null);
/* 1022 */       if (getField.defaulted("mmbConstructors")) {
/* 1023 */         throw new NullPointerException("mmbConstructors");
/*      */       }
/* 1025 */       this
/* 1026 */         .modelMBeanNotifications = (MBeanNotificationInfo[])getField.get("mmbNotifications", (Object)null);
/* 1027 */       if (getField.defaulted("mmbNotifications")) {
/* 1028 */         throw new NullPointerException("mmbNotifications");
/*      */       }
/* 1030 */       this
/* 1031 */         .modelMBeanOperations = (MBeanOperationInfo[])getField.get("mmbOperations", (Object)null);
/* 1032 */       if (getField.defaulted("mmbOperations")) {
/* 1033 */         throw new NullPointerException("mmbOperations");
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1038 */       paramObjectInputStream.defaultReadObject();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1048 */     if (compat) {
/*      */ 
/*      */       
/* 1051 */       ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 1052 */       putField.put("modelMBeanDescriptor", this.modelMBeanDescriptor);
/* 1053 */       putField.put("mmbAttributes", this.modelMBeanAttributes);
/* 1054 */       putField.put("mmbConstructors", this.modelMBeanConstructors);
/* 1055 */       putField.put("mmbNotifications", this.modelMBeanNotifications);
/* 1056 */       putField.put("mmbOperations", this.modelMBeanOperations);
/* 1057 */       putField.put("currClass", "ModelMBeanInfoSupport");
/* 1058 */       paramObjectOutputStream.writeFields();
/*      */     }
/*      */     else {
/*      */       
/* 1062 */       paramObjectOutputStream.defaultWriteObject();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/modelmbean/ModelMBeanInfoSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */