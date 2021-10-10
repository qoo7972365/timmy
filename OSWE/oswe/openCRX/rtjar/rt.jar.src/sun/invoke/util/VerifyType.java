/*     */ package sun.invoke.util;
/*     */ 
/*     */ import java.lang.invoke.MethodType;
/*     */ import sun.invoke.empty.Empty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VerifyType
/*     */ {
/*     */   public static boolean isNullConversion(Class<?> paramClass1, Class<?> paramClass2, boolean paramBoolean) {
/*  68 */     if (paramClass1 == paramClass2) return true;
/*     */     
/*  70 */     if (!paramBoolean) {
/*  71 */       if (paramClass2.isInterface()) paramClass2 = Object.class; 
/*  72 */       if (paramClass1.isInterface()) paramClass1 = Object.class; 
/*  73 */       if (paramClass1 == paramClass2) return true; 
/*     */     } 
/*  75 */     if (isNullType(paramClass1)) return !paramClass2.isPrimitive(); 
/*  76 */     if (!paramClass1.isPrimitive()) return paramClass2.isAssignableFrom(paramClass1); 
/*  77 */     if (!paramClass2.isPrimitive()) return false;
/*     */     
/*  79 */     Wrapper wrapper1 = Wrapper.forPrimitiveType(paramClass1);
/*  80 */     if (paramClass2 == int.class) return wrapper1.isSubwordOrInt(); 
/*  81 */     Wrapper wrapper2 = Wrapper.forPrimitiveType(paramClass2);
/*  82 */     if (!wrapper1.isSubwordOrInt()) return false; 
/*  83 */     if (!wrapper2.isSubwordOrInt()) return false; 
/*  84 */     if (!wrapper2.isSigned() && wrapper1.isSigned()) return false; 
/*  85 */     return (wrapper2.bitWidth() > wrapper1.bitWidth());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNullReferenceConversion(Class<?> paramClass1, Class<?> paramClass2) {
/*  95 */     assert !paramClass2.isPrimitive();
/*  96 */     if (paramClass2.isInterface()) return true; 
/*  97 */     if (isNullType(paramClass1)) return true; 
/*  98 */     return paramClass2.isAssignableFrom(paramClass1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNullType(Class<?> paramClass) {
/* 108 */     if (paramClass == Void.class) return true;
/*     */     
/* 110 */     if (paramClass == Empty.class) return true; 
/* 111 */     return false;
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
/*     */   public static boolean isNullConversion(MethodType paramMethodType1, MethodType paramMethodType2, boolean paramBoolean) {
/* 123 */     if (paramMethodType1 == paramMethodType2) return true; 
/* 124 */     int i = paramMethodType1.parameterCount();
/* 125 */     if (i != paramMethodType2.parameterCount()) return false; 
/* 126 */     for (byte b = 0; b < i; b++) {
/* 127 */       if (!isNullConversion(paramMethodType1.parameterType(b), paramMethodType2.parameterType(b), paramBoolean))
/* 128 */         return false; 
/* 129 */     }  return isNullConversion(paramMethodType2.returnType(), paramMethodType1.returnType(), paramBoolean);
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
/*     */   public static int canPassUnchecked(Class<?> paramClass1, Class<?> paramClass2) {
/* 147 */     if (paramClass1 == paramClass2) {
/* 148 */       return 1;
/*     */     }
/* 150 */     if (paramClass2.isPrimitive()) {
/* 151 */       if (paramClass2 == void.class)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 156 */         return 1; } 
/* 157 */       if (paramClass1 == void.class)
/* 158 */         return 0; 
/* 159 */       if (!paramClass1.isPrimitive())
/*     */       {
/* 161 */         return 0; } 
/* 162 */       Wrapper wrapper1 = Wrapper.forPrimitiveType(paramClass1);
/* 163 */       Wrapper wrapper2 = Wrapper.forPrimitiveType(paramClass2);
/* 164 */       if (wrapper1.isSubwordOrInt() && wrapper2.isSubwordOrInt()) {
/* 165 */         if (wrapper1.bitWidth() >= wrapper2.bitWidth())
/* 166 */           return -1; 
/* 167 */         if (!wrapper2.isSigned() && wrapper1.isSigned())
/* 168 */           return -1; 
/* 169 */         return 1;
/*     */       } 
/* 171 */       if (paramClass1 == float.class || paramClass2 == float.class) {
/* 172 */         if (paramClass1 == double.class || paramClass2 == double.class) {
/* 173 */           return -1;
/*     */         }
/* 175 */         return 0;
/*     */       } 
/*     */       
/* 178 */       return 0;
/*     */     } 
/* 180 */     if (paramClass1.isPrimitive())
/*     */     {
/*     */       
/* 183 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     if (isNullReferenceConversion(paramClass1, paramClass2))
/*     */     {
/* 191 */       return 1;
/*     */     }
/* 193 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean isSpreadArgType(Class<?> paramClass) {
/* 197 */     return paramClass.isArray();
/*     */   }
/*     */   public static Class<?> spreadArgElementType(Class<?> paramClass, int paramInt) {
/* 200 */     return paramClass.getComponentType();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/invoke/util/VerifyType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */