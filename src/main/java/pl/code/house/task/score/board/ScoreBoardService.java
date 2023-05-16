package pl.code.house.task.score.board;

import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public interface ScoreBoardService {

  List<GameResult> findActiveGames();

  GameResult createNewGame(Pair<String, String> teams);

  GameResult updateGameScore(Integer gameId, Pair<Integer, Integer> newScore);

  GameResult finishGame(Integer gameId);
}
