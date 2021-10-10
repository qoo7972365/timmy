/*      */ package javax.xml.bind;
/*      */ 
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.TimeZone;
/*      */ import javax.xml.datatype.DatatypeConfigurationException;
/*      */ import javax.xml.datatype.DatatypeFactory;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class DatatypeConverterImpl
/*      */   implements DatatypeConverterInterface
/*      */ {
/*   59 */   public static final DatatypeConverterInterface theInstance = new DatatypeConverterImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String parseString(String lexicalXSDString) {
/*   65 */     return lexicalXSDString;
/*      */   }
/*      */   
/*      */   public BigInteger parseInteger(String lexicalXSDInteger) {
/*   69 */     return _parseInteger(lexicalXSDInteger);
/*      */   }
/*      */   
/*      */   public static BigInteger _parseInteger(CharSequence s) {
/*   73 */     return new BigInteger(removeOptionalPlus(WhiteSpaceProcessor.trim(s)).toString());
/*      */   }
/*      */   
/*      */   public String printInteger(BigInteger val) {
/*   77 */     return _printInteger(val);
/*      */   }
/*      */   
/*      */   public static String _printInteger(BigInteger val) {
/*   81 */     return val.toString();
/*      */   }
/*      */   
/*      */   public int parseInt(String s) {
/*   85 */     return _parseInt(s);
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
/*      */   public static int _parseInt(CharSequence s) {
/*   99 */     int len = s.length();
/*  100 */     int sign = 1;
/*      */     
/*  102 */     int r = 0;
/*      */     
/*  104 */     for (int i = 0; i < len; i++) {
/*  105 */       char ch = s.charAt(i);
/*  106 */       if (!WhiteSpaceProcessor.isWhiteSpace(ch))
/*      */       {
/*  108 */         if ('0' <= ch && ch <= '9') {
/*  109 */           r = r * 10 + ch - 48;
/*  110 */         } else if (ch == '-') {
/*  111 */           sign = -1;
/*  112 */         } else if (ch != '+') {
/*      */ 
/*      */           
/*  115 */           throw new NumberFormatException("Not a number: " + s);
/*      */         } 
/*      */       }
/*      */     } 
/*  119 */     return r * sign;
/*      */   }
/*      */   
/*      */   public long parseLong(String lexicalXSLong) {
/*  123 */     return _parseLong(lexicalXSLong);
/*      */   }
/*      */   
/*      */   public static long _parseLong(CharSequence s) {
/*  127 */     return Long.valueOf(removeOptionalPlus(WhiteSpaceProcessor.trim(s)).toString()).longValue();
/*      */   }
/*      */   
/*      */   public short parseShort(String lexicalXSDShort) {
/*  131 */     return _parseShort(lexicalXSDShort);
/*      */   }
/*      */   
/*      */   public static short _parseShort(CharSequence s) {
/*  135 */     return (short)_parseInt(s);
/*      */   }
/*      */   
/*      */   public String printShort(short val) {
/*  139 */     return _printShort(val);
/*      */   }
/*      */   
/*      */   public static String _printShort(short val) {
/*  143 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public BigDecimal parseDecimal(String content) {
/*  147 */     return _parseDecimal(content);
/*      */   }
/*      */   
/*      */   public static BigDecimal _parseDecimal(CharSequence content) {
/*  151 */     content = WhiteSpaceProcessor.trim(content);
/*      */     
/*  153 */     if (content.length() <= 0) {
/*  154 */       return null;
/*      */     }
/*      */     
/*  157 */     return new BigDecimal(content.toString());
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
/*      */   public float parseFloat(String lexicalXSDFloat) {
/*  173 */     return _parseFloat(lexicalXSDFloat);
/*      */   }
/*      */   
/*      */   public static float _parseFloat(CharSequence _val) {
/*  177 */     String s = WhiteSpaceProcessor.trim(_val).toString();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  193 */     if (s.equals("NaN")) {
/*  194 */       return Float.NaN;
/*      */     }
/*  196 */     if (s.equals("INF")) {
/*  197 */       return Float.POSITIVE_INFINITY;
/*      */     }
/*  199 */     if (s.equals("-INF")) {
/*  200 */       return Float.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/*  203 */     if (s.length() == 0 || 
/*  204 */       !isDigitOrPeriodOrSign(s.charAt(0)) || 
/*  205 */       !isDigitOrPeriodOrSign(s.charAt(s.length() - 1))) {
/*  206 */       throw new NumberFormatException();
/*      */     }
/*      */ 
/*      */     
/*  210 */     return Float.parseFloat(s);
/*      */   }
/*      */   
/*      */   public String printFloat(float v) {
/*  214 */     return _printFloat(v);
/*      */   }
/*      */   
/*      */   public static String _printFloat(float v) {
/*  218 */     if (Float.isNaN(v)) {
/*  219 */       return "NaN";
/*      */     }
/*  221 */     if (v == Float.POSITIVE_INFINITY) {
/*  222 */       return "INF";
/*      */     }
/*  224 */     if (v == Float.NEGATIVE_INFINITY) {
/*  225 */       return "-INF";
/*      */     }
/*  227 */     return String.valueOf(v);
/*      */   }
/*      */   
/*      */   public double parseDouble(String lexicalXSDDouble) {
/*  231 */     return _parseDouble(lexicalXSDDouble);
/*      */   }
/*      */   
/*      */   public static double _parseDouble(CharSequence _val) {
/*  235 */     String val = WhiteSpaceProcessor.trim(_val).toString();
/*      */     
/*  237 */     if (val.equals("NaN")) {
/*  238 */       return Double.NaN;
/*      */     }
/*  240 */     if (val.equals("INF")) {
/*  241 */       return Double.POSITIVE_INFINITY;
/*      */     }
/*  243 */     if (val.equals("-INF")) {
/*  244 */       return Double.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/*  247 */     if (val.length() == 0 || 
/*  248 */       !isDigitOrPeriodOrSign(val.charAt(0)) || 
/*  249 */       !isDigitOrPeriodOrSign(val.charAt(val.length() - 1))) {
/*  250 */       throw new NumberFormatException(val);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  255 */     return Double.parseDouble(val);
/*      */   }
/*      */   
/*      */   public boolean parseBoolean(String lexicalXSDBoolean) {
/*  259 */     Boolean b = _parseBoolean(lexicalXSDBoolean);
/*  260 */     return (b == null) ? false : b.booleanValue();
/*      */   } public static Boolean _parseBoolean(CharSequence literal) {
/*      */     char ch;
/*      */     String strTrue, strFalse;
/*  264 */     if (literal == null) {
/*  265 */       return null;
/*      */     }
/*      */     
/*  268 */     int i = 0;
/*  269 */     int len = literal.length();
/*      */     
/*  271 */     boolean value = false;
/*      */     
/*  273 */     if (literal.length() <= 0) {
/*  274 */       return null;
/*      */     }
/*      */     
/*      */     do {
/*  278 */       ch = literal.charAt(i++);
/*  279 */     } while (WhiteSpaceProcessor.isWhiteSpace(ch) && i < len);
/*      */     
/*  281 */     int strIndex = 0;
/*      */     
/*  283 */     switch (ch) {
/*      */       case '1':
/*  285 */         value = true;
/*      */         break;
/*      */       case '0':
/*  288 */         value = false;
/*      */         break;
/*      */       case 't':
/*  291 */         strTrue = "rue";
/*      */         do {
/*  293 */           ch = literal.charAt(i++);
/*  294 */         } while (strTrue.charAt(strIndex++) == ch && i < len && strIndex < 3);
/*      */         
/*  296 */         if (strIndex == 3) {
/*  297 */           value = true; break;
/*      */         } 
/*  299 */         return Boolean.valueOf(false);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 'f':
/*  305 */         strFalse = "alse";
/*      */         do {
/*  307 */           ch = literal.charAt(i++);
/*  308 */         } while (strFalse.charAt(strIndex++) == ch && i < len && strIndex < 4);
/*      */ 
/*      */         
/*  311 */         if (strIndex == 4) {
/*  312 */           value = false; break;
/*      */         } 
/*  314 */         return Boolean.valueOf(false);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  321 */     if (i < len) {
/*      */       do {
/*  323 */         ch = literal.charAt(i++);
/*  324 */       } while (WhiteSpaceProcessor.isWhiteSpace(ch) && i < len);
/*      */     }
/*      */     
/*  327 */     if (i == len) {
/*  328 */       return Boolean.valueOf(value);
/*      */     }
/*  330 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String printBoolean(boolean val) {
/*  336 */     return val ? "true" : "false";
/*      */   }
/*      */   
/*      */   public static String _printBoolean(boolean val) {
/*  340 */     return val ? "true" : "false";
/*      */   }
/*      */   
/*      */   public byte parseByte(String lexicalXSDByte) {
/*  344 */     return _parseByte(lexicalXSDByte);
/*      */   }
/*      */   
/*      */   public static byte _parseByte(CharSequence literal) {
/*  348 */     return (byte)_parseInt(literal);
/*      */   }
/*      */   
/*      */   public String printByte(byte val) {
/*  352 */     return _printByte(val);
/*      */   }
/*      */   
/*      */   public static String _printByte(byte val) {
/*  356 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public QName parseQName(String lexicalXSDQName, NamespaceContext nsc) {
/*  360 */     return _parseQName(lexicalXSDQName, nsc);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static QName _parseQName(CharSequence text, NamespaceContext nsc) {
/*      */     String uri, localPart, prefix;
/*  367 */     int length = text.length();
/*      */ 
/*      */     
/*  370 */     int start = 0;
/*  371 */     while (start < length && WhiteSpaceProcessor.isWhiteSpace(text.charAt(start))) {
/*  372 */       start++;
/*      */     }
/*      */     
/*  375 */     int end = length;
/*  376 */     while (end > start && WhiteSpaceProcessor.isWhiteSpace(text.charAt(end - 1))) {
/*  377 */       end--;
/*      */     }
/*      */     
/*  380 */     if (end == start) {
/*  381 */       throw new IllegalArgumentException("input is empty");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  390 */     int idx = start + 1;
/*  391 */     while (idx < end && text.charAt(idx) != ':') {
/*  392 */       idx++;
/*      */     }
/*      */     
/*  395 */     if (idx == end) {
/*  396 */       uri = nsc.getNamespaceURI("");
/*  397 */       localPart = text.subSequence(start, end).toString();
/*  398 */       prefix = "";
/*      */     } else {
/*      */       
/*  401 */       prefix = text.subSequence(start, idx).toString();
/*  402 */       localPart = text.subSequence(idx + 1, end).toString();
/*  403 */       uri = nsc.getNamespaceURI(prefix);
/*      */ 
/*      */       
/*  406 */       if (uri == null || uri.length() == 0)
/*      */       {
/*      */         
/*  409 */         throw new IllegalArgumentException("prefix " + prefix + " is not bound to a namespace");
/*      */       }
/*      */     } 
/*      */     
/*  413 */     return new QName(uri, localPart, prefix);
/*      */   }
/*      */   
/*      */   public Calendar parseDateTime(String lexicalXSDDateTime) {
/*  417 */     return _parseDateTime(lexicalXSDDateTime);
/*      */   }
/*      */   
/*      */   public static GregorianCalendar _parseDateTime(CharSequence s) {
/*  421 */     String val = WhiteSpaceProcessor.trim(s).toString();
/*  422 */     return datatypeFactory.newXMLGregorianCalendar(val).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   public String printDateTime(Calendar val) {
/*  426 */     return _printDateTime(val);
/*      */   }
/*      */   
/*      */   public static String _printDateTime(Calendar val) {
/*  430 */     return CalendarFormatter.doFormat("%Y-%M-%DT%h:%m:%s%z", val);
/*      */   }
/*      */   
/*      */   public byte[] parseBase64Binary(String lexicalXSDBase64Binary) {
/*  434 */     return _parseBase64Binary(lexicalXSDBase64Binary);
/*      */   }
/*      */   
/*      */   public byte[] parseHexBinary(String s) {
/*  438 */     int len = s.length();
/*      */ 
/*      */     
/*  441 */     if (len % 2 != 0) {
/*  442 */       throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
/*      */     }
/*      */     
/*  445 */     byte[] out = new byte[len / 2];
/*      */     
/*  447 */     for (int i = 0; i < len; i += 2) {
/*  448 */       int h = hexToBin(s.charAt(i));
/*  449 */       int l = hexToBin(s.charAt(i + 1));
/*  450 */       if (h == -1 || l == -1) {
/*  451 */         throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
/*      */       }
/*      */       
/*  454 */       out[i / 2] = (byte)(h * 16 + l);
/*      */     } 
/*      */     
/*  457 */     return out;
/*      */   }
/*      */   
/*      */   private static int hexToBin(char ch) {
/*  461 */     if ('0' <= ch && ch <= '9') {
/*  462 */       return ch - 48;
/*      */     }
/*  464 */     if ('A' <= ch && ch <= 'F') {
/*  465 */       return ch - 65 + 10;
/*      */     }
/*  467 */     if ('a' <= ch && ch <= 'f') {
/*  468 */       return ch - 97 + 10;
/*      */     }
/*  470 */     return -1;
/*      */   }
/*  472 */   private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
/*      */   
/*      */   public String printHexBinary(byte[] data) {
/*  475 */     StringBuilder r = new StringBuilder(data.length * 2);
/*  476 */     for (byte b : data) {
/*  477 */       r.append(hexCode[b >> 4 & 0xF]);
/*  478 */       r.append(hexCode[b & 0xF]);
/*      */     } 
/*  480 */     return r.toString();
/*      */   }
/*      */   
/*      */   public long parseUnsignedInt(String lexicalXSDUnsignedInt) {
/*  484 */     return _parseLong(lexicalXSDUnsignedInt);
/*      */   }
/*      */   
/*      */   public String printUnsignedInt(long val) {
/*  488 */     return _printLong(val);
/*      */   }
/*      */   
/*      */   public int parseUnsignedShort(String lexicalXSDUnsignedShort) {
/*  492 */     return _parseInt(lexicalXSDUnsignedShort);
/*      */   }
/*      */   
/*      */   public Calendar parseTime(String lexicalXSDTime) {
/*  496 */     return datatypeFactory.newXMLGregorianCalendar(lexicalXSDTime).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   public String printTime(Calendar val) {
/*  500 */     return CalendarFormatter.doFormat("%h:%m:%s%z", val);
/*      */   }
/*      */   
/*      */   public Calendar parseDate(String lexicalXSDDate) {
/*  504 */     return datatypeFactory.newXMLGregorianCalendar(lexicalXSDDate).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   public String printDate(Calendar val) {
/*  508 */     return _printDate(val);
/*      */   }
/*      */   
/*      */   public static String _printDate(Calendar val) {
/*  512 */     return CalendarFormatter.doFormat("%Y-%M-%D" + "%z", val);
/*      */   }
/*      */   
/*      */   public String parseAnySimpleType(String lexicalXSDAnySimpleType) {
/*  516 */     return lexicalXSDAnySimpleType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String printString(String val) {
/*  522 */     return val;
/*      */   }
/*      */   
/*      */   public String printInt(int val) {
/*  526 */     return _printInt(val);
/*      */   }
/*      */   
/*      */   public static String _printInt(int val) {
/*  530 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public String printLong(long val) {
/*  534 */     return _printLong(val);
/*      */   }
/*      */   
/*      */   public static String _printLong(long val) {
/*  538 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public String printDecimal(BigDecimal val) {
/*  542 */     return _printDecimal(val);
/*      */   }
/*      */   
/*      */   public static String _printDecimal(BigDecimal val) {
/*  546 */     return val.toPlainString();
/*      */   }
/*      */   
/*      */   public String printDouble(double v) {
/*  550 */     return _printDouble(v);
/*      */   }
/*      */   
/*      */   public static String _printDouble(double v) {
/*  554 */     if (Double.isNaN(v)) {
/*  555 */       return "NaN";
/*      */     }
/*  557 */     if (v == Double.POSITIVE_INFINITY) {
/*  558 */       return "INF";
/*      */     }
/*  560 */     if (v == Double.NEGATIVE_INFINITY) {
/*  561 */       return "-INF";
/*      */     }
/*  563 */     return String.valueOf(v);
/*      */   }
/*      */   
/*      */   public String printQName(QName val, NamespaceContext nsc) {
/*  567 */     return _printQName(val, nsc);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String _printQName(QName val, NamespaceContext nsc) {
/*  573 */     String qname, prefix = nsc.getPrefix(val.getNamespaceURI());
/*  574 */     String localPart = val.getLocalPart();
/*      */     
/*  576 */     if (prefix == null || prefix.length() == 0) {
/*  577 */       qname = localPart;
/*      */     } else {
/*  579 */       qname = prefix + ':' + localPart;
/*      */     } 
/*      */     
/*  582 */     return qname;
/*      */   }
/*      */   
/*      */   public String printBase64Binary(byte[] val) {
/*  586 */     return _printBase64Binary(val);
/*      */   }
/*      */   
/*      */   public String printUnsignedShort(int val) {
/*  590 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public String printAnySimpleType(String val) {
/*  594 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String installHook(String s) {
/*  603 */     DatatypeConverter.setDatatypeConverter(theInstance);
/*  604 */     return s;
/*      */   }
/*      */   
/*  607 */   private static final byte[] decodeMap = initDecodeMap();
/*      */   private static final byte PADDING = 127;
/*      */   
/*      */   private static byte[] initDecodeMap() {
/*  611 */     byte[] map = new byte[128];
/*      */     int i;
/*  613 */     for (i = 0; i < 128; i++) {
/*  614 */       map[i] = -1;
/*      */     }
/*      */     
/*  617 */     for (i = 65; i <= 90; i++) {
/*  618 */       map[i] = (byte)(i - 65);
/*      */     }
/*  620 */     for (i = 97; i <= 122; i++) {
/*  621 */       map[i] = (byte)(i - 97 + 26);
/*      */     }
/*  623 */     for (i = 48; i <= 57; i++) {
/*  624 */       map[i] = (byte)(i - 48 + 52);
/*      */     }
/*  626 */     map[43] = 62;
/*  627 */     map[47] = 63;
/*  628 */     map[61] = Byte.MAX_VALUE;
/*      */     
/*  630 */     return map;
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
/*      */   private static int guessLength(String text) {
/*  654 */     int len = text.length();
/*      */ 
/*      */     
/*  657 */     int j = len - 1;
/*  658 */     while (j >= 0) {
/*  659 */       byte code = decodeMap[text.charAt(j)];
/*  660 */       if (code == Byte.MAX_VALUE) {
/*      */         j--; continue;
/*      */       } 
/*  663 */       if (code == -1)
/*      */       {
/*  665 */         return text.length() / 4 * 3;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  670 */     j++;
/*  671 */     int padSize = len - j;
/*  672 */     if (padSize > 2)
/*      */     {
/*  674 */       return text.length() / 4 * 3;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  679 */     return text.length() / 4 * 3 - padSize;
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
/*      */   public static byte[] _parseBase64Binary(String text) {
/*  692 */     int buflen = guessLength(text);
/*  693 */     byte[] out = new byte[buflen];
/*  694 */     int o = 0;
/*      */     
/*  696 */     int len = text.length();
/*      */ 
/*      */     
/*  699 */     byte[] quadruplet = new byte[4];
/*  700 */     int q = 0;
/*      */ 
/*      */     
/*  703 */     for (int i = 0; i < len; i++) {
/*  704 */       char ch = text.charAt(i);
/*  705 */       byte v = decodeMap[ch];
/*      */       
/*  707 */       if (v != -1) {
/*  708 */         quadruplet[q++] = v;
/*      */       }
/*      */       
/*  711 */       if (q == 4) {
/*      */         
/*  713 */         out[o++] = (byte)(quadruplet[0] << 2 | quadruplet[1] >> 4);
/*  714 */         if (quadruplet[2] != Byte.MAX_VALUE) {
/*  715 */           out[o++] = (byte)(quadruplet[1] << 4 | quadruplet[2] >> 2);
/*      */         }
/*  717 */         if (quadruplet[3] != Byte.MAX_VALUE) {
/*  718 */           out[o++] = (byte)(quadruplet[2] << 6 | quadruplet[3]);
/*      */         }
/*  720 */         q = 0;
/*      */       } 
/*      */     } 
/*      */     
/*  724 */     if (buflen == o)
/*      */     {
/*  726 */       return out;
/*      */     }
/*      */ 
/*      */     
/*  730 */     byte[] nb = new byte[o];
/*  731 */     System.arraycopy(out, 0, nb, 0, o);
/*  732 */     return nb;
/*      */   }
/*  734 */   private static final char[] encodeMap = initEncodeMap(); private static final DatatypeFactory datatypeFactory;
/*      */   
/*      */   private static char[] initEncodeMap() {
/*  737 */     char[] map = new char[64];
/*      */     int i;
/*  739 */     for (i = 0; i < 26; i++) {
/*  740 */       map[i] = (char)(65 + i);
/*      */     }
/*  742 */     for (i = 26; i < 52; i++) {
/*  743 */       map[i] = (char)(97 + i - 26);
/*      */     }
/*  745 */     for (i = 52; i < 62; i++) {
/*  746 */       map[i] = (char)(48 + i - 52);
/*      */     }
/*  748 */     map[62] = '+';
/*  749 */     map[63] = '/';
/*      */     
/*  751 */     return map;
/*      */   }
/*      */   
/*      */   public static char encode(int i) {
/*  755 */     return encodeMap[i & 0x3F];
/*      */   }
/*      */   
/*      */   public static byte encodeByte(int i) {
/*  759 */     return (byte)encodeMap[i & 0x3F];
/*      */   }
/*      */   
/*      */   public static String _printBase64Binary(byte[] input) {
/*  763 */     return _printBase64Binary(input, 0, input.length);
/*      */   }
/*      */   
/*      */   public static String _printBase64Binary(byte[] input, int offset, int len) {
/*  767 */     char[] buf = new char[(len + 2) / 3 * 4];
/*  768 */     int ptr = _printBase64Binary(input, offset, len, buf, 0);
/*  769 */     assert ptr == buf.length;
/*  770 */     return new String(buf);
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
/*      */   public static int _printBase64Binary(byte[] input, int offset, int len, char[] buf, int ptr) {
/*  784 */     int remaining = len;
/*      */     int i;
/*  786 */     for (i = offset; remaining >= 3; remaining -= 3, i += 3) {
/*  787 */       buf[ptr++] = encode(input[i] >> 2);
/*  788 */       buf[ptr++] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  791 */       buf[ptr++] = encode((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*      */ 
/*      */       
/*  794 */       buf[ptr++] = encode(input[i + 2] & 0x3F);
/*      */     } 
/*      */     
/*  797 */     if (remaining == 1) {
/*  798 */       buf[ptr++] = encode(input[i] >> 2);
/*  799 */       buf[ptr++] = encode((input[i] & 0x3) << 4);
/*  800 */       buf[ptr++] = '=';
/*  801 */       buf[ptr++] = '=';
/*      */     } 
/*      */     
/*  804 */     if (remaining == 2) {
/*  805 */       buf[ptr++] = encode(input[i] >> 2);
/*  806 */       buf[ptr++] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */       
/*  808 */       buf[ptr++] = encode((input[i + 1] & 0xF) << 2);
/*  809 */       buf[ptr++] = '=';
/*      */     } 
/*  811 */     return ptr;
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
/*      */   public static int _printBase64Binary(byte[] input, int offset, int len, byte[] out, int ptr) {
/*  825 */     byte[] buf = out;
/*  826 */     int remaining = len;
/*      */     int i;
/*  828 */     for (i = offset; remaining >= 3; remaining -= 3, i += 3) {
/*  829 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  830 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  833 */       buf[ptr++] = encodeByte((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*      */ 
/*      */       
/*  836 */       buf[ptr++] = encodeByte(input[i + 2] & 0x3F);
/*      */     } 
/*      */     
/*  839 */     if (remaining == 1) {
/*  840 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  841 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4);
/*  842 */       buf[ptr++] = 61;
/*  843 */       buf[ptr++] = 61;
/*      */     } 
/*      */     
/*  846 */     if (remaining == 2) {
/*  847 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  848 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  851 */       buf[ptr++] = encodeByte((input[i + 1] & 0xF) << 2);
/*  852 */       buf[ptr++] = 61;
/*      */     } 
/*      */     
/*  855 */     return ptr;
/*      */   }
/*      */   
/*      */   private static CharSequence removeOptionalPlus(CharSequence s) {
/*  859 */     int len = s.length();
/*      */     
/*  861 */     if (len <= 1 || s.charAt(0) != '+') {
/*  862 */       return s;
/*      */     }
/*      */     
/*  865 */     s = s.subSequence(1, len);
/*  866 */     char ch = s.charAt(0);
/*  867 */     if ('0' <= ch && ch <= '9') {
/*  868 */       return s;
/*      */     }
/*  870 */     if ('.' == ch) {
/*  871 */       return s;
/*      */     }
/*      */     
/*  874 */     throw new NumberFormatException();
/*      */   }
/*      */   
/*      */   private static boolean isDigitOrPeriodOrSign(char ch) {
/*  878 */     if ('0' <= ch && ch <= '9') {
/*  879 */       return true;
/*      */     }
/*  881 */     if (ch == '+' || ch == '-' || ch == '.') {
/*  882 */       return true;
/*      */     }
/*  884 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/*  890 */       datatypeFactory = DatatypeFactory.newInstance();
/*  891 */     } catch (DatatypeConfigurationException e) {
/*  892 */       throw new Error(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static final class CalendarFormatter
/*      */   {
/*      */     public static String doFormat(String format, Calendar cal) throws IllegalArgumentException {
/*  899 */       int fidx = 0;
/*  900 */       int flen = format.length();
/*  901 */       StringBuilder buf = new StringBuilder();
/*      */       
/*  903 */       while (fidx < flen) {
/*  904 */         char fch = format.charAt(fidx++);
/*      */         
/*  906 */         if (fch != '%') {
/*  907 */           buf.append(fch);
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  912 */         switch (format.charAt(fidx++)) {
/*      */           case 'Y':
/*  914 */             formatYear(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'M':
/*  918 */             formatMonth(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'D':
/*  922 */             formatDays(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'h':
/*  926 */             formatHours(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'm':
/*  930 */             formatMinutes(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 's':
/*  934 */             formatSeconds(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'z':
/*  938 */             formatTimeZone(cal, buf);
/*      */             continue;
/*      */         } 
/*      */ 
/*      */         
/*  943 */         throw new InternalError();
/*      */       } 
/*      */ 
/*      */       
/*  947 */       return buf.toString();
/*      */     }
/*      */     private static void formatYear(Calendar cal, StringBuilder buf) {
/*      */       String s;
/*  951 */       int year = cal.get(1);
/*      */ 
/*      */       
/*  954 */       if (year <= 0) {
/*      */         
/*  956 */         s = Integer.toString(1 - year);
/*      */       } else {
/*      */         
/*  959 */         s = Integer.toString(year);
/*      */       } 
/*      */       
/*  962 */       while (s.length() < 4) {
/*  963 */         s = '0' + s;
/*      */       }
/*  965 */       if (year <= 0) {
/*  966 */         s = '-' + s;
/*      */       }
/*      */       
/*  969 */       buf.append(s);
/*      */     }
/*      */     
/*      */     private static void formatMonth(Calendar cal, StringBuilder buf) {
/*  973 */       formatTwoDigits(cal.get(2) + 1, buf);
/*      */     }
/*      */     
/*      */     private static void formatDays(Calendar cal, StringBuilder buf) {
/*  977 */       formatTwoDigits(cal.get(5), buf);
/*      */     }
/*      */     
/*      */     private static void formatHours(Calendar cal, StringBuilder buf) {
/*  981 */       formatTwoDigits(cal.get(11), buf);
/*      */     }
/*      */     
/*      */     private static void formatMinutes(Calendar cal, StringBuilder buf) {
/*  985 */       formatTwoDigits(cal.get(12), buf);
/*      */     }
/*      */     
/*      */     private static void formatSeconds(Calendar cal, StringBuilder buf) {
/*  989 */       formatTwoDigits(cal.get(13), buf);
/*  990 */       if (cal.isSet(14)) {
/*  991 */         int n = cal.get(14);
/*  992 */         if (n != 0) {
/*  993 */           String ms = Integer.toString(n);
/*  994 */           while (ms.length() < 3) {
/*  995 */             ms = '0' + ms;
/*      */           }
/*  997 */           buf.append('.');
/*  998 */           buf.append(ms);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private static void formatTimeZone(Calendar cal, StringBuilder buf) {
/* 1005 */       TimeZone tz = cal.getTimeZone();
/*      */       
/* 1007 */       if (tz == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1012 */       int offset = tz.getOffset(cal.getTime().getTime());
/*      */       
/* 1014 */       if (offset == 0) {
/* 1015 */         buf.append('Z');
/*      */         
/*      */         return;
/*      */       } 
/* 1019 */       if (offset >= 0) {
/* 1020 */         buf.append('+');
/*      */       } else {
/* 1022 */         buf.append('-');
/* 1023 */         offset *= -1;
/*      */       } 
/*      */       
/* 1026 */       offset /= 60000;
/*      */       
/* 1028 */       formatTwoDigits(offset / 60, buf);
/* 1029 */       buf.append(':');
/* 1030 */       formatTwoDigits(offset % 60, buf);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static void formatTwoDigits(int n, StringBuilder buf) {
/* 1036 */       if (n < 10) {
/* 1037 */         buf.append('0');
/*      */       }
/* 1039 */       buf.append(n);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/DatatypeConverterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */