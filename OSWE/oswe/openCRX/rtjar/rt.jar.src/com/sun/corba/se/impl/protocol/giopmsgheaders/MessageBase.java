/*     */ package com.sun.corba.se.impl.protocol.giopmsgheaders;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
/*     */ import com.sun.corba.se.impl.encoding.CDRInputStream_1_0;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.protocol.AddressingDispositionException;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPFactories;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfile;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*     */ import com.sun.corba.se.spi.ior.iiop.RequestPartitioningComponent;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.servicecontext.ServiceContexts;
/*     */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*     */ import com.sun.corba.se.spi.transport.ReadTimeouts;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Iterator;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Principal;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.IOP.TaggedProfile;
/*     */ import sun.corba.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MessageBase
/*     */   implements Message
/*     */ {
/*     */   public byte[] giopHeader;
/*     */   private ByteBuffer byteBuffer;
/*     */   private int threadPoolToUse;
/*  85 */   byte encodingVersion = 0;
/*     */ 
/*     */   
/*  88 */   private static ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.protocol");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String typeToString(int paramInt) {
/*  94 */     return typeToString((byte)paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String typeToString(byte paramByte) {
/*  99 */     String str = paramByte + "/";
/* 100 */     switch (paramByte) { case 0:
/* 101 */         str = str + "GIOPRequest";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 111 */         return str;case 1: str = str + "GIOPReply"; return str;case 2: str = str + "GIOPCancelRequest"; return str;case 3: str = str + "GIOPLocateRequest"; return str;case 4: str = str + "GIOPLocateReply"; return str;case 5: str = str + "GIOPCloseConnection"; return str;case 6: str = str + "GIOPMessageError"; return str;case 7: str = str + "GIOPFragment"; return str; }  str = str + "Unknown"; return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static MessageBase readGIOPMessage(ORB paramORB, CorbaConnection paramCorbaConnection) {
/* 116 */     MessageBase messageBase = readGIOPHeader(paramORB, paramCorbaConnection);
/* 117 */     messageBase = (MessageBase)readGIOPBody(paramORB, paramCorbaConnection, messageBase);
/* 118 */     return messageBase;
/*     */   }
/*     */   
/*     */   public static MessageBase readGIOPHeader(ORB paramORB, CorbaConnection paramCorbaConnection) {
/*     */     Message_1_1 message_1_1;
/* 123 */     RequestMessage_1_0 requestMessage_1_0 = null;
/*     */     
/* 125 */     ReadTimeouts readTimeouts = paramORB.getORBData().getTransportTCPReadTimeouts();
/*     */     
/* 127 */     ByteBuffer byteBuffer = null;
/*     */     
/*     */     try {
/* 130 */       byteBuffer = paramCorbaConnection.read(12, 0, 12, readTimeouts
/*     */           
/* 132 */           .get_max_giop_header_time_to_wait());
/* 133 */     } catch (IOException iOException) {
/* 134 */       throw wrapper.ioexceptionWhenReadingConnection(iOException);
/*     */     } 
/*     */     
/* 137 */     if (paramORB.giopDebugFlag) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       dprint(".readGIOPHeader: " + typeToString(byteBuffer.get(7)));
/* 143 */       dprint(".readGIOPHeader: GIOP header is: ");
/* 144 */       ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
/* 145 */       byteBuffer1.position(0).limit(12);
/* 146 */       ByteBufferWithInfo byteBufferWithInfo = new ByteBufferWithInfo((ORB)paramORB, byteBuffer1);
/* 147 */       byteBufferWithInfo.buflen = 12;
/* 148 */       CDRInputStream_1_0.printBuffer(byteBufferWithInfo);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     int i = byteBuffer.get(0) << 24 & 0xFF000000;
/* 166 */     int j = byteBuffer.get(1) << 16 & 0xFF0000;
/* 167 */     int k = byteBuffer.get(2) << 8 & 0xFF00;
/* 168 */     int m = byteBuffer.get(3) << 0 & 0xFF;
/* 169 */     int n = i | j | k | m;
/*     */     
/* 171 */     if (n != 1195986768)
/*     */     {
/*     */       
/* 174 */       throw wrapper.giopMagicError(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     byte b = 0;
/* 181 */     if (byteBuffer.get(4) == 13 && byteBuffer
/* 182 */       .get(5) <= 1 && byteBuffer
/* 183 */       .get(5) > 0 && paramORB
/* 184 */       .getORBData().isJavaSerializationEnabled()) {
/*     */ 
/*     */       
/* 187 */       b = byteBuffer.get(5);
/* 188 */       byteBuffer.put(4, (byte)1);
/* 189 */       byteBuffer.put(5, (byte)2);
/*     */     } 
/*     */     
/* 192 */     GIOPVersion gIOPVersion = paramORB.getORBData().getGIOPVersion();
/*     */     
/* 194 */     if (paramORB.giopDebugFlag) {
/* 195 */       dprint(".readGIOPHeader: Message GIOP version: " + byteBuffer
/* 196 */           .get(4) + '.' + byteBuffer.get(5));
/* 197 */       dprint(".readGIOPHeader: ORB Max GIOP Version: " + gIOPVersion);
/*     */     } 
/*     */ 
/*     */     
/* 201 */     if (byteBuffer.get(4) > gIOPVersion.getMajor() || (byteBuffer
/* 202 */       .get(4) == gIOPVersion.getMajor() && byteBuffer.get(5) > gIOPVersion.getMinor()))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 214 */       if (byteBuffer.get(7) != 6) {
/* 215 */         throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */       }
/*     */     }
/*     */     
/* 219 */     AreFragmentsAllowed(byteBuffer.get(4), byteBuffer.get(5), byteBuffer.get(6), byteBuffer.get(7));
/*     */ 
/*     */ 
/*     */     
/* 223 */     switch (byteBuffer.get(7)) {
/*     */       
/*     */       case 0:
/* 226 */         if (paramORB.giopDebugFlag) {
/* 227 */           dprint(".readGIOPHeader: creating RequestMessage");
/*     */         }
/*     */         
/* 230 */         if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 0) {
/* 231 */           requestMessage_1_0 = new RequestMessage_1_0(paramORB); break;
/* 232 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 1) {
/* 233 */           RequestMessage_1_1 requestMessage_1_1 = new RequestMessage_1_1(paramORB); break;
/* 234 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 2) {
/* 235 */           RequestMessage_1_2 requestMessage_1_2 = new RequestMessage_1_2(paramORB); break;
/*     */         } 
/* 237 */         throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 243 */         if (paramORB.giopDebugFlag) {
/* 244 */           dprint(".readGIOPHeader: creating LocateRequestMessage");
/*     */         }
/*     */         
/* 247 */         if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 0) {
/* 248 */           LocateRequestMessage_1_0 locateRequestMessage_1_0 = new LocateRequestMessage_1_0(paramORB); break;
/* 249 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 1) {
/* 250 */           LocateRequestMessage_1_1 locateRequestMessage_1_1 = new LocateRequestMessage_1_1(paramORB); break;
/* 251 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 2) {
/* 252 */           LocateRequestMessage_1_2 locateRequestMessage_1_2 = new LocateRequestMessage_1_2(paramORB); break;
/*     */         } 
/* 254 */         throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 260 */         if (paramORB.giopDebugFlag) {
/* 261 */           dprint(".readGIOPHeader: creating CancelRequestMessage");
/*     */         }
/*     */         
/* 264 */         if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 0) {
/* 265 */           CancelRequestMessage_1_0 cancelRequestMessage_1_0 = new CancelRequestMessage_1_0(); break;
/* 266 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 1) {
/* 267 */           CancelRequestMessage_1_1 cancelRequestMessage_1_1 = new CancelRequestMessage_1_1(); break;
/* 268 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 2) {
/* 269 */           CancelRequestMessage_1_2 cancelRequestMessage_1_2 = new CancelRequestMessage_1_2(); break;
/*     */         } 
/* 271 */         throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 277 */         if (paramORB.giopDebugFlag) {
/* 278 */           dprint(".readGIOPHeader: creating ReplyMessage");
/*     */         }
/*     */         
/* 281 */         if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 0) {
/* 282 */           ReplyMessage_1_0 replyMessage_1_0 = new ReplyMessage_1_0(paramORB); break;
/* 283 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 1) {
/* 284 */           ReplyMessage_1_1 replyMessage_1_1 = new ReplyMessage_1_1(paramORB); break;
/* 285 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 2) {
/* 286 */           ReplyMessage_1_2 replyMessage_1_2 = new ReplyMessage_1_2(paramORB); break;
/*     */         } 
/* 288 */         throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 294 */         if (paramORB.giopDebugFlag) {
/* 295 */           dprint(".readGIOPHeader: creating LocateReplyMessage");
/*     */         }
/*     */         
/* 298 */         if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 0) {
/* 299 */           LocateReplyMessage_1_0 locateReplyMessage_1_0 = new LocateReplyMessage_1_0(paramORB); break;
/* 300 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 1) {
/* 301 */           LocateReplyMessage_1_1 locateReplyMessage_1_1 = new LocateReplyMessage_1_1(paramORB); break;
/* 302 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 2) {
/* 303 */           LocateReplyMessage_1_2 locateReplyMessage_1_2 = new LocateReplyMessage_1_2(paramORB); break;
/*     */         } 
/* 305 */         throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/*     */       case 6:
/* 312 */         if (paramORB.giopDebugFlag) {
/* 313 */           dprint(".readGIOPHeader: creating Message for CloseConnection or MessageError");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 320 */         if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 0) {
/* 321 */           Message_1_0 message_1_0 = new Message_1_0(); break;
/* 322 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 1) {
/* 323 */           message_1_1 = new Message_1_1(); break;
/* 324 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 2) {
/* 325 */           message_1_1 = new Message_1_1(); break;
/*     */         } 
/* 327 */         throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 7:
/* 333 */         if (paramORB.giopDebugFlag) {
/* 334 */           dprint(".readGIOPHeader: creating FragmentMessage");
/*     */         }
/*     */         
/* 337 */         if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 0)
/*     */           break; 
/* 339 */         if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 1) {
/* 340 */           message_1_1 = new FragmentMessage_1_1(); break;
/* 341 */         }  if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 2) {
/* 342 */           message_1_1 = new FragmentMessage_1_2(); break;
/*     */         } 
/* 344 */         throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 350 */         if (paramORB.giopDebugFlag) {
/* 351 */           dprint(".readGIOPHeader: UNKNOWN MESSAGE TYPE: " + byteBuffer
/* 352 */               .get(7));
/*     */         }
/*     */         
/* 355 */         throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 363 */     if (byteBuffer.get(4) == 1 && byteBuffer.get(5) == 0) {
/* 364 */       Message_1_0 message_1_0 = (Message_1_0)message_1_1;
/* 365 */       message_1_0.magic = n;
/* 366 */       message_1_0.GIOP_version = new GIOPVersion(byteBuffer.get(4), byteBuffer.get(5));
/* 367 */       message_1_0.byte_order = (byteBuffer.get(6) == 1);
/*     */ 
/*     */       
/* 370 */       message_1_1.threadPoolToUse = 0;
/* 371 */       message_1_0.message_type = byteBuffer.get(7);
/* 372 */       message_1_0.message_size = readSize(byteBuffer.get(8), byteBuffer.get(9), byteBuffer.get(10), byteBuffer.get(11), message_1_0
/* 373 */           .isLittleEndian()) + 12;
/*     */     } else {
/*     */       
/* 376 */       Message_1_1 message_1_11 = message_1_1;
/* 377 */       message_1_11.magic = n;
/* 378 */       message_1_11.GIOP_version = new GIOPVersion(byteBuffer.get(4), byteBuffer.get(5));
/* 379 */       message_1_11.flags = (byte)(byteBuffer.get(6) & 0x3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 389 */       message_1_1.threadPoolToUse = byteBuffer.get(6) >>> 2 & 0x3F;
/* 390 */       message_1_11.message_type = byteBuffer.get(7);
/* 391 */       message_1_11
/* 392 */         .message_size = readSize(byteBuffer.get(8), byteBuffer.get(9), byteBuffer.get(10), byteBuffer.get(11), message_1_11
/* 393 */           .isLittleEndian()) + 12;
/*     */     } 
/*     */ 
/*     */     
/* 397 */     if (paramORB.giopDebugFlag) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 402 */       dprint(".readGIOPHeader: header construction complete.");
/*     */ 
/*     */       
/* 405 */       ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
/* 406 */       byte[] arrayOfByte = new byte[12];
/* 407 */       byteBuffer1.position(0).limit(12);
/* 408 */       byteBuffer1.get(arrayOfByte, 0, arrayOfByte.length);
/*     */       
/* 410 */       message_1_1.giopHeader = arrayOfByte;
/*     */     } 
/*     */     
/* 413 */     message_1_1.setByteBuffer(byteBuffer);
/* 414 */     message_1_1.setEncodingVersion(b);
/*     */     
/* 416 */     return message_1_1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message readGIOPBody(ORB paramORB, CorbaConnection paramCorbaConnection, Message paramMessage) {
/* 424 */     ReadTimeouts readTimeouts = paramORB.getORBData().getTransportTCPReadTimeouts();
/* 425 */     ByteBuffer byteBuffer = paramMessage.getByteBuffer();
/*     */     
/* 427 */     byteBuffer.position(12);
/*     */     
/* 429 */     int i = paramMessage.getSize() - 12;
/*     */     try {
/* 431 */       byteBuffer = paramCorbaConnection.read(byteBuffer, 12, i, readTimeouts
/*     */           
/* 433 */           .get_max_time_to_wait());
/* 434 */     } catch (IOException iOException) {
/* 435 */       throw wrapper.ioexceptionWhenReadingConnection(iOException);
/*     */     } 
/*     */     
/* 438 */     paramMessage.setByteBuffer(byteBuffer);
/*     */     
/* 440 */     if (paramORB.giopDebugFlag) {
/* 441 */       dprint(".readGIOPBody: received message:");
/* 442 */       ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
/* 443 */       byteBuffer1.position(0).limit(paramMessage.getSize());
/* 444 */       ByteBufferWithInfo byteBufferWithInfo = new ByteBufferWithInfo((ORB)paramORB, byteBuffer1);
/* 445 */       CDRInputStream_1_0.printBuffer(byteBufferWithInfo);
/*     */     } 
/*     */     
/* 448 */     return paramMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RequestMessage createRequest(ORB paramORB, GIOPVersion paramGIOPVersion, byte paramByte, int paramInt, boolean paramBoolean, byte[] paramArrayOfbyte, String paramString, ServiceContexts paramServiceContexts, Principal paramPrincipal) {
/* 456 */     if (paramGIOPVersion.equals(GIOPVersion.V1_0)) {
/* 457 */       return new RequestMessage_1_0(paramORB, paramServiceContexts, paramInt, paramBoolean, paramArrayOfbyte, paramString, paramPrincipal);
/*     */     }
/*     */     
/* 460 */     if (paramGIOPVersion.equals(GIOPVersion.V1_1)) {
/* 461 */       return new RequestMessage_1_1(paramORB, paramServiceContexts, paramInt, paramBoolean, new byte[] { 0, 0, 0 }, paramArrayOfbyte, paramString, paramPrincipal);
/*     */     }
/*     */     
/* 464 */     if (paramGIOPVersion.equals(GIOPVersion.V1_2)) {
/*     */ 
/*     */ 
/*     */       
/* 468 */       byte b = 3;
/* 469 */       if (paramBoolean) {
/* 470 */         b = 3;
/*     */       } else {
/* 472 */         b = 0;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 490 */       TargetAddress targetAddress = new TargetAddress();
/* 491 */       targetAddress.object_key(paramArrayOfbyte);
/* 492 */       RequestMessage_1_2 requestMessage_1_2 = new RequestMessage_1_2(paramORB, paramInt, b, new byte[] { 0, 0, 0 }, targetAddress, paramString, paramServiceContexts);
/*     */ 
/*     */ 
/*     */       
/* 496 */       requestMessage_1_2.setEncodingVersion(paramByte);
/* 497 */       return requestMessage_1_2;
/*     */     } 
/* 499 */     throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RequestMessage createRequest(ORB paramORB, GIOPVersion paramGIOPVersion, byte paramByte, int paramInt, boolean paramBoolean, IOR paramIOR, short paramShort, String paramString, ServiceContexts paramServiceContexts, Principal paramPrincipal) {
/* 510 */     RequestMessage requestMessage = null;
/* 511 */     IIOPProfile iIOPProfile = paramIOR.getProfile();
/*     */     
/* 513 */     if (paramShort == 0) {
/*     */       
/* 515 */       iIOPProfile = paramIOR.getProfile();
/* 516 */       ObjectKey objectKey = iIOPProfile.getObjectKey();
/* 517 */       byte[] arrayOfByte = objectKey.getBytes((ORB)paramORB);
/*     */       
/* 519 */       requestMessage = createRequest(paramORB, paramGIOPVersion, paramByte, paramInt, paramBoolean, arrayOfByte, paramString, paramServiceContexts, paramPrincipal);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 525 */       if (!paramGIOPVersion.equals(GIOPVersion.V1_2))
/*     */       {
/*     */         
/* 528 */         throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 535 */       byte b = 3;
/* 536 */       if (paramBoolean) {
/* 537 */         b = 3;
/*     */       } else {
/* 539 */         b = 0;
/*     */       } 
/*     */       
/* 542 */       TargetAddress targetAddress = new TargetAddress();
/* 543 */       if (paramShort == 1) {
/* 544 */         iIOPProfile = paramIOR.getProfile();
/* 545 */         targetAddress.profile(iIOPProfile.getIOPProfile());
/* 546 */       } else if (paramShort == 2) {
/*     */ 
/*     */         
/* 549 */         IORAddressingInfo iORAddressingInfo = new IORAddressingInfo(0, paramIOR.getIOPIOR());
/* 550 */         targetAddress.ior(iORAddressingInfo);
/*     */       } else {
/*     */         
/* 553 */         throw wrapper.illegalTargetAddressDisposition(CompletionStatus.COMPLETED_NO);
/*     */       } 
/*     */ 
/*     */       
/* 557 */       requestMessage = new RequestMessage_1_2(paramORB, paramInt, b, new byte[] { 0, 0, 0 }, targetAddress, paramString, paramServiceContexts);
/*     */ 
/*     */ 
/*     */       
/* 561 */       requestMessage.setEncodingVersion(paramByte);
/*     */     } 
/*     */     
/* 564 */     if (paramGIOPVersion.supportsIORIIOPProfileComponents()) {
/*     */       
/* 566 */       int i = 0;
/*     */       
/* 568 */       IIOPProfileTemplate iIOPProfileTemplate = (IIOPProfileTemplate)iIOPProfile.getTaggedProfileTemplate();
/*     */       
/* 570 */       Iterator<RequestPartitioningComponent> iterator = iIOPProfileTemplate.iteratorById(1398099457);
/* 571 */       if (iterator.hasNext())
/*     */       {
/* 573 */         i = ((RequestPartitioningComponent)iterator.next()).getRequestPartitioningId();
/*     */       }
/*     */       
/* 576 */       if (i < 0 || i > 63)
/*     */       {
/* 578 */         throw wrapper.invalidRequestPartitioningId(new Integer(i), new Integer(0), new Integer(63));
/*     */       }
/*     */ 
/*     */       
/* 582 */       requestMessage.setThreadPoolToUse(i);
/*     */     } 
/*     */     
/* 585 */     return requestMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReplyMessage createReply(ORB paramORB, GIOPVersion paramGIOPVersion, byte paramByte, int paramInt1, int paramInt2, ServiceContexts paramServiceContexts, IOR paramIOR) {
/* 592 */     if (paramGIOPVersion.equals(GIOPVersion.V1_0)) {
/* 593 */       return new ReplyMessage_1_0(paramORB, paramServiceContexts, paramInt1, paramInt2, paramIOR);
/*     */     }
/* 595 */     if (paramGIOPVersion.equals(GIOPVersion.V1_1)) {
/* 596 */       return new ReplyMessage_1_1(paramORB, paramServiceContexts, paramInt1, paramInt2, paramIOR);
/*     */     }
/* 598 */     if (paramGIOPVersion.equals(GIOPVersion.V1_2)) {
/* 599 */       ReplyMessage_1_2 replyMessage_1_2 = new ReplyMessage_1_2(paramORB, paramInt1, paramInt2, paramServiceContexts, paramIOR);
/*     */ 
/*     */       
/* 602 */       replyMessage_1_2.setEncodingVersion(paramByte);
/* 603 */       return replyMessage_1_2;
/*     */     } 
/* 605 */     throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LocateRequestMessage createLocateRequest(ORB paramORB, GIOPVersion paramGIOPVersion, byte paramByte, int paramInt, byte[] paramArrayOfbyte) {
/* 614 */     if (paramGIOPVersion.equals(GIOPVersion.V1_0))
/* 615 */       return new LocateRequestMessage_1_0(paramORB, paramInt, paramArrayOfbyte); 
/* 616 */     if (paramGIOPVersion.equals(GIOPVersion.V1_1))
/* 617 */       return new LocateRequestMessage_1_1(paramORB, paramInt, paramArrayOfbyte); 
/* 618 */     if (paramGIOPVersion.equals(GIOPVersion.V1_2)) {
/* 619 */       TargetAddress targetAddress = new TargetAddress();
/* 620 */       targetAddress.object_key(paramArrayOfbyte);
/* 621 */       LocateRequestMessage_1_2 locateRequestMessage_1_2 = new LocateRequestMessage_1_2(paramORB, paramInt, targetAddress);
/*     */       
/* 623 */       locateRequestMessage_1_2.setEncodingVersion(paramByte);
/* 624 */       return locateRequestMessage_1_2;
/*     */     } 
/* 626 */     throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LocateReplyMessage createLocateReply(ORB paramORB, GIOPVersion paramGIOPVersion, byte paramByte, int paramInt1, int paramInt2, IOR paramIOR) {
/* 635 */     if (paramGIOPVersion.equals(GIOPVersion.V1_0)) {
/* 636 */       return new LocateReplyMessage_1_0(paramORB, paramInt1, paramInt2, paramIOR);
/*     */     }
/* 638 */     if (paramGIOPVersion.equals(GIOPVersion.V1_1)) {
/* 639 */       return new LocateReplyMessage_1_1(paramORB, paramInt1, paramInt2, paramIOR);
/*     */     }
/* 641 */     if (paramGIOPVersion.equals(GIOPVersion.V1_2)) {
/* 642 */       LocateReplyMessage_1_2 locateReplyMessage_1_2 = new LocateReplyMessage_1_2(paramORB, paramInt1, paramInt2, paramIOR);
/*     */ 
/*     */       
/* 645 */       locateReplyMessage_1_2.setEncodingVersion(paramByte);
/* 646 */       return locateReplyMessage_1_2;
/*     */     } 
/* 648 */     throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CancelRequestMessage createCancelRequest(GIOPVersion paramGIOPVersion, int paramInt) {
/* 656 */     if (paramGIOPVersion.equals(GIOPVersion.V1_0))
/* 657 */       return new CancelRequestMessage_1_0(paramInt); 
/* 658 */     if (paramGIOPVersion.equals(GIOPVersion.V1_1))
/* 659 */       return new CancelRequestMessage_1_1(paramInt); 
/* 660 */     if (paramGIOPVersion.equals(GIOPVersion.V1_2)) {
/* 661 */       return new CancelRequestMessage_1_2(paramInt);
/*     */     }
/* 663 */     throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createCloseConnection(GIOPVersion paramGIOPVersion) {
/* 669 */     if (paramGIOPVersion.equals(GIOPVersion.V1_0)) {
/* 670 */       return new Message_1_0(1195986768, false, (byte)5, 0);
/*     */     }
/* 672 */     if (paramGIOPVersion.equals(GIOPVersion.V1_1)) {
/* 673 */       return new Message_1_1(1195986768, GIOPVersion.V1_1, (byte)0, (byte)5, 0);
/*     */     }
/*     */     
/* 676 */     if (paramGIOPVersion.equals(GIOPVersion.V1_2)) {
/* 677 */       return new Message_1_1(1195986768, GIOPVersion.V1_2, (byte)0, (byte)5, 0);
/*     */     }
/*     */ 
/*     */     
/* 681 */     throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createMessageError(GIOPVersion paramGIOPVersion) {
/* 687 */     if (paramGIOPVersion.equals(GIOPVersion.V1_0)) {
/* 688 */       return new Message_1_0(1195986768, false, (byte)6, 0);
/*     */     }
/* 690 */     if (paramGIOPVersion.equals(GIOPVersion.V1_1)) {
/* 691 */       return new Message_1_1(1195986768, GIOPVersion.V1_1, (byte)0, (byte)6, 0);
/*     */     }
/*     */     
/* 694 */     if (paramGIOPVersion.equals(GIOPVersion.V1_2)) {
/* 695 */       return new Message_1_1(1195986768, GIOPVersion.V1_2, (byte)0, (byte)6, 0);
/*     */     }
/*     */ 
/*     */     
/* 699 */     throw wrapper.giopVersionError(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FragmentMessage createFragmentMessage(GIOPVersion paramGIOPVersion) {
/* 709 */     return null;
/*     */   }
/*     */   
/*     */   public static int getRequestId(Message paramMessage) {
/* 713 */     switch (paramMessage.getType()) {
/*     */       case 0:
/* 715 */         return ((RequestMessage)paramMessage).getRequestId();
/*     */       case 1:
/* 717 */         return ((ReplyMessage)paramMessage).getRequestId();
/*     */       case 3:
/* 719 */         return ((LocateRequestMessage)paramMessage).getRequestId();
/*     */       case 4:
/* 721 */         return ((LocateReplyMessage)paramMessage).getRequestId();
/*     */       case 2:
/* 723 */         return ((CancelRequestMessage)paramMessage).getRequestId();
/*     */       case 7:
/* 725 */         return ((FragmentMessage)paramMessage).getRequestId();
/*     */     } 
/*     */     
/* 728 */     throw wrapper.illegalGiopMsgType(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setFlag(ByteBuffer paramByteBuffer, int paramInt) {
/* 736 */     byte b = paramByteBuffer.get(6);
/* 737 */     b = (byte)(b | paramInt);
/* 738 */     paramByteBuffer.put(6, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearFlag(byte[] paramArrayOfbyte, int paramInt) {
/* 745 */     paramArrayOfbyte[6] = (byte)(paramArrayOfbyte[6] & (0xFF ^ paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void AreFragmentsAllowed(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
/* 751 */     if (paramByte1 == 1 && paramByte2 == 0 && 
/* 752 */       paramByte4 == 7) {
/* 753 */       throw wrapper.fragmentationDisallowed(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 758 */     if ((paramByte3 & 0x2) == 2) {
/* 759 */       switch (paramByte4) {
/*     */         case 2:
/*     */         case 5:
/*     */         case 6:
/* 763 */           throw wrapper.fragmentationDisallowed(CompletionStatus.COMPLETED_MAYBE);
/*     */         
/*     */         case 3:
/*     */         case 4:
/* 767 */           if (paramByte1 == 1 && paramByte2 == 1) {
/* 768 */             throw wrapper.fragmentationDisallowed(CompletionStatus.COMPLETED_MAYBE);
/*     */           }
/*     */           break;
/*     */       } 
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
/*     */   static ObjectKey extractObjectKey(byte[] paramArrayOfbyte, ORB paramORB) {
/*     */     try {
/* 784 */       if (paramArrayOfbyte != null) {
/*     */         
/* 786 */         ObjectKey objectKey = paramORB.getObjectKeyFactory().create(paramArrayOfbyte);
/* 787 */         if (objectKey != null) {
/* 788 */           return objectKey;
/*     */         }
/*     */       } 
/* 791 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 797 */     throw wrapper.invalidObjectKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ObjectKey extractObjectKey(TargetAddress paramTargetAddress, ORB paramORB) {
/* 807 */     short s1 = paramORB.getORBData().getGIOPTargetAddressPreference();
/* 808 */     short s2 = paramTargetAddress.discriminator();
/*     */     
/* 810 */     switch (s1) {
/*     */       case 0:
/* 812 */         if (s2 != 0) {
/* 813 */           throw new AddressingDispositionException((short)0);
/*     */         }
/*     */         break;
/*     */       case 1:
/* 817 */         if (s2 != 1) {
/* 818 */           throw new AddressingDispositionException((short)1);
/*     */         }
/*     */         break;
/*     */       case 2:
/* 822 */         if (s2 != 2) {
/* 823 */           throw new AddressingDispositionException((short)2);
/*     */         }
/*     */         break;
/*     */       case 3:
/*     */         break;
/*     */       default:
/* 829 */         throw wrapper.orbTargetAddrPreferenceInExtractObjectkeyInvalid();
/*     */     }  try {
/*     */       byte[] arrayOfByte; IIOPProfile iIOPProfile; TaggedProfile taggedProfile;
/*     */       IORAddressingInfo iORAddressingInfo;
/* 833 */       switch (s2) {
/*     */         case 0:
/* 835 */           arrayOfByte = paramTargetAddress.object_key();
/* 836 */           if (arrayOfByte != null) {
/*     */             
/* 838 */             ObjectKey objectKey = paramORB.getObjectKeyFactory().create(arrayOfByte);
/* 839 */             if (objectKey != null) {
/* 840 */               return objectKey;
/*     */             }
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 845 */           iIOPProfile = null;
/* 846 */           taggedProfile = paramTargetAddress.profile();
/* 847 */           if (taggedProfile != null) {
/* 848 */             iIOPProfile = IIOPFactories.makeIIOPProfile(paramORB, taggedProfile);
/* 849 */             ObjectKey objectKey = iIOPProfile.getObjectKey();
/* 850 */             if (objectKey != null) {
/* 851 */               return objectKey;
/*     */             }
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 856 */           iORAddressingInfo = paramTargetAddress.ior();
/* 857 */           if (iORAddressingInfo != null) {
/* 858 */             taggedProfile = iORAddressingInfo.ior.profiles[iORAddressingInfo.selected_profile_index];
/* 859 */             iIOPProfile = IIOPFactories.makeIIOPProfile(paramORB, taggedProfile);
/* 860 */             ObjectKey objectKey = iIOPProfile.getObjectKey();
/* 861 */             if (objectKey != null) {
/* 862 */               return objectKey;
/*     */             }
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 872 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 877 */     throw wrapper.invalidObjectKey();
/*     */   }
/*     */   
/*     */   private static int readSize(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, boolean paramBoolean) {
/*     */     int i;
/*     */     int j;
/*     */     int k;
/*     */     int m;
/* 885 */     if (!paramBoolean) {
/* 886 */       i = paramByte1 << 24 & 0xFF000000;
/* 887 */       j = paramByte2 << 16 & 0xFF0000;
/* 888 */       k = paramByte3 << 8 & 0xFF00;
/* 889 */       m = paramByte4 << 0 & 0xFF;
/*     */     } else {
/* 891 */       i = paramByte4 << 24 & 0xFF000000;
/* 892 */       j = paramByte3 << 16 & 0xFF0000;
/* 893 */       k = paramByte2 << 8 & 0xFF00;
/* 894 */       m = paramByte1 << 0 & 0xFF;
/*     */     } 
/*     */     
/* 897 */     return i | j | k | m;
/*     */   }
/*     */   
/*     */   static void nullCheck(Object paramObject) {
/* 901 */     if (paramObject == null) {
/* 902 */       throw wrapper.nullNotAllowed();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static SystemException getSystemException(String paramString1, int paramInt, CompletionStatus paramCompletionStatus, String paramString2, ORBUtilSystemException paramORBUtilSystemException) {
/* 910 */     SystemException systemException = null;
/*     */ 
/*     */     
/*     */     try {
/* 914 */       Class<SystemException> clazz = SharedSecrets.getJavaCorbaAccess().loadClass(paramString1);
/* 915 */       if (paramString2 == null) {
/* 916 */         systemException = clazz.newInstance();
/*     */       } else {
/* 918 */         Class[] arrayOfClass = { String.class };
/* 919 */         Constructor<SystemException> constructor = clazz.getConstructor(arrayOfClass);
/* 920 */         Object[] arrayOfObject = { paramString2 };
/* 921 */         systemException = constructor.newInstance(arrayOfObject);
/*     */       } 
/* 923 */     } catch (Exception exception) {
/* 924 */       throw paramORBUtilSystemException.badSystemExceptionInReply(CompletionStatus.COMPLETED_MAYBE, exception);
/*     */     } 
/*     */ 
/*     */     
/* 928 */     systemException.minor = paramInt;
/* 929 */     systemException.completed = paramCompletionStatus;
/*     */     
/* 931 */     return systemException;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void callback(MessageHandler paramMessageHandler) throws IOException {
/* 937 */     paramMessageHandler.handleInput(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getByteBuffer() {
/* 942 */     return this.byteBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setByteBuffer(ByteBuffer paramByteBuffer) {
/* 947 */     this.byteBuffer = paramByteBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getThreadPoolToUse() {
/* 952 */     return this.threadPoolToUse;
/*     */   }
/*     */   
/*     */   public byte getEncodingVersion() {
/* 956 */     return this.encodingVersion;
/*     */   }
/*     */   
/*     */   public void setEncodingVersion(byte paramByte) {
/* 960 */     this.encodingVersion = paramByte;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dprint(String paramString) {
/* 965 */     ORBUtility.dprint("MessageBase", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/MessageBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */