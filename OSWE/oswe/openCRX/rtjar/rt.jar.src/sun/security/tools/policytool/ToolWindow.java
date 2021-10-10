/*      */ package sun.security.tools.policytool;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.security.AccessController;
/*      */ import java.text.MessageFormat;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.DefaultListModel;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class ToolWindow
/*      */   extends JFrame
/*      */ {
/*      */   private static final long serialVersionUID = 5682568601210376777L;
/* 1020 */   static final KeyStroke escKey = KeyStroke.getKeyStroke(27, 0);
/*      */ 
/*      */   
/* 1023 */   public static final Insets TOP_PADDING = new Insets(25, 0, 0, 0);
/* 1024 */   public static final Insets BOTTOM_PADDING = new Insets(0, 0, 25, 0);
/* 1025 */   public static final Insets LITE_BOTTOM_PADDING = new Insets(0, 0, 10, 0);
/* 1026 */   public static final Insets LR_PADDING = new Insets(0, 10, 0, 10);
/* 1027 */   public static final Insets TOP_BOTTOM_PADDING = new Insets(15, 0, 15, 0);
/* 1028 */   public static final Insets L_TOP_BOTTOM_PADDING = new Insets(5, 10, 15, 0);
/* 1029 */   public static final Insets LR_TOP_BOTTOM_PADDING = new Insets(15, 4, 15, 4);
/* 1030 */   public static final Insets LR_BOTTOM_PADDING = new Insets(0, 10, 5, 10);
/* 1031 */   public static final Insets L_BOTTOM_PADDING = new Insets(0, 10, 5, 0);
/* 1032 */   public static final Insets R_BOTTOM_PADDING = new Insets(0, 0, 25, 5);
/* 1033 */   public static final Insets R_PADDING = new Insets(0, 0, 0, 5);
/*      */   
/*      */   public static final String NEW_POLICY_FILE = "New";
/*      */   
/*      */   public static final String OPEN_POLICY_FILE = "Open";
/*      */   
/*      */   public static final String SAVE_POLICY_FILE = "Save";
/*      */   
/*      */   public static final String SAVE_AS_POLICY_FILE = "Save.As";
/*      */   
/*      */   public static final String VIEW_WARNINGS = "View.Warning.Log";
/*      */   
/*      */   public static final String QUIT = "Exit";
/*      */   public static final String ADD_POLICY_ENTRY = "Add.Policy.Entry";
/*      */   public static final String EDIT_POLICY_ENTRY = "Edit.Policy.Entry";
/*      */   public static final String REMOVE_POLICY_ENTRY = "Remove.Policy.Entry";
/*      */   public static final String EDIT_KEYSTORE = "Edit";
/*      */   public static final String ADD_PUBKEY_ALIAS = "Add.Public.Key.Alias";
/*      */   public static final String REMOVE_PUBKEY_ALIAS = "Remove.Public.Key.Alias";
/*      */   public static final int MW_FILENAME_LABEL = 0;
/*      */   public static final int MW_FILENAME_TEXTFIELD = 1;
/*      */   public static final int MW_PANEL = 2;
/*      */   public static final int MW_ADD_BUTTON = 0;
/*      */   public static final int MW_EDIT_BUTTON = 1;
/*      */   public static final int MW_REMOVE_BUTTON = 2;
/*      */   public static final int MW_POLICY_LIST = 3;
/* 1059 */   static final int TEXTFIELD_HEIGHT = ((new JComboBox()).getPreferredSize()).height;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PolicyTool tool;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int shortCutModifier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ToolWindow(PolicyTool paramPolicyTool) {
/* 1221 */     this.shortCutModifier = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(); this.tool = paramPolicyTool;
/*      */   } public Component getComponent(int paramInt) { Component component = getContentPane().getComponent(paramInt); if (component instanceof JScrollPane)
/*      */       component = ((JScrollPane)component).getViewport().getView(); 
/* 1224 */     return component; } private void addMenuItem(JMenu paramJMenu, String paramString1, ActionListener paramActionListener, String paramString2) { JMenuItem jMenuItem = new JMenuItem();
/* 1225 */     configureButton(jMenuItem, paramString1);
/*      */     
/* 1227 */     if (PolicyTool.rb.containsKey(paramString1 + ".accelerator"))
/*      */     {
/* 1229 */       paramString2 = PolicyTool.getMessage(paramString1 + ".accelerator");
/*      */     }
/*      */     
/* 1232 */     if (paramString2 != null && !paramString2.isEmpty()) {
/*      */       KeyStroke keyStroke;
/* 1234 */       if (paramString2.length() == 1) {
/* 1235 */         keyStroke = KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar(paramString2.charAt(0)), this.shortCutModifier);
/*      */       } else {
/*      */         
/* 1238 */         keyStroke = KeyStroke.getKeyStroke(paramString2);
/*      */       } 
/* 1240 */       jMenuItem.setAccelerator(keyStroke);
/*      */     } 
/*      */     
/* 1243 */     jMenuItem.addActionListener(paramActionListener);
/* 1244 */     paramJMenu.add(jMenuItem); }
/*      */   private void initWindow() { setDefaultCloseOperation(0); JMenuBar jMenuBar = new JMenuBar(); JMenu jMenu = new JMenu(); configureButton(jMenu, "File"); FileMenuListener fileMenuListener = new FileMenuListener(this.tool, this); addMenuItem(jMenu, "New", fileMenuListener, "N"); addMenuItem(jMenu, "Open", fileMenuListener, "O"); addMenuItem(jMenu, "Save", fileMenuListener, "S"); addMenuItem(jMenu, "Save.As", fileMenuListener, (String)null); addMenuItem(jMenu, "View.Warning.Log", fileMenuListener, (String)null); addMenuItem(jMenu, "Exit", fileMenuListener, (String)null); jMenuBar.add(jMenu); jMenu = new JMenu(); configureButton(jMenu, "KeyStore"); MainWindowListener mainWindowListener = new MainWindowListener(this.tool, this); addMenuItem(jMenu, "Edit", mainWindowListener, (String)null); jMenuBar.add(jMenu); setJMenuBar(jMenuBar); ((JPanel)getContentPane()).setBorder(new EmptyBorder(6, 6, 6, 6)); JLabel jLabel = new JLabel(PolicyTool.getMessage("Policy.File.")); addNewComponent(this, jLabel, 0, 0, 0, 1, 1, 0.0D, 0.0D, 1, LR_TOP_BOTTOM_PADDING); JTextField jTextField = new JTextField(50); jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT)); jTextField.getAccessibleContext().setAccessibleName(PolicyTool.getMessage("Policy.File.")); jTextField.setEditable(false); addNewComponent(this, jTextField, 1, 1, 0, 1, 1, 0.0D, 0.0D, 1, LR_TOP_BOTTOM_PADDING); JPanel jPanel = new JPanel(); jPanel.setLayout(new GridBagLayout()); JButton jButton = new JButton(); configureButton(jButton, "Add.Policy.Entry"); jButton.addActionListener(new MainWindowListener(this.tool, this)); addNewComponent(jPanel, jButton, 0, 0, 0, 1, 1, 0.0D, 0.0D, 1, LR_PADDING); jButton = new JButton(); configureButton(jButton, "Edit.Policy.Entry"); jButton.addActionListener(new MainWindowListener(this.tool, this)); addNewComponent(jPanel, jButton, 1, 1, 0, 1, 1, 0.0D, 0.0D, 1, LR_PADDING); jButton = new JButton(); configureButton(jButton, "Remove.Policy.Entry"); jButton.addActionListener(new MainWindowListener(this.tool, this)); addNewComponent(jPanel, jButton, 2, 2, 0, 1, 1, 0.0D, 0.0D, 1, LR_PADDING); addNewComponent(this, jPanel, 2, 0, 2, 2, 1, 0.0D, 0.0D, 1, BOTTOM_PADDING); String str = this.tool.getPolicyFileName(); if (str == null) { String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("user.home")); str = str1 + File.separatorChar + ".java.policy"; }  try { this.tool.openPolicy(str); DefaultListModel<?> defaultListModel = new DefaultListModel(); JList jList = new JList(defaultListModel); jList.setVisibleRowCount(15); jList.setSelectionMode(0); jList.addMouseListener(new PolicyListListener(this.tool, this)); PolicyEntry[] arrayOfPolicyEntry = this.tool.getEntry(); if (arrayOfPolicyEntry != null)
/*      */         for (byte b = 0; b < arrayOfPolicyEntry.length; b++)
/*      */           defaultListModel.addElement(arrayOfPolicyEntry[b].headerToString());   JTextField jTextField1 = (JTextField)getComponent(1); jTextField1.setText(str); initPolicyList(jList); } catch (FileNotFoundException fileNotFoundException) { JList jList = new JList(new DefaultListModel()); jList.setVisibleRowCount(15); jList.setSelectionMode(0); jList.addMouseListener(new PolicyListListener(this.tool, this)); initPolicyList(jList); this.tool.setPolicyFileName(null); this.tool.modified = false; this.tool.warnings.addElement(fileNotFoundException.toString()); } catch (Exception exception) { JList jList = new JList(new DefaultListModel()); jList.setVisibleRowCount(15); jList.setSelectionMode(0); jList.addMouseListener(new PolicyListListener(this.tool, this)); initPolicyList(jList); this.tool.setPolicyFileName(null); this.tool.modified = false; MessageFormat messageFormat = new MessageFormat(PolicyTool.getMessage("Could.not.open.policy.file.policyFile.e.toString.")); Object[] arrayOfObject = { str, exception.toString() }; displayErrorDialog((Window)null, messageFormat.format(arrayOfObject)); }
/* 1248 */      } static void configureButton(AbstractButton paramAbstractButton, String paramString) { paramAbstractButton.setText(PolicyTool.getMessage(paramString));
/* 1249 */     paramAbstractButton.setActionCommand(paramString);
/*      */     
/* 1251 */     int i = PolicyTool.getMnemonicInt(paramString);
/* 1252 */     if (i > 0) {
/* 1253 */       paramAbstractButton.setMnemonic(i);
/* 1254 */       paramAbstractButton.setDisplayedMnemonicIndex(PolicyTool.getDisplayedMnemonicIndex(paramString));
/*      */     }  }
/*      */ 
/*      */   
/*      */   static void configureLabelFor(JLabel paramJLabel, JComponent paramJComponent, String paramString) {
/* 1259 */     paramJLabel.setText(PolicyTool.getMessage(paramString));
/* 1260 */     paramJLabel.setLabelFor(paramJComponent);
/*      */     
/* 1262 */     int i = PolicyTool.getMnemonicInt(paramString);
/* 1263 */     if (i > 0) {
/* 1264 */       paramJLabel.setDisplayedMnemonic(i);
/* 1265 */       paramJLabel.setDisplayedMnemonicIndex(PolicyTool.getDisplayedMnemonicIndex(paramString));
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
/*      */   void addNewComponent(Container paramContainer, JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2, int paramInt6, Insets paramInsets) {
/* 1277 */     if (paramContainer instanceof JFrame) {
/* 1278 */       paramContainer = ((JFrame)paramContainer).getContentPane();
/* 1279 */     } else if (paramContainer instanceof JDialog) {
/* 1280 */       paramContainer = ((JDialog)paramContainer).getContentPane();
/*      */     } 
/*      */ 
/*      */     
/* 1284 */     paramContainer.add(paramJComponent, paramInt1);
/*      */ 
/*      */     
/* 1287 */     GridBagLayout gridBagLayout = (GridBagLayout)paramContainer.getLayout();
/* 1288 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 1289 */     gridBagConstraints.gridx = paramInt2;
/* 1290 */     gridBagConstraints.gridy = paramInt3;
/* 1291 */     gridBagConstraints.gridwidth = paramInt4;
/* 1292 */     gridBagConstraints.gridheight = paramInt5;
/* 1293 */     gridBagConstraints.weightx = paramDouble1;
/* 1294 */     gridBagConstraints.weighty = paramDouble2;
/* 1295 */     gridBagConstraints.fill = paramInt6;
/* 1296 */     if (paramInsets != null) gridBagConstraints.insets = paramInsets; 
/* 1297 */     gridBagLayout.setConstraints(paramJComponent, gridBagConstraints);
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
/*      */   void addNewComponent(Container paramContainer, JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2, int paramInt6) {
/* 1309 */     addNewComponent(paramContainer, paramJComponent, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramDouble1, paramDouble2, paramInt6, (Insets)null);
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
/*      */   void initPolicyList(JList paramJList) {
/* 1323 */     JScrollPane jScrollPane = new JScrollPane(paramJList);
/* 1324 */     addNewComponent(this, jScrollPane, 3, 0, 3, 2, 1, 1.0D, 1.0D, 1);
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
/*      */   void replacePolicyList(JList paramJList) {
/* 1336 */     JList jList = (JList)getComponent(3);
/* 1337 */     jList.setModel(paramJList.getModel());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void displayToolWindow(String[] paramArrayOfString) {
/* 1345 */     setTitle(PolicyTool.getMessage("Policy.Tool"));
/* 1346 */     setResizable(true);
/* 1347 */     addWindowListener(new ToolWindowListener(this.tool, this));
/*      */     
/* 1349 */     getContentPane().setLayout(new GridBagLayout());
/*      */     
/* 1351 */     initWindow();
/* 1352 */     pack();
/* 1353 */     setLocationRelativeTo((Component)null);
/*      */ 
/*      */     
/* 1356 */     setVisible(true);
/*      */     
/* 1358 */     if (this.tool.newWarning == true) {
/* 1359 */       displayStatusDialog(this, 
/* 1360 */           PolicyTool.getMessage("Errors.have.occurred.while.opening.the.policy.configuration.View.the.Warning.Log.for.more.information."));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void displayErrorDialog(Window paramWindow, String paramString) {
/* 1369 */     ToolDialog toolDialog = new ToolDialog(PolicyTool.getMessage("Error"), this.tool, this, true);
/*      */ 
/*      */ 
/*      */     
/* 1373 */     Point point = (paramWindow == null) ? getLocationOnScreen() : paramWindow.getLocationOnScreen();
/*      */     
/* 1375 */     toolDialog.setLayout(new GridBagLayout());
/*      */     
/* 1377 */     JLabel jLabel = new JLabel(paramString);
/* 1378 */     addNewComponent(toolDialog, jLabel, 0, 0, 0, 1, 1, 0.0D, 0.0D, 1);
/*      */ 
/*      */     
/* 1381 */     JButton jButton = new JButton(PolicyTool.getMessage("OK"));
/* 1382 */     ErrorOKButtonListener errorOKButtonListener = new ErrorOKButtonListener(toolDialog);
/* 1383 */     jButton.addActionListener(errorOKButtonListener);
/* 1384 */     addNewComponent(toolDialog, jButton, 1, 0, 1, 1, 1, 0.0D, 0.0D, 3);
/*      */ 
/*      */     
/* 1387 */     toolDialog.getRootPane().setDefaultButton(jButton);
/* 1388 */     toolDialog.getRootPane().registerKeyboardAction(errorOKButtonListener, escKey, 2);
/*      */     
/* 1390 */     toolDialog.pack();
/* 1391 */     toolDialog.setLocationRelativeTo(paramWindow);
/* 1392 */     toolDialog.setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void displayErrorDialog(Window paramWindow, Throwable paramThrowable) {
/* 1399 */     if (paramThrowable instanceof NoDisplayException) {
/*      */       return;
/*      */     }
/* 1402 */     displayErrorDialog(paramWindow, paramThrowable.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void displayStatusDialog(Window paramWindow, String paramString) {
/* 1410 */     ToolDialog toolDialog = new ToolDialog(PolicyTool.getMessage("Status"), this.tool, this, true);
/*      */ 
/*      */ 
/*      */     
/* 1414 */     Point point = (paramWindow == null) ? getLocationOnScreen() : paramWindow.getLocationOnScreen();
/*      */     
/* 1416 */     toolDialog.setLayout(new GridBagLayout());
/*      */     
/* 1418 */     JLabel jLabel = new JLabel(paramString);
/* 1419 */     addNewComponent(toolDialog, jLabel, 0, 0, 0, 1, 1, 0.0D, 0.0D, 1);
/*      */ 
/*      */     
/* 1422 */     JButton jButton = new JButton(PolicyTool.getMessage("OK"));
/* 1423 */     StatusOKButtonListener statusOKButtonListener = new StatusOKButtonListener(toolDialog);
/* 1424 */     jButton.addActionListener(statusOKButtonListener);
/* 1425 */     addNewComponent(toolDialog, jButton, 1, 0, 1, 1, 1, 0.0D, 0.0D, 3);
/*      */ 
/*      */     
/* 1428 */     toolDialog.getRootPane().setDefaultButton(jButton);
/* 1429 */     toolDialog.getRootPane().registerKeyboardAction(statusOKButtonListener, escKey, 2);
/*      */     
/* 1431 */     toolDialog.pack();
/* 1432 */     toolDialog.setLocationRelativeTo(paramWindow);
/* 1433 */     toolDialog.setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void displayWarningLog(Window paramWindow) {
/* 1442 */     ToolDialog toolDialog = new ToolDialog(PolicyTool.getMessage("Warning"), this.tool, this, true);
/*      */ 
/*      */ 
/*      */     
/* 1446 */     Point point = (paramWindow == null) ? getLocationOnScreen() : paramWindow.getLocationOnScreen();
/*      */     
/* 1448 */     toolDialog.setLayout(new GridBagLayout());
/*      */     
/* 1450 */     JTextArea jTextArea = new JTextArea();
/* 1451 */     jTextArea.setEditable(false);
/* 1452 */     for (byte b = 0; b < this.tool.warnings.size(); b++) {
/* 1453 */       jTextArea.append(this.tool.warnings.elementAt(b));
/* 1454 */       jTextArea.append(PolicyTool.getMessage("NEWLINE"));
/*      */     } 
/* 1456 */     addNewComponent(toolDialog, jTextArea, 0, 0, 0, 1, 1, 0.0D, 0.0D, 1, BOTTOM_PADDING);
/*      */ 
/*      */     
/* 1459 */     jTextArea.setFocusable(false);
/*      */     
/* 1461 */     JButton jButton = new JButton(PolicyTool.getMessage("OK"));
/* 1462 */     CancelButtonListener cancelButtonListener = new CancelButtonListener(toolDialog);
/* 1463 */     jButton.addActionListener(cancelButtonListener);
/* 1464 */     addNewComponent(toolDialog, jButton, 1, 0, 1, 1, 1, 0.0D, 0.0D, 3, LR_PADDING);
/*      */ 
/*      */ 
/*      */     
/* 1468 */     toolDialog.getRootPane().setDefaultButton(jButton);
/* 1469 */     toolDialog.getRootPane().registerKeyboardAction(cancelButtonListener, escKey, 2);
/*      */     
/* 1471 */     toolDialog.pack();
/* 1472 */     toolDialog.setLocationRelativeTo(paramWindow);
/* 1473 */     toolDialog.setVisible(true);
/*      */   }
/*      */ 
/*      */   
/*      */   char displayYesNoDialog(Window paramWindow, String paramString1, String paramString2, String paramString3, String paramString4) {
/* 1478 */     final ToolDialog tw = new ToolDialog(paramString1, this.tool, this, true);
/*      */ 
/*      */     
/* 1481 */     Point point = (paramWindow == null) ? getLocationOnScreen() : paramWindow.getLocationOnScreen();
/*      */     
/* 1483 */     toolDialog.setLayout(new GridBagLayout());
/*      */     
/* 1485 */     JTextArea jTextArea = new JTextArea(paramString2, 10, 50);
/* 1486 */     jTextArea.setEditable(false);
/* 1487 */     jTextArea.setLineWrap(true);
/* 1488 */     jTextArea.setWrapStyleWord(true);
/* 1489 */     JScrollPane jScrollPane = new JScrollPane(jTextArea, 20, 31);
/*      */ 
/*      */     
/* 1492 */     addNewComponent(toolDialog, jScrollPane, 0, 0, 0, 1, 1, 0.0D, 0.0D, 1);
/*      */     
/* 1494 */     jTextArea.setFocusable(false);
/*      */     
/* 1496 */     JPanel jPanel = new JPanel();
/* 1497 */     jPanel.setLayout(new GridBagLayout());
/*      */ 
/*      */     
/* 1500 */     final StringBuffer chooseResult = new StringBuffer();
/*      */     
/* 1502 */     JButton jButton = new JButton(paramString3);
/* 1503 */     jButton.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1505 */             chooseResult.append('Y');
/* 1506 */             tw.setVisible(false);
/* 1507 */             tw.dispose();
/*      */           }
/*      */         });
/* 1510 */     addNewComponent(jPanel, jButton, 0, 0, 0, 1, 1, 0.0D, 0.0D, 3, LR_PADDING);
/*      */ 
/*      */ 
/*      */     
/* 1514 */     jButton = new JButton(paramString4);
/* 1515 */     jButton.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1517 */             chooseResult.append('N');
/* 1518 */             tw.setVisible(false);
/* 1519 */             tw.dispose();
/*      */           }
/*      */         });
/* 1522 */     addNewComponent(jPanel, jButton, 1, 1, 0, 1, 1, 0.0D, 0.0D, 3, LR_PADDING);
/*      */ 
/*      */ 
/*      */     
/* 1526 */     addNewComponent(toolDialog, jPanel, 1, 0, 1, 1, 1, 0.0D, 0.0D, 3);
/*      */ 
/*      */     
/* 1529 */     toolDialog.pack();
/* 1530 */     toolDialog.setLocationRelativeTo(paramWindow);
/* 1531 */     toolDialog.setVisible(true);
/* 1532 */     if (stringBuffer.length() > 0) {
/* 1533 */       return stringBuffer.charAt(0);
/*      */     }
/*      */     
/* 1536 */     return 'N';
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/tools/policytool/ToolWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */