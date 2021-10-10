/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.DefaultDesktopManager;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDesktopPane;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JLayeredPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicDesktopPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifDesktopPaneUI
/*     */   extends BasicDesktopPaneUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  55 */     return new MotifDesktopPaneUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDesktopManager() {
/*  62 */     this.desktopManager = this.desktop.getDesktopManager();
/*  63 */     if (this.desktopManager == null) {
/*  64 */       this.desktopManager = new MotifDesktopManager();
/*  65 */       this.desktop.setDesktopManager(this.desktopManager);
/*  66 */       ((MotifDesktopManager)this.desktopManager).adjustIcons(this.desktop);
/*     */     } 
/*     */   }
/*     */   public Insets getInsets(JComponent paramJComponent) {
/*  70 */     return new Insets(0, 0, 0, 0);
/*     */   }
/*     */   
/*     */   private class DragPane extends JComponent {
/*     */     private DragPane() {}
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/*  77 */       param1Graphics.setColor(Color.darkGray);
/*  78 */       param1Graphics.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class MotifDesktopManager
/*     */     extends DefaultDesktopManager
/*     */     implements Serializable, UIResource
/*     */   {
/*     */     JComponent dragPane;
/*     */     boolean usingDragPane = false;
/*     */     private transient JLayeredPane layeredPaneForDragPane;
/*     */     int iconWidth;
/*     */     int iconHeight;
/*     */     
/*     */     public void setBoundsForFrame(JComponent param1JComponent, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  94 */       if (!this.usingDragPane) {
/*     */         
/*  96 */         boolean bool = (param1JComponent.getWidth() != param1Int3 || param1JComponent.getHeight() != param1Int4) ? true : false;
/*  97 */         Rectangle rectangle = param1JComponent.getBounds();
/*  98 */         param1JComponent.setBounds(param1Int1, param1Int2, param1Int3, param1Int4);
/*  99 */         SwingUtilities.computeUnion(param1Int1, param1Int2, param1Int3, param1Int4, rectangle);
/* 100 */         param1JComponent.getParent().repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/* 101 */         if (bool) {
/* 102 */           param1JComponent.validate();
/*     */         }
/*     */       } else {
/* 105 */         Rectangle rectangle = this.dragPane.getBounds();
/* 106 */         this.dragPane.setBounds(param1Int1, param1Int2, param1Int3, param1Int4);
/* 107 */         SwingUtilities.computeUnion(param1Int1, param1Int2, param1Int3, param1Int4, rectangle);
/* 108 */         this.dragPane.getParent().repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void beginDraggingFrame(JComponent param1JComponent) {
/* 113 */       this.usingDragPane = false;
/* 114 */       if (param1JComponent.getParent() instanceof JLayeredPane) {
/* 115 */         if (this.dragPane == null)
/* 116 */           this.dragPane = new MotifDesktopPaneUI.DragPane(); 
/* 117 */         this.layeredPaneForDragPane = (JLayeredPane)param1JComponent.getParent();
/* 118 */         this.layeredPaneForDragPane.setLayer(this.dragPane, 2147483647);
/* 119 */         this.dragPane.setBounds(param1JComponent.getX(), param1JComponent.getY(), param1JComponent.getWidth(), param1JComponent.getHeight());
/* 120 */         this.layeredPaneForDragPane.add(this.dragPane);
/* 121 */         this.usingDragPane = true;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void dragFrame(JComponent param1JComponent, int param1Int1, int param1Int2) {
/* 126 */       setBoundsForFrame(param1JComponent, param1Int1, param1Int2, param1JComponent.getWidth(), param1JComponent.getHeight());
/*     */     }
/*     */     
/*     */     public void endDraggingFrame(JComponent param1JComponent) {
/* 130 */       if (this.usingDragPane) {
/* 131 */         this.layeredPaneForDragPane.remove(this.dragPane);
/* 132 */         this.usingDragPane = false;
/* 133 */         if (param1JComponent instanceof JInternalFrame) {
/* 134 */           setBoundsForFrame(param1JComponent, this.dragPane.getX(), this.dragPane.getY(), this.dragPane
/* 135 */               .getWidth(), this.dragPane.getHeight());
/* 136 */         } else if (param1JComponent instanceof JInternalFrame.JDesktopIcon) {
/* 137 */           adjustBoundsForIcon((JInternalFrame.JDesktopIcon)param1JComponent, this.dragPane
/* 138 */               .getX(), this.dragPane.getY());
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void beginResizingFrame(JComponent param1JComponent, int param1Int) {
/* 144 */       this.usingDragPane = false;
/* 145 */       if (param1JComponent.getParent() instanceof JLayeredPane) {
/* 146 */         if (this.dragPane == null)
/* 147 */           this.dragPane = new MotifDesktopPaneUI.DragPane(); 
/* 148 */         JLayeredPane jLayeredPane = (JLayeredPane)param1JComponent.getParent();
/* 149 */         jLayeredPane.setLayer(this.dragPane, 2147483647);
/* 150 */         this.dragPane.setBounds(param1JComponent.getX(), param1JComponent.getY(), param1JComponent
/* 151 */             .getWidth(), param1JComponent.getHeight());
/* 152 */         jLayeredPane.add(this.dragPane);
/* 153 */         this.usingDragPane = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void resizeFrame(JComponent param1JComponent, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 159 */       setBoundsForFrame(param1JComponent, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */     
/*     */     public void endResizingFrame(JComponent param1JComponent) {
/* 163 */       if (this.usingDragPane) {
/* 164 */         JLayeredPane jLayeredPane = (JLayeredPane)param1JComponent.getParent();
/* 165 */         jLayeredPane.remove(this.dragPane);
/* 166 */         this.usingDragPane = false;
/* 167 */         setBoundsForFrame(param1JComponent, this.dragPane.getX(), this.dragPane.getY(), this.dragPane
/* 168 */             .getWidth(), this.dragPane.getHeight());
/*     */       } 
/*     */     }
/*     */     
/*     */     public void iconifyFrame(JInternalFrame param1JInternalFrame) {
/* 173 */       JInternalFrame.JDesktopIcon jDesktopIcon = param1JInternalFrame.getDesktopIcon();
/* 174 */       Point point = jDesktopIcon.getLocation();
/* 175 */       adjustBoundsForIcon(jDesktopIcon, point.x, point.y);
/* 176 */       super.iconifyFrame(param1JInternalFrame);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void adjustIcons(JDesktopPane param1JDesktopPane) {
/* 185 */       JInternalFrame.JDesktopIcon jDesktopIcon = new JInternalFrame.JDesktopIcon(new JInternalFrame());
/*     */       
/* 187 */       Dimension dimension = jDesktopIcon.getPreferredSize();
/* 188 */       this.iconWidth = dimension.width;
/* 189 */       this.iconHeight = dimension.height;
/*     */       
/* 191 */       JInternalFrame[] arrayOfJInternalFrame = param1JDesktopPane.getAllFrames();
/* 192 */       for (byte b = 0; b < arrayOfJInternalFrame.length; b++) {
/* 193 */         jDesktopIcon = arrayOfJInternalFrame[b].getDesktopIcon();
/* 194 */         Point point = jDesktopIcon.getLocation();
/* 195 */         adjustBoundsForIcon(jDesktopIcon, point.x, point.y);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void adjustBoundsForIcon(JInternalFrame.JDesktopIcon param1JDesktopIcon, int param1Int1, int param1Int2) {
/* 205 */       JDesktopPane jDesktopPane = param1JDesktopIcon.getDesktopPane();
/*     */       
/* 207 */       int i = jDesktopPane.getHeight();
/* 208 */       int j = this.iconWidth;
/* 209 */       int k = this.iconHeight;
/* 210 */       jDesktopPane.repaint(param1Int1, param1Int2, j, k);
/* 211 */       param1Int1 = (param1Int1 < 0) ? 0 : param1Int1;
/* 212 */       param1Int2 = (param1Int2 < 0) ? 0 : param1Int2;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       param1Int2 = (param1Int2 >= i) ? (i - 1) : param1Int2;
/*     */ 
/*     */       
/* 220 */       int m = param1Int1 / j * j;
/* 221 */       int n = i % k;
/* 222 */       int i1 = (param1Int2 - n) / k * k + n;
/*     */ 
/*     */       
/* 225 */       int i2 = param1Int1 - m;
/* 226 */       int i3 = param1Int2 - i1;
/*     */ 
/*     */       
/* 229 */       param1Int1 = (i2 < j / 2) ? m : (m + j);
/* 230 */       param1Int2 = (i3 < k / 2) ? i1 : ((i1 + k < i) ? (i1 + k) : i1);
/*     */       
/* 232 */       while (getIconAt(jDesktopPane, param1JDesktopIcon, param1Int1, param1Int2) != null) {
/* 233 */         param1Int1 += j;
/*     */       }
/*     */ 
/*     */       
/* 237 */       if (param1Int1 > jDesktopPane.getWidth()) {
/*     */         return;
/*     */       }
/* 240 */       if (param1JDesktopIcon.getParent() != null) {
/* 241 */         setBoundsForFrame(param1JDesktopIcon, param1Int1, param1Int2, j, k);
/*     */       } else {
/* 243 */         param1JDesktopIcon.setLocation(param1Int1, param1Int2);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected JInternalFrame.JDesktopIcon getIconAt(JDesktopPane param1JDesktopPane, JInternalFrame.JDesktopIcon param1JDesktopIcon, int param1Int1, int param1Int2) {
/* 250 */       Object object = null;
/* 251 */       Component[] arrayOfComponent = param1JDesktopPane.getComponents();
/*     */       
/* 253 */       for (byte b = 0; b < arrayOfComponent.length; b++) {
/* 254 */         Component component = arrayOfComponent[b];
/* 255 */         if (component instanceof JInternalFrame.JDesktopIcon && component != param1JDesktopIcon) {
/*     */ 
/*     */           
/* 258 */           Point point = component.getLocation();
/* 259 */           if (point.x == param1Int1 && point.y == param1Int2) {
/* 260 */             return (JInternalFrame.JDesktopIcon)component;
/*     */           }
/*     */         } 
/*     */       } 
/* 264 */       return null;
/*     */     }
/*     */     
/*     */     private MotifDesktopManager() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifDesktopPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */