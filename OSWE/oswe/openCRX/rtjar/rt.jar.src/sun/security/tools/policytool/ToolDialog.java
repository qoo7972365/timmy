/*      */ package sun.security.tools.policytool;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FileDialog;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.Point;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.net.MalformedURLException;
/*      */ import java.security.InvalidParameterException;
/*      */ import java.security.PublicKey;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.LinkedList;
/*      */ import java.util.Vector;
/*      */ import javax.swing.DefaultListModel;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import sun.security.provider.PolicyParser;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class ToolDialog
/*      */   extends JDialog
/*      */ {
/*      */   private static final long serialVersionUID = -372244357011301190L;
/* 1550 */   static final KeyStroke escKey = KeyStroke.getKeyStroke(27, 0);
/*      */ 
/*      */   
/*      */   public static final int NOACTION = 0;
/*      */ 
/*      */   
/*      */   public static final int QUIT = 1;
/*      */ 
/*      */   
/*      */   public static final int NEW = 2;
/*      */   
/*      */   public static final int OPEN = 3;
/*      */   
/*      */   public static final String ALL_PERM_CLASS = "java.security.AllPermission";
/*      */   
/*      */   public static final String FILE_PERM_CLASS = "java.io.FilePermission";
/*      */   
/*      */   public static final String X500_PRIN_CLASS = "javax.security.auth.x500.X500Principal";
/*      */   
/* 1569 */   public static final String PERM = PolicyTool.getMessage("Permission.");
/*      */ 
/*      */   
/* 1572 */   public static final String PRIN_TYPE = PolicyTool.getMessage("Principal.Type.");
/*      */   
/* 1574 */   public static final String PRIN_NAME = PolicyTool.getMessage("Principal.Name.");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1579 */   public static final String PERM_NAME = PolicyTool.getMessage("Target.Name.");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1584 */   public static final String PERM_ACTIONS = PolicyTool.getMessage("Actions.");
/*      */   
/*      */   public static final int PE_CODEBASE_LABEL = 0;
/*      */   
/*      */   public static final int PE_CODEBASE_TEXTFIELD = 1;
/*      */   
/*      */   public static final int PE_SIGNEDBY_LABEL = 2;
/*      */   
/*      */   public static final int PE_SIGNEDBY_TEXTFIELD = 3;
/*      */   
/*      */   public static final int PE_PANEL0 = 4;
/*      */   
/*      */   public static final int PE_ADD_PRIN_BUTTON = 0;
/*      */   
/*      */   public static final int PE_EDIT_PRIN_BUTTON = 1;
/*      */   
/*      */   public static final int PE_REMOVE_PRIN_BUTTON = 2;
/*      */   
/*      */   public static final int PE_PRIN_LABEL = 5;
/*      */   
/*      */   public static final int PE_PRIN_LIST = 6;
/*      */   
/*      */   public static final int PE_PANEL1 = 7;
/*      */   
/*      */   public static final int PE_ADD_PERM_BUTTON = 0;
/*      */   
/*      */   public static final int PE_EDIT_PERM_BUTTON = 1;
/*      */   
/*      */   public static final int PE_REMOVE_PERM_BUTTON = 2;
/*      */   
/*      */   public static final int PE_PERM_LIST = 8;
/*      */   
/*      */   public static final int PE_PANEL2 = 9;
/*      */   
/*      */   public static final int PE_CANCEL_BUTTON = 1;
/*      */   
/*      */   public static final int PE_DONE_BUTTON = 0;
/*      */   
/*      */   public static final int PRD_DESC_LABEL = 0;
/*      */   
/*      */   public static final int PRD_PRIN_CHOICE = 1;
/*      */   
/*      */   public static final int PRD_PRIN_TEXTFIELD = 2;
/*      */   
/*      */   public static final int PRD_NAME_LABEL = 3;
/*      */   
/*      */   public static final int PRD_NAME_TEXTFIELD = 4;
/*      */   public static final int PRD_CANCEL_BUTTON = 6;
/*      */   public static final int PRD_OK_BUTTON = 5;
/*      */   public static final int PD_DESC_LABEL = 0;
/*      */   public static final int PD_PERM_CHOICE = 1;
/*      */   public static final int PD_PERM_TEXTFIELD = 2;
/*      */   public static final int PD_NAME_CHOICE = 3;
/*      */   public static final int PD_NAME_TEXTFIELD = 4;
/*      */   public static final int PD_ACTIONS_CHOICE = 5;
/*      */   public static final int PD_ACTIONS_TEXTFIELD = 6;
/*      */   public static final int PD_SIGNEDBY_LABEL = 7;
/*      */   public static final int PD_SIGNEDBY_TEXTFIELD = 8;
/*      */   public static final int PD_CANCEL_BUTTON = 10;
/*      */   public static final int PD_OK_BUTTON = 9;
/*      */   public static final int EDIT_KEYSTORE = 0;
/*      */   public static final int KSD_NAME_LABEL = 0;
/*      */   public static final int KSD_NAME_TEXTFIELD = 1;
/*      */   public static final int KSD_TYPE_LABEL = 2;
/*      */   public static final int KSD_TYPE_TEXTFIELD = 3;
/*      */   public static final int KSD_PROVIDER_LABEL = 4;
/*      */   public static final int KSD_PROVIDER_TEXTFIELD = 5;
/*      */   public static final int KSD_PWD_URL_LABEL = 6;
/*      */   public static final int KSD_PWD_URL_TEXTFIELD = 7;
/*      */   public static final int KSD_CANCEL_BUTTON = 9;
/*      */   public static final int KSD_OK_BUTTON = 8;
/*      */   public static final int USC_LABEL = 0;
/*      */   public static final int USC_PANEL = 1;
/*      */   public static final int USC_YES_BUTTON = 0;
/*      */   public static final int USC_NO_BUTTON = 1;
/*      */   public static final int USC_CANCEL_BUTTON = 2;
/*      */   public static final int CRPE_LABEL1 = 0;
/*      */   public static final int CRPE_LABEL2 = 1;
/*      */   public static final int CRPE_PANEL = 2;
/*      */   public static final int CRPE_PANEL_OK = 0;
/*      */   public static final int CRPE_PANEL_CANCEL = 1;
/*      */   private static final int PERMISSION = 0;
/*      */   private static final int PERMISSION_NAME = 1;
/*      */   private static final int PERMISSION_ACTIONS = 2;
/*      */   private static final int PERMISSION_SIGNEDBY = 3;
/*      */   private static final int PRINCIPAL_TYPE = 4;
/*      */   private static final int PRINCIPAL_NAME = 5;
/* 1671 */   static final int TEXTFIELD_HEIGHT = ((new JComboBox()).getPreferredSize()).height;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1682 */   public static ArrayList<Perm> PERM_ARRAY = new ArrayList<>(); static {
/* 1683 */     PERM_ARRAY.add(new AllPerm());
/* 1684 */     PERM_ARRAY.add(new AudioPerm());
/* 1685 */     PERM_ARRAY.add(new AuthPerm());
/* 1686 */     PERM_ARRAY.add(new AWTPerm());
/* 1687 */     PERM_ARRAY.add(new DelegationPerm());
/* 1688 */     PERM_ARRAY.add(new FilePerm());
/* 1689 */     PERM_ARRAY.add(new URLPerm());
/* 1690 */     PERM_ARRAY.add(new InqSecContextPerm());
/* 1691 */     PERM_ARRAY.add(new LogPerm());
/* 1692 */     PERM_ARRAY.add(new MgmtPerm());
/* 1693 */     PERM_ARRAY.add(new MBeanPerm());
/* 1694 */     PERM_ARRAY.add(new MBeanSvrPerm());
/* 1695 */     PERM_ARRAY.add(new MBeanTrustPerm());
/* 1696 */     PERM_ARRAY.add(new NetPerm());
/* 1697 */     PERM_ARRAY.add(new PrivCredPerm());
/* 1698 */     PERM_ARRAY.add(new PropPerm());
/* 1699 */     PERM_ARRAY.add(new ReflectPerm());
/* 1700 */     PERM_ARRAY.add(new RuntimePerm());
/* 1701 */     PERM_ARRAY.add(new SecurityPerm());
/* 1702 */     PERM_ARRAY.add(new SerialPerm());
/* 1703 */     PERM_ARRAY.add(new ServicePerm());
/* 1704 */     PERM_ARRAY.add(new SocketPerm());
/* 1705 */     PERM_ARRAY.add(new SQLPerm());
/* 1706 */     PERM_ARRAY.add(new SSLPerm());
/* 1707 */     PERM_ARRAY.add(new SubjDelegPerm());
/*      */   }
/*      */ 
/*      */   
/* 1711 */   public static ArrayList<Prin> PRIN_ARRAY = new ArrayList<>(); PolicyTool tool; static {
/* 1712 */     PRIN_ARRAY.add(new KrbPrin());
/* 1713 */     PRIN_ARRAY.add(new X500Prin());
/*      */   }
/*      */   ToolWindow tw;
/*      */   ToolDialog(String paramString, PolicyTool paramPolicyTool, ToolWindow paramToolWindow, boolean paramBoolean) {
/* 1717 */     super(paramToolWindow, paramBoolean);
/* 1718 */     setTitle(paramString);
/* 1719 */     this.tool = paramPolicyTool;
/* 1720 */     this.tw = paramToolWindow;
/* 1721 */     addWindowListener(new ChildWindowListener(this));
/*      */ 
/*      */     
/* 1724 */     ((JPanel)getContentPane()).setBorder(new EmptyBorder(6, 6, 6, 6));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component getComponent(int paramInt) {
/* 1731 */     Component component = getContentPane().getComponent(paramInt);
/* 1732 */     if (component instanceof JScrollPane) {
/* 1733 */       component = ((JScrollPane)component).getViewport().getView();
/*      */     }
/* 1735 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Perm getPerm(String paramString, boolean paramBoolean) {
/* 1743 */     for (byte b = 0; b < PERM_ARRAY.size(); b++) {
/* 1744 */       Perm perm = PERM_ARRAY.get(b);
/* 1745 */       if (paramBoolean) {
/* 1746 */         if (perm.FULL_CLASS.equals(paramString)) {
/* 1747 */           return perm;
/*      */         }
/*      */       }
/* 1750 */       else if (perm.CLASS.equals(paramString)) {
/* 1751 */         return perm;
/*      */       } 
/*      */     } 
/*      */     
/* 1755 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Prin getPrin(String paramString, boolean paramBoolean) {
/* 1763 */     for (byte b = 0; b < PRIN_ARRAY.size(); b++) {
/* 1764 */       Prin prin = PRIN_ARRAY.get(b);
/* 1765 */       if (paramBoolean) {
/* 1766 */         if (prin.FULL_CLASS.equals(paramString)) {
/* 1767 */           return prin;
/*      */         }
/*      */       }
/* 1770 */       else if (prin.CLASS.equals(paramString)) {
/* 1771 */         return prin;
/*      */       } 
/*      */     } 
/*      */     
/* 1775 */     return null;
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
/*      */   void displayPolicyEntryDialog(boolean paramBoolean) {
/* 1792 */     int i = 0;
/* 1793 */     PolicyEntry[] arrayOfPolicyEntry = null;
/* 1794 */     TaggedList taggedList1 = new TaggedList(3, false);
/* 1795 */     taggedList1.getAccessibleContext().setAccessibleName(
/* 1796 */         PolicyTool.getMessage("Principal.List"));
/* 1797 */     taggedList1
/* 1798 */       .addMouseListener(new EditPrinButtonListener(this.tool, this.tw, this, paramBoolean));
/* 1799 */     TaggedList taggedList2 = new TaggedList(10, false);
/* 1800 */     taggedList2.getAccessibleContext().setAccessibleName(
/* 1801 */         PolicyTool.getMessage("Permission.List"));
/* 1802 */     taggedList2
/* 1803 */       .addMouseListener(new EditPermButtonListener(this.tool, this.tw, this, paramBoolean));
/*      */ 
/*      */     
/* 1806 */     Point point = this.tw.getLocationOnScreen();
/*      */     
/* 1808 */     setLayout(new GridBagLayout());
/* 1809 */     setResizable(true);
/*      */     
/* 1811 */     if (paramBoolean) {
/*      */       
/* 1813 */       arrayOfPolicyEntry = this.tool.getEntry();
/* 1814 */       JList jList = (JList)this.tw.getComponent(3);
/* 1815 */       i = jList.getSelectedIndex();
/*      */ 
/*      */ 
/*      */       
/* 1819 */       LinkedList<PolicyParser.PrincipalEntry> linkedList = (arrayOfPolicyEntry[i].getGrantEntry()).principals;
/* 1820 */       for (byte b1 = 0; b1 < linkedList.size(); b1++) {
/* 1821 */         Object object = null;
/* 1822 */         PolicyParser.PrincipalEntry principalEntry = linkedList.get(b1);
/* 1823 */         taggedList1.addTaggedItem(PrincipalEntryToUserFriendlyString(principalEntry), principalEntry);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1828 */       Vector<PolicyParser.PermissionEntry> vector = (arrayOfPolicyEntry[i].getGrantEntry()).permissionEntries;
/* 1829 */       for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 1830 */         Object object = null;
/*      */         
/* 1832 */         PolicyParser.PermissionEntry permissionEntry = vector.elementAt(b2);
/* 1833 */         taggedList2.addTaggedItem(PermissionEntryToUserFriendlyString(permissionEntry), permissionEntry);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1838 */     JLabel jLabel = new JLabel();
/* 1839 */     this.tw.addNewComponent(this, jLabel, 0, 0, 0, 1, 1, 0.0D, 0.0D, 1, ToolWindow.R_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1844 */     JTextField jTextField = paramBoolean ? new JTextField((arrayOfPolicyEntry[i].getGrantEntry()).codeBase) : new JTextField();
/*      */     
/* 1846 */     ToolWindow.configureLabelFor(jLabel, jTextField, "CodeBase.");
/* 1847 */     jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 1848 */     jTextField.getAccessibleContext().setAccessibleName(
/* 1849 */         PolicyTool.getMessage("Code.Base"));
/* 1850 */     this.tw.addNewComponent(this, jTextField, 1, 1, 0, 1, 1, 1.0D, 0.0D, 1);
/*      */ 
/*      */ 
/*      */     
/* 1854 */     jLabel = new JLabel();
/* 1855 */     this.tw.addNewComponent(this, jLabel, 2, 0, 1, 1, 1, 0.0D, 0.0D, 1, ToolWindow.R_PADDING);
/*      */ 
/*      */ 
/*      */     
/* 1859 */     jTextField = paramBoolean ? new JTextField((arrayOfPolicyEntry[i].getGrantEntry()).signedBy) : new JTextField();
/*      */     
/* 1861 */     ToolWindow.configureLabelFor(jLabel, jTextField, "SignedBy.");
/* 1862 */     jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 1863 */     jTextField.getAccessibleContext().setAccessibleName(
/* 1864 */         PolicyTool.getMessage("Signed.By."));
/* 1865 */     this.tw.addNewComponent(this, jTextField, 3, 1, 1, 1, 1, 1.0D, 0.0D, 1);
/*      */ 
/*      */ 
/*      */     
/* 1869 */     JPanel jPanel = new JPanel();
/* 1870 */     jPanel.setLayout(new GridBagLayout());
/*      */     
/* 1872 */     JButton jButton1 = new JButton();
/* 1873 */     ToolWindow.configureButton(jButton1, "Add.Principal");
/* 1874 */     jButton1
/* 1875 */       .addActionListener(new AddPrinButtonListener(this.tool, this.tw, this, paramBoolean));
/* 1876 */     this.tw.addNewComponent(jPanel, jButton1, 0, 0, 0, 1, 1, 100.0D, 0.0D, 2);
/*      */ 
/*      */     
/* 1879 */     jButton1 = new JButton();
/* 1880 */     ToolWindow.configureButton(jButton1, "Edit.Principal");
/* 1881 */     jButton1.addActionListener(new EditPrinButtonListener(this.tool, this.tw, this, paramBoolean));
/*      */     
/* 1883 */     this.tw.addNewComponent(jPanel, jButton1, 1, 1, 0, 1, 1, 100.0D, 0.0D, 2);
/*      */ 
/*      */     
/* 1886 */     jButton1 = new JButton();
/* 1887 */     ToolWindow.configureButton(jButton1, "Remove.Principal");
/* 1888 */     jButton1.addActionListener(new RemovePrinButtonListener(this.tool, this.tw, this, paramBoolean));
/*      */     
/* 1890 */     this.tw.addNewComponent(jPanel, jButton1, 2, 2, 0, 1, 1, 100.0D, 0.0D, 2);
/*      */ 
/*      */     
/* 1893 */     this.tw.addNewComponent(this, jPanel, 4, 1, 2, 1, 1, 0.0D, 0.0D, 2, ToolWindow.LITE_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1898 */     jLabel = new JLabel();
/* 1899 */     this.tw.addNewComponent(this, jLabel, 5, 0, 3, 1, 1, 0.0D, 0.0D, 1, ToolWindow.R_BOTTOM_PADDING);
/*      */ 
/*      */     
/* 1902 */     JScrollPane jScrollPane = new JScrollPane(taggedList1);
/* 1903 */     ToolWindow.configureLabelFor(jLabel, jScrollPane, "Principals.");
/* 1904 */     this.tw.addNewComponent(this, jScrollPane, 6, 1, 3, 3, 1, 0.0D, taggedList1
/* 1905 */         .getVisibleRowCount(), 1, ToolWindow.BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */     
/* 1909 */     jPanel = new JPanel();
/* 1910 */     jPanel.setLayout(new GridBagLayout());
/*      */     
/* 1912 */     jButton1 = new JButton();
/* 1913 */     ToolWindow.configureButton(jButton1, ".Add.Permission");
/* 1914 */     jButton1.addActionListener(new AddPermButtonListener(this.tool, this.tw, this, paramBoolean));
/*      */     
/* 1916 */     this.tw.addNewComponent(jPanel, jButton1, 0, 0, 0, 1, 1, 100.0D, 0.0D, 2);
/*      */ 
/*      */     
/* 1919 */     jButton1 = new JButton();
/* 1920 */     ToolWindow.configureButton(jButton1, ".Edit.Permission");
/* 1921 */     jButton1.addActionListener(new EditPermButtonListener(this.tool, this.tw, this, paramBoolean));
/*      */     
/* 1923 */     this.tw.addNewComponent(jPanel, jButton1, 1, 1, 0, 1, 1, 100.0D, 0.0D, 2);
/*      */ 
/*      */ 
/*      */     
/* 1927 */     jButton1 = new JButton();
/* 1928 */     ToolWindow.configureButton(jButton1, "Remove.Permission");
/* 1929 */     jButton1.addActionListener(new RemovePermButtonListener(this.tool, this.tw, this, paramBoolean));
/*      */     
/* 1931 */     this.tw.addNewComponent(jPanel, jButton1, 2, 2, 0, 1, 1, 100.0D, 0.0D, 2);
/*      */ 
/*      */     
/* 1934 */     this.tw.addNewComponent(this, jPanel, 7, 0, 4, 2, 1, 0.0D, 0.0D, 2, ToolWindow.LITE_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1939 */     jScrollPane = new JScrollPane(taggedList2);
/* 1940 */     this.tw.addNewComponent(this, jScrollPane, 8, 0, 5, 3, 1, 0.0D, taggedList2
/* 1941 */         .getVisibleRowCount(), 1, ToolWindow.BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1946 */     jPanel = new JPanel();
/* 1947 */     jPanel.setLayout(new GridBagLayout());
/*      */ 
/*      */     
/* 1950 */     JButton jButton2 = new JButton(PolicyTool.getMessage("Done"));
/* 1951 */     jButton2
/* 1952 */       .addActionListener(new AddEntryDoneButtonListener(this.tool, this.tw, this, paramBoolean));
/* 1953 */     this.tw.addNewComponent(jPanel, jButton2, 0, 0, 0, 1, 1, 0.0D, 0.0D, 3, ToolWindow.LR_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1958 */     JButton jButton3 = new JButton(PolicyTool.getMessage("Cancel"));
/* 1959 */     CancelButtonListener cancelButtonListener = new CancelButtonListener(this);
/* 1960 */     jButton3.addActionListener(cancelButtonListener);
/* 1961 */     this.tw.addNewComponent(jPanel, jButton3, 1, 1, 0, 1, 1, 0.0D, 0.0D, 3, ToolWindow.LR_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1966 */     this.tw.addNewComponent(this, jPanel, 9, 0, 6, 2, 1, 0.0D, 0.0D, 3);
/*      */ 
/*      */     
/* 1969 */     getRootPane().setDefaultButton(jButton2);
/* 1970 */     getRootPane().registerKeyboardAction(cancelButtonListener, escKey, 2);
/*      */     
/* 1972 */     pack();
/* 1973 */     setLocationRelativeTo(this.tw);
/* 1974 */     setVisible(true);
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
/*      */   PolicyEntry getPolicyEntryFromDialog() throws InvalidParameterException, MalformedURLException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, CertificateException, IOException, Exception {
/* 1988 */     JTextField jTextField = (JTextField)getComponent(1);
/* 1989 */     String str1 = null;
/* 1990 */     if (!jTextField.getText().trim().equals("")) {
/* 1991 */       str1 = new String(jTextField.getText().trim());
/*      */     }
/*      */     
/* 1994 */     jTextField = (JTextField)getComponent(3);
/* 1995 */     String str2 = null;
/* 1996 */     if (!jTextField.getText().trim().equals("")) {
/* 1997 */       str2 = new String(jTextField.getText().trim());
/*      */     }
/*      */     
/* 2000 */     PolicyParser.GrantEntry grantEntry = new PolicyParser.GrantEntry(str2, str1);
/*      */ 
/*      */ 
/*      */     
/* 2004 */     LinkedList<PolicyParser.PrincipalEntry> linkedList = new LinkedList();
/* 2005 */     TaggedList taggedList1 = (TaggedList)getComponent(6);
/* 2006 */     for (byte b1 = 0; b1 < taggedList1.getModel().getSize(); b1++) {
/* 2007 */       linkedList.add((PolicyParser.PrincipalEntry)taggedList1.getObject(b1));
/*      */     }
/* 2009 */     grantEntry.principals = linkedList;
/*      */ 
/*      */     
/* 2012 */     Vector<PolicyParser.PermissionEntry> vector = new Vector();
/* 2013 */     TaggedList taggedList2 = (TaggedList)getComponent(8);
/* 2014 */     for (byte b2 = 0; b2 < taggedList2.getModel().getSize(); b2++) {
/* 2015 */       vector.addElement((PolicyParser.PermissionEntry)taggedList2.getObject(b2));
/*      */     }
/* 2017 */     grantEntry.permissionEntries = vector;
/*      */ 
/*      */     
/* 2020 */     return new PolicyEntry(this.tool, grantEntry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void keyStoreDialog(int paramInt) {
/* 2031 */     Point point = this.tw.getLocationOnScreen();
/*      */     
/* 2033 */     setLayout(new GridBagLayout());
/*      */     
/* 2035 */     if (paramInt == 0) {
/*      */ 
/*      */       
/* 2038 */       JLabel jLabel = new JLabel();
/* 2039 */       this.tw.addNewComponent(this, jLabel, 0, 0, 0, 1, 1, 0.0D, 0.0D, 1, ToolWindow.R_BOTTOM_PADDING);
/*      */ 
/*      */       
/* 2042 */       JTextField jTextField = new JTextField(this.tool.getKeyStoreName(), 30);
/* 2043 */       ToolWindow.configureLabelFor(jLabel, jTextField, "KeyStore.URL.");
/* 2044 */       jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/*      */ 
/*      */       
/* 2047 */       jTextField.getAccessibleContext().setAccessibleName(
/* 2048 */           PolicyTool.getMessage("KeyStore.U.R.L."));
/* 2049 */       this.tw.addNewComponent(this, jTextField, 1, 1, 0, 1, 1, 1.0D, 0.0D, 1, ToolWindow.BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2054 */       jLabel = new JLabel();
/* 2055 */       this.tw.addNewComponent(this, jLabel, 2, 0, 1, 1, 1, 0.0D, 0.0D, 1, ToolWindow.R_BOTTOM_PADDING);
/*      */ 
/*      */       
/* 2058 */       jTextField = new JTextField(this.tool.getKeyStoreType(), 30);
/* 2059 */       ToolWindow.configureLabelFor(jLabel, jTextField, "KeyStore.Type.");
/* 2060 */       jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 2061 */       jTextField.getAccessibleContext().setAccessibleName(
/* 2062 */           PolicyTool.getMessage("KeyStore.Type."));
/* 2063 */       this.tw.addNewComponent(this, jTextField, 3, 1, 1, 1, 1, 1.0D, 0.0D, 1, ToolWindow.BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2068 */       jLabel = new JLabel();
/* 2069 */       this.tw.addNewComponent(this, jLabel, 4, 0, 2, 1, 1, 0.0D, 0.0D, 1, ToolWindow.R_BOTTOM_PADDING);
/*      */ 
/*      */       
/* 2072 */       jTextField = new JTextField(this.tool.getKeyStoreProvider(), 30);
/* 2073 */       ToolWindow.configureLabelFor(jLabel, jTextField, "KeyStore.Provider.");
/* 2074 */       jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 2075 */       jTextField.getAccessibleContext().setAccessibleName(
/* 2076 */           PolicyTool.getMessage("KeyStore.Provider."));
/* 2077 */       this.tw.addNewComponent(this, jTextField, 5, 1, 2, 1, 1, 1.0D, 0.0D, 1, ToolWindow.BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2082 */       jLabel = new JLabel();
/* 2083 */       this.tw.addNewComponent(this, jLabel, 6, 0, 3, 1, 1, 0.0D, 0.0D, 1, ToolWindow.R_BOTTOM_PADDING);
/*      */ 
/*      */       
/* 2086 */       jTextField = new JTextField(this.tool.getKeyStorePwdURL(), 30);
/* 2087 */       ToolWindow.configureLabelFor(jLabel, jTextField, "KeyStore.Password.URL.");
/* 2088 */       jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 2089 */       jTextField.getAccessibleContext().setAccessibleName(
/* 2090 */           PolicyTool.getMessage("KeyStore.Password.U.R.L."));
/* 2091 */       this.tw.addNewComponent(this, jTextField, 7, 1, 3, 1, 1, 1.0D, 0.0D, 1, ToolWindow.BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2096 */       JButton jButton1 = new JButton(PolicyTool.getMessage("OK"));
/* 2097 */       jButton1
/* 2098 */         .addActionListener(new ChangeKeyStoreOKButtonListener(this.tool, this.tw, this));
/* 2099 */       this.tw.addNewComponent(this, jButton1, 8, 0, 4, 1, 1, 0.0D, 0.0D, 3);
/*      */ 
/*      */ 
/*      */       
/* 2103 */       JButton jButton2 = new JButton(PolicyTool.getMessage("Cancel"));
/* 2104 */       CancelButtonListener cancelButtonListener = new CancelButtonListener(this);
/* 2105 */       jButton2.addActionListener(cancelButtonListener);
/* 2106 */       this.tw.addNewComponent(this, jButton2, 9, 1, 4, 1, 1, 0.0D, 0.0D, 3);
/*      */ 
/*      */       
/* 2109 */       getRootPane().setDefaultButton(jButton1);
/* 2110 */       getRootPane().registerKeyboardAction(cancelButtonListener, escKey, 2);
/*      */     } 
/*      */     
/* 2113 */     pack();
/* 2114 */     setLocationRelativeTo(this.tw);
/* 2115 */     setVisible(true);
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
/*      */   void displayPrincipalDialog(boolean paramBoolean1, boolean paramBoolean2) {
/* 2129 */     PolicyParser.PrincipalEntry principalEntry = null;
/*      */ 
/*      */     
/* 2132 */     TaggedList taggedList = (TaggedList)getComponent(6);
/* 2133 */     int i = taggedList.getSelectedIndex();
/*      */     
/* 2135 */     if (paramBoolean2) {
/* 2136 */       principalEntry = (PolicyParser.PrincipalEntry)taggedList.getObject(i);
/*      */     }
/*      */ 
/*      */     
/* 2140 */     ToolDialog toolDialog = new ToolDialog(PolicyTool.getMessage("Principals"), this.tool, this.tw, true);
/* 2141 */     toolDialog.addWindowListener(new ChildWindowListener(toolDialog));
/*      */ 
/*      */     
/* 2144 */     Point point = getLocationOnScreen();
/*      */     
/* 2146 */     toolDialog.setLayout(new GridBagLayout());
/* 2147 */     toolDialog.setResizable(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2152 */     JLabel jLabel = paramBoolean2 ? new JLabel(PolicyTool.getMessage(".Edit.Principal.")) : new JLabel(PolicyTool.getMessage(".Add.New.Principal."));
/* 2153 */     this.tw.addNewComponent(toolDialog, jLabel, 0, 0, 0, 1, 1, 0.0D, 0.0D, 1, ToolWindow.TOP_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2158 */     JComboBox<String> jComboBox = new JComboBox();
/* 2159 */     jComboBox.addItem(PRIN_TYPE);
/* 2160 */     jComboBox.getAccessibleContext().setAccessibleName(PRIN_TYPE);
/* 2161 */     for (byte b = 0; b < PRIN_ARRAY.size(); b++) {
/* 2162 */       Prin prin = PRIN_ARRAY.get(b);
/* 2163 */       jComboBox.addItem(prin.CLASS);
/*      */     } 
/*      */     
/* 2166 */     if (paramBoolean2) {
/* 2167 */       if ("WILDCARD_PRINCIPAL_CLASS"
/* 2168 */         .equals(principalEntry.getPrincipalClass())) {
/* 2169 */         jComboBox.setSelectedItem(PRIN_TYPE);
/*      */       } else {
/* 2171 */         Prin prin = getPrin(principalEntry.getPrincipalClass(), true);
/* 2172 */         if (prin != null) {
/* 2173 */           jComboBox.setSelectedItem(prin.CLASS);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 2178 */     jComboBox.addItemListener(new PrincipalTypeMenuListener(toolDialog));
/*      */     
/* 2180 */     this.tw.addNewComponent(toolDialog, jComboBox, 1, 0, 1, 1, 1, 0.0D, 0.0D, 1, ToolWindow.LR_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2187 */     JTextField jTextField = paramBoolean2 ? new JTextField(principalEntry.getDisplayClass(), 30) : new JTextField(30);
/*      */     
/* 2189 */     jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 2190 */     jTextField.getAccessibleContext().setAccessibleName(PRIN_TYPE);
/* 2191 */     this.tw.addNewComponent(toolDialog, jTextField, 2, 1, 1, 1, 1, 1.0D, 0.0D, 1, ToolWindow.LR_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2196 */     jLabel = new JLabel(PRIN_NAME);
/*      */     
/* 2198 */     jTextField = paramBoolean2 ? new JTextField(principalEntry.getDisplayName(), 40) : new JTextField(40);
/*      */     
/* 2200 */     jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 2201 */     jTextField.getAccessibleContext().setAccessibleName(PRIN_NAME);
/*      */     
/* 2203 */     this.tw.addNewComponent(toolDialog, jLabel, 3, 0, 2, 1, 1, 0.0D, 0.0D, 1, ToolWindow.LR_PADDING);
/*      */ 
/*      */     
/* 2206 */     this.tw.addNewComponent(toolDialog, jTextField, 4, 1, 2, 1, 1, 1.0D, 0.0D, 1, ToolWindow.LR_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2211 */     JButton jButton1 = new JButton(PolicyTool.getMessage("OK"));
/* 2212 */     jButton1.addActionListener(new NewPolicyPrinOKButtonListener(this.tool, this.tw, this, toolDialog, paramBoolean2));
/*      */ 
/*      */     
/* 2215 */     this.tw.addNewComponent(toolDialog, jButton1, 5, 0, 3, 1, 1, 0.0D, 0.0D, 3, ToolWindow.TOP_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */     
/* 2219 */     JButton jButton2 = new JButton(PolicyTool.getMessage("Cancel"));
/* 2220 */     CancelButtonListener cancelButtonListener = new CancelButtonListener(toolDialog);
/* 2221 */     jButton2.addActionListener(cancelButtonListener);
/* 2222 */     this.tw.addNewComponent(toolDialog, jButton2, 6, 1, 3, 1, 1, 0.0D, 0.0D, 3, ToolWindow.TOP_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */     
/* 2226 */     toolDialog.getRootPane().setDefaultButton(jButton1);
/* 2227 */     toolDialog.getRootPane().registerKeyboardAction(cancelButtonListener, escKey, 2);
/*      */     
/* 2229 */     toolDialog.pack();
/* 2230 */     toolDialog.setLocationRelativeTo(this.tw);
/* 2231 */     toolDialog.setVisible(true);
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
/*      */   void displayPermissionDialog(boolean paramBoolean1, boolean paramBoolean2) {
/* 2245 */     PolicyParser.PermissionEntry permissionEntry = null;
/*      */ 
/*      */     
/* 2248 */     TaggedList taggedList = (TaggedList)getComponent(8);
/* 2249 */     int i = taggedList.getSelectedIndex();
/*      */     
/* 2251 */     if (paramBoolean2) {
/* 2252 */       permissionEntry = (PolicyParser.PermissionEntry)taggedList.getObject(i);
/*      */     }
/*      */ 
/*      */     
/* 2256 */     ToolDialog toolDialog = new ToolDialog(PolicyTool.getMessage("Permissions"), this.tool, this.tw, true);
/* 2257 */     toolDialog.addWindowListener(new ChildWindowListener(toolDialog));
/*      */ 
/*      */     
/* 2260 */     Point point = getLocationOnScreen();
/*      */     
/* 2262 */     toolDialog.setLayout(new GridBagLayout());
/* 2263 */     toolDialog.setResizable(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2268 */     JLabel jLabel = paramBoolean2 ? new JLabel(PolicyTool.getMessage(".Edit.Permission.")) : new JLabel(PolicyTool.getMessage(".Add.New.Permission."));
/* 2269 */     this.tw.addNewComponent(toolDialog, jLabel, 0, 0, 0, 1, 1, 0.0D, 0.0D, 1, ToolWindow.TOP_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2274 */     JComboBox<String> jComboBox = new JComboBox();
/* 2275 */     jComboBox.addItem(PERM);
/* 2276 */     jComboBox.getAccessibleContext().setAccessibleName(PERM);
/* 2277 */     for (byte b = 0; b < PERM_ARRAY.size(); b++) {
/* 2278 */       Perm perm = PERM_ARRAY.get(b);
/* 2279 */       jComboBox.addItem(perm.CLASS);
/*      */     } 
/* 2281 */     this.tw.addNewComponent(toolDialog, jComboBox, 1, 0, 1, 1, 1, 0.0D, 0.0D, 1, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2287 */     JTextField jTextField = paramBoolean2 ? new JTextField(permissionEntry.permission, 30) : new JTextField(30);
/* 2288 */     jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 2289 */     jTextField.getAccessibleContext().setAccessibleName(PERM);
/* 2290 */     if (paramBoolean2) {
/* 2291 */       Perm perm = getPerm(permissionEntry.permission, true);
/* 2292 */       if (perm != null) {
/* 2293 */         jComboBox.setSelectedItem(perm.CLASS);
/*      */       }
/*      */     } 
/* 2296 */     this.tw.addNewComponent(toolDialog, jTextField, 2, 1, 1, 1, 1, 1.0D, 0.0D, 1, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */     
/* 2299 */     jComboBox.addItemListener(new PermissionMenuListener(toolDialog));
/*      */ 
/*      */     
/* 2302 */     jComboBox = new JComboBox<>();
/* 2303 */     jComboBox.addItem(PERM_NAME);
/* 2304 */     jComboBox.getAccessibleContext().setAccessibleName(PERM_NAME);
/* 2305 */     jTextField = paramBoolean2 ? new JTextField(permissionEntry.name, 40) : new JTextField(40);
/* 2306 */     jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 2307 */     jTextField.getAccessibleContext().setAccessibleName(PERM_NAME);
/* 2308 */     if (paramBoolean2) {
/* 2309 */       setPermissionNames(getPerm(permissionEntry.permission, true), jComboBox, jTextField);
/*      */     }
/* 2311 */     this.tw.addNewComponent(toolDialog, jComboBox, 3, 0, 2, 1, 1, 0.0D, 0.0D, 1, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */     
/* 2314 */     this.tw.addNewComponent(toolDialog, jTextField, 4, 1, 2, 1, 1, 1.0D, 0.0D, 1, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */     
/* 2317 */     jComboBox.addItemListener(new PermissionNameMenuListener(toolDialog));
/*      */ 
/*      */     
/* 2320 */     jComboBox = new JComboBox<>();
/* 2321 */     jComboBox.addItem(PERM_ACTIONS);
/* 2322 */     jComboBox.getAccessibleContext().setAccessibleName(PERM_ACTIONS);
/* 2323 */     jTextField = paramBoolean2 ? new JTextField(permissionEntry.action, 40) : new JTextField(40);
/* 2324 */     jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 2325 */     jTextField.getAccessibleContext().setAccessibleName(PERM_ACTIONS);
/* 2326 */     if (paramBoolean2) {
/* 2327 */       setPermissionActions(getPerm(permissionEntry.permission, true), jComboBox, jTextField);
/*      */     }
/* 2329 */     this.tw.addNewComponent(toolDialog, jComboBox, 5, 0, 3, 1, 1, 0.0D, 0.0D, 1, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */     
/* 2332 */     this.tw.addNewComponent(toolDialog, jTextField, 6, 1, 3, 1, 1, 1.0D, 0.0D, 1, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */     
/* 2335 */     jComboBox.addItemListener(new PermissionActionsMenuListener(toolDialog));
/*      */ 
/*      */     
/* 2338 */     jLabel = new JLabel(PolicyTool.getMessage("Signed.By."));
/* 2339 */     this.tw.addNewComponent(toolDialog, jLabel, 7, 0, 4, 1, 1, 0.0D, 0.0D, 1, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */     
/* 2342 */     jTextField = paramBoolean2 ? new JTextField(permissionEntry.signedBy, 40) : new JTextField(40);
/* 2343 */     jTextField.setPreferredSize(new Dimension((jTextField.getPreferredSize()).width, TEXTFIELD_HEIGHT));
/* 2344 */     jTextField.getAccessibleContext().setAccessibleName(
/* 2345 */         PolicyTool.getMessage("Signed.By."));
/* 2346 */     this.tw.addNewComponent(toolDialog, jTextField, 8, 1, 4, 1, 1, 1.0D, 0.0D, 1, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2351 */     JButton jButton1 = new JButton(PolicyTool.getMessage("OK"));
/* 2352 */     jButton1.addActionListener(new NewPolicyPermOKButtonListener(this.tool, this.tw, this, toolDialog, paramBoolean2));
/*      */ 
/*      */     
/* 2355 */     this.tw.addNewComponent(toolDialog, jButton1, 9, 0, 5, 1, 1, 0.0D, 0.0D, 3, ToolWindow.TOP_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2360 */     JButton jButton2 = new JButton(PolicyTool.getMessage("Cancel"));
/* 2361 */     CancelButtonListener cancelButtonListener = new CancelButtonListener(toolDialog);
/* 2362 */     jButton2.addActionListener(cancelButtonListener);
/* 2363 */     this.tw.addNewComponent(toolDialog, jButton2, 10, 1, 5, 1, 1, 0.0D, 0.0D, 3, ToolWindow.TOP_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */     
/* 2367 */     toolDialog.getRootPane().setDefaultButton(jButton1);
/* 2368 */     toolDialog.getRootPane().registerKeyboardAction(cancelButtonListener, escKey, 2);
/*      */     
/* 2370 */     toolDialog.pack();
/* 2371 */     toolDialog.setLocationRelativeTo(this.tw);
/* 2372 */     toolDialog.setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PolicyParser.PrincipalEntry getPrinFromDialog() throws Exception {
/* 2380 */     JTextField jTextField = (JTextField)getComponent(2);
/* 2381 */     String str1 = new String(jTextField.getText().trim());
/* 2382 */     jTextField = (JTextField)getComponent(4);
/* 2383 */     String str2 = new String(jTextField.getText().trim());
/* 2384 */     if (str1.equals("*")) {
/* 2385 */       str1 = "WILDCARD_PRINCIPAL_CLASS";
/*      */     }
/* 2387 */     if (str2.equals("*")) {
/* 2388 */       str2 = "WILDCARD_PRINCIPAL_NAME";
/*      */     }
/*      */     
/* 2391 */     Object object = null;
/*      */     
/* 2393 */     if (str1.equals("WILDCARD_PRINCIPAL_CLASS") && 
/* 2394 */       !str2.equals("WILDCARD_PRINCIPAL_NAME"))
/* 2395 */       throw new Exception(
/* 2396 */           PolicyTool.getMessage("Cannot.Specify.Principal.with.a.Wildcard.Class.without.a.Wildcard.Name")); 
/* 2397 */     if (str2.equals(""))
/* 2398 */       throw new Exception(
/* 2399 */           PolicyTool.getMessage("Cannot.Specify.Principal.without.a.Name")); 
/* 2400 */     if (str1.equals("")) {
/*      */ 
/*      */       
/* 2403 */       str1 = "PolicyParser.REPLACE_NAME";
/* 2404 */       this.tool.warnings.addElement("Warning: Principal name '" + str2 + "' specified without a Principal class.\n\t'" + str2 + "' will be interpreted as a key store alias.\n\tThe final principal class will be " + "javax.security.auth.x500.X500Principal" + ".\n\tThe final principal name will be determined by the following:\n\n\tIf the key store entry identified by '" + str2 + "'\n\tis a key entry, then the principal name will be\n\tthe subject distinguished name from the first\n\tcertificate in the entry's certificate chain.\n\n\tIf the key store entry identified by '" + str2 + "'\n\tis a trusted certificate entry, then the\n\tprincipal name will be the subject distinguished\n\tname from the trusted public key certificate.");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2425 */       this.tw.displayStatusDialog(this, "'" + str2 + "' will be interpreted as a key store alias.  View Warning Log for details.");
/*      */     } 
/*      */ 
/*      */     
/* 2429 */     return new PolicyParser.PrincipalEntry(str1, str2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   PolicyParser.PermissionEntry getPermFromDialog() {
/* 2438 */     JTextField jTextField = (JTextField)getComponent(2);
/* 2439 */     String str1 = new String(jTextField.getText().trim());
/* 2440 */     jTextField = (JTextField)getComponent(4);
/* 2441 */     String str2 = null;
/* 2442 */     if (!jTextField.getText().trim().equals(""))
/* 2443 */       str2 = new String(jTextField.getText().trim()); 
/* 2444 */     if (str1.equals("") || (
/* 2445 */       !str1.equals("java.security.AllPermission") && str2 == null)) {
/* 2446 */       throw new InvalidParameterException(
/* 2447 */           PolicyTool.getMessage("Permission.and.Target.Name.must.have.a.value"));
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
/* 2460 */     if (str1.equals("java.io.FilePermission") && str2.lastIndexOf("\\\\") > 0) {
/* 2461 */       char c = this.tw.displayYesNoDialog(this, 
/* 2462 */           PolicyTool.getMessage("Warning"), 
/* 2463 */           PolicyTool.getMessage("Warning.File.name.may.include.escaped.backslash.characters.It.is.not.necessary.to.escape.backslash.characters.the.tool.escapes"), 
/*      */           
/* 2465 */           PolicyTool.getMessage("Retain"), 
/* 2466 */           PolicyTool.getMessage("Edit"));
/*      */       
/* 2468 */       if (c != 'Y')
/*      */       {
/* 2470 */         throw new NoDisplayException();
/*      */       }
/*      */     } 
/*      */     
/* 2474 */     jTextField = (JTextField)getComponent(6);
/* 2475 */     String str3 = null;
/* 2476 */     if (!jTextField.getText().trim().equals("")) {
/* 2477 */       str3 = new String(jTextField.getText().trim());
/*      */     }
/*      */     
/* 2480 */     jTextField = (JTextField)getComponent(8);
/* 2481 */     String str4 = null;
/* 2482 */     if (!jTextField.getText().trim().equals("")) {
/* 2483 */       str4 = new String(jTextField.getText().trim());
/*      */     }
/* 2485 */     PolicyParser.PermissionEntry permissionEntry = new PolicyParser.PermissionEntry(str1, str2, str3);
/*      */     
/* 2487 */     permissionEntry.signedBy = str4;
/*      */ 
/*      */     
/* 2490 */     if (str4 != null) {
/* 2491 */       String[] arrayOfString = this.tool.parseSigners(permissionEntry.signedBy);
/* 2492 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*      */         try {
/* 2494 */           PublicKey publicKey = this.tool.getPublicKeyAlias(arrayOfString[b]);
/* 2495 */           if (publicKey == null) {
/*      */ 
/*      */             
/* 2498 */             MessageFormat messageFormat = new MessageFormat(PolicyTool.getMessage("Warning.A.public.key.for.alias.signers.i.does.not.exist.Make.sure.a.KeyStore.is.properly.configured."));
/* 2499 */             Object[] arrayOfObject = { arrayOfString[b] };
/* 2500 */             this.tool.warnings.addElement(messageFormat.format(arrayOfObject));
/* 2501 */             this.tw.displayStatusDialog(this, messageFormat.format(arrayOfObject));
/*      */           } 
/* 2503 */         } catch (Exception exception) {
/* 2504 */           this.tw.displayErrorDialog(this, exception);
/*      */         } 
/*      */       } 
/*      */     } 
/* 2508 */     return permissionEntry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void displayConfirmRemovePolicyEntry() {
/* 2517 */     JList jList = (JList)this.tw.getComponent(3);
/* 2518 */     int i = jList.getSelectedIndex();
/* 2519 */     PolicyEntry[] arrayOfPolicyEntry = this.tool.getEntry();
/*      */ 
/*      */     
/* 2522 */     Point point = this.tw.getLocationOnScreen();
/*      */     
/* 2524 */     setLayout(new GridBagLayout());
/*      */ 
/*      */ 
/*      */     
/* 2528 */     JLabel jLabel = new JLabel(PolicyTool.getMessage("Remove.this.Policy.Entry."));
/* 2529 */     this.tw.addNewComponent(this, jLabel, 0, 0, 0, 2, 1, 0.0D, 0.0D, 1, ToolWindow.BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2534 */     jLabel = new JLabel(arrayOfPolicyEntry[i].codebaseToString());
/* 2535 */     this.tw.addNewComponent(this, jLabel, 1, 0, 1, 2, 1, 0.0D, 0.0D, 1);
/*      */     
/* 2537 */     jLabel = new JLabel(arrayOfPolicyEntry[i].principalsToString().trim());
/* 2538 */     this.tw.addNewComponent(this, jLabel, 2, 0, 2, 2, 1, 0.0D, 0.0D, 1);
/*      */ 
/*      */     
/* 2541 */     Vector<PolicyParser.PermissionEntry> vector = (arrayOfPolicyEntry[i].getGrantEntry()).permissionEntries;
/* 2542 */     for (byte b = 0; b < vector.size(); b++) {
/* 2543 */       PolicyParser.PermissionEntry permissionEntry = vector.elementAt(b);
/* 2544 */       String str = PermissionEntryToUserFriendlyString(permissionEntry);
/* 2545 */       jLabel = new JLabel("    " + str);
/* 2546 */       if (b == vector.size() - 1) {
/* 2547 */         this.tw.addNewComponent(this, jLabel, 3 + b, 1, 3 + b, 1, 1, 0.0D, 0.0D, 1, ToolWindow.BOTTOM_PADDING);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 2552 */         this.tw.addNewComponent(this, jLabel, 3 + b, 1, 3 + b, 1, 1, 0.0D, 0.0D, 1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2560 */     JPanel jPanel = new JPanel();
/* 2561 */     jPanel.setLayout(new GridBagLayout());
/*      */ 
/*      */     
/* 2564 */     JButton jButton1 = new JButton(PolicyTool.getMessage("OK"));
/* 2565 */     jButton1
/* 2566 */       .addActionListener(new ConfirmRemovePolicyEntryOKButtonListener(this.tool, this.tw, this));
/* 2567 */     this.tw.addNewComponent(jPanel, jButton1, 0, 0, 0, 1, 1, 0.0D, 0.0D, 3, ToolWindow.LR_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2572 */     JButton jButton2 = new JButton(PolicyTool.getMessage("Cancel"));
/* 2573 */     CancelButtonListener cancelButtonListener = new CancelButtonListener(this);
/* 2574 */     jButton2.addActionListener(cancelButtonListener);
/* 2575 */     this.tw.addNewComponent(jPanel, jButton2, 1, 1, 0, 1, 1, 0.0D, 0.0D, 3, ToolWindow.LR_PADDING);
/*      */ 
/*      */ 
/*      */     
/* 2579 */     this.tw.addNewComponent(this, jPanel, 3 + vector.size(), 0, 3 + vector
/* 2580 */         .size(), 2, 1, 0.0D, 0.0D, 3, ToolWindow.TOP_BOTTOM_PADDING);
/*      */ 
/*      */     
/* 2583 */     getRootPane().setDefaultButton(jButton1);
/* 2584 */     getRootPane().registerKeyboardAction(cancelButtonListener, escKey, 2);
/*      */     
/* 2586 */     pack();
/* 2587 */     setLocationRelativeTo(this.tw);
/* 2588 */     setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void displaySaveAsDialog(int paramInt) {
/* 2598 */     FileDialog fileDialog = new FileDialog(this.tw, PolicyTool.getMessage("Save.As"), 1);
/* 2599 */     fileDialog.addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent param1WindowEvent) {
/* 2601 */             param1WindowEvent.getWindow().setVisible(false);
/*      */           }
/*      */         });
/* 2604 */     fileDialog.setVisible(true);
/*      */ 
/*      */     
/* 2607 */     if (fileDialog.getFile() == null || fileDialog
/* 2608 */       .getFile().equals("")) {
/*      */       return;
/*      */     }
/*      */     
/* 2612 */     File file = new File(fileDialog.getDirectory(), fileDialog.getFile());
/* 2613 */     String str = file.getPath();
/* 2614 */     fileDialog.dispose();
/*      */ 
/*      */     
/*      */     try {
/* 2618 */       this.tool.savePolicy(str);
/*      */ 
/*      */ 
/*      */       
/* 2622 */       MessageFormat messageFormat = new MessageFormat(PolicyTool.getMessage("Policy.successfully.written.to.filename"));
/* 2623 */       Object[] arrayOfObject = { str };
/* 2624 */       this.tw.displayStatusDialog((Window)null, messageFormat.format(arrayOfObject));
/*      */ 
/*      */ 
/*      */       
/* 2628 */       JTextField jTextField = (JTextField)this.tw.getComponent(1);
/* 2629 */       jTextField.setText(str);
/* 2630 */       this.tw.setVisible(true);
/*      */ 
/*      */ 
/*      */       
/* 2634 */       userSaveContinue(this.tool, this.tw, this, paramInt);
/*      */     }
/* 2636 */     catch (FileNotFoundException fileNotFoundException) {
/* 2637 */       if (str == null || str.equals("")) {
/* 2638 */         this.tw.displayErrorDialog((Window)null, new FileNotFoundException(
/* 2639 */               PolicyTool.getMessage("null.filename")));
/*      */       } else {
/* 2641 */         this.tw.displayErrorDialog((Window)null, fileNotFoundException);
/*      */       } 
/* 2643 */     } catch (Exception exception) {
/* 2644 */       this.tw.displayErrorDialog((Window)null, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void displayUserSave(int paramInt) {
/* 2653 */     if (this.tool.modified == true) {
/*      */ 
/*      */       
/* 2656 */       Point point = this.tw.getLocationOnScreen();
/*      */       
/* 2658 */       setLayout(new GridBagLayout());
/*      */ 
/*      */       
/* 2661 */       JLabel jLabel = new JLabel(PolicyTool.getMessage("Save.changes."));
/* 2662 */       this.tw.addNewComponent(this, jLabel, 0, 0, 0, 3, 1, 0.0D, 0.0D, 1, ToolWindow.L_TOP_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */       
/* 2666 */       JPanel jPanel = new JPanel();
/* 2667 */       jPanel.setLayout(new GridBagLayout());
/*      */       
/* 2669 */       JButton jButton1 = new JButton();
/* 2670 */       ToolWindow.configureButton(jButton1, "Yes");
/* 2671 */       jButton1
/* 2672 */         .addActionListener(new UserSaveYesButtonListener(this, this.tool, this.tw, paramInt));
/* 2673 */       this.tw.addNewComponent(jPanel, jButton1, 0, 0, 0, 1, 1, 0.0D, 0.0D, 3, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */       
/* 2677 */       JButton jButton2 = new JButton();
/* 2678 */       ToolWindow.configureButton(jButton2, "No");
/* 2679 */       jButton2
/* 2680 */         .addActionListener(new UserSaveNoButtonListener(this, this.tool, this.tw, paramInt));
/* 2681 */       this.tw.addNewComponent(jPanel, jButton2, 1, 1, 0, 1, 1, 0.0D, 0.0D, 3, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */       
/* 2685 */       JButton jButton3 = new JButton();
/* 2686 */       ToolWindow.configureButton(jButton3, "Cancel");
/* 2687 */       CancelButtonListener cancelButtonListener = new CancelButtonListener(this);
/* 2688 */       jButton3.addActionListener(cancelButtonListener);
/* 2689 */       this.tw.addNewComponent(jPanel, jButton3, 2, 2, 0, 1, 1, 0.0D, 0.0D, 3, ToolWindow.LR_BOTTOM_PADDING);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2694 */       this.tw.addNewComponent(this, jPanel, 1, 0, 1, 1, 1, 0.0D, 0.0D, 1);
/*      */ 
/*      */       
/* 2697 */       getRootPane().registerKeyboardAction(cancelButtonListener, escKey, 2);
/*      */       
/* 2699 */       pack();
/* 2700 */       setLocationRelativeTo(this.tw);
/* 2701 */       setVisible(true);
/*      */     } else {
/*      */       
/* 2704 */       userSaveContinue(this.tool, this.tw, this, paramInt);
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
/*      */   void userSaveContinue(PolicyTool paramPolicyTool, ToolWindow paramToolWindow, ToolDialog paramToolDialog, int paramInt) {
/*      */     JList jList;
/*      */     JTextField jTextField;
/*      */     FileDialog fileDialog;
/*      */     String str;
/* 2720 */     switch (paramInt) {
/*      */       
/*      */       case 1:
/* 2723 */         paramToolWindow.setVisible(false);
/* 2724 */         paramToolWindow.dispose();
/* 2725 */         System.exit(0);
/*      */ 
/*      */       
/*      */       case 2:
/*      */         try {
/* 2730 */           paramPolicyTool.openPolicy(null);
/* 2731 */         } catch (Exception exception) {
/* 2732 */           paramPolicyTool.modified = false;
/* 2733 */           paramToolWindow.displayErrorDialog((Window)null, exception);
/*      */         } 
/*      */ 
/*      */         
/* 2737 */         jList = new JList(new DefaultListModel());
/* 2738 */         jList.setVisibleRowCount(15);
/* 2739 */         jList.setSelectionMode(0);
/* 2740 */         jList.addMouseListener(new PolicyListListener(paramPolicyTool, paramToolWindow));
/* 2741 */         paramToolWindow.replacePolicyList(jList);
/*      */ 
/*      */         
/* 2744 */         jTextField = (JTextField)paramToolWindow.getComponent(1);
/*      */         
/* 2746 */         jTextField.setText("");
/* 2747 */         paramToolWindow.setVisible(true);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 2754 */         fileDialog = new FileDialog(paramToolWindow, PolicyTool.getMessage("Open"), 0);
/* 2755 */         fileDialog.addWindowListener(new WindowAdapter() {
/*      */               public void windowClosing(WindowEvent param1WindowEvent) {
/* 2757 */                 param1WindowEvent.getWindow().setVisible(false);
/*      */               }
/*      */             });
/* 2760 */         fileDialog.setVisible(true);
/*      */ 
/*      */         
/* 2763 */         if (fileDialog.getFile() == null || fileDialog
/* 2764 */           .getFile().equals("")) {
/*      */           return;
/*      */         }
/*      */         
/* 2768 */         str = (new File(fileDialog.getDirectory(), fileDialog.getFile())).getPath();
/*      */ 
/*      */         
/*      */         try {
/* 2772 */           paramPolicyTool.openPolicy(str);
/*      */ 
/*      */           
/* 2775 */           DefaultListModel<?> defaultListModel = new DefaultListModel();
/* 2776 */           jList = new JList(defaultListModel);
/* 2777 */           jList.setVisibleRowCount(15);
/* 2778 */           jList.setSelectionMode(0);
/* 2779 */           jList.addMouseListener(new PolicyListListener(paramPolicyTool, paramToolWindow));
/* 2780 */           PolicyEntry[] arrayOfPolicyEntry = paramPolicyTool.getEntry();
/* 2781 */           if (arrayOfPolicyEntry != null) {
/* 2782 */             for (byte b = 0; b < arrayOfPolicyEntry.length; b++) {
/* 2783 */               defaultListModel.addElement(arrayOfPolicyEntry[b].headerToString());
/*      */             }
/*      */           }
/* 2786 */           paramToolWindow.replacePolicyList(jList);
/* 2787 */           paramPolicyTool.modified = false;
/*      */ 
/*      */           
/* 2790 */           jTextField = (JTextField)paramToolWindow.getComponent(1);
/*      */           
/* 2792 */           jTextField.setText(str);
/* 2793 */           paramToolWindow.setVisible(true);
/*      */ 
/*      */           
/* 2796 */           if (paramPolicyTool.newWarning == true) {
/* 2797 */             paramToolWindow.displayStatusDialog((Window)null, 
/* 2798 */                 PolicyTool.getMessage("Errors.have.occurred.while.opening.the.policy.configuration.View.the.Warning.Log.for.more.information."));
/*      */           }
/*      */         }
/* 2801 */         catch (Exception exception) {
/*      */           
/* 2803 */           jList = new JList(new DefaultListModel());
/* 2804 */           jList.setVisibleRowCount(15);
/* 2805 */           jList.setSelectionMode(0);
/* 2806 */           jList.addMouseListener(new PolicyListListener(paramPolicyTool, paramToolWindow));
/* 2807 */           paramToolWindow.replacePolicyList(jList);
/* 2808 */           paramPolicyTool.setPolicyFileName(null);
/* 2809 */           paramPolicyTool.modified = false;
/*      */ 
/*      */           
/* 2812 */           jTextField = (JTextField)paramToolWindow.getComponent(1);
/*      */           
/* 2814 */           jTextField.setText("");
/* 2815 */           paramToolWindow.setVisible(true);
/*      */ 
/*      */ 
/*      */           
/* 2819 */           MessageFormat messageFormat = new MessageFormat(PolicyTool.getMessage("Could.not.open.policy.file.policyFile.e.toString."));
/* 2820 */           Object[] arrayOfObject = { str, exception.toString() };
/* 2821 */           paramToolWindow.displayErrorDialog((Window)null, messageFormat.format(arrayOfObject));
/*      */         } 
/*      */         break;
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
/*      */   void setPermissionNames(Perm paramPerm, JComboBox<String> paramJComboBox, JTextField paramJTextField) {
/* 2839 */     paramJComboBox.removeAllItems();
/* 2840 */     paramJComboBox.addItem(PERM_NAME);
/*      */     
/* 2842 */     if (paramPerm == null) {
/*      */       
/* 2844 */       paramJTextField.setEditable(true);
/* 2845 */     } else if (paramPerm.TARGETS == null) {
/*      */       
/* 2847 */       paramJTextField.setEditable(false);
/*      */     } else {
/*      */       
/* 2850 */       paramJTextField.setEditable(true);
/* 2851 */       for (byte b = 0; b < paramPerm.TARGETS.length; b++) {
/* 2852 */         paramJComboBox.addItem(paramPerm.TARGETS[b]);
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
/*      */   void setPermissionActions(Perm paramPerm, JComboBox<String> paramJComboBox, JTextField paramJTextField) {
/* 2869 */     paramJComboBox.removeAllItems();
/* 2870 */     paramJComboBox.addItem(PERM_ACTIONS);
/*      */     
/* 2872 */     if (paramPerm == null) {
/*      */       
/* 2874 */       paramJTextField.setEditable(true);
/* 2875 */     } else if (paramPerm.ACTIONS == null) {
/*      */       
/* 2877 */       paramJTextField.setEditable(false);
/*      */     } else {
/*      */       
/* 2880 */       paramJTextField.setEditable(true);
/* 2881 */       for (byte b = 0; b < paramPerm.ACTIONS.length; b++) {
/* 2882 */         paramJComboBox.addItem(paramPerm.ACTIONS[b]);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   static String PermissionEntryToUserFriendlyString(PolicyParser.PermissionEntry paramPermissionEntry) {
/* 2888 */     String str = paramPermissionEntry.permission;
/* 2889 */     if (paramPermissionEntry.name != null) {
/* 2890 */       str = str + " " + paramPermissionEntry.name;
/*      */     }
/* 2892 */     if (paramPermissionEntry.action != null) {
/* 2893 */       str = str + ", \"" + paramPermissionEntry.action + "\"";
/*      */     }
/* 2895 */     if (paramPermissionEntry.signedBy != null) {
/* 2896 */       str = str + ", signedBy " + paramPermissionEntry.signedBy;
/*      */     }
/* 2898 */     return str;
/*      */   }
/*      */   
/*      */   static String PrincipalEntryToUserFriendlyString(PolicyParser.PrincipalEntry paramPrincipalEntry) {
/* 2902 */     StringWriter stringWriter = new StringWriter();
/* 2903 */     PrintWriter printWriter = new PrintWriter(stringWriter);
/* 2904 */     paramPrincipalEntry.write(printWriter);
/* 2905 */     return stringWriter.toString();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/tools/policytool/ToolDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */