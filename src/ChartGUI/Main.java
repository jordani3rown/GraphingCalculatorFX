package ChartGUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import java.util.ArrayList;


public class Main extends Application {
    /** Instance Variables */

    /** BorderPane - contains the following areas: (TOP, LEFT, CENTER, RIGHT, BOTTOM) */
    BorderPane layout;

    /** Accordion to hold the user added expressions */
    Accordion functionMenu;

    /** Pane that will contain the userExpressions and their corresponding ID's */
    TitledPane functionPane;

    /** GridPane to hold user expressionID's and expressionInput fields */
    GridPane userExpressions;

    /** Button used to add additional expressions */
    Button addButton;

    /** Pane used to hold the javafx implementation of the calculator */
    VBox containerCalc;

    /** Contains the buttons for the calculator */
    GridPane calcButtonPane;

    /** Text area that takes in user input in the form of mathematical expressions */
    TextArea expressionInput;

    /** Current Count of UserExpressions */
    int expressionCount;

    /** ArrayList of UserExpression objects that have been created by the user */
    ArrayList<UserExpression> addedExpressions;

    /** Array of buttons to be used for the calculator buttons in the JavaFX implementation */
    private Button[] calcButtons;

    /** ObservableList of Point objects that is used for the TableView */
    private ObservableList<Point> line = FXCollections.observableArrayList();

    /** Button objects */
    Button butSquare;
    Button butExp;
    Button butSqrt;
    Button butRt;
    Button butEX;
    Button butLog;
    Button butLN;
    Button butSin;
    Button butCos;
    Button butTan;
    Button butOpenParen;
    Button butCloseParen;
    Button butVar;
    Button butDel;
    Button butClear;
    Button butSeven;
    Button butEight;
    Button butNine;
    Button butMultiply;
    Button butDivide;
    Button butFour;
    Button butFive;
    Button butSix;
    Button butAdd;
    Button butSubtract;
    Button butOne;
    Button butTwo;
    Button butThree;
    Button butComma;
    Button butEquals;
    Button butZero;
    Button butDot;
    Button butPi;
    Button butUndo;
    Button butAns;

    /** TableView of the plotted lines */
    TableView dataTable;

    /** Parsing object */
    calc backEnd;

    /** String provided by the user */
    String userInput;

    /** Graph object */
    Graph graph;

    /** LineChart used to plot the mathematical expressions */
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

            // Setup the Calculator Buttons
            calcButtons = createCalcButtons();

            // Create a new calc object to parse the user input
            backEnd = new calc();

            // Initialize the BorderPane
            layout = new BorderPane();

            // Custom chart initialized in the start method
            Scene scene  = new Scene(layout,1280,960);

            // Add the stylesheet to the scene
            scene.getStylesheets().add("ChartGUI/CalculatorStylesheet.css");

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

            // Add the button to the GridPane
            userExpressions.add(addButton, 0, 1);

            // Create event handler for MouseEvent.MOUSE_CLICK
            addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // Create a new expression when the mouse is clicked
                    createExpression();
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
                    while(newToggle != null) {
                        if(topNav.getSelectedToggle() != newToggle) {
                            topNav.selectToggle(newToggle);
                        }
                        else {
                            return;
                        }
                    }
                }
            });


            // Button used to access the settings
            // (SETTINGS MENU, NOT CURRENTLY IMPLEMENTED)
            //Button settingsButton = new Button();


            // Set the style for the buttons that will be located in the top pane of the BorderPane 'layout'
            calcButton.getStyleClass().add("topButton");
            tableButton.getStyleClass().add("topButton");
            graphButton.getStyleClass().add("topButton");

            // Adding Background image to settings menu
            //Image menu = new Image(getClass().getResourceAsStream("images/menuButtonWhite.jpg"));
           // BackgroundImage menuBut = new BackgroundImage(menu, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(settingsButton.getWidth(), settingsButton.getHeight(), true, true, true, false));
            //Background settingsBG = new Background(menuBut);
            //settingsButton.setBackground(settingsBG);

            // Create GridPane to hold the buttons
            GridPane topPane = new GridPane();
            topPane.setId("topNav");

            // Creating ColumnConstraints for the GridPain in order to set the relative lengths for the buttons
            ColumnConstraints cc = new ColumnConstraints();
            //ColumnConstraints ccSettings = new ColumnConstraints();

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
            //topPane.add(settingsButton, 3, 0);

            // Set the lengths for the buttons to span the length of their ColumnConstraints
            calcButton.setMaxWidth(Double.MAX_VALUE);
            tableButton.setMaxWidth(Double.MAX_VALUE);
            graphButton.setMaxWidth(Double.MAX_VALUE);
            //settingsButton.setMaxWidth(Double.MAX_VALUE);

            // Add text to TextField to be informative to the user
            //userInput.setPromptText("Enter Expression Here");

            // Add the TitledPane 'functionMenuTitle' to the Accordion 'functionMenu'
            functionMenu.getPanes().add(functionPane);

            // MouseEvent on click to send the function menu to the top of the borderPane on shrink
            functionPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    // Condition that checks for cases where Accordion is clicked while located at the bottom of the BorderPane
                    if (functionPane.isExpanded() == true) {
                        // Condition that checks for the current location of the function menu
                        if(layout.getLeft() != functionPane && layout.getBottom() != null) {
                            // Set the top of the BorderPane to null, which removes the node from the bottom of the BorderPane 'layout'
                            layout.setBottom(null);
                            // Change the title
                            functionPane.setText("Collapse");
                            // Set the location of the function menu to the left of the BorderPane 'layout'
                            layout.setLeft(functionPane);
                            functionPane.setMaxHeight(layout.getLeft().getLayoutY());
                        }
                        // Return if the Accordion is expanded and is already set to the left side of the BorderPane 'layout'
                        else {
                            return;
                        }
                    }
                    // Condition to check set alignment to the top of the BorderPane when the Accordion shrinks
                    else {
                        // Place the node at the bottom of the BorderPane
                        layout.setLeft(null);
                        // Set the text of the button to Expand
                        functionPane.setText("Expand");
                        // Set the functionPane to be displayed on the Bottom pane of the BorderPane
                        layout.setBottom(functionPane);
                    }
                }
            });


            // Create the lineChart
            LineChart lineChart = createLineChart();
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
     Method that creates the LineChart
     *****************************************************************/
    public LineChart<Number, Number> createLineChart() {
        // Create a new Graph object
        graph = new Graph();

        //defining the axes
        //final NumberAxis xAxis = new NumberAxis(-10, 10, 1);
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

        // setting the chart title
        //lineChart.setTitle("");

        // removes the legend from the chart (series name would go in there)
        lineChart.setLegendVisible(false);

        return lineChart;
    }

    /*****************************************************************
     Method that plots the data points on the lineChart
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
        for(int x = (int) graph.getXMin(); x < xMax; x++ ) {
            // Parse the expression, and set the tmpInput to use the current x value instead of the variable x
            if (newInput.contains("x")) {
                // Check if the character preceding the variable x is a digit, if so, multiply the current iteration of x
                if(newInput.indexOf('x') > 0 && Character.isDigit(newInput.charAt(newInput.indexOf('x') - 1))) {
                    tmpInput = newInput.replaceAll("x", "*" + x);
                }
                // Set the value of x if the character preceding is not a digit
                else {
                    tmpInput = newInput.replaceAll("x", "" + x);
                }
            }

            // Add the expression to the parser and evaluate the y value from the current value of x
            expressionBackend.addToExpression(tmpInput);
            double y = expressionBackend.evaluate();

            // Output representing the x and y values for the current expression to the console
            System.out.println("x = " + x);
            System.out.println("y = " +y);

            // Add the data point to the series
            series.getData().add(new XYChart.Data<>(x, y));
            // Add the data point to the dataTable
            Point p = new Point(x, y);
            // Add the point object to the observable list 'line'
            line.add(p);

            // Print the tmpInput expression
            System.out.println("Expression: " +tmpInput);
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
     Creates a UserExpression and adds the corresponding fields to the pane
     *****************************************************************/
    public void createExpression() {
        // expressionCount Increment the number of expressions
        expressionCount ++;

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
                if(newInput != oldInput) {
                    // Remove the series of the current expression from the lineChart
                    lineChart.getData().removeAll(newExpression.expressionSeries);
                }
            }
        });
        // When the enter button is pressed in the expression text field then plot the expression on the graph
        userInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    plotData(newExpression);
                }
            }
        });
    }

    /*****************************************************************
     Creates and sets the content of the SwingNode for the javafx calculator
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
        containerCalc.setPadding(new Insets(5,5,5,5));

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
     Creates and labels the calculator buttons
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
        //Button butGraph = new Button("Graph");
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



        // Initialize the Button array with the initialized buttons
        calcButtons = new Button[]{butSquare, butExp, butSqrt, butRt, butEX, butLog, butLN,
                butCos, butSin, butTan, butOpenParen, butCloseParen, butComma, butVar, butPi,
                butSeven, butEight, butNine, butMultiply, butDivide, butFour, butFive, butSix,
                butAdd, butSubtract, butOne, butTwo, butThree, butDel, butClear, butZero, butDot, butEquals, butUndo, butAns};

        // Return the array of Buttons
        return calcButtons;
    }

    /*****************************************************************
     Display the calculation that took place in the text area field
     of the Calculator
     *****************************************************************/
    public String displayExpression() {
        // Obtain the user expression from the parser
        userInput = backEnd.getExpression();

        // Check conditions, replace input for proper calculation
        if (userInput.contains("pi")) {
            userInput = userInput.replaceAll("pi", "π");
        }
        if(userInput.contains("sqrt")){
            userInput = userInput.replaceAll("sqrt", "√");
        }
        if(userInput.contains("cbrt")){
            userInput = userInput.replaceAll("cbrt", "∛");
        }

        // Return the String object of the expression
        return userInput;
    }

    /*****************************************************************
     Function to add the calculator buttons to the Calculator Pane
     *****************************************************************/
    public void setupCalcButtons() {
        // Setup the Calculator Buttons by initializing a GridPane
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

        RowConstraints rowHeight = new RowConstraints();
        rowHeight.setPercentHeight(100);
        rowHeight.setVgrow(Priority.ALWAYS);

        // Add the column constraints to the gridpane
        calcButtonPane.getColumnConstraints().addAll(colWidth, colWidth, colWidth, colWidth, colWidth);
        calcButtonPane.getRowConstraints().addAll(rowHeight, rowHeight, rowHeight, rowHeight, rowHeight, rowHeight, rowHeight);
        //calcButtonPane.getRowConstraints().addAll(rowHeight, rowHeight, rowHeight);

        // Variables to track the current row / column
        int col;
        int row;

        // Set the button functionality and style properties
        for(int bElement = 0; bElement < calcButtons.length; bElement++) {
            row = bElement / 5;
            col = bElement % 5;


            //calcButtons[bElement].setId("calc-button");
            calcButtons[bElement].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            calcButtonPane.add(calcButtons[bElement], col, row);
            calcButtonPane.setVgrow(calcButtons[bElement], Priority.ALWAYS);
            Button currentButton = calcButtons[bElement];
            currentButton.setMaxHeight(Double.MAX_VALUE);
            //currentButton.setPrefHeight(calcButtonPane.getHeight() / 7.0);

            if(currentButton == butOne || currentButton == butTwo || currentButton == butThree || currentButton == butFour || currentButton == butFive || currentButton == butSix || currentButton == butSeven || currentButton == butEight || currentButton == butNine || currentButton == butZero || currentButton == butDot) {
                currentButton.setId("numButton");
            }
            else if(currentButton == butEquals) {
                currentButton.setId("equalsButton");
            }
            else {
                currentButton.setId("calc-button");
            }

            currentButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error in Expression");
                    Button source = currentButton;


                    if(source == butOne || source == butTwo || source == butThree || source == butFour || source == butFive || source == butSix || source == butSeven || source == butEight || source == butNine || source == butZero) {
                        backEnd.addToExpression("" + source.getText());
                        currentButton.setId("numButton");
                    }

                    if (source == butSquare) {
                        if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))){
                            alert.setHeaderText("Error in Squaring Operation");
                            alert.setContentText("Cannot square a non-number!");
                            alert.showAndWait();
                            return;
                        }
                        backEnd.addToExpression("^2");
                    }

                    if (source == butExp) {
                        if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                            alert.setHeaderText("Error Applying Exponent");
                            alert.setContentText("Cannot exponentiate a non-number!");
                            alert.showAndWait();
                            return;
                        }
                        backEnd.addToExpression("^");
                    }

                    if (source == butClear) {
                        backEnd.clear();
                    }

                    if (source == butSqrt) {
                        backEnd.addToExpression("sqrt(");
                    }

                    if (source == butRt) {
                        backEnd.addToExpression("cbrt(");
                    }

                    if (source == butLog) {
                        backEnd.addToExpression("log(10,");
                    }

                    if (source == butLN) {
                        backEnd.addToExpression("ln");
                    }

                    if (source == butSin) {
                        backEnd.addToExpression("sin(");
                    }

                    if (source == butCos) {
                        backEnd.addToExpression("cos(");
                    }

                    if (source == butTan) {
                        backEnd.addToExpression("tan(");
                    }

                    if (source == butDivide) {
                        if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                            alert.setHeaderText("Error in Division Operation");
                            alert.setContentText("Syntax: Number must precede division symbol");
                            alert.showAndWait();
                            return;
                        }
                        backEnd.addToExpression("/");
                    }

                    if (source == butAdd) {
                        if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                            alert.setHeaderText("Error in Addition Operation");
                            alert.setContentText("Syntax: Number must precede addition symbol");
                            alert.showAndWait();
                            return;
                        }
                        backEnd.addToExpression("+");
                    }

                    if (source == butSubtract) {
                        if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                            alert.setHeaderText("Error in Subtraction Operation");
                            alert.setContentText("Syntax: Number must precede minus symbol");
                            alert.showAndWait();
                            return;
                        }
                        backEnd.addToExpression("-");
                    }

                    if(source == butMultiply){
                        if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                            alert.setHeaderText("Error in Multiplication Operation");
                            alert.setContentText("Syntax: Number must precede multiplication symbol");
                            alert.showAndWait();
                            return;
                        }
                        backEnd.addToExpression("*");
                    }

                    if (source == butEquals) {
                        double result = backEnd.evaluate();
                        backEnd.clear();
                        backEnd.addToExpression(""+result);
                    }

                    if(source == butEX){
                        backEnd.addToExpression("e^");
                    }

                    if(source == butDot){
                        if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                            alert.setHeaderText("Error in Dot Operation");
                            alert.setContentText("Syntax: Number must precede a dot");
                            alert.showAndWait();
                            return;
                        }
                        backEnd.addToExpression(".");
                    }

                    if(source == butOpenParen){
                        backEnd.addToExpression("(");
                    }

                    if(source == butCloseParen){
                        backEnd.addToExpression(")");
                    }

                    if(source == butDel){
                        backEnd.del();
                    }

                    if(source == butPi){
                        backEnd.addToExpression("pi");
                    }

                    if(source == butComma){
                        backEnd.addToExpression(",");
                    }

                    if(source == butUndo){
                        backEnd.clear();
                        backEnd.addToExpression(backEnd.getPrevInput());
                    }

                    if(source == butAns){
                        backEnd.addToExpression(""+backEnd.getPrevResult());
                    }
                    // need to clear before appending the updated expression
                    expressionInput.clear();
                    expressionInput.appendText(displayExpression());
                }
            });
        }
    }

}