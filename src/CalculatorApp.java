import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp {
    // Create frame, panel, and text field
    private JFrame frame;
    private JTextField textField;
    private String operator = "";
    private double firstNumber;

    public static void main(String[] args) {
        // Run the application on the Event-Dispatching Thread
        SwingUtilities.invokeLater(() -> {
            CalculatorApp app = new CalculatorApp();
            app.createGUI();
        });
    }

    public void createGUI() {
        // Create a frame for the calculator
        frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

        // Create the text field to display input/output
        textField = new JTextField();
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        frame.add(textField, BorderLayout.NORTH);

        // Create panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        // Button definitions (0-9, operations, etc.)
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        // Add buttons to the panel
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Action listener for buttons
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            // If the user presses 'C', clear the input
            if (command.equals("C")) {
                textField.setText("");
                firstNumber = 0;
                operator = "";
            }
            // If the user presses '=', calculate the result
            else if (command.equals("=")) {
                double secondNumber = Double.parseDouble(textField.getText().split("[+\\-*/]")[1].trim());
                double result = 0;

                switch (operator) {
                    case "+":
                        result = firstNumber + secondNumber;
                        break;
                    case "-":
                        result = firstNumber - secondNumber;
                        break;
                    case "*":
                        result = firstNumber * secondNumber;
                        break;
                    case "/":
                        if (secondNumber != 0) {
                            result = firstNumber / secondNumber;
                        } else {
                            textField.setText("Error");
                            return;
                        }
                        break;
                }

                textField.setText(String.valueOf(result));
                firstNumber = result;
                operator = "";
            }
            // If the user presses an operator (+, -, *, /)
            else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
                // Store the first number and operator, then display it
                if (!textField.getText().isEmpty()) {
                    firstNumber = Double.parseDouble(textField.getText());
                    operator = command;
                    textField.setText(textField.getText() + " " + operator + " ");
                }
            }
            // Otherwise, it's a number, so append it to the display
            else {
                textField.setText(textField.getText() + command);
            }
        }
    }
}
