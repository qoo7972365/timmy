/*     */ package javax.swing.text.html.parser;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javax.swing.text.html.HTMLEditorKit;
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
/*     */ public class ParserDelegator
/*     */   extends HTMLEditorKit.Parser
/*     */   implements Serializable
/*     */ {
/*  50 */   private static final Object DTD_KEY = new Object();
/*     */   
/*     */   protected static void setDefaultDTD() {
/*  53 */     getDefaultDTD();
/*     */   }
/*     */   
/*     */   private static synchronized DTD getDefaultDTD() {
/*  57 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  59 */     DTD dTD = (DTD)appContext.get(DTD_KEY);
/*     */     
/*  61 */     if (dTD == null) {
/*  62 */       DTD dTD1 = null;
/*     */       
/*  64 */       String str = "html32";
/*     */       try {
/*  66 */         dTD1 = DTD.getDTD(str);
/*  67 */       } catch (IOException iOException) {
/*     */         
/*  69 */         System.out.println("Throw an exception: could not get default dtd: " + str);
/*     */       } 
/*  71 */       dTD = createDTD(dTD1, str);
/*     */       
/*  73 */       appContext.put(DTD_KEY, dTD);
/*     */     } 
/*     */     
/*  76 */     return dTD;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static DTD createDTD(DTD paramDTD, String paramString) {
/*  81 */     InputStream inputStream = null;
/*  82 */     boolean bool = true;
/*     */     try {
/*  84 */       String str = paramString + ".bdtd";
/*  85 */       inputStream = getResourceAsStream(str);
/*  86 */       if (inputStream != null) {
/*  87 */         paramDTD.read(new DataInputStream(new BufferedInputStream(inputStream)));
/*  88 */         DTD.putDTDHash(paramString, paramDTD);
/*     */       } 
/*  90 */     } catch (Exception exception) {
/*  91 */       System.out.println(exception);
/*     */     } 
/*  93 */     return paramDTD;
/*     */   }
/*     */ 
/*     */   
/*     */   public ParserDelegator() {
/*  98 */     setDefaultDTD();
/*     */   }
/*     */   
/*     */   public void parse(Reader paramReader, HTMLEditorKit.ParserCallback paramParserCallback, boolean paramBoolean) throws IOException {
/* 102 */     (new DocumentParser(getDefaultDTD())).parse(paramReader, paramParserCallback, paramBoolean);
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
/*     */   static InputStream getResourceAsStream(final String name) {
/* 116 */     return AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>()
/*     */         {
/*     */           public InputStream run() {
/* 119 */             return ParserDelegator.class.getResourceAsStream(name);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 126 */     paramObjectInputStream.defaultReadObject();
/* 127 */     setDefaultDTD();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/parser/ParserDelegator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */