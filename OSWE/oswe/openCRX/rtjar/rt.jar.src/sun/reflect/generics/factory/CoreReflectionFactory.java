/*     */ package sun.reflect.generics.factory;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.GenericDeclaration;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;
/*     */ import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
/*     */ import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;
/*     */ import sun.reflect.generics.reflectiveObjects.WildcardTypeImpl;
/*     */ import sun.reflect.generics.scope.Scope;
/*     */ import sun.reflect.generics.tree.FieldTypeSignature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoreReflectionFactory
/*     */   implements GenericsFactory
/*     */ {
/*     */   private final GenericDeclaration decl;
/*     */   private final Scope scope;
/*     */   
/*     */   private CoreReflectionFactory(GenericDeclaration paramGenericDeclaration, Scope paramScope) {
/*  52 */     this.decl = paramGenericDeclaration;
/*  53 */     this.scope = paramScope;
/*     */   }
/*     */   private GenericDeclaration getDecl() {
/*  56 */     return this.decl;
/*     */   } private Scope getScope() {
/*  58 */     return this.scope;
/*     */   }
/*     */   
/*     */   private ClassLoader getDeclsLoader() {
/*  62 */     if (this.decl instanceof Class) return ((Class)this.decl).getClassLoader(); 
/*  63 */     if (this.decl instanceof Method) {
/*  64 */       return ((Method)this.decl).getDeclaringClass().getClassLoader();
/*     */     }
/*  66 */     assert this.decl instanceof Constructor : "Constructor expected";
/*  67 */     return ((Constructor)this.decl).getDeclaringClass().getClassLoader();
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
/*     */   public static CoreReflectionFactory make(GenericDeclaration paramGenericDeclaration, Scope paramScope) {
/*  89 */     return new CoreReflectionFactory(paramGenericDeclaration, paramScope);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeVariable<?> makeTypeVariable(String paramString, FieldTypeSignature[] paramArrayOfFieldTypeSignature) {
/*  94 */     return TypeVariableImpl.make(getDecl(), paramString, paramArrayOfFieldTypeSignature, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public WildcardType makeWildcard(FieldTypeSignature[] paramArrayOfFieldTypeSignature1, FieldTypeSignature[] paramArrayOfFieldTypeSignature2) {
/*  99 */     return WildcardTypeImpl.make(paramArrayOfFieldTypeSignature1, paramArrayOfFieldTypeSignature2, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterizedType makeParameterizedType(Type paramType1, Type[] paramArrayOfType, Type paramType2) {
/* 105 */     return ParameterizedTypeImpl.make((Class)paramType1, paramArrayOfType, paramType2);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeVariable<?> findTypeVariable(String paramString) {
/* 110 */     return getScope().lookup(paramString);
/*     */   }
/*     */   public Type makeNamedType(String paramString) {
/*     */     try {
/* 114 */       return Class.forName(paramString, false, 
/* 115 */           getDeclsLoader());
/* 116 */     } catch (ClassNotFoundException classNotFoundException) {
/* 117 */       throw new TypeNotPresentException(paramString, classNotFoundException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Type makeArrayType(Type paramType) {
/* 122 */     if (paramType instanceof Class) {
/* 123 */       return Array.newInstance((Class)paramType, 0).getClass();
/*     */     }
/* 125 */     return GenericArrayTypeImpl.make(paramType);
/*     */   }
/*     */   
/* 128 */   public Type makeByte() { return byte.class; }
/* 129 */   public Type makeBool() { return boolean.class; }
/* 130 */   public Type makeShort() { return short.class; }
/* 131 */   public Type makeChar() { return char.class; }
/* 132 */   public Type makeInt() { return int.class; }
/* 133 */   public Type makeLong() { return long.class; }
/* 134 */   public Type makeFloat() { return float.class; } public Type makeDouble() {
/* 135 */     return double.class;
/*     */   } public Type makeVoid() {
/* 137 */     return void.class;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/factory/CoreReflectionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */