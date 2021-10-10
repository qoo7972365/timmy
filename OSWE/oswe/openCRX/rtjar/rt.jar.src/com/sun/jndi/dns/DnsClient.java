/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.naming.CommunicationException;
/*     */ import javax.naming.ConfigurationException;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.OperationNotSupportedException;
/*     */ import javax.naming.ServiceUnavailableException;
/*     */ import sun.security.jca.JCAUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DnsClient
/*     */ {
/*     */   private static final int IDENT_OFFSET = 0;
/*     */   private static final int FLAGS_OFFSET = 2;
/*     */   private static final int NUMQ_OFFSET = 4;
/*     */   private static final int NUMANS_OFFSET = 6;
/*     */   private static final int NUMAUTH_OFFSET = 8;
/*     */   private static final int NUMADD_OFFSET = 10;
/*     */   private static final int DNS_HDR_SIZE = 12;
/*     */   private static final int NO_ERROR = 0;
/*     */   private static final int FORMAT_ERROR = 1;
/*     */   private static final int SERVER_FAILURE = 2;
/*     */   private static final int NAME_ERROR = 3;
/*     */   private static final int NOT_IMPL = 4;
/*     */   private static final int REFUSED = 5;
/*  71 */   private static final String[] rcodeDescription = new String[] { "No error", "DNS format error", "DNS server failure", "DNS name not found", "DNS operation not supported", "DNS service refused" };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_PORT = 53;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TRANSACTION_ID_BOUND = 65536;
/*     */ 
/*     */   
/*  82 */   private static final SecureRandom random = JCAUtil.getSecureRandom();
/*     */   
/*     */   private InetAddress[] servers;
/*     */   private int[] serverPorts;
/*     */   private int timeout;
/*     */   private int retries;
/*  88 */   private final Object udpSocketLock = new Object();
/*  89 */   private static final DNSDatagramSocketFactory factory = new DNSDatagramSocketFactory(random);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<Integer, ResourceRecord> reqs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<Integer, byte[]> resps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object queuesLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean debug = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DatagramSocket getDatagramSocket() throws NamingException {
/*     */     try {
/* 141 */       return factory.open();
/* 142 */     } catch (SocketException socketException) {
/* 143 */       ConfigurationException configurationException = new ConfigurationException();
/* 144 */       configurationException.setRootCause(socketException);
/* 145 */       throw configurationException;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() {
/* 150 */     close();
/*     */   }
/*     */   
/*     */   public DnsClient(String[] paramArrayOfString, int paramInt1, int paramInt2) throws NamingException {
/* 154 */     this.queuesLock = new Object(); this.timeout = paramInt1; this.retries = paramInt2; this.servers = new InetAddress[paramArrayOfString.length]; this.serverPorts = new int[paramArrayOfString.length]; for (byte b = 0; b < paramArrayOfString.length; b++) { int i = paramArrayOfString[b].indexOf(':', paramArrayOfString[b].indexOf(']') + 1); this.serverPorts[b] = (i < 0) ? 53 : Integer.parseInt(paramArrayOfString[b].substring(i + 1)); String str = (i < 0) ? paramArrayOfString[b] : paramArrayOfString[b].substring(0, i); try { this.servers[b] = InetAddress.getByName(str); }
/*     */       catch (UnknownHostException unknownHostException) { ConfigurationException configurationException = new ConfigurationException("Unknown DNS server: " + str); configurationException.setRootCause(unknownHostException); throw configurationException; }
/*     */        }
/* 157 */      this.reqs = Collections.synchronizedMap(new HashMap<>()); this.resps = Collections.synchronizedMap(new HashMap<>()); } public void close() { synchronized (this.queuesLock) {
/* 158 */       this.reqs.clear();
/* 159 */       this.resps.clear();
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ResourceRecords query(DnsName paramDnsName, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) throws NamingException {
/*     */     int i;
/*     */     Packet packet;
/*     */     ResourceRecord resourceRecord;
/*     */     do {
/* 178 */       i = random.nextInt(65536);
/* 179 */       packet = makeQueryPacket(paramDnsName, i, paramInt1, paramInt2, paramBoolean1);
/*     */ 
/*     */       
/* 182 */       resourceRecord = this.reqs.putIfAbsent(Integer.valueOf(i), new ResourceRecord(packet.getData(), packet
/* 183 */             .length(), 12, true, false));
/*     */     }
/* 185 */     while (resourceRecord != null);
/*     */     
/* 187 */     NamingException namingException = null;
/* 188 */     boolean[] arrayOfBoolean = new boolean[this.servers.length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 196 */       for (byte b = 0; b < this.retries; b++) {
/*     */ 
/*     */         
/* 199 */         for (byte b1 = 0; b1 < this.servers.length; b1++) {
/* 200 */           IOException iOException; CommunicationException communicationException1; if (arrayOfBoolean[b1]) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 210 */             byte[] arrayOfByte = null;
/* 211 */             arrayOfByte = doUdpQuery(packet, this.servers[b1], this.serverPorts[b1], b, i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 219 */             if (arrayOfByte == null) {
/* 220 */               if (this.resps.size() > 0) {
/* 221 */                 arrayOfByte = lookupResponse(Integer.valueOf(i));
/*     */               }
/* 223 */               if (arrayOfByte == null) {
/*     */                 continue;
/*     */               }
/*     */             } 
/* 227 */             Header header = new Header(arrayOfByte, arrayOfByte.length);
/*     */             
/* 229 */             if (paramBoolean2 && !header.authoritative) {
/* 230 */               namingException = new NameNotFoundException("DNS response not authoritative");
/*     */               
/* 232 */               arrayOfBoolean[b1] = true;
/*     */             } else {
/*     */               
/* 235 */               if (header.truncated)
/*     */               {
/*     */ 
/*     */                 
/* 239 */                 for (byte b2 = 0; b2 < this.servers.length; b2++) {
/* 240 */                   int j = (b1 + b2) % this.servers.length;
/* 241 */                   if (!arrayOfBoolean[j]) {
/*     */                     try {
/*     */                       byte[] arrayOfByte1;
/*     */                       
/* 245 */                       Tcp tcp = new Tcp(this.servers[j], this.serverPorts[j]);
/*     */ 
/*     */                       
/*     */                       try {
/* 249 */                         arrayOfByte1 = doTcpQuery(tcp, packet);
/*     */                       } finally {
/* 251 */                         tcp.close();
/*     */                       } 
/* 253 */                       Header header1 = new Header(arrayOfByte1, arrayOfByte1.length);
/* 254 */                       if (header1.query) {
/* 255 */                         throw new CommunicationException("DNS error: expecting response");
/*     */                       }
/*     */                       
/* 258 */                       checkResponseCode(header1);
/*     */                       
/* 260 */                       if (!paramBoolean2 || header1.authoritative) {
/*     */                         
/* 262 */                         header = header1;
/* 263 */                         arrayOfByte = arrayOfByte1;
/*     */                         break;
/*     */                       } 
/* 266 */                       arrayOfBoolean[j] = true;
/*     */                     }
/* 268 */                     catch (Exception exception) {}
/*     */                   }
/*     */                 } 
/*     */               }
/*     */               
/* 273 */               return new ResourceRecords(arrayOfByte, arrayOfByte.length, header, false);
/*     */             } 
/* 275 */           } catch (IOException iOException1) {
/*     */ 
/*     */ 
/*     */             
/* 279 */             if (namingException == null) {
/* 280 */               iOException = iOException1;
/*     */             }
/*     */ 
/*     */             
/* 284 */             if (iOException1.getClass().getName().equals("java.net.PortUnreachableException"))
/*     */             {
/* 286 */               arrayOfBoolean[b1] = true;
/*     */             }
/* 288 */           } catch (NameNotFoundException nameNotFoundException) {
/*     */             
/* 290 */             throw nameNotFoundException;
/* 291 */           } catch (CommunicationException communicationException2) {
/* 292 */             if (iOException == null) {
/* 293 */               communicationException1 = communicationException2;
/*     */             }
/* 295 */           } catch (NamingException namingException1) {
/* 296 */             if (communicationException1 == null) {
/* 297 */               namingException = namingException1;
/*     */             }
/* 299 */             arrayOfBoolean[b1] = true;
/*     */           } 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 305 */       this.reqs.remove(Integer.valueOf(i));
/*     */     } 
/*     */     
/* 308 */     if (namingException instanceof NamingException) {
/* 309 */       throw namingException;
/*     */     }
/*     */     
/* 312 */     CommunicationException communicationException = new CommunicationException("DNS error");
/* 313 */     communicationException.setRootCause(namingException);
/* 314 */     throw communicationException;
/*     */   }
/*     */ 
/*     */   
/*     */   ResourceRecords queryZone(DnsName paramDnsName, int paramInt, boolean paramBoolean) throws NamingException {
/*     */     NamingException namingException;
/* 320 */     int i = random.nextInt(65536);
/*     */     
/* 322 */     Packet packet = makeQueryPacket(paramDnsName, i, paramInt, 252, paramBoolean);
/*     */     
/* 324 */     IOException iOException = null;
/*     */ 
/*     */     
/* 327 */     for (byte b = 0; b < this.servers.length; b++) {
/*     */       try {
/* 329 */         Tcp tcp = new Tcp(this.servers[b], this.serverPorts[b]);
/*     */         
/*     */         try {
/* 332 */           byte[] arrayOfByte = doTcpQuery(tcp, packet);
/* 333 */           Header header = new Header(arrayOfByte, arrayOfByte.length);
/*     */ 
/*     */           
/* 336 */           checkResponseCode(header);
/* 337 */           ResourceRecords resourceRecords = new ResourceRecords(arrayOfByte, arrayOfByte.length, header, true);
/*     */           
/* 339 */           if (resourceRecords.getFirstAnsType() != 6) {
/* 340 */             throw new CommunicationException("DNS error: zone xfer doesn't begin with SOA");
/*     */           }
/*     */ 
/*     */           
/* 344 */           if (resourceRecords.answer.size() == 1 || resourceRecords
/* 345 */             .getLastAnsType() != 6) {
/*     */             
/*     */             do {
/* 348 */               arrayOfByte = continueTcpQuery(tcp);
/* 349 */               if (arrayOfByte == null) {
/* 350 */                 throw new CommunicationException("DNS error: incomplete zone transfer");
/*     */               }
/*     */               
/* 353 */               header = new Header(arrayOfByte, arrayOfByte.length);
/* 354 */               checkResponseCode(header);
/* 355 */               resourceRecords.add(arrayOfByte, arrayOfByte.length, header);
/* 356 */             } while (resourceRecords.getLastAnsType() != 6);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 361 */           resourceRecords.answer.removeElementAt(resourceRecords.answer.size() - 1);
/* 362 */           return resourceRecords;
/*     */         } finally {
/*     */           
/* 365 */           tcp.close();
/*     */         }
/*     */       
/* 368 */       } catch (IOException iOException1) {
/* 369 */         iOException = iOException1;
/* 370 */       } catch (NameNotFoundException nameNotFoundException) {
/* 371 */         throw nameNotFoundException;
/* 372 */       } catch (NamingException namingException1) {
/* 373 */         namingException = namingException1;
/*     */       } 
/*     */     } 
/* 376 */     if (namingException instanceof NamingException) {
/* 377 */       throw namingException;
/*     */     }
/* 379 */     CommunicationException communicationException = new CommunicationException("DNS error during zone transfer");
/*     */     
/* 381 */     communicationException.setRootCause(namingException);
/* 382 */     throw communicationException;
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
/*     */   private byte[] doUdpQuery(Packet paramPacket, InetAddress paramInetAddress, int paramInt1, int paramInt2, int paramInt3) throws IOException, NamingException {
/* 396 */     byte b = 50;
/*     */     
/* 398 */     synchronized (this.udpSocketLock) {
/* 399 */       try (DatagramSocket null = getDatagramSocket()) {
/*     */         
/* 401 */         DatagramPacket datagramPacket1 = new DatagramPacket(paramPacket.getData(), paramPacket.length(), paramInetAddress, paramInt1);
/* 402 */         DatagramPacket datagramPacket2 = new DatagramPacket(new byte[8000], 8000);
/*     */         
/* 404 */         datagramSocket.connect(paramInetAddress, paramInt1);
/* 405 */         int i = this.timeout * (1 << paramInt2);
/*     */         try {
/* 407 */           datagramSocket.send(datagramPacket1);
/*     */ 
/*     */           
/* 410 */           int j = i;
/* 411 */           boolean bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           do {
/* 420 */             datagramSocket.setSoTimeout(j);
/* 421 */             long l1 = System.currentTimeMillis();
/* 422 */             datagramSocket.receive(datagramPacket2);
/* 423 */             long l2 = System.currentTimeMillis();
/*     */             
/* 425 */             byte[] arrayOfByte = datagramPacket2.getData();
/* 426 */             if (isMatchResponse(arrayOfByte, paramInt3)) {
/* 427 */               return arrayOfByte;
/*     */             }
/* 429 */             j = i - (int)(l2 - l1);
/* 430 */           } while (j > b);
/*     */         } finally {
/*     */           
/* 433 */           datagramSocket.disconnect();
/*     */         } 
/* 435 */         return null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] doTcpQuery(Tcp paramTcp, Packet paramPacket) throws IOException {
/* 445 */     int i = paramPacket.length();
/*     */     
/* 447 */     paramTcp.out.write(i >> 8);
/* 448 */     paramTcp.out.write(i);
/* 449 */     paramTcp.out.write(paramPacket.getData(), 0, i);
/* 450 */     paramTcp.out.flush();
/*     */     
/* 452 */     byte[] arrayOfByte = continueTcpQuery(paramTcp);
/* 453 */     if (arrayOfByte == null) {
/* 454 */       throw new IOException("DNS error: no response");
/*     */     }
/* 456 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] continueTcpQuery(Tcp paramTcp) throws IOException {
/* 464 */     int i = paramTcp.in.read();
/* 465 */     if (i == -1) {
/* 466 */       return null;
/*     */     }
/* 468 */     int j = paramTcp.in.read();
/* 469 */     if (j == -1) {
/* 470 */       throw new IOException("Corrupted DNS response: bad length");
/*     */     }
/* 472 */     int k = i << 8 | j;
/* 473 */     byte[] arrayOfByte = new byte[k];
/* 474 */     int m = 0;
/* 475 */     while (k > 0) {
/* 476 */       int n = paramTcp.in.read(arrayOfByte, m, k);
/* 477 */       if (n == -1) {
/* 478 */         throw new IOException("Corrupted DNS response: too little data");
/*     */       }
/*     */       
/* 481 */       k -= n;
/* 482 */       m += n;
/*     */     } 
/* 484 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   private Packet makeQueryPacket(DnsName paramDnsName, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 489 */     short s = paramDnsName.getOctets();
/* 490 */     int i = 12 + s + 4;
/* 491 */     Packet packet = new Packet(i);
/*     */     
/* 493 */     boolean bool = paramBoolean ? true : false;
/*     */     
/* 495 */     packet.putShort(paramInt1, 0);
/* 496 */     packet.putShort(bool, 2);
/* 497 */     packet.putShort(1, 4);
/* 498 */     packet.putShort(0, 6);
/* 499 */     packet.putInt(0, 8);
/*     */     
/* 501 */     makeQueryName(paramDnsName, packet, 12);
/* 502 */     packet.putShort(paramInt3, 12 + s);
/* 503 */     packet.putShort(paramInt2, 12 + s + 2);
/*     */     
/* 505 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void makeQueryName(DnsName paramDnsName, Packet paramPacket, int paramInt) {
/* 512 */     for (int i = paramDnsName.size() - 1; i >= 0; i--) {
/* 513 */       String str = paramDnsName.get(i);
/* 514 */       int j = str.length();
/*     */       
/* 516 */       paramPacket.putByte(j, paramInt++);
/* 517 */       for (byte b = 0; b < j; b++) {
/* 518 */         paramPacket.putByte(str.charAt(b), paramInt++);
/*     */       }
/*     */     } 
/* 521 */     if (!paramDnsName.hasRootLabel()) {
/* 522 */       paramPacket.putByte(0, paramInt);
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
/*     */ 
/*     */   
/*     */   private byte[] lookupResponse(Integer paramInteger) throws NamingException {
/*     */     byte[] arrayOfByte;
/* 538 */     if ((arrayOfByte = this.resps.get(paramInteger)) != null) {
/* 539 */       checkResponseCode(new Header(arrayOfByte, arrayOfByte.length));
/* 540 */       synchronized (this.queuesLock) {
/* 541 */         this.resps.remove(paramInteger);
/* 542 */         this.reqs.remove(paramInteger);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 550 */     return arrayOfByte;
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
/*     */   private boolean isMatchResponse(byte[] paramArrayOfbyte, int paramInt) throws NamingException {
/* 564 */     Header header = new Header(paramArrayOfbyte, paramArrayOfbyte.length);
/* 565 */     if (header.query) {
/* 566 */       throw new CommunicationException("DNS error: expecting response");
/*     */     }
/*     */     
/* 569 */     if (!this.reqs.containsKey(Integer.valueOf(paramInt))) {
/* 570 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 574 */     if (header.xid == paramInt) {
/*     */ 
/*     */ 
/*     */       
/* 578 */       checkResponseCode(header);
/* 579 */       if (!header.query && header.numQuestions == 1) {
/*     */         
/* 581 */         ResourceRecord resourceRecord1 = new ResourceRecord(paramArrayOfbyte, paramArrayOfbyte.length, 12, true, false);
/*     */ 
/*     */ 
/*     */         
/* 585 */         ResourceRecord resourceRecord2 = this.reqs.get(Integer.valueOf(paramInt));
/* 586 */         int i = resourceRecord2.getType();
/* 587 */         int j = resourceRecord2.getRrclass();
/* 588 */         DnsName dnsName = resourceRecord2.getName();
/*     */ 
/*     */ 
/*     */         
/* 592 */         if ((i == 255 || i == resourceRecord1
/* 593 */           .getType()) && (j == 255 || j == resourceRecord1
/*     */           
/* 595 */           .getRrclass()) && dnsName
/* 596 */           .equals(resourceRecord1.getName())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 605 */           synchronized (this.queuesLock) {
/* 606 */             this.resps.remove(Integer.valueOf(paramInt));
/* 607 */             this.reqs.remove(Integer.valueOf(paramInt));
/*     */           } 
/* 609 */           return true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 618 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 626 */     synchronized (this.queuesLock) {
/* 627 */       if (this.reqs.containsKey(Integer.valueOf(header.xid))) {
/* 628 */         this.resps.put(Integer.valueOf(header.xid), paramArrayOfbyte);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 638 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkResponseCode(Header paramHeader) throws NamingException {
/* 647 */     int i = paramHeader.rcode;
/* 648 */     if (i == 0) {
/*     */       return;
/*     */     }
/* 651 */     String str = (i < rcodeDescription.length) ? rcodeDescription[i] : "DNS error";
/*     */ 
/*     */     
/* 654 */     str = str + " [response code " + i + "]";
/*     */     
/* 656 */     switch (i) {
/*     */       case 2:
/* 658 */         throw new ServiceUnavailableException(str);
/*     */       case 3:
/* 660 */         throw new NameNotFoundException(str);
/*     */       case 4:
/*     */       case 5:
/* 663 */         throw new OperationNotSupportedException(str);
/*     */     } 
/*     */     
/* 666 */     throw new NamingException(str);
/*     */   }
/*     */   
/*     */   private static void dprint(String paramString) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/DnsClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */