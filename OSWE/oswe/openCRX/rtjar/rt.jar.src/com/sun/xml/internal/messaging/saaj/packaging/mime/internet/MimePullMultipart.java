/*     */ package com.sun.xml.internal.messaging.saaj.packaging.mime.internet;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
/*     */ import com.sun.xml.internal.messaging.saaj.soap.AttachmentPartImpl;
/*     */ import com.sun.xml.internal.org.jvnet.mimepull.MIMEConfig;
/*     */ import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;
/*     */ import com.sun.xml.internal.org.jvnet.mimepull.MIMEPart;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import javax.activation.DataSource;
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
/*     */ public class MimePullMultipart
/*     */   extends MimeMultipart
/*     */ {
/*  45 */   private InputStream in = null;
/*  46 */   private String boundary = null;
/*  47 */   private MIMEMessage mm = null;
/*  48 */   private DataSource dataSource = null;
/*  49 */   private ContentType contType = null;
/*  50 */   private String startParam = null;
/*  51 */   private MIMEPart soapPart = null;
/*     */ 
/*     */   
/*     */   public MimePullMultipart(DataSource ds, ContentType ct) throws MessagingException {
/*  55 */     this.parsed = false;
/*  56 */     if (ct == null) {
/*  57 */       this.contType = new ContentType(ds.getContentType());
/*     */     } else {
/*  59 */       this.contType = ct;
/*     */     } 
/*  61 */     this.dataSource = ds;
/*  62 */     this.boundary = this.contType.getParameter("boundary");
/*     */   }
/*     */   
/*     */   public MIMEPart readAndReturnSOAPPart() throws MessagingException {
/*  66 */     if (this.soapPart != null) {
/*  67 */       throw new MessagingException("Inputstream from datasource was already consumed");
/*     */     }
/*  69 */     readSOAPPart();
/*  70 */     return this.soapPart;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readSOAPPart() throws MessagingException {
/*     */     try {
/*  76 */       if (this.soapPart != null) {
/*     */         return;
/*     */       }
/*  79 */       this.in = this.dataSource.getInputStream();
/*  80 */       MIMEConfig config = new MIMEConfig();
/*  81 */       this.mm = new MIMEMessage(this.in, this.boundary, config);
/*  82 */       String st = this.contType.getParameter("start");
/*  83 */       if (this.startParam == null) {
/*  84 */         this.soapPart = this.mm.getPart(0);
/*     */       } else {
/*     */         
/*  87 */         if (st != null && st.length() > 2 && st.charAt(0) == '<' && st.charAt(st.length() - 1) == '>') {
/*  88 */           st = st.substring(1, st.length() - 1);
/*     */         }
/*  90 */         this.startParam = st;
/*  91 */         this.soapPart = this.mm.getPart(this.startParam);
/*     */       }
/*     */     
/*  94 */     } catch (IOException ex) {
/*  95 */       throw new MessagingException("No inputstream from datasource", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void parseAll() throws MessagingException {
/* 100 */     if (this.parsed) {
/*     */       return;
/*     */     }
/* 103 */     if (this.soapPart == null) {
/* 104 */       readSOAPPart();
/*     */     }
/*     */     
/* 107 */     List<MIMEPart> prts = this.mm.getAttachments();
/* 108 */     for (MIMEPart part : prts) {
/* 109 */       if (part != this.soapPart) {
/* 110 */         AttachmentPartImpl attachmentPartImpl = new AttachmentPartImpl(part);
/* 111 */         addBodyPart(new MimeBodyPart(part));
/*     */       } 
/*     */     } 
/* 114 */     this.parsed = true;
/*     */   }
/*     */   
/*     */   protected void parse() throws MessagingException {
/* 118 */     parseAll();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/packaging/mime/internet/MimePullMultipart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */