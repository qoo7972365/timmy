package javax.imageio.event;

import java.awt.image.BufferedImage;
import java.util.EventListener;
import javax.imageio.ImageReader;

public interface IIOReadUpdateListener extends EventListener {
  void passStarted(ImageReader paramImageReader, BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int[] paramArrayOfint);
  
  void imageUpdate(ImageReader paramImageReader, BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint);
  
  void passComplete(ImageReader paramImageReader, BufferedImage paramBufferedImage);
  
  void thumbnailPassStarted(ImageReader paramImageReader, BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int[] paramArrayOfint);
  
  void thumbnailUpdate(ImageReader paramImageReader, BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint);
  
  void thumbnailPassComplete(ImageReader paramImageReader, BufferedImage paramBufferedImage);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/event/IIOReadUpdateListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */