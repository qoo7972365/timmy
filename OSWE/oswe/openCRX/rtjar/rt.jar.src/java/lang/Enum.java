/*     */ package java.lang;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Enum<E extends Enum<E>>
/*     */   implements Comparable<E>, Serializable
/*     */ {
/*     */   private final String name;
/*     */   private final int ordinal;
/*     */   
/*     */   public final String name() {
/*  77 */     return this.name;
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
/*     */   public final int ordinal() {
/* 103 */     return this.ordinal;
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
/*     */   protected Enum(String paramString, int paramInt) {
/* 118 */     this.name = paramString;
/* 119 */     this.ordinal = paramInt;
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
/*     */   public String toString() {
/* 131 */     return this.name;
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
/*     */   public final boolean equals(Object paramObject) {
/* 143 */     return (this == paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 152 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object clone() throws CloneNotSupportedException {
/* 163 */     throw new CloneNotSupportedException();
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
/*     */   public final int compareTo(E paramE) {
/* 176 */     E e = paramE;
/* 177 */     Enum<E> enum_ = this;
/* 178 */     if (enum_.getClass() != e.getClass() && enum_
/* 179 */       .getDeclaringClass() != e.getDeclaringClass())
/* 180 */       throw new ClassCastException(); 
/* 181 */     return enum_.ordinal - ((Enum)e).ordinal;
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
/*     */   public final Class<E> getDeclaringClass() {
/* 198 */     Class<?> clazz1 = getClass();
/* 199 */     Class<?> clazz2 = clazz1.getSuperclass();
/* 200 */     return (clazz2 == Enum.class) ? (Class)clazz1 : (Class)clazz2;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Enum<T>> T valueOf(Class<T> paramClass, String paramString) {
/* 232 */     Enum enum_ = (Enum)paramClass.enumConstantDirectory().get(paramString);
/* 233 */     if (enum_ != null)
/* 234 */       return (T)enum_; 
/* 235 */     if (paramString == null)
/* 236 */       throw new NullPointerException("Name is null"); 
/* 237 */     throw new IllegalArgumentException("No enum constant " + paramClass
/* 238 */         .getCanonicalName() + "." + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void finalize() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 251 */     throw new InvalidObjectException("can't deserialize enum");
/*     */   }
/*     */   
/*     */   private void readObjectNoData() throws ObjectStreamException {
/* 255 */     throw new InvalidObjectException("can't deserialize enum");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Enum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */