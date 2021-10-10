/*      */ package com.sun.xml.internal.messaging.saaj.packaging.mime.internet;
/*      */ 
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64DecoderStream;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.LineInputStream;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QDecoderStream;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QPDecoderStream;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QPEncoderStream;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.UUDecoderStream;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.UUEncoderStream;
/*      */ import com.sun.xml.internal.messaging.saaj.util.SAAJUtil;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.Hashtable;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.activation.DataHandler;
/*      */ import javax.activation.DataSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MimeUtility
/*      */ {
/*      */   public static final int ALL = -1;
/*      */   private static final int BUFFER_SIZE = 1024;
/*      */   private static boolean decodeStrict = true;
/*      */   private static boolean encodeEolStrict = false;
/*      */   private static boolean foldEncodedWords = false;
/*      */   private static boolean foldText = true;
/*      */   private static String defaultJavaCharset;
/*      */   private static String defaultMIMECharset;
/*      */   private static Hashtable mime2java;
/*      */   
/*      */   static {
/*      */     try {
/*  138 */       String s = SAAJUtil.getSystemProperty("mail.mime.decodetext.strict");
/*      */       
/*  140 */       decodeStrict = (s == null || !s.equalsIgnoreCase("false"));
/*  141 */       s = SAAJUtil.getSystemProperty("mail.mime.encodeeol.strict");
/*      */       
/*  143 */       encodeEolStrict = (s != null && s.equalsIgnoreCase("true"));
/*  144 */       s = SAAJUtil.getSystemProperty("mail.mime.foldencodedwords");
/*      */       
/*  146 */       foldEncodedWords = (s != null && s.equalsIgnoreCase("true"));
/*  147 */       s = SAAJUtil.getSystemProperty("mail.mime.foldtext");
/*      */       
/*  149 */       foldText = (s == null || !s.equalsIgnoreCase("false"));
/*  150 */     } catch (SecurityException securityException) {}
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
/*      */   public static String getEncoding(DataSource ds) {
/*  180 */     ContentType cType = null;
/*  181 */     InputStream is = null;
/*  182 */     String encoding = null;
/*      */     
/*      */     try {
/*  185 */       cType = new ContentType(ds.getContentType());
/*  186 */       is = ds.getInputStream();
/*  187 */     } catch (Exception ex) {
/*  188 */       return "base64";
/*      */     } 
/*      */     
/*  191 */     boolean isText = cType.match("text/*");
/*      */     
/*  193 */     int i = checkAscii(is, -1, !isText);
/*  194 */     switch (i) {
/*      */       case 1:
/*  196 */         encoding = "7bit";
/*      */         break;
/*      */       case 2:
/*  199 */         encoding = "quoted-printable";
/*      */         break;
/*      */       default:
/*  202 */         encoding = "base64";
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  208 */       is.close();
/*  209 */     } catch (IOException iOException) {}
/*      */     
/*  211 */     return encoding;
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
/*      */   public static String getEncoding(DataHandler dh) {
/*  228 */     ContentType cType = null;
/*  229 */     String encoding = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  244 */     if (dh.getName() != null) {
/*  245 */       return getEncoding(dh.getDataSource());
/*      */     }
/*      */     try {
/*  248 */       cType = new ContentType(dh.getContentType());
/*  249 */     } catch (Exception ex) {
/*  250 */       return "base64";
/*      */     } 
/*      */     
/*  253 */     if (cType.match("text/*"))
/*      */     
/*  255 */     { AsciiOutputStream aos = new AsciiOutputStream(false, false);
/*      */       try {
/*  257 */         dh.writeTo(aos);
/*  258 */       } catch (IOException iOException) {}
/*  259 */       switch (aos.getAscii())
/*      */       { case 1:
/*  261 */           encoding = "7bit";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  284 */           return encoding;case 2: encoding = "quoted-printable"; return encoding; }  encoding = "base64"; } else { AsciiOutputStream aos = new AsciiOutputStream(true, encodeEolStrict); try { dh.writeTo(aos); } catch (IOException iOException) {} if (aos.getAscii() == 1) { encoding = "7bit"; } else { encoding = "base64"; }  }  return encoding;
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
/*      */   public static InputStream decode(InputStream is, String encoding) throws MessagingException {
/*  300 */     if (encoding.equalsIgnoreCase("base64"))
/*  301 */       return (InputStream)new BASE64DecoderStream(is); 
/*  302 */     if (encoding.equalsIgnoreCase("quoted-printable"))
/*  303 */       return (InputStream)new QPDecoderStream(is); 
/*  304 */     if (encoding.equalsIgnoreCase("uuencode") || encoding
/*  305 */       .equalsIgnoreCase("x-uuencode") || encoding
/*  306 */       .equalsIgnoreCase("x-uue"))
/*  307 */       return (InputStream)new UUDecoderStream(is); 
/*  308 */     if (encoding.equalsIgnoreCase("binary") || encoding
/*  309 */       .equalsIgnoreCase("7bit") || encoding
/*  310 */       .equalsIgnoreCase("8bit")) {
/*  311 */       return is;
/*      */     }
/*  313 */     throw new MessagingException("Unknown encoding: " + encoding);
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
/*      */   public static OutputStream encode(OutputStream os, String encoding) throws MessagingException {
/*  329 */     if (encoding == null)
/*  330 */       return os; 
/*  331 */     if (encoding.equalsIgnoreCase("base64"))
/*  332 */       return (OutputStream)new BASE64EncoderStream(os); 
/*  333 */     if (encoding.equalsIgnoreCase("quoted-printable"))
/*  334 */       return (OutputStream)new QPEncoderStream(os); 
/*  335 */     if (encoding.equalsIgnoreCase("uuencode") || encoding
/*  336 */       .equalsIgnoreCase("x-uuencode") || encoding
/*  337 */       .equalsIgnoreCase("x-uue"))
/*  338 */       return (OutputStream)new UUEncoderStream(os); 
/*  339 */     if (encoding.equalsIgnoreCase("binary") || encoding
/*  340 */       .equalsIgnoreCase("7bit") || encoding
/*  341 */       .equalsIgnoreCase("8bit")) {
/*  342 */       return os;
/*      */     }
/*  344 */     throw new MessagingException("Unknown encoding: " + encoding);
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
/*      */   public static OutputStream encode(OutputStream os, String encoding, String filename) throws MessagingException {
/*  366 */     if (encoding == null)
/*  367 */       return os; 
/*  368 */     if (encoding.equalsIgnoreCase("base64"))
/*  369 */       return (OutputStream)new BASE64EncoderStream(os); 
/*  370 */     if (encoding.equalsIgnoreCase("quoted-printable"))
/*  371 */       return (OutputStream)new QPEncoderStream(os); 
/*  372 */     if (encoding.equalsIgnoreCase("uuencode") || encoding
/*  373 */       .equalsIgnoreCase("x-uuencode") || encoding
/*  374 */       .equalsIgnoreCase("x-uue"))
/*  375 */       return (OutputStream)new UUEncoderStream(os, filename); 
/*  376 */     if (encoding.equalsIgnoreCase("binary") || encoding
/*  377 */       .equalsIgnoreCase("7bit") || encoding
/*  378 */       .equalsIgnoreCase("8bit")) {
/*  379 */       return os;
/*      */     }
/*  381 */     throw new MessagingException("Unknown encoding: " + encoding);
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
/*      */   public static String encodeText(String text) throws UnsupportedEncodingException {
/*  422 */     return encodeText(text, null, null);
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
/*      */   public static String encodeText(String text, String charset, String encoding) throws UnsupportedEncodingException {
/*  453 */     return encodeWord(text, charset, encoding, false);
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
/*      */   public static String decodeText(String etext) throws UnsupportedEncodingException {
/*  495 */     String lwsp = " \t\n\r";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  507 */     if (etext.indexOf("=?") == -1) {
/*  508 */       return etext;
/*      */     }
/*      */ 
/*      */     
/*  512 */     StringTokenizer st = new StringTokenizer(etext, lwsp, true);
/*  513 */     StringBuffer sb = new StringBuffer();
/*  514 */     StringBuffer wsb = new StringBuffer();
/*  515 */     boolean prevWasEncoded = false;
/*      */     
/*  517 */     while (st.hasMoreTokens()) {
/*      */       
/*  519 */       String word, s = st.nextToken();
/*      */       char c;
/*  521 */       if ((c = s.charAt(0)) == ' ' || c == '\t' || c == '\r' || c == '\n') {
/*      */         
/*  523 */         wsb.append(c);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       try {
/*  528 */         word = decodeWord(s);
/*      */         
/*  530 */         if (!prevWasEncoded && wsb.length() > 0)
/*      */         {
/*      */ 
/*      */           
/*  534 */           sb.append(wsb);
/*      */         }
/*  536 */         prevWasEncoded = true;
/*  537 */       } catch (ParseException pex) {
/*      */         
/*  539 */         word = s;
/*      */         
/*  541 */         if (!decodeStrict) {
/*  542 */           word = decodeInnerWords(word);
/*      */         }
/*  544 */         if (wsb.length() > 0)
/*  545 */           sb.append(wsb); 
/*  546 */         prevWasEncoded = false;
/*      */       } 
/*  548 */       sb.append(word);
/*  549 */       wsb.setLength(0);
/*      */     } 
/*      */     
/*  552 */     return sb.toString();
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
/*      */   public static String encodeWord(String word) throws UnsupportedEncodingException {
/*  578 */     return encodeWord(word, null, null);
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
/*      */   public static String encodeWord(String word, String charset, String encoding) throws UnsupportedEncodingException {
/*  606 */     return encodeWord(word, charset, encoding, true);
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
/*      */   private static String encodeWord(String string, String charset, String encoding, boolean encodingWord) throws UnsupportedEncodingException {
/*      */     String jcharset;
/*      */     boolean b64;
/*  622 */     int ascii = checkAscii(string);
/*  623 */     if (ascii == 1) {
/*  624 */       return string;
/*      */     }
/*      */ 
/*      */     
/*  628 */     if (charset == null) {
/*  629 */       jcharset = getDefaultJavaCharset();
/*  630 */       charset = getDefaultMIMECharset();
/*      */     } else {
/*  632 */       jcharset = javaCharset(charset);
/*      */     } 
/*      */     
/*  635 */     if (encoding == null) {
/*  636 */       if (ascii != 3) {
/*  637 */         encoding = "Q";
/*      */       } else {
/*  639 */         encoding = "B";
/*      */       } 
/*      */     }
/*      */     
/*  643 */     if (encoding.equalsIgnoreCase("B")) {
/*  644 */       b64 = true;
/*  645 */     } else if (encoding.equalsIgnoreCase("Q")) {
/*  646 */       b64 = false;
/*      */     } else {
/*  648 */       throw new UnsupportedEncodingException("Unknown transfer encoding: " + encoding);
/*      */     } 
/*      */     
/*  651 */     StringBuffer outb = new StringBuffer();
/*  652 */     doEncode(string, b64, jcharset, 68 - charset
/*      */ 
/*      */ 
/*      */         
/*  656 */         .length(), "=?" + charset + "?" + encoding + "?", true, encodingWord, outb);
/*      */ 
/*      */ 
/*      */     
/*  660 */     return outb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void doEncode(String string, boolean b64, String jcharset, int avail, String prefix, boolean first, boolean encodingWord, StringBuffer buf) throws UnsupportedEncodingException {
/*      */     int len;
/*  670 */     byte[] bytes = string.getBytes(jcharset);
/*      */     
/*  672 */     if (b64) {
/*  673 */       len = BEncoderStream.encodedLength(bytes);
/*      */     } else {
/*  675 */       len = QEncoderStream.encodedLength(bytes, encodingWord);
/*      */     } 
/*      */     int size;
/*  678 */     if (len > avail && (size = string.length()) > 1) {
/*      */ 
/*      */       
/*  681 */       doEncode(string.substring(0, size / 2), b64, jcharset, avail, prefix, first, encodingWord, buf);
/*      */       
/*  683 */       doEncode(string.substring(size / 2, size), b64, jcharset, avail, prefix, false, encodingWord, buf);
/*      */     } else {
/*      */       QEncoderStream qEncoderStream;
/*      */       
/*  687 */       ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
/*      */       
/*  689 */       if (b64) {
/*  690 */         BEncoderStream bEncoderStream = new BEncoderStream(os);
/*      */       } else {
/*  692 */         qEncoderStream = new QEncoderStream(os, encodingWord);
/*      */       } 
/*      */       try {
/*  695 */         qEncoderStream.write(bytes);
/*  696 */         qEncoderStream.close();
/*  697 */       } catch (IOException iOException) {}
/*      */       
/*  699 */       byte[] encodedBytes = os.toByteArray();
/*      */ 
/*      */       
/*  702 */       if (!first)
/*  703 */         if (foldEncodedWords) {
/*  704 */           buf.append("\r\n ");
/*      */         } else {
/*  706 */           buf.append(" ");
/*      */         }  
/*  708 */       buf.append(prefix);
/*  709 */       for (int i = 0; i < encodedBytes.length; i++)
/*  710 */         buf.append((char)encodedBytes[i]); 
/*  711 */       buf.append("?=");
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
/*      */   public static String decodeWord(String eword) throws ParseException, UnsupportedEncodingException {
/*  731 */     if (!eword.startsWith("=?")) {
/*  732 */       throw new ParseException();
/*      */     }
/*      */     
/*  735 */     int start = 2; int pos;
/*  736 */     if ((pos = eword.indexOf('?', start)) == -1)
/*  737 */       throw new ParseException(); 
/*  738 */     String charset = javaCharset(eword.substring(start, pos));
/*      */ 
/*      */     
/*  741 */     start = pos + 1;
/*  742 */     if ((pos = eword.indexOf('?', start)) == -1)
/*  743 */       throw new ParseException(); 
/*  744 */     String encoding = eword.substring(start, pos);
/*      */ 
/*      */     
/*  747 */     start = pos + 1;
/*  748 */     if ((pos = eword.indexOf("?=", start)) == -1)
/*  749 */       throw new ParseException(); 
/*  750 */     String word = eword.substring(start, pos);
/*      */     
/*      */     try {
/*      */       QDecoderStream qDecoderStream;
/*      */       
/*  755 */       ByteArrayInputStream bis = new ByteArrayInputStream(ASCIIUtility.getBytes(word));
/*      */ 
/*      */ 
/*      */       
/*  759 */       if (encoding.equalsIgnoreCase("B")) {
/*  760 */         BASE64DecoderStream bASE64DecoderStream = new BASE64DecoderStream(bis);
/*  761 */       } else if (encoding.equalsIgnoreCase("Q")) {
/*  762 */         qDecoderStream = new QDecoderStream(bis);
/*      */       } else {
/*  764 */         throw new UnsupportedEncodingException("unknown encoding: " + encoding);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  772 */       int count = bis.available();
/*  773 */       byte[] bytes = new byte[count];
/*      */       
/*  775 */       count = qDecoderStream.read(bytes, 0, count);
/*      */ 
/*      */ 
/*      */       
/*  779 */       String s = new String(bytes, 0, count, charset);
/*  780 */       if (pos + 2 < eword.length()) {
/*      */         
/*  782 */         String rest = eword.substring(pos + 2);
/*  783 */         if (!decodeStrict)
/*  784 */           rest = decodeInnerWords(rest); 
/*  785 */         s = s + rest;
/*      */       } 
/*  787 */       return s;
/*  788 */     } catch (UnsupportedEncodingException uex) {
/*      */ 
/*      */       
/*  791 */       throw uex;
/*  792 */     } catch (IOException ioex) {
/*      */       
/*  794 */       throw new ParseException();
/*  795 */     } catch (IllegalArgumentException iex) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  803 */       throw new UnsupportedEncodingException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String decodeInnerWords(String word) throws UnsupportedEncodingException {
/*  814 */     int start = 0;
/*  815 */     StringBuffer buf = new StringBuffer(); int i;
/*  816 */     while ((i = word.indexOf("=?", start)) >= 0) {
/*  817 */       buf.append(word.substring(start, i));
/*  818 */       int end = word.indexOf("?=", i);
/*  819 */       if (end < 0)
/*      */         break; 
/*  821 */       String s = word.substring(i, end + 2);
/*      */       try {
/*  823 */         s = decodeWord(s);
/*  824 */       } catch (ParseException parseException) {}
/*      */ 
/*      */       
/*  827 */       buf.append(s);
/*  828 */       start = end + 2;
/*      */     } 
/*  830 */     if (start == 0)
/*  831 */       return word; 
/*  832 */     if (start < word.length())
/*  833 */       buf.append(word.substring(start)); 
/*  834 */     return buf.toString();
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
/*      */   public static String quote(String word, String specials) {
/*  854 */     int len = word.length();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  860 */     boolean needQuoting = false;
/*  861 */     for (int i = 0; i < len; i++) {
/*  862 */       char c = word.charAt(i);
/*  863 */       if (c == '"' || c == '\\' || c == '\r' || c == '\n') {
/*      */         
/*  865 */         StringBuffer sb = new StringBuffer(len + 3);
/*  866 */         sb.append('"');
/*  867 */         sb.append(word.substring(0, i));
/*  868 */         int lastc = 0;
/*  869 */         for (int j = i; j < len; j++) {
/*  870 */           char cc = word.charAt(j);
/*  871 */           if (cc == '"' || cc == '\\' || cc == '\r' || cc == '\n')
/*      */           {
/*  873 */             if (cc != '\n' || lastc != 13)
/*      */             {
/*      */               
/*  876 */               sb.append('\\'); }  } 
/*  877 */           sb.append(cc);
/*  878 */           lastc = cc;
/*      */         } 
/*  880 */         sb.append('"');
/*  881 */         return sb.toString();
/*  882 */       }  if (c < ' ' || c >= '' || specials.indexOf(c) >= 0)
/*      */       {
/*  884 */         needQuoting = true;
/*      */       }
/*      */     } 
/*  887 */     if (needQuoting) {
/*  888 */       StringBuffer sb = new StringBuffer(len + 2);
/*  889 */       sb.append('"').append(word).append('"');
/*  890 */       return sb.toString();
/*      */     } 
/*  892 */     return word;
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
/*      */   static String fold(int used, String s) {
/*  911 */     if (!foldText) {
/*  912 */       return s;
/*      */     }
/*      */     
/*      */     int end;
/*      */     
/*  917 */     for (end = s.length() - 1; end >= 0; end--) {
/*  918 */       char c = s.charAt(end);
/*  919 */       if (c != ' ' && c != '\t')
/*      */         break; 
/*      */     } 
/*  922 */     if (end != s.length() - 1) {
/*  923 */       s = s.substring(0, end + 1);
/*      */     }
/*      */     
/*  926 */     if (used + s.length() <= 76) {
/*  927 */       return s;
/*      */     }
/*      */     
/*  930 */     StringBuffer sb = new StringBuffer(s.length() + 4);
/*  931 */     char lastc = Character.MIN_VALUE;
/*  932 */     while (used + s.length() > 76) {
/*  933 */       int lastspace = -1;
/*  934 */       for (int i = 0; i < s.length() && (
/*  935 */         lastspace == -1 || used + i <= 76); i++) {
/*      */         
/*  937 */         char c = s.charAt(i);
/*  938 */         if ((c == ' ' || c == '\t') && 
/*  939 */           lastc != ' ' && lastc != '\t')
/*  940 */           lastspace = i; 
/*  941 */         lastc = c;
/*      */       } 
/*  943 */       if (lastspace == -1) {
/*      */         
/*  945 */         sb.append(s);
/*  946 */         s = "";
/*  947 */         used = 0;
/*      */         break;
/*      */       } 
/*  950 */       sb.append(s.substring(0, lastspace));
/*  951 */       sb.append("\r\n");
/*  952 */       lastc = s.charAt(lastspace);
/*  953 */       sb.append(lastc);
/*  954 */       s = s.substring(lastspace + 1);
/*  955 */       used = 1;
/*      */     } 
/*  957 */     sb.append(s);
/*  958 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String unfold(String s) {
/*  969 */     if (!foldText) {
/*  970 */       return s;
/*      */     }
/*  972 */     StringBuffer sb = null;
/*      */     int i;
/*  974 */     while ((i = indexOfAny(s, "\r\n")) >= 0) {
/*  975 */       int start = i;
/*  976 */       int l = s.length();
/*  977 */       i++;
/*  978 */       if (i < l && s.charAt(i - 1) == '\r' && s.charAt(i) == '\n')
/*  979 */         i++; 
/*  980 */       if (start == 0 || s.charAt(start - 1) != '\\') {
/*      */         char c;
/*      */ 
/*      */         
/*  984 */         if (i < l && ((c = s.charAt(i)) == ' ' || c == '\t')) {
/*  985 */           i++;
/*  986 */           while (i < l && ((c = s.charAt(i)) == ' ' || c == '\t'))
/*  987 */             i++; 
/*  988 */           if (sb == null)
/*  989 */             sb = new StringBuffer(s.length()); 
/*  990 */           if (start != 0) {
/*  991 */             sb.append(s.substring(0, start));
/*  992 */             sb.append(' ');
/*      */           } 
/*  994 */           s = s.substring(i);
/*      */           
/*      */           continue;
/*      */         } 
/*  998 */         if (sb == null)
/*  999 */           sb = new StringBuffer(s.length()); 
/* 1000 */         sb.append(s.substring(0, i));
/* 1001 */         s = s.substring(i);
/*      */         
/*      */         continue;
/*      */       } 
/* 1005 */       if (sb == null)
/* 1006 */         sb = new StringBuffer(s.length()); 
/* 1007 */       sb.append(s.substring(0, start - 1));
/* 1008 */       sb.append(s.substring(start, i));
/* 1009 */       s = s.substring(i);
/*      */     } 
/*      */     
/* 1012 */     if (sb != null) {
/* 1013 */       sb.append(s);
/* 1014 */       return sb.toString();
/*      */     } 
/* 1016 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int indexOfAny(String s, String any) {
/* 1026 */     return indexOfAny(s, any, 0);
/*      */   }
/*      */   
/*      */   private static int indexOfAny(String s, String any, int start) {
/*      */     try {
/* 1031 */       int len = s.length();
/* 1032 */       for (int i = start; i < len; i++) {
/* 1033 */         if (any.indexOf(s.charAt(i)) >= 0)
/* 1034 */           return i; 
/*      */       } 
/* 1036 */       return -1;
/* 1037 */     } catch (StringIndexOutOfBoundsException e) {
/* 1038 */       return -1;
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
/*      */   public static String javaCharset(String charset) {
/* 1050 */     if (mime2java == null || charset == null)
/*      */     {
/* 1052 */       return charset;
/*      */     }
/* 1054 */     String alias = (String)mime2java.get(charset.toLowerCase());
/* 1055 */     return (alias == null) ? charset : alias;
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
/*      */   public static String mimeCharset(String charset) {
/* 1072 */     if (java2mime == null || charset == null)
/*      */     {
/* 1074 */       return charset;
/*      */     }
/* 1076 */     String alias = (String)java2mime.get(charset.toLowerCase());
/* 1077 */     return (alias == null) ? charset : alias;
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
/*      */   public static String getDefaultJavaCharset() {
/* 1094 */     if (defaultJavaCharset == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1099 */       String mimecs = null;
/*      */       
/* 1101 */       mimecs = SAAJUtil.getSystemProperty("mail.mime.charset");
/*      */       
/* 1103 */       if (mimecs != null && mimecs.length() > 0) {
/* 1104 */         defaultJavaCharset = javaCharset(mimecs);
/* 1105 */         return defaultJavaCharset;
/*      */       } 
/*      */       
/*      */       try {
/* 1109 */         defaultJavaCharset = System.getProperty("file.encoding", "8859_1");
/*      */       }
/* 1111 */       catch (SecurityException sex) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1118 */         InputStreamReader reader = new InputStreamReader(new NullInputStream());
/*      */         
/* 1120 */         defaultJavaCharset = reader.getEncoding();
/* 1121 */         if (defaultJavaCharset == null)
/* 1122 */           defaultJavaCharset = "8859_1"; 
/*      */       } 
/*      */     }  class NullInputStream extends InputStream { public int read() {
/*      */         return 0;
/* 1126 */       } }; return defaultJavaCharset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String getDefaultMIMECharset() {
/* 1133 */     if (defaultMIMECharset == null) {
/* 1134 */       defaultMIMECharset = SAAJUtil.getSystemProperty("mail.mime.charset");
/*      */     }
/* 1136 */     if (defaultMIMECharset == null)
/* 1137 */       defaultMIMECharset = mimeCharset(getDefaultJavaCharset()); 
/* 1138 */     return defaultMIMECharset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1147 */   private static Hashtable java2mime = new Hashtable<>(40); static final int ALL_ASCII = 1; static final int MOSTLY_ASCII = 2; static final int MOSTLY_NONASCII = 3; static {
/* 1148 */     mime2java = new Hashtable<>(10);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1154 */       InputStream is = MimeUtility.class.getResourceAsStream("/META-INF/javamail.charset.map");
/*      */ 
/*      */       
/* 1157 */       if (is != null) {
/* 1158 */         LineInputStream lineInputStream = new LineInputStream(is);
/*      */ 
/*      */         
/* 1161 */         loadMappings(lineInputStream, java2mime);
/*      */ 
/*      */         
/* 1164 */         loadMappings(lineInputStream, mime2java);
/*      */       } 
/* 1166 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1171 */     if (java2mime.isEmpty()) {
/* 1172 */       java2mime.put("8859_1", "ISO-8859-1");
/* 1173 */       java2mime.put("iso8859_1", "ISO-8859-1");
/* 1174 */       java2mime.put("ISO8859-1", "ISO-8859-1");
/*      */       
/* 1176 */       java2mime.put("8859_2", "ISO-8859-2");
/* 1177 */       java2mime.put("iso8859_2", "ISO-8859-2");
/* 1178 */       java2mime.put("ISO8859-2", "ISO-8859-2");
/*      */       
/* 1180 */       java2mime.put("8859_3", "ISO-8859-3");
/* 1181 */       java2mime.put("iso8859_3", "ISO-8859-3");
/* 1182 */       java2mime.put("ISO8859-3", "ISO-8859-3");
/*      */       
/* 1184 */       java2mime.put("8859_4", "ISO-8859-4");
/* 1185 */       java2mime.put("iso8859_4", "ISO-8859-4");
/* 1186 */       java2mime.put("ISO8859-4", "ISO-8859-4");
/*      */       
/* 1188 */       java2mime.put("8859_5", "ISO-8859-5");
/* 1189 */       java2mime.put("iso8859_5", "ISO-8859-5");
/* 1190 */       java2mime.put("ISO8859-5", "ISO-8859-5");
/*      */       
/* 1192 */       java2mime.put("8859_6", "ISO-8859-6");
/* 1193 */       java2mime.put("iso8859_6", "ISO-8859-6");
/* 1194 */       java2mime.put("ISO8859-6", "ISO-8859-6");
/*      */       
/* 1196 */       java2mime.put("8859_7", "ISO-8859-7");
/* 1197 */       java2mime.put("iso8859_7", "ISO-8859-7");
/* 1198 */       java2mime.put("ISO8859-7", "ISO-8859-7");
/*      */       
/* 1200 */       java2mime.put("8859_8", "ISO-8859-8");
/* 1201 */       java2mime.put("iso8859_8", "ISO-8859-8");
/* 1202 */       java2mime.put("ISO8859-8", "ISO-8859-8");
/*      */       
/* 1204 */       java2mime.put("8859_9", "ISO-8859-9");
/* 1205 */       java2mime.put("iso8859_9", "ISO-8859-9");
/* 1206 */       java2mime.put("ISO8859-9", "ISO-8859-9");
/*      */       
/* 1208 */       java2mime.put("SJIS", "Shift_JIS");
/* 1209 */       java2mime.put("MS932", "Shift_JIS");
/* 1210 */       java2mime.put("JIS", "ISO-2022-JP");
/* 1211 */       java2mime.put("ISO2022JP", "ISO-2022-JP");
/* 1212 */       java2mime.put("EUC_JP", "euc-jp");
/* 1213 */       java2mime.put("KOI8_R", "koi8-r");
/* 1214 */       java2mime.put("EUC_CN", "euc-cn");
/* 1215 */       java2mime.put("EUC_TW", "euc-tw");
/* 1216 */       java2mime.put("EUC_KR", "euc-kr");
/*      */     } 
/* 1218 */     if (mime2java.isEmpty()) {
/* 1219 */       mime2java.put("iso-2022-cn", "ISO2022CN");
/* 1220 */       mime2java.put("iso-2022-kr", "ISO2022KR");
/* 1221 */       mime2java.put("utf-8", "UTF8");
/* 1222 */       mime2java.put("utf8", "UTF8");
/* 1223 */       mime2java.put("ja_jp.iso2022-7", "ISO2022JP");
/* 1224 */       mime2java.put("ja_jp.eucjp", "EUCJIS");
/* 1225 */       mime2java.put("euc-kr", "KSC5601");
/* 1226 */       mime2java.put("euckr", "KSC5601");
/* 1227 */       mime2java.put("us-ascii", "ISO-8859-1");
/* 1228 */       mime2java.put("x-us-ascii", "ISO-8859-1");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadMappings(LineInputStream is, Hashtable<String, String> table) {
/*      */     while (true) {
/*      */       String currLine;
/*      */       try {
/* 1237 */         currLine = is.readLine();
/* 1238 */       } catch (IOException ioex) {
/*      */         break;
/*      */       } 
/*      */       
/* 1242 */       if (currLine == null)
/*      */         break; 
/* 1244 */       if (currLine.startsWith("--") && currLine.endsWith("--")) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/* 1249 */       if (currLine.trim().length() == 0 || currLine.startsWith("#")) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 1254 */       StringTokenizer tk = new StringTokenizer(currLine, " \t");
/*      */       try {
/* 1256 */         String key = tk.nextToken();
/* 1257 */         String value = tk.nextToken();
/* 1258 */         table.put(key.toLowerCase(), value);
/* 1259 */       } catch (NoSuchElementException noSuchElementException) {}
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
/*      */   static int checkAscii(String s) {
/* 1276 */     int ascii = 0, non_ascii = 0;
/* 1277 */     int l = s.length();
/*      */     
/* 1279 */     for (int i = 0; i < l; i++) {
/* 1280 */       if (nonascii(s.charAt(i))) {
/* 1281 */         non_ascii++;
/*      */       } else {
/* 1283 */         ascii++;
/*      */       } 
/*      */     } 
/* 1286 */     if (non_ascii == 0)
/* 1287 */       return 1; 
/* 1288 */     if (ascii > non_ascii) {
/* 1289 */       return 2;
/*      */     }
/* 1291 */     return 3;
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
/*      */   static int checkAscii(byte[] b) {
/* 1305 */     int ascii = 0, non_ascii = 0;
/*      */     
/* 1307 */     for (int i = 0; i < b.length; i++) {
/*      */ 
/*      */ 
/*      */       
/* 1311 */       if (nonascii(b[i] & 0xFF)) {
/* 1312 */         non_ascii++;
/*      */       } else {
/* 1314 */         ascii++;
/*      */       } 
/*      */     } 
/* 1317 */     if (non_ascii == 0)
/* 1318 */       return 1; 
/* 1319 */     if (ascii > non_ascii) {
/* 1320 */       return 2;
/*      */     }
/* 1322 */     return 3;
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
/*      */   static int checkAscii(InputStream is, int max, boolean breakOnNonAscii) {
/* 1347 */     int ascii = 0, non_ascii = 0;
/*      */     
/* 1349 */     int block = 4096;
/* 1350 */     int linelen = 0;
/* 1351 */     boolean longLine = false, badEOL = false;
/* 1352 */     boolean checkEOL = (encodeEolStrict && breakOnNonAscii);
/* 1353 */     byte[] buf = null;
/* 1354 */     if (max != 0) {
/* 1355 */       block = (max == -1) ? 4096 : Math.min(max, 4096);
/* 1356 */       buf = new byte[block];
/*      */     } 
/* 1358 */     while (max != 0) {
/*      */       int len; try {
/* 1360 */         if ((len = is.read(buf, 0, block)) == -1)
/*      */           break; 
/* 1362 */         int lastb = 0;
/* 1363 */         for (int i = 0; i < len; i++) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1368 */           int b = buf[i] & 0xFF;
/* 1369 */           if (checkEOL && ((lastb == 13 && b != 10) || (lastb != 13 && b == 10)))
/*      */           {
/*      */             
/* 1372 */             badEOL = true; } 
/* 1373 */           if (b == 13 || b == 10) {
/* 1374 */             linelen = 0;
/*      */           } else {
/* 1376 */             linelen++;
/* 1377 */             if (linelen > 998)
/* 1378 */               longLine = true; 
/*      */           } 
/* 1380 */           if (nonascii(b)) {
/* 1381 */             if (breakOnNonAscii) {
/* 1382 */               return 3;
/*      */             }
/* 1384 */             non_ascii++;
/*      */           } else {
/* 1386 */             ascii++;
/* 1387 */           }  lastb = b;
/*      */         } 
/* 1389 */       } catch (IOException ioex) {
/*      */         break;
/*      */       } 
/* 1392 */       if (max != -1) {
/* 1393 */         max -= len;
/*      */       }
/*      */     } 
/* 1396 */     if (max == 0 && breakOnNonAscii)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1403 */       return 3;
/*      */     }
/* 1405 */     if (non_ascii == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1410 */       if (badEOL) {
/* 1411 */         return 3;
/*      */       }
/* 1413 */       if (longLine) {
/* 1414 */         return 2;
/*      */       }
/* 1416 */       return 1;
/*      */     } 
/* 1418 */     if (ascii > non_ascii)
/* 1419 */       return 2; 
/* 1420 */     return 3;
/*      */   }
/*      */   
/*      */   static final boolean nonascii(int b) {
/* 1424 */     return (b >= 127 || (b < 32 && b != 13 && b != 10 && b != 9));
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/packaging/mime/internet/MimeUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */