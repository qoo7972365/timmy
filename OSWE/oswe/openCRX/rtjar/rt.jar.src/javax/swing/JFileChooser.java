/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.Frame;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.HierarchyEvent;
/*      */ import java.awt.event.HierarchyListener;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.swing.filechooser.FileFilter;
/*      */ import javax.swing.filechooser.FileSystemView;
/*      */ import javax.swing.filechooser.FileView;
/*      */ import javax.swing.plaf.FileChooserUI;
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
/*      */ public class JFileChooser
/*      */   extends JComponent
/*      */   implements Accessible
/*      */ {
/*      */   private static final String uiClassID = "FileChooserUI";
/*      */   public static final int OPEN_DIALOG = 0;
/*      */   public static final int SAVE_DIALOG = 1;
/*      */   public static final int CUSTOM_DIALOG = 2;
/*      */   public static final int CANCEL_OPTION = 1;
/*      */   public static final int APPROVE_OPTION = 0;
/*      */   public static final int ERROR_OPTION = -1;
/*      */   public static final int FILES_ONLY = 0;
/*      */   public static final int DIRECTORIES_ONLY = 1;
/*      */   public static final int FILES_AND_DIRECTORIES = 2;
/*      */   public static final String CANCEL_SELECTION = "CancelSelection";
/*      */   public static final String APPROVE_SELECTION = "ApproveSelection";
/*      */   public static final String APPROVE_BUTTON_TEXT_CHANGED_PROPERTY = "ApproveButtonTextChangedProperty";
/*      */   public static final String APPROVE_BUTTON_TOOL_TIP_TEXT_CHANGED_PROPERTY = "ApproveButtonToolTipTextChangedProperty";
/*      */   public static final String APPROVE_BUTTON_MNEMONIC_CHANGED_PROPERTY = "ApproveButtonMnemonicChangedProperty";
/*      */   public static final String CONTROL_BUTTONS_ARE_SHOWN_CHANGED_PROPERTY = "ControlButtonsAreShownChangedProperty";
/*      */   public static final String DIRECTORY_CHANGED_PROPERTY = "directoryChanged";
/*      */   public static final String SELECTED_FILE_CHANGED_PROPERTY = "SelectedFileChangedProperty";
/*      */   public static final String SELECTED_FILES_CHANGED_PROPERTY = "SelectedFilesChangedProperty";
/*      */   public static final String MULTI_SELECTION_ENABLED_CHANGED_PROPERTY = "MultiSelectionEnabledChangedProperty";
/*      */   public static final String FILE_SYSTEM_VIEW_CHANGED_PROPERTY = "FileSystemViewChanged";
/*      */   public static final String FILE_VIEW_CHANGED_PROPERTY = "fileViewChanged";
/*      */   public static final String FILE_HIDING_CHANGED_PROPERTY = "FileHidingChanged";
/*      */   public static final String FILE_FILTER_CHANGED_PROPERTY = "fileFilterChanged";
/*      */   public static final String FILE_SELECTION_MODE_CHANGED_PROPERTY = "fileSelectionChanged";
/*      */   public static final String ACCESSORY_CHANGED_PROPERTY = "AccessoryChangedProperty";
/*      */   public static final String ACCEPT_ALL_FILE_FILTER_USED_CHANGED_PROPERTY = "acceptAllFileFilterUsedChanged";
/*      */   public static final String DIALOG_TITLE_CHANGED_PROPERTY = "DialogTitleChangedProperty";
/*      */   public static final String DIALOG_TYPE_CHANGED_PROPERTY = "DialogTypeChangedProperty";
/*      */   public static final String CHOOSABLE_FILE_FILTER_CHANGED_PROPERTY = "ChoosableFileFilterChangedProperty";
/*  246 */   private String dialogTitle = null;
/*  247 */   private String approveButtonText = null;
/*  248 */   private String approveButtonToolTipText = null;
/*  249 */   private int approveButtonMnemonic = 0;
/*      */   
/*  251 */   private Vector<FileFilter> filters = new Vector<>(5);
/*  252 */   private JDialog dialog = null;
/*  253 */   private int dialogType = 0;
/*  254 */   private int returnValue = -1;
/*  255 */   private JComponent accessory = null;
/*      */   
/*  257 */   private FileView fileView = null;
/*      */ 
/*      */   
/*      */   private boolean controlsShown = true;
/*      */ 
/*      */   
/*      */   private boolean useFileHiding = true;
/*      */   
/*      */   private static final String SHOW_HIDDEN_PROP = "awt.file.showHiddenFiles";
/*      */   
/*  267 */   private transient PropertyChangeListener showFilesListener = null;
/*      */   
/*  269 */   private int fileSelectionMode = 0;
/*      */   
/*      */   private boolean multiSelectionEnabled = false;
/*      */   
/*      */   private boolean useAcceptAllFileFilter = true;
/*      */   
/*      */   private boolean dragEnabled = false;
/*      */   
/*  277 */   private FileFilter fileFilter = null;
/*      */   
/*  279 */   private FileSystemView fileSystemView = null;
/*      */   
/*  281 */   private File currentDirectory = null;
/*  282 */   private File selectedFile = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private File[] selectedFiles;
/*      */ 
/*      */ 
/*      */   
/*      */   protected AccessibleContext accessibleContext;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFileChooser() {
/*  296 */     this((File)null, (FileSystemView)null);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public JFileChooser(String paramString) {
/*  311 */     this(paramString, (FileSystemView)null);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public JFileChooser(File paramFile) {
/*  326 */     this(paramFile, (FileSystemView)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFileChooser(FileSystemView paramFileSystemView) {
/*  334 */     this((File)null, paramFileSystemView);
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
/*      */   protected void setup(FileSystemView paramFileSystemView) {
/*  364 */     installShowFilesListener();
/*  365 */     installHierarchyListener();
/*      */     
/*  367 */     if (paramFileSystemView == null) {
/*  368 */       paramFileSystemView = FileSystemView.getFileSystemView();
/*      */     }
/*  370 */     setFileSystemView(paramFileSystemView);
/*  371 */     updateUI();
/*  372 */     if (isAcceptAllFileFilterUsed()) {
/*  373 */       setFileFilter(getAcceptAllFileFilter());
/*      */     }
/*  375 */     enableEvents(16L);
/*      */   }
/*      */   
/*      */   private void installHierarchyListener() {
/*  379 */     addHierarchyListener(new HierarchyListener()
/*      */         {
/*      */           public void hierarchyChanged(HierarchyEvent param1HierarchyEvent) {
/*  382 */             if ((param1HierarchyEvent.getChangeFlags() & 0x1L) == 1L) {
/*      */               
/*  384 */               JFileChooser jFileChooser = JFileChooser.this;
/*  385 */               JRootPane jRootPane = SwingUtilities.getRootPane(jFileChooser);
/*  386 */               if (jRootPane != null) {
/*  387 */                 jRootPane.setDefaultButton(jFileChooser.getUI().getDefaultButton(jFileChooser));
/*      */               }
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private void installShowFilesListener() {
/*  396 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  397 */     Object object = toolkit.getDesktopProperty("awt.file.showHiddenFiles");
/*  398 */     if (object instanceof Boolean) {
/*  399 */       this.useFileHiding = !((Boolean)object).booleanValue();
/*  400 */       this.showFilesListener = new WeakPCL(this);
/*  401 */       toolkit.addPropertyChangeListener("awt.file.showHiddenFiles", this.showFilesListener);
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
/*      */   public void setDragEnabled(boolean paramBoolean) {
/*  450 */     if (paramBoolean && GraphicsEnvironment.isHeadless()) {
/*  451 */       throw new HeadlessException();
/*      */     }
/*  453 */     this.dragEnabled = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDragEnabled() {
/*  464 */     return this.dragEnabled;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getSelectedFile() {
/*  481 */     return this.selectedFile;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectedFile(File paramFile) {
/*  498 */     File file = this.selectedFile;
/*  499 */     this.selectedFile = paramFile;
/*  500 */     if (this.selectedFile != null) {
/*  501 */       if (paramFile.isAbsolute() && !getFileSystemView().isParent(getCurrentDirectory(), this.selectedFile)) {
/*  502 */         setCurrentDirectory(this.selectedFile.getParentFile());
/*      */       }
/*  504 */       if (!isMultiSelectionEnabled() || this.selectedFiles == null || this.selectedFiles.length == 1) {
/*  505 */         ensureFileIsVisible(this.selectedFile);
/*      */       }
/*      */     } 
/*  508 */     firePropertyChange("SelectedFileChangedProperty", file, this.selectedFile);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File[] getSelectedFiles() {
/*  516 */     if (this.selectedFiles == null) {
/*  517 */       return new File[0];
/*      */     }
/*  519 */     return (File[])this.selectedFiles.clone();
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
/*      */   
/*      */   public void setSelectedFiles(File[] paramArrayOfFile) {
/*  532 */     File[] arrayOfFile = this.selectedFiles;
/*  533 */     if (paramArrayOfFile == null || paramArrayOfFile.length == 0) {
/*  534 */       paramArrayOfFile = null;
/*  535 */       this.selectedFiles = null;
/*  536 */       setSelectedFile((File)null);
/*      */     } else {
/*  538 */       this.selectedFiles = (File[])paramArrayOfFile.clone();
/*  539 */       setSelectedFile(this.selectedFiles[0]);
/*      */     } 
/*  541 */     firePropertyChange("SelectedFilesChangedProperty", arrayOfFile, paramArrayOfFile);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getCurrentDirectory() {
/*  551 */     return this.currentDirectory;
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
/*      */   public void setCurrentDirectory(File paramFile) {
/*  576 */     File file1 = this.currentDirectory;
/*      */     
/*  578 */     if (paramFile != null && !paramFile.exists()) {
/*  579 */       paramFile = this.currentDirectory;
/*      */     }
/*  581 */     if (paramFile == null) {
/*  582 */       paramFile = getFileSystemView().getDefaultDirectory();
/*      */     }
/*  584 */     if (this.currentDirectory != null)
/*      */     {
/*  586 */       if (this.currentDirectory.equals(paramFile)) {
/*      */         return;
/*      */       }
/*      */     }
/*      */     
/*  591 */     File file2 = null;
/*  592 */     while (!isTraversable(paramFile) && file2 != paramFile) {
/*  593 */       file2 = paramFile;
/*  594 */       paramFile = getFileSystemView().getParentDirectory(paramFile);
/*      */     } 
/*  596 */     this.currentDirectory = paramFile;
/*      */     
/*  598 */     firePropertyChange("directoryChanged", file1, this.currentDirectory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void changeToParentDirectory() {
/*  608 */     this.selectedFile = null;
/*  609 */     File file = getCurrentDirectory();
/*  610 */     setCurrentDirectory(getFileSystemView().getParentDirectory(file));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rescanCurrentDirectory() {
/*  617 */     getUI().rescanCurrentDirectory(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureFileIsVisible(File paramFile) {
/*  627 */     getUI().ensureFileIsVisible(this, paramFile);
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
/*      */   public int showOpenDialog(Component paramComponent) throws HeadlessException {
/*  655 */     setDialogType(0);
/*  656 */     return showDialog(paramComponent, (String)null);
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
/*      */   public int showSaveDialog(Component paramComponent) throws HeadlessException {
/*  680 */     setDialogType(1);
/*  681 */     return showDialog(paramComponent, (String)null);
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
/*      */ 
/*      */   
/*      */   public int showDialog(Component paramComponent, String paramString) throws HeadlessException {
/*  740 */     if (this.dialog != null)
/*      */     {
/*  742 */       return -1;
/*      */     }
/*      */     
/*  745 */     if (paramString != null) {
/*  746 */       setApproveButtonText(paramString);
/*  747 */       setDialogType(2);
/*      */     } 
/*  749 */     this.dialog = createDialog(paramComponent);
/*  750 */     this.dialog.addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent param1WindowEvent) {
/*  752 */             JFileChooser.this.returnValue = 1;
/*      */           }
/*      */         });
/*  755 */     this.returnValue = -1;
/*  756 */     rescanCurrentDirectory();
/*      */     
/*  758 */     this.dialog.show();
/*  759 */     firePropertyChange("JFileChooserDialogIsClosingProperty", this.dialog, (Object)null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  764 */     this.dialog.getContentPane().removeAll();
/*  765 */     this.dialog.dispose();
/*  766 */     this.dialog = null;
/*  767 */     return this.returnValue;
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
/*      */   protected JDialog createDialog(Component paramComponent) throws HeadlessException {
/*      */     JDialog jDialog;
/*  796 */     FileChooserUI fileChooserUI = getUI();
/*  797 */     String str = fileChooserUI.getDialogTitle(this);
/*  798 */     putClientProperty("AccessibleDescription", str);
/*      */ 
/*      */ 
/*      */     
/*  802 */     Window window = JOptionPane.getWindowForComponent(paramComponent);
/*  803 */     if (window instanceof Frame) {
/*  804 */       jDialog = new JDialog((Frame)window, str, true);
/*      */     } else {
/*  806 */       jDialog = new JDialog((Dialog)window, str, true);
/*      */     } 
/*  808 */     jDialog.setComponentOrientation(getComponentOrientation());
/*      */     
/*  810 */     Container container = jDialog.getContentPane();
/*  811 */     container.setLayout(new BorderLayout());
/*  812 */     container.add(this, "Center");
/*      */     
/*  814 */     if (JDialog.isDefaultLookAndFeelDecorated()) {
/*      */       
/*  816 */       boolean bool = UIManager.getLookAndFeel().getSupportsWindowDecorations();
/*  817 */       if (bool) {
/*  818 */         jDialog.getRootPane().setWindowDecorationStyle(6);
/*      */       }
/*      */     } 
/*  821 */     jDialog.pack();
/*  822 */     jDialog.setLocationRelativeTo(paramComponent);
/*      */     
/*  824 */     return jDialog;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getControlButtonsAreShown() {
/*  842 */     return this.controlsShown;
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
/*      */   public void setControlButtonsAreShown(boolean paramBoolean) {
/*  871 */     if (this.controlsShown == paramBoolean) {
/*      */       return;
/*      */     }
/*  874 */     boolean bool = this.controlsShown;
/*  875 */     this.controlsShown = paramBoolean;
/*  876 */     firePropertyChange("ControlButtonsAreShownChangedProperty", bool, this.controlsShown);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDialogType() {
/*  893 */     return this.dialogType;
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
/*      */   public void setDialogType(int paramInt) {
/*  933 */     if (this.dialogType == paramInt) {
/*      */       return;
/*      */     }
/*  936 */     if (paramInt != 0 && paramInt != 1 && paramInt != 2) {
/*  937 */       throw new IllegalArgumentException("Incorrect Dialog Type: " + paramInt);
/*      */     }
/*  939 */     int i = this.dialogType;
/*  940 */     this.dialogType = paramInt;
/*  941 */     if (paramInt == 0 || paramInt == 1) {
/*  942 */       setApproveButtonText((String)null);
/*      */     }
/*  944 */     firePropertyChange("DialogTypeChangedProperty", i, paramInt);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDialogTitle(String paramString) {
/*  962 */     String str = this.dialogTitle;
/*  963 */     this.dialogTitle = paramString;
/*  964 */     if (this.dialog != null) {
/*  965 */       this.dialog.setTitle(paramString);
/*      */     }
/*  967 */     firePropertyChange("DialogTitleChangedProperty", str, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDialogTitle() {
/*  976 */     return this.dialogTitle;
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
/*      */   public void setApproveButtonToolTipText(String paramString) {
/* 1000 */     if (this.approveButtonToolTipText == paramString) {
/*      */       return;
/*      */     }
/* 1003 */     String str = this.approveButtonToolTipText;
/* 1004 */     this.approveButtonToolTipText = paramString;
/* 1005 */     firePropertyChange("ApproveButtonToolTipTextChangedProperty", str, this.approveButtonToolTipText);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public String getApproveButtonToolTipText() {
/* 1020 */     return this.approveButtonToolTipText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getApproveButtonMnemonic() {
/* 1030 */     return this.approveButtonMnemonic;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setApproveButtonMnemonic(int paramInt) {
/* 1046 */     if (this.approveButtonMnemonic == paramInt) {
/*      */       return;
/*      */     }
/* 1049 */     int i = this.approveButtonMnemonic;
/* 1050 */     this.approveButtonMnemonic = paramInt;
/* 1051 */     firePropertyChange("ApproveButtonMnemonicChangedProperty", i, this.approveButtonMnemonic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setApproveButtonMnemonic(char paramChar) {
/* 1061 */     char c = paramChar;
/* 1062 */     if (c >= 'a' && c <= 'z') {
/* 1063 */       c -= ' ';
/*      */     }
/* 1065 */     setApproveButtonMnemonic(c);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setApproveButtonText(String paramString) {
/* 1086 */     if (this.approveButtonText == paramString) {
/*      */       return;
/*      */     }
/* 1089 */     String str = this.approveButtonText;
/* 1090 */     this.approveButtonText = paramString;
/* 1091 */     firePropertyChange("ApproveButtonTextChangedProperty", str, paramString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getApproveButtonText() {
/* 1108 */     return this.approveButtonText;
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
/*      */ 
/*      */   
/*      */   public FileFilter[] getChoosableFileFilters() {
/* 1122 */     FileFilter[] arrayOfFileFilter = new FileFilter[this.filters.size()];
/* 1123 */     this.filters.copyInto((Object[])arrayOfFileFilter);
/* 1124 */     return arrayOfFileFilter;
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
/*      */   public void addChoosableFileFilter(FileFilter paramFileFilter) {
/* 1146 */     if (paramFileFilter != null && !this.filters.contains(paramFileFilter)) {
/* 1147 */       FileFilter[] arrayOfFileFilter = getChoosableFileFilters();
/* 1148 */       this.filters.addElement(paramFileFilter);
/* 1149 */       firePropertyChange("ChoosableFileFilterChangedProperty", arrayOfFileFilter, getChoosableFileFilters());
/* 1150 */       if (this.fileFilter == null && this.filters.size() == 1) {
/* 1151 */         setFileFilter(paramFileFilter);
/*      */       }
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
/*      */   public boolean removeChoosableFileFilter(FileFilter paramFileFilter) {
/* 1165 */     int i = this.filters.indexOf(paramFileFilter);
/* 1166 */     if (i >= 0) {
/* 1167 */       if (getFileFilter() == paramFileFilter) {
/* 1168 */         FileFilter fileFilter = getAcceptAllFileFilter();
/* 1169 */         if (isAcceptAllFileFilterUsed() && fileFilter != paramFileFilter) {
/*      */           
/* 1171 */           setFileFilter(fileFilter);
/*      */         }
/* 1173 */         else if (i > 0) {
/*      */           
/* 1175 */           setFileFilter(this.filters.get(0));
/*      */         }
/* 1177 */         else if (this.filters.size() > 1) {
/*      */           
/* 1179 */           setFileFilter(this.filters.get(1));
/*      */         }
/*      */         else {
/*      */           
/* 1183 */           setFileFilter((FileFilter)null);
/*      */         } 
/*      */       } 
/* 1186 */       FileFilter[] arrayOfFileFilter = getChoosableFileFilters();
/* 1187 */       this.filters.removeElement(paramFileFilter);
/* 1188 */       firePropertyChange("ChoosableFileFilterChangedProperty", arrayOfFileFilter, getChoosableFileFilters());
/* 1189 */       return true;
/*      */     } 
/* 1191 */     return false;
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
/*      */ 
/*      */   
/*      */   public void resetChoosableFileFilters() {
/* 1205 */     FileFilter[] arrayOfFileFilter = getChoosableFileFilters();
/* 1206 */     setFileFilter((FileFilter)null);
/* 1207 */     this.filters.removeAllElements();
/* 1208 */     if (isAcceptAllFileFilterUsed()) {
/* 1209 */       addChoosableFileFilter(getAcceptAllFileFilter());
/*      */     }
/* 1211 */     firePropertyChange("ChoosableFileFilterChangedProperty", arrayOfFileFilter, getChoosableFileFilters());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FileFilter getAcceptAllFileFilter() {
/* 1219 */     FileFilter fileFilter = null;
/* 1220 */     if (getUI() != null) {
/* 1221 */       fileFilter = getUI().getAcceptAllFileFilter(this);
/*      */     }
/* 1223 */     return fileFilter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAcceptAllFileFilterUsed() {
/* 1233 */     return this.useAcceptAllFileFilter;
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
/*      */   public void setAcceptAllFileFilterUsed(boolean paramBoolean) {
/* 1255 */     boolean bool = this.useAcceptAllFileFilter;
/* 1256 */     this.useAcceptAllFileFilter = paramBoolean;
/* 1257 */     if (!paramBoolean) {
/* 1258 */       removeChoosableFileFilter(getAcceptAllFileFilter());
/*      */     } else {
/* 1260 */       removeChoosableFileFilter(getAcceptAllFileFilter());
/* 1261 */       addChoosableFileFilter(getAcceptAllFileFilter());
/*      */     } 
/* 1263 */     firePropertyChange("acceptAllFileFilterUsedChanged", bool, this.useAcceptAllFileFilter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JComponent getAccessory() {
/* 1273 */     return this.accessory;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAccessory(JComponent paramJComponent) {
/* 1292 */     JComponent jComponent = this.accessory;
/* 1293 */     this.accessory = paramJComponent;
/* 1294 */     firePropertyChange("AccessoryChangedProperty", jComponent, this.accessory);
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
/*      */   public void setFileSelectionMode(int paramInt) {
/* 1324 */     if (this.fileSelectionMode == paramInt) {
/*      */       return;
/*      */     }
/*      */     
/* 1328 */     if (paramInt == 0 || paramInt == 1 || paramInt == 2) {
/* 1329 */       int i = this.fileSelectionMode;
/* 1330 */       this.fileSelectionMode = paramInt;
/* 1331 */       firePropertyChange("fileSelectionChanged", i, this.fileSelectionMode);
/*      */     } else {
/* 1333 */       throw new IllegalArgumentException("Incorrect Mode for file selection: " + paramInt);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFileSelectionMode() {
/* 1350 */     return this.fileSelectionMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFileSelectionEnabled() {
/* 1361 */     return (this.fileSelectionMode == 0 || this.fileSelectionMode == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDirectorySelectionEnabled() {
/* 1372 */     return (this.fileSelectionMode == 1 || this.fileSelectionMode == 2);
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
/*      */ 
/*      */   
/*      */   public void setMultiSelectionEnabled(boolean paramBoolean) {
/* 1386 */     if (this.multiSelectionEnabled == paramBoolean) {
/*      */       return;
/*      */     }
/* 1389 */     boolean bool = this.multiSelectionEnabled;
/* 1390 */     this.multiSelectionEnabled = paramBoolean;
/* 1391 */     firePropertyChange("MultiSelectionEnabledChangedProperty", bool, this.multiSelectionEnabled);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMultiSelectionEnabled() {
/* 1400 */     return this.multiSelectionEnabled;
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
/*      */   public boolean isFileHidingEnabled() {
/* 1412 */     return this.useFileHiding;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFileHidingEnabled(boolean paramBoolean) {
/* 1431 */     if (this.showFilesListener != null) {
/* 1432 */       Toolkit.getDefaultToolkit().removePropertyChangeListener("awt.file.showHiddenFiles", this.showFilesListener);
/* 1433 */       this.showFilesListener = null;
/*      */     } 
/* 1435 */     boolean bool = this.useFileHiding;
/* 1436 */     this.useFileHiding = paramBoolean;
/* 1437 */     firePropertyChange("FileHidingChanged", bool, this.useFileHiding);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFileFilter(FileFilter paramFileFilter) {
/* 1453 */     FileFilter fileFilter = this.fileFilter;
/* 1454 */     this.fileFilter = paramFileFilter;
/* 1455 */     if (paramFileFilter != null) {
/* 1456 */       if (isMultiSelectionEnabled() && this.selectedFiles != null && this.selectedFiles.length > 0) {
/* 1457 */         Vector<File> vector = new Vector();
/* 1458 */         boolean bool = false;
/* 1459 */         for (File file : this.selectedFiles) {
/* 1460 */           if (paramFileFilter.accept(file)) {
/* 1461 */             vector.add(file);
/*      */           } else {
/* 1463 */             bool = true;
/*      */           } 
/*      */         } 
/* 1466 */         if (bool) {
/* 1467 */           setSelectedFiles((vector.size() == 0) ? null : vector.<File>toArray(new File[vector.size()]));
/*      */         }
/* 1469 */       } else if (this.selectedFile != null && !paramFileFilter.accept(this.selectedFile)) {
/* 1470 */         setSelectedFile((File)null);
/*      */       } 
/*      */     }
/* 1473 */     firePropertyChange("fileFilterChanged", fileFilter, this.fileFilter);
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
/*      */   public FileFilter getFileFilter() {
/* 1485 */     return this.fileFilter;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFileView(FileView paramFileView) {
/* 1500 */     FileView fileView = this.fileView;
/* 1501 */     this.fileView = paramFileView;
/* 1502 */     firePropertyChange("fileViewChanged", fileView, paramFileView);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FileView getFileView() {
/* 1511 */     return this.fileView;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName(File paramFile) {
/* 1531 */     String str = null;
/* 1532 */     if (paramFile != null) {
/* 1533 */       if (getFileView() != null) {
/* 1534 */         str = getFileView().getName(paramFile);
/*      */       }
/*      */       
/* 1537 */       FileView fileView = getUI().getFileView(this);
/*      */       
/* 1539 */       if (str == null && fileView != null) {
/* 1540 */         str = fileView.getName(paramFile);
/*      */       }
/*      */     } 
/* 1543 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription(File paramFile) {
/* 1554 */     String str = null;
/* 1555 */     if (paramFile != null) {
/* 1556 */       if (getFileView() != null) {
/* 1557 */         str = getFileView().getDescription(paramFile);
/*      */       }
/*      */       
/* 1560 */       FileView fileView = getUI().getFileView(this);
/*      */       
/* 1562 */       if (str == null && fileView != null) {
/* 1563 */         str = fileView.getDescription(paramFile);
/*      */       }
/*      */     } 
/* 1566 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTypeDescription(File paramFile) {
/* 1577 */     String str = null;
/* 1578 */     if (paramFile != null) {
/* 1579 */       if (getFileView() != null) {
/* 1580 */         str = getFileView().getTypeDescription(paramFile);
/*      */       }
/*      */       
/* 1583 */       FileView fileView = getUI().getFileView(this);
/*      */       
/* 1585 */       if (str == null && fileView != null) {
/* 1586 */         str = fileView.getTypeDescription(paramFile);
/*      */       }
/*      */     } 
/* 1589 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getIcon(File paramFile) {
/* 1600 */     Icon icon = null;
/* 1601 */     if (paramFile != null) {
/* 1602 */       if (getFileView() != null) {
/* 1603 */         icon = getFileView().getIcon(paramFile);
/*      */       }
/*      */       
/* 1606 */       FileView fileView = getUI().getFileView(this);
/*      */       
/* 1608 */       if (icon == null && fileView != null) {
/* 1609 */         icon = fileView.getIcon(paramFile);
/*      */       }
/*      */     } 
/* 1612 */     return icon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTraversable(File paramFile) {
/* 1623 */     Boolean bool = null;
/* 1624 */     if (paramFile != null) {
/* 1625 */       if (getFileView() != null) {
/* 1626 */         bool = getFileView().isTraversable(paramFile);
/*      */       }
/*      */       
/* 1629 */       FileView fileView = getUI().getFileView(this);
/*      */       
/* 1631 */       if (bool == null && fileView != null) {
/* 1632 */         bool = fileView.isTraversable(paramFile);
/*      */       }
/* 1634 */       if (bool == null) {
/* 1635 */         bool = getFileSystemView().isTraversable(paramFile);
/*      */       }
/*      */     } 
/* 1638 */     return (bool != null && bool.booleanValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean accept(File paramFile) {
/* 1648 */     boolean bool = true;
/* 1649 */     if (paramFile != null && this.fileFilter != null) {
/* 1650 */       bool = this.fileFilter.accept(paramFile);
/*      */     }
/* 1652 */     return bool;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFileSystemView(FileSystemView paramFileSystemView) {
/* 1669 */     FileSystemView fileSystemView = this.fileSystemView;
/* 1670 */     this.fileSystemView = paramFileSystemView;
/* 1671 */     firePropertyChange("FileSystemViewChanged", fileSystemView, this.fileSystemView);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FileSystemView getFileSystemView() {
/* 1680 */     return this.fileSystemView;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void approveSelection() {
/* 1698 */     this.returnValue = 0;
/* 1699 */     if (this.dialog != null) {
/* 1700 */       this.dialog.setVisible(false);
/*      */     }
/* 1702 */     fireActionPerformed("ApproveSelection");
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
/*      */   
/*      */   public void cancelSelection() {
/* 1715 */     this.returnValue = 1;
/* 1716 */     if (this.dialog != null) {
/* 1717 */       this.dialog.setVisible(false);
/*      */     }
/* 1719 */     fireActionPerformed("CancelSelection");
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
/*      */   public void addActionListener(ActionListener paramActionListener) {
/* 1731 */     this.listenerList.add(ActionListener.class, paramActionListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeActionListener(ActionListener paramActionListener) {
/* 1742 */     this.listenerList.remove(ActionListener.class, paramActionListener);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ActionListener[] getActionListeners() {
/* 1759 */     return this.listenerList.<ActionListener>getListeners(ActionListener.class);
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
/*      */   protected void fireActionPerformed(String paramString) {
/* 1771 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1772 */     long l = EventQueue.getMostRecentEventTime();
/* 1773 */     int i = 0;
/* 1774 */     AWTEvent aWTEvent = EventQueue.getCurrentEvent();
/* 1775 */     if (aWTEvent instanceof InputEvent) {
/* 1776 */       i = ((InputEvent)aWTEvent).getModifiers();
/* 1777 */     } else if (aWTEvent instanceof ActionEvent) {
/* 1778 */       i = ((ActionEvent)aWTEvent).getModifiers();
/*      */     } 
/* 1780 */     ActionEvent actionEvent = null;
/*      */ 
/*      */     
/* 1783 */     for (int j = arrayOfObject.length - 2; j >= 0; j -= 2) {
/* 1784 */       if (arrayOfObject[j] == ActionListener.class) {
/*      */         
/* 1786 */         if (actionEvent == null) {
/* 1787 */           actionEvent = new ActionEvent(this, 1001, paramString, l, i);
/*      */         }
/*      */ 
/*      */         
/* 1791 */         ((ActionListener)arrayOfObject[j + 1]).actionPerformed(actionEvent);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static class WeakPCL implements PropertyChangeListener {
/*      */     WeakReference<JFileChooser> jfcRef;
/*      */     
/*      */     public WeakPCL(JFileChooser param1JFileChooser) {
/* 1800 */       this.jfcRef = new WeakReference<>(param1JFileChooser);
/*      */     }
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1803 */       assert param1PropertyChangeEvent.getPropertyName().equals("awt.file.showHiddenFiles");
/* 1804 */       JFileChooser jFileChooser = this.jfcRef.get();
/* 1805 */       if (jFileChooser == null) {
/*      */ 
/*      */         
/* 1808 */         Toolkit.getDefaultToolkit().removePropertyChangeListener("awt.file.showHiddenFiles", this);
/*      */       } else {
/*      */         
/* 1811 */         boolean bool = jFileChooser.useFileHiding;
/* 1812 */         jFileChooser.useFileHiding = !((Boolean)param1PropertyChangeEvent.getNewValue()).booleanValue();
/* 1813 */         jFileChooser.firePropertyChange("FileHidingChanged", bool, jFileChooser.useFileHiding);
/*      */       } 
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
/*      */   
/*      */   public void updateUI() {
/* 1828 */     if (isAcceptAllFileFilterUsed()) {
/* 1829 */       removeChoosableFileFilter(getAcceptAllFileFilter());
/*      */     }
/* 1831 */     FileChooserUI fileChooserUI = (FileChooserUI)UIManager.getUI(this);
/* 1832 */     if (this.fileSystemView == null)
/*      */     {
/* 1834 */       setFileSystemView(FileSystemView.getFileSystemView());
/*      */     }
/* 1836 */     setUI(fileChooserUI);
/*      */     
/* 1838 */     if (isAcceptAllFileFilterUsed()) {
/* 1839 */       addChoosableFileFilter(getAcceptAllFileFilter());
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
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUIClassID() {
/* 1855 */     return "FileChooserUI";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FileChooserUI getUI() {
/* 1864 */     return (FileChooserUI)this.ui;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1874 */     paramObjectInputStream.defaultReadObject();
/* 1875 */     installShowFilesListener();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1884 */     FileSystemView fileSystemView = null;
/*      */     
/* 1886 */     if (isAcceptAllFileFilterUsed())
/*      */     {
/*      */       
/* 1889 */       removeChoosableFileFilter(getAcceptAllFileFilter());
/*      */     }
/* 1891 */     if (this.fileSystemView.equals(FileSystemView.getFileSystemView())) {
/*      */ 
/*      */       
/* 1894 */       fileSystemView = this.fileSystemView;
/* 1895 */       this.fileSystemView = null;
/*      */     } 
/* 1897 */     paramObjectOutputStream.defaultWriteObject();
/* 1898 */     if (fileSystemView != null) {
/* 1899 */       this.fileSystemView = fileSystemView;
/*      */     }
/* 1901 */     if (isAcceptAllFileFilterUsed()) {
/* 1902 */       addChoosableFileFilter(getAcceptAllFileFilter());
/*      */     }
/* 1904 */     if (getUIClassID().equals("FileChooserUI")) {
/* 1905 */       byte b = JComponent.getWriteObjCounter(this);
/* 1906 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 1907 */       if (b == 0 && this.ui != null) {
/* 1908 */         this.ui.installUI(this);
/*      */       }
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected String paramString() {
/* 1925 */     String str3, str4, str6, str1 = (this.approveButtonText != null) ? this.approveButtonText : "";
/*      */     
/* 1927 */     String str2 = (this.dialogTitle != null) ? this.dialogTitle : "";
/*      */ 
/*      */     
/* 1930 */     if (this.dialogType == 0)
/* 1931 */     { str3 = "OPEN_DIALOG"; }
/* 1932 */     else if (this.dialogType == 1)
/* 1933 */     { str3 = "SAVE_DIALOG"; }
/* 1934 */     else if (this.dialogType == 2)
/* 1935 */     { str3 = "CUSTOM_DIALOG"; }
/* 1936 */     else { str3 = ""; }
/*      */     
/* 1938 */     if (this.returnValue == 1)
/* 1939 */     { str4 = "CANCEL_OPTION"; }
/* 1940 */     else if (this.returnValue == 0)
/* 1941 */     { str4 = "APPROVE_OPTION"; }
/* 1942 */     else if (this.returnValue == -1)
/* 1943 */     { str4 = "ERROR_OPTION"; }
/* 1944 */     else { str4 = ""; }
/* 1945 */      String str5 = this.useFileHiding ? "true" : "false";
/*      */ 
/*      */     
/* 1948 */     if (this.fileSelectionMode == 0)
/* 1949 */     { str6 = "FILES_ONLY"; }
/* 1950 */     else if (this.fileSelectionMode == 1)
/* 1951 */     { str6 = "DIRECTORIES_ONLY"; }
/* 1952 */     else if (this.fileSelectionMode == 2)
/* 1953 */     { str6 = "FILES_AND_DIRECTORIES"; }
/* 1954 */     else { str6 = ""; }
/*      */     
/* 1956 */     String str7 = (this.currentDirectory != null) ? this.currentDirectory.toString() : "";
/*      */     
/* 1958 */     String str8 = (this.selectedFile != null) ? this.selectedFile.toString() : "";
/*      */     
/* 1960 */     return super.paramString() + ",approveButtonText=" + str1 + ",currentDirectory=" + str7 + ",dialogTitle=" + str2 + ",dialogType=" + str3 + ",fileSelectionMode=" + str6 + ",returnValue=" + str4 + ",selectedFile=" + str8 + ",useFileHiding=" + str5;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public JFileChooser(File paramFile, FileSystemView paramFileSystemView) {
/* 1975 */     this.accessibleContext = null; setup(paramFileSystemView); setCurrentDirectory(paramFile); } public JFileChooser(String paramString, FileSystemView paramFileSystemView) { this.accessibleContext = null;
/*      */     setup(paramFileSystemView);
/*      */     if (paramString == null) {
/*      */       setCurrentDirectory((File)null);
/*      */     } else {
/*      */       setCurrentDirectory(this.fileSystemView.createFileObject(paramString));
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AccessibleContext getAccessibleContext() {
/* 1987 */     if (this.accessibleContext == null) {
/* 1988 */       this.accessibleContext = new AccessibleJFileChooser();
/*      */     }
/* 1990 */     return this.accessibleContext;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AccessibleJFileChooser
/*      */     extends JComponent.AccessibleJComponent
/*      */   {
/*      */     public AccessibleRole getAccessibleRole() {
/* 2009 */       return AccessibleRole.FILE_CHOOSER;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JFileChooser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */