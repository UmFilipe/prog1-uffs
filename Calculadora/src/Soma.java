public class Soma extends BaseOperacoes {
    public Soma(){
        super("+");
    }

    @Override
    public double executar(double x, double y) {
        return x + y;
    }
}
