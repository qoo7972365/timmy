/*     */ package com.sun.xml.internal.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;
/*     */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.InternetHeaders;
/*     */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeBodyPart;
/*     */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimePartDataSource;
/*     */ import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
/*     */ import com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility;
/*     */ import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
/*     */ import com.sun.xml.internal.messaging.saaj.util.FinalArrayList;
/*     */ import com.sun.xml.internal.org.jvnet.mimepull.Header;
/*     */ import com.sun.xml.internal.org.jvnet.mimepull.MIMEPart;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.CommandInfo;
/*     */ import javax.activation.CommandMap;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.activation.MailcapCommandMap;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPException;
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
/*     */ public class AttachmentPartImpl
/*     */   extends AttachmentPart
/*     */ {
/*  62 */   protected static final Logger log = Logger.getLogger("com.sun.xml.internal.messaging.saaj.soap", "com.sun.xml.internal.messaging.saaj.soap.LocalStrings");
/*     */   
/*     */   private final MimeHeaders headers;
/*     */   
/*  66 */   private MimeBodyPart rawContent = null;
/*  67 */   private DataHandler dataHandler = null;
/*     */ 
/*     */   
/*  70 */   private MIMEPart mimePart = null;
/*     */   
/*     */   public AttachmentPartImpl() {
/*  73 */     this.headers = new MimeHeaders();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     initializeJavaActivationHandlers();
/*     */   }
/*     */   
/*     */   public AttachmentPartImpl(MIMEPart part) {
/*  83 */     this.headers = new MimeHeaders();
/*  84 */     this.mimePart = part;
/*  85 */     List<? extends Header> hdrs = part.getAllHeaders();
/*  86 */     for (Header hd : hdrs) {
/*  87 */       this.headers.addHeader(hd.getName(), hd.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() throws SOAPException {
/*  93 */     if (this.mimePart != null) {
/*     */       try {
/*  95 */         return this.mimePart.read().available();
/*  96 */       } catch (IOException e) {
/*  97 */         return -1;
/*     */       } 
/*     */     }
/* 100 */     if (this.rawContent == null && this.dataHandler == null) {
/* 101 */       return 0;
/*     */     }
/* 103 */     if (this.rawContent != null) {
/*     */       try {
/* 105 */         return this.rawContent.getSize();
/* 106 */       } catch (Exception ex) {
/* 107 */         log.log(Level.SEVERE, "SAAJ0573.soap.attachment.getrawbytes.ioexception", (Object[])new String[] { ex
/*     */ 
/*     */               
/* 110 */               .getLocalizedMessage() });
/* 111 */         throw new SOAPExceptionImpl("Raw InputStream Error: " + ex);
/*     */       } 
/*     */     }
/* 114 */     ByteOutputStream bout = new ByteOutputStream();
/*     */     try {
/* 116 */       this.dataHandler.writeTo((OutputStream)bout);
/* 117 */     } catch (IOException ex) {
/* 118 */       log.log(Level.SEVERE, "SAAJ0501.soap.data.handler.err", (Object[])new String[] { ex
/*     */ 
/*     */             
/* 121 */             .getLocalizedMessage() });
/* 122 */       throw new SOAPExceptionImpl("Data handler error: " + ex);
/*     */     } 
/* 124 */     return bout.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 129 */     if (this.mimePart != null) {
/* 130 */       this.mimePart.close();
/* 131 */       this.mimePart = null;
/*     */     } 
/* 133 */     this.dataHandler = null;
/* 134 */     this.rawContent = null;
/*     */   }
/*     */   
/*     */   public Object getContent() throws SOAPException {
/*     */     try {
/* 139 */       if (this.mimePart != null)
/*     */       {
/* 141 */         return this.mimePart.read();
/*     */       }
/* 143 */       if (this.dataHandler != null)
/* 144 */         return getDataHandler().getContent(); 
/* 145 */       if (this.rawContent != null) {
/* 146 */         return this.rawContent.getContent();
/*     */       }
/* 148 */       log.severe("SAAJ0572.soap.no.content.for.attachment");
/* 149 */       throw new SOAPExceptionImpl("No data handler/content associated with this attachment");
/*     */     }
/* 151 */     catch (Exception ex) {
/* 152 */       log.log(Level.SEVERE, "SAAJ0575.soap.attachment.getcontent.exception", ex);
/* 153 */       throw new SOAPExceptionImpl(ex.getLocalizedMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(Object object, String contentType) throws IllegalArgumentException {
/* 159 */     if (this.mimePart != null) {
/* 160 */       this.mimePart.close();
/* 161 */       this.mimePart = null;
/*     */     } 
/* 163 */     DataHandler dh = new DataHandler(object, contentType);
/*     */     
/* 165 */     setDataHandler(dh);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataHandler getDataHandler() throws SOAPException {
/* 170 */     if (this.mimePart != null)
/*     */     {
/* 172 */       return new DataHandler(new DataSource()
/*     */           {
/*     */             public InputStream getInputStream() throws IOException {
/* 175 */               return AttachmentPartImpl.this.mimePart.read();
/*     */             }
/*     */             
/*     */             public OutputStream getOutputStream() throws IOException {
/* 179 */               throw new UnsupportedOperationException("getOutputStream cannot be supported : You have enabled LazyAttachments Option");
/*     */             }
/*     */             
/*     */             public String getContentType() {
/* 183 */               return AttachmentPartImpl.this.mimePart.getContentType();
/*     */             }
/*     */             
/*     */             public String getName() {
/* 187 */               return "MIMEPart Wrapper DataSource";
/*     */             }
/*     */           });
/*     */     }
/* 191 */     if (this.dataHandler == null) {
/* 192 */       if (this.rawContent != null) {
/* 193 */         return new DataHandler((DataSource)new MimePartDataSource(this.rawContent));
/*     */       }
/* 195 */       log.severe("SAAJ0502.soap.no.handler.for.attachment");
/* 196 */       throw new SOAPExceptionImpl("No data handler associated with this attachment");
/*     */     } 
/* 198 */     return this.dataHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDataHandler(DataHandler dataHandler) throws IllegalArgumentException {
/* 203 */     if (this.mimePart != null) {
/* 204 */       this.mimePart.close();
/* 205 */       this.mimePart = null;
/*     */     } 
/* 207 */     if (dataHandler == null) {
/* 208 */       log.severe("SAAJ0503.soap.no.null.to.dataHandler");
/* 209 */       throw new IllegalArgumentException("Null dataHandler argument to setDataHandler");
/*     */     } 
/* 211 */     this.dataHandler = dataHandler;
/* 212 */     this.rawContent = null;
/*     */     
/* 214 */     if (log.isLoggable(Level.FINE))
/* 215 */       log.log(Level.FINE, "SAAJ0580.soap.set.Content-Type", (Object[])new String[] { dataHandler
/* 216 */             .getContentType() }); 
/* 217 */     setMimeHeader("Content-Type", dataHandler.getContentType());
/*     */   }
/*     */   
/*     */   public void removeAllMimeHeaders() {
/* 221 */     this.headers.removeAllHeaders();
/*     */   }
/*     */   
/*     */   public void removeMimeHeader(String header) {
/* 225 */     this.headers.removeHeader(header);
/*     */   }
/*     */   
/*     */   public String[] getMimeHeader(String name) {
/* 229 */     return this.headers.getHeader(name);
/*     */   }
/*     */   
/*     */   public void setMimeHeader(String name, String value) {
/* 233 */     this.headers.setHeader(name, value);
/*     */   }
/*     */   
/*     */   public void addMimeHeader(String name, String value) {
/* 237 */     this.headers.addHeader(name, value);
/*     */   }
/*     */   
/*     */   public Iterator getAllMimeHeaders() {
/* 241 */     return this.headers.getAllHeaders();
/*     */   }
/*     */   
/*     */   public Iterator getMatchingMimeHeaders(String[] names) {
/* 245 */     return this.headers.getMatchingHeaders(names);
/*     */   }
/*     */   
/*     */   public Iterator getNonMatchingMimeHeaders(String[] names) {
/* 249 */     return this.headers.getNonMatchingHeaders(names);
/*     */   }
/*     */   
/*     */   boolean hasAllHeaders(MimeHeaders hdrs) {
/* 253 */     if (hdrs != null) {
/* 254 */       Iterator<MimeHeader> i = hdrs.getAllHeaders();
/* 255 */       while (i.hasNext()) {
/* 256 */         MimeHeader hdr = i.next();
/* 257 */         String[] values = this.headers.getHeader(hdr.getName());
/* 258 */         boolean found = false;
/*     */         
/* 260 */         if (values != null) {
/* 261 */           for (int j = 0; j < values.length; j++) {
/* 262 */             if (hdr.getValue().equalsIgnoreCase(values[j])) {
/* 263 */               found = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/* 268 */         if (!found) {
/* 269 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 273 */     return true;
/*     */   }
/*     */   
/*     */   MimeBodyPart getMimePart() throws SOAPException {
/*     */     try {
/* 278 */       if (this.mimePart != null) {
/* 279 */         return new MimeBodyPart(this.mimePart);
/*     */       }
/* 281 */       if (this.rawContent != null) {
/* 282 */         copyMimeHeaders(this.headers, this.rawContent);
/* 283 */         return this.rawContent;
/*     */       } 
/*     */       
/* 286 */       MimeBodyPart envelope = new MimeBodyPart();
/*     */       
/* 288 */       envelope.setDataHandler(this.dataHandler);
/* 289 */       copyMimeHeaders(this.headers, envelope);
/*     */       
/* 291 */       return envelope;
/* 292 */     } catch (Exception ex) {
/* 293 */       log.severe("SAAJ0504.soap.cannot.externalize.attachment");
/* 294 */       throw new SOAPExceptionImpl("Unable to externalize attachment", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyMimeHeaders(MimeHeaders headers, MimeBodyPart mbp) throws SOAPException {
/* 301 */     Iterator<MimeHeader> i = headers.getAllHeaders();
/*     */     
/* 303 */     while (i.hasNext()) {
/*     */       try {
/* 305 */         MimeHeader mh = i.next();
/*     */         
/* 307 */         mbp.setHeader(mh.getName(), mh.getValue());
/* 308 */       } catch (Exception ex) {
/* 309 */         log.severe("SAAJ0505.soap.cannot.copy.mime.hdr");
/* 310 */         throw new SOAPExceptionImpl("Unable to copy MIME header", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void copyMimeHeaders(MimeBodyPart mbp, AttachmentPartImpl ap) throws SOAPException {
/*     */     try {
/* 317 */       FinalArrayList<Header> finalArrayList = mbp.getAllHeaders();
/* 318 */       int sz = finalArrayList.size();
/* 319 */       for (int i = 0; i < sz; i++) {
/* 320 */         Header h = finalArrayList.get(i);
/* 321 */         if (!h.getName().equalsIgnoreCase("Content-Type"))
/*     */         {
/* 323 */           ap.addMimeHeader(h.getName(), h.getValue()); } 
/*     */       } 
/* 325 */     } catch (Exception ex) {
/* 326 */       log.severe("SAAJ0506.soap.cannot.copy.mime.hdrs.into.attachment");
/* 327 */       throw new SOAPExceptionImpl("Unable to copy MIME headers into attachment", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBase64Content(InputStream content, String contentType) throws SOAPException {
/* 336 */     if (this.mimePart != null) {
/* 337 */       this.mimePart.close();
/* 338 */       this.mimePart = null;
/*     */     } 
/* 340 */     this.dataHandler = null;
/* 341 */     InputStream decoded = null;
/*     */     try {
/* 343 */       decoded = MimeUtility.decode(content, "base64");
/* 344 */       InternetHeaders hdrs = new InternetHeaders();
/* 345 */       hdrs.setHeader("Content-Type", contentType);
/*     */ 
/*     */ 
/*     */       
/* 349 */       ByteOutputStream bos = new ByteOutputStream();
/* 350 */       bos.write(decoded);
/* 351 */       this.rawContent = new MimeBodyPart(hdrs, bos.getBytes(), bos.getCount());
/* 352 */       setMimeHeader("Content-Type", contentType);
/* 353 */     } catch (Exception e) {
/* 354 */       log.log(Level.SEVERE, "SAAJ0578.soap.attachment.setbase64content.exception", e);
/* 355 */       throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */     } finally {
/*     */       try {
/* 358 */         decoded.close();
/* 359 */       } catch (IOException ex) {
/* 360 */         throw new SOAPException(ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public InputStream getBase64Content() throws SOAPException {
/*     */     InputStream stream;
/* 367 */     if (this.mimePart != null) {
/* 368 */       stream = this.mimePart.read();
/* 369 */     } else if (this.rawContent != null) {
/*     */       try {
/* 371 */         stream = this.rawContent.getInputStream();
/* 372 */       } catch (Exception e) {
/* 373 */         log.log(Level.SEVERE, "SAAJ0579.soap.attachment.getbase64content.exception", e);
/* 374 */         throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */       } 
/* 376 */     } else if (this.dataHandler != null) {
/*     */       try {
/* 378 */         stream = this.dataHandler.getInputStream();
/* 379 */       } catch (IOException e) {
/* 380 */         log.severe("SAAJ0574.soap.attachment.datahandler.ioexception");
/* 381 */         throw new SOAPExceptionImpl("DataHandler error" + e);
/*     */       } 
/*     */     } else {
/* 384 */       log.severe("SAAJ0572.soap.no.content.for.attachment");
/* 385 */       throw new SOAPExceptionImpl("No data handler/content associated with this attachment");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 392 */     int size = 1024;
/*     */     
/* 394 */     if (stream != null) {
/*     */       try {
/* 396 */         ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
/*     */ 
/*     */         
/* 399 */         OutputStream ret = MimeUtility.encode(bos, "base64");
/* 400 */         byte[] buf = new byte[size]; int len;
/* 401 */         while ((len = stream.read(buf, 0, size)) != -1) {
/* 402 */           ret.write(buf, 0, len);
/*     */         }
/* 404 */         ret.flush();
/* 405 */         buf = bos.toByteArray();
/* 406 */         return new ByteArrayInputStream(buf);
/* 407 */       } catch (Exception e) {
/*     */         
/* 409 */         log.log(Level.SEVERE, "SAAJ0579.soap.attachment.getbase64content.exception", e);
/* 410 */         throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */       } finally {
/*     */         try {
/* 413 */           stream.close();
/* 414 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 420 */     log.log(Level.SEVERE, "SAAJ0572.soap.no.content.for.attachment");
/* 421 */     throw new SOAPExceptionImpl("No data handler/content associated with this attachment");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRawContent(InputStream content, String contentType) throws SOAPException {
/* 427 */     if (this.mimePart != null) {
/* 428 */       this.mimePart.close();
/* 429 */       this.mimePart = null;
/*     */     } 
/* 431 */     this.dataHandler = null;
/*     */     try {
/* 433 */       InternetHeaders hdrs = new InternetHeaders();
/* 434 */       hdrs.setHeader("Content-Type", contentType);
/*     */ 
/*     */ 
/*     */       
/* 438 */       ByteOutputStream bos = new ByteOutputStream();
/* 439 */       bos.write(content);
/* 440 */       this.rawContent = new MimeBodyPart(hdrs, bos.getBytes(), bos.getCount());
/* 441 */       setMimeHeader("Content-Type", contentType);
/* 442 */     } catch (Exception e) {
/* 443 */       log.log(Level.SEVERE, "SAAJ0576.soap.attachment.setrawcontent.exception", e);
/* 444 */       throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */     } finally {
/*     */       try {
/* 447 */         content.close();
/* 448 */       } catch (IOException ex) {
/* 449 */         throw new SOAPException(ex);
/*     */       } 
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
/*     */   public void setRawContentBytes(byte[] content, int off, int len, String contentType) throws SOAPException {
/* 475 */     if (this.mimePart != null) {
/* 476 */       this.mimePart.close();
/* 477 */       this.mimePart = null;
/*     */     } 
/* 479 */     if (content == null) {
/* 480 */       throw new SOAPExceptionImpl("Null content passed to setRawContentBytes");
/*     */     }
/* 482 */     this.dataHandler = null;
/*     */     try {
/* 484 */       InternetHeaders hdrs = new InternetHeaders();
/* 485 */       hdrs.setHeader("Content-Type", contentType);
/* 486 */       this.rawContent = new MimeBodyPart(hdrs, content, off, len);
/* 487 */       setMimeHeader("Content-Type", contentType);
/* 488 */     } catch (Exception e) {
/* 489 */       log.log(Level.SEVERE, "SAAJ0576.soap.attachment.setrawcontent.exception", e);
/*     */       
/* 491 */       throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public InputStream getRawContent() throws SOAPException {
/* 496 */     if (this.mimePart != null) {
/* 497 */       return this.mimePart.read();
/*     */     }
/* 499 */     if (this.rawContent != null)
/*     */       try {
/* 501 */         return this.rawContent.getInputStream();
/* 502 */       } catch (Exception e) {
/* 503 */         log.log(Level.SEVERE, "SAAJ0577.soap.attachment.getrawcontent.exception", e);
/* 504 */         throw new SOAPExceptionImpl(e.getLocalizedMessage());
/*     */       }  
/* 506 */     if (this.dataHandler != null) {
/*     */       try {
/* 508 */         return this.dataHandler.getInputStream();
/* 509 */       } catch (IOException e) {
/* 510 */         log.severe("SAAJ0574.soap.attachment.datahandler.ioexception");
/* 511 */         throw new SOAPExceptionImpl("DataHandler error" + e);
/*     */       } 
/*     */     }
/* 514 */     log.severe("SAAJ0572.soap.no.content.for.attachment");
/* 515 */     throw new SOAPExceptionImpl("No data handler/content associated with this attachment");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getRawContentBytes() throws SOAPException {
/* 521 */     if (this.mimePart != null) {
/*     */       try {
/* 523 */         InputStream ret = this.mimePart.read();
/* 524 */         return ASCIIUtility.getBytes(ret);
/* 525 */       } catch (IOException ex) {
/* 526 */         log.log(Level.SEVERE, "SAAJ0577.soap.attachment.getrawcontent.exception", ex);
/* 527 */         throw new SOAPExceptionImpl(ex);
/*     */       } 
/*     */     }
/* 530 */     if (this.rawContent != null)
/*     */       try {
/* 532 */         InputStream ret = this.rawContent.getInputStream();
/* 533 */         return ASCIIUtility.getBytes(ret);
/* 534 */       } catch (Exception e) {
/* 535 */         log.log(Level.SEVERE, "SAAJ0577.soap.attachment.getrawcontent.exception", e);
/* 536 */         throw new SOAPExceptionImpl(e);
/*     */       }  
/* 538 */     if (this.dataHandler != null) {
/*     */       try {
/* 540 */         InputStream ret = this.dataHandler.getInputStream();
/* 541 */         return ASCIIUtility.getBytes(ret);
/* 542 */       } catch (IOException e) {
/* 543 */         log.severe("SAAJ0574.soap.attachment.datahandler.ioexception");
/* 544 */         throw new SOAPExceptionImpl("DataHandler error" + e);
/*     */       } 
/*     */     }
/* 547 */     log.severe("SAAJ0572.soap.no.content.for.attachment");
/* 548 */     throw new SOAPExceptionImpl("No data handler/content associated with this attachment");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 554 */     return (this == o);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 560 */     return super.hashCode();
/*     */   }
/*     */   
/*     */   public MimeHeaders getMimeHeaders() {
/* 564 */     return this.headers;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initializeJavaActivationHandlers() {
/*     */     try {
/* 570 */       CommandMap map = CommandMap.getDefaultCommandMap();
/* 571 */       if (map instanceof MailcapCommandMap) {
/* 572 */         MailcapCommandMap mailMap = (MailcapCommandMap)map;
/*     */ 
/*     */         
/* 575 */         if (!cmdMapInitialized(mailMap)) {
/* 576 */           mailMap.addMailcap("text/xml;;x-java-content-handler=com.sun.xml.internal.messaging.saaj.soap.XmlDataContentHandler");
/* 577 */           mailMap.addMailcap("application/xml;;x-java-content-handler=com.sun.xml.internal.messaging.saaj.soap.XmlDataContentHandler");
/* 578 */           mailMap.addMailcap("application/fastinfoset;;x-java-content-handler=com.sun.xml.internal.messaging.saaj.soap.FastInfosetDataContentHandler");
/*     */ 
/*     */           
/* 581 */           mailMap.addMailcap("image/*;;x-java-content-handler=com.sun.xml.internal.messaging.saaj.soap.ImageDataContentHandler");
/* 582 */           mailMap.addMailcap("text/plain;;x-java-content-handler=com.sun.xml.internal.messaging.saaj.soap.StringDataContentHandler");
/*     */         } 
/*     */       } 
/* 585 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean cmdMapInitialized(MailcapCommandMap mailMap) {
/* 593 */     CommandInfo[] commands = mailMap.getAllCommands("application/fastinfoset");
/* 594 */     if (commands == null || commands.length == 0) {
/* 595 */       return false;
/*     */     }
/*     */     
/* 598 */     String saajClassName = "com.sun.xml.internal.ws.binding.FastInfosetDataContentHandler";
/* 599 */     for (CommandInfo command : commands) {
/* 600 */       String commandClass = command.getCommandClass();
/* 601 */       if (saajClassName.equals(commandClass)) {
/* 602 */         return true;
/*     */       }
/*     */     } 
/* 605 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/AttachmentPartImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */