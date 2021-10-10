/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.SwingConstants;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.border.LineBorder;
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicBorders;
/*     */ import javax.swing.plaf.basic.BasicGraphicsUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsBorders
/*     */ {
/*     */   public static Border getProgressBarBorder() {
/*  53 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  54 */     return new BorderUIResource.CompoundBorderUIResource(new ProgressBarBorder(uIDefaults
/*     */           
/*  56 */           .getColor("ProgressBar.shadow"), uIDefaults
/*  57 */           .getColor("ProgressBar.highlight")), new EmptyBorder(1, 1, 1, 1));
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
/*     */   public static Border getToolBarBorder() {
/*  70 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  71 */     return new ToolBarBorder(uIDefaults
/*  72 */         .getColor("ToolBar.shadow"), uIDefaults
/*  73 */         .getColor("ToolBar.highlight"));
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
/*     */   public static Border getFocusCellHighlightBorder() {
/*  85 */     return new ComplementDashedBorder();
/*     */   }
/*     */   
/*     */   public static Border getTableHeaderBorder() {
/*  89 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  90 */     return new BorderUIResource.CompoundBorderUIResource(new BasicBorders.ButtonBorder(uIDefaults
/*     */           
/*  92 */           .getColor("Table.shadow"), uIDefaults
/*  93 */           .getColor("Table.darkShadow"), uIDefaults
/*  94 */           .getColor("Table.light"), uIDefaults
/*  95 */           .getColor("Table.highlight")), new BasicBorders.MarginBorder());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getInternalFrameBorder() {
/* 101 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 102 */     return new BorderUIResource.CompoundBorderUIResource(
/*     */         
/* 104 */         BorderFactory.createBevelBorder(0, uIDefaults
/* 105 */           .getColor("InternalFrame.borderColor"), uIDefaults
/* 106 */           .getColor("InternalFrame.borderHighlight"), uIDefaults
/* 107 */           .getColor("InternalFrame.borderDarkShadow"), uIDefaults
/* 108 */           .getColor("InternalFrame.borderShadow")), new InternalFrameLineBorder(uIDefaults
/*     */           
/* 110 */           .getColor("InternalFrame.activeBorderColor"), uIDefaults
/* 111 */           .getColor("InternalFrame.inactiveBorderColor"), uIDefaults
/* 112 */           .getInt("InternalFrame.borderWidth")));
/*     */   }
/*     */   
/*     */   public static class ProgressBarBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource {
/*     */     protected Color shadow;
/*     */     protected Color highlight;
/*     */     
/*     */     public ProgressBarBorder(Color param1Color1, Color param1Color2) {
/* 122 */       this.highlight = param1Color2;
/* 123 */       this.shadow = param1Color1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 128 */       param1Graphics.setColor(this.shadow);
/* 129 */       param1Graphics.drawLine(param1Int1, param1Int2, param1Int3 - 1, param1Int2);
/* 130 */       param1Graphics.drawLine(param1Int1, param1Int2, param1Int1, param1Int4 - 1);
/* 131 */       param1Graphics.setColor(this.highlight);
/* 132 */       param1Graphics.drawLine(param1Int1, param1Int4 - 1, param1Int3 - 1, param1Int4 - 1);
/* 133 */       param1Graphics.drawLine(param1Int3 - 1, param1Int2, param1Int3 - 1, param1Int4 - 1);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 137 */       param1Insets.set(1, 1, 1, 1);
/* 138 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ToolBarBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource, SwingConstants
/*     */   {
/*     */     protected Color shadow;
/*     */     
/*     */     protected Color highlight;
/*     */     
/*     */     public ToolBarBorder(Color param1Color1, Color param1Color2) {
/* 152 */       this.highlight = param1Color2;
/* 153 */       this.shadow = param1Color1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 158 */       if (!(param1Component instanceof JToolBar)) {
/*     */         return;
/*     */       }
/* 161 */       param1Graphics.translate(param1Int1, param1Int2);
/*     */       
/* 163 */       XPStyle xPStyle = XPStyle.getXP();
/* 164 */       if (xPStyle != null) {
/* 165 */         Border border = xPStyle.getBorder(param1Component, TMSchema.Part.TP_TOOLBAR);
/* 166 */         if (border != null) {
/* 167 */           border.paintBorder(param1Component, param1Graphics, 0, 0, param1Int3, param1Int4);
/*     */         }
/*     */       } 
/* 170 */       if (((JToolBar)param1Component).isFloatable()) {
/* 171 */         boolean bool = (((JToolBar)param1Component).getOrientation() == 1) ? true : false;
/*     */         
/* 173 */         if (xPStyle != null) {
/* 174 */           boolean bool1, bool2; int i, j; TMSchema.Part part = bool ? TMSchema.Part.RP_GRIPPERVERT : TMSchema.Part.RP_GRIPPER;
/* 175 */           XPStyle.Skin skin = xPStyle.getSkin(param1Component, part);
/*     */           
/* 177 */           if (bool) {
/* 178 */             bool1 = false;
/* 179 */             bool2 = true;
/* 180 */             i = param1Int3 - 1;
/* 181 */             j = skin.getHeight();
/*     */           } else {
/* 183 */             i = skin.getWidth();
/* 184 */             j = param1Int4 - 1;
/* 185 */             bool1 = param1Component.getComponentOrientation().isLeftToRight() ? true : (param1Int3 - i - 2);
/* 186 */             bool2 = false;
/*     */           } 
/* 188 */           skin.paintSkin(param1Graphics, bool1, bool2, i, j, TMSchema.State.NORMAL);
/*     */ 
/*     */         
/*     */         }
/* 192 */         else if (!bool) {
/* 193 */           if (param1Component.getComponentOrientation().isLeftToRight()) {
/* 194 */             param1Graphics.setColor(this.shadow);
/* 195 */             param1Graphics.drawLine(4, 3, 4, param1Int4 - 4);
/* 196 */             param1Graphics.drawLine(4, param1Int4 - 4, 2, param1Int4 - 4);
/*     */             
/* 198 */             param1Graphics.setColor(this.highlight);
/* 199 */             param1Graphics.drawLine(2, 3, 3, 3);
/* 200 */             param1Graphics.drawLine(2, 3, 2, param1Int4 - 5);
/*     */           } else {
/* 202 */             param1Graphics.setColor(this.shadow);
/* 203 */             param1Graphics.drawLine(param1Int3 - 3, 3, param1Int3 - 3, param1Int4 - 4);
/* 204 */             param1Graphics.drawLine(param1Int3 - 4, param1Int4 - 4, param1Int3 - 4, param1Int4 - 4);
/*     */             
/* 206 */             param1Graphics.setColor(this.highlight);
/* 207 */             param1Graphics.drawLine(param1Int3 - 5, 3, param1Int3 - 4, 3);
/* 208 */             param1Graphics.drawLine(param1Int3 - 5, 3, param1Int3 - 5, param1Int4 - 5);
/*     */           } 
/*     */         } else {
/* 211 */           param1Graphics.setColor(this.shadow);
/* 212 */           param1Graphics.drawLine(3, 4, param1Int3 - 4, 4);
/* 213 */           param1Graphics.drawLine(param1Int3 - 4, 2, param1Int3 - 4, 4);
/*     */           
/* 215 */           param1Graphics.setColor(this.highlight);
/* 216 */           param1Graphics.drawLine(3, 2, param1Int3 - 4, 2);
/* 217 */           param1Graphics.drawLine(3, 2, 3, 3);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 222 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 226 */       param1Insets.set(1, 1, 1, 1);
/* 227 */       if (!(param1Component instanceof JToolBar)) {
/* 228 */         return param1Insets;
/*     */       }
/* 230 */       if (((JToolBar)param1Component).isFloatable()) {
/* 231 */         byte b = (XPStyle.getXP() != null) ? 12 : 9;
/* 232 */         if (((JToolBar)param1Component).getOrientation() == 0) {
/* 233 */           if (param1Component.getComponentOrientation().isLeftToRight()) {
/* 234 */             param1Insets.left = b;
/*     */           } else {
/* 236 */             param1Insets.right = b;
/*     */           } 
/*     */         } else {
/* 239 */           param1Insets.top = b;
/*     */         } 
/*     */       } 
/* 242 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DashedBorder
/*     */     extends LineBorder
/*     */     implements UIResource
/*     */   {
/*     */     public DashedBorder(Color param1Color) {
/* 252 */       super(param1Color);
/*     */     }
/*     */     
/*     */     public DashedBorder(Color param1Color, int param1Int) {
/* 256 */       super(param1Color, param1Int);
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 260 */       Color color = param1Graphics.getColor();
/*     */ 
/*     */       
/* 263 */       param1Graphics.setColor(this.lineColor);
/* 264 */       for (byte b = 0; b < this.thickness; b++) {
/* 265 */         BasicGraphicsUtils.drawDashedRect(param1Graphics, param1Int1 + b, param1Int2 + b, param1Int3 - b - b, param1Int4 - b - b);
/*     */       }
/* 267 */       param1Graphics.setColor(color);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class ComplementDashedBorder
/*     */     extends LineBorder
/*     */     implements UIResource
/*     */   {
/*     */     private Color origColor;
/*     */     private Color paintColor;
/*     */     
/*     */     public ComplementDashedBorder() {
/* 280 */       super(null);
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 284 */       Color color = param1Component.getBackground();
/*     */       
/* 286 */       if (this.origColor != color) {
/* 287 */         this.origColor = color;
/* 288 */         this.paintColor = new Color(this.origColor.getRGB() ^ 0xFFFFFFFF);
/*     */       } 
/*     */       
/* 291 */       param1Graphics.setColor(this.paintColor);
/* 292 */       BasicGraphicsUtils.drawDashedRect(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class InternalFrameLineBorder
/*     */     extends LineBorder
/*     */     implements UIResource
/*     */   {
/*     */     protected Color activeColor;
/*     */     
/*     */     protected Color inactiveColor;
/*     */ 
/*     */     
/*     */     public InternalFrameLineBorder(Color param1Color1, Color param1Color2, int param1Int) {
/* 308 */       super(param1Color1, param1Int);
/* 309 */       this.activeColor = param1Color1;
/* 310 */       this.inactiveColor = param1Color2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 316 */       JInternalFrame jInternalFrame = null;
/* 317 */       if (param1Component instanceof JInternalFrame) {
/* 318 */         jInternalFrame = (JInternalFrame)param1Component;
/* 319 */       } else if (param1Component instanceof JInternalFrame.JDesktopIcon) {
/* 320 */         jInternalFrame = ((JInternalFrame.JDesktopIcon)param1Component).getInternalFrame();
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */       
/* 325 */       if (jInternalFrame.isSelected()) {
/*     */ 
/*     */         
/* 328 */         this.lineColor = this.activeColor;
/* 329 */         super.paintBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       } else {
/* 331 */         this.lineColor = this.inactiveColor;
/* 332 */         super.paintBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsBorders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */