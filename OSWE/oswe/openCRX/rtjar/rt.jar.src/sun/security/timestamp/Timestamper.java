package sun.security.timestamp;

import java.io.IOException;

public interface Timestamper {
  TSResponse generateTimestamp(TSRequest paramTSRequest) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/timestamp/Timestamper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */