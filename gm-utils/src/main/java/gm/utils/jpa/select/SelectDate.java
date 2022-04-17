package gm.utils.jpa.select;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import gm.utils.comum.UConstantes;
import gm.utils.date.Data;
import gm.utils.exception.UException;

public class SelectDate<TS extends SelectBase<?,?,?>> extends SelectTypedLogical<TS, Data> {
	public SelectDate(TS x, String campo) {
		super(x, campo);
	}
	
	public TS eq(Calendar value) {
		return super.eq(Data.to(value));
	}
	public TS ne(Calendar value) {
		return super.ne(Data.to(value));
	}
	public TS maior(Calendar value) {
		return super.maior(Data.to(value));
	}
	public TS menor(Calendar value) {
		return super.menor(Data.to(value));
	}
	public TS maiorOuIgual(Calendar value) {
		return super.maiorOuIgual(Data.to(value));
	}
	public TS menorOuIgual(Calendar value) {
		return super.menorOuIgual(Data.to(value));
	}
	public TS entre(Calendar a, Calendar b) {
		return super.entre(Data.to(a), Data.to(b));
	}
	public TS naoEntre(Calendar a, Calendar b) {
		return super.naoEntre(Data.to(a), Data.to(b));
	}

	public TS eq(LocalDate value) {
		return super.eq(Data.to(value));
	}
	public TS ne(LocalDate value) {
		return super.ne(Data.to(value));
	}
	public TS maior(LocalDate value) {
		return super.maior(Data.to(value));
	}
	public TS menor(LocalDate value) {
		return super.menor(Data.to(value));
	}
	public TS maiorOuIgual(LocalDate value) {
		return super.maiorOuIgual(Data.to(value));
	}
	public TS menorOuIgual(LocalDate value) {
		return super.menorOuIgual(Data.to(value));
	}
	public TS entre(LocalDate a, LocalDate b) {
		return super.entre(Data.to(a), Data.to(b));
	}
	public TS naoEntre(LocalDate a, LocalDate b) {
		return super.naoEntre(Data.to(a), Data.to(b));
	}
	
	public TS eq(Date value) {
		return super.eq(Data.to(value));
	}
	public TS ne(Date value) {
		return super.ne(Data.to(value));
	}
	public TS maior(Date value) {
		return super.maior(Data.to(value));
	}
	public TS menor(Date value) {
		return super.menor(Data.to(value));
	}
	public TS maiorOuIgual(Date value) {
		return super.maiorOuIgual(Data.to(value));
	}
	public TS menorOuIgual(Date value) {
		return super.menorOuIgual(Data.to(value));
	}
	public TS entre(Date a, Date b) {
		return super.entre(Data.to(a), Data.to(b));
	}
	public TS naoEntre(Date a, Date b) {
		return super.naoEntre(Data.to(a), Data.to(b));
	}
	
	public TS isHoje() {
		c().isHoje(getCampo());
		return ts;
	}
	public TS isNotHoje() {
		c().isNotHoje(getCampo());
		return ts;
	}
	public TS menorQueAgora(){
		return menor(Data.now());
	}
	public TS maiorQueAgora(){
		return maior(Data.now());
	}
	public TS menorQueHoje(){
		return menor(Data.hoje());
	}
	public TS maiorQueHoje(){
		return maiorOuIgual(Data.amanha());
	}
	public Data max() {
		return Data.to( c().max(getCampo()) );
	}
	public Data min() {
		return Data.to( c().min(getCampo()) );
	}
	public TS op(Operador operador, Data inicio, Data fim) {
		if (operador == Operador.igual) return eq(inicio);
		if (operador == Operador.maiorOuIgual) return maiorOuIgual(inicio);
		if (operador == Operador.menorOuIgual) return menorOuIgual(inicio);
		if (operador == Operador.entre) return entre(inicio,fim);
		if (operador == Operador.diferente) return ne(inicio);
		if (operador == Operador.naoEntre) return naoEntre(inicio,fim);
		throw UException.runtime("Operador inv"+UConstantes.a_agudo+"lido: " + operador);
	}
}
