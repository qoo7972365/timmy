/*     */ package com.sun.xml.internal.ws.encoding;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageDataContentHandler
/*     */   extends Component
/*     */   implements DataContentHandler
/*     */ {
/*  51 */   private static final Logger log = Logger.getLogger(ImageDataContentHandler.class.getName());
/*     */   private final DataFlavor[] flavor;
/*     */   
/*     */   public ImageDataContentHandler() {
/*  55 */     String[] mimeTypes = ImageIO.getReaderMIMETypes();
/*  56 */     this.flavor = new DataFlavor[mimeTypes.length];
/*  57 */     for (int i = 0; i < mimeTypes.length; i++) {
/*  58 */       this.flavor[i] = (DataFlavor)new ActivationDataFlavor(Image.class, mimeTypes[i], "Image");
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
/*  85 */     for (DataFlavor aFlavor : this.flavor) {
/*  86 */       if (aFlavor.equals(df)) {
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
/*     */       BufferedImage bufImage;
/* 120 */       if (obj instanceof BufferedImage) {
/* 121 */         bufImage = (BufferedImage)obj;
/* 122 */       } else if (obj instanceof Image) {
/* 123 */         bufImage = render((Image)obj);
/*     */       } else {
/* 125 */         throw new IOException("ImageDataContentHandler requires Image object, was given object of type " + obj
/*     */ 
/*     */             
/* 128 */             .getClass().toString());
/*     */       } 
/* 130 */       ImageWriter writer = null;
/* 131 */       Iterator<ImageWriter> i = ImageIO.getImageWritersByMIMEType(type);
/* 132 */       if (i.hasNext()) {
/* 133 */         writer = i.next();
/*     */       }
/* 135 */       if (writer != null) {
/* 136 */         ImageOutputStream stream = ImageIO.createImageOutputStream(os);
/* 137 */         writer.setOutput(stream);
/* 138 */         writer.write(bufImage);
/* 139 */         writer.dispose();
/* 140 */         stream.close();
/*     */       } else {
/* 142 */         throw new IOException("Unsupported mime type:" + type);
/*     */       } 
/* 144 */     } catch (Exception e) {
/* 145 */       BufferedImage bufImage; throw new IOException("Unable to encode the image to a stream " + bufImage
/* 146 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage render(Image img) throws InterruptedException {
/* 153 */     MediaTracker tracker = new MediaTracker(this);
/* 154 */     tracker.addImage(img, 0);
/* 155 */     tracker.waitForAll();
/*     */     
/* 157 */     BufferedImage bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), 1);
/* 158 */     Graphics g = bufImage.createGraphics();
/* 159 */     g.drawImage(img, 0, 0, null);
/* 160 */     g.dispose();
/* 161 */     return bufImage;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/ImageDataContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */