package javax.security.sasl;

import java.util.Map;
import javax.security.auth.callback.CallbackHandler;

public interface SaslServerFactory {
  SaslServer createSaslServer(String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException;
  
  String[] getMechanismNames(Map<String, ?> paramMap);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/sasl/SaslServerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */