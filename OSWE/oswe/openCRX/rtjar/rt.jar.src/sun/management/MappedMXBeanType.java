/*     */ package sun.management;
/*     */ 
/*     */ import com.sun.management.VMOption;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.lang.management.LockInfo;
/*     */ import java.lang.management.MemoryNotificationInfo;
/*     */ import java.lang.management.MemoryUsage;
/*     */ import java.lang.management.MonitorInfo;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.management.openmbean.ArrayType;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeDataSupport;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import javax.management.openmbean.OpenDataException;
/*     */ import javax.management.openmbean.OpenType;
/*     */ import javax.management.openmbean.SimpleType;
/*     */ import javax.management.openmbean.TabularData;
/*     */ import javax.management.openmbean.TabularDataSupport;
/*     */ import javax.management.openmbean.TabularType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MappedMXBeanType
/*     */ {
/*     */   static {
/*     */     InProgress inProgress;
/*     */   }
/*     */   
/*  64 */   private static final WeakHashMap<Type, MappedMXBeanType> convertedTypes = new WeakHashMap<>();
/*     */   
/*     */   boolean isBasicType = false;
/*     */   
/*  68 */   OpenType<?> openType = inProgress;
/*     */   
/*     */   Class<?> mappedTypeClass;
/*     */   
/*     */   static synchronized MappedMXBeanType newMappedType(Type paramType) throws OpenDataException {
/*     */     ArrayMXBeanType arrayMXBeanType;
/*  74 */     EnumMXBeanType enumMXBeanType = null;
/*  75 */     if (paramType instanceof Class) {
/*  76 */       Class<?> clazz = (Class)paramType;
/*  77 */       if (clazz.isEnum()) {
/*  78 */         enumMXBeanType = new EnumMXBeanType(clazz);
/*  79 */       } else if (clazz.isArray()) {
/*  80 */         arrayMXBeanType = new ArrayMXBeanType(clazz);
/*     */       } else {
/*  82 */         CompositeDataMXBeanType compositeDataMXBeanType = new CompositeDataMXBeanType(clazz);
/*     */       } 
/*  84 */     } else if (paramType instanceof ParameterizedType) {
/*  85 */       ParameterizedType parameterizedType = (ParameterizedType)paramType;
/*  86 */       Type type = parameterizedType.getRawType();
/*  87 */       if (type instanceof Class) {
/*  88 */         Class<List> clazz = (Class)type;
/*  89 */         if (clazz == List.class) {
/*  90 */           ListMXBeanType listMXBeanType = new ListMXBeanType(parameterizedType);
/*  91 */         } else if (clazz == Map.class) {
/*  92 */           MapMXBeanType mapMXBeanType = new MapMXBeanType(parameterizedType);
/*     */         } 
/*     */       } 
/*  95 */     } else if (paramType instanceof GenericArrayType) {
/*  96 */       GenericArrayType genericArrayType = (GenericArrayType)paramType;
/*  97 */       arrayMXBeanType = new GenericArrayMXBeanType(genericArrayType);
/*     */     } 
/*     */     
/* 100 */     if (arrayMXBeanType == null) {
/* 101 */       throw new OpenDataException(paramType + " is not a supported MXBean type.");
/*     */     }
/*     */     
/* 104 */     convertedTypes.put(paramType, arrayMXBeanType);
/* 105 */     return arrayMXBeanType;
/*     */   }
/*     */   private static final String KEY = "key";
/*     */   private static final String VALUE = "value";
/*     */   
/*     */   static synchronized MappedMXBeanType newBasicType(Class<?> paramClass, OpenType<?> paramOpenType) throws OpenDataException {
/* 111 */     BasicMXBeanType basicMXBeanType = new BasicMXBeanType(paramClass, paramOpenType);
/* 112 */     convertedTypes.put(paramClass, basicMXBeanType);
/* 113 */     return basicMXBeanType;
/*     */   }
/*     */ 
/*     */   
/*     */   static synchronized MappedMXBeanType getMappedType(Type paramType) throws OpenDataException {
/* 118 */     MappedMXBeanType mappedMXBeanType = convertedTypes.get(paramType);
/* 119 */     if (mappedMXBeanType == null) {
/* 120 */       mappedMXBeanType = newMappedType(paramType);
/*     */     }
/*     */     
/* 123 */     if (mappedMXBeanType.getOpenType() instanceof InProgress) {
/* 124 */       throw new OpenDataException("Recursive data structure");
/*     */     }
/* 126 */     return mappedMXBeanType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized OpenType<?> toOpenType(Type paramType) throws OpenDataException {
/* 132 */     MappedMXBeanType mappedMXBeanType = getMappedType(paramType);
/* 133 */     return mappedMXBeanType.getOpenType();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object toJavaTypeData(Object paramObject, Type paramType) throws OpenDataException, InvalidObjectException {
/* 138 */     if (paramObject == null) {
/* 139 */       return null;
/*     */     }
/* 141 */     MappedMXBeanType mappedMXBeanType = getMappedType(paramType);
/* 142 */     return mappedMXBeanType.toJavaTypeData(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object toOpenTypeData(Object paramObject, Type paramType) throws OpenDataException {
/* 147 */     if (paramObject == null) {
/* 148 */       return null;
/*     */     }
/* 150 */     MappedMXBeanType mappedMXBeanType = getMappedType(paramType);
/* 151 */     return mappedMXBeanType.toOpenTypeData(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   OpenType<?> getOpenType() {
/* 156 */     return this.openType;
/*     */   }
/*     */   
/*     */   boolean isBasicType() {
/* 160 */     return this.isBasicType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getTypeName() {
/* 167 */     return getMappedTypeClass().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   Class<?> getMappedTypeClass() {
/* 172 */     return this.mappedTypeClass;
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
/*     */   static class BasicMXBeanType
/*     */     extends MappedMXBeanType
/*     */   {
/*     */     final Class<?> basicType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     BasicMXBeanType(Class<?> param1Class, OpenType<?> param1OpenType) {
/* 197 */       this.basicType = param1Class;
/* 198 */       this.openType = param1OpenType;
/* 199 */       this.mappedTypeClass = param1Class;
/* 200 */       this.isBasicType = true;
/*     */     }
/*     */     
/*     */     Type getJavaType() {
/* 204 */       return this.basicType;
/*     */     }
/*     */     
/*     */     String getName() {
/* 208 */       return this.basicType.getName();
/*     */     }
/*     */     
/*     */     Object toOpenTypeData(Object param1Object) throws OpenDataException {
/* 212 */       return param1Object;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Object toJavaTypeData(Object param1Object) throws OpenDataException, InvalidObjectException {
/* 218 */       return param1Object;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class EnumMXBeanType
/*     */     extends MappedMXBeanType
/*     */   {
/*     */     final Class enumClass;
/*     */ 
/*     */ 
/*     */     
/*     */     EnumMXBeanType(Class<?> param1Class) {
/* 232 */       this.enumClass = param1Class;
/* 233 */       this.openType = SimpleType.STRING;
/* 234 */       this.mappedTypeClass = String.class;
/*     */     }
/*     */     
/*     */     Type getJavaType() {
/* 238 */       return this.enumClass;
/*     */     }
/*     */     
/*     */     String getName() {
/* 242 */       return this.enumClass.getName();
/*     */     }
/*     */     
/*     */     Object toOpenTypeData(Object param1Object) throws OpenDataException {
/* 246 */       return ((Enum)param1Object).name();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Object toJavaTypeData(Object param1Object) throws OpenDataException, InvalidObjectException {
/*     */       try {
/* 253 */         return Enum.valueOf(this.enumClass, (String)param1Object);
/* 254 */       } catch (IllegalArgumentException illegalArgumentException) {
/*     */         
/* 256 */         InvalidObjectException invalidObjectException = new InvalidObjectException("Enum constant named " + (String)param1Object + " is missing");
/*     */ 
/*     */         
/* 259 */         invalidObjectException.initCause(illegalArgumentException);
/* 260 */         throw invalidObjectException;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class ArrayMXBeanType
/*     */     extends MappedMXBeanType
/*     */   {
/*     */     final Class<?> arrayClass;
/*     */     
/*     */     protected MappedMXBeanType componentType;
/*     */     
/*     */     protected MappedMXBeanType baseElementType;
/*     */ 
/*     */     
/*     */     ArrayMXBeanType(Class<?> param1Class) throws OpenDataException {
/* 277 */       this.arrayClass = param1Class;
/* 278 */       this.componentType = getMappedType(param1Class.getComponentType());
/*     */       
/* 280 */       StringBuilder stringBuilder = new StringBuilder();
/* 281 */       Class<?> clazz = param1Class;
/*     */       byte b;
/* 283 */       for (b = 0; clazz.isArray(); b++) {
/* 284 */         stringBuilder.append('[');
/* 285 */         clazz = clazz.getComponentType();
/*     */       } 
/* 287 */       this.baseElementType = getMappedType(clazz);
/* 288 */       if (clazz.isPrimitive()) {
/* 289 */         stringBuilder = new StringBuilder(param1Class.getName());
/*     */       } else {
/* 291 */         stringBuilder.append("L" + this.baseElementType.getTypeName() + ";");
/*     */       } 
/*     */       try {
/* 294 */         this.mappedTypeClass = Class.forName(stringBuilder.toString());
/* 295 */       } catch (ClassNotFoundException classNotFoundException) {
/* 296 */         OpenDataException openDataException = new OpenDataException("Cannot obtain array class");
/*     */         
/* 298 */         openDataException.initCause(classNotFoundException);
/* 299 */         throw openDataException;
/*     */       } 
/*     */       
/* 302 */       this.openType = new ArrayType(b, this.baseElementType.getOpenType());
/*     */     }
/*     */     
/*     */     protected ArrayMXBeanType() {
/* 306 */       this.arrayClass = null;
/*     */     }
/*     */     
/*     */     Type getJavaType() {
/* 310 */       return this.arrayClass;
/*     */     }
/*     */     
/*     */     String getName() {
/* 314 */       return this.arrayClass.getName();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object toOpenTypeData(Object param1Object) throws OpenDataException {
/* 321 */       if (this.baseElementType.isBasicType()) {
/* 322 */         return param1Object;
/*     */       }
/*     */       
/* 325 */       Object[] arrayOfObject1 = (Object[])param1Object;
/*     */       
/* 327 */       Object[] arrayOfObject2 = (Object[])Array.newInstance(this.componentType.getMappedTypeClass(), arrayOfObject1.length);
/*     */       
/* 329 */       byte b = 0;
/* 330 */       for (Object object : arrayOfObject1) {
/* 331 */         if (object == null) {
/* 332 */           arrayOfObject2[b] = null;
/*     */         } else {
/* 334 */           arrayOfObject2[b] = this.componentType.toOpenTypeData(object);
/*     */         } 
/* 336 */         b++;
/*     */       } 
/* 338 */       return arrayOfObject2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object toJavaTypeData(Object param1Object) throws OpenDataException, InvalidObjectException {
/* 347 */       if (this.baseElementType.isBasicType()) {
/* 348 */         return param1Object;
/*     */       }
/*     */       
/* 351 */       Object[] arrayOfObject1 = (Object[])param1Object;
/*     */       
/* 353 */       Object[] arrayOfObject2 = (Object[])Array.newInstance((Class)this.componentType.getJavaType(), arrayOfObject1.length);
/*     */       
/* 355 */       byte b = 0;
/* 356 */       for (Object object : arrayOfObject1) {
/* 357 */         if (object == null) {
/* 358 */           arrayOfObject2[b] = null;
/*     */         } else {
/* 360 */           arrayOfObject2[b] = this.componentType.toJavaTypeData(object);
/*     */         } 
/* 362 */         b++;
/*     */       } 
/* 364 */       return arrayOfObject2;
/*     */     }
/*     */   }
/*     */   
/*     */   static class GenericArrayMXBeanType extends ArrayMXBeanType {
/*     */     final GenericArrayType gtype;
/*     */     
/*     */     GenericArrayMXBeanType(GenericArrayType param1GenericArrayType) throws OpenDataException {
/* 372 */       this.gtype = param1GenericArrayType;
/* 373 */       this.componentType = getMappedType(param1GenericArrayType.getGenericComponentType());
/*     */       
/* 375 */       StringBuilder stringBuilder = new StringBuilder();
/* 376 */       Type type = param1GenericArrayType;
/*     */       byte b;
/* 378 */       for (b = 0; type instanceof GenericArrayType; b++) {
/* 379 */         stringBuilder.append('[');
/* 380 */         GenericArrayType genericArrayType = (GenericArrayType)type;
/* 381 */         type = genericArrayType.getGenericComponentType();
/*     */       } 
/* 383 */       this.baseElementType = getMappedType(type);
/* 384 */       if (type instanceof Class && ((Class)type).isPrimitive()) {
/* 385 */         stringBuilder = new StringBuilder(param1GenericArrayType.toString());
/*     */       } else {
/* 387 */         stringBuilder.append("L" + this.baseElementType.getTypeName() + ";");
/*     */       } 
/*     */       try {
/* 390 */         this.mappedTypeClass = Class.forName(stringBuilder.toString());
/* 391 */       } catch (ClassNotFoundException classNotFoundException) {
/* 392 */         OpenDataException openDataException = new OpenDataException("Cannot obtain array class");
/*     */         
/* 394 */         openDataException.initCause(classNotFoundException);
/* 395 */         throw openDataException;
/*     */       } 
/*     */       
/* 398 */       this.openType = new ArrayType(b, this.baseElementType.getOpenType());
/*     */     }
/*     */     
/*     */     Type getJavaType() {
/* 402 */       return this.gtype;
/*     */     }
/*     */     
/*     */     String getName() {
/* 406 */       return this.gtype.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class ListMXBeanType
/*     */     extends MappedMXBeanType
/*     */   {
/*     */     final ParameterizedType javaType;
/*     */     
/*     */     final MappedMXBeanType paramType;
/*     */     
/*     */     final String typeName;
/*     */ 
/*     */     
/*     */     ListMXBeanType(ParameterizedType param1ParameterizedType) throws OpenDataException {
/* 422 */       this.javaType = param1ParameterizedType;
/*     */       
/* 424 */       Type[] arrayOfType = param1ParameterizedType.getActualTypeArguments();
/* 425 */       assert arrayOfType.length == 1;
/*     */       
/* 427 */       if (!(arrayOfType[0] instanceof Class)) {
/* 428 */         throw new OpenDataException("Element Type for " + param1ParameterizedType + " not supported");
/*     */       }
/*     */       
/* 431 */       Class clazz = (Class)arrayOfType[0];
/* 432 */       if (clazz.isArray()) {
/* 433 */         throw new OpenDataException("Element Type for " + param1ParameterizedType + " not supported");
/*     */       }
/*     */       
/* 436 */       this.paramType = getMappedType(clazz);
/* 437 */       this.typeName = "List<" + this.paramType.getName() + ">";
/*     */       
/*     */       try {
/* 440 */         this.mappedTypeClass = Class.forName("[L" + this.paramType
/* 441 */             .getTypeName() + ";");
/* 442 */       } catch (ClassNotFoundException classNotFoundException) {
/* 443 */         OpenDataException openDataException = new OpenDataException("Array class not found");
/*     */         
/* 445 */         openDataException.initCause(classNotFoundException);
/* 446 */         throw openDataException;
/*     */       } 
/* 448 */       this.openType = new ArrayType(1, this.paramType.getOpenType());
/*     */     }
/*     */     
/*     */     Type getJavaType() {
/* 452 */       return this.javaType;
/*     */     }
/*     */     
/*     */     String getName() {
/* 456 */       return this.typeName;
/*     */     }
/*     */     
/*     */     Object toOpenTypeData(Object param1Object) throws OpenDataException {
/* 460 */       List list = (List)param1Object;
/*     */ 
/*     */       
/* 463 */       Object[] arrayOfObject = (Object[])Array.newInstance(this.paramType.getMappedTypeClass(), list
/* 464 */           .size());
/* 465 */       byte b = 0;
/* 466 */       for (Object object : list) {
/* 467 */         arrayOfObject[b++] = this.paramType.toOpenTypeData(object);
/*     */       }
/* 469 */       return arrayOfObject;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Object toJavaTypeData(Object param1Object) throws OpenDataException, InvalidObjectException {
/* 475 */       Object[] arrayOfObject = (Object[])param1Object;
/* 476 */       ArrayList<Object> arrayList = new ArrayList(arrayOfObject.length);
/* 477 */       for (Object object : arrayOfObject) {
/* 478 */         arrayList.add(this.paramType.toJavaTypeData(object));
/*     */       }
/* 480 */       return arrayList;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 486 */   private static final String[] mapIndexNames = new String[] { "key" };
/* 487 */   private static final String[] mapItemNames = new String[] { "key", "value" };
/*     */ 
/*     */ 
/*     */   
/*     */   static class MapMXBeanType
/*     */     extends MappedMXBeanType
/*     */   {
/*     */     final ParameterizedType javaType;
/*     */ 
/*     */     
/*     */     final MappedMXBeanType keyType;
/*     */ 
/*     */     
/*     */     final MappedMXBeanType valueType;
/*     */     
/*     */     final String typeName;
/*     */ 
/*     */     
/*     */     MapMXBeanType(ParameterizedType param1ParameterizedType) throws OpenDataException {
/* 506 */       this.javaType = param1ParameterizedType;
/*     */       
/* 508 */       Type[] arrayOfType = param1ParameterizedType.getActualTypeArguments();
/* 509 */       assert arrayOfType.length == 2;
/* 510 */       this.keyType = getMappedType(arrayOfType[0]);
/* 511 */       this.valueType = getMappedType(arrayOfType[1]);
/*     */ 
/*     */ 
/*     */       
/* 515 */       this
/* 516 */         .typeName = "Map<" + this.keyType.getName() + "," + this.valueType.getName() + ">";
/*     */ 
/*     */       
/* 519 */       OpenType[] arrayOfOpenType = { this.keyType.getOpenType(), this.valueType.getOpenType() };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 525 */       CompositeType compositeType = new CompositeType(this.typeName, this.typeName, MappedMXBeanType.mapItemNames, MappedMXBeanType.mapItemNames, (OpenType<?>[])arrayOfOpenType);
/*     */ 
/*     */       
/* 528 */       this.openType = new TabularType(this.typeName, this.typeName, compositeType, MappedMXBeanType.mapIndexNames);
/* 529 */       this.mappedTypeClass = TabularData.class;
/*     */     }
/*     */     
/*     */     Type getJavaType() {
/* 533 */       return this.javaType;
/*     */     }
/*     */     
/*     */     String getName() {
/* 537 */       return this.typeName;
/*     */     }
/*     */     
/*     */     Object toOpenTypeData(Object param1Object) throws OpenDataException {
/* 541 */       Map map = (Map)param1Object;
/* 542 */       TabularType tabularType = (TabularType)this.openType;
/* 543 */       TabularDataSupport tabularDataSupport = new TabularDataSupport(tabularType);
/* 544 */       CompositeType compositeType = tabularType.getRowType();
/*     */       
/* 546 */       for (Map.Entry entry : map.entrySet()) {
/* 547 */         Object object1 = this.keyType.toOpenTypeData(entry.getKey());
/* 548 */         Object object2 = this.valueType.toOpenTypeData(entry.getValue());
/*     */ 
/*     */         
/* 551 */         CompositeDataSupport compositeDataSupport = new CompositeDataSupport(compositeType, MappedMXBeanType.mapItemNames, new Object[] { object1, object2 });
/*     */         
/* 553 */         tabularDataSupport.put(compositeDataSupport);
/*     */       } 
/* 555 */       return tabularDataSupport;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Object toJavaTypeData(Object param1Object) throws OpenDataException, InvalidObjectException {
/* 561 */       TabularData tabularData = (TabularData)param1Object;
/*     */       
/* 563 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 564 */       for (CompositeData compositeData : tabularData.values()) {
/* 565 */         Object object1 = this.keyType.toJavaTypeData(compositeData.get("key"));
/* 566 */         Object object2 = this.valueType.toJavaTypeData(compositeData.get("value"));
/* 567 */         hashMap.put(object1, object2);
/*     */       } 
/* 569 */       return hashMap;
/*     */     }
/*     */   }
/*     */   
/* 573 */   private static final Class<?> COMPOSITE_DATA_CLASS = CompositeData.class;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final OpenType<?> inProgress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class CompositeDataMXBeanType
/*     */     extends MappedMXBeanType
/*     */   {
/*     */     final Class<?> javaClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final boolean isCompositeData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 608 */     Method fromMethod = null;
/*     */     
/*     */     CompositeDataMXBeanType(Class<?> param1Class) throws OpenDataException {
/* 611 */       this.javaClass = param1Class;
/* 612 */       this.mappedTypeClass = MappedMXBeanType.COMPOSITE_DATA_CLASS;
/*     */ 
/*     */       
/*     */       try {
/* 616 */         this.fromMethod = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>() {
/*     */               public Method run() throws NoSuchMethodException {
/* 618 */                 return MappedMXBeanType.CompositeDataMXBeanType.this.javaClass.getMethod("from", new Class[] { MappedMXBeanType.access$200() });
/*     */               }
/*     */             });
/* 621 */       } catch (PrivilegedActionException privilegedActionException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 626 */       if (MappedMXBeanType.COMPOSITE_DATA_CLASS.isAssignableFrom(param1Class)) {
/*     */ 
/*     */ 
/*     */         
/* 630 */         this.isCompositeData = true;
/* 631 */         this.openType = null;
/*     */       } else {
/* 633 */         this.isCompositeData = false;
/*     */ 
/*     */ 
/*     */         
/* 637 */         Method[] arrayOfMethod = AccessController.<Method[]>doPrivileged((PrivilegedAction)new PrivilegedAction<Method[]>() {
/*     */               public Method[] run() {
/* 639 */                 return MappedMXBeanType.CompositeDataMXBeanType.this.javaClass.getMethods();
/*     */               }
/*     */             });
/* 642 */         ArrayList<String> arrayList = new ArrayList();
/* 643 */         ArrayList<OpenType<?>> arrayList1 = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 648 */         for (byte b = 0; b < arrayOfMethod.length; b++) {
/* 649 */           String str2; Method method = arrayOfMethod[b];
/* 650 */           String str1 = method.getName();
/* 651 */           Type type = method.getGenericReturnType();
/*     */           
/* 653 */           if (str1.startsWith("get")) {
/* 654 */             str2 = str1.substring(3);
/* 655 */           } else if (str1.startsWith("is") && type instanceof Class && (Class<boolean>)type == boolean.class) {
/*     */ 
/*     */             
/* 658 */             str2 = str1.substring(2);
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */ 
/*     */           
/* 664 */           if (!str2.equals("") && (method
/* 665 */             .getParameterTypes()).length <= 0 && type != void.class && 
/*     */             
/* 667 */             !str2.equals("Class")) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 672 */             arrayList.add(MappedMXBeanType.decapitalize(str2));
/* 673 */             arrayList1.add(toOpenType(type));
/*     */           }  continue;
/*     */         } 
/* 676 */         String[] arrayOfString = arrayList.<String>toArray(new String[0]);
/* 677 */         this
/*     */ 
/*     */ 
/*     */           
/* 681 */           .openType = new CompositeType(param1Class.getName(), param1Class.getName(), arrayOfString, arrayOfString, (OpenType<?>[])arrayList1.<OpenType>toArray(new OpenType[0]));
/*     */       } 
/*     */     }
/*     */     
/*     */     Type getJavaType() {
/* 686 */       return this.javaClass;
/*     */     }
/*     */     
/*     */     String getName() {
/* 690 */       return this.javaClass.getName();
/*     */     }
/*     */     
/*     */     Object toOpenTypeData(Object param1Object) throws OpenDataException {
/* 694 */       if (param1Object instanceof MemoryUsage) {
/* 695 */         return MemoryUsageCompositeData.toCompositeData((MemoryUsage)param1Object);
/*     */       }
/*     */       
/* 698 */       if (param1Object instanceof ThreadInfo) {
/* 699 */         return ThreadInfoCompositeData.toCompositeData((ThreadInfo)param1Object);
/*     */       }
/*     */       
/* 702 */       if (param1Object instanceof LockInfo) {
/* 703 */         if (param1Object instanceof MonitorInfo) {
/* 704 */           return MonitorInfoCompositeData.toCompositeData((MonitorInfo)param1Object);
/*     */         }
/* 706 */         return LockInfoCompositeData.toCompositeData((LockInfo)param1Object);
/*     */       } 
/*     */       
/* 709 */       if (param1Object instanceof MemoryNotificationInfo) {
/* 710 */         return 
/* 711 */           MemoryNotifInfoCompositeData.toCompositeData((MemoryNotificationInfo)param1Object);
/*     */       }
/*     */       
/* 714 */       if (param1Object instanceof VMOption) {
/* 715 */         return VMOptionCompositeData.toCompositeData((VMOption)param1Object);
/*     */       }
/*     */       
/* 718 */       if (this.isCompositeData) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 723 */         CompositeData compositeData = (CompositeData)param1Object;
/* 724 */         CompositeType compositeType = compositeData.getCompositeType();
/* 725 */         String[] arrayOfString = compositeType.keySet().<String>toArray(new String[0]);
/* 726 */         Object[] arrayOfObject = compositeData.getAll(arrayOfString);
/* 727 */         return new CompositeDataSupport(compositeType, arrayOfString, arrayOfObject);
/*     */       } 
/*     */       
/* 730 */       throw new OpenDataException(this.javaClass.getName() + " is not supported for platform MXBeans");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object toJavaTypeData(Object param1Object) throws OpenDataException, InvalidObjectException {
/* 737 */       if (this.fromMethod == null) {
/* 738 */         throw new AssertionError("Does not support data conversion");
/*     */       }
/*     */       
/*     */       try {
/* 742 */         return this.fromMethod.invoke(null, new Object[] { param1Object });
/* 743 */       } catch (IllegalAccessException illegalAccessException) {
/*     */         
/* 745 */         throw new AssertionError(illegalAccessException);
/* 746 */       } catch (InvocationTargetException invocationTargetException) {
/*     */ 
/*     */ 
/*     */         
/* 750 */         OpenDataException openDataException = new OpenDataException("Failed to invoke " + this.fromMethod.getName() + " to convert CompositeData  to " + this.javaClass.getName());
/* 751 */         openDataException.initCause(invocationTargetException);
/* 752 */         throw openDataException;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class InProgress extends OpenType {
/*     */     private static final String description = "Marker to detect recursive type use -- internal use only!";
/*     */     private static final long serialVersionUID = -3413063475064374490L;
/*     */     
/*     */     InProgress() throws OpenDataException {
/* 762 */       super("java.lang.String", "java.lang.String", "Marker to detect recursive type use -- internal use only!");
/*     */     }
/*     */     
/*     */     public String toString() {
/* 766 */       return "Marker to detect recursive type use -- internal use only!";
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 770 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 774 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isValue(Object param1Object) {
/* 778 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 786 */       inProgress = new InProgress();
/* 787 */     } catch (OpenDataException openDataException) {
/*     */       
/* 789 */       throw new AssertionError(openDataException);
/*     */     } 
/* 791 */     inProgress = inProgress;
/*     */   }
/*     */   
/* 794 */   private static final OpenType[] simpleTypes = new OpenType[] { SimpleType.BIGDECIMAL, SimpleType.BIGINTEGER, SimpleType.BOOLEAN, SimpleType.BYTE, SimpleType.CHARACTER, SimpleType.DATE, SimpleType.DOUBLE, SimpleType.FLOAT, SimpleType.INTEGER, SimpleType.LONG, SimpleType.OBJECTNAME, SimpleType.SHORT, SimpleType.STRING, SimpleType.VOID };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 801 */       for (byte b = 0; b < simpleTypes.length; b++) {
/* 802 */         Class<?> clazz; OpenType<?> openType = simpleTypes[b];
/*     */         
/*     */         try {
/* 805 */           clazz = Class.forName(openType.getClassName(), false, MappedMXBeanType.class
/* 806 */               .getClassLoader());
/* 807 */           newBasicType(clazz, openType);
/* 808 */         } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */           
/* 811 */           throw new AssertionError(classNotFoundException);
/* 812 */         } catch (OpenDataException openDataException) {
/* 813 */           throw new AssertionError(openDataException);
/*     */         } 
/*     */         
/* 816 */         if (clazz.getName().startsWith("java.lang.")) {
/*     */           try {
/* 818 */             Field field = clazz.getField("TYPE");
/* 819 */             Class<?> clazz1 = (Class)field.get(null);
/* 820 */             newBasicType(clazz1, openType);
/* 821 */           } catch (NoSuchFieldException noSuchFieldException) {
/*     */           
/* 823 */           } catch (IllegalAccessException illegalAccessException) {
/*     */             
/* 825 */             throw new AssertionError(illegalAccessException);
/*     */           } 
/*     */         }
/*     */       } 
/* 829 */     } catch (OpenDataException openDataException) {
/* 830 */       throw new AssertionError(openDataException);
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
/*     */   private static String decapitalize(String paramString) {
/* 848 */     if (paramString == null || paramString.length() == 0) {
/* 849 */       return paramString;
/*     */     }
/* 851 */     if (paramString.length() > 1 && Character.isUpperCase(paramString.charAt(1)) && 
/* 852 */       Character.isUpperCase(paramString.charAt(0))) {
/* 853 */       return paramString;
/*     */     }
/* 855 */     char[] arrayOfChar = paramString.toCharArray();
/* 856 */     arrayOfChar[0] = Character.toLowerCase(arrayOfChar[0]);
/* 857 */     return new String(arrayOfChar);
/*     */   }
/*     */   
/*     */   abstract Type getJavaType();
/*     */   
/*     */   abstract String getName();
/*     */   
/*     */   abstract Object toOpenTypeData(Object paramObject) throws OpenDataException;
/*     */   
/*     */   abstract Object toJavaTypeData(Object paramObject) throws OpenDataException, InvalidObjectException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/MappedMXBeanType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */