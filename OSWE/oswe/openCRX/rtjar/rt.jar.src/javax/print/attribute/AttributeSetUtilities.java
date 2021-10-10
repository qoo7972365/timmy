/*     */ package javax.print.attribute;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AttributeSetUtilities
/*     */ {
/*     */   private static class UnmodifiableAttributeSet
/*     */     implements AttributeSet, Serializable
/*     */   {
/*     */     private AttributeSet attrset;
/*     */     
/*     */     public UnmodifiableAttributeSet(AttributeSet param1AttributeSet) {
/*  87 */       this.attrset = param1AttributeSet;
/*     */     }
/*     */     
/*     */     public Attribute get(Class<?> param1Class) {
/*  91 */       return this.attrset.get(param1Class);
/*     */     }
/*     */     
/*     */     public boolean add(Attribute param1Attribute) {
/*  95 */       throw new UnmodifiableSetException();
/*     */     }
/*     */     
/*     */     public synchronized boolean remove(Class<?> param1Class) {
/*  99 */       throw new UnmodifiableSetException();
/*     */     }
/*     */     
/*     */     public boolean remove(Attribute param1Attribute) {
/* 103 */       throw new UnmodifiableSetException();
/*     */     }
/*     */     
/*     */     public boolean containsKey(Class<?> param1Class) {
/* 107 */       return this.attrset.containsKey(param1Class);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Attribute param1Attribute) {
/* 111 */       return this.attrset.containsValue(param1Attribute);
/*     */     }
/*     */     
/*     */     public boolean addAll(AttributeSet param1AttributeSet) {
/* 115 */       throw new UnmodifiableSetException();
/*     */     }
/*     */     
/*     */     public int size() {
/* 119 */       return this.attrset.size();
/*     */     }
/*     */     
/*     */     public Attribute[] toArray() {
/* 123 */       return this.attrset.toArray();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 127 */       throw new UnmodifiableSetException();
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 131 */       return this.attrset.isEmpty();
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 135 */       return this.attrset.equals(param1Object);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 139 */       return this.attrset.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class UnmodifiableDocAttributeSet
/*     */     extends UnmodifiableAttributeSet
/*     */     implements DocAttributeSet, Serializable
/*     */   {
/*     */     public UnmodifiableDocAttributeSet(DocAttributeSet param1DocAttributeSet) {
/* 153 */       super(param1DocAttributeSet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class UnmodifiablePrintRequestAttributeSet
/*     */     extends UnmodifiableAttributeSet
/*     */     implements PrintRequestAttributeSet, Serializable
/*     */   {
/*     */     public UnmodifiablePrintRequestAttributeSet(PrintRequestAttributeSet param1PrintRequestAttributeSet) {
/* 167 */       super(param1PrintRequestAttributeSet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class UnmodifiablePrintJobAttributeSet
/*     */     extends UnmodifiableAttributeSet
/*     */     implements PrintJobAttributeSet, Serializable
/*     */   {
/*     */     public UnmodifiablePrintJobAttributeSet(PrintJobAttributeSet param1PrintJobAttributeSet) {
/* 181 */       super(param1PrintJobAttributeSet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class UnmodifiablePrintServiceAttributeSet
/*     */     extends UnmodifiableAttributeSet
/*     */     implements PrintServiceAttributeSet, Serializable
/*     */   {
/*     */     public UnmodifiablePrintServiceAttributeSet(PrintServiceAttributeSet param1PrintServiceAttributeSet) {
/* 195 */       super(param1PrintServiceAttributeSet);
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
/*     */ 
/*     */   
/*     */   public static AttributeSet unmodifiableView(AttributeSet paramAttributeSet) {
/* 210 */     if (paramAttributeSet == null) {
/* 211 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 214 */     return new UnmodifiableAttributeSet(paramAttributeSet);
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
/*     */   public static DocAttributeSet unmodifiableView(DocAttributeSet paramDocAttributeSet) {
/* 229 */     if (paramDocAttributeSet == null) {
/* 230 */       throw new NullPointerException();
/*     */     }
/* 232 */     return new UnmodifiableDocAttributeSet(paramDocAttributeSet);
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
/*     */   public static PrintRequestAttributeSet unmodifiableView(PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 247 */     if (paramPrintRequestAttributeSet == null) {
/* 248 */       throw new NullPointerException();
/*     */     }
/* 250 */     return new UnmodifiablePrintRequestAttributeSet(paramPrintRequestAttributeSet);
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
/*     */   public static PrintJobAttributeSet unmodifiableView(PrintJobAttributeSet paramPrintJobAttributeSet) {
/* 265 */     if (paramPrintJobAttributeSet == null) {
/* 266 */       throw new NullPointerException();
/*     */     }
/* 268 */     return new UnmodifiablePrintJobAttributeSet(paramPrintJobAttributeSet);
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
/*     */   public static PrintServiceAttributeSet unmodifiableView(PrintServiceAttributeSet paramPrintServiceAttributeSet) {
/* 283 */     if (paramPrintServiceAttributeSet == null) {
/* 284 */       throw new NullPointerException();
/*     */     }
/* 286 */     return new UnmodifiablePrintServiceAttributeSet(paramPrintServiceAttributeSet);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SynchronizedAttributeSet
/*     */     implements AttributeSet, Serializable
/*     */   {
/*     */     private AttributeSet attrset;
/*     */ 
/*     */     
/*     */     public SynchronizedAttributeSet(AttributeSet param1AttributeSet) {
/* 298 */       this.attrset = param1AttributeSet;
/*     */     }
/*     */     
/*     */     public synchronized Attribute get(Class<?> param1Class) {
/* 302 */       return this.attrset.get(param1Class);
/*     */     }
/*     */     
/*     */     public synchronized boolean add(Attribute param1Attribute) {
/* 306 */       return this.attrset.add(param1Attribute);
/*     */     }
/*     */     
/*     */     public synchronized boolean remove(Class<?> param1Class) {
/* 310 */       return this.attrset.remove(param1Class);
/*     */     }
/*     */     
/*     */     public synchronized boolean remove(Attribute param1Attribute) {
/* 314 */       return this.attrset.remove(param1Attribute);
/*     */     }
/*     */     
/*     */     public synchronized boolean containsKey(Class<?> param1Class) {
/* 318 */       return this.attrset.containsKey(param1Class);
/*     */     }
/*     */     
/*     */     public synchronized boolean containsValue(Attribute param1Attribute) {
/* 322 */       return this.attrset.containsValue(param1Attribute);
/*     */     }
/*     */     
/*     */     public synchronized boolean addAll(AttributeSet param1AttributeSet) {
/* 326 */       return this.attrset.addAll(param1AttributeSet);
/*     */     }
/*     */     
/*     */     public synchronized int size() {
/* 330 */       return this.attrset.size();
/*     */     }
/*     */     
/*     */     public synchronized Attribute[] toArray() {
/* 334 */       return this.attrset.toArray();
/*     */     }
/*     */     
/*     */     public synchronized void clear() {
/* 338 */       this.attrset.clear();
/*     */     }
/*     */     
/*     */     public synchronized boolean isEmpty() {
/* 342 */       return this.attrset.isEmpty();
/*     */     }
/*     */     
/*     */     public synchronized boolean equals(Object param1Object) {
/* 346 */       return this.attrset.equals(param1Object);
/*     */     }
/*     */     
/*     */     public synchronized int hashCode() {
/* 350 */       return this.attrset.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SynchronizedDocAttributeSet
/*     */     extends SynchronizedAttributeSet
/*     */     implements DocAttributeSet, Serializable
/*     */   {
/*     */     public SynchronizedDocAttributeSet(DocAttributeSet param1DocAttributeSet) {
/* 362 */       super(param1DocAttributeSet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SynchronizedPrintRequestAttributeSet
/*     */     extends SynchronizedAttributeSet
/*     */     implements PrintRequestAttributeSet, Serializable
/*     */   {
/*     */     public SynchronizedPrintRequestAttributeSet(PrintRequestAttributeSet param1PrintRequestAttributeSet) {
/* 375 */       super(param1PrintRequestAttributeSet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SynchronizedPrintJobAttributeSet
/*     */     extends SynchronizedAttributeSet
/*     */     implements PrintJobAttributeSet, Serializable
/*     */   {
/*     */     public SynchronizedPrintJobAttributeSet(PrintJobAttributeSet param1PrintJobAttributeSet) {
/* 388 */       super(param1PrintJobAttributeSet);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SynchronizedPrintServiceAttributeSet
/*     */     extends SynchronizedAttributeSet
/*     */     implements PrintServiceAttributeSet, Serializable
/*     */   {
/*     */     public SynchronizedPrintServiceAttributeSet(PrintServiceAttributeSet param1PrintServiceAttributeSet) {
/* 400 */       super(param1PrintServiceAttributeSet);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttributeSet synchronizedView(AttributeSet paramAttributeSet) {
/* 416 */     if (paramAttributeSet == null) {
/* 417 */       throw new NullPointerException();
/*     */     }
/* 419 */     return new SynchronizedAttributeSet(paramAttributeSet);
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
/*     */   public static DocAttributeSet synchronizedView(DocAttributeSet paramDocAttributeSet) {
/* 434 */     if (paramDocAttributeSet == null) {
/* 435 */       throw new NullPointerException();
/*     */     }
/* 437 */     return new SynchronizedDocAttributeSet(paramDocAttributeSet);
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
/*     */   public static PrintRequestAttributeSet synchronizedView(PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/* 452 */     if (paramPrintRequestAttributeSet == null) {
/* 453 */       throw new NullPointerException();
/*     */     }
/* 455 */     return new SynchronizedPrintRequestAttributeSet(paramPrintRequestAttributeSet);
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
/*     */   public static PrintJobAttributeSet synchronizedView(PrintJobAttributeSet paramPrintJobAttributeSet) {
/* 470 */     if (paramPrintJobAttributeSet == null) {
/* 471 */       throw new NullPointerException();
/*     */     }
/* 473 */     return new SynchronizedPrintJobAttributeSet(paramPrintJobAttributeSet);
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
/*     */   public static PrintServiceAttributeSet synchronizedView(PrintServiceAttributeSet paramPrintServiceAttributeSet) {
/* 485 */     if (paramPrintServiceAttributeSet == null) {
/* 486 */       throw new NullPointerException();
/*     */     }
/* 488 */     return new SynchronizedPrintServiceAttributeSet(paramPrintServiceAttributeSet);
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
/*     */   public static Class<?> verifyAttributeCategory(Object paramObject, Class<?> paramClass) {
/* 515 */     Class<?> clazz = (Class)paramObject;
/* 516 */     if (paramClass.isAssignableFrom(clazz)) {
/* 517 */       return clazz;
/*     */     }
/*     */     
/* 520 */     throw new ClassCastException();
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
/*     */   public static Attribute verifyAttributeValue(Object paramObject, Class<?> paramClass) {
/* 546 */     if (paramObject == null) {
/* 547 */       throw new NullPointerException();
/*     */     }
/* 549 */     if (paramClass.isInstance(paramObject)) {
/* 550 */       return (Attribute)paramObject;
/*     */     }
/* 552 */     throw new ClassCastException();
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
/*     */   public static void verifyCategoryForValue(Class<?> paramClass, Attribute paramAttribute) {
/* 574 */     if (!paramClass.equals(paramAttribute.getCategory()))
/* 575 */       throw new IllegalArgumentException(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/AttributeSetUtilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */