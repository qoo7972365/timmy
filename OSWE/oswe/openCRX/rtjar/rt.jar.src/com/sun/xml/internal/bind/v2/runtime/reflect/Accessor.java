/*     */ package com.sun.xml.internal.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.bind.Util;
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.model.core.Adapter;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.opt.OptimizedAccessorFactory;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.awt.Image;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.datatype.Duration;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ import javax.xml.transform.Source;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Accessor<BeanT, ValueT>
/*     */   implements Receiver
/*     */ {
/*     */   public final Class<ValueT> valueType;
/*     */   
/*     */   public Class<ValueT> getValueType() {
/*  78 */     return this.valueType;
/*     */   }
/*     */   
/*     */   protected Accessor(Class<ValueT> valueType) {
/*  82 */     this.valueType = valueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Accessor<BeanT, ValueT> optimize(@Nullable JAXBContextImpl context) {
/*  93 */     return this;
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
/*     */   public Object getUnadapted(BeanT bean) throws AccessorException {
/* 127 */     return get(bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdapted() {
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnadapted(BeanT bean, Object value) throws AccessorException {
/* 146 */     set(bean, (ValueT)value);
/*     */   }
/*     */   
/*     */   public void receive(UnmarshallingContext.State state, Object o) throws SAXException {
/*     */     try {
/* 151 */       set((BeanT)state.getTarget(), (ValueT)o);
/* 152 */     } catch (AccessorException e) {
/* 153 */       Loader.handleGenericException((Exception)e, true);
/* 154 */     } catch (IllegalAccessError iae) {
/*     */       
/* 156 */       Loader.handleGenericError(iae);
/*     */     } 
/*     */   }
/*     */   
/* 160 */   private static List<Class> nonAbstractableClasses = Arrays.asList(new Class[] { Object.class, Calendar.class, Duration.class, XMLGregorianCalendar.class, Image.class, DataHandler.class, Source.class, Date.class, File.class, URI.class, URL.class, Class.class, String.class, Source.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValueTypeAbstractable() {
/* 178 */     return !nonAbstractableClasses.contains(getValueType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbstractable(Class clazz) {
/* 187 */     return !nonAbstractableClasses.contains(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> Accessor<BeanT, T> adapt(Class<T> targetType, Class<? extends XmlAdapter<T, ValueT>> adapter) {
/* 195 */     return new AdaptedAccessor<>(targetType, this, adapter);
/*     */   }
/*     */   
/*     */   public final <T> Accessor<BeanT, T> adapt(Adapter<Type, Class<?>> adapter) {
/* 199 */     return new AdaptedAccessor<>((Class<T>)Utils.REFLECTION_NAVIGATOR
/* 200 */         .erasure(adapter.defaultType), this, (Class<? extends XmlAdapter<T, ValueT>>)adapter.adapterType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean accessWarned = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FieldReflection<BeanT, ValueT>
/*     */     extends Accessor<BeanT, ValueT>
/*     */   {
/*     */     public final Field f;
/*     */ 
/*     */ 
/*     */     
/* 218 */     private static final Logger logger = Util.getClassLogger();
/*     */     
/*     */     public FieldReflection(Field f) {
/* 221 */       this(f, false);
/*     */     }
/*     */     
/*     */     public FieldReflection(Field f, boolean supressAccessorWarnings) {
/* 225 */       super((Class)f.getType());
/* 226 */       this.f = f;
/*     */       
/* 228 */       int mod = f.getModifiers();
/* 229 */       if (!Modifier.isPublic(mod) || Modifier.isFinal(mod) || !Modifier.isPublic(f.getDeclaringClass().getModifiers())) {
/*     */         
/*     */         try {
/*     */ 
/*     */           
/* 234 */           f.setAccessible(true);
/* 235 */         } catch (SecurityException e) {
/* 236 */           if (!Accessor.accessWarned && !supressAccessorWarnings)
/*     */           {
/* 238 */             logger.log(Level.WARNING, Messages.UNABLE_TO_ACCESS_NON_PUBLIC_FIELD.format(new Object[] { f
/* 239 */                     .getDeclaringClass().getName(), f
/* 240 */                     .getName() }, ), e);
/*     */           }
/*     */           
/* 243 */           Accessor.accessWarned = true;
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     public ValueT get(BeanT bean) {
/*     */       try {
/* 250 */         return (ValueT)this.f.get(bean);
/* 251 */       } catch (IllegalAccessException e) {
/* 252 */         throw new IllegalAccessError(e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/*     */     public void set(BeanT bean, ValueT value) {
/*     */       try {
/* 258 */         if (value == null)
/* 259 */           value = (ValueT)Accessor.uninitializedValues.get(this.valueType); 
/* 260 */         this.f.set(bean, value);
/* 261 */       } catch (IllegalAccessException e) {
/* 262 */         throw new IllegalAccessError(e.getMessage());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Accessor<BeanT, ValueT> optimize(JAXBContextImpl context) {
/* 268 */       if (context != null && context.fastBoot)
/*     */       {
/* 270 */         return this; } 
/* 271 */       Accessor<BeanT, ValueT> acc = OptimizedAccessorFactory.get(this.f);
/* 272 */       if (acc != null) {
/* 273 */         return acc;
/*     */       }
/* 275 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class ReadOnlyFieldReflection<BeanT, ValueT>
/*     */     extends FieldReflection<BeanT, ValueT>
/*     */   {
/*     */     public ReadOnlyFieldReflection(Field f, boolean supressAccessorWarnings) {
/* 284 */       super(f, supressAccessorWarnings);
/*     */     }
/*     */     public ReadOnlyFieldReflection(Field f) {
/* 287 */       super(f);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void set(BeanT bean, ValueT value) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Accessor<BeanT, ValueT> optimize(JAXBContextImpl context) {
/* 297 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GetterSetterReflection<BeanT, ValueT>
/*     */     extends Accessor<BeanT, ValueT>
/*     */   {
/*     */     public final Method getter;
/*     */     
/*     */     public final Method setter;
/*     */     
/* 309 */     private static final Logger logger = Util.getClassLogger();
/*     */     
/*     */     public GetterSetterReflection(Method getter, Method setter) {
/* 312 */       super((getter != null) ? (Class)getter
/*     */           
/* 314 */           .getReturnType() : 
/* 315 */           (Class)setter.getParameterTypes()[0]);
/* 316 */       this.getter = getter;
/* 317 */       this.setter = setter;
/*     */       
/* 319 */       if (getter != null)
/* 320 */         makeAccessible(getter); 
/* 321 */       if (setter != null)
/* 322 */         makeAccessible(setter); 
/*     */     }
/*     */     
/*     */     private void makeAccessible(Method m) {
/* 326 */       if (!Modifier.isPublic(m.getModifiers()) || !Modifier.isPublic(m.getDeclaringClass().getModifiers())) {
/*     */         try {
/* 328 */           m.setAccessible(true);
/* 329 */         } catch (SecurityException e) {
/* 330 */           if (!Accessor.accessWarned)
/*     */           {
/* 332 */             logger.log(Level.WARNING, Messages.UNABLE_TO_ACCESS_NON_PUBLIC_FIELD.format(new Object[] { m
/* 333 */                     .getDeclaringClass().getName(), m
/* 334 */                     .getName() }, ), e);
/*     */           }
/* 336 */           Accessor.accessWarned = true;
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     public ValueT get(BeanT bean) throws AccessorException {
/*     */       try {
/* 343 */         return (ValueT)this.getter.invoke(bean, new Object[0]);
/* 344 */       } catch (IllegalAccessException e) {
/* 345 */         throw new IllegalAccessError(e.getMessage());
/* 346 */       } catch (InvocationTargetException e) {
/* 347 */         throw handleInvocationTargetException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void set(BeanT bean, ValueT value) throws AccessorException {
/*     */       try {
/* 353 */         if (value == null)
/* 354 */           value = (ValueT)Accessor.uninitializedValues.get(this.valueType); 
/* 355 */         this.setter.invoke(bean, new Object[] { value });
/* 356 */       } catch (IllegalAccessException e) {
/* 357 */         throw new IllegalAccessError(e.getMessage());
/* 358 */       } catch (InvocationTargetException e) {
/* 359 */         throw handleInvocationTargetException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private AccessorException handleInvocationTargetException(InvocationTargetException e) {
/* 365 */       Throwable t = e.getTargetException();
/* 366 */       if (t instanceof RuntimeException)
/* 367 */         throw (RuntimeException)t; 
/* 368 */       if (t instanceof Error) {
/* 369 */         throw (Error)t;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 376 */       return new AccessorException(t);
/*     */     }
/*     */ 
/*     */     
/*     */     public Accessor<BeanT, ValueT> optimize(JAXBContextImpl context) {
/* 381 */       if (this.getter == null || this.setter == null)
/*     */       {
/* 383 */         return this; } 
/* 384 */       if (context != null && context.fastBoot)
/*     */       {
/* 386 */         return this;
/*     */       }
/* 388 */       Accessor<BeanT, ValueT> acc = OptimizedAccessorFactory.get(this.getter, this.setter);
/* 389 */       if (acc != null) {
/* 390 */         return acc;
/*     */       }
/* 392 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class GetterOnlyReflection<BeanT, ValueT>
/*     */     extends GetterSetterReflection<BeanT, ValueT>
/*     */   {
/*     */     public GetterOnlyReflection(Method getter) {
/* 404 */       super(getter, null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(BeanT bean, ValueT value) throws AccessorException {
/* 409 */       throw new AccessorException(Messages.NO_SETTER.format(new Object[] { this.getter.toString() }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SetterOnlyReflection<BeanT, ValueT>
/*     */     extends GetterSetterReflection<BeanT, ValueT>
/*     */   {
/*     */     public SetterOnlyReflection(Method setter) {
/* 421 */       super(null, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     public ValueT get(BeanT bean) throws AccessorException {
/* 426 */       throw new AccessorException(Messages.NO_GETTER.format(new Object[] { this.setter.toString() }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A, B> Accessor<A, B> getErrorInstance() {
/* 435 */     return ERROR;
/*     */   }
/*     */   
/* 438 */   private static final Accessor ERROR = new Accessor<Object, Object>(Object.class) {
/*     */       public Object get(Object o) {
/* 440 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void set(Object o, Object o1) {}
/*     */     };
/*     */ 
/*     */ 
/*     */   
/* 450 */   public static final Accessor<JAXBElement, Object> JAXB_ELEMENT_VALUE = new Accessor<JAXBElement, Object>(Object.class) {
/*     */       public Object get(JAXBElement jaxbElement) {
/* 452 */         return jaxbElement.getValue();
/*     */       }
/*     */       
/*     */       public void set(JAXBElement jaxbElement, Object o) {
/* 456 */         jaxbElement.setValue(o);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 463 */   private static final Map<Class, Object> uninitializedValues = (Map)new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 476 */     uninitializedValues.put(byte.class, Byte.valueOf((byte)0));
/* 477 */     uninitializedValues.put(boolean.class, Boolean.valueOf(false));
/* 478 */     uninitializedValues.put(char.class, Character.valueOf(false));
/* 479 */     uninitializedValues.put(float.class, Float.valueOf(0.0F));
/* 480 */     uninitializedValues.put(double.class, Double.valueOf(0.0D));
/* 481 */     uninitializedValues.put(int.class, Integer.valueOf(0));
/* 482 */     uninitializedValues.put(long.class, Long.valueOf(0L));
/* 483 */     uninitializedValues.put(short.class, Short.valueOf((short)0));
/*     */   }
/*     */   
/*     */   public abstract ValueT get(BeanT paramBeanT) throws AccessorException;
/*     */   
/*     */   public abstract void set(BeanT paramBeanT, ValueT paramValueT) throws AccessorException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/Accessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */