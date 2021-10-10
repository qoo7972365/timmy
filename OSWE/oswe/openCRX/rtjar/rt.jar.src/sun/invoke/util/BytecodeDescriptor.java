/*     */ package sun.invoke.util;
/*     */ 
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BytecodeDescriptor
/*     */ {
/*     */   public static List<Class<?>> parseMethod(String paramString, ClassLoader paramClassLoader) {
/*  41 */     return parseMethod(paramString, 0, paramString.length(), paramClassLoader);
/*     */   }
/*     */ 
/*     */   
/*     */   static List<Class<?>> parseMethod(String paramString, int paramInt1, int paramInt2, ClassLoader paramClassLoader) {
/*  46 */     if (paramClassLoader == null)
/*  47 */       paramClassLoader = ClassLoader.getSystemClassLoader(); 
/*  48 */     String str = paramString;
/*  49 */     int[] arrayOfInt = { paramInt1 };
/*  50 */     ArrayList<Class<?>> arrayList = new ArrayList();
/*  51 */     if (arrayOfInt[0] < paramInt2 && str.charAt(arrayOfInt[0]) == '(') {
/*  52 */       arrayOfInt[0] = arrayOfInt[0] + 1;
/*  53 */       while (arrayOfInt[0] < paramInt2 && str.charAt(arrayOfInt[0]) != ')') {
/*  54 */         Class<?> clazz1 = parseSig(str, arrayOfInt, paramInt2, paramClassLoader);
/*  55 */         if (clazz1 == null || clazz1 == void.class)
/*  56 */           parseError(str, "bad argument type"); 
/*  57 */         arrayList.add(clazz1);
/*     */       } 
/*  59 */       arrayOfInt[0] = arrayOfInt[0] + 1;
/*     */     } else {
/*  61 */       parseError(str, "not a method type");
/*     */     } 
/*  63 */     Class<?> clazz = parseSig(str, arrayOfInt, paramInt2, paramClassLoader);
/*  64 */     if (clazz == null || arrayOfInt[0] != paramInt2)
/*  65 */       parseError(str, "bad return type"); 
/*  66 */     arrayList.add(clazz);
/*  67 */     return arrayList;
/*     */   }
/*     */   
/*     */   private static void parseError(String paramString1, String paramString2) {
/*  71 */     throw new IllegalArgumentException("bad signature: " + paramString1 + ": " + paramString2);
/*     */   }
/*     */   
/*     */   private static Class<?> parseSig(String paramString, int[] paramArrayOfint, int paramInt, ClassLoader paramClassLoader) {
/*  75 */     if (paramArrayOfint[0] == paramInt) return null; 
/*  76 */     paramArrayOfint[0] = paramArrayOfint[0] + 1; char c = paramString.charAt(paramArrayOfint[0]);
/*  77 */     if (c == 'L') {
/*  78 */       int i = paramArrayOfint[0], j = paramString.indexOf(';', i);
/*  79 */       if (j < 0) return null; 
/*  80 */       paramArrayOfint[0] = j + 1;
/*  81 */       String str = paramString.substring(i, j).replace('/', '.');
/*     */       try {
/*  83 */         return paramClassLoader.loadClass(str);
/*  84 */       } catch (ClassNotFoundException classNotFoundException) {
/*  85 */         throw new TypeNotPresentException(str, classNotFoundException);
/*     */       } 
/*  87 */     }  if (c == '[') {
/*  88 */       Class<?> clazz = parseSig(paramString, paramArrayOfint, paramInt, paramClassLoader);
/*  89 */       if (clazz != null)
/*  90 */         clazz = Array.newInstance(clazz, 0).getClass(); 
/*  91 */       return clazz;
/*     */     } 
/*  93 */     return Wrapper.forBasicType(c).primitiveType();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String unparse(Class<?> paramClass) {
/*  98 */     StringBuilder stringBuilder = new StringBuilder();
/*  99 */     unparseSig(paramClass, stringBuilder);
/* 100 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public static String unparse(MethodType paramMethodType) {
/* 104 */     return unparseMethod(paramMethodType.returnType(), paramMethodType.parameterList());
/*     */   }
/*     */   
/*     */   public static String unparse(Object paramObject) {
/* 108 */     if (paramObject instanceof Class)
/* 109 */       return unparse((Class)paramObject); 
/* 110 */     if (paramObject instanceof MethodType)
/* 111 */       return unparse((MethodType)paramObject); 
/* 112 */     return (String)paramObject;
/*     */   }
/*     */   
/*     */   public static String unparseMethod(Class<?> paramClass, List<Class<?>> paramList) {
/* 116 */     StringBuilder stringBuilder = new StringBuilder();
/* 117 */     stringBuilder.append('(');
/* 118 */     for (Class<?> clazz : paramList)
/* 119 */       unparseSig(clazz, stringBuilder); 
/* 120 */     stringBuilder.append(')');
/* 121 */     unparseSig(paramClass, stringBuilder);
/* 122 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static void unparseSig(Class<?> paramClass, StringBuilder paramStringBuilder) {
/* 126 */     char c = Wrapper.forBasicType(paramClass).basicTypeChar();
/* 127 */     if (c != 'L') {
/* 128 */       paramStringBuilder.append(c);
/*     */     } else {
/* 130 */       boolean bool = !paramClass.isArray() ? true : false;
/* 131 */       if (bool) paramStringBuilder.append('L'); 
/* 132 */       paramStringBuilder.append(paramClass.getName().replace('.', '/'));
/* 133 */       if (bool) paramStringBuilder.append(';'); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/invoke/util/BytecodeDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */