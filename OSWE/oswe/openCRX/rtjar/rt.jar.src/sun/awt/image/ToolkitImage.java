/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.ImageProducer;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToolkitImage
/*     */   extends Image
/*     */ {
/*     */   ImageProducer source;
/*     */   InputStreamImageSource src;
/*     */   ImageRepresentation imagerep;
/*     */   
/*     */   static {
/*  57 */     NativeLibLoader.loadLibraries();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolkitImage(ImageProducer paramImageProducer) {
/*  67 */     this.source = paramImageProducer;
/*  68 */     if (paramImageProducer instanceof InputStreamImageSource) {
/*  69 */       this.src = (InputStreamImageSource)paramImageProducer;
/*     */     }
/*     */   }
/*     */   
/*     */   public ImageProducer getSource() {
/*  74 */     if (this.src != null) {
/*  75 */       this.src.checkSecurity(null, false);
/*     */     }
/*  77 */     return this.source;
/*     */   }
/*     */   
/*  80 */   private int width = -1;
/*  81 */   private int height = -1;
/*     */ 
/*     */   
/*     */   private Hashtable properties;
/*     */ 
/*     */   
/*     */   private int availinfo;
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  91 */     if (this.src != null) {
/*  92 */       this.src.checkSecurity(null, false);
/*     */     }
/*  94 */     if ((this.availinfo & 0x1) == 0) {
/*  95 */       reconstruct(1);
/*     */     }
/*  97 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getWidth(ImageObserver paramImageObserver) {
/* 106 */     if (this.src != null) {
/* 107 */       this.src.checkSecurity(null, false);
/*     */     }
/* 109 */     if ((this.availinfo & 0x1) == 0) {
/* 110 */       addWatcher(paramImageObserver, true);
/* 111 */       if ((this.availinfo & 0x1) == 0) {
/* 112 */         return -1;
/*     */       }
/*     */     } 
/* 115 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 123 */     if (this.src != null) {
/* 124 */       this.src.checkSecurity(null, false);
/*     */     }
/* 126 */     if ((this.availinfo & 0x2) == 0) {
/* 127 */       reconstruct(2);
/*     */     }
/* 129 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getHeight(ImageObserver paramImageObserver) {
/* 138 */     if (this.src != null) {
/* 139 */       this.src.checkSecurity(null, false);
/*     */     }
/* 141 */     if ((this.availinfo & 0x2) == 0) {
/* 142 */       addWatcher(paramImageObserver, true);
/* 143 */       if ((this.availinfo & 0x2) == 0) {
/* 144 */         return -1;
/*     */       }
/*     */     } 
/* 147 */     return this.height;
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
/*     */   public Object getProperty(String paramString, ImageObserver paramImageObserver) {
/* 161 */     if (paramString == null) {
/* 162 */       throw new NullPointerException("null property name is not allowed");
/*     */     }
/*     */     
/* 165 */     if (this.src != null) {
/* 166 */       this.src.checkSecurity(null, false);
/*     */     }
/* 168 */     if (this.properties == null) {
/* 169 */       addWatcher(paramImageObserver, true);
/* 170 */       if (this.properties == null) {
/* 171 */         return null;
/*     */       }
/*     */     } 
/* 174 */     Object object = this.properties.get(paramString);
/* 175 */     if (object == null) {
/* 176 */       object = Image.UndefinedProperty;
/*     */     }
/* 178 */     return object;
/*     */   }
/*     */   
/*     */   public boolean hasError() {
/* 182 */     if (this.src != null) {
/* 183 */       this.src.checkSecurity(null, false);
/*     */     }
/* 185 */     return ((this.availinfo & 0x40) != 0);
/*     */   }
/*     */   
/*     */   public int check(ImageObserver paramImageObserver) {
/* 189 */     if (this.src != null) {
/* 190 */       this.src.checkSecurity(null, false);
/*     */     }
/* 192 */     if ((this.availinfo & 0x40) == 0 && ((this.availinfo ^ 0xFFFFFFFF) & 0x7) != 0)
/*     */     {
/*     */ 
/*     */       
/* 196 */       addWatcher(paramImageObserver, false);
/*     */     }
/* 198 */     return this.availinfo;
/*     */   }
/*     */   
/*     */   public void preload(ImageObserver paramImageObserver) {
/* 202 */     if (this.src != null) {
/* 203 */       this.src.checkSecurity(null, false);
/*     */     }
/* 205 */     if ((this.availinfo & 0x20) == 0) {
/* 206 */       addWatcher(paramImageObserver, true);
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized void addWatcher(ImageObserver paramImageObserver, boolean paramBoolean) {
/* 211 */     if ((this.availinfo & 0x40) != 0) {
/* 212 */       if (paramImageObserver != null) {
/* 213 */         paramImageObserver.imageUpdate(this, 192, -1, -1, -1, -1);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 218 */     ImageRepresentation imageRepresentation = getImageRep();
/* 219 */     imageRepresentation.addWatcher(paramImageObserver);
/* 220 */     if (paramBoolean) {
/* 221 */       imageRepresentation.startProduction();
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized void reconstruct(int paramInt) {
/* 226 */     if ((paramInt & (this.availinfo ^ 0xFFFFFFFF)) != 0) {
/* 227 */       if ((this.availinfo & 0x40) != 0) {
/*     */         return;
/*     */       }
/* 230 */       ImageRepresentation imageRepresentation = getImageRep();
/* 231 */       imageRepresentation.startProduction();
/* 232 */       while ((paramInt & (this.availinfo ^ 0xFFFFFFFF)) != 0) {
/*     */         try {
/* 234 */           wait();
/* 235 */         } catch (InterruptedException interruptedException) {
/* 236 */           Thread.currentThread().interrupt();
/*     */           return;
/*     */         } 
/* 239 */         if ((this.availinfo & 0x40) != 0) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void addInfo(int paramInt) {
/* 247 */     this.availinfo |= paramInt;
/* 248 */     notifyAll();
/*     */   }
/*     */   
/*     */   void setDimensions(int paramInt1, int paramInt2) {
/* 252 */     this.width = paramInt1;
/* 253 */     this.height = paramInt2;
/* 254 */     addInfo(3);
/*     */   }
/*     */   
/*     */   void setProperties(Hashtable<Object, Object> paramHashtable) {
/* 258 */     if (paramHashtable == null) {
/* 259 */       paramHashtable = new Hashtable<>();
/*     */     }
/* 261 */     this.properties = paramHashtable;
/* 262 */     addInfo(4);
/*     */   }
/*     */   
/*     */   synchronized void infoDone(int paramInt) {
/* 266 */     if (paramInt == 1 || ((this.availinfo ^ 0xFFFFFFFF) & 0x3) != 0) {
/*     */ 
/*     */       
/* 269 */       addInfo(64);
/* 270 */     } else if ((this.availinfo & 0x4) == 0) {
/* 271 */       setProperties(null);
/*     */     } 
/*     */   }
/*     */   public void flush() {
/*     */     ImageRepresentation imageRepresentation;
/* 276 */     if (this.src != null) {
/* 277 */       this.src.checkSecurity(null, false);
/*     */     }
/*     */ 
/*     */     
/* 281 */     synchronized (this) {
/* 282 */       this.availinfo &= 0xFFFFFFBF;
/* 283 */       imageRepresentation = this.imagerep;
/* 284 */       this.imagerep = null;
/*     */     } 
/* 286 */     if (imageRepresentation != null) {
/* 287 */       imageRepresentation.abort();
/*     */     }
/* 289 */     if (this.src != null) {
/* 290 */       this.src.flush();
/*     */     }
/*     */   }
/*     */   
/*     */   protected ImageRepresentation makeImageRep() {
/* 295 */     return new ImageRepresentation(this, ColorModel.getRGBdefault(), false);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized ImageRepresentation getImageRep() {
/* 300 */     if (this.src != null) {
/* 301 */       this.src.checkSecurity(null, false);
/*     */     }
/* 303 */     if (this.imagerep == null) {
/* 304 */       this.imagerep = makeImageRep();
/*     */     }
/* 306 */     return this.imagerep;
/*     */   }
/*     */   
/*     */   public Graphics getGraphics() {
/* 310 */     throw new UnsupportedOperationException("getGraphics() not valid for images created with createImage(producer)");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorModel getColorModel() {
/* 316 */     ImageRepresentation imageRepresentation = getImageRep();
/* 317 */     return imageRepresentation.getColorModel();
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage getBufferedImage() {
/* 322 */     ImageRepresentation imageRepresentation = getImageRep();
/* 323 */     return imageRepresentation.getBufferedImage();
/*     */   }
/*     */   
/*     */   public void setAccelerationPriority(float paramFloat) {
/* 327 */     super.setAccelerationPriority(paramFloat);
/* 328 */     ImageRepresentation imageRepresentation = getImageRep();
/* 329 */     imageRepresentation.setAccelerationPriority(this.accelerationPriority);
/*     */   }
/*     */   
/*     */   protected ToolkitImage() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ToolkitImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */