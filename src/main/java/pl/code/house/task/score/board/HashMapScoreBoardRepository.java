package pl.code.house.task.score.board;

import static java.util.function.Predicate.not;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class HashMapScoreBoardRepository implements ScoreBoardRepository {

  private final Map<Integer, GameResult> db;

  HashMapScoreBoardRepository(Map<Integer, GameResult> dbSource) {
    this.db = dbSource;
  }

  @Override
  public GameResult save(GameResult game) {
    return db.compute(game.gameId(), (k, v) -> game);
  }

  @Override
  public List<GameResult> findActiveGames() {
    return db.values()
        .stream()
        .filter(not(GameResult::finished))
        .sorted(GameResultComparator.Instance)
        .toList();
  }

  @Override
  public Optional<GameResult> findById(Integer gameId) {
    return Optional.ofNullable(db.get(gameId));
  }
}
