package jdk.internal.org.objectweb.asm.util;

import java.util.Map;
import jdk.internal.org.objectweb.asm.Label;

public interface ASMifiable {
  void asmify(StringBuffer paramStringBuffer, String paramString, Map<Label, String> paramMap);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/util/ASMifiable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */