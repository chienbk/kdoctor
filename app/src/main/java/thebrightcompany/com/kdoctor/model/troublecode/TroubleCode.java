package thebrightcompany.com.kdoctor.model.troublecode;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TroubleCode implements Serializable{

    @SerializedName("code")
    private String code;
    @SerializedName("title")
    private String title;
    @SerializedName("importance_level")
    private int importance_level;
    @SerializedName("difficulty_level")
    private int difficulty_level;
    @SerializedName("possible_cause")
    private String possible_cause;
    @SerializedName("tech_notes")
    private String tech_notes;
    @SerializedName("code_detected")
    private String code_detected;
    @SerializedName("possible_symptoms")
    private String possible_symptoms;
    @SerializedName("description")
    private String description;
    @SerializedName("summary_en")
    private String summary_en;
    @SerializedName("summary_vi")
    private String summary_vi;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImportance_level() {
        return importance_level;
    }

    public void setImportance_level(int importance_level) {
        this.importance_level = importance_level;
    }

    public int getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(int difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getPossible_cause() {
        return possible_cause;
    }

    public void setPossible_cause(String possible_cause) {
        this.possible_cause = possible_cause;
    }

    public String getTech_notes() {
        return tech_notes;
    }

    public void setTech_notes(String tech_notes) {
        this.tech_notes = tech_notes;
    }

    public String getCode_detected() {
        return code_detected;
    }

    public void setCode_detected(String code_detected) {
        this.code_detected = code_detected;
    }

    public String getPossible_symptoms() {
        return possible_symptoms;
    }

    public void setPossible_symptoms(String possible_symptoms) {
        this.possible_symptoms = possible_symptoms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary_en() {
        return summary_en;
    }

    public void setSummary_en(String summary_en) {
        this.summary_en = summary_en;
    }

    public String getSummary_vi() {
        return summary_vi;
    }

    public void setSummary_vi(String summary_vi) {
        this.summary_vi = summary_vi;
    }
}
