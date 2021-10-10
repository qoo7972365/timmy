/*     */ package sun.reflect.generics.reflectiveObjects;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.Arrays;
/*     */ import sun.reflect.generics.factory.GenericsFactory;
/*     */ import sun.reflect.generics.tree.FieldTypeSignature;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WildcardTypeImpl
/*     */   extends LazyReflectiveObjectGenerator
/*     */   implements WildcardType
/*     */ {
/*     */   private Type[] upperBounds;
/*     */   private Type[] lowerBounds;
/*     */   private FieldTypeSignature[] upperBoundASTs;
/*     */   private FieldTypeSignature[] lowerBoundASTs;
/*     */   
/*     */   private WildcardTypeImpl(FieldTypeSignature[] paramArrayOfFieldTypeSignature1, FieldTypeSignature[] paramArrayOfFieldTypeSignature2, GenericsFactory paramGenericsFactory) {
/*  58 */     super(paramGenericsFactory);
/*  59 */     this.upperBoundASTs = paramArrayOfFieldTypeSignature1;
/*  60 */     this.lowerBoundASTs = paramArrayOfFieldTypeSignature2;
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
/*     */   public static WildcardTypeImpl make(FieldTypeSignature[] paramArrayOfFieldTypeSignature1, FieldTypeSignature[] paramArrayOfFieldTypeSignature2, GenericsFactory paramGenericsFactory) {
/*  76 */     return new WildcardTypeImpl(paramArrayOfFieldTypeSignature1, paramArrayOfFieldTypeSignature2, paramGenericsFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FieldTypeSignature[] getUpperBoundASTs() {
/*  86 */     assert this.upperBounds == null;
/*  87 */     return this.upperBoundASTs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FieldTypeSignature[] getLowerBoundASTs() {
/*  94 */     assert this.lowerBounds == null;
/*  95 */     return this.lowerBoundASTs;
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
/*     */   public Type[] getUpperBounds() {
/* 121 */     if (this.upperBounds == null) {
/* 122 */       FieldTypeSignature[] arrayOfFieldTypeSignature = getUpperBoundASTs();
/*     */ 
/*     */ 
/*     */       
/* 126 */       Type[] arrayOfType = new Type[arrayOfFieldTypeSignature.length];
/*     */       
/* 128 */       for (byte b = 0; b < arrayOfFieldTypeSignature.length; b++) {
/* 129 */         Reifier reifier = getReifier();
/* 130 */         arrayOfFieldTypeSignature[b].accept(reifier);
/* 131 */         arrayOfType[b] = reifier.getResult();
/*     */       } 
/*     */       
/* 134 */       this.upperBounds = arrayOfType;
/*     */     } 
/*     */     
/* 137 */     return (Type[])this.upperBounds.clone();
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
/*     */   public Type[] getLowerBounds() {
/* 164 */     if (this.lowerBounds == null) {
/* 165 */       FieldTypeSignature[] arrayOfFieldTypeSignature = getLowerBoundASTs();
/*     */ 
/*     */       
/* 168 */       Type[] arrayOfType = new Type[arrayOfFieldTypeSignature.length];
/*     */       
/* 170 */       for (byte b = 0; b < arrayOfFieldTypeSignature.length; b++) {
/* 171 */         Reifier reifier = getReifier();
/* 172 */         arrayOfFieldTypeSignature[b].accept(reifier);
/* 173 */         arrayOfType[b] = reifier.getResult();
/*     */       } 
/*     */       
/* 176 */       this.lowerBounds = arrayOfType;
/*     */     } 
/*     */     
/* 179 */     return (Type[])this.lowerBounds.clone();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 183 */     Type[] arrayOfType1 = getLowerBounds();
/* 184 */     Type[] arrayOfType2 = arrayOfType1;
/* 185 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 187 */     if (arrayOfType1.length > 0) {
/* 188 */       stringBuilder.append("? super ");
/*     */     } else {
/* 190 */       Type[] arrayOfType = getUpperBounds();
/* 191 */       if (arrayOfType.length > 0 && !arrayOfType[0].equals(Object.class)) {
/* 192 */         arrayOfType2 = arrayOfType;
/* 193 */         stringBuilder.append("? extends ");
/*     */       } else {
/* 195 */         return "?";
/*     */       } 
/*     */     } 
/* 198 */     assert arrayOfType2.length > 0;
/*     */     
/* 200 */     boolean bool = true;
/* 201 */     for (Type type : arrayOfType2) {
/* 202 */       if (!bool) {
/* 203 */         stringBuilder.append(" & ");
/*     */       }
/* 205 */       bool = false;
/* 206 */       stringBuilder.append(type.getTypeName());
/*     */     } 
/* 208 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 213 */     if (paramObject instanceof WildcardType) {
/* 214 */       WildcardType wildcardType = (WildcardType)paramObject;
/* 215 */       return (
/* 216 */         Arrays.equals((Object[])getLowerBounds(), (Object[])wildcardType
/* 217 */           .getLowerBounds()) && 
/* 218 */         Arrays.equals((Object[])getUpperBounds(), (Object[])wildcardType
/* 219 */           .getUpperBounds()));
/*     */     } 
/* 221 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 226 */     Type[] arrayOfType1 = getLowerBounds();
/* 227 */     Type[] arrayOfType2 = getUpperBounds();
/*     */     
/* 229 */     return Arrays.hashCode((Object[])arrayOfType1) ^ Arrays.hashCode((Object[])arrayOfType2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/reflectiveObjects/WildcardTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */