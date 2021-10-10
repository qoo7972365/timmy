package jdk.internal.org.objectweb.asm.util;

import java.util.Map;
import jdk.internal.org.objectweb.asm.Label;

public interface Textifiable {
  void textify(StringBuffer paramStringBuffer, Map<Label, String> paramMap);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/Textifiable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */