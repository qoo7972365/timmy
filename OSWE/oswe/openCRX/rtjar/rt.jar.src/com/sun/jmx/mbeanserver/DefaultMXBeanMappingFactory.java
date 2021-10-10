/*      */ package com.sun.jmx.mbeanserver;
/*      */ 
/*      */ import com.sun.jmx.remote.util.EnvHelp;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.annotation.ElementType;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.GenericArrayType;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.TreeSet;
/*      */ import java.util.WeakHashMap;
/*      */ import javax.management.JMX;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.openmbean.ArrayType;
/*      */ import javax.management.openmbean.CompositeData;
/*      */ import javax.management.openmbean.CompositeDataInvocationHandler;
/*      */ import javax.management.openmbean.CompositeDataSupport;
/*      */ import javax.management.openmbean.CompositeDataView;
/*      */ import javax.management.openmbean.CompositeType;
/*      */ import javax.management.openmbean.OpenDataException;
/*      */ import javax.management.openmbean.OpenType;
/*      */ import javax.management.openmbean.SimpleType;
/*      */ import javax.management.openmbean.TabularData;
/*      */ import javax.management.openmbean.TabularDataSupport;
/*      */ import javax.management.openmbean.TabularType;
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
/*      */ public class DefaultMXBeanMappingFactory
/*      */   extends MXBeanMappingFactory
/*      */ {
/*      */   static abstract class NonNullMXBeanMapping
/*      */     extends MXBeanMapping
/*      */   {
/*      */     NonNullMXBeanMapping(Type param1Type, OpenType<?> param1OpenType) {
/*  124 */       super(param1Type, param1OpenType);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final Object fromOpenValue(Object param1Object) throws InvalidObjectException {
/*  130 */       if (param1Object == null) {
/*  131 */         return null;
/*      */       }
/*  133 */       return fromNonNullOpenValue(param1Object);
/*      */     }
/*      */ 
/*      */     
/*      */     public final Object toOpenValue(Object param1Object) throws OpenDataException {
/*  138 */       if (param1Object == null) {
/*  139 */         return null;
/*      */       }
/*  141 */       return toNonNullOpenValue(param1Object);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     abstract Object fromNonNullOpenValue(Object param1Object) throws InvalidObjectException;
/*      */ 
/*      */ 
/*      */     
/*      */     abstract Object toNonNullOpenValue(Object param1Object) throws OpenDataException;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isIdentity() {
/*  155 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   static boolean isIdentity(MXBeanMapping paramMXBeanMapping) {
/*  160 */     return (paramMXBeanMapping instanceof NonNullMXBeanMapping && ((NonNullMXBeanMapping)paramMXBeanMapping)
/*  161 */       .isIdentity());
/*      */   }
/*      */   
/*      */   private static final class Mappings extends WeakHashMap<Type, WeakReference<MXBeanMapping>> {
/*      */     private Mappings() {} }
/*      */   
/*  167 */   private static final Mappings mappings = new Mappings();
/*      */ 
/*      */ 
/*      */   
/*  171 */   private static final List<MXBeanMapping> permanentMappings = Util.newList();
/*      */   
/*      */   private static synchronized MXBeanMapping getMapping(Type paramType) {
/*  174 */     WeakReference<MXBeanMapping> weakReference = mappings.get(paramType);
/*  175 */     return (weakReference == null) ? null : weakReference.get();
/*      */   }
/*      */   
/*      */   private static synchronized void putMapping(Type paramType, MXBeanMapping paramMXBeanMapping) {
/*  179 */     WeakReference<MXBeanMapping> weakReference = new WeakReference<>(paramMXBeanMapping);
/*      */     
/*  181 */     mappings.put(paramType, weakReference);
/*      */   }
/*      */ 
/*      */   
/*      */   private static synchronized void putPermanentMapping(Type paramType, MXBeanMapping paramMXBeanMapping) {
/*  186 */     putMapping(paramType, paramMXBeanMapping);
/*  187 */     permanentMappings.add(paramMXBeanMapping);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  193 */     OpenType[] arrayOfOpenType = { SimpleType.BIGDECIMAL, SimpleType.BIGINTEGER, SimpleType.BOOLEAN, SimpleType.BYTE, SimpleType.CHARACTER, SimpleType.DATE, SimpleType.DOUBLE, SimpleType.FLOAT, SimpleType.INTEGER, SimpleType.LONG, SimpleType.OBJECTNAME, SimpleType.SHORT, SimpleType.STRING, SimpleType.VOID };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  199 */     for (byte b = 0; b < arrayOfOpenType.length; b++) {
/*  200 */       Class<?> clazz; OpenType<?> openType = arrayOfOpenType[b];
/*      */       
/*      */       try {
/*  203 */         clazz = Class.forName(openType.getClassName(), false, ObjectName.class
/*  204 */             .getClassLoader());
/*  205 */       } catch (ClassNotFoundException classNotFoundException) {
/*      */         
/*  207 */         throw new Error(classNotFoundException);
/*      */       } 
/*  209 */       IdentityMapping identityMapping = new IdentityMapping(clazz, openType);
/*  210 */       putPermanentMapping(clazz, identityMapping);
/*      */       
/*  212 */       if (clazz.getName().startsWith("java.lang.")) {
/*      */         try {
/*  214 */           Field field = clazz.getField("TYPE");
/*  215 */           Class<void> clazz1 = (Class)field.get(null);
/*  216 */           IdentityMapping identityMapping1 = new IdentityMapping(clazz1, openType);
/*      */           
/*  218 */           putPermanentMapping(clazz1, identityMapping1);
/*  219 */           if (clazz1 != void.class)
/*      */           {
/*  221 */             Class<?> clazz2 = Array.newInstance(clazz1, 0).getClass();
/*      */             
/*  223 */             ArrayType<?> arrayType = ArrayType.getPrimitiveArrayType(clazz2);
/*  224 */             IdentityMapping identityMapping2 = new IdentityMapping(clazz2, arrayType);
/*      */ 
/*      */             
/*  227 */             putPermanentMapping(clazz2, identityMapping2);
/*      */           }
/*      */         
/*  230 */         } catch (NoSuchFieldException noSuchFieldException) {
/*      */         
/*  232 */         } catch (IllegalAccessException illegalAccessException) {
/*      */           assert false;
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized MXBeanMapping mappingForType(Type paramType, MXBeanMappingFactory paramMXBeanMappingFactory) throws OpenDataException {
/*  245 */     if (inProgress.containsKey(paramType)) {
/*  246 */       throw new OpenDataException("Recursive data structure, including " + 
/*  247 */           MXBeanIntrospector.typeName(paramType));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  252 */     MXBeanMapping mXBeanMapping = getMapping(paramType);
/*  253 */     if (mXBeanMapping != null) {
/*  254 */       return mXBeanMapping;
/*      */     }
/*  256 */     inProgress.put(paramType, paramType);
/*      */     try {
/*  258 */       mXBeanMapping = makeMapping(paramType, paramMXBeanMappingFactory);
/*  259 */     } catch (OpenDataException openDataException) {
/*  260 */       throw openDataException("Cannot convert type: " + MXBeanIntrospector.typeName(paramType), openDataException);
/*      */     } finally {
/*  262 */       inProgress.remove(paramType);
/*      */     } 
/*      */     
/*  265 */     putMapping(paramType, mXBeanMapping);
/*  266 */     return mXBeanMapping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MXBeanMapping makeMapping(Type paramType, MXBeanMappingFactory paramMXBeanMappingFactory) throws OpenDataException {
/*  275 */     if (paramType instanceof GenericArrayType) {
/*      */       
/*  277 */       Type type = ((GenericArrayType)paramType).getGenericComponentType();
/*  278 */       return makeArrayOrCollectionMapping(paramType, type, paramMXBeanMappingFactory);
/*  279 */     }  if (paramType instanceof Class) {
/*  280 */       Class<?> clazz = (Class)paramType;
/*  281 */       if (clazz.isEnum())
/*      */       {
/*      */ 
/*      */         
/*  285 */         return makeEnumMapping(clazz, ElementType.class); } 
/*  286 */       if (clazz.isArray()) {
/*  287 */         Class<?> clazz1 = clazz.getComponentType();
/*  288 */         return makeArrayOrCollectionMapping(clazz, clazz1, paramMXBeanMappingFactory);
/*      */       } 
/*  290 */       if (JMX.isMXBeanInterface(clazz)) {
/*  291 */         return makeMXBeanRefMapping(clazz);
/*      */       }
/*  293 */       return makeCompositeMapping(clazz, paramMXBeanMappingFactory);
/*      */     } 
/*  295 */     if (paramType instanceof ParameterizedType) {
/*  296 */       return makeParameterizedTypeMapping((ParameterizedType)paramType, paramMXBeanMappingFactory);
/*      */     }
/*      */     
/*  299 */     throw new OpenDataException("Cannot map type: " + paramType);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T extends Enum<T>> MXBeanMapping makeEnumMapping(Class<?> paramClass, Class<T> paramClass1) {
/*  304 */     ReflectUtil.checkPackageAccess(paramClass);
/*  305 */     return new EnumMapping<>(Util.<Class<Enum>>cast(paramClass));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MXBeanMapping makeArrayOrCollectionMapping(Type paramType1, Type paramType2, MXBeanMappingFactory paramMXBeanMappingFactory) throws OpenDataException {
/*      */     Class<?> clazz2;
/*      */     String str;
/*  318 */     MXBeanMapping mXBeanMapping = paramMXBeanMappingFactory.mappingForType(paramType2, paramMXBeanMappingFactory);
/*  319 */     OpenType<?> openType = mXBeanMapping.getOpenType();
/*  320 */     ArrayType<?> arrayType = ArrayType.getArrayType(openType);
/*  321 */     Class<?> clazz1 = mXBeanMapping.getOpenClass();
/*      */ 
/*      */ 
/*      */     
/*  325 */     if (clazz1.isArray()) {
/*  326 */       str = "[" + clazz1.getName();
/*      */     } else {
/*  328 */       str = "[L" + clazz1.getName() + ";";
/*      */     }  try {
/*  330 */       clazz2 = Class.forName(str);
/*  331 */     } catch (ClassNotFoundException classNotFoundException) {
/*  332 */       throw openDataException("Cannot obtain array class", classNotFoundException);
/*      */     } 
/*      */     
/*  335 */     if (paramType1 instanceof ParameterizedType) {
/*  336 */       return new CollectionMapping(paramType1, arrayType, clazz2, mXBeanMapping);
/*      */     }
/*      */ 
/*      */     
/*  340 */     if (isIdentity(mXBeanMapping)) {
/*  341 */       return new IdentityMapping(paramType1, arrayType);
/*      */     }
/*      */     
/*  344 */     return new ArrayMapping(paramType1, arrayType, clazz2, mXBeanMapping);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  352 */   private static final String[] keyArray = new String[] { "key" };
/*  353 */   private static final String[] keyValueArray = new String[] { "key", "value" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MXBeanMapping makeTabularMapping(Type paramType1, boolean paramBoolean, Type paramType2, Type paramType3, MXBeanMappingFactory paramMXBeanMappingFactory) throws OpenDataException {
/*  361 */     String str = MXBeanIntrospector.typeName(paramType1);
/*  362 */     MXBeanMapping mXBeanMapping1 = paramMXBeanMappingFactory.mappingForType(paramType2, paramMXBeanMappingFactory);
/*  363 */     MXBeanMapping mXBeanMapping2 = paramMXBeanMappingFactory.mappingForType(paramType3, paramMXBeanMappingFactory);
/*  364 */     OpenType<?> openType1 = mXBeanMapping1.getOpenType();
/*  365 */     OpenType<?> openType2 = mXBeanMapping2.getOpenType();
/*  366 */     CompositeType compositeType = new CompositeType(str, str, keyValueArray, keyValueArray, (OpenType<?>[])new OpenType[] { openType1, openType2 });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  372 */     TabularType tabularType = new TabularType(str, str, compositeType, keyArray);
/*      */     
/*  374 */     return new TabularMapping(paramType1, paramBoolean, tabularType, mXBeanMapping1, mXBeanMapping2);
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
/*      */   private MXBeanMapping makeParameterizedTypeMapping(ParameterizedType paramParameterizedType, MXBeanMappingFactory paramMXBeanMappingFactory) throws OpenDataException {
/*  388 */     Type type = paramParameterizedType.getRawType();
/*      */     
/*  390 */     if (type instanceof Class) {
/*  391 */       Class<List> clazz = (Class)type;
/*  392 */       if (clazz == List.class || clazz == Set.class || clazz == SortedSet.class) {
/*  393 */         Type[] arrayOfType = paramParameterizedType.getActualTypeArguments();
/*  394 */         assert arrayOfType.length == 1;
/*  395 */         if (clazz == SortedSet.class)
/*  396 */           mustBeComparable(clazz, arrayOfType[0]); 
/*  397 */         return makeArrayOrCollectionMapping(paramParameterizedType, arrayOfType[0], paramMXBeanMappingFactory);
/*      */       } 
/*  399 */       boolean bool = (clazz == SortedMap.class) ? true : false;
/*  400 */       if (clazz == Map.class || bool) {
/*  401 */         Type[] arrayOfType = paramParameterizedType.getActualTypeArguments();
/*  402 */         assert arrayOfType.length == 2;
/*  403 */         if (bool)
/*  404 */           mustBeComparable(clazz, arrayOfType[0]); 
/*  405 */         return makeTabularMapping(paramParameterizedType, bool, arrayOfType[0], arrayOfType[1], paramMXBeanMappingFactory);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  410 */     throw new OpenDataException("Cannot convert type: " + paramParameterizedType);
/*      */   }
/*      */ 
/*      */   
/*      */   private static MXBeanMapping makeMXBeanRefMapping(Type paramType) throws OpenDataException {
/*  415 */     return new MXBeanRefMapping(paramType);
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
/*      */   private MXBeanMapping makeCompositeMapping(Class<?> paramClass, MXBeanMappingFactory paramMXBeanMappingFactory) throws OpenDataException {
/*  427 */     boolean bool = (paramClass.getName().equals("com.sun.management.GcInfo") && paramClass.getClassLoader() == null) ? true : false;
/*      */     
/*  429 */     ReflectUtil.checkPackageAccess(paramClass);
/*      */     
/*  431 */     List<Method> list = MBeanAnalyzer.eliminateCovariantMethods(Arrays.asList(paramClass.getMethods()));
/*  432 */     SortedMap<?, ?> sortedMap = Util.newSortedMap();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  437 */     for (Method method1 : list) {
/*  438 */       String str = propertyName(method1);
/*      */       
/*  440 */       if (str == null)
/*      */         continue; 
/*  442 */       if (bool && str.equals("CompositeType")) {
/*      */         continue;
/*      */       }
/*      */       
/*  446 */       Method method2 = (Method)sortedMap.put(decapitalize(str), method1);
/*      */       
/*  448 */       if (method2 != null) {
/*      */ 
/*      */         
/*  451 */         String str1 = "Class " + paramClass.getName() + " has method name clash: " + method2.getName() + ", " + method1.getName();
/*  452 */         throw new OpenDataException(str1);
/*      */       } 
/*      */     } 
/*      */     
/*  456 */     int i = sortedMap.size();
/*      */     
/*  458 */     if (i == 0) {
/*  459 */       throw new OpenDataException("Can't map " + paramClass.getName() + " to an open data type");
/*      */     }
/*      */ 
/*      */     
/*  463 */     Method[] arrayOfMethod = new Method[i];
/*  464 */     String[] arrayOfString = new String[i];
/*  465 */     OpenType[] arrayOfOpenType = new OpenType[i];
/*  466 */     byte b = 0;
/*  467 */     for (Map.Entry<?, ?> entry : sortedMap.entrySet()) {
/*  468 */       arrayOfString[b] = (String)entry.getKey();
/*  469 */       Method method = (Method)entry.getValue();
/*  470 */       arrayOfMethod[b] = method;
/*  471 */       Type type = method.getGenericReturnType();
/*  472 */       arrayOfOpenType[b] = paramMXBeanMappingFactory.mappingForType(type, paramMXBeanMappingFactory).getOpenType();
/*  473 */       b++;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  478 */     CompositeType compositeType = new CompositeType(paramClass.getName(), paramClass.getName(), arrayOfString, arrayOfString, (OpenType<?>[])arrayOfOpenType);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  483 */     return new CompositeMapping(paramClass, compositeType, arrayOfString, arrayOfMethod, paramMXBeanMappingFactory);
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
/*      */   private static final class IdentityMapping
/*      */     extends NonNullMXBeanMapping
/*      */   {
/*      */     IdentityMapping(Type param1Type, OpenType<?> param1OpenType) {
/*  498 */       super(param1Type, param1OpenType);
/*      */     }
/*      */     
/*      */     boolean isIdentity() {
/*  502 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     Object fromNonNullOpenValue(Object param1Object) throws InvalidObjectException {
/*  508 */       return param1Object;
/*      */     }
/*      */ 
/*      */     
/*      */     Object toNonNullOpenValue(Object param1Object) throws OpenDataException {
/*  513 */       return param1Object;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class EnumMapping<T extends Enum<T>> extends NonNullMXBeanMapping {
/*      */     private final Class<T> enumClass;
/*      */     
/*      */     EnumMapping(Class<T> param1Class) {
/*  521 */       super(param1Class, SimpleType.STRING);
/*  522 */       this.enumClass = param1Class;
/*      */     }
/*      */ 
/*      */     
/*      */     final Object toNonNullOpenValue(Object param1Object) {
/*  527 */       return ((Enum)param1Object).name();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final T fromNonNullOpenValue(Object param1Object) throws InvalidObjectException {
/*      */       try {
/*  534 */         return Enum.valueOf(this.enumClass, (String)param1Object);
/*  535 */       } catch (Exception exception) {
/*  536 */         throw DefaultMXBeanMappingFactory.invalidObjectException("Cannot convert to enum: " + param1Object, exception);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class ArrayMapping
/*      */     extends NonNullMXBeanMapping
/*      */   {
/*      */     private final MXBeanMapping elementMapping;
/*      */     
/*      */     ArrayMapping(Type param1Type, ArrayType<?> param1ArrayType, Class<?> param1Class, MXBeanMapping param1MXBeanMapping) {
/*  548 */       super(param1Type, param1ArrayType);
/*  549 */       this.elementMapping = param1MXBeanMapping;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final Object toNonNullOpenValue(Object param1Object) throws OpenDataException {
/*  555 */       Object[] arrayOfObject1 = (Object[])param1Object;
/*  556 */       int i = arrayOfObject1.length;
/*      */       
/*  558 */       Object[] arrayOfObject2 = (Object[])Array.newInstance(getOpenClass().getComponentType(), i);
/*  559 */       for (byte b = 0; b < i; b++)
/*  560 */         arrayOfObject2[b] = this.elementMapping.toOpenValue(arrayOfObject1[b]); 
/*  561 */       return arrayOfObject2;
/*      */     }
/*      */ 
/*      */     
/*      */     final Object fromNonNullOpenValue(Object param1Object) throws InvalidObjectException {
/*      */       Type type2;
/*  567 */       Object[] arrayOfObject1 = (Object[])param1Object;
/*  568 */       Type type1 = getJavaType();
/*      */ 
/*      */       
/*  571 */       if (type1 instanceof GenericArrayType) {
/*      */         
/*  573 */         type2 = ((GenericArrayType)type1).getGenericComponentType();
/*  574 */       } else if (type1 instanceof Class && ((Class)type1)
/*  575 */         .isArray()) {
/*  576 */         type2 = ((Class)type1).getComponentType();
/*      */       } else {
/*  578 */         throw new IllegalArgumentException("Not an array: " + type1);
/*      */       } 
/*      */       
/*  581 */       Object[] arrayOfObject2 = (Object[])Array.newInstance((Class)type2, arrayOfObject1.length);
/*      */       
/*  583 */       for (byte b = 0; b < arrayOfObject1.length; b++)
/*  584 */         arrayOfObject2[b] = this.elementMapping.fromOpenValue(arrayOfObject1[b]); 
/*  585 */       return arrayOfObject2;
/*      */     }
/*      */     
/*      */     public void checkReconstructible() throws InvalidObjectException {
/*  589 */       this.elementMapping.checkReconstructible();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class CollectionMapping
/*      */     extends NonNullMXBeanMapping
/*      */   {
/*      */     private final Class<? extends Collection<?>> collectionClass;
/*      */ 
/*      */     
/*      */     private final MXBeanMapping elementMapping;
/*      */ 
/*      */     
/*      */     CollectionMapping(Type param1Type, ArrayType<?> param1ArrayType, Class<?> param1Class, MXBeanMapping param1MXBeanMapping) {
/*  605 */       super(param1Type, param1ArrayType); Object object;
/*  606 */       this.elementMapping = param1MXBeanMapping;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  612 */       Type type = ((ParameterizedType)param1Type).getRawType();
/*  613 */       Class<List> clazz = (Class)type;
/*      */       
/*  615 */       if (clazz == List.class) {
/*  616 */         object = ArrayList.class;
/*  617 */       } else if (clazz == Set.class) {
/*  618 */         object = HashSet.class;
/*  619 */       } else if (clazz == SortedSet.class) {
/*  620 */         object = TreeSet.class;
/*      */       } else {
/*      */         assert false;
/*  623 */         object = null;
/*      */       } 
/*  625 */       this.collectionClass = Util.<Class<? extends Collection<?>>>cast(object);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final Object toNonNullOpenValue(Object param1Object) throws OpenDataException {
/*  631 */       Collection collection = (Collection)param1Object;
/*  632 */       if (collection instanceof SortedSet) {
/*      */         
/*  634 */         Comparator comparator = ((SortedSet)collection).comparator();
/*  635 */         if (comparator != null) {
/*  636 */           String str = "Cannot convert SortedSet with non-null comparator: " + comparator;
/*      */ 
/*      */           
/*  639 */           throw DefaultMXBeanMappingFactory.openDataException(str, new IllegalArgumentException(str));
/*      */         } 
/*      */       } 
/*      */       
/*  643 */       Object[] arrayOfObject = (Object[])Array.newInstance(getOpenClass().getComponentType(), collection
/*  644 */           .size());
/*  645 */       byte b = 0;
/*  646 */       for (Object object : collection)
/*  647 */         arrayOfObject[b++] = this.elementMapping.toOpenValue(object); 
/*  648 */       return arrayOfObject;
/*      */     }
/*      */ 
/*      */     
/*      */     final Object fromNonNullOpenValue(Object param1Object) throws InvalidObjectException {
/*      */       Collection<Object> collection;
/*  654 */       Object[] arrayOfObject = (Object[])param1Object;
/*      */       
/*      */       try {
/*  657 */         collection = Util.<Collection>cast(this.collectionClass.newInstance());
/*  658 */       } catch (Exception exception) {
/*  659 */         throw DefaultMXBeanMappingFactory.invalidObjectException("Cannot create collection", exception);
/*      */       } 
/*  661 */       for (Object object1 : arrayOfObject) {
/*  662 */         Object object2 = this.elementMapping.fromOpenValue(object1);
/*  663 */         if (!collection.add(object2)) {
/*      */ 
/*      */           
/*  666 */           String str = "Could not add " + object1 + " to " + this.collectionClass.getName() + " (duplicate set element?)";
/*      */           
/*  668 */           throw new InvalidObjectException(str);
/*      */         } 
/*      */       } 
/*  671 */       return collection;
/*      */     }
/*      */     
/*      */     public void checkReconstructible() throws InvalidObjectException {
/*  675 */       this.elementMapping.checkReconstructible();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class MXBeanRefMapping
/*      */     extends NonNullMXBeanMapping
/*      */   {
/*      */     MXBeanRefMapping(Type param1Type) {
/*  684 */       super(param1Type, SimpleType.OBJECTNAME);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final Object toNonNullOpenValue(Object param1Object) throws OpenDataException {
/*  690 */       MXBeanLookup mXBeanLookup = lookupNotNull(OpenDataException.class);
/*  691 */       ObjectName objectName = mXBeanLookup.mxbeanToObjectName(param1Object);
/*  692 */       if (objectName == null)
/*  693 */         throw new OpenDataException("No name for object: " + param1Object); 
/*  694 */       return objectName;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final Object fromNonNullOpenValue(Object param1Object) throws InvalidObjectException {
/*  700 */       MXBeanLookup mXBeanLookup = lookupNotNull(InvalidObjectException.class);
/*  701 */       ObjectName objectName = (ObjectName)param1Object;
/*      */       
/*  703 */       Object object = mXBeanLookup.objectNameToMXBean(objectName, (Class<Object>)getJavaType());
/*  704 */       if (object == null) {
/*  705 */         String str = "No MXBean for name: " + objectName;
/*      */         
/*  707 */         throw new InvalidObjectException(str);
/*      */       } 
/*  709 */       return object;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private <T extends Exception> MXBeanLookup lookupNotNull(Class<T> param1Class) throws T {
/*  715 */       MXBeanLookup mXBeanLookup = MXBeanLookup.getLookup();
/*  716 */       if (mXBeanLookup == null) {
/*      */         Exception exception;
/*      */ 
/*      */         
/*      */         try {
/*  721 */           Constructor<T> constructor = param1Class.getConstructor(new Class[] { String.class });
/*  722 */           exception = (Exception)constructor.newInstance(new Object[] { "Cannot convert MXBean interface in this context" });
/*  723 */         } catch (Exception exception1) {
/*  724 */           throw (T)new RuntimeException(exception1);
/*      */         } 
/*  726 */         throw (T)exception;
/*      */       } 
/*  728 */       return mXBeanLookup;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class TabularMapping extends NonNullMXBeanMapping {
/*      */     private final boolean sortedMap;
/*      */     private final MXBeanMapping keyMapping;
/*      */     private final MXBeanMapping valueMapping;
/*      */     
/*      */     TabularMapping(Type param1Type, boolean param1Boolean, TabularType param1TabularType, MXBeanMapping param1MXBeanMapping1, MXBeanMapping param1MXBeanMapping2) {
/*  738 */       super(param1Type, param1TabularType);
/*  739 */       this.sortedMap = param1Boolean;
/*  740 */       this.keyMapping = param1MXBeanMapping1;
/*  741 */       this.valueMapping = param1MXBeanMapping2;
/*      */     }
/*      */ 
/*      */     
/*      */     final Object toNonNullOpenValue(Object param1Object) throws OpenDataException {
/*  746 */       Map map = Util.<Map>cast(param1Object);
/*  747 */       if (map instanceof SortedMap) {
/*  748 */         Comparator comparator = ((SortedMap)map).comparator();
/*  749 */         if (comparator != null) {
/*  750 */           String str = "Cannot convert SortedMap with non-null comparator: " + comparator;
/*      */ 
/*      */           
/*  753 */           throw DefaultMXBeanMappingFactory.openDataException(str, new IllegalArgumentException(str));
/*      */         } 
/*      */       } 
/*  756 */       TabularType tabularType = (TabularType)getOpenType();
/*  757 */       TabularDataSupport tabularDataSupport = new TabularDataSupport(tabularType);
/*  758 */       CompositeType compositeType = tabularType.getRowType();
/*  759 */       for (Map.Entry entry : map.entrySet()) {
/*  760 */         Object object1 = this.keyMapping.toOpenValue(entry.getKey());
/*  761 */         Object object2 = this.valueMapping.toOpenValue(entry.getValue());
/*      */ 
/*      */         
/*  764 */         CompositeDataSupport compositeDataSupport = new CompositeDataSupport(compositeType, DefaultMXBeanMappingFactory.keyValueArray, new Object[] { object1, object2 });
/*      */ 
/*      */         
/*  767 */         tabularDataSupport.put(compositeDataSupport);
/*      */       } 
/*  769 */       return tabularDataSupport;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final Object fromNonNullOpenValue(Object param1Object) throws InvalidObjectException {
/*  775 */       TabularData tabularData = (TabularData)param1Object;
/*  776 */       Collection collection = Util.<Collection>cast(tabularData.values());
/*      */       
/*  778 */       Map<Object, Object> map = this.sortedMap ? Util.newSortedMap() : Util.newInsertionOrderMap();
/*  779 */       for (CompositeData compositeData : collection) {
/*      */         
/*  781 */         Object object1 = this.keyMapping.fromOpenValue(compositeData.get("key"));
/*      */         
/*  783 */         Object object2 = this.valueMapping.fromOpenValue(compositeData.get("value"));
/*  784 */         if (map.put(object1, object2) != null) {
/*  785 */           String str = "Duplicate entry in TabularData: key=" + object1;
/*      */           
/*  787 */           throw new InvalidObjectException(str);
/*      */         } 
/*      */       } 
/*  790 */       return map;
/*      */     }
/*      */ 
/*      */     
/*      */     public void checkReconstructible() throws InvalidObjectException {
/*  795 */       this.keyMapping.checkReconstructible();
/*  796 */       this.valueMapping.checkReconstructible();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final class CompositeMapping
/*      */     extends NonNullMXBeanMapping
/*      */   {
/*      */     private final String[] itemNames;
/*      */     private final Method[] getters;
/*      */     private final MXBeanMapping[] getterMappings;
/*      */     private DefaultMXBeanMappingFactory.CompositeBuilder compositeBuilder;
/*      */     
/*      */     CompositeMapping(Class<?> param1Class, CompositeType param1CompositeType, String[] param1ArrayOfString, Method[] param1ArrayOfMethod, MXBeanMappingFactory param1MXBeanMappingFactory) throws OpenDataException {
/*  810 */       super(param1Class, param1CompositeType);
/*      */       
/*  812 */       assert param1ArrayOfString.length == param1ArrayOfMethod.length;
/*      */       
/*  814 */       this.itemNames = param1ArrayOfString;
/*  815 */       this.getters = param1ArrayOfMethod;
/*  816 */       this.getterMappings = new MXBeanMapping[param1ArrayOfMethod.length];
/*  817 */       for (byte b = 0; b < param1ArrayOfMethod.length; b++) {
/*  818 */         Type type = param1ArrayOfMethod[b].getGenericReturnType();
/*  819 */         this.getterMappings[b] = param1MXBeanMappingFactory.mappingForType(type, param1MXBeanMappingFactory);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final Object toNonNullOpenValue(Object param1Object) throws OpenDataException {
/*  826 */       CompositeType compositeType = (CompositeType)getOpenType();
/*  827 */       if (param1Object instanceof CompositeDataView)
/*  828 */         return ((CompositeDataView)param1Object).toCompositeData(compositeType); 
/*  829 */       if (param1Object == null) {
/*  830 */         return null;
/*      */       }
/*  832 */       Object[] arrayOfObject = new Object[this.getters.length];
/*  833 */       for (byte b = 0; b < this.getters.length; b++) {
/*      */         try {
/*  835 */           Object object = MethodUtil.invoke(this.getters[b], param1Object, (Object[])null);
/*  836 */           arrayOfObject[b] = this.getterMappings[b].toOpenValue(object);
/*  837 */         } catch (Exception exception) {
/*  838 */           throw DefaultMXBeanMappingFactory.openDataException("Error calling getter for " + this.itemNames[b] + ": " + exception, exception);
/*      */         } 
/*      */       } 
/*      */       
/*  842 */       return new CompositeDataSupport(compositeType, this.itemNames, arrayOfObject);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private synchronized void makeCompositeBuilder() throws InvalidObjectException {
/*  851 */       if (this.compositeBuilder != null) {
/*      */         return;
/*      */       }
/*  854 */       Class<?> clazz = (Class)getJavaType();
/*      */ 
/*      */ 
/*      */       
/*  858 */       DefaultMXBeanMappingFactory.CompositeBuilder[][] arrayOfCompositeBuilder = { { new DefaultMXBeanMappingFactory.CompositeBuilderViaFrom(clazz, this.itemNames) }, { new DefaultMXBeanMappingFactory.CompositeBuilderViaConstructor(clazz, this.itemNames) }, { new DefaultMXBeanMappingFactory.CompositeBuilderCheckGetters(clazz, this.itemNames, this.getterMappings), new DefaultMXBeanMappingFactory.CompositeBuilderViaSetters(clazz, this.itemNames), new DefaultMXBeanMappingFactory.CompositeBuilderViaProxy(clazz, this.itemNames) } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  872 */       DefaultMXBeanMappingFactory.CompositeBuilder compositeBuilder = null;
/*      */ 
/*      */ 
/*      */       
/*  876 */       StringBuilder stringBuilder = new StringBuilder();
/*  877 */       Throwable throwable = null;
/*      */       
/*  879 */       label33: for (DefaultMXBeanMappingFactory.CompositeBuilder[] arrayOfCompositeBuilder1 : arrayOfCompositeBuilder) {
/*  880 */         for (byte b = 0; b < arrayOfCompositeBuilder1.length; b++) {
/*  881 */           DefaultMXBeanMappingFactory.CompositeBuilder compositeBuilder1 = arrayOfCompositeBuilder1[b];
/*  882 */           String str = compositeBuilder1.applicable(this.getters);
/*  883 */           if (str == null) {
/*  884 */             compositeBuilder = compositeBuilder1;
/*      */             break label33;
/*      */           } 
/*  887 */           Throwable throwable1 = compositeBuilder1.possibleCause();
/*  888 */           if (throwable1 != null)
/*  889 */             throwable = throwable1; 
/*  890 */           if (str.length() > 0) {
/*  891 */             if (stringBuilder.length() > 0)
/*  892 */               stringBuilder.append("; "); 
/*  893 */             stringBuilder.append(str);
/*  894 */             if (b == 0)
/*      */               break; 
/*      */           } 
/*      */         } 
/*      */       } 
/*  899 */       if (compositeBuilder == null) {
/*      */         
/*  901 */         String str = "Do not know how to make a " + clazz.getName() + " from a CompositeData: " + stringBuilder;
/*      */         
/*  903 */         if (throwable != null)
/*  904 */           str = str + ". Remaining exceptions show a POSSIBLE cause."; 
/*  905 */         throw DefaultMXBeanMappingFactory.invalidObjectException(str, throwable);
/*      */       } 
/*  907 */       this.compositeBuilder = compositeBuilder;
/*      */     }
/*      */ 
/*      */     
/*      */     public void checkReconstructible() throws InvalidObjectException {
/*  912 */       makeCompositeBuilder();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final Object fromNonNullOpenValue(Object param1Object) throws InvalidObjectException {
/*  918 */       makeCompositeBuilder();
/*  919 */       return this.compositeBuilder.fromCompositeData((CompositeData)param1Object, this.itemNames, this.getterMappings);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static abstract class CompositeBuilder
/*      */   {
/*      */     private final Class<?> targetClass;
/*      */     
/*      */     private final String[] itemNames;
/*      */ 
/*      */     
/*      */     CompositeBuilder(Class<?> param1Class, String[] param1ArrayOfString) {
/*  933 */       this.targetClass = param1Class;
/*  934 */       this.itemNames = param1ArrayOfString;
/*      */     }
/*      */     
/*      */     Class<?> getTargetClass() {
/*  938 */       return this.targetClass;
/*      */     }
/*      */     
/*      */     String[] getItemNames() {
/*  942 */       return this.itemNames;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract String applicable(Method[] param1ArrayOfMethod) throws InvalidObjectException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Throwable possibleCause() {
/*  960 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     abstract Object fromCompositeData(CompositeData param1CompositeData, String[] param1ArrayOfString, MXBeanMapping[] param1ArrayOfMXBeanMapping) throws InvalidObjectException;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class CompositeBuilderViaFrom
/*      */     extends CompositeBuilder
/*      */   {
/*      */     private Method fromMethod;
/*      */ 
/*      */ 
/*      */     
/*      */     CompositeBuilderViaFrom(Class<?> param1Class, String[] param1ArrayOfString) {
/*  978 */       super(param1Class, param1ArrayOfString);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     String applicable(Method[] param1ArrayOfMethod) throws InvalidObjectException {
/*  984 */       Class<?> clazz = getTargetClass();
/*      */       
/*      */       try {
/*  987 */         Method method = clazz.getMethod("from", new Class[] { CompositeData.class });
/*      */         
/*  989 */         if (!Modifier.isStatic(method.getModifiers()))
/*      */         {
/*      */           
/*  992 */           throw new InvalidObjectException("Method from(CompositeData) is not static");
/*      */         }
/*      */         
/*  995 */         if (method.getReturnType() != getTargetClass()) {
/*      */ 
/*      */ 
/*      */           
/*  999 */           String str = "Method from(CompositeData) returns " + MXBeanIntrospector.typeName(method.getReturnType()) + " not " + MXBeanIntrospector.typeName(clazz);
/* 1000 */           throw new InvalidObjectException(str);
/*      */         } 
/*      */         
/* 1003 */         this.fromMethod = method;
/* 1004 */         return null;
/* 1005 */       } catch (InvalidObjectException invalidObjectException) {
/* 1006 */         throw invalidObjectException;
/* 1007 */       } catch (Exception exception) {
/*      */         
/* 1009 */         return "no method from(CompositeData)";
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Object fromCompositeData(CompositeData param1CompositeData, String[] param1ArrayOfString, MXBeanMapping[] param1ArrayOfMXBeanMapping) throws InvalidObjectException {
/*      */       try {
/* 1018 */         return MethodUtil.invoke(this.fromMethod, null, new Object[] { param1CompositeData });
/* 1019 */       } catch (Exception exception) {
/*      */         
/* 1021 */         throw DefaultMXBeanMappingFactory.invalidObjectException("Failed to invoke from(CompositeData)", exception);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CompositeBuilderCheckGetters
/*      */     extends CompositeBuilder
/*      */   {
/*      */     private final MXBeanMapping[] getterConverters;
/*      */ 
/*      */ 
/*      */     
/*      */     private Throwable possibleCause;
/*      */ 
/*      */ 
/*      */     
/*      */     CompositeBuilderCheckGetters(Class<?> param1Class, String[] param1ArrayOfString, MXBeanMapping[] param1ArrayOfMXBeanMapping) {
/* 1041 */       super(param1Class, param1ArrayOfString);
/* 1042 */       this.getterConverters = param1ArrayOfMXBeanMapping;
/*      */     }
/*      */     
/*      */     String applicable(Method[] param1ArrayOfMethod) {
/* 1046 */       for (byte b = 0; b < param1ArrayOfMethod.length; b++) {
/*      */         try {
/* 1048 */           this.getterConverters[b].checkReconstructible();
/* 1049 */         } catch (InvalidObjectException invalidObjectException) {
/* 1050 */           this.possibleCause = invalidObjectException;
/* 1051 */           return "method " + param1ArrayOfMethod[b].getName() + " returns type that cannot be mapped back from OpenData";
/*      */         } 
/*      */       } 
/*      */       
/* 1055 */       return "";
/*      */     }
/*      */ 
/*      */     
/*      */     Throwable possibleCause() {
/* 1060 */       return this.possibleCause;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final Object fromCompositeData(CompositeData param1CompositeData, String[] param1ArrayOfString, MXBeanMapping[] param1ArrayOfMXBeanMapping) {
/* 1066 */       throw new Error();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class CompositeBuilderViaSetters
/*      */     extends CompositeBuilder
/*      */   {
/*      */     private Method[] setters;
/*      */     
/*      */     CompositeBuilderViaSetters(Class<?> param1Class, String[] param1ArrayOfString) {
/* 1077 */       super(param1Class, param1ArrayOfString);
/*      */     }
/*      */     
/*      */     String applicable(Method[] param1ArrayOfMethod) {
/*      */       try {
/* 1082 */         Constructor<?> constructor = getTargetClass().getConstructor(new Class[0]);
/* 1083 */       } catch (Exception exception) {
/* 1084 */         return "does not have a public no-arg constructor";
/*      */       } 
/*      */       
/* 1087 */       Method[] arrayOfMethod = new Method[param1ArrayOfMethod.length];
/* 1088 */       for (byte b = 0; b < param1ArrayOfMethod.length; b++) {
/* 1089 */         Method method2, method1 = param1ArrayOfMethod[b];
/* 1090 */         Class<?> clazz = method1.getReturnType();
/* 1091 */         String str1 = DefaultMXBeanMappingFactory.propertyName(method1);
/* 1092 */         String str2 = "set" + str1;
/*      */         
/*      */         try {
/* 1095 */           method2 = getTargetClass().getMethod(str2, new Class[] { clazz });
/* 1096 */           if (method2.getReturnType() != void.class)
/* 1097 */             throw new Exception(); 
/* 1098 */         } catch (Exception exception) {
/* 1099 */           return "not all getters have corresponding setters (" + method1 + ")";
/*      */         } 
/*      */         
/* 1102 */         arrayOfMethod[b] = method2;
/*      */       } 
/* 1104 */       this.setters = arrayOfMethod;
/* 1105 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object fromCompositeData(CompositeData param1CompositeData, String[] param1ArrayOfString, MXBeanMapping[] param1ArrayOfMXBeanMapping) throws InvalidObjectException {
/*      */       Object object;
/*      */       try {
/* 1114 */         Class<?> clazz = getTargetClass();
/* 1115 */         ReflectUtil.checkPackageAccess(clazz);
/* 1116 */         object = clazz.newInstance();
/* 1117 */         for (byte b = 0; b < param1ArrayOfString.length; b++) {
/* 1118 */           if (param1CompositeData.containsKey(param1ArrayOfString[b])) {
/* 1119 */             Object object1 = param1CompositeData.get(param1ArrayOfString[b]);
/*      */             
/* 1121 */             Object object2 = param1ArrayOfMXBeanMapping[b].fromOpenValue(object1);
/* 1122 */             MethodUtil.invoke(this.setters[b], object, new Object[] { object2 });
/*      */           } 
/*      */         } 
/* 1125 */       } catch (Exception exception) {
/* 1126 */         throw DefaultMXBeanMappingFactory.invalidObjectException(exception);
/*      */       } 
/* 1128 */       return object;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class CompositeBuilderViaConstructor
/*      */     extends CompositeBuilder
/*      */   {
/*      */     private List<Constr> annotatedConstructors;
/*      */     
/*      */     static class AnnotationHelper
/*      */     {
/*      */       private static Class<? extends Annotation> constructorPropertiesClass;
/*      */       private static Method valueMethod;
/*      */       
/*      */       static {
/* 1143 */         findConstructorPropertiesClass();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       private static void findConstructorPropertiesClass() {
/*      */         try {
/* 1150 */           constructorPropertiesClass = (Class)Class.forName("java.beans.ConstructorProperties", false, DefaultMXBeanMappingFactory.class
/* 1151 */               .getClassLoader());
/* 1152 */           valueMethod = constructorPropertiesClass.getMethod("value", new Class[0]);
/* 1153 */         } catch (ClassNotFoundException classNotFoundException) {
/*      */         
/* 1155 */         } catch (NoSuchMethodException noSuchMethodException) {
/*      */           
/* 1157 */           throw new InternalError(noSuchMethodException);
/*      */         } 
/*      */       }
/*      */       
/*      */       static boolean isAvailable() {
/* 1162 */         return (constructorPropertiesClass != null);
/*      */       }
/*      */       
/*      */       static String[] getPropertyNames(Constructor<?> param2Constructor) {
/* 1166 */         if (!isAvailable()) {
/* 1167 */           return null;
/*      */         }
/* 1169 */         Object object = param2Constructor.getAnnotation((Class)constructorPropertiesClass);
/* 1170 */         if (object == null) return null;
/*      */         
/*      */         try {
/* 1173 */           return (String[])valueMethod.invoke(object, new Object[0]);
/* 1174 */         } catch (InvocationTargetException invocationTargetException) {
/* 1175 */           throw new InternalError(invocationTargetException);
/* 1176 */         } catch (IllegalAccessException illegalAccessException) {
/* 1177 */           throw new InternalError(illegalAccessException);
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     CompositeBuilderViaConstructor(Class<?> param1Class, String[] param1ArrayOfString) {
/* 1183 */       super(param1Class, param1ArrayOfString);
/*      */     }
/*      */     
/*      */     String applicable(Method[] param1ArrayOfMethod) throws InvalidObjectException {
/* 1187 */       if (!AnnotationHelper.isAvailable()) {
/* 1188 */         return "@ConstructorProperties annotation not available";
/*      */       }
/* 1190 */       Class<?> clazz = getTargetClass();
/* 1191 */       Constructor[] arrayOfConstructor = (Constructor[])clazz.getConstructors();
/*      */ 
/*      */       
/* 1194 */       List<?> list = Util.newList();
/* 1195 */       for (Constructor<?> constructor : arrayOfConstructor) {
/* 1196 */         if (Modifier.isPublic(constructor.getModifiers()) && 
/* 1197 */           AnnotationHelper.getPropertyNames(constructor) != null) {
/* 1198 */           list.add(constructor);
/*      */         }
/*      */       } 
/* 1201 */       if (list.isEmpty()) {
/* 1202 */         return "no constructor has @ConstructorProperties annotation";
/*      */       }
/* 1204 */       this.annotatedConstructors = Util.newList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1210 */       Map<?, ?> map = Util.newMap();
/* 1211 */       String[] arrayOfString = getItemNames();
/* 1212 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 1213 */         map.put(arrayOfString[b], Integer.valueOf(b));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1224 */       Set<?> set = Util.newSet();
/* 1225 */       for (Constructor<?> constructor : list) {
/* 1226 */         String[] arrayOfString1 = AnnotationHelper.getPropertyNames(constructor);
/*      */         
/* 1228 */         Type[] arrayOfType = constructor.getGenericParameterTypes();
/* 1229 */         if (arrayOfType.length != arrayOfString1.length) {
/* 1230 */           String str = "Number of constructor params does not match @ConstructorProperties annotation: " + constructor;
/*      */ 
/*      */           
/* 1233 */           throw new InvalidObjectException(str);
/*      */         } 
/*      */         
/* 1236 */         int[] arrayOfInt = new int[param1ArrayOfMethod.length];
/* 1237 */         for (byte b1 = 0; b1 < param1ArrayOfMethod.length; b1++)
/* 1238 */           arrayOfInt[b1] = -1; 
/* 1239 */         BitSet bitSet = new BitSet();
/*      */         
/* 1241 */         for (byte b2 = 0; b2 < arrayOfString1.length; b2++) {
/* 1242 */           String str = arrayOfString1[b2];
/* 1243 */           if (!map.containsKey(str)) {
/* 1244 */             String str1 = "@ConstructorProperties includes name " + str + " which does not correspond to a property";
/*      */ 
/*      */             
/* 1247 */             for (String str2 : map.keySet()) {
/* 1248 */               if (str2.equalsIgnoreCase(str)) {
/* 1249 */                 str1 = str1 + " (differs only in case from property " + str2 + ")";
/*      */               }
/*      */             } 
/*      */             
/* 1253 */             str1 = str1 + ": " + constructor;
/* 1254 */             throw new InvalidObjectException(str1);
/*      */           } 
/* 1256 */           int i = ((Integer)map.get(str)).intValue();
/* 1257 */           arrayOfInt[i] = b2;
/* 1258 */           if (bitSet.get(i)) {
/* 1259 */             String str1 = "@ConstructorProperties contains property " + str + " more than once: " + constructor;
/*      */ 
/*      */             
/* 1262 */             throw new InvalidObjectException(str1);
/*      */           } 
/* 1264 */           bitSet.set(i);
/* 1265 */           Method method = param1ArrayOfMethod[i];
/* 1266 */           Type type = method.getGenericReturnType();
/* 1267 */           if (!type.equals(arrayOfType[b2])) {
/* 1268 */             String str1 = "@ConstructorProperties gives property " + str + " of type " + type + " for parameter  of type " + arrayOfType[b2] + ": " + constructor;
/*      */ 
/*      */ 
/*      */             
/* 1272 */             throw new InvalidObjectException(str1);
/*      */           } 
/*      */         } 
/*      */         
/* 1276 */         if (!set.add(bitSet)) {
/*      */ 
/*      */ 
/*      */           
/* 1280 */           String str = "More than one constructor has a @ConstructorProperties annotation with this set of names: " + Arrays.toString((Object[])arrayOfString1);
/* 1281 */           throw new InvalidObjectException(str);
/*      */         } 
/*      */         
/* 1284 */         Constr constr = new Constr(constructor, arrayOfInt, bitSet);
/* 1285 */         this.annotatedConstructors.add(constr);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1303 */       for (BitSet bitSet : set) {
/* 1304 */         boolean bool = false;
/* 1305 */         for (BitSet bitSet1 : set) {
/* 1306 */           if (bitSet == bitSet1) {
/* 1307 */             bool = true; continue;
/* 1308 */           }  if (bool) {
/* 1309 */             BitSet bitSet2 = new BitSet();
/* 1310 */             bitSet2.or(bitSet); bitSet2.or(bitSet1);
/* 1311 */             if (!set.contains(bitSet2)) {
/* 1312 */               TreeSet<String> treeSet = new TreeSet(); int i;
/* 1313 */               for (i = bitSet2.nextSetBit(0); i >= 0; 
/* 1314 */                 i = bitSet2.nextSetBit(i + 1))
/* 1315 */                 treeSet.add(arrayOfString[i]); 
/* 1316 */               String str = "Constructors with @ConstructorProperties annotation  would be ambiguous for these items: " + treeSet;
/*      */ 
/*      */ 
/*      */               
/* 1320 */               throw new InvalidObjectException(str);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1326 */       return null;
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
/*      */     final Object fromCompositeData(CompositeData param1CompositeData, String[] param1ArrayOfString, MXBeanMapping[] param1ArrayOfMXBeanMapping) throws InvalidObjectException {
/* 1338 */       CompositeType compositeType = param1CompositeData.getCompositeType();
/* 1339 */       BitSet bitSet = new BitSet();
/* 1340 */       for (byte b1 = 0; b1 < param1ArrayOfString.length; b1++) {
/* 1341 */         if (compositeType.getType(param1ArrayOfString[b1]) != null) {
/* 1342 */           bitSet.set(b1);
/*      */         }
/*      */       } 
/* 1345 */       Constr constr = null;
/* 1346 */       for (Constr constr1 : this.annotatedConstructors) {
/* 1347 */         if (subset(constr1.presentParams, bitSet) && (constr == null || 
/*      */           
/* 1349 */           subset(constr.presentParams, constr1.presentParams))) {
/* 1350 */           constr = constr1;
/*      */         }
/*      */       } 
/* 1353 */       if (constr == null) {
/*      */ 
/*      */         
/* 1356 */         String str = "No constructor has a @ConstructorProperties for this set of items: " + compositeType.keySet();
/* 1357 */         throw new InvalidObjectException(str);
/*      */       } 
/*      */       
/* 1360 */       Object[] arrayOfObject = new Object[constr.presentParams.cardinality()];
/* 1361 */       for (byte b2 = 0; b2 < param1ArrayOfString.length; b2++) {
/* 1362 */         if (constr.presentParams.get(b2)) {
/*      */           
/* 1364 */           Object object1 = param1CompositeData.get(param1ArrayOfString[b2]);
/* 1365 */           Object object2 = param1ArrayOfMXBeanMapping[b2].fromOpenValue(object1);
/* 1366 */           int i = constr.paramIndexes[b2];
/* 1367 */           if (i >= 0)
/* 1368 */             arrayOfObject[i] = object2; 
/*      */         } 
/*      */       } 
/*      */       try {
/* 1372 */         ReflectUtil.checkPackageAccess(constr.constructor.getDeclaringClass());
/* 1373 */         return constr.constructor.newInstance(arrayOfObject);
/* 1374 */       } catch (Exception exception) {
/*      */         
/* 1376 */         String str = "Exception constructing " + getTargetClass().getName();
/* 1377 */         throw DefaultMXBeanMappingFactory.invalidObjectException(str, exception);
/*      */       } 
/*      */     }
/*      */     
/*      */     private static boolean subset(BitSet param1BitSet1, BitSet param1BitSet2) {
/* 1382 */       BitSet bitSet = (BitSet)param1BitSet1.clone();
/* 1383 */       bitSet.andNot(param1BitSet2);
/* 1384 */       return bitSet.isEmpty();
/*      */     }
/*      */     
/*      */     private static class Constr {
/*      */       final Constructor<?> constructor;
/*      */       final int[] paramIndexes;
/*      */       final BitSet presentParams;
/*      */       
/*      */       Constr(Constructor<?> param2Constructor, int[] param2ArrayOfint, BitSet param2BitSet) {
/* 1393 */         this.constructor = param2Constructor;
/* 1394 */         this.paramIndexes = param2ArrayOfint;
/* 1395 */         this.presentParams = param2BitSet;
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
/*      */   private static final class CompositeBuilderViaProxy
/*      */     extends CompositeBuilder
/*      */   {
/*      */     CompositeBuilderViaProxy(Class<?> param1Class, String[] param1ArrayOfString) {
/* 1410 */       super(param1Class, param1ArrayOfString);
/*      */     }
/*      */     
/*      */     String applicable(Method[] param1ArrayOfMethod) {
/* 1414 */       Class<?> clazz = getTargetClass();
/* 1415 */       if (!clazz.isInterface()) {
/* 1416 */         return "not an interface";
/*      */       }
/* 1418 */       Set<?> set = Util.newSet(Arrays.asList((Object[])clazz.getMethods()));
/* 1419 */       set.removeAll(Arrays.asList((Object[])param1ArrayOfMethod));
/*      */ 
/*      */ 
/*      */       
/* 1423 */       String str = null;
/* 1424 */       for (Method method : set) {
/* 1425 */         String str1 = method.getName();
/* 1426 */         Class[] arrayOfClass = method.getParameterTypes();
/*      */         try {
/* 1428 */           Method method1 = Object.class.getMethod(str1, arrayOfClass);
/* 1429 */           if (!Modifier.isPublic(method1.getModifiers()))
/* 1430 */             str = str1; 
/* 1431 */         } catch (NoSuchMethodException noSuchMethodException) {
/* 1432 */           str = str1;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1439 */       if (str != null)
/* 1440 */         return "contains methods other than getters (" + str + ")"; 
/* 1441 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final Object fromCompositeData(CompositeData param1CompositeData, String[] param1ArrayOfString, MXBeanMapping[] param1ArrayOfMXBeanMapping) {
/* 1447 */       Class<?> clazz = getTargetClass();
/* 1448 */       return 
/* 1449 */         Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new CompositeDataInvocationHandler(param1CompositeData));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static InvalidObjectException invalidObjectException(String paramString, Throwable paramThrowable) {
/* 1457 */     return EnvHelp.<InvalidObjectException>initCause(new InvalidObjectException(paramString), paramThrowable);
/*      */   }
/*      */   
/*      */   static InvalidObjectException invalidObjectException(Throwable paramThrowable) {
/* 1461 */     return invalidObjectException(paramThrowable.getMessage(), paramThrowable);
/*      */   }
/*      */   
/*      */   static OpenDataException openDataException(String paramString, Throwable paramThrowable) {
/* 1465 */     return EnvHelp.<OpenDataException>initCause(new OpenDataException(paramString), paramThrowable);
/*      */   }
/*      */   
/*      */   static OpenDataException openDataException(Throwable paramThrowable) {
/* 1469 */     return openDataException(paramThrowable.getMessage(), paramThrowable);
/*      */   }
/*      */ 
/*      */   
/*      */   static void mustBeComparable(Class<?> paramClass, Type paramType) throws OpenDataException {
/* 1474 */     if (!(paramType instanceof Class) || 
/* 1475 */       !Comparable.class.isAssignableFrom((Class)paramType)) {
/*      */ 
/*      */ 
/*      */       
/* 1479 */       String str = "Parameter class " + paramType + " of " + paramClass.getName() + " does not implement " + Comparable.class.getName();
/* 1480 */       throw new OpenDataException(str);
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
/*      */   public static String decapitalize(String paramString) {
/* 1498 */     if (paramString == null || paramString.length() == 0) {
/* 1499 */       return paramString;
/*      */     }
/* 1501 */     int i = Character.offsetByCodePoints(paramString, 0, 1);
/*      */     
/* 1503 */     if (i < paramString.length() && 
/* 1504 */       Character.isUpperCase(paramString.codePointAt(i)))
/* 1505 */       return paramString; 
/* 1506 */     return paramString.substring(0, i).toLowerCase() + paramString
/* 1507 */       .substring(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String capitalize(String paramString) {
/* 1517 */     if (paramString == null || paramString.length() == 0)
/* 1518 */       return paramString; 
/* 1519 */     int i = paramString.offsetByCodePoints(0, 1);
/* 1520 */     return paramString.substring(0, i).toUpperCase() + paramString
/* 1521 */       .substring(i);
/*      */   }
/*      */   
/*      */   public static String propertyName(Method paramMethod) {
/* 1525 */     String str1 = null;
/* 1526 */     String str2 = paramMethod.getName();
/* 1527 */     if (str2.startsWith("get")) {
/* 1528 */       str1 = str2.substring(3);
/* 1529 */     } else if (str2.startsWith("is") && paramMethod.getReturnType() == boolean.class) {
/* 1530 */       str1 = str2.substring(2);
/* 1531 */     }  if (str1 == null || str1.length() == 0 || (paramMethod
/* 1532 */       .getParameterTypes()).length > 0 || paramMethod
/* 1533 */       .getReturnType() == void.class || str2
/* 1534 */       .equals("getClass"))
/* 1535 */       return null; 
/* 1536 */     return str1;
/*      */   }
/*      */   
/* 1539 */   private static final Map<Type, Type> inProgress = Util.newIdentityHashMap();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/DefaultMXBeanMappingFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */