/*      */ package javax.management.modelmbean;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.Attribute;
/*      */ import javax.management.AttributeChangeNotification;
/*      */ import javax.management.AttributeChangeNotificationFilter;
/*      */ import javax.management.AttributeList;
/*      */ import javax.management.AttributeNotFoundException;
/*      */ import javax.management.Descriptor;
/*      */ import javax.management.InstanceNotFoundException;
/*      */ import javax.management.InvalidAttributeValueException;
/*      */ import javax.management.ListenerNotFoundException;
/*      */ import javax.management.MBeanAttributeInfo;
/*      */ import javax.management.MBeanConstructorInfo;
/*      */ import javax.management.MBeanException;
/*      */ import javax.management.MBeanInfo;
/*      */ import javax.management.MBeanNotificationInfo;
/*      */ import javax.management.MBeanOperationInfo;
/*      */ import javax.management.MBeanRegistration;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.MBeanServerFactory;
/*      */ import javax.management.Notification;
/*      */ import javax.management.NotificationBroadcasterSupport;
/*      */ import javax.management.NotificationEmitter;
/*      */ import javax.management.NotificationFilter;
/*      */ import javax.management.NotificationListener;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.ReflectionException;
/*      */ import javax.management.RuntimeErrorException;
/*      */ import javax.management.RuntimeOperationsException;
/*      */ import javax.management.ServiceNotFoundException;
/*      */ import javax.management.loading.ClassLoaderRepository;
/*      */ import sun.misc.JavaSecurityAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.reflect.misc.MethodUtil;
/*      */ import sun.reflect.misc.ReflectUtil;
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
/*      */ 
/*      */ public class RequiredModelMBean
/*      */   implements ModelMBean, MBeanRegistration, NotificationEmitter
/*      */ {
/*      */   ModelMBeanInfo modelMBeanInfo;
/*  131 */   private NotificationBroadcasterSupport generalBroadcaster = null;
/*      */ 
/*      */   
/*  134 */   private NotificationBroadcasterSupport attributeBroadcaster = null;
/*      */ 
/*      */ 
/*      */   
/*  138 */   private Object managedResource = null;
/*      */ 
/*      */   
/*      */   private boolean registered = false;
/*      */   
/*  143 */   private transient MBeanServer server = null;
/*      */   
/*  145 */   private static final JavaSecurityAccess javaSecurityAccess = SharedSecrets.getJavaSecurityAccess();
/*  146 */   private final AccessControlContext acc = AccessController.getContext();
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
/*      */   public RequiredModelMBean() throws MBeanException, RuntimeOperationsException {
/*  169 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  170 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  171 */           .getName(), "RequiredModelMBean()", "Entry");
/*      */     }
/*      */     
/*  174 */     this.modelMBeanInfo = createDefaultModelMBeanInfo();
/*  175 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  176 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  177 */           .getName(), "RequiredModelMBean()", "Exit");
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
/*      */   public RequiredModelMBean(ModelMBeanInfo paramModelMBeanInfo) throws MBeanException, RuntimeOperationsException {
/*  205 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  206 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  207 */           .getName(), "RequiredModelMBean(MBeanInfo)", "Entry");
/*      */     }
/*      */     
/*  210 */     setModelMBeanInfo(paramModelMBeanInfo);
/*      */     
/*  212 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  213 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  214 */           .getName(), "RequiredModelMBean(MBeanInfo)", "Exit");
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
/*      */   public void setModelMBeanInfo(ModelMBeanInfo paramModelMBeanInfo) throws MBeanException, RuntimeOperationsException {
/*  260 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  261 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  262 */           .getName(), "setModelMBeanInfo(ModelMBeanInfo)", "Entry");
/*      */     }
/*      */ 
/*      */     
/*  266 */     if (paramModelMBeanInfo == null) {
/*  267 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  268 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  269 */             .getName(), "setModelMBeanInfo(ModelMBeanInfo)", "ModelMBeanInfo is null: Raising exception.");
/*      */       }
/*      */ 
/*      */       
/*  273 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("ModelMBeanInfo must not be null");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  278 */       throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred trying to initialize the ModelMBeanInfo of the RequiredModelMBean");
/*      */     } 
/*      */     
/*  281 */     if (this.registered) {
/*  282 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  283 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  284 */             .getName(), "setModelMBeanInfo(ModelMBeanInfo)", "RequiredMBean is registered: Raising exception.");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  291 */       IllegalStateException illegalStateException = new IllegalStateException("cannot call setModelMBeanInfo while ModelMBean is registered");
/*      */       
/*  293 */       throw new RuntimeOperationsException(illegalStateException, "Exception occurred trying to set the ModelMBeanInfo of the RequiredModelMBean");
/*      */     } 
/*      */     
/*  296 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  297 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  298 */           .getName(), "setModelMBeanInfo(ModelMBeanInfo)", "Setting ModelMBeanInfo to " + 
/*      */           
/*  300 */           printModelMBeanInfo(paramModelMBeanInfo));
/*  301 */       int i = 0;
/*  302 */       if (paramModelMBeanInfo.getNotifications() != null) {
/*  303 */         i = (paramModelMBeanInfo.getNotifications()).length;
/*      */       }
/*  305 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  306 */           .getName(), "setModelMBeanInfo(ModelMBeanInfo)", "ModelMBeanInfo notifications has " + i + " elements");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  312 */     this.modelMBeanInfo = (ModelMBeanInfo)paramModelMBeanInfo.clone();
/*      */     
/*  314 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  315 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  316 */           .getName(), "setModelMBeanInfo(ModelMBeanInfo)", "set mbeanInfo to: " + 
/*      */           
/*  318 */           printModelMBeanInfo(this.modelMBeanInfo));
/*  319 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  320 */           .getName(), "setModelMBeanInfo(ModelMBeanInfo)", "Exit");
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
/*      */   public void setManagedResource(Object paramObject, String paramString) throws MBeanException, RuntimeOperationsException, InstanceNotFoundException, InvalidTargetObjectTypeException {
/*  349 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  350 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  351 */           .getName(), "setManagedResource(Object,String)", "Entry");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  357 */     if (paramString == null || 
/*  358 */       !paramString.equalsIgnoreCase("objectReference")) {
/*  359 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  360 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  361 */             .getName(), "setManagedResource(Object,String)", "Managed Resource Type is not supported: " + paramString);
/*      */       }
/*      */ 
/*      */       
/*  365 */       throw new InvalidTargetObjectTypeException(paramString);
/*      */     } 
/*      */     
/*  368 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  369 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  370 */           .getName(), "setManagedResource(Object,String)", "Managed Resource is valid");
/*      */     }
/*      */ 
/*      */     
/*  374 */     this.managedResource = paramObject;
/*      */     
/*  376 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  377 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  378 */           .getName(), "setManagedResource(Object, String)", "Exit");
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
/*      */   public void load() throws MBeanException, RuntimeOperationsException, InstanceNotFoundException {
/*  406 */     ServiceNotFoundException serviceNotFoundException = new ServiceNotFoundException("Persistence not supported for this MBean");
/*      */     
/*  408 */     throw new MBeanException(serviceNotFoundException, serviceNotFoundException.getMessage());
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
/*      */   public void store() throws MBeanException, RuntimeOperationsException, InstanceNotFoundException {
/*  448 */     ServiceNotFoundException serviceNotFoundException = new ServiceNotFoundException("Persistence not supported for this MBean");
/*      */     
/*  450 */     throw new MBeanException(serviceNotFoundException, serviceNotFoundException.getMessage());
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
/*      */   private Object resolveForCacheValue(Descriptor paramDescriptor) throws MBeanException, RuntimeOperationsException {
/*      */     String str;
/*  485 */     boolean bool = JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER);
/*      */     
/*  487 */     if (bool) {
/*  488 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  489 */           .getName(), "resolveForCacheValue(Descriptor)", "Entry");
/*      */     }
/*      */     
/*  492 */     Object object1 = null;
/*  493 */     boolean bool1 = false, bool2 = true;
/*  494 */     long l = 0L;
/*      */     
/*  496 */     if (paramDescriptor == null) {
/*  497 */       if (bool) {
/*  498 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  499 */             .getName(), "resolveForCacheValue(Descriptor)", "Input Descriptor is null");
/*      */       }
/*      */       
/*  502 */       return object1;
/*      */     } 
/*      */     
/*  505 */     if (bool) {
/*  506 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  507 */           .getName(), "resolveForCacheValue(Descriptor)", "descriptor is " + paramDescriptor);
/*      */     }
/*      */ 
/*      */     
/*  511 */     Descriptor descriptor = this.modelMBeanInfo.getMBeanDescriptor();
/*  512 */     if (descriptor == null && 
/*  513 */       bool) {
/*  514 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  515 */           .getName(), "resolveForCacheValue(Descriptor)", "MBean Descriptor is null");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  521 */     Object object2 = paramDescriptor.getFieldValue("currencyTimeLimit");
/*      */ 
/*      */     
/*  524 */     if (object2 != null) {
/*  525 */       str = object2.toString();
/*      */     } else {
/*  527 */       str = null;
/*      */     } 
/*      */     
/*  530 */     if (str == null && descriptor != null) {
/*  531 */       object2 = descriptor.getFieldValue("currencyTimeLimit");
/*  532 */       if (object2 != null) {
/*  533 */         str = object2.toString();
/*      */       } else {
/*  535 */         str = null;
/*      */       } 
/*      */     } 
/*      */     
/*  539 */     if (str != null) {
/*  540 */       if (bool) {
/*  541 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  542 */             .getName(), "resolveForCacheValue(Descriptor)", "currencyTimeLimit: " + str);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  547 */       l = (new Long(str)).longValue() * 1000L;
/*  548 */       if (l < 0L) {
/*      */         
/*  550 */         bool2 = false;
/*  551 */         bool1 = true;
/*  552 */         if (bool) {
/*  553 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  554 */               .getName(), "resolveForCacheValue(Descriptor)", l + ": never Cached");
/*      */         }
/*      */       }
/*  557 */       else if (l == 0L) {
/*      */         
/*  559 */         bool2 = true;
/*  560 */         bool1 = false;
/*  561 */         if (bool) {
/*  562 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  563 */               .getName(), "resolveForCacheValue(Descriptor)", "always valid Cache");
/*      */         }
/*      */       } else {
/*      */         String str1;
/*      */         
/*  568 */         Object object = paramDescriptor.getFieldValue("lastUpdatedTimeStamp");
/*      */ 
/*      */         
/*  571 */         if (object != null) { str1 = object.toString(); }
/*  572 */         else { str1 = null; }
/*      */         
/*  574 */         if (bool) {
/*  575 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  576 */               .getName(), "resolveForCacheValue(Descriptor)", "lastUpdatedTimeStamp: " + str1);
/*      */         }
/*      */ 
/*      */         
/*  580 */         if (str1 == null) {
/*  581 */           str1 = "0";
/*      */         }
/*  583 */         long l1 = (new Long(str1)).longValue();
/*      */         
/*  585 */         if (bool) {
/*  586 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  587 */               .getName(), "resolveForCacheValue(Descriptor)", "currencyPeriod:" + l + " lastUpdatedTimeStamp:" + l1);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  592 */         long l2 = (new Date()).getTime();
/*      */         
/*  594 */         if (l2 < l1 + l) {
/*  595 */           bool2 = true;
/*  596 */           bool1 = false;
/*  597 */           if (bool) {
/*  598 */             JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  599 */                 .getName(), "resolveForCacheValue(Descriptor)", " timed valid Cache for " + l2 + " < " + (l1 + l));
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  604 */           bool2 = false;
/*  605 */           bool1 = true;
/*  606 */           if (bool) {
/*  607 */             JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  608 */                 .getName(), "resolveForCacheValue(Descriptor)", "timed expired cache for " + l2 + " > " + (l1 + l));
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  614 */       if (bool) {
/*  615 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  616 */             .getName(), "resolveForCacheValue(Descriptor)", "returnCachedValue:" + bool2 + " resetValue: " + bool1);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  621 */       if (bool2 == true) {
/*  622 */         Object object = paramDescriptor.getFieldValue("value");
/*  623 */         if (object != null) {
/*      */           
/*  625 */           object1 = object;
/*      */           
/*  627 */           if (bool) {
/*  628 */             JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  629 */                 .getName(), "resolveForCacheValue(Descriptor)", "valid Cache value: " + object);
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  634 */           object1 = null;
/*  635 */           if (bool) {
/*  636 */             JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  637 */                 .getName(), "resolveForCacheValue(Descriptor)", "no Cached value");
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  643 */       if (bool1 == true) {
/*      */         
/*  645 */         paramDescriptor.removeField("lastUpdatedTimeStamp");
/*  646 */         paramDescriptor.removeField("value");
/*  647 */         object1 = null;
/*  648 */         this.modelMBeanInfo.setDescriptor(paramDescriptor, null);
/*  649 */         if (bool) {
/*  650 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  651 */               .getName(), "resolveForCacheValue(Descriptor)", "reset cached value to null");
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  657 */     if (bool) {
/*  658 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  659 */           .getName(), "resolveForCacheValue(Descriptor)", "Exit");
/*      */     }
/*      */     
/*  662 */     return object1;
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
/*      */   public MBeanInfo getMBeanInfo() {
/*  675 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  676 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  677 */           .getName(), "getMBeanInfo()", "Entry");
/*      */     }
/*      */ 
/*      */     
/*  681 */     if (this.modelMBeanInfo == null) {
/*  682 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  683 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  684 */             .getName(), "getMBeanInfo()", "modelMBeanInfo is null");
/*      */       }
/*      */       
/*  687 */       this.modelMBeanInfo = createDefaultModelMBeanInfo();
/*      */     } 
/*      */ 
/*      */     
/*  691 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  692 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  693 */           .getName(), "getMBeanInfo()", "ModelMBeanInfo is " + this.modelMBeanInfo
/*      */           
/*  695 */           .getClassName() + " for " + this.modelMBeanInfo
/*  696 */           .getDescription());
/*  697 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  698 */           .getName(), "getMBeanInfo()", 
/*  699 */           printModelMBeanInfo(this.modelMBeanInfo));
/*      */     } 
/*      */     
/*  702 */     return (MBeanInfo)this.modelMBeanInfo.clone();
/*      */   }
/*      */   
/*      */   private String printModelMBeanInfo(ModelMBeanInfo paramModelMBeanInfo) {
/*  706 */     StringBuilder stringBuilder = new StringBuilder();
/*  707 */     if (paramModelMBeanInfo == null) {
/*  708 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/*  709 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  710 */             .getName(), "printModelMBeanInfo(ModelMBeanInfo)", "ModelMBeanInfo to print is null, printing local ModelMBeanInfo");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  715 */       paramModelMBeanInfo = this.modelMBeanInfo;
/*      */     } 
/*      */     
/*  718 */     stringBuilder.append("\nMBeanInfo for ModelMBean is:");
/*  719 */     stringBuilder.append("\nCLASSNAME: \t" + paramModelMBeanInfo.getClassName());
/*  720 */     stringBuilder.append("\nDESCRIPTION: \t" + paramModelMBeanInfo.getDescription());
/*      */ 
/*      */     
/*      */     try {
/*  724 */       stringBuilder.append("\nMBEAN DESCRIPTOR: \t" + paramModelMBeanInfo
/*  725 */           .getMBeanDescriptor());
/*  726 */     } catch (Exception exception) {
/*  727 */       stringBuilder.append("\nMBEAN DESCRIPTOR: \t is invalid");
/*      */     } 
/*      */     
/*  730 */     stringBuilder.append("\nATTRIBUTES");
/*      */     
/*  732 */     MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = paramModelMBeanInfo.getAttributes();
/*  733 */     if (arrayOfMBeanAttributeInfo != null && arrayOfMBeanAttributeInfo.length > 0) {
/*  734 */       for (byte b = 0; b < arrayOfMBeanAttributeInfo.length; b++) {
/*  735 */         ModelMBeanAttributeInfo modelMBeanAttributeInfo = (ModelMBeanAttributeInfo)arrayOfMBeanAttributeInfo[b];
/*      */         
/*  737 */         stringBuilder.append(" ** NAME: \t" + modelMBeanAttributeInfo.getName());
/*  738 */         stringBuilder.append("    DESCR: \t" + modelMBeanAttributeInfo.getDescription());
/*  739 */         stringBuilder.append("    TYPE: \t" + modelMBeanAttributeInfo.getType() + "    READ: \t" + modelMBeanAttributeInfo
/*  740 */             .isReadable() + "    WRITE: \t" + modelMBeanAttributeInfo
/*  741 */             .isWritable());
/*  742 */         stringBuilder.append("    DESCRIPTOR: " + modelMBeanAttributeInfo
/*  743 */             .getDescriptor().toString());
/*      */       } 
/*      */     } else {
/*  746 */       stringBuilder.append(" ** No attributes **");
/*      */     } 
/*      */     
/*  749 */     stringBuilder.append("\nCONSTRUCTORS");
/*  750 */     MBeanConstructorInfo[] arrayOfMBeanConstructorInfo = paramModelMBeanInfo.getConstructors();
/*  751 */     if (arrayOfMBeanConstructorInfo != null && arrayOfMBeanConstructorInfo.length > 0) {
/*  752 */       for (byte b = 0; b < arrayOfMBeanConstructorInfo.length; b++) {
/*  753 */         ModelMBeanConstructorInfo modelMBeanConstructorInfo = (ModelMBeanConstructorInfo)arrayOfMBeanConstructorInfo[b];
/*      */         
/*  755 */         stringBuilder.append(" ** NAME: \t" + modelMBeanConstructorInfo.getName());
/*  756 */         stringBuilder.append("    DESCR: \t" + modelMBeanConstructorInfo
/*  757 */             .getDescription());
/*  758 */         stringBuilder.append("    PARAM: \t" + (modelMBeanConstructorInfo
/*  759 */             .getSignature()).length + " parameter(s)");
/*      */         
/*  761 */         stringBuilder.append("    DESCRIPTOR: " + modelMBeanConstructorInfo
/*  762 */             .getDescriptor().toString());
/*      */       } 
/*      */     } else {
/*  765 */       stringBuilder.append(" ** No Constructors **");
/*      */     } 
/*      */     
/*  768 */     stringBuilder.append("\nOPERATIONS");
/*  769 */     MBeanOperationInfo[] arrayOfMBeanOperationInfo = paramModelMBeanInfo.getOperations();
/*  770 */     if (arrayOfMBeanOperationInfo != null && arrayOfMBeanOperationInfo.length > 0) {
/*  771 */       for (byte b = 0; b < arrayOfMBeanOperationInfo.length; b++) {
/*  772 */         ModelMBeanOperationInfo modelMBeanOperationInfo = (ModelMBeanOperationInfo)arrayOfMBeanOperationInfo[b];
/*      */         
/*  774 */         stringBuilder.append(" ** NAME: \t" + modelMBeanOperationInfo.getName());
/*  775 */         stringBuilder.append("    DESCR: \t" + modelMBeanOperationInfo.getDescription());
/*  776 */         stringBuilder.append("    PARAM: \t" + (modelMBeanOperationInfo
/*  777 */             .getSignature()).length + " parameter(s)");
/*      */         
/*  779 */         stringBuilder.append("    DESCRIPTOR: " + modelMBeanOperationInfo
/*  780 */             .getDescriptor().toString());
/*      */       } 
/*      */     } else {
/*  783 */       stringBuilder.append(" ** No operations ** ");
/*      */     } 
/*      */     
/*  786 */     stringBuilder.append("\nNOTIFICATIONS");
/*      */     
/*  788 */     MBeanNotificationInfo[] arrayOfMBeanNotificationInfo = paramModelMBeanInfo.getNotifications();
/*  789 */     if (arrayOfMBeanNotificationInfo != null && arrayOfMBeanNotificationInfo.length > 0) {
/*  790 */       for (byte b = 0; b < arrayOfMBeanNotificationInfo.length; b++) {
/*  791 */         ModelMBeanNotificationInfo modelMBeanNotificationInfo = (ModelMBeanNotificationInfo)arrayOfMBeanNotificationInfo[b];
/*      */         
/*  793 */         stringBuilder.append(" ** NAME: \t" + modelMBeanNotificationInfo.getName());
/*  794 */         stringBuilder.append("    DESCR: \t" + modelMBeanNotificationInfo.getDescription());
/*  795 */         stringBuilder.append("    DESCRIPTOR: " + modelMBeanNotificationInfo
/*  796 */             .getDescriptor().toString());
/*      */       } 
/*      */     } else {
/*  799 */       stringBuilder.append(" ** No notifications **");
/*      */     } 
/*      */     
/*  802 */     stringBuilder.append(" ** ModelMBean: End of MBeanInfo ** ");
/*      */     
/*  804 */     return stringBuilder.toString();
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
/*      */   public Object invoke(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws MBeanException, ReflectionException {
/*      */     Object object3;
/*  917 */     boolean bool = JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER);
/*      */ 
/*      */     
/*  920 */     if (bool) {
/*  921 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  922 */           .getName(), "invoke(String, Object[], String[])", "Entry");
/*      */     }
/*      */     
/*  925 */     if (paramString == null) {
/*  926 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Method name must not be null");
/*      */       
/*  928 */       throw new RuntimeOperationsException(illegalArgumentException, "An exception occurred while trying to invoke a method on a RequiredModelMBean");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  933 */     String str1 = null;
/*      */ 
/*      */ 
/*      */     
/*  937 */     int i = paramString.lastIndexOf(".");
/*  938 */     if (i > 0) {
/*  939 */       str1 = paramString.substring(0, i);
/*  940 */       str2 = paramString.substring(i + 1);
/*      */     } else {
/*  942 */       str2 = paramString;
/*      */     } 
/*      */ 
/*      */     
/*  946 */     i = str2.indexOf("(");
/*  947 */     if (i > 0) {
/*  948 */       str2 = str2.substring(0, i);
/*      */     }
/*  950 */     if (bool) {
/*  951 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  952 */           .getName(), "invoke(String, Object[], String[])", "Finding operation " + paramString + " as " + str2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  957 */     ModelMBeanOperationInfo modelMBeanOperationInfo = this.modelMBeanInfo.getOperation(str2);
/*  958 */     if (modelMBeanOperationInfo == null) {
/*  959 */       final String className = "Operation " + paramString + " not in ModelMBeanInfo";
/*      */       
/*  961 */       throw new MBeanException(new ServiceNotFoundException(str), str);
/*      */     } 
/*      */     
/*  964 */     Descriptor descriptor = modelMBeanOperationInfo.getDescriptor();
/*  965 */     if (descriptor == null)
/*      */     {
/*  967 */       throw new MBeanException(new ServiceNotFoundException("Operation descriptor null"), "Operation descriptor null");
/*      */     }
/*      */     
/*  970 */     Object object1 = resolveForCacheValue(descriptor);
/*  971 */     if (object1 != null) {
/*  972 */       if (bool) {
/*  973 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/*  974 */             .getName(), "invoke(String, Object[], String[])", "Returning cached value");
/*      */       }
/*      */ 
/*      */       
/*  978 */       return object1;
/*      */     } 
/*      */     
/*  981 */     if (str1 == null) {
/*  982 */       str1 = (String)descriptor.getFieldValue("class");
/*      */     }
/*      */     
/*  985 */     String str2 = (String)descriptor.getFieldValue("name");
/*  986 */     if (str2 == null)
/*      */     {
/*      */       
/*  989 */       throw new MBeanException(new ServiceNotFoundException("Method descriptor must include `name' field"), "Method descriptor must include `name' field");
/*      */     }
/*      */ 
/*      */     
/*  993 */     String str3 = (String)descriptor.getFieldValue("targetType");
/*  994 */     if (str3 != null && 
/*  995 */       !str3.equalsIgnoreCase("objectReference")) {
/*  996 */       final String className = "Target type must be objectReference: " + str3;
/*      */       
/*  998 */       throw new MBeanException(new InvalidTargetObjectTypeException(str), str);
/*      */     } 
/*      */ 
/*      */     
/* 1002 */     Object object2 = descriptor.getFieldValue("targetObject");
/* 1003 */     if (bool && object2 != null) {
/* 1004 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1005 */           .getName(), "invoke(String, Object[], String[])", "Found target object in descriptor");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1014 */     Method method = findRMMBMethod(str2, object2, str1, paramArrayOfString);
/*      */ 
/*      */     
/* 1017 */     if (method != null) {
/* 1018 */       object3 = this;
/*      */     } else {
/* 1020 */       Class<?> clazz; if (bool) {
/* 1021 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1022 */             .getName(), "invoke(String, Object[], String[])", "looking for method in managedResource class");
/*      */       }
/*      */       
/* 1025 */       if (object2 != null) {
/* 1026 */         object3 = object2;
/*      */       } else {
/* 1028 */         object3 = this.managedResource;
/* 1029 */         if (object3 == null) {
/* 1030 */           final String className = "managedResource for invoke " + paramString + " is null";
/*      */ 
/*      */           
/* 1033 */           ServiceNotFoundException serviceNotFoundException = new ServiceNotFoundException(str);
/* 1034 */           throw new MBeanException(serviceNotFoundException);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1040 */       if (str1 != null) {
/*      */         try {
/* 1042 */           AccessControlContext accessControlContext = AccessController.getContext();
/* 1043 */           final Object obj = object3;
/* 1044 */           final String className = str1;
/* 1045 */           final ClassNotFoundException[] caughtException = new ClassNotFoundException[1];
/*      */           
/* 1047 */           clazz = (Class)javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction<Class<?>>()
/*      */               {
/*      */                 public Class<?> run()
/*      */                 {
/*      */                   try {
/* 1052 */                     ReflectUtil.checkPackageAccess(className);
/*      */                     
/* 1054 */                     ClassLoader classLoader = obj.getClass().getClassLoader();
/* 1055 */                     return Class.forName(className, false, classLoader);
/*      */                   }
/* 1057 */                   catch (ClassNotFoundException classNotFoundException) {
/* 1058 */                     caughtException[0] = classNotFoundException;
/*      */                     
/* 1060 */                     return null;
/*      */                   } 
/*      */                 }
/*      */               }accessControlContext, this.acc);
/* 1064 */           if (arrayOfClassNotFoundException[0] != null) {
/* 1065 */             throw arrayOfClassNotFoundException[0];
/*      */           }
/* 1067 */         } catch (ClassNotFoundException classNotFoundException) {
/* 1068 */           final String className = "class for invoke " + paramString + " not found";
/*      */           
/* 1070 */           throw new ReflectionException(classNotFoundException, str);
/*      */         } 
/*      */       } else {
/* 1073 */         clazz = object3.getClass();
/*      */       } 
/* 1075 */       method = resolveMethod(clazz, str2, paramArrayOfString);
/*      */     } 
/*      */     
/* 1078 */     if (bool) {
/* 1079 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1080 */           .getName(), "invoke(String, Object[], String[])", "found " + str2 + ", now invoking");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1085 */     Object object4 = invokeMethod(paramString, method, object3, paramArrayOfObject);
/*      */     
/* 1087 */     if (bool) {
/* 1088 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1089 */           .getName(), "invoke(String, Object[], String[])", "successfully invoked method");
/*      */     }
/*      */ 
/*      */     
/* 1093 */     if (object4 != null) {
/* 1094 */       cacheResult(modelMBeanOperationInfo, descriptor, object4);
/*      */     }
/* 1096 */     return object4;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Method resolveMethod(Class<?> paramClass, String paramString, final String[] sig) throws ReflectionException {
/*      */     final Class[] argClasses;
/* 1103 */     final boolean tracing = JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER);
/*      */     
/* 1105 */     if (bool) {
/* 1106 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1107 */           .getName(), "resolveMethod", "resolving " + paramClass
/* 1108 */           .getName() + "." + paramString);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1113 */     if (sig == null) {
/* 1114 */       arrayOfClass = null;
/*      */     } else {
/* 1116 */       AccessControlContext accessControlContext = AccessController.getContext();
/* 1117 */       final ReflectionException[] caughtException = new ReflectionException[1];
/* 1118 */       final ClassLoader targetClassLoader = paramClass.getClassLoader();
/* 1119 */       arrayOfClass = new Class[sig.length];
/*      */       
/* 1121 */       javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction<Void>()
/*      */           {
/*      */             public Void run()
/*      */             {
/* 1125 */               for (byte b = 0; b < sig.length; b++) {
/* 1126 */                 if (tracing) {
/* 1127 */                   JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1128 */                       .getName(), "resolveMethod", "resolve type " + sig[b]);
/*      */                 }
/*      */                 
/* 1131 */                 argClasses[b] = (Class)RequiredModelMBean.primitiveClassMap.get(sig[b]);
/* 1132 */                 if (argClasses[b] == null) {
/*      */                   try {
/* 1134 */                     ReflectUtil.checkPackageAccess(sig[b]);
/* 1135 */                     argClasses[b] = 
/* 1136 */                       Class.forName(sig[b], false, targetClassLoader);
/* 1137 */                   } catch (ClassNotFoundException classNotFoundException) {
/* 1138 */                     if (tracing) {
/* 1139 */                       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1140 */                           .getName(), "resolveMethod", "class not found");
/*      */                     }
/*      */ 
/*      */ 
/*      */                     
/* 1145 */                     caughtException[0] = new ReflectionException(classNotFoundException, "Parameter class not found");
/*      */                   } 
/*      */                 }
/*      */               } 
/* 1149 */               return null;
/*      */             }
/*      */           }accessControlContext, this.acc);
/*      */       
/* 1153 */       if (arrayOfReflectionException[0] != null) {
/* 1154 */         throw arrayOfReflectionException[0];
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/* 1159 */       return paramClass.getMethod(paramString, arrayOfClass);
/* 1160 */     } catch (NoSuchMethodException noSuchMethodException) {
/*      */       
/* 1162 */       String str = "Target method not found: " + paramClass.getName() + "." + paramString;
/*      */       
/* 1164 */       throw new ReflectionException(noSuchMethodException, str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1170 */   private static final Class<?>[] primitiveClasses = new Class[] { int.class, long.class, boolean.class, double.class, float.class, short.class, byte.class, char.class };
/*      */ 
/*      */ 
/*      */   
/* 1174 */   private static final Map<String, Class<?>> primitiveClassMap = new HashMap<>(); private static Set<String> rmmbMethodNames;
/*      */   
/*      */   static {
/* 1177 */     for (byte b = 0; b < primitiveClasses.length; b++) {
/* 1178 */       Class<?> clazz = primitiveClasses[b];
/* 1179 */       primitiveClassMap.put(clazz.getName(), clazz);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Method findRMMBMethod(String paramString1, Object paramObject, String paramString2, String[] paramArrayOfString) {
/*      */     Class<?> clazz1;
/* 1190 */     boolean bool = JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER);
/*      */     
/* 1192 */     if (bool) {
/* 1193 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1194 */           .getName(), "invoke(String, Object[], String[])", "looking for method in RequiredModelMBean class");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1199 */     if (!isRMMBMethodName(paramString1))
/* 1200 */       return null; 
/* 1201 */     if (paramObject != null)
/* 1202 */       return null; 
/* 1203 */     final Class<RequiredModelMBean> rmmbClass = RequiredModelMBean.class;
/*      */     
/* 1205 */     if (paramString2 == null) {
/* 1206 */       clazz1 = clazz;
/*      */     } else {
/* 1208 */       AccessControlContext accessControlContext = AccessController.getContext();
/* 1209 */       final String className = paramString2;
/* 1210 */       clazz1 = (Class)javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction<Class<?>>()
/*      */           {
/*      */             public Class<?> run()
/*      */             {
/*      */               try {
/* 1215 */                 ReflectUtil.checkPackageAccess(className);
/*      */                 
/* 1217 */                 ClassLoader classLoader = rmmbClass.getClassLoader();
/* 1218 */                 Class<?> clazz = Class.forName(className, false, classLoader);
/*      */                 
/* 1220 */                 if (!rmmbClass.isAssignableFrom(clazz))
/* 1221 */                   return null; 
/* 1222 */                 return clazz;
/* 1223 */               } catch (ClassNotFoundException classNotFoundException) {
/* 1224 */                 return null;
/*      */               } 
/*      */             }
/*      */           }accessControlContext, this.acc);
/*      */     } 
/*      */     try {
/* 1230 */       return (clazz1 != null) ? resolveMethod(clazz1, paramString1, paramArrayOfString) : null;
/* 1231 */     } catch (ReflectionException reflectionException) {
/* 1232 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object invokeMethod(String paramString, final Method method, final Object targetObject, final Object[] opArgs) throws MBeanException, ReflectionException {
/*      */     try {
/* 1244 */       final Throwable[] caughtException = new Throwable[1];
/* 1245 */       AccessControlContext accessControlContext = AccessController.getContext();
/* 1246 */       Object object = javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction()
/*      */           {
/*      */             public Object run()
/*      */             {
/*      */               try {
/* 1251 */                 ReflectUtil.checkPackageAccess(method.getDeclaringClass());
/* 1252 */                 return MethodUtil.invoke(method, targetObject, opArgs);
/* 1253 */               } catch (InvocationTargetException invocationTargetException) {
/* 1254 */                 caughtException[0] = invocationTargetException;
/* 1255 */               } catch (IllegalAccessException illegalAccessException) {
/* 1256 */                 caughtException[0] = illegalAccessException;
/*      */               } 
/* 1258 */               return null;
/*      */             }
/*      */           }accessControlContext, this.acc);
/* 1261 */       if (arrayOfThrowable[0] != null) {
/* 1262 */         if (arrayOfThrowable[0] instanceof Exception)
/* 1263 */           throw (Exception)arrayOfThrowable[0]; 
/* 1264 */         if (arrayOfThrowable[0] instanceof Error) {
/* 1265 */           throw (Error)arrayOfThrowable[0];
/*      */         }
/*      */       } 
/* 1268 */       return object;
/* 1269 */     } catch (RuntimeErrorException runtimeErrorException) {
/* 1270 */       throw new RuntimeOperationsException(runtimeErrorException, "RuntimeException occurred in RequiredModelMBean while trying to invoke operation " + paramString);
/*      */     
/*      */     }
/* 1273 */     catch (RuntimeException runtimeException) {
/* 1274 */       throw new RuntimeOperationsException(runtimeException, "RuntimeException occurred in RequiredModelMBean while trying to invoke operation " + paramString);
/*      */     
/*      */     }
/* 1277 */     catch (IllegalAccessException illegalAccessException) {
/* 1278 */       throw new ReflectionException(illegalAccessException, "IllegalAccessException occurred in RequiredModelMBean while trying to invoke operation " + paramString);
/*      */ 
/*      */     
/*      */     }
/* 1282 */     catch (InvocationTargetException invocationTargetException) {
/* 1283 */       Throwable throwable = invocationTargetException.getTargetException();
/* 1284 */       if (throwable instanceof RuntimeException) {
/* 1285 */         throw new MBeanException((RuntimeException)throwable, "RuntimeException thrown in RequiredModelMBean while trying to invoke operation " + paramString);
/*      */       }
/*      */       
/* 1288 */       if (throwable instanceof Error) {
/* 1289 */         throw new RuntimeErrorException((Error)throwable, "Error occurred in RequiredModelMBean while trying to invoke operation " + paramString);
/*      */       }
/*      */       
/* 1292 */       if (throwable instanceof ReflectionException) {
/* 1293 */         throw (ReflectionException)throwable;
/*      */       }
/* 1295 */       throw new MBeanException((Exception)throwable, "Exception thrown in RequiredModelMBean while trying to invoke operation " + paramString);
/*      */ 
/*      */     
/*      */     }
/* 1299 */     catch (Error error) {
/* 1300 */       throw new RuntimeErrorException(error, "Error occurred in RequiredModelMBean while trying to invoke operation " + paramString);
/*      */     
/*      */     }
/* 1303 */     catch (Exception exception) {
/* 1304 */       throw new ReflectionException(exception, "Exception occurred in RequiredModelMBean while trying to invoke operation " + paramString);
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
/*      */   private void cacheResult(ModelMBeanOperationInfo paramModelMBeanOperationInfo, Descriptor paramDescriptor, Object paramObject) throws MBeanException {
/*      */     String str;
/* 1321 */     Descriptor descriptor = this.modelMBeanInfo.getMBeanDescriptor();
/*      */ 
/*      */     
/* 1324 */     Object object = paramDescriptor.getFieldValue("currencyTimeLimit");
/*      */     
/* 1326 */     if (object != null) {
/* 1327 */       str = object.toString();
/*      */     } else {
/* 1329 */       str = null;
/*      */     } 
/* 1331 */     if (str == null && descriptor != null) {
/*      */       
/* 1333 */       object = descriptor.getFieldValue("currencyTimeLimit");
/* 1334 */       if (object != null) {
/* 1335 */         str = object.toString();
/*      */       } else {
/* 1337 */         str = null;
/*      */       } 
/*      */     } 
/* 1340 */     if (str != null && !str.equals("-1")) {
/* 1341 */       paramDescriptor.setField("value", paramObject);
/* 1342 */       paramDescriptor.setField("lastUpdatedTimeStamp", 
/* 1343 */           String.valueOf((new Date()).getTime()));
/*      */ 
/*      */       
/* 1346 */       this.modelMBeanInfo.setDescriptor(paramDescriptor, "operation");
/*      */       
/* 1348 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 1349 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1350 */             .getName(), "invoke(String,Object[],Object[])", "new descriptor is " + paramDescriptor);
/*      */       }
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
/*      */   private static synchronized boolean isRMMBMethodName(String paramString) {
/* 1373 */     if (rmmbMethodNames == null) {
/*      */       try {
/* 1375 */         HashSet<String> hashSet = new HashSet();
/* 1376 */         Method[] arrayOfMethod = RequiredModelMBean.class.getMethods();
/* 1377 */         for (byte b = 0; b < arrayOfMethod.length; b++)
/* 1378 */           hashSet.add(arrayOfMethod[b].getName()); 
/* 1379 */         rmmbMethodNames = hashSet;
/* 1380 */       } catch (Exception exception) {
/* 1381 */         return true;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1386 */     return rmmbMethodNames.contains(paramString);
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
/*      */   public Object getAttribute(String paramString) throws AttributeNotFoundException, MBeanException, ReflectionException {
/*      */     Object object;
/* 1498 */     if (paramString == null) {
/* 1499 */       throw new RuntimeOperationsException(new IllegalArgumentException("attributeName must not be null"), "Exception occurred trying to get attribute of a RequiredModelMBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1504 */     boolean bool = JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER);
/* 1505 */     if (bool) {
/* 1506 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1507 */           .getName(), "getAttribute(String)", "Entry with " + paramString);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1515 */       if (this.modelMBeanInfo == null) {
/* 1516 */         throw new AttributeNotFoundException("getAttribute failed: ModelMBeanInfo not found for " + paramString);
/*      */       }
/*      */ 
/*      */       
/* 1520 */       ModelMBeanAttributeInfo modelMBeanAttributeInfo = this.modelMBeanInfo.getAttribute(paramString);
/* 1521 */       Descriptor descriptor1 = this.modelMBeanInfo.getMBeanDescriptor();
/*      */       
/* 1523 */       if (modelMBeanAttributeInfo == null) {
/* 1524 */         throw new AttributeNotFoundException("getAttribute failed: ModelMBeanAttributeInfo not found for " + paramString);
/*      */       }
/*      */       
/* 1527 */       Descriptor descriptor2 = modelMBeanAttributeInfo.getDescriptor();
/* 1528 */       if (descriptor2 != null) {
/* 1529 */         if (!modelMBeanAttributeInfo.isReadable()) {
/* 1530 */           throw new AttributeNotFoundException("getAttribute failed: " + paramString + " is not readable ");
/*      */         }
/*      */ 
/*      */         
/* 1534 */         object = resolveForCacheValue(descriptor2);
/*      */ 
/*      */         
/* 1537 */         if (bool) {
/* 1538 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1539 */               .getName(), "getAttribute(String)", "*** cached value is " + object);
/*      */         }
/*      */ 
/*      */         
/* 1543 */         if (object == null) {
/*      */           
/* 1545 */           if (bool) {
/* 1546 */             JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1547 */                 .getName(), "getAttribute(String)", "**** cached value is null - getting getMethod");
/*      */           }
/*      */ 
/*      */           
/* 1551 */           String str1 = (String)descriptor2.getFieldValue("getMethod");
/*      */           
/* 1553 */           if (str1 != null) {
/*      */             
/* 1555 */             if (bool) {
/* 1556 */               JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1557 */                   .getName(), "getAttribute(String)", "invoking a getMethod for " + paramString);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1562 */             Object object1 = invoke(str1, new Object[0], new String[0]);
/*      */ 
/*      */             
/* 1565 */             if (object1 != null) {
/*      */               String str2;
/* 1567 */               if (bool) {
/* 1568 */                 JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1569 */                     .getName(), "getAttribute(String)", "got a non-null response from getMethod\n");
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 1574 */               object = object1;
/*      */ 
/*      */ 
/*      */               
/* 1578 */               Object object2 = descriptor2.getFieldValue("currencyTimeLimit");
/*      */ 
/*      */               
/* 1581 */               if (object2 != null) { str2 = object2.toString(); }
/* 1582 */               else { str2 = null; }
/*      */               
/* 1584 */               if (str2 == null && descriptor1 != null) {
/*      */                 
/* 1586 */                 object2 = descriptor1.getFieldValue("currencyTimeLimit");
/* 1587 */                 if (object2 != null) { str2 = object2.toString(); }
/* 1588 */                 else { str2 = null; }
/*      */               
/*      */               } 
/* 1591 */               if (str2 != null && !str2.equals("-1")) {
/* 1592 */                 if (bool) {
/* 1593 */                   JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1594 */                       .getName(), "getAttribute(String)", "setting cached value and lastUpdatedTime in descriptor");
/*      */                 }
/*      */ 
/*      */ 
/*      */                 
/* 1599 */                 descriptor2.setField("value", object);
/* 1600 */                 String str3 = String.valueOf((new Date())
/* 1601 */                     .getTime());
/* 1602 */                 descriptor2.setField("lastUpdatedTimeStamp", str3);
/*      */                 
/* 1604 */                 modelMBeanAttributeInfo.setDescriptor(descriptor2);
/* 1605 */                 this.modelMBeanInfo.setDescriptor(descriptor2, "attribute");
/*      */                 
/* 1607 */                 if (bool) {
/* 1608 */                   JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1609 */                       .getName(), "getAttribute(String)", "new descriptor is " + descriptor2);
/*      */                   
/* 1611 */                   JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1612 */                       .getName(), "getAttribute(String)", "AttributeInfo descriptor is " + modelMBeanAttributeInfo
/*      */                       
/* 1614 */                       .getDescriptor());
/*      */ 
/*      */                   
/* 1617 */                   String str4 = this.modelMBeanInfo.getDescriptor(paramString, "attribute").toString();
/* 1618 */                   JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1619 */                       .getName(), "getAttribute(String)", "modelMBeanInfo: AttributeInfo descriptor is " + str4);
/*      */                 }
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 1627 */               if (bool) {
/* 1628 */                 JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1629 */                     .getName(), "getAttribute(String)", "got a null response from getMethod\n");
/*      */               }
/*      */               
/* 1632 */               object = null;
/*      */             } 
/*      */           } else {
/*      */             
/* 1636 */             String str2 = "";
/* 1637 */             object = descriptor2.getFieldValue("value");
/* 1638 */             if (object == null) {
/* 1639 */               str2 = "default ";
/* 1640 */               object = descriptor2.getFieldValue("default");
/*      */             } 
/* 1642 */             if (bool) {
/* 1643 */               JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1644 */                   .getName(), "getAttribute(String)", "could not find getMethod for " + paramString + ", returning descriptor " + str2 + "value");
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1653 */         final String respType = modelMBeanAttributeInfo.getType();
/* 1654 */         if (object != null) {
/* 1655 */           String str1 = object.getClass().getName();
/* 1656 */           if (!str.equals(str1)) {
/* 1657 */             boolean bool1 = false;
/* 1658 */             boolean bool2 = false;
/* 1659 */             boolean bool3 = false; byte b;
/* 1660 */             for (b = 0; b < primitiveTypes.length; b++) {
/* 1661 */               if (str.equals(primitiveTypes[b])) {
/* 1662 */                 bool2 = true;
/* 1663 */                 if (str1.equals(primitiveWrappers[b]))
/* 1664 */                   bool3 = true; 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1668 */             if (bool2) {
/*      */               
/* 1670 */               if (!bool3) {
/* 1671 */                 bool1 = true;
/*      */               }
/*      */             } else {
/*      */               
/*      */               try {
/* 1676 */                 final Class<?> respClass = object.getClass();
/* 1677 */                 final Exception[] caughException = new Exception[1];
/*      */                 
/* 1679 */                 AccessControlContext accessControlContext = AccessController.getContext();
/*      */                 
/* 1681 */                 Class clazz1 = (Class)javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction<Class<?>>()
/*      */                     {
/*      */                       public Class<?> run()
/*      */                       {
/*      */                         try {
/* 1686 */                           ReflectUtil.checkPackageAccess(respType);
/*      */                           
/* 1688 */                           ClassLoader classLoader = respClass.getClassLoader();
/* 1689 */                           return Class.forName(respType, true, classLoader);
/* 1690 */                         } catch (Exception exception) {
/* 1691 */                           caughException[0] = exception;
/*      */                           
/* 1693 */                           return null;
/*      */                         } 
/*      */                       }
/*      */                     }accessControlContext, this.acc);
/* 1697 */                 if (arrayOfException[0] != null) {
/* 1698 */                   throw arrayOfException[0];
/*      */                 }
/*      */                 
/* 1701 */                 boolean bool4 = clazz1.isInstance(object);
/* 1702 */               } catch (Exception exception) {
/* 1703 */                 b = 0;
/*      */                 
/* 1705 */                 if (bool) {
/* 1706 */                   JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1707 */                       .getName(), "getAttribute(String)", "Exception: ", exception);
/*      */                 }
/*      */               } 
/*      */               
/* 1711 */               if (b == 0)
/* 1712 */                 bool1 = true; 
/*      */             } 
/* 1714 */             if (bool1) {
/* 1715 */               if (bool) {
/* 1716 */                 JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1717 */                     .getName(), "getAttribute(String)", "Wrong response type '" + str + "'");
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 1722 */               throw new MBeanException(new InvalidAttributeValueException("Wrong value type received for get attribute"), "An exception occurred while trying to get an attribute value through a RequiredModelMBean");
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1731 */         if (bool) {
/* 1732 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1733 */               .getName(), "getAttribute(String)", "getMethod failed " + paramString + " not in attributeDescriptor\n");
/*      */         }
/*      */ 
/*      */         
/* 1737 */         throw new MBeanException(new InvalidAttributeValueException("Unable to resolve attribute value, no getMethod defined in descriptor for attribute"), "An exception occurred while trying to get an attribute value through a RequiredModelMBean");
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1745 */     catch (MBeanException mBeanException) {
/* 1746 */       throw mBeanException;
/* 1747 */     } catch (AttributeNotFoundException attributeNotFoundException) {
/* 1748 */       throw attributeNotFoundException;
/* 1749 */     } catch (Exception exception) {
/* 1750 */       if (bool) {
/* 1751 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1752 */             .getName(), "getAttribute(String)", "getMethod failed with " + exception
/* 1753 */             .getMessage() + " exception type " + exception
/* 1754 */             .getClass().toString());
/*      */       }
/* 1756 */       throw new MBeanException(exception, "An exception occurred while trying to get an attribute value: " + exception
/* 1757 */           .getMessage());
/*      */     } 
/*      */     
/* 1760 */     if (bool) {
/* 1761 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1762 */           .getName(), "getAttribute(String)", "Exit");
/*      */     }
/*      */     
/* 1765 */     return object;
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
/*      */   public AttributeList getAttributes(String[] paramArrayOfString) {
/* 1785 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 1786 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1787 */           .getName(), "getAttributes(String[])", "Entry");
/*      */     }
/*      */ 
/*      */     
/* 1791 */     if (paramArrayOfString == null) {
/* 1792 */       throw new RuntimeOperationsException(new IllegalArgumentException("attributeNames must not be null"), "Exception occurred trying to get attributes of a RequiredModelMBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1797 */     AttributeList attributeList = new AttributeList();
/* 1798 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*      */       try {
/* 1800 */         attributeList.add(new Attribute(paramArrayOfString[b], 
/* 1801 */               getAttribute(paramArrayOfString[b])));
/* 1802 */       } catch (Exception exception) {
/*      */ 
/*      */         
/* 1805 */         if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 1806 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1807 */               .getName(), "getAttributes(String[])", "Failed to get \"" + paramArrayOfString[b] + "\": ", exception);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1814 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 1815 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1816 */           .getName(), "getAttributes(String[])", "Exit");
/*      */     }
/*      */ 
/*      */     
/* 1820 */     return attributeList;
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
/*      */   public void setAttribute(Attribute paramAttribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
/* 1902 */     boolean bool = JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER);
/* 1903 */     if (bool) {
/* 1904 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1905 */           .getName(), "setAttribute()", "Entry");
/*      */     }
/*      */ 
/*      */     
/* 1909 */     if (paramAttribute == null) {
/* 1910 */       throw new RuntimeOperationsException(new IllegalArgumentException("attribute must not be null"), "Exception occurred trying to set an attribute of a RequiredModelMBean");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1920 */     String str = paramAttribute.getName();
/* 1921 */     Object object = paramAttribute.getValue();
/* 1922 */     boolean bool1 = false;
/*      */ 
/*      */     
/* 1925 */     ModelMBeanAttributeInfo modelMBeanAttributeInfo = this.modelMBeanInfo.getAttribute(str);
/*      */     
/* 1927 */     if (modelMBeanAttributeInfo == null) {
/* 1928 */       throw new AttributeNotFoundException("setAttribute failed: " + str + " is not found ");
/*      */     }
/*      */     
/* 1931 */     Descriptor descriptor1 = this.modelMBeanInfo.getMBeanDescriptor();
/* 1932 */     Descriptor descriptor2 = modelMBeanAttributeInfo.getDescriptor();
/*      */     
/* 1934 */     if (descriptor2 != null) {
/* 1935 */       String str4; if (!modelMBeanAttributeInfo.isWritable()) {
/* 1936 */         throw new AttributeNotFoundException("setAttribute failed: " + str + " is not writable ");
/*      */       }
/*      */ 
/*      */       
/* 1940 */       String str1 = (String)descriptor2.getFieldValue("setMethod");
/*      */       
/* 1942 */       String str2 = (String)descriptor2.getFieldValue("getMethod");
/*      */       
/* 1944 */       String str3 = modelMBeanAttributeInfo.getType();
/* 1945 */       Object object1 = "Unknown";
/*      */       
/*      */       try {
/* 1948 */         object1 = getAttribute(str);
/* 1949 */       } catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */       
/* 1953 */       Attribute attribute = new Attribute(str, object1);
/*      */ 
/*      */       
/* 1956 */       if (str1 == null) {
/* 1957 */         if (object != null) {
/*      */           try {
/* 1959 */             Class<?> clazz = loadClass(str3);
/* 1960 */             if (!clazz.isInstance(object)) throw new InvalidAttributeValueException(clazz
/* 1961 */                   .getName() + " expected, " + object
/*      */                   
/* 1963 */                   .getClass().getName() + " received.");
/*      */           
/* 1965 */           } catch (ClassNotFoundException classNotFoundException) {
/* 1966 */             if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 1967 */               JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 1968 */                   .getName(), "setAttribute(Attribute)", "Class " + str3 + " for attribute " + str + " not found: ", classNotFoundException);
/*      */             }
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1975 */         bool1 = true;
/*      */       } else {
/* 1977 */         invoke(str1, new Object[] { object }, new String[] { str3 });
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1983 */       Object object2 = descriptor2.getFieldValue("currencyTimeLimit");
/*      */       
/* 1985 */       if (object2 != null) { str4 = object2.toString(); }
/* 1986 */       else { str4 = null; }
/*      */       
/* 1988 */       if (str4 == null && descriptor1 != null) {
/* 1989 */         object2 = descriptor1.getFieldValue("currencyTimeLimit");
/* 1990 */         if (object2 != null) { str4 = object2.toString(); }
/* 1991 */         else { str4 = null; }
/*      */       
/*      */       } 
/* 1994 */       boolean bool2 = (str4 != null && !str4.equals("-1")) ? true : false;
/*      */       
/* 1996 */       if (str1 == null && !bool2 && str2 != null) {
/* 1997 */         throw new MBeanException(new ServiceNotFoundException("No setMethod field is defined in the descriptor for " + str + " attribute and caching is not enabled for it"));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2002 */       if (bool2 || bool1) {
/* 2003 */         if (bool) {
/* 2004 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2005 */               .getName(), "setAttribute(Attribute)", "setting cached value of " + str + " to " + object);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2011 */         descriptor2.setField("value", object);
/*      */         
/* 2013 */         if (bool2) {
/* 2014 */           String str5 = String.valueOf((new Date())
/* 2015 */               .getTime());
/*      */           
/* 2017 */           descriptor2.setField("lastUpdatedTimeStamp", str5);
/*      */         } 
/*      */         
/* 2020 */         modelMBeanAttributeInfo.setDescriptor(descriptor2);
/*      */         
/* 2022 */         this.modelMBeanInfo.setDescriptor(descriptor2, "attribute");
/* 2023 */         if (bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2029 */           StringBuilder stringBuilder = (new StringBuilder()).append("new descriptor is ").append(descriptor2).append(". AttributeInfo descriptor is ").append(modelMBeanAttributeInfo.getDescriptor()).append(". AttributeInfo descriptor is ").append(this.modelMBeanInfo.getDescriptor(str, "attribute"));
/* 2030 */           JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2031 */               .getName(), "setAttribute(Attribute)", stringBuilder
/* 2032 */               .toString());
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2037 */       if (bool) {
/* 2038 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2039 */             .getName(), "setAttribute(Attribute)", "sending sendAttributeNotification");
/*      */       }
/*      */       
/* 2042 */       sendAttributeChangeNotification(attribute, paramAttribute);
/*      */     }
/*      */     else {
/*      */       
/* 2046 */       if (bool) {
/* 2047 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2048 */             .getName(), "setAttribute(Attribute)", "setMethod failed " + str + " not in attributeDescriptor\n");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2053 */       throw new InvalidAttributeValueException("Unable to resolve attribute value, no defined in descriptor for attribute");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2058 */     if (bool) {
/* 2059 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2060 */           .getName(), "setAttribute(Attribute)", "Exit");
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
/*      */   public AttributeList setAttributes(AttributeList paramAttributeList) {
/* 2084 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2085 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2086 */           .getName(), "setAttribute(Attribute)", "Entry");
/*      */     }
/*      */ 
/*      */     
/* 2090 */     if (paramAttributeList == null) {
/* 2091 */       throw new RuntimeOperationsException(new IllegalArgumentException("attributes must not be null"), "Exception occurred trying to set attributes of a RequiredModelMBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2096 */     AttributeList attributeList = new AttributeList();
/*      */ 
/*      */     
/* 2099 */     for (Attribute attribute : paramAttributeList.asList()) {
/*      */       try {
/* 2101 */         setAttribute(attribute);
/* 2102 */         attributeList.add(attribute);
/* 2103 */       } catch (Exception exception) {
/* 2104 */         attributeList.remove(attribute);
/*      */       } 
/*      */     } 
/*      */     
/* 2108 */     return attributeList;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private ModelMBeanInfo createDefaultModelMBeanInfo() {
/* 2114 */     return new ModelMBeanInfoSupport(getClass().getName(), "Default ModelMBean", null, null, null, null);
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
/*      */   private synchronized void writeToLog(String paramString1, String paramString2) throws Exception {
/* 2126 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2127 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2128 */           .getName(), "writeToLog(String, String)", "Notification Logging to " + paramString1 + ": " + paramString2);
/*      */     }
/*      */ 
/*      */     
/* 2132 */     if (paramString1 == null || paramString2 == null) {
/* 2133 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2134 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2135 */             .getName(), "writeToLog(String, String)", "Bad input parameters, will not log this entry.");
/*      */       }
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2142 */     FileOutputStream fileOutputStream = new FileOutputStream(paramString1, true);
/*      */     try {
/* 2144 */       PrintStream printStream = new PrintStream(fileOutputStream);
/* 2145 */       printStream.println(paramString2);
/* 2146 */       printStream.close();
/* 2147 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2148 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2149 */             .getName(), "writeToLog(String, String)", "Successfully opened log " + paramString1);
/*      */       
/*      */       }
/*      */     }
/* 2153 */     catch (Exception exception) {
/* 2154 */       if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2155 */         JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2156 */             .getName(), "writeToLog(String, String)", "Exception " + exception
/*      */             
/* 2158 */             .toString() + " trying to write to the Notification log file " + paramString1);
/*      */       }
/*      */ 
/*      */       
/* 2162 */       throw exception;
/*      */     } finally {
/* 2164 */       fileOutputStream.close();
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
/*      */   
/*      */   public void addNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws IllegalArgumentException {
/* 2194 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2195 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2196 */           .getName(), "addNotificationListener(NotificationListener, NotificationFilter, Object)", "Entry");
/*      */     }
/*      */     
/* 2199 */     if (paramNotificationListener == null) {
/* 2200 */       throw new IllegalArgumentException("notification listener must not be null");
/*      */     }
/*      */     
/* 2203 */     if (this.generalBroadcaster == null) {
/* 2204 */       this.generalBroadcaster = new NotificationBroadcasterSupport();
/*      */     }
/* 2206 */     this.generalBroadcaster.addNotificationListener(paramNotificationListener, paramNotificationFilter, paramObject);
/*      */     
/* 2208 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2209 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2210 */           .getName(), "addNotificationListener(NotificationListener, NotificationFilter, Object)", "NotificationListener added");
/*      */       
/* 2212 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2213 */           .getName(), "addNotificationListener(NotificationListener, NotificationFilter, Object)", "Exit");
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
/*      */   public void removeNotificationListener(NotificationListener paramNotificationListener) throws ListenerNotFoundException {
/* 2231 */     if (paramNotificationListener == null) {
/* 2232 */       throw new ListenerNotFoundException("Notification listener is null");
/*      */     }
/*      */ 
/*      */     
/* 2236 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2237 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2238 */           .getName(), "removeNotificationListener(NotificationListener)", "Entry");
/*      */     }
/*      */     
/* 2241 */     if (this.generalBroadcaster == null) {
/* 2242 */       throw new ListenerNotFoundException("No notification listeners registered");
/*      */     }
/*      */ 
/*      */     
/* 2246 */     this.generalBroadcaster.removeNotificationListener(paramNotificationListener);
/* 2247 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2248 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2249 */           .getName(), "removeNotificationListener(NotificationListener)", "Exit");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws ListenerNotFoundException {
/* 2259 */     if (paramNotificationListener == null) {
/* 2260 */       throw new ListenerNotFoundException("Notification listener is null");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2266 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2267 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2268 */           .getName(), "removeNotificationListener(NotificationListener, NotificationFilter, Object)", "Entry");
/*      */     }
/*      */     
/* 2271 */     if (this.generalBroadcaster == null) {
/* 2272 */       throw new ListenerNotFoundException("No notification listeners registered");
/*      */     }
/*      */ 
/*      */     
/* 2276 */     this.generalBroadcaster.removeNotificationListener(paramNotificationListener, paramNotificationFilter, paramObject);
/*      */ 
/*      */     
/* 2279 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2280 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2281 */           .getName(), "removeNotificationListener(NotificationListener, NotificationFilter, Object)", "Exit");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendNotification(Notification paramNotification) throws MBeanException, RuntimeOperationsException {
/* 2288 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2289 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2290 */           .getName(), "sendNotification(Notification)", "Entry");
/*      */     }
/*      */ 
/*      */     
/* 2294 */     if (paramNotification == null) {
/* 2295 */       throw new RuntimeOperationsException(new IllegalArgumentException("notification object must not be null"), "Exception occurred trying to send a notification from a RequiredModelMBean");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2304 */     Descriptor descriptor1 = this.modelMBeanInfo.getDescriptor(paramNotification.getType(), "notification");
/* 2305 */     Descriptor descriptor2 = this.modelMBeanInfo.getMBeanDescriptor();
/*      */     
/* 2307 */     if (descriptor1 != null) {
/* 2308 */       String str = (String)descriptor1.getFieldValue("log");
/*      */       
/* 2310 */       if (str == null && 
/* 2311 */         descriptor2 != null) {
/* 2312 */         str = (String)descriptor2.getFieldValue("log");
/*      */       }
/*      */       
/* 2315 */       if (str != null && (str
/* 2316 */         .equalsIgnoreCase("t") || str
/* 2317 */         .equalsIgnoreCase("true"))) {
/*      */         
/* 2319 */         String str1 = (String)descriptor1.getFieldValue("logfile");
/* 2320 */         if (str1 == null && 
/* 2321 */           descriptor2 != null) {
/* 2322 */           str1 = (String)descriptor2.getFieldValue("logfile");
/*      */         }
/* 2324 */         if (str1 != null) {
/*      */           try {
/* 2326 */             writeToLog(str1, "LogMsg: " + (new Date(paramNotification
/* 2327 */                   .getTimeStamp())).toString() + " " + paramNotification
/* 2328 */                 .getType() + " " + paramNotification
/* 2329 */                 .getMessage() + " Severity = " + (String)descriptor1
/* 2330 */                 .getFieldValue("severity"));
/* 2331 */           } catch (Exception exception) {
/* 2332 */             if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINE)) {
/* 2333 */               JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINE, RequiredModelMBean.class
/* 2334 */                   .getName(), "sendNotification(Notification)", "Failed to log " + paramNotification
/*      */ 
/*      */                   
/* 2337 */                   .getType() + " notification: ", exception);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 2343 */     if (this.generalBroadcaster != null) {
/* 2344 */       this.generalBroadcaster.sendNotification(paramNotification);
/*      */     }
/*      */     
/* 2347 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2348 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2349 */           .getName(), "sendNotification(Notification)", "sendNotification sent provided notification object");
/*      */ 
/*      */       
/* 2352 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2353 */           .getName(), "sendNotification(Notification)", " Exit");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendNotification(String paramString) throws MBeanException, RuntimeOperationsException {
/* 2362 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2363 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2364 */           .getName(), "sendNotification(String)", "Entry");
/*      */     }
/*      */ 
/*      */     
/* 2368 */     if (paramString == null) {
/* 2369 */       throw new RuntimeOperationsException(new IllegalArgumentException("notification message must not be null"), "Exception occurred trying to send a text notification from a ModelMBean");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2375 */     Notification notification = new Notification("jmx.modelmbean.generic", this, 1L, paramString);
/*      */     
/* 2377 */     sendNotification(notification);
/* 2378 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2379 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2380 */           .getName(), "sendNotification(String)", "Notification sent");
/*      */       
/* 2382 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2383 */           .getName(), "sendNotification(String)", "Exit");
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
/*      */   private static final boolean hasNotification(ModelMBeanInfo paramModelMBeanInfo, String paramString) {
/*      */     try {
/* 2396 */       if (paramModelMBeanInfo == null) return false; 
/* 2397 */       return (paramModelMBeanInfo.getNotification(paramString) != null);
/* 2398 */     } catch (MBeanException mBeanException) {
/* 2399 */       return false;
/* 2400 */     } catch (RuntimeOperationsException runtimeOperationsException) {
/* 2401 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final ModelMBeanNotificationInfo makeGenericInfo() {
/* 2410 */     DescriptorSupport descriptorSupport = new DescriptorSupport(new String[] { "name=GENERIC", "descriptorType=notification", "log=T", "severity=6", "displayName=jmx.modelmbean.generic" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2418 */     return new ModelMBeanNotificationInfo(new String[] { "jmx.modelmbean.generic" }, "GENERIC", "A text notification has been issued by the managed resource", descriptorSupport);
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
/*      */   private static final ModelMBeanNotificationInfo makeAttributeChangeInfo() {
/* 2431 */     DescriptorSupport descriptorSupport = new DescriptorSupport(new String[] { "name=ATTRIBUTE_CHANGE", "descriptorType=notification", "log=T", "severity=6", "displayName=jmx.attribute.change" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2439 */     return new ModelMBeanNotificationInfo(new String[] { "jmx.attribute.change" }, "ATTRIBUTE_CHANGE", "Signifies that an observed MBean attribute value has changed", descriptorSupport);
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
/*      */   public MBeanNotificationInfo[] getNotificationInfo() {
/* 2464 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2465 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2466 */           .getName(), "getNotificationInfo()", "Entry");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2475 */     boolean bool1 = hasNotification(this.modelMBeanInfo, "GENERIC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2481 */     boolean bool2 = hasNotification(this.modelMBeanInfo, "ATTRIBUTE_CHANGE");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2486 */     ModelMBeanNotificationInfo[] arrayOfModelMBeanNotificationInfo1 = (ModelMBeanNotificationInfo[])this.modelMBeanInfo.getNotifications();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2492 */     int i = ((arrayOfModelMBeanNotificationInfo1 == null) ? 0 : arrayOfModelMBeanNotificationInfo1.length) + (bool1 ? 0 : 1) + (bool2 ? 0 : 1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2497 */     ModelMBeanNotificationInfo[] arrayOfModelMBeanNotificationInfo2 = new ModelMBeanNotificationInfo[i];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2506 */     byte b1 = 0;
/* 2507 */     if (!bool1)
/*      */     {
/*      */       
/* 2510 */       arrayOfModelMBeanNotificationInfo2[b1++] = makeGenericInfo();
/*      */     }
/*      */     
/* 2513 */     if (!bool2)
/*      */     {
/*      */       
/* 2516 */       arrayOfModelMBeanNotificationInfo2[b1++] = makeAttributeChangeInfo();
/*      */     }
/*      */ 
/*      */     
/* 2520 */     int j = arrayOfModelMBeanNotificationInfo1.length;
/* 2521 */     byte b2 = b1;
/* 2522 */     for (byte b3 = 0; b3 < j; b3++) {
/* 2523 */       arrayOfModelMBeanNotificationInfo2[b2 + b3] = arrayOfModelMBeanNotificationInfo1[b3];
/*      */     }
/*      */     
/* 2526 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2527 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2528 */           .getName(), "getNotificationInfo()", "Exit");
/*      */     }
/*      */ 
/*      */     
/* 2532 */     return (MBeanNotificationInfo[])arrayOfModelMBeanNotificationInfo2;
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
/*      */   public void addAttributeChangeNotificationListener(NotificationListener paramNotificationListener, String paramString, Object paramObject) throws MBeanException, RuntimeOperationsException, IllegalArgumentException {
/* 2546 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2547 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2548 */           .getName(), "addAttributeChangeNotificationListener(NotificationListener, String, Object)", "Entry");
/*      */     }
/*      */     
/* 2551 */     if (paramNotificationListener == null) {
/* 2552 */       throw new IllegalArgumentException("Listener to be registered must not be null");
/*      */     }
/*      */ 
/*      */     
/* 2556 */     if (this.attributeBroadcaster == null) {
/* 2557 */       this.attributeBroadcaster = new NotificationBroadcasterSupport();
/*      */     }
/* 2559 */     AttributeChangeNotificationFilter attributeChangeNotificationFilter = new AttributeChangeNotificationFilter();
/*      */ 
/*      */     
/* 2562 */     MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = this.modelMBeanInfo.getAttributes();
/* 2563 */     boolean bool = false;
/* 2564 */     if (paramString == null) {
/* 2565 */       if (arrayOfMBeanAttributeInfo != null && arrayOfMBeanAttributeInfo.length > 0) {
/* 2566 */         for (byte b = 0; b < arrayOfMBeanAttributeInfo.length; b++) {
/* 2567 */           attributeChangeNotificationFilter.enableAttribute(arrayOfMBeanAttributeInfo[b].getName());
/*      */         }
/*      */       }
/*      */     } else {
/* 2571 */       if (arrayOfMBeanAttributeInfo != null && arrayOfMBeanAttributeInfo.length > 0) {
/* 2572 */         for (byte b = 0; b < arrayOfMBeanAttributeInfo.length; b++) {
/* 2573 */           if (paramString.equals(arrayOfMBeanAttributeInfo[b].getName())) {
/* 2574 */             bool = true;
/* 2575 */             attributeChangeNotificationFilter.enableAttribute(paramString);
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/* 2580 */       if (!bool) {
/* 2581 */         throw new RuntimeOperationsException(new IllegalArgumentException("The attribute name does not exist"), "Exception occurred trying to add an AttributeChangeNotification listener");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2589 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2590 */       Vector<String> vector = attributeChangeNotificationFilter.getEnabledAttributes();
/*      */ 
/*      */       
/* 2593 */       String str = (vector.size() > 1) ? ("[" + (String)vector.firstElement() + ", ...]") : vector.toString();
/* 2594 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2595 */           .getName(), "addAttributeChangeNotificationListener(NotificationListener, String, Object)", "Set attribute change filter to " + str);
/*      */     } 
/*      */ 
/*      */     
/* 2599 */     this.attributeBroadcaster.addNotificationListener(paramNotificationListener, attributeChangeNotificationFilter, paramObject);
/*      */     
/* 2601 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2602 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2603 */           .getName(), "addAttributeChangeNotificationListener(NotificationListener, String, Object)", "Notification listener added for " + paramString);
/*      */       
/* 2605 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2606 */           .getName(), "addAttributeChangeNotificationListener(NotificationListener, String, Object)", "Exit");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAttributeChangeNotificationListener(NotificationListener paramNotificationListener, String paramString) throws MBeanException, RuntimeOperationsException, ListenerNotFoundException {
/* 2614 */     if (paramNotificationListener == null) throw new ListenerNotFoundException("Notification listener is null");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2620 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2621 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2622 */           .getName(), "removeAttributeChangeNotificationListener(NotificationListener, String)", "Entry");
/*      */     }
/*      */ 
/*      */     
/* 2626 */     if (this.attributeBroadcaster == null) {
/* 2627 */       throw new ListenerNotFoundException("No attribute change notification listeners registered");
/*      */     }
/*      */ 
/*      */     
/* 2631 */     MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = this.modelMBeanInfo.getAttributes();
/* 2632 */     boolean bool = false;
/* 2633 */     if (arrayOfMBeanAttributeInfo != null && arrayOfMBeanAttributeInfo.length > 0) {
/* 2634 */       for (byte b = 0; b < arrayOfMBeanAttributeInfo.length; b++) {
/* 2635 */         if (arrayOfMBeanAttributeInfo[b].getName().equals(paramString)) {
/* 2636 */           bool = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 2642 */     if (!bool && paramString != null) {
/* 2643 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid attribute name"), "Exception occurred trying to remove attribute change notification listener");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2654 */     this.attributeBroadcaster.removeNotificationListener(paramNotificationListener);
/*      */     
/* 2656 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2657 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2658 */           .getName(), "removeAttributeChangeNotificationListener(NotificationListener, String)", "Exit");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAttributeChangeNotification(AttributeChangeNotification paramAttributeChangeNotification) throws MBeanException, RuntimeOperationsException {
/* 2668 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2669 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2670 */           .getName(), "sendAttributeChangeNotification(AttributeChangeNotification)", "Entry");
/*      */     }
/*      */     
/* 2673 */     if (paramAttributeChangeNotification == null) {
/* 2674 */       throw new RuntimeOperationsException(new IllegalArgumentException("attribute change notification object must not be null"), "Exception occurred trying to send attribute change notification of a ModelMBean");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2680 */     Object object1 = paramAttributeChangeNotification.getOldValue();
/* 2681 */     Object object2 = paramAttributeChangeNotification.getNewValue();
/*      */     
/* 2683 */     if (object1 == null) object1 = "null"; 
/* 2684 */     if (object2 == null) object2 = "null";
/*      */     
/* 2686 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2687 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2688 */           .getName(), "sendAttributeChangeNotification(AttributeChangeNotification)", "Sending AttributeChangeNotification with " + paramAttributeChangeNotification
/*      */           
/* 2690 */           .getAttributeName() + paramAttributeChangeNotification.getAttributeType() + paramAttributeChangeNotification
/* 2691 */           .getNewValue() + paramAttributeChangeNotification.getOldValue());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2696 */     Descriptor descriptor1 = this.modelMBeanInfo.getDescriptor(paramAttributeChangeNotification.getType(), "notification");
/* 2697 */     Descriptor descriptor2 = this.modelMBeanInfo.getMBeanDescriptor();
/*      */ 
/*      */ 
/*      */     
/* 2701 */     if (descriptor1 != null) {
/* 2702 */       String str = (String)descriptor1.getFieldValue("log");
/* 2703 */       if (str == null && 
/* 2704 */         descriptor2 != null) {
/* 2705 */         str = (String)descriptor2.getFieldValue("log");
/*      */       }
/* 2707 */       if (str != null && (str
/* 2708 */         .equalsIgnoreCase("t") || str
/* 2709 */         .equalsIgnoreCase("true"))) {
/* 2710 */         String str1 = (String)descriptor1.getFieldValue("logfile");
/* 2711 */         if (str1 == null && 
/* 2712 */           descriptor2 != null) {
/* 2713 */           str1 = (String)descriptor2.getFieldValue("logfile");
/*      */         }
/*      */         
/* 2716 */         if (str1 != null) {
/*      */           try {
/* 2718 */             writeToLog(str1, "LogMsg: " + (new Date(paramAttributeChangeNotification
/* 2719 */                   .getTimeStamp())).toString() + " " + paramAttributeChangeNotification
/* 2720 */                 .getType() + " " + paramAttributeChangeNotification
/* 2721 */                 .getMessage() + " Name = " + paramAttributeChangeNotification
/* 2722 */                 .getAttributeName() + " Old value = " + object1 + " New value = " + object2);
/*      */           
/*      */           }
/* 2725 */           catch (Exception exception) {
/* 2726 */             if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINE)) {
/* 2727 */               JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINE, RequiredModelMBean.class
/* 2728 */                   .getName(), "sendAttributeChangeNotification(AttributeChangeNotification)", "Failed to log " + paramAttributeChangeNotification
/* 2729 */                   .getType() + " notification: ", exception);
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */       } 
/* 2735 */     } else if (descriptor2 != null) {
/* 2736 */       String str = (String)descriptor2.getFieldValue("log");
/* 2737 */       if (str != null && (str
/* 2738 */         .equalsIgnoreCase("t") || str
/* 2739 */         .equalsIgnoreCase("true"))) {
/* 2740 */         String str1 = (String)descriptor2.getFieldValue("logfile");
/*      */         
/* 2742 */         if (str1 != null) {
/*      */           try {
/* 2744 */             writeToLog(str1, "LogMsg: " + (new Date(paramAttributeChangeNotification
/* 2745 */                   .getTimeStamp())).toString() + " " + paramAttributeChangeNotification
/* 2746 */                 .getType() + " " + paramAttributeChangeNotification
/* 2747 */                 .getMessage() + " Name = " + paramAttributeChangeNotification
/* 2748 */                 .getAttributeName() + " Old value = " + object1 + " New value = " + object2);
/*      */           
/*      */           }
/* 2751 */           catch (Exception exception) {
/* 2752 */             if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINE)) {
/* 2753 */               JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINE, RequiredModelMBean.class
/* 2754 */                   .getName(), "sendAttributeChangeNotification(AttributeChangeNotification)", "Failed to log " + paramAttributeChangeNotification
/* 2755 */                   .getType() + " notification: ", exception);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2762 */     if (this.attributeBroadcaster != null) {
/* 2763 */       this.attributeBroadcaster.sendNotification(paramAttributeChangeNotification);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2772 */     if (this.generalBroadcaster != null) {
/* 2773 */       this.generalBroadcaster.sendNotification(paramAttributeChangeNotification);
/*      */     }
/*      */     
/* 2776 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2777 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2778 */           .getName(), "sendAttributeChangeNotification(AttributeChangeNotification)", "sent notification");
/*      */       
/* 2780 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2781 */           .getName(), "sendAttributeChangeNotification(AttributeChangeNotification)", "Exit");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAttributeChangeNotification(Attribute paramAttribute1, Attribute paramAttribute2) throws MBeanException, RuntimeOperationsException {
/* 2791 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2792 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2793 */           .getName(), "sendAttributeChangeNotification(Attribute, Attribute)", "Entry");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2798 */     if (paramAttribute1 == null || paramAttribute2 == null) {
/* 2799 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute object must not be null"), "Exception occurred trying to send attribute change notification of a ModelMBean");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2805 */     if (!paramAttribute1.getName().equals(paramAttribute2.getName())) {
/* 2806 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute names are not the same"), "Exception occurred trying to send attribute change notification of a ModelMBean");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2812 */     Object object1 = paramAttribute2.getValue();
/* 2813 */     Object object2 = paramAttribute1.getValue();
/* 2814 */     String str = "unknown";
/* 2815 */     if (object1 != null)
/* 2816 */       str = object1.getClass().getName(); 
/* 2817 */     if (object2 != null) {
/* 2818 */       str = object2.getClass().getName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2828 */     AttributeChangeNotification attributeChangeNotification = new AttributeChangeNotification(this, 1L, (new Date()).getTime(), "AttributeChangeDetected", paramAttribute1.getName(), str, paramAttribute1.getValue(), paramAttribute2.getValue());
/*      */     
/* 2830 */     sendAttributeChangeNotification(attributeChangeNotification);
/*      */     
/* 2832 */     if (JmxProperties.MODELMBEAN_LOGGER.isLoggable(Level.FINER)) {
/* 2833 */       JmxProperties.MODELMBEAN_LOGGER.logp(Level.FINER, RequiredModelMBean.class
/* 2834 */           .getName(), "sendAttributeChangeNotification(Attribute, Attribute)", "Exit");
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
/*      */   protected ClassLoaderRepository getClassLoaderRepository() {
/* 2850 */     return MBeanServerFactory.getClassLoaderRepository(this.server);
/*      */   }
/*      */ 
/*      */   
/*      */   private Class<?> loadClass(final String className) throws ClassNotFoundException {
/* 2855 */     AccessControlContext accessControlContext = AccessController.getContext();
/* 2856 */     final ClassNotFoundException[] caughtException = new ClassNotFoundException[1];
/*      */     
/* 2858 */     Class<?> clazz = (Class)javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction<Class<?>>()
/*      */         {
/*      */           public Class<?> run()
/*      */           {
/*      */             try {
/* 2863 */               ReflectUtil.checkPackageAccess(className);
/* 2864 */               return Class.forName(className);
/* 2865 */             } catch (ClassNotFoundException classNotFoundException) {
/*      */               
/* 2867 */               ClassLoaderRepository classLoaderRepository = RequiredModelMBean.this.getClassLoaderRepository();
/*      */               try {
/* 2869 */                 if (classLoaderRepository == null) throw new ClassNotFoundException(className); 
/* 2870 */                 return classLoaderRepository.loadClass(className);
/* 2871 */               } catch (ClassNotFoundException classNotFoundException1) {
/* 2872 */                 caughtException[0] = classNotFoundException1;
/*      */ 
/*      */                 
/* 2875 */                 return null;
/*      */               } 
/*      */             }  }
/*      */         },  accessControlContext, this.acc);
/* 2879 */     if (arrayOfClassNotFoundException[0] != null) {
/* 2880 */       throw arrayOfClassNotFoundException[0];
/*      */     }
/*      */     
/* 2883 */     return clazz;
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
/*      */ 
/*      */   
/*      */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/* 2927 */     if (paramObjectName == null) throw new NullPointerException("name of RequiredModelMBean to registered is null");
/*      */     
/* 2929 */     this.server = paramMBeanServer;
/* 2930 */     return paramObjectName;
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
/*      */   public void postRegister(Boolean paramBoolean) {
/* 2947 */     this.registered = paramBoolean.booleanValue();
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
/*      */   public void preDeregister() throws Exception {}
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
/*      */   public void postDeregister() {
/* 2977 */     this.registered = false;
/* 2978 */     this.server = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2984 */   private static final String[] primitiveTypes = new String[] { boolean.class
/* 2985 */       .getName(), byte.class
/* 2986 */       .getName(), char.class
/* 2987 */       .getName(), short.class
/* 2988 */       .getName(), int.class
/* 2989 */       .getName(), long.class
/* 2990 */       .getName(), float.class
/* 2991 */       .getName(), double.class
/* 2992 */       .getName(), void.class
/* 2993 */       .getName() };
/*      */   
/* 2995 */   private static final String[] primitiveWrappers = new String[] { Boolean.class
/* 2996 */       .getName(), Byte.class
/* 2997 */       .getName(), Character.class
/* 2998 */       .getName(), Short.class
/* 2999 */       .getName(), Integer.class
/* 3000 */       .getName(), Long.class
/* 3001 */       .getName(), Float.class
/* 3002 */       .getName(), Double.class
/* 3003 */       .getName(), Void.class
/* 3004 */       .getName() };
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/modelmbean/RequiredModelMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */