/*      */ package javax.swing.text.html;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringReader;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.util.EmptyStackException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Stack;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.Document;
/*      */ import javax.swing.text.Element;
/*      */ import javax.swing.text.MutableAttributeSet;
/*      */ import javax.swing.text.SimpleAttributeSet;
/*      */ import javax.swing.text.Style;
/*      */ import javax.swing.text.StyleConstants;
/*      */ import javax.swing.text.StyleContext;
/*      */ import javax.swing.text.StyledDocument;
/*      */ import javax.swing.text.View;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StyleSheet
/*      */   extends StyleContext
/*      */ {
/*  167 */   private SelectorMapping selectorMapping = new SelectorMapping(0);
/*  168 */   private Hashtable<String, ResolvedStyle> resolvedStyles = new Hashtable<>(); public StyleSheet() {
/*  169 */     if (this.css == null) {
/*  170 */       this.css = new CSS();
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
/*      */   public Style getRule(HTML.Tag paramTag, Element paramElement) {
/*  190 */     SearchBuffer searchBuffer = SearchBuffer.obtainSearchBuffer();
/*      */ 
/*      */     
/*      */     try {
/*  194 */       Vector<Element> vector = searchBuffer.getVector();
/*      */       
/*  196 */       for (Element element = paramElement; element != null; element = element.getParentElement()) {
/*  197 */         vector.addElement(element);
/*      */       }
/*      */ 
/*      */       
/*  201 */       int i = vector.size();
/*  202 */       StringBuffer stringBuffer = searchBuffer.getStringBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  208 */       for (int j = i - 1; j >= 1; j--) {
/*  209 */         paramElement = vector.elementAt(j);
/*  210 */         AttributeSet attributeSet1 = paramElement.getAttributes();
/*  211 */         Object object = attributeSet1.getAttribute(StyleConstants.NameAttribute);
/*  212 */         String str = object.toString();
/*  213 */         stringBuffer.append(str);
/*  214 */         if (attributeSet1 != null) {
/*  215 */           if (attributeSet1.isDefined(HTML.Attribute.ID)) {
/*  216 */             stringBuffer.append('#');
/*  217 */             stringBuffer.append(attributeSet1
/*  218 */                 .getAttribute(HTML.Attribute.ID));
/*      */           }
/*  220 */           else if (attributeSet1.isDefined(HTML.Attribute.CLASS)) {
/*  221 */             stringBuffer.append('.');
/*  222 */             stringBuffer.append(attributeSet1
/*  223 */                 .getAttribute(HTML.Attribute.CLASS));
/*      */           } 
/*      */         }
/*  226 */         stringBuffer.append(' ');
/*      */       } 
/*  228 */       stringBuffer.append(paramTag.toString());
/*  229 */       paramElement = vector.elementAt(0);
/*  230 */       AttributeSet attributeSet = paramElement.getAttributes();
/*  231 */       if (paramElement.isLeaf()) {
/*      */         
/*  233 */         Object object = attributeSet.getAttribute(paramTag);
/*  234 */         if (object instanceof AttributeSet) {
/*  235 */           attributeSet = (AttributeSet)object;
/*      */         } else {
/*      */           
/*  238 */           attributeSet = null;
/*      */         } 
/*      */       } 
/*  241 */       if (attributeSet != null) {
/*  242 */         if (attributeSet.isDefined(HTML.Attribute.ID)) {
/*  243 */           stringBuffer.append('#');
/*  244 */           stringBuffer.append(attributeSet.getAttribute(HTML.Attribute.ID));
/*      */         }
/*  246 */         else if (attributeSet.isDefined(HTML.Attribute.CLASS)) {
/*  247 */           stringBuffer.append('.');
/*  248 */           stringBuffer.append(attributeSet
/*  249 */               .getAttribute(HTML.Attribute.CLASS));
/*      */         } 
/*      */       }
/*      */       
/*  253 */       Style style = getResolvedStyle(stringBuffer.toString(), vector, paramTag);
/*      */       
/*  255 */       return style;
/*      */     } finally {
/*      */       
/*  258 */       SearchBuffer.releaseSearchBuffer(searchBuffer);
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
/*      */   public Style getRule(String paramString) {
/*  274 */     paramString = cleanSelectorString(paramString);
/*  275 */     if (paramString != null) {
/*  276 */       return getResolvedStyle(paramString);
/*      */     }
/*      */     
/*  279 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRule(String paramString) {
/*  288 */     if (paramString != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  295 */       if (paramString == "BASE_SIZE_DISABLE") {
/*  296 */         this.sizeMap = sizeMapDefault;
/*  297 */       } else if (paramString.startsWith("BASE_SIZE ")) {
/*  298 */         rebaseSizeMap(
/*  299 */             Integer.parseInt(paramString.substring("BASE_SIZE ".length())));
/*  300 */       } else if (paramString == "W3C_LENGTH_UNITS_ENABLE") {
/*  301 */         this.w3cLengthUnits = true;
/*  302 */       } else if (paramString == "W3C_LENGTH_UNITS_DISABLE") {
/*  303 */         this.w3cLengthUnits = false;
/*      */       } else {
/*  305 */         CssParser cssParser = new CssParser();
/*      */         try {
/*  307 */           cssParser.parse(getBase(), new StringReader(paramString), false, false);
/*  308 */         } catch (IOException iOException) {}
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeSet getDeclaration(String paramString) {
/*  319 */     if (paramString == null) {
/*  320 */       return SimpleAttributeSet.EMPTY;
/*      */     }
/*  322 */     CssParser cssParser = new CssParser();
/*  323 */     return cssParser.parseDeclaration(paramString);
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
/*      */   public void loadRules(Reader paramReader, URL paramURL) throws IOException {
/*  338 */     CssParser cssParser = new CssParser();
/*  339 */     cssParser.parse(paramURL, paramReader, false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeSet getViewAttributes(View paramView) {
/*  348 */     return new ViewAttributeSet(paramView);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeStyle(String paramString) {
/*  357 */     Style style = getStyle(paramString);
/*      */     
/*  359 */     if (style != null) {
/*  360 */       String str = cleanSelectorString(paramString);
/*  361 */       String[] arrayOfString = getSimpleSelectors(str);
/*  362 */       synchronized (this) {
/*  363 */         SelectorMapping selectorMapping = getRootSelectorMapping();
/*  364 */         for (int i = arrayOfString.length - 1; i >= 0; i--) {
/*  365 */           selectorMapping = selectorMapping.getChildSelectorMapping(arrayOfString[i], true);
/*      */         }
/*      */         
/*  368 */         Style style1 = selectorMapping.getStyle();
/*  369 */         if (style1 != null) {
/*  370 */           selectorMapping.setStyle(null);
/*  371 */           if (this.resolvedStyles.size() > 0) {
/*  372 */             Enumeration<ResolvedStyle> enumeration = this.resolvedStyles.elements();
/*  373 */             while (enumeration.hasMoreElements()) {
/*  374 */               ResolvedStyle resolvedStyle = enumeration.nextElement();
/*  375 */               resolvedStyle.removeStyle(style1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  381 */     super.removeStyle(paramString);
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
/*      */   public void addStyleSheet(StyleSheet paramStyleSheet) {
/*  393 */     synchronized (this) {
/*  394 */       if (this.linkedStyleSheets == null) {
/*  395 */         this.linkedStyleSheets = new Vector<>();
/*      */       }
/*  397 */       if (!this.linkedStyleSheets.contains(paramStyleSheet)) {
/*  398 */         int i = 0;
/*  399 */         if (paramStyleSheet instanceof javax.swing.plaf.UIResource && this.linkedStyleSheets
/*  400 */           .size() > 1) {
/*  401 */           i = this.linkedStyleSheets.size() - 1;
/*      */         }
/*  403 */         this.linkedStyleSheets.insertElementAt(paramStyleSheet, i);
/*  404 */         linkStyleSheetAt(paramStyleSheet, i);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeStyleSheet(StyleSheet paramStyleSheet) {
/*  415 */     synchronized (this) {
/*  416 */       if (this.linkedStyleSheets != null) {
/*  417 */         int i = this.linkedStyleSheets.indexOf(paramStyleSheet);
/*  418 */         if (i != -1) {
/*  419 */           this.linkedStyleSheets.removeElementAt(i);
/*  420 */           unlinkStyleSheet(paramStyleSheet, i);
/*  421 */           if (i == 0 && this.linkedStyleSheets.size() == 0) {
/*  422 */             this.linkedStyleSheets = null;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public StyleSheet[] getStyleSheets() {
/*      */     StyleSheet[] arrayOfStyleSheet;
/*  442 */     synchronized (this) {
/*  443 */       if (this.linkedStyleSheets != null) {
/*  444 */         arrayOfStyleSheet = new StyleSheet[this.linkedStyleSheets.size()];
/*  445 */         this.linkedStyleSheets.copyInto((Object[])arrayOfStyleSheet);
/*      */       } else {
/*      */         
/*  448 */         arrayOfStyleSheet = null;
/*      */       } 
/*      */     } 
/*  451 */     return arrayOfStyleSheet;
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
/*      */   public void importStyleSheet(URL paramURL) {
/*      */     try {
/*  466 */       InputStream inputStream = paramURL.openStream();
/*  467 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
/*  468 */       CssParser cssParser = new CssParser();
/*  469 */       cssParser.parse(paramURL, bufferedReader, false, true);
/*  470 */       bufferedReader.close();
/*  471 */       inputStream.close();
/*  472 */     } catch (Throwable throwable) {}
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
/*      */   public void setBase(URL paramURL) {
/*  485 */     this.base = paramURL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL getBase() {
/*  494 */     return this.base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCSSAttribute(MutableAttributeSet paramMutableAttributeSet, CSS.Attribute paramAttribute, String paramString) {
/*  504 */     this.css.addInternalCSSValue(paramMutableAttributeSet, paramAttribute, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addCSSAttributeFromHTML(MutableAttributeSet paramMutableAttributeSet, CSS.Attribute paramAttribute, String paramString) {
/*  514 */     Object object = this.css.getCssValue(paramAttribute, paramString);
/*  515 */     if (object != null) {
/*  516 */       paramMutableAttributeSet.addAttribute(paramAttribute, object);
/*  517 */       return true;
/*      */     } 
/*  519 */     return false;
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
/*      */   public AttributeSet translateHTMLToCSS(AttributeSet paramAttributeSet) {
/*  531 */     AttributeSet attributeSet = this.css.translateHTMLToCSS(paramAttributeSet);
/*      */     
/*  533 */     Style style = addStyle(null, null);
/*  534 */     style.addAttributes(attributeSet);
/*      */     
/*  536 */     return style;
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
/*      */   public AttributeSet addAttribute(AttributeSet paramAttributeSet, Object paramObject1, Object paramObject2) {
/*  555 */     if (this.css == null)
/*      */     {
/*      */       
/*  558 */       this.css = new CSS();
/*      */     }
/*  560 */     if (paramObject1 instanceof StyleConstants) {
/*  561 */       HTML.Tag tag = HTML.getTagForStyleConstantsKey((StyleConstants)paramObject1);
/*      */ 
/*      */       
/*  564 */       if (tag != null && paramAttributeSet.isDefined(tag)) {
/*  565 */         paramAttributeSet = removeAttribute(paramAttributeSet, tag);
/*      */       }
/*      */ 
/*      */       
/*  569 */       Object object = this.css.styleConstantsValueToCSSValue((StyleConstants)paramObject1, paramObject2);
/*  570 */       if (object != null) {
/*      */         
/*  572 */         CSS.Attribute attribute = this.css.styleConstantsKeyToCSSKey((StyleConstants)paramObject1);
/*  573 */         if (attribute != null) {
/*  574 */           return super.addAttribute(paramAttributeSet, attribute, object);
/*      */         }
/*      */       } 
/*      */     } 
/*  578 */     return super.addAttribute(paramAttributeSet, paramObject1, paramObject2);
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
/*      */   public AttributeSet addAttributes(AttributeSet paramAttributeSet1, AttributeSet paramAttributeSet2) {
/*  592 */     if (!(paramAttributeSet2 instanceof HTMLDocument.TaggedAttributeSet)) {
/*  593 */       paramAttributeSet1 = removeHTMLTags(paramAttributeSet1, paramAttributeSet2);
/*      */     }
/*  595 */     return super.addAttributes(paramAttributeSet1, convertAttributeSet(paramAttributeSet2));
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
/*      */   public AttributeSet removeAttribute(AttributeSet paramAttributeSet, Object paramObject) {
/*  609 */     if (paramObject instanceof StyleConstants) {
/*  610 */       HTML.Tag tag = HTML.getTagForStyleConstantsKey((StyleConstants)paramObject);
/*      */       
/*  612 */       if (tag != null) {
/*  613 */         paramAttributeSet = super.removeAttribute(paramAttributeSet, tag);
/*      */       }
/*      */       
/*  616 */       CSS.Attribute attribute = this.css.styleConstantsKeyToCSSKey((StyleConstants)paramObject);
/*  617 */       if (attribute != null) {
/*  618 */         return super.removeAttribute(paramAttributeSet, attribute);
/*      */       }
/*      */     } 
/*  621 */     return super.removeAttribute(paramAttributeSet, paramObject);
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
/*      */   public AttributeSet removeAttributes(AttributeSet paramAttributeSet, Enumeration<?> paramEnumeration) {
/*  638 */     return super.removeAttributes(paramAttributeSet, paramEnumeration);
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
/*      */   public AttributeSet removeAttributes(AttributeSet paramAttributeSet1, AttributeSet paramAttributeSet2) {
/*  652 */     if (paramAttributeSet1 != paramAttributeSet2) {
/*  653 */       paramAttributeSet1 = removeHTMLTags(paramAttributeSet1, paramAttributeSet2);
/*      */     }
/*  655 */     return super.removeAttributes(paramAttributeSet1, convertAttributeSet(paramAttributeSet2));
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
/*      */   protected StyleContext.SmallAttributeSet createSmallAttributeSet(AttributeSet paramAttributeSet) {
/*  669 */     return new SmallConversionSet(paramAttributeSet);
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
/*      */   protected MutableAttributeSet createLargeAttributeSet(AttributeSet paramAttributeSet) {
/*  685 */     return new LargeConversionSet(paramAttributeSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeSet removeHTMLTags(AttributeSet paramAttributeSet1, AttributeSet paramAttributeSet2) {
/*  693 */     if (!(paramAttributeSet2 instanceof LargeConversionSet) && !(paramAttributeSet2 instanceof SmallConversionSet)) {
/*      */       
/*  695 */       Enumeration<?> enumeration = paramAttributeSet2.getAttributeNames();
/*      */       
/*  697 */       while (enumeration.hasMoreElements()) {
/*  698 */         Object object = enumeration.nextElement();
/*      */         
/*  700 */         if (object instanceof StyleConstants) {
/*  701 */           HTML.Tag tag = HTML.getTagForStyleConstantsKey((StyleConstants)object);
/*      */ 
/*      */           
/*  704 */           if (tag != null && paramAttributeSet1.isDefined(tag)) {
/*  705 */             paramAttributeSet1 = super.removeAttribute(paramAttributeSet1, tag);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  710 */     return paramAttributeSet1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   AttributeSet convertAttributeSet(AttributeSet paramAttributeSet) {
/*  720 */     if (paramAttributeSet instanceof LargeConversionSet || paramAttributeSet instanceof SmallConversionSet)
/*      */     {
/*      */       
/*  723 */       return paramAttributeSet;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  728 */     Enumeration<?> enumeration = paramAttributeSet.getAttributeNames();
/*  729 */     while (enumeration.hasMoreElements()) {
/*  730 */       Object object = enumeration.nextElement();
/*  731 */       if (object instanceof StyleConstants) {
/*      */ 
/*      */         
/*  734 */         LargeConversionSet largeConversionSet = new LargeConversionSet();
/*  735 */         Enumeration<?> enumeration1 = paramAttributeSet.getAttributeNames();
/*  736 */         while (enumeration1.hasMoreElements()) {
/*  737 */           Object object1 = enumeration1.nextElement();
/*  738 */           Object object2 = null;
/*  739 */           if (object1 instanceof StyleConstants) {
/*      */ 
/*      */             
/*  742 */             CSS.Attribute attribute = this.css.styleConstantsKeyToCSSKey((StyleConstants)object1);
/*  743 */             if (attribute != null) {
/*  744 */               Object object3 = paramAttributeSet.getAttribute(object1);
/*      */               
/*  746 */               object2 = this.css.styleConstantsValueToCSSValue((StyleConstants)object1, object3);
/*  747 */               if (object2 != null) {
/*  748 */                 largeConversionSet.addAttribute(attribute, object2);
/*      */               }
/*      */             } 
/*      */           } 
/*  752 */           if (object2 == null) {
/*  753 */             largeConversionSet.addAttribute(object1, paramAttributeSet.getAttribute(object1));
/*      */           }
/*      */         } 
/*  756 */         return largeConversionSet;
/*      */       } 
/*      */     } 
/*  759 */     return paramAttributeSet;
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
/*      */   class LargeConversionSet
/*      */     extends SimpleAttributeSet
/*      */   {
/*      */     public LargeConversionSet(AttributeSet param1AttributeSet) {
/*  774 */       super(param1AttributeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LargeConversionSet() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isDefined(Object param1Object) {
/*  789 */       if (param1Object instanceof StyleConstants) {
/*      */         
/*  791 */         CSS.Attribute attribute = StyleSheet.this.css.styleConstantsKeyToCSSKey((StyleConstants)param1Object);
/*  792 */         if (attribute != null) {
/*  793 */           return super.isDefined(attribute);
/*      */         }
/*      */       } 
/*  796 */       return super.isDefined(param1Object);
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
/*  807 */       if (param1Object instanceof StyleConstants) {
/*      */         
/*  809 */         CSS.Attribute attribute = StyleSheet.this.css.styleConstantsKeyToCSSKey((StyleConstants)param1Object);
/*  810 */         if (attribute != null) {
/*  811 */           Object object = super.getAttribute(attribute);
/*  812 */           if (object != null) {
/*  813 */             return StyleSheet.this.css
/*  814 */               .cssValueToStyleConstantsValue((StyleConstants)param1Object, object);
/*      */           }
/*      */         } 
/*      */       } 
/*  818 */       return super.getAttribute(param1Object);
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
/*      */   class SmallConversionSet
/*      */     extends StyleContext.SmallAttributeSet
/*      */   {
/*      */     public SmallConversionSet(AttributeSet param1AttributeSet) {
/*  834 */       super(param1AttributeSet);
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
/*  845 */       if (param1Object instanceof StyleConstants) {
/*      */         
/*  847 */         CSS.Attribute attribute = StyleSheet.this.css.styleConstantsKeyToCSSKey((StyleConstants)param1Object);
/*  848 */         if (attribute != null) {
/*  849 */           return super.isDefined(attribute);
/*      */         }
/*      */       } 
/*  852 */       return super.isDefined(param1Object);
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
/*  863 */       if (param1Object instanceof StyleConstants) {
/*      */         
/*  865 */         CSS.Attribute attribute = StyleSheet.this.css.styleConstantsKeyToCSSKey((StyleConstants)param1Object);
/*  866 */         if (attribute != null) {
/*  867 */           Object object = super.getAttribute(attribute);
/*  868 */           if (object != null) {
/*  869 */             return StyleSheet.this.css
/*  870 */               .cssValueToStyleConstantsValue((StyleConstants)param1Object, object);
/*      */           }
/*      */         } 
/*      */       } 
/*  874 */       return super.getAttribute(param1Object);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont(AttributeSet paramAttributeSet) {
/*  884 */     return this.css.getFont(this, paramAttributeSet, 12, this);
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
/*      */   public Color getForeground(AttributeSet paramAttributeSet) {
/*  896 */     Color color = this.css.getColor(paramAttributeSet, CSS.Attribute.COLOR);
/*  897 */     if (color == null) {
/*  898 */       return Color.black;
/*      */     }
/*  900 */     return color;
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
/*      */   public Color getBackground(AttributeSet paramAttributeSet) {
/*  912 */     return this.css.getColor(paramAttributeSet, CSS.Attribute.BACKGROUND_COLOR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BoxPainter getBoxPainter(AttributeSet paramAttributeSet) {
/*  920 */     return new BoxPainter(paramAttributeSet, this.css, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListPainter getListPainter(AttributeSet paramAttributeSet) {
/*  928 */     return new ListPainter(paramAttributeSet, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseFontSize(int paramInt) {
/*  935 */     this.css.setBaseFontSize(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseFontSize(String paramString) {
/*  944 */     this.css.setBaseFontSize(paramString);
/*      */   }
/*      */   
/*      */   public static int getIndexOfSize(float paramFloat) {
/*  948 */     return CSS.getIndexOfSize(paramFloat, sizeMapDefault);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getPointSize(int paramInt) {
/*  955 */     return this.css.getPointSize(paramInt, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getPointSize(String paramString) {
/*  963 */     return this.css.getPointSize(paramString, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color stringToColor(String paramString) {
/*  973 */     return CSS.stringToColor(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ImageIcon getBackgroundImage(AttributeSet paramAttributeSet) {
/*  981 */     Object object = paramAttributeSet.getAttribute(CSS.Attribute.BACKGROUND_IMAGE);
/*      */     
/*  983 */     if (object != null) {
/*  984 */       return ((CSS.BackgroundImage)object).getImage(getBase());
/*      */     }
/*  986 */     return null;
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
/*      */   void addRule(String[] paramArrayOfString, AttributeSet paramAttributeSet, boolean paramBoolean) {
/* 1000 */     int i = paramArrayOfString.length;
/* 1001 */     StringBuilder stringBuilder = new StringBuilder();
/* 1002 */     stringBuilder.append(paramArrayOfString[0]);
/* 1003 */     for (byte b = 1; b < i; b++) {
/* 1004 */       stringBuilder.append(' ');
/* 1005 */       stringBuilder.append(paramArrayOfString[b]);
/*      */     } 
/* 1007 */     String str = stringBuilder.toString();
/* 1008 */     Style style = getStyle(str);
/* 1009 */     if (style == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1015 */       Style style1 = addStyle(str, null);
/* 1016 */       synchronized (this) {
/* 1017 */         SelectorMapping selectorMapping = getRootSelectorMapping();
/* 1018 */         for (int j = i - 1; j >= 0; j--)
/*      */         {
/* 1020 */           selectorMapping = selectorMapping.getChildSelectorMapping(paramArrayOfString[j], true);
/*      */         }
/* 1022 */         style = selectorMapping.getStyle();
/* 1023 */         if (style == null) {
/* 1024 */           style = style1;
/* 1025 */           selectorMapping.setStyle(style);
/* 1026 */           refreshResolvedRules(str, paramArrayOfString, style, selectorMapping
/* 1027 */               .getSpecificity());
/*      */         } 
/*      */       } 
/*      */     } 
/* 1031 */     if (paramBoolean) {
/* 1032 */       style = getLinkedStyle(style);
/*      */     }
/* 1034 */     style.addAttributes(paramAttributeSet);
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
/*      */   private synchronized void linkStyleSheetAt(StyleSheet paramStyleSheet, int paramInt) {
/* 1047 */     if (this.resolvedStyles.size() > 0) {
/* 1048 */       Enumeration<ResolvedStyle> enumeration = this.resolvedStyles.elements();
/* 1049 */       while (enumeration.hasMoreElements()) {
/* 1050 */         ResolvedStyle resolvedStyle = enumeration.nextElement();
/* 1051 */         resolvedStyle.insertExtendedStyleAt(paramStyleSheet.getRule(resolvedStyle.getName()), paramInt);
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
/*      */   private synchronized void unlinkStyleSheet(StyleSheet paramStyleSheet, int paramInt) {
/* 1063 */     if (this.resolvedStyles.size() > 0) {
/* 1064 */       Enumeration<ResolvedStyle> enumeration = this.resolvedStyles.elements();
/* 1065 */       while (enumeration.hasMoreElements()) {
/* 1066 */         ResolvedStyle resolvedStyle = enumeration.nextElement();
/* 1067 */         resolvedStyle.removeExtendedStyleAt(paramInt);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String[] getSimpleSelectors(String paramString) {
/* 1077 */     paramString = cleanSelectorString(paramString);
/* 1078 */     SearchBuffer searchBuffer = SearchBuffer.obtainSearchBuffer();
/* 1079 */     Vector<String> vector = searchBuffer.getVector();
/* 1080 */     int i = 0;
/* 1081 */     int j = paramString.length();
/* 1082 */     while (i != -1) {
/* 1083 */       int k = paramString.indexOf(' ', i);
/* 1084 */       if (k != -1) {
/* 1085 */         vector.addElement(paramString.substring(i, k));
/* 1086 */         if (++k == j) {
/* 1087 */           i = -1;
/*      */           continue;
/*      */         } 
/* 1090 */         i = k;
/*      */         
/*      */         continue;
/*      */       } 
/* 1094 */       vector.addElement(paramString.substring(i));
/* 1095 */       i = -1;
/*      */     } 
/*      */     
/* 1098 */     String[] arrayOfString = new String[vector.size()];
/* 1099 */     vector.copyInto((Object[])arrayOfString);
/* 1100 */     SearchBuffer.releaseSearchBuffer(searchBuffer);
/* 1101 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String cleanSelectorString(String paramString) {
/* 1109 */     boolean bool = true;
/* 1110 */     byte b = 0; int i = paramString.length();
/* 1111 */     for (; b < i; b++) {
/* 1112 */       switch (paramString.charAt(b)) {
/*      */         case ' ':
/* 1114 */           if (bool) {
/* 1115 */             return _cleanSelectorString(paramString);
/*      */           }
/* 1117 */           bool = true;
/*      */           break;
/*      */         case '\t':
/*      */         case '\n':
/*      */         case '\r':
/* 1122 */           return _cleanSelectorString(paramString);
/*      */         default:
/* 1124 */           bool = false; break;
/*      */       } 
/*      */     } 
/* 1127 */     if (bool) {
/* 1128 */       return _cleanSelectorString(paramString);
/*      */     }
/*      */     
/* 1131 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String _cleanSelectorString(String paramString) {
/* 1139 */     SearchBuffer searchBuffer = SearchBuffer.obtainSearchBuffer();
/* 1140 */     StringBuffer stringBuffer = searchBuffer.getStringBuffer();
/* 1141 */     boolean bool = true;
/* 1142 */     int i = 0;
/* 1143 */     char[] arrayOfChar = paramString.toCharArray();
/* 1144 */     int j = arrayOfChar.length;
/* 1145 */     String str = null;
/*      */     try {
/* 1147 */       for (byte b = 0; b < j; b++) {
/* 1148 */         switch (arrayOfChar[b]) {
/*      */           case ' ':
/* 1150 */             if (!bool) {
/* 1151 */               bool = true;
/* 1152 */               if (i < b) {
/* 1153 */                 stringBuffer.append(arrayOfChar, i, 1 + b - i);
/*      */               }
/*      */             } 
/*      */             
/* 1157 */             i = b + 1;
/*      */             break;
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\r':
/* 1162 */             if (!bool) {
/* 1163 */               bool = true;
/* 1164 */               if (i < b) {
/* 1165 */                 stringBuffer.append(arrayOfChar, i, b - i);
/*      */                 
/* 1167 */                 stringBuffer.append(' ');
/*      */               } 
/*      */             } 
/* 1170 */             i = b + 1;
/*      */             break;
/*      */           default:
/* 1173 */             bool = false;
/*      */             break;
/*      */         } 
/*      */       } 
/* 1177 */       if (bool && stringBuffer.length() > 0) {
/*      */         
/* 1179 */         stringBuffer.setLength(stringBuffer.length() - 1);
/*      */       }
/* 1181 */       else if (i < j) {
/* 1182 */         stringBuffer.append(arrayOfChar, i, j - i);
/*      */       } 
/* 1184 */       str = stringBuffer.toString();
/*      */     } finally {
/*      */       
/* 1187 */       SearchBuffer.releaseSearchBuffer(searchBuffer);
/*      */     } 
/* 1189 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SelectorMapping getRootSelectorMapping() {
/* 1197 */     return this.selectorMapping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int getSpecificity(String paramString) {
/* 1208 */     byte b1 = 0;
/* 1209 */     boolean bool = true;
/*      */     
/* 1211 */     byte b2 = 0; int i = paramString.length();
/* 1212 */     for (; b2 < i; b2++) {
/* 1213 */       switch (paramString.charAt(b2)) {
/*      */         case '.':
/* 1215 */           b1 += true;
/*      */           break;
/*      */         case '#':
/* 1218 */           b1 += true;
/*      */           break;
/*      */         case ' ':
/* 1221 */           bool = true;
/*      */           break;
/*      */         default:
/* 1224 */           if (bool) {
/* 1225 */             bool = false;
/* 1226 */             b1++;
/*      */           }  break;
/*      */       } 
/*      */     } 
/* 1230 */     return b1;
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
/*      */   private Style getLinkedStyle(Style paramStyle) {
/* 1244 */     Style style = (Style)paramStyle.getResolveParent();
/* 1245 */     if (style == null) {
/* 1246 */       style = addStyle(null, null);
/* 1247 */       paramStyle.setResolveParent(style);
/*      */     } 
/* 1249 */     return style;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized Style getResolvedStyle(String paramString, Vector paramVector, HTML.Tag paramTag) {
/* 1259 */     Style style = this.resolvedStyles.get(paramString);
/* 1260 */     if (style == null) {
/* 1261 */       style = createResolvedStyle(paramString, paramVector, paramTag);
/*      */     }
/* 1263 */     return style;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized Style getResolvedStyle(String paramString) {
/* 1271 */     Style style = this.resolvedStyles.get(paramString);
/* 1272 */     if (style == null) {
/* 1273 */       style = createResolvedStyle(paramString);
/*      */     }
/* 1275 */     return style;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addSortedStyle(SelectorMapping paramSelectorMapping, Vector<SelectorMapping> paramVector) {
/* 1284 */     int i = paramVector.size();
/*      */     
/* 1286 */     if (i > 0) {
/* 1287 */       int j = paramSelectorMapping.getSpecificity();
/*      */       
/* 1289 */       for (byte b = 0; b < i; b++) {
/* 1290 */         if (j >= ((SelectorMapping)paramVector.elementAt(b)).getSpecificity()) {
/* 1291 */           paramVector.insertElementAt(paramSelectorMapping, b);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1296 */     paramVector.addElement(paramSelectorMapping);
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
/*      */   private synchronized void getStyles(SelectorMapping paramSelectorMapping, Vector<SelectorMapping> paramVector, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, int paramInt1, int paramInt2, Hashtable<SelectorMapping, SelectorMapping> paramHashtable) {
/* 1310 */     if (paramHashtable.contains(paramSelectorMapping)) {
/*      */       return;
/*      */     }
/* 1313 */     paramHashtable.put(paramSelectorMapping, paramSelectorMapping);
/* 1314 */     Style style = paramSelectorMapping.getStyle();
/* 1315 */     if (style != null) {
/* 1316 */       addSortedStyle(paramSelectorMapping, paramVector);
/*      */     }
/* 1318 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 1319 */       String str = paramArrayOfString1[i];
/* 1320 */       if (str != null) {
/*      */         
/* 1322 */         SelectorMapping selectorMapping = paramSelectorMapping.getChildSelectorMapping(str, false);
/* 1323 */         if (selectorMapping != null) {
/* 1324 */           getStyles(selectorMapping, paramVector, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, i + 1, paramInt2, paramHashtable);
/*      */         }
/*      */         
/* 1327 */         if (paramArrayOfString3[i] != null) {
/* 1328 */           String str1 = paramArrayOfString3[i];
/* 1329 */           selectorMapping = paramSelectorMapping.getChildSelectorMapping(str + "." + str1, false);
/*      */           
/* 1331 */           if (selectorMapping != null) {
/* 1332 */             getStyles(selectorMapping, paramVector, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, i + 1, paramInt2, paramHashtable);
/*      */           }
/*      */           
/* 1335 */           selectorMapping = paramSelectorMapping.getChildSelectorMapping("." + str1, false);
/*      */           
/* 1337 */           if (selectorMapping != null) {
/* 1338 */             getStyles(selectorMapping, paramVector, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, i + 1, paramInt2, paramHashtable);
/*      */           }
/*      */         } 
/*      */         
/* 1342 */         if (paramArrayOfString2[i] != null) {
/* 1343 */           String str1 = paramArrayOfString2[i];
/* 1344 */           selectorMapping = paramSelectorMapping.getChildSelectorMapping(str + "#" + str1, false);
/*      */           
/* 1346 */           if (selectorMapping != null) {
/* 1347 */             getStyles(selectorMapping, paramVector, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, i + 1, paramInt2, paramHashtable);
/*      */           }
/*      */           
/* 1350 */           selectorMapping = paramSelectorMapping.getChildSelectorMapping("#" + str1, false);
/*      */           
/* 1352 */           if (selectorMapping != null) {
/* 1353 */             getStyles(selectorMapping, paramVector, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, i + 1, paramInt2, paramHashtable);
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
/*      */   private synchronized Style createResolvedStyle(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) {
/* 1368 */     SearchBuffer searchBuffer = SearchBuffer.obtainSearchBuffer();
/* 1369 */     Vector<SelectorMapping> vector = searchBuffer.getVector();
/* 1370 */     Hashtable<SelectorMapping, SelectorMapping> hashtable = searchBuffer.getHashtable();
/*      */ 
/*      */     
/*      */     try {
/* 1374 */       SelectorMapping selectorMapping1 = getRootSelectorMapping();
/* 1375 */       int i = paramArrayOfString1.length;
/* 1376 */       String str = paramArrayOfString1[0];
/* 1377 */       SelectorMapping selectorMapping2 = selectorMapping1.getChildSelectorMapping(str, false);
/*      */       
/* 1379 */       if (selectorMapping2 != null) {
/* 1380 */         getStyles(selectorMapping2, vector, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, 1, i, hashtable);
/*      */       }
/*      */       
/* 1383 */       if (paramArrayOfString3[0] != null) {
/* 1384 */         String str1 = paramArrayOfString3[0];
/* 1385 */         selectorMapping2 = selectorMapping1.getChildSelectorMapping(str + "." + str1, false);
/*      */         
/* 1387 */         if (selectorMapping2 != null) {
/* 1388 */           getStyles(selectorMapping2, vector, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, 1, i, hashtable);
/*      */         }
/*      */         
/* 1391 */         selectorMapping2 = selectorMapping1.getChildSelectorMapping("." + str1, false);
/*      */         
/* 1393 */         if (selectorMapping2 != null) {
/* 1394 */           getStyles(selectorMapping2, vector, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, 1, i, hashtable);
/*      */         }
/*      */       } 
/*      */       
/* 1398 */       if (paramArrayOfString2[0] != null) {
/* 1399 */         String str1 = paramArrayOfString2[0];
/* 1400 */         selectorMapping2 = selectorMapping1.getChildSelectorMapping(str + "#" + str1, false);
/*      */         
/* 1402 */         if (selectorMapping2 != null) {
/* 1403 */           getStyles(selectorMapping2, vector, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, 1, i, hashtable);
/*      */         }
/*      */         
/* 1406 */         selectorMapping2 = selectorMapping1.getChildSelectorMapping("#" + str1, false);
/*      */         
/* 1408 */         if (selectorMapping2 != null) {
/* 1409 */           getStyles(selectorMapping2, vector, paramArrayOfString1, paramArrayOfString2, paramArrayOfString3, 1, i, hashtable);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1416 */       byte b1 = (this.linkedStyleSheets != null) ? this.linkedStyleSheets.size() : 0;
/* 1417 */       int j = vector.size();
/* 1418 */       AttributeSet[] arrayOfAttributeSet = new AttributeSet[j + b1]; byte b2;
/* 1419 */       for (b2 = 0; b2 < j; b2++) {
/* 1420 */         arrayOfAttributeSet[b2] = ((SelectorMapping)vector.elementAt(b2)).getStyle();
/*      */       }
/*      */       
/* 1423 */       for (b2 = 0; b2 < b1; b2++) {
/* 1424 */         Style style = ((StyleSheet)this.linkedStyleSheets.elementAt(b2)).getRule(paramString);
/* 1425 */         if (style == null) {
/* 1426 */           arrayOfAttributeSet[b2 + j] = SimpleAttributeSet.EMPTY;
/*      */         } else {
/*      */           
/* 1429 */           arrayOfAttributeSet[b2 + j] = style;
/*      */         } 
/*      */       } 
/* 1432 */       ResolvedStyle resolvedStyle = new ResolvedStyle(paramString, arrayOfAttributeSet, j);
/*      */       
/* 1434 */       this.resolvedStyles.put(paramString, resolvedStyle);
/* 1435 */       return resolvedStyle;
/*      */     } finally {
/*      */       
/* 1438 */       SearchBuffer.releaseSearchBuffer(searchBuffer);
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
/*      */   private Style createResolvedStyle(String paramString, Vector<Element> paramVector, HTML.Tag paramTag) {
/* 1455 */     int i = paramVector.size();
/*      */ 
/*      */     
/* 1458 */     String[] arrayOfString1 = new String[i];
/* 1459 */     String[] arrayOfString2 = new String[i];
/* 1460 */     String[] arrayOfString3 = new String[i];
/* 1461 */     for (byte b = 0; b < i; b++) {
/* 1462 */       Element element = paramVector.elementAt(b);
/* 1463 */       AttributeSet attributeSet = element.getAttributes();
/* 1464 */       if (b == 0 && element.isLeaf()) {
/*      */         
/* 1466 */         Object object = attributeSet.getAttribute(paramTag);
/* 1467 */         if (object instanceof AttributeSet) {
/* 1468 */           attributeSet = (AttributeSet)object;
/*      */         } else {
/*      */           
/* 1471 */           attributeSet = null;
/*      */         } 
/*      */       } 
/* 1474 */       if (attributeSet != null) {
/* 1475 */         HTML.Tag tag = (HTML.Tag)attributeSet.getAttribute(StyleConstants.NameAttribute);
/*      */         
/* 1477 */         if (tag != null) {
/* 1478 */           arrayOfString1[b] = tag.toString();
/*      */         } else {
/*      */           
/* 1481 */           arrayOfString1[b] = null;
/*      */         } 
/* 1483 */         if (attributeSet.isDefined(HTML.Attribute.CLASS)) {
/* 1484 */           arrayOfString3[b] = attributeSet
/* 1485 */             .getAttribute(HTML.Attribute.CLASS).toString();
/*      */         } else {
/*      */           
/* 1488 */           arrayOfString3[b] = null;
/*      */         } 
/* 1490 */         if (attributeSet.isDefined(HTML.Attribute.ID)) {
/* 1491 */           arrayOfString2[b] = attributeSet.getAttribute(HTML.Attribute.ID)
/* 1492 */             .toString();
/*      */         } else {
/*      */           
/* 1495 */           arrayOfString2[b] = null;
/*      */         } 
/*      */       } else {
/*      */         
/* 1499 */         arrayOfString3[b] = null; arrayOfString2[b] = null; arrayOfString1[b] = null;
/*      */       } 
/*      */     } 
/* 1502 */     arrayOfString1[0] = paramTag.toString();
/* 1503 */     return createResolvedStyle(paramString, arrayOfString1, arrayOfString2, arrayOfString3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Style createResolvedStyle(String paramString) {
/* 1512 */     SearchBuffer searchBuffer = SearchBuffer.obtainSearchBuffer();
/*      */     
/* 1514 */     Vector<String> vector = searchBuffer.getVector();
/*      */     
/*      */     try {
/* 1517 */       int i = 0;
/*      */       
/* 1519 */       int j = 0;
/* 1520 */       int k = 0;
/* 1521 */       int m = paramString.length();
/* 1522 */       while (k < m) {
/* 1523 */         if (i == k) {
/* 1524 */           i = paramString.indexOf('.', k);
/*      */         }
/* 1526 */         if (j == k) {
/* 1527 */           j = paramString.indexOf('#', k);
/*      */         }
/* 1529 */         int i3 = paramString.indexOf(' ', k);
/* 1530 */         if (i3 == -1) {
/* 1531 */           i3 = m;
/*      */         }
/* 1533 */         if (i != -1 && j != -1 && i < i3 && j < i3) {
/*      */           
/* 1535 */           if (j < i) {
/*      */             
/* 1537 */             if (k == j) {
/* 1538 */               vector.addElement("");
/*      */             } else {
/*      */               
/* 1541 */               vector.addElement(paramString.substring(k, j));
/*      */             } 
/*      */             
/* 1544 */             if (i + 1 < i3) {
/* 1545 */               vector.addElement(paramString
/* 1546 */                   .substring(i + 1, i3));
/*      */             } else {
/*      */               
/* 1549 */               vector.addElement(null);
/*      */             } 
/* 1551 */             if (j + 1 == i) {
/* 1552 */               vector.addElement(null);
/*      */             } else {
/*      */               
/* 1555 */               vector.addElement(paramString
/* 1556 */                   .substring(j + 1, i));
/*      */             }
/*      */           
/* 1559 */           } else if (j < i3) {
/*      */             
/* 1561 */             if (k == i) {
/* 1562 */               vector.addElement("");
/*      */             } else {
/*      */               
/* 1565 */               vector.addElement(paramString.substring(k, i));
/*      */             } 
/*      */             
/* 1568 */             if (i + 1 < j) {
/* 1569 */               vector.addElement(paramString
/* 1570 */                   .substring(i + 1, j));
/*      */             } else {
/*      */               
/* 1573 */               vector.addElement(null);
/*      */             } 
/* 1575 */             if (j + 1 == i3) {
/* 1576 */               vector.addElement(null);
/*      */             } else {
/*      */               
/* 1579 */               vector.addElement(paramString
/* 1580 */                   .substring(j + 1, i3));
/*      */             } 
/*      */           } 
/* 1583 */           i = j = i3 + 1;
/*      */         }
/* 1585 */         else if (i != -1 && i < i3) {
/*      */           
/* 1587 */           if (i == k) {
/* 1588 */             vector.addElement("");
/*      */           } else {
/*      */             
/* 1591 */             vector.addElement(paramString.substring(k, i));
/*      */           } 
/*      */           
/* 1594 */           if (i + 1 == i3) {
/* 1595 */             vector.addElement(null);
/*      */           } else {
/*      */             
/* 1598 */             vector.addElement(paramString.substring(i + 1, i3));
/*      */           } 
/*      */           
/* 1601 */           vector.addElement(null);
/* 1602 */           i = i3 + 1;
/*      */         }
/* 1604 */         else if (j != -1 && j < i3) {
/*      */           
/* 1606 */           if (j == k) {
/* 1607 */             vector.addElement("");
/*      */           } else {
/*      */             
/* 1610 */             vector.addElement(paramString.substring(k, j));
/*      */           } 
/*      */           
/* 1613 */           vector.addElement(null);
/* 1614 */           if (j + 1 == i3) {
/* 1615 */             vector.addElement(null);
/*      */           } else {
/*      */             
/* 1618 */             vector.addElement(paramString.substring(j + 1, i3));
/*      */           } 
/*      */           
/* 1621 */           j = i3 + 1;
/*      */         }
/*      */         else {
/*      */           
/* 1625 */           vector.addElement(paramString.substring(k, i3));
/*      */           
/* 1627 */           vector.addElement(null);
/* 1628 */           vector.addElement(null);
/*      */         } 
/* 1630 */         k = i3 + 1;
/*      */       } 
/*      */       
/* 1633 */       int n = vector.size();
/* 1634 */       int i1 = n / 3;
/* 1635 */       String[] arrayOfString1 = new String[i1];
/* 1636 */       String[] arrayOfString2 = new String[i1];
/* 1637 */       String[] arrayOfString3 = new String[i1]; byte b; int i2;
/* 1638 */       for (b = 0, i2 = n - 3; b < i1; 
/* 1639 */         b++, i2 -= 3) {
/* 1640 */         arrayOfString1[b] = vector.elementAt(i2);
/* 1641 */         arrayOfString3[b] = vector.elementAt(i2 + 1);
/* 1642 */         arrayOfString2[b] = vector.elementAt(i2 + 2);
/*      */       } 
/* 1644 */       return createResolvedStyle(paramString, arrayOfString1, arrayOfString2, arrayOfString3);
/*      */     } finally {
/*      */       
/* 1647 */       SearchBuffer.releaseSearchBuffer(searchBuffer);
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
/*      */   private synchronized void refreshResolvedRules(String paramString, String[] paramArrayOfString, Style paramStyle, int paramInt) {
/* 1660 */     if (this.resolvedStyles.size() > 0) {
/* 1661 */       Enumeration<ResolvedStyle> enumeration = this.resolvedStyles.elements();
/* 1662 */       while (enumeration.hasMoreElements()) {
/* 1663 */         ResolvedStyle resolvedStyle = enumeration.nextElement();
/* 1664 */         if (resolvedStyle.matches(paramString)) {
/* 1665 */           resolvedStyle.insertStyle(paramStyle, paramInt);
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
/*      */   
/*      */   private static class SearchBuffer
/*      */   {
/* 1682 */     static Stack<SearchBuffer> searchBuffers = new Stack<>();
/*      */     
/* 1684 */     Vector vector = null;
/* 1685 */     StringBuffer stringBuffer = null;
/* 1686 */     Hashtable hashtable = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static SearchBuffer obtainSearchBuffer() {
/*      */       SearchBuffer searchBuffer;
/*      */       try {
/* 1695 */         if (!searchBuffers.empty()) {
/* 1696 */           searchBuffer = searchBuffers.pop();
/*      */         } else {
/* 1698 */           searchBuffer = new SearchBuffer();
/*      */         } 
/* 1700 */       } catch (EmptyStackException emptyStackException) {
/* 1701 */         searchBuffer = new SearchBuffer();
/*      */       } 
/* 1703 */       return searchBuffer;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static void releaseSearchBuffer(SearchBuffer param1SearchBuffer) {
/* 1711 */       param1SearchBuffer.empty();
/* 1712 */       searchBuffers.push(param1SearchBuffer);
/*      */     }
/*      */     
/*      */     StringBuffer getStringBuffer() {
/* 1716 */       if (this.stringBuffer == null) {
/* 1717 */         this.stringBuffer = new StringBuffer();
/*      */       }
/* 1719 */       return this.stringBuffer;
/*      */     }
/*      */     
/*      */     Vector getVector() {
/* 1723 */       if (this.vector == null) {
/* 1724 */         this.vector = new Vector();
/*      */       }
/* 1726 */       return this.vector;
/*      */     }
/*      */     
/*      */     Hashtable getHashtable() {
/* 1730 */       if (this.hashtable == null) {
/* 1731 */         this.hashtable = new Hashtable<>();
/*      */       }
/* 1733 */       return this.hashtable;
/*      */     }
/*      */     
/*      */     void empty() {
/* 1737 */       if (this.stringBuffer != null) {
/* 1738 */         this.stringBuffer.setLength(0);
/*      */       }
/* 1740 */       if (this.vector != null) {
/* 1741 */         this.vector.removeAllElements();
/*      */       }
/* 1743 */       if (this.hashtable != null) {
/* 1744 */         this.hashtable.clear();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 1750 */   static final Border noBorder = new EmptyBorder(0, 0, 0, 0);
/*      */   
/*      */   public static class BoxPainter implements Serializable {
/*      */     float topMargin;
/*      */     float bottomMargin;
/*      */     float leftMargin;
/*      */     float rightMargin;
/*      */     short marginFlags;
/*      */     Border border;
/*      */     Insets binsets;
/*      */     CSS css;
/*      */     StyleSheet ss;
/*      */     Color bg;
/*      */     StyleSheet.BackgroundImagePainter bgPainter;
/*      */     
/*      */     BoxPainter(AttributeSet param1AttributeSet, CSS param1CSS, StyleSheet param1StyleSheet) {
/* 1766 */       this.ss = param1StyleSheet;
/* 1767 */       this.css = param1CSS;
/* 1768 */       this.border = getBorder(param1AttributeSet);
/* 1769 */       this.binsets = this.border.getBorderInsets(null);
/* 1770 */       this.topMargin = getLength(CSS.Attribute.MARGIN_TOP, param1AttributeSet);
/* 1771 */       this.bottomMargin = getLength(CSS.Attribute.MARGIN_BOTTOM, param1AttributeSet);
/* 1772 */       this.leftMargin = getLength(CSS.Attribute.MARGIN_LEFT, param1AttributeSet);
/* 1773 */       this.rightMargin = getLength(CSS.Attribute.MARGIN_RIGHT, param1AttributeSet);
/* 1774 */       this.bg = param1StyleSheet.getBackground(param1AttributeSet);
/* 1775 */       if (param1StyleSheet.getBackgroundImage(param1AttributeSet) != null) {
/* 1776 */         this.bgPainter = new StyleSheet.BackgroundImagePainter(param1AttributeSet, param1CSS, param1StyleSheet);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Border getBorder(AttributeSet param1AttributeSet) {
/* 1786 */       return new CSSBorder(param1AttributeSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Color getBorderColor(AttributeSet param1AttributeSet) {
/* 1796 */       Color color = this.css.getColor(param1AttributeSet, CSS.Attribute.BORDER_COLOR);
/* 1797 */       if (color == null) {
/* 1798 */         color = this.css.getColor(param1AttributeSet, CSS.Attribute.COLOR);
/* 1799 */         if (color == null) {
/* 1800 */           return Color.black;
/*      */         }
/*      */       } 
/* 1803 */       return color;
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
/*      */     public float getInset(int param1Int, View param1View) {
/* 1819 */       AttributeSet attributeSet = param1View.getAttributes();
/* 1820 */       float f = 0.0F;
/* 1821 */       switch (param1Int) {
/*      */         case 2:
/* 1823 */           f += getOrientationMargin(HorizontalMargin.LEFT, this.leftMargin, attributeSet, 
/* 1824 */               isLeftToRight(param1View));
/* 1825 */           f += this.binsets.left;
/* 1826 */           f += getLength(CSS.Attribute.PADDING_LEFT, attributeSet);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1847 */           return f;case 4: f += getOrientationMargin(HorizontalMargin.RIGHT, this.rightMargin, attributeSet, isLeftToRight(param1View)); f += this.binsets.right; f += getLength(CSS.Attribute.PADDING_RIGHT, attributeSet); return f;case 1: f += this.topMargin; f += this.binsets.top; f += getLength(CSS.Attribute.PADDING_TOP, attributeSet); return f;case 3: f += this.bottomMargin; f += this.binsets.bottom; f += getLength(CSS.Attribute.PADDING_BOTTOM, attributeSet); return f;
/*      */       } 
/*      */       throw new IllegalArgumentException("Invalid side: " + param1Int);
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paint(Graphics param1Graphics, float param1Float1, float param1Float2, float param1Float3, float param1Float4, View param1View) {
/* 1871 */       float f1 = 0.0F;
/* 1872 */       float f2 = 0.0F;
/* 1873 */       float f3 = 0.0F;
/* 1874 */       float f4 = 0.0F;
/* 1875 */       AttributeSet attributeSet = param1View.getAttributes();
/* 1876 */       boolean bool = isLeftToRight(param1View);
/* 1877 */       float f5 = getOrientationMargin(HorizontalMargin.LEFT, this.leftMargin, attributeSet, bool);
/*      */ 
/*      */       
/* 1880 */       float f6 = getOrientationMargin(HorizontalMargin.RIGHT, this.rightMargin, attributeSet, bool);
/*      */ 
/*      */       
/* 1883 */       if (!(param1View instanceof HTMLEditorKit.HTMLFactory.BodyBlockView)) {
/* 1884 */         f1 = f5;
/* 1885 */         f2 = this.topMargin;
/* 1886 */         f3 = -(f5 + f6);
/* 1887 */         f4 = -(this.topMargin + this.bottomMargin);
/*      */       } 
/* 1889 */       if (this.bg != null) {
/* 1890 */         param1Graphics.setColor(this.bg);
/* 1891 */         param1Graphics.fillRect((int)(param1Float1 + f1), (int)(param1Float2 + f2), (int)(param1Float3 + f3), (int)(param1Float4 + f4));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1896 */       if (this.bgPainter != null) {
/* 1897 */         this.bgPainter.paint(param1Graphics, param1Float1 + f1, param1Float2 + f2, param1Float3 + f3, param1Float4 + f4, param1View);
/*      */       }
/* 1899 */       param1Float1 += f5;
/* 1900 */       param1Float2 += this.topMargin;
/* 1901 */       param1Float3 -= f5 + f6;
/* 1902 */       param1Float4 -= this.topMargin + this.bottomMargin;
/* 1903 */       if (this.border instanceof javax.swing.border.BevelBorder) {
/*      */         
/* 1905 */         int i = (int)getLength(CSS.Attribute.BORDER_TOP_WIDTH, attributeSet);
/* 1906 */         for (int j = i - 1; j >= 0; j--) {
/* 1907 */           this.border.paintBorder(null, param1Graphics, (int)param1Float1 + j, (int)param1Float2 + j, (int)param1Float3 - 2 * j, (int)param1Float4 - 2 * j);
/*      */         }
/*      */       } else {
/*      */         
/* 1911 */         this.border.paintBorder(null, param1Graphics, (int)param1Float1, (int)param1Float2, (int)param1Float3, (int)param1Float4);
/*      */       } 
/*      */     }
/*      */     
/*      */     float getLength(CSS.Attribute param1Attribute, AttributeSet param1AttributeSet) {
/* 1916 */       return this.css.getLength(param1AttributeSet, param1Attribute, this.ss);
/*      */     }
/*      */     
/*      */     static boolean isLeftToRight(View param1View) {
/* 1920 */       boolean bool = true;
/* 1921 */       if (isOrientationAware(param1View)) {
/*      */         Container container;
/* 1923 */         if (param1View != null && (container = param1View.getContainer()) != null) {
/* 1924 */           bool = container.getComponentOrientation().isLeftToRight();
/*      */         }
/*      */       } 
/* 1927 */       return bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean isOrientationAware(View param1View) {
/* 1937 */       boolean bool = false;
/*      */       AttributeSet attributeSet;
/*      */       Object object;
/* 1940 */       if (param1View != null && (
/* 1941 */         attributeSet = param1View.getElement().getAttributes()) != null && 
/* 1942 */         object = attributeSet.getAttribute(StyleConstants.NameAttribute) instanceof HTML.Tag && (object == HTML.Tag.DIR || object == HTML.Tag.MENU || object == HTML.Tag.UL || object == HTML.Tag.OL))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1947 */         bool = true;
/*      */       }
/*      */       
/* 1950 */       return bool;
/*      */     }
/*      */     
/* 1953 */     enum HorizontalMargin { LEFT, RIGHT; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     float getOrientationMargin(HorizontalMargin param1HorizontalMargin, float param1Float, AttributeSet param1AttributeSet, boolean param1Boolean) {
/* 1971 */       float f1 = param1Float;
/* 1972 */       float f2 = param1Float;
/* 1973 */       Object object = null;
/* 1974 */       switch (param1HorizontalMargin) {
/*      */ 
/*      */ 
/*      */         
/*      */         case RIGHT:
/* 1979 */           f2 = param1Boolean ? getLength(CSS.Attribute.MARGIN_RIGHT_LTR, param1AttributeSet) : getLength(CSS.Attribute.MARGIN_RIGHT_RTL, param1AttributeSet);
/* 1980 */           object = param1AttributeSet.getAttribute(CSS.Attribute.MARGIN_RIGHT);
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case LEFT:
/* 1987 */           f2 = param1Boolean ? getLength(CSS.Attribute.MARGIN_LEFT_LTR, param1AttributeSet) : getLength(CSS.Attribute.MARGIN_LEFT_RTL, param1AttributeSet);
/* 1988 */           object = param1AttributeSet.getAttribute(CSS.Attribute.MARGIN_LEFT);
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1993 */       if (object == null && f2 != -2.14748365E9F)
/*      */       {
/* 1995 */         f1 = f2;
/*      */       }
/* 1997 */       return f1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ListPainter
/*      */     implements Serializable
/*      */   {
/*      */     ListPainter(AttributeSet param1AttributeSet, StyleSheet param1StyleSheet) {
/* 2441 */       this.ss = null;
/* 2442 */       this.img = null;
/* 2443 */       this.bulletgap = 5; this.ss = param1StyleSheet; String str = (String)param1AttributeSet.getAttribute(CSS.Attribute.LIST_STYLE_IMAGE); this.type = null; if (str != null && !str.equals("none")) { String str1 = null; try { StringTokenizer stringTokenizer = new StringTokenizer(str, "()"); if (stringTokenizer.hasMoreTokens()) str1 = stringTokenizer.nextToken();  if (stringTokenizer.hasMoreTokens()) str1 = stringTokenizer.nextToken();  URL uRL = new URL(str1); this.img = new ImageIcon(uRL); } catch (MalformedURLException malformedURLException) { if (str1 != null && param1StyleSheet != null && param1StyleSheet.getBase() != null) { try { URL uRL = new URL(param1StyleSheet.getBase(), str1); this.img = new ImageIcon(uRL); } catch (MalformedURLException malformedURLException1) { this.img = null; }  } else { this.img = null; }  }  }  if (this.img == null)
/*      */         this.type = (CSS.Value)param1AttributeSet.getAttribute(CSS.Attribute.LIST_STYLE_TYPE);  this.start = 1; this.paintRect = new Rectangle();
/*      */     }
/*      */     private CSS.Value getChildType(View param1View) { CSS.Value value = (CSS.Value)param1View.getAttributes().getAttribute(CSS.Attribute.LIST_STYLE_TYPE); if (value == null)
/*      */         if (this.type == null) { View view = param1View.getParent(); HTMLDocument hTMLDocument = (HTMLDocument)view.getDocument(); if (HTMLDocument.matchNameAttribute(view.getElement().getAttributes(), HTML.Tag.OL)) { value = CSS.Value.DECIMAL; } else { value = CSS.Value.DISC; }  } else { value = this.type; }   return value; }
/*      */     private void getStart(View param1View) { this.checkedForStart = true; Element element = param1View.getElement(); if (element != null) { AttributeSet attributeSet = element.getAttributes(); Object object; if (attributeSet != null && attributeSet.isDefined(HTML.Attribute.START) && (object = attributeSet.getAttribute(HTML.Attribute.START)) != null && object instanceof String)
/*      */           try { this.start = Integer.parseInt((String)object); } catch (NumberFormatException numberFormatException) {}  }  }
/*      */     private int getRenderIndex(View param1View, int param1Int) { if (!this.checkedForStart)
/*      */         getStart(param1View);  int i = param1Int; for (int j = param1Int; j >= 0; j--) { AttributeSet attributeSet = param1View.getElement().getElement(j).getAttributes(); if (attributeSet.getAttribute(StyleConstants.NameAttribute) != HTML.Tag.LI) { i--; } else if (attributeSet.isDefined(HTML.Attribute.VALUE)) { Object object = attributeSet.getAttribute(HTML.Attribute.VALUE); if (object != null && object instanceof String)
/*      */             try { int k = Integer.parseInt((String)object); return i - j + k; } catch (NumberFormatException numberFormatException) {}  }  }  return i + this.start; }
/*      */     public void paint(Graphics param1Graphics, float param1Float1, float param1Float2, float param1Float3, float param1Float4, View param1View, int param1Int) { View view = param1View.getView(param1Int); Container container = param1View.getContainer(); Object object = view.getElement().getAttributes().getAttribute(StyleConstants.NameAttribute); if (!(object instanceof HTML.Tag) || object != HTML.Tag.LI)
/*      */         return;  this.isLeftToRight = container.getComponentOrientation().isLeftToRight(); float f = 0.0F; if (view.getViewCount() > 0) { View view1 = view.getView(0); Object object1 = view1.getElement().getAttributes().getAttribute(StyleConstants.NameAttribute); if ((object1 == HTML.Tag.P || object1 == HTML.Tag.IMPLIED) && view1.getViewCount() > 0) { this.paintRect.setBounds((int)param1Float1, (int)param1Float2, (int)param1Float3, (int)param1Float4); Shape shape = view.getChildAllocation(0, this.paintRect); if (shape != null && (shape = view1.getView(0).getChildAllocation(0, shape)) != null) { Rectangle rectangle = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds(); f = view1.getView(0).getAlignment(1); param1Float2 = rectangle.y; param1Float4 = rectangle.height; }  }  }  Color color = container.isEnabled() ? ((this.ss != null) ? this.ss.getForeground(view.getAttributes()) : container.getForeground()) : UIManager.getColor("textInactiveText"); param1Graphics.setColor(color); if (this.img != null) { drawIcon(param1Graphics, (int)param1Float1, (int)param1Float2, (int)param1Float3, (int)param1Float4, f, container); return; }  CSS.Value value = getChildType(view); Font font = ((StyledDocument)view.getDocument()).getFont(view.getAttributes()); if (font != null)
/*      */         param1Graphics.setFont(font);  if (value == CSS.Value.SQUARE || value == CSS.Value.CIRCLE || value == CSS.Value.DISC) { drawShape(param1Graphics, value, (int)param1Float1, (int)param1Float2, (int)param1Float3, (int)param1Float4, f); } else if (value == CSS.Value.DECIMAL) { drawLetter(param1Graphics, '1', (int)param1Float1, (int)param1Float2, (int)param1Float3, (int)param1Float4, f, getRenderIndex(param1View, param1Int)); } else if (value == CSS.Value.LOWER_ALPHA) { drawLetter(param1Graphics, 'a', (int)param1Float1, (int)param1Float2, (int)param1Float3, (int)param1Float4, f, getRenderIndex(param1View, param1Int)); } else if (value == CSS.Value.UPPER_ALPHA) { drawLetter(param1Graphics, 'A', (int)param1Float1, (int)param1Float2, (int)param1Float3, (int)param1Float4, f, getRenderIndex(param1View, param1Int)); } else if (value == CSS.Value.LOWER_ROMAN) { drawLetter(param1Graphics, 'i', (int)param1Float1, (int)param1Float2, (int)param1Float3, (int)param1Float4, f, getRenderIndex(param1View, param1Int)); } else if (value == CSS.Value.UPPER_ROMAN) { drawLetter(param1Graphics, 'I', (int)param1Float1, (int)param1Float2, (int)param1Float3, (int)param1Float4, f, getRenderIndex(param1View, param1Int)); }  }
/*      */     void drawIcon(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, float param1Float, Component param1Component) { int i = this.isLeftToRight ? -(this.img.getIconWidth() + this.bulletgap) : (param1Int3 + this.bulletgap); int j = param1Int1 + i; int k = Math.max(param1Int2, param1Int2 + (int)(param1Float * param1Int4) - this.img.getIconHeight()); this.img.paintIcon(param1Component, param1Graphics, j, k); }
/*      */     void drawShape(Graphics param1Graphics, CSS.Value param1Value, int param1Int1, int param1Int2, int param1Int3, int param1Int4, float param1Float) { int i = this.isLeftToRight ? -(this.bulletgap + 8) : (param1Int3 + this.bulletgap); int j = param1Int1 + i; int k = Math.max(param1Int2, param1Int2 + (int)(param1Float * param1Int4) - 8); if (param1Value == CSS.Value.SQUARE) { param1Graphics.drawRect(j, k, 8, 8); } else if (param1Value == CSS.Value.CIRCLE) { param1Graphics.drawOval(j, k, 8, 8); } else { param1Graphics.fillOval(j, k, 8, 8); }  }
/*      */     void drawLetter(Graphics param1Graphics, char param1Char, int param1Int1, int param1Int2, int param1Int3, int param1Int4, float param1Float, int param1Int5) { String str = formatItemNum(param1Int5, param1Char); str = this.isLeftToRight ? (str + ".") : ("." + str); FontMetrics fontMetrics = SwingUtilities2.getFontMetrics((JComponent)null, param1Graphics); int i = SwingUtilities2.stringWidth(null, fontMetrics, str); int j = this.isLeftToRight ? -(i + this.bulletgap) : (param1Int3 + this.bulletgap); int k = param1Int1 + j; int m = Math.max(param1Int2 + fontMetrics.getAscent(), param1Int2 + (int)(param1Int4 * param1Float)); SwingUtilities2.drawString((JComponent)null, param1Graphics, str, k, m); }
/*      */     String formatItemNum(int param1Int, char param1Char) { String str2, str1 = "1"; boolean bool = false; switch (param1Char) { default: str2 = String.valueOf(param1Int); break;case 'A': bool = true;case 'a': str2 = formatAlphaNumerals(param1Int); break;case 'I': bool = true;case 'i': str2 = formatRomanNumerals(param1Int); break; }  if (bool)
/*      */         str2 = str2.toUpperCase();  return str2; } String formatAlphaNumerals(int param1Int) { String str; if (param1Int > 26) { str = formatAlphaNumerals(param1Int / 26) + formatAlphaNumerals(param1Int % 26); } else { str = String.valueOf((char)(97 + param1Int - 1)); }  return str; } static final char[][] romanChars = new char[][] { { 'i', 'v' }, { 'x', 'l' }, { 'c', 'd' }, { 'm', '?' } }; private Rectangle paintRect; private boolean checkedForStart; private int start; private CSS.Value type; URL imageurl; private StyleSheet ss; Icon img; private int bulletgap; private boolean isLeftToRight; String formatRomanNumerals(int param1Int) { return formatRomanNumerals(0, param1Int); } String formatRomanNumerals(int param1Int1, int param1Int2) { if (param1Int2 < 10)
/*      */         return formatRomanDigit(param1Int1, param1Int2);  return formatRomanNumerals(param1Int1 + 1, param1Int2 / 10) + formatRomanDigit(param1Int1, param1Int2 % 10); } String formatRomanDigit(int param1Int1, int param1Int2) { String str = ""; if (param1Int2 == 9) { str = str + romanChars[param1Int1][0]; str = str + romanChars[param1Int1 + 1][0]; return str; }  if (param1Int2 == 4) { str = str + romanChars[param1Int1][0]; str = str + romanChars[param1Int1][1]; return str; }  if (param1Int2 >= 5) { str = str + romanChars[param1Int1][1]; param1Int2 -= 5; }  for (byte b = 0; b < param1Int2; b++)
/*      */         str = str + romanChars[param1Int1][0];  return str; }
/*      */   } static class BackgroundImagePainter implements Serializable
/*      */   {
/* 2465 */     ImageIcon backgroundImage; float hPosition; float vPosition; short flags; BackgroundImagePainter(AttributeSet param1AttributeSet, CSS param1CSS, StyleSheet param1StyleSheet) { this.backgroundImage = param1StyleSheet.getBackgroundImage(param1AttributeSet);
/*      */ 
/*      */       
/* 2468 */       CSS.BackgroundPosition backgroundPosition = (CSS.BackgroundPosition)param1AttributeSet.getAttribute(CSS.Attribute.BACKGROUND_POSITION);
/* 2469 */       if (backgroundPosition != null) {
/* 2470 */         this.hPosition = backgroundPosition.getHorizontalPosition();
/* 2471 */         this.vPosition = backgroundPosition.getVerticalPosition();
/* 2472 */         if (backgroundPosition.isHorizontalPositionRelativeToSize()) {
/* 2473 */           this.flags = (short)(this.flags | 0x4);
/*      */         }
/* 2475 */         else if (backgroundPosition.isHorizontalPositionRelativeToSize()) {
/* 2476 */           this.hPosition *= CSS.getFontSize(param1AttributeSet, 12, param1StyleSheet);
/*      */         } 
/* 2478 */         if (backgroundPosition.isVerticalPositionRelativeToSize()) {
/* 2479 */           this.flags = (short)(this.flags | 0x8);
/*      */         }
/* 2481 */         else if (backgroundPosition.isVerticalPositionRelativeToFontSize()) {
/* 2482 */           this.vPosition *= CSS.getFontSize(param1AttributeSet, 12, param1StyleSheet);
/*      */         } 
/*      */       } 
/*      */       
/* 2486 */       CSS.Value value = (CSS.Value)param1AttributeSet.getAttribute(CSS.Attribute.BACKGROUND_REPEAT);
/*      */       
/* 2488 */       if (value == null || value == CSS.Value.BACKGROUND_REPEAT) {
/* 2489 */         this.flags = (short)(this.flags | 0x3);
/*      */       }
/* 2491 */       else if (value == CSS.Value.BACKGROUND_REPEAT_X) {
/* 2492 */         this.flags = (short)(this.flags | 0x1);
/*      */       }
/* 2494 */       else if (value == CSS.Value.BACKGROUND_REPEAT_Y) {
/* 2495 */         this.flags = (short)(this.flags | 0x2);
/*      */       }  }
/*      */     
/*      */     private int paintX; private int paintY; private int paintMaxX; private int paintMaxY;
/*      */     void paint(Graphics param1Graphics, float param1Float1, float param1Float2, float param1Float3, float param1Float4, View param1View) {
/* 2500 */       Rectangle rectangle = param1Graphics.getClipRect();
/* 2501 */       if (rectangle != null)
/*      */       {
/*      */         
/* 2504 */         param1Graphics.clipRect((int)param1Float1, (int)param1Float2, (int)param1Float3, (int)param1Float4);
/*      */       }
/* 2506 */       if ((this.flags & 0x3) == 0) {
/*      */         
/* 2508 */         int i = this.backgroundImage.getIconWidth();
/* 2509 */         int j = this.backgroundImage.getIconWidth();
/* 2510 */         if ((this.flags & 0x4) == 4) {
/* 2511 */           this.paintX = (int)(param1Float1 + param1Float3 * this.hPosition - i * this.hPosition);
/*      */         }
/*      */         else {
/*      */           
/* 2515 */           this.paintX = (int)param1Float1 + (int)this.hPosition;
/*      */         } 
/* 2517 */         if ((this.flags & 0x8) == 8) {
/* 2518 */           this.paintY = (int)(param1Float2 + param1Float4 * this.vPosition - j * this.vPosition);
/*      */         }
/*      */         else {
/*      */           
/* 2522 */           this.paintY = (int)param1Float2 + (int)this.vPosition;
/*      */         } 
/* 2524 */         if (rectangle == null || (this.paintX + i > rectangle.x && this.paintY + j > rectangle.y && this.paintX < rectangle.x + rectangle.width && this.paintY < rectangle.y + rectangle.height))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2529 */           this.backgroundImage.paintIcon(null, param1Graphics, this.paintX, this.paintY);
/*      */         }
/*      */       } else {
/*      */         
/* 2533 */         int i = this.backgroundImage.getIconWidth();
/* 2534 */         int j = this.backgroundImage.getIconHeight();
/* 2535 */         if (i > 0 && j > 0) {
/* 2536 */           this.paintX = (int)param1Float1;
/* 2537 */           this.paintY = (int)param1Float2;
/* 2538 */           this.paintMaxX = (int)(param1Float1 + param1Float3);
/* 2539 */           this.paintMaxY = (int)(param1Float2 + param1Float4);
/* 2540 */           if (updatePaintCoordinates(rectangle, i, j)) {
/* 2541 */             while (this.paintX < this.paintMaxX) {
/* 2542 */               int k = this.paintY;
/* 2543 */               while (k < this.paintMaxY) {
/* 2544 */                 this.backgroundImage.paintIcon(null, param1Graphics, this.paintX, k);
/*      */                 
/* 2546 */                 k += j;
/*      */               } 
/* 2548 */               this.paintX += i;
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/* 2553 */       if (rectangle != null)
/*      */       {
/* 2555 */         param1Graphics.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean updatePaintCoordinates(Rectangle param1Rectangle, int param1Int1, int param1Int2) {
/* 2561 */       if ((this.flags & 0x3) == 1) {
/* 2562 */         this.paintMaxY = this.paintY + 1;
/*      */       }
/* 2564 */       else if ((this.flags & 0x3) == 2) {
/* 2565 */         this.paintMaxX = this.paintX + 1;
/*      */       } 
/* 2567 */       if (param1Rectangle != null) {
/* 2568 */         if ((this.flags & 0x3) == 1 && (this.paintY + param1Int2 <= param1Rectangle.y || this.paintY > param1Rectangle.y + param1Rectangle.height))
/*      */         {
/*      */           
/* 2571 */           return false;
/*      */         }
/* 2573 */         if ((this.flags & 0x3) == 2 && (this.paintX + param1Int1 <= param1Rectangle.x || this.paintX > param1Rectangle.x + param1Rectangle.width))
/*      */         {
/*      */           
/* 2576 */           return false;
/*      */         }
/* 2578 */         if ((this.flags & 0x1) == 1) {
/* 2579 */           if (param1Rectangle.x + param1Rectangle.width < this.paintMaxX) {
/* 2580 */             if ((param1Rectangle.x + param1Rectangle.width - this.paintX) % param1Int1 == 0) {
/* 2581 */               this.paintMaxX = param1Rectangle.x + param1Rectangle.width;
/*      */             } else {
/*      */               
/* 2584 */               this.paintMaxX = ((param1Rectangle.x + param1Rectangle.width - this.paintX) / param1Int1 + 1) * param1Int1 + this.paintX;
/*      */             } 
/*      */           }
/*      */           
/* 2588 */           if (param1Rectangle.x > this.paintX) {
/* 2589 */             this.paintX = (param1Rectangle.x - this.paintX) / param1Int1 * param1Int1 + this.paintX;
/*      */           }
/*      */         } 
/* 2592 */         if ((this.flags & 0x2) == 2) {
/* 2593 */           if (param1Rectangle.y + param1Rectangle.height < this.paintMaxY) {
/* 2594 */             if ((param1Rectangle.y + param1Rectangle.height - this.paintY) % param1Int2 == 0) {
/* 2595 */               this.paintMaxY = param1Rectangle.y + param1Rectangle.height;
/*      */             } else {
/*      */               
/* 2598 */               this.paintMaxY = ((param1Rectangle.y + param1Rectangle.height - this.paintY) / param1Int2 + 1) * param1Int2 + this.paintY;
/*      */             } 
/*      */           }
/*      */           
/* 2602 */           if (param1Rectangle.y > this.paintY) {
/* 2603 */             this.paintY = (param1Rectangle.y - this.paintY) / param1Int2 * param1Int2 + this.paintY;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 2608 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class ViewAttributeSet
/*      */     extends MuxingAttributeSet
/*      */   {
/*      */     View host;
/*      */ 
/*      */     
/*      */     ViewAttributeSet(View param1View) {
/* 2620 */       this.host = param1View;
/*      */ 
/*      */ 
/*      */       
/* 2624 */       Document document = param1View.getDocument();
/* 2625 */       StyleSheet.SearchBuffer searchBuffer = StyleSheet.SearchBuffer.obtainSearchBuffer();
/* 2626 */       Vector<AttributeSet> vector = searchBuffer.getVector();
/*      */       try {
/* 2628 */         if (document instanceof HTMLDocument) {
/* 2629 */           StyleSheet styleSheet = StyleSheet.this;
/* 2630 */           Element element = param1View.getElement();
/* 2631 */           AttributeSet attributeSet1 = element.getAttributes();
/* 2632 */           AttributeSet attributeSet2 = styleSheet.translateHTMLToCSS(attributeSet1);
/*      */           
/* 2634 */           if (attributeSet2.getAttributeCount() != 0) {
/* 2635 */             vector.addElement(attributeSet2);
/*      */           }
/* 2637 */           if (element.isLeaf()) {
/* 2638 */             Enumeration<?> enumeration = attributeSet1.getAttributeNames();
/* 2639 */             while (enumeration.hasMoreElements()) {
/* 2640 */               Object object = enumeration.nextElement();
/* 2641 */               if (object instanceof HTML.Tag) {
/* 2642 */                 if (object == HTML.Tag.A) {
/* 2643 */                   Object object1 = attributeSet1.getAttribute(object);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2657 */                   if (object1 != null && object1 instanceof AttributeSet) {
/* 2658 */                     AttributeSet attributeSet = (AttributeSet)object1;
/* 2659 */                     if (attributeSet.getAttribute(HTML.Attribute.HREF) == null) {
/*      */                       continue;
/*      */                     }
/*      */                   } 
/*      */                 } 
/* 2664 */                 Style style = styleSheet.getRule((HTML.Tag)object, element);
/* 2665 */                 if (style != null) {
/* 2666 */                   vector.addElement(style);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } else {
/*      */             
/* 2672 */             HTML.Tag tag = (HTML.Tag)attributeSet1.getAttribute(StyleConstants.NameAttribute);
/* 2673 */             Style style = styleSheet.getRule(tag, element);
/* 2674 */             if (style != null) {
/* 2675 */               vector.addElement(style);
/*      */             }
/*      */           } 
/*      */         } 
/* 2679 */         AttributeSet[] arrayOfAttributeSet = new AttributeSet[vector.size()];
/* 2680 */         vector.copyInto((Object[])arrayOfAttributeSet);
/* 2681 */         setAttributes(arrayOfAttributeSet);
/*      */       } finally {
/*      */         
/* 2684 */         StyleSheet.SearchBuffer.releaseSearchBuffer(searchBuffer);
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
/*      */     public boolean isDefined(Object param1Object) {
/* 2701 */       if (param1Object instanceof StyleConstants) {
/*      */         
/* 2703 */         CSS.Attribute attribute = StyleSheet.this.css.styleConstantsKeyToCSSKey((StyleConstants)param1Object);
/* 2704 */         if (attribute != null) {
/* 2705 */           param1Object = attribute;
/*      */         }
/*      */       } 
/* 2708 */       return super.isDefined(param1Object);
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
/*      */     public Object getAttribute(Object param1Object) {
/* 2721 */       if (param1Object instanceof StyleConstants) {
/*      */         
/* 2723 */         CSS.Attribute attribute = StyleSheet.this.css.styleConstantsKeyToCSSKey((StyleConstants)param1Object);
/* 2724 */         if (attribute != null) {
/* 2725 */           Object object = doGetAttribute(attribute);
/* 2726 */           if (object instanceof CSS.CssValue) {
/* 2727 */             return ((CSS.CssValue)object)
/* 2728 */               .toStyleConstants((StyleConstants)param1Object, this.host);
/*      */           }
/*      */         } 
/*      */       } 
/* 2732 */       return doGetAttribute(param1Object);
/*      */     }
/*      */     
/*      */     Object doGetAttribute(Object param1Object) {
/* 2736 */       Object object = super.getAttribute(param1Object);
/* 2737 */       if (object != null) {
/* 2738 */         return object;
/*      */       }
/*      */ 
/*      */       
/* 2742 */       if (param1Object instanceof CSS.Attribute) {
/* 2743 */         CSS.Attribute attribute = (CSS.Attribute)param1Object;
/* 2744 */         if (attribute.isInherited()) {
/* 2745 */           AttributeSet attributeSet = getResolveParent();
/* 2746 */           if (attributeSet != null)
/* 2747 */             return attributeSet.getAttribute(param1Object); 
/*      */         } 
/*      */       } 
/* 2750 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet getResolveParent() {
/* 2761 */       if (this.host == null) {
/* 2762 */         return null;
/*      */       }
/* 2764 */       View view = this.host.getParent();
/* 2765 */       return (view != null) ? view.getAttributes() : null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class ResolvedStyle
/*      */     extends MuxingAttributeSet
/*      */     implements Serializable, Style
/*      */   {
/*      */     String name;
/*      */ 
/*      */ 
/*      */     
/*      */     private int extendedIndex;
/*      */ 
/*      */ 
/*      */     
/*      */     ResolvedStyle(String param1String, AttributeSet[] param1ArrayOfAttributeSet, int param1Int) {
/* 2785 */       super(param1ArrayOfAttributeSet);
/* 2786 */       this.name = param1String;
/* 2787 */       this.extendedIndex = param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void insertStyle(Style param1Style, int param1Int) {
/* 2797 */       AttributeSet[] arrayOfAttributeSet = getAttributes();
/* 2798 */       int i = arrayOfAttributeSet.length;
/* 2799 */       byte b = 0;
/* 2800 */       for (; b < this.extendedIndex && 
/* 2801 */         param1Int <= StyleSheet.getSpecificity(((Style)arrayOfAttributeSet[b])
/* 2802 */           .getName()); b++);
/*      */ 
/*      */ 
/*      */       
/* 2806 */       insertAttributeSetAt(param1Style, b);
/* 2807 */       this.extendedIndex++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void removeStyle(Style param1Style) {
/* 2815 */       AttributeSet[] arrayOfAttributeSet = getAttributes();
/*      */       
/* 2817 */       for (int i = arrayOfAttributeSet.length - 1; i >= 0; i--) {
/* 2818 */         if (arrayOfAttributeSet[i] == param1Style) {
/* 2819 */           removeAttributeSetAt(i);
/* 2820 */           if (i < this.extendedIndex) {
/* 2821 */             this.extendedIndex--;
/*      */           }
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void insertExtendedStyleAt(Style param1Style, int param1Int) {
/* 2833 */       insertAttributeSetAt(param1Style, this.extendedIndex + param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void addExtendedStyle(Style param1Style) {
/* 2841 */       insertAttributeSetAt(param1Style, (getAttributes()).length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void removeExtendedStyleAt(int param1Int) {
/* 2849 */       removeAttributeSetAt(this.extendedIndex + param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean matches(String param1String) {
/* 2858 */       int i = param1String.length();
/*      */       
/* 2860 */       if (i == 0) {
/* 2861 */         return false;
/*      */       }
/* 2863 */       int j = this.name.length();
/* 2864 */       int k = param1String.lastIndexOf(' ');
/* 2865 */       int m = this.name.lastIndexOf(' ');
/* 2866 */       if (k >= 0) {
/* 2867 */         k++;
/*      */       }
/* 2869 */       if (m >= 0) {
/* 2870 */         m++;
/*      */       }
/* 2872 */       if (!matches(param1String, k, i, m, j)) {
/* 2873 */         return false;
/*      */       }
/* 2875 */       while (k != -1) {
/* 2876 */         i = k - 1;
/* 2877 */         k = param1String.lastIndexOf(' ', i - 1);
/* 2878 */         if (k >= 0) {
/* 2879 */           k++;
/*      */         }
/* 2881 */         boolean bool = false;
/* 2882 */         while (!bool && m != -1) {
/* 2883 */           j = m - 1;
/* 2884 */           m = this.name.lastIndexOf(' ', j - 1);
/* 2885 */           if (m >= 0) {
/* 2886 */             m++;
/*      */           }
/* 2888 */           bool = matches(param1String, k, i, m, j);
/*      */         } 
/*      */         
/* 2891 */         if (!bool) {
/* 2892 */           return false;
/*      */         }
/*      */       } 
/* 2895 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean matches(String param1String, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2905 */       param1Int1 = Math.max(param1Int1, 0);
/* 2906 */       param1Int3 = Math.max(param1Int3, 0);
/* 2907 */       int i = boundedIndexOf(this.name, '.', param1Int3, param1Int4);
/*      */       
/* 2909 */       int j = boundedIndexOf(this.name, '#', param1Int3, param1Int4);
/*      */       
/* 2911 */       int k = boundedIndexOf(param1String, '.', param1Int1, param1Int2);
/* 2912 */       int m = boundedIndexOf(param1String, '#', param1Int1, param1Int2);
/* 2913 */       if (k != -1) {
/*      */ 
/*      */ 
/*      */         
/* 2917 */         if (i == -1) {
/* 2918 */           return false;
/*      */         }
/* 2920 */         if (param1Int1 == k) {
/* 2921 */           if (param1Int4 - i != param1Int2 - k || 
/* 2922 */             !param1String.regionMatches(param1Int1, this.name, i, param1Int4 - i))
/*      */           {
/* 2924 */             return false;
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 2929 */         else if (param1Int2 - param1Int1 != param1Int4 - param1Int3 || 
/* 2930 */           !param1String.regionMatches(param1Int1, this.name, param1Int3, param1Int4 - param1Int3)) {
/*      */           
/* 2932 */           return false;
/*      */         } 
/*      */         
/* 2935 */         return true;
/*      */       } 
/* 2937 */       if (m != -1) {
/*      */ 
/*      */ 
/*      */         
/* 2941 */         if (j == -1) {
/* 2942 */           return false;
/*      */         }
/* 2944 */         if (param1Int1 == m) {
/* 2945 */           if (param1Int4 - j != param1Int2 - m || 
/* 2946 */             !param1String.regionMatches(param1Int1, this.name, j, param1Int4 - j))
/*      */           {
/* 2948 */             return false;
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 2953 */         else if (param1Int2 - param1Int1 != param1Int4 - param1Int3 || 
/* 2954 */           !param1String.regionMatches(param1Int1, this.name, param1Int3, param1Int4 - param1Int3)) {
/*      */           
/* 2956 */           return false;
/*      */         } 
/*      */         
/* 2959 */         return true;
/*      */       } 
/* 2961 */       if (i != -1)
/*      */       {
/* 2963 */         return (i - param1Int3 == param1Int2 - param1Int1 && param1String
/* 2964 */           .regionMatches(param1Int1, this.name, param1Int3, i - param1Int3));
/*      */       }
/*      */       
/* 2967 */       if (j != -1)
/*      */       {
/* 2969 */         return (j - param1Int3 == param1Int2 - param1Int1 && param1String
/* 2970 */           .regionMatches(param1Int1, this.name, param1Int3, j - param1Int3));
/*      */       }
/*      */ 
/*      */       
/* 2974 */       return (param1Int4 - param1Int3 == param1Int2 - param1Int1 && param1String
/* 2975 */         .regionMatches(param1Int1, this.name, param1Int3, param1Int4 - param1Int3));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int boundedIndexOf(String param1String, char param1Char, int param1Int1, int param1Int2) {
/* 2986 */       int i = param1String.indexOf(param1Char, param1Int1);
/* 2987 */       if (i >= param1Int2) {
/* 2988 */         return -1;
/*      */       }
/* 2990 */       return i;
/*      */     }
/*      */     public void addAttribute(Object param1Object1, Object param1Object2) {}
/*      */     public void addAttributes(AttributeSet param1AttributeSet) {}
/*      */     public void removeAttribute(Object param1Object) {}
/*      */     public void removeAttributes(Enumeration<?> param1Enumeration) {}
/*      */     public void removeAttributes(AttributeSet param1AttributeSet) {}
/*      */     public void setResolveParent(AttributeSet param1AttributeSet) {}
/*      */     public String getName() {
/* 2999 */       return this.name;
/*      */     } public void addChangeListener(ChangeListener param1ChangeListener) {}
/*      */     public void removeChangeListener(ChangeListener param1ChangeListener) {}
/*      */     public ChangeListener[] getChangeListeners() {
/* 3003 */       return new ChangeListener[0];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class SelectorMapping
/*      */     implements Serializable
/*      */   {
/*      */     private int specificity;
/*      */ 
/*      */ 
/*      */     
/*      */     private Style style;
/*      */ 
/*      */     
/*      */     private HashMap<String, SelectorMapping> children;
/*      */ 
/*      */ 
/*      */     
/*      */     public SelectorMapping(int param1Int) {
/* 3025 */       this.specificity = param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getSpecificity() {
/* 3032 */       return this.specificity;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setStyle(Style param1Style) {
/* 3039 */       this.style = param1Style;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Style getStyle() {
/* 3046 */       return this.style;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SelectorMapping getChildSelectorMapping(String param1String, boolean param1Boolean) {
/* 3057 */       SelectorMapping selectorMapping = null;
/*      */       
/* 3059 */       if (this.children != null) {
/* 3060 */         selectorMapping = this.children.get(param1String);
/*      */       }
/* 3062 */       else if (param1Boolean) {
/* 3063 */         this.children = new HashMap<>(7);
/*      */       } 
/* 3065 */       if (selectorMapping == null && param1Boolean) {
/* 3066 */         int i = getChildSpecificity(param1String);
/*      */         
/* 3068 */         selectorMapping = createChildSelectorMapping(i);
/* 3069 */         this.children.put(param1String, selectorMapping);
/*      */       } 
/* 3071 */       return selectorMapping;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected SelectorMapping createChildSelectorMapping(int param1Int) {
/* 3079 */       return new SelectorMapping(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getChildSpecificity(String param1String) {
/* 3089 */       char c = param1String.charAt(0);
/* 3090 */       int i = getSpecificity();
/*      */       
/* 3092 */       if (c == '.') {
/* 3093 */         i += 100;
/*      */       }
/* 3095 */       else if (c == '#') {
/* 3096 */         i += 10000;
/*      */       } else {
/*      */         
/* 3099 */         i++;
/* 3100 */         if (param1String.indexOf('.') != -1) {
/* 3101 */           i += 100;
/*      */         }
/* 3103 */         if (param1String.indexOf('#') != -1) {
/* 3104 */           i += 10000;
/*      */         }
/*      */       } 
/* 3107 */       return i;
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
/*      */   class CssParser
/*      */     implements CSSParser.CSSParserCallback
/*      */   {
/*      */     public AttributeSet parseDeclaration(String param1String) {
/*      */       try {
/* 3162 */         return parseDeclaration(new StringReader(param1String));
/* 3163 */       } catch (IOException iOException) {
/* 3164 */         return null;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet parseDeclaration(Reader param1Reader) throws IOException {
/* 3171 */       parse(this.base, param1Reader, true, false);
/* 3172 */       return this.declaration.copyAttributes();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void parse(URL param1URL, Reader param1Reader, boolean param1Boolean1, boolean param1Boolean2) throws IOException {
/* 3180 */       this.base = param1URL;
/* 3181 */       this.isLink = param1Boolean2;
/* 3182 */       this.parsingDeclaration = param1Boolean1;
/* 3183 */       this.declaration.removeAttributes(this.declaration);
/* 3184 */       this.selectorTokens.removeAllElements();
/* 3185 */       this.selectors.removeAllElements();
/* 3186 */       this.propertyName = null;
/* 3187 */       this.parser.parse(param1Reader, this, param1Boolean1);
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
/*      */     public void handleImport(String param1String) {
/* 3201 */       URL uRL = CSS.getURL(this.base, param1String);
/* 3202 */       if (uRL != null) {
/* 3203 */         StyleSheet.this.importStyleSheet(uRL);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handleSelector(String param1String) {
/* 3212 */       if (!param1String.startsWith(".") && 
/* 3213 */         !param1String.startsWith("#")) {
/* 3214 */         param1String = param1String.toLowerCase();
/*      */       }
/* 3216 */       int i = param1String.length();
/*      */       
/* 3218 */       if (param1String.endsWith(",")) {
/* 3219 */         if (i > 1) {
/* 3220 */           param1String = param1String.substring(0, i - 1);
/* 3221 */           this.selectorTokens.addElement(param1String);
/*      */         } 
/* 3223 */         addSelector();
/*      */       }
/* 3225 */       else if (i > 0) {
/* 3226 */         this.selectorTokens.addElement(param1String);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void startRule() {
/* 3234 */       if (this.selectorTokens.size() > 0) {
/* 3235 */         addSelector();
/*      */       }
/* 3237 */       this.propertyName = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handleProperty(String param1String) {
/* 3244 */       this.propertyName = param1String;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handleValue(String param1String) {
/* 3251 */       if (this.propertyName != null && param1String != null && param1String.length() > 0) {
/* 3252 */         CSS.Attribute attribute = CSS.getAttribute(this.propertyName);
/* 3253 */         if (attribute != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3259 */           if (attribute == CSS.Attribute.LIST_STYLE_IMAGE && 
/* 3260 */             param1String != null && !param1String.equals("none")) {
/* 3261 */             URL uRL = CSS.getURL(this.base, param1String);
/*      */             
/* 3263 */             if (uRL != null) {
/* 3264 */               param1String = uRL.toString();
/*      */             }
/*      */           } 
/*      */           
/* 3268 */           StyleSheet.this.addCSSAttribute(this.declaration, attribute, param1String);
/*      */         } 
/* 3270 */         this.propertyName = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void endRule() {
/* 3278 */       int i = this.selectors.size();
/* 3279 */       for (byte b = 0; b < i; b++) {
/* 3280 */         String[] arrayOfString = this.selectors.elementAt(b);
/* 3281 */         if (arrayOfString.length > 0) {
/* 3282 */           StyleSheet.this.addRule(arrayOfString, this.declaration, this.isLink);
/*      */         }
/*      */       } 
/* 3285 */       this.declaration.removeAttributes(this.declaration);
/* 3286 */       this.selectors.removeAllElements();
/*      */     }
/*      */     
/*      */     private void addSelector() {
/* 3290 */       String[] arrayOfString = new String[this.selectorTokens.size()];
/* 3291 */       this.selectorTokens.copyInto((Object[])arrayOfString);
/* 3292 */       this.selectors.addElement(arrayOfString);
/* 3293 */       this.selectorTokens.removeAllElements();
/*      */     }
/*      */ 
/*      */     
/* 3297 */     Vector<String[]> selectors = (Vector)new Vector<>();
/* 3298 */     Vector<String> selectorTokens = new Vector<>();
/*      */     
/*      */     String propertyName;
/* 3301 */     MutableAttributeSet declaration = new SimpleAttributeSet();
/*      */     
/*      */     boolean parsingDeclaration;
/*      */     
/*      */     boolean isLink;
/*      */     
/*      */     URL base;
/*      */     
/* 3309 */     CSSParser parser = new CSSParser();
/*      */   }
/*      */ 
/*      */   
/*      */   void rebaseSizeMap(int paramInt) {
/* 3314 */     this.sizeMap = new int[sizeMapDefault.length];
/* 3315 */     for (byte b = 0; b < sizeMapDefault.length; b++) {
/* 3316 */       this.sizeMap[b] = Math.max(paramInt * sizeMapDefault[b] / sizeMapDefault[CSS.baseFontSizeIndex], 4);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int[] getSizeMap() {
/* 3324 */     return this.sizeMap;
/*      */   }
/*      */   boolean isW3CLengthUnits() {
/* 3327 */     return this.w3cLengthUnits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3334 */   static final int[] sizeMapDefault = new int[] { 8, 10, 12, 14, 18, 24, 36 };
/*      */   
/* 3336 */   private int[] sizeMap = sizeMapDefault;
/*      */   private boolean w3cLengthUnits = false;
/*      */   static final int DEFAULT_FONT_SIZE = 3;
/*      */   private CSS css;
/*      */   private Vector<StyleSheet> linkedStyleSheets;
/*      */   private URL base;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/StyleSheet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */