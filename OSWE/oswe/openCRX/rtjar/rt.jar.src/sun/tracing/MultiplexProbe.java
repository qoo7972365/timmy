/*     */ package sun.tracing;
/*     */ 
/*     */ import com.sun.tracing.Probe;
/*     */ import com.sun.tracing.Provider;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MultiplexProbe
/*     */   extends ProbeSkeleton
/*     */ {
/*     */   private Set<Probe> probes;
/*     */   
/*     */   MultiplexProbe(Method paramMethod, Set<Provider> paramSet) {
/*  98 */     super(paramMethod.getParameterTypes());
/*  99 */     this.probes = new HashSet<>();
/* 100 */     for (Provider provider : paramSet) {
/* 101 */       Probe probe = provider.getProbe(paramMethod);
/* 102 */       if (probe != null) {
/* 103 */         this.probes.add(probe);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 109 */     for (Probe probe : this.probes) {
/* 110 */       if (probe.isEnabled()) {
/* 111 */         return true;
/*     */       }
/*     */     } 
/* 114 */     return false;
/*     */   }
/*     */   
/*     */   public void uncheckedTrigger(Object[] paramArrayOfObject) {
/* 118 */     for (Probe probe : this.probes) {
/*     */       
/*     */       try {
/* 121 */         ProbeSkeleton probeSkeleton = (ProbeSkeleton)probe;
/* 122 */         probeSkeleton.uncheckedTrigger(paramArrayOfObject);
/* 123 */       } catch (ClassCastException classCastException) {
/*     */ 
/*     */         
/*     */         try {
/* 127 */           Method method = Probe.class.getMethod("trigger", new Class[] {
/* 128 */                 Class.forName("[java.lang.Object") });
/* 129 */           method.invoke(probe, paramArrayOfObject);
/* 130 */         } catch (Exception exception) {
/*     */           assert false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tracing/MultiplexProbe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */