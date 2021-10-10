/*      */ package com.sun.java.swing.plaf.windows;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.KeyAdapter;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.File;
/*      */ import java.util.Arrays;
/*      */ import java.util.Locale;
/*      */ import java.util.Vector;
/*      */ import javax.swing.AbstractListModel;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.ComboBoxModel;
/*      */ import javax.swing.DefaultButtonModel;
/*      */ import javax.swing.DefaultListCellRenderer;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JRadioButtonMenuItem;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.JToolBar;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.event.PopupMenuEvent;
/*      */ import javax.swing.event.PopupMenuListener;
/*      */ import javax.swing.filechooser.FileFilter;
/*      */ import javax.swing.filechooser.FileSystemView;
/*      */ import javax.swing.filechooser.FileView;
/*      */ import javax.swing.plaf.ActionMapUIResource;
/*      */ import javax.swing.plaf.InsetsUIResource;
/*      */ import javax.swing.plaf.basic.BasicDirectoryModel;
/*      */ import javax.swing.plaf.basic.BasicFileChooserUI;
/*      */ import sun.awt.shell.ShellFolder;
/*      */ import sun.swing.FilePane;
/*      */ import sun.swing.SwingUtilities2;
/*      */ import sun.swing.WindowsPlacesBar;
/*      */ 
/*      */ public class WindowsFileChooserUI extends BasicFileChooserUI {
/*   65 */   private ActionListener directoryComboBoxAction = new DirectoryComboBoxAction();
/*      */   
/*      */   private JPanel centerPanel;
/*      */   
/*      */   private JLabel lookInLabel;
/*      */   private JComboBox<File> directoryComboBox;
/*      */   private DirectoryComboBoxModel directoryComboBoxModel;
/*      */   private FilterComboBoxModel filterComboBoxModel;
/*      */   private JTextField filenameTextField;
/*      */   private FilePane filePane;
/*      */   private WindowsPlacesBar placesBar;
/*      */   private JButton approveButton;
/*      */   private JButton cancelButton;
/*      */   private JPanel buttonPanel;
/*      */   private JPanel bottomPanel;
/*      */   private JComboBox<FileFilter> filterComboBox;
/*   81 */   private static final Dimension hstrut10 = new Dimension(10, 1);
/*      */   
/*   83 */   private static final Dimension vstrut4 = new Dimension(1, 4);
/*   84 */   private static final Dimension vstrut6 = new Dimension(1, 6);
/*   85 */   private static final Dimension vstrut8 = new Dimension(1, 8);
/*      */   
/*   87 */   private static final Insets shrinkwrap = new Insets(0, 0, 0, 0);
/*      */ 
/*      */   
/*   90 */   private static int PREF_WIDTH = 425;
/*   91 */   private static int PREF_HEIGHT = 245;
/*   92 */   private static Dimension PREF_SIZE = new Dimension(PREF_WIDTH, PREF_HEIGHT);
/*      */   
/*   94 */   private static int MIN_WIDTH = 425;
/*   95 */   private static int MIN_HEIGHT = 245;
/*      */   
/*   97 */   private static int LIST_PREF_WIDTH = 444;
/*   98 */   private static int LIST_PREF_HEIGHT = 138;
/*   99 */   private static Dimension LIST_PREF_SIZE = new Dimension(LIST_PREF_WIDTH, LIST_PREF_HEIGHT);
/*      */ 
/*      */   
/*  102 */   private int lookInLabelMnemonic = 0;
/*  103 */   private String lookInLabelText = null;
/*  104 */   private String saveInLabelText = null;
/*      */   
/*  106 */   private int fileNameLabelMnemonic = 0;
/*  107 */   private String fileNameLabelText = null;
/*  108 */   private int folderNameLabelMnemonic = 0;
/*  109 */   private String folderNameLabelText = null;
/*      */   
/*  111 */   private int filesOfTypeLabelMnemonic = 0;
/*  112 */   private String filesOfTypeLabelText = null;
/*      */   
/*  114 */   private String upFolderToolTipText = null;
/*  115 */   private String upFolderAccessibleName = null;
/*      */   
/*  117 */   private String newFolderToolTipText = null;
/*  118 */   private String newFolderAccessibleName = null;
/*      */   
/*  120 */   private String viewMenuButtonToolTipText = null;
/*  121 */   private String viewMenuButtonAccessibleName = null;
/*      */   
/*  123 */   private BasicFileChooserUI.BasicFileView fileView = new WindowsFileView();
/*      */   private JLabel fileNameLabel;
/*      */   static final int space = 10;
/*      */   
/*      */   private void populateFileNameLabel() {
/*  128 */     if (getFileChooser().getFileSelectionMode() == 1) {
/*  129 */       this.fileNameLabel.setText(this.folderNameLabelText);
/*  130 */       this.fileNameLabel.setDisplayedMnemonic(this.folderNameLabelMnemonic);
/*      */     } else {
/*  132 */       this.fileNameLabel.setText(this.fileNameLabelText);
/*  133 */       this.fileNameLabel.setDisplayedMnemonic(this.fileNameLabelMnemonic);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  141 */     return new WindowsFileChooserUI((JFileChooser)paramJComponent);
/*      */   }
/*      */   
/*      */   public WindowsFileChooserUI(JFileChooser paramJFileChooser) {
/*  145 */     super(paramJFileChooser);
/*      */   }
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  149 */     super.installUI(paramJComponent);
/*      */   }
/*      */   
/*      */   public void uninstallComponents(JFileChooser paramJFileChooser) {
/*  153 */     paramJFileChooser.removeAll();
/*      */   }
/*      */   private class WindowsFileChooserUIAccessor implements FilePane.FileChooserUIAccessor { private WindowsFileChooserUIAccessor() {}
/*      */     
/*      */     public JFileChooser getFileChooser() {
/*  158 */       return WindowsFileChooserUI.this.getFileChooser();
/*      */     }
/*      */     
/*      */     public BasicDirectoryModel getModel() {
/*  162 */       return WindowsFileChooserUI.this.getModel();
/*      */     }
/*      */     
/*      */     public JPanel createList() {
/*  166 */       return WindowsFileChooserUI.this.createList(getFileChooser());
/*      */     }
/*      */     
/*      */     public JPanel createDetailsView() {
/*  170 */       return WindowsFileChooserUI.this.createDetailsView(getFileChooser());
/*      */     }
/*      */     
/*      */     public boolean isDirectorySelected() {
/*  174 */       return WindowsFileChooserUI.this.isDirectorySelected();
/*      */     }
/*      */     
/*      */     public File getDirectory() {
/*  178 */       return WindowsFileChooserUI.this.getDirectory();
/*      */     }
/*      */     
/*      */     public Action getChangeToParentDirectoryAction() {
/*  182 */       return WindowsFileChooserUI.this.getChangeToParentDirectoryAction();
/*      */     }
/*      */     
/*      */     public Action getApproveSelectionAction() {
/*  186 */       return WindowsFileChooserUI.this.getApproveSelectionAction();
/*      */     }
/*      */     
/*      */     public Action getNewFolderAction() {
/*  190 */       return WindowsFileChooserUI.this.getNewFolderAction();
/*      */     }
/*      */     
/*      */     public MouseListener createDoubleClickListener(JList param1JList) {
/*  194 */       return WindowsFileChooserUI.this.createDoubleClickListener(getFileChooser(), param1JList);
/*      */     }
/*      */ 
/*      */     
/*      */     public ListSelectionListener createListSelectionListener() {
/*  199 */       return WindowsFileChooserUI.this.createListSelectionListener(getFileChooser());
/*      */     } }
/*      */ 
/*      */   
/*      */   public void installComponents(JFileChooser paramJFileChooser) {
/*  204 */     this.filePane = new FilePane(new WindowsFileChooserUIAccessor());
/*  205 */     paramJFileChooser.addPropertyChangeListener(this.filePane);
/*      */     
/*  207 */     FileSystemView fileSystemView = paramJFileChooser.getFileSystemView();
/*      */     
/*  209 */     paramJFileChooser.setBorder(new EmptyBorder(4, 10, 10, 10));
/*  210 */     paramJFileChooser.setLayout(new BorderLayout(8, 8));
/*      */     
/*  212 */     updateUseShellFolder();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  219 */     JToolBar jToolBar = new JToolBar();
/*  220 */     jToolBar.setFloatable(false);
/*  221 */     jToolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
/*      */ 
/*      */     
/*  224 */     paramJFileChooser.add(jToolBar, "North");
/*      */ 
/*      */     
/*  227 */     this.lookInLabel = new JLabel(this.lookInLabelText, 11) {
/*      */         public Dimension getPreferredSize() {
/*  229 */           return getMinimumSize();
/*      */         }
/*      */         
/*      */         public Dimension getMinimumSize() {
/*  233 */           Dimension dimension = super.getPreferredSize();
/*  234 */           if (WindowsFileChooserUI.this.placesBar != null) {
/*  235 */             dimension.width = Math.max(dimension.width, WindowsFileChooserUI.this.placesBar.getWidth());
/*      */           }
/*  237 */           return dimension;
/*      */         }
/*      */       };
/*  240 */     this.lookInLabel.setDisplayedMnemonic(this.lookInLabelMnemonic);
/*  241 */     this.lookInLabel.setAlignmentX(0.0F);
/*  242 */     this.lookInLabel.setAlignmentY(0.5F);
/*  243 */     jToolBar.add(this.lookInLabel);
/*  244 */     jToolBar.add(Box.createRigidArea(new Dimension(8, 0)));
/*      */ 
/*      */     
/*  247 */     this.directoryComboBox = new JComboBox<File>() {
/*      */         public Dimension getMinimumSize() {
/*  249 */           Dimension dimension = super.getMinimumSize();
/*  250 */           dimension.width = 60;
/*  251 */           return dimension;
/*      */         }
/*      */         
/*      */         public Dimension getPreferredSize() {
/*  255 */           Dimension dimension = super.getPreferredSize();
/*      */           
/*  257 */           dimension.width = 150;
/*  258 */           return dimension;
/*      */         }
/*      */       };
/*  261 */     this.directoryComboBox.putClientProperty("JComboBox.lightweightKeyboardNavigation", "Lightweight");
/*  262 */     this.lookInLabel.setLabelFor(this.directoryComboBox);
/*  263 */     this.directoryComboBoxModel = createDirectoryComboBoxModel(paramJFileChooser);
/*  264 */     this.directoryComboBox.setModel(this.directoryComboBoxModel);
/*  265 */     this.directoryComboBox.addActionListener(this.directoryComboBoxAction);
/*  266 */     this.directoryComboBox.setRenderer(createDirectoryComboBoxRenderer(paramJFileChooser));
/*  267 */     this.directoryComboBox.setAlignmentX(0.0F);
/*  268 */     this.directoryComboBox.setAlignmentY(0.5F);
/*  269 */     this.directoryComboBox.setMaximumRowCount(8);
/*      */     
/*  271 */     jToolBar.add(this.directoryComboBox);
/*  272 */     jToolBar.add(Box.createRigidArea(hstrut10));
/*      */ 
/*      */     
/*  275 */     JButton jButton1 = createToolButton(getChangeToParentDirectoryAction(), this.upFolderIcon, this.upFolderToolTipText, this.upFolderAccessibleName);
/*      */     
/*  277 */     jToolBar.add(jButton1);
/*      */ 
/*      */     
/*  280 */     if (!UIManager.getBoolean("FileChooser.readOnly")) {
/*  281 */       JButton jButton = createToolButton(this.filePane.getNewFolderAction(), this.newFolderIcon, this.newFolderToolTipText, this.newFolderAccessibleName);
/*      */       
/*  283 */       jToolBar.add(jButton);
/*      */     } 
/*      */ 
/*      */     
/*  287 */     ButtonGroup buttonGroup = new ButtonGroup();
/*      */ 
/*      */     
/*  290 */     final JPopupMenu viewTypePopupMenu = new JPopupMenu();
/*      */ 
/*      */     
/*  293 */     final JRadioButtonMenuItem listViewMenuItem = new JRadioButtonMenuItem(this.filePane.getViewTypeAction(0));
/*  294 */     jRadioButtonMenuItem1.setSelected((this.filePane.getViewType() == 0));
/*  295 */     jPopupMenu.add(jRadioButtonMenuItem1);
/*  296 */     buttonGroup.add(jRadioButtonMenuItem1);
/*      */ 
/*      */     
/*  299 */     final JRadioButtonMenuItem detailsViewMenuItem = new JRadioButtonMenuItem(this.filePane.getViewTypeAction(1));
/*  300 */     jRadioButtonMenuItem2.setSelected((this.filePane.getViewType() == 1));
/*  301 */     jPopupMenu.add(jRadioButtonMenuItem2);
/*  302 */     buttonGroup.add(jRadioButtonMenuItem2);
/*      */ 
/*      */     
/*  305 */     BufferedImage bufferedImage = new BufferedImage(this.viewMenuIcon.getIconWidth() + 7, this.viewMenuIcon.getIconHeight(), 2);
/*      */     
/*  307 */     Graphics graphics = bufferedImage.getGraphics();
/*  308 */     this.viewMenuIcon.paintIcon(this.filePane, graphics, 0, 0);
/*  309 */     int i = bufferedImage.getWidth() - 5;
/*  310 */     int j = bufferedImage.getHeight() / 2 - 1;
/*  311 */     graphics.setColor(Color.BLACK);
/*  312 */     graphics.fillPolygon(new int[] { i, i + 5, i + 2 }, new int[] { j, j, j + 3 }, 3);
/*      */ 
/*      */     
/*  315 */     final JButton viewMenuButton = createToolButton((Action)null, new ImageIcon(bufferedImage), this.viewMenuButtonToolTipText, this.viewMenuButtonAccessibleName);
/*      */ 
/*      */     
/*  318 */     jButton2.addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent param1MouseEvent) {
/*  320 */             if (SwingUtilities.isLeftMouseButton(param1MouseEvent) && !viewMenuButton.isSelected()) {
/*  321 */               viewMenuButton.setSelected(true);
/*      */               
/*  323 */               viewTypePopupMenu.show(viewMenuButton, 0, viewMenuButton.getHeight());
/*      */             } 
/*      */           }
/*      */         });
/*  327 */     jButton2.addKeyListener(new KeyAdapter()
/*      */         {
/*      */           public void keyPressed(KeyEvent param1KeyEvent) {
/*  330 */             if (param1KeyEvent.getKeyCode() == 32 && viewMenuButton.getModel().isRollover()) {
/*  331 */               viewMenuButton.setSelected(true);
/*      */               
/*  333 */               viewTypePopupMenu.show(viewMenuButton, 0, viewMenuButton.getHeight());
/*      */             } 
/*      */           }
/*      */         });
/*  337 */     jPopupMenu.addPopupMenuListener(new PopupMenuListener()
/*      */         {
/*      */           public void popupMenuWillBecomeVisible(PopupMenuEvent param1PopupMenuEvent) {}
/*      */           
/*      */           public void popupMenuWillBecomeInvisible(PopupMenuEvent param1PopupMenuEvent) {
/*  342 */             SwingUtilities.invokeLater(new Runnable() {
/*      */                   public void run() {
/*  344 */                     viewMenuButton.setSelected(false);
/*      */                   }
/*      */                 });
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void popupMenuCanceled(PopupMenuEvent param1PopupMenuEvent) {}
/*      */         });
/*  353 */     jToolBar.add(jButton2);
/*      */     
/*  355 */     jToolBar.add(Box.createRigidArea(new Dimension(80, 0)));
/*      */     
/*  357 */     this.filePane.addPropertyChangeListener(new PropertyChangeListener() {
/*      */           public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  359 */             if ("viewType".equals(param1PropertyChangeEvent.getPropertyName())) {
/*  360 */               switch (WindowsFileChooserUI.this.filePane.getViewType()) {
/*      */                 case 0:
/*  362 */                   listViewMenuItem.setSelected(true);
/*      */                   break;
/*      */                 
/*      */                 case 1:
/*  366 */                   detailsViewMenuItem.setSelected(true);
/*      */                   break;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */           }
/*      */         });
/*  376 */     this.centerPanel = new JPanel(new BorderLayout());
/*  377 */     this.centerPanel.add(getAccessoryPanel(), "After");
/*  378 */     JComponent jComponent = paramJFileChooser.getAccessory();
/*  379 */     if (jComponent != null) {
/*  380 */       getAccessoryPanel().add(jComponent);
/*      */     }
/*  382 */     this.filePane.setPreferredSize(LIST_PREF_SIZE);
/*  383 */     this.centerPanel.add(this.filePane, "Center");
/*  384 */     paramJFileChooser.add(this.centerPanel, "Center");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  389 */     getBottomPanel().setLayout(new BoxLayout(getBottomPanel(), 2));
/*      */ 
/*      */     
/*  392 */     this.centerPanel.add(getBottomPanel(), "South");
/*      */ 
/*      */     
/*  395 */     JPanel jPanel1 = new JPanel();
/*  396 */     jPanel1.setLayout(new BoxLayout(jPanel1, 3));
/*  397 */     jPanel1.add(Box.createRigidArea(vstrut4));
/*      */     
/*  399 */     this.fileNameLabel = new JLabel();
/*  400 */     populateFileNameLabel();
/*  401 */     this.fileNameLabel.setAlignmentY(0.0F);
/*  402 */     jPanel1.add(this.fileNameLabel);
/*      */     
/*  404 */     jPanel1.add(Box.createRigidArea(new Dimension(1, 12)));
/*      */     
/*  406 */     JLabel jLabel = new JLabel(this.filesOfTypeLabelText);
/*  407 */     jLabel.setDisplayedMnemonic(this.filesOfTypeLabelMnemonic);
/*  408 */     jPanel1.add(jLabel);
/*      */     
/*  410 */     getBottomPanel().add(jPanel1);
/*  411 */     getBottomPanel().add(Box.createRigidArea(new Dimension(15, 0)));
/*      */ 
/*      */     
/*  414 */     JPanel jPanel2 = new JPanel();
/*  415 */     jPanel2.add(Box.createRigidArea(vstrut8));
/*  416 */     jPanel2.setLayout(new BoxLayout(jPanel2, 1));
/*      */ 
/*      */     
/*  419 */     this.filenameTextField = new JTextField(35) {
/*      */         public Dimension getMaximumSize() {
/*  421 */           return new Dimension(32767, (getPreferredSize()).height);
/*      */         }
/*      */       };
/*      */     
/*  425 */     this.fileNameLabel.setLabelFor(this.filenameTextField);
/*  426 */     this.filenameTextField.addFocusListener(new FocusAdapter()
/*      */         {
/*      */           public void focusGained(FocusEvent param1FocusEvent) {
/*  429 */             if (!WindowsFileChooserUI.this.getFileChooser().isMultiSelectionEnabled()) {
/*  430 */               WindowsFileChooserUI.this.filePane.clearSelection();
/*      */             }
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  436 */     if (paramJFileChooser.isMultiSelectionEnabled()) {
/*  437 */       setFileName(fileNameString(paramJFileChooser.getSelectedFiles()));
/*      */     } else {
/*  439 */       setFileName(fileNameString(paramJFileChooser.getSelectedFile()));
/*      */     } 
/*      */     
/*  442 */     jPanel2.add(this.filenameTextField);
/*  443 */     jPanel2.add(Box.createRigidArea(vstrut8));
/*      */     
/*  445 */     this.filterComboBoxModel = createFilterComboBoxModel();
/*  446 */     paramJFileChooser.addPropertyChangeListener(this.filterComboBoxModel);
/*  447 */     this.filterComboBox = new JComboBox<>(this.filterComboBoxModel);
/*  448 */     jLabel.setLabelFor(this.filterComboBox);
/*  449 */     this.filterComboBox.setRenderer(createFilterComboBoxRenderer());
/*  450 */     jPanel2.add(this.filterComboBox);
/*      */     
/*  452 */     getBottomPanel().add(jPanel2);
/*  453 */     getBottomPanel().add(Box.createRigidArea(new Dimension(30, 0)));
/*      */ 
/*      */     
/*  456 */     getButtonPanel().setLayout(new BoxLayout(getButtonPanel(), 1));
/*      */     
/*  458 */     this.approveButton = new JButton(getApproveButtonText(paramJFileChooser)) {
/*      */         public Dimension getMaximumSize() {
/*  460 */           return ((WindowsFileChooserUI.this.approveButton.getPreferredSize()).width > (WindowsFileChooserUI.this.cancelButton.getPreferredSize()).width) ? WindowsFileChooserUI.this
/*  461 */             .approveButton.getPreferredSize() : WindowsFileChooserUI.this.cancelButton.getPreferredSize();
/*      */         }
/*      */       };
/*  464 */     Insets insets = this.approveButton.getMargin();
/*  465 */     insets = new InsetsUIResource(insets.top, insets.left + 5, insets.bottom, insets.right + 5);
/*      */     
/*  467 */     this.approveButton.setMargin(insets);
/*  468 */     this.approveButton.setMnemonic(getApproveButtonMnemonic(paramJFileChooser));
/*  469 */     this.approveButton.addActionListener(getApproveSelectionAction());
/*  470 */     this.approveButton.setToolTipText(getApproveButtonToolTipText(paramJFileChooser));
/*  471 */     getButtonPanel().add(Box.createRigidArea(vstrut6));
/*  472 */     getButtonPanel().add(this.approveButton);
/*  473 */     getButtonPanel().add(Box.createRigidArea(vstrut4));
/*      */     
/*  475 */     this.cancelButton = new JButton(this.cancelButtonText) {
/*      */         public Dimension getMaximumSize() {
/*  477 */           return ((WindowsFileChooserUI.this.approveButton.getPreferredSize()).width > (WindowsFileChooserUI.this.cancelButton.getPreferredSize()).width) ? WindowsFileChooserUI.this
/*  478 */             .approveButton.getPreferredSize() : WindowsFileChooserUI.this.cancelButton.getPreferredSize();
/*      */         }
/*      */       };
/*  481 */     this.cancelButton.setMargin(insets);
/*  482 */     this.cancelButton.setToolTipText(this.cancelButtonToolTipText);
/*  483 */     this.cancelButton.addActionListener(getCancelSelectionAction());
/*  484 */     getButtonPanel().add(this.cancelButton);
/*      */     
/*  486 */     if (paramJFileChooser.getControlButtonsAreShown()) {
/*  487 */       addControlButtons();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateUseShellFolder() {
/*  494 */     JFileChooser jFileChooser = getFileChooser();
/*      */     
/*  496 */     if (FilePane.usesShellFolder(jFileChooser)) {
/*  497 */       if (this.placesBar == null && !UIManager.getBoolean("FileChooser.noPlacesBar")) {
/*  498 */         this.placesBar = new WindowsPlacesBar(jFileChooser, (XPStyle.getXP() != null));
/*  499 */         jFileChooser.add(this.placesBar, "Before");
/*  500 */         jFileChooser.addPropertyChangeListener(this.placesBar);
/*      */       }
/*      */     
/*  503 */     } else if (this.placesBar != null) {
/*  504 */       jFileChooser.remove(this.placesBar);
/*  505 */       jFileChooser.removePropertyChangeListener(this.placesBar);
/*  506 */       this.placesBar = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected JPanel getButtonPanel() {
/*  512 */     if (this.buttonPanel == null) {
/*  513 */       this.buttonPanel = new JPanel();
/*      */     }
/*  515 */     return this.buttonPanel;
/*      */   }
/*      */   
/*      */   protected JPanel getBottomPanel() {
/*  519 */     if (this.bottomPanel == null) {
/*  520 */       this.bottomPanel = new JPanel();
/*      */     }
/*  522 */     return this.bottomPanel;
/*      */   }
/*      */   
/*      */   protected void installStrings(JFileChooser paramJFileChooser) {
/*  526 */     super.installStrings(paramJFileChooser);
/*      */     
/*  528 */     Locale locale = paramJFileChooser.getLocale();
/*      */     
/*  530 */     this.lookInLabelMnemonic = getMnemonic("FileChooser.lookInLabelMnemonic", locale).intValue();
/*  531 */     this.lookInLabelText = UIManager.getString("FileChooser.lookInLabelText", locale);
/*  532 */     this.saveInLabelText = UIManager.getString("FileChooser.saveInLabelText", locale);
/*      */     
/*  534 */     this.fileNameLabelMnemonic = getMnemonic("FileChooser.fileNameLabelMnemonic", locale).intValue();
/*  535 */     this.fileNameLabelText = UIManager.getString("FileChooser.fileNameLabelText", locale);
/*  536 */     this.folderNameLabelMnemonic = getMnemonic("FileChooser.folderNameLabelMnemonic", locale).intValue();
/*  537 */     this.folderNameLabelText = UIManager.getString("FileChooser.folderNameLabelText", locale);
/*      */     
/*  539 */     this.filesOfTypeLabelMnemonic = getMnemonic("FileChooser.filesOfTypeLabelMnemonic", locale).intValue();
/*  540 */     this.filesOfTypeLabelText = UIManager.getString("FileChooser.filesOfTypeLabelText", locale);
/*      */     
/*  542 */     this.upFolderToolTipText = UIManager.getString("FileChooser.upFolderToolTipText", locale);
/*  543 */     this.upFolderAccessibleName = UIManager.getString("FileChooser.upFolderAccessibleName", locale);
/*      */     
/*  545 */     this.newFolderToolTipText = UIManager.getString("FileChooser.newFolderToolTipText", locale);
/*  546 */     this.newFolderAccessibleName = UIManager.getString("FileChooser.newFolderAccessibleName", locale);
/*      */     
/*  548 */     this.viewMenuButtonToolTipText = UIManager.getString("FileChooser.viewMenuButtonToolTipText", locale);
/*  549 */     this.viewMenuButtonAccessibleName = UIManager.getString("FileChooser.viewMenuButtonAccessibleName", locale);
/*      */   }
/*      */   
/*      */   private Integer getMnemonic(String paramString, Locale paramLocale) {
/*  553 */     return Integer.valueOf(SwingUtilities2.getUIDefaultsInt(paramString, paramLocale));
/*      */   }
/*      */   
/*      */   protected void installListeners(JFileChooser paramJFileChooser) {
/*  557 */     super.installListeners(paramJFileChooser);
/*  558 */     ActionMap actionMap = getActionMap();
/*  559 */     SwingUtilities.replaceUIActionMap(paramJFileChooser, actionMap);
/*      */   }
/*      */   
/*      */   protected ActionMap getActionMap() {
/*  563 */     return createActionMap();
/*      */   }
/*      */   
/*      */   protected ActionMap createActionMap() {
/*  567 */     ActionMapUIResource actionMapUIResource = new ActionMapUIResource();
/*  568 */     FilePane.addActionsToMap(actionMapUIResource, this.filePane.getActions());
/*  569 */     return actionMapUIResource;
/*      */   }
/*      */   
/*      */   protected JPanel createList(JFileChooser paramJFileChooser) {
/*  573 */     return this.filePane.createList();
/*      */   }
/*      */   
/*      */   protected JPanel createDetailsView(JFileChooser paramJFileChooser) {
/*  577 */     return this.filePane.createDetailsView();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListSelectionListener createListSelectionListener(JFileChooser paramJFileChooser) {
/*  587 */     return super.createListSelectionListener(paramJFileChooser);
/*      */   }
/*      */ 
/*      */   
/*      */   protected class WindowsNewFolderAction
/*      */     extends BasicFileChooserUI.NewFolderAction {}
/*      */ 
/*      */   
/*      */   protected class SingleClickListener
/*      */     extends MouseAdapter {}
/*      */ 
/*      */   
/*      */   protected class FileRenderer
/*      */     extends DefaultListCellRenderer {}
/*      */ 
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  604 */     paramJComponent.removePropertyChangeListener(this.filterComboBoxModel);
/*  605 */     paramJComponent.removePropertyChangeListener(this.filePane);
/*  606 */     if (this.placesBar != null) {
/*  607 */       paramJComponent.removePropertyChangeListener(this.placesBar);
/*      */     }
/*  609 */     this.cancelButton.removeActionListener(getCancelSelectionAction());
/*  610 */     this.approveButton.removeActionListener(getApproveSelectionAction());
/*  611 */     this.filenameTextField.removeActionListener(getApproveSelectionAction());
/*      */     
/*  613 */     if (this.filePane != null) {
/*  614 */       this.filePane.uninstallUI();
/*  615 */       this.filePane = null;
/*      */     } 
/*      */     
/*  618 */     super.uninstallUI(paramJComponent);
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
/*  635 */     int i = PREF_SIZE.width;
/*  636 */     Dimension dimension = paramJComponent.getLayout().preferredLayoutSize(paramJComponent);
/*  637 */     if (dimension != null) {
/*  638 */       return new Dimension((dimension.width < i) ? i : dimension.width, (dimension.height < PREF_SIZE.height) ? PREF_SIZE.height : dimension.height);
/*      */     }
/*      */     
/*  641 */     return new Dimension(i, PREF_SIZE.height);
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
/*  654 */     return new Dimension(MIN_WIDTH, MIN_HEIGHT);
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
/*  666 */     return new Dimension(2147483647, 2147483647);
/*      */   }
/*      */   
/*      */   private String fileNameString(File paramFile) {
/*  670 */     if (paramFile == null) {
/*  671 */       return null;
/*      */     }
/*  673 */     JFileChooser jFileChooser = getFileChooser();
/*  674 */     if ((jFileChooser.isDirectorySelectionEnabled() && !jFileChooser.isFileSelectionEnabled()) || (jFileChooser
/*  675 */       .isDirectorySelectionEnabled() && jFileChooser.isFileSelectionEnabled() && jFileChooser.getFileSystemView().isFileSystemRoot(paramFile))) {
/*  676 */       return paramFile.getPath();
/*      */     }
/*  678 */     return paramFile.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String fileNameString(File[] paramArrayOfFile) {
/*  684 */     StringBuffer stringBuffer = new StringBuffer();
/*  685 */     for (byte b = 0; paramArrayOfFile != null && b < paramArrayOfFile.length; b++) {
/*  686 */       if (b > 0) {
/*  687 */         stringBuffer.append(" ");
/*      */       }
/*  689 */       if (paramArrayOfFile.length > 1) {
/*  690 */         stringBuffer.append("\"");
/*      */       }
/*  692 */       stringBuffer.append(fileNameString(paramArrayOfFile[b]));
/*  693 */       if (paramArrayOfFile.length > 1) {
/*  694 */         stringBuffer.append("\"");
/*      */       }
/*      */     } 
/*  697 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void doSelectedFileChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  703 */     File file = (File)paramPropertyChangeEvent.getNewValue();
/*  704 */     JFileChooser jFileChooser = getFileChooser();
/*  705 */     if (file != null && ((jFileChooser
/*  706 */       .isFileSelectionEnabled() && !file.isDirectory()) || (file
/*  707 */       .isDirectory() && jFileChooser.isDirectorySelectionEnabled())))
/*      */     {
/*  709 */       setFileName(fileNameString(file));
/*      */     }
/*      */   }
/*      */   
/*      */   private void doSelectedFilesChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  714 */     File[] arrayOfFile = (File[])paramPropertyChangeEvent.getNewValue();
/*  715 */     JFileChooser jFileChooser = getFileChooser();
/*  716 */     if (arrayOfFile != null && arrayOfFile.length > 0 && (arrayOfFile.length > 1 || jFileChooser
/*      */       
/*  718 */       .isDirectorySelectionEnabled() || !arrayOfFile[0].isDirectory())) {
/*  719 */       setFileName(fileNameString(arrayOfFile));
/*      */     }
/*      */   }
/*      */   
/*      */   private void doDirectoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  724 */     JFileChooser jFileChooser = getFileChooser();
/*  725 */     FileSystemView fileSystemView = jFileChooser.getFileSystemView();
/*      */     
/*  727 */     clearIconCache();
/*  728 */     File file = jFileChooser.getCurrentDirectory();
/*  729 */     if (file != null) {
/*  730 */       this.directoryComboBoxModel.addItem(file);
/*      */       
/*  732 */       if (jFileChooser.isDirectorySelectionEnabled() && !jFileChooser.isFileSelectionEnabled()) {
/*  733 */         if (fileSystemView.isFileSystem(file)) {
/*  734 */           setFileName(file.getPath());
/*      */         } else {
/*  736 */           setFileName((String)null);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doFilterChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  743 */     clearIconCache();
/*      */   }
/*      */   
/*      */   private void doFileSelectionModeChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  747 */     if (this.fileNameLabel != null) {
/*  748 */       populateFileNameLabel();
/*      */     }
/*  750 */     clearIconCache();
/*      */     
/*  752 */     JFileChooser jFileChooser = getFileChooser();
/*  753 */     File file = jFileChooser.getCurrentDirectory();
/*  754 */     if (file != null && jFileChooser
/*  755 */       .isDirectorySelectionEnabled() && 
/*  756 */       !jFileChooser.isFileSelectionEnabled() && jFileChooser
/*  757 */       .getFileSystemView().isFileSystem(file)) {
/*      */       
/*  759 */       setFileName(file.getPath());
/*      */     } else {
/*  761 */       setFileName((String)null);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doAccessoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  766 */     if (getAccessoryPanel() != null) {
/*  767 */       if (paramPropertyChangeEvent.getOldValue() != null) {
/*  768 */         getAccessoryPanel().remove((JComponent)paramPropertyChangeEvent.getOldValue());
/*      */       }
/*  770 */       JComponent jComponent = (JComponent)paramPropertyChangeEvent.getNewValue();
/*  771 */       if (jComponent != null) {
/*  772 */         getAccessoryPanel().add(jComponent, "Center");
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doApproveButtonTextChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  778 */     JFileChooser jFileChooser = getFileChooser();
/*  779 */     this.approveButton.setText(getApproveButtonText(jFileChooser));
/*  780 */     this.approveButton.setToolTipText(getApproveButtonToolTipText(jFileChooser));
/*  781 */     this.approveButton.setMnemonic(getApproveButtonMnemonic(jFileChooser));
/*      */   }
/*      */   
/*      */   private void doDialogTypeChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  785 */     JFileChooser jFileChooser = getFileChooser();
/*  786 */     this.approveButton.setText(getApproveButtonText(jFileChooser));
/*  787 */     this.approveButton.setToolTipText(getApproveButtonToolTipText(jFileChooser));
/*  788 */     this.approveButton.setMnemonic(getApproveButtonMnemonic(jFileChooser));
/*  789 */     if (jFileChooser.getDialogType() == 1) {
/*  790 */       this.lookInLabel.setText(this.saveInLabelText);
/*      */     } else {
/*  792 */       this.lookInLabel.setText(this.lookInLabelText);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doApproveButtonMnemonicChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  797 */     this.approveButton.setMnemonic(getApproveButtonMnemonic(getFileChooser()));
/*      */   }
/*      */   
/*      */   private void doControlButtonsChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/*  801 */     if (getFileChooser().getControlButtonsAreShown()) {
/*  802 */       addControlButtons();
/*      */     } else {
/*  804 */       removeControlButtons();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PropertyChangeListener createPropertyChangeListener(JFileChooser paramJFileChooser) {
/*  813 */     return new PropertyChangeListener() {
/*      */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  815 */           String str = param1PropertyChangeEvent.getPropertyName();
/*  816 */           if (str.equals("SelectedFileChangedProperty")) {
/*  817 */             WindowsFileChooserUI.this.doSelectedFileChanged(param1PropertyChangeEvent);
/*  818 */           } else if (str.equals("SelectedFilesChangedProperty")) {
/*  819 */             WindowsFileChooserUI.this.doSelectedFilesChanged(param1PropertyChangeEvent);
/*  820 */           } else if (str.equals("directoryChanged")) {
/*  821 */             WindowsFileChooserUI.this.doDirectoryChanged(param1PropertyChangeEvent);
/*  822 */           } else if (str.equals("fileFilterChanged")) {
/*  823 */             WindowsFileChooserUI.this.doFilterChanged(param1PropertyChangeEvent);
/*  824 */           } else if (str.equals("fileSelectionChanged")) {
/*  825 */             WindowsFileChooserUI.this.doFileSelectionModeChanged(param1PropertyChangeEvent);
/*  826 */           } else if (str.equals("AccessoryChangedProperty")) {
/*  827 */             WindowsFileChooserUI.this.doAccessoryChanged(param1PropertyChangeEvent);
/*  828 */           } else if (str.equals("ApproveButtonTextChangedProperty") || str
/*  829 */             .equals("ApproveButtonToolTipTextChangedProperty")) {
/*  830 */             WindowsFileChooserUI.this.doApproveButtonTextChanged(param1PropertyChangeEvent);
/*  831 */           } else if (str.equals("DialogTypeChangedProperty")) {
/*  832 */             WindowsFileChooserUI.this.doDialogTypeChanged(param1PropertyChangeEvent);
/*  833 */           } else if (str.equals("ApproveButtonMnemonicChangedProperty")) {
/*  834 */             WindowsFileChooserUI.this.doApproveButtonMnemonicChanged(param1PropertyChangeEvent);
/*  835 */           } else if (str.equals("ControlButtonsAreShownChangedProperty")) {
/*  836 */             WindowsFileChooserUI.this.doControlButtonsChanged(param1PropertyChangeEvent);
/*  837 */           } else if (str == "FileChooser.useShellFolder") {
/*  838 */             WindowsFileChooserUI.this.updateUseShellFolder();
/*  839 */             WindowsFileChooserUI.this.doDirectoryChanged(param1PropertyChangeEvent);
/*  840 */           } else if (str.equals("componentOrientation")) {
/*  841 */             ComponentOrientation componentOrientation = (ComponentOrientation)param1PropertyChangeEvent.getNewValue();
/*  842 */             JFileChooser jFileChooser = (JFileChooser)param1PropertyChangeEvent.getSource();
/*  843 */             if (componentOrientation != param1PropertyChangeEvent.getOldValue()) {
/*  844 */               jFileChooser.applyComponentOrientation(componentOrientation);
/*      */             }
/*  846 */           } else if (str.equals("ancestor") && 
/*  847 */             param1PropertyChangeEvent.getOldValue() == null && param1PropertyChangeEvent.getNewValue() != null) {
/*      */             
/*  849 */             WindowsFileChooserUI.this.filenameTextField.selectAll();
/*  850 */             WindowsFileChooserUI.this.filenameTextField.requestFocus();
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeControlButtons() {
/*  859 */     getBottomPanel().remove(getButtonPanel());
/*      */   }
/*      */   
/*      */   protected void addControlButtons() {
/*  863 */     getBottomPanel().add(getButtonPanel());
/*      */   }
/*      */   
/*      */   public void ensureFileIsVisible(JFileChooser paramJFileChooser, File paramFile) {
/*  867 */     this.filePane.ensureFileIsVisible(paramJFileChooser, paramFile);
/*      */   }
/*      */   
/*      */   public void rescanCurrentDirectory(JFileChooser paramJFileChooser) {
/*  871 */     this.filePane.rescanCurrentDirectory();
/*      */   }
/*      */   
/*      */   public String getFileName() {
/*  875 */     if (this.filenameTextField != null) {
/*  876 */       return this.filenameTextField.getText();
/*      */     }
/*  878 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFileName(String paramString) {
/*  883 */     if (this.filenameTextField != null) {
/*  884 */       this.filenameTextField.setText(paramString);
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
/*  896 */     super.setDirectorySelected(paramBoolean);
/*  897 */     JFileChooser jFileChooser = getFileChooser();
/*  898 */     if (paramBoolean) {
/*  899 */       this.approveButton.setText(this.directoryOpenButtonText);
/*  900 */       this.approveButton.setToolTipText(this.directoryOpenButtonToolTipText);
/*  901 */       this.approveButton.setMnemonic(this.directoryOpenButtonMnemonic);
/*      */     } else {
/*  903 */       this.approveButton.setText(getApproveButtonText(jFileChooser));
/*  904 */       this.approveButton.setToolTipText(getApproveButtonToolTipText(jFileChooser));
/*  905 */       this.approveButton.setMnemonic(getApproveButtonMnemonic(jFileChooser));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDirectoryName() {
/*  911 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDirectoryName(String paramString) {}
/*      */ 
/*      */   
/*      */   protected DirectoryComboBoxRenderer createDirectoryComboBoxRenderer(JFileChooser paramJFileChooser) {
/*  919 */     return new DirectoryComboBoxRenderer();
/*      */   }
/*      */   
/*      */   private static JButton createToolButton(Action paramAction, Icon paramIcon, String paramString1, String paramString2) {
/*  923 */     final JButton result = new JButton(paramAction);
/*      */     
/*  925 */     jButton.setText((String)null);
/*  926 */     jButton.setIcon(paramIcon);
/*  927 */     jButton.setToolTipText(paramString1);
/*  928 */     jButton.setRequestFocusEnabled(false);
/*  929 */     jButton.putClientProperty("AccessibleName", paramString2);
/*  930 */     jButton.putClientProperty(WindowsLookAndFeel.HI_RES_DISABLED_ICON_CLIENT_KEY, Boolean.TRUE);
/*  931 */     jButton.setAlignmentX(0.0F);
/*  932 */     jButton.setAlignmentY(0.5F);
/*  933 */     jButton.setMargin(shrinkwrap);
/*  934 */     jButton.setFocusPainted(false);
/*      */     
/*  936 */     jButton.setModel(new DefaultButtonModel()
/*      */         {
/*      */           public void setPressed(boolean param1Boolean) {
/*  939 */             if (!param1Boolean || isRollover()) {
/*  940 */               super.setPressed(param1Boolean);
/*      */             }
/*      */           }
/*      */           
/*      */           public void setRollover(boolean param1Boolean) {
/*  945 */             if (param1Boolean && !isRollover())
/*      */             {
/*  947 */               for (Component component : result.getParent().getComponents()) {
/*  948 */                 if (component instanceof JButton && component != result) {
/*  949 */                   ((JButton)component).getModel().setRollover(false);
/*      */                 }
/*      */               } 
/*      */             }
/*      */             
/*  954 */             super.setRollover(param1Boolean);
/*      */           }
/*      */           
/*      */           public void setSelected(boolean param1Boolean) {
/*  958 */             super.setSelected(param1Boolean);
/*      */             
/*  960 */             if (param1Boolean) {
/*  961 */               this.stateMask |= 0x5;
/*      */             } else {
/*  963 */               this.stateMask &= 0xFFFFFFFA;
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  968 */     jButton.addFocusListener(new FocusAdapter() {
/*      */           public void focusGained(FocusEvent param1FocusEvent) {
/*  970 */             result.getModel().setRollover(true);
/*      */           }
/*      */           
/*      */           public void focusLost(FocusEvent param1FocusEvent) {
/*  974 */             result.getModel().setRollover(false);
/*      */           }
/*      */         });
/*      */     
/*  978 */     return jButton;
/*      */   }
/*      */ 
/*      */   
/*      */   class DirectoryComboBoxRenderer
/*      */     extends DefaultListCellRenderer
/*      */   {
/*  985 */     WindowsFileChooserUI.IndentIcon ii = new WindowsFileChooserUI.IndentIcon();
/*      */ 
/*      */ 
/*      */     
/*      */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/*  990 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/*      */       
/*  992 */       if (param1Object == null) {
/*  993 */         setText("");
/*  994 */         return this;
/*      */       } 
/*  996 */       File file = (File)param1Object;
/*  997 */       setText(WindowsFileChooserUI.this.getFileChooser().getName(file));
/*  998 */       Icon icon = WindowsFileChooserUI.this.getFileChooser().getIcon(file);
/*  999 */       this.ii.icon = icon;
/* 1000 */       this.ii.depth = WindowsFileChooserUI.this.directoryComboBoxModel.getDepth(param1Int);
/* 1001 */       setIcon(this.ii);
/*      */       
/* 1003 */       return this;
/*      */     }
/*      */   }
/*      */   
/*      */   class IndentIcon
/*      */     implements Icon
/*      */   {
/* 1010 */     Icon icon = null;
/* 1011 */     int depth = 0;
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1014 */       if (param1Component.getComponentOrientation().isLeftToRight()) {
/* 1015 */         this.icon.paintIcon(param1Component, param1Graphics, param1Int1 + this.depth * 10, param1Int2);
/*      */       } else {
/* 1017 */         this.icon.paintIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 1022 */       return this.icon.getIconWidth() + this.depth * 10;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 1026 */       return this.icon.getIconHeight();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DirectoryComboBoxModel createDirectoryComboBoxModel(JFileChooser paramJFileChooser) {
/* 1035 */     return new DirectoryComboBoxModel();
/*      */   }
/*      */   
/*      */   protected class DirectoryComboBoxModel
/*      */     extends AbstractListModel<File>
/*      */     implements ComboBoxModel<File>
/*      */   {
/* 1042 */     Vector<File> directories = new Vector<>();
/* 1043 */     int[] depths = null;
/* 1044 */     File selectedDirectory = null;
/* 1045 */     JFileChooser chooser = WindowsFileChooserUI.this.getFileChooser();
/* 1046 */     FileSystemView fsv = this.chooser.getFileSystemView();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1064 */       if (param1File == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1068 */       boolean bool = FilePane.usesShellFolder(this.chooser);
/*      */       
/* 1070 */       this.directories.clear();
/*      */ 
/*      */ 
/*      */       
/* 1074 */       File[] arrayOfFile = bool ? (File[])ShellFolder.get("fileChooserComboBoxFolders") : this.fsv.getRoots();
/* 1075 */       this.directories.addAll(Arrays.asList(arrayOfFile));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1082 */         file = param1File.getCanonicalFile();
/* 1083 */       } catch (IOException iOException) {
/*      */         
/* 1085 */         file = param1File;
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 1090 */         File file1 = bool ? ShellFolder.getShellFolder(file) : file;
/*      */         
/* 1092 */         File file2 = file1;
/* 1093 */         Vector<File> vector = new Vector(10);
/*      */         do {
/* 1095 */           vector.addElement(file2);
/* 1096 */         } while ((file2 = file2.getParentFile()) != null);
/*      */         
/* 1098 */         int i = vector.size();
/*      */         
/* 1100 */         for (byte b = 0; b < i; b++) {
/* 1101 */           file2 = vector.get(b);
/* 1102 */           if (this.directories.contains(file2)) {
/* 1103 */             int j = this.directories.indexOf(file2);
/* 1104 */             for (int k = b - 1; k >= 0; k--) {
/* 1105 */               this.directories.insertElementAt(vector.get(k), j + b - k);
/*      */             }
/*      */             break;
/*      */           } 
/*      */         } 
/* 1110 */         calculateDepths();
/* 1111 */         setSelectedItem(file1);
/* 1112 */       } catch (FileNotFoundException fileNotFoundException) {
/* 1113 */         calculateDepths();
/*      */       } 
/*      */     }
/*      */     
/*      */     private void calculateDepths() {
/* 1118 */       this.depths = new int[this.directories.size()];
/* 1119 */       for (byte b = 0; b < this.depths.length; b++) {
/* 1120 */         File file1 = this.directories.get(b);
/* 1121 */         File file2 = file1.getParentFile();
/* 1122 */         this.depths[b] = 0;
/* 1123 */         if (file2 != null) {
/* 1124 */           for (int i = b - 1; i >= 0; i--) {
/* 1125 */             if (file2.equals(this.directories.get(i))) {
/* 1126 */               this.depths[b] = this.depths[i] + 1;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public int getDepth(int param1Int) {
/* 1135 */       return (this.depths != null && param1Int >= 0 && param1Int < this.depths.length) ? this.depths[param1Int] : 0;
/*      */     }
/*      */     
/*      */     public void setSelectedItem(Object param1Object) {
/* 1139 */       this.selectedDirectory = (File)param1Object;
/* 1140 */       fireContentsChanged(this, -1, -1);
/*      */     }
/*      */     
/*      */     public Object getSelectedItem() {
/* 1144 */       return this.selectedDirectory;
/*      */     }
/*      */     
/*      */     public int getSize() {
/* 1148 */       return this.directories.size();
/*      */     }
/*      */     
/*      */     public File getElementAt(int param1Int) {
/* 1152 */       return this.directories.elementAt(param1Int);
/*      */     }
/*      */     public DirectoryComboBoxModel() {
/*      */       File file = WindowsFileChooserUI.this.getFileChooser().getCurrentDirectory();
/*      */       if (file != null)
/*      */         addItem(file); 
/*      */     } }
/*      */   protected FilterComboBoxRenderer createFilterComboBoxRenderer() {
/* 1160 */     return new FilterComboBoxRenderer();
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
/* 1171 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/*      */       
/* 1173 */       if (param1Object != null && param1Object instanceof FileFilter) {
/* 1174 */         setText(((FileFilter)param1Object).getDescription());
/*      */       }
/*      */       
/* 1177 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FilterComboBoxModel createFilterComboBoxModel() {
/* 1185 */     return new FilterComboBoxModel();
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
/* 1196 */     protected FileFilter[] filters = WindowsFileChooserUI.this.getFileChooser().getChoosableFileFilters();
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1200 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1201 */       if (str == "ChoosableFileFilterChangedProperty") {
/* 1202 */         this.filters = (FileFilter[])param1PropertyChangeEvent.getNewValue();
/* 1203 */         fireContentsChanged(this, -1, -1);
/* 1204 */       } else if (str == "fileFilterChanged") {
/* 1205 */         fireContentsChanged(this, -1, -1);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void setSelectedItem(Object param1Object) {
/* 1210 */       if (param1Object != null) {
/* 1211 */         WindowsFileChooserUI.this.getFileChooser().setFileFilter((FileFilter)param1Object);
/* 1212 */         fireContentsChanged(this, -1, -1);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getSelectedItem() {
/* 1222 */       FileFilter fileFilter = WindowsFileChooserUI.this.getFileChooser().getFileFilter();
/* 1223 */       boolean bool = false;
/* 1224 */       if (fileFilter != null) {
/* 1225 */         for (FileFilter fileFilter1 : this.filters) {
/* 1226 */           if (fileFilter1 == fileFilter) {
/* 1227 */             bool = true;
/*      */           }
/*      */         } 
/* 1230 */         if (!bool) {
/* 1231 */           WindowsFileChooserUI.this.getFileChooser().addChoosableFileFilter(fileFilter);
/*      */         }
/*      */       } 
/* 1234 */       return WindowsFileChooserUI.this.getFileChooser().getFileFilter();
/*      */     }
/*      */     
/*      */     public int getSize() {
/* 1238 */       if (this.filters != null) {
/* 1239 */         return this.filters.length;
/*      */       }
/* 1241 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public FileFilter getElementAt(int param1Int) {
/* 1246 */       if (param1Int > getSize() - 1)
/*      */       {
/* 1248 */         return WindowsFileChooserUI.this.getFileChooser().getFileFilter();
/*      */       }
/* 1250 */       if (this.filters != null) {
/* 1251 */         return this.filters[param1Int];
/*      */       }
/* 1253 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void valueChanged(ListSelectionEvent paramListSelectionEvent) {
/* 1259 */     JFileChooser jFileChooser = getFileChooser();
/* 1260 */     File file = jFileChooser.getSelectedFile();
/* 1261 */     if (!paramListSelectionEvent.getValueIsAdjusting() && file != null && !getFileChooser().isTraversable(file)) {
/* 1262 */       setFileName(fileNameString(file));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class DirectoryComboBoxAction
/*      */     implements ActionListener
/*      */   {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1275 */       File file = (File)WindowsFileChooserUI.this.directoryComboBox.getSelectedItem();
/* 1276 */       WindowsFileChooserUI.this.getFileChooser().setCurrentDirectory(file);
/*      */     }
/*      */   }
/*      */   
/*      */   protected JButton getApproveButton(JFileChooser paramJFileChooser) {
/* 1281 */     return this.approveButton;
/*      */   }
/*      */   
/*      */   public FileView getFileView(JFileChooser paramJFileChooser) {
/* 1285 */     return this.fileView;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class WindowsFileView
/*      */     extends BasicFileChooserUI.BasicFileView
/*      */   {
/*      */     public Icon getIcon(File param1File) {
/* 1295 */       Icon icon = getCachedIcon(param1File);
/* 1296 */       if (icon != null) {
/* 1297 */         return icon;
/*      */       }
/* 1299 */       if (param1File != null) {
/* 1300 */         icon = WindowsFileChooserUI.this.getFileChooser().getFileSystemView().getSystemIcon(param1File);
/*      */       }
/* 1302 */       if (icon == null) {
/* 1303 */         icon = super.getIcon(param1File);
/*      */       }
/* 1305 */       cacheIcon(param1File, icon);
/* 1306 */       return icon;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsFileChooserUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */