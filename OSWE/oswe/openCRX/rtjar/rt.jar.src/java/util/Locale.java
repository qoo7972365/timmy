/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.spi.LocaleNameProvider;
/*      */ import java.util.spi.LocaleServiceProvider;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.util.locale.BaseLocale;
/*      */ import sun.util.locale.InternalLocaleBuilder;
/*      */ import sun.util.locale.LanguageTag;
/*      */ import sun.util.locale.LocaleExtensions;
/*      */ import sun.util.locale.LocaleMatcher;
/*      */ import sun.util.locale.LocaleObjectCache;
/*      */ import sun.util.locale.LocaleSyntaxException;
/*      */ import sun.util.locale.LocaleUtils;
/*      */ import sun.util.locale.ParseStatus;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleResources;
/*      */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Locale
/*      */   implements Cloneable, Serializable
/*      */ {
/*  486 */   private static final Cache LOCALECACHE = new Cache();
/*      */ 
/*      */ 
/*      */   
/*  490 */   public static final Locale ENGLISH = createConstant("en", "");
/*      */ 
/*      */ 
/*      */   
/*  494 */   public static final Locale FRENCH = createConstant("fr", "");
/*      */ 
/*      */ 
/*      */   
/*  498 */   public static final Locale GERMAN = createConstant("de", "");
/*      */ 
/*      */ 
/*      */   
/*  502 */   public static final Locale ITALIAN = createConstant("it", "");
/*      */ 
/*      */ 
/*      */   
/*  506 */   public static final Locale JAPANESE = createConstant("ja", "");
/*      */ 
/*      */ 
/*      */   
/*  510 */   public static final Locale KOREAN = createConstant("ko", "");
/*      */ 
/*      */ 
/*      */   
/*  514 */   public static final Locale CHINESE = createConstant("zh", "");
/*      */ 
/*      */ 
/*      */   
/*  518 */   public static final Locale SIMPLIFIED_CHINESE = createConstant("zh", "CN");
/*      */ 
/*      */ 
/*      */   
/*  522 */   public static final Locale TRADITIONAL_CHINESE = createConstant("zh", "TW");
/*      */ 
/*      */ 
/*      */   
/*  526 */   public static final Locale FRANCE = createConstant("fr", "FR");
/*      */ 
/*      */ 
/*      */   
/*  530 */   public static final Locale GERMANY = createConstant("de", "DE");
/*      */ 
/*      */ 
/*      */   
/*  534 */   public static final Locale ITALY = createConstant("it", "IT");
/*      */ 
/*      */ 
/*      */   
/*  538 */   public static final Locale JAPAN = createConstant("ja", "JP");
/*      */ 
/*      */ 
/*      */   
/*  542 */   public static final Locale KOREA = createConstant("ko", "KR");
/*      */ 
/*      */ 
/*      */   
/*  546 */   public static final Locale CHINA = SIMPLIFIED_CHINESE;
/*      */ 
/*      */ 
/*      */   
/*  550 */   public static final Locale PRC = SIMPLIFIED_CHINESE;
/*      */ 
/*      */ 
/*      */   
/*  554 */   public static final Locale TAIWAN = TRADITIONAL_CHINESE;
/*      */ 
/*      */ 
/*      */   
/*  558 */   public static final Locale UK = createConstant("en", "GB");
/*      */ 
/*      */ 
/*      */   
/*  562 */   public static final Locale US = createConstant("en", "US");
/*      */ 
/*      */ 
/*      */   
/*  566 */   public static final Locale CANADA = createConstant("en", "CA");
/*      */ 
/*      */ 
/*      */   
/*  570 */   public static final Locale CANADA_FRENCH = createConstant("fr", "CA");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  580 */   public static final Locale ROOT = createConstant("", "");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final char PRIVATE_USE_EXTENSION = 'x';
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final char UNICODE_LOCALE_EXTENSION = 'u';
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long serialVersionUID = 9149081749638150636L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DISPLAY_LANGUAGE = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DISPLAY_COUNTRY = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DISPLAY_VARIANT = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DISPLAY_SCRIPT = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient BaseLocale baseLocale;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient LocaleExtensions localeExtensions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile transient int hashCodeValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Locale(BaseLocale paramBaseLocale, LocaleExtensions paramLocaleExtensions)
/*      */   {
/* 2020 */     this.hashCodeValue = 0; this.baseLocale = paramBaseLocale; this.localeExtensions = paramLocaleExtensions; } public Locale(String paramString1, String paramString2, String paramString3) { this.hashCodeValue = 0; if (paramString1 == null || paramString2 == null || paramString3 == null) throw new NullPointerException();  this.baseLocale = BaseLocale.getInstance(convertOldISOCodes(paramString1), "", paramString2, paramString3); this.localeExtensions = getCompatibilityExtensions(paramString1, "", paramString2, paramString3); } public Locale(String paramString1, String paramString2) { this(paramString1, paramString2, ""); } public Locale(String paramString) { this(paramString, "", ""); } private static Locale createConstant(String paramString1, String paramString2) { BaseLocale baseLocale = BaseLocale.createInstance(paramString1, paramString2); return getInstance(baseLocale, null); } static Locale getInstance(String paramString1, String paramString2, String paramString3) { return getInstance(paramString1, "", paramString2, paramString3, null); } static Locale getInstance(String paramString1, String paramString2, String paramString3, String paramString4, LocaleExtensions paramLocaleExtensions) { if (paramString1 == null || paramString2 == null || paramString3 == null || paramString4 == null) throw new NullPointerException();  if (paramLocaleExtensions == null) paramLocaleExtensions = getCompatibilityExtensions(paramString1, paramString2, paramString3, paramString4);  BaseLocale baseLocale = BaseLocale.getInstance(paramString1, paramString2, paramString3, paramString4); return getInstance(baseLocale, paramLocaleExtensions); } static Locale getInstance(BaseLocale paramBaseLocale, LocaleExtensions paramLocaleExtensions) { LocaleKey localeKey = new LocaleKey(paramBaseLocale, paramLocaleExtensions); return LOCALECACHE.get(localeKey); } private static class Cache extends LocaleObjectCache<LocaleKey, Locale> {
/*      */     private Cache() {} protected Locale createObject(Locale.LocaleKey param1LocaleKey) { return new Locale(param1LocaleKey.base, param1LocaleKey.exts); } } private static final class LocaleKey {
/* 2022 */     private final BaseLocale base; private final LocaleExtensions exts; private final int hash; private LocaleKey(BaseLocale param1BaseLocale, LocaleExtensions param1LocaleExtensions) { this.base = param1BaseLocale; this.exts = param1LocaleExtensions; int i = this.base.hashCode(); if (this.exts != null) i ^= this.exts.hashCode();  this.hash = i; } public boolean equals(Object param1Object) { if (this == param1Object) return true;  if (!(param1Object instanceof LocaleKey)) return false;  LocaleKey localeKey = (LocaleKey)param1Object; if (this.hash != localeKey.hash || !this.base.equals(localeKey.base)) return false;  if (this.exts == null) return (localeKey.exts == null);  return this.exts.equals(localeKey.exts); } public int hashCode() { return this.hash; } } public static Locale getDefault() { return defaultLocale; } public static Locale getDefault(Category paramCategory) { switch (paramCategory) { case DISPLAY: if (defaultDisplayLocale == null) synchronized (Locale.class) { if (defaultDisplayLocale == null) defaultDisplayLocale = initDefault(paramCategory);  }   return defaultDisplayLocale;case FORMAT: if (defaultFormatLocale == null) synchronized (Locale.class) { if (defaultFormatLocale == null) defaultFormatLocale = initDefault(paramCategory);  }   return defaultFormatLocale; }  assert false : "Unknown Category"; return getDefault(); } private static Locale initDefault() { String str3, str4, str5, str1 = AccessController.<String>doPrivileged(new GetPropertyAction("user.language", "en")); String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("user.region")); if (str2 != null) { int i = str2.indexOf('_'); if (i >= 0) { str4 = str2.substring(0, i); str5 = str2.substring(i + 1); } else { str4 = str2; str5 = ""; }  str3 = ""; } else { str3 = AccessController.<String>doPrivileged(new GetPropertyAction("user.script", "")); str4 = AccessController.<String>doPrivileged(new GetPropertyAction("user.country", "")); str5 = AccessController.<String>doPrivileged(new GetPropertyAction("user.variant", "")); }  return getInstance(str1, str3, str4, str5, null); } private static Locale initDefault(Category paramCategory) { return getInstance(AccessController.<String>doPrivileged(new GetPropertyAction(paramCategory.languageKey, defaultLocale.getLanguage())), AccessController.<String>doPrivileged(new GetPropertyAction(paramCategory.scriptKey, defaultLocale.getScript())), AccessController.<String>doPrivileged(new GetPropertyAction(paramCategory.countryKey, defaultLocale.getCountry())), AccessController.<String>doPrivileged(new GetPropertyAction(paramCategory.variantKey, defaultLocale.getVariant())), null); } public static synchronized void setDefault(Locale paramLocale) { setDefault(Category.DISPLAY, paramLocale); setDefault(Category.FORMAT, paramLocale); defaultLocale = paramLocale; } public static synchronized void setDefault(Category paramCategory, Locale paramLocale) { if (paramCategory == null) throw new NullPointerException("Category cannot be NULL");  if (paramLocale == null) throw new NullPointerException("Can't set default locale to NULL");  SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkPermission(new PropertyPermission("user.language", "write"));  switch (paramCategory) { case DISPLAY: defaultDisplayLocale = paramLocale; return;case FORMAT: defaultFormatLocale = paramLocale; return; }  assert false : "Unknown Category"; } public static Locale[] getAvailableLocales() { return LocaleServiceProviderPool.getAllAvailableLocales(); } public static String[] getISOCountries() { if (isoCountries == null) isoCountries = getISO2Table("ADANDAEAREAFAFGAGATGAIAIAALALBAMARMANANTAOAGOAQATAARARGASASMATAUTAUAUSAWABWAXALAAZAZEBABIHBBBRBBDBGDBEBELBFBFABGBGRBHBHRBIBDIBJBENBLBLMBMBMUBNBRNBOBOLBQBESBRBRABSBHSBTBTNBVBVTBWBWABYBLRBZBLZCACANCCCCKCDCODCFCAFCGCOGCHCHECICIVCKCOKCLCHLCMCMRCNCHNCOCOLCRCRICUCUBCVCPVCWCUWCXCXRCYCYPCZCZEDEDEUDJDJIDKDNKDMDMADODOMDZDZAECECUEEESTEGEGYEHESHERERIESESPETETHFIFINFJFJIFKFLKFMFSMFOFROFRFRAGAGABGBGBRGDGRDGEGEOGFGUFGGGGYGHGHAGIGIBGLGRLGMGMBGNGINGPGLPGQGNQGRGRCGSSGSGTGTMGUGUMGWGNBGYGUYHKHKGHMHMDHNHNDHRHRVHTHTIHUHUNIDIDNIEIRLILISRIMIMNININDIOIOTIQIRQIRIRNISISLITITAJEJEYJMJAMJOJORJPJPNKEKENKGKGZKHKHMKIKIRKMCOMKNKNAKPPRKKRKORKWKWTKYCYMKZKAZLALAOLBLBNLCLCALILIELKLKALRLBRLSLSOLTLTULULUXLVLVALYLBYMAMARMCMCOMDMDAMEMNEMFMAFMGMDGMHMHLMKMKDMLMLIMMMMRMNMNGMOMACMPMNPMQMTQMRMRTMSMSRMTMLTMUMUSMVMDVMWMWIMXMEXMYMYSMZMOZNANAMNCNCLNENERNFNFKNGNGANINICNLNLDNONORNPNPLNRNRUNUNIUNZNZLOMOMNPAPANPEPERPFPYFPGPNGPHPHLPKPAKPLPOLPMSPMPNPCNPRPRIPSPSEPTPRTPWPLWPYPRYQAQATREREUROROURSSRBRURUSRWRWASASAUSBSLBSCSYCSDSDNSESWESGSGPSHSHNSISVNSJSJMSKSVKSLSLESMSMRSNSENSOSOMSRSURSSSSDSTSTPSVSLVSXSXMSYSYRSZSWZTCTCATDTCDTFATFTGTGOTHTHATJTJKTKTKLTLTLSTMTKMTNTUNTOTONTRTURTTTTOTVTUVTWTWNTZTZAUAUKRUGUGAUMUMIUSUSAUYURYUZUZBVAVATVCVCTVEVENVGVGBVIVIRVNVNMVUVUTWFWLFWSWSMYEYEMYTMYTZAZAFZMZMBZWZWE");  String[] arrayOfString = new String[isoCountries.length]; System.arraycopy(isoCountries, 0, arrayOfString, 0, isoCountries.length); return arrayOfString; } public static String[] getISOLanguages() { if (isoLanguages == null) isoLanguages = getISO2Table("aaaarababkaeaveafafrakakaamamhanargararaasasmavavaayaymazazebabakbebelbgbulbhbihbibisbmbambnbenbobodbrbrebsboscacatcechechchacocoscrcrecscescuchucvchvcycymdadandedeudvdivdzdzoeeeweelellenengeoepoesspaetesteueusfafasfffulfifinfjfijfofaofrfrafyfrygaglegdglaglglggngrngugujgvglvhahauhehebhihinhohmohrhrvhthathuhunhyhyehzheriainaidindieileigiboiiiiiikipkinindioidoisislititaiuikuiwhebjajpnjiyidjvjavkakatkgkonkikikkjkuakkkazklkalkmkhmknkankokorkrkaukskaskukurkvkomkwcorkykirlalatlbltzlgluglilimlnlinlolaoltlitlulublvlavmgmlgmhmahmimrimkmkdmlmalmnmonmomolmrmarmsmsamtmltmymyananaunbnobndndenenepngndonlnldnnnnononornrnblnvnavnynyaocociojojiomormororiososspapanpipliplpolpspusptporququermrohrnrunroronrurusrwkinsasanscsrdsdsndsesmesgsagsisinskslkslslvsmsmosnsnasosomsqsqisrsrpsssswstsotsusunsvsweswswatatamteteltgtgkththatitirtktuktltgltntsntotontrturtstsotttattwtwitytahuguigukukrururduzuzbvevenvivievovolwawlnwowolxhxhoyiyidyoyorzazhazhzhozuzul");  String[] arrayOfString = new String[isoLanguages.length]; System.arraycopy(isoLanguages, 0, arrayOfString, 0, isoLanguages.length); return arrayOfString; } private static String[] getISO2Table(String paramString) { int i = paramString.length() / 5; String[] arrayOfString = new String[i]; for (byte b1 = 0, b2 = 0; b1 < i; b1++, b2 += 5) arrayOfString[b1] = paramString.substring(b2, b2 + 2);  return arrayOfString; } public String getLanguage() { return this.baseLocale.getLanguage(); } public String getScript() { return this.baseLocale.getScript(); } public String getCountry() { return this.baseLocale.getRegion(); } public String getVariant() { return this.baseLocale.getVariant(); } public boolean hasExtensions() { return (this.localeExtensions != null); } public Locale stripExtensions() { return hasExtensions() ? getInstance(this.baseLocale, null) : this; } public String getExtension(char paramChar) { if (!LocaleExtensions.isValidKey(paramChar)) throw new IllegalArgumentException("Ill-formed extension key: " + paramChar);  return hasExtensions() ? this.localeExtensions.getExtensionValue(Character.valueOf(paramChar)) : null; } private static volatile Locale defaultLocale = initDefault();
/* 2023 */   public Set<Character> getExtensionKeys() { if (!hasExtensions()) return Collections.emptySet();  return this.localeExtensions.getKeys(); } public Set<String> getUnicodeLocaleAttributes() { if (!hasExtensions()) return Collections.emptySet();  return this.localeExtensions.getUnicodeLocaleAttributes(); } public String getUnicodeLocaleType(String paramString) { if (!isUnicodeExtensionKey(paramString)) throw new IllegalArgumentException("Ill-formed Unicode locale key: " + paramString);  return hasExtensions() ? this.localeExtensions.getUnicodeLocaleType(paramString) : null; } public Set<String> getUnicodeLocaleKeys() { if (this.localeExtensions == null) return Collections.emptySet();  return this.localeExtensions.getUnicodeLocaleKeys(); } BaseLocale getBaseLocale() { return this.baseLocale; } LocaleExtensions getLocaleExtensions() { return this.localeExtensions; } public final String toString() { boolean bool1 = (this.baseLocale.getLanguage().length() != 0) ? true : false; boolean bool2 = (this.baseLocale.getScript().length() != 0) ? true : false; boolean bool3 = (this.baseLocale.getRegion().length() != 0) ? true : false; boolean bool4 = (this.baseLocale.getVariant().length() != 0) ? true : false; boolean bool5 = (this.localeExtensions != null && this.localeExtensions.getID().length() != 0) ? true : false; StringBuilder stringBuilder = new StringBuilder(this.baseLocale.getLanguage()); if (bool3 || (bool1 && (bool4 || bool2 || bool5))) stringBuilder.append('_').append(this.baseLocale.getRegion());  if (bool4 && (bool1 || bool3)) stringBuilder.append('_').append(this.baseLocale.getVariant());  if (bool2 && (bool1 || bool3)) stringBuilder.append("_#").append(this.baseLocale.getScript());  if (bool5 && (bool1 || bool3)) { stringBuilder.append('_'); if (!bool2) stringBuilder.append('#');  stringBuilder.append(this.localeExtensions.getID()); }  return stringBuilder.toString(); } public String toLanguageTag() { if (this.languageTag != null) return this.languageTag;  LanguageTag languageTag = LanguageTag.parseLocale(this.baseLocale, this.localeExtensions); StringBuilder stringBuilder = new StringBuilder(); String str1 = languageTag.getLanguage(); if (str1.length() > 0) stringBuilder.append(LanguageTag.canonicalizeLanguage(str1));  str1 = languageTag.getScript(); if (str1.length() > 0) { stringBuilder.append("-"); stringBuilder.append(LanguageTag.canonicalizeScript(str1)); }  str1 = languageTag.getRegion(); if (str1.length() > 0) { stringBuilder.append("-"); stringBuilder.append(LanguageTag.canonicalizeRegion(str1)); }  List<String> list = languageTag.getVariants(); for (String str : list) { stringBuilder.append("-"); stringBuilder.append(str); }  list = languageTag.getExtensions(); for (String str : list) { stringBuilder.append("-"); stringBuilder.append(LanguageTag.canonicalizeExtension(str)); }  str1 = languageTag.getPrivateuse(); if (str1.length() > 0) { if (stringBuilder.length() > 0) stringBuilder.append("-");  stringBuilder.append("x").append("-"); stringBuilder.append(str1); }  String str2 = stringBuilder.toString(); synchronized (this) { if (this.languageTag == null) this.languageTag = str2;  }  return this.languageTag; } public static Locale forLanguageTag(String paramString) { LanguageTag languageTag = LanguageTag.parse(paramString, null); InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder(); internalLocaleBuilder.setLanguageTag(languageTag); BaseLocale baseLocale = internalLocaleBuilder.getBaseLocale(); LocaleExtensions localeExtensions = internalLocaleBuilder.getLocaleExtensions(); if (localeExtensions == null && baseLocale.getVariant().length() > 0) localeExtensions = getCompatibilityExtensions(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), baseLocale.getVariant());  return getInstance(baseLocale, localeExtensions); } public String getISO3Language() throws MissingResourceException { String str1 = this.baseLocale.getLanguage(); if (str1.length() == 3) return str1;  String str2 = getISO3Code(str1, "aaaarababkaeaveafafrakakaamamhanargararaasasmavavaayaymazazebabakbebelbgbulbhbihbibisbmbambnbenbobodbrbrebsboscacatcechechchacocoscrcrecscescuchucvchvcycymdadandedeudvdivdzdzoeeeweelellenengeoepoesspaetesteueusfafasfffulfifinfjfijfofaofrfrafyfrygaglegdglaglglggngrngugujgvglvhahauhehebhihinhohmohrhrvhthathuhunhyhyehzheriainaidindieileigiboiiiiiikipkinindioidoisislititaiuikuiwhebjajpnjiyidjvjavkakatkgkonkikikkjkuakkkazklkalkmkhmknkankokorkrkaukskaskukurkvkomkwcorkykirlalatlbltzlgluglilimlnlinlolaoltlitlulublvlavmgmlgmhmahmimrimkmkdmlmalmnmonmomolmrmarmsmsamtmltmymyananaunbnobndndenenepngndonlnldnnnnononornrnblnvnavnynyaocociojojiomormororiososspapanpipliplpolpspusptporququermrohrnrunroronrurusrwkinsasanscsrdsdsndsesmesgsagsisinskslkslslvsmsmosnsnasosomsqsqisrsrpsssswstsotsusunsvsweswswatatamteteltgtgkththatitirtktuktltgltntsntotontrturtstsotttattwtwitytahuguigukukrururduzuzbvevenvivievovolwawlnwowolxhxhoyiyidyoyorzazhazhzhozuzul"); if (str2 == null) throw new MissingResourceException("Couldn't find 3-letter language code for " + str1, "FormatData_" + toString(), "ShortLanguage");  return str2; } public String getISO3Country() throws MissingResourceException { String str = getISO3Code(this.baseLocale.getRegion(), "ADANDAEAREAFAFGAGATGAIAIAALALBAMARMANANTAOAGOAQATAARARGASASMATAUTAUAUSAWABWAXALAAZAZEBABIHBBBRBBDBGDBEBELBFBFABGBGRBHBHRBIBDIBJBENBLBLMBMBMUBNBRNBOBOLBQBESBRBRABSBHSBTBTNBVBVTBWBWABYBLRBZBLZCACANCCCCKCDCODCFCAFCGCOGCHCHECICIVCKCOKCLCHLCMCMRCNCHNCOCOLCRCRICUCUBCVCPVCWCUWCXCXRCYCYPCZCZEDEDEUDJDJIDKDNKDMDMADODOMDZDZAECECUEEESTEGEGYEHESHERERIESESPETETHFIFINFJFJIFKFLKFMFSMFOFROFRFRAGAGABGBGBRGDGRDGEGEOGFGUFGGGGYGHGHAGIGIBGLGRLGMGMBGNGINGPGLPGQGNQGRGRCGSSGSGTGTMGUGUMGWGNBGYGUYHKHKGHMHMDHNHNDHRHRVHTHTIHUHUNIDIDNIEIRLILISRIMIMNININDIOIOTIQIRQIRIRNISISLITITAJEJEYJMJAMJOJORJPJPNKEKENKGKGZKHKHMKIKIRKMCOMKNKNAKPPRKKRKORKWKWTKYCYMKZKAZLALAOLBLBNLCLCALILIELKLKALRLBRLSLSOLTLTULULUXLVLVALYLBYMAMARMCMCOMDMDAMEMNEMFMAFMGMDGMHMHLMKMKDMLMLIMMMMRMNMNGMOMACMPMNPMQMTQMRMRTMSMSRMTMLTMUMUSMVMDVMWMWIMXMEXMYMYSMZMOZNANAMNCNCLNENERNFNFKNGNGANINICNLNLDNONORNPNPLNRNRUNUNIUNZNZLOMOMNPAPANPEPERPFPYFPGPNGPHPHLPKPAKPLPOLPMSPMPNPCNPRPRIPSPSEPTPRTPWPLWPYPRYQAQATREREUROROURSSRBRURUSRWRWASASAUSBSLBSCSYCSDSDNSESWESGSGPSHSHNSISVNSJSJMSKSVKSLSLESMSMRSNSENSOSOMSRSURSSSSDSTSTPSVSLVSXSXMSYSYRSZSWZTCTCATDTCDTFATFTGTGOTHTHATJTJKTKTKLTLTLSTMTKMTNTUNTOTONTRTURTTTTOTVTUVTWTWNTZTZAUAUKRUGUGAUMUMIUSUSAUYURYUZUZBVAVATVCVCTVEVENVGVGBVIVIRVNVNMVUVUTWFWLFWSWSMYEYEMYTMYTZAZAFZMZMBZWZWE"); if (str == null) throw new MissingResourceException("Couldn't find 3-letter country code for " + this.baseLocale.getRegion(), "FormatData_" + toString(), "ShortCountry");  return str; } private static String getISO3Code(String paramString1, String paramString2) { int i = paramString1.length(); if (i == 0) return "";  int j = paramString2.length(); int k = j; if (i == 2) { char c1 = paramString1.charAt(0); char c2 = paramString1.charAt(1); for (k = 0; k < j && (paramString2.charAt(k) != c1 || paramString2.charAt(k + 1) != c2); k += 5); }  return (k < j) ? paramString2.substring(k + 2, k + 5) : null; } public final String getDisplayLanguage() { return getDisplayLanguage(getDefault(Category.DISPLAY)); } public String getDisplayLanguage(Locale paramLocale) { return getDisplayString(this.baseLocale.getLanguage(), paramLocale, 0); } public String getDisplayScript() { return getDisplayScript(getDefault(Category.DISPLAY)); } public String getDisplayScript(Locale paramLocale) { return getDisplayString(this.baseLocale.getScript(), paramLocale, 3); } public final String getDisplayCountry() { return getDisplayCountry(getDefault(Category.DISPLAY)); } public String getDisplayCountry(Locale paramLocale) { return getDisplayString(this.baseLocale.getRegion(), paramLocale, 1); } private String getDisplayString(String paramString, Locale paramLocale, int paramInt) { if (paramString.length() == 0) return "";  if (paramLocale == null) throw new NullPointerException();  LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)LocaleNameProvider.class); String str1 = (paramInt == 2) ? ("%%" + paramString) : paramString; String str2 = localeServiceProviderPool.<LocaleServiceProvider, String>getLocalizedObject(LocaleNameGetter.INSTANCE, paramLocale, str1, new Object[] { Integer.valueOf(paramInt), paramString }); if (str2 != null) return str2;  return paramString; } public final String getDisplayVariant() { return getDisplayVariant(getDefault(Category.DISPLAY)); } public String getDisplayVariant(Locale paramLocale) { if (this.baseLocale.getVariant().length() == 0) return "";  LocaleResources localeResources = LocaleProviderAdapter.forJRE().getLocaleResources(paramLocale); String[] arrayOfString = getDisplayVariantArray(paramLocale); return formatList(arrayOfString, localeResources.getLocaleName("ListPattern"), localeResources.getLocaleName("ListCompositionPattern")); } public final String getDisplayName() { return getDisplayName(getDefault(Category.DISPLAY)); } public String getDisplayName(Locale paramLocale) { LocaleResources localeResources = LocaleProviderAdapter.forJRE().getLocaleResources(paramLocale); String str1 = getDisplayLanguage(paramLocale); String str2 = getDisplayScript(paramLocale); String str3 = getDisplayCountry(paramLocale); String[] arrayOfString1 = getDisplayVariantArray(paramLocale); String str4 = localeResources.getLocaleName("DisplayNamePattern"); String str5 = localeResources.getLocaleName("ListPattern"); String str6 = localeResources.getLocaleName("ListCompositionPattern"); String str7 = null; String[] arrayOfString2 = null; if (str1.length() == 0 && str2.length() == 0 && str3.length() == 0) { if (arrayOfString1.length == 0) return "";  return formatList(arrayOfString1, str5, str6); }  ArrayList<String> arrayList = new ArrayList(4); if (str1.length() != 0) arrayList.add(str1);  if (str2.length() != 0) arrayList.add(str2);  if (str3.length() != 0) arrayList.add(str3);  if (arrayOfString1.length != 0) arrayList.addAll(Arrays.asList(arrayOfString1));  str7 = arrayList.get(0); int i = arrayList.size(); arrayOfString2 = (i > 1) ? (String[])arrayList.subList(1, i).toArray((Object[])new String[i - 1]) : new String[0]; Object[] arrayOfObject = { new Integer((arrayOfString2.length != 0) ? 2 : 1), str7, (arrayOfString2.length != 0) ? formatList(arrayOfString2, str5, str6) : null }; if (str4 != null) return (new MessageFormat(str4)).format(arrayOfObject);  StringBuilder stringBuilder = new StringBuilder(); stringBuilder.append((String)arrayOfObject[1]); if (arrayOfObject.length > 2) { stringBuilder.append(" ("); stringBuilder.append((String)arrayOfObject[2]); stringBuilder.append(')'); }  return stringBuilder.toString(); } public Object clone() { try { return super.clone(); } catch (CloneNotSupportedException cloneNotSupportedException) { throw new InternalError(cloneNotSupportedException); }  } public int hashCode() { int i = this.hashCodeValue; if (i == 0) { i = this.baseLocale.hashCode(); if (this.localeExtensions != null) i ^= this.localeExtensions.hashCode();  this.hashCodeValue = i; }  return i; } public boolean equals(Object paramObject) { if (this == paramObject) return true;  if (!(paramObject instanceof Locale)) return false;  BaseLocale baseLocale = ((Locale)paramObject).baseLocale; if (!this.baseLocale.equals(baseLocale)) return false;  if (this.localeExtensions == null) return (((Locale)paramObject).localeExtensions == null);  return this.localeExtensions.equals(((Locale)paramObject).localeExtensions); } private static volatile Locale defaultDisplayLocale = null;
/* 2024 */   private static volatile Locale defaultFormatLocale = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile transient String languageTag;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] getDisplayVariantArray(Locale paramLocale) {
/* 2035 */     StringTokenizer stringTokenizer = new StringTokenizer(this.baseLocale.getVariant(), "_");
/* 2036 */     String[] arrayOfString = new String[stringTokenizer.countTokens()];
/*      */ 
/*      */ 
/*      */     
/* 2040 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 2041 */       arrayOfString[b] = getDisplayString(stringTokenizer.nextToken(), paramLocale, 2);
/*      */     }
/*      */ 
/*      */     
/* 2045 */     return arrayOfString;
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
/*      */   private static String formatList(String[] paramArrayOfString, String paramString1, String paramString2) {
/* 2062 */     if (paramString1 == null || paramString2 == null) {
/* 2063 */       StringBuilder stringBuilder = new StringBuilder();
/* 2064 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 2065 */         if (b > 0) {
/* 2066 */           stringBuilder.append(',');
/*      */         }
/* 2068 */         stringBuilder.append(paramArrayOfString[b]);
/*      */       } 
/* 2070 */       return stringBuilder.toString();
/*      */     } 
/*      */ 
/*      */     
/* 2074 */     if (paramArrayOfString.length > 3) {
/* 2075 */       MessageFormat messageFormat1 = new MessageFormat(paramString2);
/* 2076 */       paramArrayOfString = composeList(messageFormat1, paramArrayOfString);
/*      */     } 
/*      */ 
/*      */     
/* 2080 */     Object[] arrayOfObject = new Object[paramArrayOfString.length + 1];
/* 2081 */     System.arraycopy(paramArrayOfString, 0, arrayOfObject, 1, paramArrayOfString.length);
/* 2082 */     arrayOfObject[0] = new Integer(paramArrayOfString.length);
/*      */ 
/*      */     
/* 2085 */     MessageFormat messageFormat = new MessageFormat(paramString1);
/* 2086 */     return messageFormat.format(arrayOfObject);
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
/*      */   private static String[] composeList(MessageFormat paramMessageFormat, String[] paramArrayOfString) {
/* 2099 */     if (paramArrayOfString.length <= 3) return paramArrayOfString;
/*      */ 
/*      */     
/* 2102 */     String[] arrayOfString1 = { paramArrayOfString[0], paramArrayOfString[1] };
/* 2103 */     String str = paramMessageFormat.format(arrayOfString1);
/*      */ 
/*      */     
/* 2106 */     String[] arrayOfString2 = new String[paramArrayOfString.length - 1];
/* 2107 */     System.arraycopy(paramArrayOfString, 2, arrayOfString2, 1, arrayOfString2.length - 1);
/* 2108 */     arrayOfString2[0] = str;
/*      */ 
/*      */     
/* 2111 */     return composeList(paramMessageFormat, arrayOfString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isUnicodeExtensionKey(String paramString) {
/* 2118 */     return (paramString.length() == 2 && LocaleUtils.isAlphaNumericString(paramString));
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
/* 2140 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("language", String.class), new ObjectStreamField("country", String.class), new ObjectStreamField("variant", String.class), new ObjectStreamField("hashcode", int.class), new ObjectStreamField("script", String.class), new ObjectStreamField("extensions", String.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 2156 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 2157 */     putField.put("language", this.baseLocale.getLanguage());
/* 2158 */     putField.put("script", this.baseLocale.getScript());
/* 2159 */     putField.put("country", this.baseLocale.getRegion());
/* 2160 */     putField.put("variant", this.baseLocale.getVariant());
/* 2161 */     putField.put("extensions", (this.localeExtensions == null) ? "" : this.localeExtensions.getID());
/* 2162 */     putField.put("hashcode", -1);
/* 2163 */     paramObjectOutputStream.writeFields();
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 2175 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 2176 */     String str1 = (String)getField.get("language", "");
/* 2177 */     String str2 = (String)getField.get("script", "");
/* 2178 */     String str3 = (String)getField.get("country", "");
/* 2179 */     String str4 = (String)getField.get("variant", "");
/* 2180 */     String str5 = (String)getField.get("extensions", "");
/* 2181 */     this.baseLocale = BaseLocale.getInstance(convertOldISOCodes(str1), str2, str3, str4);
/* 2182 */     if (str5.length() > 0) {
/*      */       try {
/* 2184 */         InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
/* 2185 */         internalLocaleBuilder.setExtensions(str5);
/* 2186 */         this.localeExtensions = internalLocaleBuilder.getLocaleExtensions();
/* 2187 */       } catch (LocaleSyntaxException localeSyntaxException) {
/* 2188 */         throw new IllformedLocaleException(localeSyntaxException.getMessage());
/*      */       } 
/*      */     } else {
/* 2191 */       this.localeExtensions = null;
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
/*      */   private Object readResolve() throws ObjectStreamException {
/* 2210 */     return getInstance(this.baseLocale.getLanguage(), this.baseLocale.getScript(), this.baseLocale
/* 2211 */         .getRegion(), this.baseLocale.getVariant(), this.localeExtensions);
/*      */   }
/*      */   
/* 2214 */   private static volatile String[] isoLanguages = null;
/*      */   
/* 2216 */   private static volatile String[] isoCountries = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private static String convertOldISOCodes(String paramString) {
/* 2221 */     paramString = LocaleUtils.toLowerString(paramString).intern();
/* 2222 */     if (paramString == "he")
/* 2223 */       return "iw"; 
/* 2224 */     if (paramString == "yi")
/* 2225 */       return "ji"; 
/* 2226 */     if (paramString == "id") {
/* 2227 */       return "in";
/*      */     }
/* 2229 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static LocaleExtensions getCompatibilityExtensions(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 2237 */     LocaleExtensions localeExtensions = null;
/*      */     
/* 2239 */     if (LocaleUtils.caseIgnoreMatch(paramString1, "ja") && paramString2
/* 2240 */       .length() == 0 && 
/* 2241 */       LocaleUtils.caseIgnoreMatch(paramString3, "jp") && "JP"
/* 2242 */       .equals(paramString4)) {
/*      */       
/* 2244 */       localeExtensions = LocaleExtensions.CALENDAR_JAPANESE;
/* 2245 */     } else if (LocaleUtils.caseIgnoreMatch(paramString1, "th") && paramString2
/* 2246 */       .length() == 0 && 
/* 2247 */       LocaleUtils.caseIgnoreMatch(paramString3, "th") && "TH"
/* 2248 */       .equals(paramString4)) {
/*      */       
/* 2250 */       localeExtensions = LocaleExtensions.NUMBER_THAI;
/*      */     } 
/* 2252 */     return localeExtensions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class LocaleNameGetter
/*      */     implements LocaleServiceProviderPool.LocalizedObjectGetter<LocaleNameProvider, String>
/*      */   {
/* 2261 */     private static final LocaleNameGetter INSTANCE = new LocaleNameGetter();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getObject(LocaleNameProvider param1LocaleNameProvider, Locale param1Locale, String param1String, Object... param1VarArgs) {
/* 2268 */       assert param1VarArgs.length == 2;
/* 2269 */       int i = ((Integer)param1VarArgs[0]).intValue();
/* 2270 */       String str = (String)param1VarArgs[1];
/*      */       
/* 2272 */       switch (i) {
/*      */         case 0:
/* 2274 */           return param1LocaleNameProvider.getDisplayLanguage(str, param1Locale);
/*      */         case 1:
/* 2276 */           return param1LocaleNameProvider.getDisplayCountry(str, param1Locale);
/*      */         case 2:
/* 2278 */           return param1LocaleNameProvider.getDisplayVariant(str, param1Locale);
/*      */         case 3:
/* 2280 */           return param1LocaleNameProvider.getDisplayScript(str, param1Locale);
/*      */       } 
/*      */ 
/*      */       
/*      */       assert false;
/* 2285 */       return null;
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
/*      */   public enum Category
/*      */   {
/* 2304 */     DISPLAY("user.language.display", "user.script.display", "user.country.display", "user.variant.display"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2313 */     FORMAT("user.language.format", "user.script.format", "user.country.format", "user.variant.format"); final String languageKey;
/*      */     final String scriptKey;
/*      */     final String countryKey;
/*      */     final String variantKey;
/*      */     
/*      */     Category(String param1String1, String param1String2, String param1String3, String param1String4) {
/* 2319 */       this.languageKey = param1String1;
/* 2320 */       this.scriptKey = param1String2;
/* 2321 */       this.countryKey = param1String3;
/* 2322 */       this.variantKey = param1String4;
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
/*      */   public static final class Builder
/*      */   {
/* 2373 */     private final InternalLocaleBuilder localeBuilder = new InternalLocaleBuilder();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setLocale(Locale param1Locale) {
/*      */       try {
/* 2398 */         this.localeBuilder.setLocale(param1Locale.baseLocale, param1Locale.localeExtensions);
/* 2399 */       } catch (LocaleSyntaxException localeSyntaxException) {
/* 2400 */         throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
/*      */       } 
/* 2402 */       return this;
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
/*      */     public Builder setLanguageTag(String param1String) {
/* 2423 */       ParseStatus parseStatus = new ParseStatus();
/* 2424 */       LanguageTag languageTag = LanguageTag.parse(param1String, parseStatus);
/* 2425 */       if (parseStatus.isError()) {
/* 2426 */         throw new IllformedLocaleException(parseStatus.getErrorMessage(), parseStatus.getErrorIndex());
/*      */       }
/* 2428 */       this.localeBuilder.setLanguageTag(languageTag);
/* 2429 */       return this;
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
/*      */     public Builder setLanguage(String param1String) {
/*      */       try {
/* 2447 */         this.localeBuilder.setLanguage(param1String);
/* 2448 */       } catch (LocaleSyntaxException localeSyntaxException) {
/* 2449 */         throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
/*      */       } 
/* 2451 */       return this;
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
/*      */     public Builder setScript(String param1String) {
/*      */       try {
/* 2468 */         this.localeBuilder.setScript(param1String);
/* 2469 */       } catch (LocaleSyntaxException localeSyntaxException) {
/* 2470 */         throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
/*      */       } 
/* 2472 */       return this;
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
/*      */     public Builder setRegion(String param1String) {
/*      */       try {
/* 2493 */         this.localeBuilder.setRegion(param1String);
/* 2494 */       } catch (LocaleSyntaxException localeSyntaxException) {
/* 2495 */         throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
/*      */       } 
/* 2497 */       return this;
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
/*      */     public Builder setVariant(String param1String) {
/*      */       try {
/* 2520 */         this.localeBuilder.setVariant(param1String);
/* 2521 */       } catch (LocaleSyntaxException localeSyntaxException) {
/* 2522 */         throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
/*      */       } 
/* 2524 */       return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setExtension(char param1Char, String param1String) {
/*      */       try {
/* 2552 */         this.localeBuilder.setExtension(param1Char, param1String);
/* 2553 */       } catch (LocaleSyntaxException localeSyntaxException) {
/* 2554 */         throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
/*      */       } 
/* 2556 */       return this;
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
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setUnicodeLocaleKeyword(String param1String1, String param1String2) {
/*      */       try {
/* 2582 */         this.localeBuilder.setUnicodeLocaleKeyword(param1String1, param1String2);
/* 2583 */       } catch (LocaleSyntaxException localeSyntaxException) {
/* 2584 */         throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
/*      */       } 
/* 2586 */       return this;
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
/*      */     public Builder addUnicodeLocaleAttribute(String param1String) {
/*      */       try {
/* 2603 */         this.localeBuilder.addUnicodeLocaleAttribute(param1String);
/* 2604 */       } catch (LocaleSyntaxException localeSyntaxException) {
/* 2605 */         throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
/*      */       } 
/* 2607 */       return this;
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
/*      */     public Builder removeUnicodeLocaleAttribute(String param1String) {
/*      */       try {
/* 2626 */         this.localeBuilder.removeUnicodeLocaleAttribute(param1String);
/* 2627 */       } catch (LocaleSyntaxException localeSyntaxException) {
/* 2628 */         throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
/*      */       } 
/* 2630 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clear() {
/* 2639 */       this.localeBuilder.clear();
/* 2640 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder clearExtensions() {
/* 2651 */       this.localeBuilder.clearExtensions();
/* 2652 */       return this;
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
/*      */     public Locale build() {
/* 2666 */       BaseLocale baseLocale = this.localeBuilder.getBaseLocale();
/* 2667 */       LocaleExtensions localeExtensions = this.localeBuilder.getLocaleExtensions();
/* 2668 */       if (localeExtensions == null && baseLocale.getVariant().length() > 0) {
/* 2669 */         localeExtensions = Locale.getCompatibilityExtensions(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale
/* 2670 */             .getRegion(), baseLocale.getVariant());
/*      */       }
/* 2672 */       return Locale.getInstance(baseLocale, localeExtensions);
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
/*      */   public enum FilteringMode
/*      */   {
/* 2775 */     AUTOSELECT_FILTERING,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2780 */     EXTENDED_FILTERING,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2786 */     IGNORE_EXTENDED_RANGES,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2797 */     MAP_EXTENDED_RANGES,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2804 */     REJECT_EXTENDED_RANGES;
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
/*      */   public static final class LanguageRange
/*      */   {
/*      */     public static final double MAX_WEIGHT = 1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final double MIN_WEIGHT = 0.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final String range;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final double weight;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2858 */     private volatile int hash = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LanguageRange(String param1String) {
/* 2872 */       this(param1String, 1.0D);
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
/*      */     public LanguageRange(String param1String, double param1Double) {
/* 2889 */       if (param1String == null) {
/* 2890 */         throw new NullPointerException();
/*      */       }
/* 2892 */       if (param1Double < 0.0D || param1Double > 1.0D) {
/* 2893 */         throw new IllegalArgumentException("weight=" + param1Double);
/*      */       }
/*      */       
/* 2896 */       param1String = param1String.toLowerCase();
/*      */ 
/*      */       
/* 2899 */       boolean bool = false;
/* 2900 */       String[] arrayOfString = param1String.split("-");
/* 2901 */       if (isSubtagIllFormed(arrayOfString[0], true) || param1String
/* 2902 */         .endsWith("-")) {
/* 2903 */         bool = true;
/*      */       } else {
/* 2905 */         for (byte b = 1; b < arrayOfString.length; b++) {
/* 2906 */           if (isSubtagIllFormed(arrayOfString[b], false)) {
/* 2907 */             bool = true;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 2912 */       if (bool) {
/* 2913 */         throw new IllegalArgumentException("range=" + param1String);
/*      */       }
/*      */       
/* 2916 */       this.range = param1String;
/* 2917 */       this.weight = param1Double;
/*      */     }
/*      */ 
/*      */     
/*      */     private static boolean isSubtagIllFormed(String param1String, boolean param1Boolean) {
/* 2922 */       if (param1String.equals("") || param1String.length() > 8)
/* 2923 */         return true; 
/* 2924 */       if (param1String.equals("*")) {
/* 2925 */         return false;
/*      */       }
/* 2927 */       char[] arrayOfChar = param1String.toCharArray();
/* 2928 */       if (param1Boolean) {
/* 2929 */         for (char c : arrayOfChar) {
/* 2930 */           if (c < 'a' || c > 'z') {
/* 2931 */             return true;
/*      */           }
/*      */         } 
/*      */       } else {
/* 2935 */         for (char c : arrayOfChar) {
/* 2936 */           if (c < '0' || (c > '9' && c < 'a') || c > 'z') {
/* 2937 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/* 2941 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getRange() {
/* 2950 */       return this.range;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getWeight() {
/* 2959 */       return this.weight;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static List<LanguageRange> parse(String param1String) {
/* 3028 */       return LocaleMatcher.parse(param1String);
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
/*      */ 
/*      */     
/*      */     public static List<LanguageRange> parse(String param1String, Map<String, List<String>> param1Map) {
/* 3052 */       return mapEquivalents(parse(param1String), param1Map);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static List<LanguageRange> mapEquivalents(List<LanguageRange> param1List, Map<String, List<String>> param1Map) {
/* 3105 */       return LocaleMatcher.mapEquivalents(param1List, param1Map);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 3115 */       if (this.hash == 0) {
/* 3116 */         int i = 17;
/* 3117 */         i = 37 * i + this.range.hashCode();
/* 3118 */         long l = Double.doubleToLongBits(this.weight);
/* 3119 */         i = 37 * i + (int)(l ^ l >>> 32L);
/* 3120 */         this.hash = i;
/*      */       } 
/* 3122 */       return this.hash;
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
/*      */     public boolean equals(Object param1Object) {
/* 3138 */       if (this == param1Object) {
/* 3139 */         return true;
/*      */       }
/* 3141 */       if (!(param1Object instanceof LanguageRange)) {
/* 3142 */         return false;
/*      */       }
/* 3144 */       LanguageRange languageRange = (LanguageRange)param1Object;
/* 3145 */       return (this.hash == languageRange.hash && this.range
/* 3146 */         .equals(languageRange.range) && this.weight == languageRange.weight);
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
/*      */   public static List<Locale> filter(List<LanguageRange> paramList, Collection<Locale> paramCollection, FilteringMode paramFilteringMode) {
/* 3173 */     return LocaleMatcher.filter(paramList, paramCollection, paramFilteringMode);
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
/*      */   public static List<Locale> filter(List<LanguageRange> paramList, Collection<Locale> paramCollection) {
/* 3195 */     return filter(paramList, paramCollection, FilteringMode.AUTOSELECT_FILTERING);
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
/*      */   public static List<String> filterTags(List<LanguageRange> paramList, Collection<String> paramCollection, FilteringMode paramFilteringMode) {
/* 3220 */     return LocaleMatcher.filterTags(paramList, paramCollection, paramFilteringMode);
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
/*      */   public static List<String> filterTags(List<LanguageRange> paramList, Collection<String> paramCollection) {
/* 3242 */     return filterTags(paramList, paramCollection, FilteringMode.AUTOSELECT_FILTERING);
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
/*      */   public static Locale lookup(List<LanguageRange> paramList, Collection<Locale> paramCollection) {
/* 3261 */     return LocaleMatcher.lookup(paramList, paramCollection);
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
/*      */   public static String lookupTag(List<LanguageRange> paramList, Collection<String> paramCollection) {
/* 3280 */     return LocaleMatcher.lookupTag(paramList, paramCollection);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Locale.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */