/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.text.JTextComponent;
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
/*     */ class SynthBorder
/*     */   extends AbstractBorder
/*     */   implements UIResource
/*     */ {
/*     */   private SynthUI ui;
/*     */   private Insets insets;
/*     */   
/*     */   SynthBorder(SynthUI paramSynthUI, Insets paramInsets) {
/*  44 */     this.ui = paramSynthUI;
/*  45 */     this.insets = paramInsets;
/*     */   }
/*     */   
/*     */   SynthBorder(SynthUI paramSynthUI) {
/*  49 */     this(paramSynthUI, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  54 */     JComponent jComponent = (JComponent)paramComponent;
/*  55 */     SynthContext synthContext = this.ui.getContext(jComponent);
/*  56 */     SynthStyle synthStyle = synthContext.getStyle();
/*  57 */     if (synthStyle == null) {
/*  58 */       assert false : "SynthBorder is being used outside after the UI has been uninstalled";
/*     */       
/*     */       return;
/*     */     } 
/*  62 */     this.ui.paintBorder(synthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*  63 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets getBorderInsets(Component paramComponent, Insets paramInsets) {
/*  73 */     if (this.insets != null) {
/*  74 */       if (paramInsets == null) {
/*  75 */         paramInsets = new Insets(this.insets.top, this.insets.left, this.insets.bottom, this.insets.right);
/*     */       }
/*     */       else {
/*     */         
/*  79 */         paramInsets.top = this.insets.top;
/*  80 */         paramInsets.bottom = this.insets.bottom;
/*  81 */         paramInsets.left = this.insets.left;
/*  82 */         paramInsets.right = this.insets.right;
/*     */       }
/*     */     
/*  85 */     } else if (paramInsets == null) {
/*  86 */       paramInsets = new Insets(0, 0, 0, 0);
/*     */     } else {
/*     */       
/*  89 */       paramInsets.top = paramInsets.bottom = paramInsets.left = paramInsets.right = 0;
/*     */     } 
/*  91 */     if (paramComponent instanceof JComponent) {
/*  92 */       Region region = Region.getRegion((JComponent)paramComponent);
/*  93 */       Insets insets = null;
/*  94 */       if ((region == Region.ARROW_BUTTON || region == Region.BUTTON || region == Region.CHECK_BOX || region == Region.CHECK_BOX_MENU_ITEM || region == Region.MENU || region == Region.MENU_ITEM || region == Region.RADIO_BUTTON || region == Region.RADIO_BUTTON_MENU_ITEM || region == Region.TOGGLE_BUTTON) && paramComponent instanceof AbstractButton) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 102 */         insets = ((AbstractButton)paramComponent).getMargin();
/*     */       }
/* 104 */       else if ((region == Region.EDITOR_PANE || region == Region.FORMATTED_TEXT_FIELD || region == Region.PASSWORD_FIELD || region == Region.TEXT_AREA || region == Region.TEXT_FIELD || region == Region.TEXT_PANE) && paramComponent instanceof JTextComponent) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 111 */         insets = ((JTextComponent)paramComponent).getMargin();
/*     */       }
/* 113 */       else if (region == Region.TOOL_BAR && paramComponent instanceof JToolBar) {
/* 114 */         insets = ((JToolBar)paramComponent).getMargin();
/*     */       }
/* 116 */       else if (region == Region.MENU_BAR && paramComponent instanceof JMenuBar) {
/* 117 */         insets = ((JMenuBar)paramComponent).getMargin();
/*     */       } 
/* 119 */       if (insets != null) {
/* 120 */         paramInsets.top += insets.top;
/* 121 */         paramInsets.bottom += insets.bottom;
/* 122 */         paramInsets.left += insets.left;
/* 123 */         paramInsets.right += insets.right;
/*     */       } 
/*     */     } 
/* 126 */     return paramInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBorderOpaque() {
/* 134 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */