/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.datatransfer.DataFlavor;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.regex.Pattern;
/*      */ import java.util.regex.PatternSyntaxException;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.TransferHandler;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.filechooser.FileFilter;
/*      */ import javax.swing.filechooser.FileSystemView;
/*      */ import javax.swing.filechooser.FileView;
/*      */ import javax.swing.plaf.ActionMapUIResource;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.FileChooserUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import sun.awt.shell.ShellFolder;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.FilePane;
/*      */ import sun.swing.SwingUtilities2;
/*      */ import sun.swing.UIAction;
/*      */ 
/*      */ public class BasicFileChooserUI extends FileChooserUI {
/*   53 */   protected Icon directoryIcon = null;
/*   54 */   protected Icon fileIcon = null;
/*   55 */   protected Icon computerIcon = null;
/*   56 */   protected Icon hardDriveIcon = null;
/*   57 */   protected Icon floppyDriveIcon = null;
/*      */   
/*   59 */   protected Icon newFolderIcon = null;
/*   60 */   protected Icon upFolderIcon = null;
/*   61 */   protected Icon homeFolderIcon = null;
/*   62 */   protected Icon listViewIcon = null;
/*   63 */   protected Icon detailsViewIcon = null;
/*   64 */   protected Icon viewMenuIcon = null;
/*      */   
/*   66 */   protected int saveButtonMnemonic = 0;
/*   67 */   protected int openButtonMnemonic = 0;
/*   68 */   protected int cancelButtonMnemonic = 0;
/*   69 */   protected int updateButtonMnemonic = 0;
/*   70 */   protected int helpButtonMnemonic = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   protected int directoryOpenButtonMnemonic = 0;
/*      */   
/*   80 */   protected String saveButtonText = null;
/*   81 */   protected String openButtonText = null;
/*   82 */   protected String cancelButtonText = null;
/*   83 */   protected String updateButtonText = null;
/*   84 */   protected String helpButtonText = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   92 */   protected String directoryOpenButtonText = null;
/*      */   
/*   94 */   private String openDialogTitleText = null;
/*   95 */   private String saveDialogTitleText = null;
/*      */   
/*   97 */   protected String saveButtonToolTipText = null;
/*   98 */   protected String openButtonToolTipText = null;
/*   99 */   protected String cancelButtonToolTipText = null;
/*  100 */   protected String updateButtonToolTipText = null;
/*  101 */   protected String helpButtonToolTipText = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   protected String directoryOpenButtonToolTipText = null;
/*      */ 
/*      */   
/*  112 */   private Action approveSelectionAction = new ApproveSelectionAction();
/*  113 */   private Action cancelSelectionAction = new CancelSelectionAction();
/*  114 */   private Action updateAction = new UpdateAction();
/*      */   private Action newFolderAction;
/*  116 */   private Action goHomeAction = new GoHomeAction();
/*  117 */   private Action changeToParentDirectoryAction = new ChangeToParentDirectoryAction();
/*      */   
/*  119 */   private String newFolderErrorSeparator = null;
/*  120 */   private String newFolderErrorText = null;
/*  121 */   private String newFolderParentDoesntExistTitleText = null;
/*  122 */   private String newFolderParentDoesntExistText = null;
/*  123 */   private String fileDescriptionText = null;
/*  124 */   private String directoryDescriptionText = null;
/*      */   
/*  126 */   private JFileChooser filechooser = null;
/*      */   
/*      */   private boolean directorySelected = false;
/*  129 */   private File directory = null;
/*      */   
/*  131 */   private PropertyChangeListener propertyChangeListener = null;
/*  132 */   private AcceptAllFileFilter acceptAllFileFilter = new AcceptAllFileFilter();
/*  133 */   private FileFilter actualFileFilter = null;
/*  134 */   private GlobFilter globFilter = null;
/*  135 */   private BasicDirectoryModel model = null;
/*  136 */   private BasicFileView fileView = new BasicFileView();
/*      */   
/*      */   private boolean usesSingleFilePane;
/*      */   
/*      */   private boolean readOnly;
/*  141 */   private JPanel accessoryPanel = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler handler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  158 */     return new BasicFileChooserUI((JFileChooser)paramJComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  165 */     this.accessoryPanel = new JPanel(new BorderLayout());
/*  166 */     this.filechooser = (JFileChooser)paramJComponent;
/*      */     
/*  168 */     createModel();
/*      */     
/*  170 */     clearIconCache();
/*      */     
/*  172 */     installDefaults(this.filechooser);
/*  173 */     installComponents(this.filechooser);
/*  174 */     installListeners(this.filechooser);
/*  175 */     this.filechooser.applyComponentOrientation(this.filechooser.getComponentOrientation());
/*      */   }
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  179 */     uninstallListeners(this.filechooser);
/*  180 */     uninstallComponents(this.filechooser);
/*  181 */     uninstallDefaults(this.filechooser);
/*      */     
/*  183 */     if (this.accessoryPanel != null) {
/*  184 */       this.accessoryPanel.removeAll();
/*      */     }
/*      */     
/*  187 */     this.accessoryPanel = null;
/*  188 */     getFileChooser().removeAll();
/*      */     
/*  190 */     this.handler = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void installComponents(JFileChooser paramJFileChooser) {}
/*      */ 
/*      */   
/*      */   public void uninstallComponents(JFileChooser paramJFileChooser) {}
/*      */   
/*      */   protected void installListeners(JFileChooser paramJFileChooser) {
/*  200 */     this.propertyChangeListener = createPropertyChangeListener(paramJFileChooser);
/*  201 */     if (this.propertyChangeListener != null) {
/*  202 */       paramJFileChooser.addPropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*  204 */     paramJFileChooser.addPropertyChangeListener(getModel());
/*      */     
/*  206 */     InputMap inputMap = getInputMap(1);
/*      */     
/*  208 */     SwingUtilities.replaceUIInputMap(paramJFileChooser, 1, inputMap);
/*      */     
/*  210 */     ActionMap actionMap = getActionMap();
/*  211 */     SwingUtilities.replaceUIActionMap(paramJFileChooser, actionMap);
/*      */   }
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/*  215 */     if (paramInt == 1) {
/*  216 */       return (InputMap)DefaultLookup.get(getFileChooser(), this, "FileChooser.ancestorInputMap");
/*      */     }
/*      */     
/*  219 */     return null;
/*      */   }
/*      */   
/*      */   ActionMap getActionMap() {
/*  223 */     return createActionMap();
/*      */   }
/*      */   
/*      */   ActionMap createActionMap() {
/*  227 */     ActionMapUIResource actionMapUIResource = new ActionMapUIResource();
/*      */     
/*  229 */     UIAction uIAction = new UIAction("refresh") {
/*      */         public void actionPerformed(ActionEvent param1ActionEvent) {
/*  231 */           BasicFileChooserUI.this.getFileChooser().rescanCurrentDirectory();
/*      */         }
/*      */       };
/*      */     
/*  235 */     actionMapUIResource.put("approveSelection", getApproveSelectionAction());
/*  236 */     actionMapUIResource.put("cancelSelection", getCancelSelectionAction());
/*  237 */     actionMapUIResource.put("refresh", uIAction);
/*  238 */     actionMapUIResource.put("Go Up", 
/*  239 */         getChangeToParentDirectoryAction());
/*  240 */     return actionMapUIResource;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallListeners(JFileChooser paramJFileChooser) {
/*  245 */     if (this.propertyChangeListener != null) {
/*  246 */       paramJFileChooser.removePropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*  248 */     paramJFileChooser.removePropertyChangeListener(getModel());
/*  249 */     SwingUtilities.replaceUIInputMap(paramJFileChooser, 1, null);
/*      */     
/*  251 */     SwingUtilities.replaceUIActionMap(paramJFileChooser, null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installDefaults(JFileChooser paramJFileChooser) {
/*  256 */     installIcons(paramJFileChooser);
/*  257 */     installStrings(paramJFileChooser);
/*  258 */     this.usesSingleFilePane = UIManager.getBoolean("FileChooser.usesSingleFilePane");
/*  259 */     this.readOnly = UIManager.getBoolean("FileChooser.readOnly");
/*  260 */     TransferHandler transferHandler = paramJFileChooser.getTransferHandler();
/*  261 */     if (transferHandler == null || transferHandler instanceof UIResource) {
/*  262 */       paramJFileChooser.setTransferHandler(defaultTransferHandler);
/*      */     }
/*  264 */     LookAndFeel.installProperty(paramJFileChooser, "opaque", Boolean.FALSE);
/*      */   }
/*      */   
/*      */   protected void installIcons(JFileChooser paramJFileChooser) {
/*  268 */     this.directoryIcon = UIManager.getIcon("FileView.directoryIcon");
/*  269 */     this.fileIcon = UIManager.getIcon("FileView.fileIcon");
/*  270 */     this.computerIcon = UIManager.getIcon("FileView.computerIcon");
/*  271 */     this.hardDriveIcon = UIManager.getIcon("FileView.hardDriveIcon");
/*  272 */     this.floppyDriveIcon = UIManager.getIcon("FileView.floppyDriveIcon");
/*      */     
/*  274 */     this.newFolderIcon = UIManager.getIcon("FileChooser.newFolderIcon");
/*  275 */     this.upFolderIcon = UIManager.getIcon("FileChooser.upFolderIcon");
/*  276 */     this.homeFolderIcon = UIManager.getIcon("FileChooser.homeFolderIcon");
/*  277 */     this.detailsViewIcon = UIManager.getIcon("FileChooser.detailsViewIcon");
/*  278 */     this.listViewIcon = UIManager.getIcon("FileChooser.listViewIcon");
/*  279 */     this.viewMenuIcon = UIManager.getIcon("FileChooser.viewMenuIcon");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installStrings(JFileChooser paramJFileChooser) {
/*  284 */     Locale locale = paramJFileChooser.getLocale();
/*  285 */     this.newFolderErrorText = UIManager.getString("FileChooser.newFolderErrorText", locale);
/*  286 */     this.newFolderErrorSeparator = UIManager.getString("FileChooser.newFolderErrorSeparator", locale);
/*      */     
/*  288 */     this.newFolderParentDoesntExistTitleText = UIManager.getString("FileChooser.newFolderParentDoesntExistTitleText", locale);
/*  289 */     this.newFolderParentDoesntExistText = UIManager.getString("FileChooser.newFolderParentDoesntExistText", locale);
/*      */     
/*  291 */     this.fileDescriptionText = UIManager.getString("FileChooser.fileDescriptionText", locale);
/*  292 */     this.directoryDescriptionText = UIManager.getString("FileChooser.directoryDescriptionText", locale);
/*      */     
/*  294 */     this.saveButtonText = UIManager.getString("FileChooser.saveButtonText", locale);
/*  295 */     this.openButtonText = UIManager.getString("FileChooser.openButtonText", locale);
/*  296 */     this.saveDialogTitleText = UIManager.getString("FileChooser.saveDialogTitleText", locale);
/*  297 */     this.openDialogTitleText = UIManager.getString("FileChooser.openDialogTitleText", locale);
/*  298 */     this.cancelButtonText = UIManager.getString("FileChooser.cancelButtonText", locale);
/*  299 */     this.updateButtonText = UIManager.getString("FileChooser.updateButtonText", locale);
/*  300 */     this.helpButtonText = UIManager.getString("FileChooser.helpButtonText", locale);
/*  301 */     this.directoryOpenButtonText = UIManager.getString("FileChooser.directoryOpenButtonText", locale);
/*      */     
/*  303 */     this.saveButtonMnemonic = getMnemonic("FileChooser.saveButtonMnemonic", locale);
/*  304 */     this.openButtonMnemonic = getMnemonic("FileChooser.openButtonMnemonic", locale);
/*  305 */     this.cancelButtonMnemonic = getMnemonic("FileChooser.cancelButtonMnemonic", locale);
/*  306 */     this.updateButtonMnemonic = getMnemonic("FileChooser.updateButtonMnemonic", locale);
/*  307 */     this.helpButtonMnemonic = getMnemonic("FileChooser.helpButtonMnemonic", locale);
/*  308 */     this.directoryOpenButtonMnemonic = getMnemonic("FileChooser.directoryOpenButtonMnemonic", locale);
/*      */     
/*  310 */     this.saveButtonToolTipText = UIManager.getString("FileChooser.saveButtonToolTipText", locale);
/*  311 */     this.openButtonToolTipText = UIManager.getString("FileChooser.openButtonToolTipText", locale);
/*  312 */     this.cancelButtonToolTipText = UIManager.getString("FileChooser.cancelButtonToolTipText", locale);
/*  313 */     this.updateButtonToolTipText = UIManager.getString("FileChooser.updateButtonToolTipText", locale);
/*  314 */     this.helpButtonToolTipText = UIManager.getString("FileChooser.helpButtonToolTipText", locale);
/*  315 */     this.directoryOpenButtonToolTipText = UIManager.getString("FileChooser.directoryOpenButtonToolTipText", locale);
/*      */   }
/*      */   
/*      */   protected void uninstallDefaults(JFileChooser paramJFileChooser) {
/*  319 */     uninstallIcons(paramJFileChooser);
/*  320 */     uninstallStrings(paramJFileChooser);
/*  321 */     if (paramJFileChooser.getTransferHandler() instanceof UIResource) {
/*  322 */       paramJFileChooser.setTransferHandler((TransferHandler)null);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void uninstallIcons(JFileChooser paramJFileChooser) {
/*  327 */     this.directoryIcon = null;
/*  328 */     this.fileIcon = null;
/*  329 */     this.computerIcon = null;
/*  330 */     this.hardDriveIcon = null;
/*  331 */     this.floppyDriveIcon = null;
/*      */     
/*  333 */     this.newFolderIcon = null;
/*  334 */     this.upFolderIcon = null;
/*  335 */     this.homeFolderIcon = null;
/*  336 */     this.detailsViewIcon = null;
/*  337 */     this.listViewIcon = null;
/*  338 */     this.viewMenuIcon = null;
/*      */   }
/*      */   
/*      */   protected void uninstallStrings(JFileChooser paramJFileChooser) {
/*  342 */     this.saveButtonText = null;
/*  343 */     this.openButtonText = null;
/*  344 */     this.cancelButtonText = null;
/*  345 */     this.updateButtonText = null;
/*  346 */     this.helpButtonText = null;
/*  347 */     this.directoryOpenButtonText = null;
/*      */     
/*  349 */     this.saveButtonToolTipText = null;
/*  350 */     this.openButtonToolTipText = null;
/*  351 */     this.cancelButtonToolTipText = null;
/*  352 */     this.updateButtonToolTipText = null;
/*  353 */     this.helpButtonToolTipText = null;
/*  354 */     this.directoryOpenButtonToolTipText = null;
/*      */   }
/*      */   
/*      */   protected void createModel() {
/*  358 */     if (this.model != null) {
/*  359 */       this.model.invalidateFileCache();
/*      */     }
/*  361 */     this.model = new BasicDirectoryModel(getFileChooser());
/*      */   }
/*      */   
/*      */   public BasicDirectoryModel getModel() {
/*  365 */     return this.model;
/*      */   }
/*      */   
/*      */   public PropertyChangeListener createPropertyChangeListener(JFileChooser paramJFileChooser) {
/*  369 */     return null;
/*      */   }
/*      */   
/*      */   public String getFileName() {
/*  373 */     return null;
/*      */   }
/*      */   
/*      */   public String getDirectoryName() {
/*  377 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFileName(String paramString) {}
/*      */ 
/*      */   
/*      */   public void setDirectoryName(String paramString) {}
/*      */ 
/*      */   
/*      */   public void rescanCurrentDirectory(JFileChooser paramJFileChooser) {}
/*      */ 
/*      */   
/*      */   public void ensureFileIsVisible(JFileChooser paramJFileChooser, File paramFile) {}
/*      */   
/*      */   public JFileChooser getFileChooser() {
/*  393 */     return this.filechooser;
/*      */   }
/*      */   
/*      */   public JPanel getAccessoryPanel() {
/*  397 */     return this.accessoryPanel;
/*      */   }
/*      */   
/*      */   protected JButton getApproveButton(JFileChooser paramJFileChooser) {
/*  401 */     return null;
/*      */   }
/*      */   
/*      */   public JButton getDefaultButton(JFileChooser paramJFileChooser) {
/*  405 */     return getApproveButton(paramJFileChooser);
/*      */   }
/*      */   
/*      */   public String getApproveButtonToolTipText(JFileChooser paramJFileChooser) {
/*  409 */     String str = paramJFileChooser.getApproveButtonToolTipText();
/*  410 */     if (str != null) {
/*  411 */       return str;
/*      */     }
/*      */     
/*  414 */     if (paramJFileChooser.getDialogType() == 0)
/*  415 */       return this.openButtonToolTipText; 
/*  416 */     if (paramJFileChooser.getDialogType() == 1) {
/*  417 */       return this.saveButtonToolTipText;
/*      */     }
/*  419 */     return null;
/*      */   }
/*      */   
/*      */   public void clearIconCache() {
/*  423 */     this.fileView.clearIconCache();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler getHandler() {
/*  432 */     if (this.handler == null) {
/*  433 */       this.handler = new Handler();
/*      */     }
/*  435 */     return this.handler;
/*      */   }
/*      */ 
/*      */   
/*      */   protected MouseListener createDoubleClickListener(JFileChooser paramJFileChooser, JList paramJList) {
/*  440 */     return new Handler(paramJList);
/*      */   }
/*      */   
/*      */   public ListSelectionListener createListSelectionListener(JFileChooser paramJFileChooser) {
/*  444 */     return getHandler();
/*      */   }
/*      */   
/*      */   private class Handler
/*      */     implements MouseListener, ListSelectionListener {
/*      */     JList list;
/*      */     
/*      */     Handler() {}
/*      */     
/*      */     Handler(JList param1JList) {
/*  454 */       this.list = param1JList;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/*  460 */       if (this.list != null && 
/*  461 */         SwingUtilities.isLeftMouseButton(param1MouseEvent) && param1MouseEvent
/*  462 */         .getClickCount() % 2 == 0) {
/*      */         
/*  464 */         int i = SwingUtilities2.loc2IndexFileList(this.list, param1MouseEvent.getPoint());
/*  465 */         if (i >= 0) {
/*  466 */           File file = this.list.getModel().getElementAt(i);
/*      */           
/*      */           try {
/*  469 */             file = ShellFolder.getNormalizedFile(file);
/*  470 */           } catch (IOException iOException) {}
/*      */ 
/*      */           
/*  473 */           if (BasicFileChooserUI.this.getFileChooser().isTraversable(file)) {
/*  474 */             this.list.clearSelection();
/*  475 */             BasicFileChooserUI.this.changeDirectory(file);
/*      */           } else {
/*  477 */             BasicFileChooserUI.this.getFileChooser().approveSelection();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/*  484 */       if (this.list != null) {
/*  485 */         TransferHandler transferHandler1 = BasicFileChooserUI.this.getFileChooser().getTransferHandler();
/*  486 */         TransferHandler transferHandler2 = this.list.getTransferHandler();
/*  487 */         if (transferHandler1 != transferHandler2) {
/*  488 */           this.list.setTransferHandler(transferHandler1);
/*      */         }
/*  490 */         if (BasicFileChooserUI.this.getFileChooser().getDragEnabled() != this.list.getDragEnabled()) {
/*  491 */           this.list.setDragEnabled(BasicFileChooserUI.this.getFileChooser().getDragEnabled());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {
/*  506 */       if (!param1ListSelectionEvent.getValueIsAdjusting()) {
/*  507 */         JFileChooser jFileChooser = BasicFileChooserUI.this.getFileChooser();
/*  508 */         FileSystemView fileSystemView = jFileChooser.getFileSystemView();
/*  509 */         JList<File> jList = (JList)param1ListSelectionEvent.getSource();
/*      */         
/*  511 */         int i = jFileChooser.getFileSelectionMode();
/*  512 */         boolean bool = (BasicFileChooserUI.this.usesSingleFilePane && i == 0) ? true : false;
/*      */ 
/*      */         
/*  515 */         if (jFileChooser.isMultiSelectionEnabled()) {
/*  516 */           File[] arrayOfFile = null;
/*  517 */           Object[] arrayOfObject = jList.getSelectedValues();
/*  518 */           if (arrayOfObject != null) {
/*  519 */             if (arrayOfObject.length == 1 && ((File)arrayOfObject[0])
/*  520 */               .isDirectory() && jFileChooser
/*  521 */               .isTraversable((File)arrayOfObject[0]) && (bool || 
/*  522 */               !fileSystemView.isFileSystem((File)arrayOfObject[0]))) {
/*  523 */               BasicFileChooserUI.this.setDirectorySelected(true);
/*  524 */               BasicFileChooserUI.this.setDirectory((File)arrayOfObject[0]);
/*      */             } else {
/*  526 */               ArrayList<File> arrayList = new ArrayList(arrayOfObject.length);
/*  527 */               for (Object object : arrayOfObject) {
/*  528 */                 File file = (File)object;
/*  529 */                 boolean bool1 = file.isDirectory();
/*  530 */                 if ((jFileChooser.isFileSelectionEnabled() && !bool1) || (jFileChooser
/*  531 */                   .isDirectorySelectionEnabled() && fileSystemView
/*  532 */                   .isFileSystem(file) && bool1))
/*      */                 {
/*  534 */                   arrayList.add(file);
/*      */                 }
/*      */               } 
/*  537 */               if (arrayList.size() > 0) {
/*  538 */                 arrayOfFile = arrayList.<File>toArray(new File[arrayList.size()]);
/*      */               }
/*  540 */               BasicFileChooserUI.this.setDirectorySelected(false);
/*      */             } 
/*      */           }
/*  543 */           jFileChooser.setSelectedFiles(arrayOfFile);
/*      */         } else {
/*  545 */           File file = jList.getSelectedValue();
/*  546 */           if (file != null && file
/*  547 */             .isDirectory() && jFileChooser
/*  548 */             .isTraversable(file) && (bool || 
/*  549 */             !fileSystemView.isFileSystem(file))) {
/*      */             
/*  551 */             BasicFileChooserUI.this.setDirectorySelected(true);
/*  552 */             BasicFileChooserUI.this.setDirectory(file);
/*  553 */             if (BasicFileChooserUI.this.usesSingleFilePane) {
/*  554 */               jFileChooser.setSelectedFile((File)null);
/*      */             }
/*      */           } else {
/*  557 */             BasicFileChooserUI.this.setDirectorySelected(false);
/*  558 */             if (file != null) {
/*  559 */               jFileChooser.setSelectedFile(file);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected class DoubleClickListener
/*      */     extends MouseAdapter
/*      */   {
/*      */     BasicFileChooserUI.Handler handler;
/*      */     
/*      */     public DoubleClickListener(JList param1JList) {
/*  574 */       this.handler = new BasicFileChooserUI.Handler(param1JList);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/*  585 */       this.handler.mouseEntered(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/*  589 */       this.handler.mouseClicked(param1MouseEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class SelectionListener
/*      */     implements ListSelectionListener
/*      */   {
/*      */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {
/*  599 */       BasicFileChooserUI.this.getHandler().valueChanged(param1ListSelectionEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isDirectorySelected() {
/*  610 */     return this.directorySelected;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDirectorySelected(boolean paramBoolean) {
/*  621 */     this.directorySelected = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected File getDirectory() {
/*  632 */     return this.directory;
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
/*      */   protected void setDirectory(File paramFile) {
/*  644 */     this.directory = paramFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getMnemonic(String paramString, Locale paramLocale) {
/*  651 */     return SwingUtilities2.getUIDefaultsInt(paramString, paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FileFilter getAcceptAllFileFilter(JFileChooser paramJFileChooser) {
/*  662 */     return this.acceptAllFileFilter;
/*      */   }
/*      */ 
/*      */   
/*      */   public FileView getFileView(JFileChooser paramJFileChooser) {
/*  667 */     return this.fileView;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDialogTitle(JFileChooser paramJFileChooser) {
/*  675 */     String str = paramJFileChooser.getDialogTitle();
/*  676 */     if (str != null)
/*  677 */       return str; 
/*  678 */     if (paramJFileChooser.getDialogType() == 0)
/*  679 */       return this.openDialogTitleText; 
/*  680 */     if (paramJFileChooser.getDialogType() == 1) {
/*  681 */       return this.saveDialogTitleText;
/*      */     }
/*  683 */     return getApproveButtonText(paramJFileChooser);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getApproveButtonMnemonic(JFileChooser paramJFileChooser) {
/*  689 */     int i = paramJFileChooser.getApproveButtonMnemonic();
/*  690 */     if (i > 0)
/*  691 */       return i; 
/*  692 */     if (paramJFileChooser.getDialogType() == 0)
/*  693 */       return this.openButtonMnemonic; 
/*  694 */     if (paramJFileChooser.getDialogType() == 1) {
/*  695 */       return this.saveButtonMnemonic;
/*      */     }
/*  697 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getApproveButtonText(JFileChooser paramJFileChooser) {
/*  702 */     String str = paramJFileChooser.getApproveButtonText();
/*  703 */     if (str != null)
/*  704 */       return str; 
/*  705 */     if (paramJFileChooser.getDialogType() == 0)
/*  706 */       return this.openButtonText; 
/*  707 */     if (paramJFileChooser.getDialogType() == 1) {
/*  708 */       return this.saveButtonText;
/*      */     }
/*  710 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Action getNewFolderAction() {
/*  720 */     if (this.newFolderAction == null) {
/*  721 */       this.newFolderAction = new NewFolderAction();
/*      */ 
/*      */       
/*  724 */       if (this.readOnly) {
/*  725 */         this.newFolderAction.setEnabled(false);
/*      */       }
/*      */     } 
/*  728 */     return this.newFolderAction;
/*      */   }
/*      */   
/*      */   public Action getGoHomeAction() {
/*  732 */     return this.goHomeAction;
/*      */   }
/*      */   
/*      */   public Action getChangeToParentDirectoryAction() {
/*  736 */     return this.changeToParentDirectoryAction;
/*      */   }
/*      */   
/*      */   public Action getApproveSelectionAction() {
/*  740 */     return this.approveSelectionAction;
/*      */   }
/*      */   
/*      */   public Action getCancelSelectionAction() {
/*  744 */     return this.cancelSelectionAction;
/*      */   }
/*      */   
/*      */   public Action getUpdateAction() {
/*  748 */     return this.updateAction;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class NewFolderAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected NewFolderAction() {
/*  757 */       super("New Folder");
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  760 */       if (BasicFileChooserUI.this.readOnly) {
/*      */         return;
/*      */       }
/*  763 */       JFileChooser jFileChooser = BasicFileChooserUI.this.getFileChooser();
/*  764 */       File file = jFileChooser.getCurrentDirectory();
/*      */       
/*  766 */       if (!file.exists()) {
/*  767 */         JOptionPane.showMessageDialog(jFileChooser, BasicFileChooserUI.this
/*      */             
/*  769 */             .newFolderParentDoesntExistText, BasicFileChooserUI.this
/*  770 */             .newFolderParentDoesntExistTitleText, 2);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*      */       try {
/*  776 */         File file1 = jFileChooser.getFileSystemView().createNewFolder(file);
/*  777 */         if (jFileChooser.isMultiSelectionEnabled()) {
/*  778 */           jFileChooser.setSelectedFiles(new File[] { file1 });
/*      */         } else {
/*  780 */           jFileChooser.setSelectedFile(file1);
/*      */         } 
/*  782 */       } catch (IOException iOException) {
/*  783 */         JOptionPane.showMessageDialog(jFileChooser, BasicFileChooserUI.this
/*      */             
/*  785 */             .newFolderErrorText + BasicFileChooserUI.this.newFolderErrorSeparator + iOException, BasicFileChooserUI.this
/*  786 */             .newFolderErrorText, 0);
/*      */         
/*      */         return;
/*      */       } 
/*  790 */       jFileChooser.rescanCurrentDirectory();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected class GoHomeAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected GoHomeAction() {
/*  799 */       super("Go Home");
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  802 */       JFileChooser jFileChooser = BasicFileChooserUI.this.getFileChooser();
/*  803 */       BasicFileChooserUI.this.changeDirectory(jFileChooser.getFileSystemView().getHomeDirectory());
/*      */     }
/*      */   }
/*      */   
/*      */   protected class ChangeToParentDirectoryAction extends AbstractAction {
/*      */     protected ChangeToParentDirectoryAction() {
/*  809 */       super("Go Up");
/*  810 */       putValue("ActionCommandKey", "Go Up");
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  813 */       BasicFileChooserUI.this.getFileChooser().changeToParentDirectory();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected class ApproveSelectionAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected ApproveSelectionAction() {
/*  822 */       super("approveSelection");
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  825 */       if (BasicFileChooserUI.this.isDirectorySelected()) {
/*  826 */         File file = BasicFileChooserUI.this.getDirectory();
/*  827 */         if (file != null) {
/*      */           
/*      */           try {
/*  830 */             file = ShellFolder.getNormalizedFile(file);
/*  831 */           } catch (IOException iOException) {}
/*      */ 
/*      */           
/*  834 */           BasicFileChooserUI.this.changeDirectory(file);
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  839 */       JFileChooser jFileChooser = BasicFileChooserUI.this.getFileChooser();
/*      */       
/*  841 */       String str = BasicFileChooserUI.this.getFileName();
/*  842 */       FileSystemView fileSystemView = jFileChooser.getFileSystemView();
/*  843 */       File file1 = jFileChooser.getCurrentDirectory();
/*      */       
/*  845 */       if (str != null) {
/*      */         
/*  847 */         int i = str.length() - 1;
/*      */         
/*  849 */         while (i >= 0 && str.charAt(i) <= ' ') {
/*  850 */           i--;
/*      */         }
/*      */         
/*  853 */         str = str.substring(0, i + 1);
/*      */       } 
/*      */       
/*  856 */       if (str == null || str.length() == 0) {
/*      */         
/*  858 */         BasicFileChooserUI.this.resetGlobFilter();
/*      */         
/*      */         return;
/*      */       } 
/*  862 */       File file2 = null;
/*  863 */       File[] arrayOfFile = null;
/*      */ 
/*      */       
/*  866 */       if (File.separatorChar == '/') {
/*  867 */         if (str.startsWith("~/")) {
/*  868 */           str = System.getProperty("user.home") + str.substring(1);
/*  869 */         } else if (str.equals("~")) {
/*  870 */           str = System.getProperty("user.home");
/*      */         } 
/*      */       }
/*      */       
/*  874 */       if (jFileChooser.isMultiSelectionEnabled() && str.length() > 1 && str
/*  875 */         .charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
/*  876 */         ArrayList<File> arrayList = new ArrayList();
/*      */         
/*  878 */         String[] arrayOfString = str.substring(1, str.length() - 1).split("\" \"");
/*      */         
/*  880 */         Arrays.sort((Object[])arrayOfString);
/*      */         
/*  882 */         File[] arrayOfFile1 = null;
/*  883 */         int i = 0;
/*      */         
/*  885 */         for (String str1 : arrayOfString) {
/*  886 */           File file = fileSystemView.createFileObject(str1);
/*  887 */           if (!file.isAbsolute()) {
/*  888 */             if (arrayOfFile1 == null) {
/*  889 */               arrayOfFile1 = fileSystemView.getFiles(file1, false);
/*  890 */               Arrays.sort((Object[])arrayOfFile1);
/*      */             } 
/*  892 */             for (byte b = 0; b < arrayOfFile1.length; b++) {
/*  893 */               int j = (i + b) % arrayOfFile1.length;
/*  894 */               if (arrayOfFile1[j].getName().equals(str1)) {
/*  895 */                 file = arrayOfFile1[j];
/*  896 */                 i = j + 1;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*  901 */           arrayList.add(file);
/*      */         } 
/*      */         
/*  904 */         if (!arrayList.isEmpty()) {
/*  905 */           arrayOfFile = arrayList.<File>toArray(new File[arrayList.size()]);
/*      */         }
/*  907 */         BasicFileChooserUI.this.resetGlobFilter();
/*      */       } else {
/*  909 */         file2 = fileSystemView.createFileObject(str);
/*  910 */         if (!file2.isAbsolute()) {
/*  911 */           file2 = fileSystemView.getChild(file1, str);
/*      */         }
/*      */         
/*  914 */         FileFilter fileFilter = jFileChooser.getFileFilter();
/*  915 */         if (!file2.exists() && BasicFileChooserUI.isGlobPattern(str)) {
/*  916 */           BasicFileChooserUI.this.changeDirectory(file2.getParentFile());
/*  917 */           if (BasicFileChooserUI.this.globFilter == null) {
/*  918 */             BasicFileChooserUI.this.globFilter = new BasicFileChooserUI.GlobFilter();
/*      */           }
/*      */           try {
/*  921 */             BasicFileChooserUI.this.globFilter.setPattern(file2.getName());
/*  922 */             if (!(fileFilter instanceof BasicFileChooserUI.GlobFilter)) {
/*  923 */               BasicFileChooserUI.this.actualFileFilter = fileFilter;
/*      */             }
/*  925 */             jFileChooser.setFileFilter((FileFilter)null);
/*  926 */             jFileChooser.setFileFilter(BasicFileChooserUI.this.globFilter);
/*      */             return;
/*  928 */           } catch (PatternSyntaxException patternSyntaxException) {}
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  933 */         BasicFileChooserUI.this.resetGlobFilter();
/*      */ 
/*      */         
/*  936 */         boolean bool1 = (file2 != null && file2.isDirectory()) ? true : false;
/*  937 */         boolean bool2 = (file2 != null && jFileChooser.isTraversable(file2)) ? true : false;
/*  938 */         boolean bool3 = jFileChooser.isDirectorySelectionEnabled();
/*  939 */         boolean bool4 = jFileChooser.isFileSelectionEnabled();
/*      */         
/*  941 */         boolean bool5 = (param1ActionEvent != null && (param1ActionEvent.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0) ? true : false;
/*      */         
/*  943 */         if (bool1 && bool2 && (bool5 || !bool3)) {
/*  944 */           BasicFileChooserUI.this.changeDirectory(file2); return;
/*      */         } 
/*  946 */         if ((bool1 || !bool4) && (!bool1 || !bool3) && (!bool3 || file2
/*      */           
/*  948 */           .exists())) {
/*  949 */           file2 = null;
/*      */         }
/*      */       } 
/*      */       
/*  953 */       if (arrayOfFile != null || file2 != null) {
/*  954 */         if (arrayOfFile != null || jFileChooser.isMultiSelectionEnabled()) {
/*  955 */           if (arrayOfFile == null) {
/*  956 */             arrayOfFile = new File[] { file2 };
/*      */           }
/*  958 */           jFileChooser.setSelectedFiles(arrayOfFile);
/*      */ 
/*      */ 
/*      */           
/*  962 */           jFileChooser.setSelectedFiles(arrayOfFile);
/*      */         } else {
/*  964 */           jFileChooser.setSelectedFile(file2);
/*      */         } 
/*  966 */         jFileChooser.approveSelection();
/*      */       } else {
/*  968 */         if (jFileChooser.isMultiSelectionEnabled()) {
/*  969 */           jFileChooser.setSelectedFiles((File[])null);
/*      */         } else {
/*  971 */           jFileChooser.setSelectedFile((File)null);
/*      */         } 
/*  973 */         jFileChooser.cancelSelection();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void resetGlobFilter() {
/*  980 */     if (this.actualFileFilter != null) {
/*  981 */       JFileChooser jFileChooser = getFileChooser();
/*  982 */       FileFilter fileFilter = jFileChooser.getFileFilter();
/*  983 */       if (fileFilter != null && fileFilter.equals(this.globFilter)) {
/*  984 */         jFileChooser.setFileFilter(this.actualFileFilter);
/*  985 */         jFileChooser.removeChoosableFileFilter(this.globFilter);
/*      */       } 
/*  987 */       this.actualFileFilter = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean isGlobPattern(String paramString) {
/*  992 */     return ((File.separatorChar == '\\' && (paramString.indexOf('*') >= 0 || paramString
/*  993 */       .indexOf('?') >= 0)) || (File.separatorChar == '/' && (paramString
/*  994 */       .indexOf('*') >= 0 || paramString
/*  995 */       .indexOf('?') >= 0 || paramString
/*  996 */       .indexOf('[') >= 0)));
/*      */   }
/*      */ 
/*      */   
/*      */   class GlobFilter
/*      */     extends FileFilter
/*      */   {
/*      */     Pattern pattern;
/*      */     
/*      */     String globPattern;
/*      */     
/*      */     public void setPattern(String param1String) {
/* 1008 */       char[] arrayOfChar1 = param1String.toCharArray();
/* 1009 */       char[] arrayOfChar2 = new char[arrayOfChar1.length * 2];
/* 1010 */       boolean bool1 = (File.separatorChar == '\\') ? true : false;
/* 1011 */       boolean bool2 = false;
/* 1012 */       byte b = 0;
/*      */       
/* 1014 */       this.globPattern = param1String;
/*      */       
/* 1016 */       if (bool1) {
/*      */         
/* 1018 */         int i = arrayOfChar1.length;
/* 1019 */         if (param1String.endsWith("*.*")) {
/* 1020 */           i -= 2;
/*      */         }
/* 1022 */         for (byte b1 = 0; b1 < i; b1++) {
/* 1023 */           switch (arrayOfChar1[b1]) {
/*      */             case '*':
/* 1025 */               arrayOfChar2[b++] = '.';
/* 1026 */               arrayOfChar2[b++] = '*';
/*      */               break;
/*      */             
/*      */             case '?':
/* 1030 */               arrayOfChar2[b++] = '.';
/*      */               break;
/*      */             
/*      */             case '\\':
/* 1034 */               arrayOfChar2[b++] = '\\';
/* 1035 */               arrayOfChar2[b++] = '\\';
/*      */               break;
/*      */             
/*      */             default:
/* 1039 */               if ("+()^$.{}[]".indexOf(arrayOfChar1[b1]) >= 0) {
/* 1040 */                 arrayOfChar2[b++] = '\\';
/*      */               }
/* 1042 */               arrayOfChar2[b++] = arrayOfChar1[b1];
/*      */               break;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1047 */         for (byte b1 = 0; b1 < arrayOfChar1.length; b1++) {
/* 1048 */           switch (arrayOfChar1[b1]) {
/*      */             case '*':
/* 1050 */               if (!bool2) {
/* 1051 */                 arrayOfChar2[b++] = '.';
/*      */               }
/* 1053 */               arrayOfChar2[b++] = '*';
/*      */               break;
/*      */             
/*      */             case '?':
/* 1057 */               arrayOfChar2[b++] = bool2 ? '?' : '.';
/*      */               break;
/*      */             
/*      */             case '[':
/* 1061 */               bool2 = true;
/* 1062 */               arrayOfChar2[b++] = arrayOfChar1[b1];
/*      */               
/* 1064 */               if (b1 < arrayOfChar1.length - 1) {
/* 1065 */                 switch (arrayOfChar1[b1 + 1]) {
/*      */                   case '!':
/*      */                   case '^':
/* 1068 */                     arrayOfChar2[b++] = '^';
/* 1069 */                     b1++;
/*      */                     break;
/*      */                   
/*      */                   case ']':
/* 1073 */                     arrayOfChar2[b++] = arrayOfChar1[++b1];
/*      */                     break;
/*      */                 } 
/*      */               
/*      */               }
/*      */               break;
/*      */             case ']':
/* 1080 */               arrayOfChar2[b++] = arrayOfChar1[b1];
/* 1081 */               bool2 = false;
/*      */               break;
/*      */             
/*      */             case '\\':
/* 1085 */               if (b1 == 0 && arrayOfChar1.length > 1 && arrayOfChar1[1] == '~') {
/* 1086 */                 arrayOfChar2[b++] = arrayOfChar1[++b1]; break;
/*      */               } 
/* 1088 */               arrayOfChar2[b++] = '\\';
/* 1089 */               if (b1 < arrayOfChar1.length - 1 && "*?[]".indexOf(arrayOfChar1[b1 + 1]) >= 0) {
/* 1090 */                 arrayOfChar2[b++] = arrayOfChar1[++b1]; break;
/*      */               } 
/* 1092 */               arrayOfChar2[b++] = '\\';
/*      */               break;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             default:
/* 1099 */               if (!Character.isLetterOrDigit(arrayOfChar1[b1])) {
/* 1100 */                 arrayOfChar2[b++] = '\\';
/*      */               }
/* 1102 */               arrayOfChar2[b++] = arrayOfChar1[b1];
/*      */               break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1107 */       this.pattern = Pattern.compile(new String(arrayOfChar2, 0, b), 2);
/*      */     }
/*      */     
/*      */     public boolean accept(File param1File) {
/* 1111 */       if (param1File == null) {
/* 1112 */         return false;
/*      */       }
/* 1114 */       if (param1File.isDirectory()) {
/* 1115 */         return true;
/*      */       }
/* 1117 */       return this.pattern.matcher(param1File.getName()).matches();
/*      */     }
/*      */     
/*      */     public String getDescription() {
/* 1121 */       return this.globPattern;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected class CancelSelectionAction
/*      */     extends AbstractAction
/*      */   {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1130 */       BasicFileChooserUI.this.getFileChooser().cancelSelection();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected class UpdateAction
/*      */     extends AbstractAction
/*      */   {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1139 */       JFileChooser jFileChooser = BasicFileChooserUI.this.getFileChooser();
/* 1140 */       jFileChooser.setCurrentDirectory(jFileChooser.getFileSystemView().createFileObject(BasicFileChooserUI.this.getDirectoryName()));
/* 1141 */       jFileChooser.rescanCurrentDirectory();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void changeDirectory(File paramFile) {
/* 1147 */     JFileChooser jFileChooser = getFileChooser();
/*      */     
/* 1149 */     if (paramFile != null && FilePane.usesShellFolder(jFileChooser)) {
/*      */       try {
/* 1151 */         ShellFolder shellFolder = ShellFolder.getShellFolder(paramFile);
/*      */         
/* 1153 */         if (shellFolder.isLink()) {
/* 1154 */           ShellFolder shellFolder1 = shellFolder.getLinkLocation();
/*      */ 
/*      */           
/* 1157 */           if (shellFolder1 != null) {
/* 1158 */             if (jFileChooser.isTraversable(shellFolder1)) {
/* 1159 */               paramFile = shellFolder1;
/*      */             } else {
/*      */               return;
/*      */             } 
/*      */           } else {
/* 1164 */             paramFile = shellFolder;
/*      */           } 
/*      */         } 
/* 1167 */       } catch (FileNotFoundException fileNotFoundException) {
/*      */         return;
/*      */       } 
/*      */     }
/* 1171 */     jFileChooser.setCurrentDirectory(paramFile);
/* 1172 */     if (jFileChooser.getFileSelectionMode() == 2 && jFileChooser
/* 1173 */       .getFileSystemView().isFileSystem(paramFile))
/*      */     {
/* 1175 */       setFileName(paramFile.getAbsolutePath());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AcceptAllFileFilter
/*      */     extends FileFilter
/*      */   {
/*      */     public boolean accept(File param1File) {
/* 1189 */       return true;
/*      */     }
/*      */     
/*      */     public String getDescription() {
/* 1193 */       return UIManager.getString("FileChooser.acceptAllFileFilterText");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class BasicFileView
/*      */     extends FileView
/*      */   {
/* 1204 */     protected Hashtable<File, Icon> iconCache = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clearIconCache() {
/* 1210 */       this.iconCache = new Hashtable<>();
/*      */     }
/*      */ 
/*      */     
/*      */     public String getName(File param1File) {
/* 1215 */       String str = null;
/* 1216 */       if (param1File != null) {
/* 1217 */         str = BasicFileChooserUI.this.getFileChooser().getFileSystemView().getSystemDisplayName(param1File);
/*      */       }
/* 1219 */       return str;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getDescription(File param1File) {
/* 1224 */       return param1File.getName();
/*      */     }
/*      */     
/*      */     public String getTypeDescription(File param1File) {
/* 1228 */       String str = BasicFileChooserUI.this.getFileChooser().getFileSystemView().getSystemTypeDescription(param1File);
/* 1229 */       if (str == null) {
/* 1230 */         if (param1File.isDirectory()) {
/* 1231 */           str = BasicFileChooserUI.this.directoryDescriptionText;
/*      */         } else {
/* 1233 */           str = BasicFileChooserUI.this.fileDescriptionText;
/*      */         } 
/*      */       }
/* 1236 */       return str;
/*      */     }
/*      */     
/*      */     public Icon getCachedIcon(File param1File) {
/* 1240 */       return this.iconCache.get(param1File);
/*      */     }
/*      */     
/*      */     public void cacheIcon(File param1File, Icon param1Icon) {
/* 1244 */       if (param1File == null || param1Icon == null) {
/*      */         return;
/*      */       }
/* 1247 */       this.iconCache.put(param1File, param1Icon);
/*      */     }
/*      */     
/*      */     public Icon getIcon(File param1File) {
/* 1251 */       Icon icon = getCachedIcon(param1File);
/* 1252 */       if (icon != null) {
/* 1253 */         return icon;
/*      */       }
/* 1255 */       icon = BasicFileChooserUI.this.fileIcon;
/* 1256 */       if (param1File != null) {
/* 1257 */         FileSystemView fileSystemView = BasicFileChooserUI.this.getFileChooser().getFileSystemView();
/*      */         
/* 1259 */         if (fileSystemView.isFloppyDrive(param1File)) {
/* 1260 */           icon = BasicFileChooserUI.this.floppyDriveIcon;
/* 1261 */         } else if (fileSystemView.isDrive(param1File)) {
/* 1262 */           icon = BasicFileChooserUI.this.hardDriveIcon;
/* 1263 */         } else if (fileSystemView.isComputerNode(param1File)) {
/* 1264 */           icon = BasicFileChooserUI.this.computerIcon;
/* 1265 */         } else if (param1File.isDirectory()) {
/* 1266 */           icon = BasicFileChooserUI.this.directoryIcon;
/*      */         } 
/*      */       } 
/* 1269 */       cacheIcon(param1File, icon);
/* 1270 */       return icon;
/*      */     }
/*      */     
/*      */     public Boolean isHidden(File param1File) {
/* 1274 */       String str = param1File.getName();
/* 1275 */       if (str != null && str.charAt(0) == '.') {
/* 1276 */         return Boolean.TRUE;
/*      */       }
/* 1278 */       return Boolean.FALSE;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 1283 */   private static final TransferHandler defaultTransferHandler = new FileTransferHandler();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BasicFileChooserUI(JFileChooser paramJFileChooser) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class FileTransferHandler
/*      */     extends TransferHandler
/*      */     implements UIResource
/*      */   {
/*      */     protected Transferable createTransferable(JComponent param1JComponent) {
/* 1301 */       Object[] arrayOfObject = null;
/* 1302 */       if (param1JComponent instanceof JList) {
/* 1303 */         arrayOfObject = ((JList)param1JComponent).getSelectedValues();
/* 1304 */       } else if (param1JComponent instanceof JTable) {
/* 1305 */         JTable jTable = (JTable)param1JComponent;
/* 1306 */         int[] arrayOfInt = jTable.getSelectedRows();
/* 1307 */         if (arrayOfInt != null) {
/* 1308 */           arrayOfObject = new Object[arrayOfInt.length];
/* 1309 */           for (byte b = 0; b < arrayOfInt.length; b++) {
/* 1310 */             arrayOfObject[b] = jTable.getValueAt(arrayOfInt[b], 0);
/*      */           }
/*      */         } 
/*      */       } 
/* 1314 */       if (arrayOfObject == null || arrayOfObject.length == 0) {
/* 1315 */         return null;
/*      */       }
/*      */       
/* 1318 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 1319 */       StringBuffer stringBuffer2 = new StringBuffer();
/*      */       
/* 1321 */       stringBuffer2.append("<html>\n<body>\n<ul>\n");
/*      */       
/* 1323 */       for (Object object : arrayOfObject) {
/* 1324 */         String str = (object == null) ? "" : object.toString();
/* 1325 */         stringBuffer1.append(str + "\n");
/* 1326 */         stringBuffer2.append("  <li>" + str + "\n");
/*      */       } 
/*      */ 
/*      */       
/* 1330 */       stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
/* 1331 */       stringBuffer2.append("</ul>\n</body>\n</html>");
/*      */       
/* 1333 */       return new FileTransferable(stringBuffer1.toString(), stringBuffer2.toString(), arrayOfObject);
/*      */     }
/*      */     
/*      */     public int getSourceActions(JComponent param1JComponent) {
/* 1337 */       return 1;
/*      */     }
/*      */     
/*      */     static class FileTransferable
/*      */       extends BasicTransferable {
/*      */       Object[] fileData;
/*      */       
/*      */       FileTransferable(String param2String1, String param2String2, Object[] param2ArrayOfObject) {
/* 1345 */         super(param2String1, param2String2);
/* 1346 */         this.fileData = param2ArrayOfObject;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected DataFlavor[] getRicherFlavors() {
/* 1353 */         DataFlavor[] arrayOfDataFlavor = new DataFlavor[1];
/* 1354 */         arrayOfDataFlavor[0] = DataFlavor.javaFileListFlavor;
/* 1355 */         return arrayOfDataFlavor;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected Object getRicherData(DataFlavor param2DataFlavor) {
/* 1362 */         if (DataFlavor.javaFileListFlavor.equals(param2DataFlavor)) {
/* 1363 */           ArrayList<Object> arrayList = new ArrayList();
/* 1364 */           for (Object object : this.fileData) {
/* 1365 */             arrayList.add(object);
/*      */           }
/* 1367 */           return arrayList;
/*      */         } 
/* 1369 */         return null;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicFileChooserUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */