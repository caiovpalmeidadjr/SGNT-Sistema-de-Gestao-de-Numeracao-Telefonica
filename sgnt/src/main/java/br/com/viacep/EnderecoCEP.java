package br.com.viacep;

/**
 * Entidade baseada nos dados do WS do viacep.com
 */
public class EnderecoCEP {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }

    public String getUnidade() {
        return unidade;
    }

    public String getIbge() {
        return ibge;
    }

    public String getGia() {
        return gia;
    }

    public EnderecoCEP setCep(String cep) {
        this.cep = cep;
        return this;
    }

    public EnderecoCEP setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public EnderecoCEP setComplemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public EnderecoCEP setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public EnderecoCEP setLocalidade(String localidade) {
        this.localidade = localidade;
        return this;
    }

    public EnderecoCEP setUf(String uf) {
        this.uf = uf;
        return this;
    }

    public EnderecoCEP setUnidade(String unidade) {
        this.unidade = unidade;
        return this;
    }

    public EnderecoCEP setIbge(String ibge) {
        this.ibge = ibge;
        return this;
    }

    public EnderecoCEP setGia(String gia) {
        this.gia = gia;
        return this;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                ", unidade='" + unidade + '\'' +
                ", ibge='" + ibge + '\'' +
                ", gia='" + gia + '\'' +
                '}';
    }
}
