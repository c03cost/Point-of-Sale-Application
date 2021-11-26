/**
 * This class is responsible for handling individual tools
 *
 * @author Cameron Costello
 * @version 11/24/2021
 */
public class Tool
{

    private String code;
    private ToolType type;
    private String brand;

    /**
     * Constructor for objects of class Tools
     */
    public Tool(String code, ToolType type, String brand)
    {
        this.code = code;
        this.type = type;
        this.brand = brand;

    }

    /**
     * Returns a tool string
     * 
     * @return String tool format
     */
    @Override
    public String toString() {
        return " Type: " + this.type.tool + ", Brand: " + this.brand + ", ID: " + this.code;
    }

    /**
     * Returns the tool code
     * 
     * @return String tool code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Returns the tool type
     * 
     * @return String tool type
     */
    public ToolType getType() {
        return this.type;
    }

    /**
     * Returns the tool brand
     * 
     * @return String tool brand
     */
    public String getBrand() {
        return this.brand;
    }

    

}
