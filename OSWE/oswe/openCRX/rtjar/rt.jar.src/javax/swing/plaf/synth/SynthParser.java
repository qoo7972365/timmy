/*      */ package javax.swing.plaf.synth;
/*      */ 
/*      */ import com.sun.beans.decoder.DocumentHandler;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Toolkit;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.net.URLClassLoader;
/*      */ import java.text.ParseException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.regex.PatternSyntaxException;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.plaf.ColorUIResource;
/*      */ import javax.swing.plaf.DimensionUIResource;
/*      */ import javax.swing.plaf.FontUIResource;
/*      */ import javax.swing.plaf.InsetsUIResource;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.parsers.SAXParser;
/*      */ import javax.xml.parsers.SAXParserFactory;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.Locator;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.helpers.DefaultHandler;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.swing.plaf.synth.DefaultSynthStyle;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class SynthParser
/*      */   extends DefaultHandler
/*      */ {
/*  200 */   private Map<String, Object> _mapping = new HashMap<>();
/*  201 */   private List<ParsedSynthStyle.StateInfo> _stateInfos = new ArrayList<>();
/*  202 */   private List<ColorType> _colorTypes = new ArrayList<>();
/*  203 */   private List<String> _inputMapBindings = new ArrayList<>();
/*  204 */   private List<ParsedSynthStyle.PainterInfo> _stylePainters = new ArrayList<>();
/*  205 */   private List<ParsedSynthStyle.PainterInfo> _statePainters = new ArrayList<>(); private static final String ELEMENT_SYNTH = "synth"; private static final String ELEMENT_STYLE = "style"; private static final String ELEMENT_STATE = "state"; private static final String ELEMENT_FONT = "font"; private static final String ELEMENT_COLOR = "color"; private static final String ELEMENT_IMAGE_PAINTER = "imagePainter"; private static final String ELEMENT_PAINTER = "painter"; private static final String ELEMENT_PROPERTY = "property"; private static final String ELEMENT_SYNTH_GRAPHICS = "graphicsUtils"; private static final String ELEMENT_IMAGE_ICON = "imageIcon"; private static final String ELEMENT_BIND = "bind";
/*      */   private static final String ELEMENT_BIND_KEY = "bindKey";
/*      */   private static final String ELEMENT_INSETS = "insets";
/*      */   private static final String ELEMENT_OPAQUE = "opaque";
/*      */   private static final String ELEMENT_DEFAULTS_PROPERTY = "defaultsProperty";
/*      */   private static final String ELEMENT_INPUT_MAP = "inputMap";
/*      */   private static final String ATTRIBUTE_ACTION = "action";
/*      */   private static final String ATTRIBUTE_ID = "id";
/*      */   private static final String ATTRIBUTE_IDREF = "idref";
/*      */   private static final String ATTRIBUTE_CLONE = "clone";
/*      */   private static final String ATTRIBUTE_VALUE = "value";
/*      */   private static final String ATTRIBUTE_NAME = "name";
/*      */   private static final String ATTRIBUTE_STYLE = "style";
/*      */   private static final String ATTRIBUTE_SIZE = "size";
/*      */   private static final String ATTRIBUTE_TYPE = "type";
/*      */   private static final String ATTRIBUTE_TOP = "top";
/*      */   private static final String ATTRIBUTE_LEFT = "left";
/*      */   private static final String ATTRIBUTE_BOTTOM = "bottom";
/*      */   private static final String ATTRIBUTE_RIGHT = "right";
/*      */   
/*      */   public void parse(InputStream paramInputStream, DefaultSynthStyleFactory paramDefaultSynthStyleFactory, URL paramURL, Class<?> paramClass, Map<String, Object> paramMap) throws ParseException, IllegalArgumentException {
/*  226 */     if (paramInputStream == null || paramDefaultSynthStyleFactory == null || (paramURL == null && paramClass == null))
/*      */     {
/*  228 */       throw new IllegalArgumentException("You must supply an InputStream, StyleFactory and Class or URL");
/*      */     }
/*      */ 
/*      */     
/*  232 */     assert paramURL == null || paramClass == null;
/*      */     
/*  234 */     this._factory = paramDefaultSynthStyleFactory;
/*  235 */     this._classResourceBase = paramClass;
/*  236 */     this._urlResourceBase = paramURL;
/*  237 */     this._defaultsMap = paramMap;
/*      */     
/*      */     try {
/*      */       try {
/*  241 */         SAXParser sAXParser = SAXParserFactory.newInstance().newSAXParser();
/*  242 */         sAXParser.parse(new BufferedInputStream(paramInputStream), this);
/*  243 */       } catch (ParserConfigurationException parserConfigurationException) {
/*  244 */         throw new ParseException("Error parsing: " + parserConfigurationException, 0);
/*      */       }
/*  246 */       catch (SAXException sAXException) {
/*  247 */         throw new ParseException("Error parsing: " + sAXException + " " + sAXException
/*  248 */             .getException(), 0);
/*      */       }
/*  250 */       catch (IOException iOException) {
/*  251 */         throw new ParseException("Error parsing: " + iOException, 0);
/*      */       } 
/*      */     } finally {
/*  254 */       reset();
/*      */     } 
/*      */   }
/*      */   private static final String ATTRIBUTE_KEY = "key"; private static final String ATTRIBUTE_SOURCE_INSETS = "sourceInsets"; private static final String ATTRIBUTE_DEST_INSETS = "destinationInsets"; private static final String ATTRIBUTE_PATH = "path";
/*      */   private static final String ATTRIBUTE_STRETCH = "stretch";
/*      */   private static final String ATTRIBUTE_PAINT_CENTER = "paintCenter";
/*      */   
/*      */   private URL getResource(String paramString) {
/*  262 */     if (this._classResourceBase != null) {
/*  263 */       return this._classResourceBase.getResource(paramString);
/*      */     }
/*      */     try {
/*  266 */       return new URL(this._urlResourceBase, paramString);
/*  267 */     } catch (MalformedURLException malformedURLException) {
/*  268 */       return null;
/*      */     } 
/*      */   }
/*      */   private static final String ATTRIBUTE_METHOD = "method"; private static final String ATTRIBUTE_DIRECTION = "direction"; private static final String ATTRIBUTE_CENTER = "center"; private DocumentHandler _handler; private int _depth; private DefaultSynthStyleFactory _factory; private ParsedSynthStyle _style; private ParsedSynthStyle.StateInfo _stateInfo; private String _inputMapID;
/*      */   private URL _urlResourceBase;
/*      */   private Class<?> _classResourceBase;
/*      */   private Map<String, Object> _defaultsMap;
/*      */   
/*      */   private void reset() {
/*  277 */     this._handler = null;
/*  278 */     this._depth = 0;
/*  279 */     this._mapping.clear();
/*  280 */     this._stateInfos.clear();
/*  281 */     this._colorTypes.clear();
/*  282 */     this._statePainters.clear();
/*  283 */     this._stylePainters.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isForwarding() {
/*  290 */     return (this._depth > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DocumentHandler getHandler() {
/*  297 */     if (this._handler == null) {
/*  298 */       this._handler = new DocumentHandler();
/*  299 */       if (this._urlResourceBase != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  306 */         URL[] arrayOfURL = { getResource(".") };
/*  307 */         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  308 */         URLClassLoader uRLClassLoader = new URLClassLoader(arrayOfURL, classLoader);
/*  309 */         this._handler.setClassLoader(uRLClassLoader);
/*      */       } else {
/*  311 */         this._handler.setClassLoader(this._classResourceBase.getClassLoader());
/*      */       } 
/*      */       
/*  314 */       for (String str : this._mapping.keySet()) {
/*  315 */         this._handler.setVariable(str, this._mapping.get(str));
/*      */       }
/*      */     } 
/*  318 */     return this._handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object checkCast(Object paramObject, Class paramClass) throws SAXException {
/*  326 */     if (!paramClass.isInstance(paramObject)) {
/*  327 */       throw new SAXException("Expected type " + paramClass + " got " + paramObject
/*  328 */           .getClass());
/*      */     }
/*  330 */     return paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object lookup(String paramString, Class paramClass) throws SAXException {
/*  339 */     if (this._handler != null && 
/*  340 */       this._handler.hasVariable(paramString)) {
/*  341 */       return checkCast(this._handler.getVariable(paramString), paramClass);
/*      */     }
/*      */     
/*  344 */     Object object = this._mapping.get(paramString);
/*  345 */     if (object == null) {
/*  346 */       throw new SAXException("ID " + paramString + " has not been defined");
/*      */     }
/*  348 */     return checkCast(object, paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void register(String paramString, Object paramObject) throws SAXException {
/*  356 */     if (paramString != null) {
/*  357 */       if (this._mapping.get(paramString) != null || (this._handler != null && this._handler
/*  358 */         .hasVariable(paramString))) {
/*  359 */         throw new SAXException("ID " + paramString + " is already defined");
/*      */       }
/*  361 */       if (this._handler != null) {
/*  362 */         this._handler.setVariable(paramString, paramObject);
/*      */       } else {
/*      */         
/*  365 */         this._mapping.put(paramString, paramObject);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int nextInt(StringTokenizer paramStringTokenizer, String paramString) throws SAXException {
/*  376 */     if (!paramStringTokenizer.hasMoreTokens()) {
/*  377 */       throw new SAXException(paramString);
/*      */     }
/*      */     try {
/*  380 */       return Integer.parseInt(paramStringTokenizer.nextToken());
/*  381 */     } catch (NumberFormatException numberFormatException) {
/*  382 */       throw new SAXException(paramString);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Insets parseInsets(String paramString1, String paramString2) throws SAXException {
/*  391 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString1);
/*  392 */     return new Insets(nextInt(stringTokenizer, paramString2), 
/*  393 */         nextInt(stringTokenizer, paramString2), 
/*  394 */         nextInt(stringTokenizer, paramString2), 
/*  395 */         nextInt(stringTokenizer, paramString2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void startStyle(Attributes paramAttributes) throws SAXException {
/*  405 */     String str = null;
/*      */     
/*  407 */     this._style = null;
/*  408 */     for (int i = paramAttributes.getLength() - 1; i >= 0; i--) {
/*  409 */       String str1 = paramAttributes.getQName(i);
/*  410 */       if (str1.equals("clone")) {
/*  411 */         this
/*      */           
/*  413 */           ._style = (ParsedSynthStyle)((ParsedSynthStyle)lookup(paramAttributes.getValue(i), ParsedSynthStyle.class)).clone();
/*      */       }
/*  415 */       else if (str1.equals("id")) {
/*  416 */         str = paramAttributes.getValue(i);
/*      */       } 
/*      */     } 
/*  419 */     if (this._style == null) {
/*  420 */       this._style = new ParsedSynthStyle();
/*      */     }
/*  422 */     register(str, this._style);
/*      */   }
/*      */   
/*      */   private void endStyle() {
/*  426 */     int i = this._stylePainters.size();
/*  427 */     if (i > 0) {
/*  428 */       this._style.setPainters(this._stylePainters.<ParsedSynthStyle.PainterInfo>toArray(new ParsedSynthStyle.PainterInfo[i]));
/*  429 */       this._stylePainters.clear();
/*      */     } 
/*  431 */     i = this._stateInfos.size();
/*  432 */     if (i > 0) {
/*  433 */       this._style.setStateInfo(this._stateInfos.<DefaultSynthStyle.StateInfo>toArray((DefaultSynthStyle.StateInfo[])new ParsedSynthStyle.StateInfo[i]));
/*  434 */       this._stateInfos.clear();
/*      */     } 
/*  436 */     this._style = null;
/*      */   }
/*      */   
/*      */   private void startState(Attributes paramAttributes) throws SAXException {
/*  440 */     Object object = null;
/*  441 */     int i = 0;
/*  442 */     String str = null;
/*      */     
/*  444 */     this._stateInfo = null;
/*  445 */     for (int j = paramAttributes.getLength() - 1; j >= 0; j--) {
/*  446 */       String str1 = paramAttributes.getQName(j);
/*  447 */       if (str1.equals("id")) {
/*  448 */         str = paramAttributes.getValue(j);
/*      */       }
/*  450 */       else if (str1.equals("idref")) {
/*  451 */         this._stateInfo = (ParsedSynthStyle.StateInfo)lookup(paramAttributes
/*  452 */             .getValue(j), ParsedSynthStyle.StateInfo.class);
/*      */       }
/*  454 */       else if (str1.equals("clone")) {
/*  455 */         this
/*      */           
/*  457 */           ._stateInfo = (ParsedSynthStyle.StateInfo)((ParsedSynthStyle.StateInfo)lookup(paramAttributes.getValue(j), ParsedSynthStyle.StateInfo.class)).clone();
/*      */       }
/*  459 */       else if (str1.equals("value")) {
/*      */         
/*  461 */         StringTokenizer stringTokenizer = new StringTokenizer(paramAttributes.getValue(j));
/*  462 */         while (stringTokenizer.hasMoreTokens()) {
/*      */           
/*  464 */           String str2 = stringTokenizer.nextToken().toUpperCase().intern();
/*  465 */           if (str2 == "ENABLED") {
/*  466 */             i |= 0x1; continue;
/*      */           } 
/*  468 */           if (str2 == "MOUSE_OVER") {
/*  469 */             i |= 0x2; continue;
/*      */           } 
/*  471 */           if (str2 == "PRESSED") {
/*  472 */             i |= 0x4; continue;
/*      */           } 
/*  474 */           if (str2 == "DISABLED") {
/*  475 */             i |= 0x8; continue;
/*      */           } 
/*  477 */           if (str2 == "FOCUSED") {
/*  478 */             i |= 0x100; continue;
/*      */           } 
/*  480 */           if (str2 == "SELECTED") {
/*  481 */             i |= 0x200; continue;
/*      */           } 
/*  483 */           if (str2 == "DEFAULT") {
/*  484 */             i |= 0x400; continue;
/*      */           } 
/*  486 */           if (str2 != "AND") {
/*  487 */             throw new SAXException("Unknown state: " + i);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  492 */     if (this._stateInfo == null) {
/*  493 */       this._stateInfo = new ParsedSynthStyle.StateInfo();
/*      */     }
/*  495 */     this._stateInfo.setComponentState(i);
/*  496 */     register(str, this._stateInfo);
/*  497 */     this._stateInfos.add(this._stateInfo);
/*      */   }
/*      */   
/*      */   private void endState() {
/*  501 */     int i = this._statePainters.size();
/*  502 */     if (i > 0) {
/*  503 */       this._stateInfo.setPainters(this._statePainters.<ParsedSynthStyle.PainterInfo>toArray(new ParsedSynthStyle.PainterInfo[i]));
/*  504 */       this._statePainters.clear();
/*      */     } 
/*  506 */     this._stateInfo = null;
/*      */   }
/*      */   
/*      */   private void startFont(Attributes paramAttributes) throws SAXException {
/*  510 */     Font font = null;
/*  511 */     int i = 0;
/*  512 */     int j = 0;
/*  513 */     String str1 = null;
/*  514 */     String str2 = null;
/*      */     
/*  516 */     for (int k = paramAttributes.getLength() - 1; k >= 0; k--) {
/*  517 */       String str = paramAttributes.getQName(k);
/*  518 */       if (str.equals("id")) {
/*  519 */         str1 = paramAttributes.getValue(k);
/*      */       }
/*  521 */       else if (str.equals("idref")) {
/*  522 */         font = (Font)lookup(paramAttributes.getValue(k), Font.class);
/*      */       }
/*  524 */       else if (str.equals("name")) {
/*  525 */         str2 = paramAttributes.getValue(k);
/*      */       }
/*  527 */       else if (str.equals("size")) {
/*      */         try {
/*  529 */           j = Integer.parseInt(paramAttributes.getValue(k));
/*  530 */         } catch (NumberFormatException numberFormatException) {
/*  531 */           throw new SAXException("Invalid font size: " + paramAttributes
/*  532 */               .getValue(k));
/*      */         }
/*      */       
/*  535 */       } else if (str.equals("style")) {
/*      */         
/*  537 */         StringTokenizer stringTokenizer = new StringTokenizer(paramAttributes.getValue(k));
/*  538 */         while (stringTokenizer.hasMoreTokens()) {
/*  539 */           String str3 = stringTokenizer.nextToken().intern();
/*  540 */           if (str3 == "BOLD") {
/*  541 */             i = (i | 0x0) ^ 0x0 | 0x1;
/*      */             continue;
/*      */           } 
/*  544 */           if (str3 == "ITALIC") {
/*  545 */             i |= 0x2;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  550 */     if (font == null) {
/*  551 */       if (str2 == null) {
/*  552 */         throw new SAXException("You must define a name for the font");
/*      */       }
/*  554 */       if (j == 0) {
/*  555 */         throw new SAXException("You must define a size for the font");
/*      */       }
/*  557 */       font = new FontUIResource(str2, i, j);
/*      */     }
/*  559 */     else if (str2 != null || j != 0 || i != 0) {
/*  560 */       throw new SAXException("Name, size and style are not for use with idref");
/*      */     } 
/*      */     
/*  563 */     register(str1, font);
/*  564 */     if (this._stateInfo != null) {
/*  565 */       this._stateInfo.setFont(font);
/*      */     }
/*  567 */     else if (this._style != null) {
/*  568 */       this._style.setFont(font);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void startColor(Attributes paramAttributes) throws SAXException {
/*  573 */     Color color = null;
/*  574 */     String str = null;
/*      */     
/*  576 */     this._colorTypes.clear();
/*  577 */     for (int i = paramAttributes.getLength() - 1; i >= 0; i--) {
/*  578 */       String str1 = paramAttributes.getQName(i);
/*  579 */       if (str1.equals("id")) {
/*  580 */         str = paramAttributes.getValue(i);
/*      */       }
/*  582 */       else if (str1.equals("idref")) {
/*  583 */         color = (Color)lookup(paramAttributes.getValue(i), Color.class);
/*      */       }
/*  585 */       else if (!str1.equals("name")) {
/*      */         
/*  587 */         if (str1.equals("value")) {
/*  588 */           String str2 = paramAttributes.getValue(i);
/*      */           
/*  590 */           if (str2.startsWith("#")) {
/*      */             try {
/*      */               int j;
/*      */               
/*      */               boolean bool;
/*  595 */               int k = str2.length();
/*  596 */               if (k < 8) {
/*      */                 
/*  598 */                 j = Integer.decode(str2).intValue();
/*  599 */                 bool = false;
/*  600 */               } else if (k == 8) {
/*      */                 
/*  602 */                 j = Integer.decode(str2).intValue();
/*  603 */                 bool = true;
/*  604 */               } else if (k == 9) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  612 */                 int m = Integer.decode('#' + str2
/*  613 */                     .substring(3, 9)).intValue();
/*  614 */                 int n = Integer.decode(str2.substring(0, 3)).intValue();
/*  615 */                 j = n << 24 | m;
/*  616 */                 bool = true;
/*      */               } else {
/*  618 */                 throw new SAXException("Invalid Color value: " + str2);
/*      */               } 
/*      */ 
/*      */               
/*  622 */               color = new ColorUIResource(new Color(j, bool));
/*  623 */             } catch (NumberFormatException numberFormatException) {
/*  624 */               throw new SAXException("Invalid Color value: " + str2);
/*      */             } 
/*      */           } else {
/*      */ 
/*      */             
/*      */             try {
/*  630 */               color = new ColorUIResource((Color)Color.class.getField(str2.toUpperCase()).get(Color.class));
/*  631 */             } catch (NoSuchFieldException noSuchFieldException) {
/*  632 */               throw new SAXException("Invalid color name: " + str2);
/*  633 */             } catch (IllegalAccessException illegalAccessException) {
/*  634 */               throw new SAXException("Invalid color name: " + str2);
/*      */             }
/*      */           
/*      */           } 
/*  638 */         } else if (str1.equals("type")) {
/*      */           
/*  640 */           StringTokenizer stringTokenizer = new StringTokenizer(paramAttributes.getValue(i));
/*  641 */           while (stringTokenizer.hasMoreTokens()) {
/*  642 */             Class<?> clazz; String str2 = stringTokenizer.nextToken();
/*  643 */             int j = str2.lastIndexOf('.');
/*      */ 
/*      */             
/*  646 */             if (j == -1) {
/*  647 */               clazz = ColorType.class;
/*  648 */               j = 0;
/*      */             } else {
/*      */               
/*      */               try {
/*  652 */                 clazz = ReflectUtil.forName(str2.substring(0, j));
/*      */               }
/*  654 */               catch (ClassNotFoundException classNotFoundException) {
/*  655 */                 throw new SAXException("Unknown class: " + str2
/*  656 */                     .substring(0, j));
/*      */               } 
/*  658 */               j++;
/*      */             } 
/*      */             try {
/*  661 */               this._colorTypes.add((ColorType)checkCast(clazz
/*  662 */                     .getField(str2.substring(j))
/*  663 */                     .get(clazz), ColorType.class));
/*  664 */             } catch (NoSuchFieldException noSuchFieldException) {
/*  665 */               throw new SAXException("Unable to find color type: " + str2);
/*      */             }
/*  667 */             catch (IllegalAccessException illegalAccessException) {
/*  668 */               throw new SAXException("Unable to find color type: " + str2);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  674 */     if (color == null) {
/*  675 */       throw new SAXException("color: you must specificy a value");
/*      */     }
/*  677 */     register(str, color);
/*  678 */     if (this._stateInfo != null && this._colorTypes.size() > 0) {
/*  679 */       Color[] arrayOfColor = this._stateInfo.getColors();
/*  680 */       int j = 0; int k;
/*  681 */       for (k = this._colorTypes.size() - 1; k >= 0; 
/*  682 */         k--) {
/*  683 */         j = Math.max(j, ((ColorType)this._colorTypes.get(k)).getID());
/*      */       }
/*  685 */       if (arrayOfColor == null || arrayOfColor.length <= j) {
/*  686 */         Color[] arrayOfColor1 = new Color[j + 1];
/*  687 */         if (arrayOfColor != null) {
/*  688 */           System.arraycopy(arrayOfColor, 0, arrayOfColor1, 0, arrayOfColor.length);
/*      */         }
/*  690 */         arrayOfColor = arrayOfColor1;
/*      */       } 
/*  692 */       for (k = this._colorTypes.size() - 1; k >= 0; 
/*  693 */         k--) {
/*  694 */         arrayOfColor[((ColorType)this._colorTypes.get(k)).getID()] = color;
/*      */       }
/*  696 */       this._stateInfo.setColors(arrayOfColor);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void startProperty(Attributes paramAttributes, Object paramObject) throws SAXException {
/*  702 */     Object object = null;
/*  703 */     String str1 = null;
/*      */ 
/*      */     
/*  706 */     byte b = 0;
/*  707 */     String str2 = null;
/*      */     
/*  709 */     for (int i = paramAttributes.getLength() - 1; i >= 0; i--) {
/*  710 */       String str = paramAttributes.getQName(i);
/*  711 */       if (str.equals("type")) {
/*  712 */         String str3 = paramAttributes.getValue(i).toUpperCase();
/*  713 */         if (str3.equals("IDREF")) {
/*  714 */           b = 0;
/*      */         }
/*  716 */         else if (str3.equals("BOOLEAN")) {
/*  717 */           b = 1;
/*      */         }
/*  719 */         else if (str3.equals("DIMENSION")) {
/*  720 */           b = 2;
/*      */         }
/*  722 */         else if (str3.equals("INSETS")) {
/*  723 */           b = 3;
/*      */         }
/*  725 */         else if (str3.equals("INTEGER")) {
/*  726 */           b = 4;
/*      */         }
/*  728 */         else if (str3.equals("STRING")) {
/*  729 */           b = 5;
/*      */         } else {
/*      */           
/*  732 */           throw new SAXException(paramObject + " unknown type, useidref, boolean, dimension, insets or integer");
/*      */         }
/*      */       
/*      */       }
/*  736 */       else if (str.equals("value")) {
/*  737 */         str2 = paramAttributes.getValue(i);
/*      */       }
/*  739 */       else if (str.equals("key")) {
/*  740 */         str1 = paramAttributes.getValue(i);
/*      */       } 
/*      */     } 
/*  743 */     if (str2 != null) {
/*  744 */       StringTokenizer stringTokenizer; switch (b) {
/*      */         case 0:
/*  746 */           object = lookup(str2, Object.class);
/*      */           break;
/*      */         case 1:
/*  749 */           if (str2.toUpperCase().equals("TRUE")) {
/*  750 */             object = Boolean.TRUE;
/*      */             break;
/*      */           } 
/*  753 */           object = Boolean.FALSE;
/*      */           break;
/*      */         
/*      */         case 2:
/*  757 */           stringTokenizer = new StringTokenizer(str2);
/*      */ 
/*      */           
/*  760 */           object = new DimensionUIResource(nextInt(stringTokenizer, "Invalid dimension"), nextInt(stringTokenizer, "Invalid dimension"));
/*      */           break;
/*      */         case 3:
/*  763 */           object = parseInsets(str2, paramObject + " invalid insets");
/*      */           break;
/*      */         case 4:
/*      */           try {
/*  767 */             object = new Integer(Integer.parseInt(str2));
/*  768 */           } catch (NumberFormatException numberFormatException) {
/*  769 */             throw new SAXException(paramObject + " invalid value");
/*      */           } 
/*      */           break;
/*      */         case 5:
/*  773 */           object = str2;
/*      */           break;
/*      */       } 
/*      */     } 
/*  777 */     if (object == null || str1 == null) {
/*  778 */       throw new SAXException(paramObject + ": you must supply a key and value");
/*      */     }
/*      */     
/*  781 */     if (paramObject == "defaultsProperty") {
/*  782 */       this._defaultsMap.put(str1, object);
/*      */     }
/*  784 */     else if (this._stateInfo != null) {
/*  785 */       if (this._stateInfo.getData() == null) {
/*  786 */         this._stateInfo.setData(new HashMap<>());
/*      */       }
/*  788 */       this._stateInfo.getData().put(str1, object);
/*      */     }
/*  790 */     else if (this._style != null) {
/*  791 */       if (this._style.getData() == null) {
/*  792 */         this._style.setData(new HashMap<>());
/*      */       }
/*  794 */       this._style.getData().put(str1, object);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void startGraphics(Attributes paramAttributes) throws SAXException {
/*  799 */     SynthGraphicsUtils synthGraphicsUtils = null;
/*      */     
/*  801 */     for (int i = paramAttributes.getLength() - 1; i >= 0; i--) {
/*  802 */       String str = paramAttributes.getQName(i);
/*  803 */       if (str.equals("idref")) {
/*  804 */         synthGraphicsUtils = (SynthGraphicsUtils)lookup(paramAttributes.getValue(i), SynthGraphicsUtils.class);
/*      */       }
/*      */     } 
/*      */     
/*  808 */     if (synthGraphicsUtils == null) {
/*  809 */       throw new SAXException("graphicsUtils: you must supply an idref");
/*      */     }
/*  811 */     if (this._style != null) {
/*  812 */       this._style.setGraphicsUtils(synthGraphicsUtils);
/*      */     }
/*      */   }
/*      */   
/*      */   private void startInsets(Attributes paramAttributes) throws SAXException {
/*  817 */     int i = 0;
/*  818 */     int j = 0;
/*  819 */     int k = 0;
/*  820 */     int m = 0;
/*  821 */     Insets insets = null;
/*  822 */     String str = null;
/*      */     
/*  824 */     for (int n = paramAttributes.getLength() - 1; n >= 0; n--) {
/*  825 */       String str1 = paramAttributes.getQName(n);
/*      */       
/*      */       try {
/*  828 */         if (str1.equals("idref")) {
/*  829 */           insets = (Insets)lookup(paramAttributes.getValue(n), Insets.class);
/*      */         
/*      */         }
/*  832 */         else if (str1.equals("id")) {
/*  833 */           str = paramAttributes.getValue(n);
/*      */         }
/*  835 */         else if (str1.equals("top")) {
/*  836 */           i = Integer.parseInt(paramAttributes.getValue(n));
/*      */         }
/*  838 */         else if (str1.equals("left")) {
/*  839 */           k = Integer.parseInt(paramAttributes.getValue(n));
/*      */         }
/*  841 */         else if (str1.equals("bottom")) {
/*  842 */           j = Integer.parseInt(paramAttributes.getValue(n));
/*      */         }
/*  844 */         else if (str1.equals("right")) {
/*  845 */           m = Integer.parseInt(paramAttributes.getValue(n));
/*      */         } 
/*  847 */       } catch (NumberFormatException numberFormatException) {
/*  848 */         throw new SAXException("insets: bad integer value for " + paramAttributes
/*  849 */             .getValue(n));
/*      */       } 
/*      */     } 
/*  852 */     if (insets == null) {
/*  853 */       insets = new InsetsUIResource(i, k, j, m);
/*      */     }
/*  855 */     register(str, insets);
/*  856 */     if (this._style != null) {
/*  857 */       this._style.setInsets(insets);
/*      */     }
/*      */   }
/*      */   
/*      */   private void startBind(Attributes paramAttributes) throws SAXException {
/*  862 */     ParsedSynthStyle parsedSynthStyle = null;
/*  863 */     String str = null;
/*  864 */     byte b = -1;
/*      */     
/*  866 */     for (int i = paramAttributes.getLength() - 1; i >= 0; i--) {
/*  867 */       String str1 = paramAttributes.getQName(i);
/*      */       
/*  869 */       if (str1.equals("style")) {
/*  870 */         parsedSynthStyle = (ParsedSynthStyle)lookup(paramAttributes.getValue(i), ParsedSynthStyle.class);
/*      */       
/*      */       }
/*  873 */       else if (str1.equals("type")) {
/*  874 */         String str2 = paramAttributes.getValue(i).toUpperCase();
/*      */         
/*  876 */         if (str2.equals("NAME")) {
/*  877 */           b = 0;
/*      */         }
/*  879 */         else if (str2.equals("REGION")) {
/*  880 */           b = 1;
/*      */         } else {
/*      */           
/*  883 */           throw new SAXException("bind: unknown type " + str2);
/*      */         }
/*      */       
/*  886 */       } else if (str1.equals("key")) {
/*  887 */         str = paramAttributes.getValue(i);
/*      */       } 
/*      */     } 
/*  890 */     if (parsedSynthStyle == null || str == null || b == -1) {
/*  891 */       throw new SAXException("bind: you must specify a style, type and key");
/*      */     }
/*      */     
/*      */     try {
/*  895 */       this._factory.addStyle(parsedSynthStyle, str, b);
/*  896 */     } catch (PatternSyntaxException patternSyntaxException) {
/*  897 */       throw new SAXException("bind: " + str + " is not a valid regular expression");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void startPainter(Attributes paramAttributes, String paramString) throws SAXException {
/*  903 */     Insets insets1 = null;
/*  904 */     Insets insets2 = null;
/*  905 */     String str1 = null;
/*  906 */     boolean bool1 = true;
/*  907 */     boolean bool2 = true;
/*  908 */     SynthPainter synthPainter = null;
/*  909 */     String str2 = null;
/*  910 */     String str3 = null;
/*  911 */     byte b = -1;
/*  912 */     boolean bool3 = false;
/*      */     
/*  914 */     boolean bool4 = false;
/*  915 */     boolean bool5 = false;
/*      */     
/*  917 */     for (int i = paramAttributes.getLength() - 1; i >= 0; i--) {
/*  918 */       String str4 = paramAttributes.getQName(i);
/*  919 */       String str5 = paramAttributes.getValue(i);
/*      */       
/*  921 */       if (str4.equals("id")) {
/*  922 */         str3 = str5;
/*      */       }
/*  924 */       else if (str4.equals("method")) {
/*  925 */         str2 = str5.toLowerCase(Locale.ENGLISH);
/*      */       }
/*  927 */       else if (str4.equals("idref")) {
/*  928 */         synthPainter = (SynthPainter)lookup(str5, SynthPainter.class);
/*      */       }
/*  930 */       else if (str4.equals("path")) {
/*  931 */         str1 = str5;
/*      */       }
/*  933 */       else if (str4.equals("sourceInsets")) {
/*  934 */         insets1 = parseInsets(str5, paramString + ": sourceInsets must be top left bottom right");
/*      */       
/*      */       }
/*  937 */       else if (str4.equals("destinationInsets")) {
/*  938 */         insets2 = parseInsets(str5, paramString + ": destinationInsets must be top left bottom right");
/*      */       
/*      */       }
/*  941 */       else if (str4.equals("paintCenter")) {
/*  942 */         bool1 = str5.toLowerCase().equals("true");
/*  943 */         bool5 = true;
/*      */       }
/*  945 */       else if (str4.equals("stretch")) {
/*  946 */         bool2 = str5.toLowerCase().equals("true");
/*  947 */         bool4 = true;
/*      */       }
/*  949 */       else if (str4.equals("direction")) {
/*  950 */         str5 = str5.toUpperCase().intern();
/*  951 */         if (str5 == "EAST") {
/*  952 */           b = 3;
/*      */         }
/*  954 */         else if (str5 == "NORTH") {
/*  955 */           b = 1;
/*      */         }
/*  957 */         else if (str5 == "SOUTH") {
/*  958 */           b = 5;
/*      */         }
/*  960 */         else if (str5 == "WEST") {
/*  961 */           b = 7;
/*      */         }
/*  963 */         else if (str5 == "TOP") {
/*  964 */           b = 1;
/*      */         }
/*  966 */         else if (str5 == "LEFT") {
/*  967 */           b = 2;
/*      */         }
/*  969 */         else if (str5 == "BOTTOM") {
/*  970 */           b = 3;
/*      */         }
/*  972 */         else if (str5 == "RIGHT") {
/*  973 */           b = 4;
/*      */         }
/*  975 */         else if (str5 == "HORIZONTAL") {
/*  976 */           b = 0;
/*      */         }
/*  978 */         else if (str5 == "VERTICAL") {
/*  979 */           b = 1;
/*      */         }
/*  981 */         else if (str5 == "HORIZONTAL_SPLIT") {
/*  982 */           b = 1;
/*      */         }
/*  984 */         else if (str5 == "VERTICAL_SPLIT") {
/*  985 */           b = 0;
/*      */         } else {
/*      */           
/*  988 */           throw new SAXException(paramString + ": unknown direction");
/*      */         }
/*      */       
/*  991 */       } else if (str4.equals("center")) {
/*  992 */         bool3 = str5.toLowerCase().equals("true");
/*      */       } 
/*      */     } 
/*  995 */     if (synthPainter == null) {
/*  996 */       if (paramString == "painter") {
/*  997 */         throw new SAXException(paramString + ": you must specify an idref");
/*      */       }
/*      */       
/* 1000 */       if (insets1 == null && !bool3) {
/* 1001 */         throw new SAXException("property: you must specify sourceInsets");
/*      */       }
/*      */       
/* 1004 */       if (str1 == null) {
/* 1005 */         throw new SAXException("property: you must specify a path");
/*      */       }
/* 1007 */       if (bool3 && (insets1 != null || insets2 != null || bool5 || bool4))
/*      */       {
/* 1009 */         throw new SAXException("The attributes: sourceInsets, destinationInsets, paintCenter and stretch  are not legal when center is true");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1014 */       synthPainter = new ImagePainter(!bool2, bool1, insets1, insets2, getResource(str1), bool3);
/*      */     } 
/* 1016 */     register(str3, synthPainter);
/* 1017 */     if (this._stateInfo != null) {
/* 1018 */       addPainterOrMerge(this._statePainters, str2, synthPainter, b);
/*      */     }
/* 1020 */     else if (this._style != null) {
/* 1021 */       addPainterOrMerge(this._stylePainters, str2, synthPainter, b);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addPainterOrMerge(List<ParsedSynthStyle.PainterInfo> paramList, String paramString, SynthPainter paramSynthPainter, int paramInt) {
/* 1028 */     ParsedSynthStyle.PainterInfo painterInfo = new ParsedSynthStyle.PainterInfo(paramString, paramSynthPainter, paramInt);
/*      */ 
/*      */ 
/*      */     
/* 1032 */     for (ParsedSynthStyle.PainterInfo painterInfo1 : paramList) {
/*      */       
/* 1034 */       ParsedSynthStyle.PainterInfo painterInfo2 = painterInfo1;
/*      */       
/* 1036 */       if (painterInfo.equalsPainter(painterInfo2)) {
/* 1037 */         painterInfo2.addPainter(paramSynthPainter);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1042 */     paramList.add(painterInfo);
/*      */   }
/*      */   
/*      */   private void startImageIcon(Attributes paramAttributes) throws SAXException {
/* 1046 */     String str1 = null;
/* 1047 */     String str2 = null;
/*      */     
/* 1049 */     for (int i = paramAttributes.getLength() - 1; i >= 0; i--) {
/* 1050 */       String str = paramAttributes.getQName(i);
/*      */       
/* 1052 */       if (str.equals("id")) {
/* 1053 */         str2 = paramAttributes.getValue(i);
/*      */       }
/* 1055 */       else if (str.equals("path")) {
/* 1056 */         str1 = paramAttributes.getValue(i);
/*      */       } 
/*      */     } 
/* 1059 */     if (str1 == null) {
/* 1060 */       throw new SAXException("imageIcon: you must specify a path");
/*      */     }
/* 1062 */     register(str2, new LazyImageIcon(getResource(str1)));
/*      */   }
/*      */   
/*      */   private void startOpaque(Attributes paramAttributes) {
/* 1066 */     if (this._style != null) {
/* 1067 */       this._style.setOpaque(true);
/* 1068 */       for (int i = paramAttributes.getLength() - 1; i >= 0; i--) {
/* 1069 */         String str = paramAttributes.getQName(i);
/*      */         
/* 1071 */         if (str.equals("value")) {
/* 1072 */           this._style.setOpaque("true".equals(paramAttributes.getValue(i)
/* 1073 */                 .toLowerCase()));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void startInputMap(Attributes paramAttributes) throws SAXException {
/* 1080 */     this._inputMapBindings.clear();
/* 1081 */     this._inputMapID = null;
/* 1082 */     if (this._style != null) {
/* 1083 */       for (int i = paramAttributes.getLength() - 1; i >= 0; i--) {
/* 1084 */         String str = paramAttributes.getQName(i);
/*      */         
/* 1086 */         if (str.equals("id")) {
/* 1087 */           this._inputMapID = paramAttributes.getValue(i);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void endInputMap() throws SAXException {
/* 1094 */     if (this._inputMapID != null) {
/* 1095 */       register(this._inputMapID, new UIDefaults.LazyInputMap(this._inputMapBindings
/* 1096 */             .toArray(
/* 1097 */               new Object[this._inputMapBindings.size()])));
/*      */     }
/* 1099 */     this._inputMapBindings.clear();
/* 1100 */     this._inputMapID = null;
/*      */   }
/*      */   
/*      */   private void startBindKey(Attributes paramAttributes) throws SAXException {
/* 1104 */     if (this._inputMapID == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1108 */     if (this._style != null) {
/* 1109 */       String str1 = null;
/* 1110 */       String str2 = null;
/* 1111 */       for (int i = paramAttributes.getLength() - 1; i >= 0; i--) {
/* 1112 */         String str = paramAttributes.getQName(i);
/*      */         
/* 1114 */         if (str.equals("key")) {
/* 1115 */           str1 = paramAttributes.getValue(i);
/*      */         }
/* 1117 */         else if (str.equals("action")) {
/* 1118 */           str2 = paramAttributes.getValue(i);
/*      */         } 
/*      */       } 
/* 1121 */       if (str1 == null || str2 == null) {
/* 1122 */         throw new SAXException("bindKey: you must supply a key and action");
/*      */       }
/*      */       
/* 1125 */       this._inputMapBindings.add(str1);
/* 1126 */       this._inputMapBindings.add(str2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputSource resolveEntity(String paramString1, String paramString2) throws IOException, SAXException {
/* 1137 */     if (isForwarding()) {
/* 1138 */       return getHandler().resolveEntity(paramString1, paramString2);
/*      */     }
/* 1140 */     return null;
/*      */   }
/*      */   
/*      */   public void notationDecl(String paramString1, String paramString2, String paramString3) throws SAXException {
/* 1144 */     if (isForwarding()) {
/* 1145 */       getHandler().notationDecl(paramString1, paramString2, paramString3);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void unparsedEntityDecl(String paramString1, String paramString2, String paramString3, String paramString4) throws SAXException {
/* 1151 */     if (isForwarding()) {
/* 1152 */       getHandler().unparsedEntityDecl(paramString1, paramString2, paramString3, paramString4);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDocumentLocator(Locator paramLocator) {
/* 1158 */     if (isForwarding()) {
/* 1159 */       getHandler().setDocumentLocator(paramLocator);
/*      */     }
/*      */   }
/*      */   
/*      */   public void startDocument() throws SAXException {
/* 1164 */     if (isForwarding()) {
/* 1165 */       getHandler().startDocument();
/*      */     }
/*      */   }
/*      */   
/*      */   public void endDocument() throws SAXException {
/* 1170 */     if (isForwarding()) {
/* 1171 */       getHandler().endDocument();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) throws SAXException {
/* 1177 */     paramString3 = paramString3.intern();
/* 1178 */     if (paramString3 == "style") {
/* 1179 */       startStyle(paramAttributes);
/*      */     }
/* 1181 */     else if (paramString3 == "state") {
/* 1182 */       startState(paramAttributes);
/*      */     }
/* 1184 */     else if (paramString3 == "font") {
/* 1185 */       startFont(paramAttributes);
/*      */     }
/* 1187 */     else if (paramString3 == "color") {
/* 1188 */       startColor(paramAttributes);
/*      */     }
/* 1190 */     else if (paramString3 == "painter") {
/* 1191 */       startPainter(paramAttributes, paramString3);
/*      */     }
/* 1193 */     else if (paramString3 == "imagePainter") {
/* 1194 */       startPainter(paramAttributes, paramString3);
/*      */     }
/* 1196 */     else if (paramString3 == "property") {
/* 1197 */       startProperty(paramAttributes, "property");
/*      */     }
/* 1199 */     else if (paramString3 == "defaultsProperty") {
/* 1200 */       startProperty(paramAttributes, "defaultsProperty");
/*      */     }
/* 1202 */     else if (paramString3 == "graphicsUtils") {
/* 1203 */       startGraphics(paramAttributes);
/*      */     }
/* 1205 */     else if (paramString3 == "insets") {
/* 1206 */       startInsets(paramAttributes);
/*      */     }
/* 1208 */     else if (paramString3 == "bind") {
/* 1209 */       startBind(paramAttributes);
/*      */     }
/* 1211 */     else if (paramString3 == "bindKey") {
/* 1212 */       startBindKey(paramAttributes);
/*      */     }
/* 1214 */     else if (paramString3 == "imageIcon") {
/* 1215 */       startImageIcon(paramAttributes);
/*      */     }
/* 1217 */     else if (paramString3 == "opaque") {
/* 1218 */       startOpaque(paramAttributes);
/*      */     }
/* 1220 */     else if (paramString3 == "inputMap") {
/* 1221 */       startInputMap(paramAttributes);
/*      */     }
/* 1223 */     else if (paramString3 != "synth") {
/* 1224 */       if (this._depth++ == 0) {
/* 1225 */         getHandler().startDocument();
/*      */       }
/* 1227 */       getHandler().startElement(paramString1, paramString2, paramString3, paramAttributes);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void endElement(String paramString1, String paramString2, String paramString3) throws SAXException {
/* 1232 */     if (isForwarding()) {
/* 1233 */       getHandler().endElement(paramString1, paramString2, paramString3);
/* 1234 */       this._depth--;
/* 1235 */       if (!isForwarding()) {
/* 1236 */         getHandler().startDocument();
/*      */       }
/*      */     } else {
/*      */       
/* 1240 */       paramString3 = paramString3.intern();
/* 1241 */       if (paramString3 == "style") {
/* 1242 */         endStyle();
/*      */       }
/* 1244 */       else if (paramString3 == "state") {
/* 1245 */         endState();
/*      */       }
/* 1247 */       else if (paramString3 == "inputMap") {
/* 1248 */         endInputMap();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void characters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException {
/* 1255 */     if (isForwarding()) {
/* 1256 */       getHandler().characters(paramArrayOfchar, paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void ignorableWhitespace(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException {
/* 1262 */     if (isForwarding()) {
/* 1263 */       getHandler().ignorableWhitespace(paramArrayOfchar, paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void processingInstruction(String paramString1, String paramString2) throws SAXException {
/* 1269 */     if (isForwarding()) {
/* 1270 */       getHandler().processingInstruction(paramString1, paramString2);
/*      */     }
/*      */   }
/*      */   
/*      */   public void warning(SAXParseException paramSAXParseException) throws SAXException {
/* 1275 */     if (isForwarding()) {
/* 1276 */       getHandler().warning(paramSAXParseException);
/*      */     }
/*      */   }
/*      */   
/*      */   public void error(SAXParseException paramSAXParseException) throws SAXException {
/* 1281 */     if (isForwarding()) {
/* 1282 */       getHandler().error(paramSAXParseException);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void fatalError(SAXParseException paramSAXParseException) throws SAXException {
/* 1288 */     if (isForwarding()) {
/* 1289 */       getHandler().fatalError(paramSAXParseException);
/*      */     }
/* 1291 */     throw paramSAXParseException;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class LazyImageIcon
/*      */     extends ImageIcon
/*      */     implements UIResource
/*      */   {
/*      */     private URL location;
/*      */ 
/*      */     
/*      */     public LazyImageIcon(URL param1URL) {
/* 1303 */       this.location = param1URL;
/*      */     }
/*      */     
/*      */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 1307 */       if (getImage() != null) {
/* 1308 */         super.paintIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*      */       }
/*      */     }
/*      */     
/*      */     public int getIconWidth() {
/* 1313 */       if (getImage() != null) {
/* 1314 */         return super.getIconWidth();
/*      */       }
/* 1316 */       return 0;
/*      */     }
/*      */     
/*      */     public int getIconHeight() {
/* 1320 */       if (getImage() != null) {
/* 1321 */         return super.getIconHeight();
/*      */       }
/* 1323 */       return 0;
/*      */     }
/*      */     
/*      */     public Image getImage() {
/* 1327 */       if (this.location != null) {
/* 1328 */         setImage(Toolkit.getDefaultToolkit().getImage(this.location));
/* 1329 */         this.location = null;
/*      */       } 
/* 1331 */       return super.getImage();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */