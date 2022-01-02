
import java.util.HashMap;

/**
 * This class creates a list of all tools available for rental
 *
 * @author Cameron Costello
 * @version 11/24/2021
 */
public class ToolsInventory {

    public HashMap<String, Tool> toolsAvailale;
    public HashMap<String, Tool> tools = new HashMap<String, Tool>();

    public ToolsInventory() {
        this.toolsAvailale = getTools();
    }

    /**
     * Gets all of the tools available for rental
     * 
     * @return HashMap<String, Tool> contains all the tools
     */
    private HashMap<String, Tool> getTools() {

        this.tools.put("CHNS", new Tool("CHNS", new ToolType("Chainsaw", 1.49, true, false, true), "Stihl"));
        this.tools.put("LADW", new Tool("LADW", new ToolType("Ladder", 1.99, true, true, false), "Werner"));
        this.tools.put("JAKD", new Tool("JAKD", new ToolType("Jackhammer", 2.99, true, false, false), "DeWalt"));
        this.tools.put("JAKR", new Tool("JAKR", new ToolType("Jackhammer", 2.99, true, false, false), "Ridgid"));

        return tools;
    }

    /**
     * Add a new tool with a unique identifier
     * 
     * @param code the unique tool identifier
     * @param newTool the tool that is being added to the list of rentals
     */
    public void addTool(String code, Tool newTool) {
        this.tools.put(code, newTool);
    }

    /**
     * Remove a tool from the list of rentals
     * 
     * @param code code of the tool being removed
     */
    public void removeTool(String code) {
        this.tools.remove(code);
    }

}
