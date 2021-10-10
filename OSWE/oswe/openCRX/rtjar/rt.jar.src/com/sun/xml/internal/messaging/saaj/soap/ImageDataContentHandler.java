/*     */ package com.sun.xml.internal.messaging.saaj.soap;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.MediaTracker;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.ActivationDataFlavor;
/*     */ import javax.activation.DataContentHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageWriter;
/*     */ import javax.imageio.stream.ImageOutputStream;
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
/*     */ public class ImageDataContentHandler
/*     */   extends Component
/*     */   implements DataContentHandler
/*     */ {
/*  48 */   protected static final Logger log = Logger.getLogger("com.sun.xml.internal.messaging.saaj.soap", "com.sun.xml.internal.messaging.saaj.soap.LocalStrings");
/*     */   
/*     */   private DataFlavor[] flavor;
/*     */ 
/*     */   
/*     */   public ImageDataContentHandler() {
/*  54 */     String[] mimeTypes = ImageIO.getReaderMIMETypes();
/*  55 */     this.flavor = new DataFlavor[mimeTypes.length];
/*  56 */     for (int i = 0; i < mimeTypes.length; i++) {
/*  57 */       this.flavor[i] = (DataFlavor)new ActivationDataFlavor(Image.class, mimeTypes[i], "Image");
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
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  71 */     return Arrays.<DataFlavor>copyOf(this.flavor, this.flavor.length);
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
/*     */   public Object getTransferData(DataFlavor df, DataSource ds) throws IOException {
/*  85 */     for (int i = 0; i < this.flavor.length; i++) {
/*  86 */       if (this.flavor[i].equals(df)) {
/*  87 */         return getContent(ds);
/*     */       }
/*     */     } 
/*  90 */     return null;
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
/*     */   public Object getContent(DataSource ds) throws IOException {
/* 102 */     return ImageIO.read(new BufferedInputStream(ds.getInputStream()));
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
/*     */   public void writeTo(Object obj, String type, OutputStream os) throws IOException {
/*     */     try {
/* 119 */       BufferedImage bufImage = null;
/* 120 */       if (obj instanceof BufferedImage) {
/* 121 */         bufImage = (BufferedImage)obj;
/* 122 */       } else if (obj instanceof Image) {
/* 123 */         bufImage = render((Image)obj);
/*     */       } else {
/* 125 */         log.log(Level.SEVERE, "SAAJ0520.soap.invalid.obj.type", (Object[])new String[] { obj
/*     */               
/* 127 */               .getClass().toString() });
/* 128 */         throw new IOException("ImageDataContentHandler requires Image object, was given object of type " + obj
/*     */ 
/*     */             
/* 131 */             .getClass().toString());
/*     */       } 
/* 133 */       ImageWriter writer = null;
/* 134 */       Iterator<ImageWriter> i = ImageIO.getImageWritersByMIMEType(type);
/* 135 */       if (i.hasNext()) {
/* 136 */         writer = i.next();
/*     */       }
/* 138 */       if (writer != null) {
/* 139 */         ImageOutputStream stream = null;
/* 140 */         stream = ImageIO.createImageOutputStream(os);
/* 141 */         writer.setOutput(stream);
/* 142 */         writer.write(bufImage);
/* 143 */         writer.dispose();
/* 144 */         stream.close();
/*     */       } else {
/* 146 */         log.log(Level.SEVERE, "SAAJ0526.soap.unsupported.mime.type", (Object[])new String[] { type });
/*     */         
/* 148 */         throw new IOException("Unsupported mime type:" + type);
/*     */       } 
/* 150 */     } catch (Exception e) {
/* 151 */       log.severe("SAAJ0525.soap.cannot.encode.img");
/* 152 */       throw new IOException("Unable to encode the image to a stream " + e
/* 153 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage render(Image img) throws InterruptedException {
/* 160 */     MediaTracker tracker = new MediaTracker(this);
/* 161 */     tracker.addImage(img, 0);
/* 162 */     tracker.waitForAll();
/*     */     
/* 164 */     BufferedImage bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), 1);
/* 165 */     Graphics g = bufImage.createGraphics();
/* 166 */     g.drawImage(img, 0, 0, null);
/* 167 */     g.dispose();
/* 168 */     return bufImage;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/ImageDataContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */