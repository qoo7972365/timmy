/*     */ package sun.applet;
/*     */ 
/*     */ import java.applet.AppletContext;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AppletViewerPanel
/*     */   extends AppletPanel
/*     */ {
/*     */   protected static boolean debug = false;
/*     */   protected URL documentURL;
/*     */   protected URL baseURL;
/*     */   protected Hashtable<String, String> atts;
/*     */   private static final long serialVersionUID = 8890989370785545619L;
/*     */   
/*     */   protected AppletViewerPanel(URL paramURL, Hashtable<String, String> paramHashtable) {
/*  74 */     this.documentURL = paramURL;
/*  75 */     this.atts = paramHashtable;
/*     */     
/*  77 */     String str = getParameter("codebase");
/*  78 */     if (str != null) {
/*  79 */       if (!str.endsWith("/")) {
/*  80 */         str = str + "/";
/*     */       }
/*     */       try {
/*  83 */         this.baseURL = new URL(paramURL, str);
/*  84 */       } catch (MalformedURLException malformedURLException) {}
/*     */     } 
/*     */     
/*  87 */     if (this.baseURL == null) {
/*  88 */       String str1 = paramURL.getFile();
/*  89 */       int i = str1.lastIndexOf('/');
/*  90 */       if (i >= 0 && i < str1.length() - 1) {
/*     */         try {
/*  92 */           this.baseURL = new URL(paramURL, str1.substring(0, i + 1));
/*  93 */         } catch (MalformedURLException malformedURLException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  99 */     if (this.baseURL == null) {
/* 100 */       this.baseURL = paramURL;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameter(String paramString) {
/* 109 */     return this.atts.get(paramString.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getDocumentBase() {
/* 116 */     return this.documentURL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getCodeBase() {
/* 124 */     return this.baseURL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 131 */     String str = getParameter("width");
/* 132 */     if (str != null) {
/* 133 */       return Integer.valueOf(str).intValue();
/*     */     }
/* 135 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 143 */     String str = getParameter("height");
/* 144 */     if (str != null) {
/* 145 */       return Integer.valueOf(str).intValue();
/*     */     }
/* 147 */     return 0;
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
/*     */   public boolean hasInitialFocus() {
/* 159 */     if (isJDK11Applet() || isJDK12Applet()) {
/* 160 */       return false;
/*     */     }
/* 162 */     String str = getParameter("initial_focus");
/*     */     
/* 164 */     if (str != null)
/*     */     {
/* 166 */       if (str.toLowerCase().equals("false")) {
/* 167 */         return false;
/*     */       }
/*     */     }
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCode() {
/* 177 */     return getParameter("code");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJarFiles() {
/* 186 */     return getParameter("archive");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSerializedObject() {
/* 193 */     return getParameter("object");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AppletContext getAppletContext() {
/* 202 */     return (AppletContext)getParent();
/*     */   }
/*     */   
/*     */   protected static void debug(String paramString) {
/* 206 */     if (debug)
/* 207 */       System.err.println("AppletViewerPanel:::" + paramString); 
/*     */   }
/*     */   
/*     */   protected static void debug(String paramString, Throwable paramThrowable) {
/* 211 */     if (debug) {
/* 212 */       paramThrowable.printStackTrace();
/* 213 */       debug(paramString);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletViewerPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */