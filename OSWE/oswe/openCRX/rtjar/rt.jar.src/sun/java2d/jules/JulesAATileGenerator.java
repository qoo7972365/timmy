/*     */ package sun.java2d.jules;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import sun.java2d.pipe.AATileGenerator;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.java2d.xr.GrowableIntArray;
/*     */ import sun.java2d.xr.XRUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JulesAATileGenerator
/*     */   implements AATileGenerator
/*     */ {
/*  37 */   static final ExecutorService rasterThreadPool = Executors.newCachedThreadPool();
/*  38 */   static final int CPU_CNT = Runtime.getRuntime().availableProcessors();
/*     */   
/*     */   static final boolean ENABLE_THREADING = false;
/*     */   
/*     */   static final int THREAD_MIN = 16;
/*     */   
/*     */   static final int THREAD_BEGIN = 16;
/*     */   
/*     */   IdleTileCache tileCache;
/*     */   
/*     */   TileWorker worker;
/*     */   
/*     */   boolean threaded = false;
/*     */   int rasterTileCnt;
/*     */   static final int TILE_SIZE = 32;
/*     */   static final int TILE_SIZE_FP = 2097152;
/*     */   int left;
/*  55 */   int currTilePos = 0; int right; int top; int bottom; int width; int height; int leftFP; int topFP; int tileCnt;
/*     */   int tilesX;
/*     */   int tilesY;
/*     */   TrapezoidList traps;
/*     */   TileTrapContainer[] tiledTrapArray;
/*     */   JulesTile mainTile;
/*     */   
/*     */   public JulesAATileGenerator(Shape paramShape, AffineTransform paramAffineTransform, Region paramRegion, BasicStroke paramBasicStroke, boolean paramBoolean1, boolean paramBoolean2, int[] paramArrayOfint) {
/*  63 */     JulesPathBuf julesPathBuf = new JulesPathBuf();
/*     */     
/*  65 */     if (paramBasicStroke == null) {
/*  66 */       this.traps = julesPathBuf.tesselateFill(paramShape, paramAffineTransform, paramRegion);
/*     */     } else {
/*  68 */       this.traps = julesPathBuf.tesselateStroke(paramShape, paramBasicStroke, paramBoolean1, false, true, paramAffineTransform, paramRegion);
/*     */     } 
/*     */     
/*  71 */     calculateArea(paramArrayOfint);
/*  72 */     bucketSortTraps();
/*  73 */     calculateTypicalAlpha();
/*     */     
/*  75 */     this.threaded = false;
/*     */     
/*  77 */     if (this.threaded) {
/*  78 */       this.tileCache = new IdleTileCache();
/*  79 */       this.worker = new TileWorker(this, 16, this.tileCache);
/*  80 */       rasterThreadPool.execute(this.worker);
/*     */     } 
/*     */     
/*  83 */     this.mainTile = new JulesTile();
/*     */   }
/*     */ 
/*     */   
/*     */   private static native long rasterizeTrapezoidsNative(long paramLong, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3);
/*     */ 
/*     */   
/*     */   private static native void freePixmanImgPtr(long paramLong);
/*     */ 
/*     */   
/*     */   private void calculateArea(int[] paramArrayOfint) {
/*  94 */     this.tilesX = 0;
/*  95 */     this.tilesY = 0;
/*  96 */     this.tileCnt = 0;
/*  97 */     paramArrayOfint[0] = 0;
/*  98 */     paramArrayOfint[1] = 0;
/*  99 */     paramArrayOfint[2] = 0;
/* 100 */     paramArrayOfint[3] = 0;
/*     */     
/* 102 */     if (this.traps.getSize() > 0) {
/* 103 */       this.left = this.traps.getLeft();
/* 104 */       this.right = this.traps.getRight();
/* 105 */       this.top = this.traps.getTop();
/* 106 */       this.bottom = this.traps.getBottom();
/* 107 */       this.leftFP = this.left << 16;
/* 108 */       this.topFP = this.top << 16;
/*     */       
/* 110 */       paramArrayOfint[0] = this.left;
/* 111 */       paramArrayOfint[1] = this.top;
/* 112 */       paramArrayOfint[2] = this.right;
/* 113 */       paramArrayOfint[3] = this.bottom;
/*     */       
/* 115 */       this.width = this.right - this.left;
/* 116 */       this.height = this.bottom - this.top;
/*     */       
/* 118 */       if (this.width > 0 && this.height > 0) {
/* 119 */         this.tilesX = (int)Math.ceil(this.width / 32.0D);
/* 120 */         this.tilesY = (int)Math.ceil(this.height / 32.0D);
/* 121 */         this.tileCnt = this.tilesY * this.tilesX;
/* 122 */         this.tiledTrapArray = new TileTrapContainer[this.tileCnt];
/*     */       }
/*     */       else {
/*     */         
/* 126 */         this.traps.setSize(0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void bucketSortTraps() {
/* 134 */     for (byte b = 0; b < this.traps.getSize(); b++) {
/* 135 */       int i = this.traps.getTop(b) - XRUtils.XDoubleToFixed(this.top);
/* 136 */       int j = this.traps.getBottom(b) - this.topFP;
/* 137 */       int k = this.traps.getP1XLeft(b) - this.leftFP;
/* 138 */       int m = this.traps.getP2XLeft(b) - this.leftFP;
/* 139 */       int n = this.traps.getP1XRight(b) - this.leftFP;
/* 140 */       int i1 = this.traps.getP2XRight(b) - this.leftFP;
/*     */       
/* 142 */       int i2 = Math.min(k, m);
/* 143 */       int i3 = Math.max(n, i1);
/*     */       
/* 145 */       i3 = (i3 > 0) ? (i3 - 1) : i3;
/* 146 */       j = (j > 0) ? (j - 1) : j;
/*     */       
/* 148 */       int i4 = i / 2097152;
/* 149 */       int i5 = j / 2097152;
/* 150 */       int i6 = i2 / 2097152;
/* 151 */       int i7 = i3 / 2097152;
/*     */       
/* 153 */       for (int i8 = i4; i8 <= i5; i8++) {
/*     */         
/* 155 */         for (int i9 = i6; i9 <= i7; i9++) {
/* 156 */           int i10 = i8 * this.tilesX + i9;
/* 157 */           TileTrapContainer tileTrapContainer = this.tiledTrapArray[i10];
/* 158 */           if (tileTrapContainer == null) {
/* 159 */             tileTrapContainer = new TileTrapContainer(new GrowableIntArray(1, 16));
/* 160 */             this.tiledTrapArray[i10] = tileTrapContainer;
/*     */           } 
/*     */           
/* 163 */           tileTrapContainer.getTraps().addInt(b);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getAlpha(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 170 */     JulesTile julesTile = null;
/*     */     
/* 172 */     if (this.threaded) {
/* 173 */       julesTile = this.worker.getPreRasterizedTile(this.currTilePos);
/*     */     }
/*     */     
/* 176 */     if (julesTile != null) {
/* 177 */       System.arraycopy(julesTile.getImgBuffer(), 0, paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */       
/* 179 */       this.tileCache.releaseTile(julesTile);
/*     */     } else {
/* 181 */       this.mainTile.setImgBuffer(paramArrayOfbyte);
/* 182 */       rasterizeTile(this.currTilePos, this.mainTile);
/*     */     } 
/*     */     
/* 185 */     nextTile();
/*     */   }
/*     */   
/*     */   public void calculateTypicalAlpha() {
/* 189 */     this.rasterTileCnt = 0;
/*     */     
/* 191 */     for (byte b = 0; b < this.tileCnt; b++) {
/*     */       
/* 193 */       TileTrapContainer tileTrapContainer = this.tiledTrapArray[b];
/* 194 */       if (tileTrapContainer != null) {
/* 195 */         GrowableIntArray growableIntArray = tileTrapContainer.getTraps();
/*     */         
/* 197 */         char c = '';
/* 198 */         if (growableIntArray == null || growableIntArray.getSize() == 0) {
/* 199 */           c = Character.MIN_VALUE;
/* 200 */         } else if (doTrapsCoverTile(growableIntArray, b)) {
/* 201 */           c = 'ÿ';
/*     */         } 
/*     */         
/* 204 */         if (c == '' || c == 'ÿ') {
/* 205 */           this.rasterTileCnt++;
/*     */         }
/*     */         
/* 208 */         tileTrapContainer.setTileAlpha(c);
/*     */       } 
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
/*     */   protected boolean doTrapsCoverTile(GrowableIntArray paramGrowableIntArray, int paramInt) {
/* 225 */     if (paramGrowableIntArray.getSize() > 32) {
/* 226 */       return false;
/*     */     }
/*     */     
/* 229 */     int i = getXPos(paramInt) * 2097152 + this.leftFP;
/* 230 */     int j = getYPos(paramInt) * 2097152 + this.topFP;
/* 231 */     int k = i + 2097152;
/* 232 */     int m = j + 2097152;
/*     */ 
/*     */     
/* 235 */     int n = this.traps.getTop(paramGrowableIntArray.getInt(0));
/* 236 */     int i1 = this.traps.getBottom(paramGrowableIntArray.getInt(0));
/* 237 */     if (n > j || i1 < j) {
/* 238 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 243 */     int i2 = n;
/*     */     
/* 245 */     for (byte b = 0; b < paramGrowableIntArray.getSize(); b++) {
/* 246 */       int i3 = paramGrowableIntArray.getInt(b);
/* 247 */       if (this.traps.getP1XLeft(i3) > i || this.traps
/* 248 */         .getP2XLeft(i3) > i || this.traps
/* 249 */         .getP1XRight(i3) < k || this.traps
/* 250 */         .getP2XRight(i3) < k || this.traps
/* 251 */         .getTop(i3) != i2)
/*     */       {
/* 253 */         return false;
/*     */       }
/* 255 */       i2 = this.traps.getBottom(i3);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 260 */     return (i2 >= m);
/*     */   }
/*     */   
/*     */   public int getTypicalAlpha() {
/* 264 */     if (this.tiledTrapArray[this.currTilePos] == null) {
/* 265 */       return 0;
/*     */     }
/* 267 */     return this.tiledTrapArray[this.currTilePos].getTileAlpha();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 272 */     freePixmanImgPtr(this.mainTile.getPixmanImgPtr());
/*     */     
/* 274 */     if (this.threaded) {
/* 275 */       this.tileCache.disposeConsumerResources();
/* 276 */       this.worker.disposeConsumerResources();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected JulesTile rasterizeTile(int paramInt, JulesTile paramJulesTile) {
/* 281 */     int i = this.left + getXPos(paramInt) * 32;
/* 282 */     int j = this.top + getYPos(paramInt) * 32;
/* 283 */     TileTrapContainer tileTrapContainer = this.tiledTrapArray[paramInt];
/* 284 */     GrowableIntArray growableIntArray = tileTrapContainer.getTraps();
/*     */     
/* 286 */     if (tileTrapContainer.getTileAlpha() == 127) {
/*     */       
/* 288 */       long l = rasterizeTrapezoidsNative(paramJulesTile.getPixmanImgPtr(), this.traps
/* 289 */           .getTrapArray(), growableIntArray
/* 290 */           .getArray(), growableIntArray
/* 291 */           .getSize(), paramJulesTile
/* 292 */           .getImgBuffer(), i, j);
/*     */       
/* 294 */       paramJulesTile.setPixmanImgPtr(l);
/*     */     } 
/*     */     
/* 297 */     paramJulesTile.setTilePos(paramInt);
/* 298 */     return paramJulesTile;
/*     */   }
/*     */   
/*     */   protected int getXPos(int paramInt) {
/* 302 */     return paramInt % this.tilesX;
/*     */   }
/*     */   
/*     */   protected int getYPos(int paramInt) {
/* 306 */     return paramInt / this.tilesX;
/*     */   }
/*     */   
/*     */   public void nextTile() {
/* 310 */     this.currTilePos++;
/*     */   }
/*     */   
/*     */   public int getTileHeight() {
/* 314 */     return 32;
/*     */   }
/*     */   
/*     */   public int getTileWidth() {
/* 318 */     return 32;
/*     */   }
/*     */   
/*     */   public int getTileCount() {
/* 322 */     return this.tileCnt;
/*     */   }
/*     */   
/*     */   public TileTrapContainer getTrapContainer(int paramInt) {
/* 326 */     return this.tiledTrapArray[paramInt];
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/jules/JulesAATileGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */