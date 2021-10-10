/*     */ package javax.swing.plaf.multi;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.io.File;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.filechooser.FileFilter;
/*     */ import javax.swing.filechooser.FileView;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.FileChooserUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiFileChooserUI
/*     */   extends FileChooserUI
/*     */ {
/*  55 */   protected Vector uis = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentUI[] getUIs() {
/*  67 */     return MultiLookAndFeel.uisToArray(this.uis);
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
/*     */   public FileFilter getAcceptAllFileFilter(JFileChooser paramJFileChooser) {
/*  82 */     FileFilter fileFilter = ((FileChooserUI)this.uis.elementAt(0)).getAcceptAllFileFilter(paramJFileChooser);
/*  83 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  84 */       ((FileChooserUI)this.uis.elementAt(b)).getAcceptAllFileFilter(paramJFileChooser);
/*     */     }
/*  86 */     return fileFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileView getFileView(JFileChooser paramJFileChooser) {
/*  97 */     FileView fileView = ((FileChooserUI)this.uis.elementAt(0)).getFileView(paramJFileChooser);
/*  98 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  99 */       ((FileChooserUI)this.uis.elementAt(b)).getFileView(paramJFileChooser);
/*     */     }
/* 101 */     return fileView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getApproveButtonText(JFileChooser paramJFileChooser) {
/* 112 */     String str = ((FileChooserUI)this.uis.elementAt(0)).getApproveButtonText(paramJFileChooser);
/* 113 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 114 */       ((FileChooserUI)this.uis.elementAt(b)).getApproveButtonText(paramJFileChooser);
/*     */     }
/* 116 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDialogTitle(JFileChooser paramJFileChooser) {
/* 127 */     String str = ((FileChooserUI)this.uis.elementAt(0)).getDialogTitle(paramJFileChooser);
/* 128 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 129 */       ((FileChooserUI)this.uis.elementAt(b)).getDialogTitle(paramJFileChooser);
/*     */     }
/* 131 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rescanCurrentDirectory(JFileChooser paramJFileChooser) {
/* 138 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 139 */       ((FileChooserUI)this.uis.elementAt(b)).rescanCurrentDirectory(paramJFileChooser);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureFileIsVisible(JFileChooser paramJFileChooser, File paramFile) {
/* 147 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 148 */       ((FileChooserUI)this.uis.elementAt(b)).ensureFileIsVisible(paramJFileChooser, paramFile);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 164 */     boolean bool = ((ComponentUI)this.uis.elementAt(0)).contains(paramJComponent, paramInt1, paramInt2);
/* 165 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 166 */       ((ComponentUI)this.uis.elementAt(b)).contains(paramJComponent, paramInt1, paramInt2);
/*     */     }
/* 168 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 175 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 176 */       ((ComponentUI)this.uis.elementAt(b)).update(paramGraphics, paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 186 */     MultiFileChooserUI multiFileChooserUI = new MultiFileChooserUI();
/* 187 */     return MultiLookAndFeel.createUIs(multiFileChooserUI, multiFileChooserUI.uis, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 196 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 197 */       ((ComponentUI)this.uis.elementAt(b)).installUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 205 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 206 */       ((ComponentUI)this.uis.elementAt(b)).uninstallUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 214 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 215 */       ((ComponentUI)this.uis.elementAt(b)).paint(paramGraphics, paramJComponent);
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
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 227 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getPreferredSize(paramJComponent);
/* 228 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 229 */       ((ComponentUI)this.uis.elementAt(b)).getPreferredSize(paramJComponent);
/*     */     }
/* 231 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 242 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMinimumSize(paramJComponent);
/* 243 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 244 */       ((ComponentUI)this.uis.elementAt(b)).getMinimumSize(paramJComponent);
/*     */     }
/* 246 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 257 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMaximumSize(paramJComponent);
/* 258 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 259 */       ((ComponentUI)this.uis.elementAt(b)).getMaximumSize(paramJComponent);
/*     */     }
/* 261 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAccessibleChildrenCount(JComponent paramJComponent) {
/* 272 */     int i = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChildrenCount(paramJComponent);
/* 273 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 274 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChildrenCount(paramJComponent);
/*     */     }
/* 276 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Accessible getAccessibleChild(JComponent paramJComponent, int paramInt) {
/* 287 */     Accessible accessible = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChild(paramJComponent, paramInt);
/* 288 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 289 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChild(paramJComponent, paramInt);
/*     */     }
/* 291 */     return accessible;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/multi/MultiFileChooserUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */