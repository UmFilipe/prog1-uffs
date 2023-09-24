public class Gerente extends Funcionario {
    private String usuario;
    private String senha;

    // Construtor com uso de herança
    public Gerente(String usuario, String senha, String nome, double salario) {
        super(nome, salario);
        this.usuario = usuario;
        this.senha = senha;
    }

    // Getters
    public String getUsuario() {
        return usuario;
    }

    public String getSenha(){
        return senha;
    }

    // Setters
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

     public void setSenha(String senha){
        this.senha = senha;
    }

    // Reescrita do método que calcula a bonificação, para aumenta-lo para 15% 
    @Override
    public double calculaBonificacao() {
        return getSalario() * 0.15; 
    }
}
