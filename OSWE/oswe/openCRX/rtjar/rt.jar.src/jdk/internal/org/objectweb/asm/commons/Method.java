/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Method
/*     */ {
/*     */   private final String name;
/*     */   private final String desc;
/*  91 */   private static final Map<String, String> DESCRIPTORS = new HashMap<>(); static {
/*  92 */     DESCRIPTORS.put("void", "V");
/*  93 */     DESCRIPTORS.put("byte", "B");
/*  94 */     DESCRIPTORS.put("char", "C");
/*  95 */     DESCRIPTORS.put("double", "D");
/*  96 */     DESCRIPTORS.put("float", "F");
/*  97 */     DESCRIPTORS.put("int", "I");
/*  98 */     DESCRIPTORS.put("long", "J");
/*  99 */     DESCRIPTORS.put("short", "S");
/* 100 */     DESCRIPTORS.put("boolean", "Z");
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
/*     */   public Method(String paramString1, String paramString2) {
/* 112 */     this.name = paramString1;
/* 113 */     this.desc = paramString2;
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
/*     */   public Method(String paramString, Type paramType, Type[] paramArrayOfType) {
/* 128 */     this(paramString, Type.getMethodDescriptor(paramType, paramArrayOfType));
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
/*     */   public static Method getMethod(Method paramMethod) {
/* 140 */     return new Method(paramMethod.getName(), Type.getMethodDescriptor(paramMethod));
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
/*     */   public static Method getMethod(Constructor<?> paramConstructor) {
/* 152 */     return new Method("<init>", Type.getConstructorDescriptor(paramConstructor));
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
/*     */   public static Method getMethod(String paramString) throws IllegalArgumentException {
/* 173 */     return getMethod(paramString, false);
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
/*     */   public static Method getMethod(String paramString, boolean paramBoolean) throws IllegalArgumentException {
/* 200 */     int i = paramString.indexOf(' ');
/* 201 */     int j = paramString.indexOf('(', i) + 1;
/* 202 */     int k = paramString.indexOf(')', j);
/* 203 */     if (i == -1 || j == -1 || k == -1) {
/* 204 */       throw new IllegalArgumentException();
/*     */     }
/* 206 */     String str1 = paramString.substring(0, i);
/* 207 */     String str2 = paramString.substring(i + 1, j - 1).trim();
/* 208 */     StringBuilder stringBuilder = new StringBuilder();
/* 209 */     stringBuilder.append('(');
/*     */     
/*     */     while (true) {
/*     */       String str;
/* 213 */       int m = paramString.indexOf(',', j);
/* 214 */       if (m == -1) {
/* 215 */         str = map(paramString.substring(j, k).trim(), paramBoolean);
/*     */       } else {
/* 217 */         str = map(paramString.substring(j, m).trim(), paramBoolean);
/* 218 */         j = m + 1;
/*     */       } 
/* 220 */       stringBuilder.append(str);
/* 221 */       if (m == -1) {
/* 222 */         stringBuilder.append(')');
/* 223 */         stringBuilder.append(map(str1, paramBoolean));
/* 224 */         return new Method(str2, stringBuilder.toString());
/*     */       } 
/*     */     } 
/*     */   } private static String map(String paramString, boolean paramBoolean) {
/* 228 */     if ("".equals(paramString)) {
/* 229 */       return paramString;
/*     */     }
/*     */     
/* 232 */     StringBuilder stringBuilder = new StringBuilder();
/* 233 */     int i = 0;
/* 234 */     while ((i = paramString.indexOf("[]", i) + 1) > 0) {
/* 235 */       stringBuilder.append('[');
/*     */     }
/*     */     
/* 238 */     String str1 = paramString.substring(0, paramString.length() - stringBuilder.length() * 2);
/* 239 */     String str2 = DESCRIPTORS.get(str1);
/* 240 */     if (str2 != null) {
/* 241 */       stringBuilder.append(str2);
/*     */     } else {
/* 243 */       stringBuilder.append('L');
/* 244 */       if (str1.indexOf('.') < 0) {
/* 245 */         if (!paramBoolean) {
/* 246 */           stringBuilder.append("java/lang/");
/*     */         }
/* 248 */         stringBuilder.append(str1);
/*     */       } else {
/* 250 */         stringBuilder.append(str1.replace('.', '/'));
/*     */       } 
/* 252 */       stringBuilder.append(';');
/*     */     } 
/* 254 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 263 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescriptor() {
/* 272 */     return this.desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getReturnType() {
/* 281 */     return Type.getReturnType(this.desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type[] getArgumentTypes() {
/* 290 */     return Type.getArgumentTypes(this.desc);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 295 */     return this.name + this.desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 300 */     if (!(paramObject instanceof Method)) {
/* 301 */       return false;
/*     */     }
/* 303 */     Method method = (Method)paramObject;
/* 304 */     return (this.name.equals(method.name) && this.desc.equals(method.desc));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 309 */     return this.name.hashCode() ^ this.desc.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/Method.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */