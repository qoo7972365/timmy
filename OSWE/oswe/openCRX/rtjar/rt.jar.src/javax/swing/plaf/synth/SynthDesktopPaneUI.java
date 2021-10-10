/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.awt.event.ContainerEvent;
/*     */ import java.awt.event.ContainerListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyVetoException;
/*     */ import javax.swing.DefaultDesktopManager;
/*     */ import javax.swing.DesktopManager;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDesktopPane;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JLayeredPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.BevelBorder;
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
/*     */ public class SynthDesktopPaneUI
/*     */   extends BasicDesktopPaneUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   private TaskBar taskBar;
/*     */   private DesktopManager oldDesktopManager;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  57 */     return new SynthDesktopPaneUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  65 */     super.installListeners();
/*  66 */     this.desktop.addPropertyChangeListener(this);
/*  67 */     if (this.taskBar != null) {
/*     */       
/*  69 */       this.desktop.addComponentListener(this.taskBar);
/*     */       
/*  71 */       this.desktop.addContainerListener(this.taskBar);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  80 */     updateStyle(this.desktop);
/*     */     
/*  82 */     if (UIManager.getBoolean("InternalFrame.useTaskBar")) {
/*  83 */       this.taskBar = new TaskBar();
/*     */       
/*  85 */       for (Component component : this.desktop.getComponents()) {
/*     */         JInternalFrame.JDesktopIcon jDesktopIcon;
/*     */         
/*  88 */         if (component instanceof JInternalFrame.JDesktopIcon) {
/*  89 */           jDesktopIcon = (JInternalFrame.JDesktopIcon)component;
/*  90 */         } else if (component instanceof JInternalFrame) {
/*  91 */           jDesktopIcon = ((JInternalFrame)component).getDesktopIcon();
/*     */         } else {
/*     */           continue;
/*     */         } 
/*     */         
/*  96 */         if (jDesktopIcon.getParent() == this.desktop) {
/*  97 */           this.desktop.remove(jDesktopIcon);
/*     */         }
/*  99 */         if (jDesktopIcon.getParent() != this.taskBar) {
/* 100 */           this.taskBar.add(jDesktopIcon);
/* 101 */           jDesktopIcon.getInternalFrame().addComponentListener(this.taskBar);
/*     */         } 
/*     */         continue;
/*     */       } 
/* 105 */       this.taskBar.setBackground(this.desktop.getBackground());
/* 106 */       this.desktop.add(this.taskBar, 
/* 107 */           Integer.valueOf(JLayeredPane.PALETTE_LAYER.intValue() + 1));
/* 108 */       if (this.desktop.isShowing()) {
/* 109 */         this.taskBar.adjustSize();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateStyle(JDesktopPane paramJDesktopPane) {
/* 115 */     SynthStyle synthStyle = this.style;
/* 116 */     SynthContext synthContext = getContext(paramJDesktopPane, 1);
/* 117 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 118 */     if (synthStyle != null) {
/* 119 */       uninstallKeyboardActions();
/* 120 */       installKeyboardActions();
/*     */     } 
/* 122 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 130 */     if (this.taskBar != null) {
/* 131 */       this.desktop.removeComponentListener(this.taskBar);
/* 132 */       this.desktop.removeContainerListener(this.taskBar);
/*     */     } 
/* 134 */     this.desktop.removePropertyChangeListener(this);
/* 135 */     super.uninstallListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 143 */     SynthContext synthContext = getContext(this.desktop, 1);
/*     */     
/* 145 */     this.style.uninstallDefaults(synthContext);
/* 146 */     synthContext.dispose();
/* 147 */     this.style = null;
/*     */     
/* 149 */     if (this.taskBar != null) {
/* 150 */       for (Component component : this.taskBar.getComponents()) {
/* 151 */         JInternalFrame.JDesktopIcon jDesktopIcon = (JInternalFrame.JDesktopIcon)component;
/*     */         
/* 153 */         this.taskBar.remove(jDesktopIcon);
/* 154 */         jDesktopIcon.setPreferredSize((Dimension)null);
/* 155 */         JInternalFrame jInternalFrame = jDesktopIcon.getInternalFrame();
/* 156 */         if (jInternalFrame.isIcon()) {
/* 157 */           this.desktop.add(jDesktopIcon);
/*     */         }
/* 159 */         jInternalFrame.removeComponentListener(this.taskBar);
/*     */       } 
/* 161 */       this.desktop.remove(this.taskBar);
/* 162 */       this.taskBar = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDesktopManager() {
/* 171 */     if (UIManager.getBoolean("InternalFrame.useTaskBar")) {
/* 172 */       this.desktopManager = this.oldDesktopManager = this.desktop.getDesktopManager();
/* 173 */       if (!(this.desktopManager instanceof SynthDesktopManager)) {
/* 174 */         this.desktopManager = new SynthDesktopManager();
/* 175 */         this.desktop.setDesktopManager(this.desktopManager);
/*     */       } 
/*     */     } else {
/* 178 */       super.installDesktopManager();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDesktopManager() {
/* 187 */     if (this.oldDesktopManager != null && !(this.oldDesktopManager instanceof UIResource)) {
/* 188 */       this.desktopManager = this.desktop.getDesktopManager();
/* 189 */       if (this.desktopManager == null || this.desktopManager instanceof UIResource) {
/* 190 */         this.desktop.setDesktopManager(this.oldDesktopManager);
/*     */       }
/*     */     } 
/* 193 */     this.oldDesktopManager = null;
/* 194 */     super.uninstallDesktopManager();
/*     */   }
/*     */   
/*     */   static class TaskBar extends JPanel implements ComponentListener, ContainerListener {
/*     */     TaskBar() {
/* 199 */       setOpaque(true);
/* 200 */       setLayout(new FlowLayout(0, 0, 0)
/*     */           {
/*     */             public void layoutContainer(Container param2Container) {
/* 203 */               Component[] arrayOfComponent = param2Container.getComponents();
/* 204 */               int i = arrayOfComponent.length;
/* 205 */               if (i > 0) {
/*     */                 
/* 207 */                 int j = 0;
/* 208 */                 for (Component component : arrayOfComponent) {
/* 209 */                   component.setPreferredSize(null);
/* 210 */                   Dimension dimension = component.getPreferredSize();
/* 211 */                   if (dimension.width > j) {
/* 212 */                     j = dimension.width;
/*     */                   }
/*     */                 } 
/*     */                 
/* 216 */                 Insets insets = param2Container.getInsets();
/* 217 */                 int k = param2Container.getWidth() - insets.left - insets.right;
/* 218 */                 int m = Math.min(j, Math.max(10, k / i));
/* 219 */                 for (Component component : arrayOfComponent) {
/* 220 */                   Dimension dimension = component.getPreferredSize();
/* 221 */                   component.setPreferredSize(new Dimension(m, dimension.height));
/*     */                 } 
/*     */               } 
/* 224 */               super.layoutContainer(param2Container);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 229 */       setBorder(new BevelBorder(0)
/*     */           {
/*     */             protected void paintRaisedBevel(Component param2Component, Graphics param2Graphics, int param2Int1, int param2Int2, int param2Int3, int param2Int4) {
/* 232 */               Color color = param2Graphics.getColor();
/* 233 */               param2Graphics.translate(param2Int1, param2Int2);
/* 234 */               param2Graphics.setColor(getHighlightOuterColor(param2Component));
/* 235 */               param2Graphics.drawLine(0, 0, 0, param2Int4 - 2);
/* 236 */               param2Graphics.drawLine(1, 0, param2Int3 - 2, 0);
/* 237 */               param2Graphics.setColor(getShadowOuterColor(param2Component));
/* 238 */               param2Graphics.drawLine(0, param2Int4 - 1, param2Int3 - 1, param2Int4 - 1);
/* 239 */               param2Graphics.drawLine(param2Int3 - 1, 0, param2Int3 - 1, param2Int4 - 2);
/* 240 */               param2Graphics.translate(-param2Int1, -param2Int2);
/* 241 */               param2Graphics.setColor(color);
/*     */             }
/*     */           });
/*     */     }
/*     */     
/*     */     void adjustSize() {
/* 247 */       JDesktopPane jDesktopPane = (JDesktopPane)getParent();
/* 248 */       if (jDesktopPane != null) {
/* 249 */         int i = (getPreferredSize()).height;
/* 250 */         Insets insets = getInsets();
/* 251 */         if (i == insets.top + insets.bottom) {
/* 252 */           if (getHeight() <= i) {
/*     */             
/* 254 */             i += 21;
/*     */           } else {
/*     */             
/* 257 */             i = getHeight();
/*     */           } 
/*     */         }
/* 260 */         setBounds(0, jDesktopPane.getHeight() - i, jDesktopPane.getWidth(), i);
/* 261 */         revalidate();
/* 262 */         repaint();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void componentResized(ComponentEvent param1ComponentEvent) {
/* 269 */       if (param1ComponentEvent.getSource() instanceof JDesktopPane) {
/* 270 */         adjustSize();
/*     */       }
/*     */     }
/*     */     
/*     */     public void componentMoved(ComponentEvent param1ComponentEvent) {}
/*     */     
/*     */     public void componentShown(ComponentEvent param1ComponentEvent) {
/* 277 */       if (param1ComponentEvent.getSource() instanceof JInternalFrame) {
/* 278 */         adjustSize();
/*     */       }
/*     */     }
/*     */     
/*     */     public void componentHidden(ComponentEvent param1ComponentEvent) {
/* 283 */       if (param1ComponentEvent.getSource() instanceof JInternalFrame) {
/* 284 */         ((JInternalFrame)param1ComponentEvent.getSource()).getDesktopIcon().setVisible(false);
/* 285 */         revalidate();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void componentAdded(ContainerEvent param1ContainerEvent) {
/* 292 */       if (param1ContainerEvent.getChild() instanceof JInternalFrame) {
/* 293 */         JDesktopPane jDesktopPane = (JDesktopPane)param1ContainerEvent.getSource();
/* 294 */         JInternalFrame jInternalFrame = (JInternalFrame)param1ContainerEvent.getChild();
/* 295 */         JInternalFrame.JDesktopIcon jDesktopIcon = jInternalFrame.getDesktopIcon();
/* 296 */         for (Component component : getComponents()) {
/* 297 */           if (component == jDesktopIcon) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */         
/* 302 */         add(jDesktopIcon);
/* 303 */         jInternalFrame.addComponentListener(this);
/* 304 */         if (getComponentCount() == 1) {
/* 305 */           adjustSize();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public void componentRemoved(ContainerEvent param1ContainerEvent) {
/* 311 */       if (param1ContainerEvent.getChild() instanceof JInternalFrame) {
/* 312 */         JInternalFrame jInternalFrame = (JInternalFrame)param1ContainerEvent.getChild();
/* 313 */         if (!jInternalFrame.isIcon()) {
/*     */           
/* 315 */           remove(jInternalFrame.getDesktopIcon());
/* 316 */           jInternalFrame.removeComponentListener(this);
/* 317 */           revalidate();
/* 318 */           repaint();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   class SynthDesktopManager
/*     */     extends DefaultDesktopManager
/*     */     implements UIResource {
/*     */     public void maximizeFrame(JInternalFrame param1JInternalFrame) {
/* 328 */       if (param1JInternalFrame.isIcon()) {
/*     */         try {
/* 330 */           param1JInternalFrame.setIcon(false);
/* 331 */         } catch (PropertyVetoException propertyVetoException) {}
/*     */       } else {
/*     */         
/* 334 */         param1JInternalFrame.setNormalBounds(param1JInternalFrame.getBounds());
/* 335 */         Container container = param1JInternalFrame.getParent();
/* 336 */         setBoundsForFrame(param1JInternalFrame, 0, 0, container
/* 337 */             .getWidth(), container
/* 338 */             .getHeight() - SynthDesktopPaneUI.this.taskBar.getHeight());
/*     */       } 
/*     */       
/*     */       try {
/* 342 */         param1JInternalFrame.setSelected(true);
/* 343 */       } catch (PropertyVetoException propertyVetoException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void iconifyFrame(JInternalFrame param1JInternalFrame) {
/* 349 */       Container container = param1JInternalFrame.getParent();
/* 350 */       JDesktopPane jDesktopPane = param1JInternalFrame.getDesktopPane();
/* 351 */       boolean bool = param1JInternalFrame.isSelected();
/*     */       
/* 353 */       if (container == null) {
/*     */         return;
/*     */       }
/*     */       
/* 357 */       JInternalFrame.JDesktopIcon jDesktopIcon = param1JInternalFrame.getDesktopIcon();
/*     */       
/* 359 */       if (!param1JInternalFrame.isMaximum()) {
/* 360 */         param1JInternalFrame.setNormalBounds(param1JInternalFrame.getBounds());
/*     */       }
/* 362 */       container.remove(param1JInternalFrame);
/* 363 */       container.repaint(param1JInternalFrame.getX(), param1JInternalFrame.getY(), param1JInternalFrame.getWidth(), param1JInternalFrame.getHeight());
/*     */       try {
/* 365 */         param1JInternalFrame.setSelected(false);
/* 366 */       } catch (PropertyVetoException propertyVetoException) {}
/*     */ 
/*     */ 
/*     */       
/* 370 */       if (bool) {
/* 371 */         for (Component component : container.getComponents()) {
/* 372 */           if (component instanceof JInternalFrame) {
/*     */             try {
/* 374 */               ((JInternalFrame)component).setSelected(true);
/* 375 */             } catch (PropertyVetoException propertyVetoException) {}
/*     */             
/* 377 */             ((JInternalFrame)component).moveToFront();
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void deiconifyFrame(JInternalFrame param1JInternalFrame) {
/* 386 */       JInternalFrame.JDesktopIcon jDesktopIcon = param1JInternalFrame.getDesktopIcon();
/* 387 */       Container container = jDesktopIcon.getParent();
/* 388 */       if (container != null) {
/* 389 */         container = container.getParent();
/* 390 */         if (container != null) {
/* 391 */           container.add(param1JInternalFrame);
/* 392 */           if (param1JInternalFrame.isMaximum()) {
/* 393 */             int i = container.getWidth();
/* 394 */             int j = container.getHeight() - SynthDesktopPaneUI.this.taskBar.getHeight();
/* 395 */             if (param1JInternalFrame.getWidth() != i || param1JInternalFrame.getHeight() != j) {
/* 396 */               setBoundsForFrame(param1JInternalFrame, 0, 0, i, j);
/*     */             }
/*     */           } 
/* 399 */           if (param1JInternalFrame.isSelected()) {
/* 400 */             param1JInternalFrame.moveToFront();
/*     */           } else {
/*     */             try {
/* 403 */               param1JInternalFrame.setSelected(true);
/* 404 */             } catch (PropertyVetoException propertyVetoException) {}
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void removeIconFor(JInternalFrame param1JInternalFrame) {
/* 412 */       super.removeIconFor(param1JInternalFrame);
/* 413 */       SynthDesktopPaneUI.this.taskBar.validate();
/*     */     }
/*     */     
/*     */     public void setBoundsForFrame(JComponent param1JComponent, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 417 */       super.setBoundsForFrame(param1JComponent, param1Int1, param1Int2, param1Int3, param1Int4);
/* 418 */       if (SynthDesktopPaneUI.this.taskBar != null && param1Int2 >= SynthDesktopPaneUI.this.taskBar.getY()) {
/* 419 */         param1JComponent.setLocation(param1JComponent.getX(), SynthDesktopPaneUI.this.taskBar.getY() - (param1JComponent.getInsets()).top);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 429 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 433 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 437 */     return SynthLookAndFeel.getComponentState(paramJComponent);
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
/* 454 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 456 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 457 */     synthContext.getPainter().paintDesktopPaneBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 458 */         .getWidth(), paramJComponent.getHeight());
/* 459 */     paint(synthContext, paramGraphics);
/* 460 */     synthContext.dispose();
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
/* 474 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 476 */     paint(synthContext, paramGraphics);
/* 477 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 496 */     paramSynthContext.getPainter().paintDesktopPaneBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 504 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 505 */       updateStyle((JDesktopPane)paramPropertyChangeEvent.getSource());
/*     */     }
/* 507 */     if (paramPropertyChangeEvent.getPropertyName() == "ancestor" && this.taskBar != null)
/* 508 */       this.taskBar.adjustSize(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthDesktopPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */