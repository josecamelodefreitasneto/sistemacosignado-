package gm.utils.number;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import gm.utils.reflection.Atributo;
import gm.utils.reflection.ListAtributos;

public class CalculoLista {

	List<Numeric1> list = new ArrayList<>();
	boolean temVazios = false;

	public CalculoLista() {
		
	}
	
	public void add(Numeric1 n){
		list.add(n);
	}
	
	public void add(Double d){
		add( new Numeric1( UBigDecimal.toMoney( d ) ) );
	}
	public int size() {
		return list.size();
	}
	public CalculoLista(List<?> list, String campo) {
		if (list.isEmpty()){
			return;
		}
		
		Atributo a = ListAtributos.get(list.get(0).getClass()).get(campo);
		for (Object o : list) {
			
			o = a.get(o);
			
			if (o == null) {
				temVazios = true;
				this.list.add(new Numeric1());
				continue;
			}
			
			this.list.add( new Numeric1( UBigDecimal.toMoney(o) ) );
			
		}
	}
	public Numeric1 media(){
		return soma().dividido(list.size());
	}
	public Numeric1 soma(){
		Numeric1 soma = new Numeric1();
		for (Numeric1 o : list) {
			soma.add(o);
		}
		return soma;
	}
	public Numeric1 menor(){
		Numeric1 x = list.get(0);
		for (Numeric1 o : list) {
			if (o.menor(x)) {
				x = o;
			}
		}
		return x;
	}
	public Numeric1 maior(){
		Numeric1 x = list.get(0);
		for (Numeric1 o : list) {
			if (o.maior(x)) {
				x = o;
			}
		}
		return x;
	}
	public boolean temMenorQue(GetNumeric x){
		return temMenorQue(x.valor().getValor());
	}
	public boolean temMenorQue(Double d){
		return temMenorQue(new Numeric1(d));
	}
	public boolean temMenorQue(Integer d){
		return temMenorQue(new Numeric1(d));
	}
	public boolean temMenorQue(BigDecimal d){
		return temMenorQue(new Numeric1(d));
	}
	public boolean temMaiorQue(Double d){
		return temMaiorQue(new Numeric1(d));
	}
	public boolean temMenorQue(Numeric1 d){
		return menor().menor(d);
	}
	public boolean temMaiorQue(Numeric1 d){
		return maior().maior(d);
	}
	public boolean temVazios() {
		return temVazios;
	}
	public Numeric1 get(int i) {
		return list.get(i);
	}
	public Numeric1 last(){
		return get( size()-1 );  
	}
	public Numeric1 remove(Numeric1 o) {
		list.remove(o);
		return o;
	}
}
