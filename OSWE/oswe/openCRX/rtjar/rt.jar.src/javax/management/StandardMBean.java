/*      */ package javax.management;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.mbeanserver.DescriptorCache;
/*      */ import com.sun.jmx.mbeanserver.Introspector;
/*      */ import com.sun.jmx.mbeanserver.MBeanSupport;
/*      */ import com.sun.jmx.mbeanserver.MXBeanSupport;
/*      */ import com.sun.jmx.mbeanserver.StandardMBeanSupport;
/*      */ import com.sun.jmx.mbeanserver.Util;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.openmbean.OpenMBeanAttributeInfo;
/*      */ import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
/*      */ import javax.management.openmbean.OpenMBeanConstructorInfoSupport;
/*      */ import javax.management.openmbean.OpenMBeanOperationInfo;
/*      */ import javax.management.openmbean.OpenMBeanOperationInfoSupport;
/*      */ import javax.management.openmbean.OpenMBeanParameterInfo;
/*      */ import javax.management.openmbean.OpenMBeanParameterInfoSupport;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StandardMBean
/*      */   implements DynamicMBean, MBeanRegistration
/*      */ {
/*  129 */   private static final DescriptorCache descriptors = DescriptorCache.getInstance(JMX.proof);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile MBeanSupport<?> mbean;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile MBeanInfo cachedMBeanInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> void construct(T paramT, Class<T> paramClass, boolean paramBoolean1, boolean paramBoolean2) throws NotCompliantMBeanException {
/*  163 */     if (paramT == null)
/*      */     {
/*      */       
/*  166 */       if (paramBoolean1)
/*  167 */       { paramT = Util.cast(this); }
/*  168 */       else { throw new IllegalArgumentException("implementation is null"); }
/*      */        } 
/*  170 */     if (paramBoolean2) {
/*  171 */       if (paramClass == null) {
/*  172 */         paramClass = Util.<Class<T>>cast(Introspector.getMXBeanInterface(paramT
/*  173 */               .getClass()));
/*      */       }
/*  175 */       this.mbean = new MXBeanSupport(paramT, paramClass);
/*      */     } else {
/*  177 */       if (paramClass == null) {
/*  178 */         paramClass = Util.<Class<T>>cast(Introspector.getStandardMBeanInterface(paramT
/*  179 */               .getClass()));
/*      */       }
/*  181 */       this.mbean = new StandardMBeanSupport(paramT, paramClass);
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
/*      */   public <T> StandardMBean(T paramT, Class<T> paramClass) throws NotCompliantMBeanException {
/*  212 */     construct(paramT, paramClass, false, false);
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
/*      */   protected StandardMBean(Class<?> paramClass) throws NotCompliantMBeanException {
/*  232 */     construct(null, paramClass, true, false);
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
/*      */   public <T> StandardMBean(T paramT, Class<T> paramClass, boolean paramBoolean) {
/*      */     try {
/*  269 */       construct(paramT, paramClass, false, paramBoolean);
/*  270 */     } catch (NotCompliantMBeanException notCompliantMBeanException) {
/*  271 */       throw new IllegalArgumentException(notCompliantMBeanException);
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
/*      */   protected StandardMBean(Class<?> paramClass, boolean paramBoolean) {
/*      */     try {
/*  300 */       construct(null, paramClass, true, paramBoolean);
/*  301 */     } catch (NotCompliantMBeanException notCompliantMBeanException) {
/*  302 */       throw new IllegalArgumentException(notCompliantMBeanException);
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
/*      */   public void setImplementation(Object paramObject) throws NotCompliantMBeanException {
/*  327 */     if (paramObject == null) {
/*  328 */       throw new IllegalArgumentException("implementation is null");
/*      */     }
/*  330 */     if (isMXBean()) {
/*  331 */       this
/*  332 */         .mbean = new MXBeanSupport((T)paramObject, Util.<Class<T>>cast(getMBeanInterface()));
/*      */     } else {
/*  334 */       this
/*  335 */         .mbean = new StandardMBeanSupport((T)paramObject, Util.<Class<T>>cast(getMBeanInterface()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getImplementation() {
/*  346 */     return this.mbean.getResource();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Class<?> getMBeanInterface() {
/*  354 */     return this.mbean.getMBeanInterface();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> getImplementationClass() {
/*  362 */     return this.mbean.getResource().getClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getAttribute(String paramString) throws AttributeNotFoundException, MBeanException, ReflectionException {
/*  372 */     return this.mbean.getAttribute(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttribute(Attribute paramAttribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
/*  383 */     this.mbean.setAttribute(paramAttribute);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeList getAttributes(String[] paramArrayOfString) {
/*  390 */     return this.mbean.getAttributes(paramArrayOfString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeList setAttributes(AttributeList paramAttributeList) {
/*  397 */     return this.mbean.setAttributes(paramAttributeList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object invoke(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws MBeanException, ReflectionException {
/*  405 */     return this.mbean.invoke(paramString, paramArrayOfObject, paramArrayOfString);
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
/*      */   public MBeanInfo getMBeanInfo() {
/*      */     try {
/*  432 */       MBeanInfo mBeanInfo = getCachedMBeanInfo();
/*  433 */       if (mBeanInfo != null) return mBeanInfo; 
/*  434 */     } catch (RuntimeException runtimeException) {
/*  435 */       if (JmxProperties.MISC_LOGGER.isLoggable(Level.FINEST)) {
/*  436 */         JmxProperties.MISC_LOGGER.logp(Level.FINEST, MBeanServerFactory.class
/*  437 */             .getName(), "getMBeanInfo", "Failed to get cached MBeanInfo", runtimeException);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  442 */     if (JmxProperties.MISC_LOGGER.isLoggable(Level.FINER)) {
/*  443 */       JmxProperties.MISC_LOGGER.logp(Level.FINER, MBeanServerFactory.class
/*  444 */           .getName(), "getMBeanInfo", "Building MBeanInfo for " + 
/*      */           
/*  446 */           getImplementationClass().getName());
/*      */     }
/*      */     
/*  449 */     MBeanSupport<?> mBeanSupport = this.mbean;
/*  450 */     MBeanInfo mBeanInfo1 = mBeanSupport.getMBeanInfo();
/*  451 */     Object object = mBeanSupport.getResource();
/*      */     
/*  453 */     boolean bool = immutableInfo((Class)getClass());
/*      */     
/*  455 */     String str1 = getClassName(mBeanInfo1);
/*  456 */     String str2 = getDescription(mBeanInfo1);
/*  457 */     MBeanConstructorInfo[] arrayOfMBeanConstructorInfo = getConstructors(mBeanInfo1, object);
/*  458 */     MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = getAttributes(mBeanInfo1);
/*  459 */     MBeanOperationInfo[] arrayOfMBeanOperationInfo = getOperations(mBeanInfo1);
/*  460 */     MBeanNotificationInfo[] arrayOfMBeanNotificationInfo = getNotifications(mBeanInfo1);
/*  461 */     Descriptor descriptor = getDescriptor(mBeanInfo1, bool);
/*      */     
/*  463 */     MBeanInfo mBeanInfo2 = new MBeanInfo(str1, str2, arrayOfMBeanAttributeInfo, arrayOfMBeanConstructorInfo, arrayOfMBeanOperationInfo, arrayOfMBeanNotificationInfo, descriptor);
/*      */     
/*      */     try {
/*  466 */       cacheMBeanInfo(mBeanInfo2);
/*  467 */     } catch (RuntimeException runtimeException) {
/*  468 */       if (JmxProperties.MISC_LOGGER.isLoggable(Level.FINEST)) {
/*  469 */         JmxProperties.MISC_LOGGER.logp(Level.FINEST, MBeanServerFactory.class
/*  470 */             .getName(), "getMBeanInfo", "Failed to cache MBeanInfo", runtimeException);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  475 */     return mBeanInfo2;
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
/*      */   protected String getClassName(MBeanInfo paramMBeanInfo) {
/*  490 */     if (paramMBeanInfo == null) return getImplementationClass().getName(); 
/*  491 */     return paramMBeanInfo.getClassName();
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
/*      */   protected String getDescription(MBeanInfo paramMBeanInfo) {
/*  506 */     if (paramMBeanInfo == null) return null; 
/*  507 */     return paramMBeanInfo.getDescription();
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
/*      */   protected String getDescription(MBeanFeatureInfo paramMBeanFeatureInfo) {
/*  529 */     if (paramMBeanFeatureInfo == null) return null; 
/*  530 */     return paramMBeanFeatureInfo.getDescription();
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
/*      */   protected String getDescription(MBeanAttributeInfo paramMBeanAttributeInfo) {
/*  546 */     return getDescription(paramMBeanAttributeInfo);
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
/*      */   protected String getDescription(MBeanConstructorInfo paramMBeanConstructorInfo) {
/*  563 */     return getDescription(paramMBeanConstructorInfo);
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
/*      */   protected String getDescription(MBeanConstructorInfo paramMBeanConstructorInfo, MBeanParameterInfo paramMBeanParameterInfo, int paramInt) {
/*  585 */     if (paramMBeanParameterInfo == null) return null; 
/*  586 */     return paramMBeanParameterInfo.getDescription();
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
/*      */   protected String getParameterName(MBeanConstructorInfo paramMBeanConstructorInfo, MBeanParameterInfo paramMBeanParameterInfo, int paramInt) {
/*  608 */     if (paramMBeanParameterInfo == null) return null; 
/*  609 */     return paramMBeanParameterInfo.getName();
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
/*      */   protected String getDescription(MBeanOperationInfo paramMBeanOperationInfo) {
/*  625 */     return getDescription(paramMBeanOperationInfo);
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
/*      */   protected int getImpact(MBeanOperationInfo paramMBeanOperationInfo) {
/*  640 */     if (paramMBeanOperationInfo == null) return 3; 
/*  641 */     return paramMBeanOperationInfo.getImpact();
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
/*      */   protected String getParameterName(MBeanOperationInfo paramMBeanOperationInfo, MBeanParameterInfo paramMBeanParameterInfo, int paramInt) {
/*  663 */     if (paramMBeanParameterInfo == null) return null; 
/*  664 */     return paramMBeanParameterInfo.getName();
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
/*      */   protected String getDescription(MBeanOperationInfo paramMBeanOperationInfo, MBeanParameterInfo paramMBeanParameterInfo, int paramInt) {
/*  686 */     if (paramMBeanParameterInfo == null) return null; 
/*  687 */     return paramMBeanParameterInfo.getDescription();
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
/*      */   protected MBeanConstructorInfo[] getConstructors(MBeanConstructorInfo[] paramArrayOfMBeanConstructorInfo, Object paramObject) {
/*  713 */     if (paramArrayOfMBeanConstructorInfo == null) return null; 
/*  714 */     if (paramObject != null && paramObject != this) return null; 
/*  715 */     return paramArrayOfMBeanConstructorInfo;
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
/*      */   MBeanNotificationInfo[] getNotifications(MBeanInfo paramMBeanInfo) {
/*  729 */     return null;
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
/*      */   Descriptor getDescriptor(MBeanInfo paramMBeanInfo, boolean paramBoolean) {
/*      */     ImmutableDescriptor immutableDescriptor;
/*  754 */     if (paramMBeanInfo == null || paramMBeanInfo
/*  755 */       .getDescriptor() == null || (paramMBeanInfo
/*  756 */       .getDescriptor().getFieldNames()).length == 0) {
/*      */       
/*  758 */       String str1 = "interfaceClassName=" + getMBeanInterface().getName();
/*  759 */       String str2 = "immutableInfo=" + paramBoolean;
/*      */       
/*  761 */       immutableDescriptor = new ImmutableDescriptor(new String[] { str1, str2 });
/*  762 */       immutableDescriptor = descriptors.get(immutableDescriptor);
/*      */     } else {
/*  764 */       Descriptor descriptor = paramMBeanInfo.getDescriptor();
/*  765 */       HashMap<Object, Object> hashMap = new HashMap<>();
/*  766 */       for (String str : descriptor.getFieldNames()) {
/*  767 */         if (str.equals("immutableInfo")) {
/*      */ 
/*      */ 
/*      */           
/*  771 */           hashMap.put(str, Boolean.toString(paramBoolean));
/*      */         } else {
/*  773 */           hashMap.put(str, descriptor.getFieldValue(str));
/*      */         } 
/*      */       } 
/*  776 */       immutableDescriptor = new ImmutableDescriptor((Map)hashMap);
/*      */     } 
/*  778 */     return immutableDescriptor;
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
/*      */   protected MBeanInfo getCachedMBeanInfo() {
/*  794 */     return this.cachedMBeanInfo;
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
/*      */   protected void cacheMBeanInfo(MBeanInfo paramMBeanInfo) {
/*  815 */     this.cachedMBeanInfo = paramMBeanInfo;
/*      */   }
/*      */   
/*      */   private boolean isMXBean() {
/*  819 */     return this.mbean.isMXBean();
/*      */   }
/*      */   
/*      */   private static <T> boolean identicalArrays(T[] paramArrayOfT1, T[] paramArrayOfT2) {
/*  823 */     if (paramArrayOfT1 == paramArrayOfT2)
/*  824 */       return true; 
/*  825 */     if (paramArrayOfT1 == null || paramArrayOfT2 == null || paramArrayOfT1.length != paramArrayOfT2.length)
/*  826 */       return false; 
/*  827 */     for (byte b = 0; b < paramArrayOfT1.length; b++) {
/*  828 */       if (paramArrayOfT1[b] != paramArrayOfT2[b])
/*  829 */         return false; 
/*      */     } 
/*  831 */     return true;
/*      */   }
/*      */   
/*      */   private static <T> boolean equal(T paramT1, T paramT2) {
/*  835 */     if (paramT1 == paramT2)
/*  836 */       return true; 
/*  837 */     if (paramT1 == null || paramT2 == null)
/*  838 */       return false; 
/*  839 */     return paramT1.equals(paramT2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MBeanParameterInfo customize(MBeanParameterInfo paramMBeanParameterInfo, String paramString1, String paramString2) {
/*  846 */     if (equal(paramString1, paramMBeanParameterInfo.getName()) && 
/*  847 */       equal(paramString2, paramMBeanParameterInfo.getDescription()))
/*  848 */       return paramMBeanParameterInfo; 
/*  849 */     if (paramMBeanParameterInfo instanceof OpenMBeanParameterInfo) {
/*  850 */       OpenMBeanParameterInfo openMBeanParameterInfo = (OpenMBeanParameterInfo)paramMBeanParameterInfo;
/*  851 */       return new OpenMBeanParameterInfoSupport(paramString1, paramString2, openMBeanParameterInfo
/*      */           
/*  853 */           .getOpenType(), paramMBeanParameterInfo
/*  854 */           .getDescriptor());
/*      */     } 
/*  856 */     return new MBeanParameterInfo(paramString1, paramMBeanParameterInfo
/*  857 */         .getType(), paramString2, paramMBeanParameterInfo
/*      */         
/*  859 */         .getDescriptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MBeanConstructorInfo customize(MBeanConstructorInfo paramMBeanConstructorInfo, String paramString, MBeanParameterInfo[] paramArrayOfMBeanParameterInfo) {
/*  867 */     if (equal(paramString, paramMBeanConstructorInfo.getDescription()) && 
/*  868 */       identicalArrays(paramArrayOfMBeanParameterInfo, paramMBeanConstructorInfo.getSignature()))
/*  869 */       return paramMBeanConstructorInfo; 
/*  870 */     if (paramMBeanConstructorInfo instanceof javax.management.openmbean.OpenMBeanConstructorInfo) {
/*      */       
/*  872 */       OpenMBeanParameterInfo[] arrayOfOpenMBeanParameterInfo = paramsToOpenParams(paramArrayOfMBeanParameterInfo);
/*  873 */       return new OpenMBeanConstructorInfoSupport(paramMBeanConstructorInfo.getName(), paramString, arrayOfOpenMBeanParameterInfo, paramMBeanConstructorInfo
/*      */ 
/*      */           
/*  876 */           .getDescriptor());
/*      */     } 
/*  878 */     return new MBeanConstructorInfo(paramMBeanConstructorInfo.getName(), paramString, paramArrayOfMBeanParameterInfo, paramMBeanConstructorInfo
/*      */ 
/*      */         
/*  881 */         .getDescriptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MBeanOperationInfo customize(MBeanOperationInfo paramMBeanOperationInfo, String paramString, MBeanParameterInfo[] paramArrayOfMBeanParameterInfo, int paramInt) {
/*  890 */     if (equal(paramString, paramMBeanOperationInfo.getDescription()) && 
/*  891 */       identicalArrays(paramArrayOfMBeanParameterInfo, paramMBeanOperationInfo.getSignature()) && paramInt == paramMBeanOperationInfo
/*  892 */       .getImpact())
/*  893 */       return paramMBeanOperationInfo; 
/*  894 */     if (paramMBeanOperationInfo instanceof OpenMBeanOperationInfo) {
/*  895 */       OpenMBeanOperationInfo openMBeanOperationInfo = (OpenMBeanOperationInfo)paramMBeanOperationInfo;
/*      */       
/*  897 */       OpenMBeanParameterInfo[] arrayOfOpenMBeanParameterInfo = paramsToOpenParams(paramArrayOfMBeanParameterInfo);
/*  898 */       return new OpenMBeanOperationInfoSupport(paramMBeanOperationInfo.getName(), paramString, arrayOfOpenMBeanParameterInfo, openMBeanOperationInfo
/*      */ 
/*      */           
/*  901 */           .getReturnOpenType(), paramInt, paramMBeanOperationInfo
/*      */           
/*  903 */           .getDescriptor());
/*      */     } 
/*  905 */     return new MBeanOperationInfo(paramMBeanOperationInfo.getName(), paramString, paramArrayOfMBeanParameterInfo, paramMBeanOperationInfo
/*      */ 
/*      */         
/*  908 */         .getReturnType(), paramInt, paramMBeanOperationInfo
/*      */         
/*  910 */         .getDescriptor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MBeanAttributeInfo customize(MBeanAttributeInfo paramMBeanAttributeInfo, String paramString) {
/*  917 */     if (equal(paramString, paramMBeanAttributeInfo.getDescription()))
/*  918 */       return paramMBeanAttributeInfo; 
/*  919 */     if (paramMBeanAttributeInfo instanceof OpenMBeanAttributeInfo) {
/*  920 */       OpenMBeanAttributeInfo openMBeanAttributeInfo = (OpenMBeanAttributeInfo)paramMBeanAttributeInfo;
/*  921 */       return new OpenMBeanAttributeInfoSupport(paramMBeanAttributeInfo.getName(), paramString, openMBeanAttributeInfo
/*      */           
/*  923 */           .getOpenType(), paramMBeanAttributeInfo
/*  924 */           .isReadable(), paramMBeanAttributeInfo
/*  925 */           .isWritable(), paramMBeanAttributeInfo
/*  926 */           .isIs(), paramMBeanAttributeInfo
/*  927 */           .getDescriptor());
/*      */     } 
/*  929 */     return new MBeanAttributeInfo(paramMBeanAttributeInfo.getName(), paramMBeanAttributeInfo
/*  930 */         .getType(), paramString, paramMBeanAttributeInfo
/*      */         
/*  932 */         .isReadable(), paramMBeanAttributeInfo
/*  933 */         .isWritable(), paramMBeanAttributeInfo
/*  934 */         .isIs(), paramMBeanAttributeInfo
/*  935 */         .getDescriptor());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static OpenMBeanParameterInfo[] paramsToOpenParams(MBeanParameterInfo[] paramArrayOfMBeanParameterInfo) {
/*  941 */     if (paramArrayOfMBeanParameterInfo instanceof OpenMBeanParameterInfo[])
/*  942 */       return (OpenMBeanParameterInfo[])paramArrayOfMBeanParameterInfo; 
/*  943 */     OpenMBeanParameterInfoSupport[] arrayOfOpenMBeanParameterInfoSupport = new OpenMBeanParameterInfoSupport[paramArrayOfMBeanParameterInfo.length];
/*      */     
/*  945 */     System.arraycopy(paramArrayOfMBeanParameterInfo, 0, arrayOfOpenMBeanParameterInfoSupport, 0, paramArrayOfMBeanParameterInfo.length);
/*  946 */     return (OpenMBeanParameterInfo[])arrayOfOpenMBeanParameterInfoSupport;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MBeanConstructorInfo[] getConstructors(MBeanInfo paramMBeanInfo, Object paramObject) {
/*  955 */     MBeanConstructorInfo[] arrayOfMBeanConstructorInfo1 = getConstructors(paramMBeanInfo.getConstructors(), paramObject);
/*  956 */     if (arrayOfMBeanConstructorInfo1 == null)
/*  957 */       return null; 
/*  958 */     int i = arrayOfMBeanConstructorInfo1.length;
/*  959 */     MBeanConstructorInfo[] arrayOfMBeanConstructorInfo2 = new MBeanConstructorInfo[i];
/*  960 */     for (byte b = 0; b < i; b++) {
/*  961 */       MBeanParameterInfo[] arrayOfMBeanParameterInfo2; MBeanConstructorInfo mBeanConstructorInfo = arrayOfMBeanConstructorInfo1[b];
/*  962 */       MBeanParameterInfo[] arrayOfMBeanParameterInfo1 = mBeanConstructorInfo.getSignature();
/*      */       
/*  964 */       if (arrayOfMBeanParameterInfo1 != null) {
/*  965 */         int j = arrayOfMBeanParameterInfo1.length;
/*  966 */         arrayOfMBeanParameterInfo2 = new MBeanParameterInfo[j];
/*  967 */         for (byte b1 = 0; b1 < j; b1++) {
/*  968 */           MBeanParameterInfo mBeanParameterInfo = arrayOfMBeanParameterInfo1[b1];
/*  969 */           arrayOfMBeanParameterInfo2[b1] = customize(mBeanParameterInfo, 
/*  970 */               getParameterName(mBeanConstructorInfo, mBeanParameterInfo, b1), 
/*  971 */               getDescription(mBeanConstructorInfo, mBeanParameterInfo, b1));
/*      */         } 
/*      */       } else {
/*  974 */         arrayOfMBeanParameterInfo2 = null;
/*      */       } 
/*  976 */       arrayOfMBeanConstructorInfo2[b] = 
/*  977 */         customize(mBeanConstructorInfo, getDescription(mBeanConstructorInfo), arrayOfMBeanParameterInfo2);
/*      */     } 
/*  979 */     return arrayOfMBeanConstructorInfo2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MBeanOperationInfo[] getOperations(MBeanInfo paramMBeanInfo) {
/*  986 */     MBeanOperationInfo[] arrayOfMBeanOperationInfo1 = paramMBeanInfo.getOperations();
/*  987 */     if (arrayOfMBeanOperationInfo1 == null)
/*  988 */       return null; 
/*  989 */     int i = arrayOfMBeanOperationInfo1.length;
/*  990 */     MBeanOperationInfo[] arrayOfMBeanOperationInfo2 = new MBeanOperationInfo[i];
/*  991 */     for (byte b = 0; b < i; b++) {
/*  992 */       MBeanParameterInfo[] arrayOfMBeanParameterInfo2; MBeanOperationInfo mBeanOperationInfo = arrayOfMBeanOperationInfo1[b];
/*  993 */       MBeanParameterInfo[] arrayOfMBeanParameterInfo1 = mBeanOperationInfo.getSignature();
/*      */       
/*  995 */       if (arrayOfMBeanParameterInfo1 != null) {
/*  996 */         int j = arrayOfMBeanParameterInfo1.length;
/*  997 */         arrayOfMBeanParameterInfo2 = new MBeanParameterInfo[j];
/*  998 */         for (byte b1 = 0; b1 < j; b1++) {
/*  999 */           MBeanParameterInfo mBeanParameterInfo = arrayOfMBeanParameterInfo1[b1];
/* 1000 */           arrayOfMBeanParameterInfo2[b1] = customize(mBeanParameterInfo, 
/* 1001 */               getParameterName(mBeanOperationInfo, mBeanParameterInfo, b1), 
/* 1002 */               getDescription(mBeanOperationInfo, mBeanParameterInfo, b1));
/*      */         } 
/*      */       } else {
/* 1005 */         arrayOfMBeanParameterInfo2 = null;
/*      */       } 
/* 1007 */       arrayOfMBeanOperationInfo2[b] = customize(mBeanOperationInfo, getDescription(mBeanOperationInfo), arrayOfMBeanParameterInfo2, getImpact(mBeanOperationInfo));
/*      */     } 
/* 1009 */     return arrayOfMBeanOperationInfo2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MBeanAttributeInfo[] getAttributes(MBeanInfo paramMBeanInfo) {
/* 1016 */     MBeanAttributeInfo[] arrayOfMBeanAttributeInfo1 = paramMBeanInfo.getAttributes();
/* 1017 */     if (arrayOfMBeanAttributeInfo1 == null) {
/* 1018 */       return null;
/*      */     }
/* 1020 */     int i = arrayOfMBeanAttributeInfo1.length;
/* 1021 */     MBeanAttributeInfo[] arrayOfMBeanAttributeInfo2 = new MBeanAttributeInfo[i];
/* 1022 */     for (byte b = 0; b < i; b++) {
/* 1023 */       MBeanAttributeInfo mBeanAttributeInfo = arrayOfMBeanAttributeInfo1[b];
/* 1024 */       arrayOfMBeanAttributeInfo2[b] = customize(mBeanAttributeInfo, getDescription(mBeanAttributeInfo));
/*      */     } 
/* 1026 */     return arrayOfMBeanAttributeInfo2;
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
/*      */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/* 1075 */     this.mbean.register(paramMBeanServer, paramObjectName);
/* 1076 */     return paramObjectName;
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
/*      */   public void postRegister(Boolean paramBoolean) {
/* 1099 */     if (!paramBoolean.booleanValue()) {
/* 1100 */       this.mbean.unregister();
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void postDeregister() {
/* 1137 */     this.mbean.unregister();
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
/* 1150 */   private static final Map<Class<?>, Boolean> mbeanInfoSafeMap = new WeakHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean immutableInfo(Class<? extends StandardMBean> paramClass) {
/* 1161 */     if (paramClass == StandardMBean.class || paramClass == StandardEmitterMBean.class)
/*      */     {
/* 1163 */       return true; } 
/* 1164 */     synchronized (mbeanInfoSafeMap) {
/* 1165 */       Boolean bool = mbeanInfoSafeMap.get(paramClass);
/* 1166 */       if (bool == null) {
/*      */         try {
/* 1168 */           MBeanInfoSafeAction mBeanInfoSafeAction = new MBeanInfoSafeAction(paramClass);
/*      */           
/* 1170 */           bool = AccessController.<Boolean>doPrivileged(mBeanInfoSafeAction);
/* 1171 */         } catch (Exception exception) {
/*      */           
/* 1173 */           bool = Boolean.valueOf(false);
/*      */         } 
/* 1175 */         mbeanInfoSafeMap.put(paramClass, bool);
/*      */       } 
/* 1177 */       return bool.booleanValue();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean overrides(Class<?> paramClass1, Class<?> paramClass2, String paramString, Class<?>... paramVarArgs) {
/* 1183 */     for (Class<?> clazz = paramClass1; clazz != paramClass2; clazz = clazz.getSuperclass()) {
/*      */       try {
/* 1185 */         clazz.getDeclaredMethod(paramString, paramVarArgs);
/* 1186 */         return true;
/* 1187 */       } catch (NoSuchMethodException noSuchMethodException) {}
/*      */     } 
/*      */ 
/*      */     
/* 1191 */     return false;
/*      */   }
/*      */   
/*      */   private static class MBeanInfoSafeAction
/*      */     implements PrivilegedAction<Boolean>
/*      */   {
/*      */     private final Class<?> subclass;
/*      */     
/*      */     MBeanInfoSafeAction(Class<?> param1Class) {
/* 1200 */       this.subclass = param1Class;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Boolean run() {
/* 1206 */       if (StandardMBean.overrides(this.subclass, StandardMBean.class, "cacheMBeanInfo", new Class[] { MBeanInfo.class }))
/*      */       {
/* 1208 */         return Boolean.valueOf(false);
/*      */       }
/*      */ 
/*      */       
/* 1212 */       if (StandardMBean.overrides(this.subclass, StandardMBean.class, "getCachedMBeanInfo", (Class[])null))
/*      */       {
/* 1214 */         return Boolean.valueOf(false);
/*      */       }
/*      */ 
/*      */       
/* 1218 */       if (StandardMBean.overrides(this.subclass, StandardMBean.class, "getMBeanInfo", (Class[])null))
/*      */       {
/* 1220 */         return Boolean.valueOf(false);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1230 */       if (StandardEmitterMBean.class.isAssignableFrom(this.subclass) && 
/* 1231 */         StandardMBean.overrides(this.subclass, StandardEmitterMBean.class, "getNotificationInfo", (Class[])null))
/*      */       {
/* 1233 */         return Boolean.valueOf(false); } 
/* 1234 */       return Boolean.valueOf(true);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/StandardMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */