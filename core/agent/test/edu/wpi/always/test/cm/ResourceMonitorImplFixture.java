package edu.wpi.always.test.cm;

import static org.junit.Assert.*;

import java.awt.*;
import java.util.*;

import org.joda.time.*;
import org.junit.*;

import edu.wpi.always.cm.*;
import edu.wpi.always.cm.primitives.*;
import edu.wpi.always.cm.realizer.*;

// TODO:
//	- Ignore primitives that use resources other than those in behavior

public class ResourceMonitorImplFixture {
	private PrimitiveBehaviorControlStub realizer;
	private ResourceMonitorImpl resMon;

	@Before
	public void setUp() {
		realizer = new PrimitiveBehaviorControlStub();
		resMon = new ResourceMonitorImpl(realizer);
	}

	@Test
	public void testBehaviorWithOnePrimitive() {
		assertNotNull(realizer.observer);

		DateTime someEarlierTime = DateTime.now().minusMinutes(1);

		realizer.observer.primitiveDone(realizer, new SpeechBehavior("something"));

		ArrayList<PrimitiveBehavior> primitives = new ArrayList<PrimitiveBehavior>();
		primitives.add(new SpeechBehavior("something"));

		assertTrue(resMon.allDone(primitives, someEarlierTime));

		DateTime someLaterTime = DateTime.now().plusMinutes(1);

		assertFalse(resMon.allDone(primitives, someLaterTime));
	}

	@Test
	public void testWithOnePrimitive_OnePrimitiveDoneWithDifferentParameterShouldNotCount() {
		DateTime someEarlierTime = DateTime.now().minusMinutes(1);

		realizer.observer.primitiveDone(realizer, new SpeechBehavior("something"));

		ArrayList<PrimitiveBehavior> primitives = new ArrayList<PrimitiveBehavior>();
		primitives.add(new SpeechBehavior("something else"));

		assertFalse(resMon.allDone(primitives, someEarlierTime));
	}

	@Test
	public void testWithTwoPrimitives() {
		DateTime someEarlierTime = DateTime.now().minusMinutes(1);

		ArrayList<PrimitiveBehavior> primitives = new ArrayList<PrimitiveBehavior>();
		primitives.add(new SpeechBehavior("Good"));
		primitives.add(new GazeBehavior(new Point(1, 1)));

		assertFalse(resMon.allDone(primitives, someEarlierTime));

		realizer.observer.primitiveDone(realizer, new SpeechBehavior("Good"));

		assertFalse(resMon.allDone(primitives, someEarlierTime));

		realizer.observer.primitiveDone(realizer,
				new GazeBehavior(new Point(1, 1)));

		assertTrue(resMon.allDone(primitives, someEarlierTime));
	}

	@Test
	public void primtivesShouldRunWithNoOtherPrimitivesUsingSameResourcesInBetween() {
		DateTime someEarlierTime = DateTime.now().minusMinutes(1);

		ArrayList<PrimitiveBehavior> primitives = new ArrayList<PrimitiveBehavior>();
		primitives.add(new SpeechBehavior("Good"));
		primitives.add(new GazeBehavior(new Point(1, 1)));

		realizer.observer.primitiveDone(realizer, new SpeechBehavior("Good"));
		realizer.observer.primitiveDone(realizer, new SpeechBehavior("something else"));
		realizer.observer.primitiveDone(realizer,
				new GazeBehavior(new Point(1, 1)));

		assertFalse(resMon.allDone(primitives, someEarlierTime));
	}
	
	@Test
	public void whenPrimitivesThatAreDoneTheSame_ShouldBeOk() {
		DateTime someEarlierTime = DateTime.now().minusMinutes(1);

		ArrayList<PrimitiveBehavior> primitives = new ArrayList<PrimitiveBehavior>();
		primitives.add(new SpeechBehavior("Good"));
		primitives.add(new GazeBehavior(new Point(1, 1)));

		realizer.observer.primitiveDone(realizer,
				new GazeBehavior(new Point(1, 1)));
		realizer.observer.primitiveDone(realizer, new SpeechBehavior("Good"));
		realizer.observer.primitiveDone(realizer, new SpeechBehavior("Good"));

		assertTrue(resMon.allDone(primitives, someEarlierTime));		
	}
}