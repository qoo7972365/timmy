package com.sun.xml.internal.ws.api.server;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public interface AsyncProviderCallback<T> {
  void send(@Nullable T paramT);
  
  void sendError(@NotNull Throwable paramThrowable);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/AsyncProviderCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */