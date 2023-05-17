package pl.code.house.task.score.board;

import java.util.List;

public interface ScoreBoardService {

  /**
   * @return Find all active games sorted by total score and game started date-time properties.
   */
  List<GameResult> findActiveGames();

  /**
   * Creates a new game between two teams
   * @param teams pair of team names (home team and away team)
   * @return GameResult object with the current score set to 0-0
   */
  GameResult createNewGame(CompetitorsPair<String> teams);

  /**
   * Update the game score of a given game.
   * @param gameId Game that should be updated
   * @param newScore new score to be updated.
   * @return GameResult object with the updated score.
   */
  GameResult updateGameScore(Integer gameId, CompetitorsPair<Integer> newScore);

  /**
   * Mark the following game as finished.
   * @param gameId
   * @return GameResult object with the current score, marked as no longer active.
   */
  GameResult finishGame(Integer gameId);
}
