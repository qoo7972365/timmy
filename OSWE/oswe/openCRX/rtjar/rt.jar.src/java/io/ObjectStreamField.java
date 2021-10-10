/*     */ package java.io;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectStreamField
/*     */   implements Comparable<Object>
/*     */ {
/*     */   private final String name;
/*     */   private final String signature;
/*     */   private final Class<?> type;
/*     */   private final boolean unshared;
/*     */   private final Field field;
/*  57 */   private int offset = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectStreamField(String paramString, Class<?> paramClass) {
/*  67 */     this(paramString, paramClass, false);
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
/*     */   public ObjectStreamField(String paramString, Class<?> paramClass, boolean paramBoolean) {
/*  88 */     if (paramString == null) {
/*  89 */       throw new NullPointerException();
/*     */     }
/*  91 */     this.name = paramString;
/*  92 */     this.type = paramClass;
/*  93 */     this.unshared = paramBoolean;
/*  94 */     this.signature = getClassSignature(paramClass).intern();
/*  95 */     this.field = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ObjectStreamField(String paramString1, String paramString2, boolean paramBoolean) {
/* 103 */     if (paramString1 == null) {
/* 104 */       throw new NullPointerException();
/*     */     }
/* 106 */     this.name = paramString1;
/* 107 */     this.signature = paramString2.intern();
/* 108 */     this.unshared = paramBoolean;
/* 109 */     this.field = null;
/*     */     
/* 111 */     switch (paramString2.charAt(0)) { case 'Z':
/* 112 */         this.type = boolean.class; return;
/* 113 */       case 'B': this.type = byte.class; return;
/* 114 */       case 'C': this.type = char.class; return;
/* 115 */       case 'S': this.type = short.class; return;
/* 116 */       case 'I': this.type = int.class; return;
/* 117 */       case 'J': this.type = long.class; return;
/* 118 */       case 'F': this.type = float.class; return;
/* 119 */       case 'D': this.type = double.class; return;
/*     */       case 'L': case '[':
/* 121 */         this.type = Object.class; return; }
/* 122 */      throw new IllegalArgumentException("illegal signature");
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
/*     */   ObjectStreamField(Field paramField, boolean paramBoolean1, boolean paramBoolean2) {
/* 135 */     this.field = paramField;
/* 136 */     this.unshared = paramBoolean1;
/* 137 */     this.name = paramField.getName();
/* 138 */     Class<?> clazz = paramField.getType();
/* 139 */     this.type = (paramBoolean2 || clazz.isPrimitive()) ? clazz : Object.class;
/* 140 */     this.signature = getClassSignature(clazz).intern();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 150 */     return this.name;
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
/*     */   @CallerSensitive
/*     */   public Class<?> getType() {
/* 165 */     if (System.getSecurityManager() != null) {
/* 166 */       Class clazz = Reflection.getCallerClass();
/* 167 */       if (ReflectUtil.needsPackageAccessCheck(clazz.getClassLoader(), this.type.getClassLoader())) {
/* 168 */         ReflectUtil.checkPackageAccess(this.type);
/*     */       }
/*     */     } 
/* 171 */     return this.type;
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
/*     */   public char getTypeCode() {
/* 193 */     return this.signature.charAt(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeString() {
/* 203 */     return isPrimitive() ? null : this.signature;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOffset() {
/* 214 */     return this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setOffset(int paramInt) {
/* 225 */     this.offset = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPrimitive() {
/* 235 */     char c = this.signature.charAt(0);
/* 236 */     return (c != 'L' && c != '[');
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
/*     */   public boolean isUnshared() {
/* 248 */     return this.unshared;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Object paramObject) {
/* 259 */     ObjectStreamField objectStreamField = (ObjectStreamField)paramObject;
/* 260 */     boolean bool = isPrimitive();
/* 261 */     if (bool != objectStreamField.isPrimitive()) {
/* 262 */       return bool ? -1 : 1;
/*     */     }
/* 264 */     return this.name.compareTo(objectStreamField.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 271 */     return this.signature + ' ' + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Field getField() {
/* 279 */     return this.field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getSignature() {
/* 287 */     return this.signature;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getClassSignature(Class<?> paramClass) {
/* 294 */     StringBuilder stringBuilder = new StringBuilder();
/* 295 */     while (paramClass.isArray()) {
/* 296 */       stringBuilder.append('[');
/* 297 */       paramClass = paramClass.getComponentType();
/*     */     } 
/* 299 */     if (paramClass.isPrimitive()) {
/* 300 */       if (paramClass == int.class) {
/* 301 */         stringBuilder.append('I');
/* 302 */       } else if (paramClass == byte.class) {
/* 303 */         stringBuilder.append('B');
/* 304 */       } else if (paramClass == long.class) {
/* 305 */         stringBuilder.append('J');
/* 306 */       } else if (paramClass == float.class) {
/* 307 */         stringBuilder.append('F');
/* 308 */       } else if (paramClass == double.class) {
/* 309 */         stringBuilder.append('D');
/* 310 */       } else if (paramClass == short.class) {
/* 311 */         stringBuilder.append('S');
/* 312 */       } else if (paramClass == char.class) {
/* 313 */         stringBuilder.append('C');
/* 314 */       } else if (paramClass == boolean.class) {
/* 315 */         stringBuilder.append('Z');
/* 316 */       } else if (paramClass == void.class) {
/* 317 */         stringBuilder.append('V');
/*     */       } else {
/* 319 */         throw new InternalError();
/*     */       } 
/*     */     } else {
/* 322 */       stringBuilder.append('L' + paramClass.getName().replace('.', '/') + ';');
/*     */     } 
/* 324 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/ObjectStreamField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */