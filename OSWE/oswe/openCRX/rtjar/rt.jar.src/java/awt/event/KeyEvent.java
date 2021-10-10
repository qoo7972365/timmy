/*      */ package java.awt.event;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Toolkit;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.ExtendedKeyCodes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class KeyEvent
/*      */   extends InputEvent
/*      */ {
/*      */   private boolean isProxyActive = false;
/*      */   public static final int KEY_FIRST = 400;
/*      */   public static final int KEY_LAST = 402;
/*      */   public static final int KEY_TYPED = 400;
/*      */   public static final int KEY_PRESSED = 401;
/*      */   public static final int KEY_RELEASED = 402;
/*      */   public static final int VK_ENTER = 10;
/*      */   public static final int VK_BACK_SPACE = 8;
/*      */   public static final int VK_TAB = 9;
/*      */   public static final int VK_CANCEL = 3;
/*      */   public static final int VK_CLEAR = 12;
/*      */   public static final int VK_SHIFT = 16;
/*      */   public static final int VK_CONTROL = 17;
/*      */   public static final int VK_ALT = 18;
/*      */   public static final int VK_PAUSE = 19;
/*      */   public static final int VK_CAPS_LOCK = 20;
/*      */   public static final int VK_ESCAPE = 27;
/*      */   public static final int VK_SPACE = 32;
/*      */   public static final int VK_PAGE_UP = 33;
/*      */   public static final int VK_PAGE_DOWN = 34;
/*      */   public static final int VK_END = 35;
/*      */   public static final int VK_HOME = 36;
/*      */   public static final int VK_LEFT = 37;
/*      */   public static final int VK_UP = 38;
/*      */   public static final int VK_RIGHT = 39;
/*      */   public static final int VK_DOWN = 40;
/*      */   public static final int VK_COMMA = 44;
/*      */   public static final int VK_MINUS = 45;
/*      */   public static final int VK_PERIOD = 46;
/*      */   public static final int VK_SLASH = 47;
/*      */   public static final int VK_0 = 48;
/*      */   public static final int VK_1 = 49;
/*      */   public static final int VK_2 = 50;
/*      */   public static final int VK_3 = 51;
/*      */   public static final int VK_4 = 52;
/*      */   public static final int VK_5 = 53;
/*      */   public static final int VK_6 = 54;
/*      */   public static final int VK_7 = 55;
/*      */   public static final int VK_8 = 56;
/*      */   public static final int VK_9 = 57;
/*      */   public static final int VK_SEMICOLON = 59;
/*      */   public static final int VK_EQUALS = 61;
/*      */   public static final int VK_A = 65;
/*      */   public static final int VK_B = 66;
/*      */   public static final int VK_C = 67;
/*      */   public static final int VK_D = 68;
/*      */   public static final int VK_E = 69;
/*      */   public static final int VK_F = 70;
/*      */   public static final int VK_G = 71;
/*      */   public static final int VK_H = 72;
/*      */   public static final int VK_I = 73;
/*      */   public static final int VK_J = 74;
/*      */   public static final int VK_K = 75;
/*      */   public static final int VK_L = 76;
/*      */   public static final int VK_M = 77;
/*      */   public static final int VK_N = 78;
/*      */   public static final int VK_O = 79;
/*      */   public static final int VK_P = 80;
/*      */   public static final int VK_Q = 81;
/*      */   public static final int VK_R = 82;
/*      */   public static final int VK_S = 83;
/*      */   public static final int VK_T = 84;
/*      */   public static final int VK_U = 85;
/*      */   public static final int VK_V = 86;
/*      */   public static final int VK_W = 87;
/*      */   public static final int VK_X = 88;
/*      */   public static final int VK_Y = 89;
/*      */   public static final int VK_Z = 90;
/*      */   public static final int VK_OPEN_BRACKET = 91;
/*      */   public static final int VK_BACK_SLASH = 92;
/*      */   public static final int VK_CLOSE_BRACKET = 93;
/*      */   public static final int VK_NUMPAD0 = 96;
/*      */   public static final int VK_NUMPAD1 = 97;
/*      */   public static final int VK_NUMPAD2 = 98;
/*      */   public static final int VK_NUMPAD3 = 99;
/*      */   public static final int VK_NUMPAD4 = 100;
/*      */   public static final int VK_NUMPAD5 = 101;
/*      */   public static final int VK_NUMPAD6 = 102;
/*      */   public static final int VK_NUMPAD7 = 103;
/*      */   public static final int VK_NUMPAD8 = 104;
/*      */   public static final int VK_NUMPAD9 = 105;
/*      */   public static final int VK_MULTIPLY = 106;
/*      */   public static final int VK_ADD = 107;
/*      */   public static final int VK_SEPARATER = 108;
/*      */   public static final int VK_SEPARATOR = 108;
/*      */   public static final int VK_SUBTRACT = 109;
/*      */   public static final int VK_DECIMAL = 110;
/*      */   public static final int VK_DIVIDE = 111;
/*      */   public static final int VK_DELETE = 127;
/*      */   public static final int VK_NUM_LOCK = 144;
/*      */   public static final int VK_SCROLL_LOCK = 145;
/*      */   public static final int VK_F1 = 112;
/*      */   public static final int VK_F2 = 113;
/*      */   public static final int VK_F3 = 114;
/*      */   public static final int VK_F4 = 115;
/*      */   public static final int VK_F5 = 116;
/*      */   public static final int VK_F6 = 117;
/*      */   public static final int VK_F7 = 118;
/*      */   public static final int VK_F8 = 119;
/*      */   public static final int VK_F9 = 120;
/*      */   public static final int VK_F10 = 121;
/*      */   public static final int VK_F11 = 122;
/*      */   public static final int VK_F12 = 123;
/*      */   public static final int VK_F13 = 61440;
/*      */   public static final int VK_F14 = 61441;
/*      */   public static final int VK_F15 = 61442;
/*      */   public static final int VK_F16 = 61443;
/*      */   public static final int VK_F17 = 61444;
/*      */   public static final int VK_F18 = 61445;
/*      */   public static final int VK_F19 = 61446;
/*      */   public static final int VK_F20 = 61447;
/*      */   public static final int VK_F21 = 61448;
/*      */   public static final int VK_F22 = 61449;
/*      */   public static final int VK_F23 = 61450;
/*      */   public static final int VK_F24 = 61451;
/*      */   public static final int VK_PRINTSCREEN = 154;
/*      */   public static final int VK_INSERT = 155;
/*      */   public static final int VK_HELP = 156;
/*      */   public static final int VK_META = 157;
/*      */   public static final int VK_BACK_QUOTE = 192;
/*      */   public static final int VK_QUOTE = 222;
/*      */   public static final int VK_KP_UP = 224;
/*      */   public static final int VK_KP_DOWN = 225;
/*      */   public static final int VK_KP_LEFT = 226;
/*      */   public static final int VK_KP_RIGHT = 227;
/*      */   public static final int VK_DEAD_GRAVE = 128;
/*      */   public static final int VK_DEAD_ACUTE = 129;
/*      */   public static final int VK_DEAD_CIRCUMFLEX = 130;
/*      */   public static final int VK_DEAD_TILDE = 131;
/*      */   public static final int VK_DEAD_MACRON = 132;
/*      */   public static final int VK_DEAD_BREVE = 133;
/*      */   public static final int VK_DEAD_ABOVEDOT = 134;
/*      */   public static final int VK_DEAD_DIAERESIS = 135;
/*      */   public static final int VK_DEAD_ABOVERING = 136;
/*      */   public static final int VK_DEAD_DOUBLEACUTE = 137;
/*      */   public static final int VK_DEAD_CARON = 138;
/*      */   public static final int VK_DEAD_CEDILLA = 139;
/*      */   public static final int VK_DEAD_OGONEK = 140;
/*      */   public static final int VK_DEAD_IOTA = 141;
/*      */   public static final int VK_DEAD_VOICED_SOUND = 142;
/*      */   public static final int VK_DEAD_SEMIVOICED_SOUND = 143;
/*      */   public static final int VK_AMPERSAND = 150;
/*      */   public static final int VK_ASTERISK = 151;
/*      */   public static final int VK_QUOTEDBL = 152;
/*      */   public static final int VK_LESS = 153;
/*      */   public static final int VK_GREATER = 160;
/*      */   public static final int VK_BRACELEFT = 161;
/*      */   public static final int VK_BRACERIGHT = 162;
/*      */   public static final int VK_AT = 512;
/*      */   public static final int VK_COLON = 513;
/*      */   public static final int VK_CIRCUMFLEX = 514;
/*      */   public static final int VK_DOLLAR = 515;
/*      */   public static final int VK_EURO_SIGN = 516;
/*      */   public static final int VK_EXCLAMATION_MARK = 517;
/*      */   public static final int VK_INVERTED_EXCLAMATION_MARK = 518;
/*      */   public static final int VK_LEFT_PARENTHESIS = 519;
/*      */   public static final int VK_NUMBER_SIGN = 520;
/*      */   public static final int VK_PLUS = 521;
/*      */   public static final int VK_RIGHT_PARENTHESIS = 522;
/*      */   public static final int VK_UNDERSCORE = 523;
/*      */   public static final int VK_WINDOWS = 524;
/*      */   public static final int VK_CONTEXT_MENU = 525;
/*      */   public static final int VK_FINAL = 24;
/*      */   public static final int VK_CONVERT = 28;
/*      */   public static final int VK_NONCONVERT = 29;
/*      */   public static final int VK_ACCEPT = 30;
/*      */   public static final int VK_MODECHANGE = 31;
/*      */   public static final int VK_KANA = 21;
/*      */   public static final int VK_KANJI = 25;
/*      */   public static final int VK_ALPHANUMERIC = 240;
/*      */   public static final int VK_KATAKANA = 241;
/*      */   public static final int VK_HIRAGANA = 242;
/*      */   public static final int VK_FULL_WIDTH = 243;
/*      */   public static final int VK_HALF_WIDTH = 244;
/*      */   public static final int VK_ROMAN_CHARACTERS = 245;
/*      */   public static final int VK_ALL_CANDIDATES = 256;
/*      */   public static final int VK_PREVIOUS_CANDIDATE = 257;
/*      */   public static final int VK_CODE_INPUT = 258;
/*      */   public static final int VK_JAPANESE_KATAKANA = 259;
/*      */   public static final int VK_JAPANESE_HIRAGANA = 260;
/*      */   public static final int VK_JAPANESE_ROMAN = 261;
/*      */   public static final int VK_KANA_LOCK = 262;
/*      */   public static final int VK_INPUT_METHOD_ON_OFF = 263;
/*      */   public static final int VK_CUT = 65489;
/*      */   public static final int VK_COPY = 65485;
/*      */   public static final int VK_PASTE = 65487;
/*      */   public static final int VK_UNDO = 65483;
/*      */   public static final int VK_AGAIN = 65481;
/*      */   public static final int VK_FIND = 65488;
/*      */   public static final int VK_PROPS = 65482;
/*      */   public static final int VK_STOP = 65480;
/*      */   public static final int VK_COMPOSE = 65312;
/*      */   public static final int VK_ALT_GRAPH = 65406;
/*      */   public static final int VK_BEGIN = 65368;
/*      */   public static final int VK_UNDEFINED = 0;
/*      */   public static final char CHAR_UNDEFINED = '￿';
/*      */   public static final int KEY_LOCATION_UNKNOWN = 0;
/*      */   public static final int KEY_LOCATION_STANDARD = 1;
/*      */   public static final int KEY_LOCATION_LEFT = 2;
/*      */   public static final int KEY_LOCATION_RIGHT = 3;
/*      */   public static final int KEY_LOCATION_NUMPAD = 4;
/*      */   int keyCode;
/*      */   char keyChar;
/*      */   int keyLocation;
/*  901 */   private transient long rawCode = 0L;
/*  902 */   private transient long primaryLevelUnicode = 0L;
/*  903 */   private transient long scancode = 0L;
/*  904 */   private transient long extendedKeyCode = 0L;
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -2352130953028126954L;
/*      */   
/*      */   private Component originalSource;
/*      */ 
/*      */   
/*      */   static {
/*  913 */     NativeLibLoader.loadLibraries();
/*  914 */     if (!GraphicsEnvironment.isHeadless()) {
/*  915 */       initIDs();
/*      */     }
/*      */     
/*  918 */     AWTAccessor.setKeyEventAccessor(new AWTAccessor.KeyEventAccessor()
/*      */         {
/*      */           public void setRawCode(KeyEvent param1KeyEvent, long param1Long) {
/*  921 */             param1KeyEvent.rawCode = param1Long;
/*      */           }
/*      */ 
/*      */           
/*      */           public void setPrimaryLevelUnicode(KeyEvent param1KeyEvent, long param1Long) {
/*  926 */             param1KeyEvent.primaryLevelUnicode = param1Long;
/*      */           }
/*      */ 
/*      */           
/*      */           public void setExtendedKeyCode(KeyEvent param1KeyEvent, long param1Long) {
/*  931 */             param1KeyEvent.extendedKeyCode = param1Long;
/*      */           }
/*      */           
/*      */           public Component getOriginalSource(KeyEvent param1KeyEvent) {
/*  935 */             return param1KeyEvent.originalSource;
/*      */           }
/*      */         });
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
/*      */   private KeyEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2, int paramInt3, char paramChar, int paramInt4, boolean paramBoolean) {
/*  956 */     this(paramComponent, paramInt1, paramLong, paramInt2, paramInt3, paramChar, paramInt4);
/*  957 */     this.isProxyActive = paramBoolean;
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
/*      */   public KeyEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2, int paramInt3, char paramChar, int paramInt4) {
/* 1012 */     super(paramComponent, paramInt1, paramLong, paramInt2);
/* 1013 */     if (paramInt1 == 400) {
/* 1014 */       if (paramChar == Character.MAX_VALUE) {
/* 1015 */         throw new IllegalArgumentException("invalid keyChar");
/*      */       }
/* 1017 */       if (paramInt3 != 0) {
/* 1018 */         throw new IllegalArgumentException("invalid keyCode");
/*      */       }
/* 1020 */       if (paramInt4 != 0) {
/* 1021 */         throw new IllegalArgumentException("invalid keyLocation");
/*      */       }
/*      */     } 
/*      */     
/* 1025 */     this.keyCode = paramInt3;
/* 1026 */     this.keyChar = paramChar;
/*      */     
/* 1028 */     if (paramInt4 < 0 || paramInt4 > 4)
/*      */     {
/* 1030 */       throw new IllegalArgumentException("invalid keyLocation");
/*      */     }
/* 1032 */     this.keyLocation = paramInt4;
/* 1033 */     if (getModifiers() != 0 && getModifiersEx() == 0) {
/* 1034 */       setNewModifiers();
/* 1035 */     } else if (getModifiers() == 0 && getModifiersEx() != 0) {
/* 1036 */       setOldModifiers();
/*      */     } 
/* 1038 */     this.originalSource = paramComponent;
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
/*      */   public KeyEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2, int paramInt3, char paramChar) {
/* 1083 */     this(paramComponent, paramInt1, paramLong, paramInt2, paramInt3, paramChar, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public KeyEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2, int paramInt3) {
/* 1093 */     this(paramComponent, paramInt1, paramLong, paramInt2, paramInt3, (char)paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getKeyCode() {
/* 1104 */     return this.keyCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeyCode(int paramInt) {
/* 1113 */     this.keyCode = paramInt;
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
/*      */   public char getKeyChar() {
/* 1131 */     return this.keyChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeyChar(char paramChar) {
/* 1141 */     this.keyChar = paramChar;
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
/*      */   @Deprecated
/*      */   public void setModifiers(int paramInt) {
/* 1159 */     this.modifiers = paramInt;
/* 1160 */     if (getModifiers() != 0 && getModifiersEx() == 0) {
/* 1161 */       setNewModifiers();
/* 1162 */     } else if (getModifiers() == 0 && getModifiersEx() != 0) {
/* 1163 */       setOldModifiers();
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
/*      */   public int getKeyLocation() {
/* 1180 */     return this.keyLocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getKeyText(int paramInt) {
/* 1191 */     if ((paramInt >= 48 && paramInt <= 57) || (paramInt >= 65 && paramInt <= 90))
/*      */     {
/* 1193 */       return String.valueOf((char)paramInt);
/*      */     }
/*      */     
/* 1196 */     switch (paramInt) { case 10:
/* 1197 */         return Toolkit.getProperty("AWT.enter", "Enter");
/* 1198 */       case 8: return Toolkit.getProperty("AWT.backSpace", "Backspace");
/* 1199 */       case 9: return Toolkit.getProperty("AWT.tab", "Tab");
/* 1200 */       case 3: return Toolkit.getProperty("AWT.cancel", "Cancel");
/* 1201 */       case 12: return Toolkit.getProperty("AWT.clear", "Clear");
/* 1202 */       case 65312: return Toolkit.getProperty("AWT.compose", "Compose");
/* 1203 */       case 19: return Toolkit.getProperty("AWT.pause", "Pause");
/* 1204 */       case 20: return Toolkit.getProperty("AWT.capsLock", "Caps Lock");
/* 1205 */       case 27: return Toolkit.getProperty("AWT.escape", "Escape");
/* 1206 */       case 32: return Toolkit.getProperty("AWT.space", "Space");
/* 1207 */       case 33: return Toolkit.getProperty("AWT.pgup", "Page Up");
/* 1208 */       case 34: return Toolkit.getProperty("AWT.pgdn", "Page Down");
/* 1209 */       case 35: return Toolkit.getProperty("AWT.end", "End");
/* 1210 */       case 36: return Toolkit.getProperty("AWT.home", "Home");
/* 1211 */       case 37: return Toolkit.getProperty("AWT.left", "Left");
/* 1212 */       case 38: return Toolkit.getProperty("AWT.up", "Up");
/* 1213 */       case 39: return Toolkit.getProperty("AWT.right", "Right");
/* 1214 */       case 40: return Toolkit.getProperty("AWT.down", "Down");
/* 1215 */       case 65368: return Toolkit.getProperty("AWT.begin", "Begin");
/*      */       
/*      */       case 16:
/* 1218 */         return Toolkit.getProperty("AWT.shift", "Shift");
/* 1219 */       case 17: return Toolkit.getProperty("AWT.control", "Control");
/* 1220 */       case 18: return Toolkit.getProperty("AWT.alt", "Alt");
/* 1221 */       case 157: return Toolkit.getProperty("AWT.meta", "Meta");
/* 1222 */       case 65406: return Toolkit.getProperty("AWT.altGraph", "Alt Graph");
/*      */       
/*      */       case 44:
/* 1225 */         return Toolkit.getProperty("AWT.comma", "Comma");
/* 1226 */       case 46: return Toolkit.getProperty("AWT.period", "Period");
/* 1227 */       case 47: return Toolkit.getProperty("AWT.slash", "Slash");
/* 1228 */       case 59: return Toolkit.getProperty("AWT.semicolon", "Semicolon");
/* 1229 */       case 61: return Toolkit.getProperty("AWT.equals", "Equals");
/* 1230 */       case 91: return Toolkit.getProperty("AWT.openBracket", "Open Bracket");
/* 1231 */       case 92: return Toolkit.getProperty("AWT.backSlash", "Back Slash");
/* 1232 */       case 93: return Toolkit.getProperty("AWT.closeBracket", "Close Bracket");
/*      */       
/*      */       case 106:
/* 1235 */         return Toolkit.getProperty("AWT.multiply", "NumPad *");
/* 1236 */       case 107: return Toolkit.getProperty("AWT.add", "NumPad +");
/* 1237 */       case 108: return Toolkit.getProperty("AWT.separator", "NumPad ,");
/* 1238 */       case 109: return Toolkit.getProperty("AWT.subtract", "NumPad -");
/* 1239 */       case 110: return Toolkit.getProperty("AWT.decimal", "NumPad .");
/* 1240 */       case 111: return Toolkit.getProperty("AWT.divide", "NumPad /");
/* 1241 */       case 127: return Toolkit.getProperty("AWT.delete", "Delete");
/* 1242 */       case 144: return Toolkit.getProperty("AWT.numLock", "Num Lock");
/* 1243 */       case 145: return Toolkit.getProperty("AWT.scrollLock", "Scroll Lock");
/*      */       case 524:
/* 1245 */         return Toolkit.getProperty("AWT.windows", "Windows");
/* 1246 */       case 525: return Toolkit.getProperty("AWT.context", "Context Menu");
/*      */       case 112:
/* 1248 */         return Toolkit.getProperty("AWT.f1", "F1");
/* 1249 */       case 113: return Toolkit.getProperty("AWT.f2", "F2");
/* 1250 */       case 114: return Toolkit.getProperty("AWT.f3", "F3");
/* 1251 */       case 115: return Toolkit.getProperty("AWT.f4", "F4");
/* 1252 */       case 116: return Toolkit.getProperty("AWT.f5", "F5");
/* 1253 */       case 117: return Toolkit.getProperty("AWT.f6", "F6");
/* 1254 */       case 118: return Toolkit.getProperty("AWT.f7", "F7");
/* 1255 */       case 119: return Toolkit.getProperty("AWT.f8", "F8");
/* 1256 */       case 120: return Toolkit.getProperty("AWT.f9", "F9");
/* 1257 */       case 121: return Toolkit.getProperty("AWT.f10", "F10");
/* 1258 */       case 122: return Toolkit.getProperty("AWT.f11", "F11");
/* 1259 */       case 123: return Toolkit.getProperty("AWT.f12", "F12");
/* 1260 */       case 61440: return Toolkit.getProperty("AWT.f13", "F13");
/* 1261 */       case 61441: return Toolkit.getProperty("AWT.f14", "F14");
/* 1262 */       case 61442: return Toolkit.getProperty("AWT.f15", "F15");
/* 1263 */       case 61443: return Toolkit.getProperty("AWT.f16", "F16");
/* 1264 */       case 61444: return Toolkit.getProperty("AWT.f17", "F17");
/* 1265 */       case 61445: return Toolkit.getProperty("AWT.f18", "F18");
/* 1266 */       case 61446: return Toolkit.getProperty("AWT.f19", "F19");
/* 1267 */       case 61447: return Toolkit.getProperty("AWT.f20", "F20");
/* 1268 */       case 61448: return Toolkit.getProperty("AWT.f21", "F21");
/* 1269 */       case 61449: return Toolkit.getProperty("AWT.f22", "F22");
/* 1270 */       case 61450: return Toolkit.getProperty("AWT.f23", "F23");
/* 1271 */       case 61451: return Toolkit.getProperty("AWT.f24", "F24");
/*      */       case 154:
/* 1273 */         return Toolkit.getProperty("AWT.printScreen", "Print Screen");
/* 1274 */       case 155: return Toolkit.getProperty("AWT.insert", "Insert");
/* 1275 */       case 156: return Toolkit.getProperty("AWT.help", "Help");
/* 1276 */       case 192: return Toolkit.getProperty("AWT.backQuote", "Back Quote");
/* 1277 */       case 222: return Toolkit.getProperty("AWT.quote", "Quote");
/*      */       case 224:
/* 1279 */         return Toolkit.getProperty("AWT.up", "Up");
/* 1280 */       case 225: return Toolkit.getProperty("AWT.down", "Down");
/* 1281 */       case 226: return Toolkit.getProperty("AWT.left", "Left");
/* 1282 */       case 227: return Toolkit.getProperty("AWT.right", "Right");
/*      */       case 128:
/* 1284 */         return Toolkit.getProperty("AWT.deadGrave", "Dead Grave");
/* 1285 */       case 129: return Toolkit.getProperty("AWT.deadAcute", "Dead Acute");
/* 1286 */       case 130: return Toolkit.getProperty("AWT.deadCircumflex", "Dead Circumflex");
/* 1287 */       case 131: return Toolkit.getProperty("AWT.deadTilde", "Dead Tilde");
/* 1288 */       case 132: return Toolkit.getProperty("AWT.deadMacron", "Dead Macron");
/* 1289 */       case 133: return Toolkit.getProperty("AWT.deadBreve", "Dead Breve");
/* 1290 */       case 134: return Toolkit.getProperty("AWT.deadAboveDot", "Dead Above Dot");
/* 1291 */       case 135: return Toolkit.getProperty("AWT.deadDiaeresis", "Dead Diaeresis");
/* 1292 */       case 136: return Toolkit.getProperty("AWT.deadAboveRing", "Dead Above Ring");
/* 1293 */       case 137: return Toolkit.getProperty("AWT.deadDoubleAcute", "Dead Double Acute");
/* 1294 */       case 138: return Toolkit.getProperty("AWT.deadCaron", "Dead Caron");
/* 1295 */       case 139: return Toolkit.getProperty("AWT.deadCedilla", "Dead Cedilla");
/* 1296 */       case 140: return Toolkit.getProperty("AWT.deadOgonek", "Dead Ogonek");
/* 1297 */       case 141: return Toolkit.getProperty("AWT.deadIota", "Dead Iota");
/* 1298 */       case 142: return Toolkit.getProperty("AWT.deadVoicedSound", "Dead Voiced Sound");
/* 1299 */       case 143: return Toolkit.getProperty("AWT.deadSemivoicedSound", "Dead Semivoiced Sound");
/*      */       case 150:
/* 1301 */         return Toolkit.getProperty("AWT.ampersand", "Ampersand");
/* 1302 */       case 151: return Toolkit.getProperty("AWT.asterisk", "Asterisk");
/* 1303 */       case 152: return Toolkit.getProperty("AWT.quoteDbl", "Double Quote");
/* 1304 */       case 153: return Toolkit.getProperty("AWT.Less", "Less");
/* 1305 */       case 160: return Toolkit.getProperty("AWT.greater", "Greater");
/* 1306 */       case 161: return Toolkit.getProperty("AWT.braceLeft", "Left Brace");
/* 1307 */       case 162: return Toolkit.getProperty("AWT.braceRight", "Right Brace");
/* 1308 */       case 512: return Toolkit.getProperty("AWT.at", "At");
/* 1309 */       case 513: return Toolkit.getProperty("AWT.colon", "Colon");
/* 1310 */       case 514: return Toolkit.getProperty("AWT.circumflex", "Circumflex");
/* 1311 */       case 515: return Toolkit.getProperty("AWT.dollar", "Dollar");
/* 1312 */       case 516: return Toolkit.getProperty("AWT.euro", "Euro");
/* 1313 */       case 517: return Toolkit.getProperty("AWT.exclamationMark", "Exclamation Mark");
/* 1314 */       case 518: return Toolkit.getProperty("AWT.invertedExclamationMark", "Inverted Exclamation Mark");
/* 1315 */       case 519: return Toolkit.getProperty("AWT.leftParenthesis", "Left Parenthesis");
/* 1316 */       case 520: return Toolkit.getProperty("AWT.numberSign", "Number Sign");
/* 1317 */       case 45: return Toolkit.getProperty("AWT.minus", "Minus");
/* 1318 */       case 521: return Toolkit.getProperty("AWT.plus", "Plus");
/* 1319 */       case 522: return Toolkit.getProperty("AWT.rightParenthesis", "Right Parenthesis");
/* 1320 */       case 523: return Toolkit.getProperty("AWT.underscore", "Underscore");
/*      */       case 24:
/* 1322 */         return Toolkit.getProperty("AWT.final", "Final");
/* 1323 */       case 28: return Toolkit.getProperty("AWT.convert", "Convert");
/* 1324 */       case 29: return Toolkit.getProperty("AWT.noconvert", "No Convert");
/* 1325 */       case 30: return Toolkit.getProperty("AWT.accept", "Accept");
/* 1326 */       case 31: return Toolkit.getProperty("AWT.modechange", "Mode Change");
/* 1327 */       case 21: return Toolkit.getProperty("AWT.kana", "Kana");
/* 1328 */       case 25: return Toolkit.getProperty("AWT.kanji", "Kanji");
/* 1329 */       case 240: return Toolkit.getProperty("AWT.alphanumeric", "Alphanumeric");
/* 1330 */       case 241: return Toolkit.getProperty("AWT.katakana", "Katakana");
/* 1331 */       case 242: return Toolkit.getProperty("AWT.hiragana", "Hiragana");
/* 1332 */       case 243: return Toolkit.getProperty("AWT.fullWidth", "Full-Width");
/* 1333 */       case 244: return Toolkit.getProperty("AWT.halfWidth", "Half-Width");
/* 1334 */       case 245: return Toolkit.getProperty("AWT.romanCharacters", "Roman Characters");
/* 1335 */       case 256: return Toolkit.getProperty("AWT.allCandidates", "All Candidates");
/* 1336 */       case 257: return Toolkit.getProperty("AWT.previousCandidate", "Previous Candidate");
/* 1337 */       case 258: return Toolkit.getProperty("AWT.codeInput", "Code Input");
/* 1338 */       case 259: return Toolkit.getProperty("AWT.japaneseKatakana", "Japanese Katakana");
/* 1339 */       case 260: return Toolkit.getProperty("AWT.japaneseHiragana", "Japanese Hiragana");
/* 1340 */       case 261: return Toolkit.getProperty("AWT.japaneseRoman", "Japanese Roman");
/* 1341 */       case 262: return Toolkit.getProperty("AWT.kanaLock", "Kana Lock");
/* 1342 */       case 263: return Toolkit.getProperty("AWT.inputMethodOnOff", "Input Method On/Off");
/*      */       case 65481:
/* 1344 */         return Toolkit.getProperty("AWT.again", "Again");
/* 1345 */       case 65483: return Toolkit.getProperty("AWT.undo", "Undo");
/* 1346 */       case 65485: return Toolkit.getProperty("AWT.copy", "Copy");
/* 1347 */       case 65487: return Toolkit.getProperty("AWT.paste", "Paste");
/* 1348 */       case 65489: return Toolkit.getProperty("AWT.cut", "Cut");
/* 1349 */       case 65488: return Toolkit.getProperty("AWT.find", "Find");
/* 1350 */       case 65482: return Toolkit.getProperty("AWT.props", "Props");
/* 1351 */       case 65480: return Toolkit.getProperty("AWT.stop", "Stop"); }
/*      */ 
/*      */     
/* 1354 */     if (paramInt >= 96 && paramInt <= 105) {
/* 1355 */       String str1 = Toolkit.getProperty("AWT.numpad", "NumPad");
/* 1356 */       char c = (char)(paramInt - 96 + 48);
/* 1357 */       return str1 + "-" + c;
/*      */     } 
/*      */     
/* 1360 */     if ((paramInt & 0x1000000) != 0) {
/* 1361 */       return String.valueOf((char)(paramInt ^ 0x1000000));
/*      */     }
/* 1363 */     String str = Toolkit.getProperty("AWT.unknown", "Unknown");
/* 1364 */     return str + " keyCode: 0x" + Integer.toString(paramInt, 16);
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
/*      */   public static String getKeyModifiersText(int paramInt) {
/* 1384 */     StringBuilder stringBuilder = new StringBuilder();
/* 1385 */     if ((paramInt & 0x4) != 0) {
/* 1386 */       stringBuilder.append(Toolkit.getProperty("AWT.meta", "Meta"));
/* 1387 */       stringBuilder.append("+");
/*      */     } 
/* 1389 */     if ((paramInt & 0x2) != 0) {
/* 1390 */       stringBuilder.append(Toolkit.getProperty("AWT.control", "Ctrl"));
/* 1391 */       stringBuilder.append("+");
/*      */     } 
/* 1393 */     if ((paramInt & 0x8) != 0) {
/* 1394 */       stringBuilder.append(Toolkit.getProperty("AWT.alt", "Alt"));
/* 1395 */       stringBuilder.append("+");
/*      */     } 
/* 1397 */     if ((paramInt & 0x1) != 0) {
/* 1398 */       stringBuilder.append(Toolkit.getProperty("AWT.shift", "Shift"));
/* 1399 */       stringBuilder.append("+");
/*      */     } 
/* 1401 */     if ((paramInt & 0x20) != 0) {
/* 1402 */       stringBuilder.append(Toolkit.getProperty("AWT.altGraph", "Alt Graph"));
/* 1403 */       stringBuilder.append("+");
/*      */     } 
/* 1405 */     if ((paramInt & 0x10) != 0) {
/* 1406 */       stringBuilder.append(Toolkit.getProperty("AWT.button1", "Button1"));
/* 1407 */       stringBuilder.append("+");
/*      */     } 
/* 1409 */     if (stringBuilder.length() > 0) {
/* 1410 */       stringBuilder.setLength(stringBuilder.length() - 1);
/*      */     }
/* 1412 */     return stringBuilder.toString();
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
/*      */   public boolean isActionKey() {
/* 1425 */     switch (this.keyCode) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 24:
/*      */       case 25:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 40:
/*      */       case 112:
/*      */       case 113:
/*      */       case 114:
/*      */       case 115:
/*      */       case 116:
/*      */       case 117:
/*      */       case 118:
/*      */       case 119:
/*      */       case 120:
/*      */       case 121:
/*      */       case 122:
/*      */       case 123:
/*      */       case 144:
/*      */       case 145:
/*      */       case 154:
/*      */       case 155:
/*      */       case 156:
/*      */       case 224:
/*      */       case 225:
/*      */       case 226:
/*      */       case 227:
/*      */       case 240:
/*      */       case 241:
/*      */       case 242:
/*      */       case 243:
/*      */       case 244:
/*      */       case 245:
/*      */       case 256:
/*      */       case 257:
/*      */       case 258:
/*      */       case 259:
/*      */       case 260:
/*      */       case 261:
/*      */       case 262:
/*      */       case 263:
/*      */       case 524:
/*      */       case 525:
/*      */       case 61440:
/*      */       case 61441:
/*      */       case 61442:
/*      */       case 61443:
/*      */       case 61444:
/*      */       case 61445:
/*      */       case 61446:
/*      */       case 61447:
/*      */       case 61448:
/*      */       case 61449:
/*      */       case 61450:
/*      */       case 61451:
/*      */       case 65368:
/*      */       case 65480:
/*      */       case 65481:
/*      */       case 65482:
/*      */       case 65483:
/*      */       case 65485:
/*      */       case 65487:
/*      */       case 65488:
/*      */       case 65489:
/* 1506 */         return true;
/*      */     } 
/* 1508 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String paramString() {
/* 1518 */     StringBuilder stringBuilder = new StringBuilder(100);
/*      */     
/* 1520 */     switch (this.id) {
/*      */       case 401:
/* 1522 */         stringBuilder.append("KEY_PRESSED");
/*      */         break;
/*      */       case 402:
/* 1525 */         stringBuilder.append("KEY_RELEASED");
/*      */         break;
/*      */       case 400:
/* 1528 */         stringBuilder.append("KEY_TYPED");
/*      */         break;
/*      */       default:
/* 1531 */         stringBuilder.append("unknown type");
/*      */         break;
/*      */     } 
/*      */     
/* 1535 */     stringBuilder.append(",keyCode=").append(this.keyCode);
/* 1536 */     stringBuilder.append(",keyText=").append(getKeyText(this.keyCode));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1542 */     stringBuilder.append(",keyChar=");
/* 1543 */     switch (this.keyChar) {
/*      */       case '\b':
/* 1545 */         stringBuilder.append(getKeyText(8));
/*      */         break;
/*      */       case '\t':
/* 1548 */         stringBuilder.append(getKeyText(9));
/*      */         break;
/*      */       case '\n':
/* 1551 */         stringBuilder.append(getKeyText(10));
/*      */         break;
/*      */       case '\030':
/* 1554 */         stringBuilder.append(getKeyText(3));
/*      */         break;
/*      */       case '\033':
/* 1557 */         stringBuilder.append(getKeyText(27));
/*      */         break;
/*      */       case '':
/* 1560 */         stringBuilder.append(getKeyText(127));
/*      */         break;
/*      */       case '￿':
/* 1563 */         stringBuilder.append(Toolkit.getProperty("AWT.undefined", "Undefined"));
/* 1564 */         stringBuilder.append(" keyChar");
/*      */         break;
/*      */       default:
/* 1567 */         stringBuilder.append("'").append(this.keyChar).append("'");
/*      */         break;
/*      */     } 
/*      */     
/* 1571 */     if (getModifiers() != 0) {
/* 1572 */       stringBuilder.append(",modifiers=").append(getKeyModifiersText(this.modifiers));
/*      */     }
/* 1574 */     if (getModifiersEx() != 0) {
/* 1575 */       stringBuilder.append(",extModifiers=").append(getModifiersExText(this.modifiers));
/*      */     }
/*      */     
/* 1578 */     stringBuilder.append(",keyLocation=");
/* 1579 */     switch (this.keyLocation)
/*      */     { case 0:
/* 1581 */         stringBuilder.append("KEY_LOCATION_UNKNOWN");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1599 */         stringBuilder.append(",rawCode=").append(this.rawCode);
/* 1600 */         stringBuilder.append(",primaryLevelUnicode=").append(this.primaryLevelUnicode);
/* 1601 */         stringBuilder.append(",scancode=").append(this.scancode);
/* 1602 */         stringBuilder.append(",extendedKeyCode=0x").append(Long.toHexString(this.extendedKeyCode));
/*      */         
/* 1604 */         return stringBuilder.toString();case 1: stringBuilder.append("KEY_LOCATION_STANDARD"); stringBuilder.append(",rawCode=").append(this.rawCode); stringBuilder.append(",primaryLevelUnicode=").append(this.primaryLevelUnicode); stringBuilder.append(",scancode=").append(this.scancode); stringBuilder.append(",extendedKeyCode=0x").append(Long.toHexString(this.extendedKeyCode)); return stringBuilder.toString();case 2: stringBuilder.append("KEY_LOCATION_LEFT"); stringBuilder.append(",rawCode=").append(this.rawCode); stringBuilder.append(",primaryLevelUnicode=").append(this.primaryLevelUnicode); stringBuilder.append(",scancode=").append(this.scancode); stringBuilder.append(",extendedKeyCode=0x").append(Long.toHexString(this.extendedKeyCode)); return stringBuilder.toString();case 3: stringBuilder.append("KEY_LOCATION_RIGHT"); stringBuilder.append(",rawCode=").append(this.rawCode); stringBuilder.append(",primaryLevelUnicode=").append(this.primaryLevelUnicode); stringBuilder.append(",scancode=").append(this.scancode); stringBuilder.append(",extendedKeyCode=0x").append(Long.toHexString(this.extendedKeyCode)); return stringBuilder.toString();case 4: stringBuilder.append("KEY_LOCATION_NUMPAD"); stringBuilder.append(",rawCode=").append(this.rawCode); stringBuilder.append(",primaryLevelUnicode=").append(this.primaryLevelUnicode); stringBuilder.append(",scancode=").append(this.scancode); stringBuilder.append(",extendedKeyCode=0x").append(Long.toHexString(this.extendedKeyCode)); return stringBuilder.toString(); }  stringBuilder.append("KEY_LOCATION_UNKNOWN"); stringBuilder.append(",rawCode=").append(this.rawCode); stringBuilder.append(",primaryLevelUnicode=").append(this.primaryLevelUnicode); stringBuilder.append(",scancode=").append(this.scancode); stringBuilder.append(",extendedKeyCode=0x").append(Long.toHexString(this.extendedKeyCode)); return stringBuilder.toString();
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
/*      */   public int getExtendedKeyCode() {
/* 1619 */     return (int)this.extendedKeyCode;
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
/*      */   public static int getExtendedKeyCodeForChar(int paramInt) {
/* 1635 */     return ExtendedKeyCodes.getExtendedKeyCodeForChar(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setNewModifiers() {
/* 1643 */     if ((this.modifiers & 0x1) != 0) {
/* 1644 */       this.modifiers |= 0x40;
/*      */     }
/* 1646 */     if ((this.modifiers & 0x8) != 0) {
/* 1647 */       this.modifiers |= 0x200;
/*      */     }
/* 1649 */     if ((this.modifiers & 0x2) != 0) {
/* 1650 */       this.modifiers |= 0x80;
/*      */     }
/* 1652 */     if ((this.modifiers & 0x4) != 0) {
/* 1653 */       this.modifiers |= 0x100;
/*      */     }
/* 1655 */     if ((this.modifiers & 0x20) != 0) {
/* 1656 */       this.modifiers |= 0x2000;
/*      */     }
/* 1658 */     if ((this.modifiers & 0x10) != 0) {
/* 1659 */       this.modifiers |= 0x400;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setOldModifiers() {
/* 1667 */     if ((this.modifiers & 0x40) != 0) {
/* 1668 */       this.modifiers |= 0x1;
/*      */     }
/* 1670 */     if ((this.modifiers & 0x200) != 0) {
/* 1671 */       this.modifiers |= 0x8;
/*      */     }
/* 1673 */     if ((this.modifiers & 0x80) != 0) {
/* 1674 */       this.modifiers |= 0x2;
/*      */     }
/* 1676 */     if ((this.modifiers & 0x100) != 0) {
/* 1677 */       this.modifiers |= 0x4;
/*      */     }
/* 1679 */     if ((this.modifiers & 0x2000) != 0) {
/* 1680 */       this.modifiers |= 0x20;
/*      */     }
/* 1682 */     if ((this.modifiers & 0x400) != 0) {
/* 1683 */       this.modifiers |= 0x10;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1694 */     paramObjectInputStream.defaultReadObject();
/* 1695 */     if (getModifiers() != 0 && getModifiersEx() == 0)
/* 1696 */       setNewModifiers(); 
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/KeyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */