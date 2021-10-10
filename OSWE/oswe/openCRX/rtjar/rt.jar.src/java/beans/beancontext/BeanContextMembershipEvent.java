/*     */ package java.beans.beancontext;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanContextMembershipEvent
/*     */   extends BeanContextEvent
/*     */ {
/*     */   private static final long serialVersionUID = 3499346510334590959L;
/*     */   protected Collection children;
/*     */   
/*     */   public BeanContextMembershipEvent(BeanContext paramBeanContext, Collection paramCollection) {
/*  70 */     super(paramBeanContext);
/*     */     
/*  72 */     if (paramCollection == null) throw new NullPointerException("BeanContextMembershipEvent constructor:  changes is null.");
/*     */ 
/*     */     
/*  75 */     this.children = paramCollection;
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
/*     */   public BeanContextMembershipEvent(BeanContext paramBeanContext, Object[] paramArrayOfObject) {
/*  88 */     super(paramBeanContext);
/*     */     
/*  90 */     if (paramArrayOfObject == null) throw new NullPointerException("BeanContextMembershipEvent:  changes is null.");
/*     */ 
/*     */     
/*  93 */     this.children = Arrays.asList(paramArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 100 */     return this.children.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Object paramObject) {
/* 109 */     return this.children.contains(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 116 */     return this.children.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/* 123 */     return this.children.iterator();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/beancontext/BeanContextMembershipEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */