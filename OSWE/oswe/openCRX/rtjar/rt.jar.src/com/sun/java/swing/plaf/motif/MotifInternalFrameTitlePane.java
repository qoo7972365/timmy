/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyVetoException;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
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
/*     */ public class MotifInternalFrameTitlePane
/*     */   extends BasicInternalFrameTitlePane
/*     */   implements LayoutManager, ActionListener, PropertyChangeListener
/*     */ {
/*     */   SystemButton systemButton;
/*     */   MinimizeButton minimizeButton;
/*     */   MaximizeButton maximizeButton;
/*     */   JPopupMenu systemMenu;
/*     */   Title title;
/*     */   Color color;
/*     */   Color highlight;
/*     */   Color shadow;
/*     */   public static final int BUTTON_SIZE = 19;
/*     */   
/*     */   public MotifInternalFrameTitlePane(JInternalFrame paramJInternalFrame) {
/*  65 */     super(paramJInternalFrame);
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/*  69 */     setFont(UIManager.getFont("InternalFrame.titleFont"));
/*  70 */     setPreferredSize(new Dimension(100, 19));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/*  75 */     super.uninstallListeners();
/*     */   }
/*     */   
/*     */   protected PropertyChangeListener createPropertyChangeListener() {
/*  79 */     return this;
/*     */   }
/*     */   
/*     */   protected LayoutManager createLayout() {
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   JPopupMenu getSystemMenu() {
/*  87 */     return this.systemMenu;
/*     */   }
/*     */   
/*     */   protected void assembleSystemMenu() {
/*  91 */     this.systemMenu = new JPopupMenu();
/*  92 */     JMenuItem jMenuItem = this.systemMenu.add(this.restoreAction);
/*  93 */     jMenuItem.setMnemonic(getButtonMnemonic("restore"));
/*  94 */     jMenuItem = this.systemMenu.add(this.moveAction);
/*  95 */     jMenuItem.setMnemonic(getButtonMnemonic("move"));
/*  96 */     jMenuItem = this.systemMenu.add(this.sizeAction);
/*  97 */     jMenuItem.setMnemonic(getButtonMnemonic("size"));
/*  98 */     jMenuItem = this.systemMenu.add(this.iconifyAction);
/*  99 */     jMenuItem.setMnemonic(getButtonMnemonic("minimize"));
/* 100 */     jMenuItem = this.systemMenu.add(this.maximizeAction);
/* 101 */     jMenuItem.setMnemonic(getButtonMnemonic("maximize"));
/* 102 */     this.systemMenu.add(new JSeparator());
/* 103 */     jMenuItem = this.systemMenu.add(this.closeAction);
/* 104 */     jMenuItem.setMnemonic(getButtonMnemonic("close"));
/*     */     
/* 106 */     this.systemButton = new SystemButton();
/* 107 */     this.systemButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent param1ActionEvent) {
/* 109 */             MotifInternalFrameTitlePane.this.systemMenu.show(MotifInternalFrameTitlePane.this.systemButton, 0, 19);
/*     */           }
/*     */         });
/*     */     
/* 113 */     this.systemButton.addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent param1MouseEvent) {
/*     */             try {
/* 116 */               MotifInternalFrameTitlePane.this.frame.setSelected(true);
/* 117 */             } catch (PropertyVetoException propertyVetoException) {}
/*     */             
/* 119 */             if (param1MouseEvent.getClickCount() == 2) {
/* 120 */               MotifInternalFrameTitlePane.this.closeAction.actionPerformed(new ActionEvent(param1MouseEvent
/* 121 */                     .getSource(), 1001, null, param1MouseEvent
/*     */                     
/* 123 */                     .getWhen(), 0));
/* 124 */               MotifInternalFrameTitlePane.this.systemMenu.setVisible(false);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static int getButtonMnemonic(String paramString) {
/*     */     try {
/* 132 */       return Integer.parseInt(UIManager.getString("InternalFrameTitlePane." + paramString + "Button.mnemonic"));
/*     */     }
/* 134 */     catch (NumberFormatException numberFormatException) {
/* 135 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void createButtons() {
/* 140 */     this.minimizeButton = new MinimizeButton();
/* 141 */     this.minimizeButton.addActionListener(this.iconifyAction);
/*     */     
/* 143 */     this.maximizeButton = new MaximizeButton();
/* 144 */     this.maximizeButton.addActionListener(this.maximizeAction);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addSubComponents() {
/* 149 */     this.title = new Title(this.frame.getTitle());
/* 150 */     this.title.setFont(getFont());
/*     */     
/* 152 */     add(this.systemButton);
/* 153 */     add(this.title);
/* 154 */     add(this.minimizeButton);
/* 155 */     add(this.maximizeButton);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics paramGraphics) {}
/*     */   
/*     */   void setColors(Color paramColor1, Color paramColor2, Color paramColor3) {
/* 162 */     this.color = paramColor1;
/* 163 */     this.highlight = paramColor2;
/* 164 */     this.shadow = paramColor3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {}
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 171 */     String str = paramPropertyChangeEvent.getPropertyName();
/* 172 */     JInternalFrame jInternalFrame = (JInternalFrame)paramPropertyChangeEvent.getSource();
/* 173 */     boolean bool = false;
/* 174 */     if ("selected".equals(str)) {
/* 175 */       repaint();
/* 176 */     } else if (str.equals("maximizable")) {
/* 177 */       if ((Boolean)paramPropertyChangeEvent.getNewValue() == Boolean.TRUE) {
/* 178 */         add(this.maximizeButton);
/*     */       } else {
/* 180 */         remove(this.maximizeButton);
/* 181 */       }  revalidate();
/* 182 */       repaint();
/* 183 */     } else if (str.equals("iconable")) {
/* 184 */       if ((Boolean)paramPropertyChangeEvent.getNewValue() == Boolean.TRUE) {
/* 185 */         add(this.minimizeButton);
/*     */       } else {
/* 187 */         remove(this.minimizeButton);
/* 188 */       }  revalidate();
/* 189 */       repaint();
/* 190 */     } else if (str.equals("title")) {
/* 191 */       repaint();
/*     */     } 
/* 193 */     enableActions();
/*     */   }
/*     */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*     */   public void removeLayoutComponent(Component paramComponent) {}
/*     */   
/*     */   public Dimension preferredLayoutSize(Container paramContainer) {
/* 199 */     return minimumLayoutSize(paramContainer);
/*     */   }
/*     */   
/*     */   public Dimension minimumLayoutSize(Container paramContainer) {
/* 203 */     return new Dimension(100, 19);
/*     */   }
/*     */   
/*     */   public void layoutContainer(Container paramContainer) {
/* 207 */     int i = getWidth();
/* 208 */     this.systemButton.setBounds(0, 0, 19, 19);
/* 209 */     int j = i - 19;
/*     */     
/* 211 */     if (this.frame.isMaximizable()) {
/* 212 */       this.maximizeButton.setBounds(j, 0, 19, 19);
/* 213 */       j -= 19;
/* 214 */     } else if (this.maximizeButton.getParent() != null) {
/* 215 */       this.maximizeButton.getParent().remove(this.maximizeButton);
/*     */     } 
/*     */     
/* 218 */     if (this.frame.isIconifiable()) {
/* 219 */       this.minimizeButton.setBounds(j, 0, 19, 19);
/* 220 */       j -= 19;
/* 221 */     } else if (this.minimizeButton.getParent() != null) {
/* 222 */       this.minimizeButton.getParent().remove(this.minimizeButton);
/*     */     } 
/*     */     
/* 225 */     this.title.setBounds(19, 0, j, 19);
/*     */   }
/*     */   
/*     */   protected void showSystemMenu() {
/* 229 */     this.systemMenu.show(this.systemButton, 0, 19);
/*     */   }
/*     */   
/*     */   protected void hideSystemMenu() {
/* 233 */     this.systemMenu.setVisible(false);
/*     */   }
/*     */   
/* 236 */   static Dimension buttonDimension = new Dimension(19, 19);
/*     */   
/*     */   private abstract class FrameButton
/*     */     extends JButton
/*     */   {
/*     */     FrameButton() {
/* 242 */       setFocusPainted(false);
/* 243 */       setBorderPainted(false);
/*     */     }
/*     */     
/*     */     public boolean isFocusTraversable() {
/* 247 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void requestFocus() {}
/*     */ 
/*     */     
/*     */     public Dimension getMinimumSize() {
/* 255 */       return MotifInternalFrameTitlePane.buttonDimension;
/*     */     }
/*     */     
/*     */     public Dimension getPreferredSize() {
/* 259 */       return MotifInternalFrameTitlePane.buttonDimension;
/*     */     }
/*     */     
/*     */     public void paintComponent(Graphics param1Graphics) {
/* 263 */       Dimension dimension = getSize();
/* 264 */       int i = dimension.width - 1;
/* 265 */       int j = dimension.height - 1;
/*     */ 
/*     */       
/* 268 */       param1Graphics.setColor(MotifInternalFrameTitlePane.this.color);
/* 269 */       param1Graphics.fillRect(1, 1, dimension.width, dimension.height);
/*     */ 
/*     */       
/* 272 */       boolean bool = getModel().isPressed();
/* 273 */       param1Graphics.setColor(bool ? MotifInternalFrameTitlePane.this.shadow : MotifInternalFrameTitlePane.this.highlight);
/* 274 */       param1Graphics.drawLine(0, 0, i, 0);
/* 275 */       param1Graphics.drawLine(0, 0, 0, j);
/* 276 */       param1Graphics.setColor(bool ? MotifInternalFrameTitlePane.this.highlight : MotifInternalFrameTitlePane.this.shadow);
/* 277 */       param1Graphics.drawLine(1, j, i, j);
/* 278 */       param1Graphics.drawLine(i, 1, i, j);
/*     */     } }
/*     */   
/*     */   private class MinimizeButton extends FrameButton { private MinimizeButton() {}
/*     */     
/*     */     public void paintComponent(Graphics param1Graphics) {
/* 284 */       super.paintComponent(param1Graphics);
/* 285 */       param1Graphics.setColor(MotifInternalFrameTitlePane.this.highlight);
/* 286 */       param1Graphics.drawLine(7, 8, 7, 11);
/* 287 */       param1Graphics.drawLine(7, 8, 10, 8);
/* 288 */       param1Graphics.setColor(MotifInternalFrameTitlePane.this.shadow);
/* 289 */       param1Graphics.drawLine(8, 11, 10, 11);
/* 290 */       param1Graphics.drawLine(11, 9, 11, 11);
/*     */     } }
/*     */   
/*     */   private class MaximizeButton extends FrameButton { private MaximizeButton() {}
/*     */     
/*     */     public void paintComponent(Graphics param1Graphics) {
/* 296 */       super.paintComponent(param1Graphics);
/* 297 */       byte b = 14;
/* 298 */       boolean bool = MotifInternalFrameTitlePane.this.frame.isMaximum();
/* 299 */       param1Graphics.setColor(bool ? MotifInternalFrameTitlePane.this.shadow : MotifInternalFrameTitlePane.this.highlight);
/* 300 */       param1Graphics.drawLine(4, 4, 4, b);
/* 301 */       param1Graphics.drawLine(4, 4, b, 4);
/* 302 */       param1Graphics.setColor(bool ? MotifInternalFrameTitlePane.this.highlight : MotifInternalFrameTitlePane.this.shadow);
/* 303 */       param1Graphics.drawLine(5, b, b, b);
/* 304 */       param1Graphics.drawLine(b, 5, b, b);
/*     */     } }
/*     */   
/*     */   private class SystemButton extends FrameButton {
/*     */     public boolean isFocusTraversable() {
/* 309 */       return false;
/*     */     } private SystemButton() {}
/*     */     public void requestFocus() {}
/*     */     public void paintComponent(Graphics param1Graphics) {
/* 313 */       super.paintComponent(param1Graphics);
/* 314 */       param1Graphics.setColor(MotifInternalFrameTitlePane.this.highlight);
/* 315 */       param1Graphics.drawLine(4, 8, 4, 11);
/* 316 */       param1Graphics.drawLine(4, 8, 14, 8);
/* 317 */       param1Graphics.setColor(MotifInternalFrameTitlePane.this.shadow);
/* 318 */       param1Graphics.drawLine(5, 11, 14, 11);
/* 319 */       param1Graphics.drawLine(14, 9, 14, 11);
/*     */     }
/*     */   }
/*     */   
/*     */   private class Title
/*     */     extends FrameButton {
/*     */     Title(String param1String) {
/* 326 */       setText(param1String);
/* 327 */       setHorizontalAlignment(0);
/* 328 */       setBorder(BorderFactory.createBevelBorder(0, 
/*     */             
/* 330 */             UIManager.getColor("activeCaptionBorder"), 
/* 331 */             UIManager.getColor("inactiveCaptionBorder")));
/*     */ 
/*     */       
/* 334 */       addMouseMotionListener(new MouseMotionListener() {
/*     */             public void mouseDragged(MouseEvent param2MouseEvent) {
/* 336 */               MotifInternalFrameTitlePane.Title.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseMoved(MouseEvent param2MouseEvent) {
/* 339 */               MotifInternalFrameTitlePane.Title.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */           });
/* 342 */       addMouseListener(new MouseListener() {
/*     */             public void mouseClicked(MouseEvent param2MouseEvent) {
/* 344 */               MotifInternalFrameTitlePane.Title.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mousePressed(MouseEvent param2MouseEvent) {
/* 347 */               MotifInternalFrameTitlePane.Title.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseReleased(MouseEvent param2MouseEvent) {
/* 350 */               MotifInternalFrameTitlePane.Title.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseEntered(MouseEvent param2MouseEvent) {
/* 353 */               MotifInternalFrameTitlePane.Title.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseExited(MouseEvent param2MouseEvent) {
/* 356 */               MotifInternalFrameTitlePane.Title.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void forwardEventToParent(MouseEvent param1MouseEvent) {
/* 366 */       MouseEvent mouseEvent = new MouseEvent(getParent(), param1MouseEvent.getID(), param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getXOnScreen(), param1MouseEvent.getYOnScreen(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), 0);
/* 367 */       AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 368 */       mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/* 369 */           .isCausedByTouchEvent(param1MouseEvent));
/* 370 */       getParent().dispatchEvent(mouseEvent);
/*     */     }
/*     */     
/*     */     public void paintComponent(Graphics param1Graphics) {
/* 374 */       super.paintComponent(param1Graphics);
/* 375 */       if (MotifInternalFrameTitlePane.this.frame.isSelected()) {
/* 376 */         param1Graphics.setColor(UIManager.getColor("activeCaptionText"));
/*     */       } else {
/* 378 */         param1Graphics.setColor(UIManager.getColor("inactiveCaptionText"));
/*     */       } 
/* 380 */       Dimension dimension = getSize();
/* 381 */       String str = MotifInternalFrameTitlePane.this.frame.getTitle();
/* 382 */       if (str != null)
/* 383 */         MotifGraphicsUtils.drawStringInRect(MotifInternalFrameTitlePane.this.frame, param1Graphics, str, 0, 0, dimension.width, dimension.height, 0); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifInternalFrameTitlePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */