/*    */ package com.sun.xml.internal.ws.encoding;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*    */ import com.sun.xml.internal.ws.api.message.Attachment;
/*    */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*    */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*    */ import com.sun.xml.internal.ws.message.MimeAttachmentSet;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.channels.ReadableByteChannel;
/*    */ import java.nio.channels.WritableByteChannel;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SwACodec
/*    */   extends MimeCodec
/*    */ {
/*    */   public SwACodec(SOAPVersion version, WSFeatureList f, Codec rootCodec) {
/* 50 */     super(version, f);
/* 51 */     this.mimeRootCodec = rootCodec;
/*    */   }
/*    */   
/*    */   private SwACodec(SwACodec that) {
/* 55 */     super(that);
/* 56 */     this.mimeRootCodec = that.mimeRootCodec.copy();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void decode(MimeMultipartParser mpp, Packet packet) throws IOException {
/* 62 */     Attachment root = mpp.getRootPart();
/* 63 */     Codec rootCodec = getMimeRootCodec(packet);
/* 64 */     if (rootCodec instanceof RootOnlyCodec) {
/* 65 */       ((RootOnlyCodec)rootCodec).decode(root.asInputStream(), root.getContentType(), packet, (AttachmentSet)new MimeAttachmentSet(mpp));
/*    */     } else {
/* 67 */       rootCodec.decode(root.asInputStream(), root.getContentType(), packet);
/* 68 */       Map<String, Attachment> atts = mpp.getAttachmentParts();
/* 69 */       for (Map.Entry<String, Attachment> att : atts.entrySet()) {
/* 70 */         packet.getMessage().getAttachments().add(att.getValue());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 77 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public SwACodec copy() {
/* 81 */     return new SwACodec(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/SwACodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */