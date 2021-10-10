/*      */ package com.sun.java.swing.plaf.gtk;
/*      */ import java.awt.AWTKeyStroke;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FlowLayout;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.Insets;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Comparator;
/*      */ import java.util.Locale;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.AbstractListModel;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.ComboBoxModel;
/*      */ import javax.swing.DefaultListCellRenderer;
/*      */ import javax.swing.DefaultListSelectionModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JSplitPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.TransferHandler;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.event.ListDataEvent;
/*      */ import javax.swing.event.ListDataListener;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.filechooser.FileFilter;
/*      */ import javax.swing.filechooser.FileSystemView;
/*      */ import javax.swing.plaf.ActionMapUIResource;
/*      */ import javax.swing.plaf.basic.BasicDirectoryModel;
/*      */ import javax.swing.table.TableCellRenderer;
/*      */ import sun.awt.shell.ShellFolder;
/*      */ 
/*      */ class GTKFileChooserUI extends SynthFileChooserUI {
/*   59 */   private JPanel accessoryPanel = null;
/*      */   
/*   61 */   private String newFolderButtonText = null;
/*   62 */   private String newFolderErrorSeparator = null;
/*   63 */   private String newFolderErrorText = null;
/*   64 */   private String newFolderDialogText = null;
/*   65 */   private String newFolderNoDirectoryErrorTitleText = null;
/*   66 */   private String newFolderNoDirectoryErrorText = null;
/*      */   
/*   68 */   private String deleteFileButtonText = null;
/*   69 */   private String renameFileButtonText = null;
/*      */   
/*   71 */   private String newFolderButtonToolTipText = null;
/*   72 */   private String deleteFileButtonToolTipText = null;
/*   73 */   private String renameFileButtonToolTipText = null;
/*      */   
/*   75 */   private int newFolderButtonMnemonic = 0;
/*   76 */   private int deleteFileButtonMnemonic = 0;
/*   77 */   private int renameFileButtonMnemonic = 0;
/*   78 */   private int foldersLabelMnemonic = 0;
/*   79 */   private int filesLabelMnemonic = 0;
/*      */   
/*   81 */   private String renameFileDialogText = null;
/*   82 */   private String renameFileErrorTitle = null;
/*   83 */   private String renameFileErrorText = null;
/*      */   
/*      */   private JComboBox filterComboBox;
/*      */   
/*      */   private FilterComboBoxModel filterComboBoxModel;
/*      */   
/*      */   private JPanel rightPanel;
/*      */   
/*      */   private JList directoryList;
/*      */   
/*      */   private JList fileList;
/*      */   
/*      */   private JLabel pathField;
/*      */   private JTextField fileNameTextField;
/*   97 */   private static final Dimension hstrut3 = new Dimension(3, 1);
/*   98 */   private static final Dimension vstrut10 = new Dimension(1, 10);
/*      */   
/*  100 */   private static Dimension prefListSize = new Dimension(75, 150);
/*      */   
/*  102 */   private static Dimension PREF_SIZE = new Dimension(435, 360);
/*      */   
/*      */   private static final int MIN_WIDTH = 200;
/*      */   private static final int MIN_HEIGHT = 300;
/*  106 */   private static Dimension ZERO_ACC_SIZE = new Dimension(1, 1);
/*      */   
/*  108 */   private static Dimension MAX_SIZE = new Dimension(32767, 32767);
/*      */   
/*  110 */   private static final Insets buttonMargin = new Insets(3, 3, 3, 3);
/*      */   
/*  112 */   private String filesLabelText = null;
/*  113 */   private String foldersLabelText = null;
/*  114 */   private String pathLabelText = null;
/*  115 */   private String filterLabelText = null;
/*      */   
/*  117 */   private int pathLabelMnemonic = 0;
/*  118 */   private int filterLabelMnemonic = 0;
/*      */   
/*      */   private JComboBox directoryComboBox;
/*      */   private DirectoryComboBoxModel directoryComboBoxModel;
/*  122 */   private Action directoryComboBoxAction = new DirectoryComboBoxAction();
/*      */   private JPanel bottomButtonPanel;
/*  124 */   private GTKDirectoryModel model = null;
/*      */   private Action newFolderAction;
/*      */   private boolean readOnly;
/*      */   private boolean showDirectoryIcons;
/*      */   private boolean showFileIcons;
/*  129 */   private GTKFileView fileView = new GTKFileView();
/*      */   private PropertyChangeListener gtkFCPropertyChangeListener;
/*  131 */   private Action approveSelectionAction = new GTKApproveSelectionAction();
/*      */   private GTKDirectoryListModel directoryListModel;
/*      */   
/*      */   public GTKFileChooserUI(JFileChooser paramJFileChooser) {
/*  135 */     super(paramJFileChooser);
/*      */   }
/*      */   
/*      */   protected ActionMap createActionMap() {
/*  139 */     ActionMapUIResource actionMapUIResource = new ActionMapUIResource();
/*  140 */     actionMapUIResource.put("approveSelection", getApproveSelectionAction());
/*  141 */     actionMapUIResource.put("cancelSelection", getCancelSelectionAction());
/*  142 */     actionMapUIResource.put("Go Up", getChangeToParentDirectoryAction());
/*  143 */     actionMapUIResource.put("fileNameCompletion", getFileNameCompletionAction());
/*  144 */     return actionMapUIResource;
/*      */   }
/*      */   
/*      */   public String getFileName() {
/*  148 */     JFileChooser jFileChooser = getFileChooser();
/*      */     
/*  150 */     String str = (this.fileNameTextField != null) ? this.fileNameTextField.getText() : null;
/*      */     
/*  152 */     if (!jFileChooser.isMultiSelectionEnabled()) {
/*  153 */       return str;
/*      */     }
/*      */     
/*  156 */     int i = jFileChooser.getFileSelectionMode();
/*  157 */     JList jList = (i == 1) ? this.directoryList : this.fileList;
/*      */     
/*  159 */     Object[] arrayOfObject = jList.getSelectedValues();
/*  160 */     int j = arrayOfObject.length;
/*  161 */     Vector<String> vector = new Vector(j + 1);
/*      */ 
/*      */     
/*  164 */     for (byte b1 = 0; b1 < j; b1++) {
/*  165 */       File file = (File)arrayOfObject[b1];
/*  166 */       vector.add(file.getName());
/*      */     } 
/*      */     
/*  169 */     if (str != null && !vector.contains(str)) {
/*  170 */       vector.add(str);
/*      */     }
/*      */     
/*  173 */     StringBuffer stringBuffer = new StringBuffer();
/*  174 */     j = vector.size();
/*      */ 
/*      */     
/*  177 */     for (byte b2 = 0; b2 < j; b2++) {
/*  178 */       if (b2 > 0) {
/*  179 */         stringBuffer.append(" ");
/*      */       }
/*  181 */       if (j > 1) {
/*  182 */         stringBuffer.append("\"");
/*      */       }
/*  184 */       stringBuffer.append(vector.get(b2));
/*  185 */       if (j > 1) {
/*  186 */         stringBuffer.append("\"");
/*      */       }
/*      */     } 
/*  189 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   public void setFileName(String paramString) {
/*  193 */     if (this.fileNameTextField != null) {
/*  194 */       this.fileNameTextField.setText(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDirectoryName(String paramString) {
/*  203 */     this.pathField.setText(paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public void ensureFileIsVisible(JFileChooser paramJFileChooser, File paramFile) {}
/*      */ 
/*      */   
/*      */   public void rescanCurrentDirectory(JFileChooser paramJFileChooser) {
/*  211 */     getModel().validateFileCache();
/*      */   }
/*      */   
/*      */   public JPanel getAccessoryPanel() {
/*  215 */     return this.accessoryPanel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FileView getFileView(JFileChooser paramJFileChooser) {
/*  223 */     return this.fileView;
/*      */   }
/*      */   
/*      */   private class GTKFileView extends BasicFileChooserUI.BasicFileView {
/*      */     public GTKFileView() {
/*  228 */       this.iconCache = null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clearIconCache() {}
/*      */     
/*      */     public Icon getCachedIcon(File param1File) {
/*  235 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void cacheIcon(File param1File, Icon param1Icon) {}
/*      */     
/*      */     public Icon getIcon(File param1File) {
/*  242 */       return (param1File != null && param1File.isDirectory()) ? GTKFileChooserUI.this.directoryIcon : GTKFileChooserUI.this.fileIcon;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateDefaultButton() {
/*  248 */     JFileChooser jFileChooser = getFileChooser();
/*  249 */     JRootPane jRootPane = SwingUtilities.getRootPane(jFileChooser);
/*  250 */     if (jRootPane == null) {
/*      */       return;
/*      */     }
/*      */     
/*  254 */     if (jFileChooser.getControlButtonsAreShown()) {
/*  255 */       if (jRootPane.getDefaultButton() == null) {
/*  256 */         jRootPane.setDefaultButton(getApproveButton(jFileChooser));
/*  257 */         getCancelButton(jFileChooser).setDefaultCapable(false);
/*      */       }
/*      */     
/*  260 */     } else if (jRootPane.getDefaultButton() == getApproveButton(jFileChooser)) {
/*  261 */       jRootPane.setDefaultButton(null);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doSelectedFileChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  267 */     super.doSelectedFileChanged(paramPropertyChangeEvent);
/*  268 */     File file = (File)paramPropertyChangeEvent.getNewValue();
/*  269 */     if (file != null) {
/*  270 */       setFileName(getFileChooser().getName(file));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void doDirectoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  275 */     this.directoryList.clearSelection();
/*  276 */     ListSelectionModel listSelectionModel = this.directoryList.getSelectionModel();
/*  277 */     if (listSelectionModel instanceof DefaultListSelectionModel) {
/*  278 */       ((DefaultListSelectionModel)listSelectionModel).moveLeadSelectionIndex(0);
/*  279 */       listSelectionModel.setAnchorSelectionIndex(0);
/*      */     } 
/*  281 */     this.fileList.clearSelection();
/*  282 */     listSelectionModel = this.fileList.getSelectionModel();
/*  283 */     if (listSelectionModel instanceof DefaultListSelectionModel) {
/*  284 */       ((DefaultListSelectionModel)listSelectionModel).moveLeadSelectionIndex(0);
/*  285 */       listSelectionModel.setAnchorSelectionIndex(0);
/*      */     } 
/*      */     
/*  288 */     File file = getFileChooser().getCurrentDirectory();
/*  289 */     if (file != null) {
/*      */       try {
/*  291 */         setDirectoryName(ShellFolder.getNormalizedFile((File)paramPropertyChangeEvent.getNewValue()).getPath());
/*  292 */       } catch (IOException iOException) {
/*  293 */         setDirectoryName(((File)paramPropertyChangeEvent.getNewValue()).getAbsolutePath());
/*      */       } 
/*  295 */       if (getFileChooser().getFileSelectionMode() == 1 && !getFileChooser().isMultiSelectionEnabled()) {
/*  296 */         setFileName(this.pathField.getText());
/*      */       }
/*  298 */       this.directoryComboBoxModel.addItem(file);
/*  299 */       this.directoryListModel.directoryChanged();
/*      */     } 
/*  301 */     super.doDirectoryChanged(paramPropertyChangeEvent);
/*      */   }
/*      */   
/*      */   protected void doAccessoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  305 */     if (getAccessoryPanel() != null) {
/*  306 */       if (paramPropertyChangeEvent.getOldValue() != null) {
/*  307 */         getAccessoryPanel().remove((JComponent)paramPropertyChangeEvent.getOldValue());
/*      */       }
/*  309 */       JComponent jComponent = (JComponent)paramPropertyChangeEvent.getNewValue();
/*  310 */       if (jComponent != null) {
/*  311 */         getAccessoryPanel().add(jComponent, "Center");
/*  312 */         getAccessoryPanel().setPreferredSize(jComponent.getPreferredSize());
/*  313 */         getAccessoryPanel().setMaximumSize(MAX_SIZE);
/*      */       } else {
/*  315 */         getAccessoryPanel().setPreferredSize(ZERO_ACC_SIZE);
/*  316 */         getAccessoryPanel().setMaximumSize(ZERO_ACC_SIZE);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void doFileSelectionModeChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  322 */     this.directoryList.clearSelection();
/*  323 */     this.rightPanel.setVisible((((Integer)paramPropertyChangeEvent.getNewValue()).intValue() != 1));
/*      */     
/*  325 */     super.doFileSelectionModeChanged(paramPropertyChangeEvent);
/*      */   }
/*      */   
/*      */   protected void doMultiSelectionChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  329 */     if (getFileChooser().isMultiSelectionEnabled()) {
/*  330 */       this.fileList.setSelectionMode(2);
/*      */     } else {
/*  332 */       this.fileList.setSelectionMode(0);
/*  333 */       this.fileList.clearSelection();
/*      */     } 
/*      */     
/*  336 */     super.doMultiSelectionChanged(paramPropertyChangeEvent);
/*      */   }
/*      */   
/*      */   protected void doControlButtonsChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  340 */     super.doControlButtonsChanged(paramPropertyChangeEvent);
/*      */     
/*  342 */     JFileChooser jFileChooser = getFileChooser();
/*  343 */     if (jFileChooser.getControlButtonsAreShown()) {
/*  344 */       jFileChooser.add(this.bottomButtonPanel, "South");
/*      */     } else {
/*  346 */       jFileChooser.remove(this.bottomButtonPanel);
/*      */     } 
/*  348 */     updateDefaultButton();
/*      */   }
/*      */   
/*      */   protected void doAncestorChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  352 */     if (paramPropertyChangeEvent.getOldValue() == null && paramPropertyChangeEvent.getNewValue() != null) {
/*      */       
/*  354 */       this.fileNameTextField.selectAll();
/*  355 */       this.fileNameTextField.requestFocus();
/*  356 */       updateDefaultButton();
/*      */     } 
/*      */     
/*  359 */     super.doAncestorChanged(paramPropertyChangeEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListSelectionListener createListSelectionListener(JFileChooser paramJFileChooser) {
/*  369 */     return new SelectionListener();
/*      */   }
/*      */   
/*      */   class DoubleClickListener extends MouseAdapter { JList list;
/*      */     
/*      */     public DoubleClickListener(JList param1JList) {
/*  375 */       this.list = param1JList;
/*      */     }
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/*  379 */       if (SwingUtilities.isLeftMouseButton(param1MouseEvent) && param1MouseEvent.getClickCount() == 2) {
/*  380 */         int i = this.list.locationToIndex(param1MouseEvent.getPoint());
/*  381 */         if (i >= 0) {
/*  382 */           File file = this.list.getModel().getElementAt(i);
/*      */           
/*      */           try {
/*  385 */             file = ShellFolder.getNormalizedFile(file);
/*  386 */           } catch (IOException iOException) {}
/*      */ 
/*      */           
/*  389 */           if (GTKFileChooserUI.this.getFileChooser().isTraversable(file)) {
/*  390 */             this.list.clearSelection();
/*  391 */             if (GTKFileChooserUI.this.getFileChooser().getCurrentDirectory().equals(file)) {
/*  392 */               GTKFileChooserUI.this.rescanCurrentDirectory(GTKFileChooserUI.this.getFileChooser());
/*      */             } else {
/*  394 */               GTKFileChooserUI.this.getFileChooser().setCurrentDirectory(file);
/*      */             } 
/*      */           } else {
/*  397 */             GTKFileChooserUI.this.getFileChooser().approveSelection();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/*  404 */       if (this.list != null) {
/*  405 */         TransferHandler transferHandler1 = GTKFileChooserUI.this.getFileChooser().getTransferHandler();
/*  406 */         TransferHandler transferHandler2 = this.list.getTransferHandler();
/*  407 */         if (transferHandler1 != transferHandler2) {
/*  408 */           this.list.setTransferHandler(transferHandler1);
/*      */         }
/*  410 */         if (GTKFileChooserUI.this.getFileChooser().getDragEnabled() != this.list.getDragEnabled()) {
/*  411 */           this.list.setDragEnabled(GTKFileChooserUI.this.getFileChooser().getDragEnabled());
/*      */         }
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   protected MouseListener createDoubleClickListener(JFileChooser paramJFileChooser, JList paramJList) {
/*  418 */     return new DoubleClickListener(paramJList);
/*      */   }
/*      */   
/*      */   protected class SelectionListener
/*      */     implements ListSelectionListener
/*      */   {
/*      */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {
/*  425 */       if (!param1ListSelectionEvent.getValueIsAdjusting()) {
/*  426 */         JFileChooser jFileChooser = GTKFileChooserUI.this.getFileChooser();
/*  427 */         JList<File> jList = (JList)param1ListSelectionEvent.getSource();
/*      */         
/*  429 */         if (jFileChooser.isMultiSelectionEnabled()) {
/*  430 */           File[] arrayOfFile = null;
/*  431 */           Object[] arrayOfObject = jList.getSelectedValues();
/*  432 */           if (arrayOfObject != null) {
/*  433 */             if (arrayOfObject.length == 1 && ((File)arrayOfObject[0])
/*  434 */               .isDirectory() && jFileChooser
/*  435 */               .isTraversable((File)arrayOfObject[0]) && (jFileChooser
/*  436 */               .getFileSelectionMode() != 1 || 
/*  437 */               !jFileChooser.getFileSystemView().isFileSystem((File)arrayOfObject[0]))) {
/*  438 */               GTKFileChooserUI.this.setDirectorySelected(true);
/*  439 */               GTKFileChooserUI.this.setDirectory((File)arrayOfObject[0]);
/*      */             } else {
/*  441 */               ArrayList<File> arrayList = new ArrayList(arrayOfObject.length);
/*  442 */               for (Object object : arrayOfObject) {
/*  443 */                 File file = (File)object;
/*  444 */                 if ((jFileChooser.isFileSelectionEnabled() && file.isFile()) || (jFileChooser
/*  445 */                   .isDirectorySelectionEnabled() && file.isDirectory())) {
/*  446 */                   arrayList.add(file);
/*      */                 }
/*      */               } 
/*  449 */               if (arrayList.size() > 0) {
/*  450 */                 arrayOfFile = arrayList.<File>toArray(new File[arrayList.size()]);
/*      */               }
/*  452 */               GTKFileChooserUI.this.setDirectorySelected(false);
/*      */             } 
/*      */           }
/*  455 */           jFileChooser.setSelectedFiles(arrayOfFile);
/*      */         } else {
/*  457 */           File file = jList.getSelectedValue();
/*  458 */           if (file != null && file
/*  459 */             .isDirectory() && jFileChooser
/*  460 */             .isTraversable(file) && (jFileChooser
/*  461 */             .getFileSelectionMode() == 0 || 
/*  462 */             !jFileChooser.getFileSystemView().isFileSystem(file))) {
/*      */             
/*  464 */             GTKFileChooserUI.this.setDirectorySelected(true);
/*  465 */             GTKFileChooserUI.this.setDirectory(file);
/*      */           } else {
/*  467 */             GTKFileChooserUI.this.setDirectorySelected(false);
/*  468 */             if (file != null) {
/*  469 */               jFileChooser.setSelectedFile(file);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  482 */     return new GTKFileChooserUI((JFileChooser)paramJComponent);
/*      */   }
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  486 */     this.accessoryPanel = new JPanel(new BorderLayout(10, 10));
/*  487 */     this.accessoryPanel.setName("GTKFileChooser.accessoryPanel");
/*      */     
/*  489 */     super.installUI(paramJComponent);
/*      */   }
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  493 */     paramJComponent.removePropertyChangeListener(this.filterComboBoxModel);
/*  494 */     super.uninstallUI(paramJComponent);
/*      */     
/*  496 */     if (this.accessoryPanel != null) {
/*  497 */       this.accessoryPanel.removeAll();
/*      */     }
/*  499 */     this.accessoryPanel = null;
/*  500 */     getFileChooser().removeAll();
/*      */   }
/*      */   
/*      */   public void installComponents(JFileChooser paramJFileChooser) {
/*  504 */     super.installComponents(paramJFileChooser);
/*      */     
/*  506 */     boolean bool = paramJFileChooser.getComponentOrientation().isLeftToRight();
/*      */     
/*  508 */     paramJFileChooser.setLayout(new BorderLayout());
/*  509 */     paramJFileChooser.setAlignmentX(0.5F);
/*      */ 
/*      */     
/*  512 */     JPanel jPanel1 = new JPanel(new FlowLayout(3, 0, 0));
/*  513 */     jPanel1.setBorder(new EmptyBorder(10, 10, 0, 10));
/*  514 */     jPanel1.setName("GTKFileChooser.topButtonPanel");
/*      */     
/*  516 */     if (!UIManager.getBoolean("FileChooser.readOnly")) {
/*  517 */       JButton jButton = new JButton(getNewFolderAction());
/*  518 */       jButton.setName("GTKFileChooser.newFolderButton");
/*  519 */       jButton.setMnemonic(this.newFolderButtonMnemonic);
/*  520 */       jButton.setToolTipText(this.newFolderButtonToolTipText);
/*  521 */       jButton.setText(this.newFolderButtonText);
/*  522 */       jPanel1.add(jButton);
/*      */     } 
/*  524 */     JButton jButton1 = new JButton(this.deleteFileButtonText);
/*  525 */     jButton1.setName("GTKFileChooser.deleteFileButton");
/*  526 */     jButton1.setMnemonic(this.deleteFileButtonMnemonic);
/*  527 */     jButton1.setToolTipText(this.deleteFileButtonToolTipText);
/*  528 */     jButton1.setEnabled(false);
/*  529 */     jPanel1.add(jButton1);
/*      */     
/*  531 */     RenameFileAction renameFileAction = new RenameFileAction();
/*  532 */     JButton jButton2 = new JButton(renameFileAction);
/*  533 */     if (this.readOnly) {
/*  534 */       renameFileAction.setEnabled(false);
/*      */     }
/*  536 */     jButton2.setText(this.renameFileButtonText);
/*  537 */     jButton2.setName("GTKFileChooser.renameFileButton");
/*  538 */     jButton2.setMnemonic(this.renameFileButtonMnemonic);
/*  539 */     jButton2.setToolTipText(this.renameFileButtonToolTipText);
/*  540 */     jPanel1.add(jButton2);
/*      */     
/*  542 */     paramJFileChooser.add(jPanel1, "North");
/*      */ 
/*      */     
/*  545 */     JPanel jPanel2 = new JPanel();
/*  546 */     jPanel2.setBorder(new EmptyBorder(0, 10, 10, 10));
/*  547 */     jPanel2.setName("GTKFileChooser.interiorPanel");
/*  548 */     align(jPanel2);
/*  549 */     jPanel2.setLayout(new BoxLayout(jPanel2, 3));
/*      */     
/*  551 */     paramJFileChooser.add(jPanel2, "Center");
/*      */     
/*  553 */     JPanel jPanel3 = new JPanel(new FlowLayout(1, 0, 0)
/*      */         {
/*      */           public void layoutContainer(Container param1Container) {
/*  556 */             super.layoutContainer(param1Container);
/*  557 */             JComboBox jComboBox = GTKFileChooserUI.this.directoryComboBox;
/*  558 */             if (jComboBox.getWidth() > param1Container.getWidth()) {
/*  559 */               jComboBox.setBounds(0, jComboBox.getY(), param1Container.getWidth(), jComboBox
/*  560 */                   .getHeight());
/*      */             }
/*      */           }
/*      */         });
/*  564 */     jPanel3.setBorder(new EmptyBorder(0, 0, 4, 0));
/*  565 */     jPanel3.setName("GTKFileChooser.directoryComboBoxPanel");
/*      */     
/*  567 */     this.directoryComboBoxModel = createDirectoryComboBoxModel(paramJFileChooser);
/*  568 */     this.directoryComboBox = new JComboBox(this.directoryComboBoxModel);
/*  569 */     this.directoryComboBox.setName("GTKFileChooser.directoryComboBox");
/*  570 */     this.directoryComboBox.putClientProperty("JComboBox.lightweightKeyboardNavigation", "Lightweight");
/*  571 */     this.directoryComboBox.addActionListener(this.directoryComboBoxAction);
/*  572 */     this.directoryComboBox.setMaximumRowCount(8);
/*  573 */     jPanel3.add(this.directoryComboBox);
/*  574 */     jPanel2.add(jPanel3);
/*      */ 
/*      */ 
/*      */     
/*  578 */     JPanel jPanel4 = new JPanel(new BorderLayout());
/*  579 */     jPanel4.setName("GTKFileChooser.centerPanel");
/*      */ 
/*      */     
/*  582 */     JSplitPane jSplitPane = new JSplitPane();
/*  583 */     jSplitPane.setName("GTKFileChooser.splitPanel");
/*  584 */     jSplitPane.setDividerLocation((PREF_SIZE.width - 8) / 2);
/*      */ 
/*      */     
/*  587 */     JPanel jPanel5 = new JPanel(new GridBagLayout());
/*  588 */     jPanel5.setName("GTKFileChooser.directoryListPanel");
/*      */ 
/*      */ 
/*      */     
/*  592 */     TableCellRenderer tableCellRenderer = (new JTableHeader()).getDefaultRenderer();
/*      */     
/*  594 */     JLabel jLabel1 = (JLabel)tableCellRenderer.getTableCellRendererComponent(null, this.foldersLabelText, false, false, 0, 0);
/*      */     
/*  596 */     jLabel1.setName("GTKFileChooser.directoryListLabel");
/*  597 */     jPanel5.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
/*      */ 
/*      */ 
/*      */     
/*  601 */     jPanel5.add(createDirectoryList(), new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 13, 1, new Insets(0, 0, 0, 0), 0, 0));
/*      */ 
/*      */ 
/*      */     
/*  605 */     jLabel1.setDisplayedMnemonic(this.foldersLabelMnemonic);
/*  606 */     jLabel1.setLabelFor(this.directoryList);
/*      */ 
/*      */     
/*  609 */     this.rightPanel = new JPanel(new GridBagLayout());
/*  610 */     this.rightPanel.setName("GTKFileChooser.fileListPanel");
/*      */     
/*  612 */     tableCellRenderer = (new JTableHeader()).getDefaultRenderer();
/*      */     
/*  614 */     JLabel jLabel2 = (JLabel)tableCellRenderer.getTableCellRendererComponent(null, this.filesLabelText, false, false, 0, 0);
/*      */     
/*  616 */     jLabel2.setName("GTKFileChooser.fileListLabel");
/*  617 */     this.rightPanel.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
/*      */ 
/*      */ 
/*      */     
/*  621 */     this.rightPanel.add(createFilesList(), new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 13, 1, new Insets(0, 0, 0, 0), 0, 0));
/*      */ 
/*      */ 
/*      */     
/*  625 */     jLabel2.setDisplayedMnemonic(this.filesLabelMnemonic);
/*  626 */     jLabel2.setLabelFor(this.fileList);
/*      */     
/*  628 */     jSplitPane.add(jPanel5, bool ? "left" : "right");
/*  629 */     jSplitPane.add(this.rightPanel, bool ? "right" : "left");
/*  630 */     jPanel4.add(jSplitPane, "Center");
/*      */     
/*  632 */     JPanel jPanel6 = getAccessoryPanel();
/*  633 */     JComponent jComponent = paramJFileChooser.getAccessory();
/*  634 */     if (jPanel6 != null) {
/*  635 */       if (jComponent == null) {
/*  636 */         jPanel6.setPreferredSize(ZERO_ACC_SIZE);
/*  637 */         jPanel6.setMaximumSize(ZERO_ACC_SIZE);
/*      */       } else {
/*  639 */         getAccessoryPanel().add(jComponent, "Center");
/*  640 */         jPanel6.setPreferredSize(jComponent.getPreferredSize());
/*  641 */         jPanel6.setMaximumSize(MAX_SIZE);
/*      */       } 
/*  643 */       align(jPanel6);
/*  644 */       jPanel4.add(jPanel6, "After");
/*      */     } 
/*  646 */     jPanel2.add(jPanel4);
/*  647 */     jPanel2.add(Box.createRigidArea(vstrut10));
/*      */     
/*  649 */     JPanel jPanel7 = new JPanel(new FlowLayout(3, 0, 0));
/*      */     
/*  651 */     jPanel7.setBorder(new EmptyBorder(0, 0, 4, 0));
/*  652 */     JLabel jLabel3 = new JLabel(this.pathLabelText);
/*  653 */     jLabel3.setName("GTKFileChooser.pathFieldLabel");
/*  654 */     jLabel3.setDisplayedMnemonic(this.pathLabelMnemonic);
/*  655 */     align(jLabel3);
/*  656 */     jPanel7.add(jLabel3);
/*  657 */     jPanel7.add(Box.createRigidArea(hstrut3));
/*      */     
/*  659 */     File file = paramJFileChooser.getCurrentDirectory();
/*  660 */     String str = null;
/*  661 */     if (file != null) {
/*  662 */       str = file.getPath();
/*      */     }
/*  664 */     this.pathField = new JLabel(str) {
/*      */         public Dimension getMaximumSize() {
/*  666 */           Dimension dimension = super.getMaximumSize();
/*  667 */           dimension.height = (getPreferredSize()).height;
/*  668 */           return dimension;
/*      */         }
/*      */       };
/*  671 */     this.pathField.setName("GTKFileChooser.pathField");
/*  672 */     align(this.pathField);
/*  673 */     jPanel7.add(this.pathField);
/*  674 */     jPanel2.add(jPanel7);
/*      */ 
/*      */     
/*  677 */     this.fileNameTextField = new JTextField() {
/*      */         public Dimension getMaximumSize() {
/*  679 */           Dimension dimension = super.getMaximumSize();
/*  680 */           dimension.height = (getPreferredSize()).height;
/*  681 */           return dimension;
/*      */         }
/*      */       };
/*      */     
/*  685 */     jLabel3.setLabelFor(this.fileNameTextField);
/*      */     
/*  687 */     Set<AWTKeyStroke> set = this.fileNameTextField.getFocusTraversalKeys(0);
/*      */     
/*  689 */     set = new HashSet<>(set);
/*  690 */     set.remove(KeyStroke.getKeyStroke(9, 0));
/*  691 */     this.fileNameTextField.setFocusTraversalKeys(0, set);
/*      */     
/*  693 */     this.fileNameTextField.setName("GTKFileChooser.fileNameTextField");
/*  694 */     this.fileNameTextField.getActionMap().put("fileNameCompletionAction", getFileNameCompletionAction());
/*  695 */     this.fileNameTextField.getInputMap().put(KeyStroke.getKeyStroke(9, 0), "fileNameCompletionAction");
/*  696 */     jPanel2.add(this.fileNameTextField);
/*      */ 
/*      */     
/*  699 */     JPanel jPanel8 = new JPanel();
/*  700 */     jPanel8.setLayout(new FlowLayout(3, 0, 0));
/*  701 */     jPanel8.setBorder(new EmptyBorder(0, 0, 4, 0));
/*  702 */     JLabel jLabel4 = new JLabel(this.filterLabelText);
/*  703 */     jLabel4.setName("GTKFileChooser.filterLabel");
/*  704 */     jLabel4.setDisplayedMnemonic(this.filterLabelMnemonic);
/*  705 */     jPanel8.add(jLabel4);
/*      */     
/*  707 */     this.filterComboBoxModel = createFilterComboBoxModel();
/*  708 */     paramJFileChooser.addPropertyChangeListener(this.filterComboBoxModel);
/*  709 */     this.filterComboBox = new JComboBox(this.filterComboBoxModel);
/*  710 */     this.filterComboBox.setRenderer(createFilterComboBoxRenderer());
/*  711 */     jLabel4.setLabelFor(this.filterComboBox);
/*      */     
/*  713 */     jPanel2.add(Box.createRigidArea(vstrut10));
/*  714 */     jPanel2.add(jPanel8);
/*  715 */     jPanel2.add(this.filterComboBox);
/*      */ 
/*      */     
/*  718 */     this.bottomButtonPanel = new JPanel(new FlowLayout(4));
/*  719 */     this.bottomButtonPanel.setName("GTKFileChooser.bottomButtonPanel");
/*  720 */     align(this.bottomButtonPanel);
/*      */     
/*  722 */     JPanel jPanel9 = new JPanel(new GridLayout(1, 2, 5, 0));
/*      */     
/*  724 */     JButton jButton3 = getCancelButton(paramJFileChooser);
/*  725 */     align(jButton3);
/*  726 */     jButton3.setMargin(buttonMargin);
/*  727 */     jPanel9.add(jButton3);
/*      */     
/*  729 */     JButton jButton4 = getApproveButton(paramJFileChooser);
/*  730 */     align(jButton4);
/*  731 */     jButton4.setMargin(buttonMargin);
/*  732 */     jPanel9.add(jButton4);
/*      */     
/*  734 */     this.bottomButtonPanel.add(jPanel9);
/*      */     
/*  736 */     if (paramJFileChooser.getControlButtonsAreShown()) {
/*  737 */       paramJFileChooser.add(this.bottomButtonPanel, "South");
/*      */     }
/*      */   }
/*      */   
/*      */   protected void installListeners(JFileChooser paramJFileChooser) {
/*  742 */     super.installListeners(paramJFileChooser);
/*      */     
/*  744 */     this.gtkFCPropertyChangeListener = new GTKFCPropertyChangeListener();
/*  745 */     paramJFileChooser.addPropertyChangeListener(this.gtkFCPropertyChangeListener);
/*      */   }
/*      */   
/*      */   private int getMnemonic(String paramString, Locale paramLocale) {
/*  749 */     return SwingUtilities2.getUIDefaultsInt(paramString, paramLocale);
/*      */   }
/*      */   
/*      */   protected void uninstallListeners(JFileChooser paramJFileChooser) {
/*  753 */     super.uninstallListeners(paramJFileChooser);
/*      */     
/*  755 */     if (this.gtkFCPropertyChangeListener != null)
/*  756 */       paramJFileChooser.removePropertyChangeListener(this.gtkFCPropertyChangeListener); 
/*      */   }
/*      */   
/*      */   private class GTKFCPropertyChangeListener implements PropertyChangeListener { private GTKFCPropertyChangeListener() {}
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  762 */       String str = param1PropertyChangeEvent.getPropertyName();
/*  763 */       if (str.equals("GTKFileChooser.showDirectoryIcons")) {
/*  764 */         GTKFileChooserUI.this.showDirectoryIcons = Boolean.TRUE.equals(param1PropertyChangeEvent.getNewValue());
/*  765 */       } else if (str.equals("GTKFileChooser.showFileIcons")) {
/*  766 */         GTKFileChooserUI.this.showFileIcons = Boolean.TRUE.equals(param1PropertyChangeEvent.getNewValue());
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   protected void installDefaults(JFileChooser paramJFileChooser) {
/*  772 */     super.installDefaults(paramJFileChooser);
/*  773 */     this.readOnly = UIManager.getBoolean("FileChooser.readOnly");
/*  774 */     this
/*  775 */       .showDirectoryIcons = Boolean.TRUE.equals(paramJFileChooser.getClientProperty("GTKFileChooser.showDirectoryIcons"));
/*  776 */     this
/*  777 */       .showFileIcons = Boolean.TRUE.equals(paramJFileChooser.getClientProperty("GTKFileChooser.showFileIcons"));
/*      */   }
/*      */   
/*      */   protected void installIcons(JFileChooser paramJFileChooser) {
/*  781 */     this.directoryIcon = UIManager.getIcon("FileView.directoryIcon");
/*  782 */     this.fileIcon = UIManager.getIcon("FileView.fileIcon");
/*      */   }
/*      */   
/*      */   protected void installStrings(JFileChooser paramJFileChooser) {
/*  786 */     super.installStrings(paramJFileChooser);
/*      */     
/*  788 */     Locale locale = paramJFileChooser.getLocale();
/*      */     
/*  790 */     this.newFolderDialogText = UIManager.getString("FileChooser.newFolderDialogText", locale);
/*  791 */     this.newFolderErrorText = UIManager.getString("FileChooser.newFolderErrorText", locale);
/*  792 */     this.newFolderErrorSeparator = UIManager.getString("FileChooser.newFolderErrorSeparator", locale);
/*  793 */     this.newFolderButtonText = UIManager.getString("FileChooser.newFolderButtonText", locale);
/*  794 */     this.newFolderNoDirectoryErrorTitleText = UIManager.getString("FileChooser.newFolderNoDirectoryErrorTitleText", locale);
/*  795 */     this.newFolderNoDirectoryErrorText = UIManager.getString("FileChooser.newFolderNoDirectoryErrorText", locale);
/*  796 */     this.deleteFileButtonText = UIManager.getString("FileChooser.deleteFileButtonText", locale);
/*  797 */     this.renameFileButtonText = UIManager.getString("FileChooser.renameFileButtonText", locale);
/*      */     
/*  799 */     this.newFolderButtonMnemonic = getMnemonic("FileChooser.newFolderButtonMnemonic", locale);
/*  800 */     this.deleteFileButtonMnemonic = getMnemonic("FileChooser.deleteFileButtonMnemonic", locale);
/*  801 */     this.renameFileButtonMnemonic = getMnemonic("FileChooser.renameFileButtonMnemonic", locale);
/*      */     
/*  803 */     this.newFolderButtonToolTipText = UIManager.getString("FileChooser.newFolderButtonToolTipText", locale);
/*  804 */     this.deleteFileButtonToolTipText = UIManager.getString("FileChooser.deleteFileButtonToolTipText", locale);
/*  805 */     this.renameFileButtonToolTipText = UIManager.getString("FileChooser.renameFileButtonToolTipText", locale);
/*      */     
/*  807 */     this.renameFileDialogText = UIManager.getString("FileChooser.renameFileDialogText", locale);
/*  808 */     this.renameFileErrorTitle = UIManager.getString("FileChooser.renameFileErrorTitle", locale);
/*  809 */     this.renameFileErrorText = UIManager.getString("FileChooser.renameFileErrorText", locale);
/*      */     
/*  811 */     this.foldersLabelText = UIManager.getString("FileChooser.foldersLabelText", locale);
/*  812 */     this.foldersLabelMnemonic = getMnemonic("FileChooser.foldersLabelMnemonic", locale);
/*      */     
/*  814 */     this.filesLabelText = UIManager.getString("FileChooser.filesLabelText", locale);
/*  815 */     this.filesLabelMnemonic = getMnemonic("FileChooser.filesLabelMnemonic", locale);
/*      */     
/*  817 */     this.pathLabelText = UIManager.getString("FileChooser.pathLabelText", locale);
/*  818 */     this.pathLabelMnemonic = getMnemonic("FileChooser.pathLabelMnemonic", locale);
/*      */     
/*  820 */     this.filterLabelText = UIManager.getString("FileChooser.filterLabelText", locale);
/*  821 */     this.filterLabelMnemonic = UIManager.getInt("FileChooser.filterLabelMnemonic");
/*      */   }
/*      */   
/*      */   protected void uninstallStrings(JFileChooser paramJFileChooser) {
/*  825 */     super.uninstallStrings(paramJFileChooser);
/*      */     
/*  827 */     this.newFolderButtonText = null;
/*  828 */     this.deleteFileButtonText = null;
/*  829 */     this.renameFileButtonText = null;
/*      */     
/*  831 */     this.newFolderButtonToolTipText = null;
/*  832 */     this.deleteFileButtonToolTipText = null;
/*  833 */     this.renameFileButtonToolTipText = null;
/*      */     
/*  835 */     this.renameFileDialogText = null;
/*  836 */     this.renameFileErrorTitle = null;
/*  837 */     this.renameFileErrorText = null;
/*      */     
/*  839 */     this.foldersLabelText = null;
/*  840 */     this.filesLabelText = null;
/*      */     
/*  842 */     this.pathLabelText = null;
/*      */     
/*  844 */     this.newFolderDialogText = null;
/*  845 */     this.newFolderErrorText = null;
/*  846 */     this.newFolderErrorSeparator = null;
/*      */   }
/*      */   
/*      */   protected JScrollPane createFilesList() {
/*  850 */     this.fileList = new JList();
/*  851 */     this.fileList.setName("GTKFileChooser.fileList");
/*  852 */     this.fileList.putClientProperty("AccessibleName", this.filesLabelText);
/*      */     
/*  854 */     if (getFileChooser().isMultiSelectionEnabled()) {
/*  855 */       this.fileList.setSelectionMode(2);
/*      */     } else {
/*  857 */       this.fileList.setSelectionMode(0);
/*      */     } 
/*      */     
/*  860 */     this.fileList.setModel(new GTKFileListModel());
/*  861 */     this.fileList.getSelectionModel().removeSelectionInterval(0, 0);
/*  862 */     this.fileList.setCellRenderer(new FileCellRenderer());
/*  863 */     this.fileList.addListSelectionListener(createListSelectionListener(getFileChooser()));
/*  864 */     this.fileList.addMouseListener(createDoubleClickListener(getFileChooser(), this.fileList));
/*  865 */     align(this.fileList);
/*  866 */     JScrollPane jScrollPane = new JScrollPane(this.fileList);
/*  867 */     jScrollPane.setVerticalScrollBarPolicy(22);
/*  868 */     jScrollPane.setName("GTKFileChooser.fileListScrollPane");
/*  869 */     jScrollPane.setPreferredSize(prefListSize);
/*  870 */     jScrollPane.setMaximumSize(MAX_SIZE);
/*  871 */     align(jScrollPane);
/*  872 */     return jScrollPane;
/*      */   }
/*      */   
/*      */   protected JScrollPane createDirectoryList() {
/*  876 */     this.directoryList = new JList();
/*  877 */     this.directoryList.setName("GTKFileChooser.directoryList");
/*  878 */     this.directoryList.putClientProperty("AccessibleName", this.foldersLabelText);
/*  879 */     align(this.directoryList);
/*      */     
/*  881 */     this.directoryList.setCellRenderer(new DirectoryCellRenderer());
/*  882 */     this.directoryListModel = new GTKDirectoryListModel();
/*  883 */     this.directoryList.getSelectionModel().removeSelectionInterval(0, 0);
/*  884 */     this.directoryList.setModel(this.directoryListModel);
/*  885 */     this.directoryList.addMouseListener(createDoubleClickListener(getFileChooser(), this.directoryList));
/*  886 */     this.directoryList.addListSelectionListener(createListSelectionListener(getFileChooser()));
/*      */     
/*  888 */     JScrollPane jScrollPane = new JScrollPane(this.directoryList);
/*  889 */     jScrollPane.setVerticalScrollBarPolicy(22);
/*  890 */     jScrollPane.setName("GTKFileChooser.directoryListScrollPane");
/*  891 */     jScrollPane.setMaximumSize(MAX_SIZE);
/*  892 */     jScrollPane.setPreferredSize(prefListSize);
/*  893 */     align(jScrollPane);
/*  894 */     return jScrollPane;
/*      */   }
/*      */   
/*      */   protected void createModel() {
/*  898 */     this.model = new GTKDirectoryModel();
/*      */   }
/*      */   
/*      */   public BasicDirectoryModel getModel() {
/*  902 */     return this.model;
/*      */   }
/*      */   
/*      */   public Action getApproveSelectionAction() {
/*  906 */     return this.approveSelectionAction;
/*      */   }
/*      */   
/*      */   private class GTKDirectoryModel extends BasicDirectoryModel { FileSystemView fsv;
/*      */     
/*  911 */     private Comparator<File> fileComparator = new Comparator<File>() {
/*      */         public int compare(File param2File1, File param2File2) {
/*  913 */           return GTKFileChooserUI.GTKDirectoryModel.this.fsv.getSystemDisplayName(param2File1).compareTo(GTKFileChooserUI.GTKDirectoryModel.this.fsv.getSystemDisplayName(param2File2));
/*      */         }
/*      */       };
/*      */     
/*      */     public GTKDirectoryModel() {
/*  918 */       super(GTKFileChooserUI.this.getFileChooser());
/*      */     }
/*      */     
/*      */     protected void sort(Vector<? extends File> param1Vector) {
/*  922 */       this.fsv = GTKFileChooserUI.this.getFileChooser().getFileSystemView();
/*  923 */       Collections.sort(param1Vector, this.fileComparator);
/*      */     } }
/*      */   
/*      */   protected class GTKDirectoryListModel extends AbstractListModel implements ListDataListener {
/*      */     File curDir;
/*      */     
/*      */     public GTKDirectoryListModel() {
/*  930 */       GTKFileChooserUI.this.getModel().addListDataListener(this);
/*  931 */       directoryChanged();
/*      */     }
/*      */     
/*      */     public int getSize() {
/*  935 */       return GTKFileChooserUI.this.getModel().getDirectories().size() + 1;
/*      */     }
/*      */     
/*      */     public Object getElementAt(int param1Int) {
/*  939 */       return (param1Int > 0) ? GTKFileChooserUI.this.getModel().getDirectories().elementAt(param1Int - 1) : this.curDir;
/*      */     }
/*      */ 
/*      */     
/*      */     public void intervalAdded(ListDataEvent param1ListDataEvent) {
/*  944 */       fireIntervalAdded(this, param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/*      */     }
/*      */     
/*      */     public void intervalRemoved(ListDataEvent param1ListDataEvent) {
/*  948 */       fireIntervalRemoved(this, param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void fireContentsChanged() {
/*  955 */       fireContentsChanged(this, 0, GTKFileChooserUI.this.getModel().getDirectories().size() - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void contentsChanged(ListDataEvent param1ListDataEvent) {
/*  961 */       fireContentsChanged();
/*      */     }
/*      */     
/*      */     private void directoryChanged() {
/*  965 */       this.curDir = GTKFileChooserUI.this.getFileChooser().getFileSystemView().createFileObject(GTKFileChooserUI.this
/*  966 */           .getFileChooser().getCurrentDirectory(), ".");
/*      */     }
/*      */   }
/*      */   
/*      */   protected class GTKFileListModel extends AbstractListModel implements ListDataListener {
/*      */     public GTKFileListModel() {
/*  972 */       GTKFileChooserUI.this.getModel().addListDataListener(this);
/*      */     }
/*      */     
/*      */     public int getSize() {
/*  976 */       return GTKFileChooserUI.this.getModel().getFiles().size();
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/*  980 */       return GTKFileChooserUI.this.getModel().getFiles().contains(param1Object);
/*      */     }
/*      */     
/*      */     public int indexOf(Object param1Object) {
/*  984 */       return GTKFileChooserUI.this.getModel().getFiles().indexOf(param1Object);
/*      */     }
/*      */     
/*      */     public Object getElementAt(int param1Int) {
/*  988 */       return GTKFileChooserUI.this.getModel().getFiles().elementAt(param1Int);
/*      */     }
/*      */     
/*      */     public void intervalAdded(ListDataEvent param1ListDataEvent) {
/*  992 */       fireIntervalAdded(this, param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/*      */     }
/*      */     
/*      */     public void intervalRemoved(ListDataEvent param1ListDataEvent) {
/*  996 */       fireIntervalRemoved(this, param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void fireContentsChanged() {
/* 1003 */       fireContentsChanged(this, 0, GTKFileChooserUI.this.getModel().getFiles().size() - 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void contentsChanged(ListDataEvent param1ListDataEvent) {
/* 1008 */       fireContentsChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected class FileCellRenderer
/*      */     extends DefaultListCellRenderer
/*      */   {
/*      */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 1017 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/* 1018 */       setText(GTKFileChooserUI.this.getFileChooser().getName((File)param1Object));
/* 1019 */       if (GTKFileChooserUI.this.showFileIcons) {
/* 1020 */         setIcon(GTKFileChooserUI.this.getFileChooser().getIcon((File)param1Object));
/*      */       }
/* 1022 */       return this;
/*      */     }
/*      */   }
/*      */   
/*      */   protected class DirectoryCellRenderer
/*      */     extends DefaultListCellRenderer
/*      */   {
/*      */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 1030 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/*      */       
/* 1032 */       if (GTKFileChooserUI.this.showDirectoryIcons) {
/* 1033 */         setIcon(GTKFileChooserUI.this.getFileChooser().getIcon((File)param1Object));
/* 1034 */         setText(GTKFileChooserUI.this.getFileChooser().getName((File)param1Object));
/*      */       } else {
/* 1036 */         setText(GTKFileChooserUI.this.getFileChooser().getName((File)param1Object) + "/");
/*      */       } 
/* 1038 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 1044 */     Dimension dimension1 = new Dimension(PREF_SIZE);
/* 1045 */     JComponent jComponent = getFileChooser().getAccessory();
/* 1046 */     if (jComponent != null) {
/* 1047 */       dimension1.width += (jComponent.getPreferredSize()).width + 20;
/*      */     }
/* 1049 */     Dimension dimension2 = paramJComponent.getLayout().preferredLayoutSize(paramJComponent);
/* 1050 */     if (dimension2 != null) {
/* 1051 */       return new Dimension((dimension2.width < dimension1.width) ? dimension1.width : dimension2.width, (dimension2.height < dimension1.height) ? dimension1.height : dimension2.height);
/*      */     }
/*      */     
/* 1054 */     return dimension1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 1060 */     return new Dimension(200, 300);
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 1065 */     return new Dimension(2147483647, 2147483647);
/*      */   }
/*      */   
/*      */   protected void align(JComponent paramJComponent) {
/* 1069 */     paramJComponent.setAlignmentX(0.0F);
/* 1070 */     paramJComponent.setAlignmentY(0.0F);
/*      */   }
/*      */   
/*      */   public Action getNewFolderAction() {
/* 1074 */     if (this.newFolderAction == null) {
/* 1075 */       this.newFolderAction = new NewFolderAction();
/* 1076 */       this.newFolderAction.setEnabled(!this.readOnly);
/*      */     } 
/* 1078 */     return this.newFolderAction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DirectoryComboBoxModel createDirectoryComboBoxModel(JFileChooser paramJFileChooser) {
/* 1085 */     return new DirectoryComboBoxModel();
/*      */   }
/*      */   
/*      */   protected class DirectoryComboBoxModel
/*      */     extends AbstractListModel
/*      */     implements ComboBoxModel
/*      */   {
/* 1092 */     Vector<File> directories = new Vector<>();
/* 1093 */     File selectedDirectory = null;
/* 1094 */     JFileChooser chooser = GTKFileChooserUI.this.getFileChooser();
/* 1095 */     FileSystemView fsv = this.chooser.getFileSystemView();
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
/*      */     private void addItem(File param1File) {
/*      */       File file1;
/* 1113 */       if (param1File == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1117 */       int i = this.directories.size();
/* 1118 */       this.directories.clear();
/* 1119 */       if (i > 0) {
/* 1120 */         fireIntervalRemoved(this, 0, i);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1128 */         file1 = this.fsv.createFileObject(ShellFolder.getNormalizedFile(param1File).getPath());
/* 1129 */       } catch (IOException iOException) {
/*      */         
/* 1131 */         file1 = param1File;
/*      */       } 
/*      */ 
/*      */       
/* 1135 */       File file2 = file1;
/*      */       do {
/* 1137 */         this.directories.add(file2);
/* 1138 */       } while ((file2 = file2.getParentFile()) != null);
/* 1139 */       int j = this.directories.size();
/* 1140 */       if (j > 0) {
/* 1141 */         fireIntervalAdded(this, 0, j);
/*      */       }
/* 1143 */       setSelectedItem(file1);
/*      */     }
/*      */     
/*      */     public void setSelectedItem(Object param1Object) {
/* 1147 */       this.selectedDirectory = (File)param1Object;
/* 1148 */       fireContentsChanged(this, -1, -1);
/*      */     }
/*      */     
/*      */     public Object getSelectedItem() {
/* 1152 */       return this.selectedDirectory;
/*      */     }
/*      */     
/*      */     public int getSize() {
/* 1156 */       return this.directories.size();
/*      */     }
/*      */     
/*      */     public Object getElementAt(int param1Int) {
/* 1160 */       return this.directories.elementAt(param1Int);
/*      */     }
/*      */     public DirectoryComboBoxModel() {
/*      */       File file = GTKFileChooserUI.this.getFileChooser().getCurrentDirectory();
/*      */       if (file != null)
/*      */         addItem(file); 
/*      */     } }
/*      */   
/*      */   protected class DirectoryComboBoxAction extends AbstractAction { protected DirectoryComboBoxAction() {
/* 1169 */       super("DirectoryComboBoxAction");
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1173 */       File file = (File)GTKFileChooserUI.this.directoryComboBox.getSelectedItem();
/* 1174 */       GTKFileChooserUI.this.getFileChooser().setCurrentDirectory(file);
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   private class NewFolderAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected NewFolderAction() {
/* 1183 */       super("New Folder");
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1186 */       if (GTKFileChooserUI.this.readOnly) {
/*      */         return;
/*      */       }
/* 1189 */       JFileChooser jFileChooser = GTKFileChooserUI.this.getFileChooser();
/* 1190 */       File file = jFileChooser.getCurrentDirectory();
/* 1191 */       String str = JOptionPane.showInputDialog(jFileChooser, GTKFileChooserUI.this
/* 1192 */           .newFolderDialogText, GTKFileChooserUI.this.newFolderButtonText, -1);
/*      */ 
/*      */       
/* 1195 */       if (str != null) {
/* 1196 */         if (!file.exists()) {
/* 1197 */           JOptionPane.showMessageDialog(jFileChooser, 
/* 1198 */               MessageFormat.format(GTKFileChooserUI.this.newFolderNoDirectoryErrorText, new Object[] { str }), GTKFileChooserUI.this
/* 1199 */               .newFolderNoDirectoryErrorTitleText, 0);
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 1204 */         File file1 = jFileChooser.getFileSystemView().createFileObject(file, str);
/* 1205 */         if (file1 == null || !file1.mkdir()) {
/* 1206 */           JOptionPane.showMessageDialog(jFileChooser, GTKFileChooserUI.this
/* 1207 */               .newFolderErrorText + GTKFileChooserUI.this.newFolderErrorSeparator + " \"" + str + "\"", GTKFileChooserUI.this
/*      */               
/* 1209 */               .newFolderErrorText, 0);
/*      */         }
/* 1211 */         jFileChooser.rescanCurrentDirectory();
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class GTKApproveSelectionAction extends BasicFileChooserUI.ApproveSelectionAction { private GTKApproveSelectionAction() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1218 */       if (GTKFileChooserUI.this.isDirectorySelected()) {
/* 1219 */         File file = GTKFileChooserUI.this.getDirectory();
/*      */         
/*      */         try {
/* 1222 */           if (file != null) {
/* 1223 */             file = ShellFolder.getNormalizedFile(file);
/*      */           }
/* 1225 */         } catch (IOException iOException) {}
/*      */ 
/*      */         
/* 1228 */         if (GTKFileChooserUI.this.getFileChooser().getCurrentDirectory().equals(file)) {
/* 1229 */           GTKFileChooserUI.this.directoryList.clearSelection();
/* 1230 */           GTKFileChooserUI.this.fileList.clearSelection();
/* 1231 */           ListSelectionModel listSelectionModel = GTKFileChooserUI.this.fileList.getSelectionModel();
/* 1232 */           if (listSelectionModel instanceof DefaultListSelectionModel) {
/* 1233 */             ((DefaultListSelectionModel)listSelectionModel).moveLeadSelectionIndex(0);
/* 1234 */             listSelectionModel.setAnchorSelectionIndex(0);
/*      */           } 
/* 1236 */           GTKFileChooserUI.this.rescanCurrentDirectory(GTKFileChooserUI.this.getFileChooser());
/*      */           return;
/*      */         } 
/*      */       } 
/* 1240 */       super.actionPerformed(param1ActionEvent);
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   private class RenameFileAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected RenameFileAction() {
/* 1249 */       super("editFileName");
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1252 */       if (GTKFileChooserUI.this.getFileName().equals("")) {
/*      */         return;
/*      */       }
/* 1255 */       JFileChooser jFileChooser = GTKFileChooserUI.this.getFileChooser();
/* 1256 */       File file = jFileChooser.getCurrentDirectory();
/*      */       
/* 1258 */       String str = (String)JOptionPane.showInputDialog(jFileChooser, (new MessageFormat(GTKFileChooserUI.this.renameFileDialogText))
/* 1259 */           .format(new Object[] { this.this$0.getFileName() }, ), GTKFileChooserUI.this
/* 1260 */           .renameFileButtonText, -1, null, null, GTKFileChooserUI.this
/* 1261 */           .getFileName());
/*      */       
/* 1263 */       if (str != null) {
/*      */         
/* 1265 */         File file1 = jFileChooser.getFileSystemView().createFileObject(file, GTKFileChooserUI.this.getFileName());
/*      */         
/* 1267 */         File file2 = jFileChooser.getFileSystemView().createFileObject(file, str);
/* 1268 */         if (file1 == null || file2 == null || 
/* 1269 */           !GTKFileChooserUI.this.getModel().renameFile(file1, file2)) {
/* 1270 */           JOptionPane.showMessageDialog(jFileChooser, (new MessageFormat(GTKFileChooserUI.this
/* 1271 */                 .renameFileErrorText))
/* 1272 */               .format(new Object[] { this.this$0.getFileName(), str }, ), GTKFileChooserUI.this
/* 1273 */               .renameFileErrorTitle, 0);
/*      */         } else {
/* 1275 */           GTKFileChooserUI.this.setFileName(GTKFileChooserUI.this.getFileChooser().getName(file2));
/* 1276 */           jFileChooser.rescanCurrentDirectory();
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FilterComboBoxRenderer createFilterComboBoxRenderer() {
/* 1286 */     return new FilterComboBoxRenderer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class FilterComboBoxRenderer
/*      */     extends DefaultListCellRenderer
/*      */   {
/*      */     public String getName() {
/* 1298 */       String str = super.getName();
/* 1299 */       if (str == null) {
/* 1300 */         return "ComboBox.renderer";
/*      */       }
/* 1302 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 1309 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/*      */       
/* 1311 */       setName("ComboBox.listRenderer");
/*      */       
/* 1313 */       if (param1Object != null) {
/* 1314 */         if (param1Object instanceof FileFilter) {
/* 1315 */           setText(((FileFilter)param1Object).getDescription());
/*      */         }
/*      */       } else {
/* 1318 */         setText("");
/*      */       } 
/*      */       
/* 1321 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FilterComboBoxModel createFilterComboBoxModel() {
/* 1329 */     return new FilterComboBoxModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class FilterComboBoxModel
/*      */     extends AbstractListModel
/*      */     implements ComboBoxModel, PropertyChangeListener
/*      */   {
/* 1341 */     protected FileFilter[] filters = GTKFileChooserUI.this.getFileChooser().getChoosableFileFilters();
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1345 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1346 */       if (str == "ChoosableFileFilterChangedProperty") {
/* 1347 */         this.filters = (FileFilter[])param1PropertyChangeEvent.getNewValue();
/* 1348 */         fireContentsChanged(this, -1, -1);
/* 1349 */       } else if (str == "fileFilterChanged") {
/* 1350 */         fireContentsChanged(this, -1, -1);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void setSelectedItem(Object param1Object) {
/* 1355 */       if (param1Object != null) {
/* 1356 */         GTKFileChooserUI.this.getFileChooser().setFileFilter((FileFilter)param1Object);
/* 1357 */         fireContentsChanged(this, -1, -1);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getSelectedItem() {
/* 1367 */       FileFilter fileFilter = GTKFileChooserUI.this.getFileChooser().getFileFilter();
/* 1368 */       boolean bool = false;
/* 1369 */       if (fileFilter != null) {
/* 1370 */         for (FileFilter fileFilter1 : this.filters) {
/* 1371 */           if (fileFilter1 == fileFilter) {
/* 1372 */             bool = true;
/*      */           }
/*      */         } 
/* 1375 */         if (!bool) {
/* 1376 */           GTKFileChooserUI.this.getFileChooser().addChoosableFileFilter(fileFilter);
/*      */         }
/*      */       } 
/* 1379 */       return GTKFileChooserUI.this.getFileChooser().getFileFilter();
/*      */     }
/*      */     
/*      */     public int getSize() {
/* 1383 */       if (this.filters != null) {
/* 1384 */         return this.filters.length;
/*      */       }
/* 1386 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getElementAt(int param1Int) {
/* 1391 */       if (param1Int > getSize() - 1)
/*      */       {
/* 1393 */         return GTKFileChooserUI.this.getFileChooser().getFileFilter();
/*      */       }
/* 1395 */       if (this.filters != null) {
/* 1396 */         return this.filters[param1Int];
/*      */       }
/* 1398 */       return null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/GTKFileChooserUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */