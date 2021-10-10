/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WindowDimensions
/*     */ {
/*     */   private Point loc;
/*     */   private Dimension size;
/*     */   private Insets insets;
/*     */   private boolean isClientSizeSet;
/*     */   
/*     */   public WindowDimensions(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  40 */     this(new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4), null, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WindowDimensions(Rectangle paramRectangle, Insets paramInsets, boolean paramBoolean) {
/*  48 */     if (paramRectangle == null) {
/*  49 */       throw new IllegalArgumentException("Client bounds can't be null");
/*     */     }
/*  51 */     this.isClientSizeSet = paramBoolean;
/*  52 */     this.loc = paramRectangle.getLocation();
/*  53 */     this.size = paramRectangle.getSize();
/*  54 */     setInsets(paramInsets);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WindowDimensions(Point paramPoint, Dimension paramDimension, Insets paramInsets, boolean paramBoolean) {
/*  62 */     this(new Rectangle(paramPoint, paramDimension), paramInsets, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WindowDimensions(Rectangle paramRectangle, boolean paramBoolean) {
/*  70 */     this(paramRectangle, null, paramBoolean);
/*     */   }
/*     */   
/*     */   public WindowDimensions(WindowDimensions paramWindowDimensions) {
/*  74 */     this.loc = new Point(paramWindowDimensions.loc);
/*  75 */     this.size = new Dimension(paramWindowDimensions.size);
/*  76 */     this.insets = (paramWindowDimensions.insets != null) ? (Insets)paramWindowDimensions.insets.clone() : new Insets(0, 0, 0, 0);
/*  77 */     this.isClientSizeSet = paramWindowDimensions.isClientSizeSet;
/*     */   }
/*     */   
/*     */   public Rectangle getClientRect() {
/*  81 */     if (this.isClientSizeSet) {
/*  82 */       return new Rectangle(this.loc, this.size);
/*     */     }
/*     */     
/*  85 */     if (this.insets != null) {
/*  86 */       return new Rectangle(this.loc.x, this.loc.y, this.size.width - this.insets.left + this.insets.right, this.size.height - this.insets.top + this.insets.bottom);
/*     */     }
/*     */ 
/*     */     
/*  90 */     return new Rectangle(this.loc, this.size);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClientSize(Dimension paramDimension) {
/*  96 */     this.size = new Dimension(paramDimension);
/*  97 */     this.isClientSizeSet = true;
/*     */   }
/*     */   
/*     */   public void setClientSize(int paramInt1, int paramInt2) {
/* 101 */     this.size = new Dimension(paramInt1, paramInt2);
/* 102 */     this.isClientSizeSet = true;
/*     */   }
/*     */   
/*     */   public Dimension getClientSize() {
/* 106 */     return getClientRect().getSize();
/*     */   }
/*     */   
/*     */   public void setSize(int paramInt1, int paramInt2) {
/* 110 */     this.size = new Dimension(paramInt1, paramInt2);
/* 111 */     this.isClientSizeSet = false;
/*     */   }
/*     */   
/*     */   public Dimension getSize() {
/* 115 */     return getBounds().getSize();
/*     */   }
/*     */   
/*     */   public Insets getInsets() {
/* 119 */     return (Insets)this.insets.clone();
/*     */   }
/*     */   public Rectangle getBounds() {
/* 122 */     if (this.isClientSizeSet) {
/* 123 */       Rectangle rectangle = new Rectangle(this.loc, this.size);
/* 124 */       rectangle.width += this.insets.left + this.insets.right;
/* 125 */       rectangle.height += this.insets.top + this.insets.bottom;
/* 126 */       return rectangle;
/*     */     } 
/* 128 */     return new Rectangle(this.loc, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public Point getLocation() {
/* 133 */     return new Point(this.loc);
/*     */   }
/*     */   public void setLocation(int paramInt1, int paramInt2) {
/* 136 */     this.loc = new Point(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public Rectangle getScreenBounds() {
/* 140 */     Dimension dimension = getClientSize();
/* 141 */     Point point = getLocation();
/* 142 */     point.x += this.insets.left;
/* 143 */     point.y += this.insets.top;
/* 144 */     return new Rectangle(point, dimension);
/*     */   }
/*     */   
/*     */   public void setInsets(Insets paramInsets) {
/* 148 */     this.insets = (paramInsets != null) ? (Insets)paramInsets.clone() : new Insets(0, 0, 0, 0);
/* 149 */     if (!this.isClientSizeSet) {
/* 150 */       if (this.size.width < this.insets.left + this.insets.right) {
/* 151 */         this.size.width = this.insets.left + this.insets.right;
/*     */       }
/* 153 */       if (this.size.height < this.insets.top + this.insets.bottom) {
/* 154 */         this.size.height = this.insets.top + this.insets.bottom;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isClientSizeSet() {
/* 160 */     return this.isClientSizeSet;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 164 */     return "[" + this.loc + ", " + this.size + "(" + (this.isClientSizeSet ? "client" : "bounds") + ")+" + this.insets + "]";
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 168 */     if (!(paramObject instanceof WindowDimensions)) {
/* 169 */       return false;
/*     */     }
/* 171 */     WindowDimensions windowDimensions = (WindowDimensions)paramObject;
/* 172 */     return (windowDimensions.insets.equals(this.insets) && 
/* 173 */       getClientRect().equals(windowDimensions.getClientRect()) && 
/* 174 */       getBounds().equals(windowDimensions.getBounds()));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 178 */     return ((this.insets == null) ? 0 : this.insets.hashCode()) ^ getClientRect().hashCode() ^ getBounds().hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/WindowDimensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */