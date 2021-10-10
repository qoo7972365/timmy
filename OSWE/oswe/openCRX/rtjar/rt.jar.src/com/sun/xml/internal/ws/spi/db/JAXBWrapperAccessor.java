/*     */ package com.sun.xml.internal.ws.spi.db;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlElementWrapper;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBWrapperAccessor
/*     */   extends WrapperAccessor
/*     */ {
/*     */   protected Class<?> contentClass;
/*     */   protected HashMap<Object, Class> elementDeclaredTypes;
/*     */   
/*     */   public JAXBWrapperAccessor(Class<?> wrapperBean) {
/*  61 */     this.contentClass = wrapperBean;
/*     */     
/*  63 */     HashMap<Object, PropertySetter> setByQName = new HashMap<>();
/*  64 */     HashMap<Object, PropertySetter> setByLocalpart = new HashMap<>();
/*  65 */     HashMap<String, Method> publicSetters = new HashMap<>();
/*     */     
/*  67 */     HashMap<Object, PropertyGetter> getByQName = new HashMap<>();
/*  68 */     HashMap<Object, PropertyGetter> getByLocalpart = new HashMap<>();
/*  69 */     HashMap<String, Method> publicGetters = new HashMap<>();
/*     */     
/*  71 */     HashMap<Object, Class<?>> elementDeclaredTypesByQName = new HashMap<>();
/*  72 */     HashMap<Object, Class<?>> elementDeclaredTypesByLocalpart = new HashMap<>();
/*     */     
/*  74 */     for (Method method : this.contentClass.getMethods()) {
/*  75 */       if (PropertySetterBase.setterPattern(method)) {
/*     */         
/*  77 */         String key = method.getName().substring(3, method.getName().length()).toLowerCase();
/*  78 */         publicSetters.put(key, method);
/*     */       } 
/*  80 */       if (PropertyGetterBase.getterPattern(method)) {
/*  81 */         String methodName = method.getName();
/*     */ 
/*     */ 
/*     */         
/*  85 */         String key = methodName.startsWith("is") ? methodName.substring(2, method.getName().length()).toLowerCase() : methodName.substring(3, method.getName().length()).toLowerCase();
/*  86 */         publicGetters.put(key, method);
/*     */       } 
/*     */     } 
/*  89 */     HashSet<String> elementLocalNames = new HashSet<>();
/*  90 */     for (Field field : getAllFields(this.contentClass)) {
/*  91 */       XmlElementWrapper xmlElemWrapper = field.<XmlElementWrapper>getAnnotation(XmlElementWrapper.class);
/*  92 */       XmlElement xmlElem = field.<XmlElement>getAnnotation(XmlElement.class);
/*  93 */       XmlElementRef xmlElemRef = field.<XmlElementRef>getAnnotation(XmlElementRef.class);
/*  94 */       String fieldName = field.getName().toLowerCase();
/*  95 */       String namespace = "";
/*  96 */       String localName = field.getName();
/*  97 */       if (xmlElemWrapper != null) {
/*  98 */         namespace = xmlElemWrapper.namespace();
/*  99 */         if (xmlElemWrapper.name() != null && !xmlElemWrapper.name().equals("") && 
/* 100 */           !xmlElemWrapper.name().equals("##default")) {
/* 101 */           localName = xmlElemWrapper.name();
/*     */         }
/* 103 */       } else if (xmlElem != null) {
/* 104 */         namespace = xmlElem.namespace();
/* 105 */         if (xmlElem.name() != null && !xmlElem.name().equals("") && 
/* 106 */           !xmlElem.name().equals("##default")) {
/* 107 */           localName = xmlElem.name();
/*     */         }
/* 109 */       } else if (xmlElemRef != null) {
/* 110 */         namespace = xmlElemRef.namespace();
/* 111 */         if (xmlElemRef.name() != null && !xmlElemRef.name().equals("") && 
/* 112 */           !xmlElemRef.name().equals("##default")) {
/* 113 */           localName = xmlElemRef.name();
/*     */         }
/*     */       } 
/* 116 */       if (elementLocalNames.contains(localName)) {
/* 117 */         this.elementLocalNameCollision = true;
/*     */       } else {
/* 119 */         elementLocalNames.add(localName);
/*     */       } 
/*     */       
/* 122 */       QName qname = new QName(namespace, localName);
/* 123 */       if (field.getType().equals(JAXBElement.class) && 
/* 124 */         field.getGenericType() instanceof ParameterizedType) {
/*     */         
/* 126 */         Type arg = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
/* 127 */         if (arg instanceof Class) {
/* 128 */           elementDeclaredTypesByQName.put(qname, (Class)arg);
/* 129 */           elementDeclaredTypesByLocalpart.put(localName, (Class)arg);
/*     */         }
/* 131 */         else if (arg instanceof GenericArrayType) {
/*     */           
/* 133 */           Type componentType = ((GenericArrayType)arg).getGenericComponentType();
/* 134 */           if (componentType instanceof Class) {
/*     */             
/* 136 */             Class<?> arrayClass = Array.newInstance((Class)componentType, 0).getClass();
/* 137 */             elementDeclaredTypesByQName.put(qname, arrayClass);
/* 138 */             elementDeclaredTypesByLocalpart.put(localName, arrayClass);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       if (fieldName.startsWith("_") && !localName.startsWith("_")) {
/* 147 */         fieldName = fieldName.substring(1);
/*     */       }
/* 149 */       Method setMethod = publicSetters.get(fieldName);
/* 150 */       Method getMethod = publicGetters.get(fieldName);
/* 151 */       PropertySetter setter = createPropertySetter(field, setMethod);
/* 152 */       PropertyGetter getter = createPropertyGetter(field, getMethod);
/* 153 */       setByQName.put(qname, setter);
/* 154 */       setByLocalpart.put(localName, setter);
/* 155 */       getByQName.put(qname, getter);
/* 156 */       getByLocalpart.put(localName, getter);
/*     */     } 
/* 158 */     if (this.elementLocalNameCollision) {
/* 159 */       this.propertySetters = setByQName;
/* 160 */       this.propertyGetters = getByQName;
/* 161 */       this.elementDeclaredTypes = elementDeclaredTypesByQName;
/*     */     } else {
/* 163 */       this.propertySetters = setByLocalpart;
/* 164 */       this.propertyGetters = getByLocalpart;
/* 165 */       this.elementDeclaredTypes = elementDeclaredTypesByLocalpart;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static List<Field> getAllFields(Class<?> clz) {
/* 170 */     List<Field> list = new ArrayList<>();
/* 171 */     while (!Object.class.equals(clz)) {
/* 172 */       list.addAll(Arrays.asList(getDeclaredFields(clz)));
/* 173 */       clz = clz.getSuperclass();
/*     */     } 
/* 175 */     return list;
/*     */   }
/*     */   
/*     */   protected static Field[] getDeclaredFields(final Class<?> clz) {
/*     */     try {
/* 180 */       return (System.getSecurityManager() == null) ? clz.getDeclaredFields() : 
/* 181 */         AccessController.<Field[]>doPrivileged((PrivilegedExceptionAction)new PrivilegedExceptionAction<Field[]>()
/*     */           {
/*     */             public Field[] run() throws IllegalAccessException {
/* 184 */               return clz.getDeclaredFields();
/*     */             }
/*     */           });
/* 187 */     } catch (PrivilegedActionException e) {
/*     */       
/* 189 */       e.printStackTrace();
/* 190 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static PropertyGetter createPropertyGetter(Field field, Method getMethod) {
/* 195 */     if (!field.isAccessible() && 
/* 196 */       getMethod != null) {
/* 197 */       MethodGetter methodGetter = new MethodGetter(getMethod);
/* 198 */       if (methodGetter.getType().toString().equals(field.getType().toString())) {
/* 199 */         return methodGetter;
/*     */       }
/*     */     } 
/*     */     
/* 203 */     return new FieldGetter(field);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static PropertySetter createPropertySetter(Field field, Method setter) {
/* 208 */     if (!field.isAccessible() && 
/* 209 */       setter != null) {
/* 210 */       MethodSetter injection = new MethodSetter(setter);
/* 211 */       if (injection.getType().toString().equals(field.getType().toString())) {
/* 212 */         return injection;
/*     */       }
/*     */     } 
/*     */     
/* 216 */     return new FieldSetter(field);
/*     */   }
/*     */ 
/*     */   
/*     */   private Class getElementDeclaredType(QName name) {
/* 221 */     Object key = this.elementLocalNameCollision ? name : name.getLocalPart();
/* 222 */     return this.elementDeclaredTypes.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyAccessor getPropertyAccessor(String ns, String name) {
/* 227 */     final QName n = new QName(ns, name);
/* 228 */     final PropertySetter setter = getPropertySetter(n);
/* 229 */     final PropertyGetter getter = getPropertyGetter(n);
/*     */     
/* 231 */     final boolean isJAXBElement = setter.getType().equals(JAXBElement.class);
/* 232 */     final boolean isListType = List.class.isAssignableFrom(setter
/* 233 */         .getType());
/* 234 */     final Class elementDeclaredType = isJAXBElement ? getElementDeclaredType(n) : null;
/*     */     
/* 236 */     return new PropertyAccessor<Object, Object>()
/*     */       {
/*     */         public Object get(Object bean) throws DatabindingException {
/*     */           Object val;
/* 240 */           if (isJAXBElement) {
/* 241 */             JAXBElement<Object> jaxbElement = (JAXBElement<Object>)getter.get(bean);
/* 242 */             val = (jaxbElement == null) ? null : jaxbElement.getValue();
/*     */           } else {
/* 244 */             val = getter.get(bean);
/*     */           } 
/* 246 */           if (val == null && isListType) {
/* 247 */             val = new ArrayList();
/* 248 */             set(bean, val);
/*     */           } 
/* 250 */           return val;
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(Object bean, Object value) throws DatabindingException {
/* 255 */           if (isJAXBElement) {
/* 256 */             JAXBElement<Object> jaxbElement = new JAXBElement(n, elementDeclaredType, JAXBWrapperAccessor.this.contentClass, value);
/*     */             
/* 258 */             setter.set(bean, jaxbElement);
/*     */           } else {
/* 260 */             setter.set(bean, value);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/JAXBWrapperAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */