/*      */ package sun.applet;
/*      */ 
/*      */ import java.applet.Applet;
/*      */ import java.applet.AppletContext;
/*      */ import java.applet.AudioClip;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FileDialog;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Label;
/*      */ import java.awt.Menu;
/*      */ import java.awt.MenuBar;
/*      */ import java.awt.MenuItem;
/*      */ import java.awt.Point;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Reader;
/*      */ import java.net.SocketPermission;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permission;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.misc.Ref;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AppletViewer
/*      */   extends Frame
/*      */   implements AppletContext, Printable
/*      */ {
/*  124 */   private static String defaultSaveFile = "Applet.ser";
/*      */ 
/*      */ 
/*      */   
/*      */   AppletViewerPanel panel;
/*      */ 
/*      */ 
/*      */   
/*      */   Label label;
/*      */ 
/*      */ 
/*      */   
/*      */   PrintStream statusMsgStream;
/*      */ 
/*      */   
/*      */   AppletViewerFactory factory;
/*      */ 
/*      */ 
/*      */   
/*      */   private final class UserActionListener
/*      */     implements ActionListener
/*      */   {
/*      */     private UserActionListener() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  151 */       AppletViewer.this.processUserAction(param1ActionEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AppletViewer(int paramInt1, int paramInt2, URL paramURL, Hashtable<String, String> paramHashtable, PrintStream paramPrintStream, AppletViewerFactory paramAppletViewerFactory) {
/*  160 */     this.factory = paramAppletViewerFactory;
/*  161 */     this.statusMsgStream = paramPrintStream;
/*  162 */     setTitle(amh.getMessage("tool.title", paramHashtable.get("code")));
/*      */     
/*  164 */     MenuBar menuBar = paramAppletViewerFactory.getBaseMenuBar();
/*      */     
/*  166 */     Menu menu = new Menu(amh.getMessage("menu.applet"));
/*      */     
/*  168 */     addMenuItem(menu, "menuitem.restart");
/*  169 */     addMenuItem(menu, "menuitem.reload");
/*  170 */     addMenuItem(menu, "menuitem.stop");
/*  171 */     addMenuItem(menu, "menuitem.save");
/*  172 */     addMenuItem(menu, "menuitem.start");
/*  173 */     addMenuItem(menu, "menuitem.clone");
/*  174 */     menu.add(new MenuItem("-"));
/*  175 */     addMenuItem(menu, "menuitem.tag");
/*  176 */     addMenuItem(menu, "menuitem.info");
/*  177 */     addMenuItem(menu, "menuitem.edit").disable();
/*  178 */     addMenuItem(menu, "menuitem.encoding");
/*  179 */     menu.add(new MenuItem("-"));
/*  180 */     addMenuItem(menu, "menuitem.print");
/*  181 */     menu.add(new MenuItem("-"));
/*  182 */     addMenuItem(menu, "menuitem.props");
/*  183 */     menu.add(new MenuItem("-"));
/*  184 */     addMenuItem(menu, "menuitem.close");
/*  185 */     if (paramAppletViewerFactory.isStandalone()) {
/*  186 */       addMenuItem(menu, "menuitem.quit");
/*      */     }
/*      */     
/*  189 */     menuBar.add(menu);
/*      */     
/*  191 */     setMenuBar(menuBar);
/*      */     
/*  193 */     add("Center", this.panel = new AppletViewerPanel(paramURL, paramHashtable));
/*  194 */     add("South", this.label = new Label(amh.getMessage("label.hello")));
/*  195 */     this.panel.init();
/*  196 */     appletPanels.addElement(this.panel);
/*      */     
/*  198 */     pack();
/*  199 */     move(paramInt1, paramInt2);
/*  200 */     setVisible(true);
/*      */     
/*  202 */     WindowAdapter windowAdapter = new WindowAdapter()
/*      */       {
/*      */         public void windowClosing(WindowEvent param1WindowEvent)
/*      */         {
/*  206 */           AppletViewer.this.appletClose();
/*      */         }
/*      */ 
/*      */         
/*      */         public void windowIconified(WindowEvent param1WindowEvent) {
/*  211 */           AppletViewer.this.appletStop();
/*      */         }
/*      */ 
/*      */         
/*      */         public void windowDeiconified(WindowEvent param1WindowEvent) {
/*  216 */           AppletViewer.this.appletStart();
/*      */         }
/*      */       };
/*      */     class AppletEventListener
/*      */       implements AppletListener
/*      */     {
/*      */       final Frame frame;
/*      */ 
/*      */       
/*      */       public AppletEventListener(Frame param1Frame) {
/*  226 */         this.frame = param1Frame;
/*      */       }
/*      */ 
/*      */       
/*      */       public void appletStateChanged(AppletEvent param1AppletEvent) {
/*      */         Applet applet;
/*  232 */         AppletPanel appletPanel = (AppletPanel)param1AppletEvent.getSource();
/*      */         
/*  234 */         switch (param1AppletEvent.getID()) {
/*      */           case 51234:
/*  236 */             if (appletPanel != null) {
/*  237 */               AppletViewer.this.resize(AppletViewer.this.preferredSize());
/*  238 */               AppletViewer.this.validate();
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 51236:
/*  243 */             applet = appletPanel.getApplet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  259 */             if (applet != null) {
/*  260 */               AppletPanel.changeFrameAppContext(this.frame, SunToolkit.targetToAppContext(applet)); break;
/*      */             } 
/*  262 */             AppletPanel.changeFrameAppContext(this.frame, AppContext.getAppContext());
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */     };
/*  270 */     addWindowListener(windowAdapter);
/*  271 */     this.panel.addAppletListener(new AppletEventListener(this));
/*      */ 
/*      */     
/*  274 */     showStatus(amh.getMessage("status.start"));
/*  275 */     initEventQueue();
/*      */   }
/*      */ 
/*      */   
/*      */   public MenuItem addMenuItem(Menu paramMenu, String paramString) {
/*  280 */     MenuItem menuItem = new MenuItem(amh.getMessage(paramString));
/*  281 */     menuItem.addActionListener(new UserActionListener());
/*  282 */     return paramMenu.add(menuItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initEventQueue() {
/*  293 */     String str = System.getProperty("appletviewer.send.event");
/*      */     
/*  295 */     if (str == null) {
/*      */       
/*  297 */       this.panel.sendEvent(1);
/*  298 */       this.panel.sendEvent(2);
/*  299 */       this.panel.sendEvent(3);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/*  307 */       String[] arrayOfString = splitSeparator(",", str);
/*      */       
/*  309 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*  310 */         System.out.println("Adding event to queue: " + arrayOfString[b]);
/*  311 */         if (arrayOfString[b].equals("dispose")) {
/*  312 */           this.panel.sendEvent(0);
/*  313 */         } else if (arrayOfString[b].equals("load")) {
/*  314 */           this.panel.sendEvent(1);
/*  315 */         } else if (arrayOfString[b].equals("init")) {
/*  316 */           this.panel.sendEvent(2);
/*  317 */         } else if (arrayOfString[b].equals("start")) {
/*  318 */           this.panel.sendEvent(3);
/*  319 */         } else if (arrayOfString[b].equals("stop")) {
/*  320 */           this.panel.sendEvent(4);
/*  321 */         } else if (arrayOfString[b].equals("destroy")) {
/*  322 */           this.panel.sendEvent(5);
/*  323 */         } else if (arrayOfString[b].equals("quit")) {
/*  324 */           this.panel.sendEvent(6);
/*  325 */         } else if (arrayOfString[b].equals("error")) {
/*  326 */           this.panel.sendEvent(7);
/*      */         } else {
/*      */           
/*  329 */           System.out.println("Unrecognized event name: " + arrayOfString[b]);
/*      */         } 
/*      */       } 
/*  332 */       while (!this.panel.emptyEventQueue());
/*  333 */       appletSystemExit();
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
/*      */   private String[] splitSeparator(String paramString1, String paramString2) {
/*  352 */     Vector<String> vector = new Vector();
/*  353 */     int i = 0;
/*  354 */     int j = 0;
/*      */     
/*  356 */     while ((j = paramString2.indexOf(paramString1, i)) != -1) {
/*  357 */       vector.addElement(paramString2.substring(i, j));
/*  358 */       i = j + 1;
/*      */     } 
/*      */     
/*  361 */     vector.addElement(paramString2.substring(i));
/*      */     
/*  363 */     String[] arrayOfString = new String[vector.size()];
/*  364 */     vector.copyInto((Object[])arrayOfString);
/*  365 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  372 */   private static Map audioClips = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AudioClip getAudioClip(URL paramURL) {
/*  379 */     checkConnect(paramURL);
/*  380 */     synchronized (audioClips) {
/*  381 */       AudioClip audioClip = (AudioClip)audioClips.get(paramURL);
/*  382 */       if (audioClip == null) {
/*  383 */         audioClips.put(paramURL, audioClip = new AppletAudioClip(paramURL));
/*      */       }
/*  385 */       return audioClip;
/*      */     } 
/*      */   }
/*      */   
/*  389 */   private static Map imageRefs = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Image getImage(URL paramURL) {
/*  396 */     return getCachedImage(paramURL);
/*      */   }
/*      */ 
/*      */   
/*      */   static Image getCachedImage(URL paramURL) {
/*  401 */     return (Image)getCachedImageRef(paramURL).get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Ref getCachedImageRef(URL paramURL) {
/*  408 */     synchronized (imageRefs) {
/*  409 */       AppletImageRef appletImageRef = (AppletImageRef)imageRefs.get(paramURL);
/*  410 */       if (appletImageRef == null) {
/*  411 */         appletImageRef = new AppletImageRef(paramURL);
/*  412 */         imageRefs.put(paramURL, appletImageRef);
/*      */       } 
/*  414 */       return appletImageRef;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void flushImageCache() {
/*  422 */     imageRefs.clear();
/*      */   }
/*      */   
/*  425 */   static Vector appletPanels = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Applet getApplet(String paramString) {
/*  432 */     AppletSecurity appletSecurity = (AppletSecurity)System.getSecurityManager();
/*  433 */     paramString = paramString.toLowerCase();
/*      */     
/*  435 */     SocketPermission socketPermission = new SocketPermission(this.panel.getCodeBase().getHost(), "connect");
/*  436 */     for (Enumeration<AppletPanel> enumeration = appletPanels.elements(); enumeration.hasMoreElements(); ) {
/*  437 */       AppletPanel appletPanel = enumeration.nextElement();
/*  438 */       String str = appletPanel.getParameter("name");
/*  439 */       if (str != null) {
/*  440 */         str = str.toLowerCase();
/*      */       }
/*  442 */       if (paramString.equals(str) && appletPanel
/*  443 */         .getDocumentBase().equals(this.panel.getDocumentBase())) {
/*      */ 
/*      */         
/*  446 */         SocketPermission socketPermission1 = new SocketPermission(appletPanel.getCodeBase().getHost(), "connect");
/*      */         
/*  448 */         if (socketPermission.implies(socketPermission1)) {
/*  449 */           return appletPanel.applet;
/*      */         }
/*      */       } 
/*      */     } 
/*  453 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration getApplets() {
/*  462 */     AppletSecurity appletSecurity = (AppletSecurity)System.getSecurityManager();
/*  463 */     Vector<Applet> vector = new Vector();
/*      */     
/*  465 */     SocketPermission socketPermission = new SocketPermission(this.panel.getCodeBase().getHost(), "connect");
/*      */     
/*  467 */     for (Enumeration<AppletPanel> enumeration = appletPanels.elements(); enumeration.hasMoreElements(); ) {
/*  468 */       AppletPanel appletPanel = enumeration.nextElement();
/*  469 */       if (appletPanel.getDocumentBase().equals(this.panel.getDocumentBase())) {
/*      */ 
/*      */         
/*  472 */         SocketPermission socketPermission1 = new SocketPermission(appletPanel.getCodeBase().getHost(), "connect");
/*  473 */         if (socketPermission.implies(socketPermission1)) {
/*  474 */           vector.addElement(appletPanel.applet);
/*      */         }
/*      */       } 
/*      */     } 
/*  478 */     return vector.elements();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showDocument(URL paramURL) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showDocument(URL paramURL, String paramString) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showStatus(String paramString) {
/*  500 */     this.label.setText(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStream(String paramString, InputStream paramInputStream) throws IOException {}
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getStream(String paramString) {
/*  511 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator getStreamKeys() {
/*  517 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  523 */   static Hashtable systemParam = new Hashtable<>(); static AppletProps props;
/*      */   
/*      */   static {
/*  526 */     systemParam.put("codebase", "codebase");
/*  527 */     systemParam.put("code", "code");
/*  528 */     systemParam.put("alt", "alt");
/*  529 */     systemParam.put("width", "width");
/*  530 */     systemParam.put("height", "height");
/*  531 */     systemParam.put("align", "align");
/*  532 */     systemParam.put("vspace", "vspace");
/*  533 */     systemParam.put("hspace", "hspace");
/*      */   }
/*      */ 
/*      */   
/*      */   static int c;
/*      */   
/*      */   public static void printTag(PrintStream paramPrintStream, Hashtable paramHashtable) {
/*  540 */     paramPrintStream.print("<applet");
/*      */     
/*  542 */     String str = (String)paramHashtable.get("codebase");
/*  543 */     if (str != null) {
/*  544 */       paramPrintStream.print(" codebase=\"" + str + "\"");
/*      */     }
/*      */     
/*  547 */     str = (String)paramHashtable.get("code");
/*  548 */     if (str == null) {
/*  549 */       str = "applet.class";
/*      */     }
/*  551 */     paramPrintStream.print(" code=\"" + str + "\"");
/*  552 */     str = (String)paramHashtable.get("width");
/*  553 */     if (str == null) {
/*  554 */       str = "150";
/*      */     }
/*  556 */     paramPrintStream.print(" width=" + str);
/*      */     
/*  558 */     str = (String)paramHashtable.get("height");
/*  559 */     if (str == null) {
/*  560 */       str = "100";
/*      */     }
/*  562 */     paramPrintStream.print(" height=" + str);
/*      */     
/*  564 */     str = (String)paramHashtable.get("name");
/*  565 */     if (str != null) {
/*  566 */       paramPrintStream.print(" name=\"" + str + "\"");
/*      */     }
/*  568 */     paramPrintStream.println(">");
/*      */ 
/*      */     
/*  571 */     int i = paramHashtable.size();
/*  572 */     String[] arrayOfString = new String[i];
/*  573 */     i = 0;
/*  574 */     for (Enumeration<String> enumeration = paramHashtable.keys(); enumeration.hasMoreElements(); ) {
/*  575 */       String str1 = enumeration.nextElement();
/*  576 */       byte b1 = 0;
/*  577 */       for (; b1 < i && 
/*  578 */         arrayOfString[b1].compareTo(str1) < 0; b1++);
/*      */ 
/*      */ 
/*      */       
/*  582 */       System.arraycopy(arrayOfString, b1, arrayOfString, b1 + 1, i - b1);
/*  583 */       arrayOfString[b1] = str1;
/*  584 */       i++;
/*      */     } 
/*      */     
/*  587 */     for (byte b = 0; b < i; b++) {
/*  588 */       String str1 = arrayOfString[b];
/*  589 */       if (systemParam.get(str1) == null) {
/*  590 */         paramPrintStream.println("<param name=" + str1 + " value=\"" + paramHashtable
/*  591 */             .get(str1) + "\">");
/*      */       }
/*      */     } 
/*  594 */     paramPrintStream.println("</applet>");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAtts() {
/*  601 */     Dimension dimension = this.panel.size();
/*  602 */     Insets insets = this.panel.insets();
/*  603 */     this.panel.atts.put("width", 
/*  604 */         Integer.toString(dimension.width - insets.left + insets.right));
/*  605 */     this.panel.atts.put("height", 
/*  606 */         Integer.toString(dimension.height - insets.top + insets.bottom));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletRestart() {
/*  613 */     this.panel.sendEvent(4);
/*  614 */     this.panel.sendEvent(5);
/*  615 */     this.panel.sendEvent(2);
/*  616 */     this.panel.sendEvent(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletReload() {
/*  623 */     this.panel.sendEvent(4);
/*  624 */     this.panel.sendEvent(5);
/*  625 */     this.panel.sendEvent(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  632 */     AppletPanel.flushClassLoader(this.panel.getClassLoaderCacheKey());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  639 */       this.panel.joinAppletThread();
/*  640 */       this.panel.release();
/*  641 */     } catch (InterruptedException interruptedException) {
/*      */       return;
/*      */     } 
/*      */     
/*  645 */     this.panel.createAppletThread();
/*  646 */     this.panel.sendEvent(1);
/*  647 */     this.panel.sendEvent(2);
/*  648 */     this.panel.sendEvent(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletSave() {
/*  655 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public Object run()
/*      */           {
/*  669 */             AppletViewer.this.panel.sendEvent(4);
/*      */             
/*  671 */             FileDialog fileDialog = new FileDialog(AppletViewer.this, AppletViewer.amh.getMessage("appletsave.filedialogtitle"), 1);
/*      */ 
/*      */             
/*  674 */             fileDialog.setDirectory(System.getProperty("user.dir"));
/*  675 */             fileDialog.setFile(AppletViewer.defaultSaveFile);
/*  676 */             fileDialog.show();
/*  677 */             String str1 = fileDialog.getFile();
/*  678 */             if (str1 == null) {
/*      */               
/*  680 */               AppletViewer.this.panel.sendEvent(3);
/*  681 */               return null;
/*      */             } 
/*  683 */             String str2 = fileDialog.getDirectory();
/*  684 */             File file = new File(str2, str1);
/*      */             
/*  686 */             try(FileOutputStream null = new FileOutputStream(file); 
/*  687 */                 BufferedOutputStream null = new BufferedOutputStream(fileOutputStream); 
/*  688 */                 ObjectOutputStream null = new ObjectOutputStream(bufferedOutputStream)) {
/*      */               
/*  690 */               AppletViewer.this.showStatus(AppletViewer.amh.getMessage("appletsave.err1", AppletViewer.this.panel.applet.toString(), file.toString()));
/*  691 */               objectOutputStream.writeObject(AppletViewer.this.panel.applet);
/*  692 */             } catch (IOException iOException) {
/*  693 */               System.err.println(AppletViewer.amh.getMessage("appletsave.err2", iOException));
/*      */             } finally {
/*  695 */               AppletViewer.this.panel.sendEvent(3);
/*      */             } 
/*  697 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletClone() {
/*  706 */     Point point = location();
/*  707 */     updateAtts();
/*  708 */     this.factory.createAppletViewer(point.x + 30, point.y + 30, this.panel.documentURL, (Hashtable)this.panel.atts
/*  709 */         .clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletTag() {
/*  716 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*  717 */     updateAtts();
/*  718 */     printTag(new PrintStream(byteArrayOutputStream), this.panel.atts);
/*  719 */     showStatus(amh.getMessage("applettag"));
/*      */     
/*  721 */     Point point = location();
/*  722 */     new TextFrame(point.x + 30, point.y + 30, amh.getMessage("applettag.textframe"), byteArrayOutputStream.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletInfo() {
/*  729 */     String str = this.panel.applet.getAppletInfo();
/*  730 */     if (str == null) {
/*  731 */       str = amh.getMessage("appletinfo.applet");
/*      */     }
/*  733 */     str = str + "\n\n";
/*      */     
/*  735 */     String[][] arrayOfString = this.panel.applet.getParameterInfo();
/*  736 */     if (arrayOfString != null) {
/*  737 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*  738 */         str = str + arrayOfString[b][0] + " -- " + arrayOfString[b][1] + " -- " + arrayOfString[b][2] + "\n";
/*      */       }
/*      */     } else {
/*  741 */       str = str + amh.getMessage("appletinfo.param");
/*      */     } 
/*      */     
/*  744 */     Point point = location();
/*  745 */     new TextFrame(point.x + 30, point.y + 30, amh.getMessage("appletinfo.textframe"), str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletCharacterEncoding() {
/*  753 */     showStatus(amh.getMessage("appletencoding", encoding));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletEdit() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletPrint() {
/*  766 */     PrinterJob printerJob = PrinterJob.getPrinterJob();
/*      */     
/*  768 */     if (printerJob != null) {
/*  769 */       HashPrintRequestAttributeSet hashPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
/*  770 */       if (printerJob.printDialog(hashPrintRequestAttributeSet)) {
/*  771 */         printerJob.setPrintable(this);
/*      */         try {
/*  773 */           printerJob.print(hashPrintRequestAttributeSet);
/*  774 */           this.statusMsgStream.println(amh.getMessage("appletprint.finish"));
/*  775 */         } catch (PrinterException printerException) {
/*  776 */           this.statusMsgStream.println(amh.getMessage("appletprint.fail"));
/*      */         } 
/*      */       } else {
/*  779 */         this.statusMsgStream.println(amh.getMessage("appletprint.cancel"));
/*      */       } 
/*      */     } else {
/*  782 */       this.statusMsgStream.println(amh.getMessage("appletprint.fail"));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int print(Graphics paramGraphics, PageFormat paramPageFormat, int paramInt) {
/*  788 */     if (paramInt > 0) {
/*  789 */       return 1;
/*      */     }
/*  791 */     Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*  792 */     graphics2D.translate(paramPageFormat.getImageableX(), paramPageFormat.getImageableY());
/*  793 */     this.panel.applet.printAll(paramGraphics);
/*  794 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized void networkProperties() {
/*  803 */     if (props == null) {
/*  804 */       props = new AppletProps();
/*      */     }
/*  806 */     props.addNotify();
/*  807 */     props.setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletStart() {
/*  814 */     this.panel.sendEvent(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void appletStop() {
/*  821 */     this.panel.sendEvent(4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appletShutdown(AppletPanel paramAppletPanel) {
/*  829 */     paramAppletPanel.sendEvent(4);
/*  830 */     paramAppletPanel.sendEvent(5);
/*  831 */     paramAppletPanel.sendEvent(0);
/*  832 */     paramAppletPanel.sendEvent(6);
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
/*      */   void appletClose() {
/*  847 */     final AppletViewerPanel p = this.panel;
/*      */     
/*  849 */     (new Thread(new Runnable()
/*      */         {
/*      */           
/*      */           public void run()
/*      */           {
/*  854 */             AppletViewer.this.appletShutdown(p);
/*  855 */             AppletViewer.appletPanels.removeElement(p);
/*  856 */             AppletViewer.this.dispose();
/*      */             
/*  858 */             if (AppletViewer.countApplets() == 0) {
/*  859 */               AppletViewer.this.appletSystemExit();
/*      */             }
/*      */           }
/*  862 */         })).start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appletSystemExit() {
/*  870 */     if (this.factory.isStandalone()) {
/*  871 */       System.exit(0);
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
/*      */   protected void appletQuit() {
/*  885 */     (new Thread(new Runnable()
/*      */         {
/*      */           
/*      */           public void run()
/*      */           {
/*  890 */             for (Enumeration<AppletPanel> enumeration = AppletViewer.appletPanels.elements(); enumeration.hasMoreElements(); ) {
/*  891 */               AppletPanel appletPanel = enumeration.nextElement();
/*  892 */               AppletViewer.this.appletShutdown(appletPanel);
/*      */             } 
/*  894 */             AppletViewer.this.appletSystemExit();
/*      */           }
/*  896 */         })).start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processUserAction(ActionEvent paramActionEvent) {
/*  904 */     String str = ((MenuItem)paramActionEvent.getSource()).getLabel();
/*      */     
/*  906 */     if (amh.getMessage("menuitem.restart").equals(str)) {
/*  907 */       appletRestart();
/*      */       
/*      */       return;
/*      */     } 
/*  911 */     if (amh.getMessage("menuitem.reload").equals(str)) {
/*  912 */       appletReload();
/*      */       
/*      */       return;
/*      */     } 
/*  916 */     if (amh.getMessage("menuitem.clone").equals(str)) {
/*  917 */       appletClone();
/*      */       
/*      */       return;
/*      */     } 
/*  921 */     if (amh.getMessage("menuitem.stop").equals(str)) {
/*  922 */       appletStop();
/*      */       
/*      */       return;
/*      */     } 
/*  926 */     if (amh.getMessage("menuitem.save").equals(str)) {
/*  927 */       appletSave();
/*      */       
/*      */       return;
/*      */     } 
/*  931 */     if (amh.getMessage("menuitem.start").equals(str)) {
/*  932 */       appletStart();
/*      */       
/*      */       return;
/*      */     } 
/*  936 */     if (amh.getMessage("menuitem.tag").equals(str)) {
/*  937 */       appletTag();
/*      */       
/*      */       return;
/*      */     } 
/*  941 */     if (amh.getMessage("menuitem.info").equals(str)) {
/*  942 */       appletInfo();
/*      */       
/*      */       return;
/*      */     } 
/*  946 */     if (amh.getMessage("menuitem.encoding").equals(str)) {
/*  947 */       appletCharacterEncoding();
/*      */       
/*      */       return;
/*      */     } 
/*  951 */     if (amh.getMessage("menuitem.edit").equals(str)) {
/*  952 */       appletEdit();
/*      */       
/*      */       return;
/*      */     } 
/*  956 */     if (amh.getMessage("menuitem.print").equals(str)) {
/*  957 */       appletPrint();
/*      */       
/*      */       return;
/*      */     } 
/*  961 */     if (amh.getMessage("menuitem.props").equals(str)) {
/*  962 */       networkProperties();
/*      */       
/*      */       return;
/*      */     } 
/*  966 */     if (amh.getMessage("menuitem.close").equals(str)) {
/*  967 */       appletClose();
/*      */       
/*      */       return;
/*      */     } 
/*  971 */     if (this.factory.isStandalone() && amh.getMessage("menuitem.quit").equals(str)) {
/*  972 */       appletQuit();
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int countApplets() {
/*  983 */     return appletPanels.size();
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
/*      */   public static void skipSpace(Reader paramReader) throws IOException {
/*  996 */     while (c >= 0 && (c == 32 || c == 9 || c == 10 || c == 13))
/*      */     {
/*  998 */       c = paramReader.read();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String scanIdentifier(Reader paramReader) throws IOException {
/* 1006 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 1008 */     while ((c >= 97 && c <= 122) || (c >= 65 && c <= 90) || (c >= 48 && c <= 57) || c == 95) {
/*      */ 
/*      */       
/* 1011 */       stringBuffer.append((char)c);
/* 1012 */       c = paramReader.read();
/*      */     } 
/* 1014 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Hashtable scanTag(Reader paramReader) throws IOException {
/* 1023 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1024 */     skipSpace(paramReader);
/* 1025 */     while (c >= 0 && c != 62) {
/* 1026 */       String str1 = scanIdentifier(paramReader);
/* 1027 */       String str2 = "";
/* 1028 */       skipSpace(paramReader);
/* 1029 */       if (c == 61) {
/* 1030 */         int i = -1;
/* 1031 */         c = paramReader.read();
/* 1032 */         skipSpace(paramReader);
/* 1033 */         if (c == 39 || c == 34) {
/* 1034 */           i = c;
/* 1035 */           c = paramReader.read();
/*      */         } 
/* 1037 */         StringBuffer stringBuffer = new StringBuffer();
/* 1038 */         while (c > 0 && ((i < 0 && c != 32 && c != 9 && c != 10 && c != 13 && c != 62) || (i >= 0 && c != i))) {
/*      */ 
/*      */ 
/*      */           
/* 1042 */           stringBuffer.append((char)c);
/* 1043 */           c = paramReader.read();
/*      */         } 
/* 1045 */         if (c == i) {
/* 1046 */           c = paramReader.read();
/*      */         }
/* 1048 */         skipSpace(paramReader);
/* 1049 */         str2 = stringBuffer.toString();
/*      */       } 
/*      */       
/* 1052 */       if (!str2.equals("")) {
/* 1053 */         hashtable.put(str1.toLowerCase(Locale.ENGLISH), str2);
/*      */       }
/*      */       
/* 1056 */       while (c != 62 && c >= 0 && (c < 97 || c > 122) && (c < 65 || c > 90) && (c < 48 || c > 57) && c != 95)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1061 */         c = paramReader.read();
/*      */       }
/*      */     } 
/*      */     
/* 1065 */     return hashtable;
/*      */   }
/*      */ 
/*      */   
/* 1069 */   private static int x = 0;
/* 1070 */   private static int y = 0;
/*      */   
/*      */   private static final int XDELTA = 30;
/*      */   private static final int YDELTA = 30;
/* 1074 */   static String encoding = null;
/*      */   
/*      */   private static Reader makeReader(InputStream paramInputStream) {
/* 1077 */     if (encoding != null) {
/*      */       try {
/* 1079 */         return new BufferedReader(new InputStreamReader(paramInputStream, encoding));
/* 1080 */       } catch (IOException iOException) {}
/*      */     }
/* 1082 */     InputStreamReader inputStreamReader = new InputStreamReader(paramInputStream);
/* 1083 */     encoding = inputStreamReader.getEncoding();
/* 1084 */     return new BufferedReader(inputStreamReader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parse(URL paramURL, String paramString) throws IOException {
/* 1091 */     encoding = paramString;
/* 1092 */     parse(paramURL, System.out, new StdAppletViewerFactory());
/*      */   }
/*      */   
/*      */   public static void parse(URL paramURL) throws IOException {
/* 1096 */     parse(paramURL, System.out, new StdAppletViewerFactory());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parse(URL paramURL, PrintStream paramPrintStream, AppletViewerFactory paramAppletViewerFactory) throws IOException {
/* 1102 */     boolean bool1 = false;
/* 1103 */     boolean bool2 = false;
/* 1104 */     boolean bool3 = false;
/*      */ 
/*      */     
/* 1107 */     String str1 = amh.getMessage("parse.warning.requiresname");
/* 1108 */     String str2 = amh.getMessage("parse.warning.paramoutside");
/* 1109 */     String str3 = amh.getMessage("parse.warning.applet.requirescode");
/* 1110 */     String str4 = amh.getMessage("parse.warning.applet.requiresheight");
/* 1111 */     String str5 = amh.getMessage("parse.warning.applet.requireswidth");
/* 1112 */     String str6 = amh.getMessage("parse.warning.object.requirescode");
/* 1113 */     String str7 = amh.getMessage("parse.warning.object.requiresheight");
/* 1114 */     String str8 = amh.getMessage("parse.warning.object.requireswidth");
/* 1115 */     String str9 = amh.getMessage("parse.warning.embed.requirescode");
/* 1116 */     String str10 = amh.getMessage("parse.warning.embed.requiresheight");
/* 1117 */     String str11 = amh.getMessage("parse.warning.embed.requireswidth");
/* 1118 */     String str12 = amh.getMessage("parse.warning.appnotLongersupported");
/*      */     
/* 1120 */     URLConnection uRLConnection = paramURL.openConnection();
/* 1121 */     Reader reader = makeReader(uRLConnection.getInputStream());
/*      */ 
/*      */ 
/*      */     
/* 1125 */     paramURL = uRLConnection.getURL();
/*      */     
/* 1127 */     byte b = 1;
/* 1128 */     Hashtable<String, String> hashtable = null;
/*      */     
/*      */     while (true) {
/* 1131 */       c = reader.read();
/* 1132 */       if (c == -1) {
/*      */         break;
/*      */       }
/* 1135 */       if (c == 60) {
/* 1136 */         c = reader.read();
/* 1137 */         if (c == 47) {
/* 1138 */           c = reader.read();
/* 1139 */           String str13 = scanIdentifier(reader);
/* 1140 */           if (str13.equalsIgnoreCase("applet") || str13
/* 1141 */             .equalsIgnoreCase("object") || str13
/* 1142 */             .equalsIgnoreCase("embed")) {
/*      */ 
/*      */ 
/*      */             
/* 1146 */             if (bool2 && 
/* 1147 */               hashtable.get("code") == null && hashtable.get("object") == null) {
/* 1148 */               paramPrintStream.println(str6);
/* 1149 */               hashtable = null;
/*      */             } 
/*      */ 
/*      */             
/* 1153 */             if (hashtable != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1158 */               paramAppletViewerFactory.createAppletViewer(x, y, paramURL, hashtable);
/* 1159 */               x += 30;
/* 1160 */               y += 30;
/*      */               
/* 1162 */               Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
/* 1163 */               if (x > dimension.width - 300 || y > dimension.height - 300) {
/* 1164 */                 x = 0;
/* 1165 */                 y = 2 * b * 30;
/* 1166 */                 b++;
/*      */               } 
/*      */             } 
/* 1169 */             hashtable = null;
/* 1170 */             bool1 = false;
/* 1171 */             bool2 = false;
/* 1172 */             bool3 = false;
/*      */           } 
/*      */           continue;
/*      */         } 
/* 1176 */         String str = scanIdentifier(reader);
/* 1177 */         if (str.equalsIgnoreCase("param")) {
/* 1178 */           Hashtable hashtable1 = scanTag(reader);
/* 1179 */           String str13 = (String)hashtable1.get("name");
/* 1180 */           if (str13 == null) {
/* 1181 */             paramPrintStream.println(str1); continue;
/*      */           } 
/* 1183 */           String str14 = (String)hashtable1.get("value");
/* 1184 */           if (str14 == null) {
/* 1185 */             paramPrintStream.println(str1); continue;
/* 1186 */           }  if (hashtable != null) {
/* 1187 */             hashtable.put(str13.toLowerCase(), str14); continue;
/*      */           } 
/* 1189 */           paramPrintStream.println(str2);
/*      */           
/*      */           continue;
/*      */         } 
/* 1193 */         if (str.equalsIgnoreCase("applet")) {
/* 1194 */           bool1 = true;
/* 1195 */           hashtable = scanTag(reader);
/* 1196 */           if (hashtable.get("code") == null && hashtable.get("object") == null) {
/* 1197 */             paramPrintStream.println(str3);
/* 1198 */             hashtable = null; continue;
/* 1199 */           }  if (hashtable.get("width") == null) {
/* 1200 */             paramPrintStream.println(str5);
/* 1201 */             hashtable = null; continue;
/* 1202 */           }  if (hashtable.get("height") == null) {
/* 1203 */             paramPrintStream.println(str4);
/* 1204 */             hashtable = null;
/*      */           }  continue;
/*      */         } 
/* 1207 */         if (str.equalsIgnoreCase("object")) {
/* 1208 */           bool2 = true;
/* 1209 */           hashtable = scanTag(reader);
/*      */ 
/*      */           
/* 1212 */           if (hashtable.get("codebase") != null) {
/* 1213 */             hashtable.remove("codebase");
/*      */           }
/*      */           
/* 1216 */           if (hashtable.get("width") == null) {
/* 1217 */             paramPrintStream.println(str8);
/* 1218 */             hashtable = null; continue;
/* 1219 */           }  if (hashtable.get("height") == null) {
/* 1220 */             paramPrintStream.println(str7);
/* 1221 */             hashtable = null;
/*      */           }  continue;
/*      */         } 
/* 1224 */         if (str.equalsIgnoreCase("embed")) {
/* 1225 */           bool3 = true;
/* 1226 */           hashtable = scanTag(reader);
/*      */           
/* 1228 */           if (hashtable.get("code") == null && hashtable.get("object") == null) {
/* 1229 */             paramPrintStream.println(str9);
/* 1230 */             hashtable = null; continue;
/* 1231 */           }  if (hashtable.get("width") == null) {
/* 1232 */             paramPrintStream.println(str11);
/* 1233 */             hashtable = null; continue;
/* 1234 */           }  if (hashtable.get("height") == null) {
/* 1235 */             paramPrintStream.println(str10);
/* 1236 */             hashtable = null;
/*      */           }  continue;
/*      */         } 
/* 1239 */         if (str.equalsIgnoreCase("app")) {
/* 1240 */           paramPrintStream.println(str12);
/* 1241 */           Hashtable<String, String> hashtable1 = scanTag(reader);
/* 1242 */           str = (String)hashtable1.get("class");
/* 1243 */           if (str != null) {
/* 1244 */             hashtable1.remove("class");
/* 1245 */             hashtable1.put("code", str + ".class");
/*      */           } 
/* 1247 */           str = hashtable1.get("src");
/* 1248 */           if (str != null) {
/* 1249 */             hashtable1.remove("src");
/* 1250 */             hashtable1.put("codebase", str);
/*      */           } 
/* 1252 */           if (hashtable1.get("width") == null) {
/* 1253 */             hashtable1.put("width", "100");
/*      */           }
/* 1255 */           if (hashtable1.get("height") == null) {
/* 1256 */             hashtable1.put("height", "100");
/*      */           }
/* 1258 */           printTag(paramPrintStream, hashtable1);
/* 1259 */           paramPrintStream.println();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1264 */     reader.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void main(String[] paramArrayOfString) {
/* 1275 */     Main.main(paramArrayOfString);
/*      */   }
/*      */   
/* 1278 */   private static AppletMessageHandler amh = new AppletMessageHandler("appletviewer");
/*      */ 
/*      */   
/*      */   private static void checkConnect(URL paramURL) {
/* 1282 */     SecurityManager securityManager = System.getSecurityManager();
/* 1283 */     if (securityManager != null)
/*      */       
/*      */       try {
/* 1286 */         Permission permission = paramURL.openConnection().getPermission();
/* 1287 */         if (permission != null)
/* 1288 */         { securityManager.checkPermission(permission); }
/*      */         else
/* 1290 */         { securityManager.checkConnect(paramURL.getHost(), paramURL.getPort()); } 
/* 1291 */       } catch (IOException iOException) {
/* 1292 */         securityManager.checkConnect(paramURL.getHost(), paramURL.getPort());
/*      */       }  
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */