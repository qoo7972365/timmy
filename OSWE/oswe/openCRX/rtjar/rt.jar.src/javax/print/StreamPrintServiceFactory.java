/*     */ package javax.print;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StreamPrintServiceFactory
/*     */ {
/*     */   static class Services
/*     */   {
/*  62 */     private ArrayList listOfFactories = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Services getServices() {
/*  67 */     Services services = (Services)AppContext.getAppContext().get(Services.class);
/*  68 */     if (services == null) {
/*  69 */       services = new Services();
/*  70 */       AppContext.getAppContext().put(Services.class, services);
/*     */     } 
/*  72 */     return services;
/*     */   }
/*     */   
/*     */   private static ArrayList getListOfFactories() {
/*  76 */     return (getServices()).listOfFactories;
/*     */   }
/*     */   
/*     */   private static ArrayList initListOfFactories() {
/*  80 */     ArrayList arrayList = new ArrayList();
/*  81 */     (getServices()).listOfFactories = arrayList;
/*  82 */     return arrayList;
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
/*     */   public static StreamPrintServiceFactory[] lookupStreamPrintServiceFactories(DocFlavor paramDocFlavor, String paramString) {
/* 111 */     ArrayList arrayList = getFactories(paramDocFlavor, paramString);
/* 112 */     return (StreamPrintServiceFactory[])arrayList
/* 113 */       .toArray((Object[])new StreamPrintServiceFactory[arrayList.size()]);
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
/*     */   private static ArrayList getAllFactories() {
/* 166 */     synchronized (StreamPrintServiceFactory.class) {
/*     */       
/* 168 */       ArrayList arrayList = getListOfFactories();
/* 169 */       if (arrayList != null) {
/* 170 */         return arrayList;
/*     */       }
/* 172 */       arrayList = initListOfFactories();
/*     */ 
/*     */       
/*     */       try {
/* 176 */         AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */             {
/*     */               
/*     */               public Object run()
/*     */               {
/* 181 */                 Iterator iterator = ServiceLoader.<StreamPrintServiceFactory>load(StreamPrintServiceFactory.class).iterator();
/* 182 */                 ArrayList arrayList = StreamPrintServiceFactory.getListOfFactories();
/* 183 */                 while (iterator.hasNext()) {
/*     */                   try {
/* 185 */                     arrayList.add(iterator.next());
/* 186 */                   } catch (ServiceConfigurationError serviceConfigurationError) {
/*     */                     
/* 188 */                     if (System.getSecurityManager() != null) {
/* 189 */                       serviceConfigurationError.printStackTrace(); continue;
/*     */                     } 
/* 191 */                     throw serviceConfigurationError;
/*     */                   } 
/*     */                 } 
/*     */                 
/* 195 */                 return null;
/*     */               }
/*     */             });
/* 198 */       } catch (PrivilegedActionException privilegedActionException) {}
/*     */       
/* 200 */       return arrayList;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isMember(DocFlavor paramDocFlavor, DocFlavor[] paramArrayOfDocFlavor) {
/* 205 */     for (byte b = 0; b < paramArrayOfDocFlavor.length; b++) {
/* 206 */       if (paramDocFlavor.equals(paramArrayOfDocFlavor[b])) {
/* 207 */         return true;
/*     */       }
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ArrayList getFactories(DocFlavor paramDocFlavor, String paramString) {
/* 215 */     if (paramDocFlavor == null && paramString == null) {
/* 216 */       return getAllFactories();
/*     */     }
/*     */     
/* 219 */     ArrayList<StreamPrintServiceFactory> arrayList = new ArrayList();
/* 220 */     Iterator<StreamPrintServiceFactory> iterator = getAllFactories().iterator();
/* 221 */     while (iterator.hasNext()) {
/*     */       
/* 223 */       StreamPrintServiceFactory streamPrintServiceFactory = iterator.next();
/* 224 */       if ((paramString == null || paramString
/* 225 */         .equalsIgnoreCase(streamPrintServiceFactory.getOutputFormat())) && (paramDocFlavor == null || 
/*     */         
/* 227 */         isMember(paramDocFlavor, streamPrintServiceFactory.getSupportedDocFlavors()))) {
/* 228 */         arrayList.add(streamPrintServiceFactory);
/*     */       }
/*     */     } 
/*     */     
/* 232 */     return arrayList;
/*     */   }
/*     */   
/*     */   public abstract String getOutputFormat();
/*     */   
/*     */   public abstract DocFlavor[] getSupportedDocFlavors();
/*     */   
/*     */   public abstract StreamPrintService getPrintService(OutputStream paramOutputStream);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/StreamPrintServiceFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */