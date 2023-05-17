package pl.code.house.task.score.board;

import java.util.concurrent.ConcurrentHashMap;

public class ScoreBoardFactory {

  public static ScoreBoardService createDefault() {
    return new ScoreBoardServiceImpl(new HashMapScoreBoardRepository(new ConcurrentHashMap<Integer, GameResult>()));
  }

  public static ScoreBoardService createService(ScoreBoardRepository repository) {
    return new ScoreBoardServiceImpl(repository);
  }

}
