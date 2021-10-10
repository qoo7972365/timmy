/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyVetoException;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.ToolTipManager;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicDesktopIconUI;
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
/*     */ public class SynthDesktopIconUI
/*     */   extends BasicDesktopIconUI
/*     */   implements SynthUI, PropertyChangeListener
/*     */ {
/*     */   private SynthStyle style;
/*  45 */   private Handler handler = new Handler();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  54 */     return new SynthDesktopIconUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installComponents() {
/*  62 */     if (UIManager.getBoolean("InternalFrame.useTaskBar")) {
/*  63 */       this.iconPane = new JToggleButton(this.frame.getTitle(), this.frame.getFrameIcon()) {
/*     */           public String getToolTipText() {
/*  65 */             return getText();
/*     */           }
/*     */           
/*     */           public JPopupMenu getComponentPopupMenu() {
/*  69 */             return SynthDesktopIconUI.this.frame.getComponentPopupMenu();
/*     */           }
/*     */         };
/*  72 */       ToolTipManager.sharedInstance().registerComponent(this.iconPane);
/*  73 */       this.iconPane.setFont(this.desktopIcon.getFont());
/*  74 */       this.iconPane.setBackground(this.desktopIcon.getBackground());
/*  75 */       this.iconPane.setForeground(this.desktopIcon.getForeground());
/*     */     } else {
/*  77 */       this.iconPane = new SynthInternalFrameTitlePane(this.frame);
/*  78 */       this.iconPane.setName("InternalFrame.northPane");
/*     */     } 
/*  80 */     this.desktopIcon.setLayout(new BorderLayout());
/*  81 */     this.desktopIcon.add(this.iconPane, "Center");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  89 */     super.installListeners();
/*  90 */     this.desktopIcon.addPropertyChangeListener(this);
/*     */     
/*  92 */     if (this.iconPane instanceof JToggleButton) {
/*  93 */       this.frame.addPropertyChangeListener(this);
/*  94 */       ((JToggleButton)this.iconPane).addActionListener(this.handler);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 103 */     if (this.iconPane instanceof JToggleButton) {
/* 104 */       ((JToggleButton)this.iconPane).removeActionListener(this.handler);
/* 105 */       this.frame.removePropertyChangeListener(this);
/*     */     } 
/* 107 */     this.desktopIcon.removePropertyChangeListener(this);
/* 108 */     super.uninstallListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/* 116 */     updateStyle(this.desktopIcon);
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/* 120 */     SynthContext synthContext = getContext(paramJComponent, 1);
/* 121 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 122 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 130 */     SynthContext synthContext = getContext(this.desktopIcon, 1);
/* 131 */     this.style.uninstallDefaults(synthContext);
/* 132 */     synthContext.dispose();
/* 133 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 141 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 145 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 149 */     return SynthLookAndFeel.getComponentState(paramJComponent);
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
/* 166 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 168 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 169 */     synthContext.getPainter().paintDesktopIconBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 170 */         .getWidth(), paramJComponent.getHeight());
/* 171 */     paint(synthContext, paramGraphics);
/* 172 */     synthContext.dispose();
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
/* 186 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 188 */     paint(synthContext, paramGraphics);
/* 189 */     synthContext.dispose();
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
/* 208 */     paramSynthContext.getPainter().paintDesktopIconBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 212 */     if (paramPropertyChangeEvent.getSource() instanceof JInternalFrame.JDesktopIcon) {
/* 213 */       if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 214 */         updateStyle((JInternalFrame.JDesktopIcon)paramPropertyChangeEvent.getSource());
/*     */       }
/* 216 */     } else if (paramPropertyChangeEvent.getSource() instanceof JInternalFrame) {
/* 217 */       JInternalFrame jInternalFrame = (JInternalFrame)paramPropertyChangeEvent.getSource();
/* 218 */       if (this.iconPane instanceof JToggleButton) {
/* 219 */         JToggleButton jToggleButton = (JToggleButton)this.iconPane;
/* 220 */         String str = paramPropertyChangeEvent.getPropertyName();
/* 221 */         if (str == "title") {
/* 222 */           jToggleButton.setText((String)paramPropertyChangeEvent.getNewValue());
/* 223 */         } else if (str == "frameIcon") {
/* 224 */           jToggleButton.setIcon((Icon)paramPropertyChangeEvent.getNewValue());
/* 225 */         } else if (str == "icon" || str == "selected") {
/*     */           
/* 227 */           jToggleButton.setSelected((!jInternalFrame.isIcon() && jInternalFrame.isSelected()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private final class Handler implements ActionListener {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 235 */       if (param1ActionEvent.getSource() instanceof JToggleButton) {
/*     */         
/* 237 */         JToggleButton jToggleButton = (JToggleButton)param1ActionEvent.getSource();
/*     */         try {
/* 239 */           boolean bool = jToggleButton.isSelected();
/* 240 */           if (!bool && !SynthDesktopIconUI.this.frame.isIconifiable()) {
/* 241 */             jToggleButton.setSelected(true);
/*     */           } else {
/* 243 */             SynthDesktopIconUI.this.frame.setIcon(!bool);
/* 244 */             if (bool) {
/* 245 */               SynthDesktopIconUI.this.frame.setSelected(true);
/*     */             }
/*     */           } 
/* 248 */         } catch (PropertyVetoException propertyVetoException) {}
/*     */       } 
/*     */     }
/*     */     
/*     */     private Handler() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthDesktopIconUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */