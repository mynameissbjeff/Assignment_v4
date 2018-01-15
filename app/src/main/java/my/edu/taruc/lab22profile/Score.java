package my.edu.taruc.lab22profile;

/**
 * Created by Bailey on 1/9/2018.
 */

public class Score {
    private String score;

    public Score() {
    }

    public Score(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "score='" + score + '\'' +
                '}';
    }
}
