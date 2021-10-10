/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collection;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SCDynamicStoreConfig
/*     */ {
/*  37 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */   
/*     */   static {
/*  40 */     boolean bool = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run() {
/*  43 */             String str = System.getProperty("os.name");
/*  44 */             if (str.contains("OS X")) {
/*  45 */               System.loadLibrary("osx");
/*  46 */               return Boolean.valueOf(true);
/*     */             } 
/*  48 */             return Boolean.valueOf(false);
/*     */           }
/*     */         })).booleanValue();
/*  51 */     if (bool) installNotificationCallback();
/*     */   
/*     */   }
/*     */   
/*     */   private static Vector<String> unwrapHost(Collection<Hashtable<String, String>> paramCollection) {
/*  56 */     Vector<String> vector = new Vector();
/*  57 */     for (Hashtable<String, String> hashtable : paramCollection) {
/*  58 */       vector.add(hashtable.get("host"));
/*     */     }
/*  60 */     return vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Hashtable<String, Object> convertRealmConfigs(Hashtable<String, ?> paramHashtable) {
/*  71 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*     */     
/*  73 */     for (String str : paramHashtable.keySet()) {
/*     */ 
/*     */       
/*  76 */       Hashtable hashtable1 = (Hashtable)paramHashtable.get(str);
/*  77 */       Hashtable<Object, Object> hashtable2 = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  82 */       Collection<Hashtable<String, String>> collection1 = (Collection)hashtable1.get("kdc");
/*  83 */       if (collection1 != null) hashtable2.put("kdc", unwrapHost(collection1));
/*     */ 
/*     */ 
/*     */       
/*  87 */       Collection<Hashtable<String, String>> collection2 = (Collection)hashtable1.get("kadmin");
/*  88 */       if (collection2 != null) hashtable2.put("admin_server", unwrapHost(collection2));
/*     */ 
/*     */       
/*  91 */       hashtable.put(str, hashtable2);
/*     */     } 
/*     */     
/*  94 */     return (Hashtable)hashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Hashtable<String, Object> getConfig() throws IOException {
/* 105 */     Hashtable<String, Object> hashtable = getKerberosConfig();
/* 106 */     if (hashtable == null) {
/* 107 */       throw new IOException("Could not load configuration from SCDynamicStore");
/*     */     }
/*     */     
/* 110 */     if (DEBUG) System.out.println("Raw map from JNI: " + hashtable); 
/* 111 */     return convertNativeConfig(hashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Hashtable<String, Object> convertNativeConfig(Hashtable<String, Object> paramHashtable) {
/* 119 */     Hashtable<String, ?> hashtable = (Hashtable)paramHashtable.get("realms");
/* 120 */     if (hashtable != null) {
/* 121 */       paramHashtable.remove("realms");
/* 122 */       Hashtable<String, Object> hashtable1 = convertRealmConfigs(hashtable);
/* 123 */       paramHashtable.put("realms", hashtable1);
/*     */     } 
/* 125 */     WrapAllStringInVector(paramHashtable);
/* 126 */     if (DEBUG) System.out.println("stanzaTable : " + paramHashtable); 
/* 127 */     return paramHashtable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void WrapAllStringInVector(Hashtable<String, Object> paramHashtable) {
/* 133 */     for (String str : paramHashtable.keySet()) {
/* 134 */       Object object = paramHashtable.get(str);
/* 135 */       if (object instanceof Hashtable) {
/* 136 */         WrapAllStringInVector((Hashtable<String, Object>)object); continue;
/* 137 */       }  if (object instanceof String) {
/* 138 */         Vector<String> vector = new Vector();
/* 139 */         vector.add((String)object);
/* 140 */         paramHashtable.put(str, vector);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static native void installNotificationCallback();
/*     */   
/*     */   private static native Hashtable<String, Object> getKerberosConfig();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/SCDynamicStoreConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */