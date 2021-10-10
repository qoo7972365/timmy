package sun.nio.ch.sctp;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.Notification;

interface SctpNotification extends Notification {
  int assocId();
  
  void setAssociation(Association paramAssociation);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/SctpNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */