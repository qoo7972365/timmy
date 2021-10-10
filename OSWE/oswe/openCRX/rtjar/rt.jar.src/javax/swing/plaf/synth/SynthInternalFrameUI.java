/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDesktopPane;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicInternalFrameUI;
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
/*     */ public class SynthInternalFrameUI
/*     */   extends BasicInternalFrameUI
/*     */   implements SynthUI, PropertyChangeListener
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  56 */     return new SynthInternalFrameUI((JInternalFrame)paramJComponent);
/*     */   }
/*     */   
/*     */   protected SynthInternalFrameUI(JInternalFrame paramJInternalFrame) {
/*  60 */     super(paramJInternalFrame);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installDefaults() {
/*  68 */     this.frame.setLayout(this.internalFrameLayout = createLayoutManager());
/*  69 */     updateStyle(this.frame);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  77 */     super.installListeners();
/*  78 */     this.frame.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallComponents() {
/*  86 */     if (this.frame.getComponentPopupMenu() instanceof javax.swing.plaf.UIResource) {
/*  87 */       this.frame.setComponentPopupMenu((JPopupMenu)null);
/*     */     }
/*  89 */     super.uninstallComponents();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/*  97 */     this.frame.removePropertyChangeListener(this);
/*  98 */     super.uninstallListeners();
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/* 102 */     SynthContext synthContext = getContext(paramJComponent, 1);
/* 103 */     SynthStyle synthStyle = this.style;
/*     */     
/* 105 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 106 */     if (this.style != synthStyle) {
/* 107 */       Icon icon = this.frame.getFrameIcon();
/* 108 */       if (icon == null || icon instanceof javax.swing.plaf.UIResource) {
/* 109 */         this.frame.setFrameIcon(synthContext.getStyle().getIcon(synthContext, "InternalFrame.icon"));
/*     */       }
/*     */       
/* 112 */       if (synthStyle != null) {
/* 113 */         uninstallKeyboardActions();
/* 114 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 117 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 125 */     SynthContext synthContext = getContext(this.frame, 1);
/* 126 */     this.style.uninstallDefaults(synthContext);
/* 127 */     synthContext.dispose();
/* 128 */     this.style = null;
/* 129 */     if (this.frame.getLayout() == this.internalFrameLayout) {
/* 130 */       this.frame.setLayout((LayoutManager)null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 140 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 144 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 148 */     return SynthLookAndFeel.getComponentState(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JComponent createNorthPane(JInternalFrame paramJInternalFrame) {
/* 156 */     this.titlePane = new SynthInternalFrameTitlePane(paramJInternalFrame);
/* 157 */     this.titlePane.setName("InternalFrame.northPane");
/* 158 */     return this.titlePane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ComponentListener createComponentListener() {
/* 166 */     if (UIManager.getBoolean("InternalFrame.useTaskBar")) {
/* 167 */       return new BasicInternalFrameUI.ComponentHandler() {
/*     */           public void componentResized(ComponentEvent param1ComponentEvent) {
/* 169 */             if (SynthInternalFrameUI.this.frame != null && SynthInternalFrameUI.this.frame.isMaximum()) {
/* 170 */               JDesktopPane jDesktopPane = (JDesktopPane)param1ComponentEvent.getSource();
/* 171 */               for (Component component : jDesktopPane.getComponents()) {
/* 172 */                 if (component instanceof SynthDesktopPaneUI.TaskBar) {
/* 173 */                   SynthInternalFrameUI.this.frame.setBounds(0, 0, jDesktopPane
/* 174 */                       .getWidth(), jDesktopPane
/* 175 */                       .getHeight() - component.getHeight());
/* 176 */                   SynthInternalFrameUI.this.frame.revalidate();
/*     */ 
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 184 */             JInternalFrame jInternalFrame = SynthInternalFrameUI.this.frame;
/* 185 */             SynthInternalFrameUI.this.frame = null;
/* 186 */             super.componentResized(param1ComponentEvent);
/* 187 */             SynthInternalFrameUI.this.frame = jInternalFrame;
/*     */           }
/*     */         };
/*     */     }
/* 191 */     return super.createComponentListener();
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 209 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 211 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 212 */     synthContext.getPainter().paintInternalFrameBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 213 */         .getWidth(), paramJComponent.getHeight());
/* 214 */     paint(synthContext, paramGraphics);
/* 215 */     synthContext.dispose();
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
/* 229 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 231 */     paint(synthContext, paramGraphics);
/* 232 */     synthContext.dispose();
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
/* 251 */     paramSynthContext.getPainter().paintInternalFrameBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 260 */     SynthStyle synthStyle = this.style;
/* 261 */     JInternalFrame jInternalFrame = (JInternalFrame)paramPropertyChangeEvent.getSource();
/* 262 */     String str = paramPropertyChangeEvent.getPropertyName();
/*     */     
/* 264 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 265 */       updateStyle(jInternalFrame);
/*     */     }
/*     */     
/* 268 */     if (this.style == synthStyle && (str == "maximum" || str == "selected")) {
/*     */ 
/*     */ 
/*     */       
/* 272 */       SynthContext synthContext = getContext(jInternalFrame, 1);
/* 273 */       this.style.uninstallDefaults(synthContext);
/* 274 */       this.style.installDefaults(synthContext, this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthInternalFrameUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */