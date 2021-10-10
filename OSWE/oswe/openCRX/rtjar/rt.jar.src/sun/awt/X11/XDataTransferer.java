/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ import javax.imageio.ImageWriter;
/*     */ import javax.imageio.spi.ImageWriterSpi;
/*     */ import sun.awt.datatransfer.DataTransferer;
/*     */ import sun.awt.datatransfer.ToolkitThreadBlockedHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XDataTransferer
/*     */   extends DataTransferer
/*     */ {
/*  65 */   static final XAtom FILE_NAME_ATOM = XAtom.get("FILE_NAME");
/*  66 */   static final XAtom DT_NET_FILE_ATOM = XAtom.get("_DT_NETFILE");
/*  67 */   static final XAtom PNG_ATOM = XAtom.get("PNG");
/*  68 */   static final XAtom JFIF_ATOM = XAtom.get("JFIF");
/*  69 */   static final XAtom TARGETS_ATOM = XAtom.get("TARGETS");
/*  70 */   static final XAtom INCR_ATOM = XAtom.get("INCR");
/*  71 */   static final XAtom MULTIPLE_ATOM = XAtom.get("MULTIPLE");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static XDataTransferer transferer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized XDataTransferer getInstanceImpl() {
/*  82 */     if (transferer == null) {
/*  83 */       transferer = new XDataTransferer();
/*     */     }
/*  85 */     return transferer;
/*     */   }
/*     */   
/*     */   public String getDefaultUnicodeEncoding() {
/*  89 */     return "iso-10646-ucs-2";
/*     */   }
/*     */   
/*     */   public boolean isLocaleDependentTextFormat(long paramLong) {
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isTextFormat(long paramLong) {
/*  97 */     return (super.isTextFormat(paramLong) || 
/*  98 */       isMimeFormat(paramLong, "text"));
/*     */   }
/*     */   
/*     */   protected String getCharsetForTextFormat(Long paramLong) {
/* 102 */     long l = paramLong.longValue();
/* 103 */     if (isMimeFormat(l, "text")) {
/* 104 */       String str1 = getNativeForFormat(l);
/* 105 */       DataFlavor dataFlavor = new DataFlavor(str1, null);
/*     */ 
/*     */       
/* 108 */       if (!DataTransferer.doesSubtypeSupportCharset(dataFlavor)) {
/* 109 */         return null;
/*     */       }
/* 111 */       String str2 = dataFlavor.getParameter("charset");
/* 112 */       if (str2 != null) {
/* 113 */         return str2;
/*     */       }
/*     */     } 
/* 116 */     return super.getCharsetForTextFormat(paramLong);
/*     */   }
/*     */   
/*     */   protected boolean isURIListFormat(long paramLong) {
/* 120 */     String str = getNativeForFormat(paramLong);
/* 121 */     if (str == null) {
/* 122 */       return false;
/*     */     }
/*     */     try {
/* 125 */       DataFlavor dataFlavor = new DataFlavor(str);
/* 126 */       if (dataFlavor.getPrimaryType().equals("text") && dataFlavor.getSubType().equals("uri-list")) {
/* 127 */         return true;
/*     */       }
/* 129 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFileFormat(long paramLong) {
/* 136 */     return (paramLong == FILE_NAME_ATOM.getAtom() || paramLong == DT_NET_FILE_ATOM
/* 137 */       .getAtom());
/*     */   }
/*     */   
/*     */   public boolean isImageFormat(long paramLong) {
/* 141 */     return (paramLong == PNG_ATOM.getAtom() || paramLong == JFIF_ATOM
/* 142 */       .getAtom() || 
/* 143 */       isMimeFormat(paramLong, "image"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Long getFormatForNativeAsLong(String paramString) {
/* 149 */     long l = XAtom.get(paramString).getAtom();
/* 150 */     return Long.valueOf(l);
/*     */   }
/*     */   
/*     */   protected String getNativeForFormat(long paramLong) {
/* 154 */     return getTargetNameForAtom(paramLong);
/*     */   }
/*     */   
/*     */   public ToolkitThreadBlockedHandler getToolkitThreadBlockedHandler() {
/* 158 */     return XToolkitThreadBlockedHandler.getToolkitThreadBlockedHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getTargetNameForAtom(long paramLong) {
/* 165 */     return XAtom.get(paramLong).getName();
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] imageToPlatformBytes(Image paramImage, long paramLong) throws IOException {
/* 170 */     String str1 = null;
/* 171 */     if (paramLong == PNG_ATOM.getAtom()) {
/* 172 */       str1 = "image/png";
/* 173 */     } else if (paramLong == JFIF_ATOM.getAtom()) {
/* 174 */       str1 = "image/jpeg";
/*     */     } else {
/*     */       
/*     */       try {
/* 178 */         String str3 = getNativeForFormat(paramLong);
/* 179 */         DataFlavor dataFlavor = new DataFlavor(str3);
/* 180 */         String str4 = dataFlavor.getPrimaryType();
/* 181 */         if ("image".equals(str4)) {
/* 182 */           str1 = dataFlavor.getPrimaryType() + "/" + dataFlavor.getSubType();
/*     */         }
/* 184 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/* 188 */     if (str1 != null) {
/* 189 */       return imageToStandardBytes(paramImage, str1);
/*     */     }
/* 191 */     String str2 = getNativeForFormat(paramLong);
/* 192 */     throw new IOException("Translation to " + str2 + " is not supported.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ByteArrayOutputStream convertFileListToBytes(ArrayList<String> paramArrayList) throws IOException {
/* 200 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 201 */     for (byte b = 0; b < paramArrayList.size(); b++) {
/*     */       
/* 203 */       byte[] arrayOfByte = ((String)paramArrayList.get(b)).getBytes();
/* 204 */       if (b != 0) byteArrayOutputStream.write(0); 
/* 205 */       byteArrayOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
/*     */     } 
/* 207 */     return byteArrayOutputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Image platformImageBytesToImage(byte[] paramArrayOfbyte, long paramLong) throws IOException {
/* 217 */     String str1 = null;
/* 218 */     if (paramLong == PNG_ATOM.getAtom()) {
/* 219 */       str1 = "image/png";
/* 220 */     } else if (paramLong == JFIF_ATOM.getAtom()) {
/* 221 */       str1 = "image/jpeg";
/*     */     } else {
/*     */       
/*     */       try {
/* 225 */         String str3 = getNativeForFormat(paramLong);
/* 226 */         DataFlavor dataFlavor = new DataFlavor(str3);
/* 227 */         String str4 = dataFlavor.getPrimaryType();
/* 228 */         if ("image".equals(str4)) {
/* 229 */           str1 = dataFlavor.getPrimaryType() + "/" + dataFlavor.getSubType();
/*     */         }
/* 231 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/* 235 */     if (str1 != null) {
/* 236 */       return standardImageBytesToImage(paramArrayOfbyte, str1);
/*     */     }
/* 238 */     String str2 = getNativeForFormat(paramLong);
/* 239 */     throw new IOException("Translation from " + str2 + " is not supported.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] dragQueryFile(byte[] paramArrayOfbyte) {
/* 246 */     XToolkit.awtLock();
/*     */     try {
/* 248 */       return XlibWrapper.XTextPropertyToStringList(paramArrayOfbyte, 
/* 249 */           XAtom.get("STRING").getAtom());
/*     */     } finally {
/* 251 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected URI[] dragQueryURIs(InputStream paramInputStream, long paramLong, Transferable paramTransferable) throws IOException {
/* 261 */     String str = null;
/* 262 */     if (paramTransferable != null && 
/* 263 */       isLocaleDependentTextFormat(paramLong) && paramTransferable
/* 264 */       .isDataFlavorSupported(javaTextEncodingFlavor)) {
/*     */       
/*     */       try {
/* 267 */         str = new String((byte[])paramTransferable.getTransferData(javaTextEncodingFlavor), "UTF-8");
/*     */       
/*     */       }
/* 270 */       catch (UnsupportedFlavorException unsupportedFlavorException) {}
/*     */     } else {
/*     */       
/* 273 */       str = getCharsetForTextFormat(Long.valueOf(paramLong));
/*     */     } 
/* 275 */     if (str == null)
/*     */     {
/* 277 */       str = getDefaultTextCharset();
/*     */     }
/*     */     
/* 280 */     BufferedReader bufferedReader = null;
/*     */     try {
/* 282 */       bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, str));
/*     */       
/* 284 */       ArrayList<URI> arrayList = new ArrayList(); String str1;
/* 285 */       while ((str1 = bufferedReader.readLine()) != null) {
/*     */         try {
/* 287 */           if (!"\000".equals(str1)) {
/* 288 */             arrayList.add(new URI(str1));
/*     */           }
/* 290 */         } catch (URISyntaxException uRISyntaxException) {
/* 291 */           throw new IOException(uRISyntaxException);
/*     */         } 
/*     */       } 
/* 294 */       return arrayList.<URI>toArray(new URI[arrayList.size()]);
/*     */     } finally {
/* 296 */       if (bufferedReader != null) {
/* 297 */         bufferedReader.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMimeFormat(long paramLong, String paramString) {
/* 306 */     String str = getNativeForFormat(paramLong);
/*     */     
/* 308 */     if (str == null) {
/* 309 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 313 */       DataFlavor dataFlavor = new DataFlavor(str);
/* 314 */       if (paramString.equals(dataFlavor.getPrimaryType())) {
/* 315 */         return true;
/*     */       }
/* 317 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 321 */     return false;
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
/*     */   public LinkedHashSet<DataFlavor> getPlatformMappingsForNative(String paramString) {
/* 333 */     LinkedHashSet<DataFlavor> linkedHashSet = new LinkedHashSet();
/*     */ 
/*     */     
/* 336 */     if (paramString == null) {
/* 337 */       return linkedHashSet;
/*     */     }
/*     */     
/* 340 */     DataFlavor dataFlavor1 = null;
/*     */     
/*     */     try {
/* 343 */       dataFlavor1 = new DataFlavor(paramString);
/* 344 */     } catch (Exception exception) {
/*     */       
/* 346 */       return linkedHashSet;
/*     */     } 
/*     */     
/* 349 */     DataFlavor dataFlavor2 = dataFlavor1;
/* 350 */     String str1 = dataFlavor1.getPrimaryType();
/* 351 */     String str2 = str1 + "/" + dataFlavor1.getSubType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 356 */     if ("image".equals(str1)) {
/* 357 */       Iterator<ImageReader> iterator = ImageIO.getImageReadersByMIMEType(str2);
/* 358 */       if (iterator.hasNext()) {
/* 359 */         linkedHashSet.add(DataFlavor.imageFlavor);
/*     */       }
/*     */     } 
/*     */     
/* 363 */     linkedHashSet.add(dataFlavor2);
/*     */     
/* 365 */     return linkedHashSet;
/*     */   }
/*     */   
/* 368 */   private static ImageTypeSpecifier defaultSpecifier = null;
/*     */   
/*     */   private ImageTypeSpecifier getDefaultImageTypeSpecifier() {
/* 371 */     if (defaultSpecifier == null) {
/* 372 */       ColorModel colorModel = ColorModel.getRGBdefault();
/*     */       
/* 374 */       WritableRaster writableRaster = colorModel.createCompatibleWritableRaster(10, 10);
/*     */ 
/*     */       
/* 377 */       BufferedImage bufferedImage = new BufferedImage(colorModel, writableRaster, colorModel.isAlphaPremultiplied(), null);
/*     */ 
/*     */       
/* 380 */       defaultSpecifier = new ImageTypeSpecifier(bufferedImage);
/*     */     } 
/*     */     
/* 383 */     return defaultSpecifier;
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
/*     */   public LinkedHashSet<String> getPlatformMappingsForFlavor(DataFlavor paramDataFlavor) {
/* 395 */     LinkedHashSet<String> linkedHashSet = new LinkedHashSet(1);
/*     */     
/* 397 */     if (paramDataFlavor == null) {
/* 398 */       return linkedHashSet;
/*     */     }
/*     */     
/* 401 */     String str1 = paramDataFlavor.getParameter("charset");
/* 402 */     String str2 = paramDataFlavor.getPrimaryType() + "/" + paramDataFlavor.getSubType();
/* 403 */     String str3 = str2;
/*     */     
/* 405 */     if (str1 != null && DataTransferer.isFlavorCharsetTextType(paramDataFlavor)) {
/* 406 */       str3 = str3 + ";charset=" + str1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 411 */     if (paramDataFlavor.getRepresentationClass() != null && (paramDataFlavor
/* 412 */       .isRepresentationClassInputStream() || paramDataFlavor
/* 413 */       .isRepresentationClassByteBuffer() || byte[].class
/* 414 */       .equals(paramDataFlavor.getRepresentationClass()))) {
/* 415 */       linkedHashSet.add(str3);
/*     */     }
/*     */     
/* 418 */     if (DataFlavor.imageFlavor.equals(paramDataFlavor)) {
/* 419 */       String[] arrayOfString = ImageIO.getWriterMIMETypes();
/* 420 */       if (arrayOfString != null) {
/* 421 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*     */           
/* 423 */           Iterator<ImageWriter> iterator = ImageIO.getImageWritersByMIMEType(arrayOfString[b]);
/*     */           
/* 425 */           while (iterator.hasNext()) {
/* 426 */             ImageWriter imageWriter = iterator.next();
/*     */             
/* 428 */             ImageWriterSpi imageWriterSpi = imageWriter.getOriginatingProvider();
/*     */             
/* 430 */             if (imageWriterSpi != null && imageWriterSpi
/* 431 */               .canEncodeImage(getDefaultImageTypeSpecifier())) {
/* 432 */               linkedHashSet.add(arrayOfString[b]);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 438 */     } else if (DataTransferer.isFlavorCharsetTextType(paramDataFlavor)) {
/*     */ 
/*     */       
/* 441 */       if (DataFlavor.stringFlavor.equals(paramDataFlavor)) {
/* 442 */         str2 = "text/plain";
/*     */       }
/*     */       
/* 445 */       for (String str : DataTransferer.standardEncodings()) {
/* 446 */         if (!str.equals(str1)) {
/* 447 */           linkedHashSet.add(str2 + ";charset=" + str);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 452 */       if (!linkedHashSet.contains(str2)) {
/* 453 */         linkedHashSet.add(str2);
/*     */       }
/*     */     } 
/*     */     
/* 457 */     return linkedHashSet;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XDataTransferer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */