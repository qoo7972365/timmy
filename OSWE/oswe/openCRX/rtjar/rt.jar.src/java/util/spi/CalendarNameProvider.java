package java.util.spi;

import java.util.Locale;
import java.util.Map;

public abstract class CalendarNameProvider extends LocaleServiceProvider {
  public abstract String getDisplayName(String paramString, int paramInt1, int paramInt2, int paramInt3, Locale paramLocale);
  
  public abstract Map<String, Integer> getDisplayNames(String paramString, int paramInt1, int paramInt2, Locale paramLocale);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/spi/CalendarNameProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */