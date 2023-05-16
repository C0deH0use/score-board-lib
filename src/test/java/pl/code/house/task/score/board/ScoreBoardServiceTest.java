package pl.code.house.task.score.board;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreBoardServiceTest {

  private static final Clock clock = Clock.fixed(Instant.parse("2023-05-16T20:15:30.00Z"), ZoneOffset.UTC);

  private final String TeamOne = "Argentina";
  private final String TeamTwo = "Austria";
  private final Pair<Integer, Integer> Score = Pair.of(0, 0);
  private ScoreBoardService sut;
  private ScoreBoardRepository repository;
  private Map<Integer, GameResult> dbSource;

  @BeforeEach
  void setUp() {
    dbSource = new ConcurrentHashMap<>();
    repository = new HashMapScoreBoardRepository(dbSource);
    sut = new ScoreBoardServiceImpl(repository);
  }

  @Test
  @DisplayName("should add new game to the score board")
  void shouldAddNewGameToTheScoreBoard() {
    //given
    GameResult expectedGame = new GameResult(1, TeamOne, TeamTwo, Score, false, now());

    //when
    sut.createNewGame(Pair.of(TeamOne, TeamTwo));

    //then
    List<GameResult> gameResults = sut.findActiveGames();
    assertThat(gameResults).hasSize(1);
    assertThat(gameResults)
        .usingDefaultComparator()
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("gameId", "started")
        .contains(expectedGame);
  }

  @Test
  @DisplayName("should update existing game by new score")
  void shouldUpdateExistingGameByNewScore() {
    //given
    var gameId = 100;
    var newScore = Pair.of(0, 2);
    repository.save(new GameResult(gameId, TeamOne, TeamTwo, Score, false, now(clock)));
    GameResult expectedGame = new GameResult(gameId, TeamOne, TeamTwo, newScore, false, now(clock));

    //when
    sut.updateGameScore(gameId, newScore);

    //then
    List<GameResult> gameResults = sut.findActiveGames();
    assertThat(gameResults).hasSize(1);
    assertThat(gameResults).contains(expectedGame);
  }

  @Test
  @DisplayName("should update existing game as finished")
  void shouldUpdateExistingGameAsFinished() {
    //given
    var gameId = 100;
    repository.save(new GameResult(gameId, TeamOne, TeamTwo, Score, false, now(clock)));
    GameResult expectedGame = new GameResult(gameId, TeamOne, TeamTwo, Score, true, now(clock));

    //when
    sut.finishGame(gameId);

    //then
    List<GameResult> gameResults = sut.findActiveGames();
    assertThat(gameResults).isEmpty();

    // and
    Collection<GameResult> values = dbSource.values();
    assertThat(values).hasSize(1);
    assertThat(values).contains(expectedGame);
  }

  @Test
  @DisplayName("should throw exception when updating game that does not exists")
  void shouldThrowExceptionWhenUpdatingGameThatDoesNotExists() {
    //when & then
    assertThatThrownBy(() -> sut.updateGameScore(1, Pair.of(0, 2)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Game with the following id 1 does not exist");
  }

  @Test
  @DisplayName("should throw exception when marking game as finished that does not exists")
  void shouldThrowExceptionWhenMarkingGameAsFinishedThatDoesNotExists() {
    //when & then
    assertThatThrownBy(() -> sut.finishGame(1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Game with the following id 1 does not exist");
  }

  @Test
  @DisplayName("should throw exception when trying to mark game as finished when one already ended")
  void shouldThrowExceptionWhenTryingToMarkGameAsFinishedWhenOneAlreadyEnded() {
    // given
    var gameId = 100;
    repository.save(new GameResult(gameId, TeamOne, TeamTwo, Score, true, now(clock)));

    //when & then
    assertThatThrownBy(() -> sut.finishGame(gameId))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("Game is already finished");
  }
}