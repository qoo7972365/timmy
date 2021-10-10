/*     */ package com.sun.xml.internal.bind.v2.model.nav;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.runtime.Location;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.GenericDeclaration;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ReflectionNavigator
/*     */   implements Navigator<Type, Class, Field, Method>
/*     */ {
/*  50 */   private static final ReflectionNavigator INSTANCE = new ReflectionNavigator();
/*     */   
/*     */   static ReflectionNavigator getInstance() {
/*  53 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getSuperClass(Class<Object> clazz) {
/*  61 */     if (clazz == Object.class) {
/*  62 */       return null;
/*     */     }
/*  64 */     Class<? super Object> sc = clazz.getSuperclass();
/*  65 */     if (sc == null) {
/*  66 */       sc = Object.class;
/*     */     }
/*  68 */     return sc;
/*     */   }
/*     */   
/*  71 */   private static final TypeVisitor<Type, Class> baseClassFinder = new TypeVisitor<Type, Class>()
/*     */     {
/*     */       public Type onClass(Class c, Class sup)
/*     */       {
/*  75 */         if (sup == c) {
/*  76 */           return sup;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  81 */         Type sc = c.getGenericSuperclass();
/*  82 */         if (sc != null) {
/*  83 */           Type r = visit(sc, sup);
/*  84 */           if (r != null) {
/*  85 */             return r;
/*     */           }
/*     */         } 
/*     */         
/*  89 */         for (Type i : c.getGenericInterfaces()) {
/*  90 */           Type r = visit(i, sup);
/*  91 */           if (r != null) {
/*  92 */             return r;
/*     */           }
/*     */         } 
/*     */         
/*  96 */         return null;
/*     */       }
/*     */       
/*     */       public Type onParameterizdType(ParameterizedType p, Class sup) {
/* 100 */         Class raw = (Class)p.getRawType();
/* 101 */         if (raw == sup)
/*     */         {
/* 103 */           return p;
/*     */         }
/*     */         
/* 106 */         Type r = raw.getGenericSuperclass();
/* 107 */         if (r != null) {
/* 108 */           r = visit(bind(r, raw, p), sup);
/*     */         }
/* 110 */         if (r != null) {
/* 111 */           return r;
/*     */         }
/* 113 */         for (Type i : raw.getGenericInterfaces()) {
/* 114 */           r = visit(bind(i, raw, p), sup);
/* 115 */           if (r != null) {
/* 116 */             return r;
/*     */           }
/*     */         } 
/* 119 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public Type onGenericArray(GenericArrayType g, Class sup) {
/* 125 */         return null;
/*     */       }
/*     */       
/*     */       public Type onVariable(TypeVariable v, Class sup) {
/* 129 */         return visit(v.getBounds()[0], sup);
/*     */       }
/*     */ 
/*     */       
/*     */       public Type onWildcard(WildcardType w, Class sup) {
/* 134 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private Type bind(Type t, GenericDeclaration decl, ParameterizedType args) {
/* 146 */         return ReflectionNavigator.binder.visit(t, new ReflectionNavigator.BinderArg(decl, args.getActualTypeArguments()));
/*     */       }
/*     */     };
/*     */   
/*     */   private static class BinderArg
/*     */   {
/*     */     final TypeVariable[] params;
/*     */     final Type[] args;
/*     */     
/*     */     BinderArg(TypeVariable[] params, Type[] args) {
/* 156 */       this.params = params;
/* 157 */       this.args = args;
/* 158 */       assert params.length == args.length;
/*     */     }
/*     */     
/*     */     public BinderArg(GenericDeclaration decl, Type[] args) {
/* 162 */       this((TypeVariable[])decl.getTypeParameters(), args);
/*     */     }
/*     */     
/*     */     Type replace(TypeVariable v) {
/* 166 */       for (int i = 0; i < this.params.length; i++) {
/* 167 */         if (this.params[i].equals(v)) {
/* 168 */           return this.args[i];
/*     */         }
/*     */       } 
/* 171 */       return v;
/*     */     } }
/*     */   
/* 174 */   private static final TypeVisitor<Type, BinderArg> binder = new TypeVisitor<Type, BinderArg>()
/*     */     {
/*     */       public Type onClass(Class c, ReflectionNavigator.BinderArg args) {
/* 177 */         return c;
/*     */       }
/*     */       public Type onParameterizdType(ParameterizedType p, ReflectionNavigator.BinderArg args) {
/*     */         int j;
/* 181 */         Type[] params = p.getActualTypeArguments();
/*     */         
/* 183 */         boolean different = false;
/* 184 */         for (int i = 0; i < params.length; i++) {
/* 185 */           Type t = params[i];
/* 186 */           params[i] = visit(t, args);
/* 187 */           j = different | ((t != params[i]) ? 1 : 0);
/*     */         } 
/*     */         
/* 190 */         Type newOwner = p.getOwnerType();
/* 191 */         if (newOwner != null) {
/* 192 */           newOwner = visit(newOwner, args);
/*     */         }
/* 194 */         j |= (p.getOwnerType() != newOwner) ? 1 : 0;
/*     */         
/* 196 */         if (j == 0) {
/* 197 */           return p;
/*     */         }
/*     */         
/* 200 */         return new ParameterizedTypeImpl((Class)p.getRawType(), params, newOwner);
/*     */       }
/*     */       
/*     */       public Type onGenericArray(GenericArrayType g, ReflectionNavigator.BinderArg types) {
/* 204 */         Type c = visit(g.getGenericComponentType(), types);
/* 205 */         if (c == g.getGenericComponentType()) {
/* 206 */           return g;
/*     */         }
/*     */         
/* 209 */         return new GenericArrayTypeImpl(c);
/*     */       }
/*     */       
/*     */       public Type onVariable(TypeVariable v, ReflectionNavigator.BinderArg types) {
/* 213 */         return types.replace(v);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public Type onWildcard(WildcardType w, ReflectionNavigator.BinderArg types) {
/*     */         int j;
/* 220 */         Type[] lb = w.getLowerBounds();
/* 221 */         Type[] ub = w.getUpperBounds();
/* 222 */         boolean diff = false;
/*     */         int i;
/* 224 */         for (i = 0; i < lb.length; i++) {
/* 225 */           Type t = lb[i];
/* 226 */           lb[i] = visit(t, types);
/* 227 */           j = diff | ((t != lb[i]) ? 1 : 0);
/*     */         } 
/*     */         
/* 230 */         for (i = 0; i < ub.length; i++) {
/* 231 */           Type t = ub[i];
/* 232 */           ub[i] = visit(t, types);
/* 233 */           j |= (t != ub[i]) ? 1 : 0;
/*     */         } 
/*     */         
/* 236 */         if (j == 0) {
/* 237 */           return w;
/*     */         }
/*     */         
/* 240 */         return new WildcardTypeImpl(lb, ub);
/*     */       }
/*     */     };
/*     */   
/*     */   public Type getBaseClass(Type t, Class sup) {
/* 245 */     return baseClassFinder.visit(t, sup);
/*     */   }
/*     */   
/*     */   public String getClassName(Class clazz) {
/* 249 */     return clazz.getName();
/*     */   }
/*     */   
/*     */   public String getTypeName(Type type) {
/* 253 */     if (type instanceof Class) {
/* 254 */       Class c = (Class)type;
/* 255 */       if (c.isArray()) {
/* 256 */         return getTypeName(c.getComponentType()) + "[]";
/*     */       }
/* 258 */       return c.getName();
/*     */     } 
/* 260 */     return type.toString();
/*     */   }
/*     */   
/*     */   public String getClassShortName(Class clazz) {
/* 264 */     return clazz.getSimpleName();
/*     */   }
/*     */   
/*     */   public Collection<? extends Field> getDeclaredFields(Class clazz) {
/* 268 */     return Arrays.asList(clazz.getDeclaredFields());
/*     */   }
/*     */   
/*     */   public Field getDeclaredField(Class clazz, String fieldName) {
/*     */     try {
/* 273 */       return clazz.getDeclaredField(fieldName);
/* 274 */     } catch (NoSuchFieldException e) {
/* 275 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<? extends Method> getDeclaredMethods(Class clazz) {
/* 280 */     return Arrays.asList(clazz.getDeclaredMethods());
/*     */   }
/*     */   
/*     */   public Class getDeclaringClassForField(Field field) {
/* 284 */     return field.getDeclaringClass();
/*     */   }
/*     */   
/*     */   public Class getDeclaringClassForMethod(Method method) {
/* 288 */     return method.getDeclaringClass();
/*     */   }
/*     */   
/*     */   public Type getFieldType(Field field) {
/* 292 */     if (field.getType().isArray()) {
/* 293 */       Class<?> c = field.getType().getComponentType();
/* 294 */       if (c.isPrimitive()) {
/* 295 */         return Array.newInstance(c, 0).getClass();
/*     */       }
/*     */     } 
/* 298 */     return fix(field.getGenericType());
/*     */   }
/*     */   
/*     */   public String getFieldName(Field field) {
/* 302 */     return field.getName();
/*     */   }
/*     */   
/*     */   public String getMethodName(Method method) {
/* 306 */     return method.getName();
/*     */   }
/*     */   
/*     */   public Type getReturnType(Method method) {
/* 310 */     return fix(method.getGenericReturnType());
/*     */   }
/*     */   
/*     */   public Type[] getMethodParameters(Method method) {
/* 314 */     return method.getGenericParameterTypes();
/*     */   }
/*     */   
/*     */   public boolean isStaticMethod(Method method) {
/* 318 */     return Modifier.isStatic(method.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isFinalMethod(Method method) {
/* 322 */     return Modifier.isFinal(method.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isSubClassOf(Type sub, Type sup) {
/* 326 */     return erasure(sup).isAssignableFrom(erasure(sub));
/*     */   }
/*     */   
/*     */   public Class ref(Class c) {
/* 330 */     return c;
/*     */   }
/*     */   
/*     */   public Class use(Class c) {
/* 334 */     return c;
/*     */   }
/*     */   
/*     */   public Class asDecl(Type t) {
/* 338 */     return erasure(t);
/*     */   }
/*     */   
/*     */   public Class asDecl(Class c) {
/* 342 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 347 */   private static final TypeVisitor<Class, Void> eraser = new TypeVisitor<Class, Void>()
/*     */     {
/*     */       public Class onClass(Class c, Void v) {
/* 350 */         return c;
/*     */       }
/*     */ 
/*     */       
/*     */       public Class onParameterizdType(ParameterizedType p, Void v) {
/* 355 */         return visit(p.getRawType(), null);
/*     */       }
/*     */       
/*     */       public Class onGenericArray(GenericArrayType g, Void v) {
/* 359 */         return Array.newInstance(
/* 360 */             visit(g.getGenericComponentType(), null), 0)
/* 361 */           .getClass();
/*     */       }
/*     */       
/*     */       public Class onVariable(TypeVariable tv, Void v) {
/* 365 */         return visit(tv.getBounds()[0], null);
/*     */       }
/*     */       
/*     */       public Class onWildcard(WildcardType w, Void v) {
/* 369 */         return visit(w.getUpperBounds()[0], null);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Class<T> erasure(Type t) {
/* 389 */     return eraser.visit(t, null);
/*     */   }
/*     */   
/*     */   public boolean isAbstract(Class clazz) {
/* 393 */     return Modifier.isAbstract(clazz.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isFinal(Class clazz) {
/* 397 */     return Modifier.isFinal(clazz.getModifiers());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type createParameterizedType(Class<?> rawType, Type... arguments) {
/* 404 */     return new ParameterizedTypeImpl(rawType, arguments, null);
/*     */   }
/*     */   
/*     */   public boolean isArray(Type t) {
/* 408 */     if (t instanceof Class) {
/* 409 */       Class c = (Class)t;
/* 410 */       return c.isArray();
/*     */     } 
/* 412 */     if (t instanceof GenericArrayType) {
/* 413 */       return true;
/*     */     }
/* 415 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isArrayButNotByteArray(Type t) {
/* 419 */     if (t instanceof Class) {
/* 420 */       Class<byte[]> c = (Class)t;
/* 421 */       return (c.isArray() && c != byte[].class);
/*     */     } 
/* 423 */     if (t instanceof GenericArrayType) {
/* 424 */       t = ((GenericArrayType)t).getGenericComponentType();
/* 425 */       return (t != byte.class);
/*     */     } 
/* 427 */     return false;
/*     */   }
/*     */   
/*     */   public Type getComponentType(Type t) {
/* 431 */     if (t instanceof Class) {
/* 432 */       Class c = (Class)t;
/* 433 */       return c.getComponentType();
/*     */     } 
/* 435 */     if (t instanceof GenericArrayType) {
/* 436 */       return ((GenericArrayType)t).getGenericComponentType();
/*     */     }
/*     */     
/* 439 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public Type getTypeArgument(Type type, int i) {
/* 443 */     if (type instanceof ParameterizedType) {
/* 444 */       ParameterizedType p = (ParameterizedType)type;
/* 445 */       return fix(p.getActualTypeArguments()[i]);
/*     */     } 
/* 447 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParameterizedType(Type type) {
/* 452 */     return type instanceof ParameterizedType;
/*     */   }
/*     */   
/*     */   public boolean isPrimitive(Type type) {
/* 456 */     if (type instanceof Class) {
/* 457 */       Class c = (Class)type;
/* 458 */       return c.isPrimitive();
/*     */     } 
/* 460 */     return false;
/*     */   }
/*     */   
/*     */   public Type getPrimitive(Class primitiveType) {
/* 464 */     assert primitiveType.isPrimitive();
/* 465 */     return primitiveType;
/*     */   }
/*     */   
/*     */   public Location getClassLocation(final Class clazz) {
/* 469 */     return new Location()
/*     */       {
/*     */         public String toString()
/*     */         {
/* 473 */           return clazz.getName();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public Location getFieldLocation(final Field field) {
/* 479 */     return new Location()
/*     */       {
/*     */         public String toString()
/*     */         {
/* 483 */           return field.toString();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public Location getMethodLocation(final Method method) {
/* 489 */     return new Location()
/*     */       {
/*     */         public String toString()
/*     */         {
/* 493 */           return method.toString();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean hasDefaultConstructor(Class c) {
/*     */     try {
/* 500 */       c.getDeclaredConstructor(new Class[0]);
/* 501 */       return true;
/* 502 */     } catch (NoSuchMethodException e) {
/* 503 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isStaticField(Field field) {
/* 508 */     return Modifier.isStatic(field.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isPublicMethod(Method method) {
/* 512 */     return Modifier.isPublic(method.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isPublicField(Field field) {
/* 516 */     return Modifier.isPublic(field.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isEnum(Class<?> c) {
/* 520 */     return Enum.class.isAssignableFrom(c);
/*     */   }
/*     */   
/*     */   public Field[] getEnumConstants(Class<Object> clazz) {
/*     */     try {
/* 525 */       Object[] values = clazz.getEnumConstants();
/* 526 */       Field[] fields = new Field[values.length];
/* 527 */       for (int i = 0; i < values.length; i++) {
/* 528 */         fields[i] = clazz.getField(((Enum<Enum>)values[i]).name());
/*     */       }
/* 530 */       return fields;
/* 531 */     } catch (NoSuchFieldException e) {
/*     */       
/* 533 */       throw new NoSuchFieldError(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Type getVoidType() {
/* 538 */     return Void.class;
/*     */   }
/*     */   
/*     */   public String getPackageName(Class clazz) {
/* 542 */     String name = clazz.getName();
/* 543 */     int idx = name.lastIndexOf('.');
/* 544 */     if (idx < 0) {
/* 545 */       return "";
/*     */     }
/* 547 */     return name.substring(0, idx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class loadObjectFactory(Class referencePoint, String pkg) {
/* 553 */     ClassLoader cl = SecureLoader.getClassClassLoader(referencePoint);
/* 554 */     if (cl == null) {
/* 555 */       cl = SecureLoader.getSystemClassLoader();
/*     */     }
/*     */     try {
/* 558 */       return cl.loadClass(pkg + ".ObjectFactory");
/* 559 */     } catch (ClassNotFoundException e) {
/* 560 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isBridgeMethod(Method method) {
/* 565 */     return method.isBridge();
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
/*     */   public boolean isOverriding(Method method, Class base) {
/* 579 */     String name = method.getName();
/* 580 */     Class[] params = method.getParameterTypes();
/*     */     
/* 582 */     while (base != null) {
/*     */       try {
/* 584 */         if (base.getDeclaredMethod(name, params) != null) {
/* 585 */           return true;
/*     */         }
/* 587 */       } catch (NoSuchMethodException noSuchMethodException) {}
/*     */ 
/*     */ 
/*     */       
/* 591 */       base = base.getSuperclass();
/*     */     } 
/*     */     
/* 594 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isInterface(Class clazz) {
/* 598 */     return clazz.isInterface();
/*     */   }
/*     */   
/*     */   public boolean isTransient(Field f) {
/* 602 */     return Modifier.isTransient(f.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isInnerClass(Class clazz) {
/* 606 */     return (clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSameType(Type t1, Type t2) {
/* 611 */     return t1.equals(t2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Type fix(Type t) {
/* 621 */     if (!(t instanceof GenericArrayType)) {
/* 622 */       return t;
/*     */     }
/*     */     
/* 625 */     GenericArrayType gat = (GenericArrayType)t;
/* 626 */     if (gat.getGenericComponentType() instanceof Class) {
/* 627 */       Class<?> c = (Class)gat.getGenericComponentType();
/* 628 */       return Array.newInstance(c, 0).getClass();
/*     */     } 
/*     */     
/* 631 */     return t;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/nav/ReflectionNavigator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */