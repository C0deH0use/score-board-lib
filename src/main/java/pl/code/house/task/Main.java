package pl.code.house.task;

import java.time.Duration;
import java.util.List;
import pl.code.house.task.score.board.CompetitorsPair;
import pl.code.house.task.score.board.GameResult;
import pl.code.house.task.score.board.ScoreBoardFactory;

public class Main {

  public static void main(String[] args) {
    try {

      System.out.println("Hello world!");
      var scoreBoard = ScoreBoardFactory.createDefault();

      System.out.println();
      System.out.println("First games about to be started....");
      var gameOneId = scoreBoard.createNewGame(new CompetitorsPair("Poland", "Germany")).gameId();
      var gameTwoId = scoreBoard.createNewGame(new CompetitorsPair("Austria", "Switzerland")).gameId();

      Thread.sleep(Duration.ofSeconds(10));
      scoreBoard.updateGameScore(gameOneId, new CompetitorsPair<>(1, 0));
      loggGameResults(scoreBoard.findActiveGames());

      Thread.sleep(Duration.ofSeconds(10));
      scoreBoard.updateGameScore(gameTwoId, new CompetitorsPair<>(0, 1));
      loggGameResults(scoreBoard.findActiveGames());

      System.out.println();
      System.out.println("Second round of games about to be started....");
      var gameThreeId = scoreBoard.createNewGame(new CompetitorsPair("Slovenia", "Croatia")).gameId();
      scoreBoard.updateGameScore(gameOneId, new CompetitorsPair<>(2, 0));
      scoreBoard.updateGameScore(gameOneId, new CompetitorsPair<>(2, 1));
      loggGameResults(scoreBoard.findActiveGames());
      Thread.sleep(Duration.ofSeconds(10));


      var gameFourId = scoreBoard.createNewGame(new CompetitorsPair("France", "Great Britain")).gameId();
      loggGameResults(scoreBoard.findActiveGames());
      scoreBoard.updateGameScore(gameTwoId, new CompetitorsPair<>(1, 1));
      loggGameResults(scoreBoard.findActiveGames());

      scoreBoard.updateGameScore(gameThreeId, new CompetitorsPair<>(1, 0));
      scoreBoard.updateGameScore(gameThreeId, new CompetitorsPair<>(1, 1));
      scoreBoard.updateGameScore(gameThreeId, new CompetitorsPair<>(2, 1));
      Thread.sleep(Duration.ofSeconds(10));

      scoreBoard.updateGameScore(gameThreeId, new CompetitorsPair<>(3, 1));

      Thread.sleep(Duration.ofSeconds(10));
      scoreBoard.finishGame(gameOneId);
      scoreBoard.finishGame(gameTwoId);
      loggGameResults(scoreBoard.findActiveGames());


      Thread.sleep(Duration.ofSeconds(10));
      scoreBoard.finishGame(gameFourId);

      loggGameResults(scoreBoard.findActiveGames());


    } catch (InterruptedException exception) {
      System.err.println("Thread error occurred stopping the game");
    }
  }


  private static final void loggGameResults(List<GameResult> results) {
    System.out.println();
    System.out.println(" ======= ScoreBoard ======= ");
    results.forEach(game -> System.out.println("[Game %s] %s score - %s".formatted(game.gameId(), game.teams().log(), game.score().log())));
    System.out.println(" ========================== ");
    System.out.println();
  }
}