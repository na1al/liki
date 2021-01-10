package ua.catalog.loader.component.parser.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class MedicineDto {


    @CsvBindByName(column = "id", required = true)
    private int id;

    @CsvBindByName(column = "name", required = true)
    private String name;

    @CsvBindByName(column = "code_ua")
    private String codeUa;

    @CsvBindByName(column = "code_morion")
    private String codeMorion;

    @CsvBindByName(column = "code_optima")
    private String codeOptima;

    @CsvBindByName(column = "code_badm")
    private String codeBadm;

    @CsvBindByName(column = "image")
    private String image;

    @CsvBindByName(column = "description")
    private String description;

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setCodeUa(String codeUa) {
        this.codeUa = codeUa.trim();
    }

    public void setCodeMorion(String codeMorion) {
        this.codeMorion = codeMorion.trim();
    }

    public void setCodeOptima(String codeOptima) {
        this.codeOptima = codeOptima.trim();
    }

    public void setCodeBadm(String codeBadm) {
        this.codeBadm = codeBadm.trim();
    }

    public void setImage(String image) {
        this.image = image.trim();
    }
}
