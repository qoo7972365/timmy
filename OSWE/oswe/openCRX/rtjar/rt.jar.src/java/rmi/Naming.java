/*     */ package java.rmi;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.rmi.registry.LocateRegistry;
/*     */ import java.rmi.registry.Registry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Naming
/*     */ {
/*     */   public static Remote lookup(String paramString) throws NotBoundException, MalformedURLException, RemoteException {
/*  96 */     ParsedNamingURL parsedNamingURL = parseURL(paramString);
/*  97 */     Registry registry = getRegistry(parsedNamingURL);
/*     */     
/*  99 */     if (parsedNamingURL.name == null)
/* 100 */       return registry; 
/* 101 */     return registry.lookup(parsedNamingURL.name);
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
/*     */   public static void bind(String paramString, Remote paramRemote) throws AlreadyBoundException, MalformedURLException, RemoteException {
/* 122 */     ParsedNamingURL parsedNamingURL = parseURL(paramString);
/* 123 */     Registry registry = getRegistry(parsedNamingURL);
/*     */     
/* 125 */     if (paramRemote == null) {
/* 126 */       throw new NullPointerException("cannot bind to null");
/*     */     }
/* 128 */     registry.bind(parsedNamingURL.name, paramRemote);
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
/*     */   public static void unbind(String paramString) throws RemoteException, NotBoundException, MalformedURLException {
/* 149 */     ParsedNamingURL parsedNamingURL = parseURL(paramString);
/* 150 */     Registry registry = getRegistry(parsedNamingURL);
/*     */     
/* 152 */     registry.unbind(parsedNamingURL.name);
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
/*     */   public static void rebind(String paramString, Remote paramRemote) throws RemoteException, MalformedURLException {
/* 171 */     ParsedNamingURL parsedNamingURL = parseURL(paramString);
/* 172 */     Registry registry = getRegistry(parsedNamingURL);
/*     */     
/* 174 */     if (paramRemote == null) {
/* 175 */       throw new NullPointerException("cannot bind to null");
/*     */     }
/* 177 */     registry.rebind(parsedNamingURL.name, paramRemote);
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
/*     */   public static String[] list(String paramString) throws RemoteException, MalformedURLException {
/* 198 */     ParsedNamingURL parsedNamingURL = parseURL(paramString);
/* 199 */     Registry registry = getRegistry(parsedNamingURL);
/*     */     
/* 201 */     String str = "";
/* 202 */     if (parsedNamingURL.port > 0 || !parsedNamingURL.host.equals(""))
/* 203 */       str = str + "//" + parsedNamingURL.host; 
/* 204 */     if (parsedNamingURL.port > 0)
/* 205 */       str = str + ":" + parsedNamingURL.port; 
/* 206 */     str = str + "/";
/*     */     
/* 208 */     String[] arrayOfString = registry.list();
/* 209 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 210 */       arrayOfString[b] = str + arrayOfString[b];
/*     */     }
/* 212 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Registry getRegistry(ParsedNamingURL paramParsedNamingURL) throws RemoteException {
/* 221 */     return LocateRegistry.getRegistry(paramParsedNamingURL.host, paramParsedNamingURL.port);
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
/*     */   private static ParsedNamingURL parseURL(String paramString) throws MalformedURLException {
/*     */     try {
/* 237 */       return intParseURL(paramString);
/* 238 */     } catch (URISyntaxException uRISyntaxException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 243 */       MalformedURLException malformedURLException = new MalformedURLException("invalid URL String: " + paramString);
/*     */       
/* 245 */       malformedURLException.initCause(uRISyntaxException);
/* 246 */       int i = paramString.indexOf(':');
/* 247 */       int j = paramString.indexOf("//:");
/* 248 */       if (j < 0) {
/* 249 */         throw malformedURLException;
/*     */       }
/* 251 */       if (j == 0 || (i > 0 && j == i + 1)) {
/*     */ 
/*     */         
/* 254 */         int k = j + 2;
/*     */ 
/*     */         
/* 257 */         String str = paramString.substring(0, k) + "localhost" + paramString.substring(k);
/*     */         try {
/* 259 */           return intParseURL(str);
/* 260 */         } catch (URISyntaxException uRISyntaxException1) {
/* 261 */           throw malformedURLException;
/* 262 */         } catch (MalformedURLException malformedURLException1) {
/* 263 */           throw malformedURLException1;
/*     */         } 
/*     */       } 
/* 266 */       throw malformedURLException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ParsedNamingURL intParseURL(String paramString) throws MalformedURLException, URISyntaxException {
/* 273 */     URI uRI = new URI(paramString);
/* 274 */     if (uRI.isOpaque()) {
/* 275 */       throw new MalformedURLException("not a hierarchical URL: " + paramString);
/*     */     }
/*     */     
/* 278 */     if (uRI.getFragment() != null) {
/* 279 */       throw new MalformedURLException("invalid character, '#', in URL name: " + paramString);
/*     */     }
/* 281 */     if (uRI.getQuery() != null) {
/* 282 */       throw new MalformedURLException("invalid character, '?', in URL name: " + paramString);
/*     */     }
/* 284 */     if (uRI.getUserInfo() != null) {
/* 285 */       throw new MalformedURLException("invalid character, '@', in URL host: " + paramString);
/*     */     }
/*     */     
/* 288 */     String str1 = uRI.getScheme();
/* 289 */     if (str1 != null && !str1.equals("rmi")) {
/* 290 */       throw new MalformedURLException("invalid URL scheme: " + paramString);
/*     */     }
/*     */     
/* 293 */     String str2 = uRI.getPath();
/* 294 */     if (str2 != null) {
/* 295 */       if (str2.startsWith("/")) {
/* 296 */         str2 = str2.substring(1);
/*     */       }
/* 298 */       if (str2.length() == 0) {
/* 299 */         str2 = null;
/*     */       }
/*     */     } 
/*     */     
/* 303 */     String str3 = uRI.getHost();
/* 304 */     if (str3 == null) {
/* 305 */       str3 = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 313 */         uRI.parseServerAuthority();
/* 314 */       } catch (URISyntaxException uRISyntaxException) {
/*     */         
/* 316 */         String str = uRI.getAuthority();
/* 317 */         if (str != null && str.startsWith(":")) {
/*     */           
/* 319 */           str = "localhost" + str;
/*     */           try {
/* 321 */             uRI = new URI(null, str, null, null, null);
/*     */ 
/*     */             
/* 324 */             uRI.parseServerAuthority();
/* 325 */           } catch (URISyntaxException uRISyntaxException1) {
/* 326 */             throw new MalformedURLException("invalid authority: " + paramString);
/*     */           } 
/*     */         } else {
/*     */           
/* 330 */           throw new MalformedURLException("invalid authority: " + paramString);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 335 */     int i = uRI.getPort();
/* 336 */     if (i == -1) {
/* 337 */       i = 1099;
/*     */     }
/* 339 */     return new ParsedNamingURL(str3, i, str2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ParsedNamingURL
/*     */   {
/*     */     String host;
/*     */     
/*     */     int port;
/*     */     String name;
/*     */     
/*     */     ParsedNamingURL(String param1String1, int param1Int, String param1String2) {
/* 351 */       this.host = param1String1;
/* 352 */       this.port = param1Int;
/* 353 */       this.name = param1String2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/Naming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */