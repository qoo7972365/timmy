/*     */ package com.sun.xml.internal.messaging.saaj.soap;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.MediaTracker;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.ActivationDataFlavor;
/*     */ import javax.activation.DataContentHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JpegDataContentHandler
/*     */   extends Component
/*     */   implements DataContentHandler
/*     */ {
/*     */   public static final String STR_SRC = "java.awt.Image";
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  54 */     DataFlavor[] flavors = new DataFlavor[1];
/*     */     
/*     */     try {
/*  57 */       flavors[0] = (DataFlavor)new ActivationDataFlavor(
/*     */           
/*  59 */           Class.forName("java.awt.Image"), "image/jpeg", "JPEG");
/*     */     
/*     */     }
/*  62 */     catch (Exception e) {
/*  63 */       System.out.println(e);
/*     */     } 
/*     */     
/*  66 */     return flavors;
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
/*     */   public Object getTransferData(DataFlavor df, DataSource ds) {
/*  79 */     if (df.getMimeType().startsWith("image/jpeg") && 
/*  80 */       df.getRepresentationClass().getName().equals("java.awt.Image")) {
/*  81 */       InputStream inputStream = null;
/*  82 */       BufferedImage jpegLoadImage = null;
/*     */       
/*     */       try {
/*  85 */         inputStream = ds.getInputStream();
/*  86 */         jpegLoadImage = ImageIO.read(inputStream);
/*     */       }
/*  88 */       catch (Exception e) {
/*  89 */         System.out.println(e);
/*     */       } 
/*     */       
/*  92 */       return jpegLoadImage;
/*     */     } 
/*     */     
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getContent(DataSource ds) {
/* 102 */     InputStream inputStream = null;
/* 103 */     BufferedImage jpegLoadImage = null;
/*     */     
/*     */     try {
/* 106 */       inputStream = ds.getInputStream();
/* 107 */       jpegLoadImage = ImageIO.read(inputStream);
/*     */     }
/* 109 */     catch (Exception exception) {}
/*     */ 
/*     */     
/* 112 */     return jpegLoadImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
/* 122 */     if (!mimeType.equals("image/jpeg")) {
/* 123 */       throw new IOException("Invalid content type \"" + mimeType + "\" for ImageContentHandler");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 128 */     if (obj == null) {
/* 129 */       throw new IOException("Null object for ImageContentHandler");
/*     */     }
/*     */     
/*     */     try {
/* 133 */       BufferedImage bufImage = null;
/* 134 */       if (obj instanceof BufferedImage) {
/* 135 */         bufImage = (BufferedImage)obj;
/*     */       } else {
/*     */         
/* 138 */         Image img = (Image)obj;
/* 139 */         MediaTracker tracker = new MediaTracker(this);
/* 140 */         tracker.addImage(img, 0);
/* 141 */         tracker.waitForAll();
/* 142 */         if (tracker.isErrorAny()) {
/* 143 */           throw new IOException("Error while loading image");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 148 */         bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), 1);
/*     */ 
/*     */         
/* 151 */         Graphics g = bufImage.createGraphics();
/* 152 */         g.drawImage(img, 0, 0, null);
/*     */       } 
/* 154 */       ImageIO.write(bufImage, "jpeg", os);
/*     */     }
/* 156 */     catch (Exception ex) {
/* 157 */       throw new IOException("Unable to run the JPEG Encoder on a stream " + ex
/*     */           
/* 159 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/JpegDataContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */