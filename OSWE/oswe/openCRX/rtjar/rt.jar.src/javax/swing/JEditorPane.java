/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.StringReader;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleHyperlink;
/*      */ import javax.accessibility.AccessibleHypertext;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.event.HyperlinkEvent;
/*      */ import javax.swing.event.HyperlinkListener;
/*      */ import javax.swing.plaf.TextUI;
/*      */ import javax.swing.text.AbstractDocument;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.BadLocationException;
/*      */ import javax.swing.text.BoxView;
/*      */ import javax.swing.text.Caret;
/*      */ import javax.swing.text.ChangedCharSetException;
/*      */ import javax.swing.text.CompositeView;
/*      */ import javax.swing.text.DefaultEditorKit;
/*      */ import javax.swing.text.Document;
/*      */ import javax.swing.text.EditorKit;
/*      */ import javax.swing.text.Element;
/*      */ import javax.swing.text.ElementIterator;
/*      */ import javax.swing.text.GlyphView;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import javax.swing.text.ParagraphView;
/*      */ import javax.swing.text.StyledEditorKit;
/*      */ import javax.swing.text.View;
/*      */ import javax.swing.text.ViewFactory;
/*      */ import javax.swing.text.WrappedPlainView;
/*      */ import javax.swing.text.html.HTML;
/*      */ import javax.swing.text.html.HTMLDocument;
/*      */ import javax.swing.text.html.HTMLEditorKit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JEditorPane
/*      */   extends JTextComponent
/*      */ {
/*      */   private SwingWorker<URL, Object> pageLoader;
/*      */   private EditorKit kit;
/*      */   private boolean isUserSetEditorKit;
/*      */   private Hashtable<String, Object> pageProperties;
/*      */   static final String PostDataProperty = "javax.swing.JEditorPane.postdata";
/*      */   private Hashtable<String, EditorKit> typeHandlers;
/*      */   
/*      */   public JEditorPane() {
/*  197 */     setFocusCycleRoot(true);
/*  198 */     setFocusTraversalPolicy(new LayoutFocusTraversalPolicy()
/*      */         {
/*      */           public Component getComponentAfter(Container param1Container, Component param1Component) {
/*  201 */             if (param1Container != JEditorPane.this || (
/*  202 */               !JEditorPane.this.isEditable() && JEditorPane.this.getComponentCount() > 0)) {
/*  203 */               return super.getComponentAfter(param1Container, param1Component);
/*      */             }
/*      */             
/*  206 */             Container container = JEditorPane.this.getFocusCycleRootAncestor();
/*  207 */             return (container != null) ? container
/*  208 */               .getFocusTraversalPolicy()
/*  209 */               .getComponentAfter(container, JEditorPane.this) : null;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public Component getComponentBefore(Container param1Container, Component param1Component) {
/*  216 */             if (param1Container != JEditorPane.this || (
/*  217 */               !JEditorPane.this.isEditable() && JEditorPane.this.getComponentCount() > 0)) {
/*  218 */               return super.getComponentBefore(param1Container, param1Component);
/*      */             }
/*      */             
/*  221 */             Container container = JEditorPane.this.getFocusCycleRootAncestor();
/*  222 */             return (container != null) ? container
/*  223 */               .getFocusTraversalPolicy()
/*  224 */               .getComponentBefore(container, JEditorPane.this) : null;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public Component getDefaultComponent(Container param1Container) {
/*  231 */             return (param1Container != JEditorPane.this || (
/*  232 */               !JEditorPane.this.isEditable() && JEditorPane.this.getComponentCount() > 0)) ? super
/*  233 */               .getDefaultComponent(param1Container) : null;
/*      */           }
/*      */           
/*      */           protected boolean accept(Component param1Component) {
/*  237 */             return (param1Component != JEditorPane.this) ? super
/*  238 */               .accept(param1Component) : false;
/*      */           }
/*      */         });
/*      */     
/*  242 */     LookAndFeel.installProperty(this, "focusTraversalKeysForward", 
/*      */ 
/*      */         
/*  245 */         JComponent.getManagingFocusForwardTraversalKeys());
/*  246 */     LookAndFeel.installProperty(this, "focusTraversalKeysBackward", 
/*      */ 
/*      */         
/*  249 */         JComponent.getManagingFocusBackwardTraversalKeys());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JEditorPane(URL paramURL) throws IOException {
/*  260 */     this();
/*  261 */     setPage(paramURL);
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
/*      */   public JEditorPane(String paramString) throws IOException {
/*  273 */     this();
/*  274 */     setPage(paramString);
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
/*      */   public JEditorPane(String paramString1, String paramString2) {
/*  288 */     this();
/*  289 */     setContentType(paramString1);
/*  290 */     setText(paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addHyperlinkListener(HyperlinkListener paramHyperlinkListener) {
/*  300 */     this.listenerList.add(HyperlinkListener.class, paramHyperlinkListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeHyperlinkListener(HyperlinkListener paramHyperlinkListener) {
/*  309 */     this.listenerList.remove(HyperlinkListener.class, paramHyperlinkListener);
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
/*      */   public synchronized HyperlinkListener[] getHyperlinkListeners() {
/*  321 */     return this.listenerList.<HyperlinkListener>getListeners(HyperlinkListener.class);
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
/*      */   public void fireHyperlinkUpdate(HyperlinkEvent paramHyperlinkEvent) {
/*  337 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */ 
/*      */     
/*  340 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  341 */       if (arrayOfObject[i] == HyperlinkListener.class) {
/*  342 */         ((HyperlinkListener)arrayOfObject[i + 1]).hyperlinkUpdate(paramHyperlinkEvent);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPage(URL paramURL) throws IOException {
/*  414 */     if (paramURL == null) {
/*  415 */       throw new IOException("invalid url");
/*      */     }
/*  417 */     URL uRL = getPage();
/*      */ 
/*      */ 
/*      */     
/*  421 */     if (!paramURL.equals(uRL) && paramURL.getRef() == null) {
/*  422 */       scrollRectToVisible(new Rectangle(0, 0, 1, 1));
/*      */     }
/*  424 */     boolean bool = false;
/*  425 */     Object object = getPostData();
/*  426 */     if (uRL == null || !uRL.sameFile(paramURL) || object != null) {
/*      */ 
/*      */       
/*  429 */       int i = getAsynchronousLoadPriority(getDocument());
/*  430 */       if (i < 0) {
/*      */         
/*  432 */         InputStream inputStream = getStream(paramURL);
/*  433 */         if (this.kit != null) {
/*  434 */           Document document = initializeModel(this.kit, paramURL);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  440 */           i = getAsynchronousLoadPriority(document);
/*  441 */           if (i >= 0) {
/*      */             
/*  443 */             setDocument(document);
/*  444 */             synchronized (this) {
/*  445 */               this.pageLoader = new PageLoader(document, inputStream, uRL, paramURL);
/*  446 */               this.pageLoader.execute();
/*      */             } 
/*      */             return;
/*      */           } 
/*  450 */           read(inputStream, document);
/*  451 */           setDocument(document);
/*  452 */           bool = true;
/*      */         } 
/*      */       } else {
/*      */         
/*  456 */         if (this.pageLoader != null) {
/*  457 */           this.pageLoader.cancel(true);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  462 */         this.pageLoader = new PageLoader(null, null, uRL, paramURL);
/*  463 */         this.pageLoader.execute();
/*      */         return;
/*      */       } 
/*      */     } 
/*  467 */     final String reference = paramURL.getRef();
/*  468 */     if (str != null) {
/*  469 */       if (!bool) {
/*  470 */         scrollToReference(str);
/*      */       }
/*      */       else {
/*      */         
/*  474 */         SwingUtilities.invokeLater(new Runnable() {
/*      */               public void run() {
/*  476 */                 JEditorPane.this.scrollToReference(reference);
/*      */               }
/*      */             });
/*      */       } 
/*  480 */       getDocument().putProperty("stream", paramURL);
/*      */     } 
/*  482 */     firePropertyChange("page", uRL, paramURL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Document initializeModel(EditorKit paramEditorKit, URL paramURL) {
/*  489 */     Document document = paramEditorKit.createDefaultDocument();
/*  490 */     if (this.pageProperties != null) {
/*      */ 
/*      */       
/*  493 */       for (Enumeration<String> enumeration = this.pageProperties.keys(); enumeration.hasMoreElements(); ) {
/*  494 */         String str = enumeration.nextElement();
/*  495 */         document.putProperty(str, this.pageProperties.get(str));
/*      */       } 
/*  497 */       this.pageProperties.clear();
/*      */     } 
/*  499 */     if (document.getProperty("stream") == null) {
/*  500 */       document.putProperty("stream", paramURL);
/*      */     }
/*  502 */     return document;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getAsynchronousLoadPriority(Document paramDocument) {
/*  509 */     return (paramDocument instanceof AbstractDocument) ? ((AbstractDocument)paramDocument)
/*  510 */       .getAsynchronousLoadPriority() : -1;
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
/*      */   public void read(InputStream paramInputStream, Object paramObject) throws IOException {
/*  530 */     if (paramObject instanceof HTMLDocument && this.kit instanceof HTMLEditorKit) {
/*      */       
/*  532 */       HTMLDocument hTMLDocument = (HTMLDocument)paramObject;
/*  533 */       setDocument(hTMLDocument);
/*  534 */       read(paramInputStream, hTMLDocument);
/*      */     } else {
/*  536 */       String str = (String)getClientProperty("charset");
/*  537 */       InputStreamReader inputStreamReader = (str != null) ? new InputStreamReader(paramInputStream, str) : new InputStreamReader(paramInputStream);
/*      */       
/*  539 */       read(inputStreamReader, paramObject);
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
/*      */   void read(InputStream paramInputStream, Document paramDocument) throws IOException {
/*  556 */     if (!Boolean.TRUE.equals(paramDocument.getProperty("IgnoreCharsetDirective"))) {
/*      */       
/*  558 */       paramInputStream = new BufferedInputStream(paramInputStream, 10240);
/*  559 */       paramInputStream.mark(10240);
/*      */     } 
/*      */     try {
/*  562 */       String str = (String)getClientProperty("charset");
/*  563 */       InputStreamReader inputStreamReader = (str != null) ? new InputStreamReader(paramInputStream, str) : new InputStreamReader(paramInputStream);
/*      */       
/*  565 */       this.kit.read(inputStreamReader, paramDocument, 0);
/*  566 */     } catch (BadLocationException badLocationException) {
/*  567 */       throw new IOException(badLocationException.getMessage());
/*  568 */     } catch (ChangedCharSetException changedCharSetException) {
/*  569 */       String str = changedCharSetException.getCharSetSpec();
/*  570 */       if (changedCharSetException.keyEqualsCharSet()) {
/*  571 */         putClientProperty("charset", str);
/*      */       } else {
/*  573 */         setCharsetFromContentTypeParameters(str);
/*      */       } 
/*      */       try {
/*  576 */         paramInputStream.reset();
/*  577 */       } catch (IOException iOException) {
/*      */         
/*  579 */         paramInputStream.close();
/*  580 */         URL uRL = (URL)paramDocument.getProperty("stream");
/*  581 */         if (uRL != null) {
/*  582 */           URLConnection uRLConnection = uRL.openConnection();
/*  583 */           paramInputStream = uRLConnection.getInputStream();
/*      */         } else {
/*      */           
/*  586 */           throw changedCharSetException;
/*      */         } 
/*      */       } 
/*      */       try {
/*  590 */         paramDocument.remove(0, paramDocument.getLength());
/*  591 */       } catch (BadLocationException badLocationException) {}
/*  592 */       paramDocument.putProperty("IgnoreCharsetDirective", Boolean.valueOf(true));
/*  593 */       read(paramInputStream, paramDocument);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   class PageLoader
/*      */     extends SwingWorker<URL, Object>
/*      */   {
/*      */     InputStream in;
/*      */     URL old;
/*      */     URL page;
/*      */     Document doc;
/*      */     
/*      */     PageLoader(Document param1Document, InputStream param1InputStream, URL param1URL1, URL param1URL2) {
/*  607 */       this.in = param1InputStream;
/*  608 */       this.old = param1URL1;
/*  609 */       this.page = param1URL2;
/*  610 */       this.doc = param1Document;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected URL doInBackground() {
/*  619 */       boolean bool = false;
/*      */       
/*  621 */       try { if (this.in == null)
/*  622 */         { this.in = JEditorPane.this.getStream(this.page);
/*  623 */           if (JEditorPane.this.kit == null)
/*      */           
/*  625 */           { UIManager.getLookAndFeel()
/*  626 */               .provideErrorFeedback(JEditorPane.this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  678 */             return bool ? this.page : this.old; }  }  if (this.doc == null) try { SwingUtilities.invokeAndWait(new Runnable() { public void run() { JEditorPane.PageLoader.this.doc = JEditorPane.this.initializeModel(JEditorPane.this.kit, JEditorPane.PageLoader.this.page); JEditorPane.this.setDocument(JEditorPane.PageLoader.this.doc); } }); } catch (InvocationTargetException invocationTargetException) { UIManager.getLookAndFeel().provideErrorFeedback(JEditorPane.this); return bool ? this.page : this.old; } catch (InterruptedException interruptedException) { UIManager.getLookAndFeel().provideErrorFeedback(JEditorPane.this); return bool ? this.page : this.old; }   JEditorPane.this.read(this.in, this.doc); URL uRL = (URL)this.doc.getProperty("stream"); String str = uRL.getRef(); if (str != null) { Runnable runnable = new Runnable() { public void run() { URL uRL = (URL)JEditorPane.this.getDocument().getProperty("stream"); String str = uRL.getRef(); JEditorPane.this.scrollToReference(str); } }; SwingUtilities.invokeLater(runnable); }  return bool ? this.page : this.old; } catch (IOException iOException) { return bool ? this.page : this.old; }
/*      */       finally
/*      */       { Exception exception = null;
/*      */         if (bool) {
/*      */           SwingUtilities.invokeLater(new Runnable()
/*      */               {
/*      */                 public void run() {
/*      */                   JEditorPane.this.firePropertyChange("page", JEditorPane.PageLoader.this.old, JEditorPane.PageLoader.this.page);
/*      */                 }
/*      */               });
/*      */         } }
/*      */     
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
/*      */   protected InputStream getStream(URL paramURL) throws IOException {
/*  725 */     final URLConnection conn = paramURL.openConnection();
/*  726 */     if (uRLConnection instanceof HttpURLConnection) {
/*  727 */       HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
/*  728 */       httpURLConnection.setInstanceFollowRedirects(false);
/*  729 */       Object object = getPostData();
/*  730 */       if (object != null) {
/*  731 */         handlePostData(httpURLConnection, object);
/*      */       }
/*  733 */       int i = httpURLConnection.getResponseCode();
/*  734 */       boolean bool = (i >= 300 && i <= 399) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  740 */       if (bool) {
/*  741 */         String str = uRLConnection.getHeaderField("Location");
/*  742 */         if (str.startsWith("http", 0)) {
/*  743 */           paramURL = new URL(str);
/*      */         } else {
/*  745 */           paramURL = new URL(paramURL, str);
/*      */         } 
/*  747 */         return getStream(paramURL);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  753 */     if (SwingUtilities.isEventDispatchThread()) {
/*  754 */       handleConnectionProperties(uRLConnection);
/*      */     } else {
/*      */       try {
/*  757 */         SwingUtilities.invokeAndWait(new Runnable() {
/*      */               public void run() {
/*  759 */                 JEditorPane.this.handleConnectionProperties(conn);
/*      */               }
/*      */             });
/*  762 */       } catch (InterruptedException interruptedException) {
/*  763 */         throw new RuntimeException(interruptedException);
/*  764 */       } catch (InvocationTargetException invocationTargetException) {
/*  765 */         throw new RuntimeException(invocationTargetException);
/*      */       } 
/*      */     } 
/*  768 */     return uRLConnection.getInputStream();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleConnectionProperties(URLConnection paramURLConnection) {
/*  775 */     if (this.pageProperties == null) {
/*  776 */       this.pageProperties = new Hashtable<>();
/*      */     }
/*  778 */     String str1 = paramURLConnection.getContentType();
/*  779 */     if (str1 != null) {
/*  780 */       setContentType(str1);
/*  781 */       this.pageProperties.put("content-type", str1);
/*      */     } 
/*  783 */     this.pageProperties.put("stream", paramURLConnection.getURL());
/*  784 */     String str2 = paramURLConnection.getContentEncoding();
/*  785 */     if (str2 != null) {
/*  786 */       this.pageProperties.put("content-encoding", str2);
/*      */     }
/*      */   }
/*      */   
/*      */   private Object getPostData() {
/*  791 */     return getDocument().getProperty("javax.swing.JEditorPane.postdata");
/*      */   }
/*      */ 
/*      */   
/*      */   private void handlePostData(HttpURLConnection paramHttpURLConnection, Object paramObject) throws IOException {
/*  796 */     paramHttpURLConnection.setDoOutput(true);
/*  797 */     DataOutputStream dataOutputStream = null;
/*      */     try {
/*  799 */       paramHttpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*      */       
/*  801 */       dataOutputStream = new DataOutputStream(paramHttpURLConnection.getOutputStream());
/*  802 */       dataOutputStream.writeBytes((String)paramObject);
/*      */     } finally {
/*  804 */       if (dataOutputStream != null) {
/*  805 */         dataOutputStream.close();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void scrollToReference(String paramString) {
/*  827 */     Document document = getDocument();
/*  828 */     if (document instanceof HTMLDocument) {
/*  829 */       HTMLDocument hTMLDocument = (HTMLDocument)document;
/*  830 */       HTMLDocument.Iterator iterator = hTMLDocument.getIterator(HTML.Tag.A);
/*  831 */       for (; iterator.isValid(); iterator.next()) {
/*  832 */         AttributeSet attributeSet = iterator.getAttributes();
/*  833 */         String str = (String)attributeSet.getAttribute(HTML.Attribute.NAME);
/*  834 */         if (str != null && str.equals(paramString)) {
/*      */           
/*      */           try {
/*  837 */             int i = iterator.getStartOffset();
/*  838 */             Rectangle rectangle = modelToView(i);
/*  839 */             if (rectangle != null) {
/*      */ 
/*      */               
/*  842 */               Rectangle rectangle1 = getVisibleRect();
/*      */               
/*  844 */               rectangle.height = rectangle1.height;
/*  845 */               scrollRectToVisible(rectangle);
/*  846 */               setCaretPosition(i);
/*      */             } 
/*  848 */           } catch (BadLocationException badLocationException) {
/*  849 */             UIManager.getLookAndFeel().provideErrorFeedback(this);
/*      */           } 
/*      */         }
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
/*      */   public URL getPage() {
/*  865 */     return (URL)getDocument().getProperty("stream");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPage(String paramString) throws IOException {
/*  876 */     if (paramString == null) {
/*  877 */       throw new IOException("invalid url");
/*      */     }
/*  879 */     URL uRL = new URL(paramString);
/*  880 */     setPage(uRL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUIClassID() {
/*  891 */     return "EditorPaneUI";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EditorKit createDefaultEditorKit() {
/*  901 */     return new PlainEditorKit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EditorKit getEditorKit() {
/*  912 */     if (this.kit == null) {
/*  913 */       this.kit = createDefaultEditorKit();
/*  914 */       this.isUserSetEditorKit = false;
/*      */     } 
/*  916 */     return this.kit;
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
/*      */   public final String getContentType() {
/*  928 */     return (this.kit != null) ? this.kit.getContentType() : null;
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
/*      */   public final void setContentType(String paramString) {
/*  964 */     int i = paramString.indexOf(";");
/*  965 */     if (i > -1) {
/*      */       
/*  967 */       String str = paramString.substring(i);
/*      */       
/*  969 */       paramString = paramString.substring(0, i).trim();
/*  970 */       if (paramString.toLowerCase().startsWith("text/")) {
/*  971 */         setCharsetFromContentTypeParameters(str);
/*      */       }
/*      */     } 
/*  974 */     if (this.kit == null || !paramString.equals(this.kit.getContentType()) || !this.isUserSetEditorKit) {
/*      */       
/*  976 */       EditorKit editorKit = getEditorKitForContentType(paramString);
/*  977 */       if (editorKit != null && editorKit != this.kit) {
/*  978 */         setEditorKit(editorKit);
/*  979 */         this.isUserSetEditorKit = false;
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
/*      */   private void setCharsetFromContentTypeParameters(String paramString) {
/*      */     try {
/*  993 */       int i = paramString.indexOf(';');
/*  994 */       if (i > -1 && i < paramString.length() - 1) {
/*  995 */         paramString = paramString.substring(i + 1);
/*      */       }
/*      */       
/*  998 */       if (paramString.length() > 0)
/*      */       {
/*      */         
/* 1001 */         HeaderParser headerParser = new HeaderParser(paramString);
/* 1002 */         String str = headerParser.findValue("charset");
/* 1003 */         if (str != null) {
/* 1004 */           putClientProperty("charset", str);
/*      */         }
/*      */       }
/*      */     
/* 1008 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*      */ 
/*      */     
/* 1011 */     } catch (NullPointerException nullPointerException) {
/*      */ 
/*      */     
/* 1014 */     } catch (Exception exception) {
/*      */       
/* 1016 */       System.err.println("JEditorPane.getCharsetFromContentTypeParameters failed on: " + paramString);
/* 1017 */       exception.printStackTrace();
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
/*      */   public void setEditorKit(EditorKit paramEditorKit) {
/* 1047 */     EditorKit editorKit = this.kit;
/* 1048 */     this.isUserSetEditorKit = true;
/* 1049 */     if (editorKit != null) {
/* 1050 */       editorKit.deinstall(this);
/*      */     }
/* 1052 */     this.kit = paramEditorKit;
/* 1053 */     if (this.kit != null) {
/* 1054 */       this.kit.install(this);
/* 1055 */       setDocument(this.kit.createDefaultDocument());
/*      */     } 
/* 1057 */     firePropertyChange("editorKit", editorKit, paramEditorKit);
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
/*      */   public EditorKit getEditorKitForContentType(String paramString) {
/* 1080 */     if (this.typeHandlers == null) {
/* 1081 */       this.typeHandlers = new Hashtable<>(3);
/*      */     }
/* 1083 */     EditorKit editorKit = this.typeHandlers.get(paramString);
/* 1084 */     if (editorKit == null) {
/* 1085 */       editorKit = createEditorKitForContentType(paramString);
/* 1086 */       if (editorKit != null) {
/* 1087 */         setEditorKitForContentType(paramString, editorKit);
/*      */       }
/*      */     } 
/* 1090 */     if (editorKit == null) {
/* 1091 */       editorKit = createDefaultEditorKit();
/*      */     }
/* 1093 */     return editorKit;
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
/*      */   public void setEditorKitForContentType(String paramString, EditorKit paramEditorKit) {
/* 1106 */     if (this.typeHandlers == null) {
/* 1107 */       this.typeHandlers = new Hashtable<>(3);
/*      */     }
/* 1109 */     this.typeHandlers.put(paramString, paramEditorKit);
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
/*      */   public void replaceSelection(String paramString) {
/* 1127 */     if (!isEditable()) {
/* 1128 */       UIManager.getLookAndFeel().provideErrorFeedback(this);
/*      */       return;
/*      */     } 
/* 1131 */     EditorKit editorKit = getEditorKit();
/* 1132 */     if (editorKit instanceof StyledEditorKit) {
/*      */       try {
/* 1134 */         Document document = getDocument();
/* 1135 */         Caret caret = getCaret();
/* 1136 */         boolean bool = saveComposedText(caret.getDot());
/* 1137 */         int i = Math.min(caret.getDot(), caret.getMark());
/* 1138 */         int j = Math.max(caret.getDot(), caret.getMark());
/* 1139 */         if (document instanceof AbstractDocument) {
/* 1140 */           ((AbstractDocument)document).replace(i, j - i, paramString, ((StyledEditorKit)editorKit)
/* 1141 */               .getInputAttributes());
/*      */         } else {
/*      */           
/* 1144 */           if (i != j) {
/* 1145 */             document.remove(i, j - i);
/*      */           }
/* 1147 */           if (paramString != null && paramString.length() > 0) {
/* 1148 */             document.insertString(i, paramString, ((StyledEditorKit)editorKit)
/* 1149 */                 .getInputAttributes());
/*      */           }
/*      */         } 
/* 1152 */         if (bool) {
/* 1153 */           restoreComposedText();
/*      */         }
/* 1155 */       } catch (BadLocationException badLocationException) {
/* 1156 */         UIManager.getLookAndFeel().provideErrorFeedback(this);
/*      */       } 
/*      */     } else {
/*      */       
/* 1160 */       super.replaceSelection(paramString);
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
/*      */   public static EditorKit createEditorKitForContentType(String paramString) {
/* 1182 */     Hashtable<String, EditorKit> hashtable = getKitRegisty();
/* 1183 */     EditorKit editorKit = hashtable.get(paramString);
/* 1184 */     if (editorKit == null) {
/*      */       
/* 1186 */       String str = getKitTypeRegistry().get(paramString);
/* 1187 */       ClassLoader classLoader = getKitLoaderRegistry().get(paramString);
/*      */       try {
/*      */         Class<?> clazz;
/* 1190 */         if (classLoader != null) {
/* 1191 */           clazz = classLoader.loadClass(str);
/*      */         }
/*      */         else {
/*      */           
/* 1195 */           clazz = Class.forName(str, true, Thread.currentThread()
/* 1196 */               .getContextClassLoader());
/*      */         } 
/* 1198 */         editorKit = (EditorKit)clazz.newInstance();
/* 1199 */         hashtable.put(paramString, editorKit);
/* 1200 */       } catch (Throwable throwable) {
/* 1201 */         editorKit = null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1207 */     if (editorKit != null) {
/* 1208 */       return (EditorKit)editorKit.clone();
/*      */     }
/* 1210 */     return null;
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
/*      */   public static void registerEditorKitForContentType(String paramString1, String paramString2) {
/* 1226 */     registerEditorKitForContentType(paramString1, paramString2, Thread.currentThread()
/* 1227 */         .getContextClassLoader());
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
/*      */   public static void registerEditorKitForContentType(String paramString1, String paramString2, ClassLoader paramClassLoader) {
/* 1243 */     getKitTypeRegistry().put(paramString1, paramString2);
/* 1244 */     if (paramClassLoader != null) {
/* 1245 */       getKitLoaderRegistry().put(paramString1, paramClassLoader);
/*      */     } else {
/* 1247 */       getKitLoaderRegistry().remove(paramString1);
/*      */     } 
/* 1249 */     getKitRegisty().remove(paramString1);
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
/*      */   public static String getEditorKitClassNameForContentType(String paramString) {
/* 1261 */     return getKitTypeRegistry().get(paramString);
/*      */   }
/*      */   
/*      */   private static Hashtable<String, String> getKitTypeRegistry() {
/* 1265 */     loadDefaultKitsIfNecessary();
/* 1266 */     return (Hashtable<String, String>)SwingUtilities.appContextGet(kitTypeRegistryKey);
/*      */   }
/*      */   
/*      */   private static Hashtable<String, ClassLoader> getKitLoaderRegistry() {
/* 1270 */     loadDefaultKitsIfNecessary();
/* 1271 */     return (Hashtable<String, ClassLoader>)SwingUtilities.appContextGet(kitLoaderRegistryKey);
/*      */   }
/*      */   
/*      */   private static Hashtable<String, EditorKit> getKitRegisty() {
/* 1275 */     Hashtable<Object, Object> hashtable = (Hashtable)SwingUtilities.appContextGet(kitRegistryKey);
/* 1276 */     if (hashtable == null) {
/* 1277 */       hashtable = new Hashtable<>(3);
/* 1278 */       SwingUtilities.appContextPut(kitRegistryKey, hashtable);
/*      */     } 
/* 1280 */     return (Hashtable)hashtable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void loadDefaultKitsIfNecessary() {
/* 1290 */     if (SwingUtilities.appContextGet(kitTypeRegistryKey) == null) {
/* 1291 */       synchronized (defaultEditorKitMap) {
/* 1292 */         if (defaultEditorKitMap.size() == 0) {
/* 1293 */           defaultEditorKitMap.put("text/plain", "javax.swing.JEditorPane$PlainEditorKit");
/*      */           
/* 1295 */           defaultEditorKitMap.put("text/html", "javax.swing.text.html.HTMLEditorKit");
/*      */           
/* 1297 */           defaultEditorKitMap.put("text/rtf", "javax.swing.text.rtf.RTFEditorKit");
/*      */           
/* 1299 */           defaultEditorKitMap.put("application/rtf", "javax.swing.text.rtf.RTFEditorKit");
/*      */         } 
/*      */       } 
/*      */       
/* 1303 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1304 */       SwingUtilities.appContextPut(kitTypeRegistryKey, hashtable);
/* 1305 */       hashtable = new Hashtable<>();
/* 1306 */       SwingUtilities.appContextPut(kitLoaderRegistryKey, hashtable);
/* 1307 */       for (String str : defaultEditorKitMap.keySet()) {
/* 1308 */         registerEditorKitForContentType(str, defaultEditorKitMap.get(str));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize() {
/* 1333 */     Dimension dimension = super.getPreferredSize();
/* 1334 */     Container container = SwingUtilities.getUnwrappedParent(this);
/* 1335 */     if (container instanceof JViewport) {
/* 1336 */       JViewport jViewport = (JViewport)container;
/* 1337 */       TextUI textUI = getUI();
/* 1338 */       int i = dimension.width;
/* 1339 */       int j = dimension.height;
/* 1340 */       if (!getScrollableTracksViewportWidth()) {
/* 1341 */         int k = jViewport.getWidth();
/* 1342 */         Dimension dimension1 = textUI.getMinimumSize(this);
/* 1343 */         if (k != 0 && k < dimension1.width)
/*      */         {
/* 1345 */           i = dimension1.width;
/*      */         }
/*      */       } 
/* 1348 */       if (!getScrollableTracksViewportHeight()) {
/* 1349 */         int k = jViewport.getHeight();
/* 1350 */         Dimension dimension1 = textUI.getMinimumSize(this);
/* 1351 */         if (k != 0 && k < dimension1.height)
/*      */         {
/* 1353 */           j = dimension1.height;
/*      */         }
/*      */       } 
/* 1356 */       if (i != dimension.width || j != dimension.height) {
/* 1357 */         dimension = new Dimension(i, j);
/*      */       }
/*      */     } 
/* 1360 */     return dimension;
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
/*      */   public void setText(String paramString) {
/*      */     try {
/* 1409 */       Document document = getDocument();
/* 1410 */       document.remove(0, document.getLength());
/* 1411 */       if (paramString == null || paramString.equals("")) {
/*      */         return;
/*      */       }
/* 1414 */       StringReader stringReader = new StringReader(paramString);
/* 1415 */       EditorKit editorKit = getEditorKit();
/* 1416 */       editorKit.read(stringReader, document, 0);
/* 1417 */     } catch (IOException iOException) {
/* 1418 */       UIManager.getLookAndFeel().provideErrorFeedback(this);
/* 1419 */     } catch (BadLocationException badLocationException) {
/* 1420 */       UIManager.getLookAndFeel().provideErrorFeedback(this);
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
/*      */   public String getText() {
/*      */     String str;
/*      */     try {
/* 1438 */       StringWriter stringWriter = new StringWriter();
/* 1439 */       write(stringWriter);
/* 1440 */       str = stringWriter.toString();
/* 1441 */     } catch (IOException iOException) {
/* 1442 */       str = null;
/*      */     } 
/* 1444 */     return str;
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
/*      */   public boolean getScrollableTracksViewportWidth() {
/* 1457 */     Container container = SwingUtilities.getUnwrappedParent(this);
/* 1458 */     if (container instanceof JViewport) {
/* 1459 */       JViewport jViewport = (JViewport)container;
/* 1460 */       TextUI textUI = getUI();
/* 1461 */       int i = jViewport.getWidth();
/* 1462 */       Dimension dimension1 = textUI.getMinimumSize(this);
/* 1463 */       Dimension dimension2 = textUI.getMaximumSize(this);
/* 1464 */       if (i >= dimension1.width && i <= dimension2.width) {
/* 1465 */         return true;
/*      */       }
/*      */     } 
/* 1468 */     return false;
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
/*      */   public boolean getScrollableTracksViewportHeight() {
/* 1480 */     Container container = SwingUtilities.getUnwrappedParent(this);
/* 1481 */     if (container instanceof JViewport) {
/* 1482 */       JViewport jViewport = (JViewport)container;
/* 1483 */       TextUI textUI = getUI();
/* 1484 */       int i = jViewport.getHeight();
/* 1485 */       Dimension dimension = textUI.getMinimumSize(this);
/* 1486 */       if (i >= dimension.height) {
/* 1487 */         Dimension dimension1 = textUI.getMaximumSize(this);
/* 1488 */         if (i <= dimension1.height) {
/* 1489 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/* 1493 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1504 */     paramObjectOutputStream.defaultWriteObject();
/* 1505 */     if (getUIClassID().equals("EditorPaneUI")) {
/* 1506 */       byte b = JComponent.getWriteObjCounter(this);
/* 1507 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 1508 */       if (b == 0 && this.ui != null) {
/* 1509 */         this.ui.installUI(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1537 */   private static final Object kitRegistryKey = new StringBuffer("JEditorPane.kitRegistry");
/*      */   
/* 1539 */   private static final Object kitTypeRegistryKey = new StringBuffer("JEditorPane.kitTypeRegistry");
/*      */   
/* 1541 */   private static final Object kitLoaderRegistryKey = new StringBuffer("JEditorPane.kitLoaderRegistry");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String uiClassID = "EditorPaneUI";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String W3C_LENGTH_UNITS = "JEditorPane.w3cLengthUnits";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String HONOR_DISPLAY_PROPERTIES = "JEditorPane.honorDisplayProperties";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1578 */   static final Map<String, String> defaultEditorKitMap = new HashMap<>(0);
/*      */ 
/*      */ 
/*      */ 
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
/* 1592 */     String str1 = (this.kit != null) ? this.kit.toString() : "";
/*      */     
/* 1594 */     String str2 = (this.typeHandlers != null) ? this.typeHandlers.toString() : "";
/*      */     
/* 1596 */     return super.paramString() + ",kit=" + str1 + ",typeHandlers=" + str2;
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 1617 */     if (getEditorKit() instanceof HTMLEditorKit) {
/* 1618 */       if (this.accessibleContext == null || this.accessibleContext.getClass() != AccessibleJEditorPaneHTML.class)
/*      */       {
/* 1620 */         this.accessibleContext = new AccessibleJEditorPaneHTML();
/*      */       }
/* 1622 */     } else if (this.accessibleContext == null || this.accessibleContext.getClass() != AccessibleJEditorPane.class) {
/*      */       
/* 1624 */       this.accessibleContext = new AccessibleJEditorPane();
/*      */     } 
/* 1626 */     return this.accessibleContext;
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
/*      */   protected class AccessibleJEditorPane
/*      */     extends JTextComponent.AccessibleJTextComponent
/*      */   {
/*      */     public String getAccessibleDescription() {
/* 1657 */       String str = this.accessibleDescription;
/*      */ 
/*      */       
/* 1660 */       if (str == null) {
/* 1661 */         str = (String)JEditorPane.this.getClientProperty("AccessibleDescription");
/*      */       }
/* 1663 */       if (str == null) {
/* 1664 */         str = JEditorPane.this.getContentType();
/*      */       }
/* 1666 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/* 1677 */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 1678 */       accessibleStateSet.add(AccessibleState.MULTI_LINE);
/* 1679 */       return accessibleStateSet;
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
/*      */   protected class AccessibleJEditorPaneHTML
/*      */     extends AccessibleJEditorPane
/*      */   {
/*      */     private AccessibleContext accessibleContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleText getAccessibleText() {
/* 1703 */       return new JEditorPane.JEditorPaneAccessibleHypertextSupport();
/*      */     }
/*      */     
/*      */     protected AccessibleJEditorPaneHTML() {
/* 1707 */       HTMLEditorKit hTMLEditorKit = (HTMLEditorKit)JEditorPane.this.getEditorKit();
/* 1708 */       this.accessibleContext = hTMLEditorKit.getAccessibleContext();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/* 1717 */       if (this.accessibleContext != null) {
/* 1718 */         return this.accessibleContext.getAccessibleChildrenCount();
/*      */       }
/* 1720 */       return 0;
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
/*      */     public Accessible getAccessibleChild(int param1Int) {
/* 1735 */       if (this.accessibleContext != null) {
/* 1736 */         return this.accessibleContext.getAccessibleChild(param1Int);
/*      */       }
/* 1738 */       return null;
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
/*      */     public Accessible getAccessibleAt(Point param1Point) {
/* 1751 */       if (this.accessibleContext != null && param1Point != null) {
/*      */         
/*      */         try {
/* 1754 */           AccessibleComponent accessibleComponent = this.accessibleContext.getAccessibleComponent();
/* 1755 */           if (accessibleComponent != null) {
/* 1756 */             return accessibleComponent.getAccessibleAt(param1Point);
/*      */           }
/* 1758 */           return null;
/*      */         }
/* 1760 */         catch (IllegalComponentStateException illegalComponentStateException) {
/* 1761 */           return null;
/*      */         } 
/*      */       }
/* 1764 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class JEditorPaneAccessibleHypertextSupport
/*      */     extends AccessibleJEditorPane
/*      */     implements AccessibleHypertext
/*      */   {
/*      */     LinkVector hyperlinks;
/*      */ 
/*      */ 
/*      */     
/*      */     public class HTMLLink
/*      */       extends AccessibleHyperlink
/*      */     {
/*      */       Element element;
/*      */ 
/*      */       
/*      */       public HTMLLink(Element param2Element) {
/* 1785 */         this.element = param2Element;
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
/*      */       public boolean isValid() {
/* 1797 */         return JEditorPane.JEditorPaneAccessibleHypertextSupport.this.linksValid;
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
/*      */       public int getAccessibleActionCount() {
/* 1809 */         return 1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean doAccessibleAction(int param2Int) {
/* 1820 */         if (param2Int == 0 && isValid() == true) {
/* 1821 */           URL uRL = (URL)getAccessibleActionObject(param2Int);
/* 1822 */           if (uRL != null) {
/* 1823 */             HyperlinkEvent hyperlinkEvent = new HyperlinkEvent(JEditorPane.this, HyperlinkEvent.EventType.ACTIVATED, uRL);
/*      */             
/* 1825 */             JEditorPane.this.fireHyperlinkUpdate(hyperlinkEvent);
/* 1826 */             return true;
/*      */           } 
/*      */         } 
/* 1829 */         return false;
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
/*      */       public String getAccessibleActionDescription(int param2Int) {
/* 1843 */         if (param2Int == 0 && isValid() == true) {
/* 1844 */           Document document = JEditorPane.this.getDocument();
/* 1845 */           if (document != null) {
/*      */             try {
/* 1847 */               return document.getText(getStartIndex(), 
/* 1848 */                   getEndIndex() - getStartIndex());
/* 1849 */             } catch (BadLocationException badLocationException) {
/* 1850 */               return null;
/*      */             } 
/*      */           }
/*      */         } 
/* 1854 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Object getAccessibleActionObject(int param2Int) {
/* 1865 */         if (param2Int == 0 && isValid() == true) {
/* 1866 */           AttributeSet attributeSet1 = this.element.getAttributes();
/*      */           
/* 1868 */           AttributeSet attributeSet2 = (AttributeSet)attributeSet1.getAttribute(HTML.Tag.A);
/*      */           
/* 1870 */           String str = (attributeSet2 != null) ? (String)attributeSet2.getAttribute(HTML.Attribute.HREF) : null;
/* 1871 */           if (str != null) {
/*      */             Object object;
/*      */             try {
/* 1874 */               object = new URL(JEditorPane.this.getPage(), str);
/* 1875 */             } catch (MalformedURLException malformedURLException) {
/* 1876 */               object = null;
/*      */             } 
/* 1878 */             return object;
/*      */           } 
/*      */         } 
/* 1881 */         return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Object getAccessibleActionAnchor(int param2Int) {
/* 1900 */         return getAccessibleActionDescription(param2Int);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getStartIndex() {
/* 1911 */         return this.element.getStartOffset();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getEndIndex() {
/* 1921 */         return this.element.getEndOffset();
/*      */       } }
/*      */     
/*      */     private class LinkVector extends Vector<HTMLLink> {
/*      */       private LinkVector() {}
/*      */       
/*      */       public int baseElementIndex(Element param2Element) {
/* 1928 */         for (byte b = 0; b < this.elementCount; b++) {
/* 1929 */           JEditorPane.JEditorPaneAccessibleHypertextSupport.HTMLLink hTMLLink = elementAt(b);
/* 1930 */           if (hTMLLink.element == param2Element) {
/* 1931 */             return b;
/*      */           }
/*      */         } 
/* 1934 */         return -1;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     boolean linksValid = false;
/*      */ 
/*      */ 
/*      */     
/*      */     private void buildLinkTable() {
/* 1945 */       this.hyperlinks.removeAllElements();
/* 1946 */       Document document = JEditorPane.this.getDocument();
/* 1947 */       if (document != null) {
/* 1948 */         ElementIterator elementIterator = new ElementIterator(document);
/*      */ 
/*      */         
/*      */         Element element;
/*      */         
/* 1953 */         while ((element = elementIterator.next()) != null) {
/* 1954 */           if (element.isLeaf()) {
/* 1955 */             AttributeSet attributeSet1 = element.getAttributes();
/* 1956 */             AttributeSet attributeSet2 = (AttributeSet)attributeSet1.getAttribute(HTML.Tag.A);
/*      */             
/* 1958 */             String str = (attributeSet2 != null) ? (String)attributeSet2.getAttribute(HTML.Attribute.HREF) : null;
/* 1959 */             if (str != null) {
/* 1960 */               this.hyperlinks.addElement(new HTMLLink(element));
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 1965 */       this.linksValid = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JEditorPaneAccessibleHypertextSupport() {
/* 1972 */       this.hyperlinks = new LinkVector();
/* 1973 */       Document document = JEditorPane.this.getDocument();
/* 1974 */       if (document != null) {
/* 1975 */         document.addDocumentListener(new DocumentListener() {
/*      */               public void changedUpdate(DocumentEvent param2DocumentEvent) {
/* 1977 */                 JEditorPane.JEditorPaneAccessibleHypertextSupport.this.linksValid = false;
/*      */               }
/*      */               public void insertUpdate(DocumentEvent param2DocumentEvent) {
/* 1980 */                 JEditorPane.JEditorPaneAccessibleHypertextSupport.this.linksValid = false;
/*      */               }
/*      */               public void removeUpdate(DocumentEvent param2DocumentEvent) {
/* 1983 */                 JEditorPane.JEditorPaneAccessibleHypertextSupport.this.linksValid = false;
/*      */               }
/*      */             });
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getLinkCount() {
/* 1995 */       if (!this.linksValid) {
/* 1996 */         buildLinkTable();
/*      */       }
/* 1998 */       return this.hyperlinks.size();
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
/*      */     public int getLinkIndex(int param1Int) {
/* 2010 */       if (!this.linksValid) {
/* 2011 */         buildLinkTable();
/*      */       }
/* 2013 */       Element element = null;
/* 2014 */       Document document = JEditorPane.this.getDocument();
/* 2015 */       if (document != null) {
/* 2016 */         for (element = document.getDefaultRootElement(); !element.isLeaf(); ) {
/* 2017 */           int i = element.getElementIndex(param1Int);
/* 2018 */           element = element.getElement(i);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2025 */       return this.hyperlinks.baseElementIndex(element);
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
/*      */     public AccessibleHyperlink getLink(int param1Int) {
/* 2037 */       if (!this.linksValid) {
/* 2038 */         buildLinkTable();
/*      */       }
/* 2040 */       if (param1Int >= 0 && param1Int < this.hyperlinks.size()) {
/* 2041 */         return this.hyperlinks.elementAt(param1Int);
/*      */       }
/* 2043 */       return null;
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
/*      */     public String getLinkText(int param1Int) {
/* 2055 */       if (!this.linksValid) {
/* 2056 */         buildLinkTable();
/*      */       }
/* 2058 */       Element element = (Element)this.hyperlinks.elementAt(param1Int);
/* 2059 */       if (element != null) {
/* 2060 */         Document document = JEditorPane.this.getDocument();
/* 2061 */         if (document != null) {
/*      */           try {
/* 2063 */             return document.getText(element.getStartOffset(), element
/* 2064 */                 .getEndOffset() - element.getStartOffset());
/* 2065 */           } catch (BadLocationException badLocationException) {
/* 2066 */             return null;
/*      */           } 
/*      */         }
/*      */       } 
/* 2070 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class PlainEditorKit
/*      */     extends DefaultEditorKit
/*      */     implements ViewFactory
/*      */   {
/*      */     public ViewFactory getViewFactory() {
/* 2085 */       return this;
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
/*      */     public View create(Element param1Element) {
/* 2097 */       Document document = param1Element.getDocument();
/*      */       
/* 2099 */       Object object = document.getProperty("i18n");
/* 2100 */       if (object != null && object.equals(Boolean.TRUE))
/*      */       {
/* 2102 */         return createI18N(param1Element);
/*      */       }
/* 2104 */       return new WrappedPlainView(param1Element);
/*      */     }
/*      */ 
/*      */     
/*      */     View createI18N(Element param1Element) {
/* 2109 */       String str = param1Element.getName();
/* 2110 */       if (str != null) {
/* 2111 */         if (str.equals("content"))
/* 2112 */           return new PlainParagraph(param1Element); 
/* 2113 */         if (str.equals("paragraph")) {
/* 2114 */           return new BoxView(param1Element, 1);
/*      */         }
/*      */       } 
/* 2117 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static class PlainParagraph
/*      */       extends ParagraphView
/*      */     {
/*      */       PlainParagraph(Element param2Element) {
/* 2127 */         super(param2Element);
/* 2128 */         this.layoutPool = new LogicalView(param2Element);
/* 2129 */         this.layoutPool.setParent(this);
/*      */       }
/*      */       
/*      */       protected void setPropertiesFromAttributes() {
/* 2133 */         Container container = getContainer();
/* 2134 */         if (container != null && 
/* 2135 */           !container.getComponentOrientation().isLeftToRight()) {
/*      */           
/* 2137 */           setJustification(2);
/*      */         } else {
/* 2139 */           setJustification(0);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getFlowSpan(int param2Int) {
/* 2148 */         Container container = getContainer();
/* 2149 */         if (container instanceof JTextArea) {
/* 2150 */           JTextArea jTextArea = (JTextArea)container;
/* 2151 */           if (!jTextArea.getLineWrap())
/*      */           {
/* 2153 */             return Integer.MAX_VALUE;
/*      */           }
/*      */         } 
/* 2156 */         return super.getFlowSpan(param2Int);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       protected SizeRequirements calculateMinorAxisRequirements(int param2Int, SizeRequirements param2SizeRequirements) {
/* 2163 */         SizeRequirements sizeRequirements = super.calculateMinorAxisRequirements(param2Int, param2SizeRequirements);
/* 2164 */         Container container = getContainer();
/* 2165 */         if (container instanceof JTextArea) {
/* 2166 */           JTextArea jTextArea = (JTextArea)container;
/* 2167 */           if (!jTextArea.getLineWrap())
/*      */           {
/* 2169 */             sizeRequirements.minimum = sizeRequirements.preferred;
/*      */           }
/*      */         } 
/* 2172 */         return sizeRequirements;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       static class LogicalView
/*      */         extends CompositeView
/*      */       {
/*      */         LogicalView(Element param3Element) {
/* 2185 */           super(param3Element);
/*      */         }
/*      */         
/*      */         protected int getViewIndexAtPosition(int param3Int) {
/* 2189 */           Element element = getElement();
/* 2190 */           if (element.getElementCount() > 0) {
/* 2191 */             return element.getElementIndex(param3Int);
/*      */           }
/* 2193 */           return 0;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         protected boolean updateChildren(DocumentEvent.ElementChange param3ElementChange, DocumentEvent param3DocumentEvent, ViewFactory param3ViewFactory) {
/* 2200 */           return false;
/*      */         }
/*      */         
/*      */         protected void loadChildren(ViewFactory param3ViewFactory) {
/* 2204 */           Element element = getElement();
/* 2205 */           if (element.getElementCount() > 0) {
/* 2206 */             super.loadChildren(param3ViewFactory);
/*      */           } else {
/* 2208 */             GlyphView glyphView = new GlyphView(element);
/* 2209 */             append(glyphView);
/*      */           } 
/*      */         }
/*      */         
/*      */         public float getPreferredSpan(int param3Int) {
/* 2214 */           if (getViewCount() != 1) {
/* 2215 */             throw new Error("One child view is assumed.");
/*      */           }
/* 2217 */           View view = getView(0);
/*      */           
/* 2219 */           return view.getPreferredSpan(param3Int);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         protected void forwardUpdateToView(View param3View, DocumentEvent param3DocumentEvent, Shape param3Shape, ViewFactory param3ViewFactory) {
/* 2239 */           param3View.setParent(this);
/* 2240 */           super.forwardUpdateToView(param3View, param3DocumentEvent, param3Shape, param3ViewFactory);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void paint(Graphics param3Graphics, Shape param3Shape) {}
/*      */ 
/*      */ 
/*      */         
/*      */         protected boolean isBefore(int param3Int1, int param3Int2, Rectangle param3Rectangle) {
/* 2250 */           return false;
/*      */         }
/*      */         
/*      */         protected boolean isAfter(int param3Int1, int param3Int2, Rectangle param3Rectangle) {
/* 2254 */           return false;
/*      */         }
/*      */         
/*      */         protected View getViewAtPoint(int param3Int1, int param3Int2, Rectangle param3Rectangle) {
/* 2258 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         protected void childAllocation(int param3Int, Rectangle param3Rectangle) {}
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class HeaderParser
/*      */   {
/*      */     String raw;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String[][] tab;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public HeaderParser(String param1String) {
/* 2293 */       this.raw = param1String;
/* 2294 */       this.tab = new String[10][2];
/* 2295 */       parse();
/*      */     }
/*      */ 
/*      */     
/*      */     private void parse() {
/* 2300 */       if (this.raw != null) {
/* 2301 */         this.raw = this.raw.trim();
/* 2302 */         char[] arrayOfChar = this.raw.toCharArray();
/* 2303 */         byte b1 = 0, b2 = 0, b3 = 0;
/* 2304 */         boolean bool1 = true;
/* 2305 */         boolean bool2 = false;
/* 2306 */         int i = arrayOfChar.length;
/* 2307 */         while (b2 < i) {
/* 2308 */           char c = arrayOfChar[b2];
/* 2309 */           if (c == '=') {
/* 2310 */             this.tab[b3][0] = (new String(arrayOfChar, b1, b2 - b1)).toLowerCase();
/* 2311 */             bool1 = false;
/*      */             
/* 2313 */             b1 = ++b2; continue;
/* 2314 */           }  if (c == '"') {
/* 2315 */             if (bool2) {
/* 2316 */               this.tab[b3++][1] = new String(arrayOfChar, b1, b2 - b1);
/* 2317 */               bool2 = false;
/*      */               do {
/* 2319 */                 b2++;
/* 2320 */               } while (b2 < i && (arrayOfChar[b2] == ' ' || arrayOfChar[b2] == ','));
/* 2321 */               bool1 = true;
/* 2322 */               b1 = b2; continue;
/*      */             } 
/* 2324 */             bool2 = true;
/*      */             
/* 2326 */             b1 = ++b2; continue;
/*      */           } 
/* 2328 */           if (c == ' ' || c == ',') {
/* 2329 */             if (bool2) {
/* 2330 */               b2++; continue;
/*      */             } 
/* 2332 */             if (bool1) {
/* 2333 */               this.tab[b3++][0] = (new String(arrayOfChar, b1, b2 - b1)).toLowerCase();
/*      */             } else {
/* 2335 */               this.tab[b3++][1] = new String(arrayOfChar, b1, b2 - b1);
/*      */             } 
/* 2337 */             while (b2 < i && (arrayOfChar[b2] == ' ' || arrayOfChar[b2] == ',')) {
/* 2338 */               b2++;
/*      */             }
/* 2340 */             bool1 = true;
/* 2341 */             b1 = b2; continue;
/*      */           } 
/* 2343 */           b2++;
/*      */         } 
/*      */ 
/*      */         
/* 2347 */         if (--b2 > b1) {
/* 2348 */           if (!bool1) {
/* 2349 */             if (arrayOfChar[b2] == '"') {
/* 2350 */               this.tab[b3++][1] = new String(arrayOfChar, b1, b2 - b1);
/*      */             } else {
/* 2352 */               this.tab[b3++][1] = new String(arrayOfChar, b1, b2 - b1 + 1);
/*      */             } 
/*      */           } else {
/* 2355 */             this.tab[b3][0] = (new String(arrayOfChar, b1, b2 - b1 + 1)).toLowerCase();
/*      */           } 
/* 2357 */         } else if (b2 == b1) {
/* 2358 */           if (!bool1) {
/* 2359 */             if (arrayOfChar[b2] == '"') {
/* 2360 */               this.tab[b3++][1] = String.valueOf(arrayOfChar[b2 - 1]);
/*      */             } else {
/* 2362 */               this.tab[b3++][1] = String.valueOf(arrayOfChar[b2]);
/*      */             } 
/*      */           } else {
/* 2365 */             this.tab[b3][0] = String.valueOf(arrayOfChar[b2]).toLowerCase();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public String findKey(int param1Int) {
/* 2373 */       if (param1Int < 0 || param1Int > 10)
/* 2374 */         return null; 
/* 2375 */       return this.tab[param1Int][0];
/*      */     }
/*      */     
/*      */     public String findValue(int param1Int) {
/* 2379 */       if (param1Int < 0 || param1Int > 10)
/* 2380 */         return null; 
/* 2381 */       return this.tab[param1Int][1];
/*      */     }
/*      */     
/*      */     public String findValue(String param1String) {
/* 2385 */       return findValue(param1String, null);
/*      */     }
/*      */     
/*      */     public String findValue(String param1String1, String param1String2) {
/* 2389 */       if (param1String1 == null)
/* 2390 */         return param1String2; 
/* 2391 */       param1String1 = param1String1.toLowerCase();
/* 2392 */       for (byte b = 0; b < 10; b++) {
/* 2393 */         if (this.tab[b][0] == null)
/* 2394 */           return param1String2; 
/* 2395 */         if (param1String1.equals(this.tab[b][0])) {
/* 2396 */           return this.tab[b][1];
/*      */         }
/*      */       } 
/* 2399 */       return param1String2;
/*      */     }
/*      */     
/*      */     public int findInt(String param1String, int param1Int) {
/*      */       try {
/* 2404 */         return Integer.parseInt(findValue(param1String, String.valueOf(param1Int)));
/* 2405 */       } catch (Throwable throwable) {
/* 2406 */         return param1Int;
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JEditorPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */