/*     */ package sun.reflect;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sun.misc.VM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Reflection
/*     */ {
/*     */   private static volatile Map<Class<?>, String[]> fieldFilterMap;
/*     */   
/*     */   static {
/*  45 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  46 */     hashMap.put(Reflection.class, new String[] { "fieldFilterMap", "methodFilterMap" });
/*     */     
/*  48 */     hashMap.put(System.class, new String[] { "security" });
/*  49 */     hashMap.put(Class.class, new String[] { "classLoader" });
/*  50 */     fieldFilterMap = (Map)hashMap;
/*     */   }
/*  52 */   private static volatile Map<Class<?>, String[]> methodFilterMap = (Map)new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean quickCheckMemberAccess(Class<?> paramClass, int paramInt) {
/*  84 */     return Modifier.isPublic(getClassAccessFlags(paramClass) & paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ensureMemberAccess(Class<?> paramClass1, Class<?> paramClass2, Object paramObject, int paramInt) throws IllegalAccessException {
/*  93 */     if (paramClass1 == null || paramClass2 == null) {
/*  94 */       throw new InternalError();
/*     */     }
/*     */     
/*  97 */     if (!verifyMemberAccess(paramClass1, paramClass2, paramObject, paramInt)) {
/*  98 */       throw new IllegalAccessException("Class " + paramClass1.getName() + " can not access a member of class " + paramClass2
/*     */           
/* 100 */           .getName() + " with modifiers \"" + 
/*     */           
/* 102 */           Modifier.toString(paramInt) + "\"");
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
/*     */   public static boolean verifyMemberAccess(Class<?> paramClass1, Class<?> paramClass2, Object paramObject, int paramInt) {
/* 119 */     boolean bool1 = false;
/* 120 */     boolean bool = false;
/*     */     
/* 122 */     if (paramClass1 == paramClass2)
/*     */     {
/* 124 */       return true;
/*     */     }
/*     */     
/* 127 */     if (!Modifier.isPublic(getClassAccessFlags(paramClass2))) {
/* 128 */       bool = isSameClassPackage(paramClass1, paramClass2);
/* 129 */       bool1 = true;
/* 130 */       if (!bool) {
/* 131 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 137 */     if (Modifier.isPublic(paramInt)) {
/* 138 */       return true;
/*     */     }
/*     */     
/* 141 */     boolean bool2 = false;
/*     */     
/* 143 */     if (Modifier.isProtected(paramInt))
/*     */     {
/* 145 */       if (isSubclassOf(paramClass1, paramClass2)) {
/* 146 */         bool2 = true;
/*     */       }
/*     */     }
/*     */     
/* 150 */     if (!bool2 && !Modifier.isPrivate(paramInt)) {
/* 151 */       if (!bool1) {
/* 152 */         bool = isSameClassPackage(paramClass1, paramClass2);
/*     */         
/* 154 */         bool1 = true;
/*     */       } 
/*     */       
/* 157 */       if (bool) {
/* 158 */         bool2 = true;
/*     */       }
/*     */     } 
/*     */     
/* 162 */     if (!bool2) {
/* 163 */       return false;
/*     */     }
/*     */     
/* 166 */     if (Modifier.isProtected(paramInt)) {
/*     */       
/* 168 */       Class<?> clazz = (paramObject == null) ? paramClass2 : paramObject.getClass();
/* 169 */       if (clazz != paramClass1) {
/* 170 */         if (!bool1) {
/* 171 */           bool = isSameClassPackage(paramClass1, paramClass2);
/* 172 */           bool1 = true;
/*     */         } 
/* 174 */         if (!bool && 
/* 175 */           !isSubclassOf(clazz, paramClass1)) {
/* 176 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 182 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean isSameClassPackage(Class<?> paramClass1, Class<?> paramClass2) {
/* 186 */     return isSameClassPackage(paramClass1.getClassLoader(), paramClass1.getName(), paramClass2
/* 187 */         .getClassLoader(), paramClass2.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSameClassPackage(ClassLoader paramClassLoader1, String paramString1, ClassLoader paramClassLoader2, String paramString2) {
/* 195 */     if (paramClassLoader1 != paramClassLoader2) {
/* 196 */       return false;
/*     */     }
/* 198 */     int i = paramString1.lastIndexOf('.');
/* 199 */     int j = paramString2.lastIndexOf('.');
/* 200 */     if (i == -1 || j == -1)
/*     */     {
/*     */       
/* 203 */       return (i == j);
/*     */     }
/* 205 */     byte b1 = 0;
/* 206 */     byte b2 = 0;
/*     */ 
/*     */     
/* 209 */     if (paramString1.charAt(b1) == '[')
/*     */       while (true) {
/* 211 */         b1++;
/* 212 */         if (paramString1.charAt(b1) != '[') {
/* 213 */           if (paramString1.charAt(b1) != 'L')
/*     */           {
/* 215 */             throw new InternalError("Illegal class name " + paramString1); }  break;
/*     */         } 
/*     */       }  
/* 218 */     if (paramString2.charAt(b2) == '[')
/*     */       while (true) {
/* 220 */         b2++;
/* 221 */         if (paramString2.charAt(b2) != '[') {
/* 222 */           if (paramString2.charAt(b2) != 'L')
/*     */           {
/* 224 */             throw new InternalError("Illegal class name " + paramString2);
/*     */           }
/*     */           break;
/*     */         } 
/*     */       }  
/* 229 */     int k = i - b1;
/* 230 */     int m = j - b2;
/*     */     
/* 232 */     if (k != m) {
/* 233 */       return false;
/*     */     }
/* 235 */     return paramString1.regionMatches(false, b1, paramString2, b2, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isSubclassOf(Class<?> paramClass1, Class<?> paramClass2) {
/* 243 */     while (paramClass1 != null) {
/* 244 */       if (paramClass1 == paramClass2) {
/* 245 */         return true;
/*     */       }
/* 247 */       paramClass1 = paramClass1.getSuperclass();
/*     */     } 
/* 249 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void registerFieldsToFilter(Class<?> paramClass, String... paramVarArgs) {
/* 256 */     fieldFilterMap = registerFilter(fieldFilterMap, paramClass, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void registerMethodsToFilter(Class<?> paramClass, String... paramVarArgs) {
/* 263 */     methodFilterMap = registerFilter(methodFilterMap, paramClass, paramVarArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<Class<?>, String[]> registerFilter(Map<Class<?>, String[]> paramMap, Class<?> paramClass, String... paramVarArgs) {
/* 268 */     if (paramMap.get(paramClass) != null) {
/* 269 */       throw new IllegalArgumentException("Filter already registered: " + paramClass);
/*     */     }
/*     */     
/* 272 */     paramMap = (Map)new HashMap<>((Map)paramMap);
/* 273 */     paramMap.put(paramClass, paramVarArgs);
/* 274 */     return paramMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Field[] filterFields(Class<?> paramClass, Field[] paramArrayOfField) {
/* 279 */     if (fieldFilterMap == null)
/*     */     {
/* 281 */       return paramArrayOfField;
/*     */     }
/* 283 */     return (Field[])filter((Member[])paramArrayOfField, fieldFilterMap.get(paramClass));
/*     */   }
/*     */   
/*     */   public static Method[] filterMethods(Class<?> paramClass, Method[] paramArrayOfMethod) {
/* 287 */     if (methodFilterMap == null)
/*     */     {
/* 289 */       return paramArrayOfMethod;
/*     */     }
/* 291 */     return (Method[])filter((Member[])paramArrayOfMethod, methodFilterMap.get(paramClass));
/*     */   }
/*     */   
/*     */   private static Member[] filter(Member[] paramArrayOfMember, String[] paramArrayOfString) {
/* 295 */     if (paramArrayOfString == null || paramArrayOfMember.length == 0) {
/* 296 */       return paramArrayOfMember;
/*     */     }
/* 298 */     byte b1 = 0;
/* 299 */     for (Member member : paramArrayOfMember) {
/* 300 */       boolean bool = false;
/* 301 */       for (String str : paramArrayOfString) {
/* 302 */         if (member.getName() == str) {
/* 303 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 307 */       if (!bool) {
/* 308 */         b1++;
/*     */       }
/*     */     } 
/*     */     
/* 312 */     Member[] arrayOfMember = (Member[])Array.newInstance(paramArrayOfMember[0].getClass(), b1);
/* 313 */     byte b2 = 0;
/* 314 */     for (Member member : paramArrayOfMember) {
/* 315 */       boolean bool = false;
/* 316 */       for (String str : paramArrayOfString) {
/* 317 */         if (member.getName() == str) {
/* 318 */           bool = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 322 */       if (!bool) {
/* 323 */         arrayOfMember[b2++] = member;
/*     */       }
/*     */     } 
/* 326 */     return arrayOfMember;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCallerSensitive(Method paramMethod) {
/* 334 */     ClassLoader classLoader = paramMethod.getDeclaringClass().getClassLoader();
/* 335 */     if (VM.isSystemDomainLoader(classLoader) || isExtClassLoader(classLoader)) {
/* 336 */       return paramMethod.isAnnotationPresent((Class)CallerSensitive.class);
/*     */     }
/* 338 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isExtClassLoader(ClassLoader paramClassLoader) {
/* 342 */     ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 343 */     while (classLoader != null) {
/* 344 */       if (classLoader.getParent() == null && classLoader == paramClassLoader) {
/* 345 */         return true;
/*     */       }
/* 347 */       classLoader = classLoader.getParent();
/*     */     } 
/* 349 */     return false;
/*     */   }
/*     */   
/*     */   @CallerSensitive
/*     */   public static native Class<?> getCallerClass();
/*     */   
/*     */   @Deprecated
/*     */   public static native Class<?> getCallerClass(int paramInt);
/*     */   
/*     */   public static native int getClassAccessFlags(Class<?> paramClass);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/Reflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */