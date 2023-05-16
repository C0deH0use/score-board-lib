package pl.code.house.task.score.board;

import java.util.Comparator;

public class GameResultComparator implements Comparator<GameResult> {

  public static final Comparator<GameResult> Instance = new GameResultComparator();

  @Override
  public int compare(GameResult game1, GameResult game2) {
    var scoreComparator = game2.totalScore().compareTo(game1.totalScore());
    if (scoreComparator != 0) {
      return scoreComparator;
    }

    return game2.started().compareTo(game1.started());
  }
}
