/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.IllegalComponentStateException;
/*     */ import java.awt.Image;
/*     */ import java.awt.MediaTracker;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.MemoryImageSource;
/*     */ import java.awt.image.PixelGrabber;
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.beans.Transient;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.URL;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.Locale;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleIcon;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleStateSet;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageIcon
/*     */   implements Icon, Serializable, Accessible
/*     */ {
/*     */   private transient String filename;
/*     */   private transient URL location;
/*     */   transient Image image;
/*  79 */   transient int loadStatus = 0;
/*     */   ImageObserver imageObserver;
/*  81 */   String description = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 100 */   protected static final Component component = AccessController.<Component>doPrivileged(new PrivilegedAction<Component>() {
/*     */         public Component run() {
/*     */           try {
/* 103 */             Component component = ImageIcon.createNoPermsComponent();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 108 */             Field field = Component.class.getDeclaredField("appContext");
/* 109 */             field.setAccessible(true);
/* 110 */             field.set(component, null);
/*     */             
/* 112 */             return component;
/* 113 */           } catch (Throwable throwable) {
/*     */ 
/*     */             
/* 116 */             throwable.printStackTrace();
/* 117 */             return null;
/*     */           } 
/*     */         }
/*     */       }); @Deprecated
/* 121 */   protected static final MediaTracker tracker = new MediaTracker(component);
/*     */   
/*     */   private static int mediaTrackerID;
/*     */ 
/*     */   
/*     */   private static Component createNoPermsComponent() {
/* 127 */     return AccessController.<Component>doPrivileged(new PrivilegedAction<Component>()
/*     */         {
/*     */           public Component run() {
/* 130 */             return new Component()
/*     */               {
/*     */               
/*     */               },  ;
/*     */           }
/*     */         },  new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, null) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   private static final Object TRACKER_KEY = new StringBuilder("TRACKER_KEY");
/*     */   
/* 147 */   int width = -1;
/* 148 */   int height = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AccessibleImageIcon accessibleContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ConstructorProperties({"description"})
/*     */   public ImageIcon(String paramString) {
/* 186 */     this(paramString, paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageIcon(URL paramURL) {
/* 217 */     this(paramURL, paramURL.toExternalForm());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageIcon(Image paramImage, String paramString) {
/* 226 */     this(paramImage);
/* 227 */     this.description = paramString;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadImage(Image paramImage) {
/* 308 */     MediaTracker mediaTracker = getTracker();
/* 309 */     synchronized (mediaTracker) {
/* 310 */       int i = getNextID();
/*     */       
/* 312 */       mediaTracker.addImage(paramImage, i);
/*     */       try {
/* 314 */         mediaTracker.waitForID(i, 0L);
/* 315 */       } catch (InterruptedException interruptedException) {
/* 316 */         System.out.println("INTERRUPTED while loading Image");
/*     */       } 
/* 318 */       this.loadStatus = mediaTracker.statusID(i, false);
/* 319 */       mediaTracker.removeImage(paramImage, i);
/*     */       
/* 321 */       this.width = paramImage.getWidth(this.imageObserver);
/* 322 */       this.height = paramImage.getHeight(this.imageObserver);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getNextID() {
/* 330 */     synchronized (getTracker()) {
/* 331 */       return ++mediaTrackerID;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MediaTracker getTracker() {
/*     */     Object object;
/* 341 */     AppContext appContext = AppContext.getAppContext();
/*     */ 
/*     */     
/* 344 */     synchronized (appContext) {
/* 345 */       object = appContext.get(TRACKER_KEY);
/* 346 */       if (object == null) {
/* 347 */         Component component = new Component() {  };
/* 348 */         object = new MediaTracker(component);
/* 349 */         appContext.put(TRACKER_KEY, object);
/*     */       } 
/*     */     } 
/* 352 */     return (MediaTracker)object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageLoadStatus() {
/* 363 */     return this.loadStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transient
/*     */   public Image getImage() {
/* 372 */     return this.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImage(Image paramImage) {
/* 380 */     this.image = paramImage;
/* 381 */     loadImage(paramImage);
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
/*     */   public String getDescription() {
/* 394 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String paramString) {
/* 405 */     this.description = paramString;
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
/*     */   public synchronized void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 424 */     if (this.imageObserver == null) {
/* 425 */       paramGraphics.drawImage(this.image, paramInt1, paramInt2, paramComponent);
/*     */     } else {
/* 427 */       paramGraphics.drawImage(this.image, paramInt1, paramInt2, this.imageObserver);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconWidth() {
/* 437 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconHeight() {
/* 446 */     return this.height;
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
/*     */   public void setImageObserver(ImageObserver paramImageObserver) {
/* 463 */     this.imageObserver = paramImageObserver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transient
/*     */   public ImageObserver getImageObserver() {
/* 473 */     return this.imageObserver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 482 */     if (this.description != null) {
/* 483 */       return this.description;
/*     */     }
/* 485 */     return super.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 491 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 493 */     int i = paramObjectInputStream.readInt();
/* 494 */     int j = paramObjectInputStream.readInt();
/* 495 */     int[] arrayOfInt = (int[])paramObjectInputStream.readObject();
/*     */     
/* 497 */     if (arrayOfInt != null) {
/* 498 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 499 */       ColorModel colorModel = ColorModel.getRGBdefault();
/* 500 */       this.image = toolkit.createImage(new MemoryImageSource(i, j, colorModel, arrayOfInt, 0, i));
/* 501 */       loadImage(this.image);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 509 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 511 */     int i = getIconWidth();
/* 512 */     int j = getIconHeight();
/* 513 */     int[] arrayOfInt = (this.image != null) ? new int[i * j] : null;
/*     */     
/* 515 */     if (this.image != null) {
/*     */       try {
/* 517 */         PixelGrabber pixelGrabber = new PixelGrabber(this.image, 0, 0, i, j, arrayOfInt, 0, i);
/* 518 */         pixelGrabber.grabPixels();
/* 519 */         if ((pixelGrabber.getStatus() & 0x80) != 0) {
/* 520 */           throw new IOException("failed to load image contents");
/*     */         }
/*     */       }
/* 523 */       catch (InterruptedException interruptedException) {
/* 524 */         throw new IOException("image load interrupted");
/*     */       } 
/*     */     }
/*     */     
/* 528 */     paramObjectOutputStream.writeInt(i);
/* 529 */     paramObjectOutputStream.writeInt(j);
/* 530 */     paramObjectOutputStream.writeObject(arrayOfInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageIcon(String paramString1, String paramString2) {
/* 537 */     this.accessibleContext = null; this.image = Toolkit.getDefaultToolkit().getImage(paramString1); if (this.image == null) return;  this.filename = paramString1; this.description = paramString2; loadImage(this.image); } public ImageIcon(URL paramURL, String paramString) { this.accessibleContext = null; this.image = Toolkit.getDefaultToolkit().getImage(paramURL); if (this.image == null) return;  this.location = paramURL; this.description = paramString; loadImage(this.image); } public ImageIcon(Image paramImage) { this.accessibleContext = null; this.image = paramImage; Object object = paramImage.getProperty("comment", this.imageObserver); if (object instanceof String) this.description = (String)object;  loadImage(paramImage); } public ImageIcon(byte[] paramArrayOfbyte, String paramString) { this.accessibleContext = null; this.image = Toolkit.getDefaultToolkit().createImage(paramArrayOfbyte); if (this.image == null) return;  this.description = paramString; loadImage(this.image); } public ImageIcon(byte[] paramArrayOfbyte) { this.accessibleContext = null; this.image = Toolkit.getDefaultToolkit().createImage(paramArrayOfbyte); if (this.image == null) return;  Object object = this.image.getProperty("comment", this.imageObserver); if (object instanceof String) this.description = (String)object;  loadImage(this.image); } public ImageIcon() { this.accessibleContext = null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleContext getAccessibleContext() {
/* 553 */     if (this.accessibleContext == null) {
/* 554 */       this.accessibleContext = new AccessibleImageIcon();
/*     */     }
/* 556 */     return this.accessibleContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class AccessibleImageIcon
/*     */     extends AccessibleContext
/*     */     implements AccessibleIcon, Serializable
/*     */   {
/*     */     public AccessibleRole getAccessibleRole() {
/* 590 */       return AccessibleRole.ICON;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleStateSet getAccessibleStateSet() {
/* 601 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Accessible getAccessibleParent() {
/* 613 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getAccessibleIndexInParent() {
/* 624 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getAccessibleChildrenCount() {
/* 635 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Accessible getAccessibleChild(int param1Int) {
/* 645 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Locale getLocale() throws IllegalComponentStateException {
/* 654 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAccessibleIconDescription() {
/* 670 */       return ImageIcon.this.getDescription();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setAccessibleIconDescription(String param1String) {
/* 682 */       ImageIcon.this.setDescription(param1String);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getAccessibleIconHeight() {
/* 691 */       return ImageIcon.this.height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getAccessibleIconWidth() {
/* 700 */       return ImageIcon.this.width;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void readObject(ObjectInputStream param1ObjectInputStream) throws ClassNotFoundException, IOException {
/* 706 */       param1ObjectInputStream.defaultReadObject();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 712 */       param1ObjectOutputStream.defaultWriteObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ImageIcon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */