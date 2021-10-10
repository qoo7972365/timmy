package javax.accessibility;

import java.awt.Rectangle;

public interface AccessibleExtendedText {
  public static final int LINE = 4;
  
  public static final int ATTRIBUTE_RUN = 5;
  
  String getTextRange(int paramInt1, int paramInt2);
  
  AccessibleTextSequence getTextSequenceAt(int paramInt1, int paramInt2);
  
  AccessibleTextSequence getTextSequenceAfter(int paramInt1, int paramInt2);
  
  AccessibleTextSequence getTextSequenceBefore(int paramInt1, int paramInt2);
  
  Rectangle getTextBounds(int paramInt1, int paramInt2);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/accessibility/AccessibleExtendedText.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */