/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ImageWatched
/*     */ {
/*  37 */   public static Link endlink = new Link();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public Link watcherList = endlink;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Link
/*     */   {
/*     */     public boolean isWatcher(ImageObserver param1ImageObserver) {
/*  58 */       return false;
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
/*     */     public Link removeWatcher(ImageObserver param1ImageObserver) {
/*  73 */       return this;
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
/*     */     public boolean newInfo(Image param1Image, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*  88 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   static class AccWeakReference<T>
/*     */     extends WeakReference<T> {
/*     */     private final AccessControlContext acc;
/*     */     
/*     */     AccWeakReference(T param1T) {
/*  97 */       super(param1T);
/*  98 */       this.acc = AccessController.getContext();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class WeakLink
/*     */     extends Link
/*     */   {
/*     */     private final ImageWatched.AccWeakReference<ImageObserver> myref;
/*     */     
/*     */     private ImageWatched.Link next;
/*     */     
/*     */     public WeakLink(ImageObserver param1ImageObserver, ImageWatched.Link param1Link) {
/* 111 */       this.myref = new ImageWatched.AccWeakReference<>(param1ImageObserver);
/* 112 */       this.next = param1Link;
/*     */     }
/*     */     
/*     */     public boolean isWatcher(ImageObserver param1ImageObserver) {
/* 116 */       return (this.myref.get() == param1ImageObserver || this.next.isWatcher(param1ImageObserver));
/*     */     }
/*     */     
/*     */     public ImageWatched.Link removeWatcher(ImageObserver param1ImageObserver) {
/* 120 */       ImageObserver imageObserver = this.myref.get();
/* 121 */       if (imageObserver == null)
/*     */       {
/* 123 */         return this.next.removeWatcher(param1ImageObserver);
/*     */       }
/*     */ 
/*     */       
/* 127 */       if (imageObserver == param1ImageObserver)
/*     */       {
/* 129 */         return this.next;
/*     */       }
/*     */ 
/*     */       
/* 133 */       this.next = this.next.removeWatcher(param1ImageObserver);
/* 134 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean update(ImageObserver param1ImageObserver, AccessControlContext param1AccessControlContext, Image param1Image, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 141 */       if (param1AccessControlContext != null || System.getSecurityManager() != null) {
/* 142 */         return ((Boolean)AccessController.<Boolean>doPrivileged(() -> Boolean.valueOf(param1ImageObserver.imageUpdate(param1Image, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5)), param1AccessControlContext)).booleanValue();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 147 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean newInfo(Image param1Image, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 154 */       boolean bool = this.next.newInfo(param1Image, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/* 155 */       ImageObserver imageObserver = this.myref.get();
/* 156 */       if (imageObserver == null) {
/*     */         
/* 158 */         bool = true;
/* 159 */       } else if (!update(imageObserver, this.myref.acc, param1Image, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5)) {
/*     */ 
/*     */         
/* 162 */         this.myref.clear();
/* 163 */         bool = true;
/*     */       } 
/* 165 */       return bool;
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void addWatcher(ImageObserver paramImageObserver) {
/* 170 */     if (paramImageObserver != null && !isWatcher(paramImageObserver)) {
/* 171 */       this.watcherList = new WeakLink(paramImageObserver, this.watcherList);
/*     */     }
/* 173 */     this.watcherList = this.watcherList.removeWatcher(null);
/*     */   }
/*     */   
/*     */   public synchronized boolean isWatcher(ImageObserver paramImageObserver) {
/* 177 */     return this.watcherList.isWatcher(paramImageObserver);
/*     */   }
/*     */   
/*     */   public void removeWatcher(ImageObserver paramImageObserver) {
/* 181 */     synchronized (this) {
/* 182 */       this.watcherList = this.watcherList.removeWatcher(paramImageObserver);
/*     */     } 
/* 184 */     if (this.watcherList == endlink) {
/* 185 */       notifyWatcherListEmpty();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isWatcherListEmpty() {
/* 190 */     synchronized (this) {
/* 191 */       this.watcherList = this.watcherList.removeWatcher(null);
/*     */     } 
/* 193 */     return (this.watcherList == endlink);
/*     */   }
/*     */   
/*     */   public void newInfo(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 197 */     if (this.watcherList.newInfo(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5))
/*     */     {
/* 199 */       removeWatcher(null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract void notifyWatcherListEmpty();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ImageWatched.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */