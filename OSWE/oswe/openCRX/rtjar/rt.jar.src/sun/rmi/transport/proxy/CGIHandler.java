/*     */ package sun.rmi.transport.proxy;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CGIHandler
/*     */ {
/*     */   static int ContentLength;
/*     */   static String QueryString;
/*     */   static String RequestMethod;
/*     */   static String ServerName;
/*     */   static int ServerPort;
/*     */   
/*     */   static {
/* 102 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run()
/*     */           {
/* 106 */             CGIHandler.ContentLength = Integer.getInteger("CONTENT_LENGTH", 0).intValue();
/* 107 */             CGIHandler.QueryString = System.getProperty("QUERY_STRING", "");
/* 108 */             CGIHandler.RequestMethod = System.getProperty("REQUEST_METHOD", "");
/* 109 */             CGIHandler.ServerName = System.getProperty("SERVER_NAME", "");
/* 110 */             CGIHandler.ServerPort = Integer.getInteger("SERVER_PORT", 0).intValue();
/* 111 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/* 117 */   private static CGICommandHandler[] commands = new CGICommandHandler[] { new CGIForwardCommand(), new CGIGethostnameCommand(), new CGIPingCommand(), new CGITryHostnameCommand() };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   private static Hashtable<String, CGICommandHandler> commandLookup = new Hashtable<>(); static {
/* 128 */     for (byte b = 0; b < commands.length; b++) {
/* 129 */       commandLookup.put(commands[b].getName(), commands[b]);
/*     */     }
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
/*     */   public static void main(String[] paramArrayOfString) {
/*     */     try {
/*     */       String str1, str2;
/* 144 */       int i = QueryString.indexOf("=");
/* 145 */       if (i == -1) {
/* 146 */         str1 = QueryString;
/* 147 */         str2 = "";
/*     */       } else {
/*     */         
/* 150 */         str1 = QueryString.substring(0, i);
/* 151 */         str2 = QueryString.substring(i + 1);
/*     */       } 
/*     */       
/* 154 */       CGICommandHandler cGICommandHandler = commandLookup.get(str1);
/* 155 */       if (cGICommandHandler != null)
/*     */       { try {
/* 157 */           cGICommandHandler.execute(str2);
/* 158 */         } catch (CGIClientException cGIClientException) {
/* 159 */           cGIClientException.printStackTrace();
/* 160 */           returnClientError(cGIClientException.getMessage());
/* 161 */         } catch (CGIServerException cGIServerException) {
/* 162 */           cGIServerException.printStackTrace();
/* 163 */           returnServerError(cGIServerException.getMessage());
/*     */         }  }
/*     */       else
/* 166 */       { returnClientError("invalid command."); } 
/* 167 */     } catch (Exception exception) {
/* 168 */       exception.printStackTrace();
/* 169 */       returnServerError("internal error: " + exception.getMessage());
/*     */     } 
/* 171 */     System.exit(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void returnClientError(String paramString) {
/* 180 */     System.out.println("Status: 400 Bad Request: " + paramString);
/* 181 */     System.out.println("Content-type: text/html");
/* 182 */     System.out.println("");
/* 183 */     System.out.println("<HTML><HEAD><TITLE>Java RMI Client Error</TITLE></HEAD><BODY>");
/*     */ 
/*     */ 
/*     */     
/* 187 */     System.out.println("<H1>Java RMI Client Error</H1>");
/* 188 */     System.out.println("");
/* 189 */     System.out.println(paramString);
/* 190 */     System.out.println("</BODY></HTML>");
/* 191 */     System.exit(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void returnServerError(String paramString) {
/* 200 */     System.out.println("Status: 500 Server Error: " + paramString);
/* 201 */     System.out.println("Content-type: text/html");
/* 202 */     System.out.println("");
/* 203 */     System.out.println("<HTML><HEAD><TITLE>Java RMI Server Error</TITLE></HEAD><BODY>");
/*     */ 
/*     */ 
/*     */     
/* 207 */     System.out.println("<H1>Java RMI Server Error</H1>");
/* 208 */     System.out.println("");
/* 209 */     System.out.println(paramString);
/* 210 */     System.out.println("</BODY></HTML>");
/* 211 */     System.exit(1);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/proxy/CGIHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */