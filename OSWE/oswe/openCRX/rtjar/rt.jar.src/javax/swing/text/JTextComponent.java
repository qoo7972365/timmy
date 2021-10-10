/*      */ package javax.swing.text;
/*      */ 
/*      */ import com.sun.beans.util.Cache;
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Color;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.datatransfer.Clipboard;
/*      */ import java.awt.datatransfer.DataFlavor;
/*      */ import java.awt.datatransfer.StringSelection;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.datatransfer.UnsupportedFlavorException;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.InputMethodEvent;
/*      */ import java.awt.event.InputMethodListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.font.TextHitInfo;
/*      */ import java.awt.im.InputContext;
/*      */ import java.awt.im.InputMethodRequests;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterAbortException;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.beans.Transient;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.Writer;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.AttributedString;
/*      */ import java.text.BreakIterator;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleAction;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleEditableText;
/*      */ import javax.accessibility.AccessibleExtendedText;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.accessibility.AccessibleTextSequence;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*      */ import javax.print.attribute.PrintRequestAttributeSet;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.DropMode;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.Scrollable;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.TransferHandler;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.CaretEvent;
/*      */ import javax.swing.event.CaretListener;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.plaf.TextUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import sun.awt.AppContext;
/*      */ import sun.swing.PrintingStatus;
/*      */ import sun.swing.SwingAccessor;
/*      */ import sun.swing.SwingUtilities2;
/*      */ import sun.swing.text.TextComponentPrintable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class JTextComponent
/*      */   extends JComponent
/*      */   implements Scrollable, Accessible
/*      */ {
/*      */   public static final String FOCUS_ACCELERATOR_KEY = "focusAcceleratorKey";
/*      */   private Document model;
/*      */   private transient Caret caret;
/*      */   private NavigationFilter navigationFilter;
/*      */   private transient Highlighter highlighter;
/*      */   private transient Keymap keymap;
/*      */   private transient MutableCaretEvent caretEvent;
/*      */   private Color caretColor;
/*      */   private Color selectionColor;
/*      */   private Color selectedTextColor;
/*      */   private Color disabledTextColor;
/*      */   private boolean editable;
/*      */   private Insets margin;
/*      */   private char focusAccelerator;
/*      */   private boolean dragEnabled;
/*      */   private DropMode dropMode;
/*      */   private transient DropLocation dropLocation;
/*      */   private static DefaultTransferHandler defaultTransferHandler;
/*      */   
/*      */   public TextUI getUI() {
/*      */     return (TextUI)this.ui;
/*      */   }
/*      */   
/*      */   public void setUI(TextUI paramTextUI) {
/*      */     setUI(paramTextUI);
/*      */   }
/*      */   
/*      */   public void updateUI() {
/*      */     setUI((TextUI)UIManager.getUI(this));
/*      */     invalidate();
/*      */   }
/*      */   
/*      */   public void addCaretListener(CaretListener paramCaretListener) {
/*      */     this.listenerList.add(CaretListener.class, paramCaretListener);
/*      */   }
/*      */   
/*      */   public void removeCaretListener(CaretListener paramCaretListener) {
/*      */     this.listenerList.remove(CaretListener.class, paramCaretListener);
/*      */   }
/*      */   
/*      */   public CaretListener[] getCaretListeners() {
/*      */     return this.listenerList.<CaretListener>getListeners(CaretListener.class);
/*      */   }
/*      */   
/*      */   protected void fireCaretUpdate(CaretEvent paramCaretEvent) {
/*      */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*      */       if (arrayOfObject[i] == CaretListener.class)
/*      */         ((CaretListener)arrayOfObject[i + 1]).caretUpdate(paramCaretEvent); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setDocument(Document paramDocument) {
/*      */     Document document = this.model;
/*      */     try {
/*      */       if (document instanceof AbstractDocument)
/*      */         ((AbstractDocument)document).readLock(); 
/*      */       if (this.accessibleContext != null)
/*      */         this.model.removeDocumentListener((AccessibleJTextComponent)this.accessibleContext); 
/*      */       if (this.inputMethodRequestsHandler != null)
/*      */         this.model.removeDocumentListener((DocumentListener)this.inputMethodRequestsHandler); 
/*      */       this.model = paramDocument;
/*      */       Boolean bool = getComponentOrientation().isLeftToRight() ? TextAttribute.RUN_DIRECTION_LTR : TextAttribute.RUN_DIRECTION_RTL;
/*      */       if (bool != paramDocument.getProperty(TextAttribute.RUN_DIRECTION))
/*      */         paramDocument.putProperty(TextAttribute.RUN_DIRECTION, bool); 
/*      */       firePropertyChange("document", document, paramDocument);
/*      */     } finally {
/*      */       if (document instanceof AbstractDocument)
/*      */         ((AbstractDocument)document).readUnlock(); 
/*      */     } 
/*      */     revalidate();
/*      */     repaint();
/*      */     if (this.accessibleContext != null)
/*      */       this.model.addDocumentListener((AccessibleJTextComponent)this.accessibleContext); 
/*      */     if (this.inputMethodRequestsHandler != null)
/*      */       this.model.addDocumentListener((DocumentListener)this.inputMethodRequestsHandler); 
/*      */   }
/*      */   
/*      */   public Document getDocument() {
/*      */     return this.model;
/*      */   }
/*      */   
/*      */   public void setComponentOrientation(ComponentOrientation paramComponentOrientation) {
/*      */     Document document = getDocument();
/*      */     if (document != null) {
/*      */       Boolean bool = paramComponentOrientation.isLeftToRight() ? TextAttribute.RUN_DIRECTION_LTR : TextAttribute.RUN_DIRECTION_RTL;
/*      */       document.putProperty(TextAttribute.RUN_DIRECTION, bool);
/*      */     } 
/*      */     super.setComponentOrientation(paramComponentOrientation);
/*      */   }
/*      */   
/*      */   public Action[] getActions() {
/*      */     return getUI().getEditorKit(this).getActions();
/*      */   }
/*      */   
/*      */   public void setMargin(Insets paramInsets) {
/*      */     Insets insets = this.margin;
/*      */     this.margin = paramInsets;
/*      */     firePropertyChange("margin", insets, paramInsets);
/*      */     invalidate();
/*      */   }
/*      */   
/*      */   public Insets getMargin() {
/*      */     return this.margin;
/*      */   }
/*      */   
/*      */   public void setNavigationFilter(NavigationFilter paramNavigationFilter) {
/*      */     this.navigationFilter = paramNavigationFilter;
/*      */   }
/*      */   
/*      */   public NavigationFilter getNavigationFilter() {
/*      */     return this.navigationFilter;
/*      */   }
/*      */   
/*      */   @Transient
/*      */   public Caret getCaret() {
/*      */     return this.caret;
/*      */   }
/*      */   
/*      */   public void setCaret(Caret paramCaret) {
/*      */     if (this.caret != null) {
/*      */       this.caret.removeChangeListener(this.caretEvent);
/*      */       this.caret.deinstall(this);
/*      */     } 
/*      */     Caret caret = this.caret;
/*      */     this.caret = paramCaret;
/*      */     if (this.caret != null) {
/*      */       this.caret.install(this);
/*      */       this.caret.addChangeListener(this.caretEvent);
/*      */     } 
/*      */     firePropertyChange("caret", caret, this.caret);
/*      */   }
/*      */   
/*      */   public Highlighter getHighlighter() {
/*      */     return this.highlighter;
/*      */   }
/*      */   
/*      */   public void setHighlighter(Highlighter paramHighlighter) {
/*      */     if (this.highlighter != null)
/*      */       this.highlighter.deinstall(this); 
/*      */     Highlighter highlighter = this.highlighter;
/*      */     this.highlighter = paramHighlighter;
/*      */     if (this.highlighter != null)
/*      */       this.highlighter.install(this); 
/*      */     firePropertyChange("highlighter", highlighter, paramHighlighter);
/*      */   }
/*      */   
/*      */   public void setKeymap(Keymap paramKeymap) {
/*      */     Keymap keymap = this.keymap;
/*      */     this.keymap = paramKeymap;
/*      */     firePropertyChange("keymap", keymap, this.keymap);
/*      */     updateInputMap(keymap, paramKeymap);
/*      */   }
/*      */   
/*      */   public void setDragEnabled(boolean paramBoolean) {
/*      */     if (paramBoolean && GraphicsEnvironment.isHeadless())
/*      */       throw new HeadlessException(); 
/*      */     this.dragEnabled = paramBoolean;
/*      */   }
/*      */   
/*      */   public boolean getDragEnabled() {
/*      */     return this.dragEnabled;
/*      */   }
/*      */   
/*      */   public final void setDropMode(DropMode paramDropMode) {
/*      */     if (paramDropMode != null)
/*      */       switch (paramDropMode) {
/*      */         case USE_SELECTION:
/*      */         case INSERT:
/*      */           this.dropMode = paramDropMode;
/*      */           return;
/*      */       }  
/*      */     throw new IllegalArgumentException(paramDropMode + ": Unsupported drop mode for text");
/*      */   }
/*      */   
/*      */   public final DropMode getDropMode() {
/*      */     return this.dropMode;
/*      */   }
/*      */   
/*      */   static {
/*      */     SwingAccessor.setJTextComponentAccessor(new SwingAccessor.JTextComponentAccessor()
/*      */         {
/*      */           public TransferHandler.DropLocation dropLocationForPoint(JTextComponent param1JTextComponent, Point param1Point) {
/*      */             return param1JTextComponent.dropLocationForPoint(param1Point);
/*      */           }
/*      */           
/*      */           public Object setDropLocation(JTextComponent param1JTextComponent, TransferHandler.DropLocation param1DropLocation, Object param1Object, boolean param1Boolean) {
/*      */             return param1JTextComponent.setDropLocation(param1DropLocation, param1Object, param1Boolean);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   DropLocation dropLocationForPoint(Point paramPoint) {
/*      */     Position.Bias[] arrayOfBias = new Position.Bias[1];
/*      */     int i = getUI().viewToModel(this, paramPoint, arrayOfBias);
/*      */     if (arrayOfBias[0] == null)
/*      */       arrayOfBias[0] = Position.Bias.Forward; 
/*      */     return new DropLocation(paramPoint, i, arrayOfBias[0]);
/*      */   }
/*      */   
/*      */   Object setDropLocation(TransferHandler.DropLocation paramDropLocation, Object paramObject, boolean paramBoolean) {
/*      */     Object object = null;
/*      */     DropLocation dropLocation1 = (DropLocation)paramDropLocation;
/*      */     if (this.dropMode == DropMode.USE_SELECTION) {
/*      */       if (dropLocation1 == null) {
/*      */         if (paramObject != null) {
/*      */           Object[] arrayOfObject = (Object[])paramObject;
/*      */           if (!paramBoolean)
/*      */             if (this.caret instanceof DefaultCaret) {
/*      */               ((DefaultCaret)this.caret).setDot(((Integer)arrayOfObject[0]).intValue(), (Position.Bias)arrayOfObject[3]);
/*      */               ((DefaultCaret)this.caret).moveDot(((Integer)arrayOfObject[1]).intValue(), (Position.Bias)arrayOfObject[4]);
/*      */             } else {
/*      */               this.caret.setDot(((Integer)arrayOfObject[0]).intValue());
/*      */               this.caret.moveDot(((Integer)arrayOfObject[1]).intValue());
/*      */             }  
/*      */           this.caret.setVisible(((Boolean)arrayOfObject[2]).booleanValue());
/*      */         } 
/*      */       } else {
/*      */         if (this.dropLocation == null) {
/*      */           if (this.caret instanceof DefaultCaret) {
/*      */             DefaultCaret defaultCaret = (DefaultCaret)this.caret;
/*      */             boolean bool = defaultCaret.isActive();
/*      */             object = new Object[] { Integer.valueOf(defaultCaret.getMark()), Integer.valueOf(defaultCaret.getDot()), Boolean.valueOf(bool), defaultCaret.getMarkBias(), defaultCaret.getDotBias() };
/*      */           } else {
/*      */             boolean bool = this.caret.isVisible();
/*      */             object = new Object[] { Integer.valueOf(this.caret.getMark()), Integer.valueOf(this.caret.getDot()), Boolean.valueOf(bool) };
/*      */           } 
/*      */           this.caret.setVisible(true);
/*      */         } else {
/*      */           Object object1 = paramObject;
/*      */         } 
/*      */         if (this.caret instanceof DefaultCaret) {
/*      */           ((DefaultCaret)this.caret).setDot(dropLocation1.getIndex(), dropLocation1.getBias());
/*      */         } else {
/*      */           this.caret.setDot(dropLocation1.getIndex());
/*      */         } 
/*      */       } 
/*      */     } else if (dropLocation1 == null) {
/*      */       if (paramObject != null)
/*      */         this.caret.setVisible(((Boolean)paramObject).booleanValue()); 
/*      */     } else if (this.dropLocation == null) {
/*      */       boolean bool1 = (this.caret instanceof DefaultCaret) ? ((DefaultCaret)this.caret).isActive() : this.caret.isVisible();
/*      */       Boolean bool = Boolean.valueOf(bool1);
/*      */       this.caret.setVisible(false);
/*      */     } else {
/*      */       object = paramObject;
/*      */     } 
/*      */     DropLocation dropLocation2 = this.dropLocation;
/*      */     this.dropLocation = dropLocation1;
/*      */     firePropertyChange("dropLocation", dropLocation2, this.dropLocation);
/*      */     return object;
/*      */   }
/*      */   
/*      */   public final DropLocation getDropLocation() {
/*      */     return this.dropLocation;
/*      */   }
/*      */   
/*      */   void updateInputMap(Keymap paramKeymap1, Keymap paramKeymap2) {
/*      */     InputMap inputMap1 = getInputMap(0);
/*      */     InputMap inputMap2 = inputMap1;
/*      */     while (inputMap1 != null && !(inputMap1 instanceof KeymapWrapper)) {
/*      */       inputMap2 = inputMap1;
/*      */       inputMap1 = inputMap1.getParent();
/*      */     } 
/*      */     if (inputMap1 != null) {
/*      */       if (paramKeymap2 == null) {
/*      */         if (inputMap2 != inputMap1) {
/*      */           inputMap2.setParent(inputMap1.getParent());
/*      */         } else {
/*      */           inputMap2.setParent(null);
/*      */         } 
/*      */       } else {
/*      */         KeymapWrapper keymapWrapper = new KeymapWrapper(paramKeymap2);
/*      */         inputMap2.setParent(keymapWrapper);
/*      */         if (inputMap2 != inputMap1)
/*      */           keymapWrapper.setParent(inputMap1.getParent()); 
/*      */       } 
/*      */     } else if (paramKeymap2 != null) {
/*      */       inputMap1 = getInputMap(0);
/*      */       if (inputMap1 != null) {
/*      */         KeymapWrapper keymapWrapper = new KeymapWrapper(paramKeymap2);
/*      */         keymapWrapper.setParent(inputMap1.getParent());
/*      */         inputMap1.setParent(keymapWrapper);
/*      */       } 
/*      */     } 
/*      */     ActionMap actionMap1 = getActionMap();
/*      */     ActionMap actionMap2 = actionMap1;
/*      */     while (actionMap1 != null && !(actionMap1 instanceof KeymapActionMap)) {
/*      */       actionMap2 = actionMap1;
/*      */       actionMap1 = actionMap1.getParent();
/*      */     } 
/*      */     if (actionMap1 != null) {
/*      */       if (paramKeymap2 == null) {
/*      */         if (actionMap2 != actionMap1) {
/*      */           actionMap2.setParent(actionMap1.getParent());
/*      */         } else {
/*      */           actionMap2.setParent(null);
/*      */         } 
/*      */       } else {
/*      */         KeymapActionMap keymapActionMap = new KeymapActionMap(paramKeymap2);
/*      */         actionMap2.setParent(keymapActionMap);
/*      */         if (actionMap2 != actionMap1)
/*      */           keymapActionMap.setParent(actionMap1.getParent()); 
/*      */       } 
/*      */     } else if (paramKeymap2 != null) {
/*      */       actionMap1 = getActionMap();
/*      */       if (actionMap1 != null) {
/*      */         KeymapActionMap keymapActionMap = new KeymapActionMap(paramKeymap2);
/*      */         keymapActionMap.setParent(actionMap1.getParent());
/*      */         actionMap1.setParent(keymapActionMap);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Keymap getKeymap() {
/*      */     return this.keymap;
/*      */   }
/*      */   
/*      */   public static Keymap addKeymap(String paramString, Keymap paramKeymap) {
/*      */     DefaultKeymap defaultKeymap = new DefaultKeymap(paramString, paramKeymap);
/*      */     if (paramString != null)
/*      */       getKeymapTable().put(paramString, defaultKeymap); 
/*      */     return defaultKeymap;
/*      */   }
/*      */   
/*      */   public static Keymap removeKeymap(String paramString) {
/*      */     return getKeymapTable().remove(paramString);
/*      */   }
/*      */   
/*      */   public static Keymap getKeymap(String paramString) {
/*      */     return getKeymapTable().get(paramString);
/*      */   }
/*      */   
/*      */   private static HashMap<String, Keymap> getKeymapTable() {
/*      */     synchronized (KEYMAP_TABLE) {
/*      */       AppContext appContext = AppContext.getAppContext();
/*      */       HashMap<Object, Object> hashMap = (HashMap)appContext.get(KEYMAP_TABLE);
/*      */       if (hashMap == null) {
/*      */         hashMap = new HashMap<>(17);
/*      */         appContext.put(KEYMAP_TABLE, hashMap);
/*      */         Keymap keymap = addKeymap("default", (Keymap)null);
/*      */         keymap.setDefaultAction(new DefaultEditorKit.DefaultKeyTypedAction());
/*      */       } 
/*      */       return (HashMap)hashMap;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static class KeyBinding
/*      */   {
/*      */     public KeyStroke key;
/*      */     public String actionName;
/*      */     
/*      */     public KeyBinding(KeyStroke param1KeyStroke, String param1String) {
/*      */       this.key = param1KeyStroke;
/*      */       this.actionName = param1String;
/*      */     }
/*      */   }
/*      */   
/*      */   public static void loadKeymap(Keymap paramKeymap, KeyBinding[] paramArrayOfKeyBinding, Action[] paramArrayOfAction) {
/*      */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     for (Action action : paramArrayOfAction) {
/*      */       String str = (String)action.getValue("Name");
/*      */       hashtable.put((str != null) ? str : "", action);
/*      */     } 
/*      */     for (KeyBinding keyBinding : paramArrayOfKeyBinding) {
/*      */       Action action = (Action)hashtable.get(keyBinding.actionName);
/*      */       if (action != null)
/*      */         paramKeymap.addActionForKeyStroke(keyBinding.key, action); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Color getCaretColor() {
/*      */     return this.caretColor;
/*      */   }
/*      */   
/*      */   public void setCaretColor(Color paramColor) {
/*      */     Color color = this.caretColor;
/*      */     this.caretColor = paramColor;
/*      */     firePropertyChange("caretColor", color, this.caretColor);
/*      */   }
/*      */   
/*      */   public Color getSelectionColor() {
/*      */     return this.selectionColor;
/*      */   }
/*      */   
/*      */   public void setSelectionColor(Color paramColor) {
/*      */     Color color = this.selectionColor;
/*      */     this.selectionColor = paramColor;
/*      */     firePropertyChange("selectionColor", color, this.selectionColor);
/*      */   }
/*      */   
/*      */   public Color getSelectedTextColor() {
/*      */     return this.selectedTextColor;
/*      */   }
/*      */   
/*      */   public void setSelectedTextColor(Color paramColor) {
/*      */     Color color = this.selectedTextColor;
/*      */     this.selectedTextColor = paramColor;
/*      */     firePropertyChange("selectedTextColor", color, this.selectedTextColor);
/*      */   }
/*      */   
/*      */   public Color getDisabledTextColor() {
/*      */     return this.disabledTextColor;
/*      */   }
/*      */   
/*      */   public void setDisabledTextColor(Color paramColor) {
/*      */     Color color = this.disabledTextColor;
/*      */     this.disabledTextColor = paramColor;
/*      */     firePropertyChange("disabledTextColor", color, this.disabledTextColor);
/*      */   }
/*      */   
/*      */   public void replaceSelection(String paramString) {
/*      */     Document document = getDocument();
/*      */     if (document != null)
/*      */       try {
/*      */         boolean bool = saveComposedText(this.caret.getDot());
/*      */         int i = Math.min(this.caret.getDot(), this.caret.getMark());
/*      */         int j = Math.max(this.caret.getDot(), this.caret.getMark());
/*      */         if (document instanceof AbstractDocument) {
/*      */           ((AbstractDocument)document).replace(i, j - i, paramString, null);
/*      */         } else {
/*      */           if (i != j)
/*      */             document.remove(i, j - i); 
/*      */           if (paramString != null && paramString.length() > 0)
/*      */             document.insertString(i, paramString, null); 
/*      */         } 
/*      */         if (bool)
/*      */           restoreComposedText(); 
/*      */       } catch (BadLocationException badLocationException) {
/*      */         UIManager.getLookAndFeel().provideErrorFeedback(this);
/*      */       }  
/*      */   }
/*      */   
/*      */   public String getText(int paramInt1, int paramInt2) throws BadLocationException {
/*      */     return getDocument().getText(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public Rectangle modelToView(int paramInt) throws BadLocationException {
/*      */     return getUI().modelToView(this, paramInt);
/*      */   }
/*      */   
/*      */   public int viewToModel(Point paramPoint) {
/*      */     return getUI().viewToModel(this, paramPoint);
/*      */   }
/*      */   
/*      */   public void cut() {
/*      */     if (isEditable() && isEnabled())
/*      */       invokeAction("cut", TransferHandler.getCutAction()); 
/*      */   }
/*      */   
/*      */   public void copy() {
/*      */     invokeAction("copy", TransferHandler.getCopyAction());
/*      */   }
/*      */   
/*      */   public void paste() {
/*      */     if (isEditable() && isEnabled())
/*      */       invokeAction("paste", TransferHandler.getPasteAction()); 
/*      */   }
/*      */   
/*      */   private void invokeAction(String paramString, Action paramAction) {
/*      */     ActionMap actionMap = getActionMap();
/*      */     Action action = null;
/*      */     if (actionMap != null)
/*      */       action = actionMap.get(paramString); 
/*      */     if (action == null) {
/*      */       installDefaultTransferHandlerIfNecessary();
/*      */       action = paramAction;
/*      */     } 
/*      */     action.actionPerformed(new ActionEvent(this, 1001, (String)action.getValue("Name"), EventQueue.getMostRecentEventTime(), getCurrentEventModifiers()));
/*      */   }
/*      */   
/*      */   private void installDefaultTransferHandlerIfNecessary() {
/*      */     if (getTransferHandler() == null) {
/*      */       if (defaultTransferHandler == null)
/*      */         defaultTransferHandler = new DefaultTransferHandler(); 
/*      */       setTransferHandler(defaultTransferHandler);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void moveCaretPosition(int paramInt) {
/*      */     Document document = getDocument();
/*      */     if (document != null) {
/*      */       if (paramInt > document.getLength() || paramInt < 0)
/*      */         throw new IllegalArgumentException("bad position: " + paramInt); 
/*      */       this.caret.moveDot(paramInt);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setFocusAccelerator(char paramChar) {
/*      */     paramChar = Character.toUpperCase(paramChar);
/*      */     char c = this.focusAccelerator;
/*      */     this.focusAccelerator = paramChar;
/*      */     firePropertyChange("focusAcceleratorKey", c, this.focusAccelerator);
/*      */     firePropertyChange("focusAccelerator", c, this.focusAccelerator);
/*      */   }
/*      */   
/*      */   public char getFocusAccelerator() {
/*      */     return this.focusAccelerator;
/*      */   }
/*      */   
/*      */   public void read(Reader paramReader, Object paramObject) throws IOException {
/*      */     EditorKit editorKit = getUI().getEditorKit(this);
/*      */     Document document = editorKit.createDefaultDocument();
/*      */     if (paramObject != null)
/*      */       document.putProperty("stream", paramObject); 
/*      */     try {
/*      */       editorKit.read(paramReader, document, 0);
/*      */       setDocument(document);
/*      */     } catch (BadLocationException badLocationException) {
/*      */       throw new IOException(badLocationException.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void write(Writer paramWriter) throws IOException {
/*      */     Document document = getDocument();
/*      */     try {
/*      */       getUI().getEditorKit(this).write(paramWriter, document, 0, document.getLength());
/*      */     } catch (BadLocationException badLocationException) {
/*      */       throw new IOException(badLocationException.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeNotify() {
/*      */     super.removeNotify();
/*      */     if (getFocusedComponent() == this)
/*      */       AppContext.getAppContext().remove(FOCUSED_COMPONENT); 
/*      */   }
/*      */   
/*      */   public void setCaretPosition(int paramInt) {
/*      */     Document document = getDocument();
/*      */     if (document != null) {
/*      */       if (paramInt > document.getLength() || paramInt < 0)
/*      */         throw new IllegalArgumentException("bad position: " + paramInt); 
/*      */       this.caret.setDot(paramInt);
/*      */     } 
/*      */   }
/*      */   
/*      */   @Transient
/*      */   public int getCaretPosition() {
/*      */     return this.caret.getDot();
/*      */   }
/*      */   
/*      */   public void setText(String paramString) {
/*      */     try {
/*      */       Document document = getDocument();
/*      */       if (document instanceof AbstractDocument) {
/*      */         ((AbstractDocument)document).replace(0, document.getLength(), paramString, null);
/*      */       } else {
/*      */         document.remove(0, document.getLength());
/*      */         document.insertString(0, paramString, null);
/*      */       } 
/*      */     } catch (BadLocationException badLocationException) {
/*      */       UIManager.getLookAndFeel().provideErrorFeedback(this);
/*      */     } 
/*      */   }
/*      */   
/*      */   public String getText() {
/*      */     String str;
/*      */     Document document = getDocument();
/*      */     try {
/*      */       str = document.getText(0, document.getLength());
/*      */     } catch (BadLocationException badLocationException) {
/*      */       str = null;
/*      */     } 
/*      */     return str;
/*      */   }
/*      */   
/*      */   public String getSelectedText() {
/*      */     String str = null;
/*      */     int i = Math.min(this.caret.getDot(), this.caret.getMark());
/*      */     int j = Math.max(this.caret.getDot(), this.caret.getMark());
/*      */     if (i != j)
/*      */       try {
/*      */         Document document = getDocument();
/*      */         str = document.getText(i, j - i);
/*      */       } catch (BadLocationException badLocationException) {
/*      */         throw new IllegalArgumentException(badLocationException.getMessage());
/*      */       }  
/*      */     return str;
/*      */   }
/*      */   
/*      */   public boolean isEditable() {
/*      */     return this.editable;
/*      */   }
/*      */   
/*      */   public void setEditable(boolean paramBoolean) {
/*      */     if (paramBoolean != this.editable) {
/*      */       boolean bool = this.editable;
/*      */       this.editable = paramBoolean;
/*      */       enableInputMethods(this.editable);
/*      */       firePropertyChange("editable", Boolean.valueOf(bool), Boolean.valueOf(this.editable));
/*      */       repaint();
/*      */     } 
/*      */   }
/*      */   
/*      */   @Transient
/*      */   public int getSelectionStart() {
/*      */     return Math.min(this.caret.getDot(), this.caret.getMark());
/*      */   }
/*      */   
/*      */   public void setSelectionStart(int paramInt) {
/*      */     select(paramInt, getSelectionEnd());
/*      */   }
/*      */   
/*      */   @Transient
/*      */   public int getSelectionEnd() {
/*      */     return Math.max(this.caret.getDot(), this.caret.getMark());
/*      */   }
/*      */   
/*      */   public void setSelectionEnd(int paramInt) {
/*      */     select(getSelectionStart(), paramInt);
/*      */   }
/*      */   
/*      */   public void select(int paramInt1, int paramInt2) {
/*      */     int i = getDocument().getLength();
/*      */     if (paramInt1 < 0)
/*      */       paramInt1 = 0; 
/*      */     if (paramInt1 > i)
/*      */       paramInt1 = i; 
/*      */     if (paramInt2 > i)
/*      */       paramInt2 = i; 
/*      */     if (paramInt2 < paramInt1)
/*      */       paramInt2 = paramInt1; 
/*      */     setCaretPosition(paramInt1);
/*      */     moveCaretPosition(paramInt2);
/*      */   }
/*      */   
/*      */   public void selectAll() {
/*      */     Document document = getDocument();
/*      */     if (document != null) {
/*      */       setCaretPosition(0);
/*      */       moveCaretPosition(document.getLength());
/*      */     } 
/*      */   }
/*      */   
/*      */   public String getToolTipText(MouseEvent paramMouseEvent) {
/*      */     String str = super.getToolTipText(paramMouseEvent);
/*      */     if (str == null) {
/*      */       TextUI textUI = getUI();
/*      */       if (textUI != null)
/*      */         str = textUI.getToolTipText(this, new Point(paramMouseEvent.getX(), paramMouseEvent.getY())); 
/*      */     } 
/*      */     return str;
/*      */   }
/*      */   
/*      */   public Dimension getPreferredScrollableViewportSize() {
/*      */     return getPreferredSize();
/*      */   }
/*      */   
/*      */   public int getScrollableUnitIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/*      */     switch (paramInt1) {
/*      */       case 1:
/*      */         return paramRectangle.height / 10;
/*      */       case 0:
/*      */         return paramRectangle.width / 10;
/*      */     } 
/*      */     throw new IllegalArgumentException("Invalid orientation: " + paramInt1);
/*      */   }
/*      */   
/*      */   public int getScrollableBlockIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/*      */     switch (paramInt1) {
/*      */       case 1:
/*      */         return paramRectangle.height;
/*      */       case 0:
/*      */         return paramRectangle.width;
/*      */     } 
/*      */     throw new IllegalArgumentException("Invalid orientation: " + paramInt1);
/*      */   }
/*      */   
/*      */   public boolean getScrollableTracksViewportWidth() {
/*      */     Container container = SwingUtilities.getUnwrappedParent(this);
/*      */     if (container instanceof javax.swing.JViewport)
/*      */       return (container.getWidth() > (getPreferredSize()).width); 
/*      */     return false;
/*      */   }
/*      */   
/*      */   public boolean getScrollableTracksViewportHeight() {
/*      */     Container container = SwingUtilities.getUnwrappedParent(this);
/*      */     if (container instanceof javax.swing.JViewport)
/*      */       return (container.getHeight() > (getPreferredSize()).height); 
/*      */     return false;
/*      */   }
/*      */   
/*      */   public boolean print() throws PrinterException {
/*      */     return print((MessageFormat)null, (MessageFormat)null, true, (PrintService)null, (PrintRequestAttributeSet)null, true);
/*      */   }
/*      */   
/*      */   public boolean print(MessageFormat paramMessageFormat1, MessageFormat paramMessageFormat2) throws PrinterException {
/*      */     return print(paramMessageFormat1, paramMessageFormat2, true, (PrintService)null, (PrintRequestAttributeSet)null, true);
/*      */   }
/*      */   
/*      */   public boolean print(MessageFormat paramMessageFormat1, MessageFormat paramMessageFormat2, boolean paramBoolean1, PrintService paramPrintService, PrintRequestAttributeSet paramPrintRequestAttributeSet, boolean paramBoolean2) throws PrinterException {
/*      */     Printable printable1;
/*      */     final PrintingStatus printingStatus;
/*      */     final PrinterJob job = PrinterJob.getPrinterJob();
/*      */     boolean bool1 = GraphicsEnvironment.isHeadless();
/*      */     final boolean isEventDispatchThread = SwingUtilities.isEventDispatchThread();
/*      */     Printable printable2 = getPrintable(paramMessageFormat1, paramMessageFormat2);
/*      */     if (paramBoolean2 && !bool1) {
/*      */       printingStatus = PrintingStatus.createPrintingStatus(this, printerJob);
/*      */       printable1 = printingStatus.createNotificationPrintable(printable2);
/*      */     } else {
/*      */       printingStatus = null;
/*      */       printable1 = printable2;
/*      */     } 
/*      */     if (paramPrintService != null)
/*      */       printerJob.setPrintService(paramPrintService); 
/*      */     printerJob.setPrintable(printable1);
/*      */     final PrintRequestAttributeSet attr = (paramPrintRequestAttributeSet == null) ? new HashPrintRequestAttributeSet() : paramPrintRequestAttributeSet;
/*      */     if (paramBoolean1 && !bool1 && !printerJob.printDialog(printRequestAttributeSet))
/*      */       return false; 
/*      */     Callable<Object> callable = new Callable()
/*      */       {
/*      */         public Object call() throws Exception {
/*      */           try {
/*      */             job.print(attr);
/*      */           } finally {
/*      */             if (printingStatus != null)
/*      */               printingStatus.dispose(); 
/*      */           } 
/*      */           return null;
/*      */         }
/*      */       };
/*      */     final FutureTask futurePrinting = new FutureTask(callable);
/*      */     Runnable runnable = new Runnable()
/*      */       {
/*      */         public void run() {
/*      */           boolean bool = false;
/*      */           if (isEventDispatchThread) {
/*      */             if (JTextComponent.this.isEnabled()) {
/*      */               bool = true;
/*      */               JTextComponent.this.setEnabled(false);
/*      */             } 
/*      */           } else {
/*      */             try {
/*      */               bool = ((Boolean)SwingUtilities2.<Boolean>submit(new Callable<Boolean>()
/*      */                   {
/*      */                     public Boolean call() throws Exception {
/*      */                       boolean bool = JTextComponent.this.isEnabled();
/*      */                       if (bool)
/*      */                         JTextComponent.this.setEnabled(false); 
/*      */                       return Boolean.valueOf(bool);
/*      */                     }
/*      */                   }).get()).booleanValue();
/*      */             } catch (InterruptedException interruptedException) {
/*      */               throw new RuntimeException(interruptedException);
/*      */             } catch (ExecutionException executionException) {
/*      */               Throwable throwable = executionException.getCause();
/*      */               if (throwable instanceof Error)
/*      */                 throw (Error)throwable; 
/*      */               if (throwable instanceof RuntimeException)
/*      */                 throw (RuntimeException)throwable; 
/*      */               throw new AssertionError(throwable);
/*      */             } 
/*      */           } 
/*      */           JTextComponent.this.getDocument().render(futurePrinting);
/*      */           if (bool)
/*      */             if (isEventDispatchThread) {
/*      */               JTextComponent.this.setEnabled(true);
/*      */             } else {
/*      */               try {
/*      */                 SwingUtilities2.submit(new Runnable()
/*      */                     {
/*      */                       public void run() {
/*      */                         JTextComponent.this.setEnabled(true);
/*      */                       }
/*      */                     },  null).get();
/*      */               } catch (InterruptedException interruptedException) {
/*      */                 throw new RuntimeException(interruptedException);
/*      */               } catch (ExecutionException executionException) {
/*      */                 Throwable throwable = executionException.getCause();
/*      */                 if (throwable instanceof Error)
/*      */                   throw (Error)throwable; 
/*      */                 if (throwable instanceof RuntimeException)
/*      */                   throw (RuntimeException)throwable; 
/*      */                 throw new AssertionError(throwable);
/*      */               } 
/*      */             }  
/*      */         }
/*      */       };
/*      */     if (!paramBoolean2 || bool1) {
/*      */       runnable.run();
/*      */     } else if (bool2) {
/*      */       (new Thread(runnable)).start();
/*      */       printingStatus.showModal(true);
/*      */     } else {
/*      */       printingStatus.showModal(false);
/*      */       runnable.run();
/*      */     } 
/*      */     try {
/*      */       futureTask.get();
/*      */     } catch (InterruptedException interruptedException) {
/*      */       throw new RuntimeException(interruptedException);
/*      */     } catch (ExecutionException executionException) {
/*      */       Throwable throwable = executionException.getCause();
/*      */       if (throwable instanceof PrinterAbortException) {
/*      */         if (printingStatus != null && printingStatus.isAborted())
/*      */           return false; 
/*      */         throw (PrinterAbortException)throwable;
/*      */       } 
/*      */       if (throwable instanceof PrinterException)
/*      */         throw (PrinterException)throwable; 
/*      */       if (throwable instanceof RuntimeException)
/*      */         throw (RuntimeException)throwable; 
/*      */       if (throwable instanceof Error)
/*      */         throw (Error)throwable; 
/*      */       throw new AssertionError(throwable);
/*      */     } 
/*      */     return true;
/*      */   }
/*      */   
/*      */   public Printable getPrintable(MessageFormat paramMessageFormat1, MessageFormat paramMessageFormat2) {
/*      */     return TextComponentPrintable.getPrintable(this, paramMessageFormat1, paramMessageFormat2);
/*      */   }
/*      */   
/*      */   public AccessibleContext getAccessibleContext() {
/*      */     if (this.accessibleContext == null)
/*      */       this.accessibleContext = new AccessibleJTextComponent(); 
/*      */     return this.accessibleContext;
/*      */   }
/*      */   
/*      */   public class AccessibleJTextComponent
/*      */     extends JComponent.AccessibleJComponent
/*      */     implements AccessibleText, CaretListener, DocumentListener, AccessibleAction, AccessibleEditableText, AccessibleExtendedText
/*      */   {
/*      */     int caretPos;
/*      */     Point oldLocationOnScreen;
/*      */     
/*      */     public AccessibleJTextComponent() {
/*      */       Document document = JTextComponent.this.getDocument();
/*      */       if (document != null)
/*      */         document.addDocumentListener(this); 
/*      */       JTextComponent.this.addCaretListener(this);
/*      */       this.caretPos = getCaretPosition();
/*      */       try {
/*      */         this.oldLocationOnScreen = getLocationOnScreen();
/*      */       } catch (IllegalComponentStateException illegalComponentStateException) {}
/*      */       JTextComponent.this.addComponentListener(new ComponentAdapter()
/*      */           {
/*      */             public void componentMoved(ComponentEvent param2ComponentEvent) {
/*      */               try {
/*      */                 Point point = JTextComponent.AccessibleJTextComponent.this.getLocationOnScreen();
/*      */                 JTextComponent.AccessibleJTextComponent.this.firePropertyChange("AccessibleVisibleData", JTextComponent.AccessibleJTextComponent.this.oldLocationOnScreen, point);
/*      */                 JTextComponent.AccessibleJTextComponent.this.oldLocationOnScreen = point;
/*      */               } catch (IllegalComponentStateException illegalComponentStateException) {}
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     public void caretUpdate(CaretEvent param1CaretEvent) {
/*      */       int i = param1CaretEvent.getDot();
/*      */       int j = param1CaretEvent.getMark();
/*      */       if (this.caretPos != i) {
/*      */         firePropertyChange("AccessibleCaret", new Integer(this.caretPos), new Integer(i));
/*      */         this.caretPos = i;
/*      */         try {
/*      */           this.oldLocationOnScreen = getLocationOnScreen();
/*      */         } catch (IllegalComponentStateException illegalComponentStateException) {}
/*      */       } 
/*      */       if (j != i)
/*      */         firePropertyChange("AccessibleSelection", null, getSelectedText()); 
/*      */     }
/*      */     
/*      */     public void insertUpdate(DocumentEvent param1DocumentEvent) {
/*      */       final Integer pos = new Integer(param1DocumentEvent.getOffset());
/*      */       if (SwingUtilities.isEventDispatchThread()) {
/*      */         firePropertyChange("AccessibleText", null, integer);
/*      */       } else {
/*      */         Runnable runnable = new Runnable()
/*      */           {
/*      */             public void run() {
/*      */               JTextComponent.AccessibleJTextComponent.this.firePropertyChange("AccessibleText", null, pos);
/*      */             }
/*      */           };
/*      */         SwingUtilities.invokeLater(runnable);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void removeUpdate(DocumentEvent param1DocumentEvent) {
/*      */       final Integer pos = new Integer(param1DocumentEvent.getOffset());
/*      */       if (SwingUtilities.isEventDispatchThread()) {
/*      */         firePropertyChange("AccessibleText", null, integer);
/*      */       } else {
/*      */         Runnable runnable = new Runnable()
/*      */           {
/*      */             public void run() {
/*      */               JTextComponent.AccessibleJTextComponent.this.firePropertyChange("AccessibleText", null, pos);
/*      */             }
/*      */           };
/*      */         SwingUtilities.invokeLater(runnable);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void changedUpdate(DocumentEvent param1DocumentEvent) {
/*      */       final Integer pos = new Integer(param1DocumentEvent.getOffset());
/*      */       if (SwingUtilities.isEventDispatchThread()) {
/*      */         firePropertyChange("AccessibleText", null, integer);
/*      */       } else {
/*      */         Runnable runnable = new Runnable()
/*      */           {
/*      */             public void run() {
/*      */               JTextComponent.AccessibleJTextComponent.this.firePropertyChange("AccessibleText", null, pos);
/*      */             }
/*      */           };
/*      */         SwingUtilities.invokeLater(runnable);
/*      */       } 
/*      */     }
/*      */     
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/*      */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/*      */       if (JTextComponent.this.isEditable())
/*      */         accessibleStateSet.add(AccessibleState.EDITABLE); 
/*      */       return accessibleStateSet;
/*      */     }
/*      */     
/*      */     public AccessibleRole getAccessibleRole() {
/*      */       return AccessibleRole.TEXT;
/*      */     }
/*      */     
/*      */     public AccessibleText getAccessibleText() {
/*      */       return this;
/*      */     }
/*      */     
/*      */     public int getIndexAtPoint(Point param1Point) {
/*      */       if (param1Point == null)
/*      */         return -1; 
/*      */       return JTextComponent.this.viewToModel(param1Point);
/*      */     }
/*      */     
/*      */     Rectangle getRootEditorRect() {
/*      */       Rectangle rectangle = JTextComponent.this.getBounds();
/*      */       if (rectangle.width > 0 && rectangle.height > 0) {
/*      */         rectangle.x = rectangle.y = 0;
/*      */         Insets insets = JTextComponent.this.getInsets();
/*      */         rectangle.x += insets.left;
/*      */         rectangle.y += insets.top;
/*      */         rectangle.width -= insets.left + insets.right;
/*      */         rectangle.height -= insets.top + insets.bottom;
/*      */         return rectangle;
/*      */       } 
/*      */       return null;
/*      */     }
/*      */     
/*      */     public Rectangle getCharacterBounds(int param1Int) {
/*      */       if (param1Int < 0 || param1Int > JTextComponent.this.model.getLength() - 1)
/*      */         return null; 
/*      */       TextUI textUI = JTextComponent.this.getUI();
/*      */       if (textUI == null)
/*      */         return null; 
/*      */       Rectangle rectangle1 = null;
/*      */       Rectangle rectangle2 = getRootEditorRect();
/*      */       if (rectangle2 == null)
/*      */         return null; 
/*      */       if (JTextComponent.this.model instanceof AbstractDocument)
/*      */         ((AbstractDocument)JTextComponent.this.model).readLock(); 
/*      */       try {
/*      */         View view = textUI.getRootView(JTextComponent.this);
/*      */         if (view != null) {
/*      */           view.setSize(rectangle2.width, rectangle2.height);
/*      */           Shape shape = view.modelToView(param1Int, Position.Bias.Forward, param1Int + 1, Position.Bias.Backward, rectangle2);
/*      */           rectangle1 = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds();
/*      */         } 
/*      */       } catch (BadLocationException badLocationException) {
/*      */       
/*      */       } finally {
/*      */         if (JTextComponent.this.model instanceof AbstractDocument)
/*      */           ((AbstractDocument)JTextComponent.this.model).readUnlock(); 
/*      */       } 
/*      */       return rectangle1;
/*      */     }
/*      */     
/*      */     public int getCharCount() {
/*      */       return JTextComponent.this.model.getLength();
/*      */     }
/*      */     
/*      */     public int getCaretPosition() {
/*      */       return JTextComponent.this.getCaretPosition();
/*      */     }
/*      */     
/*      */     public AttributeSet getCharacterAttribute(int param1Int) {
/*      */       Element element = null;
/*      */       if (JTextComponent.this.model instanceof AbstractDocument)
/*      */         ((AbstractDocument)JTextComponent.this.model).readLock(); 
/*      */       try {
/*      */         for (element = JTextComponent.this.model.getDefaultRootElement(); !element.isLeaf(); ) {
/*      */           int i = element.getElementIndex(param1Int);
/*      */           element = element.getElement(i);
/*      */         } 
/*      */       } finally {
/*      */         if (JTextComponent.this.model instanceof AbstractDocument)
/*      */           ((AbstractDocument)JTextComponent.this.model).readUnlock(); 
/*      */       } 
/*      */       return element.getAttributes();
/*      */     }
/*      */     
/*      */     public int getSelectionStart() {
/*      */       return JTextComponent.this.getSelectionStart();
/*      */     }
/*      */     
/*      */     public int getSelectionEnd() {
/*      */       return JTextComponent.this.getSelectionEnd();
/*      */     }
/*      */     
/*      */     public String getSelectedText() {
/*      */       return JTextComponent.this.getSelectedText();
/*      */     }
/*      */     
/*      */     private class IndexedSegment
/*      */       extends Segment
/*      */     {
/*      */       public int modelOffset;
/*      */       
/*      */       private IndexedSegment() {}
/*      */     }
/*      */     
/*      */     public String getAtIndex(int param1Int1, int param1Int2) {
/*      */       return getAtIndex(param1Int1, param1Int2, 0);
/*      */     }
/*      */     
/*      */     public String getAfterIndex(int param1Int1, int param1Int2) {
/*      */       return getAtIndex(param1Int1, param1Int2, 1);
/*      */     }
/*      */     
/*      */     public String getBeforeIndex(int param1Int1, int param1Int2) {
/*      */       return getAtIndex(param1Int1, param1Int2, -1);
/*      */     }
/*      */     
/*      */     private String getAtIndex(int param1Int1, int param1Int2, int param1Int3) {
/*      */       if (JTextComponent.this.model instanceof AbstractDocument)
/*      */         ((AbstractDocument)JTextComponent.this.model).readLock(); 
/*      */       try {
/*      */         IndexedSegment indexedSegment;
/*      */         if (param1Int2 < 0 || param1Int2 >= JTextComponent.this.model.getLength())
/*      */           return null; 
/*      */         switch (param1Int1) {
/*      */           case 1:
/*      */             if (param1Int2 + param1Int3 < JTextComponent.this.model.getLength() && param1Int2 + param1Int3 >= 0)
/*      */               return JTextComponent.this.model.getText(param1Int2 + param1Int3, 1); 
/*      */             break;
/*      */           case 2:
/*      */           case 3:
/*      */             indexedSegment = getSegmentAt(param1Int1, param1Int2);
/*      */             if (indexedSegment != null) {
/*      */               if (param1Int3 != 0) {
/*      */                 int i;
/*      */                 if (param1Int3 < 0) {
/*      */                   i = indexedSegment.modelOffset - 1;
/*      */                 } else {
/*      */                   i = indexedSegment.modelOffset + param1Int3 * indexedSegment.count;
/*      */                 } 
/*      */                 if (i >= 0 && i <= JTextComponent.this.model.getLength()) {
/*      */                   indexedSegment = getSegmentAt(param1Int1, i);
/*      */                 } else {
/*      */                   indexedSegment = null;
/*      */                 } 
/*      */               } 
/*      */               if (indexedSegment != null)
/*      */                 return new String(indexedSegment.array, indexedSegment.offset, indexedSegment.count); 
/*      */             } 
/*      */             break;
/*      */         } 
/*      */       } catch (BadLocationException badLocationException) {
/*      */       
/*      */       } finally {
/*      */         if (JTextComponent.this.model instanceof AbstractDocument)
/*      */           ((AbstractDocument)JTextComponent.this.model).readUnlock(); 
/*      */       } 
/*      */       return null;
/*      */     }
/*      */     
/*      */     private Element getParagraphElement(int param1Int) {
/*      */       if (JTextComponent.this.model instanceof PlainDocument) {
/*      */         PlainDocument plainDocument = (PlainDocument)JTextComponent.this.model;
/*      */         return plainDocument.getParagraphElement(param1Int);
/*      */       } 
/*      */       if (JTextComponent.this.model instanceof StyledDocument) {
/*      */         StyledDocument styledDocument = (StyledDocument)JTextComponent.this.model;
/*      */         return styledDocument.getParagraphElement(param1Int);
/*      */       } 
/*      */       Element element;
/*      */       for (element = JTextComponent.this.model.getDefaultRootElement(); !element.isLeaf(); ) {
/*      */         int i = element.getElementIndex(param1Int);
/*      */         element = element.getElement(i);
/*      */       } 
/*      */       if (element == null)
/*      */         return null; 
/*      */       return element.getParentElement();
/*      */     }
/*      */     
/*      */     private IndexedSegment getParagraphElementText(int param1Int) throws BadLocationException {
/*      */       Element element = getParagraphElement(param1Int);
/*      */       if (element != null) {
/*      */         IndexedSegment indexedSegment = new IndexedSegment();
/*      */         try {
/*      */           int i = element.getEndOffset() - element.getStartOffset();
/*      */           JTextComponent.this.model.getText(element.getStartOffset(), i, indexedSegment);
/*      */         } catch (BadLocationException badLocationException) {
/*      */           return null;
/*      */         } 
/*      */         indexedSegment.modelOffset = element.getStartOffset();
/*      */         return indexedSegment;
/*      */       } 
/*      */       return null;
/*      */     }
/*      */     
/*      */     private IndexedSegment getSegmentAt(int param1Int1, int param1Int2) throws BadLocationException {
/*      */       BreakIterator breakIterator;
/*      */       IndexedSegment indexedSegment = getParagraphElementText(param1Int2);
/*      */       if (indexedSegment == null)
/*      */         return null; 
/*      */       switch (param1Int1) {
/*      */         case 2:
/*      */           breakIterator = BreakIterator.getWordInstance(getLocale());
/*      */           break;
/*      */         case 3:
/*      */           breakIterator = BreakIterator.getSentenceInstance(getLocale());
/*      */           break;
/*      */         default:
/*      */           return null;
/*      */       } 
/*      */       indexedSegment.first();
/*      */       breakIterator.setText(indexedSegment);
/*      */       int i = breakIterator.following(param1Int2 - indexedSegment.modelOffset + indexedSegment.offset);
/*      */       if (i == -1)
/*      */         return null; 
/*      */       if (i > indexedSegment.offset + indexedSegment.count)
/*      */         return null; 
/*      */       int j = breakIterator.previous();
/*      */       if (j == -1 || j >= indexedSegment.offset + indexedSegment.count)
/*      */         return null; 
/*      */       indexedSegment.modelOffset = indexedSegment.modelOffset + j - indexedSegment.offset;
/*      */       indexedSegment.offset = j;
/*      */       indexedSegment.count = i - j;
/*      */       return indexedSegment;
/*      */     }
/*      */     
/*      */     public AccessibleEditableText getAccessibleEditableText() {
/*      */       return this;
/*      */     }
/*      */     
/*      */     public void setTextContents(String param1String) {
/*      */       JTextComponent.this.setText(param1String);
/*      */     }
/*      */     
/*      */     public void insertTextAtIndex(int param1Int, String param1String) {
/*      */       Document document = JTextComponent.this.getDocument();
/*      */       if (document != null)
/*      */         try {
/*      */           if (param1String != null && param1String.length() > 0) {
/*      */             boolean bool = JTextComponent.this.saveComposedText(param1Int);
/*      */             document.insertString(param1Int, param1String, null);
/*      */             if (bool)
/*      */               JTextComponent.this.restoreComposedText(); 
/*      */           } 
/*      */         } catch (BadLocationException badLocationException) {
/*      */           UIManager.getLookAndFeel().provideErrorFeedback(JTextComponent.this);
/*      */         }  
/*      */     }
/*      */     
/*      */     public String getTextRange(int param1Int1, int param1Int2) {
/*      */       String str = null;
/*      */       int i = Math.min(param1Int1, param1Int2);
/*      */       int j = Math.max(param1Int1, param1Int2);
/*      */       if (i != j)
/*      */         try {
/*      */           Document document = JTextComponent.this.getDocument();
/*      */           str = document.getText(i, j - i);
/*      */         } catch (BadLocationException badLocationException) {
/*      */           throw new IllegalArgumentException(badLocationException.getMessage());
/*      */         }  
/*      */       return str;
/*      */     }
/*      */     
/*      */     public void delete(int param1Int1, int param1Int2) {
/*      */       if (JTextComponent.this.isEditable() && isEnabled()) {
/*      */         try {
/*      */           int i = Math.min(param1Int1, param1Int2);
/*      */           int j = Math.max(param1Int1, param1Int2);
/*      */           if (i != j) {
/*      */             Document document = JTextComponent.this.getDocument();
/*      */             document.remove(i, j - i);
/*      */           } 
/*      */         } catch (BadLocationException badLocationException) {}
/*      */       } else {
/*      */         UIManager.getLookAndFeel().provideErrorFeedback(JTextComponent.this);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void cut(int param1Int1, int param1Int2) {
/*      */       selectText(param1Int1, param1Int2);
/*      */       JTextComponent.this.cut();
/*      */     }
/*      */     
/*      */     public void paste(int param1Int) {
/*      */       JTextComponent.this.setCaretPosition(param1Int);
/*      */       JTextComponent.this.paste();
/*      */     }
/*      */     
/*      */     public void replaceText(int param1Int1, int param1Int2, String param1String) {
/*      */       selectText(param1Int1, param1Int2);
/*      */       JTextComponent.this.replaceSelection(param1String);
/*      */     }
/*      */     
/*      */     public void selectText(int param1Int1, int param1Int2) {
/*      */       JTextComponent.this.select(param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     public void setAttributes(int param1Int1, int param1Int2, AttributeSet param1AttributeSet) {
/*      */       Document document = JTextComponent.this.getDocument();
/*      */       if (document != null && document instanceof StyledDocument) {
/*      */         StyledDocument styledDocument = (StyledDocument)document;
/*      */         int i = param1Int1;
/*      */         int j = param1Int2 - param1Int1;
/*      */         styledDocument.setCharacterAttributes(i, j, param1AttributeSet, true);
/*      */       } 
/*      */     }
/*      */     
/*      */     private AccessibleTextSequence getSequenceAtIndex(int param1Int1, int param1Int2, int param1Int3) {
/*      */       AccessibleTextSequence accessibleTextSequence1;
/*      */       AccessibleTextSequence accessibleTextSequence2;
/*      */       AccessibleTextSequence accessibleTextSequence3;
/*      */       int i;
/*      */       int j;
/*      */       String str;
/*      */       if (param1Int2 < 0 || param1Int2 >= JTextComponent.this.model.getLength())
/*      */         return null; 
/*      */       if (param1Int3 < -1 || param1Int3 > 1)
/*      */         return null; 
/*      */       switch (param1Int1) {
/*      */         case 1:
/*      */           if (JTextComponent.this.model instanceof AbstractDocument)
/*      */             ((AbstractDocument)JTextComponent.this.model).readLock(); 
/*      */           accessibleTextSequence1 = null;
/*      */           try {
/*      */             if (param1Int2 + param1Int3 < JTextComponent.this.model.getLength() && param1Int2 + param1Int3 >= 0)
/*      */               accessibleTextSequence1 = new AccessibleTextSequence(param1Int2 + param1Int3, param1Int2 + param1Int3 + 1, JTextComponent.this.model.getText(param1Int2 + param1Int3, 1)); 
/*      */           } catch (BadLocationException badLocationException) {
/*      */           
/*      */           } finally {
/*      */             if (JTextComponent.this.model instanceof AbstractDocument)
/*      */               ((AbstractDocument)JTextComponent.this.model).readUnlock(); 
/*      */           } 
/*      */           return accessibleTextSequence1;
/*      */         case 2:
/*      */         case 3:
/*      */           if (JTextComponent.this.model instanceof AbstractDocument)
/*      */             ((AbstractDocument)JTextComponent.this.model).readLock(); 
/*      */           accessibleTextSequence2 = null;
/*      */           try {
/*      */             IndexedSegment indexedSegment = getSegmentAt(param1Int1, param1Int2);
/*      */             if (indexedSegment != null) {
/*      */               if (param1Int3 != 0) {
/*      */                 if (param1Int3 < 0) {
/*      */                   i = indexedSegment.modelOffset - 1;
/*      */                 } else {
/*      */                   i = indexedSegment.modelOffset + indexedSegment.count;
/*      */                 } 
/*      */                 if (i >= 0 && i <= JTextComponent.this.model.getLength()) {
/*      */                   indexedSegment = getSegmentAt(param1Int1, i);
/*      */                 } else {
/*      */                   indexedSegment = null;
/*      */                 } 
/*      */               } 
/*      */               if (indexedSegment != null && indexedSegment.offset + indexedSegment.count <= JTextComponent.this.model.getLength())
/*      */                 accessibleTextSequence2 = new AccessibleTextSequence(indexedSegment.offset, indexedSegment.offset + indexedSegment.count, new String(indexedSegment.array, indexedSegment.offset, indexedSegment.count)); 
/*      */             } 
/*      */           } catch (BadLocationException badLocationException) {
/*      */           
/*      */           } finally {
/*      */             if (JTextComponent.this.model instanceof AbstractDocument)
/*      */               ((AbstractDocument)JTextComponent.this.model).readUnlock(); 
/*      */           } 
/*      */           return accessibleTextSequence2;
/*      */         case 4:
/*      */           accessibleTextSequence3 = null;
/*      */           if (JTextComponent.this.model instanceof AbstractDocument)
/*      */             ((AbstractDocument)JTextComponent.this.model).readLock(); 
/*      */           try {
/*      */             i = Utilities.getRowStart(JTextComponent.this, param1Int2);
/*      */             j = Utilities.getRowEnd(JTextComponent.this, param1Int2);
/*      */             if (i >= 0 && j >= i)
/*      */               if (param1Int3 == 0) {
/*      */                 accessibleTextSequence3 = new AccessibleTextSequence(i, j, JTextComponent.this.model.getText(i, j - i + 1));
/*      */               } else if (param1Int3 == -1 && i > 0) {
/*      */                 j = Utilities.getRowEnd(JTextComponent.this, i - 1);
/*      */                 i = Utilities.getRowStart(JTextComponent.this, i - 1);
/*      */                 if (i >= 0 && j >= i)
/*      */                   accessibleTextSequence3 = new AccessibleTextSequence(i, j, JTextComponent.this.model.getText(i, j - i + 1)); 
/*      */               } else if (param1Int3 == 1 && j < JTextComponent.this.model.getLength()) {
/*      */                 i = Utilities.getRowStart(JTextComponent.this, j + 1);
/*      */                 j = Utilities.getRowEnd(JTextComponent.this, j + 1);
/*      */                 if (i >= 0 && j >= i)
/*      */                   accessibleTextSequence3 = new AccessibleTextSequence(i, j, JTextComponent.this.model.getText(i, j - i + 1)); 
/*      */               }  
/*      */           } catch (BadLocationException badLocationException) {
/*      */           
/*      */           } finally {
/*      */             if (JTextComponent.this.model instanceof AbstractDocument)
/*      */               ((AbstractDocument)JTextComponent.this.model).readUnlock(); 
/*      */           } 
/*      */           return accessibleTextSequence3;
/*      */         case 5:
/*      */           str = null;
/*      */           if (JTextComponent.this.model instanceof AbstractDocument)
/*      */             ((AbstractDocument)JTextComponent.this.model).readLock(); 
/*      */           try {
/*      */             i = j = Integer.MIN_VALUE;
/*      */             int k = param1Int2;
/*      */             switch (param1Int3) {
/*      */               case -1:
/*      */                 j = getRunEdge(param1Int2, param1Int3);
/*      */                 k = j - 1;
/*      */                 break;
/*      */               case 1:
/*      */                 i = getRunEdge(param1Int2, param1Int3);
/*      */                 k = i;
/*      */                 break;
/*      */               case 0:
/*      */                 break;
/*      */               default:
/*      */                 throw new AssertionError(param1Int3);
/*      */             } 
/*      */             i = (i != Integer.MIN_VALUE) ? i : getRunEdge(k, -1);
/*      */             j = (j != Integer.MIN_VALUE) ? j : getRunEdge(k, 1);
/*      */             str = JTextComponent.this.model.getText(i, j - i);
/*      */           } catch (BadLocationException badLocationException) {
/*      */             return null;
/*      */           } finally {
/*      */             if (JTextComponent.this.model instanceof AbstractDocument)
/*      */               ((AbstractDocument)JTextComponent.this.model).readUnlock(); 
/*      */           } 
/*      */           return new AccessibleTextSequence(i, j, str);
/*      */       } 
/*      */       return null;
/*      */     }
/*      */     
/*      */     private int getRunEdge(int param1Int1, int param1Int2) throws BadLocationException {
/*      */       Element element3;
/*      */       int j, k;
/*      */       if (param1Int1 < 0 || param1Int1 >= JTextComponent.this.model.getLength())
/*      */         throw new BadLocationException("Location out of bounds", param1Int1); 
/*      */       int i = -1;
/*      */       Element element1 = JTextComponent.this.model.getDefaultRootElement();
/*      */       while (!element1.isLeaf()) {
/*      */         i = element1.getElementIndex(param1Int1);
/*      */         element1 = element1.getElement(i);
/*      */       } 
/*      */       if (i == -1)
/*      */         throw new AssertionError(param1Int1); 
/*      */       AttributeSet attributeSet = element1.getAttributes();
/*      */       Element element2 = element1.getParentElement();
/*      */       switch (param1Int2) {
/*      */         case -1:
/*      */         case 1:
/*      */           j = i;
/*      */           k = element2.getElementCount();
/*      */           while (j + param1Int2 > 0 && j + param1Int2 < k && element2.getElement(j + param1Int2).getAttributes().isEqual(attributeSet))
/*      */             j += param1Int2; 
/*      */           element3 = element2.getElement(j);
/*      */           break;
/*      */         default:
/*      */           throw new AssertionError(param1Int2);
/*      */       } 
/*      */       switch (param1Int2) {
/*      */         case -1:
/*      */           return element3.getStartOffset();
/*      */         case 1:
/*      */           return element3.getEndOffset();
/*      */       } 
/*      */       return Integer.MIN_VALUE;
/*      */     }
/*      */     
/*      */     public AccessibleTextSequence getTextSequenceAt(int param1Int1, int param1Int2) {
/*      */       return getSequenceAtIndex(param1Int1, param1Int2, 0);
/*      */     }
/*      */     
/*      */     public AccessibleTextSequence getTextSequenceAfter(int param1Int1, int param1Int2) {
/*      */       return getSequenceAtIndex(param1Int1, param1Int2, 1);
/*      */     }
/*      */     
/*      */     public AccessibleTextSequence getTextSequenceBefore(int param1Int1, int param1Int2) {
/*      */       return getSequenceAtIndex(param1Int1, param1Int2, -1);
/*      */     }
/*      */     
/*      */     public Rectangle getTextBounds(int param1Int1, int param1Int2) {
/*      */       if (param1Int1 < 0 || param1Int1 > JTextComponent.this.model.getLength() - 1 || param1Int2 < 0 || param1Int2 > JTextComponent.this.model.getLength() - 1 || param1Int1 > param1Int2)
/*      */         return null; 
/*      */       TextUI textUI = JTextComponent.this.getUI();
/*      */       if (textUI == null)
/*      */         return null; 
/*      */       Rectangle rectangle1 = null;
/*      */       Rectangle rectangle2 = getRootEditorRect();
/*      */       if (rectangle2 == null)
/*      */         return null; 
/*      */       if (JTextComponent.this.model instanceof AbstractDocument)
/*      */         ((AbstractDocument)JTextComponent.this.model).readLock(); 
/*      */       try {
/*      */         View view = textUI.getRootView(JTextComponent.this);
/*      */         if (view != null) {
/*      */           Shape shape = view.modelToView(param1Int1, Position.Bias.Forward, param1Int2, Position.Bias.Backward, rectangle2);
/*      */           rectangle1 = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds();
/*      */         } 
/*      */       } catch (BadLocationException badLocationException) {
/*      */       
/*      */       } finally {
/*      */         if (JTextComponent.this.model instanceof AbstractDocument)
/*      */           ((AbstractDocument)JTextComponent.this.model).readUnlock(); 
/*      */       } 
/*      */       return rectangle1;
/*      */     }
/*      */     
/*      */     public AccessibleAction getAccessibleAction() {
/*      */       return this;
/*      */     }
/*      */     
/*      */     public int getAccessibleActionCount() {
/*      */       Action[] arrayOfAction = JTextComponent.this.getActions();
/*      */       return arrayOfAction.length;
/*      */     }
/*      */     
/*      */     public String getAccessibleActionDescription(int param1Int) {
/*      */       Action[] arrayOfAction = JTextComponent.this.getActions();
/*      */       if (param1Int < 0 || param1Int >= arrayOfAction.length)
/*      */         return null; 
/*      */       return (String)arrayOfAction[param1Int].getValue("Name");
/*      */     }
/*      */     
/*      */     public boolean doAccessibleAction(int param1Int) {
/*      */       Action[] arrayOfAction = JTextComponent.this.getActions();
/*      */       if (param1Int < 0 || param1Int >= arrayOfAction.length)
/*      */         return false; 
/*      */       ActionEvent actionEvent = new ActionEvent(JTextComponent.this, 1001, null, EventQueue.getMostRecentEventTime(), JTextComponent.this.getCurrentEventModifiers());
/*      */       arrayOfAction[param1Int].actionPerformed(actionEvent);
/*      */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*      */     paramObjectInputStream.defaultReadObject();
/*      */     this.caretEvent = new MutableCaretEvent(this);
/*      */     addMouseListener(this.caretEvent);
/*      */     addFocusListener(this.caretEvent);
/*      */   }
/*      */   
/*      */   public JTextComponent() {
/* 3805 */     this.dropMode = DropMode.USE_SELECTION;
/*      */     enableEvents(2056L);
/*      */     this.caretEvent = new MutableCaretEvent(this);
/*      */     addMouseListener(this.caretEvent);
/*      */     addFocusListener(this.caretEvent);
/*      */     setEditable(true);
/*      */     setDragEnabled(false);
/*      */     setLayout((LayoutManager)null);
/*      */     updateUI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class DropLocation
/*      */     extends TransferHandler.DropLocation
/*      */   {
/*      */     private DropLocation(Point param1Point, int param1Int, Position.Bias param1Bias) {
/* 3823 */       super(param1Point);
/* 3824 */       this.index = param1Int;
/* 3825 */       this.bias = param1Bias;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private final int index;
/*      */     
/*      */     private final Position.Bias bias;
/*      */ 
/*      */     
/*      */     public int getIndex() {
/* 3836 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Position.Bias getBias() {
/* 3845 */       return this.bias;
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
/*      */     public String toString() {
/* 3857 */       return getClass().getName() + "[dropPoint=" + 
/* 3858 */         getDropPoint() + ",index=" + this.index + ",bias=" + this.bias + "]";
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
/* 3873 */   private static Cache<Class<?>, Boolean> METHOD_OVERRIDDEN = new Cache<Class<?>, Boolean>(Cache.Kind.WEAK, Cache.Kind.STRONG)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Boolean create(final Class<?> type)
/*      */       {
/* 3881 */         if (JTextComponent.class == type) {
/* 3882 */           return Boolean.FALSE;
/*      */         }
/* 3884 */         if (get(type.getSuperclass()).booleanValue()) {
/* 3885 */           return Boolean.TRUE;
/*      */         }
/* 3887 */         return AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */             {
/*      */               public Boolean run() {
/*      */                 try {
/* 3891 */                   type.getDeclaredMethod("processInputMethodEvent", new Class[] { InputMethodEvent.class });
/* 3892 */                   return Boolean.TRUE;
/* 3893 */                 } catch (NoSuchMethodException noSuchMethodException) {
/* 3894 */                   return Boolean.FALSE;
/*      */                 } 
/*      */               }
/*      */             });
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3914 */     String str1 = this.editable ? "true" : "false";
/*      */ 
/*      */     
/* 3917 */     String str2 = (this.caretColor != null) ? this.caretColor.toString() : "";
/*      */     
/* 3919 */     String str3 = (this.selectionColor != null) ? this.selectionColor.toString() : "";
/*      */     
/* 3921 */     String str4 = (this.selectedTextColor != null) ? this.selectedTextColor.toString() : "";
/*      */     
/* 3923 */     String str5 = (this.disabledTextColor != null) ? this.disabledTextColor.toString() : "";
/*      */     
/* 3925 */     String str6 = (this.margin != null) ? this.margin.toString() : "";
/*      */     
/* 3927 */     return super.paramString() + ",caretColor=" + str2 + ",disabledTextColor=" + str5 + ",editable=" + str1 + ",margin=" + str6 + ",selectedTextColor=" + str4 + ",selectionColor=" + str3;
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
/*      */   static class DefaultTransferHandler
/*      */     extends TransferHandler
/*      */     implements UIResource
/*      */   {
/*      */     public void exportToClipboard(JComponent param1JComponent, Clipboard param1Clipboard, int param1Int) throws IllegalStateException {
/* 3947 */       if (param1JComponent instanceof JTextComponent) {
/* 3948 */         JTextComponent jTextComponent = (JTextComponent)param1JComponent;
/* 3949 */         int i = jTextComponent.getSelectionStart();
/* 3950 */         int j = jTextComponent.getSelectionEnd();
/* 3951 */         if (i != j)
/*      */           try {
/* 3953 */             Document document = jTextComponent.getDocument();
/* 3954 */             String str = document.getText(i, j - i);
/* 3955 */             StringSelection stringSelection = new StringSelection(str);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3960 */             param1Clipboard.setContents(stringSelection, null);
/*      */             
/* 3962 */             if (param1Int == 2) {
/* 3963 */               document.remove(i, j - i);
/*      */             }
/* 3965 */           } catch (BadLocationException badLocationException) {} 
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean importData(JComponent param1JComponent, Transferable param1Transferable) {
/* 3970 */       if (param1JComponent instanceof JTextComponent) {
/* 3971 */         DataFlavor dataFlavor = getFlavor(param1Transferable.getTransferDataFlavors());
/*      */         
/* 3973 */         if (dataFlavor != null) {
/* 3974 */           InputContext inputContext = param1JComponent.getInputContext();
/* 3975 */           if (inputContext != null) {
/* 3976 */             inputContext.endComposition();
/*      */           }
/*      */           
/* 3979 */           try { String str = (String)param1Transferable.getTransferData(dataFlavor);
/*      */             
/* 3981 */             ((JTextComponent)param1JComponent).replaceSelection(str);
/* 3982 */             return true; }
/* 3983 */           catch (UnsupportedFlavorException unsupportedFlavorException) {  }
/* 3984 */           catch (IOException iOException) {}
/*      */         } 
/*      */       } 
/*      */       
/* 3988 */       return false;
/*      */     }
/*      */     
/*      */     public boolean canImport(JComponent param1JComponent, DataFlavor[] param1ArrayOfDataFlavor) {
/* 3992 */       JTextComponent jTextComponent = (JTextComponent)param1JComponent;
/* 3993 */       if (!jTextComponent.isEditable() || !jTextComponent.isEnabled()) {
/* 3994 */         return false;
/*      */       }
/* 3996 */       return (getFlavor(param1ArrayOfDataFlavor) != null);
/*      */     }
/*      */     public int getSourceActions(JComponent param1JComponent) {
/* 3999 */       return 0;
/*      */     }
/*      */     private DataFlavor getFlavor(DataFlavor[] param1ArrayOfDataFlavor) {
/* 4002 */       if (param1ArrayOfDataFlavor != null) {
/* 4003 */         for (DataFlavor dataFlavor : param1ArrayOfDataFlavor) {
/* 4004 */           if (dataFlavor.equals(DataFlavor.stringFlavor)) {
/* 4005 */             return dataFlavor;
/*      */           }
/*      */         } 
/*      */       }
/* 4009 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final JTextComponent getFocusedComponent() {
/* 4018 */     return (JTextComponent)AppContext.getAppContext()
/* 4019 */       .get(FOCUSED_COMPONENT);
/*      */   }
/*      */   
/*      */   private int getCurrentEventModifiers() {
/* 4023 */     int i = 0;
/* 4024 */     AWTEvent aWTEvent = EventQueue.getCurrentEvent();
/* 4025 */     if (aWTEvent instanceof InputEvent) {
/* 4026 */       i = ((InputEvent)aWTEvent).getModifiers();
/* 4027 */     } else if (aWTEvent instanceof ActionEvent) {
/* 4028 */       i = ((ActionEvent)aWTEvent).getModifiers();
/*      */     } 
/* 4030 */     return i;
/*      */   }
/*      */   
/* 4033 */   private static final Object KEYMAP_TABLE = new StringBuilder("JTextComponent_KeymapTable");
/*      */   
/*      */   private transient InputMethodRequests inputMethodRequestsHandler;
/*      */   
/*      */   private SimpleAttributeSet composedTextAttribute;
/*      */   
/*      */   private String composedTextContent;
/*      */   
/*      */   private Position composedTextStart;
/*      */   private Position composedTextEnd;
/*      */   private Position latestCommittedTextStart;
/*      */   private Position latestCommittedTextEnd;
/*      */   private ComposedTextCaret composedTextCaret;
/*      */   private transient Caret originalCaret;
/*      */   private boolean checkedInputOverride;
/*      */   private boolean needToSendKeyTypedEvent;
/*      */   
/*      */   static class DefaultKeymap
/*      */     implements Keymap
/*      */   {
/*      */     String nm;
/*      */     Keymap parent;
/*      */     Hashtable<KeyStroke, Action> bindings;
/*      */     Action defaultAction;
/*      */     
/*      */     DefaultKeymap(String param1String, Keymap param1Keymap) {
/* 4059 */       this.nm = param1String;
/* 4060 */       this.parent = param1Keymap;
/* 4061 */       this.bindings = new Hashtable<>();
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
/*      */     public Action getDefaultAction() {
/* 4073 */       if (this.defaultAction != null) {
/* 4074 */         return this.defaultAction;
/*      */       }
/* 4076 */       return (this.parent != null) ? this.parent.getDefaultAction() : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDefaultAction(Action param1Action) {
/* 4083 */       this.defaultAction = param1Action;
/*      */     }
/*      */     
/*      */     public String getName() {
/* 4087 */       return this.nm;
/*      */     }
/*      */     
/*      */     public Action getAction(KeyStroke param1KeyStroke) {
/* 4091 */       Action action = this.bindings.get(param1KeyStroke);
/* 4092 */       if (action == null && this.parent != null) {
/* 4093 */         action = this.parent.getAction(param1KeyStroke);
/*      */       }
/* 4095 */       return action;
/*      */     }
/*      */     
/*      */     public KeyStroke[] getBoundKeyStrokes() {
/* 4099 */       KeyStroke[] arrayOfKeyStroke = new KeyStroke[this.bindings.size()];
/* 4100 */       byte b = 0;
/* 4101 */       for (Enumeration<KeyStroke> enumeration = this.bindings.keys(); enumeration.hasMoreElements();) {
/* 4102 */         arrayOfKeyStroke[b++] = enumeration.nextElement();
/*      */       }
/* 4104 */       return arrayOfKeyStroke;
/*      */     }
/*      */     
/*      */     public Action[] getBoundActions() {
/* 4108 */       Action[] arrayOfAction = new Action[this.bindings.size()];
/* 4109 */       byte b = 0;
/* 4110 */       for (Enumeration<Action> enumeration = this.bindings.elements(); enumeration.hasMoreElements();) {
/* 4111 */         arrayOfAction[b++] = enumeration.nextElement();
/*      */       }
/* 4113 */       return arrayOfAction;
/*      */     }
/*      */     
/*      */     public KeyStroke[] getKeyStrokesForAction(Action param1Action) {
/* 4117 */       if (param1Action == null) {
/* 4118 */         return null;
/*      */       }
/* 4120 */       KeyStroke[] arrayOfKeyStroke = null;
/*      */       
/* 4122 */       Vector<KeyStroke> vector = null;
/* 4123 */       for (Enumeration<KeyStroke> enumeration = this.bindings.keys(); enumeration.hasMoreElements(); ) {
/* 4124 */         KeyStroke keyStroke = enumeration.nextElement();
/* 4125 */         if (this.bindings.get(keyStroke) == param1Action) {
/* 4126 */           if (vector == null) {
/* 4127 */             vector = new Vector();
/*      */           }
/* 4129 */           vector.addElement(keyStroke);
/*      */         } 
/*      */       } 
/*      */       
/* 4133 */       if (this.parent != null) {
/* 4134 */         KeyStroke[] arrayOfKeyStroke1 = this.parent.getKeyStrokesForAction(param1Action);
/* 4135 */         if (arrayOfKeyStroke1 != null) {
/*      */ 
/*      */           
/* 4138 */           byte b = 0; int i;
/* 4139 */           for (i = arrayOfKeyStroke1.length - 1; i >= 0; 
/* 4140 */             i--) {
/* 4141 */             if (isLocallyDefined(arrayOfKeyStroke1[i])) {
/* 4142 */               arrayOfKeyStroke1[i] = null;
/* 4143 */               b++;
/*      */             } 
/*      */           } 
/* 4146 */           if (b > 0 && b < arrayOfKeyStroke1.length) {
/* 4147 */             if (vector == null) {
/* 4148 */               vector = new Vector<>();
/*      */             }
/* 4150 */             for (i = arrayOfKeyStroke1.length - 1; i >= 0; 
/* 4151 */               i--) {
/* 4152 */               if (arrayOfKeyStroke1[i] != null) {
/* 4153 */                 vector.addElement(arrayOfKeyStroke1[i]);
/*      */               }
/*      */             }
/*      */           
/* 4157 */           } else if (b == 0) {
/* 4158 */             if (vector == null) {
/* 4159 */               arrayOfKeyStroke = arrayOfKeyStroke1;
/*      */             } else {
/*      */               
/* 4162 */               arrayOfKeyStroke = new KeyStroke[vector.size() + arrayOfKeyStroke1.length];
/*      */               
/* 4164 */               vector.copyInto((Object[])arrayOfKeyStroke);
/* 4165 */               System.arraycopy(arrayOfKeyStroke1, 0, arrayOfKeyStroke, vector
/* 4166 */                   .size(), arrayOfKeyStroke1.length);
/* 4167 */               vector = null;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 4172 */       if (vector != null) {
/* 4173 */         arrayOfKeyStroke = new KeyStroke[vector.size()];
/* 4174 */         vector.copyInto((Object[])arrayOfKeyStroke);
/*      */       } 
/* 4176 */       return arrayOfKeyStroke;
/*      */     }
/*      */     
/*      */     public boolean isLocallyDefined(KeyStroke param1KeyStroke) {
/* 4180 */       return this.bindings.containsKey(param1KeyStroke);
/*      */     }
/*      */     
/*      */     public void addActionForKeyStroke(KeyStroke param1KeyStroke, Action param1Action) {
/* 4184 */       this.bindings.put(param1KeyStroke, param1Action);
/*      */     }
/*      */     
/*      */     public void removeKeyStrokeBinding(KeyStroke param1KeyStroke) {
/* 4188 */       this.bindings.remove(param1KeyStroke);
/*      */     }
/*      */     
/*      */     public void removeBindings() {
/* 4192 */       this.bindings.clear();
/*      */     }
/*      */     
/*      */     public Keymap getResolveParent() {
/* 4196 */       return this.parent;
/*      */     }
/*      */     
/*      */     public void setResolveParent(Keymap param1Keymap) {
/* 4200 */       this.parent = param1Keymap;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 4208 */       return "Keymap[" + this.nm + "]" + this.bindings;
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
/*      */   static class KeymapWrapper
/*      */     extends InputMap
/*      */   {
/* 4234 */     static final Object DefaultActionKey = new Object();
/*      */     
/*      */     private Keymap keymap;
/*      */     
/*      */     KeymapWrapper(Keymap param1Keymap) {
/* 4239 */       this.keymap = param1Keymap;
/*      */     }
/*      */     
/*      */     public KeyStroke[] keys() {
/* 4243 */       KeyStroke[] arrayOfKeyStroke1 = super.keys();
/* 4244 */       KeyStroke[] arrayOfKeyStroke2 = this.keymap.getBoundKeyStrokes();
/* 4245 */       byte b1 = (arrayOfKeyStroke1 == null) ? 0 : arrayOfKeyStroke1.length;
/* 4246 */       byte b2 = (arrayOfKeyStroke2 == null) ? 0 : arrayOfKeyStroke2.length;
/* 4247 */       if (!b1) {
/* 4248 */         return arrayOfKeyStroke2;
/*      */       }
/* 4250 */       if (!b2) {
/* 4251 */         return arrayOfKeyStroke1;
/*      */       }
/* 4253 */       KeyStroke[] arrayOfKeyStroke3 = new KeyStroke[b1 + b2];
/*      */       
/* 4255 */       System.arraycopy(arrayOfKeyStroke1, 0, arrayOfKeyStroke3, 0, b1);
/* 4256 */       System.arraycopy(arrayOfKeyStroke2, 0, arrayOfKeyStroke3, b1, b2);
/* 4257 */       return arrayOfKeyStroke3;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 4262 */       KeyStroke[] arrayOfKeyStroke = this.keymap.getBoundKeyStrokes();
/* 4263 */       byte b = (arrayOfKeyStroke == null) ? 0 : arrayOfKeyStroke.length;
/*      */       
/* 4265 */       return super.size() + b;
/*      */     }
/*      */     
/*      */     public Object get(KeyStroke param1KeyStroke) {
/* 4269 */       Object object = this.keymap.getAction(param1KeyStroke);
/* 4270 */       if (object == null) {
/* 4271 */         object = super.get(param1KeyStroke);
/* 4272 */         if (object == null && param1KeyStroke
/* 4273 */           .getKeyChar() != Character.MAX_VALUE && this.keymap
/* 4274 */           .getDefaultAction() != null)
/*      */         {
/*      */           
/* 4277 */           object = DefaultActionKey;
/*      */         }
/*      */       } 
/* 4280 */       return object;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class KeymapActionMap
/*      */     extends ActionMap
/*      */   {
/*      */     private Keymap keymap;
/*      */ 
/*      */ 
/*      */     
/*      */     KeymapActionMap(Keymap param1Keymap) {
/* 4295 */       this.keymap = param1Keymap;
/*      */     }
/*      */     
/*      */     public Object[] keys() {
/* 4299 */       Object[] arrayOfObject1 = super.keys();
/* 4300 */       Action[] arrayOfAction = this.keymap.getBoundActions();
/* 4301 */       byte b1 = (arrayOfObject1 == null) ? 0 : arrayOfObject1.length;
/* 4302 */       byte b2 = (arrayOfAction == null) ? 0 : arrayOfAction.length;
/* 4303 */       boolean bool = (this.keymap.getDefaultAction() != null) ? true : false;
/* 4304 */       if (bool) {
/* 4305 */         b2++;
/*      */       }
/* 4307 */       if (!b1) {
/* 4308 */         if (bool) {
/* 4309 */           Object[] arrayOfObject = new Object[b2];
/* 4310 */           if (b2 > 1) {
/* 4311 */             System.arraycopy(arrayOfAction, 0, arrayOfObject, 0, b2 - 1);
/*      */           }
/*      */           
/* 4314 */           arrayOfObject[b2 - 1] = JTextComponent.KeymapWrapper.DefaultActionKey;
/* 4315 */           return arrayOfObject;
/*      */         } 
/* 4317 */         return (Object[])arrayOfAction;
/*      */       } 
/* 4319 */       if (b2 == 0) {
/* 4320 */         return arrayOfObject1;
/*      */       }
/* 4322 */       Object[] arrayOfObject2 = new Object[b1 + b2];
/*      */       
/* 4324 */       System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, b1);
/* 4325 */       if (bool) {
/* 4326 */         if (b2 > 1) {
/* 4327 */           System.arraycopy(arrayOfAction, 0, arrayOfObject2, b1, b2 - 1);
/*      */         }
/*      */         
/* 4330 */         arrayOfObject2[b1 + b2 - 1] = JTextComponent.KeymapWrapper.DefaultActionKey;
/*      */       }
/*      */       else {
/*      */         
/* 4334 */         System.arraycopy(arrayOfAction, 0, arrayOfObject2, b1, b2);
/*      */       } 
/* 4336 */       return arrayOfObject2;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 4341 */       Action[] arrayOfAction = this.keymap.getBoundActions();
/* 4342 */       byte b = (arrayOfAction == null) ? 0 : arrayOfAction.length;
/* 4343 */       if (this.keymap.getDefaultAction() != null) {
/* 4344 */         b++;
/*      */       }
/* 4346 */       return super.size() + b;
/*      */     }
/*      */     
/*      */     public Action get(Object param1Object) {
/* 4350 */       Action action = super.get(param1Object);
/* 4351 */       if (action == null)
/*      */       {
/* 4353 */         if (param1Object == JTextComponent.KeymapWrapper.DefaultActionKey) {
/* 4354 */           action = this.keymap.getDefaultAction();
/*      */         }
/* 4356 */         else if (param1Object instanceof Action) {
/*      */ 
/*      */ 
/*      */           
/* 4360 */           action = (Action)param1Object;
/*      */         } 
/*      */       }
/* 4363 */       return action;
/*      */     }
/*      */   }
/*      */   
/* 4367 */   private static final Object FOCUSED_COMPONENT = new StringBuilder("JTextComponent_FocusedComponent");
/*      */ 
/*      */   
/*      */   public static final String DEFAULT_KEYMAP = "default";
/*      */ 
/*      */   
/*      */   static class MutableCaretEvent
/*      */     extends CaretEvent
/*      */     implements ChangeListener, FocusListener, MouseListener
/*      */   {
/*      */     private boolean dragActive;
/*      */     
/*      */     private int dot;
/*      */     
/*      */     private int mark;
/*      */ 
/*      */     
/*      */     MutableCaretEvent(JTextComponent param1JTextComponent) {
/* 4385 */       super(param1JTextComponent);
/*      */     }
/*      */     
/*      */     final void fire() {
/* 4389 */       JTextComponent jTextComponent = (JTextComponent)getSource();
/* 4390 */       if (jTextComponent != null) {
/* 4391 */         Caret caret = jTextComponent.getCaret();
/* 4392 */         this.dot = caret.getDot();
/* 4393 */         this.mark = caret.getMark();
/* 4394 */         jTextComponent.fireCaretUpdate(this);
/*      */       } 
/*      */     }
/*      */     
/*      */     public final String toString() {
/* 4399 */       return "dot=" + this.dot + ",mark=" + this.mark;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final int getDot() {
/* 4405 */       return this.dot;
/*      */     }
/*      */     
/*      */     public final int getMark() {
/* 4409 */       return this.mark;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final void stateChanged(ChangeEvent param1ChangeEvent) {
/* 4415 */       if (!this.dragActive) {
/* 4416 */         fire();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 4422 */       AppContext.getAppContext().put(JTextComponent.FOCUSED_COMPONENT, param1FocusEvent
/* 4423 */           .getSource());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void mousePressed(MouseEvent param1MouseEvent) {
/* 4439 */       this.dragActive = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void mouseReleased(MouseEvent param1MouseEvent) {
/* 4449 */       this.dragActive = false;
/* 4450 */       fire();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void mouseClicked(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void mouseEntered(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void mouseExited(MouseEvent param1MouseEvent) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processInputMethodEvent(InputMethodEvent paramInputMethodEvent) {
/* 4475 */     super.processInputMethodEvent(paramInputMethodEvent);
/*      */     
/* 4477 */     if (!paramInputMethodEvent.isConsumed()) {
/* 4478 */       if (!isEditable()) {
/*      */         return;
/*      */       }
/* 4481 */       switch (paramInputMethodEvent.getID()) {
/*      */         case 1100:
/* 4483 */           replaceInputMethodText(paramInputMethodEvent);
/*      */ 
/*      */ 
/*      */         
/*      */         case 1101:
/* 4488 */           setInputMethodCaretPosition(paramInputMethodEvent);
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 4493 */       paramInputMethodEvent.consume();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputMethodRequests getInputMethodRequests() {
/* 4501 */     if (this.inputMethodRequestsHandler == null) {
/* 4502 */       this.inputMethodRequestsHandler = new InputMethodRequestsHandler();
/* 4503 */       Document document = getDocument();
/* 4504 */       if (document != null) {
/* 4505 */         document.addDocumentListener((DocumentListener)this.inputMethodRequestsHandler);
/*      */       }
/*      */     } 
/*      */     
/* 4509 */     return this.inputMethodRequestsHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addInputMethodListener(InputMethodListener paramInputMethodListener) {
/* 4516 */     super.addInputMethodListener(paramInputMethodListener);
/* 4517 */     if (paramInputMethodListener != null) {
/* 4518 */       this.needToSendKeyTypedEvent = false;
/* 4519 */       this.checkedInputOverride = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class InputMethodRequestsHandler
/*      */     implements InputMethodRequests, DocumentListener
/*      */   {
/*      */     public AttributedCharacterIterator cancelLatestCommittedText(AttributedCharacterIterator.Attribute[] param1ArrayOfAttribute) {
/* 4533 */       Document document = JTextComponent.this.getDocument();
/* 4534 */       if (document != null && JTextComponent.this.latestCommittedTextStart != null && 
/* 4535 */         !JTextComponent.this.latestCommittedTextStart.equals(JTextComponent.this.latestCommittedTextEnd)) {
/*      */         try {
/* 4537 */           int i = JTextComponent.this.latestCommittedTextStart.getOffset();
/* 4538 */           int j = JTextComponent.this.latestCommittedTextEnd.getOffset();
/*      */           
/* 4540 */           String str = document.getText(i, j - i);
/* 4541 */           document.remove(i, j - i);
/* 4542 */           return (new AttributedString(str)).getIterator();
/* 4543 */         } catch (BadLocationException badLocationException) {}
/*      */       }
/* 4545 */       return null;
/*      */     }
/*      */     
/*      */     public AttributedCharacterIterator getCommittedText(int param1Int1, int param1Int2, AttributedCharacterIterator.Attribute[] param1ArrayOfAttribute) {
/*      */       String str;
/* 4550 */       int i = 0;
/* 4551 */       int j = 0;
/* 4552 */       if (JTextComponent.this.composedTextExists()) {
/* 4553 */         i = JTextComponent.this.composedTextStart.getOffset();
/* 4554 */         j = JTextComponent.this.composedTextEnd.getOffset();
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 4559 */         if (param1Int1 < i) {
/* 4560 */           if (param1Int2 <= i) {
/* 4561 */             str = JTextComponent.this.getText(param1Int1, param1Int2 - param1Int1);
/*      */           } else {
/* 4563 */             int k = i - param1Int1;
/*      */             
/* 4565 */             str = JTextComponent.this.getText(param1Int1, k) + JTextComponent.this.getText(j, param1Int2 - param1Int1 - k);
/*      */           } 
/*      */         } else {
/* 4568 */           str = JTextComponent.this.getText(param1Int1 + j - i, param1Int2 - param1Int1);
/*      */         }
/*      */       
/* 4571 */       } catch (BadLocationException badLocationException) {
/* 4572 */         throw new IllegalArgumentException("Invalid range");
/*      */       } 
/* 4574 */       return (new AttributedString(str)).getIterator();
/*      */     }
/*      */     
/*      */     public int getCommittedTextLength() {
/* 4578 */       Document document = JTextComponent.this.getDocument();
/* 4579 */       int i = 0;
/* 4580 */       if (document != null) {
/* 4581 */         i = document.getLength();
/* 4582 */         if (JTextComponent.this.composedTextContent != null) {
/* 4583 */           if (JTextComponent.this.composedTextEnd == null || JTextComponent.this
/* 4584 */             .composedTextStart == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4592 */             i -= JTextComponent.this.composedTextContent.length();
/*      */           } else {
/* 4594 */             i -= JTextComponent.this.composedTextEnd.getOffset() - JTextComponent.this
/* 4595 */               .composedTextStart.getOffset();
/*      */           } 
/*      */         }
/*      */       } 
/* 4599 */       return i;
/*      */     }
/*      */     
/*      */     public int getInsertPositionOffset() {
/* 4603 */       int i = 0;
/* 4604 */       int j = 0;
/* 4605 */       if (JTextComponent.this.composedTextExists()) {
/* 4606 */         i = JTextComponent.this.composedTextStart.getOffset();
/* 4607 */         j = JTextComponent.this.composedTextEnd.getOffset();
/*      */       } 
/* 4609 */       int k = JTextComponent.this.getCaretPosition();
/*      */       
/* 4611 */       if (k < i)
/* 4612 */         return k; 
/* 4613 */       if (k < j) {
/* 4614 */         return i;
/*      */       }
/* 4616 */       return k - j - i;
/*      */     }
/*      */ 
/*      */     
/*      */     public TextHitInfo getLocationOffset(int param1Int1, int param1Int2) {
/* 4621 */       if (JTextComponent.this.composedTextAttribute == null) {
/* 4622 */         return null;
/*      */       }
/* 4624 */       Point point = JTextComponent.this.getLocationOnScreen();
/* 4625 */       point.x = param1Int1 - point.x;
/* 4626 */       point.y = param1Int2 - point.y;
/* 4627 */       int i = JTextComponent.this.viewToModel(point);
/* 4628 */       if (i >= JTextComponent.this.composedTextStart.getOffset() && i <= JTextComponent.this
/* 4629 */         .composedTextEnd.getOffset()) {
/* 4630 */         return TextHitInfo.leading(i - JTextComponent.this.composedTextStart.getOffset());
/*      */       }
/* 4632 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Rectangle getTextLocation(TextHitInfo param1TextHitInfo) {
/*      */       Rectangle rectangle;
/*      */       try {
/* 4641 */         rectangle = JTextComponent.this.modelToView(JTextComponent.this.getCaretPosition());
/* 4642 */         if (rectangle != null) {
/* 4643 */           Point point = JTextComponent.this.getLocationOnScreen();
/* 4644 */           rectangle.translate(point.x, point.y);
/*      */         } 
/* 4646 */       } catch (BadLocationException badLocationException) {
/* 4647 */         rectangle = null;
/*      */       } 
/*      */       
/* 4650 */       if (rectangle == null) {
/* 4651 */         rectangle = new Rectangle();
/*      */       }
/* 4653 */       return rectangle;
/*      */     }
/*      */ 
/*      */     
/*      */     public AttributedCharacterIterator getSelectedText(AttributedCharacterIterator.Attribute[] param1ArrayOfAttribute) {
/* 4658 */       String str = JTextComponent.this.getSelectedText();
/* 4659 */       if (str != null) {
/* 4660 */         return (new AttributedString(str)).getIterator();
/*      */       }
/* 4662 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void changedUpdate(DocumentEvent param1DocumentEvent) {
/* 4669 */       JTextComponent.this.latestCommittedTextStart = JTextComponent.this.latestCommittedTextEnd = null;
/*      */     }
/*      */     
/*      */     public void insertUpdate(DocumentEvent param1DocumentEvent) {
/* 4673 */       JTextComponent.this.latestCommittedTextStart = JTextComponent.this.latestCommittedTextEnd = null;
/*      */     }
/*      */     
/*      */     public void removeUpdate(DocumentEvent param1DocumentEvent) {
/* 4677 */       JTextComponent.this.latestCommittedTextStart = JTextComponent.this.latestCommittedTextEnd = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void replaceInputMethodText(InputMethodEvent paramInputMethodEvent) {
/* 4687 */     int i = paramInputMethodEvent.getCommittedCharacterCount();
/* 4688 */     AttributedCharacterIterator attributedCharacterIterator = paramInputMethodEvent.getText();
/*      */ 
/*      */ 
/*      */     
/* 4692 */     Document document = getDocument();
/* 4693 */     if (composedTextExists()) {
/*      */       try {
/* 4695 */         document.remove(this.composedTextStart.getOffset(), this.composedTextEnd
/* 4696 */             .getOffset() - this.composedTextStart
/* 4697 */             .getOffset());
/* 4698 */       } catch (BadLocationException badLocationException) {}
/* 4699 */       this.composedTextStart = this.composedTextEnd = null;
/* 4700 */       this.composedTextAttribute = null;
/* 4701 */       this.composedTextContent = null;
/*      */     } 
/*      */     
/* 4704 */     if (attributedCharacterIterator != null) {
/* 4705 */       attributedCharacterIterator.first();
/* 4706 */       int k = 0;
/* 4707 */       int m = 0;
/*      */ 
/*      */       
/* 4710 */       if (i > 0) {
/*      */         
/* 4712 */         k = this.caret.getDot();
/*      */ 
/*      */ 
/*      */         
/* 4716 */         if (shouldSynthensizeKeyEvents()) {
/* 4717 */           for (char c = attributedCharacterIterator.current(); i > 0; 
/* 4718 */             c = attributedCharacterIterator.next(), i--) {
/*      */             
/* 4720 */             KeyEvent keyEvent = new KeyEvent(this, 400, EventQueue.getMostRecentEventTime(), 0, 0, c);
/*      */             
/* 4722 */             processKeyEvent(keyEvent);
/*      */           } 
/*      */         } else {
/* 4725 */           StringBuilder stringBuilder = new StringBuilder();
/* 4726 */           for (char c = attributedCharacterIterator.current(); i > 0; 
/* 4727 */             c = attributedCharacterIterator.next(), i--) {
/* 4728 */             stringBuilder.append(c);
/*      */           }
/*      */ 
/*      */           
/* 4732 */           mapCommittedTextToAction(stringBuilder.toString());
/*      */         } 
/*      */ 
/*      */         
/* 4736 */         m = this.caret.getDot();
/*      */       } 
/*      */ 
/*      */       
/* 4740 */       int j = attributedCharacterIterator.getIndex();
/* 4741 */       if (j < attributedCharacterIterator.getEndIndex()) {
/* 4742 */         createComposedTextAttribute(j, attributedCharacterIterator);
/*      */         try {
/* 4744 */           replaceSelection((String)null);
/* 4745 */           document.insertString(this.caret.getDot(), this.composedTextContent, this.composedTextAttribute);
/*      */           
/* 4747 */           this.composedTextStart = document.createPosition(this.caret.getDot() - this.composedTextContent
/* 4748 */               .length());
/* 4749 */           this.composedTextEnd = document.createPosition(this.caret.getDot());
/* 4750 */         } catch (BadLocationException badLocationException) {
/* 4751 */           this.composedTextStart = this.composedTextEnd = null;
/* 4752 */           this.composedTextAttribute = null;
/* 4753 */           this.composedTextContent = null;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 4758 */       if (k != m) {
/*      */         try {
/* 4760 */           this
/* 4761 */             .latestCommittedTextStart = document.createPosition(k);
/* 4762 */           this
/* 4763 */             .latestCommittedTextEnd = document.createPosition(m);
/* 4764 */         } catch (BadLocationException badLocationException) {
/* 4765 */           this.latestCommittedTextStart = this.latestCommittedTextEnd = null;
/*      */         } 
/*      */       } else {
/*      */         
/* 4769 */         this.latestCommittedTextStart = this.latestCommittedTextEnd = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createComposedTextAttribute(int paramInt, AttributedCharacterIterator paramAttributedCharacterIterator) {
/* 4777 */     Document document = getDocument();
/* 4778 */     StringBuilder stringBuilder = new StringBuilder();
/*      */ 
/*      */     
/* 4781 */     char c = paramAttributedCharacterIterator.setIndex(paramInt);
/* 4782 */     for (; c != Character.MAX_VALUE; c = paramAttributedCharacterIterator.next()) {
/* 4783 */       stringBuilder.append(c);
/*      */     }
/*      */     
/* 4786 */     this.composedTextContent = stringBuilder.toString();
/* 4787 */     this.composedTextAttribute = new SimpleAttributeSet();
/* 4788 */     this.composedTextAttribute.addAttribute(StyleConstants.ComposedTextAttribute, new AttributedString(paramAttributedCharacterIterator, paramInt, paramAttributedCharacterIterator
/* 4789 */           .getEndIndex()));
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
/*      */   protected boolean saveComposedText(int paramInt) {
/* 4805 */     if (composedTextExists()) {
/* 4806 */       int i = this.composedTextStart.getOffset();
/*      */       
/* 4808 */       int j = this.composedTextEnd.getOffset() - this.composedTextStart.getOffset();
/* 4809 */       if (paramInt >= i && paramInt <= i + j) {
/*      */         try {
/* 4811 */           getDocument().remove(i, j);
/* 4812 */           return true;
/* 4813 */         } catch (BadLocationException badLocationException) {}
/*      */       }
/*      */     } 
/* 4816 */     return false;
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
/*      */   protected void restoreComposedText() {
/* 4829 */     Document document = getDocument();
/*      */     try {
/* 4831 */       document.insertString(this.caret.getDot(), this.composedTextContent, this.composedTextAttribute);
/*      */ 
/*      */       
/* 4834 */       this.composedTextStart = document.createPosition(this.caret.getDot() - this.composedTextContent
/* 4835 */           .length());
/* 4836 */       this.composedTextEnd = document.createPosition(this.caret.getDot());
/* 4837 */     } catch (BadLocationException badLocationException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mapCommittedTextToAction(String paramString) {
/* 4846 */     Keymap keymap = getKeymap();
/* 4847 */     if (keymap != null) {
/* 4848 */       Action action = null;
/* 4849 */       if (paramString.length() == 1) {
/* 4850 */         KeyStroke keyStroke = KeyStroke.getKeyStroke(paramString.charAt(0));
/* 4851 */         action = keymap.getAction(keyStroke);
/*      */       } 
/*      */       
/* 4854 */       if (action == null) {
/* 4855 */         action = keymap.getDefaultAction();
/*      */       }
/*      */       
/* 4858 */       if (action != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4863 */         ActionEvent actionEvent = new ActionEvent(this, 1001, paramString, EventQueue.getMostRecentEventTime(), getCurrentEventModifiers());
/* 4864 */         action.actionPerformed(actionEvent);
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
/*      */   private void setInputMethodCaretPosition(InputMethodEvent paramInputMethodEvent) {
/* 4876 */     if (composedTextExists()) {
/* 4877 */       int i = this.composedTextStart.getOffset();
/* 4878 */       if (!(this.caret instanceof ComposedTextCaret)) {
/* 4879 */         if (this.composedTextCaret == null) {
/* 4880 */           this.composedTextCaret = new ComposedTextCaret();
/*      */         }
/* 4882 */         this.originalCaret = this.caret;
/*      */         
/* 4884 */         exchangeCaret(this.originalCaret, this.composedTextCaret);
/*      */       } 
/*      */       
/* 4887 */       TextHitInfo textHitInfo = paramInputMethodEvent.getCaret();
/* 4888 */       if (textHitInfo != null) {
/* 4889 */         int j = textHitInfo.getInsertionIndex();
/* 4890 */         i += j;
/* 4891 */         if (j == 0) {
/*      */           
/*      */           try {
/*      */             
/* 4895 */             Rectangle rectangle1 = modelToView(i);
/* 4896 */             Rectangle rectangle2 = modelToView(this.composedTextEnd.getOffset());
/* 4897 */             Rectangle rectangle3 = getBounds();
/* 4898 */             rectangle1.x += Math.min(rectangle2.x - rectangle1.x, rectangle3.width);
/* 4899 */             scrollRectToVisible(rectangle1);
/* 4900 */           } catch (BadLocationException badLocationException) {}
/*      */         }
/*      */       } 
/* 4903 */       this.caret.setDot(i);
/* 4904 */     } else if (this.caret instanceof ComposedTextCaret) {
/* 4905 */       int i = this.caret.getDot();
/*      */       
/* 4907 */       exchangeCaret(this.caret, this.originalCaret);
/* 4908 */       this.caret.setDot(i);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void exchangeCaret(Caret paramCaret1, Caret paramCaret2) {
/* 4913 */     int i = paramCaret1.getBlinkRate();
/* 4914 */     setCaret(paramCaret2);
/* 4915 */     this.caret.setBlinkRate(i);
/* 4916 */     this.caret.setVisible(hasFocus());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldSynthensizeKeyEvents() {
/* 4923 */     if (!this.checkedInputOverride) {
/*      */ 
/*      */ 
/*      */       
/* 4927 */       this.needToSendKeyTypedEvent = !((Boolean)METHOD_OVERRIDDEN.get(getClass())).booleanValue();
/* 4928 */       this.checkedInputOverride = true;
/*      */     } 
/* 4930 */     return this.needToSendKeyTypedEvent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean composedTextExists() {
/* 4937 */     return (this.composedTextStart != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class ComposedTextCaret
/*      */     extends DefaultCaret
/*      */     implements Serializable
/*      */   {
/*      */     Color bg;
/*      */ 
/*      */     
/*      */     public void install(JTextComponent param1JTextComponent) {
/* 4950 */       super.install(param1JTextComponent);
/*      */       
/* 4952 */       Document document = param1JTextComponent.getDocument();
/* 4953 */       if (document instanceof StyledDocument) {
/* 4954 */         StyledDocument styledDocument = (StyledDocument)document;
/* 4955 */         Element element = styledDocument.getCharacterElement(param1JTextComponent.composedTextStart.getOffset());
/* 4956 */         AttributeSet attributeSet = element.getAttributes();
/* 4957 */         this.bg = styledDocument.getBackground(attributeSet);
/*      */       } 
/*      */       
/* 4960 */       if (this.bg == null) {
/* 4961 */         this.bg = param1JTextComponent.getBackground();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paint(Graphics param1Graphics) {
/* 4969 */       if (isVisible()) {
/*      */         try {
/* 4971 */           Rectangle rectangle = this.component.modelToView(getDot());
/* 4972 */           param1Graphics.setXORMode(this.bg);
/* 4973 */           param1Graphics.drawLine(rectangle.x, rectangle.y, rectangle.x, rectangle.y + rectangle.height - 1);
/* 4974 */           param1Graphics.setPaintMode();
/* 4975 */         } catch (BadLocationException badLocationException) {}
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
/*      */     protected void positionCaret(MouseEvent param1MouseEvent) {
/* 4987 */       JTextComponent jTextComponent = this.component;
/* 4988 */       Point point = new Point(param1MouseEvent.getX(), param1MouseEvent.getY());
/* 4989 */       int i = jTextComponent.viewToModel(point);
/* 4990 */       int j = jTextComponent.composedTextStart.getOffset();
/* 4991 */       if (i < j || i > JTextComponent.this
/* 4992 */         .composedTextEnd.getOffset()) {
/*      */         
/*      */         try {
/* 4995 */           Position position = jTextComponent.getDocument().createPosition(i);
/* 4996 */           jTextComponent.getInputContext().endComposition();
/*      */ 
/*      */ 
/*      */           
/* 5000 */           EventQueue.invokeLater(new JTextComponent.DoSetCaretPosition(jTextComponent, position));
/* 5001 */         } catch (BadLocationException badLocationException) {
/* 5002 */           System.err.println(badLocationException);
/*      */         } 
/*      */       } else {
/*      */         
/* 5006 */         super.positionCaret(param1MouseEvent);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class DoSetCaretPosition
/*      */     implements Runnable
/*      */   {
/*      */     JTextComponent host;
/*      */     Position newPos;
/*      */     
/*      */     DoSetCaretPosition(JTextComponent param1JTextComponent1, Position param1Position) {
/* 5019 */       this.host = param1JTextComponent1;
/* 5020 */       this.newPos = param1Position;
/*      */     }
/*      */     
/*      */     public void run() {
/* 5024 */       this.host.setCaretPosition(this.newPos.getOffset());
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/JTextComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */