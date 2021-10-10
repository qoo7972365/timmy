/*      */ package sun.swing;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.FocusTraversalPolicy;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.font.LineBreakMeasurer;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.font.TextHitInfo;
/*      */ import java.awt.font.TextLayout;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.AttributedString;
/*      */ import java.text.BreakIterator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.ListModel;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.TreeModelEvent;
/*      */ import javax.swing.table.TableCellRenderer;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ import javax.swing.text.DefaultHighlighter;
/*      */ import javax.swing.text.Highlighter;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import javax.swing.tree.TreeModel;
/*      */ import javax.swing.tree.TreePath;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.font.FontDesignMetrics;
/*      */ import sun.font.FontUtilities;
/*      */ import sun.java2d.SunGraphicsEnvironment;
/*      */ import sun.print.ProxyPrintGraphics;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SwingUtilities2
/*      */ {
/*   82 */   public static final Object LAF_STATE_KEY = new StringBuffer("LookAndFeel State");
/*      */ 
/*      */   
/*   85 */   public static final Object MENU_SELECTION_MANAGER_LISTENER_KEY = new StringBuffer("MenuSelectionManager listener key");
/*      */ 
/*      */   
/*      */   private static LSBCacheEntry[] fontCache;
/*      */ 
/*      */   
/*      */   private static final int CACHE_SIZE = 6;
/*      */ 
/*      */   
/*      */   private static int nextIndex;
/*      */ 
/*      */   
/*      */   private static LSBCacheEntry searchKey;
/*      */ 
/*      */   
/*      */   private static final int MIN_CHAR_INDEX = 87;
/*      */ 
/*      */   
/*      */   private static final int MAX_CHAR_INDEX = 88;
/*      */ 
/*      */   
/*  106 */   public static final FontRenderContext DEFAULT_FRC = new FontRenderContext(null, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  114 */   public static final Object AA_TEXT_PROPERTY_KEY = new StringBuffer("AATextInfoPropertyKey");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String IMPLIED_CR = "CR";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  127 */   private static final StringBuilder SKIP_CLICK_COUNT = new StringBuilder("skipClickCount");
/*      */ 
/*      */   
/*      */   public static class AATextInfo
/*      */   {
/*      */     Object aaHint;
/*      */     Integer lcdContrastHint;
/*      */     FontRenderContext frc;
/*      */     
/*      */     private static AATextInfo getAATextInfoFromMap(Map param1Map) {
/*  137 */       Object object1 = param1Map.get(RenderingHints.KEY_TEXT_ANTIALIASING);
/*  138 */       Object object2 = param1Map.get(RenderingHints.KEY_TEXT_LCD_CONTRAST);
/*      */       
/*  140 */       if (object1 == null || object1 == RenderingHints.VALUE_TEXT_ANTIALIAS_OFF || object1 == RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT)
/*      */       {
/*      */         
/*  143 */         return null;
/*      */       }
/*  145 */       return new AATextInfo(object1, (Integer)object2);
/*      */     }
/*      */ 
/*      */     
/*      */     public static AATextInfo getAATextInfo(boolean param1Boolean) {
/*  150 */       SunToolkit.setAAFontSettingsCondition(param1Boolean);
/*  151 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  152 */       Object object = toolkit.getDesktopProperty("awt.font.desktophints");
/*  153 */       if (object instanceof Map) {
/*  154 */         return getAATextInfoFromMap((Map)object);
/*      */       }
/*  156 */       return null;
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
/*      */     public AATextInfo(Object param1Object, Integer param1Integer) {
/*  170 */       if (param1Object == null) {
/*  171 */         throw new InternalError("null not allowed here");
/*      */       }
/*  173 */       if (param1Object == RenderingHints.VALUE_TEXT_ANTIALIAS_OFF || param1Object == RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT)
/*      */       {
/*  175 */         throw new InternalError("AA must be on");
/*      */       }
/*  177 */       this.aaHint = param1Object;
/*  178 */       this.lcdContrastHint = param1Integer;
/*  179 */       this.frc = new FontRenderContext(null, param1Object, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  188 */   public static final Object COMPONENT_UI_PROPERTY_KEY = new StringBuffer("ComponentUIPropertyKey");
/*      */ 
/*      */ 
/*      */   
/*  192 */   public static final StringUIClientPropertyKey BASICMENUITEMUI_MAX_TEXT_OFFSET = new StringUIClientPropertyKey("maxTextOffset");
/*      */ 
/*      */ 
/*      */   
/*  196 */   private static Field inputEvent_CanAccessSystemClipboard_Field = null;
/*      */   
/*      */   private static final String UntrustedClipboardAccess = "UNTRUSTED_CLIPBOARD_ACCESS_KEY";
/*      */   
/*      */   private static final int CHAR_BUFFER_SIZE = 100;
/*      */   
/*  202 */   private static final Object charsBufferLock = new Object();
/*  203 */   private static char[] charsBuffer = new char[100];
/*      */   
/*      */   static {
/*  206 */     fontCache = new LSBCacheEntry[6];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int syncCharsBuffer(String paramString) {
/*  213 */     int i = paramString.length();
/*  214 */     if (charsBuffer == null || charsBuffer.length < i) {
/*  215 */       charsBuffer = paramString.toCharArray();
/*      */     } else {
/*  217 */       paramString.getChars(0, i, charsBuffer, 0);
/*      */     } 
/*  219 */     return i;
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
/*      */   public static final boolean isComplexLayout(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  232 */     return FontUtilities.isComplexText(paramArrayOfchar, paramInt1, paramInt2);
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
/*      */   public static AATextInfo drawTextAntialiased(JComponent paramJComponent) {
/*  255 */     if (paramJComponent != null)
/*      */     {
/*  257 */       return (AATextInfo)paramJComponent.getClientProperty(AA_TEXT_PROPERTY_KEY);
/*      */     }
/*      */     
/*  260 */     return null;
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
/*      */   public static int getLeftSideBearing(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString) {
/*  279 */     if (paramString == null || paramString.length() == 0) {
/*  280 */       return 0;
/*      */     }
/*  282 */     return getLeftSideBearing(paramJComponent, paramFontMetrics, paramString.charAt(0));
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
/*      */   public static int getLeftSideBearing(JComponent paramJComponent, FontMetrics paramFontMetrics, char paramChar) {
/*  295 */     char c = paramChar;
/*  296 */     if (c < 'X' && c >= 'W') {
/*  297 */       Object object = null;
/*      */       
/*  299 */       FontRenderContext fontRenderContext = getFontRenderContext(paramJComponent, paramFontMetrics);
/*  300 */       Font font = paramFontMetrics.getFont();
/*  301 */       synchronized (SwingUtilities2.class) {
/*  302 */         LSBCacheEntry lSBCacheEntry = null;
/*  303 */         if (searchKey == null) {
/*  304 */           searchKey = new LSBCacheEntry(fontRenderContext, font);
/*      */         } else {
/*  306 */           searchKey.reset(fontRenderContext, font);
/*      */         } 
/*      */         
/*  309 */         for (LSBCacheEntry lSBCacheEntry1 : fontCache) {
/*  310 */           if (searchKey.equals(lSBCacheEntry1)) {
/*  311 */             lSBCacheEntry = lSBCacheEntry1;
/*      */             break;
/*      */           } 
/*      */         } 
/*  315 */         if (lSBCacheEntry == null) {
/*      */           
/*  317 */           lSBCacheEntry = searchKey;
/*  318 */           fontCache[nextIndex] = searchKey;
/*  319 */           searchKey = null;
/*  320 */           nextIndex = (nextIndex + 1) % 6;
/*      */         } 
/*  322 */         return lSBCacheEntry.getLeftSideBearing(paramChar);
/*      */       } 
/*      */     } 
/*  325 */     return 0;
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
/*      */   public static FontMetrics getFontMetrics(JComponent paramJComponent, Graphics paramGraphics) {
/*  345 */     return getFontMetrics(paramJComponent, paramGraphics, paramGraphics.getFont());
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
/*      */   public static FontMetrics getFontMetrics(JComponent paramJComponent, Graphics paramGraphics, Font paramFont) {
/*  368 */     if (paramJComponent != null)
/*      */     {
/*      */ 
/*      */       
/*  372 */       return paramJComponent.getFontMetrics(paramFont);
/*      */     }
/*  374 */     return Toolkit.getDefaultToolkit().getFontMetrics(paramFont);
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
/*      */   public static int stringWidth(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString) {
/*  387 */     if (paramString == null || paramString.equals("")) {
/*  388 */       return 0;
/*      */     }
/*      */     
/*  391 */     boolean bool = (paramJComponent != null && paramJComponent.getClientProperty(TextAttribute.NUMERIC_SHAPING) != null);
/*  392 */     if (bool) {
/*  393 */       synchronized (charsBufferLock) {
/*  394 */         int i = syncCharsBuffer(paramString);
/*  395 */         bool = isComplexLayout(charsBuffer, 0, i);
/*      */       } 
/*      */     }
/*  398 */     if (bool) {
/*  399 */       TextLayout textLayout = createTextLayout(paramJComponent, paramString, paramFontMetrics
/*  400 */           .getFont(), paramFontMetrics.getFontRenderContext());
/*  401 */       return (int)textLayout.getAdvance();
/*      */     } 
/*  403 */     return paramFontMetrics.stringWidth(paramString);
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
/*      */   public static String clipStringIfNecessary(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString, int paramInt) {
/*  420 */     if (paramString == null || paramString.equals("")) {
/*  421 */       return "";
/*      */     }
/*  423 */     int i = stringWidth(paramJComponent, paramFontMetrics, paramString);
/*  424 */     if (i > paramInt) {
/*  425 */       return clipString(paramJComponent, paramFontMetrics, paramString, paramInt);
/*      */     }
/*  427 */     return paramString;
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
/*      */   public static String clipString(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString, int paramInt) {
/*      */     boolean bool;
/*  444 */     String str = "...";
/*  445 */     paramInt -= stringWidth(paramJComponent, paramFontMetrics, str);
/*  446 */     if (paramInt <= 0)
/*      */     {
/*  448 */       return str;
/*      */     }
/*      */ 
/*      */     
/*  452 */     synchronized (charsBufferLock) {
/*  453 */       int i = syncCharsBuffer(paramString);
/*      */       
/*  455 */       bool = isComplexLayout(charsBuffer, 0, i);
/*  456 */       if (!bool) {
/*  457 */         int j = 0;
/*  458 */         for (byte b = 0; b < i; b++) {
/*  459 */           j += paramFontMetrics.charWidth(charsBuffer[b]);
/*  460 */           if (j > paramInt) {
/*  461 */             paramString = paramString.substring(0, b);
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  467 */     if (bool) {
/*  468 */       AttributedString attributedString = new AttributedString(paramString);
/*  469 */       if (paramJComponent != null) {
/*  470 */         attributedString.addAttribute(TextAttribute.NUMERIC_SHAPING, paramJComponent
/*  471 */             .getClientProperty(TextAttribute.NUMERIC_SHAPING));
/*      */       }
/*      */ 
/*      */       
/*  475 */       LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(attributedString.getIterator(), BreakIterator.getCharacterInstance(), getFontRenderContext(paramJComponent, paramFontMetrics));
/*  476 */       paramString = paramString.substring(0, lineBreakMeasurer.nextOffset(paramInt));
/*      */     } 
/*      */     
/*  479 */     return paramString + str;
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
/*      */   public static void drawString(JComponent paramJComponent, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2) {
/*  499 */     if (paramString == null || paramString.length() <= 0) {
/*      */       return;
/*      */     }
/*  502 */     if (isPrinting(paramGraphics)) {
/*  503 */       Graphics2D graphics2D = getGraphics2D(paramGraphics);
/*  504 */       if (graphics2D != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  511 */         String str = trimTrailingSpaces(paramString);
/*  512 */         if (!str.isEmpty()) {
/*      */           
/*  514 */           float f = (float)graphics2D.getFont().getStringBounds(str, getFontRenderContext(paramJComponent)).getWidth();
/*  515 */           TextLayout textLayout = createTextLayout(paramJComponent, paramString, graphics2D.getFont(), graphics2D
/*  516 */               .getFontRenderContext());
/*      */ 
/*      */           
/*  519 */           if (stringWidth(paramJComponent, graphics2D.getFontMetrics(), str) > f)
/*      */           {
/*  521 */             textLayout = textLayout.getJustifiedLayout(f);
/*      */           }
/*      */           
/*  524 */           Color color = graphics2D.getColor();
/*  525 */           if (color instanceof PrintColorUIResource) {
/*  526 */             graphics2D.setColor(((PrintColorUIResource)color).getPrintColor());
/*      */           }
/*      */           
/*  529 */           textLayout.draw(graphics2D, paramInt1, paramInt2);
/*      */           
/*  531 */           graphics2D.setColor(color);
/*      */         } 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  539 */     if (paramGraphics instanceof Graphics2D) {
/*  540 */       AATextInfo aATextInfo = drawTextAntialiased(paramJComponent);
/*  541 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*      */ 
/*      */       
/*  544 */       boolean bool = (paramJComponent != null && paramJComponent.getClientProperty(TextAttribute.NUMERIC_SHAPING) != null);
/*      */       
/*  546 */       if (bool) {
/*  547 */         synchronized (charsBufferLock) {
/*  548 */           int i = syncCharsBuffer(paramString);
/*  549 */           bool = isComplexLayout(charsBuffer, 0, i);
/*      */         } 
/*      */       }
/*      */       
/*  553 */       if (aATextInfo != null) {
/*  554 */         Object object1 = null;
/*  555 */         Object object2 = graphics2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
/*  556 */         if (aATextInfo.aaHint != object2) {
/*  557 */           graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, aATextInfo.aaHint);
/*      */         } else {
/*  559 */           object2 = null;
/*      */         } 
/*  561 */         if (aATextInfo.lcdContrastHint != null) {
/*  562 */           object1 = graphics2D.getRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST);
/*  563 */           if (aATextInfo.lcdContrastHint.equals(object1)) {
/*  564 */             object1 = null;
/*      */           } else {
/*  566 */             graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, aATextInfo.lcdContrastHint);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  571 */         if (bool) {
/*  572 */           TextLayout textLayout = createTextLayout(paramJComponent, paramString, graphics2D.getFont(), graphics2D
/*  573 */               .getFontRenderContext());
/*  574 */           textLayout.draw(graphics2D, paramInt1, paramInt2);
/*      */         } else {
/*  576 */           paramGraphics.drawString(paramString, paramInt1, paramInt2);
/*      */         } 
/*      */         
/*  579 */         if (object2 != null) {
/*  580 */           graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, object2);
/*      */         }
/*  582 */         if (object1 != null) {
/*  583 */           graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, object1);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  589 */       if (bool) {
/*  590 */         TextLayout textLayout = createTextLayout(paramJComponent, paramString, graphics2D.getFont(), graphics2D
/*  591 */             .getFontRenderContext());
/*  592 */         textLayout.draw(graphics2D, paramInt1, paramInt2);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  597 */     paramGraphics.drawString(paramString, paramInt1, paramInt2);
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
/*      */   public static void drawStringUnderlineCharAt(JComponent paramJComponent, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  613 */     if (paramString == null || paramString.length() <= 0) {
/*      */       return;
/*      */     }
/*  616 */     drawString(paramJComponent, paramGraphics, paramString, paramInt2, paramInt3);
/*  617 */     int i = paramString.length();
/*  618 */     if (paramInt1 >= 0 && paramInt1 < i) {
/*  619 */       int j = paramInt3;
/*  620 */       boolean bool = true;
/*  621 */       int k = 0;
/*  622 */       int m = 0;
/*  623 */       boolean bool1 = isPrinting(paramGraphics);
/*  624 */       boolean bool2 = bool1;
/*  625 */       if (!bool2) {
/*  626 */         synchronized (charsBufferLock) {
/*  627 */           syncCharsBuffer(paramString);
/*      */           
/*  629 */           bool2 = isComplexLayout(charsBuffer, 0, i);
/*      */         } 
/*      */       }
/*  632 */       if (!bool2) {
/*  633 */         FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/*      */         
/*  635 */         k = paramInt2 + stringWidth(paramJComponent, fontMetrics, paramString
/*  636 */             .substring(0, paramInt1));
/*  637 */         m = fontMetrics.charWidth(paramString
/*  638 */             .charAt(paramInt1));
/*      */       } else {
/*  640 */         Graphics2D graphics2D = getGraphics2D(paramGraphics);
/*  641 */         if (graphics2D != null) {
/*      */           
/*  643 */           TextLayout textLayout = createTextLayout(paramJComponent, paramString, graphics2D.getFont(), graphics2D
/*  644 */               .getFontRenderContext());
/*  645 */           if (bool1) {
/*      */             
/*  647 */             float f = (float)graphics2D.getFont().getStringBounds(paramString, getFontRenderContext(paramJComponent)).getWidth();
/*      */             
/*  649 */             if (stringWidth(paramJComponent, graphics2D.getFontMetrics(), paramString) > f)
/*      */             {
/*  651 */               textLayout = textLayout.getJustifiedLayout(f);
/*      */             }
/*      */           } 
/*      */           
/*  655 */           TextHitInfo textHitInfo1 = TextHitInfo.leading(paramInt1);
/*      */           
/*  657 */           TextHitInfo textHitInfo2 = TextHitInfo.trailing(paramInt1);
/*      */           
/*  659 */           Shape shape = textLayout.getVisualHighlightShape(textHitInfo1, textHitInfo2);
/*  660 */           Rectangle rectangle = shape.getBounds();
/*  661 */           k = paramInt2 + rectangle.x;
/*  662 */           m = rectangle.width;
/*      */         } 
/*      */       } 
/*  665 */       paramGraphics.fillRect(k, j + 1, m, bool);
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
/*      */   public static int loc2IndexFileList(JList paramJList, Point paramPoint) {
/*  679 */     int i = paramJList.locationToIndex(paramPoint);
/*  680 */     if (i != -1) {
/*  681 */       Object object = paramJList.getClientProperty("List.isFileList");
/*  682 */       if (object instanceof Boolean && ((Boolean)object).booleanValue() && 
/*  683 */         !pointIsInActualBounds(paramJList, i, paramPoint)) {
/*  684 */         i = -1;
/*      */       }
/*      */     } 
/*  687 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean pointIsInActualBounds(JList paramJList, int paramInt, Point paramPoint) {
/*  697 */     ListCellRenderer listCellRenderer = paramJList.getCellRenderer();
/*  698 */     ListModel<Object> listModel = paramJList.getModel();
/*  699 */     Object object = listModel.getElementAt(paramInt);
/*  700 */     Component component = listCellRenderer.getListCellRendererComponent(paramJList, object, paramInt, false, false);
/*      */     
/*  702 */     Dimension dimension = component.getPreferredSize();
/*  703 */     Rectangle rectangle = paramJList.getCellBounds(paramInt, paramInt);
/*  704 */     if (!component.getComponentOrientation().isLeftToRight()) {
/*  705 */       rectangle.x += rectangle.width - dimension.width;
/*      */     }
/*  707 */     rectangle.width = dimension.width;
/*      */     
/*  709 */     return rectangle.contains(paramPoint);
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
/*      */   public static boolean pointOutsidePrefSize(JTable paramJTable, int paramInt1, int paramInt2, Point paramPoint) {
/*  721 */     if (paramJTable.convertColumnIndexToModel(paramInt2) != 0 || paramInt1 == -1) {
/*  722 */       return true;
/*      */     }
/*  724 */     TableCellRenderer tableCellRenderer = paramJTable.getCellRenderer(paramInt1, paramInt2);
/*  725 */     Object object = paramJTable.getValueAt(paramInt1, paramInt2);
/*  726 */     Component component = tableCellRenderer.getTableCellRendererComponent(paramJTable, object, false, false, paramInt1, paramInt2);
/*      */     
/*  728 */     Dimension dimension = component.getPreferredSize();
/*  729 */     Rectangle rectangle = paramJTable.getCellRect(paramInt1, paramInt2, false);
/*  730 */     rectangle.width = dimension.width;
/*  731 */     rectangle.height = dimension.height;
/*      */ 
/*      */ 
/*      */     
/*  735 */     assert paramPoint.x >= rectangle.x && paramPoint.y >= rectangle.y;
/*  736 */     return (paramPoint.x > rectangle.x + rectangle.width || paramPoint.y > rectangle.y + rectangle.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setLeadAnchorWithoutSelection(ListSelectionModel paramListSelectionModel, int paramInt1, int paramInt2) {
/*  745 */     if (paramInt2 == -1) {
/*  746 */       paramInt2 = paramInt1;
/*      */     }
/*  748 */     if (paramInt1 == -1) {
/*  749 */       paramListSelectionModel.setAnchorSelectionIndex(-1);
/*  750 */       paramListSelectionModel.setLeadSelectionIndex(-1);
/*      */     } else {
/*  752 */       if (paramListSelectionModel.isSelectedIndex(paramInt1)) {
/*  753 */         paramListSelectionModel.addSelectionInterval(paramInt1, paramInt1);
/*      */       } else {
/*  755 */         paramListSelectionModel.removeSelectionInterval(paramInt1, paramInt1);
/*      */       } 
/*  757 */       paramListSelectionModel.setAnchorSelectionIndex(paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean shouldIgnore(MouseEvent paramMouseEvent, JComponent paramJComponent) {
/*  767 */     return (paramJComponent == null || !paramJComponent.isEnabled() || 
/*  768 */       !SwingUtilities.isLeftMouseButton(paramMouseEvent) || paramMouseEvent
/*  769 */       .isConsumed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void adjustFocus(JComponent paramJComponent) {
/*  777 */     if (!paramJComponent.hasFocus() && paramJComponent.isRequestFocusEnabled()) {
/*  778 */       paramJComponent.requestFocus();
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
/*      */   public static int drawChars(JComponent paramJComponent, Graphics paramGraphics, char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  794 */     if (paramInt2 <= 0) {
/*  795 */       return paramInt3;
/*      */     }
/*  797 */     int i = paramInt3 + getFontMetrics(paramJComponent, paramGraphics).charsWidth(paramArrayOfchar, paramInt1, paramInt2);
/*  798 */     if (isPrinting(paramGraphics)) {
/*  799 */       Graphics2D graphics2D = getGraphics2D(paramGraphics);
/*  800 */       if (graphics2D != null) {
/*      */         
/*  802 */         FontRenderContext fontRenderContext1 = graphics2D.getFontRenderContext();
/*  803 */         FontRenderContext fontRenderContext2 = getFontRenderContext(paramJComponent);
/*  804 */         if (fontRenderContext2 != null && 
/*      */           
/*  806 */           !isFontRenderContextPrintCompatible(fontRenderContext1, fontRenderContext2)) {
/*      */           
/*  808 */           String str1 = new String(paramArrayOfchar, paramInt1, paramInt2);
/*  809 */           TextLayout textLayout = new TextLayout(str1, graphics2D.getFont(), fontRenderContext1);
/*      */           
/*  811 */           String str2 = trimTrailingSpaces(str1);
/*  812 */           if (!str2.isEmpty()) {
/*      */             
/*  814 */             float f = (float)graphics2D.getFont().getStringBounds(str2, fontRenderContext2).getWidth();
/*      */             
/*  816 */             if (stringWidth(paramJComponent, graphics2D.getFontMetrics(), str2) > f)
/*      */             {
/*  818 */               textLayout = textLayout.getJustifiedLayout(f);
/*      */             }
/*      */ 
/*      */             
/*  822 */             Color color = graphics2D.getColor();
/*  823 */             if (color instanceof PrintColorUIResource) {
/*  824 */               graphics2D.setColor(((PrintColorUIResource)color).getPrintColor());
/*      */             }
/*      */             
/*  827 */             textLayout.draw(graphics2D, paramInt3, paramInt4);
/*      */             
/*  829 */             graphics2D.setColor(color);
/*      */           } 
/*      */           
/*  832 */           return i;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  838 */     AATextInfo aATextInfo = drawTextAntialiased(paramJComponent);
/*  839 */     if (aATextInfo != null && paramGraphics instanceof Graphics2D) {
/*  840 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*      */       
/*  842 */       Object object1 = null;
/*  843 */       Object object2 = graphics2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
/*  844 */       if (aATextInfo.aaHint != null && aATextInfo.aaHint != object2) {
/*  845 */         graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, aATextInfo.aaHint);
/*      */       } else {
/*  847 */         object2 = null;
/*      */       } 
/*  849 */       if (aATextInfo.lcdContrastHint != null) {
/*  850 */         object1 = graphics2D.getRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST);
/*  851 */         if (aATextInfo.lcdContrastHint.equals(object1)) {
/*  852 */           object1 = null;
/*      */         } else {
/*  854 */           graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, aATextInfo.lcdContrastHint);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  859 */       paramGraphics.drawChars(paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       
/*  861 */       if (object2 != null) {
/*  862 */         graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, object2);
/*      */       }
/*  864 */       if (object1 != null) {
/*  865 */         graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, object1);
/*      */       }
/*      */     } else {
/*      */       
/*  869 */       paramGraphics.drawChars(paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } 
/*  871 */     return i;
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
/*      */   public static float drawString(JComponent paramJComponent, Graphics paramGraphics, AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2) {
/*      */     float f;
/*  884 */     boolean bool = isPrinting(paramGraphics);
/*  885 */     Color color = paramGraphics.getColor();
/*      */     
/*  887 */     if (bool)
/*      */     {
/*  889 */       if (color instanceof PrintColorUIResource) {
/*  890 */         paramGraphics.setColor(((PrintColorUIResource)color).getPrintColor());
/*      */       }
/*      */     }
/*      */     
/*  894 */     Graphics2D graphics2D = getGraphics2D(paramGraphics);
/*  895 */     if (graphics2D == null) {
/*  896 */       paramGraphics.drawString(paramAttributedCharacterIterator, paramInt1, paramInt2);
/*      */       
/*  898 */       f = paramInt1;
/*      */     } else {
/*      */       FontRenderContext fontRenderContext;
/*      */       TextLayout textLayout;
/*  902 */       if (bool) {
/*  903 */         fontRenderContext = getFontRenderContext(paramJComponent);
/*  904 */         if (fontRenderContext.isAntiAliased() || fontRenderContext.usesFractionalMetrics()) {
/*  905 */           fontRenderContext = new FontRenderContext(fontRenderContext.getTransform(), false, false);
/*      */         }
/*  907 */       } else if ((fontRenderContext = getFRCProperty(paramJComponent)) == null) {
/*      */ 
/*      */         
/*  910 */         fontRenderContext = graphics2D.getFontRenderContext();
/*      */       } 
/*      */       
/*  913 */       if (bool) {
/*  914 */         FontRenderContext fontRenderContext1 = graphics2D.getFontRenderContext();
/*  915 */         if (!isFontRenderContextPrintCompatible(fontRenderContext, fontRenderContext1)) {
/*  916 */           textLayout = new TextLayout(paramAttributedCharacterIterator, fontRenderContext1);
/*      */           
/*  918 */           AttributedCharacterIterator attributedCharacterIterator = getTrimmedTrailingSpacesIterator(paramAttributedCharacterIterator);
/*  919 */           if (attributedCharacterIterator != null) {
/*      */             
/*  921 */             float f1 = (new TextLayout(attributedCharacterIterator, fontRenderContext)).getAdvance();
/*  922 */             textLayout = textLayout.getJustifiedLayout(f1);
/*      */           } 
/*      */         } else {
/*  925 */           textLayout = new TextLayout(paramAttributedCharacterIterator, fontRenderContext);
/*      */         } 
/*      */       } else {
/*  928 */         textLayout = new TextLayout(paramAttributedCharacterIterator, fontRenderContext);
/*      */       } 
/*  930 */       textLayout.draw(graphics2D, paramInt1, paramInt2);
/*  931 */       f = textLayout.getAdvance();
/*      */     } 
/*      */     
/*  934 */     if (bool) {
/*  935 */       paramGraphics.setColor(color);
/*      */     }
/*      */     
/*  938 */     return f;
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
/*      */   public static void drawVLine(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3) {
/*  953 */     if (paramInt3 < paramInt2) {
/*  954 */       int i = paramInt3;
/*  955 */       paramInt3 = paramInt2;
/*  956 */       paramInt2 = i;
/*      */     } 
/*  958 */     paramGraphics.fillRect(paramInt1, paramInt2, 1, paramInt3 - paramInt2 + 1);
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
/*      */   public static void drawHLine(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3) {
/*  973 */     if (paramInt2 < paramInt1) {
/*  974 */       int i = paramInt2;
/*  975 */       paramInt2 = paramInt1;
/*  976 */       paramInt1 = i;
/*      */     } 
/*  978 */     paramGraphics.fillRect(paramInt1, paramInt3, paramInt2 - paramInt1 + 1, 1);
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
/*      */   public static void drawRect(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  998 */     if (paramInt3 < 0 || paramInt4 < 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1002 */     if (paramInt4 == 0 || paramInt3 == 0) {
/* 1003 */       paramGraphics.fillRect(paramInt1, paramInt2, paramInt3 + 1, paramInt4 + 1);
/*      */     } else {
/* 1005 */       paramGraphics.fillRect(paramInt1, paramInt2, paramInt3, 1);
/* 1006 */       paramGraphics.fillRect(paramInt1 + paramInt3, paramInt2, 1, paramInt4);
/* 1007 */       paramGraphics.fillRect(paramInt1 + 1, paramInt2 + paramInt4, paramInt3, 1);
/* 1008 */       paramGraphics.fillRect(paramInt1, paramInt2 + 1, 1, paramInt4);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextLayout createTextLayout(JComponent paramJComponent, String paramString, Font paramFont, FontRenderContext paramFontRenderContext) {
/* 1015 */     Object object = (paramJComponent == null) ? null : paramJComponent.getClientProperty(TextAttribute.NUMERIC_SHAPING);
/* 1016 */     if (object == null) {
/* 1017 */       return new TextLayout(paramString, paramFont, paramFontRenderContext);
/*      */     }
/* 1019 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 1020 */     hashMap.put(TextAttribute.FONT, paramFont);
/* 1021 */     hashMap.put(TextAttribute.NUMERIC_SHAPING, object);
/* 1022 */     return new TextLayout(paramString, (Map)hashMap, paramFontRenderContext);
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
/*      */   private static boolean isFontRenderContextPrintCompatible(FontRenderContext paramFontRenderContext1, FontRenderContext paramFontRenderContext2) {
/* 1039 */     if (paramFontRenderContext1 == paramFontRenderContext2) {
/* 1040 */       return true;
/*      */     }
/*      */     
/* 1043 */     if (paramFontRenderContext1 == null || paramFontRenderContext2 == null) {
/* 1044 */       return false;
/*      */     }
/*      */     
/* 1047 */     if (paramFontRenderContext1.getFractionalMetricsHint() != paramFontRenderContext2
/* 1048 */       .getFractionalMetricsHint()) {
/* 1049 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1053 */     if (!paramFontRenderContext1.isTransformed() && !paramFontRenderContext2.isTransformed()) {
/* 1054 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1061 */     double[] arrayOfDouble1 = new double[4];
/* 1062 */     double[] arrayOfDouble2 = new double[4];
/* 1063 */     paramFontRenderContext1.getTransform().getMatrix(arrayOfDouble1);
/* 1064 */     paramFontRenderContext2.getTransform().getMatrix(arrayOfDouble2);
/* 1065 */     return (arrayOfDouble1[0] == arrayOfDouble2[0] && arrayOfDouble1[1] == arrayOfDouble2[1] && arrayOfDouble1[2] == arrayOfDouble2[2] && arrayOfDouble1[3] == arrayOfDouble2[3]);
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
/*      */   public static Graphics2D getGraphics2D(Graphics paramGraphics) {
/* 1077 */     if (paramGraphics instanceof Graphics2D)
/* 1078 */       return (Graphics2D)paramGraphics; 
/* 1079 */     if (paramGraphics instanceof ProxyPrintGraphics) {
/* 1080 */       return (Graphics2D)((ProxyPrintGraphics)paramGraphics).getGraphics();
/*      */     }
/* 1082 */     return null;
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
/*      */   public static FontRenderContext getFontRenderContext(Component paramComponent) {
/* 1095 */     assert paramComponent != null;
/* 1096 */     if (paramComponent == null) {
/* 1097 */       return DEFAULT_FRC;
/*      */     }
/* 1099 */     return paramComponent.getFontMetrics(paramComponent.getFont()).getFontRenderContext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FontRenderContext getFontRenderContext(Component paramComponent, FontMetrics paramFontMetrics) {
/* 1109 */     assert paramFontMetrics != null || paramComponent != null;
/* 1110 */     return (paramFontMetrics != null) ? paramFontMetrics.getFontRenderContext() : 
/* 1111 */       getFontRenderContext(paramComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FontMetrics getFontMetrics(JComponent paramJComponent, Font paramFont) {
/* 1121 */     FontRenderContext fontRenderContext = getFRCProperty(paramJComponent);
/* 1122 */     if (fontRenderContext == null) {
/* 1123 */       fontRenderContext = DEFAULT_FRC;
/*      */     }
/* 1125 */     return FontDesignMetrics.getMetrics(paramFont, fontRenderContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static FontRenderContext getFRCProperty(JComponent paramJComponent) {
/* 1133 */     if (paramJComponent != null) {
/*      */       
/* 1135 */       AATextInfo aATextInfo = (AATextInfo)paramJComponent.getClientProperty(AA_TEXT_PROPERTY_KEY);
/* 1136 */       if (aATextInfo != null) {
/* 1137 */         return aATextInfo.frc;
/*      */       }
/*      */     } 
/* 1140 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isPrinting(Graphics paramGraphics) {
/* 1148 */     return (paramGraphics instanceof java.awt.print.PrinterGraphics || paramGraphics instanceof java.awt.PrintGraphics);
/*      */   }
/*      */   
/*      */   private static String trimTrailingSpaces(String paramString) {
/* 1152 */     int i = paramString.length() - 1;
/* 1153 */     while (i >= 0 && Character.isWhitespace(paramString.charAt(i))) {
/* 1154 */       i--;
/*      */     }
/* 1156 */     return paramString.substring(0, i + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   private static AttributedCharacterIterator getTrimmedTrailingSpacesIterator(AttributedCharacterIterator paramAttributedCharacterIterator) {
/* 1161 */     int i = paramAttributedCharacterIterator.getIndex();
/*      */     
/* 1163 */     char c = paramAttributedCharacterIterator.last();
/* 1164 */     while (c != Character.MAX_VALUE && Character.isWhitespace(c)) {
/* 1165 */       c = paramAttributedCharacterIterator.previous();
/*      */     }
/*      */     
/* 1168 */     if (c != Character.MAX_VALUE) {
/* 1169 */       int j = paramAttributedCharacterIterator.getIndex();
/*      */       
/* 1171 */       if (j == paramAttributedCharacterIterator.getEndIndex() - 1) {
/* 1172 */         paramAttributedCharacterIterator.setIndex(i);
/* 1173 */         return paramAttributedCharacterIterator;
/*      */       } 
/*      */       
/* 1176 */       AttributedString attributedString = new AttributedString(paramAttributedCharacterIterator, paramAttributedCharacterIterator.getBeginIndex(), j + 1);
/* 1177 */       return attributedString.getIterator();
/*      */     } 
/*      */     
/* 1180 */     return null;
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
/*      */   public static boolean useSelectedTextColor(Highlighter.Highlight paramHighlight, JTextComponent paramJTextComponent) {
/* 1196 */     Highlighter.HighlightPainter highlightPainter = paramHighlight.getPainter();
/* 1197 */     String str = highlightPainter.getClass().getName();
/* 1198 */     if (str.indexOf("javax.swing.text.DefaultHighlighter") != 0 && str
/* 1199 */       .indexOf("com.sun.java.swing.plaf.windows.WindowsTextUI") != 0) {
/* 1200 */       return false;
/*      */     }
/*      */     try {
/* 1203 */       DefaultHighlighter.DefaultHighlightPainter defaultHighlightPainter = (DefaultHighlighter.DefaultHighlightPainter)highlightPainter;
/*      */       
/* 1205 */       if (defaultHighlightPainter.getColor() != null && 
/* 1206 */         !defaultHighlightPainter.getColor().equals(paramJTextComponent.getSelectionColor())) {
/* 1207 */         return false;
/*      */       }
/* 1209 */     } catch (ClassCastException classCastException) {
/* 1210 */       return false;
/*      */     } 
/* 1212 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class LSBCacheEntry
/*      */   {
/*      */     private static final byte UNSET = 127;
/*      */ 
/*      */ 
/*      */     
/* 1225 */     private static final char[] oneChar = new char[1];
/*      */     
/*      */     private byte[] lsbCache;
/*      */     
/*      */     private Font font;
/*      */     private FontRenderContext frc;
/*      */     
/*      */     public LSBCacheEntry(FontRenderContext param1FontRenderContext, Font param1Font) {
/* 1233 */       this.lsbCache = new byte[1];
/* 1234 */       reset(param1FontRenderContext, param1Font);
/*      */     }
/*      */ 
/*      */     
/*      */     public void reset(FontRenderContext param1FontRenderContext, Font param1Font) {
/* 1239 */       this.font = param1Font;
/* 1240 */       this.frc = param1FontRenderContext;
/* 1241 */       for (int i = this.lsbCache.length - 1; i >= 0; i--) {
/* 1242 */         this.lsbCache[i] = Byte.MAX_VALUE;
/*      */       }
/*      */     }
/*      */     
/*      */     public int getLeftSideBearing(char param1Char) {
/* 1247 */       int i = param1Char - 87;
/* 1248 */       assert i >= 0 && i < 1;
/* 1249 */       byte b = this.lsbCache[i];
/* 1250 */       if (b == Byte.MAX_VALUE) {
/* 1251 */         oneChar[0] = param1Char;
/* 1252 */         GlyphVector glyphVector = this.font.createGlyphVector(this.frc, oneChar);
/* 1253 */         b = (byte)(glyphVector.getGlyphPixelBounds(0, this.frc, 0.0F, 0.0F)).x;
/* 1254 */         if (b < 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1261 */           Object object = this.frc.getAntiAliasingHint();
/* 1262 */           if (object == RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB || object == RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR)
/*      */           {
/* 1264 */             b = (byte)(b + 1);
/*      */           }
/*      */         } 
/* 1267 */         this.lsbCache[i] = b;
/*      */       } 
/* 1269 */       return b;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1275 */       if (param1Object == this) {
/* 1276 */         return true;
/*      */       }
/* 1278 */       if (!(param1Object instanceof LSBCacheEntry)) {
/* 1279 */         return false;
/*      */       }
/* 1281 */       LSBCacheEntry lSBCacheEntry = (LSBCacheEntry)param1Object;
/* 1282 */       return (this.font.equals(lSBCacheEntry.font) && this.frc
/* 1283 */         .equals(lSBCacheEntry.frc));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1287 */       int i = 17;
/* 1288 */       if (this.font != null) {
/* 1289 */         i = 37 * i + this.font.hashCode();
/*      */       }
/* 1291 */       if (this.frc != null) {
/* 1292 */         i = 37 * i + this.frc.hashCode();
/*      */       }
/* 1294 */       return i;
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
/*      */   public static boolean canAccessSystemClipboard() {
/* 1314 */     boolean bool = false;
/* 1315 */     if (!GraphicsEnvironment.isHeadless()) {
/* 1316 */       SecurityManager securityManager = System.getSecurityManager();
/* 1317 */       if (securityManager == null) {
/* 1318 */         bool = true;
/*      */       } else {
/*      */         try {
/* 1321 */           securityManager.checkPermission(SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION);
/* 1322 */           bool = true;
/* 1323 */         } catch (SecurityException securityException) {}
/*      */         
/* 1325 */         if (bool && !isTrustedContext()) {
/* 1326 */           bool = canCurrentEventAccessSystemClipboard(true);
/*      */         }
/*      */       } 
/*      */     } 
/* 1330 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean canCurrentEventAccessSystemClipboard() {
/* 1337 */     return (isTrustedContext() || 
/* 1338 */       canCurrentEventAccessSystemClipboard(false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean canEventAccessSystemClipboard(AWTEvent paramAWTEvent) {
/* 1348 */     return (isTrustedContext() || 
/* 1349 */       canEventAccessSystemClipboard(paramAWTEvent, false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized boolean inputEvent_canAccessSystemClipboard(InputEvent paramInputEvent) {
/* 1358 */     if (inputEvent_CanAccessSystemClipboard_Field == null)
/*      */     {
/* 1360 */       inputEvent_CanAccessSystemClipboard_Field = AccessController.<Field>doPrivileged(new PrivilegedAction<Field>()
/*      */           {
/*      */             public Field run()
/*      */             {
/*      */               
/* 1365 */               try { Field field = InputEvent.class.getDeclaredField("canAccessSystemClipboard");
/* 1366 */                 field.setAccessible(true);
/* 1367 */                 return field; }
/* 1368 */               catch (SecurityException securityException) {  }
/* 1369 */               catch (NoSuchFieldException noSuchFieldException) {}
/*      */               
/* 1371 */               return null;
/*      */             }
/*      */           });
/*      */     }
/* 1375 */     if (inputEvent_CanAccessSystemClipboard_Field == null) {
/* 1376 */       return false;
/*      */     }
/* 1378 */     boolean bool = false;
/*      */     
/*      */     try {
/* 1381 */       bool = inputEvent_CanAccessSystemClipboard_Field.getBoolean(paramInputEvent);
/* 1382 */     } catch (IllegalAccessException illegalAccessException) {}
/*      */     
/* 1384 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAccessClipboardGesture(InputEvent paramInputEvent) {
/* 1395 */     boolean bool = false;
/* 1396 */     if (paramInputEvent instanceof KeyEvent) {
/* 1397 */       KeyEvent keyEvent = (KeyEvent)paramInputEvent;
/* 1398 */       int i = keyEvent.getKeyCode();
/* 1399 */       int j = keyEvent.getModifiers();
/* 1400 */       switch (i) {
/*      */         case 67:
/*      */         case 86:
/*      */         case 88:
/* 1404 */           bool = (j == 2) ? true : false;
/*      */           break;
/*      */         case 155:
/* 1407 */           bool = (j == 2 || j == 1) ? true : false;
/*      */           break;
/*      */         
/*      */         case 65485:
/*      */         case 65487:
/*      */         case 65489:
/* 1413 */           bool = true;
/*      */           break;
/*      */         case 127:
/* 1416 */           bool = (j == 1) ? true : false;
/*      */           break;
/*      */       } 
/*      */     } 
/* 1420 */     return bool;
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
/*      */   private static boolean canEventAccessSystemClipboard(AWTEvent paramAWTEvent, boolean paramBoolean) {
/* 1433 */     if (EventQueue.isDispatchThread()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1438 */       if (paramAWTEvent instanceof InputEvent && (!paramBoolean || 
/* 1439 */         isAccessClipboardGesture((InputEvent)paramAWTEvent))) {
/* 1440 */         return inputEvent_canAccessSystemClipboard((InputEvent)paramAWTEvent);
/*      */       }
/* 1442 */       return false;
/*      */     } 
/*      */     
/* 1445 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkAccess(int paramInt) {
/* 1456 */     if (System.getSecurityManager() != null && 
/* 1457 */       !Modifier.isPublic(paramInt)) {
/* 1458 */       throw new SecurityException("Resource is not accessible");
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
/*      */   private static boolean canCurrentEventAccessSystemClipboard(boolean paramBoolean) {
/* 1471 */     AWTEvent aWTEvent = EventQueue.getCurrentEvent();
/* 1472 */     return canEventAccessSystemClipboard(aWTEvent, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isTrustedContext() {
/* 1481 */     return (System.getSecurityManager() == null || 
/* 1482 */       AppContext.getAppContext()
/* 1483 */       .get("UNTRUSTED_CLIPBOARD_ACCESS_KEY") == null);
/*      */   }
/*      */   
/*      */   public static String displayPropertiesToCSS(Font paramFont, Color paramColor) {
/* 1487 */     StringBuffer stringBuffer = new StringBuffer("body {");
/* 1488 */     if (paramFont != null) {
/* 1489 */       stringBuffer.append(" font-family: ");
/* 1490 */       stringBuffer.append(paramFont.getFamily());
/* 1491 */       stringBuffer.append(" ; ");
/* 1492 */       stringBuffer.append(" font-size: ");
/* 1493 */       stringBuffer.append(paramFont.getSize());
/* 1494 */       stringBuffer.append("pt ;");
/* 1495 */       if (paramFont.isBold()) {
/* 1496 */         stringBuffer.append(" font-weight: 700 ; ");
/*      */       }
/* 1498 */       if (paramFont.isItalic()) {
/* 1499 */         stringBuffer.append(" font-style: italic ; ");
/*      */       }
/*      */     } 
/* 1502 */     if (paramColor != null) {
/* 1503 */       stringBuffer.append(" color: #");
/* 1504 */       if (paramColor.getRed() < 16) {
/* 1505 */         stringBuffer.append('0');
/*      */       }
/* 1507 */       stringBuffer.append(Integer.toHexString(paramColor.getRed()));
/* 1508 */       if (paramColor.getGreen() < 16) {
/* 1509 */         stringBuffer.append('0');
/*      */       }
/* 1511 */       stringBuffer.append(Integer.toHexString(paramColor.getGreen()));
/* 1512 */       if (paramColor.getBlue() < 16) {
/* 1513 */         stringBuffer.append('0');
/*      */       }
/* 1515 */       stringBuffer.append(Integer.toHexString(paramColor.getBlue()));
/* 1516 */       stringBuffer.append(" ; ");
/*      */     } 
/* 1518 */     stringBuffer.append(" }");
/* 1519 */     return stringBuffer.toString();
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
/*      */   public static Object makeIcon(final Class<?> baseClass, final Class<?> rootClass, final String imageFile) {
/* 1543 */     return new UIDefaults.LazyValue()
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public Object createValue(UIDefaults param1UIDefaults)
/*      */         {
/* 1553 */           byte[] arrayOfByte = AccessController.<byte[]>doPrivileged((PrivilegedAction)new PrivilegedAction<byte[]>()
/*      */               {
/*      */                 public byte[] run() {
/*      */                   try {
/* 1557 */                     InputStream inputStream = null;
/* 1558 */                     Class clazz = baseClass;
/*      */                     
/* 1560 */                     while (clazz != null) {
/* 1561 */                       inputStream = clazz.getResourceAsStream(imageFile);
/*      */                       
/* 1563 */                       if (inputStream != null || clazz == rootClass) {
/*      */                         break;
/*      */                       }
/*      */                       
/* 1567 */                       clazz = clazz.getSuperclass();
/*      */                     } 
/*      */                     
/* 1570 */                     if (inputStream == null) {
/* 1571 */                       return null;
/*      */                     }
/*      */                     
/* 1574 */                     BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
/*      */                     
/* 1576 */                     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
/*      */                     
/* 1578 */                     byte[] arrayOfByte = new byte[1024];
/*      */                     int i;
/* 1580 */                     while ((i = bufferedInputStream.read(arrayOfByte)) > 0) {
/* 1581 */                       byteArrayOutputStream.write(arrayOfByte, 0, i);
/*      */                     }
/* 1583 */                     bufferedInputStream.close();
/* 1584 */                     byteArrayOutputStream.flush();
/* 1585 */                     return byteArrayOutputStream.toByteArray();
/* 1586 */                   } catch (IOException iOException) {
/* 1587 */                     System.err.println(iOException.toString());
/*      */                     
/* 1589 */                     return null;
/*      */                   } 
/*      */                 }
/*      */               });
/* 1593 */           if (arrayOfByte == null) {
/* 1594 */             return null;
/*      */           }
/* 1596 */           if (arrayOfByte.length == 0) {
/* 1597 */             System.err.println("warning: " + imageFile + " is zero-length");
/*      */             
/* 1599 */             return null;
/*      */           } 
/*      */           
/* 1602 */           return new ImageIconUIResource(arrayOfByte);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLocalDisplay() {
/*      */     boolean bool;
/* 1616 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 1617 */     if (graphicsEnvironment instanceof SunGraphicsEnvironment) {
/* 1618 */       bool = ((SunGraphicsEnvironment)graphicsEnvironment).isDisplayLocal();
/*      */     } else {
/* 1620 */       bool = true;
/*      */     } 
/* 1622 */     return bool;
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
/*      */   public static int getUIDefaultsInt(Object paramObject) {
/* 1634 */     return getUIDefaultsInt(paramObject, 0);
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
/*      */   public static int getUIDefaultsInt(Object paramObject, Locale paramLocale) {
/* 1649 */     return getUIDefaultsInt(paramObject, paramLocale, 0);
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
/*      */   public static int getUIDefaultsInt(Object paramObject, int paramInt) {
/* 1665 */     return getUIDefaultsInt(paramObject, null, paramInt);
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
/*      */   public static int getUIDefaultsInt(Object paramObject, Locale paramLocale, int paramInt) {
/* 1682 */     Object object = UIManager.get(paramObject, paramLocale);
/*      */     
/* 1684 */     if (object instanceof Integer) {
/* 1685 */       return ((Integer)object).intValue();
/*      */     }
/* 1687 */     if (object instanceof String) {
/*      */       try {
/* 1689 */         return Integer.parseInt((String)object);
/* 1690 */       } catch (NumberFormatException numberFormatException) {}
/*      */     }
/* 1692 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Component compositeRequestFocus(Component paramComponent) {
/* 1698 */     if (paramComponent instanceof Container) {
/* 1699 */       Container container1 = (Container)paramComponent;
/* 1700 */       if (container1.isFocusCycleRoot()) {
/* 1701 */         FocusTraversalPolicy focusTraversalPolicy = container1.getFocusTraversalPolicy();
/* 1702 */         Component component = focusTraversalPolicy.getDefaultComponent(container1);
/* 1703 */         if (component != null) {
/* 1704 */           component.requestFocus();
/* 1705 */           return component;
/*      */         } 
/*      */       } 
/* 1708 */       Container container2 = container1.getFocusCycleRootAncestor();
/* 1709 */       if (container2 != null) {
/* 1710 */         FocusTraversalPolicy focusTraversalPolicy = container2.getFocusTraversalPolicy();
/* 1711 */         Component component = focusTraversalPolicy.getComponentAfter(container2, container1);
/*      */         
/* 1713 */         if (component != null && SwingUtilities.isDescendingFrom(component, container1)) {
/* 1714 */           component.requestFocus();
/* 1715 */           return component;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1719 */     if (paramComponent.isFocusable()) {
/* 1720 */       paramComponent.requestFocus();
/* 1721 */       return paramComponent;
/*      */     } 
/* 1723 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean tabbedPaneChangeFocusTo(Component paramComponent) {
/* 1732 */     if (paramComponent != null) {
/* 1733 */       if (paramComponent.isFocusTraversable()) {
/* 1734 */         compositeRequestFocus(paramComponent);
/* 1735 */         return true;
/* 1736 */       }  if (paramComponent instanceof JComponent && ((JComponent)paramComponent)
/* 1737 */         .requestDefaultFocus())
/*      */       {
/* 1739 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1743 */     return false;
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
/*      */   public static <V> Future<V> submit(Callable<V> paramCallable) {
/* 1755 */     if (paramCallable == null) {
/* 1756 */       throw new NullPointerException();
/*      */     }
/* 1758 */     FutureTask<V> futureTask = new FutureTask<>(paramCallable);
/* 1759 */     execute(futureTask);
/* 1760 */     return futureTask;
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
/*      */   public static <V> Future<V> submit(Runnable paramRunnable, V paramV) {
/* 1775 */     if (paramRunnable == null) {
/* 1776 */       throw new NullPointerException();
/*      */     }
/* 1778 */     FutureTask<V> futureTask = new FutureTask<>(paramRunnable, paramV);
/* 1779 */     execute(futureTask);
/* 1780 */     return futureTask;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void execute(Runnable paramRunnable) {
/* 1787 */     SwingUtilities.invokeLater(paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setSkipClickCount(Component paramComponent, int paramInt) {
/* 1798 */     if (paramComponent instanceof JTextComponent && ((JTextComponent)paramComponent)
/* 1799 */       .getCaret() instanceof javax.swing.text.DefaultCaret)
/*      */     {
/* 1801 */       ((JTextComponent)paramComponent).putClientProperty(SKIP_CLICK_COUNT, Integer.valueOf(paramInt));
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
/*      */   public static int getAdjustedClickCount(JTextComponent paramJTextComponent, MouseEvent paramMouseEvent) {
/* 1814 */     int i = paramMouseEvent.getClickCount();
/*      */     
/* 1816 */     if (i == 1) {
/* 1817 */       paramJTextComponent.putClientProperty(SKIP_CLICK_COUNT, (Object)null);
/*      */     } else {
/* 1819 */       Integer integer = (Integer)paramJTextComponent.getClientProperty(SKIP_CLICK_COUNT);
/* 1820 */       if (integer != null) {
/* 1821 */         return i - integer.intValue();
/*      */       }
/*      */     } 
/*      */     
/* 1825 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Section
/*      */   {
/* 1837 */     LEADING,
/*      */ 
/*      */     
/* 1840 */     MIDDLE,
/*      */ 
/*      */     
/* 1843 */     TRAILING;
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
/*      */   private static Section liesIn(Rectangle paramRectangle, Point paramPoint, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/*      */     int i, j, k;
/*      */     boolean bool;
/* 1901 */     if (paramBoolean1) {
/* 1902 */       i = paramRectangle.x;
/* 1903 */       j = paramPoint.x;
/* 1904 */       k = paramRectangle.width;
/* 1905 */       bool = paramBoolean2;
/*      */     } else {
/* 1907 */       i = paramRectangle.y;
/* 1908 */       j = paramPoint.y;
/* 1909 */       k = paramRectangle.height;
/* 1910 */       bool = true;
/*      */     } 
/*      */     
/* 1913 */     if (paramBoolean3) {
/* 1914 */       byte b = (k >= 30) ? 10 : (k / 3);
/*      */       
/* 1916 */       if (j < i + b)
/* 1917 */         return bool ? Section.LEADING : Section.TRAILING; 
/* 1918 */       if (j >= i + k - b) {
/* 1919 */         return bool ? Section.TRAILING : Section.LEADING;
/*      */       }
/*      */       
/* 1922 */       return Section.MIDDLE;
/*      */     } 
/* 1924 */     int m = i + k / 2;
/* 1925 */     if (bool) {
/* 1926 */       return (j >= m) ? Section.TRAILING : Section.LEADING;
/*      */     }
/* 1928 */     return (j < m) ? Section.TRAILING : Section.LEADING;
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
/*      */   public static Section liesInHorizontal(Rectangle paramRectangle, Point paramPoint, boolean paramBoolean1, boolean paramBoolean2) {
/* 1955 */     return liesIn(paramRectangle, paramPoint, true, paramBoolean1, paramBoolean2);
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
/*      */   public static Section liesInVertical(Rectangle paramRectangle, Point paramPoint, boolean paramBoolean) {
/* 1978 */     return liesIn(paramRectangle, paramPoint, false, false, paramBoolean);
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
/*      */   public static int convertColumnIndexToModel(TableColumnModel paramTableColumnModel, int paramInt) {
/* 1997 */     if (paramInt < 0) {
/* 1998 */       return paramInt;
/*      */     }
/* 2000 */     return paramTableColumnModel.getColumn(paramInt).getModelIndex();
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
/*      */   public static int convertColumnIndexToView(TableColumnModel paramTableColumnModel, int paramInt) {
/* 2020 */     if (paramInt < 0) {
/* 2021 */       return paramInt;
/*      */     }
/* 2023 */     for (byte b = 0; b < paramTableColumnModel.getColumnCount(); b++) {
/* 2024 */       if (paramTableColumnModel.getColumn(b).getModelIndex() == paramInt) {
/* 2025 */         return b;
/*      */       }
/*      */     } 
/* 2028 */     return -1;
/*      */   }
/*      */   
/*      */   public static int getSystemMnemonicKeyMask() {
/* 2032 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 2033 */     if (toolkit instanceof SunToolkit) {
/* 2034 */       return ((SunToolkit)toolkit).getFocusAcceleratorKeyMask();
/*      */     }
/* 2036 */     return 8;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TreePath getTreePath(TreeModelEvent paramTreeModelEvent, TreeModel paramTreeModel) {
/* 2047 */     TreePath treePath = paramTreeModelEvent.getTreePath();
/* 2048 */     if (treePath == null && paramTreeModel != null) {
/* 2049 */       Object object = paramTreeModel.getRoot();
/* 2050 */       if (object != null) {
/* 2051 */         treePath = new TreePath(object);
/*      */       }
/*      */     } 
/* 2054 */     return treePath;
/*      */   }
/*      */   
/*      */   public static interface RepaintListener {
/*      */     void repaintPerformed(JComponent param1JComponent, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/SwingUtilities2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */