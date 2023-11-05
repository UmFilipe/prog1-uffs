public class Subtracao extends BaseOperacoes {
    public Subtracao(){
        super("-");
    }

    @Override
    public double executar(double x, double y) {
        return x - y;
    }
}
