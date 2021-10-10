/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.SystemColor;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DataBufferUShort;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.util.List;
/*     */ import sun.awt.IconInfo;
/*     */ import sun.awt.image.ImageRepresentation;
/*     */ import sun.awt.image.ToolkitImage;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XIconWindow
/*     */   extends XBaseWindow
/*     */ {
/*  37 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XIconWindow");
/*     */   XDecoratedPeer parent;
/*     */   Dimension size;
/*  40 */   long iconPixmap = 0L;
/*  41 */   long iconMask = 0L;
/*  42 */   int iconWidth = 0;
/*  43 */   int iconHeight = 0;
/*     */   XIconWindow(XDecoratedPeer paramXDecoratedPeer) {
/*  45 */     super(new XCreateWindowParams(new Object[] { "parent", paramXDecoratedPeer, "delayed", Boolean.TRUE }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void instantPreInit(XCreateWindowParams paramXCreateWindowParams) {
/*  51 */     super.instantPreInit(paramXCreateWindowParams);
/*  52 */     this.parent = (XDecoratedPeer)paramXCreateWindowParams.get("parent");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XIconSize[] getIconSizes() {
/*  59 */     XToolkit.awtLock();
/*     */     try {
/*  61 */       AwtGraphicsConfigData awtGraphicsConfigData = this.parent.getGraphicsConfigurationData();
/*  62 */       long l1 = awtGraphicsConfigData.get_awt_visInfo().get_screen();
/*  63 */       long l2 = XToolkit.getDisplay();
/*     */       
/*  65 */       if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/*  66 */         log.finest(awtGraphicsConfigData.toString());
/*     */       }
/*     */ 
/*     */       
/*  70 */       long l3 = XlibWrapper.XGetIconSizes(l2, XToolkit.getDefaultRootWindow(), XlibWrapper.larg1, XlibWrapper.iarg1);
/*     */       
/*  72 */       if (l3 == 0L) {
/*  73 */         return null;
/*     */       }
/*  75 */       int i = Native.getInt(XlibWrapper.iarg1);
/*  76 */       long l4 = Native.getLong(XlibWrapper.larg1);
/*  77 */       if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/*  78 */         log.finest("count = {1}, sizes_ptr = {0}", new Object[] { Long.valueOf(l4), Integer.valueOf(i) });
/*     */       }
/*  80 */       XIconSize[] arrayOfXIconSize = new XIconSize[i];
/*  81 */       for (byte b = 0; b < i; b++, l4 += XIconSize.getSize()) {
/*  82 */         arrayOfXIconSize[b] = new XIconSize(l4);
/*  83 */         if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/*  84 */           log.finest("sizes_ptr[{1}] = {0}", new Object[] { arrayOfXIconSize[b], Integer.valueOf(b) });
/*     */         }
/*     */       } 
/*  87 */       return arrayOfXIconSize;
/*     */     } finally {
/*  89 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Dimension calcIconSize(int paramInt1, int paramInt2) {
/*  94 */     if (XWM.getWMID() == 10) {
/*     */ 
/*     */       
/*  97 */       log.finest("Returning ICE_WM icon size: 16x16");
/*  98 */       return new Dimension(16, 16);
/*     */     } 
/*     */     
/* 101 */     XIconSize[] arrayOfXIconSize = getIconSizes();
/* 102 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 103 */       log.finest("Icon sizes: {0}", (Object[])arrayOfXIconSize);
/*     */     }
/* 105 */     if (arrayOfXIconSize == null)
/*     */     {
/* 107 */       return new Dimension(16, 16);
/*     */     }
/* 109 */     boolean bool = false;
/* 110 */     int i = -1, j = 0;
/* 111 */     int k = 0, m = 0; int n;
/* 112 */     for (n = 0; n < arrayOfXIconSize.length; n++) {
/* 113 */       if (paramInt1 >= arrayOfXIconSize[n].get_min_width() && paramInt1 <= arrayOfXIconSize[n]
/* 114 */         .get_max_width() && paramInt2 >= arrayOfXIconSize[n]
/* 115 */         .get_min_height() && paramInt2 <= arrayOfXIconSize[n]
/* 116 */         .get_max_height()) {
/* 117 */         int i2, i3; bool = true;
/* 118 */         if ((paramInt1 - arrayOfXIconSize[n].get_min_width()) % arrayOfXIconSize[n]
/* 119 */           .get_width_inc() == 0 && (paramInt2 - arrayOfXIconSize[n]
/* 120 */           .get_min_height()) % arrayOfXIconSize[n]
/* 121 */           .get_height_inc() == 0) {
/*     */           
/* 123 */           k = paramInt1;
/* 124 */           m = paramInt2;
/* 125 */           i = 0;
/*     */           break;
/*     */         } 
/* 128 */         j = paramInt1 - arrayOfXIconSize[n].get_min_width();
/* 129 */         if (j == 0) {
/* 130 */           i3 = paramInt1;
/*     */         } else {
/* 132 */           j %= arrayOfXIconSize[n].get_width_inc();
/* 133 */           i3 = paramInt1 - j;
/*     */         } 
/* 135 */         j = paramInt2 - arrayOfXIconSize[n].get_min_height();
/* 136 */         if (j == 0) {
/* 137 */           i2 = paramInt2;
/*     */         } else {
/* 139 */           j %= arrayOfXIconSize[n].get_height_inc();
/* 140 */           i2 = paramInt2 - j;
/*     */         } 
/* 142 */         int i1 = i3 * i3 + i2 * i2;
/*     */         
/* 144 */         if (i > i1) {
/* 145 */           k = i3;
/* 146 */           m = i2;
/* 147 */           i = i1;
/*     */         } 
/*     */       } 
/*     */     } 
/* 151 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 152 */       log.finest("found=" + bool);
/*     */     }
/* 154 */     if (!bool) {
/* 155 */       if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 156 */         log.finest("widthHint=" + paramInt1 + ", heightHint=" + paramInt2 + ", saveWidth=" + k + ", saveHeight=" + m + ", max_width=" + arrayOfXIconSize[0]
/*     */             
/* 158 */             .get_max_width() + ", max_height=" + arrayOfXIconSize[0]
/* 159 */             .get_max_height() + ", min_width=" + arrayOfXIconSize[0]
/* 160 */             .get_min_width() + ", min_height=" + arrayOfXIconSize[0]
/* 161 */             .get_min_height());
/*     */       }
/*     */       
/* 164 */       if (paramInt1 > arrayOfXIconSize[0].get_max_width() || paramInt2 > arrayOfXIconSize[0]
/* 165 */         .get_max_height()) {
/*     */ 
/*     */ 
/*     */         
/* 169 */         n = paramInt1 - arrayOfXIconSize[0].get_max_width();
/* 170 */         int i1 = paramInt2 - arrayOfXIconSize[0].get_max_height();
/* 171 */         if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 172 */           log.finest("wdiff=" + n + ", hdiff=" + i1);
/*     */         }
/* 174 */         if (n >= i1) {
/* 175 */           k = arrayOfXIconSize[0].get_max_width();
/*     */           
/* 177 */           m = (int)(arrayOfXIconSize[0].get_max_width() / paramInt1 * paramInt2);
/*     */         } else {
/*     */           
/* 180 */           k = (int)(arrayOfXIconSize[0].get_max_height() / paramInt2 * paramInt1);
/* 181 */           m = arrayOfXIconSize[0].get_max_height();
/*     */         } 
/* 183 */       } else if (paramInt1 < arrayOfXIconSize[0].get_min_width() || paramInt2 < arrayOfXIconSize[0]
/* 184 */         .get_min_height()) {
/*     */ 
/*     */         
/* 187 */         k = (arrayOfXIconSize[0].get_min_width() + arrayOfXIconSize[0].get_max_width()) / 2;
/* 188 */         m = (arrayOfXIconSize[0].get_min_height() + arrayOfXIconSize[0].get_max_height()) / 2;
/*     */       } else {
/*     */         
/* 191 */         k = paramInt1;
/* 192 */         m = paramInt1;
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     XToolkit.awtLock();
/*     */     try {
/* 198 */       XlibWrapper.XFree((arrayOfXIconSize[0]).pData);
/*     */     } finally {
/* 200 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/* 203 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 204 */       log.finest("return " + k + "x" + m);
/*     */     }
/* 206 */     return new Dimension(k, m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Dimension getIconSize(int paramInt1, int paramInt2) {
/* 213 */     if (this.size == null) {
/* 214 */       this.size = calcIconSize(paramInt1, paramInt2);
/*     */     }
/* 216 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void replaceImage(Image paramImage) {
/* 226 */     if (this.parent == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 232 */     BufferedImage bufferedImage = null;
/* 233 */     if (paramImage != null && this.iconWidth != 0 && this.iconHeight != 0) {
/* 234 */       GraphicsConfiguration graphicsConfiguration = this.parent.getGraphicsConfiguration().getDevice().getDefaultConfiguration();
/* 235 */       ColorModel colorModel = graphicsConfiguration.getColorModel();
/* 236 */       WritableRaster writableRaster = colorModel.createCompatibleWritableRaster(this.iconWidth, this.iconHeight);
/* 237 */       bufferedImage = new BufferedImage(colorModel, writableRaster, colorModel.isAlphaPremultiplied(), null);
/* 238 */       Graphics graphics = bufferedImage.getGraphics();
/*     */ 
/*     */       
/*     */       try {
/* 242 */         graphics.setColor(SystemColor.window);
/* 243 */         graphics.fillRect(0, 0, this.iconWidth, this.iconHeight);
/* 244 */         if (graphics instanceof Graphics2D) {
/* 245 */           ((Graphics2D)graphics).setComposite(AlphaComposite.Src);
/*     */         }
/* 247 */         graphics.drawImage(paramImage, 0, 0, this.iconWidth, this.iconHeight, null);
/*     */       } finally {
/* 249 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     XToolkit.awtLock();
/*     */     try {
/* 255 */       if (this.iconPixmap != 0L) {
/* 256 */         XlibWrapper.XFreePixmap(XToolkit.getDisplay(), this.iconPixmap);
/* 257 */         this.iconPixmap = 0L;
/* 258 */         log.finest("Freed previous pixmap");
/*     */       } 
/* 260 */       if (bufferedImage == null || this.iconWidth == 0 || this.iconHeight == 0) {
/*     */         return;
/*     */       }
/* 263 */       AwtGraphicsConfigData awtGraphicsConfigData = this.parent.getGraphicsConfigurationData();
/* 264 */       awtImageData awtImageData = awtGraphicsConfigData.get_awtImage(0);
/* 265 */       XVisualInfo xVisualInfo = awtGraphicsConfigData.get_awt_visInfo();
/* 266 */       this.iconPixmap = XlibWrapper.XCreatePixmap(XToolkit.getDisplay(), 
/* 267 */           XlibWrapper.RootWindow(XToolkit.getDisplay(), xVisualInfo.get_screen()), this.iconWidth, this.iconHeight, awtImageData
/*     */ 
/*     */           
/* 270 */           .get_Depth());
/*     */       
/* 272 */       if (this.iconPixmap == 0L) {
/* 273 */         log.finest("Can't create new pixmap for icon");
/*     */         
/*     */         return;
/*     */       } 
/* 277 */       long l1 = 0L;
/* 278 */       DataBuffer dataBuffer = bufferedImage.getData().getDataBuffer();
/* 279 */       if (dataBuffer instanceof DataBufferByte) {
/* 280 */         byte[] arrayOfByte = ((DataBufferByte)dataBuffer).getData();
/* 281 */         ColorData colorData = awtGraphicsConfigData.get_color_data(0);
/* 282 */         int m = colorData.get_awt_numICMcolors();
/* 283 */         for (byte b = 0; b < arrayOfByte.length; b++) {
/* 284 */           arrayOfByte[b] = (arrayOfByte[b] >= m) ? 0 : colorData
/* 285 */             .get_awt_icmLUT2Colors(arrayOfByte[b]);
/*     */         }
/* 287 */         l1 = Native.toData(arrayOfByte);
/* 288 */       } else if (dataBuffer instanceof DataBufferInt) {
/* 289 */         l1 = Native.toData(((DataBufferInt)dataBuffer).getData());
/* 290 */       } else if (dataBuffer instanceof DataBufferUShort) {
/* 291 */         l1 = Native.toData(((DataBufferUShort)dataBuffer).getData());
/*     */       } else {
/* 293 */         throw new IllegalArgumentException("Unknown data buffer: " + dataBuffer);
/*     */       } 
/* 295 */       int i = awtImageData.get_wsImageFormat().get_bits_per_pixel();
/* 296 */       int j = awtImageData.get_wsImageFormat().get_scanline_pad();
/* 297 */       int k = paddedwidth(this.iconWidth * i, j) >> 3;
/* 298 */       if ((k << 3) / i < this.iconWidth) {
/* 299 */         log.finest("Image format doesn't fit to icon width");
/*     */         return;
/*     */       } 
/* 302 */       long l2 = XlibWrapper.XCreateImage(XToolkit.getDisplay(), xVisualInfo
/* 303 */           .get_visual(), awtImageData
/* 304 */           .get_Depth(), 2, 0, l1, this.iconWidth, this.iconHeight, 32, k);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 312 */       if (l2 == 0L) {
/* 313 */         log.finest("Can't create XImage for icon");
/* 314 */         XlibWrapper.XFreePixmap(XToolkit.getDisplay(), this.iconPixmap);
/* 315 */         this.iconPixmap = 0L;
/*     */         return;
/*     */       } 
/* 318 */       log.finest("Created XImage for icon");
/*     */       
/* 320 */       long l3 = XlibWrapper.XCreateGC(XToolkit.getDisplay(), this.iconPixmap, 0L, 0L);
/* 321 */       if (l3 == 0L) {
/* 322 */         log.finest("Can't create GC for pixmap");
/* 323 */         XlibWrapper.XFreePixmap(XToolkit.getDisplay(), this.iconPixmap);
/* 324 */         this.iconPixmap = 0L;
/*     */         return;
/*     */       } 
/* 327 */       log.finest("Created GC for pixmap");
/*     */       
/*     */       try {
/* 330 */         XlibWrapper.XPutImage(XToolkit.getDisplay(), this.iconPixmap, l3, l2, 0, 0, 0, 0, this.iconWidth, this.iconHeight);
/*     */       } finally {
/*     */         
/* 333 */         XlibWrapper.XFreeGC(XToolkit.getDisplay(), l3);
/*     */       } 
/*     */     } finally {
/* 336 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void replaceMask(Image paramImage) {
/* 346 */     if (this.parent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 350 */     BufferedImage bufferedImage = null;
/* 351 */     if (paramImage != null && this.iconWidth != 0 && this.iconHeight != 0) {
/* 352 */       bufferedImage = new BufferedImage(this.iconWidth, this.iconHeight, 2);
/* 353 */       Graphics graphics = bufferedImage.getGraphics();
/*     */       try {
/* 355 */         graphics.drawImage(paramImage, 0, 0, this.iconWidth, this.iconHeight, null);
/*     */       } finally {
/* 357 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */     
/* 361 */     XToolkit.awtLock();
/*     */     try {
/* 363 */       if (this.iconMask != 0L) {
/* 364 */         XlibWrapper.XFreePixmap(XToolkit.getDisplay(), this.iconMask);
/* 365 */         this.iconMask = 0L;
/* 366 */         log.finest("Freed previous mask");
/*     */       } 
/* 368 */       if (bufferedImage == null || this.iconWidth == 0 || this.iconHeight == 0) {
/*     */         return;
/*     */       }
/* 371 */       AwtGraphicsConfigData awtGraphicsConfigData = this.parent.getGraphicsConfigurationData();
/* 372 */       awtImageData awtImageData = awtGraphicsConfigData.get_awtImage(0);
/* 373 */       XVisualInfo xVisualInfo = awtGraphicsConfigData.get_awt_visInfo();
/* 374 */       ColorModel colorModel = bufferedImage.getColorModel();
/* 375 */       DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
/* 376 */       byte b1 = 0;
/* 377 */       int i = this.iconWidth + 7 >> 3;
/* 378 */       byte[] arrayOfByte = new byte[i * this.iconHeight];
/* 379 */       byte b2 = 0;
/* 380 */       for (byte b3 = 0; b3 < this.iconHeight; b3++) {
/* 381 */         byte b4 = 0;
/* 382 */         int j = 0;
/* 383 */         for (byte b5 = 0; b5 < this.iconWidth; b5++) {
/* 384 */           if (colorModel.getAlpha(dataBuffer.getElem(b1)) != 0) {
/* 385 */             j += 1 << b4;
/*     */           }
/* 387 */           b4++;
/* 388 */           if (b4 == 8) {
/* 389 */             arrayOfByte[b2] = (byte)j;
/* 390 */             j = 0;
/* 391 */             b4 = 0;
/* 392 */             b2++;
/*     */           } 
/* 394 */           b1++;
/*     */         } 
/*     */       } 
/* 397 */       this.iconMask = XlibWrapper.XCreateBitmapFromData(XToolkit.getDisplay(), 
/* 398 */           XlibWrapper.RootWindow(XToolkit.getDisplay(), xVisualInfo.get_screen()), 
/* 399 */           Native.toData(arrayOfByte), this.iconWidth, this.iconHeight);
/*     */     } finally {
/*     */       
/* 402 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setIconImages(List<IconInfo> paramList) {
/* 411 */     if (paramList == null || paramList.size() == 0)
/*     */       return; 
/* 413 */     int i = Integer.MAX_VALUE;
/* 414 */     Image image = null;
/* 415 */     for (IconInfo iconInfo : paramList) {
/* 416 */       if (iconInfo.isValid()) {
/* 417 */         Image image1 = iconInfo.getImage();
/* 418 */         Dimension dimension = calcIconSize(image1.getWidth(null), image1.getHeight(null));
/* 419 */         int j = Math.abs(dimension.width - image1.getWidth(null));
/* 420 */         int k = Math.abs(image1.getHeight(null) - dimension.height);
/*     */ 
/*     */         
/* 423 */         if (i >= j + k) {
/* 424 */           i = j + k;
/* 425 */           image = image1;
/*     */         } 
/*     */       } 
/*     */     } 
/* 429 */     if (image != null) {
/* 430 */       if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 431 */         log.finer("Icon: {0}x{1}", new Object[] { Integer.valueOf(image.getWidth(null)), Integer.valueOf(image.getHeight(null)) });
/*     */       }
/* 433 */       setIconImage(image);
/*     */     } 
/*     */   }
/*     */   
/*     */   void setIconImage(Image paramImage) {
/* 438 */     if (paramImage == null) {
/*     */       
/* 440 */       replaceImage((Image)null);
/* 441 */       replaceMask((Image)null);
/*     */     } else {
/*     */       int i, j;
/*     */ 
/*     */       
/* 446 */       if (paramImage instanceof ToolkitImage) {
/* 447 */         ImageRepresentation imageRepresentation = ((ToolkitImage)paramImage).getImageRep();
/* 448 */         imageRepresentation.reconstruct(32);
/* 449 */         i = imageRepresentation.getWidth();
/* 450 */         j = imageRepresentation.getHeight();
/*     */       } else {
/*     */         
/* 453 */         i = paramImage.getWidth(null);
/* 454 */         j = paramImage.getHeight(null);
/*     */       } 
/* 456 */       Dimension dimension = getIconSize(i, j);
/* 457 */       if (dimension != null) {
/* 458 */         if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 459 */           log.finest("Icon size: {0}", new Object[] { dimension });
/*     */         }
/* 461 */         this.iconWidth = dimension.width;
/* 462 */         this.iconHeight = dimension.height;
/*     */       } else {
/* 464 */         log.finest("Error calculating image size");
/* 465 */         this.iconWidth = 0;
/* 466 */         this.iconHeight = 0;
/*     */       } 
/* 468 */       replaceImage(paramImage);
/* 469 */       replaceMask(paramImage);
/*     */     } 
/*     */     
/* 472 */     XToolkit.awtLock();
/*     */     try {
/* 474 */       AwtGraphicsConfigData awtGraphicsConfigData = this.parent.getGraphicsConfigurationData();
/* 475 */       awtImageData awtImageData = awtGraphicsConfigData.get_awtImage(0);
/* 476 */       XVisualInfo xVisualInfo = awtGraphicsConfigData.get_awt_visInfo();
/* 477 */       XWMHints xWMHints = this.parent.getWMHints();
/* 478 */       this.window = xWMHints.get_icon_window();
/* 479 */       if (this.window == 0L) {
/* 480 */         log.finest("Icon window wasn't set");
/* 481 */         XCreateWindowParams xCreateWindowParams = getDelayedParams();
/* 482 */         xCreateWindowParams.add("border pixel", Long.valueOf(XToolkit.getAwtDefaultFg()));
/* 483 */         xCreateWindowParams.add("pixmap", this.iconPixmap);
/* 484 */         xCreateWindowParams.add("color map", awtGraphicsConfigData.get_awt_cmap());
/* 485 */         xCreateWindowParams.add("visual depth", awtImageData.get_Depth());
/* 486 */         xCreateWindowParams.add("visual class", 1);
/* 487 */         xCreateWindowParams.add("visual", xVisualInfo.get_visual());
/* 488 */         xCreateWindowParams.add("value mask", 8201L);
/* 489 */         xCreateWindowParams.add("parent window", XlibWrapper.RootWindow(XToolkit.getDisplay(), xVisualInfo.get_screen()));
/* 490 */         xCreateWindowParams.add("bounds", new Rectangle(0, 0, this.iconWidth, this.iconHeight));
/* 491 */         xCreateWindowParams.remove("delayed");
/* 492 */         init(xCreateWindowParams);
/* 493 */         if (getWindow() == 0L) {
/* 494 */           log.finest("Can't create new icon window");
/*     */         } else {
/* 496 */           log.finest("Created new icon window");
/*     */         } 
/*     */       } 
/* 499 */       if (getWindow() != 0L) {
/* 500 */         XlibWrapper.XSetWindowBackgroundPixmap(XToolkit.getDisplay(), getWindow(), this.iconPixmap);
/* 501 */         XlibWrapper.XClearWindow(XToolkit.getDisplay(), getWindow());
/*     */       } 
/*     */       
/* 504 */       long l = xWMHints.get_flags() | 0x4L | 0x20L;
/* 505 */       if (getWindow() != 0L) {
/* 506 */         l |= 0x8L;
/*     */       }
/* 508 */       xWMHints.set_flags(l);
/* 509 */       xWMHints.set_icon_pixmap(this.iconPixmap);
/* 510 */       xWMHints.set_icon_mask(this.iconMask);
/* 511 */       xWMHints.set_icon_window(getWindow());
/* 512 */       XlibWrapper.XSetWMHints(XToolkit.getDisplay(), this.parent.getShell(), xWMHints.pData);
/* 513 */       log.finest("Set icon window hint");
/*     */     } finally {
/* 515 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static int paddedwidth(int paramInt1, int paramInt2) {
/* 521 */     return paramInt1 + paramInt2 - 1 & (paramInt2 - 1 ^ 0xFFFFFFFF);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XIconWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */