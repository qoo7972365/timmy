/*      */ package javax.imageio;
/*      */ 
/*      */ import java.awt.Dimension;
/*      */ import java.util.Locale;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ImageWriteParam
/*      */   extends IIOParam
/*      */ {
/*      */   public static final int MODE_DISABLED = 0;
/*      */   public static final int MODE_DEFAULT = 1;
/*      */   public static final int MODE_EXPLICIT = 2;
/*      */   public static final int MODE_COPY_FROM_METADATA = 3;
/*      */   private static final int MAX_MODE = 3;
/*      */   protected boolean canWriteTiles = false;
/*  208 */   protected int tilingMode = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  221 */   protected Dimension[] preferredTileSizes = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean tilingSet = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  238 */   protected int tileWidth = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  247 */   protected int tileHeight = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canOffsetTiles = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  269 */   protected int tileGridXOffset = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  279 */   protected int tileGridYOffset = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canWriteProgressive = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  309 */   protected int progressiveMode = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canWriteCompressed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  336 */   protected int compressionMode = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  346 */   protected String[] compressionTypes = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  355 */   protected String compressionType = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  364 */   protected float compressionQuality = 1.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  372 */   protected Locale locale = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ImageWriteParam() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ImageWriteParam(Locale paramLocale) {
/*  389 */     this.locale = paramLocale;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Dimension[] clonePreferredTileSizes(Dimension[] paramArrayOfDimension) {
/*  394 */     if (paramArrayOfDimension == null) {
/*  395 */       return null;
/*      */     }
/*  397 */     Dimension[] arrayOfDimension = new Dimension[paramArrayOfDimension.length];
/*  398 */     for (byte b = 0; b < paramArrayOfDimension.length; b++) {
/*  399 */       arrayOfDimension[b] = new Dimension(paramArrayOfDimension[b]);
/*      */     }
/*  401 */     return arrayOfDimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  412 */     return this.locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWriteTiles() {
/*  427 */     return this.canWriteTiles;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canOffsetTiles() {
/*  446 */     return this.canOffsetTiles;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTilingMode(int paramInt) {
/*  486 */     if (!canWriteTiles()) {
/*  487 */       throw new UnsupportedOperationException("Tiling not supported!");
/*      */     }
/*  489 */     if (paramInt < 0 || paramInt > 3) {
/*  490 */       throw new IllegalArgumentException("Illegal value for mode!");
/*      */     }
/*  492 */     this.tilingMode = paramInt;
/*  493 */     if (paramInt == 2) {
/*  494 */       unsetTiling();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTilingMode() {
/*  510 */     if (!canWriteTiles()) {
/*  511 */       throw new UnsupportedOperationException("Tiling not supported");
/*      */     }
/*  513 */     return this.tilingMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension[] getPreferredTileSizes() {
/*  540 */     if (!canWriteTiles()) {
/*  541 */       throw new UnsupportedOperationException("Tiling not supported");
/*      */     }
/*  543 */     return clonePreferredTileSizes(this.preferredTileSizes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTiling(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  585 */     if (!canWriteTiles()) {
/*  586 */       throw new UnsupportedOperationException("Tiling not supported!");
/*      */     }
/*  588 */     if (getTilingMode() != 2) {
/*  589 */       throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
/*      */     }
/*  591 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/*  592 */       throw new IllegalArgumentException("tile dimensions are non-positive!");
/*      */     }
/*      */     
/*  595 */     boolean bool = (paramInt3 != 0 || paramInt4 != 0) ? true : false;
/*  596 */     if (!canOffsetTiles() && bool) {
/*  597 */       throw new UnsupportedOperationException("Can't offset tiles!");
/*      */     }
/*  599 */     if (this.preferredTileSizes != null) {
/*  600 */       boolean bool1 = true;
/*  601 */       for (byte b = 0; b < this.preferredTileSizes.length; b += 2) {
/*  602 */         Dimension dimension1 = this.preferredTileSizes[b];
/*  603 */         Dimension dimension2 = this.preferredTileSizes[b + 1];
/*  604 */         if (paramInt1 < dimension1.width || paramInt1 > dimension2.width || paramInt2 < dimension1.height || paramInt2 > dimension2.height) {
/*      */ 
/*      */ 
/*      */           
/*  608 */           bool1 = false;
/*      */           break;
/*      */         } 
/*      */       } 
/*  612 */       if (!bool1) {
/*  613 */         throw new IllegalArgumentException("Illegal tile size!");
/*      */       }
/*      */     } 
/*      */     
/*  617 */     this.tilingSet = true;
/*  618 */     this.tileWidth = paramInt1;
/*  619 */     this.tileHeight = paramInt2;
/*  620 */     this.tileGridXOffset = paramInt3;
/*  621 */     this.tileGridYOffset = paramInt4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unsetTiling() {
/*  641 */     if (!canWriteTiles()) {
/*  642 */       throw new UnsupportedOperationException("Tiling not supported!");
/*      */     }
/*  644 */     if (getTilingMode() != 2) {
/*  645 */       throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
/*      */     }
/*  647 */     this.tilingSet = false;
/*  648 */     this.tileWidth = 0;
/*  649 */     this.tileHeight = 0;
/*  650 */     this.tileGridXOffset = 0;
/*  651 */     this.tileGridYOffset = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileWidth() {
/*  672 */     if (!canWriteTiles()) {
/*  673 */       throw new UnsupportedOperationException("Tiling not supported!");
/*      */     }
/*  675 */     if (getTilingMode() != 2) {
/*  676 */       throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
/*      */     }
/*  678 */     if (!this.tilingSet) {
/*  679 */       throw new IllegalStateException("Tiling parameters not set!");
/*      */     }
/*  681 */     return this.tileWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileHeight() {
/*  702 */     if (!canWriteTiles()) {
/*  703 */       throw new UnsupportedOperationException("Tiling not supported!");
/*      */     }
/*  705 */     if (getTilingMode() != 2) {
/*  706 */       throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
/*      */     }
/*  708 */     if (!this.tilingSet) {
/*  709 */       throw new IllegalStateException("Tiling parameters not set!");
/*      */     }
/*  711 */     return this.tileHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileGridXOffset() {
/*  732 */     if (!canWriteTiles()) {
/*  733 */       throw new UnsupportedOperationException("Tiling not supported!");
/*      */     }
/*  735 */     if (getTilingMode() != 2) {
/*  736 */       throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
/*      */     }
/*  738 */     if (!this.tilingSet) {
/*  739 */       throw new IllegalStateException("Tiling parameters not set!");
/*      */     }
/*  741 */     return this.tileGridXOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileGridYOffset() {
/*  762 */     if (!canWriteTiles()) {
/*  763 */       throw new UnsupportedOperationException("Tiling not supported!");
/*      */     }
/*  765 */     if (getTilingMode() != 2) {
/*  766 */       throw new IllegalStateException("Tiling mode not MODE_EXPLICIT!");
/*      */     }
/*  768 */     if (!this.tilingSet) {
/*  769 */       throw new IllegalStateException("Tiling parameters not set!");
/*      */     }
/*  771 */     return this.tileGridYOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWriteProgressive() {
/*  785 */     return this.canWriteProgressive;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProgressiveMode(int paramInt) {
/*  829 */     if (!canWriteProgressive()) {
/*  830 */       throw new UnsupportedOperationException("Progressive output not supported");
/*      */     }
/*      */     
/*  833 */     if (paramInt < 0 || paramInt > 3) {
/*  834 */       throw new IllegalArgumentException("Illegal value for mode!");
/*      */     }
/*  836 */     if (paramInt == 2) {
/*  837 */       throw new IllegalArgumentException("MODE_EXPLICIT not supported for progressive output");
/*      */     }
/*      */     
/*  840 */     this.progressiveMode = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getProgressiveMode() {
/*  855 */     if (!canWriteProgressive()) {
/*  856 */       throw new UnsupportedOperationException("Progressive output not supported");
/*      */     }
/*      */     
/*  859 */     return this.progressiveMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWriteCompressed() {
/*  868 */     return this.canWriteCompressed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCompressionMode(int paramInt) {
/*  913 */     if (!canWriteCompressed()) {
/*  914 */       throw new UnsupportedOperationException("Compression not supported.");
/*      */     }
/*      */     
/*  917 */     if (paramInt < 0 || paramInt > 3) {
/*  918 */       throw new IllegalArgumentException("Illegal value for mode!");
/*      */     }
/*  920 */     this.compressionMode = paramInt;
/*  921 */     if (paramInt == 2) {
/*  922 */       unsetCompression();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCompressionMode() {
/*  938 */     if (!canWriteCompressed()) {
/*  939 */       throw new UnsupportedOperationException("Compression not supported.");
/*      */     }
/*      */     
/*  942 */     return this.compressionMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getCompressionTypes() {
/*  972 */     if (!canWriteCompressed()) {
/*  973 */       throw new UnsupportedOperationException("Compression not supported");
/*      */     }
/*      */     
/*  976 */     if (this.compressionTypes == null) {
/*  977 */       return null;
/*      */     }
/*  979 */     return (String[])this.compressionTypes.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCompressionType(String paramString) {
/* 1016 */     if (!canWriteCompressed()) {
/* 1017 */       throw new UnsupportedOperationException("Compression not supported");
/*      */     }
/*      */     
/* 1020 */     if (getCompressionMode() != 2) {
/* 1021 */       throw new IllegalStateException("Compression mode not MODE_EXPLICIT!");
/*      */     }
/*      */     
/* 1024 */     String[] arrayOfString = getCompressionTypes();
/* 1025 */     if (arrayOfString == null) {
/* 1026 */       throw new UnsupportedOperationException("No settable compression types");
/*      */     }
/*      */     
/* 1029 */     if (paramString != null) {
/* 1030 */       boolean bool = false;
/* 1031 */       if (arrayOfString != null) {
/* 1032 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 1033 */           if (paramString.equals(arrayOfString[b])) {
/* 1034 */             bool = true;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/* 1039 */       if (!bool) {
/* 1040 */         throw new IllegalArgumentException("Unknown compression type!");
/*      */       }
/*      */     } 
/* 1043 */     this.compressionType = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCompressionType() {
/* 1070 */     if (!canWriteCompressed()) {
/* 1071 */       throw new UnsupportedOperationException("Compression not supported.");
/*      */     }
/*      */     
/* 1074 */     if (getCompressionMode() != 2) {
/* 1075 */       throw new IllegalStateException("Compression mode not MODE_EXPLICIT!");
/*      */     }
/*      */     
/* 1078 */     return this.compressionType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unsetCompression() {
/* 1098 */     if (!canWriteCompressed()) {
/* 1099 */       throw new UnsupportedOperationException("Compression not supported");
/*      */     }
/*      */     
/* 1102 */     if (getCompressionMode() != 2) {
/* 1103 */       throw new IllegalStateException("Compression mode not MODE_EXPLICIT!");
/*      */     }
/*      */     
/* 1106 */     this.compressionType = null;
/* 1107 */     this.compressionQuality = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalizedCompressionTypeName() {
/* 1132 */     if (!canWriteCompressed()) {
/* 1133 */       throw new UnsupportedOperationException("Compression not supported.");
/*      */     }
/*      */     
/* 1136 */     if (getCompressionMode() != 2) {
/* 1137 */       throw new IllegalStateException("Compression mode not MODE_EXPLICIT!");
/*      */     }
/*      */     
/* 1140 */     if (getCompressionType() == null) {
/* 1141 */       throw new IllegalStateException("No compression type set!");
/*      */     }
/* 1143 */     return getCompressionType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCompressionLossless() {
/* 1174 */     if (!canWriteCompressed()) {
/* 1175 */       throw new UnsupportedOperationException("Compression not supported");
/*      */     }
/*      */     
/* 1178 */     if (getCompressionMode() != 2) {
/* 1179 */       throw new IllegalStateException("Compression mode not MODE_EXPLICIT!");
/*      */     }
/*      */     
/* 1182 */     if (getCompressionTypes() != null && 
/* 1183 */       getCompressionType() == null) {
/* 1184 */       throw new IllegalStateException("No compression type set!");
/*      */     }
/* 1186 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCompressionQuality(float paramFloat) {
/* 1234 */     if (!canWriteCompressed()) {
/* 1235 */       throw new UnsupportedOperationException("Compression not supported");
/*      */     }
/*      */     
/* 1238 */     if (getCompressionMode() != 2) {
/* 1239 */       throw new IllegalStateException("Compression mode not MODE_EXPLICIT!");
/*      */     }
/*      */     
/* 1242 */     if (getCompressionTypes() != null && getCompressionType() == null) {
/* 1243 */       throw new IllegalStateException("No compression type set!");
/*      */     }
/* 1245 */     if (paramFloat < 0.0F || paramFloat > 1.0F) {
/* 1246 */       throw new IllegalArgumentException("Quality out-of-bounds!");
/*      */     }
/* 1248 */     this.compressionQuality = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCompressionQuality() {
/* 1278 */     if (!canWriteCompressed()) {
/* 1279 */       throw new UnsupportedOperationException("Compression not supported.");
/*      */     }
/*      */     
/* 1282 */     if (getCompressionMode() != 2) {
/* 1283 */       throw new IllegalStateException("Compression mode not MODE_EXPLICIT!");
/*      */     }
/*      */     
/* 1286 */     if (getCompressionTypes() != null && 
/* 1287 */       getCompressionType() == null) {
/* 1288 */       throw new IllegalStateException("No compression type set!");
/*      */     }
/* 1290 */     return this.compressionQuality;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBitRate(float paramFloat) {
/* 1331 */     if (!canWriteCompressed()) {
/* 1332 */       throw new UnsupportedOperationException("Compression not supported.");
/*      */     }
/*      */     
/* 1335 */     if (getCompressionMode() != 2) {
/* 1336 */       throw new IllegalStateException("Compression mode not MODE_EXPLICIT!");
/*      */     }
/*      */     
/* 1339 */     if (getCompressionTypes() != null && 
/* 1340 */       getCompressionType() == null) {
/* 1341 */       throw new IllegalStateException("No compression type set!");
/*      */     }
/* 1343 */     if (paramFloat < 0.0F || paramFloat > 1.0F) {
/* 1344 */       throw new IllegalArgumentException("Quality out-of-bounds!");
/*      */     }
/* 1346 */     return -1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getCompressionQualityDescriptions() {
/* 1403 */     if (!canWriteCompressed()) {
/* 1404 */       throw new UnsupportedOperationException("Compression not supported.");
/*      */     }
/*      */     
/* 1407 */     if (getCompressionMode() != 2) {
/* 1408 */       throw new IllegalStateException("Compression mode not MODE_EXPLICIT!");
/*      */     }
/*      */     
/* 1411 */     if (getCompressionTypes() != null && 
/* 1412 */       getCompressionType() == null) {
/* 1413 */       throw new IllegalStateException("No compression type set!");
/*      */     }
/* 1415 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getCompressionQualityValues() {
/* 1456 */     if (!canWriteCompressed()) {
/* 1457 */       throw new UnsupportedOperationException("Compression not supported.");
/*      */     }
/*      */     
/* 1460 */     if (getCompressionMode() != 2) {
/* 1461 */       throw new IllegalStateException("Compression mode not MODE_EXPLICIT!");
/*      */     }
/*      */     
/* 1464 */     if (getCompressionTypes() != null && 
/* 1465 */       getCompressionType() == null) {
/* 1466 */       throw new IllegalStateException("No compression type set!");
/*      */     }
/* 1468 */     return null;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/ImageWriteParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */