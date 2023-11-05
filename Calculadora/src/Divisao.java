public class Divisao extends BaseOperacoes {
    public Divisao(){
        super("/");
    }

    @Override
    public double executar(double x, double y) {
        return x / y;
    }
}
