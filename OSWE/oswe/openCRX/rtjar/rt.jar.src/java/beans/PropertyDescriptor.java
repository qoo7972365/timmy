/*     */ package java.beans;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
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
/*     */ public class PropertyDescriptor
/*     */   extends FeatureDescriptor
/*     */ {
/*     */   private Reference<? extends Class<?>> propertyTypeRef;
/*  40 */   private final MethodRef readMethodRef = new MethodRef();
/*  41 */   private final MethodRef writeMethodRef = new MethodRef();
/*     */ 
/*     */ 
/*     */   
/*     */   private Reference<? extends Class<?>> propertyEditorClassRef;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean bound;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean constrained;
/*     */ 
/*     */ 
/*     */   
/*     */   private String baseName;
/*     */ 
/*     */ 
/*     */   
/*     */   private String writeMethodName;
/*     */ 
/*     */ 
/*     */   
/*     */   private String readMethodName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyDescriptor(String paramString, Class<?> paramClass) throws IntrospectionException {
/*  71 */     this(paramString, paramClass, "is" + 
/*  72 */         NameGenerator.capitalize(paramString), "set" + 
/*  73 */         NameGenerator.capitalize(paramString));
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
/*     */   public PropertyDescriptor(String paramString1, Class<?> paramClass, String paramString2, String paramString3) throws IntrospectionException {
/*  93 */     if (paramClass == null) {
/*  94 */       throw new IntrospectionException("Target Bean class is null");
/*     */     }
/*  96 */     if (paramString1 == null || paramString1.length() == 0) {
/*  97 */       throw new IntrospectionException("bad property name");
/*     */     }
/*  99 */     if ("".equals(paramString2) || "".equals(paramString3)) {
/* 100 */       throw new IntrospectionException("read or write method name should not be the empty string");
/*     */     }
/* 102 */     setName(paramString1);
/* 103 */     setClass0(paramClass);
/*     */     
/* 105 */     this.readMethodName = paramString2;
/* 106 */     if (paramString2 != null && getReadMethod() == null) {
/* 107 */       throw new IntrospectionException("Method not found: " + paramString2);
/*     */     }
/* 109 */     this.writeMethodName = paramString3;
/* 110 */     if (paramString3 != null && getWriteMethod() == null) {
/* 111 */       throw new IntrospectionException("Method not found: " + paramString3);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 116 */     Class[] arrayOfClass = { PropertyChangeListener.class };
/* 117 */     this.bound = (null != Introspector.findMethod(paramClass, "addPropertyChangeListener", arrayOfClass.length, arrayOfClass));
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
/*     */   public PropertyDescriptor(String paramString, Method paramMethod1, Method paramMethod2) throws IntrospectionException {
/* 134 */     if (paramString == null || paramString.length() == 0) {
/* 135 */       throw new IntrospectionException("bad property name");
/*     */     }
/* 137 */     setName(paramString);
/* 138 */     setReadMethod(paramMethod1);
/* 139 */     setWriteMethod(paramMethod2);
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
/*     */   PropertyDescriptor(Class<?> paramClass, String paramString, Method paramMethod1, Method paramMethod2) throws IntrospectionException {
/* 155 */     if (paramClass == null) {
/* 156 */       throw new IntrospectionException("Target Bean class is null");
/*     */     }
/* 158 */     setClass0(paramClass);
/* 159 */     setName(Introspector.decapitalize(paramString));
/* 160 */     setReadMethod(paramMethod1);
/* 161 */     setWriteMethod(paramMethod2);
/* 162 */     this.baseName = paramString;
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
/*     */   public synchronized Class<?> getPropertyType() {
/* 178 */     Class<?> clazz = getPropertyType0();
/* 179 */     if (clazz == null) {
/*     */       try {
/* 181 */         clazz = findPropertyType(getReadMethod(), getWriteMethod());
/* 182 */         setPropertyType(clazz);
/* 183 */       } catch (IntrospectionException introspectionException) {}
/*     */     }
/*     */ 
/*     */     
/* 187 */     return clazz;
/*     */   }
/*     */   
/*     */   private void setPropertyType(Class<?> paramClass) {
/* 191 */     this.propertyTypeRef = getWeakReference(paramClass);
/*     */   }
/*     */   
/*     */   private Class<?> getPropertyType0() {
/* 195 */     return (this.propertyTypeRef != null) ? this.propertyTypeRef
/* 196 */       .get() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Method getReadMethod() {
/* 207 */     Method method = this.readMethodRef.get();
/* 208 */     if (method == null) {
/* 209 */       Class<?> clazz = getClass0();
/* 210 */       if (clazz == null || (this.readMethodName == null && !this.readMethodRef.isSet()))
/*     */       {
/* 212 */         return null;
/*     */       }
/* 214 */       String str = "get" + getBaseName();
/* 215 */       if (this.readMethodName == null) {
/* 216 */         Class<?> clazz1 = getPropertyType0();
/* 217 */         if (clazz1 == boolean.class || clazz1 == null) {
/* 218 */           this.readMethodName = "is" + getBaseName();
/*     */         } else {
/* 220 */           this.readMethodName = str;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 229 */       method = Introspector.findMethod(clazz, this.readMethodName, 0);
/* 230 */       if (method == null && !this.readMethodName.equals(str)) {
/* 231 */         this.readMethodName = str;
/* 232 */         method = Introspector.findMethod(clazz, this.readMethodName, 0);
/*     */       } 
/*     */       try {
/* 235 */         setReadMethod(method);
/* 236 */       } catch (IntrospectionException introspectionException) {}
/*     */     } 
/*     */ 
/*     */     
/* 240 */     return method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setReadMethod(Method paramMethod) throws IntrospectionException {
/* 251 */     this.readMethodRef.set(paramMethod);
/* 252 */     if (paramMethod == null) {
/* 253 */       this.readMethodName = null;
/*     */       
/*     */       return;
/*     */     } 
/* 257 */     setPropertyType(findPropertyType(paramMethod, this.writeMethodRef.get()));
/* 258 */     setClass0(paramMethod.getDeclaringClass());
/*     */     
/* 260 */     this.readMethodName = paramMethod.getName();
/* 261 */     setTransient(paramMethod.<Transient>getAnnotation(Transient.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Method getWriteMethod() {
/* 271 */     Method method = this.writeMethodRef.get();
/* 272 */     if (method == null) {
/* 273 */       Class<?> clazz1 = getClass0();
/* 274 */       if (clazz1 == null || (this.writeMethodName == null && !this.writeMethodRef.isSet()))
/*     */       {
/* 276 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 280 */       Class<?> clazz2 = getPropertyType0();
/* 281 */       if (clazz2 == null) {
/*     */         
/*     */         try {
/* 284 */           clazz2 = findPropertyType(getReadMethod(), (Method)null);
/* 285 */           setPropertyType(clazz2);
/* 286 */         } catch (IntrospectionException introspectionException) {
/*     */ 
/*     */           
/* 289 */           return null;
/*     */         } 
/*     */       }
/*     */       
/* 293 */       if (this.writeMethodName == null) {
/* 294 */         this.writeMethodName = "set" + getBaseName();
/*     */       }
/*     */       
/* 297 */       (new Class[1])[0] = clazz2; Class[] arrayOfClass = (clazz2 == null) ? null : new Class[1];
/* 298 */       method = Introspector.findMethod(clazz1, this.writeMethodName, 1, arrayOfClass);
/* 299 */       if (method != null && 
/* 300 */         !method.getReturnType().equals(void.class)) {
/* 301 */         method = null;
/*     */       }
/*     */       
/*     */       try {
/* 305 */         setWriteMethod(method);
/* 306 */       } catch (IntrospectionException introspectionException) {}
/*     */     } 
/*     */ 
/*     */     
/* 310 */     return method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setWriteMethod(Method paramMethod) throws IntrospectionException {
/* 321 */     this.writeMethodRef.set(paramMethod);
/* 322 */     if (paramMethod == null) {
/* 323 */       this.writeMethodName = null;
/*     */       
/*     */       return;
/*     */     } 
/* 327 */     setPropertyType(findPropertyType(getReadMethod(), paramMethod));
/* 328 */     setClass0(paramMethod.getDeclaringClass());
/*     */     
/* 330 */     this.writeMethodName = paramMethod.getName();
/* 331 */     setTransient(paramMethod.<Transient>getAnnotation(Transient.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setClass0(Class<?> paramClass) {
/* 338 */     if (getClass0() != null && paramClass.isAssignableFrom(getClass0())) {
/*     */       return;
/*     */     }
/*     */     
/* 342 */     super.setClass0(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBound() {
/* 352 */     return this.bound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBound(boolean paramBoolean) {
/* 362 */     this.bound = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstrained() {
/* 372 */     return this.constrained;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConstrained(boolean paramBoolean) {
/* 382 */     this.constrained = paramBoolean;
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
/*     */   public void setPropertyEditorClass(Class<?> paramClass) {
/* 395 */     this.propertyEditorClassRef = getWeakReference(paramClass);
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
/*     */   public Class<?> getPropertyEditorClass() {
/* 409 */     return (this.propertyEditorClassRef != null) ? this.propertyEditorClassRef
/* 410 */       .get() : null;
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
/*     */   public PropertyEditor createPropertyEditor(Object paramObject) {
/* 428 */     Object object = null;
/*     */     
/* 430 */     Class<?> clazz = getPropertyEditorClass();
/* 431 */     if (clazz != null && PropertyEditor.class.isAssignableFrom(clazz) && 
/* 432 */       ReflectUtil.isPackageAccessible(clazz)) {
/* 433 */       Constructor<?> constructor = null;
/* 434 */       if (paramObject != null) {
/*     */         try {
/* 436 */           constructor = clazz.getConstructor(new Class[] { Object.class });
/* 437 */         } catch (Exception exception) {}
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 442 */         if (constructor == null) {
/* 443 */           object = clazz.newInstance();
/*     */         } else {
/* 445 */           object = constructor.newInstance(new Object[] { paramObject });
/*     */         } 
/* 447 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/* 451 */     return (PropertyEditor)object;
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
/*     */   public boolean equals(Object paramObject) {
/* 464 */     if (this == paramObject) {
/* 465 */       return true;
/*     */     }
/* 467 */     if (paramObject != null && paramObject instanceof PropertyDescriptor) {
/* 468 */       PropertyDescriptor propertyDescriptor = (PropertyDescriptor)paramObject;
/* 469 */       Method method1 = propertyDescriptor.getReadMethod();
/* 470 */       Method method2 = propertyDescriptor.getWriteMethod();
/*     */       
/* 472 */       if (!compareMethods(getReadMethod(), method1)) {
/* 473 */         return false;
/*     */       }
/*     */       
/* 476 */       if (!compareMethods(getWriteMethod(), method2)) {
/* 477 */         return false;
/*     */       }
/*     */       
/* 480 */       if (getPropertyType() == propertyDescriptor.getPropertyType() && 
/* 481 */         getPropertyEditorClass() == propertyDescriptor.getPropertyEditorClass() && this.bound == propertyDescriptor
/* 482 */         .isBound() && this.constrained == propertyDescriptor.isConstrained() && this.writeMethodName == propertyDescriptor.writeMethodName && this.readMethodName == propertyDescriptor.readMethodName)
/*     */       {
/*     */         
/* 485 */         return true;
/*     */       }
/*     */     } 
/* 488 */     return false;
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
/*     */   boolean compareMethods(Method paramMethod1, Method paramMethod2) {
/* 500 */     if (((paramMethod1 == null) ? true : false) != ((paramMethod2 == null) ? true : false)) {
/* 501 */       return false;
/*     */     }
/*     */     
/* 504 */     if (paramMethod1 != null && paramMethod2 != null && 
/* 505 */       !paramMethod1.equals(paramMethod2)) {
/* 506 */       return false;
/*     */     }
/*     */     
/* 509 */     return true;
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
/*     */   PropertyDescriptor(PropertyDescriptor paramPropertyDescriptor1, PropertyDescriptor paramPropertyDescriptor2) {
/* 521 */     super(paramPropertyDescriptor1, paramPropertyDescriptor2);
/*     */     
/* 523 */     if (paramPropertyDescriptor2.baseName != null) {
/* 524 */       this.baseName = paramPropertyDescriptor2.baseName;
/*     */     } else {
/* 526 */       this.baseName = paramPropertyDescriptor1.baseName;
/*     */     } 
/*     */     
/* 529 */     if (paramPropertyDescriptor2.readMethodName != null) {
/* 530 */       this.readMethodName = paramPropertyDescriptor2.readMethodName;
/*     */     } else {
/* 532 */       this.readMethodName = paramPropertyDescriptor1.readMethodName;
/*     */     } 
/*     */     
/* 535 */     if (paramPropertyDescriptor2.writeMethodName != null) {
/* 536 */       this.writeMethodName = paramPropertyDescriptor2.writeMethodName;
/*     */     } else {
/* 538 */       this.writeMethodName = paramPropertyDescriptor1.writeMethodName;
/*     */     } 
/*     */     
/* 541 */     if (paramPropertyDescriptor2.propertyTypeRef != null) {
/* 542 */       this.propertyTypeRef = paramPropertyDescriptor2.propertyTypeRef;
/*     */     } else {
/* 544 */       this.propertyTypeRef = paramPropertyDescriptor1.propertyTypeRef;
/*     */     } 
/*     */ 
/*     */     
/* 548 */     Method method1 = paramPropertyDescriptor1.getReadMethod();
/* 549 */     Method method2 = paramPropertyDescriptor2.getReadMethod();
/*     */ 
/*     */     
/*     */     try {
/* 553 */       if (isAssignable(method1, method2)) {
/* 554 */         setReadMethod(method2);
/*     */       } else {
/* 556 */         setReadMethod(method1);
/*     */       } 
/* 558 */     } catch (IntrospectionException introspectionException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 564 */     if (method1 != null && method2 != null && method1
/* 565 */       .getDeclaringClass() == method2.getDeclaringClass() && 
/* 566 */       getReturnType(getClass0(), method1) == boolean.class && 
/* 567 */       getReturnType(getClass0(), method2) == boolean.class && method1
/* 568 */       .getName().indexOf("is") == 0 && method2
/* 569 */       .getName().indexOf("get") == 0) {
/*     */       try {
/* 571 */         setReadMethod(method1);
/* 572 */       } catch (IntrospectionException introspectionException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 577 */     Method method3 = paramPropertyDescriptor1.getWriteMethod();
/* 578 */     Method method4 = paramPropertyDescriptor2.getWriteMethod();
/*     */     
/*     */     try {
/* 581 */       if (method4 != null) {
/* 582 */         setWriteMethod(method4);
/*     */       } else {
/* 584 */         setWriteMethod(method3);
/*     */       } 
/* 586 */     } catch (IntrospectionException introspectionException) {}
/*     */ 
/*     */ 
/*     */     
/* 590 */     if (paramPropertyDescriptor2.getPropertyEditorClass() != null) {
/* 591 */       setPropertyEditorClass(paramPropertyDescriptor2.getPropertyEditorClass());
/*     */     } else {
/* 593 */       setPropertyEditorClass(paramPropertyDescriptor1.getPropertyEditorClass());
/*     */     } 
/*     */ 
/*     */     
/* 597 */     paramPropertyDescriptor1.bound |= paramPropertyDescriptor2.bound;
/* 598 */     paramPropertyDescriptor1.constrained |= paramPropertyDescriptor2.constrained;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PropertyDescriptor(PropertyDescriptor paramPropertyDescriptor) {
/* 606 */     super(paramPropertyDescriptor);
/* 607 */     this.propertyTypeRef = paramPropertyDescriptor.propertyTypeRef;
/* 608 */     this.readMethodRef.set(paramPropertyDescriptor.readMethodRef.get());
/* 609 */     this.writeMethodRef.set(paramPropertyDescriptor.writeMethodRef.get());
/* 610 */     this.propertyEditorClassRef = paramPropertyDescriptor.propertyEditorClassRef;
/*     */     
/* 612 */     this.writeMethodName = paramPropertyDescriptor.writeMethodName;
/* 613 */     this.readMethodName = paramPropertyDescriptor.readMethodName;
/* 614 */     this.baseName = paramPropertyDescriptor.baseName;
/*     */     
/* 616 */     this.bound = paramPropertyDescriptor.bound;
/* 617 */     this.constrained = paramPropertyDescriptor.constrained;
/*     */   }
/*     */   
/*     */   void updateGenericsFor(Class<?> paramClass) {
/* 621 */     setClass0(paramClass);
/*     */     try {
/* 623 */       setPropertyType(findPropertyType(this.readMethodRef.get(), this.writeMethodRef.get()));
/*     */     }
/* 625 */     catch (IntrospectionException introspectionException) {
/* 626 */       setPropertyType((Class<?>)null);
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
/*     */   private Class<?> findPropertyType(Method paramMethod1, Method paramMethod2) throws IntrospectionException {
/* 640 */     Class<?> clazz = null;
/*     */     try {
/* 642 */       if (paramMethod1 != null) {
/* 643 */         Class[] arrayOfClass = getParameterTypes(getClass0(), paramMethod1);
/* 644 */         if (arrayOfClass.length != 0) {
/* 645 */           throw new IntrospectionException("bad read method arg count: " + paramMethod1);
/*     */         }
/*     */         
/* 648 */         clazz = getReturnType(getClass0(), paramMethod1);
/* 649 */         if (clazz == void.class) {
/* 650 */           throw new IntrospectionException("read method " + paramMethod1
/* 651 */               .getName() + " returns void");
/*     */         }
/*     */       } 
/* 654 */       if (paramMethod2 != null) {
/* 655 */         Class[] arrayOfClass = getParameterTypes(getClass0(), paramMethod2);
/* 656 */         if (arrayOfClass.length != 1) {
/* 657 */           throw new IntrospectionException("bad write method arg count: " + paramMethod2);
/*     */         }
/*     */         
/* 660 */         if (clazz != null && !arrayOfClass[0].isAssignableFrom(clazz)) {
/* 661 */           throw new IntrospectionException("type mismatch between read and write methods");
/*     */         }
/* 663 */         clazz = arrayOfClass[0];
/*     */       } 
/* 665 */     } catch (IntrospectionException introspectionException) {
/* 666 */       throw introspectionException;
/*     */     } 
/* 668 */     return clazz;
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
/*     */   public int hashCode() {
/* 680 */     int i = 7;
/*     */ 
/*     */     
/* 683 */     i = 37 * i + ((getPropertyType() == null) ? 0 : getPropertyType().hashCode());
/*     */     
/* 685 */     i = 37 * i + ((getReadMethod() == null) ? 0 : getReadMethod().hashCode());
/*     */     
/* 687 */     i = 37 * i + ((getWriteMethod() == null) ? 0 : getWriteMethod().hashCode());
/*     */     
/* 689 */     i = 37 * i + ((getPropertyEditorClass() == null) ? 0 : getPropertyEditorClass().hashCode());
/*     */     
/* 691 */     i = 37 * i + ((this.writeMethodName == null) ? 0 : this.writeMethodName.hashCode());
/*     */     
/* 693 */     i = 37 * i + ((this.readMethodName == null) ? 0 : this.readMethodName.hashCode());
/* 694 */     i = 37 * i + getName().hashCode();
/* 695 */     i = 37 * i + (!this.bound ? 0 : 1);
/* 696 */     i = 37 * i + (!this.constrained ? 0 : 1);
/*     */     
/* 698 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   String getBaseName() {
/* 703 */     if (this.baseName == null) {
/* 704 */       this.baseName = NameGenerator.capitalize(getName());
/*     */     }
/* 706 */     return this.baseName;
/*     */   }
/*     */   
/*     */   void appendTo(StringBuilder paramStringBuilder) {
/* 710 */     appendTo(paramStringBuilder, "bound", this.bound);
/* 711 */     appendTo(paramStringBuilder, "constrained", this.constrained);
/* 712 */     appendTo(paramStringBuilder, "propertyEditorClass", this.propertyEditorClassRef);
/* 713 */     appendTo(paramStringBuilder, "propertyType", this.propertyTypeRef);
/* 714 */     appendTo(paramStringBuilder, "readMethod", this.readMethodRef.get());
/* 715 */     appendTo(paramStringBuilder, "writeMethod", this.writeMethodRef.get());
/*     */   }
/*     */   
/*     */   private boolean isAssignable(Method paramMethod1, Method paramMethod2) {
/* 719 */     if (paramMethod1 == null) {
/* 720 */       return true;
/*     */     }
/* 722 */     if (paramMethod2 == null) {
/* 723 */       return false;
/*     */     }
/* 725 */     if (!paramMethod1.getName().equals(paramMethod2.getName())) {
/* 726 */       return true;
/*     */     }
/* 728 */     Class<?> clazz1 = paramMethod1.getDeclaringClass();
/* 729 */     Class<?> clazz2 = paramMethod2.getDeclaringClass();
/* 730 */     if (!clazz1.isAssignableFrom(clazz2)) {
/* 731 */       return false;
/*     */     }
/* 733 */     clazz1 = getReturnType(getClass0(), paramMethod1);
/* 734 */     clazz2 = getReturnType(getClass0(), paramMethod2);
/* 735 */     if (!clazz1.isAssignableFrom(clazz2)) {
/* 736 */       return false;
/*     */     }
/* 738 */     Class[] arrayOfClass1 = getParameterTypes(getClass0(), paramMethod1);
/* 739 */     Class[] arrayOfClass2 = getParameterTypes(getClass0(), paramMethod2);
/* 740 */     if (arrayOfClass1.length != arrayOfClass2.length) {
/* 741 */       return true;
/*     */     }
/* 743 */     for (byte b = 0; b < arrayOfClass1.length; b++) {
/* 744 */       if (!arrayOfClass1[b].isAssignableFrom(arrayOfClass2[b])) {
/* 745 */         return false;
/*     */       }
/*     */     } 
/* 748 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/PropertyDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */