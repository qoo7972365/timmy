/*     */ package java.beans;
/*     */ 
/*     */ import com.sun.beans.finder.ClassFinder;
/*     */ import com.sun.beans.finder.ConstructorFinder;
/*     */ import com.sun.beans.finder.MethodFinder;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Statement
/*     */ {
/*  60 */   private static Object[] emptyArray = new Object[0];
/*     */   
/*  62 */   static ExceptionListener defaultExceptionListener = new ExceptionListener() {
/*     */       public void exceptionThrown(Exception param1Exception) {
/*  64 */         System.err.println(param1Exception);
/*     */         
/*  66 */         System.err.println("Continuing ...");
/*     */       }
/*     */     };
/*     */   
/*  70 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object target;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String methodName;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object[] arguments;
/*     */ 
/*     */ 
/*     */   
/*     */   ClassLoader loader;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ConstructorProperties({"target", "methodName", "arguments"})
/*     */   public Statement(Object paramObject, String paramString, Object[] paramArrayOfObject) {
/*  93 */     this.target = paramObject;
/*  94 */     this.methodName = paramString;
/*  95 */     this.arguments = (paramArrayOfObject == null) ? emptyArray : (Object[])paramArrayOfObject.clone();
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
/*     */   public Object getTarget() {
/* 107 */     return this.target;
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
/*     */   public String getMethodName() {
/* 119 */     return this.methodName;
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
/*     */   public Object[] getArguments() {
/* 131 */     return (Object[])this.arguments.clone();
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
/*     */   public void execute() throws Exception {
/* 173 */     invoke();
/*     */   }
/*     */   
/*     */   Object invoke() throws Exception {
/* 177 */     AccessControlContext accessControlContext = this.acc;
/* 178 */     if (accessControlContext == null && System.getSecurityManager() != null) {
/* 179 */       throw new SecurityException("AccessControlContext is not set");
/*     */     }
/*     */     try {
/* 182 */       return AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */           {
/*     */             public Object run() throws Exception {
/* 185 */               return Statement.this.invokeInternal();
/*     */             }
/*     */           }, 
/*     */ 
/*     */           
/*     */           accessControlContext);
/* 191 */     } catch (PrivilegedActionException privilegedActionException) {
/* 192 */       throw privilegedActionException.getException();
/*     */     } 
/*     */   }
/*     */   private Object invokeInternal() throws Exception {
/*     */     Method method;
/* 197 */     Object object = getTarget();
/* 198 */     String str = getMethodName();
/*     */     
/* 200 */     if (object == null || str == null) {
/* 201 */       throw new NullPointerException(((object == null) ? "target" : "methodName") + " should not be null");
/*     */     }
/*     */ 
/*     */     
/* 205 */     Object[] arrayOfObject = getArguments();
/* 206 */     if (arrayOfObject == null) {
/* 207 */       arrayOfObject = emptyArray;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 212 */     if (object == Class.class && str.equals("forName")) {
/* 213 */       return ClassFinder.resolveClass((String)arrayOfObject[0], this.loader);
/*     */     }
/* 215 */     Class[] arrayOfClass = new Class[arrayOfObject.length];
/* 216 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/* 217 */       arrayOfClass[b] = (arrayOfObject[b] == null) ? null : arrayOfObject[b].getClass();
/*     */     }
/*     */     
/* 220 */     Constructor<?> constructor = null;
/* 221 */     if (object instanceof Class) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 232 */       if (str.equals("new")) {
/* 233 */         str = "newInstance";
/*     */       }
/*     */       
/* 236 */       if (str.equals("newInstance") && ((Class)object).isArray()) {
/* 237 */         Object object1 = Array.newInstance(((Class)object).getComponentType(), arrayOfObject.length);
/* 238 */         for (byte b1 = 0; b1 < arrayOfObject.length; b1++) {
/* 239 */           Array.set(object1, b1, arrayOfObject[b1]);
/*     */         }
/* 241 */         return object1;
/*     */       } 
/* 243 */       if (str.equals("newInstance") && arrayOfObject.length != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 249 */         if (object == Character.class && arrayOfObject.length == 1 && arrayOfClass[0] == String.class)
/*     */         {
/* 251 */           return new Character(((String)arrayOfObject[0]).charAt(0));
/*     */         }
/*     */         try {
/* 254 */           constructor = ConstructorFinder.findConstructor((Class)object, arrayOfClass);
/*     */         }
/* 256 */         catch (NoSuchMethodException noSuchMethodException) {
/* 257 */           constructor = null;
/*     */         } 
/*     */       } 
/* 260 */       if (constructor == null && object != Class.class) {
/* 261 */         method = getMethod((Class)object, str, arrayOfClass);
/*     */       }
/* 263 */       if (method == null) {
/* 264 */         method = getMethod(Class.class, str, arrayOfClass);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 276 */       if (object.getClass().isArray() && (str
/* 277 */         .equals("set") || str.equals("get"))) {
/* 278 */         int i = ((Integer)arrayOfObject[0]).intValue();
/* 279 */         if (str.equals("get")) {
/* 280 */           return Array.get(object, i);
/*     */         }
/*     */         
/* 283 */         Array.set(object, i, arrayOfObject[1]);
/* 284 */         return null;
/*     */       } 
/*     */       
/* 287 */       method = getMethod(object.getClass(), str, arrayOfClass);
/*     */     } 
/* 289 */     if (method != null) {
/*     */       try {
/* 291 */         if (method instanceof Method) {
/* 292 */           return MethodUtil.invoke(method, object, arrayOfObject);
/*     */         }
/*     */         
/* 295 */         return ((Constructor)method).newInstance(arrayOfObject);
/*     */       
/*     */       }
/* 298 */       catch (IllegalAccessException illegalAccessException) {
/* 299 */         throw new Exception("Statement cannot invoke: " + str + " on " + object
/* 300 */             .getClass(), illegalAccessException);
/*     */       
/*     */       }
/* 303 */       catch (InvocationTargetException invocationTargetException) {
/* 304 */         Throwable throwable = invocationTargetException.getTargetException();
/* 305 */         if (throwable instanceof Exception) {
/* 306 */           throw (Exception)throwable;
/*     */         }
/*     */         
/* 309 */         throw invocationTargetException;
/*     */       } 
/*     */     }
/*     */     
/* 313 */     throw new NoSuchMethodException(toString());
/*     */   }
/*     */   
/*     */   String instanceName(Object paramObject) {
/* 317 */     if (paramObject == null)
/* 318 */       return "null"; 
/* 319 */     if (paramObject.getClass() == String.class) {
/* 320 */       return "\"" + (String)paramObject + "\"";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     return NameGenerator.unqualifiedClassName(paramObject.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 337 */     Object object = getTarget();
/* 338 */     String str = getMethodName();
/* 339 */     Object[] arrayOfObject = getArguments();
/* 340 */     if (arrayOfObject == null) {
/* 341 */       arrayOfObject = emptyArray;
/*     */     }
/* 343 */     StringBuffer stringBuffer = new StringBuffer(instanceName(object) + "." + str + "(");
/* 344 */     int i = arrayOfObject.length;
/* 345 */     for (byte b = 0; b < i; b++) {
/* 346 */       stringBuffer.append(instanceName(arrayOfObject[b]));
/* 347 */       if (b != i - 1) {
/* 348 */         stringBuffer.append(", ");
/*     */       }
/*     */     } 
/* 351 */     stringBuffer.append(");");
/* 352 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   static Method getMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) {
/*     */     try {
/* 357 */       return MethodFinder.findMethod(paramClass, paramString, paramVarArgs);
/*     */     }
/* 359 */     catch (NoSuchMethodException noSuchMethodException) {
/* 360 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/Statement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */