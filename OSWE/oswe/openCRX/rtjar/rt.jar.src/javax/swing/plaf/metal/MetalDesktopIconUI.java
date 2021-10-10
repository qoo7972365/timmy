/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.MatteBorder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalDesktopIconUI
/*     */   extends BasicDesktopIconUI
/*     */ {
/*     */   JButton button;
/*     */   JLabel label;
/*     */   TitleListener titleListener;
/*     */   private int width;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  52 */     return new MetalDesktopIconUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  59 */     super.installDefaults();
/*  60 */     LookAndFeel.installColorsAndFont(this.desktopIcon, "DesktopIcon.background", "DesktopIcon.foreground", "DesktopIcon.font");
/*  61 */     this.width = UIManager.getInt("DesktopIcon.width");
/*     */   }
/*     */   
/*     */   protected void installComponents() {
/*  65 */     this.frame = this.desktopIcon.getInternalFrame();
/*  66 */     Icon icon = this.frame.getFrameIcon();
/*  67 */     String str = this.frame.getTitle();
/*     */     
/*  69 */     this.button = new JButton(str, icon);
/*  70 */     this.button.addActionListener(new ActionListener()
/*     */         {
/*  72 */           public void actionPerformed(ActionEvent param1ActionEvent) { MetalDesktopIconUI.this.deiconize(); } });
/*  73 */     this.button.setFont(this.desktopIcon.getFont());
/*  74 */     this.button.setBackground(this.desktopIcon.getBackground());
/*  75 */     this.button.setForeground(this.desktopIcon.getForeground());
/*     */     
/*  77 */     int i = (this.button.getPreferredSize()).height;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     MetalBumps metalBumps = new MetalBumps(i / 3, i, MetalLookAndFeel.getControlHighlight(), MetalLookAndFeel.getControlDarkShadow(), MetalLookAndFeel.getControl());
/*  83 */     this.label = new JLabel(metalBumps);
/*     */     
/*  85 */     this.label.setBorder(new MatteBorder(0, 2, 0, 1, this.desktopIcon.getBackground()));
/*  86 */     this.desktopIcon.setLayout(new BorderLayout(2, 0));
/*  87 */     this.desktopIcon.add(this.button, "Center");
/*  88 */     this.desktopIcon.add(this.label, "West");
/*     */   }
/*     */   
/*     */   protected void uninstallComponents() {
/*  92 */     this.desktopIcon.setLayout((LayoutManager)null);
/*  93 */     this.desktopIcon.remove(this.label);
/*  94 */     this.desktopIcon.remove(this.button);
/*  95 */     this.button = null;
/*  96 */     this.frame = null;
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/* 100 */     super.installListeners();
/* 101 */     this.desktopIcon.getInternalFrame().addPropertyChangeListener(this.titleListener = new TitleListener());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 106 */     this.desktopIcon.getInternalFrame().removePropertyChangeListener(this.titleListener);
/*     */     
/* 108 */     this.titleListener = null;
/* 109 */     super.uninstallListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 116 */     return getMinimumSize(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 123 */     return new Dimension(this.width, 
/* 124 */         (this.desktopIcon.getLayout().minimumLayoutSize(this.desktopIcon)).height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 130 */     return getMinimumSize(paramJComponent);
/*     */   }
/*     */   
/*     */   class TitleListener implements PropertyChangeListener {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 135 */       if (param1PropertyChangeEvent.getPropertyName().equals("title")) {
/* 136 */         MetalDesktopIconUI.this.button.setText((String)param1PropertyChangeEvent.getNewValue());
/*     */       }
/*     */       
/* 139 */       if (param1PropertyChangeEvent.getPropertyName().equals("frameIcon"))
/* 140 */         MetalDesktopIconUI.this.button.setIcon((Icon)param1PropertyChangeEvent.getNewValue()); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalDesktopIconUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */