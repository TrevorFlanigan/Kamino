package cpen221.mp2;

import cpen221.mp2.controllers.Kamino;
import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import cpen221.mp2.spaceship.MillenniumFalcon;
import cpen221.mp2.views.BenchmarkView;
import cpen221.mp2.views.CLIView;
import cpen221.mp2.views.View;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class KaminoTest {
    @Test
    public void testKamino(){
        int count = 0;
        while(count < 2) {
            count++;
            try {
                Random RNG = new Random();
                long seed = RNG.nextLong();
                System.out.println(seed);
                View view = new BenchmarkView();
                Kamino k = new Kamino(seed, new MillenniumFalcon(), view);
            }
            catch(Exception e){
                System.out.println("Error!");
                fail();
            }
        }
    }

    @Test
    public void testManyHunts(){
        int count = 0;
        while(count < 1) {
            count++;
            try {
                Random RNG = new Random();
                long seed = RNG.nextLong();
                System.out.println(seed);
                View view = new BenchmarkView();
                Kamino k = new Kamino(seed, new MillenniumFalcon(), view);
            }
            catch(Exception e){
                System.out.println("Error!");
                fail();
            }
        }
    }

    @Test
    public void testDeadends(){
        int count = 0;
        while(count < 1) {
            count++;
            try {
                Random RNG = new Random();
                long seed = -3296189656239142911L;
                View view = new BenchmarkView();
                Kamino k = new Kamino(seed, new MillenniumFalcon(), view);
            }
            catch(Exception e){
                System.out.println("Error!");
                fail();
            }
        }
    }

    @Test
    public void testFailsafe(){
        int count = 0;
        while(count < 1) {
            count++;
            try {
                Random RNG = new Random();
                long seed = 6170258497722289779L;
                View view = new BenchmarkView();
                Kamino k = new Kamino(seed, new MillenniumFalcon(), view);
            }
            catch(Exception e){
                System.out.println("Error!");
                fail();
            }
        }
    }

    @Test
    public void testGotAllSpice(){
        int count = 0;
        while(count < 1) {
            count++;
            try {
                Random RNG = new Random();
                long seed = -2138870951789271090L;
                View view = new BenchmarkView();
                Kamino k = new Kamino(seed, new MillenniumFalcon(), view);
            }
            catch(Exception e){
                System.out.println("Error!");
                fail();
            }
        }
    }

    @Test
    public void testGotNoSpice(){
        int count = 0;
        while(count < 1) {
            count++;
            try {
                Random RNG = new Random();
                long seed = 7668854775586131845L;
                View view = new BenchmarkView();
                Kamino k = new Kamino(seed, new MillenniumFalcon(), view);
            }
            catch(Exception e){
                System.out.println("Error!");
                fail();
            }
        }
    }

}
