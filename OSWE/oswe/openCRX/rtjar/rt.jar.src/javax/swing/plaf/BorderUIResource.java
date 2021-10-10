/*     */ package javax.swing.plaf;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.CompoundBorder;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.border.EtchedBorder;
/*     */ import javax.swing.border.LineBorder;
/*     */ import javax.swing.border.MatteBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BorderUIResource
/*     */   implements Border, UIResource, Serializable
/*     */ {
/*     */   static Border etched;
/*     */   static Border loweredBevel;
/*     */   static Border raisedBevel;
/*     */   static Border blackLine;
/*     */   private Border delegate;
/*     */   
/*     */   public static Border getEtchedBorderUIResource() {
/*  70 */     if (etched == null) {
/*  71 */       etched = new EtchedBorderUIResource();
/*     */     }
/*  73 */     return etched;
/*     */   }
/*     */   
/*     */   public static Border getLoweredBevelBorderUIResource() {
/*  77 */     if (loweredBevel == null) {
/*  78 */       loweredBevel = new BevelBorderUIResource(1);
/*     */     }
/*  80 */     return loweredBevel;
/*     */   }
/*     */   
/*     */   public static Border getRaisedBevelBorderUIResource() {
/*  84 */     if (raisedBevel == null) {
/*  85 */       raisedBevel = new BevelBorderUIResource(0);
/*     */     }
/*  87 */     return raisedBevel;
/*     */   }
/*     */   
/*     */   public static Border getBlackLineBorderUIResource() {
/*  91 */     if (blackLine == null) {
/*  92 */       blackLine = new LineBorderUIResource(Color.black);
/*     */     }
/*  94 */     return blackLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BorderUIResource(Border paramBorder) {
/* 105 */     if (paramBorder == null) {
/* 106 */       throw new IllegalArgumentException("null border delegate argument");
/*     */     }
/* 108 */     this.delegate = paramBorder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 113 */     this.delegate.paintBorder(paramComponent, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   public Insets getBorderInsets(Component paramComponent) {
/* 117 */     return this.delegate.getBorderInsets(paramComponent);
/*     */   }
/*     */   
/*     */   public boolean isBorderOpaque() {
/* 121 */     return this.delegate.isBorderOpaque();
/*     */   }
/*     */   
/*     */   public static class CompoundBorderUIResource extends CompoundBorder implements UIResource {
/*     */     @ConstructorProperties({"outsideBorder", "insideBorder"})
/*     */     public CompoundBorderUIResource(Border param1Border1, Border param1Border2) {
/* 127 */       super(param1Border1, param1Border2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EmptyBorderUIResource
/*     */     extends EmptyBorder
/*     */     implements UIResource {
/*     */     public EmptyBorderUIResource(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 135 */       super(param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */     @ConstructorProperties({"borderInsets"})
/*     */     public EmptyBorderUIResource(Insets param1Insets) {
/* 139 */       super(param1Insets);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LineBorderUIResource
/*     */     extends LineBorder implements UIResource {
/*     */     public LineBorderUIResource(Color param1Color) {
/* 146 */       super(param1Color);
/*     */     }
/*     */     
/*     */     @ConstructorProperties({"lineColor", "thickness"})
/*     */     public LineBorderUIResource(Color param1Color, int param1Int) {
/* 151 */       super(param1Color, param1Int);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BevelBorderUIResource
/*     */     extends BevelBorder
/*     */     implements UIResource {
/*     */     public BevelBorderUIResource(int param1Int) {
/* 159 */       super(param1Int);
/*     */     }
/*     */     
/*     */     public BevelBorderUIResource(int param1Int, Color param1Color1, Color param1Color2) {
/* 163 */       super(param1Int, param1Color1, param1Color2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @ConstructorProperties({"bevelType", "highlightOuterColor", "highlightInnerColor", "shadowOuterColor", "shadowInnerColor"})
/*     */     public BevelBorderUIResource(int param1Int, Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 170 */       super(param1Int, param1Color1, param1Color2, param1Color3, param1Color4);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EtchedBorderUIResource
/*     */     extends EtchedBorder
/*     */     implements UIResource
/*     */   {
/*     */     public EtchedBorderUIResource() {}
/*     */     
/*     */     public EtchedBorderUIResource(int param1Int) {
/* 181 */       super(param1Int);
/*     */     }
/*     */     
/*     */     public EtchedBorderUIResource(Color param1Color1, Color param1Color2) {
/* 185 */       super(param1Color1, param1Color2);
/*     */     }
/*     */     
/*     */     @ConstructorProperties({"etchType", "highlightColor", "shadowColor"})
/*     */     public EtchedBorderUIResource(int param1Int, Color param1Color1, Color param1Color2) {
/* 190 */       super(param1Int, param1Color1, param1Color2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MatteBorderUIResource
/*     */     extends MatteBorder
/*     */     implements UIResource {
/*     */     public MatteBorderUIResource(int param1Int1, int param1Int2, int param1Int3, int param1Int4, Color param1Color) {
/* 198 */       super(param1Int1, param1Int2, param1Int3, param1Int4, param1Color);
/*     */     }
/*     */ 
/*     */     
/*     */     public MatteBorderUIResource(int param1Int1, int param1Int2, int param1Int3, int param1Int4, Icon param1Icon) {
/* 203 */       super(param1Int1, param1Int2, param1Int3, param1Int4, param1Icon);
/*     */     }
/*     */     
/*     */     public MatteBorderUIResource(Icon param1Icon) {
/* 207 */       super(param1Icon);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TitledBorderUIResource
/*     */     extends TitledBorder implements UIResource {
/*     */     public TitledBorderUIResource(String param1String) {
/* 214 */       super(param1String);
/*     */     }
/*     */     
/*     */     public TitledBorderUIResource(Border param1Border) {
/* 218 */       super(param1Border);
/*     */     }
/*     */     
/*     */     public TitledBorderUIResource(Border param1Border, String param1String) {
/* 222 */       super(param1Border, param1String);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TitledBorderUIResource(Border param1Border, String param1String, int param1Int1, int param1Int2) {
/* 229 */       super(param1Border, param1String, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TitledBorderUIResource(Border param1Border, String param1String, int param1Int1, int param1Int2, Font param1Font) {
/* 237 */       super(param1Border, param1String, param1Int1, param1Int2, param1Font);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @ConstructorProperties({"border", "title", "titleJustification", "titlePosition", "titleFont", "titleColor"})
/*     */     public TitledBorderUIResource(Border param1Border, String param1String, int param1Int1, int param1Int2, Font param1Font, Color param1Color) {
/* 247 */       super(param1Border, param1String, param1Int1, param1Int2, param1Font, param1Color);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/BorderUIResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */