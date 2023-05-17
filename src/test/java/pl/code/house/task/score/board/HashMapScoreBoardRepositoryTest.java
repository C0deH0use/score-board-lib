package pl.code.house.task.score.board;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.code.house.task.score.board.CompetitorsPair.pairOf;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HashMapScoreBoardRepositoryTest {

  private static final Clock clock = Clock.fixed(Instant.parse("2023-05-16T20:15:30.00Z"), ZoneOffset.UTC);

  private final String TeamOne = "AWAY_TEAM";
  private final String TeamTwo = "Home_TEAM";
  private final CompetitorsPair<Integer> Score = pairOf(0, 0);

  private HashMapScoreBoardRepository sut;

  @BeforeEach
  void setUp() {
    sut = new HashMapScoreBoardRepository(new ConcurrentHashMap<>());
  }

  @Test
  @DisplayName("should store new game")
  void shouldStoreNewGame() {
    //given
    GameResult game = GameResult.newGame(pairOf(TeamOne, TeamTwo));
    GameResult expectedGame = new GameResult(1, pairOf(TeamOne, TeamTwo), Score, false, now());

    //when
    sut.save(game);

    //then
    List<GameResult> gameResults = sut.findActiveGames();
    assertThat(gameResults).hasSize(1);
    assertThat(gameResults)
        .usingDefaultComparator()
        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("gameId", "started")
        .contains(expectedGame);
  }

  @Test
  @DisplayName("should update existing game with new score")
  void shouldUpdateExistingGameWithNewScore() {
    //given
    CompetitorsPair<Integer> newScore = pairOf(1, 0);
    GameResult game = new GameResult(1, pairOf(TeamOne, TeamTwo), Score, false, now(clock));
    sut.save(game);

    GameResult updatedGame = new GameResult(1, pairOf(TeamOne, TeamTwo), newScore, false, now(clock));
    //when
    sut.save(updatedGame);

    //then
    List<GameResult> gameResults = sut.findActiveGames();
    assertThat(gameResults).hasSize(1);
    assertThat(gameResults).contains(updatedGame);
  }

  @Test
  @DisplayName("should return optional when finding game that id does not exist")
  void shouldReturnOptionalWhenFindingGameThatIdDoesNotExist() {
    // given
    GameResult game = new GameResult(1000, pairOf(TeamOne, TeamTwo), Score, false, now(clock));
    sut.save(game);

    //when
    var result = sut.findById(1);

    //then
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("should return only active games")
  void shouldReturnOnlyActiveGames() {
    //given
    List.of(
            new GameResult(1, pairOf(TeamOne, TeamTwo), pairOf(1, 0), false, now(clock)),
            new GameResult(2, pairOf(TeamOne, TeamTwo), pairOf(0, 1), false, now(clock)),
            new GameResult(3, pairOf(TeamOne, TeamTwo), pairOf(0, 2), true, now(clock)),
            new GameResult(4, pairOf(TeamOne, TeamTwo), pairOf(2, 0), true, now(clock))
        )
        .forEach(sut::save);

    // when
    var results = sut.findActiveGames();

    // then
    assertThat(results).hasSize(2);
  }
}