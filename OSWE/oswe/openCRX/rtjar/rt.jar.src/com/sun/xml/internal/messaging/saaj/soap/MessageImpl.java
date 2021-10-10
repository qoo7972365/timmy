/*      */ package com.sun.xml.internal.messaging.saaj.soap;
/*      */ 
/*      */ import com.sun.xml.internal.messaging.saaj.SOAPExceptionImpl;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.BMMimeMultipart;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ContentType;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeBodyPart;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeMultipart;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimePullMultipart;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParameterList;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.ParseException;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.SharedInputStream;
/*      */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility;
/*      */ import com.sun.xml.internal.messaging.saaj.soap.impl.EnvelopeImpl;
/*      */ import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
/*      */ import com.sun.xml.internal.messaging.saaj.util.FastInfosetReflection;
/*      */ import com.sun.xml.internal.messaging.saaj.util.FinalArrayList;
/*      */ import com.sun.xml.internal.messaging.saaj.util.SAAJUtil;
/*      */ import com.sun.xml.internal.org.jvnet.mimepull.MIMEPart;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.activation.DataHandler;
/*      */ import javax.activation.DataSource;
/*      */ import javax.xml.soap.AttachmentPart;
/*      */ import javax.xml.soap.MimeHeaders;
/*      */ import javax.xml.soap.Node;
/*      */ import javax.xml.soap.SOAPBody;
/*      */ import javax.xml.soap.SOAPConstants;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import javax.xml.soap.SOAPHeader;
/*      */ import javax.xml.soap.SOAPMessage;
/*      */ import javax.xml.soap.SOAPPart;
/*      */ import javax.xml.transform.stream.StreamSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class MessageImpl
/*      */   extends SOAPMessage
/*      */   implements SOAPConstants
/*      */ {
/*      */   public static final String CONTENT_ID = "Content-ID";
/*      */   public static final String CONTENT_LOCATION = "Content-Location";
/*   68 */   protected static final Logger log = Logger.getLogger("com.sun.xml.internal.messaging.saaj.soap", "com.sun.xml.internal.messaging.saaj.soap.LocalStrings");
/*      */   
/*      */   protected static final int PLAIN_XML_FLAG = 1;
/*      */   
/*      */   protected static final int MIME_MULTIPART_FLAG = 2;
/*      */   
/*      */   protected static final int SOAP1_1_FLAG = 4;
/*      */   
/*      */   protected static final int SOAP1_2_FLAG = 8;
/*      */   protected static final int MIME_MULTIPART_XOP_SOAP1_1_FLAG = 6;
/*      */   protected static final int MIME_MULTIPART_XOP_SOAP1_2_FLAG = 10;
/*      */   protected static final int XOP_FLAG = 13;
/*      */   protected static final int FI_ENCODED_FLAG = 16;
/*      */   protected MimeHeaders headers;
/*      */   protected ContentType contentType;
/*      */   protected SOAPPartImpl soapPartImpl;
/*      */   protected FinalArrayList attachments;
/*      */   protected boolean saved = false;
/*      */   protected byte[] messageBytes;
/*      */   protected int messageByteCount;
/*   88 */   protected HashMap properties = new HashMap<>();
/*      */ 
/*      */   
/*   91 */   protected MimeMultipart multiPart = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean attachmentsInitialized = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isFastInfoset = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean acceptFastInfoset = false;
/*      */ 
/*      */   
/*  106 */   protected MimeMultipart mmp = null;
/*      */ 
/*      */   
/*      */   private boolean optimizeAttachmentProcessing = true;
/*      */   
/*  111 */   private InputStream inputStreamAfterSaveChanges = null;
/*      */   
/*      */   private static boolean switchOffBM = false;
/*      */   
/*      */   private static boolean switchOffLazyAttachment = false;
/*      */   private static boolean useMimePull = false;
/*      */   
/*      */   static {
/*  119 */     String s = SAAJUtil.getSystemProperty("saaj.mime.optimization");
/*  120 */     if (s != null && s.equals("false")) {
/*  121 */       switchOffBM = true;
/*      */     }
/*  123 */     s = SAAJUtil.getSystemProperty("saaj.lazy.mime.optimization");
/*  124 */     if (s != null && s.equals("false")) {
/*  125 */       switchOffLazyAttachment = true;
/*      */     }
/*  127 */     useMimePull = SAAJUtil.getSystemBoolean("saaj.use.mimepull");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  815 */     nullIter = Collections.EMPTY_LIST.iterator();
/*      */   }
/*      */   private boolean lazyAttachments = false; private static final Iterator nullIter; private static boolean isSoap1_1Type(String primary, String sub) { return ((primary.equalsIgnoreCase("text") && sub.equalsIgnoreCase("xml")) || (primary.equalsIgnoreCase("text") && sub.equalsIgnoreCase("xml-soap")) || (primary.equals("application") && sub.equals("fastinfoset"))); } private static boolean isEqualToSoap1_1Type(String type) { return (type.startsWith("text/xml") || type.startsWith("application/fastinfoset")); } private static boolean isSoap1_2Type(String primary, String sub) { return (primary.equals("application") && (sub.equals("soap+xml") || sub.equals("soap+fastinfoset"))); } private static boolean isEqualToSoap1_2Type(String type) { return (type.startsWith("application/soap+xml") || type.startsWith("application/soap+fastinfoset")); } protected MessageImpl() { this(false, false); this.attachmentsInitialized = true; } protected MessageImpl(boolean isFastInfoset, boolean acceptFastInfoset) { this.isFastInfoset = isFastInfoset; this.acceptFastInfoset = acceptFastInfoset; this.headers = new MimeHeaders(); this.headers.setHeader("Accept", getExpectedAcceptHeader()); this.contentType = new ContentType(); } protected MessageImpl(SOAPMessage msg) { if (!(msg instanceof MessageImpl)); MessageImpl src = (MessageImpl)msg; this.headers = src.headers; this.soapPartImpl = src.soapPartImpl; this.attachments = src.attachments; this.saved = src.saved; this.messageBytes = src.messageBytes; this.messageByteCount = src.messageByteCount; this.properties = src.properties; this.contentType = src.contentType; } protected static boolean isSoap1_1Content(int stat) { return ((stat & 0x4) != 0); } protected static boolean isSoap1_2Content(int stat) { return ((stat & 0x8) != 0); } private static boolean isMimeMultipartXOPSoap1_2Package(ContentType contentType) { String type = contentType.getParameter("type"); if (type == null) return false;  type = type.toLowerCase(); if (!type.startsWith("application/xop+xml")) return false;  String startinfo = contentType.getParameter("start-info"); if (startinfo == null) return false;  startinfo = startinfo.toLowerCase(); return isEqualToSoap1_2Type(startinfo); } private static boolean isMimeMultipartXOPSoap1_1Package(ContentType contentType) { String type = contentType.getParameter("type"); if (type == null) return false;  type = type.toLowerCase(); if (!type.startsWith("application/xop+xml")) return false;  String startinfo = contentType.getParameter("start-info"); if (startinfo == null) return false;  startinfo = startinfo.toLowerCase(); return isEqualToSoap1_1Type(startinfo); } private static boolean isSOAPBodyXOPPackage(ContentType contentType) { String primary = contentType.getPrimaryType(); String sub = contentType.getSubType(); if (primary.equalsIgnoreCase("application") && sub.equalsIgnoreCase("xop+xml")) { String type = getTypeParameter(contentType); return (isEqualToSoap1_2Type(type) || isEqualToSoap1_1Type(type)); }  return false; } protected MessageImpl(MimeHeaders headers, InputStream in) throws SOAPExceptionImpl { this.contentType = parseContentType(headers); init(headers, identifyContentType(this.contentType), this.contentType, in); } private static ContentType parseContentType(MimeHeaders headers) throws SOAPExceptionImpl { String ct; if (headers != null) { ct = getContentType(headers); } else { log.severe("SAAJ0550.soap.null.headers"); throw new SOAPExceptionImpl("Cannot create message: Headers can't be null"); }  if (ct == null) { log.severe("SAAJ0532.soap.no.Content-Type"); throw new SOAPExceptionImpl("Absent Content-Type"); }  try { return new ContentType(ct); } catch (Throwable ex) { log.severe("SAAJ0535.soap.cannot.internalize.message"); throw new SOAPExceptionImpl("Unable to internalize message", ex); }  } protected MessageImpl(MimeHeaders headers, ContentType contentType, int stat, InputStream in) throws SOAPExceptionImpl { init(headers, stat, contentType, in); } private void init(MimeHeaders headers, int stat, final ContentType contentType, final InputStream in) throws SOAPExceptionImpl { this.headers = headers; try { if ((stat & 0x10) > 0) this.isFastInfoset = this.acceptFastInfoset = true;  if (!this.isFastInfoset) { String[] values = headers.getHeader("Accept"); if (values != null) for (int i = 0; i < values.length; i++) { StringTokenizer st = new StringTokenizer(values[i], ","); while (st.hasMoreTokens()) { String token = st.nextToken().trim(); if (token.equalsIgnoreCase("application/fastinfoset") || token.equalsIgnoreCase("application/soap+fastinfoset")) { this.acceptFastInfoset = true; break; }  }  }   }  if (!isCorrectSoapVersion(stat)) { log.log(Level.SEVERE, "SAAJ0533.soap.incorrect.Content-Type", (Object[])new String[] { contentType.toString(), getExpectedContentType() }); throw new SOAPVersionMismatchException("Cannot create message: incorrect content-type for SOAP version. Got: " + contentType + " Expected: " + getExpectedContentType()); }  if ((stat & 0x1) != 0) { if (this.isFastInfoset) { getSOAPPart().setContent(FastInfosetReflection.FastInfosetSource_new(in)); } else { initCharsetProperty(contentType); getSOAPPart().setContent(new StreamSource(in)); }  } else if ((stat & 0x2) != 0) { DataSource ds = new DataSource() {
/*      */             public InputStream getInputStream() { return in; } public OutputStream getOutputStream() { return null; } public String getContentType() { return contentType.toString(); } public String getName() { return ""; }
/*  819 */           }; this.multiPart = null; if (useMimePull) { this.multiPart = (MimeMultipart)new MimePullMultipart(ds, contentType); } else if (switchOffBM) { this.multiPart = new MimeMultipart(ds, contentType); } else { this.multiPart = (MimeMultipart)new BMMimeMultipart(ds, contentType); }  String startParam = contentType.getParameter("start"); MimeBodyPart soapMessagePart = null; InputStream soapPartInputStream = null; String contentID = null; String contentIDNoAngle = null; if (switchOffBM || switchOffLazyAttachment) { if (startParam == null) { soapMessagePart = this.multiPart.getBodyPart(0); for (int i = 1; i < this.multiPart.getCount(); i++) initializeAttachment(this.multiPart, i);  } else { soapMessagePart = this.multiPart.getBodyPart(startParam); for (int i = 0; i < this.multiPart.getCount(); i++) { contentID = this.multiPart.getBodyPart(i).getContentID(); contentIDNoAngle = (contentID != null) ? contentID.replaceFirst("^<", "").replaceFirst(">$", "") : null; if (!startParam.equals(contentID) && !startParam.equals(contentIDNoAngle)) initializeAttachment(this.multiPart, i);  }  }  } else if (useMimePull) { MimePullMultipart mpMultipart = (MimePullMultipart)this.multiPart; MIMEPart sp = mpMultipart.readAndReturnSOAPPart(); soapMessagePart = new MimeBodyPart(sp); soapPartInputStream = sp.readOnce(); } else { BMMimeMultipart bmMultipart = (BMMimeMultipart)this.multiPart; InputStream stream = bmMultipart.initStream(); SharedInputStream sin = null; if (stream instanceof SharedInputStream) sin = (SharedInputStream)stream;  String boundary = "--" + contentType.getParameter("boundary"); byte[] bndbytes = ASCIIUtility.getBytes(boundary); if (startParam == null) { soapMessagePart = bmMultipart.getNextPart(stream, bndbytes, sin); bmMultipart.removeBodyPart(soapMessagePart); } else { MimeBodyPart bp = null; try { while (!startParam.equals(contentID) && !startParam.equals(contentIDNoAngle)) { bp = bmMultipart.getNextPart(stream, bndbytes, sin); contentID = bp.getContentID(); contentIDNoAngle = (contentID != null) ? contentID.replaceFirst("^<", "").replaceFirst(">$", "") : null; }  soapMessagePart = bp; bmMultipart.removeBodyPart(bp); } catch (Exception e) { throw new SOAPExceptionImpl(e); }  }  }  if (soapPartInputStream == null && soapMessagePart != null) soapPartInputStream = soapMessagePart.getInputStream();  ContentType soapPartCType = new ContentType(soapMessagePart.getContentType()); initCharsetProperty(soapPartCType); String baseType = soapPartCType.getBaseType().toLowerCase(); if (!isEqualToSoap1_1Type(baseType) && !isEqualToSoap1_2Type(baseType) && !isSOAPBodyXOPPackage(soapPartCType)) { log.log(Level.SEVERE, "SAAJ0549.soap.part.invalid.Content-Type", new Object[] { baseType }); throw new SOAPExceptionImpl("Bad Content-Type for SOAP Part : " + baseType); }  SOAPPart soapPart = getSOAPPart(); setMimeHeaders(soapPart, soapMessagePart); soapPart.setContent(this.isFastInfoset ? FastInfosetReflection.FastInfosetSource_new(soapPartInputStream) : new StreamSource(soapPartInputStream)); } else { log.severe("SAAJ0534.soap.unknown.Content-Type"); throw new SOAPExceptionImpl("Unrecognized Content-Type"); }  } catch (Throwable ex) { log.severe("SAAJ0535.soap.cannot.internalize.message"); throw new SOAPExceptionImpl("Unable to internalize message", ex); }  needsSave(); } public boolean isFastInfoset() { return this.isFastInfoset; } public boolean acceptFastInfoset() { return this.acceptFastInfoset; } public void setIsFastInfoset(boolean value) { if (value != this.isFastInfoset) { this.isFastInfoset = value; if (this.isFastInfoset) this.acceptFastInfoset = true;  this.saved = false; }  } public Object getProperty(String property) { return this.properties.get(property); } public Iterator getAttachments() { try { initializeAllAttachments(); }
/*  820 */     catch (Exception e)
/*  821 */     { throw new RuntimeException(e); }
/*      */     
/*  823 */     if (this.attachments == null)
/*  824 */       return nullIter; 
/*  825 */     return this.attachments.iterator(); }
/*      */   public void setProperty(String property, Object value) { verify(property, value); this.properties.put(property, value); }
/*      */   private void verify(String property, Object value) { if (property.equalsIgnoreCase("javax.xml.soap.write-xml-declaration")) { if (!"true".equals(value) && !"false".equals(value)) throw new RuntimeException(property + " must have value false or true");  try { EnvelopeImpl env = (EnvelopeImpl)getSOAPPart().getEnvelope(); if ("true".equalsIgnoreCase((String)value)) { env.setOmitXmlDecl("no"); } else if ("false".equalsIgnoreCase((String)value)) { env.setOmitXmlDecl("yes"); }  } catch (Exception e) { log.log(Level.SEVERE, "SAAJ0591.soap.exception.in.set.property", new Object[] { e.getMessage(), "javax.xml.soap.write-xml-declaration" }); throw new RuntimeException(e); }  return; }  if (property.equalsIgnoreCase("javax.xml.soap.character-set-encoding")) try { ((EnvelopeImpl)getSOAPPart().getEnvelope()).setCharsetEncoding((String)value); } catch (Exception e) { log.log(Level.SEVERE, "SAAJ0591.soap.exception.in.set.property", new Object[] { e.getMessage(), "javax.xml.soap.character-set-encoding" }); throw new RuntimeException(e); }   }
/*      */   static int identifyContentType(ContentType ct) throws SOAPExceptionImpl { String primary = ct.getPrimaryType().toLowerCase(); String sub = ct.getSubType().toLowerCase(); if (primary.equals("multipart")) { if (sub.equals("related")) { String type = getTypeParameter(ct); if (isEqualToSoap1_1Type(type)) return (type.equals("application/fastinfoset") ? 16 : 0) | 0x2 | 0x4;  if (isEqualToSoap1_2Type(type)) return (type.equals("application/soap+fastinfoset") ? 16 : 0) | 0x2 | 0x8;  if (isMimeMultipartXOPSoap1_1Package(ct)) return 6;  if (isMimeMultipartXOPSoap1_2Package(ct)) return 10;  log.severe("SAAJ0536.soap.content-type.mustbe.multipart"); throw new SOAPExceptionImpl("Content-Type needs to be Multipart/Related and with \"type=text/xml\" or \"type=application/soap+xml\""); }  log.severe("SAAJ0537.soap.invalid.content-type"); throw new SOAPExceptionImpl("Invalid Content-Type: " + primary + '/' + sub); }  if (isSoap1_1Type(primary, sub)) return ((primary.equalsIgnoreCase("application") && sub.equalsIgnoreCase("fastinfoset")) ? 16 : 0) | 0x1 | 0x4;  if (isSoap1_2Type(primary, sub)) return ((primary.equalsIgnoreCase("application") && sub.equalsIgnoreCase("soap+fastinfoset")) ? 16 : 0) | 0x1 | 0x8;  if (isSOAPBodyXOPPackage(ct)) return 13;  log.severe("SAAJ0537.soap.invalid.content-type"); throw new SOAPExceptionImpl("Invalid Content-Type:" + primary + '/' + sub + ". Is this an error message instead of a SOAP response?"); }
/*  829 */   private static String getTypeParameter(ContentType contentType) { String p = contentType.getParameter("type"); if (p != null) return p.toLowerCase();  return "text/xml"; } public MimeHeaders getMimeHeaders() { return this.headers; } static final String getContentType(MimeHeaders headers) { String[] values = headers.getHeader("Content-Type"); if (values == null) return null;  return values[0]; } public String getContentType() { return getContentType(this.headers); } public void setContentType(String type) { this.headers.setHeader("Content-Type", type); needsSave(); } private ContentType contentType() { ContentType ct = null; try { String currentContent = getContentType(); if (currentContent == null) return this.contentType;  ct = new ContentType(currentContent); } catch (Exception exception) {} return ct; } public String getBaseType() { return contentType().getBaseType(); } public void setBaseType(String type) { ContentType ct = contentType(); ct.setParameter("type", type); this.headers.setHeader("Content-Type", ct.toString()); needsSave(); } public String getAction() { return contentType().getParameter("action"); } public void setAction(String action) { ContentType ct = contentType(); ct.setParameter("action", action); this.headers.setHeader("Content-Type", ct.toString()); needsSave(); } public String getCharset() { return contentType().getParameter("charset"); } public void setCharset(String charset) { ContentType ct = contentType(); ct.setParameter("charset", charset); this.headers.setHeader("Content-Type", ct.toString()); needsSave(); } private final void needsSave() { this.saved = false; } public boolean saveRequired() { return (this.saved != true); } public String getContentDescription() { String[] values = this.headers.getHeader("Content-Description"); if (values != null && values.length > 0) return values[0];  return null; } public void setContentDescription(String description) { this.headers.setHeader("Content-Description", description); needsSave(); } public void removeAllAttachments() { try { initializeAllAttachments(); } catch (Exception e) { throw new RuntimeException(e); }  if (this.attachments != null) { this.attachments.clear(); needsSave(); }  } public int countAttachments() { try { initializeAllAttachments(); } catch (Exception e) { throw new RuntimeException(e); }  if (this.attachments != null) return this.attachments.size();  return 0; } public void addAttachmentPart(AttachmentPart attachment) { try { initializeAllAttachments(); this.optimizeAttachmentProcessing = true; } catch (Exception e) { throw new RuntimeException(e); }  if (this.attachments == null) this.attachments = new FinalArrayList();  this.attachments.add(attachment); needsSave(); } private void setFinalContentType(String charset) { ContentType ct = contentType();
/*  830 */     if (ct == null) {
/*  831 */       ct = new ContentType();
/*      */     }
/*  833 */     String[] split = getExpectedContentType().split("/");
/*  834 */     ct.setPrimaryType(split[0]);
/*  835 */     ct.setSubType(split[1]);
/*  836 */     ct.setParameter("charset", charset);
/*  837 */     this.headers.setHeader("Content-Type", ct.toString()); }
/*      */   
/*      */   private class MimeMatchingIterator implements Iterator { private Iterator iter; private MimeHeaders headers; private Object nextAttachment;
/*      */     
/*      */     public MimeMatchingIterator(MimeHeaders headers) {
/*  842 */       this.headers = headers;
/*  843 */       this.iter = MessageImpl.this.attachments.iterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  851 */       if (this.nextAttachment == null)
/*  852 */         this.nextAttachment = nextMatch(); 
/*  853 */       return (this.nextAttachment != null);
/*      */     }
/*      */     
/*      */     public Object next() {
/*  857 */       if (this.nextAttachment != null) {
/*  858 */         Object ret = this.nextAttachment;
/*  859 */         this.nextAttachment = null;
/*  860 */         return ret;
/*      */       } 
/*      */       
/*  863 */       if (hasNext()) {
/*  864 */         return this.nextAttachment;
/*      */       }
/*  866 */       return null;
/*      */     }
/*      */     
/*      */     Object nextMatch() {
/*  870 */       while (this.iter.hasNext()) {
/*  871 */         AttachmentPartImpl ap = this.iter.next();
/*  872 */         if (ap.hasAllHeaders(this.headers))
/*  873 */           return ap; 
/*      */       } 
/*  875 */       return null;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  879 */       this.iter.remove();
/*      */     } }
/*      */ 
/*      */   
/*      */   public Iterator getAttachments(MimeHeaders headers) {
/*      */     try {
/*  885 */       initializeAllAttachments();
/*  886 */     } catch (Exception e) {
/*  887 */       throw new RuntimeException(e);
/*      */     } 
/*  889 */     if (this.attachments == null) {
/*  890 */       return nullIter;
/*      */     }
/*  892 */     return new MimeMatchingIterator(headers);
/*      */   }
/*      */   
/*      */   public void removeAttachments(MimeHeaders headers) {
/*      */     try {
/*  897 */       initializeAllAttachments();
/*  898 */     } catch (Exception e) {
/*  899 */       throw new RuntimeException(e);
/*      */     } 
/*  901 */     if (this.attachments == null) {
/*      */       return;
/*      */     }
/*  904 */     Iterator it = new MimeMatchingIterator(headers);
/*  905 */     while (it.hasNext()) {
/*  906 */       int index = this.attachments.indexOf(it.next());
/*  907 */       this.attachments.set(index, null);
/*      */     } 
/*  909 */     FinalArrayList f = new FinalArrayList();
/*  910 */     for (int i = 0; i < this.attachments.size(); i++) {
/*  911 */       if (this.attachments.get(i) != null) {
/*  912 */         f.add(this.attachments.get(i));
/*      */       }
/*      */     } 
/*  915 */     this.attachments = f;
/*      */   }
/*      */ 
/*      */   
/*      */   public AttachmentPart createAttachmentPart() {
/*  920 */     return new AttachmentPartImpl();
/*      */   }
/*      */   
/*      */   public AttachmentPart getAttachment(SOAPElement element) throws SOAPException {
/*      */     String uri;
/*      */     try {
/*  926 */       initializeAllAttachments();
/*  927 */     } catch (Exception e) {
/*  928 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/*  931 */     String hrefAttr = element.getAttribute("href");
/*  932 */     if ("".equals(hrefAttr)) {
/*  933 */       Node node = getValueNodeStrict(element);
/*  934 */       String swaRef = null;
/*  935 */       if (node != null) {
/*  936 */         swaRef = node.getValue();
/*      */       }
/*  938 */       if (swaRef == null || "".equals(swaRef)) {
/*  939 */         return null;
/*      */       }
/*  941 */       uri = swaRef;
/*      */     } else {
/*      */       
/*  944 */       uri = hrefAttr;
/*      */     } 
/*  946 */     return getAttachmentPart(uri);
/*      */   }
/*      */   
/*      */   private Node getValueNodeStrict(SOAPElement element) {
/*  950 */     Node node = (Node)element.getFirstChild();
/*  951 */     if (node != null) {
/*  952 */       if (node.getNextSibling() == null && node
/*  953 */         .getNodeType() == 3) {
/*  954 */         return node;
/*      */       }
/*  956 */       return null;
/*      */     } 
/*      */     
/*  959 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private AttachmentPart getAttachmentPart(String uri) throws SOAPException {
/*      */     AttachmentPart _part;
/*      */     try {
/*  966 */       if (uri.startsWith("cid:")) {
/*      */         
/*  968 */         uri = '<' + uri.substring("cid:".length()) + '>';
/*      */         
/*  970 */         MimeHeaders headersToMatch = new MimeHeaders();
/*  971 */         headersToMatch.addHeader("Content-ID", uri);
/*      */         
/*  973 */         Iterator<AttachmentPart> i = getAttachments(headersToMatch);
/*  974 */         _part = (i == null) ? null : i.next();
/*      */       } else {
/*      */         
/*  977 */         MimeHeaders headersToMatch = new MimeHeaders();
/*  978 */         headersToMatch.addHeader("Content-Location", uri);
/*      */         
/*  980 */         Iterator<AttachmentPart> i = getAttachments(headersToMatch);
/*  981 */         _part = (i == null) ? null : i.next();
/*      */       } 
/*      */ 
/*      */       
/*  985 */       if (_part == null) {
/*  986 */         Iterator<AttachmentPart> j = getAttachments();
/*      */         
/*  988 */         while (j.hasNext()) {
/*  989 */           AttachmentPart p = j.next();
/*  990 */           String cl = p.getContentId();
/*  991 */           if (cl != null) {
/*      */             
/*  993 */             int eqIndex = cl.indexOf("=");
/*  994 */             if (eqIndex > -1) {
/*  995 */               cl = cl.substring(1, eqIndex);
/*  996 */               if (cl.equalsIgnoreCase(uri)) {
/*  997 */                 _part = p;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1005 */     } catch (Exception se) {
/* 1006 */       log.log(Level.SEVERE, "SAAJ0590.soap.unable.to.locate.attachment", new Object[] { uri });
/* 1007 */       throw new SOAPExceptionImpl(se);
/*      */     } 
/* 1009 */     return _part;
/*      */   }
/*      */ 
/*      */   
/*      */   private final InputStream getHeaderBytes() throws IOException {
/* 1014 */     SOAPPartImpl sp = (SOAPPartImpl)getSOAPPart();
/* 1015 */     return sp.getContentAsStream();
/*      */   }
/*      */   
/*      */   private String convertToSingleLine(String contentType) {
/* 1019 */     StringBuffer buffer = new StringBuffer();
/* 1020 */     for (int i = 0; i < contentType.length(); i++) {
/* 1021 */       char c = contentType.charAt(i);
/* 1022 */       if (c != '\r' && c != '\n' && c != '\t')
/* 1023 */         buffer.append(c); 
/*      */     } 
/* 1025 */     return buffer.toString();
/*      */   }
/*      */   
/*      */   private MimeMultipart getMimeMessage() throws SOAPException {
/*      */     try {
/* 1030 */       SOAPPartImpl soapPart = (SOAPPartImpl)getSOAPPart();
/* 1031 */       MimeBodyPart mimeSoapPart = soapPart.getMimePart();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1037 */       ContentType soapPartCtype = new ContentType(getExpectedContentType());
/*      */       
/* 1039 */       if (!this.isFastInfoset) {
/* 1040 */         soapPartCtype.setParameter("charset", initCharset());
/*      */       }
/* 1042 */       mimeSoapPart.setHeader("Content-Type", soapPartCtype.toString());
/*      */       
/* 1044 */       MimeMultipart headerAndBody = null;
/*      */       
/* 1046 */       if (!switchOffBM && !switchOffLazyAttachment && this.multiPart != null && !this.attachmentsInitialized) {
/*      */         
/* 1048 */         BMMimeMultipart bMMimeMultipart = new BMMimeMultipart();
/* 1049 */         bMMimeMultipart.addBodyPart(mimeSoapPart);
/* 1050 */         if (this.attachments != null) {
/* 1051 */           Iterator<AttachmentPartImpl> eachAttachment = this.attachments.iterator();
/* 1052 */           while (eachAttachment.hasNext()) {
/* 1053 */             bMMimeMultipart.addBodyPart(((AttachmentPartImpl)eachAttachment
/* 1054 */                 .next())
/* 1055 */                 .getMimePart());
/*      */           }
/*      */         } 
/* 1058 */         InputStream in = ((BMMimeMultipart)this.multiPart).getInputStream();
/* 1059 */         if (!((BMMimeMultipart)this.multiPart).lastBodyPartFound() && 
/* 1060 */           !((BMMimeMultipart)this.multiPart).isEndOfStream()) {
/* 1061 */           bMMimeMultipart.setInputStream(in);
/* 1062 */           bMMimeMultipart.setBoundary(((BMMimeMultipart)this.multiPart)
/* 1063 */               .getBoundary());
/* 1064 */           bMMimeMultipart
/* 1065 */             .setLazyAttachments(this.lazyAttachments);
/*      */         } 
/*      */       } else {
/*      */         
/* 1069 */         headerAndBody = new MimeMultipart();
/* 1070 */         headerAndBody.addBodyPart(mimeSoapPart);
/*      */         
/* 1072 */         Iterator<AttachmentPartImpl> eachAttachement = getAttachments();
/* 1073 */         while (eachAttachement.hasNext())
/*      */         {
/* 1075 */           headerAndBody.addBodyPart(((AttachmentPartImpl)eachAttachement
/* 1076 */               .next())
/* 1077 */               .getMimePart());
/*      */         }
/*      */       } 
/*      */       
/* 1081 */       ContentType contentType = headerAndBody.getContentType();
/*      */       
/* 1083 */       ParameterList l = contentType.getParameterList();
/*      */ 
/*      */       
/* 1086 */       l.set("type", getExpectedContentType());
/* 1087 */       l.set("boundary", contentType.getParameter("boundary"));
/* 1088 */       ContentType nct = new ContentType("multipart", "related", l);
/*      */       
/* 1090 */       this.headers.setHeader("Content-Type", 
/*      */           
/* 1092 */           convertToSingleLine(nct.toString()));
/*      */ 
/*      */ 
/*      */       
/* 1096 */       return headerAndBody;
/* 1097 */     } catch (SOAPException ex) {
/* 1098 */       throw ex;
/* 1099 */     } catch (Throwable ex) {
/* 1100 */       log.severe("SAAJ0538.soap.cannot.convert.msg.to.multipart.obj");
/* 1101 */       throw new SOAPExceptionImpl("Unable to convert SOAP message into a MimeMultipart object", ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String initCharset() {
/* 1110 */     String charset = null;
/*      */     
/* 1112 */     String[] cts = getMimeHeaders().getHeader("Content-Type");
/* 1113 */     if (cts != null && cts[0] != null) {
/* 1114 */       charset = getCharsetString(cts[0]);
/*      */     }
/*      */     
/* 1117 */     if (charset == null) {
/* 1118 */       charset = (String)getProperty("javax.xml.soap.character-set-encoding");
/*      */     }
/*      */     
/* 1121 */     if (charset != null) {
/* 1122 */       return charset;
/*      */     }
/*      */     
/* 1125 */     return "utf-8";
/*      */   }
/*      */   
/*      */   private String getCharsetString(String s) {
/*      */     try {
/* 1130 */       int index = s.indexOf(";");
/* 1131 */       if (index < 0)
/* 1132 */         return null; 
/* 1133 */       ParameterList pl = new ParameterList(s.substring(index));
/* 1134 */       return pl.get("charset");
/* 1135 */     } catch (Exception e) {
/* 1136 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveChanges() throws SOAPException {
/* 1145 */     String charset = initCharset();
/*      */ 
/*      */     
/* 1148 */     int attachmentCount = (this.attachments == null) ? 0 : this.attachments.size();
/* 1149 */     if (attachmentCount == 0 && 
/* 1150 */       !switchOffBM && !switchOffLazyAttachment && !this.attachmentsInitialized && this.multiPart != null)
/*      */     {
/*      */       
/* 1153 */       attachmentCount = 1;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1158 */       if (attachmentCount == 0 && !hasXOPContent()) {
/*      */         InputStream in;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1166 */           in = getHeaderBytes();
/*      */           
/* 1168 */           this.optimizeAttachmentProcessing = false;
/* 1169 */           if (SOAPPartImpl.lazyContentLength) {
/* 1170 */             this.inputStreamAfterSaveChanges = in;
/*      */           }
/* 1172 */         } catch (IOException ex) {
/* 1173 */           log.severe("SAAJ0539.soap.cannot.get.header.stream");
/* 1174 */           throw new SOAPExceptionImpl("Unable to get header stream in saveChanges: ", ex);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1179 */         if (in instanceof ByteInputStream) {
/* 1180 */           ByteInputStream bIn = (ByteInputStream)in;
/* 1181 */           this.messageBytes = bIn.getBytes();
/* 1182 */           this.messageByteCount = bIn.getCount();
/*      */         } 
/*      */         
/* 1185 */         setFinalContentType(charset);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1191 */         if (this.messageByteCount > 0) {
/* 1192 */           this.headers.setHeader("Content-Length", 
/*      */               
/* 1194 */               Integer.toString(this.messageByteCount));
/*      */         }
/*      */       }
/* 1197 */       else if (hasXOPContent()) {
/* 1198 */         this.mmp = getXOPMessage();
/*      */       } else {
/* 1200 */         this.mmp = getMimeMessage();
/*      */       } 
/* 1202 */     } catch (Throwable ex) {
/* 1203 */       InputStream in; log.severe("SAAJ0540.soap.err.saving.multipart.msg");
/* 1204 */       throw new SOAPExceptionImpl("Error during saving a multipart message", in);
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
/* 1221 */     this.saved = true;
/*      */   }
/*      */   
/*      */   private MimeMultipart getXOPMessage() throws SOAPException {
/*      */     try {
/* 1226 */       MimeMultipart headerAndBody = new MimeMultipart();
/* 1227 */       SOAPPartImpl soapPart = (SOAPPartImpl)getSOAPPart();
/* 1228 */       MimeBodyPart mimeSoapPart = soapPart.getMimePart();
/* 1229 */       ContentType soapPartCtype = new ContentType("application/xop+xml");
/*      */       
/* 1231 */       soapPartCtype.setParameter("type", getExpectedContentType());
/* 1232 */       String charset = initCharset();
/* 1233 */       soapPartCtype.setParameter("charset", charset);
/* 1234 */       mimeSoapPart.setHeader("Content-Type", soapPartCtype.toString());
/* 1235 */       headerAndBody.addBodyPart(mimeSoapPart);
/*      */       
/* 1237 */       Iterator<AttachmentPartImpl> eachAttachement = getAttachments();
/* 1238 */       while (eachAttachement.hasNext())
/*      */       {
/* 1240 */         headerAndBody.addBodyPart(((AttachmentPartImpl)eachAttachement
/* 1241 */             .next())
/* 1242 */             .getMimePart());
/*      */       }
/*      */       
/* 1245 */       ContentType contentType = headerAndBody.getContentType();
/*      */       
/* 1247 */       ParameterList l = contentType.getParameterList();
/*      */ 
/*      */       
/* 1250 */       l.set("start-info", getExpectedContentType());
/*      */ 
/*      */       
/* 1253 */       l.set("type", "application/xop+xml");
/*      */       
/* 1255 */       if (isCorrectSoapVersion(8)) {
/* 1256 */         String action = getAction();
/* 1257 */         if (action != null) {
/* 1258 */           l.set("action", action);
/*      */         }
/*      */       } 
/* 1261 */       l.set("boundary", contentType.getParameter("boundary"));
/* 1262 */       ContentType nct = new ContentType("Multipart", "Related", l);
/* 1263 */       this.headers.setHeader("Content-Type", 
/*      */           
/* 1265 */           convertToSingleLine(nct.toString()));
/*      */ 
/*      */ 
/*      */       
/* 1269 */       return headerAndBody;
/* 1270 */     } catch (SOAPException ex) {
/* 1271 */       throw ex;
/* 1272 */     } catch (Throwable ex) {
/* 1273 */       log.severe("SAAJ0538.soap.cannot.convert.msg.to.multipart.obj");
/* 1274 */       throw new SOAPExceptionImpl("Unable to convert SOAP message into a MimeMultipart object", ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasXOPContent() throws ParseException {
/* 1283 */     String type = getContentType();
/* 1284 */     if (type == null)
/* 1285 */       return false; 
/* 1286 */     ContentType ct = new ContentType(type);
/*      */     
/* 1288 */     return (isMimeMultipartXOPSoap1_1Package(ct) || 
/* 1289 */       isMimeMultipartXOPSoap1_2Package(ct) || isSOAPBodyXOPPackage(ct));
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeTo(OutputStream out) throws SOAPException, IOException {
/* 1294 */     if (saveRequired()) {
/* 1295 */       this.optimizeAttachmentProcessing = true;
/* 1296 */       saveChanges();
/*      */     } 
/*      */     
/* 1299 */     if (!this.optimizeAttachmentProcessing) {
/* 1300 */       if (SOAPPartImpl.lazyContentLength && this.messageByteCount <= 0) {
/* 1301 */         byte[] buf = new byte[1024];
/*      */         
/* 1303 */         int length = 0;
/* 1304 */         while ((length = this.inputStreamAfterSaveChanges.read(buf)) != -1) {
/* 1305 */           out.write(buf, 0, length);
/* 1306 */           this.messageByteCount += length;
/*      */         } 
/* 1308 */         if (this.messageByteCount > 0) {
/* 1309 */           this.headers.setHeader("Content-Length", 
/*      */               
/* 1311 */               Integer.toString(this.messageByteCount));
/*      */         }
/*      */       } else {
/* 1314 */         out.write(this.messageBytes, 0, this.messageByteCount);
/*      */       } 
/*      */     } else {
/*      */       
/*      */       try {
/* 1319 */         if (hasXOPContent()) {
/* 1320 */           this.mmp.writeTo(out);
/*      */         } else {
/* 1322 */           this.mmp.writeTo(out);
/* 1323 */           if (!switchOffBM && !switchOffLazyAttachment && this.multiPart != null && !this.attachmentsInitialized)
/*      */           {
/* 1325 */             ((BMMimeMultipart)this.multiPart).setInputStream(((BMMimeMultipart)this.mmp)
/* 1326 */                 .getInputStream());
/*      */           }
/*      */         } 
/* 1329 */       } catch (Exception ex) {
/* 1330 */         log.severe("SAAJ0540.soap.err.saving.multipart.msg");
/* 1331 */         throw new SOAPExceptionImpl("Error during saving a multipart message", ex);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1337 */     if (isCorrectSoapVersion(4)) {
/*      */       
/* 1339 */       String[] soapAction = this.headers.getHeader("SOAPAction");
/*      */       
/* 1341 */       if (soapAction == null || soapAction.length == 0) {
/* 1342 */         this.headers.setHeader("SOAPAction", "\"\"");
/*      */       }
/*      */     } 
/*      */     
/* 1346 */     this.messageBytes = null;
/* 1347 */     needsSave();
/*      */   }
/*      */   
/*      */   public SOAPBody getSOAPBody() throws SOAPException {
/* 1351 */     SOAPBody body = getSOAPPart().getEnvelope().getBody();
/*      */ 
/*      */ 
/*      */     
/* 1355 */     return body;
/*      */   }
/*      */   
/*      */   public SOAPHeader getSOAPHeader() throws SOAPException {
/* 1359 */     SOAPHeader hdr = getSOAPPart().getEnvelope().getHeader();
/*      */ 
/*      */ 
/*      */     
/* 1363 */     return hdr;
/*      */   }
/*      */ 
/*      */   
/*      */   private void initializeAllAttachments() throws MessagingException, SOAPException {
/* 1368 */     if (switchOffBM || switchOffLazyAttachment) {
/*      */       return;
/*      */     }
/*      */     
/* 1372 */     if (this.attachmentsInitialized || this.multiPart == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1376 */     if (this.attachments == null) {
/* 1377 */       this.attachments = new FinalArrayList();
/*      */     }
/* 1379 */     int count = this.multiPart.getCount();
/* 1380 */     for (int i = 0; i < count; i++) {
/* 1381 */       initializeAttachment(this.multiPart.getBodyPart(i));
/*      */     }
/* 1383 */     this.attachmentsInitialized = true;
/*      */     
/* 1385 */     needsSave();
/*      */   }
/*      */   
/*      */   private void initializeAttachment(MimeBodyPart mbp) throws SOAPException {
/* 1389 */     AttachmentPartImpl attachmentPart = new AttachmentPartImpl();
/* 1390 */     DataHandler attachmentHandler = mbp.getDataHandler();
/* 1391 */     attachmentPart.setDataHandler(attachmentHandler);
/*      */     
/* 1393 */     AttachmentPartImpl.copyMimeHeaders(mbp, attachmentPart);
/* 1394 */     this.attachments.add(attachmentPart);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initializeAttachment(MimeMultipart multiPart, int i) throws Exception {
/* 1399 */     MimeBodyPart currentBodyPart = multiPart.getBodyPart(i);
/* 1400 */     AttachmentPartImpl attachmentPart = new AttachmentPartImpl();
/*      */     
/* 1402 */     DataHandler attachmentHandler = currentBodyPart.getDataHandler();
/* 1403 */     attachmentPart.setDataHandler(attachmentHandler);
/*      */     
/* 1405 */     AttachmentPartImpl.copyMimeHeaders(currentBodyPart, attachmentPart);
/* 1406 */     addAttachmentPart(attachmentPart);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setMimeHeaders(SOAPPart soapPart, MimeBodyPart soapMessagePart) throws Exception {
/* 1413 */     soapPart.removeAllMimeHeaders();
/*      */     
/* 1415 */     FinalArrayList<Header> finalArrayList = soapMessagePart.getAllHeaders();
/* 1416 */     int sz = finalArrayList.size();
/* 1417 */     for (int i = 0; i < sz; i++) {
/* 1418 */       Header h = finalArrayList.get(i);
/* 1419 */       soapPart.addMimeHeader(h.getName(), h.getValue());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initCharsetProperty(ContentType contentType) {
/* 1424 */     String charset = contentType.getParameter("charset");
/* 1425 */     if (charset != null) {
/* 1426 */       ((SOAPPartImpl)getSOAPPart()).setSourceCharsetEncoding(charset);
/* 1427 */       if (!charset.equalsIgnoreCase("utf-8"))
/* 1428 */         setProperty("javax.xml.soap.character-set-encoding", charset); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setLazyAttachments(boolean flag) {
/* 1433 */     this.lazyAttachments = flag;
/*      */   }
/*      */   
/*      */   protected abstract boolean isCorrectSoapVersion(int paramInt);
/*      */   
/*      */   protected abstract String getExpectedContentType();
/*      */   
/*      */   protected abstract String getExpectedAcceptHeader();
/*      */   
/*      */   public abstract SOAPPart getSOAPPart();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/MessageImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */