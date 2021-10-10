package javax.net.ssl;

import java.util.EventListener;

public interface HandshakeCompletedListener extends EventListener {
  void handshakeCompleted(HandshakeCompletedEvent paramHandshakeCompletedEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/HandshakeCompletedListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */