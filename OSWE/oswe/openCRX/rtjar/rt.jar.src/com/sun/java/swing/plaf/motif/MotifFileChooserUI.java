/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import javax.swing.AbstractListModel;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.ComboBoxModel;
/*     */ import javax.swing.DefaultListCellRenderer;
/*     */ import javax.swing.DefaultListSelectionModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.ListSelectionModel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.ListDataEvent;
/*     */ import javax.swing.event.ListDataListener;
/*     */ import javax.swing.filechooser.FileFilter;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicFileChooserUI;
/*     */ import sun.awt.shell.ShellFolder;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifFileChooserUI
/*     */   extends BasicFileChooserUI
/*     */ {
/*     */   private FilterComboBoxModel filterComboBoxModel;
/*  52 */   protected JList<File> directoryList = null;
/*  53 */   protected JList<File> fileList = null;
/*     */   
/*  55 */   protected JTextField pathField = null;
/*  56 */   protected JComboBox<FileFilter> filterComboBox = null;
/*  57 */   protected JTextField filenameTextField = null;
/*     */   
/*  59 */   private static final Dimension hstrut10 = new Dimension(10, 1);
/*  60 */   private static final Dimension vstrut10 = new Dimension(1, 10);
/*     */   
/*  62 */   private static final Insets insets = new Insets(10, 10, 10, 10);
/*     */   
/*  64 */   private static Dimension prefListSize = new Dimension(75, 150);
/*     */   
/*  66 */   private static Dimension WITH_ACCELERATOR_PREF_SIZE = new Dimension(650, 450);
/*  67 */   private static Dimension PREF_SIZE = new Dimension(350, 450);
/*     */   private static final int MIN_WIDTH = 200;
/*     */   private static final int MIN_HEIGHT = 300;
/*  70 */   private static Dimension PREF_ACC_SIZE = new Dimension(10, 10);
/*  71 */   private static Dimension ZERO_ACC_SIZE = new Dimension(1, 1);
/*     */   
/*  73 */   private static Dimension MAX_SIZE = new Dimension(32767, 32767);
/*     */   
/*  75 */   private static final Insets buttonMargin = new Insets(3, 3, 3, 3);
/*     */   
/*     */   private JPanel bottomPanel;
/*     */   
/*     */   protected JButton approveButton;
/*     */   
/*  81 */   private String enterFolderNameLabelText = null;
/*  82 */   private int enterFolderNameLabelMnemonic = 0;
/*  83 */   private String enterFileNameLabelText = null;
/*  84 */   private int enterFileNameLabelMnemonic = 0;
/*     */   
/*  86 */   private String filesLabelText = null;
/*  87 */   private int filesLabelMnemonic = 0;
/*     */   
/*  89 */   private String foldersLabelText = null;
/*  90 */   private int foldersLabelMnemonic = 0;
/*     */   
/*  92 */   private String pathLabelText = null;
/*  93 */   private int pathLabelMnemonic = 0;
/*     */   
/*  95 */   private String filterLabelText = null;
/*  96 */   private int filterLabelMnemonic = 0;
/*     */   
/*     */   private JLabel fileNameLabel;
/*     */   
/*     */   private void populateFileNameLabel() {
/* 101 */     if (getFileChooser().getFileSelectionMode() == 1) {
/* 102 */       this.fileNameLabel.setText(this.enterFolderNameLabelText);
/* 103 */       this.fileNameLabel.setDisplayedMnemonic(this.enterFolderNameLabelMnemonic);
/*     */     } else {
/* 105 */       this.fileNameLabel.setText(this.enterFileNameLabelText);
/* 106 */       this.fileNameLabel.setDisplayedMnemonic(this.enterFileNameLabelMnemonic);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String fileNameString(File paramFile) {
/* 111 */     if (paramFile == null) {
/* 112 */       return null;
/*     */     }
/* 114 */     JFileChooser jFileChooser = getFileChooser();
/* 115 */     if (jFileChooser.isDirectorySelectionEnabled() && !jFileChooser.isFileSelectionEnabled()) {
/* 116 */       return paramFile.getPath();
/*     */     }
/* 118 */     return paramFile.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String fileNameString(File[] paramArrayOfFile) {
/* 124 */     StringBuffer stringBuffer = new StringBuffer();
/* 125 */     for (byte b = 0; paramArrayOfFile != null && b < paramArrayOfFile.length; b++) {
/* 126 */       if (b > 0) {
/* 127 */         stringBuffer.append(" ");
/*     */       }
/* 129 */       if (paramArrayOfFile.length > 1) {
/* 130 */         stringBuffer.append("\"");
/*     */       }
/* 132 */       stringBuffer.append(fileNameString(paramArrayOfFile[b]));
/* 133 */       if (paramArrayOfFile.length > 1) {
/* 134 */         stringBuffer.append("\"");
/*     */       }
/*     */     } 
/* 137 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public MotifFileChooserUI(JFileChooser paramJFileChooser) {
/* 141 */     super(paramJFileChooser);
/*     */   }
/*     */   
/*     */   public String getFileName() {
/* 145 */     if (this.filenameTextField != null) {
/* 146 */       return this.filenameTextField.getText();
/*     */     }
/* 148 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFileName(String paramString) {
/* 153 */     if (this.filenameTextField != null) {
/* 154 */       this.filenameTextField.setText(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getDirectoryName() {
/* 159 */     return this.pathField.getText();
/*     */   }
/*     */   
/*     */   public void setDirectoryName(String paramString) {
/* 163 */     this.pathField.setText(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void ensureFileIsVisible(JFileChooser paramJFileChooser, File paramFile) {}
/*     */ 
/*     */   
/*     */   public void rescanCurrentDirectory(JFileChooser paramJFileChooser) {
/* 171 */     getModel().validateFileCache();
/*     */   }
/*     */   
/*     */   public PropertyChangeListener createPropertyChangeListener(JFileChooser paramJFileChooser) {
/* 175 */     return new PropertyChangeListener() {
/*     */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 177 */           String str = param1PropertyChangeEvent.getPropertyName();
/* 178 */           if (str.equals("SelectedFileChangedProperty")) {
/* 179 */             File file = (File)param1PropertyChangeEvent.getNewValue();
/* 180 */             if (file != null) {
/* 181 */               MotifFileChooserUI.this.setFileName(MotifFileChooserUI.this.getFileChooser().getName(file));
/*     */             }
/* 183 */           } else if (str.equals("SelectedFilesChangedProperty")) {
/* 184 */             File[] arrayOfFile = (File[])param1PropertyChangeEvent.getNewValue();
/* 185 */             JFileChooser jFileChooser = MotifFileChooserUI.this.getFileChooser();
/* 186 */             if (arrayOfFile != null && arrayOfFile.length > 0 && (arrayOfFile.length > 1 || jFileChooser.isDirectorySelectionEnabled() || 
/* 187 */               !arrayOfFile[0].isDirectory())) {
/* 188 */               MotifFileChooserUI.this.setFileName(MotifFileChooserUI.this.fileNameString(arrayOfFile));
/*     */             }
/* 190 */           } else if (str.equals("fileFilterChanged")) {
/* 191 */             MotifFileChooserUI.this.fileList.clearSelection();
/* 192 */           } else if (str.equals("directoryChanged")) {
/* 193 */             MotifFileChooserUI.this.directoryList.clearSelection();
/* 194 */             ListSelectionModel listSelectionModel = MotifFileChooserUI.this.directoryList.getSelectionModel();
/* 195 */             if (listSelectionModel instanceof DefaultListSelectionModel) {
/* 196 */               ((DefaultListSelectionModel)listSelectionModel).moveLeadSelectionIndex(0);
/* 197 */               listSelectionModel.setAnchorSelectionIndex(0);
/*     */             } 
/* 199 */             MotifFileChooserUI.this.fileList.clearSelection();
/* 200 */             listSelectionModel = MotifFileChooserUI.this.fileList.getSelectionModel();
/* 201 */             if (listSelectionModel instanceof DefaultListSelectionModel) {
/* 202 */               ((DefaultListSelectionModel)listSelectionModel).moveLeadSelectionIndex(0);
/* 203 */               listSelectionModel.setAnchorSelectionIndex(0);
/*     */             } 
/* 205 */             File file = MotifFileChooserUI.this.getFileChooser().getCurrentDirectory();
/* 206 */             if (file != null) {
/*     */               try {
/* 208 */                 MotifFileChooserUI.this.setDirectoryName(ShellFolder.getNormalizedFile((File)param1PropertyChangeEvent.getNewValue()).getPath());
/* 209 */               } catch (IOException iOException) {
/* 210 */                 MotifFileChooserUI.this.setDirectoryName(((File)param1PropertyChangeEvent.getNewValue()).getAbsolutePath());
/*     */               } 
/* 212 */               if (MotifFileChooserUI.this.getFileChooser().getFileSelectionMode() == 1 && !MotifFileChooserUI.this.getFileChooser().isMultiSelectionEnabled()) {
/* 213 */                 MotifFileChooserUI.this.setFileName(MotifFileChooserUI.this.getDirectoryName());
/*     */               }
/*     */             } 
/* 216 */           } else if (str.equals("fileSelectionChanged")) {
/* 217 */             if (MotifFileChooserUI.this.fileNameLabel != null) {
/* 218 */               MotifFileChooserUI.this.populateFileNameLabel();
/*     */             }
/* 220 */             MotifFileChooserUI.this.directoryList.clearSelection();
/* 221 */           } else if (str.equals("MultiSelectionEnabledChangedProperty")) {
/* 222 */             if (MotifFileChooserUI.this.getFileChooser().isMultiSelectionEnabled()) {
/* 223 */               MotifFileChooserUI.this.fileList.setSelectionMode(2);
/*     */             } else {
/* 225 */               MotifFileChooserUI.this.fileList.setSelectionMode(0);
/* 226 */               MotifFileChooserUI.this.fileList.clearSelection();
/* 227 */               MotifFileChooserUI.this.getFileChooser().setSelectedFiles((File[])null);
/*     */             } 
/* 229 */           } else if (str.equals("AccessoryChangedProperty")) {
/* 230 */             if (MotifFileChooserUI.this.getAccessoryPanel() != null) {
/* 231 */               if (param1PropertyChangeEvent.getOldValue() != null) {
/* 232 */                 MotifFileChooserUI.this.getAccessoryPanel().remove((JComponent)param1PropertyChangeEvent.getOldValue());
/*     */               }
/* 234 */               JComponent jComponent = (JComponent)param1PropertyChangeEvent.getNewValue();
/* 235 */               if (jComponent != null) {
/* 236 */                 MotifFileChooserUI.this.getAccessoryPanel().add(jComponent, "Center");
/* 237 */                 MotifFileChooserUI.this.getAccessoryPanel().setPreferredSize(MotifFileChooserUI.PREF_ACC_SIZE);
/* 238 */                 MotifFileChooserUI.this.getAccessoryPanel().setMaximumSize(MotifFileChooserUI.MAX_SIZE);
/*     */               } else {
/* 240 */                 MotifFileChooserUI.this.getAccessoryPanel().setPreferredSize(MotifFileChooserUI.ZERO_ACC_SIZE);
/* 241 */                 MotifFileChooserUI.this.getAccessoryPanel().setMaximumSize(MotifFileChooserUI.ZERO_ACC_SIZE);
/*     */               } 
/*     */             } 
/* 244 */           } else if (str.equals("ApproveButtonTextChangedProperty") || str
/* 245 */             .equals("ApproveButtonToolTipTextChangedProperty") || str
/* 246 */             .equals("DialogTypeChangedProperty")) {
/* 247 */             MotifFileChooserUI.this.approveButton.setText(MotifFileChooserUI.this.getApproveButtonText(MotifFileChooserUI.this.getFileChooser()));
/* 248 */             MotifFileChooserUI.this.approveButton.setToolTipText(MotifFileChooserUI.this.getApproveButtonToolTipText(MotifFileChooserUI.this.getFileChooser()));
/* 249 */           } else if (str.equals("ControlButtonsAreShownChangedProperty")) {
/* 250 */             MotifFileChooserUI.this.doControlButtonsChanged(param1PropertyChangeEvent);
/* 251 */           } else if (str.equals("componentOrientation")) {
/* 252 */             ComponentOrientation componentOrientation = (ComponentOrientation)param1PropertyChangeEvent.getNewValue();
/* 253 */             JFileChooser jFileChooser = (JFileChooser)param1PropertyChangeEvent.getSource();
/* 254 */             if (componentOrientation != (ComponentOrientation)param1PropertyChangeEvent.getOldValue()) {
/* 255 */               jFileChooser.applyComponentOrientation(componentOrientation);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 266 */     return new MotifFileChooserUI((JFileChooser)paramJComponent);
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 270 */     super.installUI(paramJComponent);
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 274 */     paramJComponent.removePropertyChangeListener(this.filterComboBoxModel);
/* 275 */     this.approveButton.removeActionListener(getApproveSelectionAction());
/* 276 */     this.filenameTextField.removeActionListener(getApproveSelectionAction());
/* 277 */     super.uninstallUI(paramJComponent);
/*     */   }
/*     */   
/*     */   public void installComponents(JFileChooser paramJFileChooser) {
/* 281 */     paramJFileChooser.setLayout(new BorderLayout(10, 10));
/* 282 */     paramJFileChooser.setAlignmentX(0.5F);
/*     */     
/* 284 */     JPanel jPanel1 = new JPanel() {
/*     */         public Insets getInsets() {
/* 286 */           return MotifFileChooserUI.insets;
/*     */         }
/*     */       };
/* 289 */     jPanel1.setInheritsPopupMenu(true);
/* 290 */     align(jPanel1);
/* 291 */     jPanel1.setLayout(new BoxLayout(jPanel1, 3));
/*     */     
/* 293 */     paramJFileChooser.add(jPanel1, "Center");
/*     */ 
/*     */     
/* 296 */     JLabel jLabel = new JLabel(this.pathLabelText);
/* 297 */     jLabel.setDisplayedMnemonic(this.pathLabelMnemonic);
/* 298 */     align(jLabel);
/* 299 */     jPanel1.add(jLabel);
/*     */     
/* 301 */     File file = paramJFileChooser.getCurrentDirectory();
/* 302 */     String str = null;
/* 303 */     if (file != null) {
/* 304 */       str = file.getPath();
/*     */     }
/* 306 */     this.pathField = new JTextField(str) {
/*     */         public Dimension getMaximumSize() {
/* 308 */           Dimension dimension = super.getMaximumSize();
/* 309 */           dimension.height = (getPreferredSize()).height;
/* 310 */           return dimension;
/*     */         }
/*     */       };
/* 313 */     this.pathField.setInheritsPopupMenu(true);
/* 314 */     jLabel.setLabelFor(this.pathField);
/* 315 */     align(this.pathField);
/*     */ 
/*     */     
/* 318 */     this.pathField.addActionListener(getUpdateAction());
/* 319 */     jPanel1.add(this.pathField);
/*     */     
/* 321 */     jPanel1.add(Box.createRigidArea(vstrut10));
/*     */ 
/*     */ 
/*     */     
/* 325 */     JPanel jPanel2 = new JPanel();
/* 326 */     jPanel2.setLayout(new BoxLayout(jPanel2, 2));
/* 327 */     align(jPanel2);
/*     */ 
/*     */     
/* 330 */     JPanel jPanel3 = new JPanel();
/* 331 */     jPanel3.setLayout(new BoxLayout(jPanel3, 3));
/* 332 */     align(jPanel3);
/*     */ 
/*     */     
/* 335 */     jLabel = new JLabel(this.filterLabelText);
/* 336 */     jLabel.setDisplayedMnemonic(this.filterLabelMnemonic);
/* 337 */     align(jLabel);
/* 338 */     jPanel3.add(jLabel);
/*     */     
/* 340 */     this.filterComboBox = new JComboBox<FileFilter>() {
/*     */         public Dimension getMaximumSize() {
/* 342 */           Dimension dimension = super.getMaximumSize();
/* 343 */           dimension.height = (getPreferredSize()).height;
/* 344 */           return dimension;
/*     */         }
/*     */       };
/* 347 */     this.filterComboBox.setInheritsPopupMenu(true);
/* 348 */     jLabel.setLabelFor(this.filterComboBox);
/* 349 */     this.filterComboBoxModel = createFilterComboBoxModel();
/* 350 */     this.filterComboBox.setModel(this.filterComboBoxModel);
/* 351 */     this.filterComboBox.setRenderer(createFilterComboBoxRenderer());
/* 352 */     paramJFileChooser.addPropertyChangeListener(this.filterComboBoxModel);
/* 353 */     align(this.filterComboBox);
/* 354 */     jPanel3.add(this.filterComboBox);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 359 */     jLabel = new JLabel(this.foldersLabelText);
/* 360 */     jLabel.setDisplayedMnemonic(this.foldersLabelMnemonic);
/* 361 */     align(jLabel);
/* 362 */     jPanel3.add(jLabel);
/* 363 */     JScrollPane jScrollPane = createDirectoryList();
/* 364 */     jScrollPane.getVerticalScrollBar().setFocusable(false);
/* 365 */     jScrollPane.getHorizontalScrollBar().setFocusable(false);
/* 366 */     jScrollPane.setInheritsPopupMenu(true);
/* 367 */     jLabel.setLabelFor(jScrollPane.getViewport().getView());
/* 368 */     jPanel3.add(jScrollPane);
/* 369 */     jPanel3.setInheritsPopupMenu(true);
/*     */ 
/*     */ 
/*     */     
/* 373 */     JPanel jPanel4 = new JPanel();
/* 374 */     align(jPanel4);
/* 375 */     jPanel4.setLayout(new BoxLayout(jPanel4, 3));
/* 376 */     jPanel4.setInheritsPopupMenu(true);
/*     */     
/* 378 */     jLabel = new JLabel(this.filesLabelText);
/* 379 */     jLabel.setDisplayedMnemonic(this.filesLabelMnemonic);
/* 380 */     align(jLabel);
/* 381 */     jPanel4.add(jLabel);
/* 382 */     jScrollPane = createFilesList();
/* 383 */     jLabel.setLabelFor(jScrollPane.getViewport().getView());
/* 384 */     jPanel4.add(jScrollPane);
/* 385 */     jScrollPane.setInheritsPopupMenu(true);
/*     */     
/* 387 */     jPanel2.add(jPanel3);
/* 388 */     jPanel2.add(Box.createRigidArea(hstrut10));
/* 389 */     jPanel2.add(jPanel4);
/* 390 */     jPanel2.setInheritsPopupMenu(true);
/*     */     
/* 392 */     JPanel jPanel5 = getAccessoryPanel();
/* 393 */     JComponent jComponent = paramJFileChooser.getAccessory();
/* 394 */     if (jPanel5 != null) {
/* 395 */       if (jComponent == null) {
/* 396 */         jPanel5.setPreferredSize(ZERO_ACC_SIZE);
/* 397 */         jPanel5.setMaximumSize(ZERO_ACC_SIZE);
/*     */       } else {
/* 399 */         getAccessoryPanel().add(jComponent, "Center");
/* 400 */         jPanel5.setPreferredSize(PREF_ACC_SIZE);
/* 401 */         jPanel5.setMaximumSize(MAX_SIZE);
/*     */       } 
/* 403 */       align(jPanel5);
/* 404 */       jPanel2.add(jPanel5);
/* 405 */       jPanel5.setInheritsPopupMenu(true);
/*     */     } 
/* 407 */     jPanel1.add(jPanel2);
/* 408 */     jPanel1.add(Box.createRigidArea(vstrut10));
/*     */ 
/*     */     
/* 411 */     this.fileNameLabel = new JLabel();
/* 412 */     populateFileNameLabel();
/* 413 */     align(this.fileNameLabel);
/* 414 */     jPanel1.add(this.fileNameLabel);
/*     */     
/* 416 */     this.filenameTextField = new JTextField() {
/*     */         public Dimension getMaximumSize() {
/* 418 */           Dimension dimension = super.getMaximumSize();
/* 419 */           dimension.height = (getPreferredSize()).height;
/* 420 */           return dimension;
/*     */         }
/*     */       };
/* 423 */     this.filenameTextField.setInheritsPopupMenu(true);
/* 424 */     this.fileNameLabel.setLabelFor(this.filenameTextField);
/* 425 */     this.filenameTextField.addActionListener(getApproveSelectionAction());
/* 426 */     align(this.filenameTextField);
/* 427 */     this.filenameTextField.setAlignmentX(0.0F);
/* 428 */     jPanel1.add(this.filenameTextField);
/*     */     
/* 430 */     this.bottomPanel = getBottomPanel();
/* 431 */     this.bottomPanel.add(new JSeparator(), "North");
/*     */ 
/*     */     
/* 434 */     JPanel jPanel6 = new JPanel();
/* 435 */     align(jPanel6);
/* 436 */     jPanel6.setLayout(new BoxLayout(jPanel6, 2));
/* 437 */     jPanel6.add(Box.createGlue());
/*     */     
/* 439 */     this.approveButton = new JButton(getApproveButtonText(paramJFileChooser)) {
/*     */         public Dimension getMaximumSize() {
/* 441 */           return new Dimension(MotifFileChooserUI.MAX_SIZE.width, (getPreferredSize()).height);
/*     */         }
/*     */       };
/* 444 */     this.approveButton.setMnemonic(getApproveButtonMnemonic(paramJFileChooser));
/* 445 */     this.approveButton.setToolTipText(getApproveButtonToolTipText(paramJFileChooser));
/* 446 */     this.approveButton.setInheritsPopupMenu(true);
/* 447 */     align(this.approveButton);
/* 448 */     this.approveButton.setMargin(buttonMargin);
/* 449 */     this.approveButton.addActionListener(getApproveSelectionAction());
/* 450 */     jPanel6.add(this.approveButton);
/* 451 */     jPanel6.add(Box.createGlue());
/*     */     
/* 453 */     JButton jButton1 = new JButton(this.updateButtonText) {
/*     */         public Dimension getMaximumSize() {
/* 455 */           return new Dimension(MotifFileChooserUI.MAX_SIZE.width, (getPreferredSize()).height);
/*     */         }
/*     */       };
/* 458 */     jButton1.setMnemonic(this.updateButtonMnemonic);
/* 459 */     jButton1.setToolTipText(this.updateButtonToolTipText);
/* 460 */     jButton1.setInheritsPopupMenu(true);
/* 461 */     align(jButton1);
/* 462 */     jButton1.setMargin(buttonMargin);
/* 463 */     jButton1.addActionListener(getUpdateAction());
/* 464 */     jPanel6.add(jButton1);
/* 465 */     jPanel6.add(Box.createGlue());
/*     */     
/* 467 */     JButton jButton2 = new JButton(this.cancelButtonText) {
/*     */         public Dimension getMaximumSize() {
/* 469 */           return new Dimension(MotifFileChooserUI.MAX_SIZE.width, (getPreferredSize()).height);
/*     */         }
/*     */       };
/* 472 */     jButton2.setMnemonic(this.cancelButtonMnemonic);
/* 473 */     jButton2.setToolTipText(this.cancelButtonToolTipText);
/* 474 */     jButton2.setInheritsPopupMenu(true);
/* 475 */     align(jButton2);
/* 476 */     jButton2.setMargin(buttonMargin);
/* 477 */     jButton2.addActionListener(getCancelSelectionAction());
/* 478 */     jPanel6.add(jButton2);
/* 479 */     jPanel6.add(Box.createGlue());
/*     */     
/* 481 */     JButton jButton3 = new JButton(this.helpButtonText) {
/*     */         public Dimension getMaximumSize() {
/* 483 */           return new Dimension(MotifFileChooserUI.MAX_SIZE.width, (getPreferredSize()).height);
/*     */         }
/*     */       };
/* 486 */     jButton3.setMnemonic(this.helpButtonMnemonic);
/* 487 */     jButton3.setToolTipText(this.helpButtonToolTipText);
/* 488 */     align(jButton3);
/* 489 */     jButton3.setMargin(buttonMargin);
/* 490 */     jButton3.setEnabled(false);
/* 491 */     jButton3.setInheritsPopupMenu(true);
/* 492 */     jPanel6.add(jButton3);
/* 493 */     jPanel6.add(Box.createGlue());
/* 494 */     jPanel6.setInheritsPopupMenu(true);
/*     */     
/* 496 */     this.bottomPanel.add(jPanel6, "South");
/* 497 */     this.bottomPanel.setInheritsPopupMenu(true);
/* 498 */     if (paramJFileChooser.getControlButtonsAreShown()) {
/* 499 */       paramJFileChooser.add(this.bottomPanel, "South");
/*     */     }
/*     */   }
/*     */   
/*     */   protected JPanel getBottomPanel() {
/* 504 */     if (this.bottomPanel == null) {
/* 505 */       this.bottomPanel = new JPanel(new BorderLayout(0, 4));
/*     */     }
/* 507 */     return this.bottomPanel;
/*     */   }
/*     */   
/*     */   private void doControlButtonsChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/* 511 */     if (getFileChooser().getControlButtonsAreShown()) {
/* 512 */       getFileChooser().add(this.bottomPanel, "South");
/*     */     } else {
/* 514 */       getFileChooser().remove(getBottomPanel());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void uninstallComponents(JFileChooser paramJFileChooser) {
/* 519 */     paramJFileChooser.removeAll();
/* 520 */     this.bottomPanel = null;
/* 521 */     if (this.filterComboBoxModel != null) {
/* 522 */       paramJFileChooser.removePropertyChangeListener(this.filterComboBoxModel);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void installStrings(JFileChooser paramJFileChooser) {
/* 527 */     super.installStrings(paramJFileChooser);
/*     */     
/* 529 */     Locale locale = paramJFileChooser.getLocale();
/*     */     
/* 531 */     this.enterFolderNameLabelText = UIManager.getString("FileChooser.enterFolderNameLabelText", locale);
/* 532 */     this.enterFolderNameLabelMnemonic = getMnemonic("FileChooser.enterFolderNameLabelMnemonic", locale).intValue();
/* 533 */     this.enterFileNameLabelText = UIManager.getString("FileChooser.enterFileNameLabelText", locale);
/* 534 */     this.enterFileNameLabelMnemonic = getMnemonic("FileChooser.enterFileNameLabelMnemonic", locale).intValue();
/*     */     
/* 536 */     this.filesLabelText = UIManager.getString("FileChooser.filesLabelText", locale);
/* 537 */     this.filesLabelMnemonic = getMnemonic("FileChooser.filesLabelMnemonic", locale).intValue();
/*     */     
/* 539 */     this.foldersLabelText = UIManager.getString("FileChooser.foldersLabelText", locale);
/* 540 */     this.foldersLabelMnemonic = getMnemonic("FileChooser.foldersLabelMnemonic", locale).intValue();
/*     */     
/* 542 */     this.pathLabelText = UIManager.getString("FileChooser.pathLabelText", locale);
/* 543 */     this.pathLabelMnemonic = getMnemonic("FileChooser.pathLabelMnemonic", locale).intValue();
/*     */     
/* 545 */     this.filterLabelText = UIManager.getString("FileChooser.filterLabelText", locale);
/* 546 */     this.filterLabelMnemonic = getMnemonic("FileChooser.filterLabelMnemonic", locale).intValue();
/*     */   }
/*     */   
/*     */   private Integer getMnemonic(String paramString, Locale paramLocale) {
/* 550 */     return Integer.valueOf(SwingUtilities2.getUIDefaultsInt(paramString, paramLocale));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installIcons(JFileChooser paramJFileChooser) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallIcons(JFileChooser paramJFileChooser) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected JScrollPane createFilesList() {
/* 564 */     this.fileList = new JList<>();
/*     */     
/* 566 */     if (getFileChooser().isMultiSelectionEnabled()) {
/* 567 */       this.fileList.setSelectionMode(2);
/*     */     } else {
/* 569 */       this.fileList.setSelectionMode(0);
/*     */     } 
/*     */     
/* 572 */     this.fileList.setModel(new MotifFileListModel());
/* 573 */     this.fileList.getSelectionModel().removeSelectionInterval(0, 0);
/* 574 */     this.fileList.setCellRenderer(new FileCellRenderer());
/* 575 */     this.fileList.addListSelectionListener(createListSelectionListener(getFileChooser()));
/* 576 */     this.fileList.addMouseListener(createDoubleClickListener(getFileChooser(), this.fileList));
/* 577 */     this.fileList.addMouseListener(new MouseAdapter() {
/*     */           public void mouseClicked(MouseEvent param1MouseEvent) {
/* 579 */             JFileChooser jFileChooser = MotifFileChooserUI.this.getFileChooser();
/* 580 */             if (SwingUtilities.isLeftMouseButton(param1MouseEvent) && !jFileChooser.isMultiSelectionEnabled()) {
/* 581 */               int i = SwingUtilities2.loc2IndexFileList(MotifFileChooserUI.this.fileList, param1MouseEvent.getPoint());
/* 582 */               if (i >= 0) {
/* 583 */                 File file = MotifFileChooserUI.this.fileList.getModel().getElementAt(i);
/* 584 */                 MotifFileChooserUI.this.setFileName(jFileChooser.getName(file));
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/* 589 */     align(this.fileList);
/* 590 */     JScrollPane jScrollPane = new JScrollPane(this.fileList);
/* 591 */     jScrollPane.setPreferredSize(prefListSize);
/* 592 */     jScrollPane.setMaximumSize(MAX_SIZE);
/* 593 */     align(jScrollPane);
/* 594 */     this.fileList.setInheritsPopupMenu(true);
/* 595 */     jScrollPane.setInheritsPopupMenu(true);
/* 596 */     return jScrollPane;
/*     */   }
/*     */   
/*     */   protected JScrollPane createDirectoryList() {
/* 600 */     this.directoryList = new JList<>();
/* 601 */     align(this.directoryList);
/*     */     
/* 603 */     this.directoryList.setCellRenderer(new DirectoryCellRenderer());
/* 604 */     this.directoryList.setModel(new MotifDirectoryListModel());
/* 605 */     this.directoryList.getSelectionModel().removeSelectionInterval(0, 0);
/* 606 */     this.directoryList.addMouseListener(createDoubleClickListener(getFileChooser(), this.directoryList));
/* 607 */     this.directoryList.addListSelectionListener(createListSelectionListener(getFileChooser()));
/* 608 */     this.directoryList.setInheritsPopupMenu(true);
/*     */     
/* 610 */     JScrollPane jScrollPane = new JScrollPane(this.directoryList);
/* 611 */     jScrollPane.setMaximumSize(MAX_SIZE);
/* 612 */     jScrollPane.setPreferredSize(prefListSize);
/* 613 */     jScrollPane.setInheritsPopupMenu(true);
/* 614 */     align(jScrollPane);
/* 615 */     return jScrollPane;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 621 */     Dimension dimension1 = (getFileChooser().getAccessory() != null) ? WITH_ACCELERATOR_PREF_SIZE : PREF_SIZE;
/* 622 */     Dimension dimension2 = paramJComponent.getLayout().preferredLayoutSize(paramJComponent);
/* 623 */     if (dimension2 != null) {
/* 624 */       return new Dimension((dimension2.width < dimension1.width) ? dimension1.width : dimension2.width, (dimension2.height < dimension1.height) ? dimension1.height : dimension2.height);
/*     */     }
/*     */     
/* 627 */     return dimension1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 633 */     return new Dimension(200, 300);
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 638 */     return new Dimension(2147483647, 2147483647);
/*     */   }
/*     */   
/*     */   protected void align(JComponent paramJComponent) {
/* 642 */     paramJComponent.setAlignmentX(0.0F);
/* 643 */     paramJComponent.setAlignmentY(0.0F);
/*     */   }
/*     */   
/*     */   protected class FileCellRenderer
/*     */     extends DefaultListCellRenderer
/*     */   {
/*     */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 650 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/* 651 */       setText(MotifFileChooserUI.this.getFileChooser().getName((File)param1Object));
/* 652 */       setInheritsPopupMenu(true);
/* 653 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class DirectoryCellRenderer
/*     */     extends DefaultListCellRenderer
/*     */   {
/*     */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 661 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/* 662 */       setText(MotifFileChooserUI.this.getFileChooser().getName((File)param1Object));
/* 663 */       setInheritsPopupMenu(true);
/* 664 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class MotifDirectoryListModel extends AbstractListModel<File> implements ListDataListener {
/*     */     public MotifDirectoryListModel() {
/* 670 */       MotifFileChooserUI.this.getModel().addListDataListener(this);
/*     */     }
/*     */     
/*     */     public int getSize() {
/* 674 */       return MotifFileChooserUI.this.getModel().getDirectories().size();
/*     */     }
/*     */     
/*     */     public File getElementAt(int param1Int) {
/* 678 */       return MotifFileChooserUI.this.getModel().getDirectories().elementAt(param1Int);
/*     */     }
/*     */     
/*     */     public void intervalAdded(ListDataEvent param1ListDataEvent) {
/* 682 */       fireIntervalAdded(this, param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/*     */     }
/*     */     
/*     */     public void intervalRemoved(ListDataEvent param1ListDataEvent) {
/* 686 */       fireIntervalRemoved(this, param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fireContentsChanged() {
/* 693 */       fireContentsChanged(this, 0, MotifFileChooserUI.this.getModel().getDirectories().size() - 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void contentsChanged(ListDataEvent param1ListDataEvent) {
/* 699 */       fireContentsChanged();
/*     */     }
/*     */   }
/*     */   
/*     */   protected class MotifFileListModel
/*     */     extends AbstractListModel<File> implements ListDataListener {
/*     */     public MotifFileListModel() {
/* 706 */       MotifFileChooserUI.this.getModel().addListDataListener(this);
/*     */     }
/*     */     
/*     */     public int getSize() {
/* 710 */       return MotifFileChooserUI.this.getModel().getFiles().size();
/*     */     }
/*     */     
/*     */     public boolean contains(Object param1Object) {
/* 714 */       return MotifFileChooserUI.this.getModel().getFiles().contains(param1Object);
/*     */     }
/*     */     
/*     */     public int indexOf(Object param1Object) {
/* 718 */       return MotifFileChooserUI.this.getModel().getFiles().indexOf(param1Object);
/*     */     }
/*     */     
/*     */     public File getElementAt(int param1Int) {
/* 722 */       return MotifFileChooserUI.this.getModel().getFiles().elementAt(param1Int);
/*     */     }
/*     */     
/*     */     public void intervalAdded(ListDataEvent param1ListDataEvent) {
/* 726 */       fireIntervalAdded(this, param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/*     */     }
/*     */     
/*     */     public void intervalRemoved(ListDataEvent param1ListDataEvent) {
/* 730 */       fireIntervalRemoved(this, param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fireContentsChanged() {
/* 737 */       fireContentsChanged(this, 0, MotifFileChooserUI.this.getModel().getFiles().size() - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void contentsChanged(ListDataEvent param1ListDataEvent) {
/* 742 */       fireContentsChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FilterComboBoxModel createFilterComboBoxModel() {
/* 751 */     return new FilterComboBoxModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FilterComboBoxRenderer createFilterComboBoxRenderer() {
/* 758 */     return new FilterComboBoxRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class FilterComboBoxRenderer
/*     */     extends DefaultListCellRenderer
/*     */   {
/*     */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 770 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/*     */       
/* 772 */       if (param1Object != null && param1Object instanceof FileFilter) {
/* 773 */         setText(((FileFilter)param1Object).getDescription());
/*     */       }
/*     */       
/* 776 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class FilterComboBoxModel
/*     */     extends AbstractListModel<FileFilter>
/*     */     implements ComboBoxModel<FileFilter>, PropertyChangeListener
/*     */   {
/* 788 */     protected FileFilter[] filters = MotifFileChooserUI.this.getFileChooser().getChoosableFileFilters();
/*     */ 
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 792 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 793 */       if (str.equals("ChoosableFileFilterChangedProperty")) {
/* 794 */         this.filters = (FileFilter[])param1PropertyChangeEvent.getNewValue();
/* 795 */         fireContentsChanged(this, -1, -1);
/* 796 */       } else if (str.equals("fileFilterChanged")) {
/* 797 */         fireContentsChanged(this, -1, -1);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setSelectedItem(Object param1Object) {
/* 802 */       if (param1Object != null) {
/* 803 */         MotifFileChooserUI.this.getFileChooser().setFileFilter((FileFilter)param1Object);
/* 804 */         fireContentsChanged(this, -1, -1);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getSelectedItem() {
/* 814 */       FileFilter fileFilter = MotifFileChooserUI.this.getFileChooser().getFileFilter();
/* 815 */       boolean bool = false;
/* 816 */       if (fileFilter != null) {
/* 817 */         for (FileFilter fileFilter1 : this.filters) {
/* 818 */           if (fileFilter1 == fileFilter) {
/* 819 */             bool = true;
/*     */           }
/*     */         } 
/* 822 */         if (!bool) {
/* 823 */           MotifFileChooserUI.this.getFileChooser().addChoosableFileFilter(fileFilter);
/*     */         }
/*     */       } 
/* 826 */       return MotifFileChooserUI.this.getFileChooser().getFileFilter();
/*     */     }
/*     */     
/*     */     public int getSize() {
/* 830 */       if (this.filters != null) {
/* 831 */         return this.filters.length;
/*     */       }
/* 833 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public FileFilter getElementAt(int param1Int) {
/* 838 */       if (param1Int > getSize() - 1)
/*     */       {
/* 840 */         return MotifFileChooserUI.this.getFileChooser().getFileFilter();
/*     */       }
/* 842 */       if (this.filters != null) {
/* 843 */         return this.filters[param1Int];
/*     */       }
/* 845 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected JButton getApproveButton(JFileChooser paramJFileChooser) {
/* 851 */     return this.approveButton;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifFileChooserUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */