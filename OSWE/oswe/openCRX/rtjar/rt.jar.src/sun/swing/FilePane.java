/*      */ package sun.swing;
/*      */ import java.awt.AWTKeyStroke;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.io.File;
/*      */ import java.text.DateFormat;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.concurrent.Callable;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JRadioButtonMenuItem;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.ListModel;
/*      */ import javax.swing.RowSorter;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.TransferHandler;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ListDataEvent;
/*      */ import javax.swing.event.ListDataListener;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.event.TableModelEvent;
/*      */ import javax.swing.filechooser.FileSystemView;
/*      */ import javax.swing.plaf.basic.BasicDirectoryModel;
/*      */ import javax.swing.table.DefaultTableColumnModel;
/*      */ import javax.swing.table.TableCellEditor;
/*      */ import javax.swing.table.TableCellRenderer;
/*      */ import javax.swing.table.TableColumn;
/*      */ import javax.swing.table.TableModel;
/*      */ import javax.swing.text.Position;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.shell.ShellFolder;
/*      */ import sun.awt.shell.ShellFolderColumnInfo;
/*      */ 
/*      */ public class FilePane extends JPanel implements PropertyChangeListener {
/*      */   public static final String ACTION_APPROVE_SELECTION = "approveSelection";
/*      */   public static final String ACTION_CANCEL = "cancelSelection";
/*      */   public static final String ACTION_EDIT_FILE_NAME = "editFileName";
/*      */   public static final String ACTION_REFRESH = "refresh";
/*      */   public static final String ACTION_CHANGE_TO_PARENT_DIRECTORY = "Go Up";
/*      */   public static final String ACTION_NEW_FOLDER = "New Folder";
/*      */   public static final String ACTION_VIEW_LIST = "viewTypeList";
/*      */   public static final String ACTION_VIEW_DETAILS = "viewTypeDetails";
/*      */   private Action[] actions;
/*      */   public static final int VIEWTYPE_LIST = 0;
/*      */   public static final int VIEWTYPE_DETAILS = 1;
/*      */   private static final int VIEWTYPE_COUNT = 2;
/*   83 */   private int viewType = -1;
/*   84 */   private JPanel[] viewPanels = new JPanel[2];
/*      */   
/*      */   private JPanel currentViewPanel;
/*      */   private String[] viewTypeActionNames;
/*   88 */   private String filesListAccessibleName = null;
/*   89 */   private String filesDetailsAccessibleName = null;
/*      */   
/*      */   private JPopupMenu contextMenu;
/*      */   
/*      */   private JMenu viewMenu;
/*      */   
/*      */   private String viewMenuLabelText;
/*      */   
/*      */   private String refreshActionLabelText;
/*      */   
/*      */   private String newFolderActionLabelText;
/*      */   
/*      */   private String kiloByteString;
/*      */   private String megaByteString;
/*      */   private String gigaByteString;
/*      */   private String renameErrorTitleText;
/*      */   private String renameErrorText;
/*      */   private String renameErrorFileExistsText;
/*  107 */   private static final Cursor waitCursor = Cursor.getPredefinedCursor(3);
/*      */   
/*  109 */   private final KeyListener detailsKeyListener = new KeyAdapter()
/*      */     {
/*      */       private final long timeFactor;
/*  112 */       private final StringBuilder typedString = new StringBuilder();
/*      */       
/*  114 */       private long lastTime = 1000L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void keyTyped(KeyEvent param1KeyEvent) {
/*  131 */         BasicDirectoryModel basicDirectoryModel = FilePane.this.getModel();
/*  132 */         int i = basicDirectoryModel.getSize();
/*      */         
/*  134 */         if (FilePane.this.detailsTable == null || i == 0 || param1KeyEvent
/*  135 */           .isAltDown() || param1KeyEvent.isControlDown() || param1KeyEvent.isMetaDown()) {
/*      */           return;
/*      */         }
/*      */         
/*  139 */         InputMap inputMap = FilePane.this.detailsTable.getInputMap(1);
/*  140 */         KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(param1KeyEvent);
/*      */         
/*  142 */         if (inputMap != null && inputMap.get(keyStroke) != null) {
/*      */           return;
/*      */         }
/*      */         
/*  146 */         int j = FilePane.this.detailsTable.getSelectionModel().getLeadSelectionIndex();
/*      */         
/*  148 */         if (j < 0) {
/*  149 */           j = 0;
/*      */         }
/*      */         
/*  152 */         if (j >= i) {
/*  153 */           j = i - 1;
/*      */         }
/*      */         
/*  156 */         char c = param1KeyEvent.getKeyChar();
/*      */         
/*  158 */         long l = param1KeyEvent.getWhen();
/*      */         
/*  160 */         if (l - this.lastTime < this.timeFactor) {
/*  161 */           if (this.typedString.length() == 1 && this.typedString.charAt(0) == c) {
/*      */ 
/*      */             
/*  164 */             j++;
/*      */           } else {
/*  166 */             this.typedString.append(c);
/*      */           } 
/*      */         } else {
/*  169 */           j++;
/*      */           
/*  171 */           this.typedString.setLength(0);
/*  172 */           this.typedString.append(c);
/*      */         } 
/*      */         
/*  175 */         this.lastTime = l;
/*      */         
/*  177 */         if (j >= i) {
/*  178 */           j = 0;
/*      */         }
/*      */ 
/*      */         
/*  182 */         int k = getNextMatch(j, i - 1);
/*      */         
/*  184 */         if (k < 0 && j > 0) {
/*  185 */           k = getNextMatch(0, j - 1);
/*      */         }
/*      */         
/*  188 */         if (k >= 0) {
/*  189 */           FilePane.this.detailsTable.getSelectionModel().setSelectionInterval(k, k);
/*      */           
/*  191 */           Rectangle rectangle = FilePane.this.detailsTable.getCellRect(k, FilePane.this
/*  192 */               .detailsTable.convertColumnIndexToView(0), false);
/*  193 */           FilePane.this.detailsTable.scrollRectToVisible(rectangle);
/*      */         } 
/*      */       }
/*      */       
/*      */       private int getNextMatch(int param1Int1, int param1Int2) {
/*  198 */         BasicDirectoryModel basicDirectoryModel = FilePane.this.getModel();
/*  199 */         JFileChooser jFileChooser = FilePane.this.getFileChooser();
/*  200 */         FilePane.DetailsTableRowSorter detailsTableRowSorter = FilePane.this.getRowSorter();
/*      */         
/*  202 */         String str = this.typedString.toString().toLowerCase();
/*      */ 
/*      */         
/*  205 */         for (int i = param1Int1; i <= param1Int2; i++) {
/*  206 */           File file = (File)basicDirectoryModel.getElementAt(detailsTableRowSorter.convertRowIndexToModel(i));
/*      */           
/*  208 */           String str1 = jFileChooser.getName(file).toLowerCase();
/*      */           
/*  210 */           if (str1.startsWith(str)) {
/*  211 */             return i;
/*      */           }
/*      */         } 
/*      */         
/*  215 */         return -1;
/*      */       }
/*      */     };
/*      */   
/*  219 */   private FocusListener editorFocusListener = new FocusAdapter() {
/*      */       public void focusLost(FocusEvent param1FocusEvent) {
/*  221 */         if (!param1FocusEvent.isTemporary()) {
/*  222 */           FilePane.this.applyEdit();
/*      */         }
/*      */       }
/*      */     };
/*      */   
/*  227 */   private static FocusListener repaintListener = new FocusListener() {
/*      */       public void focusGained(FocusEvent param1FocusEvent) {
/*  229 */         repaintSelection(param1FocusEvent.getSource());
/*      */       }
/*      */       
/*      */       public void focusLost(FocusEvent param1FocusEvent) {
/*  233 */         repaintSelection(param1FocusEvent.getSource());
/*      */       }
/*      */       
/*      */       private void repaintSelection(Object param1Object) {
/*  237 */         if (param1Object instanceof JList) {
/*  238 */           repaintListSelection((JList)param1Object);
/*  239 */         } else if (param1Object instanceof JTable) {
/*  240 */           repaintTableSelection((JTable)param1Object);
/*      */         } 
/*      */       }
/*      */       
/*      */       private void repaintListSelection(JList param1JList) {
/*  245 */         int[] arrayOfInt = param1JList.getSelectedIndices();
/*  246 */         for (int i : arrayOfInt) {
/*  247 */           Rectangle rectangle = param1JList.getCellBounds(i, i);
/*  248 */           param1JList.repaint(rectangle);
/*      */         } 
/*      */       }
/*      */       
/*      */       private void repaintTableSelection(JTable param1JTable) {
/*  253 */         int i = param1JTable.getSelectionModel().getMinSelectionIndex();
/*  254 */         int j = param1JTable.getSelectionModel().getMaxSelectionIndex();
/*  255 */         if (i == -1 || j == -1) {
/*      */           return;
/*      */         }
/*      */         
/*  259 */         int k = param1JTable.convertColumnIndexToView(0);
/*      */         
/*  261 */         Rectangle rectangle1 = param1JTable.getCellRect(i, k, false);
/*  262 */         Rectangle rectangle2 = param1JTable.getCellRect(j, k, false);
/*  263 */         Rectangle rectangle3 = rectangle1.union(rectangle2);
/*  264 */         param1JTable.repaint(rectangle3);
/*      */       }
/*      */     };
/*      */   
/*      */   private boolean smallIconsView = false;
/*      */   private Border listViewBorder;
/*      */   private Color listViewBackground;
/*      */   private boolean listViewWindowsStyle;
/*      */   private boolean readOnly;
/*      */   private boolean fullRowSelection = false;
/*      */   private ListSelectionModel listSelectionModel;
/*      */   private JList list;
/*      */   private JTable detailsTable;
/*      */   private static final int COLUMN_FILENAME = 0;
/*      */   private File newFolderFile;
/*      */   private FileChooserUIAccessor fileChooserUIAccessor;
/*      */   private DetailsTableModel detailsTableModel;
/*      */   private DetailsTableRowSorter rowSorter;
/*      */   private DetailsTableCellEditor tableCellEditor;
/*      */   int lastIndex;
/*      */   File editFile;
/*      */   JTextField editCell;
/*      */   protected Action newFolderAction;
/*      */   private Handler handler;
/*      */   
/*      */   public FilePane(FileChooserUIAccessor paramFileChooserUIAccessor)
/*      */   {
/*  291 */     super(new BorderLayout());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1320 */     this.lastIndex = -1;
/* 1321 */     this.editFile = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1345 */     this.editCell = null; this.fileChooserUIAccessor = paramFileChooserUIAccessor; installDefaults(); createActionMap(); } public void uninstallUI() { if (getModel() != null) getModel().removePropertyChangeListener(this);  } protected JFileChooser getFileChooser() { return this.fileChooserUIAccessor.getFileChooser(); } protected BasicDirectoryModel getModel() { return this.fileChooserUIAccessor.getModel(); } public int getViewType() { return this.viewType; } public void setViewType(int paramInt) { JTable jTable; if (paramInt == this.viewType) return;  int i = this.viewType; this.viewType = paramInt; JPanel jPanel = null; JList jList = null; switch (paramInt) { case 0: if (this.viewPanels[paramInt] == null) { jPanel = this.fileChooserUIAccessor.createList(); if (jPanel == null) jPanel = createList();  this.list = (JList)findChildComponent(jPanel, JList.class); if (this.listSelectionModel == null) { this.listSelectionModel = this.list.getSelectionModel(); if (this.detailsTable != null) this.detailsTable.setSelectionModel(this.listSelectionModel);  } else { this.list.setSelectionModel(this.listSelectionModel); }  }  this.list.setLayoutOrientation(1); jList = this.list; break;case 1: if (this.viewPanels[paramInt] == null) { jPanel = this.fileChooserUIAccessor.createDetailsView(); if (jPanel == null) jPanel = createDetailsView();  this.detailsTable = (JTable)findChildComponent(jPanel, JTable.class); this.detailsTable.setRowHeight(Math.max(this.detailsTable.getFont().getSize() + 4, 17)); if (this.listSelectionModel != null) this.detailsTable.setSelectionModel(this.listSelectionModel);  }  jTable = this.detailsTable; break; }  if (jPanel != null) { this.viewPanels[paramInt] = jPanel; recursivelySetInheritsPopupMenu(jPanel, true); }  boolean bool = false; if (this.currentViewPanel != null) { Component component = DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner(); bool = (component == this.detailsTable || component == this.list) ? true : false; remove(this.currentViewPanel); }  this.currentViewPanel = this.viewPanels[paramInt]; add(this.currentViewPanel, "Center"); if (bool && jTable != null) jTable.requestFocusInWindow();  revalidate(); repaint(); updateViewMenu(); firePropertyChange("viewType", i, paramInt); } class ViewTypeAction extends AbstractAction {
/*      */     private int viewType; ViewTypeAction(int param1Int) { super(FilePane.this.viewTypeActionNames[param1Int]); String str; this.viewType = param1Int; switch (param1Int) { case 0: str = "viewTypeList"; break;case 1: str = "viewTypeDetails"; break;default: str = (String)getValue("Name"); break; }  putValue("ActionCommandKey", str); } public void actionPerformed(ActionEvent param1ActionEvent) { FilePane.this.setViewType(this.viewType); } } public Action getViewTypeAction(int paramInt) { return new ViewTypeAction(paramInt); } private static void recursivelySetInheritsPopupMenu(Container paramContainer, boolean paramBoolean) { if (paramContainer instanceof JComponent) ((JComponent)paramContainer).setInheritsPopupMenu(paramBoolean);  int i = paramContainer.getComponentCount(); for (byte b = 0; b < i; b++) recursivelySetInheritsPopupMenu((Container)paramContainer.getComponent(b), paramBoolean);  } protected void installDefaults() { Locale locale = getFileChooser().getLocale(); this.listViewBorder = UIManager.getBorder("FileChooser.listViewBorder"); this.listViewBackground = UIManager.getColor("FileChooser.listViewBackground"); this.listViewWindowsStyle = UIManager.getBoolean("FileChooser.listViewWindowsStyle"); this.readOnly = UIManager.getBoolean("FileChooser.readOnly"); this.viewMenuLabelText = UIManager.getString("FileChooser.viewMenuLabelText", locale); this.refreshActionLabelText = UIManager.getString("FileChooser.refreshActionLabelText", locale); this.newFolderActionLabelText = UIManager.getString("FileChooser.newFolderActionLabelText", locale); this.viewTypeActionNames = new String[2]; this.viewTypeActionNames[0] = UIManager.getString("FileChooser.listViewActionLabelText", locale); this.viewTypeActionNames[1] = UIManager.getString("FileChooser.detailsViewActionLabelText", locale); this.kiloByteString = UIManager.getString("FileChooser.fileSizeKiloBytes", locale); this.megaByteString = UIManager.getString("FileChooser.fileSizeMegaBytes", locale); this.gigaByteString = UIManager.getString("FileChooser.fileSizeGigaBytes", locale); this.fullRowSelection = UIManager.getBoolean("FileView.fullRowSelection"); this.filesListAccessibleName = UIManager.getString("FileChooser.filesListAccessibleName", locale); this.filesDetailsAccessibleName = UIManager.getString("FileChooser.filesDetailsAccessibleName", locale); this.renameErrorTitleText = UIManager.getString("FileChooser.renameErrorTitleText", locale); this.renameErrorText = UIManager.getString("FileChooser.renameErrorText", locale); this.renameErrorFileExistsText = UIManager.getString("FileChooser.renameErrorFileExistsText", locale); } public Action[] getActions() { if (this.actions == null) { ArrayList<FilePaneAction> arrayList = new ArrayList(8); arrayList.add(new FilePaneAction("cancelSelection")); arrayList.add(new FilePaneAction(this, "editFileName")); arrayList.add(new FilePaneAction(this, this.refreshActionLabelText, "refresh")); Action action = this.fileChooserUIAccessor.getApproveSelectionAction(); if (action != null) arrayList.add(action);  action = this.fileChooserUIAccessor.getChangeToParentDirectoryAction(); if (action != null) arrayList.add(action);  action = getNewFolderAction(); if (action != null) arrayList.add(action);  action = getViewTypeAction(0); if (action != null) arrayList.add(action);  action = getViewTypeAction(1); if (action != null) arrayList.add(action);  this.actions = arrayList.<Action>toArray(new Action[arrayList.size()]); }  class FilePaneAction extends AbstractAction {
/*      */       FilePaneAction(String param1String) { this(param1String, param1String); } FilePaneAction(String param1String1, String param1String2) { super(param1String1); putValue("ActionCommandKey", param1String2); } public void actionPerformed(ActionEvent param1ActionEvent) { String str = (String)getValue("ActionCommandKey"); if (str == "cancelSelection") { if (FilePane.this.editFile != null) { FilePane.this.cancelEdit(); } else { FilePane.this.getFileChooser().cancelSelection(); }  } else if (str == "editFileName") { JFileChooser jFileChooser = FilePane.this.getFileChooser(); int i = FilePane.this.listSelectionModel.getMinSelectionIndex(); if (i >= 0 && FilePane.this.editFile == null && (!jFileChooser.isMultiSelectionEnabled() || (jFileChooser.getSelectedFiles()).length <= 1)) FilePane.this.editFileName(i);  } else if (str == "refresh") { FilePane.this.getFileChooser().rescanCurrentDirectory(); }  } public boolean isEnabled() { String str = (String)getValue("ActionCommandKey"); if (str == "cancelSelection") return FilePane.this.getFileChooser().isEnabled();  if (str == "editFileName") return (!FilePane.this.readOnly && FilePane.this.getFileChooser().isEnabled());  return true; } }; return this.actions; } protected void createActionMap() { addActionsToMap(getActionMap(), getActions()); } public static void addActionsToMap(ActionMap paramActionMap, Action[] paramArrayOfAction) { if (paramActionMap != null && paramArrayOfAction != null) for (Action action : paramArrayOfAction) { String str = (String)action.getValue("ActionCommandKey"); if (str == null) str = (String)action.getValue("Name");  paramActionMap.put(str, action); }   } private void updateListRowCount(JList paramJList) { if (this.smallIconsView) { paramJList.setVisibleRowCount(getModel().getSize() / 3); } else { paramJList.setVisibleRowCount(-1); }  } public JPanel createList() { JPanel jPanel = new JPanel(new BorderLayout()); final JFileChooser fileChooser = getFileChooser(); final JList<Object> list = new JList() { public int getNextMatch(String param1String, int param1Int, Position.Bias param1Bias) { ListModel<File> listModel = getModel(); int i = listModel.getSize(); if (param1String == null || param1Int < 0 || param1Int >= i) throw new IllegalArgumentException();  boolean bool = (param1Bias == Position.Bias.Backward) ? true : false; int j; for (j = param1Int; bool ? (j >= 0) : (j < i); j += bool ? -1 : 1) { String str = fileChooser.getName(listModel.getElementAt(j)); if (str.regionMatches(true, 0, param1String, 0, param1String.length())) return j;  }  return -1; } }; jList.setCellRenderer(new FileRenderer()); jList.setLayoutOrientation(1); jList.putClientProperty("List.isFileList", Boolean.TRUE); if (this.listViewWindowsStyle) jList.addFocusListener(repaintListener);  updateListRowCount(jList); getModel().addListDataListener(new ListDataListener() { public void intervalAdded(ListDataEvent param1ListDataEvent) { FilePane.this.updateListRowCount(list); } public void intervalRemoved(ListDataEvent param1ListDataEvent) { FilePane.this.updateListRowCount(list); } public void contentsChanged(ListDataEvent param1ListDataEvent) { if (FilePane.this.isShowing()) FilePane.this.clearSelection();  FilePane.this.updateListRowCount(list); } }); getModel().addPropertyChangeListener(this); if (jFileChooser.isMultiSelectionEnabled()) { jList.setSelectionMode(2); } else { jList.setSelectionMode(0); }  jList.setModel(new SortableListModel()); jList.addListSelectionListener(createListSelectionListener()); jList.addMouseListener(getMouseHandler()); JScrollPane jScrollPane = new JScrollPane(jList); if (this.listViewBackground != null) jList.setBackground(this.listViewBackground);  if (this.listViewBorder != null) jScrollPane.setBorder(this.listViewBorder);  jList.putClientProperty("AccessibleName", this.filesListAccessibleName); jPanel.add(jScrollPane, "Center"); return jPanel; } private class SortableListModel extends AbstractListModel<Object> implements TableModelListener, RowSorterListener {
/*      */     public SortableListModel() { FilePane.this.getDetailsTableModel().addTableModelListener(this); FilePane.this.getRowSorter().addRowSorterListener(this); } public int getSize() { return FilePane.this.getModel().getSize(); } public Object getElementAt(int param1Int) { return FilePane.this.getModel().getElementAt(FilePane.this.getRowSorter().convertRowIndexToModel(param1Int)); } public void tableChanged(TableModelEvent param1TableModelEvent) { fireContentsChanged(this, 0, getSize()); } public void sorterChanged(RowSorterEvent param1RowSorterEvent) { fireContentsChanged(this, 0, getSize()); } } private DetailsTableModel getDetailsTableModel() { if (this.detailsTableModel == null) this.detailsTableModel = new DetailsTableModel(getFileChooser());  return this.detailsTableModel; } class DetailsTableModel extends AbstractTableModel implements ListDataListener {
/*      */     JFileChooser chooser; BasicDirectoryModel directoryModel; ShellFolderColumnInfo[] columns; int[] columnMap; DetailsTableModel(JFileChooser param1JFileChooser) { this.chooser = param1JFileChooser; this.directoryModel = FilePane.this.getModel(); this.directoryModel.addListDataListener(this); updateColumnInfo(); } void updateColumnInfo() { File file = this.chooser.getCurrentDirectory(); if (file != null && FilePane.usesShellFolder(this.chooser)) try { file = ShellFolder.getShellFolder(file); } catch (FileNotFoundException fileNotFoundException) {}  ShellFolderColumnInfo[] arrayOfShellFolderColumnInfo = ShellFolder.getFolderColumns(file); ArrayList<ShellFolderColumnInfo> arrayList = new ArrayList(); this.columnMap = new int[arrayOfShellFolderColumnInfo.length]; for (byte b = 0; b < arrayOfShellFolderColumnInfo.length; b++) { ShellFolderColumnInfo shellFolderColumnInfo = arrayOfShellFolderColumnInfo[b]; if (shellFolderColumnInfo.isVisible()) { this.columnMap[arrayList.size()] = b; arrayList.add(shellFolderColumnInfo); }  }  this.columns = new ShellFolderColumnInfo[arrayList.size()]; arrayList.toArray(this.columns); this.columnMap = Arrays.copyOf(this.columnMap, this.columns.length); List<? extends RowSorter.SortKey> list = (FilePane.this.rowSorter == null) ? null : FilePane.this.rowSorter.getSortKeys(); fireTableStructureChanged(); restoreSortKeys(list); } private void restoreSortKeys(List<? extends RowSorter.SortKey> param1List) { if (param1List != null) { for (byte b = 0; b < param1List.size(); b++) { RowSorter.SortKey sortKey = param1List.get(b); if (sortKey.getColumn() >= this.columns.length) { param1List = null; break; }  }  if (param1List != null) FilePane.this.rowSorter.setSortKeys(param1List);  }  } public int getRowCount() { return this.directoryModel.getSize(); } public int getColumnCount() { return this.columns.length; } public Object getValueAt(int param1Int1, int param1Int2) { return getFileColumnValue((File)this.directoryModel.getElementAt(param1Int1), param1Int2); } private Object getFileColumnValue(File param1File, int param1Int) { return (param1Int == 0) ? param1File : ShellFolder.getFolderColumnValue(param1File, this.columnMap[param1Int]); } public void setValueAt(Object param1Object, int param1Int1, int param1Int2) { if (param1Int2 == 0) { final JFileChooser chooser = FilePane.this.getFileChooser(); File file = (File)getValueAt(param1Int1, param1Int2); if (file != null) { String str1 = jFileChooser.getName(file); String str2 = file.getName(); String str3 = ((String)param1Object).trim(); if (!str3.equals(str1)) { String str = str3; int i = str2.length(); int j = str1.length(); if (i > j && str2.charAt(j) == '.') str = str3 + str2.substring(j);  FileSystemView fileSystemView = jFileChooser.getFileSystemView(); final File f2 = fileSystemView.createFileObject(file.getParentFile(), str); if (file1.exists()) { JOptionPane.showMessageDialog(jFileChooser, MessageFormat.format(FilePane.this.renameErrorFileExistsText, new Object[] { str2 }), FilePane.this.renameErrorTitleText, 0); } else if (FilePane.this.getModel().renameFile(file, file1)) { if (fileSystemView.isParent(jFileChooser.getCurrentDirectory(), file1)) SwingUtilities.invokeLater(new Runnable() { public void run() { if (chooser.isMultiSelectionEnabled()) { chooser.setSelectedFiles(new File[] { this.val$f2 }); } else { chooser.setSelectedFile(f2); }  } });  } else { JOptionPane.showMessageDialog(jFileChooser, MessageFormat.format(FilePane.this.renameErrorText, new Object[] { str2 }), FilePane.this.renameErrorTitleText, 0); }  }  }  }  } public boolean isCellEditable(int param1Int1, int param1Int2) { File file = FilePane.this.getFileChooser().getCurrentDirectory(); return (!FilePane.this.readOnly && param1Int2 == 0 && FilePane.this.canWrite(file)); } public void contentsChanged(ListDataEvent param1ListDataEvent) { new FilePane.DelayedSelectionUpdater(); fireTableDataChanged(); } public void intervalAdded(ListDataEvent param1ListDataEvent) { int i = param1ListDataEvent.getIndex0(); int j = param1ListDataEvent.getIndex1(); if (i == j) { File file = (File)FilePane.this.getModel().getElementAt(i); if (file.equals(FilePane.this.newFolderFile)) { new FilePane.DelayedSelectionUpdater(file); FilePane.this.newFolderFile = null; }  }  fireTableRowsInserted(param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1()); } public void intervalRemoved(ListDataEvent param1ListDataEvent) { fireTableRowsDeleted(param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1()); } public ShellFolderColumnInfo[] getColumns() { return this.columns; } } private void updateDetailsColumnModel(JTable paramJTable) { if (paramJTable != null) { ShellFolderColumnInfo[] arrayOfShellFolderColumnInfo = this.detailsTableModel.getColumns(); DefaultTableColumnModel defaultTableColumnModel = new DefaultTableColumnModel(); for (byte b = 0; b < arrayOfShellFolderColumnInfo.length; b++) { ShellFolderColumnInfo shellFolderColumnInfo = arrayOfShellFolderColumnInfo[b]; TableColumn tableColumn = new TableColumn(b); String str = shellFolderColumnInfo.getTitle(); if (str != null && str.startsWith("FileChooser.") && str.endsWith("HeaderText")) { String str1 = UIManager.getString(str, paramJTable.getLocale()); if (str1 != null) str = str1;  }  tableColumn.setHeaderValue(str); Integer integer = shellFolderColumnInfo.getWidth(); if (integer != null) tableColumn.setPreferredWidth(integer.intValue());  defaultTableColumnModel.addColumn(tableColumn); }  if (!this.readOnly && defaultTableColumnModel.getColumnCount() > 0) defaultTableColumnModel.getColumn(0).setCellEditor(getDetailsTableCellEditor());  paramJTable.setColumnModel(defaultTableColumnModel); }  } private DetailsTableRowSorter getRowSorter() { if (this.rowSorter == null) this.rowSorter = new DetailsTableRowSorter();  return this.rowSorter; } private class DetailsTableRowSorter extends TableRowSorter<TableModel> {
/*      */     public DetailsTableRowSorter() { setModelWrapper(new SorterModelWrapper()); } public void updateComparators(ShellFolderColumnInfo[] param1ArrayOfShellFolderColumnInfo) { for (byte b = 0; b < param1ArrayOfShellFolderColumnInfo.length; b++) { Comparator<?> comparator = param1ArrayOfShellFolderColumnInfo[b].getComparator(); if (comparator != null) comparator = new FilePane.DirectoriesFirstComparatorWrapper(b, comparator);  setComparator(b, comparator); }  } public void sort() { ShellFolder.invoke(new Callable<Void>() { public Void call() { FilePane.DetailsTableRowSorter.this.sort(); return null; } }); } public void modelStructureChanged() { super.modelStructureChanged(); updateComparators(FilePane.this.detailsTableModel.getColumns()); } private class SorterModelWrapper extends DefaultRowSorter.ModelWrapper<TableModel, Integer> {
/* 1351 */       private SorterModelWrapper() {} public TableModel getModel() { return FilePane.this.getDetailsTableModel(); } public int getColumnCount() { return FilePane.this.getDetailsTableModel().getColumnCount(); } public int getRowCount() { return FilePane.this.getDetailsTableModel().getRowCount(); } public Object getValueAt(int param2Int1, int param2Int2) { return FilePane.this.getModel().getElementAt(param2Int1); } public Integer getIdentifier(int param2Int) { return Integer.valueOf(param2Int); } } } private class DirectoriesFirstComparatorWrapper implements Comparator<File> { private Comparator comparator; private int column; public DirectoriesFirstComparatorWrapper(int param1Int, Comparator param1Comparator) { this.column = param1Int; this.comparator = param1Comparator; } public int compare(File param1File1, File param1File2) { if (param1File1 != null && param1File2 != null) { boolean bool1 = FilePane.this.getFileChooser().isTraversable(param1File1); boolean bool2 = FilePane.this.getFileChooser().isTraversable(param1File2); if (bool1 && !bool2) return -1;  if (!bool1 && bool2) return 1;  }  if (FilePane.this.detailsTableModel.getColumns()[this.column].isCompareByColumn()) return this.comparator.compare(FilePane.this.getDetailsTableModel().getFileColumnValue(param1File1, this.column), FilePane.this.getDetailsTableModel().getFileColumnValue(param1File2, this.column));  return this.comparator.compare(param1File1, param1File2); } } private DetailsTableCellEditor getDetailsTableCellEditor() { if (this.tableCellEditor == null) this.tableCellEditor = new DetailsTableCellEditor(new JTextField());  return this.tableCellEditor; } private class DetailsTableCellEditor extends DefaultCellEditor { private final JTextField tf; public DetailsTableCellEditor(JTextField param1JTextField) { super(param1JTextField); this.tf = param1JTextField; param1JTextField.setName("Table.editor"); param1JTextField.addFocusListener(FilePane.this.editorFocusListener); } public Component getTableCellEditorComponent(JTable param1JTable, Object param1Object, boolean param1Boolean, int param1Int1, int param1Int2) { Component component = super.getTableCellEditorComponent(param1JTable, param1Object, param1Boolean, param1Int1, param1Int2); if (param1Object instanceof File) { this.tf.setText(FilePane.this.getFileChooser().getName((File)param1Object)); this.tf.selectAll(); }  return component; } } class DetailsTableCellRenderer extends DefaultTableCellRenderer { JFileChooser chooser; DateFormat df; DetailsTableCellRenderer(JFileChooser param1JFileChooser) { this.chooser = param1JFileChooser; this.df = DateFormat.getDateTimeInstance(3, 3, param1JFileChooser.getLocale()); } public void setBounds(int param1Int1, int param1Int2, int param1Int3, int param1Int4) { if (getHorizontalAlignment() == 10 && !FilePane.this.fullRowSelection) { param1Int3 = Math.min(param1Int3, (getPreferredSize()).width + 4); } else { param1Int1 -= 4; }  super.setBounds(param1Int1, param1Int2, param1Int3, param1Int4); } public Insets getInsets(Insets param1Insets) { param1Insets = super.getInsets(param1Insets); param1Insets.left += 4; param1Insets.right += 4; return param1Insets; } public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) { String str; if ((param1JTable.convertColumnIndexToModel(param1Int2) != 0 || (FilePane.this.listViewWindowsStyle && !param1JTable.isFocusOwner())) && !FilePane.this.fullRowSelection) param1Boolean1 = false;  super.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2); setIcon((Icon)null); int i = param1JTable.convertColumnIndexToModel(param1Int2); ShellFolderColumnInfo shellFolderColumnInfo = FilePane.this.detailsTableModel.getColumns()[i]; Integer integer = shellFolderColumnInfo.getAlignment(); if (integer == null) integer = Integer.valueOf((param1Object instanceof Number) ? 4 : 10);  setHorizontalAlignment(integer.intValue()); if (param1Object == null) { str = ""; } else if (param1Object instanceof File) { File file = (File)param1Object; str = this.chooser.getName(file); Icon icon = this.chooser.getIcon(file); setIcon(icon); } else if (param1Object instanceof Long) { long l = ((Long)param1Object).longValue() / 1024L; if (FilePane.this.listViewWindowsStyle) { str = MessageFormat.format(FilePane.this.kiloByteString, new Object[] { Long.valueOf(l + 1L) }); } else if (l < 1024L) { str = MessageFormat.format(FilePane.this.kiloByteString, new Object[] { Long.valueOf((l == 0L) ? 1L : l) }); } else { l /= 1024L; if (l < 1024L) { str = MessageFormat.format(FilePane.this.megaByteString, new Object[] { Long.valueOf(l) }); } else { l /= 1024L; str = MessageFormat.format(FilePane.this.gigaByteString, new Object[] { Long.valueOf(l) }); }  }  } else if (param1Object instanceof Date) { str = this.df.format((Date)param1Object); } else { str = param1Object.toString(); }  setText(str); return this; } } public JPanel createDetailsView() { final JFileChooser chooser = getFileChooser(); JPanel jPanel = new JPanel(new BorderLayout()); JTable jTable = new JTable(getDetailsTableModel()) { protected boolean processKeyBinding(KeyStroke param1KeyStroke, KeyEvent param1KeyEvent, int param1Int, boolean param1Boolean) { if (param1KeyEvent.getKeyCode() == 27 && getCellEditor() == null) { chooser.dispatchEvent(param1KeyEvent); return true; }  return super.processKeyBinding(param1KeyStroke, param1KeyEvent, param1Int, param1Boolean); } public void tableChanged(TableModelEvent param1TableModelEvent) { super.tableChanged(param1TableModelEvent); if (param1TableModelEvent.getFirstRow() == -1) FilePane.this.updateDetailsColumnModel(this);  } }; jTable.setRowSorter(getRowSorter()); jTable.setAutoCreateColumnsFromModel(false); jTable.setComponentOrientation(jFileChooser.getComponentOrientation()); jTable.setAutoResizeMode(0); jTable.setShowGrid(false); jTable.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE); jTable.addKeyListener(this.detailsKeyListener); Font font = this.list.getFont(); jTable.setFont(font); jTable.setIntercellSpacing(new Dimension(0, 0)); AlignableTableHeaderRenderer alignableTableHeaderRenderer = new AlignableTableHeaderRenderer(jTable.getTableHeader().getDefaultRenderer()); jTable.getTableHeader().setDefaultRenderer(alignableTableHeaderRenderer); DetailsTableCellRenderer detailsTableCellRenderer = new DetailsTableCellRenderer(jFileChooser); jTable.setDefaultRenderer(Object.class, detailsTableCellRenderer); jTable.getColumnModel().getSelectionModel().setSelectionMode(0); jTable.addMouseListener(getMouseHandler()); jTable.putClientProperty("Table.isFileList", Boolean.TRUE); if (this.listViewWindowsStyle) jTable.addFocusListener(repaintListener);  ActionMap actionMap = SwingUtilities.getUIActionMap(jTable); actionMap.remove("selectNextRowCell"); actionMap.remove("selectPreviousRowCell"); actionMap.remove("selectNextColumnCell"); actionMap.remove("selectPreviousColumnCell"); jTable.setFocusTraversalKeys(0, (Set<? extends AWTKeyStroke>)null); jTable.setFocusTraversalKeys(1, (Set<? extends AWTKeyStroke>)null); JScrollPane jScrollPane = new JScrollPane(jTable); jScrollPane.setComponentOrientation(jFileChooser.getComponentOrientation()); LookAndFeel.installColors(jScrollPane.getViewport(), "Table.background", "Table.foreground"); jScrollPane.addComponentListener(new ComponentAdapter() { public void componentResized(ComponentEvent param1ComponentEvent) { JScrollPane jScrollPane = (JScrollPane)param1ComponentEvent.getComponent(); FilePane.this.fixNameColumnWidth((jScrollPane.getViewport().getSize()).width); jScrollPane.removeComponentListener(this); } }); jScrollPane.addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent param1MouseEvent) { JScrollPane jScrollPane = (JScrollPane)param1MouseEvent.getComponent(); JTable jTable = (JTable)jScrollPane.getViewport().getView(); if (!param1MouseEvent.isShiftDown() || jTable.getSelectionModel().getSelectionMode() == 0) { FilePane.this.clearSelection(); TableCellEditor tableCellEditor = jTable.getCellEditor(); if (tableCellEditor != null) tableCellEditor.stopCellEditing();  }  } }); jTable.setForeground(this.list.getForeground()); jTable.setBackground(this.list.getBackground()); if (this.listViewBorder != null) jScrollPane.setBorder(this.listViewBorder);  jPanel.add(jScrollPane, "Center"); this.detailsTableModel.fireTableStructureChanged(); jTable.putClientProperty("AccessibleName", this.filesDetailsAccessibleName); return jPanel; } private void editFileName(int paramInt) { Rectangle rectangle; ComponentOrientation componentOrientation; Icon icon; byte b; JFileChooser jFileChooser = getFileChooser();
/* 1352 */     File file = jFileChooser.getCurrentDirectory();
/*      */     
/* 1354 */     if (this.readOnly || !canWrite(file)) {
/*      */       return;
/*      */     }
/*      */     
/* 1358 */     ensureIndexIsVisible(paramInt);
/* 1359 */     switch (this.viewType)
/*      */     { case 0:
/* 1361 */         this.editFile = (File)getModel().getElementAt(getRowSorter().convertRowIndexToModel(paramInt));
/* 1362 */         rectangle = this.list.getCellBounds(paramInt, paramInt);
/* 1363 */         if (this.editCell == null) {
/* 1364 */           this.editCell = new JTextField();
/* 1365 */           this.editCell.setName("Tree.cellEditor");
/* 1366 */           this.editCell.addActionListener(new EditActionListener());
/* 1367 */           this.editCell.addFocusListener(this.editorFocusListener);
/* 1368 */           this.editCell.setNextFocusableComponent(this.list);
/*      */         } 
/* 1370 */         this.list.add(this.editCell);
/* 1371 */         this.editCell.setText(jFileChooser.getName(this.editFile));
/* 1372 */         componentOrientation = this.list.getComponentOrientation();
/* 1373 */         this.editCell.setComponentOrientation(componentOrientation);
/*      */         
/* 1375 */         icon = jFileChooser.getIcon(this.editFile);
/*      */ 
/*      */         
/* 1378 */         b = (icon == null) ? 20 : (icon.getIconWidth() + 4);
/*      */         
/* 1380 */         if (componentOrientation.isLeftToRight()) {
/* 1381 */           this.editCell.setBounds(b + rectangle.x, rectangle.y, rectangle.width - b, rectangle.height);
/*      */         } else {
/* 1383 */           this.editCell.setBounds(rectangle.x, rectangle.y, rectangle.width - b, rectangle.height);
/*      */         } 
/* 1385 */         this.editCell.requestFocus();
/* 1386 */         this.editCell.selectAll();
/*      */         break;
/*      */       
/*      */       case 1:
/* 1390 */         this.detailsTable.editCellAt(paramInt, 0); break; }  }
/*      */   private class AlignableTableHeaderRenderer implements TableCellRenderer {
/*      */     TableCellRenderer wrappedRenderer;
/*      */     public AlignableTableHeaderRenderer(TableCellRenderer param1TableCellRenderer) { this.wrappedRenderer = param1TableCellRenderer; }
/*      */     public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) { Component component = this.wrappedRenderer.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2); int i = param1JTable.convertColumnIndexToModel(param1Int2); ShellFolderColumnInfo shellFolderColumnInfo = FilePane.this.detailsTableModel.getColumns()[i]; Integer integer = shellFolderColumnInfo.getAlignment(); if (integer == null) integer = Integer.valueOf(0);  if (component instanceof JLabel) ((JLabel)component).setHorizontalAlignment(integer.intValue());  return component; }
/*      */   }
/*      */   private void fixNameColumnWidth(int paramInt) { TableColumn tableColumn = this.detailsTable.getColumnModel().getColumn(0); int i = (this.detailsTable.getPreferredSize()).width; if (i < paramInt) tableColumn.setPreferredWidth(tableColumn.getPreferredWidth() + paramInt - i);  } private class DelayedSelectionUpdater implements Runnable {
/*      */     File editFile; DelayedSelectionUpdater() { this(null); } DelayedSelectionUpdater(File param1File) { this.editFile = param1File; if (FilePane.this.isShowing()) SwingUtilities.invokeLater(this);  } public void run() { FilePane.this.setFileSelected(); if (this.editFile != null) { FilePane.this.editFileName(FilePane.this.getRowSorter().convertRowIndexToView(FilePane.this.getModel().indexOf(this.editFile))); this.editFile = null; }  }
/* 1398 */   } public ListSelectionListener createListSelectionListener() { return this.fileChooserUIAccessor.createListSelectionListener(); } private int getEditIndex() { return this.lastIndex; } private void setEditIndex(int paramInt) { this.lastIndex = paramInt; } private void resetEditIndex() { this.lastIndex = -1; } private void cancelEdit() { if (this.editFile != null) { this.editFile = null; this.list.remove(this.editCell); repaint(); } else if (this.detailsTable != null && this.detailsTable.isEditing()) { this.detailsTable.getCellEditor().cancelCellEditing(); }  } class EditActionListener implements ActionListener { public void actionPerformed(ActionEvent param1ActionEvent) { FilePane.this.applyEdit(); }
/*      */      }
/*      */ 
/*      */   
/*      */   private void applyEdit() {
/* 1403 */     if (this.editFile != null && this.editFile.exists()) {
/* 1404 */       JFileChooser jFileChooser = getFileChooser();
/* 1405 */       String str1 = jFileChooser.getName(this.editFile);
/* 1406 */       String str2 = this.editFile.getName();
/* 1407 */       String str3 = this.editCell.getText().trim();
/*      */ 
/*      */       
/* 1410 */       if (!str3.equals(str1)) {
/* 1411 */         String str = str3;
/*      */         
/* 1413 */         int i = str2.length();
/* 1414 */         int j = str1.length();
/* 1415 */         if (i > j && str2.charAt(j) == '.') {
/* 1416 */           str = str3 + str2.substring(j);
/*      */         }
/*      */ 
/*      */         
/* 1420 */         FileSystemView fileSystemView = jFileChooser.getFileSystemView();
/* 1421 */         File file = fileSystemView.createFileObject(this.editFile.getParentFile(), str);
/* 1422 */         if (file.exists()) {
/* 1423 */           JOptionPane.showMessageDialog(jFileChooser, MessageFormat.format(this.renameErrorFileExistsText, new Object[] { str2 }), this.renameErrorTitleText, 0);
/*      */         
/*      */         }
/* 1426 */         else if (getModel().renameFile(this.editFile, file)) {
/* 1427 */           if (fileSystemView.isParent(jFileChooser.getCurrentDirectory(), file)) {
/* 1428 */             if (jFileChooser.isMultiSelectionEnabled()) {
/* 1429 */               jFileChooser.setSelectedFiles(new File[] { file });
/*      */             } else {
/* 1431 */               jFileChooser.setSelectedFile(file);
/*      */             }
/*      */           
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 1438 */           JOptionPane.showMessageDialog(jFileChooser, MessageFormat.format(this.renameErrorText, new Object[] { str2 }), this.renameErrorTitleText, 0);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1444 */     if (this.detailsTable != null && this.detailsTable.isEditing()) {
/* 1445 */       this.detailsTable.getCellEditor().stopCellEditing();
/*      */     }
/* 1447 */     cancelEdit();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Action getNewFolderAction() {
/* 1453 */     if (!this.readOnly && this.newFolderAction == null) {
/* 1454 */       this.newFolderAction = new AbstractAction(this.newFolderActionLabelText)
/*      */         {
/*      */           private Action basicNewFolderAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1468 */             if (this.basicNewFolderAction == null) {
/* 1469 */               this.basicNewFolderAction = FilePane.this.fileChooserUIAccessor.getNewFolderAction();
/*      */             }
/* 1471 */             JFileChooser jFileChooser = FilePane.this.getFileChooser();
/* 1472 */             File file1 = jFileChooser.getSelectedFile();
/* 1473 */             this.basicNewFolderAction.actionPerformed(param1ActionEvent);
/* 1474 */             File file2 = jFileChooser.getSelectedFile();
/* 1475 */             if (file2 != null && !file2.equals(file1) && file2.isDirectory()) {
/* 1476 */               FilePane.this.newFolderFile = file2;
/*      */             }
/*      */           }
/*      */         };
/*      */     }
/* 1481 */     return this.newFolderAction;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class FileRenderer
/*      */     extends DefaultListCellRenderer
/*      */   {
/*      */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 1490 */       if (FilePane.this.listViewWindowsStyle && !param1JList.isFocusOwner()) {
/* 1491 */         param1Boolean1 = false;
/*      */       }
/*      */       
/* 1494 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/* 1495 */       File file = (File)param1Object;
/* 1496 */       String str = FilePane.this.getFileChooser().getName(file);
/* 1497 */       setText(str);
/* 1498 */       setFont(param1JList.getFont());
/*      */       
/* 1500 */       Icon icon = FilePane.this.getFileChooser().getIcon(file);
/* 1501 */       if (icon != null) {
/* 1502 */         setIcon(icon);
/*      */       }
/* 1504 */       else if (FilePane.this.getFileChooser().getFileSystemView().isTraversable(file).booleanValue()) {
/* 1505 */         setText(str + File.separator);
/*      */       } 
/*      */ 
/*      */       
/* 1509 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   void setFileSelected() {
/* 1515 */     if (getFileChooser().isMultiSelectionEnabled() && !isDirectorySelected()) {
/* 1516 */       File[] arrayOfFile = getFileChooser().getSelectedFiles();
/* 1517 */       Object[] arrayOfObject = this.list.getSelectedValues();
/*      */       
/* 1519 */       this.listSelectionModel.setValueIsAdjusting(true);
/*      */       try {
/* 1521 */         int i = this.listSelectionModel.getLeadSelectionIndex();
/* 1522 */         int j = this.listSelectionModel.getAnchorSelectionIndex();
/*      */         
/* 1524 */         Arrays.sort((Object[])arrayOfFile);
/* 1525 */         Arrays.sort(arrayOfObject);
/*      */         
/* 1527 */         byte b1 = 0;
/* 1528 */         byte b2 = 0;
/*      */ 
/*      */ 
/*      */         
/* 1532 */         while (b1 < arrayOfFile.length && b2 < arrayOfObject.length) {
/*      */           
/* 1534 */           int k = arrayOfFile[b1].compareTo((File)arrayOfObject[b2]);
/* 1535 */           if (k < 0) {
/* 1536 */             doSelectFile(arrayOfFile[b1++]); continue;
/* 1537 */           }  if (k > 0) {
/* 1538 */             doDeselectFile(arrayOfObject[b2++]);
/*      */             continue;
/*      */           } 
/* 1541 */           b1++;
/* 1542 */           b2++;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1547 */         while (b1 < arrayOfFile.length) {
/* 1548 */           doSelectFile(arrayOfFile[b1++]);
/*      */         }
/*      */         
/* 1551 */         while (b2 < arrayOfObject.length) {
/* 1552 */           doDeselectFile(arrayOfObject[b2++]);
/*      */         }
/*      */ 
/*      */         
/* 1556 */         if (this.listSelectionModel instanceof DefaultListSelectionModel) {
/* 1557 */           ((DefaultListSelectionModel)this.listSelectionModel)
/* 1558 */             .moveLeadSelectionIndex(i);
/* 1559 */           this.listSelectionModel.setAnchorSelectionIndex(j);
/*      */         } 
/*      */       } finally {
/* 1562 */         this.listSelectionModel.setValueIsAdjusting(false);
/*      */       } 
/*      */     } else {
/* 1565 */       File file; JFileChooser jFileChooser = getFileChooser();
/*      */       
/* 1567 */       if (isDirectorySelected()) {
/* 1568 */         file = getDirectory();
/*      */       } else {
/* 1570 */         file = jFileChooser.getSelectedFile();
/*      */       } 
/*      */       int i;
/* 1573 */       if (file != null && (i = getModel().indexOf(file)) >= 0) {
/* 1574 */         int j = getRowSorter().convertRowIndexToView(i);
/* 1575 */         this.listSelectionModel.setSelectionInterval(j, j);
/* 1576 */         ensureIndexIsVisible(j);
/*      */       } else {
/* 1578 */         clearSelection();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doSelectFile(File paramFile) {
/* 1584 */     int i = getModel().indexOf(paramFile);
/*      */     
/* 1586 */     if (i >= 0) {
/* 1587 */       i = getRowSorter().convertRowIndexToView(i);
/* 1588 */       this.listSelectionModel.addSelectionInterval(i, i);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doDeselectFile(Object paramObject) {
/* 1593 */     int i = getRowSorter().convertRowIndexToView(
/* 1594 */         getModel().indexOf(paramObject));
/* 1595 */     this.listSelectionModel.removeSelectionInterval(i, i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void doSelectedFileChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/* 1601 */     applyEdit();
/* 1602 */     File file = (File)paramPropertyChangeEvent.getNewValue();
/* 1603 */     JFileChooser jFileChooser = getFileChooser();
/* 1604 */     if (file != null && ((jFileChooser
/* 1605 */       .isFileSelectionEnabled() && !file.isDirectory()) || (file
/* 1606 */       .isDirectory() && jFileChooser.isDirectorySelectionEnabled())))
/*      */     {
/* 1608 */       setFileSelected();
/*      */     }
/*      */   }
/*      */   
/*      */   private void doSelectedFilesChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/* 1613 */     applyEdit();
/* 1614 */     File[] arrayOfFile = (File[])paramPropertyChangeEvent.getNewValue();
/* 1615 */     JFileChooser jFileChooser = getFileChooser();
/* 1616 */     if (arrayOfFile != null && arrayOfFile.length > 0 && (arrayOfFile.length > 1 || jFileChooser
/*      */       
/* 1618 */       .isDirectorySelectionEnabled() || !arrayOfFile[0].isDirectory())) {
/* 1619 */       setFileSelected();
/*      */     }
/*      */   }
/*      */   
/*      */   private void doDirectoryChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/* 1624 */     getDetailsTableModel().updateColumnInfo();
/*      */     
/* 1626 */     JFileChooser jFileChooser = getFileChooser();
/* 1627 */     FileSystemView fileSystemView = jFileChooser.getFileSystemView();
/*      */     
/* 1629 */     applyEdit();
/* 1630 */     resetEditIndex();
/* 1631 */     ensureIndexIsVisible(0);
/* 1632 */     File file = jFileChooser.getCurrentDirectory();
/* 1633 */     if (file != null) {
/* 1634 */       if (!this.readOnly) {
/* 1635 */         getNewFolderAction().setEnabled(canWrite(file));
/*      */       }
/* 1637 */       this.fileChooserUIAccessor.getChangeToParentDirectoryAction().setEnabled(!fileSystemView.isRoot(file));
/*      */     } 
/* 1639 */     if (this.list != null) {
/* 1640 */       this.list.clearSelection();
/*      */     }
/*      */   }
/*      */   
/*      */   private void doFilterChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/* 1645 */     applyEdit();
/* 1646 */     resetEditIndex();
/* 1647 */     clearSelection();
/*      */   }
/*      */   
/*      */   private void doFileSelectionModeChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/* 1651 */     applyEdit();
/* 1652 */     resetEditIndex();
/* 1653 */     clearSelection();
/*      */   }
/*      */   
/*      */   private void doMultiSelectionChanged(PropertyChangeEvent paramPropertyChangeEvent) {
/* 1657 */     if (getFileChooser().isMultiSelectionEnabled()) {
/* 1658 */       this.listSelectionModel.setSelectionMode(2);
/*      */     } else {
/* 1660 */       this.listSelectionModel.setSelectionMode(0);
/* 1661 */       clearSelection();
/* 1662 */       getFileChooser().setSelectedFiles((File[])null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 1671 */     if (this.viewType == -1) {
/* 1672 */       setViewType(0);
/*      */     }
/*      */     
/* 1675 */     String str = paramPropertyChangeEvent.getPropertyName();
/* 1676 */     if (str.equals("SelectedFileChangedProperty")) {
/* 1677 */       doSelectedFileChanged(paramPropertyChangeEvent);
/* 1678 */     } else if (str.equals("SelectedFilesChangedProperty")) {
/* 1679 */       doSelectedFilesChanged(paramPropertyChangeEvent);
/* 1680 */     } else if (str.equals("directoryChanged")) {
/* 1681 */       doDirectoryChanged(paramPropertyChangeEvent);
/* 1682 */     } else if (str.equals("fileFilterChanged")) {
/* 1683 */       doFilterChanged(paramPropertyChangeEvent);
/* 1684 */     } else if (str.equals("fileSelectionChanged")) {
/* 1685 */       doFileSelectionModeChanged(paramPropertyChangeEvent);
/* 1686 */     } else if (str.equals("MultiSelectionEnabledChangedProperty")) {
/* 1687 */       doMultiSelectionChanged(paramPropertyChangeEvent);
/* 1688 */     } else if (str.equals("CancelSelection")) {
/* 1689 */       applyEdit();
/* 1690 */     } else if (str.equals("busy")) {
/* 1691 */       setCursor(((Boolean)paramPropertyChangeEvent.getNewValue()).booleanValue() ? waitCursor : null);
/* 1692 */     } else if (str.equals("componentOrientation")) {
/* 1693 */       ComponentOrientation componentOrientation = (ComponentOrientation)paramPropertyChangeEvent.getNewValue();
/* 1694 */       JFileChooser jFileChooser = (JFileChooser)paramPropertyChangeEvent.getSource();
/* 1695 */       if (componentOrientation != paramPropertyChangeEvent.getOldValue()) {
/* 1696 */         jFileChooser.applyComponentOrientation(componentOrientation);
/*      */       }
/* 1698 */       if (this.detailsTable != null) {
/* 1699 */         this.detailsTable.setComponentOrientation(componentOrientation);
/* 1700 */         this.detailsTable.getParent().getParent().setComponentOrientation(componentOrientation);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void ensureIndexIsVisible(int paramInt) {
/* 1706 */     if (paramInt >= 0) {
/* 1707 */       if (this.list != null) {
/* 1708 */         this.list.ensureIndexIsVisible(paramInt);
/*      */       }
/* 1710 */       if (this.detailsTable != null) {
/* 1711 */         this.detailsTable.scrollRectToVisible(this.detailsTable.getCellRect(paramInt, 0, true));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void ensureFileIsVisible(JFileChooser paramJFileChooser, File paramFile) {
/* 1717 */     int i = getModel().indexOf(paramFile);
/* 1718 */     if (i >= 0) {
/* 1719 */       ensureIndexIsVisible(getRowSorter().convertRowIndexToView(i));
/*      */     }
/*      */   }
/*      */   
/*      */   public void rescanCurrentDirectory() {
/* 1724 */     getModel().validateFileCache();
/*      */   }
/*      */   
/*      */   public void clearSelection() {
/* 1728 */     if (this.listSelectionModel != null) {
/* 1729 */       this.listSelectionModel.clearSelection();
/* 1730 */       if (this.listSelectionModel instanceof DefaultListSelectionModel) {
/* 1731 */         ((DefaultListSelectionModel)this.listSelectionModel).moveLeadSelectionIndex(0);
/* 1732 */         this.listSelectionModel.setAnchorSelectionIndex(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public JMenu getViewMenu() {
/* 1738 */     if (this.viewMenu == null) {
/* 1739 */       this.viewMenu = new JMenu(this.viewMenuLabelText);
/* 1740 */       ButtonGroup buttonGroup = new ButtonGroup();
/*      */       
/* 1742 */       for (byte b = 0; b < 2; b++) {
/* 1743 */         JRadioButtonMenuItem jRadioButtonMenuItem = new JRadioButtonMenuItem(new ViewTypeAction(b));
/*      */         
/* 1745 */         buttonGroup.add(jRadioButtonMenuItem);
/* 1746 */         this.viewMenu.add(jRadioButtonMenuItem);
/*      */       } 
/* 1748 */       updateViewMenu();
/*      */     } 
/* 1750 */     return this.viewMenu;
/*      */   }
/*      */   
/*      */   private void updateViewMenu() {
/* 1754 */     if (this.viewMenu != null) {
/* 1755 */       Component[] arrayOfComponent = this.viewMenu.getMenuComponents();
/* 1756 */       for (Component component : arrayOfComponent) {
/* 1757 */         if (component instanceof JRadioButtonMenuItem) {
/* 1758 */           JRadioButtonMenuItem jRadioButtonMenuItem = (JRadioButtonMenuItem)component;
/* 1759 */           if (((ViewTypeAction)jRadioButtonMenuItem.getAction()).viewType == this.viewType) {
/* 1760 */             jRadioButtonMenuItem.setSelected(true);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public JPopupMenu getComponentPopupMenu() {
/* 1768 */     JPopupMenu jPopupMenu = getFileChooser().getComponentPopupMenu();
/* 1769 */     if (jPopupMenu != null) {
/* 1770 */       return jPopupMenu;
/*      */     }
/*      */     
/* 1773 */     JMenu jMenu = getViewMenu();
/* 1774 */     if (this.contextMenu == null) {
/* 1775 */       this.contextMenu = new JPopupMenu();
/* 1776 */       if (jMenu != null) {
/* 1777 */         this.contextMenu.add(jMenu);
/* 1778 */         if (this.listViewWindowsStyle) {
/* 1779 */           this.contextMenu.addSeparator();
/*      */         }
/*      */       } 
/* 1782 */       ActionMap actionMap = getActionMap();
/* 1783 */       Action action1 = actionMap.get("refresh");
/* 1784 */       Action action2 = actionMap.get("New Folder");
/* 1785 */       if (action1 != null) {
/* 1786 */         this.contextMenu.add(action1);
/* 1787 */         if (this.listViewWindowsStyle && action2 != null) {
/* 1788 */           this.contextMenu.addSeparator();
/*      */         }
/*      */       } 
/* 1791 */       if (action2 != null) {
/* 1792 */         this.contextMenu.add(action2);
/*      */       }
/*      */     } 
/* 1795 */     if (jMenu != null) {
/* 1796 */       jMenu.getPopupMenu().setInvoker(jMenu);
/*      */     }
/* 1798 */     return this.contextMenu;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Handler getMouseHandler() {
/* 1805 */     if (this.handler == null) {
/* 1806 */       this.handler = new Handler();
/*      */     }
/* 1808 */     return this.handler;
/*      */   }
/*      */   private class Handler implements MouseListener { private MouseListener doubleClickListener;
/*      */     private Handler() {}
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/*      */       int i;
/* 1815 */       JComponent jComponent = (JComponent)param1MouseEvent.getSource();
/*      */ 
/*      */       
/* 1818 */       if (jComponent instanceof JList) {
/* 1819 */         i = SwingUtilities2.loc2IndexFileList(FilePane.this.list, param1MouseEvent.getPoint());
/* 1820 */       } else if (jComponent instanceof JTable) {
/* 1821 */         JTable jTable = (JTable)jComponent;
/* 1822 */         Point point = param1MouseEvent.getPoint();
/* 1823 */         i = jTable.rowAtPoint(point);
/*      */ 
/*      */         
/* 1826 */         boolean bool = SwingUtilities2.pointOutsidePrefSize(jTable, i, jTable
/* 1827 */             .columnAtPoint(point), point);
/*      */         
/* 1829 */         if (bool && !FilePane.this.fullRowSelection) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1834 */         if (i >= 0 && FilePane.this.list != null && FilePane.this
/* 1835 */           .listSelectionModel.isSelectedIndex(i)) {
/*      */ 
/*      */ 
/*      */           
/* 1839 */           Rectangle rectangle = FilePane.this.list.getCellBounds(i, i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1846 */           MouseEvent mouseEvent = new MouseEvent(FilePane.this.list, param1MouseEvent.getID(), param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), rectangle.x + 1, rectangle.y + rectangle.height / 2, param1MouseEvent.getXOnScreen(), param1MouseEvent.getYOnScreen(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), param1MouseEvent.getButton());
/* 1847 */           AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 1848 */           mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/* 1849 */               .isCausedByTouchEvent(param1MouseEvent));
/* 1850 */           param1MouseEvent = mouseEvent;
/*      */         } 
/*      */       } else {
/*      */         return;
/*      */       } 
/*      */       
/* 1856 */       if (i >= 0 && SwingUtilities.isLeftMouseButton(param1MouseEvent)) {
/* 1857 */         JFileChooser jFileChooser = FilePane.this.getFileChooser();
/*      */ 
/*      */         
/* 1860 */         if (param1MouseEvent.getClickCount() == 1 && jComponent instanceof JList) {
/* 1861 */           if ((!jFileChooser.isMultiSelectionEnabled() || (jFileChooser.getSelectedFiles()).length <= 1) && i >= 0 && FilePane.this
/* 1862 */             .listSelectionModel.isSelectedIndex(i) && FilePane.this
/* 1863 */             .getEditIndex() == i && FilePane.this.editFile == null) {
/*      */             
/* 1865 */             FilePane.this.editFileName(i);
/*      */           }
/* 1867 */           else if (i >= 0) {
/* 1868 */             FilePane.this.setEditIndex(i);
/*      */           } else {
/* 1870 */             FilePane.this.resetEditIndex();
/*      */           }
/*      */         
/* 1873 */         } else if (param1MouseEvent.getClickCount() == 2) {
/*      */ 
/*      */           
/* 1876 */           FilePane.this.resetEditIndex();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1881 */       if (getDoubleClickListener() != null) {
/* 1882 */         getDoubleClickListener().mouseClicked(param1MouseEvent);
/*      */       }
/*      */     }
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 1887 */       JComponent jComponent = (JComponent)param1MouseEvent.getSource();
/* 1888 */       if (jComponent instanceof JTable) {
/* 1889 */         JTable jTable = (JTable)param1MouseEvent.getSource();
/*      */         
/* 1891 */         TransferHandler transferHandler1 = FilePane.this.getFileChooser().getTransferHandler();
/* 1892 */         TransferHandler transferHandler2 = jTable.getTransferHandler();
/* 1893 */         if (transferHandler1 != transferHandler2) {
/* 1894 */           jTable.setTransferHandler(transferHandler1);
/*      */         }
/*      */         
/* 1897 */         boolean bool = FilePane.this.getFileChooser().getDragEnabled();
/* 1898 */         if (bool != jTable.getDragEnabled()) {
/* 1899 */           jTable.setDragEnabled(bool);
/*      */         }
/* 1901 */       } else if (jComponent instanceof JList) {
/*      */         
/* 1903 */         if (getDoubleClickListener() != null) {
/* 1904 */           getDoubleClickListener().mouseEntered(param1MouseEvent);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 1910 */       if (param1MouseEvent.getSource() instanceof JList)
/*      */       {
/* 1912 */         if (getDoubleClickListener() != null) {
/* 1913 */           getDoubleClickListener().mouseExited(param1MouseEvent);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 1919 */       if (param1MouseEvent.getSource() instanceof JList)
/*      */       {
/* 1921 */         if (getDoubleClickListener() != null) {
/* 1922 */           getDoubleClickListener().mousePressed(param1MouseEvent);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 1928 */       if (param1MouseEvent.getSource() instanceof JList)
/*      */       {
/* 1930 */         if (getDoubleClickListener() != null) {
/* 1931 */           getDoubleClickListener().mouseReleased(param1MouseEvent);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private MouseListener getDoubleClickListener() {
/* 1938 */       if (this.doubleClickListener == null && FilePane.this.list != null) {
/* 1939 */         this
/* 1940 */           .doubleClickListener = FilePane.this.fileChooserUIAccessor.createDoubleClickListener(FilePane.this.list);
/*      */       }
/* 1942 */       return this.doubleClickListener;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isDirectorySelected() {
/* 1952 */     return this.fileChooserUIAccessor.isDirectorySelected();
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
/* 1963 */     return this.fileChooserUIAccessor.getDirectory();
/*      */   }
/*      */   
/*      */   private Component findChildComponent(Container paramContainer, Class paramClass) {
/* 1967 */     int i = paramContainer.getComponentCount();
/* 1968 */     for (byte b = 0; b < i; b++) {
/* 1969 */       Component component = paramContainer.getComponent(b);
/* 1970 */       if (paramClass.isInstance(component))
/* 1971 */         return component; 
/* 1972 */       if (component instanceof Container) {
/* 1973 */         Component component1 = findChildComponent((Container)component, paramClass);
/* 1974 */         if (component1 != null) {
/* 1975 */           return component1;
/*      */         }
/*      */       } 
/*      */     } 
/* 1979 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canWrite(File paramFile) {
/* 1984 */     if (!paramFile.exists()) {
/* 1985 */       return false;
/*      */     }
/*      */     
/*      */     try {
/* 1989 */       if (paramFile instanceof ShellFolder) {
/* 1990 */         return paramFile.canWrite();
/*      */       }
/* 1992 */       if (usesShellFolder(getFileChooser())) {
/*      */         try {
/* 1994 */           return ShellFolder.getShellFolder(paramFile).canWrite();
/* 1995 */         } catch (FileNotFoundException fileNotFoundException) {
/*      */           
/* 1997 */           return false;
/*      */         } 
/*      */       }
/*      */       
/* 2001 */       return paramFile.canWrite();
/*      */     
/*      */     }
/* 2004 */     catch (SecurityException securityException) {
/* 2005 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean usesShellFolder(JFileChooser paramJFileChooser) {
/* 2013 */     Boolean bool = (Boolean)paramJFileChooser.getClientProperty("FileChooser.useShellFolder");
/*      */     
/* 2015 */     return (bool == null) ? paramJFileChooser.getFileSystemView().equals(FileSystemView.getFileSystemView()) : bool
/* 2016 */       .booleanValue();
/*      */   }
/*      */   
/*      */   public static interface FileChooserUIAccessor {
/*      */     JFileChooser getFileChooser();
/*      */     
/*      */     BasicDirectoryModel getModel();
/*      */     
/*      */     JPanel createList();
/*      */     
/*      */     JPanel createDetailsView();
/*      */     
/*      */     boolean isDirectorySelected();
/*      */     
/*      */     File getDirectory();
/*      */     
/*      */     Action getApproveSelectionAction();
/*      */     
/*      */     Action getChangeToParentDirectoryAction();
/*      */     
/*      */     Action getNewFolderAction();
/*      */     
/*      */     MouseListener createDoubleClickListener(JList param1JList);
/*      */     
/*      */     ListSelectionListener createListSelectionListener();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/FilePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */