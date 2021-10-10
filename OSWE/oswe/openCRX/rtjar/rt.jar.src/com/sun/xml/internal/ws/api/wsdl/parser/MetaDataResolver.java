package com.sun.xml.internal.ws.api.wsdl.parser;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import java.net.URI;

public abstract class MetaDataResolver {
  @Nullable
  public abstract ServiceDescriptor resolve(@NotNull URI paramURI);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/wsdl/parser/MetaDataResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */