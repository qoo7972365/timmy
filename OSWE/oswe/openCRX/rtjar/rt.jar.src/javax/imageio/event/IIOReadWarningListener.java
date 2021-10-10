package javax.imageio.event;

import java.util.EventListener;
import javax.imageio.ImageReader;

public interface IIOReadWarningListener extends EventListener {
  void warningOccurred(ImageReader paramImageReader, String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/event/IIOReadWarningListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */