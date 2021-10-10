/*     */ package sun.java2d.jules;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileWorker
/*     */   implements Runnable
/*     */ {
/*     */   static final int RASTERIZED_TILE_SYNC_GRANULARITY = 8;
/*  32 */   final ArrayList<JulesTile> rasterizedTileConsumerCache = new ArrayList<>();
/*     */   
/*  34 */   final LinkedList<JulesTile> rasterizedBuffers = new LinkedList<>();
/*     */   
/*     */   IdleTileCache tileCache;
/*     */   JulesAATileGenerator tileGenerator;
/*     */   int workerStartIndex;
/*  39 */   volatile int consumerPos = 0;
/*     */ 
/*     */   
/*  42 */   int mainThreadCnt = 0;
/*  43 */   int workerCnt = 0;
/*  44 */   int doubled = 0;
/*     */   
/*     */   public TileWorker(JulesAATileGenerator paramJulesAATileGenerator, int paramInt, IdleTileCache paramIdleTileCache) {
/*  47 */     this.tileGenerator = paramJulesAATileGenerator;
/*  48 */     this.workerStartIndex = paramInt;
/*  49 */     this.tileCache = paramIdleTileCache;
/*     */   }
/*     */   
/*     */   public void run() {
/*  53 */     ArrayList<JulesTile> arrayList = new ArrayList(16);
/*     */     
/*  55 */     for (int i = this.workerStartIndex; i < this.tileGenerator.getTileCount(); i++) {
/*  56 */       TileTrapContainer tileTrapContainer = this.tileGenerator.getTrapContainer(i);
/*     */       
/*  58 */       if (tileTrapContainer != null && tileTrapContainer.getTileAlpha() == 127) {
/*     */         
/*  60 */         JulesTile julesTile = this.tileGenerator.rasterizeTile(i, this.tileCache
/*  61 */             .getIdleTileWorker(this.tileGenerator
/*  62 */               .getTileCount() - i - 1));
/*  63 */         arrayList.add(julesTile);
/*     */         
/*  65 */         if (arrayList.size() > 8) {
/*  66 */           addRasterizedTiles(arrayList);
/*  67 */           arrayList.clear();
/*     */         } 
/*     */       } 
/*     */       
/*  71 */       i = Math.max(i, this.consumerPos + 4);
/*     */     } 
/*  73 */     addRasterizedTiles(arrayList);
/*     */     
/*  75 */     this.tileCache.disposeRasterizerResources();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JulesTile getPreRasterizedTile(int paramInt) {
/*  84 */     JulesTile julesTile = null;
/*     */     
/*  86 */     if (this.rasterizedTileConsumerCache.size() == 0 && paramInt >= this.workerStartIndex)
/*     */     {
/*     */       
/*  89 */       synchronized (this.rasterizedBuffers) {
/*  90 */         this.rasterizedTileConsumerCache.addAll(this.rasterizedBuffers);
/*  91 */         this.rasterizedBuffers.clear();
/*     */       } 
/*     */     }
/*     */     
/*  95 */     while (julesTile == null && this.rasterizedTileConsumerCache.size() > 0) {
/*  96 */       JulesTile julesTile1 = this.rasterizedTileConsumerCache.get(0);
/*     */       
/*  98 */       if (julesTile1.getTilePos() > paramInt) {
/*     */         break;
/*     */       }
/*     */       
/* 102 */       if (julesTile1.getTilePos() < paramInt) {
/* 103 */         this.tileCache.releaseTile(julesTile1);
/* 104 */         this.doubled++;
/*     */       } 
/*     */       
/* 107 */       if (julesTile1.getTilePos() <= paramInt) {
/* 108 */         this.rasterizedTileConsumerCache.remove(0);
/*     */       }
/*     */       
/* 111 */       if (julesTile1.getTilePos() == paramInt) {
/* 112 */         julesTile = julesTile1;
/*     */       }
/*     */     } 
/*     */     
/* 116 */     if (julesTile == null) {
/* 117 */       this.mainThreadCnt++;
/*     */ 
/*     */ 
/*     */       
/* 121 */       this.consumerPos = paramInt;
/*     */     } else {
/* 123 */       this.workerCnt++;
/*     */     } 
/*     */     
/* 126 */     return julesTile;
/*     */   }
/*     */   
/*     */   private void addRasterizedTiles(ArrayList<JulesTile> paramArrayList) {
/* 130 */     synchronized (this.rasterizedBuffers) {
/* 131 */       this.rasterizedBuffers.addAll(paramArrayList);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disposeConsumerResources() {
/* 140 */     synchronized (this.rasterizedBuffers) {
/* 141 */       this.tileCache.releaseTiles(this.rasterizedBuffers);
/*     */     } 
/*     */     
/* 144 */     this.tileCache.releaseTiles(this.rasterizedTileConsumerCache);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/jules/TileWorker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */