/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicComboBoxUI;
/*     */ import javax.swing.plaf.basic.BasicComboPopup;
/*     */ import javax.swing.plaf.basic.ComboPopup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifComboBoxUI
/*     */   extends BasicComboBoxUI
/*     */   implements Serializable
/*     */ {
/*     */   Icon arrowIcon;
/*     */   static final int HORIZ_MARGIN = 3;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  52 */     return new MotifComboBoxUI();
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  56 */     super.installUI(paramJComponent);
/*  57 */     this
/*     */       
/*  59 */       .arrowIcon = new MotifComboBoxArrowIcon(UIManager.getColor("controlHighlight"), UIManager.getColor("controlShadow"), UIManager.getColor("control"));
/*     */     
/*  61 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/*  63 */           if (MotifComboBoxUI.this.motifGetEditor() != null) {
/*  64 */             MotifComboBoxUI.this.motifGetEditor().setBackground(UIManager.getColor("text"));
/*     */           }
/*     */         }
/*     */       };
/*     */     
/*  69 */     SwingUtilities.invokeLater(runnable);
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/*  73 */     if (!this.isMinimumSizeDirty) {
/*  74 */       return new Dimension(this.cachedMinimumSize);
/*     */     }
/*     */     
/*  77 */     Insets insets = getInsets();
/*  78 */     Dimension dimension = getDisplaySize();
/*  79 */     dimension.height += insets.top + insets.bottom;
/*  80 */     int i = iconAreaWidth();
/*  81 */     dimension.width += insets.left + insets.right + i;
/*     */     
/*  83 */     this.cachedMinimumSize.setSize(dimension.width, dimension.height);
/*  84 */     this.isMinimumSizeDirty = false;
/*     */     
/*  86 */     return dimension;
/*     */   }
/*     */   
/*     */   protected ComboPopup createPopup() {
/*  90 */     return new MotifComboPopup(this.comboBox);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected class MotifComboPopup
/*     */     extends BasicComboPopup
/*     */   {
/*     */     public MotifComboPopup(JComboBox<Object> param1JComboBox) {
/*  99 */       super(param1JComboBox);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MouseMotionListener createListMouseMotionListener() {
/* 106 */       return new MouseMotionAdapter() {  }
/*     */         ;
/*     */     }
/*     */     public KeyListener createKeyListener() {
/* 110 */       return super.createKeyListener();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected class InvocationKeyHandler
/*     */       extends BasicComboPopup.InvocationKeyHandler {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installComponents() {
/* 121 */     if (this.comboBox.isEditable()) {
/* 122 */       addEditor();
/*     */     }
/*     */     
/* 125 */     this.comboBox.add(this.currentValuePane);
/*     */   }
/*     */   
/*     */   protected void uninstallComponents() {
/* 129 */     removeEditor();
/* 130 */     this.comboBox.removeAll();
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 134 */     boolean bool = this.comboBox.hasFocus();
/*     */ 
/*     */     
/* 137 */     if (this.comboBox.isEnabled()) {
/* 138 */       paramGraphics.setColor(this.comboBox.getBackground());
/*     */     } else {
/* 140 */       paramGraphics.setColor(UIManager.getColor("ComboBox.disabledBackground"));
/*     */     } 
/* 142 */     paramGraphics.fillRect(0, 0, paramJComponent.getWidth(), paramJComponent.getHeight());
/*     */     
/* 144 */     if (!this.comboBox.isEditable()) {
/* 145 */       Rectangle rectangle1 = rectangleForCurrentValue();
/* 146 */       paintCurrentValue(paramGraphics, rectangle1, bool);
/*     */     } 
/* 148 */     Rectangle rectangle = rectangleForArrowIcon();
/* 149 */     this.arrowIcon.paintIcon(paramJComponent, paramGraphics, rectangle.x, rectangle.y);
/* 150 */     if (!this.comboBox.isEditable()) {
/* 151 */       Insets insets; Border border = this.comboBox.getBorder();
/*     */       
/* 153 */       if (border != null) {
/* 154 */         insets = border.getBorderInsets(this.comboBox);
/*     */       } else {
/*     */         
/* 157 */         insets = new Insets(0, 0, 0, 0);
/*     */       } 
/*     */       
/* 160 */       if (MotifGraphicsUtils.isLeftToRight(this.comboBox)) {
/* 161 */         rectangle.x -= 5;
/*     */       } else {
/*     */         
/* 164 */         rectangle.x += rectangle.width + 3 + 1;
/*     */       } 
/* 166 */       rectangle.y = insets.top;
/* 167 */       rectangle.width = 1;
/* 168 */       rectangle.height = (this.comboBox.getBounds()).height - insets.bottom - insets.top;
/* 169 */       paramGraphics.setColor(UIManager.getColor("controlShadow"));
/* 170 */       paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/* 171 */       rectangle.x++;
/* 172 */       paramGraphics.setColor(UIManager.getColor("controlHighlight"));
/* 173 */       paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void paintCurrentValue(Graphics paramGraphics, Rectangle paramRectangle, boolean paramBoolean) {
/* 178 */     ListCellRenderer<? super Object> listCellRenderer = this.comboBox.getRenderer();
/*     */ 
/*     */     
/* 181 */     Component component = listCellRenderer.getListCellRendererComponent(this.listBox, this.comboBox.getSelectedItem(), -1, false, false);
/* 182 */     component.setFont(this.comboBox.getFont());
/* 183 */     if (this.comboBox.isEnabled()) {
/* 184 */       component.setForeground(this.comboBox.getForeground());
/* 185 */       component.setBackground(this.comboBox.getBackground());
/*     */     } else {
/*     */       
/* 188 */       component.setForeground(UIManager.getColor("ComboBox.disabledForeground"));
/* 189 */       component.setBackground(UIManager.getColor("ComboBox.disabledBackground"));
/*     */     } 
/* 191 */     Dimension dimension = component.getPreferredSize();
/* 192 */     this.currentValuePane.paintComponent(paramGraphics, component, this.comboBox, paramRectangle.x, paramRectangle.y, paramRectangle.width, dimension.height);
/*     */   }
/*     */   
/*     */   protected Rectangle rectangleForArrowIcon() {
/*     */     Insets insets;
/* 197 */     Rectangle rectangle = this.comboBox.getBounds();
/* 198 */     Border border = this.comboBox.getBorder();
/*     */     
/* 200 */     if (border != null) {
/* 201 */       insets = border.getBorderInsets(this.comboBox);
/*     */     } else {
/*     */       
/* 204 */       insets = new Insets(0, 0, 0, 0);
/*     */     } 
/* 206 */     rectangle.x = insets.left;
/* 207 */     rectangle.y = insets.top;
/* 208 */     rectangle.width -= insets.left + insets.right;
/* 209 */     rectangle.height -= insets.top + insets.bottom;
/*     */     
/* 211 */     if (MotifGraphicsUtils.isLeftToRight(this.comboBox)) {
/* 212 */       rectangle.x = rectangle.x + rectangle.width - 3 - this.arrowIcon.getIconWidth();
/*     */     } else {
/*     */       
/* 215 */       rectangle.x += 3;
/*     */     } 
/* 217 */     rectangle.y += (rectangle.height - this.arrowIcon.getIconHeight()) / 2;
/* 218 */     rectangle.width = this.arrowIcon.getIconWidth();
/* 219 */     rectangle.height = this.arrowIcon.getIconHeight();
/* 220 */     return rectangle;
/*     */   }
/*     */   
/*     */   protected Rectangle rectangleForCurrentValue() {
/* 224 */     int i = this.comboBox.getWidth();
/* 225 */     int j = this.comboBox.getHeight();
/* 226 */     Insets insets = getInsets();
/* 227 */     if (MotifGraphicsUtils.isLeftToRight(this.comboBox)) {
/* 228 */       return new Rectangle(insets.left, insets.top, i - insets.left + insets.right - 
/*     */           
/* 230 */           iconAreaWidth(), j - insets.top + insets.bottom);
/*     */     }
/*     */ 
/*     */     
/* 234 */     return new Rectangle(insets.left + iconAreaWidth(), insets.top, i - insets.left + insets.right - 
/*     */         
/* 236 */         iconAreaWidth(), j - insets.top + insets.bottom);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int iconAreaWidth() {
/* 242 */     if (this.comboBox.isEditable()) {
/* 243 */       return this.arrowIcon.getIconWidth() + 6;
/*     */     }
/* 245 */     return this.arrowIcon.getIconWidth() + 9 + 2;
/*     */   }
/*     */   
/*     */   public void configureEditor() {
/* 249 */     super.configureEditor();
/* 250 */     this.editor.setBackground(UIManager.getColor("text"));
/*     */   }
/*     */   
/*     */   protected LayoutManager createLayoutManager() {
/* 254 */     return new ComboBoxLayoutManager();
/*     */   }
/*     */   
/*     */   private Component motifGetEditor() {
/* 258 */     return this.editor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class ComboBoxLayoutManager
/*     */     extends BasicComboBoxUI.ComboBoxLayoutManager
/*     */   {
/*     */     public void layoutContainer(Container param1Container) {
/* 271 */       if (MotifComboBoxUI.this.motifGetEditor() != null) {
/* 272 */         Rectangle rectangle = MotifComboBoxUI.this.rectangleForCurrentValue();
/* 273 */         rectangle.x++;
/* 274 */         rectangle.y++;
/* 275 */         rectangle.width--;
/* 276 */         rectangle.height -= 2;
/* 277 */         MotifComboBoxUI.this.motifGetEditor().setBounds(rectangle);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class MotifComboBoxArrowIcon implements Icon, Serializable {
/*     */     private Color lightShadow;
/*     */     private Color darkShadow;
/*     */     private Color fill;
/*     */     
/*     */     public MotifComboBoxArrowIcon(Color param1Color1, Color param1Color2, Color param1Color3) {
/* 288 */       this.lightShadow = param1Color1;
/* 289 */       this.darkShadow = param1Color2;
/* 290 */       this.fill = param1Color3;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 295 */       int i = getIconWidth();
/* 296 */       int j = getIconHeight();
/*     */       
/* 298 */       param1Graphics.setColor(this.lightShadow);
/* 299 */       param1Graphics.drawLine(param1Int1, param1Int2, param1Int1 + i - 1, param1Int2);
/* 300 */       param1Graphics.drawLine(param1Int1, param1Int2 + 1, param1Int1 + i - 3, param1Int2 + 1);
/* 301 */       param1Graphics.setColor(this.darkShadow);
/* 302 */       param1Graphics.drawLine(param1Int1 + i - 2, param1Int2 + 1, param1Int1 + i - 1, param1Int2 + 1);
/*     */       
/* 304 */       for (int k = param1Int1 + 1, m = param1Int2 + 2, n = i - 6; m + 1 < param1Int2 + j; m += 2) {
/* 305 */         param1Graphics.setColor(this.lightShadow);
/* 306 */         param1Graphics.drawLine(k, m, k + 1, m);
/* 307 */         param1Graphics.drawLine(k, m + 1, k + 1, m + 1);
/* 308 */         if (n > 0) {
/* 309 */           param1Graphics.setColor(this.fill);
/* 310 */           param1Graphics.drawLine(k + 2, m, k + 1 + n, m);
/* 311 */           param1Graphics.drawLine(k + 2, m + 1, k + 1 + n, m + 1);
/*     */         } 
/* 313 */         param1Graphics.setColor(this.darkShadow);
/* 314 */         param1Graphics.drawLine(k + n + 2, m, k + n + 3, m);
/* 315 */         param1Graphics.drawLine(k + n + 2, m + 1, k + n + 3, m + 1);
/* 316 */         k++;
/* 317 */         n -= 2;
/*     */       } 
/*     */       
/* 320 */       param1Graphics.setColor(this.darkShadow);
/* 321 */       param1Graphics.drawLine(param1Int1 + i / 2, param1Int2 + j - 1, param1Int1 + i / 2, param1Int2 + j - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIconWidth() {
/* 326 */       return 11;
/*     */     }
/*     */     
/*     */     public int getIconHeight() {
/* 330 */       return 11;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PropertyChangeListener createPropertyChangeListener() {
/* 340 */     return new MotifPropertyChangeListener();
/*     */   }
/*     */   
/*     */   private class MotifPropertyChangeListener
/*     */     extends BasicComboBoxUI.PropertyChangeHandler
/*     */   {
/*     */     private MotifPropertyChangeListener() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 349 */       super.propertyChange(param1PropertyChangeEvent);
/* 350 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 351 */       if (str == "enabled" && 
/* 352 */         MotifComboBoxUI.this.comboBox.isEnabled()) {
/* 353 */         Component component = MotifComboBoxUI.this.motifGetEditor();
/* 354 */         if (component != null)
/* 355 */           component.setBackground(UIManager.getColor("text")); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifComboBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */