package sun.misc;

import java.net.HttpCookie;
import java.util.List;

public interface JavaNetHttpCookieAccess {
  List<HttpCookie> parse(String paramString);
  
  String header(HttpCookie paramHttpCookie);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/JavaNetHttpCookieAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */