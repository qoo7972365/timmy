/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Button;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.SystemColor;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.peer.ButtonPeer;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.basic.BasicGraphicsUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XButtonPeer
/*     */   extends XComponentPeer
/*     */   implements ButtonPeer
/*     */ {
/*     */   private boolean pressed;
/*     */   private boolean armed;
/*     */   private Insets focusInsets;
/*     */   private Insets borderInsets;
/*     */   private Insets contentAreaInsets;
/*     */   private static final String propertyPrefix = "Button.";
/*  44 */   protected Color focusColor = SystemColor.windowText;
/*     */   
/*     */   private boolean disposed = false;
/*     */   
/*     */   String label;
/*     */   
/*     */   protected String getPropertyPrefix() {
/*  51 */     return "Button.";
/*     */   }
/*     */   
/*     */   void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  55 */     super.preInit(paramXCreateWindowParams);
/*  56 */     this.borderInsets = new Insets(2, 2, 2, 2);
/*  57 */     this.focusInsets = new Insets(0, 0, 0, 0);
/*  58 */     this.contentAreaInsets = new Insets(3, 3, 3, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public XButtonPeer(Button paramButton) {
/*  63 */     super(paramButton);
/*  64 */     this.pressed = false;
/*  65 */     this.armed = false;
/*  66 */     this.label = paramButton.getLabel();
/*  67 */     updateMotifColors(getPeerBackground());
/*     */   }
/*     */   
/*     */   public void dispose() {
/*  71 */     synchronized (this.target) {
/*     */       
/*  73 */       this.disposed = true;
/*     */     } 
/*  75 */     super.dispose();
/*     */   }
/*     */   
/*     */   public boolean isFocusable() {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLabel(String paramString) {
/*  84 */     if (paramString == null) {
/*  85 */       paramString = "";
/*     */     }
/*  87 */     if (!paramString.equals(this.label)) {
/*  88 */       this.label = paramString;
/*  89 */       repaint();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBackground(Color paramColor) {
/*  94 */     updateMotifColors(paramColor);
/*  95 */     super.setBackground(paramColor);
/*     */   }
/*     */   
/*     */   void handleJavaMouseEvent(MouseEvent paramMouseEvent) {
/*  99 */     super.handleJavaMouseEvent(paramMouseEvent);
/* 100 */     int i = paramMouseEvent.getID();
/* 101 */     switch (i) {
/*     */       case 501:
/* 103 */         if (XToolkit.isLeftMouseButton(paramMouseEvent)) {
/* 104 */           Button button = (Button)paramMouseEvent.getSource();
/*     */           
/* 106 */           if (button.contains(paramMouseEvent.getX(), paramMouseEvent.getY())) {
/* 107 */             if (!isEnabled()) {
/*     */               return;
/*     */             }
/*     */             
/* 111 */             this.pressed = true;
/* 112 */             this.armed = true;
/* 113 */             repaint();
/*     */           } 
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 502:
/* 120 */         if (XToolkit.isLeftMouseButton(paramMouseEvent)) {
/* 121 */           if (this.armed)
/*     */           {
/* 123 */             action(paramMouseEvent.getWhen(), paramMouseEvent.getModifiers());
/*     */           }
/* 125 */           this.pressed = false;
/* 126 */           this.armed = false;
/* 127 */           repaint();
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 504:
/* 133 */         if (this.pressed)
/* 134 */           this.armed = true; 
/*     */         break;
/*     */       case 505:
/* 137 */         this.armed = false;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void action(long paramLong, int paramInt) {
/* 146 */     postEvent(new ActionEvent(this.target, 1001, ((Button)this.target)
/* 147 */           .getActionCommand(), paramLong, paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusGained(FocusEvent paramFocusEvent) {
/* 153 */     super.focusGained(paramFocusEvent);
/* 154 */     repaint();
/*     */   }
/*     */   
/*     */   public void focusLost(FocusEvent paramFocusEvent) {
/* 158 */     super.focusLost(paramFocusEvent);
/* 159 */     repaint();
/*     */   }
/*     */   
/*     */   void handleJavaKeyEvent(KeyEvent paramKeyEvent) {
/* 163 */     int i = paramKeyEvent.getID();
/* 164 */     switch (i) {
/*     */       case 401:
/* 166 */         if (paramKeyEvent.getKeyCode() == 32) {
/*     */           
/* 168 */           this.pressed = true;
/* 169 */           this.armed = true;
/* 170 */           repaint();
/* 171 */           action(paramKeyEvent.getWhen(), paramKeyEvent.getModifiers());
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 402:
/* 177 */         if (paramKeyEvent.getKeyCode() == 32) {
/*     */           
/* 179 */           this.pressed = false;
/* 180 */           this.armed = false;
/* 181 */           repaint();
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 191 */     FontMetrics fontMetrics = getFontMetrics(getPeerFont());
/* 192 */     if (this.label == null) {
/* 193 */       this.label = "";
/*     */     }
/* 195 */     return new Dimension(fontMetrics.stringWidth(this.label) + 14, fontMetrics
/* 196 */         .getHeight() + 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension minimumSize() {
/* 203 */     return getMinimumSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void paintPeer(Graphics paramGraphics) {
/* 211 */     if (!this.disposed) {
/* 212 */       Dimension dimension = getPeerSize();
/* 213 */       paramGraphics.setColor(getPeerBackground());
/* 214 */       paramGraphics.fillRect(0, 0, dimension.width, dimension.height);
/* 215 */       paintBorder(paramGraphics, this.borderInsets.left, this.borderInsets.top, dimension.width - this.borderInsets.left + this.borderInsets.right, dimension.height - this.borderInsets.top + this.borderInsets.bottom);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 220 */       FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/*     */ 
/*     */ 
/*     */       
/* 224 */       Rectangle rectangle1 = new Rectangle();
/* 225 */       Rectangle rectangle3 = new Rectangle();
/* 226 */       Rectangle rectangle2 = new Rectangle();
/*     */ 
/*     */       
/* 229 */       rectangle3.width = dimension.width - this.contentAreaInsets.left + this.contentAreaInsets.right;
/* 230 */       rectangle3.height = dimension.height - this.contentAreaInsets.top + this.contentAreaInsets.bottom;
/* 231 */       rectangle3.x = this.contentAreaInsets.left;
/* 232 */       rectangle3.y = this.contentAreaInsets.top;
/* 233 */       String str1 = (this.label != null) ? this.label : "";
/*     */       
/* 235 */       String str2 = SwingUtilities.layoutCompoundLabel(fontMetrics, str1, null, 0, 0, 0, 0, rectangle3, rectangle2, rectangle1, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 241 */       Font font = getPeerFont();
/*     */       
/* 243 */       paramGraphics.setFont(font);
/*     */ 
/*     */       
/* 246 */       if (this.pressed && this.armed) {
/* 247 */         paintButtonPressed(paramGraphics, this.target);
/*     */       }
/*     */       
/* 250 */       paintText(paramGraphics, this.target, rectangle1, str2);
/*     */       
/* 252 */       if (hasFocus())
/*     */       {
/* 254 */         paintFocus(paramGraphics, this.focusInsets.left, this.focusInsets.top, dimension.width - this.focusInsets.left + this.focusInsets.right - 1, dimension.height - this.focusInsets.top + this.focusInsets.bottom - 1);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 260 */     flush();
/*     */   }
/*     */   
/*     */   public void paintBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 264 */     drawMotif3DRect(paramGraphics, paramInt1, paramInt2, paramInt3 - 1, paramInt4 - 1, this.pressed);
/*     */   }
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 268 */     paramGraphics.setColor(this.focusColor);
/* 269 */     paramGraphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   protected void paintButtonPressed(Graphics paramGraphics, Component paramComponent) {
/* 273 */     Dimension dimension = getPeerSize();
/* 274 */     paramGraphics.setColor(this.selectColor);
/* 275 */     paramGraphics.fillRect(this.contentAreaInsets.left, this.contentAreaInsets.top, dimension.width - this.contentAreaInsets.left + this.contentAreaInsets.right, dimension.height - this.contentAreaInsets.top + this.contentAreaInsets.bottom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintText(Graphics paramGraphics, Component paramComponent, Rectangle paramRectangle, String paramString) {
/* 282 */     FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/*     */     
/* 284 */     byte b = -1;
/*     */ 
/*     */     
/* 287 */     if (isEnabled()) {
/*     */       
/* 289 */       paramGraphics.setColor(getPeerForeground());
/* 290 */       BasicGraphicsUtils.drawStringUnderlineCharAt(paramGraphics, paramString, b, paramRectangle.x, paramRectangle.y + fontMetrics.getAscent());
/*     */     }
/*     */     else {
/*     */       
/* 294 */       paramGraphics.setColor(getPeerBackground().brighter());
/* 295 */       BasicGraphicsUtils.drawStringUnderlineCharAt(paramGraphics, paramString, b, paramRectangle.x, paramRectangle.y + fontMetrics
/* 296 */           .getAscent());
/* 297 */       paramGraphics.setColor(getPeerBackground().darker());
/* 298 */       BasicGraphicsUtils.drawStringUnderlineCharAt(paramGraphics, paramString, b, paramRectangle.x - 1, paramRectangle.y + fontMetrics
/* 299 */           .getAscent() - 1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XButtonPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */