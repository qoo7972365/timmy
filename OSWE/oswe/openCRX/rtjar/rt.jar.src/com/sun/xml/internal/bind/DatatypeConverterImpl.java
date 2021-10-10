/*      */ package com.sun.xml.internal.bind;
/*      */ 
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Map;
/*      */ import java.util.TimeZone;
/*      */ import java.util.WeakHashMap;
/*      */ import javax.xml.bind.DatatypeConverterInterface;
/*      */ import javax.xml.datatype.DatatypeConfigurationException;
/*      */ import javax.xml.datatype.DatatypeFactory;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamWriter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Deprecated
/*      */ public final class DatatypeConverterImpl
/*      */   implements DatatypeConverterInterface
/*      */ {
/*      */   @Deprecated
/*   69 */   public static final DatatypeConverterInterface theInstance = new DatatypeConverterImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigInteger _parseInteger(CharSequence s) {
/*   76 */     return new BigInteger(removeOptionalPlus(WhiteSpaceProcessor.trim(s)).toString());
/*      */   }
/*      */   
/*      */   public static String _printInteger(BigInteger val) {
/*   80 */     return val.toString();
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
/*   94 */     int len = s.length();
/*   95 */     int sign = 1;
/*      */     
/*   97 */     int r = 0;
/*      */     
/*   99 */     for (int i = 0; i < len; i++) {
/*  100 */       char ch = s.charAt(i);
/*  101 */       if (!WhiteSpaceProcessor.isWhiteSpace(ch))
/*      */       {
/*  103 */         if ('0' <= ch && ch <= '9') {
/*  104 */           r = r * 10 + ch - 48;
/*  105 */         } else if (ch == '-') {
/*  106 */           sign = -1;
/*  107 */         } else if (ch != '+') {
/*      */ 
/*      */           
/*  110 */           throw new NumberFormatException("Not a number: " + s);
/*      */         } 
/*      */       }
/*      */     } 
/*  114 */     return r * sign;
/*      */   }
/*      */   
/*      */   public static long _parseLong(CharSequence s) {
/*  118 */     return Long.valueOf(removeOptionalPlus(WhiteSpaceProcessor.trim(s)).toString()).longValue();
/*      */   }
/*      */   
/*      */   public static short _parseShort(CharSequence s) {
/*  122 */     return (short)_parseInt(s);
/*      */   }
/*      */   
/*      */   public static String _printShort(short val) {
/*  126 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public static BigDecimal _parseDecimal(CharSequence content) {
/*  130 */     content = WhiteSpaceProcessor.trim(content);
/*      */     
/*  132 */     if (content.length() <= 0) {
/*  133 */       return null;
/*      */     }
/*      */     
/*  136 */     return new BigDecimal(content.toString());
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
/*      */   public static float _parseFloat(CharSequence _val) {
/*  152 */     String s = WhiteSpaceProcessor.trim(_val).toString();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  168 */     if (s.equals("NaN")) {
/*  169 */       return Float.NaN;
/*      */     }
/*  171 */     if (s.equals("INF")) {
/*  172 */       return Float.POSITIVE_INFINITY;
/*      */     }
/*  174 */     if (s.equals("-INF")) {
/*  175 */       return Float.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/*  178 */     if (s.length() == 0 || 
/*  179 */       !isDigitOrPeriodOrSign(s.charAt(0)) || 
/*  180 */       !isDigitOrPeriodOrSign(s.charAt(s.length() - 1))) {
/*  181 */       throw new NumberFormatException();
/*      */     }
/*      */ 
/*      */     
/*  185 */     return Float.parseFloat(s);
/*      */   }
/*      */   
/*      */   public static String _printFloat(float v) {
/*  189 */     if (Float.isNaN(v)) {
/*  190 */       return "NaN";
/*      */     }
/*  192 */     if (v == Float.POSITIVE_INFINITY) {
/*  193 */       return "INF";
/*      */     }
/*  195 */     if (v == Float.NEGATIVE_INFINITY) {
/*  196 */       return "-INF";
/*      */     }
/*  198 */     return String.valueOf(v);
/*      */   }
/*      */   
/*      */   public static double _parseDouble(CharSequence _val) {
/*  202 */     String val = WhiteSpaceProcessor.trim(_val).toString();
/*      */     
/*  204 */     if (val.equals("NaN")) {
/*  205 */       return Double.NaN;
/*      */     }
/*  207 */     if (val.equals("INF")) {
/*  208 */       return Double.POSITIVE_INFINITY;
/*      */     }
/*  210 */     if (val.equals("-INF")) {
/*  211 */       return Double.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/*  214 */     if (val.length() == 0 || 
/*  215 */       !isDigitOrPeriodOrSign(val.charAt(0)) || 
/*  216 */       !isDigitOrPeriodOrSign(val.charAt(val.length() - 1))) {
/*  217 */       throw new NumberFormatException(val);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  222 */     return Double.parseDouble(val);
/*      */   } public static Boolean _parseBoolean(CharSequence literal) {
/*      */     char ch;
/*      */     String strTrue, strFalse;
/*  226 */     if (literal == null) {
/*  227 */       return null;
/*      */     }
/*      */     
/*  230 */     int i = 0;
/*  231 */     int len = literal.length();
/*      */     
/*  233 */     boolean value = false;
/*      */     
/*  235 */     if (literal.length() <= 0) {
/*  236 */       return null;
/*      */     }
/*      */     
/*      */     do {
/*  240 */       ch = literal.charAt(i++);
/*  241 */     } while (WhiteSpaceProcessor.isWhiteSpace(ch) && i < len);
/*      */     
/*  243 */     int strIndex = 0;
/*      */     
/*  245 */     switch (ch) {
/*      */       case '1':
/*  247 */         value = true;
/*      */         break;
/*      */       case '0':
/*  250 */         value = false;
/*      */         break;
/*      */       case 't':
/*  253 */         strTrue = "rue";
/*      */         do {
/*  255 */           ch = literal.charAt(i++);
/*  256 */         } while (strTrue.charAt(strIndex++) == ch && i < len && strIndex < 3);
/*      */         
/*  258 */         if (strIndex == 3) {
/*  259 */           value = true; break;
/*      */         } 
/*  261 */         return Boolean.valueOf(false);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 'f':
/*  267 */         strFalse = "alse";
/*      */         do {
/*  269 */           ch = literal.charAt(i++);
/*  270 */         } while (strFalse.charAt(strIndex++) == ch && i < len && strIndex < 4);
/*      */ 
/*      */         
/*  273 */         if (strIndex == 4) {
/*  274 */           value = false; break;
/*      */         } 
/*  276 */         return Boolean.valueOf(false);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  283 */     if (i < len) {
/*      */       do {
/*  285 */         ch = literal.charAt(i++);
/*  286 */       } while (WhiteSpaceProcessor.isWhiteSpace(ch) && i < len);
/*      */     }
/*      */     
/*  289 */     if (i == len) {
/*  290 */       return Boolean.valueOf(value);
/*      */     }
/*  292 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String _printBoolean(boolean val) {
/*  298 */     return val ? "true" : "false";
/*      */   }
/*      */   
/*      */   public static byte _parseByte(CharSequence literal) {
/*  302 */     return (byte)_parseInt(literal);
/*      */   }
/*      */   
/*      */   public static String _printByte(byte val) {
/*  306 */     return String.valueOf(val);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static QName _parseQName(CharSequence text, NamespaceContext nsc) {
/*      */     String uri, localPart, prefix;
/*  313 */     int length = text.length();
/*      */ 
/*      */     
/*  316 */     int start = 0;
/*  317 */     while (start < length && WhiteSpaceProcessor.isWhiteSpace(text.charAt(start))) {
/*  318 */       start++;
/*      */     }
/*      */     
/*  321 */     int end = length;
/*  322 */     while (end > start && WhiteSpaceProcessor.isWhiteSpace(text.charAt(end - 1))) {
/*  323 */       end--;
/*      */     }
/*      */     
/*  326 */     if (end == start) {
/*  327 */       throw new IllegalArgumentException("input is empty");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  336 */     int idx = start + 1;
/*  337 */     while (idx < end && text.charAt(idx) != ':') {
/*  338 */       idx++;
/*      */     }
/*      */     
/*  341 */     if (idx == end) {
/*  342 */       uri = nsc.getNamespaceURI("");
/*  343 */       localPart = text.subSequence(start, end).toString();
/*  344 */       prefix = "";
/*      */     } else {
/*      */       
/*  347 */       prefix = text.subSequence(start, idx).toString();
/*  348 */       localPart = text.subSequence(idx + 1, end).toString();
/*  349 */       uri = nsc.getNamespaceURI(prefix);
/*      */ 
/*      */       
/*  352 */       if (uri == null || uri.length() == 0)
/*      */       {
/*      */         
/*  355 */         throw new IllegalArgumentException("prefix " + prefix + " is not bound to a namespace");
/*      */       }
/*      */     } 
/*      */     
/*  359 */     return new QName(uri, localPart, prefix);
/*      */   }
/*      */   
/*      */   public static GregorianCalendar _parseDateTime(CharSequence s) {
/*  363 */     String val = WhiteSpaceProcessor.trim(s).toString();
/*  364 */     return getDatatypeFactory().newXMLGregorianCalendar(val).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   public static String _printDateTime(Calendar val) {
/*  368 */     return CalendarFormatter.doFormat("%Y-%M-%DT%h:%m:%s%z", val);
/*      */   }
/*      */   
/*      */   public static String _printDate(Calendar val) {
/*  372 */     return CalendarFormatter.doFormat("%Y-%M-%D" + "%z", val);
/*      */   }
/*      */   
/*      */   public static String _printInt(int val) {
/*  376 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public static String _printLong(long val) {
/*  380 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public static String _printDecimal(BigDecimal val) {
/*  384 */     return val.toPlainString();
/*      */   }
/*      */   
/*      */   public static String _printDouble(double v) {
/*  388 */     if (Double.isNaN(v)) {
/*  389 */       return "NaN";
/*      */     }
/*  391 */     if (v == Double.POSITIVE_INFINITY) {
/*  392 */       return "INF";
/*      */     }
/*  394 */     if (v == Double.NEGATIVE_INFINITY) {
/*  395 */       return "-INF";
/*      */     }
/*  397 */     return String.valueOf(v);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String _printQName(QName val, NamespaceContext nsc) {
/*  403 */     String qname, prefix = nsc.getPrefix(val.getNamespaceURI());
/*  404 */     String localPart = val.getLocalPart();
/*      */     
/*  406 */     if (prefix == null || prefix.length() == 0) {
/*  407 */       qname = localPart;
/*      */     } else {
/*  409 */       qname = prefix + ':' + localPart;
/*      */     } 
/*      */     
/*  412 */     return qname;
/*      */   }
/*      */ 
/*      */   
/*  416 */   private static final byte[] decodeMap = initDecodeMap();
/*      */   private static final byte PADDING = 127;
/*      */   
/*      */   private static byte[] initDecodeMap() {
/*  420 */     byte[] map = new byte[128];
/*      */     int i;
/*  422 */     for (i = 0; i < 128; i++) {
/*  423 */       map[i] = -1;
/*      */     }
/*      */     
/*  426 */     for (i = 65; i <= 90; i++) {
/*  427 */       map[i] = (byte)(i - 65);
/*      */     }
/*  429 */     for (i = 97; i <= 122; i++) {
/*  430 */       map[i] = (byte)(i - 97 + 26);
/*      */     }
/*  432 */     for (i = 48; i <= 57; i++) {
/*  433 */       map[i] = (byte)(i - 48 + 52);
/*      */     }
/*  435 */     map[43] = 62;
/*  436 */     map[47] = 63;
/*  437 */     map[61] = Byte.MAX_VALUE;
/*      */     
/*  439 */     return map;
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
/*  463 */     int len = text.length();
/*      */ 
/*      */     
/*  466 */     int j = len - 1;
/*  467 */     while (j >= 0) {
/*  468 */       byte code = decodeMap[text.charAt(j)];
/*  469 */       if (code == Byte.MAX_VALUE) {
/*      */         j--; continue;
/*      */       } 
/*  472 */       if (code == -1)
/*      */       {
/*  474 */         return text.length() / 4 * 3;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  479 */     j++;
/*  480 */     int padSize = len - j;
/*  481 */     if (padSize > 2)
/*      */     {
/*  483 */       return text.length() / 4 * 3;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  488 */     return text.length() / 4 * 3 - padSize;
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
/*  501 */     int buflen = guessLength(text);
/*  502 */     byte[] out = new byte[buflen];
/*  503 */     int o = 0;
/*      */     
/*  505 */     int len = text.length();
/*      */ 
/*      */     
/*  508 */     byte[] quadruplet = new byte[4];
/*  509 */     int q = 0;
/*      */ 
/*      */     
/*  512 */     for (int i = 0; i < len; i++) {
/*  513 */       char ch = text.charAt(i);
/*  514 */       byte v = decodeMap[ch];
/*      */       
/*  516 */       if (v != -1) {
/*  517 */         quadruplet[q++] = v;
/*      */       }
/*      */       
/*  520 */       if (q == 4) {
/*      */         
/*  522 */         out[o++] = (byte)(quadruplet[0] << 2 | quadruplet[1] >> 4);
/*  523 */         if (quadruplet[2] != Byte.MAX_VALUE) {
/*  524 */           out[o++] = (byte)(quadruplet[1] << 4 | quadruplet[2] >> 2);
/*      */         }
/*  526 */         if (quadruplet[3] != Byte.MAX_VALUE) {
/*  527 */           out[o++] = (byte)(quadruplet[2] << 6 | quadruplet[3]);
/*      */         }
/*  529 */         q = 0;
/*      */       } 
/*      */     } 
/*      */     
/*  533 */     if (buflen == o)
/*      */     {
/*  535 */       return out;
/*      */     }
/*      */ 
/*      */     
/*  539 */     byte[] nb = new byte[o];
/*  540 */     System.arraycopy(out, 0, nb, 0, o);
/*  541 */     return nb;
/*      */   }
/*  543 */   private static final char[] encodeMap = initEncodeMap();
/*      */   
/*      */   private static char[] initEncodeMap() {
/*  546 */     char[] map = new char[64];
/*      */     int i;
/*  548 */     for (i = 0; i < 26; i++) {
/*  549 */       map[i] = (char)(65 + i);
/*      */     }
/*  551 */     for (i = 26; i < 52; i++) {
/*  552 */       map[i] = (char)(97 + i - 26);
/*      */     }
/*  554 */     for (i = 52; i < 62; i++) {
/*  555 */       map[i] = (char)(48 + i - 52);
/*      */     }
/*  557 */     map[62] = '+';
/*  558 */     map[63] = '/';
/*      */     
/*  560 */     return map;
/*      */   }
/*      */   
/*      */   public static char encode(int i) {
/*  564 */     return encodeMap[i & 0x3F];
/*      */   }
/*      */   
/*      */   public static byte encodeByte(int i) {
/*  568 */     return (byte)encodeMap[i & 0x3F];
/*      */   }
/*      */   
/*      */   public static String _printBase64Binary(byte[] input) {
/*  572 */     return _printBase64Binary(input, 0, input.length);
/*      */   }
/*      */   
/*      */   public static String _printBase64Binary(byte[] input, int offset, int len) {
/*  576 */     char[] buf = new char[(len + 2) / 3 * 4];
/*  577 */     int ptr = _printBase64Binary(input, offset, len, buf, 0);
/*  578 */     assert ptr == buf.length;
/*  579 */     return new String(buf);
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
/*  593 */     int remaining = len;
/*      */     int i;
/*  595 */     for (i = offset; remaining >= 3; remaining -= 3, i += 3) {
/*  596 */       buf[ptr++] = encode(input[i] >> 2);
/*  597 */       buf[ptr++] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  600 */       buf[ptr++] = encode((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*      */ 
/*      */       
/*  603 */       buf[ptr++] = encode(input[i + 2] & 0x3F);
/*      */     } 
/*      */     
/*  606 */     if (remaining == 1) {
/*  607 */       buf[ptr++] = encode(input[i] >> 2);
/*  608 */       buf[ptr++] = encode((input[i] & 0x3) << 4);
/*  609 */       buf[ptr++] = '=';
/*  610 */       buf[ptr++] = '=';
/*      */     } 
/*      */     
/*  613 */     if (remaining == 2) {
/*  614 */       buf[ptr++] = encode(input[i] >> 2);
/*  615 */       buf[ptr++] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */       
/*  617 */       buf[ptr++] = encode((input[i + 1] & 0xF) << 2);
/*  618 */       buf[ptr++] = '=';
/*      */     } 
/*  620 */     return ptr;
/*      */   }
/*      */   
/*      */   public static void _printBase64Binary(byte[] input, int offset, int len, XMLStreamWriter output) throws XMLStreamException {
/*  624 */     int remaining = len;
/*      */     
/*  626 */     char[] buf = new char[4];
/*      */     int i;
/*  628 */     for (i = offset; remaining >= 3; remaining -= 3, i += 3) {
/*  629 */       buf[0] = encode(input[i] >> 2);
/*  630 */       buf[1] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  633 */       buf[2] = encode((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*      */ 
/*      */       
/*  636 */       buf[3] = encode(input[i + 2] & 0x3F);
/*  637 */       output.writeCharacters(buf, 0, 4);
/*      */     } 
/*      */     
/*  640 */     if (remaining == 1) {
/*  641 */       buf[0] = encode(input[i] >> 2);
/*  642 */       buf[1] = encode((input[i] & 0x3) << 4);
/*  643 */       buf[2] = '=';
/*  644 */       buf[3] = '=';
/*  645 */       output.writeCharacters(buf, 0, 4);
/*      */     } 
/*      */     
/*  648 */     if (remaining == 2) {
/*  649 */       buf[0] = encode(input[i] >> 2);
/*  650 */       buf[1] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */       
/*  652 */       buf[2] = encode((input[i + 1] & 0xF) << 2);
/*  653 */       buf[3] = '=';
/*  654 */       output.writeCharacters(buf, 0, 4);
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
/*      */   public static int _printBase64Binary(byte[] input, int offset, int len, byte[] out, int ptr) {
/*  669 */     byte[] buf = out;
/*  670 */     int remaining = len;
/*      */     int i;
/*  672 */     for (i = offset; remaining >= 3; remaining -= 3, i += 3) {
/*  673 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  674 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  677 */       buf[ptr++] = encodeByte((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*      */ 
/*      */       
/*  680 */       buf[ptr++] = encodeByte(input[i + 2] & 0x3F);
/*      */     } 
/*      */     
/*  683 */     if (remaining == 1) {
/*  684 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  685 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4);
/*  686 */       buf[ptr++] = 61;
/*  687 */       buf[ptr++] = 61;
/*      */     } 
/*      */     
/*  690 */     if (remaining == 2) {
/*  691 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  692 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  695 */       buf[ptr++] = encodeByte((input[i + 1] & 0xF) << 2);
/*  696 */       buf[ptr++] = 61;
/*      */     } 
/*      */     
/*  699 */     return ptr;
/*      */   }
/*      */   
/*      */   private static CharSequence removeOptionalPlus(CharSequence s) {
/*  703 */     int len = s.length();
/*      */     
/*  705 */     if (len <= 1 || s.charAt(0) != '+') {
/*  706 */       return s;
/*      */     }
/*      */     
/*  709 */     s = s.subSequence(1, len);
/*  710 */     char ch = s.charAt(0);
/*  711 */     if ('0' <= ch && ch <= '9') {
/*  712 */       return s;
/*      */     }
/*  714 */     if ('.' == ch) {
/*  715 */       return s;
/*      */     }
/*      */     
/*  718 */     throw new NumberFormatException();
/*      */   }
/*      */   
/*      */   private static boolean isDigitOrPeriodOrSign(char ch) {
/*  722 */     if ('0' <= ch && ch <= '9') {
/*  723 */       return true;
/*      */     }
/*  725 */     if (ch == '+' || ch == '-' || ch == '.') {
/*  726 */       return true;
/*      */     }
/*  728 */     return false;
/*      */   }
/*      */   
/*  731 */   private static final Map<ClassLoader, DatatypeFactory> DF_CACHE = Collections.synchronizedMap(new WeakHashMap<>());
/*      */   
/*      */   public static DatatypeFactory getDatatypeFactory() {
/*  734 */     ClassLoader tccl = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*      */           public ClassLoader run() {
/*  736 */             return Thread.currentThread().getContextClassLoader();
/*      */           }
/*      */         });
/*  739 */     DatatypeFactory df = DF_CACHE.get(tccl);
/*  740 */     if (df == null) {
/*  741 */       synchronized (DatatypeConverterImpl.class) {
/*  742 */         df = DF_CACHE.get(tccl);
/*  743 */         if (df == null) {
/*      */           try {
/*  745 */             df = DatatypeFactory.newInstance();
/*  746 */           } catch (DatatypeConfigurationException e) {
/*  747 */             throw new Error(Messages.FAILED_TO_INITIALE_DATATYPE_FACTORY.format(new Object[0]), e);
/*      */           } 
/*  749 */           DF_CACHE.put(tccl, df);
/*      */         } 
/*      */       } 
/*      */     }
/*  753 */     return df;
/*      */   }
/*      */   
/*      */   private static final class CalendarFormatter
/*      */   {
/*      */     public static String doFormat(String format, Calendar cal) throws IllegalArgumentException {
/*  759 */       int fidx = 0;
/*  760 */       int flen = format.length();
/*  761 */       StringBuilder buf = new StringBuilder();
/*      */       
/*  763 */       while (fidx < flen) {
/*  764 */         char fch = format.charAt(fidx++);
/*      */         
/*  766 */         if (fch != '%') {
/*  767 */           buf.append(fch);
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  772 */         switch (format.charAt(fidx++)) {
/*      */           case 'Y':
/*  774 */             formatYear(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'M':
/*  778 */             formatMonth(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'D':
/*  782 */             formatDays(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'h':
/*  786 */             formatHours(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'm':
/*  790 */             formatMinutes(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 's':
/*  794 */             formatSeconds(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'z':
/*  798 */             formatTimeZone(cal, buf);
/*      */             continue;
/*      */         } 
/*      */ 
/*      */         
/*  803 */         throw new InternalError();
/*      */       } 
/*      */ 
/*      */       
/*  807 */       return buf.toString();
/*      */     }
/*      */     private static void formatYear(Calendar cal, StringBuilder buf) {
/*      */       String s;
/*  811 */       int year = cal.get(1);
/*      */ 
/*      */       
/*  814 */       if (year <= 0) {
/*      */         
/*  816 */         s = Integer.toString(1 - year);
/*      */       } else {
/*      */         
/*  819 */         s = Integer.toString(year);
/*      */       } 
/*      */       
/*  822 */       while (s.length() < 4) {
/*  823 */         s = '0' + s;
/*      */       }
/*  825 */       if (year <= 0) {
/*  826 */         s = '-' + s;
/*      */       }
/*      */       
/*  829 */       buf.append(s);
/*      */     }
/*      */     
/*      */     private static void formatMonth(Calendar cal, StringBuilder buf) {
/*  833 */       formatTwoDigits(cal.get(2) + 1, buf);
/*      */     }
/*      */     
/*      */     private static void formatDays(Calendar cal, StringBuilder buf) {
/*  837 */       formatTwoDigits(cal.get(5), buf);
/*      */     }
/*      */     
/*      */     private static void formatHours(Calendar cal, StringBuilder buf) {
/*  841 */       formatTwoDigits(cal.get(11), buf);
/*      */     }
/*      */     
/*      */     private static void formatMinutes(Calendar cal, StringBuilder buf) {
/*  845 */       formatTwoDigits(cal.get(12), buf);
/*      */     }
/*      */     
/*      */     private static void formatSeconds(Calendar cal, StringBuilder buf) {
/*  849 */       formatTwoDigits(cal.get(13), buf);
/*  850 */       if (cal.isSet(14)) {
/*  851 */         int n = cal.get(14);
/*  852 */         if (n != 0) {
/*  853 */           String ms = Integer.toString(n);
/*  854 */           while (ms.length() < 3) {
/*  855 */             ms = '0' + ms;
/*      */           }
/*  857 */           buf.append('.');
/*  858 */           buf.append(ms);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private static void formatTimeZone(Calendar cal, StringBuilder buf) {
/*  865 */       TimeZone tz = cal.getTimeZone();
/*      */       
/*  867 */       if (tz == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  872 */       int offset = tz.getOffset(cal.getTime().getTime());
/*      */       
/*  874 */       if (offset == 0) {
/*  875 */         buf.append('Z');
/*      */         
/*      */         return;
/*      */       } 
/*  879 */       if (offset >= 0) {
/*  880 */         buf.append('+');
/*      */       } else {
/*  882 */         buf.append('-');
/*  883 */         offset *= -1;
/*      */       } 
/*      */       
/*  886 */       offset /= 60000;
/*      */       
/*  888 */       formatTwoDigits(offset / 60, buf);
/*  889 */       buf.append(':');
/*  890 */       formatTwoDigits(offset % 60, buf);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static void formatTwoDigits(int n, StringBuilder buf) {
/*  896 */       if (n < 10) {
/*  897 */         buf.append('0');
/*      */       }
/*  899 */       buf.append(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public String parseString(String lexicalXSDString) {
/*  907 */     return lexicalXSDString;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public BigInteger parseInteger(String lexicalXSDInteger) {
/*  912 */     return _parseInteger(lexicalXSDInteger);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printInteger(BigInteger val) {
/*  917 */     return _printInteger(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public int parseInt(String s) {
/*  922 */     return _parseInt(s);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public long parseLong(String lexicalXSLong) {
/*  927 */     return _parseLong(lexicalXSLong);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public short parseShort(String lexicalXSDShort) {
/*  932 */     return _parseShort(lexicalXSDShort);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printShort(short val) {
/*  937 */     return _printShort(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public BigDecimal parseDecimal(String content) {
/*  942 */     return _parseDecimal(content);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public float parseFloat(String lexicalXSDFloat) {
/*  947 */     return _parseFloat(lexicalXSDFloat);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printFloat(float v) {
/*  952 */     return _printFloat(v);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public double parseDouble(String lexicalXSDDouble) {
/*  957 */     return _parseDouble(lexicalXSDDouble);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public boolean parseBoolean(String lexicalXSDBoolean) {
/*  962 */     Boolean b = _parseBoolean(lexicalXSDBoolean);
/*  963 */     return (b == null) ? false : b.booleanValue();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printBoolean(boolean val) {
/*  968 */     return val ? "true" : "false";
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public byte parseByte(String lexicalXSDByte) {
/*  973 */     return _parseByte(lexicalXSDByte);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printByte(byte val) {
/*  978 */     return _printByte(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public QName parseQName(String lexicalXSDQName, NamespaceContext nsc) {
/*  983 */     return _parseQName(lexicalXSDQName, nsc);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public Calendar parseDateTime(String lexicalXSDDateTime) {
/*  988 */     return _parseDateTime(lexicalXSDDateTime);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printDateTime(Calendar val) {
/*  993 */     return _printDateTime(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public byte[] parseBase64Binary(String lexicalXSDBase64Binary) {
/*  998 */     return _parseBase64Binary(lexicalXSDBase64Binary);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public byte[] parseHexBinary(String s) {
/* 1003 */     int len = s.length();
/*      */ 
/*      */     
/* 1006 */     if (len % 2 != 0) {
/* 1007 */       throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
/*      */     }
/*      */     
/* 1010 */     byte[] out = new byte[len / 2];
/*      */     
/* 1012 */     for (int i = 0; i < len; i += 2) {
/* 1013 */       int h = hexToBin(s.charAt(i));
/* 1014 */       int l = hexToBin(s.charAt(i + 1));
/* 1015 */       if (h == -1 || l == -1) {
/* 1016 */         throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
/*      */       }
/*      */       
/* 1019 */       out[i / 2] = (byte)(h * 16 + l);
/*      */     } 
/*      */     
/* 1022 */     return out;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   private static int hexToBin(char ch) {
/* 1027 */     if ('0' <= ch && ch <= '9') {
/* 1028 */       return ch - 48;
/*      */     }
/* 1030 */     if ('A' <= ch && ch <= 'F') {
/* 1031 */       return ch - 65 + 10;
/*      */     }
/* 1033 */     if ('a' <= ch && ch <= 'f') {
/* 1034 */       return ch - 97 + 10;
/*      */     }
/* 1036 */     return -1;
/*      */   }
/*      */   
/*      */   @Deprecated
/* 1040 */   private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
/*      */   
/*      */   @Deprecated
/*      */   public String printHexBinary(byte[] data) {
/* 1044 */     StringBuilder r = new StringBuilder(data.length * 2);
/* 1045 */     for (byte b : data) {
/* 1046 */       r.append(hexCode[b >> 4 & 0xF]);
/* 1047 */       r.append(hexCode[b & 0xF]);
/*      */     } 
/* 1049 */     return r.toString();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public long parseUnsignedInt(String lexicalXSDUnsignedInt) {
/* 1054 */     return _parseLong(lexicalXSDUnsignedInt);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printUnsignedInt(long val) {
/* 1059 */     return _printLong(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public int parseUnsignedShort(String lexicalXSDUnsignedShort) {
/* 1064 */     return _parseInt(lexicalXSDUnsignedShort);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public Calendar parseTime(String lexicalXSDTime) {
/* 1069 */     return getDatatypeFactory().newXMLGregorianCalendar(lexicalXSDTime).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printTime(Calendar val) {
/* 1074 */     return CalendarFormatter.doFormat("%h:%m:%s%z", val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public Calendar parseDate(String lexicalXSDDate) {
/* 1079 */     return getDatatypeFactory().newXMLGregorianCalendar(lexicalXSDDate).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printDate(Calendar val) {
/* 1084 */     return _printDate(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String parseAnySimpleType(String lexicalXSDAnySimpleType) {
/* 1089 */     return lexicalXSDAnySimpleType;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printString(String val) {
/* 1094 */     return val;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printInt(int val) {
/* 1099 */     return _printInt(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printLong(long val) {
/* 1104 */     return _printLong(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printDecimal(BigDecimal val) {
/* 1109 */     return _printDecimal(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printDouble(double v) {
/* 1114 */     return _printDouble(v);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printQName(QName val, NamespaceContext nsc) {
/* 1119 */     return _printQName(val, nsc);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printBase64Binary(byte[] val) {
/* 1124 */     return _printBase64Binary(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printUnsignedShort(int val) {
/* 1129 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printAnySimpleType(String val) {
/* 1134 */     return val;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/DatatypeConverterImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */