package com.sun.xml.internal.txw2.output;

import java.io.IOException;
import java.io.Writer;

public interface CharacterEscapeHandler {
  void escape(char[] paramArrayOfchar, int paramInt1, int paramInt2, boolean paramBoolean, Writer paramWriter) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/output/CharacterEscapeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */