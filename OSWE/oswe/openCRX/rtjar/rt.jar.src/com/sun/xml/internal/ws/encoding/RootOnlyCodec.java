package com.sun.xml.internal.ws.encoding;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.message.AttachmentSet;
import com.sun.xml.internal.ws.api.message.Packet;
import com.sun.xml.internal.ws.api.pipe.Codec;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

public interface RootOnlyCodec extends Codec {
  void decode(@NotNull InputStream paramInputStream, @NotNull String paramString, @NotNull Packet paramPacket, @NotNull AttachmentSet paramAttachmentSet) throws IOException;
  
  void decode(@NotNull ReadableByteChannel paramReadableByteChannel, @NotNull String paramString, @NotNull Packet paramPacket, @NotNull AttachmentSet paramAttachmentSet);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/RootOnlyCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */