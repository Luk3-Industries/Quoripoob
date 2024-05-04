package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class SetUpGameGUI extends JPanel{
    public static final Dimension PREFERRED_DIMENSION = new Dimension(250, 30);

    QuoridorGUI quoridorGUI;

    // West
    private JPanel panelWest;
    private JLabel imageGame;
    private JLabel labelTitle;
    private JLabel labelDescription;
    private JPanel panelSquares;
    private JLabel labelSquareTitle;
    private JTextField textNormalSquares;
    private JTextField textTeleporterSquares;
    private JTextField textReturnSquares;
    private JTextField textDoubleTurnSquares;

    // East
    private JPanel panelEast;
    private JTextField textBoardSize;
    private JTextField textTime;
    private JPanel panelWalls;
    private JLabel labelWallTitle;
    private JTextField textNormalWalls;
    private JTextField textTemporaryWalls;
    private JTextField textLongWalls;
    private JTextField textAlliedWalls;
    private JButton buttonNext;

    public SetUpGameGUI(QuoridorGUI quoridorGUI) {
        this.quoridorGUI = quoridorGUI;
        prepareElements();
        prepareActions();
        setVisible(true);
    }

    private void prepareElements() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new RoundBorder(Color.WHITE, Color.WHITE, 20));

        prepareElementsWest(content);
        prepareElementsEast(content);

        add(content);
    }

    private void prepareElementsWest(JPanel content) {
        panelWest = new JPanel();
        panelWest.setBackground(Color.WHITE);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new EmptyBorder(0, 0, 0, 50));
        container.setBackground(Color.WHITE);

        imageGame = new JLabel();
        imageGame.setSize(50, 50);
        createImage(imageGame, "assets/Logo.png");

        labelTitle = new JLabel("Set up the game");
        labelTitle.setFont(new Font(QuoridorGUI.FONT_TITLE, Font.BOLD, 25));

        labelDescription = new JLabel("Enter the game data");
        labelDescription.setFont(new Font(QuoridorGUI.FONT_TEXT, Font.PLAIN, 15));

        container.add(imageGame);
        container.add(Box.createVerticalStrut(10));
        container.add(labelTitle);
        container.add(Box.createVerticalStrut(10));
        container.add(labelDescription);
        container.add(Box.createVerticalStrut(50));
        container.add(prepareElementsSquares());
        panelWest.add(container);

        content.add(panelWest, BorderLayout.WEST);
    }

    private JPanel prepareElementsSquares() {
        panelSquares = new JPanel();
        panelSquares.setLayout(new BoxLayout(panelSquares, BoxLayout.Y_AXIS));
        panelSquares.setBackground(Color.WHITE);

        labelSquareTitle = new JLabel("Squares");
        labelSquareTitle.setFont(new Font(QuoridorGUI.FONT_SUBTITLE, Font.BOLD, 15));

        textNormalSquares = createTextField("Number of normal squares");
        textTeleporterSquares = createTextField("Number of teleporter squares");
        textReturnSquares = createTextField("Number of return squares");
        textDoubleTurnSquares = createTextField("Number of double turn squares");

        panelSquares.add(labelSquareTitle);
        panelSquares.add(Box.createVerticalStrut(10));
        panelSquares.add(textNormalSquares);
        panelSquares.add(Box.createVerticalStrut(10));
        panelSquares.add(textTeleporterSquares);
        panelSquares.add(Box.createVerticalStrut(10));
        panelSquares.add(textReturnSquares);
        panelSquares.add(Box.createVerticalStrut(10));
        panelSquares.add(textDoubleTurnSquares);

        return panelSquares;
    }

    private void prepareElementsEast(JPanel content) {
        panelEast = new JPanel();
        panelEast.setBackground(Color.WHITE);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        JPanel panelButtonNext = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelButtonNext.setBackground(Color.WHITE);

        textBoardSize = createTextField("Board size");
        textTime = createTextField("Time");

        buttonNext = createButton("Next", QuoridorGUI.BUTTONS_COLOR, Color.WHITE, QuoridorGUI.BUTTONS_COLOR_HOVER);
        buttonNext.setBorder(new EmptyBorder(7, 7, 7, 7));

        panelButtonNext.add(buttonNext);
        container.add(Box.createVerticalStrut(70));
        container.add(textBoardSize);
        container.add(Box.createVerticalStrut(10));
        container.add(textTime);
        container.add(Box.createVerticalStrut(30));
        container.add(prepareElementsWalls());
        container.add(Box.createVerticalStrut(50));
        container.add(panelButtonNext);
        panelEast.add(container);

        content.add(panelEast, BorderLayout.EAST);
    }

    private JPanel prepareElementsWalls() {
        panelWalls = new JPanel();
        panelWalls.setLayout(new BoxLayout(panelWalls, BoxLayout.Y_AXIS));
        panelWalls.setBackground(Color.WHITE);

        labelWallTitle = new JLabel("Walls");
        labelWallTitle.setFont(new Font(QuoridorGUI.FONT_SUBTITLE, Font.BOLD, 15));

        textNormalWalls = createTextField("Number of normal Walls");
        textTemporaryWalls = createTextField("Number of temporary walls");
        textLongWalls = createTextField("Number of long walls");
        textAlliedWalls = createTextField("Number of allied walls");

        panelWalls.add(labelWallTitle);
        panelWalls.add(Box.createVerticalStrut(10));
        panelWalls.add(textNormalWalls);
        panelWalls.add(Box.createVerticalStrut(10));
        panelWalls.add(textTemporaryWalls);
        panelWalls.add(Box.createVerticalStrut(10));
        panelWalls.add(textLongWalls);
        panelWalls.add(Box.createVerticalStrut(10));
        panelWalls.add(textAlliedWalls);

        return panelWalls;
    }

    private JButton createButton(String text, Color background, Color foreGround, Color hover) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(background);
        button.setForeground(foreGround);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent ev) {
                button.setBackground(hover);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        
            public void mouseExited(MouseEvent ev) {
                button.setBackground(background);
                setCursor(Cursor.getDefaultCursor());
            }
        });
    
        return button;
    }

    private JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setPreferredSize(PREFERRED_DIMENSION);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent ev) {
                if (textField.getText().equals(text)) {
                    textField.setText("");
                }
            }

            public void focusLost(FocusEvent ev) {
                if (textField.getText().isEmpty()) {
                    textField.setText(text);
                }
            }
        });

        return textField;
    }

    private void prepareActions() {

    }

    private void createImage(JLabel label, String path) {
		URL url = getClass().getResource(path);
		if (url != null) {
			ImageIcon img = new ImageIcon(url);
			label.setIcon(new ImageIcon(img.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH)));
			label.setHorizontalAlignment(SwingConstants.CENTER);
        	label.setVerticalAlignment(SwingConstants.CENTER);
		}
	}
}
