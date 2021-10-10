package com.sun.corba.se.spi.ior;

import com.sun.corba.se.spi.orb.ORB;
import java.util.Iterator;
import java.util.List;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.IOP.TaggedComponent;

public interface TaggedProfileTemplate extends List, Identifiable, WriteContents, MakeImmutable {
  Iterator iteratorById(int paramInt);
  
  TaggedProfile create(ObjectKeyTemplate paramObjectKeyTemplate, ObjectId paramObjectId);
  
  void write(ObjectKeyTemplate paramObjectKeyTemplate, ObjectId paramObjectId, OutputStream paramOutputStream);
  
  boolean isEquivalent(TaggedProfileTemplate paramTaggedProfileTemplate);
  
  TaggedComponent[] getIOPComponents(ORB paramORB, int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/TaggedProfileTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */