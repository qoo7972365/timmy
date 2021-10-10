/*     */ package com.sun.beans.finder;
/*     */ 
/*     */ import java.lang.reflect.Executable;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractFinder<T extends Executable>
/*     */ {
/*     */   private final Class<?>[] args;
/*     */   
/*     */   protected AbstractFinder(Class<?>[] paramArrayOfClass) {
/*  55 */     this.args = paramArrayOfClass;
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
/*     */   protected boolean isValid(T paramT) {
/*  67 */     return Modifier.isPublic(paramT.getModifiers());
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
/*     */   final T find(T[] paramArrayOfT) throws NoSuchMethodException {
/*     */     T t;
/*  88 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     
/*  90 */     Object object = null;
/*  91 */     Class[] arrayOfClass = null;
/*  92 */     boolean bool = false;
/*     */     
/*  94 */     for (T t1 : paramArrayOfT) {
/*  95 */       if (isValid(t1)) {
/*  96 */         Class[] arrayOfClass1 = t1.getParameterTypes();
/*  97 */         if (arrayOfClass1.length == this.args.length) {
/*  98 */           PrimitiveWrapperMap.replacePrimitivesWithWrappers(arrayOfClass1);
/*  99 */           if (isAssignable(arrayOfClass1, this.args)) {
/* 100 */             if (object == null) {
/* 101 */               t = t1;
/* 102 */               arrayOfClass = arrayOfClass1;
/*     */             } else {
/* 104 */               boolean bool1 = isAssignable(arrayOfClass, arrayOfClass1);
/* 105 */               boolean bool2 = isAssignable(arrayOfClass1, arrayOfClass);
/*     */               
/* 107 */               if (bool2 && bool1) {
/*     */                 
/* 109 */                 bool1 = !t1.isSynthetic();
/* 110 */                 bool2 = !t.isSynthetic();
/*     */               } 
/* 112 */               if (bool2 == bool1) {
/* 113 */                 bool = true;
/* 114 */               } else if (bool1) {
/* 115 */                 t = t1;
/* 116 */                 arrayOfClass = arrayOfClass1;
/* 117 */                 bool = false;
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/* 122 */         if (t1.isVarArgs()) {
/* 123 */           int i = arrayOfClass1.length - 1;
/* 124 */           if (i <= this.args.length) {
/* 125 */             Class[] arrayOfClass2 = new Class[this.args.length];
/* 126 */             System.arraycopy(arrayOfClass1, 0, arrayOfClass2, 0, i);
/* 127 */             if (i < this.args.length) {
/* 128 */               Class<?> clazz = arrayOfClass1[i].getComponentType();
/* 129 */               if (clazz.isPrimitive()) {
/* 130 */                 clazz = PrimitiveWrapperMap.getType(clazz.getName());
/*     */               }
/* 132 */               for (int j = i; j < this.args.length; j++) {
/* 133 */                 arrayOfClass2[j] = clazz;
/*     */               }
/*     */             } 
/* 136 */             hashMap.put(t1, arrayOfClass2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 141 */     for (T t1 : paramArrayOfT) {
/* 142 */       Class[] arrayOfClass1 = (Class[])hashMap.get(t1);
/* 143 */       if (arrayOfClass1 != null && 
/* 144 */         isAssignable(arrayOfClass1, this.args)) {
/* 145 */         if (t == null) {
/* 146 */           t = t1;
/* 147 */           arrayOfClass = arrayOfClass1;
/*     */         } else {
/* 149 */           boolean bool1 = isAssignable(arrayOfClass, arrayOfClass1);
/* 150 */           boolean bool2 = isAssignable(arrayOfClass1, arrayOfClass);
/*     */           
/* 152 */           if (bool2 && bool1) {
/*     */             
/* 154 */             bool1 = !t1.isSynthetic();
/* 155 */             bool2 = !t.isSynthetic();
/*     */           } 
/* 157 */           if (bool2 == bool1) {
/* 158 */             if (arrayOfClass == hashMap.get(t)) {
/* 159 */               bool = true;
/*     */             }
/* 161 */           } else if (bool1) {
/* 162 */             t = t1;
/* 163 */             arrayOfClass = arrayOfClass1;
/* 164 */             bool = false;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 171 */     if (bool) {
/* 172 */       throw new NoSuchMethodException("Ambiguous methods are found");
/*     */     }
/* 174 */     if (t == null) {
/* 175 */       throw new NoSuchMethodException("Method is not found");
/*     */     }
/* 177 */     return t;
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
/*     */   private boolean isAssignable(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
/* 198 */     for (byte b = 0; b < this.args.length; b++) {
/* 199 */       if (null != this.args[b] && 
/* 200 */         !paramArrayOfClass1[b].isAssignableFrom(paramArrayOfClass2[b])) {
/* 201 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 205 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/AbstractFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */