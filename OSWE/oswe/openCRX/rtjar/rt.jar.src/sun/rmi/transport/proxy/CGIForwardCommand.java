/*     */ package sun.rmi.transport.proxy;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CGIForwardCommand
/*     */   implements CGICommandHandler
/*     */ {
/*     */   public String getName() {
/* 222 */     return "forward";
/*     */   }
/*     */ 
/*     */   
/*     */   private String getLine(DataInputStream paramDataInputStream) throws IOException {
/* 227 */     return paramDataInputStream.readLine(); } public void execute(String paramString) throws CGIClientException, CGIServerException {
/*     */     int i;
/*     */     Socket socket;
/*     */     DataInputStream dataInputStream2;
/*     */     String str2;
/* 232 */     if (!CGIHandler.RequestMethod.equals("POST")) {
/* 233 */       throw new CGIClientException("can only forward POST requests");
/*     */     }
/*     */     
/*     */     try {
/* 237 */       i = Integer.parseInt(paramString);
/* 238 */     } catch (NumberFormatException numberFormatException) {
/* 239 */       throw new CGIClientException("invalid port number.", numberFormatException);
/*     */     } 
/* 241 */     if (i <= 0 || i > 65535)
/* 242 */       throw new CGIClientException("invalid port: " + i); 
/* 243 */     if (i < 1024) {
/* 244 */       throw new CGIClientException("permission denied for port: " + i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 250 */       socket = new Socket(InetAddress.getLocalHost(), i);
/* 251 */     } catch (IOException iOException) {
/* 252 */       throw new CGIServerException("could not connect to local port", iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     DataInputStream dataInputStream1 = new DataInputStream(System.in);
/* 259 */     byte[] arrayOfByte = new byte[CGIHandler.ContentLength];
/*     */     try {
/* 261 */       dataInputStream1.readFully(arrayOfByte);
/* 262 */     } catch (EOFException eOFException) {
/* 263 */       throw new CGIClientException("unexpected EOF reading request body", eOFException);
/* 264 */     } catch (IOException iOException) {
/* 265 */       throw new CGIClientException("error reading request body", iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 273 */       DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
/* 274 */       dataOutputStream.writeBytes("POST / HTTP/1.0\r\n");
/* 275 */       dataOutputStream.writeBytes("Content-length: " + CGIHandler.ContentLength + "\r\n\r\n");
/*     */       
/* 277 */       dataOutputStream.write(arrayOfByte);
/* 278 */       dataOutputStream.flush();
/* 279 */     } catch (IOException iOException) {
/* 280 */       throw new CGIServerException("error writing to server", iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 288 */       dataInputStream2 = new DataInputStream(socket.getInputStream());
/* 289 */     } catch (IOException iOException) {
/* 290 */       throw new CGIServerException("error reading from server", iOException);
/*     */     } 
/* 292 */     String str1 = "Content-length:".toLowerCase();
/* 293 */     boolean bool = false;
/*     */     
/* 295 */     int j = -1;
/*     */     do {
/*     */       try {
/* 298 */         str2 = getLine(dataInputStream2);
/* 299 */       } catch (IOException iOException) {
/* 300 */         throw new CGIServerException("error reading from server", iOException);
/*     */       } 
/* 302 */       if (str2 == null) {
/* 303 */         throw new CGIServerException("unexpected EOF reading server response");
/*     */       }
/*     */       
/* 306 */       if (!str2.toLowerCase().startsWith(str1))
/* 307 */         continue;  if (bool) {
/* 308 */         throw new CGIServerException("Multiple Content-length entries found.");
/*     */       }
/*     */ 
/*     */       
/* 312 */       j = Integer.parseInt(str2.substring(str1.length()).trim());
/* 313 */       bool = true;
/*     */     
/*     */     }
/* 316 */     while (str2.length() != 0 && str2
/* 317 */       .charAt(0) != '\r' && str2.charAt(0) != '\n');
/*     */     
/* 319 */     if (!bool || j < 0) {
/* 320 */       throw new CGIServerException("missing or invalid content length in server response");
/*     */     }
/* 322 */     arrayOfByte = new byte[j];
/*     */     try {
/* 324 */       dataInputStream2.readFully(arrayOfByte);
/* 325 */     } catch (EOFException eOFException) {
/* 326 */       throw new CGIServerException("unexpected EOF reading server response", eOFException);
/*     */     }
/* 328 */     catch (IOException iOException) {
/* 329 */       throw new CGIServerException("error reading from server", iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 335 */     System.out.println("Status: 200 OK");
/* 336 */     System.out.println("Content-type: application/octet-stream");
/* 337 */     System.out.println("");
/*     */     try {
/* 339 */       System.out.write(arrayOfByte);
/* 340 */     } catch (IOException iOException) {
/* 341 */       throw new CGIServerException("error writing response", iOException);
/*     */     } 
/* 343 */     System.out.flush();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/proxy/CGIForwardCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */