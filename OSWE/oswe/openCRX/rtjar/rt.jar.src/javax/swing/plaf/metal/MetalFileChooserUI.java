/*      */ package javax.swing.plaf.metal;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.MouseAdapter;
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
/*      */ import javax.swing.DefaultListCellRenderer;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.JToggleButton;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.filechooser.FileFilter;
/*      */ import javax.swing.filechooser.FileSystemView;
/*      */ import javax.swing.plaf.ActionMapUIResource;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.basic.BasicDirectoryModel;
/*      */ import javax.swing.plaf.basic.BasicFileChooserUI;
/*      */ import sun.awt.shell.ShellFolder;
/*      */ import sun.swing.FilePane;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ public class MetalFileChooserUI
/*      */   extends BasicFileChooserUI {
/*      */   private JLabel lookInLabel;
/*      */   private JComboBox directoryComboBox;
/*      */   private DirectoryComboBoxModel directoryComboBoxModel;
/*   63 */   private Action directoryComboBoxAction = new DirectoryComboBoxAction();
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
/*      */   private JButton approveButton;
/*      */   
/*      */   private JButton cancelButton;
/*      */   private JPanel buttonPanel;
/*      */   private JPanel bottomPanel;
/*      */   private JComboBox filterComboBox;
/*   81 */   private static final Dimension hstrut5 = new Dimension(5, 1);
/*   82 */   private static final Dimension hstrut11 = new Dimension(11, 1);
/*      */   
/*   84 */   private static final Dimension vstrut5 = new Dimension(1, 5);
/*      */   
/*   86 */   private static final Insets shrinkwrap = new Insets(0, 0, 0, 0);
/*      */ 
/*      */   
/*   89 */   private static int PREF_WIDTH = 500;
/*   90 */   private static int PREF_HEIGHT = 326;
/*   91 */   private static Dimension PREF_SIZE = new Dimension(PREF_WIDTH, PREF_HEIGHT);
/*      */   
/*   93 */   private static int MIN_WIDTH = 500;
/*   94 */   private static int MIN_HEIGHT = 326;
/*   95 */   private static int LIST_PREF_WIDTH = 405;
/*   96 */   private static int LIST_PREF_HEIGHT = 135;
/*   97 */   private static Dimension LIST_PREF_SIZE = new Dimension(LIST_PREF_WIDTH, LIST_PREF_HEIGHT);
/*      */ 
/*      */   
/*  100 */   private int lookInLabelMnemonic = 0;
/*  101 */   private String lookInLabelText = null;
/*  102 */   private String saveInLabelText = null;
/*      */   
/*  104 */   private int fileNameLabelMnemonic = 0;
/*  105 */   private String fileNameLabelText = null;
/*  106 */   private int folderNameLabelMnemonic = 0;
/*  107 */   private String folderNameLabelText = null;
/*      */   
/*  109 */   private int filesOfTypeLabelMnemonic = 0;
/*  110 */   private String filesOfTypeLabelText = null;
/*      */   
/*  112 */   private String upFolderToolTipText = null;
/*  113 */   private String upFolderAccessibleName = null;
/*      */   
/*  115 */   private String homeFolderToolTipText = null;
/*  116 */   private String homeFolderAccessibleName = null;
/*      */   
/*  118 */   private String newFolderToolTipText = null;
/*  119 */   private String newFolderAccessibleName = null;
/*      */   
/*  121 */   private String listViewButtonToolTipText = null;
/*  122 */   private String listViewButtonAccessibleName = null;
/*      */   
/*  124 */   private String detailsViewButtonToolTipText = null;
/*  125 */   private String detailsViewButtonAccessibleName = null;
/*      */   private AlignedLabel fileNameLabel;
/*      */   static final int space = 10;
/*      */   
/*      */   private void populateFileNameLabel() {
/*  130 */     if (getFileChooser().getFileSelectionMode() == 1) {
/*  131 */       this.fileNameLabel.setText(this.folderNameLabelText);
/*  132 */       this.fileNameLabel.setDisplayedMnemonic(this.folderNameLabelMnemonic);
/*      */     } else {
/*  134 */       this.fileNameLabel.setText(this.fileNameLabelText);
/*  135 */       this.fileNameLabel.setDisplayedMnemonic(this.fileNameLabelMnemonic);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  143 */     return new MetalFileChooserUI((JFileChooser)paramJComponent);
/*      */   }
/*      */   
/*      */   public MetalFileChooserUI(JFileChooser paramJFileChooser) {
/*  147 */     super(paramJFileChooser);
/*      */   }
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  151 */     super.installUI(paramJComponent);
/*      */   }
/*      */   
/*      */   public void uninstallComponents(JFileChooser paramJFileChooser) {
/*  155 */     paramJFileChooser.removeAll();
/*  156 */     this.bottomPanel = null;
/*  157 */     this.buttonPanel = null;
/*      */   }
/*      */   private class MetalFileChooserUIAccessor implements FilePane.FileChooserUIAccessor { private MetalFileChooserUIAccessor() {}
/*      */     
/*      */     public JFileChooser getFileChooser() {
/*  162 */       return MetalFileChooserUI.this.getFileChooser();
/*      */     }
/*      */     
/*      */     public BasicDirectoryModel getModel() {
/*  166 */       return MetalFileChooserUI.this.getModel();
/*      */     }
/*      */     
/*      */     public JPanel createList() {
/*  170 */       return MetalFileChooserUI.this.createList(getFileChooser());
/*      */     }
/*      */     
/*      */     public JPanel createDetailsView() {
/*  174 */       return MetalFileChooserUI.this.createDetailsView(getFileChooser());
/*      */     }
/*      */     
/*      */     public boolean isDirectorySelected() {
/*  178 */       return MetalFileChooserUI.this.isDirectorySelected();
/*      */     }
/*      */     
/*      */     public File getDirectory() {
/*  182 */       return MetalFileChooserUI.this.getDirectory();
/*      */     }
/*      */     
/*      */     public Action getChangeToParentDirectoryAction() {
/*  186 */       return MetalFileChooserUI.this.getChangeToParentDirectoryAction();
/*      */     }
/*      */     
/*      */     public Action getApproveSelectionAction() {
/*  190 */       return MetalFileChooserUI.this.getApproveSelectionAction();
/*      */     }
/*      */     
/*      */     public Action getNewFolderAction() {
/*  194 */       return MetalFileChooserUI.this.getNewFolderAction();
/*      */     }
/*      */     
/*      */     public MouseListener createDoubleClickListener(JList param1JList) {
/*  198 */       return MetalFileChooserUI.this.createDoubleClickListener(getFileChooser(), param1JList);
/*      */     }
/*      */ 
/*      */     
/*      */     public ListSelectionListener createListSelectionListener() {
/*  203 */       return MetalFileChooserUI.this.createListSelectionListener(getFileChooser());
/*      */     } }
/*      */ 
/*      */   
/*      */   public void installComponents(JFileChooser paramJFileChooser) {
/*  208 */     FileSystemView fileSystemView = paramJFileChooser.getFileSystemView();
/*      */     
/*  210 */     paramJFileChooser.setBorder(new EmptyBorder(12, 12, 11, 11));
/*  211 */     paramJFileChooser.setLayout(new BorderLayout(0, 11));
/*      */     
/*  213 */     this.filePane = new FilePane(new MetalFileChooserUIAccessor());
/*  214 */     paramJFileChooser.addPropertyChangeListener(this.filePane);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  221 */     JPanel jPanel1 = new JPanel(new BorderLayout(11, 0));
/*  222 */     JPanel jPanel2 = new JPanel();
/*  223 */     jPanel2.setLayout(new BoxLayout(jPanel2, 2));
/*  224 */     jPanel1.add(jPanel2, "After");
/*      */ 
/*      */     
/*  227 */     paramJFileChooser.add(jPanel1, "North");
/*      */ 
/*      */     
/*  230 */     this.lookInLabel = new JLabel(this.lookInLabelText);
/*  231 */     this.lookInLabel.setDisplayedMnemonic(this.lookInLabelMnemonic);
/*  232 */     jPanel1.add(this.lookInLabel, "Before");
/*      */ 
/*      */     
/*  235 */     this.directoryComboBox = new JComboBox() {
/*      */         public Dimension getPreferredSize() {
/*  237 */           Dimension dimension = super.getPreferredSize();
/*      */           
/*  239 */           dimension.width = 150;
/*  240 */           return dimension;
/*      */         }
/*      */       };
/*  243 */     this.directoryComboBox.putClientProperty("AccessibleDescription", this.lookInLabelText);
/*      */     
/*  245 */     this.directoryComboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
/*  246 */     this.lookInLabel.setLabelFor(this.directoryComboBox);
/*  247 */     this.directoryComboBoxModel = createDirectoryComboBoxModel(paramJFileChooser);
/*  248 */     this.directoryComboBox.setModel(this.directoryComboBoxModel);
/*  249 */     this.directoryComboBox.addActionListener(this.directoryComboBoxAction);
/*  250 */     this.directoryComboBox.setRenderer(createDirectoryComboBoxRenderer(paramJFileChooser));
/*  251 */     this.directoryComboBox.setAlignmentX(0.0F);
/*  252 */     this.directoryComboBox.setAlignmentY(0.0F);
/*  253 */     this.directoryComboBox.setMaximumRowCount(8);
/*      */     
/*  255 */     jPanel1.add(this.directoryComboBox, "Center");
/*      */ 
/*      */     
/*  258 */     JButton jButton1 = new JButton(getChangeToParentDirectoryAction());
/*  259 */     jButton1.setText((String)null);
/*  260 */     jButton1.setIcon(this.upFolderIcon);
/*  261 */     jButton1.setToolTipText(this.upFolderToolTipText);
/*  262 */     jButton1.putClientProperty("AccessibleName", this.upFolderAccessibleName);
/*      */     
/*  264 */     jButton1.setAlignmentX(0.0F);
/*  265 */     jButton1.setAlignmentY(0.5F);
/*  266 */     jButton1.setMargin(shrinkwrap);
/*      */     
/*  268 */     jPanel2.add(jButton1);
/*  269 */     jPanel2.add(Box.createRigidArea(hstrut5));
/*      */ 
/*      */     
/*  272 */     File file = fileSystemView.getHomeDirectory();
/*  273 */     String str = this.homeFolderToolTipText;
/*      */ 
/*      */     
/*  276 */     JButton jButton2 = new JButton(this.homeFolderIcon);
/*  277 */     jButton2.setToolTipText(str);
/*  278 */     jButton2.putClientProperty("AccessibleName", this.homeFolderAccessibleName);
/*      */     
/*  280 */     jButton2.setAlignmentX(0.0F);
/*  281 */     jButton2.setAlignmentY(0.5F);
/*  282 */     jButton2.setMargin(shrinkwrap);
/*      */     
/*  284 */     jButton2.addActionListener(getGoHomeAction());
/*  285 */     jPanel2.add(jButton2);
/*  286 */     jPanel2.add(Box.createRigidArea(hstrut5));
/*      */ 
/*      */     
/*  289 */     if (!UIManager.getBoolean("FileChooser.readOnly")) {
/*  290 */       jButton2 = new JButton(this.filePane.getNewFolderAction());
/*  291 */       jButton2.setText((String)null);
/*  292 */       jButton2.setIcon(this.newFolderIcon);
/*  293 */       jButton2.setToolTipText(this.newFolderToolTipText);
/*  294 */       jButton2.putClientProperty("AccessibleName", this.newFolderAccessibleName);
/*      */       
/*  296 */       jButton2.setAlignmentX(0.0F);
/*  297 */       jButton2.setAlignmentY(0.5F);
/*  298 */       jButton2.setMargin(shrinkwrap);
/*      */     } 
/*  300 */     jPanel2.add(jButton2);
/*  301 */     jPanel2.add(Box.createRigidArea(hstrut5));
/*      */ 
/*      */     
/*  304 */     ButtonGroup buttonGroup = new ButtonGroup();
/*      */ 
/*      */     
/*  307 */     this.listViewButton = new JToggleButton(this.listViewIcon);
/*  308 */     this.listViewButton.setToolTipText(this.listViewButtonToolTipText);
/*  309 */     this.listViewButton.putClientProperty("AccessibleName", this.listViewButtonAccessibleName);
/*      */     
/*  311 */     this.listViewButton.setSelected(true);
/*  312 */     this.listViewButton.setAlignmentX(0.0F);
/*  313 */     this.listViewButton.setAlignmentY(0.5F);
/*  314 */     this.listViewButton.setMargin(shrinkwrap);
/*  315 */     this.listViewButton.addActionListener(this.filePane.getViewTypeAction(0));
/*  316 */     jPanel2.add(this.listViewButton);
/*  317 */     buttonGroup.add(this.listViewButton);
/*      */ 
/*      */     
/*  320 */     this.detailsViewButton = new JToggleButton(this.detailsViewIcon);
/*  321 */     this.detailsViewButton.setToolTipText(this.detailsViewButtonToolTipText);
/*  322 */     this.detailsViewButton.putClientProperty("AccessibleName", this.detailsViewButtonAccessibleName);
/*      */     
/*  324 */     this.detailsViewButton.setAlignmentX(0.0F);
/*  325 */     this.detailsViewButton.setAlignmentY(0.5F);
/*  326 */     this.detailsViewButton.setMargin(shrinkwrap);
/*  327 */     this.detailsViewButton.addActionListener(this.filePane.getViewTypeAction(1));
/*  328 */     jPanel2.add(this.detailsViewButton);
/*  329 */     buttonGroup.add(this.detailsViewButton);
/*      */     
/*  331 */     this.filePane.addPropertyChangeListener(new PropertyChangeListener() {
/*      */           public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  333 */             if ("viewType".equals(param1PropertyChangeEvent.getPropertyName())) {
/*  334 */               int i = MetalFileChooserUI.this.filePane.getViewType();
/*  335 */               switch (i) {
/*      */                 case 0:
/*  337 */                   MetalFileChooserUI.this.listViewButton.setSelected(true);
/*      */                   break;
/*      */                 
/*      */                 case 1:
/*  341 */                   MetalFileChooserUI.this.detailsViewButton.setSelected(true);
/*      */                   break;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             } 
/*      */           }
/*      */         });
/*  351 */     paramJFileChooser.add(getAccessoryPanel(), "After");
/*  352 */     JComponent jComponent = paramJFileChooser.getAccessory();
/*  353 */     if (jComponent != null) {
/*  354 */       getAccessoryPanel().add(jComponent);
/*      */     }
/*  356 */     this.filePane.setPreferredSize(LIST_PREF_SIZE);
/*  357 */     paramJFileChooser.add(this.filePane, "Center");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  362 */     JPanel jPanel3 = getBottomPanel();
/*  363 */     jPanel3.setLayout(new BoxLayout(jPanel3, 1));
/*  364 */     paramJFileChooser.add(jPanel3, "South");
/*      */ 
/*      */     
/*  367 */     JPanel jPanel4 = new JPanel();
/*  368 */     jPanel4.setLayout(new BoxLayout(jPanel4, 2));
/*  369 */     jPanel3.add(jPanel4);
/*  370 */     jPanel3.add(Box.createRigidArea(vstrut5));
/*      */     
/*  372 */     this.fileNameLabel = new AlignedLabel();
/*  373 */     populateFileNameLabel();
/*  374 */     jPanel4.add(this.fileNameLabel);
/*      */     
/*  376 */     this.fileNameTextField = new JTextField(35) {
/*      */         public Dimension getMaximumSize() {
/*  378 */           return new Dimension(32767, (getPreferredSize()).height);
/*      */         }
/*      */       };
/*  381 */     jPanel4.add(this.fileNameTextField);
/*  382 */     this.fileNameLabel.setLabelFor(this.fileNameTextField);
/*  383 */     this.fileNameTextField.addFocusListener(new FocusAdapter()
/*      */         {
/*      */           public void focusGained(FocusEvent param1FocusEvent) {
/*  386 */             if (!MetalFileChooserUI.this.getFileChooser().isMultiSelectionEnabled()) {
/*  387 */               MetalFileChooserUI.this.filePane.clearSelection();
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  392 */     if (paramJFileChooser.isMultiSelectionEnabled()) {
/*  393 */       setFileName(fileNameString(paramJFileChooser.getSelectedFiles()));
/*      */     } else {
/*  395 */       setFileName(fileNameString(paramJFileChooser.getSelectedFile()));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  400 */     JPanel jPanel5 = new JPanel();
/*  401 */     jPanel5.setLayout(new BoxLayout(jPanel5, 2));
/*  402 */     jPanel3.add(jPanel5);
/*      */     
/*  404 */     AlignedLabel alignedLabel = new AlignedLabel(this.filesOfTypeLabelText);
/*  405 */     alignedLabel.setDisplayedMnemonic(this.filesOfTypeLabelMnemonic);
/*  406 */     jPanel5.add(alignedLabel);
/*      */     
/*  408 */     this.filterComboBoxModel = createFilterComboBoxModel();
/*  409 */     paramJFileChooser.addPropertyChangeListener(this.filterComboBoxModel);
/*  410 */     this.filterComboBox = new JComboBox(this.filterComboBoxModel);
/*  411 */     this.filterComboBox.putClientProperty("AccessibleDescription", this.filesOfTypeLabelText);
/*      */     
/*  413 */     alignedLabel.setLabelFor(this.filterComboBox);
/*  414 */     this.filterComboBox.setRenderer(createFilterComboBoxRenderer());
/*  415 */     jPanel5.add(this.filterComboBox);
/*      */ 
/*      */     
/*  418 */     getButtonPanel().setLayout(new ButtonAreaLayout());
/*      */     
/*  420 */     this.approveButton = new JButton(getApproveButtonText(paramJFileChooser));
/*      */     
/*  422 */     this.approveButton.addActionListener(getApproveSelectionAction());
/*  423 */     this.approveButton.setToolTipText(getApproveButtonToolTipText(paramJFileChooser));
/*  424 */     getButtonPanel().add(this.approveButton);
/*      */     
/*  426 */     this.cancelButton = new JButton(this.cancelButtonText);
/*  427 */     this.cancelButton.setToolTipText(this.cancelButtonToolTipText);
/*  428 */     this.cancelButton.addActionListener(getCancelSelectionAction());
/*  429 */     getButtonPanel().add(this.cancelButton);
/*      */     
/*  431 */     if (paramJFileChooser.getControlButtonsAreShown()) {
/*  432 */       addControlButtons();
/*      */     }
/*      */     
/*  435 */     groupLabels(new AlignedLabel[] { this.fileNameLabel, alignedLabel });
/*      */   }
/*      */   
/*      */   protected JPanel getButtonPanel() {
/*  439 */     if (this.buttonPanel == null) {
/*  440 */       this.buttonPanel = new JPanel();
/*      */     }
/*  442 */     return this.buttonPanel;
/*      */   }
/*      */   
/*      */   protected JPanel getBottomPanel() {
/*  446 */     if (this.bottomPanel == null) {
/*  447 */       this.bottomPanel = new JPanel();
/*      */     }
/*  449 */     return this.bottomPanel;
/*      */   }
/*      */   
/*      */   protected void installStrings(JFileChooser paramJFileChooser) {
/*  453 */     super.installStrings(paramJFileChooser);
/*      */     
/*  455 */     Locale locale = paramJFileChooser.getLocale();
/*      */     
/*  457 */     this.lookInLabelMnemonic = getMnemonic("FileChooser.lookInLabelMnemonic", locale).intValue();
/*  458 */     this.lookInLabelText = UIManager.getString("FileChooser.lookInLabelText", locale);
/*  459 */     this.saveInLabelText = UIManager.getString("FileChooser.saveInLabelText", locale);
/*      */     
/*  461 */     this.fileNameLabelMnemonic = getMnemonic("FileChooser.fileNameLabelMnemonic", locale).intValue();
/*  462 */     this.fileNameLabelText = UIManager.getString("FileChooser.fileNameLabelText", locale);
/*  463 */     this.folderNameLabelMnemonic = getMnemonic("FileChooser.folderNameLabelMnemonic", locale).intValue();
/*  464 */     this.folderNameLabelText = UIManager.getString("FileChooser.folderNameLabelText", locale);
/*      */     
/*  466 */     this.filesOfTypeLabelMnemonic = getMnemonic("FileChooser.filesOfTypeLabelMnemonic", locale).intValue();
/*  467 */     this.filesOfTypeLabelText = UIManager.getString("FileChooser.filesOfTypeLabelText", locale);
/*      */     
/*  469 */     this.upFolderToolTipText = UIManager.getString("FileChooser.upFolderToolTipText", locale);
/*  470 */     this.upFolderAccessibleName = UIManager.getString("FileChooser.upFolderAccessibleName", locale);
/*      */     
/*  472 */     this.homeFolderToolTipText = UIManager.getString("FileChooser.homeFolderToolTipText", locale);
/*  473 */     this.homeFolderAccessibleName = UIManager.getString("FileChooser.homeFolderAccessibleName", locale);
/*      */     
/*  475 */     this.newFolderToolTipText = UIManager.getString("FileChooser.newFolderToolTipText", locale);
/*  476 */     this.newFolderAccessibleName = UIManager.getString("FileChooser.newFolderAccessibleName", locale);
/*      */     
/*  478 */     this.listViewButtonToolTipText = UIManager.getString("FileChooser.listViewButtonToolTipText", locale);
/*  479 */     this.listViewButtonAccessibleName = UIManager.getString("FileChooser.listViewButtonAccessibleName", locale);
/*      */     
/*  481 */     this.detailsViewButtonToolTipText = UIManager.getString("FileChooser.detailsViewButtonToolTipText", locale);
/*  482 */     this.detailsViewButtonAccessibleName = UIManager.getString("FileChooser.detailsViewButtonAccessibleName", locale);
/*      */   }
/*      */   
/*      */   private Integer getMnemonic(String paramString, Locale paramLocale) {
/*  486 */     return Integer.valueOf(SwingUtilities2.getUIDefaultsInt(paramString, paramLocale));
/*      */   }
/*      */   
/*      */   protected void installListeners(JFileChooser paramJFileChooser) {
/*  490 */     super.installListeners(paramJFileChooser);
/*  491 */     ActionMap actionMap = getActionMap();
/*  492 */     SwingUtilities.replaceUIActionMap(paramJFileChooser, actionMap);
/*      */   }
/*      */   
/*      */   protected ActionMap getActionMap() {
/*  496 */     return createActionMap();
/*      */   }
/*      */   
/*      */   protected ActionMap createActionMap() {
/*  500 */     ActionMapUIResource actionMapUIResource = new ActionMapUIResource();
/*  501 */     FilePane.addActionsToMap(actionMapUIResource, this.filePane.getActions());
/*  502 */     return actionMapUIResource;
/*      */   }
/*      */   
/*      */   protected JPanel createList(JFileChooser paramJFileChooser) {
/*  506 */     return this.filePane.createList();
/*      */   }
/*      */   
/*      */   protected JPanel createDetailsView(JFileChooser paramJFileChooser) {
/*  510 */     return this.filePane.createDetailsView();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListSelectionListener createListSelectionListener(JFileChooser paramJFileChooser) {
/*  520 */     return super.createListSelectionListener(paramJFileChooser);
/*      */   }
/*      */ 
/*      */   
/*      */   protected class SingleClickListener
/*      */     extends MouseAdapter
/*      */   {
/*      */     public SingleClickListener(JList param1JList) {}
/*      */   }
/*      */ 
/*      */   
/*      */   protected class FileRenderer
/*      */     extends DefaultListCellRenderer {}
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  535 */     paramJComponent.removePropertyChangeListener(this.filterComboBoxModel);
/*  536 */     paramJComponent.removePropertyChangeListener(this.filePane);
/*  537 */     this.cancelButton.removeActionListener(getCancelSelectionAction());
/*  538 */     this.approveButton.removeActionListener(getApproveSelectionAction());
/*  539 */     this.fileNameTextField.removeActionListener(getApproveSelectionAction());
/*      */     
/*  541 */     if (this.filePane != null) {
/*  542 */       this.filePane.uninstallUI();
/*  543 */       this.filePane = null;
/*      */     } 
/*      */     
/*  546 */     super.uninstallUI(paramJComponent);
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
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*  563 */     int i = PREF_SIZE.width;
/*  564 */     Dimension dimension = paramJComponent.getLayout().preferredLayoutSize(paramJComponent);
/*  565 */     if (dimension != null) {
/*  566 */       return new Dimension((dimension.width < i) ? i : dimension.width, (dimension.height < PREF_SIZE.height) ? PREF_SIZE.height : dimension.height);
/*      */     }
/*      */     
/*  569 */     return new Dimension(i, PREF_SIZE.height);
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
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/*  582 */     return new Dimension(MIN_WIDTH, MIN_HEIGHT);
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
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/*  594 */     return new Dimension(2147483647, 2147483647);
/*      */   }
/*      */   
/*      */   private String fileNameString(File paramFile) {
/*  598 */     if (paramFile == null) {
/*  599 */       return null;
/*      */     }
/*  601 */     JFileChooser jFileChooser = getFileChooser();
/*  602 */     if ((jFileChooser.isDirectorySelectionEnabled() && !jFileChooser.isFileSelectionEnabled()) || (jFileChooser
/*  603 */       .isDirectorySelectionEnabled() && jFileChooser.isFileSelectionEnabled() && jFileChooser
/*  604 */       .getFileSystemView().isFileSystemRoot(paramFile))) {
/*  605 */       return paramFile.getPath();
/*      */     }
/*  607 */     return paramFile.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String fileNameString(File[] paramArrayOfFile) {
/*  613 */     StringBuffer stringBuffer = new StringBuffer();
/*  614 */     for (byte b = 0; paramArrayOfFile != null && b < paramArrayOfFile.length; b++) {
/*  615 */       if (b > 0) {
/*  616 */         stringBuffer.append(" ");
/*      */       }
/*  618 */       if (paramArrayOfFile.length > 1) {
/*  619 */         stringBuffer.append("\"");
/*      */       }
/*  621 */       stringBuffer.append(fileNameString(paramArrayOfFile[b]));
/*  622 */       if (paramArrayOfFile.length > 1) {
/*  623 */         stringBuffer.append("\"");
/*      */       }
/*      */     } 
/*  626 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void doSelectedFileChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  632 */     File file = (File)paramPropertyChangeEvent.getNewValue();
/*  633 */     JFileChooser jFileChooser = getFileChooser();
/*  634 */     if (file != null && ((jFileChooser
/*  635 */       .isFileSelectionEnabled() && !file.isDirectory()) || (file
/*  636 */       .isDirectory() && jFileChooser.isDirectorySelectionEnabled())))
/*      */     {
/*  638 */       setFileName(fileNameString(file));
/*      */     }
/*      */   }
/*      */   
/*      */   private void doSelectedFilesChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  643 */     File[] arrayOfFile = (File[])paramPropertyChangeEvent.getNewValue();
/*  644 */     JFileChooser jFileChooser = getFileChooser();
/*  645 */     if (arrayOfFile != null && arrayOfFile.length > 0 && (arrayOfFile.length > 1 || jFileChooser
/*      */       
/*  647 */       .isDirectorySelectionEnabled() || !arrayOfFile[0].isDirectory())) {
/*  648 */       setFileName(fileNameString(arrayOfFile));
/*      */     }
/*      */   }
/*      */   
/*      */   private void doDirectoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  653 */     JFileChooser jFileChooser = getFileChooser();
/*  654 */     FileSystemView fileSystemView = jFileChooser.getFileSystemView();
/*      */     
/*  656 */     clearIconCache();
/*  657 */     File file = jFileChooser.getCurrentDirectory();
/*  658 */     if (file != null) {
/*  659 */       this.directoryComboBoxModel.addItem(file);
/*      */       
/*  661 */       if (jFileChooser.isDirectorySelectionEnabled() && !jFileChooser.isFileSelectionEnabled()) {
/*  662 */         if (fileSystemView.isFileSystem(file)) {
/*  663 */           setFileName(file.getPath());
/*      */         } else {
/*  665 */           setFileName((String)null);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doFilterChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  672 */     clearIconCache();
/*      */   }
/*      */   
/*      */   private void doFileSelectionModeChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  676 */     if (this.fileNameLabel != null) {
/*  677 */       populateFileNameLabel();
/*      */     }
/*  679 */     clearIconCache();
/*      */     
/*  681 */     JFileChooser jFileChooser = getFileChooser();
/*  682 */     File file = jFileChooser.getCurrentDirectory();
/*  683 */     if (file != null && jFileChooser
/*  684 */       .isDirectorySelectionEnabled() && 
/*  685 */       !jFileChooser.isFileSelectionEnabled() && jFileChooser
/*  686 */       .getFileSystemView().isFileSystem(file)) {
/*      */       
/*  688 */       setFileName(file.getPath());
/*      */     } else {
/*  690 */       setFileName((String)null);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doAccessoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  695 */     if (getAccessoryPanel() != null) {
/*  696 */       if (paramPropertyChangeEvent.getOldValue() != null) {
/*  697 */         getAccessoryPanel().remove((JComponent)paramPropertyChangeEvent.getOldValue());
/*      */       }
/*  699 */       JComponent jComponent = (JComponent)paramPropertyChangeEvent.getNewValue();
/*  700 */       if (jComponent != null) {
/*  701 */         getAccessoryPanel().add(jComponent, "Center");
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doApproveButtonTextChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  707 */     JFileChooser jFileChooser = getFileChooser();
/*  708 */     this.approveButton.setText(getApproveButtonText(jFileChooser));
/*  709 */     this.approveButton.setToolTipText(getApproveButtonToolTipText(jFileChooser));
/*      */   }
/*      */   
/*      */   private void doDialogTypeChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  713 */     JFileChooser jFileChooser = getFileChooser();
/*  714 */     this.approveButton.setText(getApproveButtonText(jFileChooser));
/*  715 */     this.approveButton.setToolTipText(getApproveButtonToolTipText(jFileChooser));
/*  716 */     if (jFileChooser.getDialogType() == 1) {
/*  717 */       this.lookInLabel.setText(this.saveInLabelText);
/*      */     } else {
/*  719 */       this.lookInLabel.setText(this.lookInLabelText);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void doApproveButtonMnemonicChanged(PropertyChangeEvent paramPropertyChangeEvent) {}
/*      */ 
/*      */   
/*      */   private void doControlButtonsChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  728 */     if (getFileChooser().getControlButtonsAreShown()) {
/*  729 */       addControlButtons();
/*      */     } else {
/*  731 */       removeControlButtons();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PropertyChangeListener createPropertyChangeListener(JFileChooser paramJFileChooser) {
/*  740 */     return new PropertyChangeListener() {
/*      */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  742 */           String str = param1PropertyChangeEvent.getPropertyName();
/*  743 */           if (str.equals("SelectedFileChangedProperty")) {
/*  744 */             MetalFileChooserUI.this.doSelectedFileChanged(param1PropertyChangeEvent);
/*  745 */           } else if (str.equals("SelectedFilesChangedProperty")) {
/*  746 */             MetalFileChooserUI.this.doSelectedFilesChanged(param1PropertyChangeEvent);
/*  747 */           } else if (str.equals("directoryChanged")) {
/*  748 */             MetalFileChooserUI.this.doDirectoryChanged(param1PropertyChangeEvent);
/*  749 */           } else if (str.equals("fileFilterChanged")) {
/*  750 */             MetalFileChooserUI.this.doFilterChanged(param1PropertyChangeEvent);
/*  751 */           } else if (str.equals("fileSelectionChanged")) {
/*  752 */             MetalFileChooserUI.this.doFileSelectionModeChanged(param1PropertyChangeEvent);
/*  753 */           } else if (str.equals("AccessoryChangedProperty")) {
/*  754 */             MetalFileChooserUI.this.doAccessoryChanged(param1PropertyChangeEvent);
/*  755 */           } else if (str.equals("ApproveButtonTextChangedProperty") || str
/*  756 */             .equals("ApproveButtonToolTipTextChangedProperty")) {
/*  757 */             MetalFileChooserUI.this.doApproveButtonTextChanged(param1PropertyChangeEvent);
/*  758 */           } else if (str.equals("DialogTypeChangedProperty")) {
/*  759 */             MetalFileChooserUI.this.doDialogTypeChanged(param1PropertyChangeEvent);
/*  760 */           } else if (str.equals("ApproveButtonMnemonicChangedProperty")) {
/*  761 */             MetalFileChooserUI.this.doApproveButtonMnemonicChanged(param1PropertyChangeEvent);
/*  762 */           } else if (str.equals("ControlButtonsAreShownChangedProperty")) {
/*  763 */             MetalFileChooserUI.this.doControlButtonsChanged(param1PropertyChangeEvent);
/*  764 */           } else if (str.equals("componentOrientation")) {
/*  765 */             ComponentOrientation componentOrientation = (ComponentOrientation)param1PropertyChangeEvent.getNewValue();
/*  766 */             JFileChooser jFileChooser = (JFileChooser)param1PropertyChangeEvent.getSource();
/*  767 */             if (componentOrientation != param1PropertyChangeEvent.getOldValue()) {
/*  768 */               jFileChooser.applyComponentOrientation(componentOrientation);
/*      */             }
/*  770 */           } else if (str == "FileChooser.useShellFolder") {
/*  771 */             MetalFileChooserUI.this.doDirectoryChanged(param1PropertyChangeEvent);
/*  772 */           } else if (str.equals("ancestor") && 
/*  773 */             param1PropertyChangeEvent.getOldValue() == null && param1PropertyChangeEvent.getNewValue() != null) {
/*      */             
/*  775 */             MetalFileChooserUI.this.fileNameTextField.selectAll();
/*  776 */             MetalFileChooserUI.this.fileNameTextField.requestFocus();
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeControlButtons() {
/*  785 */     getBottomPanel().remove(getButtonPanel());
/*      */   }
/*      */   
/*      */   protected void addControlButtons() {
/*  789 */     getBottomPanel().add(getButtonPanel());
/*      */   }
/*      */   
/*      */   public void ensureFileIsVisible(JFileChooser paramJFileChooser, File paramFile) {
/*  793 */     this.filePane.ensureFileIsVisible(paramJFileChooser, paramFile);
/*      */   }
/*      */   
/*      */   public void rescanCurrentDirectory(JFileChooser paramJFileChooser) {
/*  797 */     this.filePane.rescanCurrentDirectory();
/*      */   }
/*      */   
/*      */   public String getFileName() {
/*  801 */     if (this.fileNameTextField != null) {
/*  802 */       return this.fileNameTextField.getText();
/*      */     }
/*  804 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFileName(String paramString) {
/*  809 */     if (this.fileNameTextField != null) {
/*  810 */       this.fileNameTextField.setText(paramString);
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
/*      */   protected void setDirectorySelected(boolean paramBoolean) {
/*  822 */     super.setDirectorySelected(paramBoolean);
/*  823 */     JFileChooser jFileChooser = getFileChooser();
/*  824 */     if (paramBoolean) {
/*  825 */       if (this.approveButton != null) {
/*  826 */         this.approveButton.setText(this.directoryOpenButtonText);
/*  827 */         this.approveButton.setToolTipText(this.directoryOpenButtonToolTipText);
/*      */       }
/*      */     
/*  830 */     } else if (this.approveButton != null) {
/*  831 */       this.approveButton.setText(getApproveButtonText(jFileChooser));
/*  832 */       this.approveButton.setToolTipText(getApproveButtonToolTipText(jFileChooser));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDirectoryName() {
/*  839 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDirectoryName(String paramString) {}
/*      */ 
/*      */   
/*      */   protected DirectoryComboBoxRenderer createDirectoryComboBoxRenderer(JFileChooser paramJFileChooser) {
/*  847 */     return new DirectoryComboBoxRenderer();
/*      */   }
/*      */ 
/*      */   
/*      */   class DirectoryComboBoxRenderer
/*      */     extends DefaultListCellRenderer
/*      */   {
/*  854 */     MetalFileChooserUI.IndentIcon ii = new MetalFileChooserUI.IndentIcon();
/*      */ 
/*      */ 
/*      */     
/*      */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/*  859 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/*      */       
/*  861 */       if (param1Object == null) {
/*  862 */         setText("");
/*  863 */         return this;
/*      */       } 
/*  865 */       File file = (File)param1Object;
/*  866 */       setText(MetalFileChooserUI.this.getFileChooser().getName(file));
/*  867 */       Icon icon = MetalFileChooserUI.this.getFileChooser().getIcon(file);
/*  868 */       this.ii.icon = icon;
/*  869 */       this.ii.depth = MetalFileChooserUI.this.directoryComboBoxModel.getDepth(param1Int);
/*  870 */       setIcon(this.ii);
/*      */       
/*  872 */       return this;
/*      */     }
/*      */   }
/*      */   
/*      */   class IndentIcon
/*      */     implements Icon
/*      */   {
/*  879 */     Icon icon = null;
/*  880 */     int depth = 0;
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  883 */       if (param1Component.getComponentOrientation().isLeftToRight()) {
/*  884 */         this.icon.paintIcon(param1Component, param1Graphics, param1Int1 + this.depth * 10, param1Int2);
/*      */       } else {
/*  886 */         this.icon.paintIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/*  891 */       return this.icon.getIconWidth() + this.depth * 10;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/*  895 */       return this.icon.getIconHeight();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DirectoryComboBoxModel createDirectoryComboBoxModel(JFileChooser paramJFileChooser) {
/*  904 */     return new DirectoryComboBoxModel();
/*      */   }
/*      */   
/*      */   protected class DirectoryComboBoxModel
/*      */     extends AbstractListModel<Object>
/*      */     implements ComboBoxModel<Object>
/*      */   {
/*  911 */     Vector<File> directories = new Vector<>();
/*  912 */     int[] depths = null;
/*  913 */     File selectedDirectory = null;
/*  914 */     JFileChooser chooser = MetalFileChooserUI.this.getFileChooser();
/*  915 */     FileSystemView fsv = this.chooser.getFileSystemView();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */       File file;
/*  933 */       if (param1File == null) {
/*      */         return;
/*      */       }
/*      */       
/*  937 */       boolean bool = FilePane.usesShellFolder(this.chooser);
/*      */       
/*  939 */       this.directories.clear();
/*      */ 
/*      */ 
/*      */       
/*  943 */       File[] arrayOfFile = bool ? (File[])ShellFolder.get("fileChooserComboBoxFolders") : this.fsv.getRoots();
/*  944 */       this.directories.addAll(Arrays.asList(arrayOfFile));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  951 */         file = ShellFolder.getNormalizedFile(param1File);
/*  952 */       } catch (IOException iOException) {
/*      */         
/*  954 */         file = param1File;
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  959 */         File file1 = bool ? ShellFolder.getShellFolder(file) : file;
/*      */         
/*  961 */         File file2 = file1;
/*  962 */         Vector<File> vector = new Vector(10);
/*      */         do {
/*  964 */           vector.addElement(file2);
/*  965 */         } while ((file2 = file2.getParentFile()) != null);
/*      */         
/*  967 */         int i = vector.size();
/*      */         
/*  969 */         for (byte b = 0; b < i; b++) {
/*  970 */           file2 = vector.get(b);
/*  971 */           if (this.directories.contains(file2)) {
/*  972 */             int j = this.directories.indexOf(file2);
/*  973 */             for (int k = b - 1; k >= 0; k--) {
/*  974 */               this.directories.insertElementAt(vector.get(k), j + b - k);
/*      */             }
/*      */             break;
/*      */           } 
/*      */         } 
/*  979 */         calculateDepths();
/*  980 */         setSelectedItem(file1);
/*  981 */       } catch (FileNotFoundException fileNotFoundException) {
/*  982 */         calculateDepths();
/*      */       } 
/*      */     }
/*      */     
/*      */     private void calculateDepths() {
/*  987 */       this.depths = new int[this.directories.size()];
/*  988 */       for (byte b = 0; b < this.depths.length; b++) {
/*  989 */         File file1 = this.directories.get(b);
/*  990 */         File file2 = file1.getParentFile();
/*  991 */         this.depths[b] = 0;
/*  992 */         if (file2 != null) {
/*  993 */           for (int i = b - 1; i >= 0; i--) {
/*  994 */             if (file2.equals(this.directories.get(i))) {
/*  995 */               this.depths[b] = this.depths[i] + 1;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public int getDepth(int param1Int) {
/* 1004 */       return (this.depths != null && param1Int >= 0 && param1Int < this.depths.length) ? this.depths[param1Int] : 0;
/*      */     }
/*      */     
/*      */     public void setSelectedItem(Object param1Object) {
/* 1008 */       this.selectedDirectory = (File)param1Object;
/* 1009 */       fireContentsChanged(this, -1, -1);
/*      */     }
/*      */     
/*      */     public Object getSelectedItem() {
/* 1013 */       return this.selectedDirectory;
/*      */     }
/*      */     
/*      */     public int getSize() {
/* 1017 */       return this.directories.size();
/*      */     } public DirectoryComboBoxModel() { File file = MetalFileChooserUI.this.getFileChooser().getCurrentDirectory();
/*      */       if (file != null)
/*      */         addItem(file);  } public Object getElementAt(int param1Int) {
/* 1021 */       return this.directories.elementAt(param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FilterComboBoxRenderer createFilterComboBoxRenderer() {
/* 1029 */     return new FilterComboBoxRenderer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class FilterComboBoxRenderer
/*      */     extends DefaultListCellRenderer
/*      */   {
/*      */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 1040 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/*      */       
/* 1042 */       if (param1Object != null && param1Object instanceof FileFilter) {
/* 1043 */         setText(((FileFilter)param1Object).getDescription());
/*      */       }
/*      */       
/* 1046 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FilterComboBoxModel createFilterComboBoxModel() {
/* 1054 */     return new FilterComboBoxModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class FilterComboBoxModel
/*      */     extends AbstractListModel<Object>
/*      */     implements ComboBoxModel<Object>, PropertyChangeListener
/*      */   {
/* 1064 */     protected FileFilter[] filters = MetalFileChooserUI.this.getFileChooser().getChoosableFileFilters();
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1068 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1069 */       if (str == "ChoosableFileFilterChangedProperty") {
/* 1070 */         this.filters = (FileFilter[])param1PropertyChangeEvent.getNewValue();
/* 1071 */         fireContentsChanged(this, -1, -1);
/* 1072 */       } else if (str == "fileFilterChanged") {
/* 1073 */         fireContentsChanged(this, -1, -1);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void setSelectedItem(Object param1Object) {
/* 1078 */       if (param1Object != null) {
/* 1079 */         MetalFileChooserUI.this.getFileChooser().setFileFilter((FileFilter)param1Object);
/* 1080 */         fireContentsChanged(this, -1, -1);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getSelectedItem() {
/* 1090 */       FileFilter fileFilter = MetalFileChooserUI.this.getFileChooser().getFileFilter();
/* 1091 */       boolean bool = false;
/* 1092 */       if (fileFilter != null) {
/* 1093 */         for (FileFilter fileFilter1 : this.filters) {
/* 1094 */           if (fileFilter1 == fileFilter) {
/* 1095 */             bool = true;
/*      */           }
/*      */         } 
/* 1098 */         if (!bool) {
/* 1099 */           MetalFileChooserUI.this.getFileChooser().addChoosableFileFilter(fileFilter);
/*      */         }
/*      */       } 
/* 1102 */       return MetalFileChooserUI.this.getFileChooser().getFileFilter();
/*      */     }
/*      */     
/*      */     public int getSize() {
/* 1106 */       if (this.filters != null) {
/* 1107 */         return this.filters.length;
/*      */       }
/* 1109 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object getElementAt(int param1Int) {
/* 1114 */       if (param1Int > getSize() - 1)
/*      */       {
/* 1116 */         return MetalFileChooserUI.this.getFileChooser().getFileFilter();
/*      */       }
/* 1118 */       if (this.filters != null) {
/* 1119 */         return this.filters[param1Int];
/*      */       }
/* 1121 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void valueChanged(ListSelectionEvent paramListSelectionEvent) {
/* 1127 */     JFileChooser jFileChooser = getFileChooser();
/* 1128 */     File file = jFileChooser.getSelectedFile();
/* 1129 */     if (!paramListSelectionEvent.getValueIsAdjusting() && file != null && !getFileChooser().isTraversable(file)) {
/* 1130 */       setFileName(fileNameString(file));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected class DirectoryComboBoxAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected DirectoryComboBoxAction() {
/* 1139 */       super("DirectoryComboBoxAction");
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1143 */       MetalFileChooserUI.this.directoryComboBox.hidePopup();
/* 1144 */       File file = (File)MetalFileChooserUI.this.directoryComboBox.getSelectedItem();
/* 1145 */       if (!MetalFileChooserUI.this.getFileChooser().getCurrentDirectory().equals(file)) {
/* 1146 */         MetalFileChooserUI.this.getFileChooser().setCurrentDirectory(file);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected JButton getApproveButton(JFileChooser paramJFileChooser) {
/* 1152 */     return this.approveButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ButtonAreaLayout
/*      */     implements LayoutManager
/*      */   {
/* 1163 */     private int hGap = 5;
/* 1164 */     private int topMargin = 17;
/*      */ 
/*      */     
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/* 1170 */       Component[] arrayOfComponent = param1Container.getComponents();
/*      */       
/* 1172 */       if (arrayOfComponent != null && arrayOfComponent.length > 0) {
/* 1173 */         int n, i = arrayOfComponent.length;
/* 1174 */         Dimension[] arrayOfDimension = new Dimension[i];
/* 1175 */         Insets insets = param1Container.getInsets();
/* 1176 */         int j = insets.top + this.topMargin;
/* 1177 */         int k = 0;
/*      */         int m;
/* 1179 */         for (m = 0; m < i; m++) {
/* 1180 */           arrayOfDimension[m] = arrayOfComponent[m].getPreferredSize();
/* 1181 */           k = Math.max(k, (arrayOfDimension[m]).width);
/*      */         } 
/*      */         
/* 1184 */         if (param1Container.getComponentOrientation().isLeftToRight()) {
/* 1185 */           m = (param1Container.getSize()).width - insets.left - k;
/* 1186 */           n = this.hGap + k;
/*      */         } else {
/* 1188 */           m = insets.left;
/* 1189 */           n = -(this.hGap + k);
/*      */         } 
/* 1191 */         for (int i1 = i - 1; i1 >= 0; i1--) {
/* 1192 */           arrayOfComponent[i1].setBounds(m, j, k, (arrayOfDimension[i1]).height);
/*      */           
/* 1194 */           m -= n;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/* 1200 */       if (param1Container != null) {
/* 1201 */         Component[] arrayOfComponent = param1Container.getComponents();
/*      */         
/* 1203 */         if (arrayOfComponent != null && arrayOfComponent.length > 0) {
/* 1204 */           int i = arrayOfComponent.length;
/* 1205 */           int j = 0;
/* 1206 */           Insets insets = param1Container.getInsets();
/* 1207 */           int k = this.topMargin + insets.top + insets.bottom;
/* 1208 */           int m = insets.left + insets.right;
/* 1209 */           int n = 0;
/*      */           
/* 1211 */           for (byte b = 0; b < i; b++) {
/* 1212 */             Dimension dimension = arrayOfComponent[b].getPreferredSize();
/* 1213 */             j = Math.max(j, dimension.height);
/* 1214 */             n = Math.max(n, dimension.width);
/*      */           } 
/* 1216 */           return new Dimension(m + i * n + (i - 1) * this.hGap, k + j);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1221 */       return new Dimension(0, 0);
/*      */     }
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/* 1225 */       return minimumLayoutSize(param1Container);
/*      */     }
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */     
/*      */     private ButtonAreaLayout() {} }
/*      */   
/*      */   private static void groupLabels(AlignedLabel[] paramArrayOfAlignedLabel) {
/* 1232 */     for (byte b = 0; b < paramArrayOfAlignedLabel.length; b++)
/* 1233 */       (paramArrayOfAlignedLabel[b]).group = paramArrayOfAlignedLabel; 
/*      */   }
/*      */   
/*      */   private class AlignedLabel
/*      */     extends JLabel {
/*      */     private AlignedLabel[] group;
/* 1239 */     private int maxWidth = 0;
/*      */ 
/*      */     
/*      */     AlignedLabel() {
/* 1243 */       setAlignmentX(0.0F);
/*      */     }
/*      */ 
/*      */     
/*      */     AlignedLabel(String param1String) {
/* 1248 */       super(param1String);
/* 1249 */       setAlignmentX(0.0F);
/*      */     }
/*      */     
/*      */     public Dimension getPreferredSize() {
/* 1253 */       Dimension dimension = super.getPreferredSize();
/*      */       
/* 1255 */       return new Dimension(getMaxWidth() + 11, dimension.height);
/*      */     }
/*      */     
/*      */     private int getMaxWidth() {
/* 1259 */       if (this.maxWidth == 0 && this.group != null) {
/* 1260 */         int i = 0; byte b;
/* 1261 */         for (b = 0; b < this.group.length; b++) {
/* 1262 */           i = Math.max(this.group[b].getSuperPreferredWidth(), i);
/*      */         }
/* 1264 */         for (b = 0; b < this.group.length; b++) {
/* 1265 */           (this.group[b]).maxWidth = i;
/*      */         }
/*      */       } 
/* 1268 */       return this.maxWidth;
/*      */     }
/*      */     
/*      */     private int getSuperPreferredWidth() {
/* 1272 */       return (super.getPreferredSize()).width;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalFileChooserUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */