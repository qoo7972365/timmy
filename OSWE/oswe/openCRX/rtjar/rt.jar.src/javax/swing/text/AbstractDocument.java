/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectInputValidation;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.Bidi;
/*      */ import java.util.Dictionary;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.event.UndoableEditEvent;
/*      */ import javax.swing.event.UndoableEditListener;
/*      */ import javax.swing.tree.TreeNode;
/*      */ import javax.swing.undo.AbstractUndoableEdit;
/*      */ import javax.swing.undo.CannotRedoException;
/*      */ import javax.swing.undo.CannotUndoException;
/*      */ import javax.swing.undo.CompoundEdit;
/*      */ import javax.swing.undo.UndoableEdit;
/*      */ import sun.font.BidiUtils;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractDocument
/*      */   implements Document, Serializable
/*      */ {
/*      */   private transient int numReaders;
/*      */   private transient Thread currWriter;
/*      */   private transient int numWriters;
/*      */   private transient boolean notifyingListeners;
/*      */   private static Boolean defaultI18NProperty;
/*      */   private Dictionary<Object, Object> documentProperties;
/*      */   protected EventListenerList listenerList;
/*      */   private Content data;
/*      */   private AttributeContext context;
/*      */   private transient BranchElement bidiRoot;
/*      */   private DocumentFilter documentFilter;
/*      */   private transient DocumentFilter.FilterBypass filterBypass;
/*      */   private static final String BAD_LOCK_STATE = "document lock failure";
/*      */   protected static final String BAD_LOCATION = "document location failure";
/*      */   public static final String ParagraphElementName = "paragraph";
/*      */   public static final String ContentElementName = "content";
/*      */   public static final String SectionElementName = "section";
/*      */   public static final String BidiElementName = "bidi level";
/*      */   public static final String ElementNameAttribute = "$ename";
/*      */   static final String I18NProperty = "i18n";
/*      */   
/*      */   protected AbstractDocument(Content paramContent) {
/*  108 */     this(paramContent, StyleContext.getDefaultStyleContext());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractDocument(Content paramContent, AttributeContext paramAttributeContext) {
/* 1484 */     this.documentProperties = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1489 */     this.listenerList = new EventListenerList(); this.data = paramContent; this.context = paramAttributeContext; this.bidiRoot = new BidiRootElement(); if (defaultI18NProperty == null) { String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() { public String run() { return System.getProperty("i18n"); } }
/*      */         ); if (str != null) { defaultI18NProperty = Boolean.valueOf(str); }
/*      */       else { defaultI18NProperty = Boolean.FALSE; }
/*      */        }
/*      */      putProperty("i18n", defaultI18NProperty); writeLock(); try { Element[] arrayOfElement = new Element[1]; arrayOfElement[0] = new BidiElement(this.bidiRoot, 0, 1, 0); this.bidiRoot.replace(0, 0, arrayOfElement); }
/*      */     finally { writeUnlock(); }
/*      */   
/*      */   } public Dictionary<Object, Object> getDocumentProperties() { if (this.documentProperties == null)
/*      */       this.documentProperties = new Hashtable<>(2);  return this.documentProperties; } public void setDocumentProperties(Dictionary<Object, Object> paramDictionary) { this.documentProperties = paramDictionary; }
/*      */   protected void fireInsertUpdate(DocumentEvent paramDocumentEvent) { this.notifyingListeners = true; try { Object[] arrayOfObject = this.listenerList.getListenerList(); for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) { if (arrayOfObject[i] == DocumentListener.class)
/*      */           ((DocumentListener)arrayOfObject[i + 1]).insertUpdate(paramDocumentEvent);  }
/*      */        }
/*      */     finally { this.notifyingListeners = false; }
/*      */      }
/*      */   protected void fireChangedUpdate(DocumentEvent paramDocumentEvent) { this.notifyingListeners = true; try { Object[] arrayOfObject = this.listenerList.getListenerList(); for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) { if (arrayOfObject[i] == DocumentListener.class)
/*      */           ((DocumentListener)arrayOfObject[i + 1]).changedUpdate(paramDocumentEvent);  }
/*      */        }
/*      */     finally { this.notifyingListeners = false; }
/*      */      }
/*      */   protected void fireRemoveUpdate(DocumentEvent paramDocumentEvent) { this.notifyingListeners = true; try { Object[] arrayOfObject = this.listenerList.getListenerList(); for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) { if (arrayOfObject[i] == DocumentListener.class)
/*      */           ((DocumentListener)arrayOfObject[i + 1]).removeUpdate(paramDocumentEvent);  }
/*      */        }
/*      */     finally { this.notifyingListeners = false; }
/*      */      }
/*      */   protected void fireUndoableEditUpdate(UndoableEditEvent paramUndoableEditEvent) { Object[] arrayOfObject = this.listenerList.getListenerList(); for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) { if (arrayOfObject[i] == UndoableEditListener.class)
/*      */         ((UndoableEditListener)arrayOfObject[i + 1]).undoableEditHappened(paramUndoableEditEvent);  }
/*      */      }
/*      */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) { return this.listenerList.getListeners(paramClass); }
/*      */   public int getAsynchronousLoadPriority() { Integer integer = (Integer)getProperty("load priority"); if (integer != null)
/*      */       return integer.intValue();  return -1; }
/*      */   public void setAsynchronousLoadPriority(int paramInt) { Integer integer = (paramInt >= 0) ? Integer.valueOf(paramInt) : null; putProperty("load priority", integer); }
/*      */   public void setDocumentFilter(DocumentFilter paramDocumentFilter) { this.documentFilter = paramDocumentFilter; }
/*      */   public DocumentFilter getDocumentFilter() { return this.documentFilter; }
/*      */   public void render(Runnable paramRunnable) { readLock(); try { paramRunnable.run(); }
/*      */     finally { readUnlock(); }
/*      */      }
/*      */   public int getLength() { return this.data.length() - 1; }
/*      */   public void addDocumentListener(DocumentListener paramDocumentListener) { this.listenerList.add(DocumentListener.class, paramDocumentListener); }
/*      */   public void removeDocumentListener(DocumentListener paramDocumentListener) { this.listenerList.remove(DocumentListener.class, paramDocumentListener); }
/*      */   public DocumentListener[] getDocumentListeners() { return this.listenerList.<DocumentListener>getListeners(DocumentListener.class); }
/*      */   public void addUndoableEditListener(UndoableEditListener paramUndoableEditListener) { this.listenerList.add(UndoableEditListener.class, paramUndoableEditListener); }
/*      */   public void removeUndoableEditListener(UndoableEditListener paramUndoableEditListener) { this.listenerList.remove(UndoableEditListener.class, paramUndoableEditListener); }
/*      */   public UndoableEditListener[] getUndoableEditListeners() { return this.listenerList.<UndoableEditListener>getListeners(UndoableEditListener.class); }
/*      */   public final Object getProperty(Object paramObject) { return getDocumentProperties().get(paramObject); }
/*      */   public final void putProperty(Object paramObject1, Object paramObject2) { if (paramObject2 != null) { getDocumentProperties().put(paramObject1, paramObject2); }
/*      */     else { getDocumentProperties().remove(paramObject1); }
/*      */      if (paramObject1 == TextAttribute.RUN_DIRECTION && Boolean.TRUE.equals(getProperty("i18n"))) { writeLock(); try { DefaultDocumentEvent defaultDocumentEvent = new DefaultDocumentEvent(0, getLength(), DocumentEvent.EventType.INSERT); updateBidi(defaultDocumentEvent); }
/*      */       finally { writeUnlock(); }
/*      */        }
/*      */      }
/*      */   public void remove(int paramInt1, int paramInt2) throws BadLocationException { DocumentFilter documentFilter = getDocumentFilter(); writeLock(); try { if (documentFilter != null) { documentFilter.remove(getFilterBypass(), paramInt1, paramInt2); }
/*      */       else { handleRemove(paramInt1, paramInt2); }
/*      */        }
/*      */     finally { writeUnlock(); }
/*      */      }
/*      */   void handleRemove(int paramInt1, int paramInt2) throws BadLocationException { if (paramInt2 > 0) { if (paramInt1 < 0 || paramInt1 + paramInt2 > getLength())
/*      */         throw new BadLocationException("Invalid remove", getLength() + 1);  DefaultDocumentEvent defaultDocumentEvent = new DefaultDocumentEvent(paramInt1, paramInt2, DocumentEvent.EventType.REMOVE); boolean bool = Utilities.isComposedTextElement(this, paramInt1); removeUpdate(defaultDocumentEvent); UndoableEdit undoableEdit = this.data.remove(paramInt1, paramInt2); if (undoableEdit != null)
/*      */         defaultDocumentEvent.addEdit(undoableEdit);  postRemoveUpdate(defaultDocumentEvent); defaultDocumentEvent.end(); fireRemoveUpdate(defaultDocumentEvent); if (undoableEdit != null && !bool)
/*      */         fireUndoableEditUpdate(new UndoableEditEvent(this, defaultDocumentEvent));  }
/*      */      }
/*      */   public void replace(int paramInt1, int paramInt2, String paramString, AttributeSet paramAttributeSet) throws BadLocationException { if (paramInt2 == 0 && (paramString == null || paramString.length() == 0))
/*      */       return;  DocumentFilter documentFilter = getDocumentFilter(); writeLock(); try {
/*      */       if (documentFilter != null) {
/*      */         documentFilter.replace(getFilterBypass(), paramInt1, paramInt2, paramString, paramAttributeSet);
/*      */       } else {
/*      */         if (paramInt2 > 0)
/*      */           remove(paramInt1, paramInt2);  if (paramString != null && paramString.length() > 0)
/*      */           insertString(paramInt1, paramString, paramAttributeSet); 
/*      */       } 
/*      */     } finally {
/*      */       writeUnlock();
/*      */     }  }
/*      */   public void insertString(int paramInt, String paramString, AttributeSet paramAttributeSet) throws BadLocationException { if (paramString == null || paramString.length() == 0)
/*      */       return;  DocumentFilter documentFilter = getDocumentFilter(); writeLock(); try {
/*      */       if (documentFilter != null) {
/*      */         documentFilter.insertString(getFilterBypass(), paramInt, paramString, paramAttributeSet);
/*      */       } else {
/*      */         handleInsertString(paramInt, paramString, paramAttributeSet);
/*      */       } 
/*      */     } finally {
/*      */       writeUnlock();
/*      */     }  }
/* 1571 */   static final Object MultiByteProperty = "multiByte";
/*      */   private void handleInsertString(int paramInt, String paramString, AttributeSet paramAttributeSet) throws BadLocationException { if (paramString == null || paramString.length() == 0)
/*      */       return;  UndoableEdit undoableEdit = this.data.insertString(paramInt, paramString); DefaultDocumentEvent defaultDocumentEvent = new DefaultDocumentEvent(paramInt, paramString.length(), DocumentEvent.EventType.INSERT); if (undoableEdit != null)
/*      */       defaultDocumentEvent.addEdit(undoableEdit);  if (getProperty("i18n").equals(Boolean.FALSE)) {
/*      */       Object object = getProperty(TextAttribute.RUN_DIRECTION); if (object != null && object.equals(TextAttribute.RUN_DIRECTION_RTL)) {
/*      */         putProperty("i18n", Boolean.TRUE);
/*      */       } else {
/*      */         char[] arrayOfChar = paramString.toCharArray(); if (SwingUtilities2.isComplexLayout(arrayOfChar, 0, arrayOfChar.length))
/*      */           putProperty("i18n", Boolean.TRUE); 
/*      */       } 
/*      */     }  insertUpdate(defaultDocumentEvent, paramAttributeSet); defaultDocumentEvent.end(); fireInsertUpdate(defaultDocumentEvent); if (undoableEdit != null && (paramAttributeSet == null || !paramAttributeSet.isDefined(StyleConstants.ComposedTextAttribute)))
/*      */       fireUndoableEditUpdate(new UndoableEditEvent(this, defaultDocumentEvent));  }
/*      */   public String getText(int paramInt1, int paramInt2) throws BadLocationException { if (paramInt2 < 0)
/*      */       throw new BadLocationException("Length must be positive", paramInt2);  return this.data.getString(paramInt1, paramInt2); }
/*      */   public void getText(int paramInt1, int paramInt2, Segment paramSegment) throws BadLocationException { if (paramInt2 < 0)
/*      */       throw new BadLocationException("Length must be positive", paramInt2);  this.data.getChars(paramInt1, paramInt2, paramSegment); }
/*      */   public synchronized Position createPosition(int paramInt) throws BadLocationException { return this.data.createPosition(paramInt); }
/*      */   public final Position getStartPosition() { Position position; try {
/*      */       position = createPosition(0);
/*      */     } catch (BadLocationException badLocationException) {
/*      */       position = null;
/*      */     }  return position; }
/*      */   public final Position getEndPosition() { Position position; try {
/*      */       position = createPosition(this.data.length());
/*      */     } catch (BadLocationException badLocationException) {
/*      */       position = null;
/*      */     }  return position; }
/*      */   public Element[] getRootElements() { Element[] arrayOfElement = new Element[2]; arrayOfElement[0] = getDefaultRootElement(); arrayOfElement[1] = getBidiRootElement(); return arrayOfElement; }
/*      */   private DocumentFilter.FilterBypass getFilterBypass() { if (this.filterBypass == null)
/*      */       this.filterBypass = new DefaultFilterBypass();  return this.filterBypass; }
/*      */   public Element getBidiRootElement() { return this.bidiRoot; }
/*      */   static boolean isLeftToRight(Document paramDocument, int paramInt1, int paramInt2) { if (Boolean.TRUE.equals(paramDocument.getProperty("i18n")) && paramDocument instanceof AbstractDocument) {
/*      */       AbstractDocument abstractDocument = (AbstractDocument)paramDocument; Element element1 = abstractDocument.getBidiRootElement(); int i = element1.getElementIndex(paramInt1); Element element2 = element1.getElement(i); if (element2.getEndOffset() >= paramInt2) {
/*      */         AttributeSet attributeSet = element2.getAttributes(); return (StyleConstants.getBidiLevel(attributeSet) % 2 == 0);
/*      */       } 
/*      */     }  return true; }
/*      */   protected final AttributeContext getAttributeContext() { return this.context; }
/*      */   protected void insertUpdate(DefaultDocumentEvent paramDefaultDocumentEvent, AttributeSet paramAttributeSet) { if (getProperty("i18n").equals(Boolean.TRUE))
/*      */       updateBidi(paramDefaultDocumentEvent);  if (paramDefaultDocumentEvent.type == DocumentEvent.EventType.INSERT && paramDefaultDocumentEvent.getLength() > 0 && !Boolean.TRUE.equals(getProperty(MultiByteProperty))) {
/*      */       Segment segment = SegmentCache.getSharedSegment(); try {
/*      */         getText(paramDefaultDocumentEvent.getOffset(), paramDefaultDocumentEvent.getLength(), segment); segment.first(); do {
/*      */           if (segment.current() > 'ÿ') {
/*      */             putProperty(MultiByteProperty, Boolean.TRUE); break;
/*      */           } 
/*      */         } while (segment.next() != Character.MAX_VALUE);
/*      */       } catch (BadLocationException badLocationException) {} SegmentCache.releaseSharedSegment(segment);
/*      */     }  }
/*      */   protected void removeUpdate(DefaultDocumentEvent paramDefaultDocumentEvent) {}
/*      */   protected void postRemoveUpdate(DefaultDocumentEvent paramDefaultDocumentEvent) { if (getProperty("i18n").equals(Boolean.TRUE))
/*      */       updateBidi(paramDefaultDocumentEvent);  }
/*      */   void updateBidi(DefaultDocumentEvent paramDefaultDocumentEvent) { int i, j; if (paramDefaultDocumentEvent.type == DocumentEvent.EventType.INSERT || paramDefaultDocumentEvent.type == DocumentEvent.EventType.CHANGE) {
/*      */       int i4 = paramDefaultDocumentEvent.getOffset(); int i5 = i4 + paramDefaultDocumentEvent.getLength(); i = getParagraphElement(i4).getStartOffset(); j = getParagraphElement(i5).getEndOffset();
/*      */     } else if (paramDefaultDocumentEvent.type == DocumentEvent.EventType.REMOVE) {
/*      */       Element element = getParagraphElement(paramDefaultDocumentEvent.getOffset()); i = element.getStartOffset(); j = element.getEndOffset();
/*      */     } else {
/*      */       throw new Error("Internal error: unknown event type.");
/*      */     }  byte[] arrayOfByte = calculateBidiLevels(i, j); Vector<BidiElement> vector = new Vector(); int k = i; int m = 0; if (k > 0) {
/*      */       int i4 = this.bidiRoot.getElementIndex(i - 1); m = i4; Element element = this.bidiRoot.getElement(i4); int i5 = StyleConstants.getBidiLevel(element.getAttributes()); if (i5 == arrayOfByte[0]) {
/*      */         k = element.getStartOffset();
/*      */       } else if (element.getEndOffset() > i) {
/*      */         vector.addElement(new BidiElement(this.bidiRoot, element.getStartOffset(), i, i5));
/*      */       } else {
/*      */         m++;
/*      */       } 
/*      */     }  byte b1 = 0; while (b1 < arrayOfByte.length && arrayOfByte[b1] == arrayOfByte[0])
/*      */       b1++;  int n = j; BidiElement bidiElement = null; int i1 = this.bidiRoot.getElementCount() - 1; if (n <= getLength()) {
/*      */       int i4 = this.bidiRoot.getElementIndex(j); i1 = i4; Element element = this.bidiRoot.getElement(i4); int i5 = StyleConstants.getBidiLevel(element.getAttributes());
/*      */       if (i5 == arrayOfByte[arrayOfByte.length - 1]) {
/*      */         n = element.getEndOffset();
/*      */       } else if (element.getStartOffset() < j) {
/*      */         bidiElement = new BidiElement(this.bidiRoot, j, element.getEndOffset(), i5);
/*      */       } else {
/*      */         i1--;
/*      */       } 
/*      */     } 
/*      */     int i2 = arrayOfByte.length;
/*      */     while (i2 > b1 && arrayOfByte[i2 - 1] == arrayOfByte[arrayOfByte.length - 1])
/*      */       i2--; 
/*      */     if (b1 == i2 && arrayOfByte[0] == arrayOfByte[arrayOfByte.length - 1]) {
/*      */       vector.addElement(new BidiElement(this.bidiRoot, k, n, arrayOfByte[0]));
/*      */     } else {
/*      */       vector.addElement(new BidiElement(this.bidiRoot, k, b1 + i, arrayOfByte[0]));
/*      */       byte b;
/*      */       for (b = b1; b < i2; ) {
/*      */         byte b3;
/*      */         for (b3 = b; b3 < arrayOfByte.length && arrayOfByte[b3] == arrayOfByte[b]; b3++);
/*      */         vector.addElement(new BidiElement(this.bidiRoot, i + b, i + b3, arrayOfByte[b]));
/*      */         b = b3;
/*      */       } 
/*      */       vector.addElement(new BidiElement(this.bidiRoot, i2 + i, n, arrayOfByte[arrayOfByte.length - 1]));
/*      */     } 
/*      */     if (bidiElement != null)
/*      */       vector.addElement(bidiElement); 
/*      */     int i3 = 0;
/*      */     if (this.bidiRoot.getElementCount() > 0)
/*      */       i3 = i1 - m + 1; 
/*      */     Element[] arrayOfElement1 = new Element[i3];
/*      */     for (byte b2 = 0; b2 < i3; b2++)
/*      */       arrayOfElement1[b2] = this.bidiRoot.getElement(m + b2); 
/*      */     Element[] arrayOfElement2 = new Element[vector.size()];
/*      */     vector.copyInto((Object[])arrayOfElement2);
/*      */     ElementEdit elementEdit = new ElementEdit(this.bidiRoot, m, arrayOfElement1, arrayOfElement2);
/*      */     paramDefaultDocumentEvent.addEdit(elementEdit);
/*      */     this.bidiRoot.replace(m, arrayOfElement1.length, arrayOfElement2); }
/*      */   private byte[] calculateBidiLevels(int paramInt1, int paramInt2) { byte[] arrayOfByte = new byte[paramInt2 - paramInt1];
/*      */     int i = 0;
/*      */     Boolean bool = null;
/*      */     Object object = getProperty(TextAttribute.RUN_DIRECTION);
/*      */     if (object instanceof Boolean)
/*      */       bool = (Boolean)object; 
/*      */     for (int j = paramInt1; j < paramInt2; ) {
/*      */       Element element = getParagraphElement(j);
/*      */       int k = element.getStartOffset();
/*      */       int m = element.getEndOffset();
/*      */       Boolean bool1 = bool;
/*      */       object = element.getAttributes().getAttribute(TextAttribute.RUN_DIRECTION);
/*      */       if (object instanceof Boolean)
/*      */         bool1 = (Boolean)object; 
/*      */       Segment segment = SegmentCache.getSharedSegment();
/*      */       try {
/*      */         getText(k, m - k, segment);
/*      */       } catch (BadLocationException badLocationException) {
/*      */         throw new Error("Internal error: " + badLocationException.toString());
/*      */       } 
/*      */       byte b = -2;
/*      */       if (bool1 != null)
/*      */         if (TextAttribute.RUN_DIRECTION_LTR.equals(bool1)) {
/*      */           b = 0;
/*      */         } else {
/*      */           b = 1;
/*      */         }  
/*      */       Bidi bidi = new Bidi(segment.array, segment.offset, null, 0, segment.count, b);
/*      */       BidiUtils.getLevels(bidi, arrayOfByte, i);
/*      */       i += bidi.getLength();
/*      */       j = element.getEndOffset();
/*      */       SegmentCache.releaseSharedSegment(segment);
/*      */     } 
/*      */     if (i != arrayOfByte.length)
/*      */       throw new Error("levelsEnd assertion failed."); 
/*      */     return arrayOfByte; }
/*      */   public void dump(PrintStream paramPrintStream) { Element element = getDefaultRootElement();
/*      */     if (element instanceof AbstractElement)
/*      */       ((AbstractElement)element).dump(paramPrintStream, 0); 
/*      */     this.bidiRoot.dump(paramPrintStream, 0); }
/*      */   protected final Content getContent() { return this.data; }
/*      */   protected Element createLeafElement(Element paramElement, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) { return new LeafElement(paramElement, paramAttributeSet, paramInt1, paramInt2); }
/*      */   protected Element createBranchElement(Element paramElement, AttributeSet paramAttributeSet) { return new BranchElement(paramElement, paramAttributeSet); }
/*      */   protected final synchronized Thread getCurrentWriter() { return this.currWriter; }
/*      */   protected final synchronized void writeLock() { try {
/*      */       while (this.numReaders > 0 || this.currWriter != null) {
/*      */         if (Thread.currentThread() == this.currWriter) {
/*      */           if (this.notifyingListeners)
/*      */             throw new IllegalStateException("Attempt to mutate in notification"); 
/*      */           this.numWriters++;
/*      */           return;
/*      */         } 
/*      */         wait();
/*      */       } 
/*      */       this.currWriter = Thread.currentThread();
/*      */       this.numWriters = 1;
/*      */     } catch (InterruptedException interruptedException) {
/*      */       throw new Error("Interrupted attempt to acquire write lock");
/*      */     }  } protected final synchronized void writeUnlock() { if (--this.numWriters <= 0) {
/*      */       this.numWriters = 0;
/*      */       this.currWriter = null;
/*      */       notifyAll();
/*      */     }  } public final synchronized void readLock() { try {
/*      */       while (this.currWriter != null) {
/*      */         if (this.currWriter == Thread.currentThread())
/*      */           return; 
/*      */         wait();
/*      */       } 
/*      */       this.numReaders++;
/*      */     } catch (InterruptedException interruptedException) {
/*      */       throw new Error("Interrupted attempt to acquire read lock");
/*      */     }  } public final synchronized void readUnlock() { if (this.currWriter == Thread.currentThread())
/*      */       return; 
/*      */     if (this.numReaders <= 0)
/*      */       throw new StateInvariantError("document lock failure"); 
/*      */     this.numReaders--;
/*      */     notify(); } private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException { paramObjectInputStream.defaultReadObject();
/*      */     this.listenerList = new EventListenerList();
/*      */     this.bidiRoot = new BidiRootElement();
/*      */     try {
/*      */       writeLock();
/*      */       Element[] arrayOfElement = new Element[1];
/*      */       arrayOfElement[0] = new BidiElement(this.bidiRoot, 0, 1, 0);
/*      */       this.bidiRoot.replace(0, 0, arrayOfElement);
/*      */     } finally {
/*      */       writeUnlock();
/*      */     } 
/*      */     paramObjectInputStream.registerValidation(new ObjectInputValidation() { public void validateObject() { try {
/*      */               AbstractDocument.this.writeLock();
/*      */               AbstractDocument.DefaultDocumentEvent defaultDocumentEvent = new AbstractDocument.DefaultDocumentEvent(0, AbstractDocument.this.getLength(), DocumentEvent.EventType.INSERT);
/*      */               AbstractDocument.this.updateBidi(defaultDocumentEvent);
/*      */             } finally {
/*      */               AbstractDocument.this.writeUnlock();
/*      */             }  } }
/*      */         0); } static final String AsyncLoadPriority = "load priority"; public abstract Element getDefaultRootElement(); public abstract Element getParagraphElement(int paramInt); public static interface Content {
/*      */     Position createPosition(int param1Int) throws BadLocationException; int length(); UndoableEdit insertString(int param1Int, String param1String) throws BadLocationException; UndoableEdit remove(int param1Int1, int param1Int2) throws BadLocationException; String getString(int param1Int1, int param1Int2) throws BadLocationException; void getChars(int param1Int1, int param1Int2, Segment param1Segment) throws BadLocationException;
/*      */   } public static interface AttributeContext {
/*      */     AttributeSet addAttribute(AttributeSet param1AttributeSet, Object param1Object1, Object param1Object2); AttributeSet addAttributes(AttributeSet param1AttributeSet1, AttributeSet param1AttributeSet2); AttributeSet removeAttribute(AttributeSet param1AttributeSet, Object param1Object); AttributeSet removeAttributes(AttributeSet param1AttributeSet, Enumeration<?> param1Enumeration); AttributeSet removeAttributes(AttributeSet param1AttributeSet1, AttributeSet param1AttributeSet2); AttributeSet getEmptySet(); void reclaim(AttributeSet param1AttributeSet);
/*      */   } public abstract class AbstractElement implements Element, MutableAttributeSet, Serializable, TreeNode {
/* 1774 */     private Element parent; public AbstractElement(Element param1Element, AttributeSet param1AttributeSet) { this.parent = param1Element;
/* 1775 */       this.attributes = AbstractDocument.this.getAttributeContext().getEmptySet();
/* 1776 */       if (param1AttributeSet != null)
/* 1777 */         addAttributes(param1AttributeSet);  }
/*      */     
/*      */     private transient AttributeSet attributes;
/*      */     
/*      */     private final void indent(PrintWriter param1PrintWriter, int param1Int) {
/* 1782 */       for (byte b = 0; b < param1Int; b++) {
/* 1783 */         param1PrintWriter.print("  ");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void dump(PrintStream param1PrintStream, int param1Int) {
/*      */       PrintWriter printWriter;
/*      */       try {
/* 1796 */         printWriter = new PrintWriter(new OutputStreamWriter(param1PrintStream, "JavaEsc"), true);
/*      */       }
/* 1798 */       catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 1799 */         printWriter = new PrintWriter(param1PrintStream, true);
/*      */       } 
/* 1801 */       indent(printWriter, param1Int);
/* 1802 */       if (getName() == null) {
/* 1803 */         printWriter.print("<??");
/*      */       } else {
/* 1805 */         printWriter.print("<" + getName());
/*      */       } 
/* 1807 */       if (getAttributeCount() > 0) {
/* 1808 */         printWriter.println("");
/*      */         
/* 1810 */         Enumeration<?> enumeration = this.attributes.getAttributeNames();
/* 1811 */         while (enumeration.hasMoreElements()) {
/* 1812 */           Object object = enumeration.nextElement();
/* 1813 */           indent(printWriter, param1Int + 1);
/* 1814 */           printWriter.println(object + "=" + getAttribute(object));
/*      */         } 
/* 1816 */         indent(printWriter, param1Int);
/*      */       } 
/* 1818 */       printWriter.println(">");
/*      */       
/* 1820 */       if (isLeaf()) {
/* 1821 */         indent(printWriter, param1Int + 1);
/* 1822 */         printWriter.print("[" + getStartOffset() + "," + getEndOffset() + "]");
/* 1823 */         AbstractDocument.Content content = AbstractDocument.this.getContent();
/*      */         try {
/* 1825 */           String str = content.getString(getStartOffset(), 
/* 1826 */               getEndOffset() - getStartOffset());
/* 1827 */           if (str.length() > 40) {
/* 1828 */             str = str.substring(0, 40) + "...";
/*      */           }
/* 1830 */           printWriter.println("[" + str + "]");
/* 1831 */         } catch (BadLocationException badLocationException) {}
/*      */       }
/*      */       else {
/*      */         
/* 1835 */         int i = getElementCount();
/* 1836 */         for (byte b = 0; b < i; b++) {
/* 1837 */           AbstractElement abstractElement = (AbstractElement)getElement(b);
/* 1838 */           abstractElement.dump(param1PrintStream, param1Int + 1);
/*      */         } 
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
/*      */     public int getAttributeCount() {
/* 1853 */       return this.attributes.getAttributeCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isDefined(Object param1Object) {
/* 1864 */       return this.attributes.isDefined(param1Object);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEqual(AttributeSet param1AttributeSet) {
/* 1875 */       return this.attributes.isEqual(param1AttributeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet copyAttributes() {
/* 1885 */       return this.attributes.copyAttributes();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getAttribute(Object param1Object) {
/* 1896 */       Object object = this.attributes.getAttribute(param1Object);
/* 1897 */       if (object == null) {
/*      */ 
/*      */ 
/*      */         
/* 1901 */         AttributeSet attributeSet = (this.parent != null) ? this.parent.getAttributes() : null;
/* 1902 */         if (attributeSet != null) {
/* 1903 */           object = attributeSet.getAttribute(param1Object);
/*      */         }
/*      */       } 
/* 1906 */       return object;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Enumeration<?> getAttributeNames() {
/* 1916 */       return this.attributes.getAttributeNames();
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
/*      */     public boolean containsAttribute(Object param1Object1, Object param1Object2) {
/* 1928 */       return this.attributes.containsAttribute(param1Object1, param1Object2);
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
/*      */     public boolean containsAttributes(AttributeSet param1AttributeSet) {
/* 1940 */       return this.attributes.containsAttributes(param1AttributeSet);
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
/*      */     public AttributeSet getResolveParent() {
/* 1952 */       AttributeSet attributeSet = this.attributes.getResolveParent();
/* 1953 */       if (attributeSet == null && this.parent != null) {
/* 1954 */         attributeSet = this.parent.getAttributes();
/*      */       }
/* 1956 */       return attributeSet;
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
/*      */     public void addAttribute(Object param1Object1, Object param1Object2) {
/* 1971 */       checkForIllegalCast();
/* 1972 */       AbstractDocument.AttributeContext attributeContext = AbstractDocument.this.getAttributeContext();
/* 1973 */       this.attributes = attributeContext.addAttribute(this.attributes, param1Object1, param1Object2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addAttributes(AttributeSet param1AttributeSet) {
/* 1983 */       checkForIllegalCast();
/* 1984 */       AbstractDocument.AttributeContext attributeContext = AbstractDocument.this.getAttributeContext();
/* 1985 */       this.attributes = attributeContext.addAttributes(this.attributes, param1AttributeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAttribute(Object param1Object) {
/* 1995 */       checkForIllegalCast();
/* 1996 */       AbstractDocument.AttributeContext attributeContext = AbstractDocument.this.getAttributeContext();
/* 1997 */       this.attributes = attributeContext.removeAttribute(this.attributes, param1Object);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAttributes(Enumeration<?> param1Enumeration) {
/* 2007 */       checkForIllegalCast();
/* 2008 */       AbstractDocument.AttributeContext attributeContext = AbstractDocument.this.getAttributeContext();
/* 2009 */       this.attributes = attributeContext.removeAttributes(this.attributes, param1Enumeration);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAttributes(AttributeSet param1AttributeSet) {
/* 2019 */       checkForIllegalCast();
/* 2020 */       AbstractDocument.AttributeContext attributeContext = AbstractDocument.this.getAttributeContext();
/* 2021 */       if (param1AttributeSet == this) {
/* 2022 */         this.attributes = attributeContext.getEmptySet();
/*      */       } else {
/* 2024 */         this.attributes = attributeContext.removeAttributes(this.attributes, param1AttributeSet);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setResolveParent(AttributeSet param1AttributeSet) {
/* 2035 */       checkForIllegalCast();
/* 2036 */       AbstractDocument.AttributeContext attributeContext = AbstractDocument.this.getAttributeContext();
/* 2037 */       if (param1AttributeSet != null) {
/* 2038 */         this
/* 2039 */           .attributes = attributeContext.addAttribute(this.attributes, StyleConstants.ResolveAttribute, param1AttributeSet);
/*      */       } else {
/*      */         
/* 2042 */         this
/* 2043 */           .attributes = attributeContext.removeAttribute(this.attributes, StyleConstants.ResolveAttribute);
/*      */       } 
/*      */     }
/*      */     
/*      */     private final void checkForIllegalCast() {
/* 2048 */       Thread thread = AbstractDocument.this.getCurrentWriter();
/* 2049 */       if (thread == null || thread != Thread.currentThread()) {
/* 2050 */         throw new StateInvariantError("Illegal cast to MutableAttributeSet");
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
/*      */     public Document getDocument() {
/* 2062 */       return AbstractDocument.this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element getParentElement() {
/* 2071 */       return this.parent;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet getAttributes() {
/* 2080 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 2089 */       if (this.attributes.isDefined("$ename")) {
/* 2090 */         return (String)this.attributes.getAttribute("$ename");
/*      */       }
/* 2092 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract int getStartOffset();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract int getEndOffset();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract Element getElement(int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract int getElementCount();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract int getElementIndex(int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract boolean isLeaf();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TreeNode getChildAt(int param1Int) {
/* 2146 */       return (TreeNode)getElement(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getChildCount() {
/* 2156 */       return getElementCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TreeNode getParent() {
/* 2164 */       return (TreeNode)getParentElement();
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
/*      */     public int getIndex(TreeNode param1TreeNode) {
/* 2176 */       for (int i = getChildCount() - 1; i >= 0; i--) {
/* 2177 */         if (getChildAt(i) == param1TreeNode)
/* 2178 */           return i; 
/* 2179 */       }  return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract boolean getAllowsChildren();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract Enumeration children();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 2200 */       param1ObjectOutputStream.defaultWriteObject();
/* 2201 */       StyleContext.writeAttributeSet(param1ObjectOutputStream, this.attributes);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws ClassNotFoundException, IOException {
/* 2207 */       param1ObjectInputStream.defaultReadObject();
/* 2208 */       SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 2209 */       StyleContext.readAttributeSet(param1ObjectInputStream, simpleAttributeSet);
/* 2210 */       AbstractDocument.AttributeContext attributeContext = AbstractDocument.this.getAttributeContext();
/* 2211 */       this.attributes = attributeContext.addAttributes(SimpleAttributeSet.EMPTY, simpleAttributeSet);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class BranchElement
/*      */     extends AbstractElement
/*      */   {
/*      */     private AbstractDocument.AbstractElement[] children;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int nchildren;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int lastIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BranchElement(Element param1Element, AttributeSet param1AttributeSet) {
/* 2244 */       super(param1Element, param1AttributeSet);
/* 2245 */       this.children = new AbstractDocument.AbstractElement[1];
/* 2246 */       this.nchildren = 0;
/* 2247 */       this.lastIndex = -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element positionToElement(int param1Int) {
/* 2258 */       int i = getElementIndex(param1Int);
/* 2259 */       AbstractDocument.AbstractElement abstractElement = this.children[i];
/* 2260 */       int j = abstractElement.getStartOffset();
/* 2261 */       int k = abstractElement.getEndOffset();
/* 2262 */       if (param1Int >= j && param1Int < k) {
/* 2263 */         return abstractElement;
/*      */       }
/* 2265 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void replace(int param1Int1, int param1Int2, Element[] param1ArrayOfElement) {
/* 2276 */       int i = param1ArrayOfElement.length - param1Int2;
/* 2277 */       int j = param1Int1 + param1Int2;
/* 2278 */       int k = this.nchildren - j;
/* 2279 */       int m = j + i;
/* 2280 */       if (this.nchildren + i >= this.children.length) {
/*      */         
/* 2282 */         int n = Math.max(2 * this.children.length, this.nchildren + i);
/* 2283 */         AbstractDocument.AbstractElement[] arrayOfAbstractElement = new AbstractDocument.AbstractElement[n];
/* 2284 */         System.arraycopy(this.children, 0, arrayOfAbstractElement, 0, param1Int1);
/* 2285 */         System.arraycopy(param1ArrayOfElement, 0, arrayOfAbstractElement, param1Int1, param1ArrayOfElement.length);
/* 2286 */         System.arraycopy(this.children, j, arrayOfAbstractElement, m, k);
/* 2287 */         this.children = arrayOfAbstractElement;
/*      */       } else {
/*      */         
/* 2290 */         System.arraycopy(this.children, j, this.children, m, k);
/* 2291 */         System.arraycopy(param1ArrayOfElement, 0, this.children, param1Int1, param1ArrayOfElement.length);
/*      */       } 
/* 2293 */       this.nchildren += i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2302 */       return "BranchElement(" + getName() + ") " + getStartOffset() + "," + 
/* 2303 */         getEndOffset() + "\n";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 2314 */       String str = super.getName();
/* 2315 */       if (str == null) {
/* 2316 */         str = "paragraph";
/*      */       }
/* 2318 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getStartOffset() {
/* 2327 */       return this.children[0].getStartOffset();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getEndOffset() {
/* 2337 */       AbstractDocument.AbstractElement abstractElement = (this.nchildren > 0) ? this.children[this.nchildren - 1] : this.children[0];
/*      */       
/* 2339 */       return abstractElement.getEndOffset();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element getElement(int param1Int) {
/* 2349 */       if (param1Int < this.nchildren) {
/* 2350 */         return this.children[param1Int];
/*      */       }
/* 2352 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getElementCount() {
/* 2361 */       return this.nchildren;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getElementIndex(int param1Int) {
/* 2372 */       int i, j = 0;
/* 2373 */       int k = this.nchildren - 1;
/* 2374 */       int m = 0;
/* 2375 */       int n = getStartOffset();
/*      */ 
/*      */       
/* 2378 */       if (this.nchildren == 0) {
/* 2379 */         return 0;
/*      */       }
/* 2381 */       if (param1Int >= getEndOffset()) {
/* 2382 */         return this.nchildren - 1;
/*      */       }
/*      */ 
/*      */       
/* 2386 */       if (this.lastIndex >= j && this.lastIndex <= k) {
/* 2387 */         AbstractDocument.AbstractElement abstractElement = this.children[this.lastIndex];
/* 2388 */         n = abstractElement.getStartOffset();
/* 2389 */         int i1 = abstractElement.getEndOffset();
/* 2390 */         if (param1Int >= n && param1Int < i1) {
/* 2391 */           return this.lastIndex;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2396 */         if (param1Int < n) {
/* 2397 */           k = this.lastIndex;
/*      */         } else {
/* 2399 */           j = this.lastIndex;
/*      */         } 
/*      */       } 
/*      */       
/* 2403 */       while (j <= k) {
/* 2404 */         m = j + (k - j) / 2;
/* 2405 */         AbstractDocument.AbstractElement abstractElement = this.children[m];
/* 2406 */         n = abstractElement.getStartOffset();
/* 2407 */         int i1 = abstractElement.getEndOffset();
/* 2408 */         if (param1Int >= n && param1Int < i1) {
/*      */           
/* 2410 */           i = m;
/* 2411 */           this.lastIndex = i;
/* 2412 */           return i;
/* 2413 */         }  if (param1Int < n) {
/* 2414 */           k = m - 1; continue;
/*      */         } 
/* 2416 */         j = m + 1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2421 */       if (param1Int < n) {
/* 2422 */         i = m;
/*      */       } else {
/* 2424 */         i = m + 1;
/*      */       } 
/* 2426 */       this.lastIndex = i;
/* 2427 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isLeaf() {
/* 2436 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getAllowsChildren() {
/* 2447 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Enumeration children() {
/* 2457 */       if (this.nchildren == 0) {
/* 2458 */         return null;
/*      */       }
/* 2460 */       Vector<AbstractDocument.AbstractElement> vector = new Vector(this.nchildren);
/*      */       
/* 2462 */       for (byte b = 0; b < this.nchildren; b++)
/* 2463 */         vector.addElement(this.children[b]); 
/* 2464 */       return vector.elements();
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
/*      */   public class LeafElement
/*      */     extends AbstractElement
/*      */   {
/*      */     private transient Position p0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private transient Position p1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LeafElement(Element param1Element, AttributeSet param1AttributeSet, int param1Int1, int param1Int2) {
/* 2502 */       super(param1Element, param1AttributeSet);
/*      */       try {
/* 2504 */         this.p0 = AbstractDocument.this.createPosition(param1Int1);
/* 2505 */         this.p1 = AbstractDocument.this.createPosition(param1Int2);
/* 2506 */       } catch (BadLocationException badLocationException) {
/* 2507 */         this.p0 = null;
/* 2508 */         this.p1 = null;
/* 2509 */         throw new StateInvariantError("Can't create Position references");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2519 */       return "LeafElement(" + getName() + ") " + this.p0 + "," + this.p1 + "\n";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getStartOffset() {
/* 2530 */       return this.p0.getOffset();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getEndOffset() {
/* 2539 */       return this.p1.getOffset();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 2548 */       String str = super.getName();
/* 2549 */       if (str == null) {
/* 2550 */         str = "content";
/*      */       }
/* 2552 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getElementIndex(int param1Int) {
/* 2562 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element getElement(int param1Int) {
/* 2572 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getElementCount() {
/* 2581 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isLeaf() {
/* 2590 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getAllowsChildren() {
/* 2600 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Enumeration children() {
/* 2610 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 2616 */       param1ObjectOutputStream.defaultWriteObject();
/* 2617 */       param1ObjectOutputStream.writeInt(this.p0.getOffset());
/* 2618 */       param1ObjectOutputStream.writeInt(this.p1.getOffset());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws ClassNotFoundException, IOException {
/* 2624 */       param1ObjectInputStream.defaultReadObject();
/*      */ 
/*      */       
/* 2627 */       int i = param1ObjectInputStream.readInt();
/* 2628 */       int j = param1ObjectInputStream.readInt();
/*      */       try {
/* 2630 */         this.p0 = AbstractDocument.this.createPosition(i);
/* 2631 */         this.p1 = AbstractDocument.this.createPosition(j);
/* 2632 */       } catch (BadLocationException badLocationException) {
/* 2633 */         this.p0 = null;
/* 2634 */         this.p1 = null;
/* 2635 */         throw new IOException("Can't restore Position references");
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
/*      */   class BidiRootElement
/*      */     extends BranchElement
/*      */   {
/*      */     BidiRootElement() {
/* 2653 */       super(null, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 2661 */       return "bidi root";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class BidiElement
/*      */     extends LeafElement
/*      */   {
/*      */     BidiElement(Element param1Element, int param1Int1, int param1Int2, int param1Int3) {
/* 2674 */       super(param1Element, new SimpleAttributeSet(), param1Int1, param1Int2);
/* 2675 */       addAttribute(StyleConstants.BidiLevel, Integer.valueOf(param1Int3));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 2685 */       return "bidi level";
/*      */     }
/*      */     
/*      */     int getLevel() {
/* 2689 */       Integer integer = (Integer)getAttribute(StyleConstants.BidiLevel);
/* 2690 */       if (integer != null) {
/* 2691 */         return integer.intValue();
/*      */       }
/* 2693 */       return 0;
/*      */     }
/*      */     
/*      */     boolean isLeftToRight() {
/* 2697 */       return (getLevel() % 2 == 0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class DefaultDocumentEvent
/*      */     extends CompoundEdit
/*      */     implements DocumentEvent
/*      */   {
/*      */     private int offset;
/*      */ 
/*      */     
/*      */     private int length;
/*      */ 
/*      */     
/*      */     private Hashtable<Element, DocumentEvent.ElementChange> changeLookup;
/*      */ 
/*      */     
/*      */     private DocumentEvent.EventType type;
/*      */ 
/*      */     
/*      */     public DefaultDocumentEvent(int param1Int1, int param1Int2, DocumentEvent.EventType param1EventType) {
/* 2720 */       this.offset = param1Int1;
/* 2721 */       this.length = param1Int2;
/* 2722 */       this.type = param1EventType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2731 */       return this.edits.toString();
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
/*      */     public boolean addEdit(UndoableEdit param1UndoableEdit) {
/* 2748 */       if (this.changeLookup == null && this.edits.size() > 10) {
/* 2749 */         this.changeLookup = new Hashtable<>();
/* 2750 */         int i = this.edits.size();
/* 2751 */         for (byte b = 0; b < i; b++) {
/* 2752 */           DocumentEvent.ElementChange elementChange = (DocumentEvent.ElementChange)this.edits.elementAt(b);
/* 2753 */           if (elementChange instanceof DocumentEvent.ElementChange) {
/* 2754 */             DocumentEvent.ElementChange elementChange1 = elementChange;
/* 2755 */             this.changeLookup.put(elementChange1.getElement(), elementChange1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2762 */       if (this.changeLookup != null && param1UndoableEdit instanceof DocumentEvent.ElementChange) {
/* 2763 */         DocumentEvent.ElementChange elementChange = (DocumentEvent.ElementChange)param1UndoableEdit;
/* 2764 */         this.changeLookup.put(elementChange.getElement(), elementChange);
/*      */       } 
/* 2766 */       return super.addEdit(param1UndoableEdit);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void redo() throws CannotRedoException {
/* 2775 */       AbstractDocument.this.writeLock();
/*      */       
/*      */       try {
/* 2778 */         super.redo();
/*      */         
/* 2780 */         AbstractDocument.UndoRedoDocumentEvent undoRedoDocumentEvent = new AbstractDocument.UndoRedoDocumentEvent(this, false);
/* 2781 */         if (this.type == DocumentEvent.EventType.INSERT) {
/* 2782 */           AbstractDocument.this.fireInsertUpdate(undoRedoDocumentEvent);
/* 2783 */         } else if (this.type == DocumentEvent.EventType.REMOVE) {
/* 2784 */           AbstractDocument.this.fireRemoveUpdate(undoRedoDocumentEvent);
/*      */         } else {
/* 2786 */           AbstractDocument.this.fireChangedUpdate(undoRedoDocumentEvent);
/*      */         } 
/*      */       } finally {
/* 2789 */         AbstractDocument.this.writeUnlock();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void undo() throws CannotUndoException {
/* 2799 */       AbstractDocument.this.writeLock();
/*      */       
/*      */       try {
/* 2802 */         super.undo();
/*      */         
/* 2804 */         AbstractDocument.UndoRedoDocumentEvent undoRedoDocumentEvent = new AbstractDocument.UndoRedoDocumentEvent(this, true);
/* 2805 */         if (this.type == DocumentEvent.EventType.REMOVE) {
/* 2806 */           AbstractDocument.this.fireInsertUpdate(undoRedoDocumentEvent);
/* 2807 */         } else if (this.type == DocumentEvent.EventType.INSERT) {
/* 2808 */           AbstractDocument.this.fireRemoveUpdate(undoRedoDocumentEvent);
/*      */         } else {
/* 2810 */           AbstractDocument.this.fireChangedUpdate(undoRedoDocumentEvent);
/*      */         } 
/*      */       } finally {
/* 2813 */         AbstractDocument.this.writeUnlock();
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
/*      */     public boolean isSignificant() {
/* 2825 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getPresentationName() {
/* 2836 */       DocumentEvent.EventType eventType = getType();
/* 2837 */       if (eventType == DocumentEvent.EventType.INSERT)
/* 2838 */         return UIManager.getString("AbstractDocument.additionText"); 
/* 2839 */       if (eventType == DocumentEvent.EventType.REMOVE)
/* 2840 */         return UIManager.getString("AbstractDocument.deletionText"); 
/* 2841 */       return UIManager.getString("AbstractDocument.styleChangeText");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getUndoPresentationName() {
/* 2852 */       return UIManager.getString("AbstractDocument.undoText") + " " + 
/* 2853 */         getPresentationName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getRedoPresentationName() {
/* 2864 */       return UIManager.getString("AbstractDocument.redoText") + " " + 
/* 2865 */         getPresentationName();
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
/*      */     public DocumentEvent.EventType getType() {
/* 2877 */       return this.type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getOffset() {
/* 2887 */       return this.offset;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getLength() {
/* 2897 */       return this.length;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Document getDocument() {
/* 2907 */       return AbstractDocument.this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DocumentEvent.ElementChange getChange(Element param1Element) {
/* 2917 */       if (this.changeLookup != null) {
/* 2918 */         return this.changeLookup.get(param1Element);
/*      */       }
/* 2920 */       int i = this.edits.size();
/* 2921 */       for (byte b = 0; b < i; b++) {
/* 2922 */         DocumentEvent.ElementChange elementChange = (DocumentEvent.ElementChange)this.edits.elementAt(b);
/* 2923 */         if (elementChange instanceof DocumentEvent.ElementChange) {
/* 2924 */           DocumentEvent.ElementChange elementChange1 = elementChange;
/* 2925 */           if (param1Element.equals(elementChange1.getElement())) {
/* 2926 */             return elementChange1;
/*      */           }
/*      */         } 
/*      */       } 
/* 2930 */       return null;
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
/*      */   class UndoRedoDocumentEvent
/*      */     implements DocumentEvent
/*      */   {
/* 2949 */     private AbstractDocument.DefaultDocumentEvent src = null;
/* 2950 */     private DocumentEvent.EventType type = null;
/*      */     
/*      */     public UndoRedoDocumentEvent(AbstractDocument.DefaultDocumentEvent param1DefaultDocumentEvent, boolean param1Boolean) {
/* 2953 */       this.src = param1DefaultDocumentEvent;
/* 2954 */       if (param1Boolean) {
/* 2955 */         if (param1DefaultDocumentEvent.getType().equals(DocumentEvent.EventType.INSERT)) {
/* 2956 */           this.type = DocumentEvent.EventType.REMOVE;
/* 2957 */         } else if (param1DefaultDocumentEvent.getType().equals(DocumentEvent.EventType.REMOVE)) {
/* 2958 */           this.type = DocumentEvent.EventType.INSERT;
/*      */         } else {
/* 2960 */           this.type = param1DefaultDocumentEvent.getType();
/*      */         } 
/*      */       } else {
/* 2963 */         this.type = param1DefaultDocumentEvent.getType();
/*      */       } 
/*      */     }
/*      */     
/*      */     public AbstractDocument.DefaultDocumentEvent getSource() {
/* 2968 */       return this.src;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getOffset() {
/* 2974 */       return this.src.getOffset();
/*      */     }
/*      */     
/*      */     public int getLength() {
/* 2978 */       return this.src.getLength();
/*      */     }
/*      */     
/*      */     public Document getDocument() {
/* 2982 */       return this.src.getDocument();
/*      */     }
/*      */     
/*      */     public DocumentEvent.EventType getType() {
/* 2986 */       return this.type;
/*      */     }
/*      */     
/*      */     public DocumentEvent.ElementChange getChange(Element param1Element) {
/* 2990 */       return this.src.getChange(param1Element);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ElementEdit
/*      */     extends AbstractUndoableEdit
/*      */     implements DocumentEvent.ElementChange
/*      */   {
/*      */     private Element e;
/*      */ 
/*      */     
/*      */     private int index;
/*      */ 
/*      */     
/*      */     private Element[] removed;
/*      */     
/*      */     private Element[] added;
/*      */ 
/*      */     
/*      */     public ElementEdit(Element param1Element, int param1Int, Element[] param1ArrayOfElement1, Element[] param1ArrayOfElement2) {
/* 3012 */       this.e = param1Element;
/* 3013 */       this.index = param1Int;
/* 3014 */       this.removed = param1ArrayOfElement1;
/* 3015 */       this.added = param1ArrayOfElement2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element getElement() {
/* 3024 */       return this.e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getIndex() {
/* 3033 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element[] getChildrenRemoved() {
/* 3042 */       return this.removed;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element[] getChildrenAdded() {
/* 3051 */       return this.added;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void redo() throws CannotRedoException {
/* 3060 */       super.redo();
/*      */ 
/*      */       
/* 3063 */       Element[] arrayOfElement = this.removed;
/* 3064 */       this.removed = this.added;
/* 3065 */       this.added = arrayOfElement;
/*      */ 
/*      */       
/* 3068 */       ((AbstractDocument.BranchElement)this.e).replace(this.index, this.removed.length, this.added);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void undo() throws CannotUndoException {
/* 3077 */       super.undo();
/*      */       
/* 3079 */       ((AbstractDocument.BranchElement)this.e).replace(this.index, this.added.length, this.removed);
/*      */ 
/*      */       
/* 3082 */       Element[] arrayOfElement = this.removed;
/* 3083 */       this.removed = this.added;
/* 3084 */       this.added = arrayOfElement;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class DefaultFilterBypass
/*      */     extends DocumentFilter.FilterBypass
/*      */   {
/*      */     private DefaultFilterBypass() {}
/*      */ 
/*      */     
/*      */     public Document getDocument() {
/* 3096 */       return AbstractDocument.this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove(int param1Int1, int param1Int2) throws BadLocationException {
/* 3101 */       AbstractDocument.this.handleRemove(param1Int1, param1Int2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void insertString(int param1Int, String param1String, AttributeSet param1AttributeSet) throws BadLocationException {
/* 3107 */       AbstractDocument.this.handleInsertString(param1Int, param1String, param1AttributeSet);
/*      */     }
/*      */ 
/*      */     
/*      */     public void replace(int param1Int1, int param1Int2, String param1String, AttributeSet param1AttributeSet) throws BadLocationException {
/* 3112 */       AbstractDocument.this.handleRemove(param1Int1, param1Int2);
/* 3113 */       AbstractDocument.this.handleInsertString(param1Int1, param1String, param1AttributeSet);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/AbstractDocument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */