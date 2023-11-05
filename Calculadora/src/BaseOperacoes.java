public abstract class BaseOperacoes {
    private String sinal;

    public BaseOperacoes(String sinal) {
        this.sinal = sinal;
    }

    public String getSinal() {
        return sinal;
    }

    public abstract double executar(double x, double y);

    @Override
    public String toString(){
        return " " + sinal + " ";
    }
}
