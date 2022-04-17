package gm.utils.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.FileUtils;

import gm.utils.classes.UClass;
import gm.utils.comum.SO;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UList;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import gm.utils.string.UString;
public class UFile {
	
	public static void assertExists(final String s){
		UFile.assertExists( new File(s) );
	}
	
	public static void assertExists(final File file){
		if (!UFile.exists(file)) {
			throw UException.runtime("Arquivo n"+UConstantes.a_til+"o existe: " + file);
		}
	}
	
	public static boolean exists(final String s){
		return UFile.exists(new File(s));
	}
	public static boolean exists(final File file){
		return file.exists();
	}
	
	public static Data data(final Class<?> classe){
		return UFile.data(UClass.javaFileName(classe, true));
	}
	public static Data data(final String s){
		UFile.assertExists(s);
		return new Data( new File(s).lastModified() );
	}

//	public static StreamingOutput streamOutput(String fileName) {
//		File file = new File(fileName);		
//		return streamOutput(file);
//	}
//	
//	public static StreamingOutput streamOutput(File file) {
//
//		try {
//	        
//			StreamingOutput streamOutput = new StreamingOutput() {
//
//				@Override
//				public void write(OutputStream output) throws IOException, WebApplicationException {
//					BufferedOutputStream bus = new BufferedOutputStream(output);
//
//					try {
//		                FileInputStream fizip = new FileInputStream(file);
//		                byte[] buffer2 = IOUtils.toByteArray(fizip);
//		                bus.write(buffer2);
//		            } catch (Exception e) {
//		            	UException.printTrace(e);
//		            }					
//				}
//			};
//			
//			return streamOutput;
//			
//		} catch (Exception e) {
//			throw UException.runtime(e);
//		}
//        
//	}

	public static boolean delete(String fileName) {
		
		if (SO.windows()) {
			if (fileName.startsWith("/")) {
				fileName = fileName.substring(1);
			}
		}
		
		if (!UFile.exists(fileName)) {
			return false;
		}
		while ( UFile.exists(fileName) ) {
			try {
				final File file = new File(fileName);
				if (file.isDirectory()) {
					UFile.delete(file.listFiles());
				}
				final Path path = Paths.get(fileName);
				Files.deleteIfExists(path);
			} catch (final Exception e) {
				throw UException.runtime(e);
			}
		}
		
//		System.out.println("===============================");
//		USystem.sleepMiliSegundos(200);
//		UException.runtime("").printStackTrace();
		System.out.println("delete " + fileName);
//		USystem.sleepMiliSegundos(200);
//		System.out.println("===============================");
		return true;
		
	}
	
	public static void delete(final List<File> files) {
		for (final File file : files) {
			try {
				UFile.delete(file);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void delete(final File file) {
		UFile.delete( file.getAbsolutePath() );
		file.delete();
		System.out.println(file + " (delete)");
	}
	
	public static void save(final File file, final String fileName) throws IOException {
		UFile.save(new FileInputStream(file), fileName);
	}
	
	public static void save(final InputStream is, final String fileName) throws IOException {
		
		final String path = UString.beforeFirst(fileName, "/");
		
//		file.exists();
	    final File directory = new File(path);
	    if (!directory.exists()){
	        directory.mkdirs();
	    }
		final File destinationFile = new File(path, fileName);
//		destinationFile.getAbsolutePath();
		final FileOutputStream fos = new FileOutputStream(destinationFile, false);
		UFile.copyInputStreamToOutputStream(is, fos);
	}
	
	private static void copyInputStreamToOutputStream(InputStream src, OutputStream dest) throws IOException {
		if (!(dest instanceof BufferedOutputStream))
			dest = new BufferedOutputStream(dest);

		if (!(src instanceof BufferedInputStream))
			src = new BufferedInputStream(src);

		int countBytesRead = -1;
		final byte[] bufferCopy = new byte[2048];
		while ((countBytesRead = src.read(bufferCopy)) != -1)
			dest.write(bufferCopy, 0, countBytesRead);
		
		dest.flush();
		dest.close();
	}

	public static void copy(final File origem, final File destino) {
		
		try {

			if (origem.isDirectory()) {
				if (!destino.exists()) {
					destino.mkdir();
				}
				
				final String[] filhos = origem.list();
				for (final String filho : filhos) {
					UFile.copy(new File(origem,filho), new File(destino,filho));
				}
				
			} else {
				
				final boolean exists = destino.exists(); 
				
				if (exists) {
					if (origem.lastModified() == destino.lastModified() && origem.getTotalSpace() == destino.getTotalSpace()) {
						return;
					}
				} else {
					new ListString().save(destino);
				}
				
				try (InputStream in = new FileInputStream(origem); 
						OutputStream out = new FileOutputStream(destino)) {
				
					final byte[] buffer = new byte[1024];
					int len;
					while((len = in.read(buffer)) > 0){
						out.write(buffer, 0, len);
					}
				}
				
				destino.setLastModified(origem.lastModified());
				
				if (exists) {
					System.out.println("Gravando: " + destino + " (replace)");
				}
				
			}
			
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
		
	}
	
	public static void deleteFilesOfPath(final String path) {
		UFile.deleteFilesOfPath(new File(path));
	}
	public static void deleteFilesOfPath(final File path) {
		UFile.delete( path.listFiles() );
	}

	private static void delete(final File... files) {
		if (files == null || files.length == 0) {
			return;
		}
		final List<File> list = UList.asList(files);
		UFile.delete(list);
	}

	public static List<File> getFilesAndDirectories(final File file) {
		if (!file.exists()) {
			throw UException.runtime("!exists:" + file);
		}
		if (!file.isDirectory()) {
			throw UException.runtime("!isDirectory:" + file);
		}
		return UList.asList(file.listFiles());
	}
	private static List<File> getFilesOrDirectory(final File file, final boolean directory) {
		final List<File> list = UFile.getFilesAndDirectories(file);
		final List<File> result = new ArrayList<>();
		for (final File file2 : list) {
			if (file2.isDirectory() == directory) {
				result.add(file2);
			}
		}
		UFile.sort(result);
		return result;
	}
	private static void sort(final List<File> list) {
		list.sort((a, b) -> {
			if (a.isDirectory() == b.isDirectory()) {
				return UString.compare(a.getName(), b.getName());
			} else if (a.isDirectory()) {
				return 1;
			} else {
				return -1;
			}
		});		
	}

	public static List<File> getFiles(final String path, final String... extensoes) {
		final File file = new File(path);
		return UFile.getFiles(file, extensoes);
	}
	public static List<File> getFiles(final File path, final String... extensoes) {
		final List<File> files = UFile.getFiles(path);
		final List<File> list = new ArrayList<>();
		for (final String extensao : extensoes) {
			for (final File file : files) {
				if (file.getName().endsWith("." + extensao)) {
					list.add(file);
				}
			}
		}
		return list;
	}
	public static List<File> getFiles(final String path) {
		return UFile.getFiles(new File(path));
	}
	public static List<File> getFiles(final File file) {
		return UFile.getFilesOrDirectory(file, false);
	}
	public static List<File> getDirectories(final String fileName) {
		return UFile.getDirectories(new File(fileName));
	}
	public static List<File> getDirectories(final File file) {
		return UFile.getFilesOrDirectory(file, true);
	}

	public static void criaDiretorio(final String s) {
		new File(s).mkdirs();		
	}

	public static void copy(final String de, final String para) {
		if (UString.equals(de, para)) {
			throw new RuntimeException("de == para");		}
		UFile.copy(new File(de), new File(para));
	}

	private static void getAllFiles(final File path, final List<File> list) {
		list.addAll(UFile.getFiles(path));
		final List<File> directories = UFile.getDirectories(path);
		for (final File file : directories) {
			String s = file.toString();
			if (s.contains("/target")) continue;
			if (s.contains("/node_modules")) continue;
			UFile.getAllFiles(file, list);
		}
	}
	public static List<File> getAllFiles(final File path) {
		final List<File> list = new ArrayList<>();
		UFile.getAllFiles(path, list);
		return list;
	}
	
	
	private static void getAllDirectories(final File path, final List<File> list) {
		list.addAll(UFile.getDirectories(path));
		final List<File> directories = UFile.getDirectories(path);
		for (final File file : directories) {
			String s = file.toString();
			if (s.contains("/target")) continue;
			if (s.contains("/node_modules")) continue;
			UFile.getAllDirectories(file, list);
		}
	}
	public static List<File> getAllDirectories(final File path) {
		final List<File> list = new ArrayList<>();
		UFile.getAllDirectories(path, list);
		return list;
	}
	public static List<File> getAllDirectories(final String path) {
		return UFile.getAllDirectories(new File(path));
	}
	
	public static List<File> getAllFiles(final String path) {
		return UFile.getAllFiles(new File(path));
	}

	public static List<File> getJavas(final String path) {
		return UList.filter(UFile.getAllFiles(path), o -> o.toString().endsWith(".java"));
	}

	public static List<File> findFilesByName(final String path, final String name) {
		final List<File> files = UFile.getAllFiles(path);
		final List<File> list = new ArrayList<>();
		for (final File file : files) {
			if (file.getName().equals(name)) {
				list.add(file);
			}
		}
		return list;
	}

	public static byte[] getBytes(final File file) {
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static StreamingOutput streamOutput(final String fileName) {
		final File file = new File(fileName);		
		return UFile.streamOutput(file);
	}
	
	public static StreamingOutput streamOutput(final File file) {
		try {
	        
			final StreamingOutput streamOutput = output -> {
				final BufferedOutputStream bus = new BufferedOutputStream(output);
				try {
			        final FileInputStream fizip = new FileInputStream(file);
			        final byte[] buffer2 = org.apache.poi.util.IOUtils.toByteArray(fizip);
			        bus.write(buffer2);
			    } catch (final Exception e) {
			    	throw UException.runtime(e);
			    }					
			};
			
			return streamOutput;
			
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
	}

	public static long size(Path path) {
		try {
			return Files.size(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
