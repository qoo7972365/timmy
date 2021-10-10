/*     */ package com.sun.org.apache.xml.internal.serializer;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*     */ import com.sun.org.apache.xml.internal.serializer.utils.WrappedRuntimeException;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Encodings
/*     */ {
/*     */   private static final int m_defaultLastPrintable = 127;
/*     */   private static final String ENCODINGS_FILE = "com/sun/org/apache/xml/internal/serializer/Encodings.properties";
/*     */   private static final String ENCODINGS_PROP = "com.sun.org.apache.xalan.internal.serialize.encodings";
/*     */   static final String DEFAULT_MIME_ENCODING = "UTF-8";
/*     */   
/*     */   static Writer getWriter(OutputStream output, String encoding) throws UnsupportedEncodingException {
/*  90 */     EncodingInfo ei = _encodingInfos.findEncoding(toUpperCaseFast(encoding));
/*  91 */     if (ei != null) {
/*     */       try {
/*  93 */         return new BufferedWriter(new OutputStreamWriter(output, ei.javaName));
/*     */       }
/*  95 */       catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 100 */     return new BufferedWriter(new OutputStreamWriter(output, encoding));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLastPrintable() {
/* 112 */     return 127;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static EncodingInfo getEncodingInfo(String encoding) {
/* 132 */     String normalizedEncoding = toUpperCaseFast(encoding);
/* 133 */     EncodingInfo ei = _encodingInfos.findEncoding(normalizedEncoding);
/* 134 */     if (ei == null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 144 */         Charset c = Charset.forName(encoding);
/* 145 */         String name = c.name();
/* 146 */         ei = new EncodingInfo(name, name);
/* 147 */         _encodingInfos.putEncoding(normalizedEncoding, ei);
/* 148 */       } catch (IllegalCharsetNameException|java.nio.charset.UnsupportedCharsetException x) {
/* 149 */         ei = new EncodingInfo(null, null);
/*     */       } 
/*     */     }
/*     */     
/* 153 */     return ei;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toUpperCaseFast(String s) {
/*     */     String upper;
/* 168 */     boolean different = false;
/* 169 */     int mx = s.length();
/* 170 */     char[] chars = new char[mx];
/* 171 */     for (int i = 0; i < mx; i++) {
/* 172 */       char ch = s.charAt(i);
/*     */       
/* 174 */       if ('a' <= ch && ch <= 'z') {
/*     */         
/* 176 */         ch = (char)(ch + -32);
/* 177 */         different = true;
/*     */       } 
/* 179 */       chars[i] = ch;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (different) {
/* 186 */       upper = String.valueOf(chars);
/*     */     } else {
/* 188 */       upper = s;
/*     */     } 
/* 190 */     return upper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getMimeEncoding(String encoding) {
/* 215 */     if (null == encoding) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 223 */         encoding = SecuritySupport.getSystemProperty("file.encoding", "UTF8");
/*     */         
/* 225 */         if (null != encoding)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 241 */           String jencoding = (encoding.equalsIgnoreCase("Cp1252") || encoding.equalsIgnoreCase("ISO8859_1") || encoding.equalsIgnoreCase("8859_1") || encoding.equalsIgnoreCase("UTF8")) ? "UTF-8" : convertJava2MimeEncoding(encoding);
/*     */           
/* 243 */           encoding = (null != jencoding) ? jencoding : "UTF-8";
/*     */         
/*     */         }
/*     */         else
/*     */         {
/* 248 */           encoding = "UTF-8";
/*     */         }
/*     */       
/* 251 */       } catch (SecurityException se) {
/*     */         
/* 253 */         encoding = "UTF-8";
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 258 */       encoding = convertJava2MimeEncoding(encoding);
/*     */     } 
/*     */     
/* 261 */     return encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String convertJava2MimeEncoding(String encoding) {
/* 274 */     EncodingInfo enc = _encodingInfos.getEncodingFromJavaKey(toUpperCaseFast(encoding));
/* 275 */     if (null != enc)
/* 276 */       return enc.name; 
/* 277 */     return encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertMime2JavaEncoding(String encoding) {
/* 289 */     EncodingInfo info = _encodingInfos.findEncoding(toUpperCaseFast(encoding));
/* 290 */     return (info != null) ? info.javaName : encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class EncodingInfos
/*     */   {
/* 298 */     private final Map<String, EncodingInfo> _encodingTableKeyJava = new HashMap<>();
/* 299 */     private final Map<String, EncodingInfo> _encodingTableKeyMime = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     private final Map<String, EncodingInfo> _encodingDynamicTable = Collections.synchronizedMap(new HashMap<>());
/*     */     
/*     */     private EncodingInfos() {
/* 308 */       loadEncodingInfo();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private InputStream openEncodingsFileStream() throws MalformedURLException, IOException {
/* 314 */       String urlString = null;
/* 315 */       InputStream is = null;
/*     */       
/*     */       try {
/* 318 */         urlString = SecuritySupport.getSystemProperty("com.sun.org.apache.xalan.internal.serialize.encodings", "");
/* 319 */       } catch (SecurityException securityException) {}
/*     */ 
/*     */       
/* 322 */       if (urlString != null && urlString.length() > 0) {
/* 323 */         URL url = new URL(urlString);
/* 324 */         is = url.openStream();
/*     */       } 
/*     */       
/* 327 */       if (is == null) {
/* 328 */         is = SecuritySupport.getResourceAsStream("com/sun/org/apache/xml/internal/serializer/Encodings.properties");
/*     */       }
/* 330 */       return is;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Properties loadProperties() throws MalformedURLException, IOException {
/* 337 */       Properties props = new Properties();
/* 338 */       try (InputStream is = openEncodingsFileStream()) {
/* 339 */         if (is != null) {
/* 340 */           props.load(is);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 350 */       return props;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String[] parseMimeTypes(String val) {
/* 357 */       int pos = val.indexOf(' ');
/*     */       
/* 359 */       if (pos < 0)
/*     */       {
/*     */ 
/*     */         
/* 363 */         return new String[] { val };
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 369 */       StringTokenizer st = new StringTokenizer(val.substring(0, pos), ",");
/* 370 */       String[] values = new String[st.countTokens()];
/* 371 */       for (int i = 0; st.hasMoreTokens(); i++) {
/* 372 */         values[i] = st.nextToken();
/*     */       }
/* 374 */       return values;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String findCharsetNameFor(String name) {
/*     */       try {
/* 386 */         return Charset.forName(name).name();
/* 387 */       } catch (Exception x) {
/* 388 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String findCharsetNameFor(String javaName, String[] mimes) {
/* 422 */       String cs = findCharsetNameFor(javaName);
/* 423 */       if (cs != null) return javaName; 
/* 424 */       for (String m : mimes) {
/* 425 */         cs = findCharsetNameFor(m);
/* 426 */         if (cs != null)
/*     */           break; 
/* 428 */       }  return cs;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void loadEncodingInfo() {
/*     */       try {
/* 441 */         Properties props = loadProperties();
/*     */ 
/*     */         
/* 444 */         Enumeration<Object> keys = props.keys();
/* 445 */         Map<String, EncodingInfo> canonicals = new HashMap<>();
/* 446 */         while (keys.hasMoreElements()) {
/* 447 */           String javaName = (String)keys.nextElement();
/* 448 */           String[] mimes = parseMimeTypes(props.getProperty(javaName));
/*     */           
/* 450 */           String charsetName = findCharsetNameFor(javaName, mimes);
/* 451 */           if (charsetName != null) {
/* 452 */             String kj = Encodings.toUpperCaseFast(javaName);
/* 453 */             String kc = Encodings.toUpperCaseFast(charsetName);
/* 454 */             for (int i = 0; i < mimes.length; i++) {
/* 455 */               String mimeName = mimes[i];
/* 456 */               String km = Encodings.toUpperCaseFast(mimeName);
/* 457 */               EncodingInfo info = new EncodingInfo(mimeName, charsetName);
/* 458 */               this._encodingTableKeyMime.put(km, info);
/* 459 */               if (!canonicals.containsKey(kc)) {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 464 */                 canonicals.put(kc, info);
/* 465 */                 this._encodingTableKeyJava.put(kc, info);
/*     */               } 
/* 467 */               this._encodingTableKeyJava.put(kj, info);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 480 */         for (Map.Entry<String, EncodingInfo> e : this._encodingTableKeyJava.entrySet()) {
/* 481 */           e.setValue(canonicals.get(Encodings.toUpperCaseFast(((EncodingInfo)e.getValue()).javaName)));
/*     */         }
/*     */       }
/* 484 */       catch (MalformedURLException mue) {
/* 485 */         throw new WrappedRuntimeException(mue);
/* 486 */       } catch (IOException ioe) {
/* 487 */         throw new WrappedRuntimeException(ioe);
/*     */       } 
/*     */     }
/*     */     
/*     */     EncodingInfo findEncoding(String normalizedEncoding) {
/* 492 */       EncodingInfo info = this._encodingTableKeyJava.get(normalizedEncoding);
/* 493 */       if (info == null) {
/* 494 */         info = this._encodingTableKeyMime.get(normalizedEncoding);
/*     */       }
/* 496 */       if (info == null) {
/* 497 */         info = this._encodingDynamicTable.get(normalizedEncoding);
/*     */       }
/* 499 */       return info;
/*     */     }
/*     */     
/*     */     EncodingInfo getEncodingFromMimeKey(String normalizedMimeName) {
/* 503 */       return this._encodingTableKeyMime.get(normalizedMimeName);
/*     */     }
/*     */     
/*     */     EncodingInfo getEncodingFromJavaKey(String normalizedJavaName) {
/* 507 */       return this._encodingTableKeyJava.get(normalizedJavaName);
/*     */     }
/*     */     
/*     */     void putEncoding(String key, EncodingInfo info) {
/* 511 */       this._encodingDynamicTable.put(key, info);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isHighUTF16Surrogate(char ch) {
/* 523 */     return ('?' <= ch && ch <= '?');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isLowUTF16Surrogate(char ch) {
/* 533 */     return ('?' <= ch && ch <= '?');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int toCodePoint(char highSurrogate, char lowSurrogate) {
/* 544 */     int codePoint = (highSurrogate - 55296 << 10) + lowSurrogate - 56320 + 65536;
/*     */ 
/*     */ 
/*     */     
/* 548 */     return codePoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int toCodePoint(char ch) {
/* 560 */     int codePoint = ch;
/* 561 */     return codePoint;
/*     */   }
/*     */   
/* 564 */   private static final EncodingInfos _encodingInfos = new EncodingInfos();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serializer/Encodings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */