/*      */ package javax.swing.plaf.metal;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.Image;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.Serializable;
/*      */ import java.util.Vector;
/*      */ import javax.swing.ButtonModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JRadioButton;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import sun.swing.CachedPainter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MetalIconFactory
/*      */   implements Serializable
/*      */ {
/*      */   private static Icon fileChooserDetailViewIcon;
/*      */   private static Icon fileChooserHomeFolderIcon;
/*      */   private static Icon fileChooserListViewIcon;
/*      */   private static Icon fileChooserNewFolderIcon;
/*      */   private static Icon fileChooserUpFolderIcon;
/*      */   private static Icon internalFrameAltMaximizeIcon;
/*      */   private static Icon internalFrameCloseIcon;
/*      */   private static Icon internalFrameDefaultMenuIcon;
/*      */   private static Icon internalFrameMaximizeIcon;
/*      */   private static Icon internalFrameMinimizeIcon;
/*      */   private static Icon radioButtonIcon;
/*      */   private static Icon treeComputerIcon;
/*      */   private static Icon treeFloppyDriveIcon;
/*      */   private static Icon treeHardDriveIcon;
/*      */   private static Icon menuArrowIcon;
/*      */   private static Icon menuItemArrowIcon;
/*      */   private static Icon checkBoxMenuItemIcon;
/*      */   private static Icon radioButtonMenuItemIcon;
/*      */   private static Icon checkBoxIcon;
/*      */   private static Icon oceanHorizontalSliderThumb;
/*      */   private static Icon oceanVerticalSliderThumb;
/*      */   public static final boolean DARK = false;
/*      */   public static final boolean LIGHT = true;
/*      */   
/*      */   public static Icon getFileChooserDetailViewIcon() {
/*   97 */     if (fileChooserDetailViewIcon == null) {
/*   98 */       fileChooserDetailViewIcon = new FileChooserDetailViewIcon();
/*      */     }
/*  100 */     return fileChooserDetailViewIcon;
/*      */   }
/*      */   
/*      */   public static Icon getFileChooserHomeFolderIcon() {
/*  104 */     if (fileChooserHomeFolderIcon == null) {
/*  105 */       fileChooserHomeFolderIcon = new FileChooserHomeFolderIcon();
/*      */     }
/*  107 */     return fileChooserHomeFolderIcon;
/*      */   }
/*      */   
/*      */   public static Icon getFileChooserListViewIcon() {
/*  111 */     if (fileChooserListViewIcon == null) {
/*  112 */       fileChooserListViewIcon = new FileChooserListViewIcon();
/*      */     }
/*  114 */     return fileChooserListViewIcon;
/*      */   }
/*      */   
/*      */   public static Icon getFileChooserNewFolderIcon() {
/*  118 */     if (fileChooserNewFolderIcon == null) {
/*  119 */       fileChooserNewFolderIcon = new FileChooserNewFolderIcon();
/*      */     }
/*  121 */     return fileChooserNewFolderIcon;
/*      */   }
/*      */   
/*      */   public static Icon getFileChooserUpFolderIcon() {
/*  125 */     if (fileChooserUpFolderIcon == null) {
/*  126 */       fileChooserUpFolderIcon = new FileChooserUpFolderIcon();
/*      */     }
/*  128 */     return fileChooserUpFolderIcon;
/*      */   }
/*      */   
/*      */   public static Icon getInternalFrameAltMaximizeIcon(int paramInt) {
/*  132 */     return new InternalFrameAltMaximizeIcon(paramInt);
/*      */   }
/*      */   
/*      */   public static Icon getInternalFrameCloseIcon(int paramInt) {
/*  136 */     return new InternalFrameCloseIcon(paramInt);
/*      */   }
/*      */   
/*      */   public static Icon getInternalFrameDefaultMenuIcon() {
/*  140 */     if (internalFrameDefaultMenuIcon == null) {
/*  141 */       internalFrameDefaultMenuIcon = new InternalFrameDefaultMenuIcon();
/*      */     }
/*  143 */     return internalFrameDefaultMenuIcon;
/*      */   }
/*      */   
/*      */   public static Icon getInternalFrameMaximizeIcon(int paramInt) {
/*  147 */     return new InternalFrameMaximizeIcon(paramInt);
/*      */   }
/*      */   
/*      */   public static Icon getInternalFrameMinimizeIcon(int paramInt) {
/*  151 */     return new InternalFrameMinimizeIcon(paramInt);
/*      */   }
/*      */   
/*      */   public static Icon getRadioButtonIcon() {
/*  155 */     if (radioButtonIcon == null) {
/*  156 */       radioButtonIcon = new RadioButtonIcon();
/*      */     }
/*  158 */     return radioButtonIcon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Icon getCheckBoxIcon() {
/*  166 */     if (checkBoxIcon == null) {
/*  167 */       checkBoxIcon = new CheckBoxIcon();
/*      */     }
/*  169 */     return checkBoxIcon;
/*      */   }
/*      */   
/*      */   public static Icon getTreeComputerIcon() {
/*  173 */     if (treeComputerIcon == null) {
/*  174 */       treeComputerIcon = new TreeComputerIcon();
/*      */     }
/*  176 */     return treeComputerIcon;
/*      */   }
/*      */   
/*      */   public static Icon getTreeFloppyDriveIcon() {
/*  180 */     if (treeFloppyDriveIcon == null) {
/*  181 */       treeFloppyDriveIcon = new TreeFloppyDriveIcon();
/*      */     }
/*  183 */     return treeFloppyDriveIcon;
/*      */   }
/*      */   
/*      */   public static Icon getTreeFolderIcon() {
/*  187 */     return new TreeFolderIcon();
/*      */   }
/*      */   
/*      */   public static Icon getTreeHardDriveIcon() {
/*  191 */     if (treeHardDriveIcon == null) {
/*  192 */       treeHardDriveIcon = new TreeHardDriveIcon();
/*      */     }
/*  194 */     return treeHardDriveIcon;
/*      */   }
/*      */   
/*      */   public static Icon getTreeLeafIcon() {
/*  198 */     return new TreeLeafIcon();
/*      */   }
/*      */   
/*      */   public static Icon getTreeControlIcon(boolean paramBoolean) {
/*  202 */     return new TreeControlIcon(paramBoolean);
/*      */   }
/*      */   
/*      */   public static Icon getMenuArrowIcon() {
/*  206 */     if (menuArrowIcon == null) {
/*  207 */       menuArrowIcon = new MenuArrowIcon();
/*      */     }
/*  209 */     return menuArrowIcon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Icon getMenuItemCheckIcon() {
/*  219 */     return null;
/*      */   }
/*      */   
/*      */   public static Icon getMenuItemArrowIcon() {
/*  223 */     if (menuItemArrowIcon == null) {
/*  224 */       menuItemArrowIcon = new MenuItemArrowIcon();
/*      */     }
/*  226 */     return menuItemArrowIcon;
/*      */   }
/*      */   
/*      */   public static Icon getCheckBoxMenuItemIcon() {
/*  230 */     if (checkBoxMenuItemIcon == null) {
/*  231 */       checkBoxMenuItemIcon = new CheckBoxMenuItemIcon();
/*      */     }
/*  233 */     return checkBoxMenuItemIcon;
/*      */   }
/*      */   
/*      */   public static Icon getRadioButtonMenuItemIcon() {
/*  237 */     if (radioButtonMenuItemIcon == null) {
/*  238 */       radioButtonMenuItemIcon = new RadioButtonMenuItemIcon();
/*      */     }
/*  240 */     return radioButtonMenuItemIcon;
/*      */   }
/*      */   
/*      */   public static Icon getHorizontalSliderThumbIcon() {
/*  244 */     if (MetalLookAndFeel.usingOcean()) {
/*  245 */       if (oceanHorizontalSliderThumb == null) {
/*  246 */         oceanHorizontalSliderThumb = new OceanHorizontalSliderThumbIcon();
/*      */       }
/*      */       
/*  249 */       return oceanHorizontalSliderThumb;
/*      */     } 
/*      */     
/*  252 */     return new HorizontalSliderThumbIcon();
/*      */   }
/*      */   
/*      */   public static Icon getVerticalSliderThumbIcon() {
/*  256 */     if (MetalLookAndFeel.usingOcean()) {
/*  257 */       if (oceanVerticalSliderThumb == null) {
/*  258 */         oceanVerticalSliderThumb = new OceanVerticalSliderThumbIcon();
/*      */       }
/*  260 */       return oceanVerticalSliderThumb;
/*      */     } 
/*      */     
/*  263 */     return new VerticalSliderThumbIcon();
/*      */   }
/*      */   
/*      */   private static class FileChooserDetailViewIcon implements Icon, UIResource, Serializable { private FileChooserDetailViewIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  269 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/*  272 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/*      */       
/*  274 */       param1Graphics.drawLine(2, 2, 5, 2);
/*  275 */       param1Graphics.drawLine(2, 3, 2, 7);
/*  276 */       param1Graphics.drawLine(3, 7, 6, 7);
/*  277 */       param1Graphics.drawLine(6, 6, 6, 3);
/*      */       
/*  279 */       param1Graphics.drawLine(2, 10, 5, 10);
/*  280 */       param1Graphics.drawLine(2, 11, 2, 15);
/*  281 */       param1Graphics.drawLine(3, 15, 6, 15);
/*  282 */       param1Graphics.drawLine(6, 14, 6, 11);
/*      */ 
/*      */ 
/*      */       
/*  286 */       param1Graphics.drawLine(8, 5, 15, 5);
/*  287 */       param1Graphics.drawLine(8, 13, 15, 13);
/*      */ 
/*      */       
/*  290 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/*  291 */       param1Graphics.drawRect(3, 3, 2, 3);
/*  292 */       param1Graphics.drawRect(3, 11, 2, 3);
/*      */ 
/*      */       
/*  295 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/*  296 */       param1Graphics.drawLine(4, 4, 4, 5);
/*  297 */       param1Graphics.drawLine(4, 12, 4, 13);
/*      */       
/*  299 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  303 */       return 18;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  307 */       return 18;
/*      */     } }
/*      */ 
/*      */   
/*      */   private static class FileChooserHomeFolderIcon implements Icon, UIResource, Serializable { private FileChooserHomeFolderIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  314 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/*  317 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/*  318 */       param1Graphics.drawLine(8, 1, 1, 8);
/*  319 */       param1Graphics.drawLine(8, 1, 15, 8);
/*  320 */       param1Graphics.drawLine(11, 2, 11, 3);
/*  321 */       param1Graphics.drawLine(12, 2, 12, 4);
/*  322 */       param1Graphics.drawLine(3, 7, 3, 15);
/*  323 */       param1Graphics.drawLine(13, 7, 13, 15);
/*  324 */       param1Graphics.drawLine(4, 15, 12, 15);
/*      */ 
/*      */       
/*  327 */       param1Graphics.drawLine(6, 9, 6, 14);
/*  328 */       param1Graphics.drawLine(10, 9, 10, 14);
/*  329 */       param1Graphics.drawLine(7, 9, 9, 9);
/*      */ 
/*      */       
/*  332 */       param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/*  333 */       param1Graphics.fillRect(8, 2, 1, 1);
/*  334 */       param1Graphics.fillRect(7, 3, 3, 1);
/*  335 */       param1Graphics.fillRect(6, 4, 5, 1);
/*  336 */       param1Graphics.fillRect(5, 5, 7, 1);
/*  337 */       param1Graphics.fillRect(4, 6, 9, 2);
/*      */ 
/*      */       
/*  340 */       param1Graphics.drawLine(9, 12, 9, 12);
/*      */ 
/*      */       
/*  343 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/*  344 */       param1Graphics.drawLine(4, 8, 12, 8);
/*  345 */       param1Graphics.fillRect(4, 9, 2, 6);
/*  346 */       param1Graphics.fillRect(11, 9, 2, 6);
/*      */       
/*  348 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  352 */       return 18;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  356 */       return 18;
/*      */     } }
/*      */   
/*      */   private static class FileChooserListViewIcon implements Icon, UIResource, Serializable {
/*      */     private FileChooserListViewIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  363 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/*  366 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/*      */       
/*  368 */       param1Graphics.drawLine(2, 2, 5, 2);
/*  369 */       param1Graphics.drawLine(2, 3, 2, 7);
/*  370 */       param1Graphics.drawLine(3, 7, 6, 7);
/*  371 */       param1Graphics.drawLine(6, 6, 6, 3);
/*      */       
/*  373 */       param1Graphics.drawLine(10, 2, 13, 2);
/*  374 */       param1Graphics.drawLine(10, 3, 10, 7);
/*  375 */       param1Graphics.drawLine(11, 7, 14, 7);
/*  376 */       param1Graphics.drawLine(14, 6, 14, 3);
/*      */       
/*  378 */       param1Graphics.drawLine(2, 10, 5, 10);
/*  379 */       param1Graphics.drawLine(2, 11, 2, 15);
/*  380 */       param1Graphics.drawLine(3, 15, 6, 15);
/*  381 */       param1Graphics.drawLine(6, 14, 6, 11);
/*      */       
/*  383 */       param1Graphics.drawLine(10, 10, 13, 10);
/*  384 */       param1Graphics.drawLine(10, 11, 10, 15);
/*  385 */       param1Graphics.drawLine(11, 15, 14, 15);
/*  386 */       param1Graphics.drawLine(14, 14, 14, 11);
/*      */ 
/*      */ 
/*      */       
/*  390 */       param1Graphics.drawLine(8, 5, 8, 5);
/*  391 */       param1Graphics.drawLine(16, 5, 16, 5);
/*  392 */       param1Graphics.drawLine(8, 13, 8, 13);
/*  393 */       param1Graphics.drawLine(16, 13, 16, 13);
/*      */ 
/*      */       
/*  396 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/*  397 */       param1Graphics.drawRect(3, 3, 2, 3);
/*  398 */       param1Graphics.drawRect(11, 3, 2, 3);
/*  399 */       param1Graphics.drawRect(3, 11, 2, 3);
/*  400 */       param1Graphics.drawRect(11, 11, 2, 3);
/*      */ 
/*      */       
/*  403 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/*  404 */       param1Graphics.drawLine(4, 4, 4, 5);
/*  405 */       param1Graphics.drawLine(12, 4, 12, 5);
/*  406 */       param1Graphics.drawLine(4, 12, 4, 13);
/*  407 */       param1Graphics.drawLine(12, 12, 12, 13);
/*      */       
/*  409 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  413 */       return 18;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  417 */       return 18;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FileChooserNewFolderIcon implements Icon, UIResource, Serializable { private FileChooserNewFolderIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  424 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/*  427 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/*  428 */       param1Graphics.fillRect(3, 5, 12, 9);
/*      */ 
/*      */       
/*  431 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/*  432 */       param1Graphics.drawLine(1, 6, 1, 14);
/*  433 */       param1Graphics.drawLine(2, 14, 15, 14);
/*  434 */       param1Graphics.drawLine(15, 13, 15, 5);
/*  435 */       param1Graphics.drawLine(2, 5, 9, 5);
/*  436 */       param1Graphics.drawLine(10, 6, 14, 6);
/*      */ 
/*      */       
/*  439 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/*  440 */       param1Graphics.drawLine(2, 6, 2, 13);
/*  441 */       param1Graphics.drawLine(3, 6, 9, 6);
/*  442 */       param1Graphics.drawLine(10, 7, 14, 7);
/*      */ 
/*      */       
/*  445 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/*  446 */       param1Graphics.drawLine(11, 3, 15, 3);
/*  447 */       param1Graphics.drawLine(10, 4, 15, 4);
/*      */       
/*  449 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  453 */       return 18;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  457 */       return 18;
/*      */     } }
/*      */   
/*      */   private static class FileChooserUpFolderIcon implements Icon, UIResource, Serializable {
/*      */     private FileChooserUpFolderIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  464 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/*  467 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/*  468 */       param1Graphics.fillRect(3, 5, 12, 9);
/*      */ 
/*      */       
/*  471 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/*  472 */       param1Graphics.drawLine(1, 6, 1, 14);
/*  473 */       param1Graphics.drawLine(2, 14, 15, 14);
/*  474 */       param1Graphics.drawLine(15, 13, 15, 5);
/*  475 */       param1Graphics.drawLine(2, 5, 9, 5);
/*  476 */       param1Graphics.drawLine(10, 6, 14, 6);
/*      */ 
/*      */       
/*  479 */       param1Graphics.drawLine(8, 13, 8, 16);
/*  480 */       param1Graphics.drawLine(8, 9, 8, 9);
/*  481 */       param1Graphics.drawLine(7, 10, 9, 10);
/*  482 */       param1Graphics.drawLine(6, 11, 10, 11);
/*  483 */       param1Graphics.drawLine(5, 12, 11, 12);
/*      */ 
/*      */       
/*  486 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/*  487 */       param1Graphics.drawLine(2, 6, 2, 13);
/*  488 */       param1Graphics.drawLine(3, 6, 9, 6);
/*  489 */       param1Graphics.drawLine(10, 7, 14, 7);
/*      */ 
/*      */       
/*  492 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/*  493 */       param1Graphics.drawLine(11, 3, 15, 3);
/*  494 */       param1Graphics.drawLine(10, 4, 15, 4);
/*      */       
/*  496 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  500 */       return 18;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  504 */       return 18;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class PaletteCloseIcon
/*      */     implements Icon, UIResource, Serializable
/*      */   {
/*  514 */     int iconSize = 7;
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*      */       ColorUIResource colorUIResource1;
/*  517 */       JButton jButton = (JButton)param1Component;
/*  518 */       ButtonModel buttonModel = jButton.getModel();
/*      */ 
/*      */       
/*  521 */       ColorUIResource colorUIResource2 = MetalLookAndFeel.getPrimaryControlHighlight();
/*  522 */       ColorUIResource colorUIResource3 = MetalLookAndFeel.getPrimaryControlInfo();
/*  523 */       if (buttonModel.isPressed() && buttonModel.isArmed()) {
/*  524 */         colorUIResource1 = colorUIResource3;
/*      */       } else {
/*  526 */         colorUIResource1 = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*      */       } 
/*      */       
/*  529 */       param1Graphics.translate(param1Int1, param1Int2);
/*  530 */       param1Graphics.setColor(colorUIResource1);
/*  531 */       param1Graphics.drawLine(0, 1, 5, 6);
/*  532 */       param1Graphics.drawLine(1, 0, 6, 5);
/*  533 */       param1Graphics.drawLine(1, 1, 6, 6);
/*  534 */       param1Graphics.drawLine(6, 1, 1, 6);
/*  535 */       param1Graphics.drawLine(5, 0, 0, 5);
/*  536 */       param1Graphics.drawLine(5, 1, 1, 5);
/*      */       
/*  538 */       param1Graphics.setColor(colorUIResource2);
/*  539 */       param1Graphics.drawLine(6, 2, 5, 3);
/*  540 */       param1Graphics.drawLine(2, 6, 3, 5);
/*  541 */       param1Graphics.drawLine(6, 6, 6, 6);
/*      */ 
/*      */       
/*  544 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  548 */       return this.iconSize;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  552 */       return this.iconSize;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class InternalFrameCloseIcon
/*      */     implements Icon, UIResource, Serializable {
/*  558 */     int iconSize = 16;
/*      */     
/*      */     public InternalFrameCloseIcon(int param1Int) {
/*  561 */       this.iconSize = param1Int;
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  565 */       JButton jButton = (JButton)param1Component;
/*  566 */       ButtonModel buttonModel = jButton.getModel();
/*      */       
/*  568 */       ColorUIResource colorUIResource1 = MetalLookAndFeel.getPrimaryControl();
/*      */       
/*  570 */       ColorUIResource colorUIResource2 = MetalLookAndFeel.getPrimaryControl();
/*      */       
/*  572 */       ColorUIResource colorUIResource3 = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*  573 */       ColorUIResource colorUIResource4 = MetalLookAndFeel.getBlack();
/*  574 */       ColorUIResource colorUIResource5 = MetalLookAndFeel.getWhite();
/*  575 */       ColorUIResource colorUIResource6 = MetalLookAndFeel.getWhite();
/*      */ 
/*      */       
/*  578 */       if (jButton.getClientProperty("paintActive") != Boolean.TRUE) {
/*      */         
/*  580 */         colorUIResource1 = MetalLookAndFeel.getControl();
/*  581 */         colorUIResource2 = colorUIResource1;
/*  582 */         colorUIResource3 = MetalLookAndFeel.getControlDarkShadow();
/*      */         
/*  584 */         if (buttonModel.isPressed() && buttonModel.isArmed())
/*      */         {
/*  586 */           colorUIResource2 = MetalLookAndFeel.getControlShadow();
/*  587 */           colorUIResource5 = colorUIResource2;
/*  588 */           colorUIResource3 = colorUIResource4;
/*      */         }
/*      */       
/*      */       }
/*  592 */       else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/*      */         
/*  594 */         colorUIResource2 = MetalLookAndFeel.getPrimaryControlShadow();
/*  595 */         colorUIResource5 = colorUIResource2;
/*  596 */         colorUIResource3 = colorUIResource4;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  601 */       int i = this.iconSize / 2;
/*      */       
/*  603 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/*  606 */       param1Graphics.setColor(colorUIResource1);
/*  607 */       param1Graphics.fillRect(0, 0, this.iconSize, this.iconSize);
/*      */ 
/*      */       
/*  610 */       param1Graphics.setColor(colorUIResource2);
/*  611 */       param1Graphics.fillRect(3, 3, this.iconSize - 6, this.iconSize - 6);
/*      */ 
/*      */ 
/*      */       
/*  615 */       param1Graphics.setColor(colorUIResource4);
/*  616 */       param1Graphics.drawRect(1, 1, this.iconSize - 3, this.iconSize - 3);
/*      */       
/*  618 */       param1Graphics.drawRect(2, 2, this.iconSize - 5, this.iconSize - 5);
/*      */       
/*  620 */       param1Graphics.setColor(colorUIResource6);
/*  621 */       param1Graphics.drawRect(2, 2, this.iconSize - 3, this.iconSize - 3);
/*      */       
/*  623 */       param1Graphics.setColor(colorUIResource3);
/*  624 */       param1Graphics.drawRect(2, 2, this.iconSize - 4, this.iconSize - 4);
/*  625 */       param1Graphics.drawLine(3, this.iconSize - 3, 3, this.iconSize - 3);
/*  626 */       param1Graphics.drawLine(this.iconSize - 3, 3, this.iconSize - 3, 3);
/*      */ 
/*      */ 
/*      */       
/*  630 */       param1Graphics.setColor(colorUIResource4);
/*  631 */       param1Graphics.drawLine(4, 5, 5, 4);
/*  632 */       param1Graphics.drawLine(4, this.iconSize - 6, this.iconSize - 6, 4);
/*      */       
/*  634 */       param1Graphics.setColor(colorUIResource5);
/*  635 */       param1Graphics.drawLine(6, this.iconSize - 5, this.iconSize - 5, 6);
/*      */       
/*  637 */       param1Graphics.drawLine(i, i + 2, i + 2, i);
/*      */       
/*  639 */       param1Graphics.drawLine(this.iconSize - 5, this.iconSize - 5, this.iconSize - 4, this.iconSize - 5);
/*  640 */       param1Graphics.drawLine(this.iconSize - 5, this.iconSize - 4, this.iconSize - 5, this.iconSize - 4);
/*      */       
/*  642 */       param1Graphics.setColor(colorUIResource3);
/*      */       
/*  644 */       param1Graphics.drawLine(5, 5, this.iconSize - 6, this.iconSize - 6);
/*  645 */       param1Graphics.drawLine(6, 5, this.iconSize - 5, this.iconSize - 6);
/*  646 */       param1Graphics.drawLine(5, 6, this.iconSize - 6, this.iconSize - 5);
/*      */       
/*  648 */       param1Graphics.drawLine(5, this.iconSize - 5, this.iconSize - 5, 5);
/*  649 */       param1Graphics.drawLine(5, this.iconSize - 6, this.iconSize - 6, 5);
/*      */       
/*  651 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  655 */       return this.iconSize;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  659 */       return this.iconSize;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class InternalFrameAltMaximizeIcon
/*      */     implements Icon, UIResource, Serializable {
/*  665 */     int iconSize = 16;
/*      */     
/*      */     public InternalFrameAltMaximizeIcon(int param1Int) {
/*  668 */       this.iconSize = param1Int;
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  672 */       JButton jButton = (JButton)param1Component;
/*  673 */       ButtonModel buttonModel = jButton.getModel();
/*      */       
/*  675 */       ColorUIResource colorUIResource1 = MetalLookAndFeel.getPrimaryControl();
/*      */       
/*  677 */       ColorUIResource colorUIResource2 = MetalLookAndFeel.getPrimaryControl();
/*      */       
/*  679 */       ColorUIResource colorUIResource3 = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*  680 */       ColorUIResource colorUIResource4 = MetalLookAndFeel.getBlack();
/*      */       
/*  682 */       ColorUIResource colorUIResource5 = MetalLookAndFeel.getWhite();
/*  683 */       ColorUIResource colorUIResource6 = MetalLookAndFeel.getWhite();
/*      */ 
/*      */       
/*  686 */       if (jButton.getClientProperty("paintActive") != Boolean.TRUE) {
/*      */         
/*  688 */         colorUIResource1 = MetalLookAndFeel.getControl();
/*  689 */         colorUIResource2 = colorUIResource1;
/*  690 */         colorUIResource3 = MetalLookAndFeel.getControlDarkShadow();
/*      */         
/*  692 */         if (buttonModel.isPressed() && buttonModel.isArmed())
/*      */         {
/*  694 */           colorUIResource2 = MetalLookAndFeel.getControlShadow();
/*  695 */           colorUIResource5 = colorUIResource2;
/*  696 */           colorUIResource3 = colorUIResource4;
/*      */         }
/*      */       
/*      */       }
/*  700 */       else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/*      */         
/*  702 */         colorUIResource2 = MetalLookAndFeel.getPrimaryControlShadow();
/*  703 */         colorUIResource5 = colorUIResource2;
/*  704 */         colorUIResource3 = colorUIResource4;
/*      */       } 
/*      */ 
/*      */       
/*  708 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/*  711 */       param1Graphics.setColor(colorUIResource1);
/*  712 */       param1Graphics.fillRect(0, 0, this.iconSize, this.iconSize);
/*      */ 
/*      */ 
/*      */       
/*  716 */       param1Graphics.setColor(colorUIResource2);
/*  717 */       param1Graphics.fillRect(3, 6, this.iconSize - 9, this.iconSize - 9);
/*      */ 
/*      */       
/*  720 */       param1Graphics.setColor(colorUIResource4);
/*  721 */       param1Graphics.drawRect(1, 5, this.iconSize - 8, this.iconSize - 8);
/*  722 */       param1Graphics.drawLine(1, this.iconSize - 2, 1, this.iconSize - 2);
/*      */ 
/*      */       
/*  725 */       param1Graphics.setColor(colorUIResource6);
/*  726 */       param1Graphics.drawRect(2, 6, this.iconSize - 7, this.iconSize - 7);
/*      */       
/*  728 */       param1Graphics.setColor(colorUIResource5);
/*  729 */       param1Graphics.drawRect(3, 7, this.iconSize - 9, this.iconSize - 9);
/*      */ 
/*      */       
/*  732 */       param1Graphics.setColor(colorUIResource3);
/*  733 */       param1Graphics.drawRect(2, 6, this.iconSize - 8, this.iconSize - 8);
/*      */ 
/*      */       
/*  736 */       param1Graphics.setColor(colorUIResource5);
/*  737 */       param1Graphics.drawLine(this.iconSize - 6, 8, this.iconSize - 6, 8);
/*  738 */       param1Graphics.drawLine(this.iconSize - 9, 6, this.iconSize - 7, 8);
/*  739 */       param1Graphics.setColor(colorUIResource3);
/*  740 */       param1Graphics.drawLine(3, this.iconSize - 3, 3, this.iconSize - 3);
/*  741 */       param1Graphics.setColor(colorUIResource4);
/*  742 */       param1Graphics.drawLine(this.iconSize - 6, 9, this.iconSize - 6, 9);
/*  743 */       param1Graphics.setColor(colorUIResource1);
/*  744 */       param1Graphics.drawLine(this.iconSize - 9, 5, this.iconSize - 9, 5);
/*      */ 
/*      */ 
/*      */       
/*  748 */       param1Graphics.setColor(colorUIResource3);
/*  749 */       param1Graphics.fillRect(this.iconSize - 7, 3, 3, 5);
/*  750 */       param1Graphics.drawLine(this.iconSize - 6, 5, this.iconSize - 3, 2);
/*  751 */       param1Graphics.drawLine(this.iconSize - 6, 6, this.iconSize - 2, 2);
/*  752 */       param1Graphics.drawLine(this.iconSize - 6, 7, this.iconSize - 3, 7);
/*      */ 
/*      */       
/*  755 */       param1Graphics.setColor(colorUIResource4);
/*  756 */       param1Graphics.drawLine(this.iconSize - 8, 2, this.iconSize - 7, 2);
/*  757 */       param1Graphics.drawLine(this.iconSize - 8, 3, this.iconSize - 8, 7);
/*  758 */       param1Graphics.drawLine(this.iconSize - 6, 4, this.iconSize - 3, 1);
/*  759 */       param1Graphics.drawLine(this.iconSize - 4, 6, this.iconSize - 3, 6);
/*      */ 
/*      */       
/*  762 */       param1Graphics.setColor(colorUIResource6);
/*  763 */       param1Graphics.drawLine(this.iconSize - 6, 3, this.iconSize - 6, 3);
/*  764 */       param1Graphics.drawLine(this.iconSize - 4, 5, this.iconSize - 2, 3);
/*  765 */       param1Graphics.drawLine(this.iconSize - 4, 8, this.iconSize - 3, 8);
/*  766 */       param1Graphics.drawLine(this.iconSize - 2, 8, this.iconSize - 2, 7);
/*      */       
/*  768 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  772 */       return this.iconSize;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  776 */       return this.iconSize;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class InternalFrameDefaultMenuIcon implements Icon, UIResource, Serializable {
/*      */     private InternalFrameDefaultMenuIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  784 */       ColorUIResource colorUIResource1 = MetalLookAndFeel.getWindowBackground();
/*  785 */       ColorUIResource colorUIResource2 = MetalLookAndFeel.getPrimaryControl();
/*  786 */       ColorUIResource colorUIResource3 = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*      */       
/*  788 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */ 
/*      */       
/*  792 */       param1Graphics.setColor(colorUIResource2);
/*  793 */       param1Graphics.fillRect(0, 0, 16, 16);
/*      */ 
/*      */       
/*  796 */       param1Graphics.setColor(colorUIResource1);
/*  797 */       param1Graphics.fillRect(2, 6, 13, 9);
/*      */       
/*  799 */       param1Graphics.drawLine(2, 2, 2, 2);
/*  800 */       param1Graphics.drawLine(5, 2, 5, 2);
/*  801 */       param1Graphics.drawLine(8, 2, 8, 2);
/*  802 */       param1Graphics.drawLine(11, 2, 11, 2);
/*      */ 
/*      */       
/*  805 */       param1Graphics.setColor(colorUIResource3);
/*  806 */       param1Graphics.drawRect(1, 1, 13, 13);
/*  807 */       param1Graphics.drawLine(1, 0, 14, 0);
/*  808 */       param1Graphics.drawLine(15, 1, 15, 14);
/*  809 */       param1Graphics.drawLine(1, 15, 14, 15);
/*  810 */       param1Graphics.drawLine(0, 1, 0, 14);
/*  811 */       param1Graphics.drawLine(2, 5, 13, 5);
/*      */       
/*  813 */       param1Graphics.drawLine(3, 3, 3, 3);
/*  814 */       param1Graphics.drawLine(6, 3, 6, 3);
/*  815 */       param1Graphics.drawLine(9, 3, 9, 3);
/*  816 */       param1Graphics.drawLine(12, 3, 12, 3);
/*      */       
/*  818 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  822 */       return 16;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  826 */       return 16;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class InternalFrameMaximizeIcon
/*      */     implements Icon, UIResource, Serializable {
/*  832 */     protected int iconSize = 16;
/*      */     
/*      */     public InternalFrameMaximizeIcon(int param1Int) {
/*  835 */       this.iconSize = param1Int;
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  839 */       JButton jButton = (JButton)param1Component;
/*  840 */       ButtonModel buttonModel = jButton.getModel();
/*      */       
/*  842 */       ColorUIResource colorUIResource1 = MetalLookAndFeel.getPrimaryControl();
/*      */       
/*  844 */       ColorUIResource colorUIResource2 = MetalLookAndFeel.getPrimaryControl();
/*      */       
/*  846 */       ColorUIResource colorUIResource3 = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*  847 */       ColorUIResource colorUIResource4 = MetalLookAndFeel.getBlack();
/*      */       
/*  849 */       ColorUIResource colorUIResource5 = MetalLookAndFeel.getWhite();
/*  850 */       ColorUIResource colorUIResource6 = MetalLookAndFeel.getWhite();
/*      */ 
/*      */       
/*  853 */       if (jButton.getClientProperty("paintActive") != Boolean.TRUE) {
/*      */         
/*  855 */         colorUIResource1 = MetalLookAndFeel.getControl();
/*  856 */         colorUIResource2 = colorUIResource1;
/*  857 */         colorUIResource3 = MetalLookAndFeel.getControlDarkShadow();
/*      */         
/*  859 */         if (buttonModel.isPressed() && buttonModel.isArmed())
/*      */         {
/*  861 */           colorUIResource2 = MetalLookAndFeel.getControlShadow();
/*  862 */           colorUIResource5 = colorUIResource2;
/*  863 */           colorUIResource3 = colorUIResource4;
/*      */         }
/*      */       
/*      */       }
/*  867 */       else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/*      */         
/*  869 */         colorUIResource2 = MetalLookAndFeel.getPrimaryControlShadow();
/*  870 */         colorUIResource5 = colorUIResource2;
/*  871 */         colorUIResource3 = colorUIResource4;
/*      */       } 
/*      */ 
/*      */       
/*  875 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/*  878 */       param1Graphics.setColor(colorUIResource1);
/*  879 */       param1Graphics.fillRect(0, 0, this.iconSize, this.iconSize);
/*      */ 
/*      */ 
/*      */       
/*  883 */       param1Graphics.setColor(colorUIResource2);
/*  884 */       param1Graphics.fillRect(3, 7, this.iconSize - 10, this.iconSize - 10);
/*      */ 
/*      */       
/*  887 */       param1Graphics.setColor(colorUIResource5);
/*  888 */       param1Graphics.drawRect(3, 7, this.iconSize - 10, this.iconSize - 10);
/*  889 */       param1Graphics.setColor(colorUIResource6);
/*  890 */       param1Graphics.drawRect(2, 6, this.iconSize - 7, this.iconSize - 7);
/*      */       
/*  892 */       param1Graphics.setColor(colorUIResource4);
/*  893 */       param1Graphics.drawRect(1, 5, this.iconSize - 7, this.iconSize - 7);
/*  894 */       param1Graphics.drawRect(2, 6, this.iconSize - 9, this.iconSize - 9);
/*      */       
/*  896 */       param1Graphics.setColor(colorUIResource3);
/*  897 */       param1Graphics.drawRect(2, 6, this.iconSize - 8, this.iconSize - 8);
/*      */ 
/*      */ 
/*      */       
/*  901 */       param1Graphics.setColor(colorUIResource4);
/*      */       
/*  903 */       param1Graphics.drawLine(3, this.iconSize - 5, this.iconSize - 9, 7);
/*      */       
/*  905 */       param1Graphics.drawLine(this.iconSize - 6, 4, this.iconSize - 5, 3);
/*      */       
/*  907 */       param1Graphics.drawLine(this.iconSize - 7, 1, this.iconSize - 7, 2);
/*      */       
/*  909 */       param1Graphics.drawLine(this.iconSize - 6, 1, this.iconSize - 2, 1);
/*      */       
/*  911 */       param1Graphics.setColor(colorUIResource5);
/*      */       
/*  913 */       param1Graphics.drawLine(5, this.iconSize - 4, this.iconSize - 8, 9);
/*  914 */       param1Graphics.setColor(colorUIResource6);
/*  915 */       param1Graphics.drawLine(this.iconSize - 6, 3, this.iconSize - 4, 5);
/*  916 */       param1Graphics.drawLine(this.iconSize - 4, 5, this.iconSize - 4, 6);
/*  917 */       param1Graphics.drawLine(this.iconSize - 2, 7, this.iconSize - 1, 7);
/*  918 */       param1Graphics.drawLine(this.iconSize - 1, 2, this.iconSize - 1, 6);
/*      */       
/*  920 */       param1Graphics.setColor(colorUIResource3);
/*  921 */       param1Graphics.drawLine(3, this.iconSize - 4, this.iconSize - 3, 2);
/*  922 */       param1Graphics.drawLine(3, this.iconSize - 3, this.iconSize - 2, 2);
/*  923 */       param1Graphics.drawLine(4, this.iconSize - 3, 5, this.iconSize - 3);
/*  924 */       param1Graphics.drawLine(this.iconSize - 7, 8, this.iconSize - 7, 9);
/*  925 */       param1Graphics.drawLine(this.iconSize - 6, 2, this.iconSize - 4, 2);
/*  926 */       param1Graphics.drawRect(this.iconSize - 3, 3, 1, 3);
/*      */       
/*  928 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  932 */       return this.iconSize;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  936 */       return this.iconSize;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class InternalFrameMinimizeIcon
/*      */     implements Icon, UIResource, Serializable {
/*  942 */     int iconSize = 16;
/*      */     
/*      */     public InternalFrameMinimizeIcon(int param1Int) {
/*  945 */       this.iconSize = param1Int;
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  949 */       JButton jButton = (JButton)param1Component;
/*  950 */       ButtonModel buttonModel = jButton.getModel();
/*      */ 
/*      */       
/*  953 */       ColorUIResource colorUIResource1 = MetalLookAndFeel.getPrimaryControl();
/*      */       
/*  955 */       ColorUIResource colorUIResource2 = MetalLookAndFeel.getPrimaryControl();
/*      */       
/*  957 */       ColorUIResource colorUIResource3 = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*  958 */       ColorUIResource colorUIResource4 = MetalLookAndFeel.getBlack();
/*      */       
/*  960 */       ColorUIResource colorUIResource5 = MetalLookAndFeel.getWhite();
/*  961 */       ColorUIResource colorUIResource6 = MetalLookAndFeel.getWhite();
/*      */ 
/*      */       
/*  964 */       if (jButton.getClientProperty("paintActive") != Boolean.TRUE) {
/*      */         
/*  966 */         colorUIResource1 = MetalLookAndFeel.getControl();
/*  967 */         colorUIResource2 = colorUIResource1;
/*  968 */         colorUIResource3 = MetalLookAndFeel.getControlDarkShadow();
/*      */         
/*  970 */         if (buttonModel.isPressed() && buttonModel.isArmed())
/*      */         {
/*  972 */           colorUIResource2 = MetalLookAndFeel.getControlShadow();
/*  973 */           colorUIResource5 = colorUIResource2;
/*  974 */           colorUIResource3 = colorUIResource4;
/*      */         }
/*      */       
/*      */       }
/*  978 */       else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/*      */         
/*  980 */         colorUIResource2 = MetalLookAndFeel.getPrimaryControlShadow();
/*  981 */         colorUIResource5 = colorUIResource2;
/*  982 */         colorUIResource3 = colorUIResource4;
/*      */       } 
/*      */ 
/*      */       
/*  986 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/*  989 */       param1Graphics.setColor(colorUIResource1);
/*  990 */       param1Graphics.fillRect(0, 0, this.iconSize, this.iconSize);
/*      */ 
/*      */ 
/*      */       
/*  994 */       param1Graphics.setColor(colorUIResource2);
/*  995 */       param1Graphics.fillRect(4, 11, this.iconSize - 13, this.iconSize - 13);
/*      */       
/*  997 */       param1Graphics.setColor(colorUIResource6);
/*  998 */       param1Graphics.drawRect(2, 10, this.iconSize - 10, this.iconSize - 11);
/*  999 */       param1Graphics.setColor(colorUIResource5);
/* 1000 */       param1Graphics.drawRect(3, 10, this.iconSize - 12, this.iconSize - 12);
/*      */       
/* 1002 */       param1Graphics.setColor(colorUIResource4);
/* 1003 */       param1Graphics.drawRect(1, 8, this.iconSize - 10, this.iconSize - 10);
/* 1004 */       param1Graphics.drawRect(2, 9, this.iconSize - 12, this.iconSize - 12);
/*      */       
/* 1006 */       param1Graphics.setColor(colorUIResource3);
/* 1007 */       param1Graphics.drawRect(2, 9, this.iconSize - 11, this.iconSize - 11);
/* 1008 */       param1Graphics.drawLine(this.iconSize - 10, 10, this.iconSize - 10, 10);
/* 1009 */       param1Graphics.drawLine(3, this.iconSize - 3, 3, this.iconSize - 3);
/*      */ 
/*      */ 
/*      */       
/* 1013 */       param1Graphics.setColor(colorUIResource3);
/* 1014 */       param1Graphics.fillRect(this.iconSize - 7, 3, 3, 5);
/* 1015 */       param1Graphics.drawLine(this.iconSize - 6, 5, this.iconSize - 3, 2);
/* 1016 */       param1Graphics.drawLine(this.iconSize - 6, 6, this.iconSize - 2, 2);
/* 1017 */       param1Graphics.drawLine(this.iconSize - 6, 7, this.iconSize - 3, 7);
/*      */ 
/*      */       
/* 1020 */       param1Graphics.setColor(colorUIResource4);
/* 1021 */       param1Graphics.drawLine(this.iconSize - 8, 2, this.iconSize - 7, 2);
/* 1022 */       param1Graphics.drawLine(this.iconSize - 8, 3, this.iconSize - 8, 7);
/* 1023 */       param1Graphics.drawLine(this.iconSize - 6, 4, this.iconSize - 3, 1);
/* 1024 */       param1Graphics.drawLine(this.iconSize - 4, 6, this.iconSize - 3, 6);
/*      */ 
/*      */       
/* 1027 */       param1Graphics.setColor(colorUIResource6);
/* 1028 */       param1Graphics.drawLine(this.iconSize - 6, 3, this.iconSize - 6, 3);
/* 1029 */       param1Graphics.drawLine(this.iconSize - 4, 5, this.iconSize - 2, 3);
/* 1030 */       param1Graphics.drawLine(this.iconSize - 7, 8, this.iconSize - 3, 8);
/* 1031 */       param1Graphics.drawLine(this.iconSize - 2, 8, this.iconSize - 2, 7);
/*      */       
/* 1033 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 1037 */       return this.iconSize;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 1041 */       return this.iconSize;
/*      */     } }
/*      */   
/*      */   private static class CheckBoxIcon implements Icon, UIResource, Serializable { private CheckBoxIcon() {}
/*      */     
/*      */     protected int getControlSize() {
/* 1047 */       return 13;
/*      */     }
/*      */     private void paintOceanIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1050 */       ButtonModel buttonModel = ((JCheckBox)param1Component).getModel();
/*      */       
/* 1052 */       param1Graphics.translate(param1Int1, param1Int2);
/* 1053 */       int i = getIconWidth();
/* 1054 */       int j = getIconHeight();
/* 1055 */       if (buttonModel.isEnabled()) {
/* 1056 */         if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 1057 */           param1Graphics.setColor(MetalLookAndFeel.getControlShadow());
/* 1058 */           param1Graphics.fillRect(0, 0, i, j);
/* 1059 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 1060 */           param1Graphics.fillRect(0, 0, i, 2);
/* 1061 */           param1Graphics.fillRect(0, 2, 2, j - 2);
/* 1062 */           param1Graphics.fillRect(i - 1, 1, 1, j - 1);
/* 1063 */           param1Graphics.fillRect(1, j - 1, i - 2, 1);
/* 1064 */         } else if (buttonModel.isRollover()) {
/* 1065 */           MetalUtils.drawGradient(param1Component, param1Graphics, "CheckBox.gradient", 0, 0, i, j, true);
/*      */           
/* 1067 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 1068 */           param1Graphics.drawRect(0, 0, i - 1, j - 1);
/* 1069 */           param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 1070 */           param1Graphics.drawRect(1, 1, i - 3, j - 3);
/* 1071 */           param1Graphics.drawRect(2, 2, i - 5, j - 5);
/*      */         } else {
/*      */           
/* 1074 */           MetalUtils.drawGradient(param1Component, param1Graphics, "CheckBox.gradient", 0, 0, i, j, true);
/*      */           
/* 1076 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 1077 */           param1Graphics.drawRect(0, 0, i - 1, j - 1);
/*      */         } 
/* 1079 */         param1Graphics.setColor(MetalLookAndFeel.getControlInfo());
/*      */       } else {
/* 1081 */         param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 1082 */         param1Graphics.drawRect(0, 0, i - 1, j - 1);
/*      */       } 
/* 1084 */       param1Graphics.translate(-param1Int1, -param1Int2);
/* 1085 */       if (buttonModel.isSelected()) {
/* 1086 */         drawCheck(param1Component, param1Graphics, param1Int1, param1Int2);
/*      */       }
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1091 */       if (MetalLookAndFeel.usingOcean()) {
/* 1092 */         paintOceanIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*      */         return;
/*      */       } 
/* 1095 */       ButtonModel buttonModel = ((JCheckBox)param1Component).getModel();
/* 1096 */       int i = getControlSize();
/*      */       
/* 1098 */       if (buttonModel.isEnabled()) {
/* 1099 */         if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 1100 */           param1Graphics.setColor(MetalLookAndFeel.getControlShadow());
/* 1101 */           param1Graphics.fillRect(param1Int1, param1Int2, i - 1, i - 1);
/* 1102 */           MetalUtils.drawPressed3DBorder(param1Graphics, param1Int1, param1Int2, i, i);
/*      */         } else {
/* 1104 */           MetalUtils.drawFlush3DBorder(param1Graphics, param1Int1, param1Int2, i, i);
/*      */         } 
/* 1106 */         param1Graphics.setColor(param1Component.getForeground());
/*      */       } else {
/* 1108 */         param1Graphics.setColor(MetalLookAndFeel.getControlShadow());
/* 1109 */         param1Graphics.drawRect(param1Int1, param1Int2, i - 2, i - 2);
/*      */       } 
/*      */       
/* 1112 */       if (buttonModel.isSelected()) {
/* 1113 */         drawCheck(param1Component, param1Graphics, param1Int1, param1Int2);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected void drawCheck(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1119 */       int i = getControlSize();
/* 1120 */       param1Graphics.fillRect(param1Int1 + 3, param1Int2 + 5, 2, i - 8);
/* 1121 */       param1Graphics.drawLine(param1Int1 + i - 4, param1Int2 + 3, param1Int1 + 5, param1Int2 + i - 6);
/* 1122 */       param1Graphics.drawLine(param1Int1 + i - 4, param1Int2 + 4, param1Int1 + 5, param1Int2 + i - 5);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 1126 */       return getControlSize();
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 1130 */       return getControlSize();
/*      */     } }
/*      */ 
/*      */   
/*      */   private static class RadioButtonIcon implements Icon, UIResource, Serializable { private RadioButtonIcon() {}
/*      */     
/*      */     public void paintOceanIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1137 */       ButtonModel buttonModel = ((JRadioButton)param1Component).getModel();
/* 1138 */       boolean bool = buttonModel.isEnabled();
/*      */       
/* 1140 */       boolean bool1 = (bool && buttonModel.isPressed() && buttonModel.isArmed()) ? true : false;
/* 1141 */       boolean bool2 = (bool && buttonModel.isRollover()) ? true : false;
/*      */       
/* 1143 */       param1Graphics.translate(param1Int1, param1Int2);
/* 1144 */       if (bool && !bool1) {
/*      */ 
/*      */         
/* 1147 */         MetalUtils.drawGradient(param1Component, param1Graphics, "RadioButton.gradient", 1, 1, 10, 10, true);
/*      */         
/* 1149 */         param1Graphics.setColor(param1Component.getBackground());
/* 1150 */         param1Graphics.fillRect(1, 1, 1, 1);
/* 1151 */         param1Graphics.fillRect(10, 1, 1, 1);
/* 1152 */         param1Graphics.fillRect(1, 10, 1, 1);
/* 1153 */         param1Graphics.fillRect(10, 10, 1, 1);
/*      */       }
/* 1155 */       else if (bool1 || !bool) {
/* 1156 */         if (bool1) {
/* 1157 */           param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/*      */         } else {
/*      */           
/* 1160 */           param1Graphics.setColor(MetalLookAndFeel.getControl());
/*      */         } 
/* 1162 */         param1Graphics.fillRect(2, 2, 8, 8);
/* 1163 */         param1Graphics.fillRect(4, 1, 4, 1);
/* 1164 */         param1Graphics.fillRect(4, 10, 4, 1);
/* 1165 */         param1Graphics.fillRect(1, 4, 1, 4);
/* 1166 */         param1Graphics.fillRect(10, 4, 1, 4);
/*      */       } 
/*      */ 
/*      */       
/* 1170 */       if (!bool) {
/* 1171 */         param1Graphics.setColor(MetalLookAndFeel.getInactiveControlTextColor());
/*      */       } else {
/*      */         
/* 1174 */         param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/*      */       } 
/* 1176 */       param1Graphics.drawLine(4, 0, 7, 0);
/* 1177 */       param1Graphics.drawLine(8, 1, 9, 1);
/* 1178 */       param1Graphics.drawLine(10, 2, 10, 3);
/* 1179 */       param1Graphics.drawLine(11, 4, 11, 7);
/* 1180 */       param1Graphics.drawLine(10, 8, 10, 9);
/* 1181 */       param1Graphics.drawLine(9, 10, 8, 10);
/* 1182 */       param1Graphics.drawLine(7, 11, 4, 11);
/* 1183 */       param1Graphics.drawLine(3, 10, 2, 10);
/* 1184 */       param1Graphics.drawLine(1, 9, 1, 8);
/* 1185 */       param1Graphics.drawLine(0, 7, 0, 4);
/* 1186 */       param1Graphics.drawLine(1, 3, 1, 2);
/* 1187 */       param1Graphics.drawLine(2, 1, 3, 1);
/*      */       
/* 1189 */       if (bool1) {
/* 1190 */         param1Graphics.fillRect(1, 4, 1, 4);
/* 1191 */         param1Graphics.fillRect(2, 2, 1, 2);
/* 1192 */         param1Graphics.fillRect(3, 2, 1, 1);
/* 1193 */         param1Graphics.fillRect(4, 1, 4, 1);
/*      */       }
/* 1195 */       else if (bool2) {
/* 1196 */         param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 1197 */         param1Graphics.fillRect(4, 1, 4, 2);
/* 1198 */         param1Graphics.fillRect(8, 2, 2, 2);
/* 1199 */         param1Graphics.fillRect(9, 4, 2, 4);
/* 1200 */         param1Graphics.fillRect(8, 8, 2, 2);
/* 1201 */         param1Graphics.fillRect(4, 9, 4, 2);
/* 1202 */         param1Graphics.fillRect(2, 8, 2, 2);
/* 1203 */         param1Graphics.fillRect(1, 4, 2, 4);
/* 1204 */         param1Graphics.fillRect(2, 2, 2, 2);
/*      */       } 
/*      */ 
/*      */       
/* 1208 */       if (buttonModel.isSelected()) {
/* 1209 */         if (bool) {
/* 1210 */           param1Graphics.setColor(MetalLookAndFeel.getControlInfo());
/*      */         } else {
/* 1212 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/*      */         } 
/* 1214 */         param1Graphics.fillRect(4, 4, 4, 4);
/* 1215 */         param1Graphics.drawLine(4, 3, 7, 3);
/* 1216 */         param1Graphics.drawLine(8, 4, 8, 7);
/* 1217 */         param1Graphics.drawLine(7, 8, 4, 8);
/* 1218 */         param1Graphics.drawLine(3, 7, 3, 4);
/*      */       } 
/*      */       
/* 1221 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1225 */       if (MetalLookAndFeel.usingOcean()) {
/* 1226 */         paintOceanIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*      */         return;
/*      */       } 
/* 1229 */       JRadioButton jRadioButton = (JRadioButton)param1Component;
/* 1230 */       ButtonModel buttonModel = jRadioButton.getModel();
/* 1231 */       boolean bool = buttonModel.isSelected();
/*      */       
/* 1233 */       Color color1 = param1Component.getBackground();
/* 1234 */       Color color2 = param1Component.getForeground();
/* 1235 */       ColorUIResource colorUIResource = MetalLookAndFeel.getControlShadow();
/* 1236 */       Color color3 = MetalLookAndFeel.getControlDarkShadow();
/* 1237 */       Color color4 = MetalLookAndFeel.getControlHighlight();
/* 1238 */       Color color5 = MetalLookAndFeel.getControlHighlight();
/* 1239 */       Color color6 = color1;
/*      */ 
/*      */       
/* 1242 */       if (!buttonModel.isEnabled()) {
/* 1243 */         Color color = color5 = color1;
/* 1244 */         color3 = color2 = colorUIResource;
/*      */       }
/* 1246 */       else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 1247 */         color4 = color6 = colorUIResource;
/*      */       } 
/*      */       
/* 1250 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/* 1253 */       param1Graphics.setColor(color6);
/* 1254 */       param1Graphics.fillRect(2, 2, 9, 9);
/*      */ 
/*      */       
/* 1257 */       param1Graphics.setColor(color3);
/* 1258 */       param1Graphics.drawLine(4, 0, 7, 0);
/* 1259 */       param1Graphics.drawLine(8, 1, 9, 1);
/* 1260 */       param1Graphics.drawLine(10, 2, 10, 3);
/* 1261 */       param1Graphics.drawLine(11, 4, 11, 7);
/* 1262 */       param1Graphics.drawLine(10, 8, 10, 9);
/* 1263 */       param1Graphics.drawLine(9, 10, 8, 10);
/* 1264 */       param1Graphics.drawLine(7, 11, 4, 11);
/* 1265 */       param1Graphics.drawLine(3, 10, 2, 10);
/* 1266 */       param1Graphics.drawLine(1, 9, 1, 8);
/* 1267 */       param1Graphics.drawLine(0, 7, 0, 4);
/* 1268 */       param1Graphics.drawLine(1, 3, 1, 2);
/* 1269 */       param1Graphics.drawLine(2, 1, 3, 1);
/*      */ 
/*      */ 
/*      */       
/* 1273 */       param1Graphics.setColor(color4);
/* 1274 */       param1Graphics.drawLine(2, 9, 2, 8);
/* 1275 */       param1Graphics.drawLine(1, 7, 1, 4);
/* 1276 */       param1Graphics.drawLine(2, 2, 2, 3);
/* 1277 */       param1Graphics.drawLine(2, 2, 3, 2);
/* 1278 */       param1Graphics.drawLine(4, 1, 7, 1);
/* 1279 */       param1Graphics.drawLine(8, 2, 9, 2);
/*      */ 
/*      */       
/* 1282 */       param1Graphics.setColor(color5);
/* 1283 */       param1Graphics.drawLine(10, 1, 10, 1);
/* 1284 */       param1Graphics.drawLine(11, 2, 11, 3);
/* 1285 */       param1Graphics.drawLine(12, 4, 12, 7);
/* 1286 */       param1Graphics.drawLine(11, 8, 11, 9);
/* 1287 */       param1Graphics.drawLine(10, 10, 10, 10);
/* 1288 */       param1Graphics.drawLine(9, 11, 8, 11);
/* 1289 */       param1Graphics.drawLine(7, 12, 4, 12);
/* 1290 */       param1Graphics.drawLine(3, 11, 2, 11);
/*      */ 
/*      */       
/* 1293 */       if (bool) {
/* 1294 */         param1Graphics.setColor(color2);
/* 1295 */         param1Graphics.fillRect(4, 4, 4, 4);
/* 1296 */         param1Graphics.drawLine(4, 3, 7, 3);
/* 1297 */         param1Graphics.drawLine(8, 4, 8, 7);
/* 1298 */         param1Graphics.drawLine(7, 8, 4, 8);
/* 1299 */         param1Graphics.drawLine(3, 7, 3, 4);
/*      */       } 
/*      */       
/* 1302 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 1306 */       return 13;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 1310 */       return 13;
/*      */     } }
/*      */   
/*      */   private static class TreeComputerIcon implements Icon, UIResource, Serializable {
/*      */     private TreeComputerIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1317 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/* 1320 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 1321 */       param1Graphics.fillRect(5, 4, 6, 4);
/*      */ 
/*      */       
/* 1324 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/* 1325 */       param1Graphics.drawLine(2, 2, 2, 8);
/* 1326 */       param1Graphics.drawLine(13, 2, 13, 8);
/* 1327 */       param1Graphics.drawLine(3, 1, 12, 1);
/* 1328 */       param1Graphics.drawLine(12, 9, 12, 9);
/* 1329 */       param1Graphics.drawLine(3, 9, 3, 9);
/*      */       
/* 1331 */       param1Graphics.drawLine(4, 4, 4, 7);
/* 1332 */       param1Graphics.drawLine(5, 3, 10, 3);
/* 1333 */       param1Graphics.drawLine(11, 4, 11, 7);
/* 1334 */       param1Graphics.drawLine(5, 8, 10, 8);
/*      */       
/* 1336 */       param1Graphics.drawLine(1, 10, 14, 10);
/* 1337 */       param1Graphics.drawLine(14, 10, 14, 14);
/* 1338 */       param1Graphics.drawLine(1, 14, 14, 14);
/* 1339 */       param1Graphics.drawLine(1, 10, 1, 14);
/*      */ 
/*      */       
/* 1342 */       param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 1343 */       param1Graphics.drawLine(6, 12, 8, 12);
/* 1344 */       param1Graphics.drawLine(10, 12, 12, 12);
/*      */       
/* 1346 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 1350 */       return 16;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 1354 */       return 16;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class TreeHardDriveIcon implements Icon, UIResource, Serializable { private TreeHardDriveIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1361 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/* 1364 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/*      */       
/* 1366 */       param1Graphics.drawLine(1, 4, 1, 5);
/* 1367 */       param1Graphics.drawLine(2, 3, 3, 3);
/* 1368 */       param1Graphics.drawLine(4, 2, 11, 2);
/* 1369 */       param1Graphics.drawLine(12, 3, 13, 3);
/* 1370 */       param1Graphics.drawLine(14, 4, 14, 5);
/* 1371 */       param1Graphics.drawLine(12, 6, 13, 6);
/* 1372 */       param1Graphics.drawLine(4, 7, 11, 7);
/* 1373 */       param1Graphics.drawLine(2, 6, 3, 6);
/*      */       
/* 1375 */       param1Graphics.drawLine(1, 7, 1, 8);
/* 1376 */       param1Graphics.drawLine(2, 9, 3, 9);
/* 1377 */       param1Graphics.drawLine(4, 10, 11, 10);
/* 1378 */       param1Graphics.drawLine(12, 9, 13, 9);
/* 1379 */       param1Graphics.drawLine(14, 7, 14, 8);
/*      */       
/* 1381 */       param1Graphics.drawLine(1, 10, 1, 11);
/* 1382 */       param1Graphics.drawLine(2, 12, 3, 12);
/* 1383 */       param1Graphics.drawLine(4, 13, 11, 13);
/* 1384 */       param1Graphics.drawLine(12, 12, 13, 12);
/* 1385 */       param1Graphics.drawLine(14, 10, 14, 11);
/*      */ 
/*      */       
/* 1388 */       param1Graphics.setColor(MetalLookAndFeel.getControlShadow());
/*      */       
/* 1390 */       param1Graphics.drawLine(7, 6, 7, 6);
/* 1391 */       param1Graphics.drawLine(9, 6, 9, 6);
/* 1392 */       param1Graphics.drawLine(10, 5, 10, 5);
/* 1393 */       param1Graphics.drawLine(11, 6, 11, 6);
/* 1394 */       param1Graphics.drawLine(12, 5, 13, 5);
/* 1395 */       param1Graphics.drawLine(13, 4, 13, 4);
/*      */       
/* 1397 */       param1Graphics.drawLine(7, 9, 7, 9);
/* 1398 */       param1Graphics.drawLine(9, 9, 9, 9);
/* 1399 */       param1Graphics.drawLine(10, 8, 10, 8);
/* 1400 */       param1Graphics.drawLine(11, 9, 11, 9);
/* 1401 */       param1Graphics.drawLine(12, 8, 13, 8);
/* 1402 */       param1Graphics.drawLine(13, 7, 13, 7);
/*      */       
/* 1404 */       param1Graphics.drawLine(7, 12, 7, 12);
/* 1405 */       param1Graphics.drawLine(9, 12, 9, 12);
/* 1406 */       param1Graphics.drawLine(10, 11, 10, 11);
/* 1407 */       param1Graphics.drawLine(11, 12, 11, 12);
/* 1408 */       param1Graphics.drawLine(12, 11, 13, 11);
/* 1409 */       param1Graphics.drawLine(13, 10, 13, 10);
/*      */ 
/*      */       
/* 1412 */       param1Graphics.setColor(MetalLookAndFeel.getControlHighlight());
/*      */       
/* 1414 */       param1Graphics.drawLine(4, 3, 5, 3);
/* 1415 */       param1Graphics.drawLine(7, 3, 9, 3);
/* 1416 */       param1Graphics.drawLine(11, 3, 11, 3);
/* 1417 */       param1Graphics.drawLine(2, 4, 6, 4);
/* 1418 */       param1Graphics.drawLine(8, 4, 8, 4);
/* 1419 */       param1Graphics.drawLine(2, 5, 3, 5);
/* 1420 */       param1Graphics.drawLine(4, 6, 4, 6);
/*      */       
/* 1422 */       param1Graphics.drawLine(2, 7, 3, 7);
/* 1423 */       param1Graphics.drawLine(2, 8, 3, 8);
/* 1424 */       param1Graphics.drawLine(4, 9, 4, 9);
/*      */       
/* 1426 */       param1Graphics.drawLine(2, 10, 3, 10);
/* 1427 */       param1Graphics.drawLine(2, 11, 3, 11);
/* 1428 */       param1Graphics.drawLine(4, 12, 4, 12);
/*      */       
/* 1430 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 1434 */       return 16;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 1438 */       return 16;
/*      */     } }
/*      */   
/*      */   private static class TreeFloppyDriveIcon implements Icon, UIResource, Serializable {
/*      */     private TreeFloppyDriveIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1445 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/* 1448 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 1449 */       param1Graphics.fillRect(2, 2, 12, 12);
/*      */ 
/*      */       
/* 1452 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/* 1453 */       param1Graphics.drawLine(1, 1, 13, 1);
/* 1454 */       param1Graphics.drawLine(14, 2, 14, 14);
/* 1455 */       param1Graphics.drawLine(1, 14, 14, 14);
/* 1456 */       param1Graphics.drawLine(1, 1, 1, 14);
/*      */ 
/*      */       
/* 1459 */       param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 1460 */       param1Graphics.fillRect(5, 2, 6, 5);
/* 1461 */       param1Graphics.drawLine(4, 8, 11, 8);
/* 1462 */       param1Graphics.drawLine(3, 9, 3, 13);
/* 1463 */       param1Graphics.drawLine(12, 9, 12, 13);
/*      */ 
/*      */       
/* 1466 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/* 1467 */       param1Graphics.fillRect(8, 3, 2, 3);
/* 1468 */       param1Graphics.fillRect(4, 9, 8, 5);
/*      */ 
/*      */       
/* 1471 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlShadow());
/* 1472 */       param1Graphics.drawLine(5, 10, 9, 10);
/* 1473 */       param1Graphics.drawLine(5, 12, 8, 12);
/*      */       
/* 1475 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 1479 */       return 16;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 1483 */       return 16;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 1488 */   private static final Dimension folderIcon16Size = new Dimension(16, 16);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class ImageCacher
/*      */   {
/* 1505 */     Vector<ImageGcPair> images = new Vector<>(1, 1);
/*      */     ImageGcPair currentImageGcPair;
/*      */     
/*      */     class ImageGcPair { Image image;
/*      */       GraphicsConfiguration gc;
/*      */       
/*      */       ImageGcPair(Image param2Image, GraphicsConfiguration param2GraphicsConfiguration) {
/* 1512 */         this.image = param2Image;
/* 1513 */         this.gc = param2GraphicsConfiguration;
/*      */       }
/*      */       
/*      */       boolean hasSameConfiguration(GraphicsConfiguration param2GraphicsConfiguration) {
/* 1517 */         return ((param2GraphicsConfiguration != null && param2GraphicsConfiguration.equals(this.gc)) || (param2GraphicsConfiguration == null && this.gc == null));
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Image getImage(GraphicsConfiguration param1GraphicsConfiguration) {
/* 1524 */       if (this.currentImageGcPair == null || 
/* 1525 */         !this.currentImageGcPair.hasSameConfiguration(param1GraphicsConfiguration)) {
/*      */         
/* 1527 */         for (ImageGcPair imageGcPair : this.images) {
/* 1528 */           if (imageGcPair.hasSameConfiguration(param1GraphicsConfiguration)) {
/* 1529 */             this.currentImageGcPair = imageGcPair;
/* 1530 */             return imageGcPair.image;
/*      */           } 
/*      */         } 
/* 1533 */         return null;
/*      */       } 
/* 1535 */       return this.currentImageGcPair.image;
/*      */     }
/*      */     
/*      */     void cacheImage(Image param1Image, GraphicsConfiguration param1GraphicsConfiguration) {
/* 1539 */       ImageGcPair imageGcPair = new ImageGcPair(param1Image, param1GraphicsConfiguration);
/* 1540 */       this.images.addElement(imageGcPair);
/* 1541 */       this.currentImageGcPair = imageGcPair;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class FolderIcon16
/*      */     implements Icon, Serializable
/*      */   {
/*      */     MetalIconFactory.ImageCacher imageCacher;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1562 */       GraphicsConfiguration graphicsConfiguration = param1Component.getGraphicsConfiguration();
/* 1563 */       if (this.imageCacher == null) {
/* 1564 */         this.imageCacher = new MetalIconFactory.ImageCacher();
/*      */       }
/* 1566 */       Image image = this.imageCacher.getImage(graphicsConfiguration);
/* 1567 */       if (image == null) {
/* 1568 */         if (graphicsConfiguration != null) {
/* 1569 */           image = graphicsConfiguration.createCompatibleImage(getIconWidth(), 
/* 1570 */               getIconHeight(), 2);
/*      */         }
/*      */         else {
/*      */           
/* 1574 */           image = new BufferedImage(getIconWidth(), getIconHeight(), 2);
/*      */         } 
/*      */         
/* 1577 */         Graphics graphics = image.getGraphics();
/* 1578 */         paintMe(param1Component, graphics);
/* 1579 */         graphics.dispose();
/* 1580 */         this.imageCacher.cacheImage(image, graphicsConfiguration);
/*      */       } 
/* 1582 */       param1Graphics.drawImage(image, param1Int1, param1Int2 + getShift(), null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void paintMe(Component param1Component, Graphics param1Graphics) {
/* 1588 */       int i = MetalIconFactory.folderIcon16Size.width - 1;
/* 1589 */       int j = MetalIconFactory.folderIcon16Size.height - 1;
/*      */ 
/*      */       
/* 1592 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 1593 */       param1Graphics.drawLine(i - 5, 3, i, 3);
/* 1594 */       param1Graphics.drawLine(i - 6, 4, i, 4);
/*      */ 
/*      */       
/* 1597 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 1598 */       param1Graphics.fillRect(2, 7, 13, 8);
/*      */ 
/*      */       
/* 1601 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlShadow());
/* 1602 */       param1Graphics.drawLine(i - 6, 5, i - 1, 5);
/*      */ 
/*      */       
/* 1605 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/* 1606 */       param1Graphics.drawLine(0, 6, 0, j);
/* 1607 */       param1Graphics.drawLine(1, 5, i - 7, 5);
/* 1608 */       param1Graphics.drawLine(i - 6, 6, i - 1, 6);
/* 1609 */       param1Graphics.drawLine(i, 5, i, j);
/* 1610 */       param1Graphics.drawLine(0, j, i, j);
/*      */ 
/*      */       
/* 1613 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/* 1614 */       param1Graphics.drawLine(1, 6, 1, j - 1);
/* 1615 */       param1Graphics.drawLine(1, 6, i - 7, 6);
/* 1616 */       param1Graphics.drawLine(i - 6, 7, i - 1, 7);
/*      */     }
/*      */     
/*      */     public int getShift() {
/* 1620 */       return 0; } public int getAdditionalHeight() {
/* 1621 */       return 0;
/*      */     }
/* 1623 */     public int getIconWidth() { return MetalIconFactory.folderIcon16Size.width; } public int getIconHeight() {
/* 1624 */       return MetalIconFactory.folderIcon16Size.height + getAdditionalHeight();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class TreeFolderIcon
/*      */     extends FolderIcon16
/*      */   {
/*      */     public int getShift() {
/* 1640 */       return -1; } public int getAdditionalHeight() {
/* 1641 */       return 2;
/*      */     }
/*      */   }
/*      */   
/* 1645 */   private static final Dimension fileIcon16Size = new Dimension(16, 16);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class FileIcon16
/*      */     implements Icon, Serializable
/*      */   {
/*      */     MetalIconFactory.ImageCacher imageCacher;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1663 */       GraphicsConfiguration graphicsConfiguration = param1Component.getGraphicsConfiguration();
/* 1664 */       if (this.imageCacher == null) {
/* 1665 */         this.imageCacher = new MetalIconFactory.ImageCacher();
/*      */       }
/* 1667 */       Image image = this.imageCacher.getImage(graphicsConfiguration);
/* 1668 */       if (image == null) {
/* 1669 */         if (graphicsConfiguration != null) {
/* 1670 */           image = graphicsConfiguration.createCompatibleImage(getIconWidth(), 
/* 1671 */               getIconHeight(), 2);
/*      */         }
/*      */         else {
/*      */           
/* 1675 */           image = new BufferedImage(getIconWidth(), getIconHeight(), 2);
/*      */         } 
/*      */         
/* 1678 */         Graphics graphics = image.getGraphics();
/* 1679 */         paintMe(param1Component, graphics);
/* 1680 */         graphics.dispose();
/* 1681 */         this.imageCacher.cacheImage(image, graphicsConfiguration);
/*      */       } 
/* 1683 */       param1Graphics.drawImage(image, param1Int1, param1Int2 + getShift(), null);
/*      */     }
/*      */ 
/*      */     
/*      */     private void paintMe(Component param1Component, Graphics param1Graphics) {
/* 1688 */       int i = MetalIconFactory.fileIcon16Size.width - 1;
/* 1689 */       int j = MetalIconFactory.fileIcon16Size.height - 1;
/*      */ 
/*      */       
/* 1692 */       param1Graphics.setColor(MetalLookAndFeel.getWindowBackground());
/* 1693 */       param1Graphics.fillRect(4, 2, 9, 12);
/*      */ 
/*      */       
/* 1696 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/* 1697 */       param1Graphics.drawLine(2, 0, 2, j);
/* 1698 */       param1Graphics.drawLine(2, 0, i - 4, 0);
/* 1699 */       param1Graphics.drawLine(2, j, i - 1, j);
/* 1700 */       param1Graphics.drawLine(i - 1, 6, i - 1, j);
/* 1701 */       param1Graphics.drawLine(i - 6, 2, i - 2, 6);
/* 1702 */       param1Graphics.drawLine(i - 5, 1, i - 4, 1);
/* 1703 */       param1Graphics.drawLine(i - 3, 2, i - 3, 3);
/* 1704 */       param1Graphics.drawLine(i - 2, 4, i - 2, 5);
/*      */ 
/*      */       
/* 1707 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 1708 */       param1Graphics.drawLine(3, 1, 3, j - 1);
/* 1709 */       param1Graphics.drawLine(3, 1, i - 6, 1);
/* 1710 */       param1Graphics.drawLine(i - 2, 7, i - 2, j - 1);
/* 1711 */       param1Graphics.drawLine(i - 5, 2, i - 3, 4);
/* 1712 */       param1Graphics.drawLine(3, j - 1, i - 2, j - 1);
/*      */     }
/*      */     
/*      */     public int getShift() {
/* 1716 */       return 0; } public int getAdditionalHeight() {
/* 1717 */       return 0;
/*      */     }
/* 1719 */     public int getIconWidth() { return MetalIconFactory.fileIcon16Size.width; } public int getIconHeight() {
/* 1720 */       return MetalIconFactory.fileIcon16Size.height + getAdditionalHeight();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class TreeLeafIcon extends FileIcon16 {
/* 1725 */     public int getShift() { return 2; } public int getAdditionalHeight() {
/* 1726 */       return 4;
/*      */     }
/*      */   }
/*      */   
/* 1730 */   private static final Dimension treeControlSize = new Dimension(18, 18);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class TreeControlIcon
/*      */     implements Icon, Serializable
/*      */   {
/*      */     protected boolean isLight;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MetalIconFactory.ImageCacher imageCacher;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     transient boolean cachedOrientation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TreeControlIcon(boolean param1Boolean) {
/* 1756 */       this.cachedOrientation = true;
/*      */       this.isLight = param1Boolean;
/*      */     }
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1760 */       GraphicsConfiguration graphicsConfiguration = param1Component.getGraphicsConfiguration();
/*      */       
/* 1762 */       if (this.imageCacher == null) {
/* 1763 */         this.imageCacher = new MetalIconFactory.ImageCacher();
/*      */       }
/* 1765 */       Image image = this.imageCacher.getImage(graphicsConfiguration);
/*      */       
/* 1767 */       if (image == null || this.cachedOrientation != MetalUtils.isLeftToRight(param1Component)) {
/* 1768 */         this.cachedOrientation = MetalUtils.isLeftToRight(param1Component);
/* 1769 */         if (graphicsConfiguration != null) {
/* 1770 */           image = graphicsConfiguration.createCompatibleImage(getIconWidth(), 
/* 1771 */               getIconHeight(), 2);
/*      */         }
/*      */         else {
/*      */           
/* 1775 */           image = new BufferedImage(getIconWidth(), getIconHeight(), 2);
/*      */         } 
/*      */         
/* 1778 */         Graphics graphics = image.getGraphics();
/* 1779 */         paintMe(param1Component, graphics, param1Int1, param1Int2);
/* 1780 */         graphics.dispose();
/* 1781 */         this.imageCacher.cacheImage(image, graphicsConfiguration);
/*      */       } 
/*      */ 
/*      */       
/* 1785 */       if (MetalUtils.isLeftToRight(param1Component)) {
/* 1786 */         if (this.isLight) {
/* 1787 */           param1Graphics.drawImage(image, param1Int1 + 5, param1Int2 + 3, param1Int1 + 18, param1Int2 + 13, 4, 3, 17, 13, null);
/*      */         }
/*      */         else {
/*      */           
/* 1791 */           param1Graphics.drawImage(image, param1Int1 + 5, param1Int2 + 3, param1Int1 + 18, param1Int2 + 17, 4, 3, 17, 17, null);
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1796 */       else if (this.isLight) {
/* 1797 */         param1Graphics.drawImage(image, param1Int1 + 3, param1Int2 + 3, param1Int1 + 16, param1Int2 + 13, 4, 3, 17, 13, null);
/*      */       }
/*      */       else {
/*      */         
/* 1801 */         param1Graphics.drawImage(image, param1Int1 + 3, param1Int2 + 3, param1Int1 + 16, param1Int2 + 17, 4, 3, 17, 17, null);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paintMe(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1809 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/*      */       
/* 1811 */       byte b = MetalUtils.isLeftToRight(param1Component) ? 0 : 4;
/*      */ 
/*      */       
/* 1814 */       param1Graphics.drawLine(b + 4, 6, b + 4, 9);
/* 1815 */       param1Graphics.drawLine(b + 5, 5, b + 5, 5);
/* 1816 */       param1Graphics.drawLine(b + 6, 4, b + 9, 4);
/* 1817 */       param1Graphics.drawLine(b + 10, 5, b + 10, 5);
/* 1818 */       param1Graphics.drawLine(b + 11, 6, b + 11, 9);
/* 1819 */       param1Graphics.drawLine(b + 10, 10, b + 10, 10);
/* 1820 */       param1Graphics.drawLine(b + 6, 11, b + 9, 11);
/* 1821 */       param1Graphics.drawLine(b + 5, 10, b + 5, 10);
/*      */ 
/*      */       
/* 1824 */       param1Graphics.drawLine(b + 7, 7, b + 8, 7);
/* 1825 */       param1Graphics.drawLine(b + 7, 8, b + 8, 8);
/*      */ 
/*      */       
/* 1828 */       if (this.isLight) {
/* 1829 */         if (MetalUtils.isLeftToRight(param1Component)) {
/* 1830 */           param1Graphics.drawLine(12, 7, 15, 7);
/* 1831 */           param1Graphics.drawLine(12, 8, 15, 8);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1836 */           param1Graphics.drawLine(4, 7, 7, 7);
/* 1837 */           param1Graphics.drawLine(4, 8, 7, 8);
/*      */         } 
/*      */       } else {
/*      */         
/* 1841 */         param1Graphics.drawLine(b + 7, 12, b + 7, 15);
/* 1842 */         param1Graphics.drawLine(b + 8, 12, b + 8, 15);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1848 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 1849 */       param1Graphics.drawLine(b + 5, 6, b + 5, 9);
/* 1850 */       param1Graphics.drawLine(b + 6, 5, b + 9, 5);
/*      */       
/* 1852 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlShadow());
/* 1853 */       param1Graphics.drawLine(b + 6, 6, b + 6, 6);
/* 1854 */       param1Graphics.drawLine(b + 9, 6, b + 9, 6);
/* 1855 */       param1Graphics.drawLine(b + 6, 9, b + 6, 9);
/* 1856 */       param1Graphics.drawLine(b + 10, 6, b + 10, 9);
/* 1857 */       param1Graphics.drawLine(b + 6, 10, b + 9, 10);
/*      */       
/* 1859 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 1860 */       param1Graphics.drawLine(b + 6, 7, b + 6, 8);
/* 1861 */       param1Graphics.drawLine(b + 7, 6, b + 8, 6);
/* 1862 */       param1Graphics.drawLine(b + 9, 7, b + 9, 7);
/* 1863 */       param1Graphics.drawLine(b + 7, 9, b + 7, 9);
/*      */       
/* 1865 */       param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlHighlight());
/* 1866 */       param1Graphics.drawLine(b + 8, 9, b + 9, 9);
/* 1867 */       param1Graphics.drawLine(b + 9, 8, b + 9, 8);
/*      */     }
/*      */     
/* 1870 */     public int getIconWidth() { return MetalIconFactory.treeControlSize.width; } public int getIconHeight() {
/* 1871 */       return MetalIconFactory.treeControlSize.height;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1878 */   private static final Dimension menuArrowIconSize = new Dimension(4, 8);
/* 1879 */   private static final Dimension menuCheckIconSize = new Dimension(10, 10);
/*      */   private static final int xOff = 4;
/*      */   
/*      */   private static class MenuArrowIcon implements Icon, UIResource, Serializable {
/*      */     private MenuArrowIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1886 */       JMenuItem jMenuItem = (JMenuItem)param1Component;
/* 1887 */       ButtonModel buttonModel = jMenuItem.getModel();
/*      */       
/* 1889 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */       
/* 1891 */       if (!buttonModel.isEnabled()) {
/*      */         
/* 1893 */         param1Graphics.setColor(MetalLookAndFeel.getMenuDisabledForeground());
/*      */ 
/*      */       
/*      */       }
/* 1897 */       else if (buttonModel.isArmed() || (param1Component instanceof javax.swing.JMenu && buttonModel.isSelected())) {
/*      */         
/* 1899 */         param1Graphics.setColor(MetalLookAndFeel.getMenuSelectedForeground());
/*      */       }
/*      */       else {
/*      */         
/* 1903 */         param1Graphics.setColor(jMenuItem.getForeground());
/*      */       } 
/*      */       
/* 1906 */       if (MetalUtils.isLeftToRight(jMenuItem)) {
/* 1907 */         param1Graphics.drawLine(0, 0, 0, 7);
/* 1908 */         param1Graphics.drawLine(1, 1, 1, 6);
/* 1909 */         param1Graphics.drawLine(2, 2, 2, 5);
/* 1910 */         param1Graphics.drawLine(3, 3, 3, 4);
/*      */       } else {
/* 1912 */         param1Graphics.drawLine(4, 0, 4, 7);
/* 1913 */         param1Graphics.drawLine(3, 1, 3, 6);
/* 1914 */         param1Graphics.drawLine(2, 2, 2, 5);
/* 1915 */         param1Graphics.drawLine(1, 3, 1, 4);
/*      */       } 
/*      */       
/* 1918 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     public int getIconWidth() {
/* 1921 */       return MetalIconFactory.menuArrowIconSize.width;
/*      */     } public int getIconHeight() {
/* 1923 */       return MetalIconFactory.menuArrowIconSize.height;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class MenuItemArrowIcon implements Icon, UIResource, Serializable {
/*      */     private MenuItemArrowIcon() {}
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*      */     
/*      */     public int getIconWidth() {
/* 1933 */       return MetalIconFactory.menuArrowIconSize.width;
/*      */     } public int getIconHeight() {
/* 1935 */       return MetalIconFactory.menuArrowIconSize.height;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class CheckBoxMenuItemIcon implements Icon, UIResource, Serializable { private CheckBoxMenuItemIcon() {}
/*      */     
/*      */     public void paintOceanIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1942 */       ButtonModel buttonModel = ((JMenuItem)param1Component).getModel();
/* 1943 */       boolean bool1 = buttonModel.isSelected();
/* 1944 */       boolean bool2 = buttonModel.isEnabled();
/* 1945 */       boolean bool3 = buttonModel.isPressed();
/* 1946 */       boolean bool4 = buttonModel.isArmed();
/*      */       
/* 1948 */       param1Graphics.translate(param1Int1, param1Int2);
/* 1949 */       if (bool2) {
/* 1950 */         MetalUtils.drawGradient(param1Component, param1Graphics, "CheckBoxMenuItem.gradient", 1, 1, 7, 7, true);
/*      */         
/* 1952 */         if (bool3 || bool4) {
/* 1953 */           param1Graphics.setColor(MetalLookAndFeel.getControlInfo());
/* 1954 */           param1Graphics.drawLine(0, 0, 8, 0);
/* 1955 */           param1Graphics.drawLine(0, 0, 0, 8);
/* 1956 */           param1Graphics.drawLine(8, 2, 8, 8);
/* 1957 */           param1Graphics.drawLine(2, 8, 8, 8);
/*      */           
/* 1959 */           param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 1960 */           param1Graphics.drawLine(9, 1, 9, 9);
/* 1961 */           param1Graphics.drawLine(1, 9, 9, 9);
/*      */         } else {
/*      */           
/* 1964 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 1965 */           param1Graphics.drawLine(0, 0, 8, 0);
/* 1966 */           param1Graphics.drawLine(0, 0, 0, 8);
/* 1967 */           param1Graphics.drawLine(8, 2, 8, 8);
/* 1968 */           param1Graphics.drawLine(2, 8, 8, 8);
/*      */           
/* 1970 */           param1Graphics.setColor(MetalLookAndFeel.getControlHighlight());
/* 1971 */           param1Graphics.drawLine(9, 1, 9, 9);
/* 1972 */           param1Graphics.drawLine(1, 9, 9, 9);
/*      */         } 
/*      */       } else {
/*      */         
/* 1976 */         param1Graphics.setColor(MetalLookAndFeel.getMenuDisabledForeground());
/* 1977 */         param1Graphics.drawRect(0, 0, 8, 8);
/*      */       } 
/* 1979 */       if (bool1) {
/* 1980 */         if (bool2) {
/* 1981 */           if (bool4 || (param1Component instanceof javax.swing.JMenu && bool1)) {
/* 1982 */             param1Graphics.setColor(
/* 1983 */                 MetalLookAndFeel.getMenuSelectedForeground());
/*      */           } else {
/*      */             
/* 1986 */             param1Graphics.setColor(MetalLookAndFeel.getControlInfo());
/*      */           } 
/*      */         } else {
/*      */           
/* 1990 */           param1Graphics.setColor(MetalLookAndFeel.getMenuDisabledForeground());
/*      */         } 
/*      */         
/* 1993 */         param1Graphics.drawLine(2, 2, 2, 6);
/* 1994 */         param1Graphics.drawLine(3, 2, 3, 6);
/* 1995 */         param1Graphics.drawLine(4, 4, 8, 0);
/* 1996 */         param1Graphics.drawLine(4, 5, 9, 0);
/*      */       } 
/* 1998 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 2003 */       if (MetalLookAndFeel.usingOcean()) {
/* 2004 */         paintOceanIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*      */         return;
/*      */       } 
/* 2007 */       JMenuItem jMenuItem = (JMenuItem)param1Component;
/* 2008 */       ButtonModel buttonModel = jMenuItem.getModel();
/*      */       
/* 2010 */       boolean bool1 = buttonModel.isSelected();
/* 2011 */       boolean bool2 = buttonModel.isEnabled();
/* 2012 */       boolean bool3 = buttonModel.isPressed();
/* 2013 */       boolean bool4 = buttonModel.isArmed();
/*      */       
/* 2015 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */       
/* 2017 */       if (bool2) {
/*      */         
/* 2019 */         if (bool3 || bool4)
/*      */         {
/* 2021 */           param1Graphics.setColor(MetalLookAndFeel.getControlInfo());
/* 2022 */           param1Graphics.drawLine(0, 0, 8, 0);
/* 2023 */           param1Graphics.drawLine(0, 0, 0, 8);
/* 2024 */           param1Graphics.drawLine(8, 2, 8, 8);
/* 2025 */           param1Graphics.drawLine(2, 8, 8, 8);
/*      */           
/* 2027 */           param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 2028 */           param1Graphics.drawLine(1, 1, 7, 1);
/* 2029 */           param1Graphics.drawLine(1, 1, 1, 7);
/* 2030 */           param1Graphics.drawLine(9, 1, 9, 9);
/* 2031 */           param1Graphics.drawLine(1, 9, 9, 9);
/*      */         }
/*      */         else
/*      */         {
/* 2035 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 2036 */           param1Graphics.drawLine(0, 0, 8, 0);
/* 2037 */           param1Graphics.drawLine(0, 0, 0, 8);
/* 2038 */           param1Graphics.drawLine(8, 2, 8, 8);
/* 2039 */           param1Graphics.drawLine(2, 8, 8, 8);
/*      */           
/* 2041 */           param1Graphics.setColor(MetalLookAndFeel.getControlHighlight());
/* 2042 */           param1Graphics.drawLine(1, 1, 7, 1);
/* 2043 */           param1Graphics.drawLine(1, 1, 1, 7);
/* 2044 */           param1Graphics.drawLine(9, 1, 9, 9);
/* 2045 */           param1Graphics.drawLine(1, 9, 9, 9);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2050 */         param1Graphics.setColor(MetalLookAndFeel.getMenuDisabledForeground());
/* 2051 */         param1Graphics.drawRect(0, 0, 8, 8);
/*      */       } 
/*      */       
/* 2054 */       if (bool1) {
/*      */         
/* 2056 */         if (bool2) {
/*      */           
/* 2058 */           if (buttonModel.isArmed() || (param1Component instanceof javax.swing.JMenu && buttonModel.isSelected()))
/*      */           {
/* 2060 */             param1Graphics.setColor(MetalLookAndFeel.getMenuSelectedForeground());
/*      */           }
/*      */           else
/*      */           {
/* 2064 */             param1Graphics.setColor(jMenuItem.getForeground());
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2069 */           param1Graphics.setColor(MetalLookAndFeel.getMenuDisabledForeground());
/*      */         } 
/*      */         
/* 2072 */         param1Graphics.drawLine(2, 2, 2, 6);
/* 2073 */         param1Graphics.drawLine(3, 2, 3, 6);
/* 2074 */         param1Graphics.drawLine(4, 4, 8, 0);
/* 2075 */         param1Graphics.drawLine(4, 5, 9, 0);
/*      */       } 
/*      */       
/* 2078 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     public int getIconWidth() {
/* 2081 */       return MetalIconFactory.menuCheckIconSize.width;
/*      */     } public int getIconHeight() {
/* 2083 */       return MetalIconFactory.menuCheckIconSize.height;
/*      */     } }
/*      */   
/*      */   private static class RadioButtonMenuItemIcon implements Icon, UIResource, Serializable {
/*      */     private RadioButtonMenuItemIcon() {}
/*      */     
/*      */     public void paintOceanIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 2090 */       ButtonModel buttonModel = ((JMenuItem)param1Component).getModel();
/* 2091 */       boolean bool1 = buttonModel.isSelected();
/* 2092 */       boolean bool2 = buttonModel.isEnabled();
/* 2093 */       boolean bool3 = buttonModel.isPressed();
/* 2094 */       boolean bool4 = buttonModel.isArmed();
/*      */       
/* 2096 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */       
/* 2098 */       if (bool2) {
/* 2099 */         MetalUtils.drawGradient(param1Component, param1Graphics, "RadioButtonMenuItem.gradient", 1, 1, 7, 7, true);
/*      */         
/* 2101 */         if (bool3 || bool4) {
/* 2102 */           param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/*      */         } else {
/*      */           
/* 2105 */           param1Graphics.setColor(MetalLookAndFeel.getControlHighlight());
/*      */         } 
/* 2107 */         param1Graphics.drawLine(2, 9, 7, 9);
/* 2108 */         param1Graphics.drawLine(9, 2, 9, 7);
/* 2109 */         param1Graphics.drawLine(8, 8, 8, 8);
/*      */         
/* 2111 */         if (bool3 || bool4) {
/* 2112 */           param1Graphics.setColor(MetalLookAndFeel.getControlInfo());
/*      */         } else {
/*      */           
/* 2115 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/*      */         } 
/*      */       } else {
/*      */         
/* 2119 */         param1Graphics.setColor(MetalLookAndFeel.getMenuDisabledForeground());
/*      */       } 
/* 2121 */       param1Graphics.drawLine(2, 0, 6, 0);
/* 2122 */       param1Graphics.drawLine(2, 8, 6, 8);
/* 2123 */       param1Graphics.drawLine(0, 2, 0, 6);
/* 2124 */       param1Graphics.drawLine(8, 2, 8, 6);
/* 2125 */       param1Graphics.drawLine(1, 1, 1, 1);
/* 2126 */       param1Graphics.drawLine(7, 1, 7, 1);
/* 2127 */       param1Graphics.drawLine(1, 7, 1, 7);
/* 2128 */       param1Graphics.drawLine(7, 7, 7, 7);
/*      */       
/* 2130 */       if (bool1) {
/* 2131 */         if (bool2) {
/* 2132 */           if (bool4 || (param1Component instanceof javax.swing.JMenu && buttonModel.isSelected())) {
/* 2133 */             param1Graphics.setColor(
/* 2134 */                 MetalLookAndFeel.getMenuSelectedForeground());
/*      */           } else {
/*      */             
/* 2137 */             param1Graphics.setColor(MetalLookAndFeel.getControlInfo());
/*      */           } 
/*      */         } else {
/*      */           
/* 2141 */           param1Graphics.setColor(MetalLookAndFeel.getMenuDisabledForeground());
/*      */         } 
/* 2143 */         param1Graphics.drawLine(3, 2, 5, 2);
/* 2144 */         param1Graphics.drawLine(2, 3, 6, 3);
/* 2145 */         param1Graphics.drawLine(2, 4, 6, 4);
/* 2146 */         param1Graphics.drawLine(2, 5, 6, 5);
/* 2147 */         param1Graphics.drawLine(3, 6, 5, 6);
/*      */       } 
/*      */       
/* 2150 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 2155 */       if (MetalLookAndFeel.usingOcean()) {
/* 2156 */         paintOceanIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*      */         return;
/*      */       } 
/* 2159 */       JMenuItem jMenuItem = (JMenuItem)param1Component;
/* 2160 */       ButtonModel buttonModel = jMenuItem.getModel();
/*      */       
/* 2162 */       boolean bool1 = buttonModel.isSelected();
/* 2163 */       boolean bool2 = buttonModel.isEnabled();
/* 2164 */       boolean bool3 = buttonModel.isPressed();
/* 2165 */       boolean bool4 = buttonModel.isArmed();
/*      */       
/* 2167 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */       
/* 2169 */       if (bool2) {
/*      */         
/* 2171 */         if (bool3 || bool4)
/*      */         {
/* 2173 */           param1Graphics.setColor(MetalLookAndFeel.getPrimaryControl());
/* 2174 */           param1Graphics.drawLine(3, 1, 8, 1);
/* 2175 */           param1Graphics.drawLine(2, 9, 7, 9);
/* 2176 */           param1Graphics.drawLine(1, 3, 1, 8);
/* 2177 */           param1Graphics.drawLine(9, 2, 9, 7);
/* 2178 */           param1Graphics.drawLine(2, 2, 2, 2);
/* 2179 */           param1Graphics.drawLine(8, 8, 8, 8);
/*      */           
/* 2181 */           param1Graphics.setColor(MetalLookAndFeel.getControlInfo());
/* 2182 */           param1Graphics.drawLine(2, 0, 6, 0);
/* 2183 */           param1Graphics.drawLine(2, 8, 6, 8);
/* 2184 */           param1Graphics.drawLine(0, 2, 0, 6);
/* 2185 */           param1Graphics.drawLine(8, 2, 8, 6);
/* 2186 */           param1Graphics.drawLine(1, 1, 1, 1);
/* 2187 */           param1Graphics.drawLine(7, 1, 7, 1);
/* 2188 */           param1Graphics.drawLine(1, 7, 1, 7);
/* 2189 */           param1Graphics.drawLine(7, 7, 7, 7);
/*      */         }
/*      */         else
/*      */         {
/* 2193 */           param1Graphics.setColor(MetalLookAndFeel.getControlHighlight());
/* 2194 */           param1Graphics.drawLine(3, 1, 8, 1);
/* 2195 */           param1Graphics.drawLine(2, 9, 7, 9);
/* 2196 */           param1Graphics.drawLine(1, 3, 1, 8);
/* 2197 */           param1Graphics.drawLine(9, 2, 9, 7);
/* 2198 */           param1Graphics.drawLine(2, 2, 2, 2);
/* 2199 */           param1Graphics.drawLine(8, 8, 8, 8);
/*      */           
/* 2201 */           param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 2202 */           param1Graphics.drawLine(2, 0, 6, 0);
/* 2203 */           param1Graphics.drawLine(2, 8, 6, 8);
/* 2204 */           param1Graphics.drawLine(0, 2, 0, 6);
/* 2205 */           param1Graphics.drawLine(8, 2, 8, 6);
/* 2206 */           param1Graphics.drawLine(1, 1, 1, 1);
/* 2207 */           param1Graphics.drawLine(7, 1, 7, 1);
/* 2208 */           param1Graphics.drawLine(1, 7, 1, 7);
/* 2209 */           param1Graphics.drawLine(7, 7, 7, 7);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2214 */         param1Graphics.setColor(MetalLookAndFeel.getMenuDisabledForeground());
/* 2215 */         param1Graphics.drawLine(2, 0, 6, 0);
/* 2216 */         param1Graphics.drawLine(2, 8, 6, 8);
/* 2217 */         param1Graphics.drawLine(0, 2, 0, 6);
/* 2218 */         param1Graphics.drawLine(8, 2, 8, 6);
/* 2219 */         param1Graphics.drawLine(1, 1, 1, 1);
/* 2220 */         param1Graphics.drawLine(7, 1, 7, 1);
/* 2221 */         param1Graphics.drawLine(1, 7, 1, 7);
/* 2222 */         param1Graphics.drawLine(7, 7, 7, 7);
/*      */       } 
/*      */       
/* 2225 */       if (bool1) {
/*      */         
/* 2227 */         if (bool2) {
/*      */           
/* 2229 */           if (buttonModel.isArmed() || (param1Component instanceof javax.swing.JMenu && buttonModel.isSelected()))
/*      */           {
/* 2231 */             param1Graphics.setColor(MetalLookAndFeel.getMenuSelectedForeground());
/*      */           }
/*      */           else
/*      */           {
/* 2235 */             param1Graphics.setColor(jMenuItem.getForeground());
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2240 */           param1Graphics.setColor(MetalLookAndFeel.getMenuDisabledForeground());
/*      */         } 
/*      */         
/* 2243 */         param1Graphics.drawLine(3, 2, 5, 2);
/* 2244 */         param1Graphics.drawLine(2, 3, 6, 3);
/* 2245 */         param1Graphics.drawLine(2, 4, 6, 4);
/* 2246 */         param1Graphics.drawLine(2, 5, 6, 5);
/* 2247 */         param1Graphics.drawLine(3, 6, 5, 6);
/*      */       } 
/*      */       
/* 2250 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     public int getIconWidth() {
/* 2253 */       return MetalIconFactory.menuCheckIconSize.width;
/*      */     } public int getIconHeight() {
/* 2255 */       return MetalIconFactory.menuCheckIconSize.height;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class VerticalSliderThumbIcon
/*      */     implements Icon, Serializable, UIResource
/*      */   {
/*      */     protected static MetalBumps controlBumps;
/*      */     protected static MetalBumps primaryBumps;
/*      */     
/*      */     public VerticalSliderThumbIcon() {
/* 2267 */       controlBumps = new MetalBumps(6, 10, MetalLookAndFeel.getControlHighlight(), MetalLookAndFeel.getControlInfo(), MetalLookAndFeel.getControl());
/*      */ 
/*      */ 
/*      */       
/* 2271 */       primaryBumps = new MetalBumps(6, 10, MetalLookAndFeel.getPrimaryControl(), MetalLookAndFeel.getPrimaryControlDarkShadow(), MetalLookAndFeel.getPrimaryControlShadow());
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 2275 */       boolean bool = MetalUtils.isLeftToRight(param1Component);
/*      */       
/* 2277 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/* 2280 */       if (param1Component.hasFocus()) {
/* 2281 */         param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/*      */       } else {
/*      */         
/* 2284 */         param1Graphics.setColor(param1Component.isEnabled() ? MetalLookAndFeel.getPrimaryControlInfo() : 
/* 2285 */             MetalLookAndFeel.getControlDarkShadow());
/*      */       } 
/*      */       
/* 2288 */       if (bool) {
/* 2289 */         param1Graphics.drawLine(1, 0, 8, 0);
/* 2290 */         param1Graphics.drawLine(0, 1, 0, 13);
/* 2291 */         param1Graphics.drawLine(1, 14, 8, 14);
/* 2292 */         param1Graphics.drawLine(9, 1, 15, 7);
/* 2293 */         param1Graphics.drawLine(9, 13, 15, 7);
/*      */       } else {
/*      */         
/* 2296 */         param1Graphics.drawLine(7, 0, 14, 0);
/* 2297 */         param1Graphics.drawLine(15, 1, 15, 13);
/* 2298 */         param1Graphics.drawLine(7, 14, 14, 14);
/* 2299 */         param1Graphics.drawLine(0, 7, 6, 1);
/* 2300 */         param1Graphics.drawLine(0, 7, 6, 13);
/*      */       } 
/*      */ 
/*      */       
/* 2304 */       if (param1Component.hasFocus()) {
/* 2305 */         param1Graphics.setColor(param1Component.getForeground());
/*      */       } else {
/*      */         
/* 2308 */         param1Graphics.setColor(MetalLookAndFeel.getControl());
/*      */       } 
/*      */       
/* 2311 */       if (bool) {
/* 2312 */         param1Graphics.fillRect(1, 1, 8, 13);
/*      */         
/* 2314 */         param1Graphics.drawLine(9, 2, 9, 12);
/* 2315 */         param1Graphics.drawLine(10, 3, 10, 11);
/* 2316 */         param1Graphics.drawLine(11, 4, 11, 10);
/* 2317 */         param1Graphics.drawLine(12, 5, 12, 9);
/* 2318 */         param1Graphics.drawLine(13, 6, 13, 8);
/* 2319 */         param1Graphics.drawLine(14, 7, 14, 7);
/*      */       } else {
/*      */         
/* 2322 */         param1Graphics.fillRect(7, 1, 8, 13);
/*      */         
/* 2324 */         param1Graphics.drawLine(6, 3, 6, 12);
/* 2325 */         param1Graphics.drawLine(5, 4, 5, 11);
/* 2326 */         param1Graphics.drawLine(4, 5, 4, 10);
/* 2327 */         param1Graphics.drawLine(3, 6, 3, 9);
/* 2328 */         param1Graphics.drawLine(2, 7, 2, 8);
/*      */       } 
/*      */ 
/*      */       
/* 2332 */       byte b = bool ? 2 : 8;
/* 2333 */       if (param1Component.isEnabled()) {
/* 2334 */         if (param1Component.hasFocus()) {
/* 2335 */           primaryBumps.paintIcon(param1Component, param1Graphics, b, 2);
/*      */         } else {
/*      */           
/* 2338 */           controlBumps.paintIcon(param1Component, param1Graphics, b, 2);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 2343 */       if (param1Component.isEnabled()) {
/* 2344 */         param1Graphics.setColor(param1Component.hasFocus() ? MetalLookAndFeel.getPrimaryControl() : 
/* 2345 */             MetalLookAndFeel.getControlHighlight());
/* 2346 */         if (bool) {
/* 2347 */           param1Graphics.drawLine(1, 1, 8, 1);
/* 2348 */           param1Graphics.drawLine(1, 1, 1, 13);
/*      */         } else {
/*      */           
/* 2351 */           param1Graphics.drawLine(8, 1, 14, 1);
/* 2352 */           param1Graphics.drawLine(1, 7, 7, 1);
/*      */         } 
/*      */       } 
/*      */       
/* 2356 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 2360 */       return 16;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 2364 */       return 15;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class HorizontalSliderThumbIcon
/*      */     implements Icon, Serializable, UIResource
/*      */   {
/*      */     protected static MetalBumps controlBumps;
/*      */     protected static MetalBumps primaryBumps;
/*      */     
/*      */     public HorizontalSliderThumbIcon() {
/* 2376 */       controlBumps = new MetalBumps(10, 6, MetalLookAndFeel.getControlHighlight(), MetalLookAndFeel.getControlInfo(), MetalLookAndFeel.getControl());
/*      */ 
/*      */ 
/*      */       
/* 2380 */       primaryBumps = new MetalBumps(10, 6, MetalLookAndFeel.getPrimaryControl(), MetalLookAndFeel.getPrimaryControlDarkShadow(), MetalLookAndFeel.getPrimaryControlShadow());
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 2384 */       param1Graphics.translate(param1Int1, param1Int2);
/*      */ 
/*      */       
/* 2387 */       if (param1Component.hasFocus()) {
/* 2388 */         param1Graphics.setColor(MetalLookAndFeel.getPrimaryControlInfo());
/*      */       } else {
/*      */         
/* 2391 */         param1Graphics.setColor(param1Component.isEnabled() ? MetalLookAndFeel.getPrimaryControlInfo() : 
/* 2392 */             MetalLookAndFeel.getControlDarkShadow());
/*      */       } 
/*      */       
/* 2395 */       param1Graphics.drawLine(1, 0, 13, 0);
/* 2396 */       param1Graphics.drawLine(0, 1, 0, 8);
/* 2397 */       param1Graphics.drawLine(14, 1, 14, 8);
/* 2398 */       param1Graphics.drawLine(1, 9, 7, 15);
/* 2399 */       param1Graphics.drawLine(7, 15, 14, 8);
/*      */ 
/*      */       
/* 2402 */       if (param1Component.hasFocus()) {
/* 2403 */         param1Graphics.setColor(param1Component.getForeground());
/*      */       } else {
/*      */         
/* 2406 */         param1Graphics.setColor(MetalLookAndFeel.getControl());
/*      */       } 
/* 2408 */       param1Graphics.fillRect(1, 1, 13, 8);
/*      */       
/* 2410 */       param1Graphics.drawLine(2, 9, 12, 9);
/* 2411 */       param1Graphics.drawLine(3, 10, 11, 10);
/* 2412 */       param1Graphics.drawLine(4, 11, 10, 11);
/* 2413 */       param1Graphics.drawLine(5, 12, 9, 12);
/* 2414 */       param1Graphics.drawLine(6, 13, 8, 13);
/* 2415 */       param1Graphics.drawLine(7, 14, 7, 14);
/*      */ 
/*      */       
/* 2418 */       if (param1Component.isEnabled()) {
/* 2419 */         if (param1Component.hasFocus()) {
/* 2420 */           primaryBumps.paintIcon(param1Component, param1Graphics, 2, 2);
/*      */         } else {
/*      */           
/* 2423 */           controlBumps.paintIcon(param1Component, param1Graphics, 2, 2);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 2428 */       if (param1Component.isEnabled()) {
/* 2429 */         param1Graphics.setColor(param1Component.hasFocus() ? MetalLookAndFeel.getPrimaryControl() : 
/* 2430 */             MetalLookAndFeel.getControlHighlight());
/* 2431 */         param1Graphics.drawLine(1, 1, 13, 1);
/* 2432 */         param1Graphics.drawLine(1, 1, 1, 8);
/*      */       } 
/*      */       
/* 2435 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 2439 */       return 15;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 2443 */       return 16;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class OceanVerticalSliderThumbIcon
/*      */     extends CachedPainter
/*      */     implements Icon, Serializable, UIResource
/*      */   {
/* 2455 */     private static Polygon LTR_THUMB_SHAPE = new Polygon(new int[] { 0, 8, 15, 8, 0 }, new int[] { 0, 0, 7, 14, 14 }, 5);
/*      */     
/* 2457 */     private static Polygon RTL_THUMB_SHAPE = new Polygon(new int[] { 15, 15, 7, 0, 7 }, new int[] { 0, 14, 14, 7, 0 }, 5);
/*      */ 
/*      */ 
/*      */     
/*      */     OceanVerticalSliderThumbIcon() {
/* 2462 */       super(3);
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 2466 */       if (!(param1Graphics instanceof Graphics2D)) {
/*      */         return;
/*      */       }
/* 2469 */       paint(param1Component, param1Graphics, param1Int1, param1Int2, getIconWidth(), getIconHeight(), new Object[] {
/* 2470 */             Boolean.valueOf(MetalUtils.isLeftToRight(param1Component)), Boolean.valueOf(param1Component.hasFocus()), Boolean.valueOf(param1Component.isEnabled()), 
/* 2471 */             MetalLookAndFeel.getCurrentTheme()
/*      */           });
/*      */     }
/*      */     
/*      */     protected void paintToImage(Component param1Component, Image param1Image, Graphics param1Graphics, int param1Int1, int param1Int2, Object[] param1ArrayOfObject) {
/* 2476 */       Graphics2D graphics2D = (Graphics2D)param1Graphics;
/* 2477 */       boolean bool1 = ((Boolean)param1ArrayOfObject[0]).booleanValue();
/* 2478 */       boolean bool2 = ((Boolean)param1ArrayOfObject[1]).booleanValue();
/* 2479 */       boolean bool3 = ((Boolean)param1ArrayOfObject[2]).booleanValue();
/*      */       
/* 2481 */       Rectangle rectangle = graphics2D.getClipBounds();
/* 2482 */       if (bool1) {
/* 2483 */         graphics2D.clip(LTR_THUMB_SHAPE);
/*      */       } else {
/*      */         
/* 2486 */         graphics2D.clip(RTL_THUMB_SHAPE);
/*      */       } 
/* 2488 */       if (!bool3) {
/* 2489 */         graphics2D.setColor(MetalLookAndFeel.getControl());
/* 2490 */         graphics2D.fillRect(1, 1, 14, 14);
/*      */       }
/* 2492 */       else if (bool2) {
/* 2493 */         MetalUtils.drawGradient(param1Component, graphics2D, "Slider.focusGradient", 1, 1, 14, 14, false);
/*      */       }
/*      */       else {
/*      */         
/* 2497 */         MetalUtils.drawGradient(param1Component, graphics2D, "Slider.gradient", 1, 1, 14, 14, false);
/*      */       } 
/*      */       
/* 2500 */       graphics2D.setClip(rectangle);
/*      */ 
/*      */       
/* 2503 */       if (bool2) {
/* 2504 */         graphics2D.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/*      */       } else {
/*      */         
/* 2507 */         graphics2D.setColor(bool3 ? MetalLookAndFeel.getPrimaryControlInfo() : 
/* 2508 */             MetalLookAndFeel.getControlDarkShadow());
/*      */       } 
/*      */       
/* 2511 */       if (bool1) {
/* 2512 */         graphics2D.drawLine(1, 0, 8, 0);
/* 2513 */         graphics2D.drawLine(0, 1, 0, 13);
/* 2514 */         graphics2D.drawLine(1, 14, 8, 14);
/* 2515 */         graphics2D.drawLine(9, 1, 15, 7);
/* 2516 */         graphics2D.drawLine(9, 13, 15, 7);
/*      */       } else {
/*      */         
/* 2519 */         graphics2D.drawLine(7, 0, 14, 0);
/* 2520 */         graphics2D.drawLine(15, 1, 15, 13);
/* 2521 */         graphics2D.drawLine(7, 14, 14, 14);
/* 2522 */         graphics2D.drawLine(0, 7, 6, 1);
/* 2523 */         graphics2D.drawLine(0, 7, 6, 13);
/*      */       } 
/*      */       
/* 2526 */       if (bool2 && bool3) {
/*      */         
/* 2528 */         graphics2D.setColor(MetalLookAndFeel.getPrimaryControl());
/* 2529 */         if (bool1) {
/* 2530 */           graphics2D.drawLine(1, 1, 8, 1);
/* 2531 */           graphics2D.drawLine(1, 1, 1, 13);
/* 2532 */           graphics2D.drawLine(1, 13, 8, 13);
/* 2533 */           graphics2D.drawLine(9, 2, 14, 7);
/* 2534 */           graphics2D.drawLine(9, 12, 14, 7);
/*      */         } else {
/*      */           
/* 2537 */           graphics2D.drawLine(7, 1, 14, 1);
/* 2538 */           graphics2D.drawLine(14, 1, 14, 13);
/* 2539 */           graphics2D.drawLine(7, 13, 14, 13);
/* 2540 */           graphics2D.drawLine(1, 7, 7, 1);
/* 2541 */           graphics2D.drawLine(1, 7, 7, 13);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 2547 */       return 16;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 2551 */       return 15;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Image createImage(Component param1Component, int param1Int1, int param1Int2, GraphicsConfiguration param1GraphicsConfiguration, Object[] param1ArrayOfObject) {
/* 2557 */       if (param1GraphicsConfiguration == null) {
/* 2558 */         return new BufferedImage(param1Int1, param1Int2, 2);
/*      */       }
/* 2560 */       return param1GraphicsConfiguration.createCompatibleImage(param1Int1, param1Int2, 2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class OceanHorizontalSliderThumbIcon
/*      */     extends CachedPainter
/*      */     implements Icon, Serializable, UIResource
/*      */   {
/* 2572 */     private static Polygon THUMB_SHAPE = new Polygon(new int[] { 0, 14, 14, 7, 0 }, new int[] { 0, 0, 8, 15, 8 }, 5);
/*      */ 
/*      */ 
/*      */     
/*      */     OceanHorizontalSliderThumbIcon() {
/* 2577 */       super(3);
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 2581 */       if (!(param1Graphics instanceof Graphics2D)) {
/*      */         return;
/*      */       }
/* 2584 */       paint(param1Component, param1Graphics, param1Int1, param1Int2, getIconWidth(), getIconHeight(), new Object[] {
/* 2585 */             Boolean.valueOf(param1Component.hasFocus()), Boolean.valueOf(param1Component.isEnabled()), 
/* 2586 */             MetalLookAndFeel.getCurrentTheme()
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Image createImage(Component param1Component, int param1Int1, int param1Int2, GraphicsConfiguration param1GraphicsConfiguration, Object[] param1ArrayOfObject) {
/* 2593 */       if (param1GraphicsConfiguration == null) {
/* 2594 */         return new BufferedImage(param1Int1, param1Int2, 2);
/*      */       }
/* 2596 */       return param1GraphicsConfiguration.createCompatibleImage(param1Int1, param1Int2, 2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void paintToImage(Component param1Component, Image param1Image, Graphics param1Graphics, int param1Int1, int param1Int2, Object[] param1ArrayOfObject) {
/* 2602 */       Graphics2D graphics2D = (Graphics2D)param1Graphics;
/* 2603 */       boolean bool1 = ((Boolean)param1ArrayOfObject[0]).booleanValue();
/* 2604 */       boolean bool2 = ((Boolean)param1ArrayOfObject[1]).booleanValue();
/*      */ 
/*      */       
/* 2607 */       Rectangle rectangle = graphics2D.getClipBounds();
/* 2608 */       graphics2D.clip(THUMB_SHAPE);
/* 2609 */       if (!bool2) {
/* 2610 */         graphics2D.setColor(MetalLookAndFeel.getControl());
/* 2611 */         graphics2D.fillRect(1, 1, 13, 14);
/*      */       }
/* 2613 */       else if (bool1) {
/* 2614 */         MetalUtils.drawGradient(param1Component, graphics2D, "Slider.focusGradient", 1, 1, 13, 14, true);
/*      */       }
/*      */       else {
/*      */         
/* 2618 */         MetalUtils.drawGradient(param1Component, graphics2D, "Slider.gradient", 1, 1, 13, 14, true);
/*      */       } 
/*      */       
/* 2621 */       graphics2D.setClip(rectangle);
/*      */ 
/*      */       
/* 2624 */       if (bool1) {
/* 2625 */         graphics2D.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/*      */       } else {
/*      */         
/* 2628 */         graphics2D.setColor(bool2 ? MetalLookAndFeel.getPrimaryControlInfo() : 
/* 2629 */             MetalLookAndFeel.getControlDarkShadow());
/*      */       } 
/*      */       
/* 2632 */       graphics2D.drawLine(1, 0, 13, 0);
/* 2633 */       graphics2D.drawLine(0, 1, 0, 8);
/* 2634 */       graphics2D.drawLine(14, 1, 14, 8);
/* 2635 */       graphics2D.drawLine(1, 9, 7, 15);
/* 2636 */       graphics2D.drawLine(7, 15, 14, 8);
/*      */       
/* 2638 */       if (bool1 && bool2) {
/*      */         
/* 2640 */         graphics2D.setColor(MetalLookAndFeel.getPrimaryControl());
/* 2641 */         graphics2D.fillRect(1, 1, 13, 1);
/* 2642 */         graphics2D.fillRect(1, 2, 1, 7);
/* 2643 */         graphics2D.fillRect(13, 2, 1, 7);
/* 2644 */         graphics2D.drawLine(2, 9, 7, 14);
/* 2645 */         graphics2D.drawLine(8, 13, 12, 9);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 2650 */       return 15;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 2654 */       return 16;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalIconFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */