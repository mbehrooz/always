package edu.wpi.always.cm.realizer;

public interface PrimitiveBehaviorControlObserver {

   /**
    * Called when a the realizer for pb reports that it is done Note: done for
    * primitive realizers does not mean that they are automatically dis-engaged
    * 
    * @param pb
    */
   void primitiveDone (PrimitiveBehaviorControl sender, PrimitiveBehavior pb);

   void primitiveStopped (PrimitiveBehaviorControl sender, PrimitiveBehavior pb);
}
