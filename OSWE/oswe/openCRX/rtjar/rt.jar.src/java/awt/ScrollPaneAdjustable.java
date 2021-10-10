/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.AdjustmentListener;
/*     */ import java.awt.peer.ScrollPanePeer;
/*     */ import java.io.Serializable;
/*     */ import sun.awt.AWTAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScrollPaneAdjustable
/*     */   implements Adjustable, Serializable
/*     */ {
/*     */   private ScrollPane sp;
/*     */   private int orientation;
/*     */   private int value;
/*     */   private int minimum;
/*     */   private int maximum;
/*     */   private int visibleAmount;
/*     */   private transient boolean isAdjusting;
/* 128 */   private int unitIncrement = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   private int blockIncrement = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   private AdjustmentListener adjustmentListener;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String SCROLLPANE_ONLY = "Can be set by scrollpane only";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -3359745691033257079L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 157 */     Toolkit.loadLibraries();
/* 158 */     if (!GraphicsEnvironment.isHeadless()) {
/* 159 */       initIDs();
/*     */     }
/* 161 */     AWTAccessor.setScrollPaneAdjustableAccessor(new AWTAccessor.ScrollPaneAdjustableAccessor()
/*     */         {
/*     */           public void setTypedValue(ScrollPaneAdjustable param1ScrollPaneAdjustable, int param1Int1, int param1Int2) {
/* 164 */             param1ScrollPaneAdjustable.setTypedValue(param1Int1, param1Int2);
/*     */           }
/*     */         });
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
/*     */   ScrollPaneAdjustable(ScrollPane paramScrollPane, AdjustmentListener paramAdjustmentListener, int paramInt) {
/* 186 */     this.sp = paramScrollPane;
/* 187 */     this.orientation = paramInt;
/* 188 */     addAdjustmentListener(paramAdjustmentListener);
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
/*     */   void setSpan(int paramInt1, int paramInt2, int paramInt3) {
/* 200 */     this.minimum = paramInt1;
/* 201 */     this.maximum = Math.max(paramInt2, this.minimum + 1);
/* 202 */     this.visibleAmount = Math.min(paramInt3, this.maximum - this.minimum);
/* 203 */     this.visibleAmount = Math.max(this.visibleAmount, 1);
/* 204 */     this.blockIncrement = Math.max((int)(paramInt3 * 0.9D), 1);
/* 205 */     setValue(this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrientation() {
/* 215 */     return this.orientation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinimum(int paramInt) {
/* 226 */     throw new AWTError("Can be set by scrollpane only");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinimum() {
/* 232 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaximum(int paramInt) {
/* 243 */     throw new AWTError("Can be set by scrollpane only");
/*     */   }
/*     */   
/*     */   public int getMaximum() {
/* 247 */     return this.maximum;
/*     */   }
/*     */   
/*     */   public synchronized void setUnitIncrement(int paramInt) {
/* 251 */     if (paramInt != this.unitIncrement) {
/* 252 */       this.unitIncrement = paramInt;
/* 253 */       if (this.sp.peer != null) {
/* 254 */         ScrollPanePeer scrollPanePeer = (ScrollPanePeer)this.sp.peer;
/* 255 */         scrollPanePeer.setUnitIncrement(this, paramInt);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getUnitIncrement() {
/* 261 */     return this.unitIncrement;
/*     */   }
/*     */   
/*     */   public synchronized void setBlockIncrement(int paramInt) {
/* 265 */     this.blockIncrement = paramInt;
/*     */   }
/*     */   
/*     */   public int getBlockIncrement() {
/* 269 */     return this.blockIncrement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisibleAmount(int paramInt) {
/* 280 */     throw new AWTError("Can be set by scrollpane only");
/*     */   }
/*     */   
/*     */   public int getVisibleAmount() {
/* 284 */     return this.visibleAmount;
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
/*     */   public void setValueIsAdjusting(boolean paramBoolean) {
/* 296 */     if (this.isAdjusting != paramBoolean) {
/* 297 */       this.isAdjusting = paramBoolean;
/* 298 */       AdjustmentEvent adjustmentEvent = new AdjustmentEvent(this, 601, 5, this.value, paramBoolean);
/*     */ 
/*     */ 
/*     */       
/* 302 */       this.adjustmentListener.adjustmentValueChanged(adjustmentEvent);
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
/*     */   public boolean getValueIsAdjusting() {
/* 314 */     return this.isAdjusting;
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
/*     */   public void setValue(int paramInt) {
/* 327 */     setTypedValue(paramInt, 5);
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
/*     */   private void setTypedValue(int paramInt1, int paramInt2) {
/* 342 */     paramInt1 = Math.max(paramInt1, this.minimum);
/* 343 */     paramInt1 = Math.min(paramInt1, this.maximum - this.visibleAmount);
/*     */     
/* 345 */     if (paramInt1 != this.value) {
/* 346 */       this.value = paramInt1;
/*     */ 
/*     */ 
/*     */       
/* 350 */       AdjustmentEvent adjustmentEvent = new AdjustmentEvent(this, 601, paramInt2, this.value, this.isAdjusting);
/*     */ 
/*     */ 
/*     */       
/* 354 */       this.adjustmentListener.adjustmentValueChanged(adjustmentEvent);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getValue() {
/* 359 */     return this.value;
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
/*     */   public synchronized void addAdjustmentListener(AdjustmentListener paramAdjustmentListener) {
/* 377 */     if (paramAdjustmentListener == null) {
/*     */       return;
/*     */     }
/* 380 */     this.adjustmentListener = AWTEventMulticaster.add(this.adjustmentListener, paramAdjustmentListener);
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
/*     */   public synchronized void removeAdjustmentListener(AdjustmentListener paramAdjustmentListener) {
/* 399 */     if (paramAdjustmentListener == null) {
/*     */       return;
/*     */     }
/* 402 */     this.adjustmentListener = AWTEventMulticaster.remove(this.adjustmentListener, paramAdjustmentListener);
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
/*     */   public synchronized AdjustmentListener[] getAdjustmentListeners() {
/* 421 */     return AWTEventMulticaster.<AdjustmentListener>getListeners(this.adjustmentListener, AdjustmentListener.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 431 */     return getClass().getName() + "[" + paramString() + "]";
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
/*     */   public String paramString() {
/* 444 */     return ((this.orientation == 1) ? "vertical," : "horizontal,") + "[0.." + this.maximum + "],val=" + this.value + ",vis=" + this.visibleAmount + ",unit=" + this.unitIncrement + ",block=" + this.blockIncrement + ",isAdjusting=" + this.isAdjusting;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/ScrollPaneAdjustable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */