package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.models.Link;
import cpen221.mp2.models.Planet;
import cpen221.mp2.models.PlanetStatus;
import cpen221.mp2.util.Heap;

import java.util.*;

/**
 * An instance implements the methods needed to complete the mission.
 */
public class MillenniumFalcon implements Spaceship {
    long startTime = System.nanoTime(); // start time of rescue phase

    /**
     * Moves the MillenniumFalcon spaceship from the current planet
     * throughout the graph until the spaceship is on the planet Kamino
     *
     * @param state The current state of the MillenniumFalcon within the graph
     * @requires a fully connected graph that contains Kamino
     * @modifies state
     */
    @Override
    public void hunt(HunterStage state) {

        PlanetStatus[] nStats;

        double maxSignal;
        int maxId;
        int count = 0;
        Set<Integer> deadEnds = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> blackList = new HashSet<>();

        while (!state.onKamino()) {
            visited.add(state.currentID());
            nStats = state.neighbors();
            ArrayList<PlanetStatus> stats = new ArrayList<>();
            for (PlanetStatus ps : nStats) {
                if (!deadEnds.contains(ps.id())) {
                    stats.add(ps);
                }
            }
            if (stats.size() == 1) {
                deadEnds.add(state.currentID());
                state.moveTo(stats.get(0).id());
            } else {
                maxId = 0;
                maxSignal = 0;
                for (PlanetStatus s : nStats) {
                    if (s.signal() >= maxSignal
                            && !deadEnds.contains(s.id())
                            && !visited.contains(s.id())
                            && !blackList.contains(s.id())) {
                        maxId = s.id();
                        maxSignal = s.signal();
                    }
                }
                if (maxSignal == 0) {
                    deadEnds.add(state.currentID());
                    blackList.add(state.currentID());
                    if (stats.isEmpty()) {
                        count++;
                        Set<Integer> allVisited = new HashSet<>(visited);
                        hunt(state, blackList, count, allVisited);
                        return;
                    } else {
                        state.moveTo(stats.get(0).id());
                    }
                } else {
                    state.moveTo(maxId);
                }
            }
        }
    }


    /**
     * Helper method for hunt.  Continues to move the MillenniumFalcon spaceship from the current
     * planet throughout the graph until the spaceship is on the planet Kamino
     *
     * @param state      The current state of the MillenniumFalcon within the graph
     * @param blackList  A set of planet ids to not return to
     * @param count      A count of how many times the helper method has been run
     * @param allVisited A set of all the planets that have been visited by the
     *                   MillenniumFalcon
     * @requires a fully connected graph that contains Kamino
     * @modifies state, blacklist, allVisited, count
     */
    public void hunt(HunterStage state, Set<Integer> blackList, int count,
                     Set<Integer> allVisited) {

        PlanetStatus[] nStats;

        double maxSignal;
        int maxId;
        Set<Integer> deadEnds = new HashSet<>();
        Set<Integer> visited = new HashSet<>();


        if (count > 5) {
            dFS(state, new HashSet<>(), state.currentID());
            return;
        }


        while (!state.onKamino()) {
            visited.add(state.currentID());
            nStats = state.neighbors();
            ArrayList<PlanetStatus> stats = new ArrayList<>();
            for (PlanetStatus ps : nStats) {
                if (!deadEnds.contains(ps.id())) {
                    stats.add(ps);
                }
            }
            if (stats.size() == 1) {
                deadEnds.add(state.currentID());
                state.moveTo(stats.get(0).id());
            } else {
                maxId = 0;
                maxSignal = 0;
                for (PlanetStatus s : nStats) {
                    if (s.signal() >= maxSignal
                            && !deadEnds.contains(s.id())
                            && !visited.contains(s.id())
                            && !blackList.contains(s.id())) {
                        maxId = s.id();
                        maxSignal = s.signal();
                    }
                }
                if (maxSignal == 0) {
                    deadEnds.add(state.currentID());
                    if (stats.isEmpty()) {
                        blackList.add(state.currentID());
                        count++;
                        allVisited.addAll(visited);
                        hunt(state, blackList, count, allVisited);
                        return;
                    } else {
                        state.moveTo(stats.get(0).id());
                    }
                } else {
                    state.moveTo(maxId);
                }
            }
        }
    }

    /**
     * Helper method for hunt. Performs a Depth-First-Search until the
     * spaceship is on Kamino
     *
     * @param state      The current state of the MillenniumFalcon within the graph
     * @param discovered A set of planet ids that have been visited in the Depth First Search
     * @param previous   The previous id of the planet the spaceship has last been on
     * @requires a fully connected graph that contains Kamino
     * @modifies state, discovered, previous
     */
    private void dFS(HunterStage state, Set<Integer> discovered, int previous) {
        int here = state.currentID();
        if (state.onKamino()) {
            return;
        } else {
            discovered.add(state.currentID());
            PlanetStatus[] adj = state.neighbors();
            for (PlanetStatus ps : adj) {
                if (!discovered.contains(ps.id())) {
                    if (state.onKamino()) {
                        return;
                    }
                    state.moveTo(ps.id());
                    dFS(state, discovered, here);
                }
            }

            if (state.currentID() == here) {
                state.moveTo(previous);
            }

        }
    }


    /**
     * Moves the MillenniumFalcon spaceship from the current planet
     * throughout the graph collecting spice before returning the spaceship to Earth.
     *
     * @param state The current state of the MillenniumFalcon within the graph
     * @requires a fully connected graph that contains Earth
     * @modifies state
     */
    @Override
    public void gather(GathererStage state) {

        ImGraph graph = state.planetGraph();
        Planet kamino = state.currentPlanet();
        Planet earth = state.earth();
        Set<Planet> allPlanets = state.planets();
        Set<Planet> visited = new HashSet<>();
        TreeMap<Double, Planet> scores = new TreeMap<>();
        allPlanets.remove(kamino);
        allPlanets.remove(earth);

        for (Planet p : allPlanets) {
            double score = 0;
            score += p.spice();
            scores.put(score, p);
        }

        scores.put(0.0, earth);
        Map descendingScores = scores.descendingMap();
        ArrayList<Planet> bestPlanets = new ArrayList<>(descendingScores.values());

        while (state.fuelRemaining() > 0) {
            visited.add(state.currentPlanet());
            for (Planet p : bestPlanets) {
                if (p.equals(state.currentPlanet()) || visited.contains(p)) {
                    continue;
                }
                double distToEarth = graph.pathLength(graph.shortestPath(p, earth));
                double distFromHere = graph.pathLength(graph.shortestPath(state.currentPlanet(), p));
                if (distToEarth + distFromHere <= state.fuelRemaining()) {
                    moveOnPath(graph.shortestPath(state.currentPlanet(), p), state, visited);
                    if (p.equals(earth)) {
                        return;
                    }
                } else {
                    if (state.currentPlanet().equals(kamino)) {
                        continue;
                    }
                    moveOnPath(graph.shortestPath(state.currentPlanet(), state.earth()), state, visited);
                    return;
                }
            }

        }
    }

    /**
     * Helper method for gather.  Moves the MillenniumFalcon spaceship from the current planet
     * throughout the graph on the given path
     *
     * @param path    A valid path that lists planets to move on in order.
     * @param state   The current state of the MillenniumFalcon within the graph
     * @param visited A set of planets containing all planets visited in the gather stage
     * @requires a valid path containing the start planet and end planet that lists
     * in order the Planets to visit.
     * @modifies state, visited
     */
    private void moveOnPath(List<Planet> path, GathererStage state, Set<Planet> visited) {
        for (int i = 1; i < path.size(); i++) {
            state.moveTo(path.get(i));
            visited.add(path.get(i));
        }
    }
}

