package pl.code.house.task.score.board;

import java.util.Comparator;

/**
 * GameResult sorting algorithm
 */
class GameResultComparator implements Comparator<GameResult> {

  static final Comparator<GameResult> Instance = new GameResultComparator();

  /**
   * Used to compare two game results by the following: totalScore(desc) and game started date (desc)
   */
  @Override
  public int compare(GameResult game1, GameResult game2) {
    var scoreComparator = game2.totalScore().compareTo(game1.totalScore());
    if (scoreComparator != 0) {
      return scoreComparator;
    }

    return game2.started().compareTo(game1.started());
  }

}
