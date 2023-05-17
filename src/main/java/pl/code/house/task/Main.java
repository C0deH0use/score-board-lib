package pl.code.house.task;

import static pl.code.house.task.score.board.CompetitorsPair.pairOf;

import java.time.Duration;
import java.util.List;
import pl.code.house.task.score.board.GameResult;
import pl.code.house.task.score.board.ScoreBoardFactory;

public class Main {

  public static void main(String[] args) {
    try {

      System.out.println("Hello world!");
      var scoreBoard = ScoreBoardFactory.createDefault();

      System.out.println();
      System.out.println("First games about to be started....");
      var gameOneId = scoreBoard.createNewGame(pairOf("Poland", "Germany")).gameId();
      var gameTwoId = scoreBoard.createNewGame(pairOf("Austria", "Switzerland")).gameId();

      Thread.sleep(Duration.ofSeconds(10));
      scoreBoard.updateGameScore(gameOneId, pairOf(1, 0));
      loggGameResults(scoreBoard.findActiveGames());

      Thread.sleep(Duration.ofSeconds(10));
      scoreBoard.updateGameScore(gameTwoId, pairOf(0, 1));
      loggGameResults(scoreBoard.findActiveGames());

      System.out.println();
      System.out.println("Second round of games about to be started....");
      var gameThreeId = scoreBoard.createNewGame(pairOf("Slovenia", "Croatia")).gameId();
      scoreBoard.updateGameScore(gameOneId, pairOf(2, 0));
      scoreBoard.updateGameScore(gameOneId, pairOf(2, 1));
      loggGameResults(scoreBoard.findActiveGames());
      Thread.sleep(Duration.ofSeconds(10));


      var gameFourId = scoreBoard.createNewGame(pairOf("France", "Great Britain")).gameId();
      loggGameResults(scoreBoard.findActiveGames());
      scoreBoard.updateGameScore(gameTwoId, pairOf(1, 1));
      loggGameResults(scoreBoard.findActiveGames());

      scoreBoard.updateGameScore(gameThreeId, pairOf(1, 0));
      scoreBoard.updateGameScore(gameThreeId, pairOf(1, 1));
      scoreBoard.updateGameScore(gameThreeId, pairOf(2, 1));
      Thread.sleep(Duration.ofSeconds(10));

      scoreBoard.updateGameScore(gameThreeId, pairOf(3, 1));

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