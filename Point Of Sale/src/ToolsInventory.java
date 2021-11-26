
import java.util.HashMap;

/**
 * This class creates a list of all tools available for rental
 *
 * @author Cameron Costello
 * @version 11/24/2021
 */
public class ToolsInventory {

    public HashMap<String, Tool> toolsAvailale;

    public ToolsInventory() {
        this.toolsAvailale = getTools();
    }

    /**
     * Gets all of the tools available for rental
     * 
     * @return HashMap<String, Tool> contains all the tools
     */
    private HashMap<String, Tool> getTools() {

        HashMap<String, Tool> tools = new HashMap<String, Tool>();

        tools.put("CHNS", new Tool("CHNS", new ToolType("Chainsaw", 1.49, true, false, true), "Stihl"));
        tools.put("LADW", new Tool("LADW", new ToolType("Ladder", 1.99, true, true, false), "Werner"));
        tools.put("JAKD", new Tool("JAKD", new ToolType("Jackhammer", 2.99, true, false, false), "DeWalt"));
        tools.put("JAKR", new Tool("JAKR", new ToolType("Jackhammer", 2.99, true, false, false), "Ridgid"));

        return tools;
    }

}
