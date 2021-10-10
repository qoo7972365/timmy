/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicMenuItemUI;
/*     */ import sun.swing.MenuItemLayoutHelper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthMenuItemUI
/*     */   extends BasicMenuItemUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   private SynthStyle accStyle;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  58 */     return new SynthMenuItemUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  66 */     super.uninstallUI(paramJComponent);
/*     */     
/*  68 */     JComponent jComponent = MenuItemLayoutHelper.getMenuItemParent((JMenuItem)paramJComponent);
/*  69 */     if (jComponent != null) {
/*  70 */       jComponent.putClientProperty(SynthMenuItemLayoutHelper.MAX_ACC_OR_ARROW_WIDTH, (Object)null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  80 */     updateStyle(this.menuItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  88 */     super.installListeners();
/*  89 */     this.menuItem.addPropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   private void updateStyle(JMenuItem paramJMenuItem) {
/*  93 */     SynthContext synthContext1 = getContext(paramJMenuItem, 1);
/*  94 */     SynthStyle synthStyle = this.style;
/*     */     
/*  96 */     this.style = SynthLookAndFeel.updateStyle(synthContext1, this);
/*  97 */     if (synthStyle != this.style) {
/*  98 */       String str = getPropertyPrefix();
/*     */       
/* 100 */       Object object = this.style.get(synthContext1, str + ".textIconGap");
/* 101 */       if (object != null) {
/* 102 */         LookAndFeel.installProperty(paramJMenuItem, "iconTextGap", object);
/*     */       }
/* 104 */       this.defaultTextIconGap = paramJMenuItem.getIconTextGap();
/*     */       
/* 106 */       if (this.menuItem.getMargin() == null || this.menuItem
/* 107 */         .getMargin() instanceof javax.swing.plaf.UIResource) {
/* 108 */         Insets insets = (Insets)this.style.get(synthContext1, str + ".margin");
/*     */         
/* 110 */         if (insets == null)
/*     */         {
/* 112 */           insets = SynthLookAndFeel.EMPTY_UIRESOURCE_INSETS;
/*     */         }
/* 114 */         this.menuItem.setMargin(insets);
/*     */       } 
/* 116 */       this.acceleratorDelimiter = this.style.getString(synthContext1, str + ".acceleratorDelimiter", "+");
/*     */ 
/*     */       
/* 119 */       this.arrowIcon = this.style.getIcon(synthContext1, str + ".arrowIcon");
/*     */       
/* 121 */       this.checkIcon = this.style.getIcon(synthContext1, str + ".checkIcon");
/* 122 */       if (synthStyle != null) {
/* 123 */         uninstallKeyboardActions();
/* 124 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 127 */     synthContext1.dispose();
/*     */     
/* 129 */     SynthContext synthContext2 = getContext(paramJMenuItem, Region.MENU_ITEM_ACCELERATOR, 1);
/*     */ 
/*     */     
/* 132 */     this.accStyle = SynthLookAndFeel.updateStyle(synthContext2, this);
/* 133 */     synthContext2.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 141 */     SynthContext synthContext1 = getContext(this.menuItem, 1);
/* 142 */     this.style.uninstallDefaults(synthContext1);
/* 143 */     synthContext1.dispose();
/* 144 */     this.style = null;
/*     */     
/* 146 */     SynthContext synthContext2 = getContext(this.menuItem, Region.MENU_ITEM_ACCELERATOR, 1);
/*     */     
/* 148 */     this.accStyle.uninstallDefaults(synthContext2);
/* 149 */     synthContext2.dispose();
/* 150 */     this.accStyle = null;
/*     */     
/* 152 */     super.uninstallDefaults();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 160 */     super.uninstallListeners();
/* 161 */     this.menuItem.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 169 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 173 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   SynthContext getContext(JComponent paramJComponent, Region paramRegion) {
/* 177 */     return getContext(paramJComponent, paramRegion, getComponentState(paramJComponent, paramRegion));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion, int paramInt) {
/* 181 */     return SynthContext.getContext(paramJComponent, paramRegion, this.accStyle, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/*     */     int i;
/* 187 */     if (!paramJComponent.isEnabled()) {
/* 188 */       i = 8;
/*     */     }
/* 190 */     else if (this.menuItem.isArmed()) {
/* 191 */       i = 2;
/*     */     } else {
/*     */       
/* 194 */       i = SynthLookAndFeel.getComponentState(paramJComponent);
/*     */     } 
/* 196 */     if (this.menuItem.isSelected()) {
/* 197 */       i |= 0x200;
/*     */     }
/* 199 */     return i;
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent, Region paramRegion) {
/* 203 */     return getComponentState(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Dimension getPreferredMenuItemSize(JComponent paramJComponent, Icon paramIcon1, Icon paramIcon2, int paramInt) {
/* 214 */     SynthContext synthContext1 = getContext(paramJComponent);
/* 215 */     SynthContext synthContext2 = getContext(paramJComponent, Region.MENU_ITEM_ACCELERATOR);
/* 216 */     Dimension dimension = SynthGraphicsUtils.getPreferredMenuItemSize(synthContext1, synthContext2, paramJComponent, paramIcon1, paramIcon2, paramInt, this.acceleratorDelimiter, 
/*     */ 
/*     */         
/* 219 */         MenuItemLayoutHelper.useCheckAndArrow(this.menuItem), 
/* 220 */         getPropertyPrefix());
/* 221 */     synthContext1.dispose();
/* 222 */     synthContext2.dispose();
/* 223 */     return dimension;
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
/* 241 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 243 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 244 */     paintBackground(synthContext, paramGraphics, paramJComponent);
/* 245 */     paint(synthContext, paramGraphics);
/* 246 */     synthContext.dispose();
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
/* 260 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 262 */     paint(synthContext, paramGraphics);
/* 263 */     synthContext.dispose();
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
/* 274 */     SynthContext synthContext = getContext(this.menuItem, Region.MENU_ITEM_ACCELERATOR);
/*     */ 
/*     */ 
/*     */     
/* 278 */     String str = getPropertyPrefix();
/* 279 */     Icon icon1 = this.style.getIcon(paramSynthContext, str + ".checkIcon");
/* 280 */     Icon icon2 = this.style.getIcon(paramSynthContext, str + ".arrowIcon");
/* 281 */     SynthGraphicsUtils.paint(paramSynthContext, synthContext, paramGraphics, icon1, icon2, this.acceleratorDelimiter, this.defaultTextIconGap, 
/* 282 */         getPropertyPrefix());
/* 283 */     synthContext.dispose();
/*     */   }
/*     */   
/*     */   void paintBackground(SynthContext paramSynthContext, Graphics paramGraphics, JComponent paramJComponent) {
/* 287 */     SynthGraphicsUtils.paintBackground(paramSynthContext, paramGraphics, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 296 */     paramSynthContext.getPainter().paintMenuItemBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 304 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 305 */       updateStyle((JMenuItem)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */