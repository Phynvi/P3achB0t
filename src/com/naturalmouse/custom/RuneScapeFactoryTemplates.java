package com.naturalmouse.custom;

import com.naturalmouse.api.MouseMotionFactory;
import com.naturalmouse.api.SpeedManager;
import com.naturalmouse.support.DefaultMouseMotionNature;
import com.naturalmouse.support.DefaultNoiseProvider;
import com.naturalmouse.support.DefaultOvershootManager;
import com.naturalmouse.support.DefaultSpeedManager;
import com.naturalmouse.support.DoublePoint;
import com.naturalmouse.support.Flow;
import com.naturalmouse.support.MouseMotionNature;
import com.naturalmouse.support.SinusoidalDeviationProvider;
import com.naturalmouse.util.FlowTemplates;
import com.naturalmouse.util.Pair;
import com.p3achb0t._runestar_interfaces.Client;

import java.applet.Applet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RuneScapeFactoryTemplates {
    /**
     * <h1>Stereotypical granny using a computer with non-optical mouse from the 90s.</h1>
     * Low speed, variating flow, lots of noise in movement.
     *
     * @return the factory
     */
    public static MouseMotionFactory createGrannyMotionFactory(Client client, Applet applet) {
        return createGrannyMotionFactory(new RuneScapeMouseMotionNature(client, applet));
    }

    /**
     * <h1>Stereotypical granny using a computer with non-optical mouse from the 90s.</h1>
     * Low speed, variating flow, lots of noise in movement.
     * @param nature the nature for the template to be configured on
     * @return the factory
     */
    public static MouseMotionFactory createGrannyMotionFactory(MouseMotionNature nature) {
        MouseMotionFactory factory = new MouseMotionFactory(nature);
        List<Flow> flows = new ArrayList<>(Arrays.asList(
                new Flow(FlowTemplates.jaggedFlow()),
                new Flow(FlowTemplates.random()),
                new Flow(FlowTemplates.interruptedFlow()),
                new Flow(FlowTemplates.interruptedFlow2()),
                new Flow(FlowTemplates.adjustingFlow()),
                new Flow(FlowTemplates.stoppingFlow())
        ));
        DefaultSpeedManager manager = new DefaultSpeedManager(flows);
        factory.setDeviationProvider(new SinusoidalDeviationProvider(9));
        factory.setNoiseProvider(new DefaultNoiseProvider(1.6));
        factory.getNature().setReactionTimeBaseMs(100);

        DefaultOvershootManager overshootManager = (DefaultOvershootManager) factory.getOvershootManager();
        overshootManager.setOvershoots(3);
        overshootManager.setMinDistanceForOvershoots(3);
        overshootManager.setMinOvershootMovementMs(400);
        overshootManager.setOvershootRandomModifierDivider(DefaultOvershootManager.OVERSHOOT_RANDOM_MODIFIER_DIVIDER / 2);
        overshootManager.setOvershootSpeedupDivider(DefaultOvershootManager.OVERSHOOT_SPEEDUP_DIVIDER * 2);

        factory.getNature().setTimeToStepsDivider(DefaultMouseMotionNature.TIME_TO_STEPS_DIVIDER - 2);
        manager.setMouseMovementBaseTimeMs(1000);
        factory.setSpeedManager(manager);
        return factory;
    }

    /**
     * <h1>Robotic fluent movement.</h1>
     * Custom speed, constant movement, no mistakes, no overshoots.
     *
     * @param motionTimeMsPer100Pixels approximate time a movement takes per 100 pixels of travelling
     * @return the factory
     */
    public static MouseMotionFactory createDemoRobotMotionFactory(long motionTimeMsPer100Pixels) {
        return createDemoRobotMotionFactory(new DefaultMouseMotionNature(), motionTimeMsPer100Pixels);
    }

    /**
     * <h1>Robotic fluent movement.</h1>
     * Custom speed, constant movement, no mistakes, no overshoots.
     *
     * @param nature the nature for the template to be configured on
     * @param motionTimeMsPer100Pixels approximate time a movement takes per 100 pixels of travelling
     * @return the factory
     */
    public static MouseMotionFactory createDemoRobotMotionFactory(
            MouseMotionNature nature, long motionTimeMsPer100Pixels
    ) {
        MouseMotionFactory factory = new MouseMotionFactory(nature);
        final Flow flow = new Flow(FlowTemplates.constantSpeed());
        double timePerPixel = motionTimeMsPer100Pixels / 100d;
        SpeedManager manager = distance -> new Pair<>(flow, (long) (timePerPixel * distance));
        factory.setDeviationProvider((totalDistanceInPixels, completionFraction) -> DoublePoint.ZERO);
        factory.setNoiseProvider(((random, xStepSize, yStepSize) -> DoublePoint.ZERO));

        DefaultOvershootManager overshootManager = (DefaultOvershootManager) factory.getOvershootManager();
        overshootManager.setOvershoots(0);

        factory.setSpeedManager(manager);
        return factory;
    }

    /**
     * <h1>Gamer with fast reflexes and quick mouse movements.</h1>
     * Quick movement, low noise, some deviation, lots of overshoots.
     *
     * @return the factory
     */
    public static MouseMotionFactory createFastGamerMotionFactory(Client client, Applet applet) {
        return createFastGamerMotionFactory(new RuneScapeMouseMotionNature(client, applet));
    }

    /**
     * <h1>Gamer with fast reflexes and quick mouse movements.</h1>
     * Quick movement, low noise, some deviation, lots of overshoots.
     * @param nature the nature for the template to be configured on
     * @return the factory
     */
    public static MouseMotionFactory createFastGamerMotionFactory(MouseMotionNature nature) {
        MouseMotionFactory factory = new MouseMotionFactory(nature);
        List<Flow> flows = new ArrayList<>(Arrays.asList(
                new Flow(FlowTemplates.variatingFlow()),
                new Flow(FlowTemplates.slowStartupFlow()),
                new Flow(FlowTemplates.slowStartup2Flow()),
                new Flow(FlowTemplates.adjustingFlow()),
                new Flow(FlowTemplates.jaggedFlow())
        ));
        DefaultSpeedManager manager = new DefaultSpeedManager(flows);
        factory.setDeviationProvider(new SinusoidalDeviationProvider(SinusoidalDeviationProvider.DEFAULT_SLOPE_DIVIDER));
        factory.setNoiseProvider(new DefaultNoiseProvider(DefaultNoiseProvider.DEFAULT_NOISINESS_DIVIDER));
        factory.getNature().setReactionTimeVariationMs(100);
        manager.setMouseMovementBaseTimeMs(250);

        DefaultOvershootManager overshootManager = (DefaultOvershootManager) factory.getOvershootManager();
        overshootManager.setOvershoots(4);

        factory.setSpeedManager(manager);
        return factory;
    }
    /**
     * <h1>Standard computer user with average speed and movement mistakes</h1>
     * medium noise, medium speed, medium noise and deviation.
     *
     * @return the factory
     */
    public static MouseMotionFactory createAverageComputerUserMotionFactory(Object obj) {
        Client client = (Client) obj;
        Applet applet = (Applet)obj;
        return createAverageComputerUserMotionFactory(new RuneScapeMouseMotionNature(client, applet));
    }
    /**
     * <h1>Standard computer user with average speed and movement mistakes</h1>
     * medium noise, medium speed, medium noise and deviation.
     *
     * @param nature the nature for the template to be configured on
     * @return the factory
     */
    public static MouseMotionFactory createAverageComputerUserMotionFactory(MouseMotionNature nature) {
        MouseMotionFactory factory = new MouseMotionFactory(nature);
        List<Flow> flows = new ArrayList<>(Arrays.asList(
                new Flow(FlowTemplates.variatingFlow()),
                new Flow(FlowTemplates.interruptedFlow()),
                new Flow(FlowTemplates.interruptedFlow2()),
                new Flow(FlowTemplates.slowStartupFlow()),
                new Flow(FlowTemplates.slowStartup2Flow()),
                new Flow(FlowTemplates.adjustingFlow()),
                new Flow(FlowTemplates.jaggedFlow()),
                new Flow(FlowTemplates.stoppingFlow())
        ));
        DefaultSpeedManager manager = new DefaultSpeedManager(flows);
        factory.setDeviationProvider(new SinusoidalDeviationProvider(SinusoidalDeviationProvider.DEFAULT_SLOPE_DIVIDER));
        factory.setNoiseProvider(new DefaultNoiseProvider(DefaultNoiseProvider.DEFAULT_NOISINESS_DIVIDER));
        factory.getNature().setReactionTimeVariationMs(110);
        manager.setMouseMovementBaseTimeMs(400);

        DefaultOvershootManager overshootManager = (DefaultOvershootManager) factory.getOvershootManager();
        overshootManager.setOvershoots(4);

        factory.setSpeedManager(manager);
        return factory;
    }
}
