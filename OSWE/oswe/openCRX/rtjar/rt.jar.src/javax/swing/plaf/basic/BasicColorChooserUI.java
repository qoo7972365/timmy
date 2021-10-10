/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.TransferHandler;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.colorchooser.AbstractColorChooserPanel;
/*     */ import javax.swing.colorchooser.ColorChooserComponentFactory;
/*     */ import javax.swing.colorchooser.ColorSelectionModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.plaf.ColorChooserUI;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import sun.swing.DefaultLookup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicColorChooserUI
/*     */   extends ColorChooserUI
/*     */ {
/*     */   protected JColorChooser chooser;
/*     */   JTabbedPane tabbedPane;
/*     */   JPanel singlePanel;
/*     */   JPanel previewPanelHolder;
/*     */   JComponent previewPanel;
/*     */   boolean isMultiPanel = false;
/*  62 */   private static TransferHandler defaultTransferHandler = new ColorTransferHandler();
/*     */   
/*     */   protected AbstractColorChooserPanel[] defaultChoosers;
/*     */   
/*     */   protected ChangeListener previewListener;
/*     */   protected PropertyChangeListener propertyChangeListener;
/*     */   private Handler handler;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  71 */     return new BasicColorChooserUI();
/*     */   }
/*     */   
/*     */   protected AbstractColorChooserPanel[] createDefaultChoosers() {
/*  75 */     return ColorChooserComponentFactory.getDefaultChooserPanels();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallDefaultChoosers() {
/*  80 */     AbstractColorChooserPanel[] arrayOfAbstractColorChooserPanel = this.chooser.getChooserPanels();
/*  81 */     for (byte b = 0; b < arrayOfAbstractColorChooserPanel.length; b++) {
/*  82 */       this.chooser.removeChooserPanel(arrayOfAbstractColorChooserPanel[b]);
/*     */     }
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  87 */     this.chooser = (JColorChooser)paramJComponent;
/*     */     
/*  89 */     super.installUI(paramJComponent);
/*     */     
/*  91 */     installDefaults();
/*  92 */     installListeners();
/*     */     
/*  94 */     this.tabbedPane = new JTabbedPane();
/*  95 */     this.tabbedPane.setName("ColorChooser.tabPane");
/*  96 */     this.tabbedPane.setInheritsPopupMenu(true);
/*  97 */     this.tabbedPane.getAccessibleContext().setAccessibleDescription(this.tabbedPane.getName());
/*  98 */     this.singlePanel = new JPanel(new CenterLayout());
/*  99 */     this.singlePanel.setName("ColorChooser.panel");
/* 100 */     this.singlePanel.setInheritsPopupMenu(true);
/*     */     
/* 102 */     this.chooser.setLayout(new BorderLayout());
/*     */     
/* 104 */     this.defaultChoosers = createDefaultChoosers();
/* 105 */     this.chooser.setChooserPanels(this.defaultChoosers);
/*     */     
/* 107 */     this.previewPanelHolder = new JPanel(new CenterLayout());
/* 108 */     this.previewPanelHolder.setName("ColorChooser.previewPanelHolder");
/*     */     
/* 110 */     if (DefaultLookup.getBoolean(this.chooser, this, "ColorChooser.showPreviewPanelText", true)) {
/*     */       
/* 112 */       String str = UIManager.getString("ColorChooser.previewText", this.chooser
/* 113 */           .getLocale());
/* 114 */       this.previewPanelHolder.setBorder(new TitledBorder(str));
/*     */     } 
/* 116 */     this.previewPanelHolder.setInheritsPopupMenu(true);
/*     */     
/* 118 */     installPreviewPanel();
/* 119 */     this.chooser.applyComponentOrientation(paramJComponent.getComponentOrientation());
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 123 */     this.chooser.remove(this.tabbedPane);
/* 124 */     this.chooser.remove(this.singlePanel);
/* 125 */     this.chooser.remove(this.previewPanelHolder);
/*     */     
/* 127 */     uninstallDefaultChoosers();
/* 128 */     uninstallListeners();
/* 129 */     uninstallPreviewPanel();
/* 130 */     uninstallDefaults();
/*     */     
/* 132 */     this.previewPanelHolder = null;
/* 133 */     this.previewPanel = null;
/* 134 */     this.defaultChoosers = null;
/* 135 */     this.chooser = null;
/* 136 */     this.tabbedPane = null;
/*     */     
/* 138 */     this.handler = null;
/*     */   }
/*     */   
/*     */   protected void installPreviewPanel() {
/* 142 */     JComponent jComponent = this.chooser.getPreviewPanel();
/* 143 */     if (jComponent == null) {
/* 144 */       jComponent = ColorChooserComponentFactory.getPreviewPanel();
/*     */     }
/* 146 */     else if (JPanel.class.equals(jComponent.getClass()) && 0 == jComponent.getComponentCount()) {
/* 147 */       jComponent = null;
/*     */     } 
/* 149 */     this.previewPanel = jComponent;
/* 150 */     if (jComponent != null) {
/* 151 */       this.chooser.add(this.previewPanelHolder, "South");
/* 152 */       jComponent.setForeground(this.chooser.getColor());
/* 153 */       this.previewPanelHolder.add(jComponent);
/* 154 */       jComponent.addMouseListener(getHandler());
/* 155 */       jComponent.setInheritsPopupMenu(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallPreviewPanel() {
/* 165 */     if (this.previewPanel != null) {
/* 166 */       this.previewPanel.removeMouseListener(getHandler());
/* 167 */       this.previewPanelHolder.remove(this.previewPanel);
/*     */     } 
/* 169 */     this.chooser.remove(this.previewPanelHolder);
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/* 173 */     LookAndFeel.installColorsAndFont(this.chooser, "ColorChooser.background", "ColorChooser.foreground", "ColorChooser.font");
/*     */ 
/*     */     
/* 176 */     LookAndFeel.installProperty(this.chooser, "opaque", Boolean.TRUE);
/* 177 */     TransferHandler transferHandler = this.chooser.getTransferHandler();
/* 178 */     if (transferHandler == null || transferHandler instanceof UIResource) {
/* 179 */       this.chooser.setTransferHandler(defaultTransferHandler);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {
/* 184 */     if (this.chooser.getTransferHandler() instanceof UIResource) {
/* 185 */       this.chooser.setTransferHandler((TransferHandler)null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 191 */     this.propertyChangeListener = createPropertyChangeListener();
/* 192 */     this.chooser.addPropertyChangeListener(this.propertyChangeListener);
/*     */     
/* 194 */     this.previewListener = getHandler();
/* 195 */     this.chooser.getSelectionModel().addChangeListener(this.previewListener);
/*     */   }
/*     */   
/*     */   private Handler getHandler() {
/* 199 */     if (this.handler == null) {
/* 200 */       this.handler = new Handler();
/*     */     }
/* 202 */     return this.handler;
/*     */   }
/*     */   
/*     */   protected PropertyChangeListener createPropertyChangeListener() {
/* 206 */     return getHandler();
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 210 */     this.chooser.removePropertyChangeListener(this.propertyChangeListener);
/* 211 */     this.chooser.getSelectionModel().removeChangeListener(this.previewListener);
/* 212 */     this.previewListener = null;
/*     */   }
/*     */   
/*     */   private void selectionChanged(ColorSelectionModel paramColorSelectionModel) {
/* 216 */     JComponent jComponent = this.chooser.getPreviewPanel();
/* 217 */     if (jComponent != null) {
/* 218 */       jComponent.setForeground(paramColorSelectionModel.getSelectedColor());
/* 219 */       jComponent.repaint();
/*     */     } 
/* 221 */     AbstractColorChooserPanel[] arrayOfAbstractColorChooserPanel = this.chooser.getChooserPanels();
/* 222 */     if (arrayOfAbstractColorChooserPanel != null) {
/* 223 */       for (AbstractColorChooserPanel abstractColorChooserPanel : arrayOfAbstractColorChooserPanel) {
/* 224 */         if (abstractColorChooserPanel != null) {
/* 225 */           abstractColorChooserPanel.updateChooser();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class Handler
/*     */     implements ChangeListener, MouseListener, PropertyChangeListener
/*     */   {
/*     */     private Handler() {}
/*     */     
/*     */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 237 */       BasicColorChooserUI.this.selectionChanged((ColorSelectionModel)param1ChangeEvent.getSource());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 243 */       if (BasicColorChooserUI.this.chooser.getDragEnabled()) {
/* 244 */         TransferHandler transferHandler = BasicColorChooserUI.this.chooser.getTransferHandler();
/* 245 */         transferHandler.exportAsDrag(BasicColorChooserUI.this.chooser, param1MouseEvent, 1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {}
/*     */ 
/*     */     
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*     */ 
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 257 */       String str = param1PropertyChangeEvent.getPropertyName();
/*     */       
/* 259 */       if (str == "chooserPanels") {
/*     */         
/* 261 */         AbstractColorChooserPanel[] arrayOfAbstractColorChooserPanel1 = (AbstractColorChooserPanel[])param1PropertyChangeEvent.getOldValue();
/*     */         
/* 263 */         AbstractColorChooserPanel[] arrayOfAbstractColorChooserPanel2 = (AbstractColorChooserPanel[])param1PropertyChangeEvent.getNewValue();
/*     */         int i;
/* 265 */         for (i = 0; i < arrayOfAbstractColorChooserPanel1.length; i++) {
/* 266 */           Container container = arrayOfAbstractColorChooserPanel1[i].getParent();
/* 267 */           if (container != null) {
/* 268 */             Container container1 = container.getParent();
/* 269 */             if (container1 != null)
/* 270 */               container1.remove(container); 
/* 271 */             arrayOfAbstractColorChooserPanel1[i].uninstallChooserPanel(BasicColorChooserUI.this.chooser);
/*     */           } 
/*     */         } 
/*     */         
/* 275 */         i = arrayOfAbstractColorChooserPanel2.length;
/* 276 */         if (i == 0) {
/* 277 */           BasicColorChooserUI.this.chooser.remove(BasicColorChooserUI.this.tabbedPane);
/*     */           return;
/*     */         } 
/* 280 */         if (i == 1) {
/* 281 */           BasicColorChooserUI.this.chooser.remove(BasicColorChooserUI.this.tabbedPane);
/* 282 */           JPanel jPanel = new JPanel(new CenterLayout());
/* 283 */           jPanel.setInheritsPopupMenu(true);
/* 284 */           jPanel.add(arrayOfAbstractColorChooserPanel2[0]);
/* 285 */           BasicColorChooserUI.this.singlePanel.add(jPanel, "Center");
/* 286 */           BasicColorChooserUI.this.chooser.add(BasicColorChooserUI.this.singlePanel);
/*     */         } else {
/*     */           
/* 289 */           if (arrayOfAbstractColorChooserPanel1.length < 2) {
/* 290 */             BasicColorChooserUI.this.chooser.remove(BasicColorChooserUI.this.singlePanel);
/* 291 */             BasicColorChooserUI.this.chooser.add(BasicColorChooserUI.this.tabbedPane, "Center");
/*     */           } 
/*     */           
/* 294 */           for (byte b1 = 0; b1 < arrayOfAbstractColorChooserPanel2.length; b1++) {
/* 295 */             JPanel jPanel = new JPanel(new CenterLayout());
/* 296 */             jPanel.setInheritsPopupMenu(true);
/* 297 */             String str1 = arrayOfAbstractColorChooserPanel2[b1].getDisplayName();
/* 298 */             int j = arrayOfAbstractColorChooserPanel2[b1].getMnemonic();
/* 299 */             jPanel.add(arrayOfAbstractColorChooserPanel2[b1]);
/* 300 */             BasicColorChooserUI.this.tabbedPane.addTab(str1, jPanel);
/* 301 */             if (j > 0) {
/* 302 */               BasicColorChooserUI.this.tabbedPane.setMnemonicAt(b1, j);
/* 303 */               int k = arrayOfAbstractColorChooserPanel2[b1].getDisplayedMnemonicIndex();
/* 304 */               if (k >= 0) {
/* 305 */                 BasicColorChooserUI.this.tabbedPane.setDisplayedMnemonicIndexAt(b1, k);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 310 */         BasicColorChooserUI.this.chooser.applyComponentOrientation(BasicColorChooserUI.this.chooser.getComponentOrientation());
/* 311 */         for (byte b = 0; b < arrayOfAbstractColorChooserPanel2.length; b++) {
/* 312 */           arrayOfAbstractColorChooserPanel2[b].installChooserPanel(BasicColorChooserUI.this.chooser);
/*     */         }
/*     */       }
/* 315 */       else if (str == "previewPanel") {
/* 316 */         BasicColorChooserUI.this.uninstallPreviewPanel();
/* 317 */         BasicColorChooserUI.this.installPreviewPanel();
/*     */       }
/* 319 */       else if (str == "selectionModel") {
/* 320 */         ColorSelectionModel colorSelectionModel1 = (ColorSelectionModel)param1PropertyChangeEvent.getOldValue();
/* 321 */         colorSelectionModel1.removeChangeListener(BasicColorChooserUI.this.previewListener);
/* 322 */         ColorSelectionModel colorSelectionModel2 = (ColorSelectionModel)param1PropertyChangeEvent.getNewValue();
/* 323 */         colorSelectionModel2.addChangeListener(BasicColorChooserUI.this.previewListener);
/* 324 */         BasicColorChooserUI.this.selectionChanged(colorSelectionModel2);
/*     */       }
/* 326 */       else if (str == "componentOrientation") {
/*     */         
/* 328 */         ComponentOrientation componentOrientation = (ComponentOrientation)param1PropertyChangeEvent.getNewValue();
/* 329 */         JColorChooser jColorChooser = (JColorChooser)param1PropertyChangeEvent.getSource();
/* 330 */         if (componentOrientation != (ComponentOrientation)param1PropertyChangeEvent.getOldValue()) {
/* 331 */           jColorChooser.applyComponentOrientation(componentOrientation);
/* 332 */           jColorChooser.updateUI();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*     */   }
/*     */   
/*     */   public class PropertyHandler implements PropertyChangeListener {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 344 */       BasicColorChooserUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   static class ColorTransferHandler
/*     */     extends TransferHandler implements UIResource {
/*     */     ColorTransferHandler() {
/* 351 */       super("color");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicColorChooserUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */