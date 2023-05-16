package pl.code.house.task.score.board;

import java.time.LocalDateTime;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.Pair;

public record GameResult(Integer gameId, String homeTeam, String awayTeam, Pair<Integer, Integer> score, boolean finished, LocalDateTime started) {

  private static final Pair<Integer, Integer> INITIAL_SCORE = Pair.of(0, 0);

  public Integer totalScore() {
    return IntStream.of(score.getLeft(), score.getRight()).sum();
  }

  public GameResult updateScore(Pair<Integer, Integer> newScore) {
    return new GameResult(this.gameId, this.homeTeam, this.awayTeam, newScore, this.finished, this.started);
  }

  public GameResult markAsFinished() {
    return new GameResult(this.gameId, this.homeTeam, this.awayTeam, this.score, true, this.started);
  }

  public static GameResult newGame(Pair<String, String> teams) {
    var gameId = RandomUtils.nextInt(0, Integer.MAX_VALUE);
    return new GameResult(gameId, teams.getLeft(), teams.getRight(), INITIAL_SCORE, false, LocalDateTime.now());
  }
}
