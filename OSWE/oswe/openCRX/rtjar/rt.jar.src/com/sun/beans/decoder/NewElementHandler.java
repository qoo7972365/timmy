/*     */ package com.sun.beans.decoder;
/*     */ 
/*     */ import com.sun.beans.finder.ConstructorFinder;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NewElementHandler
/*     */   extends ElementHandler
/*     */ {
/*  59 */   private List<Object> arguments = new ArrayList();
/*  60 */   private ValueObject value = ValueObjectImpl.VOID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(String paramString1, String paramString2) {
/*  79 */     if (paramString1.equals("class")) {
/*  80 */       this.type = getOwner().findClass(paramString2);
/*     */     } else {
/*  82 */       super.addAttribute(paramString1, paramString2);
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
/*     */   protected final void addArgument(Object paramObject) {
/*  94 */     if (this.arguments == null) {
/*  95 */       throw new IllegalStateException("Could not add argument to evaluated element");
/*     */     }
/*  97 */     this.arguments.add(paramObject);
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
/*     */   protected final Object getContextBean() {
/* 109 */     return (this.type != null) ? this.type : super
/*     */       
/* 111 */       .getContextBean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ValueObject getValueObject() {
/* 121 */     if (this.arguments != null) {
/*     */       try {
/* 123 */         this.value = getValueObject(this.type, this.arguments.toArray());
/*     */       }
/* 125 */       catch (Exception exception) {
/* 126 */         getOwner().handleException(exception);
/*     */       } finally {
/*     */         
/* 129 */         this.arguments = null;
/*     */       } 
/*     */     }
/* 132 */     return this.value;
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
/*     */   ValueObject getValueObject(Class<?> paramClass, Object[] paramArrayOfObject) throws Exception {
/* 148 */     if (paramClass == null) {
/* 149 */       throw new IllegalArgumentException("Class name is not set");
/*     */     }
/* 151 */     Class[] arrayOfClass = getArgumentTypes(paramArrayOfObject);
/* 152 */     Constructor<?> constructor = ConstructorFinder.findConstructor(paramClass, arrayOfClass);
/* 153 */     if (constructor.isVarArgs()) {
/* 154 */       paramArrayOfObject = getArguments(paramArrayOfObject, constructor.getParameterTypes());
/*     */     }
/* 156 */     return ValueObjectImpl.create(constructor.newInstance(paramArrayOfObject));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class<?>[] getArgumentTypes(Object[] paramArrayOfObject) {
/* 167 */     Class[] arrayOfClass = new Class[paramArrayOfObject.length];
/* 168 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 169 */       if (paramArrayOfObject[b] != null) {
/* 170 */         arrayOfClass[b] = paramArrayOfObject[b].getClass();
/*     */       }
/*     */     } 
/* 173 */     return arrayOfClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object[] getArguments(Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass) {
/* 184 */     int i = paramArrayOfClass.length - 1;
/* 185 */     if (paramArrayOfClass.length == paramArrayOfObject.length) {
/* 186 */       Object object1 = paramArrayOfObject[i];
/* 187 */       if (object1 == null) {
/* 188 */         return paramArrayOfObject;
/*     */       }
/* 190 */       Class<?> clazz1 = paramArrayOfClass[i];
/* 191 */       if (clazz1.isAssignableFrom(object1.getClass())) {
/* 192 */         return paramArrayOfObject;
/*     */       }
/*     */     } 
/* 195 */     int j = paramArrayOfObject.length - i;
/* 196 */     Class<?> clazz = paramArrayOfClass[i].getComponentType();
/* 197 */     Object object = Array.newInstance(clazz, j);
/* 198 */     System.arraycopy(paramArrayOfObject, i, object, 0, j);
/*     */     
/* 200 */     Object[] arrayOfObject = new Object[paramArrayOfClass.length];
/* 201 */     System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, i);
/* 202 */     arrayOfObject[i] = object;
/* 203 */     return arrayOfObject;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/decoder/NewElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */