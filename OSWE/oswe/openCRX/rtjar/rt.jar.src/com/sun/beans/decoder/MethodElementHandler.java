/*     */ package com.sun.beans.decoder;
/*     */ 
/*     */ import com.sun.beans.finder.MethodFinder;
/*     */ import java.lang.reflect.Method;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MethodElementHandler
/*     */   extends NewElementHandler
/*     */ {
/*     */   private String name;
/*     */   
/*     */   public void addAttribute(String paramString1, String paramString2) {
/*  80 */     if (paramString1.equals("name")) {
/*  81 */       this.name = paramString2;
/*     */     } else {
/*  83 */       super.addAttribute(paramString1, paramString2);
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
/*     */   protected ValueObject getValueObject(Class<?> paramClass, Object[] paramArrayOfObject) throws Exception {
/*  97 */     Object object1 = getContextBean();
/*  98 */     Class[] arrayOfClass = getArgumentTypes(paramArrayOfObject);
/*     */ 
/*     */     
/* 101 */     Method method = (paramClass != null) ? MethodFinder.findStaticMethod(paramClass, this.name, arrayOfClass) : MethodFinder.findMethod(object1.getClass(), this.name, arrayOfClass);
/*     */     
/* 103 */     if (method.isVarArgs()) {
/* 104 */       paramArrayOfObject = getArguments(paramArrayOfObject, method.getParameterTypes());
/*     */     }
/* 106 */     Object object2 = MethodUtil.invoke(method, object1, paramArrayOfObject);
/* 107 */     return method.getReturnType().equals(void.class) ? ValueObjectImpl.VOID : 
/*     */       
/* 109 */       ValueObjectImpl.create(object2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/decoder/MethodElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */