/*     */ package com.sun.xml.internal.ws.api.message.saaj;
/*     */ 
/*     */ import com.sun.xml.internal.bind.marshaller.SAX2DOMEx;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentEx;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.message.saaj.SAAJMessage;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public class SAAJFactory
/*     */ {
/*  56 */   private static final SAAJFactory instance = new SAAJFactory();
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
/*     */   public static MessageFactory getMessageFactory(String protocol) throws SOAPException {
/*  82 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/*  83 */       MessageFactory mf = s.createMessageFactory(protocol);
/*  84 */       if (mf != null) {
/*  85 */         return mf;
/*     */       }
/*     */     } 
/*  88 */     return instance.createMessageFactory(protocol);
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
/*     */   public static SOAPFactory getSOAPFactory(String protocol) throws SOAPException {
/* 110 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/* 111 */       SOAPFactory sf = s.createSOAPFactory(protocol);
/* 112 */       if (sf != null) {
/* 113 */         return sf;
/*     */       }
/*     */     } 
/* 116 */     return instance.createSOAPFactory(protocol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message create(SOAPMessage saaj) {
/* 125 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/* 126 */       Message m = s.createMessage(saaj);
/* 127 */       if (m != null) {
/* 128 */         return m;
/*     */       }
/*     */     } 
/* 131 */     return instance.createMessage(saaj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPMessage read(SOAPVersion soapVersion, Message message) throws SOAPException {
/* 142 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/* 143 */       SOAPMessage msg = s.readAsSOAPMessage(soapVersion, message);
/* 144 */       if (msg != null) {
/* 145 */         return msg;
/*     */       }
/*     */     } 
/* 148 */     return instance.readAsSOAPMessage(soapVersion, message);
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
/*     */   public static SOAPMessage read(SOAPVersion soapVersion, Message message, Packet packet) throws SOAPException {
/* 160 */     for (SAAJFactory s : ServiceFinder.find(SAAJFactory.class)) {
/* 161 */       SOAPMessage msg = s.readAsSOAPMessage(soapVersion, message, packet);
/* 162 */       if (msg != null) {
/* 163 */         return msg;
/*     */       }
/*     */     } 
/* 166 */     return instance.readAsSOAPMessage(soapVersion, message, packet);
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
/*     */   public static SAAJMessage read(Packet packet) throws SOAPException {
/* 181 */     ServiceFinder<SAAJFactory> factories = (packet.component != null) ? ServiceFinder.find(SAAJFactory.class, packet.component) : ServiceFinder.find(SAAJFactory.class);
/* 182 */     for (SAAJFactory s : factories) {
/* 183 */       SAAJMessage msg = s.readAsSAAJ(packet);
/* 184 */       if (msg != null) return msg; 
/*     */     } 
/* 186 */     return instance.readAsSAAJ(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAAJMessage readAsSAAJ(Packet packet) throws SOAPException {
/* 196 */     SOAPVersion v = packet.getMessage().getSOAPVersion();
/* 197 */     SOAPMessage msg = readAsSOAPMessage(v, packet.getMessage());
/* 198 */     return new SAAJMessage(msg);
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
/*     */ 
/*     */   
/*     */   public MessageFactory createMessageFactory(String protocol) throws SOAPException {
/* 225 */     return MessageFactory.newInstance(protocol);
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
/*     */   public SOAPFactory createSOAPFactory(String protocol) throws SOAPException {
/* 247 */     return SOAPFactory.newInstance(protocol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message createMessage(SOAPMessage saaj) {
/* 256 */     return (Message)new SAAJMessage(saaj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(SOAPVersion soapVersion, Message message) throws SOAPException {
/* 267 */     SOAPMessage msg = soapVersion.getMessageFactory().createMessage();
/* 268 */     SaajStaxWriter writer = new SaajStaxWriter(msg);
/*     */     try {
/* 270 */       message.writeTo(writer);
/* 271 */     } catch (XMLStreamException e) {
/* 272 */       throw (e.getCause() instanceof SOAPException) ? (SOAPException)e.getCause() : new SOAPException(e);
/*     */     } 
/* 274 */     msg = writer.getSOAPMessage();
/* 275 */     addAttachmentsToSOAPMessage(msg, message);
/* 276 */     if (msg.saveRequired())
/* 277 */       msg.saveChanges(); 
/* 278 */     return msg;
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessageSax2Dom(SOAPVersion soapVersion, Message message) throws SOAPException {
/* 282 */     SOAPMessage msg = soapVersion.getMessageFactory().createMessage();
/* 283 */     SAX2DOMEx s2d = new SAX2DOMEx((Node)msg.getSOAPPart());
/*     */     try {
/* 285 */       message.writeTo((ContentHandler)s2d, XmlUtil.DRACONIAN_ERROR_HANDLER);
/* 286 */     } catch (SAXException e) {
/* 287 */       throw new SOAPException(e);
/*     */     } 
/* 289 */     addAttachmentsToSOAPMessage(msg, message);
/* 290 */     if (msg.saveRequired())
/* 291 */       msg.saveChanges(); 
/* 292 */     return msg;
/*     */   }
/*     */   
/*     */   protected static void addAttachmentsToSOAPMessage(SOAPMessage msg, Message message) {
/* 296 */     for (Attachment att : message.getAttachments()) {
/* 297 */       AttachmentPart part = msg.createAttachmentPart();
/* 298 */       part.setDataHandler(att.asDataHandler());
/*     */ 
/*     */       
/* 301 */       String cid = att.getContentId();
/* 302 */       if (cid != null) {
/* 303 */         if (cid.startsWith("<") && cid.endsWith(">")) {
/* 304 */           part.setContentId(cid);
/*     */         } else {
/* 306 */           part.setContentId('<' + cid + '>');
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 312 */       if (att instanceof AttachmentEx) {
/* 313 */         AttachmentEx ax = (AttachmentEx)att;
/* 314 */         Iterator<AttachmentEx.MimeHeader> imh = ax.getMimeHeaders();
/* 315 */         while (imh.hasNext()) {
/* 316 */           AttachmentEx.MimeHeader ame = imh.next();
/* 317 */           if (!"Content-ID".equals(ame.getName()) && 
/* 318 */             !"Content-Type".equals(ame.getName()))
/* 319 */             part.addMimeHeader(ame.getName(), ame.getValue()); 
/*     */         } 
/*     */       } 
/* 322 */       msg.addAttachmentPart(part);
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
/*     */   public SOAPMessage readAsSOAPMessage(SOAPVersion soapVersion, Message message, Packet packet) throws SOAPException {
/* 337 */     return readAsSOAPMessage(soapVersion, message);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/saaj/SAAJFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */