/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicMenuUI;
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
/*     */ public class SynthMenuUI
/*     */   extends BasicMenuUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   private SynthStyle accStyle;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  55 */     return new SynthMenuUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  63 */     updateStyle(this.menuItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  71 */     super.installListeners();
/*  72 */     this.menuItem.addPropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   private void updateStyle(JMenuItem paramJMenuItem) {
/*  76 */     SynthStyle synthStyle = this.style;
/*  77 */     SynthContext synthContext1 = getContext(paramJMenuItem, 1);
/*     */     
/*  79 */     this.style = SynthLookAndFeel.updateStyle(synthContext1, this);
/*  80 */     if (synthStyle != this.style) {
/*  81 */       String str = getPropertyPrefix();
/*  82 */       this.defaultTextIconGap = this.style.getInt(synthContext1, str + ".textIconGap", 4);
/*     */       
/*  84 */       if (this.menuItem.getMargin() == null || this.menuItem
/*  85 */         .getMargin() instanceof javax.swing.plaf.UIResource) {
/*  86 */         Insets insets = (Insets)this.style.get(synthContext1, str + ".margin");
/*     */         
/*  88 */         if (insets == null)
/*     */         {
/*  90 */           insets = SynthLookAndFeel.EMPTY_UIRESOURCE_INSETS;
/*     */         }
/*  92 */         this.menuItem.setMargin(insets);
/*     */       } 
/*  94 */       this.acceleratorDelimiter = this.style.getString(synthContext1, str + ".acceleratorDelimiter", "+");
/*     */ 
/*     */       
/*  97 */       if (MenuItemLayoutHelper.useCheckAndArrow(this.menuItem)) {
/*  98 */         this.checkIcon = this.style.getIcon(synthContext1, str + ".checkIcon");
/*  99 */         this.arrowIcon = this.style.getIcon(synthContext1, str + ".arrowIcon");
/*     */       } else {
/*     */         
/* 102 */         this.checkIcon = null;
/* 103 */         this.arrowIcon = null;
/*     */       } 
/*     */       
/* 106 */       ((JMenu)this.menuItem).setDelay(this.style.getInt(synthContext1, str + ".delay", 200));
/*     */       
/* 108 */       if (synthStyle != null) {
/* 109 */         uninstallKeyboardActions();
/* 110 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 113 */     synthContext1.dispose();
/*     */     
/* 115 */     SynthContext synthContext2 = getContext(paramJMenuItem, Region.MENU_ITEM_ACCELERATOR, 1);
/*     */ 
/*     */     
/* 118 */     this.accStyle = SynthLookAndFeel.updateStyle(synthContext2, this);
/* 119 */     synthContext2.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 127 */     super.uninstallUI(paramJComponent);
/*     */     
/* 129 */     JComponent jComponent = MenuItemLayoutHelper.getMenuItemParent((JMenuItem)paramJComponent);
/* 130 */     if (jComponent != null) {
/* 131 */       jComponent.putClientProperty(SynthMenuItemLayoutHelper.MAX_ACC_OR_ARROW_WIDTH, (Object)null);
/*     */     }
/*     */   }
/*     */ 
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
/* 188 */       return 8;
/*     */     }
/* 190 */     if (this.menuItem.isArmed()) {
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 240 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 242 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 243 */     synthContext.getPainter().paintMenuBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 244 */         .getWidth(), paramJComponent.getHeight());
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
/* 277 */     String str = getPropertyPrefix();
/* 278 */     Icon icon1 = this.style.getIcon(paramSynthContext, str + ".checkIcon");
/* 279 */     Icon icon2 = this.style.getIcon(paramSynthContext, str + ".arrowIcon");
/* 280 */     SynthGraphicsUtils.paint(paramSynthContext, synthContext, paramGraphics, icon1, icon2, this.acceleratorDelimiter, this.defaultTextIconGap, 
/* 281 */         getPropertyPrefix());
/* 282 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 291 */     paramSynthContext.getPainter().paintMenuBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 299 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent) || (paramPropertyChangeEvent
/* 300 */       .getPropertyName().equals("ancestor") && UIManager.getBoolean("Menu.useMenuBarForTopLevelMenus")))
/* 301 */       updateStyle((JMenu)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthMenuUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */