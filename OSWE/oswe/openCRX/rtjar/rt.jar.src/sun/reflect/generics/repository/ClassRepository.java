/*     */ package sun.reflect.generics.repository;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import sun.reflect.generics.factory.GenericsFactory;
/*     */ import sun.reflect.generics.parser.SignatureParser;
/*     */ import sun.reflect.generics.tree.ClassSignature;
/*     */ import sun.reflect.generics.tree.ClassTypeSignature;
/*     */ import sun.reflect.generics.tree.Tree;
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
/*     */ public class ClassRepository
/*     */   extends GenericDeclRepository<ClassSignature>
/*     */ {
/*  43 */   public static final ClassRepository NONE = make("Ljava/lang/Object;", (GenericsFactory)null);
/*     */ 
/*     */   
/*     */   private volatile Type superclass;
/*     */ 
/*     */   
/*     */   private volatile Type[] superInterfaces;
/*     */ 
/*     */   
/*     */   private ClassRepository(String paramString, GenericsFactory paramGenericsFactory) {
/*  53 */     super(paramString, paramGenericsFactory);
/*     */   }
/*     */   
/*     */   protected ClassSignature parse(String paramString) {
/*  57 */     return SignatureParser.make().parseClassSig(paramString);
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
/*     */   public static ClassRepository make(String paramString, GenericsFactory paramGenericsFactory) {
/*  70 */     return new ClassRepository(paramString, paramGenericsFactory);
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
/*     */   public Type getSuperclass() {
/*  86 */     Type type = this.superclass;
/*  87 */     if (type == null) {
/*  88 */       Reifier reifier = getReifier();
/*     */       
/*  90 */       getTree().getSuperclass().accept(reifier);
/*     */       
/*  92 */       type = reifier.getResult();
/*  93 */       this.superclass = type;
/*     */     } 
/*  95 */     return type;
/*     */   }
/*     */   
/*     */   public Type[] getSuperInterfaces() {
/*  99 */     Type[] arrayOfType = this.superInterfaces;
/* 100 */     if (arrayOfType == null) {
/*     */       
/* 102 */       ClassTypeSignature[] arrayOfClassTypeSignature = getTree().getSuperInterfaces();
/*     */       
/* 104 */       arrayOfType = new Type[arrayOfClassTypeSignature.length];
/*     */       
/* 106 */       for (byte b = 0; b < arrayOfClassTypeSignature.length; b++) {
/* 107 */         Reifier reifier = getReifier();
/* 108 */         arrayOfClassTypeSignature[b].accept(reifier);
/*     */         
/* 110 */         arrayOfType[b] = reifier.getResult();
/*     */       } 
/* 112 */       this.superInterfaces = arrayOfType;
/*     */     } 
/* 114 */     return (Type[])arrayOfType.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/repository/ClassRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */