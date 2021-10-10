/*     */ package sun.net.dns;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolverConfigurationImpl
/*     */   extends ResolverConfiguration
/*     */ {
/*  44 */   private static Object lock = new Object();
/*     */ 
/*     */   
/*  47 */   private static long lastRefresh = -1L;
/*     */ 
/*     */   
/*     */   private static final int TIMEOUT = 300000;
/*     */ 
/*     */   
/*     */   private final ResolverConfiguration.Options opts;
/*     */ 
/*     */   
/*     */   private LinkedList<String> searchlist;
/*     */ 
/*     */   
/*     */   private LinkedList<String> nameservers;
/*     */ 
/*     */   
/*     */   private LinkedList<String> resolvconf(String paramString, int paramInt1, int paramInt2) {
/*  63 */     LinkedList<String> linkedList = new LinkedList();
/*     */     
/*     */     try {
/*  66 */       BufferedReader bufferedReader = new BufferedReader(new FileReader("/etc/resolv.conf"));
/*     */       
/*     */       String str;
/*  69 */       while ((str = bufferedReader.readLine()) != null) {
/*  70 */         int i = paramInt1;
/*  71 */         if (str.length() == 0)
/*     */           continue; 
/*  73 */         if (str.charAt(0) == '#' || str.charAt(0) == ';')
/*     */           continue; 
/*  75 */         if (!str.startsWith(paramString))
/*     */           continue; 
/*  77 */         String str1 = str.substring(paramString.length());
/*  78 */         if (str1.length() == 0)
/*     */           continue; 
/*  80 */         if (str1.charAt(0) != ' ' && str1.charAt(0) != '\t')
/*     */           continue; 
/*  82 */         StringTokenizer stringTokenizer = new StringTokenizer(str1, " \t");
/*  83 */         while (stringTokenizer.hasMoreTokens()) {
/*  84 */           String str2 = stringTokenizer.nextToken();
/*  85 */           if (str2.charAt(0) == '#' || str2.charAt(0) == ';') {
/*     */             break;
/*     */           }
/*  88 */           if ("nameserver".equals(paramString) && 
/*  89 */             str2.indexOf(':') >= 0 && str2
/*  90 */             .indexOf('.') < 0 && str2
/*  91 */             .indexOf('[') < 0 && str2
/*  92 */             .indexOf(']') < 0)
/*     */           {
/*  94 */             str2 = "[" + str2 + "]";
/*     */           }
/*     */           
/*  97 */           linkedList.add(str2);
/*  98 */           if (--i == 0) {
/*     */             break;
/*     */           }
/*     */         } 
/* 102 */         if (--paramInt2 == 0) {
/*     */           break;
/*     */         }
/*     */       } 
/* 106 */       bufferedReader.close();
/* 107 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */     
/* 111 */     return linkedList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadConfig() {
/* 121 */     assert Thread.holdsLock(lock);
/*     */ 
/*     */     
/* 124 */     if (lastRefresh >= 0L) {
/* 125 */       long l = System.currentTimeMillis();
/* 126 */       if (l - lastRefresh < 300000L) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 132 */     this
/* 133 */       .nameservers = AccessController.<LinkedList<String>>doPrivileged(new PrivilegedAction<LinkedList<String>>()
/*     */         {
/*     */           
/*     */           public LinkedList<String> run()
/*     */           {
/* 138 */             return ResolverConfigurationImpl.this.resolvconf("nameserver", 1, 5);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 143 */     this.searchlist = getSearchList();
/*     */ 
/*     */     
/* 146 */     lastRefresh = System.currentTimeMillis();
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
/*     */   private LinkedList<String> getSearchList() {
/* 158 */     LinkedList<String> linkedList = AccessController.<LinkedList>doPrivileged((PrivilegedAction)new PrivilegedAction<LinkedList<String>>()
/*     */         {
/*     */ 
/*     */           
/*     */           public LinkedList<String> run()
/*     */           {
/* 164 */             LinkedList<String> linkedList = ResolverConfigurationImpl.this.resolvconf("search", 6, 1);
/* 165 */             if (linkedList.size() > 0) {
/* 166 */               return linkedList;
/*     */             }
/*     */             
/* 169 */             return null;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 174 */     if (linkedList != null) {
/* 175 */       return linkedList;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     String str1 = localDomain0();
/* 184 */     if (str1 != null && str1.length() > 0) {
/* 185 */       linkedList = new LinkedList<>();
/* 186 */       linkedList.add(str1);
/* 187 */       return linkedList;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 192 */     linkedList = AccessController.<LinkedList<String>>doPrivileged(new PrivilegedAction<LinkedList<String>>()
/*     */         {
/*     */           
/*     */           public LinkedList<String> run()
/*     */           {
/* 197 */             LinkedList<String> linkedList = ResolverConfigurationImpl.this.resolvconf("domain", 1, 1);
/* 198 */             if (linkedList.size() > 0) {
/* 199 */               return linkedList;
/*     */             }
/* 201 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 205 */     if (linkedList != null) {
/* 206 */       return linkedList;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     linkedList = new LinkedList<>();
/* 213 */     String str2 = fallbackDomain0();
/* 214 */     if (str2 != null && str2.length() > 0) {
/* 215 */       linkedList.add(str2);
/*     */     }
/*     */     
/* 218 */     return linkedList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ResolverConfigurationImpl() {
/* 225 */     this.opts = new OptionsImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> searchlist() {
/* 230 */     synchronized (lock) {
/* 231 */       loadConfig();
/*     */ 
/*     */       
/* 234 */       return (List<String>)this.searchlist.clone();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> nameservers() {
/* 240 */     synchronized (lock) {
/* 241 */       loadConfig();
/*     */ 
/*     */ 
/*     */       
/* 245 */       return (List<String>)this.nameservers.clone();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ResolverConfiguration.Options options() {
/* 251 */     return this.opts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 262 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 265 */             System.loadLibrary("net");
/* 266 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static native String localDomain0();
/*     */   
/*     */   static native String fallbackDomain0();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/dns/ResolverConfigurationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */