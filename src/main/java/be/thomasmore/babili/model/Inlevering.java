package be.thomasmore.babili.model;

public class Inlevering {

    private String audiopath;
    private String feedback;

    public Inlevering(String audiopath, String feedback) {
        this.audiopath = audiopath;
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Inlevering{" +
                "audiopath='" + audiopath + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }

    public String getAudiopath() {
        return audiopath;
    }

    public void setAudiopath(String audiopath) {
        this.audiopath = audiopath;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
