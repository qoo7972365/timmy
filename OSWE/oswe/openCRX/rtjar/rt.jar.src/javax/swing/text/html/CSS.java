/*      */ package javax.swing.text.html;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Image;
/*      */ import java.awt.Toolkit;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.SizeRequirements;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.Element;
/*      */ import javax.swing.text.MutableAttributeSet;
/*      */ import javax.swing.text.SimpleAttributeSet;
/*      */ import javax.swing.text.StyleConstants;
/*      */ import javax.swing.text.StyleContext;
/*      */ import javax.swing.text.View;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CSS
/*      */   implements Serializable
/*      */ {
/*      */   public static final class Attribute
/*      */   {
/*      */     private String name;
/*      */     private String defaultValue;
/*      */     private boolean inherited;
/*      */     
/*      */     private Attribute(String param1String1, String param1String2, boolean param1Boolean) {
/*  137 */       this.name = param1String1;
/*  138 */       this.defaultValue = param1String2;
/*  139 */       this.inherited = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  148 */       return this.name;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getDefaultValue() {
/*  157 */       return this.defaultValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInherited() {
/*  165 */       return this.inherited;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  173 */     public static final Attribute BACKGROUND = new Attribute("background", null, false);
/*      */ 
/*      */     
/*  176 */     public static final Attribute BACKGROUND_ATTACHMENT = new Attribute("background-attachment", "scroll", false);
/*      */ 
/*      */     
/*  179 */     public static final Attribute BACKGROUND_COLOR = new Attribute("background-color", "transparent", false);
/*      */ 
/*      */     
/*  182 */     public static final Attribute BACKGROUND_IMAGE = new Attribute("background-image", "none", false);
/*      */ 
/*      */     
/*  185 */     public static final Attribute BACKGROUND_POSITION = new Attribute("background-position", null, false);
/*      */ 
/*      */     
/*  188 */     public static final Attribute BACKGROUND_REPEAT = new Attribute("background-repeat", "repeat", false);
/*      */ 
/*      */     
/*  191 */     public static final Attribute BORDER = new Attribute("border", null, false);
/*      */ 
/*      */     
/*  194 */     public static final Attribute BORDER_BOTTOM = new Attribute("border-bottom", null, false);
/*      */ 
/*      */     
/*  197 */     public static final Attribute BORDER_BOTTOM_COLOR = new Attribute("border-bottom-color", null, false);
/*      */ 
/*      */     
/*  200 */     public static final Attribute BORDER_BOTTOM_STYLE = new Attribute("border-bottom-style", "none", false);
/*      */ 
/*      */     
/*  203 */     public static final Attribute BORDER_BOTTOM_WIDTH = new Attribute("border-bottom-width", "medium", false);
/*      */ 
/*      */     
/*  206 */     public static final Attribute BORDER_COLOR = new Attribute("border-color", null, false);
/*      */ 
/*      */     
/*  209 */     public static final Attribute BORDER_LEFT = new Attribute("border-left", null, false);
/*      */ 
/*      */     
/*  212 */     public static final Attribute BORDER_LEFT_COLOR = new Attribute("border-left-color", null, false);
/*      */ 
/*      */     
/*  215 */     public static final Attribute BORDER_LEFT_STYLE = new Attribute("border-left-style", "none", false);
/*      */ 
/*      */     
/*  218 */     public static final Attribute BORDER_LEFT_WIDTH = new Attribute("border-left-width", "medium", false);
/*      */ 
/*      */     
/*  221 */     public static final Attribute BORDER_RIGHT = new Attribute("border-right", null, false);
/*      */ 
/*      */     
/*  224 */     public static final Attribute BORDER_RIGHT_COLOR = new Attribute("border-right-color", null, false);
/*      */ 
/*      */     
/*  227 */     public static final Attribute BORDER_RIGHT_STYLE = new Attribute("border-right-style", "none", false);
/*      */ 
/*      */     
/*  230 */     public static final Attribute BORDER_RIGHT_WIDTH = new Attribute("border-right-width", "medium", false);
/*      */ 
/*      */     
/*  233 */     public static final Attribute BORDER_STYLE = new Attribute("border-style", "none", false);
/*      */ 
/*      */     
/*  236 */     public static final Attribute BORDER_TOP = new Attribute("border-top", null, false);
/*      */ 
/*      */     
/*  239 */     public static final Attribute BORDER_TOP_COLOR = new Attribute("border-top-color", null, false);
/*      */ 
/*      */     
/*  242 */     public static final Attribute BORDER_TOP_STYLE = new Attribute("border-top-style", "none", false);
/*      */ 
/*      */     
/*  245 */     public static final Attribute BORDER_TOP_WIDTH = new Attribute("border-top-width", "medium", false);
/*      */ 
/*      */     
/*  248 */     public static final Attribute BORDER_WIDTH = new Attribute("border-width", "medium", false);
/*      */ 
/*      */     
/*  251 */     public static final Attribute CLEAR = new Attribute("clear", "none", false);
/*      */ 
/*      */     
/*  254 */     public static final Attribute COLOR = new Attribute("color", "black", true);
/*      */ 
/*      */     
/*  257 */     public static final Attribute DISPLAY = new Attribute("display", "block", false);
/*      */ 
/*      */     
/*  260 */     public static final Attribute FLOAT = new Attribute("float", "none", false);
/*      */ 
/*      */     
/*  263 */     public static final Attribute FONT = new Attribute("font", null, true);
/*      */ 
/*      */     
/*  266 */     public static final Attribute FONT_FAMILY = new Attribute("font-family", null, true);
/*      */ 
/*      */     
/*  269 */     public static final Attribute FONT_SIZE = new Attribute("font-size", "medium", true);
/*      */ 
/*      */     
/*  272 */     public static final Attribute FONT_STYLE = new Attribute("font-style", "normal", true);
/*      */ 
/*      */     
/*  275 */     public static final Attribute FONT_VARIANT = new Attribute("font-variant", "normal", true);
/*      */ 
/*      */     
/*  278 */     public static final Attribute FONT_WEIGHT = new Attribute("font-weight", "normal", true);
/*      */ 
/*      */     
/*  281 */     public static final Attribute HEIGHT = new Attribute("height", "auto", false);
/*      */ 
/*      */     
/*  284 */     public static final Attribute LETTER_SPACING = new Attribute("letter-spacing", "normal", true);
/*      */ 
/*      */     
/*  287 */     public static final Attribute LINE_HEIGHT = new Attribute("line-height", "normal", true);
/*      */ 
/*      */     
/*  290 */     public static final Attribute LIST_STYLE = new Attribute("list-style", null, true);
/*      */ 
/*      */     
/*  293 */     public static final Attribute LIST_STYLE_IMAGE = new Attribute("list-style-image", "none", true);
/*      */ 
/*      */     
/*  296 */     public static final Attribute LIST_STYLE_POSITION = new Attribute("list-style-position", "outside", true);
/*      */ 
/*      */     
/*  299 */     public static final Attribute LIST_STYLE_TYPE = new Attribute("list-style-type", "disc", true);
/*      */ 
/*      */     
/*  302 */     public static final Attribute MARGIN = new Attribute("margin", null, false);
/*      */ 
/*      */     
/*  305 */     public static final Attribute MARGIN_BOTTOM = new Attribute("margin-bottom", "0", false);
/*      */ 
/*      */     
/*  308 */     public static final Attribute MARGIN_LEFT = new Attribute("margin-left", "0", false);
/*      */ 
/*      */     
/*  311 */     public static final Attribute MARGIN_RIGHT = new Attribute("margin-right", "0", false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  319 */     static final Attribute MARGIN_LEFT_LTR = new Attribute("margin-left-ltr", 
/*      */         
/*  321 */         Integer.toString(-2147483648), false);
/*      */     
/*  323 */     static final Attribute MARGIN_LEFT_RTL = new Attribute("margin-left-rtl", 
/*      */         
/*  325 */         Integer.toString(-2147483648), false);
/*      */     
/*  327 */     static final Attribute MARGIN_RIGHT_LTR = new Attribute("margin-right-ltr", 
/*      */         
/*  329 */         Integer.toString(-2147483648), false);
/*      */     
/*  331 */     static final Attribute MARGIN_RIGHT_RTL = new Attribute("margin-right-rtl", 
/*      */         
/*  333 */         Integer.toString(-2147483648), false);
/*      */ 
/*      */     
/*  336 */     public static final Attribute MARGIN_TOP = new Attribute("margin-top", "0", false);
/*      */ 
/*      */     
/*  339 */     public static final Attribute PADDING = new Attribute("padding", null, false);
/*      */ 
/*      */     
/*  342 */     public static final Attribute PADDING_BOTTOM = new Attribute("padding-bottom", "0", false);
/*      */ 
/*      */     
/*  345 */     public static final Attribute PADDING_LEFT = new Attribute("padding-left", "0", false);
/*      */ 
/*      */     
/*  348 */     public static final Attribute PADDING_RIGHT = new Attribute("padding-right", "0", false);
/*      */ 
/*      */     
/*  351 */     public static final Attribute PADDING_TOP = new Attribute("padding-top", "0", false);
/*      */ 
/*      */     
/*  354 */     public static final Attribute TEXT_ALIGN = new Attribute("text-align", null, true);
/*      */ 
/*      */     
/*  357 */     public static final Attribute TEXT_DECORATION = new Attribute("text-decoration", "none", true);
/*      */ 
/*      */     
/*  360 */     public static final Attribute TEXT_INDENT = new Attribute("text-indent", "0", true);
/*      */ 
/*      */     
/*  363 */     public static final Attribute TEXT_TRANSFORM = new Attribute("text-transform", "none", true);
/*      */ 
/*      */     
/*  366 */     public static final Attribute VERTICAL_ALIGN = new Attribute("vertical-align", "baseline", false);
/*      */ 
/*      */     
/*  369 */     public static final Attribute WORD_SPACING = new Attribute("word-spacing", "normal", true);
/*      */ 
/*      */     
/*  372 */     public static final Attribute WHITE_SPACE = new Attribute("white-space", "normal", true);
/*      */ 
/*      */     
/*  375 */     public static final Attribute WIDTH = new Attribute("width", "auto", false);
/*      */ 
/*      */     
/*  378 */     static final Attribute BORDER_SPACING = new Attribute("border-spacing", "0", true);
/*      */ 
/*      */     
/*  381 */     static final Attribute CAPTION_SIDE = new Attribute("caption-side", "left", true);
/*      */ 
/*      */ 
/*      */     
/*  385 */     static final Attribute[] allAttributes = new Attribute[] { BACKGROUND, BACKGROUND_ATTACHMENT, BACKGROUND_COLOR, BACKGROUND_IMAGE, BACKGROUND_POSITION, BACKGROUND_REPEAT, BORDER, BORDER_BOTTOM, BORDER_BOTTOM_WIDTH, BORDER_COLOR, BORDER_LEFT, BORDER_LEFT_WIDTH, BORDER_RIGHT, BORDER_RIGHT_WIDTH, BORDER_STYLE, BORDER_TOP, BORDER_TOP_WIDTH, BORDER_WIDTH, BORDER_TOP_STYLE, BORDER_RIGHT_STYLE, BORDER_BOTTOM_STYLE, BORDER_LEFT_STYLE, BORDER_TOP_COLOR, BORDER_RIGHT_COLOR, BORDER_BOTTOM_COLOR, BORDER_LEFT_COLOR, CLEAR, COLOR, DISPLAY, FLOAT, FONT, FONT_FAMILY, FONT_SIZE, FONT_STYLE, FONT_VARIANT, FONT_WEIGHT, HEIGHT, LETTER_SPACING, LINE_HEIGHT, LIST_STYLE, LIST_STYLE_IMAGE, LIST_STYLE_POSITION, LIST_STYLE_TYPE, MARGIN, MARGIN_BOTTOM, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, PADDING, PADDING_BOTTOM, PADDING_LEFT, PADDING_RIGHT, PADDING_TOP, TEXT_ALIGN, TEXT_DECORATION, TEXT_INDENT, TEXT_TRANSFORM, VERTICAL_ALIGN, WORD_SPACING, WHITE_SPACE, WIDTH, BORDER_SPACING, CAPTION_SIDE, MARGIN_LEFT_LTR, MARGIN_LEFT_RTL, MARGIN_RIGHT_LTR, MARGIN_RIGHT_RTL };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  406 */     private static final Attribute[] ALL_MARGINS = new Attribute[] { MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT };
/*      */     
/*  408 */     private static final Attribute[] ALL_PADDING = new Attribute[] { PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT };
/*      */     
/*  410 */     private static final Attribute[] ALL_BORDER_WIDTHS = new Attribute[] { BORDER_TOP_WIDTH, BORDER_RIGHT_WIDTH, BORDER_BOTTOM_WIDTH, BORDER_LEFT_WIDTH };
/*      */ 
/*      */     
/*  413 */     private static final Attribute[] ALL_BORDER_STYLES = new Attribute[] { BORDER_TOP_STYLE, BORDER_RIGHT_STYLE, BORDER_BOTTOM_STYLE, BORDER_LEFT_STYLE };
/*      */ 
/*      */     
/*  416 */     private static final Attribute[] ALL_BORDER_COLORS = new Attribute[] { BORDER_TOP_COLOR, BORDER_RIGHT_COLOR, BORDER_BOTTOM_COLOR, BORDER_LEFT_COLOR };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Value
/*      */   {
/*      */     private Value(String param1String) {
/*  425 */       this.name = param1String;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  434 */       return this.name;
/*      */     }
/*      */     
/*  437 */     static final Value INHERITED = new Value("inherited");
/*  438 */     static final Value NONE = new Value("none");
/*  439 */     static final Value HIDDEN = new Value("hidden");
/*  440 */     static final Value DOTTED = new Value("dotted");
/*  441 */     static final Value DASHED = new Value("dashed");
/*  442 */     static final Value SOLID = new Value("solid");
/*  443 */     static final Value DOUBLE = new Value("double");
/*  444 */     static final Value GROOVE = new Value("groove");
/*  445 */     static final Value RIDGE = new Value("ridge");
/*  446 */     static final Value INSET = new Value("inset");
/*  447 */     static final Value OUTSET = new Value("outset");
/*      */     
/*  449 */     static final Value DISC = new Value("disc");
/*  450 */     static final Value CIRCLE = new Value("circle");
/*  451 */     static final Value SQUARE = new Value("square");
/*  452 */     static final Value DECIMAL = new Value("decimal");
/*  453 */     static final Value LOWER_ROMAN = new Value("lower-roman");
/*  454 */     static final Value UPPER_ROMAN = new Value("upper-roman");
/*  455 */     static final Value LOWER_ALPHA = new Value("lower-alpha");
/*  456 */     static final Value UPPER_ALPHA = new Value("upper-alpha");
/*      */     
/*  458 */     static final Value BACKGROUND_NO_REPEAT = new Value("no-repeat");
/*  459 */     static final Value BACKGROUND_REPEAT = new Value("repeat");
/*  460 */     static final Value BACKGROUND_REPEAT_X = new Value("repeat-x");
/*  461 */     static final Value BACKGROUND_REPEAT_Y = new Value("repeat-y");
/*      */     
/*  463 */     static final Value BACKGROUND_SCROLL = new Value("scroll");
/*  464 */     static final Value BACKGROUND_FIXED = new Value("fixed");
/*      */     
/*      */     private String name;
/*      */     
/*  468 */     static final Value[] allValues = new Value[] { INHERITED, NONE, DOTTED, DASHED, SOLID, DOUBLE, GROOVE, RIDGE, INSET, OUTSET, DISC, CIRCLE, SQUARE, DECIMAL, LOWER_ROMAN, UPPER_ROMAN, LOWER_ALPHA, UPPER_ALPHA, BACKGROUND_NO_REPEAT, BACKGROUND_REPEAT, BACKGROUND_REPEAT_X, BACKGROUND_REPEAT_Y, BACKGROUND_FIXED, BACKGROUND_FIXED };
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CSS() {
/* 3401 */     this.styleSheet = null; this.baseFontSize = baseFontSizeIndex + 1; this.valueConvertor = new Hashtable<>(); this.valueConvertor.put(Attribute.FONT_SIZE, new FontSize()); this.valueConvertor.put(Attribute.FONT_FAMILY, new FontFamily()); this.valueConvertor.put(Attribute.FONT_WEIGHT, new FontWeight()); BorderStyle borderStyle = new BorderStyle(); this.valueConvertor.put(Attribute.BORDER_TOP_STYLE, borderStyle); this.valueConvertor.put(Attribute.BORDER_RIGHT_STYLE, borderStyle); this.valueConvertor.put(Attribute.BORDER_BOTTOM_STYLE, borderStyle); this.valueConvertor.put(Attribute.BORDER_LEFT_STYLE, borderStyle); ColorValue colorValue = new ColorValue(); this.valueConvertor.put(Attribute.COLOR, colorValue); this.valueConvertor.put(Attribute.BACKGROUND_COLOR, colorValue); this.valueConvertor.put(Attribute.BORDER_TOP_COLOR, colorValue); this.valueConvertor.put(Attribute.BORDER_RIGHT_COLOR, colorValue); this.valueConvertor.put(Attribute.BORDER_BOTTOM_COLOR, colorValue); this.valueConvertor.put(Attribute.BORDER_LEFT_COLOR, colorValue); LengthValue lengthValue1 = new LengthValue(); this.valueConvertor.put(Attribute.MARGIN_TOP, lengthValue1); this.valueConvertor.put(Attribute.MARGIN_BOTTOM, lengthValue1); this.valueConvertor.put(Attribute.MARGIN_LEFT, lengthValue1); this.valueConvertor.put(Attribute.MARGIN_LEFT_LTR, lengthValue1); this.valueConvertor.put(Attribute.MARGIN_LEFT_RTL, lengthValue1); this.valueConvertor.put(Attribute.MARGIN_RIGHT, lengthValue1); this.valueConvertor.put(Attribute.MARGIN_RIGHT_LTR, lengthValue1); this.valueConvertor.put(Attribute.MARGIN_RIGHT_RTL, lengthValue1); this.valueConvertor.put(Attribute.PADDING_TOP, lengthValue1); this.valueConvertor.put(Attribute.PADDING_BOTTOM, lengthValue1); this.valueConvertor.put(Attribute.PADDING_LEFT, lengthValue1); this.valueConvertor.put(Attribute.PADDING_RIGHT, lengthValue1); BorderWidthValue borderWidthValue = new BorderWidthValue(null, 0); this.valueConvertor.put(Attribute.BORDER_TOP_WIDTH, borderWidthValue); this.valueConvertor.put(Attribute.BORDER_BOTTOM_WIDTH, borderWidthValue); this.valueConvertor.put(Attribute.BORDER_LEFT_WIDTH, borderWidthValue); this.valueConvertor.put(Attribute.BORDER_RIGHT_WIDTH, borderWidthValue); LengthValue lengthValue2 = new LengthValue(true); this.valueConvertor.put(Attribute.TEXT_INDENT, lengthValue2); this.valueConvertor.put(Attribute.WIDTH, lengthValue1); this.valueConvertor.put(Attribute.HEIGHT, lengthValue1); this.valueConvertor.put(Attribute.BORDER_SPACING, lengthValue1); StringValue stringValue = new StringValue(); this.valueConvertor.put(Attribute.FONT_STYLE, stringValue); this.valueConvertor.put(Attribute.TEXT_DECORATION, stringValue); this.valueConvertor.put(Attribute.TEXT_ALIGN, stringValue); this.valueConvertor.put(Attribute.VERTICAL_ALIGN, stringValue); CssValueMapper cssValueMapper = new CssValueMapper(); this.valueConvertor.put(Attribute.LIST_STYLE_TYPE, cssValueMapper); this.valueConvertor.put(Attribute.BACKGROUND_IMAGE, new BackgroundImage()); this.valueConvertor.put(Attribute.BACKGROUND_POSITION, new BackgroundPosition()); this.valueConvertor.put(Attribute.BACKGROUND_REPEAT, cssValueMapper); this.valueConvertor.put(Attribute.BACKGROUND_ATTACHMENT, cssValueMapper); CssValue cssValue = new CssValue(); int i = Attribute.allAttributes.length; for (byte b = 0; b < i; b++) { Attribute attribute = Attribute.allAttributes[b]; if (this.valueConvertor.get(attribute) == null)
/*      */         this.valueConvertor.put(attribute, cssValue);  }
/* 3403 */      } static int baseFontSizeIndex = 3;
/*      */   
/*      */   void setBaseFontSize(int paramInt) {
/*      */     if (paramInt < 1) {
/*      */       this.baseFontSize = 0;
/*      */     } else if (paramInt > 7) {
/*      */       this.baseFontSize = 7;
/*      */     } else {
/*      */       this.baseFontSize = paramInt;
/*      */     } 
/*      */   }
/*      */   
/*      */   void setBaseFontSize(String paramString) {
/*      */     if (paramString != null)
/*      */       if (paramString.startsWith("+")) {
/*      */         int i = Integer.valueOf(paramString.substring(1)).intValue();
/*      */         setBaseFontSize(this.baseFontSize + i);
/*      */       } else if (paramString.startsWith("-")) {
/*      */         int i = -Integer.valueOf(paramString.substring(1)).intValue();
/*      */         setBaseFontSize(this.baseFontSize + i);
/*      */       } else {
/*      */         setBaseFontSize(Integer.valueOf(paramString).intValue());
/*      */       }  
/*      */   }
/*      */   
/*      */   int getBaseFontSize() {
/*      */     return this.baseFontSize;
/*      */   }
/*      */   
/*      */   void addInternalCSSValue(MutableAttributeSet paramMutableAttributeSet, Attribute paramAttribute, String paramString) {
/*      */     if (paramAttribute == Attribute.FONT) {
/*      */       ShorthandFontParser.parseShorthandFont(this, paramString, paramMutableAttributeSet);
/*      */     } else if (paramAttribute == Attribute.BACKGROUND) {
/*      */       ShorthandBackgroundParser.parseShorthandBackground(this, paramString, paramMutableAttributeSet);
/*      */     } else if (paramAttribute == Attribute.MARGIN) {
/*      */       ShorthandMarginParser.parseShorthandMargin(this, paramString, paramMutableAttributeSet, Attribute.ALL_MARGINS);
/*      */     } else if (paramAttribute == Attribute.PADDING) {
/*      */       ShorthandMarginParser.parseShorthandMargin(this, paramString, paramMutableAttributeSet, Attribute.ALL_PADDING);
/*      */     } else if (paramAttribute == Attribute.BORDER_WIDTH) {
/*      */       ShorthandMarginParser.parseShorthandMargin(this, paramString, paramMutableAttributeSet, Attribute.ALL_BORDER_WIDTHS);
/*      */     } else if (paramAttribute == Attribute.BORDER_COLOR) {
/*      */       ShorthandMarginParser.parseShorthandMargin(this, paramString, paramMutableAttributeSet, Attribute.ALL_BORDER_COLORS);
/*      */     } else if (paramAttribute == Attribute.BORDER_STYLE) {
/*      */       ShorthandMarginParser.parseShorthandMargin(this, paramString, paramMutableAttributeSet, Attribute.ALL_BORDER_STYLES);
/*      */     } else if (paramAttribute == Attribute.BORDER || paramAttribute == Attribute.BORDER_TOP || paramAttribute == Attribute.BORDER_RIGHT || paramAttribute == Attribute.BORDER_BOTTOM || paramAttribute == Attribute.BORDER_LEFT) {
/*      */       ShorthandBorderParser.parseShorthandBorder(paramMutableAttributeSet, paramAttribute, paramString);
/*      */     } else {
/*      */       Object object = getInternalCSSValue(paramAttribute, paramString);
/*      */       if (object != null)
/*      */         paramMutableAttributeSet.addAttribute(paramAttribute, object); 
/*      */     } 
/*      */   }
/*      */   
/*      */   Object getInternalCSSValue(Attribute paramAttribute, String paramString) {
/*      */     CssValue cssValue = (CssValue)this.valueConvertor.get(paramAttribute);
/*      */     Object object = cssValue.parseCssValue(paramString);
/*      */     return (object != null) ? object : cssValue.parseCssValue(paramAttribute.getDefaultValue());
/*      */   }
/*      */   
/*      */   Attribute styleConstantsKeyToCSSKey(StyleConstants paramStyleConstants) {
/*      */     return styleConstantToCssMap.get(paramStyleConstants);
/*      */   }
/*      */   
/*      */   Object styleConstantsValueToCSSValue(StyleConstants paramStyleConstants, Object paramObject) {
/*      */     Attribute attribute = styleConstantsKeyToCSSKey(paramStyleConstants);
/*      */     if (attribute != null) {
/*      */       CssValue cssValue = (CssValue)this.valueConvertor.get(attribute);
/*      */       return cssValue.fromStyleConstants(paramStyleConstants, paramObject);
/*      */     } 
/*      */     return null;
/*      */   }
/*      */   
/*      */   Object cssValueToStyleConstantsValue(StyleConstants paramStyleConstants, Object paramObject) {
/*      */     if (paramObject instanceof CssValue)
/*      */       return ((CssValue)paramObject).toStyleConstants(paramStyleConstants, null); 
/*      */     return null;
/*      */   }
/*      */   
/*      */   Font getFont(StyleContext paramStyleContext, AttributeSet paramAttributeSet, int paramInt, StyleSheet paramStyleSheet) {
/*      */     paramStyleSheet = getStyleSheet(paramStyleSheet);
/*      */     int i = getFontSize(paramAttributeSet, paramInt, paramStyleSheet);
/*      */     StringValue stringValue = (StringValue)paramAttributeSet.getAttribute(Attribute.VERTICAL_ALIGN);
/*      */     if (stringValue != null) {
/*      */       String str1 = stringValue.toString();
/*      */       if (str1.indexOf("sup") >= 0 || str1.indexOf("sub") >= 0)
/*      */         i -= 2; 
/*      */     } 
/*      */     FontFamily fontFamily = (FontFamily)paramAttributeSet.getAttribute(Attribute.FONT_FAMILY);
/*      */     String str = (fontFamily != null) ? fontFamily.getValue() : "SansSerif";
/*      */     int j = 0;
/*      */     FontWeight fontWeight = (FontWeight)paramAttributeSet.getAttribute(Attribute.FONT_WEIGHT);
/*      */     if (fontWeight != null && fontWeight.getValue() > 400)
/*      */       j |= 0x1; 
/*      */     Object object = paramAttributeSet.getAttribute(Attribute.FONT_STYLE);
/*      */     if (object != null && object.toString().indexOf("italic") >= 0)
/*      */       j |= 0x2; 
/*      */     if (str.equalsIgnoreCase("monospace"))
/*      */       str = "Monospaced"; 
/*      */     Font font = paramStyleContext.getFont(str, j, i);
/*      */     if (font == null || (font.getFamily().equals("Dialog") && !str.equalsIgnoreCase("Dialog"))) {
/*      */       str = "SansSerif";
/*      */       font = paramStyleContext.getFont(str, j, i);
/*      */     } 
/*      */     return font;
/*      */   }
/*      */   
/*      */   static int getFontSize(AttributeSet paramAttributeSet, int paramInt, StyleSheet paramStyleSheet) {
/*      */     FontSize fontSize = (FontSize)paramAttributeSet.getAttribute(Attribute.FONT_SIZE);
/*      */     return (fontSize != null) ? fontSize.getValue(paramAttributeSet, paramStyleSheet) : paramInt;
/*      */   }
/*      */   
/*      */   Color getColor(AttributeSet paramAttributeSet, Attribute paramAttribute) {
/*      */     ColorValue colorValue = (ColorValue)paramAttributeSet.getAttribute(paramAttribute);
/*      */     if (colorValue != null)
/*      */       return colorValue.getValue(); 
/*      */     return null;
/*      */   }
/*      */   
/*      */   float getPointSize(String paramString, StyleSheet paramStyleSheet) {
/*      */     paramStyleSheet = getStyleSheet(paramStyleSheet);
/*      */     if (paramString != null) {
/*      */       if (paramString.startsWith("+")) {
/*      */         int j = Integer.valueOf(paramString.substring(1)).intValue();
/*      */         return getPointSize(this.baseFontSize + j, paramStyleSheet);
/*      */       } 
/*      */       if (paramString.startsWith("-")) {
/*      */         int j = -Integer.valueOf(paramString.substring(1)).intValue();
/*      */         return getPointSize(this.baseFontSize + j, paramStyleSheet);
/*      */       } 
/*      */       int i = Integer.valueOf(paramString).intValue();
/*      */       return getPointSize(i, paramStyleSheet);
/*      */     } 
/*      */     return 0.0F;
/*      */   }
/*      */   
/*      */   float getLength(AttributeSet paramAttributeSet, Attribute paramAttribute, StyleSheet paramStyleSheet) {
/*      */     paramStyleSheet = getStyleSheet(paramStyleSheet);
/*      */     LengthValue lengthValue = (LengthValue)paramAttributeSet.getAttribute(paramAttribute);
/*      */     boolean bool = (paramStyleSheet == null) ? false : paramStyleSheet.isW3CLengthUnits();
/*      */     return (lengthValue != null) ? lengthValue.getValue(bool) : 0.0F;
/*      */   }
/*      */   
/*      */   AttributeSet translateHTMLToCSS(AttributeSet paramAttributeSet) {
/*      */     SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/*      */     Element element = (Element)paramAttributeSet;
/*      */     HTML.Tag tag = getHTMLTag(paramAttributeSet);
/*      */     if (tag == HTML.Tag.TD || tag == HTML.Tag.TH) {
/*      */       AttributeSet attributeSet = element.getParentElement().getParentElement().getAttributes();
/*      */       int i = getTableBorder(attributeSet);
/*      */       if (i > 0)
/*      */         translateAttribute(HTML.Attribute.BORDER, "1", simpleAttributeSet); 
/*      */       String str = (String)attributeSet.getAttribute(HTML.Attribute.CELLPADDING);
/*      */       if (str != null) {
/*      */         LengthValue lengthValue = (LengthValue)getInternalCSSValue(Attribute.PADDING_TOP, str);
/*      */         lengthValue.span = (lengthValue.span < 0.0F) ? 0.0F : lengthValue.span;
/*      */         simpleAttributeSet.addAttribute(Attribute.PADDING_TOP, lengthValue);
/*      */         simpleAttributeSet.addAttribute(Attribute.PADDING_BOTTOM, lengthValue);
/*      */         simpleAttributeSet.addAttribute(Attribute.PADDING_LEFT, lengthValue);
/*      */         simpleAttributeSet.addAttribute(Attribute.PADDING_RIGHT, lengthValue);
/*      */       } 
/*      */     } 
/*      */     if (element.isLeaf()) {
/*      */       translateEmbeddedAttributes(paramAttributeSet, simpleAttributeSet);
/*      */     } else {
/*      */       translateAttributes(tag, paramAttributeSet, simpleAttributeSet);
/*      */     } 
/*      */     if (tag == HTML.Tag.CAPTION) {
/*      */       Object object = paramAttributeSet.getAttribute(HTML.Attribute.ALIGN);
/*      */       if (object != null && (object.equals("top") || object.equals("bottom"))) {
/*      */         simpleAttributeSet.addAttribute(Attribute.CAPTION_SIDE, object);
/*      */         simpleAttributeSet.removeAttribute(Attribute.TEXT_ALIGN);
/*      */       } else {
/*      */         object = paramAttributeSet.getAttribute(HTML.Attribute.VALIGN);
/*      */         if (object != null)
/*      */           simpleAttributeSet.addAttribute(Attribute.CAPTION_SIDE, object); 
/*      */       } 
/*      */     } 
/*      */     return simpleAttributeSet;
/*      */   }
/*      */   
/*      */   private static int getTableBorder(AttributeSet paramAttributeSet) {
/*      */     String str = (String)paramAttributeSet.getAttribute(HTML.Attribute.BORDER);
/*      */     if (str == "#DEFAULT" || "".equals(str))
/*      */       return 1; 
/*      */     try {
/*      */       return Integer.parseInt(str);
/*      */     } catch (NumberFormatException numberFormatException) {
/*      */       return 0;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static final Hashtable<String, Attribute> attributeMap = new Hashtable<>();
/*      */   private static final Hashtable<String, Value> valueMap = new Hashtable<>();
/*      */   private static final Hashtable<HTML.Attribute, Attribute[]> htmlAttrToCssAttrMap = (Hashtable)new Hashtable<>(20);
/*      */   private static final Hashtable<Object, Attribute> styleConstantToCssMap = new Hashtable<>(17);
/*      */   private static final Hashtable<String, Value> htmlValueToCssValueMap = new Hashtable<>(8);
/*      */   private static final Hashtable<String, Value> cssValueToInternalValueMap = new Hashtable<>(13);
/*      */   private transient Hashtable<Object, Object> valueConvertor;
/*      */   private int baseFontSize;
/*      */   private transient StyleSheet styleSheet;
/*      */   
/*      */   static {
/*      */     byte b;
/*      */     for (b = 0; b < Attribute.allAttributes.length; b++)
/*      */       attributeMap.put(Attribute.allAttributes[b].toString(), Attribute.allAttributes[b]); 
/*      */     for (b = 0; b < Value.allValues.length; b++)
/*      */       valueMap.put(Value.allValues[b].toString(), Value.allValues[b]); 
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.COLOR, new Attribute[] { Attribute.COLOR });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.TEXT, new Attribute[] { Attribute.COLOR });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.CLEAR, new Attribute[] { Attribute.CLEAR });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.BACKGROUND, new Attribute[] { Attribute.BACKGROUND_IMAGE });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.BGCOLOR, new Attribute[] { Attribute.BACKGROUND_COLOR });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.WIDTH, new Attribute[] { Attribute.WIDTH });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.HEIGHT, new Attribute[] { Attribute.HEIGHT });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.BORDER, new Attribute[] { Attribute.BORDER_TOP_WIDTH, Attribute.BORDER_RIGHT_WIDTH, Attribute.BORDER_BOTTOM_WIDTH, Attribute.BORDER_LEFT_WIDTH });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.CELLPADDING, new Attribute[] { Attribute.PADDING });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.CELLSPACING, new Attribute[] { Attribute.BORDER_SPACING });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.MARGINWIDTH, new Attribute[] { Attribute.MARGIN_LEFT, Attribute.MARGIN_RIGHT });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.MARGINHEIGHT, new Attribute[] { Attribute.MARGIN_TOP, Attribute.MARGIN_BOTTOM });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.HSPACE, new Attribute[] { Attribute.PADDING_LEFT, Attribute.PADDING_RIGHT });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.VSPACE, new Attribute[] { Attribute.PADDING_BOTTOM, Attribute.PADDING_TOP });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.FACE, new Attribute[] { Attribute.FONT_FAMILY });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.SIZE, new Attribute[] { Attribute.FONT_SIZE });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.VALIGN, new Attribute[] { Attribute.VERTICAL_ALIGN });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.ALIGN, new Attribute[] { Attribute.VERTICAL_ALIGN, Attribute.TEXT_ALIGN, Attribute.FLOAT });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.TYPE, new Attribute[] { Attribute.LIST_STYLE_TYPE });
/*      */     htmlAttrToCssAttrMap.put(HTML.Attribute.NOWRAP, new Attribute[] { Attribute.WHITE_SPACE });
/*      */     styleConstantToCssMap.put(StyleConstants.FontFamily, Attribute.FONT_FAMILY);
/*      */     styleConstantToCssMap.put(StyleConstants.FontSize, Attribute.FONT_SIZE);
/*      */     styleConstantToCssMap.put(StyleConstants.Bold, Attribute.FONT_WEIGHT);
/*      */     styleConstantToCssMap.put(StyleConstants.Italic, Attribute.FONT_STYLE);
/*      */     styleConstantToCssMap.put(StyleConstants.Underline, Attribute.TEXT_DECORATION);
/*      */     styleConstantToCssMap.put(StyleConstants.StrikeThrough, Attribute.TEXT_DECORATION);
/*      */     styleConstantToCssMap.put(StyleConstants.Superscript, Attribute.VERTICAL_ALIGN);
/*      */     styleConstantToCssMap.put(StyleConstants.Subscript, Attribute.VERTICAL_ALIGN);
/*      */     styleConstantToCssMap.put(StyleConstants.Foreground, Attribute.COLOR);
/*      */     styleConstantToCssMap.put(StyleConstants.Background, Attribute.BACKGROUND_COLOR);
/*      */     styleConstantToCssMap.put(StyleConstants.FirstLineIndent, Attribute.TEXT_INDENT);
/*      */     styleConstantToCssMap.put(StyleConstants.LeftIndent, Attribute.MARGIN_LEFT);
/*      */     styleConstantToCssMap.put(StyleConstants.RightIndent, Attribute.MARGIN_RIGHT);
/*      */     styleConstantToCssMap.put(StyleConstants.SpaceAbove, Attribute.MARGIN_TOP);
/*      */     styleConstantToCssMap.put(StyleConstants.SpaceBelow, Attribute.MARGIN_BOTTOM);
/*      */     styleConstantToCssMap.put(StyleConstants.Alignment, Attribute.TEXT_ALIGN);
/*      */     htmlValueToCssValueMap.put("disc", Value.DISC);
/*      */     htmlValueToCssValueMap.put("square", Value.SQUARE);
/*      */     htmlValueToCssValueMap.put("circle", Value.CIRCLE);
/*      */     htmlValueToCssValueMap.put("1", Value.DECIMAL);
/*      */     htmlValueToCssValueMap.put("a", Value.LOWER_ALPHA);
/*      */     htmlValueToCssValueMap.put("A", Value.UPPER_ALPHA);
/*      */     htmlValueToCssValueMap.put("i", Value.LOWER_ROMAN);
/*      */     htmlValueToCssValueMap.put("I", Value.UPPER_ROMAN);
/*      */     cssValueToInternalValueMap.put("none", Value.NONE);
/*      */     cssValueToInternalValueMap.put("disc", Value.DISC);
/*      */     cssValueToInternalValueMap.put("square", Value.SQUARE);
/*      */     cssValueToInternalValueMap.put("circle", Value.CIRCLE);
/*      */     cssValueToInternalValueMap.put("decimal", Value.DECIMAL);
/*      */     cssValueToInternalValueMap.put("lower-roman", Value.LOWER_ROMAN);
/*      */     cssValueToInternalValueMap.put("upper-roman", Value.UPPER_ROMAN);
/*      */     cssValueToInternalValueMap.put("lower-alpha", Value.LOWER_ALPHA);
/*      */     cssValueToInternalValueMap.put("upper-alpha", Value.UPPER_ALPHA);
/*      */     cssValueToInternalValueMap.put("repeat", Value.BACKGROUND_REPEAT);
/*      */     cssValueToInternalValueMap.put("no-repeat", Value.BACKGROUND_NO_REPEAT);
/*      */     cssValueToInternalValueMap.put("repeat-x", Value.BACKGROUND_REPEAT_X);
/*      */     cssValueToInternalValueMap.put("repeat-y", Value.BACKGROUND_REPEAT_Y);
/*      */     cssValueToInternalValueMap.put("scroll", Value.BACKGROUND_SCROLL);
/*      */     cssValueToInternalValueMap.put("fixed", Value.BACKGROUND_FIXED);
/*      */     Attribute[] arrayOfAttribute = Attribute.allAttributes;
/*      */     try {
/*      */       for (Attribute attribute : arrayOfAttribute)
/*      */         StyleContext.registerStaticAttributeKey(attribute); 
/*      */     } catch (Throwable throwable) {
/*      */       throwable.printStackTrace();
/*      */     } 
/*      */     Value[] arrayOfValue = Value.allValues;
/*      */     try {
/*      */       for (Value value : arrayOfValue)
/*      */         StyleContext.registerStaticAttributeKey(value); 
/*      */     } catch (Throwable throwable) {
/*      */       throwable.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static Attribute[] getAllAttributeKeys() {
/*      */     Attribute[] arrayOfAttribute = new Attribute[Attribute.allAttributes.length];
/*      */     System.arraycopy(Attribute.allAttributes, 0, arrayOfAttribute, 0, Attribute.allAttributes.length);
/*      */     return arrayOfAttribute;
/*      */   }
/*      */   
/*      */   public static final Attribute getAttribute(String paramString) {
/*      */     return attributeMap.get(paramString);
/*      */   }
/*      */   
/*      */   static final Value getValue(String paramString) {
/*      */     return valueMap.get(paramString);
/*      */   }
/*      */   
/*      */   static URL getURL(URL paramURL, String paramString) {
/*      */     if (paramString == null)
/*      */       return null; 
/*      */     if (paramString.startsWith("url(") && paramString.endsWith(")"))
/*      */       paramString = paramString.substring(4, paramString.length() - 1); 
/*      */     try {
/*      */       URL uRL = new URL(paramString);
/*      */       if (uRL != null)
/*      */         return uRL; 
/*      */     } catch (MalformedURLException malformedURLException) {}
/*      */     if (paramURL != null)
/*      */       try {
/*      */         return new URL(paramURL, paramString);
/*      */       } catch (MalformedURLException malformedURLException) {} 
/*      */     return null;
/*      */   }
/*      */   
/*      */   static String colorToHex(Color paramColor) {
/*      */     String str1 = "#";
/*      */     String str2 = Integer.toHexString(paramColor.getRed());
/*      */     if (str2.length() > 2) {
/*      */       str2 = str2.substring(0, 2);
/*      */     } else if (str2.length() < 2) {
/*      */       str1 = str1 + "0" + str2;
/*      */     } else {
/*      */       str1 = str1 + str2;
/*      */     } 
/*      */     str2 = Integer.toHexString(paramColor.getGreen());
/*      */     if (str2.length() > 2) {
/*      */       str2 = str2.substring(0, 2);
/*      */     } else if (str2.length() < 2) {
/*      */       str1 = str1 + "0" + str2;
/*      */     } else {
/*      */       str1 = str1 + str2;
/*      */     } 
/*      */     str2 = Integer.toHexString(paramColor.getBlue());
/*      */     if (str2.length() > 2) {
/*      */       str2 = str2.substring(0, 2);
/*      */     } else if (str2.length() < 2) {
/*      */       str1 = str1 + "0" + str2;
/*      */     } else {
/*      */       str1 = str1 + str2;
/*      */     } 
/*      */     return str1;
/*      */   }
/*      */   
/*      */   static final Color hexToColor(String paramString) {
/*      */     String str1;
/*      */     Color color;
/*      */     int i = paramString.length();
/*      */     if (paramString.startsWith("#")) {
/*      */       str1 = paramString.substring(1, Math.min(paramString.length(), 7));
/*      */     } else {
/*      */       str1 = paramString;
/*      */     } 
/*      */     String str2 = "0x" + str1;
/*      */     try {
/*      */       color = Color.decode(str2);
/*      */     } catch (NumberFormatException numberFormatException) {
/*      */       color = null;
/*      */     } 
/*      */     return color;
/*      */   }
/*      */   
/*      */   static Color stringToColor(String paramString) {
/*      */     Color color;
/*      */     if (paramString == null)
/*      */       return null; 
/*      */     if (paramString.length() == 0) {
/*      */       color = Color.black;
/*      */     } else if (paramString.startsWith("rgb(")) {
/*      */       color = parseRGB(paramString);
/*      */     } else if (paramString.charAt(0) == '#') {
/*      */       color = hexToColor(paramString);
/*      */     } else if (paramString.equalsIgnoreCase("Black")) {
/*      */       color = hexToColor("#000000");
/*      */     } else if (paramString.equalsIgnoreCase("Silver")) {
/*      */       color = hexToColor("#C0C0C0");
/*      */     } else if (paramString.equalsIgnoreCase("Gray")) {
/*      */       color = hexToColor("#808080");
/*      */     } else if (paramString.equalsIgnoreCase("White")) {
/*      */       color = hexToColor("#FFFFFF");
/*      */     } else if (paramString.equalsIgnoreCase("Maroon")) {
/*      */       color = hexToColor("#800000");
/*      */     } else if (paramString.equalsIgnoreCase("Red")) {
/*      */       color = hexToColor("#FF0000");
/*      */     } else if (paramString.equalsIgnoreCase("Purple")) {
/*      */       color = hexToColor("#800080");
/*      */     } else if (paramString.equalsIgnoreCase("Fuchsia")) {
/*      */       color = hexToColor("#FF00FF");
/*      */     } else if (paramString.equalsIgnoreCase("Green")) {
/*      */       color = hexToColor("#008000");
/*      */     } else if (paramString.equalsIgnoreCase("Lime")) {
/*      */       color = hexToColor("#00FF00");
/*      */     } else if (paramString.equalsIgnoreCase("Olive")) {
/*      */       color = hexToColor("#808000");
/*      */     } else if (paramString.equalsIgnoreCase("Yellow")) {
/*      */       color = hexToColor("#FFFF00");
/*      */     } else if (paramString.equalsIgnoreCase("Navy")) {
/*      */       color = hexToColor("#000080");
/*      */     } else if (paramString.equalsIgnoreCase("Blue")) {
/*      */       color = hexToColor("#0000FF");
/*      */     } else if (paramString.equalsIgnoreCase("Teal")) {
/*      */       color = hexToColor("#008080");
/*      */     } else if (paramString.equalsIgnoreCase("Aqua")) {
/*      */       color = hexToColor("#00FFFF");
/*      */     } else if (paramString.equalsIgnoreCase("Orange")) {
/*      */       color = hexToColor("#FF8000");
/*      */     } else {
/*      */       color = hexToColor(paramString);
/*      */     } 
/*      */     return color;
/*      */   }
/*      */   
/*      */   private static Color parseRGB(String paramString) {
/*      */     int[] arrayOfInt = new int[1];
/*      */     arrayOfInt[0] = 4;
/*      */     int i = getColorComponent(paramString, arrayOfInt);
/*      */     int j = getColorComponent(paramString, arrayOfInt);
/*      */     int k = getColorComponent(paramString, arrayOfInt);
/*      */     return new Color(i, j, k);
/*      */   }
/*      */   
/*      */   private static int getColorComponent(String paramString, int[] paramArrayOfint) {
/*      */     int i = paramString.length();
/*      */     char c;
/*      */     while (paramArrayOfint[0] < i && (c = paramString.charAt(paramArrayOfint[0])) != '-' && !Character.isDigit(c) && c != '.')
/*      */       paramArrayOfint[0] = paramArrayOfint[0] + 1; 
/*      */     int j = paramArrayOfint[0];
/*      */     if (j < i && paramString.charAt(paramArrayOfint[0]) == '-')
/*      */       paramArrayOfint[0] = paramArrayOfint[0] + 1; 
/*      */     while (paramArrayOfint[0] < i && Character.isDigit(paramString.charAt(paramArrayOfint[0])))
/*      */       paramArrayOfint[0] = paramArrayOfint[0] + 1; 
/*      */     if (paramArrayOfint[0] < i && paramString.charAt(paramArrayOfint[0]) == '.') {
/*      */       paramArrayOfint[0] = paramArrayOfint[0] + 1;
/*      */       while (paramArrayOfint[0] < i && Character.isDigit(paramString.charAt(paramArrayOfint[0])))
/*      */         paramArrayOfint[0] = paramArrayOfint[0] + 1; 
/*      */     } 
/*      */     if (j != paramArrayOfint[0])
/*      */       try {
/*      */         float f = Float.parseFloat(paramString.substring(j, paramArrayOfint[0]));
/*      */         if (paramArrayOfint[0] < i && paramString.charAt(paramArrayOfint[0]) == '%') {
/*      */           paramArrayOfint[0] = paramArrayOfint[0] + 1;
/*      */           f = f * 255.0F / 100.0F;
/*      */         } 
/*      */         return Math.min(255, Math.max(0, (int)f));
/*      */       } catch (NumberFormatException numberFormatException) {} 
/*      */     return 0;
/*      */   }
/*      */   
/*      */   static int getIndexOfSize(float paramFloat, int[] paramArrayOfint) {
/*      */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/*      */       if (paramFloat <= paramArrayOfint[b])
/*      */         return b + 1; 
/*      */     } 
/*      */     return paramArrayOfint.length;
/*      */   }
/*      */   
/*      */   static int getIndexOfSize(float paramFloat, StyleSheet paramStyleSheet) {
/*      */     int[] arrayOfInt = (paramStyleSheet != null) ? paramStyleSheet.getSizeMap() : StyleSheet.sizeMapDefault;
/*      */     return getIndexOfSize(paramFloat, arrayOfInt);
/*      */   }
/*      */   
/*      */   static String[] parseStrings(String paramString) {
/*      */     byte b2 = (paramString == null) ? 0 : paramString.length();
/*      */     Vector<String> vector = new Vector(4);
/*      */     byte b1 = 0;
/*      */     while (b1 < b2) {
/*      */       while (b1 < b2 && Character.isWhitespace(paramString.charAt(b1)))
/*      */         b1++; 
/*      */       byte b = b1;
/*      */       while (b1 < b2 && !Character.isWhitespace(paramString.charAt(b1)))
/*      */         b1++; 
/*      */       if (b != b1)
/*      */         vector.addElement(paramString.substring(b, b1)); 
/*      */       b1++;
/*      */     } 
/*      */     String[] arrayOfString = new String[vector.size()];
/*      */     vector.copyInto((Object[])arrayOfString);
/*      */     return arrayOfString;
/*      */   }
/*      */   
/*      */   float getPointSize(int paramInt, StyleSheet paramStyleSheet) {
/*      */     paramStyleSheet = getStyleSheet(paramStyleSheet);
/*      */     int[] arrayOfInt = (paramStyleSheet != null) ? paramStyleSheet.getSizeMap() : StyleSheet.sizeMapDefault;
/*      */     paramInt--;
/*      */     if (paramInt < 0)
/*      */       return arrayOfInt[0]; 
/*      */     if (paramInt > arrayOfInt.length - 1)
/*      */       return arrayOfInt[arrayOfInt.length - 1]; 
/*      */     return arrayOfInt[paramInt];
/*      */   }
/*      */   
/*      */   private void translateEmbeddedAttributes(AttributeSet paramAttributeSet, MutableAttributeSet paramMutableAttributeSet) {
/*      */     Enumeration<?> enumeration = paramAttributeSet.getAttributeNames();
/*      */     if (paramAttributeSet.getAttribute(StyleConstants.NameAttribute) == HTML.Tag.HR)
/*      */       translateAttributes(HTML.Tag.HR, paramAttributeSet, paramMutableAttributeSet); 
/*      */     while (enumeration.hasMoreElements()) {
/*      */       Object object = enumeration.nextElement();
/*      */       if (object instanceof HTML.Tag) {
/*      */         HTML.Tag tag = (HTML.Tag)object;
/*      */         Object object1 = paramAttributeSet.getAttribute(tag);
/*      */         if (object1 != null && object1 instanceof AttributeSet)
/*      */           translateAttributes(tag, (AttributeSet)object1, paramMutableAttributeSet); 
/*      */         continue;
/*      */       } 
/*      */       if (object instanceof Attribute)
/*      */         paramMutableAttributeSet.addAttribute(object, paramAttributeSet.getAttribute(object)); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void translateAttributes(HTML.Tag paramTag, AttributeSet paramAttributeSet, MutableAttributeSet paramMutableAttributeSet) {
/*      */     Enumeration<?> enumeration = paramAttributeSet.getAttributeNames();
/*      */     while (enumeration.hasMoreElements()) {
/*      */       Object object = enumeration.nextElement();
/*      */       if (object instanceof HTML.Attribute) {
/*      */         HTML.Attribute attribute = (HTML.Attribute)object;
/*      */         if (attribute == HTML.Attribute.ALIGN) {
/*      */           String str = (String)paramAttributeSet.getAttribute(HTML.Attribute.ALIGN);
/*      */           if (str != null) {
/*      */             Attribute attribute1 = getCssAlignAttribute(paramTag, paramAttributeSet);
/*      */             if (attribute1 != null) {
/*      */               Object object1 = getCssValue(attribute1, str);
/*      */               if (object1 != null)
/*      */                 paramMutableAttributeSet.addAttribute(attribute1, object1); 
/*      */             } 
/*      */           } 
/*      */           continue;
/*      */         } 
/*      */         if (attribute == HTML.Attribute.SIZE && !isHTMLFontTag(paramTag))
/*      */           continue; 
/*      */         if (paramTag == HTML.Tag.TABLE && attribute == HTML.Attribute.BORDER) {
/*      */           int i = getTableBorder(paramAttributeSet);
/*      */           if (i > 0)
/*      */             translateAttribute(HTML.Attribute.BORDER, Integer.toString(i), paramMutableAttributeSet); 
/*      */           continue;
/*      */         } 
/*      */         translateAttribute(attribute, (String)paramAttributeSet.getAttribute(attribute), paramMutableAttributeSet);
/*      */         continue;
/*      */       } 
/*      */       if (object instanceof Attribute)
/*      */         paramMutableAttributeSet.addAttribute(object, paramAttributeSet.getAttribute(object)); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void translateAttribute(HTML.Attribute paramAttribute, String paramString, MutableAttributeSet paramMutableAttributeSet) {
/*      */     Attribute[] arrayOfAttribute = getCssAttribute(paramAttribute);
/*      */     if (arrayOfAttribute == null || paramString == null)
/*      */       return; 
/*      */     for (Attribute attribute : arrayOfAttribute) {
/*      */       Object object = getCssValue(attribute, paramString);
/*      */       if (object != null)
/*      */         paramMutableAttributeSet.addAttribute(attribute, object); 
/*      */     } 
/*      */   }
/*      */   
/*      */   Object getCssValue(Attribute paramAttribute, String paramString) {
/*      */     CssValue cssValue = (CssValue)this.valueConvertor.get(paramAttribute);
/*      */     return cssValue.parseHtmlValue(paramString);
/*      */   }
/*      */   
/*      */   private Attribute[] getCssAttribute(HTML.Attribute paramAttribute) {
/*      */     return htmlAttrToCssAttrMap.get(paramAttribute);
/*      */   }
/*      */   
/*      */   private Attribute getCssAlignAttribute(HTML.Tag paramTag, AttributeSet paramAttributeSet) {
/*      */     return Attribute.TEXT_ALIGN;
/*      */   }
/*      */   
/*      */   private HTML.Tag getHTMLTag(AttributeSet paramAttributeSet) {
/*      */     Object object = paramAttributeSet.getAttribute(StyleConstants.NameAttribute);
/*      */     if (object instanceof HTML.Tag)
/*      */       return (HTML.Tag)object; 
/*      */     return null;
/*      */   }
/*      */   
/*      */   private boolean isHTMLFontTag(HTML.Tag paramTag) {
/*      */     return (paramTag != null && (paramTag == HTML.Tag.FONT || paramTag == HTML.Tag.BASEFONT));
/*      */   }
/*      */   
/*      */   private boolean isFloater(String paramString) {
/*      */     return (paramString.equals("left") || paramString.equals("right"));
/*      */   }
/*      */   
/*      */   private boolean validTextAlignValue(String paramString) {
/*      */     return (isFloater(paramString) || paramString.equals("center"));
/*      */   }
/*      */   
/*      */   static class CssValue implements Serializable {
/*      */     String svalue;
/*      */     
/*      */     Object parseCssValue(String param1String) {
/*      */       return param1String;
/*      */     }
/*      */     
/*      */     Object parseHtmlValue(String param1String) {
/*      */       return parseCssValue(param1String);
/*      */     }
/*      */     
/*      */     Object fromStyleConstants(StyleConstants param1StyleConstants, Object param1Object) {
/*      */       return null;
/*      */     }
/*      */     
/*      */     Object toStyleConstants(StyleConstants param1StyleConstants, View param1View) {
/*      */       return null;
/*      */     }
/*      */     
/*      */     public String toString() {
/*      */       return this.svalue;
/*      */     }
/*      */   }
/*      */   
/*      */   static class StringValue extends CssValue {
/*      */     Object parseCssValue(String param1String) {
/*      */       StringValue stringValue = new StringValue();
/*      */       stringValue.svalue = param1String;
/*      */       return stringValue;
/*      */     }
/*      */     
/*      */     Object fromStyleConstants(StyleConstants param1StyleConstants, Object param1Object) {
/*      */       if (param1StyleConstants == StyleConstants.Italic) {
/*      */         if (param1Object.equals(Boolean.TRUE))
/*      */           return parseCssValue("italic"); 
/*      */         return parseCssValue("");
/*      */       } 
/*      */       if (param1StyleConstants == StyleConstants.Underline) {
/*      */         if (param1Object.equals(Boolean.TRUE))
/*      */           return parseCssValue("underline"); 
/*      */         return parseCssValue("");
/*      */       } 
/*      */       if (param1StyleConstants == StyleConstants.Alignment) {
/*      */         int i = ((Integer)param1Object).intValue();
/*      */         switch (i) {
/*      */           case 0:
/*      */             str = "left";
/*      */             return parseCssValue(str);
/*      */           case 2:
/*      */             str = "right";
/*      */             return parseCssValue(str);
/*      */           case 1:
/*      */             str = "center";
/*      */             return parseCssValue(str);
/*      */           case 3:
/*      */             str = "justify";
/*      */             return parseCssValue(str);
/*      */         } 
/*      */         String str = "left";
/*      */         return parseCssValue(str);
/*      */       } 
/*      */       if (param1StyleConstants == StyleConstants.StrikeThrough) {
/*      */         if (param1Object.equals(Boolean.TRUE))
/*      */           return parseCssValue("line-through"); 
/*      */         return parseCssValue("");
/*      */       } 
/*      */       if (param1StyleConstants == StyleConstants.Superscript) {
/*      */         if (param1Object.equals(Boolean.TRUE))
/*      */           return parseCssValue("super"); 
/*      */         return parseCssValue("");
/*      */       } 
/*      */       if (param1StyleConstants == StyleConstants.Subscript) {
/*      */         if (param1Object.equals(Boolean.TRUE))
/*      */           return parseCssValue("sub"); 
/*      */         return parseCssValue("");
/*      */       } 
/*      */       return null;
/*      */     }
/*      */     
/*      */     Object toStyleConstants(StyleConstants param1StyleConstants, View param1View) {
/*      */       if (param1StyleConstants == StyleConstants.Italic) {
/*      */         if (this.svalue.indexOf("italic") >= 0)
/*      */           return Boolean.TRUE; 
/*      */         return Boolean.FALSE;
/*      */       } 
/*      */       if (param1StyleConstants == StyleConstants.Underline) {
/*      */         if (this.svalue.indexOf("underline") >= 0)
/*      */           return Boolean.TRUE; 
/*      */         return Boolean.FALSE;
/*      */       } 
/*      */       if (param1StyleConstants == StyleConstants.Alignment) {
/*      */         if (this.svalue.equals("right"))
/*      */           return new Integer(2); 
/*      */         if (this.svalue.equals("center"))
/*      */           return new Integer(1); 
/*      */         if (this.svalue.equals("justify"))
/*      */           return new Integer(3); 
/*      */         return new Integer(0);
/*      */       } 
/*      */       if (param1StyleConstants == StyleConstants.StrikeThrough) {
/*      */         if (this.svalue.indexOf("line-through") >= 0)
/*      */           return Boolean.TRUE; 
/*      */         return Boolean.FALSE;
/*      */       } 
/*      */       if (param1StyleConstants == StyleConstants.Superscript) {
/*      */         if (this.svalue.indexOf("super") >= 0)
/*      */           return Boolean.TRUE; 
/*      */         return Boolean.FALSE;
/*      */       } 
/*      */       if (param1StyleConstants == StyleConstants.Subscript) {
/*      */         if (this.svalue.indexOf("sub") >= 0)
/*      */           return Boolean.TRUE; 
/*      */         return Boolean.FALSE;
/*      */       } 
/*      */       return null;
/*      */     }
/*      */     
/*      */     boolean isItalic() {
/*      */       return (this.svalue.indexOf("italic") != -1);
/*      */     }
/*      */     
/*      */     boolean isStrike() {
/*      */       return (this.svalue.indexOf("line-through") != -1);
/*      */     }
/*      */     
/*      */     boolean isUnderline() {
/*      */       return (this.svalue.indexOf("underline") != -1);
/*      */     }
/*      */     
/*      */     boolean isSub() {
/*      */       return (this.svalue.indexOf("sub") != -1);
/*      */     }
/*      */     
/*      */     boolean isSup() {
/*      */       return (this.svalue.indexOf("sup") != -1);
/*      */     }
/*      */   }
/*      */   
/*      */   class FontSize extends CssValue {
/*      */     float value;
/*      */     boolean index;
/*      */     CSS.LengthUnit lu;
/*      */     
/*      */     int getValue(AttributeSet param1AttributeSet, StyleSheet param1StyleSheet) {
/*      */       param1StyleSheet = CSS.this.getStyleSheet(param1StyleSheet);
/*      */       if (this.index)
/*      */         return Math.round(CSS.this.getPointSize((int)this.value, param1StyleSheet)); 
/*      */       if (this.lu == null)
/*      */         return Math.round(this.value); 
/*      */       if (this.lu.type == 0) {
/*      */         boolean bool = (param1StyleSheet == null) ? false : param1StyleSheet.isW3CLengthUnits();
/*      */         return Math.round(this.lu.getValue(bool));
/*      */       } 
/*      */       if (param1AttributeSet != null) {
/*      */         AttributeSet attributeSet = param1AttributeSet.getResolveParent();
/*      */         if (attributeSet != null) {
/*      */           float f;
/*      */           int i = StyleConstants.getFontSize(attributeSet);
/*      */           if (this.lu.type == 1 || this.lu.type == 3) {
/*      */             f = this.lu.value * i;
/*      */           } else {
/*      */             f = this.lu.value + i;
/*      */           } 
/*      */           return Math.round(f);
/*      */         } 
/*      */       } 
/*      */       return 12;
/*      */     }
/*      */     
/*      */     Object parseCssValue(String param1String) {
/*      */       FontSize fontSize = new FontSize();
/*      */       fontSize.svalue = param1String;
/*      */       try {
/*      */         if (param1String.equals("xx-small")) {
/*      */           fontSize.value = 1.0F;
/*      */           fontSize.index = true;
/*      */         } else if (param1String.equals("x-small")) {
/*      */           fontSize.value = 2.0F;
/*      */           fontSize.index = true;
/*      */         } else if (param1String.equals("small")) {
/*      */           fontSize.value = 3.0F;
/*      */           fontSize.index = true;
/*      */         } else if (param1String.equals("medium")) {
/*      */           fontSize.value = 4.0F;
/*      */           fontSize.index = true;
/*      */         } else if (param1String.equals("large")) {
/*      */           fontSize.value = 5.0F;
/*      */           fontSize.index = true;
/*      */         } else if (param1String.equals("x-large")) {
/*      */           fontSize.value = 6.0F;
/*      */           fontSize.index = true;
/*      */         } else if (param1String.equals("xx-large")) {
/*      */           fontSize.value = 7.0F;
/*      */           fontSize.index = true;
/*      */         } else {
/*      */           fontSize.lu = new CSS.LengthUnit(param1String, (short)1, 1.0F);
/*      */         } 
/*      */       } catch (NumberFormatException numberFormatException) {
/*      */         fontSize = null;
/*      */       } 
/*      */       return fontSize;
/*      */     }
/*      */     
/*      */     Object parseHtmlValue(String param1String) {
/*      */       if (param1String == null || param1String.length() == 0)
/*      */         return null; 
/*      */       FontSize fontSize = new FontSize();
/*      */       fontSize.svalue = param1String;
/*      */       try {
/*      */         int i = CSS.this.getBaseFontSize();
/*      */         if (param1String.charAt(0) == '+') {
/*      */           int j = Integer.valueOf(param1String.substring(1)).intValue();
/*      */           fontSize.value = (i + j);
/*      */           fontSize.index = true;
/*      */         } else if (param1String.charAt(0) == '-') {
/*      */           int j = -Integer.valueOf(param1String.substring(1)).intValue();
/*      */           fontSize.value = (i + j);
/*      */           fontSize.index = true;
/*      */         } else {
/*      */           fontSize.value = Integer.parseInt(param1String);
/*      */           if (fontSize.value > 7.0F) {
/*      */             fontSize.value = 7.0F;
/*      */           } else if (fontSize.value < 0.0F) {
/*      */             fontSize.value = 0.0F;
/*      */           } 
/*      */           fontSize.index = true;
/*      */         } 
/*      */       } catch (NumberFormatException numberFormatException) {
/*      */         fontSize = null;
/*      */       } 
/*      */       return fontSize;
/*      */     }
/*      */     
/*      */     Object fromStyleConstants(StyleConstants param1StyleConstants, Object param1Object) {
/*      */       if (param1Object instanceof Number) {
/*      */         FontSize fontSize = new FontSize();
/*      */         fontSize.value = CSS.getIndexOfSize(((Number)param1Object).floatValue(), StyleSheet.sizeMapDefault);
/*      */         fontSize.svalue = Integer.toString((int)fontSize.value);
/*      */         fontSize.index = true;
/*      */         return fontSize;
/*      */       } 
/*      */       return parseCssValue(param1Object.toString());
/*      */     }
/*      */     
/*      */     Object toStyleConstants(StyleConstants param1StyleConstants, View param1View) {
/*      */       if (param1View != null)
/*      */         return Integer.valueOf(getValue(param1View.getAttributes(), null)); 
/*      */       return Integer.valueOf(getValue(null, null));
/*      */     }
/*      */   }
/*      */   
/*      */   static class FontFamily extends CssValue {
/*      */     String family;
/*      */     
/*      */     String getValue() {
/*      */       return this.family;
/*      */     }
/*      */     
/*      */     Object parseCssValue(String param1String) {
/*      */       int i = param1String.indexOf(',');
/*      */       FontFamily fontFamily = new FontFamily();
/*      */       fontFamily.svalue = param1String;
/*      */       fontFamily.family = null;
/*      */       if (i == -1) {
/*      */         setFontName(fontFamily, param1String);
/*      */       } else {
/*      */         boolean bool = false;
/*      */         int j = param1String.length();
/*      */         i = 0;
/*      */         while (!bool) {
/*      */           while (i < j && Character.isWhitespace(param1String.charAt(i)))
/*      */             i++; 
/*      */           int k = i;
/*      */           i = param1String.indexOf(',', i);
/*      */           if (i == -1)
/*      */             i = j; 
/*      */           if (k < j) {
/*      */             if (k != i) {
/*      */               int m = i;
/*      */               if (i > 0 && param1String.charAt(i - 1) == ' ')
/*      */                 m--; 
/*      */               setFontName(fontFamily, param1String.substring(k, m));
/*      */               bool = (fontFamily.family != null) ? true : false;
/*      */             } 
/*      */             i++;
/*      */             continue;
/*      */           } 
/*      */           bool = true;
/*      */         } 
/*      */       } 
/*      */       if (fontFamily.family == null)
/*      */         fontFamily.family = "SansSerif"; 
/*      */       return fontFamily;
/*      */     }
/*      */     
/*      */     private void setFontName(FontFamily param1FontFamily, String param1String) {
/*      */       param1FontFamily.family = param1String;
/*      */     }
/*      */     
/*      */     Object parseHtmlValue(String param1String) {
/*      */       return parseCssValue(param1String);
/*      */     }
/*      */     
/*      */     Object fromStyleConstants(StyleConstants param1StyleConstants, Object param1Object) {
/*      */       return parseCssValue(param1Object.toString());
/*      */     }
/*      */     
/*      */     Object toStyleConstants(StyleConstants param1StyleConstants, View param1View) {
/*      */       return this.family;
/*      */     }
/*      */   }
/*      */   
/*      */   static class FontWeight extends CssValue {
/*      */     int weight;
/*      */     
/*      */     int getValue() {
/*      */       return this.weight;
/*      */     }
/*      */     
/*      */     Object parseCssValue(String param1String) {
/*      */       FontWeight fontWeight = new FontWeight();
/*      */       fontWeight.svalue = param1String;
/*      */       if (param1String.equals("bold")) {
/*      */         fontWeight.weight = 700;
/*      */       } else if (param1String.equals("normal")) {
/*      */         fontWeight.weight = 400;
/*      */       } else {
/*      */         try {
/*      */           fontWeight.weight = Integer.parseInt(param1String);
/*      */         } catch (NumberFormatException numberFormatException) {
/*      */           fontWeight = null;
/*      */         } 
/*      */       } 
/*      */       return fontWeight;
/*      */     }
/*      */     
/*      */     Object fromStyleConstants(StyleConstants param1StyleConstants, Object param1Object) {
/*      */       if (param1Object.equals(Boolean.TRUE))
/*      */         return parseCssValue("bold"); 
/*      */       return parseCssValue("normal");
/*      */     }
/*      */     
/*      */     Object toStyleConstants(StyleConstants param1StyleConstants, View param1View) {
/*      */       return (this.weight > 500) ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*      */     
/*      */     boolean isBold() {
/*      */       return (this.weight > 500);
/*      */     }
/*      */   }
/*      */   
/*      */   static class ColorValue extends CssValue {
/*      */     Color c;
/*      */     
/*      */     Color getValue() {
/*      */       return this.c;
/*      */     }
/*      */     
/*      */     Object parseCssValue(String param1String) {
/*      */       Color color = CSS.stringToColor(param1String);
/*      */       if (color != null) {
/*      */         ColorValue colorValue = new ColorValue();
/*      */         colorValue.svalue = param1String;
/*      */         colorValue.c = color;
/*      */         return colorValue;
/*      */       } 
/*      */       return null;
/*      */     }
/*      */     
/*      */     Object parseHtmlValue(String param1String) {
/*      */       return parseCssValue(param1String);
/*      */     }
/*      */     
/*      */     Object fromStyleConstants(StyleConstants param1StyleConstants, Object param1Object) {
/*      */       ColorValue colorValue = new ColorValue();
/*      */       colorValue.c = (Color)param1Object;
/*      */       colorValue.svalue = CSS.colorToHex(colorValue.c);
/*      */       return colorValue;
/*      */     }
/*      */     
/*      */     Object toStyleConstants(StyleConstants param1StyleConstants, View param1View) {
/*      */       return this.c;
/*      */     }
/*      */   }
/*      */   
/*      */   static class BorderStyle extends CssValue {
/*      */     private transient CSS.Value style;
/*      */     
/*      */     CSS.Value getValue() {
/*      */       return this.style;
/*      */     }
/*      */     
/*      */     Object parseCssValue(String param1String) {
/*      */       CSS.Value value = CSS.getValue(param1String);
/*      */       if (value != null && (value == CSS.Value.INSET || value == CSS.Value.OUTSET || value == CSS.Value.NONE || value == CSS.Value.DOTTED || value == CSS.Value.DASHED || value == CSS.Value.SOLID || value == CSS.Value.DOUBLE || value == CSS.Value.GROOVE || value == CSS.Value.RIDGE)) {
/*      */         BorderStyle borderStyle = new BorderStyle();
/*      */         borderStyle.svalue = param1String;
/*      */         borderStyle.style = value;
/*      */         return borderStyle;
/*      */       } 
/*      */       return null;
/*      */     }
/*      */     
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/*      */       param1ObjectOutputStream.defaultWriteObject();
/*      */       if (this.style == null) {
/*      */         param1ObjectOutputStream.writeObject(null);
/*      */       } else {
/*      */         param1ObjectOutputStream.writeObject(this.style.toString());
/*      */       } 
/*      */     }
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws ClassNotFoundException, IOException {
/*      */       param1ObjectInputStream.defaultReadObject();
/*      */       Object object = param1ObjectInputStream.readObject();
/*      */       if (object != null)
/*      */         this.style = CSS.getValue((String)object); 
/*      */     }
/*      */   }
/*      */   
/*      */   static class LengthValue extends CssValue {
/*      */     boolean mayBeNegative;
/*      */     boolean percentage;
/*      */     float span;
/*      */     String units;
/*      */     
/*      */     LengthValue() {
/*      */       this(false);
/*      */     }
/*      */     
/*      */     LengthValue(boolean param1Boolean) {
/*      */       this.units = null;
/*      */       this.mayBeNegative = param1Boolean;
/*      */     }
/*      */     
/*      */     float getValue() {
/*      */       return getValue(false);
/*      */     }
/*      */     
/*      */     float getValue(boolean param1Boolean) {
/*      */       return getValue(0.0F, param1Boolean);
/*      */     }
/*      */     
/*      */     float getValue(float param1Float) {
/*      */       return getValue(param1Float, false);
/*      */     }
/*      */     
/*      */     float getValue(float param1Float, boolean param1Boolean) {
/*      */       if (this.percentage)
/*      */         return this.span * param1Float; 
/*      */       return CSS.LengthUnit.getValue(this.span, this.units, Boolean.valueOf(param1Boolean));
/*      */     }
/*      */     
/*      */     boolean isPercentage() {
/*      */       return this.percentage;
/*      */     }
/*      */     
/*      */     Object parseCssValue(String param1String) {
/*      */       LengthValue lengthValue;
/*      */       try {
/*      */         float f = Float.valueOf(param1String).floatValue();
/*      */         lengthValue = new LengthValue();
/*      */         lengthValue.span = f;
/*      */       } catch (NumberFormatException numberFormatException) {
/*      */         CSS.LengthUnit lengthUnit = new CSS.LengthUnit(param1String, (short)10, 0.0F);
/*      */         switch (lengthUnit.type) {
/*      */           case 0:
/*      */             lengthValue = new LengthValue();
/*      */             lengthValue.span = this.mayBeNegative ? lengthUnit.value : Math.max(0.0F, lengthUnit.value);
/*      */             lengthValue.units = lengthUnit.units;
/*      */             lengthValue.svalue = param1String;
/*      */             return lengthValue;
/*      */           case 1:
/*      */             lengthValue = new LengthValue();
/*      */             lengthValue.span = Math.max(0.0F, Math.min(1.0F, lengthUnit.value));
/*      */             lengthValue.percentage = true;
/*      */             lengthValue.svalue = param1String;
/*      */             return lengthValue;
/*      */         } 
/*      */         return null;
/*      */       } 
/*      */       lengthValue.svalue = param1String;
/*      */       return lengthValue;
/*      */     }
/*      */     
/*      */     Object parseHtmlValue(String param1String) {
/*      */       if (param1String.equals("#DEFAULT"))
/*      */         param1String = "1"; 
/*      */       return parseCssValue(param1String);
/*      */     }
/*      */     
/*      */     Object fromStyleConstants(StyleConstants param1StyleConstants, Object param1Object) {
/*      */       LengthValue lengthValue = new LengthValue();
/*      */       lengthValue.svalue = param1Object.toString();
/*      */       lengthValue.span = ((Float)param1Object).floatValue();
/*      */       return lengthValue;
/*      */     }
/*      */     
/*      */     Object toStyleConstants(StyleConstants param1StyleConstants, View param1View) {
/*      */       return new Float(getValue(false));
/*      */     }
/*      */   }
/*      */   
/*      */   static class BorderWidthValue extends LengthValue {
/*      */     BorderWidthValue(String param1String, int param1Int) {
/*      */       this.svalue = param1String;
/*      */       this.span = values[param1Int];
/*      */       this.percentage = false;
/*      */     }
/*      */     
/*      */     Object parseCssValue(String param1String) {
/*      */       if (param1String != null) {
/*      */         if (param1String.equals("thick"))
/*      */           return new BorderWidthValue(param1String, 2); 
/*      */         if (param1String.equals("medium"))
/*      */           return new BorderWidthValue(param1String, 1); 
/*      */         if (param1String.equals("thin"))
/*      */           return new BorderWidthValue(param1String, 0); 
/*      */       } 
/*      */       return super.parseCssValue(param1String);
/*      */     }
/*      */     
/*      */     Object parseHtmlValue(String param1String) {
/*      */       if (param1String == "#DEFAULT")
/*      */         return parseCssValue("medium"); 
/*      */       return parseCssValue(param1String);
/*      */     }
/*      */     
/*      */     private static final float[] values = new float[] { 1.0F, 2.0F, 4.0F };
/*      */   }
/*      */   
/*      */   static class CssValueMapper extends CssValue {
/*      */     Object parseCssValue(String param1String) {
/*      */       Object object = CSS.cssValueToInternalValueMap.get(param1String);
/*      */       if (object == null)
/*      */         object = CSS.cssValueToInternalValueMap.get(param1String.toLowerCase()); 
/*      */       return object;
/*      */     }
/*      */     
/*      */     Object parseHtmlValue(String param1String) {
/*      */       Object object = CSS.htmlValueToCssValueMap.get(param1String);
/*      */       if (object == null)
/*      */         object = CSS.htmlValueToCssValueMap.get(param1String.toLowerCase()); 
/*      */       return object;
/*      */     }
/*      */   }
/*      */   
/*      */   static class BackgroundPosition extends CssValue {
/*      */     float horizontalPosition;
/*      */     float verticalPosition;
/*      */     short relative;
/*      */     
/*      */     Object parseCssValue(String param1String) {
/*      */       String[] arrayOfString = CSS.parseStrings(param1String);
/*      */       int i = arrayOfString.length;
/*      */       BackgroundPosition backgroundPosition = new BackgroundPosition();
/*      */       backgroundPosition.relative = 5;
/*      */       backgroundPosition.svalue = param1String;
/*      */       if (i > 0) {
/*      */         short s = 0;
/*      */         byte b = 0;
/*      */         while (b < i) {
/*      */           String str = arrayOfString[b++];
/*      */           if (str.equals("center")) {
/*      */             s = (short)(s | 0x4);
/*      */             continue;
/*      */           } 
/*      */           if ((s & 0x1) == 0)
/*      */             if (str.equals("top")) {
/*      */               s = (short)(s | 0x1);
/*      */             } else if (str.equals("bottom")) {
/*      */               s = (short)(s | 0x1);
/*      */               backgroundPosition.verticalPosition = 1.0F;
/*      */               continue;
/*      */             }  
/*      */           if ((s & 0x2) == 0) {
/*      */             if (str.equals("left")) {
/*      */               s = (short)(s | 0x2);
/*      */               backgroundPosition.horizontalPosition = 0.0F;
/*      */               continue;
/*      */             } 
/*      */             if (str.equals("right")) {
/*      */               s = (short)(s | 0x2);
/*      */               backgroundPosition.horizontalPosition = 1.0F;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         if (s != 0) {
/*      */           if ((s & 0x1) == 1) {
/*      */             if ((s & 0x2) == 0)
/*      */               backgroundPosition.horizontalPosition = 0.5F; 
/*      */           } else if ((s & 0x2) == 2) {
/*      */             backgroundPosition.verticalPosition = 0.5F;
/*      */           } else {
/*      */             backgroundPosition.horizontalPosition = backgroundPosition.verticalPosition = 0.5F;
/*      */           } 
/*      */         } else {
/*      */           CSS.LengthUnit lengthUnit = new CSS.LengthUnit(arrayOfString[0], (short)0, 0.0F);
/*      */           if (lengthUnit.type == 0) {
/*      */             backgroundPosition.horizontalPosition = lengthUnit.value;
/*      */             backgroundPosition.relative = (short)(0x1 ^ backgroundPosition.relative);
/*      */           } else if (lengthUnit.type == 1) {
/*      */             backgroundPosition.horizontalPosition = lengthUnit.value;
/*      */           } else if (lengthUnit.type == 3) {
/*      */             backgroundPosition.horizontalPosition = lengthUnit.value;
/*      */             backgroundPosition.relative = (short)(0x1 ^ backgroundPosition.relative | 0x2);
/*      */           } 
/*      */           if (i > 1) {
/*      */             lengthUnit = new CSS.LengthUnit(arrayOfString[1], (short)0, 0.0F);
/*      */             if (lengthUnit.type == 0) {
/*      */               backgroundPosition.verticalPosition = lengthUnit.value;
/*      */               backgroundPosition.relative = (short)(0x4 ^ backgroundPosition.relative);
/*      */             } else if (lengthUnit.type == 1) {
/*      */               backgroundPosition.verticalPosition = lengthUnit.value;
/*      */             } else if (lengthUnit.type == 3) {
/*      */               backgroundPosition.verticalPosition = lengthUnit.value;
/*      */               backgroundPosition.relative = (short)(0x4 ^ backgroundPosition.relative | 0x8);
/*      */             } 
/*      */           } else {
/*      */             backgroundPosition.verticalPosition = 0.5F;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       return backgroundPosition;
/*      */     }
/*      */     
/*      */     boolean isHorizontalPositionRelativeToSize() {
/*      */       return ((this.relative & 0x1) == 1);
/*      */     }
/*      */     
/*      */     boolean isHorizontalPositionRelativeToFontSize() {
/*      */       return ((this.relative & 0x2) == 2);
/*      */     }
/*      */     
/*      */     float getHorizontalPosition() {
/*      */       return this.horizontalPosition;
/*      */     }
/*      */     
/*      */     boolean isVerticalPositionRelativeToSize() {
/*      */       return ((this.relative & 0x4) == 4);
/*      */     }
/*      */     
/*      */     boolean isVerticalPositionRelativeToFontSize() {
/*      */       return ((this.relative & 0x8) == 8);
/*      */     }
/*      */     
/*      */     float getVerticalPosition() {
/*      */       return this.verticalPosition;
/*      */     }
/*      */   }
/*      */   
/*      */   static class BackgroundImage extends CssValue {
/*      */     private boolean loadedImage;
/*      */     private ImageIcon image;
/*      */     
/*      */     Object parseCssValue(String param1String) {
/*      */       BackgroundImage backgroundImage = new BackgroundImage();
/*      */       backgroundImage.svalue = param1String;
/*      */       return backgroundImage;
/*      */     }
/*      */     
/*      */     Object parseHtmlValue(String param1String) {
/*      */       return parseCssValue(param1String);
/*      */     }
/*      */     
/*      */     ImageIcon getImage(URL param1URL) {
/*      */       if (!this.loadedImage)
/*      */         synchronized (this) {
/*      */           if (!this.loadedImage) {
/*      */             URL uRL = CSS.getURL(param1URL, this.svalue);
/*      */             this.loadedImage = true;
/*      */             if (uRL != null) {
/*      */               this.image = new ImageIcon();
/*      */               Image image = Toolkit.getDefaultToolkit().createImage(uRL);
/*      */               if (image != null)
/*      */                 this.image.setImage(image); 
/*      */             } 
/*      */           } 
/*      */         }  
/*      */       return this.image;
/*      */     }
/*      */   }
/*      */   
/*      */   static class LengthUnit implements Serializable {
/*      */     static Hashtable<String, Float> lengthMapping = new Hashtable<>(6);
/*      */     static Hashtable<String, Float> w3cLengthMapping = new Hashtable<>(6);
/*      */     short type;
/*      */     float value;
/*      */     String units;
/*      */     static final short UNINITALIZED_LENGTH = 10;
/*      */     
/*      */     static {
/*      */       lengthMapping.put("pt", new Float(1.0F));
/*      */       lengthMapping.put("px", new Float(1.3F));
/*      */       lengthMapping.put("mm", new Float(2.83464F));
/*      */       lengthMapping.put("cm", new Float(28.3464F));
/*      */       lengthMapping.put("pc", new Float(12.0F));
/*      */       lengthMapping.put("in", new Float(72.0F));
/*      */       int i = 72;
/*      */       try {
/*      */         i = Toolkit.getDefaultToolkit().getScreenResolution();
/*      */       } catch (HeadlessException headlessException) {}
/*      */       w3cLengthMapping.put("pt", new Float(i / 72.0F));
/*      */       w3cLengthMapping.put("px", new Float(1.0F));
/*      */       w3cLengthMapping.put("mm", new Float(i / 25.4F));
/*      */       w3cLengthMapping.put("cm", new Float(i / 2.54F));
/*      */       w3cLengthMapping.put("pc", new Float(i / 6.0F));
/*      */       w3cLengthMapping.put("in", new Float(i));
/*      */     }
/*      */     
/*      */     LengthUnit(String param1String, short param1Short, float param1Float) {
/*      */       this.units = null;
/*      */       parse(param1String, param1Short, param1Float);
/*      */     }
/*      */     
/*      */     void parse(String param1String, short param1Short, float param1Float) {
/*      */       this.type = param1Short;
/*      */       this.value = param1Float;
/*      */       int i = param1String.length();
/*      */       if (i > 0 && param1String.charAt(i - 1) == '%')
/*      */         try {
/*      */           this.value = Float.valueOf(param1String.substring(0, i - 1)).floatValue() / 100.0F;
/*      */           this.type = 1;
/*      */         } catch (NumberFormatException numberFormatException) {} 
/*      */       if (i >= 2) {
/*      */         this.units = param1String.substring(i - 2, i);
/*      */         Float float_ = lengthMapping.get(this.units);
/*      */         if (float_ != null) {
/*      */           try {
/*      */             this.value = Float.valueOf(param1String.substring(0, i - 2)).floatValue();
/*      */             this.type = 0;
/*      */           } catch (NumberFormatException numberFormatException) {}
/*      */         } else if (this.units.equals("em") || this.units.equals("ex")) {
/*      */           try {
/*      */             this.value = Float.valueOf(param1String.substring(0, i - 2)).floatValue();
/*      */             this.type = 3;
/*      */           } catch (NumberFormatException numberFormatException) {}
/*      */         } else if (param1String.equals("larger")) {
/*      */           this.value = 2.0F;
/*      */           this.type = 2;
/*      */         } else if (param1String.equals("smaller")) {
/*      */           this.value = -2.0F;
/*      */           this.type = 2;
/*      */         } else {
/*      */           try {
/*      */             this.value = Float.valueOf(param1String).floatValue();
/*      */             this.type = 0;
/*      */           } catch (NumberFormatException numberFormatException) {}
/*      */         } 
/*      */       } else if (i > 0) {
/*      */         try {
/*      */           this.value = Float.valueOf(param1String).floatValue();
/*      */           this.type = 0;
/*      */         } catch (NumberFormatException numberFormatException) {}
/*      */       } 
/*      */     }
/*      */     
/*      */     float getValue(boolean param1Boolean) {
/*      */       Hashtable<String, Float> hashtable = param1Boolean ? w3cLengthMapping : lengthMapping;
/*      */       float f = 1.0F;
/*      */       if (this.units != null) {
/*      */         Float float_ = hashtable.get(this.units);
/*      */         if (float_ != null)
/*      */           f = float_.floatValue(); 
/*      */       } 
/*      */       return this.value * f;
/*      */     }
/*      */     
/*      */     static float getValue(float param1Float, String param1String, Boolean param1Boolean) {
/*      */       Hashtable<String, Float> hashtable = param1Boolean.booleanValue() ? w3cLengthMapping : lengthMapping;
/*      */       float f = 1.0F;
/*      */       if (param1String != null) {
/*      */         Float float_ = hashtable.get(param1String);
/*      */         if (float_ != null)
/*      */           f = float_.floatValue(); 
/*      */       } 
/*      */       return param1Float * f;
/*      */     }
/*      */     
/*      */     public String toString() {
/*      */       return this.type + " " + this.value;
/*      */     }
/*      */   }
/*      */   
/*      */   static class ShorthandFontParser {
/*      */     static void parseShorthandFont(CSS param1CSS, String param1String, MutableAttributeSet param1MutableAttributeSet) {
/*      */       String[] arrayOfString = CSS.parseStrings(param1String);
/*      */       int i = arrayOfString.length;
/*      */       byte b = 0;
/*      */       short s = 0;
/*      */       int j = Math.min(3, i);
/*      */       while (b < j) {
/*      */         if ((s & 0x1) == 0 && isFontStyle(arrayOfString[b])) {
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.FONT_STYLE, arrayOfString[b++]);
/*      */           s = (short)(s | 0x1);
/*      */           continue;
/*      */         } 
/*      */         if ((s & 0x2) == 0 && isFontVariant(arrayOfString[b])) {
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.FONT_VARIANT, arrayOfString[b++]);
/*      */           s = (short)(s | 0x2);
/*      */           continue;
/*      */         } 
/*      */         if ((s & 0x4) == 0 && isFontWeight(arrayOfString[b])) {
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.FONT_WEIGHT, arrayOfString[b++]);
/*      */           s = (short)(s | 0x4);
/*      */           continue;
/*      */         } 
/*      */         if (arrayOfString[b].equals("normal"))
/*      */           b++; 
/*      */       } 
/*      */       if ((s & 0x1) == 0)
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.FONT_STYLE, "normal"); 
/*      */       if ((s & 0x2) == 0)
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.FONT_VARIANT, "normal"); 
/*      */       if ((s & 0x4) == 0)
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.FONT_WEIGHT, "normal"); 
/*      */       if (b < i) {
/*      */         String str = arrayOfString[b];
/*      */         int k = str.indexOf('/');
/*      */         if (k != -1) {
/*      */           str = str.substring(0, k);
/*      */           arrayOfString[b] = arrayOfString[b].substring(k);
/*      */         } else {
/*      */           b++;
/*      */         } 
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.FONT_SIZE, str);
/*      */       } else {
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.FONT_SIZE, "medium");
/*      */       } 
/*      */       if (b < i && arrayOfString[b].startsWith("/")) {
/*      */         String str = null;
/*      */         if (arrayOfString[b].equals("/")) {
/*      */           if (++b < i)
/*      */             str = arrayOfString[b++]; 
/*      */         } else {
/*      */           str = arrayOfString[b++].substring(1);
/*      */         } 
/*      */         if (str != null) {
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.LINE_HEIGHT, str);
/*      */         } else {
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.LINE_HEIGHT, "normal");
/*      */         } 
/*      */       } else {
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.LINE_HEIGHT, "normal");
/*      */       } 
/*      */       if (b < i) {
/*      */         String str = arrayOfString[b++];
/*      */         while (b < i)
/*      */           str = str + " " + arrayOfString[b++]; 
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.FONT_FAMILY, str);
/*      */       } else {
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.FONT_FAMILY, "SansSerif");
/*      */       } 
/*      */     }
/*      */     
/*      */     private static boolean isFontStyle(String param1String) {
/*      */       return (param1String.equals("italic") || param1String.equals("oblique"));
/*      */     }
/*      */     
/*      */     private static boolean isFontVariant(String param1String) {
/*      */       return param1String.equals("small-caps");
/*      */     }
/*      */     
/*      */     private static boolean isFontWeight(String param1String) {
/*      */       if (param1String.equals("bold") || param1String.equals("bolder") || param1String.equals("italic") || param1String.equals("lighter"))
/*      */         return true; 
/*      */       return (param1String.length() == 3 && param1String.charAt(0) >= '1' && param1String.charAt(0) <= '9' && param1String.charAt(1) == '0' && param1String.charAt(2) == '0');
/*      */     }
/*      */   }
/*      */   
/*      */   static class ShorthandBackgroundParser {
/*      */     static void parseShorthandBackground(CSS param1CSS, String param1String, MutableAttributeSet param1MutableAttributeSet) {
/*      */       String[] arrayOfString = CSS.parseStrings(param1String);
/*      */       int i = arrayOfString.length;
/*      */       byte b = 0;
/*      */       short s = 0;
/*      */       while (b < i) {
/*      */         String str = arrayOfString[b++];
/*      */         if ((s & 0x1) == 0 && isImage(str)) {
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.BACKGROUND_IMAGE, str);
/*      */           s = (short)(s | 0x1);
/*      */           continue;
/*      */         } 
/*      */         if ((s & 0x2) == 0 && isRepeat(str)) {
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.BACKGROUND_REPEAT, str);
/*      */           s = (short)(s | 0x2);
/*      */           continue;
/*      */         } 
/*      */         if ((s & 0x4) == 0 && isAttachment(str)) {
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.BACKGROUND_ATTACHMENT, str);
/*      */           s = (short)(s | 0x4);
/*      */           continue;
/*      */         } 
/*      */         if ((s & 0x8) == 0 && isPosition(str)) {
/*      */           if (b < i && isPosition(arrayOfString[b])) {
/*      */             param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.BACKGROUND_POSITION, str + " " + arrayOfString[b++]);
/*      */           } else {
/*      */             param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.BACKGROUND_POSITION, str);
/*      */           } 
/*      */           s = (short)(s | 0x8);
/*      */           continue;
/*      */         } 
/*      */         if ((s & 0x10) == 0 && isColor(str)) {
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.BACKGROUND_COLOR, str);
/*      */           s = (short)(s | 0x10);
/*      */         } 
/*      */       } 
/*      */       if ((s & 0x1) == 0)
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.BACKGROUND_IMAGE, null); 
/*      */       if ((s & 0x2) == 0)
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.BACKGROUND_REPEAT, "repeat"); 
/*      */       if ((s & 0x4) == 0)
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.BACKGROUND_ATTACHMENT, "scroll"); 
/*      */       if ((s & 0x8) == 0)
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, CSS.Attribute.BACKGROUND_POSITION, null); 
/*      */     }
/*      */     
/*      */     static boolean isImage(String param1String) {
/*      */       return (param1String.startsWith("url(") && param1String.endsWith(")"));
/*      */     }
/*      */     
/*      */     static boolean isRepeat(String param1String) {
/*      */       return (param1String.equals("repeat-x") || param1String.equals("repeat-y") || param1String.equals("repeat") || param1String.equals("no-repeat"));
/*      */     }
/*      */     
/*      */     static boolean isAttachment(String param1String) {
/*      */       return (param1String.equals("fixed") || param1String.equals("scroll"));
/*      */     }
/*      */     
/*      */     static boolean isPosition(String param1String) {
/*      */       return (param1String.equals("top") || param1String.equals("bottom") || param1String.equals("left") || param1String.equals("right") || param1String.equals("center") || (param1String.length() > 0 && Character.isDigit(param1String.charAt(0))));
/*      */     }
/*      */     
/*      */     static boolean isColor(String param1String) {
/*      */       return (CSS.stringToColor(param1String) != null);
/*      */     }
/*      */   }
/*      */   
/*      */   static class ShorthandMarginParser {
/*      */     static void parseShorthandMargin(CSS param1CSS, String param1String, MutableAttributeSet param1MutableAttributeSet, CSS.Attribute[] param1ArrayOfAttribute) {
/*      */       String[] arrayOfString = CSS.parseStrings(param1String);
/*      */       int i = arrayOfString.length;
/*      */       boolean bool = false;
/*      */       switch (i) {
/*      */         case 0:
/*      */           return;
/*      */         case 1:
/*      */           for (b = 0; b < 4; b++)
/*      */             param1CSS.addInternalCSSValue(param1MutableAttributeSet, param1ArrayOfAttribute[b], arrayOfString[0]); 
/*      */           return;
/*      */         case 2:
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, param1ArrayOfAttribute[0], arrayOfString[0]);
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, param1ArrayOfAttribute[2], arrayOfString[0]);
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, param1ArrayOfAttribute[1], arrayOfString[1]);
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, param1ArrayOfAttribute[3], arrayOfString[1]);
/*      */           return;
/*      */         case 3:
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, param1ArrayOfAttribute[0], arrayOfString[0]);
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, param1ArrayOfAttribute[1], arrayOfString[1]);
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, param1ArrayOfAttribute[2], arrayOfString[2]);
/*      */           param1CSS.addInternalCSSValue(param1MutableAttributeSet, param1ArrayOfAttribute[3], arrayOfString[1]);
/*      */           return;
/*      */       } 
/*      */       for (byte b = 0; b < 4; b++)
/*      */         param1CSS.addInternalCSSValue(param1MutableAttributeSet, param1ArrayOfAttribute[b], arrayOfString[b]); 
/*      */     }
/*      */   }
/*      */   
/*      */   static class ShorthandBorderParser {
/*      */     static CSS.Attribute[] keys = new CSS.Attribute[] { CSS.Attribute.BORDER_TOP, CSS.Attribute.BORDER_RIGHT, CSS.Attribute.BORDER_BOTTOM, CSS.Attribute.BORDER_LEFT };
/*      */     
/*      */     static void parseShorthandBorder(MutableAttributeSet param1MutableAttributeSet, CSS.Attribute param1Attribute, String param1String) {
/*      */       Object[] arrayOfObject = new Object[CSSBorder.PARSERS.length];
/*      */       String[] arrayOfString = CSS.parseStrings(param1String);
/*      */       for (String str : arrayOfString) {
/*      */         boolean bool = false;
/*      */         for (byte b1 = 0; b1 < arrayOfObject.length; b1++) {
/*      */           Object object = CSSBorder.PARSERS[b1].parseCssValue(str);
/*      */           if (object != null) {
/*      */             if (arrayOfObject[b1] == null) {
/*      */               arrayOfObject[b1] = object;
/*      */               bool = true;
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         if (!bool)
/*      */           return; 
/*      */       } 
/*      */       byte b;
/*      */       for (b = 0; b < arrayOfObject.length; b++) {
/*      */         if (arrayOfObject[b] == null)
/*      */           arrayOfObject[b] = CSSBorder.DEFAULTS[b]; 
/*      */       } 
/*      */       for (b = 0; b < keys.length; b++) {
/*      */         if (param1Attribute == CSS.Attribute.BORDER || param1Attribute == keys[b])
/*      */           for (byte b1 = 0; b1 < arrayOfObject.length; b1++)
/*      */             param1MutableAttributeSet.addAttribute(CSSBorder.ATTRIBUTES[b1][b], arrayOfObject[b1]);  
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static SizeRequirements calculateTiledRequirements(LayoutIterator paramLayoutIterator, SizeRequirements paramSizeRequirements) {
/*      */     long l1 = 0L;
/*      */     long l2 = 0L;
/*      */     long l3 = 0L;
/*      */     int i = 0;
/*      */     int j = 0;
/*      */     int k = paramLayoutIterator.getCount();
/*      */     for (byte b = 0; b < k; b++) {
/*      */       paramLayoutIterator.setIndex(b);
/*      */       int m = i;
/*      */       int n = (int)paramLayoutIterator.getLeadingCollapseSpan();
/*      */       j += Math.max(m, n);
/*      */       l3 += (int)paramLayoutIterator.getPreferredSpan(0.0F);
/*      */       l1 = (long)((float)l1 + paramLayoutIterator.getMinimumSpan(0.0F));
/*      */       l2 = (long)((float)l2 + paramLayoutIterator.getMaximumSpan(0.0F));
/*      */       i = (int)paramLayoutIterator.getTrailingCollapseSpan();
/*      */     } 
/*      */     j += i;
/*      */     j = (int)(j + 2.0F * paramLayoutIterator.getBorderWidth());
/*      */     l1 += j;
/*      */     l3 += j;
/*      */     l2 += j;
/*      */     if (paramSizeRequirements == null)
/*      */       paramSizeRequirements = new SizeRequirements(); 
/*      */     paramSizeRequirements.minimum = (l1 > 2147483647L) ? Integer.MAX_VALUE : (int)l1;
/*      */     paramSizeRequirements.preferred = (l3 > 2147483647L) ? Integer.MAX_VALUE : (int)l3;
/*      */     paramSizeRequirements.maximum = (l2 > 2147483647L) ? Integer.MAX_VALUE : (int)l2;
/*      */     return paramSizeRequirements;
/*      */   }
/*      */   
/*      */   static void calculateTiledLayout(LayoutIterator paramLayoutIterator, int paramInt) {
/*      */     long l1 = 0L;
/*      */     int i = 0;
/*      */     int j = 0;
/*      */     int k = paramLayoutIterator.getCount();
/*      */     byte b1 = 3;
/*      */     long[] arrayOfLong1 = new long[b1];
/*      */     long[] arrayOfLong2 = new long[b1];
/*      */     int m;
/*      */     for (m = 0; m < b1; m++) {
/*      */       arrayOfLong2[m] = 0L;
/*      */       arrayOfLong1[m] = 0L;
/*      */     } 
/*      */     for (m = 0; m < k; m++) {
/*      */       paramLayoutIterator.setIndex(m);
/*      */       int i2 = i;
/*      */       int i3 = (int)paramLayoutIterator.getLeadingCollapseSpan();
/*      */       paramLayoutIterator.setOffset(Math.max(i2, i3));
/*      */       j += paramLayoutIterator.getOffset();
/*      */       long l = (long)paramLayoutIterator.getPreferredSpan(paramInt);
/*      */       paramLayoutIterator.setSpan((int)l);
/*      */       l1 += l;
/*      */       arrayOfLong1[paramLayoutIterator.getAdjustmentWeight()] = arrayOfLong1[paramLayoutIterator.getAdjustmentWeight()] + (long)paramLayoutIterator.getMaximumSpan(paramInt) - l;
/*      */       arrayOfLong2[paramLayoutIterator.getAdjustmentWeight()] = arrayOfLong2[paramLayoutIterator.getAdjustmentWeight()] + l - (long)paramLayoutIterator.getMinimumSpan(paramInt);
/*      */       i = (int)paramLayoutIterator.getTrailingCollapseSpan();
/*      */     } 
/*      */     j += i;
/*      */     j = (int)(j + 2.0F * paramLayoutIterator.getBorderWidth());
/*      */     for (m = 1; m < b1; m++) {
/*      */       arrayOfLong1[m] = arrayOfLong1[m] + arrayOfLong1[m - 1];
/*      */       arrayOfLong2[m] = arrayOfLong2[m] + arrayOfLong2[m - 1];
/*      */     } 
/*      */     m = paramInt - j;
/*      */     long l2 = m - l1;
/*      */     long[] arrayOfLong3 = (l2 > 0L) ? arrayOfLong1 : arrayOfLong2;
/*      */     l2 = Math.abs(l2);
/*      */     byte b2 = 0;
/*      */     for (; b2 <= 2; b2++) {
/*      */       if (arrayOfLong3[b2] >= l2)
/*      */         break; 
/*      */     } 
/*      */     float f = 0.0F;
/*      */     if (b2 <= 2) {
/*      */       l2 -= (b2 > 0) ? arrayOfLong3[b2 - 1] : 0L;
/*      */       if (l2 != 0L) {
/*      */         float f1 = (float)(arrayOfLong3[b2] - ((b2 > 0) ? arrayOfLong3[b2 - 1] : 0L));
/*      */         f = (float)l2 / f1;
/*      */       } 
/*      */     } 
/*      */     int n = (int)paramLayoutIterator.getBorderWidth();
/*      */     int i1;
/*      */     for (i1 = 0; i1 < k; i1++) {
/*      */       paramLayoutIterator.setIndex(i1);
/*      */       paramLayoutIterator.setOffset(paramLayoutIterator.getOffset() + n);
/*      */       if (paramLayoutIterator.getAdjustmentWeight() < b2) {
/*      */         paramLayoutIterator.setSpan((int)((m > l1) ? Math.floor(paramLayoutIterator.getMaximumSpan(paramInt)) : Math.ceil(paramLayoutIterator.getMinimumSpan(paramInt))));
/*      */       } else if (paramLayoutIterator.getAdjustmentWeight() == b2) {
/*      */         int i2 = (m > l1) ? ((int)paramLayoutIterator.getMaximumSpan(paramInt) - paramLayoutIterator.getSpan()) : (paramLayoutIterator.getSpan() - (int)paramLayoutIterator.getMinimumSpan(paramInt));
/*      */         int i3 = (int)Math.floor((f * i2));
/*      */         paramLayoutIterator.setSpan(paramLayoutIterator.getSpan() + ((m > l1) ? i3 : -i3));
/*      */       } 
/*      */       n = (int)Math.min(paramLayoutIterator.getOffset() + paramLayoutIterator.getSpan(), 2147483647L);
/*      */     } 
/*      */     i1 = paramInt - n - (int)paramLayoutIterator.getTrailingCollapseSpan() - (int)paramLayoutIterator.getBorderWidth();
/*      */     byte b3 = (i1 > 0) ? 1 : -1;
/*      */     i1 *= b3;
/*      */     boolean bool = true;
/*      */     while (i1 > 0 && bool) {
/*      */       bool = false;
/*      */       int i2 = 0;
/*      */       for (byte b = 0; b < k; b++) {
/*      */         paramLayoutIterator.setIndex(b);
/*      */         paramLayoutIterator.setOffset(paramLayoutIterator.getOffset() + i2);
/*      */         int i3 = paramLayoutIterator.getSpan();
/*      */         if (i1 > 0) {
/*      */           int i4 = (b3 > 0) ? ((int)Math.floor(paramLayoutIterator.getMaximumSpan(paramInt)) - i3) : (i3 - (int)Math.ceil(paramLayoutIterator.getMinimumSpan(paramInt)));
/*      */           if (i4 >= 1) {
/*      */             bool = true;
/*      */             paramLayoutIterator.setSpan(i3 + b3);
/*      */             i2 += b3;
/*      */             i1--;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*      */     paramObjectOutputStream.defaultWriteObject();
/*      */     Enumeration<Object> enumeration = this.valueConvertor.keys();
/*      */     paramObjectOutputStream.writeInt(this.valueConvertor.size());
/*      */     if (enumeration != null)
/*      */       while (enumeration.hasMoreElements()) {
/*      */         Object object1 = enumeration.nextElement();
/*      */         Object object2 = this.valueConvertor.get(object1);
/*      */         if (!(object1 instanceof Serializable) && (object1 = StyleContext.getStaticAttributeKey(object1)) == null) {
/*      */           object1 = null;
/*      */           object2 = null;
/*      */         } else if (!(object2 instanceof Serializable) && (object2 = StyleContext.getStaticAttributeKey(object2)) == null) {
/*      */           object1 = null;
/*      */           object2 = null;
/*      */         } 
/*      */         paramObjectOutputStream.writeObject(object1);
/*      */         paramObjectOutputStream.writeObject(object2);
/*      */       }  
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/*      */     paramObjectInputStream.defaultReadObject();
/*      */     int i = paramObjectInputStream.readInt();
/*      */     this.valueConvertor = new Hashtable<>();
/*      */     while (i-- > 0) {
/*      */       Object object1 = paramObjectInputStream.readObject();
/*      */       Object object2 = paramObjectInputStream.readObject();
/*      */       Object object3 = StyleContext.getStaticAttribute(object1);
/*      */       if (object3 != null)
/*      */         object1 = object3; 
/*      */       Object object4 = StyleContext.getStaticAttribute(object2);
/*      */       if (object4 != null)
/*      */         object2 = object4; 
/*      */       if (object1 != null && object2 != null)
/*      */         this.valueConvertor.put(object1, object2); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private StyleSheet getStyleSheet(StyleSheet paramStyleSheet) {
/*      */     if (paramStyleSheet != null)
/*      */       this.styleSheet = paramStyleSheet; 
/*      */     return this.styleSheet;
/*      */   }
/*      */   
/*      */   static interface LayoutIterator {
/*      */     public static final int WorstAdjustmentWeight = 2;
/*      */     
/*      */     void setOffset(int param1Int);
/*      */     
/*      */     int getOffset();
/*      */     
/*      */     void setSpan(int param1Int);
/*      */     
/*      */     int getSpan();
/*      */     
/*      */     int getCount();
/*      */     
/*      */     void setIndex(int param1Int);
/*      */     
/*      */     float getMinimumSpan(float param1Float);
/*      */     
/*      */     float getPreferredSpan(float param1Float);
/*      */     
/*      */     float getMaximumSpan(float param1Float);
/*      */     
/*      */     int getAdjustmentWeight();
/*      */     
/*      */     float getBorderWidth();
/*      */     
/*      */     float getLeadingCollapseSpan();
/*      */     
/*      */     float getTrailingCollapseSpan();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/CSS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */