/*     */ package sun.java2d.jules;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IdleTileCache
/*     */ {
/*     */   static final int IDLE_TILE_SYNC_GRANULARITY = 16;
/*  32 */   static final ArrayList<JulesTile> idleBuffers = new ArrayList<>();
/*     */   
/*  34 */   ArrayList<JulesTile> idleTileWorkerCacheList = new ArrayList<>();
/*  35 */   ArrayList<JulesTile> idleTileConsumerCacheList = new ArrayList<>(16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JulesTile getIdleTileWorker(int paramInt) {
/*  46 */     if (this.idleTileWorkerCacheList.size() == 0) {
/*  47 */       this.idleTileWorkerCacheList.ensureCapacity(paramInt);
/*     */       
/*  49 */       synchronized (idleBuffers) {
/*  50 */         for (byte b = 0; b < paramInt && idleBuffers.size() > 0; b++) {
/*  51 */           this.idleTileWorkerCacheList.add(idleBuffers
/*  52 */               .remove(idleBuffers.size() - 1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  57 */     if (this.idleTileWorkerCacheList.size() > 0) {
/*  58 */       return this.idleTileWorkerCacheList.remove(this.idleTileWorkerCacheList.size() - 1);
/*     */     }
/*     */     
/*  61 */     return new JulesTile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseTile(JulesTile paramJulesTile) {
/*  69 */     if (paramJulesTile != null && paramJulesTile.hasBuffer()) {
/*  70 */       this.idleTileConsumerCacheList.add(paramJulesTile);
/*     */       
/*  72 */       if (this.idleTileConsumerCacheList.size() > 16) {
/*  73 */         synchronized (idleBuffers) {
/*  74 */           idleBuffers.addAll(this.idleTileConsumerCacheList);
/*     */         } 
/*  76 */         this.idleTileConsumerCacheList.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disposeRasterizerResources() {
/*  86 */     releaseTiles(this.idleTileWorkerCacheList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disposeConsumerResources() {
/*  94 */     releaseTiles(this.idleTileConsumerCacheList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseTiles(List<JulesTile> paramList) {
/* 102 */     if (paramList.size() > 0) {
/* 103 */       synchronized (idleBuffers) {
/* 104 */         idleBuffers.addAll(paramList);
/*     */       } 
/* 106 */       paramList.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/jules/IdleTileCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */