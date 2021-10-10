/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Polygon;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicIconFactory
/*     */   implements Serializable
/*     */ {
/*     */   private static Icon frame_icon;
/*     */   private static Icon checkBoxIcon;
/*     */   private static Icon radioButtonIcon;
/*     */   private static Icon checkBoxMenuItemIcon;
/*     */   private static Icon radioButtonMenuItemIcon;
/*     */   private static Icon menuItemCheckIcon;
/*     */   private static Icon menuItemArrowIcon;
/*     */   private static Icon menuArrowIcon;
/*     */   
/*     */   public static Icon getMenuItemCheckIcon() {
/*  64 */     if (menuItemCheckIcon == null) {
/*  65 */       menuItemCheckIcon = new MenuItemCheckIcon();
/*     */     }
/*  67 */     return menuItemCheckIcon;
/*     */   }
/*     */   
/*     */   public static Icon getMenuItemArrowIcon() {
/*  71 */     if (menuItemArrowIcon == null) {
/*  72 */       menuItemArrowIcon = new MenuItemArrowIcon();
/*     */     }
/*  74 */     return menuItemArrowIcon;
/*     */   }
/*     */   
/*     */   public static Icon getMenuArrowIcon() {
/*  78 */     if (menuArrowIcon == null) {
/*  79 */       menuArrowIcon = new MenuArrowIcon();
/*     */     }
/*  81 */     return menuArrowIcon;
/*     */   }
/*     */   
/*     */   public static Icon getCheckBoxIcon() {
/*  85 */     if (checkBoxIcon == null) {
/*  86 */       checkBoxIcon = new CheckBoxIcon();
/*     */     }
/*  88 */     return checkBoxIcon;
/*     */   }
/*     */   
/*     */   public static Icon getRadioButtonIcon() {
/*  92 */     if (radioButtonIcon == null) {
/*  93 */       radioButtonIcon = new RadioButtonIcon();
/*     */     }
/*  95 */     return radioButtonIcon;
/*     */   }
/*     */   
/*     */   public static Icon getCheckBoxMenuItemIcon() {
/*  99 */     if (checkBoxMenuItemIcon == null) {
/* 100 */       checkBoxMenuItemIcon = new CheckBoxMenuItemIcon();
/*     */     }
/* 102 */     return checkBoxMenuItemIcon;
/*     */   }
/*     */   
/*     */   public static Icon getRadioButtonMenuItemIcon() {
/* 106 */     if (radioButtonMenuItemIcon == null) {
/* 107 */       radioButtonMenuItemIcon = new RadioButtonMenuItemIcon();
/*     */     }
/* 109 */     return radioButtonMenuItemIcon;
/*     */   }
/*     */   
/*     */   public static Icon createEmptyFrameIcon() {
/* 113 */     if (frame_icon == null)
/* 114 */       frame_icon = new EmptyFrameIcon(); 
/* 115 */     return frame_icon;
/*     */   }
/*     */   
/*     */   private static class EmptyFrameIcon implements Icon, Serializable {
/* 119 */     int height = 16;
/* 120 */     int width = 14;
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*     */     
/* 123 */     public int getIconWidth() { return this.width; } public int getIconHeight() {
/* 124 */       return this.height;
/*     */     }
/*     */     
/*     */     private EmptyFrameIcon() {}
/*     */   }
/*     */   
/*     */   private static class CheckBoxIcon implements Icon, Serializable {
/*     */     static final int csize = 13;
/*     */     
/*     */     public int getIconWidth() {
/* 134 */       return 13;
/*     */     } private CheckBoxIcon() {}
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*     */     public int getIconHeight() {
/* 138 */       return 13;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RadioButtonIcon implements Icon, UIResource, Serializable {
/*     */     private RadioButtonIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*     */     
/*     */     public int getIconWidth() {
/* 148 */       return 13;
/*     */     }
/*     */     
/*     */     public int getIconHeight() {
/* 152 */       return 13;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CheckBoxMenuItemIcon implements Icon, UIResource, Serializable {
/*     */     private CheckBoxMenuItemIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 160 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 161 */       ButtonModel buttonModel = abstractButton.getModel();
/* 162 */       boolean bool = buttonModel.isSelected();
/* 163 */       if (bool) {
/* 164 */         param1Graphics.drawLine(param1Int1 + 7, param1Int2 + 1, param1Int1 + 7, param1Int2 + 3);
/* 165 */         param1Graphics.drawLine(param1Int1 + 6, param1Int2 + 2, param1Int1 + 6, param1Int2 + 4);
/* 166 */         param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 3, param1Int1 + 5, param1Int2 + 5);
/* 167 */         param1Graphics.drawLine(param1Int1 + 4, param1Int2 + 4, param1Int1 + 4, param1Int2 + 6);
/* 168 */         param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 5, param1Int1 + 3, param1Int2 + 7);
/* 169 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 4, param1Int1 + 2, param1Int2 + 6);
/* 170 */         param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 3, param1Int1 + 1, param1Int2 + 5);
/*     */       } 
/*     */     }
/* 173 */     public int getIconWidth() { return 9; } public int getIconHeight() {
/* 174 */       return 9;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RadioButtonMenuItemIcon implements Icon, UIResource, Serializable {
/*     */     private RadioButtonMenuItemIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 182 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 183 */       ButtonModel buttonModel = abstractButton.getModel();
/* 184 */       if (abstractButton.isSelected() == true)
/* 185 */         param1Graphics.fillOval(param1Int1 + 1, param1Int2 + 1, getIconWidth(), getIconHeight()); 
/*     */     }
/*     */     
/* 188 */     public int getIconWidth() { return 6; } public int getIconHeight() {
/* 189 */       return 6;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MenuItemCheckIcon implements Icon, UIResource, Serializable { private MenuItemCheckIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*     */     
/* 197 */     public int getIconWidth() { return 9; } public int getIconHeight() {
/* 198 */       return 9;
/*     */     } }
/*     */   
/*     */   private static class MenuItemArrowIcon implements Icon, UIResource, Serializable { private MenuItemArrowIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*     */     
/* 205 */     public int getIconWidth() { return 4; } public int getIconHeight() {
/* 206 */       return 8;
/*     */     } }
/*     */   
/*     */   private static class MenuArrowIcon implements Icon, UIResource, Serializable { private MenuArrowIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 212 */       Polygon polygon = new Polygon();
/* 213 */       polygon.addPoint(param1Int1, param1Int2);
/* 214 */       polygon.addPoint(param1Int1 + getIconWidth(), param1Int2 + getIconHeight() / 2);
/* 215 */       polygon.addPoint(param1Int1, param1Int2 + getIconHeight());
/* 216 */       param1Graphics.fillPolygon(polygon);
/*     */     }
/*     */     
/* 219 */     public int getIconWidth() { return 4; } public int getIconHeight() {
/* 220 */       return 8;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicIconFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */