package edu.wpi.always;

import edu.wpi.disco.rt.*;
import org.w3c.dom.Document;

// note to future Will and Bahador: you need to think about replan requests
// and also, callbacks from relationshipManager when a new plan is ready
public interface IRelationshipManager {

   DiscoDocumentSet getLatestPlan ();

   Document getLatestPlanInDoc ();

   void afterInteraction (DiscoSynchronizedWrapper disco, float closeness,
         int time);
}
