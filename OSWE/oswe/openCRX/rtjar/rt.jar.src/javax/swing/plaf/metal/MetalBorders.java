/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Window;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.SwingConstants;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.CompoundBorder;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.border.LineBorder;
/*     */ import javax.swing.border.MatteBorder;
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicBorders;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import sun.swing.StringUIClientPropertyKey;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalBorders
/*     */ {
/*     */   private static Border buttonBorder;
/*     */   private static Border textBorder;
/*  57 */   static Object NO_BUTTON_ROLLOVER = new StringUIClientPropertyKey("NoButtonRollover");
/*     */   private static Border textFieldBorder;
/*     */   private static Border toggleButtonBorder;
/*     */   
/*     */   public static class Flush3DBorder
/*     */     extends AbstractBorder implements UIResource {
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  64 */       if (param1Component.isEnabled()) {
/*  65 */         MetalUtils.drawFlush3DBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       } else {
/*  67 */         MetalUtils.drawDisabledBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/*  72 */       param1Insets.set(2, 2, 2, 2);
/*  73 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ButtonBorder
/*     */     extends AbstractBorder implements UIResource {
/*  79 */     protected static Insets borderInsets = new Insets(3, 3, 3, 3);
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  82 */       if (!(param1Component instanceof AbstractButton)) {
/*     */         return;
/*     */       }
/*  85 */       if (MetalLookAndFeel.usingOcean()) {
/*  86 */         paintOceanBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */         return;
/*     */       } 
/*  89 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/*  90 */       ButtonModel buttonModel = abstractButton.getModel();
/*     */       
/*  92 */       if (buttonModel.isEnabled()) {
/*  93 */         boolean bool1 = (buttonModel.isPressed() && buttonModel.isArmed()) ? true : false;
/*  94 */         boolean bool2 = (abstractButton instanceof JButton && ((JButton)abstractButton).isDefaultButton()) ? true : false;
/*     */         
/*  96 */         if (bool1 && bool2) {
/*  97 */           MetalUtils.drawDefaultButtonPressedBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*  98 */         } else if (bool1) {
/*  99 */           MetalUtils.drawPressed3DBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/* 100 */         } else if (bool2) {
/* 101 */           MetalUtils.drawDefaultButtonBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, false);
/*     */         } else {
/* 103 */           MetalUtils.drawButtonBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, false);
/*     */         } 
/*     */       } else {
/* 106 */         MetalUtils.drawDisabledBorder(param1Graphics, param1Int1, param1Int2, param1Int3 - 1, param1Int4 - 1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void paintOceanBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 112 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 113 */       ButtonModel buttonModel = ((AbstractButton)param1Component).getModel();
/*     */       
/* 115 */       param1Graphics.translate(param1Int1, param1Int2);
/* 116 */       if (MetalUtils.isToolBarButton(abstractButton)) {
/* 117 */         if (buttonModel.isEnabled()) {
/* 118 */           if (buttonModel.isPressed()) {
/* 119 */             param1Graphics.setColor(MetalLookAndFeel.getWhite());
/* 120 */             param1Graphics.fillRect(1, param1Int4 - 1, param1Int3 - 1, 1);
/* 121 */             param1Graphics.fillRect(param1Int3 - 1, 1, 1, param1Int4 - 1);
/* 122 */             param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 123 */             param1Graphics.drawRect(0, 0, param1Int3 - 2, param1Int4 - 2);
/* 124 */             param1Graphics.fillRect(1, 1, param1Int3 - 3, 1);
/*     */           }
/* 126 */           else if (buttonModel.isSelected() || buttonModel.isRollover()) {
/* 127 */             param1Graphics.setColor(MetalLookAndFeel.getWhite());
/* 128 */             param1Graphics.fillRect(1, param1Int4 - 1, param1Int3 - 1, 1);
/* 129 */             param1Graphics.fillRect(param1Int3 - 1, 1, 1, param1Int4 - 1);
/* 130 */             param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 131 */             param1Graphics.drawRect(0, 0, param1Int3 - 2, param1Int4 - 2);
/*     */           } else {
/*     */             
/* 134 */             param1Graphics.setColor(MetalLookAndFeel.getWhite());
/* 135 */             param1Graphics.drawRect(1, 1, param1Int3 - 2, param1Int4 - 2);
/* 136 */             param1Graphics.setColor(UIManager.getColor("Button.toolBarBorderBackground"));
/*     */             
/* 138 */             param1Graphics.drawRect(0, 0, param1Int3 - 2, param1Int4 - 2);
/*     */           } 
/*     */         } else {
/*     */           
/* 142 */           param1Graphics.setColor(UIManager.getColor("Button.disabledToolBarBorderBackground"));
/*     */           
/* 144 */           param1Graphics.drawRect(0, 0, param1Int3 - 2, param1Int4 - 2);
/*     */         }
/*     */       
/* 147 */       } else if (buttonModel.isEnabled()) {
/* 148 */         boolean bool1 = buttonModel.isPressed();
/* 149 */         boolean bool2 = buttonModel.isArmed();
/*     */         
/* 151 */         if (param1Component instanceof JButton && ((JButton)param1Component).isDefaultButton()) {
/* 152 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 153 */           param1Graphics.drawRect(0, 0, param1Int3 - 1, param1Int4 - 1);
/* 154 */           param1Graphics.drawRect(1, 1, param1Int3 - 3, param1Int4 - 3);
/*     */         }
/* 156 */         else if (bool1) {
/* 157 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 158 */           param1Graphics.fillRect(0, 0, param1Int3, 2);
/* 159 */           param1Graphics.fillRect(0, 2, 2, param1Int4 - 2);
/* 160 */           param1Graphics.fillRect(param1Int3 - 1, 1, 1, param1Int4 - 1);
/* 161 */           param1Graphics.fillRect(1, param1Int4 - 1, param1Int3 - 2, 1);
/*     */         }
/* 163 */         else if (buttonModel.isRollover() && abstractButton.getClientProperty(MetalBorders.NO_BUTTON_ROLLOVER) == null) {
/*     */           
/* 165 */           param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 166 */           param1Graphics.drawRect(0, 0, param1Int3 - 1, param1Int4 - 1);
/* 167 */           param1Graphics.drawRect(2, 2, param1Int3 - 5, param1Int4 - 5);
/* 168 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 169 */           param1Graphics.drawRect(1, 1, param1Int3 - 3, param1Int4 - 3);
/*     */         } else {
/*     */           
/* 172 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 173 */           param1Graphics.drawRect(0, 0, param1Int3 - 1, param1Int4 - 1);
/*     */         } 
/*     */       } else {
/*     */         
/* 177 */         param1Graphics.setColor(MetalLookAndFeel.getInactiveControlTextColor());
/* 178 */         param1Graphics.drawRect(0, 0, param1Int3 - 1, param1Int4 - 1);
/* 179 */         if (param1Component instanceof JButton && ((JButton)param1Component).isDefaultButton()) {
/* 180 */           param1Graphics.drawRect(1, 1, param1Int3 - 3, param1Int4 - 3);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 186 */       param1Insets.set(3, 3, 3, 3);
/* 187 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class InternalFrameBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/*     */     private static final int corner = 14;
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*     */       ColorUIResource colorUIResource1, colorUIResource2, colorUIResource3;
/* 201 */       if (param1Component instanceof JInternalFrame && ((JInternalFrame)param1Component).isSelected()) {
/* 202 */         colorUIResource1 = MetalLookAndFeel.getPrimaryControlDarkShadow();
/* 203 */         colorUIResource2 = MetalLookAndFeel.getPrimaryControlShadow();
/* 204 */         colorUIResource3 = MetalLookAndFeel.getPrimaryControlInfo();
/*     */       } else {
/* 206 */         colorUIResource1 = MetalLookAndFeel.getControlDarkShadow();
/* 207 */         colorUIResource2 = MetalLookAndFeel.getControlShadow();
/* 208 */         colorUIResource3 = MetalLookAndFeel.getControlInfo();
/*     */       } 
/*     */       
/* 211 */       param1Graphics.setColor(colorUIResource1);
/*     */       
/* 213 */       param1Graphics.drawLine(1, 0, param1Int3 - 2, 0);
/* 214 */       param1Graphics.drawLine(0, 1, 0, param1Int4 - 2);
/* 215 */       param1Graphics.drawLine(param1Int3 - 1, 1, param1Int3 - 1, param1Int4 - 2);
/* 216 */       param1Graphics.drawLine(1, param1Int4 - 1, param1Int3 - 2, param1Int4 - 1);
/*     */ 
/*     */       
/* 219 */       for (byte b = 1; b < 5; b++) {
/* 220 */         param1Graphics.drawRect(param1Int1 + b, param1Int2 + b, param1Int3 - b * 2 - 1, param1Int4 - b * 2 - 1);
/*     */       }
/*     */       
/* 223 */       if (param1Component instanceof JInternalFrame && ((JInternalFrame)param1Component)
/* 224 */         .isResizable()) {
/* 225 */         param1Graphics.setColor(colorUIResource2);
/*     */         
/* 227 */         param1Graphics.drawLine(15, 3, param1Int3 - 14, 3);
/* 228 */         param1Graphics.drawLine(3, 15, 3, param1Int4 - 14);
/* 229 */         param1Graphics.drawLine(param1Int3 - 2, 15, param1Int3 - 2, param1Int4 - 14);
/* 230 */         param1Graphics.drawLine(15, param1Int4 - 2, param1Int3 - 14, param1Int4 - 2);
/*     */         
/* 232 */         param1Graphics.setColor(colorUIResource3);
/*     */         
/* 234 */         param1Graphics.drawLine(14, 2, param1Int3 - 14 - 1, 2);
/* 235 */         param1Graphics.drawLine(2, 14, 2, param1Int4 - 14 - 1);
/* 236 */         param1Graphics.drawLine(param1Int3 - 3, 14, param1Int3 - 3, param1Int4 - 14 - 1);
/* 237 */         param1Graphics.drawLine(14, param1Int4 - 3, param1Int3 - 14 - 1, param1Int4 - 3);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 243 */       param1Insets.set(5, 5, 5, 5);
/* 244 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class FrameBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/*     */     private static final int corner = 14;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*     */       ColorUIResource colorUIResource1, colorUIResource2, colorUIResource3;
/* 262 */       Window window = SwingUtilities.getWindowAncestor(param1Component);
/* 263 */       if (window != null && window.isActive()) {
/* 264 */         colorUIResource1 = MetalLookAndFeel.getPrimaryControlDarkShadow();
/* 265 */         colorUIResource2 = MetalLookAndFeel.getPrimaryControlShadow();
/* 266 */         colorUIResource3 = MetalLookAndFeel.getPrimaryControlInfo();
/*     */       } else {
/* 268 */         colorUIResource1 = MetalLookAndFeel.getControlDarkShadow();
/* 269 */         colorUIResource2 = MetalLookAndFeel.getControlShadow();
/* 270 */         colorUIResource3 = MetalLookAndFeel.getControlInfo();
/*     */       } 
/*     */       
/* 273 */       param1Graphics.setColor(colorUIResource1);
/*     */       
/* 275 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 0, param1Int1 + param1Int3 - 2, param1Int2 + 0);
/* 276 */       param1Graphics.drawLine(param1Int1 + 0, param1Int2 + 1, param1Int1 + 0, param1Int2 + param1Int4 - 2);
/* 277 */       param1Graphics.drawLine(param1Int1 + param1Int3 - 1, param1Int2 + 1, param1Int1 + param1Int3 - 1, param1Int2 + param1Int4 - 2);
/* 278 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + param1Int4 - 1, param1Int1 + param1Int3 - 2, param1Int2 + param1Int4 - 1);
/*     */ 
/*     */       
/* 281 */       for (byte b = 1; b < 5; b++) {
/* 282 */         param1Graphics.drawRect(param1Int1 + b, param1Int2 + b, param1Int3 - b * 2 - 1, param1Int4 - b * 2 - 1);
/*     */       }
/*     */       
/* 285 */       if (window instanceof Frame && ((Frame)window).isResizable()) {
/* 286 */         param1Graphics.setColor(colorUIResource2);
/*     */         
/* 288 */         param1Graphics.drawLine(15, 3, param1Int3 - 14, 3);
/* 289 */         param1Graphics.drawLine(3, 15, 3, param1Int4 - 14);
/* 290 */         param1Graphics.drawLine(param1Int3 - 2, 15, param1Int3 - 2, param1Int4 - 14);
/* 291 */         param1Graphics.drawLine(15, param1Int4 - 2, param1Int3 - 14, param1Int4 - 2);
/*     */         
/* 293 */         param1Graphics.setColor(colorUIResource3);
/*     */         
/* 295 */         param1Graphics.drawLine(14, 2, param1Int3 - 14 - 1, 2);
/* 296 */         param1Graphics.drawLine(2, 14, 2, param1Int4 - 14 - 1);
/* 297 */         param1Graphics.drawLine(param1Int3 - 3, 14, param1Int3 - 3, param1Int4 - 14 - 1);
/* 298 */         param1Graphics.drawLine(14, param1Int4 - 3, param1Int3 - 14 - 1, param1Int4 - 3);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 305 */       param1Insets.set(5, 5, 5, 5);
/* 306 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class DialogBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/*     */     private static final int corner = 14;
/*     */ 
/*     */     
/*     */     protected Color getActiveBackground() {
/* 320 */       return MetalLookAndFeel.getPrimaryControlDarkShadow();
/*     */     }
/*     */ 
/*     */     
/*     */     protected Color getActiveHighlight() {
/* 325 */       return MetalLookAndFeel.getPrimaryControlShadow();
/*     */     }
/*     */ 
/*     */     
/*     */     protected Color getActiveShadow() {
/* 330 */       return MetalLookAndFeel.getPrimaryControlInfo();
/*     */     }
/*     */ 
/*     */     
/*     */     protected Color getInactiveBackground() {
/* 335 */       return MetalLookAndFeel.getControlDarkShadow();
/*     */     }
/*     */ 
/*     */     
/*     */     protected Color getInactiveHighlight() {
/* 340 */       return MetalLookAndFeel.getControlShadow();
/*     */     }
/*     */ 
/*     */     
/*     */     protected Color getInactiveShadow() {
/* 345 */       return MetalLookAndFeel.getControlInfo();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*     */       Color color1, color2, color3;
/* 354 */       Window window = SwingUtilities.getWindowAncestor(param1Component);
/* 355 */       if (window != null && window.isActive()) {
/* 356 */         color1 = getActiveBackground();
/* 357 */         color2 = getActiveHighlight();
/* 358 */         color3 = getActiveShadow();
/*     */       } else {
/* 360 */         color1 = getInactiveBackground();
/* 361 */         color2 = getInactiveHighlight();
/* 362 */         color3 = getInactiveShadow();
/*     */       } 
/*     */       
/* 365 */       param1Graphics.setColor(color1);
/*     */       
/* 367 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 0, param1Int1 + param1Int3 - 2, param1Int2 + 0);
/* 368 */       param1Graphics.drawLine(param1Int1 + 0, param1Int2 + 1, param1Int1 + 0, param1Int2 + param1Int4 - 2);
/* 369 */       param1Graphics.drawLine(param1Int1 + param1Int3 - 1, param1Int2 + 1, param1Int1 + param1Int3 - 1, param1Int2 + param1Int4 - 2);
/* 370 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + param1Int4 - 1, param1Int1 + param1Int3 - 2, param1Int2 + param1Int4 - 1);
/*     */ 
/*     */       
/* 373 */       for (byte b = 1; b < 5; b++) {
/* 374 */         param1Graphics.drawRect(param1Int1 + b, param1Int2 + b, param1Int3 - b * 2 - 1, param1Int4 - b * 2 - 1);
/*     */       }
/*     */ 
/*     */       
/* 378 */       if (window instanceof Dialog && ((Dialog)window).isResizable()) {
/* 379 */         param1Graphics.setColor(color2);
/*     */         
/* 381 */         param1Graphics.drawLine(15, 3, param1Int3 - 14, 3);
/* 382 */         param1Graphics.drawLine(3, 15, 3, param1Int4 - 14);
/* 383 */         param1Graphics.drawLine(param1Int3 - 2, 15, param1Int3 - 2, param1Int4 - 14);
/* 384 */         param1Graphics.drawLine(15, param1Int4 - 2, param1Int3 - 14, param1Int4 - 2);
/*     */         
/* 386 */         param1Graphics.setColor(color3);
/*     */         
/* 388 */         param1Graphics.drawLine(14, 2, param1Int3 - 14 - 1, 2);
/* 389 */         param1Graphics.drawLine(2, 14, 2, param1Int4 - 14 - 1);
/* 390 */         param1Graphics.drawLine(param1Int3 - 3, 14, param1Int3 - 3, param1Int4 - 14 - 1);
/* 391 */         param1Graphics.drawLine(14, param1Int4 - 3, param1Int3 - 14 - 1, param1Int4 - 3);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 398 */       param1Insets.set(5, 5, 5, 5);
/* 399 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ErrorDialogBorder
/*     */     extends DialogBorder
/*     */     implements UIResource
/*     */   {
/*     */     protected Color getActiveBackground() {
/* 410 */       return UIManager.getColor("OptionPane.errorDialog.border.background");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class QuestionDialogBorder
/*     */     extends DialogBorder
/*     */     implements UIResource
/*     */   {
/*     */     protected Color getActiveBackground() {
/* 423 */       return UIManager.getColor("OptionPane.questionDialog.border.background");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class WarningDialogBorder
/*     */     extends DialogBorder
/*     */     implements UIResource
/*     */   {
/*     */     protected Color getActiveBackground() {
/* 435 */       return UIManager.getColor("OptionPane.warningDialog.border.background");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PaletteBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/* 445 */     int titleHeight = 0;
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 449 */       param1Graphics.translate(param1Int1, param1Int2);
/* 450 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 451 */       param1Graphics.drawLine(0, 1, 0, param1Int4 - 2);
/* 452 */       param1Graphics.drawLine(1, param1Int4 - 1, param1Int3 - 2, param1Int4 - 1);
/* 453 */       param1Graphics.drawLine(param1Int3 - 1, 1, param1Int3 - 1, param1Int4 - 2);
/* 454 */       param1Graphics.drawLine(1, 0, param1Int3 - 2, 0);
/* 455 */       param1Graphics.drawRect(1, 1, param1Int3 - 3, param1Int4 - 3);
/* 456 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 461 */       param1Insets.set(1, 1, 1, 1);
/* 462 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class OptionDialogBorder extends AbstractBorder implements UIResource {
/* 467 */     int titleHeight = 0;
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*     */       Color color;
/* 471 */       param1Graphics.translate(param1Int1, param1Int2);
/*     */       
/* 473 */       int i = -1;
/* 474 */       if (param1Component instanceof JInternalFrame) {
/* 475 */         Object object = ((JInternalFrame)param1Component).getClientProperty("JInternalFrame.messageType");
/*     */         
/* 477 */         if (object instanceof Integer) {
/* 478 */           i = ((Integer)object).intValue();
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 484 */       switch (i) {
/*     */         case 0:
/* 486 */           color = UIManager.getColor("OptionPane.errorDialog.border.background");
/*     */           break;
/*     */         
/*     */         case 3:
/* 490 */           color = UIManager.getColor("OptionPane.questionDialog.border.background");
/*     */           break;
/*     */         
/*     */         case 2:
/* 494 */           color = UIManager.getColor("OptionPane.warningDialog.border.background");
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/* 500 */           color = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*     */           break;
/*     */       } 
/*     */       
/* 504 */       param1Graphics.setColor(color);
/*     */ 
/*     */       
/* 507 */       param1Graphics.drawLine(1, 0, param1Int3 - 2, 0);
/* 508 */       param1Graphics.drawLine(0, 1, 0, param1Int4 - 2);
/* 509 */       param1Graphics.drawLine(param1Int3 - 1, 1, param1Int3 - 1, param1Int4 - 2);
/* 510 */       param1Graphics.drawLine(1, param1Int4 - 1, param1Int3 - 2, param1Int4 - 1);
/*     */ 
/*     */       
/* 513 */       for (byte b = 1; b < 3; b++) {
/* 514 */         param1Graphics.drawRect(b, b, param1Int3 - b * 2 - 1, param1Int4 - b * 2 - 1);
/*     */       }
/*     */       
/* 517 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 522 */       param1Insets.set(3, 3, 3, 3);
/* 523 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MenuBarBorder
/*     */     extends AbstractBorder implements UIResource {
/* 529 */     protected static Insets borderInsets = new Insets(1, 0, 1, 0);
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 532 */       param1Graphics.translate(param1Int1, param1Int2);
/*     */       
/* 534 */       if (MetalLookAndFeel.usingOcean()) {
/*     */         
/* 536 */         if (param1Component instanceof JMenuBar && 
/* 537 */           !MetalToolBarUI.doesMenuBarBorderToolBar((JMenuBar)param1Component)) {
/* 538 */           param1Graphics.setColor(MetalLookAndFeel.getControl());
/* 539 */           SwingUtilities2.drawHLine(param1Graphics, 0, param1Int3 - 1, param1Int4 - 2);
/* 540 */           param1Graphics.setColor(UIManager.getColor("MenuBar.borderColor"));
/* 541 */           SwingUtilities2.drawHLine(param1Graphics, 0, param1Int3 - 1, param1Int4 - 1);
/*     */         } 
/*     */       } else {
/* 544 */         param1Graphics.setColor(MetalLookAndFeel.getControlShadow());
/* 545 */         SwingUtilities2.drawHLine(param1Graphics, 0, param1Int3 - 1, param1Int4 - 1);
/*     */       } 
/* 547 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 551 */       if (MetalLookAndFeel.usingOcean()) {
/* 552 */         param1Insets.set(0, 0, 2, 0);
/*     */       } else {
/*     */         
/* 555 */         param1Insets.set(1, 0, 1, 0);
/*     */       } 
/* 557 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MenuItemBorder extends AbstractBorder implements UIResource {
/* 562 */     protected static Insets borderInsets = new Insets(2, 2, 2, 2);
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 565 */       if (!(param1Component instanceof JMenuItem)) {
/*     */         return;
/*     */       }
/* 568 */       JMenuItem jMenuItem = (JMenuItem)param1Component;
/* 569 */       ButtonModel buttonModel = jMenuItem.getModel();
/*     */       
/* 571 */       param1Graphics.translate(param1Int1, param1Int2);
/*     */       
/* 573 */       if (param1Component.getParent() instanceof JMenuBar) {
/* 574 */         if (buttonModel.isArmed() || buttonModel.isSelected()) {
/* 575 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 576 */           param1Graphics.drawLine(0, 0, param1Int3 - 2, 0);
/* 577 */           param1Graphics.drawLine(0, 0, 0, param1Int4 - 1);
/* 578 */           param1Graphics.drawLine(param1Int3 - 2, 2, param1Int3 - 2, param1Int4 - 1);
/*     */           
/* 580 */           param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/* 581 */           param1Graphics.drawLine(param1Int3 - 1, 1, param1Int3 - 1, param1Int4 - 1);
/*     */           
/* 583 */           param1Graphics.setColor(MetalLookAndFeel.getMenuBackground());
/* 584 */           param1Graphics.drawLine(param1Int3 - 1, 0, param1Int3 - 1, 0);
/*     */         }
/*     */       
/* 587 */       } else if (buttonModel.isArmed() || (param1Component instanceof javax.swing.JMenu && buttonModel.isSelected())) {
/* 588 */         param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 589 */         param1Graphics.drawLine(0, 0, param1Int3 - 1, 0);
/*     */         
/* 591 */         param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/* 592 */         param1Graphics.drawLine(0, param1Int4 - 1, param1Int3 - 1, param1Int4 - 1);
/*     */       } else {
/* 594 */         param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/* 595 */         param1Graphics.drawLine(0, 0, 0, param1Int4 - 1);
/*     */       } 
/*     */ 
/*     */       
/* 599 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 603 */       param1Insets.set(2, 2, 2, 2);
/* 604 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PopupMenuBorder extends AbstractBorder implements UIResource {
/* 609 */     protected static Insets borderInsets = new Insets(3, 1, 2, 1);
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 612 */       param1Graphics.translate(param1Int1, param1Int2);
/*     */       
/* 614 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 615 */       param1Graphics.drawRect(0, 0, param1Int3 - 1, param1Int4 - 1);
/*     */       
/* 617 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/* 618 */       param1Graphics.drawLine(1, 1, param1Int3 - 2, 1);
/* 619 */       param1Graphics.drawLine(1, 2, 1, 2);
/* 620 */       param1Graphics.drawLine(1, param1Int4 - 2, 1, param1Int4 - 2);
/*     */       
/* 622 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 627 */       param1Insets.set(3, 1, 2, 1);
/* 628 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RolloverButtonBorder
/*     */     extends ButtonBorder
/*     */   {
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 636 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 637 */       ButtonModel buttonModel = abstractButton.getModel();
/*     */       
/* 639 */       if (buttonModel.isRollover() && (!buttonModel.isPressed() || buttonModel.isArmed())) {
/* 640 */         super.paintBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class RolloverMarginBorder
/*     */     extends EmptyBorder
/*     */   {
/*     */     public RolloverMarginBorder() {
/* 656 */       super(3, 3, 3, 3);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 660 */       Insets insets = null;
/*     */       
/* 662 */       if (param1Component instanceof AbstractButton) {
/* 663 */         insets = ((AbstractButton)param1Component).getMargin();
/*     */       }
/* 665 */       if (insets == null || insets instanceof UIResource) {
/*     */         
/* 667 */         param1Insets.left = this.left;
/* 668 */         param1Insets.top = this.top;
/* 669 */         param1Insets.right = this.right;
/* 670 */         param1Insets.bottom = this.bottom;
/*     */       } else {
/*     */         
/* 673 */         param1Insets.left = insets.left;
/* 674 */         param1Insets.top = insets.top;
/* 675 */         param1Insets.right = insets.right;
/* 676 */         param1Insets.bottom = insets.bottom;
/*     */       } 
/* 678 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ToolBarBorder
/*     */     extends AbstractBorder implements UIResource, SwingConstants {
/* 684 */     protected MetalBumps bumps = new MetalBumps(10, 10, 
/* 685 */         MetalLookAndFeel.getControlHighlight(), 
/* 686 */         MetalLookAndFeel.getControlDarkShadow(), 
/* 687 */         UIManager.getColor("ToolBar.background"));
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 691 */       if (!(param1Component instanceof JToolBar)) {
/*     */         return;
/*     */       }
/* 694 */       param1Graphics.translate(param1Int1, param1Int2);
/*     */       
/* 696 */       if (((JToolBar)param1Component).isFloatable())
/*     */       {
/* 698 */         if (((JToolBar)param1Component).getOrientation() == 0) {
/*     */           
/* 700 */           byte b = MetalLookAndFeel.usingOcean() ? -1 : 0;
/* 701 */           this.bumps.setBumpArea(10, param1Int4 - 4);
/* 702 */           if (MetalUtils.isLeftToRight(param1Component)) {
/* 703 */             this.bumps.paintIcon(param1Component, param1Graphics, 2, 2 + b);
/*     */           } else {
/* 705 */             this.bumps.paintIcon(param1Component, param1Graphics, param1Int3 - 12, 2 + b);
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 711 */           this.bumps.setBumpArea(param1Int3 - 4, 10);
/* 712 */           this.bumps.paintIcon(param1Component, param1Graphics, 2, 2);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 717 */       if (((JToolBar)param1Component).getOrientation() == 0 && 
/* 718 */         MetalLookAndFeel.usingOcean()) {
/* 719 */         param1Graphics.setColor(MetalLookAndFeel.getControl());
/* 720 */         param1Graphics.drawLine(0, param1Int4 - 2, param1Int3, param1Int4 - 2);
/* 721 */         param1Graphics.setColor(UIManager.getColor("ToolBar.borderColor"));
/* 722 */         param1Graphics.drawLine(0, param1Int4 - 1, param1Int3, param1Int4 - 1);
/*     */       } 
/*     */       
/* 725 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 729 */       if (MetalLookAndFeel.usingOcean()) {
/* 730 */         param1Insets.set(1, 2, 3, 2);
/*     */       } else {
/*     */         
/* 733 */         param1Insets.top = param1Insets.left = param1Insets.bottom = param1Insets.right = 2;
/*     */       } 
/*     */       
/* 736 */       if (!(param1Component instanceof JToolBar)) {
/* 737 */         return param1Insets;
/*     */       }
/* 739 */       if (((JToolBar)param1Component).isFloatable()) {
/* 740 */         if (((JToolBar)param1Component).getOrientation() == 0) {
/* 741 */           if (param1Component.getComponentOrientation().isLeftToRight()) {
/* 742 */             param1Insets.left = 16;
/*     */           } else {
/* 744 */             param1Insets.right = 16;
/*     */           } 
/*     */         } else {
/* 747 */           param1Insets.top = 16;
/*     */         } 
/*     */       }
/*     */       
/* 751 */       Insets insets = ((JToolBar)param1Component).getMargin();
/*     */       
/* 753 */       if (insets != null) {
/* 754 */         param1Insets.left += insets.left;
/* 755 */         param1Insets.top += insets.top;
/* 756 */         param1Insets.right += insets.right;
/* 757 */         param1Insets.bottom += insets.bottom;
/*     */       } 
/*     */       
/* 760 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getButtonBorder() {
/* 771 */     if (buttonBorder == null) {
/* 772 */       buttonBorder = new BorderUIResource.CompoundBorderUIResource(new ButtonBorder(), new BasicBorders.MarginBorder());
/*     */     }
/*     */ 
/*     */     
/* 776 */     return buttonBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getTextBorder() {
/* 786 */     if (textBorder == null) {
/* 787 */       textBorder = new BorderUIResource.CompoundBorderUIResource(new Flush3DBorder(), new BasicBorders.MarginBorder());
/*     */     }
/*     */ 
/*     */     
/* 791 */     return textBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getTextFieldBorder() {
/* 801 */     if (textFieldBorder == null) {
/* 802 */       textFieldBorder = new BorderUIResource.CompoundBorderUIResource(new TextFieldBorder(), new BasicBorders.MarginBorder());
/*     */     }
/*     */ 
/*     */     
/* 806 */     return textFieldBorder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class TextFieldBorder
/*     */     extends Flush3DBorder
/*     */   {
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 814 */       if (!(param1Component instanceof JTextComponent)) {
/*     */         
/* 816 */         if (param1Component.isEnabled()) {
/* 817 */           MetalUtils.drawFlush3DBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */         } else {
/* 819 */           MetalUtils.drawDisabledBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 824 */       if (param1Component.isEnabled() && ((JTextComponent)param1Component).isEditable()) {
/* 825 */         MetalUtils.drawFlush3DBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       } else {
/* 827 */         MetalUtils.drawDisabledBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ScrollPaneBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 837 */       if (!(param1Component instanceof JScrollPane)) {
/*     */         return;
/*     */       }
/* 840 */       JScrollPane jScrollPane = (JScrollPane)param1Component;
/* 841 */       JViewport jViewport1 = jScrollPane.getColumnHeader();
/* 842 */       int i = 0;
/* 843 */       if (jViewport1 != null) {
/* 844 */         i = jViewport1.getHeight();
/*     */       }
/* 846 */       JViewport jViewport2 = jScrollPane.getRowHeader();
/* 847 */       int j = 0;
/* 848 */       if (jViewport2 != null) {
/* 849 */         j = jViewport2.getWidth();
/*     */       }
/*     */       
/* 852 */       param1Graphics.translate(param1Int1, param1Int2);
/*     */       
/* 854 */       param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 855 */       param1Graphics.drawRect(0, 0, param1Int3 - 2, param1Int4 - 2);
/* 856 */       param1Graphics.setColor(MetalLookAndFeel.getControlHighlight());
/*     */       
/* 858 */       param1Graphics.drawLine(param1Int3 - 1, 1, param1Int3 - 1, param1Int4 - 1);
/* 859 */       param1Graphics.drawLine(1, param1Int4 - 1, param1Int3 - 1, param1Int4 - 1);
/*     */       
/* 861 */       param1Graphics.setColor(MetalLookAndFeel.getControl());
/* 862 */       param1Graphics.drawLine(param1Int3 - 2, 2 + i, param1Int3 - 2, 2 + i);
/* 863 */       param1Graphics.drawLine(1 + j, param1Int4 - 2, 1 + j, param1Int4 - 2);
/*     */       
/* 865 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 870 */       param1Insets.set(1, 1, 2, 2);
/* 871 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getToggleButtonBorder() {
/* 882 */     if (toggleButtonBorder == null) {
/* 883 */       toggleButtonBorder = new BorderUIResource.CompoundBorderUIResource(new ToggleButtonBorder(), new BasicBorders.MarginBorder());
/*     */     }
/*     */ 
/*     */     
/* 887 */     return toggleButtonBorder;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ToggleButtonBorder
/*     */     extends ButtonBorder
/*     */   {
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 895 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 896 */       ButtonModel buttonModel = abstractButton.getModel();
/* 897 */       if (MetalLookAndFeel.usingOcean()) {
/* 898 */         if (buttonModel.isArmed() || !abstractButton.isEnabled()) {
/* 899 */           super.paintBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */         } else {
/*     */           
/* 902 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 903 */           param1Graphics.drawRect(0, 0, param1Int3 - 1, param1Int4 - 1);
/*     */         } 
/*     */         return;
/*     */       } 
/* 907 */       if (!param1Component.isEnabled()) {
/* 908 */         MetalUtils.drawDisabledBorder(param1Graphics, param1Int1, param1Int2, param1Int3 - 1, param1Int4 - 1);
/*     */       }
/* 910 */       else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 911 */         MetalUtils.drawPressed3DBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/* 912 */       } else if (buttonModel.isSelected()) {
/* 913 */         MetalUtils.drawDark3DBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       } else {
/* 915 */         MetalUtils.drawFlush3DBorder(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TableHeaderBorder
/*     */     extends AbstractBorder
/*     */   {
/* 926 */     protected Insets editorBorderInsets = new Insets(2, 2, 2, 0);
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 929 */       param1Graphics.translate(param1Int1, param1Int2);
/*     */       
/* 931 */       param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 932 */       param1Graphics.drawLine(param1Int3 - 1, 0, param1Int3 - 1, param1Int4 - 1);
/* 933 */       param1Graphics.drawLine(1, param1Int4 - 1, param1Int3 - 1, param1Int4 - 1);
/* 934 */       param1Graphics.setColor(MetalLookAndFeel.getControlHighlight());
/* 935 */       param1Graphics.drawLine(0, 0, param1Int3 - 2, 0);
/* 936 */       param1Graphics.drawLine(0, 0, 0, param1Int4 - 2);
/*     */       
/* 938 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 942 */       param1Insets.set(2, 2, 2, 0);
/* 943 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getDesktopIconBorder() {
/* 952 */     return new BorderUIResource.CompoundBorderUIResource(new LineBorder(
/* 953 */           MetalLookAndFeel.getControlDarkShadow(), 1), new MatteBorder(2, 2, 1, 2, 
/* 954 */           MetalLookAndFeel.getControl()));
/*     */   }
/*     */   
/*     */   static Border getToolBarRolloverBorder() {
/* 958 */     if (MetalLookAndFeel.usingOcean()) {
/* 959 */       return new CompoundBorder(new ButtonBorder(), new RolloverMarginBorder());
/*     */     }
/*     */ 
/*     */     
/* 963 */     return new CompoundBorder(new RolloverButtonBorder(), new RolloverMarginBorder());
/*     */   }
/*     */ 
/*     */   
/*     */   static Border getToolBarNonrolloverBorder() {
/* 968 */     if (MetalLookAndFeel.usingOcean()) {
/* 969 */       new CompoundBorder(new ButtonBorder(), new RolloverMarginBorder());
/*     */     }
/*     */ 
/*     */     
/* 973 */     return new CompoundBorder(new ButtonBorder(), new RolloverMarginBorder());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalBorders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */