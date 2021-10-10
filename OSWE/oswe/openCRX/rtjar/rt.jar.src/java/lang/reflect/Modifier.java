/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.reflect.ReflectAccess;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.reflect.ReflectionFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Modifier
/*     */ {
/*     */   public static final int PUBLIC = 1;
/*     */   public static final int PRIVATE = 2;
/*     */   public static final int PROTECTED = 4;
/*     */   public static final int STATIC = 8;
/*     */   public static final int FINAL = 16;
/*     */   public static final int SYNCHRONIZED = 32;
/*     */   public static final int VOLATILE = 64;
/*     */   public static final int TRANSIENT = 128;
/*     */   public static final int NATIVE = 256;
/*     */   public static final int INTERFACE = 512;
/*     */   public static final int ABSTRACT = 1024;
/*     */   public static final int STRICT = 2048;
/*     */   static final int BRIDGE = 64;
/*     */   static final int VARARGS = 128;
/*     */   static final int SYNTHETIC = 4096;
/*     */   static final int ANNOTATION = 8192;
/*     */   static final int ENUM = 16384;
/*     */   static final int MANDATED = 32768;
/*     */   private static final int CLASS_MODIFIERS = 3103;
/*     */   private static final int INTERFACE_MODIFIERS = 3087;
/*     */   private static final int CONSTRUCTOR_MODIFIERS = 7;
/*     */   private static final int METHOD_MODIFIERS = 3391;
/*     */   private static final int FIELD_MODIFIERS = 223;
/*     */   private static final int PARAMETER_MODIFIERS = 16;
/*     */   static final int ACCESS_MODIFIERS = 7;
/*     */   
/*     */   static {
/*  54 */     ReflectionFactory reflectionFactory = AccessController.<ReflectionFactory>doPrivileged((PrivilegedAction<ReflectionFactory>)new ReflectionFactory.GetReflectionFactoryAction());
/*     */     
/*  56 */     reflectionFactory.setLangReflectAccess(new ReflectAccess());
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
/*     */   public static boolean isPublic(int paramInt) {
/*  68 */     return ((paramInt & 0x1) != 0);
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
/*     */   public static boolean isPrivate(int paramInt) {
/*  80 */     return ((paramInt & 0x2) != 0);
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
/*     */   public static boolean isProtected(int paramInt) {
/*  92 */     return ((paramInt & 0x4) != 0);
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
/*     */   public static boolean isStatic(int paramInt) {
/* 104 */     return ((paramInt & 0x8) != 0);
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
/*     */   public static boolean isFinal(int paramInt) {
/* 116 */     return ((paramInt & 0x10) != 0);
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
/*     */   public static boolean isSynchronized(int paramInt) {
/* 128 */     return ((paramInt & 0x20) != 0);
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
/*     */   public static boolean isVolatile(int paramInt) {
/* 140 */     return ((paramInt & 0x40) != 0);
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
/*     */   public static boolean isTransient(int paramInt) {
/* 152 */     return ((paramInt & 0x80) != 0);
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
/*     */   public static boolean isNative(int paramInt) {
/* 164 */     return ((paramInt & 0x100) != 0);
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
/*     */   public static boolean isInterface(int paramInt) {
/* 176 */     return ((paramInt & 0x200) != 0);
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
/*     */   public static boolean isAbstract(int paramInt) {
/* 188 */     return ((paramInt & 0x400) != 0);
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
/*     */   public static boolean isStrict(int paramInt) {
/* 200 */     return ((paramInt & 0x800) != 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(int paramInt) {
/* 235 */     StringBuilder stringBuilder = new StringBuilder();
/*     */ 
/*     */     
/* 238 */     if ((paramInt & 0x1) != 0) stringBuilder.append("public "); 
/* 239 */     if ((paramInt & 0x4) != 0) stringBuilder.append("protected "); 
/* 240 */     if ((paramInt & 0x2) != 0) stringBuilder.append("private ");
/*     */ 
/*     */     
/* 243 */     if ((paramInt & 0x400) != 0) stringBuilder.append("abstract "); 
/* 244 */     if ((paramInt & 0x8) != 0) stringBuilder.append("static "); 
/* 245 */     if ((paramInt & 0x10) != 0) stringBuilder.append("final "); 
/* 246 */     if ((paramInt & 0x80) != 0) stringBuilder.append("transient "); 
/* 247 */     if ((paramInt & 0x40) != 0) stringBuilder.append("volatile "); 
/* 248 */     if ((paramInt & 0x20) != 0) stringBuilder.append("synchronized "); 
/* 249 */     if ((paramInt & 0x100) != 0) stringBuilder.append("native "); 
/* 250 */     if ((paramInt & 0x800) != 0) stringBuilder.append("strictfp "); 
/* 251 */     if ((paramInt & 0x200) != 0) stringBuilder.append("interface "); 
/*     */     int i;
/* 253 */     if ((i = stringBuilder.length()) > 0)
/* 254 */       return stringBuilder.toString().substring(0, i - 1); 
/* 255 */     return "";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isSynthetic(int paramInt) {
/* 346 */     return ((paramInt & 0x1000) != 0);
/*     */   }
/*     */   
/*     */   static boolean isMandated(int paramInt) {
/* 350 */     return ((paramInt & 0x8000) != 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int classModifiers() {
/* 429 */     return 3103;
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
/*     */   public static int interfaceModifiers() {
/* 442 */     return 3087;
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
/*     */   public static int constructorModifiers() {
/* 455 */     return 7;
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
/*     */   public static int methodModifiers() {
/* 468 */     return 3391;
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
/*     */   public static int fieldModifiers() {
/* 481 */     return 223;
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
/*     */   public static int parameterModifiers() {
/* 494 */     return 16;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/Modifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */