/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JToolTip;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.ToolTipUI;
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
/*     */ public class BasicToolTipUI
/*     */   extends ToolTipUI
/*     */ {
/*  50 */   static BasicToolTipUI sharedInstance = new BasicToolTipUI();
/*     */ 
/*     */   
/*     */   private static PropertyChangeListener sharedPropertyChangedListener;
/*     */ 
/*     */   
/*     */   private PropertyChangeListener propertyChangeListener;
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  60 */     return sharedInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  68 */     installDefaults(paramJComponent);
/*  69 */     installComponents(paramJComponent);
/*  70 */     installListeners(paramJComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  75 */     uninstallDefaults(paramJComponent);
/*  76 */     uninstallComponents(paramJComponent);
/*  77 */     uninstallListeners(paramJComponent);
/*     */   }
/*     */   
/*     */   protected void installDefaults(JComponent paramJComponent) {
/*  81 */     LookAndFeel.installColorsAndFont(paramJComponent, "ToolTip.background", "ToolTip.foreground", "ToolTip.font");
/*     */ 
/*     */     
/*  84 */     LookAndFeel.installProperty(paramJComponent, "opaque", Boolean.TRUE);
/*  85 */     componentChanged(paramJComponent);
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(JComponent paramJComponent) {
/*  89 */     LookAndFeel.uninstallBorder(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void installComponents(JComponent paramJComponent) {
/*  95 */     BasicHTML.updateRenderer(paramJComponent, ((JToolTip)paramJComponent).getTipText());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void uninstallComponents(JComponent paramJComponent) {
/* 101 */     BasicHTML.updateRenderer(paramJComponent, "");
/*     */   }
/*     */   
/*     */   protected void installListeners(JComponent paramJComponent) {
/* 105 */     this.propertyChangeListener = createPropertyChangeListener(paramJComponent);
/*     */     
/* 107 */     paramJComponent.addPropertyChangeListener(this.propertyChangeListener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners(JComponent paramJComponent) {
/* 111 */     paramJComponent.removePropertyChangeListener(this.propertyChangeListener);
/*     */     
/* 113 */     this.propertyChangeListener = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PropertyChangeListener createPropertyChangeListener(JComponent paramJComponent) {
/* 119 */     if (sharedPropertyChangedListener == null) {
/* 120 */       sharedPropertyChangedListener = new PropertyChangeHandler();
/*     */     }
/* 122 */     return sharedPropertyChangedListener;
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 126 */     Font font = paramJComponent.getFont();
/* 127 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(paramJComponent, paramGraphics, font);
/* 128 */     Dimension dimension = paramJComponent.getSize();
/*     */     
/* 130 */     paramGraphics.setColor(paramJComponent.getForeground());
/*     */     
/* 132 */     String str = ((JToolTip)paramJComponent).getTipText();
/* 133 */     if (str == null) {
/* 134 */       str = "";
/*     */     }
/*     */     
/* 137 */     Insets insets = paramJComponent.getInsets();
/* 138 */     Rectangle rectangle = new Rectangle(insets.left + 3, insets.top, dimension.width - insets.left + insets.right - 6, dimension.height - insets.top + insets.bottom);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     View view = (View)paramJComponent.getClientProperty("html");
/* 144 */     if (view != null) {
/* 145 */       view.paint(paramGraphics, rectangle);
/*     */     } else {
/* 147 */       paramGraphics.setFont(font);
/* 148 */       SwingUtilities2.drawString(paramJComponent, paramGraphics, str, rectangle.x, rectangle.y + fontMetrics
/* 149 */           .getAscent());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 154 */     Font font = paramJComponent.getFont();
/* 155 */     FontMetrics fontMetrics = paramJComponent.getFontMetrics(font);
/* 156 */     Insets insets = paramJComponent.getInsets();
/*     */     
/* 158 */     Dimension dimension = new Dimension(insets.left + insets.right, insets.top + insets.bottom);
/*     */     
/* 160 */     String str = ((JToolTip)paramJComponent).getTipText();
/*     */     
/* 162 */     if (str == null || str.equals("")) {
/* 163 */       str = "";
/*     */     } else {
/*     */       
/* 166 */       View view = (paramJComponent != null) ? (View)paramJComponent.getClientProperty("html") : null;
/* 167 */       if (view != null) {
/* 168 */         dimension.width += (int)view.getPreferredSpan(0) + 6;
/* 169 */         dimension.height += (int)view.getPreferredSpan(1);
/*     */       } else {
/* 171 */         dimension.width += SwingUtilities2.stringWidth(paramJComponent, fontMetrics, str) + 6;
/* 172 */         dimension.height += fontMetrics.getHeight();
/*     */       } 
/*     */     } 
/* 175 */     return dimension;
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 179 */     Dimension dimension = getPreferredSize(paramJComponent);
/* 180 */     View view = (View)paramJComponent.getClientProperty("html");
/* 181 */     if (view != null) {
/* 182 */       dimension.width = (int)(dimension.width - view.getPreferredSpan(0) - view.getMinimumSpan(0));
/*     */     }
/* 184 */     return dimension;
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 188 */     Dimension dimension = getPreferredSize(paramJComponent);
/* 189 */     View view = (View)paramJComponent.getClientProperty("html");
/* 190 */     if (view != null) {
/* 191 */       dimension.width = (int)(dimension.width + view.getMaximumSpan(0) - view.getPreferredSpan(0));
/*     */     }
/* 193 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void componentChanged(JComponent paramJComponent) {
/* 204 */     JComponent jComponent = ((JToolTip)paramJComponent).getComponent();
/*     */     
/* 206 */     if (jComponent != null && !jComponent.isEnabled()) {
/*     */ 
/*     */       
/* 209 */       if (UIManager.getBorder("ToolTip.borderInactive") != null) {
/* 210 */         LookAndFeel.installBorder(paramJComponent, "ToolTip.borderInactive");
/*     */       } else {
/*     */         
/* 213 */         LookAndFeel.installBorder(paramJComponent, "ToolTip.border");
/*     */       } 
/* 215 */       if (UIManager.getColor("ToolTip.backgroundInactive") != null) {
/* 216 */         LookAndFeel.installColors(paramJComponent, "ToolTip.backgroundInactive", "ToolTip.foregroundInactive");
/*     */       }
/*     */       else {
/*     */         
/* 220 */         LookAndFeel.installColors(paramJComponent, "ToolTip.background", "ToolTip.foreground");
/*     */       } 
/*     */     } else {
/*     */       
/* 224 */       LookAndFeel.installBorder(paramJComponent, "ToolTip.border");
/* 225 */       LookAndFeel.installColors(paramJComponent, "ToolTip.background", "ToolTip.foreground");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class PropertyChangeHandler
/*     */     implements PropertyChangeListener {
/*     */     private PropertyChangeHandler() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 234 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 235 */       if (str.equals("tiptext") || "font".equals(str) || "foreground"
/* 236 */         .equals(str)) {
/*     */ 
/*     */ 
/*     */         
/* 240 */         JToolTip jToolTip = (JToolTip)param1PropertyChangeEvent.getSource();
/* 241 */         String str1 = jToolTip.getTipText();
/* 242 */         BasicHTML.updateRenderer(jToolTip, str1);
/*     */       }
/* 244 */       else if ("component".equals(str)) {
/* 245 */         JToolTip jToolTip = (JToolTip)param1PropertyChangeEvent.getSource();
/*     */         
/* 247 */         if (jToolTip.getUI() instanceof BasicToolTipUI)
/* 248 */           ((BasicToolTipUI)jToolTip.getUI()).componentChanged(jToolTip); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicToolTipUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */