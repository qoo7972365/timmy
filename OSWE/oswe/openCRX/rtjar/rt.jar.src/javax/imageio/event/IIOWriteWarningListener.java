package javax.imageio.event;

import java.util.EventListener;
import javax.imageio.ImageWriter;

public interface IIOWriteWarningListener extends EventListener {
  void warningOccurred(ImageWriter paramImageWriter, int paramInt, String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/event/IIOWriteWarningListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */