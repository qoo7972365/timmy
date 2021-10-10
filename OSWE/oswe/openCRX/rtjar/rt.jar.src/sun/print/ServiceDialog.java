/*      */ package sun.print;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.FlowLayout;
/*      */ import java.awt.Frame;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.Insets;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.File;
/*      */ import java.io.FilePermission;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ import javax.print.DocFlavor;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.ServiceUIFactory;
/*      */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*      */ import javax.print.attribute.PrintRequestAttributeSet;
/*      */ import javax.print.attribute.standard.Chromaticity;
/*      */ import javax.print.attribute.standard.Copies;
/*      */ import javax.print.attribute.standard.CopiesSupported;
/*      */ import javax.print.attribute.standard.Destination;
/*      */ import javax.print.attribute.standard.JobName;
/*      */ import javax.print.attribute.standard.JobPriority;
/*      */ import javax.print.attribute.standard.JobSheets;
/*      */ import javax.print.attribute.standard.Media;
/*      */ import javax.print.attribute.standard.MediaPrintableArea;
/*      */ import javax.print.attribute.standard.MediaSize;
/*      */ import javax.print.attribute.standard.MediaSizeName;
/*      */ import javax.print.attribute.standard.MediaTray;
/*      */ import javax.print.attribute.standard.OrientationRequested;
/*      */ import javax.print.attribute.standard.PageRanges;
/*      */ import javax.print.attribute.standard.PrintQuality;
/*      */ import javax.print.attribute.standard.PrinterInfo;
/*      */ import javax.print.attribute.standard.PrinterIsAcceptingJobs;
/*      */ import javax.print.attribute.standard.PrinterMakeAndModel;
/*      */ import javax.print.attribute.standard.RequestingUserName;
/*      */ import javax.print.attribute.standard.SheetCollate;
/*      */ import javax.print.attribute.standard.Sides;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JFormattedTextField;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRadioButton;
/*      */ import javax.swing.JSpinner;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.SpinnerNumberModel;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.PopupMenuEvent;
/*      */ import javax.swing.event.PopupMenuListener;
/*      */ import javax.swing.text.NumberFormatter;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ServiceDialog
/*      */   extends JDialog
/*      */   implements ActionListener
/*      */ {
/*      */   public static final int WAITING = 0;
/*      */   public static final int APPROVE = 1;
/*      */   public static final int CANCEL = 2;
/*      */   private static final String strBundle = "sun.print.resources.serviceui";
/*  102 */   private static final Insets panelInsets = new Insets(6, 6, 6, 6);
/*  103 */   private static final Insets compInsets = new Insets(3, 6, 3, 6);
/*      */   
/*      */   private static ResourceBundle messageRB;
/*      */   
/*      */   private JTabbedPane tpTabs;
/*      */   private JButton btnCancel;
/*      */   private JButton btnApprove;
/*      */   private PrintService[] services;
/*      */   private int defaultServiceIndex;
/*      */   private PrintRequestAttributeSet asOriginal;
/*      */   private HashPrintRequestAttributeSet asCurrent;
/*      */   private PrintService psCurrent;
/*      */   private DocFlavor docFlavor;
/*      */   private int status;
/*      */   private ValidatingFileChooser jfc;
/*      */   private GeneralPanel pnlGeneral;
/*      */   private PageSetupPanel pnlPageSetup;
/*      */   private AppearancePanel pnlAppearance;
/*      */   private boolean isAWT = false;
/*      */   
/*      */   static {
/*  124 */     initResource();
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
/*      */   public ServiceDialog(GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, PrintService[] paramArrayOfPrintService, int paramInt3, DocFlavor paramDocFlavor, PrintRequestAttributeSet paramPrintRequestAttributeSet, Dialog paramDialog) {
/*  140 */     super(paramDialog, getMsg("dialog.printtitle"), true, paramGraphicsConfiguration);
/*  141 */     initPrintDialog(paramInt1, paramInt2, paramArrayOfPrintService, paramInt3, paramDocFlavor, paramPrintRequestAttributeSet);
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
/*      */   public ServiceDialog(GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, PrintService[] paramArrayOfPrintService, int paramInt3, DocFlavor paramDocFlavor, PrintRequestAttributeSet paramPrintRequestAttributeSet, Frame paramFrame) {
/*  159 */     super(paramFrame, getMsg("dialog.printtitle"), true, paramGraphicsConfiguration);
/*  160 */     initPrintDialog(paramInt1, paramInt2, paramArrayOfPrintService, paramInt3, paramDocFlavor, paramPrintRequestAttributeSet);
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
/*      */   void initPrintDialog(int paramInt1, int paramInt2, PrintService[] paramArrayOfPrintService, int paramInt3, DocFlavor paramDocFlavor, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/*  174 */     this.services = paramArrayOfPrintService;
/*  175 */     this.defaultServiceIndex = paramInt3;
/*  176 */     this.asOriginal = paramPrintRequestAttributeSet;
/*  177 */     this.asCurrent = new HashPrintRequestAttributeSet(paramPrintRequestAttributeSet);
/*  178 */     this.psCurrent = paramArrayOfPrintService[paramInt3];
/*  179 */     this.docFlavor = paramDocFlavor;
/*      */     
/*  181 */     SunPageSelection sunPageSelection = (SunPageSelection)paramPrintRequestAttributeSet.get(SunPageSelection.class);
/*  182 */     if (sunPageSelection != null) {
/*  183 */       this.isAWT = true;
/*      */     }
/*      */     
/*  186 */     if (paramPrintRequestAttributeSet.get(DialogOnTop.class) != null) {
/*  187 */       setAlwaysOnTop(true);
/*      */     }
/*  189 */     Container container = getContentPane();
/*  190 */     container.setLayout(new BorderLayout());
/*      */     
/*  192 */     this.tpTabs = new JTabbedPane();
/*  193 */     this.tpTabs.setBorder(new EmptyBorder(5, 5, 5, 5));
/*      */     
/*  195 */     String str1 = getMsg("tab.general");
/*  196 */     int i = getVKMnemonic("tab.general");
/*  197 */     this.pnlGeneral = new GeneralPanel();
/*  198 */     this.tpTabs.add(str1, this.pnlGeneral);
/*  199 */     this.tpTabs.setMnemonicAt(0, i);
/*      */     
/*  201 */     String str2 = getMsg("tab.pagesetup");
/*  202 */     int j = getVKMnemonic("tab.pagesetup");
/*  203 */     this.pnlPageSetup = new PageSetupPanel();
/*  204 */     this.tpTabs.add(str2, this.pnlPageSetup);
/*  205 */     this.tpTabs.setMnemonicAt(1, j);
/*      */     
/*  207 */     String str3 = getMsg("tab.appearance");
/*  208 */     int k = getVKMnemonic("tab.appearance");
/*  209 */     this.pnlAppearance = new AppearancePanel();
/*  210 */     this.tpTabs.add(str3, this.pnlAppearance);
/*  211 */     this.tpTabs.setMnemonicAt(2, k);
/*      */     
/*  213 */     container.add(this.tpTabs, "Center");
/*      */     
/*  215 */     updatePanels();
/*      */     
/*  217 */     JPanel jPanel = new JPanel(new FlowLayout(4));
/*  218 */     this.btnApprove = createExitButton("button.print", this);
/*  219 */     jPanel.add(this.btnApprove);
/*  220 */     getRootPane().setDefaultButton(this.btnApprove);
/*  221 */     this.btnCancel = createExitButton("button.cancel", this);
/*  222 */     handleEscKey(this.btnCancel);
/*  223 */     jPanel.add(this.btnCancel);
/*  224 */     container.add(jPanel, "South");
/*      */     
/*  226 */     addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent param1WindowEvent) {
/*  228 */             ServiceDialog.this.dispose(2);
/*      */           }
/*      */         });
/*      */     
/*  232 */     getAccessibleContext().setAccessibleDescription(getMsg("dialog.printtitle"));
/*  233 */     setResizable(false);
/*  234 */     setLocation(paramInt1, paramInt2);
/*  235 */     pack();
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
/*      */   public ServiceDialog(GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, PrintService paramPrintService, DocFlavor paramDocFlavor, PrintRequestAttributeSet paramPrintRequestAttributeSet, Dialog paramDialog) {
/*  248 */     super(paramDialog, getMsg("dialog.pstitle"), true, paramGraphicsConfiguration);
/*  249 */     initPageDialog(paramInt1, paramInt2, paramPrintService, paramDocFlavor, paramPrintRequestAttributeSet);
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
/*      */   public ServiceDialog(GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, PrintService paramPrintService, DocFlavor paramDocFlavor, PrintRequestAttributeSet paramPrintRequestAttributeSet, Frame paramFrame) {
/*  262 */     super(paramFrame, getMsg("dialog.pstitle"), true, paramGraphicsConfiguration);
/*  263 */     initPageDialog(paramInt1, paramInt2, paramPrintService, paramDocFlavor, paramPrintRequestAttributeSet);
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
/*      */   void initPageDialog(int paramInt1, int paramInt2, PrintService paramPrintService, DocFlavor paramDocFlavor, PrintRequestAttributeSet paramPrintRequestAttributeSet) {
/*  275 */     this.psCurrent = paramPrintService;
/*  276 */     this.docFlavor = paramDocFlavor;
/*  277 */     this.asOriginal = paramPrintRequestAttributeSet;
/*  278 */     this.asCurrent = new HashPrintRequestAttributeSet(paramPrintRequestAttributeSet);
/*      */     
/*  280 */     if (paramPrintRequestAttributeSet.get(DialogOnTop.class) != null) {
/*  281 */       setAlwaysOnTop(true);
/*      */     }
/*      */     
/*  284 */     Container container = getContentPane();
/*  285 */     container.setLayout(new BorderLayout());
/*      */     
/*  287 */     this.pnlPageSetup = new PageSetupPanel();
/*  288 */     container.add(this.pnlPageSetup, "Center");
/*      */     
/*  290 */     this.pnlPageSetup.updateInfo();
/*      */     
/*  292 */     JPanel jPanel = new JPanel(new FlowLayout(4));
/*  293 */     this.btnApprove = createExitButton("button.ok", this);
/*  294 */     jPanel.add(this.btnApprove);
/*  295 */     getRootPane().setDefaultButton(this.btnApprove);
/*  296 */     this.btnCancel = createExitButton("button.cancel", this);
/*  297 */     handleEscKey(this.btnCancel);
/*  298 */     jPanel.add(this.btnCancel);
/*  299 */     container.add(jPanel, "South");
/*      */     
/*  301 */     addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent param1WindowEvent) {
/*  303 */             ServiceDialog.this.dispose(2);
/*      */           }
/*      */         });
/*      */     
/*  307 */     getAccessibleContext().setAccessibleDescription(getMsg("dialog.pstitle"));
/*  308 */     setResizable(false);
/*  309 */     setLocation(paramInt1, paramInt2);
/*  310 */     pack();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleEscKey(JButton paramJButton) {
/*  317 */     AbstractAction abstractAction = new AbstractAction() {
/*      */         public void actionPerformed(ActionEvent param1ActionEvent) {
/*  319 */           ServiceDialog.this.dispose(2);
/*      */         }
/*      */       };
/*      */     
/*  323 */     KeyStroke keyStroke = KeyStroke.getKeyStroke(27, 0);
/*      */     
/*  325 */     InputMap inputMap = paramJButton.getInputMap(2);
/*  326 */     ActionMap actionMap = paramJButton.getActionMap();
/*      */     
/*  328 */     if (inputMap != null && actionMap != null) {
/*  329 */       inputMap.put(keyStroke, "cancel");
/*  330 */       actionMap.put("cancel", abstractAction);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStatus() {
/*  340 */     return this.status;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrintRequestAttributeSet getAttributes() {
/*  349 */     if (this.status == 1) {
/*  350 */       return this.asCurrent;
/*      */     }
/*  352 */     return this.asOriginal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrintService getPrintService() {
/*  362 */     if (this.status == 1) {
/*  363 */       return this.psCurrent;
/*      */     }
/*  365 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispose(int paramInt) {
/*  374 */     this.status = paramInt;
/*      */     
/*  376 */     dispose();
/*      */   }
/*      */   
/*      */   public void actionPerformed(ActionEvent paramActionEvent) {
/*  380 */     Object object = paramActionEvent.getSource();
/*  381 */     boolean bool = false;
/*      */     
/*  383 */     if (object == this.btnApprove) {
/*  384 */       bool = true;
/*      */       
/*  386 */       if (this.pnlGeneral != null) {
/*  387 */         if (this.pnlGeneral.isPrintToFileRequested()) {
/*  388 */           bool = showFileChooser();
/*      */         } else {
/*  390 */           this.asCurrent.remove(Destination.class);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  395 */     dispose(bool ? 1 : 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean showFileChooser() {
/*      */     File file;
/*  403 */     Class<Destination> clazz = Destination.class;
/*      */     
/*  405 */     Destination destination = (Destination)this.asCurrent.get(clazz);
/*  406 */     if (destination == null) {
/*  407 */       destination = (Destination)this.asOriginal.get(clazz);
/*  408 */       if (destination == null) {
/*  409 */         destination = (Destination)this.psCurrent.getDefaultAttributeValue((Class)clazz);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  414 */         if (destination == null) {
/*      */           try {
/*  416 */             destination = new Destination(new URI("file:out.prn"));
/*  417 */           } catch (URISyntaxException uRISyntaxException) {}
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  424 */     if (destination != null) {
/*      */       try {
/*  426 */         file = new File(destination.getURI());
/*  427 */       } catch (Exception exception) {
/*      */         
/*  429 */         file = new File("out.prn");
/*      */       } 
/*      */     } else {
/*  432 */       file = new File("out.prn");
/*      */     } 
/*      */     
/*  435 */     ValidatingFileChooser validatingFileChooser = new ValidatingFileChooser();
/*  436 */     validatingFileChooser.setApproveButtonText(getMsg("button.ok"));
/*  437 */     validatingFileChooser.setDialogTitle(getMsg("dialog.printtofile"));
/*  438 */     validatingFileChooser.setDialogType(1);
/*  439 */     validatingFileChooser.setSelectedFile(file);
/*      */     
/*  441 */     int i = validatingFileChooser.showDialog(this, null);
/*  442 */     if (i == 0) {
/*  443 */       file = validatingFileChooser.getSelectedFile();
/*      */       
/*      */       try {
/*  446 */         this.asCurrent.add(new Destination(file.toURI()));
/*  447 */       } catch (Exception exception) {
/*  448 */         this.asCurrent.remove(clazz);
/*      */       } 
/*      */     } else {
/*  451 */       this.asCurrent.remove(clazz);
/*      */     } 
/*      */     
/*  454 */     return (i == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updatePanels() {
/*  461 */     this.pnlGeneral.updateInfo();
/*  462 */     this.pnlPageSetup.updateInfo();
/*  463 */     this.pnlAppearance.updateInfo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void initResource() {
/*  470 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/*      */             try {
/*  474 */               ServiceDialog.messageRB = ResourceBundle.getBundle("sun.print.resources.serviceui");
/*  475 */               return null;
/*  476 */             } catch (MissingResourceException missingResourceException) {
/*  477 */               throw new Error("Fatal: Resource for ServiceUI is missing");
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getMsg(String paramString) {
/*      */     try {
/*  490 */       return removeMnemonics(messageRB.getString(paramString));
/*  491 */     } catch (MissingResourceException missingResourceException) {
/*  492 */       throw new Error("Fatal: Resource for ServiceUI is broken; there is no " + paramString + " key in resource");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static String removeMnemonics(String paramString) {
/*  498 */     int i = paramString.indexOf('&');
/*  499 */     int j = paramString.length();
/*  500 */     if (i < 0 || i == j - 1) {
/*  501 */       return paramString;
/*      */     }
/*  503 */     int k = paramString.indexOf('&', i + 1);
/*  504 */     if (k == i + 1) {
/*  505 */       if (k + 1 == j) {
/*  506 */         return paramString.substring(0, i + 1);
/*      */       }
/*  508 */       return paramString.substring(0, i + 1) + removeMnemonics(paramString.substring(k + 1));
/*      */     } 
/*      */ 
/*      */     
/*  512 */     if (i == 0) {
/*  513 */       return removeMnemonics(paramString.substring(1));
/*      */     }
/*  515 */     return paramString.substring(0, i) + removeMnemonics(paramString.substring(i + 1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static char getMnemonic(String paramString) {
/*  524 */     String str = messageRB.getString(paramString).replace("&&", "");
/*  525 */     int i = str.indexOf('&');
/*  526 */     if (0 <= i && i < str.length() - 1) {
/*  527 */       char c = str.charAt(i + 1);
/*  528 */       return Character.toUpperCase(c);
/*      */     } 
/*  530 */     return Character.MIN_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  537 */   static Class _keyEventClazz = null;
/*      */   private static int getVKMnemonic(String paramString) {
/*  539 */     String str1 = String.valueOf(getMnemonic(paramString));
/*  540 */     if (str1 == null || str1.length() != 1) {
/*  541 */       return 0;
/*      */     }
/*  543 */     String str2 = "VK_" + str1.toUpperCase();
/*      */     
/*      */     try {
/*  546 */       if (_keyEventClazz == null) {
/*  547 */         _keyEventClazz = Class.forName("java.awt.event.KeyEvent", true, ServiceDialog.class
/*  548 */             .getClassLoader());
/*      */       }
/*  550 */       Field field = _keyEventClazz.getDeclaredField(str2);
/*  551 */       return field.getInt(null);
/*      */     }
/*  553 */     catch (Exception exception) {
/*      */       
/*  555 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static URL getImageResource(final String key) {
/*  562 */     URL uRL = AccessController.<URL>doPrivileged(new PrivilegedAction<URL>()
/*      */         {
/*      */           public Object run() {
/*  565 */             return ServiceDialog.class.getResource("resources/" + key);
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */     
/*  571 */     if (uRL == null) {
/*  572 */       throw new Error("Fatal: Resource for ServiceUI is broken; there is no " + key + " key in resource");
/*      */     }
/*      */ 
/*      */     
/*  576 */     return uRL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static JButton createButton(String paramString, ActionListener paramActionListener) {
/*  583 */     JButton jButton = new JButton(getMsg(paramString));
/*  584 */     jButton.setMnemonic(getMnemonic(paramString));
/*  585 */     jButton.addActionListener(paramActionListener);
/*      */     
/*  587 */     return jButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static JButton createExitButton(String paramString, ActionListener paramActionListener) {
/*  594 */     String str = getMsg(paramString);
/*  595 */     JButton jButton = new JButton(str);
/*  596 */     jButton.addActionListener(paramActionListener);
/*  597 */     jButton.getAccessibleContext().setAccessibleDescription(str);
/*  598 */     return jButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static JCheckBox createCheckBox(String paramString, ActionListener paramActionListener) {
/*  605 */     JCheckBox jCheckBox = new JCheckBox(getMsg(paramString));
/*  606 */     jCheckBox.setMnemonic(getMnemonic(paramString));
/*  607 */     jCheckBox.addActionListener(paramActionListener);
/*      */     
/*  609 */     return jCheckBox;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static JRadioButton createRadioButton(String paramString, ActionListener paramActionListener) {
/*  619 */     JRadioButton jRadioButton = new JRadioButton(getMsg(paramString));
/*  620 */     jRadioButton.setMnemonic(getMnemonic(paramString));
/*  621 */     jRadioButton.addActionListener(paramActionListener);
/*      */     
/*  623 */     return jRadioButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void showNoPrintService(GraphicsConfiguration paramGraphicsConfiguration) {
/*  631 */     Frame frame = new Frame(paramGraphicsConfiguration);
/*  632 */     JOptionPane.showMessageDialog(frame, 
/*  633 */         getMsg("dialog.noprintermsg"));
/*  634 */     frame.dispose();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addToGB(Component paramComponent, Container paramContainer, GridBagLayout paramGridBagLayout, GridBagConstraints paramGridBagConstraints) {
/*  645 */     paramGridBagLayout.setConstraints(paramComponent, paramGridBagConstraints);
/*  646 */     paramContainer.add(paramComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addToBG(AbstractButton paramAbstractButton, Container paramContainer, ButtonGroup paramButtonGroup) {
/*  655 */     paramButtonGroup.add(paramAbstractButton);
/*  656 */     paramContainer.add(paramAbstractButton);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class GeneralPanel
/*      */     extends JPanel
/*      */   {
/*      */     private ServiceDialog.PrintServicePanel pnlPrintService;
/*      */ 
/*      */     
/*      */     private ServiceDialog.PrintRangePanel pnlPrintRange;
/*      */ 
/*      */     
/*      */     private ServiceDialog.CopiesPanel pnlCopies;
/*      */ 
/*      */ 
/*      */     
/*      */     public GeneralPanel() {
/*  675 */       GridBagLayout gridBagLayout = new GridBagLayout();
/*  676 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/*  678 */       setLayout(gridBagLayout);
/*      */       
/*  680 */       gridBagConstraints.fill = 1;
/*  681 */       gridBagConstraints.insets = ServiceDialog.panelInsets;
/*  682 */       gridBagConstraints.weightx = 1.0D;
/*  683 */       gridBagConstraints.weighty = 1.0D;
/*      */       
/*  685 */       gridBagConstraints.gridwidth = 0;
/*  686 */       this.pnlPrintService = new ServiceDialog.PrintServicePanel();
/*  687 */       ServiceDialog.addToGB(this.pnlPrintService, this, gridBagLayout, gridBagConstraints);
/*      */       
/*  689 */       gridBagConstraints.gridwidth = -1;
/*  690 */       this.pnlPrintRange = new ServiceDialog.PrintRangePanel();
/*  691 */       ServiceDialog.addToGB(this.pnlPrintRange, this, gridBagLayout, gridBagConstraints);
/*      */       
/*  693 */       gridBagConstraints.gridwidth = 0;
/*  694 */       this.pnlCopies = new ServiceDialog.CopiesPanel();
/*  695 */       ServiceDialog.addToGB(this.pnlCopies, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */     
/*      */     public boolean isPrintToFileRequested() {
/*  699 */       return this.pnlPrintService.isPrintToFileSelected();
/*      */     }
/*      */     
/*      */     public void updateInfo() {
/*  703 */       this.pnlPrintService.updateInfo();
/*  704 */       this.pnlPrintRange.updateInfo();
/*  705 */       this.pnlCopies.updateInfo();
/*      */     }
/*      */   }
/*      */   
/*      */   private class PrintServicePanel
/*      */     extends JPanel
/*      */     implements ActionListener, ItemListener, PopupMenuListener {
/*  712 */     private final String strTitle = ServiceDialog.getMsg("border.printservice");
/*      */     private FilePermission printToFilePermission;
/*      */     private JButton btnProperties;
/*      */     private JCheckBox cbPrintToFile;
/*      */     private JComboBox cbName;
/*      */     private JLabel lblType;
/*      */     private JLabel lblStatus;
/*      */     private JLabel lblInfo;
/*      */     private ServiceUIFactory uiFactory;
/*      */     private boolean changedService = false;
/*      */     private boolean filePermission;
/*      */     
/*      */     public PrintServicePanel() {
/*  725 */       this.uiFactory = ServiceDialog.this.psCurrent.getServiceUIFactory();
/*      */       
/*  727 */       GridBagLayout gridBagLayout = new GridBagLayout();
/*  728 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/*  730 */       setLayout(gridBagLayout);
/*  731 */       setBorder(BorderFactory.createTitledBorder(this.strTitle));
/*      */       
/*  733 */       String[] arrayOfString = new String[ServiceDialog.this.services.length];
/*  734 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*  735 */         arrayOfString[b] = ServiceDialog.this.services[b].getName();
/*      */       }
/*  737 */       this.cbName = new JComboBox<>(arrayOfString);
/*  738 */       this.cbName.setSelectedIndex(ServiceDialog.this.defaultServiceIndex);
/*  739 */       this.cbName.addItemListener(this);
/*  740 */       this.cbName.addPopupMenuListener(this);
/*      */       
/*  742 */       gridBagConstraints.fill = 1;
/*  743 */       gridBagConstraints.insets = ServiceDialog.compInsets;
/*      */       
/*  745 */       gridBagConstraints.weightx = 0.0D;
/*  746 */       JLabel jLabel = new JLabel(ServiceDialog.getMsg("label.psname"), 11);
/*  747 */       jLabel.setDisplayedMnemonic(ServiceDialog.getMnemonic("label.psname"));
/*  748 */       jLabel.setLabelFor(this.cbName);
/*  749 */       ServiceDialog.addToGB(jLabel, this, gridBagLayout, gridBagConstraints);
/*  750 */       gridBagConstraints.weightx = 1.0D;
/*  751 */       gridBagConstraints.gridwidth = -1;
/*  752 */       ServiceDialog.addToGB(this.cbName, this, gridBagLayout, gridBagConstraints);
/*  753 */       gridBagConstraints.weightx = 0.0D;
/*  754 */       gridBagConstraints.gridwidth = 0;
/*  755 */       this.btnProperties = ServiceDialog.createButton("button.properties", this);
/*  756 */       ServiceDialog.addToGB(this.btnProperties, this, gridBagLayout, gridBagConstraints);
/*      */       
/*  758 */       gridBagConstraints.weighty = 1.0D;
/*  759 */       this.lblStatus = addLabel(ServiceDialog.getMsg("label.status"), gridBagLayout, gridBagConstraints);
/*  760 */       this.lblStatus.setLabelFor((Component)null);
/*      */       
/*  762 */       this.lblType = addLabel(ServiceDialog.getMsg("label.pstype"), gridBagLayout, gridBagConstraints);
/*  763 */       this.lblType.setLabelFor((Component)null);
/*      */       
/*  765 */       gridBagConstraints.gridwidth = 1;
/*  766 */       ServiceDialog.addToGB(new JLabel(ServiceDialog.getMsg("label.info"), 11), this, gridBagLayout, gridBagConstraints);
/*      */       
/*  768 */       gridBagConstraints.gridwidth = -1;
/*  769 */       this.lblInfo = new JLabel();
/*  770 */       this.lblInfo.setLabelFor((Component)null);
/*      */       
/*  772 */       ServiceDialog.addToGB(this.lblInfo, this, gridBagLayout, gridBagConstraints);
/*      */       
/*  774 */       gridBagConstraints.gridwidth = 0;
/*  775 */       this.cbPrintToFile = ServiceDialog.createCheckBox("checkbox.printtofile", this);
/*  776 */       ServiceDialog.addToGB(this.cbPrintToFile, this, gridBagLayout, gridBagConstraints);
/*      */       
/*  778 */       this.filePermission = allowedToPrintToFile();
/*      */     }
/*      */     
/*      */     public boolean isPrintToFileSelected() {
/*  782 */       return this.cbPrintToFile.isSelected();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private JLabel addLabel(String param1String, GridBagLayout param1GridBagLayout, GridBagConstraints param1GridBagConstraints) {
/*  788 */       param1GridBagConstraints.gridwidth = 1;
/*  789 */       ServiceDialog.addToGB(new JLabel(param1String, 11), this, param1GridBagLayout, param1GridBagConstraints);
/*      */       
/*  791 */       param1GridBagConstraints.gridwidth = 0;
/*  792 */       JLabel jLabel = new JLabel();
/*  793 */       ServiceDialog.addToGB(jLabel, this, param1GridBagLayout, param1GridBagConstraints);
/*      */       
/*  795 */       return jLabel;
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  799 */       Object object = param1ActionEvent.getSource();
/*      */       
/*  801 */       if (object == this.btnProperties && 
/*  802 */         this.uiFactory != null) {
/*  803 */         JDialog jDialog = (JDialog)this.uiFactory.getUI(3, "javax.swing.JDialog");
/*      */ 
/*      */ 
/*      */         
/*  807 */         if (jDialog != null) {
/*  808 */           jDialog.show();
/*      */         } else {
/*  810 */           DocumentPropertiesUI documentPropertiesUI = null;
/*      */ 
/*      */           
/*      */           try {
/*  814 */             documentPropertiesUI = (DocumentPropertiesUI)this.uiFactory.getUI(199, DocumentPropertiesUI.DOCPROPERTIESCLASSNAME);
/*      */           }
/*  816 */           catch (Exception exception) {}
/*      */           
/*  818 */           if (documentPropertiesUI != null) {
/*      */             
/*  820 */             PrinterJobWrapper printerJobWrapper = (PrinterJobWrapper)ServiceDialog.this.asCurrent.get(PrinterJobWrapper.class);
/*  821 */             if (printerJobWrapper == null) {
/*      */               return;
/*      */             }
/*  824 */             PrinterJob printerJob = printerJobWrapper.getPrinterJob();
/*  825 */             if (printerJob == null) {
/*      */               return;
/*      */             }
/*      */ 
/*      */             
/*  830 */             PrintRequestAttributeSet printRequestAttributeSet = documentPropertiesUI.showDocumentProperties(printerJob, ServiceDialog.this, ServiceDialog.this.psCurrent, ServiceDialog.this.asCurrent);
/*  831 */             if (printRequestAttributeSet != null) {
/*  832 */               ServiceDialog.this.asCurrent.addAll(printRequestAttributeSet);
/*  833 */               ServiceDialog.this.updatePanels();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void itemStateChanged(ItemEvent param1ItemEvent) {
/*  842 */       if (param1ItemEvent.getStateChange() == 1) {
/*  843 */         int i = this.cbName.getSelectedIndex();
/*      */         
/*  845 */         if (i >= 0 && i < ServiceDialog.this.services.length && 
/*  846 */           !ServiceDialog.this.services[i].equals(ServiceDialog.this.psCurrent)) {
/*  847 */           ServiceDialog.this.psCurrent = ServiceDialog.this.services[i];
/*  848 */           this.uiFactory = ServiceDialog.this.psCurrent.getServiceUIFactory();
/*  849 */           this.changedService = true;
/*      */ 
/*      */           
/*  852 */           Destination destination = (Destination)ServiceDialog.this.asOriginal.get(Destination.class);
/*      */           
/*  854 */           if ((destination != null || isPrintToFileSelected()) && ServiceDialog.this
/*  855 */             .psCurrent.isAttributeCategorySupported((Class)Destination.class)) {
/*      */ 
/*      */             
/*  858 */             if (destination != null) {
/*  859 */               ServiceDialog.this.asCurrent.add(destination);
/*      */             } else {
/*      */               
/*  862 */               destination = (Destination)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)Destination.class);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  867 */               if (destination == null) {
/*      */                 try {
/*  869 */                   destination = new Destination(new URI("file:out.prn"));
/*      */                 }
/*  871 */                 catch (URISyntaxException uRISyntaxException) {}
/*      */               }
/*      */ 
/*      */               
/*  875 */               if (destination != null) {
/*  876 */                 ServiceDialog.this.asCurrent.add(destination);
/*      */               }
/*      */             } 
/*      */           } else {
/*  880 */             ServiceDialog.this.asCurrent.remove(Destination.class);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void popupMenuWillBecomeVisible(PopupMenuEvent param1PopupMenuEvent) {
/*  888 */       this.changedService = false;
/*      */     }
/*      */     
/*      */     public void popupMenuWillBecomeInvisible(PopupMenuEvent param1PopupMenuEvent) {
/*  892 */       if (this.changedService) {
/*  893 */         this.changedService = false;
/*  894 */         ServiceDialog.this.updatePanels();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void popupMenuCanceled(PopupMenuEvent param1PopupMenuEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean allowedToPrintToFile() {
/*      */       try {
/*  906 */         throwPrintToFile();
/*  907 */         return true;
/*  908 */       } catch (SecurityException securityException) {
/*  909 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void throwPrintToFile() {
/*  919 */       SecurityManager securityManager = System.getSecurityManager();
/*  920 */       if (securityManager != null) {
/*  921 */         if (this.printToFilePermission == null) {
/*  922 */           this.printToFilePermission = new FilePermission("<<ALL FILES>>", "read,write");
/*      */         }
/*      */         
/*  925 */         securityManager.checkPermission(this.printToFilePermission);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void updateInfo() {
/*  930 */       Class<Destination> clazz = Destination.class;
/*  931 */       boolean bool1 = false;
/*  932 */       boolean bool2 = false;
/*      */       
/*  934 */       boolean bool3 = this.filePermission ? allowedToPrintToFile() : false;
/*      */ 
/*      */       
/*  937 */       if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz)) {
/*  938 */         bool1 = true;
/*      */       }
/*  940 */       Destination destination = (Destination)ServiceDialog.this.asCurrent.get(clazz);
/*  941 */       if (destination != null) {
/*  942 */         bool2 = true;
/*      */       }
/*  944 */       this.cbPrintToFile.setEnabled((bool1 && bool3));
/*  945 */       this.cbPrintToFile.setSelected((bool2 && bool3 && bool1));
/*      */ 
/*      */ 
/*      */       
/*  949 */       Object object1 = ServiceDialog.this.psCurrent.getAttribute((Class)PrinterMakeAndModel.class);
/*  950 */       if (object1 != null) {
/*  951 */         this.lblType.setText(object1.toString());
/*      */       }
/*      */       
/*  954 */       Object object2 = ServiceDialog.this.psCurrent.getAttribute((Class)PrinterIsAcceptingJobs.class);
/*  955 */       if (object2 != null) {
/*  956 */         this.lblStatus.setText(ServiceDialog.getMsg(object2.toString()));
/*      */       }
/*  958 */       Object object3 = ServiceDialog.this.psCurrent.getAttribute((Class)PrinterInfo.class);
/*  959 */       if (object3 != null) {
/*  960 */         this.lblInfo.setText(object3.toString());
/*      */       }
/*  962 */       this.btnProperties.setEnabled((this.uiFactory != null));
/*      */     } }
/*      */   private class PrintRangePanel extends JPanel implements ActionListener, FocusListener { private final String strTitle; private final PageRanges prAll; private JRadioButton rbAll; private JRadioButton rbPages; private JRadioButton rbSelect; private JFormattedTextField tfRangeFrom; private JFormattedTextField tfRangeTo; private JLabel lblRangeTo;
/*      */     private boolean prSupported;
/*      */     
/*      */     public PrintRangePanel() {
/*      */       NumberFormatter numberFormatter2;
/*  969 */       this.strTitle = ServiceDialog.getMsg("border.printrange");
/*  970 */       this.prAll = new PageRanges(1, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  979 */       GridBagLayout gridBagLayout = new GridBagLayout();
/*  980 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/*  982 */       setLayout(gridBagLayout);
/*  983 */       setBorder(BorderFactory.createTitledBorder(this.strTitle));
/*      */       
/*  985 */       gridBagConstraints.fill = 1;
/*  986 */       gridBagConstraints.insets = ServiceDialog.compInsets;
/*  987 */       gridBagConstraints.gridwidth = 0;
/*      */       
/*  989 */       ButtonGroup buttonGroup = new ButtonGroup();
/*  990 */       JPanel jPanel1 = new JPanel(new FlowLayout(3));
/*  991 */       this.rbAll = ServiceDialog.createRadioButton("radiobutton.rangeall", this);
/*  992 */       this.rbAll.setSelected(true);
/*  993 */       buttonGroup.add(this.rbAll);
/*  994 */       jPanel1.add(this.rbAll);
/*  995 */       ServiceDialog.addToGB(jPanel1, this, gridBagLayout, gridBagConstraints);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1010 */       JPanel jPanel2 = new JPanel(new FlowLayout(3));
/* 1011 */       this.rbPages = ServiceDialog.createRadioButton("radiobutton.rangepages", this);
/* 1012 */       buttonGroup.add(this.rbPages);
/* 1013 */       jPanel2.add(this.rbPages);
/* 1014 */       DecimalFormat decimalFormat = new DecimalFormat("####0");
/* 1015 */       decimalFormat.setMinimumFractionDigits(0);
/* 1016 */       decimalFormat.setMaximumFractionDigits(0);
/* 1017 */       decimalFormat.setMinimumIntegerDigits(0);
/* 1018 */       decimalFormat.setMaximumIntegerDigits(5);
/* 1019 */       decimalFormat.setParseIntegerOnly(true);
/* 1020 */       decimalFormat.setDecimalSeparatorAlwaysShown(false);
/* 1021 */       NumberFormatter numberFormatter1 = new NumberFormatter(decimalFormat);
/* 1022 */       numberFormatter1.setMinimum(new Integer(1));
/* 1023 */       numberFormatter1.setMaximum(new Integer(2147483647));
/* 1024 */       numberFormatter1.setAllowsInvalid(true);
/* 1025 */       numberFormatter1.setCommitsOnValidEdit(true);
/* 1026 */       this.tfRangeFrom = new JFormattedTextField(numberFormatter1);
/* 1027 */       this.tfRangeFrom.setColumns(4);
/* 1028 */       this.tfRangeFrom.setEnabled(false);
/* 1029 */       this.tfRangeFrom.addActionListener(this);
/* 1030 */       this.tfRangeFrom.addFocusListener(this);
/* 1031 */       this.tfRangeFrom.setFocusLostBehavior(3);
/*      */       
/* 1033 */       this.tfRangeFrom.getAccessibleContext().setAccessibleName(
/* 1034 */           ServiceDialog.getMsg("radiobutton.rangepages"));
/* 1035 */       jPanel2.add(this.tfRangeFrom);
/* 1036 */       this.lblRangeTo = new JLabel(ServiceDialog.getMsg("label.rangeto"));
/* 1037 */       this.lblRangeTo.setEnabled(false);
/* 1038 */       jPanel2.add(this.lblRangeTo);
/*      */       
/*      */       try {
/* 1041 */         numberFormatter2 = (NumberFormatter)numberFormatter1.clone();
/* 1042 */       } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 1043 */         numberFormatter2 = new NumberFormatter();
/*      */       } 
/* 1045 */       this.tfRangeTo = new JFormattedTextField(numberFormatter2);
/* 1046 */       this.tfRangeTo.setColumns(4);
/* 1047 */       this.tfRangeTo.setEnabled(false);
/* 1048 */       this.tfRangeTo.addFocusListener(this);
/* 1049 */       this.tfRangeTo.getAccessibleContext().setAccessibleName(
/* 1050 */           ServiceDialog.getMsg("label.rangeto"));
/* 1051 */       jPanel2.add(this.tfRangeTo);
/* 1052 */       ServiceDialog.addToGB(jPanel2, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1056 */       Object object = param1ActionEvent.getSource();
/* 1057 */       SunPageSelection sunPageSelection = SunPageSelection.ALL;
/*      */       
/* 1059 */       setupRangeWidgets();
/*      */       
/* 1061 */       if (object == this.rbAll) {
/* 1062 */         ServiceDialog.this.asCurrent.add(this.prAll);
/* 1063 */       } else if (object == this.rbSelect) {
/* 1064 */         sunPageSelection = SunPageSelection.SELECTION;
/* 1065 */       } else if (object == this.rbPages || object == this.tfRangeFrom || object == this.tfRangeTo) {
/*      */ 
/*      */         
/* 1068 */         updateRangeAttribute();
/* 1069 */         sunPageSelection = SunPageSelection.RANGE;
/*      */       } 
/*      */       
/* 1072 */       if (ServiceDialog.this.isAWT) {
/* 1073 */         ServiceDialog.this.asCurrent.add(sunPageSelection);
/*      */       }
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 1078 */       Object object = param1FocusEvent.getSource();
/*      */       
/* 1080 */       if (object == this.tfRangeFrom || object == this.tfRangeTo) {
/* 1081 */         updateRangeAttribute();
/*      */       }
/*      */     }
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {}
/*      */     
/*      */     private void setupRangeWidgets() {
/* 1088 */       boolean bool = (this.rbPages.isSelected() && this.prSupported) ? true : false;
/* 1089 */       this.tfRangeFrom.setEnabled(bool);
/* 1090 */       this.tfRangeTo.setEnabled(bool);
/* 1091 */       this.lblRangeTo.setEnabled(bool);
/*      */     }
/*      */     private void updateRangeAttribute() {
/*      */       byte b1, b2;
/* 1095 */       String str1 = this.tfRangeFrom.getText();
/* 1096 */       String str2 = this.tfRangeTo.getText();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1102 */         b1 = Integer.parseInt(str1);
/* 1103 */       } catch (NumberFormatException numberFormatException) {
/* 1104 */         b1 = 1;
/*      */       } 
/*      */       
/*      */       try {
/* 1108 */         b2 = Integer.parseInt(str2);
/* 1109 */       } catch (NumberFormatException numberFormatException) {
/* 1110 */         b2 = b1;
/*      */       } 
/*      */       
/* 1113 */       if (b1 < 1) {
/* 1114 */         b1 = 1;
/* 1115 */         this.tfRangeFrom.setValue(new Integer(1));
/*      */       } 
/*      */       
/* 1118 */       if (b2 < b1) {
/* 1119 */         b2 = b1;
/* 1120 */         this.tfRangeTo.setValue(new Integer(b1));
/*      */       } 
/*      */       
/* 1123 */       PageRanges pageRanges = new PageRanges(b1, b2);
/* 1124 */       ServiceDialog.this.asCurrent.add(pageRanges);
/*      */     }
/*      */     
/*      */     public void updateInfo() {
/* 1128 */       Class<PageRanges> clazz = PageRanges.class;
/* 1129 */       this.prSupported = false;
/*      */       
/* 1131 */       if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz) || ServiceDialog.this
/* 1132 */         .isAWT) {
/* 1133 */         this.prSupported = true;
/*      */       }
/*      */       
/* 1136 */       SunPageSelection sunPageSelection = SunPageSelection.ALL;
/* 1137 */       int i = 1;
/* 1138 */       int j = 1;
/*      */       
/* 1140 */       PageRanges pageRanges = (PageRanges)ServiceDialog.this.asCurrent.get(clazz);
/* 1141 */       if (pageRanges != null && 
/* 1142 */         !pageRanges.equals(this.prAll)) {
/* 1143 */         sunPageSelection = SunPageSelection.RANGE;
/*      */         
/* 1145 */         int[][] arrayOfInt = pageRanges.getMembers();
/* 1146 */         if (arrayOfInt.length > 0 && (arrayOfInt[0]).length > 1) {
/*      */           
/* 1148 */           i = arrayOfInt[0][0];
/* 1149 */           j = arrayOfInt[0][1];
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1154 */       if (ServiceDialog.this.isAWT) {
/* 1155 */         sunPageSelection = (SunPageSelection)ServiceDialog.this.asCurrent.get(SunPageSelection.class);
/*      */       }
/*      */ 
/*      */       
/* 1159 */       if (sunPageSelection == SunPageSelection.ALL) {
/* 1160 */         this.rbAll.setSelected(true);
/* 1161 */       } else if (sunPageSelection != SunPageSelection.SELECTION) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1168 */         this.rbPages.setSelected(true);
/*      */       } 
/* 1170 */       this.tfRangeFrom.setValue(new Integer(i));
/* 1171 */       this.tfRangeTo.setValue(new Integer(j));
/* 1172 */       this.rbAll.setEnabled(this.prSupported);
/* 1173 */       this.rbPages.setEnabled(this.prSupported);
/* 1174 */       setupRangeWidgets();
/*      */     } }
/*      */ 
/*      */   
/*      */   private class CopiesPanel
/*      */     extends JPanel
/*      */     implements ActionListener, ChangeListener {
/* 1181 */     private final String strTitle = ServiceDialog.getMsg("border.copies");
/*      */     
/*      */     private SpinnerNumberModel snModel;
/*      */     
/*      */     private JSpinner spinCopies;
/*      */     private JLabel lblCopies;
/*      */     private JCheckBox cbCollate;
/*      */     private boolean scSupported;
/*      */     
/*      */     public CopiesPanel() {
/* 1191 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 1192 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/* 1194 */       setLayout(gridBagLayout);
/* 1195 */       setBorder(BorderFactory.createTitledBorder(this.strTitle));
/*      */       
/* 1197 */       gridBagConstraints.fill = 2;
/* 1198 */       gridBagConstraints.insets = ServiceDialog.compInsets;
/*      */       
/* 1200 */       this.lblCopies = new JLabel(ServiceDialog.getMsg("label.numcopies"), 11);
/* 1201 */       this.lblCopies.setDisplayedMnemonic(ServiceDialog.getMnemonic("label.numcopies"));
/* 1202 */       this.lblCopies.getAccessibleContext().setAccessibleName(
/* 1203 */           ServiceDialog.getMsg("label.numcopies"));
/* 1204 */       ServiceDialog.addToGB(this.lblCopies, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1206 */       this.snModel = new SpinnerNumberModel(1, 1, 999, 1);
/* 1207 */       this.spinCopies = new JSpinner(this.snModel);
/* 1208 */       this.lblCopies.setLabelFor(this.spinCopies);
/*      */       
/* 1210 */       ((JSpinner.NumberEditor)this.spinCopies.getEditor()).getTextField().setColumns(3);
/* 1211 */       this.spinCopies.addChangeListener(this);
/* 1212 */       gridBagConstraints.gridwidth = 0;
/* 1213 */       ServiceDialog.addToGB(this.spinCopies, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1215 */       this.cbCollate = ServiceDialog.createCheckBox("checkbox.collate", this);
/* 1216 */       this.cbCollate.setEnabled(false);
/* 1217 */       ServiceDialog.addToGB(this.cbCollate, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1221 */       if (this.cbCollate.isSelected()) {
/* 1222 */         ServiceDialog.this.asCurrent.add(SheetCollate.COLLATED);
/*      */       } else {
/* 1224 */         ServiceDialog.this.asCurrent.add(SheetCollate.UNCOLLATED);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 1229 */       updateCollateCB();
/*      */       
/* 1231 */       ServiceDialog.this.asCurrent.add(new Copies(this.snModel.getNumber().intValue()));
/*      */     }
/*      */     
/*      */     private void updateCollateCB() {
/* 1235 */       int i = this.snModel.getNumber().intValue();
/* 1236 */       if (ServiceDialog.this.isAWT) {
/* 1237 */         this.cbCollate.setEnabled(true);
/*      */       } else {
/* 1239 */         this.cbCollate.setEnabled((i > 1 && this.scSupported));
/*      */       } 
/*      */     } public void updateInfo() {
/*      */       byte b;
/*      */       int i;
/* 1244 */       Class<Copies> clazz = Copies.class;
/* 1245 */       Class<CopiesSupported> clazz1 = CopiesSupported.class;
/* 1246 */       Class<SheetCollate> clazz2 = SheetCollate.class;
/* 1247 */       boolean bool = false;
/* 1248 */       this.scSupported = false;
/*      */ 
/*      */       
/* 1251 */       if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz)) {
/* 1252 */         bool = true;
/*      */       }
/*      */       
/* 1255 */       CopiesSupported copiesSupported = (CopiesSupported)ServiceDialog.this.psCurrent.getSupportedAttributeValues((Class)clazz, null, null);
/*      */       
/* 1257 */       if (copiesSupported == null) {
/* 1258 */         copiesSupported = new CopiesSupported(1, 999);
/*      */       }
/* 1260 */       Copies copies = (Copies)ServiceDialog.this.asCurrent.get(clazz);
/* 1261 */       if (copies == null) {
/* 1262 */         copies = (Copies)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz);
/* 1263 */         if (copies == null) {
/* 1264 */           copies = new Copies(1);
/*      */         }
/*      */       } 
/* 1267 */       this.spinCopies.setEnabled(bool);
/* 1268 */       this.lblCopies.setEnabled(bool);
/*      */       
/* 1270 */       int[][] arrayOfInt = copiesSupported.getMembers();
/*      */       
/* 1272 */       if (arrayOfInt.length > 0 && (arrayOfInt[0]).length > 0) {
/* 1273 */         b = arrayOfInt[0][0];
/* 1274 */         i = arrayOfInt[0][1];
/*      */       } else {
/* 1276 */         b = 1;
/* 1277 */         i = Integer.MAX_VALUE;
/*      */       } 
/* 1279 */       this.snModel.setMinimum(new Integer(b));
/* 1280 */       this.snModel.setMaximum(new Integer(i));
/*      */       
/* 1282 */       int j = copies.getValue();
/* 1283 */       if (j < b || j > i) {
/* 1284 */         j = b;
/*      */       }
/* 1286 */       this.snModel.setValue(new Integer(j));
/*      */ 
/*      */       
/* 1289 */       if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz2)) {
/* 1290 */         this.scSupported = true;
/*      */       }
/* 1292 */       SheetCollate sheetCollate = (SheetCollate)ServiceDialog.this.asCurrent.get(clazz2);
/* 1293 */       if (sheetCollate == null) {
/* 1294 */         sheetCollate = (SheetCollate)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz2);
/* 1295 */         if (sheetCollate == null) {
/* 1296 */           sheetCollate = SheetCollate.UNCOLLATED;
/*      */         }
/*      */       } 
/* 1299 */       this.cbCollate.setSelected((sheetCollate == SheetCollate.COLLATED));
/* 1300 */       updateCollateCB();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class PageSetupPanel
/*      */     extends JPanel
/*      */   {
/*      */     private ServiceDialog.MediaPanel pnlMedia;
/*      */ 
/*      */     
/*      */     private ServiceDialog.OrientationPanel pnlOrientation;
/*      */ 
/*      */     
/*      */     private ServiceDialog.MarginsPanel pnlMargins;
/*      */ 
/*      */ 
/*      */     
/*      */     public PageSetupPanel() {
/* 1320 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 1321 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/* 1323 */       setLayout(gridBagLayout);
/*      */       
/* 1325 */       gridBagConstraints.fill = 1;
/* 1326 */       gridBagConstraints.insets = ServiceDialog.panelInsets;
/* 1327 */       gridBagConstraints.weightx = 1.0D;
/* 1328 */       gridBagConstraints.weighty = 1.0D;
/*      */       
/* 1330 */       gridBagConstraints.gridwidth = 0;
/* 1331 */       this.pnlMedia = new ServiceDialog.MediaPanel();
/* 1332 */       ServiceDialog.addToGB(this.pnlMedia, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1334 */       this.pnlOrientation = new ServiceDialog.OrientationPanel();
/* 1335 */       gridBagConstraints.gridwidth = -1;
/* 1336 */       ServiceDialog.addToGB(this.pnlOrientation, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1338 */       this.pnlMargins = new ServiceDialog.MarginsPanel();
/* 1339 */       this.pnlOrientation.addOrientationListener(this.pnlMargins);
/* 1340 */       this.pnlMedia.addMediaListener(this.pnlMargins);
/* 1341 */       gridBagConstraints.gridwidth = 0;
/* 1342 */       ServiceDialog.addToGB(this.pnlMargins, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */     
/*      */     public void updateInfo() {
/* 1346 */       this.pnlMedia.updateInfo();
/* 1347 */       this.pnlOrientation.updateInfo();
/* 1348 */       this.pnlMargins.updateInfo();
/*      */     } }
/*      */   private class MarginsPanel extends JPanel implements ActionListener, FocusListener { private final String strTitle; private JFormattedTextField leftMargin; private JFormattedTextField rightMargin; private JFormattedTextField topMargin; private JFormattedTextField bottomMargin; private JLabel lblLeft; private JLabel lblRight; private JLabel lblTop;
/*      */     private JLabel lblBottom;
/*      */     
/*      */     public MarginsPanel() {
/*      */       DecimalFormat decimalFormat;
/* 1355 */       this.strTitle = ServiceDialog.getMsg("border.margins");
/*      */ 
/*      */ 
/*      */       
/* 1359 */       this.units = 1000;
/*      */       
/* 1361 */       this.lmVal = -1.0F; this.rmVal = -1.0F; this.tmVal = -1.0F; this.bmVal = -1.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1368 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 1369 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 1370 */       gridBagConstraints.fill = 2;
/* 1371 */       gridBagConstraints.weightx = 1.0D;
/* 1372 */       gridBagConstraints.weighty = 0.0D;
/* 1373 */       gridBagConstraints.insets = ServiceDialog.compInsets;
/*      */       
/* 1375 */       setLayout(gridBagLayout);
/* 1376 */       setBorder(BorderFactory.createTitledBorder(this.strTitle));
/*      */       
/* 1378 */       String str1 = "label.millimetres";
/* 1379 */       String str2 = Locale.getDefault().getCountry();
/* 1380 */       if (str2 != null && (str2
/* 1381 */         .equals("") || str2
/* 1382 */         .equals(Locale.US.getCountry()) || str2
/* 1383 */         .equals(Locale.CANADA.getCountry()))) {
/* 1384 */         str1 = "label.inches";
/* 1385 */         this.units = 25400;
/*      */       } 
/* 1387 */       String str3 = ServiceDialog.getMsg(str1);
/*      */ 
/*      */       
/* 1390 */       if (this.units == 1000) {
/* 1391 */         decimalFormat = new DecimalFormat("###.##");
/* 1392 */         decimalFormat.setMaximumIntegerDigits(3);
/*      */       } else {
/* 1394 */         decimalFormat = new DecimalFormat("##.##");
/* 1395 */         decimalFormat.setMaximumIntegerDigits(2);
/*      */       } 
/*      */       
/* 1398 */       decimalFormat.setMinimumFractionDigits(1);
/* 1399 */       decimalFormat.setMaximumFractionDigits(2);
/* 1400 */       decimalFormat.setMinimumIntegerDigits(1);
/* 1401 */       decimalFormat.setParseIntegerOnly(false);
/* 1402 */       decimalFormat.setDecimalSeparatorAlwaysShown(true);
/* 1403 */       NumberFormatter numberFormatter = new NumberFormatter(decimalFormat);
/* 1404 */       numberFormatter.setMinimum(new Float(0.0F));
/* 1405 */       numberFormatter.setMaximum(new Float(999.0F));
/* 1406 */       numberFormatter.setAllowsInvalid(true);
/* 1407 */       numberFormatter.setCommitsOnValidEdit(true);
/*      */       
/* 1409 */       this.leftMargin = new JFormattedTextField(numberFormatter);
/* 1410 */       this.leftMargin.addFocusListener(this);
/* 1411 */       this.leftMargin.addActionListener(this);
/* 1412 */       this.leftMargin.getAccessibleContext().setAccessibleName(
/* 1413 */           ServiceDialog.getMsg("label.leftmargin"));
/* 1414 */       this.rightMargin = new JFormattedTextField(numberFormatter);
/* 1415 */       this.rightMargin.addFocusListener(this);
/* 1416 */       this.rightMargin.addActionListener(this);
/* 1417 */       this.rightMargin.getAccessibleContext().setAccessibleName(
/* 1418 */           ServiceDialog.getMsg("label.rightmargin"));
/* 1419 */       this.topMargin = new JFormattedTextField(numberFormatter);
/* 1420 */       this.topMargin.addFocusListener(this);
/* 1421 */       this.topMargin.addActionListener(this);
/* 1422 */       this.topMargin.getAccessibleContext().setAccessibleName(
/* 1423 */           ServiceDialog.getMsg("label.topmargin"));
/* 1424 */       this.topMargin = new JFormattedTextField(numberFormatter);
/* 1425 */       this.bottomMargin = new JFormattedTextField(numberFormatter);
/* 1426 */       this.bottomMargin.addFocusListener(this);
/* 1427 */       this.bottomMargin.addActionListener(this);
/* 1428 */       this.bottomMargin.getAccessibleContext().setAccessibleName(
/* 1429 */           ServiceDialog.getMsg("label.bottommargin"));
/* 1430 */       this.topMargin = new JFormattedTextField(numberFormatter);
/* 1431 */       gridBagConstraints.gridwidth = -1;
/* 1432 */       this.lblLeft = new JLabel(ServiceDialog.getMsg("label.leftmargin") + " " + str3, 10);
/*      */       
/* 1434 */       this.lblLeft.setDisplayedMnemonic(ServiceDialog.getMnemonic("label.leftmargin"));
/* 1435 */       this.lblLeft.setLabelFor(this.leftMargin);
/* 1436 */       ServiceDialog.addToGB(this.lblLeft, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1438 */       gridBagConstraints.gridwidth = 0;
/* 1439 */       this.lblRight = new JLabel(ServiceDialog.getMsg("label.rightmargin") + " " + str3, 10);
/*      */       
/* 1441 */       this.lblRight.setDisplayedMnemonic(ServiceDialog.getMnemonic("label.rightmargin"));
/* 1442 */       this.lblRight.setLabelFor(this.rightMargin);
/* 1443 */       ServiceDialog.addToGB(this.lblRight, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1445 */       gridBagConstraints.gridwidth = -1;
/* 1446 */       ServiceDialog.addToGB(this.leftMargin, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1448 */       gridBagConstraints.gridwidth = 0;
/* 1449 */       ServiceDialog.addToGB(this.rightMargin, this, gridBagLayout, gridBagConstraints);
/*      */ 
/*      */       
/* 1452 */       ServiceDialog.addToGB(new JPanel(), this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1454 */       gridBagConstraints.gridwidth = -1;
/* 1455 */       this.lblTop = new JLabel(ServiceDialog.getMsg("label.topmargin") + " " + str3, 10);
/*      */       
/* 1457 */       this.lblTop.setDisplayedMnemonic(ServiceDialog.getMnemonic("label.topmargin"));
/* 1458 */       this.lblTop.setLabelFor(this.topMargin);
/* 1459 */       ServiceDialog.addToGB(this.lblTop, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1461 */       gridBagConstraints.gridwidth = 0;
/* 1462 */       this.lblBottom = new JLabel(ServiceDialog.getMsg("label.bottommargin") + " " + str3, 10);
/*      */       
/* 1464 */       this.lblBottom.setDisplayedMnemonic(ServiceDialog.getMnemonic("label.bottommargin"));
/* 1465 */       this.lblBottom.setLabelFor(this.bottomMargin);
/* 1466 */       ServiceDialog.addToGB(this.lblBottom, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1468 */       gridBagConstraints.gridwidth = -1;
/* 1469 */       ServiceDialog.addToGB(this.topMargin, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1471 */       gridBagConstraints.gridwidth = 0;
/* 1472 */       ServiceDialog.addToGB(this.bottomMargin, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */     private int units; private float lmVal; private float rmVal; private float tmVal; private float bmVal; private Float lmObj; private Float rmObj; private Float tmObj; private Float bmObj;
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1477 */       Object object = param1ActionEvent.getSource();
/* 1478 */       updateMargins(object);
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 1482 */       Object object = param1FocusEvent.getSource();
/* 1483 */       updateMargins(object);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void updateMargins(Object param1Object) {
/* 1494 */       if (!(param1Object instanceof JFormattedTextField)) {
/*      */         return;
/*      */       }
/* 1497 */       JFormattedTextField jFormattedTextField = (JFormattedTextField)param1Object;
/* 1498 */       Float float_2 = (Float)jFormattedTextField.getValue();
/* 1499 */       if (float_2 == null) {
/*      */         return;
/*      */       }
/* 1502 */       if (jFormattedTextField == this.leftMargin && float_2.equals(this.lmObj)) {
/*      */         return;
/*      */       }
/* 1505 */       if (jFormattedTextField == this.rightMargin && float_2.equals(this.rmObj)) {
/*      */         return;
/*      */       }
/* 1508 */       if (jFormattedTextField == this.topMargin && float_2.equals(this.tmObj)) {
/*      */         return;
/*      */       }
/* 1511 */       if (jFormattedTextField == this.bottomMargin && float_2.equals(this.bmObj)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1516 */       Float float_1 = (Float)this.leftMargin.getValue();
/* 1517 */       float_2 = (Float)this.rightMargin.getValue();
/* 1518 */       Float float_3 = (Float)this.topMargin.getValue();
/* 1519 */       Float float_4 = (Float)this.bottomMargin.getValue();
/*      */       
/* 1521 */       float f1 = float_1.floatValue();
/* 1522 */       float f2 = float_2.floatValue();
/* 1523 */       float f3 = float_3.floatValue();
/* 1524 */       float f4 = float_4.floatValue();
/*      */ 
/*      */       
/* 1527 */       Class<OrientationRequested> clazz = OrientationRequested.class;
/*      */       
/* 1529 */       OrientationRequested orientationRequested = (OrientationRequested)ServiceDialog.this.asCurrent.get(clazz);
/*      */       
/* 1531 */       if (orientationRequested == null)
/*      */       {
/* 1533 */         orientationRequested = (OrientationRequested)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz);
/*      */       }
/*      */ 
/*      */       
/* 1537 */       if (orientationRequested == OrientationRequested.REVERSE_PORTRAIT) {
/* 1538 */         float f = f1; f1 = f2; f2 = f;
/* 1539 */         f = f3; f3 = f4; f4 = f;
/* 1540 */       } else if (orientationRequested == OrientationRequested.LANDSCAPE) {
/* 1541 */         float f = f1;
/* 1542 */         f1 = f3;
/* 1543 */         f3 = f2;
/* 1544 */         f2 = f4;
/* 1545 */         f4 = f;
/* 1546 */       } else if (orientationRequested == OrientationRequested.REVERSE_LANDSCAPE) {
/* 1547 */         float f = f1;
/* 1548 */         f1 = f4;
/* 1549 */         f4 = f2;
/* 1550 */         f2 = f3;
/* 1551 */         f3 = f;
/*      */       } 
/*      */       MediaPrintableArea mediaPrintableArea;
/* 1554 */       if ((mediaPrintableArea = validateMargins(f1, f2, f3, f4)) != null) {
/* 1555 */         ServiceDialog.this.asCurrent.add(mediaPrintableArea);
/* 1556 */         this.lmVal = f1;
/* 1557 */         this.rmVal = f2;
/* 1558 */         this.tmVal = f3;
/* 1559 */         this.bmVal = f4;
/* 1560 */         this.lmObj = float_1;
/* 1561 */         this.rmObj = float_2;
/* 1562 */         this.tmObj = float_3;
/* 1563 */         this.bmObj = float_4;
/*      */       } else {
/* 1565 */         if (this.lmObj == null || this.rmObj == null || this.tmObj == null || this.rmObj == null) {
/*      */           return;
/*      */         }
/*      */         
/* 1569 */         this.leftMargin.setValue(this.lmObj);
/* 1570 */         this.rightMargin.setValue(this.rmObj);
/* 1571 */         this.topMargin.setValue(this.tmObj);
/* 1572 */         this.bottomMargin.setValue(this.bmObj);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MediaPrintableArea validateMargins(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 1591 */       Class<MediaPrintableArea> clazz = MediaPrintableArea.class;
/*      */       
/* 1593 */       MediaPrintableArea mediaPrintableArea = null;
/* 1594 */       MediaSize mediaSize = null;
/*      */       
/* 1596 */       Media media = (Media)ServiceDialog.this.asCurrent.get(Media.class);
/* 1597 */       if (media == null || !(media instanceof MediaSizeName)) {
/* 1598 */         media = (Media)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)Media.class);
/*      */       }
/* 1600 */       if (media != null && media instanceof MediaSizeName) {
/* 1601 */         MediaSizeName mediaSizeName = (MediaSizeName)media;
/* 1602 */         mediaSize = MediaSize.getMediaSizeForName(mediaSizeName);
/*      */       } 
/* 1604 */       if (mediaSize == null) {
/* 1605 */         mediaSize = new MediaSize(8.5F, 11.0F, 25400);
/*      */       }
/*      */       
/* 1608 */       if (media != null) {
/*      */         
/* 1610 */         HashPrintRequestAttributeSet hashPrintRequestAttributeSet = new HashPrintRequestAttributeSet(ServiceDialog.this.asCurrent);
/* 1611 */         hashPrintRequestAttributeSet.add(media);
/*      */ 
/*      */         
/* 1614 */         Object object = ServiceDialog.this.psCurrent.getSupportedAttributeValues((Class)clazz, ServiceDialog.this
/* 1615 */             .docFlavor, hashPrintRequestAttributeSet);
/*      */         
/* 1617 */         if (object instanceof MediaPrintableArea[] && ((MediaPrintableArea[])object).length > 0)
/*      */         {
/* 1619 */           mediaPrintableArea = ((MediaPrintableArea[])object)[0];
/*      */         }
/*      */       } 
/*      */       
/* 1623 */       if (mediaPrintableArea == null)
/*      */       {
/*      */         
/* 1626 */         mediaPrintableArea = new MediaPrintableArea(0.0F, 0.0F, mediaSize.getX(this.units), mediaSize.getY(this.units), this.units);
/*      */       }
/*      */ 
/*      */       
/* 1630 */       float f1 = mediaSize.getX(this.units);
/* 1631 */       float f2 = mediaSize.getY(this.units);
/* 1632 */       float f3 = param1Float1;
/* 1633 */       float f4 = param1Float3;
/* 1634 */       float f5 = f1 - param1Float1 - param1Float2;
/* 1635 */       float f6 = f2 - param1Float3 - param1Float4;
/*      */       
/* 1637 */       if (f5 <= 0.0F || f6 <= 0.0F || f3 < 0.0F || f4 < 0.0F || f3 < mediaPrintableArea
/* 1638 */         .getX(this.units) || f5 > mediaPrintableArea.getWidth(this.units) || f4 < mediaPrintableArea
/* 1639 */         .getY(this.units) || f6 > mediaPrintableArea.getHeight(this.units)) {
/* 1640 */         return null;
/*      */       }
/* 1642 */       return new MediaPrintableArea(param1Float1, param1Float3, f5, f6, this.units);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void updateInfo() {
/*      */       float f4, f5;
/* 1661 */       if (ServiceDialog.this.isAWT) {
/* 1662 */         this.leftMargin.setEnabled(false);
/* 1663 */         this.rightMargin.setEnabled(false);
/* 1664 */         this.topMargin.setEnabled(false);
/* 1665 */         this.bottomMargin.setEnabled(false);
/* 1666 */         this.lblLeft.setEnabled(false);
/* 1667 */         this.lblRight.setEnabled(false);
/* 1668 */         this.lblTop.setEnabled(false);
/* 1669 */         this.lblBottom.setEnabled(false);
/*      */         
/*      */         return;
/*      */       } 
/* 1673 */       Class<MediaPrintableArea> clazz = MediaPrintableArea.class;
/*      */       
/* 1675 */       MediaPrintableArea mediaPrintableArea1 = (MediaPrintableArea)ServiceDialog.this.asCurrent.get(clazz);
/* 1676 */       MediaPrintableArea mediaPrintableArea2 = null;
/* 1677 */       MediaSize mediaSize = null;
/*      */       
/* 1679 */       Media media = (Media)ServiceDialog.this.asCurrent.get(Media.class);
/* 1680 */       if (media == null || !(media instanceof MediaSizeName)) {
/* 1681 */         media = (Media)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)Media.class);
/*      */       }
/* 1683 */       if (media != null && media instanceof MediaSizeName) {
/* 1684 */         MediaSizeName mediaSizeName = (MediaSizeName)media;
/* 1685 */         mediaSize = MediaSize.getMediaSizeForName(mediaSizeName);
/*      */       } 
/* 1687 */       if (mediaSize == null) {
/* 1688 */         mediaSize = new MediaSize(8.5F, 11.0F, 25400);
/*      */       }
/*      */       
/* 1691 */       if (media != null) {
/*      */         
/* 1693 */         HashPrintRequestAttributeSet hashPrintRequestAttributeSet = new HashPrintRequestAttributeSet(ServiceDialog.this.asCurrent);
/* 1694 */         hashPrintRequestAttributeSet.add(media);
/*      */ 
/*      */         
/* 1697 */         Object object = ServiceDialog.this.psCurrent.getSupportedAttributeValues((Class)clazz, ServiceDialog.this
/* 1698 */             .docFlavor, hashPrintRequestAttributeSet);
/*      */         
/* 1700 */         if (object instanceof MediaPrintableArea[] && ((MediaPrintableArea[])object).length > 0) {
/*      */           
/* 1702 */           mediaPrintableArea2 = ((MediaPrintableArea[])object)[0];
/*      */         }
/* 1704 */         else if (object instanceof MediaPrintableArea) {
/* 1705 */           mediaPrintableArea2 = (MediaPrintableArea)object;
/*      */         } 
/*      */       } 
/* 1708 */       if (mediaPrintableArea2 == null)
/*      */       {
/*      */         
/* 1711 */         mediaPrintableArea2 = new MediaPrintableArea(0.0F, 0.0F, mediaSize.getX(this.units), mediaSize.getY(this.units), this.units);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1725 */       float f1 = mediaSize.getX(25400);
/* 1726 */       float f2 = mediaSize.getY(25400);
/* 1727 */       float f3 = 5.0F;
/*      */       
/* 1729 */       if (f1 > f3) {
/* 1730 */         f4 = 1.0F;
/*      */       } else {
/* 1732 */         f4 = f1 / f3;
/*      */       } 
/* 1734 */       if (f2 > f3) {
/* 1735 */         f5 = 1.0F;
/*      */       } else {
/* 1737 */         f5 = f2 / f3;
/*      */       } 
/*      */       
/* 1740 */       if (mediaPrintableArea1 == null) {
/* 1741 */         mediaPrintableArea1 = new MediaPrintableArea(f4, f5, f1 - 2.0F * f4, f2 - 2.0F * f5, 25400);
/*      */ 
/*      */         
/* 1744 */         ServiceDialog.this.asCurrent.add(mediaPrintableArea1);
/*      */       } 
/* 1746 */       float f6 = mediaPrintableArea1.getX(this.units);
/* 1747 */       float f7 = mediaPrintableArea1.getY(this.units);
/* 1748 */       float f8 = mediaPrintableArea1.getWidth(this.units);
/* 1749 */       float f9 = mediaPrintableArea1.getHeight(this.units);
/* 1750 */       float f10 = mediaPrintableArea2.getX(this.units);
/* 1751 */       float f11 = mediaPrintableArea2.getY(this.units);
/* 1752 */       float f12 = mediaPrintableArea2.getWidth(this.units);
/* 1753 */       float f13 = mediaPrintableArea2.getHeight(this.units);
/*      */ 
/*      */       
/* 1756 */       boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1771 */       f1 = mediaSize.getX(this.units);
/* 1772 */       f2 = mediaSize.getY(this.units);
/* 1773 */       if (this.lmVal >= 0.0F) {
/* 1774 */         bool = true;
/*      */         
/* 1776 */         if (this.lmVal + this.rmVal > f1) {
/*      */           
/* 1778 */           if (f8 > f12) {
/* 1779 */             f8 = f12;
/*      */           }
/*      */           
/* 1782 */           f6 = (f1 - f8) / 2.0F;
/*      */         } else {
/* 1784 */           f6 = (this.lmVal >= f10) ? this.lmVal : f10;
/* 1785 */           f8 = f1 - f6 - this.rmVal;
/*      */         } 
/* 1787 */         if (this.tmVal + this.bmVal > f2) {
/* 1788 */           if (f9 > f13) {
/* 1789 */             f9 = f13;
/*      */           }
/* 1791 */           f7 = (f2 - f9) / 2.0F;
/*      */         } else {
/* 1793 */           f7 = (this.tmVal >= f11) ? this.tmVal : f11;
/* 1794 */           f9 = f2 - f7 - this.bmVal;
/*      */         } 
/*      */       } 
/* 1797 */       if (f6 < f10) {
/* 1798 */         bool = true;
/* 1799 */         f6 = f10;
/*      */       } 
/* 1801 */       if (f7 < f11) {
/* 1802 */         bool = true;
/* 1803 */         f7 = f11;
/*      */       } 
/* 1805 */       if (f8 > f12) {
/* 1806 */         bool = true;
/* 1807 */         f8 = f12;
/*      */       } 
/* 1809 */       if (f9 > f13) {
/* 1810 */         bool = true;
/* 1811 */         f9 = f13;
/*      */       } 
/*      */       
/* 1814 */       if (f6 + f8 > f10 + f12 || f8 <= 0.0F) {
/* 1815 */         bool = true;
/* 1816 */         f6 = f10;
/* 1817 */         f8 = f12;
/*      */       } 
/* 1819 */       if (f7 + f9 > f11 + f13 || f9 <= 0.0F) {
/* 1820 */         bool = true;
/* 1821 */         f7 = f11;
/* 1822 */         f9 = f13;
/*      */       } 
/*      */       
/* 1825 */       if (bool) {
/* 1826 */         mediaPrintableArea1 = new MediaPrintableArea(f6, f7, f8, f9, this.units);
/* 1827 */         ServiceDialog.this.asCurrent.add(mediaPrintableArea1);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1833 */       this.lmVal = f6;
/* 1834 */       this.tmVal = f7;
/* 1835 */       this.rmVal = mediaSize.getX(this.units) - f6 - f8;
/* 1836 */       this.bmVal = mediaSize.getY(this.units) - f7 - f9;
/*      */       
/* 1838 */       this.lmObj = new Float(this.lmVal);
/* 1839 */       this.rmObj = new Float(this.rmVal);
/* 1840 */       this.tmObj = new Float(this.tmVal);
/* 1841 */       this.bmObj = new Float(this.bmVal);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1847 */       Class<OrientationRequested> clazz1 = OrientationRequested.class;
/*      */       
/* 1849 */       OrientationRequested orientationRequested = (OrientationRequested)ServiceDialog.this.asCurrent.get(clazz1);
/*      */       
/* 1851 */       if (orientationRequested == null)
/*      */       {
/* 1853 */         orientationRequested = (OrientationRequested)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz1);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1858 */       if (orientationRequested == OrientationRequested.REVERSE_PORTRAIT) {
/* 1859 */         Float float_ = this.lmObj; this.lmObj = this.rmObj; this.rmObj = float_;
/* 1860 */         float_ = this.tmObj; this.tmObj = this.bmObj; this.bmObj = float_;
/* 1861 */       } else if (orientationRequested == OrientationRequested.LANDSCAPE) {
/* 1862 */         Float float_ = this.lmObj;
/* 1863 */         this.lmObj = this.bmObj;
/* 1864 */         this.bmObj = this.rmObj;
/* 1865 */         this.rmObj = this.tmObj;
/* 1866 */         this.tmObj = float_;
/* 1867 */       } else if (orientationRequested == OrientationRequested.REVERSE_LANDSCAPE) {
/* 1868 */         Float float_ = this.lmObj;
/* 1869 */         this.lmObj = this.tmObj;
/* 1870 */         this.tmObj = this.rmObj;
/* 1871 */         this.rmObj = this.bmObj;
/* 1872 */         this.bmObj = float_;
/*      */       } 
/*      */       
/* 1875 */       this.leftMargin.setValue(this.lmObj);
/* 1876 */       this.rightMargin.setValue(this.rmObj);
/* 1877 */       this.topMargin.setValue(this.tmObj);
/* 1878 */       this.bottomMargin.setValue(this.bmObj);
/*      */     } }
/*      */ 
/*      */   
/*      */   private class MediaPanel
/*      */     extends JPanel implements ItemListener {
/* 1884 */     private final String strTitle = ServiceDialog.getMsg("border.media"); private JLabel lblSize; private JLabel lblSource;
/*      */     private JComboBox cbSize;
/*      */     private JComboBox cbSource;
/* 1887 */     private Vector sizes = new Vector();
/* 1888 */     private Vector sources = new Vector();
/* 1889 */     private ServiceDialog.MarginsPanel pnlMargins = null;
/*      */ 
/*      */ 
/*      */     
/*      */     public MediaPanel() {
/* 1894 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 1895 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/* 1897 */       setLayout(gridBagLayout);
/* 1898 */       setBorder(BorderFactory.createTitledBorder(this.strTitle));
/*      */       
/* 1900 */       this.cbSize = new JComboBox();
/* 1901 */       this.cbSource = new JComboBox();
/*      */       
/* 1903 */       gridBagConstraints.fill = 1;
/* 1904 */       gridBagConstraints.insets = ServiceDialog.compInsets;
/* 1905 */       gridBagConstraints.weighty = 1.0D;
/*      */       
/* 1907 */       gridBagConstraints.weightx = 0.0D;
/* 1908 */       this.lblSize = new JLabel(ServiceDialog.getMsg("label.size"), 11);
/* 1909 */       this.lblSize.setDisplayedMnemonic(ServiceDialog.getMnemonic("label.size"));
/* 1910 */       this.lblSize.setLabelFor(this.cbSize);
/* 1911 */       ServiceDialog.addToGB(this.lblSize, this, gridBagLayout, gridBagConstraints);
/* 1912 */       gridBagConstraints.weightx = 1.0D;
/* 1913 */       gridBagConstraints.gridwidth = 0;
/* 1914 */       ServiceDialog.addToGB(this.cbSize, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 1916 */       gridBagConstraints.weightx = 0.0D;
/* 1917 */       gridBagConstraints.gridwidth = 1;
/* 1918 */       this.lblSource = new JLabel(ServiceDialog.getMsg("label.source"), 11);
/* 1919 */       this.lblSource.setDisplayedMnemonic(ServiceDialog.getMnemonic("label.source"));
/* 1920 */       this.lblSource.setLabelFor(this.cbSource);
/* 1921 */       ServiceDialog.addToGB(this.lblSource, this, gridBagLayout, gridBagConstraints);
/* 1922 */       gridBagConstraints.gridwidth = 0;
/* 1923 */       ServiceDialog.addToGB(this.cbSource, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private String getMediaName(String param1String) {
/*      */       try {
/* 1930 */         String str = param1String.replace(' ', '-');
/* 1931 */         str = str.replace('#', 'n');
/*      */         
/* 1933 */         return ServiceDialog.messageRB.getString(str);
/* 1934 */       } catch (MissingResourceException missingResourceException) {
/* 1935 */         return param1String;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void itemStateChanged(ItemEvent param1ItemEvent) {
/* 1940 */       Object object = param1ItemEvent.getSource();
/*      */       
/* 1942 */       if (param1ItemEvent.getStateChange() == 1) {
/* 1943 */         if (object == this.cbSize) {
/* 1944 */           int i = this.cbSize.getSelectedIndex();
/*      */           
/* 1946 */           if (i >= 0 && i < this.sizes.size()) {
/* 1947 */             if (this.cbSource.getItemCount() > 1 && this.cbSource
/* 1948 */               .getSelectedIndex() >= 1) {
/*      */               
/* 1950 */               int j = this.cbSource.getSelectedIndex() - 1;
/* 1951 */               MediaTray mediaTray = this.sources.get(j);
/* 1952 */               ServiceDialog.this.asCurrent.add(new SunAlternateMedia(mediaTray));
/*      */             } 
/* 1954 */             ServiceDialog.this.asCurrent.add(this.sizes.get(i));
/*      */           } 
/* 1956 */         } else if (object == this.cbSource) {
/* 1957 */           int i = this.cbSource.getSelectedIndex();
/*      */           
/* 1959 */           if (i >= 1 && i < this.sources.size() + 1) {
/* 1960 */             ServiceDialog.this.asCurrent.remove(SunAlternateMedia.class);
/* 1961 */             MediaTray mediaTray = this.sources.get(i - 1);
/* 1962 */             Media media = (Media)ServiceDialog.this.asCurrent.get(Media.class);
/* 1963 */             if (media == null || media instanceof MediaTray) {
/* 1964 */               ServiceDialog.this.asCurrent.add(mediaTray);
/* 1965 */             } else if (media instanceof MediaSizeName) {
/* 1966 */               MediaSizeName mediaSizeName = (MediaSizeName)media;
/* 1967 */               Media media1 = (Media)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)Media.class);
/* 1968 */               if (media1 instanceof MediaSizeName && media1.equals(mediaSizeName)) {
/* 1969 */                 ServiceDialog.this.asCurrent.add(mediaTray);
/*      */               
/*      */               }
/*      */               else {
/*      */                 
/* 1974 */                 ServiceDialog.this.asCurrent.add(new SunAlternateMedia(mediaTray));
/*      */               } 
/*      */             } 
/* 1977 */           } else if (i == 0) {
/* 1978 */             ServiceDialog.this.asCurrent.remove(SunAlternateMedia.class);
/* 1979 */             if (this.cbSize.getItemCount() > 0) {
/* 1980 */               int j = this.cbSize.getSelectedIndex();
/* 1981 */               ServiceDialog.this.asCurrent.add(this.sizes.get(j));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1986 */         if (this.pnlMargins != null) {
/* 1987 */           this.pnlMargins.updateInfo();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void addMediaListener(ServiceDialog.MarginsPanel param1MarginsPanel) {
/* 1995 */       this.pnlMargins = param1MarginsPanel;
/*      */     }
/*      */     public void updateInfo() {
/* 1998 */       Class<Media> clazz = Media.class;
/* 1999 */       Class<SunAlternateMedia> clazz1 = SunAlternateMedia.class;
/* 2000 */       boolean bool1 = false;
/*      */       
/* 2002 */       this.cbSize.removeItemListener(this);
/* 2003 */       this.cbSize.removeAllItems();
/* 2004 */       this.cbSource.removeItemListener(this);
/* 2005 */       this.cbSource.removeAllItems();
/* 2006 */       this.cbSource.addItem(getMediaName("auto-select"));
/*      */       
/* 2008 */       this.sizes.clear();
/* 2009 */       this.sources.clear();
/*      */       
/* 2011 */       if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz)) {
/* 2012 */         bool1 = true;
/*      */ 
/*      */         
/* 2015 */         Object object = ServiceDialog.this.psCurrent.getSupportedAttributeValues((Class)clazz, ServiceDialog.this
/* 2016 */             .docFlavor, ServiceDialog.this
/* 2017 */             .asCurrent);
/*      */         
/* 2019 */         if (object instanceof Media[]) {
/* 2020 */           Media[] arrayOfMedia = (Media[])object;
/*      */           
/* 2022 */           for (byte b = 0; b < arrayOfMedia.length; b++) {
/* 2023 */             Media media = arrayOfMedia[b];
/*      */             
/* 2025 */             if (media instanceof MediaSizeName) {
/* 2026 */               this.sizes.add(media);
/* 2027 */               this.cbSize.addItem(getMediaName(media.toString()));
/* 2028 */             } else if (media instanceof MediaTray) {
/* 2029 */               this.sources.add(media);
/* 2030 */               this.cbSource.addItem(getMediaName(media.toString()));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2036 */       boolean bool2 = (bool1 && this.sizes.size() > 0) ? true : false;
/* 2037 */       this.lblSize.setEnabled(bool2);
/* 2038 */       this.cbSize.setEnabled(bool2);
/*      */       
/* 2040 */       if (ServiceDialog.this.isAWT) {
/* 2041 */         this.cbSource.setEnabled(false);
/* 2042 */         this.lblSource.setEnabled(false);
/*      */       } else {
/* 2044 */         this.cbSource.setEnabled(bool1);
/*      */       } 
/*      */       
/* 2047 */       if (bool1) {
/*      */         
/* 2049 */         Media media1 = (Media)ServiceDialog.this.asCurrent.get(clazz);
/*      */ 
/*      */         
/* 2052 */         Media media2 = (Media)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz);
/* 2053 */         if (media2 instanceof MediaSizeName) {
/* 2054 */           this.cbSize.setSelectedIndex((this.sizes.size() > 0) ? this.sizes.indexOf(media2) : -1);
/*      */         }
/*      */         
/* 2057 */         if (media1 == null || 
/* 2058 */           !ServiceDialog.this.psCurrent.isAttributeValueSupported(media1, ServiceDialog.this
/* 2059 */             .docFlavor, ServiceDialog.this.asCurrent)) {
/*      */           
/* 2061 */           media1 = media2;
/*      */           
/* 2063 */           if (media1 == null && 
/* 2064 */             this.sizes.size() > 0) {
/* 2065 */             media1 = this.sizes.get(0);
/*      */           }
/*      */           
/* 2068 */           if (media1 != null) {
/* 2069 */             ServiceDialog.this.asCurrent.add(media1);
/*      */           }
/*      */         } 
/* 2072 */         if (media1 != null) {
/* 2073 */           if (media1 instanceof MediaSizeName) {
/* 2074 */             MediaSizeName mediaSizeName = (MediaSizeName)media1;
/* 2075 */             this.cbSize.setSelectedIndex(this.sizes.indexOf(mediaSizeName));
/* 2076 */           } else if (media1 instanceof MediaTray) {
/* 2077 */             MediaTray mediaTray = (MediaTray)media1;
/* 2078 */             this.cbSource.setSelectedIndex(this.sources.indexOf(mediaTray) + 1);
/*      */           } 
/*      */         } else {
/* 2081 */           this.cbSize.setSelectedIndex((this.sizes.size() > 0) ? 0 : -1);
/* 2082 */           this.cbSource.setSelectedIndex(0);
/*      */         } 
/*      */         
/* 2085 */         SunAlternateMedia sunAlternateMedia = (SunAlternateMedia)ServiceDialog.this.asCurrent.get(clazz1);
/* 2086 */         if (sunAlternateMedia != null) {
/* 2087 */           Media media = sunAlternateMedia.getMedia();
/* 2088 */           if (media instanceof MediaTray) {
/* 2089 */             MediaTray mediaTray = (MediaTray)media;
/* 2090 */             this.cbSource.setSelectedIndex(this.sources.indexOf(mediaTray) + 1);
/*      */           } 
/*      */         } 
/*      */         
/* 2094 */         int i = this.cbSize.getSelectedIndex();
/* 2095 */         if (i >= 0 && i < this.sizes.size()) {
/* 2096 */           ServiceDialog.this.asCurrent.add(this.sizes.get(i));
/*      */         }
/*      */         
/* 2099 */         i = this.cbSource.getSelectedIndex();
/* 2100 */         if (i >= 1 && i < this.sources.size() + 1) {
/* 2101 */           MediaTray mediaTray = this.sources.get(i - 1);
/* 2102 */           if (media1 instanceof MediaTray) {
/* 2103 */             ServiceDialog.this.asCurrent.add(mediaTray);
/*      */           } else {
/* 2105 */             ServiceDialog.this.asCurrent.add(new SunAlternateMedia(mediaTray));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2111 */       this.cbSize.addItemListener(this);
/* 2112 */       this.cbSource.addItemListener(this);
/*      */     }
/*      */   }
/*      */   
/*      */   private class OrientationPanel
/*      */     extends JPanel
/*      */     implements ActionListener {
/* 2119 */     private final String strTitle = ServiceDialog.getMsg("border.orientation"); private ServiceDialog.IconRadioButton rbPortrait; private ServiceDialog.IconRadioButton rbLandscape;
/*      */     private ServiceDialog.IconRadioButton rbRevPortrait;
/*      */     private ServiceDialog.IconRadioButton rbRevLandscape;
/* 2122 */     private ServiceDialog.MarginsPanel pnlMargins = null;
/*      */ 
/*      */ 
/*      */     
/*      */     public OrientationPanel() {
/* 2127 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 2128 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/* 2130 */       setLayout(gridBagLayout);
/* 2131 */       setBorder(BorderFactory.createTitledBorder(this.strTitle));
/*      */       
/* 2133 */       gridBagConstraints.fill = 1;
/* 2134 */       gridBagConstraints.insets = ServiceDialog.compInsets;
/* 2135 */       gridBagConstraints.weighty = 1.0D;
/* 2136 */       gridBagConstraints.gridwidth = 0;
/*      */       
/* 2138 */       ButtonGroup buttonGroup = new ButtonGroup();
/* 2139 */       this.rbPortrait = new ServiceDialog.IconRadioButton("radiobutton.portrait", "orientPortrait.png", true, buttonGroup, this);
/*      */ 
/*      */       
/* 2142 */       this.rbPortrait.addActionListener(this);
/* 2143 */       ServiceDialog.addToGB(this.rbPortrait, this, gridBagLayout, gridBagConstraints);
/* 2144 */       this.rbLandscape = new ServiceDialog.IconRadioButton("radiobutton.landscape", "orientLandscape.png", false, buttonGroup, this);
/*      */ 
/*      */       
/* 2147 */       this.rbLandscape.addActionListener(this);
/* 2148 */       ServiceDialog.addToGB(this.rbLandscape, this, gridBagLayout, gridBagConstraints);
/* 2149 */       this.rbRevPortrait = new ServiceDialog.IconRadioButton("radiobutton.revportrait", "orientRevPortrait.png", false, buttonGroup, this);
/*      */ 
/*      */       
/* 2152 */       this.rbRevPortrait.addActionListener(this);
/* 2153 */       ServiceDialog.addToGB(this.rbRevPortrait, this, gridBagLayout, gridBagConstraints);
/* 2154 */       this.rbRevLandscape = new ServiceDialog.IconRadioButton("radiobutton.revlandscape", "orientRevLandscape.png", false, buttonGroup, this);
/*      */ 
/*      */       
/* 2157 */       this.rbRevLandscape.addActionListener(this);
/* 2158 */       ServiceDialog.addToGB(this.rbRevLandscape, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2162 */       Object object = param1ActionEvent.getSource();
/*      */       
/* 2164 */       if (this.rbPortrait.isSameAs(object)) {
/* 2165 */         ServiceDialog.this.asCurrent.add(OrientationRequested.PORTRAIT);
/* 2166 */       } else if (this.rbLandscape.isSameAs(object)) {
/* 2167 */         ServiceDialog.this.asCurrent.add(OrientationRequested.LANDSCAPE);
/* 2168 */       } else if (this.rbRevPortrait.isSameAs(object)) {
/* 2169 */         ServiceDialog.this.asCurrent.add(OrientationRequested.REVERSE_PORTRAIT);
/* 2170 */       } else if (this.rbRevLandscape.isSameAs(object)) {
/* 2171 */         ServiceDialog.this.asCurrent.add(OrientationRequested.REVERSE_LANDSCAPE);
/*      */       } 
/*      */       
/* 2174 */       if (this.pnlMargins != null) {
/* 2175 */         this.pnlMargins.updateInfo();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void addOrientationListener(ServiceDialog.MarginsPanel param1MarginsPanel) {
/* 2181 */       this.pnlMargins = param1MarginsPanel;
/*      */     }
/*      */     
/*      */     public void updateInfo() {
/* 2185 */       Class<OrientationRequested> clazz = OrientationRequested.class;
/* 2186 */       boolean bool1 = false;
/* 2187 */       boolean bool2 = false;
/* 2188 */       boolean bool3 = false;
/* 2189 */       boolean bool4 = false;
/*      */       
/* 2191 */       if (ServiceDialog.this.isAWT) {
/* 2192 */         bool1 = true;
/* 2193 */         bool2 = true;
/*      */       }
/* 2195 */       else if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz)) {
/*      */         
/* 2197 */         Object object = ServiceDialog.this.psCurrent.getSupportedAttributeValues((Class)clazz, ServiceDialog.this
/* 2198 */             .docFlavor, ServiceDialog.this
/* 2199 */             .asCurrent);
/*      */         
/* 2201 */         if (object instanceof OrientationRequested[]) {
/* 2202 */           OrientationRequested[] arrayOfOrientationRequested = (OrientationRequested[])object;
/*      */ 
/*      */           
/* 2205 */           for (byte b = 0; b < arrayOfOrientationRequested.length; b++) {
/* 2206 */             OrientationRequested orientationRequested1 = arrayOfOrientationRequested[b];
/*      */             
/* 2208 */             if (orientationRequested1 == OrientationRequested.PORTRAIT) {
/* 2209 */               bool1 = true;
/* 2210 */             } else if (orientationRequested1 == OrientationRequested.LANDSCAPE) {
/* 2211 */               bool2 = true;
/* 2212 */             } else if (orientationRequested1 == OrientationRequested.REVERSE_PORTRAIT) {
/* 2213 */               bool3 = true;
/* 2214 */             } else if (orientationRequested1 == OrientationRequested.REVERSE_LANDSCAPE) {
/* 2215 */               bool4 = true;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2222 */       this.rbPortrait.setEnabled(bool1);
/* 2223 */       this.rbLandscape.setEnabled(bool2);
/* 2224 */       this.rbRevPortrait.setEnabled(bool3);
/* 2225 */       this.rbRevLandscape.setEnabled(bool4);
/*      */       
/* 2227 */       OrientationRequested orientationRequested = (OrientationRequested)ServiceDialog.this.asCurrent.get(clazz);
/* 2228 */       if (orientationRequested == null || 
/* 2229 */         !ServiceDialog.this.psCurrent.isAttributeValueSupported(orientationRequested, ServiceDialog.this.docFlavor, ServiceDialog.this.asCurrent)) {
/*      */         
/* 2231 */         orientationRequested = (OrientationRequested)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz);
/*      */         
/* 2233 */         if (orientationRequested != null && 
/* 2234 */           !ServiceDialog.this.psCurrent.isAttributeValueSupported(orientationRequested, ServiceDialog.this.docFlavor, ServiceDialog.this.asCurrent)) {
/* 2235 */           orientationRequested = null;
/*      */           
/* 2237 */           Object object = ServiceDialog.this.psCurrent.getSupportedAttributeValues((Class)clazz, ServiceDialog.this
/* 2238 */               .docFlavor, ServiceDialog.this
/* 2239 */               .asCurrent);
/* 2240 */           if (object instanceof OrientationRequested[]) {
/* 2241 */             OrientationRequested[] arrayOfOrientationRequested = (OrientationRequested[])object;
/*      */             
/* 2243 */             if (arrayOfOrientationRequested.length > 1)
/*      */             {
/* 2245 */               orientationRequested = arrayOfOrientationRequested[0];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2250 */         if (orientationRequested == null) {
/* 2251 */           orientationRequested = OrientationRequested.PORTRAIT;
/*      */         }
/* 2253 */         ServiceDialog.this.asCurrent.add(orientationRequested);
/*      */       } 
/*      */       
/* 2256 */       if (orientationRequested == OrientationRequested.PORTRAIT) {
/* 2257 */         this.rbPortrait.setSelected(true);
/* 2258 */       } else if (orientationRequested == OrientationRequested.LANDSCAPE) {
/* 2259 */         this.rbLandscape.setSelected(true);
/* 2260 */       } else if (orientationRequested == OrientationRequested.REVERSE_PORTRAIT) {
/* 2261 */         this.rbRevPortrait.setSelected(true);
/*      */       } else {
/* 2263 */         this.rbRevLandscape.setSelected(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class AppearancePanel
/*      */     extends JPanel
/*      */   {
/*      */     private ServiceDialog.ChromaticityPanel pnlChromaticity;
/*      */ 
/*      */     
/*      */     private ServiceDialog.QualityPanel pnlQuality;
/*      */     
/*      */     private ServiceDialog.JobAttributesPanel pnlJobAttributes;
/*      */     
/*      */     private ServiceDialog.SidesPanel pnlSides;
/*      */ 
/*      */     
/*      */     public AppearancePanel() {
/* 2284 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 2285 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/* 2287 */       setLayout(gridBagLayout);
/*      */       
/* 2289 */       gridBagConstraints.fill = 1;
/* 2290 */       gridBagConstraints.insets = ServiceDialog.panelInsets;
/* 2291 */       gridBagConstraints.weightx = 1.0D;
/* 2292 */       gridBagConstraints.weighty = 1.0D;
/*      */       
/* 2294 */       gridBagConstraints.gridwidth = -1;
/* 2295 */       this.pnlChromaticity = new ServiceDialog.ChromaticityPanel();
/* 2296 */       ServiceDialog.addToGB(this.pnlChromaticity, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 2298 */       gridBagConstraints.gridwidth = 0;
/* 2299 */       this.pnlQuality = new ServiceDialog.QualityPanel();
/* 2300 */       ServiceDialog.addToGB(this.pnlQuality, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 2302 */       gridBagConstraints.gridwidth = 1;
/* 2303 */       this.pnlSides = new ServiceDialog.SidesPanel();
/* 2304 */       ServiceDialog.addToGB(this.pnlSides, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 2306 */       gridBagConstraints.gridwidth = 0;
/* 2307 */       this.pnlJobAttributes = new ServiceDialog.JobAttributesPanel();
/* 2308 */       ServiceDialog.addToGB(this.pnlJobAttributes, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */ 
/*      */     
/*      */     public void updateInfo() {
/* 2313 */       this.pnlChromaticity.updateInfo();
/* 2314 */       this.pnlQuality.updateInfo();
/* 2315 */       this.pnlSides.updateInfo();
/* 2316 */       this.pnlJobAttributes.updateInfo();
/*      */     }
/*      */   }
/*      */   
/*      */   private class ChromaticityPanel
/*      */     extends JPanel
/*      */     implements ActionListener {
/* 2323 */     private final String strTitle = ServiceDialog.getMsg("border.chromaticity");
/*      */     
/*      */     private JRadioButton rbMonochrome;
/*      */     private JRadioButton rbColor;
/*      */     
/*      */     public ChromaticityPanel() {
/* 2329 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 2330 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/* 2332 */       setLayout(gridBagLayout);
/* 2333 */       setBorder(BorderFactory.createTitledBorder(this.strTitle));
/*      */       
/* 2335 */       gridBagConstraints.fill = 1;
/* 2336 */       gridBagConstraints.gridwidth = 0;
/* 2337 */       gridBagConstraints.weighty = 1.0D;
/*      */       
/* 2339 */       ButtonGroup buttonGroup = new ButtonGroup();
/* 2340 */       this.rbMonochrome = ServiceDialog.createRadioButton("radiobutton.monochrome", this);
/* 2341 */       this.rbMonochrome.setSelected(true);
/* 2342 */       buttonGroup.add(this.rbMonochrome);
/* 2343 */       ServiceDialog.addToGB(this.rbMonochrome, this, gridBagLayout, gridBagConstraints);
/* 2344 */       this.rbColor = ServiceDialog.createRadioButton("radiobutton.color", this);
/* 2345 */       buttonGroup.add(this.rbColor);
/* 2346 */       ServiceDialog.addToGB(this.rbColor, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2350 */       Object object = param1ActionEvent.getSource();
/*      */ 
/*      */       
/* 2353 */       if (object == this.rbMonochrome) {
/* 2354 */         ServiceDialog.this.asCurrent.add(Chromaticity.MONOCHROME);
/* 2355 */       } else if (object == this.rbColor) {
/* 2356 */         ServiceDialog.this.asCurrent.add(Chromaticity.COLOR);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void updateInfo() {
/* 2361 */       Class<Chromaticity> clazz = Chromaticity.class;
/* 2362 */       boolean bool1 = false;
/* 2363 */       boolean bool2 = false;
/*      */       
/* 2365 */       if (ServiceDialog.this.isAWT) {
/* 2366 */         bool1 = true;
/* 2367 */         bool2 = true;
/*      */       }
/* 2369 */       else if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz)) {
/*      */         
/* 2371 */         Object object = ServiceDialog.this.psCurrent.getSupportedAttributeValues((Class)clazz, ServiceDialog.this
/* 2372 */             .docFlavor, ServiceDialog.this
/* 2373 */             .asCurrent);
/*      */         
/* 2375 */         if (object instanceof Chromaticity[]) {
/* 2376 */           Chromaticity[] arrayOfChromaticity = (Chromaticity[])object;
/*      */           
/* 2378 */           for (byte b = 0; b < arrayOfChromaticity.length; b++) {
/* 2379 */             Chromaticity chromaticity1 = arrayOfChromaticity[b];
/*      */             
/* 2381 */             if (chromaticity1 == Chromaticity.MONOCHROME) {
/* 2382 */               bool1 = true;
/* 2383 */             } else if (chromaticity1 == Chromaticity.COLOR) {
/* 2384 */               bool2 = true;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2391 */       this.rbMonochrome.setEnabled(bool1);
/* 2392 */       this.rbColor.setEnabled(bool2);
/*      */       
/* 2394 */       Chromaticity chromaticity = (Chromaticity)ServiceDialog.this.asCurrent.get(clazz);
/* 2395 */       if (chromaticity == null) {
/* 2396 */         chromaticity = (Chromaticity)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz);
/* 2397 */         if (chromaticity == null) {
/* 2398 */           chromaticity = Chromaticity.MONOCHROME;
/*      */         }
/*      */       } 
/*      */       
/* 2402 */       if (chromaticity == Chromaticity.MONOCHROME) {
/* 2403 */         this.rbMonochrome.setSelected(true);
/*      */       } else {
/* 2405 */         this.rbColor.setSelected(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class QualityPanel
/*      */     extends JPanel
/*      */     implements ActionListener {
/* 2413 */     private final String strTitle = ServiceDialog.getMsg("border.quality");
/*      */     private JRadioButton rbDraft;
/*      */     private JRadioButton rbNormal;
/*      */     private JRadioButton rbHigh;
/*      */     
/*      */     public QualityPanel() {
/* 2419 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 2420 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/* 2422 */       setLayout(gridBagLayout);
/* 2423 */       setBorder(BorderFactory.createTitledBorder(this.strTitle));
/*      */       
/* 2425 */       gridBagConstraints.fill = 1;
/* 2426 */       gridBagConstraints.gridwidth = 0;
/* 2427 */       gridBagConstraints.weighty = 1.0D;
/*      */       
/* 2429 */       ButtonGroup buttonGroup = new ButtonGroup();
/* 2430 */       this.rbDraft = ServiceDialog.createRadioButton("radiobutton.draftq", this);
/* 2431 */       buttonGroup.add(this.rbDraft);
/* 2432 */       ServiceDialog.addToGB(this.rbDraft, this, gridBagLayout, gridBagConstraints);
/* 2433 */       this.rbNormal = ServiceDialog.createRadioButton("radiobutton.normalq", this);
/* 2434 */       this.rbNormal.setSelected(true);
/* 2435 */       buttonGroup.add(this.rbNormal);
/* 2436 */       ServiceDialog.addToGB(this.rbNormal, this, gridBagLayout, gridBagConstraints);
/* 2437 */       this.rbHigh = ServiceDialog.createRadioButton("radiobutton.highq", this);
/* 2438 */       buttonGroup.add(this.rbHigh);
/* 2439 */       ServiceDialog.addToGB(this.rbHigh, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2443 */       Object object = param1ActionEvent.getSource();
/*      */       
/* 2445 */       if (object == this.rbDraft) {
/* 2446 */         ServiceDialog.this.asCurrent.add(PrintQuality.DRAFT);
/* 2447 */       } else if (object == this.rbNormal) {
/* 2448 */         ServiceDialog.this.asCurrent.add(PrintQuality.NORMAL);
/* 2449 */       } else if (object == this.rbHigh) {
/* 2450 */         ServiceDialog.this.asCurrent.add(PrintQuality.HIGH);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void updateInfo() {
/* 2455 */       Class<PrintQuality> clazz = PrintQuality.class;
/* 2456 */       boolean bool1 = false;
/* 2457 */       boolean bool2 = false;
/* 2458 */       boolean bool3 = false;
/*      */       
/* 2460 */       if (ServiceDialog.this.isAWT) {
/* 2461 */         bool1 = true;
/* 2462 */         bool2 = true;
/* 2463 */         bool3 = true;
/*      */       }
/* 2465 */       else if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz)) {
/*      */         
/* 2467 */         Object object = ServiceDialog.this.psCurrent.getSupportedAttributeValues((Class)clazz, ServiceDialog.this
/* 2468 */             .docFlavor, ServiceDialog.this
/* 2469 */             .asCurrent);
/*      */         
/* 2471 */         if (object instanceof PrintQuality[]) {
/* 2472 */           PrintQuality[] arrayOfPrintQuality = (PrintQuality[])object;
/*      */           
/* 2474 */           for (byte b = 0; b < arrayOfPrintQuality.length; b++) {
/* 2475 */             PrintQuality printQuality1 = arrayOfPrintQuality[b];
/*      */             
/* 2477 */             if (printQuality1 == PrintQuality.DRAFT) {
/* 2478 */               bool1 = true;
/* 2479 */             } else if (printQuality1 == PrintQuality.NORMAL) {
/* 2480 */               bool2 = true;
/* 2481 */             } else if (printQuality1 == PrintQuality.HIGH) {
/* 2482 */               bool3 = true;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2488 */       this.rbDraft.setEnabled(bool1);
/* 2489 */       this.rbNormal.setEnabled(bool2);
/* 2490 */       this.rbHigh.setEnabled(bool3);
/*      */       
/* 2492 */       PrintQuality printQuality = (PrintQuality)ServiceDialog.this.asCurrent.get(clazz);
/* 2493 */       if (printQuality == null) {
/* 2494 */         printQuality = (PrintQuality)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz);
/* 2495 */         if (printQuality == null) {
/* 2496 */           printQuality = PrintQuality.NORMAL;
/*      */         }
/*      */       } 
/*      */       
/* 2500 */       if (printQuality == PrintQuality.DRAFT) {
/* 2501 */         this.rbDraft.setSelected(true);
/* 2502 */       } else if (printQuality == PrintQuality.NORMAL) {
/* 2503 */         this.rbNormal.setSelected(true);
/*      */       } else {
/* 2505 */         this.rbHigh.setSelected(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class SidesPanel
/*      */     extends JPanel
/*      */     implements ActionListener
/*      */   {
/* 2514 */     private final String strTitle = ServiceDialog.getMsg("border.sides");
/*      */     private ServiceDialog.IconRadioButton rbOneSide;
/*      */     private ServiceDialog.IconRadioButton rbTumble;
/*      */     private ServiceDialog.IconRadioButton rbDuplex;
/*      */     
/*      */     public SidesPanel() {
/* 2520 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 2521 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/* 2523 */       setLayout(gridBagLayout);
/* 2524 */       setBorder(BorderFactory.createTitledBorder(this.strTitle));
/*      */       
/* 2526 */       gridBagConstraints.fill = 1;
/* 2527 */       gridBagConstraints.insets = ServiceDialog.compInsets;
/* 2528 */       gridBagConstraints.weighty = 1.0D;
/* 2529 */       gridBagConstraints.gridwidth = 0;
/*      */       
/* 2531 */       ButtonGroup buttonGroup = new ButtonGroup();
/* 2532 */       this.rbOneSide = new ServiceDialog.IconRadioButton("radiobutton.oneside", "oneside.png", true, buttonGroup, this);
/*      */ 
/*      */       
/* 2535 */       this.rbOneSide.addActionListener(this);
/* 2536 */       ServiceDialog.addToGB(this.rbOneSide, this, gridBagLayout, gridBagConstraints);
/* 2537 */       this.rbTumble = new ServiceDialog.IconRadioButton("radiobutton.tumble", "tumble.png", false, buttonGroup, this);
/*      */ 
/*      */       
/* 2540 */       this.rbTumble.addActionListener(this);
/* 2541 */       ServiceDialog.addToGB(this.rbTumble, this, gridBagLayout, gridBagConstraints);
/* 2542 */       this.rbDuplex = new ServiceDialog.IconRadioButton("radiobutton.duplex", "duplex.png", false, buttonGroup, this);
/*      */ 
/*      */       
/* 2545 */       this.rbDuplex.addActionListener(this);
/* 2546 */       gridBagConstraints.gridwidth = 0;
/* 2547 */       ServiceDialog.addToGB(this.rbDuplex, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2551 */       Object object = param1ActionEvent.getSource();
/*      */       
/* 2553 */       if (this.rbOneSide.isSameAs(object)) {
/* 2554 */         ServiceDialog.this.asCurrent.add(Sides.ONE_SIDED);
/* 2555 */       } else if (this.rbTumble.isSameAs(object)) {
/* 2556 */         ServiceDialog.this.asCurrent.add(Sides.TUMBLE);
/* 2557 */       } else if (this.rbDuplex.isSameAs(object)) {
/* 2558 */         ServiceDialog.this.asCurrent.add(Sides.DUPLEX);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void updateInfo() {
/* 2563 */       Class<Sides> clazz = Sides.class;
/* 2564 */       boolean bool1 = false;
/* 2565 */       boolean bool2 = false;
/* 2566 */       boolean bool3 = false;
/*      */       
/* 2568 */       if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz)) {
/*      */         
/* 2570 */         Object object = ServiceDialog.this.psCurrent.getSupportedAttributeValues((Class)clazz, ServiceDialog.this
/* 2571 */             .docFlavor, ServiceDialog.this
/* 2572 */             .asCurrent);
/*      */         
/* 2574 */         if (object instanceof Sides[]) {
/* 2575 */           Sides[] arrayOfSides = (Sides[])object;
/*      */           
/* 2577 */           for (byte b = 0; b < arrayOfSides.length; b++) {
/* 2578 */             Sides sides1 = arrayOfSides[b];
/*      */             
/* 2580 */             if (sides1 == Sides.ONE_SIDED) {
/* 2581 */               bool1 = true;
/* 2582 */             } else if (sides1 == Sides.TUMBLE) {
/* 2583 */               bool2 = true;
/* 2584 */             } else if (sides1 == Sides.DUPLEX) {
/* 2585 */               bool3 = true;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2590 */       this.rbOneSide.setEnabled(bool1);
/* 2591 */       this.rbTumble.setEnabled(bool2);
/* 2592 */       this.rbDuplex.setEnabled(bool3);
/*      */       
/* 2594 */       Sides sides = (Sides)ServiceDialog.this.asCurrent.get(clazz);
/* 2595 */       if (sides == null) {
/* 2596 */         sides = (Sides)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz);
/* 2597 */         if (sides == null) {
/* 2598 */           sides = Sides.ONE_SIDED;
/*      */         }
/*      */       } 
/*      */       
/* 2602 */       if (sides == Sides.ONE_SIDED) {
/* 2603 */         this.rbOneSide.setSelected(true);
/* 2604 */       } else if (sides == Sides.TUMBLE) {
/* 2605 */         this.rbTumble.setSelected(true);
/*      */       } else {
/* 2607 */         this.rbDuplex.setSelected(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class JobAttributesPanel
/*      */     extends JPanel
/*      */     implements ActionListener, ChangeListener, FocusListener
/*      */   {
/* 2617 */     private final String strTitle = ServiceDialog.getMsg("border.jobattributes"); private JLabel lblPriority;
/*      */     private JLabel lblJobName;
/*      */     private JLabel lblUserName;
/*      */     private JSpinner spinPriority;
/*      */     private SpinnerNumberModel snModel;
/*      */     private JCheckBox cbJobSheets;
/*      */     private JTextField tfJobName;
/*      */     private JTextField tfUserName;
/*      */     
/*      */     public JobAttributesPanel() {
/* 2627 */       GridBagLayout gridBagLayout = new GridBagLayout();
/* 2628 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*      */       
/* 2630 */       setLayout(gridBagLayout);
/* 2631 */       setBorder(BorderFactory.createTitledBorder(this.strTitle));
/*      */       
/* 2633 */       gridBagConstraints.fill = 0;
/* 2634 */       gridBagConstraints.insets = ServiceDialog.compInsets;
/* 2635 */       gridBagConstraints.weighty = 1.0D;
/*      */       
/* 2637 */       this.cbJobSheets = ServiceDialog.createCheckBox("checkbox.jobsheets", this);
/* 2638 */       gridBagConstraints.anchor = 21;
/* 2639 */       ServiceDialog.addToGB(this.cbJobSheets, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 2641 */       JPanel jPanel = new JPanel();
/* 2642 */       this.lblPriority = new JLabel(ServiceDialog.getMsg("label.priority"), 11);
/* 2643 */       this.lblPriority.setDisplayedMnemonic(ServiceDialog.getMnemonic("label.priority"));
/*      */       
/* 2645 */       jPanel.add(this.lblPriority);
/* 2646 */       this.snModel = new SpinnerNumberModel(1, 1, 100, 1);
/* 2647 */       this.spinPriority = new JSpinner(this.snModel);
/* 2648 */       this.lblPriority.setLabelFor(this.spinPriority);
/*      */       
/* 2650 */       ((JSpinner.NumberEditor)this.spinPriority.getEditor()).getTextField().setColumns(3);
/* 2651 */       this.spinPriority.addChangeListener(this);
/* 2652 */       jPanel.add(this.spinPriority);
/* 2653 */       gridBagConstraints.anchor = 22;
/* 2654 */       gridBagConstraints.gridwidth = 0;
/* 2655 */       jPanel.getAccessibleContext().setAccessibleName(
/* 2656 */           ServiceDialog.getMsg("label.priority"));
/* 2657 */       ServiceDialog.addToGB(jPanel, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 2659 */       gridBagConstraints.fill = 2;
/* 2660 */       gridBagConstraints.anchor = 10;
/* 2661 */       gridBagConstraints.weightx = 0.0D;
/* 2662 */       gridBagConstraints.gridwidth = 1;
/* 2663 */       char c1 = ServiceDialog.getMnemonic("label.jobname");
/* 2664 */       this.lblJobName = new JLabel(ServiceDialog.getMsg("label.jobname"), 11);
/* 2665 */       this.lblJobName.setDisplayedMnemonic(c1);
/* 2666 */       ServiceDialog.addToGB(this.lblJobName, this, gridBagLayout, gridBagConstraints);
/* 2667 */       gridBagConstraints.weightx = 1.0D;
/* 2668 */       gridBagConstraints.gridwidth = 0;
/* 2669 */       this.tfJobName = new JTextField();
/* 2670 */       this.lblJobName.setLabelFor(this.tfJobName);
/* 2671 */       this.tfJobName.addFocusListener(this);
/* 2672 */       this.tfJobName.setFocusAccelerator(c1);
/* 2673 */       this.tfJobName.getAccessibleContext().setAccessibleName(
/* 2674 */           ServiceDialog.getMsg("label.jobname"));
/* 2675 */       ServiceDialog.addToGB(this.tfJobName, this, gridBagLayout, gridBagConstraints);
/*      */       
/* 2677 */       gridBagConstraints.weightx = 0.0D;
/* 2678 */       gridBagConstraints.gridwidth = 1;
/* 2679 */       char c2 = ServiceDialog.getMnemonic("label.username");
/* 2680 */       this.lblUserName = new JLabel(ServiceDialog.getMsg("label.username"), 11);
/* 2681 */       this.lblUserName.setDisplayedMnemonic(c2);
/* 2682 */       ServiceDialog.addToGB(this.lblUserName, this, gridBagLayout, gridBagConstraints);
/* 2683 */       gridBagConstraints.gridwidth = 0;
/* 2684 */       this.tfUserName = new JTextField();
/* 2685 */       this.lblUserName.setLabelFor(this.tfUserName);
/* 2686 */       this.tfUserName.addFocusListener(this);
/* 2687 */       this.tfUserName.setFocusAccelerator(c2);
/* 2688 */       this.tfUserName.getAccessibleContext().setAccessibleName(
/* 2689 */           ServiceDialog.getMsg("label.username"));
/* 2690 */       ServiceDialog.addToGB(this.tfUserName, this, gridBagLayout, gridBagConstraints);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2694 */       if (this.cbJobSheets.isSelected()) {
/* 2695 */         ServiceDialog.this.asCurrent.add(JobSheets.STANDARD);
/*      */       } else {
/* 2697 */         ServiceDialog.this.asCurrent.add(JobSheets.NONE);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 2702 */       ServiceDialog.this.asCurrent.add(new JobPriority(this.snModel.getNumber().intValue()));
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 2706 */       Object object = param1FocusEvent.getSource();
/*      */       
/* 2708 */       if (object == this.tfJobName) {
/* 2709 */         ServiceDialog.this.asCurrent.add(new JobName(this.tfJobName.getText(), 
/* 2710 */               Locale.getDefault()));
/* 2711 */       } else if (object == this.tfUserName) {
/* 2712 */         ServiceDialog.this.asCurrent.add(new RequestingUserName(this.tfUserName.getText(), 
/* 2713 */               Locale.getDefault()));
/*      */       } 
/*      */     }
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {}
/*      */     
/*      */     public void updateInfo() {
/* 2720 */       Class<JobSheets> clazz = JobSheets.class;
/* 2721 */       Class<JobPriority> clazz1 = JobPriority.class;
/* 2722 */       Class<JobName> clazz2 = JobName.class;
/* 2723 */       Class<RequestingUserName> clazz3 = RequestingUserName.class;
/* 2724 */       boolean bool1 = false;
/* 2725 */       boolean bool2 = false;
/* 2726 */       boolean bool3 = false;
/* 2727 */       boolean bool4 = false;
/*      */ 
/*      */       
/* 2730 */       if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz)) {
/* 2731 */         bool1 = true;
/*      */       }
/* 2733 */       JobSheets jobSheets = (JobSheets)ServiceDialog.this.asCurrent.get(clazz);
/* 2734 */       if (jobSheets == null) {
/* 2735 */         jobSheets = (JobSheets)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz);
/* 2736 */         if (jobSheets == null) {
/* 2737 */           jobSheets = JobSheets.NONE;
/*      */         }
/*      */       } 
/* 2740 */       this.cbJobSheets.setSelected((jobSheets != JobSheets.NONE));
/* 2741 */       this.cbJobSheets.setEnabled(bool1);
/*      */ 
/*      */       
/* 2744 */       if (!ServiceDialog.this.isAWT && ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz1)) {
/* 2745 */         bool2 = true;
/*      */       }
/* 2747 */       JobPriority jobPriority = (JobPriority)ServiceDialog.this.asCurrent.get(clazz1);
/* 2748 */       if (jobPriority == null) {
/* 2749 */         jobPriority = (JobPriority)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz1);
/* 2750 */         if (jobPriority == null) {
/* 2751 */           jobPriority = new JobPriority(1);
/*      */         }
/*      */       } 
/* 2754 */       int i = jobPriority.getValue();
/* 2755 */       if (i < 1 || i > 100) {
/* 2756 */         i = 1;
/*      */       }
/* 2758 */       this.snModel.setValue(new Integer(i));
/* 2759 */       this.lblPriority.setEnabled(bool2);
/* 2760 */       this.spinPriority.setEnabled(bool2);
/*      */ 
/*      */       
/* 2763 */       if (ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz2)) {
/* 2764 */         bool3 = true;
/*      */       }
/* 2766 */       JobName jobName = (JobName)ServiceDialog.this.asCurrent.get(clazz2);
/* 2767 */       if (jobName == null) {
/* 2768 */         jobName = (JobName)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz2);
/* 2769 */         if (jobName == null) {
/* 2770 */           jobName = new JobName("", Locale.getDefault());
/*      */         }
/*      */       } 
/* 2773 */       this.tfJobName.setText(jobName.getValue());
/* 2774 */       this.tfJobName.setEnabled(bool3);
/* 2775 */       this.lblJobName.setEnabled(bool3);
/*      */ 
/*      */       
/* 2778 */       if (!ServiceDialog.this.isAWT && ServiceDialog.this.psCurrent.isAttributeCategorySupported((Class)clazz3)) {
/* 2779 */         bool4 = true;
/*      */       }
/* 2781 */       RequestingUserName requestingUserName = (RequestingUserName)ServiceDialog.this.asCurrent.get(clazz3);
/* 2782 */       if (requestingUserName == null) {
/* 2783 */         requestingUserName = (RequestingUserName)ServiceDialog.this.psCurrent.getDefaultAttributeValue((Class)clazz3);
/* 2784 */         if (requestingUserName == null) {
/* 2785 */           requestingUserName = new RequestingUserName("", Locale.getDefault());
/*      */         }
/*      */       } 
/* 2788 */       this.tfUserName.setText(requestingUserName.getValue());
/* 2789 */       this.tfUserName.setEnabled(bool4);
/* 2790 */       this.lblUserName.setEnabled(bool4);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class IconRadioButton
/*      */     extends JPanel
/*      */   {
/*      */     private JRadioButton rb;
/*      */ 
/*      */ 
/*      */     
/*      */     private JLabel lbl;
/*      */ 
/*      */ 
/*      */     
/*      */     public IconRadioButton(String param1String1, String param1String2, boolean param1Boolean, ButtonGroup param1ButtonGroup, ActionListener param1ActionListener) {
/* 2809 */       super(new FlowLayout(3));
/* 2810 */       final URL imgURL = ServiceDialog.getImageResource(param1String2);
/* 2811 */       Icon icon = AccessController.<Icon>doPrivileged(new PrivilegedAction<Icon>()
/*      */           {
/*      */             public Object run() {
/* 2814 */               return new ImageIcon(imgURL);
/*      */             }
/*      */           });
/*      */       
/* 2818 */       this.lbl = new JLabel(icon);
/* 2819 */       add(this.lbl);
/*      */       
/* 2821 */       this.rb = ServiceDialog.createRadioButton(param1String1, param1ActionListener);
/* 2822 */       this.rb.setSelected(param1Boolean);
/* 2823 */       ServiceDialog.addToBG(this.rb, this, param1ButtonGroup);
/*      */     }
/*      */     
/*      */     public void addActionListener(ActionListener param1ActionListener) {
/* 2827 */       this.rb.addActionListener(param1ActionListener);
/*      */     }
/*      */     
/*      */     public boolean isSameAs(Object param1Object) {
/* 2831 */       return (this.rb == param1Object);
/*      */     }
/*      */     
/*      */     public void setEnabled(boolean param1Boolean) {
/* 2835 */       this.rb.setEnabled(param1Boolean);
/* 2836 */       this.lbl.setEnabled(param1Boolean);
/*      */     }
/*      */     
/*      */     public boolean isSelected() {
/* 2840 */       return this.rb.isSelected();
/*      */     }
/*      */     
/*      */     public void setSelected(boolean param1Boolean) {
/* 2844 */       this.rb.setSelected(param1Boolean);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ValidatingFileChooser
/*      */     extends JFileChooser
/*      */   {
/*      */     private ValidatingFileChooser() {}
/*      */     
/*      */     public void approveSelection() {
/*      */       boolean bool;
/* 2855 */       File file1 = getSelectedFile();
/*      */ 
/*      */       
/*      */       try {
/* 2859 */         bool = file1.exists();
/* 2860 */       } catch (SecurityException securityException) {
/* 2861 */         bool = false;
/*      */       } 
/*      */       
/* 2864 */       if (bool) {
/*      */         
/* 2866 */         int i = JOptionPane.showConfirmDialog(this, 
/* 2867 */             ServiceDialog.getMsg("dialog.overwrite"), 
/* 2868 */             ServiceDialog.getMsg("dialog.owtitle"), 0);
/*      */         
/* 2870 */         if (i != 0) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */       
/*      */       try {
/* 2876 */         if (file1.createNewFile()) {
/* 2877 */           file1.delete();
/*      */         }
/* 2879 */       } catch (IOException iOException) {
/* 2880 */         JOptionPane.showMessageDialog(this, 
/* 2881 */             ServiceDialog.getMsg("dialog.writeerror") + " " + file1, 
/* 2882 */             ServiceDialog.getMsg("dialog.owtitle"), 2);
/*      */         
/*      */         return;
/* 2885 */       } catch (SecurityException securityException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2891 */       File file2 = file1.getParentFile();
/* 2892 */       if ((file1.exists() && (
/* 2893 */         !file1.isFile() || !file1.canWrite())) || (file2 != null && (
/*      */         
/* 2895 */         !file2.exists() || (file2.exists() && !file2.canWrite())))) {
/* 2896 */         JOptionPane.showMessageDialog(this, 
/* 2897 */             ServiceDialog.getMsg("dialog.writeerror") + " " + file1, 
/* 2898 */             ServiceDialog.getMsg("dialog.owtitle"), 2);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 2903 */       super.approveSelection();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/ServiceDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */