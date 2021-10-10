/*    */ package sun.reflect.generics.reflectiveObjects;
/*    */ 
/*    */ import java.lang.reflect.GenericArrayType;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenericArrayTypeImpl
/*    */   implements GenericArrayType
/*    */ {
/*    */   private final Type genericComponentType;
/*    */   
/*    */   private GenericArrayTypeImpl(Type paramType) {
/* 41 */     this.genericComponentType = paramType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static GenericArrayTypeImpl make(Type paramType) {
/* 51 */     return new GenericArrayTypeImpl(paramType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Type getGenericComponentType() {
/* 64 */     return this.genericComponentType;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 68 */     Type type = getGenericComponentType();
/* 69 */     StringBuilder stringBuilder = new StringBuilder();
/*    */     
/* 71 */     if (type instanceof Class) {
/* 72 */       stringBuilder.append(((Class)type).getName());
/*    */     } else {
/* 74 */       stringBuilder.append(type.toString());
/* 75 */     }  stringBuilder.append("[]");
/* 76 */     return stringBuilder.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 81 */     if (paramObject instanceof GenericArrayType) {
/* 82 */       GenericArrayType genericArrayType = (GenericArrayType)paramObject;
/*    */       
/* 84 */       return Objects.equals(this.genericComponentType, genericArrayType.getGenericComponentType());
/*    */     } 
/* 86 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 91 */     return Objects.hashCode(this.genericComponentType);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/reflectiveObjects/GenericArrayTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */