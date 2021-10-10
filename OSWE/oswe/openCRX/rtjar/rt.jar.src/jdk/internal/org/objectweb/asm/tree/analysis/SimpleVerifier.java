/*     */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*     */ 
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleVerifier
/*     */   extends BasicVerifier
/*     */ {
/*     */   private final Type currentClass;
/*     */   private final Type currentSuperClass;
/*     */   private final List<Type> currentClassInterfaces;
/*     */   private final boolean isInterface;
/*  98 */   private ClassLoader loader = getClass().getClassLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleVerifier() {
/* 104 */     this(null, null, false);
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
/*     */   public SimpleVerifier(Type paramType1, Type paramType2, boolean paramBoolean) {
/* 120 */     this(paramType1, paramType2, null, paramBoolean);
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
/*     */   public SimpleVerifier(Type paramType1, Type paramType2, List<Type> paramList, boolean paramBoolean) {
/* 139 */     this(327680, paramType1, paramType2, paramList, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleVerifier(int paramInt, Type paramType1, Type paramType2, List<Type> paramList, boolean paramBoolean) {
/* 146 */     super(paramInt);
/* 147 */     this.currentClass = paramType1;
/* 148 */     this.currentSuperClass = paramType2;
/* 149 */     this.currentClassInterfaces = paramList;
/* 150 */     this.isInterface = paramBoolean;
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
/*     */   public void setClassLoader(ClassLoader paramClassLoader) {
/* 162 */     this.loader = paramClassLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   public BasicValue newValue(Type paramType) {
/* 167 */     if (paramType == null) {
/* 168 */       return BasicValue.UNINITIALIZED_VALUE;
/*     */     }
/*     */     
/* 171 */     boolean bool = (paramType.getSort() == 9) ? true : false;
/* 172 */     if (bool) {
/* 173 */       switch (paramType.getElementType().getSort()) {
/*     */         case 1:
/*     */         case 2:
/*     */         case 3:
/*     */         case 4:
/* 178 */           return new BasicValue(paramType);
/*     */       } 
/*     */     
/*     */     }
/* 182 */     BasicValue basicValue = super.newValue(paramType);
/* 183 */     if (BasicValue.REFERENCE_VALUE.equals(basicValue)) {
/* 184 */       if (bool) {
/* 185 */         basicValue = newValue(paramType.getElementType());
/* 186 */         String str = basicValue.getType().getDescriptor();
/* 187 */         for (byte b = 0; b < paramType.getDimensions(); b++) {
/* 188 */           str = '[' + str;
/*     */         }
/* 190 */         basicValue = new BasicValue(Type.getType(str));
/*     */       } else {
/* 192 */         basicValue = new BasicValue(paramType);
/*     */       } 
/*     */     }
/* 195 */     return basicValue;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isArrayValue(BasicValue paramBasicValue) {
/* 200 */     Type type = paramBasicValue.getType();
/* 201 */     return (type != null && ("Lnull;"
/* 202 */       .equals(type.getDescriptor()) || type.getSort() == 9));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasicValue getElementValue(BasicValue paramBasicValue) throws AnalyzerException {
/* 208 */     Type type = paramBasicValue.getType();
/* 209 */     if (type != null) {
/* 210 */       if (type.getSort() == 9)
/* 211 */         return newValue(Type.getType(type.getDescriptor()
/* 212 */               .substring(1))); 
/* 213 */       if ("Lnull;".equals(type.getDescriptor())) {
/* 214 */         return paramBasicValue;
/*     */       }
/*     */     } 
/* 217 */     throw new Error("Internal error");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSubTypeOf(BasicValue paramBasicValue1, BasicValue paramBasicValue2) {
/* 223 */     Type type1 = paramBasicValue2.getType();
/* 224 */     Type type2 = paramBasicValue1.getType();
/* 225 */     switch (type1.getSort()) {
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/* 230 */         return type2.equals(type1);
/*     */       case 9:
/*     */       case 10:
/* 233 */         if ("Lnull;".equals(type2.getDescriptor()))
/* 234 */           return true; 
/* 235 */         if (type2.getSort() == 10 || type2
/* 236 */           .getSort() == 9) {
/* 237 */           return isAssignableFrom(type1, type2);
/*     */         }
/* 239 */         return false;
/*     */     } 
/*     */     
/* 242 */     throw new Error("Internal error");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue merge(BasicValue paramBasicValue1, BasicValue paramBasicValue2) {
/* 248 */     if (!paramBasicValue1.equals(paramBasicValue2)) {
/* 249 */       Type type1 = paramBasicValue1.getType();
/* 250 */       Type type2 = paramBasicValue2.getType();
/* 251 */       if (type1 != null && (type1
/* 252 */         .getSort() == 10 || type1.getSort() == 9) && 
/* 253 */         type2 != null && (type2
/* 254 */         .getSort() == 10 || type2.getSort() == 9)) {
/* 255 */         if ("Lnull;".equals(type1.getDescriptor())) {
/* 256 */           return paramBasicValue2;
/*     */         }
/* 258 */         if ("Lnull;".equals(type2.getDescriptor())) {
/* 259 */           return paramBasicValue1;
/*     */         }
/* 261 */         if (isAssignableFrom(type1, type2)) {
/* 262 */           return paramBasicValue1;
/*     */         }
/* 264 */         if (isAssignableFrom(type2, type1)) {
/* 265 */           return paramBasicValue2;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         while (true) {
/* 272 */           if (type1 == null || isInterface(type1)) {
/* 273 */             return BasicValue.REFERENCE_VALUE;
/*     */           }
/* 275 */           type1 = getSuperClass(type1);
/* 276 */           if (isAssignableFrom(type1, type2)) {
/* 277 */             return newValue(type1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 282 */       return BasicValue.UNINITIALIZED_VALUE;
/*     */     } 
/* 284 */     return paramBasicValue1;
/*     */   }
/*     */   
/*     */   protected boolean isInterface(Type paramType) {
/* 288 */     if (this.currentClass != null && paramType.equals(this.currentClass)) {
/* 289 */       return this.isInterface;
/*     */     }
/* 291 */     return getClass(paramType).isInterface();
/*     */   }
/*     */   
/*     */   protected Type getSuperClass(Type paramType) {
/* 295 */     if (this.currentClass != null && paramType.equals(this.currentClass)) {
/* 296 */       return this.currentSuperClass;
/*     */     }
/* 298 */     Class<?> clazz = getClass(paramType).getSuperclass();
/* 299 */     return (clazz == null) ? null : Type.getType(clazz);
/*     */   }
/*     */   
/*     */   protected boolean isAssignableFrom(Type paramType1, Type paramType2) {
/* 303 */     if (paramType1.equals(paramType2)) {
/* 304 */       return true;
/*     */     }
/* 306 */     if (this.currentClass != null && paramType1.equals(this.currentClass)) {
/* 307 */       if (getSuperClass(paramType2) == null) {
/* 308 */         return false;
/*     */       }
/* 310 */       if (this.isInterface) {
/* 311 */         return (paramType2.getSort() == 10 || paramType2
/* 312 */           .getSort() == 9);
/*     */       }
/* 314 */       return isAssignableFrom(paramType1, getSuperClass(paramType2));
/*     */     } 
/*     */     
/* 317 */     if (this.currentClass != null && paramType2.equals(this.currentClass)) {
/* 318 */       if (isAssignableFrom(paramType1, this.currentSuperClass)) {
/* 319 */         return true;
/*     */       }
/* 321 */       if (this.currentClassInterfaces != null) {
/* 322 */         for (byte b = 0; b < this.currentClassInterfaces.size(); b++) {
/* 323 */           Type type = this.currentClassInterfaces.get(b);
/* 324 */           if (isAssignableFrom(paramType1, type)) {
/* 325 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/* 329 */       return false;
/*     */     } 
/* 331 */     Class<?> clazz = getClass(paramType1);
/* 332 */     if (clazz.isInterface()) {
/* 333 */       clazz = Object.class;
/*     */     }
/* 335 */     return clazz.isAssignableFrom(getClass(paramType2));
/*     */   }
/*     */   
/*     */   protected Class<?> getClass(Type paramType) {
/*     */     try {
/* 340 */       if (paramType.getSort() == 9) {
/* 341 */         return Class.forName(paramType.getDescriptor().replace('/', '.'), false, this.loader);
/*     */       }
/*     */       
/* 344 */       return Class.forName(paramType.getClassName(), false, this.loader);
/* 345 */     } catch (ClassNotFoundException classNotFoundException) {
/* 346 */       throw new RuntimeException(classNotFoundException.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/SimpleVerifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */