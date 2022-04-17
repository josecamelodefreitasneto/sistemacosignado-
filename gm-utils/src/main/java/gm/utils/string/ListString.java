package gm.utils.string;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import gm.utils.abstrato.Lista;
import gm.utils.abstrato.SimpleIdObject;
import gm.utils.classes.UClass;
import gm.utils.comum.SO;
import gm.utils.comum.UAssert;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UHtml;
import gm.utils.comum.ULog;
import gm.utils.config.UConfig;
import gm.utils.exception.UException;
import gm.utils.files.UFile;
import gm.utils.lambda.FTT;
import gm.utils.lambda.FVoidTT;
import gm.utils.number.ListInteger;
import gm.utils.number.UInteger;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ListString extends Lista<String> {
	
	private CharSet charSet = CharSet.UTF8;
	private boolean printOnAdd = false;
	private boolean autoIdentacao = false;
	
	public ListString() {}
	
	private Margem margem = new Margem();
	
	public ListString(final Object... os) {
		for (final Object o : os) {
			this.add(UString.toString(o));
		}
	}
	
	public ListString(final String... list) {
		for (final String s : list) {
			this.add(s);
		}
	}
	public ListString(final List<String> list) {
		this.addAll(list);
	}
	
	private static final long serialVersionUID = 1;

	private String fileName;
	
	public static ListString newFromArray(final String... strings) {
		final ListString list = new ListString();
		for (final String s : strings) {
			list.add(s);
		}
		return list;
	}
	public static ListString array(final String... strings) {
		return ListString.newFromArray(strings);
	}
	public static ListString fromFile(final File file) {
		return ListString.fromFile(file.toString());
	}
	public static ListString fromFile(final String file) {
		final ListString list = new ListString();
		list.load(file);
		return list;
	}
	public static ListString fromFileUTF8(final String file) {
		final ListString list = new ListString();
		list.loadUTF8(file);
		return list;
	}
	public static ListString fromFileISO88591(final String file) {
		final ListString list = new ListString();
		list.loadISO88591(file);
		return list;
	}
	public static ListString newListString(final ListString... lists) {
		final ListString list = new ListString();
		for (final ListString l : lists) {
			list.add(l);
		}
		return list;
	}
	public static ListString newListString(final Enumeration<String> list) {
		final ListString result = new ListString();
		while (list.hasMoreElements()) {
			result.add(list.nextElement());
		}
		return result;
	}
	public ListString addArray(final String... list) {
		for (final String string : list) {
			this.add(string);
		}
		return this;
	}

	private FTT<String, String> filterAdd;

	@Override
	public boolean add(String s) {
		
		if (s != null && s.contains("\n") && !s.contentEquals("\n")) {
			final ListString split = ListString.split(s, "\n");
			for (final String ss : split) {
				this.add(ss);
			}
			return true;
		}

		if (this.filterAdd != null) {
			s = this.filterAdd.call(s);
			if (s == null) {
				return false;
			}
		}

		if (this.autoIdentacao) {
			return this.addComAutoIdentacao(s);
		} else {
			return this.addSemAutoIdentacao(s);
		}
		
	}

	private boolean addComAutoIdentacao(String s) {

		s = UString.trimPlus(s);
		
		while (s.endsWith(";;")) {
			s = UString.ignoreRigth(s);
		}
		
		if (s.startsWith("}") || s.startsWith(")")) {
			while (UString.isEmpty(this.getLast())) {
				this.removeLast();
			}
			String last = this.getLast();
			if (last.endsWith(",")) {
				this.removeLast();
				last = UString.ignoreRight(last, 1).trim();
				this.addSemAutoIdentacao(last);
			}
			this.getMargem().dec();
		}
		final boolean result = this.addSemAutoIdentacao(s);
		if (s.contains("//")) {
			s = UString.beforeFirst(s, "//");
		}
		if (s.endsWith("{") || s.endsWith("(")) {
			this.getMargem().inc();
//		} else if (s.contains("(") && !UString.afterLast(s, "(").contains(")")) {
//			this.getMargem().inc();
		}
		return result;
	}
	
	private boolean addSemAutoIdentacao(String s) {

		s = this.margem + s;
		if (!this.isAceitaRepetidos()) {
			if (this.contains(s)) {
				return false;
			}
		}
		if (this.printOnAdd) {
			System.out.println(s);
		}
		
		return super.add(s);

	}
	
	@Override
	public void add(final int index, final String element) {
		super.add(index, this.margem + element);
	}
	public boolean add(final String... list) {
		boolean b = false;
		for (final String string : list) {
			b = this.add(string) || b;
		}
		return b;
	}
	public ListString loadUTF8() {
		this.loadUTF8("c:\\temp\\x.txt");
		return this;
	}
	public ListString loadISO88591() {
		this.loadISO88591("c:\\temp\\x.txt");
		return this;
	}
	public ListString load() {
		this.load("c:\\temp\\x.txt");
		return this;
	}
	public ListString loadUTF8(final String file) {
		this.load(file, CharSet.UTF8);
		return this;
	}
	public ListString loadISO88591(final String file) {
		this.load(file, CharSet.ISO88591);
		return this;
	}
	public ListString loadISO88591(final File file) {
		return this.loadISO88591(file.toString());
	}
	public ListString load(final String file) {
		this.load(file, this.getCharSet());
		return this;
	}
	public ListString load(final File file) {
		return this.load(file.toString());
	}
	public ListString load(final File file, final CharSet charSet) {
		return this.load(file.toString(), charSet);
	}
	
	public ListString load(final String file, final CharSet charSet, FTT<String, String> filterAdd) {
		if (this.filterAdd != null) {
			throw new RuntimeException("!impementado");
		}
		this.filterAdd = filterAdd;
		try {
			return this.load(file.toString(), charSet);	
		} finally {
			this.filterAdd = null;
		}
	}
	
	public ListString load(final String file, final CharSet charSet) {
		
		this.fileName = file;
		
		try {
			if (file.startsWith("smb://")) {
				String domain = System.getProperty("smb-domain");
				String username = System.getProperty("smb-username");
				String password = System.getProperty("smb-password");
//@gm-utils     final NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("cooperforte", "gamarra", "@m03386551157");
				final NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, username, password);
				final SmbFile smbFile = new SmbFile(file, auth);
				return this.load(smbFile, charSet);
			} else {
				return this.load(new FileInputStream(file), charSet);
			}
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
	}
	
	public ListString load(final SmbFile file) {
		return this.load(file, CharSet.UTF8);
	}
	
	public ListString load(final SmbFile file, final CharSet charSet) {
		try {
			return this.load(file.getInputStream(), charSet);
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
	}
	
//	String detectCharSet(InputStream is){
//		
//		try {
//			byte[] buf = new byte[4096];
//			UniversalDetector detector = new UniversalDetector(null);
//			int nread;
//			while ((nread = is.read(buf)) > 0 && !detector.isDone()) {
//			  detector.handleData(buf, 0, nread);
//			}
//			detector.dataEnd();
//			String encoding = detector.getDetectedCharset();
//			detector.reset();
//			return encoding;
//			
//		} catch (Exception e) {
//			U.printStackTrace(e);
//			return null;
//		}
//	}
	
	public ListString load(final InputStream is, final CharSet charSet) {
		
		try {
			
			UAssert.notEmpty(is, "is == null");
			
			this.setCharSet(charSet);
			
			InputStreamReader in;
			
			if ( charSet == null ) {
				in = new InputStreamReader(is);
			} else {
				in = new InputStreamReader(is, charSet.getNome());
			}
			
			UAssert.notEmpty(in, "in == null");
			
			final BufferedReader buffer = new BufferedReader(in);
			
			String linha = buffer.readLine();
			while (linha != null) {
				this.add(linha);
				linha = buffer.readLine();
			}
			buffer.close();
			return this;
		} catch (final Exception e) {
			
			if (e instanceof java.io.UnsupportedEncodingException) {
				
				if (charSet == null) {
					this.load(is, CharSet.UTF8);
				} else if (charSet == CharSet.UTF8) {
					this.load(is, CharSet.ISO88591);
				}
				return this;
				
			}
			
			throw UException.runtime(e);
			
		}
	}
	public boolean has(){
		return !this.isEmpty();
	}
	public ListString print() {
		if (this.isEmpty()) {
			return this;
		}
		ULog.debug(this.toString("\n"));
		return this;
	}
	public ListString sort_considerando_case() {
		this.sort((a, b) -> {
			a = UString.removerAcentos(a);
			b = UString.removerAcentos(b);
			return a.compareTo(b);
		});
		return this;
	}
	public void inverteOrdem() {
		final ListString list = this.copy();
		this.clear();
		for (final String s : list) {
			this.add(0, s);
		}
	}
	public ListString sort() {
		this.sort((a, b) -> {
			a = UString.removerAcentos(a).toLowerCase();
			b = UString.removerAcentos(b).toLowerCase();
			return a.compareTo(b);
		});
		return this;
	}
	public ListString mergeBefore(final String s) {
		final ListString x = new ListString();
		for (final String string : this) {
			x.add(s + string);
		}
		this.clear();
		this.addAll(x);
		return this;
	}
	public ListString mergeAfter(final String s) {
		final ListString x = new ListString();
		for (final String string : this) {
			x.add(string + s);
		}
		this.clear();
		this.addAll(x);
		return this;
	}
	public ListString add() {
		this.add("");
		return this;
	}
	public ListString trataCaracteresEspeciais() {
		return this;
	}
	public ListString replace(final int char1, final int char2, final String s) {
		final Character c1 = Character.toChars(195)[0];
		final Character c2 = Character.toChars(65533)[0];
		final String a = "" + c1 + c2;
		this.replaceTexto(a, s);
		return this;
	}
	public ListString replace(final String a, final Object b) {
		this.replace(a, b.toString());
		return this;
	}
	public ListString replace(final String a, final String b) {
		if (a.equals(b)) {
			return this;
		}
		while (this.contains(a)) {
			final int i = this.indexOf(a);
			this.remove(i);
			this.add(i, b);
		}
		return this;
	}
	public ListString replaceTexto(final String a, final String b) {
		final ListString x = new ListString();
		for (final String string : this) {
			if (UString.isEmpty(string)) {
				x.add();
				continue;
			}
			final String s = string.replace(a, b);
			if (UString.isEmpty(s)) {
				x.add();
				continue;
			}
			x.add(s);
		}
		this.clear();
		this.addAll(x);
		return this;
	}
	
	public boolean eq(final ListString list){
		if (super.equals(list)) {
			return true;
		}
		if (list.toString("").equals(this.toString(""))) {
			return true;
		}
		return false;
	}
	
	public ListString save(final File diretorio, final String fileName) {
		return this.save(diretorio.toString() + "/" + fileName);
	}
	public ListString save() {
		if (UString.isEmpty(this.fileName)) {
			throw UException.runtime("fileName == null");
		}
		return this.save(this.fileName);
	}
	public ListString save(final String fileName) {
		this.salvar(fileName);
		return this;
	}
	public boolean salvar(String fileName) {
		UConfig.checaStop();
		if (SO.windows()) {
			fileName = fileName.replace("\\", "/");
		}
		
		if (fileName.contains(" ")) {
			throw new RuntimeException("Nao coloque espacos em nomes de arquivos: " + fileName);
		}
		
		final String pasta = UString.beforeLast(fileName, "/");
		if (pasta != null) {
			new File(pasta).mkdirs();
		}
		
		final boolean exists = UFile.exists(fileName);
		
		if (exists) {
			final ListString list = new ListString();
			list.load(fileName);
			//se for igual nao precisa salvar de novo
			if (list.eq(this)) {
				return false;
			}
		}

		if ( fileName.contains("null.war") ) {
			throw UException.runtime("Gravando no lugar errado");
		}
		
		if (exists) {
			System.out.println("Gravando: " + fileName + " (replace)");
		} else {
			System.out.println("Gravando: " + fileName + " (new)");
		}
		
		try {			
			if ( this.getCharSet() == null ) {
				try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))) {
					for (final String s : this) {
						out.write(s + "\n");
					}
				}	
			} else {
				try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), this.getCharSet().getNome()))) {
					for (final String s : this) {
						out.write(s + "\n");
					}
				}
			}
			
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	public static ListString split(final String s, final String delimiter) {
		return ListString.byDelimiter(s, delimiter);
	}
	public static ListString byDelimiter(String s, final String... delimiters) {
		
		final ListString list = new ListString();
		if (UString.isEmpty(s)) {
			return list;
		}
		while (UString.contains(s, delimiters)) {
			for (final String delimiter : delimiters) {
				if (delimiter != null && !"".equals(delimiter)) {
					while (s.contains(delimiter)) {
						final String x = s.substring(0, s.indexOf(delimiter));
						s = s.substring(x.length() + delimiter.length(), s.length());
						list.add(x);
					}
				}
			}
		}
		if (!UString.isEmpty(s)) {
			list.add(s);
		}
		list.removeLastEmptys();
		return list;
	}
	public ListString add(final String before, final ListString list) {
		for (final String s : list) {
			this.add(before + s);
		}
		return this;
	}
	public ListString add(final ListString list) {
		this.addAll(list);
		return this;
	}
	public Integer getInt(final int index) {
		return UInteger.toInt(this.get(index));
	}

	@Override
	public ListString filter(final Predicate<String> predicate) {
		return new ListString(super.filter(predicate));
	}
	
	@Override
	public ListString removeAndGet(final Predicate<String> predicate) {
		return new ListString(super.removeAndGet(predicate));
	}
	
	public ListString removeIfTrimStartsWith(final String s) {
		final Predicate<String> p = x -> {
			if (x == null) {
				return s == null;
			}
			return x.trim().startsWith(s.trim());
		};
		this.removeIf(p);
		return this;
	}
	public ListString removeIfStartsWith(final String s) {
		final Predicate<String> p = x -> {
			if (x == null) {
				return false;
			}
			return x.startsWith(s);
		};
		this.removeIf(p);
		return this;
	}
	
	public ListString removeIfNotStartsWith(final ListString itens) {
		
		final Predicate<String> p = x -> {
			if (x == null) {
				return true;
			}
			for (final String s : itens) {
				if (x.startsWith(s)) {
					return false;
				}
			}
			return true;
		};
		this.removeIf(p);
		return this;
		
	}

	public ListString removeIfNotEndsWith(final ListString itens) {
		
		final Predicate<String> p = x -> {
			if (x == null) {
				return true;
			}
			for (final String s : itens) {
				if (x.endsWith(s)) {
					return false;
				}
			}
			return true;
		};
		this.removeIf(p);
		return this;
		
	}
	
	public ListString removeIfNotStartsWith(final String... list) {
		return this.removeIfNotStartsWith( ListString.array(list) );
	}
	public ListString removeIfNotEndsWith(final String... list) {
		return this.removeIfNotEndsWith( ListString.array(list) );
	}
	public ListString removeIfTrimEquals(final String s) {
		final Predicate<String> p = x -> {
			if (x == null) {
				return s == null;
			}
			return x.trim().equals(s);
		};
		this.removeIf(p);
		return this;
	}
	public ListString removeFisrtEmptys() {
		while (!this.isEmpty() && UString.isEmpty(this.get(0))) {
			this.remove(0);
		}
		return this;
	}
	public ListString removeLastEmptys() {
		while (!this.isEmpty() && UString.isEmpty(this.getLast())) {
			this.removeLast();
		}
		return this;
	}
	public ListString removeIfEquals(final String s) {
		final Predicate<String> p = x -> {
			if (x == null) {
				return s == null;
			}
			return x.equals(s);
		};
		this.removeIf(p);
		return this;
	}
	@Override
	public boolean contains(final Object o) {
		if (o instanceof String) {
			final String s = (String) o;
			return this.contains(s);
		} else {
			return this.contains( UString.toString(o) );
		}
	}
	
	public boolean containsAny(final String... strings) {
		for (final String s : strings) {
			if (this.contains(s)) {
				return true;
			}
		}
		return false;
	}
	
//	nao remover
	public boolean contains(final String arg0) {
		return super.contains(arg0);
	}
	public ListString removeWhites() {
		while (this.contains("")) {
			this.remove("");
		}
		return this;
	}
	public ListString addLeft(final String s) {
		final ListString list = new ListString();
		for (final String linha : this) {
			list.add(s + linha);
		}
		this.clear();
		this.addAll(list);
		return this;
	}
	public ListString addRight(final String s) {
		final ListString list = new ListString();
		for (final String linha : this) {
			list.add(linha + s);
		}
		this.clear();
		this.addAll(list);
		return this;
	}
	public ListString removeLeft(final int length) {
		final ListString list = new ListString();
		for (final String s : this) {
			if (s.length() <= length) {
				list.add();	
			} else {
				list.add(s.substring(length));
			}
		}
		this.clear();
		this.addAll(list);
		return this;
	}
	public ListString removeRight(final int length) {
		final ListString list = new ListString();
		for (final String s : this) {
			if (s.length() <= length) {
				list.add();
			} else {
				list.add(UString.ignoreRight(s, length));
			}
		}
		this.clear();
		this.addAll(list);
		return this;
	}
	
	public ListString removeLast(final int quantidade) {
		return (ListString) super.removeLast_(quantidade);
	}

	public ListString remove(final int index, final int quantidade) {
		return (ListString) super.remove_(index, quantidade);
	}
	public ListString add(final List<?> list) {
		for (final Object o : list) {
			this.add(o.toString());
		}
		return this;
	}
	public ListString trimPlus() {
		final Lista<String> lista = this.copy();
		this.clear();
		for (String s : lista) {
			s = UString.trimPlus(s);
			if (!UString.isEmpty(s)) {
				this.add(s);
			}
		}
		this.removeWhites();
		return this;
	}
	public ListString trim() {
		final Lista<String> lista = this.copy();
		this.clear();
		for (final String s : lista) {
			this.add(s.trim());
		}
		return this;
	}
	public ListString rtrim() {
		final Lista<String> lista = this.copy();
		this.clear();
		for (final String s : lista) {
			this.add(UString.rtrim(s));
		}
		return this;
	}
	public static ListString loadResource(final Class<?> classe) {
		return ListString.loadResourceByExtensao(classe, "txt");
	}
	public static ListString loadResourceByExtensao(final Class<?> classe, final String extensao) {
		return ListString.loadResource(classe, classe.getSimpleName() + "." + extensao);
	}
	public static ListString loadResource(final Class<?> classe, final String fileName) {
		return ListString.fromFile(ListString.s_resource(classe, fileName));
	}
	public static ListString loadResourceUTF8(final Class<?> classe, final String fileName) {
		return ListString.fromFileUTF8(ListString.s_resource(classe, fileName));
	}
	public static ListString loadResourceISO88591(final Class<?> classe, final String fileName) {
		return ListString.fromFileISO88591(ListString.s_resource(classe, fileName));
	}
	static String s_resource(final Class<?> classe, String fileName) {
		if (fileName.startsWith("+")) {
			fileName = fileName.replace("+", classe.getSimpleName());
		}
		final URL resource = classe.getResource(fileName);
		if (resource == null) {
			throw UException.runtime("resource == null : " + fileName);
		}
		String s = resource.toString();
		s = s.replace("file:/", "");
		s = s.replace("vfs:/", "");
		s = s.replace("/", "\\");
		return s;
	}
	public static ListString nova(final String[] list) {
		final ListString l = new ListString();
		l.addArray(list);
		return l;
	}
	public static ListString separaPalavras(String s) {
		
		final ListString simbolos = UConstantes.SIMBOLOS.copy();
		simbolos.remove("_");
		simbolos.remove("$");
		simbolos.remove("@");
		
		final ListString list = new ListString();
		String x = "";
		while (!s.isEmpty()) {
			final String caracter = s.substring(0, 1);
			s = s.substring(1);
			if (simbolos.contains(caracter)) {
				if (!"".equals(x)) {
					list.add(x);
				}
				list.add(caracter);
				x = "";
			} else {
				x += caracter;
			}
		}
		if (!"".equals(x)) {
			list.add(x);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListString copy() {
		return super.copy();
	}
	public ListString menos(final ListString list) {
		final ListString copy = this.copy();
		for (final String string : list) {
			copy.remove(string);
		}
		return copy;
	}
	public static ListString loadClass(final Class<?> classe) {
		final String s = UClass.javaFileName(classe);
		if (s == null) {
			throw UException.runtime("ListString - JavaFile nao encontrado: " + classe.getSimpleName());
		}
		final File file = new File(s);
		return new ListString().load(file);
	}
	public ListString juntarComASuperiorSe(FTT<Boolean, String> predicate, String separador) {
		return juntarComASuperiorSe(predicate, separador, "");
	}
	public ListString juntarComASuperiorSe(FTT<Boolean, String> predicate, String separador, String after) {
		final ListString list = new ListString();
		for (String linha : this) {
			if (!list.isEmpty() && predicate.call(linha)) {
				linha = list.removeLast() + separador + linha.trim() + after;
			}
			list.add(linha);
		}
		this.clear();
		this.add(list);
		return this;
	}
	public ListString juntarComASuperiorSeEquals(String s, String separador, String after) {
		return juntarComASuperiorSe(linha -> UString.equals(s, linha), separador, after);
	}
	public ListString juntarComASuperiorSeEquals(String s, String separador) {
		return juntarComASuperiorSe(linha -> UString.equals(s, linha), separador);
	}
	public ListString juntarComASuperiorSeEquals(String s) {
		return juntarComASuperiorSe(linha -> UString.equals(s, linha), "");
	}
	public ListString juntarComASuperiorSeTrimStartarCom(final String prefix) {
		return this.juntarComASuperiorSeTrimStartarCom(prefix, "");
	}
	public ListString juntarComASuperiorSeTrimStartarCom(final String prefix, final String separador) {
		return juntarComASuperiorSe(s -> s.trim().startsWith(prefix), separador);
	}
	
	public ListString juntarComAProximaSeTrimTerminarCom(final String prefix) {
		return this.juntarComAProximaSeTrimTerminarCom(prefix, "");
	}
	public ListString juntarComAProximaSe(FTT<Boolean, String> predicate, String separador, String after) {
		final ListString list = new ListString();
		boolean juntar = false;
		for (String linha : this) {
			if (juntar) {
				linha = UString.rtrim(list.removeLast()) + separador + linha.trim() + after;
				juntar = false;
			} else if (predicate.call(linha)) {
				juntar = true;
			}
			list.add(linha);
		}
		this.clear();
		this.add(list);
		return this;		
	}
	public ListString juntarComAProximaSe(FTT<Boolean, String> predicate, String separador) {
		return juntarComAProximaSe(predicate, separador, "");
	}
	public ListString juntarComAProximaSeEquals(String s, String separador) {
		return juntarComAProximaSe(linha -> UString.equals(s, linha), separador);
	}
	public ListString juntarComAProximaSeEquals(String s) {
		return juntarComAProximaSe(linha -> UString.equals(s, linha), "");
	}
	public ListString juntarComAProximaSeTrimTerminarCom(final String prefix, final String separador) {
		return juntarComAProximaSe(s -> s.trim().endsWith(prefix), separador);
	}
	public ListString add(final Throwable e) {
		this.add(e.getClass().getName());
		final StackTraceElement[] stack = e.getStackTrace();
		for (final StackTraceElement o : stack) {
			this.add(o.toString());
		}
		return this;
	}
	public ListString loadResource(final String fileName) {
		return this.loadResource(fileName, this.getCharSet());
	}
	public ListString loadResource(final String fileName, final CharSet charSet) {
		
		final ClassLoader classLoader = this.getClass().getClassLoader();
		final InputStream is = classLoader.getResourceAsStream(fileName);
		
		if (is == null) {
			throw UException.runtime("is is null: " + fileName);
		}
		
		this.load(is, charSet);
//		File file = new File(classLoader.getResource(fileName).getFile());
//		load(file, charSet);
		
//		ServletContext x = ServletActionContext.getServletContext();
//		String realPath = x.getRealPath("");
//		String realFileName = realPath + "/" + fileName;
//		load(realFileName);
		return this;
	}
	public ListString toStringSqlDeclaration() {
		final ListString list = new ListString();
		list.add("static final String sql = \"\"");
		
		for (String s : this) {
			
			if (UString.isEmpty(s)) {
				list.add();
				continue;
			}
			s = UString.rtrim(s);
			if (s.contains("--")) {
				s = s.replace("--", "/*");
				s += "*/";
			}
			
			s = s.replace("\t", "  ");
			
			s = "\t+ \" " + s;
			s += "\"";
			
			list.add(s);
		}
		
		list.add(";");
		return list;
	}
	public static ListString clipboard(){
		final ListString list = new ListString();
		list.add(UString.clipboard().split("\n"));
		return list;
	}
	public void toClipboard(){
		UString.clipboard(this.toString("\n"));
	}
	public void save(final Class<?> classe){
		this.save(UClass.javaFileName(classe));
	}
	public static ListString loadClassText(final Class<?> classe){
  		return new ListString().load( UClass.javaFileName(classe) );
	}
	public ListString removeIfStartsWithAndEndsWith(final String a, final String b) {

		final Predicate<String> p = x -> x.startsWith(a) && x.endsWith(b);
		this.removeIf(p);
		return this;
		
	}
	public ListString removeIfNotContains(final String s) {

		final Predicate<String> p = x -> {
			if (x == null) {
				return s == null;
			}
			return !x.contains(s);
		};
		this.removeIf(p);
		return this;
		
	}
	public ListString removeIfContains(final String s) {
		final String s2 = UHtml.replaceSpecialChars(s);
		final Predicate<String> p = x -> {
			if (x == null) {
				return s == null;
			}
			return x.contains(s) || x.contains(s2);
		};
		return this.remove(p);
	}
	
	private ListString remove(final Predicate<String> predicate) {
		final ListString list = new ListString();
		for (final String s : this) {
			if ( predicate.test(s) ) {
				list.add(s);
			}
		}
		for (final String s : list) {
			this.remove(s);
		}
		return list;
	}
	
	public void save(final File file) {
		this.save(file.toString());
	}

	private String lastRemoved;
	
	@Override
	public String remove(final int index) {
		this.lastRemoved = super.remove(index);
		return this.lastRemoved;
	}
	
	static void testeRemoveTextEntre(){
	
		final ListString list = new ListString();
		list.add("teste a");
		list.add("teste b");
		list.add("teste c");
		list.add("<nome>na mesma linha abc");
		list.add("linha abc</nome>");
		list.add("teste x");
		list.add("teste y");
		list.add("teste z");
		
		final ListString result = list.removeTextEntre("<nome>", "</nome>");
		list.print();
		ULog.debug("=======");
		result.print();
		
	}
	
	public ListString removeTextEntre(final String inicio, final String fim) {
		
		final ListString result = new ListString();
		final ListString l = new ListString();
		
		int inicios = 0;
		
		while ( this.has() ) {
			
			String s = this.remove(0);
			
			if (s.contains(inicio)) {
				final String x = UString.beforeFirst(s, inicio);
				s = UString.afterFirst(s, inicio);
				if (!UString.isEmpty(x)) {
					l.add(x);
				}
				inicios++;
				if (UString.isEmpty(s)) {
					continue;
				}
			}
			
			if ( inicios == 0 ) {
				l.add(s);
				continue;
			}
			
			if (!s.contains(fim)) {
				result.add(s);
				continue;
			}

			final String x = UString.afterFirst(s, fim);
			s = UString.beforeFirst(s, fim);
			if (!UString.isEmpty(x)) {
				l.add(x);
			}
			
			if (!UString.isEmpty(s) && inicios > 0) {
				result.add(s);
			}
			
			inicios--;
			
			if (inicios == 0) {
				l.addAll( this );
				this.clear();
				break;
			}
			
		}
		
		this.addAll(l);
		return result;
		
	}

	private static ListInteger invalids = new ListInteger(65533);
	
	public static ListString load_e_escolhe_qual_por_encoding(final File file){
		return ListString.load_e_escolhe_qual_encoding(file.toString());
	}
	public static ListString load_e_escolhe_qual_encoding(final String file){

		final ListString a = new ListString();
		a.loadUTF8(file);
		
		final ListString b = new ListString();
		b.loadISO88591(file);
		
		for (String s : a) {
			while (!s.isEmpty()) {
				final int c = s.charAt(0);
				if (ListString.invalids.contains(c)) {
//					ULog.debug("iso");
					return b;
				}
//				ULog.debug(c);
				s = s.substring(1);
			}
		}

//		ULog.debug("utf");
		return a;
		
	}
	
	public ListString eachRemoveBefore(final String string, final boolean paramTo) {
		
		final ListString list = new ListString();
		
		for (String s : this) {
			
			if (s.contains(string)) {
				s = UString.afterFirst(s, string);
				if (!paramTo) {
					s = string + s;
				}
			}
			
			list.add(s);
			
		}
		
		this.clear();
		this.addAll(list);
		return this;
		
	}
	public ListString eachRemoveAfter(final String string, final boolean paramTo) {
		
		final ListString list = new ListString();
		
		for (String s : this) {
			
			if (s.contains(string)) {
				s = UString.beforeFirst(s, string);
				if (!paramTo) {
					s += string;
				}
			}
			
			list.add(s);
			
		}
		
		this.clear();
		this.addAll(list);
		return this;
		
	}
	public boolean size(final int i) {
		return this.size() == i;
	}
	
	
	public void addObject(final Object o) {
		this.add(o.toString());
	}
	
	public InputStream getFileStream() {
		
		return null;
	}
	public String toSQL() {
		this.replaceTexto("'", "''");
		return this.toString("");
	}
	public static <T> List<T> loadResource(final Class<?> from, final String file, final String delimiter, final Class<T> classe){
		final ListString list = ListString.loadResource(from, file);
		return ListString.load(list, delimiter, classe);
	}
	public static <T> List<T> load(final String file, final String delimiter, final Class<T> classe){
		ListString list = new ListString();
		list = list.load(file);
		return ListString.load(list, delimiter, classe);
	}
	public static <T> List<T> load(final ListString list, final String delimiter, final Class<T> classe){
		
		final List<T> result = new ArrayList<>();
		
		final Atributos as = ListAtributos.get(classe);
		
		for( final String s : list ) {
			final T o = UClass.newInstance(classe);
			result.add(o);
			
			final ListString values = ListString.byDelimiter(s, delimiter);
			
			for (final Atributo a : as) {
				a.set(o, values.remove(0));
			}
			
		}
		
		return result;
		
	}
	private static String getTempFile() {
		if (SO.windows()) {
			return "c:/temp/x.txt";
		} else {
			return "/tmp/x.txt";
		}
	}
	private static String getTempFile(final int index) {
		if (SO.windows()) {
			return "c:/temp/x"+index+".txt";
		} else {
			return "/tmp/x"+index+".txt";
		}
		
	}
	public void loadTemp() {
		this.load( ListString.getTempFile() );
	}
	public void saveTemp() {
		this.save( ListString.getTempFile() );
	}
	public void saveTemp(final int index) {
		this.save( ListString.getTempFile(index) );
	}
	public ListString load(final Map<?,?> map){
		for (final Object key : map.keySet()) {
			this.add( UString.toString(key) + " = " + UString.toString( map.get(key) ) );
		}
		return this;
	}
	public void quebraPor(final String delimiter, final boolean antes) {
		ListString list = this.copy();
		this.clear();
		for (String s : list) {
			while (s.contains(delimiter)) {
				String x = UString.beforeFirst(s, delimiter);
				if (antes) {
					x += delimiter;
					this.add(x);
				} else {
					this.add(x);
					this.add(delimiter);
				}
				s = UString.afterFirst(s, delimiter).trim();
			}
			if (!UString.isEmpty(s)) {
				this.add(s);
			}
		}
		
		if (!antes) {
			list = this.copy();
			this.clear();
			String s = "";
			while (!list.isEmpty()) {
				s += list.remove(0);
				if (s.equals(delimiter)) {
					continue;
				}
				this.add(s);
				s = "";
			}
		}
	}
	public void removeHtml() {
		final ListString list = this.copy();
		this.clear();
		for( String s : list ) {
			s = UHtml.removeAtributos(s);
			s = UHtml.removeHtml(s);
			this.add(s);
		}
	}
	public void toHtml(){
		final ListString list = this.copy();
		this.clear();
		for( String s : list ) {
			s = UHtml.replaceSpecialChars(s);
			this.add(s);
		}
	}
	public List<SimpleIdObject> putIds() {
		final List<SimpleIdObject> list = new ArrayList<>();
		int i = 1;
		for (final String s : this) {
			list.add( new SimpleIdObject(i++, s) );
		}
		return list;
	}
	public ListString primeirasMaiusculas() {
		final ListString x = new ListString();
		for (String s : this) {
			if (s == null) {
				continue;
			}
			s = UString.primeiraMaiuscula(s);
			x.add(s);
		}
		this.clear();
		this.addAll(x);
		return this;
	}
	public String join(final String separador) {
		return this.toString(separador);
	}
	
	public int indexOfWithTrim(final String s) {
		return this.copy().trim().indexOf(s.trim());
	}
	public int indexOfWithTrim(final ListString list) {
		
		for (int i = 0; i < this.size()-list.size()+1; i++) {
			boolean igual = true;
			for (int j = 0; j < list.size(); j++) {
				final String a = UString.trimPlus(this.get(i+j));
				String b = UString.trimPlus(list.get(j));
				if (UString.equals(a, b)) {
					continue;
				}
				if (b.endsWith("(*?)")) {
					b = b.substring(0, b.length()-4);
					if (a.startsWith(b)) {
						continue;	
					}
				}
				igual = false;
				break;
			}
			if (igual) {
				return i;
			}
		} 
		
		return -1;
	}
	public void add(int index, final ListString list){
		for(final String s : list){
			this.add(index, s);
			index++;
		}
	}
	public int replace(final ListString velho, final String novo) {
		final ListString list = new ListString();
		list.add(novo);
		return this.replace(velho, list);
	}
	private boolean replacePrivate(final ListString velho, FTT<ListString, ListString> func) {
		final int index = this.indexOfWithTrim(velho);
		if (index == -1) {
			return false;
		}
		int margens = 0;
		String s = this.get(index);
		while (s.startsWith("\t")) {
			s = s.substring(1);
			this.margemInc();
			margens++;
		}
		this.remove(velho);
		final int m = this.margem.get();
		this.margem.set(margens);
		ListString novo = func.call(velho);
		this.add(index, novo);
		this.margem.set(m);
		return true;		
	}
	public int replace(final ListString velho, final ListString novo) {
		return replace(velho, v -> novo);
	}
	public int replace(final ListString velho, FTT<ListString, ListString> func) {
		int result = 0;
		while (this.replacePrivate(velho, func)) {
			result++;
		}
		return result;
	}
	public int remove(final String... itens) {
		return this.remove(ListString.array(itens));
	}
	public int remove(final ListString list) {
		final int index = this.indexOfWithTrim(list);
		if (index == -1) {
			return -1;
		}
		for (int i = 0; i < list.size(); i++) {
			this.remove(index);
		}
		return index;
	}
	public int removeDoubleWhites(){
		final ListString novo = new ListString();
		novo.add();
		final ListString velho = new ListString();
		velho.add();
		velho.add();
		return this.replace(velho, novo);
	}
	@Override
	public ListString addIfNotContains(final String s) {
		if (s != null) {
			super.addIfNotContains(s);
		}
		return this;
	}
	public static ListString newListString(final Set<String> keySet) {
		final ListString list = new ListString();
		for (final String string : keySet) {
			list.add(string);
		}
		return list;
	}

	public void removeEmptys() {
		while (this.remove((String)null)) {
			
		}
		this.removeIfTrimEquals("");
	}

	public boolean moveToFirst(final String s, final boolean addIfNotContains) {
		final boolean remove = this.remove(s);
		if (remove || addIfNotContains) {
			this.add(0, s);	
		}
		return remove;
	}
	public boolean moveToLast(final String s, final boolean addIfNotContains) {
		final boolean remove = this.remove(s);
		if (remove || addIfNotContains) {
			this.add(s);	
		}
		return remove;
	}
	public List<Map<String, Object>> asMap() {
		final List<Map<String, Object>> maps = new ArrayList<>();
		int id = 1;
		for (final String s : this) {
			final HashMap<String, Object> map = new HashMap<>();
			map.put("id", id++);
			map.put("text", s);
			maps.add(map);
		}
		return maps;
	}

	public void removeRepetidos() {
		final ListString copy = this.copy();
		this.clear();
		for (final String s : copy) {
			this.addIfNotContains(s);
		}
	}

	public boolean containsIgnoreCase(final String valor) {
		for (final String s : this) {
			if ( s.equalsIgnoreCase(valor) ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String[] toArray() {
		final String[] list = new String[this.size()];
		for (int i = 0; i < this.size(); i++) {
			list[i] = this.get(i);
		}
		return list;
	}

	public void identarJava() {
		final ListString list = this.copy().trim();
		this.clear();
		this.margem.set(0);
		for (final String s : list) {
			if (s.startsWith("}")) {
				this.margem.dec();
			}
			this.add(s);
			if (s.endsWith("{")) {
				this.margem.inc();
			}
		}
	}

	public ListString union(final ListString list) {
		return this.copy().add(list);
	}

	public static ListString convert(final List<?> list) {
		final ListString listString = new ListString();
		for (final Object o : list) {
			final String s = UString.toString(o);
			if (s != null) {
				listString.add(s);
			}
		}
		return listString; 
	}
	
	public static ListString byQuebra(String s) {
		s = s.replace("\r", "\n");
		return ListString.byDelimiter(s, "\n");
	}

	public static void main(final String[] args) {
		final ListString list = new ListString();
		list.load("/opt/desen/git/notec/associacao-digital-2019/src/app/misc/utils/StringBox.js");
		list.juntarFimComComecos("{", "}", "");
		list.print();
	}
	
	public void juntarFimComComecos(final String fim, final String comeco, final String separador) {
		
		this.removeFisrtEmptys();
		this.removeLastEmptys();
		
		if (this.isEmpty()) {
			return;
		}
		
		final ListString list2 = new ListString();
		
		boolean lastEndsWithFim = false;
		
		while (!this.isEmpty()) {
			
			String s = UString.rtrim(this.remove(0));
			
			if (UString.isEmpty(s)) {
				list2.add();
				continue;
			}
			
			if (!lastEndsWithFim) {
				list2.add(s);
				lastEndsWithFim = s.endsWith(fim);
				continue;
			}
			
			if (!s.trim().startsWith(comeco)) {
				list2.add(s);
				lastEndsWithFim = false;
				continue;
			}
			
			list2.removeLastEmptys();
			s = list2.removeLast() + separador + s.trim();
			list2.add(s);
			
			lastEndsWithFim = s.endsWith(fim);
			
		}
		
		this.add(list2);
		
	}

	public boolean first(final String s, final String... strings) {
		if (this.isEmpty()) {
			return false;
		} else {
			return UString.in(this.get(0), s, strings);
		}
	}
	
	public boolean endsWith(final String s) {
		if (this.isEmpty()) {
			return false;
		} else {
			return this.getLast().equals(s);
		}
	}
	
	public boolean endsWith(final String s, final String... strings) {
		if (this.isEmpty()) {
			return false;
		} else {
			return UString.in(this.getLast(), s, strings);
		}
	}

	public static ListString loadFile(final File file) {
		UFile.assertExists(file);
		final ListString list = new ListString();
		list.load(file);
		return list;
	}
	
	public void replaceEach(final FTT<String, String> func) {
		final ListString list = this.copy();
		this.clear();
		for (String s : list) {
			s = func.call(s);
			this.add(s);
		}
	}
	
	public void forEach(final FVoidTT<String, Integer> action) {
		for (int i = 0; i < this.size(); i++) {
			action.call(this.get(i), i);
		}
	}
	
	public int margemInc() {
		return this.getMargem().inc();
	}
	public int margemDec() {
		return this.getMargem().dec();
	}
	public ListString rm(final String s) {
		this.remove(s);
		return this;
	}

	public String getFirst() {
		if (this.isEmpty()) {
			return null;
		} else {
			return this.get(0);
		}
	}

	public ListString mapString(FTT<String, String> func) {
		ListString list = new ListString();
		for (String s : this) {
			list.add(func.call(s));
		}
		return list;
	}

	public <T> List<T> map(FTT<T, String> func) {
		List<T> list = new ArrayList<>();
		for (String s : this) {
			list.add(func.call(s));
		}
		return list;
	}
	
}
