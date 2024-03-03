import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Create a spider.
 * 
 * @author Daniel Diaz && Miguel Motta
 * @version (a version number or a date)
 */
public class Spider
{
    private Ellipse2D.Double body;
    private int strand = 0;
    private int distance = 0;
    private ArrayList<Integer> lastPath;
    private HashMap<String, Bridge> usedBridges;
    
    private Canvas canvas;

    /**
     * Constructor for objects of class Spider
     */
    public Spider(){
        canvas = Canvas.getCanvas();
        body = new Ellipse2D.Double(canvas.getCenterX() - 7.5, canvas.getCenterY() - 7.5, 15, 15);
        this.strand = 1;
        this.distance = 0;
        lastPath = new ArrayList<>();
        usedBridges = new HashMap<>();
        makeVisible();
    }
    
    /**
     * Seat the spider in the center with reference to a specific strand
     * @param   strand  the strand to wich the spider will refer
     */
    public void spiderSit(int strand){
        this.body.setFrame(canvas.getCenterX() - 7.5, canvas.getCenterY() - 7.5, 15, 15);
        makeVisible();
        this.strand = strand;
        this.distance = 0;
        this.lastPath.clear();
        this.usedBridges.clear();
    }
    
    /**
     * The spider walks through the strands and bridges
     * @param   strands the existing strands in the web
     * @param   bridges the existing bridges in the web
     */
    public void spiderWalk(HashMap<Integer, Strand> strands, HashMap<String, Bridge> bridges){
        double x0 = strands.get(this.strand).getBody().getX1();
        double y0 = strands.get(this.strand).getBody().getY1();
        double x1, y1, x2, y2;
        Bridge bridge;
        
        do{
            bridge = bridgeToGo(bridges);
            this.lastPath.add(this.strand);
            if(bridge != null){
                if(bridge.getInicialStrand() == this.strand){
                    x1 = bridge.getBody().getX1();
                    y1 = bridge.getBody().getY1();
                    x2 = bridge.getBody().getX2();
                    y2 = bridge.getBody().getY2();
                    this.strand = bridge.getFinalStrand();
                }else{
                    x1 = bridge.getBody().getX2();
                    y1 = bridge.getBody().getY2();
                    x2 = bridge.getBody().getX1();
                    y2 = bridge.getBody().getY1();
                    this.strand = bridge.getInicialStrand();
                }
                
                moveSpider(x0, y0, x1, y1);
                moveSpider(x1, y1, x2, y2);
                this.distance = bridge.getDistance();
                usedBridges.put(bridge.getColor(), bridge);
                x0 = x2;
                y0 = y2;
            }
        }while(bridge != null);
        
        moveSpider(x0, y0, strands.get(this.strand).getBody().getX2(), strands.get(this.strand).getBody().getY2());
    }
    
    /*
     * Move the spider trough a line
     * @param   x1  the starting x position
     * @param   y1  the starting y position
     * @param   x2  the x position of arrival
     * @param   y2  the x position of arrival
     */
    private void moveSpider(double x1, double y1, double x2, double y2){
        for(double i = 0; i <= 1; i += 0.01){
            Point2D.Double point = parameterizedSegment(x1, y1, x2, y2, i);
            this.body.setFrame(point.getX() - 7.5, point.getY() - 7.5, 15, 15);
            canvas.wait(16);
            makeVisible();
        }
    }
    
    /*
     * Return a point belonging to the line
     * @param   x1  the initial x point
     * @param   y1  the initial y point
     * @param   x2  the final x point
     * @param   y2  the final y point
     * @param   t   the parameter of the parameterization
     * @return  a point belonging to the line
     */
    private Point2D.Double parameterizedSegment(double x1, double y1, double x2, double y2, double t){
        double x = x1 + t * (x2 - x1);
        double y = y1 + t * (y2 - y1);
        return new Point2D.Double(x, y);
    }
    
    /*
     * Return the bridge closest to the spider
     * @param   bridges   the existing bridges in the web
     * @return  the bridge closest to the spider
     */
    private Bridge bridgeToGo(HashMap<String, Bridge> bridges){
        ArrayList<Bridge> bridgesInStrand = new ArrayList<>();
        
        for(Bridge bridge : bridges.values()){
            bridgesInStrand.add((bridge.getInicialStrand() == this.strand || bridge.getFinalStrand() == this.strand) ? bridge : null);
        }
        
        double minDistance = Double.MAX_VALUE;
        Bridge selectedBridge = null;
        
        for(Bridge bridge : bridgesInStrand){
            if(bridge != null){
                double distance = bridge.getDistance();
                if (distance > this.distance && distance < minDistance) {
                    minDistance = distance;
                    selectedBridge = bridge;
                }
            }
        }
        
        return (selectedBridge != null) ? selectedBridge : null;
    }
    
    /**
     * Return the path along wich the spider walked
     * @return  the strands where the spider walked
     */ 
    public int[] getLastPath(){
        int[] output = new int[lastPath.size()];
        for(int i = 0; i < lastPath.size(); i++){
            output[i] = lastPath.get(i);
        }
        return output;
    }

    /**
     * Return the spots that the spider can reach
     * @param bridges   the existing bridges in the web
     * @param spots     the existing spots in the web
     * @return          the spots color that the spider can reach
     */
    public String[] reachableSpots(HashMap<String, Bridge> bridges, HashMap<String, Spot> spots){
        String[] reachableSpots = new String[1];
        Bridge bridge;
        int tempStrand = this.strand;
        int distance = this.distance;
        
        do{
            bridge = bridgeToGo(bridges);
            if(bridge != null){
                if(bridge.getInicialStrand() == this.strand){
                    this.strand = bridge.getFinalStrand();
                }else{
                    this.strand = bridge.getInicialStrand();
                }
                this.distance = bridge.getDistance();
            }
        }while(bridge != null);
        
        reachableSpots[0] = spotInStrand(this.strand, spots);
        this.strand = tempStrand;
        this.distance = distance;
        return reachableSpots;
    }

    /**
     * Return the color of the spot that is on the strand
     * @param strand    the last strand where the spider ends after walking
     * @param spots     the existing spots in the web
     * @return          the color of the spot that is on the strand
     */
    private String spotInStrand(int strand, HashMap<String, Spot> spots){
        for(Spot spot : spots.values()){
            if(spot.getStrand() == strand){
                return spot.getColor();
            }
        }
        return null;
    }

    /**
     * Return the bridges that the spider didn't use
     * @param bridges   the existing bridges in the web
     * @return  the bridges color that the spider didn't use
     */
    public String[] unusedBridges(HashMap<String, Bridge> bridges){
        HashMap<String, Bridge> unusedBridges = new HashMap<>();

        for(Map.Entry<String, Bridge> entry : bridges.entrySet()){
            String key = entry.getKey();
            Bridge bridge = entry.getValue();
            if(!this.usedBridges.containsKey(key)){
                unusedBridges.put(key, bridge);
            }
        }

        String[] output = new String[unusedBridges.size()];
        int i = 0;
        for(String color : unusedBridges.keySet()){
            output[i] = color;
            i += 1;
        }

        return output; 
    }

    /**
     * Make visible the spider
     */
    public void makeVisible(){
        canvas.draw(body, "black", body);
    }
    
    /**
     * Make invisible the spider
     */
    public void makeInvisible(){
        canvas.erase(body);
    }
}
