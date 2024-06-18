import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private JTextField display;
    private double result;
    private String operator;
    private boolean startNewNumber;

    public Calculator() {
        setTitle("계산기");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 메뉴바 추가
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);

        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        String[] buttons = {
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "0", "AC", "=", "/"
        };

        for (String text : buttons) {
            addButton(panel, text);
        }

        add(panel, BorderLayout.CENTER);

        result = 0;
        operator = "=";
        startNewNumber = true;
    }

    private void addButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        button.addActionListener(new ButtonClickListener());
        panel.add(button);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("0123456789".indexOf(command) >= 0) {
                if (startNewNumber) {
                    display.setText(command);
                    startNewNumber = false;
                } else {
                    display.setText(display.getText() + command);
                }
            } else {
                if (startNewNumber) {
                    if (command.equals("-")) {
                        display.setText(command);
                        startNewNumber = false;
                    } else {
                        operator = command;
                    }
                } else {
                    double x = Double.parseDouble(display.getText());
                    calculate(x);
                    operator = command;
                    startNewNumber = true;
                }
            }

            if (command.equals("AC")) {
                result = 0;
                display.setText("");
                startNewNumber = true;
                operator = "=";
            }

            if (command.equals("=")) {
                double x = Double.parseDouble(display.getText());
                calculate(x);
                operator = "=";
                startNewNumber = true;
            }
        }

        private void calculate(double n) {
            switch (operator) {
                case "+":
                    result += n;
                    break;
                case "-":
                    result -= n;
                    break;
                case "*":
                    result *= n;
                    break;
                case "/":
                    if (n != 0) {
                        result /= n;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                case "=":
                    result = n;
                    break;
            }
            display.setText(formatResult(result));
        }

        private String formatResult(double result) {
            if (result == (long) result) {
                return String.format("%d", (long) result);
            } else {
                return String.format("%s", result);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
