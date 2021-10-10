/*     */ package sun.misc;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public abstract class Ref
/*     */ {
/*  51 */   private SoftReference soft = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Object get() {
/*  62 */     Object object = check();
/*  63 */     if (object == null) {
/*  64 */       object = reconstitute();
/*  65 */       setThing(object);
/*     */     } 
/*  67 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Object reconstitute();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() {
/*  87 */     SoftReference softReference = this.soft;
/*  88 */     if (softReference != null) softReference.clear(); 
/*  89 */     this.soft = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setThing(Object paramObject) {
/*  97 */     flush();
/*  98 */     this.soft = new SoftReference(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Object check() {
/* 105 */     SoftReference softReference = this.soft;
/* 106 */     if (softReference == null) return null; 
/* 107 */     return softReference.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ref() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ref(Object paramObject) {
/* 119 */     setThing(paramObject);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/Ref.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */