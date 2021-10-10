/*      */ package com.sun.java.swing.plaf.gtk;
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.GradientPaint;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.FilteredImageSource;
/*      */ import java.awt.image.RGBImageFilter;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JInternalFrame;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.synth.ColorType;
/*      */ import javax.swing.plaf.synth.SynthConstants;
/*      */ import javax.swing.plaf.synth.SynthContext;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.SAXException;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ class Metacity implements SynthConstants {
/*      */   static Metacity INSTANCE;
/*   60 */   private static final String[] themeNames = new String[] {
/*   61 */       getUserTheme(), "blueprint", "Bluecurve", "Crux", "SwingFallbackTheme"
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*   69 */     for (String str : themeNames) {
/*   70 */       if (str != null) {
/*      */         
/*   72 */         try { INSTANCE = new Metacity(str); }
/*   73 */         catch (FileNotFoundException fileNotFoundException) {  }
/*   74 */         catch (IOException iOException)
/*   75 */         { logError(str, iOException); }
/*   76 */         catch (ParserConfigurationException parserConfigurationException)
/*   77 */         { logError(str, parserConfigurationException); }
/*   78 */         catch (SAXException sAXException)
/*   79 */         { logError(str, sAXException); }
/*      */       
/*      */       }
/*   82 */       if (INSTANCE != null) {
/*      */         break;
/*      */       }
/*      */     } 
/*   86 */     if (INSTANCE == null) {
/*   87 */       throw new Error("Could not find any installed metacity theme, and fallback failed");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean errorLogged = false;
/*      */   
/*      */   private static DocumentBuilder documentBuilder;
/*      */   private static Document xmlDoc;
/*      */   private static String userHome;
/*      */   private Node frame_style_set;
/*      */   private Map<String, Object> frameGeometry;
/*      */   private Map<String, Map<String, Object>> frameGeometries;
/*  100 */   private LayoutManager titlePaneLayout = new TitlePaneLayout();
/*      */   
/*  102 */   private ColorizeImageFilter imageFilter = new ColorizeImageFilter();
/*  103 */   private URL themeDir = null;
/*      */   
/*      */   private SynthContext context;
/*      */   private String themeName;
/*  107 */   private ArithmeticExpressionEvaluator aee = new ArithmeticExpressionEvaluator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, Integer> variables;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RoundRectClipShape roundedClipShape;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private HashMap<String, Image> images;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LayoutManager getTitlePaneLayout() {
/*  205 */     return INSTANCE.titlePaneLayout;
/*      */   }
/*      */ 
/*      */   
/*      */   private Shape getRoundedClipShape(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/*  210 */     if (this.roundedClipShape == null) {
/*  211 */       this.roundedClipShape = new RoundRectClipShape();
/*      */     }
/*  213 */     this.roundedClipShape.setRoundedRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*      */     
/*  215 */     return this.roundedClipShape;
/*      */   }
/*      */   void paintButtonBackground(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*      */     JInternalFrame jInternalFrame;
/*  219 */     updateFrameGeometry(paramSynthContext);
/*      */     
/*  221 */     this.context = paramSynthContext;
/*  222 */     JButton jButton = (JButton)paramSynthContext.getComponent();
/*  223 */     String str1 = jButton.getName();
/*  224 */     int i = paramSynthContext.getComponentState();
/*      */     
/*  226 */     JComponent jComponent = (JComponent)jButton.getParent();
/*  227 */     Container container = jComponent.getParent();
/*      */ 
/*      */     
/*  230 */     if (container instanceof JInternalFrame) {
/*  231 */       jInternalFrame = (JInternalFrame)container;
/*  232 */     } else if (container instanceof JInternalFrame.JDesktopIcon) {
/*  233 */       jInternalFrame = ((JInternalFrame.JDesktopIcon)container).getInternalFrame();
/*      */     } else {
/*      */       return;
/*      */     } 
/*      */     
/*  238 */     boolean bool = jInternalFrame.isSelected();
/*  239 */     jButton.setOpaque(false);
/*      */     
/*  241 */     String str2 = "normal";
/*  242 */     if ((i & 0x4) != 0) {
/*  243 */       str2 = "pressed";
/*  244 */     } else if ((i & 0x2) != 0) {
/*  245 */       str2 = "prelight";
/*      */     } 
/*      */     
/*  248 */     String str3 = null;
/*  249 */     String str4 = null;
/*  250 */     boolean bool1 = false;
/*  251 */     boolean bool2 = false;
/*      */ 
/*      */     
/*  254 */     if (str1 == "InternalFrameTitlePane.menuButton") {
/*  255 */       str3 = "menu";
/*  256 */       str4 = "left_left";
/*  257 */       bool1 = true;
/*  258 */     } else if (str1 == "InternalFrameTitlePane.iconifyButton") {
/*  259 */       str3 = "minimize";
/*      */ 
/*      */       
/*  262 */       int j = (jInternalFrame.isIconifiable() ? 1 : 0) + (jInternalFrame.isMaximizable() ? 1 : 0) + (jInternalFrame.isClosable() ? 1 : 0);
/*  263 */       bool2 = (j == 1) ? true : false;
/*  264 */       switch (j) { case 1:
/*  265 */           str4 = "right_right"; break;
/*  266 */         case 2: str4 = "right_middle"; break;
/*  267 */         case 3: str4 = "right_left"; break; }
/*      */     
/*  269 */     } else if (str1 == "InternalFrameTitlePane.maximizeButton") {
/*  270 */       str3 = "maximize";
/*  271 */       bool2 = !jInternalFrame.isClosable() ? true : false;
/*  272 */       str4 = jInternalFrame.isClosable() ? "right_middle" : "right_right";
/*  273 */     } else if (str1 == "InternalFrameTitlePane.closeButton") {
/*  274 */       str3 = "close";
/*  275 */       bool2 = true;
/*  276 */       str4 = "right_right";
/*      */     } 
/*      */     
/*  279 */     Node node = getNode(this.frame_style_set, "frame", new String[] { "focus", bool ? "yes" : "no", "state", 
/*      */           
/*  281 */           jInternalFrame.isMaximum() ? "maximized" : "normal" });
/*      */ 
/*      */     
/*  284 */     if (str3 != null && node != null) {
/*  285 */       Node node1 = getNode("frame_style", new String[] { "name", 
/*  286 */             getStringAttr(node, "style") });
/*      */       
/*  288 */       if (node1 != null) {
/*  289 */         Shape shape = paramGraphics.getClip();
/*  290 */         if ((bool2 && getBoolean("rounded_top_right", false)) || (bool1 && 
/*  291 */           getBoolean("rounded_top_left", false))) {
/*      */           
/*  293 */           Point point = jButton.getLocation();
/*  294 */           if (bool2) {
/*  295 */             paramGraphics.setClip(getRoundedClipShape(0, 0, paramInt3, paramInt4, 12, 12, 2));
/*      */           } else {
/*      */             
/*  298 */             paramGraphics.setClip(getRoundedClipShape(0, 0, paramInt3, paramInt4, 11, 11, 1));
/*      */           } 
/*      */ 
/*      */           
/*  302 */           Rectangle rectangle = shape.getBounds();
/*  303 */           paramGraphics.clipRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */         } 
/*      */         
/*  306 */         drawButton(node1, str4 + "_background", str2, paramGraphics, paramInt3, paramInt4, jInternalFrame);
/*  307 */         drawButton(node1, str3, str2, paramGraphics, paramInt3, paramInt4, jInternalFrame);
/*  308 */         paramGraphics.setClip(shape);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawButton(Node paramNode, String paramString1, String paramString2, Graphics paramGraphics, int paramInt1, int paramInt2, JInternalFrame paramJInternalFrame) {
/*  315 */     Node node = getNode(paramNode, "button", new String[] { "function", paramString1, "state", paramString2 });
/*      */     
/*  317 */     if (node == null && !paramString2.equals("normal")) {
/*  318 */       node = getNode(paramNode, "button", new String[] { "function", paramString1, "state", "normal" });
/*      */     }
/*      */     
/*  321 */     if (node != null) {
/*      */       Node node1;
/*  323 */       String str = getStringAttr(node, "draw_ops");
/*  324 */       if (str != null) {
/*  325 */         node1 = getNode("draw_ops", new String[] { "name", str });
/*      */       } else {
/*  327 */         node1 = getNode(node, "draw_ops", (String[])null);
/*      */       } 
/*  329 */       this.variables.put("width", Integer.valueOf(paramInt1));
/*  330 */       this.variables.put("height", Integer.valueOf(paramInt2));
/*  331 */       draw(node1, paramGraphics, paramJInternalFrame);
/*      */     } 
/*      */   }
/*      */   
/*      */   void paintFrameBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  336 */     updateFrameGeometry(paramSynthContext);
/*      */     
/*  338 */     this.context = paramSynthContext;
/*  339 */     JComponent jComponent1 = paramSynthContext.getComponent();
/*  340 */     JComponent jComponent2 = findChild(jComponent1, "InternalFrame.northPane");
/*      */     
/*  342 */     if (jComponent2 == null) {
/*      */       return;
/*      */     }
/*      */     
/*  346 */     JInternalFrame jInternalFrame = null;
/*  347 */     if (jComponent1 instanceof JInternalFrame) {
/*  348 */       jInternalFrame = (JInternalFrame)jComponent1;
/*  349 */     } else if (jComponent1 instanceof JInternalFrame.JDesktopIcon) {
/*  350 */       jInternalFrame = ((JInternalFrame.JDesktopIcon)jComponent1).getInternalFrame();
/*      */     } else {
/*  352 */       assert false : "component is not JInternalFrame or JInternalFrame.JDesktopIcon";
/*      */       
/*      */       return;
/*      */     } 
/*  356 */     boolean bool = jInternalFrame.isSelected();
/*  357 */     Font font = paramGraphics.getFont();
/*  358 */     paramGraphics.setFont(jComponent2.getFont());
/*  359 */     paramGraphics.translate(paramInt1, paramInt2);
/*      */     
/*  361 */     Rectangle rectangle = calculateTitleArea(jInternalFrame);
/*  362 */     JComponent jComponent3 = findChild(jComponent2, "InternalFrameTitlePane.menuButton");
/*      */     
/*  364 */     Icon icon = jInternalFrame.getFrameIcon();
/*  365 */     this.variables.put("mini_icon_width", 
/*  366 */         Integer.valueOf((icon != null) ? icon.getIconWidth() : 0));
/*  367 */     this.variables.put("mini_icon_height", 
/*  368 */         Integer.valueOf((icon != null) ? icon.getIconHeight() : 0));
/*  369 */     this.variables.put("title_width", Integer.valueOf(calculateTitleTextWidth(paramGraphics, jInternalFrame)));
/*  370 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(jInternalFrame, paramGraphics);
/*  371 */     this.variables.put("title_height", Integer.valueOf(fontMetrics.getAscent() + fontMetrics.getDescent()));
/*      */ 
/*      */     
/*  374 */     this.variables.put("icon_width", Integer.valueOf(32));
/*  375 */     this.variables.put("icon_height", Integer.valueOf(32));
/*      */     
/*  377 */     if (this.frame_style_set != null) {
/*  378 */       Node node = getNode(this.frame_style_set, "frame", new String[] { "focus", bool ? "yes" : "no", "state", 
/*      */             
/*  380 */             jInternalFrame.isMaximum() ? "maximized" : "normal" });
/*      */ 
/*      */       
/*  383 */       if (node != null) {
/*  384 */         Node node1 = getNode("frame_style", new String[] { "name", 
/*  385 */               getStringAttr(node, "style") });
/*      */         
/*  387 */         if (node1 != null) {
/*  388 */           Shape shape = paramGraphics.getClip();
/*  389 */           boolean bool1 = getBoolean("rounded_top_left", false);
/*  390 */           boolean bool2 = getBoolean("rounded_top_right", false);
/*  391 */           boolean bool3 = getBoolean("rounded_bottom_left", false);
/*  392 */           boolean bool4 = getBoolean("rounded_bottom_right", false);
/*      */           
/*  394 */           if (bool1 || bool2 || bool3 || bool4) {
/*  395 */             jInternalFrame.setOpaque(false);
/*      */             
/*  397 */             paramGraphics.setClip(getRoundedClipShape(0, 0, paramInt3, paramInt4, 12, 12, (bool1 ? 1 : 0) | (bool2 ? 2 : 0) | (bool3 ? 4 : 0) | (bool4 ? 8 : 0)));
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  404 */           Rectangle rectangle1 = shape.getBounds();
/*  405 */           paramGraphics.clipRect(rectangle1.x, rectangle1.y, rectangle1.width, rectangle1.height);
/*      */ 
/*      */           
/*  408 */           int i = jComponent2.getHeight();
/*      */           
/*  410 */           boolean bool5 = jInternalFrame.isIcon();
/*  411 */           Insets insets = getBorderInsets(paramSynthContext, null);
/*      */           
/*  413 */           int j = getInt("left_titlebar_edge");
/*  414 */           int k = getInt("right_titlebar_edge");
/*  415 */           int m = getInt("top_titlebar_edge");
/*  416 */           int n = getInt("bottom_titlebar_edge");
/*      */           
/*  418 */           if (!bool5) {
/*  419 */             drawPiece(node1, paramGraphics, "entire_background", 0, 0, paramInt3, paramInt4, jInternalFrame);
/*      */           }
/*      */           
/*  422 */           drawPiece(node1, paramGraphics, "titlebar", 0, 0, paramInt3, i, jInternalFrame);
/*      */           
/*  424 */           drawPiece(node1, paramGraphics, "titlebar_middle", j, m, paramInt3 - j - k, i - m - n, jInternalFrame);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  429 */           drawPiece(node1, paramGraphics, "left_titlebar_edge", 0, 0, j, i, jInternalFrame);
/*      */           
/*  431 */           drawPiece(node1, paramGraphics, "right_titlebar_edge", paramInt3 - k, 0, k, i, jInternalFrame);
/*      */ 
/*      */           
/*  434 */           drawPiece(node1, paramGraphics, "top_titlebar_edge", 0, 0, paramInt3, m, jInternalFrame);
/*      */           
/*  436 */           drawPiece(node1, paramGraphics, "bottom_titlebar_edge", 0, i - n, paramInt3, n, jInternalFrame);
/*      */ 
/*      */           
/*  439 */           drawPiece(node1, paramGraphics, "title", rectangle.x, rectangle.y, rectangle.width, rectangle.height, jInternalFrame);
/*      */           
/*  441 */           if (!bool5) {
/*  442 */             drawPiece(node1, paramGraphics, "left_edge", 0, i, insets.left, paramInt4 - i, jInternalFrame);
/*      */             
/*  444 */             drawPiece(node1, paramGraphics, "right_edge", paramInt3 - insets.right, i, insets.right, paramInt4 - i, jInternalFrame);
/*      */             
/*  446 */             drawPiece(node1, paramGraphics, "bottom_edge", 0, paramInt4 - insets.bottom, paramInt3, insets.bottom, jInternalFrame);
/*      */             
/*  448 */             drawPiece(node1, paramGraphics, "overlay", 0, 0, paramInt3, paramInt4, jInternalFrame);
/*      */           } 
/*      */           
/*  451 */           paramGraphics.setClip(shape);
/*      */         } 
/*      */       } 
/*      */     } 
/*  455 */     paramGraphics.translate(-paramInt1, -paramInt2);
/*  456 */     paramGraphics.setFont(font);
/*      */   }
/*      */   
/*      */   private static class Privileged implements PrivilegedAction<Object> {
/*      */     private Privileged() {}
/*      */     
/*  462 */     private static int GET_THEME_DIR = 0;
/*  463 */     private static int GET_USER_THEME = 1;
/*  464 */     private static int GET_IMAGE = 2;
/*      */     private int type;
/*      */     private Object arg;
/*      */     
/*      */     public Object doPrivileged(int param1Int, Object param1Object) {
/*  469 */       this.type = param1Int;
/*  470 */       this.arg = param1Object;
/*  471 */       return AccessController.doPrivileged(this);
/*      */     }
/*      */     
/*      */     public Object run() {
/*  475 */       if (this.type == GET_THEME_DIR) {
/*  476 */         String str = File.separator;
/*      */ 
/*      */         
/*  479 */         String[] arrayOfString = { Metacity.userHome + str + ".themes", System.getProperty("swing.metacitythemedir"), "/usr/X11R6/share/themes", "/usr/X11R6/share/gnome/themes", "/usr/local/share/themes", "/usr/local/share/gnome/themes", "/usr/share/themes", "/usr/gnome/share/themes", "/opt/gnome2/share/themes" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  489 */         URL uRL = null;
/*  490 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*      */           
/*  492 */           if (arrayOfString[b] != null) {
/*      */ 
/*      */             
/*  495 */             File file = new File(arrayOfString[b] + str + this.arg + str + "metacity-1");
/*      */             
/*  497 */             if ((new File(file, "metacity-theme-1.xml")).canRead()) {
/*      */               try {
/*  499 */                 uRL = file.toURI().toURL();
/*  500 */               } catch (MalformedURLException malformedURLException) {
/*  501 */                 uRL = null;
/*      */               }  break;
/*      */             } 
/*      */           } 
/*      */         } 
/*  506 */         if (uRL == null) {
/*  507 */           String str1 = "resources/metacity/" + this.arg + "/metacity-1/metacity-theme-1.xml";
/*      */           
/*  509 */           URL uRL1 = getClass().getResource(str1);
/*  510 */           if (uRL1 != null) {
/*  511 */             String str2 = uRL1.toString();
/*      */             try {
/*  513 */               uRL = new URL(str2.substring(0, str2.lastIndexOf('/')) + "/");
/*  514 */             } catch (MalformedURLException malformedURLException) {
/*  515 */               uRL = null;
/*      */             } 
/*      */           } 
/*      */         } 
/*  519 */         return uRL;
/*  520 */       }  if (this.type == GET_USER_THEME) {
/*      */         
/*      */         try {
/*  523 */           Metacity.userHome = System.getProperty("user.home");
/*      */           
/*  525 */           String str1 = System.getProperty("swing.metacitythemename");
/*  526 */           if (str1 != null) {
/*  527 */             return str1;
/*      */           }
/*      */ 
/*      */           
/*  531 */           URL uRL = new URL((new File(Metacity.userHome)).toURI().toURL(), ".gconf/apps/metacity/general/%25gconf.xml");
/*      */ 
/*      */           
/*  534 */           InputStreamReader inputStreamReader = new InputStreamReader(uRL.openStream(), "ISO-8859-1");
/*  535 */           char[] arrayOfChar = new char[1024];
/*  536 */           StringBuffer stringBuffer = new StringBuffer();
/*      */           int i;
/*  538 */           while ((i = inputStreamReader.read(arrayOfChar)) >= 0) {
/*  539 */             stringBuffer.append(arrayOfChar, 0, i);
/*      */           }
/*  541 */           inputStreamReader.close();
/*  542 */           String str2 = stringBuffer.toString();
/*  543 */           if (str2 != null) {
/*  544 */             String str = str2.toLowerCase();
/*  545 */             int j = str.indexOf("<entry name=\"theme\"");
/*  546 */             if (j >= 0) {
/*  547 */               j = str.indexOf("<stringvalue>", j);
/*  548 */               if (j > 0) {
/*  549 */                 j += "<stringvalue>".length();
/*  550 */                 int k = str2.indexOf("<", j);
/*  551 */                 return str2.substring(j, k);
/*      */               } 
/*      */             } 
/*      */           } 
/*  555 */         } catch (MalformedURLException malformedURLException) {
/*      */         
/*  557 */         } catch (IOException iOException) {}
/*      */ 
/*      */         
/*  560 */         return null;
/*  561 */       }  if (this.type == GET_IMAGE) {
/*  562 */         return (new ImageIcon((URL)this.arg)).getImage();
/*      */       }
/*  564 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static URL getThemeDir(String paramString) {
/*  570 */     return (URL)(new Privileged()).doPrivileged(Privileged.GET_THEME_DIR, paramString);
/*      */   }
/*      */   
/*      */   private static String getUserTheme() {
/*  574 */     return (String)(new Privileged()).doPrivileged(Privileged.GET_USER_THEME, null);
/*      */   }
/*      */   
/*      */   protected void tileImage(Graphics paramGraphics, Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat) {
/*  578 */     Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*  579 */     Composite composite = graphics2D.getComposite();
/*      */     
/*  581 */     int i = paramImage.getWidth(null);
/*  582 */     int j = paramImage.getHeight(null);
/*  583 */     int k = paramInt2;
/*  584 */     while (k < paramInt2 + paramInt4) {
/*  585 */       j = Math.min(j, paramInt2 + paramInt4 - k);
/*  586 */       int m = paramInt1;
/*  587 */       while (m < paramInt1 + paramInt3) {
/*  588 */         float f1 = (paramArrayOffloat.length - 1.0F) * m / (paramInt1 + paramInt3);
/*  589 */         int n = (int)f1;
/*  590 */         f1 -= (int)f1;
/*  591 */         float f2 = (1.0F - f1) * paramArrayOffloat[n];
/*  592 */         if (n + 1 < paramArrayOffloat.length) {
/*  593 */           f2 += f1 * paramArrayOffloat[n + 1];
/*      */         }
/*  595 */         graphics2D.setComposite(AlphaComposite.getInstance(3, f2));
/*  596 */         int i1 = Math.min(i, paramInt1 + paramInt3 - m);
/*  597 */         paramGraphics.drawImage(paramImage, m, k, m + i1, k + j, 0, 0, i1, j, null);
/*  598 */         m += i1;
/*      */       } 
/*  600 */       k += j;
/*      */     } 
/*  602 */     graphics2D.setComposite(composite);
/*      */   }
/*      */   
/*  605 */   protected Metacity(String paramString) throws IOException, ParserConfigurationException, SAXException { this.images = new HashMap<>(); this.themeName = paramString; this.themeDir = getThemeDir(paramString); if (this.themeDir != null) { URL uRL = new URL(this.themeDir, "metacity-theme-1.xml"); xmlDoc = getXMLDoc(uRL); if (xmlDoc == null) throw new IOException(uRL.toString());  } else { throw new FileNotFoundException(paramString); }  this.variables = new HashMap<>(); NodeList nodeList = xmlDoc.getElementsByTagName("constant"); int i = nodeList.getLength(); byte b; for (b = 0; b < i; b++) { Node node = nodeList.item(b); String str = getStringAttr(node, "name"); if (str != null) { String str1 = getStringAttr(node, "value"); if (str1 != null)
/*      */           try { this.variables.put(str, Integer.valueOf(Integer.parseInt(str1))); } catch (NumberFormatException numberFormatException) { logError(paramString, numberFormatException); }   }  }  this.frameGeometries = new HashMap<>(); nodeList = xmlDoc.getElementsByTagName("frame_geometry"); i = nodeList.getLength(); for (b = 0; b < i; b++) { Node node = nodeList.item(b); String str = getStringAttr(node, "name"); if (str != null) { HashMap<Object, Object> hashMap = new HashMap<>(); this.frameGeometries.put(str, hashMap); String str1 = getStringAttr(node, "parent"); if (str1 != null)
/*      */           hashMap.putAll(this.frameGeometries.get(str1));  hashMap.put("has_title", Boolean.valueOf(getBooleanAttr(node, "has_title", true))); hashMap.put("rounded_top_left", Boolean.valueOf(getBooleanAttr(node, "rounded_top_left", false))); hashMap.put("rounded_top_right", Boolean.valueOf(getBooleanAttr(node, "rounded_top_right", false))); hashMap.put("rounded_bottom_left", Boolean.valueOf(getBooleanAttr(node, "rounded_bottom_left", false))); hashMap.put("rounded_bottom_right", Boolean.valueOf(getBooleanAttr(node, "rounded_bottom_right", false))); NodeList nodeList1 = node.getChildNodes(); int j = nodeList1.getLength(); for (byte b1 = 0; b1 < j; b1++) { Node node1 = nodeList1.item(b1); if (node1.getNodeType() == 1) { Float float_; str = node1.getNodeName(); Integer integer = null; if ("distance".equals(str)) { integer = Integer.valueOf(getIntAttr(node1, "value", 0)); } else if ("border".equals(str)) { Insets insets = new Insets(getIntAttr(node1, "top", 0), getIntAttr(node1, "left", 0), getIntAttr(node1, "bottom", 0), getIntAttr(node1, "right", 0)); } else if ("aspect_ratio".equals(str)) { float_ = new Float(getFloatAttr(node1, "value", 1.0F)); } else { logError(paramString, "Unknown Metacity frame geometry value type: " + str); }  String str2 = getStringAttr(node1, "name"); if (str2 != null && float_ != null)
/*  608 */               hashMap.put(str2, float_);  }  }  }  }  this.frameGeometry = this.frameGeometries.get("normal"); } protected Image getImage(String paramString, Color paramColor) { Image image = this.images.get(paramString + "-" + paramColor.getRGB());
/*  609 */     if (image == null) {
/*  610 */       image = this.imageFilter.colorize(getImage(paramString), paramColor);
/*  611 */       if (image != null) {
/*  612 */         this.images.put(paramString + "-" + paramColor.getRGB(), image);
/*      */       }
/*      */     } 
/*  615 */     return image; }
/*      */ 
/*      */   
/*      */   protected Image getImage(String paramString) {
/*  619 */     Image image = this.images.get(paramString);
/*  620 */     if (image == null) {
/*  621 */       if (this.themeDir != null) {
/*      */         try {
/*  623 */           URL uRL = new URL(this.themeDir, paramString);
/*  624 */           image = (Image)(new Privileged()).doPrivileged(Privileged.GET_IMAGE, uRL);
/*  625 */         } catch (MalformedURLException malformedURLException) {}
/*      */       }
/*      */ 
/*      */       
/*  629 */       if (image != null) {
/*  630 */         this.images.put(paramString, image);
/*      */       }
/*      */     } 
/*  633 */     return image;
/*      */   }
/*      */   
/*      */   private class ColorizeImageFilter
/*      */     extends RGBImageFilter
/*      */   {
/*      */     double cr;
/*      */     double cg;
/*      */     double cb;
/*      */     
/*      */     public void setColor(Color param1Color) {
/*  644 */       this.cr = param1Color.getRed() / 255.0D;
/*  645 */       this.cg = param1Color.getGreen() / 255.0D;
/*  646 */       this.cb = param1Color.getBlue() / 255.0D;
/*      */     }
/*      */     
/*      */     public Image colorize(Image param1Image, Color param1Color) {
/*  650 */       setColor(param1Color);
/*  651 */       FilteredImageSource filteredImageSource = new FilteredImageSource(param1Image.getSource(), this);
/*  652 */       return (new ImageIcon(Metacity.this.context.getComponent().createImage(filteredImageSource))).getImage();
/*      */     }
/*      */ 
/*      */     
/*      */     public int filterRGB(int param1Int1, int param1Int2, int param1Int3) {
/*  657 */       double d2, d3, d4, d1 = (2 * (param1Int3 & 0xFF)) / 255.0D;
/*      */ 
/*      */       
/*  660 */       if (d1 <= 1.0D) {
/*  661 */         d2 = this.cr * d1;
/*  662 */         d3 = this.cg * d1;
/*  663 */         d4 = this.cb * d1;
/*      */       } else {
/*  665 */         d1--;
/*  666 */         d2 = this.cr + (1.0D - this.cr) * d1;
/*  667 */         d3 = this.cg + (1.0D - this.cg) * d1;
/*  668 */         d4 = this.cb + (1.0D - this.cb) * d1;
/*      */       } 
/*      */       
/*  671 */       return (param1Int3 & 0xFF000000) + ((int)(d2 * 255.0D) << 16) + ((int)(d3 * 255.0D) << 8) + (int)(d4 * 255.0D);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static JComponent findChild(JComponent paramJComponent, String paramString) {
/*  679 */     int i = paramJComponent.getComponentCount();
/*  680 */     for (byte b = 0; b < i; b++) {
/*  681 */       JComponent jComponent = (JComponent)paramJComponent.getComponent(b);
/*  682 */       if (paramString.equals(jComponent.getName())) {
/*  683 */         return jComponent;
/*      */       }
/*      */     } 
/*  686 */     return null;
/*      */   }
/*      */   
/*      */   protected class TitlePaneLayout implements LayoutManager { public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/*  694 */       return minimumLayoutSize(param1Container);
/*      */     }
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/*      */       JInternalFrame jInternalFrame;
/*  698 */       JComponent jComponent = (JComponent)param1Container;
/*  699 */       Container container = jComponent.getParent();
/*      */       
/*  701 */       if (container instanceof JInternalFrame) {
/*  702 */         jInternalFrame = (JInternalFrame)container;
/*  703 */       } else if (container instanceof JInternalFrame.JDesktopIcon) {
/*  704 */         jInternalFrame = ((JInternalFrame.JDesktopIcon)container).getInternalFrame();
/*      */       } else {
/*  706 */         return null;
/*      */       } 
/*      */       
/*  709 */       Dimension dimension = Metacity.this.calculateButtonSize(jComponent);
/*  710 */       Insets insets1 = (Insets)Metacity.this.getFrameGeometry().get("title_border");
/*  711 */       Insets insets2 = (Insets)Metacity.this.getFrameGeometry().get("button_border");
/*      */ 
/*      */       
/*  714 */       int i = Metacity.this.getInt("left_titlebar_edge") + dimension.width + Metacity.this.getInt("right_titlebar_edge");
/*  715 */       if (insets1 != null) {
/*  716 */         i += insets1.left + insets1.right;
/*      */       }
/*  718 */       if (jInternalFrame.isClosable()) {
/*  719 */         i += dimension.width;
/*      */       }
/*  721 */       if (jInternalFrame.isMaximizable()) {
/*  722 */         i += dimension.width;
/*      */       }
/*  724 */       if (jInternalFrame.isIconifiable()) {
/*  725 */         i += dimension.width;
/*      */       }
/*  727 */       FontMetrics fontMetrics = jInternalFrame.getFontMetrics(jComponent.getFont());
/*  728 */       String str = jInternalFrame.getTitle();
/*  729 */       byte b1 = (str != null) ? SwingUtilities2.stringWidth(jInternalFrame, fontMetrics, str) : 0;
/*      */       
/*  731 */       byte b2 = (str != null) ? str.length() : 0;
/*      */ 
/*      */       
/*  734 */       if (b2 > 3) {
/*  735 */         int n = SwingUtilities2.stringWidth(jInternalFrame, fontMetrics, str
/*  736 */             .substring(0, 3) + "...");
/*  737 */         i += (b1 < n) ? b1 : n;
/*      */       } else {
/*  739 */         i += b1;
/*      */       } 
/*      */ 
/*      */       
/*  743 */       int j = fontMetrics.getHeight() + Metacity.this.getInt("title_vertical_pad");
/*  744 */       if (insets1 != null) {
/*  745 */         j += insets1.top + insets1.bottom;
/*      */       }
/*  747 */       int k = dimension.height;
/*  748 */       if (insets2 != null) {
/*  749 */         k += insets2.top + insets2.bottom;
/*      */       }
/*  751 */       int m = Math.max(k, j);
/*      */       
/*  753 */       return new Dimension(i, m);
/*      */     }
/*      */     public void layoutContainer(Container param1Container) {
/*      */       JInternalFrame jInternalFrame;
/*  757 */       JComponent jComponent1 = (JComponent)param1Container;
/*  758 */       Container container = jComponent1.getParent();
/*      */       
/*  760 */       if (container instanceof JInternalFrame) {
/*  761 */         jInternalFrame = (JInternalFrame)container;
/*  762 */       } else if (container instanceof JInternalFrame.JDesktopIcon) {
/*  763 */         jInternalFrame = ((JInternalFrame.JDesktopIcon)container).getInternalFrame();
/*      */       } else {
/*      */         return;
/*      */       } 
/*  767 */       Map map = Metacity.this.getFrameGeometry();
/*      */       
/*  769 */       int i = jComponent1.getWidth();
/*  770 */       int j = jComponent1.getHeight();
/*      */       
/*  772 */       JComponent jComponent2 = Metacity.findChild(jComponent1, "InternalFrameTitlePane.menuButton");
/*  773 */       JComponent jComponent3 = Metacity.findChild(jComponent1, "InternalFrameTitlePane.iconifyButton");
/*  774 */       JComponent jComponent4 = Metacity.findChild(jComponent1, "InternalFrameTitlePane.maximizeButton");
/*  775 */       JComponent jComponent5 = Metacity.findChild(jComponent1, "InternalFrameTitlePane.closeButton");
/*      */       
/*  777 */       Insets insets = (Insets)map.get("button_border");
/*  778 */       Dimension dimension = Metacity.this.calculateButtonSize(jComponent1);
/*      */       
/*  780 */       boolean bool = (insets != null) ? insets.top : false;
/*  781 */       if (container.getComponentOrientation().isLeftToRight()) {
/*  782 */         int k = Metacity.this.getInt("left_titlebar_edge");
/*      */         
/*  784 */         jComponent2.setBounds(k, bool, dimension.width, dimension.height);
/*      */         
/*  786 */         k = i - dimension.width - Metacity.this.getInt("right_titlebar_edge");
/*  787 */         if (insets != null) {
/*  788 */           k -= insets.right;
/*      */         }
/*      */         
/*  791 */         if (jInternalFrame.isClosable()) {
/*  792 */           jComponent5.setBounds(k, bool, dimension.width, dimension.height);
/*  793 */           k -= dimension.width;
/*      */         } 
/*      */         
/*  796 */         if (jInternalFrame.isMaximizable()) {
/*  797 */           jComponent4.setBounds(k, bool, dimension.width, dimension.height);
/*  798 */           k -= dimension.width;
/*      */         } 
/*      */         
/*  801 */         if (jInternalFrame.isIconifiable()) {
/*  802 */           jComponent3.setBounds(k, bool, dimension.width, dimension.height);
/*      */         }
/*      */       } else {
/*  805 */         int k = i - dimension.width - Metacity.this.getInt("right_titlebar_edge");
/*      */         
/*  807 */         jComponent2.setBounds(k, bool, dimension.width, dimension.height);
/*      */         
/*  809 */         k = Metacity.this.getInt("left_titlebar_edge");
/*  810 */         if (insets != null) {
/*  811 */           k += insets.left;
/*      */         }
/*      */         
/*  814 */         if (jInternalFrame.isClosable()) {
/*  815 */           jComponent5.setBounds(k, bool, dimension.width, dimension.height);
/*  816 */           k += dimension.width;
/*      */         } 
/*      */         
/*  819 */         if (jInternalFrame.isMaximizable()) {
/*  820 */           jComponent4.setBounds(k, bool, dimension.width, dimension.height);
/*  821 */           k += dimension.width;
/*      */         } 
/*      */         
/*  824 */         if (jInternalFrame.isIconifiable()) {
/*  825 */           jComponent3.setBounds(k, bool, dimension.width, dimension.height);
/*      */         }
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   protected Map getFrameGeometry() {
/*  832 */     return this.frameGeometry;
/*      */   }
/*      */   
/*      */   protected void setFrameGeometry(JComponent paramJComponent, Map<String, Object> paramMap) {
/*  836 */     this.frameGeometry = paramMap;
/*  837 */     if (getInt("top_height") == 0 && paramJComponent != null) {
/*  838 */       paramMap.put("top_height", Integer.valueOf(paramJComponent.getHeight()));
/*      */     }
/*      */   }
/*      */   
/*      */   protected int getInt(String paramString) {
/*  843 */     Integer integer = (Integer)this.frameGeometry.get(paramString);
/*  844 */     if (integer == null) {
/*  845 */       integer = this.variables.get(paramString);
/*      */     }
/*  847 */     return (integer != null) ? integer.intValue() : 0;
/*      */   }
/*      */   
/*      */   protected boolean getBoolean(String paramString, boolean paramBoolean) {
/*  851 */     Boolean bool = (Boolean)this.frameGeometry.get(paramString);
/*  852 */     return (bool != null) ? bool.booleanValue() : paramBoolean;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawArc(Node paramNode, Graphics paramGraphics) {
/*  857 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/*  858 */     Color color = parseColor(getStringAttr(namedNodeMap, "color"));
/*  859 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/*  860 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/*  861 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "width"));
/*  862 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "height"));
/*  863 */     int n = this.aee.evaluate(getStringAttr(namedNodeMap, "start_angle"));
/*  864 */     int i1 = this.aee.evaluate(getStringAttr(namedNodeMap, "extent_angle"));
/*  865 */     boolean bool = getBooleanAttr(paramNode, "filled", false);
/*  866 */     if (getInt("width") == -1) {
/*  867 */       i -= k;
/*      */     }
/*  869 */     if (getInt("height") == -1) {
/*  870 */       j -= m;
/*      */     }
/*  872 */     paramGraphics.setColor(color);
/*  873 */     if (bool) {
/*  874 */       paramGraphics.fillArc(i, j, k, m, n, i1);
/*      */     } else {
/*  876 */       paramGraphics.drawArc(i, j, k, m, n, i1);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void drawLine(Node paramNode, Graphics paramGraphics) {
/*  881 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/*  882 */     Color color = parseColor(getStringAttr(namedNodeMap, "color"));
/*  883 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x1"));
/*  884 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y1"));
/*  885 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "x2"));
/*  886 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "y2"));
/*  887 */     int n = this.aee.evaluate(getStringAttr(namedNodeMap, "width"), 1);
/*  888 */     paramGraphics.setColor(color);
/*  889 */     if (n != 1) {
/*  890 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*  891 */       Stroke stroke = graphics2D.getStroke();
/*  892 */       graphics2D.setStroke(new BasicStroke(n));
/*  893 */       graphics2D.drawLine(i, j, k, m);
/*  894 */       graphics2D.setStroke(stroke);
/*      */     } else {
/*  896 */       paramGraphics.drawLine(i, j, k, m);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void drawRectangle(Node paramNode, Graphics paramGraphics) {
/*  901 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/*  902 */     Color color = parseColor(getStringAttr(namedNodeMap, "color"));
/*  903 */     boolean bool = getBooleanAttr(paramNode, "filled", false);
/*  904 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/*  905 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/*  906 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "width"));
/*  907 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "height"));
/*  908 */     paramGraphics.setColor(color);
/*  909 */     if (getInt("width") == -1) {
/*  910 */       i -= k;
/*      */     }
/*  912 */     if (getInt("height") == -1) {
/*  913 */       j -= m;
/*      */     }
/*  915 */     if (bool) {
/*  916 */       paramGraphics.fillRect(i, j, k, m);
/*      */     } else {
/*  918 */       paramGraphics.drawRect(i, j, k, m);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void drawTile(Node paramNode, Graphics paramGraphics, JInternalFrame paramJInternalFrame) {
/*  923 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/*  924 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/*  925 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/*  926 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "width"));
/*  927 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "height"));
/*  928 */     int n = this.aee.evaluate(getStringAttr(namedNodeMap, "tile_width"));
/*  929 */     int i1 = this.aee.evaluate(getStringAttr(namedNodeMap, "tile_height"));
/*  930 */     int i2 = getInt("width");
/*  931 */     int i3 = getInt("height");
/*  932 */     if (i2 == -1) {
/*  933 */       i -= k;
/*      */     }
/*  935 */     if (i3 == -1) {
/*  936 */       j -= m;
/*      */     }
/*  938 */     Shape shape = paramGraphics.getClip();
/*  939 */     if (paramGraphics instanceof Graphics2D) {
/*  940 */       ((Graphics2D)paramGraphics).clip(new Rectangle(i, j, k, m));
/*      */     }
/*  942 */     this.variables.put("width", Integer.valueOf(n));
/*  943 */     this.variables.put("height", Integer.valueOf(i1));
/*      */     
/*  945 */     Node node = getNode("draw_ops", new String[] { "name", getStringAttr(paramNode, "name") });
/*      */     
/*  947 */     int i4 = j;
/*  948 */     while (i4 < j + m) {
/*  949 */       int i5 = i;
/*  950 */       while (i5 < i + k) {
/*  951 */         paramGraphics.translate(i5, i4);
/*  952 */         draw(node, paramGraphics, paramJInternalFrame);
/*  953 */         paramGraphics.translate(-i5, -i4);
/*  954 */         i5 += n;
/*      */       } 
/*  956 */       i4 += i1;
/*      */     } 
/*      */     
/*  959 */     this.variables.put("width", Integer.valueOf(i2));
/*  960 */     this.variables.put("height", Integer.valueOf(i3));
/*  961 */     paramGraphics.setClip(shape);
/*      */   }
/*      */   
/*      */   protected void drawTint(Node paramNode, Graphics paramGraphics) {
/*  965 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/*  966 */     Color color = parseColor(getStringAttr(namedNodeMap, "color"));
/*  967 */     float f = Float.parseFloat(getStringAttr(namedNodeMap, "alpha"));
/*  968 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/*  969 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/*  970 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "width"));
/*  971 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "height"));
/*  972 */     if (getInt("width") == -1) {
/*  973 */       i -= k;
/*      */     }
/*  975 */     if (getInt("height") == -1) {
/*  976 */       j -= m;
/*      */     }
/*  978 */     if (paramGraphics instanceof Graphics2D) {
/*  979 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*  980 */       Composite composite = graphics2D.getComposite();
/*  981 */       AlphaComposite alphaComposite = AlphaComposite.getInstance(3, f);
/*  982 */       graphics2D.setComposite(alphaComposite);
/*  983 */       graphics2D.setColor(color);
/*  984 */       graphics2D.fillRect(i, j, k, m);
/*  985 */       graphics2D.setComposite(composite);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void drawTitle(Node paramNode, Graphics paramGraphics, JInternalFrame paramJInternalFrame) {
/*  990 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/*  991 */     String str1 = getStringAttr(namedNodeMap, "color");
/*  992 */     int i = str1.indexOf("gtk:fg[");
/*  993 */     if (i > 0) {
/*  994 */       str1 = str1.substring(0, i) + "gtk:text[" + str1.substring(i + 7);
/*      */     }
/*  996 */     Color color = parseColor(str1);
/*  997 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/*  998 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/*      */     
/* 1000 */     String str2 = paramJInternalFrame.getTitle();
/* 1001 */     if (str2 != null) {
/* 1002 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(paramJInternalFrame, paramGraphics);
/* 1003 */       str2 = SwingUtilities2.clipStringIfNecessary(paramJInternalFrame, fontMetrics, str2, 
/* 1004 */           (calculateTitleArea(paramJInternalFrame)).width);
/* 1005 */       paramGraphics.setColor(color);
/* 1006 */       SwingUtilities2.drawString(paramJInternalFrame, paramGraphics, str2, j, k + fontMetrics.getAscent());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Dimension calculateButtonSize(JComponent paramJComponent) {
/* 1011 */     int i = getInt("button_height");
/* 1012 */     if (i == 0) {
/* 1013 */       i = paramJComponent.getHeight();
/* 1014 */       if (i == 0) {
/* 1015 */         i = 13;
/*      */       } else {
/* 1017 */         Insets insets = (Insets)this.frameGeometry.get("button_border");
/* 1018 */         if (insets != null) {
/* 1019 */           i -= insets.top + insets.bottom;
/*      */         }
/*      */       } 
/*      */     } 
/* 1023 */     int j = getInt("button_width");
/* 1024 */     if (j == 0) {
/* 1025 */       j = i;
/* 1026 */       Float float_ = (Float)this.frameGeometry.get("aspect_ratio");
/* 1027 */       if (float_ != null) {
/* 1028 */         j = (int)(i / float_.floatValue());
/*      */       }
/*      */     } 
/* 1031 */     return new Dimension(j, i);
/*      */   }
/*      */   
/*      */   protected Rectangle calculateTitleArea(JInternalFrame paramJInternalFrame) {
/* 1035 */     JComponent jComponent = findChild(paramJInternalFrame, "InternalFrame.northPane");
/* 1036 */     Dimension dimension = calculateButtonSize(jComponent);
/* 1037 */     Insets insets1 = (Insets)this.frameGeometry.get("title_border");
/* 1038 */     Insets insets2 = (Insets)getFrameGeometry().get("button_border");
/*      */     
/* 1040 */     Rectangle rectangle = new Rectangle();
/* 1041 */     rectangle.x = getInt("left_titlebar_edge");
/* 1042 */     rectangle.y = 0;
/* 1043 */     rectangle.height = jComponent.getHeight();
/* 1044 */     if (insets1 != null) {
/* 1045 */       rectangle.x += insets1.left;
/* 1046 */       rectangle.y += insets1.top;
/* 1047 */       rectangle.height -= insets1.top + insets1.bottom;
/*      */     } 
/*      */     
/* 1050 */     if (jComponent.getParent().getComponentOrientation().isLeftToRight()) {
/* 1051 */       rectangle.x += dimension.width;
/* 1052 */       if (insets2 != null) {
/* 1053 */         rectangle.x += insets2.left;
/*      */       }
/* 1055 */       rectangle.width = jComponent.getWidth() - rectangle.x - getInt("right_titlebar_edge");
/* 1056 */       if (paramJInternalFrame.isClosable()) {
/* 1057 */         rectangle.width -= dimension.width;
/*      */       }
/* 1059 */       if (paramJInternalFrame.isMaximizable()) {
/* 1060 */         rectangle.width -= dimension.width;
/*      */       }
/* 1062 */       if (paramJInternalFrame.isIconifiable()) {
/* 1063 */         rectangle.width -= dimension.width;
/*      */       }
/*      */     } else {
/* 1066 */       if (paramJInternalFrame.isClosable()) {
/* 1067 */         rectangle.x += dimension.width;
/*      */       }
/* 1069 */       if (paramJInternalFrame.isMaximizable()) {
/* 1070 */         rectangle.x += dimension.width;
/*      */       }
/* 1072 */       if (paramJInternalFrame.isIconifiable()) {
/* 1073 */         rectangle.x += dimension.width;
/*      */       }
/* 1075 */       rectangle.width = jComponent.getWidth() - rectangle.x - getInt("right_titlebar_edge") - dimension.width;
/*      */       
/* 1077 */       if (insets2 != null) {
/* 1078 */         rectangle.x -= insets2.right;
/*      */       }
/*      */     } 
/* 1081 */     if (insets1 != null) {
/* 1082 */       rectangle.width -= insets1.right;
/*      */     }
/* 1084 */     return rectangle;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int calculateTitleTextWidth(Graphics paramGraphics, JInternalFrame paramJInternalFrame) {
/* 1089 */     String str = paramJInternalFrame.getTitle();
/* 1090 */     if (str != null) {
/* 1091 */       Rectangle rectangle = calculateTitleArea(paramJInternalFrame);
/* 1092 */       return Math.min(SwingUtilities2.stringWidth(paramJInternalFrame, 
/* 1093 */             SwingUtilities2.getFontMetrics(paramJInternalFrame, paramGraphics), str), rectangle.width);
/*      */     } 
/* 1095 */     return 0;
/*      */   }
/*      */   
/*      */   protected void setClip(Node paramNode, Graphics paramGraphics) {
/* 1099 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 1100 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/* 1101 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/* 1102 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "width"));
/* 1103 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "height"));
/* 1104 */     if (getInt("width") == -1) {
/* 1105 */       i -= k;
/*      */     }
/* 1107 */     if (getInt("height") == -1) {
/* 1108 */       j -= m;
/*      */     }
/* 1110 */     if (paramGraphics instanceof Graphics2D) {
/* 1111 */       ((Graphics2D)paramGraphics).clip(new Rectangle(i, j, k, m));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void drawGTKArrow(Node paramNode, Graphics paramGraphics) {
/* 1116 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 1117 */     String str1 = getStringAttr(namedNodeMap, "arrow");
/* 1118 */     String str2 = getStringAttr(namedNodeMap, "shadow");
/* 1119 */     String str3 = getStringAttr(namedNodeMap, "state").toUpperCase();
/* 1120 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/* 1121 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/* 1122 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "width"));
/* 1123 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "height"));
/*      */     
/* 1125 */     short s = -1;
/* 1126 */     if ("NORMAL".equals(str3)) {
/* 1127 */       s = 1;
/* 1128 */     } else if ("SELECTED".equals(str3)) {
/* 1129 */       s = 512;
/* 1130 */     } else if ("INSENSITIVE".equals(str3)) {
/* 1131 */       s = 8;
/* 1132 */     } else if ("PRELIGHT".equals(str3)) {
/* 1133 */       s = 2;
/*      */     } 
/*      */     
/* 1136 */     GTKConstants.ShadowType shadowType = null;
/* 1137 */     if ("in".equals(str2)) {
/* 1138 */       shadowType = GTKConstants.ShadowType.IN;
/* 1139 */     } else if ("out".equals(str2)) {
/* 1140 */       shadowType = GTKConstants.ShadowType.OUT;
/* 1141 */     } else if ("etched_in".equals(str2)) {
/* 1142 */       shadowType = GTKConstants.ShadowType.ETCHED_IN;
/* 1143 */     } else if ("etched_out".equals(str2)) {
/* 1144 */       shadowType = GTKConstants.ShadowType.ETCHED_OUT;
/* 1145 */     } else if ("none".equals(str2)) {
/* 1146 */       shadowType = GTKConstants.ShadowType.NONE;
/*      */     } 
/*      */     
/* 1149 */     GTKConstants.ArrowType arrowType = null;
/* 1150 */     if ("up".equals(str1)) {
/* 1151 */       arrowType = GTKConstants.ArrowType.UP;
/* 1152 */     } else if ("down".equals(str1)) {
/* 1153 */       arrowType = GTKConstants.ArrowType.DOWN;
/* 1154 */     } else if ("left".equals(str1)) {
/* 1155 */       arrowType = GTKConstants.ArrowType.LEFT;
/* 1156 */     } else if ("right".equals(str1)) {
/* 1157 */       arrowType = GTKConstants.ArrowType.RIGHT;
/*      */     } 
/*      */     
/* 1160 */     GTKPainter.INSTANCE.paintMetacityElement(this.context, paramGraphics, s, "metacity-arrow", i, j, k, m, shadowType, arrowType);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawGTKBox(Node paramNode, Graphics paramGraphics) {
/* 1165 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 1166 */     String str1 = getStringAttr(namedNodeMap, "shadow");
/* 1167 */     String str2 = getStringAttr(namedNodeMap, "state").toUpperCase();
/* 1168 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/* 1169 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/* 1170 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "width"));
/* 1171 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "height"));
/*      */     
/* 1173 */     short s = -1;
/* 1174 */     if ("NORMAL".equals(str2)) {
/* 1175 */       s = 1;
/* 1176 */     } else if ("SELECTED".equals(str2)) {
/* 1177 */       s = 512;
/* 1178 */     } else if ("INSENSITIVE".equals(str2)) {
/* 1179 */       s = 8;
/* 1180 */     } else if ("PRELIGHT".equals(str2)) {
/* 1181 */       s = 2;
/*      */     } 
/*      */     
/* 1184 */     GTKConstants.ShadowType shadowType = null;
/* 1185 */     if ("in".equals(str1)) {
/* 1186 */       shadowType = GTKConstants.ShadowType.IN;
/* 1187 */     } else if ("out".equals(str1)) {
/* 1188 */       shadowType = GTKConstants.ShadowType.OUT;
/* 1189 */     } else if ("etched_in".equals(str1)) {
/* 1190 */       shadowType = GTKConstants.ShadowType.ETCHED_IN;
/* 1191 */     } else if ("etched_out".equals(str1)) {
/* 1192 */       shadowType = GTKConstants.ShadowType.ETCHED_OUT;
/* 1193 */     } else if ("none".equals(str1)) {
/* 1194 */       shadowType = GTKConstants.ShadowType.NONE;
/*      */     } 
/* 1196 */     GTKPainter.INSTANCE.paintMetacityElement(this.context, paramGraphics, s, "metacity-box", i, j, k, m, shadowType, null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawGTKVLine(Node paramNode, Graphics paramGraphics) {
/* 1201 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 1202 */     String str = getStringAttr(namedNodeMap, "state").toUpperCase();
/*      */     
/* 1204 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/* 1205 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y1"));
/* 1206 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "y2"));
/*      */     
/* 1208 */     short s = -1;
/* 1209 */     if ("NORMAL".equals(str)) {
/* 1210 */       s = 1;
/* 1211 */     } else if ("SELECTED".equals(str)) {
/* 1212 */       s = 512;
/* 1213 */     } else if ("INSENSITIVE".equals(str)) {
/* 1214 */       s = 8;
/* 1215 */     } else if ("PRELIGHT".equals(str)) {
/* 1216 */       s = 2;
/*      */     } 
/*      */     
/* 1219 */     GTKPainter.INSTANCE.paintMetacityElement(this.context, paramGraphics, s, "metacity-vline", i, j, 1, k - j, null, null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawGradient(Node paramNode, Graphics paramGraphics) {
/* 1224 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 1225 */     String str = getStringAttr(namedNodeMap, "type");
/* 1226 */     float f = getFloatAttr(paramNode, "alpha", -1.0F);
/* 1227 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/* 1228 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/* 1229 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "width"));
/* 1230 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "height"));
/* 1231 */     if (getInt("width") == -1) {
/* 1232 */       i -= k;
/*      */     }
/* 1234 */     if (getInt("height") == -1) {
/* 1235 */       j -= m;
/*      */     }
/*      */ 
/*      */     
/* 1239 */     Node[] arrayOfNode = getNodesByName(paramNode, "color");
/* 1240 */     Color[] arrayOfColor = new Color[arrayOfNode.length]; byte b;
/* 1241 */     for (b = 0; b < arrayOfNode.length; b++) {
/* 1242 */       arrayOfColor[b] = parseColor(getStringAttr(arrayOfNode[b], "value"));
/*      */     }
/*      */     
/* 1245 */     b = ("diagonal".equals(str) || "horizontal".equals(str)) ? 1 : 0;
/* 1246 */     boolean bool = ("diagonal".equals(str) || "vertical".equals(str)) ? true : false;
/*      */     
/* 1248 */     if (paramGraphics instanceof Graphics2D) {
/* 1249 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 1250 */       Composite composite = graphics2D.getComposite();
/* 1251 */       if (f >= 0.0F) {
/* 1252 */         graphics2D.setComposite(AlphaComposite.getInstance(3, f));
/*      */       }
/* 1254 */       int n = arrayOfColor.length - 1;
/* 1255 */       for (byte b1 = 0; b1 < n; b1++) {
/* 1256 */         graphics2D.setPaint(new GradientPaint((i + ((b != 0) ? (b1 * k / n) : 0)), (j + (bool ? (b1 * m / n) : 0)), arrayOfColor[b1], (i + ((b != 0) ? ((b1 + 1) * k / n) : 0)), (j + (bool ? ((b1 + 1) * m / n) : 0)), arrayOfColor[b1 + 1]));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1262 */         graphics2D.fillRect(i + ((b != 0) ? (b1 * k / n) : 0), j + (bool ? (b1 * m / n) : 0), (b != 0) ? (k / n) : k, bool ? (m / n) : m);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1267 */       graphics2D.setComposite(composite);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void drawImage(Node paramNode, Graphics paramGraphics) {
/* 1272 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 1273 */     String str1 = getStringAttr(namedNodeMap, "filename");
/* 1274 */     String str2 = getStringAttr(namedNodeMap, "colorize");
/* 1275 */     Color color = (str2 != null) ? parseColor(str2) : null;
/* 1276 */     String str3 = getStringAttr(namedNodeMap, "alpha");
/* 1277 */     Image image = (color != null) ? getImage(str1, color) : getImage(str1);
/* 1278 */     this.variables.put("object_width", Integer.valueOf(image.getWidth(null)));
/* 1279 */     this.variables.put("object_height", Integer.valueOf(image.getHeight(null)));
/* 1280 */     String str4 = getStringAttr(namedNodeMap, "fill_type");
/* 1281 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/* 1282 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/* 1283 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "width"));
/* 1284 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "height"));
/* 1285 */     if (getInt("width") == -1) {
/* 1286 */       i -= k;
/*      */     }
/* 1288 */     if (getInt("height") == -1) {
/* 1289 */       j -= m;
/*      */     }
/*      */     
/* 1292 */     if (str3 != null) {
/* 1293 */       if ("tile".equals(str4)) {
/* 1294 */         StringTokenizer stringTokenizer = new StringTokenizer(str3, ":");
/* 1295 */         float[] arrayOfFloat = new float[stringTokenizer.countTokens()];
/* 1296 */         for (byte b = 0; b < arrayOfFloat.length; b++) {
/* 1297 */           arrayOfFloat[b] = Float.parseFloat(stringTokenizer.nextToken());
/*      */         }
/* 1299 */         tileImage(paramGraphics, image, i, j, k, m, arrayOfFloat);
/*      */       } else {
/* 1301 */         float f = Float.parseFloat(str3);
/* 1302 */         if (paramGraphics instanceof Graphics2D) {
/* 1303 */           Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 1304 */           Composite composite = graphics2D.getComposite();
/* 1305 */           graphics2D.setComposite(AlphaComposite.getInstance(3, f));
/* 1306 */           graphics2D.drawImage(image, i, j, k, m, null);
/* 1307 */           graphics2D.setComposite(composite);
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1311 */       paramGraphics.drawImage(image, i, j, k, m, null);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void drawIcon(Node paramNode, Graphics paramGraphics, JInternalFrame paramJInternalFrame) {
/* 1316 */     Icon icon = paramJInternalFrame.getFrameIcon();
/* 1317 */     if (icon == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1321 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 1322 */     String str = getStringAttr(namedNodeMap, "alpha");
/* 1323 */     int i = this.aee.evaluate(getStringAttr(namedNodeMap, "x"));
/* 1324 */     int j = this.aee.evaluate(getStringAttr(namedNodeMap, "y"));
/* 1325 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "width"));
/* 1326 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "height"));
/* 1327 */     if (getInt("width") == -1) {
/* 1328 */       i -= k;
/*      */     }
/* 1330 */     if (getInt("height") == -1) {
/* 1331 */       j -= m;
/*      */     }
/*      */     
/* 1334 */     if (str != null) {
/* 1335 */       float f = Float.parseFloat(str);
/* 1336 */       if (paramGraphics instanceof Graphics2D) {
/* 1337 */         Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 1338 */         Composite composite = graphics2D.getComposite();
/* 1339 */         graphics2D.setComposite(AlphaComposite.getInstance(3, f));
/* 1340 */         icon.paintIcon(paramJInternalFrame, paramGraphics, i, j);
/* 1341 */         graphics2D.setComposite(composite);
/*      */       } 
/*      */     } else {
/* 1344 */       icon.paintIcon(paramJInternalFrame, paramGraphics, i, j);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void drawInclude(Node paramNode, Graphics paramGraphics, JInternalFrame paramJInternalFrame) {
/* 1349 */     int i = getInt("width");
/* 1350 */     int j = getInt("height");
/*      */     
/* 1352 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 1353 */     int k = this.aee.evaluate(getStringAttr(namedNodeMap, "x"), 0);
/* 1354 */     int m = this.aee.evaluate(getStringAttr(namedNodeMap, "y"), 0);
/* 1355 */     int n = this.aee.evaluate(getStringAttr(namedNodeMap, "width"), -1);
/* 1356 */     int i1 = this.aee.evaluate(getStringAttr(namedNodeMap, "height"), -1);
/*      */     
/* 1358 */     if (n != -1) {
/* 1359 */       this.variables.put("width", Integer.valueOf(n));
/*      */     }
/* 1361 */     if (i1 != -1) {
/* 1362 */       this.variables.put("height", Integer.valueOf(i1));
/*      */     }
/*      */     
/* 1365 */     Node node = getNode("draw_ops", new String[] { "name", 
/* 1366 */           getStringAttr(paramNode, "name") });
/*      */     
/* 1368 */     paramGraphics.translate(k, m);
/* 1369 */     draw(node, paramGraphics, paramJInternalFrame);
/* 1370 */     paramGraphics.translate(-k, -m);
/*      */     
/* 1372 */     if (n != -1) {
/* 1373 */       this.variables.put("width", Integer.valueOf(i));
/*      */     }
/* 1375 */     if (i1 != -1) {
/* 1376 */       this.variables.put("height", Integer.valueOf(j));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void draw(Node paramNode, Graphics paramGraphics, JInternalFrame paramJInternalFrame) {
/* 1381 */     if (paramNode != null) {
/* 1382 */       NodeList nodeList = paramNode.getChildNodes();
/* 1383 */       if (nodeList != null) {
/* 1384 */         Shape shape = paramGraphics.getClip();
/* 1385 */         for (byte b = 0; b < nodeList.getLength(); b++) {
/* 1386 */           Node node = nodeList.item(b);
/* 1387 */           if (node.getNodeType() == 1) {
/*      */             try {
/* 1389 */               String str = node.getNodeName();
/* 1390 */               if ("include".equals(str)) {
/* 1391 */                 drawInclude(node, paramGraphics, paramJInternalFrame);
/* 1392 */               } else if ("arc".equals(str)) {
/* 1393 */                 drawArc(node, paramGraphics);
/* 1394 */               } else if ("clip".equals(str)) {
/* 1395 */                 setClip(node, paramGraphics);
/* 1396 */               } else if ("gradient".equals(str)) {
/* 1397 */                 drawGradient(node, paramGraphics);
/* 1398 */               } else if ("gtk_arrow".equals(str)) {
/* 1399 */                 drawGTKArrow(node, paramGraphics);
/* 1400 */               } else if ("gtk_box".equals(str)) {
/* 1401 */                 drawGTKBox(node, paramGraphics);
/* 1402 */               } else if ("gtk_vline".equals(str)) {
/* 1403 */                 drawGTKVLine(node, paramGraphics);
/* 1404 */               } else if ("image".equals(str)) {
/* 1405 */                 drawImage(node, paramGraphics);
/* 1406 */               } else if ("icon".equals(str)) {
/* 1407 */                 drawIcon(node, paramGraphics, paramJInternalFrame);
/* 1408 */               } else if ("line".equals(str)) {
/* 1409 */                 drawLine(node, paramGraphics);
/* 1410 */               } else if ("rectangle".equals(str)) {
/* 1411 */                 drawRectangle(node, paramGraphics);
/* 1412 */               } else if ("tint".equals(str)) {
/* 1413 */                 drawTint(node, paramGraphics);
/* 1414 */               } else if ("tile".equals(str)) {
/* 1415 */                 drawTile(node, paramGraphics, paramJInternalFrame);
/* 1416 */               } else if ("title".equals(str)) {
/* 1417 */                 drawTitle(node, paramGraphics, paramJInternalFrame);
/*      */               } else {
/* 1419 */                 System.err.println("Unknown Metacity drawing op: " + node);
/*      */               } 
/* 1421 */             } catch (NumberFormatException numberFormatException) {
/* 1422 */               logError(this.themeName, numberFormatException);
/*      */             } 
/*      */           }
/*      */         } 
/* 1426 */         paramGraphics.setClip(shape);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawPiece(Node paramNode, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, JInternalFrame paramJInternalFrame) {
/* 1433 */     Node node = getNode(paramNode, "piece", new String[] { "position", paramString });
/* 1434 */     if (node != null) {
/*      */       Node node1;
/* 1436 */       String str = getStringAttr(node, "draw_ops");
/* 1437 */       if (str != null) {
/* 1438 */         node1 = getNode("draw_ops", new String[] { "name", str });
/*      */       } else {
/* 1440 */         node1 = getNode(node, "draw_ops", (String[])null);
/*      */       } 
/* 1442 */       this.variables.put("width", Integer.valueOf(paramInt3));
/* 1443 */       this.variables.put("height", Integer.valueOf(paramInt4));
/* 1444 */       paramGraphics.translate(paramInt1, paramInt2);
/* 1445 */       draw(node1, paramGraphics, paramJInternalFrame);
/* 1446 */       paramGraphics.translate(-paramInt1, -paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   Insets getBorderInsets(SynthContext paramSynthContext, Insets paramInsets) {
/* 1452 */     updateFrameGeometry(paramSynthContext);
/*      */     
/* 1454 */     if (paramInsets == null) {
/* 1455 */       paramInsets = new Insets(0, 0, 0, 0);
/*      */     }
/* 1457 */     paramInsets.top = ((Insets)this.frameGeometry.get("title_border")).top;
/* 1458 */     paramInsets.bottom = getInt("bottom_height");
/* 1459 */     paramInsets.left = getInt("left_width");
/* 1460 */     paramInsets.right = getInt("right_width");
/* 1461 */     return paramInsets;
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateFrameGeometry(SynthContext paramSynthContext) {
/* 1466 */     this.context = paramSynthContext;
/* 1467 */     JComponent jComponent1 = paramSynthContext.getComponent();
/* 1468 */     JComponent jComponent2 = findChild(jComponent1, "InternalFrame.northPane");
/*      */     
/* 1470 */     JInternalFrame jInternalFrame = null;
/* 1471 */     if (jComponent1 instanceof JInternalFrame) {
/* 1472 */       jInternalFrame = (JInternalFrame)jComponent1;
/* 1473 */     } else if (jComponent1 instanceof JInternalFrame.JDesktopIcon) {
/* 1474 */       jInternalFrame = ((JInternalFrame.JDesktopIcon)jComponent1).getInternalFrame();
/*      */     } else {
/* 1476 */       assert false : "component is not JInternalFrame or JInternalFrame.JDesktopIcon";
/*      */       
/*      */       return;
/*      */     } 
/* 1480 */     if (this.frame_style_set == null) {
/* 1481 */       Node node = getNode("window", new String[] { "type", "normal" });
/*      */       
/* 1483 */       if (node != null) {
/* 1484 */         this.frame_style_set = getNode("frame_style_set", new String[] { "name", 
/* 1485 */               getStringAttr(node, "style_set") });
/*      */       }
/*      */       
/* 1488 */       if (this.frame_style_set == null) {
/* 1489 */         this.frame_style_set = getNode("frame_style_set", new String[] { "name", "normal" });
/*      */       }
/*      */     } 
/*      */     
/* 1493 */     if (this.frame_style_set != null) {
/* 1494 */       Node node = getNode(this.frame_style_set, "frame", new String[] { "focus", 
/* 1495 */             jInternalFrame.isSelected() ? "yes" : "no", "state", 
/* 1496 */             jInternalFrame.isMaximum() ? "maximized" : "normal" });
/*      */ 
/*      */       
/* 1499 */       if (node != null) {
/* 1500 */         Node node1 = getNode("frame_style", new String[] { "name", 
/* 1501 */               getStringAttr(node, "style") });
/*      */         
/* 1503 */         if (node1 != null) {
/* 1504 */           Map map = this.frameGeometries.get(getStringAttr(node1, "geometry"));
/*      */           
/* 1506 */           setFrameGeometry(jComponent2, map);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected static void logError(String paramString, Exception paramException) {
/* 1514 */     logError(paramString, paramException.toString());
/*      */   }
/*      */   
/*      */   protected static void logError(String paramString1, String paramString2) {
/* 1518 */     if (!errorLogged) {
/* 1519 */       System.err.println("Exception in Metacity for theme \"" + paramString1 + "\": " + paramString2);
/* 1520 */       errorLogged = true;
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
/*      */   protected static Document getXMLDoc(final URL xmlFile) throws IOException, ParserConfigurationException, SAXException {
/* 1532 */     if (documentBuilder == null)
/*      */     {
/* 1534 */       documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
/*      */     }
/*      */     
/* 1537 */     InputStream inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*      */           public InputStream run() {
/*      */             try {
/* 1540 */               return new BufferedInputStream(xmlFile.openStream());
/* 1541 */             } catch (IOException iOException) {
/* 1542 */               return null;
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/* 1547 */     Document document = null;
/* 1548 */     if (inputStream != null) {
/* 1549 */       document = documentBuilder.parse(inputStream);
/*      */     }
/* 1551 */     return document;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Node[] getNodesByName(Node paramNode, String paramString) {
/* 1556 */     NodeList nodeList = paramNode.getChildNodes();
/* 1557 */     int i = nodeList.getLength();
/* 1558 */     ArrayList<Node> arrayList = new ArrayList();
/* 1559 */     for (byte b = 0; b < i; b++) {
/* 1560 */       Node node = nodeList.item(b);
/* 1561 */       if (paramString.equals(node.getNodeName())) {
/* 1562 */         arrayList.add(node);
/*      */       }
/*      */     } 
/* 1565 */     return arrayList.<Node>toArray(new Node[arrayList.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Node getNode(String paramString, String[] paramArrayOfString) {
/* 1571 */     NodeList nodeList = xmlDoc.getElementsByTagName(paramString);
/* 1572 */     return (nodeList != null) ? getNode(nodeList, paramString, paramArrayOfString) : null;
/*      */   }
/*      */   
/*      */   protected Node getNode(Node paramNode, String paramString, String[] paramArrayOfString) {
/* 1576 */     Node node = null;
/* 1577 */     NodeList nodeList = paramNode.getChildNodes();
/* 1578 */     if (nodeList != null) {
/* 1579 */       node = getNode(nodeList, paramString, paramArrayOfString);
/*      */     }
/* 1581 */     if (node == null) {
/* 1582 */       String str = getStringAttr(paramNode, "parent");
/* 1583 */       if (str != null) {
/* 1584 */         Node node1 = getNode(paramNode.getParentNode(), paramNode
/* 1585 */             .getNodeName(), new String[] { "name", str });
/*      */         
/* 1587 */         if (node1 != null) {
/* 1588 */           node = getNode(node1, paramString, paramArrayOfString);
/*      */         }
/*      */       } 
/*      */     } 
/* 1592 */     return node;
/*      */   }
/*      */   
/*      */   protected Node getNode(NodeList paramNodeList, String paramString, String[] paramArrayOfString) {
/* 1596 */     int i = paramNodeList.getLength();
/* 1597 */     for (byte b = 0; b < i; b++) {
/* 1598 */       Node node = paramNodeList.item(b);
/* 1599 */       if (paramString.equals(node.getNodeName())) {
/* 1600 */         if (paramArrayOfString != null) {
/* 1601 */           NamedNodeMap namedNodeMap = node.getAttributes();
/* 1602 */           if (namedNodeMap != null) {
/* 1603 */             boolean bool = true;
/* 1604 */             int j = paramArrayOfString.length / 2;
/* 1605 */             for (byte b1 = 0; b1 < j; b1++) {
/* 1606 */               String str1 = paramArrayOfString[b1 * 2];
/* 1607 */               String str2 = paramArrayOfString[b1 * 2 + 1];
/* 1608 */               Node node1 = namedNodeMap.getNamedItem(str1);
/* 1609 */               if (node1 == null || (str2 != null && 
/* 1610 */                 !str2.equals(node1.getNodeValue()))) {
/* 1611 */                 bool = false;
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1615 */             if (bool) {
/* 1616 */               return node;
/*      */             }
/*      */           } 
/*      */         } else {
/* 1620 */           return node;
/*      */         } 
/*      */       }
/*      */     } 
/* 1624 */     return null;
/*      */   }
/*      */   
/*      */   protected String getStringAttr(Node paramNode, String paramString) {
/* 1628 */     String str = null;
/* 1629 */     NamedNodeMap namedNodeMap = paramNode.getAttributes();
/* 1630 */     if (namedNodeMap != null) {
/* 1631 */       str = getStringAttr(namedNodeMap, paramString);
/* 1632 */       if (str == null) {
/* 1633 */         String str1 = getStringAttr(namedNodeMap, "parent");
/* 1634 */         if (str1 != null) {
/* 1635 */           Node node = getNode(paramNode.getParentNode(), paramNode
/* 1636 */               .getNodeName(), new String[] { "name", str1 });
/*      */           
/* 1638 */           if (node != null) {
/* 1639 */             str = getStringAttr(node, paramString);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1644 */     return str;
/*      */   }
/*      */   
/*      */   protected String getStringAttr(NamedNodeMap paramNamedNodeMap, String paramString) {
/* 1648 */     Node node = paramNamedNodeMap.getNamedItem(paramString);
/* 1649 */     return (node != null) ? node.getNodeValue() : null;
/*      */   }
/*      */   
/*      */   protected boolean getBooleanAttr(Node paramNode, String paramString, boolean paramBoolean) {
/* 1653 */     String str = getStringAttr(paramNode, paramString);
/* 1654 */     if (str != null) {
/* 1655 */       return Boolean.valueOf(str).booleanValue();
/*      */     }
/* 1657 */     return paramBoolean;
/*      */   }
/*      */   
/*      */   protected int getIntAttr(Node paramNode, String paramString, int paramInt) {
/* 1661 */     String str = getStringAttr(paramNode, paramString);
/* 1662 */     int i = paramInt;
/* 1663 */     if (str != null) {
/*      */       try {
/* 1665 */         i = Integer.parseInt(str);
/* 1666 */       } catch (NumberFormatException numberFormatException) {
/* 1667 */         logError(this.themeName, numberFormatException);
/*      */       } 
/*      */     }
/* 1670 */     return i;
/*      */   }
/*      */   
/*      */   protected float getFloatAttr(Node paramNode, String paramString, float paramFloat) {
/* 1674 */     String str = getStringAttr(paramNode, paramString);
/* 1675 */     float f = paramFloat;
/* 1676 */     if (str != null) {
/*      */       try {
/* 1678 */         f = Float.parseFloat(str);
/* 1679 */       } catch (NumberFormatException numberFormatException) {
/* 1680 */         logError(this.themeName, numberFormatException);
/*      */       } 
/*      */     }
/* 1683 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Color parseColor(String paramString) {
/* 1689 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "/");
/* 1690 */     int i = stringTokenizer.countTokens();
/* 1691 */     if (i > 1) {
/* 1692 */       String str = stringTokenizer.nextToken();
/* 1693 */       if ("shade".equals(str)) {
/* 1694 */         assert i == 3;
/* 1695 */         Color color = parseColor2(stringTokenizer.nextToken());
/* 1696 */         float f = Float.parseFloat(stringTokenizer.nextToken());
/* 1697 */         return GTKColorType.adjustColor(color, 1.0F, f, f);
/* 1698 */       }  if ("blend".equals(str)) {
/* 1699 */         assert i == 4;
/* 1700 */         Color color1 = parseColor2(stringTokenizer.nextToken());
/* 1701 */         Color color2 = parseColor2(stringTokenizer.nextToken());
/* 1702 */         float f = Float.parseFloat(stringTokenizer.nextToken());
/* 1703 */         if (f > 1.0F) {
/* 1704 */           f = 1.0F / f;
/*      */         }
/*      */         
/* 1707 */         return new Color((int)(color1.getRed() + (color2.getRed() - color1.getRed()) * f), 
/* 1708 */             (int)(color1.getRed() + (color2.getRed() - color1.getRed()) * f), 
/* 1709 */             (int)(color1.getRed() + (color2.getRed() - color1.getRed()) * f));
/*      */       } 
/* 1711 */       System.err.println("Unknown Metacity color function=" + paramString);
/* 1712 */       return null;
/*      */     } 
/*      */     
/* 1715 */     return parseColor2(paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Color parseColor2(String paramString) {
/* 1720 */     Color color = null;
/* 1721 */     if (paramString.startsWith("gtk:")) {
/* 1722 */       int i = paramString.indexOf('[');
/* 1723 */       if (i > 3) {
/* 1724 */         String str = paramString.substring(4, i).toLowerCase();
/* 1725 */         int j = paramString.indexOf(']');
/* 1726 */         if (j > i + 1) {
/* 1727 */           String str1 = paramString.substring(i + 1, j).toUpperCase();
/* 1728 */           short s = -1;
/* 1729 */           if ("ACTIVE".equals(str1)) {
/* 1730 */             s = 4;
/* 1731 */           } else if ("INSENSITIVE".equals(str1)) {
/* 1732 */             s = 8;
/* 1733 */           } else if ("NORMAL".equals(str1)) {
/* 1734 */             s = 1;
/* 1735 */           } else if ("PRELIGHT".equals(str1)) {
/* 1736 */             s = 2;
/* 1737 */           } else if ("SELECTED".equals(str1)) {
/* 1738 */             s = 512;
/*      */           } 
/* 1740 */           ColorType colorType = null;
/* 1741 */           if ("fg".equals(str)) {
/* 1742 */             colorType = GTKColorType.FOREGROUND;
/* 1743 */           } else if ("bg".equals(str)) {
/* 1744 */             colorType = GTKColorType.BACKGROUND;
/* 1745 */           } else if ("base".equals(str)) {
/* 1746 */             colorType = GTKColorType.TEXT_BACKGROUND;
/* 1747 */           } else if ("text".equals(str)) {
/* 1748 */             colorType = GTKColorType.TEXT_FOREGROUND;
/* 1749 */           } else if ("dark".equals(str)) {
/* 1750 */             colorType = GTKColorType.DARK;
/* 1751 */           } else if ("light".equals(str)) {
/* 1752 */             colorType = GTKColorType.LIGHT;
/*      */           } 
/* 1754 */           if (s >= 0 && colorType != null) {
/* 1755 */             color = ((GTKStyle)this.context.getStyle()).getGTKColor(this.context, s, colorType);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1760 */     if (color == null) {
/* 1761 */       color = parseColorString(paramString);
/*      */     }
/* 1763 */     return color;
/*      */   }
/*      */   
/*      */   private static Color parseColorString(String paramString) {
/* 1767 */     if (paramString.charAt(0) == '#') {
/* 1768 */       int j, k, m; paramString = paramString.substring(1);
/*      */       
/* 1770 */       int i = paramString.length();
/*      */       
/* 1772 */       if (i < 3 || i > 12 || i % 3 != 0) {
/* 1773 */         return null;
/*      */       }
/*      */       
/* 1776 */       i /= 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1783 */         j = Integer.parseInt(paramString.substring(0, i), 16);
/* 1784 */         k = Integer.parseInt(paramString.substring(i, i * 2), 16);
/* 1785 */         m = Integer.parseInt(paramString.substring(i * 2, i * 3), 16);
/* 1786 */       } catch (NumberFormatException numberFormatException) {
/* 1787 */         return null;
/*      */       } 
/*      */       
/* 1790 */       if (i == 4)
/* 1791 */         return new ColorUIResource(j / 65535.0F, k / 65535.0F, m / 65535.0F); 
/* 1792 */       if (i == 1)
/* 1793 */         return new ColorUIResource(j / 15.0F, k / 15.0F, m / 15.0F); 
/* 1794 */       if (i == 2) {
/* 1795 */         return new ColorUIResource(j, k, m);
/*      */       }
/* 1797 */       return new ColorUIResource(j / 4095.0F, k / 4095.0F, m / 4095.0F);
/*      */     } 
/*      */     
/* 1800 */     return XColors.lookupColor(paramString);
/*      */   }
/*      */   
/*      */   class ArithmeticExpressionEvaluator
/*      */   {
/*      */     private Metacity.PeekableStringTokenizer tokenizer;
/*      */     
/*      */     int evaluate(String param1String) {
/* 1808 */       this.tokenizer = new Metacity.PeekableStringTokenizer(param1String, " \t+-*/%()", true);
/* 1809 */       return Math.round(expression());
/*      */     }
/*      */     
/*      */     int evaluate(String param1String, int param1Int) {
/* 1813 */       return (param1String != null) ? evaluate(param1String) : param1Int;
/*      */     }
/*      */     
/*      */     public float expression() {
/* 1817 */       float f = getTermValue();
/* 1818 */       boolean bool = false;
/* 1819 */       while (!bool && this.tokenizer.hasMoreTokens()) {
/* 1820 */         String str = this.tokenizer.peek();
/* 1821 */         if ("+".equals(str) || "-"
/* 1822 */           .equals(str) || "`max`"
/* 1823 */           .equals(str) || "`min`"
/* 1824 */           .equals(str)) {
/* 1825 */           this.tokenizer.nextToken();
/* 1826 */           float f1 = getTermValue();
/* 1827 */           if ("+".equals(str)) {
/* 1828 */             f += f1; continue;
/* 1829 */           }  if ("-".equals(str)) {
/* 1830 */             f -= f1; continue;
/* 1831 */           }  if ("`max`".equals(str)) {
/* 1832 */             f = Math.max(f, f1); continue;
/* 1833 */           }  if ("`min`".equals(str))
/* 1834 */             f = Math.min(f, f1); 
/*      */           continue;
/*      */         } 
/* 1837 */         bool = true;
/*      */       } 
/*      */       
/* 1840 */       return f;
/*      */     }
/*      */     
/*      */     public float getTermValue() {
/* 1844 */       float f = getFactorValue();
/* 1845 */       boolean bool = false;
/* 1846 */       while (!bool && this.tokenizer.hasMoreTokens()) {
/* 1847 */         String str = this.tokenizer.peek();
/* 1848 */         if ("*".equals(str) || "/".equals(str) || "%".equals(str)) {
/* 1849 */           this.tokenizer.nextToken();
/* 1850 */           float f1 = getFactorValue();
/* 1851 */           if ("*".equals(str)) {
/* 1852 */             f *= f1; continue;
/* 1853 */           }  if ("/".equals(str)) {
/* 1854 */             f /= f1; continue;
/*      */           } 
/* 1856 */           f %= f1;
/*      */           continue;
/*      */         } 
/* 1859 */         bool = true;
/*      */       } 
/*      */       
/* 1862 */       return f;
/*      */     }
/*      */     
/*      */     public float getFactorValue() {
/*      */       float f;
/* 1867 */       if ("(".equals(this.tokenizer.peek())) {
/* 1868 */         this.tokenizer.nextToken();
/* 1869 */         f = expression();
/* 1870 */         this.tokenizer.nextToken();
/*      */       } else {
/* 1872 */         String str = this.tokenizer.nextToken();
/* 1873 */         if (Character.isDigit(str.charAt(0))) {
/* 1874 */           f = Float.parseFloat(str);
/*      */         } else {
/* 1876 */           Integer integer = (Integer)Metacity.this.variables.get(str);
/* 1877 */           if (integer == null) {
/* 1878 */             integer = (Integer)Metacity.this.getFrameGeometry().get(str);
/*      */           }
/* 1880 */           if (integer == null) {
/* 1881 */             Metacity.logError(Metacity.this.themeName, "Variable \"" + str + "\" not defined");
/* 1882 */             return 0.0F;
/*      */           } 
/* 1884 */           f = (integer != null) ? integer.intValue() : 0.0F;
/*      */         } 
/*      */       } 
/* 1887 */       return f;
/*      */     }
/*      */   }
/*      */   
/*      */   static class PeekableStringTokenizer
/*      */     extends StringTokenizer
/*      */   {
/* 1894 */     String token = null;
/*      */ 
/*      */     
/*      */     public PeekableStringTokenizer(String param1String1, String param1String2, boolean param1Boolean) {
/* 1898 */       super(param1String1, param1String2, param1Boolean);
/* 1899 */       peek();
/*      */     }
/*      */     
/*      */     public String peek() {
/* 1903 */       if (this.token == null) {
/* 1904 */         this.token = nextToken();
/*      */       }
/* 1906 */       return this.token;
/*      */     }
/*      */     
/*      */     public boolean hasMoreTokens() {
/* 1910 */       return (this.token != null || super.hasMoreTokens());
/*      */     }
/*      */     
/*      */     public String nextToken() {
/* 1914 */       if (this.token != null) {
/* 1915 */         String str1 = this.token;
/* 1916 */         this.token = null;
/* 1917 */         if (hasMoreTokens()) {
/* 1918 */           peek();
/*      */         }
/* 1920 */         return str1;
/*      */       } 
/* 1922 */       String str = super.nextToken();
/* 1923 */       while ((str.equals(" ") || str.equals("\t")) && 
/* 1924 */         hasMoreTokens()) {
/* 1925 */         str = super.nextToken();
/*      */       }
/* 1927 */       return str;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class RoundRectClipShape
/*      */     extends RectangularShape
/*      */   {
/*      */     static final int TOP_LEFT = 1;
/*      */     
/*      */     static final int TOP_RIGHT = 2;
/*      */     
/*      */     static final int BOTTOM_LEFT = 4;
/*      */     static final int BOTTOM_RIGHT = 8;
/*      */     int x;
/*      */     int y;
/*      */     int width;
/*      */     int height;
/*      */     int arcwidth;
/*      */     int archeight;
/*      */     int corners;
/*      */     
/*      */     public RoundRectClipShape() {}
/*      */     
/*      */     public RoundRectClipShape(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7) {
/* 1952 */       setRoundedRect(param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1Int7);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setRoundedRect(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7) {
/* 1957 */       this.corners = param1Int7;
/* 1958 */       this.x = param1Int1;
/* 1959 */       this.y = param1Int2;
/* 1960 */       this.width = param1Int3;
/* 1961 */       this.height = param1Int4;
/* 1962 */       this.arcwidth = param1Int5;
/* 1963 */       this.archeight = param1Int6;
/*      */     }
/*      */     
/*      */     public double getX() {
/* 1967 */       return this.x;
/*      */     }
/*      */     
/*      */     public double getY() {
/* 1971 */       return this.y;
/*      */     }
/*      */     
/*      */     public double getWidth() {
/* 1975 */       return this.width;
/*      */     }
/*      */     
/*      */     public double getHeight() {
/* 1979 */       return this.height;
/*      */     }
/*      */     
/*      */     public double getArcWidth() {
/* 1983 */       return this.arcwidth;
/*      */     }
/*      */     
/*      */     public double getArcHeight() {
/* 1987 */       return this.archeight;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1991 */       return false;
/*      */     }
/*      */     
/*      */     public Rectangle2D getBounds2D() {
/* 1995 */       return null;
/*      */     }
/*      */     
/*      */     public int getCornerFlags() {
/* 1999 */       return this.corners;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setFrame(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {}
/*      */ 
/*      */     
/*      */     public boolean contains(double param1Double1, double param1Double2) {
/* 2007 */       return false;
/*      */     }
/*      */     
/*      */     private int classify(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 2011 */       return 0;
/*      */     }
/*      */     
/*      */     public boolean intersects(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 2015 */       return false;
/*      */     }
/*      */     
/*      */     public boolean contains(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 2019 */       return false;
/*      */     }
/*      */     
/*      */     public PathIterator getPathIterator(AffineTransform param1AffineTransform) {
/* 2023 */       return new RoundishRectIterator(this, param1AffineTransform);
/*      */     }
/*      */     static class RoundishRectIterator implements PathIterator { double x;
/*      */       double y;
/*      */       double w;
/*      */       double h;
/*      */       double aw;
/*      */       double ah;
/*      */       AffineTransform affine;
/*      */       int index;
/*      */       double[][] ctrlpts;
/*      */       int[] types;
/*      */       private static final double angle = 0.7853981633974483D;
/* 2036 */       private static final double a = 1.0D - Math.cos(0.7853981633974483D);
/* 2037 */       private static final double b = Math.tan(0.7853981633974483D);
/* 2038 */       private static final double c = Math.sqrt(1.0D + b * b) - 1.0D + a;
/* 2039 */       private static final double cv = 1.3333333333333333D * a * b / c;
/* 2040 */       private static final double acv = (1.0D - cv) / 2.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2046 */       private static final double[][] CtrlPtTemplate = new double[][] { { 0.0D, 0.0D, 1.0D, 0.0D }, { 0.0D, 0.0D, 1.0D, -0.5D }, { 0.0D, 0.0D, 1.0D, -acv, 0.0D, acv, 1.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.0D }, { 1.0D, 0.0D, 1.0D, 0.0D }, { 1.0D, -0.5D, 1.0D, 0.0D }, { 1.0D, -acv, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, -acv, 1.0D, 0.0D, 1.0D, -0.5D }, { 1.0D, 0.0D, 0.0D, 0.0D }, { 1.0D, 0.0D, 0.0D, 0.5D }, { 1.0D, 0.0D, 0.0D, acv, 1.0D, -acv, 0.0D, 0.0D, 1.0D, -0.5D, 0.0D, 0.0D }, { 0.0D, 0.0D, 0.0D, 0.0D }, { 0.0D, 0.5D, 0.0D, 0.0D }, { 0.0D, acv, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, acv, 0.0D, 0.0D, 0.0D, 0.5D }, {} };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2069 */       private static final int[] CornerFlags = new int[] { 4, 8, 2, 1 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       RoundishRectIterator(Metacity.RoundRectClipShape param2RoundRectClipShape, AffineTransform param2AffineTransform) {
/* 2077 */         this.x = param2RoundRectClipShape.getX();
/* 2078 */         this.y = param2RoundRectClipShape.getY();
/* 2079 */         this.w = param2RoundRectClipShape.getWidth();
/* 2080 */         this.h = param2RoundRectClipShape.getHeight();
/* 2081 */         this.aw = Math.min(this.w, Math.abs(param2RoundRectClipShape.getArcWidth()));
/* 2082 */         this.ah = Math.min(this.h, Math.abs(param2RoundRectClipShape.getArcHeight()));
/* 2083 */         this.affine = param2AffineTransform;
/* 2084 */         if (this.w < 0.0D || this.h < 0.0D) {
/*      */           
/* 2086 */           this.ctrlpts = new double[0][];
/* 2087 */           this.types = new int[0];
/*      */         } else {
/* 2089 */           int i = param2RoundRectClipShape.getCornerFlags();
/* 2090 */           byte b1 = 5; int j;
/* 2091 */           for (j = 1; j < 16; j <<= 1) {
/*      */             
/* 2093 */             if ((i & j) != 0) b1++; 
/*      */           } 
/* 2095 */           this.ctrlpts = new double[b1][];
/* 2096 */           this.types = new int[b1];
/* 2097 */           j = 0;
/* 2098 */           for (byte b2 = 0; b2 < 4; b2++) {
/* 2099 */             this.types[j] = 1;
/* 2100 */             if ((i & CornerFlags[b2]) == 0) {
/* 2101 */               this.ctrlpts[j++] = CtrlPtTemplate[b2 * 3 + 0];
/*      */             } else {
/* 2103 */               this.ctrlpts[j++] = CtrlPtTemplate[b2 * 3 + 1];
/* 2104 */               this.types[j] = 3;
/* 2105 */               this.ctrlpts[j++] = CtrlPtTemplate[b2 * 3 + 2];
/*      */             } 
/*      */           } 
/* 2108 */           this.types[j] = 4;
/* 2109 */           this.ctrlpts[j++] = CtrlPtTemplate[12];
/* 2110 */           this.types[0] = 0;
/*      */         } 
/*      */       }
/*      */       
/*      */       public int getWindingRule() {
/* 2115 */         return 1;
/*      */       }
/*      */       
/*      */       public boolean isDone() {
/* 2119 */         return (this.index >= this.ctrlpts.length);
/*      */       }
/*      */       
/*      */       public void next() {
/* 2123 */         this.index++;
/*      */       }
/*      */       
/*      */       public int currentSegment(float[] param2ArrayOffloat) {
/* 2127 */         if (isDone()) {
/* 2128 */           throw new NoSuchElementException("roundrect iterator out of bounds");
/*      */         }
/* 2130 */         double[] arrayOfDouble = this.ctrlpts[this.index];
/* 2131 */         byte b1 = 0;
/* 2132 */         for (byte b2 = 0; b2 < arrayOfDouble.length; b2 += 4) {
/* 2133 */           param2ArrayOffloat[b1++] = (float)(this.x + arrayOfDouble[b2 + 0] * this.w + arrayOfDouble[b2 + 1] * this.aw);
/* 2134 */           param2ArrayOffloat[b1++] = (float)(this.y + arrayOfDouble[b2 + 2] * this.h + arrayOfDouble[b2 + 3] * this.ah);
/*      */         } 
/* 2136 */         if (this.affine != null) {
/* 2137 */           this.affine.transform(param2ArrayOffloat, 0, param2ArrayOffloat, 0, b1 / 2);
/*      */         }
/* 2139 */         return this.types[this.index];
/*      */       }
/*      */       
/*      */       public int currentSegment(double[] param2ArrayOfdouble) {
/* 2143 */         if (isDone()) {
/* 2144 */           throw new NoSuchElementException("roundrect iterator out of bounds");
/*      */         }
/* 2146 */         double[] arrayOfDouble = this.ctrlpts[this.index];
/* 2147 */         byte b1 = 0;
/* 2148 */         for (byte b2 = 0; b2 < arrayOfDouble.length; b2 += 4) {
/* 2149 */           param2ArrayOfdouble[b1++] = this.x + arrayOfDouble[b2 + 0] * this.w + arrayOfDouble[b2 + 1] * this.aw;
/* 2150 */           param2ArrayOfdouble[b1++] = this.y + arrayOfDouble[b2 + 2] * this.h + arrayOfDouble[b2 + 3] * this.ah;
/*      */         } 
/* 2152 */         if (this.affine != null) {
/* 2153 */           this.affine.transform(param2ArrayOfdouble, 0, param2ArrayOfdouble, 0, b1 / 2);
/*      */         }
/* 2155 */         return this.types[this.index];
/*      */       } }
/*      */   
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/gtk/Metacity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */