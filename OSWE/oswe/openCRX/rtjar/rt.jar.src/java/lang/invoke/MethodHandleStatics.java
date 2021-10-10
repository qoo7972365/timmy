/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.MemberName;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleStatics;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MethodHandleStatics
/*     */ {
/*  42 */   static final Unsafe UNSAFE = Unsafe.getUnsafe();
/*     */   
/*     */   static final boolean DEBUG_METHOD_HANDLE_NAMES;
/*     */   static final boolean DUMP_CLASS_FILES;
/*     */   static final boolean TRACE_INTERPRETER;
/*     */   static final boolean TRACE_METHOD_LINKAGE;
/*     */   static final int COMPILE_THRESHOLD;
/*     */   static final int DONT_INLINE_THRESHOLD;
/*     */   static final int PROFILE_LEVEL;
/*     */   static final boolean PROFILE_GWT;
/*     */   static final int CUSTOMIZE_THRESHOLD;
/*     */   
/*     */   static {
/*  55 */     final Object[] values = new Object[9];
/*  56 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*  58 */             values[0] = Boolean.valueOf(Boolean.getBoolean("java.lang.invoke.MethodHandle.DEBUG_NAMES"));
/*  59 */             values[1] = Boolean.valueOf(Boolean.getBoolean("java.lang.invoke.MethodHandle.DUMP_CLASS_FILES"));
/*  60 */             values[2] = Boolean.valueOf(Boolean.getBoolean("java.lang.invoke.MethodHandle.TRACE_INTERPRETER"));
/*  61 */             values[3] = Boolean.valueOf(Boolean.getBoolean("java.lang.invoke.MethodHandle.TRACE_METHOD_LINKAGE"));
/*  62 */             values[4] = Integer.getInteger("java.lang.invoke.MethodHandle.COMPILE_THRESHOLD", 0);
/*  63 */             values[5] = Integer.getInteger("java.lang.invoke.MethodHandle.DONT_INLINE_THRESHOLD", 30);
/*  64 */             values[6] = Integer.getInteger("java.lang.invoke.MethodHandle.PROFILE_LEVEL", 0);
/*  65 */             values[7] = Boolean.valueOf(Boolean.parseBoolean(System.getProperty("java.lang.invoke.MethodHandle.PROFILE_GWT", "true")));
/*  66 */             values[8] = Integer.getInteger("java.lang.invoke.MethodHandle.CUSTOMIZE_THRESHOLD", 127);
/*  67 */             return null;
/*     */           }
/*     */         });
/*  70 */     DEBUG_METHOD_HANDLE_NAMES = ((Boolean)arrayOfObject[0]).booleanValue();
/*  71 */     DUMP_CLASS_FILES = ((Boolean)arrayOfObject[1]).booleanValue();
/*  72 */     TRACE_INTERPRETER = ((Boolean)arrayOfObject[2]).booleanValue();
/*  73 */     TRACE_METHOD_LINKAGE = ((Boolean)arrayOfObject[3]).booleanValue();
/*  74 */     COMPILE_THRESHOLD = ((Integer)arrayOfObject[4]).intValue();
/*  75 */     DONT_INLINE_THRESHOLD = ((Integer)arrayOfObject[5]).intValue();
/*  76 */     PROFILE_LEVEL = ((Integer)arrayOfObject[6]).intValue();
/*  77 */     PROFILE_GWT = ((Boolean)arrayOfObject[7]).booleanValue();
/*  78 */     CUSTOMIZE_THRESHOLD = ((Integer)arrayOfObject[8]).intValue();
/*     */     
/*  80 */     if (CUSTOMIZE_THRESHOLD < -1 || CUSTOMIZE_THRESHOLD > 127) {
/*  81 */       throw newInternalError("CUSTOMIZE_THRESHOLD should be in [-1...127] range");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean debugEnabled() {
/*  89 */     return DEBUG_METHOD_HANDLE_NAMES | DUMP_CLASS_FILES | TRACE_INTERPRETER | TRACE_METHOD_LINKAGE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getNameString(MethodHandle paramMethodHandle, MethodType paramMethodType) {
/*  96 */     if (paramMethodType == null)
/*  97 */       paramMethodType = paramMethodHandle.type(); 
/*  98 */     MemberName memberName = null;
/*  99 */     if (paramMethodHandle != null)
/* 100 */       memberName = paramMethodHandle.internalMemberName(); 
/* 101 */     if (memberName == null)
/* 102 */       return "invoke" + paramMethodType; 
/* 103 */     return memberName.getName() + paramMethodType;
/*     */   }
/*     */   
/*     */   static String getNameString(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2) {
/* 107 */     return getNameString(paramMethodHandle1, (paramMethodHandle2 == null) ? (MethodType)null : paramMethodHandle2.type());
/*     */   }
/*     */   
/*     */   static String getNameString(MethodHandle paramMethodHandle) {
/* 111 */     return getNameString(paramMethodHandle, (MethodType)null);
/*     */   }
/*     */   
/*     */   static String addTypeString(Object paramObject, MethodHandle paramMethodHandle) {
/* 115 */     String str = String.valueOf(paramObject);
/* 116 */     if (paramMethodHandle == null) return str; 
/* 117 */     int i = str.indexOf('(');
/* 118 */     if (i >= 0) str = str.substring(0, i); 
/* 119 */     return str + paramMethodHandle.type();
/*     */   }
/*     */ 
/*     */   
/*     */   static InternalError newInternalError(String paramString) {
/* 124 */     return new InternalError(paramString);
/*     */   }
/*     */   static InternalError newInternalError(String paramString, Throwable paramThrowable) {
/* 127 */     return new InternalError(paramString, paramThrowable);
/*     */   }
/*     */   static InternalError newInternalError(Throwable paramThrowable) {
/* 130 */     return new InternalError(paramThrowable);
/*     */   }
/*     */   static RuntimeException newIllegalStateException(String paramString) {
/* 133 */     return new IllegalStateException(paramString);
/*     */   }
/*     */   static RuntimeException newIllegalStateException(String paramString, Object paramObject) {
/* 136 */     return new IllegalStateException(message(paramString, paramObject));
/*     */   }
/*     */   static RuntimeException newIllegalArgumentException(String paramString) {
/* 139 */     return new IllegalArgumentException(paramString);
/*     */   }
/*     */   static RuntimeException newIllegalArgumentException(String paramString, Object paramObject) {
/* 142 */     return new IllegalArgumentException(message(paramString, paramObject));
/*     */   }
/*     */   static RuntimeException newIllegalArgumentException(String paramString, Object paramObject1, Object paramObject2) {
/* 145 */     return new IllegalArgumentException(message(paramString, paramObject1, paramObject2));
/*     */   }
/*     */   
/*     */   static Error uncaughtException(Throwable paramThrowable) {
/* 149 */     if (paramThrowable instanceof Error) throw (Error)paramThrowable; 
/* 150 */     if (paramThrowable instanceof RuntimeException) throw (RuntimeException)paramThrowable; 
/* 151 */     throw newInternalError("uncaught exception", paramThrowable);
/*     */   }
/*     */   static Error NYI() {
/* 154 */     throw new AssertionError("NYI");
/*     */   }
/*     */   private static String message(String paramString, Object paramObject) {
/* 157 */     if (paramObject != null) paramString = paramString + ": " + paramObject; 
/* 158 */     return paramString;
/*     */   }
/*     */   private static String message(String paramString, Object paramObject1, Object paramObject2) {
/* 161 */     if (paramObject1 != null || paramObject2 != null) paramString = paramString + ": " + paramObject1 + ", " + paramObject2; 
/* 162 */     return paramString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/MethodHandleStatics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */