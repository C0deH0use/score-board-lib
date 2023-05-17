package pl.code.house.task.score.board;

import java.util.List;
import java.util.Optional;

public interface ScoreBoardRepository {

  GameResult save(GameResult game);

  List<GameResult> findActiveGames();

  Optional<GameResult> findById(Integer gameId);

}
