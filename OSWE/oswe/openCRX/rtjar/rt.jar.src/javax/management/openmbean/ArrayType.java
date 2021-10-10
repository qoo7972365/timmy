/*      */ package javax.management.openmbean;
/*      */ 
/*      */ import java.io.ObjectStreamException;
/*      */ import java.lang.reflect.Array;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ArrayType<T>
/*      */   extends OpenType<T>
/*      */ {
/*      */   static final long serialVersionUID = 720504429830309770L;
/*      */   private int dimension;
/*      */   private OpenType<?> elementType;
/*      */   private boolean primitiveArray;
/*  139 */   private transient Integer myHashCode = null;
/*  140 */   private transient String myToString = null;
/*      */   
/*      */   private static final int PRIMITIVE_WRAPPER_NAME_INDEX = 0;
/*      */   
/*      */   private static final int PRIMITIVE_TYPE_NAME_INDEX = 1;
/*      */   
/*      */   private static final int PRIMITIVE_TYPE_KEY_INDEX = 2;
/*      */   private static final int PRIMITIVE_OPEN_TYPE_INDEX = 3;
/*  148 */   private static final Object[][] PRIMITIVE_ARRAY_TYPES = new Object[][] { { Boolean.class
/*  149 */         .getName(), boolean.class.getName(), "Z", SimpleType.BOOLEAN }, { Character.class
/*  150 */         .getName(), char.class.getName(), "C", SimpleType.CHARACTER }, { Byte.class
/*  151 */         .getName(), byte.class.getName(), "B", SimpleType.BYTE }, { Short.class
/*  152 */         .getName(), short.class.getName(), "S", SimpleType.SHORT }, { Integer.class
/*  153 */         .getName(), int.class.getName(), "I", SimpleType.INTEGER }, { Long.class
/*  154 */         .getName(), long.class.getName(), "J", SimpleType.LONG }, { Float.class
/*  155 */         .getName(), float.class.getName(), "F", SimpleType.FLOAT }, { Double.class
/*  156 */         .getName(), double.class.getName(), "D", SimpleType.DOUBLE } };
/*      */ 
/*      */   
/*      */   static boolean isPrimitiveContentType(String paramString) {
/*  160 */     for (Object[] arrayOfObject : PRIMITIVE_ARRAY_TYPES) {
/*  161 */       if (arrayOfObject[2].equals(paramString)) {
/*  162 */         return true;
/*      */       }
/*      */     } 
/*  165 */     return false;
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
/*      */   static String getPrimitiveTypeKey(String paramString) {
/*  178 */     for (Object[] arrayOfObject : PRIMITIVE_ARRAY_TYPES) {
/*  179 */       if (paramString.equals(arrayOfObject[0]))
/*  180 */         return (String)arrayOfObject[2]; 
/*      */     } 
/*  182 */     return null;
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
/*      */   static String getPrimitiveTypeName(String paramString) {
/*  196 */     for (Object[] arrayOfObject : PRIMITIVE_ARRAY_TYPES) {
/*  197 */       if (paramString.equals(arrayOfObject[0]))
/*  198 */         return (String)arrayOfObject[1]; 
/*      */     } 
/*  200 */     return null;
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
/*      */   static SimpleType<?> getPrimitiveOpenType(String paramString) {
/*  215 */     for (Object[] arrayOfObject : PRIMITIVE_ARRAY_TYPES) {
/*  216 */       if (paramString.equals(arrayOfObject[1]))
/*  217 */         return (SimpleType)arrayOfObject[3]; 
/*      */     } 
/*  219 */     return null;
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
/*      */   public ArrayType(int paramInt, OpenType<?> paramOpenType) throws OpenDataException {
/*  292 */     super(buildArrayClassName(paramInt, paramOpenType), 
/*  293 */         buildArrayClassName(paramInt, paramOpenType), 
/*  294 */         buildArrayDescription(paramInt, paramOpenType));
/*      */ 
/*      */ 
/*      */     
/*  298 */     if (paramOpenType.isArray()) {
/*  299 */       ArrayType arrayType = (ArrayType)paramOpenType;
/*  300 */       this.dimension = arrayType.getDimension() + paramInt;
/*  301 */       this.elementType = arrayType.getElementOpenType();
/*  302 */       this.primitiveArray = arrayType.isPrimitiveArray();
/*      */     } else {
/*  304 */       this.dimension = paramInt;
/*  305 */       this.elementType = paramOpenType;
/*  306 */       this.primitiveArray = false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayType(SimpleType<?> paramSimpleType, boolean paramBoolean) throws OpenDataException {
/*  373 */     super(buildArrayClassName(1, paramSimpleType, paramBoolean), 
/*  374 */         buildArrayClassName(1, paramSimpleType, paramBoolean), 
/*  375 */         buildArrayDescription(1, paramSimpleType, paramBoolean), true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  380 */     this.dimension = 1;
/*  381 */     this.elementType = paramSimpleType;
/*  382 */     this.primitiveArray = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ArrayType(String paramString1, String paramString2, String paramString3, int paramInt, OpenType<?> paramOpenType, boolean paramBoolean) {
/*  389 */     super(paramString1, paramString2, paramString3, true);
/*  390 */     this.dimension = paramInt;
/*  391 */     this.elementType = paramOpenType;
/*  392 */     this.primitiveArray = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String buildArrayClassName(int paramInt, OpenType<?> paramOpenType) throws OpenDataException {
/*  398 */     boolean bool = false;
/*  399 */     if (paramOpenType.isArray()) {
/*  400 */       bool = ((ArrayType)paramOpenType).isPrimitiveArray();
/*      */     }
/*  402 */     return buildArrayClassName(paramInt, paramOpenType, bool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String buildArrayClassName(int paramInt, OpenType<?> paramOpenType, boolean paramBoolean) throws OpenDataException {
/*  409 */     if (paramInt < 1) {
/*  410 */       throw new IllegalArgumentException("Value of argument dimension must be greater than 0");
/*      */     }
/*      */     
/*  413 */     StringBuilder stringBuilder = new StringBuilder();
/*  414 */     String str = paramOpenType.getClassName();
/*      */     
/*  416 */     for (byte b = 1; b <= paramInt; b++) {
/*  417 */       stringBuilder.append('[');
/*      */     }
/*  419 */     if (paramOpenType.isArray()) {
/*  420 */       stringBuilder.append(str);
/*      */     }
/*  422 */     else if (paramBoolean) {
/*  423 */       String str1 = getPrimitiveTypeKey(str);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  428 */       if (str1 == null) {
/*  429 */         throw new OpenDataException("Element type is not primitive: " + str);
/*      */       }
/*  431 */       stringBuilder.append(str1);
/*      */     } else {
/*  433 */       stringBuilder.append("L");
/*  434 */       stringBuilder.append(str);
/*  435 */       stringBuilder.append(';');
/*      */     } 
/*      */     
/*  438 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String buildArrayDescription(int paramInt, OpenType<?> paramOpenType) throws OpenDataException {
/*  444 */     boolean bool = false;
/*  445 */     if (paramOpenType.isArray()) {
/*  446 */       bool = ((ArrayType)paramOpenType).isPrimitiveArray();
/*      */     }
/*  448 */     return buildArrayDescription(paramInt, paramOpenType, bool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String buildArrayDescription(int paramInt, OpenType<?> paramOpenType, boolean paramBoolean) throws OpenDataException {
/*  455 */     if (paramOpenType.isArray()) {
/*  456 */       ArrayType arrayType = (ArrayType)paramOpenType;
/*  457 */       paramInt += arrayType.getDimension();
/*  458 */       paramOpenType = arrayType.getElementOpenType();
/*  459 */       paramBoolean = arrayType.isPrimitiveArray();
/*      */     } 
/*  461 */     StringBuilder stringBuilder = new StringBuilder(paramInt + "-dimension array of ");
/*      */     
/*  463 */     String str = paramOpenType.getClassName();
/*  464 */     if (paramBoolean) {
/*      */ 
/*      */       
/*  467 */       String str1 = getPrimitiveTypeName(str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  473 */       if (str1 == null) {
/*  474 */         throw new OpenDataException("Element is not a primitive type: " + str);
/*      */       }
/*  476 */       stringBuilder.append(str1);
/*      */     } else {
/*  478 */       stringBuilder.append(str);
/*      */     } 
/*  480 */     return stringBuilder.toString();
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
/*      */   public int getDimension() {
/*  492 */     return this.dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OpenType<?> getElementOpenType() {
/*  502 */     return this.elementType;
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
/*      */   public boolean isPrimitiveArray() {
/*  515 */     return this.primitiveArray;
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
/*      */   public boolean isValue(Object paramObject) {
/*  550 */     if (paramObject == null) {
/*  551 */       return false;
/*      */     }
/*      */     
/*  554 */     Class<?> clazz = paramObject.getClass();
/*  555 */     String str = clazz.getName();
/*      */ 
/*      */ 
/*      */     
/*  559 */     if (!clazz.isArray()) {
/*  560 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  566 */     if (getClassName().equals(str)) {
/*  567 */       return true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  583 */     if (this.elementType.getClassName().equals(TabularData.class.getName()) || this.elementType
/*  584 */       .getClassName().equals(CompositeData.class.getName())) {
/*      */ 
/*      */       
/*  587 */       boolean bool = this.elementType.getClassName().equals(TabularData.class.getName());
/*  588 */       int[] arrayOfInt = new int[getDimension()];
/*  589 */       Class clazz1 = (Class)(bool ? TabularData.class : CompositeData.class);
/*  590 */       Class<?> clazz2 = Array.newInstance(clazz1, arrayOfInt).getClass();
/*      */ 
/*      */       
/*  593 */       if (!clazz2.isAssignableFrom(clazz)) {
/*  594 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  598 */       if (!checkElementsType((Object[])paramObject, this.dimension)) {
/*  599 */         return false;
/*      */       }
/*      */       
/*  602 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  606 */     return false;
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
/*      */   private boolean checkElementsType(Object[] paramArrayOfObject, int paramInt) {
/*  619 */     if (paramInt > 1) {
/*  620 */       for (byte b1 = 0; b1 < paramArrayOfObject.length; b1++) {
/*  621 */         if (!checkElementsType((Object[])paramArrayOfObject[b1], paramInt - 1)) {
/*  622 */           return false;
/*      */         }
/*      */       } 
/*  625 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  629 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/*  630 */       if (paramArrayOfObject[b] != null && !getElementOpenType().isValue(paramArrayOfObject[b])) {
/*  631 */         return false;
/*      */       }
/*      */     } 
/*  634 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isAssignableFrom(OpenType<?> paramOpenType) {
/*  640 */     if (!(paramOpenType instanceof ArrayType))
/*  641 */       return false; 
/*  642 */     ArrayType arrayType = (ArrayType)paramOpenType;
/*  643 */     return (arrayType.getDimension() == getDimension() && arrayType
/*  644 */       .isPrimitiveArray() == isPrimitiveArray() && arrayType
/*  645 */       .getElementOpenType().isAssignableFrom(getElementOpenType()));
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
/*      */   public boolean equals(Object paramObject) {
/*  672 */     if (paramObject == null) {
/*  673 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  678 */     if (!(paramObject instanceof ArrayType))
/*  679 */       return false; 
/*  680 */     ArrayType arrayType = (ArrayType)paramObject;
/*      */ 
/*      */ 
/*      */     
/*  684 */     if (this.dimension != arrayType.dimension) {
/*  685 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  690 */     if (!this.elementType.equals(arrayType.elementType)) {
/*  691 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  696 */     return (this.primitiveArray == arrayType.primitiveArray);
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
/*      */   public int hashCode() {
/*  724 */     if (this.myHashCode == null) {
/*  725 */       int i = 0;
/*  726 */       i += this.dimension;
/*  727 */       i += this.elementType.hashCode();
/*  728 */       i += Boolean.valueOf(this.primitiveArray).hashCode();
/*  729 */       this.myHashCode = Integer.valueOf(i);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  734 */     return this.myHashCode.intValue();
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
/*      */   public String toString() {
/*  756 */     if (this.myToString == null) {
/*  757 */       this
/*  758 */         .myToString = getClass().getName() + "(name=" + getTypeName() + ",dimension=" + this.dimension + ",elementType=" + this.elementType + ",primitiveArray=" + this.primitiveArray + ")";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  766 */     return this.myToString;
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
/*      */   public static <E> ArrayType<E[]> getArrayType(OpenType<E> paramOpenType) throws OpenDataException {
/*  812 */     return new ArrayType<>(1, paramOpenType);
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
/*      */   public static <T> ArrayType<T> getPrimitiveArrayType(Class<T> paramClass) {
/*  853 */     if (!paramClass.isArray()) {
/*  854 */       throw new IllegalArgumentException("arrayClass must be an array");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  859 */     byte b = 1;
/*  860 */     Class<?> clazz = paramClass.getComponentType();
/*  861 */     while (clazz.isArray()) {
/*  862 */       b++;
/*  863 */       clazz = clazz.getComponentType();
/*      */     } 
/*  865 */     String str = clazz.getName();
/*      */ 
/*      */ 
/*      */     
/*  869 */     if (!clazz.isPrimitive()) {
/*  870 */       throw new IllegalArgumentException("component type of the array must be a primitive type");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  877 */     SimpleType<?> simpleType = getPrimitiveOpenType(str);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  883 */       ArrayType<?> arrayType = new ArrayType(simpleType, true);
/*  884 */       if (b > 1)
/*  885 */         arrayType = new ArrayType(b - 1, arrayType); 
/*  886 */       return (ArrayType)arrayType;
/*  887 */     } catch (OpenDataException openDataException) {
/*  888 */       throw new IllegalArgumentException(openDataException);
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
/*      */   private Object readResolve() throws ObjectStreamException {
/*  931 */     if (this.primitiveArray) {
/*  932 */       return convertFromWrapperToPrimitiveTypes();
/*      */     }
/*  934 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> ArrayType<T> convertFromWrapperToPrimitiveTypes() {
/*  939 */     String str1 = getClassName();
/*  940 */     String str2 = getTypeName();
/*  941 */     String str3 = getDescription();
/*  942 */     for (Object[] arrayOfObject : PRIMITIVE_ARRAY_TYPES) {
/*  943 */       if (str1.indexOf((String)arrayOfObject[0]) != -1) {
/*  944 */         str1 = str1.replaceFirst("L" + arrayOfObject[0] + ";", (String)arrayOfObject[2]);
/*      */ 
/*      */         
/*  947 */         str2 = str2.replaceFirst("L" + arrayOfObject[0] + ";", (String)arrayOfObject[2]);
/*      */ 
/*      */         
/*  950 */         str3 = str3.replaceFirst((String)arrayOfObject[0], (String)arrayOfObject[1]);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  956 */     return new ArrayType(str1, str2, str3, this.dimension, this.elementType, this.primitiveArray);
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
/*      */   private Object writeReplace() throws ObjectStreamException {
/*  999 */     if (this.primitiveArray) {
/* 1000 */       return convertFromPrimitiveToWrapperTypes();
/*      */     }
/* 1002 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private <T> ArrayType<T> convertFromPrimitiveToWrapperTypes() {
/* 1007 */     String str1 = getClassName();
/* 1008 */     String str2 = getTypeName();
/* 1009 */     String str3 = getDescription();
/* 1010 */     for (Object[] arrayOfObject : PRIMITIVE_ARRAY_TYPES) {
/* 1011 */       if (str1.indexOf((String)arrayOfObject[2]) != -1) {
/* 1012 */         str1 = str1.replaceFirst((String)arrayOfObject[2], "L" + arrayOfObject[0] + ";");
/*      */ 
/*      */         
/* 1015 */         str2 = str2.replaceFirst((String)arrayOfObject[2], "L" + arrayOfObject[0] + ";");
/*      */ 
/*      */         
/* 1018 */         str3 = str3.replaceFirst((String)arrayOfObject[1], (String)arrayOfObject[0]);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 1024 */     return new ArrayType(str1, str2, str3, this.dimension, this.elementType, this.primitiveArray);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/openmbean/ArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */