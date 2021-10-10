/*     */ package java.beans;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanDescriptor
/*     */   extends FeatureDescriptor
/*     */ {
/*     */   private Reference<? extends Class<?>> beanClassRef;
/*     */   private Reference<? extends Class<?>> customizerClassRef;
/*     */   
/*     */   public BeanDescriptor(Class<?> paramClass) {
/*  50 */     this(paramClass, (Class<?>)null);
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
/*     */   public BeanDescriptor(Class<?> paramClass1, Class<?> paramClass2) {
/*  62 */     this.beanClassRef = getWeakReference(paramClass1);
/*  63 */     this.customizerClassRef = getWeakReference(paramClass2);
/*     */     
/*  65 */     String str = paramClass1.getName();
/*  66 */     while (str.indexOf('.') >= 0) {
/*  67 */       str = str.substring(str.indexOf('.') + 1);
/*     */     }
/*  69 */     setName(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getBeanClass() {
/*  78 */     return (this.beanClassRef != null) ? this.beanClassRef
/*  79 */       .get() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getCustomizerClass() {
/*  90 */     return (this.customizerClassRef != null) ? this.customizerClassRef
/*  91 */       .get() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BeanDescriptor(BeanDescriptor paramBeanDescriptor) {
/* 100 */     super(paramBeanDescriptor);
/* 101 */     this.beanClassRef = paramBeanDescriptor.beanClassRef;
/* 102 */     this.customizerClassRef = paramBeanDescriptor.customizerClassRef;
/*     */   }
/*     */   
/*     */   void appendTo(StringBuilder paramStringBuilder) {
/* 106 */     appendTo(paramStringBuilder, "beanClass", this.beanClassRef);
/* 107 */     appendTo(paramStringBuilder, "customizerClass", this.customizerClassRef);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/BeanDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */