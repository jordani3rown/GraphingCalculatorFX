package ChartGUI;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/*****************************************************************
 Class for the GUI of the Graphing Calculator.
 @author    Jordan Brown, Jacob Rodriguez, Cade Snuffer
 @version   1.0
 *****************************************************************/
public class Main extends Application {
    /** Instance Variables */

    /** BorderPane - contains the following areas: (TOP, LEFT, CENTER, RIGHT, BOTTOM). */
    BorderPane layout;

    /** Accordion to hold the user added expressions. */
    Accordion functionMenu;

    /** Pane that will contain the userExpressions and their corresponding ID's. */
    TitledPane functionPane;

    /** GridPane to hold user expressionID's and expressionInput fields. */
    GridPane userExpressions;

    /** Button used to add additional expressions. */
    Button addButton;

    /** Button used to access graph window setting. */
    Button settingsButton;

    /** Pane used to hold the javafx implementation of the calculator. */
    VBox containerCalc;

    /** Contains the buttons for the calculator. */
    GridPane calcButtonPane;

    /** Text area that takes in user input in the form of mathematical expressions. */
    TextArea expressionInput;

    /** Current Count of UserExpressions. */
    int expressionCount;

    /** ArrayList of UserExpression objects that have been created by the user. */
    ArrayList<UserExpression> addedExpressions;

    /** Array of buttons to be used for the calculator buttons in the JavaFX implementation. */
    private Button[] calcButtons;

    /** ObservableList of Point objects that is used for the TableView. */
    private ObservableList<Point> line = FXCollections.observableArrayList();

    /** Button objects. */

    /** Squaring button. */
    Button butSquare;

    /** Exponentiation button. */
    Button butExp;

    /** Square root button. */
    Button butSqrt;

    /** Cube root button. */
    Button butRt;

    /** e^ button. */
    Button butEX;

    /** Log base 10 button. */
    Button butLog;

    /** Natural log button. */
    Button butLN;

    /** Sine button. */
    Button butSin;

    /** Cosine button. */
    Button butCos;

    /** Tangent button. */
    Button butTan;

    /** Open parenthesis button. */
    Button butOpenParen;

    /** Close parenthesis button. */
    Button butCloseParen;

    /** Stored expression button. */
    Button butVar;

    /** Delete button. */
    Button butDel;

    /** Clear expression button. */
    Button butClear;

    /** #7 button. */
    Button butSeven;

    /** #8 button. */
    Button butEight;

    /** #9 button. */
    Button butNine;

    /** Multiplication button. */
    Button butMultiply;

    /** Division button. */
    Button butDivide;

    /** #4 button. */
    Button butFour;

    /** #5 button. */
    Button butFive;

    /** #6 button. */
    Button butSix;

    /** Addition button. */
    Button butAdd;

    /** Subtraction button. */
    Button butSubtract;

    /** #1 button. */
    Button butOne;

    /** #2 button. */
    Button butTwo;

    /** #3 button. */
    Button butThree;

    /** Comma button. */
    Button butComma;

    /** Evaluation button. */
    Button butEquals;

    /** #0 button. */
    Button butZero;

    /** Decimal point button. */
    Button butDot;

    /** Pi button. */
    Button butPi;

    /** Undo button. */
    Button butUndo;

    /** Last calculated answer button. */
    Button butAns;

    /** Quadratic root calculator button. */
    Button butPoly;

    /** Factorial button. */
    Button butFact;

    /** Store expression button. */
    Button butStore;

    /** Calculator syntax button. */
    Button butSyn;

    /** TableView of the plotted lines. */
    TableView dataTable;

    /** Parsing object. */
    calc backEnd;

    /** String provided by the user. */
    String userInput;

    /** Graph object. */
    Graph graph;

    /** LineChart used to plot the mathematical expressions. */
    LineChart<Number, Number> lineChart;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Wrap method inside try-catch block
        try {
            // Initialize the ArrayList addedExpressions
            addedExpressions = new ArrayList<>();

            // Create the graph
            graph = new Graph();

            // Create the lineChart
            lineChart = createLineChart();

            // Setup the Calculator Buttons
            calcButtons = createCalcButtons();

            // Create a new calc object to parse the user input
            backEnd = new calc();

            // Initialize the BorderPane
            layout = new BorderPane();

            // Custom chart initialized in the start method
            Scene scene  = new Scene(layout, 1200, 960);

            // Add the stylesheet to the scene
            scene.getStylesheets().add("ChartGUI/bbgc.css");

            // Initialize the Accordion
            functionMenu = new Accordion();

            // Set the function menu to the left-hand side of the BorderPane
            layout.setLeft(functionMenu);

            // Initialize the GridPane
            userExpressions = new GridPane();
            userExpressions.setId("expression-panel");

            // Initialize the TitledPane with the title Functions
            functionPane = new TitledPane("Collapse", userExpressions);

            // Set top padding to 2px for the TitledPane top, right, bottom, left
            functionPane.setPadding(new Insets(0, 0, 0, 0));

            // Set the default status of the TitledPane to expanded
            functionMenu.setExpandedPane(functionPane);

            // Initialize expressionCount
            expressionCount = 0;

            userExpressions.setVgap(5);
            //userExpressions.setGridLinesVisible(true);

            // Initialize addButton to add additional expressions
            addButton = new Button("+");
            addButton.setId("addButton");

            settingsButton = new Button("Set");
            settingsButton.setId("settingsButton");

            // Add the button to the GridPane
            userExpressions.add(addButton, 0, 1);
            userExpressions.add(settingsButton, 2, 2);


            // Create event handler for MouseEvent.MOUSE_CLICK
            addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // Create a new expression when the mouse is clicked
                    createExpression();
                }
            });

            // Create event handler for settings button
            settingsButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // Create dialog box for window change
                    Dialog<Pair<String, String>> dialog = new Dialog<>();
                    dialog.setTitle("Change Axes for Graph");
                    dialog.setHeaderText("Please set the min and max values for x and y");

                    // Create the confirmation and cancel buttons for window change
                    ButtonType loginButtonType = new ButtonType("Set", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                    // Creating and setting up a grid
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));

                    // Creating labels and text fields for window size
                    TextField a = new TextField();
                    a.setPromptText("Y minimum");
                    TextField b = new TextField();
                    b.setPromptText("Y maximum");
                    TextField c = new TextField();
                    c.setPromptText("X minimum");
                    TextField d = new TextField();
                    d.setPromptText("X maximum");

                    // Adding text fields and labels to grid
                    grid.add(new Label("Y min:"), 0, 0);
                    grid.add(a, 1, 0);
                    grid.add(new Label("Y max:"), 0, 1);
                    grid.add(b, 1, 1);
                    grid.add(new Label("X min:"), 0, 2);
                    grid.add(c, 1, 2);
                    grid.add(new Label("X max:"), 0, 3);
                    grid.add(d, 1, 3);

                    // Attaching grid to dialog box
                    dialog.getDialogPane().setContent(grid);

                    // Setting graph min and max values when set button is clicked
                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == loginButtonType) {
                            if (a.getText().equals("") || b.getText().equals("") || c.getText().equals("")
                                    || d.getText().equals("")) {
                                Alert syn = new Alert(Alert.AlertType.ERROR);
                                syn.setHeaderText("You didn't fill everything in!");
                                syn.setContentText("Please fill in all fields to change the window");
                                syn.showAndWait();
                                return null;
                            }
                            graph.setYMin(parseInt(a.getText()));
                            graph.setYMax(parseInt(b.getText()));
                            graph.setXMin(parseInt(c.getText()));
                            graph.setXMax(parseInt(d.getText()));

                            // Re-create the line chart
                            lineChart = createLineChart();
                            // Show the new line chart
                            layout.setCenter(lineChart);
                            // Re-plot any user-entered expressions
                            for (int i = 0; i < addedExpressions.size(); i++) {
                                plotData(addedExpressions.get(i));
                            }
                        }
                        return null;
                    });
                    Optional<Pair<String, String>> result1 = dialog.showAndWait();
                }
            });
            // Create the first UserExpression with an empty TextField
            createExpression();

            // Setup the calculator buttons and input text area
            setupCalculator();

            // BorderPane TOP section for calculator and graphing tabs
            // Button used to switch to the Calculator node
            ToggleButton calcButton = new ToggleButton("Calculator");
            // Button used to switch to LineChart
            ToggleButton graphButton = new ToggleButton("Graph");
            // Button used to switch to TableView
            ToggleButton tableButton = new ToggleButton("Table");

            // Group of the ToggleButtons that are displayed in the topPane of the BorderPane 'layout'
            final ToggleGroup topNav = new ToggleGroup();
            // Add each ToggleButton to the ToggleGroup
            calcButton.setToggleGroup(topNav);
            graphButton.setToggleGroup(topNav);
            tableButton.setToggleGroup(topNav);

            // Set the selected Toggle to true, and inherit style properties on selection
            topNav.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldToggle, Toggle newToggle) {
                    while (newToggle != null) {
                        if (topNav.getSelectedToggle() != newToggle) {
                            topNav.selectToggle(newToggle);
                        } else {
                            return;
                        }
                    }
                }
            });

            // Set the style for the buttons that will be located in the top pane of the BorderPane 'layout'
            calcButton.getStyleClass().add("topButton");
            tableButton.getStyleClass().add("topButton");
            graphButton.getStyleClass().add("topButton");

            // Create GridPane to hold the buttons
            GridPane topPane = new GridPane();
            topPane.setId("topNav");

            // Creating ColumnConstraints for the GridPain in order to set the relative lengths for the buttons
            ColumnConstraints cc = new ColumnConstraints();

            // Set the top bane of the layout BorderPane to the GridPane 'topPane' in which will consist of 3 buttons
            layout.setTop(topPane);
            // Define the Max size and bind the topPane to its container 'layout'
            topPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            topPane.prefWidthProperty().bind(layout.widthProperty());

            // Set the column constraints for the first 3 columns to take up 90% of the total width
            cc.setPercentWidth(100);
            // Set the column constraints for the settings button to take up the last 10% of the total width
            //ccSettings.setPercentWidth(9.5);
            // Add the ColumnConstraints to the GridPane 'topPane'
            topPane.getColumnConstraints().addAll(cc, cc, cc);

            // Add all of the buttons to the topPane
            topPane.add(calcButton, 0, 0);
            topPane.add(tableButton, 1, 0);
            topPane.add(graphButton, 2, 0);

            // Set the lengths for the buttons to span the length of their ColumnConstraints
            calcButton.setMaxWidth(Double.MAX_VALUE);
            tableButton.setMaxWidth(Double.MAX_VALUE);
            graphButton.setMaxWidth(Double.MAX_VALUE);

            // Add the TitledPane 'functionMenuTitle' to the Accordion 'functionMenu'
            functionMenu.getPanes().add(functionPane);

            // MouseEvent on click to send the function menu to the top of the borderPane on shrink
            functionPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    // Condition that checks for cases where Accordion is clicked while located at the bottom of the BorderPane
                    if (functionPane.isExpanded() == true) {
                        // Condition that checks for the current location of the function menu
                        if (layout.getLeft() != functionPane && layout.getBottom() != null) {
                            // Set the top of the BorderPane to null, which removes the node from the bottom of the BorderPane 'layout'
                            layout.setBottom(null);
                            // Change the title
                            functionPane.setText("Collapse");
                            // Set the location of the function menu to the left of the BorderPane 'layout'
                            layout.setLeft(functionPane);
                            functionPane.setMaxHeight(layout.getLeft().getLayoutY());
                        } else { // Return if the Accordion is expanded and is already set to the left side of the BorderPane 'layout'
                            return;
                        }
                    } else { // Condition to check set alignment to the top of the BorderPane when the Accordion shrinks
                        // Place the node at the bottom of the BorderPane
                        layout.setLeft(null);
                        // Set the text of the button to Expand
                        functionPane.setText("Expand");
                        // Set the functionPane to be displayed on the Bottom pane of the BorderPane
                        layout.setBottom(functionPane);
                    }
                }
            });

            // Set the default center pane of the BorderPane to the LineChart view
            layout.setCenter(lineChart);
            // Select the graphButton in the topPane of the BorderPane 'layout'
            graphButton.setSelected(true);

            // Create a table of the data
            dataTable = new TableView();

            // Create event handler for the calculator button in the top pane
            calcButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // set the center pane to null
                    layout.setCenter(null);
                    // set the center pane to the VBox containing the calculator
                    layout.setCenter(containerCalc);
                }
            });

            // MouseEvent to change switch center pane of BorderPane 'layout' to TableView
            tableButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // set the center pane to null
                    layout.setCenter(null);
                    // set the center pane to the dataTable TableView object
                    layout.setCenter(dataTable);
                }
            });

            // MouseEvent to change the center pane of the BorderPane 'layout' to the LineChart object
            graphButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // set the center pane to null
                    layout.setCenter(null);
                    // set the center pane to the LineChart object
                    layout.setCenter(lineChart);
                }
            });

            // Set the scene of the stage
            primaryStage.setScene(scene);

            // stage title
            primaryStage.setTitle("Graphing Calculator");

            // Display the stage
            primaryStage.show();

        } catch (Exception e) {

        }
    }

    /*****************************************************************
     Method that creates the LineChart.
     *****************************************************************/
    public LineChart<Number, Number> createLineChart() {

        //defining the axes
        double xMin = graph.getXMin();
        double xMax = graph.getXMax();
        double yMin = graph.getYMin();
        double yMax = graph.getYMax();
        double tickInterval = graph.getTickDistance();

        NumberAxis xAxis = new NumberAxis(xMin, xMax, tickInterval);
        NumberAxis yAxis = new NumberAxis(yMin, yMax, tickInterval);

        //xAxis.setLabel("Number of Month");

        //creating the chart
        //final LineChart<Number, Number> lineChart
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        // removes the legend from the chart (series name would go in there)
        lineChart.setLegendVisible(false);

        return lineChart;
    }

    /*****************************************************************
     Method that plots the data points on the lineChart.
     @param newExpression a "new" expression from "new" input
     *****************************************************************/
    public void plotData(UserExpression newExpression) {
        // Initialize a series of data points
        XYChart.Series series = new XYChart.Series();
        // Obtain the String object of the expression
        String newInput = newExpression.expression;
        // Initialize a new calc object
        calc expressionBackend = new calc();

        // Create columns in the TableView for the X and Y values of the current expression
        TableColumn<Point, Double> colX = new TableColumn<>("X" + newExpression.expressionID);
        TableColumn<Point, Double> colY = new TableColumn<>("Y" + newExpression.expressionID);

        dataTable.getColumns().addAll(colX, colY);

        // Set the maximum x value of x for the LineChart
        int xMax = (int) graph.getXMax();
        // Temporary String object to use for calculating individual data points
        String tmpInput = newInput;


        // Iterate through the visible x and y values and set the corresponding data points
        for (int x = (int) graph.getXMin(); x < xMax; x++) {
            // Parse the expression, and set the tmpInput to use the current x value instead of the variable x
            if (newInput.contains("x")) {
                // Check if the character preceding the variable x is a digit, if so, multiply the current iteration of x
                if (newInput.indexOf('x') > 0 && Character.isDigit(newInput.charAt(newInput.indexOf('x') - 1))) {
                    tmpInput = newInput.replaceAll("x", "*" + x);
                } else { // Set the value of x if the character preceding is not a digit
                    tmpInput = newInput.replaceAll("x", "" + x);
                }
            }

            // Add the expression to the parser and evaluate the y value from the current value of x
            expressionBackend.addToExpression(tmpInput);
            double y = expressionBackend.evaluate();

            // Add the data point to the series
            series.getData().add(new XYChart.Data<>(x, y));
            // Add the data point to the dataTable
            Point p = new Point(x, y);
            // Add the point object to the observable list 'line'
            line.add(p);
            // Clear the expression for the next iteration of x
            expressionBackend.clear();
        }
        // Add the series to the UserExpression object 'newExpression'
        newExpression.setExpressionSeries(series);
        // Add the data to the lineChart
        lineChart.getData().add(series);

        // Tell the dataTable where to retrieve the data for the x and y columns
        colX.setCellValueFactory(new PropertyValueFactory<>("x"));
        colY.setCellValueFactory(new PropertyValueFactory<>("y"));
        // Add the data to the TableView 'dataTable'
        dataTable.setItems(line);
    }

    /*****************************************************************
     Creates a UserExpression and adds the corresponding fields to the pane.
     *****************************************************************/
    public void createExpression() {
        // expressionCount Increment the number of expressions
        expressionCount++;

        // Create a default userExpression object starting with an ID equivalent to 1 and an expression String value of null
        UserExpression userExpression = new UserExpression(expressionCount, null);

        // Label the expression box
        Label expressionID = new Label("" + userExpression.getExpressionID() + ". ");
        expressionID.setId("expressionID");

        // Create the text field that will take in the user input
        TextField userInput = new TextField();

        // Set the prompt text for the user
        userInput.setPromptText("Enter Expression Here");

        // Add the components to the GridPane and align them
        userExpressions.add(expressionID, 0, expressionCount - 1);
        userExpressions.add(userInput, 1, expressionCount - 1);

        // Shift the location of the addButton below the most recently created UserExpression
        userExpressions.setRowIndex(addButton, expressionCount + 1);
        userExpressions.setRowIndex(settingsButton, expressionCount + 1);

        // Create a new UserExpression on the MouseEvent
        UserExpression newExpression = new UserExpression(expressionCount, null);
        //UserExpression newExpression = new UserExpression(expressionCount, userInput.getText());

        // Add the expression to the ArrayList 'addedExpressions'
        addedExpressions.add(newExpression);

        // Remove the line from the plot if the expression is modified
        userInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldInput, String newInput) {
                // variable representing the updated string in the text field
                newExpression.setExpression(newInput);
                if (newInput != oldInput) {
                    // Remove the series of the current expression from the lineChart
                    lineChart.getData().removeAll(newExpression.expressionSeries);
                }
            }
        });
        // When the enter button is pressed in the expression text field then plot the expression on the graph
        userInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    plotData(newExpression);
                }
            }
        });
    }

    /*****************************************************************
     Creates and sets the content of the SwingNode for the javafx calculator.
     *****************************************************************/
    public void setupCalculator() {
        // Initialize the container of the calculator with type VBox
        containerCalc = new VBox();
        // Set the maximum size of the VBox
        containerCalc.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        // Bind the width and height of the Calculator container
        containerCalc.prefWidthProperty().bind(layout.widthProperty().multiply(1.0));
        containerCalc.prefHeightProperty().bind(layout.heightProperty().multiply(1.0));
        // Set Fill, Spacing, and Padding defaults for the calculator
        containerCalc.setFillWidth(true);
        containerCalc.setSpacing(5.0);
        containerCalc.setPadding(new Insets(5, 5, 5, 5));

        // Create expression input text area
        expressionInput = new TextArea();
        expressionInput.setMaxHeight(Double.MAX_VALUE);

        containerCalc.setId("calculator-container");

        // Setup calculator buttons
        this.calcButtons = createCalcButtons();
        setupCalcButtons();

        // Add the components to the container
        containerCalc.getChildren().add(expressionInput);
        containerCalc.getChildren().add(calcButtonPane);
        // Set the components to vertically expand with a given priority
        containerCalc.setVgrow(expressionInput, Priority.SOMETIMES);
        containerCalc.setVgrow(calcButtonPane, Priority.ALWAYS);

        // Set the height of the TextArea to 20% of the height of the VBox
        expressionInput.setPrefHeight(containerCalc.getPrefHeight() * 0.20);
    }

    /*****************************************************************
     Creates and labels the calculator buttons.
     *****************************************************************/
    public Button[] createCalcButtons() {
        // Initialize each of the calculator buttons
        butSquare = new Button("^2");
        butExp = new Button("^");
        butSqrt = new Button("√");
        butRt = new Button("∛");
        butEX = new Button("e^x");
        butLog = new Button("Log");
        butLN = new Button("LN");
        butSin = new Button("Sin");
        butCos = new Button("Cos");
        butTan = new Button("Tan");
        butOpenParen = new Button("(");
        butCloseParen = new Button(")");
        butVar = new Button("X");
        butDel = new Button("Del");
        butClear = new Button("Clear");
        butSeven = new Button("7");
        butEight = new Button("8");
        butNine = new Button("9");
        butMultiply = new Button("*");
        butDivide = new Button("/");
        butFour = new Button("4");
        butFive = new Button("5");
        butSix = new Button("6");
        butAdd = new Button("+");
        butSubtract = new Button("-");
        butOne = new Button("1");
        butTwo = new Button("2");
        butThree = new Button("3");
        butComma = new Button(",");
        butEquals = new Button("=");
        butZero = new Button("0");
        butDot = new Button(".");
        butPi = new Button("π");
        butUndo = new Button("Undo");
        butAns = new Button("Prev. Answer");
        butPoly = new Button("Quad");
        butFact = new Button("!");
        butStore = new Button("Store");
        butSyn = new Button("Syntax");



        // Initialize the Button array with the initialized buttons
        calcButtons = new Button[]{butSquare, butExp, butSqrt, butRt, butEX, butLog, butLN,
                butCos, butSin, butTan, butOpenParen, butCloseParen, butComma, butVar, butPi,
                butSeven, butEight, butNine, butMultiply, butDivide, butFour, butFive, butSix,
                butAdd, butSubtract, butOne, butTwo, butThree, butDel, butClear, butZero, butDot,
                butEquals, butUndo, butAns, butPoly, butFact, butStore, butSyn};

        // Return the array of Buttons
        return calcButtons;
    }

    /*****************************************************************
     Display the calculation that took place in the text area field
     of the Calculator.
     @return
     *****************************************************************/
    public String displayExpression() {
        // Obtain the user expression from the parser
        userInput = backEnd.getExpression();

        // Check conditions, replace input for better display
        if (userInput.contains("pi")) {
            userInput = userInput.replaceAll("pi", "π");
        }
        if (userInput.contains("sqrt")) {
            userInput = userInput.replaceAll("sqrt", "√");
        }
        if (userInput.contains("root(3,")) {
            userInput = userInput.replaceAll("root\\(3,", "∛(");
        }
        if (userInput.contains("log(10,")) {
            userInput = userInput.replaceAll("log\\(10,", "log(");
        }

        // Return the String object of the expression
        return userInput;
    }

    /*****************************************************************
     Function to add the calculator buttons to the Calculator Pane.
     *****************************************************************/
    public void setupCalcButtons() {
        // Setup the gridpane
        calcButtonPane = new GridPane();
        calcButtonPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        calcButtonPane.setHgap(2.0);
        calcButtonPane.setVgap(2.0);
        calcButtonPane.setGridLinesVisible(true);

        // Set the width of the columns
        ColumnConstraints colWidth = new ColumnConstraints();
        colWidth.setHgrow(Priority.ALWAYS);
        colWidth.setPercentWidth(20);
        calcButtonPane.setMaxHeight(Double.MAX_VALUE);

        // Add the column constraints to the gridpane
        calcButtonPane.getColumnConstraints().addAll(colWidth, colWidth, colWidth, colWidth, colWidth);

        // Variables to track the current row / column
        int col;
        int row;

        // Loop to place buttons in grid view
        for (int bElement = 0; bElement < calcButtons.length; bElement++) {
            row = bElement / 5;
            col = bElement % 5;

            calcButtons[bElement].setId("calc-button");
            calcButtons[bElement].setStyle("-fx-font-size:25");
            calcButtons[bElement].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            calcButtonPane.add(calcButtons[bElement], col, row);
            calcButtonPane.setVgrow(calcButtons[bElement], Priority.ALWAYS);
            Button currentButton = calcButtons[bElement];

            // Creating action listeners for each calculator button
            currentButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // Alert dialog for showing error messages
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error in Expression");

                    // Button for selected button
                    Button source = currentButton;

                    // Handles buttons #0 - 9
                    if (source == butOne || source == butTwo || source == butThree || source == butFour || source == butFive || source == butSix || source == butSeven || source == butEight || source == butNine || source == butZero) {
                        // Add selected # to expression
                        backEnd.addToExpression("" + source.getText());
                    }
                    // Handles squaring operator button
                    if (source == butSquare) {
                        try {
                            backEnd.addToExpression("^2");
                        } catch (IllegalArgumentException b) {
                            alert.setHeaderText("Error in Squaring Operation");
                            alert.setContentText("Cannot square a non-number!");
                            alert.showAndWait();
                            return;
                        }
                    }
                    // Handles exponentiation operator button
                    if (source == butExp) {
                        try {
                            backEnd.addToExpression("^");
                        } catch (IllegalArgumentException b) {
                            alert.setHeaderText("Error Applying Exponent");
                            alert.setContentText("Cannot exponentiate a non-number!");
                            alert.showAndWait();
                            return;
                        }
                    }
                    // Handles the stored variable button
                    if (source == butVar) {
                        backEnd.addToExpression("X");
                    }
                    // Handles the clear expression button
                    if (source == butClear) {
                        backEnd.clear();
                    }
                    // Handles the square root operator button
                    if (source == butSqrt) {
                        backEnd.addToExpression("sqrt(");
                    }
                    // Handles the cube root operator button
                    if (source == butRt) {
                        backEnd.addToExpression("root(3,");
                    }
                    // Handles the log base 10 operator button
                    if (source == butLog) {
                        backEnd.addToExpression("log(10,");
                    }
                    // Handles the natural log operator button
                    if (source == butLN) {
                        backEnd.addToExpression("ln(");
                    }
                    // Handles the sin operator button
                    if (source == butSin) {
                        backEnd.addToExpression("sin(");
                    }
                    // Handles the cos operator button
                    if (source == butCos) {
                        backEnd.addToExpression("cos(");
                    }
                    // Handles the tan operator button
                    if (source == butTan) {
                        backEnd.addToExpression("tan(");
                    }
                    // Handles the divide operator button
                    if (source == butDivide) {
                        try {
                            backEnd.addToExpression("/");
                        } catch (IllegalArgumentException b) {
                            alert.setHeaderText("Error in Divide Operation");
                            alert.setContentText("Syntax: Number must precede division symbol");
                            alert.showAndWait();
                            return;
                        }
                    }
                    // Handles the add operator button
                    if (source == butAdd) {
                        try {
                            backEnd.addToExpression("+");
                        } catch (IllegalArgumentException b) {
                            alert.setHeaderText("Error in Addition Operation");
                            alert.setContentText("Syntax: Number must precede addition symbol");
                            alert.showAndWait();
                            return;
                        }
                    }
                    // Handles the subtract operator button
                    if (source == butSubtract) {
                        try {
                            backEnd.addToExpression("-");
                        } catch (IllegalArgumentException b) {
                            alert.setHeaderText("Error in Subtraction Operation");
                            alert.setContentText("Syntax: Number must precede minus symbol");
                            alert.showAndWait();
                            return;
                        }
                    }
                    // Handles the multiply operator button
                    if (source == butMultiply) {
                        try {
                            backEnd.addToExpression("*");
                        } catch (IllegalArgumentException b) {
                            alert.setHeaderText("Error in Multiplication Operation");
                            alert.setContentText("Syntax: Number must precede multiplication symbol");
                            alert.showAndWait();
                            return;
                        }
                    }
                    // Handles the evaluate expression button
                    if (source == butEquals) {
                        double result = backEnd.evaluate();
                        backEnd.clear();
                        try {
                            backEnd.addToExpression("" + result);
                        } catch (IllegalArgumentException b) {
                            alert.setHeaderText("Error in syntax");
                            alert.setContentText("Unable to evaluate expression");
                            alert.showAndWait();
                            return;
                        }
                    }
                    // Handles the e^ special character button
                    if (source == butEX) {
                        backEnd.addToExpression("e^");
                    }
                    // Handles the decimal point button
                    if (source == butDot) {
                        try {
                            backEnd.addToExpression(".");
                        } catch (IllegalArgumentException b) {
                            alert.setHeaderText("Error in Dot Operation");
                            alert.setContentText("Syntax: Number must precede a dot");
                            alert.showAndWait();
                            return;
                        }
                    }
                    // Handles the open parenthesis button
                    if (source == butOpenParen) {
                        backEnd.addToExpression("(");
                    }
                    // Handles the close parenthesis button
                    if (source == butCloseParen) {
                        backEnd.addToExpression(")");
                    }
                    // Handles the delete button
                    if (source == butDel) {
                        backEnd.del();
                    }
                    // Handles the pi special character button
                    if (source == butPi) {
                        backEnd.addToExpression("pi");
                    }
                    // Handles the comma button
                    if (source == butComma) {
                        backEnd.addToExpression(",");
                    }
                    // Handles the undo button
                    if (source == butUndo) {
                        backEnd.clear();
                        backEnd.addToExpression(backEnd.getPrevInput());
                    }
                    // Handles the previous answer button
                    if (source == butAns) {
                        backEnd.addToExpression("" + backEnd.getCurrentAns());
                    }
                    // Handles the quadratic roots button
                    if (source == butPoly) {
                        polyRoot();
                    }
                    // Handles the store expression button
                    if (source == butStore) {
                        backEnd.storeInVar();
                    }
                    // Handles the syntax button
                    if (source == butSyn) {
                        Alert flert = new Alert(Alert.AlertType.INFORMATION);
                        flert.setTitle("Information Dialog");
                        flert.setHeaderText("Calculator Syntax");
                        TextArea area = new TextArea("SCIENTIFIC CALCULATOR SYNTAX\n" +
                                "\n" +
                                "Natural Log (LN)\n" +
                                "CORRECT SYNTAX:\n" +
                                "LN(x)\n" +
                                "INCORRECT SYNTAX:\n" +
                                "LN x\n" +
                                "\n" +
                                "Square (^2)\n" +
                                "CORRECT SYNTAX:\n" +
                                "x^2, (x+x)^2, x^(2)\n" +
                                "\n" +
                                "Exponentiation (^)\n" +
                                "CORRECT SYNTAX:\n" +
                                "5^x, (5+2)^x, 5^(x)\n" +
                                "\n" +
                                "Square Root (√)\n" +
                                "CORRECT SYNTAX:\n" +
                                "√(x)\n" +
                                "INCORRECT SYNTAX:\n" +
                                "√x\n" +
                                "\n" +
                                "Cube root (∛)\n" +
                                "CORRECT SYNTAX:\n" +
                                "∛(x)\n" +
                                "INCORRECT SYNTAX:\n" +
                                "∛x\n" +
                                "\n" +
                                "Special character e (e^)\n" +
                                "CORRECT SYNTAX:\n" +
                                "e^x, e^(x), e^(x+x)\n" +
                                "\n" +
                                "Log base 10 (log())\n" +
                                "CORRECT SYNTAX:\n" +
                                "log(x)\n" +
                                "INCORRECT SYNTAX:\n" +
                                "log x\n" +
                                "\n" +
                                "Trig functions (cos())\n" +
                                "CORRECT SYNTAX:\n" +
                                "cos(x), sin(x), tan(x)\n" +
                                "INCORRECT SYNTAX:\n" +
                                "cos x, sin x, tan x\n" +
                                "\n" +
                                "Operators (+, -, *, /)\n" +
                                "CORRECT SYNTAX:\n" +
                                "x+x, (x)+(x), x*(x+x)\n" +
                                "INCORRECT SYNTAX:\n" +
                                "x(x+x)");
                        area.setWrapText(true);
                        area.setEditable(false);

                        flert.getDialogPane().setExpandableContent(area);
                        //flert.setContentText();
                        flert.showAndWait();
                        return;
                    }
                    // Handles the factorial operator button
                    if (source == butFact) {
                        try {
                            backEnd.addToExpression("!");
                        } catch (IllegalArgumentException b) {
                            alert.setHeaderText("Error in Factorial Operation");
                            alert.setContentText("Syntax: Number must precede factorial operator");
                            alert.showAndWait();
                            return;
                        }
                    }
                    // need to clear before appending the updated expression
                    expressionInput.clear();
                    expressionInput.appendText(displayExpression());
                }
            });
        }
    }

    /*****************************************************************
     Function to display and handle the quadratic root calculator.
     *****************************************************************/
    public void polyRoot() {
        // Create new custom dialog with instructions
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Quadratic Root Calculator");
        dialog.setHeaderText("Please fill in the coefficients for the following equation:\n Ax^2 + Bx + C = 0");

        //Create an execute and cancel button for dialog
        ButtonType loginButtonType = new ButtonType("Find Roots", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create and adjust grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Create labels and text fields for each coefficient
        TextField a = new TextField();
        a.setPromptText("A");
        TextField b = new TextField();
        b.setPromptText("B");
        TextField c = new TextField();
        c.setPromptText("C");

        // Add labels and text fields to grid
        grid.add(new Label("A:"), 0, 0);
        grid.add(a, 1, 0);
        grid.add(new Label("B:"), 0, 1);
        grid.add(b, 1, 1);
        grid.add(new Label("C:"), 0, 2);
        grid.add(c, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Set listener for the Find Roots button
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                // Create and show answer dialog
                Alert dlert = new Alert(Alert.AlertType.CONFIRMATION);
                dlert.setHeaderText("Roots of quadratic");
                if (a.getText().equals("") || b.getText().equals("") || c.getText().equals("")) {
                    Alert syn = new Alert(Alert.AlertType.ERROR);
                    syn.setHeaderText("You didn't fill everything in!");
                    syn.setContentText("Please fill in all fields to calculate roots");
                    syn.showAndWait();
                    return null;
                }
                dlert.setContentText(backEnd.polySolve(parseDouble(a.getText()), parseDouble(b.getText()), parseDouble(c.getText())));
                dlert.showAndWait();
            }
            return null;
        });

        Optional<Pair<String, String>> result1 = dialog.showAndWait();
    }
}
