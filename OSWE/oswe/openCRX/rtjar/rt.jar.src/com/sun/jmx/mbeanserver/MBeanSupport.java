/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import javax.management.Attribute;
/*     */ import javax.management.AttributeList;
/*     */ import javax.management.AttributeNotFoundException;
/*     */ import javax.management.InvalidAttributeValueException;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanInfo;
/*     */ import javax.management.MBeanRegistration;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.ReflectionException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MBeanSupport<M>
/*     */   implements DynamicMBean2, MBeanRegistration
/*     */ {
/*     */   private final MBeanInfo mbeanInfo;
/*     */   private final Object resource;
/*     */   private final PerInterface<M> perInterface;
/*     */   
/*     */   <T> MBeanSupport(T paramT, Class<T> paramClass) throws NotCompliantMBeanException {
/* 127 */     if (paramClass == null)
/* 128 */       throw new NotCompliantMBeanException("Null MBean interface"); 
/* 129 */     if (!paramClass.isInstance(paramT)) {
/*     */ 
/*     */       
/* 132 */       String str = "Resource class " + paramT.getClass().getName() + " is not an instance of " + paramClass.getName();
/* 133 */       throw new NotCompliantMBeanException(str);
/*     */     } 
/* 135 */     ReflectUtil.checkPackageAccess(paramClass);
/* 136 */     this.resource = paramT;
/* 137 */     MBeanIntrospector<M> mBeanIntrospector = getMBeanIntrospector();
/* 138 */     this.perInterface = mBeanIntrospector.getPerInterface(paramClass);
/* 139 */     this.mbeanInfo = mBeanIntrospector.getMBeanInfo(paramT, this.perInterface);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract MBeanIntrospector<M> getMBeanIntrospector();
/*     */ 
/*     */ 
/*     */   
/*     */   abstract Object getCookie();
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isMXBean() {
/* 154 */     return this.perInterface.isMXBean();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void register(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception;
/*     */ 
/*     */   
/*     */   public abstract void unregister();
/*     */ 
/*     */   
/*     */   public final ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/* 166 */     if (this.resource instanceof MBeanRegistration)
/* 167 */       paramObjectName = ((MBeanRegistration)this.resource).preRegister(paramMBeanServer, paramObjectName); 
/* 168 */     return paramObjectName;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void preRegister2(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/* 173 */     register(paramMBeanServer, paramObjectName);
/*     */   }
/*     */   
/*     */   public final void registerFailed() {
/* 177 */     unregister();
/*     */   }
/*     */   
/*     */   public final void postRegister(Boolean paramBoolean) {
/* 181 */     if (this.resource instanceof MBeanRegistration)
/* 182 */       ((MBeanRegistration)this.resource).postRegister(paramBoolean); 
/*     */   }
/*     */   
/*     */   public final void preDeregister() throws Exception {
/* 186 */     if (this.resource instanceof MBeanRegistration) {
/* 187 */       ((MBeanRegistration)this.resource).preDeregister();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void postDeregister() {
/*     */     try {
/* 195 */       unregister();
/*     */     } finally {
/* 197 */       if (this.resource instanceof MBeanRegistration) {
/* 198 */         ((MBeanRegistration)this.resource).postDeregister();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object getAttribute(String paramString) throws AttributeNotFoundException, MBeanException, ReflectionException {
/* 206 */     return this.perInterface.getAttribute(this.resource, paramString, getCookie());
/*     */   }
/*     */   
/*     */   public final AttributeList getAttributes(String[] paramArrayOfString) {
/* 210 */     AttributeList attributeList = new AttributeList(paramArrayOfString.length);
/* 211 */     for (String str : paramArrayOfString) {
/*     */       try {
/* 213 */         Object object = getAttribute(str);
/* 214 */         attributeList.add(new Attribute(str, object));
/* 215 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 220 */     return attributeList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setAttribute(Attribute paramAttribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
/* 228 */     String str = paramAttribute.getName();
/* 229 */     Object object = paramAttribute.getValue();
/* 230 */     this.perInterface.setAttribute(this.resource, str, object, getCookie());
/*     */   }
/*     */   
/*     */   public final AttributeList setAttributes(AttributeList paramAttributeList) {
/* 234 */     AttributeList attributeList = new AttributeList(paramAttributeList.size());
/* 235 */     for (Attribute attribute1 : paramAttributeList) {
/*     */       
/* 237 */       Attribute attribute2 = attribute1;
/*     */       try {
/* 239 */         setAttribute(attribute2);
/* 240 */         attributeList.add(new Attribute(attribute2.getName(), attribute2.getValue()));
/* 241 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 246 */     return attributeList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object invoke(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws MBeanException, ReflectionException {
/* 252 */     return this.perInterface.invoke(this.resource, paramString, paramArrayOfObject, paramArrayOfString, 
/* 253 */         getCookie());
/*     */   }
/*     */ 
/*     */   
/*     */   public MBeanInfo getMBeanInfo() {
/* 258 */     return this.mbeanInfo;
/*     */   }
/*     */   
/*     */   public final String getClassName() {
/* 262 */     return this.resource.getClass().getName();
/*     */   }
/*     */   
/*     */   public final Object getResource() {
/* 266 */     return this.resource;
/*     */   }
/*     */   
/*     */   public final Class<?> getMBeanInterface() {
/* 270 */     return this.perInterface.getMBeanInterface();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/MBeanSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */