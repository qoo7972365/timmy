/*     */ package com.sun.imageio.plugins.common;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.RenderedImage;
/*     */ import java.awt.image.WritableRaster;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PaletteBuilder
/*     */ {
/*     */   protected static final int MAXLEVEL = 8;
/*     */   protected RenderedImage src;
/*     */   protected ColorModel srcColorModel;
/*     */   protected Raster srcRaster;
/*     */   protected int requiredSize;
/*     */   protected ColorNode root;
/*     */   protected int numNodes;
/*     */   protected int maxNodes;
/*     */   protected int currLevel;
/*     */   protected int currSize;
/*     */   protected ColorNode[] reduceList;
/*     */   protected ColorNode[] palette;
/*     */   protected int transparency;
/*     */   protected ColorNode transColor;
/*     */   
/*     */   public static RenderedImage createIndexedImage(RenderedImage paramRenderedImage) {
/*  93 */     PaletteBuilder paletteBuilder = new PaletteBuilder(paramRenderedImage);
/*  94 */     paletteBuilder.buildPalette();
/*  95 */     return paletteBuilder.getIndexedImage();
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
/*     */   public static IndexColorModel createIndexColorModel(RenderedImage paramRenderedImage) {
/* 116 */     PaletteBuilder paletteBuilder = new PaletteBuilder(paramRenderedImage);
/* 117 */     paletteBuilder.buildPalette();
/* 118 */     return paletteBuilder.getIndexColorModel();
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
/*     */   public static boolean canCreatePalette(ImageTypeSpecifier paramImageTypeSpecifier) {
/* 135 */     if (paramImageTypeSpecifier == null) {
/* 136 */       throw new IllegalArgumentException("type == null");
/*     */     }
/* 138 */     return true;
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
/*     */   public static boolean canCreatePalette(RenderedImage paramRenderedImage) {
/* 155 */     if (paramRenderedImage == null) {
/* 156 */       throw new IllegalArgumentException("image == null");
/*     */     }
/* 158 */     ImageTypeSpecifier imageTypeSpecifier = new ImageTypeSpecifier(paramRenderedImage);
/* 159 */     return canCreatePalette(imageTypeSpecifier);
/*     */   }
/*     */   
/*     */   protected RenderedImage getIndexedImage() {
/* 163 */     IndexColorModel indexColorModel = getIndexColorModel();
/*     */ 
/*     */     
/* 166 */     BufferedImage bufferedImage = new BufferedImage(this.src.getWidth(), this.src.getHeight(), 13, indexColorModel);
/*     */ 
/*     */     
/* 169 */     WritableRaster writableRaster = bufferedImage.getRaster();
/* 170 */     for (byte b = 0; b < bufferedImage.getHeight(); b++) {
/* 171 */       for (byte b1 = 0; b1 < bufferedImage.getWidth(); b1++) {
/* 172 */         Color color = getSrcColor(b1, b);
/* 173 */         writableRaster.setSample(b1, b, 0, findColorIndex(this.root, color));
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     return bufferedImage;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PaletteBuilder(RenderedImage paramRenderedImage) {
/* 182 */     this(paramRenderedImage, 256);
/*     */   }
/*     */   
/*     */   protected PaletteBuilder(RenderedImage paramRenderedImage, int paramInt) {
/* 186 */     this.src = paramRenderedImage;
/* 187 */     this.srcColorModel = paramRenderedImage.getColorModel();
/* 188 */     this.srcRaster = paramRenderedImage.getData();
/*     */     
/* 190 */     this
/* 191 */       .transparency = this.srcColorModel.getTransparency();
/*     */     
/* 193 */     this.requiredSize = paramInt;
/*     */   }
/*     */   
/*     */   private Color getSrcColor(int paramInt1, int paramInt2) {
/* 197 */     int i = this.srcColorModel.getRGB(this.srcRaster.getDataElements(paramInt1, paramInt2, null));
/* 198 */     return new Color(i, (this.transparency != 1));
/*     */   }
/*     */   
/*     */   protected int findColorIndex(ColorNode paramColorNode, Color paramColor) {
/* 202 */     if (this.transparency != 1 && paramColor
/* 203 */       .getAlpha() != 255)
/*     */     {
/* 205 */       return 0;
/*     */     }
/*     */     
/* 208 */     if (paramColorNode.isLeaf) {
/* 209 */       return paramColorNode.paletteIndex;
/*     */     }
/* 211 */     int i = getBranchIndex(paramColor, paramColorNode.level);
/*     */     
/* 213 */     return findColorIndex(paramColorNode.children[i], paramColor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void buildPalette() {
/* 218 */     this.reduceList = new ColorNode[9]; int i;
/* 219 */     for (i = 0; i < this.reduceList.length; i++) {
/* 220 */       this.reduceList[i] = null;
/*     */     }
/*     */     
/* 223 */     this.numNodes = 0;
/* 224 */     this.maxNodes = 0;
/* 225 */     this.root = null;
/* 226 */     this.currSize = 0;
/* 227 */     this.currLevel = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     i = this.src.getWidth();
/* 235 */     int j = this.src.getHeight();
/* 236 */     for (byte b = 0; b < j; b++) {
/* 237 */       for (byte b1 = 0; b1 < i; b1++) {
/*     */         
/* 239 */         Color color = getSrcColor(i - b1 - 1, j - b - 1);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 244 */         if (this.transparency != 1 && color
/* 245 */           .getAlpha() != 255) {
/*     */           
/* 247 */           if (this.transColor == null) {
/* 248 */             this.requiredSize--;
/*     */             
/* 250 */             this.transColor = new ColorNode();
/* 251 */             this.transColor.isLeaf = true;
/*     */           } 
/* 253 */           this.transColor = insertNode(this.transColor, color, 0);
/*     */         } else {
/* 255 */           this.root = insertNode(this.root, color, 0);
/*     */         } 
/* 257 */         if (this.currSize > this.requiredSize) {
/* 258 */           reduceTree();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ColorNode insertNode(ColorNode paramColorNode, Color paramColor, int paramInt) {
/* 266 */     if (paramColorNode == null) {
/* 267 */       paramColorNode = new ColorNode();
/* 268 */       this.numNodes++;
/* 269 */       if (this.numNodes > this.maxNodes) {
/* 270 */         this.maxNodes = this.numNodes;
/*     */       }
/* 272 */       paramColorNode.level = paramInt;
/* 273 */       paramColorNode.isLeaf = (paramInt > 8);
/* 274 */       if (paramColorNode.isLeaf) {
/* 275 */         this.currSize++;
/*     */       }
/*     */     } 
/* 278 */     paramColorNode.colorCount++;
/* 279 */     paramColorNode.red += paramColor.getRed();
/* 280 */     paramColorNode.green += paramColor.getGreen();
/* 281 */     paramColorNode.blue += paramColor.getBlue();
/*     */     
/* 283 */     if (!paramColorNode.isLeaf) {
/* 284 */       int i = getBranchIndex(paramColor, paramInt);
/* 285 */       if (paramColorNode.children[i] == null) {
/* 286 */         paramColorNode.childCount++;
/* 287 */         if (paramColorNode.childCount == 2) {
/* 288 */           paramColorNode.nextReducible = this.reduceList[paramInt];
/* 289 */           this.reduceList[paramInt] = paramColorNode;
/*     */         } 
/*     */       } 
/* 292 */       paramColorNode.children[i] = 
/* 293 */         insertNode(paramColorNode.children[i], paramColor, paramInt + 1);
/*     */     } 
/* 295 */     return paramColorNode;
/*     */   }
/*     */   
/*     */   protected IndexColorModel getIndexColorModel() {
/* 299 */     int i = this.currSize;
/* 300 */     if (this.transColor != null) {
/* 301 */       i++;
/*     */     }
/*     */     
/* 304 */     byte[] arrayOfByte1 = new byte[i];
/* 305 */     byte[] arrayOfByte2 = new byte[i];
/* 306 */     byte[] arrayOfByte3 = new byte[i];
/*     */     
/* 308 */     byte b = 0;
/* 309 */     this.palette = new ColorNode[i];
/* 310 */     if (this.transColor != null) {
/* 311 */       b++;
/*     */     }
/*     */     
/* 314 */     if (this.root != null) {
/* 315 */       findPaletteEntry(this.root, b, arrayOfByte1, arrayOfByte2, arrayOfByte3);
/*     */     }
/*     */     
/* 318 */     IndexColorModel indexColorModel = null;
/* 319 */     if (this.transColor != null) {
/* 320 */       indexColorModel = new IndexColorModel(8, i, arrayOfByte1, arrayOfByte2, arrayOfByte3, 0);
/*     */     } else {
/* 322 */       indexColorModel = new IndexColorModel(8, this.currSize, arrayOfByte1, arrayOfByte2, arrayOfByte3);
/*     */     } 
/* 324 */     return indexColorModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int findPaletteEntry(ColorNode paramColorNode, int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
/* 330 */     if (paramColorNode.isLeaf) {
/* 331 */       paramArrayOfbyte1[paramInt] = (byte)(int)(paramColorNode.red / paramColorNode.colorCount);
/* 332 */       paramArrayOfbyte2[paramInt] = (byte)(int)(paramColorNode.green / paramColorNode.colorCount);
/* 333 */       paramArrayOfbyte3[paramInt] = (byte)(int)(paramColorNode.blue / paramColorNode.colorCount);
/* 334 */       paramColorNode.paletteIndex = paramInt;
/*     */       
/* 336 */       this.palette[paramInt] = paramColorNode;
/*     */       
/* 338 */       paramInt++;
/*     */     } else {
/* 340 */       for (byte b = 0; b < 8; b++) {
/* 341 */         if (paramColorNode.children[b] != null) {
/* 342 */           paramInt = findPaletteEntry(paramColorNode.children[b], paramInt, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 347 */     return paramInt;
/*     */   }
/*     */   
/*     */   protected int getBranchIndex(Color paramColor, int paramInt) {
/* 351 */     if (paramInt > 8 || paramInt < 0) {
/* 352 */       throw new IllegalArgumentException("Invalid octree node depth: " + paramInt);
/*     */     }
/*     */ 
/*     */     
/* 356 */     int i = 8 - paramInt;
/* 357 */     int j = 0x1 & (0xFF & paramColor.getRed()) >> i;
/* 358 */     int k = 0x1 & (0xFF & paramColor.getGreen()) >> i;
/* 359 */     int m = 0x1 & (0xFF & paramColor.getBlue()) >> i;
/* 360 */     return j << 2 | k << 1 | m;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reduceTree() {
/* 365 */     int i = this.reduceList.length - 1;
/* 366 */     while (this.reduceList[i] == null && i >= 0) {
/* 367 */       i--;
/*     */     }
/*     */     
/* 370 */     ColorNode colorNode1 = this.reduceList[i];
/* 371 */     if (colorNode1 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 377 */     ColorNode colorNode2 = colorNode1;
/* 378 */     int j = colorNode2.colorCount;
/*     */     
/* 380 */     byte b1 = 1;
/* 381 */     while (colorNode2.nextReducible != null) {
/* 382 */       if (j > colorNode2.nextReducible.colorCount) {
/* 383 */         colorNode1 = colorNode2;
/* 384 */         j = colorNode2.colorCount;
/*     */       } 
/* 386 */       colorNode2 = colorNode2.nextReducible;
/* 387 */       b1++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 392 */     if (colorNode1 == this.reduceList[i]) {
/* 393 */       this.reduceList[i] = colorNode1.nextReducible;
/*     */     } else {
/* 395 */       colorNode2 = colorNode1.nextReducible;
/* 396 */       colorNode1.nextReducible = colorNode2.nextReducible;
/* 397 */       colorNode1 = colorNode2;
/*     */     } 
/*     */     
/* 400 */     if (colorNode1.isLeaf) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 405 */     int k = colorNode1.getLeafChildCount();
/* 406 */     colorNode1.isLeaf = true;
/* 407 */     this.currSize -= k - 1;
/* 408 */     int m = colorNode1.level;
/* 409 */     for (byte b2 = 0; b2 < 8; b2++) {
/* 410 */       colorNode1.children[b2] = freeTree(colorNode1.children[b2]);
/*     */     }
/* 412 */     colorNode1.childCount = 0;
/*     */   }
/*     */   
/*     */   protected ColorNode freeTree(ColorNode paramColorNode) {
/* 416 */     if (paramColorNode == null) {
/* 417 */       return null;
/*     */     }
/* 419 */     for (byte b = 0; b < 8; b++) {
/* 420 */       paramColorNode.children[b] = freeTree(paramColorNode.children[b]);
/*     */     }
/*     */     
/* 423 */     this.numNodes--;
/* 424 */     return null;
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
/*     */   protected class ColorNode
/*     */   {
/*     */     public boolean isLeaf = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 447 */     public int level = 0;
/* 448 */     public int childCount = 0;
/* 449 */     ColorNode[] children = new ColorNode[8]; public int colorCount; public long red; public long blue; public ColorNode() {
/* 450 */       for (byte b = 0; b < 8; b++) {
/* 451 */         this.children[b] = null;
/*     */       }
/*     */       
/* 454 */       this.colorCount = 0;
/* 455 */       this.red = this.green = this.blue = 0L;
/*     */       
/* 457 */       this.paletteIndex = 0;
/*     */     }
/*     */     public long green; public int paletteIndex; ColorNode nextReducible;
/*     */     public int getLeafChildCount() {
/* 461 */       if (this.isLeaf) {
/* 462 */         return 0;
/*     */       }
/* 464 */       int i = 0;
/* 465 */       for (byte b = 0; b < this.children.length; b++) {
/* 466 */         if (this.children[b] != null) {
/* 467 */           if ((this.children[b]).isLeaf) {
/* 468 */             i++;
/*     */           } else {
/* 470 */             i += this.children[b].getLeafChildCount();
/*     */           } 
/*     */         }
/*     */       } 
/* 474 */       return i;
/*     */     }
/*     */     
/*     */     public int getRGB() {
/* 478 */       int i = (int)this.red / this.colorCount;
/* 479 */       int j = (int)this.green / this.colorCount;
/* 480 */       int k = (int)this.blue / this.colorCount;
/*     */       
/* 482 */       return 0xFF000000 | (0xFF & i) << 16 | (0xFF & j) << 8 | 0xFF & k;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/common/PaletteBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */