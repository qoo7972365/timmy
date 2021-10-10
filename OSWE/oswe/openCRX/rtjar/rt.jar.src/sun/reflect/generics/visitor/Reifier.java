/*     */ package sun.reflect.generics.visitor;
/*     */ 
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import sun.reflect.generics.factory.GenericsFactory;
/*     */ import sun.reflect.generics.tree.ArrayTypeSignature;
/*     */ import sun.reflect.generics.tree.BooleanSignature;
/*     */ import sun.reflect.generics.tree.BottomSignature;
/*     */ import sun.reflect.generics.tree.ByteSignature;
/*     */ import sun.reflect.generics.tree.CharSignature;
/*     */ import sun.reflect.generics.tree.ClassTypeSignature;
/*     */ import sun.reflect.generics.tree.DoubleSignature;
/*     */ import sun.reflect.generics.tree.FloatSignature;
/*     */ import sun.reflect.generics.tree.FormalTypeParameter;
/*     */ import sun.reflect.generics.tree.IntSignature;
/*     */ import sun.reflect.generics.tree.LongSignature;
/*     */ import sun.reflect.generics.tree.ShortSignature;
/*     */ import sun.reflect.generics.tree.SimpleClassTypeSignature;
/*     */ import sun.reflect.generics.tree.TypeArgument;
/*     */ import sun.reflect.generics.tree.TypeVariableSignature;
/*     */ import sun.reflect.generics.tree.VoidDescriptor;
/*     */ import sun.reflect.generics.tree.Wildcard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Reifier
/*     */   implements TypeTreeVisitor<Type>
/*     */ {
/*     */   private Type resultType;
/*     */   private GenericsFactory factory;
/*     */   
/*     */   private Reifier(GenericsFactory paramGenericsFactory) {
/*  45 */     this.factory = paramGenericsFactory;
/*     */   }
/*     */   private GenericsFactory getFactory() {
/*  48 */     return this.factory;
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
/*     */   public static Reifier make(GenericsFactory paramGenericsFactory) {
/*  60 */     return new Reifier(paramGenericsFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Type[] reifyTypeArguments(TypeArgument[] paramArrayOfTypeArgument) {
/*  66 */     Type[] arrayOfType = new Type[paramArrayOfTypeArgument.length];
/*  67 */     for (byte b = 0; b < paramArrayOfTypeArgument.length; b++) {
/*  68 */       paramArrayOfTypeArgument[b].accept(this);
/*  69 */       arrayOfType[b] = this.resultType;
/*     */     } 
/*  71 */     return arrayOfType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getResult() {
/*  80 */     assert this.resultType != null; return this.resultType;
/*     */   }
/*     */   public void visitFormalTypeParameter(FormalTypeParameter paramFormalTypeParameter) {
/*  83 */     this.resultType = getFactory().makeTypeVariable(paramFormalTypeParameter.getName(), paramFormalTypeParameter
/*  84 */         .getBounds());
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
/*     */   public void visitClassTypeSignature(ClassTypeSignature paramClassTypeSignature) {
/* 106 */     List<SimpleClassTypeSignature> list = paramClassTypeSignature.getPath();
/* 107 */     assert !list.isEmpty();
/* 108 */     Iterator<SimpleClassTypeSignature> iterator = list.iterator();
/* 109 */     SimpleClassTypeSignature simpleClassTypeSignature = iterator.next();
/* 110 */     StringBuilder stringBuilder = new StringBuilder(simpleClassTypeSignature.getName());
/* 111 */     boolean bool = simpleClassTypeSignature.getDollar();
/*     */ 
/*     */ 
/*     */     
/* 115 */     while (iterator.hasNext() && (simpleClassTypeSignature.getTypeArguments()).length == 0) {
/* 116 */       simpleClassTypeSignature = iterator.next();
/* 117 */       bool = simpleClassTypeSignature.getDollar();
/* 118 */       stringBuilder.append(bool ? "$" : ".").append(simpleClassTypeSignature.getName());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 123 */     assert !iterator.hasNext() || (simpleClassTypeSignature.getTypeArguments()).length > 0;
/*     */     
/* 125 */     Type type = getFactory().makeNamedType(stringBuilder.toString());
/*     */     
/* 127 */     if ((simpleClassTypeSignature.getTypeArguments()).length == 0) {
/*     */       
/* 129 */       assert !iterator.hasNext();
/* 130 */       this.resultType = type;
/*     */     } else {
/* 132 */       assert (simpleClassTypeSignature.getTypeArguments()).length > 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 138 */       Type[] arrayOfType = reifyTypeArguments(simpleClassTypeSignature.getTypeArguments());
/*     */       
/* 140 */       ParameterizedType parameterizedType = getFactory().makeParameterizedType(type, arrayOfType, null);
/*     */       
/* 142 */       bool = false;
/* 143 */       while (iterator.hasNext()) {
/* 144 */         simpleClassTypeSignature = iterator.next();
/* 145 */         bool = simpleClassTypeSignature.getDollar();
/* 146 */         stringBuilder.append(bool ? "$" : ".").append(simpleClassTypeSignature.getName());
/* 147 */         type = getFactory().makeNamedType(stringBuilder.toString());
/* 148 */         arrayOfType = reifyTypeArguments(simpleClassTypeSignature.getTypeArguments());
/*     */ 
/*     */         
/* 151 */         parameterizedType = getFactory().makeParameterizedType(type, arrayOfType, parameterizedType);
/*     */       } 
/* 153 */       this.resultType = parameterizedType;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitArrayTypeSignature(ArrayTypeSignature paramArrayTypeSignature) {
/* 159 */     paramArrayTypeSignature.getComponentType().accept(this);
/* 160 */     Type type = this.resultType;
/* 161 */     this.resultType = getFactory().makeArrayType(type);
/*     */   }
/*     */   
/*     */   public void visitTypeVariableSignature(TypeVariableSignature paramTypeVariableSignature) {
/* 165 */     this.resultType = getFactory().findTypeVariable(paramTypeVariableSignature.getIdentifier());
/*     */   }
/*     */   
/*     */   public void visitWildcard(Wildcard paramWildcard) {
/* 169 */     this.resultType = getFactory().makeWildcard(paramWildcard.getUpperBounds(), paramWildcard
/* 170 */         .getLowerBounds());
/*     */   }
/*     */   
/*     */   public void visitSimpleClassTypeSignature(SimpleClassTypeSignature paramSimpleClassTypeSignature) {
/* 174 */     this.resultType = getFactory().makeNamedType(paramSimpleClassTypeSignature.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitBottomSignature(BottomSignature paramBottomSignature) {}
/*     */ 
/*     */   
/*     */   public void visitByteSignature(ByteSignature paramByteSignature) {
/* 182 */     this.resultType = getFactory().makeByte();
/*     */   }
/*     */   
/*     */   public void visitBooleanSignature(BooleanSignature paramBooleanSignature) {
/* 186 */     this.resultType = getFactory().makeBool();
/*     */   }
/*     */   
/*     */   public void visitShortSignature(ShortSignature paramShortSignature) {
/* 190 */     this.resultType = getFactory().makeShort();
/*     */   }
/*     */   
/*     */   public void visitCharSignature(CharSignature paramCharSignature) {
/* 194 */     this.resultType = getFactory().makeChar();
/*     */   }
/*     */   
/*     */   public void visitIntSignature(IntSignature paramIntSignature) {
/* 198 */     this.resultType = getFactory().makeInt();
/*     */   }
/*     */   
/*     */   public void visitLongSignature(LongSignature paramLongSignature) {
/* 202 */     this.resultType = getFactory().makeLong();
/*     */   }
/*     */   
/*     */   public void visitFloatSignature(FloatSignature paramFloatSignature) {
/* 206 */     this.resultType = getFactory().makeFloat();
/*     */   }
/*     */   
/*     */   public void visitDoubleSignature(DoubleSignature paramDoubleSignature) {
/* 210 */     this.resultType = getFactory().makeDouble();
/*     */   }
/*     */   
/*     */   public void visitVoidDescriptor(VoidDescriptor paramVoidDescriptor) {
/* 214 */     this.resultType = getFactory().makeVoid();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/visitor/Reifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */