/*    */ package java.beans;
/*    */ 
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.lang.reflect.Method;
/*    */ import sun.reflect.misc.ReflectUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class MethodRef
/*    */ {
/*    */   private String signature;
/*    */   private SoftReference<Method> methodRef;
/*    */   private WeakReference<Class<?>> typeRef;
/*    */   
/*    */   void set(Method paramMethod) {
/* 40 */     if (paramMethod == null) {
/* 41 */       this.signature = null;
/* 42 */       this.methodRef = null;
/* 43 */       this.typeRef = null;
/*    */     } else {
/*    */       
/* 46 */       this.signature = paramMethod.toGenericString();
/* 47 */       this.methodRef = new SoftReference<>(paramMethod);
/* 48 */       this.typeRef = new WeakReference<>(paramMethod.getDeclaringClass());
/*    */     } 
/*    */   }
/*    */   
/*    */   boolean isSet() {
/* 53 */     return (this.methodRef != null);
/*    */   }
/*    */   
/*    */   Method get() {
/* 57 */     if (this.methodRef == null) {
/* 58 */       return null;
/*    */     }
/* 60 */     Method method = this.methodRef.get();
/* 61 */     if (method == null) {
/* 62 */       method = find(this.typeRef.get(), this.signature);
/* 63 */       if (method == null) {
/* 64 */         this.signature = null;
/* 65 */         this.methodRef = null;
/* 66 */         this.typeRef = null;
/* 67 */         return null;
/*    */       } 
/* 69 */       this.methodRef = new SoftReference<>(method);
/*    */     } 
/* 71 */     return ReflectUtil.isPackageAccessible(method.getDeclaringClass()) ? method : null;
/*    */   }
/*    */   
/*    */   private static Method find(Class<?> paramClass, String paramString) {
/* 75 */     if (paramClass != null) {
/* 76 */       for (Method method : paramClass.getMethods()) {
/* 77 */         if (paramClass.equals(method.getDeclaringClass()) && 
/* 78 */           method.toGenericString().equals(paramString)) {
/* 79 */           return method;
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 84 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/MethodRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */