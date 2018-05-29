package thebrightcompany.com.kdoctor.model.diagnostic;

/**
 * Created by ChienNv9 on 1/24/2018.
 */

public class Diagnostic {

    private String name;
    private String code;
    private String codeResult;
    private String value;
    private String unit;
    private String description;

    public Diagnostic() {
    }

    public Diagnostic(String name, String code, String codeResult, String value, String unit, String description) {
        this.name = name;
        this.code = code;
        this.codeResult = codeResult;
        this.value = value;
        this.unit = unit;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeResult() {
        return codeResult;
    }

    public void setCodeResult(String codeResult) {
        this.codeResult = codeResult;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
