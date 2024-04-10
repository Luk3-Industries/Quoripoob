import java.util.HashMap;

/**
 * This Spot's subclass has the same behaviors that a normal Spot,
 * except that when the spider reaches this spot, it jump to the next strand.
 * 
 * @author (Daniel Diaz y Miguel Motta)
 * @version (CICLO 4 / 2024 - 01)
 */

public class Bouncy extends Spot {

    /**
     * Constructor for objects of class Bouncy
     */
    public Bouncy(String color, int strand, Web web,  boolean isVisible) {
        super(color, strand, web, isVisible);
    }

    /**
     * Add the spot to the web
     * 
     * @param strands       the strands of the web
     */
    @Override
    public void addSpot(HashMap<Integer, Strand> strands) {
        super.addSpot(strands);
        draw();
    }

    /**
     * Its the behavior of the spot
     */
    @Override
    public void act() {
        this.web.spiderJump();
    }
}
