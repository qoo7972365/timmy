/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.KeyEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JToolTip;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicHTML;
/*     */ import javax.swing.plaf.basic.BasicToolTipUI;
/*     */ import javax.swing.text.View;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalToolTipUI
/*     */   extends BasicToolTipUI
/*     */ {
/*  56 */   static MetalToolTipUI sharedInstance = new MetalToolTipUI();
/*     */   
/*     */   private Font smallFont;
/*     */   
/*     */   private JToolTip tip;
/*     */   
/*     */   public static final int padSpaceBetweenStrings = 12;
/*     */   
/*     */   private String acceleratorDelimiter;
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  68 */     return sharedInstance;
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  72 */     super.installUI(paramJComponent);
/*  73 */     this.tip = (JToolTip)paramJComponent;
/*  74 */     Font font = paramJComponent.getFont();
/*  75 */     this.smallFont = new Font(font.getName(), font.getStyle(), font.getSize() - 2);
/*  76 */     this.acceleratorDelimiter = UIManager.getString("MenuItem.acceleratorDelimiter");
/*  77 */     if (this.acceleratorDelimiter == null) this.acceleratorDelimiter = "-"; 
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  81 */     super.uninstallUI(paramJComponent);
/*  82 */     this.tip = null;
/*     */   }
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*     */     int i;
/*  86 */     JToolTip jToolTip = (JToolTip)paramJComponent;
/*  87 */     Font font = paramJComponent.getFont();
/*  88 */     FontMetrics fontMetrics1 = SwingUtilities2.getFontMetrics(paramJComponent, paramGraphics, font);
/*  89 */     Dimension dimension = paramJComponent.getSize();
/*     */ 
/*     */     
/*  92 */     paramGraphics.setColor(paramJComponent.getForeground());
/*     */     
/*  94 */     String str1 = jToolTip.getTipText();
/*  95 */     if (str1 == null) {
/*  96 */       str1 = "";
/*     */     }
/*     */     
/*  99 */     String str2 = getAcceleratorString(jToolTip);
/* 100 */     FontMetrics fontMetrics2 = SwingUtilities2.getFontMetrics(paramJComponent, paramGraphics, this.smallFont);
/* 101 */     int j = calcAccelSpacing(paramJComponent, fontMetrics2, str2);
/*     */     
/* 103 */     Insets insets = jToolTip.getInsets();
/* 104 */     Rectangle rectangle = new Rectangle(insets.left + 3, insets.top, dimension.width - insets.left + insets.right - 6 - j, dimension.height - insets.top + insets.bottom);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     View view = (View)paramJComponent.getClientProperty("html");
/* 110 */     if (view != null) {
/* 111 */       view.paint(paramGraphics, rectangle);
/* 112 */       i = BasicHTML.getHTMLBaseline(view, rectangle.width, rectangle.height);
/*     */     } else {
/*     */       
/* 115 */       paramGraphics.setFont(font);
/* 116 */       SwingUtilities2.drawString(jToolTip, paramGraphics, str1, rectangle.x, rectangle.y + fontMetrics1
/* 117 */           .getAscent());
/* 118 */       i = fontMetrics1.getAscent();
/*     */     } 
/*     */     
/* 121 */     if (!str2.equals("")) {
/* 122 */       paramGraphics.setFont(this.smallFont);
/* 123 */       paramGraphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 124 */       SwingUtilities2.drawString(jToolTip, paramGraphics, str2, jToolTip
/* 125 */           .getWidth() - 1 - insets.right - j + 12 - 3, rectangle.y + i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calcAccelSpacing(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString) {
/* 134 */     return paramString.equals("") ? 0 : (12 + 
/*     */ 
/*     */       
/* 137 */       SwingUtilities2.stringWidth(paramJComponent, paramFontMetrics, paramString));
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 141 */     Dimension dimension = super.getPreferredSize(paramJComponent);
/*     */     
/* 143 */     String str = getAcceleratorString((JToolTip)paramJComponent);
/* 144 */     if (!str.equals("")) {
/* 145 */       dimension.width += calcAccelSpacing(paramJComponent, paramJComponent.getFontMetrics(this.smallFont), str);
/*     */     }
/* 147 */     return dimension;
/*     */   }
/*     */   
/*     */   protected boolean isAcceleratorHidden() {
/* 151 */     Boolean bool = (Boolean)UIManager.get("ToolTip.hideAccelerator");
/* 152 */     return (bool != null && bool.booleanValue());
/*     */   }
/*     */   
/*     */   private String getAcceleratorString(JToolTip paramJToolTip) {
/* 156 */     this.tip = paramJToolTip;
/*     */     
/* 158 */     String str = getAcceleratorString();
/*     */     
/* 160 */     this.tip = null;
/* 161 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAcceleratorString() {
/* 171 */     if (this.tip == null || isAcceleratorHidden()) {
/* 172 */       return "";
/*     */     }
/* 174 */     JComponent jComponent = this.tip.getComponent();
/* 175 */     if (!(jComponent instanceof javax.swing.AbstractButton)) {
/* 176 */       return "";
/*     */     }
/*     */     
/* 179 */     KeyStroke[] arrayOfKeyStroke = jComponent.getInputMap(2).keys();
/* 180 */     if (arrayOfKeyStroke == null) {
/* 181 */       return "";
/*     */     }
/*     */     
/* 184 */     String str = "";
/*     */     
/* 186 */     byte b = 0; if (b < arrayOfKeyStroke.length) {
/* 187 */       int i = arrayOfKeyStroke[b].getModifiers();
/*     */ 
/*     */       
/* 190 */       str = KeyEvent.getKeyModifiersText(i) + this.acceleratorDelimiter + KeyEvent.getKeyText(arrayOfKeyStroke[b].getKeyCode());
/*     */     } 
/*     */ 
/*     */     
/* 194 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalToolTipUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */