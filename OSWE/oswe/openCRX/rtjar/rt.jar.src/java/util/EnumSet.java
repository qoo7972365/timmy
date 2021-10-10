/*     */ package java.util;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EnumSet<E extends Enum<E>>
/*     */   extends AbstractSet<E>
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   final Class<E> elementType;
/*     */   final Enum<?>[] universe;
/*  93 */   private static Enum<?>[] ZERO_LENGTH_ENUM_ARRAY = (Enum<?>[])new Enum[0];
/*     */   
/*     */   EnumSet(Class<E> paramClass, Enum<?>[] paramArrayOfEnum) {
/*  96 */     this.elementType = paramClass;
/*  97 */     this.universe = paramArrayOfEnum;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> paramClass) {
/* 110 */     Object[] arrayOfObject = getUniverse((Class)paramClass);
/* 111 */     if (arrayOfObject == null) {
/* 112 */       throw new ClassCastException(paramClass + " not an enum");
/*     */     }
/* 114 */     if (arrayOfObject.length <= 64) {
/* 115 */       return new RegularEnumSet<>(paramClass, (Enum<?>[])arrayOfObject);
/*     */     }
/* 117 */     return new JumboEnumSet<>(paramClass, (Enum<?>[])arrayOfObject);
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
/*     */   public static <E extends Enum<E>> EnumSet<E> allOf(Class<E> paramClass) {
/* 131 */     EnumSet<E> enumSet = noneOf(paramClass);
/* 132 */     enumSet.addAll();
/* 133 */     return enumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> copyOf(EnumSet<E> paramEnumSet) {
/* 152 */     return paramEnumSet.clone();
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
/*     */   public static <E extends Enum<E>> EnumSet<E> copyOf(Collection<E> paramCollection) {
/* 170 */     if (paramCollection instanceof EnumSet) {
/* 171 */       return ((EnumSet<E>)paramCollection).clone();
/*     */     }
/* 173 */     if (paramCollection.isEmpty())
/* 174 */       throw new IllegalArgumentException("Collection is empty"); 
/* 175 */     Iterator<E> iterator = paramCollection.iterator();
/* 176 */     Enum enum_ = (Enum)iterator.next();
/* 177 */     EnumSet<Enum> enumSet = of(enum_);
/* 178 */     while (iterator.hasNext())
/* 179 */       enumSet.add((Enum)iterator.next()); 
/* 180 */     return (EnumSet)enumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> complementOf(EnumSet<E> paramEnumSet) {
/* 195 */     EnumSet<E> enumSet = copyOf(paramEnumSet);
/* 196 */     enumSet.complement();
/* 197 */     return enumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE) {
/* 215 */     EnumSet<Enum> enumSet = noneOf(paramE.getDeclaringClass());
/* 216 */     enumSet.add((Enum)paramE);
/* 217 */     return (EnumSet)enumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE1, E paramE2) {
/* 236 */     EnumSet<Enum> enumSet = noneOf(paramE1.getDeclaringClass());
/* 237 */     enumSet.add((Enum)paramE1);
/* 238 */     enumSet.add((Enum)paramE2);
/* 239 */     return (EnumSet)enumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE1, E paramE2, E paramE3) {
/* 259 */     EnumSet<Enum> enumSet = noneOf(paramE1.getDeclaringClass());
/* 260 */     enumSet.add((Enum)paramE1);
/* 261 */     enumSet.add((Enum)paramE2);
/* 262 */     enumSet.add((Enum)paramE3);
/* 263 */     return (EnumSet)enumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE1, E paramE2, E paramE3, E paramE4) {
/* 284 */     EnumSet<Enum> enumSet = noneOf(paramE1.getDeclaringClass());
/* 285 */     enumSet.add((Enum)paramE1);
/* 286 */     enumSet.add((Enum)paramE2);
/* 287 */     enumSet.add((Enum)paramE3);
/* 288 */     enumSet.add((Enum)paramE4);
/* 289 */     return (EnumSet)enumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE1, E paramE2, E paramE3, E paramE4, E paramE5) {
/* 313 */     EnumSet<Enum> enumSet = noneOf(paramE1.getDeclaringClass());
/* 314 */     enumSet.add((Enum)paramE1);
/* 315 */     enumSet.add((Enum)paramE2);
/* 316 */     enumSet.add((Enum)paramE3);
/* 317 */     enumSet.add((Enum)paramE4);
/* 318 */     enumSet.add((Enum)paramE5);
/* 319 */     return (EnumSet)enumSet;
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
/*     */   @SafeVarargs
/*     */   public static <E extends Enum<E>> EnumSet<E> of(E paramE, E... paramVarArgs) {
/* 338 */     EnumSet<Enum> enumSet = noneOf(paramE.getDeclaringClass());
/* 339 */     enumSet.add((Enum)paramE);
/* 340 */     for (E e : paramVarArgs)
/* 341 */       enumSet.add((Enum)e); 
/* 342 */     return (EnumSet)enumSet;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> range(E paramE1, E paramE2) {
/* 360 */     if (paramE1.compareTo(paramE2) > 0)
/* 361 */       throw new IllegalArgumentException((new StringBuilder()).append(paramE1).append(" > ").append(paramE2).toString()); 
/* 362 */     EnumSet<Enum> enumSet = noneOf(paramE1.getDeclaringClass());
/* 363 */     enumSet.addRange((Enum)paramE1, (Enum)paramE2);
/* 364 */     return (EnumSet)enumSet;
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
/*     */   public EnumSet<E> clone() {
/*     */     try {
/* 381 */       return (EnumSet<E>)super.clone();
/* 382 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 383 */       throw new AssertionError(cloneNotSupportedException);
/*     */     } 
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
/*     */   final void typeCheck(E paramE) {
/* 396 */     Class<?> clazz = paramE.getClass();
/* 397 */     if (clazz != this.elementType && clazz.getSuperclass() != this.elementType) {
/* 398 */       throw new ClassCastException(clazz + " != " + this.elementType);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <E extends Enum<E>> E[] getUniverse(Class<E> paramClass) {
/* 406 */     return (E[])SharedSecrets.getJavaLangAccess()
/* 407 */       .getEnumConstantsShared(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SerializationProxy<E extends Enum<E>>
/*     */     implements Serializable
/*     */   {
/*     */     private final Class<E> elementType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Enum<?>[] elements;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 362491234563181265L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SerializationProxy(EnumSet<E> param1EnumSet) {
/* 437 */       this.elementType = param1EnumSet.elementType;
/* 438 */       this.elements = (Enum<?>[])param1EnumSet.toArray((Object[])EnumSet.ZERO_LENGTH_ENUM_ARRAY);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 445 */       EnumSet<E> enumSet = EnumSet.noneOf(this.elementType);
/* 446 */       for (Enum<?> enum_ : this.elements)
/* 447 */         enumSet.add((E)enum_); 
/* 448 */       return enumSet;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Object writeReplace() {
/* 455 */     return new SerializationProxy<>(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 462 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */   
/*     */   abstract void addAll();
/*     */   
/*     */   abstract void addRange(E paramE1, E paramE2);
/*     */   
/*     */   abstract void complement();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/EnumSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */