package pl.sokol.pacman.gui.panels.loading;

import lombok.Getter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;

import static pl.sokol.pacman.Utils.FRAME_HEIGHT;
import static pl.sokol.pacman.Utils.FRAME_WIDTH;

@Getter
public class LoadingPanelView extends JPanel {

    private JPanel mainPanel;
    private JPanel loadingPanel;
    private JButton backToMenuButton;
    private JScrollPane scrollPanel;

    public LoadingPanelView() {
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        scrollPanel.setViewportView(loadingPanel);
        scrollPanel.getVerticalScrollBar().setBackground(Color.BLACK);
        scrollPanel.setBorder(null);
        this.add(mainPanel);
    }
}
