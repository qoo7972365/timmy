/*     */ package sun.management.jdp;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JdpPacketReader
/*     */ {
/*     */   private final DataInputStream pkt;
/*  43 */   private Map<String, String> pmap = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdpPacketReader(byte[] paramArrayOfbyte) throws JdpException {
/*  53 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/*  54 */     this.pkt = new DataInputStream(byteArrayInputStream);
/*     */     
/*     */     try {
/*  57 */       int i = this.pkt.readInt();
/*  58 */       JdpGenericPacket.checkMagic(i);
/*  59 */     } catch (IOException iOException) {
/*  60 */       throw new JdpException("Invalid JDP packet received, bad magic");
/*     */     } 
/*     */     
/*     */     try {
/*  64 */       short s = this.pkt.readShort();
/*  65 */       JdpGenericPacket.checkVersion(s);
/*  66 */     } catch (IOException iOException) {
/*  67 */       throw new JdpException("Invalid JDP packet received, bad protocol version");
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
/*     */   public String getEntry() throws EOFException, JdpException {
/*     */     try {
/*  82 */       short s = this.pkt.readShort();
/*     */ 
/*     */       
/*  85 */       if (s < 1 && s > this.pkt.available()) {
/*  86 */         throw new JdpException("Broken JDP packet. Invalid entry length field.");
/*     */       }
/*     */       
/*  89 */       byte[] arrayOfByte = new byte[s];
/*  90 */       if (this.pkt.read(arrayOfByte) != s) {
/*  91 */         throw new JdpException("Broken JDP packet. Unable to read entry.");
/*     */       }
/*  93 */       return new String(arrayOfByte, "UTF-8");
/*     */     }
/*  95 */     catch (EOFException eOFException) {
/*  96 */       throw eOFException;
/*  97 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  98 */       throw new JdpException("Broken JDP packet. Unable to decode entry.");
/*  99 */     } catch (IOException iOException) {
/* 100 */       throw new JdpException("Broken JDP packet. Unable to read entry.");
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
/*     */ 
/*     */   
/*     */   public Map<String, String> getDiscoveryDataAsMap() throws JdpException {
/* 117 */     if (this.pmap != null) {
/* 118 */       return this.pmap;
/*     */     }
/*     */     
/* 121 */     String str1 = null, str2 = null;
/*     */     
/* 123 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     try {
/*     */       while (true) {
/* 126 */         str1 = getEntry();
/* 127 */         str2 = getEntry();
/* 128 */         hashMap.put(str1, str2);
/*     */       } 
/* 130 */     } catch (EOFException eOFException) {
/*     */ 
/*     */       
/* 133 */       if (str2 == null) {
/* 134 */         throw new JdpException("Broken JDP packet. Key without value." + str1);
/*     */       }
/*     */ 
/*     */       
/* 138 */       this.pmap = Collections.unmodifiableMap(hashMap);
/* 139 */       return this.pmap;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/jdp/JdpPacketReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */