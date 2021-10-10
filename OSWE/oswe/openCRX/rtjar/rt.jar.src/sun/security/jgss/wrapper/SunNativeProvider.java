/*     */ package sun.security.jgss.wrapper;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import java.util.HashMap;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.action.PutAllAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SunNativeProvider
/*     */   extends Provider
/*     */ {
/*     */   private static final long serialVersionUID = -238911724858694204L;
/*     */   private static final String NAME = "SunNativeGSS";
/*     */   private static final String INFO = "Sun Native GSS provider";
/*     */   private static final String MF_CLASS = "sun.security.jgss.wrapper.NativeGSSFactory";
/*     */   private static final String LIB_PROP = "sun.security.jgss.lib";
/*     */   private static final String DEBUG_PROP = "sun.security.nativegss.debug";
/*     */   private static HashMap<String, String> MECH_MAP;
/*  56 */   static final Provider INSTANCE = new SunNativeProvider();
/*     */   
/*     */   static void debug(String paramString) {
/*  59 */     if (DEBUG) {
/*  60 */       if (paramString == null) {
/*  61 */         throw new NullPointerException();
/*     */       }
/*  63 */       System.out.println("SunNativeGSS: " + paramString);
/*     */     } 
/*     */   }
/*     */   static boolean DEBUG;
/*     */   
/*     */   static {
/*  69 */     MECH_MAP = AccessController.<HashMap<String, String>>doPrivileged(new PrivilegedAction<HashMap<String, String>>()
/*     */         {
/*     */           public HashMap<String, String> run()
/*     */           {
/*  73 */             SunNativeProvider.DEBUG = Boolean.parseBoolean(System.getProperty("sun.security.nativegss.debug"));
/*     */             try {
/*  75 */               System.loadLibrary("j2gss");
/*  76 */             } catch (Error error) {
/*  77 */               SunNativeProvider.debug("No j2gss library found!");
/*  78 */               if (SunNativeProvider.DEBUG) error.printStackTrace(); 
/*  79 */               return null;
/*     */             } 
/*  81 */             String[] arrayOfString = new String[0];
/*  82 */             String str = System.getProperty("sun.security.jgss.lib");
/*  83 */             if (str == null || str.trim().equals("")) {
/*  84 */               String str1 = System.getProperty("os.name");
/*  85 */               if (str1.startsWith("SunOS")) {
/*  86 */                 arrayOfString = new String[] { "libgss.so" };
/*  87 */               } else if (str1.startsWith("Linux")) {
/*  88 */                 arrayOfString = new String[] { "libgssapi.so", "libgssapi_krb5.so", "libgssapi_krb5.so.2" };
/*     */ 
/*     */ 
/*     */               
/*     */               }
/*  93 */               else if (str1.contains("OS X")) {
/*  94 */                 arrayOfString = new String[] { "libgssapi_krb5.dylib", "/usr/lib/sasl2/libgssapiv2.2.so" };
/*     */               }
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 100 */               arrayOfString = new String[] { str };
/*     */             } 
/* 102 */             for (String str1 : arrayOfString) {
/* 103 */               if (GSSLibStub.init(str1, SunNativeProvider.DEBUG)) {
/* 104 */                 SunNativeProvider.debug("Loaded GSS library: " + str1);
/* 105 */                 Oid[] arrayOfOid = GSSLibStub.indicateMechs();
/* 106 */                 HashMap<Object, Object> hashMap = new HashMap<>();
/*     */                 
/* 108 */                 for (byte b = 0; b < arrayOfOid.length; b++) {
/* 109 */                   SunNativeProvider.debug("Native MF for " + arrayOfOid[b]);
/* 110 */                   hashMap.put("GssApiMechanism." + arrayOfOid[b], "sun.security.jgss.wrapper.NativeGSSFactory");
/*     */                 } 
/*     */                 
/* 113 */                 return (HashMap)hashMap;
/*     */               } 
/*     */             } 
/* 116 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public SunNativeProvider() {
/* 123 */     super("SunNativeGSS", 1.8D, "Sun Native GSS provider");
/*     */     
/* 125 */     if (MECH_MAP != null)
/* 126 */       AccessController.doPrivileged(new PutAllAction(this, MECH_MAP)); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/wrapper/SunNativeProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */