/*     */ package sun.swing;
/*     */ 
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageCache
/*     */ {
/*     */   private int maxCount;
/*     */   private final LinkedList<SoftReference<Entry>> entries;
/*     */   
/*     */   public ImageCache(int paramInt) {
/*  43 */     this.maxCount = paramInt;
/*  44 */     this.entries = new LinkedList<>();
/*     */   }
/*     */   
/*     */   void setMaxCount(int paramInt) {
/*  48 */     this.maxCount = paramInt;
/*     */   }
/*     */   
/*     */   public void flush() {
/*  52 */     this.entries.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Entry getEntry(Object paramObject, GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  58 */     ListIterator<SoftReference<Entry>> listIterator = this.entries.listIterator();
/*  59 */     while (listIterator.hasNext()) {
/*  60 */       SoftReference<Entry> softReference = listIterator.next();
/*  61 */       Entry entry1 = softReference.get();
/*  62 */       if (entry1 == null) {
/*     */         
/*  64 */         listIterator.remove(); continue;
/*     */       } 
/*  66 */       if (entry1.equals(paramGraphicsConfiguration, paramInt1, paramInt2, paramArrayOfObject)) {
/*     */         
/*  68 */         listIterator.remove();
/*  69 */         this.entries.addFirst(softReference);
/*  70 */         return entry1;
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     Entry entry = new Entry(paramGraphicsConfiguration, paramInt1, paramInt2, paramArrayOfObject);
/*  75 */     if (this.entries.size() >= this.maxCount) {
/*  76 */       this.entries.removeLast();
/*     */     }
/*  78 */     this.entries.addFirst(new SoftReference<>(entry));
/*  79 */     return entry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getImage(Object paramObject, GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/*  87 */     Entry entry = getEntry(paramObject, paramGraphicsConfiguration, paramInt1, paramInt2, paramArrayOfObject);
/*  88 */     return entry.getImage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImage(Object paramObject, GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, Object[] paramArrayOfObject, Image paramImage) {
/*  96 */     Entry entry = getEntry(paramObject, paramGraphicsConfiguration, paramInt1, paramInt2, paramArrayOfObject);
/*  97 */     entry.setImage(paramImage);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Entry
/*     */   {
/*     */     private final GraphicsConfiguration config;
/*     */     
/*     */     private final int w;
/*     */     
/*     */     private final int h;
/*     */     private final Object[] args;
/*     */     private Image image;
/*     */     
/*     */     Entry(GraphicsConfiguration param1GraphicsConfiguration, int param1Int1, int param1Int2, Object[] param1ArrayOfObject) {
/* 112 */       this.config = param1GraphicsConfiguration;
/* 113 */       this.args = param1ArrayOfObject;
/* 114 */       this.w = param1Int1;
/* 115 */       this.h = param1Int2;
/*     */     }
/*     */     
/*     */     public void setImage(Image param1Image) {
/* 119 */       this.image = param1Image;
/*     */     }
/*     */     
/*     */     public Image getImage() {
/* 123 */       return this.image;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 127 */       String str = super.toString() + "[ graphicsConfig=" + this.config + ", image=" + this.image + ", w=" + this.w + ", h=" + this.h;
/*     */ 
/*     */ 
/*     */       
/* 131 */       if (this.args != null) {
/* 132 */         for (byte b = 0; b < this.args.length; b++) {
/* 133 */           str = str + ", " + this.args[b];
/*     */         }
/*     */       }
/* 136 */       str = str + "]";
/* 137 */       return str;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(GraphicsConfiguration param1GraphicsConfiguration, int param1Int1, int param1Int2, Object[] param1ArrayOfObject) {
/* 142 */       if (this.w == param1Int1 && this.h == param1Int2 && ((this.config != null && this.config
/* 143 */         .equals(param1GraphicsConfiguration)) || (this.config == null && param1GraphicsConfiguration == null))) {
/*     */         
/* 145 */         if (this.args == null && param1ArrayOfObject == null) {
/* 146 */           return true;
/*     */         }
/* 148 */         if (this.args != null && param1ArrayOfObject != null && this.args.length == param1ArrayOfObject.length) {
/*     */           
/* 150 */           for (int i = param1ArrayOfObject.length - 1; i >= 0; 
/* 151 */             i--) {
/* 152 */             Object object1 = this.args[i];
/* 153 */             Object object2 = param1ArrayOfObject[i];
/* 154 */             if ((object1 == null && object2 != null) || (object1 != null && 
/* 155 */               !object1.equals(object2))) {
/* 156 */               return false;
/*     */             }
/*     */           } 
/* 159 */           return true;
/*     */         } 
/*     */       } 
/* 162 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/ImageCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */