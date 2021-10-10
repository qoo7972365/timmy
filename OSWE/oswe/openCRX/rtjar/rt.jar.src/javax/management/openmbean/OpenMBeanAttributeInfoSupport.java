/*      */ package javax.management.openmbean;
/*      */ 
/*      */ import com.sun.jmx.remote.util.EnvHelp;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.management.Descriptor;
/*      */ import javax.management.DescriptorRead;
/*      */ import javax.management.ImmutableDescriptor;
/*      */ import javax.management.MBeanAttributeInfo;
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
/*      */ public class OpenMBeanAttributeInfoSupport
/*      */   extends MBeanAttributeInfo
/*      */   implements OpenMBeanAttributeInfo
/*      */ {
/*      */   static final long serialVersionUID = -4867215622149721849L;
/*      */   private OpenType<?> openType;
/*      */   private final Object defaultValue;
/*      */   private final Set<?> legalValues;
/*      */   private final Comparable<?> minValue;
/*      */   private final Comparable<?> maxValue;
/*   93 */   private transient Integer myHashCode = null;
/*   94 */   private transient String myToString = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OpenMBeanAttributeInfoSupport(String paramString1, String paramString2, OpenType<?> paramOpenType, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/*  128 */     this(paramString1, paramString2, paramOpenType, paramBoolean1, paramBoolean2, paramBoolean3, (Descriptor)null);
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
/*      */   public OpenMBeanAttributeInfoSupport(String paramString1, String paramString2, OpenType<?> paramOpenType, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Descriptor paramDescriptor) {
/*  177 */     super(paramString1, (paramOpenType == null) ? null : paramOpenType
/*  178 */         .getClassName(), paramString2, paramBoolean1, paramBoolean2, paramBoolean3, 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  183 */         ImmutableDescriptor.union(new Descriptor[] {
/*  184 */             paramDescriptor, (paramOpenType == null) ? null : paramOpenType.getDescriptor()
/*      */           }));
/*      */ 
/*      */     
/*  188 */     this.openType = paramOpenType;
/*      */     
/*  190 */     paramDescriptor = getDescriptor();
/*  191 */     this.defaultValue = valueFrom(paramDescriptor, "defaultValue", paramOpenType);
/*  192 */     this.legalValues = valuesFrom(paramDescriptor, "legalValues", paramOpenType);
/*  193 */     this.minValue = comparableValueFrom(paramDescriptor, "minValue", paramOpenType);
/*  194 */     this.maxValue = comparableValueFrom(paramDescriptor, "maxValue", paramOpenType);
/*      */     
/*      */     try {
/*  197 */       check(this);
/*  198 */     } catch (OpenDataException openDataException) {
/*  199 */       throw new IllegalArgumentException(openDataException.getMessage(), openDataException);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> OpenMBeanAttributeInfoSupport(String paramString1, String paramString2, OpenType<T> paramOpenType, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, T paramT) throws OpenDataException {
/*  250 */     this(paramString1, paramString2, paramOpenType, paramBoolean1, paramBoolean2, paramBoolean3, paramT, (T[])null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> OpenMBeanAttributeInfoSupport(String paramString1, String paramString2, OpenType<T> paramOpenType, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, T paramT, T[] paramArrayOfT) throws OpenDataException {
/*  320 */     this(paramString1, paramString2, paramOpenType, paramBoolean1, paramBoolean2, paramBoolean3, paramT, paramArrayOfT, (Comparable<T>)null, (Comparable<T>)null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> OpenMBeanAttributeInfoSupport(String paramString1, String paramString2, OpenType<T> paramOpenType, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, T paramT, Comparable<T> paramComparable1, Comparable<T> paramComparable2) throws OpenDataException {
/*  393 */     this(paramString1, paramString2, paramOpenType, paramBoolean1, paramBoolean2, paramBoolean3, paramT, (T[])null, paramComparable1, paramComparable2);
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
/*      */   private <T> OpenMBeanAttributeInfoSupport(String paramString1, String paramString2, OpenType<T> paramOpenType, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, T paramT, T[] paramArrayOfT, Comparable<T> paramComparable1, Comparable<T> paramComparable2) throws OpenDataException {
/*  408 */     super(paramString1, (paramOpenType == null) ? null : paramOpenType
/*  409 */         .getClassName(), paramString2, paramBoolean1, paramBoolean2, paramBoolean3, 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  414 */         makeDescriptor(paramOpenType, paramT, paramArrayOfT, paramComparable1, paramComparable2));
/*      */ 
/*      */     
/*  417 */     this.openType = paramOpenType;
/*      */     
/*  419 */     Descriptor descriptor = getDescriptor();
/*  420 */     this.defaultValue = paramT;
/*  421 */     this.minValue = paramComparable1;
/*  422 */     this.maxValue = paramComparable2;
/*      */ 
/*      */     
/*  425 */     this.legalValues = (Set)descriptor.getFieldValue("legalValues");
/*      */     
/*  427 */     check(this);
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
/*      */   private Object readResolve() {
/*  439 */     if ((getDescriptor().getFieldNames()).length == 0) {
/*  440 */       OpenType openType = cast(this.openType);
/*  441 */       Set set = cast(this.legalValues);
/*  442 */       Comparable comparable1 = cast(this.minValue);
/*  443 */       Comparable comparable2 = cast(this.maxValue);
/*  444 */       return new OpenMBeanAttributeInfoSupport(this.name, this.description, this.openType, 
/*      */           
/*  446 */           isReadable(), isWritable(), isIs(), 
/*  447 */           makeDescriptor(openType, this.defaultValue, set, comparable1, comparable2));
/*      */     } 
/*      */     
/*  450 */     return this;
/*      */   }
/*      */   
/*      */   static void check(OpenMBeanParameterInfo paramOpenMBeanParameterInfo) throws OpenDataException {
/*  454 */     OpenType<?> openType = paramOpenMBeanParameterInfo.getOpenType();
/*  455 */     if (openType == null) {
/*  456 */       throw new IllegalArgumentException("OpenType cannot be null");
/*      */     }
/*  458 */     if (paramOpenMBeanParameterInfo.getName() == null || paramOpenMBeanParameterInfo
/*  459 */       .getName().trim().equals("")) {
/*  460 */       throw new IllegalArgumentException("Name cannot be null or empty");
/*      */     }
/*  462 */     if (paramOpenMBeanParameterInfo.getDescription() == null || paramOpenMBeanParameterInfo
/*  463 */       .getDescription().trim().equals("")) {
/*  464 */       throw new IllegalArgumentException("Description cannot be null or empty");
/*      */     }
/*      */ 
/*      */     
/*  468 */     if (paramOpenMBeanParameterInfo.hasDefaultValue()) {
/*      */ 
/*      */       
/*  471 */       if (openType.isArray() || openType instanceof TabularType) {
/*  472 */         throw new OpenDataException("Default value not supported for ArrayType and TabularType");
/*      */       }
/*      */ 
/*      */       
/*  476 */       if (!openType.isValue(paramOpenMBeanParameterInfo.getDefaultValue())) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  481 */         String str = "Argument defaultValue's class [\"" + paramOpenMBeanParameterInfo.getDefaultValue().getClass().getName() + "\"] does not match the one defined in openType[\"" + openType.getClassName() + "\"]";
/*  482 */         throw new OpenDataException(str);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  488 */     if (paramOpenMBeanParameterInfo.hasLegalValues() && (paramOpenMBeanParameterInfo
/*  489 */       .hasMinValue() || paramOpenMBeanParameterInfo.hasMaxValue())) {
/*  490 */       throw new OpenDataException("cannot have both legalValue and minValue or maxValue");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  495 */     if (paramOpenMBeanParameterInfo.hasMinValue() && !openType.isValue(paramOpenMBeanParameterInfo.getMinValue())) {
/*      */ 
/*      */       
/*  498 */       String str = "Type of minValue [" + paramOpenMBeanParameterInfo.getMinValue().getClass().getName() + "] does not match OpenType [" + openType.getClassName() + "]";
/*  499 */       throw new OpenDataException(str);
/*      */     } 
/*  501 */     if (paramOpenMBeanParameterInfo.hasMaxValue() && !openType.isValue(paramOpenMBeanParameterInfo.getMaxValue())) {
/*      */ 
/*      */       
/*  504 */       String str = "Type of maxValue [" + paramOpenMBeanParameterInfo.getMaxValue().getClass().getName() + "] does not match OpenType [" + openType.getClassName() + "]";
/*  505 */       throw new OpenDataException(str);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  510 */     if (paramOpenMBeanParameterInfo.hasDefaultValue()) {
/*  511 */       Object object = paramOpenMBeanParameterInfo.getDefaultValue();
/*  512 */       if (paramOpenMBeanParameterInfo.hasLegalValues() && 
/*  513 */         !paramOpenMBeanParameterInfo.getLegalValues().contains(object)) {
/*  514 */         throw new OpenDataException("defaultValue is not contained in legalValues");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  520 */       if (paramOpenMBeanParameterInfo.hasMinValue() && 
/*  521 */         compare(paramOpenMBeanParameterInfo.getMinValue(), object) > 0) {
/*  522 */         throw new OpenDataException("minValue cannot be greater than defaultValue");
/*      */       }
/*      */ 
/*      */       
/*  526 */       if (paramOpenMBeanParameterInfo.hasMaxValue() && 
/*  527 */         compare(paramOpenMBeanParameterInfo.getMaxValue(), object) < 0) {
/*  528 */         throw new OpenDataException("maxValue cannot be less than defaultValue");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  536 */     if (paramOpenMBeanParameterInfo.hasLegalValues()) {
/*      */       
/*  538 */       if (openType instanceof TabularType || openType.isArray()) {
/*  539 */         throw new OpenDataException("Legal values not supported for TabularType and arrays");
/*      */       }
/*      */ 
/*      */       
/*  543 */       for (Object object : paramOpenMBeanParameterInfo.getLegalValues()) {
/*  544 */         if (!openType.isValue(object)) {
/*      */ 
/*      */ 
/*      */           
/*  548 */           String str = "Element of legalValues [" + object + "] is not a valid value for the specified openType [" + openType.toString() + "]";
/*  549 */           throw new OpenDataException(str);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  557 */     if (paramOpenMBeanParameterInfo.hasMinValue() && paramOpenMBeanParameterInfo.hasMaxValue() && 
/*  558 */       compare(paramOpenMBeanParameterInfo.getMinValue(), paramOpenMBeanParameterInfo.getMaxValue()) > 0) {
/*  559 */       throw new OpenDataException("minValue cannot be greater than maxValue");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int compare(Object paramObject1, Object paramObject2) {
/*  568 */     return ((Comparable<Object>)paramObject1).compareTo(paramObject2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> Descriptor makeDescriptor(OpenType<T> paramOpenType, T paramT, T[] paramArrayOfT, Comparable<T> paramComparable1, Comparable<T> paramComparable2) {
/*  576 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  577 */     if (paramT != null)
/*  578 */       hashMap.put("defaultValue", paramT); 
/*  579 */     if (paramArrayOfT != null) {
/*  580 */       HashSet<T> hashSet = new HashSet();
/*  581 */       for (T t : paramArrayOfT)
/*  582 */         hashSet.add(t); 
/*  583 */       Set<T> set = Collections.unmodifiableSet(hashSet);
/*  584 */       hashMap.put("legalValues", set);
/*      */     } 
/*  586 */     if (paramComparable1 != null)
/*  587 */       hashMap.put("minValue", paramComparable1); 
/*  588 */     if (paramComparable2 != null)
/*  589 */       hashMap.put("maxValue", paramComparable2); 
/*  590 */     if (hashMap.isEmpty()) {
/*  591 */       return paramOpenType.getDescriptor();
/*      */     }
/*  593 */     hashMap.put("openType", paramOpenType);
/*  594 */     return new ImmutableDescriptor((Map)hashMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> Descriptor makeDescriptor(OpenType<T> paramOpenType, T paramT, Set<T> paramSet, Comparable<T> paramComparable1, Comparable<T> paramComparable2) {
/*      */     Object[] arrayOfObject;
/*  604 */     if (paramSet == null) {
/*  605 */       arrayOfObject = null;
/*      */     } else {
/*  607 */       arrayOfObject = cast(new Object[paramSet.size()]);
/*  608 */       paramSet.toArray(arrayOfObject);
/*      */     } 
/*  610 */     return makeDescriptor(paramOpenType, paramT, (T[])arrayOfObject, paramComparable1, paramComparable2);
/*      */   }
/*      */ 
/*      */   
/*      */   static <T> T valueFrom(Descriptor paramDescriptor, String paramString, OpenType<T> paramOpenType) {
/*  615 */     Object object = paramDescriptor.getFieldValue(paramString);
/*  616 */     if (object == null)
/*  617 */       return null; 
/*      */     try {
/*  619 */       return convertFrom(object, paramOpenType);
/*  620 */     } catch (Exception exception) {
/*      */ 
/*      */       
/*  623 */       String str = "Cannot convert descriptor field " + paramString + "  to " + paramOpenType.getTypeName();
/*  624 */       throw (IllegalArgumentException)EnvHelp.initCause(new IllegalArgumentException(str), exception);
/*      */     } 
/*      */   }
/*      */   
/*      */   static <T> Set<T> valuesFrom(Descriptor paramDescriptor, String paramString, OpenType<T> paramOpenType) {
/*      */     List list;
/*  630 */     Object object = paramDescriptor.getFieldValue(paramString);
/*  631 */     if (object == null) {
/*  632 */       return null;
/*      */     }
/*  634 */     if (object instanceof Set) {
/*  635 */       Set set2 = (Set)object;
/*  636 */       boolean bool = true;
/*  637 */       for (Object object1 : set2) {
/*  638 */         if (!paramOpenType.isValue(object1)) {
/*  639 */           bool = false;
/*      */           break;
/*      */         } 
/*      */       } 
/*  643 */       if (bool)
/*  644 */         return cast(set2); 
/*  645 */       Set set1 = set2;
/*  646 */     } else if (object instanceof Object[]) {
/*  647 */       list = Arrays.asList((Object[])object);
/*      */     }
/*      */     else {
/*      */       
/*  651 */       String str = "Descriptor value for " + paramString + " must be a Set or an array: " + object.getClass().getName();
/*  652 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  655 */     HashSet<T> hashSet = new HashSet();
/*  656 */     for (Object object1 : list)
/*  657 */       hashSet.add(convertFrom(object1, paramOpenType)); 
/*  658 */     return hashSet;
/*      */   }
/*      */ 
/*      */   
/*      */   static <T> Comparable<?> comparableValueFrom(Descriptor paramDescriptor, String paramString, OpenType<T> paramOpenType) {
/*  663 */     Comparable comparable = (Comparable)valueFrom(paramDescriptor, paramString, (OpenType)paramOpenType);
/*  664 */     if (comparable == null || comparable instanceof Comparable)
/*  665 */       return comparable; 
/*  666 */     String str = "Descriptor field " + paramString + " with value " + comparable + " is not Comparable";
/*      */ 
/*      */     
/*  669 */     throw new IllegalArgumentException(str);
/*      */   }
/*      */   
/*      */   private static <T> T convertFrom(Object paramObject, OpenType<T> paramOpenType) {
/*  673 */     if (paramOpenType.isValue(paramObject)) {
/*  674 */       return (T)cast(paramObject);
/*      */     }
/*      */     
/*  677 */     return convertFromStrings(paramObject, paramOpenType);
/*      */   }
/*      */   
/*      */   private static <T> T convertFromStrings(Object paramObject, OpenType<T> paramOpenType) {
/*  681 */     if (paramOpenType instanceof ArrayType)
/*  682 */       return convertFromStringArray(paramObject, paramOpenType); 
/*  683 */     if (paramObject instanceof String) {
/*  684 */       return convertFromString((String)paramObject, paramOpenType);
/*      */     }
/*      */     
/*  687 */     String str = "Cannot convert value " + paramObject + " of type " + paramObject.getClass().getName() + " to type " + paramOpenType.getTypeName();
/*  688 */     throw new IllegalArgumentException(str);
/*      */   } private static <T> T convertFromString(String paramString, OpenType<T> paramOpenType) {
/*      */     Class<?> clazz;
/*      */     Method method;
/*      */     Constructor<T> constructor;
/*      */     try {
/*  694 */       method = (Method)paramOpenType.safeGetClassName();
/*  695 */       ReflectUtil.checkPackageAccess((String)method);
/*  696 */       clazz = cast(Class.forName((String)method));
/*  697 */     } catch (ClassNotFoundException null) {
/*  698 */       throw new NoClassDefFoundError(method.toString());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  706 */       method = clazz.getMethod("valueOf", new Class[] { String.class });
/*  707 */       if (!Modifier.isStatic(method.getModifiers()) || method
/*  708 */         .getReturnType() != clazz)
/*  709 */         method = null; 
/*  710 */     } catch (NoSuchMethodException null) {
/*  711 */       method = null;
/*      */     } 
/*  713 */     if (method != null) {
/*      */       try {
/*  715 */         return (T)clazz.cast(MethodUtil.invoke(method, null, new Object[] { paramString }));
/*  716 */       } catch (Exception null) {
/*  717 */         String str = "Could not convert \"" + paramString + "\" using method: " + method;
/*      */         
/*  719 */         throw new IllegalArgumentException(str, constructor);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  728 */       constructor = (Constructor)clazz.getConstructor(new Class[] { String.class });
/*  729 */     } catch (NoSuchMethodException noSuchMethodException) {
/*  730 */       constructor = null;
/*      */     } 
/*  732 */     if (constructor != null) {
/*      */       try {
/*  734 */         return constructor.newInstance(new Object[] { paramString });
/*  735 */       } catch (Exception exception) {
/*  736 */         String str = "Could not convert \"" + paramString + "\" using constructor: " + constructor;
/*      */         
/*  738 */         throw new IllegalArgumentException(str, exception);
/*      */       } 
/*      */     }
/*      */     
/*  742 */     throw new IllegalArgumentException("Don't know how to convert string to " + paramOpenType
/*      */         
/*  744 */         .getTypeName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T convertFromStringArray(Object paramObject, OpenType<T> paramOpenType) {
/*      */     Class<?> clazz1, clazz2;
/*      */     OpenType<?> openType2;
/*  755 */     ArrayType arrayType = (ArrayType)paramOpenType;
/*  756 */     OpenType<?> openType1 = arrayType.getElementOpenType();
/*  757 */     int i = arrayType.getDimension();
/*  758 */     String str = "[";
/*  759 */     for (byte b1 = 1; b1 < i; b1++) {
/*  760 */       str = str + "[";
/*      */     }
/*      */     
/*      */     try {
/*  764 */       String str1 = openType1.safeGetClassName();
/*      */ 
/*      */       
/*  767 */       ReflectUtil.checkPackageAccess(str1);
/*      */ 
/*      */       
/*  770 */       clazz1 = Class.forName(str + "Ljava.lang.String;");
/*      */       
/*  772 */       clazz2 = Class.forName(str + "L" + str1 + ";");
/*  773 */     } catch (ClassNotFoundException classNotFoundException) {
/*  774 */       throw new NoClassDefFoundError(classNotFoundException.toString());
/*      */     } 
/*  776 */     if (!clazz1.isInstance(paramObject)) {
/*      */ 
/*      */       
/*  779 */       String str1 = "Value for " + i + "-dimensional array of " + openType1.getTypeName() + " must be same type or a String array with same dimensions";
/*      */       
/*  781 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/*  784 */     if (i == 1) {
/*  785 */       openType2 = openType1;
/*      */     } else {
/*      */       try {
/*  788 */         openType2 = new ArrayType(i - 1, openType1);
/*  789 */       } catch (OpenDataException openDataException) {
/*  790 */         throw new IllegalArgumentException(openDataException.getMessage(), openDataException);
/*      */       } 
/*      */     } 
/*      */     
/*  794 */     int j = Array.getLength(paramObject);
/*      */     
/*  796 */     Object[] arrayOfObject = (Object[])Array.newInstance(clazz2.getComponentType(), j);
/*  797 */     for (byte b2 = 0; b2 < j; b2++) {
/*  798 */       Object object = Array.get(paramObject, b2);
/*      */       
/*  800 */       Object object1 = convertFromStrings(object, (OpenType)openType2);
/*  801 */       Array.set(arrayOfObject, b2, object1);
/*      */     } 
/*  803 */     return cast(arrayOfObject);
/*      */   }
/*      */ 
/*      */   
/*      */   static <T> T cast(Object paramObject) {
/*  808 */     return (T)paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OpenType<?> getOpenType() {
/*  816 */     return this.openType;
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
/*      */   public Object getDefaultValue() {
/*  832 */     return this.defaultValue;
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
/*      */   public Set<?> getLegalValues() {
/*  849 */     return this.legalValues;
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
/*      */   public Comparable<?> getMinValue() {
/*  862 */     return this.minValue;
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
/*      */   public Comparable<?> getMaxValue() {
/*  875 */     return this.maxValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasDefaultValue() {
/*  886 */     return (this.defaultValue != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasLegalValues() {
/*  897 */     return (this.legalValues != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasMinValue() {
/*  908 */     return (this.minValue != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasMaxValue() {
/*  919 */     return (this.maxValue != null);
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
/*      */   public boolean isValue(Object paramObject) {
/*  936 */     return isValue(this, paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean isValue(OpenMBeanParameterInfo paramOpenMBeanParameterInfo, Object paramObject) {
/*  941 */     if (paramOpenMBeanParameterInfo.hasDefaultValue() && paramObject == null)
/*  942 */       return true; 
/*  943 */     return (paramOpenMBeanParameterInfo
/*  944 */       .getOpenType().isValue(paramObject) && (
/*  945 */       !paramOpenMBeanParameterInfo.hasLegalValues() || paramOpenMBeanParameterInfo.getLegalValues().contains(paramObject)) && (
/*  946 */       !paramOpenMBeanParameterInfo.hasMinValue() || paramOpenMBeanParameterInfo
/*  947 */       .getMinValue().compareTo(paramObject) <= 0) && (
/*  948 */       !paramOpenMBeanParameterInfo.hasMaxValue() || paramOpenMBeanParameterInfo
/*  949 */       .getMaxValue().compareTo(paramObject) >= 0));
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
/*      */   public boolean equals(Object paramObject) {
/*  983 */     if (!(paramObject instanceof OpenMBeanAttributeInfo)) {
/*  984 */       return false;
/*      */     }
/*  986 */     OpenMBeanAttributeInfo openMBeanAttributeInfo = (OpenMBeanAttributeInfo)paramObject;
/*      */     
/*  988 */     return (
/*  989 */       isReadable() == openMBeanAttributeInfo.isReadable() && 
/*  990 */       isWritable() == openMBeanAttributeInfo.isWritable() && 
/*  991 */       isIs() == openMBeanAttributeInfo.isIs() && 
/*  992 */       equal(this, openMBeanAttributeInfo));
/*      */   }
/*      */   
/*      */   static boolean equal(OpenMBeanParameterInfo paramOpenMBeanParameterInfo1, OpenMBeanParameterInfo paramOpenMBeanParameterInfo2) {
/*  996 */     if (paramOpenMBeanParameterInfo1 instanceof DescriptorRead) {
/*  997 */       if (!(paramOpenMBeanParameterInfo2 instanceof DescriptorRead))
/*  998 */         return false; 
/*  999 */       Descriptor descriptor1 = ((DescriptorRead)paramOpenMBeanParameterInfo1).getDescriptor();
/* 1000 */       Descriptor descriptor2 = ((DescriptorRead)paramOpenMBeanParameterInfo2).getDescriptor();
/* 1001 */       if (!descriptor1.equals(descriptor2))
/* 1002 */         return false; 
/* 1003 */     } else if (paramOpenMBeanParameterInfo2 instanceof DescriptorRead) {
/* 1004 */       return false;
/*      */     } 
/* 1006 */     if (paramOpenMBeanParameterInfo1
/* 1007 */       .getName().equals(paramOpenMBeanParameterInfo2.getName()) && paramOpenMBeanParameterInfo1
/* 1008 */       .getOpenType().equals(paramOpenMBeanParameterInfo2.getOpenType()) && (
/* 1009 */       paramOpenMBeanParameterInfo1.hasDefaultValue() ? paramOpenMBeanParameterInfo1
/* 1010 */       .getDefaultValue().equals(paramOpenMBeanParameterInfo2.getDefaultValue()) : 
/* 1011 */       !paramOpenMBeanParameterInfo2.hasDefaultValue())) {
/* 1012 */       if (paramOpenMBeanParameterInfo1.hasMinValue() ? paramOpenMBeanParameterInfo1
/* 1013 */         .getMinValue().equals(paramOpenMBeanParameterInfo2.getMinValue()) : 
/* 1014 */         !paramOpenMBeanParameterInfo2.hasMinValue()) {
/* 1015 */         if (paramOpenMBeanParameterInfo1.hasMaxValue() ? paramOpenMBeanParameterInfo1
/* 1016 */           .getMaxValue().equals(paramOpenMBeanParameterInfo2.getMaxValue()) : 
/* 1017 */           !paramOpenMBeanParameterInfo2.hasMaxValue()) {
/* 1018 */           if (paramOpenMBeanParameterInfo1.hasLegalValues() ? paramOpenMBeanParameterInfo1
/* 1019 */             .getLegalValues().equals(paramOpenMBeanParameterInfo2.getLegalValues()) : 
/* 1020 */             !paramOpenMBeanParameterInfo2.hasLegalValues());
/*      */         }
/*      */       }
/*      */     }
/*      */     return false;
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
/*      */   public int hashCode() {
/* 1058 */     if (this.myHashCode == null) {
/* 1059 */       this.myHashCode = Integer.valueOf(hashCode(this));
/*      */     }
/*      */ 
/*      */     
/* 1063 */     return this.myHashCode.intValue();
/*      */   }
/*      */   
/*      */   static int hashCode(OpenMBeanParameterInfo paramOpenMBeanParameterInfo) {
/* 1067 */     int i = 0;
/* 1068 */     i += paramOpenMBeanParameterInfo.getName().hashCode();
/* 1069 */     i += paramOpenMBeanParameterInfo.getOpenType().hashCode();
/* 1070 */     if (paramOpenMBeanParameterInfo.hasDefaultValue())
/* 1071 */       i += paramOpenMBeanParameterInfo.getDefaultValue().hashCode(); 
/* 1072 */     if (paramOpenMBeanParameterInfo.hasMinValue())
/* 1073 */       i += paramOpenMBeanParameterInfo.getMinValue().hashCode(); 
/* 1074 */     if (paramOpenMBeanParameterInfo.hasMaxValue())
/* 1075 */       i += paramOpenMBeanParameterInfo.getMaxValue().hashCode(); 
/* 1076 */     if (paramOpenMBeanParameterInfo.hasLegalValues())
/* 1077 */       i += paramOpenMBeanParameterInfo.getLegalValues().hashCode(); 
/* 1078 */     if (paramOpenMBeanParameterInfo instanceof DescriptorRead)
/* 1079 */       i += ((DescriptorRead)paramOpenMBeanParameterInfo).getDescriptor().hashCode(); 
/* 1080 */     return i;
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
/*      */   public String toString() {
/* 1107 */     if (this.myToString == null) {
/* 1108 */       this.myToString = toString(this);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1113 */     return this.myToString;
/*      */   }
/*      */ 
/*      */   
/*      */   static String toString(OpenMBeanParameterInfo paramOpenMBeanParameterInfo) {
/* 1118 */     Descriptor descriptor = (paramOpenMBeanParameterInfo instanceof DescriptorRead) ? ((DescriptorRead)paramOpenMBeanParameterInfo).getDescriptor() : null;
/* 1119 */     return paramOpenMBeanParameterInfo
/* 1120 */       .getClass().getName() + "(name=" + paramOpenMBeanParameterInfo
/* 1121 */       .getName() + ",openType=" + paramOpenMBeanParameterInfo
/* 1122 */       .getOpenType() + ",default=" + paramOpenMBeanParameterInfo
/* 1123 */       .getDefaultValue() + ",minValue=" + paramOpenMBeanParameterInfo
/* 1124 */       .getMinValue() + ",maxValue=" + paramOpenMBeanParameterInfo
/* 1125 */       .getMaxValue() + ",legalValues=" + paramOpenMBeanParameterInfo
/* 1126 */       .getLegalValues() + ((descriptor == null) ? "" : (",descriptor=" + descriptor)) + ")";
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/openmbean/OpenMBeanAttributeInfoSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */