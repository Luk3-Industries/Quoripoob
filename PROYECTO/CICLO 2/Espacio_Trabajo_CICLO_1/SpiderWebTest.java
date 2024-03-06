import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The test class SpiderWebTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SpiderWebTest{
    private static SpiderWeb spiderWeb;
    
    @BeforeClass
    public static void beforeClass(){
    }
    
    @Before
    public void before(){
        spiderWeb = new SpiderWeb(20, 400);
        spiderWeb.makeInvisible();
        spiderWeb.addBridge("blue", 80, 1);
        spiderWeb.addBridge("red", 130, 2);
        spiderWeb.addBridge("magenta", 200, 3);
        spiderWeb.addBridge("green", 230, 2);
        spiderWeb.addBridge("orange", 270, 1);
        spiderWeb.addBridge("cyan", 50, 19);
        spiderWeb.addSpot("yellow", 2);
        spiderWeb.addSpot("aquamarine", 4);
        spiderWeb.addSpot("brown", 20);
    }
    
    @Test
    public void accordingDCShouldNotUseBridges(){
       //
       spiderWeb.spiderSit(1);
       String[] expected = {"orange", "green", "cyan"};
       spiderWeb.spiderWalk(true);
       assertArrayEquals(spiderWeb.unusedBridges(), expected);
       //
       spiderWeb.spiderSit(20);
       expected = new String[]{"red", "orange", "magenta", "green", "blue"};
       spiderWeb.spiderWalk(true);
       assertArrayEquals(spiderWeb.unusedBridges(), expected);
       //
       spiderWeb.spiderSit(4);
       expected = new String[]{"red", "blue", "cyan"};
       spiderWeb.spiderWalk(true);
       assertArrayEquals(spiderWeb.unusedBridges(), expected);
       
       
       //
       spiderWeb.spiderSit(3);
       expected = new String[]{"red", "orange"};
       spiderWeb.spiderWalk(true);
       assertNotEquals(spiderWeb.unusedBridges(), expected);
       //
       spiderWeb.spiderSit(19);
       expected = new String[]{"red", "orange", "magenta", "green"};
       spiderWeb.spiderWalk(true);
       assertNotEquals(spiderWeb.unusedBridges(), expected);
    }
    
    @Test
    public void accordingDCShouldShowTheSpiderPath(){
        int[] expected = {1, 2, 3, 4};
        spiderWeb.spiderWalk(true);
        assertArrayEquals(spiderWeb.spiderLastPath(), expected);
        spiderWeb.spiderSit(4);
        expected = new int[]{4, 3, 2, 1};
        spiderWeb.spiderWalk(true);
        assertArrayEquals(spiderWeb.spiderLastPath(), expected);
        spiderWeb.spiderSit(3);
        expected = new int[]{3, 2, 3};
        spiderWeb.spiderWalk(true);
        assertArrayEquals(spiderWeb.spiderLastPath(), expected);
        
        spiderWeb.spiderSit(20);
        expected = new int[]{19, 20};
        spiderWeb.spiderWalk(true);
        assertNotEquals(spiderWeb.spiderLastPath(), expected);
        spiderWeb.spiderSit(2);
        expected = new int[]{2, 1};
        spiderWeb.spiderWalk(true);
        assertNotEquals(spiderWeb.spiderLastPath(), expected);
    }
    
    @Test
    public void accordingDCShouldShowAllTheBridges(){
        //
        String[] expected = {"red", "orange", "magenta", "green", "blue", "cyan"};
        assertArrayEquals(spiderWeb.bridges(), expected);
        //
        spiderWeb.delBridge("red");
        expected = new String[]{"orange", "magenta", "green", "blue", "cyan"};
        assertArrayEquals(spiderWeb.bridges(), expected);
        //
        spiderWeb.delBridge("blue");
        expected = new String[]{"orange", "magenta", "green", "cyan"};
        assertArrayEquals(spiderWeb.bridges(), expected);
        
        
        //
        spiderWeb.addBridge("blue", 80, 1);
        expected = new String[]{"orange", "magenta", "green", "cyan"};
        assertNotEquals(spiderWeb.bridges(), expected);
        //
        spiderWeb.addBridge("red", 130, 2);
        expected = new String[]{"red", "orange", "magenta", "green", "cyan"};
        assertNotEquals(spiderWeb.bridges(), expected);
    }
    
    @Test
    public void accordingDCShouldShowTheCoordinatesOfTheBridge(){
        //
        double[] expected = {580.0, 400.0, 576.0845213036123, 375.2786404500042};
        assertArrayEquals(spiderWeb.bridge("blue"), expected, 0.01);
        //
        expected = new double[]{770.0, 400.0, 756.7852593996914, 316.5654115187642};
        assertArrayEquals(spiderWeb.bridge("orange"), expected, 0.01);
        
        
        //
        expected = new double[]{580.0, 400.0, 576.0845213036123, 375.2786404500042};
        assertNotEquals(spiderWeb.bridge("red"), expected);
        //
        expected = new double[]{770.0, 400.0, 755.7852593996914, 316.5654115187642};
        assertNotEquals(spiderWeb.bridge("orange"), expected);
    }
    
    @Test
    public void accordingDCShouldShowAllTheSpots(){
        //
        String[] expected = {"aquamarine", "yellow", "brown"};
        assertArrayEquals(spiderWeb.spots(), expected);
        //
        spiderWeb.delSpot("aquamarine");
        expected = new String[]{"yellow", "brown"};
        assertArrayEquals(spiderWeb.spots(), expected);
        //
        spiderWeb.delSpot("yellow");
        spiderWeb.delSpot("brown");
        expected = new String[]{};
        assertArrayEquals(spiderWeb.spots(), expected);  
        
        
        //
        spiderWeb.addSpot("aquamarine", 4);
        expected = new String[]{null};
        assertNotEquals(spiderWeb.spots(), expected);
        //
        spiderWeb.addSpot("yellow", 2);
        expected = new String[]{"aquamarine"};
        assertNotEquals(spiderWeb.spots(), expected);
        //
        spiderWeb.addSpot("brown", 20);
        expected = new String[]{"aquamarine", "yellow", "black"};
        assertNotEquals(spiderWeb.spots(), expected);
    }
    
    @Test
    public void accordingDCShouldShowTheCoordinatesOfTheSpot(){
        //
        double[] expected = {732.6141009169893, 732.6141009169893};
        assertArrayEquals(spiderWeb.spot("aquamarine"), expected, 0.01);
        //
        expected = new double[]{877.9226065180615, 877.9226065180615};
        assertArrayEquals(spiderWeb.spot("brown"), expected, 0.01);
        
        
        //
        expected = new double[]{732.6141009169893, 732.6141009169893};
        assertNotEquals(spiderWeb.spot("yellow"), expected);
        //
        expected = new double[]{876.9226065180615, 877.9226065180615};
        assertNotEquals(spiderWeb.spot("brown"), expected);
    }
    
    @Test
    public void accordingDCShouldReachSpot(){
        //
        String[] expected = {"aquamarine"};
        assertArrayEquals(spiderWeb.reachableSpots(), expected);
        //
        spiderWeb.spiderSit(2);
        expected = new String[]{"yellow"};
        assertArrayEquals(spiderWeb.reachableSpots(), expected);
        //
        spiderWeb.spiderSit(3);
        expected = new String[]{null};
        assertArrayEquals(spiderWeb.reachableSpots(), expected);
        
        
        //
        expected = new String[]{"brown"};
        assertNotEquals(spiderWeb.reachableSpots(), expected);
        //
        spiderWeb.spiderSit(19);
        expected = new String[]{"yellow"};
        assertNotEquals(spiderWeb.reachableSpots(), expected);
        //
        expected = new String[]{null};
        assertNotEquals(spiderWeb.reachableSpots(), expected);
    }
    
    
     @Test
    public void accordingDCShouldExpand(){
        
        int originalL =  spiderWeb.getStrandLength();
        spiderWeb.enlarge(50);
        assertEquals((spiderWeb.getStrandLength()), originalL + 50*originalL/100);
        
        originalL =  spiderWeb.getStrandLength();
        spiderWeb.enlarge(87);
        assertEquals((spiderWeb.getStrandLength()), originalL + 87*originalL/100);
        
        originalL =  spiderWeb.getStrandLength();
        spiderWeb.enlarge(50);
        assertNotEquals((spiderWeb.getStrandLength()), originalL);
        
        originalL =  spiderWeb.getStrandLength();
        spiderWeb.enlarge(87);
        assertNotEquals((spiderWeb.getStrandLength()), originalL);
    }
    
    @Test
    public void accordingDCShouldAddStrand(){
        int originalStrands = 20;
        for (int i = 1; i<= 5; i++){
            spiderWeb.addStrand();
            assertEquals(spiderWeb.getStrandsNumber(),20+i);
            assertEquals(spiderWeb.getBridges(),6);
            assertEquals(spiderWeb.getSpots(),3);
        }
        
        for (int i = 1; i<= 5; i++){
            int strands = spiderWeb.getStrandsNumber();
            spiderWeb.addStrand();
            assertNotEquals(spiderWeb.getStrandsNumber(),strands);
            assertNotEquals(spiderWeb.getBridges(),6+i);
            assertNotEquals(spiderWeb.getSpots(),3+i);
        }
    }
}
