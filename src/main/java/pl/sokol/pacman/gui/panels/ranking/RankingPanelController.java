package pl.sokol.pacman.gui.panels.ranking;

import lombok.Getter;
import pl.sokol.pacman.database.dao.RankingDao;
import pl.sokol.pacman.database.domain.Ranking;
import pl.sokol.pacman.gui.frame.GameFrameController;

import javax.swing.table.DefaultTableModel;
import java.util.Collections;
import java.util.List;

@Getter
public class RankingPanelController {

    private RankingPanelView view;

    public RankingPanelController(GameFrameController game) {
        this.view = new RankingPanelView();
        initListeners(game);
        initRankingPanel();
    }

    private void initListeners(GameFrameController game) {
        view.getBackToMenuButton().addActionListener(e -> game.backToMenu());
    }

    private void initRankingPanel() {
        RankingDao rankingDao = new RankingDao();
        List<Ranking> rankings = rankingDao.getAll();
        DefaultTableModel model = initRankingTable();

        // sort list by highest score
        Collections.sort(rankings);

        // fill table with ranking values
        for (int i = 0; i < rankings.size(); i++) {
            model.addRow(
                    new Object[]{
                            i + 1,
                            rankings.get(i).getNick(),
                            rankings.get(i).getPoints(),
                    }
            );
        }
        view.getRankingTable().setModel(model);
        rankingDao.shutdown();
    }

    private DefaultTableModel initRankingTable() {
        return new DefaultTableModel(
                new String[]{
                        "Position", "Nick", "Score"
                }, 0);
    }
}
