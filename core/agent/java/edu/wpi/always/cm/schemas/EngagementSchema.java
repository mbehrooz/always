package edu.wpi.always.cm.schemas;

import java.util.Arrays;
import edu.wpi.always.cm.perceptors.EngagementPerception;
import edu.wpi.always.cm.perceptors.EngagementPerception.EngagementState;
import edu.wpi.always.cm.perceptors.EngagementPerceptor;
import edu.wpi.always.cm.perceptors.FacePerception;
import edu.wpi.always.cm.perceptors.FacePerceptor;
import edu.wpi.always.cm.primitives.FaceTrackBehavior;
import edu.wpi.always.cm.primitives.IdleBehavior;
import edu.wpi.disco.rt.ResourceMonitor;
import edu.wpi.disco.rt.behavior.Behavior;
import edu.wpi.disco.rt.behavior.BehaviorHistory;
import edu.wpi.disco.rt.behavior.BehaviorMetadata;
import edu.wpi.disco.rt.behavior.BehaviorMetadataBuilder;
import edu.wpi.disco.rt.behavior.BehaviorProposalReceiver;
import edu.wpi.disco.rt.behavior.SpeechBehavior;
import edu.wpi.disco.rt.menu.*;
import edu.wpi.disco.rt.schema.SchemaBase;
import edu.wpi.disco.rt.schema.SchemaManager;

public class EngagementSchema extends SchemaBase {

   private final MenuTurnStateMachine stateMachine;
   private final EngagementPerceptor engagementPerceptor;
   private final FacePerceptor facePerceptor;

   public EngagementSchema (BehaviorProposalReceiver behaviorReceiver,
         EngagementPerceptor engagementPerceptor,
         FacePerceptor facePerceptor, BehaviorHistory behaviorHistory,
         ResourceMonitor resourceMonitor, MenuPerceptor menuPerceptor) {
      super(behaviorReceiver, behaviorHistory);
      this.engagementPerceptor = engagementPerceptor;
      this.facePerceptor = facePerceptor;
      stateMachine = new MenuTurnStateMachine(behaviorHistory, resourceMonitor,
            menuPerceptor, new RepeatMenuTimeoutHandler<AdjacencyPair.Context>());
      stateMachine.setSpecificityMetadata(.9);
   }

   @SuppressWarnings("unused")
   private EngagementState lastState;
   
   @Override
   public void run () {
      BehaviorMetadata m = new BehaviorMetadataBuilder().specificity(0.05)
            .build();
      EngagementPerception engagementPerception = engagementPerceptor
            .getLatest();
      FacePerception facePerception = facePerceptor.getLatest();
      if ( engagementPerception != null ) {
         switch (engagementPerception.getState()) {
         case Idle:
            propose(Behavior.newInstance(new IdleBehavior(false)), m);
            break;
         case Attention:
            // Light up screen?
            //if ( facePerception != null && facePerception.getPoint() != null )
            //   propose(new FaceTrackBehavior(), m);
            break;
         case Initiation:
            propose(Behavior.newInstance(new SpeechBehavior("Hi"), new MenuBehavior(Arrays.asList("Hi"))), m);
            break;
         case Engaged:
            // FIXME Disabled engagement dialogue for testing...
            //if ( lastState != EngagementState.Engaged )
            //   stateMachine.setAdjacencyPair(new EngagementDialog(schemaManager));
            //propose(stateMachine);
            break;
         case Recovering:
            propose(Behavior.newInstance(new FaceTrackBehavior(),
                  new SpeechBehavior("Are you still there"), new MenuBehavior(
                        Arrays.asList("Yes"))), m);
            break;
         }
         lastState = engagementPerception.getState();
      } else
         lastState = null;
   }
}
