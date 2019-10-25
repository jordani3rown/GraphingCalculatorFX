package ChartGUI;

import javafx.scene.layout.HBox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends HBox implements ActionListener {
    private String userInput;
    //private final JFrame frame;
    public final JPanel panel;
    public final JPanel cardPanel;
    private final JTextArea text;
    private GridBagConstraints gbc;
    private final JButton but[], butSquare, butExp, butClear, butSqrt, butRt,
            butLog, butLN, butDivide, butCos, butSin, butTan, butOpenParen, butCloseParen,
            butDel, butPi, butExe, butAdd, butMinus, butMultiply, butDot, butEX, butVar, butGraph,
            butUndo, butAns, butComma;

    calc backEnd;

    public Calculator(){
        //frame = new JFrame("Calculator PH");
        //frame.setResizable(false);
        panel = new JPanel(new GridBagLayout());
        cardPanel = new JPanel(new CardLayout());

        gbc = new GridBagConstraints();
        text = new JTextArea(4, 10);
        but = new JButton[10];
        for (int i = 0; i < 10; i++) {
            but[i] = new JButton(String.valueOf(i));
        }

        butSquare = new JButton("^2");
        butExp = new JButton("^");
        butSqrt= new JButton("√");
        butRt = new JButton("∛");
        butEX = new JButton("e^x");
        butLog = new JButton("Log");
        butLN = new JButton("LN");
        butSin = new JButton("Sin");
        butCos = new JButton("Cos");
        butTan = new JButton("Tan");
        butOpenParen = new JButton("(");
        butCloseParen = new JButton(")");
        butVar = new JButton("X");
        butGraph = new JButton("Graph");
        butDel = new JButton("Del");
        butClear = new JButton("Clear");
        butMultiply = new JButton("*");
        butDivide = new JButton("/");
        butAdd = new JButton("+");
        butMinus = new JButton("-");
        butDot = new JButton(".");
        butExe = new JButton("EXE");
        butPi = new JButton("π");
        butUndo = new JButton("Undo");
        butAns = new JButton("Answer");
        butComma = new JButton(",");
        backEnd = new calc();
        userInput = "";
    }

    public void initialize() {
        //frame.setVisible(true);
        //frame.setSize( 400, 700);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //panel.setVisible(true);
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH; // fill cell
        gbc.weightx = .5; //expand to fill container size
        gbc.weighty = .5; //  "    "   "      "       "
        addComponent(text, 0, 0, 5);
        text.setFont(text.getFont().deriveFont(24f));
        addComponent(butSquare, 0, 1, 1);
        addComponent(butExp, 1, 1, 1);
        addComponent(butSqrt, 2, 1, 1);
        addComponent(butRt, 3, 1, 1);
        addComponent(butEX, 4, 1, 1);
        addComponent(butLog, 0, 2, 1);
        addComponent(butLN, 1, 2, 1);
        addComponent(butSin, 2,2, 1);
        addComponent(butCos, 3, 2, 1);
        addComponent(butTan, 4, 2, 1);
        addComponent(butOpenParen, 0, 3, 1);
        addComponent(butCloseParen,1,3,1);
        addComponent(butVar, 2,3,1);
        addComponent(butGraph, 3, 3, 2);
        for(int i = 1; i < 4; i++){
            addComponent(but[i], i-1, 4, 1);
            but[i].addActionListener(this);
        }
        addComponent(butDel, 3, 4, 1);
        addComponent(butClear, 4,4,1);
        for(int i = 4; i < 7; i++){
            addComponent(but[i], i-4, 5, 1);
            but[i].addActionListener(this);
        }
        addComponent(butMultiply, 3, 5, 1);
        addComponent(butDivide, 4, 5, 1);
        for(int i = 7; i < 10; i++){
            addComponent(but[i], i-7, 6, 1);
            but[i].addActionListener(this);
        }
        addComponent(butAdd, 3, 6, 1);
        addComponent(butMinus, 4, 6, 1);
        addComponent(but[0], 0, 7, 1);
        addComponent(butDot, 1, 7, 1);
        addComponent(butPi, 3, 7, 1);
        addComponent(butExe, 4, 7, 1);
        addComponent(butUndo, 0, 8, 1);
        addComponent(butAns, 1, 8, 1);
        addComponent(butComma,2,8,1);

        but[0].addActionListener(this);
        butComma.addActionListener(this);
        butDot.addActionListener(this);
        butEX.addActionListener(this);
        butAdd.addActionListener(this);
        butMinus.addActionListener(this);
        butMultiply.addActionListener(this);
        butDivide.addActionListener(this);
        butSquare.addActionListener(this);
        butExe.addActionListener(this);
        butExp.addActionListener(this);
        butCos.addActionListener(this);
        butSin.addActionListener(this);
        butTan.addActionListener(this);
        butClear.addActionListener(this);
        butSqrt.addActionListener(this);
        butOpenParen.addActionListener(this);
        butCloseParen.addActionListener(this);
        butRt.addActionListener(this);
        butDel.addActionListener(this);
        butLog.addActionListener(this);
        butLN.addActionListener(this);
        butPi.addActionListener(this);
        butUndo.addActionListener(this);
        butAns.addActionListener(this);


        cardPanel.add(panel);
        cardPanel.setVisible(true);

        //cardPanel.setLayout(new CardLayout());

    }

    private final void addComponent(Component c, int gridx, int gridy, int gridwidth) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        panel.add(c, gbc);
    }

    private String displayExpression(){
        userInput = backEnd.getExpression();
        if (userInput.contains("pi")) {
            userInput = userInput.replaceAll("pi", "π");
        }
        if(userInput.contains("sqrt")){
            userInput = userInput.replaceAll("sqrt", "√");
        }
        if(userInput.contains("cbrt")){
            userInput = userInput.replaceAll("cbrt", "∛");
        }
        return userInput;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();

        for (int i = 0; i < 10; i++) {
            if (source == but[i]) {
                backEnd.addToExpression(""+i);
                text.setText(displayExpression());
                return;
            }
        }

        if (source == butSquare) {
            if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))){
                JOptionPane.showMessageDialog(cardPanel, "Cannot square a non-number!");
                return;
            }
            backEnd.addToExpression("^2");
        }

        if (source == butExp) {
            if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                JOptionPane.showMessageDialog(cardPanel, "Cannot exponentiate a non-number!");
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
                JOptionPane.showMessageDialog(cardPanel, "Syntax: Number must precede division symbol");
                return;
            }
            backEnd.addToExpression("/");
        }
        if (source == butAdd) {
            if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                JOptionPane.showMessageDialog(cardPanel, "Syntax: Number must precede addition symbol");
                return;
            }
            backEnd.addToExpression("+");
        }
        if (source == butMinus) {
            if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                JOptionPane.showMessageDialog(cardPanel, "Syntax: Number must precede minus symbol");
                return;
            }
            backEnd.addToExpression("-");
        }
        if(source == butMultiply){
            if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                JOptionPane.showMessageDialog(cardPanel, "Syntax: Number must precede multiplication symbol");
                return;
            }
            backEnd.addToExpression("*");
        }
        if (source == butExe) {
            double result = backEnd.evaluate();
            backEnd.clear();
            backEnd.addToExpression(""+result);
        }
        if(source == butEX){
            backEnd.addToExpression("e^");
        }
        if(source == butDot){
            if(backEnd.getExpression().length()==0||!Character.isDigit(backEnd.getExpression().charAt(backEnd.getExpression().length()-1))||backEnd.getExpression().length()==0){
                JOptionPane.showMessageDialog(cardPanel, "Syntax: Number must precede a dot");
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
        text.setText(displayExpression());
        // text.selectAll();
    }

}
