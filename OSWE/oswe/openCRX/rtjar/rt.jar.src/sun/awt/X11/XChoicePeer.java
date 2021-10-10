/*      */ package sun.awt.X11;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Choice;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseWheelEvent;
/*      */ import java.awt.peer.ChoicePeer;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XChoicePeer
/*      */   extends XComponentPeer
/*      */   implements ChoicePeer, ToplevelStateListener
/*      */ {
/*   46 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XChoicePeer");
/*      */ 
/*      */   
/*      */   private static final int MAX_UNFURLED_ITEMS = 10;
/*      */ 
/*      */   
/*      */   public static final int TEXT_SPACE = 1;
/*      */   
/*      */   public static final int BORDER_WIDTH = 1;
/*      */   
/*      */   public static final int ITEM_MARGIN = 1;
/*      */   
/*      */   public static final int SCROLLBAR_WIDTH = 15;
/*      */   
/*   60 */   private static final Insets focusInsets = new Insets(0, 0, 0, 0);
/*      */ 
/*      */   
/*      */   static final int WIDGET_OFFSET = 18;
/*      */ 
/*      */   
/*      */   static final int TEXT_XPAD = 8;
/*      */ 
/*      */   
/*      */   static final int TEXT_YPAD = 6;
/*      */ 
/*      */   
/*   72 */   static final Color focusColor = Color.black;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean unfurled = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean dragging = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mouseInSB = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstPress = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean wasDragged = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private ListHelper helper;
/*      */ 
/*      */ 
/*      */   
/*      */   private UnfurledChoice unfurledChoice;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean drawSelectedItem = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private Component alignUnder;
/*      */ 
/*      */ 
/*      */   
/*  117 */   private int dragStartIdx = -1;
/*      */ 
/*      */   
/*      */   private XChoicePeerListener choiceListener;
/*      */ 
/*      */   
/*      */   XChoicePeer(Choice paramChoice) {
/*  124 */     super(paramChoice);
/*      */   }
/*      */   
/*      */   void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  128 */     super.preInit(paramXCreateWindowParams);
/*  129 */     Choice choice = (Choice)this.target;
/*  130 */     int i = choice.getItemCount();
/*  131 */     this.unfurledChoice = new UnfurledChoice(choice);
/*  132 */     getToplevelXWindow().addToplevelStateListener(this);
/*  133 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  139 */       .helper = new ListHelper(this.unfurledChoice, getGUIcolors(), i, false, true, false, choice.getFont(), 10, 1, 1, 1, 15);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void postInit(XCreateWindowParams paramXCreateWindowParams) {
/*  148 */     super.postInit(paramXCreateWindowParams);
/*  149 */     Choice choice = (Choice)this.target;
/*  150 */     int i = choice.getItemCount();
/*      */ 
/*      */     
/*  153 */     for (byte b = 0; b < i; b++) {
/*  154 */       this.helper.add(choice.getItem(b));
/*      */     }
/*  156 */     if (!this.helper.isEmpty()) {
/*  157 */       this.helper.select(choice.getSelectedIndex());
/*  158 */       this.helper.setFocusedIndex(choice.getSelectedIndex());
/*      */     } 
/*  160 */     this.helper.updateColors(getGUIcolors());
/*  161 */     updateMotifColors(getPeerBackground());
/*      */   }
/*      */   public boolean isFocusable() {
/*  164 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  170 */     int i = this.x;
/*  171 */     int j = this.y;
/*  172 */     int k = this.width;
/*  173 */     int m = this.height;
/*  174 */     super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*  175 */     if (this.unfurled && (i != this.x || j != this.y || k != this.width || m != this.height)) {
/*  176 */       hidePopdownMenu();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void focusGained(FocusEvent paramFocusEvent) {
/*  182 */     super.focusGained(paramFocusEvent);
/*  183 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnabled(boolean paramBoolean) {
/*  192 */     super.setEnabled(paramBoolean);
/*  193 */     this.helper.updateColors(getGUIcolors());
/*  194 */     if (!paramBoolean && this.unfurled) {
/*  195 */       hidePopdownMenu();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void focusLost(FocusEvent paramFocusEvent) {
/*  201 */     super.focusLost(paramFocusEvent);
/*  202 */     repaint();
/*      */   }
/*      */   
/*      */   void ungrabInputImpl() {
/*  206 */     if (this.unfurled) {
/*  207 */       this.unfurled = false;
/*  208 */       this.dragging = false;
/*  209 */       this.mouseInSB = false;
/*  210 */       this.unfurledChoice.setVisible(false);
/*      */     } 
/*      */     
/*  213 */     super.ungrabInputImpl();
/*      */   }
/*      */   
/*      */   void handleJavaKeyEvent(KeyEvent paramKeyEvent) {
/*  217 */     if (paramKeyEvent.getID() == 401) {
/*  218 */       keyPressed(paramKeyEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   public void keyPressed(KeyEvent paramKeyEvent) {
/*  223 */     switch (paramKeyEvent.getKeyCode()) {
/*      */       
/*      */       case 40:
/*      */       case 225:
/*  227 */         if (this.helper.getItemCount() > 1) {
/*  228 */           this.helper.down();
/*  229 */           int i = this.helper.getSelectedIndex();
/*      */           
/*  231 */           ((Choice)this.target).select(i);
/*  232 */           postEvent(new ItemEvent((Choice)this.target, 701, ((Choice)this.target)
/*      */                 
/*  234 */                 .getItem(i), 1));
/*      */           
/*  236 */           repaint();
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 38:
/*      */       case 224:
/*  242 */         if (this.helper.getItemCount() > 1) {
/*  243 */           this.helper.up();
/*  244 */           int i = this.helper.getSelectedIndex();
/*      */           
/*  246 */           ((Choice)this.target).select(i);
/*  247 */           postEvent(new ItemEvent((Choice)this.target, 701, ((Choice)this.target)
/*      */                 
/*  249 */                 .getItem(i), 1));
/*      */           
/*  251 */           repaint();
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 34:
/*  256 */         if (this.unfurled && !this.dragging) {
/*  257 */           int i = this.helper.getSelectedIndex();
/*  258 */           this.helper.pageDown();
/*  259 */           int j = this.helper.getSelectedIndex();
/*  260 */           if (i != j) {
/*  261 */             ((Choice)this.target).select(j);
/*  262 */             postEvent(new ItemEvent((Choice)this.target, 701, ((Choice)this.target)
/*      */                   
/*  264 */                   .getItem(j), 1));
/*      */             
/*  266 */             repaint();
/*      */           } 
/*      */         } 
/*      */         return;
/*      */       case 33:
/*  271 */         if (this.unfurled && !this.dragging) {
/*  272 */           int i = this.helper.getSelectedIndex();
/*  273 */           this.helper.pageUp();
/*  274 */           int j = this.helper.getSelectedIndex();
/*  275 */           if (i != j) {
/*  276 */             ((Choice)this.target).select(j);
/*  277 */             postEvent(new ItemEvent((Choice)this.target, 701, ((Choice)this.target)
/*      */                   
/*  279 */                   .getItem(j), 1));
/*      */             
/*  281 */             repaint();
/*      */           } 
/*      */         } 
/*      */         return;
/*      */       case 10:
/*      */       case 27:
/*  287 */         if (this.unfurled) {
/*  288 */           if (this.dragging) {
/*  289 */             if (paramKeyEvent.getKeyCode() == 27) {
/*      */ 
/*      */ 
/*      */               
/*  293 */               this.helper.select(this.dragStartIdx);
/*      */             } else {
/*  295 */               int i = this.helper.getSelectedIndex();
/*  296 */               ((Choice)this.target).select(i);
/*  297 */               postEvent(new ItemEvent((Choice)this.target, 701, ((Choice)this.target)
/*      */                     
/*  299 */                     .getItem(i), 1));
/*      */             } 
/*      */           }
/*      */           
/*  303 */           hidePopdownMenu();
/*  304 */           this.dragging = false;
/*  305 */           this.wasDragged = false;
/*  306 */           this.mouseInSB = false;
/*      */ 
/*      */           
/*  309 */           if (this.choiceListener != null) {
/*  310 */             this.choiceListener.unfurledChoiceClosing();
/*      */           }
/*      */         } 
/*      */         return;
/*      */     } 
/*  315 */     if (this.unfurled) {
/*  316 */       Toolkit.getDefaultToolkit().beep();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean handlesWheelScrolling() {
/*  322 */     return true;
/*      */   }
/*      */   void handleJavaMouseWheelEvent(MouseWheelEvent paramMouseWheelEvent) {
/*  325 */     if (this.unfurled && this.helper.isVSBVisible() && 
/*  326 */       ListHelper.doWheelScroll(this.helper.getVSB(), null, paramMouseWheelEvent)) {
/*  327 */       repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   void handleJavaMouseEvent(MouseEvent paramMouseEvent) {
/*  333 */     super.handleJavaMouseEvent(paramMouseEvent);
/*  334 */     int i = paramMouseEvent.getID();
/*  335 */     switch (i) {
/*      */       case 501:
/*  337 */         mousePressed(paramMouseEvent);
/*      */         break;
/*      */       case 502:
/*  340 */         mouseReleased(paramMouseEvent);
/*      */         break;
/*      */       case 506:
/*  343 */         mouseDragged(paramMouseEvent);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mousePressed(MouseEvent paramMouseEvent) {
/*  354 */     if (paramMouseEvent.getButton() == 1) {
/*  355 */       this.dragStartIdx = this.helper.getSelectedIndex();
/*  356 */       if (this.unfurled) {
/*      */         
/*  358 */         if (!isMouseEventInChoice(paramMouseEvent) && 
/*  359 */           !this.unfurledChoice.isMouseEventInside(paramMouseEvent))
/*      */         {
/*  361 */           hidePopdownMenu();
/*      */         }
/*      */ 
/*      */         
/*  365 */         this.unfurledChoice.trackMouse(paramMouseEvent);
/*      */       }
/*      */       else {
/*      */         
/*  369 */         grabInput();
/*  370 */         this.unfurledChoice.toFront();
/*  371 */         this.firstPress = true;
/*  372 */         this.wasDragged = false;
/*  373 */         this.unfurled = true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void hidePopdownMenu() {
/*  382 */     ungrabInput();
/*  383 */     this.unfurledChoice.setVisible(false);
/*  384 */     this.unfurled = false;
/*      */   }
/*      */   
/*      */   public void mouseReleased(MouseEvent paramMouseEvent) {
/*  388 */     if (this.unfurled) {
/*  389 */       if (this.mouseInSB) {
/*  390 */         this.unfurledChoice.trackMouse(paramMouseEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  403 */         boolean bool1 = this.unfurledChoice.isMouseEventInside(paramMouseEvent);
/*  404 */         boolean bool2 = this.unfurledChoice.isMouseInListArea(paramMouseEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  409 */         if (!this.helper.isEmpty() && !bool2 && this.dragging)
/*      */         {
/*  411 */           ((Choice)this.target).select(this.dragStartIdx);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  416 */         if (!this.firstPress && bool2) {
/*  417 */           hidePopdownMenu();
/*      */         }
/*      */ 
/*      */         
/*  421 */         if (!this.firstPress && !bool1) {
/*  422 */           hidePopdownMenu();
/*      */         }
/*      */ 
/*      */         
/*  426 */         if (this.firstPress && this.dragging) {
/*  427 */           hidePopdownMenu();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  433 */         if (!this.firstPress && !bool2 && bool1 && this.dragging)
/*      */         {
/*      */           
/*  436 */           hidePopdownMenu();
/*      */         }
/*      */         
/*  439 */         if (!this.helper.isEmpty())
/*      */         {
/*      */           
/*  442 */           if (this.unfurledChoice.isMouseInListArea(paramMouseEvent)) {
/*  443 */             int i = this.helper.getSelectedIndex();
/*  444 */             if (i >= 0) {
/*      */ 
/*      */               
/*  447 */               if (i != this.dragStartIdx) {
/*  448 */                 ((Choice)this.target).select(i);
/*      */               }
/*      */ 
/*      */               
/*  452 */               if (this.wasDragged && paramMouseEvent.getButton() != 1) {
/*  453 */                 ((Choice)this.target).select(this.dragStartIdx);
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  459 */               if (paramMouseEvent.getButton() == 1 && (!this.firstPress || this.wasDragged))
/*      */               {
/*      */                 
/*  462 */                 postEvent(new ItemEvent((Choice)this.target, 701, ((Choice)this.target)
/*      */                       
/*  464 */                       .getItem(i), 1));
/*      */               }
/*      */ 
/*      */ 
/*      */               
/*  469 */               if (this.choiceListener != null) {
/*  470 */                 this.choiceListener.unfurledChoiceClosing();
/*      */               }
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/*  476 */         this.unfurledChoice.trackMouse(paramMouseEvent);
/*      */       } 
/*      */     }
/*      */     
/*  480 */     this.dragging = false;
/*  481 */     this.wasDragged = false;
/*  482 */     this.firstPress = false;
/*  483 */     this.dragStartIdx = -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mouseDragged(MouseEvent paramMouseEvent) {
/*  494 */     if (paramMouseEvent.getModifiers() == 16) {
/*  495 */       this.dragging = true;
/*  496 */       this.wasDragged = true;
/*  497 */       this.unfurledChoice.trackMouse(paramMouseEvent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize() {
/*  504 */     FontMetrics fontMetrics = getFontMetrics(this.target.getFont());
/*  505 */     Choice choice = (Choice)this.target;
/*  506 */     int i = 0;
/*  507 */     for (int j = choice.countItems(); j-- > 0;) {
/*  508 */       i = Math.max(fontMetrics.stringWidth(choice.getItem(j)), i);
/*      */     }
/*  510 */     return new Dimension(i + 8 + 18, fontMetrics
/*  511 */         .getMaxAscent() + fontMetrics.getMaxDescent() + 6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void layout() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void paintPeer(Graphics paramGraphics) {
/*  555 */     flush();
/*  556 */     Dimension dimension = getPeerSize();
/*      */     
/*  558 */     paramGraphics.setColor(getPeerBackground());
/*  559 */     paramGraphics.fillRect(0, 0, this.width, this.height);
/*      */     
/*  561 */     drawMotif3DRect(paramGraphics, 1, 1, this.width - 2, this.height - 2, false);
/*  562 */     drawMotif3DRect(paramGraphics, this.width - 18, this.height / 2 - 3, 12, 6, false);
/*      */     
/*  564 */     if (!this.helper.isEmpty() && this.helper.getSelectedIndex() != -1) {
/*  565 */       paramGraphics.setFont(getPeerFont());
/*  566 */       FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/*  567 */       String str = this.helper.getItem(this.helper.getSelectedIndex());
/*  568 */       if (str != null && this.drawSelectedItem) {
/*  569 */         paramGraphics.setClip(1, 1, this.width - 18 - 2, this.height);
/*  570 */         if (isEnabled()) {
/*  571 */           paramGraphics.setColor(getPeerForeground());
/*  572 */           paramGraphics.drawString(str, 5, (this.height + fontMetrics.getMaxAscent() - fontMetrics.getMaxDescent()) / 2);
/*      */         } else {
/*      */           
/*  575 */           paramGraphics.setColor(getPeerBackground().brighter());
/*  576 */           paramGraphics.drawString(str, 5, (this.height + fontMetrics.getMaxAscent() - fontMetrics.getMaxDescent()) / 2);
/*  577 */           paramGraphics.setColor(getPeerBackground().darker());
/*  578 */           paramGraphics.drawString(str, 4, (this.height + fontMetrics.getMaxAscent() - fontMetrics.getMaxDescent()) / 2 - 1);
/*      */         } 
/*  580 */         paramGraphics.setClip(0, 0, this.width, this.height);
/*      */       } 
/*      */     } 
/*  583 */     if (hasFocus()) {
/*  584 */       paintFocus(paramGraphics, focusInsets.left, focusInsets.top, dimension.width - focusInsets.left + focusInsets.right - 1, dimension.height - focusInsets.top + focusInsets.bottom - 1);
/*      */     }
/*  586 */     if (this.unfurled) {
/*  587 */       this.unfurledChoice.repaint();
/*      */     }
/*  589 */     flush();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void paintFocus(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  594 */     paramGraphics.setColor(focusColor);
/*  595 */     paramGraphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void select(int paramInt) {
/*  605 */     this.helper.select(paramInt);
/*  606 */     this.helper.setFocusedIndex(paramInt);
/*  607 */     repaint();
/*      */   }
/*      */   
/*      */   public void add(String paramString, int paramInt) {
/*  611 */     this.helper.add(paramString, paramInt);
/*  612 */     repaint();
/*      */   }
/*      */   
/*      */   public void remove(int paramInt) {
/*  616 */     boolean bool1 = (paramInt == this.helper.getSelectedIndex()) ? true : false;
/*  617 */     boolean bool2 = (paramInt >= this.helper.firstDisplayedIndex() && paramInt <= this.helper.lastDisplayedIndex()) ? true : false;
/*  618 */     this.helper.remove(paramInt);
/*  619 */     if (bool1) {
/*  620 */       if (this.helper.isEmpty()) {
/*  621 */         this.helper.select(-1);
/*      */       } else {
/*      */         
/*  624 */         this.helper.select(0);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  636 */     if (!this.unfurled) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  641 */       if (this.helper.isEmpty()) {
/*  642 */         repaint();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  651 */     if (bool2) {
/*  652 */       Rectangle rectangle = this.unfurledChoice.placeOnScreen();
/*  653 */       this.unfurledChoice.reshape(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  662 */     if (bool2 || bool1) {
/*  663 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeAll() {
/*  668 */     this.helper.removeAll();
/*  669 */     this.helper.select(-1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  675 */     Rectangle rectangle = this.unfurledChoice.placeOnScreen();
/*  676 */     this.unfurledChoice.reshape(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*  677 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItem(String paramString, int paramInt) {
/*  684 */     add(paramString, paramInt);
/*      */   }
/*      */   
/*      */   public void setFont(Font paramFont) {
/*  688 */     super.setFont(paramFont);
/*  689 */     this.helper.setFont(this.font);
/*      */   }
/*      */   
/*      */   public void setForeground(Color paramColor) {
/*  693 */     super.setForeground(paramColor);
/*  694 */     this.helper.updateColors(getGUIcolors());
/*      */   }
/*      */   
/*      */   public void setBackground(Color paramColor) {
/*  698 */     super.setBackground(paramColor);
/*  699 */     this.unfurledChoice.setBackground(paramColor);
/*  700 */     this.helper.updateColors(getGUIcolors());
/*  701 */     updateMotifColors(paramColor);
/*      */   }
/*      */   
/*      */   public void setDrawSelectedItem(boolean paramBoolean) {
/*  705 */     this.drawSelectedItem = paramBoolean;
/*      */   }
/*      */   
/*      */   public void setAlignUnder(Component paramComponent) {
/*  709 */     this.alignUnder = paramComponent;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addXChoicePeerListener(XChoicePeerListener paramXChoicePeerListener) {
/*  714 */     this.choiceListener = paramXChoicePeerListener;
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeXChoicePeerListener() {
/*  719 */     this.choiceListener = null;
/*      */   }
/*      */   
/*      */   public boolean isUnfurled() {
/*  723 */     return this.unfurled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stateChangedICCCM(int paramInt1, int paramInt2) {
/*  731 */     if (this.unfurled && paramInt1 != paramInt2) {
/*  732 */       hidePopdownMenu();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void stateChangedJava(int paramInt1, int paramInt2) {
/*  738 */     if (this.unfurled && paramInt1 != paramInt2) {
/*  739 */       hidePopdownMenu();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class UnfurledChoice
/*      */     extends XWindow
/*      */   {
/*      */     public UnfurledChoice(Component param1Component) {
/*  756 */       super(param1Component);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void preInit(XCreateWindowParams param1XCreateWindowParams) {
/*  764 */       param1XCreateWindowParams.delete("parent window");
/*  765 */       super.preInit(param1XCreateWindowParams);
/*      */       
/*  767 */       param1XCreateWindowParams.remove("bounds");
/*  768 */       param1XCreateWindowParams.add("overrideRedirect", Boolean.TRUE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Rectangle placeOnScreen() {
/*      */       int i;
/*  779 */       if (XChoicePeer.this.helper.isEmpty()) {
/*  780 */         i = 1;
/*      */       } else {
/*      */         
/*  783 */         int j = XChoicePeer.this.helper.getItemCount();
/*  784 */         i = Math.min(10, j);
/*      */       } 
/*  786 */       Point point = XChoicePeer.this.toGlobal(0, 0);
/*  787 */       Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
/*      */       
/*  789 */       if (XChoicePeer.this.alignUnder != null) {
/*  790 */         Rectangle rectangle1 = XChoicePeer.this.getBounds();
/*  791 */         rectangle1.setLocation(0, 0);
/*  792 */         rectangle1 = XChoicePeer.this.toGlobal(rectangle1);
/*  793 */         Rectangle rectangle2 = new Rectangle(XChoicePeer.this.alignUnder.getLocationOnScreen(), XChoicePeer.this.alignUnder.getSize());
/*  794 */         Rectangle rectangle3 = rectangle1.union(rectangle2);
/*      */         
/*  796 */         this.width = rectangle3.width;
/*  797 */         this.x = rectangle3.x;
/*  798 */         this.y = rectangle3.y + rectangle3.height;
/*  799 */         this
/*  800 */           .height = 2 + i * (XChoicePeer.this.helper.getItemHeight() + 2);
/*      */       } else {
/*  802 */         this.x = point.x;
/*  803 */         this.y = point.y + XChoicePeer.this.height;
/*  804 */         this.width = Math.max(XChoicePeer.this.width, XChoicePeer.this
/*  805 */             .helper.getMaxItemWidth() + 6 + (XChoicePeer.this.helper.isVSBVisible() ? 15 : 0));
/*  806 */         this
/*  807 */           .height = 2 + i * (XChoicePeer.this.helper.getItemHeight() + 2);
/*      */       } 
/*      */       
/*  810 */       if (this.x < 0) {
/*  811 */         this.x = 0;
/*      */       }
/*  813 */       else if (this.x + this.width > dimension.width) {
/*  814 */         this.x = dimension.width - this.width;
/*      */       } 
/*      */       
/*  817 */       if (this.y + this.height > dimension.height) {
/*  818 */         this.y = point.y - this.height;
/*      */       }
/*  820 */       if (this.y < 0) {
/*  821 */         this.y = 0;
/*      */       }
/*  823 */       return new Rectangle(this.x, this.y, this.width, this.height);
/*      */     }
/*      */ 
/*      */     
/*      */     public void toFront() {
/*  828 */       if (XChoicePeer.this.choiceListener != null) {
/*  829 */         XChoicePeer.this.choiceListener.unfurledChoiceOpening(XChoicePeer.this.helper);
/*      */       }
/*  831 */       Rectangle rectangle = placeOnScreen();
/*  832 */       reshape(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*  833 */       super.toFront();
/*  834 */       setVisible(true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void trackMouse(MouseEvent param1MouseEvent) {
/*  845 */       Point point = toLocalCoords(param1MouseEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  850 */       switch (param1MouseEvent.getID()) {
/*      */ 
/*      */ 
/*      */         
/*      */         case 501:
/*  855 */           if (XChoicePeer.this.helper.isInVertSB(getBounds(), point.x, point.y)) {
/*  856 */             XChoicePeer.this.mouseInSB = true;
/*  857 */             XChoicePeer.this.helper.handleVSBEvent(param1MouseEvent, getBounds(), point.x, point.y);
/*      */             break;
/*      */           } 
/*  860 */           trackSelection(point.x, point.y);
/*      */           break;
/*      */         
/*      */         case 502:
/*  864 */           if (XChoicePeer.this.mouseInSB) {
/*  865 */             XChoicePeer.this.mouseInSB = false;
/*  866 */             XChoicePeer.this.helper.handleVSBEvent(param1MouseEvent, getBounds(), point.x, point.y);
/*      */             break;
/*      */           } 
/*  869 */           XChoicePeer.this.helper.trackMouseReleasedScroll();
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 506:
/*  878 */           if (XChoicePeer.this.mouseInSB) {
/*  879 */             XChoicePeer.this.helper.handleVSBEvent(param1MouseEvent, getBounds(), point.x, point.y);
/*      */             
/*      */             break;
/*      */           } 
/*  883 */           XChoicePeer.this.helper.trackMouseDraggedScroll(point.x, point.y, this.width, this.height);
/*  884 */           trackSelection(point.x, point.y);
/*      */           break;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void trackSelection(int param1Int1, int param1Int2) {
/*  891 */       if (!XChoicePeer.this.helper.isEmpty() && 
/*  892 */         param1Int1 > 0 && param1Int1 < this.width && param1Int2 > 0 && param1Int2 < this.height) {
/*      */         
/*  894 */         int i = XChoicePeer.this.helper.y2index(param1Int2);
/*  895 */         if (XChoicePeer.log.isLoggable(PlatformLogger.Level.FINE)) {
/*  896 */           XChoicePeer.log.fine("transX=" + param1Int1 + ", transY=" + param1Int2 + ",width=" + this.width + ", height=" + this.height + ", newIdx=" + i + " on " + this.target);
/*      */         }
/*      */ 
/*      */         
/*  900 */         if (i >= 0 && i < XChoicePeer.this.helper.getItemCount() && i != XChoicePeer.this
/*  901 */           .helper.getSelectedIndex()) {
/*      */           
/*  903 */           XChoicePeer.this.helper.select(i);
/*  904 */           XChoicePeer.this.unfurledChoice.repaint();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintBackground() {
/*  916 */       Graphics graphics = getGraphics();
/*  917 */       if (graphics != null) {
/*      */         try {
/*  919 */           graphics.setColor(XChoicePeer.this.getPeerBackground());
/*  920 */           graphics.fillRect(0, 0, this.width, this.height);
/*      */         } finally {
/*  922 */           graphics.dispose();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void repaint() {
/*  932 */       if (!isVisible()) {
/*      */         return;
/*      */       }
/*  935 */       if (XChoicePeer.this.helper.checkVsbVisibilityChangedAndReset()) {
/*  936 */         paintBackground();
/*      */       }
/*  938 */       super.repaint();
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintPeer(Graphics param1Graphics) {
/*  943 */       Choice choice = (Choice)this.target;
/*  944 */       Color[] arrayOfColor = XChoicePeer.this.getGUIcolors();
/*  945 */       XChoicePeer.this.draw3DRect(param1Graphics, XComponentPeer.getSystemColors(), 0, 0, this.width - 1, this.height - 1, true);
/*  946 */       XChoicePeer.this.draw3DRect(param1Graphics, XComponentPeer.getSystemColors(), 1, 1, this.width - 3, this.height - 3, true);
/*      */       
/*  948 */       XChoicePeer.this.helper.paintAllItems(param1Graphics, arrayOfColor, 
/*      */           
/*  950 */           getBounds());
/*      */     }
/*      */     
/*      */     public void setVisible(boolean param1Boolean) {
/*  954 */       xSetVisible(param1Boolean);
/*      */       
/*  956 */       if (!param1Boolean && XChoicePeer.this.alignUnder != null) {
/*  957 */         XChoicePeer.this.alignUnder.requestFocusInWindow();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Point toLocalCoords(MouseEvent param1MouseEvent) {
/*  967 */       Point point = param1MouseEvent.getLocationOnScreen();
/*      */       
/*  969 */       point.x -= this.x;
/*  970 */       point.y -= this.y;
/*  971 */       return point;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isMouseEventInside(MouseEvent param1MouseEvent) {
/*  978 */       Point point = toLocalCoords(param1MouseEvent);
/*  979 */       if (point.x > 0 && point.x < this.width && point.y > 0 && point.y < this.height)
/*      */       {
/*  981 */         return true;
/*      */       }
/*  983 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isMouseInListArea(MouseEvent param1MouseEvent) {
/*  991 */       if (isMouseEventInside(param1MouseEvent)) {
/*  992 */         Point point = toLocalCoords(param1MouseEvent);
/*  993 */         Rectangle rectangle = getBounds();
/*  994 */         if (!XChoicePeer.this.helper.isInVertSB(rectangle, point.x, point.y)) {
/*  995 */           return true;
/*      */         }
/*      */       } 
/*  998 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void handleConfigureNotifyEvent(XEvent param1XEvent) {}
/*      */ 
/*      */     
/*      */     public void handleMapNotifyEvent(XEvent param1XEvent) {}
/*      */     
/*      */     public void handleUnmapNotifyEvent(XEvent param1XEvent) {}
/*      */   }
/*      */   
/*      */   public void dispose() {
/* 1011 */     if (this.unfurledChoice != null) {
/* 1012 */       this.unfurledChoice.destroy();
/*      */     }
/* 1014 */     super.dispose();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean prePostEvent(final AWTEvent e) {
/* 1028 */     if (this.unfurled) {
/*      */       
/* 1030 */       if (e instanceof MouseWheelEvent) {
/* 1031 */         return super.prePostEvent(e);
/*      */       }
/*      */       
/* 1034 */       if (e instanceof KeyEvent) {
/*      */         
/* 1036 */         InvocationEvent invocationEvent = new InvocationEvent(this.target, new Runnable() {
/*      */               public void run() {
/* 1038 */                 if (XChoicePeer.this.target.isFocusable() && XChoicePeer.this
/* 1039 */                   .getParentTopLevel().isFocusableWindow())
/*      */                 {
/* 1041 */                   XChoicePeer.this.handleJavaKeyEvent((KeyEvent)e);
/*      */                 }
/*      */               }
/*      */             });
/* 1045 */         postEvent(invocationEvent);
/*      */         
/* 1047 */         return true;
/*      */       } 
/* 1049 */       if (e instanceof MouseEvent) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1055 */         MouseEvent mouseEvent = (MouseEvent)e;
/* 1056 */         int i = e.getID();
/*      */ 
/*      */         
/* 1059 */         if (this.unfurledChoice.isMouseEventInside(mouseEvent) || (!this.firstPress && i == 506))
/*      */         {
/*      */           
/* 1062 */           return handleMouseEventByChoice(mouseEvent);
/*      */         }
/*      */ 
/*      */         
/* 1066 */         if (i == 503) {
/* 1067 */           return handleMouseEventByChoice(mouseEvent);
/*      */         }
/*      */         
/* 1070 */         if (!this.firstPress && !isMouseEventInChoice(mouseEvent) && 
/* 1071 */           !this.unfurledChoice.isMouseEventInside(mouseEvent) && (i == 501 || i == 502 || i == 500))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1077 */           return handleMouseEventByChoice(mouseEvent);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1082 */     return super.prePostEvent(e);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleMouseEventByChoice(final MouseEvent me) {
/* 1088 */     InvocationEvent invocationEvent = new InvocationEvent(this.target, new Runnable() {
/*      */           public void run() {
/* 1090 */             XChoicePeer.this.handleJavaMouseEvent(me);
/*      */           }
/*      */         });
/* 1093 */     postEvent(invocationEvent);
/*      */     
/* 1095 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isMouseEventInChoice(MouseEvent paramMouseEvent) {
/* 1103 */     int i = paramMouseEvent.getX();
/* 1104 */     int j = paramMouseEvent.getY();
/* 1105 */     Rectangle rectangle = getBounds();
/*      */     
/* 1107 */     if (i < 0 || i > rectangle.width || j < 0 || j > rectangle.height)
/*      */     {
/*      */       
/* 1110 */       return false;
/*      */     }
/* 1112 */     return true;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XChoicePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */