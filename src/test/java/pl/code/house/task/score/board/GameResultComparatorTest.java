package pl.code.house.task.score.board;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.code.house.task.score.board.CompetitorsPair.pairOf;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameResultComparatorTest {

  private static final Clock clock = Clock.fixed(Instant.parse("2023-05-16T20:15:30.00Z"), ZoneOffset.UTC);

  @Test
  @DisplayName("should order game results by total score")
  void shouldOrderGameResultsByTotalScore() {
    // given
    var gameOne = new GameResult(1, pairOf("D Team", "Dd Team"), pairOf(0, 0), false, now(clock));
    var gameTwo = new GameResult(2, pairOf("A Team", "D Team"), pairOf(1, 1), false, now(clock));
    var gameThree = new GameResult(3, pairOf("A Team", "B Team"), pairOf(2, 1), false, now(clock));
    var list = List.of(
        gameOne,
        gameTwo,
        gameThree
    );

    //when
    var result = list.stream().sorted(GameResultComparator.Instance).toList();

    //then
    assertThat(result).containsExactly(gameThree, gameTwo, gameOne);
  }

  @Test
  @DisplayName("should sorting additionally by games started earlier when score is same")
  void shouldSortingAdditionallyByGamesStartedEarlierWhenScoreIsSame() {
    // given
    var gameOne = new GameResult(1, pairOf("D Team", "Dd Team"), pairOf(0, 0), false, now(clock));
    var gameTwo = new GameResult(2, pairOf("C Team", "B Team"), pairOf(3, 1), true, now(clock));
    var gameThree = new GameResult(3, pairOf("A Team", "B Team"), pairOf(3, 0), false, now(clock).plusMinutes(10));
    var gameFour = new GameResult(4,pairOf( "A Team", "C Team"), pairOf(3, 0), false, now(clock));
    var list = List.of(
        gameOne,
        gameTwo,
        gameThree,
        gameFour
    );

    //when
    var result = list.stream().sorted(GameResultComparator.Instance).toList();

    //then
    assertThat(result).containsExactly(gameTwo, gameThree, gameFour, gameOne);
  }


  @Test
  @DisplayName("should correctly order task example")
  void shouldCorrectlyOrderTaskExample() {
    // given
    var gameOne = new GameResult(1, pairOf("Mexico", "Canada"), pairOf(0, 5), false, now());
    var gameTwo = new GameResult(2, pairOf("Spain", "Brazil"), pairOf(10, 2), true, now());
    var gameThree = new GameResult(3, pairOf("Germany", "France"), pairOf(2, 2), false, now());
    var gameFour = new GameResult(4, pairOf("Uruguay", "Italy"), pairOf(6, 6), false, now());
    var gameFive = new GameResult(5, pairOf("Argentina", "Australia"), pairOf(3, 1), false, now());
    var list = List.of(
        gameOne,
        gameTwo,
        gameThree,
        gameFour,
        gameFive
    );

    //when
    var result = list.stream().sorted(GameResultComparator.Instance).toList();

    //then
    assertThat(result).containsExactly(gameFour, gameTwo, gameOne, gameFive, gameThree);
  }
}