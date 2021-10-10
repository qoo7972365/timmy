/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicScrollBarUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthScrollBarUI
/*     */   extends BasicScrollBarUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   private SynthStyle thumbStyle;
/*     */   private SynthStyle trackStyle;
/*     */   private boolean validMinimumThumbSize;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  52 */     return new SynthScrollBarUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  60 */     super.installDefaults();
/*  61 */     this.trackHighlight = 0;
/*  62 */     if (this.scrollbar.getLayout() == null || this.scrollbar
/*  63 */       .getLayout() instanceof javax.swing.plaf.UIResource) {
/*  64 */       this.scrollbar.setLayout(this);
/*     */     }
/*  66 */     configureScrollBarColors();
/*  67 */     updateStyle(this.scrollbar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configureScrollBarColors() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateStyle(JScrollBar paramJScrollBar) {
/*  78 */     SynthStyle synthStyle = this.style;
/*  79 */     SynthContext synthContext = getContext(paramJScrollBar, 1);
/*  80 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  81 */     if (this.style != synthStyle) {
/*  82 */       this.scrollBarWidth = this.style.getInt(synthContext, "ScrollBar.thumbHeight", 14);
/*  83 */       this.minimumThumbSize = (Dimension)this.style.get(synthContext, "ScrollBar.minimumThumbSize");
/*     */       
/*  85 */       if (this.minimumThumbSize == null) {
/*  86 */         this.minimumThumbSize = new Dimension();
/*  87 */         this.validMinimumThumbSize = false;
/*     */       } else {
/*     */         
/*  90 */         this.validMinimumThumbSize = true;
/*     */       } 
/*  92 */       this.maximumThumbSize = (Dimension)this.style.get(synthContext, "ScrollBar.maximumThumbSize");
/*     */       
/*  94 */       if (this.maximumThumbSize == null) {
/*  95 */         this.maximumThumbSize = new Dimension(4096, 4097);
/*     */       }
/*     */       
/*  98 */       this.incrGap = this.style.getInt(synthContext, "ScrollBar.incrementButtonGap", 0);
/*  99 */       this.decrGap = this.style.getInt(synthContext, "ScrollBar.decrementButtonGap", 0);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       String str = (String)this.scrollbar.getClientProperty("JComponent.sizeVariant");
/*     */       
/* 106 */       if (str != null) {
/* 107 */         if ("large".equals(str)) {
/* 108 */           this.scrollBarWidth = (int)(this.scrollBarWidth * 1.15D);
/* 109 */           this.incrGap = (int)(this.incrGap * 1.15D);
/* 110 */           this.decrGap = (int)(this.decrGap * 1.15D);
/* 111 */         } else if ("small".equals(str)) {
/* 112 */           this.scrollBarWidth = (int)(this.scrollBarWidth * 0.857D);
/* 113 */           this.incrGap = (int)(this.incrGap * 0.857D);
/* 114 */           this.decrGap = (int)(this.decrGap * 0.857D);
/* 115 */         } else if ("mini".equals(str)) {
/* 116 */           this.scrollBarWidth = (int)(this.scrollBarWidth * 0.714D);
/* 117 */           this.incrGap = (int)(this.incrGap * 0.714D);
/* 118 */           this.decrGap = (int)(this.decrGap * 0.714D);
/*     */         } 
/*     */       }
/*     */       
/* 122 */       if (synthStyle != null) {
/* 123 */         uninstallKeyboardActions();
/* 124 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 127 */     synthContext.dispose();
/*     */     
/* 129 */     synthContext = getContext(paramJScrollBar, Region.SCROLL_BAR_TRACK, 1);
/* 130 */     this.trackStyle = SynthLookAndFeel.updateStyle(synthContext, this);
/* 131 */     synthContext.dispose();
/*     */     
/* 133 */     synthContext = getContext(paramJScrollBar, Region.SCROLL_BAR_THUMB, 1);
/* 134 */     this.thumbStyle = SynthLookAndFeel.updateStyle(synthContext, this);
/* 135 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 143 */     super.installListeners();
/* 144 */     this.scrollbar.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 152 */     super.uninstallListeners();
/* 153 */     this.scrollbar.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 161 */     SynthContext synthContext = getContext(this.scrollbar, 1);
/* 162 */     this.style.uninstallDefaults(synthContext);
/* 163 */     synthContext.dispose();
/* 164 */     this.style = null;
/*     */     
/* 166 */     synthContext = getContext(this.scrollbar, Region.SCROLL_BAR_TRACK, 1);
/* 167 */     this.trackStyle.uninstallDefaults(synthContext);
/* 168 */     synthContext.dispose();
/* 169 */     this.trackStyle = null;
/*     */     
/* 171 */     synthContext = getContext(this.scrollbar, Region.SCROLL_BAR_THUMB, 1);
/* 172 */     this.thumbStyle.uninstallDefaults(synthContext);
/* 173 */     synthContext.dispose();
/* 174 */     this.thumbStyle = null;
/*     */     
/* 176 */     super.uninstallDefaults();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 184 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 188 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion) {
/* 192 */     return getContext(paramJComponent, paramRegion, getComponentState(paramJComponent, paramRegion));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion, int paramInt) {
/* 196 */     SynthStyle synthStyle = this.trackStyle;
/*     */     
/* 198 */     if (paramRegion == Region.SCROLL_BAR_THUMB) {
/* 199 */       synthStyle = this.thumbStyle;
/*     */     }
/* 201 */     return SynthContext.getContext(paramJComponent, paramRegion, synthStyle, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent, Region paramRegion) {
/* 205 */     if (paramRegion == Region.SCROLL_BAR_THUMB && isThumbRollover() && paramJComponent
/* 206 */       .isEnabled()) {
/* 207 */       return 2;
/*     */     }
/* 209 */     return SynthLookAndFeel.getComponentState(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSupportsAbsolutePositioning() {
/* 217 */     SynthContext synthContext = getContext(this.scrollbar);
/* 218 */     boolean bool = this.style.getBoolean(synthContext, "ScrollBar.allowsAbsolutePositioning", false);
/*     */     
/* 220 */     synthContext.dispose();
/* 221 */     return bool;
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 238 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 240 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 241 */     synthContext.getPainter().paintScrollBarBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 242 */         .getWidth(), paramJComponent.getHeight(), this.scrollbar
/* 243 */         .getOrientation());
/* 244 */     paint(synthContext, paramGraphics);
/* 245 */     synthContext.dispose();
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
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 259 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 261 */     paint(synthContext, paramGraphics);
/* 262 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 273 */     SynthContext synthContext = getContext(this.scrollbar, Region.SCROLL_BAR_TRACK);
/*     */     
/* 275 */     paintTrack(synthContext, paramGraphics, getTrackBounds());
/* 276 */     synthContext.dispose();
/*     */     
/* 278 */     synthContext = getContext(this.scrollbar, Region.SCROLL_BAR_THUMB);
/* 279 */     paintThumb(synthContext, paramGraphics, getThumbBounds());
/* 280 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 289 */     paramSynthContext.getPainter().paintScrollBarBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, this.scrollbar
/* 290 */         .getOrientation());
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
/*     */   protected void paintTrack(SynthContext paramSynthContext, Graphics paramGraphics, Rectangle paramRectangle) {
/* 302 */     SynthLookAndFeel.updateSubregion(paramSynthContext, paramGraphics, paramRectangle);
/* 303 */     paramSynthContext.getPainter().paintScrollBarTrackBackground(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, this.scrollbar
/*     */         
/* 305 */         .getOrientation());
/* 306 */     paramSynthContext.getPainter().paintScrollBarTrackBorder(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, this.scrollbar
/*     */         
/* 308 */         .getOrientation());
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
/*     */   protected void paintThumb(SynthContext paramSynthContext, Graphics paramGraphics, Rectangle paramRectangle) {
/* 320 */     SynthLookAndFeel.updateSubregion(paramSynthContext, paramGraphics, paramRectangle);
/* 321 */     int i = this.scrollbar.getOrientation();
/* 322 */     paramSynthContext.getPainter().paintScrollBarThumbBackground(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, i);
/*     */ 
/*     */     
/* 325 */     paramSynthContext.getPainter().paintScrollBarThumbBorder(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, i);
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
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 348 */     Insets insets = paramJComponent.getInsets();
/* 349 */     return (this.scrollbar.getOrientation() == 1) ? new Dimension(this.scrollBarWidth + insets.left + insets.right, 48) : new Dimension(48, this.scrollBarWidth + insets.top + insets.bottom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Dimension getMinimumThumbSize() {
/* 359 */     if (!this.validMinimumThumbSize) {
/* 360 */       if (this.scrollbar.getOrientation() == 1) {
/* 361 */         this.minimumThumbSize.width = this.scrollBarWidth;
/* 362 */         this.minimumThumbSize.height = 7;
/*     */       } else {
/* 364 */         this.minimumThumbSize.width = 7;
/* 365 */         this.minimumThumbSize.height = this.scrollBarWidth;
/*     */       } 
/*     */     }
/* 368 */     return this.minimumThumbSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createDecreaseButton(int paramInt) {
/* 376 */     SynthArrowButton synthArrowButton = new SynthArrowButton(paramInt)
/*     */       {
/*     */         public boolean contains(int param1Int1, int param1Int2) {
/* 379 */           if (SynthScrollBarUI.this.decrGap < 0) {
/* 380 */             int i = getWidth();
/* 381 */             int j = getHeight();
/* 382 */             if (SynthScrollBarUI.this.scrollbar.getOrientation() == 1) {
/*     */ 
/*     */               
/* 385 */               j += SynthScrollBarUI.this.decrGap;
/*     */             }
/*     */             else {
/*     */               
/* 389 */               i += SynthScrollBarUI.this.decrGap;
/*     */             } 
/* 391 */             return (param1Int1 >= 0 && param1Int1 < i && param1Int2 >= 0 && param1Int2 < j);
/*     */           } 
/* 393 */           return super.contains(param1Int1, param1Int2);
/*     */         }
/*     */       };
/* 396 */     synthArrowButton.setName("ScrollBar.button");
/* 397 */     return synthArrowButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createIncreaseButton(int paramInt) {
/* 405 */     SynthArrowButton synthArrowButton = new SynthArrowButton(paramInt)
/*     */       {
/*     */         public boolean contains(int param1Int1, int param1Int2) {
/* 408 */           if (SynthScrollBarUI.this.incrGap < 0) {
/* 409 */             int i = getWidth();
/* 410 */             int j = getHeight();
/* 411 */             if (SynthScrollBarUI.this.scrollbar.getOrientation() == 1) {
/*     */ 
/*     */               
/* 414 */               j += SynthScrollBarUI.this.incrGap;
/* 415 */               param1Int2 += SynthScrollBarUI.this.incrGap;
/*     */             }
/*     */             else {
/*     */               
/* 419 */               i += SynthScrollBarUI.this.incrGap;
/* 420 */               param1Int1 += SynthScrollBarUI.this.incrGap;
/*     */             } 
/* 422 */             return (param1Int1 >= 0 && param1Int1 < i && param1Int2 >= 0 && param1Int2 < j);
/*     */           } 
/* 424 */           return super.contains(param1Int1, param1Int2);
/*     */         }
/*     */       };
/* 427 */     synthArrowButton.setName("ScrollBar.button");
/* 428 */     return synthArrowButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setThumbRollover(boolean paramBoolean) {
/* 436 */     if (isThumbRollover() != paramBoolean) {
/* 437 */       this.scrollbar.repaint(getThumbBounds());
/* 438 */       super.setThumbRollover(paramBoolean);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateButtonDirections() {
/* 443 */     int i = this.scrollbar.getOrientation();
/* 444 */     if (this.scrollbar.getComponentOrientation().isLeftToRight()) {
/* 445 */       ((SynthArrowButton)this.incrButton).setDirection((i == 0) ? 3 : 5);
/*     */       
/* 447 */       ((SynthArrowButton)this.decrButton).setDirection((i == 0) ? 7 : 1);
/*     */     }
/*     */     else {
/*     */       
/* 451 */       ((SynthArrowButton)this.incrButton).setDirection((i == 0) ? 7 : 5);
/*     */       
/* 453 */       ((SynthArrowButton)this.decrButton).setDirection((i == 0) ? 3 : 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 462 */     String str = paramPropertyChangeEvent.getPropertyName();
/*     */     
/* 464 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 465 */       updateStyle((JScrollBar)paramPropertyChangeEvent.getSource());
/*     */     }
/*     */     
/* 468 */     if ("orientation" == str) {
/* 469 */       updateButtonDirections();
/*     */     }
/* 471 */     else if ("componentOrientation" == str) {
/* 472 */       updateButtonDirections();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthScrollBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */