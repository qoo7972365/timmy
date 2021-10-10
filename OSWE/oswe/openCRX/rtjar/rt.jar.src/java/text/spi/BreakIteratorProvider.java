package java.text.spi;

import java.text.BreakIterator;
import java.util.Locale;
import java.util.spi.LocaleServiceProvider;

public abstract class BreakIteratorProvider extends LocaleServiceProvider {
  public abstract BreakIterator getWordInstance(Locale paramLocale);
  
  public abstract BreakIterator getLineInstance(Locale paramLocale);
  
  public abstract BreakIterator getCharacterInstance(Locale paramLocale);
  
  public abstract BreakIterator getSentenceInstance(Locale paramLocale);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/spi/BreakIteratorProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */