/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ContainerEvent;
/*     */ import java.awt.event.ContainerListener;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicScrollPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthScrollPaneUI
/*     */   extends BasicScrollPaneUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   private boolean viewportViewHasFocus = false;
/*     */   private ViewportViewFocusHandler viewportViewFocusHandler;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  63 */     return new SynthScrollPaneUI();
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
/*  80 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/*  82 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/*  83 */     synthContext.getPainter().paintScrollPaneBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/*  84 */         .getWidth(), paramJComponent.getHeight());
/*  85 */     paint(synthContext, paramGraphics);
/*  86 */     synthContext.dispose();
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
/* 100 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 102 */     paint(synthContext, paramGraphics);
/* 103 */     synthContext.dispose();
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
/* 114 */     Border border = this.scrollpane.getViewportBorder();
/* 115 */     if (border != null) {
/* 116 */       Rectangle rectangle = this.scrollpane.getViewportBorderBounds();
/* 117 */       border.paintBorder(this.scrollpane, paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 127 */     paramSynthContext.getPainter().paintScrollPaneBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(JScrollPane paramJScrollPane) {
/* 135 */     updateStyle(paramJScrollPane);
/*     */   }
/*     */   
/*     */   private void updateStyle(JScrollPane paramJScrollPane) {
/* 139 */     SynthContext synthContext = getContext(paramJScrollPane, 1);
/* 140 */     SynthStyle synthStyle = this.style;
/*     */     
/* 142 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 143 */     if (this.style != synthStyle) {
/* 144 */       Border border = this.scrollpane.getViewportBorder();
/* 145 */       if (border == null || border instanceof UIResource) {
/* 146 */         this.scrollpane.setViewportBorder(new ViewportBorder(synthContext));
/*     */       }
/* 148 */       if (synthStyle != null) {
/* 149 */         uninstallKeyboardActions(paramJScrollPane);
/* 150 */         installKeyboardActions(paramJScrollPane);
/*     */       } 
/*     */     } 
/* 153 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners(JScrollPane paramJScrollPane) {
/* 161 */     super.installListeners(paramJScrollPane);
/* 162 */     paramJScrollPane.addPropertyChangeListener(this);
/* 163 */     if (UIManager.getBoolean("ScrollPane.useChildTextComponentFocus")) {
/* 164 */       this.viewportViewFocusHandler = new ViewportViewFocusHandler();
/* 165 */       paramJScrollPane.getViewport().addContainerListener(this.viewportViewFocusHandler);
/* 166 */       Component component = paramJScrollPane.getViewport().getView();
/* 167 */       if (component instanceof javax.swing.text.JTextComponent) {
/* 168 */         component.addFocusListener(this.viewportViewFocusHandler);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults(JScrollPane paramJScrollPane) {
/* 178 */     SynthContext synthContext = getContext(paramJScrollPane, 1);
/*     */     
/* 180 */     this.style.uninstallDefaults(synthContext);
/* 181 */     synthContext.dispose();
/*     */     
/* 183 */     if (this.scrollpane.getViewportBorder() instanceof UIResource) {
/* 184 */       this.scrollpane.setViewportBorder((Border)null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners(JComponent paramJComponent) {
/* 193 */     super.uninstallListeners(paramJComponent);
/* 194 */     paramJComponent.removePropertyChangeListener(this);
/* 195 */     if (this.viewportViewFocusHandler != null) {
/* 196 */       JViewport jViewport = ((JScrollPane)paramJComponent).getViewport();
/* 197 */       jViewport.removeContainerListener(this.viewportViewFocusHandler);
/* 198 */       if (jViewport.getView() != null) {
/* 199 */         jViewport.getView().removeFocusListener(this.viewportViewFocusHandler);
/*     */       }
/* 201 */       this.viewportViewFocusHandler = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 210 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 214 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 218 */     int i = SynthLookAndFeel.getComponentState(paramJComponent);
/* 219 */     if (this.viewportViewFocusHandler != null && this.viewportViewHasFocus) {
/* 220 */       i |= 0x100;
/*     */     }
/* 222 */     return i;
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 226 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 227 */       updateStyle(this.scrollpane); 
/*     */   }
/*     */   
/*     */   private class ViewportBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/*     */     private Insets insets;
/*     */     
/*     */     ViewportBorder(SynthContext param1SynthContext) {
/* 237 */       this.insets = (Insets)param1SynthContext.getStyle().get(param1SynthContext, "ScrollPane.viewportBorderInsets");
/*     */       
/* 239 */       if (this.insets == null) {
/* 240 */         this.insets = SynthLookAndFeel.EMPTY_UIRESOURCE_INSETS;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 247 */       JComponent jComponent = (JComponent)param1Component;
/* 248 */       SynthContext synthContext = SynthScrollPaneUI.this.getContext(jComponent);
/* 249 */       SynthStyle synthStyle = synthContext.getStyle();
/* 250 */       if (synthStyle == null) {
/* 251 */         assert false : "SynthBorder is being used outside after the  UI has been uninstalled";
/*     */         
/*     */         return;
/*     */       } 
/* 255 */       synthContext.getPainter().paintViewportBorder(synthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       
/* 257 */       synthContext.dispose();
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 262 */       if (param1Insets == null) {
/* 263 */         return new Insets(this.insets.top, this.insets.left, this.insets.bottom, this.insets.right);
/*     */       }
/*     */       
/* 266 */       param1Insets.top = this.insets.top;
/* 267 */       param1Insets.bottom = this.insets.bottom;
/* 268 */       param1Insets.left = this.insets.left;
/* 269 */       param1Insets.right = this.insets.left;
/* 270 */       return param1Insets;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isBorderOpaque() {
/* 275 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewportViewFocusHandler
/*     */     implements ContainerListener, FocusListener
/*     */   {
/*     */     private ViewportViewFocusHandler() {}
/*     */     
/*     */     public void componentAdded(ContainerEvent param1ContainerEvent) {
/* 285 */       if (param1ContainerEvent.getChild() instanceof javax.swing.text.JTextComponent) {
/* 286 */         param1ContainerEvent.getChild().addFocusListener(this);
/* 287 */         SynthScrollPaneUI.this.viewportViewHasFocus = param1ContainerEvent.getChild().isFocusOwner();
/* 288 */         SynthScrollPaneUI.this.scrollpane.repaint();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void componentRemoved(ContainerEvent param1ContainerEvent) {
/* 293 */       if (param1ContainerEvent.getChild() instanceof javax.swing.text.JTextComponent) {
/* 294 */         param1ContainerEvent.getChild().removeFocusListener(this);
/*     */       }
/*     */     }
/*     */     
/*     */     public void focusGained(FocusEvent param1FocusEvent) {
/* 299 */       SynthScrollPaneUI.this.viewportViewHasFocus = true;
/* 300 */       SynthScrollPaneUI.this.scrollpane.repaint();
/*     */     }
/*     */     
/*     */     public void focusLost(FocusEvent param1FocusEvent) {
/* 304 */       SynthScrollPaneUI.this.viewportViewHasFocus = false;
/* 305 */       SynthScrollPaneUI.this.scrollpane.repaint();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthScrollPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */