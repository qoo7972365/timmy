/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.swing.colorchooser.AbstractColorChooserPanel;
/*     */ import javax.swing.colorchooser.ColorChooserComponentFactory;
/*     */ import javax.swing.colorchooser.ColorSelectionModel;
/*     */ import javax.swing.colorchooser.DefaultColorSelectionModel;
/*     */ import javax.swing.plaf.ColorChooserUI;
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
/*     */ public class JColorChooser
/*     */   extends JComponent
/*     */   implements Accessible
/*     */ {
/*     */   private static final String uiClassID = "ColorChooserUI";
/*     */   private ColorSelectionModel selectionModel;
/*  96 */   private JComponent previewPanel = ColorChooserComponentFactory.getPreviewPanel();
/*     */   
/*  98 */   private AbstractColorChooserPanel[] chooserPanels = new AbstractColorChooserPanel[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dragEnabled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String SELECTION_MODEL_PROPERTY = "selectionModel";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String PREVIEW_PANEL_PROPERTY = "previewPanel";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String CHOOSER_PANELS_PROPERTY = "chooserPanels";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AccessibleContext accessibleContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color showDialog(Component paramComponent, String paramString, Color paramColor) throws HeadlessException {
/* 137 */     JColorChooser jColorChooser = new JColorChooser((paramColor != null) ? paramColor : Color.white);
/*     */ 
/*     */     
/* 140 */     ColorTracker colorTracker = new ColorTracker(jColorChooser);
/* 141 */     JDialog jDialog = createDialog(paramComponent, paramString, true, jColorChooser, colorTracker, (ActionListener)null);
/*     */     
/* 143 */     jDialog.addComponentListener(new ColorChooserDialog.DisposeOnClose());
/*     */     
/* 145 */     jDialog.show();
/*     */     
/* 147 */     return colorTracker.getColor();
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
/*     */   public static JDialog createDialog(Component paramComponent, String paramString, boolean paramBoolean, JColorChooser paramJColorChooser, ActionListener paramActionListener1, ActionListener paramActionListener2) throws HeadlessException {
/*     */     ColorChooserDialog colorChooserDialog;
/* 176 */     Window window = JOptionPane.getWindowForComponent(paramComponent);
/*     */     
/* 178 */     if (window instanceof Frame) {
/* 179 */       colorChooserDialog = new ColorChooserDialog((Frame)window, paramString, paramBoolean, paramComponent, paramJColorChooser, paramActionListener1, paramActionListener2);
/*     */     } else {
/*     */       
/* 182 */       colorChooserDialog = new ColorChooserDialog((Dialog)window, paramString, paramBoolean, paramComponent, paramJColorChooser, paramActionListener1, paramActionListener2);
/*     */     } 
/*     */     
/* 185 */     colorChooserDialog.getAccessibleContext().setAccessibleDescription(paramString);
/* 186 */     return colorChooserDialog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JColorChooser() {
/* 193 */     this(Color.white);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JColorChooser(Color paramColor) {
/* 202 */     this(new DefaultColorSelectionModel(paramColor));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorChooserUI getUI() {
/* 225 */     return (ColorChooserUI)this.ui;
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
/*     */   public void setUI(ColorChooserUI paramColorChooserUI) {
/* 240 */     setUI(paramColorChooserUI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 251 */     setUI((ColorChooserUI)UIManager.getUI(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUIClassID() {
/* 262 */     return "ColorChooserUI";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 272 */     return this.selectionModel.getSelectedColor();
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
/*     */   public void setColor(Color paramColor) {
/* 287 */     this.selectionModel.setSelectedColor(paramColor);
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
/*     */   
/*     */   public void setColor(int paramInt1, int paramInt2, int paramInt3) {
/* 303 */     setColor(new Color(paramInt1, paramInt2, paramInt3));
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
/*     */   public void setColor(int paramInt) {
/* 316 */     setColor(paramInt >> 16 & 0xFF, paramInt >> 8 & 0xFF, paramInt & 0xFF);
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
/*     */   public void setDragEnabled(boolean paramBoolean) {
/* 359 */     if (paramBoolean && GraphicsEnvironment.isHeadless()) {
/* 360 */       throw new HeadlessException();
/*     */     }
/* 362 */     this.dragEnabled = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getDragEnabled() {
/* 373 */     return this.dragEnabled;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreviewPanel(JComponent paramJComponent) {
/* 391 */     if (this.previewPanel != paramJComponent) {
/* 392 */       JComponent jComponent = this.previewPanel;
/* 393 */       this.previewPanel = paramJComponent;
/* 394 */       firePropertyChange("previewPanel", jComponent, paramJComponent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JComponent getPreviewPanel() {
/* 404 */     return this.previewPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChooserPanel(AbstractColorChooserPanel paramAbstractColorChooserPanel) {
/* 413 */     AbstractColorChooserPanel[] arrayOfAbstractColorChooserPanel1 = getChooserPanels();
/* 414 */     AbstractColorChooserPanel[] arrayOfAbstractColorChooserPanel2 = new AbstractColorChooserPanel[arrayOfAbstractColorChooserPanel1.length + 1];
/* 415 */     System.arraycopy(arrayOfAbstractColorChooserPanel1, 0, arrayOfAbstractColorChooserPanel2, 0, arrayOfAbstractColorChooserPanel1.length);
/* 416 */     arrayOfAbstractColorChooserPanel2[arrayOfAbstractColorChooserPanel2.length - 1] = paramAbstractColorChooserPanel;
/* 417 */     setChooserPanels(arrayOfAbstractColorChooserPanel2);
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
/*     */   public AbstractColorChooserPanel removeChooserPanel(AbstractColorChooserPanel paramAbstractColorChooserPanel) {
/* 431 */     byte b = -1;
/*     */     
/* 433 */     for (byte b1 = 0; b1 < this.chooserPanels.length; b1++) {
/* 434 */       if (this.chooserPanels[b1] == paramAbstractColorChooserPanel) {
/* 435 */         b = b1;
/*     */         break;
/*     */       } 
/*     */     } 
/* 439 */     if (b == -1) {
/* 440 */       throw new IllegalArgumentException("chooser panel not in this chooser");
/*     */     }
/*     */     
/* 443 */     AbstractColorChooserPanel[] arrayOfAbstractColorChooserPanel = new AbstractColorChooserPanel[this.chooserPanels.length - 1];
/*     */     
/* 445 */     if (b == this.chooserPanels.length - 1) {
/* 446 */       System.arraycopy(this.chooserPanels, 0, arrayOfAbstractColorChooserPanel, 0, arrayOfAbstractColorChooserPanel.length);
/*     */     }
/* 448 */     else if (b == 0) {
/* 449 */       System.arraycopy(this.chooserPanels, 1, arrayOfAbstractColorChooserPanel, 0, arrayOfAbstractColorChooserPanel.length);
/*     */     } else {
/*     */       
/* 452 */       System.arraycopy(this.chooserPanels, 0, arrayOfAbstractColorChooserPanel, 0, b);
/* 453 */       System.arraycopy(this.chooserPanels, b + 1, arrayOfAbstractColorChooserPanel, b, this.chooserPanels.length - b - 1);
/*     */     } 
/*     */ 
/*     */     
/* 457 */     setChooserPanels(arrayOfAbstractColorChooserPanel);
/*     */     
/* 459 */     return paramAbstractColorChooserPanel;
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
/*     */   
/*     */   public void setChooserPanels(AbstractColorChooserPanel[] paramArrayOfAbstractColorChooserPanel) {
/* 475 */     AbstractColorChooserPanel[] arrayOfAbstractColorChooserPanel = this.chooserPanels;
/* 476 */     this.chooserPanels = paramArrayOfAbstractColorChooserPanel;
/* 477 */     firePropertyChange("chooserPanels", arrayOfAbstractColorChooserPanel, paramArrayOfAbstractColorChooserPanel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractColorChooserPanel[] getChooserPanels() {
/* 486 */     return this.chooserPanels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorSelectionModel getSelectionModel() {
/* 495 */     return this.selectionModel;
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
/*     */   public void setSelectionModel(ColorSelectionModel paramColorSelectionModel) {
/* 510 */     ColorSelectionModel colorSelectionModel = this.selectionModel;
/* 511 */     this.selectionModel = paramColorSelectionModel;
/* 512 */     firePropertyChange("selectionModel", colorSelectionModel, paramColorSelectionModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 522 */     paramObjectOutputStream.defaultWriteObject();
/* 523 */     if (getUIClassID().equals("ColorChooserUI")) {
/* 524 */       byte b = JComponent.getWriteObjCounter(this);
/* 525 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 526 */       if (b == 0 && this.ui != null) {
/* 527 */         this.ui.installUI(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String paramString() {
/* 544 */     StringBuffer stringBuffer = new StringBuffer("");
/* 545 */     for (byte b = 0; b < this.chooserPanels.length; b++) {
/* 546 */       stringBuffer.append("[" + this.chooserPanels[b].toString() + "]");
/*     */     }
/*     */ 
/*     */     
/* 550 */     String str = (this.previewPanel != null) ? this.previewPanel.toString() : "";
/*     */     
/* 552 */     return super.paramString() + ",chooserPanels=" + stringBuffer
/* 553 */       .toString() + ",previewPanel=" + str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JColorChooser(ColorSelectionModel paramColorSelectionModel) {
/* 561 */     this.accessibleContext = null;
/*     */     this.selectionModel = paramColorSelectionModel;
/*     */     updateUI();
/*     */     this.dragEnabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleContext getAccessibleContext() {
/* 573 */     if (this.accessibleContext == null) {
/* 574 */       this.accessibleContext = new AccessibleJColorChooser();
/*     */     }
/* 576 */     return this.accessibleContext;
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
/*     */   
/*     */   protected class AccessibleJColorChooser
/*     */     extends JComponent.AccessibleJComponent
/*     */   {
/*     */     public AccessibleRole getAccessibleRole() {
/* 595 */       return AccessibleRole.COLOR_CHOOSER;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JColorChooser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */