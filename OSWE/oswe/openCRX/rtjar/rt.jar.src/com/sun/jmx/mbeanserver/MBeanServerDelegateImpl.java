/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.Attribute;
/*     */ import javax.management.AttributeList;
/*     */ import javax.management.AttributeNotFoundException;
/*     */ import javax.management.DynamicMBean;
/*     */ import javax.management.InvalidAttributeValueException;
/*     */ import javax.management.JMRuntimeException;
/*     */ import javax.management.MBeanAttributeInfo;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanInfo;
/*     */ import javax.management.MBeanRegistration;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MBeanServerDelegate;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.ReflectionException;
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
/*     */ final class MBeanServerDelegateImpl
/*     */   extends MBeanServerDelegate
/*     */   implements DynamicMBean, MBeanRegistration
/*     */ {
/*  56 */   private static final String[] attributeNames = new String[] { "MBeanServerId", "SpecificationName", "SpecificationVersion", "SpecificationVendor", "ImplementationName", "ImplementationVersion", "ImplementationVendor" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static final MBeanAttributeInfo[] attributeInfos = new MBeanAttributeInfo[] { new MBeanAttributeInfo("MBeanServerId", "java.lang.String", "The MBean server agent identification", true, false, false), new MBeanAttributeInfo("SpecificationName", "java.lang.String", "The full name of the JMX specification implemented by this product.", true, false, false), new MBeanAttributeInfo("SpecificationVersion", "java.lang.String", "The version of the JMX specification implemented by this product.", true, false, false), new MBeanAttributeInfo("SpecificationVendor", "java.lang.String", "The vendor of the JMX specification implemented by this product.", true, false, false), new MBeanAttributeInfo("ImplementationName", "java.lang.String", "The JMX implementation name (the name of this product)", true, false, false), new MBeanAttributeInfo("ImplementationVersion", "java.lang.String", "The JMX implementation version (the version of this product).", true, false, false), new MBeanAttributeInfo("ImplementationVendor", "java.lang.String", "the JMX implementation vendor (the vendor of this product).", true, false, false) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private final MBeanInfo delegateInfo = new MBeanInfo("javax.management.MBeanServerDelegate", "Represents  the MBean server from the management point of view.", attributeInfos, null, null, 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       getNotificationInfo());
/*     */ 
/*     */ 
/*     */   
/*     */   public final ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/* 111 */     if (paramObjectName == null) return DELEGATE_NAME; 
/* 112 */     return paramObjectName;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void postRegister(Boolean paramBoolean) {}
/*     */ 
/*     */   
/*     */   public final void preDeregister() throws Exception {
/* 120 */     throw new IllegalArgumentException("The MBeanServerDelegate MBean cannot be unregistered");
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
/*     */   public final void postDeregister() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttribute(String paramString) throws AttributeNotFoundException, MBeanException, ReflectionException {
/*     */     try {
/* 145 */       if (paramString == null) {
/* 146 */         throw new AttributeNotFoundException("null");
/*     */       }
/*     */ 
/*     */       
/* 150 */       if (paramString.equals("MBeanServerId"))
/* 151 */         return getMBeanServerId(); 
/* 152 */       if (paramString.equals("SpecificationName"))
/* 153 */         return getSpecificationName(); 
/* 154 */       if (paramString.equals("SpecificationVersion"))
/* 155 */         return getSpecificationVersion(); 
/* 156 */       if (paramString.equals("SpecificationVendor"))
/* 157 */         return getSpecificationVendor(); 
/* 158 */       if (paramString.equals("ImplementationName"))
/* 159 */         return getImplementationName(); 
/* 160 */       if (paramString.equals("ImplementationVersion"))
/* 161 */         return getImplementationVersion(); 
/* 162 */       if (paramString.equals("ImplementationVendor")) {
/* 163 */         return getImplementationVendor();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 168 */       throw new AttributeNotFoundException("null");
/*     */     }
/* 170 */     catch (AttributeNotFoundException attributeNotFoundException) {
/* 171 */       throw attributeNotFoundException;
/* 172 */     } catch (JMRuntimeException jMRuntimeException) {
/* 173 */       throw jMRuntimeException;
/* 174 */     } catch (SecurityException securityException) {
/* 175 */       throw securityException;
/* 176 */     } catch (Exception exception) {
/* 177 */       throw new MBeanException(exception, "Failed to get " + paramString);
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
/*     */   public void setAttribute(Attribute paramAttribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
/* 198 */     String str = (paramAttribute == null) ? null : paramAttribute.getName();
/* 199 */     if (str == null) {
/* 200 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Attribute name cannot be null");
/*     */       
/* 202 */       throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred trying to invoke the setter on the MBean");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     Object object = getAttribute(str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     throw new AttributeNotFoundException(str + " not accessible");
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
/*     */   public AttributeList getAttributes(String[] paramArrayOfString) {
/* 230 */     String[] arrayOfString = (paramArrayOfString == null) ? attributeNames : paramArrayOfString;
/*     */ 
/*     */ 
/*     */     
/* 234 */     int i = arrayOfString.length;
/* 235 */     AttributeList attributeList = new AttributeList(i);
/*     */ 
/*     */ 
/*     */     
/* 239 */     for (byte b = 0; b < i; b++) {
/*     */       
/*     */       try {
/* 242 */         Attribute attribute = new Attribute(arrayOfString[b], getAttribute(arrayOfString[b]));
/* 243 */         attributeList.add(attribute);
/* 244 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 247 */         if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
/* 248 */           JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, MBeanServerDelegateImpl.class
/* 249 */               .getName(), "getAttributes", "Attribute " + arrayOfString[b] + " not found");
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     return attributeList;
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
/*     */   public AttributeList setAttributes(AttributeList paramAttributeList) {
/* 273 */     return new AttributeList(0);
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
/*     */   public Object invoke(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws MBeanException, ReflectionException {
/* 298 */     if (paramString == null) {
/* 299 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Operation name  cannot be null");
/*     */       
/* 301 */       throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred trying to invoke the operation on the MBean");
/*     */     } 
/*     */ 
/*     */     
/* 305 */     throw new ReflectionException(new NoSuchMethodException(paramString), "The operation with name " + paramString + " could not be found");
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
/*     */   public MBeanInfo getMBeanInfo() {
/* 318 */     return this.delegateInfo;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/MBeanServerDelegateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */