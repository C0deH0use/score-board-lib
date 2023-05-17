package pl.code.house.task.score.board;

/**
 * Class representing data of the same type associated with both teams
 * @param homeTeam value for the homeTeam
 * @param awayTeam value for the awayTeam
 * @param <T> type of data that should be used
 */
public record CompetitorsPair<T>(T homeTeam, T awayTeam) {

}
