package com.sun.xml.internal.ws.assembler.dev;

import javax.xml.ws.WebServiceException;

public interface TubelineAssemblyContextUpdater {
  void prepareContext(ClientTubelineAssemblyContext paramClientTubelineAssemblyContext) throws WebServiceException;
  
  void prepareContext(ServerTubelineAssemblyContext paramServerTubelineAssemblyContext) throws WebServiceException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/dev/TubelineAssemblyContextUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */