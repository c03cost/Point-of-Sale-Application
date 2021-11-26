
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.GroupLayout.Alignment;
import java.text.*;

/**
 * This is the application that runs the point of sale UI
 *
 * @author Cameron Costello
 * @version 11/24/2021
 */
public class SaleApplication {

    public static final int TEXT_FIELD_SIZE = 10;
    public static final int TEXT_FIELD_X_BOUNDARY = 100;
    public static final int TEXT_FIELD_BOUNDARY = 150;

    public static final int MAIN_PANEL_WIDTH = 400;
    public static final int MAIN_PANEL_HEIGHT = 100;

    public static final int CEHCKOUT_FRAME_X_AND_Y = 300;
    public static final int CEHCKOUT_FRAME_WIDTH = 350;
    public static final int CEHCKOUT_FRAME_HEIGHT = 300;

    public static ToolsInventory allTools = new ToolsInventory();
    private ArrayList<JRadioButton> toolButtons = new ArrayList<JRadioButton>();

    private JLabel rentalDays = new JLabel("Number of days for rent:");
    private JLabel discountPercent = new JLabel("Enter Discount Percent:");
    private JLabel checkoutDateLabel = new JLabel("Checkout Date (MM/dd/yy):");
    private JLabel titleLabel = new JLabel("Tool Rentals");

    private JTextField numberOfDaysRented = new JTextField(TEXT_FIELD_SIZE);
    private JTextField discount = new JTextField(TEXT_FIELD_SIZE);

    private JFrame mainFrame = new JFrame("Home Depot Rentals");
    private JPanel mainPanel = new JPanel();
    private JPanel panelOne = new JPanel();
    private JPanel panelTwo = new JPanel(new GridBagLayout());
    private JPanel panelThree = new JPanel();
    private JPanel panelFour = new JPanel();

    private BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
    private DateFormat format = new SimpleDateFormat("MM/dd/yy");
    private JFormattedTextField dateTextField = new JFormattedTextField(format);

    /**
     * Constructor for objects of class SaleApplication
     */
    public SaleApplication() {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        JButton checkout = new JButton("Checkout");

        // create layouts
        this.createGroupLayouts();
        this.createButtonGroup();

        rentalDays.setDisplayedMnemonic(KeyEvent.VK_U);
        rentalDays.setLabelFor(numberOfDaysRented);

        numberOfDaysRented.setBounds(TEXT_FIELD_X_BOUNDARY, TEXT_FIELD_BOUNDARY, TEXT_FIELD_BOUNDARY,
                TEXT_FIELD_BOUNDARY);
        dateTextField.setBounds(TEXT_FIELD_X_BOUNDARY, TEXT_FIELD_BOUNDARY, TEXT_FIELD_BOUNDARY, TEXT_FIELD_BOUNDARY);
        discount.setBounds(TEXT_FIELD_X_BOUNDARY, TEXT_FIELD_BOUNDARY, TEXT_FIELD_BOUNDARY, TEXT_FIELD_BOUNDARY);

        checkoutDateLabel.setLabelFor(dateTextField);
        checkoutDateLabel.setDisplayedMnemonic(KeyEvent.VK_U);

        discountPercent.setLabelFor(discount);
        discountPercent.setDisplayedMnemonic(KeyEvent.VK_U);
        discount.setBounds(5, 5, 5, 5);

        checkout.setPreferredSize(new Dimension(100, 25));
        checkout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent cl) {
                createRentalAgreement();
            }
        });

        // add UI components
        panelOne.add(titleLabel);
        panelThree.add(rentalDays);
        panelThree.add(numberOfDaysRented);
        panelThree.add(discountPercent);
        panelThree.add(discount);
        panelThree.add(checkoutDateLabel);
        panelThree.add(dateTextField);
        panelFour.add(checkout);

        mainPanel.add(panelOne);
        mainPanel.add(panelTwo);
        mainPanel.add(panelThree);
        mainPanel.add(panelFour);
        mainPanel.setLayout(layout);
        mainPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setPreferredSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));
        mainPanel.setMaximumSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));
        panelTwo.setBorder(BorderFactory.createTitledBorder("Items Available For Rent"));
        panelThree.setBorder(BorderFactory.createTitledBorder("Checkout"));

        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(dim.width / 2 - mainFrame.getSize().width / 2,
                dim.height / 2 - mainFrame.getSize().height / 2, 350, 500);

        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

    }

    /**
     * Creates a rental agreement when the user presses checkout
     */
    public void createRentalAgreement() {
        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panelOne = new JPanel(new FlowLayout());
        JPanel panelTwo = new JPanel(new FlowLayout());
        JPanel panelThree = new JPanel(new FlowLayout());

        JLabel rentalAgreementLabel = new JLabel("Rental Agreement Below");

        JButton agreeButton = new JButton("Agree");
        agreeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent cl) {
                frame.dispose();
                mainFrame.dispose();
            }
        });

        Tool toolRented = null;

        // check for selected tool and get the tool by the unique tool ID
        for (int i = 0; i < toolButtons.size(); i++) {
            if (toolButtons.get(i).isSelected()) {
                String itemID = toolButtons.get(i).getText()
                        .substring(toolButtons.get(i).getText().lastIndexOf(" "), toolButtons.get(i).getText().length())
                        .trim();
                toolRented = allTools.toolsAvailale.get(itemID);
            }
        }

        String toolCode = toolRented.getCode();
        String numDays = numberOfDaysRented.getText();
        String discountAmount = discount.getText();
        String toolType = toolRented.getType().getTool();
        String toolBrand = toolRented.getBrand();
        Date currentDate = new Date();

        if (!dateTextField.getText().equals("") || dateTextField.getText().length() > 8) {
            try {
                currentDate = this.format.parse(dateTextField.getText());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        // checkout calculations and rental agreement formation
        Checkout proceedToCheckout = new Checkout(toolCode, numDays, discountAmount, currentDate);
        proceedToCheckout.checkoutMessage();
        String dueDate = proceedToCheckout.calculateDueDate(currentDate, numDays);

        double dailyRentalCharge = toolRented.getType().getDailyCharge();
        int chargeDays = proceedToCheckout.calculateChargeableDays(currentDate, dueDate,
                toolRented.getType().getWeekdayCharge(), toolRented.getType().getWeekendCharge(),
                toolRented.getType().getHolidayCharge());

        double preDiscountCharge = proceedToCheckout.calculatePreDiscountCharge(toolRented.getType().getDailyCharge(),
                chargeDays);
        double discountApplied = proceedToCheckout.calculateDiscountAmount(discountAmount, preDiscountCharge);
        double finalCharge = Math.round((preDiscountCharge - discountApplied) * 100.00) / 100.00;

        String rentalAgreement = proceedToCheckout.generateRentalAgreement(toolCode, toolType, toolBrand, numDays,
                currentDate, dueDate, dailyRentalCharge, chargeDays, preDiscountCharge, discountAmount, discountApplied,
                finalCharge);

        JLabel rentalLabel = new JLabel(rentalAgreement);

        panelOne.add(rentalAgreementLabel);
        panelTwo.add(rentalLabel);
        panelThree.add(agreeButton);
        mainPanel.add(panelOne, BorderLayout.NORTH);
        mainPanel.add(panelTwo, BorderLayout.CENTER);
        mainPanel.add(panelThree, BorderLayout.SOUTH);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(CEHCKOUT_FRAME_X_AND_Y, CEHCKOUT_FRAME_X_AND_Y, CEHCKOUT_FRAME_WIDTH, CEHCKOUT_FRAME_HEIGHT);
    }

    /**
     * Set the layouts for the text fields up
     */
    public void createGroupLayouts() {

        GroupLayout groupLayout = new GroupLayout(panelThree);

        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        panelThree.setLayout(groupLayout);

        // group layout organization
        GroupLayout.SequentialGroup hGroup = groupLayout.createSequentialGroup();
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(rentalDays).addComponent(discountPercent)
                .addComponent(checkoutDateLabel));
        hGroup.addGroup(groupLayout.createParallelGroup().addComponent(numberOfDaysRented).addComponent(discount)
                .addComponent(dateTextField));
        groupLayout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = groupLayout.createSequentialGroup();
        vGroup.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(rentalDays)
                .addComponent(numberOfDaysRented));
        vGroup.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(discountPercent)
                .addComponent(discount));
        vGroup.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(checkoutDateLabel)
                .addComponent(dateTextField));

        groupLayout.setVerticalGroup(vGroup);
    }

    /**
     * Creates button group of tool radio buttons
     */
    public void createButtonGroup() {
        GridBagConstraints gbc = new GridBagConstraints();
        ButtonGroup buttonGroup = new ButtonGroup();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.WEST;
        Iterator hmIterator = allTools.toolsAvailale.entrySet().iterator();

        // create radio button group
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            Tool marks = (Tool) mapElement.getValue();
            JRadioButton button = new JRadioButton(marks.toString());
            if (mapElement.getKey().equals("JAKD")) {
                button.setSelected(true);
            }
            toolButtons.add(button);
            buttonGroup.add(button);
            panelTwo.add(button, gbc);

        }
    }

    /**
     * Runs the Sale Application
     *
     * @param args Arguments received
     */
    public static void main(String[] args) {
        new SaleApplication();
    }
}
