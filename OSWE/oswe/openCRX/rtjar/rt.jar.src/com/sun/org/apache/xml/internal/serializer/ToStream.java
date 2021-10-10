/*      */ package com.sun.org.apache.xml.internal.serializer;
/*      */ 
/*      */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xml.internal.serializer.utils.Utils;
/*      */ import com.sun.org.apache.xml.internal.serializer.utils.WrappedRuntimeException;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.xml.transform.ErrorListener;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ToStream
/*      */   extends SerializerBase
/*      */ {
/*      */   private static final String COMMENT_BEGIN = "<!--";
/*      */   private static final String COMMENT_END = "-->";
/*   62 */   protected BoolStack m_disableOutputEscapingStates = new BoolStack();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   76 */   EncodingInfo m_encodingInfo = new EncodingInfo(null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Method m_canConvertMeth;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean m_triedToGetConverter = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   96 */   Object m_charToByteConverter = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   protected BoolStack m_preserves = new BoolStack();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean m_ispreserve = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean m_isprevtext = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  134 */   protected int m_maxCharacter = Encodings.getLastPrintable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  144 */   protected char[] m_lineSep = SecuritySupport.getSystemProperty("line.separator").toCharArray();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean m_lineSepUse = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   protected int m_lineSepLen = this.m_lineSep.length;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CharInfo m_charInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean m_shouldFlush = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean m_spaceBeforeClose = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean m_startNewLine;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean m_inDoctype = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean m_isUTF8 = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Properties m_format;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean m_cdataStartCalled = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean m_expandDTDEntities = true;
/*      */ 
/*      */ 
/*      */   
/*  203 */   private char m_highSurrogate = Character.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean m_escaping;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeCDATA() throws SAXException {
/*      */     try {
/*  221 */       this.m_writer.write("]]>");
/*      */       
/*  223 */       this.m_cdataTagOpen = false;
/*      */     }
/*  225 */     catch (IOException e) {
/*      */       
/*  227 */       throw new SAXException(e);
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
/*      */   public void serialize(Node node) throws IOException {
/*      */     try {
/*  243 */       TreeWalker walker = new TreeWalker(this);
/*      */ 
/*      */       
/*  246 */       walker.traverse(node);
/*      */     }
/*  248 */     catch (SAXException se) {
/*      */       
/*  250 */       throw new WrappedRuntimeException(se);
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
/*      */   static final boolean isUTF16Surrogate(char c) {
/*  263 */     return ((c & 0xFC00) == 55296);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ToStream() {
/*  269 */     this.m_escaping = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void flushWriter() throws SAXException {
/*  278 */     Writer writer = this.m_writer;
/*  279 */     if (null != writer) {
/*      */       
/*      */       try {
/*      */         
/*  283 */         if (writer instanceof WriterToUTF8Buffered)
/*      */         {
/*  285 */           if (this.m_shouldFlush) {
/*  286 */             ((WriterToUTF8Buffered)writer).flush();
/*      */           } else {
/*  288 */             ((WriterToUTF8Buffered)writer).flushBuffer();
/*      */           }  } 
/*  290 */         if (writer instanceof WriterToASCI)
/*      */         {
/*  292 */           if (this.m_shouldFlush) {
/*  293 */             writer.flush();
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else
/*      */         {
/*  300 */           writer.flush();
/*      */         }
/*      */       
/*  303 */       } catch (IOException ioe) {
/*      */         
/*  305 */         throw new SAXException(ioe);
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
/*      */   public OutputStream getOutputStream() {
/*  319 */     if (this.m_writer instanceof WriterToUTF8Buffered)
/*  320 */       return ((WriterToUTF8Buffered)this.m_writer).getOutputStream(); 
/*  321 */     if (this.m_writer instanceof WriterToASCI) {
/*  322 */       return ((WriterToASCI)this.m_writer).getOutputStream();
/*      */     }
/*  324 */     return null;
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
/*      */   public void elementDecl(String name, String model) throws SAXException {
/*  345 */     if (this.m_inExternalDTD) {
/*      */       return;
/*      */     }
/*      */     try {
/*  349 */       Writer writer = this.m_writer;
/*  350 */       DTDprolog();
/*      */       
/*  352 */       writer.write("<!ELEMENT ");
/*  353 */       writer.write(name);
/*  354 */       writer.write(32);
/*  355 */       writer.write(model);
/*  356 */       writer.write(62);
/*  357 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*      */     }
/*  359 */     catch (IOException e) {
/*      */       
/*  361 */       throw new SAXException(e);
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
/*      */   public void internalEntityDecl(String name, String value) throws SAXException {
/*  383 */     if (this.m_inExternalDTD) {
/*      */       return;
/*      */     }
/*      */     try {
/*  387 */       DTDprolog();
/*  388 */       outputEntityDecl(name, value);
/*      */     }
/*  390 */     catch (IOException e) {
/*      */       
/*  392 */       throw new SAXException(e);
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
/*      */   void outputEntityDecl(String name, String value) throws IOException {
/*  407 */     Writer writer = this.m_writer;
/*  408 */     writer.write("<!ENTITY ");
/*  409 */     writer.write(name);
/*  410 */     writer.write(" \"");
/*  411 */     writer.write(value);
/*  412 */     writer.write("\">");
/*  413 */     writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void outputLineSep() throws IOException {
/*  424 */     this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
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
/*      */   public void setOutputFormat(Properties format) {
/*  439 */     boolean shouldFlush = this.m_shouldFlush;
/*      */     
/*  441 */     init(this.m_writer, format, false, false);
/*      */     
/*  443 */     this.m_shouldFlush = shouldFlush;
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
/*      */   private synchronized void init(Writer writer, Properties format, boolean defaultProperties, boolean shouldFlush) {
/*  463 */     this.m_shouldFlush = shouldFlush;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  468 */     if (this.m_tracer != null && !(writer instanceof SerializerTraceWriter)) {
/*      */       
/*  470 */       this.m_writer = new SerializerTraceWriter(writer, this.m_tracer);
/*      */     } else {
/*  472 */       this.m_writer = writer;
/*      */     } 
/*      */     
/*  475 */     this.m_format = format;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  480 */     setCdataSectionElements("cdata-section-elements", format);
/*      */     
/*  482 */     setIndentAmount(
/*  483 */         OutputPropertyUtils.getIntProperty("{http://xml.apache.org/xalan}indent-amount", format));
/*      */ 
/*      */     
/*  486 */     setIndent(
/*  487 */         OutputPropertyUtils.getBooleanProperty("indent", format));
/*      */ 
/*      */ 
/*      */     
/*  491 */     String sep = format.getProperty("{http://xml.apache.org/xalan}line-separator");
/*  492 */     if (sep != null) {
/*  493 */       this.m_lineSep = sep.toCharArray();
/*  494 */       this.m_lineSepLen = sep.length();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  499 */     boolean shouldNotWriteXMLHeader = OutputPropertyUtils.getBooleanProperty("omit-xml-declaration", format);
/*      */ 
/*      */     
/*  502 */     setOmitXMLDeclaration(shouldNotWriteXMLHeader);
/*  503 */     setDoctypeSystem(format.getProperty("doctype-system"));
/*  504 */     String doctypePublic = format.getProperty("doctype-public");
/*  505 */     setDoctypePublic(doctypePublic);
/*      */ 
/*      */     
/*  508 */     if (format.get("standalone") != null) {
/*      */       
/*  510 */       String val = format.getProperty("standalone");
/*  511 */       if (defaultProperties) {
/*  512 */         setStandaloneInternal(val);
/*      */       } else {
/*  514 */         setStandalone(val);
/*      */       } 
/*      */     } 
/*  517 */     setMediaType(format.getProperty("media-type"));
/*      */     
/*  519 */     if (null != doctypePublic)
/*      */     {
/*  521 */       if (doctypePublic.startsWith("-//W3C//DTD XHTML")) {
/*  522 */         this.m_spaceBeforeClose = true;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  528 */     String version = getVersion();
/*  529 */     if (null == version) {
/*      */       
/*  531 */       version = format.getProperty("version");
/*  532 */       setVersion(version);
/*      */     } 
/*      */ 
/*      */     
/*  536 */     String encoding = getEncoding();
/*  537 */     if (null == encoding) {
/*      */ 
/*      */       
/*  540 */       encoding = Encodings.getMimeEncoding(format
/*  541 */           .getProperty("encoding"));
/*  542 */       setEncoding(encoding);
/*      */     } 
/*      */     
/*  545 */     this.m_isUTF8 = encoding.equals("UTF-8");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  550 */     String entitiesFileName = (String)format.get("{http://xml.apache.org/xalan}entities");
/*      */     
/*  552 */     if (null != entitiesFileName) {
/*      */ 
/*      */ 
/*      */       
/*  556 */       String method = (String)format.get("method");
/*      */       
/*  558 */       this.m_charInfo = CharInfo.getCharInfo(entitiesFileName, method);
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
/*      */   private synchronized void init(Writer writer, Properties format) {
/*  572 */     init(writer, format, false, false);
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
/*      */   protected synchronized void init(OutputStream output, Properties format, boolean defaultProperties) throws UnsupportedEncodingException {
/*  593 */     String encoding = getEncoding();
/*  594 */     if (encoding == null) {
/*      */ 
/*      */ 
/*      */       
/*  598 */       encoding = Encodings.getMimeEncoding(format
/*  599 */           .getProperty("encoding"));
/*  600 */       setEncoding(encoding);
/*      */     } 
/*      */     
/*  603 */     if (encoding.equalsIgnoreCase("UTF-8")) {
/*      */       
/*  605 */       this.m_isUTF8 = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  622 */       init(new WriterToUTF8Buffered(output), format, defaultProperties, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  630 */     else if (encoding
/*  631 */       .equals("WINDOWS-1250") || encoding
/*  632 */       .equals("US-ASCII") || encoding
/*  633 */       .equals("ASCII")) {
/*      */       
/*  635 */       init(new WriterToASCI(output), format, defaultProperties, true);
/*      */     } else {
/*      */       Writer osw;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  643 */         osw = Encodings.getWriter(output, encoding);
/*      */       }
/*  645 */       catch (UnsupportedEncodingException uee) {
/*      */         
/*  647 */         System.out.println("Warning: encoding \"" + encoding + "\" not supported, using " + "UTF-8");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  654 */         encoding = "UTF-8";
/*  655 */         setEncoding(encoding);
/*  656 */         osw = Encodings.getWriter(output, encoding);
/*      */       } 
/*      */       
/*  659 */       init(osw, format, defaultProperties, true);
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
/*      */   public Properties getOutputFormat() {
/*  671 */     return this.m_format;
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
/*      */   public void setWriter(Writer writer) {
/*  685 */     if (this.m_tracer != null && !(writer instanceof SerializerTraceWriter)) {
/*      */       
/*  687 */       this.m_writer = new SerializerTraceWriter(writer, this.m_tracer);
/*      */     } else {
/*  689 */       this.m_writer = writer;
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
/*      */   public boolean setLineSepUse(boolean use_sytem_line_break) {
/*  706 */     boolean oldValue = this.m_lineSepUse;
/*  707 */     this.m_lineSepUse = use_sytem_line_break;
/*  708 */     return oldValue;
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
/*      */   public void setOutputStream(OutputStream output) {
/*      */     try {
/*      */       Properties format;
/*  728 */       if (null == this.m_format) {
/*      */         
/*  730 */         format = OutputPropertiesFactory.getDefaultMethodProperties("xml");
/*      */       } else {
/*      */         
/*  733 */         format = this.m_format;
/*  734 */       }  init(output, format, true);
/*      */     }
/*  736 */     catch (UnsupportedEncodingException unsupportedEncodingException) {}
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
/*      */   public boolean setEscaping(boolean escape) {
/*  748 */     boolean temp = this.m_escaping;
/*  749 */     this.m_escaping = escape;
/*  750 */     return temp;
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
/*      */   protected void indent(int depth) throws IOException {
/*  766 */     if (this.m_startNewLine) {
/*  767 */       outputLineSep();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  772 */     if (this.m_indentAmount > 0) {
/*  773 */       printSpace(depth * this.m_indentAmount);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void indent() throws IOException {
/*  783 */     indent(this.m_elemContext.m_currentElemDepth);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printSpace(int n) throws IOException {
/*  793 */     Writer writer = this.m_writer;
/*  794 */     for (int i = 0; i < n; i++)
/*      */     {
/*  796 */       writer.write(32);
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
/*      */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value) throws SAXException {
/*  829 */     if (this.m_inExternalDTD) {
/*      */       return;
/*      */     }
/*      */     try {
/*  833 */       Writer writer = this.m_writer;
/*  834 */       DTDprolog();
/*      */       
/*  836 */       writer.write("<!ATTLIST ");
/*  837 */       writer.write(eName);
/*  838 */       writer.write(32);
/*      */       
/*  840 */       writer.write(aName);
/*  841 */       writer.write(32);
/*  842 */       writer.write(type);
/*  843 */       if (valueDefault != null) {
/*      */         
/*  845 */         writer.write(32);
/*  846 */         writer.write(valueDefault);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  851 */       writer.write(62);
/*  852 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*      */     }
/*  854 */     catch (IOException e) {
/*      */       
/*  856 */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer getWriter() {
/*  867 */     return this.m_writer;
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
/*      */   public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {
/*      */     try {
/*  892 */       DTDprolog();
/*      */       
/*  894 */       this.m_writer.write("<!ENTITY ");
/*  895 */       this.m_writer.write(name);
/*  896 */       if (publicId != null) {
/*  897 */         this.m_writer.write(" PUBLIC \"");
/*  898 */         this.m_writer.write(publicId);
/*      */       }
/*      */       else {
/*      */         
/*  902 */         this.m_writer.write(" SYSTEM \"");
/*  903 */         this.m_writer.write(systemId);
/*      */       } 
/*  905 */       this.m_writer.write("\" >");
/*  906 */       this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  907 */     } catch (IOException e) {
/*      */       
/*  909 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean escapingNotNeeded(char ch) {
/*      */     boolean ret;
/*  920 */     if (ch < '') {
/*      */ 
/*      */ 
/*      */       
/*  924 */       if (ch >= ' ' || '\n' == ch || '\r' == ch || '\t' == ch) {
/*  925 */         ret = true;
/*      */       } else {
/*  927 */         ret = false;
/*      */       } 
/*      */     } else {
/*  930 */       ret = this.m_encodingInfo.isInEncoding(ch);
/*      */     } 
/*  932 */     return ret;
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
/*      */   protected int writeUTF16Surrogate(char c, char[] ch, int i, int end) throws IOException, SAXException {
/*      */     char high, low;
/*  962 */     int status = -1;
/*  963 */     if (i + 1 >= end) {
/*      */       
/*  965 */       this.m_highSurrogate = c;
/*  966 */       return status;
/*      */     } 
/*      */ 
/*      */     
/*  970 */     if (this.m_highSurrogate == '\000') {
/*  971 */       high = c;
/*  972 */       low = ch[i + 1];
/*  973 */       status = 0;
/*      */     } else {
/*  975 */       high = this.m_highSurrogate;
/*  976 */       low = c;
/*  977 */       this.m_highSurrogate = Character.MIN_VALUE;
/*      */     } 
/*      */     
/*  980 */     if (!Encodings.isLowUTF16Surrogate(low)) {
/*  981 */       throwIOE(high, low);
/*      */     }
/*      */     
/*  984 */     Writer writer = this.m_writer;
/*      */ 
/*      */     
/*  987 */     if (this.m_encodingInfo.isInEncoding(high, low)) {
/*      */ 
/*      */       
/*  990 */       writer.write(new char[] { high, low }, 0, 2);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  996 */       String encoding = getEncoding();
/*  997 */       if (encoding != null) {
/*  998 */         status = writeCharRef(writer, high, low);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1003 */         writer.write(new char[] { high, low }, 0, 2);
/*      */       } 
/*      */     } 
/*      */     
/* 1007 */     return status;
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
/*      */   protected int accumDefaultEntity(Writer writer, char ch, int i, char[] chars, int len, boolean fromTextNode, boolean escLF) throws IOException {
/* 1037 */     if (!escLF && '\n' == ch) {
/*      */       
/* 1039 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1045 */     else if ((fromTextNode && this.m_charInfo.isSpecialTextChar(ch)) || (!fromTextNode && this.m_charInfo.isSpecialAttrChar(ch))) {
/*      */       
/* 1047 */       String outputStringForChar = this.m_charInfo.getOutputStringForChar(ch);
/*      */       
/* 1049 */       if (null != outputStringForChar) {
/*      */         
/* 1051 */         writer.write(outputStringForChar);
/*      */       } else {
/*      */         
/* 1054 */         return i;
/*      */       } 
/*      */     } else {
/* 1057 */       return i;
/*      */     } 
/*      */     
/* 1060 */     return i + 1;
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
/*      */   void writeNormalizedChars(char[] ch, int start, int length, boolean isCData, boolean useSystemLineSeparator) throws IOException, SAXException {
/* 1084 */     Writer writer = this.m_writer;
/* 1085 */     int end = start + length;
/*      */     
/* 1087 */     for (int i = start; i < end; i++) {
/*      */       
/* 1089 */       char c = ch[i];
/*      */       
/* 1091 */       if ('\n' == c && useSystemLineSeparator) {
/*      */         
/* 1093 */         writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*      */       }
/* 1095 */       else if (isCData && !escapingNotNeeded(c)) {
/*      */         
/* 1097 */         i = handleEscaping(writer, c, ch, i, end);
/*      */       }
/* 1099 */       else if (isCData && i < end - 2 && ']' == c && ']' == ch[i + 1] && '>' == ch[i + 2]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1106 */         writer.write("]]]]><![CDATA[>");
/*      */         
/* 1108 */         i += 2;
/*      */ 
/*      */       
/*      */       }
/* 1112 */       else if (escapingNotNeeded(c)) {
/*      */         
/* 1114 */         if (isCData && !this.m_cdataTagOpen) {
/*      */           
/* 1116 */           writer.write("<![CDATA[");
/* 1117 */           this.m_cdataTagOpen = true;
/*      */         } 
/* 1119 */         writer.write(c);
/*      */       } else {
/*      */         
/* 1122 */         i = handleEscaping(writer, c, ch, i, end);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handleEscaping(Writer writer, char c, char[] ch, int i, int end) throws IOException, SAXException {
/* 1144 */     if (Encodings.isHighUTF16Surrogate(c) || Encodings.isLowUTF16Surrogate(c)) {
/*      */       
/* 1146 */       if (writeUTF16Surrogate(c, ch, i, end) >= 0)
/*      */       {
/*      */         
/* 1149 */         if (Encodings.isHighUTF16Surrogate(c)) {
/* 1150 */           i++;
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1156 */       writeCharRef(writer, c);
/*      */     } 
/* 1158 */     return i;
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
/*      */   public void endNonEscaping() throws SAXException {
/* 1170 */     this.m_disableOutputEscapingStates.pop();
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
/*      */   public void startNonEscaping() throws SAXException {
/* 1185 */     this.m_disableOutputEscapingStates.push(true);
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
/*      */   protected void cdata(char[] ch, int start, int length) throws SAXException {
/*      */     try {
/* 1221 */       int old_start = start;
/* 1222 */       if (this.m_elemContext.m_startTagOpen) {
/*      */         
/* 1224 */         closeStartTag();
/* 1225 */         this.m_elemContext.m_startTagOpen = false;
/*      */       } 
/* 1227 */       this.m_ispreserve = true;
/*      */       
/* 1229 */       if (!this.m_cdataTagOpen && shouldIndent()) {
/* 1230 */         indent();
/*      */       }
/*      */       
/* 1233 */       boolean writeCDataBrackets = (length >= 1 && escapingNotNeeded(ch[start]));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1239 */       if (writeCDataBrackets && !this.m_cdataTagOpen) {
/*      */         
/* 1241 */         this.m_writer.write("<![CDATA[");
/* 1242 */         this.m_cdataTagOpen = true;
/*      */       } 
/*      */ 
/*      */       
/* 1246 */       if (isEscapingDisabled()) {
/*      */         
/* 1248 */         charactersRaw(ch, start, length);
/*      */       } else {
/*      */         
/* 1251 */         writeNormalizedChars(ch, start, length, true, this.m_lineSepUse);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1257 */       if (writeCDataBrackets)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1264 */         if (ch[start + length - 1] == ']') {
/* 1265 */           closeCDATA();
/*      */         }
/*      */       }
/*      */       
/* 1269 */       if (this.m_tracer != null) {
/* 1270 */         fireCDATAEvent(ch, old_start, length);
/*      */       }
/* 1272 */     } catch (IOException ioe) {
/*      */       
/* 1274 */       throw new SAXException(Utils.messages
/* 1275 */           .createMessage("ER_OIERROR", null), ioe);
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
/*      */   private boolean isEscapingDisabled() {
/* 1290 */     return this.m_disableOutputEscapingStates.peekOrFalse();
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
/*      */   protected void charactersRaw(char[] ch, int start, int length) throws SAXException {
/* 1307 */     if (this.m_inEntityRef) {
/*      */       return;
/*      */     }
/*      */     try {
/* 1311 */       if (this.m_elemContext.m_startTagOpen) {
/*      */         
/* 1313 */         closeStartTag();
/* 1314 */         this.m_elemContext.m_startTagOpen = false;
/*      */       } 
/*      */       
/* 1317 */       this.m_ispreserve = true;
/*      */       
/* 1319 */       this.m_writer.write(ch, start, length);
/*      */     }
/* 1321 */     catch (IOException e) {
/*      */       
/* 1323 */       throw new SAXException(e);
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
/*      */   public void characters(char[] chars, int start, int length) throws SAXException {
/* 1362 */     if (length == 0 || (this.m_inEntityRef && !this.m_expandDTDEntities))
/*      */       return; 
/* 1364 */     if (this.m_elemContext.m_startTagOpen) {
/*      */       
/* 1366 */       closeStartTag();
/* 1367 */       this.m_elemContext.m_startTagOpen = false;
/*      */     }
/* 1369 */     else if (this.m_needToCallStartDocument) {
/*      */       
/* 1371 */       startDocumentInternal();
/*      */     } 
/*      */     
/* 1374 */     if (this.m_cdataStartCalled || this.m_elemContext.m_isCdataSection) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1379 */       cdata(chars, start, length);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1384 */     if (this.m_cdataTagOpen) {
/* 1385 */       closeCDATA();
/*      */     }
/*      */     
/* 1388 */     if (this.m_disableOutputEscapingStates.peekOrFalse() || !this.m_escaping) {
/*      */       
/* 1390 */       charactersRaw(chars, start, length);
/*      */ 
/*      */       
/* 1393 */       if (this.m_tracer != null) {
/* 1394 */         fireCharEvent(chars, start, length);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/* 1399 */     if (this.m_elemContext.m_startTagOpen) {
/*      */       
/* 1401 */       closeStartTag();
/* 1402 */       this.m_elemContext.m_startTagOpen = false;
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
/*      */     try {
/* 1415 */       int end = start + length;
/* 1416 */       int lastDirty = start - 1;
/* 1417 */       int i = start; char ch1;
/* 1418 */       for (; i < end && ((ch1 = chars[i]) == ' ' || (ch1 == '\n' && this.m_lineSepUse) || ch1 == '\r' || ch1 == '\t'); 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1423 */         i++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1430 */         if (!this.m_charInfo.isTextASCIIClean(ch1)) {
/*      */           
/* 1432 */           lastDirty = processDirty(chars, end, i, ch1, lastDirty, true);
/* 1433 */           i = lastDirty;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1439 */       if (i < end) {
/* 1440 */         this.m_ispreserve = true;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1446 */       boolean isXML10 = "1.0".equals(getVersion());
/*      */       
/* 1448 */       for (; i < end; i++) {
/*      */         char ch2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1455 */         while (i < end && (ch2 = chars[i]) < '' && this.m_charInfo
/*      */           
/* 1457 */           .isTextASCIIClean(ch2))
/* 1458 */           i++; 
/* 1459 */         if (i == end) {
/*      */           break;
/*      */         }
/*      */         
/* 1463 */         char ch = chars[i];
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1468 */         if ((isCharacterInC0orC1Range(ch) || (!isXML10 && 
/* 1469 */           isNELorLSEPCharacter(ch)) || 
/* 1470 */           !escapingNotNeeded(ch) || this.m_charInfo.isSpecialTextChar(ch)) && '"' != ch) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1477 */           lastDirty = processDirty(chars, end, i, ch, lastDirty, true);
/* 1478 */           i = lastDirty;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1484 */       int startClean = lastDirty + 1;
/* 1485 */       if (i > startClean) {
/*      */         
/* 1487 */         int lengthClean = i - startClean;
/* 1488 */         this.m_writer.write(chars, startClean, lengthClean);
/*      */       } 
/*      */ 
/*      */       
/* 1492 */       this.m_isprevtext = true;
/*      */     }
/* 1494 */     catch (IOException e) {
/*      */       
/* 1496 */       throw new SAXException(e);
/*      */     } 
/*      */ 
/*      */     
/* 1500 */     if (this.m_tracer != null) {
/* 1501 */       fireCharEvent(chars, start, length);
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
/*      */   private static boolean isCharacterInC0orC1Range(char ch) {
/* 1515 */     if (ch == '\t' || ch == '\n' || ch == '\r') {
/* 1516 */       return false;
/*      */     }
/* 1518 */     return ((ch >= '' && ch <= '') || (ch >= '\001' && ch <= '\037'));
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
/*      */   private static boolean isNELorLSEPCharacter(char ch) {
/* 1530 */     return (ch == '' || ch == ' ');
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
/*      */   private int processDirty(char[] chars, int end, int i, char ch, int lastDirty, boolean fromTextNode) throws IOException, SAXException {
/* 1553 */     int startClean = lastDirty + 1;
/*      */ 
/*      */     
/* 1556 */     if (i > startClean) {
/*      */       
/* 1558 */       int lengthClean = i - startClean;
/* 1559 */       this.m_writer.write(chars, startClean, lengthClean);
/*      */     } 
/*      */ 
/*      */     
/* 1563 */     if ('\n' == ch && fromTextNode) {
/*      */       
/* 1565 */       this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1570 */       startClean = accumDefaultEscape(this.m_writer, ch, i, chars, end, fromTextNode, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1578 */       i = startClean - 1;
/*      */     } 
/*      */ 
/*      */     
/* 1582 */     return i;
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
/*      */   public void characters(String s) throws SAXException {
/* 1594 */     if (this.m_inEntityRef && !this.m_expandDTDEntities)
/*      */       return; 
/* 1596 */     int length = s.length();
/* 1597 */     if (length > this.m_charsBuff.length)
/*      */     {
/* 1599 */       this.m_charsBuff = new char[length * 2 + 1];
/*      */     }
/* 1601 */     s.getChars(0, length, this.m_charsBuff, 0);
/* 1602 */     characters(this.m_charsBuff, 0, length);
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
/*      */   protected int accumDefaultEscape(Writer writer, char ch, int i, char[] chars, int len, boolean fromTextNode, boolean escLF) throws IOException, SAXException {
/* 1633 */     int pos = accumDefaultEntity(writer, ch, i, chars, len, fromTextNode, escLF);
/*      */     
/* 1635 */     if (i == pos) {
/*      */       
/* 1637 */       if (this.m_highSurrogate != '\000') {
/* 1638 */         if (!Encodings.isLowUTF16Surrogate(ch)) {
/* 1639 */           throwIOE(this.m_highSurrogate, ch);
/*      */         }
/* 1641 */         writeCharRef(writer, this.m_highSurrogate, ch);
/* 1642 */         this.m_highSurrogate = Character.MIN_VALUE;
/* 1643 */         return ++pos;
/*      */       } 
/*      */       
/* 1646 */       if (Encodings.isHighUTF16Surrogate(ch)) {
/*      */         
/* 1648 */         if (i + 1 >= len)
/*      */         {
/*      */           
/* 1651 */           this.m_highSurrogate = ch;
/* 1652 */           pos++;
/*      */         
/*      */         }
/*      */         else
/*      */         {
/* 1657 */           char next = chars[++i];
/*      */           
/* 1659 */           if (!Encodings.isLowUTF16Surrogate(next)) {
/* 1660 */             throwIOE(ch, next);
/*      */           }
/* 1662 */           writeCharRef(writer, ch, next);
/* 1663 */           pos += 2;
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1673 */         if (isCharacterInC0orC1Range(ch) || ("1.1"
/* 1674 */           .equals(getVersion()) && isNELorLSEPCharacter(ch))) {
/*      */           
/* 1676 */           writeCharRef(writer, ch);
/*      */         }
/* 1678 */         else if ((!escapingNotNeeded(ch) || (fromTextNode && this.m_charInfo
/* 1679 */           .isSpecialTextChar(ch)) || (!fromTextNode && this.m_charInfo
/* 1680 */           .isSpecialAttrChar(ch))) && this.m_elemContext.m_currentElemDepth > 0) {
/*      */ 
/*      */           
/* 1683 */           writeCharRef(writer, ch);
/*      */         }
/*      */         else {
/*      */           
/* 1687 */           writer.write(ch);
/*      */         } 
/* 1689 */         pos++;
/*      */       } 
/*      */     } 
/*      */     
/* 1693 */     return pos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeCharRef(Writer writer, char c) throws IOException, SAXException {
/* 1703 */     if (this.m_cdataTagOpen)
/* 1704 */       closeCDATA(); 
/* 1705 */     writer.write("&#");
/* 1706 */     writer.write(Integer.toString(c));
/* 1707 */     writer.write(59);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int writeCharRef(Writer writer, char high, char low) throws IOException, SAXException {
/* 1718 */     if (this.m_cdataTagOpen) {
/* 1719 */       closeCDATA();
/*      */     }
/* 1721 */     int codePoint = Encodings.toCodePoint(high, low);
/* 1722 */     writer.write("&#");
/* 1723 */     writer.write(Integer.toString(codePoint));
/* 1724 */     writer.write(59);
/* 1725 */     return codePoint;
/*      */   }
/*      */   
/*      */   private void throwIOE(char ch, char next) throws IOException {
/* 1729 */     throw new IOException(Utils.messages.createMessage("ER_INVALID_UTF16_SURROGATE", new Object[] {
/*      */             
/* 1731 */             Integer.toHexString(ch) + " " + 
/* 1732 */             Integer.toHexString(next)
/*      */           }));
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
/*      */   public void startElement(String namespaceURI, String localName, String name, Attributes atts) throws SAXException {
/* 1764 */     if (this.m_inEntityRef) {
/*      */       return;
/*      */     }
/* 1767 */     if (this.m_needToCallStartDocument) {
/*      */       
/* 1769 */       startDocumentInternal();
/* 1770 */       this.m_needToCallStartDocument = false;
/*      */     }
/* 1772 */     else if (this.m_cdataTagOpen) {
/* 1773 */       closeCDATA();
/*      */     } 
/*      */     try {
/* 1776 */       if (true == this.m_needToOutputDocTypeDecl && null != 
/* 1777 */         getDoctypeSystem())
/*      */       {
/* 1779 */         outputDocTypeDecl(name, true);
/*      */       }
/*      */       
/* 1782 */       this.m_needToOutputDocTypeDecl = false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1787 */       if (this.m_elemContext.m_startTagOpen) {
/*      */         
/* 1789 */         closeStartTag();
/* 1790 */         this.m_elemContext.m_startTagOpen = false;
/*      */       } 
/*      */       
/* 1793 */       if (namespaceURI != null) {
/* 1794 */         ensurePrefixIsDeclared(namespaceURI, name);
/*      */       }
/* 1796 */       this.m_ispreserve = false;
/*      */       
/* 1798 */       if (shouldIndent() && this.m_startNewLine)
/*      */       {
/* 1800 */         indent();
/*      */       }
/*      */       
/* 1803 */       this.m_startNewLine = true;
/*      */       
/* 1805 */       Writer writer = this.m_writer;
/* 1806 */       writer.write(60);
/* 1807 */       writer.write(name);
/*      */     }
/* 1809 */     catch (IOException e) {
/*      */       
/* 1811 */       throw new SAXException(e);
/*      */     } 
/*      */ 
/*      */     
/* 1815 */     if (atts != null) {
/* 1816 */       addAttributes(atts);
/*      */     }
/* 1818 */     this.m_elemContext = this.m_elemContext.push(namespaceURI, localName, name);
/* 1819 */     this.m_isprevtext = false;
/*      */     
/* 1821 */     if (this.m_tracer != null) {
/* 1822 */       firePseudoAttributes();
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
/*      */   public void startElement(String elementNamespaceURI, String elementLocalName, String elementName) throws SAXException {
/* 1854 */     startElement(elementNamespaceURI, elementLocalName, elementName, (Attributes)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void startElement(String elementName) throws SAXException {
/* 1859 */     startElement((String)null, (String)null, elementName, (Attributes)null);
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
/*      */   void outputDocTypeDecl(String name, boolean closeDecl) throws SAXException {
/* 1872 */     if (this.m_cdataTagOpen) {
/* 1873 */       closeCDATA();
/*      */     }
/*      */     try {
/* 1876 */       Writer writer = this.m_writer;
/* 1877 */       writer.write("<!DOCTYPE ");
/* 1878 */       writer.write(name);
/*      */       
/* 1880 */       String doctypePublic = getDoctypePublic();
/* 1881 */       if (null != doctypePublic) {
/*      */         
/* 1883 */         writer.write(" PUBLIC \"");
/* 1884 */         writer.write(doctypePublic);
/* 1885 */         writer.write(34);
/*      */       } 
/*      */       
/* 1888 */       String doctypeSystem = getDoctypeSystem();
/* 1889 */       if (null != doctypeSystem) {
/*      */         
/* 1891 */         if (null == doctypePublic) {
/* 1892 */           writer.write(" SYSTEM \"");
/*      */         } else {
/* 1894 */           writer.write(" \"");
/*      */         } 
/* 1896 */         writer.write(doctypeSystem);
/*      */         
/* 1898 */         if (closeDecl) {
/*      */           
/* 1900 */           writer.write("\">");
/* 1901 */           writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/* 1902 */           closeDecl = false;
/*      */         } else {
/*      */           
/* 1905 */           writer.write(34);
/*      */         } 
/* 1907 */       }  boolean dothis = false;
/* 1908 */       if (dothis)
/*      */       {
/*      */ 
/*      */         
/* 1912 */         if (closeDecl)
/*      */         {
/* 1914 */           writer.write(62);
/* 1915 */           writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*      */         }
/*      */       
/*      */       }
/* 1919 */     } catch (IOException e) {
/*      */       
/* 1921 */       throw new SAXException(e);
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
/*      */   public void processAttributes(Writer writer, int nAttrs) throws IOException, SAXException {
/* 1944 */     String encoding = getEncoding();
/* 1945 */     for (int i = 0; i < nAttrs; i++) {
/*      */ 
/*      */       
/* 1948 */       String name = this.m_attributes.getQName(i);
/* 1949 */       String value = this.m_attributes.getValue(i);
/* 1950 */       writer.write(32);
/* 1951 */       writer.write(name);
/* 1952 */       writer.write("=\"");
/* 1953 */       writeAttrString(writer, value, encoding);
/* 1954 */       writer.write(34);
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
/*      */   public void writeAttrString(Writer writer, String string, String encoding) throws IOException, SAXException {
/* 1973 */     int len = string.length();
/* 1974 */     if (len > this.m_attrBuff.length)
/*      */     {
/* 1976 */       this.m_attrBuff = new char[len * 2 + 1];
/*      */     }
/* 1978 */     string.getChars(0, len, this.m_attrBuff, 0);
/* 1979 */     char[] stringChars = this.m_attrBuff;
/*      */     int i;
/* 1981 */     for (i = 0; i < len; ) {
/*      */       
/* 1983 */       char ch = stringChars[i];
/* 1984 */       if (escapingNotNeeded(ch) && !this.m_charInfo.isSpecialAttrChar(ch)) {
/*      */         
/* 1986 */         writer.write(ch);
/* 1987 */         i++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1999 */       i = accumDefaultEscape(writer, ch, i, stringChars, len, false, true);
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
/*      */   public void endElement(String namespaceURI, String localName, String name) throws SAXException {
/* 2025 */     if (this.m_inEntityRef) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2030 */     this.m_prefixMap.popNamespaces(this.m_elemContext.m_currentElemDepth, null);
/*      */ 
/*      */     
/*      */     try {
/* 2034 */       Writer writer = this.m_writer;
/* 2035 */       if (this.m_elemContext.m_startTagOpen)
/*      */       {
/* 2037 */         if (this.m_tracer != null)
/* 2038 */           fireStartElem(this.m_elemContext.m_elementName); 
/* 2039 */         int nAttrs = this.m_attributes.getLength();
/* 2040 */         if (nAttrs > 0) {
/*      */           
/* 2042 */           processAttributes(this.m_writer, nAttrs);
/*      */           
/* 2044 */           this.m_attributes.clear();
/*      */         } 
/* 2046 */         if (this.m_spaceBeforeClose) {
/* 2047 */           writer.write(" />");
/*      */         } else {
/* 2049 */           writer.write("/>");
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*      */         
/* 2058 */         if (this.m_cdataTagOpen) {
/* 2059 */           closeCDATA();
/*      */         }
/* 2061 */         if (shouldIndent())
/* 2062 */           indent(this.m_elemContext.m_currentElemDepth - 1); 
/* 2063 */         writer.write(60);
/* 2064 */         writer.write(47);
/* 2065 */         writer.write(name);
/* 2066 */         writer.write(62);
/*      */       }
/*      */     
/* 2069 */     } catch (IOException e) {
/*      */       
/* 2071 */       throw new SAXException(e);
/*      */     } 
/*      */     
/* 2074 */     if (!this.m_elemContext.m_startTagOpen && this.m_doIndent)
/*      */     {
/* 2076 */       this.m_ispreserve = this.m_preserves.isEmpty() ? false : this.m_preserves.pop();
/*      */     }
/*      */     
/* 2079 */     this.m_isprevtext = false;
/*      */ 
/*      */     
/* 2082 */     if (this.m_tracer != null)
/* 2083 */       fireEndElem(name); 
/* 2084 */     this.m_elemContext = this.m_elemContext.m_prev;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endElement(String name) throws SAXException {
/* 2095 */     endElement((String)null, (String)null, name);
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
/*      */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 2117 */     startPrefixMapping(prefix, uri, true);
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
/*      */   public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush) throws SAXException {
/*      */     int pushDepth;
/* 2156 */     if (shouldFlush) {
/*      */       
/* 2158 */       flushPending();
/*      */       
/* 2160 */       pushDepth = this.m_elemContext.m_currentElemDepth + 1;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 2165 */       pushDepth = this.m_elemContext.m_currentElemDepth;
/*      */     } 
/* 2167 */     boolean pushed = this.m_prefixMap.pushNamespace(prefix, uri, pushDepth);
/*      */     
/* 2169 */     if (pushed)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2177 */       if ("".equals(prefix)) {
/*      */         
/* 2179 */         String name = "xmlns";
/* 2180 */         addAttributeAlways("http://www.w3.org/2000/xmlns/", name, name, "CDATA", uri, false);
/*      */ 
/*      */       
/*      */       }
/* 2184 */       else if (!"".equals(uri)) {
/*      */ 
/*      */         
/* 2187 */         String name = "xmlns:" + prefix;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2193 */         addAttributeAlways("http://www.w3.org/2000/xmlns/", prefix, name, "CDATA", uri, false);
/*      */       } 
/*      */     }
/*      */     
/* 2197 */     return pushed;
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
/*      */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 2213 */     int start_old = start;
/* 2214 */     if (this.m_inEntityRef)
/*      */       return; 
/* 2216 */     if (this.m_elemContext.m_startTagOpen) {
/*      */       
/* 2218 */       closeStartTag();
/* 2219 */       this.m_elemContext.m_startTagOpen = false;
/*      */     }
/* 2221 */     else if (this.m_needToCallStartDocument) {
/*      */       
/* 2223 */       startDocumentInternal();
/* 2224 */       this.m_needToCallStartDocument = false;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 2229 */       if (shouldIndent() && this.m_isStandalone) {
/* 2230 */         indent();
/*      */       }
/* 2232 */       int limit = start + length;
/* 2233 */       boolean wasDash = false;
/* 2234 */       if (this.m_cdataTagOpen) {
/* 2235 */         closeCDATA();
/*      */       }
/* 2237 */       if (shouldIndent() && !this.m_isStandalone) {
/* 2238 */         indent();
/*      */       }
/* 2240 */       Writer writer = this.m_writer;
/* 2241 */       writer.write("<!--");
/*      */       
/* 2243 */       for (int i = start; i < limit; i++) {
/*      */         
/* 2245 */         if (wasDash && ch[i] == '-') {
/*      */           
/* 2247 */           writer.write(ch, start, i - start);
/* 2248 */           writer.write(" -");
/* 2249 */           start = i + 1;
/*      */         } 
/* 2251 */         wasDash = (ch[i] == '-');
/*      */       } 
/*      */ 
/*      */       
/* 2255 */       if (length > 0) {
/*      */ 
/*      */         
/* 2258 */         int remainingChars = limit - start;
/* 2259 */         if (remainingChars > 0) {
/* 2260 */           writer.write(ch, start, remainingChars);
/*      */         }
/* 2262 */         if (ch[limit - 1] == '-')
/* 2263 */           writer.write(32); 
/*      */       } 
/* 2265 */       writer.write("-->");
/*      */     }
/* 2267 */     catch (IOException e) {
/*      */       
/* 2269 */       throw new SAXException(e);
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
/* 2281 */     this.m_startNewLine = true;
/*      */     
/* 2283 */     if (this.m_tracer != null) {
/* 2284 */       fireCommentEvent(ch, start_old, length);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endCDATA() throws SAXException {
/* 2295 */     if (this.m_cdataTagOpen)
/* 2296 */       closeCDATA(); 
/* 2297 */     this.m_cdataStartCalled = false;
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
/*      */   public void endDTD() throws SAXException {
/*      */     try {
/* 2311 */       if (this.m_needToCallStartDocument) {
/*      */         return;
/*      */       }
/*      */       
/* 2315 */       if (this.m_needToOutputDocTypeDecl) {
/*      */         
/* 2317 */         outputDocTypeDecl(this.m_elemContext.m_elementName, false);
/* 2318 */         this.m_needToOutputDocTypeDecl = false;
/*      */       } 
/* 2320 */       Writer writer = this.m_writer;
/* 2321 */       if (!this.m_inDoctype) {
/* 2322 */         writer.write("]>");
/*      */       } else {
/*      */         
/* 2325 */         writer.write(62);
/*      */       } 
/*      */       
/* 2328 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*      */     }
/* 2330 */     catch (IOException e) {
/*      */       
/* 2332 */       throw new SAXException(e);
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
/*      */   public void endPrefixMapping(String prefix) throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 2367 */     if (0 == length)
/*      */       return; 
/* 2369 */     characters(ch, start, length);
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
/*      */   public void skippedEntity(String name) throws SAXException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startCDATA() throws SAXException {
/* 2395 */     this.m_cdataStartCalled = true;
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
/*      */   public void startEntity(String name) throws SAXException {
/* 2415 */     if (name.equals("[dtd]")) {
/* 2416 */       this.m_inExternalDTD = true;
/*      */     }
/* 2418 */     if (!this.m_expandDTDEntities && !this.m_inExternalDTD) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2423 */       startNonEscaping();
/* 2424 */       characters("&" + name + ';');
/* 2425 */       endNonEscaping();
/*      */     } 
/*      */     
/* 2428 */     this.m_inEntityRef = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeStartTag() throws SAXException {
/* 2439 */     if (this.m_elemContext.m_startTagOpen) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/* 2444 */         if (this.m_tracer != null)
/* 2445 */           fireStartElem(this.m_elemContext.m_elementName); 
/* 2446 */         int nAttrs = this.m_attributes.getLength();
/* 2447 */         if (nAttrs > 0) {
/*      */           
/* 2449 */           processAttributes(this.m_writer, nAttrs);
/*      */           
/* 2451 */           this.m_attributes.clear();
/*      */         } 
/* 2453 */         this.m_writer.write(62);
/*      */       }
/* 2455 */       catch (IOException e) {
/*      */         
/* 2457 */         throw new SAXException(e);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2464 */       if (this.m_cdataSectionElements != null) {
/* 2465 */         this.m_elemContext.m_isCdataSection = isCdataSection();
/*      */       }
/* 2467 */       if (this.m_doIndent) {
/*      */         
/* 2469 */         this.m_isprevtext = false;
/* 2470 */         this.m_preserves.push(this.m_ispreserve);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startDTD(String name, String publicId, String systemId) throws SAXException {
/* 2495 */     setDoctypeSystem(systemId);
/* 2496 */     setDoctypePublic(publicId);
/*      */     
/* 2498 */     this.m_elemContext.m_elementName = name;
/* 2499 */     this.m_inDoctype = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIndentAmount() {
/* 2508 */     return this.m_indentAmount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIndentAmount(int m_indentAmount) {
/* 2518 */     this.m_indentAmount = m_indentAmount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean shouldIndent() {
/* 2529 */     return (this.m_doIndent && !this.m_ispreserve && !this.m_isprevtext && (this.m_elemContext.m_currentElemDepth > 0 || this.m_isStandalone));
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
/*      */   private void setCdataSectionElements(String key, Properties props) {
/* 2551 */     String s = props.getProperty(key);
/*      */     
/* 2553 */     if (null != s) {
/*      */ 
/*      */       
/* 2556 */       Vector v = new Vector();
/* 2557 */       int l = s.length();
/* 2558 */       boolean inCurly = false;
/* 2559 */       StringBuffer buf = new StringBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2564 */       for (int i = 0; i < l; i++) {
/*      */         
/* 2566 */         char c = s.charAt(i);
/*      */         
/* 2568 */         if (Character.isWhitespace(c)) {
/*      */           
/* 2570 */           if (!inCurly) {
/*      */             
/* 2572 */             if (buf.length() > 0) {
/*      */               
/* 2574 */               addCdataSectionElement(buf.toString(), v);
/* 2575 */               buf.setLength(0);
/*      */             } 
/*      */             
/*      */             continue;
/*      */           } 
/* 2580 */         } else if ('{' == c) {
/* 2581 */           inCurly = true;
/* 2582 */         } else if ('}' == c) {
/* 2583 */           inCurly = false;
/*      */         } 
/* 2585 */         buf.append(c);
/*      */         continue;
/*      */       } 
/* 2588 */       if (buf.length() > 0) {
/*      */         
/* 2590 */         addCdataSectionElement(buf.toString(), v);
/* 2591 */         buf.setLength(0);
/*      */       } 
/*      */       
/* 2594 */       setCdataSectionElements(v);
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
/*      */   private void addCdataSectionElement(String URI_and_localName, Vector<String> v) {
/* 2609 */     StringTokenizer tokenizer = new StringTokenizer(URI_and_localName, "{}", false);
/*      */     
/* 2611 */     String s1 = tokenizer.nextToken();
/* 2612 */     String s2 = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
/*      */     
/* 2614 */     if (null == s2) {
/*      */ 
/*      */       
/* 2617 */       v.addElement(null);
/* 2618 */       v.addElement(s1);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 2623 */       v.addElement(s1);
/* 2624 */       v.addElement(s2);
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
/*      */   public void setCdataSectionElements(Vector URI_and_localNames) {
/* 2637 */     this.m_cdataSectionElements = URI_and_localNames;
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
/*      */   protected String ensureAttributesNamespaceIsDeclared(String ns, String localName, String rawName) throws SAXException {
/* 2656 */     if (ns != null && ns.length() > 0) {
/*      */ 
/*      */ 
/*      */       
/* 2660 */       int index = 0;
/*      */ 
/*      */ 
/*      */       
/* 2664 */       String prefixFromRawName = ((index = rawName.indexOf(":")) < 0) ? "" : rawName.substring(0, index);
/*      */       
/* 2666 */       if (index > 0) {
/*      */ 
/*      */         
/* 2669 */         String uri = this.m_prefixMap.lookupNamespace(prefixFromRawName);
/* 2670 */         if (uri != null && uri.equals(ns))
/*      */         {
/*      */ 
/*      */           
/* 2674 */           return null;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2680 */         startPrefixMapping(prefixFromRawName, ns, false);
/* 2681 */         addAttribute("http://www.w3.org/2000/xmlns/", prefixFromRawName, "xmlns:" + prefixFromRawName, "CDATA", ns, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2687 */         return prefixFromRawName;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2694 */       String prefix = this.m_prefixMap.lookupPrefix(ns);
/* 2695 */       if (prefix == null) {
/*      */ 
/*      */ 
/*      */         
/* 2699 */         prefix = this.m_prefixMap.generateNextPrefix();
/* 2700 */         startPrefixMapping(prefix, ns, false);
/* 2701 */         addAttribute("http://www.w3.org/2000/xmlns/", prefix, "xmlns:" + prefix, "CDATA", ns, false);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2709 */       return prefix;
/*      */     } 
/*      */ 
/*      */     
/* 2713 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void ensurePrefixIsDeclared(String ns, String rawName) throws SAXException {
/* 2720 */     if (ns != null && ns.length() > 0) {
/*      */       int index;
/*      */       
/* 2723 */       boolean no_prefix = ((index = rawName.indexOf(":")) < 0);
/* 2724 */       String prefix = no_prefix ? "" : rawName.substring(0, index);
/*      */       
/* 2726 */       if (null != prefix) {
/*      */         
/* 2728 */         String foundURI = this.m_prefixMap.lookupNamespace(prefix);
/*      */         
/* 2730 */         if (null == foundURI || !foundURI.equals(ns)) {
/*      */           
/* 2732 */           startPrefixMapping(prefix, ns);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2737 */           addAttributeAlways("http://www.w3.org/2000/xmlns/", no_prefix ? "xmlns" : prefix, no_prefix ? "xmlns" : ("xmlns:" + prefix), "CDATA", ns, false);
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
/*      */   
/*      */   public void flushPending() throws SAXException {
/* 2756 */     if (this.m_needToCallStartDocument) {
/*      */       
/* 2758 */       startDocumentInternal();
/* 2759 */       this.m_needToCallStartDocument = false;
/*      */     } 
/* 2761 */     if (this.m_elemContext.m_startTagOpen) {
/*      */       
/* 2763 */       closeStartTag();
/* 2764 */       this.m_elemContext.m_startTagOpen = false;
/*      */     } 
/*      */     
/* 2767 */     if (this.m_cdataTagOpen) {
/*      */       
/* 2769 */       closeCDATA();
/* 2770 */       this.m_cdataTagOpen = false;
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
/*      */   public void setContentHandler(ContentHandler ch) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAttributeAlways(String uri, String localName, String rawName, String type, String value, boolean xslAttribute) {
/*      */     boolean was_added;
/* 2811 */     int index = this.m_attributes.getIndex(rawName);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2816 */     if (index >= 0) {
/*      */       
/* 2818 */       String old_value = null;
/* 2819 */       if (this.m_tracer != null) {
/*      */         
/* 2821 */         old_value = this.m_attributes.getValue(index);
/* 2822 */         if (value.equals(old_value)) {
/* 2823 */           old_value = null;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2830 */       this.m_attributes.setValue(index, value);
/* 2831 */       was_added = false;
/* 2832 */       if (old_value != null) {
/* 2833 */         firePseudoAttributes();
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 2840 */       if (xslAttribute) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2855 */         int colonIndex = rawName.indexOf(':');
/* 2856 */         if (colonIndex > 0) {
/*      */           
/* 2858 */           String prefix = rawName.substring(0, colonIndex);
/* 2859 */           NamespaceMappings.MappingRecord existing_mapping = this.m_prefixMap.getMappingFromPrefix(prefix);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2864 */           if (existing_mapping != null && existing_mapping.m_declarationDepth == this.m_elemContext.m_currentElemDepth && 
/*      */             
/* 2866 */             !existing_mapping.m_uri.equals(uri)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2878 */             prefix = this.m_prefixMap.lookupPrefix(uri);
/* 2879 */             if (prefix == null)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2890 */               prefix = this.m_prefixMap.generateNextPrefix();
/*      */             }
/*      */             
/* 2893 */             rawName = prefix + ':' + localName;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 2905 */           String str = ensureAttributesNamespaceIsDeclared(uri, localName, rawName);
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 2910 */         catch (SAXException e) {
/*      */ 
/*      */           
/* 2913 */           e.printStackTrace();
/*      */         } 
/*      */       } 
/* 2916 */       this.m_attributes.addAttribute(uri, localName, rawName, type, value);
/* 2917 */       was_added = true;
/* 2918 */       if (this.m_tracer != null) {
/* 2919 */         firePseudoAttributes();
/*      */       }
/*      */     } 
/* 2922 */     return was_added;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void firePseudoAttributes() {
/* 2933 */     if (this.m_tracer != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 2938 */         this.m_writer.flush();
/*      */ 
/*      */         
/* 2941 */         StringBuffer sb = new StringBuffer();
/* 2942 */         int nAttrs = this.m_attributes.getLength();
/* 2943 */         if (nAttrs > 0) {
/*      */ 
/*      */ 
/*      */           
/* 2947 */           Writer writer = new WritertoStringBuffer(sb);
/*      */ 
/*      */           
/* 2950 */           processAttributes(writer, nAttrs);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2955 */         sb.append('>');
/*      */ 
/*      */ 
/*      */         
/* 2959 */         char[] ch = sb.toString().toCharArray();
/* 2960 */         this.m_tracer.fireGenerateEvent(11, ch, 0, ch.length);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 2966 */       catch (IOException iOException) {
/*      */ 
/*      */       
/*      */       }
/* 2970 */       catch (SAXException sAXException) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class WritertoStringBuffer
/*      */     extends Writer
/*      */   {
/*      */     private final StringBuffer m_stringbuf;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     WritertoStringBuffer(StringBuffer sb) {
/* 2991 */       this.m_stringbuf = sb;
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(char[] arg0, int arg1, int arg2) throws IOException {
/* 2996 */       this.m_stringbuf.append(arg0, arg1, arg2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void flush() throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(int i) {
/* 3013 */       this.m_stringbuf.append((char)i);
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(String s) {
/* 3018 */       this.m_stringbuf.append(s);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransformer(Transformer transformer) {
/* 3026 */     super.setTransformer(transformer);
/* 3027 */     if (this.m_tracer != null && !(this.m_writer instanceof SerializerTraceWriter))
/*      */     {
/* 3029 */       this.m_writer = new SerializerTraceWriter(this.m_writer, this.m_tracer);
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
/*      */   public boolean reset() {
/* 3042 */     boolean wasReset = false;
/* 3043 */     if (super.reset()) {
/*      */       
/* 3045 */       resetToStream();
/* 3046 */       wasReset = true;
/*      */     } 
/* 3048 */     return wasReset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetToStream() {
/* 3057 */     this.m_cdataStartCalled = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3066 */     this.m_disableOutputEscapingStates.clear();
/*      */     
/* 3068 */     this.m_escaping = true;
/*      */ 
/*      */     
/* 3071 */     this.m_inDoctype = false;
/* 3072 */     this.m_ispreserve = false;
/* 3073 */     this.m_ispreserve = false;
/* 3074 */     this.m_isprevtext = false;
/* 3075 */     this.m_isUTF8 = false;
/* 3076 */     this.m_preserves.clear();
/* 3077 */     this.m_shouldFlush = true;
/* 3078 */     this.m_spaceBeforeClose = false;
/* 3079 */     this.m_startNewLine = false;
/* 3080 */     this.m_lineSepUse = true;
/*      */ 
/*      */     
/* 3083 */     this.m_expandDTDEntities = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEncoding(String encoding) {
/* 3093 */     String old = getEncoding();
/* 3094 */     super.setEncoding(encoding);
/* 3095 */     if (old == null || !old.equals(encoding)) {
/*      */       
/* 3097 */       this.m_encodingInfo = Encodings.getEncodingInfo(encoding);
/*      */       
/* 3099 */       if (encoding != null && this.m_encodingInfo.name == null) {
/*      */ 
/*      */ 
/*      */         
/* 3103 */         String msg = Utils.messages.createMessage("ER_ENCODING_NOT_SUPPORTED", new Object[] { encoding });
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 3108 */           Transformer tran = getTransformer();
/* 3109 */           if (tran != null) {
/* 3110 */             ErrorListener errHandler = tran.getErrorListener();
/*      */             
/* 3112 */             if (null != errHandler && this.m_sourceLocator != null) {
/* 3113 */               errHandler.warning(new TransformerException(msg, this.m_sourceLocator));
/*      */             } else {
/* 3115 */               System.out.println(msg);
/*      */             } 
/*      */           } else {
/* 3118 */             System.out.println(msg);
/*      */           } 
/* 3120 */         } catch (Exception exception) {}
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
/*      */   static final class BoolStack
/*      */   {
/*      */     private boolean[] m_values;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int m_allocatedSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int m_index;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BoolStack() {
/* 3154 */       this(32);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BoolStack(int size) {
/* 3165 */       this.m_allocatedSize = size;
/* 3166 */       this.m_values = new boolean[size];
/* 3167 */       this.m_index = -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int size() {
/* 3177 */       return this.m_index + 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/* 3186 */       this.m_index = -1;
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
/*      */     public final boolean push(boolean val) {
/* 3199 */       if (this.m_index == this.m_allocatedSize - 1) {
/* 3200 */         grow();
/*      */       }
/* 3202 */       this.m_values[++this.m_index] = val; return val;
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
/*      */     public final boolean pop() {
/* 3214 */       return this.m_values[this.m_index--];
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
/*      */     public final boolean popAndTop() {
/* 3227 */       this.m_index--;
/*      */       
/* 3229 */       return (this.m_index >= 0) ? this.m_values[this.m_index] : false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void setTop(boolean b) {
/* 3240 */       this.m_values[this.m_index] = b;
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
/*      */     public final boolean peek() {
/* 3252 */       return this.m_values[this.m_index];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final boolean peekOrFalse() {
/* 3263 */       return (this.m_index > -1) ? this.m_values[this.m_index] : false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final boolean peekOrTrue() {
/* 3274 */       return (this.m_index > -1) ? this.m_values[this.m_index] : true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 3285 */       return (this.m_index == -1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void grow() {
/* 3295 */       this.m_allocatedSize *= 2;
/*      */       
/* 3297 */       boolean[] newVector = new boolean[this.m_allocatedSize];
/*      */       
/* 3299 */       System.arraycopy(this.m_values, 0, newVector, 0, this.m_index + 1);
/*      */       
/* 3301 */       this.m_values = newVector;
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
/*      */   public void notationDecl(String name, String pubID, String sysID) throws SAXException {
/*      */     try {
/* 3315 */       DTDprolog();
/*      */       
/* 3317 */       this.m_writer.write("<!NOTATION ");
/* 3318 */       this.m_writer.write(name);
/* 3319 */       if (pubID != null) {
/* 3320 */         this.m_writer.write(" PUBLIC \"");
/* 3321 */         this.m_writer.write(pubID);
/*      */       }
/*      */       else {
/*      */         
/* 3325 */         this.m_writer.write(" SYSTEM \"");
/* 3326 */         this.m_writer.write(sysID);
/*      */       } 
/* 3328 */       this.m_writer.write("\" >");
/* 3329 */       this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/* 3330 */     } catch (IOException e) {
/*      */       
/* 3332 */       e.printStackTrace();
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
/*      */   public void unparsedEntityDecl(String name, String pubID, String sysID, String notationName) throws SAXException {
/*      */     try {
/* 3345 */       DTDprolog();
/*      */       
/* 3347 */       this.m_writer.write("<!ENTITY ");
/* 3348 */       this.m_writer.write(name);
/* 3349 */       if (pubID != null) {
/* 3350 */         this.m_writer.write(" PUBLIC \"");
/* 3351 */         this.m_writer.write(pubID);
/*      */       }
/*      */       else {
/*      */         
/* 3355 */         this.m_writer.write(" SYSTEM \"");
/* 3356 */         this.m_writer.write(sysID);
/*      */       } 
/* 3358 */       this.m_writer.write("\" NDATA ");
/* 3359 */       this.m_writer.write(notationName);
/* 3360 */       this.m_writer.write(" >");
/* 3361 */       this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/* 3362 */     } catch (IOException e) {
/*      */       
/* 3364 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void DTDprolog() throws SAXException, IOException {
/* 3374 */     Writer writer = this.m_writer;
/* 3375 */     if (this.m_needToOutputDocTypeDecl) {
/*      */       
/* 3377 */       outputDocTypeDecl(this.m_elemContext.m_elementName, false);
/* 3378 */       this.m_needToOutputDocTypeDecl = false;
/*      */     } 
/* 3380 */     if (this.m_inDoctype) {
/*      */       
/* 3382 */       writer.write(" [");
/* 3383 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/* 3384 */       this.m_inDoctype = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDTDEntityExpansion(boolean expand) {
/* 3393 */     this.m_expandDTDEntities = expand;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serializer/ToStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */