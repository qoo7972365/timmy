package sun.java2d.cmm;

import java.awt.color.ICC_Profile;

public interface PCMM {
  Profile loadProfile(byte[] paramArrayOfbyte);
  
  void freeProfile(Profile paramProfile);
  
  int getProfileSize(Profile paramProfile);
  
  void getProfileData(Profile paramProfile, byte[] paramArrayOfbyte);
  
  void getTagData(Profile paramProfile, int paramInt, byte[] paramArrayOfbyte);
  
  int getTagSize(Profile paramProfile, int paramInt);
  
  void setTagData(Profile paramProfile, int paramInt, byte[] paramArrayOfbyte);
  
  ColorTransform createTransform(ICC_Profile paramICC_Profile, int paramInt1, int paramInt2);
  
  ColorTransform createTransform(ColorTransform[] paramArrayOfColorTransform);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/PCMM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */