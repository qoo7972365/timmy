package javax.imageio;

import javax.imageio.metadata.IIOMetadata;

public interface ImageTranscoder {
  IIOMetadata convertStreamMetadata(IIOMetadata paramIIOMetadata, ImageWriteParam paramImageWriteParam);
  
  IIOMetadata convertImageMetadata(IIOMetadata paramIIOMetadata, ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/ImageTranscoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */