/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicSliderUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsSliderUI
/*     */   extends BasicSliderUI
/*     */ {
/*     */   private boolean rollover = false;
/*     */   private boolean pressed = false;
/*     */   
/*     */   public WindowsSliderUI(JSlider paramJSlider) {
/*  55 */     super(paramJSlider);
/*     */   }
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  59 */     return new WindowsSliderUI((JSlider)paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasicSliderUI.TrackListener createTrackListener(JSlider paramJSlider) {
/*  69 */     return new WindowsTrackListener();
/*     */   }
/*     */   
/*     */   private class WindowsTrackListener
/*     */     extends BasicSliderUI.TrackListener {
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {
/*  75 */       updateRollover(WindowsSliderUI.this.thumbRect.contains(param1MouseEvent.getX(), param1MouseEvent.getY()));
/*  76 */       super.mouseMoved(param1MouseEvent);
/*     */     }
/*     */     private WindowsTrackListener() {}
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {
/*  80 */       updateRollover(WindowsSliderUI.this.thumbRect.contains(param1MouseEvent.getX(), param1MouseEvent.getY()));
/*  81 */       super.mouseEntered(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {
/*  85 */       updateRollover(false);
/*  86 */       super.mouseExited(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  90 */       updatePressed(WindowsSliderUI.this.thumbRect.contains(param1MouseEvent.getX(), param1MouseEvent.getY()));
/*  91 */       super.mousePressed(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  95 */       updatePressed(false);
/*  96 */       super.mouseReleased(param1MouseEvent);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updatePressed(boolean param1Boolean) {
/* 101 */       if (!WindowsSliderUI.this.slider.isEnabled()) {
/*     */         return;
/*     */       }
/* 104 */       if (WindowsSliderUI.this.pressed != param1Boolean) {
/* 105 */         WindowsSliderUI.this.pressed = param1Boolean;
/* 106 */         WindowsSliderUI.this.slider.repaint(WindowsSliderUI.this.thumbRect);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateRollover(boolean param1Boolean) {
/* 112 */       if (!WindowsSliderUI.this.slider.isEnabled()) {
/*     */         return;
/*     */       }
/* 115 */       if (WindowsSliderUI.this.rollover != param1Boolean) {
/* 116 */         WindowsSliderUI.this.rollover = param1Boolean;
/* 117 */         WindowsSliderUI.this.slider.repaint(WindowsSliderUI.this.thumbRect);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintTrack(Graphics paramGraphics) {
/* 125 */     XPStyle xPStyle = XPStyle.getXP();
/* 126 */     if (xPStyle != null) {
/* 127 */       boolean bool = (this.slider.getOrientation() == 1) ? true : false;
/* 128 */       TMSchema.Part part = bool ? TMSchema.Part.TKP_TRACKVERT : TMSchema.Part.TKP_TRACK;
/* 129 */       XPStyle.Skin skin = xPStyle.getSkin(this.slider, part);
/*     */       
/* 131 */       if (bool) {
/* 132 */         int i = (this.trackRect.width - skin.getWidth()) / 2;
/* 133 */         skin.paintSkin(paramGraphics, this.trackRect.x + i, this.trackRect.y, skin
/* 134 */             .getWidth(), this.trackRect.height, null);
/*     */       } else {
/* 136 */         int i = (this.trackRect.height - skin.getHeight()) / 2;
/* 137 */         skin.paintSkin(paramGraphics, this.trackRect.x, this.trackRect.y + i, this.trackRect.width, skin
/* 138 */             .getHeight(), null);
/*     */       } 
/*     */     } else {
/* 141 */       super.paintTrack(paramGraphics);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintMinorTickForHorizSlider(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/* 147 */     XPStyle xPStyle = XPStyle.getXP();
/* 148 */     if (xPStyle != null) {
/* 149 */       paramGraphics.setColor(xPStyle.getColor(this.slider, TMSchema.Part.TKP_TICS, null, TMSchema.Prop.COLOR, Color.black));
/*     */     }
/* 151 */     super.paintMinorTickForHorizSlider(paramGraphics, paramRectangle, paramInt);
/*     */   }
/*     */   
/*     */   protected void paintMajorTickForHorizSlider(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/* 155 */     XPStyle xPStyle = XPStyle.getXP();
/* 156 */     if (xPStyle != null) {
/* 157 */       paramGraphics.setColor(xPStyle.getColor(this.slider, TMSchema.Part.TKP_TICS, null, TMSchema.Prop.COLOR, Color.black));
/*     */     }
/* 159 */     super.paintMajorTickForHorizSlider(paramGraphics, paramRectangle, paramInt);
/*     */   }
/*     */   
/*     */   protected void paintMinorTickForVertSlider(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/* 163 */     XPStyle xPStyle = XPStyle.getXP();
/* 164 */     if (xPStyle != null) {
/* 165 */       paramGraphics.setColor(xPStyle.getColor(this.slider, TMSchema.Part.TKP_TICSVERT, null, TMSchema.Prop.COLOR, Color.black));
/*     */     }
/* 167 */     super.paintMinorTickForVertSlider(paramGraphics, paramRectangle, paramInt);
/*     */   }
/*     */   
/*     */   protected void paintMajorTickForVertSlider(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/* 171 */     XPStyle xPStyle = XPStyle.getXP();
/* 172 */     if (xPStyle != null) {
/* 173 */       paramGraphics.setColor(xPStyle.getColor(this.slider, TMSchema.Part.TKP_TICSVERT, null, TMSchema.Prop.COLOR, Color.black));
/*     */     }
/* 175 */     super.paintMajorTickForVertSlider(paramGraphics, paramRectangle, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintThumb(Graphics paramGraphics) {
/* 180 */     XPStyle xPStyle = XPStyle.getXP();
/* 181 */     if (xPStyle != null) {
/* 182 */       TMSchema.Part part = getXPThumbPart();
/* 183 */       TMSchema.State state = TMSchema.State.NORMAL;
/*     */       
/* 185 */       if (this.slider.hasFocus()) {
/* 186 */         state = TMSchema.State.FOCUSED;
/*     */       }
/* 188 */       if (this.rollover) {
/* 189 */         state = TMSchema.State.HOT;
/*     */       }
/* 191 */       if (this.pressed) {
/* 192 */         state = TMSchema.State.PRESSED;
/*     */       }
/* 194 */       if (!this.slider.isEnabled()) {
/* 195 */         state = TMSchema.State.DISABLED;
/*     */       }
/*     */       
/* 198 */       xPStyle.getSkin(this.slider, part).paintSkin(paramGraphics, this.thumbRect.x, this.thumbRect.y, state);
/*     */     } else {
/* 200 */       super.paintThumb(paramGraphics);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Dimension getThumbSize() {
/* 205 */     XPStyle xPStyle = XPStyle.getXP();
/* 206 */     if (xPStyle != null) {
/* 207 */       Dimension dimension = new Dimension();
/* 208 */       XPStyle.Skin skin = xPStyle.getSkin(this.slider, getXPThumbPart());
/* 209 */       dimension.width = skin.getWidth();
/* 210 */       dimension.height = skin.getHeight();
/* 211 */       return dimension;
/*     */     } 
/* 213 */     return super.getThumbSize();
/*     */   }
/*     */ 
/*     */   
/*     */   private TMSchema.Part getXPThumbPart() {
/*     */     TMSchema.Part part;
/* 219 */     boolean bool = (this.slider.getOrientation() == 1) ? true : false;
/* 220 */     boolean bool1 = this.slider.getComponentOrientation().isLeftToRight();
/*     */     
/* 222 */     Boolean bool2 = (Boolean)this.slider.getClientProperty("Slider.paintThumbArrowShape");
/* 223 */     if ((!this.slider.getPaintTicks() && bool2 == null) || bool2 == Boolean.FALSE) {
/*     */       
/* 225 */       part = bool ? TMSchema.Part.TKP_THUMBVERT : TMSchema.Part.TKP_THUMB;
/*     */     } else {
/*     */       
/* 228 */       part = bool ? (bool1 ? TMSchema.Part.TKP_THUMBRIGHT : TMSchema.Part.TKP_THUMBLEFT) : TMSchema.Part.TKP_THUMBBOTTOM;
/*     */     } 
/*     */     
/* 231 */     return part;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsSliderUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */