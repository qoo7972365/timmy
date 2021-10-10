/*     */ package sun.reflect.generics.repository;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import sun.reflect.generics.factory.GenericsFactory;
/*     */ import sun.reflect.generics.parser.SignatureParser;
/*     */ import sun.reflect.generics.tree.FieldTypeSignature;
/*     */ import sun.reflect.generics.tree.MethodTypeSignature;
/*     */ import sun.reflect.generics.tree.Tree;
/*     */ import sun.reflect.generics.tree.TypeSignature;
/*     */ import sun.reflect.generics.visitor.Reifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConstructorRepository
/*     */   extends GenericDeclRepository<MethodTypeSignature>
/*     */ {
/*     */   private Type[] paramTypes;
/*     */   private Type[] exceptionTypes;
/*     */   
/*     */   protected ConstructorRepository(String paramString, GenericsFactory paramGenericsFactory) {
/*  51 */     super(paramString, paramGenericsFactory);
/*     */   }
/*     */   
/*     */   protected MethodTypeSignature parse(String paramString) {
/*  55 */     return SignatureParser.make().parseMethodSig(paramString);
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
/*     */   public static ConstructorRepository make(String paramString, GenericsFactory paramGenericsFactory) {
/*  69 */     return new ConstructorRepository(paramString, paramGenericsFactory);
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
/*     */   public Type[] getParameterTypes() {
/*  86 */     if (this.paramTypes == null) {
/*     */       
/*  88 */       TypeSignature[] arrayOfTypeSignature = getTree().getParameterTypes();
/*     */       
/*  90 */       Type[] arrayOfType = new Type[arrayOfTypeSignature.length];
/*     */       
/*  92 */       for (byte b = 0; b < arrayOfTypeSignature.length; b++) {
/*  93 */         Reifier reifier = getReifier();
/*  94 */         arrayOfTypeSignature[b].accept(reifier);
/*     */         
/*  96 */         arrayOfType[b] = reifier.getResult();
/*     */       } 
/*  98 */       this.paramTypes = arrayOfType;
/*     */     } 
/* 100 */     return (Type[])this.paramTypes.clone();
/*     */   }
/*     */   
/*     */   public Type[] getExceptionTypes() {
/* 104 */     if (this.exceptionTypes == null) {
/*     */       
/* 106 */       FieldTypeSignature[] arrayOfFieldTypeSignature = getTree().getExceptionTypes();
/*     */       
/* 108 */       Type[] arrayOfType = new Type[arrayOfFieldTypeSignature.length];
/*     */       
/* 110 */       for (byte b = 0; b < arrayOfFieldTypeSignature.length; b++) {
/* 111 */         Reifier reifier = getReifier();
/* 112 */         arrayOfFieldTypeSignature[b].accept(reifier);
/*     */         
/* 114 */         arrayOfType[b] = reifier.getResult();
/*     */       } 
/* 116 */       this.exceptionTypes = arrayOfType;
/*     */     } 
/* 118 */     return (Type[])this.exceptionTypes.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/repository/ConstructorRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */