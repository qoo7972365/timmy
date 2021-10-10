/*     */ package javax.swing.plaf.multi;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
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
/*     */ public class MultiLookAndFeel
/*     */   extends LookAndFeel
/*     */ {
/*     */   public String getName() {
/*  72 */     return "Multiplexing Look and Feel";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getID() {
/*  82 */     return "Multiplex";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  91 */     return "Allows multiple UI instances per component instance";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNativeLookAndFeel() {
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportedLookAndFeel() {
/* 111 */     return true;
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
/*     */   public UIDefaults getDefaults() {
/* 128 */     String str = "javax.swing.plaf.multi.Multi";
/* 129 */     Object[] arrayOfObject = { "ButtonUI", str + "ButtonUI", "CheckBoxMenuItemUI", str + "MenuItemUI", "CheckBoxUI", str + "ButtonUI", "ColorChooserUI", str + "ColorChooserUI", "ComboBoxUI", str + "ComboBoxUI", "DesktopIconUI", str + "DesktopIconUI", "DesktopPaneUI", str + "DesktopPaneUI", "EditorPaneUI", str + "TextUI", "FileChooserUI", str + "FileChooserUI", "FormattedTextFieldUI", str + "TextUI", "InternalFrameUI", str + "InternalFrameUI", "LabelUI", str + "LabelUI", "ListUI", str + "ListUI", "MenuBarUI", str + "MenuBarUI", "MenuItemUI", str + "MenuItemUI", "MenuUI", str + "MenuItemUI", "OptionPaneUI", str + "OptionPaneUI", "PanelUI", str + "PanelUI", "PasswordFieldUI", str + "TextUI", "PopupMenuSeparatorUI", str + "SeparatorUI", "PopupMenuUI", str + "PopupMenuUI", "ProgressBarUI", str + "ProgressBarUI", "RadioButtonMenuItemUI", str + "MenuItemUI", "RadioButtonUI", str + "ButtonUI", "RootPaneUI", str + "RootPaneUI", "ScrollBarUI", str + "ScrollBarUI", "ScrollPaneUI", str + "ScrollPaneUI", "SeparatorUI", str + "SeparatorUI", "SliderUI", str + "SliderUI", "SpinnerUI", str + "SpinnerUI", "SplitPaneUI", str + "SplitPaneUI", "TabbedPaneUI", str + "TabbedPaneUI", "TableHeaderUI", str + "TableHeaderUI", "TableUI", str + "TableUI", "TextAreaUI", str + "TextUI", "TextFieldUI", str + "TextUI", "TextPaneUI", str + "TextUI", "ToggleButtonUI", str + "ButtonUI", "ToolBarSeparatorUI", str + "SeparatorUI", "ToolBarUI", str + "ToolBarUI", "ToolTipUI", str + "ToolTipUI", "TreeUI", str + "TreeUI", "ViewportUI", str + "ViewportUI" };
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
/* 175 */     MultiUIDefaults multiUIDefaults = new MultiUIDefaults(arrayOfObject.length / 2, 0.75F);
/* 176 */     multiUIDefaults.putDefaults(arrayOfObject);
/* 177 */     return multiUIDefaults;
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
/*     */   public static ComponentUI createUIs(ComponentUI paramComponentUI, Vector<ComponentUI> paramVector, JComponent paramJComponent) {
/* 229 */     ComponentUI componentUI = UIManager.getDefaults().getUI(paramJComponent);
/* 230 */     if (componentUI != null) {
/* 231 */       paramVector.addElement(componentUI);
/*     */       
/* 233 */       LookAndFeel[] arrayOfLookAndFeel = UIManager.getAuxiliaryLookAndFeels();
/* 234 */       if (arrayOfLookAndFeel != null) {
/* 235 */         for (byte b = 0; b < arrayOfLookAndFeel.length; b++) {
/* 236 */           componentUI = arrayOfLookAndFeel[b].getDefaults().getUI(paramJComponent);
/* 237 */           if (componentUI != null) {
/* 238 */             paramVector.addElement(componentUI);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } else {
/* 243 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     if (paramVector.size() == 1) {
/* 250 */       return paramVector.elementAt(0);
/*     */     }
/* 252 */     return paramComponentUI;
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
/*     */ 
/*     */   
/*     */   protected static ComponentUI[] uisToArray(Vector<ComponentUI> paramVector) {
/* 272 */     if (paramVector == null) {
/* 273 */       return new ComponentUI[0];
/*     */     }
/* 275 */     int i = paramVector.size();
/* 276 */     if (i > 0) {
/* 277 */       ComponentUI[] arrayOfComponentUI = new ComponentUI[i];
/* 278 */       for (byte b = 0; b < i; b++) {
/* 279 */         arrayOfComponentUI[b] = paramVector.elementAt(b);
/*     */       }
/* 281 */       return arrayOfComponentUI;
/*     */     } 
/* 283 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/multi/MultiLookAndFeel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */