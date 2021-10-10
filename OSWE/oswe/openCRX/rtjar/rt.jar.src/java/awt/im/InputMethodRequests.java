package java.awt.im;

import java.awt.Rectangle;
import java.awt.font.TextHitInfo;
import java.text.AttributedCharacterIterator;

public interface InputMethodRequests {
  Rectangle getTextLocation(TextHitInfo paramTextHitInfo);
  
  TextHitInfo getLocationOffset(int paramInt1, int paramInt2);
  
  int getInsertPositionOffset();
  
  AttributedCharacterIterator getCommittedText(int paramInt1, int paramInt2, AttributedCharacterIterator.Attribute[] paramArrayOfAttribute);
  
  int getCommittedTextLength();
  
  AttributedCharacterIterator cancelLatestCommittedText(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute);
  
  AttributedCharacterIterator getSelectedText(AttributedCharacterIterator.Attribute[] paramArrayOfAttribute);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/im/InputMethodRequests.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */