/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.beans.PropertyVetoException;
/*     */ import java.util.EventListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JLayeredPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicDesktopIconUI;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifDesktopIconUI
/*     */   extends BasicDesktopIconUI
/*     */ {
/*     */   protected DesktopIconActionListener desktopIconActionListener;
/*     */   protected DesktopIconMouseListener desktopIconMouseListener;
/*     */   protected Icon defaultIcon;
/*     */   protected IconButton iconButton;
/*     */   protected IconLabel iconLabel;
/*     */   private MotifInternalFrameTitlePane sysMenuTitlePane;
/*     */   JPopupMenu systemMenu;
/*     */   EventListener mml;
/*     */   static final int LABEL_HEIGHT = 18;
/*     */   static final int LABEL_DIVIDER = 4;
/*  67 */   static final Font defaultTitleFont = new Font("SansSerif", 0, 12);
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  71 */     return new MotifDesktopIconUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  78 */     super.installDefaults();
/*  79 */     setDefaultIcon(UIManager.getIcon("DesktopIcon.icon"));
/*  80 */     this.iconButton = createIconButton(this.defaultIcon);
/*     */     
/*  82 */     this.sysMenuTitlePane = new MotifInternalFrameTitlePane(this.frame);
/*  83 */     this.systemMenu = this.sysMenuTitlePane.getSystemMenu();
/*     */     
/*  85 */     MotifBorders.FrameBorder frameBorder = new MotifBorders.FrameBorder(this.desktopIcon);
/*  86 */     this.desktopIcon.setLayout(new BorderLayout());
/*  87 */     this.iconButton.setBorder(frameBorder);
/*  88 */     this.desktopIcon.add(this.iconButton, "Center");
/*  89 */     this.iconLabel = createIconLabel(this.frame);
/*  90 */     this.iconLabel.setBorder(frameBorder);
/*  91 */     this.desktopIcon.add(this.iconLabel, "South");
/*  92 */     this.desktopIcon.setSize(this.desktopIcon.getPreferredSize());
/*  93 */     this.desktopIcon.validate();
/*  94 */     JLayeredPane.putLayer(this.desktopIcon, JLayeredPane.getLayer(this.frame));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installComponents() {}
/*     */ 
/*     */   
/*     */   protected void uninstallComponents() {}
/*     */   
/*     */   protected void installListeners() {
/* 104 */     super.installListeners();
/* 105 */     this.desktopIconActionListener = createDesktopIconActionListener();
/* 106 */     this.desktopIconMouseListener = createDesktopIconMouseListener();
/* 107 */     this.iconButton.addActionListener(this.desktopIconActionListener);
/* 108 */     this.iconButton.addMouseListener(this.desktopIconMouseListener);
/* 109 */     this.iconLabel.addMouseListener(this.desktopIconMouseListener);
/*     */   }
/*     */   
/*     */   JInternalFrame.JDesktopIcon getDesktopIcon() {
/* 113 */     return this.desktopIcon;
/*     */   }
/*     */   
/*     */   void setDesktopIcon(JInternalFrame.JDesktopIcon paramJDesktopIcon) {
/* 117 */     this.desktopIcon = paramJDesktopIcon;
/*     */   }
/*     */   
/*     */   JInternalFrame getFrame() {
/* 121 */     return this.frame;
/*     */   }
/*     */   
/*     */   void setFrame(JInternalFrame paramJInternalFrame) {
/* 125 */     this.frame = paramJInternalFrame;
/*     */   }
/*     */   
/*     */   protected void showSystemMenu() {
/* 129 */     this.systemMenu.show(this.iconButton, 0, getDesktopIcon().getHeight());
/*     */   }
/*     */   
/*     */   protected void hideSystemMenu() {
/* 133 */     this.systemMenu.setVisible(false);
/*     */   }
/*     */   
/*     */   protected IconLabel createIconLabel(JInternalFrame paramJInternalFrame) {
/* 137 */     return new IconLabel(paramJInternalFrame);
/*     */   }
/*     */   
/*     */   protected IconButton createIconButton(Icon paramIcon) {
/* 141 */     return new IconButton(paramIcon);
/*     */   }
/*     */   
/*     */   protected DesktopIconActionListener createDesktopIconActionListener() {
/* 145 */     return new DesktopIconActionListener();
/*     */   }
/*     */   
/*     */   protected DesktopIconMouseListener createDesktopIconMouseListener() {
/* 149 */     return new DesktopIconMouseListener();
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {
/* 153 */     super.uninstallDefaults();
/* 154 */     this.desktopIcon.setLayout((LayoutManager)null);
/* 155 */     this.desktopIcon.remove(this.iconButton);
/* 156 */     this.desktopIcon.remove(this.iconLabel);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 160 */     super.uninstallListeners();
/* 161 */     this.iconButton.removeActionListener(this.desktopIconActionListener);
/* 162 */     this.iconButton.removeMouseListener(this.desktopIconMouseListener);
/* 163 */     this.sysMenuTitlePane.uninstallListeners();
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 167 */     JInternalFrame jInternalFrame = this.desktopIcon.getInternalFrame();
/*     */     
/* 169 */     int i = this.defaultIcon.getIconWidth();
/* 170 */     int j = this.defaultIcon.getIconHeight() + 18 + 4;
/*     */     
/* 172 */     Border border = jInternalFrame.getBorder();
/* 173 */     if (border != null) {
/* 174 */       i += (border.getBorderInsets(jInternalFrame)).left + 
/* 175 */         (border.getBorderInsets(jInternalFrame)).right;
/* 176 */       j += (border.getBorderInsets(jInternalFrame)).bottom + 
/* 177 */         (border.getBorderInsets(jInternalFrame)).top;
/*     */     } 
/*     */     
/* 180 */     return new Dimension(i, j);
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 184 */     return getMinimumSize(paramJComponent);
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 188 */     return getMinimumSize(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getDefaultIcon() {
/* 195 */     return this.defaultIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultIcon(Icon paramIcon) {
/* 202 */     this.defaultIcon = paramIcon;
/*     */   }
/*     */   
/*     */   protected class IconLabel
/*     */     extends JPanel {
/*     */     JInternalFrame frame;
/*     */     
/*     */     IconLabel(JInternalFrame param1JInternalFrame) {
/* 210 */       this.frame = param1JInternalFrame;
/* 211 */       setFont(MotifDesktopIconUI.defaultTitleFont);
/*     */ 
/*     */       
/* 214 */       addMouseMotionListener(new MouseMotionListener() {
/*     */             public void mouseDragged(MouseEvent param2MouseEvent) {
/* 216 */               MotifDesktopIconUI.IconLabel.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseMoved(MouseEvent param2MouseEvent) {
/* 219 */               MotifDesktopIconUI.IconLabel.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */           });
/* 222 */       addMouseListener(new MouseListener() {
/*     */             public void mouseClicked(MouseEvent param2MouseEvent) {
/* 224 */               MotifDesktopIconUI.IconLabel.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mousePressed(MouseEvent param2MouseEvent) {
/* 227 */               MotifDesktopIconUI.IconLabel.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseReleased(MouseEvent param2MouseEvent) {
/* 230 */               MotifDesktopIconUI.IconLabel.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseEntered(MouseEvent param2MouseEvent) {
/* 233 */               MotifDesktopIconUI.IconLabel.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseExited(MouseEvent param2MouseEvent) {
/* 236 */               MotifDesktopIconUI.IconLabel.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void forwardEventToParent(MouseEvent param1MouseEvent) {
/* 246 */       MouseEvent mouseEvent = new MouseEvent(getParent(), param1MouseEvent.getID(), param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getXOnScreen(), param1MouseEvent.getYOnScreen(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), 0);
/* 247 */       AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 248 */       mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/* 249 */           .isCausedByTouchEvent(param1MouseEvent));
/* 250 */       getParent().dispatchEvent(mouseEvent);
/*     */     }
/*     */     
/*     */     public boolean isFocusTraversable() {
/* 254 */       return false;
/*     */     }
/*     */     
/*     */     public Dimension getMinimumSize() {
/* 258 */       return new Dimension(MotifDesktopIconUI.this.defaultIcon.getIconWidth() + 1, 22);
/*     */     }
/*     */ 
/*     */     
/*     */     public Dimension getPreferredSize() {
/* 263 */       String str = this.frame.getTitle();
/* 264 */       FontMetrics fontMetrics = this.frame.getFontMetrics(MotifDesktopIconUI.defaultTitleFont);
/* 265 */       int i = 4;
/* 266 */       if (str != null) {
/* 267 */         i += SwingUtilities2.stringWidth(this.frame, fontMetrics, str);
/*     */       }
/* 269 */       return new Dimension(i, 22);
/*     */     }
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 273 */       super.paint(param1Graphics);
/*     */ 
/*     */       
/* 276 */       int i = getWidth() - 1;
/*     */       
/* 278 */       Color color = UIManager.getColor("inactiveCaptionBorder").darker().darker();
/* 279 */       param1Graphics.setColor(color);
/* 280 */       param1Graphics.setClip(0, 0, getWidth(), getHeight());
/* 281 */       param1Graphics.drawLine(i - 1, 1, i - 1, 1);
/* 282 */       param1Graphics.drawLine(i, 0, i, 0);
/*     */ 
/*     */       
/* 285 */       param1Graphics.setColor(UIManager.getColor("inactiveCaption"));
/* 286 */       param1Graphics.fillRect(2, 1, i - 3, 19);
/*     */ 
/*     */       
/* 289 */       param1Graphics.setClip(2, 1, i - 4, 18);
/*     */       
/* 291 */       int j = 18 - SwingUtilities2.getFontMetrics(this.frame, param1Graphics).getDescent();
/* 292 */       param1Graphics.setColor(UIManager.getColor("inactiveCaptionText"));
/* 293 */       String str = this.frame.getTitle();
/* 294 */       if (str != null)
/* 295 */         SwingUtilities2.drawString(this.frame, param1Graphics, str, 4, j); 
/*     */     }
/*     */   }
/*     */   
/*     */   protected class IconButton
/*     */     extends JButton {
/*     */     Icon icon;
/*     */     
/*     */     IconButton(Icon param1Icon) {
/* 304 */       super(param1Icon);
/* 305 */       this.icon = param1Icon;
/*     */       
/* 307 */       addMouseMotionListener(new MouseMotionListener() {
/*     */             public void mouseDragged(MouseEvent param2MouseEvent) {
/* 309 */               MotifDesktopIconUI.IconButton.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseMoved(MouseEvent param2MouseEvent) {
/* 312 */               MotifDesktopIconUI.IconButton.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */           });
/* 315 */       addMouseListener(new MouseListener() {
/*     */             public void mouseClicked(MouseEvent param2MouseEvent) {
/* 317 */               MotifDesktopIconUI.IconButton.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mousePressed(MouseEvent param2MouseEvent) {
/* 320 */               MotifDesktopIconUI.IconButton.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseReleased(MouseEvent param2MouseEvent) {
/* 323 */               if (!MotifDesktopIconUI.this.systemMenu.isShowing())
/* 324 */                 MotifDesktopIconUI.IconButton.this.forwardEventToParent(param2MouseEvent); 
/*     */             }
/*     */             
/*     */             public void mouseEntered(MouseEvent param2MouseEvent) {
/* 328 */               MotifDesktopIconUI.IconButton.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */             public void mouseExited(MouseEvent param2MouseEvent) {
/* 331 */               MotifDesktopIconUI.IconButton.this.forwardEventToParent(param2MouseEvent);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void forwardEventToParent(MouseEvent param1MouseEvent) {
/* 340 */       MouseEvent mouseEvent = new MouseEvent(getParent(), param1MouseEvent.getID(), param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getXOnScreen(), param1MouseEvent.getYOnScreen(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), 0);
/* 341 */       AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 342 */       mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/* 343 */           .isCausedByTouchEvent(param1MouseEvent));
/* 344 */       getParent().dispatchEvent(mouseEvent);
/*     */     }
/*     */     
/*     */     public boolean isFocusTraversable() {
/* 348 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class DesktopIconActionListener
/*     */     implements ActionListener {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 355 */       MotifDesktopIconUI.this.systemMenu.show(MotifDesktopIconUI.this.iconButton, 0, MotifDesktopIconUI.this.getDesktopIcon().getHeight());
/*     */     }
/*     */   }
/*     */   
/*     */   protected class DesktopIconMouseListener
/*     */     extends MouseAdapter {
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 362 */       if (param1MouseEvent.getClickCount() > 1) {
/*     */         try {
/* 364 */           MotifDesktopIconUI.this.getFrame().setIcon(false);
/* 365 */         } catch (PropertyVetoException propertyVetoException) {}
/* 366 */         MotifDesktopIconUI.this.systemMenu.setVisible(false);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 371 */         MotifDesktopIconUI.this.getFrame().getDesktopPane().getDesktopManager().endDraggingFrame((JComponent)param1MouseEvent.getSource());
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifDesktopIconUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */