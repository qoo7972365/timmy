/*     */ package java.beans;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndexedPropertyDescriptor
/*     */   extends PropertyDescriptor
/*     */ {
/*     */   private Reference<? extends Class<?>> indexedPropertyTypeRef;
/*  44 */   private final MethodRef indexedReadMethodRef = new MethodRef();
/*  45 */   private final MethodRef indexedWriteMethodRef = new MethodRef();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String indexedReadMethodName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String indexedWriteMethodName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndexedPropertyDescriptor(String paramString, Class<?> paramClass) throws IntrospectionException {
/*  67 */     this(paramString, paramClass, "get" + 
/*  68 */         NameGenerator.capitalize(paramString), "set" + 
/*  69 */         NameGenerator.capitalize(paramString), "get" + 
/*  70 */         NameGenerator.capitalize(paramString), "set" + 
/*  71 */         NameGenerator.capitalize(paramString));
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
/*     */   public IndexedPropertyDescriptor(String paramString1, Class<?> paramClass, String paramString2, String paramString3, String paramString4, String paramString5) throws IntrospectionException {
/* 100 */     super(paramString1, paramClass, paramString2, paramString3);
/*     */     
/* 102 */     this.indexedReadMethodName = paramString4;
/* 103 */     if (paramString4 != null && getIndexedReadMethod() == null) {
/* 104 */       throw new IntrospectionException("Method not found: " + paramString4);
/*     */     }
/*     */     
/* 107 */     this.indexedWriteMethodName = paramString5;
/* 108 */     if (paramString5 != null && getIndexedWriteMethod() == null) {
/* 109 */       throw new IntrospectionException("Method not found: " + paramString5);
/*     */     }
/*     */     
/* 112 */     findIndexedPropertyType(getIndexedReadMethod(), getIndexedWriteMethod());
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
/*     */   public IndexedPropertyDescriptor(String paramString, Method paramMethod1, Method paramMethod2, Method paramMethod3, Method paramMethod4) throws IntrospectionException {
/* 134 */     super(paramString, paramMethod1, paramMethod2);
/*     */     
/* 136 */     setIndexedReadMethod0(paramMethod3);
/* 137 */     setIndexedWriteMethod0(paramMethod4);
/*     */ 
/*     */     
/* 140 */     setIndexedPropertyType(findIndexedPropertyType(paramMethod3, paramMethod4));
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
/*     */   IndexedPropertyDescriptor(Class<?> paramClass, String paramString, Method paramMethod1, Method paramMethod2, Method paramMethod3, Method paramMethod4) throws IntrospectionException {
/* 158 */     super(paramClass, paramString, paramMethod1, paramMethod2);
/*     */     
/* 160 */     setIndexedReadMethod0(paramMethod3);
/* 161 */     setIndexedWriteMethod0(paramMethod4);
/*     */ 
/*     */     
/* 164 */     setIndexedPropertyType(findIndexedPropertyType(paramMethod3, paramMethod4));
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
/*     */   public synchronized Method getIndexedReadMethod() {
/* 176 */     Method method = this.indexedReadMethodRef.get();
/* 177 */     if (method == null) {
/* 178 */       Class<?> clazz = getClass0();
/* 179 */       if (clazz == null || (this.indexedReadMethodName == null && 
/* 180 */         !this.indexedReadMethodRef.isSet()))
/*     */       {
/* 182 */         return null;
/*     */       }
/* 184 */       String str = "get" + getBaseName();
/* 185 */       if (this.indexedReadMethodName == null) {
/* 186 */         Class<?> clazz1 = getIndexedPropertyType0();
/* 187 */         if (clazz1 == boolean.class || clazz1 == null) {
/* 188 */           this.indexedReadMethodName = "is" + getBaseName();
/*     */         } else {
/* 190 */           this.indexedReadMethodName = str;
/*     */         } 
/*     */       } 
/*     */       
/* 194 */       Class[] arrayOfClass = { int.class };
/* 195 */       method = Introspector.findMethod(clazz, this.indexedReadMethodName, 1, arrayOfClass);
/* 196 */       if (method == null && !this.indexedReadMethodName.equals(str)) {
/*     */         
/* 198 */         this.indexedReadMethodName = str;
/* 199 */         method = Introspector.findMethod(clazz, this.indexedReadMethodName, 1, arrayOfClass);
/*     */       } 
/* 201 */       setIndexedReadMethod0(method);
/*     */     } 
/* 203 */     return method;
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
/*     */   public synchronized void setIndexedReadMethod(Method paramMethod) throws IntrospectionException {
/* 217 */     setIndexedPropertyType(findIndexedPropertyType(paramMethod, this.indexedWriteMethodRef
/* 218 */           .get()));
/* 219 */     setIndexedReadMethod0(paramMethod);
/*     */   }
/*     */   
/*     */   private void setIndexedReadMethod0(Method paramMethod) {
/* 223 */     this.indexedReadMethodRef.set(paramMethod);
/* 224 */     if (paramMethod == null) {
/* 225 */       this.indexedReadMethodName = null;
/*     */       return;
/*     */     } 
/* 228 */     setClass0(paramMethod.getDeclaringClass());
/*     */     
/* 230 */     this.indexedReadMethodName = paramMethod.getName();
/* 231 */     setTransient(paramMethod.<Transient>getAnnotation(Transient.class));
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
/*     */   public synchronized Method getIndexedWriteMethod() {
/* 243 */     Method method = this.indexedWriteMethodRef.get();
/* 244 */     if (method == null) {
/* 245 */       Class<?> clazz1 = getClass0();
/* 246 */       if (clazz1 == null || (this.indexedWriteMethodName == null && 
/* 247 */         !this.indexedWriteMethodRef.isSet()))
/*     */       {
/* 249 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 255 */       Class<?> clazz2 = getIndexedPropertyType0();
/* 256 */       if (clazz2 == null) {
/*     */         try {
/* 258 */           clazz2 = findIndexedPropertyType(getIndexedReadMethod(), (Method)null);
/* 259 */           setIndexedPropertyType(clazz2);
/* 260 */         } catch (IntrospectionException introspectionException) {
/*     */           
/* 262 */           Class<?> clazz = getPropertyType();
/* 263 */           if (clazz.isArray()) {
/* 264 */             clazz2 = clazz.getComponentType();
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 269 */       if (this.indexedWriteMethodName == null) {
/* 270 */         this.indexedWriteMethodName = "set" + getBaseName();
/*     */       }
/*     */       
/* 273 */       (new Class[2])[0] = int.class; (new Class[2])[1] = clazz2; Class[] arrayOfClass = (clazz2 == null) ? null : new Class[2];
/* 274 */       method = Introspector.findMethod(clazz1, this.indexedWriteMethodName, 2, arrayOfClass);
/* 275 */       if (method != null && 
/* 276 */         !method.getReturnType().equals(void.class)) {
/* 277 */         method = null;
/*     */       }
/*     */       
/* 280 */       setIndexedWriteMethod0(method);
/*     */     } 
/* 282 */     return method;
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
/*     */   public synchronized void setIndexedWriteMethod(Method paramMethod) throws IntrospectionException {
/* 296 */     Class<?> clazz = findIndexedPropertyType(getIndexedReadMethod(), paramMethod);
/*     */     
/* 298 */     setIndexedPropertyType(clazz);
/* 299 */     setIndexedWriteMethod0(paramMethod);
/*     */   }
/*     */   
/*     */   private void setIndexedWriteMethod0(Method paramMethod) {
/* 303 */     this.indexedWriteMethodRef.set(paramMethod);
/* 304 */     if (paramMethod == null) {
/* 305 */       this.indexedWriteMethodName = null;
/*     */       return;
/*     */     } 
/* 308 */     setClass0(paramMethod.getDeclaringClass());
/*     */     
/* 310 */     this.indexedWriteMethodName = paramMethod.getName();
/* 311 */     setTransient(paramMethod.<Transient>getAnnotation(Transient.class));
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
/*     */   public synchronized Class<?> getIndexedPropertyType() {
/* 325 */     Class<?> clazz = getIndexedPropertyType0();
/* 326 */     if (clazz == null) {
/*     */       try {
/* 328 */         clazz = findIndexedPropertyType(getIndexedReadMethod(), 
/* 329 */             getIndexedWriteMethod());
/* 330 */         setIndexedPropertyType(clazz);
/* 331 */       } catch (IntrospectionException introspectionException) {}
/*     */     }
/*     */ 
/*     */     
/* 335 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setIndexedPropertyType(Class<?> paramClass) {
/* 341 */     this.indexedPropertyTypeRef = getWeakReference(paramClass);
/*     */   }
/*     */   
/*     */   private Class<?> getIndexedPropertyType0() {
/* 345 */     return (this.indexedPropertyTypeRef != null) ? this.indexedPropertyTypeRef
/* 346 */       .get() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> findIndexedPropertyType(Method paramMethod1, Method paramMethod2) throws IntrospectionException {
/* 353 */     Class<?> clazz1 = null;
/*     */     
/* 355 */     if (paramMethod1 != null) {
/* 356 */       Class[] arrayOfClass = getParameterTypes(getClass0(), paramMethod1);
/* 357 */       if (arrayOfClass.length != 1) {
/* 358 */         throw new IntrospectionException("bad indexed read method arg count");
/*     */       }
/* 360 */       if (arrayOfClass[0] != int.class) {
/* 361 */         throw new IntrospectionException("non int index to indexed read method");
/*     */       }
/* 363 */       clazz1 = getReturnType(getClass0(), paramMethod1);
/* 364 */       if (clazz1 == void.class) {
/* 365 */         throw new IntrospectionException("indexed read method returns void");
/*     */       }
/*     */     } 
/* 368 */     if (paramMethod2 != null) {
/* 369 */       Class[] arrayOfClass = getParameterTypes(getClass0(), paramMethod2);
/* 370 */       if (arrayOfClass.length != 2) {
/* 371 */         throw new IntrospectionException("bad indexed write method arg count");
/*     */       }
/* 373 */       if (arrayOfClass[0] != int.class) {
/* 374 */         throw new IntrospectionException("non int index to indexed write method");
/*     */       }
/* 376 */       if (clazz1 == null || arrayOfClass[1].isAssignableFrom(clazz1)) {
/* 377 */         clazz1 = arrayOfClass[1];
/* 378 */       } else if (!clazz1.isAssignableFrom(arrayOfClass[1])) {
/* 379 */         throw new IntrospectionException("type mismatch between indexed read and indexed write methods: " + 
/*     */             
/* 381 */             getName());
/*     */       } 
/*     */     } 
/* 384 */     Class<?> clazz2 = getPropertyType();
/* 385 */     if (clazz2 != null && (!clazz2.isArray() || clazz2
/* 386 */       .getComponentType() != clazz1)) {
/* 387 */       throw new IntrospectionException("type mismatch between indexed and non-indexed methods: " + 
/* 388 */           getName());
/*     */     }
/* 390 */     return clazz1;
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
/*     */   public boolean equals(Object paramObject) {
/* 404 */     if (this == paramObject) {
/* 405 */       return true;
/*     */     }
/*     */     
/* 408 */     if (paramObject != null && paramObject instanceof IndexedPropertyDescriptor) {
/* 409 */       IndexedPropertyDescriptor indexedPropertyDescriptor = (IndexedPropertyDescriptor)paramObject;
/* 410 */       Method method1 = indexedPropertyDescriptor.getIndexedReadMethod();
/* 411 */       Method method2 = indexedPropertyDescriptor.getIndexedWriteMethod();
/*     */       
/* 413 */       if (!compareMethods(getIndexedReadMethod(), method1)) {
/* 414 */         return false;
/*     */       }
/*     */       
/* 417 */       if (!compareMethods(getIndexedWriteMethod(), method2)) {
/* 418 */         return false;
/*     */       }
/*     */       
/* 421 */       if (getIndexedPropertyType() != indexedPropertyDescriptor.getIndexedPropertyType()) {
/* 422 */         return false;
/*     */       }
/* 424 */       return super.equals(paramObject);
/*     */     } 
/* 426 */     return false;
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
/*     */   IndexedPropertyDescriptor(PropertyDescriptor paramPropertyDescriptor1, PropertyDescriptor paramPropertyDescriptor2) {
/* 439 */     super(paramPropertyDescriptor1, paramPropertyDescriptor2);
/* 440 */     if (paramPropertyDescriptor1 instanceof IndexedPropertyDescriptor) {
/* 441 */       IndexedPropertyDescriptor indexedPropertyDescriptor = (IndexedPropertyDescriptor)paramPropertyDescriptor1;
/*     */       try {
/* 443 */         Method method1 = indexedPropertyDescriptor.getIndexedReadMethod();
/* 444 */         if (method1 != null) {
/* 445 */           setIndexedReadMethod(method1);
/*     */         }
/*     */         
/* 448 */         Method method2 = indexedPropertyDescriptor.getIndexedWriteMethod();
/* 449 */         if (method2 != null) {
/* 450 */           setIndexedWriteMethod(method2);
/*     */         }
/* 452 */       } catch (IntrospectionException introspectionException) {
/*     */         
/* 454 */         throw new AssertionError(introspectionException);
/*     */       } 
/*     */     } 
/* 457 */     if (paramPropertyDescriptor2 instanceof IndexedPropertyDescriptor) {
/* 458 */       IndexedPropertyDescriptor indexedPropertyDescriptor = (IndexedPropertyDescriptor)paramPropertyDescriptor2;
/*     */       try {
/* 460 */         Method method1 = indexedPropertyDescriptor.getIndexedReadMethod();
/* 461 */         if (method1 != null && method1.getDeclaringClass() == getClass0()) {
/* 462 */           setIndexedReadMethod(method1);
/*     */         }
/*     */         
/* 465 */         Method method2 = indexedPropertyDescriptor.getIndexedWriteMethod();
/* 466 */         if (method2 != null && method2.getDeclaringClass() == getClass0()) {
/* 467 */           setIndexedWriteMethod(method2);
/*     */         }
/* 469 */       } catch (IntrospectionException introspectionException) {
/*     */         
/* 471 */         throw new AssertionError(introspectionException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   IndexedPropertyDescriptor(IndexedPropertyDescriptor paramIndexedPropertyDescriptor) {
/* 481 */     super(paramIndexedPropertyDescriptor);
/* 482 */     this.indexedReadMethodRef.set(paramIndexedPropertyDescriptor.indexedReadMethodRef.get());
/* 483 */     this.indexedWriteMethodRef.set(paramIndexedPropertyDescriptor.indexedWriteMethodRef.get());
/* 484 */     this.indexedPropertyTypeRef = paramIndexedPropertyDescriptor.indexedPropertyTypeRef;
/* 485 */     this.indexedWriteMethodName = paramIndexedPropertyDescriptor.indexedWriteMethodName;
/* 486 */     this.indexedReadMethodName = paramIndexedPropertyDescriptor.indexedReadMethodName;
/*     */   }
/*     */   
/*     */   void updateGenericsFor(Class<?> paramClass) {
/* 490 */     super.updateGenericsFor(paramClass);
/*     */     try {
/* 492 */       setIndexedPropertyType(findIndexedPropertyType(this.indexedReadMethodRef.get(), this.indexedWriteMethodRef.get()));
/*     */     }
/* 494 */     catch (IntrospectionException introspectionException) {
/* 495 */       setIndexedPropertyType((Class<?>)null);
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
/*     */   public int hashCode() {
/* 507 */     int i = super.hashCode();
/*     */ 
/*     */     
/* 510 */     i = 37 * i + ((this.indexedWriteMethodName == null) ? 0 : this.indexedWriteMethodName.hashCode());
/*     */     
/* 512 */     i = 37 * i + ((this.indexedReadMethodName == null) ? 0 : this.indexedReadMethodName.hashCode());
/*     */     
/* 514 */     i = 37 * i + ((getIndexedPropertyType() == null) ? 0 : getIndexedPropertyType().hashCode());
/*     */     
/* 516 */     return i;
/*     */   }
/*     */   
/*     */   void appendTo(StringBuilder paramStringBuilder) {
/* 520 */     super.appendTo(paramStringBuilder);
/* 521 */     appendTo(paramStringBuilder, "indexedPropertyType", this.indexedPropertyTypeRef);
/* 522 */     appendTo(paramStringBuilder, "indexedReadMethod", this.indexedReadMethodRef.get());
/* 523 */     appendTo(paramStringBuilder, "indexedWriteMethod", this.indexedWriteMethodRef.get());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/IndexedPropertyDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */