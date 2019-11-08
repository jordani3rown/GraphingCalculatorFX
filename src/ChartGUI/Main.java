package ChartGUI;

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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;


public class Main extends Application {
    /** Instance Variables */

    /** BorderPane - contains the following areas: (TOP, LEFT, CENTER, RIGHT, BOTTOM) */
    BorderPane layout;

    /** Accordion to hold the user added expressions */
    Accordion functionMenu;

    TitledPane functionPane;

    /** GridPane to hold user expressionID's and expressionInput fields */
    GridPane userExpressions;

    /** Button used to add additional expressions */
    Button addButton;

    /** Pane used to hold the javafx implementation of the calculator */
    VBox containerCalc;

    GridPane calcButtonPane;

    TextArea expressionInput;

    RowConstraints textAreaHeight;

    /** Current Count of UserExpressions */
    int expressionCount;

    /** UserExpression object */
    UserExpression[] addedExpressions;

    /** Calculator object that held the swing calculator */
    Calculator calculator;

    /** Array of buttons to be used for the calculator buttons in the JavaFX implementation */
    private Button calcButtons[];


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Wrap method inside try-catch block
        try {
            // Initialize the BorderPane
            layout = new BorderPane();

            // Custom chart initialized in the start method
            Scene scene  = new Scene(layout,800,600);

            // Add the stylesheet to the scene
            scene.getStylesheets().add("ChartGUI/bbgc.css");

            // Initialize the Accordion
            functionMenu = new Accordion();

            // Set the function menu to the left-hand side of the BorderPane
            layout.setLeft(functionMenu);

            // Initialize the GridPane
            userExpressions = new GridPane();

            // Initialize the TitledPane with the title Functions
            functionPane = new TitledPane("Collapse", userExpressions);
            //functionPane.setEffect(new DropShadow(10, Color.GRAY));

            // Set top padding to 2px for the TitledPane
            functionPane.setPadding(new Insets(2, 0, 0, 0));

            // Set the default status of the TitledPane to expanded
            functionMenu.setExpandedPane(functionPane);
            // Apply the stylesheet to the titledPane
            //functionPane.applyCss();

            // Initialize expressionCount
            expressionCount = 0;

            userExpressions.setVgap(5);
            //userExpressions.setGridLinesVisible(true);

            // Initialize addButton to add additional expressions
            addButton = new Button("+");

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

            // Previously used containers for the swing version of the calculator
            //HBox calcBox = new HBox();
            // Create a new Calculator object
            //calculator = new Calculator();
            //calcBox.getChildren().add(calculator);


            /*
            Section to be used for the JavaFX implementation of the calculator
             */
            /*
            // GridPane to hold the calculator buttons
            calcPane = new GridPane();
            //setupCalcButtons();
            ColumnConstraints ccCalcRow = new ColumnConstraints();
            ccCalcRow.setPercentWidth(100);
            RowConstraints ccCalcCol = new RowConstraints();
            ccCalcCol.setPercentHeight(10); // 10% of the height of the pane for each column of the calculator buttons

            //addButtonsToGrid();
            calcPane.getRowConstraints().addAll(textAreaHeight, ccCalcCol, ccCalcCol, ccCalcCol, ccCalcCol, ccCalcCol, ccCalcCol, ccCalcCol, ccCalcCol);;
            //calcPane.getColumnConstraints().addAll(ccCalcRow);
            */

            setupCalculator();

            // BorderPane top section for calculator and graphing tabs
            // Button used to switch to the Calculator node
            Button calcButton = new Button("Calculator");
            // Button used to switch between charting and TableView
            // (TableView currently not implemented)
            Button graphTableButton = new Button("Table");
            // Button used to access the settings
            // (Settings Menu currently not implemented)
            Button settingsButton = new Button();

            //calcButton.setStyle("-fx-background-color: #2E303E; -fx-text-fill: #ffffff");
            //graphTableButton.setStyle("-fx-background-color: #2E303E; -fx-text-fill: #ffffff");
            calcButton.getStyleClass().add("topButton");
            graphTableButton.getStyleClass().add("topButton");


            // Notes for future addition of hovering effects on buttons
            /*
            EventHandler<DragEvent> enterHandler = new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent hover) {
                    if(hover.getTarget() == calcButton) {
                        calcButton.setId("topButton:hover");
                        while (hover.getTarget() == calcButton) {
                            continue;
                        }
                        calcButton.setId("topButton");
                    }
                    else if(hover.getTarget() == graphTableButton) {
                        graphTableButton.setId("topButton:hover");
                        while(hover.getTarget() == graphTableButton) {
                            continue;
                        }
                    }
                    else {
                        return;
                    }
                }
            };
            calcButton.setOnDragEntered(enterHandler);
            graphTableButton.setOnDragEntered(enterHandler);
            */

            // Adding Background image to settings menu
            Image menu = new Image(getClass().getResourceAsStream("images/menuButtonWhite.jpg"));
            BackgroundImage menuBut = new BackgroundImage(menu, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(settingsButton.getWidth(), settingsButton.getHeight(), true, true, true, false));
            Background settingsBG = new Background(menuBut);
            settingsButton.setBackground(settingsBG);

            // Create GridPane to hold the buttons
            GridPane topPane = new GridPane();

            // Creating ColumnConstraints for the GridPain in order to set the relative lengths for the buttons
            ColumnConstraints cc = new ColumnConstraints();
            ColumnConstraints ccSettings = new ColumnConstraints();

            // Set the top bane of the layout BorderPane to the GridPane 'topPane' in which will consist of 3 buttons
            layout.setTop(topPane);
            // Define the Max size and bind the topPane to its container 'layout'
            topPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            topPane.prefWidthProperty().bind(layout.widthProperty());

            // Set the relative lengths for the ColumnConstraints
            cc.setPercentWidth(90);
            ccSettings.setPercentWidth(10);
            // Add the ColumnConstraints to the GridPane 'topPane'
            topPane.getColumnConstraints().addAll(cc, cc, ccSettings);

            // Add the buttons to the topPane
            topPane.add(calcButton, 0, 0);
            topPane.add(graphTableButton, 1, 0);
            topPane.add(settingsButton, 2, 0);

            // Set the lengths for the buttons to span the length of their ColumnConstraints
            calcButton.setMaxWidth(Double.MAX_VALUE);
            graphTableButton.setMaxWidth(Double.MAX_VALUE);
            settingsButton.setMaxWidth(Double.MAX_VALUE);

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
            // Set the location of the chart in the BorderPane
            layout.setCenter(lineChart);
            // Plot the data on the chart
            plotData(lineChart);

            // Create a table of the data
            TableView dataTable = new TableView();

            // MouseEvent to change the Button text and Transition between LineChart and TableView
            graphTableButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(layout.getCenter() == lineChart) {
                        if(graphTableButton.getText() != "Table") {
                            graphTableButton.setText("Table");
                        }
                        else {
                            layout.setCenter(dataTable);
                            graphTableButton.setText("Graph");
                        }
                    }
                    else if (layout.getCenter() == dataTable) {
                        if(graphTableButton.getText() != "Graph") {
                            graphTableButton.setText("Graph");
                        }
                        else {
                            layout.setCenter(lineChart);
                            graphTableButton.setText("Table");
                        }
                    }
                    else {
                        return;
                    }
                }
            });


            // Create event handler for MouseEvent.MOUSE_CLICK

            calcButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // Create a new expression when the mouse is clicked
                    layout.setCenter(null);
                    layout.setCenter(containerCalc);

                    // SwingNode used previously to add embed the swing calculator inside the BorderPane
                    // Buttons would not display properly due to a conflict with the GridBagLayout used
                    // Could potentially be implemented with CardLayout
                    /*
                    final SwingNode calcNode = new SwingNode();
                    //CardLayout cLayout = new CardLayout();
                    //cLayout.next();

                    createAndSetSwingContent(calcNode);
                    calculator.initialize();
                    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    calcBox.getChildren().add(calcNode);

                    layout.setCenter(calcBox);
                    calcNode.requestFocus();
                     */

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
        Graph example = new Graph();

        //defining the axes
        //final NumberAxis xAxis = new NumberAxis(-10, 10, 1);
        double xMin = example.getXMin();
        double xMax = example.getXMax();
        double yMin = example.getYMin();
        double yMax = example.getYMax();
        double tickInterval = example.getTickDistance();

        NumberAxis xAxis = new NumberAxis(xMin, xMax, tickInterval);
        NumberAxis yAxis = new NumberAxis(yMin, yMax, tickInterval);

        //xAxis.setLabel("Number of Month");

        //creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        // setting the chart title
        //lineChart.setTitle("");

        // removes the legend from the chart (series name would go in there)
        lineChart.setLegendVisible(false);

        return lineChart;
    }

    /*****************************************************************
     Method that plots the data points on the lineChart
     *****************************************************************/
    public void plotData(LineChart lineChart) {
        //defining a series
        XYChart.Series series = new XYChart.Series();

        //series.setName("Series Name");

        //populating the series with data
        series.getData().add(new XYChart.Data(0, 2));
        series.getData().add(new XYChart.Data(1, 4));

        lineChart.getData().add(series);
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

        // Create the text field that will take in the user input
        TextField userInput = new TextField();

        // Create a new UserExpression on the MouseEvent
        UserExpression newExpression = new UserExpression(expressionCount, null);

        // Set the prompt text for the user
        userInput.setPromptText("Enter Expression Here");

        // Add the components to the GridPane and align them
        userExpressions.add(expressionID, 0, expressionCount - 1);
        userExpressions.add(userInput, 1, expressionCount - 1);

        // Shift the location of the addButton below the most recently created UserExpression
        userExpressions.setRowIndex(addButton, expressionCount + 1);
    }


    /*****************************************************************
     Creates and sets the content of the SwingNode for the swing calculator
     *****************************************************************/
    /*
    public void createAndSetSwingContent(final SwingNode calcNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                calcNode.setContent(calculator.cardPanel);
            }
        });
    }
    */

    /*****************************************************************
     Creates and sets the content of the SwingNode for the javafx calculator
     *****************************************************************/
    public void setupCalculator() {
        containerCalc = new VBox();
        containerCalc.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        containerCalc.prefWidthProperty().bind(layout.widthProperty().multiply(1.0));
        containerCalc.prefHeightProperty().bind(layout.heightProperty().multiply(1.0));
        containerCalc.setFillWidth(true);
        containerCalc.setSpacing(5.0);
        containerCalc.setPadding(new Insets(5,5,5,5));

        // Create expression input text area
        expressionInput = new TextArea();
        expressionInput.setMaxHeight(Double.MAX_VALUE);

        // Setup calculator buttons
        createCalcButtons();
        setupCalcButtons();

        // Add the components to the container
        containerCalc.getChildren().add(expressionInput);
        containerCalc.getChildren().add(calcButtonPane);
        // Set the components to vertically expand with a given priority
        containerCalc.setVgrow(expressionInput, Priority.SOMETIMES);
        containerCalc.setVgrow(calcButtonPane, Priority.ALWAYS);
    }

    /*****************************************************************
     Creates and labels the calculator buttons
     *****************************************************************/
    public void createCalcButtons() {
        Button butSquare = new Button("^2");
        Button butExp = new Button("^");
        Button butSqrt = new Button("√");
        Button butRt = new Button("∛");
        Button butEX = new Button("e^x");
        Button butLog = new Button("Log");
        Button butLN = new Button("LN");
        Button butSin = new Button("Sin");
        Button butCos = new Button("Cos");
        Button butTan = new Button("Tan");
        Button butOpenParen = new Button("(");
        Button butCloseParen = new Button(")");
        Button butVar = new Button("X");
        //Button butGraph = new Button("Graph");
        Button butDel = new Button("Del");
        Button butClear = new Button("Clear");
        Button butSeven = new Button("7");
        Button butEight = new Button("8");
        Button butNine = new Button("9");
        Button butMultiply = new Button("*");
        Button butDivide = new Button("/");
        Button butFour = new Button("4");
        Button butFive = new Button("5");
        Button butSix = new Button("6");
        Button butAdd = new Button("+");
        Button butSubtract = new Button("-");
        Button butOne = new Button("1");
        Button butTwo = new Button("2");
        Button butThree = new Button("3");
        Button butComma = new Button(",");
        Button butExe = new Button("EXE");
        Button butZero = new Button("0");
        Button butDot = new Button(".");
        Button butPi = new Button("π");
        Button butUndo = new Button("Undo");
        Button butAns = new Button("Answer");

        //calcButtons = new Button[25];
        calcButtons = new Button[]{butSquare, butExp, butSqrt, butRt, butEX, butLog, butLN,
            butCos, butSin, butTan, butOpenParen, butCloseParen, butVar, butDel, butClear,
            butSeven, butEight, butNine, butMultiply, butDivide, butFour, butFive, butSix,
            butAdd, butSubtract, butOne, butTwo, butThree, butPi, butExe, butZero, butDot, butComma, butUndo, butAns};
    }

    /*****************************************************************
     Function to add the calculator buttons to the Calculator Pane
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

        for(int bElement = 0; bElement < calcButtons.length; bElement++) {
            row = bElement / 5;
            col = bElement % 5;

            calcButtons[bElement].setId("calc-button");
            calcButtons[bElement].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            calcButtonPane.add(calcButtons[bElement], col, row);
            calcButtonPane.setVgrow(calcButtons[bElement], Priority.ALWAYS);
            Button currentButton = calcButtons[bElement];

            currentButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    expressionInput.appendText(currentButton.getText());
                }
            });
        }

    }

}