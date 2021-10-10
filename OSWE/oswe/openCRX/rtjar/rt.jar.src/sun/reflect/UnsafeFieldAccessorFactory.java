/*     */ package sun.reflect;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnsafeFieldAccessorFactory
/*     */ {
/*     */   static FieldAccessor newFieldAccessor(Field paramField, boolean paramBoolean) {
/*  33 */     Class<?> clazz = paramField.getType();
/*  34 */     boolean bool1 = Modifier.isStatic(paramField.getModifiers());
/*  35 */     boolean bool2 = Modifier.isFinal(paramField.getModifiers());
/*  36 */     boolean bool3 = Modifier.isVolatile(paramField.getModifiers());
/*  37 */     boolean bool4 = (bool2 || bool3) ? true : false;
/*  38 */     boolean bool5 = (bool2 && (bool1 || !paramBoolean)) ? true : false;
/*  39 */     if (bool1) {
/*     */ 
/*     */ 
/*     */       
/*  43 */       UnsafeFieldAccessorImpl.unsafe.ensureClassInitialized(paramField.getDeclaringClass());
/*     */       
/*  45 */       if (!bool4) {
/*  46 */         if (clazz == boolean.class)
/*  47 */           return new UnsafeStaticBooleanFieldAccessorImpl(paramField); 
/*  48 */         if (clazz == byte.class)
/*  49 */           return new UnsafeStaticByteFieldAccessorImpl(paramField); 
/*  50 */         if (clazz == short.class)
/*  51 */           return new UnsafeStaticShortFieldAccessorImpl(paramField); 
/*  52 */         if (clazz == char.class)
/*  53 */           return new UnsafeStaticCharacterFieldAccessorImpl(paramField); 
/*  54 */         if (clazz == int.class)
/*  55 */           return new UnsafeStaticIntegerFieldAccessorImpl(paramField); 
/*  56 */         if (clazz == long.class)
/*  57 */           return new UnsafeStaticLongFieldAccessorImpl(paramField); 
/*  58 */         if (clazz == float.class)
/*  59 */           return new UnsafeStaticFloatFieldAccessorImpl(paramField); 
/*  60 */         if (clazz == double.class) {
/*  61 */           return new UnsafeStaticDoubleFieldAccessorImpl(paramField);
/*     */         }
/*  63 */         return new UnsafeStaticObjectFieldAccessorImpl(paramField);
/*     */       } 
/*     */       
/*  66 */       if (clazz == boolean.class)
/*  67 */         return new UnsafeQualifiedStaticBooleanFieldAccessorImpl(paramField, bool5); 
/*  68 */       if (clazz == byte.class)
/*  69 */         return new UnsafeQualifiedStaticByteFieldAccessorImpl(paramField, bool5); 
/*  70 */       if (clazz == short.class)
/*  71 */         return new UnsafeQualifiedStaticShortFieldAccessorImpl(paramField, bool5); 
/*  72 */       if (clazz == char.class)
/*  73 */         return new UnsafeQualifiedStaticCharacterFieldAccessorImpl(paramField, bool5); 
/*  74 */       if (clazz == int.class)
/*  75 */         return new UnsafeQualifiedStaticIntegerFieldAccessorImpl(paramField, bool5); 
/*  76 */       if (clazz == long.class)
/*  77 */         return new UnsafeQualifiedStaticLongFieldAccessorImpl(paramField, bool5); 
/*  78 */       if (clazz == float.class)
/*  79 */         return new UnsafeQualifiedStaticFloatFieldAccessorImpl(paramField, bool5); 
/*  80 */       if (clazz == double.class) {
/*  81 */         return new UnsafeQualifiedStaticDoubleFieldAccessorImpl(paramField, bool5);
/*     */       }
/*  83 */       return new UnsafeQualifiedStaticObjectFieldAccessorImpl(paramField, bool5);
/*     */     } 
/*     */ 
/*     */     
/*  87 */     if (!bool4) {
/*  88 */       if (clazz == boolean.class)
/*  89 */         return new UnsafeBooleanFieldAccessorImpl(paramField); 
/*  90 */       if (clazz == byte.class)
/*  91 */         return new UnsafeByteFieldAccessorImpl(paramField); 
/*  92 */       if (clazz == short.class)
/*  93 */         return new UnsafeShortFieldAccessorImpl(paramField); 
/*  94 */       if (clazz == char.class)
/*  95 */         return new UnsafeCharacterFieldAccessorImpl(paramField); 
/*  96 */       if (clazz == int.class)
/*  97 */         return new UnsafeIntegerFieldAccessorImpl(paramField); 
/*  98 */       if (clazz == long.class)
/*  99 */         return new UnsafeLongFieldAccessorImpl(paramField); 
/* 100 */       if (clazz == float.class)
/* 101 */         return new UnsafeFloatFieldAccessorImpl(paramField); 
/* 102 */       if (clazz == double.class) {
/* 103 */         return new UnsafeDoubleFieldAccessorImpl(paramField);
/*     */       }
/* 105 */       return new UnsafeObjectFieldAccessorImpl(paramField);
/*     */     } 
/*     */     
/* 108 */     if (clazz == boolean.class)
/* 109 */       return new UnsafeQualifiedBooleanFieldAccessorImpl(paramField, bool5); 
/* 110 */     if (clazz == byte.class)
/* 111 */       return new UnsafeQualifiedByteFieldAccessorImpl(paramField, bool5); 
/* 112 */     if (clazz == short.class)
/* 113 */       return new UnsafeQualifiedShortFieldAccessorImpl(paramField, bool5); 
/* 114 */     if (clazz == char.class)
/* 115 */       return new UnsafeQualifiedCharacterFieldAccessorImpl(paramField, bool5); 
/* 116 */     if (clazz == int.class)
/* 117 */       return new UnsafeQualifiedIntegerFieldAccessorImpl(paramField, bool5); 
/* 118 */     if (clazz == long.class)
/* 119 */       return new UnsafeQualifiedLongFieldAccessorImpl(paramField, bool5); 
/* 120 */     if (clazz == float.class)
/* 121 */       return new UnsafeQualifiedFloatFieldAccessorImpl(paramField, bool5); 
/* 122 */     if (clazz == double.class) {
/* 123 */       return new UnsafeQualifiedDoubleFieldAccessorImpl(paramField, bool5);
/*     */     }
/* 125 */     return new UnsafeQualifiedObjectFieldAccessorImpl(paramField, bool5);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/UnsafeFieldAccessorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */