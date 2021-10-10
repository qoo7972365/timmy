/*     */ package sun.reflect.annotation;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedArrayType;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.AnnotatedParameterizedType;
/*     */ import java.lang.reflect.AnnotatedType;
/*     */ import java.lang.reflect.AnnotatedTypeVariable;
/*     */ import java.lang.reflect.AnnotatedWildcardType;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AnnotatedTypeFactory
/*     */ {
/*     */   public static AnnotatedType buildAnnotatedType(Type paramType, TypeAnnotation.LocationInfo paramLocationInfo, TypeAnnotation[] paramArrayOfTypeAnnotation1, TypeAnnotation[] paramArrayOfTypeAnnotation2, AnnotatedElement paramAnnotatedElement) {
/*  54 */     if (paramType == null) {
/*  55 */       return EMPTY_ANNOTATED_TYPE;
/*     */     }
/*  57 */     if (isArray(paramType)) {
/*  58 */       return new AnnotatedArrayTypeImpl(paramType, paramLocationInfo, paramArrayOfTypeAnnotation1, paramArrayOfTypeAnnotation2, paramAnnotatedElement);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  63 */     if (paramType instanceof Class) {
/*  64 */       return new AnnotatedTypeBaseImpl(paramType, 
/*  65 */           addNesting(paramType, paramLocationInfo), paramArrayOfTypeAnnotation1, paramArrayOfTypeAnnotation2, paramAnnotatedElement);
/*     */     }
/*     */ 
/*     */     
/*  69 */     if (paramType instanceof TypeVariable) {
/*  70 */       return new AnnotatedTypeVariableImpl((TypeVariable)paramType, paramLocationInfo, paramArrayOfTypeAnnotation1, paramArrayOfTypeAnnotation2, paramAnnotatedElement);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  75 */     if (paramType instanceof ParameterizedType) {
/*  76 */       return new AnnotatedParameterizedTypeImpl((ParameterizedType)paramType, 
/*  77 */           addNesting(paramType, paramLocationInfo), paramArrayOfTypeAnnotation1, paramArrayOfTypeAnnotation2, paramAnnotatedElement);
/*     */     }
/*     */ 
/*     */     
/*  81 */     if (paramType instanceof WildcardType) {
/*  82 */       return new AnnotatedWildcardTypeImpl((WildcardType)paramType, paramLocationInfo, paramArrayOfTypeAnnotation1, paramArrayOfTypeAnnotation2, paramAnnotatedElement);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     throw new AssertionError("Unknown instance of Type: " + paramType + "\nThis should not happen.");
/*     */   }
/*     */   
/*     */   private static TypeAnnotation.LocationInfo addNesting(Type paramType, TypeAnnotation.LocationInfo paramLocationInfo) {
/*  92 */     if (isArray(paramType))
/*  93 */       return paramLocationInfo; 
/*  94 */     if (paramType instanceof Class) {
/*  95 */       Class clazz = (Class)paramType;
/*  96 */       if (clazz.getEnclosingClass() == null)
/*  97 */         return paramLocationInfo; 
/*  98 */       if (Modifier.isStatic(clazz.getModifiers()))
/*  99 */         return addNesting(clazz.getEnclosingClass(), paramLocationInfo); 
/* 100 */       return addNesting(clazz.getEnclosingClass(), paramLocationInfo.pushInner());
/* 101 */     }  if (paramType instanceof ParameterizedType) {
/* 102 */       ParameterizedType parameterizedType = (ParameterizedType)paramType;
/* 103 */       if (parameterizedType.getOwnerType() == null)
/* 104 */         return paramLocationInfo; 
/* 105 */       return addNesting(parameterizedType.getOwnerType(), paramLocationInfo.pushInner());
/*     */     } 
/* 107 */     return paramLocationInfo;
/*     */   }
/*     */   
/*     */   private static boolean isArray(Type paramType) {
/* 111 */     if (paramType instanceof Class) {
/* 112 */       Class clazz = (Class)paramType;
/* 113 */       if (clazz.isArray())
/* 114 */         return true; 
/* 115 */     } else if (paramType instanceof GenericArrayType) {
/* 116 */       return true;
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */   
/* 121 */   static final AnnotatedType EMPTY_ANNOTATED_TYPE = new AnnotatedTypeBaseImpl(null, TypeAnnotation.LocationInfo.BASE_LOCATION, new TypeAnnotation[0], new TypeAnnotation[0], null);
/*     */   
/* 123 */   static final AnnotatedType[] EMPTY_ANNOTATED_TYPE_ARRAY = new AnnotatedType[0];
/*     */   
/*     */   private static class AnnotatedTypeBaseImpl
/*     */     implements AnnotatedType
/*     */   {
/*     */     private final Type type;
/*     */     private final AnnotatedElement decl;
/*     */     private final TypeAnnotation.LocationInfo location;
/*     */     private final TypeAnnotation[] allOnSameTargetTypeAnnotations;
/*     */     private final Map<Class<? extends Annotation>, Annotation> annotations;
/*     */     
/*     */     AnnotatedTypeBaseImpl(Type param1Type, TypeAnnotation.LocationInfo param1LocationInfo, TypeAnnotation[] param1ArrayOfTypeAnnotation1, TypeAnnotation[] param1ArrayOfTypeAnnotation2, AnnotatedElement param1AnnotatedElement) {
/* 135 */       this.type = param1Type;
/* 136 */       this.decl = param1AnnotatedElement;
/* 137 */       this.location = param1LocationInfo;
/* 138 */       this.allOnSameTargetTypeAnnotations = param1ArrayOfTypeAnnotation2;
/* 139 */       this.annotations = TypeAnnotationParser.mapTypeAnnotations(param1LocationInfo.filter(param1ArrayOfTypeAnnotation1));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final Annotation[] getAnnotations() {
/* 145 */       return getDeclaredAnnotations();
/*     */     }
/*     */ 
/*     */     
/*     */     public final <T extends Annotation> T getAnnotation(Class<T> param1Class) {
/* 150 */       return getDeclaredAnnotation(param1Class);
/*     */     }
/*     */ 
/*     */     
/*     */     public final <T extends Annotation> T[] getAnnotationsByType(Class<T> param1Class) {
/* 155 */       return getDeclaredAnnotationsByType(param1Class);
/*     */     }
/*     */ 
/*     */     
/*     */     public final Annotation[] getDeclaredAnnotations() {
/* 160 */       return (Annotation[])this.annotations.values().toArray((Object[])new Annotation[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final <T extends Annotation> T getDeclaredAnnotation(Class<T> param1Class) {
/* 166 */       return (T)this.annotations.get(param1Class);
/*     */     }
/*     */ 
/*     */     
/*     */     public final <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> param1Class) {
/* 171 */       return AnnotationSupport.getDirectlyAndIndirectlyPresent(this.annotations, param1Class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final Type getType() {
/* 177 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     final TypeAnnotation.LocationInfo getLocation() {
/* 182 */       return this.location;
/*     */     }
/*     */     final TypeAnnotation[] getTypeAnnotations() {
/* 185 */       return this.allOnSameTargetTypeAnnotations;
/*     */     }
/*     */     final AnnotatedElement getDecl() {
/* 188 */       return this.decl;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AnnotatedArrayTypeImpl
/*     */     extends AnnotatedTypeBaseImpl
/*     */     implements AnnotatedArrayType {
/*     */     AnnotatedArrayTypeImpl(Type param1Type, TypeAnnotation.LocationInfo param1LocationInfo, TypeAnnotation[] param1ArrayOfTypeAnnotation1, TypeAnnotation[] param1ArrayOfTypeAnnotation2, AnnotatedElement param1AnnotatedElement) {
/* 196 */       super(param1Type, param1LocationInfo, param1ArrayOfTypeAnnotation1, param1ArrayOfTypeAnnotation2, param1AnnotatedElement);
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotatedType getAnnotatedGenericComponentType() {
/* 201 */       return AnnotatedTypeFactory.buildAnnotatedType(getComponentType(), 
/* 202 */           getLocation().pushArray(), 
/* 203 */           getTypeAnnotations(), 
/* 204 */           getTypeAnnotations(), 
/* 205 */           getDecl());
/*     */     }
/*     */     
/*     */     private Type getComponentType() {
/* 209 */       Type type = getType();
/* 210 */       if (type instanceof Class) {
/* 211 */         Class clazz = (Class)type;
/* 212 */         return clazz.getComponentType();
/*     */       } 
/* 214 */       return ((GenericArrayType)type).getGenericComponentType();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AnnotatedTypeVariableImpl
/*     */     extends AnnotatedTypeBaseImpl
/*     */     implements AnnotatedTypeVariable {
/*     */     AnnotatedTypeVariableImpl(TypeVariable<?> param1TypeVariable, TypeAnnotation.LocationInfo param1LocationInfo, TypeAnnotation[] param1ArrayOfTypeAnnotation1, TypeAnnotation[] param1ArrayOfTypeAnnotation2, AnnotatedElement param1AnnotatedElement) {
/* 222 */       super(param1TypeVariable, param1LocationInfo, param1ArrayOfTypeAnnotation1, param1ArrayOfTypeAnnotation2, param1AnnotatedElement);
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotatedType[] getAnnotatedBounds() {
/* 227 */       return getTypeVariable().getAnnotatedBounds();
/*     */     }
/*     */     
/*     */     private TypeVariable<?> getTypeVariable() {
/* 231 */       return (TypeVariable)getType();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AnnotatedParameterizedTypeImpl
/*     */     extends AnnotatedTypeBaseImpl
/*     */     implements AnnotatedParameterizedType
/*     */   {
/*     */     AnnotatedParameterizedTypeImpl(ParameterizedType param1ParameterizedType, TypeAnnotation.LocationInfo param1LocationInfo, TypeAnnotation[] param1ArrayOfTypeAnnotation1, TypeAnnotation[] param1ArrayOfTypeAnnotation2, AnnotatedElement param1AnnotatedElement) {
/* 240 */       super(param1ParameterizedType, param1LocationInfo, param1ArrayOfTypeAnnotation1, param1ArrayOfTypeAnnotation2, param1AnnotatedElement);
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotatedType[] getAnnotatedActualTypeArguments() {
/* 245 */       Type[] arrayOfType = getParameterizedType().getActualTypeArguments();
/* 246 */       AnnotatedType[] arrayOfAnnotatedType = new AnnotatedType[arrayOfType.length];
/* 247 */       Arrays.fill((Object[])arrayOfAnnotatedType, AnnotatedTypeFactory.EMPTY_ANNOTATED_TYPE);
/* 248 */       int i = (getTypeAnnotations()).length;
/* 249 */       for (byte b = 0; b < arrayOfAnnotatedType.length; b++) {
/* 250 */         ArrayList<TypeAnnotation> arrayList = new ArrayList(i);
/* 251 */         TypeAnnotation.LocationInfo locationInfo = getLocation().pushTypeArg((short)(byte)b);
/* 252 */         for (TypeAnnotation typeAnnotation : getTypeAnnotations()) {
/* 253 */           if (typeAnnotation.getLocationInfo().isSameLocationInfo(locationInfo))
/* 254 */             arrayList.add(typeAnnotation); 
/* 255 */         }  arrayOfAnnotatedType[b] = AnnotatedTypeFactory.buildAnnotatedType(arrayOfType[b], locationInfo, arrayList
/*     */             
/* 257 */             .<TypeAnnotation>toArray(new TypeAnnotation[0]), 
/* 258 */             getTypeAnnotations(), 
/* 259 */             getDecl());
/*     */       } 
/* 261 */       return arrayOfAnnotatedType;
/*     */     }
/*     */     
/*     */     private ParameterizedType getParameterizedType() {
/* 265 */       return (ParameterizedType)getType();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AnnotatedWildcardTypeImpl
/*     */     extends AnnotatedTypeBaseImpl implements AnnotatedWildcardType {
/*     */     private final boolean hasUpperBounds;
/*     */     
/*     */     AnnotatedWildcardTypeImpl(WildcardType param1WildcardType, TypeAnnotation.LocationInfo param1LocationInfo, TypeAnnotation[] param1ArrayOfTypeAnnotation1, TypeAnnotation[] param1ArrayOfTypeAnnotation2, AnnotatedElement param1AnnotatedElement) {
/* 274 */       super(param1WildcardType, param1LocationInfo, param1ArrayOfTypeAnnotation1, param1ArrayOfTypeAnnotation2, param1AnnotatedElement);
/* 275 */       this.hasUpperBounds = ((param1WildcardType.getLowerBounds()).length == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotatedType[] getAnnotatedUpperBounds() {
/* 280 */       if (!hasUpperBounds())
/* 281 */         return new AnnotatedType[0]; 
/* 282 */       return getAnnotatedBounds(getWildcardType().getUpperBounds());
/*     */     }
/*     */ 
/*     */     
/*     */     public AnnotatedType[] getAnnotatedLowerBounds() {
/* 287 */       if (this.hasUpperBounds)
/* 288 */         return new AnnotatedType[0]; 
/* 289 */       return getAnnotatedBounds(getWildcardType().getLowerBounds());
/*     */     }
/*     */     
/*     */     private AnnotatedType[] getAnnotatedBounds(Type[] param1ArrayOfType) {
/* 293 */       AnnotatedType[] arrayOfAnnotatedType = new AnnotatedType[param1ArrayOfType.length];
/* 294 */       Arrays.fill((Object[])arrayOfAnnotatedType, AnnotatedTypeFactory.EMPTY_ANNOTATED_TYPE);
/* 295 */       TypeAnnotation.LocationInfo locationInfo = getLocation().pushWildcard();
/* 296 */       int i = (getTypeAnnotations()).length;
/* 297 */       for (byte b = 0; b < arrayOfAnnotatedType.length; b++) {
/* 298 */         ArrayList<TypeAnnotation> arrayList = new ArrayList(i);
/* 299 */         for (TypeAnnotation typeAnnotation : getTypeAnnotations()) {
/* 300 */           if (typeAnnotation.getLocationInfo().isSameLocationInfo(locationInfo))
/* 301 */             arrayList.add(typeAnnotation); 
/* 302 */         }  arrayOfAnnotatedType[b] = AnnotatedTypeFactory.buildAnnotatedType(param1ArrayOfType[b], locationInfo, arrayList
/*     */             
/* 304 */             .<TypeAnnotation>toArray(new TypeAnnotation[0]), 
/* 305 */             getTypeAnnotations(), 
/* 306 */             getDecl());
/*     */       } 
/* 308 */       return arrayOfAnnotatedType;
/*     */     }
/*     */     
/*     */     private WildcardType getWildcardType() {
/* 312 */       return (WildcardType)getType();
/*     */     }
/*     */     
/*     */     private boolean hasUpperBounds() {
/* 316 */       return this.hasUpperBounds;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/annotation/AnnotatedTypeFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */