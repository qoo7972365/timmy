/*     */ package sun.invoke.util;
/*     */ 
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.reflect.Reflection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VerifyAccess
/*     */ {
/*     */   private static final int PACKAGE_ONLY = 0;
/*     */   private static final int PACKAGE_ALLOWED = 8;
/*     */   private static final int PROTECTED_OR_PACKAGE_ALLOWED = 12;
/*     */   private static final int ALL_ACCESS_MODES = 7;
/*     */   private static final boolean ALLOW_NESTMATE_ACCESS = false;
/*     */   
/*     */   public static boolean isMemberAccessible(Class<?> paramClass1, Class<?> paramClass2, int paramInt1, Class<?> paramClass3, int paramInt2) {
/*  90 */     if (paramInt2 == 0) return false; 
/*  91 */     assert (paramInt2 & 0x1) != 0 && (paramInt2 & 0xFFFFFFF0) == 0;
/*     */ 
/*     */     
/*  94 */     if (!isClassAccessible(paramClass1, paramClass3, paramInt2)) {
/*  95 */       return false;
/*     */     }
/*     */     
/*  98 */     if (paramClass2 == paramClass3 && (paramInt2 & 0x2) != 0)
/*     */     {
/* 100 */       return true; } 
/* 101 */     switch (paramInt1 & 0x7) {
/*     */       case 1:
/* 103 */         return true;
/*     */       case 4:
/* 105 */         assert !paramClass2.isInterface();
/* 106 */         if ((paramInt2 & 0xC) != 0 && 
/* 107 */           isSamePackage(paramClass2, paramClass3))
/* 108 */           return true; 
/* 109 */         if ((paramInt2 & 0x4) == 0) {
/* 110 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 114 */         if ((paramInt1 & 0x8) != 0 && 
/* 115 */           !isRelatedClass(paramClass1, paramClass3))
/* 116 */           return false; 
/* 117 */         if ((paramInt2 & 0x4) != 0 && 
/* 118 */           isSubClass(paramClass3, paramClass2))
/* 119 */           return true; 
/* 120 */         return false;
/*     */       case 0:
/* 122 */         assert !paramClass2.isInterface();
/* 123 */         return ((paramInt2 & 0x8) != 0 && 
/* 124 */           isSamePackage(paramClass2, paramClass3));
/*     */       
/*     */       case 2:
/* 127 */         return false;
/*     */     } 
/*     */ 
/*     */     
/* 131 */     throw new IllegalArgumentException("bad modifiers: " + Modifier.toString(paramInt1));
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isRelatedClass(Class<?> paramClass1, Class<?> paramClass2) {
/* 136 */     return (paramClass1 == paramClass2 || 
/* 137 */       isSubClass(paramClass1, paramClass2) || 
/* 138 */       isSubClass(paramClass2, paramClass1));
/*     */   }
/*     */   
/*     */   static boolean isSubClass(Class<?> paramClass1, Class<?> paramClass2) {
/* 142 */     return (paramClass2.isAssignableFrom(paramClass1) && 
/* 143 */       !paramClass1.isInterface());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getClassModifiers(Class<?> paramClass) {
/* 151 */     if (paramClass.isArray() || paramClass.isPrimitive())
/* 152 */       return paramClass.getModifiers(); 
/* 153 */     return Reflection.getClassAccessFlags(paramClass);
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
/*     */   public static boolean isClassAccessible(Class<?> paramClass1, Class<?> paramClass2, int paramInt) {
/* 169 */     if (paramInt == 0) return false; 
/* 170 */     assert (paramInt & 0x1) != 0 && (paramInt & 0xFFFFFFF0) == 0;
/*     */     
/* 172 */     int i = getClassModifiers(paramClass1);
/* 173 */     if (Modifier.isPublic(i))
/* 174 */       return true; 
/* 175 */     if ((paramInt & 0x8) != 0 && 
/* 176 */       isSamePackage(paramClass2, paramClass1))
/* 177 */       return true; 
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTypeVisible(Class<?> paramClass1, Class<?> paramClass2) {
/* 188 */     if (paramClass1 == paramClass2) {
/* 189 */       return true;
/*     */     }
/* 191 */     for (; paramClass1.isArray(); paramClass1 = paramClass1.getComponentType());
/* 192 */     if (paramClass1.isPrimitive() || paramClass1 == Object.class) {
/* 193 */       return true;
/*     */     }
/* 195 */     ClassLoader classLoader1 = paramClass1.getClassLoader();
/* 196 */     final ClassLoader refcLoader = paramClass2.getClassLoader();
/* 197 */     if (classLoader1 == classLoader2) {
/* 198 */       return true;
/*     */     }
/* 200 */     if (classLoader2 == null && classLoader1 != null) {
/* 201 */       return false;
/*     */     }
/* 203 */     if (classLoader1 == null && paramClass1.getName().startsWith("java."))
/*     */     {
/*     */ 
/*     */       
/* 207 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     final String name = paramClass1.getName();
/* 237 */     Class<?> clazz = AccessController.<Class<?>>doPrivileged(new PrivilegedAction<Class>()
/*     */         {
/*     */           public Class<?> run() {
/*     */             try {
/* 241 */               return Class.forName(name, false, refcLoader);
/* 242 */             } catch (ClassNotFoundException|LinkageError classNotFoundException) {
/* 243 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/* 247 */     return (paramClass1 == clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTypeVisible(MethodType paramMethodType, Class<?> paramClass) {
/*     */     byte b;
/*     */     int i;
/* 257 */     for (b = -1, i = paramMethodType.parameterCount(); b < i; b++) {
/* 258 */       Class<?> clazz = (b < 0) ? paramMethodType.returnType() : paramMethodType.parameterType(b);
/* 259 */       if (!isTypeVisible(clazz, paramClass))
/* 260 */         return false; 
/*     */     } 
/* 262 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSamePackage(Class<?> paramClass1, Class<?> paramClass2) {
/* 272 */     assert !paramClass1.isArray() && !paramClass2.isArray();
/* 273 */     if (paramClass1 == paramClass2)
/* 274 */       return true; 
/* 275 */     if (paramClass1.getClassLoader() != paramClass2.getClassLoader())
/* 276 */       return false; 
/* 277 */     String str1 = paramClass1.getName(), str2 = paramClass2.getName();
/* 278 */     int i = str1.lastIndexOf('.');
/* 279 */     if (i != str2.lastIndexOf('.'))
/* 280 */       return false; 
/* 281 */     for (byte b = 0; b < i; b++) {
/* 282 */       if (str1.charAt(b) != str2.charAt(b))
/* 283 */         return false; 
/*     */     } 
/* 285 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPackageName(Class<?> paramClass) {
/* 291 */     assert !paramClass.isArray();
/* 292 */     String str = paramClass.getName();
/* 293 */     int i = str.lastIndexOf('.');
/* 294 */     if (i < 0) return ""; 
/* 295 */     return str.substring(0, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSamePackageMember(Class<?> paramClass1, Class<?> paramClass2) {
/* 306 */     if (paramClass1 == paramClass2)
/* 307 */       return true; 
/* 308 */     if (!isSamePackage(paramClass1, paramClass2))
/* 309 */       return false; 
/* 310 */     if (getOutermostEnclosingClass(paramClass1) != getOutermostEnclosingClass(paramClass2))
/* 311 */       return false; 
/* 312 */     return true;
/*     */   }
/*     */   
/*     */   private static Class<?> getOutermostEnclosingClass(Class<?> paramClass) {
/* 316 */     Class<?> clazz1 = paramClass;
/* 317 */     for (Class<?> clazz2 = paramClass; (clazz2 = clazz2.getEnclosingClass()) != null;)
/* 318 */       clazz1 = clazz2; 
/* 319 */     return clazz1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean loadersAreRelated(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2, boolean paramBoolean) {
/* 324 */     if (paramClassLoader1 == paramClassLoader2 || paramClassLoader1 == null || (paramClassLoader2 == null && !paramBoolean))
/*     */     {
/* 326 */       return true;
/*     */     }
/* 328 */     ClassLoader classLoader = paramClassLoader2;
/* 329 */     for (; classLoader != null; classLoader = classLoader.getParent()) {
/* 330 */       if (classLoader == paramClassLoader1) return true; 
/*     */     } 
/* 332 */     if (paramBoolean) return false;
/*     */     
/* 334 */     classLoader = paramClassLoader1;
/* 335 */     for (; classLoader != null; classLoader = classLoader.getParent()) {
/* 336 */       if (classLoader == paramClassLoader2) return true; 
/*     */     } 
/* 338 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean classLoaderIsAncestor(Class<?> paramClass1, Class<?> paramClass2) {
/* 349 */     return loadersAreRelated(paramClass1.getClassLoader(), paramClass2.getClassLoader(), true);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/invoke/util/VerifyAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */