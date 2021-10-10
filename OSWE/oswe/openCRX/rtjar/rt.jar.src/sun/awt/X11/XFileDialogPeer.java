/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Button;
/*     */ import java.awt.Choice;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FileDialog;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.KeyEventDispatcher;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Label;
/*     */ import java.awt.List;
/*     */ import java.awt.Panel;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.peer.FileDialogPeer;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import javax.swing.UIDefaults;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ class XFileDialogPeer
/*     */   extends XDialogPeer
/*     */   implements FileDialogPeer, ActionListener, ItemListener, KeyEventDispatcher, XChoicePeerListener {
/*  43 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XFileDialogPeer");
/*     */ 
/*     */   
/*     */   FileDialog target;
/*     */ 
/*     */   
/*     */   String file;
/*     */ 
/*     */   
/*     */   String dir;
/*     */ 
/*     */   
/*     */   String title;
/*     */ 
/*     */   
/*     */   int mode;
/*     */ 
/*     */   
/*     */   FilenameFilter filter;
/*     */ 
/*     */   
/*     */   private static final int PATH_CHOICE_WIDTH = 20;
/*     */ 
/*     */   
/*     */   String savedFile;
/*     */ 
/*     */   
/*     */   String savedDir;
/*     */ 
/*     */   
/*     */   String userDir;
/*     */ 
/*     */   
/*     */   Dialog fileDialog;
/*     */ 
/*     */   
/*     */   GridBagLayout gbl;
/*     */ 
/*     */   
/*     */   GridBagLayout gblButtons;
/*     */   
/*     */   GridBagConstraints gbc;
/*     */   
/*     */   TextField filterField;
/*     */   
/*     */   TextField selectionField;
/*     */   
/*     */   List directoryList;
/*     */   
/*     */   List fileList;
/*     */   
/*     */   Panel buttons;
/*     */   
/*     */   Button openButton;
/*     */   
/*     */   Button filterButton;
/*     */   
/*     */   Button cancelButton;
/*     */   
/*     */   Choice pathChoice;
/*     */   
/*     */   TextField pathField;
/*     */   
/*     */   Panel pathPanel;
/*     */   
/* 108 */   String cancelButtonText = null;
/* 109 */   String enterFileNameLabelText = null;
/* 110 */   String filesLabelText = null;
/* 111 */   String foldersLabelText = null;
/* 112 */   String pathLabelText = null;
/* 113 */   String filterLabelText = null;
/* 114 */   String openButtonText = null;
/* 115 */   String saveButtonText = null;
/* 116 */   String actionButtonText = null;
/*     */ 
/*     */   
/*     */   void installStrings() {
/* 120 */     Locale locale = this.target.getLocale();
/* 121 */     UIDefaults uIDefaults = XToolkit.getUIDefaults();
/* 122 */     this.cancelButtonText = uIDefaults.getString("FileChooser.cancelButtonText", locale);
/* 123 */     this.enterFileNameLabelText = uIDefaults.getString("FileChooser.enterFileNameLabelText", locale);
/* 124 */     this.filesLabelText = uIDefaults.getString("FileChooser.filesLabelText", locale);
/* 125 */     this.foldersLabelText = uIDefaults.getString("FileChooser.foldersLabelText", locale);
/* 126 */     this.pathLabelText = uIDefaults.getString("FileChooser.pathLabelText", locale);
/* 127 */     this.filterLabelText = uIDefaults.getString("FileChooser.filterLabelText", locale);
/* 128 */     this.openButtonText = uIDefaults.getString("FileChooser.openButtonText", locale);
/* 129 */     this.saveButtonText = uIDefaults.getString("FileChooser.saveButtonText", locale);
/*     */   }
/*     */ 
/*     */   
/*     */   XFileDialogPeer(FileDialog paramFileDialog) {
/* 134 */     super(paramFileDialog);
/* 135 */     this.target = paramFileDialog;
/*     */   }
/*     */   
/*     */   private void init(FileDialog paramFileDialog) {
/* 139 */     this.fileDialog = paramFileDialog;
/* 140 */     this.title = paramFileDialog.getTitle();
/* 141 */     this.mode = paramFileDialog.getMode();
/* 142 */     this.target = paramFileDialog;
/* 143 */     this.filter = paramFileDialog.getFilenameFilter();
/*     */     
/* 145 */     this.savedFile = paramFileDialog.getFile();
/* 146 */     this.savedDir = paramFileDialog.getDirectory();
/*     */ 
/*     */     
/* 149 */     this.userDir = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public Object run() {
/* 152 */             return System.getProperty("user.dir");
/*     */           }
/*     */         });
/*     */     
/* 156 */     installStrings();
/* 157 */     this.gbl = new GridBagLayout();
/* 158 */     this.gblButtons = new GridBagLayout();
/* 159 */     this.gbc = new GridBagConstraints();
/* 160 */     this.fileDialog.setLayout(this.gbl);
/*     */ 
/*     */     
/* 163 */     this.buttons = new Panel();
/* 164 */     this.buttons.setLayout(this.gblButtons);
/* 165 */     this.actionButtonText = (paramFileDialog.getMode() == 1) ? this.saveButtonText : this.openButtonText;
/* 166 */     this.openButton = new Button(this.actionButtonText);
/*     */     
/* 168 */     this.filterButton = new Button(this.filterLabelText);
/* 169 */     this.cancelButton = new Button(this.cancelButtonText);
/* 170 */     this.directoryList = new List();
/* 171 */     this.fileList = new List();
/* 172 */     this.filterField = new TextField();
/* 173 */     this.selectionField = new TextField();
/*     */ 
/*     */     
/* 176 */     boolean bool = AWTAccessor.getFileDialogAccessor().isMultipleMode(paramFileDialog);
/* 177 */     this.fileList.setMultipleMode(bool);
/*     */ 
/*     */     
/* 180 */     Insets insets1 = new Insets(0, 0, 0, 0);
/* 181 */     Insets insets2 = new Insets(0, 8, 0, 8);
/* 182 */     Insets insets3 = new Insets(0, 8, 0, 4);
/* 183 */     Insets insets4 = new Insets(0, 4, 0, 8);
/* 184 */     Insets insets5 = new Insets(8, 0, 0, 0);
/* 185 */     Insets insets6 = new Insets(0, 8, 0, 0);
/* 186 */     Insets insets7 = new Insets(10, 8, 10, 8);
/*     */ 
/*     */ 
/*     */     
/* 190 */     Font font = new Font("Dialog", 0, 12);
/*     */     
/* 192 */     Label label = new Label(this.pathLabelText);
/* 193 */     label.setFont(font);
/* 194 */     addComponent(label, this.gbl, this.gbc, 0, 0, 1, 17, this.fileDialog, 1, 0, 0, insets6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     this.pathField = new TextField((this.savedDir != null) ? this.savedDir : this.userDir);
/*     */     
/* 203 */     this.pathChoice = new Choice() {
/*     */         public Dimension getPreferredSize() {
/* 205 */           return new Dimension(20, (XFileDialogPeer.this.pathField.getPreferredSize()).height);
/*     */         }
/*     */       };
/* 208 */     this.pathPanel = new Panel();
/* 209 */     this.pathPanel.setLayout(new BorderLayout());
/*     */     
/* 211 */     this.pathPanel.add(this.pathField, "Center");
/* 212 */     this.pathPanel.add(this.pathChoice, "East");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     addComponent(this.pathPanel, this.gbl, this.gbc, 0, 1, 2, 17, this.fileDialog, 1, 0, 2, insets2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     label = new Label(this.filterLabelText);
/*     */     
/* 227 */     label.setFont(font);
/* 228 */     addComponent(label, this.gbl, this.gbc, 0, 2, 1, 17, this.fileDialog, 1, 0, 0, insets6);
/*     */ 
/*     */     
/* 231 */     addComponent(this.filterField, this.gbl, this.gbc, 0, 3, 2, 17, this.fileDialog, 1, 0, 2, insets2);
/*     */ 
/*     */ 
/*     */     
/* 235 */     label = new Label(this.foldersLabelText);
/*     */     
/* 237 */     label.setFont(font);
/* 238 */     addComponent(label, this.gbl, this.gbc, 0, 4, 1, 17, this.fileDialog, 1, 0, 0, insets6);
/*     */ 
/*     */ 
/*     */     
/* 242 */     label = new Label(this.filesLabelText);
/*     */     
/* 244 */     label.setFont(font);
/* 245 */     addComponent(label, this.gbl, this.gbc, 1, 4, 1, 17, this.fileDialog, 1, 0, 0, insets6);
/*     */ 
/*     */     
/* 248 */     addComponent(this.directoryList, this.gbl, this.gbc, 0, 5, 1, 17, this.fileDialog, 1, 1, 1, insets3);
/*     */ 
/*     */     
/* 251 */     addComponent(this.fileList, this.gbl, this.gbc, 1, 5, 1, 17, this.fileDialog, 1, 1, 1, insets4);
/*     */ 
/*     */ 
/*     */     
/* 255 */     label = new Label(this.enterFileNameLabelText);
/*     */     
/* 257 */     label.setFont(font);
/* 258 */     addComponent(label, this.gbl, this.gbc, 0, 6, 1, 17, this.fileDialog, 1, 0, 0, insets6);
/*     */ 
/*     */     
/* 261 */     addComponent(this.selectionField, this.gbl, this.gbc, 0, 7, 2, 17, this.fileDialog, 1, 0, 2, insets2);
/*     */ 
/*     */     
/* 264 */     addComponent(new Separator((this.fileDialog.size()).width, 2, 0), this.gbl, this.gbc, 0, 8, 15, 17, this.fileDialog, 1, 0, 2, insets5);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     addComponent(this.openButton, this.gblButtons, this.gbc, 0, 0, 1, 17, this.buttons, 1, 0, 0, insets1);
/*     */ 
/*     */     
/* 272 */     addComponent(this.filterButton, this.gblButtons, this.gbc, 1, 0, 1, 10, this.buttons, 1, 0, 0, insets1);
/*     */ 
/*     */     
/* 275 */     addComponent(this.cancelButton, this.gblButtons, this.gbc, 2, 0, 1, 13, this.buttons, 1, 0, 0, insets1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     addComponent(this.buttons, this.gbl, this.gbc, 0, 9, 2, 17, this.fileDialog, 1, 0, 2, insets7);
/*     */ 
/*     */ 
/*     */     
/* 284 */     this.fileDialog.setSize(400, 400);
/*     */ 
/*     */     
/* 287 */     XChoicePeer xChoicePeer = (XChoicePeer)this.pathChoice.getPeer();
/* 288 */     xChoicePeer.setDrawSelectedItem(false);
/* 289 */     xChoicePeer.setAlignUnder(this.pathField);
/*     */     
/* 291 */     this.filterField.addActionListener(this);
/* 292 */     this.selectionField.addActionListener(this);
/* 293 */     this.directoryList.addActionListener(this);
/* 294 */     this.directoryList.addItemListener(this);
/* 295 */     this.fileList.addItemListener(this);
/* 296 */     this.fileList.addActionListener(this);
/* 297 */     this.openButton.addActionListener(this);
/* 298 */     this.filterButton.addActionListener(this);
/* 299 */     this.cancelButton.addActionListener(this);
/* 300 */     this.pathChoice.addItemListener(this);
/* 301 */     this.pathField.addActionListener(this);
/*     */ 
/*     */     
/* 304 */     paramFileDialog.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent param1WindowEvent) {
/* 307 */             XFileDialogPeer.this.handleCancel();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 313 */     this.pathChoice.addItemListener(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateMinimumSize() {}
/*     */ 
/*     */   
/*     */   public void updateIconImages() {
/* 321 */     if (this.winAttr.icons == null) {
/* 322 */       this.winAttr.iconsInherited = false;
/* 323 */       this.winAttr.icons = getDefaultIconInfo();
/* 324 */       setIconHints(this.winAttr.icons);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addComponent(Component paramComponent, GridBagLayout paramGridBagLayout, GridBagConstraints paramGridBagConstraints, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Container paramContainer, int paramInt5, int paramInt6, int paramInt7, Insets paramInsets) {
/* 335 */     paramGridBagConstraints.gridx = paramInt1;
/* 336 */     paramGridBagConstraints.gridy = paramInt2;
/* 337 */     paramGridBagConstraints.gridwidth = paramInt3;
/* 338 */     paramGridBagConstraints.anchor = paramInt4;
/* 339 */     paramGridBagConstraints.weightx = paramInt5;
/* 340 */     paramGridBagConstraints.weighty = paramInt6;
/* 341 */     paramGridBagConstraints.fill = paramInt7;
/* 342 */     paramGridBagConstraints.insets = paramInsets;
/* 343 */     paramGridBagLayout.setConstraints(paramComponent, paramGridBagConstraints);
/* 344 */     paramContainer.add(paramComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getFileName(String paramString) {
/* 351 */     if (paramString == null) {
/* 352 */       return "";
/*     */     }
/*     */     
/* 355 */     int i = paramString.lastIndexOf('/');
/*     */     
/* 357 */     if (i == -1) {
/* 358 */       return paramString;
/*     */     }
/* 360 */     return paramString.substring(i + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handleFilter(String paramString) {
/* 369 */     if (paramString == null) {
/*     */       return;
/*     */     }
/* 372 */     setFilterEntry(this.dir, paramString);
/*     */ 
/*     */ 
/*     */     
/* 376 */     this.directoryList.select(0);
/* 377 */     if (this.fileList.getItemCount() != 0) {
/* 378 */       this.fileList.requestFocus();
/*     */     } else {
/* 380 */       this.directoryList.requestFocus();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handleSelection(String paramString) {
/* 389 */     int i = paramString.lastIndexOf(File.separatorChar);
/*     */     
/* 391 */     if (i == -1) {
/* 392 */       this.savedDir = this.dir;
/* 393 */       this.savedFile = paramString;
/*     */     } else {
/* 395 */       this.savedDir = paramString.substring(0, i + 1);
/* 396 */       this.savedFile = paramString.substring(i + 1);
/*     */     } 
/*     */     
/* 399 */     String[] arrayOfString = this.fileList.getSelectedItems();
/* 400 */     byte b1 = (arrayOfString != null) ? arrayOfString.length : 0;
/* 401 */     File[] arrayOfFile = new File[b1];
/* 402 */     for (byte b2 = 0; b2 < b1; b2++) {
/* 403 */       arrayOfFile[b2] = new File(this.savedDir, arrayOfString[b2]);
/*     */     }
/*     */     
/* 406 */     AWTAccessor.FileDialogAccessor fileDialogAccessor = AWTAccessor.getFileDialogAccessor();
/*     */     
/* 408 */     fileDialogAccessor.setDirectory(this.target, this.savedDir);
/* 409 */     fileDialogAccessor.setFile(this.target, this.savedFile);
/* 410 */     fileDialogAccessor.setFiles(this.target, arrayOfFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handleCancel() {
/* 417 */     KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 418 */       .removeKeyEventDispatcher(this);
/*     */     
/* 420 */     setSelectionField((String)null);
/* 421 */     setFilterField((String)null);
/* 422 */     this.directoryList.clear();
/* 423 */     this.fileList.clear();
/*     */     
/* 425 */     AWTAccessor.FileDialogAccessor fileDialogAccessor = AWTAccessor.getFileDialogAccessor();
/*     */     
/* 427 */     fileDialogAccessor.setDirectory(this.target, null);
/* 428 */     fileDialogAccessor.setFile(this.target, null);
/* 429 */     fileDialogAccessor.setFiles(this.target, null);
/*     */     
/* 431 */     handleQuitButton();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handleQuitButton() {
/* 438 */     this.dir = null;
/* 439 */     this.file = null;
/* 440 */     this.target.hide();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setFilterEntry(String paramString1, String paramString2) {
/* 447 */     File file = new File(paramString1);
/*     */     
/* 449 */     if (file.isDirectory() && file.canRead()) {
/*     */       String[] arrayOfString;
/*     */       
/* 452 */       setSelectionField(this.target.getFile());
/*     */       
/* 454 */       if (paramString2.equals("")) {
/* 455 */         paramString2 = "*";
/* 456 */         setFilterField(paramString2);
/*     */       } else {
/* 458 */         setFilterField(paramString2);
/*     */       } 
/*     */ 
/*     */       
/* 462 */       if (paramString2.equals("*")) {
/* 463 */         arrayOfString = file.list();
/*     */       } else {
/*     */         
/* 466 */         FileDialogFilter fileDialogFilter = new FileDialogFilter(paramString2);
/* 467 */         arrayOfString = file.list(fileDialogFilter);
/*     */       } 
/*     */       
/* 470 */       if (arrayOfString == null) {
/* 471 */         this.dir = getParentDirectory();
/*     */         return;
/*     */       } 
/* 474 */       this.directoryList.clear();
/* 475 */       this.fileList.clear();
/* 476 */       this.directoryList.setVisible(false);
/* 477 */       this.fileList.setVisible(false);
/*     */       
/* 479 */       this.directoryList.addItem("..");
/* 480 */       Arrays.sort((Object[])arrayOfString);
/* 481 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 482 */         File file1 = new File(paramString1 + arrayOfString[b]);
/* 483 */         if (file1.isDirectory()) {
/* 484 */           this.directoryList.addItem(arrayOfString[b] + "/");
/*     */         }
/* 486 */         else if (this.filter != null) {
/* 487 */           if (this.filter.accept(new File(arrayOfString[b]), arrayOfString[b])) this.fileList.addItem(arrayOfString[b]); 
/*     */         } else {
/* 489 */           this.fileList.addItem(arrayOfString[b]);
/*     */         } 
/*     */       } 
/* 492 */       this.dir = paramString1;
/*     */       
/* 494 */       this.pathField.setText(this.dir);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 499 */       this.target.setDirectory(this.dir);
/* 500 */       this.directoryList.setVisible(true);
/* 501 */       this.fileList.setVisible(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   String[] getDirList(String paramString) {
/* 507 */     if (!paramString.endsWith("/"))
/* 508 */       paramString = paramString + "/"; 
/* 509 */     char[] arrayOfChar = paramString.toCharArray();
/* 510 */     byte b1 = 0;
/* 511 */     for (byte b2 = 0; b2 < arrayOfChar.length; b2++) {
/* 512 */       if (arrayOfChar[b2] == '/')
/* 513 */         b1++; 
/*     */     } 
/* 515 */     String[] arrayOfString = new String[b1];
/* 516 */     byte b3 = 0;
/* 517 */     for (int i = arrayOfChar.length - 1; i >= 0; i--) {
/* 518 */       if (arrayOfChar[i] == '/')
/*     */       {
/* 520 */         arrayOfString[b3++] = new String(arrayOfChar, 0, i + 1);
/*     */       }
/*     */     } 
/* 523 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setSelectionField(String paramString) {
/* 530 */     this.selectionField.setText(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setFilterField(String paramString) {
/* 537 */     this.filterField.setText(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void itemStateChanged(ItemEvent paramItemEvent) {
/* 546 */     if (paramItemEvent.getID() != 701 || paramItemEvent
/* 547 */       .getStateChange() == 2) {
/*     */       return;
/*     */     }
/*     */     
/* 551 */     Object object = paramItemEvent.getSource();
/*     */     
/* 553 */     if (object == this.pathChoice) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 559 */       String str = this.pathChoice.getSelectedItem();
/* 560 */       this.pathField.setText(str);
/* 561 */     } else if (this.directoryList == object) {
/* 562 */       setFilterField(getFileName(this.filterField.getText()));
/* 563 */     } else if (this.fileList == object) {
/* 564 */       String str = this.fileList.getItem(((Integer)paramItemEvent.getItem()).intValue());
/* 565 */       setSelectionField(str);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean updateDirectoryByUserAction(String paramString) {
/*     */     String str;
/* 576 */     if (paramString.equals("..")) {
/* 577 */       str = getParentDirectory();
/*     */     } else {
/*     */       
/* 580 */       str = this.dir + paramString;
/*     */     } 
/*     */     
/* 583 */     File file = new File(str);
/* 584 */     if (file.canRead()) {
/* 585 */       this.dir = str;
/* 586 */       return true;
/*     */     } 
/* 588 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   String getParentDirectory() {
/* 593 */     String str = this.dir;
/* 594 */     if (!this.dir.equals("/")) {
/*     */       
/* 596 */       if (this.dir.endsWith("/")) {
/* 597 */         str = str.substring(0, str.lastIndexOf("/"));
/*     */       }
/* 599 */       str = str.substring(0, str.lastIndexOf("/") + 1);
/*     */     } 
/* 601 */     return str;
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {
/* 605 */     String str = paramActionEvent.getActionCommand();
/* 606 */     Object object = paramActionEvent.getSource();
/*     */     
/* 608 */     if (str.equals(this.actionButtonText)) {
/* 609 */       handleSelection(this.selectionField.getText());
/* 610 */       handleQuitButton();
/* 611 */     } else if (str.equals(this.filterLabelText)) {
/* 612 */       handleFilter(this.filterField.getText());
/* 613 */     } else if (str.equals(this.cancelButtonText)) {
/* 614 */       handleCancel();
/* 615 */     } else if (object instanceof TextField) {
/* 616 */       if (this.selectionField == (TextField)object) {
/*     */ 
/*     */ 
/*     */         
/* 620 */         handleSelection(this.selectionField.getText());
/* 621 */         handleQuitButton();
/* 622 */       } else if (this.filterField == (TextField)object) {
/* 623 */         handleFilter(this.filterField.getText());
/* 624 */       } else if (this.pathField == (TextField)object) {
/* 625 */         this.target.setDirectory(this.pathField.getText());
/*     */       } 
/* 627 */     } else if (object instanceof List) {
/* 628 */       if (this.directoryList == (List)object) {
/*     */         
/* 630 */         if (updateDirectoryByUserAction(str)) {
/* 631 */           handleFilter(getFileName(this.filterField.getText()));
/*     */         }
/* 633 */       } else if (this.fileList == (List)object) {
/* 634 */         handleSelection(str);
/* 635 */         handleQuitButton();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
/* 641 */     int i = paramKeyEvent.getID();
/* 642 */     int j = paramKeyEvent.getKeyCode();
/*     */     
/* 644 */     if (i == 401 && j == 27) {
/* 645 */       synchronized (this.target.getTreeLock()) {
/* 646 */         Component component = (Component)paramKeyEvent.getSource();
/* 647 */         while (component != null) {
/*     */ 
/*     */           
/* 650 */           if (component == this.pathChoice) {
/* 651 */             XChoicePeer xChoicePeer = (XChoicePeer)this.pathChoice.getPeer();
/* 652 */             if (xChoicePeer.isUnfurled()) {
/* 653 */               return false;
/*     */             }
/*     */           } 
/* 656 */           if (component.getPeer() == this) {
/* 657 */             handleCancel();
/* 658 */             return true;
/*     */           } 
/* 660 */           component = component.getParent();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 665 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFile(String paramString) {
/* 674 */     if (paramString == null) {
/* 675 */       this.file = null;
/*     */       
/*     */       return;
/*     */     } 
/* 679 */     if (this.dir == null) {
/* 680 */       String str = "./";
/* 681 */       File file = new File(str, paramString);
/*     */       
/* 683 */       if (file.isFile()) {
/* 684 */         this.file = paramString;
/* 685 */         setDirectory(str);
/*     */       } 
/*     */     } else {
/* 688 */       File file = new File(this.dir, paramString);
/* 689 */       if (file.isFile()) {
/* 690 */         this.file = paramString;
/*     */       }
/*     */     } 
/*     */     
/* 694 */     setSelectionField(paramString);
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
/*     */   public void setDirectory(String paramString) {
/* 707 */     if (paramString == null) {
/* 708 */       this.dir = null;
/*     */       
/*     */       return;
/*     */     } 
/* 712 */     if (paramString.equals(this.dir)) {
/*     */       return;
/*     */     }
/*     */     
/*     */     int i;
/* 717 */     if ((i = paramString.indexOf("~")) != -1)
/*     */     {
/* 719 */       paramString = paramString.substring(0, i) + System.getProperty("user.home") + paramString.substring(i + 1, paramString.length());
/*     */     }
/*     */     
/* 722 */     File file = (new File(paramString)).getAbsoluteFile();
/* 723 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 724 */       log.fine("Current directory : " + file);
/*     */     }
/*     */     
/* 727 */     if (!file.isDirectory()) {
/* 728 */       paramString = "./";
/* 729 */       file = (new File(paramString)).getAbsoluteFile();
/*     */       
/* 731 */       if (!file.isDirectory()) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     try {
/* 736 */       paramString = this.dir = file.getCanonicalPath();
/* 737 */     } catch (IOException iOException) {
/* 738 */       paramString = this.dir = file.getAbsolutePath();
/*     */     } 
/* 740 */     this.pathField.setText(this.dir);
/*     */ 
/*     */     
/* 743 */     if (paramString.endsWith("/")) {
/* 744 */       this.dir = paramString;
/* 745 */       handleFilter("");
/*     */     } else {
/* 747 */       this.dir = paramString + "/";
/* 748 */       handleFilter("");
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
/*     */   public void setFilenameFilter(FilenameFilter paramFilenameFilter) {
/* 763 */     this.filter = paramFilenameFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 768 */     FileDialog fileDialog = (FileDialog)this.fileDialog;
/* 769 */     if (fileDialog != null) {
/* 770 */       fileDialog.removeAll();
/*     */     }
/* 772 */     super.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean paramBoolean) {
/* 777 */     if (this.fileDialog == null) {
/* 778 */       init(this.target);
/*     */     }
/*     */     
/* 781 */     if (this.savedDir != null || this.userDir != null) {
/* 782 */       setDirectory((this.savedDir != null) ? this.savedDir : this.userDir);
/*     */     }
/*     */     
/* 785 */     if (this.savedFile != null)
/*     */     {
/*     */       
/* 788 */       setFile(this.savedFile);
/*     */     }
/*     */     
/* 791 */     super.setVisible(paramBoolean);
/* 792 */     if (paramBoolean == true) {
/*     */       
/* 794 */       XChoicePeer xChoicePeer = (XChoicePeer)this.pathChoice.getPeer();
/* 795 */       xChoicePeer.addXChoicePeerListener(this);
/* 796 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
/*     */     } else {
/*     */       
/* 799 */       XChoicePeer xChoicePeer = (XChoicePeer)this.pathChoice.getPeer();
/* 800 */       xChoicePeer.removeXChoicePeerListener();
/* 801 */       KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
/*     */     } 
/*     */     
/* 804 */     this.selectionField.requestFocusInWindow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemsToPathChoice(String paramString) {
/* 812 */     String[] arrayOfString = getDirList(paramString);
/* 813 */     for (byte b = 0; b < arrayOfString.length; ) { this.pathChoice.addItem(arrayOfString[b]); b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unfurledChoiceOpening(ListHelper paramListHelper) {
/* 823 */     if (paramListHelper.getItemCount() == 0) {
/* 824 */       addItemsToPathChoice(this.pathField.getText());
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 829 */     if (this.pathChoice.getItem(0).equals(this.pathField.getText())) {
/*     */       return;
/*     */     }
/* 832 */     this.pathChoice.removeAll();
/* 833 */     addItemsToPathChoice(this.pathField.getText());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unfurledChoiceClosing() {
/* 843 */     String str = this.pathChoice.getSelectedItem();
/* 844 */     this.target.setDirectory(str);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XFileDialogPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */