/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Checkbox;
/*     */ import java.awt.CheckboxGroup;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.SystemColor;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.peer.CheckboxPeer;
/*     */ import java.util.Objects;
/*     */ import javax.swing.plaf.basic.BasicGraphicsUtils;
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
/*     */ class XCheckboxPeer
/*     */   extends XComponentPeer
/*     */   implements CheckboxPeer
/*     */ {
/*  40 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XCheckboxPeer");
/*     */   
/*  42 */   private static final Insets focusInsets = new Insets(0, 0, 0, 0);
/*  43 */   private static final Insets borderInsets = new Insets(2, 2, 2, 2);
/*     */ 
/*     */   
/*     */   private static final int checkBoxInsetFromText = 2;
/*     */   
/*     */   private static final double MASTER_SIZE = 128.0D;
/*     */   
/*  50 */   private static final Polygon MASTER_CHECKMARK = new Polygon(new int[] { 1, 25, 56, 124, 124, 85, 64 }, new int[] { 59, 35, 67, 0, 12, 66, 123 }, 7);
/*     */ 
/*     */ 
/*     */   
/*     */   private Shape myCheckMark;
/*     */ 
/*     */   
/*  57 */   private Color focusColor = SystemColor.windowText;
/*     */   
/*     */   private boolean pressed;
/*     */   
/*     */   private boolean armed;
/*     */   
/*     */   private boolean selected;
/*     */   private Rectangle textRect;
/*     */   private Rectangle focusRect;
/*     */   private int checkBoxSize;
/*     */   private int cbX;
/*     */   private int cbY;
/*     */   String label;
/*     */   CheckboxGroup checkBoxGroup;
/*     */   
/*     */   XCheckboxPeer(Checkbox paramCheckbox) {
/*  73 */     super(paramCheckbox);
/*  74 */     this.pressed = false;
/*  75 */     this.armed = false;
/*  76 */     this.selected = paramCheckbox.getState();
/*  77 */     this.label = paramCheckbox.getLabel();
/*  78 */     if (this.label == null) {
/*  79 */       this.label = "";
/*     */     }
/*  81 */     this.checkBoxGroup = paramCheckbox.getCheckboxGroup();
/*  82 */     updateMotifColors(getPeerBackground());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  88 */     this.textRect = new Rectangle();
/*  89 */     this.focusRect = new Rectangle();
/*  90 */     super.preInit(paramXCreateWindowParams);
/*     */   }
/*     */   public boolean isFocusable() {
/*  93 */     return true;
/*     */   }
/*     */   
/*     */   public void focusGained(FocusEvent paramFocusEvent) {
/*  97 */     super.focusGained(paramFocusEvent);
/*  98 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void focusLost(FocusEvent paramFocusEvent) {
/* 103 */     super.focusLost(paramFocusEvent);
/* 104 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   void handleJavaKeyEvent(KeyEvent paramKeyEvent) {
/* 109 */     int i = paramKeyEvent.getID();
/* 110 */     switch (i) {
/*     */       case 401:
/* 112 */         keyPressed(paramKeyEvent);
/*     */         break;
/*     */       case 402:
/* 115 */         keyReleased(paramKeyEvent);
/*     */         break;
/*     */       case 400:
/* 118 */         keyTyped(paramKeyEvent);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void keyTyped(KeyEvent paramKeyEvent) {}
/*     */   
/*     */   public void keyPressed(KeyEvent paramKeyEvent) {
/* 126 */     if (paramKeyEvent.getKeyCode() == 32)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 131 */       action(!this.selected);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyReleased(KeyEvent paramKeyEvent) {}
/*     */ 
/*     */   
/*     */   public void setLabel(String paramString) {
/* 141 */     if (paramString == null) {
/* 142 */       paramString = "";
/*     */     }
/* 144 */     if (!paramString.equals(this.label)) {
/* 145 */       this.label = paramString;
/* 146 */       layout();
/* 147 */       repaint();
/*     */     } 
/*     */   }
/*     */   
/*     */   void handleJavaMouseEvent(MouseEvent paramMouseEvent) {
/* 152 */     super.handleJavaMouseEvent(paramMouseEvent);
/* 153 */     int i = paramMouseEvent.getID();
/* 154 */     switch (i) {
/*     */       case 501:
/* 156 */         mousePressed(paramMouseEvent);
/*     */         break;
/*     */       case 502:
/* 159 */         mouseReleased(paramMouseEvent);
/*     */         break;
/*     */       case 504:
/* 162 */         mouseEntered(paramMouseEvent);
/*     */         break;
/*     */       case 505:
/* 165 */         mouseExited(paramMouseEvent);
/*     */         break;
/*     */       case 500:
/* 168 */         mouseClicked(paramMouseEvent);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 174 */     if (XToolkit.isLeftMouseButton(paramMouseEvent)) {
/* 175 */       Checkbox checkbox = (Checkbox)paramMouseEvent.getSource();
/*     */       
/* 177 */       if (checkbox.contains(paramMouseEvent.getX(), paramMouseEvent.getY())) {
/* 178 */         if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 179 */           log.finer("mousePressed() on " + this.target.getName() + " : armed = " + this.armed + ", pressed = " + this.pressed + ", selected = " + this.selected + ", enabled = " + 
/* 180 */               isEnabled());
/*     */         }
/* 182 */         if (!isEnabled()) {
/*     */           return;
/*     */         }
/*     */         
/* 186 */         if (!this.armed) {
/* 187 */           this.armed = true;
/*     */         }
/* 189 */         this.pressed = true;
/* 190 */         repaint();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseReleased(MouseEvent paramMouseEvent) {
/* 196 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 197 */       log.finer("mouseReleased() on " + this.target.getName() + ": armed = " + this.armed + ", pressed = " + this.pressed + ", selected = " + this.selected + ", enabled = " + 
/* 198 */           isEnabled());
/*     */     }
/* 200 */     boolean bool = false;
/* 201 */     if (XToolkit.isLeftMouseButton(paramMouseEvent)) {
/*     */       
/* 203 */       if (this.armed)
/*     */       {
/*     */ 
/*     */         
/* 207 */         bool = true;
/*     */       }
/* 209 */       this.pressed = false;
/* 210 */       this.armed = false;
/* 211 */       if (bool) {
/* 212 */         action(!this.selected);
/*     */       } else {
/*     */         
/* 215 */         repaint();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseEntered(MouseEvent paramMouseEvent) {
/* 221 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 222 */       log.finer("mouseEntered() on " + this.target.getName() + ": armed = " + this.armed + ", pressed = " + this.pressed + ", selected = " + this.selected + ", enabled = " + 
/* 223 */           isEnabled());
/*     */     }
/* 225 */     if (this.pressed) {
/* 226 */       this.armed = true;
/* 227 */       repaint();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {
/* 232 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 233 */       log.finer("mouseExited() on " + this.target.getName() + ": armed = " + this.armed + ", pressed = " + this.pressed + ", selected = " + this.selected + ", enabled = " + 
/* 234 */           isEnabled());
/*     */     }
/* 236 */     if (this.armed) {
/* 237 */       this.armed = false;
/* 238 */       repaint();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 252 */     FontMetrics fontMetrics = getFontMetrics(getPeerFont());
/*     */     
/* 254 */     int i = fontMetrics.stringWidth(this.label) + getCheckboxSize(fontMetrics) + 4 + 8;
/* 255 */     int j = Math.max(fontMetrics.getHeight() + 8, 15);
/*     */     
/* 257 */     return new Dimension(i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getCheckboxSize(FontMetrics paramFontMetrics) {
/* 263 */     return paramFontMetrics.getHeight() * 76 / 100 - 1;
/*     */   }
/*     */   
/*     */   public void setBackground(Color paramColor) {
/* 267 */     updateMotifColors(paramColor);
/* 268 */     super.setBackground(paramColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout() {
/* 275 */     Dimension dimension = getPeerSize();
/* 276 */     Font font = getPeerFont();
/* 277 */     FontMetrics fontMetrics = getFontMetrics(font);
/* 278 */     String str = this.label;
/*     */     
/* 280 */     this.checkBoxSize = getCheckboxSize(fontMetrics);
/*     */ 
/*     */ 
/*     */     
/* 284 */     this.cbX = borderInsets.left + 2;
/* 285 */     this.cbY = dimension.height / 2 - this.checkBoxSize / 2;
/* 286 */     int i = borderInsets.left + 4 + this.checkBoxSize;
/*     */ 
/*     */ 
/*     */     
/* 290 */     this.textRect.width = fontMetrics.stringWidth((str == null) ? "" : str);
/* 291 */     this.textRect.height = fontMetrics.getHeight();
/*     */     
/* 293 */     this.textRect.x = Math.max(i, dimension.width / 2 - this.textRect.width / 2);
/* 294 */     this.textRect.y = (dimension.height - this.textRect.height) / 2;
/*     */     
/* 296 */     this.focusRect.x = focusInsets.left;
/* 297 */     this.focusRect.y = focusInsets.top;
/* 298 */     this.focusRect.width = dimension.width - focusInsets.left + focusInsets.right - 1;
/* 299 */     this.focusRect.height = dimension.height - focusInsets.top + focusInsets.bottom - 1;
/*     */     
/* 301 */     double d = this.checkBoxSize;
/* 302 */     this.myCheckMark = AffineTransform.getScaleInstance(d / 128.0D, d / 128.0D).createTransformedShape(MASTER_CHECKMARK);
/*     */   }
/*     */ 
/*     */   
/*     */   void paintPeer(Graphics paramGraphics) {
/* 307 */     Dimension dimension = getPeerSize();
/* 308 */     Font font = getPeerFont();
/* 309 */     flush();
/* 310 */     paramGraphics.setColor(getPeerBackground());
/* 311 */     paramGraphics.fillRect(0, 0, dimension.width, dimension.height);
/* 312 */     if (this.label != null) {
/* 313 */       paramGraphics.setFont(font);
/* 314 */       paintText(paramGraphics, this.textRect, this.label);
/*     */     } 
/*     */     
/* 317 */     if (hasFocus()) {
/* 318 */       paintFocus(paramGraphics, this.focusRect.x, this.focusRect.y, this.focusRect.width, this.focusRect.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     if (this.checkBoxGroup == null) {
/* 326 */       paintCheckbox(paramGraphics, this.cbX, this.cbY, this.checkBoxSize, this.checkBoxSize);
/*     */     } else {
/*     */       
/* 329 */       paintRadioButton(paramGraphics, this.cbX, this.cbY, this.checkBoxSize, this.checkBoxSize);
/*     */     } 
/* 331 */     flush();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintCheckbox(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 337 */     boolean bool = false;
/* 338 */     BufferedImage bufferedImage = null;
/* 339 */     Graphics2D graphics2D = null;
/* 340 */     int i = paramInt1;
/* 341 */     int j = paramInt2;
/* 342 */     if (!(paramGraphics instanceof Graphics2D)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 347 */       bufferedImage = this.graphicsConfig.createCompatibleImage(paramInt3, paramInt4);
/* 348 */       graphics2D = bufferedImage.createGraphics();
/* 349 */       bool = true;
/* 350 */       i = 0;
/* 351 */       j = 0;
/*     */     } else {
/*     */       
/* 354 */       graphics2D = (Graphics2D)paramGraphics;
/*     */     } 
/*     */     try {
/* 357 */       drawMotif3DRect(graphics2D, i, j, paramInt3 - 1, paramInt4 - 1, this.armed | this.selected);
/*     */ 
/*     */       
/* 360 */       graphics2D.setColor(((this.armed | this.selected) != 0) ? this.selectColor : getPeerBackground());
/* 361 */       graphics2D.fillRect(i + 1, j + 1, paramInt3 - 2, paramInt4 - 2);
/*     */       
/* 363 */       if ((this.armed | this.selected) != 0) {
/*     */ 
/*     */ 
/*     */         
/* 367 */         graphics2D.setColor(getPeerForeground());
/*     */         
/* 369 */         AffineTransform affineTransform = graphics2D.getTransform();
/* 370 */         graphics2D.setTransform(AffineTransform.getTranslateInstance(i, j));
/* 371 */         graphics2D.fill(this.myCheckMark);
/* 372 */         graphics2D.setTransform(affineTransform);
/*     */       } 
/*     */     } finally {
/* 375 */       if (bool) {
/* 376 */         graphics2D.dispose();
/*     */       }
/*     */     } 
/* 379 */     if (bool) {
/* 380 */       paramGraphics.drawImage(bufferedImage, paramInt1, paramInt2, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintRadioButton(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 386 */     paramGraphics.setColor(((this.armed | this.selected) != 0) ? this.darkShadow : this.lightShadow);
/* 387 */     paramGraphics.drawArc(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2, 45, 180);
/*     */     
/* 389 */     paramGraphics.setColor(((this.armed | this.selected) != 0) ? this.lightShadow : this.darkShadow);
/* 390 */     paramGraphics.drawArc(paramInt1 - 1, paramInt2 - 1, paramInt3 + 2, paramInt4 + 2, 45, -180);
/*     */     
/* 392 */     if ((this.armed | this.selected) != 0) {
/* 393 */       paramGraphics.setColor(this.selectColor);
/* 394 */       paramGraphics.fillArc(paramInt1 + 1, paramInt2 + 1, paramInt3 - 1, paramInt4 - 1, 0, 360);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void paintText(Graphics paramGraphics, Rectangle paramRectangle, String paramString) {
/* 399 */     FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/*     */     
/* 401 */     byte b = -1;
/*     */     
/* 403 */     if (isEnabled()) {
/*     */       
/* 405 */       paramGraphics.setColor(getPeerForeground());
/* 406 */       BasicGraphicsUtils.drawStringUnderlineCharAt(paramGraphics, paramString, b, paramRectangle.x, paramRectangle.y + fontMetrics.getAscent());
/*     */     }
/*     */     else {
/*     */       
/* 410 */       paramGraphics.setColor(getPeerBackground().brighter());
/*     */       
/* 412 */       BasicGraphicsUtils.drawStringUnderlineCharAt(paramGraphics, paramString, b, paramRectangle.x, paramRectangle.y + fontMetrics
/* 413 */           .getAscent());
/* 414 */       paramGraphics.setColor(getPeerBackground().darker());
/* 415 */       BasicGraphicsUtils.drawStringUnderlineCharAt(paramGraphics, paramString, b, paramRectangle.x - 1, paramRectangle.y + fontMetrics
/* 416 */           .getAscent() - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 422 */     paramGraphics.setColor(this.focusColor);
/* 423 */     paramGraphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(boolean paramBoolean) {
/* 428 */     if (this.selected != paramBoolean) {
/* 429 */       this.selected = paramBoolean;
/* 430 */       repaint();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCheckboxGroup(CheckboxGroup paramCheckboxGroup) {
/* 436 */     if (!Objects.equals(paramCheckboxGroup, this.checkBoxGroup)) {
/*     */       
/* 438 */       this.checkBoxGroup = paramCheckboxGroup;
/* 439 */       repaint();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void action(boolean paramBoolean) {
/* 447 */     final Checkbox cb = (Checkbox)this.target;
/* 448 */     final boolean newState = paramBoolean;
/* 449 */     XToolkit.executeOnEventHandlerThread(checkbox, new Runnable() {
/*     */           public void run() {
/* 451 */             CheckboxGroup checkboxGroup = XCheckboxPeer.this.checkBoxGroup;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 456 */             if (checkboxGroup != null && checkboxGroup.getSelectedCheckbox() == cb && cb
/* 457 */               .getState()) {
/*     */               
/* 459 */               cb.setState(true);
/*     */               
/*     */               return;
/*     */             } 
/* 463 */             cb.setState(newState);
/* 464 */             XCheckboxPeer.this.notifyStateChanged(newState);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   void notifyStateChanged(boolean paramBoolean) {
/* 470 */     Checkbox checkbox = (Checkbox)this.target;
/*     */ 
/*     */     
/* 473 */     ItemEvent itemEvent = new ItemEvent(checkbox, 701, checkbox.getLabel(), paramBoolean ? 1 : 2);
/*     */     
/* 475 */     postEvent(itemEvent);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XCheckboxPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */