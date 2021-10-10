/*     */ package sun.java2d.loops;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RenderCache
/*     */ {
/*     */   private Entry[] entries;
/*     */   
/*     */   final class Entry
/*     */   {
/*     */     private SurfaceType src;
/*     */     private CompositeType comp;
/*     */     private SurfaceType dst;
/*     */     private Object value;
/*     */     
/*     */     public Entry(SurfaceType param1SurfaceType1, CompositeType param1CompositeType, SurfaceType param1SurfaceType2, Object param1Object) {
/*  40 */       this.src = param1SurfaceType1;
/*  41 */       this.comp = param1CompositeType;
/*  42 */       this.dst = param1SurfaceType2;
/*  43 */       this.value = param1Object;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matches(SurfaceType param1SurfaceType1, CompositeType param1CompositeType, SurfaceType param1SurfaceType2) {
/*  54 */       return (this.src == param1SurfaceType1 && this.comp == param1CompositeType && this.dst == param1SurfaceType2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getValue() {
/*  60 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderCache(int paramInt) {
/*  67 */     this.entries = new Entry[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Object get(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  74 */     int i = this.entries.length - 1;
/*  75 */     for (int j = i; j >= 0; j--) {
/*  76 */       Entry entry = this.entries[j];
/*  77 */       if (entry == null) {
/*     */         break;
/*     */       }
/*  80 */       if (entry.matches(paramSurfaceType1, paramCompositeType, paramSurfaceType2)) {
/*  81 */         if (j < i - 4) {
/*  82 */           System.arraycopy(this.entries, j + 1, this.entries, j, i - j);
/*  83 */           this.entries[i] = entry;
/*     */         } 
/*  85 */         return entry.getValue();
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void put(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2, Object paramObject) {
/*  97 */     Entry entry = new Entry(paramSurfaceType1, paramCompositeType, paramSurfaceType2, paramObject);
/*     */     
/*  99 */     int i = this.entries.length;
/* 100 */     System.arraycopy(this.entries, 1, this.entries, 0, i - 1);
/* 101 */     this.entries[i - 1] = entry;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/RenderCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */