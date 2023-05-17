package pl.code.house.task.score.board;

import java.time.LocalDateTime;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomUtils;

/**
 * Class representing the current game state between two teams.
 * @param gameId Game Id, int type
 * @param teams Team names, CompetitorsPair of String type.
 * @param score game score, CompetitorsPair of String type.
 * @param finished status of the date, is the game still ongoing or now.
 * @param started Date when the game started to be tracked on the score board.
 */
public record GameResult(Integer gameId, CompetitorsPair<String> teams, CompetitorsPair<Integer> score, boolean finished, LocalDateTime started) {

  private static final CompetitorsPair<Integer> INITIAL_SCORE = new CompetitorsPair<>(0, 0);

  /**
   * Methods used to calculate the total score sum of the game
   * For Example:
   * 0 - 0 will return total score of value 0.
   * 1 - 1 will return total score of value 2.
   * 3 - 8 will return total score of value 11.
   * @return Returns the sum of the home and away team scores.
   */
  Integer totalScore() {
    return IntStream.of(score.homeTeam(), score.awayTeam()).sum();
  }

  /**
   * returns a new GameResult value with the new updated score.
   * @param newScore the score of the game that should be updated.
   * @return Returns the GameResult object with the updated game score.
   */
  public GameResult updateScore(CompetitorsPair<Integer> newScore) {
    return new GameResult(this.gameId, this.teams, newScore, this.finished, this.started);
  }

  /**
   * Marks the current game as finished.
   * @return Returns the game result with the latest score.
   */
  public GameResult markAsFinished() {
    return new GameResult(this.gameId, this.teams, this.score, true, this.started);
  }

  /**
   * Based on the parameters, creates a new GameResult object with score set to 0 - 0.
   * @param teams Team names that are playing against each-other in the match.
   * @return Returns the GameResult object with the initial game score.
   */
  public static GameResult newGame(CompetitorsPair<String> teams) {
    var gameId = RandomUtils.nextInt(0, Integer.MAX_VALUE);
    return new GameResult(gameId, teams, INITIAL_SCORE, false, LocalDateTime.now());
  }
}
