package pl.code.house.task.score.board;

import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

class ScoreBoardServiceImpl implements ScoreBoardService {

  private final ScoreBoardRepository repository;

  ScoreBoardServiceImpl(ScoreBoardRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<GameResult> findActiveGames() {
    return repository.findActiveGames();
  }

  @Override
  public GameResult createNewGame(Pair<String, String> teams) {
    return repository.save(GameResult.newGame(teams));
  }

  @Override
  public GameResult updateGameScore(Integer gameId, Pair<Integer, Integer> newScore) {
    return repository.findById(gameId)
        .map(game -> game.updateScore(newScore))
        .map(repository::save)
        .orElseThrow(() -> new IllegalArgumentException("Game with the following id %s does not exist".formatted(gameId)));
  }

  @Override
  public GameResult finishGame(Integer gameId) {
    var game = repository.findById(gameId)
        .orElseThrow(() -> new IllegalArgumentException("Game with the following id %s does not exist".formatted(gameId)));

    if(game.finished()) {
      throw  new IllegalStateException("Game is already finished");
    }
    return repository.save(game.markAsFinished());
  }
}
