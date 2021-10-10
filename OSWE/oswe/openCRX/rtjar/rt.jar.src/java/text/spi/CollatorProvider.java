package java.text.spi;

import java.text.Collator;
import java.util.Locale;
import java.util.spi.LocaleServiceProvider;

public abstract class CollatorProvider extends LocaleServiceProvider {
  public abstract Collator getInstance(Locale paramLocale);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/spi/CollatorProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */