package com.sun.xml.internal.ws.api.pipe;

import com.sun.istack.internal.NotNull;

public interface PipelineAssembler {
  @NotNull
  Pipe createClient(@NotNull ClientPipeAssemblerContext paramClientPipeAssemblerContext);
  
  @NotNull
  Pipe createServer(@NotNull ServerPipeAssemblerContext paramServerPipeAssemblerContext);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/PipelineAssembler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */