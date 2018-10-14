package movil.tesis.miguel.opuestos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Opuestos {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("url_im1")
    @Expose
    private String urlIm1;
    @SerializedName("url_im2")
    @Expose
    private String urlIm2;
    @SerializedName("nombre_im1")
    @Expose
    private String nombreIm1;
    @SerializedName("nombre_im2")
    @Expose
    private String nombreIm2;
    @SerializedName("opuesto_im1")
    @Expose
    private String opuestoIm1;
    @SerializedName("opuesto_im2")
    @Expose
    private String opuestoIm2;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlIm1() {
        return urlIm1;
    }

    public void setUrlIm1(String urlIm1) {
        this.urlIm1 = urlIm1;
    }

    public String getUrlIm2() {
        return urlIm2;
    }

    public void setUrlIm2(String urlIm2) {
        this.urlIm2 = urlIm2;
    }

    public String getNombreIm1() {
        return nombreIm1;
    }

    public void setNombreIm1(String nombreIm1) {
        this.nombreIm1 = nombreIm1;
    }

    public String getNombreIm2() {
        return nombreIm2;
    }

    public void setNombreIm2(String nombreIm2) {
        this.nombreIm2 = nombreIm2;
    }

    public String getOpuestoIm1() {
        return opuestoIm1;
    }

    public void setOpuestoIm1(String opuestoIm1) {
        this.opuestoIm1 = opuestoIm1;
    }

    public String getOpuestoIm2() {
        return opuestoIm2;
    }

    public void setOpuestoIm2(String opuestoIm2) {
        this.opuestoIm2 = opuestoIm2;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
