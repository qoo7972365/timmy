package sun.rmi.transport.proxy;

interface CGICommandHandler {
  String getName();
  
  void execute(String paramString) throws CGIClientException, CGIServerException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/proxy/CGICommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */