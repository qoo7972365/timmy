/*      */ package sun.swing.plaf.synth;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Locale;
/*      */ import java.util.Vector;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.AbstractListModel;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.ComboBoxModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.JToggleButton;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.filechooser.FileFilter;
/*      */ import javax.swing.filechooser.FileSystemView;
/*      */ import javax.swing.plaf.ActionMapUIResource;
/*      */ import javax.swing.plaf.basic.BasicDirectoryModel;
/*      */ import javax.swing.plaf.synth.SynthContext;
/*      */ import sun.awt.shell.ShellFolder;
/*      */ import sun.swing.FilePane;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SynthFileChooserUIImpl
/*      */   extends SynthFileChooserUI
/*      */ {
/*      */   private JLabel lookInLabel;
/*      */   private JComboBox<File> directoryComboBox;
/*      */   private DirectoryComboBoxModel directoryComboBoxModel;
/*   65 */   private Action directoryComboBoxAction = new DirectoryComboBoxAction();
/*      */   
/*      */   private FilterComboBoxModel filterComboBoxModel;
/*      */   
/*      */   private JTextField fileNameTextField;
/*      */   
/*      */   private FilePane filePane;
/*      */   
/*      */   private JToggleButton listViewButton;
/*      */   
/*      */   private JToggleButton detailsViewButton;
/*      */   
/*      */   private boolean readOnly;
/*      */   
/*      */   private JPanel buttonPanel;
/*      */   private JPanel bottomPanel;
/*      */   private JComboBox<FileFilter> filterComboBox;
/*   82 */   private static final Dimension hstrut5 = new Dimension(5, 1);
/*      */   
/*   84 */   private static final Insets shrinkwrap = new Insets(0, 0, 0, 0);
/*      */ 
/*      */   
/*   87 */   private static Dimension LIST_PREF_SIZE = new Dimension(405, 135);
/*      */ 
/*      */   
/*   90 */   private int lookInLabelMnemonic = 0;
/*   91 */   private String lookInLabelText = null;
/*   92 */   private String saveInLabelText = null;
/*      */   
/*   94 */   private int fileNameLabelMnemonic = 0;
/*   95 */   private String fileNameLabelText = null;
/*   96 */   private int folderNameLabelMnemonic = 0;
/*   97 */   private String folderNameLabelText = null;
/*      */   
/*   99 */   private int filesOfTypeLabelMnemonic = 0;
/*  100 */   private String filesOfTypeLabelText = null;
/*      */   
/*  102 */   private String upFolderToolTipText = null;
/*  103 */   private String upFolderAccessibleName = null;
/*      */   
/*  105 */   private String homeFolderToolTipText = null;
/*  106 */   private String homeFolderAccessibleName = null;
/*      */   
/*  108 */   private String newFolderToolTipText = null;
/*  109 */   private String newFolderAccessibleName = null;
/*      */   
/*  111 */   private String listViewButtonToolTipText = null;
/*  112 */   private String listViewButtonAccessibleName = null;
/*      */   
/*  114 */   private String detailsViewButtonToolTipText = null;
/*  115 */   private String detailsViewButtonAccessibleName = null;
/*      */   private AlignedLabel fileNameLabel;
/*      */   
/*  118 */   private final PropertyChangeListener modeListener = new PropertyChangeListener() {
/*      */       public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  120 */         if (SynthFileChooserUIImpl.this.fileNameLabel != null)
/*  121 */           SynthFileChooserUIImpl.this.populateFileNameLabel(); 
/*      */       }
/*      */     };
/*      */   static final int space = 10;
/*      */   
/*      */   private void populateFileNameLabel() {
/*  127 */     if (getFileChooser().getFileSelectionMode() == 1) {
/*  128 */       this.fileNameLabel.setText(this.folderNameLabelText);
/*  129 */       this.fileNameLabel.setDisplayedMnemonic(this.folderNameLabelMnemonic);
/*      */     } else {
/*  131 */       this.fileNameLabel.setText(this.fileNameLabelText);
/*  132 */       this.fileNameLabel.setDisplayedMnemonic(this.fileNameLabelMnemonic);
/*      */     } 
/*      */   }
/*      */   
/*      */   public SynthFileChooserUIImpl(JFileChooser paramJFileChooser) {
/*  137 */     super(paramJFileChooser);
/*      */   }
/*      */   
/*      */   private class SynthFileChooserUIAccessor implements FilePane.FileChooserUIAccessor { private SynthFileChooserUIAccessor() {}
/*      */     
/*      */     public JFileChooser getFileChooser() {
/*  143 */       return SynthFileChooserUIImpl.this.getFileChooser();
/*      */     }
/*      */     
/*      */     public BasicDirectoryModel getModel() {
/*  147 */       return SynthFileChooserUIImpl.this.getModel();
/*      */     }
/*      */     
/*      */     public JPanel createList() {
/*  151 */       return null;
/*      */     }
/*      */     
/*      */     public JPanel createDetailsView() {
/*  155 */       return null;
/*      */     }
/*      */     
/*      */     public boolean isDirectorySelected() {
/*  159 */       return SynthFileChooserUIImpl.this.isDirectorySelected();
/*      */     }
/*      */     
/*      */     public File getDirectory() {
/*  163 */       return SynthFileChooserUIImpl.this.getDirectory();
/*      */     }
/*      */     
/*      */     public Action getChangeToParentDirectoryAction() {
/*  167 */       return SynthFileChooserUIImpl.this.getChangeToParentDirectoryAction();
/*      */     }
/*      */     
/*      */     public Action getApproveSelectionAction() {
/*  171 */       return SynthFileChooserUIImpl.this.getApproveSelectionAction();
/*      */     }
/*      */     
/*      */     public Action getNewFolderAction() {
/*  175 */       return SynthFileChooserUIImpl.this.getNewFolderAction();
/*      */     }
/*      */     
/*      */     public MouseListener createDoubleClickListener(JList param1JList) {
/*  179 */       return SynthFileChooserUIImpl.this.createDoubleClickListener(getFileChooser(), param1JList);
/*      */     }
/*      */ 
/*      */     
/*      */     public ListSelectionListener createListSelectionListener() {
/*  184 */       return SynthFileChooserUIImpl.this.createListSelectionListener(getFileChooser());
/*      */     } }
/*      */ 
/*      */   
/*      */   protected void installDefaults(JFileChooser paramJFileChooser) {
/*  189 */     super.installDefaults(paramJFileChooser);
/*  190 */     this.readOnly = UIManager.getBoolean("FileChooser.readOnly");
/*      */   }
/*      */   
/*      */   public void installComponents(JFileChooser paramJFileChooser) {
/*  194 */     super.installComponents(paramJFileChooser);
/*      */     
/*  196 */     SynthContext synthContext = getContext(paramJFileChooser, 1);
/*      */     
/*  198 */     paramJFileChooser.setLayout(new BorderLayout(0, 11));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  205 */     JPanel jPanel1 = new JPanel(new BorderLayout(11, 0));
/*  206 */     JPanel jPanel2 = new JPanel();
/*  207 */     jPanel2.setLayout(new BoxLayout(jPanel2, 2));
/*  208 */     jPanel1.add(jPanel2, "After");
/*      */ 
/*      */     
/*  211 */     paramJFileChooser.add(jPanel1, "North");
/*      */ 
/*      */     
/*  214 */     this.lookInLabel = new JLabel(this.lookInLabelText);
/*  215 */     this.lookInLabel.setDisplayedMnemonic(this.lookInLabelMnemonic);
/*  216 */     jPanel1.add(this.lookInLabel, "Before");
/*      */ 
/*      */     
/*  219 */     this.directoryComboBox = new JComboBox<>();
/*  220 */     this.directoryComboBox.getAccessibleContext().setAccessibleDescription(this.lookInLabelText);
/*  221 */     this.directoryComboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
/*  222 */     this.lookInLabel.setLabelFor(this.directoryComboBox);
/*  223 */     this.directoryComboBoxModel = createDirectoryComboBoxModel(paramJFileChooser);
/*  224 */     this.directoryComboBox.setModel(this.directoryComboBoxModel);
/*  225 */     this.directoryComboBox.addActionListener(this.directoryComboBoxAction);
/*  226 */     this.directoryComboBox.setRenderer(createDirectoryComboBoxRenderer(paramJFileChooser));
/*  227 */     this.directoryComboBox.setAlignmentX(0.0F);
/*  228 */     this.directoryComboBox.setAlignmentY(0.0F);
/*  229 */     this.directoryComboBox.setMaximumRowCount(8);
/*  230 */     jPanel1.add(this.directoryComboBox, "Center");
/*      */     
/*  232 */     this.filePane = new FilePane(new SynthFileChooserUIAccessor());
/*  233 */     paramJFileChooser.addPropertyChangeListener(this.filePane);
/*      */ 
/*      */     
/*  236 */     JPopupMenu jPopupMenu = this.filePane.getComponentPopupMenu();
/*  237 */     if (jPopupMenu != null) {
/*  238 */       jPopupMenu.insert(getChangeToParentDirectoryAction(), 0);
/*  239 */       if (File.separatorChar == '/') {
/*  240 */         jPopupMenu.insert(getGoHomeAction(), 1);
/*      */       }
/*      */     } 
/*      */     
/*  244 */     FileSystemView fileSystemView = paramJFileChooser.getFileSystemView();
/*      */ 
/*      */     
/*  247 */     JButton jButton1 = new JButton(getChangeToParentDirectoryAction());
/*  248 */     jButton1.setText((String)null);
/*  249 */     jButton1.setIcon(this.upFolderIcon);
/*  250 */     jButton1.setToolTipText(this.upFolderToolTipText);
/*  251 */     jButton1.getAccessibleContext().setAccessibleName(this.upFolderAccessibleName);
/*  252 */     jButton1.setAlignmentX(0.0F);
/*  253 */     jButton1.setAlignmentY(0.5F);
/*  254 */     jButton1.setMargin(shrinkwrap);
/*      */     
/*  256 */     jPanel2.add(jButton1);
/*  257 */     jPanel2.add(Box.createRigidArea(hstrut5));
/*      */ 
/*      */     
/*  260 */     File file = fileSystemView.getHomeDirectory();
/*  261 */     String str = this.homeFolderToolTipText;
/*      */     
/*  263 */     JButton jButton2 = new JButton(this.homeFolderIcon);
/*  264 */     jButton2.setToolTipText(str);
/*  265 */     jButton2.getAccessibleContext().setAccessibleName(this.homeFolderAccessibleName);
/*  266 */     jButton2.setAlignmentX(0.0F);
/*  267 */     jButton2.setAlignmentY(0.5F);
/*  268 */     jButton2.setMargin(shrinkwrap);
/*      */     
/*  270 */     jButton2.addActionListener(getGoHomeAction());
/*  271 */     jPanel2.add(jButton2);
/*  272 */     jPanel2.add(Box.createRigidArea(hstrut5));
/*      */ 
/*      */     
/*  275 */     if (!this.readOnly) {
/*  276 */       jButton2 = new JButton(this.filePane.getNewFolderAction());
/*  277 */       jButton2.setText((String)null);
/*  278 */       jButton2.setIcon(this.newFolderIcon);
/*  279 */       jButton2.setToolTipText(this.newFolderToolTipText);
/*  280 */       jButton2.getAccessibleContext().setAccessibleName(this.newFolderAccessibleName);
/*  281 */       jButton2.setAlignmentX(0.0F);
/*  282 */       jButton2.setAlignmentY(0.5F);
/*  283 */       jButton2.setMargin(shrinkwrap);
/*  284 */       jPanel2.add(jButton2);
/*  285 */       jPanel2.add(Box.createRigidArea(hstrut5));
/*      */     } 
/*      */ 
/*      */     
/*  289 */     ButtonGroup buttonGroup = new ButtonGroup();
/*      */ 
/*      */     
/*  292 */     this.listViewButton = new JToggleButton(this.listViewIcon);
/*  293 */     this.listViewButton.setToolTipText(this.listViewButtonToolTipText);
/*  294 */     this.listViewButton.getAccessibleContext().setAccessibleName(this.listViewButtonAccessibleName);
/*  295 */     this.listViewButton.setSelected(true);
/*  296 */     this.listViewButton.setAlignmentX(0.0F);
/*  297 */     this.listViewButton.setAlignmentY(0.5F);
/*  298 */     this.listViewButton.setMargin(shrinkwrap);
/*  299 */     this.listViewButton.addActionListener(this.filePane.getViewTypeAction(0));
/*  300 */     jPanel2.add(this.listViewButton);
/*  301 */     buttonGroup.add(this.listViewButton);
/*      */ 
/*      */     
/*  304 */     this.detailsViewButton = new JToggleButton(this.detailsViewIcon);
/*  305 */     this.detailsViewButton.setToolTipText(this.detailsViewButtonToolTipText);
/*  306 */     this.detailsViewButton.getAccessibleContext().setAccessibleName(this.detailsViewButtonAccessibleName);
/*  307 */     this.detailsViewButton.setAlignmentX(0.0F);
/*  308 */     this.detailsViewButton.setAlignmentY(0.5F);
/*  309 */     this.detailsViewButton.setMargin(shrinkwrap);
/*  310 */     this.detailsViewButton.addActionListener(this.filePane.getViewTypeAction(1));
/*  311 */     jPanel2.add(this.detailsViewButton);
/*  312 */     buttonGroup.add(this.detailsViewButton);
/*      */     
/*  314 */     this.filePane.addPropertyChangeListener(new PropertyChangeListener() {
/*      */           public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  316 */             if ("viewType".equals(param1PropertyChangeEvent.getPropertyName())) {
/*  317 */               int i = SynthFileChooserUIImpl.this.filePane.getViewType();
/*  318 */               switch (i) {
/*      */                 case 0:
/*  320 */                   SynthFileChooserUIImpl.this.listViewButton.setSelected(true);
/*      */                   break;
/*      */                 case 1:
/*  323 */                   SynthFileChooserUIImpl.this.detailsViewButton.setSelected(true);
/*      */                   break;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             } 
/*      */           }
/*      */         });
/*  333 */     paramJFileChooser.add(getAccessoryPanel(), "After");
/*  334 */     JComponent jComponent = paramJFileChooser.getAccessory();
/*  335 */     if (jComponent != null) {
/*  336 */       getAccessoryPanel().add(jComponent);
/*      */     }
/*  338 */     this.filePane.setPreferredSize(LIST_PREF_SIZE);
/*  339 */     paramJFileChooser.add(this.filePane, "Center");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  345 */     this.bottomPanel = new JPanel();
/*  346 */     this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, 1));
/*  347 */     paramJFileChooser.add(this.bottomPanel, "South");
/*      */ 
/*      */     
/*  350 */     JPanel jPanel3 = new JPanel();
/*  351 */     jPanel3.setLayout(new BoxLayout(jPanel3, 2));
/*  352 */     this.bottomPanel.add(jPanel3);
/*  353 */     this.bottomPanel.add(Box.createRigidArea(new Dimension(1, 5)));
/*      */     
/*  355 */     this.fileNameLabel = new AlignedLabel();
/*  356 */     populateFileNameLabel();
/*  357 */     jPanel3.add(this.fileNameLabel);
/*      */     
/*  359 */     this.fileNameTextField = new JTextField(35) {
/*      */         public Dimension getMaximumSize() {
/*  361 */           return new Dimension(32767, (getPreferredSize()).height);
/*      */         }
/*      */       };
/*  364 */     jPanel3.add(this.fileNameTextField);
/*  365 */     this.fileNameLabel.setLabelFor(this.fileNameTextField);
/*  366 */     this.fileNameTextField.addFocusListener(new FocusAdapter()
/*      */         {
/*      */           public void focusGained(FocusEvent param1FocusEvent) {
/*  369 */             if (!SynthFileChooserUIImpl.this.getFileChooser().isMultiSelectionEnabled()) {
/*  370 */               SynthFileChooserUIImpl.this.filePane.clearSelection();
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  375 */     if (paramJFileChooser.isMultiSelectionEnabled()) {
/*  376 */       setFileName(fileNameString(paramJFileChooser.getSelectedFiles()));
/*      */     } else {
/*  378 */       setFileName(fileNameString(paramJFileChooser.getSelectedFile()));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  383 */     JPanel jPanel4 = new JPanel();
/*  384 */     jPanel4.setLayout(new BoxLayout(jPanel4, 2));
/*  385 */     this.bottomPanel.add(jPanel4);
/*      */     
/*  387 */     AlignedLabel alignedLabel = new AlignedLabel(this.filesOfTypeLabelText);
/*  388 */     alignedLabel.setDisplayedMnemonic(this.filesOfTypeLabelMnemonic);
/*  389 */     jPanel4.add(alignedLabel);
/*      */     
/*  391 */     this.filterComboBoxModel = createFilterComboBoxModel();
/*  392 */     paramJFileChooser.addPropertyChangeListener(this.filterComboBoxModel);
/*  393 */     this.filterComboBox = new JComboBox<>(this.filterComboBoxModel);
/*  394 */     this.filterComboBox.getAccessibleContext().setAccessibleDescription(this.filesOfTypeLabelText);
/*  395 */     alignedLabel.setLabelFor(this.filterComboBox);
/*  396 */     this.filterComboBox.setRenderer(createFilterComboBoxRenderer());
/*  397 */     jPanel4.add(this.filterComboBox);
/*      */ 
/*      */ 
/*      */     
/*  401 */     this.buttonPanel = new JPanel();
/*  402 */     this.buttonPanel.setLayout(new ButtonAreaLayout());
/*      */     
/*  404 */     this.buttonPanel.add(getApproveButton(paramJFileChooser));
/*  405 */     this.buttonPanel.add(getCancelButton(paramJFileChooser));
/*      */     
/*  407 */     if (paramJFileChooser.getControlButtonsAreShown()) {
/*  408 */       addControlButtons();
/*      */     }
/*      */     
/*  411 */     groupLabels(new AlignedLabel[] { this.fileNameLabel, alignedLabel });
/*      */   }
/*      */   
/*      */   protected void installListeners(JFileChooser paramJFileChooser) {
/*  415 */     super.installListeners(paramJFileChooser);
/*  416 */     paramJFileChooser.addPropertyChangeListener("fileSelectionChanged", this.modeListener);
/*      */   }
/*      */   
/*      */   protected void uninstallListeners(JFileChooser paramJFileChooser) {
/*  420 */     paramJFileChooser.removePropertyChangeListener("fileSelectionChanged", this.modeListener);
/*  421 */     super.uninstallListeners(paramJFileChooser);
/*      */   }
/*      */   
/*      */   private String fileNameString(File paramFile) {
/*  425 */     if (paramFile == null) {
/*  426 */       return null;
/*      */     }
/*  428 */     JFileChooser jFileChooser = getFileChooser();
/*  429 */     if (jFileChooser.isDirectorySelectionEnabled() && !jFileChooser.isFileSelectionEnabled()) {
/*  430 */       return paramFile.getPath();
/*      */     }
/*  432 */     return paramFile.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String fileNameString(File[] paramArrayOfFile) {
/*  438 */     StringBuffer stringBuffer = new StringBuffer();
/*  439 */     for (byte b = 0; paramArrayOfFile != null && b < paramArrayOfFile.length; b++) {
/*  440 */       if (b > 0) {
/*  441 */         stringBuffer.append(" ");
/*      */       }
/*  443 */       if (paramArrayOfFile.length > 1) {
/*  444 */         stringBuffer.append("\"");
/*      */       }
/*  446 */       stringBuffer.append(fileNameString(paramArrayOfFile[b]));
/*  447 */       if (paramArrayOfFile.length > 1) {
/*  448 */         stringBuffer.append("\"");
/*      */       }
/*      */     } 
/*  451 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  456 */     paramJComponent.removePropertyChangeListener(this.filterComboBoxModel);
/*  457 */     paramJComponent.removePropertyChangeListener(this.filePane);
/*      */     
/*  459 */     if (this.filePane != null) {
/*  460 */       this.filePane.uninstallUI();
/*  461 */       this.filePane = null;
/*      */     } 
/*      */     
/*  464 */     super.uninstallUI(paramJComponent);
/*      */   }
/*      */   
/*      */   protected void installStrings(JFileChooser paramJFileChooser) {
/*  468 */     super.installStrings(paramJFileChooser);
/*      */     
/*  470 */     Locale locale = paramJFileChooser.getLocale();
/*      */     
/*  472 */     this.lookInLabelMnemonic = getMnemonic("FileChooser.lookInLabelMnemonic", locale);
/*  473 */     this.lookInLabelText = UIManager.getString("FileChooser.lookInLabelText", locale);
/*  474 */     this.saveInLabelText = UIManager.getString("FileChooser.saveInLabelText", locale);
/*      */     
/*  476 */     this.fileNameLabelMnemonic = getMnemonic("FileChooser.fileNameLabelMnemonic", locale);
/*  477 */     this.fileNameLabelText = UIManager.getString("FileChooser.fileNameLabelText", locale);
/*  478 */     this.folderNameLabelMnemonic = getMnemonic("FileChooser.folderNameLabelMnemonic", locale);
/*  479 */     this.folderNameLabelText = UIManager.getString("FileChooser.folderNameLabelText", locale);
/*      */     
/*  481 */     this.filesOfTypeLabelMnemonic = getMnemonic("FileChooser.filesOfTypeLabelMnemonic", locale);
/*  482 */     this.filesOfTypeLabelText = UIManager.getString("FileChooser.filesOfTypeLabelText", locale);
/*      */     
/*  484 */     this.upFolderToolTipText = UIManager.getString("FileChooser.upFolderToolTipText", locale);
/*  485 */     this.upFolderAccessibleName = UIManager.getString("FileChooser.upFolderAccessibleName", locale);
/*      */     
/*  487 */     this.homeFolderToolTipText = UIManager.getString("FileChooser.homeFolderToolTipText", locale);
/*  488 */     this.homeFolderAccessibleName = UIManager.getString("FileChooser.homeFolderAccessibleName", locale);
/*      */     
/*  490 */     this.newFolderToolTipText = UIManager.getString("FileChooser.newFolderToolTipText", locale);
/*  491 */     this.newFolderAccessibleName = UIManager.getString("FileChooser.newFolderAccessibleName", locale);
/*      */     
/*  493 */     this.listViewButtonToolTipText = UIManager.getString("FileChooser.listViewButtonToolTipText", locale);
/*  494 */     this.listViewButtonAccessibleName = UIManager.getString("FileChooser.listViewButtonAccessibleName", locale);
/*      */     
/*  496 */     this.detailsViewButtonToolTipText = UIManager.getString("FileChooser.detailsViewButtonToolTipText", locale);
/*  497 */     this.detailsViewButtonAccessibleName = UIManager.getString("FileChooser.detailsViewButtonAccessibleName", locale);
/*      */   }
/*      */   
/*      */   private int getMnemonic(String paramString, Locale paramLocale) {
/*  501 */     return SwingUtilities2.getUIDefaultsInt(paramString, paramLocale);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getFileName() {
/*  506 */     if (this.fileNameTextField != null) {
/*  507 */       return this.fileNameTextField.getText();
/*      */     }
/*  509 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFileName(String paramString) {
/*  514 */     if (this.fileNameTextField != null) {
/*  515 */       this.fileNameTextField.setText(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   public void rescanCurrentDirectory(JFileChooser paramJFileChooser) {
/*  520 */     this.filePane.rescanCurrentDirectory();
/*      */   }
/*      */   
/*      */   protected void doSelectedFileChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  524 */     super.doSelectedFileChanged(paramPropertyChangeEvent);
/*      */     
/*  526 */     File file = (File)paramPropertyChangeEvent.getNewValue();
/*  527 */     JFileChooser jFileChooser = getFileChooser();
/*  528 */     if (file != null && ((jFileChooser
/*  529 */       .isFileSelectionEnabled() && !file.isDirectory()) || (file
/*  530 */       .isDirectory() && jFileChooser.isDirectorySelectionEnabled())))
/*      */     {
/*  532 */       setFileName(fileNameString(file));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void doSelectedFilesChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  537 */     super.doSelectedFilesChanged(paramPropertyChangeEvent);
/*      */     
/*  539 */     File[] arrayOfFile = (File[])paramPropertyChangeEvent.getNewValue();
/*  540 */     JFileChooser jFileChooser = getFileChooser();
/*  541 */     if (arrayOfFile != null && arrayOfFile.length > 0 && (arrayOfFile.length > 1 || jFileChooser
/*      */       
/*  543 */       .isDirectorySelectionEnabled() || !arrayOfFile[0].isDirectory())) {
/*  544 */       setFileName(fileNameString(arrayOfFile));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void doDirectoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  549 */     super.doDirectoryChanged(paramPropertyChangeEvent);
/*      */     
/*  551 */     JFileChooser jFileChooser = getFileChooser();
/*  552 */     FileSystemView fileSystemView = jFileChooser.getFileSystemView();
/*  553 */     File file = jFileChooser.getCurrentDirectory();
/*      */     
/*  555 */     if (!this.readOnly && file != null) {
/*  556 */       getNewFolderAction().setEnabled(this.filePane.canWrite(file));
/*      */     }
/*      */     
/*  559 */     if (file != null) {
/*  560 */       JComponent jComponent = getDirectoryComboBox();
/*  561 */       if (jComponent instanceof JComboBox) {
/*  562 */         ComboBoxModel comboBoxModel = ((JComboBox)jComponent).getModel();
/*  563 */         if (comboBoxModel instanceof DirectoryComboBoxModel) {
/*  564 */           ((DirectoryComboBoxModel)comboBoxModel).addItem(file);
/*      */         }
/*      */       } 
/*      */       
/*  568 */       if (jFileChooser.isDirectorySelectionEnabled() && !jFileChooser.isFileSelectionEnabled()) {
/*  569 */         if (fileSystemView.isFileSystem(file)) {
/*  570 */           setFileName(file.getPath());
/*      */         } else {
/*  572 */           setFileName((String)null);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doFileSelectionModeChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  580 */     super.doFileSelectionModeChanged(paramPropertyChangeEvent);
/*      */     
/*  582 */     JFileChooser jFileChooser = getFileChooser();
/*  583 */     File file = jFileChooser.getCurrentDirectory();
/*  584 */     if (file != null && jFileChooser
/*  585 */       .isDirectorySelectionEnabled() && 
/*  586 */       !jFileChooser.isFileSelectionEnabled() && jFileChooser
/*  587 */       .getFileSystemView().isFileSystem(file)) {
/*      */       
/*  589 */       setFileName(file.getPath());
/*      */     } else {
/*  591 */       setFileName((String)null);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void doAccessoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  596 */     if (getAccessoryPanel() != null) {
/*  597 */       if (paramPropertyChangeEvent.getOldValue() != null) {
/*  598 */         getAccessoryPanel().remove((JComponent)paramPropertyChangeEvent.getOldValue());
/*      */       }
/*  600 */       JComponent jComponent = (JComponent)paramPropertyChangeEvent.getNewValue();
/*  601 */       if (jComponent != null) {
/*  602 */         getAccessoryPanel().add(jComponent, "Center");
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void doControlButtonsChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  608 */     super.doControlButtonsChanged(paramPropertyChangeEvent);
/*      */     
/*  610 */     if (getFileChooser().getControlButtonsAreShown()) {
/*  611 */       addControlButtons();
/*      */     } else {
/*  613 */       removeControlButtons();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void addControlButtons() {
/*  618 */     if (this.bottomPanel != null) {
/*  619 */       this.bottomPanel.add(this.buttonPanel);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void removeControlButtons() {
/*  624 */     if (this.bottomPanel != null) {
/*  625 */       this.bottomPanel.remove(this.buttonPanel);
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
/*      */   protected ActionMap createActionMap() {
/*  637 */     ActionMapUIResource actionMapUIResource = new ActionMapUIResource();
/*      */     
/*  639 */     FilePane.addActionsToMap(actionMapUIResource, this.filePane.getActions());
/*      */     
/*  641 */     actionMapUIResource.put("fileNameCompletion", getFileNameCompletionAction());
/*  642 */     return actionMapUIResource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JComponent getDirectoryComboBox() {
/*  650 */     return this.directoryComboBox;
/*      */   }
/*      */   
/*      */   protected Action getDirectoryComboBoxAction() {
/*  654 */     return this.directoryComboBoxAction;
/*      */   }
/*      */   
/*      */   protected DirectoryComboBoxRenderer createDirectoryComboBoxRenderer(JFileChooser paramJFileChooser) {
/*  658 */     return new DirectoryComboBoxRenderer(this.directoryComboBox.getRenderer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class DirectoryComboBoxRenderer
/*      */     implements ListCellRenderer<File>
/*      */   {
/*      */     private ListCellRenderer<? super File> delegate;
/*      */ 
/*      */ 
/*      */     
/*  672 */     SynthFileChooserUIImpl.IndentIcon ii = new SynthFileChooserUIImpl.IndentIcon();
/*      */     
/*      */     private DirectoryComboBoxRenderer(ListCellRenderer<? super File> param1ListCellRenderer) {
/*  675 */       this.delegate = param1ListCellRenderer;
/*      */     }
/*      */ 
/*      */     
/*      */     public Component getListCellRendererComponent(JList<? extends File> param1JList, File param1File, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/*  680 */       Component component = this.delegate.getListCellRendererComponent(param1JList, param1File, param1Int, param1Boolean1, param1Boolean2);
/*      */       
/*  682 */       assert component instanceof JLabel;
/*  683 */       JLabel jLabel = (JLabel)component;
/*  684 */       if (param1File == null) {
/*  685 */         jLabel.setText("");
/*  686 */         return jLabel;
/*      */       } 
/*  688 */       jLabel.setText(SynthFileChooserUIImpl.this.getFileChooser().getName(param1File));
/*  689 */       Icon icon = SynthFileChooserUIImpl.this.getFileChooser().getIcon(param1File);
/*  690 */       this.ii.icon = icon;
/*  691 */       this.ii.depth = SynthFileChooserUIImpl.this.directoryComboBoxModel.getDepth(param1Int);
/*  692 */       jLabel.setIcon(this.ii);
/*      */       
/*  694 */       return jLabel;
/*      */     }
/*      */   }
/*      */   
/*      */   class IndentIcon
/*      */     implements Icon
/*      */   {
/*  701 */     Icon icon = null;
/*  702 */     int depth = 0;
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  705 */       if (this.icon != null) {
/*  706 */         if (param1Component.getComponentOrientation().isLeftToRight()) {
/*  707 */           this.icon.paintIcon(param1Component, param1Graphics, param1Int1 + this.depth * 10, param1Int2);
/*      */         } else {
/*  709 */           this.icon.paintIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  715 */       return ((this.icon != null) ? this.icon.getIconWidth() : 0) + this.depth * 10;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  719 */       return (this.icon != null) ? this.icon.getIconHeight() : 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DirectoryComboBoxModel createDirectoryComboBoxModel(JFileChooser paramJFileChooser) {
/*  728 */     return new DirectoryComboBoxModel();
/*      */   }
/*      */   
/*      */   protected class DirectoryComboBoxModel
/*      */     extends AbstractListModel<File>
/*      */     implements ComboBoxModel<File>
/*      */   {
/*  735 */     Vector<File> directories = new Vector<>();
/*  736 */     int[] depths = null;
/*  737 */     File selectedDirectory = null;
/*  738 */     JFileChooser chooser = SynthFileChooserUIImpl.this.getFileChooser();
/*  739 */     FileSystemView fsv = this.chooser.getFileSystemView();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addItem(File param1File) {
/*      */       File file;
/*  757 */       if (param1File == null) {
/*      */         return;
/*      */       }
/*      */       
/*  761 */       boolean bool = FilePane.usesShellFolder(this.chooser);
/*      */       
/*  763 */       int i = this.directories.size();
/*  764 */       this.directories.clear();
/*  765 */       if (i > 0) {
/*  766 */         fireIntervalRemoved(this, 0, i);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  771 */       File[] arrayOfFile = bool ? (File[])ShellFolder.get("fileChooserComboBoxFolders") : this.fsv.getRoots();
/*  772 */       this.directories.addAll(Arrays.asList(arrayOfFile));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  779 */         file = ShellFolder.getNormalizedFile(param1File);
/*  780 */       } catch (IOException iOException) {
/*      */         
/*  782 */         file = param1File;
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  787 */         File file1 = bool ? ShellFolder.getShellFolder(file) : file;
/*      */         
/*  789 */         File file2 = file1;
/*  790 */         Vector<File> vector = new Vector(10);
/*      */         do {
/*  792 */           vector.addElement(file2);
/*  793 */         } while ((file2 = file2.getParentFile()) != null);
/*      */         
/*  795 */         int j = vector.size();
/*      */         
/*  797 */         for (byte b = 0; b < j; b++) {
/*  798 */           file2 = vector.get(b);
/*  799 */           if (this.directories.contains(file2)) {
/*  800 */             int k = this.directories.indexOf(file2);
/*  801 */             for (int m = b - 1; m >= 0; m--) {
/*  802 */               this.directories.insertElementAt(vector.get(m), k + b - m);
/*      */             }
/*      */             break;
/*      */           } 
/*      */         } 
/*  807 */         calculateDepths();
/*  808 */         setSelectedItem(file1);
/*  809 */       } catch (FileNotFoundException fileNotFoundException) {
/*  810 */         calculateDepths();
/*      */       } 
/*      */     }
/*      */     
/*      */     private void calculateDepths() {
/*  815 */       this.depths = new int[this.directories.size()];
/*  816 */       for (byte b = 0; b < this.depths.length; b++) {
/*  817 */         File file1 = this.directories.get(b);
/*  818 */         File file2 = file1.getParentFile();
/*  819 */         this.depths[b] = 0;
/*  820 */         if (file2 != null) {
/*  821 */           for (int i = b - 1; i >= 0; i--) {
/*  822 */             if (file2.equals(this.directories.get(i))) {
/*  823 */               this.depths[b] = this.depths[i] + 1;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public int getDepth(int param1Int) {
/*  832 */       return (this.depths != null && param1Int >= 0 && param1Int < this.depths.length) ? this.depths[param1Int] : 0;
/*      */     }
/*      */     
/*      */     public void setSelectedItem(Object param1Object) {
/*  836 */       this.selectedDirectory = (File)param1Object;
/*  837 */       fireContentsChanged(this, -1, -1);
/*      */     }
/*      */     
/*      */     public Object getSelectedItem() {
/*  841 */       return this.selectedDirectory;
/*      */     }
/*      */     
/*      */     public int getSize() {
/*  845 */       return this.directories.size();
/*      */     } public DirectoryComboBoxModel() { File file = SynthFileChooserUIImpl.this.getFileChooser().getCurrentDirectory();
/*      */       if (file != null)
/*      */         addItem(file);  } public File getElementAt(int param1Int) {
/*  849 */       return this.directories.elementAt(param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected class DirectoryComboBoxAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected DirectoryComboBoxAction() {
/*  858 */       super("DirectoryComboBoxAction");
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  862 */       SynthFileChooserUIImpl.this.directoryComboBox.hidePopup();
/*  863 */       JComponent jComponent = SynthFileChooserUIImpl.this.getDirectoryComboBox();
/*  864 */       if (jComponent instanceof JComboBox) {
/*  865 */         File file = (File)((JComboBox)jComponent).getSelectedItem();
/*  866 */         SynthFileChooserUIImpl.this.getFileChooser().setCurrentDirectory(file);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FilterComboBoxRenderer createFilterComboBoxRenderer() {
/*  875 */     return new FilterComboBoxRenderer(this.filterComboBox.getRenderer());
/*      */   }
/*      */   
/*      */   public class FilterComboBoxRenderer
/*      */     implements ListCellRenderer<FileFilter>
/*      */   {
/*      */     private ListCellRenderer<? super FileFilter> delegate;
/*      */     
/*      */     private FilterComboBoxRenderer(ListCellRenderer<? super FileFilter> param1ListCellRenderer) {
/*  884 */       this.delegate = param1ListCellRenderer;
/*      */     }
/*      */ 
/*      */     
/*      */     public Component getListCellRendererComponent(JList<? extends FileFilter> param1JList, FileFilter param1FileFilter, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/*  889 */       Component component = this.delegate.getListCellRendererComponent(param1JList, param1FileFilter, param1Int, param1Boolean1, param1Boolean2);
/*      */       
/*  891 */       String str = null;
/*  892 */       if (param1FileFilter != null) {
/*  893 */         str = param1FileFilter.getDescription();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  898 */       assert component instanceof JLabel;
/*  899 */       if (str != null) {
/*  900 */         ((JLabel)component).setText(str);
/*      */       }
/*  902 */       return component;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FilterComboBoxModel createFilterComboBoxModel() {
/*  910 */     return new FilterComboBoxModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class FilterComboBoxModel
/*      */     extends AbstractListModel<FileFilter>
/*      */     implements ComboBoxModel<FileFilter>, PropertyChangeListener
/*      */   {
/*  921 */     protected FileFilter[] filters = SynthFileChooserUIImpl.this.getFileChooser().getChoosableFileFilters();
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  925 */       String str = param1PropertyChangeEvent.getPropertyName();
/*  926 */       if (str == "ChoosableFileFilterChangedProperty") {
/*  927 */         this.filters = (FileFilter[])param1PropertyChangeEvent.getNewValue();
/*  928 */         fireContentsChanged(this, -1, -1);
/*  929 */       } else if (str == "fileFilterChanged") {
/*  930 */         fireContentsChanged(this, -1, -1);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void setSelectedItem(Object param1Object) {
/*  935 */       if (param1Object != null) {
/*  936 */         SynthFileChooserUIImpl.this.getFileChooser().setFileFilter((FileFilter)param1Object);
/*  937 */         fireContentsChanged(this, -1, -1);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getSelectedItem() {
/*  947 */       FileFilter fileFilter = SynthFileChooserUIImpl.this.getFileChooser().getFileFilter();
/*  948 */       boolean bool = false;
/*  949 */       if (fileFilter != null) {
/*  950 */         for (FileFilter fileFilter1 : this.filters) {
/*  951 */           if (fileFilter1 == fileFilter) {
/*  952 */             bool = true;
/*      */           }
/*      */         } 
/*  955 */         if (!bool) {
/*  956 */           SynthFileChooserUIImpl.this.getFileChooser().addChoosableFileFilter(fileFilter);
/*      */         }
/*      */       } 
/*  959 */       return SynthFileChooserUIImpl.this.getFileChooser().getFileFilter();
/*      */     }
/*      */     
/*      */     public int getSize() {
/*  963 */       if (this.filters != null) {
/*  964 */         return this.filters.length;
/*      */       }
/*  966 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public FileFilter getElementAt(int param1Int) {
/*  971 */       if (param1Int > getSize() - 1)
/*      */       {
/*  973 */         return SynthFileChooserUIImpl.this.getFileChooser().getFileFilter();
/*      */       }
/*  975 */       if (this.filters != null) {
/*  976 */         return this.filters[param1Int];
/*      */       }
/*  978 */       return null;
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
/*      */   private static class ButtonAreaLayout
/*      */     implements LayoutManager
/*      */   {
/*  992 */     private int hGap = 5;
/*  993 */     private int topMargin = 17;
/*      */ 
/*      */     
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/*  999 */       Component[] arrayOfComponent = param1Container.getComponents();
/*      */       
/* 1001 */       if (arrayOfComponent != null && arrayOfComponent.length > 0) {
/* 1002 */         int n, i = arrayOfComponent.length;
/* 1003 */         Dimension[] arrayOfDimension = new Dimension[i];
/* 1004 */         Insets insets = param1Container.getInsets();
/* 1005 */         int j = insets.top + this.topMargin;
/* 1006 */         int k = 0;
/*      */         int m;
/* 1008 */         for (m = 0; m < i; m++) {
/* 1009 */           arrayOfDimension[m] = arrayOfComponent[m].getPreferredSize();
/* 1010 */           k = Math.max(k, (arrayOfDimension[m]).width);
/*      */         } 
/*      */         
/* 1013 */         if (param1Container.getComponentOrientation().isLeftToRight()) {
/* 1014 */           m = (param1Container.getSize()).width - insets.left - k;
/* 1015 */           n = this.hGap + k;
/*      */         } else {
/* 1017 */           m = insets.left;
/* 1018 */           n = -(this.hGap + k);
/*      */         } 
/* 1020 */         for (int i1 = i - 1; i1 >= 0; i1--) {
/* 1021 */           arrayOfComponent[i1].setBounds(m, j, k, (arrayOfDimension[i1]).height);
/*      */           
/* 1023 */           m -= n;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/* 1029 */       if (param1Container != null) {
/* 1030 */         Component[] arrayOfComponent = param1Container.getComponents();
/*      */         
/* 1032 */         if (arrayOfComponent != null && arrayOfComponent.length > 0) {
/* 1033 */           int i = arrayOfComponent.length;
/* 1034 */           int j = 0;
/* 1035 */           Insets insets = param1Container.getInsets();
/* 1036 */           int k = this.topMargin + insets.top + insets.bottom;
/* 1037 */           int m = insets.left + insets.right;
/* 1038 */           int n = 0;
/*      */           
/* 1040 */           for (byte b = 0; b < i; b++) {
/* 1041 */             Dimension dimension = arrayOfComponent[b].getPreferredSize();
/* 1042 */             j = Math.max(j, dimension.height);
/* 1043 */             n = Math.max(n, dimension.width);
/*      */           } 
/* 1045 */           return new Dimension(m + i * n + (i - 1) * this.hGap, k + j);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1050 */       return new Dimension(0, 0);
/*      */     }
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/* 1054 */       return minimumLayoutSize(param1Container);
/*      */     }
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */     
/*      */     private ButtonAreaLayout() {} }
/*      */   
/*      */   private static void groupLabels(AlignedLabel[] paramArrayOfAlignedLabel) {
/* 1061 */     for (byte b = 0; b < paramArrayOfAlignedLabel.length; b++)
/* 1062 */       (paramArrayOfAlignedLabel[b]).group = paramArrayOfAlignedLabel; 
/*      */   }
/*      */   
/*      */   private class AlignedLabel
/*      */     extends JLabel {
/*      */     private AlignedLabel[] group;
/* 1068 */     private int maxWidth = 0;
/*      */ 
/*      */     
/*      */     AlignedLabel() {
/* 1072 */       setAlignmentX(0.0F);
/*      */     }
/*      */     
/*      */     AlignedLabel(String param1String) {
/* 1076 */       super(param1String);
/* 1077 */       setAlignmentX(0.0F);
/*      */     }
/*      */     
/*      */     public Dimension getPreferredSize() {
/* 1081 */       Dimension dimension = super.getPreferredSize();
/*      */       
/* 1083 */       return new Dimension(getMaxWidth() + 11, dimension.height);
/*      */     }
/*      */     
/*      */     private int getMaxWidth() {
/* 1087 */       if (this.maxWidth == 0 && this.group != null) {
/* 1088 */         int i = 0; byte b;
/* 1089 */         for (b = 0; b < this.group.length; b++) {
/* 1090 */           i = Math.max(this.group[b].getSuperPreferredWidth(), i);
/*      */         }
/* 1092 */         for (b = 0; b < this.group.length; b++) {
/* 1093 */           (this.group[b]).maxWidth = i;
/*      */         }
/*      */       } 
/* 1096 */       return this.maxWidth;
/*      */     }
/*      */     
/*      */     private int getSuperPreferredWidth() {
/* 1100 */       return (super.getPreferredSize()).width;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/plaf/synth/SynthFileChooserUIImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */