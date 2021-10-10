/*     */ package sun.reflect.generics.reflectiveObjects;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.AnnotationFormatError;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.reflect.AnnotatedType;
/*     */ import java.lang.reflect.GenericDeclaration;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import sun.reflect.annotation.AnnotationSupport;
/*     */ import sun.reflect.annotation.AnnotationType;
/*     */ import sun.reflect.annotation.TypeAnnotationParser;
/*     */ import sun.reflect.generics.factory.GenericsFactory;
/*     */ import sun.reflect.generics.tree.FieldTypeSignature;
/*     */ import sun.reflect.generics.visitor.Reifier;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeVariableImpl<D extends GenericDeclaration>
/*     */   extends LazyReflectiveObjectGenerator
/*     */   implements TypeVariable<D>
/*     */ {
/*     */   D genericDeclaration;
/*     */   private String name;
/*     */   private Type[] bounds;
/*     */   private FieldTypeSignature[] boundASTs;
/*     */   
/*     */   private TypeVariableImpl(D paramD, String paramString, FieldTypeSignature[] paramArrayOfFieldTypeSignature, GenericsFactory paramGenericsFactory) {
/*  69 */     super(paramGenericsFactory);
/*  70 */     this.genericDeclaration = paramD;
/*  71 */     this.name = paramString;
/*  72 */     this.boundASTs = paramArrayOfFieldTypeSignature;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FieldTypeSignature[] getBoundASTs() {
/*  82 */     assert this.bounds == null;
/*  83 */     return this.boundASTs;
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
/*     */   public static <T extends GenericDeclaration> TypeVariableImpl<T> make(T paramT, String paramString, FieldTypeSignature[] paramArrayOfFieldTypeSignature, GenericsFactory paramGenericsFactory) {
/* 103 */     if (!(paramT instanceof Class) && !(paramT instanceof java.lang.reflect.Method) && !(paramT instanceof java.lang.reflect.Constructor))
/*     */     {
/*     */       
/* 106 */       throw new AssertionError("Unexpected kind of GenericDeclaration" + paramT
/* 107 */           .getClass().toString());
/*     */     }
/* 109 */     return new TypeVariableImpl<>(paramT, paramString, paramArrayOfFieldTypeSignature, paramGenericsFactory);
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
/*     */   public Type[] getBounds() {
/* 136 */     if (this.bounds == null) {
/* 137 */       FieldTypeSignature[] arrayOfFieldTypeSignature = getBoundASTs();
/*     */ 
/*     */       
/* 140 */       Type[] arrayOfType = new Type[arrayOfFieldTypeSignature.length];
/*     */       
/* 142 */       for (byte b = 0; b < arrayOfFieldTypeSignature.length; b++) {
/* 143 */         Reifier reifier = getReifier();
/* 144 */         arrayOfFieldTypeSignature[b].accept(reifier);
/* 145 */         arrayOfType[b] = reifier.getResult();
/*     */       } 
/*     */       
/* 148 */       this.bounds = arrayOfType;
/*     */     } 
/*     */     
/* 151 */     return (Type[])this.bounds.clone();
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
/*     */   public D getGenericDeclaration() {
/* 163 */     if (this.genericDeclaration instanceof Class) {
/* 164 */       ReflectUtil.checkPackageAccess((Class)this.genericDeclaration);
/* 165 */     } else if (this.genericDeclaration instanceof java.lang.reflect.Method || this.genericDeclaration instanceof java.lang.reflect.Constructor) {
/*     */       
/* 167 */       ReflectUtil.conservativeCheckMemberAccess((Member)this.genericDeclaration);
/*     */     } else {
/* 169 */       throw new AssertionError("Unexpected kind of GenericDeclaration");
/* 170 */     }  return this.genericDeclaration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 179 */     return this.name;
/*     */   } public String toString() {
/* 181 */     return getName();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 185 */     if (paramObject instanceof TypeVariable && paramObject
/* 186 */       .getClass() == TypeVariableImpl.class) {
/* 187 */       TypeVariable<Object> typeVariable = (TypeVariable)paramObject;
/*     */       
/* 189 */       Object object = typeVariable.getGenericDeclaration();
/* 190 */       String str = typeVariable.getName();
/*     */       
/* 192 */       return (Objects.equals(this.genericDeclaration, object) && 
/* 193 */         Objects.equals(this.name, str));
/*     */     } 
/*     */     
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 201 */     return this.genericDeclaration.hashCode() ^ this.name.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T getAnnotation(Class<T> paramClass) {
/* 207 */     Objects.requireNonNull(paramClass);
/*     */     
/* 209 */     return (T)mapAnnotations(getAnnotations()).get(paramClass);
/*     */   }
/*     */   
/*     */   public <T extends Annotation> T getDeclaredAnnotation(Class<T> paramClass) {
/* 213 */     Objects.requireNonNull(paramClass);
/* 214 */     return getAnnotation(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T[] getAnnotationsByType(Class<T> paramClass) {
/* 219 */     Objects.requireNonNull(paramClass);
/* 220 */     return AnnotationSupport.getDirectlyAndIndirectlyPresent(mapAnnotations(getAnnotations()), paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> paramClass) {
/* 225 */     Objects.requireNonNull(paramClass);
/* 226 */     return getAnnotationsByType(paramClass);
/*     */   }
/*     */   
/*     */   public Annotation[] getAnnotations() {
/* 230 */     int i = typeVarIndex();
/* 231 */     if (i < 0)
/* 232 */       throw new AssertionError("Index must be non-negative."); 
/* 233 */     return TypeAnnotationParser.parseTypeVariableAnnotations(getGenericDeclaration(), i);
/*     */   }
/*     */   
/*     */   public Annotation[] getDeclaredAnnotations() {
/* 237 */     return getAnnotations();
/*     */   }
/*     */   
/*     */   public AnnotatedType[] getAnnotatedBounds() {
/* 241 */     return TypeAnnotationParser.parseAnnotatedBounds(getBounds(), 
/* 242 */         getGenericDeclaration(), 
/* 243 */         typeVarIndex());
/*     */   }
/*     */   
/* 246 */   private static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];
/*     */ 
/*     */   
/*     */   private int typeVarIndex() {
/* 250 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])getGenericDeclaration().getTypeParameters();
/* 251 */     byte b = -1;
/* 252 */     for (TypeVariable typeVariable : arrayOfTypeVariable) {
/* 253 */       b++;
/* 254 */       if (equals(typeVariable))
/* 255 */         return b; 
/*     */     } 
/* 257 */     return -1;
/*     */   }
/*     */   
/*     */   private static Map<Class<? extends Annotation>, Annotation> mapAnnotations(Annotation[] paramArrayOfAnnotation) {
/* 261 */     LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
/*     */     
/* 263 */     for (Annotation annotation : paramArrayOfAnnotation) {
/* 264 */       Class<? extends Annotation> clazz = annotation.annotationType();
/* 265 */       AnnotationType annotationType = AnnotationType.getInstance(clazz);
/* 266 */       if (annotationType.retention() == RetentionPolicy.RUNTIME && 
/* 267 */         linkedHashMap.put(clazz, annotation) != null)
/* 268 */         throw new AnnotationFormatError("Duplicate annotation for class: " + clazz + ": " + annotation); 
/*     */     } 
/* 270 */     return (Map)linkedHashMap;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/reflectiveObjects/TypeVariableImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */