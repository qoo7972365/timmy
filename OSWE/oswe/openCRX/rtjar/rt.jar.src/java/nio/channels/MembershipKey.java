package java.nio.channels;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;

public abstract class MembershipKey {
  public abstract boolean isValid();
  
  public abstract void drop();
  
  public abstract MembershipKey block(InetAddress paramInetAddress) throws IOException;
  
  public abstract MembershipKey unblock(InetAddress paramInetAddress);
  
  public abstract MulticastChannel channel();
  
  public abstract InetAddress group();
  
  public abstract NetworkInterface networkInterface();
  
  public abstract InetAddress sourceAddress();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/channels/MembershipKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */