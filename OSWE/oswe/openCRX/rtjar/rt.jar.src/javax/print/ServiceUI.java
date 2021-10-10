/*     */ package javax.print;
/*     */ 
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Rectangle;
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.AttributeSet;
/*     */ import javax.print.attribute.PrintRequestAttributeSet;
/*     */ import javax.print.attribute.standard.Destination;
/*     */ import javax.print.attribute.standard.Fidelity;
/*     */ import sun.print.ServiceDialog;
/*     */ import sun.print.SunAlternateMedia;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServiceUI
/*     */ {
/*     */   public static PrintService printDialog(GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, PrintService[] paramArrayOfPrintService, PrintService paramPrintService, DocFlavor paramDocFlavor, PrintRequestAttributeSet paramPrintRequestAttributeSet) throws HeadlessException {
/*     */     ServiceDialog serviceDialog;
/* 162 */     byte b = -1;
/*     */     
/* 164 */     if (GraphicsEnvironment.isHeadless())
/* 165 */       throw new HeadlessException(); 
/* 166 */     if (paramArrayOfPrintService == null || paramArrayOfPrintService.length == 0) {
/* 167 */       throw new IllegalArgumentException("services must be non-null and non-empty");
/*     */     }
/* 169 */     if (paramPrintRequestAttributeSet == null) {
/* 170 */       throw new IllegalArgumentException("attributes must be non-null");
/*     */     }
/*     */     
/* 173 */     if (paramPrintService != null) {
/* 174 */       for (byte b2 = 0; b2 < paramArrayOfPrintService.length; b2++) {
/* 175 */         if (paramArrayOfPrintService[b2].equals(paramPrintService)) {
/* 176 */           b = b2;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 181 */       if (b < 0) {
/* 182 */         throw new IllegalArgumentException("services must contain defaultService");
/*     */       }
/*     */     } else {
/*     */       
/* 186 */       b = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 191 */     Frame frame = null;
/*     */ 
/*     */ 
/*     */     
/* 195 */     Rectangle rectangle1 = (paramGraphicsConfiguration == null) ? GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds() : paramGraphicsConfiguration.getBounds();
/*     */ 
/*     */     
/* 198 */     if (frame instanceof Frame) {
/* 199 */       serviceDialog = new ServiceDialog(paramGraphicsConfiguration, paramInt1 + rectangle1.x, paramInt2 + rectangle1.y, paramArrayOfPrintService, b, paramDocFlavor, paramPrintRequestAttributeSet, frame);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 206 */       serviceDialog = new ServiceDialog(paramGraphicsConfiguration, paramInt1 + rectangle1.x, paramInt2 + rectangle1.y, paramArrayOfPrintService, b, paramDocFlavor, paramPrintRequestAttributeSet, (Dialog)frame);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     Rectangle rectangle2 = serviceDialog.getBounds();
/*     */ 
/*     */     
/* 216 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 217 */     GraphicsDevice[] arrayOfGraphicsDevice = graphicsEnvironment.getScreenDevices();
/* 218 */     for (byte b1 = 0; b1 < arrayOfGraphicsDevice.length; b1++)
/*     */     {
/* 220 */       rectangle1 = rectangle1.union(arrayOfGraphicsDevice[b1].getDefaultConfiguration().getBounds());
/*     */     }
/*     */ 
/*     */     
/* 224 */     if (!rectangle1.contains(rectangle2))
/*     */     {
/* 226 */       serviceDialog.setLocationRelativeTo(frame);
/*     */     }
/* 228 */     serviceDialog.show();
/*     */     
/* 230 */     if (serviceDialog.getStatus() == 1) {
/* 231 */       PrintRequestAttributeSet printRequestAttributeSet = serviceDialog.getAttributes();
/* 232 */       Class<Destination> clazz = Destination.class;
/* 233 */       Class<SunAlternateMedia> clazz1 = SunAlternateMedia.class;
/* 234 */       Class<Fidelity> clazz2 = Fidelity.class;
/*     */       
/* 236 */       if (paramPrintRequestAttributeSet.containsKey(clazz) && 
/* 237 */         !printRequestAttributeSet.containsKey(clazz))
/*     */       {
/* 239 */         paramPrintRequestAttributeSet.remove(clazz);
/*     */       }
/*     */       
/* 242 */       if (paramPrintRequestAttributeSet.containsKey(clazz1) && 
/* 243 */         !printRequestAttributeSet.containsKey(clazz1))
/*     */       {
/* 245 */         paramPrintRequestAttributeSet.remove(clazz1);
/*     */       }
/*     */       
/* 248 */       paramPrintRequestAttributeSet.addAll(printRequestAttributeSet);
/*     */       
/* 250 */       Fidelity fidelity = (Fidelity)paramPrintRequestAttributeSet.get(clazz2);
/* 251 */       if (fidelity != null && 
/* 252 */         fidelity == Fidelity.FIDELITY_TRUE) {
/* 253 */         removeUnsupportedAttributes(serviceDialog.getPrintService(), paramDocFlavor, paramPrintRequestAttributeSet);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 259 */     return serviceDialog.getPrintService();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void removeUnsupportedAttributes(PrintService paramPrintService, DocFlavor paramDocFlavor, AttributeSet paramAttributeSet) {
/* 312 */     AttributeSet attributeSet = paramPrintService.getUnsupportedAttributes(paramDocFlavor, paramAttributeSet);
/*     */ 
/*     */     
/* 315 */     if (attributeSet != null) {
/* 316 */       Attribute[] arrayOfAttribute = attributeSet.toArray();
/*     */       
/* 318 */       for (byte b = 0; b < arrayOfAttribute.length; b++) {
/* 319 */         Class<? extends Attribute> clazz = arrayOfAttribute[b].getCategory();
/*     */         
/* 321 */         if (paramPrintService.isAttributeCategorySupported(clazz)) {
/*     */           
/* 323 */           Attribute attribute = (Attribute)paramPrintService.getDefaultAttributeValue(clazz);
/*     */           
/* 325 */           if (attribute != null) {
/* 326 */             paramAttributeSet.add(attribute);
/*     */           } else {
/* 328 */             paramAttributeSet.remove(clazz);
/*     */           } 
/*     */         } else {
/* 331 */           paramAttributeSet.remove(clazz);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/ServiceUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */