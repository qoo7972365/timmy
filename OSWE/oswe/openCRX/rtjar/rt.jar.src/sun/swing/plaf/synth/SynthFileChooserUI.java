/*     */ package sun.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.File;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.ListDataEvent;
/*     */ import javax.swing.event.ListDataListener;
/*     */ import javax.swing.filechooser.FileFilter;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicFileChooserUI;
/*     */ import javax.swing.plaf.synth.ColorType;
/*     */ import javax.swing.plaf.synth.Region;
/*     */ import javax.swing.plaf.synth.SynthContext;
/*     */ import javax.swing.plaf.synth.SynthLookAndFeel;
/*     */ import javax.swing.plaf.synth.SynthStyle;
/*     */ import javax.swing.plaf.synth.SynthUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SynthFileChooserUI
/*     */   extends BasicFileChooserUI
/*     */   implements SynthUI
/*     */ {
/*     */   private JButton approveButton;
/*     */   private JButton cancelButton;
/*     */   private SynthStyle style;
/*  65 */   private Action fileNameCompletionAction = new FileNameCompletionAction();
/*     */   
/*  67 */   private FileFilter actualFileFilter = null;
/*  68 */   private GlobFilter globFilter = null; private String fileNameCompletionString;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  71 */     return new SynthFileChooserUIImpl((JFileChooser)paramJComponent);
/*     */   }
/*     */   
/*     */   public SynthFileChooserUI(JFileChooser paramJFileChooser) {
/*  75 */     super(paramJFileChooser);
/*     */   }
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/*  79 */     return new SynthContext(paramJComponent, Region.FILE_CHOOSER, this.style, 
/*  80 */         getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   protected SynthContext getContext(JComponent paramJComponent, int paramInt) {
/*  84 */     Region region = SynthLookAndFeel.getRegion(paramJComponent);
/*  85 */     return new SynthContext(paramJComponent, Region.FILE_CHOOSER, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private Region getRegion(JComponent paramJComponent) {
/*  89 */     return SynthLookAndFeel.getRegion(paramJComponent);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/*  93 */     if (paramJComponent.isEnabled()) {
/*  94 */       if (paramJComponent.isFocusOwner()) {
/*  95 */         return 257;
/*     */       }
/*  97 */       return 1;
/*     */     } 
/*  99 */     return 8;
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/* 103 */     SynthStyle synthStyle = SynthLookAndFeel.getStyleFactory().getStyle(paramJComponent, Region.FILE_CHOOSER);
/*     */     
/* 105 */     if (synthStyle != this.style) {
/* 106 */       if (this.style != null) {
/* 107 */         this.style.uninstallDefaults(getContext(paramJComponent, 1));
/*     */       }
/* 109 */       this.style = synthStyle;
/* 110 */       SynthContext synthContext = getContext(paramJComponent, 1);
/* 111 */       this.style.installDefaults(synthContext);
/* 112 */       Border border = paramJComponent.getBorder();
/* 113 */       if (border == null || border instanceof UIResource) {
/* 114 */         paramJComponent.setBorder(new UIBorder(this.style.getInsets(synthContext, null)));
/*     */       }
/*     */       
/* 117 */       this.directoryIcon = this.style.getIcon(synthContext, "FileView.directoryIcon");
/* 118 */       this.fileIcon = this.style.getIcon(synthContext, "FileView.fileIcon");
/* 119 */       this.computerIcon = this.style.getIcon(synthContext, "FileView.computerIcon");
/* 120 */       this.hardDriveIcon = this.style.getIcon(synthContext, "FileView.hardDriveIcon");
/* 121 */       this.floppyDriveIcon = this.style.getIcon(synthContext, "FileView.floppyDriveIcon");
/*     */       
/* 123 */       this.newFolderIcon = this.style.getIcon(synthContext, "FileChooser.newFolderIcon");
/* 124 */       this.upFolderIcon = this.style.getIcon(synthContext, "FileChooser.upFolderIcon");
/* 125 */       this.homeFolderIcon = this.style.getIcon(synthContext, "FileChooser.homeFolderIcon");
/* 126 */       this.detailsViewIcon = this.style.getIcon(synthContext, "FileChooser.detailsViewIcon");
/* 127 */       this.listViewIcon = this.style.getIcon(synthContext, "FileChooser.listViewIcon");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 132 */     super.installUI(paramJComponent);
/* 133 */     SwingUtilities.replaceUIActionMap(paramJComponent, createActionMap());
/*     */   }
/*     */   
/*     */   public void installComponents(JFileChooser paramJFileChooser) {
/* 137 */     SynthContext synthContext = getContext(paramJFileChooser, 1);
/*     */     
/* 139 */     this.cancelButton = new JButton(this.cancelButtonText);
/* 140 */     this.cancelButton.setName("SynthFileChooser.cancelButton");
/* 141 */     this.cancelButton.setIcon(synthContext.getStyle().getIcon(synthContext, "FileChooser.cancelIcon"));
/* 142 */     this.cancelButton.setMnemonic(this.cancelButtonMnemonic);
/* 143 */     this.cancelButton.setToolTipText(this.cancelButtonToolTipText);
/* 144 */     this.cancelButton.addActionListener(getCancelSelectionAction());
/*     */     
/* 146 */     this.approveButton = new JButton(getApproveButtonText(paramJFileChooser));
/* 147 */     this.approveButton.setName("SynthFileChooser.approveButton");
/* 148 */     this.approveButton.setIcon(synthContext.getStyle().getIcon(synthContext, "FileChooser.okIcon"));
/* 149 */     this.approveButton.setMnemonic(getApproveButtonMnemonic(paramJFileChooser));
/* 150 */     this.approveButton.setToolTipText(getApproveButtonToolTipText(paramJFileChooser));
/* 151 */     this.approveButton.addActionListener(getApproveSelectionAction());
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninstallComponents(JFileChooser paramJFileChooser) {
/* 156 */     paramJFileChooser.removeAll();
/*     */   }
/*     */   
/*     */   protected void installListeners(JFileChooser paramJFileChooser) {
/* 160 */     super.installListeners(paramJFileChooser);
/*     */     
/* 162 */     getModel().addListDataListener(new ListDataListener()
/*     */         {
/*     */           public void contentsChanged(ListDataEvent param1ListDataEvent) {
/* 165 */             new SynthFileChooserUI.DelayedSelectionUpdater();
/*     */           }
/*     */           public void intervalAdded(ListDataEvent param1ListDataEvent) {
/* 168 */             new SynthFileChooserUI.DelayedSelectionUpdater();
/*     */           }
/*     */           
/*     */           public void intervalRemoved(ListDataEvent param1ListDataEvent) {}
/*     */         });
/*     */   }
/*     */   
/*     */   private class DelayedSelectionUpdater
/*     */     implements Runnable {
/*     */     DelayedSelectionUpdater() {
/* 178 */       SwingUtilities.invokeLater(this);
/*     */     }
/*     */     
/*     */     public void run() {
/* 182 */       SynthFileChooserUI.this.updateFileNameCompletion();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(JFileChooser paramJFileChooser) {
/* 190 */     super.installDefaults(paramJFileChooser);
/* 191 */     updateStyle(paramJFileChooser);
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(JFileChooser paramJFileChooser) {
/* 195 */     super.uninstallDefaults(paramJFileChooser);
/*     */     
/* 197 */     SynthContext synthContext = getContext(getFileChooser(), 1);
/* 198 */     this.style.uninstallDefaults(synthContext);
/* 199 */     this.style = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installIcons(JFileChooser paramJFileChooser) {}
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 207 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 209 */     if (paramJComponent.isOpaque()) {
/* 210 */       paramGraphics.setColor(this.style.getColor(synthContext, ColorType.BACKGROUND));
/* 211 */       paramGraphics.fillRect(0, 0, paramJComponent.getWidth(), paramJComponent.getHeight());
/*     */     } 
/*     */     
/* 214 */     this.style.getPainter(synthContext).paintFileChooserBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 215 */         .getWidth(), paramJComponent.getHeight());
/* 216 */     paint(synthContext, paramGraphics);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 223 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 225 */     paint(synthContext, paramGraphics);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSelectedFileChanged(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */ 
/*     */   
/*     */   protected void doSelectedFilesChanged(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */ 
/*     */   
/*     */   protected void doDirectoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */ 
/*     */   
/*     */   protected void doAccessoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */ 
/*     */   
/*     */   protected void doFileSelectionModeChanged(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */ 
/*     */   
/*     */   protected void doMultiSelectionChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/* 250 */     if (!getFileChooser().isMultiSelectionEnabled()) {
/* 251 */       getFileChooser().setSelectedFiles((File[])null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doControlButtonsChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/* 256 */     if (getFileChooser().getControlButtonsAreShown()) {
/* 257 */       this.approveButton.setText(getApproveButtonText(getFileChooser()));
/* 258 */       this.approveButton.setToolTipText(getApproveButtonToolTipText(getFileChooser()));
/* 259 */       this.approveButton.setMnemonic(getApproveButtonMnemonic(getFileChooser()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doAncestorChanged(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */   
/*     */   public PropertyChangeListener createPropertyChangeListener(JFileChooser paramJFileChooser) {
/* 267 */     return new SynthFCPropertyChangeListener();
/*     */   }
/*     */   
/*     */   private class SynthFCPropertyChangeListener implements PropertyChangeListener {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 272 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 273 */       if (str.equals("fileSelectionChanged")) {
/* 274 */         SynthFileChooserUI.this.doFileSelectionModeChanged(param1PropertyChangeEvent);
/* 275 */       } else if (str.equals("SelectedFileChangedProperty")) {
/* 276 */         SynthFileChooserUI.this.doSelectedFileChanged(param1PropertyChangeEvent);
/* 277 */       } else if (str.equals("SelectedFilesChangedProperty")) {
/* 278 */         SynthFileChooserUI.this.doSelectedFilesChanged(param1PropertyChangeEvent);
/* 279 */       } else if (str.equals("directoryChanged")) {
/* 280 */         SynthFileChooserUI.this.doDirectoryChanged(param1PropertyChangeEvent);
/* 281 */       } else if (str == "MultiSelectionEnabledChangedProperty") {
/* 282 */         SynthFileChooserUI.this.doMultiSelectionChanged(param1PropertyChangeEvent);
/* 283 */       } else if (str == "AccessoryChangedProperty") {
/* 284 */         SynthFileChooserUI.this.doAccessoryChanged(param1PropertyChangeEvent);
/* 285 */       } else if (str == "ApproveButtonTextChangedProperty" || str == "ApproveButtonToolTipTextChangedProperty" || str == "DialogTypeChangedProperty" || str == "ControlButtonsAreShownChangedProperty") {
/*     */ 
/*     */ 
/*     */         
/* 289 */         SynthFileChooserUI.this.doControlButtonsChanged(param1PropertyChangeEvent);
/* 290 */       } else if (str.equals("componentOrientation")) {
/* 291 */         ComponentOrientation componentOrientation = (ComponentOrientation)param1PropertyChangeEvent.getNewValue();
/* 292 */         JFileChooser jFileChooser = (JFileChooser)param1PropertyChangeEvent.getSource();
/* 293 */         if (componentOrientation != (ComponentOrientation)param1PropertyChangeEvent.getOldValue()) {
/* 294 */           jFileChooser.applyComponentOrientation(componentOrientation);
/*     */         }
/* 296 */       } else if (str.equals("ancestor")) {
/* 297 */         SynthFileChooserUI.this.doAncestorChanged(param1PropertyChangeEvent);
/*     */       } 
/*     */     }
/*     */     
/*     */     private SynthFCPropertyChangeListener() {}
/*     */   }
/*     */   
/*     */   private class FileNameCompletionAction
/*     */     extends AbstractAction
/*     */   {
/*     */     protected FileNameCompletionAction() {
/* 308 */       super("fileNameCompletion");
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 312 */       JFileChooser jFileChooser = SynthFileChooserUI.this.getFileChooser();
/*     */       
/* 314 */       String str = SynthFileChooserUI.this.getFileName();
/*     */       
/* 316 */       if (str != null)
/*     */       {
/* 318 */         str = str.trim();
/*     */       }
/*     */       
/* 321 */       SynthFileChooserUI.this.resetGlobFilter();
/*     */       
/* 323 */       if (str == null || str.equals("") || (jFileChooser
/* 324 */         .isMultiSelectionEnabled() && str.startsWith("\""))) {
/*     */         return;
/*     */       }
/*     */       
/* 328 */       FileFilter fileFilter = jFileChooser.getFileFilter();
/* 329 */       if (SynthFileChooserUI.this.globFilter == null) {
/* 330 */         SynthFileChooserUI.this.globFilter = new SynthFileChooserUI.GlobFilter();
/*     */       }
/*     */       try {
/* 333 */         SynthFileChooserUI.this.globFilter.setPattern(!SynthFileChooserUI.isGlobPattern(str) ? (str + "*") : str);
/* 334 */         if (!(fileFilter instanceof SynthFileChooserUI.GlobFilter)) {
/* 335 */           SynthFileChooserUI.this.actualFileFilter = fileFilter;
/*     */         }
/* 337 */         jFileChooser.setFileFilter((FileFilter)null);
/* 338 */         jFileChooser.setFileFilter(SynthFileChooserUI.this.globFilter);
/* 339 */         SynthFileChooserUI.this.fileNameCompletionString = str;
/* 340 */       } catch (PatternSyntaxException patternSyntaxException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateFileNameCompletion() {
/* 349 */     if (this.fileNameCompletionString != null && 
/* 350 */       this.fileNameCompletionString.equals(getFileName())) {
/* 351 */       File[] arrayOfFile = getModel().getFiles().<File>toArray(new File[0]);
/* 352 */       String str = getCommonStartString(arrayOfFile);
/* 353 */       if (str != null && str.startsWith(this.fileNameCompletionString)) {
/* 354 */         setFileName(str);
/*     */       }
/* 356 */       this.fileNameCompletionString = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getCommonStartString(File[] paramArrayOfFile) {
/* 362 */     String str1 = null;
/* 363 */     String str2 = null;
/* 364 */     byte b = 0;
/* 365 */     if (paramArrayOfFile.length == 0) {
/* 366 */       return null;
/*     */     }
/*     */     while (true) {
/* 369 */       for (byte b1 = 0; b1 < paramArrayOfFile.length; b1++) {
/* 370 */         String str = paramArrayOfFile[b1].getName();
/* 371 */         if (b1 == 0) {
/* 372 */           if (str.length() == b) {
/* 373 */             return str1;
/*     */           }
/* 375 */           str2 = str.substring(0, b + 1);
/*     */         } 
/* 377 */         if (!str.startsWith(str2)) {
/* 378 */           return str1;
/*     */         }
/*     */       } 
/* 381 */       str1 = str2;
/* 382 */       b++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetGlobFilter() {
/* 387 */     if (this.actualFileFilter != null) {
/* 388 */       JFileChooser jFileChooser = getFileChooser();
/* 389 */       FileFilter fileFilter = jFileChooser.getFileFilter();
/* 390 */       if (fileFilter != null && fileFilter.equals(this.globFilter)) {
/* 391 */         jFileChooser.setFileFilter(this.actualFileFilter);
/* 392 */         jFileChooser.removeChoosableFileFilter(this.globFilter);
/*     */       } 
/* 394 */       this.actualFileFilter = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isGlobPattern(String paramString) {
/* 399 */     return ((File.separatorChar == '\\' && paramString.indexOf('*') >= 0) || (File.separatorChar == '/' && (paramString
/* 400 */       .indexOf('*') >= 0 || paramString
/* 401 */       .indexOf('?') >= 0 || paramString
/* 402 */       .indexOf('[') >= 0)));
/*     */   }
/*     */ 
/*     */   
/*     */   class GlobFilter
/*     */     extends FileFilter
/*     */   {
/*     */     Pattern pattern;
/*     */     
/*     */     String globPattern;
/*     */     
/*     */     public void setPattern(String param1String) {
/* 414 */       char[] arrayOfChar1 = param1String.toCharArray();
/* 415 */       char[] arrayOfChar2 = new char[arrayOfChar1.length * 2];
/* 416 */       boolean bool1 = (File.separatorChar == '\\') ? true : false;
/* 417 */       boolean bool2 = false;
/* 418 */       byte b = 0;
/*     */       
/* 420 */       this.globPattern = param1String;
/*     */       
/* 422 */       if (bool1) {
/*     */         
/* 424 */         int i = arrayOfChar1.length;
/* 425 */         if (param1String.endsWith("*.*")) {
/* 426 */           i -= 2;
/*     */         }
/* 428 */         for (byte b1 = 0; b1 < i; b1++) {
/* 429 */           if (arrayOfChar1[b1] == '*') {
/* 430 */             arrayOfChar2[b++] = '.';
/*     */           }
/* 432 */           arrayOfChar2[b++] = arrayOfChar1[b1];
/*     */         } 
/*     */       } else {
/* 435 */         for (byte b1 = 0; b1 < arrayOfChar1.length; b1++) {
/* 436 */           switch (arrayOfChar1[b1]) {
/*     */             case '*':
/* 438 */               if (!bool2) {
/* 439 */                 arrayOfChar2[b++] = '.';
/*     */               }
/* 441 */               arrayOfChar2[b++] = '*';
/*     */               break;
/*     */             
/*     */             case '?':
/* 445 */               arrayOfChar2[b++] = bool2 ? '?' : '.';
/*     */               break;
/*     */             
/*     */             case '[':
/* 449 */               bool2 = true;
/* 450 */               arrayOfChar2[b++] = arrayOfChar1[b1];
/*     */               
/* 452 */               if (b1 < arrayOfChar1.length - 1) {
/* 453 */                 switch (arrayOfChar1[b1 + 1]) {
/*     */                   case '!':
/*     */                   case '^':
/* 456 */                     arrayOfChar2[b++] = '^';
/* 457 */                     b1++;
/*     */                     break;
/*     */                   
/*     */                   case ']':
/* 461 */                     arrayOfChar2[b++] = arrayOfChar1[++b1];
/*     */                     break;
/*     */                 } 
/*     */               
/*     */               }
/*     */               break;
/*     */             case ']':
/* 468 */               arrayOfChar2[b++] = arrayOfChar1[b1];
/* 469 */               bool2 = false;
/*     */               break;
/*     */             
/*     */             case '\\':
/* 473 */               if (b1 == 0 && arrayOfChar1.length > 1 && arrayOfChar1[1] == '~') {
/* 474 */                 arrayOfChar2[b++] = arrayOfChar1[++b1]; break;
/*     */               } 
/* 476 */               arrayOfChar2[b++] = '\\';
/* 477 */               if (b1 < arrayOfChar1.length - 1 && "*?[]".indexOf(arrayOfChar1[b1 + 1]) >= 0) {
/* 478 */                 arrayOfChar2[b++] = arrayOfChar1[++b1]; break;
/*     */               } 
/* 480 */               arrayOfChar2[b++] = '\\';
/*     */               break;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             default:
/* 487 */               if (!Character.isLetterOrDigit(arrayOfChar1[b1])) {
/* 488 */                 arrayOfChar2[b++] = '\\';
/*     */               }
/* 490 */               arrayOfChar2[b++] = arrayOfChar1[b1];
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 495 */       this.pattern = Pattern.compile(new String(arrayOfChar2, 0, b), 2);
/*     */     }
/*     */     
/*     */     public boolean accept(File param1File) {
/* 499 */       if (param1File == null) {
/* 500 */         return false;
/*     */       }
/* 502 */       if (param1File.isDirectory()) {
/* 503 */         return true;
/*     */       }
/* 505 */       return this.pattern.matcher(param1File.getName()).matches();
/*     */     }
/*     */     
/*     */     public String getDescription() {
/* 509 */       return this.globPattern;
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
/*     */   public Action getFileNameCompletionAction() {
/* 524 */     return this.fileNameCompletionAction;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JButton getApproveButton(JFileChooser paramJFileChooser) {
/* 529 */     return this.approveButton;
/*     */   } public void clearIconCache() {}
/*     */   protected abstract ActionMap createActionMap();
/*     */   protected JButton getCancelButton(JFileChooser paramJFileChooser) {
/* 533 */     return this.cancelButton;
/*     */   }
/*     */   
/*     */   public abstract void setFileName(String paramString);
/*     */   
/*     */   public abstract String getFileName();
/*     */   
/*     */   private class UIBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource {
/*     */     UIBorder(Insets param1Insets) {
/* 544 */       if (param1Insets != null) {
/* 545 */         this._insets = new Insets(param1Insets.top, param1Insets.left, param1Insets.bottom, param1Insets.right);
/*     */       }
/*     */       else {
/*     */         
/* 549 */         this._insets = null;
/*     */       } 
/*     */     }
/*     */     private Insets _insets;
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 555 */       if (!(param1Component instanceof JComponent)) {
/*     */         return;
/*     */       }
/* 558 */       JComponent jComponent = (JComponent)param1Component;
/* 559 */       SynthContext synthContext = SynthFileChooserUI.this.getContext(jComponent);
/* 560 */       SynthStyle synthStyle = synthContext.getStyle();
/* 561 */       if (synthStyle != null) {
/* 562 */         synthStyle.getPainter(synthContext).paintFileChooserBorder(synthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 568 */       if (param1Insets == null) {
/* 569 */         param1Insets = new Insets(0, 0, 0, 0);
/*     */       }
/* 571 */       if (this._insets != null) {
/* 572 */         param1Insets.top = this._insets.top;
/* 573 */         param1Insets.bottom = this._insets.bottom;
/* 574 */         param1Insets.left = this._insets.left;
/* 575 */         param1Insets.right = this._insets.right;
/*     */       } else {
/*     */         
/* 578 */         param1Insets.top = param1Insets.bottom = param1Insets.right = param1Insets.left = 0;
/*     */       } 
/* 580 */       return param1Insets;
/*     */     }
/*     */     public boolean isBorderOpaque() {
/* 583 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/plaf/synth/SynthFileChooserUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */